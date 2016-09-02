package cn.jsprun.struts.foreg.actions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.dao.ForumfieldsDao;
import cn.jsprun.domain.Forumfields;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Posts;
import cn.jsprun.domain.Threads;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.foreg.vo.topicadmin.BaseVO;
import cn.jsprun.foreg.vo.topicadmin.OtherBaseVO;
import cn.jsprun.foreg.vo.topicadmin.ToTopAndEliteVO;
import cn.jsprun.foreg.vo.topicadmin.TopicCopyVO;
import cn.jsprun.foreg.vo.topicadmin.TopicPublicVO;
import cn.jsprun.foreg.vo.topicadmin.TopicRefundVO;
import cn.jsprun.foreg.vo.topicadmin.TopicSplitVO;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
public class TopicAdminAction extends BaseAction {
	private final Map<String,Class> operation_VOMap = new HashMap<String, Class>();
	{
		try {	
			Class<BaseVO> baseClass = (Class<BaseVO>)Class.forName("cn.jsprun.foreg.vo.topicadmin.BaseVO");
			Class<ToTopAndEliteVO> toTopAndEliteClass = ToTopAndEliteVO.class;
			operation_VOMap.put("move", Class.forName("cn.jsprun.foreg.vo.topicadmin.TopicAdmin_MoveVO"));
			operation_VOMap.put("close", Class.forName("cn.jsprun.foreg.vo.topicadmin.CloseOrOpenTopicVO"));
			operation_VOMap.put("delete", baseClass);
			operation_VOMap.put("bump", baseClass);
			operation_VOMap.put("highlight",Class.forName("cn.jsprun.foreg.vo.topicadmin.HighLightVO") );
			operation_VOMap.put("stick",toTopAndEliteClass );
			operation_VOMap.put("digest",toTopAndEliteClass );
			operation_VOMap.put("type",Class.forName("cn.jsprun.foreg.vo.topicadmin.TopicClassVO") );
			operation_VOMap.put("delpost",baseClass );
			operation_VOMap.put("recommend",baseClass );
			operation_VOMap.put("deleteMirrorImage", baseClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public ActionForward moderate(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String[] moderates = request.getParameterValues("moderates");
		String fid = request.getParameter("fid");
		if(fid == null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Members members = (Members)session.getAttribute("user");
		if(members==null){
			request.setAttribute("errorInfo", getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}
		Byte adminId = members.getAdminid();	
		if(adminId == (byte)0){
			request.setAttribute("errorInfo", getMessage(request, "purview_error"));
			return mapping.findForward("showMessage");
		}
		if(!Common.ismoderator(Short.valueOf(fid), members)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String pageInfo = request.getParameter("pageInfo");
		if(pageInfo==null){
			pageInfo = "forumdisplay.jsp?fid="+fid;
			String extra = request.getParameter("extra");
			if(extra != null && !extra.trim().equals("")){
				pageInfo += "&"+extra;
			}
		}
		if(moderates==null){
			moderates = (String[])session.getAttribute("moderate_moderate");
			session.removeAttribute("moderate_moderate");
		}
		if(moderates==null||moderates.length==0){
			request.setAttribute("errorInfo", getMessage(request, "admin_moderate_invalid"));
			return mapping.findForward("showMessage");
		}else{
			if(!topicAdminActionService.checkTid(Short.valueOf(fid),moderates)){
				request.setAttribute("errorInfo", getMessage(request, "admin_moderate_invalid2"));
				return mapping.findForward("showMessage");
			}
		}
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		String operation = request.getParameter("operation");
		if(operation == null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Map<String,Object> transfersMap = new HashMap<String, Object>();
		transfersMap.put("dateformat", session.getAttribute("dateformat"));
		transfersMap.put("timeformat", session.getAttribute("timeformat"));
		transfersMap.put("pageInfo", pageInfo);
		transfersMap.put("moderates", moderates);
		transfersMap.put("operation", operation);
		transfersMap.put("fid", fid);
		fid = null;
		transfersMap.put("modreasons", settingMap.get("modreasons"));
		transfersMap.put("timeoffset", session.getAttribute("timeoffset"));
		settingMap = null;
		transfersMap.put("reasonpm", ((Map<String,String>)request.getAttribute("usergroups")).get("reasonpm"));
		transfersMap.put("fromWhere", request.getParameter("fromWhere"));
		if(operation.equals("stick")){
			transfersMap.put("allowstickthread", ((Map<String,String>)request.getAttribute("usergroups")).get("allowstickthread"));
		}
		if(operation.equals("move")){
			short groupid = (Short)session.getAttribute("jsprun_groupid");
			transfersMap.put("groupid", groupid);
			transfersMap.put("member", members);
		}
		if(operation.equals("highlight")){
			request.setAttribute("colorArray", Common.THREAD_COLORS);
		}
		session  =null;
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		transfersMap.put("messageResources", resources);
		transfersMap.put("locale", locale);
		Map<String,String> operationMap = getOperationMap(request);
		BaseVO baseVO = topicAdminActionService.getFinalBaseVO(transfersMap,operationMap);
		if(baseVO==null){
			request.setAttribute("errorInfo", getMessage(request, "thread_unexist"));
			return mapping.findForward("showMessage");
		}
		transfersMap = null;
		request.setAttribute("valueObject", operation_VOMap.get(operation).cast(baseVO));
		baseVO = null;
		operation = null;
		return mapping.findForward("goTopicadmin_moderate");
	}
	public ActionForward operating(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(!submitCheck(request, "modsubmit")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		HttpSession session = request.getSession();
		String fid = request.getParameter("fid"); 
		if(fid == null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Members members = (Members)session.getAttribute("user");
		if(members==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Byte adminId = members.getAdminid();	
		Short groupId = members.getGroupid();	
		Integer uid = members.getUid();	
		String username = members.getUsername(); 
		if(adminId == (byte)0){
			request.setAttribute("errorInfo", getMessage(request, "purview_error"));
			return mapping.findForward("showMessage");
		}
		if(!Common.ismoderator(Short.valueOf(fid), members)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String pageInfo = request.getParameter("pageInfo");
		String reason = request.getParameter("reason"); 
		String isNecessary = request.getParameter("isNecessary");  
		if(isNecessary!=null&&(reason==null||reason.trim().equals(""))){
			request.setAttribute("errorInfo", getMessage(request, "admin_reason_unexist"));
			return mapping.findForward("showMessage");
		}
		String[] moderate_ = request.getParameterValues("moderate_"); 
		if(!topicAdminActionService.checkTid(Short.valueOf(fid),moderate_)){
			request.setAttribute("errorInfo", getMessage(request, "admin_moderate_invalid2"));
			return mapping.findForward("showMessage");
		}
		String next = request.getParameter("next");
		String sendreasonpm = request.getParameter("sendreasonpm"); 
		String operation = request.getParameter("currentOperation"); 
		if(operation == null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		boolean afterOperation = next!=null&&!next.equals("");
		Map<String,String> groupInfo = (Map<String,String>)request.getAttribute("usergroups");
		String grouptitle = groupInfo.get("grouptitle"); 
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		String IP = Common.get_onlineip(request);
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("fid", fid); 
		parameterMap.put("sendreasonpm", sendreasonpm); 
		parameterMap.put("reason", reason);	
		parameterMap.put("adminId", adminId);	
		parameterMap.put("uid", uid);	
		parameterMap.put("username", username); 
		parameterMap.put("groupId", groupId);	
		parameterMap.put("grouptitle", grouptitle);	
		parameterMap.put("moderate_", moderate_);	
		parameterMap.put("url", url);	
		parameterMap.put("IP", IP);	
		parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
		Map<String,String> settingMap = ForumInit.settings;
		if(pageInfo.indexOf("forumdisplay")==-1){
			pageInfo = pageInfo.replaceAll("&page=", "-")+".html";
		}
		parameterMap.put("settingMap", settingMap);
		if(operation.equals("digest")||operation.equals("delete") || operation.equals("delpost")){ 
			String expiration = request.getParameter("expiration"); 
			if((operation.equals("digest")||operation.equals("delete")) && expiration == null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			if(!operation.equals("digest")){
				Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
				String allowdelpost = usergroups.get("allowdelpost");
				if(allowdelpost==null||allowdelpost.equals("0")){
					request.setAttribute("errorInfo", getMessage(request, "purview_error"));
					return mapping.findForward("showMessage");
				}
				String attachdir = settingMap.get("attachdir");	
				String losslessdel = settingMap.get("losslessdel"); 
				String attachdir_realy = JspRunConfig.realPath+attachdir;
				parameterMap.put("attachdir_realy", attachdir_realy);
				parameterMap.put("losslessdel", losslessdel);
			}else{
				String level = request.getParameter("level");
				if(level==null||level.equals("")){
					request.setAttribute("errorInfo", getMessage(request, "admin_leave_unchoose"));
					return mapping.findForward("showMessage");
				}
				if("0".equals(level)){
					expiration = "";
				}
				parameterMap.put("level", level);
				parameterMap.put("next", next); 
			}
			parameterMap.put("expiration", expiration);
			parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
			String creditsformula = settingMap.get("creditsformula");
			String creditspolicy = settingMap.get("creditspolicy");
			parameterMap.put("creditsformula", creditsformula);
			parameterMap.put("creditspolicy", creditspolicy);
		}else if(operation.equals("move")){
			String allowdirectpost  = groupInfo.get("allowdirectpost");
			String expiration = request.getParameter("expiration"); 
			if(expiration == null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			parameterMap.put("expiration", expiration);
			parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
			parameterMap.put("extgroupids", members.getExtgroupids());
			parameterMap.put("accessmasks", members.getAccessmasks()); 
			parameterMap.put("allowdirectpost", allowdirectpost); 
			String movetoTemp = request.getParameter("moveto");
			if(movetoTemp == null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			parameterMap.put("moveto", movetoTemp); 
			parameterMap.put("type", request.getParameter("type")); 
		}else if(operation.equals("highlight")){
			String[] highlight_style = request.getParameterValues("highlight_style"); 
			String highlight_color = request.getParameter("highlight_color"); 
			if(highlight_color == null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			String expiration = request.getParameter("expiration"); 
			if(expiration == null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
			parameterMap.put("highlight_style", highlight_style);
			parameterMap.put("highlight_color", highlight_color);
			parameterMap.put("expiration", expiration);
			parameterMap.put("next", next); 
		}else if(operation.equals("close")){
			String close = request.getParameter("close");
			if(close==null||close.equals("")){
				request.setAttribute("errorInfo", getMessage(request, "admin_unchoose"));
				return mapping.findForward("showMessage");
			}
			String expiration = request.getParameter("expiration"); 
			if(expiration == null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
			parameterMap.put("next", next); 
			parameterMap.put("expiration", expiration);
			parameterMap.put("close", close);
		}else if(operation.equals("bump")){
			String isbump = request.getParameter("isbump");
			if(isbump == null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			parameterMap.put("isbump", isbump);
		}else if(operation.equals("stick")){
			String level = request.getParameter("level");
			if(level==null||level.equals("")){
				request.setAttribute("errorInfo", getMessage(request, "stick_level_unchoose"));
				return mapping.findForward("showMessage");
			}
			boolean allowStick = allowStick(request, level);
			if(!allowStick){
				request.setAttribute("errorInfo", getMessage(request, "purview_error"));
				return mapping.findForward("showMessage");
			}
			String expiration = request.getParameter("expiration"); 
			expiration = level.equals("0") ? "" : expiration;
			if(expiration == null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			parameterMap.put("level", level);
			parameterMap.put("expiration", expiration);
			parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
			parameterMap.put("next", next); 
		}else if(operation.equals("type")){
			Forumfields forumfields = ((ForumfieldsDao)BeanFactory.getBean("forumfieldsDao")).findById(Short.valueOf(fid));
			if(forumfields.getThreadtypes() == null || forumfields.getThreadtypes().equals("")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			String typeId = request.getParameter("typeid");
			if(typeId == null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			parameterMap.put("typeId", typeId);
		}else if(operation.equals("recommend")){
			String isRecommend = request.getParameter("isrecommend");
			String recommendExpire = request.getParameter("recommendexpire");
			if(isRecommend == null || recommendExpire==null){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
			parameterMap.put("isRecommend", isRecommend);
			parameterMap.put("recommendExpire", recommendExpire);
		}
		Map<String,String> operationMap = getOperationMap(request);
		if(operation.equals("delpost")){
			operation = "delete";
		}
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String result = topicAdminActionService.operating(operation, parameterMap,operationMap,resources,locale);
		if(result!=null){
			request.setAttribute("errorInfo", result);
			return mapping.findForward("showMessage");
		}else{
			if(afterOperation){
				request.getSession().setAttribute("moderate_moderate",moderate_);
				if(Common.isshowsuccess(request.getSession(), "admin_succeed_next")){
					Common.requestforward(response, "topicadmin.jsp?operation="+next+"&fid="+fid);
					return null;
				}else{
					request.setAttribute("successInfo", getMessage(request, "admin_succeed_next"));
					request.setAttribute("requestPath", request.getContextPath()+"/topicadmin.jsp?operation="+next+"&fid="+fid);
					return mapping.findForward("showMessage");
				}
			}
		}
		if(Common.isshowsuccess(request.getSession(), "admin_succeed")){
			Common.requestforward(response, pageInfo);
			return null;
		}else{
			request.setAttribute("successInfo", getMessage(request, "admin_succeed"));
			request.setAttribute("requestPath", pageInfo);
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward repairOperating(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try{
			if(!submitCheck(request, "fid")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		HttpSession session = request.getSession();
		Members member = (Members)session.getAttribute("user");
		String fid = request.getParameter("fid");
		if(!Common.ismoderator(Short.valueOf(fid), member)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String moderates = request.getParameter("moderates"); 
		if(moderates==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Threads currentThread = threadsService.findByTid(Integer.valueOf(moderates));
		if(currentThread==null||currentThread.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		topicAdminActionService.repairTopic(Integer.valueOf(moderates));
		if(Common.isshowsuccess(session, "admin_succeed")){
			Common.requestforward(response, "viewthread.jsp?tid="+moderates);
			return null;
		}else{
			request.setAttribute("successInfo", getMessage(request, "admin_succeed"));
			request.setAttribute("requestPath", "viewthread.jsp?tid="+moderates);
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward removerewardOperating(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try{
			if(!submitCheck(request, "fid")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		HttpSession session = request.getSession();
		Members member = (Members)session.getAttribute("user");
		String fid = request.getParameter("fid");
		if(!Common.ismoderator(Short.valueOf(fid), member)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String moderates = request.getParameter("moderates"); 
		if(moderates==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Threads currentThread = threadsService.findByTid(Integer.valueOf(moderates));
		if(currentThread==null||currentThread.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		String result = topicAdminActionService.removereward(currentThread,(Map<String,String>)servlet.getServletContext().getAttribute("settings"),(Integer)session.getAttribute("jsprun_uid"));
		if(result==null){
			if(Common.isshowsuccess(session, "admin_succeed")){
				Common.requestforward(response, "viewthread.jsp?tid="+moderates);
				return null;
			}else{
				request.setAttribute("successInfo", getMessage(request, "admin_succeed"));
				request.setAttribute("requestPath", "viewthread.jsp?tid="+moderates);
				return mapping.findForward("showMessage");
			}
		}else{
			request.setAttribute("errorInfo", getMessage(request, result));
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward merge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String fid = request.getParameter("fid");
		HttpSession session = request.getSession();
		Members member = (Members)session.getAttribute("user");
		if(!Common.ismoderator(Short.valueOf(fid), member)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String moderates = request.getParameter("moderates"); 
		if(moderates==null||fid==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Threads currentThread = threadsService.findByTid(Integer.valueOf(moderates));
		if(currentThread==null||currentThread.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		String reasonpm = ((Map<String,String>)request.getAttribute("usergroups")).get("reasonpm");
		Forums currentforum = forumService.findById(Short.valueOf(fid)); 
		if(currentforum == null){
			request.setAttribute("errorInfo", getMessage(request, "forum_nonexistence_2"));
			return mapping.findForward("showMessage");
		}
		String modreasons = settingService.findBySettingvariable("modreasons").getValue();
		Map<String,Object> transfersMap = new HashMap<String, Object>();
		transfersMap.put("currentforum", currentforum);
		transfersMap.put("currentThread", currentThread);
		transfersMap.put("operation", "merge");
		transfersMap.put("reasonpm", reasonpm);
		transfersMap.put("modreasons", modreasons);
		TopicPublicVO topicPublicVO = topicAdminActionService.geTopicMergeVO(transfersMap);
		request.setAttribute("valueObject", topicPublicVO);
		return mapping.findForward("goTopicadmin_merge");
	}
	public ActionForward mergeOperating(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try{
			if(!submitCheck(request, "mergesubmit")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		String fidString = request.getParameter("fid"); 
		HttpSession session = request.getSession();
		Members member = (Members)session.getAttribute("user");
		if(!Common.ismoderator(Short.valueOf(fidString), member)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String tidString = request.getParameter("tid");	
		String targetTidString = request.getParameter("othertid");	
		int targetTid = 0;
		int fid = 0;
		int tid = 0;
		String sendreasonpm = request.getParameter("sendreasonpm");
		String reason = request.getParameter("reason");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(checkReasonpm(reason, usergroupMap.get("reasonpm"))){
			request.setAttribute("errorInfo", getMessage(request, "admin_reason_invalid"));
			return mapping.findForward("showMessage");
		}
		Members currentMember = (Members)request.getSession().getAttribute("user");
		if(fidString==null||tidString==null||targetTidString==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		try{
			targetTid = Integer.parseInt(targetTidString);
			fid = Integer.parseInt(fidString);
			tid = Integer.parseInt(tidString);
		}catch(Exception exception){
			request.setAttribute("errorInfo", getMessage(request, "admin_merge_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(targetTid == tid){
			request.setAttribute("errorInfo", getMessage(request, "admin_merge_one_thread"));
			return mapping.findForward("showMessage");
		}
		Threads operatingThread = threadsService.findByTid(Integer.valueOf(tid));
		if(operatingThread==null||operatingThread.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		Threads targetThread = threadsService.findByTid(Integer.valueOf(targetTid));
		if(targetThread==null||targetThread.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "target_thread_unexist"));
			return mapping.findForward("showMessage");
		}else if(targetThread.getSpecial()!=0){
			request.setAttribute("errorInfo", getMessage(request, "special_thread_invalid"));
			return mapping.findForward("showMessage");
		}else if(operatingThread.getTid().intValue()==targetThread.getTid().intValue()||
				(currentMember.getAdminid()==3&&
						operatingThread.getFid().shortValue()!=targetThread.getFid().shortValue())){
			request.setAttribute("errorInfo", getMessage(request, "admin_merge_invalid"));
			return mapping.findForward("showMessage");
		}
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		String IP = Common.get_onlineip(request);
		Map<String , Object> transfersMap = new HashMap<String, Object>();
		transfersMap.put("fid", fid);
		transfersMap.put("operatingThread", operatingThread);
		transfersMap.put("targetThread", targetThread);
		transfersMap.put("currentMember", currentMember);
		transfersMap.put("timeoffset", session.getAttribute("timeoffset"));
		transfersMap.put("sendreasonpm", sendreasonpm); 
		transfersMap.put("reason", reason);	
		transfersMap.put("IP", IP);	
		transfersMap.put("url", url);	
		transfersMap.put("settingMap", (Map<String,String>)servlet.getServletContext().getAttribute("settings"));
		Map<String,String> operationMap = getOperationMap(request);
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String result = topicAdminActionService.operatingMerge(transfersMap,operationMap,resources,locale);
		if(result!=null){
			request.setAttribute("errorInfo", result);
			return mapping.findForward("showMessage");
		}
		if(Common.isshowsuccess(request.getSession(), "admin_succeed")){
			Common.requestforward(response, "forumdisplay.jsp?fid="+fid);
			return null;
		}else{
			request.setAttribute("successInfo", getMessage(request, "admin_succeed"));
			request.setAttribute("requestPath", "forumdisplay.jsp?fid="+fid);
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward split(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String fid = request.getParameter("fid");
		HttpSession session = request.getSession();
		Members member = (Members)session.getAttribute("user");
		if(!Common.ismoderator(Short.valueOf(fid), member)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String moderates = request.getParameter("moderates"); 
		if(moderates==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Threads currentThread = threadsService.findByTid(Integer.valueOf(moderates));
		if(currentThread==null||currentThread.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		List<Posts> postsList_validateBeing = postsService.getPostsListByTid(Integer.valueOf(moderates));
		if(postsList_validateBeing==null||postsList_validateBeing.size()<2){
			request.setAttribute("errorInfo", getMessage(request, "admin_split_invalid"));
			return mapping.findForward("showMessage");
		}
		String reasonpm = ((Map<String,String>)request.getAttribute("usergroups")).get("reasonpm");
		Forums currentforum = forumService.findById(Short.valueOf(fid)); 
		if(currentforum == null){
			request.setAttribute("errorInfo", getMessage(request, "forum_nonexistence_2"));
			return mapping.findForward("showMessage");
		}
		String modreasons = settingService.findBySettingvariable("modreasons").getValue();
		Map transfersMap = new HashMap();
		transfersMap.put("currentThread", currentThread);
		transfersMap.put("currentforum", currentforum);
		transfersMap.put("postsList", postsList_validateBeing);
		transfersMap.put("operation", "split");
		transfersMap.put("reasonpm", reasonpm);
		transfersMap.put("modreasons", modreasons);
		transfersMap.put("timeoffset", session.getAttribute("timeoffset"));
		TopicSplitVO topicSplitVO = topicAdminActionService.geTopicSplitVO(transfersMap);
		request.setAttribute("valueObject", topicSplitVO);
		return mapping.findForward("goTopicadmin_split");
	}
	public ActionForward splitOperating(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try{
			if(!submitCheck(request, "splitsubmit")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		String fid = request.getParameter("fid");
		HttpSession session = request.getSession();
		Members member = (Members)session.getAttribute("user");
		if(!Common.ismoderator(Short.valueOf(fid), member)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String tid = request.getParameter("tid");
		String[] pidArray = request.getParameterValues("split_pid"); 
		String subject = request.getParameter("subject"); 
		String sendreasonpm = request.getParameter("sendreasonpm");
		String reason = request.getParameter("reason");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(checkReasonpm(reason, usergroupMap.get("reasonpm"))){
			request.setAttribute("errorInfo", getMessage(request, "admin_reason_invalid"));
			return mapping.findForward("showMessage");
		}
		if(tid==null||fid==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		if(subject==null||subject.trim().equals("")){
			request.setAttribute("errorInfo", getMessage(request, "thread_name_invalid"));
			return mapping.findForward("showMessage");
		}
		Threads passiveThread = threadsService.findByTid(Integer.valueOf(tid));
		if(passiveThread==null||passiveThread.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		if(pidArray==null||pidArray.length==0){
			request.setAttribute("errorInfo", getMessage(request, "admin_split_new_invalid"));
			return mapping.findForward("showMessage");
		}
		List<Integer> pidList = new ArrayList<Integer>();
		for(int i = 0;i<pidArray.length;i++){
			pidList.add(Integer.valueOf(pidArray[i]));
		}
		List<Posts> postsList = postsService.getPostsListByPidList(pidList);
		for(int i = 0;i<postsList.size();i++){
			if(postsList.get(i)==null){
				request.setAttribute("errorInfo", getMessage(request, "admin_split_post_invalid"));
				return mapping.findForward("showMessage");
			}
		}
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		String IP = Common.get_onlineip(request);
		Members currentMember = (Members)request.getSession().getAttribute("user");
		Map<String,Object> transfersMap = new HashMap<String, Object>();
		transfersMap.put("fid", fid);
		transfersMap.put("passiveThread",passiveThread );
		transfersMap.put("postsList", postsList);
		transfersMap.put("subject", subject);
		transfersMap.put("timeoffset", session.getAttribute("timeoffset"));
		transfersMap.put("sendreasonpm", sendreasonpm); 
		transfersMap.put("reason", reason);	
		transfersMap.put("IP", IP);	
		transfersMap.put("url", url);	
		transfersMap.put("currentMember", currentMember);
		transfersMap.put("settingMap", (Map<String,String>)servlet.getServletContext().getAttribute("settings"));
		Map<String,String> operationMap = getOperationMap(request);
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String result = topicAdminActionService.operatingSplit(transfersMap,operationMap,resources,locale);
		if(result!=null){
			request.setAttribute("errorInfo", result);
			return mapping.findForward("showMessage");
		}
		if(Common.isshowsuccess(request.getSession(), "admin_succeed")){
			Common.requestforward(response, "forumdisplay.jsp?fid="+fid);
			return null;
		}else{
			request.setAttribute("successInfo", getMessage(request, "admin_succeed"));
			request.setAttribute("requestPath", "forumdisplay.jsp?fid="+fid);
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward deletePost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		boolean allowDeletePosts = allowDeletePosts(request);
		if(!allowDeletePosts){
			request.setAttribute("errorInfo", getMessage(request, "purview_error"));
			return mapping.findForward("showMessage");
		}
		String fid = request.getParameter("fid");
		String currentPage = request.getParameter("page");
		String threadPage = request.getParameter("threadpage"); 
		String[] topiclist = request.getParameterValues("topiclist[]");
		String moderates = request.getParameter("moderates"); 
		if(moderates==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		if(topiclist==null){
			request.setAttribute("errorInfo", getMessage(request, "no_provide_replypost"));
			return mapping.findForward("showMessage");
		}
		Threads threads = threadsService.findByTid(Integer.valueOf(moderates));
		if(threads==null||threads.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		List<Integer> pidList = new ArrayList<Integer>();
		for(int i = 0;i<topiclist.length;i++){
			pidList.add(Integer.valueOf(topiclist[i]));
		}
		List<Posts> postsList = postsService.getPostsListByPidList(pidList);
		for(int i = 0;i<postsList.size();i++){
			if(postsList.get(i)==null){
				request.setAttribute("errorInfo", getMessage(request, "post_part_deleted"));
				return mapping.findForward("showMessage");
			}
		}
		String pid = postsService.findPostByThreadId(Integer.valueOf(moderates)).getPid().toString();
		for(int i = 0;i<topiclist.length;i++){
			if(topiclist[i].equals(pid)){
				return moderate(mapping, form, request, response);
			}
		}
		String reasonpm = ((Map<String,String>)request.getAttribute("usergroups")).get("reasonpm");
		Forums currentForums = forumService.findById(Short.valueOf(fid)); 
		if(currentForums == null){
			request.setAttribute("errorInfo", getMessage(request, "forum_nonexistence_2"));
			return mapping.findForward("showMessage");
		}
		String modreasons = settingService.findBySettingvariable("modreasons").getValue();
		Map transfersMap = new HashMap();
		transfersMap.put("currentPage", currentPage);
		transfersMap.put("threadPage", threadPage);
		transfersMap.put("currentThread", threads);
		transfersMap.put("currentForum", currentForums);
		transfersMap.put("pidArray", topiclist);
		transfersMap.put("operation", "delpost");
		transfersMap.put("reasonpm", reasonpm);
		transfersMap.put("modreasons", modreasons);
		transfersMap.put("timeoffset", request.getSession().getAttribute("timeoffset"));
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		OtherBaseVO otherBaseVO = topicAdminActionService.getValueObject(transfersMap,resources,locale);
		request.setAttribute("valueObject", otherBaseVO);
		return mapping.findForward("goTopicadmin_delpost");
	}
	public ActionForward deletePostOperating(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try{
			if(!submitCheck(request, "delpsubmit")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		boolean allowDeletePosts = allowDeletePosts(request);
		if(!allowDeletePosts){
			request.setAttribute("errorInfo", getMessage(request, "purview_error"));
			return mapping.findForward("showMessage");
		}
		String tid = request.getParameter("tid");
		String currentPage = request.getParameter("currentPage"); 
		String threadPage = request.getParameter("threadPage"); 
		threadPage = Common.encode("page="+threadPage);
		String[] postIdArray = request.getParameterValues("postIdArray"); 
		String sendreasonpm = request.getParameter("sendreasonpm");
		String reason = request.getParameter("reason");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(checkReasonpm(reason, usergroupMap.get("reasonpm"))){
			request.setAttribute("errorInfo", getMessage(request, "admin_reason_invalid"));
			return mapping.findForward("showMessage");
		}
		if(tid==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		if(postIdArray==null){
			request.setAttribute("errorInfo", getMessage(request, "no_provide_replypost"));
			return mapping.findForward("showMessage");
		}
		Threads threads = threadsService.findByTid(Integer.valueOf(tid));
		if(threads==null||threads.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		List<Integer> pidList = new ArrayList<Integer>();
		for(int i = 0;i<postIdArray.length;i++){
			pidList.add(Integer.valueOf(postIdArray[i]));
		}
		List<Posts> postsList = postsService.getPostsListByPidList(pidList);
		for(int i = 0;i<postsList.size();i++){
			if(postsList.get(i)==null){
				request.setAttribute("errorInfo", getMessage(request, "post_part_deleted"));
				return mapping.findForward("showMessage");
			}
		}
		String pid = postsService.findPostByThreadId(Integer.valueOf(tid)).getPid().toString();
		for(int i = 0;i<postIdArray.length;i++){
			if(postIdArray[i].equals(pid)){
				return moderate(mapping, form, request, response);
			}
		}
		HttpSession session = request.getSession();
		Members members = (Members)session.getAttribute("user");
		if(members==null){
			request.setAttribute("errorInfo", getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}	
		ServletContext context = servlet.getServletContext();
		String IP = Common.get_onlineip(request);
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		Map<String,String> settingMap = ((Map<String,String>)context.getAttribute("settings"));
		String losslessdel = settingMap.get("losslessdel");
		String creditsformula = settingMap.get("creditsformula");
		String creditspolicy = settingMap.get("creditspolicy");
		String attachdir = settingMap.get("attachdir");	
		String attachdir_realy = JspRunConfig.realPath + attachdir;
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("losslessdel", losslessdel);
		parameterMap.put("threads", threads); 
		parameterMap.put("postIdArray", postIdArray);	
		parameterMap.put("members", members);	
		parameterMap.put("fid", threads.getFid()); 
		parameterMap.put("sendreasonpm", sendreasonpm); 
		parameterMap.put("reason", reason);	
		parameterMap.put("adminId", members.getAdminid());	
		parameterMap.put("uid", members.getUid());	
		parameterMap.put("username", members.getUsername()); 
		parameterMap.put("groupId", members.getGroupid());	
		parameterMap.put("url", url);	
		parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
		parameterMap.put("IP", IP);	
		parameterMap.put("creditsformula", creditsformula);
		parameterMap.put("creditspolicy", creditspolicy);
		parameterMap.put("attachdir_realy", attachdir_realy);
		parameterMap.put("settingMap", (Map<String,String>)context.getAttribute("settings"));
		Map<String,String> operationMap = getOperationMap(request);
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String result = topicAdminActionService.operatingDelPost(parameterMap,operationMap,resources,locale);
		if(result!=null){
			request.setAttribute("errorInfo", result);
			return mapping.findForward("showMessage");
		}
		if(Common.isshowsuccess(request.getSession(), "admin_succeed")){
			Common.requestforward(response, "viewthread.jsp?tid="+tid+"&page="+currentPage+"&extra="+threadPage);
			return null;
		}else{
			request.setAttribute("successInfo", getMessage(request, "admin_succeed"));
			request.setAttribute("requestPath", "viewthread.jsp?tid="+tid+"&page="+currentPage+"&extra="+threadPage);
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward banPost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		boolean allowBanpost = allowBanpost(request);
		if(!allowBanpost){
			request.setAttribute("errorInfo", getMessage(request, "purview_error"));
			return mapping.findForward("showMessage");
		}
		String fid = request.getParameter("fid");
		String currentPage = request.getParameter("page");
		String threadPage = request.getParameter("threadpage"); 
		String[] topiclist = request.getParameterValues("topiclist[]");
		String moderates = request.getParameter("moderates"); 
		if(moderates==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		if(topiclist==null){
			request.setAttribute("errorInfo", getMessage(request, "no_provide_replypost"));
			return mapping.findForward("showMessage");
		}
		Threads threads = threadsService.findByTid(Integer.valueOf(moderates));
		if(threads==null||threads.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		List<Integer> pidList = new ArrayList<Integer>();
		for(int i = 0;i<topiclist.length;i++){
			pidList.add(Integer.valueOf(topiclist[i]));
		}
		List<Posts> postsList = postsService.getPostsListByPidList(pidList);
		for(int i = 0;i<postsList.size();i++){
			if(postsList.get(i)==null){
				request.setAttribute("errorInfo", getMessage(request, "post_part_deleted"));
				return mapping.findForward("showMessage");
			}
		}
		String reasonpm = ((Map<String,String>)request.getAttribute("usergroups")).get("reasonpm");
		Forums currentForum = forumService.findById(Short.valueOf(fid)); 
		if(currentForum == null){
			request.setAttribute("errorInfo", getMessage(request, "forum_nonexistence_2"));
			return mapping.findForward("showMessage");
		}
		String modreasons = settingService.findBySettingvariable("modreasons").getValue();
		Map transfersMap = new HashMap();
		transfersMap.put("currentThread", threads);
		transfersMap.put("currentForum", currentForum);
		transfersMap.put("currentPage", currentPage);
		transfersMap.put("threadPage", threadPage);
		transfersMap.put("pidArray", topiclist);
		transfersMap.put("operation", "banpost");
		transfersMap.put("reasonpm", reasonpm);
		transfersMap.put("modreasons", modreasons);
		transfersMap.put("timeoffset", request.getSession().getAttribute("timeoffset"));
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		OtherBaseVO otherBaseVO = topicAdminActionService.getValueObject(transfersMap,resources,locale);
		request.setAttribute("valueObject", otherBaseVO);
		return mapping.findForward("goTopicadmin_banpost");
	}
	public ActionForward banPostOperating(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try{
			if(!submitCheck(request, "banpostsubmit")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		boolean allowBanpost = allowBanpost(request);
		if(!allowBanpost){
			request.setAttribute("errorInfo", getMessage(request, "purview_error"));
			return mapping.findForward("showMessage");
		}
		String tid = request.getParameter("tid");
		String currentPage = request.getParameter("currentPage"); 
		String threadPage = request.getParameter("threadPage"); 
		threadPage = Common.encode("page="+threadPage);
		String[] postIdArray = request.getParameterValues("postIdArray"); 
		String banned = request.getParameter("banned");
		String sendreasonpm = request.getParameter("sendreasonpm");
		String reason = request.getParameter("reason");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(checkReasonpm(reason, usergroupMap.get("reasonpm"))){
			request.setAttribute("errorInfo", getMessage(request, "admin_reason_invalid"));
			return mapping.findForward("showMessage");
		}
		if(tid==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		if(postIdArray==null){
			request.setAttribute("errorInfo", getMessage(request, "no_provide_replypost"));
			return mapping.findForward("showMessage");
		}
		Threads threads = threadsService.findByTid(Integer.valueOf(tid));
		if(threads==null||threads.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		List<Integer> pidList = new ArrayList<Integer>();
		for(int i = 0;i<postIdArray.length;i++){
			pidList.add(Integer.valueOf(postIdArray[i]));
		}
		List<Posts> postsList = postsService.getPostsListByPidList(pidList);
		for(int i = 0;i<postsList.size();i++){
			if(postsList.get(i)==null){
				request.setAttribute("errorInfo", getMessage(request, "post_part_deleted"));
				return mapping.findForward("showMessage");
			}
		}
		HttpSession session = request.getSession();
		Members members = (Members)session.getAttribute("user");
		if(members==null){
			request.setAttribute("errorInfo", getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}	
		String IP = Common.get_onlineip(request);
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("threads", threads);
		parameterMap.put("postsList", postsList);
		parameterMap.put("banned", banned);
		parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
		parameterMap.put("fid", threads.getFid()); 
		parameterMap.put("sendreasonpm", sendreasonpm); 
		parameterMap.put("reason", reason);	
		parameterMap.put("adminId", members.getAdminid());	
		parameterMap.put("uid", members.getUid());	
		parameterMap.put("username", members.getUsername()); 
		parameterMap.put("groupId", members.getGroupid());	
		parameterMap.put("url", url);	
		parameterMap.put("IP", IP);	
		Map<String,String> operationMap = getOperationMap(request);
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String result = topicAdminActionService.operatingBanPost(parameterMap,operationMap,resources,locale);
		if(result!=null){
			request.setAttribute("errorInfo", result);
			return mapping.findForward("showMessage");
		}
		if(Common.isshowsuccess(request.getSession(), "admin_succeed")){
			Common.requestforward(response, "viewthread.jsp?tid="+tid+"&page="+currentPage+"&extra="+threadPage);
			return null;
		}else{
			request.setAttribute("successInfo", getMessage(request, "admin_succeed"));
			request.setAttribute("requestPath", "viewthread.jsp?tid="+tid+"&page="+currentPage+"&extra="+threadPage);
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward copy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String fid = request.getParameter("fid");
		HttpSession session = request.getSession();
		Members member = (Members)session.getAttribute("user");
		if(!Common.ismoderator(Short.valueOf(fid), member)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String moderates = request.getParameter("moderates"); 
		if(fid==null||moderates==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Threads threads = threadsService.findByTid(Integer.valueOf(moderates));
		if(threads==null||threads.getDisplayorder()<0){
			request.setAttribute("errorInfo", getMessage(request, "thread_deleted"));
			return mapping.findForward("showMessage");
		}
		String reasonpm = ((Map<String,String>)request.getAttribute("usergroups")).get("reasonpm");
		String modreasons = settingService.findBySettingvariable("modreasons").getValue();
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		parameterMap.put("groupid", groupid);
		parameterMap.put("member", member);
		parameterMap.put("fid", fid);
		parameterMap.put("tid", moderates);
		parameterMap.put("operation", "copy");
		parameterMap.put("reasonpm", reasonpm);
		parameterMap.put("modreasons", modreasons);
		parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
		TopicCopyVO topicCopyVO = topicAdminActionService.geTopicCopyVO(parameterMap);
		request.setAttribute("valueObject", topicCopyVO);
		return mapping.findForward("goTopicadmin_copy");
	}
	public ActionForward operatingCopy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try{
			if(!submitCheck(request, "copysubmit")){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		String fid = request.getParameter("fid");
		HttpSession session = request.getSession();
		Members members = (Members)session.getAttribute("user");
		if(!Common.ismoderator(Short.valueOf(fid), members)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		String tid = request.getParameter("tid"); 
		String copyTo = request.getParameter("copyto"); 
		String sendreasonpm = request.getParameter("sendreasonpm");
		String reason = request.getParameter("reason");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(checkReasonpm(reason, usergroupMap.get("reasonpm"))){
			request.setAttribute("errorInfo", getMessage(request, "admin_reason_invalid"));
			return mapping.findForward("showMessage");
		}
		if(fid==null||tid==null||copyTo==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		Threads threads = threadsService.findByTid(Integer.valueOf(tid));
		Forums targetForums = forumService.findById(Short.valueOf(copyTo));
		Members threadsOwner = memberService.findMemberById(threads.getAuthorid());
		Usergroups usergroups = userGroupService.findUserGroupById(threadsOwner.getGroupid());
		String allowdirectpost = usergroups.getAllowdirectpost().toString();
		boolean allowCopy = allowCopy(targetForums, allowdirectpost, threads);
		if(!allowCopy){
			request.setAttribute("errorInfo", getMessage(request, "admin_copy_invalid"));
			return mapping.findForward("showMessage");
		}
		if(members==null){
			request.setAttribute("errorInfo", getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}	
		String IP = Common.get_onlineip(request);
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		Map<String,String> settingMap = ((Map<String,String>)servlet.getServletContext().getAttribute("settings"));
		String creditsformula = settingMap.get("creditsformula");
		String creditspolicy = settingMap.get("creditspolicy");
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("threads", threads);
		parameterMap.put("fid", fid);
		parameterMap.put("targetForums", targetForums);
		parameterMap.put("sendreasonpm", sendreasonpm); 
		parameterMap.put("reason", reason);	
		parameterMap.put("adminId", members.getAdminid());	
		parameterMap.put("uid", members.getUid());	
		parameterMap.put("username", members.getUsername()); 
		parameterMap.put("groupId", members.getGroupid());	
		parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
		parameterMap.put("IP", IP);	
		parameterMap.put("url", url);	
		parameterMap.put("creditsformula", creditsformula);
		parameterMap.put("creditspolicy", creditspolicy);
		parameterMap.put("settingMap", (Map<String,String>)servlet.getServletContext().getAttribute("settings"));
		Map<String,String> operationMap = getOperationMap(request);
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String result = topicAdminActionService.operatingCopy(parameterMap,operationMap,resources,locale);
		if(result!=null){
			request.setAttribute("errorInfo", result);
			return mapping.findForward("showMessage");
		}
		if(Common.isshowsuccess(request.getSession(), "admin_succeed")){
			Common.requestforward(response, "forumdisplay.jsp?fid="+fid);
			return null;
		}else{
			request.setAttribute("successInfo", getMessage(request, "admin_succeed"));
			request.setAttribute("requestPath", "forumdisplay.jsp?fid="+fid);
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward operatingRefund(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String tid = request.getParameter("moderates");
		String fid = request.getParameter("fid");
		if(tid == null || fid == null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		HttpSession session = request.getSession();
		Integer memberId = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
		Threads threads = threadsService.findByTid(Integer.valueOf(tid));
		if(threads == null){
			request.setAttribute("errorInfo", getMessage(request, "thread_unexist"));
			return mapping.findForward("showMessage");
		}
		String popedomInfo = getRefundAccess(memberId, settingMap, usergroupMap, request, threads);
		if(popedomInfo != null){
			request.setAttribute("errorInfo", popedomInfo);
			return mapping.findForward("showMessage");
		}
		try{
			if(submitCheck(request, "refundsubmit")){
				String reason = request.getParameter("reason");
				if(checkReasonpm(reason, usergroupMap.get("reasonpm"))){
					request.setAttribute("errorInfo", getMessage(request, "admin_reason_invalid"));
					return mapping.findForward("showMessage");
				}
				Map<String,Object> parameterMap = new HashMap<String, Object>();
				Members members = (Members)session.getAttribute("user");
				Byte adminId = members.getAdminid();	
				Integer uid = members.getUid();	
				String username = members.getUsername(); 
				parameterMap.put("uid", uid);
				parameterMap.put("username", username);
				parameterMap.put("adminId", adminId);
				parameterMap.put("IP", Common.get_onlineip(request));
				parameterMap.put("fid", fid);
				parameterMap.put("reason", reason);
				parameterMap.put("sendreasonpm", request.getParameter("sendreasonpm"));
				parameterMap.put("settingMap", settingMap);
				String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
				parameterMap.put("url", url);
				parameterMap.put("timeoffset", session.getAttribute("timeoffset"));
				parameterMap.put("threads", threads);
				Map<String,String> operationMap = getOperationMap(request);
				MessageResources resources = getResources(request);
				Locale locale = getLocale(request);
				topicAdminActionService.operatingRefund(parameterMap,operationMap,resources,locale);
				if(Common.isshowsuccess(request.getSession(), "admin_succeed")){
					Common.requestforward(response, "forumdisplay.jsp?fid="+fid);
					return null;
				}else{
					request.setAttribute("successInfo", getMessage(request, "admin_succeed"));
					request.setAttribute("requestPath", "forumdisplay.jsp?fid="+fid);
					return mapping.findForward("showMessage");
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		TopicRefundVO topicRefundVO = topicAdminActionService.goTopicRefund(threads, fid, settingMap, usergroupMap);
		request.setAttribute("valueObject", topicRefundVO);
		return mapping.findForward("goTopicadmin_refund");
	}
	private String getRefundAccess(Integer memberId,Map<String,String> settingsMap,Map<String,String> usergroupMap,HttpServletRequest request,Threads threadsInfo){
		if(memberId==null){
			return  getMessage(request, "notlogin");
		}
		if(settingsMap==null){
			return  getMessage(request, "magics_getinfo_faild");
		}
		String creditstrans = settingsMap.get("creditstrans");
		if (creditstrans==null||creditstrans.equals("0")) {
			return  getMessage(request, "magics_credits_no_open");
		}
		if(usergroupMap==null){
			return  getMessage(request, "magics_get_group_info_faild");
		}
		String allowrefund = usergroupMap.get("allowrefund");
		if(allowrefund == null || allowrefund.equals("") || allowrefund.equals("0")){
			return  getMessage(request, "admin_no_popedom");
		}
		if(threadsInfo.getSpecial() != 0 ){
			return  getMessage(request, "special_refundment_invalid");
		}
		return null;
	}
	private boolean allowDeletePosts(HttpServletRequest request){
		HttpSession session = request.getSession(); 
		Byte adminId = (Byte)session.getAttribute("jsprun_adminid");	
		if(adminId!=null){
			if(adminId==0){
				return false;
			}else{
				Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
				String allowdelpost = usergroups.get("allowdelpost");
				if(allowdelpost!=null){
					if(allowdelpost.equals("0")){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	private boolean allowBanpost(HttpServletRequest request){
		HttpSession session = request.getSession(); 
		Byte adminId = (Byte)session.getAttribute("jsprun_adminid");	
		if(adminId!=null){
			if(adminId==0){
				return false;
			}else{
				Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
				String allowdelpost = usergroups.get("allowbanpost");
				if(allowdelpost!=null){
					if(allowdelpost.equals("0")){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	private boolean allowStick(HttpServletRequest request,String level){
		HttpSession session = request.getSession(); 
		Byte adminId = (Byte)session.getAttribute("jsprun_adminid");	
		if(adminId!=null){
			if(adminId==0){
				return false;
			}else{
				Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
				String allowstickthread = usergroups.get("allowstickthread");
				if(allowstickthread!=null){
					Integer levelInteger = Integer.valueOf(level);
					Integer aInteger = Integer.valueOf(allowstickthread);
					if(levelInteger>aInteger){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	private boolean allowCopy(Forums targetForums,String allowdirectpost,Threads threads){
		if(targetForums==null||allowdirectpost==null||threads==null){
			return false;
		}
		if(threads.getSpecial()>0){
			return false;
		}
		if(needAuditing(targetForums, allowdirectpost)){
			return false;
		}
		return true;
	}
	private boolean needAuditing(Forums targetForums,String allowdirectpost){
		if(targetForums.getModnewposts()>0&&!allowdirectpost.equals("2")&&!allowdirectpost.equals("3")){
			return true;
		}else{
			return false;
		}
	}
	private boolean checkReasonpm(String reason,String reasonpm){
		return (reasonpm.equals("1") || reasonpm.equals("3")) && reason.trim().equals("");
	}
	private Map<String,String> getOperationMap(HttpServletRequest request){
		Map<String,String> operationInfoMap = new HashMap<String, String>();
		operationInfoMap.put("MOV", getMessage(request, "MOV"));
		operationInfoMap.put("TMV", getMessage(request, "TMV"));
		operationInfoMap.put("TDEL", getMessage(request, "TDEL"));
		operationInfoMap.put("HLT", getMessage(request, "HLT"));
		operationInfoMap.put("EHL", getMessage(request, "EHL"));
		operationInfoMap.put("ECL", getMessage(request, "ECL"));
		operationInfoMap.put("EOP", getMessage(request, "EOP"));
		operationInfoMap.put("CLS", getMessage(request, "CLS"));
		operationInfoMap.put("OPN", getMessage(request, "OPN"));
		operationInfoMap.put("DWN", getMessage(request, "DWN"));
		operationInfoMap.put("BMP", getMessage(request, "BMP"));
		operationInfoMap.put("STK", getMessage(request, "STK"));
		operationInfoMap.put("UST", getMessage(request, "UST"));
		operationInfoMap.put("EST", getMessage(request, "EST"));
		operationInfoMap.put("DIG", getMessage(request, "DIG"));
		operationInfoMap.put("UDG", getMessage(request, "UDG"));
		operationInfoMap.put("EDI", getMessage(request, "EDI"));
		operationInfoMap.put("DEL", getMessage(request, "DEL"));
		operationInfoMap.put("TYP", getMessage(request, "TYP"));
		operationInfoMap.put("REC", getMessage(request, "REC"));
		operationInfoMap.put("URE", getMessage(request, "URE"));
		operationInfoMap.put("DLP", getMessage(request, "DLP"));
		operationInfoMap.put("BNP", getMessage(request, "BNP"));
		operationInfoMap.put("UBN", getMessage(request, "UBN"));
		operationInfoMap.put("CPY", getMessage(request, "CPY"));
		operationInfoMap.put("MRG", getMessage(request, "MRG"));
		operationInfoMap.put("SPL", getMessage(request, "SPL"));
		operationInfoMap.put("RFD", getMessage(request, "RFD"));
		return operationInfoMap;
	}
}
