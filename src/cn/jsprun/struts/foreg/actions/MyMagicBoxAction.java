package cn.jsprun.struts.foreg.actions;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.MagiclogId;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Membermagics;
import cn.jsprun.domain.MembermagicsId;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Posts;
import cn.jsprun.domain.Settings;
import cn.jsprun.domain.Threads;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.foreg.service.MemberMagic_MagiclogService;
import cn.jsprun.foreg.vo.magic.Magic_navbarVO;
import cn.jsprun.foreg.vo.magic.Magic_userVO;
import cn.jsprun.foreg.vo.magic.Magic_userVO_Operation;
import cn.jsprun.foreg.vo.magic.Magic_navbarVO.OtherScoring;
import cn.jsprun.foreg.vo.magic.Magic_userVO.MagicInfo;
import cn.jsprun.foreg.vo.magic.Magic_userVO_Operation.Module;
import cn.jsprun.service.CreditsSetService;
import cn.jsprun.service.ForumService;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.JspRunConfig;
public class MyMagicBoxAction extends BaseAction {
	private static final String tablePrefix = "jrun_";
	private final Byte buyOrUse = 1;
	private final Byte sendOrSell = 2;
	public ActionForward showMagicBox(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		String typeid = request.getParameter("typeid");
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, buyOrUse,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		Map<String,Integer> rMap = multi(request, memberId, "SELECT count(magicid) AS count FROM jrun_membermagics WHERE uid='"+memberId+"'", "magic.jsp?action=user");
		int beginsize = rMap.get("beginsize");
		int pagesize = rMap.get("pagesize");
		StringBuffer sqlBuffer = new StringBuffer("SELECT mm.magicid, mm.num, m.identifier, m.name, m.description, m.weight FROM " +tablePrefix+
				"magics AS m LEFT JOIN "+tablePrefix+"membermagics AS mm ON m.magicid=mm.magicid WHERE mm.uid='"+memberId+"'");
		if(typeid!=null){
			try{
				Short.valueOf(typeid);
			}catch(NumberFormatException exception){
				request.setAttribute("errorInfo", " ERROR ., ");
				return mapping.findForward("showMessage");
			}
			sqlBuffer.append(" AND m.type="+typeid);
		}
		sqlBuffer.append(" LIMIT "+beginsize+", "+pagesize);
		List<Map<String,String>> resultList = dataBaseService.executeQuery(sqlBuffer.toString());
		Magic_userVO valueObject = new Magic_userVO();
		if(resultList!=null){
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
			setMagic_userVO(valueObject, typeid, resultList, memberCredits, memberId, settingsMap, usergroupMap, extcreditsMap);
		}
		request.setAttribute("valueObject", valueObject);
		return mapping.findForward("goMagic_user");
	}
	public ActionForward prepareOperation(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		String operation = request.getParameter("operation");
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		Byte action = buyOrUse;
		if(operation.equals("sell")||operation.equals("give")){
			action = sendOrSell;
		}
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, action,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String magicidFormRequest = request.getParameter("magicid");
		short magicid = 0 ;
		try{
			magicid = Short.valueOf(magicidFormRequest);
		}catch(NumberFormatException exception){
			request.setAttribute("errorInfo", " ERROR .. ");
			return mapping.findForward("showMessage");
		}
		Magics magics = otherSetService.queryMagicById(magicid);
		if(!operation.equals("drop")&&!magicIsUserable(magics.getMagicperm(), usergroupMap.get("groupid"))){
			request.setAttribute("errorInfo", getMessage(request, "magics_group_not_use"));
			return mapping.findForward("showMessage");
		}
		Membermagics memberMagics = memberMagicsService.getMemberMagics(memberId, magics.getMagicid());
		if(memberMagics==null){
			return mapping.findForward("goMagic_user");
		}
		List<Forums> allForumsList = getAllForumsList(magics);
		List<Map<String,String>> forumsList = getForums(magics);
		List<Usergroups> usergroupsList = getUsergroups(magics);
		Magic_userVO_Operation valueObject = new Magic_userVO_Operation();
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
		setMagic_userVO_Operation(operation, valueObject, magics, memberMagics, forumsList, usergroupsList, allForumsList, memberCredits, memberId, usergroupMap, settingsMap, extcreditsMap,request);
		request.setAttribute("valueObject", valueObject);
		request.setAttribute("magicaction", "user");
		return mapping.findForward("goMagic_user");
	}
	private void setMagic_userVO_Operation(String operation,Magic_userVO_Operation valueObject,
											Magics magics,Membermagics memberMagics,
											List<Map<String,String>> forumsList,List<Usergroups> usergroupsList,
											List<Forums> allForumsList,String memberCredits,Integer memberId,
											Map<String,String> usergroupMap,Map<String,String> settingsMap,
											Map<Integer,String> extcreditsMap,HttpServletRequest request){
		valueObject.setOperation(operation);
		valueObject.setMagicId(magics.getMagicid());
		valueObject.setImageName(magics.getIdentifier().toLowerCase());
		valueObject.setMagicName(magics.getName());
		valueObject.setMagicExplaining(magics.getDescription());
		short magicCount = memberMagics.getNum();
		valueObject.setMagicCount(magicCount);
		valueObject.setAllMagicWeight(magics.getWeight()*magicCount);
		String groupid = usergroupMap.get("groupid");
		if(magicIsUserable(magics.getMagicperm(), groupid)){
			valueObject.setUsable(getMessage(request, "available"));
		}else{
			valueObject.setUsable(getMessage(request, "unavailable"));
		}
		String magicType = magics.getType().toString();
		valueObject.setMagicType(magicType);
		if(magicType.equals("1")){
			if(forumsList!=null){
				List<Magic_userVO_Operation.Module> moduleList = valueObject.getModuleList();
				for(Map<String,String> formId_Name : forumsList){
					Module module = valueObject.getModule();
					module.setId(Integer.parseInt(formId_Name.get("fid")));
					module.setName(formId_Name.get("name"));
					moduleList.add(module);
				}
			}
		}
		else if(magicType.equals("2")){
			List<String> usergroupNameList = valueObject.getUsergroupNameList();
			if(usergroupsList!=null){
				for(int i = 0;i<usergroupsList.size();i++){
					usergroupNameList.add(usergroupsList.get(i).getGrouptitle());
				}
			}
		}
		if(magics.getMagicid()==3
				||magics.getMagicid()==6
				||magics.getMagicid()==10
				||magics.getMagicid()==11){
			valueObject.setOperationInfo1(getMessage(request, "target_pid"));
			valueObject.setTextName("targetPid");
		}else if(magics.getMagicid()==1
				||magics.getMagicid()==4
				||magics.getMagicid()==5
				||magics.getMagicid()==8
				||magics.getMagicid()==9
				||magics.getMagicid()==12){
			valueObject.setOperationInfo1(getMessage(request, "target_tid"));
			valueObject.setTextName("targetTid");
		}else if(magics.getMagicid()==2){
			valueObject.setOperationInfo1(getMessage(request, "MOK_info"));
		}else if(magics.getMagicid()==7){
			valueObject.setOperationInfo1("target_username");
			valueObject.setTextName("targetUsername");
		}
		valueObject.setShowOperationInfo2(magics.getMagicid()==1||magics.getMagicid()==12);
		if(magics.getMagicid()==1){
			valueObject.setOperationInfo2(getMessage(request, "CCK_color"));
		}else if(magics.getMagicid()==12){
			valueObject.setOperationInfo2(getMessage(request, "MVK_target"));
		}
		valueObject.setIsChangeColor(magics.getMagicid()==1);
		if (magics.getMagicid()==1) {
			List<String> colorList = valueObject.getColorList();
			String[] colorArray = Common.THREAD_COLORS;
			for (int i = 1; i < colorArray.length; i++) {
				colorList.add(colorArray[i]);
			}
		}
		if (magics.getMagicid()==12) {
			List<Map<String,String>> mapList = dataBaseService.executeQuery("SELECT extgroupids FROM "+tablePrefix+"members WHERE uid="+memberId);
			String extgroupids = null;
			if(mapList!=null&&mapList.size()>0){
				extgroupids = mapList.get(0).get("extgroupids");
			}
			valueObject.setSelectContent(Common.forumselect(false, false,Short.valueOf(groupid),extgroupids!=null?extgroupids:"",null));
		}
		valueObject.setDisplayText(magics.getMagicid()!=2);
		setMagic_navbarVO(valueObject, memberCredits, memberId, usergroupMap, settingsMap, extcreditsMap);
	}
	private List<Forums> getAllForumsList(Magics magics){
		if(magics.getMagicid()==12){
			ForumService forumService = (ForumService) BeanFactory
			.getBean("forumService");
			return forumService.findAll();
		}else {
			return null;
		}
	}
	private List<Map<String,String>> getForums(Magics magics){
		if(magics.getType()==1){
			String magicPerm = magics.getMagicperm();
			Map temp1 = dataParse.characterParse(magicPerm, false);
			String forumsIdString = (String)temp1.get("forum");
			if(forumsIdString!=null&&!forumsIdString.trim().equals("")){
				StringBuffer sqlBuffer = new StringBuffer("SELECT f.fid , f.name FROM "+tablePrefix+"forums AS f WHERE f.fid IN(");
				String[] forumsIdArray = forumsIdString.split("\t");
				for(String forumsId : forumsIdArray){
					if(!forumsId.equals("")){
						sqlBuffer.append(forumsId+",");
					}
				}
				int sqlBufferLength = sqlBuffer.length();
				sqlBuffer.replace(sqlBufferLength-1, sqlBufferLength, ")");
				return dataBaseService.executeQuery(sqlBuffer.toString());
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	private List<Usergroups> getUsergroups(Magics magics){
		if(magics.getType()==2){
			String magicPerm = magics.getMagicperm();
			Map temp1 = dataParse.characterParse(magicPerm, false);
			String usergroupIdString = (String)temp1.get("targetgroups");
			if(usergroupIdString==null){
				return null;
			}
			String[] usergroupIdArray = usergroupIdString.split("\t");
			List<Short> usergroutIdList = new ArrayList<Short>();
			for(int i = 0;i<usergroupIdArray.length;i++){
				if(!usergroupIdArray[i].equals("")){
					usergroutIdList.add(Short.valueOf(usergroupIdArray[i]));
				}
			}
			return userGroupService.getUsergroupList(usergroutIdList);
		}else {
			return null;
		}
	}
	public  ActionForward operating(ActionMapping mapping, ActionForm form,
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
		String operation = request.getParameter("operation");
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		Byte action = buyOrUse;
		if(operation.equals("sell")||operation.equals("give")){
			action = sendOrSell;
		}
		String accessInfo = getAccessInfo(memberId, settingsMap, usergroupMap, action,request);
		if(accessInfo!=null){
			request.setAttribute("errorInfo", accessInfo);
			return mapping.findForward("showMessage");
		}
		String targetPidFromPage = request.getParameter("targetPid");
		String targetTidFromPage = request.getParameter("targetTid");
		String targetUsername = request.getParameter("targetUsername");
		short magicid = Short.valueOf(request.getParameter("magicid"));
		String magicnumFromPage = request.getParameter("magicnum");
		String toUsername = request.getParameter("tousername");
		String priceFromPage = request.getParameter("price");
		targetPidFromPage = getIntegerStringFromRequest(targetPidFromPage);
		targetTidFromPage = getIntegerStringFromRequest(targetTidFromPage);
		magicnumFromPage = getIntegerStringFromRequest(magicnumFromPage);
		priceFromPage = getIntegerStringFromRequest(priceFromPage);
		Integer targetPid = targetPidFromPage==null?null:Integer.valueOf(targetPidFromPage);
		Integer targetTid = targetTidFromPage==null?null:Integer.valueOf(targetTidFromPage);
		Integer magicnum = magicnumFromPage==null?null:Integer.valueOf(magicnumFromPage);
		Integer price = priceFromPage==null?null:Integer.valueOf(priceFromPage);
		Magics magics = otherSetService.queryMagicById(magicid);
		if(!operation.equals("drop")&&!magicIsUserable(magics.getMagicperm(), usergroupMap.get("groupid"))){
			request.setAttribute("errorInfo", getMessage(request, "magics_group_not_use"));
			return mapping.findForward("showMessage");
		}
		if(operation.equals("use")){
			if(targetTid!=null){
				List<Map<String,String>> checkMapList = dataBaseService.executeQuery("SELECT * FROM "+tablePrefix+"threadsmod WHERE magicid='0' AND tid='"+targetTid+"'");
				String[] tempActionArray = {"CLS", "ECL", "EHL", "EST", "HLT", "STK"};
				for(Map<String,String> tempMap : checkMapList){
					if("0".equals(tempMap.get("magicid"))&&Arrays.binarySearch(tempActionArray, tempMap.get("action"))>-1){
						request.setAttribute("errorInfo", getMessage(request, "magics_mod_forbidden"));
						return mapping.findForward("showMessage");
					}
				}
			}
			request.setAttribute("targetPid", targetPid);
			request.setAttribute("targetTid", targetTid);
			request.setAttribute("targetUsername", targetUsername);
			if(operating_use(request,response, magics, targetPid,targetTid,targetUsername)){
				if(magicid==2){
					request.setAttribute("errorInfo", request.getAttribute("messageFromOperationMagic"));
				}else if(magicid==3){
					request.setAttribute("errorInfo", request.getAttribute("messageFromOperationMagic"));
				}else if(magicid==7){
					String messageFromOperationMagic = (String)request.getAttribute("messageFromOperationMagic");
					if(messageFromOperationMagic!=null){
						request.setAttribute("errorInfo", messageFromOperationMagic);
					}else{
						List<Short> keyList = (List<Short>)request.getAttribute("messageFromOperationMagic_rl");
						StringBuffer message = null;
						int keyListSize = keyList.size();
						if(keyListSize==0){
							message = new StringBuffer(getMessage(request, "magics_user_not_online"));
						}else{
							message = new StringBuffer(getMessage(request, "magics_user_on"));
						}
						for(Short key : keyList){
							message.append(getMessage(request,key.toString())+"\t");
						}
						request.setAttribute("errorInfo", message);
					}
				}else {
					String messageFromOperationMagic = (String)request.getAttribute("messageFromOperationMagic");
					if(messageFromOperationMagic!=null){
						request.setAttribute("errorInfo", messageFromOperationMagic);
					}else{
						request.setAttribute("successInfo", getMessage(request, "magics_operation_succeed"));
						request.setAttribute("requestPath", request.getContextPath()+"/magic.jsp?action=user");
					}
				}
			}else{
				String tempMessage = (String)request.getAttribute("errorInfo");
				if(tempMessage==null){
					tempMessage = getMessage(request, "magics_operation_faild");
				}
				request.setAttribute("errorInfo", tempMessage);
			}
		}else if(operation.equals("sell")){
			if(price!=null&&magicnum!=null){
				String result = operating_sell(magicnum, price, magics, request);
				if(result==null){
					request.setAttribute("successInfo", getMessage(request, "magics_succeed"));
					request.setAttribute("requestPath", request.getContextPath()+"/magic.jsp?action=market");
				}else{
					request.setAttribute("errorInfo", result);
				}
			}else{
				request.setAttribute("errorInfo", getMessage(request, "magics_faild"));
			}
		}else if(operation.equals("give")){
			if(magicnum!=null&&toUsername!=null){
				String result = operating_give(magicnum, toUsername, magics, request);
				if(result==null){
					request.setAttribute("successInfo", getMessage(request, "magics_succeed"));
					request.setAttribute("requestPath", request.getContextPath()+"/magic.jsp?action=user");
				}else{
					request.setAttribute("errorInfo", result);
				}
			}else{
				request.setAttribute("errorInfo", getMessage(request, "magics_faild"));
			}
		}else {
			if(magicnum!=null){
				String result = operating_drop(magicnum,magicid,request);
				if(result==null){
					request.setAttribute("successInfo", getMessage(request, "magics_succeed"));
					request.setAttribute("requestPath", request.getContextPath()+"/magic.jsp?action=user");
				}else{
					request.setAttribute("errorInfo", result);
				}
			}else{
				request.setAttribute("errorInfo", getMessage(request, "magics_faild"));
			}
		}
		return mapping.findForward("showMessage");
	}
	private String getIntegerStringFromRequest(String targetString){
		if (targetString != null) {
			targetString = FormDataCheck
					.getNumberFromForm(targetString);
			Long temp = Long.valueOf(targetString);
			if (temp > Integer.MAX_VALUE) {
				targetString = Integer.MAX_VALUE + "";
			}
		}
		return targetString;
	}
	private boolean operating_use(HttpServletRequest request,HttpServletResponse response,Magics magics,Integer targetPid,Integer targetTid,String targetUsername){
		Map magicPermMap = getMapByString(magics);
		if(validateScriptFile(magics)){
			if(usableByCurrentUser(request,magicPermMap)){
				String temp = validateTargetBeing(magicPermMap,targetPid,targetTid,targetUsername,request);
				if(temp==null){
					if(magics.getAvailable()==1){
						if(validateMemberMagics(magics, request)){
							String scriptPath = "/include/magics/"+magics.getFilename();
							RequestDispatcher dispatcher = request.getRequestDispatcher(scriptPath);
							try {
								dispatcher.include(request, response);
								return true;
							} catch (Exception exception){
								return false;
							}
						}else{
							request.setAttribute("errorInfo", getMessage(request, "magics_nonexistence"));
							return false;
						}
					}else{
						request.setAttribute("errorInfo", getMessage(request, "magics_nonexistence"));
						return false;
					}
				}else{
					request.setAttribute("errorInfo", temp);
					return false;
				}
			}else{
				request.setAttribute("errorInfo", getMessage(request, "magics_group_not_use"));
				return false;
			}
		}else {
			request.setAttribute("errorInfo", getMessage(request, "magics_filename_nonexistence"," ./include/magic/"+magics.getFilename()));
			return false;
		}
	}
	private boolean validateScriptFile(Magics magics){
		String fileName = magics.getFilename();
		File file = new File(JspRunConfig.realPath+"include/magics/"+fileName);
		if(!file.isFile()){
			return false;
		}else{
			return true;
		}
	}
	private Map getMapByString(Magics magics){
		return dataParse.characterParse(magics.getMagicperm(), false);
	}
	private boolean usableByCurrentUser(HttpServletRequest request,Map magicPermMap){
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		String groupId = groupMap.get("groupid");
		String usergroups = (String)magicPermMap.get("usergroups");
		if(usergroups!=null&&!usergroups.trim().equals("")){
			String[] tempArray = usergroups.split("\t");
			for(int i = 0;i<tempArray.length;i++){
				if(tempArray[i].equals(groupId)){
					return true;
				}
			}
		}
		return false;
	}
	private String validateTargetBeing(Map magicPermMap,Integer targePid,Integer targetTid,String targetUsername,HttpServletRequest request){
		if(targePid!=null){
			Posts posts = postsService.getPostsById(targePid);
			if(posts==null){
				return getMessage(request, "magics_target_no_exist");
			}else{
				Short fid = posts.getFid();
				String targetModel = (String)magicPermMap.get("forum");
				if(targetModel!=null&&!targetModel.trim().equals("")){
					String[] targetModelIdArray = targetModel.split("\t");
					for(int i=0;i<targetModelIdArray.length;i++){
						if(targetModelIdArray[i].equals(fid.toString())){
							return null;
						}
					}
				}
				return getMessage(request, "magics_target_forum_invalid");
			}
		}else if(targetTid!=null){
			Threads threads = threadsService.getThreadsById(targetTid);
			if(threads==null){
				return getMessage(request, "magics_target_no_exist");
			}else {
				Short fid = threads.getFid();
				String targetModel = (String)magicPermMap.get("forum");
				if(targetModel!=null&&!targetModel.trim().equals("")){
					String[] targetModelIdArray = targetModel.split("\t");
					for(int i=0;i<targetModelIdArray.length;i++){
						if(targetModelIdArray[i].equals(fid.toString())){
							return null;
						}
					}
				}
				return getMessage(request, "magics_target_forum_invalid");
			}
		}else if(targetUsername!=null){
			Members members = memberService.findByName(targetUsername);
			if(members==null){
				return getMessage(request, "magics_target_no_exist");
			}else{
				Short groupId = members.getGroupid();
				String targetGroup = (String)magicPermMap.get("targetgroups");
				if(targetGroup!=null&&!targetGroup.trim().equals("")){
					String[] targetGroupIdArray = targetGroup.split("\t");
					for(int i = 0;i<targetGroupIdArray.length;i++){
						if(targetGroupIdArray[i].equals(groupId.toString())){
							return null;
						}
					}
				}
				return getMessage(request, "magics_target_group_invalid");
			}
		}else {
			return null;
		}
	}
	private boolean validateMemberMagics(Magics magics,HttpServletRequest request){
		Membermagics memberMagics = memberMagicsService.getMemberMagics((Integer)request.getSession().getAttribute("jsprun_uid"), magics.getMagicid());
		if(memberMagics==null||memberMagics.getNum()<1){
			return false;
		}else{
			return true;
		}
	}
	private String operating_give(Integer magicnum,String targetUsername,Magics magics,HttpServletRequest request){
		short magicid = magics.getMagicid();
		if(magicnum==0){
			return getMessage(request, "magics_num_invalid");
		}
		Members members = (Members)request.getSession().getAttribute("user");
		Integer userid = members.getUid();
		String username = members.getUsername();
		if(username.equals(targetUsername)){
			return getMessage(request, "magics_give_myself");
		}
		Membermagics memberMagics = memberMagicsService.getMemberMagics(userid, magicid);
		if(memberMagics.getNum()<magicnum){
			return getMessage(request, "magics_amount_no_enough");
		}
		memberMagics.setNum((short)(memberMagics.getNum()-magicnum));
		Members targetMembers = memberService.findByName(targetUsername);
		if(targetMembers==null){
			return getMessage(request, "magics_group_no_exist");
		}
		Integer targetUid = targetMembers.getUid();
		Short targetGroupid = targetMembers.getGroupid();
		Usergroups usergroups = userGroupService.findUserGroupById(targetGroupid);
		Short maxmagicsweight = usergroups.getMaxmagicsweight();
		boolean bool = false;
		try{
			bool = memberMagicsService.validateWeight(targetUid, maxmagicsweight, magicid, magicnum.longValue());
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(!bool){
			return getMessage(request, "magics_overweight");
		}
		Membermagics targetMemberMagics = memberMagicsService.getMemberMagics(targetUid, magicid);
		if(targetMemberMagics==null){
			targetMemberMagics = new Membermagics();
			MembermagicsId targetMembermagicsId = new MembermagicsId();
			targetMembermagicsId.setMagicid(magicid);
			targetMembermagicsId.setUid(targetUid);
			targetMemberMagics.setId(targetMembermagicsId);
			targetMemberMagics.setNum(magicnum.shortValue());
		}else{
			targetMemberMagics.setNum((short)(targetMemberMagics.getNum()+magicnum));
		}
		Integer price = magics.getPrice();
		Magiclog magiclog = new Magiclog();
		MagiclogId magiclogId = new MagiclogId();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		magiclogId.setAction((byte)3);
		magiclogId.setAmount(magicnum.shortValue());
		magiclogId.setDateline(timestamp);
		magiclogId.setMagicid(magicid);
		magiclogId.setPrice(price);
		magiclogId.setTargetpid(0);
		magiclogId.setTargettid(0);
		magiclogId.setTargetuid(targetUid);
		magiclogId.setUid(userid);
		magiclog.setId(magiclogId);
		MemberMagic_MagiclogService memberMagic_MagiclogService = (MemberMagic_MagiclogService) BeanFactory
		.getBean("memberMagic_MagiclogService");
		if(memberMagic_MagiclogService.sendMagics(magiclog, memberMagics, targetMemberMagics)){
			return null;
		}else{
			return getMessage(request, "magics_faild");
		}
	}
	private String operating_sell(Integer magicnum,Integer price,Magics magics,HttpServletRequest request){
		if(magicnum==0){
			return getMessage(request, "magics_num_invalid");
		}
		Map<String,String> settingsMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		String maxmagicpriceFromMap = null;
		String creditstaxFromMap = null;
		if(settingsMap!=null){
			maxmagicpriceFromMap = settingsMap.get("maxmagicprice");
			creditstaxFromMap = settingsMap.get("creditstax");
		}else{
			CreditsSetService creSetSer = (CreditsSetService) BeanFactory
			.getBean("creditsSetService");
			maxmagicpriceFromMap = ((Settings)creSetSer.getSetting("maxmagicprice")).getValue();
			creditstaxFromMap = ((Settings)creSetSer.getSetting("creditstax")).getValue();
		}
		maxmagicpriceFromMap = FormDataCheck.getDoubleString(maxmagicpriceFromMap);
		creditstaxFromMap = FormDataCheck.turnToDoubleString(creditstaxFromMap);
		Integer priceFromDB = magics.getPrice();
		Double maxmagicprice = Double.valueOf(maxmagicpriceFromMap);
		if(price>priceFromDB*(1+maxmagicprice/100)){
			return getMessage(request, "magics_price_high");
		}
		Double creditstax = Double.valueOf(creditstaxFromMap);
		double tempDouble = price-price*creditstax;
		if(tempDouble<1){
			return getMessage(request, "magics_price_iszero");
		}
		Members members = (Members)request.getSession().getAttribute("user");
		Integer userid = members.getUid();
		String username = members.getUsername();
		Membermagics memberMagics = memberMagicsService.getMemberMagics(userid, magics.getMagicid());
		if(memberMagics.getNum()<magicnum){
			return getMessage(request, "magics_amount_no_enough");
		}
		memberMagics.setNum((short)(memberMagics.getNum()-magicnum));
		Magiclog magiclog = new Magiclog();
		MagiclogId magiclogId = new MagiclogId();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		magiclogId.setAction((byte)4);
		magiclogId.setAmount(magicnum.shortValue());
		magiclogId.setDateline(timestamp);
		magiclogId.setMagicid(magics.getMagicid());
		magiclogId.setPrice(price);
		magiclogId.setTargetpid(0);
		magiclogId.setTargettid(0);
		magiclogId.setTargetuid(0);
		magiclogId.setUid(userid);
		magiclog.setId(magiclogId);
		Magicmarket magicmarket = new Magicmarket();
		magicmarket.setMagicid(magics.getMagicid());
		magicmarket.setNum(magicnum.shortValue());
		magicmarket.setPrice(price);
		magicmarket.setUid(userid);
		magicmarket.setUsername(username);
		MemberMagic_MagiclogService memberMagic_MagiclogService = (MemberMagic_MagiclogService) BeanFactory
		.getBean("memberMagic_MagiclogService");
		if(memberMagic_MagiclogService.sellMagics(magiclog, memberMagics, magicmarket)){
			return null;
		}else{
			return getMessage(request, "magics_faild");
		}
	}
	private String operating_drop(Integer magicnum,Short magicid,HttpServletRequest request){
		if(magicnum==0){
			return getMessage(request, "magics_num_invalid");
		}
		Integer userid = (Integer)request.getSession().getAttribute("jsprun_uid");
		Membermagics memberMagics = memberMagicsService.getMemberMagics(userid, magicid);
		if(memberMagics.getNum()-magicnum<0){
			return getMessage(request, "magics_amount_no_enough");
		}
		memberMagics.setNum((short)(memberMagics.getNum()-magicnum));
		Magiclog magiclog = new Magiclog();
		MagiclogId magiclogId = new MagiclogId();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		magiclogId.setAction((byte)2);
		magiclogId.setAmount(magicnum.shortValue());
		magiclogId.setDateline(timestamp);
		magiclogId.setMagicid(magicid);
		magiclogId.setPrice(0);
		magiclogId.setTargetpid(0);
		magiclogId.setTargettid(0);
		magiclogId.setTargetuid(0);
		magiclogId.setUid(userid);
		magiclog.setId(magiclogId);
		MemberMagic_MagiclogService memberMagic_MagiclogService = (MemberMagic_MagiclogService) BeanFactory
				.getBean("memberMagic_MagiclogService");
		if(memberMagic_MagiclogService.dropMagicOfOneBoday(magiclog, memberMagics)){
			return null;
		}else{
			return getMessage(request, "magics_faild");
		}
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
	private void setMagic_userVO(Magic_userVO valueObject,String typeid,List<Map<String,String>> resultList,String memberCredits,Integer memberId,Map<String,String> settingsMap,Map<String,String> usergroupMap,Map<Integer,String> extcreditsMap){
		valueObject.setCurrent(typeid==null?"all":typeid);
		valueObject.setSelectSendASell(Byte.parseByte(usergroupMap.get("allowmagics"))==2);
		List<MagicInfo> magicInfoList = valueObject.getMagicInfoList();
		for(Map<String,String> mapTemp : resultList){
			if(mapTemp!=null){
				String magicId = mapTemp.get("magicid");
				String identifier = mapTemp.get("identifier");
				String name = mapTemp.get("name");
				String description = mapTemp.get("description");
				String num = mapTemp.get("num");
				String weight = mapTemp.get("weight");
				if(magicId==null||identifier==null||num==null||weight==null){
				}else{
					MagicInfo magicInfo = valueObject.getMagicInfo();
					magicInfo.setMagicId(Short.parseShort(magicId));
					magicInfo.setImageName(identifier.toLowerCase());
					magicInfo.setMagicName(name);
					magicInfo.setMagicExplaining(description);
					short magicCount = Short.parseShort(num);
					magicInfo.setMagicCount(magicCount);
					magicInfo.setAllMagicWeight(magicCount * Short.parseShort(weight));
					magicInfoList.add(magicInfo);
				}
			}
		}
		setMagic_navbarVO(valueObject, memberCredits, memberId, usergroupMap, settingsMap, extcreditsMap);
	}
	private void setMagic_navbarVO(Magic_navbarVO magic_navbarVO,String memberCredits,Integer memberId,Map<String,String> usergroupMap,Map<String,String> settingsMap,Map<Integer,String> extcreditsMap){
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
