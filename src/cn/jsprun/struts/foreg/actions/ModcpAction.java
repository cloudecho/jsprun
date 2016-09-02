package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Admingroups;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.IPSeeker;
import cn.jsprun.utils.Jspruncode;
public class ModcpAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward editsubject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		int tid=Common.toDigit(request.getParameter("tid"));
		List<Map<String,String>> orig=dataBaseService.executeQuery("SELECT m.adminid,p.fid,p.subject,p.first, p.authorid, p.author, p.dateline, p.anonymous, p.invisible FROM jrun_posts p LEFT JOIN jrun_members m ON m.uid=p.authorid WHERE p.tid='"+tid+"' AND p.first='1'");
		if(orig==null||orig.size()<=0){
			Common.writeMessage(response, getMessage(request, "thread_nonexistence"), true);
			return null;
		}
		Map<String,String> post=orig.get(0);
		Map<String,String> settings=ForumInit.settings;
		Map<String,String> usergroups=(Map<String,String>)request.getAttribute("usergroups");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String message=Common.periodscheck(settings.get("postbanperiods"), Byte.valueOf(usergroups.get("disableperiodctrl")), timestamp, settings.get("timeoffset"),resources,locale);
		if(message!=null){
			Common.writeMessage(response, message, true);
			return null;
		}
		HttpSession session = request.getSession();
		Members member = (Members) session.getAttribute("user");
		short groupid=(Short)session.getAttribute("jsprun_groupid");
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		String modadd2 = "";String modadd1 = "";
		if(member!=null&&member.getAdminid()==3){
			modadd1 = ", m.uid AS ismoderator ";
			modadd2 = " LEFT JOIN jrun_moderators m ON m.uid='"+jsprun_uid+"' AND m.fid=f.fid ";
		}
		Map<String,String> forum=dataBaseService.executeQuery("SELECT f.alloweditpost,ff.viewperm, a.allowview "+modadd1+" FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid LEFT JOIN jrun_access a ON a.uid='"+ jsprun_uid+ "' AND a.fid=f.fid "+modadd2+" WHERE f.fid='"+post.get("fid")+"'").get(0);
		int allowview =Common.toDigit(forum.get("allowview"));
		if(allowview==0){
			String viewperm=forum.get("viewperm");
			if(viewperm.length()==0&&Integer.valueOf(usergroups.get("readaccess"))==0){
				Common.writeMessage(response, getMessage(request, "group_nopermission", usergroups.get("grouptitle")), true);
				return null;
			}
			else if(viewperm.length()>0&&!Common.forumperm(viewperm, groupid, member!=null?member.getExtgroupids():null)){
				Common.writeMessage(response, getMessage(request, "forum_nopermission_2"), true);
				return null;
			}
		}
		boolean ismoderator=Common.ismoderator(forum.get("ismoderator"), member);
		int adminid=Common.toDigit(post.get("adminid"));
		if(!ismoderator|| usergroups.get("alloweditpost").equals("0")||((adminid==1||adminid==2||adminid==3)&&member.getAdminid()>adminid)){
			Common.writeMessage(response, getMessage(request, "post_edit_nopermission"), true);
			return null;
		}
		String subjectnew=request.getParameter("subjectnew");
		if(subjectnew!=null){
			subjectnew=Common.ajax_decode(subjectnew);
		}else{
			subjectnew="";
		}
		String result=Common.checkpost(subjectnew, null, settings, usergroups,resources,locale);
		if(result!=null){
			Common.writeMessage(response, result, true);
			return null;
		}
		try{
			if(submitCheck(request, "editsubjectsubmit",true)){
				subjectnew=Common.htmlspecialchars(subjectnew);
				dataBaseService.runQuery("UPDATE jrun_threads SET subject='"+Common.addslashes(subjectnew)+"' WHERE tid='"+tid+"'");
				dataBaseService.runQuery("UPDATE jrun_posts SET subject='"+Common.addslashes(subjectnew)+"' WHERE tid='"+tid+"' AND first='1'");
				showMessage("<a href=\"viewthread.jsp?tid="+tid+"\">"+subjectnew+"</a>",null, response);
				return null;
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("tid", tid);
		request.setAttribute("fid", post.get("fid"));
		request.setAttribute("subject",post.get("subject"));
		return mapping.findForward("tomodcppost");
	}
	@SuppressWarnings("unchecked")
	public ActionForward editmessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		if(jsprun_uid==0){
			showMessage(null,getMessage(request, "not_loggedin"),response);
			return null;
		}
		int pid = Common.toDigit(request.getParameter("pid"));
		List<Map<String,String>> orig=dataBaseService.executeQuery("SELECT m.adminid,p.pid,p.fid, p.first, p.authorid, p.author, p.dateline, p.anonymous, p.invisible, p.message FROM jrun_posts p LEFT JOIN jrun_members m ON m.uid=p.authorid WHERE p.pid='"+pid+"' AND p.invisible > -1");
		if(orig==null||orig.size()<=0){
			showMessage(null,getMessage(request, "post_check", request.getParameter("tid")),response);
			return null;
		}
		Map<String,String> post=orig.get(0);
		Map<String,String> settings=ForumInit.settings;
		Map<String,String> usergroups=(Map<String,String>)request.getAttribute("usergroups");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String showmessage=Common.periodscheck(settings.get("postbanperiods"), Byte.valueOf(usergroups.get("disableperiodctrl")), timestamp, settings.get("timeoffset"),resources,locale);
		if(showmessage!=null){
			showMessage(null,showmessage, response);
			return null;
		}
		Members member = (Members)session.getAttribute("user");
		short groupid=(Short)session.getAttribute("jsprun_groupid");
		String modadd2 = "";String modadd1 = "";
		if(member!=null&&member.getAdminid()==3){
			modadd1 = ", m.uid AS ismoderator ";
			modadd2 = " LEFT JOIN jrun_moderators m ON m.uid='"+jsprun_uid+"' AND m.fid=f.fid ";
		}
		Map<String,String> forum=dataBaseService.executeQuery("SELECT f.alloweditpost,f.allowimgcode,ff.viewperm, a.allowview "+modadd1+"FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid LEFT JOIN jrun_access a ON a.uid='"+ jsprun_uid+ "' AND a.fid=f.fid "+modadd2+" WHERE f.fid='"+post.get("fid")+"'").get(0);
		int allowview =Common.toDigit(forum.get("allowview"));
		if(allowview==0){
			String viewperm=forum.get("viewperm");
			if(viewperm.length()==0&&Integer.valueOf(usergroups.get("readaccess"))==0){
				showMessage(null,getMessage(request, "group_nopermission", usergroups.get("grouptitle")), response);
				return null;
			}
			else if(viewperm.length()>0&&!Common.forumperm(viewperm, groupid, member!=null?member.getExtgroupids():null)){
				showMessage(null,getMessage(request, "forum_nopermission_2"), response);
				return null;
			}
		}
		boolean ismoderator = Common.ismoderator(forum.get("ismoderator"), member);
		int adminid=Common.toDigit(post.get("adminid"));
		if(!ismoderator|| usergroups.get("alloweditpost").equals("0")||((adminid==1||adminid==2||adminid==3)&&member.getAdminid()>adminid)){
			showMessage(null,getMessage(request, "post_edit_nopermission"), response);
			return null;
		}
		try{
			if(submitCheck(request, "editmessagesubmit",true)){
				String smilies_parse = ((Map<String,String>)request.getAttribute("smilies_parse")).get("smilies_parse");
				List<Map<String,String>> smilieslist = dataParse.characterParse(smilies_parse);
				String dos = request.getParameter("do");
				int parnum = Common.intval(settings.get("maxsmilies"));
				if (dos != null) {
					String message = post.get("message");
					message = parseSmilies(smilieslist,message,parnum,Integer.valueOf(forum.get("allowimgcode"))>0,resources,locale,pid);
					showMessage(message, null, response);
				} else {
					String message = request.getParameter("message");
					String result=Common.checkpost(null,message, settings, usergroups,resources,locale);
					if(result!=null){
						Common.writeMessage(response,result,true);
						return null;
					}
					dataBaseService.runQuery("update jrun_posts set message='" + Common.addslashes(message)+ "' where pid = " + pid,true);
					message = parseSmilies(smilieslist,message, parnum,Integer.valueOf(forum.get("allowimgcode"))>0,resources,locale,pid);
					Common.writeMessage(response,message,false);
				}
				smilieslist=null;
				return null;
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		post.put("message", Common.htmlspecialchars(post.get("message")));
		request.setAttribute("post", post);
		return mapping.findForward("tomodcppost");
	}
	public ActionForward getip(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		int pid = Common.toDigit(request.getParameter("pid"));
		int tid = Common.toDigit(request.getParameter("tid"));
		HttpSession session = request.getSession();
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		byte jsprun_adminid = (Byte)( session.getAttribute("jsprun_adminid")==null?0:session.getAttribute("jsprun_adminid"));
		String modertarhql = "SELECT m.adminid, p.first, p.useip ,p.fid,p.tid FROM jrun_posts p	LEFT JOIN jrun_members m ON m.uid=p.authorid WHERE pid='"+ pid + "' AND tid='" + tid + "'";
		List<Map<String, String>> members = dataBaseService.executeQuery(modertarhql);
		String message = "";
		String ajax = request.getParameter("inajax");
		if(members==null || members.size()<=0){
			message = getMessage(request, "thread_nonexistence");
			if(ajax!=null){
				Common.writeMessage(response, message, true);
				return null;
			}else{
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}
		}else{
			Map<String,String> membermap = members.get(0);
			List<Map<String,String>> threadpost = dataBaseService.executeQuery("select f.fid,f.type,f.fup,f.name,t.tid,t.subject,t.digest from jrun_threads t left join jrun_forums f on t.fid=f.fid where t.tid='"+membermap.get("tid")+"'");
			Map<String,String> threads = threadpost.get(0);
			if(ajax==null){
				String navigation = "<a href=\"forumdisplay.jsp?fid="+threads.get("fid")+"\">"+threads.get("name")+"</a> &raquo; <a href=\"viewthread.jsp?tid="+threads.get("tid")+"\">"+threads.get("subject")+"</a>";
				if(threads.get("type").equals("sub")){
					Map<String,String> paratforum = dataBaseService.executeQuery("select f.fid,f.name from jrun_forums f where fid='"+threads.get("fup")+"'").get(0);
					navigation = "<a href=\"forumdisplay.jsp?fid="+paratforum.get("fid")+"\">"+paratforum.get("name")+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+threads.get("fid")+"\">"+threads.get("name")+"</a> &raquo <a href=\"viewthread.jsp?tid="+threads.get("tid")+"\">"+threads.get("subject")+"</a>";
				}
				request.setAttribute("navigation", navigation);
			}
			if(("1".equals(membermap.get("adminid")) && jsprun_adminid>1)||("2".equals(membermap.get("adminid")) && jsprun_adminid>2)){
				message = getMessage(request, "admin_getip_nopermission");
				if(ajax!=null){
					Common.writeMessage(response, message, true);
					return null;
				}else{
					request.setAttribute("errorInfo", message);
					return mapping.findForward("showMessage");
				}
			}else if(membermap.get("first").equals("1") && Integer.valueOf(threads.get("digest"))==-1){
				message = getMessage(request, "special_noaction");
				if(ajax!=null){
					Common.writeMessage(response, message, true);
					return null;
				}else{
					request.setAttribute("errorInfo", message);
					return mapping.findForward("showMessage");
				}
			}
			IPSeeker seeker  = IPSeeker.getInstance();
			MessageResources mr = getResources(request);
			Locale locale = getLocale(request);
			String address = seeker.getAddress(membermap.get("useip")==null?"":membermap.get("useip"),mr,locale);
			request.setAttribute("address", address);
			request.setAttribute("ip", membermap.get("useip"));
			Admingroups admingroup = userGroupService.findAdminGroupById(Short.valueOf(groupid+""));
			if(admingroup!=null){
				request.setAttribute("banip", admingroup.getAllowbanip());
			}
			return mapping.findForward("topicagetip");
		}
	}
	private String parseSmilies(List<Map<String,String>> smilieslist,String message, int parnum,boolean allowimgcode,MessageResources resources,Locale locale,int pid) {
		message = message.replace("$", Common.SIGNSTRING)+" ";
		Jspruncode jspruncode = new Jspruncode();
		message = jspruncode.parseCode(message, pid,resources.getMessage(locale,"copy_code"));
		message = jspruncode.parseJsprunCode(message, resources, locale);
		message = jspruncode.parsetable(message);
		message = jspruncode.parseimg(message, allowimgcode);
		message = message.replaceAll("\\n", "<br/>");
		message = message.replaceAll("(?i)<br/>(<TD[^>]*>|<TR[^>]*>|</TR[^>]*>)", "$1");
		message = relacesmile(smilieslist,message, parnum);
		int count = jspruncode.getCodecount();
		List<String> codelist = jspruncode.getCodelist();
		for(int i=0;i<count;i++){
			String str = codelist.get(i);
			message = StringUtils.replace(message, "[\tJSPRUN_CODE_"+i+"\t]", str);
		}
		message = message.replace(Common.SIGNSTRING, "$");
		message = message.replaceAll("<EM>", "<I>");
		message = message.replaceAll("</EM>", "</I>");
		return message;
	}
	private String relacesmile(List<Map<String,String>> smilieslist,String message, int parnum) {
		for(Map<String,String> smiles:smilieslist){
			if(message.indexOf(smiles.get("code")+" ")!=-1 || message.indexOf(" "+smiles.get("code"))!=-1){
				StringBuffer buffer = new StringBuffer();
				buffer.append("<img src='images/smilies/");
				buffer.append(smiles.get("directory"));
				buffer.append("/");
				buffer.append(smiles.get("url"));
				buffer.append("' smilieid='");
				buffer.append(smiles.get("id"));
				buffer.append("' border='0' alt='' /> ");
				message = StringUtils.replace(message, smiles.get("code"), buffer.toString(),parnum);
			}
		}
		return message;
	}
	private void showMessage(String url,String message,HttpServletResponse response)
	{
		Common.setResponseHeader(response);
		try {
			if(message!=null)
			{
				response.getWriter().write("<script type=\"text/javascript\">alert('"+message+"','"+Common.rand(10000)+"');</script>");
			}
			else{
				response.getWriter().write(url);
			}
		} catch (IOException e) {
		}
	}
}