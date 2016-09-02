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
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.MagiclogId;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Settings;
import cn.jsprun.foreg.vo.magic.MagicLogVO;
import cn.jsprun.foreg.vo.magic.Magic_navbarVO;
import cn.jsprun.foreg.vo.magic.Magic_navbarVO.OtherScoring;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
public class MagicLogAction extends BaseAction {
	private String tablePrefix = "jrun_";
	public ActionForward useLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		String timeoffset=(String)session.getAttribute("timeoffset");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, (byte)1,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String operation = "uselog";
		String sql = "select count(*) as count from "+tablePrefix+"magiclog where uid="+memberId+" and action=2";
		String url = "magic.jsp?action=magicLog&operation=useLog";
		MagicLogVO magicLogVO = new MagicLogVO();
		Map<String,Integer> multiMap = multi(magicLogVO,request, memberId, sql, url);
		List<Magiclog> magicLogList = magiclogService.getMagiclogByActionAndUid(memberId,new Byte[]{2},multiMap.get("beginsize"), multiMap.get("pagesize"));
		Map<Short,String> maigcId_MagicNameMap = getMaigcId_MagicNameMap(magicLogList);
		magicLogVO.setOperation(operation);
		magicLogVO.setMagicUseLogList(magicLogList, maigcId_MagicNameMap,request.getContextPath(),timeoffset);
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
		setMagicLogVOWithNavbar(magicLogVO, memberCredits, memberId, usergroupMap, settingsMap, extcreditsMap);
		request.setAttribute("valueObject", magicLogVO);
		return mapping.findForward("goMagic_log");
	}
	public ActionForward buyLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		String timeoffset=(String)session.getAttribute("timeoffset");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, (byte)1,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String operation = "buylog";
		String sql = "select count(*) as count from "+tablePrefix+"magiclog where uid="+memberId+" and action=1";
		String url = "magic.jsp?action=magicLog&operation=buyLog";
		MagicLogVO magicLogVO = new MagicLogVO();
		Map<String,Integer> multiMap = multi(magicLogVO,request, memberId, sql, url);
		List<Magiclog> magicLogList = magiclogService.getMagiclogByActionAndUid(memberId,new Byte[]{1},multiMap.get("beginsize"), multiMap.get("pagesize"));
		Map<Short,String> maigcId_MagicNameMap = getMaigcId_MagicNameMap(magicLogList);
		String util = getUtil(request);
		magicLogVO.setOperation(operation);
		magicLogVO.setMagicBuyLogList(magicLogList, maigcId_MagicNameMap, util,timeoffset);
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
		setMagicLogVOWithNavbar(magicLogVO, memberCredits, memberId, usergroupMap, settingsMap, extcreditsMap);
		request.setAttribute("valueObject", magicLogVO);
		return mapping.findForward("goMagic_log");
	}
	public ActionForward giveLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		String timeoffset=(String)session.getAttribute("timeoffset");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, (byte)1,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String operation = "givelog";
		String sql = "select count(*) as count from "+tablePrefix+"magiclog where uid="+memberId+" and action=3";
		String url = "magic.jsp?action=magicLog&operation=giveLog";
		MagicLogVO magicLogVO = new MagicLogVO();
		Map<String,Integer> multiMap = multi(magicLogVO,request, memberId, sql, url);
		List<Magiclog> magicLogList = magiclogService.getMagiclogByActionAndUid(memberId,new Byte[]{3},multiMap.get("beginsize"), multiMap.get("pagesize"));
		Map<Short,String> maigcId_MagicNameMap = getMaigcId_MagicNameMap(magicLogList);
		Map<Integer,String> memberId_memberNameMap = getTargetUid_UsernameMap(magicLogList);
		magicLogVO.setOperation(operation);
		magicLogVO.setMagicGiveOrReceiveLogList(magicLogList, maigcId_MagicNameMap, memberId_memberNameMap,request.getContextPath(),timeoffset);
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
		setMagicLogVOWithNavbar(magicLogVO, memberCredits, memberId, usergroupMap, settingsMap, extcreditsMap);
		request.setAttribute("valueObject", magicLogVO);
		return mapping.findForward("goMagic_log");
	}
	public ActionForward receiveLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		String timeoffset=(String)session.getAttribute("timeoffset");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, (byte)1,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String operation = "receivelog";
		String sql = "select count(*) as count from "+tablePrefix+"magiclog where targetuid="+memberId+" and action=3";
		String url = "magic.jsp?action=magicLog&operation=receiveLog";
		MagicLogVO magicLogVO = new MagicLogVO();
		Map<String,Integer> multiMap = multi(magicLogVO,request, memberId, sql, url);
		List<Magiclog> magicLogList = magiclogService.getMagiclogByActionAndTargetUid(memberId, new Byte[]{3},multiMap.get("beginsize"), multiMap.get("pagesize"));
		Map<Short,String> maigcId_MagicNameMap = getMaigcId_MagicNameMap(magicLogList);
		Map<Integer,String> memberId_memberNameMap = getUid_UsernameMap(magicLogList);
		magicLogVO.setOperation(operation);
		magicLogVO.setMagicGiveOrReceiveLogList(magicLogList, maigcId_MagicNameMap, memberId_memberNameMap,request.getContextPath(),timeoffset);
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
		setMagicLogVOWithNavbar(magicLogVO, memberCredits, memberId, usergroupMap, settingsMap, extcreditsMap);
		request.setAttribute("valueObject", magicLogVO);
		return mapping.findForward("goMagic_log");
	}
	public ActionForward marketLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		String timeoffset=(String)session.getAttribute("timeoffset");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, (byte)1,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String operation = "marketlog";
		String sql = "select count(*) as count from "+tablePrefix+"magiclog where uid="+memberId+" and action in (4,5,6)";
		String url = "magic.jsp?action=magicLog&operation=marketLog";
		MagicLogVO magicLogVO = new MagicLogVO();
		Map<String,Integer> multiMap = multi(magicLogVO,request, memberId, sql, url);
		List<Magiclog> magicLogList = magiclogService.getMagiclogByActionAndUid(memberId,new Byte[]{4,5,6},multiMap.get("beginsize"), multiMap.get("pagesize"));
		Map<Short,String> maigcId_MagicNameMap = getMaigcId_MagicNameMap(magicLogList);
		String util = getUtil(request);
		magicLogVO.setOperation(operation);
		magicLogVO.setMagicMarketLogVOList(magicLogList, maigcId_MagicNameMap, util,timeoffset);
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
		setMagicLogVOWithNavbar(magicLogVO, memberCredits, memberId, usergroupMap, settingsMap, extcreditsMap);
		request.setAttribute("valueObject", magicLogVO);
		return mapping.findForward("goMagic_log");
	}
	private Map<Short,String> getMaigcId_MagicNameMap(List<Magiclog> magicLogList){
		List<Short> magicIdList = new ArrayList<Short>();
		for(int i = 0;i<magicLogList.size();i++){
			magicIdList.add(magicLogList.get(i).getId().getMagicid());
		}
		List<Magics> magicsList = otherSetService.getMagicListByIdList(magicIdList);
		Map<Short,String> map = new HashMap<Short, String>();
		for(int i = 0;i<magicsList.size();i++){
			map.put(magicsList.get(i).getMagicid(), magicsList.get(i).getName());
		}
		return map;
	}
	private void setMagicLogVOWithNavbar(Magic_navbarVO magic_navbarVO,String memberCredits,Integer memberId,Map<String,String> usergroupMap,Map<String,String> settingsMap,Map<Integer,String> extcreditsMap){
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
		Map tempMap2 = null;
		String extcreditValue = null;
		int key = 0;
		for(Entry<Integer,Map> entry : tempMap1.entrySet()){
			key = entry.getKey();
			tempMap2 = entry.getValue();
			extcreditValue = extcreditsMap.get(key);
			otherScoringList.add(magic_navbarVO.getOtherScoring((String)tempMap2.get("title"), extcreditValue,(String)tempMap2.get("unit"),(Integer.valueOf(creditstrans)==key)));
		}
	}
	private String getUtil(HttpServletRequest request){
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		String extcreditsFromMap = null;
		String creditstransFromMap = null;
		if(settingsMap!=null){
			creditstransFromMap = settingsMap.get("creditstrans");
			extcreditsFromMap = settingsMap.get("extcredits");
		}else{
			extcreditsFromMap = ((Settings)creSetSer.getSetting("extcredits")).getValue();
			creditstransFromMap = ((Settings)creSetSer.getSetting("creditstrans")).getValue();
		}
		Map mapTemp1 = dataParse.characterParse(extcreditsFromMap, true);
		Map mapTemp2 = (Map)mapTemp1.get(Integer.valueOf(creditstransFromMap));
		return (String)mapTemp2.get("title");
	}
	private Map<Integer,String> getTargetUid_UsernameMap(List<Magiclog> magicLogList){
		List<Integer> memberIdList = new ArrayList<Integer>();
		for(int i = 0;i<magicLogList.size();i++){
			Magiclog magiclog = magicLogList.get(i);
			MagiclogId magiclogId = magiclog.getId();
			memberIdList.add(magiclogId.getTargetuid());
		}
		List<Members> memberList = memberService.getMemberListWithMemberIdList(memberIdList);
		Map<Integer,String> map = new HashMap<Integer, String>();
		if(memberList!=null){
			for(int i = 0;i<memberList.size();i++){
				Members members = memberList.get(i);
				map.put(members.getUid(), members.getUsername());
			}
		}
		return map;
	}
	private Map<Integer,String> getUid_UsernameMap(List<Magiclog> magicLogList){
		List<Integer> memberIdList = new ArrayList<Integer>();
		for(int i = 0;i<magicLogList.size();i++){
			Magiclog magiclog = magicLogList.get(i);
			MagiclogId magiclogId = magiclog.getId();
			memberIdList.add(magiclogId.getUid());
		}
		List<Members> memberList = memberService.getMemberListWithMemberIdList(memberIdList);
		Map<Integer,String> map = new HashMap<Integer, String>();
		if(memberList!=null){
			for(int i = 0;i<memberList.size();i++){
				Members members = memberList.get(i);
				map.put(members.getUid(), members.getUsername());
			}
		}
		return map;
	}
	private Map<String, Integer> multi(MagicLogVO magicLogVO,HttpServletRequest request,int uid, String sql, String url) {
		HttpSession session=request.getSession();
		Map<String, String> settings = (Map<String, String>) servlet.getServletContext().getAttribute("settings");
		Members member = uid > 0 ? (Members)session.getAttribute("user") : null;
		List<Map<String, String>> count = dataBaseService.executeQuery(sql);
		int num = Integer.valueOf(count.get(0).get("count"));
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
		int page = Math.max(Common.intval(request.getParameter("page")), 1);
		Map<String,Integer> multiInfo=Common.getMultiInfo(num, tpp, page);
		page=multiInfo.get("curpage");
		Map<String,Object> multi=Common.multi(num, tpp, page, url, 0, 10, true, false, null);
		magicLogVO.setMultipage((String)multi.get("multipage"));
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("beginsize",  multiInfo.get("start_limit"));
		map.put("pagesize", tpp);
		return map;
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
