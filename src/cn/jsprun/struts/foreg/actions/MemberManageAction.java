package cn.jsprun.struts.foreg.actions;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Validating;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Mail;
import cn.jsprun.utils.Md5Token;
public class MemberManageAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward online(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		int num = Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_sessions").get(0).get("count"));
		int membermaxpages=Integer.valueOf(settings.get("membermaxpages"));
		int memberperpage=Integer.valueOf(settings.get("memberperpage"));
		int page = Common.range(Common.intval(request.getParameter("page")), membermaxpages>0?membermaxpages:2147483647, 1);
		Map<String,Integer> multiInfo=Common.getMultiInfo(num, memberperpage, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		Map<String,Object> multi=Common.multi(num, memberperpage, page, "member.jsp?action=online", membermaxpages, 10, true, false, null);
		request.setAttribute("multi", multi);
		List<Map<String,String>> onlinelist=dataBaseService.executeQuery("SELECT CONCAT(s.ip1,'.',s.ip2,'.',s.ip3,'.',s.ip4) AS ip,s.uid,s.username,s.lastactivity,s.action,s.fid,s.tid, f.name, t.subject FROM jrun_sessions s LEFT JOIN jrun_forums f ON s.fid=f.fid LEFT JOIN jrun_threads t ON s.tid=t.tid WHERE s.invisible='0' ORDER BY s.lastactivity DESC LIMIT "+ start_limit+ ", " +memberperpage);
		if(onlinelist!=null&&onlinelist.size()>0){
			HttpSession session=request.getSession();
			String timeformat=(String)session.getAttribute("timeformat");
			String timeoffset=(String)session.getAttribute("timeoffset");
			SimpleDateFormat sdf=Common.getSimpleDateFormat(timeformat, timeoffset);
			for (Map<String, String> online : onlinelist) {
				String subject=online.get("subject");
				online.put("lastactivity", Common.gmdate(sdf, Integer.parseInt(online.get("lastactivity"))));
				online.put("subject",subject!=null? Common.cutstr(subject, 35):"");
				online.put("action", getMessage(request,online.get("action")));
			}
			request.setAttribute("onlinelist",onlinelist);
		}
		return mapping.findForward("whosonline");
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ActionForward list(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		byte adminid=(Byte)session.getAttribute("jsprun_adminid");
		Map<String, String> settings = ForumInit.settings;
		String type=request.getParameter("type");
		type=type!=null&&type.matches("^(admins|birthdays|grouplist)+$")?type:"";
		String maxbdays=settings.get("maxbdays");
		if((adminid!=1)&&!settings.get("memliststatus").equals("1")&&!type.equals("birthdays")){
			request.setAttribute("resultInfo", getMessage(request, "member_list_disable"));
			return mapping.findForward("showMessage");
		}else if(type.equals("birthdays")&&maxbdays.equals("0")){
			request.setAttribute("resultInfo", getMessage(request, "todays_birthdays_banned"));
			return mapping.findForward("showMessage");
		}
		int listgid=Common.toDigit(request.getParameter("listgid"));
		String order=request.getParameter("order");
		String srchmem=request.getParameter("srchmem");
		order=order==null?"":order;
		srchmem=srchmem==null?"":srchmem;
		if(listgid!=0){
			type=adminid==1?"grouplist":type;
		}
		String orderadd=null;
		String sql=null;
		if("admins".equals(type)){
			sql="WHERE groupid IN (1, 2, 3)";
		}else if("birthdays".equals(type)){
			int timestamp = (Integer)(request.getAttribute("timestamp"));
			sql="WHERE RIGHT(bday, 5)='"+Common.gmdate("MM-dd", timestamp, settings.get("timeoffset"))+"' ORDER BY bday";
		}else if("grouplist".equals(type)){
			sql="WHERE groupid='"+listgid+"'";
		}else{
			if("credits".equals(order)){
				orderadd="ORDER BY credits DESC";
			}else if("gender".equals(order)){
				orderadd="ORDER BY gender DESC";
			}else if("username".equals(order)){
				orderadd="ORDER BY username DESC";
			}else{
				orderadd="ORDER BY uid"; 
				order = "uid";
			}			
			if(!srchmem.equals("")){
				srchmem=srchmem.replaceAll("_", "\\_");
				srchmem=srchmem.replaceAll("%", "\\%");
				sql=" WHERE username LIKE '"+Common.addslashes(srchmem)+"%'";
			}			
		}
		String numSql="SELECT COUNT(*) count FROM jrun_members ";
		int num = Integer.valueOf(dataBaseService.executeQuery(numSql+(sql!=null?sql:"")).get(0).get("count"));
		int membermaxpages=Integer.valueOf(settings.get("membermaxpages"));
		int memberperpage=Integer.valueOf(settings.get("memberperpage"));
		int page = Common.range(Common.intval(request.getParameter("page")), membermaxpages>0?membermaxpages:2147483647, 1);
		Map<String,Integer> multiInfo=Common.getMultiInfo(num, memberperpage, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		String url = "member.jsp?action=list&listgid="+listgid+"&srchmem="+Common.encode(srchmem)+"&order="+order+"&type="+type;
		Map<String,Object> multi=Common.multi(num, memberperpage, page, url, membermaxpages, 10, true, false, null);
		request.setAttribute("multi", multi);
		List<Map<String,String>> memberList=dataBaseService.executeQuery("SELECT m.uid, m.username, m.gender,m.regdate, m.lastvisit, m.posts, "+("birthdays".equals(type)?"m.bday":"m.credits")+" FROM jrun_members m "+(sql!=null?sql:"")+(orderadd!=null?orderadd:"")+" LIMIT "+ start_limit+ ", " + memberperpage);
		if(memberList!=null&&memberList.size()>0){
			String timeoffset=(String)session.getAttribute("timeoffset");
			String timeformat=(String)session.getAttribute("timeformat");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat,timeoffset);
			SimpleDateFormat sdf_dateformat=Common.getSimpleDateFormat(dateformat,timeoffset);
			for (Map<String, String> member : memberList) {
				member.put("regdate", Common.gmdate(sdf_dateformat, Integer.parseInt(member.get("regdate"))));
				member.put("lastvisit", Common.gmdate(sdf_all, Integer.parseInt(member.get("lastvisit"))));
			}
			request.setAttribute("memberList", memberList);
		}
		request.setAttribute("type", type);
		return mapping.findForward("toMemberList");
	}
	public ActionForward viewavatars(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		String avatarsdir =  JspRunConfig.realPath + "images/avatars";
		File file = new File(avatarsdir);
		if (file.isDirectory()) {
			Map<Integer, String> avatars = new TreeMap<Integer, String>();
			HttpSession session=request.getSession();
			String userAvatar="";
			int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
			if (jsprun_uid>0) {
				List<Map<String,String>> members=dataBaseService.executeQuery("SELECT avatar FROM jrun_memberfields WHERE uid='"+jsprun_uid+"'");
				if (members != null&&members.size()>0) {
					userAvatar=members.get(0).get("avatar");
				}
			}
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				String avatarregex=".*\\.(gif|jpg|png)$";
				int i=1;
				for (File avatarFile : files) {
					String avatarName = avatarFile.getName();
					if (avatarName.matches(avatarregex)&&!"noavatar.gif".equals(avatarName)) {
						avatars.put(i,avatarName);
						i++;
					}
				}
			}
			int app = 16;
			int page =Math.max(Common.intval(request.getParameter("page")), 1);
			int num=avatars.size();
			int start = (page - 1) * app;
			int end = (start + app > num) ? (num) : (start + app - 1);
			StringBuffer avatarlist=new StringBuffer();
			for(int i = start; i <= end; i += 4) {
				avatarlist.append("<tr>");
				for(int j = 0; j < 4; j++) {
					avatarlist.append("<td width=\"25%\" align=\"center\" style=\"border-bottom: none;\">");
					String avatar=avatars.get(i + j);
					if(avatar!=null&&(i + j)>0) {
						avatarlist.append("<img src=\"images/avatars/"+avatar+"\"/></td>");
					} else {
						if(i == 0) {
							avatarlist.append("<img src=\"images/avatars/noavatar.gif\"/>");
						}
						avatarlist.append("</td>");
					}
				}
				avatarlist.append("</tr><tr>");
				for(int j = 0; j < 4; j++) {
					avatarlist.append("<td width=\"25%\" align=\"center\">");
					String avatar=avatars.get(i + j);
					if(avatar!=null&&(i + j)>0) {
						String checked="";
						if(userAvatar.indexOf(avatar)>=0) {
							checked = "checked=\"checked\"";
						}
						avatarlist.append("<input type=\"radio\" value=\"images/avatars/"+avatar+"\" name=\"systemavatar\" "+checked+" onclick=\"if($(\'urlavatar\')) { $(\'urlavatar\').value=this.value;previewavatar(this.value); }\" />"+avatar);
					} else if((i + j) == 0) {
						String checked="";
						if(userAvatar.length()==0) {
							checked = "checked=\"checked\"";
						}
						avatarlist.append("<input type=\"radio\" value=\"\" name=\"systemavatar\" "+checked+" onclick=\"if($(\'urlavatar\')) { $(\'urlavatar\').value=this.value;previewavatar(this.value); }\" /><strong>None</strong>");
					} else {
						avatarlist.append("&nbsp;</td>");
					}
				}
				avatarlist.append("</tr>");
			}
			request.setAttribute("avatarlist", avatarlist);
			String ajaxtarget=request.getParameter("ajaxtarget");
			Map<String,Object> multi=Common.multi(num, app, page, "member.do?action=viewavatars", 0, 10, false, false, ajaxtarget);
			request.setAttribute("multi", multi);
		} else {
			request.setAttribute("errorInfo",getMessage(request, "profile_avatardir_nonexistence"));
		}
		return mapping.findForward("member_misc");
	}
	@SuppressWarnings("unchecked")
	public ActionForward activate(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) {
		int uid=Common.intval(request.getParameter("uid"));
		String id=request.getParameter("id");
		List<Map<String,String>> members=dataBaseService.executeQuery("SELECT m.uid, m.username, m.credits, mf.authstr FROM jrun_members m, jrun_memberfields mf WHERE m.uid='"+uid+"' AND mf.uid=m.uid AND m.groupid='8'");
		if(members!=null&&members.size()>0){
			Map<String,String> member=members.get(0);
			String[] authstr = member.get("authstr").split("\t");
			if(authstr.length==3&&"2".equals(authstr[1])&&id.equals(authstr[2])){
				int credits=Common.toDigit(member.get("credits"));
				List<Map<String,String>> usergroups=dataBaseService.executeQuery("SELECT groupid FROM jrun_usergroups WHERE type='member' AND "+credits+">=creditshigher AND "+credits+"<creditslower LIMIT 1");
				int groupid=0;
				if(usergroups!=null&&usergroups.size()>0){
					groupid=Common.toDigit(usergroups.get(0).get("groupid"));
				}
				dataBaseService.runQuery("UPDATE jrun_members SET groupid='"+groupid+"' WHERE uid='"+member.get("uid")+"'");
				dataBaseService.runQuery("UPDATE jrun_memberfields SET authstr='' WHERE uid='"+member.get("uid")+"'");
				request.setAttribute("successInfo", getMessage(request, "activate_succeed", member.get("username")));
				request.setAttribute("requestPath",ForumInit.settings.get("indexname"));
				return mapping.findForward("showMessage");
			}else{
				request.setAttribute("errorInfo", getMessage(request, "activate_illegal"));
				return mapping.findForward("showMessage");
			}
		}else{
			request.setAttribute("errorInfo", getMessage(request, "activate_illegal"));
			return mapping.findForward("showMessage");
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward lostpasswd(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "lostpwsubmit")){
				String username = request.getParameter("username");
				String email = request.getParameter("email");
				int questionid = Integer.valueOf(request.getParameter("questionid"));
				String answer = request.getParameter("answer");
				String secques = Common.quescrypt(questionid, answer);
				List<Map<String,String>> members=dataBaseService.executeQuery("SELECT uid, username, adminid, email FROM jrun_members WHERE username=? AND secques=? AND email=?",Common.addslashes(username),secques,Common.addslashes(email));
				if (members != null&&members.size()>0) {
					Map<String,String> member=members.get(0);
					int adminid=Integer.valueOf(member.get("adminid"));
					if (adminid == 1 || adminid == 2) {
						request.setAttribute("errorInfo",getMessage(request, "getpasswd_account_invalid"));
						return mapping.findForward("showMessage");
					} else {
						String idstring = Common.getRandStr(6,false);
						int timestamp = (Integer)(request.getAttribute("timestamp"));
						dataBaseService.runQuery("UPDATE jrun_memberfields SET authstr='"+timestamp+"\t1\t"+idstring+"' WHERE uid='"+member.get("uid")+"'", false);
						request.setAttribute("resultInfo",getMessage(request, "getpasswd_send_succeed"));
						Map<String,String> settings=ForumInit.settings;
						Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
						HttpSession session=request.getSession();
						String boardurl=(String)session.getAttribute("boardurl");
						Mail mail=new Mail(mails);
						mail.sendMessage(mails.get("from"),member.get("username")+" <"+member.get("email")+">", getMessage(request, "get_passwd_subject"), getMessage(request, "get_passwd_message", settings.get("bbname"),boardurl,member.get("uid"),idstring,Common.get_onlineip(request)), null);
						return mapping.findForward("showMessage");
					}
				} else {
					request.setAttribute("errorInfo", getMessage(request, "getpasswd_account_notmatch"));
					return mapping.findForward("showMessage");
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("resultInfo",getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	public ActionForward toGetpasswd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		int uid=Common.intval(request.getParameter("uid"));
		List<Map<String,String>> members=dataBaseService.executeQuery("SELECT m.username, mf.authstr,m.salt FROM jrun_members m,jrun_memberfields mf WHERE m.uid='"+uid+"' AND mf.uid=m.uid");
		if (members == null||members.size()==0||members.get(0).get("authstr").equals("")) {
			request.setAttribute("resultInfo", getMessage(request, "getpasswd_illegal"));
			return mapping.findForward("showMessage");
		}
		Map<String,String> member=members.get(0);
		String id = request.getParameter("id");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String[] authstr = member.get("authstr").split("\t");
		int dataline = Common.toDigit(authstr[0]);
		int operation = Common.toDigit(authstr[1]);
		String idString = authstr[2];
		if (dataline < timestamp - 86400 * 3 || operation != 1|| !idString.equals(id)) {
			request.setAttribute("resultInfo", getMessage(request, "getpasswd_illegal"));
			return mapping.findForward("showMessage");
		}
		String newpasswd1 = request.getParameter("newpasswd1");
		String newpasswd2 = request.getParameter("newpasswd2");
		String getpwsubmit = request.getParameter("getpwsubmit");
		if (getpwsubmit == null || newpasswd1 != null&& !newpasswd1.equals(newpasswd2)) {
			request.setAttribute("username",member.get("username"));
			request.setAttribute("uid",uid);
			request.setAttribute("id", idString);
			return mapping.findForward("toGetpasswd");
		}
		try{
			if(submitCheck(request, "getpwsubmit")){
				if (newpasswd1.equals("")) {
					request.setAttribute("errorInfo", getMessage(request, "register_profile_passwd_illegal"));
					return mapping.findForward("showMessage");
				} else {
					newpasswd1 = Md5Token.getInstance().getLongToken(Md5Token.getInstance().getLongToken(newpasswd1)+member.get("salt"));
					dataBaseService.runQuery("UPDATE jrun_members SET password='"+newpasswd1+"' WHERE uid='"+uid+"'", false);
					dataBaseService.runQuery("UPDATE jrun_memberfields SET authstr='' WHERE uid='"+uid+"'", false);
					request.setAttribute("resultInfo", getMessage(request, "getpasswd_succeed"));
					return mapping.findForward("showMessage");
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("resultInfo",getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	@SuppressWarnings("unchecked")
	public ActionForward switchstatus(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String,String> usergroups=(Map<String,String>)request.getAttribute("usergroups");
		String allowinvisible = usergroups != null&&usergroups.size()>0 ? usergroups.get("allowinvisible") : "0";
		if ("0".equals(allowinvisible)) {
			request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		Members member = (Members) session.getAttribute("user");
		member.setInvisible((byte)(member.getInvisible() == 1 ?0 : 1));
		memberService.modifyMember(member);
		session.setAttribute("user", member);
		Common.setResponseHeader(response);
		try {
			Writer writer = response.getWriter();
			if (member.getInvisible() == 1) {
				writer.write("<a href='member.jsp?action=switchstatus' title='"+getMessage(request, "login_switch_normal_mode")+"' ajaxtarget='loginstatus'>"+getMessage(request, "login_invisible_mode")+"</a>");
			} else {
				writer.write("<a href='member.jsp?action=switchstatus' title='"+getMessage(request, "login_switch_invisible_mode")+"' ajaxtarget='loginstatus'>"+getMessage(request, "login_normal_mode")+"</a>");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward markread(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		if (jsprun_uid>0) {
			int timestamp = (Integer)(request.getAttribute("timestamp"));
			dataBaseService.runQuery("UPDATE jrun_members SET lastvisit='"+timestamp+"' WHERE uid='"+jsprun_uid+"'");
			session.setAttribute("user", memberService.findMemberById(jsprun_uid));
		}
		Common.setResponseHeader(response);
		try {
			Writer writer = response.getWriter();
			writer.write(getMessage(request, "mark_read_succeed"));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward credits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int fid = Common.intval(request.getParameter("fid"));
		Map<String, String> settings = ForumInit.settings;
		String extcredits = settings.get("extcredits");
		if (extcredits.equals("")) {
			request.setAttribute("errorInfo", getMessage(request, "credits_disabled"));
			return mapping.findForward("showMessage");
		}
		String creditspolicy = settings.get("creditspolicy");
		Map creditspolicymap = dataParse.characterParse(creditspolicy,false);
		Map extcreditsmap = dataParse.characterParse(extcredits,true);
		List<Map<String, String>> forumsfileds = dataBaseService.executeQuery("select * from jrun_forumfields as f where f.fid=" + fid);
		String[] operation = new String[] { "post", "reply", "digest",	"postattach", "getattach" };
		String[] operationname = new String[] {getMessage(request, "credits_policy_post"), getMessage(request, "a_forum_edit_perm_reply"), getMessage(request, "DIG"), getMessage(request, "credits_policy_postattach"),getMessage(request, "credits_policy_getattach")};
		int size = 9;
		List resutl = new ArrayList();
		for (int i = 0; i < operation.length; i++) {
			if (creditspolicymap != null) {
				Iterator extit = extcreditsmap.keySet().iterator();
				Map creditpoli = (Map) creditspolicymap.get(operation[i]);
				if (creditpoli != null) {
					String[] creditresult = new String[size];
					creditresult[0] = operationname[i];
					while (extit.hasNext()) {
						String unit = "";
						Object key = extit.next();
						Map extmap = (Map) extcreditsmap.get(key);
							if (extmap.get("unit") != null) {
								unit = extmap.get("unit").toString();
							}
							if (creditpoli.get(key) != null) {
								if (creditpoli.get(key).toString().equals("0")) {
									creditresult[convertInt(key.toString())] = creditpoli.get(key).toString();
								} else {
									if (i == 4) {
										creditresult[convertInt(key.toString())] = "-" 	+ creditpoli.get(key) .toString() + unit;
									} else {
										creditresult[convertInt(key.toString())] = "+" + creditpoli.get(key) .toString() + unit;
									}
								}
							} else {
								creditresult[convertInt(key.toString())] = "N/A";
							}
					}
					resutl.add(creditresult);
				} else {
					String[] creditresult = new String[size];
					creditresult[0] = operationname[i];
					while (extit.hasNext()) {
						Object key = extit.next();
						creditresult[convertInt(key.toString())] = "N/A";
					}
					resutl.add(creditresult);
				}
			}
			String operationcredits = "";
			if (forumsfileds!=null&&forumsfileds.size() > 0) {
				Map<String, String> forumsfildmap = forumsfileds.get(0);
				operationcredits = forumsfildmap.get(operation[i] + "credits");
			}
			if (!operationcredits.equals("")) {
				Iterator extit = extcreditsmap.keySet().iterator();
				Map creditforum = dataParse.characterParse(operationcredits,false);
				String[] creditresult = new String[size];
				creditresult[0] = getMessage(request, "credits_forum") + operationname[i];
				while (extit.hasNext()) {
					String unit = "";
					Object key = extit.next();
					Map extmap = (Map) extcreditsmap.get(key);
					if (extmap.get("unit") != null) {
						unit = extmap.get("unit").toString();
					}
					if (creditforum.get(key) != null) {
						if (creditforum.get(key).toString().equals("0")) {
							creditresult[convertInt(key.toString())] = creditforum .get(key).toString();
						} else {
							if (i == 4) {
								creditresult[convertInt(key.toString())] = "-" + creditforum.get(key).toString() + unit;
							} else {
								creditresult[convertInt(key.toString())] = "+" + creditforum.get(key).toString() + unit;
							}
						}
					} else {
						creditresult[convertInt(key.toString())] = "N/A";
					}
				}
				resutl.add(creditresult);
			} else {
				Iterator extit = extcreditsmap.keySet().iterator();
				String[] creditresult = new String[size];
				creditresult[0] = getMessage(request, "credits_forum") + operationname[i];
				while (extit.hasNext()) {
					Object key = extit.next();
					creditresult[convertInt(key.toString())] = "N/A";
				}
				resutl.add(creditresult);
			}
		}
		String[] operationother = new String[] { "pm", "search", "promotion_visit", "promotion_register", "tradefinished", "votepoll", "lowerlimit" };
		String[] operationnameother = new String[] {getMessage(request, "send_pm"), getMessage(request, "search"), getMessage(request, "credits_policy_promotion_visit"), getMessage(request, "credits_policy_promotion_register"), getMessage(request, "a_setting_credits_policy_trade2"), getMessage(request, "a_setting_credits_policy_poll2"), getMessage(request, "credits_policy_lowerlimit")};
		for (int i = 0; i < operationother.length; i++) {
			Iterator extit = extcreditsmap.keySet().iterator();
			if (creditspolicymap != null) {
				Map creditpoli = (Map) creditspolicymap.get(operationother[i]);
				if (creditpoli != null) {
					String[] creditresult = new String[size];
					creditresult[0] = operationnameother[i];
					while (extit.hasNext()) {
						String unit = "";
						Object key = extit.next();
						Map extmap = (Map) extcreditsmap.get(key);
							if (extmap.get("unit") != null) {
								unit = extmap.get("unit").toString();
							}
							if (creditpoli.get(key) != null) {
								if (creditpoli.get(key).toString().equals("0")) {
									creditresult[convertInt(key.toString())] = creditpoli.get(key).toString();
								} else {
									if (i == 0 || i == 1) {
										creditresult[convertInt(key.toString())] = "-" + creditpoli.get(key).toString() + unit;
									} else {
										creditresult[convertInt(key.toString())] = "+" + creditpoli.get(key).toString() + unit;
									}
								}
							} else {
								creditresult[convertInt(key.toString())] = "N/A";
							}
					}
					resutl.add(creditresult);
				}
			}
		}
		request.setAttribute("result", resutl);
		request.setAttribute("extmap", extcreditsmap);
		return mapping.findForward("credits");
	}
	@SuppressWarnings("unchecked")
	public ActionForward clearcookies(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		CookieUtil.clearCookies(request, response,settings);
		return mapping.findForward("toIndex");
	}
	private int convertInt(String s) {
		int count = 0;
		try {
			count = Integer.valueOf(s);
		} catch (Exception e) {
		}
		return count;
	}
	public ActionForward regverify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "verifysubmit")){
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				int validateid = Common.toDigit(request.getParameter("validateid"));
				Validating validate = memberService.findValidatingById(validateid);
				String message = request.getParameter("regmessage");
				validate.setMessage(message ==null?"":message);
				validate.setStatus((byte)0);
				validate.setSubmitdate(timestamp);
				validate.setSubmittimes((short)(validate.getSubmittimes()+1));
				memberService.modifyValidating(validate);
				request.setAttribute("successInfo", getMessage(request, "submit_verify_succeed"));
				request.setAttribute("requestPath", "memcp.jsp");
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("resultInfo",getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	@SuppressWarnings("unchecked")
	public ActionForward emailverify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String,String> settings = ForumInit.settings;
		int timestamp = (Integer)request.getAttribute("timestamp");
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		List<Map<String,String>> members = dataBaseService.executeQuery("SELECT mf.authstr,m.username,m.email FROM jrun_members m, jrun_memberfields mf WHERE m.uid='"+jsprun_uid+"' AND mf.uid=m.uid AND m.groupid='8'");
		if(members==null||members.size()<=0){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action"));
			return mapping.findForward("showMessage");
		}
		if("2".equals(settings.get("regverify"))){
			request.setAttribute("errorInfo", getMessage(request, "register_verify_invalid"));
			return mapping.findForward("showMessage");
		}
		Map<String,String> member = members.get(0);
		String[] list = new String[3];
		list = member.get("authstr").split("\t");
		int type = Common.intval(list[1]);
		int dateline = Common.intval(list[0]);
		if(type==2 && timestamp - dateline < 86400){
			request.setAttribute("errorInfo", getMessage(request, "email_verify_invalid"));
			return mapping.findForward("showMessage");
		}
		String idstring = type==2 && list[2]!=null && list[2].length()>0 ? list[2]:Common.getRandStr(6, false);
		dataBaseService.runQuery("UPDATE jrun_memberfields SET authstr='"+timestamp+"\t2\t"+idstring+"' WHERE uid='"+jsprun_uid+"'",true);	
		Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
		Mail mail=new Mail(mails);
		String boardurl=(String)session.getAttribute("boardurl");
		String username = member.get("username");
		String email = member.get("email");
		mail.sendMessage(mails.get("from"),username+" <"+email+">",getMessage(request, "email_verify_subject"),getMessage(request, "email_verify_message",username,settings.get("bbname"),boardurl+"member.jsp?action=activate&uid="+jsprun_uid+"&id="+idstring,settings.get("bbname"),boardurl),null);
		request.setAttribute("resultInfo", getMessage(request, "email_verify_succeed"));
		return mapping.findForward("showMessage");
	}
}