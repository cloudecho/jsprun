package cn.jsprun.struts.foreg.actions;
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
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.Md5Token;
public class LoggingManageAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward toLogin(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings=ForumInit.settings;
		HttpSession session=request.getSession();
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		if (jsprun_uid >0 ) {
			request.setAttribute("successInfo", getMessage(request, "login_succeed_3",(String)session.getAttribute("jsprun_userss")));
			request.setAttribute("requestPath",settings.get("indexname"));
			return mapping.findForward("showMessage");
		}
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		boolean seccodecheck = (Integer.valueOf(settings.get("seccodestatus"))&2)>0;
		if (seccodecheck && loginCheck(Common.get_onlineip(request), timestamp) > 0) {
			request.setAttribute("seccodedata", dataParse.characterParse(settings.get("seccodedata"),  false));
		}
		request.setAttribute("seccodecheck", seccodecheck);
		String timeoffset = (String)session.getAttribute("timeoffset");
		String timeformat = (String)session.getAttribute("timeformat");
		String dateformat = (String)session.getAttribute("dateformat");
		int offset = timeoffset.compareTo("0");
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("thetimenow", "(GMT "+ (offset >= 0 ? ( offset == 0 ? "" : "+" + timeoffset) : timeoffset)+ ") "+Common.gmdate(dateformat+" "+timeformat,timestamp,timeoffset));
		request.setAttribute("forumStyles", dataParse.characterParse(settings.get("forumStyles"),true));
		String cookietimes = CookieUtil.getCookie(request, "cookietime", true, settings);
		int cookietime = cookietimes!=null ? Common.toDigit(cookietimes) : -1;
		request.setAttribute("cookietime", cookietime >-1 ? cookietime : 2592000);
		String referer = request.getParameter("referer");
		if(referer==null){
			referer = request.getHeader("Referer");
			referer = referer!=null ? referer.substring(referer.lastIndexOf("/")+1) : "";
		}
		request.setAttribute("referer", Common.matches(referer, "(logging|register)") ? "" : referer);
		return mapping.findForward("toLogin");
	}
	@SuppressWarnings("unchecked")
	public ActionForward login(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings=ForumInit.settings;
		HttpSession session=request.getSession();
		Md5Token md5 = Md5Token.getInstance();
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		boolean isfastsuccess = Common.isshowsuccess(session, "login_succeed");
		if (jsprun_uid>0) {
			if(isfastsuccess){
				Common.requestforward(response, settings.get("indexname"));
				return null;
			}else{
				request.setAttribute("successInfo", getMessage(request, "login_succeed_3",(String)session.getAttribute("jsprun_userss")));
				request.setAttribute("requestPath", settings.get("indexname"));
				return mapping.findForward("showMessage");
			}
		}
		try{
			if(request.getParameter("userlogin")!=null||submitCheck(request, "loginsubmit")){
				String referer = request.getParameter("referer");
				if(referer == null){
					referer = request.getHeader("Referer");
				}else if(referer.equals("")){
					referer = settings.get("indexname");
				}
				String onlineip = Common.get_onlineip(request) ;
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				int loginperm = loginCheck(onlineip, timestamp);
				if (loginperm <= 0) {
					request.setAttribute("resultInfo", getMessage(request, "login_strike"));
					return mapping.findForward("showMessage");
				}
				String styleid = request.getParameter("styleid");
				if(styleid!=null&&!styleid.equals("")){
					session.setAttribute("styleid", styleid);
				}else{
					styleid=(String)session.getAttribute("styleid");
				}
				boolean seccodecheck = (Integer.valueOf(settings.get("seccodestatus"))&2)>0;
				if (seccodecheck&& loginperm > 0) {
					request.setAttribute("seccodedata", dataParse.characterParse(settings.get("seccodedata"), false));
				}
				String seccodeverify = request.getParameter("seccodeverify");
				boolean seccodemiss = (seccodecheck&&seccodeverify==null)||(seccodecheck && seccodeverify!=null&&seccodeverify.equals("")) ? true : false;
				if (seccodecheck&&!seccodemiss) {
					if (!seccodeverify.equalsIgnoreCase((String)session.getAttribute("rand"))) {
						request.setAttribute("errorInfo", getMessage(request, "submit_seccode_invalid"));
						return mapping.findForward("showMessage");
					}
				}
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String loginauth = request.getParameter("loginauth");
				int questionid = Common.toDigit(request.getParameter("questionid"));
				String answer = request.getParameter("answer");
				String cookietime =request.getParameter("cookietime");
				String loginmode = request.getParameter("loginmode");
				String secques = Common.quescrypt(questionid, answer);
				Members member = null;
				if ("uid".equals(request.getParameter("loginfield"))) {
					member = memberService.findMemberById(Common.toDigit(username));
				} else{
					member = memberService.findByName(username);
				}
				if(loginauth!=null){
					password=loginauth;
				}else{
					password = password!=null?md5.getLongToken(md5.getLongToken(password)+(member!=null?member.getSalt():"")):"";
				}
				if (member != null && member.getPassword().equals(password)) {
					if(member.getSecques().equals(secques) && !seccodemiss){
						Short groupid = member.getGroupid();
						String jsprun_userss = member.getUsername();
						Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
						if("1".equals(usergroups.get("allowinvisible"))&&loginmode!=null&&loginmode.equals("invisible")||loginmode!=null&&loginmode.equals("normal")){
							member.setInvisible(Byte.valueOf(loginmode.equals("invisible") ? "1" : "0"));
							memberService.modifyMember(member);
						}
						styleid=styleid==null||styleid.equals("")?(member.getStyleid()!=0?member.getStyleid().toString():settings.get("styleid")):styleid;
						if(cookietime==null){
							cookietime=CookieUtil.getCookie(request, "cookietime",true,settings);
						}
						int time=Common.toDigit(cookietime);
						CookieUtil.setCookie(request, response, "uid", String.valueOf(member.getUid()), time, true,settings);
						CookieUtil.setCookie(request, response, "cookietime", String.valueOf(cookietime), 31536000, true,settings);
						CookieUtil.setCookie(request, response, "auth", Md5Token.getInstance().getLongToken(member.getPassword()+"\t"+member.getSecques()+"\t"+member.getUid()), time, true,settings);
						session.setAttribute("jsprun_uid", member.getUid());
						session.setAttribute("jsprun_userss", jsprun_userss);
						session.setAttribute("jsprun_groupid", groupid);
						session.setAttribute("jsprun_adminid", member.getAdminid());
						session.setAttribute("jsprun_pw", member.getPassword());
						session.setAttribute("formhash", Common.getRandStr(8,false));
						session.setAttribute("user", member);
						session.setAttribute("styleid",styleid);
						request.setAttribute("refresh", "true");
						Common.setDateformat(session, settings);
						request.setAttribute("sessionexists", false);
						if(settings.get("passport_status").equals("shopex")&&!settings.get("passport_shopex").equals("0")){
							if(groupid==8){
								request.setAttribute("successInfo", getMessage(request, "login_succeed_inactive_member",jsprun_userss));
								request.setAttribute("requestPath", "memcp.jsp");
								return mapping.findForward("showMessage");
							}else{
								if(isfastsuccess){
									Common.updatesession(request,settings);
									Common.requestforward(response,referer);
									return null;
								}else{
									request.setAttribute("successInfo", getMessage(request, "login_succeed_3",jsprun_userss));
									request.setAttribute("requestPath",referer);
									return mapping.findForward("showMessage");
								}
							}
						}else{
							if(groupid==8){
								request.setAttribute("successInfo", getMessage(request, "login_succeed_inactive_member",jsprun_userss));
								request.setAttribute("requestPath", "memcp.jsp");
								return mapping.findForward("showMessage");
							}
							else{
								if(isfastsuccess){
									Common.updatesession(request,settings);
									Common.requestforward(response,referer);
									return null;
								}else{
									request.setAttribute("successInfo", getMessage(request, "login_succeed_3",jsprun_userss));
									request.setAttribute("requestPath",referer);
									return mapping.findForward("showMessage");
								}
							}
						}
					}else if(secques==null||secques.equals("")||seccodemiss){
						if(!member.getSecques().equals("")){
							request.setAttribute("login_secques", getMessage(request, "login_secques"));
						}
						request.setAttribute("username", member.getUsername());
						request.setAttribute("cookietime", cookietime);
						request.setAttribute("loginmode", loginmode);
						request.setAttribute("styleid", styleid);
						request.setAttribute("loginauth", member.getPassword());
						request.setAttribute("seccodecheck", seccodecheck);	
						return mapping.findForward("toLogin_secques");
					}
				}
				Log.writelog("illegallog", timestamp, timestamp+"\t"+(member!=null?member.getUsername():username)+"\t"+password+"\t"+(secques!=null&&!secques.equals("")?"Ques #"+questionid:"")+"\t"+onlineip);
				loginFailed(loginperm,onlineip,timestamp);
				request.setAttribute("successInfo", getMessage(request, "login_invalid"));
				request.setAttribute("requestPath", "logging.jsp?action=login&referer="+referer);
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		Common.requestforward(response, "logging.jsp?action=login");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward logout(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		Map<String,String> settings=ForumInit.settings;;
		if(request.getParameter("formhash").equals(session.getAttribute("formhash"))){
			CookieUtil.clearCookies(request, response,settings);
			session.setAttribute("styleid", settings.get("styleid"));
		}
		session.removeAttribute("members");
		String referer=request.getParameter("referer");
		if(referer==null){
			referer=request.getHeader("Referer");
		}
		if(referer==null||referer.length()==0||referer.equals("null")){
			referer=settings.get("indexname");
		}
		if(Common.isshowsuccess(session, "logout_succeed")){
			Common.requestforward(response, referer);
			return null;
		}else{
			request.setAttribute("successInfo", getMessage(request, "logout_succeed"));
			request.setAttribute("requestPath", referer);
			return mapping.findForward("showMessage");
		}
	}
	private void loginFailed(int permission,String onlineip,int timestamp){
		switch(permission){
			case 1:
				dataBaseService.runQuery("REPLACE INTO jrun_failedlogins (ip, count, lastupdate) VALUES ('"+onlineip+"', '1', '"+timestamp+"')",true);
				break;
			case 2:
				dataBaseService.runQuery("UPDATE jrun_failedlogins SET count=count+1, lastupdate='"+timestamp+"' WHERE ip='"+onlineip+"'",true);
				break;
			case 3:
				dataBaseService.runQuery("UPDATE jrun_failedlogins SET count='1', lastupdate='"+timestamp+"' WHERE ip='"+onlineip+"'",true);
				dataBaseService.runQuery("DELETE FROM jrun_failedlogins WHERE lastupdate<"+(timestamp-901),true);
				break;
		}
	}
	private int loginCheck(String onlineip, int timestamp) {
		List<Map<String,String>> failedlogins=dataBaseService.executeQuery("SELECT count, lastupdate FROM jrun_failedlogins WHERE ip='"+onlineip+"'");
		if (failedlogins != null&&failedlogins.size()>0) {
			Map<String,String> failedlogin=failedlogins.get(0);
			if (timestamp - Integer.parseInt(failedlogin.get("lastupdate")) > 900) {
				return 3;
			} else if (Integer.parseInt(failedlogin.get("count")) < 5) {
				return 2;
			} else {
				return 0;
			}
		} else {
			return 1;
		}
	}
}