package cn.jsprun.struts.action;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import cn.jsprun.domain.Members;
import cn.jsprun.utils.Common;
import cn.jsprun.vo.TableStatusVO;
public class HomeManageAction extends BaseAction {
	public ActionForward home(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Members member = (Members)session.getAttribute("members");
		byte adminid = member.getAdminid();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String timeoffset = (String)session.getAttribute("timeoffset");
		String dateformat = (String)session.getAttribute("dateformat");
		String timeformat = (String)session.getAttribute("timeformat");
		SimpleDateFormat sdf_all = Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
		List<Map<String,String>> adminSessions=dataBaseService.executeQuery("SELECT a.*, m.username, m.adminid, m.regip FROM jrun_adminsessions a LEFT JOIN jrun_members m USING(uid) ORDER BY a.errorcount");
		for (Map<String, String> adminSession : adminSessions) {
			adminSession.put("dateline", Common.gmdate(sdf_all, Integer.parseInt(adminSession.get("dateline"))));
		}
		request.setAttribute("adminSessions", adminSessions);
		try{
			if(submitCheck(request, "notesubmit")){
				String delete[] = request.getParameterValues("delete");
				if(delete!=null){
					String ids=Common.implodeids(delete);
					dataBaseService.runQuery("delete from jrun_adminnotes where id in ("+ids+")");
				}
				String newmessage = request.getParameter("newmessage");
				if(!Common.empty(newmessage)){
					String newaccess1 = request.getParameter("newaccess[1]");
					String newaccess2 = request.getParameter("newaccess[2]");
					String newaccess3 = request.getParameter("newaccess[3]");
					newaccess1 = newaccess1==null?"0":newaccess1;
					newaccess2 = newaccess2==null?"0":newaccess2;
					newaccess3 = newaccess3==null?"0":newaccess3;
					if(adminid==1){
						newaccess1 = "1";
					}else if(adminid==2){
						newaccess2 = "1";
					}else{
						newaccess3 = "1";
					}
					int newaccess=Integer.valueOf(newaccess1+newaccess2+newaccess3, 2);
					int newexpiration = Common.dataToInteger(request.getParameter("newexpiration"), "yyyy-MM-dd", timeoffset);
					newmessage = Common.nl2br(Common.htmlspecialchars(newmessage));
					dataBaseService.runQuery("INSERT INTO jrun_adminnotes (admin, access, adminid, dateline, expiration, message)VALUES ('"+Common.addslashes(member.getUsername())+"', '"+newaccess+"', '"+adminid+"', '"+timestamp+"', '"+newexpiration+"', '"+Common.addslashes(newmessage)+"')",true);
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		request.setAttribute("newexpiration", Common.gmdate("yyyy-MM-dd", timestamp + 86400 * 30, timeoffset));
		String access = null;
		switch(adminid) {
			case 1: access = "4,5,6,7"; break;
			case 2: access = "2,3,6,7"; break;
			default: access = "1,3,5,7"; break;
		}
		List<Map<String,String>> notes = dataBaseService.executeQuery("SELECT * FROM jrun_adminnotes WHERE access IN ("+access+") ORDER BY dateline DESC");
		if(notes!=null && notes.size()>0){
			List<Map<String,String>> adminNotes =new ArrayList<Map<String,String>>();
			SimpleDateFormat sdf_dateformat = Common.getSimpleDateFormat(dateformat, timeoffset);
			String yes = getMessage(request, "yes");
			String no = getMessage(request, "no");
			for(Map<String,String> adminNote : notes){
				int expiration = Integer.parseInt(adminNote.get("expiration"));
				if(expiration < timestamp){
					dataBaseService.runQuery("DELETE FROM jrun_adminnotes WHERE id='"+adminNote.get("id")+"'");
				}else{
					adminNote.put("adminEnc", adminNote.get("admin"));
					adminNote.put("dateline", Common.gmdate(sdf_all, Integer.parseInt(adminNote.get("dateline"))));
					adminNote.put("expiration", Common.gmdate(sdf_dateformat, Integer.parseInt(adminNote.get("expiration"))));
					int accesss=Integer.parseInt(adminNote.get("access"));
					adminNote.put("access1", (accesss&4)>0 ? yes : no);
					adminNote.put("access2", (accesss&2)>0 ? yes : no);
					adminNote.put("access3", (accesss&1)>0 ? yes : no);
					adminNotes.add(adminNote);
				}
			}
			request.setAttribute("adminNotes", adminNotes);
		}
		if(adminid==1){
			request.setAttribute("forumSelect", Common.forumselect(true, false,member!=null?member.getGroupid():7,member!=null?member.getExtgroupids():null,null));
			List<Map<String,String>> userGroups = dataBaseService.executeQuery("SELECT groupid, grouptitle FROM jrun_usergroups ORDER BY creditslower, groupid");
			request.setAttribute("userGroups", userGroups);
			ModuleConfig ac =(ModuleConfig) request.getAttribute(Globals.MODULE_KEY);
		    request.setAttribute("maxupload", ac.getControllerConfig().getMaxFileSize());
			List<Map<String,String>> version = dataBaseService.executeQuery("select VERSION() as version;");
			request.setAttribute("mysqlversion", version.get(0).get("version"));
			String sysType = System.getProperty("os.name");
			request.setAttribute("sysType", sysType);
			String javaversion = System.getProperty("java.version");  
			request.setAttribute("jkdversion", "JDK v"+javaversion);
			List<TableStatusVO> tableStatusVOs = dataBaseService.findTableStatus("SHOW TABLE STATUS LIKE 'jrun_%';");
			long totalsize = 0L;
			for (TableStatusVO statusVO : tableStatusVOs) {
				totalsize += statusVO.getData_length()	+ statusVO.getIndex_length();
			}
			request.setAttribute("totalsize", totalsize);
			String attachsize = request.getParameter("attasize");
			if(attachsize!=null){
				List<Map<String,String>> attasize = dataBaseService.executeQuery("SELECT SUM(filesize) as size FROM jrun_attachments");
				if(attasize!=null && attasize.size()>0){
					String size=attasize.get(0).get("size");
					request.setAttribute("attasize", size!=null?Long.parseLong(size):0);
				}else{
					request.setAttribute("attasize", 0);
				}
			}
		}
		return mapping.findForward("home");
	}
}