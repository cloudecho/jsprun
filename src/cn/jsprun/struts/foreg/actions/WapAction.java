package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Posts;
import cn.jsprun.domain.Searchindex;
import cn.jsprun.domain.Threads;
import cn.jsprun.foreg.vo.wap.Forums_threadsVO;
import cn.jsprun.foreg.vo.wap.HomeVO;
import cn.jsprun.foreg.vo.wap.LoginVO;
import cn.jsprun.foreg.vo.wap.MyCollectionVO;
import cn.jsprun.foreg.vo.wap.MyVO;
import cn.jsprun.foreg.vo.wap.NewReplyVO;
import cn.jsprun.foreg.vo.wap.NewThreadVO;
import cn.jsprun.foreg.vo.wap.PmListVO;
import cn.jsprun.foreg.vo.wap.PmSendVO;
import cn.jsprun.foreg.vo.wap.PmVO;
import cn.jsprun.foreg.vo.wap.PmViewVO;
import cn.jsprun.foreg.vo.wap.RegisterVO;
import cn.jsprun.foreg.vo.wap.SearchResultVO;
import cn.jsprun.foreg.vo.wap.SearchVO;
import cn.jsprun.foreg.vo.wap.StatsVO;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.Mail;
import cn.jsprun.utils.Md5Token;
public class WapAction extends BaseAction {
	private static final String tablePre = "jrun_";
	public ActionForward home(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Members memberInSession = (Members)session.getAttribute("user");
		String formhashInSession = (String)session.getAttribute("formhash");
		String sid = session.getId();
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, memberInSession, formhashInSession,sid,resources,locale)){
			return null;
		}
		Map<String,String> userGroupMap = (Map<String,String>)request.getAttribute("usergroups");
		request.setAttribute("jsprun_action", "191");
		HomeVO homeVO = wapService.getHomeVO(request, settingMap, memberInSession, userGroupMap.get("allowsearch"), formhashInSession,sid);
		request.setAttribute("valueObject", homeVO);
		return mapping.findForward("home");
	}
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Members memberInSession = (Members)session.getAttribute("user");
		String formhashInSession = (String)session.getAttribute("formhash");
		String sid = session.getId();
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, memberInSession, formhashInSession,sid,resources,locale)){
			return null;
		}
		String logout = request.getParameter("logout");
		if(logout == null){
			String username = request.getParameter("username");
			if(username == null || username.equals("")){
				LoginVO loginVO = wapService.getLoginVO(request, settingMap, null, formhashInSession, sid,false,"",null);
				request.setAttribute("valueObject", loginVO);
				return mapping.findForward("login");
			}else{
				String onlineIp = Common.get_onlineip(request);
				int timestamp = (Integer)request.getAttribute("timestamp");
				if(!wapService.loginCheck(onlineIp, timestamp)){
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "login_strike"), null, settingMap.get("bbname"), memberInSession, "login", formhashInSession, sid);
					return null;
				}
				String password = request.getParameter("password");
				String answer = request.getParameter("answer");
				String questionid = request.getParameter("questionid");
				if(questionid == null){
					return mapping.findForward("accessdenied");
				}
				String secques = Common.quescrypt(Integer.parseInt(questionid), answer);
				String loginauth = request.getParameter("loginauth");
				String md5_password = "";
				Md5Token md5 = Md5Token.getInstance();
				if(loginauth!=null&&!loginauth.equals("")){
					String[] loginauthArray = Common.authcode(loginauth, "DECODE", md5.getLongToken(settingMap.get("authkey")),null).split("\t");
					username = loginauthArray[0].replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'").trim();
					md5_password = loginauthArray[1].replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'").trim();
				}
				Members member = null;
				if (username.matches("^\\d+$")) {
					member = memberService.findMemberById(Integer.parseInt(username));
					if(member == null){
						member = memberService.findByName(username);
					}
				}else{
					member = memberService.findByName(username);
				}
				boolean failedLogin = true;
				if(member != null){
					String jsprun_uid = member.getUid().toString();
					String jsprun_pw = member.getPassword();
					if(loginauth==null){
						md5_password = md5.getLongToken(md5.getLongToken(password)+member.getSalt());
					}
					if(jsprun_uid!=null&&!jsprun_uid.equals("")&&!jsprun_uid.equals("0")&&md5_password.equals(jsprun_pw)){
						failedLogin = false;
						String jsprun_secques = member.getSecques();
						if(jsprun_secques!=null&&!jsprun_secques.equals(secques)){
							loginauth = Common.authcode(member.getUsername()+"\t"+jsprun_pw, "ENCODE", md5.getLongToken(settingMap.get("authkey")),null);
							LoginVO loginVO = wapService.getLoginVO(request, settingMap, null, formhashInSession, sid,true,member.getUsername(),loginauth);
							request.setAttribute("valueObject", loginVO);
							return mapping.findForward("login");
						}else{
							memberInSession = member;
							CookieUtil.setCookie(request, response, "uid", String.valueOf(memberInSession.getUid()), 2592000, true,settingMap);
							CookieUtil.setCookie(request, response, "cookietime", String.valueOf(2592000), 31536000, true,settingMap);
							CookieUtil.setCookie(request, response, "auth", Md5Token.getInstance().getLongToken(memberInSession.getPassword()+"\t"+memberInSession.getSecques()+"\t"+memberInSession.getUid()), 2592000, true,settingMap);
							session.setAttribute("jsprun_uid", memberInSession.getUid());
							session.setAttribute("jsprun_userss", memberInSession.getUsername());
							session.setAttribute("jsprun_groupid", memberInSession.getGroupid());
							session.setAttribute("jsprun_adminid", memberInSession.getAdminid());
							session.setAttribute("jsprun_pw", memberInSession.getPassword());
							session.setAttribute("formhash", Common.getRandStr(8,false));
							session.setAttribute("user", memberInSession);
							Common.setDateformat(session, settingMap);
							String stytleId = memberInSession.getStyleid()!=0?memberInSession.getStyleid().toString():settingMap.get("styleid");
							session.setAttribute("styleid",stytleId);
							CookieUtil.setCookie(request, response, "auth", Common.authcode(jsprun_pw+"\t"+jsprun_secques+"\t"+jsprun_uid, "ENCODE", md5.getLongToken(settingMap.get("authkey")),null), 2592000, true, settingMap);
							wapService.forwardToMessage(request, response, settingMap,getMessage(request, "login_succeed_2", member.getUsername()), null, settingMap.get("bbname"), memberInSession, "login", formhashInSession, sid);
							return null;
						}
					}
					if(failedLogin){
						String errorlog = Common.htmlspecialchars(timestamp+"\t"+(member.getUsername()!=null ? member.getUsername() : stripslashes(username))+"\t"+
								password+"\t"+(secques!=null&&!secques.equals("")?"Ques #"+Integer.parseInt(questionid):"")+"\t"+onlineIp);
						Log.writelog("illegallog", timestamp, errorlog);
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "login_invalid_wap"), null, settingMap.get("bbname"), memberInSession, "login", formhashInSession, sid);
						return null;
					}
				}else{
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "login_invalid_wap"), null, settingMap.get("bbname"), memberInSession, "login", formhashInSession, sid);
					return null;
				}
			}
		}else{
			String formhashInRequest = request.getParameter("formhash");
			if(formhashInRequest!=null&&formhashInRequest.equals(formhashInSession)){
				session.removeAttribute("jsprun_sid");
				session.setAttribute("jsprun_uid", 0);
				session.setAttribute("jsprun_userss", "");
				session.setAttribute("jsprun_pw", "");
				session.removeAttribute("user");
				session.setAttribute("jsprun_groupid",(short)7);
				session.setAttribute("jsprun_adminid",(byte)0);
				session.setAttribute("styleid", settingMap.get("styleid"));
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "logout_succeed_2"), null, settingMap.get("bbname"), memberInSession, "login", formhashInSession, sid);
				return null;
			}
		}
		return null;
	}
	public ActionForward register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		int timestamp = (Integer)request.getAttribute("timestamp");
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		HttpSession session = request.getSession();
		String sid = session.getId();
		Members memberInSession = (Members)session.getAttribute("user");
		String formhashInSession = (String)session.getAttribute("formhash");
		Integer jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, memberInSession, formhashInSession,sid,resources,locale)){
			return null;
		}
		String wapregister = settingMap.get("wapregister");
		if(wapregister==null||wapregister.equals("")||wapregister.equals("0")){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "register_disable_wap"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
			return null;
		}
		if(jsprun_uid!=null&&jsprun_uid>0){
			Map<String,String> forwardMap = new HashMap<String, String>();
			forwardMap.put("link", "index.jsp");
			forwardMap.put("title", getMessage(request, "return"));
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "login_succeed_2",memberInSession.getUsername()), forwardMap, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
			return null;
		}
		String[] initcredits=settingMap.get("initcredits").split(",");
		int credits = Integer.valueOf(initcredits[0]);
		int initcredit1=Integer.valueOf(initcredits[1]);
		int initcredit2=Integer.valueOf(initcredits[2]);
		int initcredit3=Integer.valueOf(initcredits[3]);
		int initcredit4=Integer.valueOf(initcredits[4]);
		int initcredit5=Integer.valueOf(initcredits[5]);
		int initcredit6=Integer.valueOf(initcredits[6]);
		int initcredit7=Integer.valueOf(initcredits[7]);
		int initcredit8=Integer.valueOf(initcredits[8]);
		int regverify=Integer.valueOf(settingMap.get("regverify"));
		Map<String,String> groupinfo=dataBaseService.executeQuery("SELECT groupid, allownickname, allowcstatus, allowavatar, allowcusbbcode, allowsigbbcode, allowsigimgcode, maxsigsize FROM jrun_usergroups WHERE "+ (regverify >0 ? "groupid='8'" : "creditshigher <= "+ credits + " AND " + credits+ "< creditslower LIMIT 1")).get(0);
		String username = request.getParameter("username");
		if(username==null||username.equals("")){
			RegisterVO registerVO = wapService.getRegisterVO(request, settingMap, memberInSession, formhashInSession, sid);
			request.setAttribute("valueObject", registerVO);
			return mapping.findForward("register");
		}else{
			String email = request.getParameter("email");
			email = email!=null?email.trim():"";
			username = username.trim();
			if(username.length()>15){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "a_member_add_toolong"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
				return null;
			}
			if(username.length()<3){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "a_member_add_tooshort"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
				return null;
			}
			String censoruser=settingMap.get("censoruser");
			if (Common.censoruser(username, censoruser)) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "profile_username_illegal"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
				return null;
			}
			String password = request.getParameter("password");
			if(password == null||password.equals("")||password.contains("'")||password.contains("\"")||password.contains("\\")){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "profile_passwd_illegal"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
				return null;
			}
			String accessemail=settingMap.get("accessemail");
			String censoremail=settingMap.get("censoremail");
			boolean invalidemail = !accessemail.equals("") ? !Common.matches(email,"(" + accessemail.replaceAll("\r\n", "1")+ ")$") : !censoremail.equals("")&& Common.matches(email, "(" + censoremail.replaceAll("\r\n", "1")+ ")$");
			if (!Common.isEmail(email) || invalidemail) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "profile_email_illegal"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
				return null;
			}
			String onlineIp = Common.get_onlineip(request);
			String ipregctrl =settingMap.get("ipregctrl");
			String ctrlip = "";
			int regctrl = Integer.valueOf(settingMap.get("regctrl"));
			if (!"".equals(ipregctrl)) {
				String[] ipregctrls = ipregctrl.split("\n");
				for (String obj : ipregctrls) {
					if (Common.matches(onlineIp, "^(" + obj + ")")) {
						ctrlip = obj + "%";
						regctrl = 72;
						break;
					} else {
						ctrlip = onlineIp;
					}
				}
				ipregctrls=null;
			} else {
				ctrlip = onlineIp;
			}
			if (regctrl > 0) {
				List<Map<String,String>> regips=dataBaseService.executeQuery("SELECT ip FROM jrun_regips WHERE ip LIKE '"+ ctrlip+ "' AND count='-1' AND dateline>"+(timestamp - regctrl * 3600)+" LIMIT 1");
				if (regips != null && regips.size() > 0) {
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "register_ctrl",String.valueOf(regctrl)), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
					return null;
				}
			}
			List<Map<String,String>> user=dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE username=?",username);
			if (user != null&&user.size()>0) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "profile_username_duplicate"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
				return null;
			}
			if (Integer.valueOf(settingMap.get("doublee"))==0) {
				List<Map<String,String>> members=dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE email=? LIMIT 1",email);
				if (members != null && members.size() > 0) {
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "profile_email_duplicate"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
					return null;
				}
			}
			int regfloodctrl = Integer.valueOf(settingMap.get("regfloodctrl"));
			if(regfloodctrl>0){
				List<Map<String,String>> regips=dataBaseService.executeQuery("SELECT count FROM jrun_regips WHERE ip=? AND count>'0' AND dateline>"+(timestamp-86400),onlineIp);
				if(regips!=null&&regips.size()>0){
					Map<String,String> regip=regips.get(0);
					if(Integer.valueOf(regip.get("count"))>=regfloodctrl){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "register_flood_ctrl",String.valueOf(regfloodctrl)), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
						return null;
					}else{
						dataBaseService.execute("UPDATE jrun_regips SET count=count+1 WHERE ip=? AND count>'0'",onlineIp);
					}
				}
				else{
					dataBaseService.execute("INSERT INTO jrun_regips (ip, count, dateline) VALUES (?, '1', '"+timestamp+"')",onlineIp);
				}
			}
			byte sigstatus = 0;
			String idstring =Common.getRandStr(6,false);
			String authstr = regverify==1 ? timestamp + "\t2\t" +idstring: "";
			Members member = new Members();
			member = (Members) setValues(member, request);
			member.setUsername(username);
			password = Md5Token.getInstance().getLongToken(password);
			int salt = Common.rand(100000, 999999);
			password = Md5Token.getInstance().getLongToken(password+salt);
			member.setPassword(password);
			member.setSecques("");
			member.setAdminid((byte)0);
			member.setGroupid(Short.valueOf(groupinfo.get("groupid")));
			member.setRegdate(timestamp);
			member.setRegip(onlineIp);
			member.setLastvisit(timestamp);
			member.setLastactivity(timestamp);
			member.setPosts(0);
			member.setCredits(credits);
			member.setSigstatus(sigstatus);
			member.setExtcredits1(initcredit1);
			member.setExtcredits2(initcredit2);
			member.setExtcredits3(initcredit3);
			member.setExtcredits4(initcredit4);
			member.setExtcredits5(initcredit5);
			member.setExtcredits6(initcredit6);
			member.setExtcredits7(initcredit7);
			member.setExtcredits8(initcredit8);
			member.setSalt(salt+"");
			member.setTimeoffset("9999");
			memberService.insertMember(member);
			int uid = member.getUid();
			dataBaseService.runQuery("INSERT INTO jrun_memberfields (uid,bio,sightml,ignorepm,groupterms,authstr,spacename) VALUES ('"+uid+"','','','','','"+authstr+"','')",true);
			if (regverify==2) {
				dataBaseService.runQuery("REPLACE INTO jrun_validating (uid, submitdate, moddate, admin, submittimes, status, message, remark) VALUES ('"+uid+"', '"+timestamp+"', '0', '', '1', '0', '', '')",true);
			}
			request.setAttribute("sessionexists", false);
			session.setAttribute("jsprun_uid", member.getUid());
			session.setAttribute("jsprun_userss", member.getUsername());
			session.setAttribute("jsprun_groupid", member.getGroupid());
			session.setAttribute("jsprun_adminid", member.getAdminid());
			session.setAttribute("jsprun_pw", member.getPassword());
			session.setAttribute("user", memberService.findMemberById(member.getUid()));
			session.setAttribute("formhash", Common.getRandStr(8,false));
			request.setAttribute("refresh", "true");
			Map<String,String> map = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_members").get(0);
			settingMap.put("totalmembers", map!=null?map.get("count"):"0");
			List<Map<String,String>> lastMember=dataBaseService.executeQuery("SELECT username FROM jrun_members ORDER BY uid DESC LIMIT 1");
			map=lastMember!=null&&lastMember.size()>0?lastMember.get(0):null;
			lastMember=null;
			settingMap.put("lastmember", map!=null?map.get("username").replace("\\", "\\\\"):"");
			if (regverify==1) {
				Map<String,String> mails=dataParse.characterParse(settingMap.get("mail"), false);
				Mail mail=new Mail(mails);
				String boardurl=(String)session.getAttribute("boardurl");
				mail.sendMessage(mails.get("from"),username+" <"+email+">",getMessage(request, "email_verify_subject"),getMessage(request, "email_verify_message",username,settingMap.get("bbname"),boardurl+"member.jsp?action=activate&uid="+uid+"&id="+idstring,settingMap.get("bbname"),boardurl),null);
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "profile_email_verify"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
				return null;
			} else if (regverify==2) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "register_manual_verify"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
				return null;
			} else {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "register_succeed"), null, settingMap.get("bbname"), memberInSession, "register", formhashInSession, sid);
				return null;
			}
		}
	}
	public ActionForward forum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("jsprun_action", "191");
		HttpSession session = request.getSession();
		Members currentMember = (Members)session.getAttribute("user");
		String formhashInSession = (String)session.getAttribute("formhash");
		short groupid=(Short)session.getAttribute("jsprun_groupid");
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, currentMember, formhashInSession,session.getId(),resources,locale)){
			return null;
		}
		String pageString = request.getParameter("page");
		String fidString = request.getParameter("fid");
		short fid = fidString!=null?Short.parseShort(fidString):0;
		if(fid!=0){
			Map<String,String> forumsCache = (Map<String,String>)request.getAttribute("forums");
			String forumsString = forumsCache.get("forums");
			DataParse dataParse= (DataParse)BeanFactory.getBean("dataParse");
			Map<String,Map<String,String>> forums = dataParse.characterParse(forumsString, false);
			Map<String,String> forum = null;
			if(forums==null||forums.size()==0){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "forum_nonexistence_2"), null, settingMap.get("bbname"), currentMember, "forum", formhashInSession, session.getId());
				return null;
			}else{
				forum = forums.get(fid+"");
				if(forum==null){
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "forum_nonexistence_2"), null, settingMap.get("bbname"), currentMember, "forum", formhashInSession, session.getId());
					return null;
				}
			}
			String  viewperm = forum.get("viewperm");
			String extgroupid=currentMember!=null?currentMember.getExtgroupids():null;
			if(!viewperm.equals("")&&!Common.forumperm(viewperm, groupid, extgroupid)){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "forum_nopermission"), null, settingMap.get("bbname"), currentMember, "forum", formhashInSession, session.getId());
				return null;
			}
			String dow = request.getParameter("do");
			Map<String,String> groupCache = (Map<String,String>)request.getAttribute("usergroups");
			Forums_threadsVO forums_threadsVO = wapService.getPartOfT(request, response, pageString, dow, fid, groupid, settingMap, forums, groupCache, currentMember, (String)session.getAttribute("formhash"), session.getId(),resources,locale);
			request.setAttribute("valueObject", forums_threadsVO);
		}else{
			int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
			Forums_threadsVO forums_threadsVO = wapService.getPartOfF(request, response, pageString, settingMap, currentMember, jsprun_uid, groupid, (String)session.getAttribute("formhash"), session.getId(),resources,locale,(String)session.getAttribute("timeoffset"));
			request.setAttribute("valueObject", forums_threadsVO);
		}
		return mapping.findForward("showFOT");
	}
	public ActionForward thread(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("jsprun_action", "193");
		HttpSession session = request.getSession();
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> groupCache = (Map<String,String>)request.getAttribute("usergroups");
		Members currentMember = (Members)session.getAttribute("user");
		String formhashInSession = (String)session.getAttribute("formhash");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, currentMember, formhashInSession,session.getId(),resources,locale)){
			return null;
		}
		String timeoffset=(String)session.getAttribute("timeoffset");
		String tid = request.getParameter("tid");
		String pid = request.getParameter("pid");
		String pageString = request.getParameter("page");
		String offsetString = request.getParameter("offset");
		String startString = request.getParameter("start");
		int offset = offsetString == null ? 0 : Integer.parseInt(offsetString);
		int start = startString == null ? 0 : Integer.parseInt(startString);
		if(tid == null){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "undefined_action"), null, settingMap.get("bbname"), currentMember, "thread", formhashInSession, session.getId());
			return null;
		}
		List<Map<String,String>> threadMapList = dataBaseService.executeQuery("SELECT * FROM "+tablePre+"threads WHERE tid=? AND displayorder>='0'",tid);
		if(threadMapList==null||threadMapList.size()==0){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "thread_nonexistence_wap"), null, settingMap.get("bbname"), currentMember, "thread", formhashInSession, session.getId());
			return null;
		}
		Map<String,String> thread = threadMapList.get(0);
		short fid = Short.parseShort(thread.get("fid"));
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		short groupId = (Short)session.getAttribute("jsprun_groupid");
		List<Map<String, String>> forumslist = dataBaseService.executeQuery("SELECT ff.modrecommend, ff.viewperm, a.allowview, ff.postperm, ff.typemodels,ff.threadtypes,ff.replyperm,ff.getattachperm,ff.password FROM  jrun_forumfields ff LEFT JOIN jrun_access a ON a.uid="+ jsprun_uid+ " AND a.fid=ff.fid WHERE ff.fid = (select fid from jrun_forums where status=1 and fid = "+thread.get("fid")+")");
		Map<String, String> forumMap = forumslist.get(0);
		forumslist = null;
		String extgroupids=currentMember!=null?currentMember.getExtgroupids():null;
		if (forumMap.get("alloview") == null) {
			if (forumMap.get("viewperm").equals("") && (groupCache==null || groupCache.get("readaccess").equals("0"))) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "thread_nopermission_wap"), null, settingMap.get("bbname"), currentMember, "thread", formhashInSession, session.getId());
				return null;
			} else if (!forumMap.get("viewperm").equals("")&& !Common.forumperm(forumMap.get("viewperm"), groupId,extgroupids)) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "thread_nopermission_wap"), null, settingMap.get("bbname"), currentMember, "thread", formhashInSession, session.getId());
				return null;
			}
		}
		if(Common.toDigit(thread.get("digest"))>0 && (groupCache==null || groupCache.get("allowviewdigest").equals("0")) && !thread.get("authorid").equals(jsprun_uid+"")){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "thread_nopermission_wap"), null, settingMap.get("bbname"), currentMember, "thread", formhashInSession, session.getId());
			return null;
		}
		if(Common.toDigit(thread.get("price"))>0 && thread.get("special").equals("0")){
			String maxchargespan = settingMap.get("maxchargespan");
			int nowtime=(Integer)request.getAttribute("timestamp");
			if(!maxchargespan.equals("0") && nowtime-Common.toDigit(thread.get("dateline"))>=Common.toDigit(maxchargespan)*3600){
				dataBaseService.execute("update jrun_threads set price=0 where tid=?",tid);
				thread.put("price","0");
			}else{
				if(Common.ismoderator(fid, currentMember) && !thread.get("authorid").equals(jsprun_uid+"")){
					List<Map<String, String>> paylog = dataBaseService.executeQuery("SELECT tid FROM jrun_paymentlog WHERE tid=? AND uid="+jsprun_uid,tid);
					if(paylog==null || paylog.size()<=0){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "thread_nopermission_wap"), null, settingMap.get("bbname"), currentMember, "thread", formhashInSession, session.getId());
						return null;
					}
				}
			}
		}
		String dow = request.getParameter("do");
		dow = dow!=null&&!dow.equals("")?dow:"";
		request.setAttribute("valueObject", wapService.getThreadVO(request, response, currentMember, thread, settingMap, formhashInSession, session.getId(), dow, pageString, offset,pid,start,groupCache,timeoffset,resources,locale));
		return mapping.findForward("thread");
	}
	public ActionForward goTo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Members currentMember = (Members)session.getAttribute("user");
		String formhashInSession = (String)session.getAttribute("formhash");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, currentMember, formhashInSession,session.getId(),resources,locale)){
			return null;
		}
		String dow = request.getParameter("do");
		String fid = request.getParameter("fid");
		String tid = request.getParameter("tid");
		request.setAttribute("jsprun_action", "194");
		if(!(dow!=null && (dow.equals("last")||dow.equals("next")))){
			dow = "";
		}
		if("last".equals(dow)){
			if(fid!=null && tid != null){
				List<Map<String,String>> threadMapList = dataBaseService.executeQuery("SELECT lastpost FROM "+tablePre+"threads WHERE tid=? AND displayorder>='0'",tid);
				if(threadMapList!=null && threadMapList.size()>0){
					Map<String,String> this_lastpostMap = threadMapList.get(0);
					String this_lastpost = this_lastpostMap.get("lastpost");
					List<Map<String,String>> threadMapList2 = dataBaseService.executeQuery("SELECT tid FROM "+tablePre+"threads WHERE fid=? AND displayorder>='0' AND lastpost>'"+this_lastpost+"' ORDER BY lastpost ASC LIMIT 1",fid);
					if(threadMapList2!=null && threadMapList2.size()>0){
						Map<String,String> threadMap2 = threadMapList2.get(0);
						String tid_last = threadMap2.get("tid");
						String redirectURL = "index.jsp?action=thread&tid="+tid_last;
						redirectURL = response.encodeRedirectURL(redirectURL);
						try {
							response.sendRedirect(redirectURL);
							return null;
						} catch (IOException e) {
							e.printStackTrace();
							return null;
						}
					}else{
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "goto_last_nonexistence"), null, settingMap.get("bbname"), currentMember, "goto", formhashInSession, session.getId());
						return null;
					}
				}else{
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "thread_no_exist_flush"), null, settingMap.get("bbname"), currentMember, "goto", formhashInSession, session.getId());
					return null;
				}
			}else{
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "undefined_action"), null, settingMap.get("bbname"), currentMember, "goto", formhashInSession, session.getId());
				return null;
			}
		}else if("next".equals(dow)){
			if(fid!=null && tid != null){
				List<Map<String,String>> threadLPMapList = dataBaseService.executeQuery("SELECT lastpost FROM "+tablePre+"threads WHERE tid=? AND displayorder>='0'",tid);
				if(threadLPMapList!=null && threadLPMapList.size()>0){
					String this_lastpost = threadLPMapList.get(0).get("lastpost");
					List<Map<String,String>> threadTidMapList = dataBaseService.executeQuery("SELECT tid FROM "+tablePre+"threads WHERE fid=? AND displayorder>='0' AND lastpost<'"+this_lastpost+"' ORDER BY lastpost DESC LIMIT 1",fid);
					if(threadTidMapList!=null && threadTidMapList.size()>0){
						String nextTid = threadTidMapList.get(0).get("tid");
						String redirectURL = "index.jsp?action=thread&tid="+nextTid;
						redirectURL = response.encodeRedirectURL(redirectURL);
						try {
							response.sendRedirect(redirectURL);
							return null;
						} catch (IOException e) {
							e.printStackTrace();
							return null;
						}
					}else{
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "goto_next_nonexistence"), null, settingMap.get("bbname"), currentMember, "goto", formhashInSession, session.getId());
						return null;
					}
				}else{
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "thread_no_exist_flush"), null, settingMap.get("bbname"), currentMember, "goto", formhashInSession, session.getId());
					return null;
				}
			}else{
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "undefined_action"), null, settingMap.get("bbname"), currentMember, "goto", formhashInSession, session.getId());
				return null;
			}
		}else if("nversio".equals(dow)){
			try {
				PrintWriter out = response.getWriter();
				out.write("&#80;&#111;&#119;&#101;&#114;&#101;&#100;&#32;&#98;&#121;&#32;&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#54;&#46;&#48;&#46;&#48;&#32;&#82;&#101;&#108;&#101;&#97;&#115;&#101;&#32;<br/>");
				out.write("&#67;&#111;&#112;&#121;&#82;&#105;&#103;&#104;&#116;&#32;&#50;&#48;&#48;&#55;&#45;&#50;&#48;&#49;&#49;&#32;&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#73;&#110;&#99;&#46;&#32;&#65;&#108;&#108;&#32;&#82;&#105;&#103;&#104;&#116;&#115;&#32;&#82;&#101;&#115;&#101;&#114;&#118;&#101;&#100;<br/><br/>");
				out.write("&#21271;&#20140;&#39134;&#36895;&#21019;&#24819;&#31185;&#25216;&#26377;&#38480;&#20844;&#21496;<br/>");
				out.write("&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#23448;&#26041;&#32593;&#31449;&#65306;&#119;&#119;&#119;&#46;&#106;&#115;&#112;&#114;&#117;&#110;&#46;&#99;&#111;&#109;<br/>");
				out.write("&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#23448;&#26041;&#35770;&#22363;&#65306;&#119;&#119;&#119;&#46;&#106;&#115;&#112;&#114;&#117;&#110;&#46;&#110;&#101;&#116;<br/>");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			String url = request.getParameter("url");
			if(url != null){
				try {
					response.sendRedirect(response.encodeRedirectURL(url));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			request.setAttribute("valueObject", wapService.getGoToVo(request, settingMap, currentMember, formhashInSession, session.getId()));
			return mapping.findForward("goto");
		}
	}
	public ActionForward post(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		int timestamp = (Integer)request.getAttribute("timestamp");
		HttpSession session = request.getSession();
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Map<String,String> userGroupMap = (Map<String,String>)request.getAttribute("usergroups");
		Integer jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		Members currentMember = (Members)session.getAttribute("user");
		String formhashInSession = (String)session.getAttribute("formhash");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, currentMember, formhashInSession,session.getId(),resources,locale)){
			return null;
		}
		Map<String,String> cacheForm = (Map<String, String>)request.getAttribute("forums");
		String fid = request.getParameter("fid");
		String forumsString = cacheForm.get("forums");
		Map<String,Map<String,String>> forums = dataParse.characterParse(forumsString, false);
		Map<String,String> currentForum = forums.get(fid);
		if(currentForum==null || currentForum.get("type").equals("group")){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "forum_nonexistence_2"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
			return null;
		}
		String  viewperm = currentForum.get("viewperm");
		short groupid=(Short)session.getAttribute("jsprun_groupid");
		String extgroupid=currentMember!=null?currentMember.getExtgroupids():null;
		if(!viewperm.equals("")&&!Common.forumperm(viewperm, groupid, extgroupid)){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "forum_nopermission"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
			return null;
		}
		String allowhidecode = userGroupMap.get("allowhidecode");
		String bbcodeoff = request.getParameter("bbcodeoff");
		String message = request.getParameter("message");
		if(message != null){
			if(bbcodeoff == null && (allowhidecode == null||allowhidecode.equals("")||allowhidecode.equals("0"))
					&&message.toLowerCase().replaceAll("(\\[code\\].*\\[\\/code\\])", "").matches("\\[hide=?\\d*\\].+?\\[\\/hide\\]")){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_hide_nopermission"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			}
			if(message.length()>20000){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_messagelength_outof_limit"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			}
		}
		if(currentMember!=null){
			byte adminid = currentMember.getAdminid();
			int newbiespan = Integer.parseInt(settingMap.get("newbiespan"));
			int lastpost = currentMember.getLastpost();
			if(adminid == 0 && newbiespan > 0 &&
					(lastpost ==0 || timestamp - lastpost < newbiespan * 3600)){
				int regdate = currentMember.getRegdate();
				if(timestamp - regdate < newbiespan * 3600){
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_newbie_span" ,String.valueOf(newbiespan)), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
			}
		}
		List<Map<String,String>> ffMapList = dataBaseService.executeQuery("SELECT postcredits ,replycredits,postperm,threadtypes,replyperm FROM "+tablePre+"forumfields WHERE fid=?",fid);
		String postperm = "";
		String threadtypesString = "";
		String replyperm = "";
		Map<Integer, Integer> postcredits = null;
		Map<Integer, Integer> replycredits = null;
		Map<String,Map<Integer,Integer>> creditspolicys = null;
		if(ffMapList != null && ffMapList.size()>0){
			Map<String,String> ffMap = ffMapList.get(0);
			postcredits = dataParse.characterParse(ffMap.get("postcredits"),false);
			replycredits = dataParse.characterParse(ffMap.get("replycredits"),false);
			postperm = ffMap.get("postperm");
			threadtypesString = ffMap.get("threadtypes");
			replyperm = ffMap.get("replyperm");
		}
		if(postcredits == null || postcredits.size()<=0){
			creditspolicys = dataParse.characterParse(settingMap.get("creditspolicy"),false);
			postcredits = creditspolicys.get("post");
		}
		if(replycredits==null||replycredits.size()<=0){
			if(creditspolicys == null){
				creditspolicys = dataParse.characterParse(settingMap.get("creditspolicy"),false);
			}
			replycredits = creditspolicys.get("reply");
		}
		String subject = request.getParameter("subject");
		boolean modnewthreads = false;
		boolean modnewreplies = false;
		subject = subject == null?"":subject;
		message = message == null?"":message;
		List<Map<String,String>> forumMapList = dataBaseService.executeQuery("SELECT modnewposts FROM "+tablePre+"forums WHERE fid=?",fid);
		int allowdirectpost = Integer.parseInt(userGroupMap.get("allowdirectpost"));
		boolean censormod = censormod(subject + "\t" + message, request);
		modnewthreads = (allowdirectpost == 0 || allowdirectpost == 1)&& ((forumMapList!=null&&forumMapList.size()>0&&Integer.parseInt(forumMapList.get(0).get("modnewposts")) >0 ) || censormod);
		modnewreplies = (allowdirectpost == 0 || allowdirectpost == 2)&& ((forumMapList!=null&&forumMapList.size()>0&&Integer.parseInt(forumMapList.get(0).get("modnewposts")) == 2 ) || censormod);
		List<Map<String,String>> wordlist = dataBaseService.executeQuery("select find,replacement from jrun_words");
		if(wordlist!=null && wordlist.size()>0){
			for(Map<String,String> word :wordlist){
				if(!message.equals("")&&Common.matches(message,word.get("find"))){
					if(word.get("replacement").equals("{BANNED}")){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "subject_invalid"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
						return null;
					}else if(word.get("replacement").equals("{MOD}")){
						modnewthreads = true;
					}else{
						message = message.replaceAll(word.get("find"),word.get("replacement"));
					}
				}
				if(!subject.equals("") && Common.matches(subject,word.get("find"))){
					if(word.get("replacement").equals("{BANNED}")){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "subject_invalid"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
						return null;
					}else if(word.get("replacement").equals("{MOD}")){
						modnewthreads = true;
					}else{
						subject = Common.htmlspecialchars(subject.replaceAll(word.get("find"),word.get("replacement")));
					}
				}
			}
		}
		String dow = request.getParameter("do");
		Map<String, String> admingroupMap = (Map<String, String>) request.getAttribute("usergroups");
		if(dow == null){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "undefined_action"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
			return null;
		}else if(dow.equals("newthread")){
			request.setAttribute("jsprun_action", "195");
			int allowpost =Integer.valueOf(userGroupMap.get("allowpost"));
			if (currentMember == null&& !((postperm.equals("") && allowpost > 0) || (!postperm.equals("") && Common.forumperm(postperm, groupid, currentMember!=null?currentMember.getExtgroupids():"")))) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_newthread_nopermission"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			} else if (postperm.equals("") && allowpost == 0) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_newthread_nopermission"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			} else if (!postperm.equals("") && !Common.forumperm(postperm, groupid, currentMember!=null?currentMember.getExtgroupids():"")) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_newthread_nopermission"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			}
			if(subject == null || subject.trim().equals("") || message ==null||message.trim().equals("")){
				NewThreadVO newThreadVO = wapService.getNewThreadVO(request, settingMap, currentMember, formhashInSession, session.getId(), fid, threadtypesString, dataParse);
				request.setAttribute("valueObject", newThreadVO);
				return mapping.findForward("newThread");
			}else{
				String post_invalid = Common.checkpost(subject, message, settingMap, admingroupMap,resources,locale);
				if (post_invalid != null) {
					wapService.forwardToMessage(request, response, settingMap, post_invalid, null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
				String formhash = request.getParameter("formhash");
				if(formhash == null || !formhash.equals(formhashInSession)){
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "wap_submit_invalid"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
				String checkflood = checkflood(jsprun_uid, timestamp, currentMember!=null?currentMember.getLastpost():0,Integer.valueOf(settingMap.get("floodctrl")), admingroupMap==null?0:Integer.valueOf(admingroupMap.get("disablepostctrl")), Integer.valueOf(userGroupMap.get("maxpostsperhour")),request);
				if (checkflood != null) {
					wapService.forwardToMessage(request, response, settingMap, checkflood, null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
				String typeidString = request.getParameter("typeid");
				int typeid = 0;
				Map threadtypesMap = dataParse.characterParse(threadtypesString, false);
				Map tempMap = null;
				if(typeidString!=null && threadtypesMap != null && (tempMap = (Map)threadtypesMap.get("types"))!=null){
					Set key = tempMap.keySet();
					Iterator iterator = key.iterator();
					while(iterator.hasNext()){
						Object object = iterator.next();
						if(object!=null && object.toString().equals(typeidString)){
							typeid = Integer.parseInt(typeidString);
						}
					}
				}
				if(typeid == 0){
					Object objectTemp = threadtypesMap.get("required");
					if(objectTemp!=null){
						String stringTemp = objectTemp.toString();
						if(!stringTemp.equals("") && !stringTemp.equals("0")){
							wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_type_isnull_wap"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
							return null;
						}
					}
				}
				int displayorder = modnewthreads ? -2 : 0;
				int pinvisible = displayorder;
				String author = currentMember!=null?currentMember.getUsername():getMessage(request, "anonymous");
				Threads thread = new Threads();
				thread.setFid(Short.parseShort(fid));
				thread.setReadperm((short)0);
				thread.setIconid((short)0);
				thread.setTypeid((short)typeid);
				thread.setAuthor(author);
				thread.setAuthorid(currentMember!=null?currentMember.getUid():0);
				thread.setSubject(subject);
				thread.setDateline(timestamp);
				thread.setLastpost(timestamp);
				thread.setLastposter(author);
				thread.setDisplayorder((byte)displayorder);
				thread.setDigest((byte)0);
				thread.setBlog((byte)0);
				thread.setSpecial((byte)0);
				thread.setAttachment(Byte.valueOf("0"));
				thread.setModerated((byte)0);
				threadsService.addThread(thread);
				int tid = thread.getTid();
				Posts post = new Posts();
				post.setFid(Short.valueOf(fid));
				post.setTid(tid);
				post.setFirst(Byte.valueOf("1"));
				post.setAuthor(author);
				post.setAuthorid(currentMember!=null?currentMember.getUid():0);
				post.setSubject(subject);
				post.setDateline(timestamp);
				post.setMessage(message);
				post.setUseip(Common.get_onlineip(request));
				post.setInvisible((byte)pinvisible);
				post.setUsesig((byte)0);
				post.setHtmlon((byte)0);
				post.setBbcodeoff((byte)0);
				post.setSmileyoff((byte)0);
				post.setParseurloff((byte)0);
				post.setAttachment((byte)0);
				postsService.saveOrupdatePosts(post);
				int pid = post.getPid();
				String sql = "REPLACE INTO "+tablePre+"mythreads (uid, tid, dateline) VALUES ('"+jsprun_uid+"', '"+tid+"', '"+timestamp+"')";
				dataBaseService.execute(sql);
				if(modnewthreads){
					Map<String,String> link_titleMap = new HashMap<String, String>();
					link_titleMap.put("link", "index.jsp?action=forum&amp;fid="+fid);
					link_titleMap.put("title", getMessage(request, "post_mod_succeed"));
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_mod_succeed"), link_titleMap, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}else{
					Common.updatepostcredits("+", jsprun_uid, postcredits, timestamp);
					Common.updatepostcredits(jsprun_uid,settingMap.get("creditsformula"));
					Common.updateMember(session, jsprun_uid);
					String lastpost = tid + "\t" + Common.htmlspecialchars(Common.cutstr(subject.replaceAll("\t", " "), 40, null)) + "\t" + timestamp + "\t"+ author;
					dataBaseService.execute("UPDATE "+tablePre+"forums SET lastpost='"+lastpost+"', threads=threads+1, posts=posts+1, todayposts=todayposts+1 WHERE fid=?",fid);
					if (currentForum.get("type").equals("sub")) {
						dataBaseService.runQuery("UPDATE jrun_forums SET lastpost='"+ Common.addslashes(lastpost) + "' WHERE fid=" + currentForum.get("fup"),true);
					}
					String temp_link = "index.jsp?action=forum&amp;fid="+fid;
					temp_link = response.encodeURL(temp_link);
					Map<String,String> link_titleMap = new HashMap<String, String>();
					link_titleMap.put("link", "index.jsp?action=thread&amp;tid="+tid);
					link_titleMap.put("title", getMessage(request, "post_newthread_forward"));
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_newthread_succeed_wap")+"<br /><a href=\""+temp_link+"\">"+getMessage(request, "space_returnboard")+"</a>", link_titleMap, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
			}
		}else if(dow.equals("reply")){
			request.setAttribute("jsprun_action", "196");
			String tid = request.getParameter("tid");
			if(tid == null){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "undefined_action"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			}
			List<Map<String,String>> threadMapList = dataBaseService.executeQuery("SELECT * FROM "+tablePre+"threads WHERE tid=?",tid);
			if(threadMapList == null || threadMapList.size()==0){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "thread_nonexistence_wap"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			}
			Map<String,String> threadMap = threadMapList.get(0);
			if(replyperm.equals("")){
				if (userGroupMap.get("allowreply").equals("0")) {
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_newreply_nopermission"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
			}else if(!Common.forumperm(replyperm, groupid, currentMember==null?"0":currentMember.getExtgroupids())){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_newreply_nopermission"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			}
			if (Integer.parseInt(threadMap.get("price")) > 0 && Integer.parseInt(threadMap.get("special")) == 0&& jsprun_uid == 0) {
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_newreply_nopermission"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			}
			boolean modertar = Common.ismoderator(Short.parseShort(threadMap.get("fid")), currentMember);
			if(Integer.parseInt(threadMap.get("closed")) == -1 && !modertar){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_thread_closed_wap"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			}
			String post_autoclose = checkautoclose(timestamp, currentForum, threadMap, modertar,request);
			if(post_autoclose!=null){
				wapService.forwardToMessage(request, response, settingMap, post_autoclose, null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
				return null;
			}
			if(message.trim().equals("")){
				NewReplyVO newReplyVO = wapService.getNewReplyVO(request, settingMap, currentMember, formhashInSession, session.getId(), fid, tid);
				request.setAttribute("valueObject", newReplyVO);
				return mapping.findForward("newReply");
			}else{
				String post_invalid = Common.checkpost(null, message, settingMap, admingroupMap,resources,locale);
				if(post_invalid !=null){
					wapService.forwardToMessage(request, response, settingMap, post_invalid, null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
				String formhash = request.getParameter("formhash");
				if(formhash == null || !formhash.equals(formhashInSession)){
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "wap_submit_invalid"), null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
				String checkflood = checkflood(jsprun_uid, timestamp, currentMember!=null?currentMember.getLastpost():0,Integer.valueOf(settingMap.get("floodctrl")), admingroupMap==null?0:Integer.valueOf(admingroupMap.get("disablepostctrl")), Integer.valueOf(userGroupMap.get("maxpostsperhour")),request);
				if (checkflood != null) {
					wapService.forwardToMessage(request, response, settingMap, checkflood, null, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
				int pinvisible = modnewreplies ? -2 : 0;
				String author = currentMember!=null?currentMember.getUsername():getMessage(request, "anonymous");
				Posts post = new Posts();
				post.setFid(Short.valueOf(fid));
				post.setTid(Integer.parseInt(tid));
				post.setFirst((byte)0);
				post.setAuthor(author);
				post.setAuthorid(jsprun_uid);
				post.setDateline(timestamp);
				post.setMessage(message);
				post.setUseip(Common.get_onlineip(request));
				post.setInvisible((byte)pinvisible);
				post.setUsesig((byte)1);
				post.setHtmlon((byte)0);
				post.setBbcodeoff((byte)0);
				post.setSmileyoff((byte)0);
				post.setParseurloff((byte)0);
				post.setAttachment((byte)0);
				postsService.saveOrupdatePosts(post);
				Integer pid = post.getPid();
				dataBaseService.execute("REPLACE INTO "+tablePre+"myposts (uid, tid, pid, position, dateline) VALUES ('"+jsprun_uid+"', ?, '"+pid+"', '"+(Integer.parseInt(threadMap.get("replies")) + 1)+"', '"+timestamp+"')",tid);
				if(modnewreplies){
					Map<String,String> link_titleMap = new HashMap<String, String>();
					link_titleMap.put("link", "index.jsp?action=forum&amp;fid="+fid);
					link_titleMap.put("title", getMessage(request, "post_mod_forward"));
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_mod_succeed"), link_titleMap, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}else{
					dataBaseService.execute("UPDATE "+tablePre+"threads SET lastposter='"+author+"', lastpost='"+timestamp+"', replies=replies+1 WHERE tid=? AND fid=?",tid,fid);
					Common.updatepostcredits("+", jsprun_uid, postcredits, timestamp);
					Common.updatepostcredits(jsprun_uid,settingMap.get("creditsformula"));
					Common.updateMember(session, jsprun_uid);
					String lastpost = tid + "\t" + Common.cutstr(threadMap.get("subject").replaceAll("\t", " "), 40, null) + "\t" + timestamp + "\t"+ author;
					dataBaseService.execute("update jrun_forums set lastpost='"+Common.addslashes(lastpost)+"',posts=posts+1, todayposts=todayposts+1 where fid=?",fid);
					if(currentForum.get("type").equals("sub")){
						dataBaseService.runQuery("update jrun_forums set lastpost='"+Common.addslashes(lastpost)+"' where fid='"+currentForum.get("fup")+"'",true);
					}
					int wapppp = Integer.parseInt(settingMap.get("wapppp"));
					String tempUrl = response.encodeURL("index.jsp?action=forum&amp;fid="+fid);
					Map<String,String> link_titleMap = new HashMap<String, String>();
					link_titleMap.put("link", "index.jsp?action=thread&amp;tid="+tid+"&amp;page="+(int)(Math.ceil((Double.valueOf(threadMap.get("replies"))+ 2) / wapppp)));
					link_titleMap.put("title", getMessage(request, "post_mod_forward"));
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_newreply_succeed")+"<br /><a href=\""+tempUrl+"\">"+getMessage(request, "space_returnboard")+"</a>", link_titleMap, settingMap.get("bbname"), currentMember, "post", formhashInSession, session.getId());
					return null;
				}
			}
		}
		return null;
	}
	public ActionForward my(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		HttpSession session = request.getSession();
		String formhashInSession = (String)session.getAttribute("formhash");
		Members currentMember = (Members)session.getAttribute("user");
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, currentMember, formhashInSession,session.getId(),resources,locale)){
			return null;
		}
		if(currentMember==null){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "wap_not_loggedin"), null, settingMap.get("bbname"), currentMember, "my", formhashInSession, session.getId());
			return null;
		}
		String uidString = request.getParameter("uid");
		int uid = uidString !=null ? Integer.parseInt(uidString) : jsprun_uid;
		String username = request.getParameter("username");
		username = username !=null ? Common.htmlspecialchars(username) : "";
		String[] unaddParameter = null;
		String usernameadd = null;
		if(uid !=0){
			usernameadd = "m.uid='"+uid+"'";
		}else{
			usernameadd = "m.username=?";
			unaddParameter = new String[]{username};
		}
		String dow = request.getParameter("do");
		if(dow==null){
			List<Map<String,String>> memberInfoMapList = dataBaseService.executeQuery("SELECT m.*, mf.* FROM "+tablePre+"members m " +
					"LEFT JOIN "+tablePre+"memberfields mf ON mf.uid=m.uid " +
					"WHERE "+usernameadd+" LIMIT 1",unaddParameter);
			if(memberInfoMapList == null || memberInfoMapList.size()<0){
				wapService.forwardToMessage(request, response, settingMap, getMessage(request, "my_nonexistence"), null, settingMap.get("bbname"), currentMember, "my", formhashInSession, session.getId());
				return null;
			}
			Map<String,String> memberMap = memberInfoMapList.get(0);
			MyVO myVO = wapService.getMyVO(request, settingMap, currentMember, formhashInSession, session.getId(), memberMap, uid, jsprun_uid, username,resources,locale);
			request.setAttribute("valueObject", myVO);
			return mapping.findForward("my");
		}else{
			if(dow.equals("fav")){
				String favid = request.getParameter("favid");
				if(favid != null){
					String type = request.getParameter("type");
					String selectid = type!=null && type.equals("thread") ? "tid" : "fid";
					List<Map<String,String>> tempMapList = dataBaseService.executeQuery("SELECT "+selectid+" FROM "+tablePre+"favorites WHERE uid='"+jsprun_uid+"' AND "+selectid+"=? LIMIT 1",favid);
					if(tempMapList!=null && tempMapList.size()>0){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "fav_existence"), null, settingMap.get("bbname"), currentMember, "my", formhashInSession, session.getId());
						return null;
					}else{
						dataBaseService.execute("INSERT INTO "+tablePre+"favorites (uid, "+selectid+") " +
								"VALUES ('"+jsprun_uid+"', ?)",favid);
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "fav_add_succeed"), null, settingMap.get("bbname"), currentMember, "my", formhashInSession, session.getId());
						return null;
					}
				}else{
					MyCollectionVO myCollectionVO = wapService.getMyCollectionVO(request, settingMap, currentMember, formhashInSession, session.getId(), jsprun_uid);
					request.setAttribute("valueObject", myCollectionVO);
					return mapping.findForward("mycollection");
				}
			}
		}
		return null;
	}
	public ActionForward myphone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("jsprun_action", "194");
		HttpSession session = request.getSession();
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Members currentMember = (Members)request.getAttribute("user");
		String formhash = (String)session.getAttribute("formhash");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, currentMember, formhash,session.getId(),resources,locale)){
			return null;
		}
		request.setAttribute("valueObject", wapService.getMyPhoneVO(request, settingMap, currentMember, formhash, session.getId()));
		return mapping.findForward("myphone");
	}
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		int cachelife_time = 300;
		int cachelife_text = 3600;
		Map<String,String> groupMap = (Map<String,String>)request.getAttribute("usergroups");
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Members currentMember = (Members)session.getAttribute("user");
		String formhashInSession = (String)session.getAttribute("formhash");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, currentMember, formhashInSession,session.getId(),resources,locale)){
			return null;
		}
		String allowsearch = groupMap.get("allowsearch");
		if(allowsearch == null || allowsearch.equals("")|| allowsearch.equals("0")){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "search_group_nopermission"), null, settingMap.get("bbname"), currentMember, "search", formhashInSession, session.getId());
			return null;
		}
		String dow = request.getParameter("do");
		if(dow == null){
			dow = "";
		}
		if(!dow.equals("submit")){
			SearchVO searchVO = wapService.getSearchVO(request, settingMap, currentMember, formhashInSession, session.getId());
			request.setAttribute("valueObject", searchVO);
			return mapping.findForward("search");
		}else{
			String searchid = request.getParameter("searchid");
			if(searchid!=null){
				String pageString = request.getParameter("page");
				int page = pageString == null || pageString.equals("") ? 1 : Math.max(1, Integer.parseInt(pageString));
				int waptpp = Integer.parseInt(settingMap.get("waptpp"));
				int start_limit = (page - 1) * waptpp;
				List<Map<String,String>> tempMapList = dataBaseService.executeQuery("SELECT searchstring, keywords, threads, tids FROM "+tablePre+"searchindex WHERE searchid=?",searchid);
				if(tempMapList == null || tempMapList.size()==0){
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "search_id_invalid"), null, settingMap.get("bbname"), currentMember, "search", formhashInSession, session.getId());
					return null;
				}
				Map<String,String> index = tempMapList.get(0);
				List<Map<String,String>> tempMapList2 = dataBaseService.executeQuery("SELECT COUNT(*) AS cnt FROM  "+tablePre+"threads WHERE tid IN ("+index.get("tids")+") AND displayorder>='0'");
				int searchnum = 0;
				if(tempMapList2!=null && tempMapList2.size()>0){
					searchnum = Integer.parseInt(tempMapList2.get(0).get("cnt"));
				}
				if(searchnum == 0){
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "search_not_find"), null, settingMap.get("bbname"), currentMember, "search", formhashInSession, session.getId());
					return null;
				}else{
					SearchResultVO searchResultVO  = wapService.getSearchResultVO(request, response, settingMap, currentMember, formhashInSession, session.getId(), index, waptpp, start_limit, page, searchnum, searchid,resources,locale);
					request.setAttribute("valueObject", searchResultVO);
					return mapping.findForward("searchresult");
				}
			}else{
				String srchtxt = request.getParameter("srchtxt");
				String srchuname = request.getParameter("srchuname");
				String srchuid = request.getParameter("srchuid");
				srchtxt = srchtxt==null?"":srchtxt.trim();
				srchuname = srchuname==null?"":srchuname.trim();
				srchuid = srchuid!=null ? srchuid : "0";
				Md5Token md5 = Md5Token.getInstance();
				String searchstring = md5.getLongToken("title|"+srchtxt+"|"+srchuid+"|"+srchuname);
				Map<String,String> searchindex = new HashMap<String, String>();
				searchindex.put("id", "0");
				searchindex.put("dateline", "0");
				String searchctrl = settingMap.get("searchctrl");
				int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
				int timestamp = (Integer)request.getAttribute("timestamp");
				String onlineip = Common.get_onlineip(request);
				List<Map<String,String>> tempMapList = dataBaseService.executeQuery("SELECT searchid, dateline, " +
						"('"+searchctrl+"'<>'0' AND "+(jsprun_uid==0 ? "useip='"+onlineip+"'" : "uid='"+jsprun_uid+"'")+" AND "+timestamp+"-dateline<"+searchctrl+") AS flood, " +
								"(searchstring=? AND expiration>'"+timestamp+"') AS indexvalid " +
								"FROM "+tablePre+"searchindex " +
								"WHERE ('"+searchctrl+"'<>'0' AND "+(jsprun_uid==0 ? "useip='"+onlineip+"'" : "uid='"+jsprun_uid+"'")+" AND "+timestamp+"-dateline<"+searchctrl+") OR (searchstring=? AND expiration>'"+timestamp+"') " +
										"ORDER BY flood",searchstring,searchstring);
				if(tempMapList!=null){
					for(Map<String,String> index : tempMapList){
						String indexvalid = index.get("indexvalid");
						String dateline = index.get("dateline");
						String searchindex_dateline = searchindex.get("dateline");
						if(indexvalid.equals("1") && Integer.parseInt(dateline) > Integer.parseInt(searchindex_dateline)){
							searchindex.put("id", index.get("searchid"));
							searchindex.put("dateline", dateline);
							break;
						}else if(index.get("flood").equals("1")){
							wapService.forwardToMessage(request, response, settingMap, getMessage(request, "search_ctrl",searchctrl), null, settingMap.get("bbname"), currentMember, "search", formhashInSession, session.getId());
							return null;
						}
					}
				}
				if(!searchindex.get("id").equals("0")){
					searchid = searchindex.get("id");
				}else{
					String srchfid = request.getParameter("srchfid");
					if(srchfid == null){
						srchfid = "all";
					}
					String srchfrom = request.getParameter("srchfrom");
					if((srchtxt==null||srchtxt.trim().equals(""))
							&&(srchuname==null||srchuname.trim().equals(""))
							&&!srchuid.equals("0")
							&&(srchfrom==null||srchfrom.trim().equals(""))){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "search_invalid"), null, settingMap.get("bbname"), currentMember, "search", formhashInSession, session.getId());
						return null;
					}
					String before = request.getParameter("before");
					String searchfrom = "";
					String sqlsrch = "";
					int expiration = 0;
					String keywords = "";
					if((srchfrom!=null&&!srchfrom.trim().equals(""))
							&&(srchtxt==null||srchtxt.trim().equals(""))
							&&srchuid.equals("0")
							&&(srchuname==null||srchuname.trim().equals(""))){
						searchfrom = before==null||before.trim().equals("")? "<=" :">=";
						searchfrom += (timestamp - Integer.parseInt(srchfrom))+"";
						sqlsrch = "FROM "+tablePre+"threads t WHERE t.displayorder>='0' AND t.lastpost"+searchfrom;
						expiration = (timestamp + cachelife_time);
					}else{
						String mytopics = request.getParameter("mytopics");
						if(mytopics!=null && !srchuid.equals("0")){
							srchfrom = 2592000+"";
							srchuname = "";
							srchtxt = "";
							before = "";
						}
						sqlsrch = "FROM "+tablePre+"threads t WHERE t.displayorder>='0'";
						if(srchuname!=null && !srchuname.trim().equals("")){
							srchuid = "0";
							String comma = "";
							srchuname = srchuname.replace("%_", "\\%_").replace("*", "%");
							List<Map<String,String>> tempMapList3 = dataBaseService.executeQuery("SELECT uid FROM "+tablePre+"members WHERE username LIKE ? LIMIT 50",srchuname.replace("_", "\\_"));
							if(tempMapList3!=null && tempMapList3.size()>0){
								for(Map<String,String> member : tempMapList3){
									srchuid += comma+"'"+member.get("uid")+",";
									comma = ",";
								}
							}
							if(!srchuid.equals("0")){
								sqlsrch += " AND 0";
							}
						}else if(!srchuid.equals("0")){
							srchuid = "'"+srchuid+"'";
						}
						String sqltxtsrch = "";
						if(srchtxt!=null && !srchtxt.equals("")){
							srchtxt = srchtxt.replace("%_", "\\%_").replace("*", "%");
							sqltxtsrch += "t.subject LIKE '%"+srchtxt+"%'";
							sqlsrch += " AND ("+sqltxtsrch+")";
						}
						if(!srchuid.equals("0")){
							sqlsrch += " AND authorid IN ("+srchuid+")";
						}
						if(!srchfid.equals("all") && srchfid!=null&& srchfid.trim().equals("")){
							sqlsrch += " AND fid='"+srchfid+"'";
						}
						keywords = srchtxt.replace("%", "+")+(srchuname!=null&&!srchuname.trim().equals("")?"+"+srchuname.replace("%", "+"):"");
						expiration = (timestamp + cachelife_text);
					}
					short threads = 0;
					StringBuffer tids = new StringBuffer("0");
					String maxsearchresults = settingMap.get("maxsearchresults");
					List<Map<String,String>> tempMapList4 = dataBaseService.executeQuery("SELECT DISTINCT t.tid, t.closed "+sqlsrch+" ORDER BY tid DESC LIMIT "+maxsearchresults);
					if(tempMapList4!=null){
						for(Map<String,String> thread : tempMapList4){
							if(Integer.parseInt(thread.get("closed"))<1){
								tids.append(","+thread.get("tid"));
								threads++;
							}
						}
					}
					Searchindex searchindex2 = new Searchindex();
					searchindex2.setKeywords(keywords);
					searchindex2.setSearchstring(searchstring);
					searchindex2.setUseip(onlineip);
					searchindex2.setUid(jsprun_uid);
					searchindex2.setDateline(timestamp);
					searchindex2.setExpiration(expiration);
					searchindex2.setThreads(threads);
					searchindex2.setTids(tids.toString());
					searchindex2.setThreadtypeid((short)0);
					searchService.insertSearchindex(searchindex2);
					searchid = searchindex2.getSearchid()+"";
				}
				try {
					response.sendRedirect(response.encodeRedirectURL("index.jsp?action=search&searchid="+searchid+"&do=submit&sid="+session.getId()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		}
	}
	public ActionForward stats(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("jsprun_action", "194");
		HttpSession session = request.getSession();
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Members currentMember = (Members)session.getAttribute("user");
		String formhash = (String)session.getAttribute("formhash");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, currentMember, formhash,session.getId(),resources,locale)){
			return null;
		}
		StatsVO statsVO = wapService.getStatsVO(request, settingMap, currentMember, formhash, session.getId());
		request.setAttribute("valueObject", statsVO);
		return mapping.findForward("stats");
	}
	public ActionForward pm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("jsprun_action", "197");
		HttpSession session = request.getSession();
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		Members currentMember = (Members)session.getAttribute("user");
		String formhashInSession = (String)session.getAttribute("formhash");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(!wapService.validateWap(request, response, settingMap, currentMember, formhashInSession,session.getId(),resources,locale)){
			return null;
		}
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		if(currentMember == null){
			wapService.forwardToMessage(request, response, settingMap, getMessage(request, "wap_not_loggedin"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
			return null;
		}
		String dow = request.getParameter("do");
		if(dow == null){
			PmVO pmVO = wapService.getPmVO(request, settingMap, currentMember, formhashInSession, session.getId(), jsprun_uid);
			request.setAttribute("valueObject", pmVO);
			return mapping.findForward("pm");
		}else{
			if(dow.equals("list")){
				String unreadFR = request.getParameter("unread");
				String pageString = request.getParameter("page");
				PmListVO pmListVO = wapService.getPmListVO(request, response, settingMap, currentMember,formhashInSession, session.getId(), jsprun_uid, unreadFR, pageString, dow,resources,locale,(String)session.getAttribute("timeoffset"));
				request.setAttribute("valueObject", pmListVO);
				return mapping.findForward("pmlist");
			}else if(dow.equals("view")){
				String pmid = request.getParameter("pmid");
				if(pmid == null){
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "pm_operation_error"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
					return null;
				}
				PmViewVO pmViewVO = wapService.getPmViewVO(request, settingMap, currentMember,formhashInSession, session.getId(), pmid, jsprun_uid,(String)session.getAttribute("timeoffset"));
				request.setAttribute("valueObject", pmViewVO);
				return mapping.findForward("pmview");
			}else if(dow.equals("send")){
				String msgto = request.getParameter("msgto");
				if(msgto == null){
					String pmid = request.getParameter("pmid");
					PmSendVO pmSendVO = wapService.getPmSendVO(request, settingMap, currentMember, formhashInSession, session.getId(), pmid, jsprun_uid);
					request.setAttribute("valueObject", pmSendVO);
					return mapping.findForward("pmsend");
				}else{
					Map<String, String> admingroupMap = (Map<String, String>) request.getAttribute("usergroups");
					int disablepostctrl = admingroupMap==null?0:Integer.parseInt(admingroupMap.get("disablepostctrl"));
					int floodctrl = Integer.parseInt(settingMap.get("floodctrl")) * 2;
					int lastpost = 0;
					int timestamp = (Integer)request.getAttribute("timestamp");
					List<Map<String,String>> tempMapList = dataBaseService.executeQuery("SELECT lastpost FROM "+tablePre+"members WHERE uid='"+jsprun_uid+"'");
					if(tempMapList!=null && tempMapList.size()>0){
						lastpost = Integer.parseInt(tempMapList.get(0).get("lastpost"));
					}
					if(floodctrl != 0 && disablepostctrl == 0 && timestamp - lastpost < floodctrl){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_flood_ctrl",String.valueOf(floodctrl)), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
						return null;
					}
					String formhash = request.getParameter("formhash");
					if(!formhashInSession.equals(formhash)){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "wap_submit_invalid"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
						return null;
					}
					List<Map<String,String>> tempMapList2 = dataBaseService.executeQuery("SELECT m.uid AS msgtoid, mf.ignorepm FROM "+tablePre+"members m " +
							"LEFT JOIN "+tablePre+"memberfields mf USING (uid) " +
							"WHERE username=?",msgto);
					if(tempMapList2== null || tempMapList2.size()==0){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "pm_send_nonexistence_wap"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
						return null;
					}
					String jsprun_user = (String)session.getAttribute("jsprun_userss");
					jsprun_user = jsprun_user==null?"":jsprun_user.replace("\\", "\\\\").replace(".", "\\.")
							.replace("+", "\\+").replace("*", "\\*").replace("?", "\\?")
							.replace("[", "\\[").replace("^", "\\^").replace("]", "\\]").replace("$", "\\$")
							.replace("(", "\\(").replace(")", "\\)").replace("{", "\\{").replace("}", "\\}")
							.replace("=", "\\=").replace("!", "\\!").replace("<", "\\<").replace(">", "\\>")
							.replace("|", "\\|").replace(":", "\\:");
					Map<String,String> member = tempMapList2.get(0);
					String ignorepm = member.get("ignorepm");
					if (!ignorepm.equals("")) {
						if (ignorepm.toLowerCase().matches("\\{all\\}")) {
							wapService.forwardToMessage(request, response, settingMap, getMessage(request, "pm_send_ignore_wap"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
							return null;
						} else {
							String ignores[] = ignorepm.split(",");
							for (int i = 0; i < ignores.length; i++) {
								if (jsprun_user.equals(ignores[i].trim())) {
									wapService.forwardToMessage(request, response, settingMap, getMessage(request, "pm_send_ignore_wap"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
									return null;
								}
							}
						}
					}
					Map<String,String> groupCache = (Map<String,String>)request.getAttribute("usergroups");
					String maxpmnum = groupCache.get("maxpmnum");
					if (maxpmnum!=null && maxpmnum.equals("0")) {
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "pm_send_ignore_wap"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
						return null;
					}
					String subject = request.getParameter("subject");
					String message = request.getParameter("message");
					if(subject == null || message == null){
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "post_sm_isnull_wap"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
						return null;
					}
					subject = Common.htmlspecialchars(Common.cutstr(subject.trim(), 75, null));
					dataBaseService.execute("INSERT INTO "+tablePre+"pms (msgfrom, msgfromid, msgtoid, folder, new, subject, dateline, message) " +
							"VALUES(?, '"+jsprun_uid+"', '"+member.get("msgtoid")+"', 'inbox', '1', ?, '"+timestamp+"', ?)",jsprun_user,subject,message);
					dataBaseService.execute("UPDATE "+tablePre+"members SET newpm='1' WHERE uid='"+member.get("msgtoid")+"'");
					if(floodctrl!=0){
						dataBaseService.execute("UPDATE "+tablePre+"members SET lastpost='"+timestamp+"' WHERE uid='"+jsprun_uid+"'");
					}
					Map<String,String> link_titleMap = new HashMap<String, String>();
					link_titleMap.put("link", "index.jsp?action=pm");
					link_titleMap.put("title", getMessage(request, "pm_home"));
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "pm_send_succeed_wap"), link_titleMap, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
					return null;
				}
			}else if(dow.equals("delete")){
				String pmid = request.getParameter("pmid");
				if(pmid!=null){
					dataBaseService.execute("DELETE FROM "+tablePre+"pms WHERE pmid=? AND msgtoid='"+jsprun_uid+"' AND folder='inbox'",pmid);
					wapService.forwardToMessage(request, response, settingMap, getMessage(request, "pm_delete_succeed_2"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
					return null;
				}else{
					String confirm = request.getParameter("confirm");
					if(confirm == null){
						Map<String,String> link_titleMap = new HashMap<String, String>();
						link_titleMap.put("link", "index.jsp?action=pm&amp;do=delete&amp;confirm=yes");
						link_titleMap.put("title", getMessage(request, "pm_delete_confirm"));
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "validate_info"), link_titleMap, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
						return null;
					}else{
						dataBaseService.execute("DELETE FROM "+tablePre+"pms WHERE new='0' AND msgtoid='"+jsprun_uid+"' AND folder='inbox'");
						wapService.forwardToMessage(request, response, settingMap, getMessage(request, "pm_delete_succeed_2"), null, settingMap.get("bbname"), currentMember, "pm", formhashInSession, session.getId());
						return null;
					}
				}
			}
		}
		return null;
	}
	private String checkautoclose(int timestamp,Map<String,String> currentForum,Map<String,String> thread,boolean ismoderator,HttpServletRequest request){
		if(!ismoderator){
			String autoclose = currentForum.get("autoclose");
			if(autoclose!= null && !autoclose.equals("") && !autoclose.equals("0")){
				int tempAutoclose = Integer.parseInt(autoclose);
				String closedby = tempAutoclose > 0?"dateline" : "lastpost";
				tempAutoclose = Math.abs(tempAutoclose);
				if(timestamp - Integer.parseInt(thread.get(closedby)) > tempAutoclose * 86400){
					if(closedby.equals("dateline")){
						return getMessage(request, "post_thread_closed_by_dateline",String.valueOf(tempAutoclose));
					}else{
						return getMessage(request, "post_thread_closed_by_lastpost",String.valueOf(tempAutoclose));
					}
				}
			}
		}
		return null;
	}
	private static String stripslashes(String targetString){
		int tl = targetString.length();
		StringBuffer buffer = new StringBuffer();
		boolean add = false;
		for(int i = 0;i<tl;i++){
			char temp = targetString.charAt(i);
			if(temp!='\\'){
				add = false;
				buffer.append(temp);
			}else{
				if(!add){
					add = true;
				}else{
					add = false;
					buffer.append(temp);
				}
			}
		}
		return buffer.toString();
	}
	private Object setValues(Object bean, HttpServletRequest request) {
		try {
			Field[] fields = bean.getClass().getDeclaredFields();
			String paraName =null;
			String paraValue =null;
			String setMethod = null;
			int fieldLength = fields.length;
			for (int i = 0; i < fieldLength; i++) {
				paraName = fields[i].getName();
				Object obj = request.getParameter((paraName));
				if (obj != null) {
					paraValue = obj.toString();
					if (paraValue != null && !paraValue.equals("")) {
						if ("icq".equals(paraName)) {
							paraValue = (Common.matches(paraValue, "^([0-9]{5,12})$")) ? paraValue: "";
						}
						if ("qq".equals(paraName)) {
							paraValue = (Common.matches(paraValue, "^([0-9]{5,12})$")) ? paraValue: "";
						}
						if ("bday".equals(paraName)) {
							paraValue = FormDataCheck.validateDateFormat(paraValue);
							paraValue =  paraValue != null? paraValue : "0000-00-00";
						}
						setMethod = "set"+ paraName.substring(0, 1).toUpperCase()+ paraName.substring(1, paraName.length());
						Method method = bean.getClass().getMethod(setMethod,fields[i].getType());
						method.invoke(bean, Common.convert(paraValue, fields[i].getType()));
					}
				}
			}
			fields=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	private boolean censormod(String message, HttpServletRequest request) {
		Map<String, String> censor = (Map<String, String>) request.getAttribute("censor");
		return Common.matches(message, censor.get("mod"));
	}
	private String checkflood(int uid, int timestamp, int lastpost,int floodctrl,int disablepostctrl,int maxpostsperhour,HttpServletRequest request) {
		String floodmsg = null;
		if (disablepostctrl== 0&& uid > 0) {
			floodmsg = floodctrl > 0 && (timestamp - floodctrl) <= lastpost ? getMessage(request, "post_flood_ctrl_water",String.valueOf(floodctrl)) : null;
			if (floodmsg == null && maxpostsperhour > 0) {
				List<Map<String, String>> count = dataBaseService.executeQuery("SELECT COUNT(*) count from jrun_posts WHERE authorid='" + uid + "' AND dateline>"+ (timestamp - 3600));
				int userposts = (count != null && count.size() > 0 ? Integer.valueOf(count.get(0).get("count")) : 0);
				floodmsg = userposts >= maxpostsperhour ? getMessage(request, "thread_maxpostsperhour_invalid",String.valueOf(maxpostsperhour)) : null;
			}
		}
		return floodmsg;
	}
}
