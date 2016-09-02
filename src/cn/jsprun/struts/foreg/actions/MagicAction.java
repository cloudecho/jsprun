package cn.jsprun.struts.foreg.actions;
import java.lang.reflect.Method;
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
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.MagiclogId;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Membermagics;
import cn.jsprun.domain.MembermagicsId;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Settings;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.foreg.vo.magic.Magic_navbarVO;
import cn.jsprun.foreg.vo.magic.Magic_shopVO;
import cn.jsprun.foreg.vo.magic.Magic_navbarVO.OtherScoring;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.LogPage;
public class MagicAction extends BaseAction {
	private static final String tablePrefix = "jrun_";
	private final Byte buyOrUse = 1;
	private final Byte send = 2;
	@SuppressWarnings("unchecked")
	public ActionForward shop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, buyOrUse, request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String creditstrans = settingsMap.get("creditstrans");
		Map mapTemp1 = dataParse.characterParse(settingsMap.get("extcredits"), true);
		Map mapTemp2 = (Map)mapTemp1.get(Integer.valueOf(creditstrans));
		String extcredits = (String)mapTemp2.get("title");
		String magicType = request.getParameter("typeid");
		Members currentMember = (Members)session.getAttribute("user");
		String memberCredits = currentMember.getCredits().toString();
		Map<Integer,String> extcreditsMap = new HashMap<Integer, String>();
		extcreditsMap.put(1, String.valueOf(currentMember.getExtcredits1()));
		extcreditsMap.put(2, String.valueOf(currentMember.getExtcredits2()));
		extcreditsMap.put(3, String.valueOf(currentMember.getExtcredits3()));
		extcreditsMap.put(4, String.valueOf(currentMember.getExtcredits4()));
		extcreditsMap.put(5, String.valueOf(currentMember.getExtcredits5()));
		extcreditsMap.put(6, String.valueOf(currentMember.getExtcredits6()));
		extcreditsMap.put(7, String.valueOf(currentMember.getExtcredits7()));
		extcreditsMap.put(8, String.valueOf(currentMember.getExtcredits8()));
		List<Magics> magicsList = null;
		if (magicType == null) {
			Map<String,Integer> rMap = multi(request, memberId, "SELECT count(magicid) AS count FROM jrun_magics", "magic.jsp?action=shop");
			int beginsize = rMap.get("beginsize");
			int pagesize = rMap.get("pagesize");
			magicsList = otherSetService.queryAllMagicForPage(beginsize,pagesize);
		} else {
			Short type = 0;
			try{
				type = Short.valueOf(magicType);
			}catch(Exception exception){
				request.setAttribute("errorInfo", " ERROR .");
				return mapping.findForward("showMessage");
			}
			Map<String,Integer> rMap = multi(request, memberId, "SELECT count(magicid) AS count FROM jrun_magics WHERE type="+type, "magic.jsp?action=shop&typeid="+type);
			int beginsize = rMap.get("beginsize");
			int pagesize = rMap.get("pagesize");
			magicsList = otherSetService.queryAllMagicByType(type, beginsize, pagesize);
		}
		int discount = getDiscount(usergroupMap);
		Magic_shopVO pageValueOb = null;
		List<Magic_shopVO> voList = new ArrayList<Magic_shopVO>();
		for (int i = 0; i < magicsList.size(); i++) {
			Magics magics = magicsList.get(i);
			if (magics.getAvailable() == (byte) 1) {
				pageValueOb = new Magic_shopVO();
				pageValueOb.setId(magics.getMagicid());
				pageValueOb.setImageName(magics.getIdentifier().toLowerCase());
				pageValueOb.setMagicInfo(magics.getDescription());
				pageValueOb.setMagicName(magics.getName());
				pageValueOb.setNumOfSale(magics.getSalevolume() + "");
				pageValueOb.setPrice(magics.getPrice()*discount/10 + "");
				pageValueOb.setWeight(magics.getWeight() + "");
				pageValueOb.setStock(magics.getNum() + "");
				pageValueOb.setType(magics.getType() + "");
				pageValueOb.setUsable(magics.getAvailable() == (byte) 1);
				pageValueOb.setExtcredits(extcredits);
				voList.add(pageValueOb);
			}
		}
		request.setAttribute("valueObject", getMagic_navbarVO(memberId, memberCredits, usergroupMap, settingsMap, extcreditsMap));
		request.setAttribute("isBuyAnyOne", false);
		request.setAttribute("magic_shopVOList", voList);
		request.setAttribute("current", magicType == null ? "all" : magicType);
		return mapping.findForward("goMagic_shop");
	}
	public ActionForward prepareShopping(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, buyOrUse, request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String magicid = request.getParameter("magicid");
		Magics magics = otherSetService.queryMagicById(Short.valueOf(magicid));
		String creditstrans = settingsMap.get("creditstrans");
		Map mapTemp1 = dataParse.characterParse(settingsMap.get("extcredits"), true);
		Map mapTemp2 = (Map)mapTemp1.get(Integer.valueOf(creditstrans));
		String extcredits = (String)mapTemp2.get("title");
		int discount  = getDiscount(usergroupMap);
		Magic_shopVO pageValueOb = new Magic_shopVO();
		pageValueOb.setId(magics.getMagicid());
		pageValueOb.setImageName(magics.getIdentifier().toLowerCase());
		pageValueOb.setMagicInfo(magics.getDescription());
		pageValueOb.setMagicName(magics.getName());
		pageValueOb.setNumOfSale(magics.getSalevolume() + "");
		pageValueOb.setPrice(magics.getPrice()*discount/10 + "");
		pageValueOb.setWeight(magics.getWeight() + "");
		pageValueOb.setStock(magics.getNum() + "");
		pageValueOb.setType(magics.getType() + "");
		pageValueOb.setUsable(magicIsUserable(magics.getMagicperm(), usergroupMap.get("groupid")));
		pageValueOb.setExtcredits(extcredits);
		if (magics.getType() == 1||magics.getType() == 2) {
			String magicperm = magics.getMagicperm();
			Map map = dataParse.characterParse(magicperm, false);
			if (magics.getType() == 1) {
				String temp = (String) map.get("forum");
				if (temp!=null&&!temp.trim().equals("")) {
					String[] tempArray = temp.split("\t");
					for (int i = 0; i < tempArray.length; i++) {
						if (!tempArray[i].equals("")) {
							Short fid = Short.valueOf(tempArray[i]);
							Forums forums = forumService.findById(fid);
							if(forums != null){
								pageValueOb.setModuleList(forums.getFid(), forums
										.getName());
							}
						}
					}
				}
			}else{
				String temp = (String) map.get("targetgroups");
				if (temp!=null&&!temp.trim().equals("")) {
					String[] tempArray = temp.split("\t");
					List<String> usergroupNameList = pageValueOb.getUsergroupNameList();
					for (int i = 0; i < tempArray.length; i++) {
						if (!tempArray[i].equals("")) {
							Short userGroupId = Short.valueOf(tempArray[i]);
							Usergroups usergroups = userGroupService.findUserGroupById(userGroupId);
							if(usergroups != null){
								usergroupNameList.add(usergroups.getGrouptitle());
							}
						}
					}
				}
			}
		}
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
		request.setAttribute("valueObject", getMagic_navbarVO(memberId, memberCredits, usergroupMap, settingsMap, extcreditsMap));
		request.setAttribute("isBuyAnyOne", true);
		request.setAttribute("allowMagics",usergroupMap.get("allowmagics"));
		request.setAttribute("magic_shopVO", pageValueOb);
		request.setAttribute("magicaction", "shop");
		return mapping.findForward("goMagic_shop");
	}
	public ActionForward shopping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(!submitCheck(request, "operatesubmit")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		String sendToOtherUser = request.getParameter("sendToOtherUser");
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Members member = memberService.findMemberById(memberId);
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(member.getUid(), settingsMap, usergroupMap, sendToOtherUser==null?buyOrUse:send, request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		final String messageFinal = getMessage(request, "magics_succeed");
		String message = messageFinal;
		String magicid = request.getParameter("magicid");
		Magics magics = otherSetService.queryMagicById(Short.valueOf(magicid));
		String magicnum = request.getParameter("magicnum");
		magicnum = FormDataCheck.getNumberFromForm(magicnum);
		Long magicnumLong = Long.valueOf(magicnum);
		if (magicnumLong < 1) {
			message = getMessage(request, "magics_num_invalid");
			request.setAttribute("errorInfo", message);
		} else if (magicnumLong > (long) magics.getNum()) {
			message = getMessage(request, "magics_num_no_enough");
			request.setAttribute("errorInfo", message);
		} else {
			Integer magicPrice = magics.getPrice();
			String settingsValue = settingsMap.get("creditstrans");
			Map mapTemp1 = dataParse.characterParse(settingsMap.get("extcredits"), true);
			Map mapTemp2 = (Map)mapTemp1.get(Integer.valueOf(settingsValue));
			String extcredits = (String)mapTemp2.get("title");
			Integer value = null;
			try {
				Method method_get = Members.class.getMethod("getExtcredits"+settingsValue);
				value = (Integer)method_get.invoke(member);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int discount  = getDiscount(usergroupMap);
			if(magicPrice*discount/10*magicnumLong>value){
				message = getMessage(request, "magics_credits_no_enough", extcredits);
			}else{
				int timestamp=(Integer)request.getAttribute("timestamp");
				if(sendToOtherUser==null){
					if(!validateWeight(member.getUid(),usergroupMap,magics.getMagicid(),magicnumLong)){
						message = getMessage(request, "magics_weight_range_invalid");
					}else{
						updateMagics_NumAndSalevoume(magics, magicnumLong.intValue());
						Magiclog magiclog = getMagiclog(member.getUid(), magics.getMagicid(), magicnumLong.shortValue(), magicPrice*discount/10, member.getUid(),(byte)1,timestamp);
						setMemberBalance(value, magicPrice*discount/10*magicnumLong.intValue(), member, settingsValue);
						Membermagics memberMagics = creatOrUpdateMemberMagics(member.getUid(),  magics.getMagicid(), magicnumLong.shortValue());
						if(!MMMMService.userBuyMagic(magics, magiclog, member, memberMagics)){
							message = getMessage(request, "magics_buy_faild");
						}
					}
				}else{
					String tousername = request.getParameter("tousername");
					if(tousername!=null&&tousername.trim().equals("")){
						message = getMessage(request, "magics_send_invalid");
					} else if(member.getUsername().equals(tousername)){
						message = getMessage(request, "magics_give_myself");
					} else {
						Members targetMembers = memberService.findByName(tousername);
						if (targetMembers == null) {
							message = getMessage(request, "magics_target_nonexistence");
						} else {
							if (!validateWeight(targetMembers,magics.getMagicid(),magicnumLong)) {
								message = getMessage(request, "magics_weight_range_invalid");
							} else {
								updateMagics_NumAndSalevoume(magics,magicnumLong.intValue());
								Magiclog magiclog = getMagiclog(member.getUid(), magics.getMagicid(),magicnumLong.shortValue(), magicPrice* discount / 10, targetMembers.getUid(),(byte)3,timestamp);
								setMemberBalance(value, magicPrice*discount/10*magicnumLong.intValue(), member, settingsValue);
								Membermagics memberMagics = creatOrUpdateMemberMagics(targetMembers.getUid(),  magics.getMagicid(), magicnumLong.shortValue());
								if (!MMMMService.userBuyMagic(magics, magiclog,member, memberMagics)) {
									message = getMessage(request, "magics_buy_faild");
								}
							}
						}
					}
				}
			}
		}
		if(message.equals(messageFinal)){
			 String requestPath =  request.getContextPath()+"/magic.jsp?action=shop";
			 request.setAttribute("requestPath", requestPath);
			 request.setAttribute("successInfo", message);
		}else{
			request.setAttribute("errorInfo", message);
		}
		return mapping.findForward("showMessage");
	}
	private boolean validateWeight(int userid,Map<String,String> usergroupMap,Short magicsidOfGet,Long magicsNum){
		Short maxmagicsweight = Short.valueOf(usergroupMap.get("maxmagicsweight"));
		return memberMagicsService.validateWeight(userid, maxmagicsweight, magicsidOfGet, magicsNum);
	}
	private boolean validateWeight(Members members,Short magicsidOfGet,Long magicsNum){
		Integer userid = members.getUid();
		short userGroupId = members.getGroupid();
		Usergroups usergroups = userGroupService.findUserGroupById(userGroupId);
		short maxmagicsweight = usergroups.getMaxmagicsweight();
		return memberMagicsService.validateWeight(userid, maxmagicsweight, magicsidOfGet, magicsNum);
	}
	private int getDiscount(Map<String,String> usergroupMap){
		int discount = 0;
		try{
			discount = Integer.parseInt(usergroupMap.get("magicsdiscount"));
		}catch(NumberFormatException exception){
			exception.printStackTrace();
		}
		if(discount==0){
			discount=10;
		}
		return discount;
	}
	private void updateMagics_NumAndSalevoume(Magics magics,int num){
		magics.setNum(magics.getNum()-num);
		magics.setSalevolume(Integer.valueOf(magics.getSalevolume()+num).shortValue());
	}
	private Magiclog getMagiclog(int uid,short magicid,short magicNum,Integer price,Integer targetUid,Byte action,int timestamp){
		Magiclog magiclog = new Magiclog();
		MagiclogId magiclogId = new MagiclogId();
		magiclogId.setUid(uid);
		magiclogId.setMagicid(magicid);
		magiclogId.setAction(action);
		magiclogId.setDateline(timestamp);
		magiclogId.setAmount(magicNum);
		magiclogId.setPrice(price);
		magiclogId.setTargettid(0);
		magiclogId.setTargetpid(action==3?0:targetUid);
		magiclogId.setTargetuid(action==1?0:targetUid);
		magiclog.setId(magiclogId);
		return magiclog;
	}
	private void setMemberBalance(int moneyOfBefore,int moneyOfSpend,Members members,String extcreditsNumber){
		try {
			Integer balance = moneyOfBefore-moneyOfSpend;
			Method method_set = Members.class.getMethod("setExtcredits"+extcreditsNumber,Integer.class);
			method_set.invoke(members,balance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Membermagics creatOrUpdateMemberMagics(int uid,short magicid,short magicNum){
		Membermagics memberMagics =  memberMagicsService.getMemberMagics(uid, magicid);
		if(memberMagics==null){
			memberMagics = new Membermagics();
			MembermagicsId membermagicsId = new MembermagicsId();
			membermagicsId.setUid(uid);
			membermagicsId.setMagicid(magicid);
			memberMagics.setId(membermagicsId);
			memberMagics.setNum(magicNum);
		}else{
			memberMagics.setNum((short)(magicNum+memberMagics.getNum()));
		}
		return memberMagics;
	}
	private Magic_navbarVO getMagic_navbarVO(int memberId,String memberCredits,
			Map<String,String> usergroups,Map<String,String> settingsMap,
			Map<Integer,String> extcreditsMap){
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
		Magic_navbarVO magic_navbarVO = new Magic_navbarVO();
		magic_navbarVO.setOpenmarket(magicmarket!=null&&magicmarket.equals("1"));
		magic_navbarVO.setAllowMagicWeigth(usergroups.get("maxmagicsweight"));
		magic_navbarVO.setMagicWeigthNow(magicWeightNow.toString());
		magic_navbarVO.setScoring(memberCredits);
		magic_navbarVO.setAgio(usergroups.get("magicsdiscount"));
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
		Map<Integer,Map> tempMap1 = dataParse.characterParse(extcreditsFromMap,  true);
		Map tempMap2 = null;
		String extcreditValue = null;
		int key = 0;
		for(Entry<Integer,Map> entry : tempMap1.entrySet()){
			key = entry.getKey();
			tempMap2 = entry.getValue();
			extcreditValue = extcreditsMap.get(key);
			otherScoringList.add(magic_navbarVO.getOtherScoring((String)tempMap2.get("title"), extcreditValue,(String)tempMap2.get("unit"),(Integer.valueOf(creditstrans)==key)));
		}
		return magic_navbarVO;
	}
	private void setPage(HttpServletRequest request,Integer size,Integer pagesize,Integer pages,String url){
		LogPage logpage = new LogPage(size, pagesize, pages);
		request.setAttribute("lpp", pagesize);
		request.setAttribute("url", url);
		request.setAttribute("logpage", logpage);
	}
	@SuppressWarnings("unused")
	private static int convertInt(String s) {
		int count = 0;
		try {
			count = Integer.valueOf(s);
		} catch (Exception e) {
		}
		return count;
	}
	private String getAccessInfo(Integer memberId,Map<String,String> settingsMap,Map<String,String> usergroupMap,Byte action,HttpServletRequest request){
		String accessInfo = null;
		if(memberId==null){
			return accessInfo = getMessage(request, "notlogin");
		}
		if(settingsMap==null){
			return accessInfo = getMessage(request, "magics_getinfo_faild");
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
	private boolean magicIsUserable(String magicperm,String currentUsergroupId){
		Map tempMap = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(magicperm, false);
		String usergroups = (String)tempMap.get("usergroups");
		if(usergroups!=null&&!usergroups.trim().equals("")){
			String[] usergroupIdArray = usergroups.split("\t");
			for(String usergroupId : usergroupIdArray){
				if(usergroupId.equals(currentUsergroupId)){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	private Map<String, Integer> multi(HttpServletRequest request,int uid, String sql, String url) {
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
		request.setAttribute("multipage", (String)multi.get("multipage"));
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("beginsize", multiInfo.get("start_limit"));
		map.put("pagesize", tpp);
		return map;
	}
}
