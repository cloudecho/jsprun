package cn.jsprun.struts.foreg.actions;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Memberfields;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Mail;
import cn.jsprun.utils.Md5Token;
public class RegisterManageAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward toRegister(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings = ForumInit.settings;
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		if (jsprun_uid > 0) {
			request.setAttribute("successInfo", getMessage(request, "login_succeed_3",(String)session.getAttribute("jsprun_userss")));
			request.setAttribute("requestPath", settings.get("indexname"));
			return mapping.findForward("showMessage");
		}
		int regstatus = Integer.valueOf(settings.get("regstatus"));
		if (regstatus == 0) {
			request.setAttribute("errorInfo", getMessage(request, "register_disable"));
			return mapping.findForward("showMessage");
		}
		Map<String, String> faqs = (Map<String, String>) request.getAttribute("faqs");
		request.setAttribute("faqs", dataParse.characterParse(faqs.get("faqs"), false));
		int bbrules = Integer.valueOf(settings.get("bbrules"));
		String rulesubmit = request.getParameter("rulesubmit");
		if (bbrules==1 && rulesubmit == null) {
			request.setAttribute("bbrulestxt", Common.nl2br(settings.get("bbrulestxt")));
		} else {
			request.setAttribute("regname", settings.get("regname"));
			int regadvance = Integer.valueOf(settings.get("regadvance"));
			request.setAttribute("advcheck",regadvance==1 ? "checked='checked'" : "");
			request.setAttribute("advdisplay", regadvance==1? "": "none");
			boolean seccodecheck = (Integer.valueOf(settings.get("seccodestatus"))&1)>0;
			if (seccodecheck) {
				request.setAttribute("seccodedata", dataParse.characterParse(settings.get("seccodedata"), false));
			}
			request.setAttribute("seccodecheck", seccodecheck);
			Map secqaas  = dataParse.characterParse(settings.get("secqaa"), false);
			int secqaaStatus = secqaas!=null?((Integer)secqaas.get("status"))&1:0;
			secqaas=null;
			request.setAttribute("secqaaStatus", secqaaStatus);
			int regverify = Integer.valueOf(settings.get("regverify"));
			request.setAttribute("regverify",regverify);
			request.setAttribute("accessemail", settings.get("accessemail").replaceAll("\r|\n", "|"));
			request.setAttribute("censoremail", settings.get("censoremail").replaceAll("\r|\n", "|"));
			request.setAttribute("doublee", settings.get("doublee"));
			int fromuid = Common.toDigit(CookieUtil.getCookie(request, "promotion", true, settings));
			Map creditspolicys = dataParse.characterParse(settings.get("creditspolicy"),false);
			String fromuser = request.getParameter("fromuser");
			fromuser = !Common.empty(fromuser) ? Common.htmlspecialchars(fromuser) : "";
			if (fromuid >0 && !Common.empty(creditspolicys.get("promotion_register"))) {
				List<Map<String,String>> members=dataBaseService.executeQuery("SELECT username FROM jrun_members WHERE uid='"+fromuid+"'");
				if (members != null&&members.size()>0) {
					fromuser = members.get(0).get("username");
					request.setAttribute("fromuser", fromuser);
				} else {
					CookieUtil.setCookie(request, response, "promotion", "", 0,true,settings);
				}
			}
			Map<String,String> profilefields=(Map<String,String>)request.getAttribute("profilefields");
			request.setAttribute("fields_required", dataParse.characterParse(profilefields.get("fields_required"),true));
			request.setAttribute("fields_optional",dataParse.characterParse(profilefields.get("fields_optional"),true));
			int initcredits = Integer.valueOf(settings.get("initcredits").split(",")[0]);
			Map<String,String> groupinfo=dataBaseService.executeQuery("SELECT groupid, allownickname, allowcstatus, allowavatar, allowcusbbcode, allowsigbbcode, allowsigimgcode, maxsigsize FROM jrun_usergroups WHERE "+ (regverify >0? "groupid='8'" : "creditshigher<="+ initcredits + " AND " + initcredits+ "< creditslower LIMIT 1")).get(0);
			if(groupinfo.get("allowavatar").equals("3")){
				groupinfo.put("allowavatar", "2");
			}
			request.setAttribute("groupinfo", groupinfo);
			request.setAttribute("forumStyles", dataParse.characterParse(settings.get("forumStyles"),true));
			request.setAttribute("dateformats", settings.get("userdateformat").split("\r\n"));
			request.setAttribute("rulesubmit", rulesubmit);
			request.setAttribute("regstatus", regstatus);
		}
		request.setAttribute("timeZoneIDs", Common.getTimeZoneIDs());
		request.setAttribute("bbrules", bbrules);
		return mapping.findForward("toRegister");
	}
	@SuppressWarnings("unchecked")
	public ActionForward register(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "regsubmit")){
				String username = request.getParameter("username");
				int usersize = Common.strlen(username);
				if(usersize < 3){
					request.setAttribute("errorInfo", getMessage(request, "a_member_add_tooshort"));
					return mapping.findForward("showMessage");
				}else if(usersize > 15){
					request.setAttribute("errorInfo", getMessage(request, "a_member_add_toolong"));
					return mapping.findForward("showMessage");
				}
				String seccodeverify = request.getParameter("seccodeverify");
				String secanswer = request.getParameter("secanswer");
				String password = request.getParameter("password").trim();
				String password2 = request.getParameter("password2").trim();
				String email = request.getParameter("email").trim();
				String invitecode = request.getParameter("invitecode");
				String regmessage = request.getParameter("regmessage");
				HttpSession session = request.getSession();
				if (seccodeverify != null&&!seccodeverify.equalsIgnoreCase((String)session.getAttribute("rand"))) {
					request.setAttribute("errorInfo", getMessage(request, "submit_seccode_invalid"));
					return mapping.findForward("showMessage");
				}
				if (secanswer != null&&!secanswer.trim().equals((String)session.getAttribute("answer"))) {
					request.setAttribute("errorInfo", getMessage(request, "submit_secqaa_invalid"));
					return mapping.findForward("showMessage");
				}
				if (!password.equals(password2)) {
					request.setAttribute("errorInfo", getMessage(request, "profile_passwd_notmatch"));
					return mapping.findForward("showMessage");
				}
				Map<String,String> settings=ForumInit.settings;
				String censoruser=settings.get("censoruser");
				if (Common.censoruser(username, censoruser)) {
					request.setAttribute("errorInfo", getMessage(request, "profile_username_illegal"));
					return mapping.findForward("showMessage");
				}
				if ("".equals(password)) {
					request.setAttribute("errorInfo", getMessage(request, "profile_passwd_illegal"));
					return mapping.findForward("showMessage");
				}
				password=Md5Token.getInstance().getLongToken(password);
				String accessemail=settings.get("accessemail");
				String censoremail=settings.get("censoremail");
				boolean invalidemail = !accessemail.equals("") ? !Common.matches(email,"(" + accessemail.replaceAll("\r\n", "1")+ ")$") : !censoremail.equals("")&& Common.matches(email, "(" + censoremail.replaceAll("\r\n", "1")+ ")$");
				if (!Common.isEmail(email) || invalidemail) {
					request.setAttribute("errorInfo",getMessage(request, "profile_email_illegal"));
					return mapping.findForward("showMessage");
				}
				int regstatus = Integer.valueOf(settings.get("regstatus"));
				invitecode=regstatus>1&&invitecode!=null?Common.htmlspecialchars(invitecode):"";
				int regverify = Integer.valueOf(settings.get("regverify"));
				if (regverify == 2&&(regmessage == null||"".equals(regmessage.trim()))) {
					request.setAttribute("errorInfo", getMessage(request, "profile_required_info_invalid"));
					return mapping.findForward("showMessage");
				}
				String[] initcredits=settings.get("initcredits").split(",");
				int credits = Integer.valueOf(initcredits[0]);
				int initcredit1=Integer.valueOf(initcredits[1]);
				int initcredit2=Integer.valueOf(initcredits[2]);
				int initcredit3=Integer.valueOf(initcredits[3]);
				int initcredit4=Integer.valueOf(initcredits[4]);
				int initcredit5=Integer.valueOf(initcredits[5]);
				int initcredit6=Integer.valueOf(initcredits[6]);
				int initcredit7=Integer.valueOf(initcredits[7]);
				int initcredit8=Integer.valueOf(initcredits[8]);
				Map<String,String> groupinfo=dataBaseService.executeQuery("SELECT groupid, allownickname, allowcstatus, allowavatar, allowcusbbcode, allowsigbbcode, allowsigimgcode, maxsigsize FROM jrun_usergroups WHERE "+ (regverify >0 ? "groupid='8'" : "creditshigher <= "+ credits + " AND " + credits+ "< creditslower LIMIT 1")).get(0);
				String msn = request.getParameter("msn");
				String nickname = request.getParameter("nickname");
				String cstatus = request.getParameter("cstatus");
				String signature = request.getParameter("signature");
				int questionid = Common.toDigit(request.getParameter("questionid"));
				String answer = request.getParameter("answer");
				String secques = Common.quescrypt(questionid, answer);
				if (nickname != null&& censoruser.length()>0 && Common.censoruser(nickname, censoruser)) {
					request.setAttribute("errorInfo", getMessage(request, "nickname_invalid"));
					return mapping.findForward("showMessage");
				}
				if (cstatus != null&& censoruser.length()>0	&& Common.censoruser(cstatus, censoruser)) {
					request.setAttribute("errorInfo", getMessage(request, "cstatus_invalid"));
					return mapping.findForward("showMessage");
				}		
				if (!"".equals(msn) && !Common.isEmail(msn)) {
					request.setAttribute("errorInfo", getMessage(request, "profile_alipay_msn"));
					return mapping.findForward("showMessage");
				}
				Map<String,String> profilefields=(Map<String,String>)request.getAttribute("profilefields");
				Map<String,Map<String,String>> fields_required=dataParse.characterParse(profilefields.get("fields_required"),true);
				Map<String,Map<String,String>> fields_optional=dataParse.characterParse(profilefields.get("fields_optional"),true);
				if(fields_required==null){
					fields_required =new TreeMap<String,Map<String,String>>();
				}
				if(fields_optional!=null&&fields_optional.size()>0){
					fields_required.putAll(fields_optional);
				}
				StringBuffer fieldadd1=new StringBuffer();
				StringBuffer fieldadd2=new StringBuffer();
				if(fields_required.size()>0){
					Set<Entry<String,Map<String,String>>> field_keys=fields_required.entrySet();
					for(Entry<String,Map<String,String>> temp:field_keys){
						String field_key = temp.getKey();
						Map<String,String>  field=temp.getValue();
						String field_val=request.getParameter(field_key+"new");
						if(field_val!=null){
							field_val=field_val.trim();
						}else{
							field_val="";
						}
						int size = Common.intval(field.get("size"));
						if(field_val.length()>size){
							field_val = field_val.substring(0,size);
						}
						if("1".equals(field.get("required"))&&"".equals(field_val)){
							request.setAttribute("errorInfo", getMessage(request, "profile_required_info_invalid"));
							return mapping.findForward("showMessage");
						}else{
							fieldadd1.append(", "+field_key);
							fieldadd2.append(", '"+Common.addslashes(Common.htmlspecialchars(field_val))+"'");
						}
					}
				}
				int maxsigsize = Integer.valueOf(groupinfo.get("maxsigsize"));
				if (maxsigsize > 0) {
					if (signature.length() > maxsigsize) {
						request.setAttribute("maxsigsize", maxsigsize);
						request.setAttribute("errorInfo", getMessage(request, "memcp_profile_sig_toolong",String.valueOf(maxsigsize)));
						return mapping.findForward("showMessage");
					}
					signature = Common.htmlspecialchars(signature);
				} else {
					signature = "";
				}
				Map<String,String> inviteconfigs = dataParse.characterParse(settings.get("inviteconfig"),false);
				String onlineip = Common.get_onlineip(request);
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				String ipregctrl =settings.get("ipregctrl");
				String ctrlip = "";
				int regctrl = Integer.valueOf(settings.get("regctrl"));
				if (!"".equals(ipregctrl)) {
					String[] ipregctrls = ipregctrl.split("\n");
					for (String obj : ipregctrls) {
						if (Common.matches(onlineip, "^(" + obj + ")")) {
							ctrlip = obj + "%";
							regctrl = 72;
							break;
						} else {
							ctrlip = onlineip;
						}
					}
					ipregctrls=null;
				} else {
					ctrlip = onlineip;
				}
				Map<String,String> invite=null;
				if (regstatus > 1) {
					if (regstatus == 2 &&invitecode.equals("")) {
						request.setAttribute("errorInfo", getMessage(request, "register_invite_notfound"));
						return mapping.findForward("showMessage");
					} else if (!invitecode.equals("")) {
						groupinfo.put("groupid",inviteconfigs.get("invitegroupid") != null	&& !inviteconfigs.get("invitegroupid").equals("") ? inviteconfigs.get("invitegroupid") : groupinfo.get("groupid"));
						List<Map<String,String>> invites=dataBaseService.executeQuery("SELECT uid,inviteip, expiration FROM jrun_invites WHERE invitecode=? AND status IN ('1', '3')", Common.addslashes(invitecode));
						if (invites == null || invites.size() == 0) {
							request.setAttribute("errorInfo", getMessage(request, "invite_invalid"));
							return mapping.findForward("showMessage");
						} else {
							invite = invites.get(0);
							if (invite.get("inviteip").equals(onlineip)) {
								request.setAttribute("errorInfo",getMessage(request, "register_invite_iperror"));
								return mapping.findForward("showMessage");
							} else if (Integer.valueOf(invite.get("expiration"))< timestamp) {
								request.setAttribute("errorInfo", getMessage(request, "register_invite_expiration"));
								return mapping.findForward("showMessage");
							}
						}
					}
				}
				if (regctrl > 0) {
					List<Map<String,String>> regips=dataBaseService.executeQuery("SELECT ip FROM jrun_regips WHERE ip LIKE '"+ ctrlip+ "' AND count='-1' AND dateline>"+(timestamp - regctrl * 3600)+" LIMIT 1");
					if (regips != null && regips.size() > 0) {
						request.setAttribute("errorInfo", getMessage(request, "register_ctrl",String.valueOf(regctrl)));
						return mapping.findForward("showMessage");
					}
				}
				List<Map<String,String>> user=dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE username='"+Common.addslashes(username)+"'");
				if (user != null&&user.size()>0) {
					request.setAttribute("errorInfo", getMessage(request, "profile_username_duplicate"));
					return mapping.findForward("showMessage");
				}
				if (Integer.valueOf(settings.get("doublee"))==0) {
					List<Map<String,String>> members=dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE email='"+Common.addslashes(email)+"' LIMIT 1");
					if (members != null && members.size() > 0) {
						request.setAttribute("errorInfo", getMessage(request, "profile_email_duplicate"));
						return mapping.findForward("showMessage");
					}
				}
				int regfloodctrl = Integer.valueOf(settings.get("regfloodctrl"));
				if (regfloodctrl>0){
					List<Map<String,String>> regips=dataBaseService.executeQuery("SELECT count FROM jrun_regips WHERE ip='"+onlineip+"' AND count>'0' AND dateline>"+(timestamp-86400));
					if (regips!=null&&regips.size()>0){
						Map<String,String> regip=regips.get(0);
						if (Integer.valueOf(regip.get("count"))>=regfloodctrl){
							request.setAttribute("errorInfo", getMessage(request, "register_flood_ctrl",String.valueOf(regfloodctrl)));
							return mapping.findForward("showMessage");
						} else{
							dataBaseService.runQuery("UPDATE jrun_regips SET count=count+1 WHERE ip='"+onlineip+"' AND count>'0'",true);
						}
					} else{
						dataBaseService.runQuery("INSERT INTO jrun_regips (ip, count, dateline) VALUES ('"+onlineip+"', '1', '"+timestamp+"')",true);
					}
				}
				byte sigstatus = Byte.valueOf(!signature.equals("") ? "1" : "0");
				String idstring =Common.getRandStr(6,false);
				String authstr = regverify==1 ? timestamp + "\t2\t" +idstring: "";
				String urlavatar = request.getParameter("urlavatar");
				String avatarwidth = request.getParameter("avatarwidthnew");
				String avatarheight = request.getParameter("avatarheightnew");
				String avatar = null;
				if (urlavatar != null && !urlavatar.equals("")&& groupinfo.get("allowavatar").equals(1)) {
					avatar = urlavatar;
					if (!(Common.matches(urlavatar, "^(images\\/avatars\\/.+?)$") || Common.matches(urlavatar, "^(http:\\/\\/.+?)$"))) {
						request.setAttribute("errorInfo", getMessage(request, "profile_avatar_invalid"));
						return mapping.findForward("showMessage");
					}
					if (!Common.matches(avatar.substring(avatar.lastIndexOf(".") + 1),"(gif|jpg|png")) {
						request.setAttribute("errorInfo", getMessage(request, "profile_avatar_invalid"));
						return mapping.findForward("showMessage");
					}
					if (avatarwidth == null || avatarwidth.equals("")|| avatarwidth.equals("*") || avatarheight == null|| avatarheight.equals("") || avatarheight.equals("*")) {
						avatarwidth = "80";
						avatarheight = "80";
					}
				} else {
					avatar = "";
					avatarwidth = "";
					avatarheight = "";
				}
				int salt = Common.rand(100000, 999999);
				password = Md5Token.getInstance().getLongToken(password+salt);
				Members member = new Members();
				member = (Members) setValues(member, request);
				String bday=request.getParameter("bday");
				bday=Common.datecheck(bday) ? Common.dateformat(bday): "0000-00-00";
				int uid = dataBaseService.insert("INSERT INTO jrun_members (username, password, secques, gender, adminid, groupid, regip, regdate, lastvisit, lastactivity, posts, credits, extcredits1, extcredits2, extcredits3, extcredits4, extcredits5, extcredits6, extcredits7, extcredits8, email, bday, sigstatus, tpp, ppp, styleid, dateformat, timeformat, pmsound, showemail,newsletter, invisible, timeoffset,salt)VALUES" +
						" ('"+Common.addslashes(username)+"', '"+password+"', '"+secques+"', '"+member.getGender()+"', '0', '"+groupinfo.get("groupid")+"', '"+onlineip+"', '"+timestamp+"', '"+timestamp+"', '"+timestamp+"', '0', "+credits+","+initcredit1+","+initcredit2+","+initcredit3+","+initcredit4+","+initcredit5+","+initcredit6+","+initcredit7+","+initcredit8+", '"+email+"', '"+bday+"', '"+sigstatus+"', '"+member.getTpp()+"', '"+member.getPpp()+"', '"+member.getStyleid()+"', '"+member.getDateformat()+"', '"+member.getTimeformat()+"', '"+(member.getPmsound()!=null?member.getPmsound():1)+"', '"+(member.getShowemail()!=null?member.getShowemail():0)+"', '"+(member.getNewsletter()!=null?member.getNewsletter():0)+"', '"+(member.getInvisible()!=null?member.getInvisible():0)+"', '"+member.getTimeoffset()+"','"+salt+"')", true);
				if(uid>0){
					Memberfields memberfield = new Memberfields();
					memberfield.setUid(uid);
					memberfield.setBio("");
					memberfield.setIgnorepm("");
					memberfield.setGroupterms("");
					memberfield.setSpacename("");
					memberfield = (Memberfields) setValues(memberfield, request);
					String location = request.getParameter("location");
					memberfield.setLocation(Common.cutstr(location, 30));
					memberfield.setSightml(signature);
					memberfield.setAuthstr(authstr);
					memberfield.setAvatar(avatar);
					memberfield.setAvatarheight((short)Common.range(Common.intval(avatarheight), 127, 0));
					memberfield.setAvatarwidth((short)Common.range(Common.intval(avatarwidth), 127, 0));
					dataBaseService.runQuery("INSERT INTO jrun_memberfields (uid, nickname, site,alipay,icq, qq, yahoo, msn, taobao, location, customstatus, medals, avatar, avatarwidth, avatarheight, bio, sightml,ignorepm,groupterms,authstr,spacename "+fieldadd1+") VALUES ('"+uid+"', '"+(memberfield.getNickname()!=null?Common.addslashes(memberfield.getNickname()):"")+"', '"+(memberfield.getSite()!=null?Common.addslashes(memberfield.getSite()):"")+"', '"+(memberfield.getAlipay()!=null?Common.addslashes(memberfield.getAlipay()):"")+"', '"+(memberfield.getIcq()!=null?memberfield.getIcq():"")+"', '"+(memberfield.getQq()!=null?memberfield.getQq():"")+"', '"+(memberfield.getYahoo()!=null?memberfield.getYahoo():"")+"', '"+(memberfield.getMsn()!=null?memberfield.getMsn():"")+"', '"+(memberfield.getTaobao()!=null?Common.addslashes(memberfield.getTaobao()):"")+"', '"+(memberfield.getLocation()!=null?Common.addslashes(memberfield.getLocation()):"")+"', '"+(memberfield.getCustomstatus()!=null?memberfield.getCustomstatus():"")+"', '"+(memberfield.getMedals()!=null?memberfield.getMedals():"")+"', '"+memberfield.getAvatar()+"', '"+memberfield.getAvatarwidth()+"', '"+memberfield.getAvatarheight()+"', '"+Common.addslashes(memberfield.getBio())+"', '"+Common.addslashes(memberfield.getSightml())+"', '"+Common.addslashes(memberfield.getIgnorepm())+"', '"+memberfield.getGroupterms()+"', '"+Common.addslashes(memberfield.getAuthstr())+"','' "+fieldadd2+")",true);
					if (regctrl > 0 || regfloodctrl > 0) {
						dataBaseService.runQuery("DELETE FROM jrun_regips WHERE dateline<='"+(timestamp-(regctrl > 72 ? regctrl : 72)*3600)+"'",true);
						if(regctrl>0){
							dataBaseService.runQuery("INSERT INTO jrun_regips (ip, count, dateline) VALUES ('"+onlineip+"', '-1', '"+timestamp+"')",true);
						}
					}
					if (regverify==2) {
						dataBaseService.runQuery("REPLACE INTO jrun_validating (uid, submitdate, moddate, admin, submittimes, status, message, remark) VALUES ('"+uid+"', '"+timestamp+"', '0', '', '1', '0', '"+Common.addslashes(regmessage)+"', '')",true);
					}
					if (regstatus > 1&&!invitecode.equals("")) {
						dataBaseService.runQuery("UPDATE jrun_invites SET reguid='"+uid+"', regdateline='"+timestamp+"', status='2' WHERE invitecode='"+Common.addslashes(invitecode)+"' AND status IN ('1', '3')",true);
						if("1".equals(inviteconfigs.get("inviteaddbuddy"))) {
							dataBaseService.runQuery("INSERT INTO jrun_buddys (uid, buddyid, dateline) VALUES ('"+invite.get("uid")+"', '"+uid+"', '"+timestamp+"')", true);
						}
						int inviterewardcredit = Common.toDigit(inviteconfigs.get("inviterewardcredit"));
						if(inviterewardcredit>0) {
							int inviteaddcredit = Common.toDigit(inviteconfigs.get("inviteaddcredit"));
							int invitedaddcredit = Common.toDigit(inviteconfigs.get("invitedaddcredit"));
							if(inviteaddcredit>0) {
								dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+inviterewardcredit+"=extcredits"+inviterewardcredit+"+'"+inviteaddcredit+"' WHERE uid='"+uid+"'",true);
							}
							if(invitedaddcredit>0) {
								dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+inviterewardcredit+"=extcredits"+inviterewardcredit+"+'"+invitedaddcredit+"' WHERE uid='"+invite.get("uid")+"'",true);
							}
						}
					}
					member=memberService.findMemberById(uid);
					request.setAttribute("sessionexists", false);
					session.setAttribute("jsprun_uid", member.getUid());
					session.setAttribute("jsprun_userss", member.getUsername());
					session.setAttribute("jsprun_groupid", member.getGroupid());
					session.setAttribute("jsprun_adminid", member.getAdminid());
					session.setAttribute("jsprun_pw", member.getPassword());
					session.setAttribute("user", member);
					session.setAttribute("formhash", Common.getRandStr(8,false));
					request.setAttribute("refresh", "true");
					Common.setDateformat(session, settings);
					settings.put("totalmembers", String.valueOf(Integer.valueOf(settings.get("totalmembers"))+1));
					settings.put("lastmember",  member.getUsername().replace("\\", "\\\\"));
					int welcomemsg=Common.toDigit(settings.get("welcomemsg"));
					String welcomemsgtxt=settings.get("welcomemsgtxt");
					if(welcomemsg>0&&welcomemsgtxt!=null&&!"".equals(welcomemsgtxt))
					{
						String timeoffset=settings.get("timeoffset");
						Map<String,String> replaces=new HashMap<String, String>();
						replaces.put("{sitename}", settings.get("sitename"));
						replaces.put("{bbname}", settings.get("bbname"));
						replaces.put("{time}", Common.gmdate("yyyy-MM-dd HH:mm",timestamp,timeoffset));
						replaces.put("{adminemail}",settings.get("adminemail"));
						replaces.put("{myname}", member.getUsername());
						replaces.put("{username}", member.getUsername());
						replaces.put("(\t|\n)", "<br/>");
						String welcomemsgtitle=settings.get("welcomemsgtitle");
						String welcomtitle=welcomemsgtitle!=null&&!"".equals(welcomemsgtitle)?welcomemsgtitle:"Welcome to {bbname}!";
						welcomtitle=replacesitevar(welcomtitle, replaces);
						welcomemsgtxt=replacesitevar(welcomemsgtxt, replaces);
						if(welcomemsg==1){
							dataBaseService.runQuery("INSERT INTO jrun_pms (msgfrom, msgfromid, msgtoid, folder, new, subject, dateline, message) VALUES ('System Message', '0', '"+uid+"', 'inbox', '1', '"+Common.addslashes(welcomtitle)+"', '"+timestamp+"','"+Common.addslashes(welcomemsgtxt)+"')",true);
							dataBaseService.runQuery("UPDATE jrun_members SET newpm='1' WHERE uid='"+uid+"'",true);
						}else if(welcomemsg==2){
							Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
							Mail mail=new Mail(mails);
							mail.sendMessage(mails.get("from"),username+" <"+email+">",welcomtitle,null,welcomemsgtxt);
						}
					}
					int fromuid = Common.toDigit(CookieUtil.getCookie(request, "promotion", true, settings));
					if(fromuid>0) {
						Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
						Map<Integer,Integer>  creditsarray = (Map<Integer,Integer>)creditspolicys.get("promotion_register");
						Common.updatepostcredits("+", fromuid, 1,creditsarray);
						Common.updatepostcredits(fromuid, settings.get("creditsformula"));
						CookieUtil.setCookie(request, response, "promotion", "", 0,true,settings);
					}
					if (regverify==1) {
						Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
						Mail mail=new Mail(mails);
						String boardurl=(String)session.getAttribute("boardurl");
						mail.sendMessage(mails.get("from"),username+" <"+email+">",getMessage(request, "email_verify_subject"),getMessage(request, "email_verify_message",username,settings.get("bbname"),boardurl+"member.jsp?action=activate&uid="+uid+"&id="+idstring,settings.get("bbname"),boardurl),null);
						request.setAttribute("resultInfo",getMessage(request, "profile_email_verify"));
						return mapping.findForward("showMessage");
					} else if (regverify==2) {
						request.setAttribute("successInfo",getMessage(request, "register_manual_verify"));
						request.setAttribute("requestPath", "memcp.jsp");
						return mapping.findForward("showMessage");
					} else {
						if(Common.isshowsuccess(session, "register_succeed")){
							Common.requestforward(response, settings.get("indexname"));
							return null;
						}else{
							request.setAttribute("successInfo", getMessage(request, "register_succeed"));
							request.setAttribute("requestPath",settings.get("indexname"));
							return mapping.findForward("showMessage");
						}
					}
				}else{
					return this.toRegister(mapping, form, request, response);
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		return this.toRegister(mapping, form, request, response);
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
							paraValue = paraValue.matches("^\\d{5,12}$") ? paraValue: "";
						}
						if ("qq".equals(paraName)) {
							paraValue = paraValue.matches("^\\d{5,12}$") ? paraValue: "";
						}
						if ("alipay".equals(paraName)) {
							paraValue = paraValue.matches("^\\d{5,12}$") ? paraValue: "";
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
	private String replacesitevar(String value,Map<String,String> replaces){
		Set<Entry<String,String>> keys=replaces.entrySet();
		for (Entry<String,String> temp : keys) {
			value=value.replace(temp.getKey(),temp.getValue());
		}
		return value;
	}
}