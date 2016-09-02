package cn.jsprun.struts.foreg.actions;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.struts.action.BaseAction;
public class StatisticAction extends BaseAction {
	public ActionForward selectBaseInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		int timestamp=(Integer)request.getAttribute("timestamp");
		String timeoffset=(String)request.getSession().getAttribute("timeoffset");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		request.setAttribute("valueObject", statvarsService.getFinalStats_mainVO(timestamp, timeoffset,resources,locale));
		return mapping.findForward("goStats_main");
	}
	public ActionForward forumCompositor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		int timestamp=(Integer)request.getAttribute("timestamp");
		String timeoffset=(String)request.getSession().getAttribute("timeoffset");
		request.setAttribute("valueObject", statvarsService.getForumCompositor(timestamp, timeoffset));
		return mapping.findForward("goStats_forumsrank");
	}
	public ActionForward fluxStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		String tempS = settingMap.get("statstatus");
		if(tempS==null || tempS.equals("0") || tempS.equals("")){
			request.setAttribute("errorInfo", getMessage(request, "statistic_bad_operation"));
			return mapping.findForward("showMessage");
		}
		request.setAttribute("valueObject", statvarsService.getFinalStats_viewsVO());
		return mapping.findForward("goStats_views");
	}
	public ActionForward softWareOfUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		String tempS = settingMap.get("statstatus");
		if(tempS==null || tempS.equals("0") || tempS.equals("")){
			request.setAttribute("errorInfo", getMessage(request, "statistic_bad_operation"));
			return mapping.findForward("showMessage");
		}
		request.setAttribute("valueObject", statvarsService.getFinalStats_agentVO());
		return mapping.findForward("goStats_agent");
	}
	public ActionForward postsLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		String tempS = settingMap.get("statstatus");
		if(tempS==null || tempS.equals("0") || tempS.equals("")){
			request.setAttribute("errorInfo", getMessage(request, "statistic_bad_operation"));
			return mapping.findForward("showMessage");
		}
		int timestamp=(Integer)request.getAttribute("timestamp");
		request.setAttribute("valueObject", statvarsService.getFinalStats_postsLogVO(timestamp));
		return mapping.findForward("goStats_postslog");
	}
	public ActionForward threadCompositor(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		request.setAttribute("valueObject", statvarsService.getFinalStats_threadCompositorVO());
		return mapping.findForward("goStats_threadcompositor");
	}
	public ActionForward postsCompositor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		int timestamp=(Integer)request.getAttribute("timestamp");
		request.setAttribute("valueObject", statvarsService.getFinalStats_postsCompositorVO(timestamp));
		return mapping.findForward("goStats_postscompositor");
	}
	public ActionForward creditCompositor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		int timestamp=(Integer)request.getAttribute("timestamp");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		request.setAttribute("valueObject", statvarsService.getFinalStats_creditCompositorVO(timestamp,resources,locale));
		return mapping.findForward("goStats_creditcompositor");
	}
	public ActionForward onlineTimeCompositor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		Map<String, String> settingMap = (Map)servlet.getServletContext().getAttribute("settings");
		String oltimespan = settingMap.get("oltimespan");
		if(Double.valueOf(oltimespan)<=0){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		int timestamp=(Integer)request.getAttribute("timestamp");
		String timeoffset=(String)request.getSession().getAttribute("timeoffset");
		request.setAttribute("valueObject", statvarsService.getFinalStats_onlineCompositorVO(timestamp, timeoffset));
		return mapping.findForward("goStats_onlinecompositor");
	}
	public ActionForward manageTeam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		int timestamp=(Integer)request.getAttribute("timestamp");
		String timeoffset=(String)request.getSession().getAttribute("timeoffset");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		request.setAttribute("valueObject", statvarsService.getFinalStats_manageTeamVO(timestamp, timeoffset,resources,locale));
		request.setAttribute("username_properties", getMessage(request, "username"));
		request.setAttribute("thisMonthManage_properties", getMessage(request, "stats_modworks_thismonth"));
		request.setAttribute("onlineTotal_properties", getMessage(request, "onlinetime_total"));
		request.setAttribute("onlineThisMonth_properties", getMessage(request, "onlinetime_thismonth"));
		return mapping.findForward("goStats_manageTeam");
	}
	public ActionForward tradeCompositor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		int timestamp=(Integer)request.getAttribute("timestamp");
		String timeoffset=(String)request.getSession().getAttribute("timeoffset");
		request.setAttribute("valueObject", statvarsService.getFinalStats_tradeCompositorVO(timestamp, timeoffset));
		return mapping.findForward("goStats_tradeCompositor");
	}
	public ActionForward manageStatistic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		if(!statvarsService.allowAccess(groupMap)){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",groupMap.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		Map<String, String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		String modworkstatus = settingMap.get("modworkstatus");
		if(modworkstatus.equals("0")){
			request.setAttribute("errorInfo", getMessage(request, "statistic_not_allow_operation"));
			return mapping.findForward("showMessage");
		}
		String uidFromRequest = request.getParameter("uid");
		String beforeFromRequest = request.getParameter("before");
		if (uidFromRequest!=null && !uidFromRequest.equals("")) {
			if (!statvarsService.checkUserid(uidFromRequest)) {
				request.setAttribute("errorInfo", getMessage(request, "statistic_no_manager"));
				return mapping.findForward("showMessage");
			}
		}		
		request.setAttribute("valueObject", statvarsService.getFinalStats_manageStatisticVO(beforeFromRequest,uidFromRequest,getResources(request),getLocale(request)));
		return mapping.findForward("goStats_manageStatistic");
	}
}
