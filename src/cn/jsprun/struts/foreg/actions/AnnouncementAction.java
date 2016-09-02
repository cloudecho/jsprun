package cn.jsprun.struts.foreg.actions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
public class AnnouncementAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("jsprun_action", "21");
		int annid=Common.toDigit(request.getParameter("id"));
		int total=0;
		int page=Common.intval(request.getParameter("page"));
		HttpSession session=request.getSession();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String groupid=session.getAttribute("jsprun_groupid").toString();
		Members member=(Members)session.getAttribute("user");
		Map<String,String> settings=ForumInit.settings;
		int ppp = member!=null&&member.getPpp()>0?member.getPpp():Integer.valueOf(settings.get("postperpage"));
		List<Map<String,String>> announces=dataBaseService.executeQuery("SELECT id, groups FROM jrun_announcements WHERE type!=2 AND starttime<='"+timestamp+"' AND (endtime='0' OR endtime>'"+timestamp+"') ORDER BY displayorder, starttime DESC, id DESC");
		if(announces!=null&&announces.size()>0){
			for (Map<String, String> announce : announces) {
				String groups=announce.get("groups");
				if(groups.length() == 0||Common.in_array(groups.split(","), groupid)){
					total++;
					if(annid>0&&Integer.valueOf(announce.get("id"))==annid){
						page=(int)Math.ceil((double)total/(double)ppp);
					}
				}
			}
		}
		page=Math.max(1, page);
		Map<String,Integer> multiInfo=Common.getMultiInfo(total, ppp, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		Map<String,Object> multi=Common.multi(total, ppp, page, "announcement.jsp", 0, 10, true, false, null);
		request.setAttribute("multi",multi);
		List<Map<String,String>> announcements=dataBaseService.executeQuery("SELECT * FROM jrun_announcements WHERE type!=2 AND starttime<='"+timestamp+"' AND (endtime='0' OR endtime>'"+timestamp+"') ORDER BY displayorder, starttime DESC, id DESC LIMIT "+start_limit+", "+ppp);
		if(announcements!=null&&announcements.size()>0){
			String timeoffset=(String)session.getAttribute("timeoffset");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat, timeoffset);
			List<Map<String,String>> announcementList=new ArrayList<Map<String,String>>();
			for(Map<String,String> announcement:announcements){
				String groups=announcement.get("groups");
				if(groups.length()==0||Common.in_array(groups.split(","), groupid)){
					announcement.put("authorenc", Common.encode(announcement.get("author")));
					int endtime=Integer.parseInt(announcement.get("endtime"));
					announcement.put("starttime",Common.gmdate(sdf_all, Integer.parseInt(announcement.get("starttime"))));
					announcement.put("endtime",endtime>0?Common.gmdate(sdf_all, endtime): getMessage(request, "unlimite"));
					String  message=announcement.get("message");
					if("1".equals(announcement.get("type"))){
						message="<a href=\""+message+"\" target=\"_blank\">"+message+"</a>";
					}
					announcement.put("message", Common.nl2br(message));
					announcementList.add(announcement);
				}
			}
			request.setAttribute("announcements", announcementList);
			request.setAttribute("annid", annid);
			return mapping.findForward("toAnnouncement");
		}else{
			request.setAttribute("errorInfo", getMessage(request, "announcement_nonexistence"));
			return mapping.findForward("showMessage");
		}
	}
}