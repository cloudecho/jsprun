package cn.jsprun.struts.foreg.actions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Settings;
import cn.jsprun.foreg.vo.magic.Magic_marketVO;
import cn.jsprun.foreg.vo.magic.Magic_market_prepareoperationVO;
import cn.jsprun.foreg.vo.magic.Magic_navbarVO;
import cn.jsprun.foreg.vo.magic.Magic_marketVO.MagicInfo;
import cn.jsprun.foreg.vo.magic.Magic_marketVO.MagicOfDB;
import cn.jsprun.foreg.vo.magic.Magic_navbarVO.OtherScoring;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FormDataCheck;
public class MagicMarketAction extends BaseAction {
	private String tablePrefix = "jrun_";
	public ActionForward showMagics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, (byte)1,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		Map<Integer,String> extcredits = new HashMap<Integer, String>();
		Members currentMember = (Members)session.getAttribute("user");
		String memberCredits = String.valueOf(currentMember.getCredits());
		extcredits.put(1, String.valueOf(currentMember.getExtcredits1()));
		extcredits.put(2, String.valueOf(currentMember.getExtcredits2()));
		extcredits.put(3, String.valueOf(currentMember.getExtcredits3()));
		extcredits.put(4, String.valueOf(currentMember.getExtcredits4()));
		extcredits.put(5, String.valueOf(currentMember.getExtcredits5()));
		extcredits.put(6, String.valueOf(currentMember.getExtcredits6()));
		extcredits.put(7, String.valueOf(currentMember.getExtcredits7()));
		extcredits.put(8, String.valueOf(currentMember.getExtcredits8()));
		String operation = request.getParameter("operation");
		String extcreditsFromMap = settingsMap.get("extcredits");
		String creditstrans = settingsMap.get("creditstrans");
		Map<Integer,Map> tempMap1 = dataParse.characterParse(extcreditsFromMap, true);
		Map tempMap2 = (Map)tempMap1.get(Integer.valueOf(creditstrans));
		String unit = (String)tempMap2.get("title");
		StringBuffer sqlBuffer = new StringBuffer("select count(*) as count from "+tablePrefix+"magicmarket");
		Magic_marketVO magic_marketVO = new Magic_marketVO();
		if(operation==null){
			Map<String,Integer> rMap = multi(request, memberId, sqlBuffer.toString(),null, "magic.jsp?action=market");
			int beginsize = rMap.get("beginsize");
			int pagesize = rMap.get("pagesize");
			showMagics_null(unit, magic_marketVO, beginsize, pagesize, tempMap1, memberCredits, memberId, creditstrans, usergroupMap, settingsMap, extcredits);
		}else if(operation.equals("myMagics")){
			sqlBuffer.append(" WHERE uid="+memberId);
			Map<String,Integer> rMap = multi(request, memberId, sqlBuffer.toString(), null, "magic.jsp?action=market&operation="+operation);
			int beginsize = rMap.get("beginsize");
			int pagesize = rMap.get("pagesize");
			showMagics_myMagics(unit, magic_marketVO, beginsize, pagesize, tempMap1, memberCredits, memberId, creditstrans, usergroupMap, settingsMap, extcredits);
		}else {
			String magicidFromSelect = request.getParameter("magicidFromSelect");
			String orderby = request.getParameter("orderby");
			String ascdesc = request.getParameter("ascdesc");
			if(magicidFromSelect!=null&&!magicidFromSelect.equals("")){
				sqlBuffer.append(" WHERE magicid=? ");
			}
			if(orderby!=null&&!orderby.equals("")){
				sqlBuffer.append(" ORDER BY "+orderby+" ");
				if(ascdesc!=null&&!ascdesc.equals("")){
					sqlBuffer.append(ascdesc);
				}
			}
			Map<String,Integer> rMap = multi(request, memberId, sqlBuffer.toString(),new String[]{magicidFromSelect}, "magic.jsp?action=market&operation="+operation+"&magicidFromSelect="+magicidFromSelect+"&orderby="+orderby+"&ascdesc="+ascdesc);
			int beginsize = rMap.get("beginsize");
			int pagesize = rMap.get("pagesize");
			showMagics_find(unit, magic_marketVO, magicidFromSelect, orderby, ascdesc, beginsize, pagesize, tempMap1, memberCredits, memberId, creditstrans, usergroupMap, settingsMap, extcredits);
		}
		request.setAttribute("valueObject", magic_marketVO);
		return mapping.findForward("magic_market");
	}
	private void showMagics_null(String unit,Magic_marketVO magic_marketVO,int firstNmu,int maxNum,Map<Integer,Map> extcreditsMap,String memberCredits,Integer memberId,String creditstrans,Map<String,String> usergroupMap,Map<String,String> settingsMap,Map<Integer,String> extcredits){
		List<Magicmarket> magicmarketList = magicMarketService.getAllMagicFormMarket(firstNmu, maxNum);
		List<Magics> magicsList = otherSetService.queryAllMagic();
		Map<Short, Magics> magicMap = MagicsListToMagicsMap(magicsList);
		encapsulationVO(magic_marketVO, unit, true, magicmarketList, magicMap, magicsList, null, null, null, extcreditsMap, memberCredits, memberId, creditstrans, usergroupMap, settingsMap, extcredits);
	}
	private Map<Short, Magics> MagicsListToMagicsMap(List<Magics> magicsList){
		Map<Short,Magics> map = new HashMap<Short, Magics>();
		for(int i = 0;i<magicsList.size();i++){
			map.put(magicsList.get(i).getMagicid(), magicsList.get(i));
		}
		return map;
	}
	private void encapsulationVO(Magic_marketVO magic_marketVO,String unit,
									boolean selectFind,List<Magicmarket> magicmarketList,
									Map<Short,Magics> magicMap,List<Magics> magicsList,
									String selectAscdesc,String selectOrderby,Short selectMagicId,
									Map<Integer,Map> extcreditsMap,String memberCredits,Integer memberId,String creditstrans,
									Map<String,String> usergroupMap,Map<String,String> settingsMap,Map<Integer,String> extcredits){
		magic_marketVO.setMagicUnit(unit);
		magic_marketVO.setSelectFind(selectFind);
		magic_marketVO.setSelectAscdesc(selectAscdesc);
		magic_marketVO.setSelectMagicId(selectMagicId);
		magic_marketVO.setSelectOrderby(selectOrderby);
		List<MagicInfo> magicInfoList = magic_marketVO.getMagicInfoList();
		List<MagicOfDB> magicOfDBList = magic_marketVO.getMagicOfDBList();
		if (magicmarketList != null) {
			for (int i = 0; i < magicmarketList.size(); i++) {
				MagicInfo magicInfo = magic_marketVO.getMagicInfo();
				Magicmarket magicmarket = magicmarketList.get(i);
				magicInfo.setDebusOrBuy(memberId.intValue() == magicmarket.getUid()
						.intValue() ? "debus" : "buy");
				magicInfo.setMagicFunction(magicMap.get(
						magicmarket.getMagicid()).getDescription());
				magicInfo.setMagicId(magicmarket.getMagicid());
				magicInfo.setMagicName(magicMap.get(magicmarket.getMagicid())
						.getName());
				magicInfo.setMagicNumber(magicmarket.getNum().intValue());
				magicInfo.setMagicPrice(magicmarket.getPrice());
				magicInfo.setMagicWeight(magicMap.get(magicmarket.getMagicid())
						.getWeight().intValue());
				magicInfo.setSellerId(magicmarket.getUid());
				magicInfo.setSellerName(magicmarket.getUsername());
				magicInfo.setMagicMarketId(magicmarket.getMid());
				magicInfoList.add(magicInfo);
			}
		}
		if (magicsList != null) {
			for (int i = 0; i < magicsList.size(); i++) {
				MagicOfDB magicOfDB = magic_marketVO.getMagicOfDB();
				Magics magics = magicsList.get(i);
				magicOfDB.setMagicId(magics.getMagicid());
				magicOfDB.setMagicName(magics.getName());
				magicOfDBList.add(magicOfDB);
			}
		}
		encapsulationMagic_navbarVO(magic_marketVO, memberCredits, memberId, extcreditsMap, creditstrans, usergroupMap, settingsMap, extcredits);
	}
	private void encapsulationMagic_navbarVO(Magic_navbarVO magic_navbarVO,String memberCredits,Integer memberId, Map<Integer,Map> extcreditsMap,String creditstrans,Map<String,String> usergroupMap,Map<String,String> settingsMap,Map<Integer,String> extcredits){
		Integer magicWeightNow = 0;
		String sql = "SELECT SUM(mm.num*m.weight) AS s FROM "+tablePrefix+"membermagics AS mm LEFT JOIN "+tablePrefix+"magics AS m ON mm.magicid=m.magicid WHERE mm.uid="+memberId;
		List<Map<String,String>> resultTemp = dataBaseService.executeQuery(sql);
		if(resultTemp!=null&&resultTemp.size()>0){
			Map<String,String> resultMap = resultTemp.get(0);
			if(resultMap!=null){
				String reuslt = resultMap.get("s");
				if(reuslt!=null){
					magicWeightNow = Integer.valueOf(reuslt);
				}
			}
		}
		String magicmarket = settingsMap.get("magicmarket");
		magic_navbarVO.setOpenmarket(magicmarket!=null&&magicmarket.equals("1"));
		magic_navbarVO.setAllowMagicWeigth(usergroupMap.get("maxmagicsweight"));
		magic_navbarVO.setMagicWeigthNow(magicWeightNow.toString());
		magic_navbarVO.setScoring(memberCredits);
		magic_navbarVO.setAgio(usergroupMap.get("magicsdiscount"));
		List<OtherScoring> otherScoringList = magic_navbarVO.getOtherScoringList();
		Map tempMap2 = null;
		String extcreditValue = null;
		int key = 0;
		for(Entry<Integer,Map> entry : extcreditsMap.entrySet()){
			key = entry.getKey();
			tempMap2 = entry.getValue();
			extcreditValue = extcredits.get(key);
			otherScoringList.add(magic_navbarVO.getOtherScoring((String)tempMap2.get("title"), extcreditValue,(String)tempMap2.get("unit"),(Integer.valueOf(creditstrans)==key)));
		}
	}
	private void showMagics_myMagics(String unit,Magic_marketVO magic_marketVO,int firstNmu,int maxNum,Map<Integer,Map> extcreditsMap,String memberCredits,Integer memberId,String creditstrans,Map<String,String> usergroupMap,Map<String,String> settingsMap,Map<Integer,String> extcredits){
		List<Magicmarket> magicmarketList = magicMarketService.getMagicFromMarketByUid(memberId, firstNmu, maxNum);
		List<Short> magicIdList = new ArrayList<Short>();
		for(int i = 0;i<magicmarketList.size();i++){
			magicIdList.add(magicmarketList.get(i).getMagicid());
		}
		List<Magics> magicsList = otherSetService.getMagicListByIdList(magicIdList);
		Map<Short,Magics> magicMap = MagicsListToMagicsMap(magicsList);
		encapsulationVO(magic_marketVO, unit, false, magicmarketList, magicMap, null, null, null, null, extcreditsMap, memberCredits, memberId, creditstrans, usergroupMap, settingsMap, extcredits);
	}
	private void showMagics_find(String unit,Magic_marketVO magic_marketVO,String magicidFromSelect,String orderby,String ascdesc,int firstNmu,int maxNum,Map<Integer,Map> extcreditsMap,String memberCredits,Integer memberId,String creditstrans,Map<String,String> usergroupMap,Map<String,String> settingsMap,Map<Integer,String> extcredits){
		Short magicid = Short.valueOf(FormDataCheck.getNumberFromForm(magicidFromSelect));
		List<Magicmarket> magicmarketList = magicMarketService.getMagicFormMarketByMagicId(magicid, orderby, ascdesc, firstNmu, maxNum);
		List<Magics> magicsList = otherSetService.queryAllMagic();
		Map<Short,Magics> magicMap = MagicsListToMagicsMap(magicsList);
		encapsulationVO(magic_marketVO, unit, true, magicmarketList, magicMap, magicsList, ascdesc, orderby, magicid, extcreditsMap, memberCredits, memberId, creditstrans, usergroupMap, settingsMap, extcredits);
	}
	private Map<String, Integer> multi(HttpServletRequest request,int uid, String sql,String[] args, String url) {
		HttpSession session=request.getSession();
		Map<String, String> settings = (Map<String, String>) servlet.getServletContext().getAttribute("settings");
		Members member = uid > 0 ? (Members)session.getAttribute("user") : null;
		List<Map<String, String>> count = dataBaseService.executeQuery(sql,args);
		int num = Integer.valueOf(count.get(0).get("count"));
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
		int page = Math.max(Common.intval(request.getParameter("page")), 1);
		Map<String,Integer> multiInfo=Common.getMultiInfo(num, tpp, page);
		page=multiInfo.get("curpage");
		Map<String,Object> multi=Common.multi(num, tpp, page, url, 0, 10, true, false, null);
		request.setAttribute("multipage", (String)multi.get("multipage"));
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("beginsize", multiInfo.get("start_limit"));
		map.put("pagesize", tpp);
		return map;
	}
	public ActionForward marketPrepareOperation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, (byte)1,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		Short magicId = Short.valueOf(request.getParameter("magicid"));
		Short magicMarketId = Short.valueOf(request.getParameter("magicMarketId"));
		String debusOrBuy = request.getParameter("debusOrBuy");
		Magics magics = otherSetService.queryMagicById(magicId);
		Magicmarket magicmarket = magicMarketService.getMagicmarketById(magicMarketId);
		String extcreditsFromMap = null;
		String creditstrans = null;
		if(settingsMap!=null){
			extcreditsFromMap = settingsMap.get("extcredits");
			creditstrans = settingsMap.get("creditstrans");
		}else{
			extcreditsFromMap = ((Settings)creSetSer.getSetting("extcredits")).getValue();
			creditstrans = ((Settings)creSetSer.getSetting("creditstrans")).getValue();
		}
		Map<Integer,Map> tempMap1 = dataParse.characterParse(extcreditsFromMap, true);
		Map tempMap2 = (Map)tempMap1.get(Integer.valueOf(creditstrans));
		String unit = (String)tempMap2.get("title");
		List<Map<String,String>> memberInfoMapList = dataBaseService.executeQuery("SELECT credits,extcredits1,extcredits2,extcredits3,extcredits4,extcredits5,extcredits6,extcredits7,extcredits8 FROM "+tablePrefix+"members WHERE uid="+memberId);
		String memberCredits = "";
		Map<Integer,String> extcreditsMap = new HashMap<Integer, String>();
		if(memberInfoMapList!=null&&memberInfoMapList.size()>0){
			Map<String,String> memberInfoMap = memberInfoMapList.get(0);
			memberCredits = memberInfoMap.get("credits");
			for(int i = 1;i<9;i++){
				extcreditsMap.put(i, memberInfoMap.get("extcredits"+i));
			}
		}
		Magic_market_prepareoperationVO valueObject = new Magic_market_prepareoperationVO();
		valueObject.setMagicMarketId(magicMarketId.toString());
		valueObject.setMagicInfo(magics.getDescription());
		valueObject.setImageName(magics.getIdentifier().toLowerCase());
		valueObject.setMagicName(magics.getName());
		valueObject.setMagicPrice(magicmarket.getPrice().toString());
		valueObject.setMagicStock(magicmarket.getNum().toString());
		valueObject.setMagicUtil(unit);
		valueObject.setMagicWeight(magics.getWeight().toString());
		valueObject.setOperation(debusOrBuy);
		encapsulationMagic_navbarVO(valueObject, memberCredits, memberId, tempMap1, creditstrans, usergroupMap, settingsMap, extcreditsMap);
		request.setAttribute("valueObject", valueObject);
		return mapping.findForward("magic_market_prepareoperation");
	}
	public ActionForward marketOperating(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(!submitCheck(request, "operation")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, (byte)1,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String magicnumFromRequest = request.getParameter("magicnum");
		String magicMarketId = request.getParameter("magicMarketId");
		String operation = request.getParameter("operation");
		Long magicNumLong = Long.valueOf(FormDataCheck.getNumberFromForm(magicnumFromRequest));
		int magicNum = 0;
		if(magicNumLong>Integer.MAX_VALUE){
			magicNum = Integer.MAX_VALUE;
		}else{
			magicNum = magicNumLong.intValue();
		}
		if(magicNum==0){
			request.setAttribute("errorInfo", getMessage(request, "magics_num_invalid"));
			return mapping.findForward("showMessage");
		}
		Short magicmarketId = Short.valueOf(magicMarketId);
		Magicmarket magicmarket = magicMarketService.getMagicmarketById(magicmarketId);
		if(magicmarket.getNum()<magicNum){
			request.setAttribute("errorInfo", getMessage(request, "magics_amount_no_enough"));
			return mapping.findForward("showMessage");
		}
		boolean bool = false;
		try{
			bool = memberMagicsService.validateWeight(memberId,Short.valueOf(usergroupMap.get("maxmagicsweight")),magicmarket.getMagicid(),magicNumLong);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		if(!bool){
			request.setAttribute("errorInfo", getMessage(request, "magics_overweight_oneself"));
			return mapping.findForward("showMessage");
		}
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		if(operation.equals("buy")){
			operation_buy(settingsMap, memberId, magicNumLong.shortValue(), magicmarket,timestamp);
		}else{
			operation_debus(memberId, magicNumLong.shortValue(), magicmarket,timestamp);
		}
		request.setAttribute("successInfo", getMessage(request, "magics_succeed"));
		request.setAttribute("requestPath", "magic.jsp?action=market");
		return mapping.findForward("showMessage");
	}
	private void operation_buy(Map<String,String> settingsMap,Integer uid,Short num,Magicmarket magicmarket,int timestamp){
		String creditstaxFromMap = null;
		String creditstransFromMap = null;
		if(settingsMap!=null){
			creditstaxFromMap = settingsMap.get("creditstax");
			creditstransFromMap = settingsMap.get("creditstrans");
		}else{
			creditstaxFromMap = ((Settings)creSetSer.getSetting("creditstax")).getValue();
			creditstransFromMap = ((Settings)creSetSer.getSetting("creditstrans")).getValue();
		}
		Double revenue = Double.valueOf(creditstaxFromMap);
		Short extcreditsNum = Short.valueOf(creditstransFromMap);
		short magicid = magicmarket.getMagicid();
		updateMagicLog(uid, magicmarket.getMagicid(), (byte)5, num, magicmarket.getPrice(), 0, uid, 0,timestamp);
		List<Map<String,String>> queryMapList = dataBaseService.executeQuery("SELECT magicid FROM "+tablePrefix+"membermagics WHERE magicid='"+magicid+"' AND uid='"+uid+"'");
		if(queryMapList != null){
			if(queryMapList.size()>0){
				dataBaseService.execute("UPDATE "+tablePrefix+"membermagics SET num=num+'"+num+"' WHERE magicid='"+magicid+"' AND uid='"+uid+"'");
			}else{
				dataBaseService.execute("INSERT INTO "+tablePrefix+"membermagics (uid, magicid, num) VALUES ('"+uid+"', '"+magicid+"', '"+num+"')");
			}
		}
		short mid = magicmarket.getMid();
		if(magicmarket.getNum().shortValue() == num){
			dataBaseService.execute("DELETE FROM "+tablePrefix+"magicmarket WHERE mid='"+mid+"'");
		}else{
			dataBaseService.execute("UPDATE "+tablePrefix+"magicmarket SET num=num+(-'"+num+"') WHERE mid='"+mid+"'");
		}
		int price = magicmarket.getPrice();
		int	totalcredit = (int)Math.floor(num*price*(1-revenue));
		dataBaseService.execute("UPDATE "+tablePrefix+"members SET extcredits"+extcreditsNum+"=extcredits"+extcreditsNum+"+'"+totalcredit+"' WHERE uid='"+magicmarket.getUid()+"'");
		totalcredit = num*price;
		dataBaseService.execute("UPDATE "+tablePrefix+"members SET extcredits"+extcreditsNum+"=extcredits"+extcreditsNum+"+(-'"+totalcredit+"') WHERE uid='"+uid+"'");
	}
	private void operation_debus(Integer uid,Short num,Magicmarket magicmarket,int timestamp){
		short magicid = magicmarket.getMagicid();
		updateMagicLog(uid, magicmarket.getMagicid(), (byte)6, num, magicmarket.getPrice(), 0, 0, uid,timestamp);
		List<Map<String,String>> queryMapList = dataBaseService.executeQuery("SELECT magicid FROM "+tablePrefix+"membermagics WHERE magicid='"+magicid+"' AND uid='"+uid+"'");
		if(queryMapList != null){
			if(queryMapList.size()>0){
				dataBaseService.execute("UPDATE "+tablePrefix+"membermagics SET num=num+'"+num+"' WHERE magicid='"+magicid+"' AND uid='"+uid+"'");
			}else{
				dataBaseService.execute("INSERT INTO "+tablePrefix+"membermagics (uid, magicid, num) VALUES ('"+uid+"', '"+magicid+"', '"+num+"')");
			}
		}
		short mid = magicmarket.getMid();
		if(magicmarket.getNum().shortValue() == num){
			dataBaseService.execute("DELETE FROM "+tablePrefix+"magicmarket WHERE mid='"+mid+"'");
		}else{
			dataBaseService.execute("UPDATE "+tablePrefix+"magicmarket SET num=num+(-'"+num+"') WHERE mid='"+mid+"'");
		}
	}
	private void updateMagicLog(Integer uid, Short magicid, Byte action, Short amount, Integer price, Integer targettid, Integer targetpid, Integer targetuid,int timestamp){
		dataBaseService.execute("INSERT INTO "+tablePrefix+"magiclog (uid, magicid, action, dateline, amount, price, targettid, targetpid, targetuid) " +
				"VALUES ('"+uid+"', '"+magicid+"', '"+action+"', '"+timestamp+"', '"+amount+"', '"+price+"','"+0+"', '"+0+"', '"+uid+"')");
	}
	private String getAccessInfo(Integer memberId,Map<String,String> settingsMap,Map<String,String> usergroupMap,Byte action,HttpServletRequest request){
		String accessInfo = null;
		if(memberId==null){
			return accessInfo = getMessage(request, "notlogin");
		}
		if(settingsMap==null){
			return accessInfo = getMessage(request, "magics_getinfo_error");
		}
		String magicstatus = settingsMap.get("magicstatus");
		if(magicstatus==null||!magicstatus.equals("1")){
			return accessInfo = getMessage(request, "magics_no_open");
		}
		String magicmarket = settingsMap.get("magicmarket");
		if(magicmarket==null||!magicmarket.equals("1")){
			return accessInfo = getMessage(request, "magics_market_no_open");
		}
		String creditstrans = settingsMap.get("creditstrans");
		if (creditstrans==null||creditstrans.equals("0")) {
			return accessInfo = getMessage(request, "magics_credits_no_open");
		}
		if(usergroupMap==null){
			return accessInfo = getMessage(request, "magics_get_group_info_faild");
		}
		String allowMagics = usergroupMap.get("allowmagics");
		if(allowMagics==null){
			return accessInfo = getMessage(request, "magics_get_use_magics_info_f");
		}
		if(Byte.parseByte(allowMagics)<action){
			if(action==1){
				return accessInfo = getMessage(request, "magics_perm");
			}else{
				return accessInfo = getMessage(request, "magics_not_to_transfer");
			}
		}
		return accessInfo;
	}
}
