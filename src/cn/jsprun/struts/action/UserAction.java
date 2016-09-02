package cn.jsprun.struts.action;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import cn.jsprun.domain.Admingroups;
import cn.jsprun.domain.Banned;
import cn.jsprun.domain.Memberfields;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Onlinetime;
import cn.jsprun.domain.Profilefields;
import cn.jsprun.domain.Projects;
import cn.jsprun.domain.Ranks;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.struts.form.UserForm;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.IPSeeker;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.LogPage;
import cn.jsprun.utils.Mail;
import cn.jsprun.utils.Md5Token;
public class UserAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward memberAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "addsubmit")){
				String username = request.getParameter("username").trim();
				String password = request.getParameter("password").trim();
				String formPass = password;
				Md5Token md5 = Md5Token.getInstance();
				String email = request.getParameter("email").trim();
				if (username.length() == 0 || password.length() == 0|| email.length() == 0) {
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_member_add_invalid");
					return mapping.findForward("message");
				}
				int strlen = Common.strlen(username);
				if (strlen < 3) {
					request.setAttribute("return", true);
					request.setAttribute("message_key","a_member_add_tooshort");
					return mapping.findForward("message");
				} else if (strlen > 15) {
					request.setAttribute("return", true);
					request.setAttribute("message_key","a_member_add_toolong");
					return mapping.findForward("message");
				}
				Map<String, String> settings = ForumInit.settings;
				String censoruser = settings.get("censoruser");
				if (Common.censoruser(username, censoruser)) {
					request.setAttribute("return", true);
					request.setAttribute("message_key", "profile_username_illegal");
					return mapping.findForward("message");
				}
				List<Map<String, String>> members = dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE username='"+ username + "'");
				if (members != null && members.size() > 0) {
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_member_add_username_duplicate");
					return mapping.findForward("message");
				}
				String uidadd1 = "", uidadd2 = "";
				int uidupperlimit = Common.toDigit(request.getParameter("uidupperlimit"));
				int uidlowerlimit = Common.toDigit(request.getParameter("uidlowerlimit"));
				int uid = 0;
				if (uidlowerlimit > 0 && uidupperlimit >= uidlowerlimit) {
					uid = uidlowerlimit;
					members = dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE uid BETWEEN '"+ uidlowerlimit+ "' AND '"+ uidupperlimit+ "' ORDER BY uid");
					if (members != null && members.size() > 0) {
						for (Map<String, String> member : members) {
							if (Integer.valueOf(member.get("uid")) > uid) {
								break;
							} else {
								uid++;
							}
						}
					}
					if (uid <= uidupperlimit) {
						uidadd1 = "uid, ";
						uidadd2 = uid + ", ";
					} else {
						request.setAttribute("return", true);
						request.setAttribute("message_key","a_member_add_uid_invalid");
						return mapping.findForward("message");
					}
				}
				int groupid = Integer.valueOf(request.getParameter("groupid"));
				Map<String, String> group = dataBaseService.executeQuery("SELECT groupid, radminid, type FROM jrun_usergroups WHERE groupid='"+ groupid + "'").get(0);
				int radminid = Common.toDigit(group.get("radminid"));
				if (radminid == 1) {
					request.setAttribute("return", true);
					request.setAttribute("message_key","a_member_add_admin_none");
					return mapping.findForward("message");
				}
				if (groupid == 5 || groupid == 6 || groupid == 7) {
					request.setAttribute("return", true);
					request.setAttribute("message_key","a_member_add_ban_all_none");
					return mapping.findForward("message");
				}
				int adminid = radminid == 1 || radminid == 2 || radminid == 3 ? radminid: ("special".equals(group.get("type")) ? -1 : 0);
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				int salt = Common.rand(100000, 999999);
				password = md5.getLongToken(md5.getLongToken(password)+salt);
				int tempuid = dataBaseService.insert(
								"INSERT INTO jrun_members ("
										+ uidadd1
										+ " username, password, secques, gender, adminid, groupid, regip, regdate, lastvisit, lastactivity, posts, credits, email, bday, sigstatus, tpp, ppp, styleid, dateformat, timeformat, showemail, newsletter, invisible, timeoffset,salt) VALUES ("
										+ uidadd2
										+ " '"
										+ username
										+ "', '"
										+ password
										+ "', '', '0', '"
										+ adminid
										+ "', '"
										+ groupid
										+ "', 'Manual Acting', '"
										+ timestamp
										+ "', '"
										+ timestamp
										+ "', '"
										+ timestamp
										+ "', '0', '0', '"
										+ Common.addslashes(email)
										+ "', '0000-00-00', '0', '0', '0', '0', '0', '2', '1', '1', '0', '"
										+ settings.get("timeoffset") + "','"+salt+"')", true);
				if (tempuid > 0 && uid==0) {
					uid = tempuid;
				}
				dataBaseService.runQuery("REPLACE INTO jrun_memberfields (uid,nickname,site,alipay,icq,qq,yahoo,msn,taobao,location,customstatus,medals,avatar,bio,sightml,ignorepm,groupterms,authstr,spacename) VALUES ('"
										+ uid+ "','','','','','','','','','','','','','','','','','','')",true);
				String emailnotify = request.getParameter("emailnotify");
				if ("yes".equals(emailnotify)) {
					HttpSession session = request.getSession();
					String boardurl = (String) session.getAttribute("boardurl");
					String jsprun_user = (String)session.getAttribute("jsprun_userss");
					Map<String, String> mails = dataParse.characterParse(settings.get("mail"), false);
					Mail mail = new Mail(mails);
					mail.sendMessage(mails.get("from"),email,getMessage(request, "add_member_subject"),getMessage(request, "add_member_message",settings.get("bbname"),jsprun_user,boardurl,username,formPass), null);
				}
				settings.put("totalmembers", String.valueOf(Integer.valueOf(settings.get("totalmembers"))+1));
				settings.put("lastmember",  username.replace("\\", "\\\\"));
				request.setAttribute("message", getMessage(request, "members_add_succeed",username,uid+""));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String, String>> usergroups = dataBaseService.executeQuery("SELECT groupid, type, grouptitle, creditshigher FROM jrun_usergroups WHERE type='member' AND creditshigher='0' OR (groupid NOT IN ('5', '6', '7') AND radminid<>'1' AND type<>'member') ORDER BY type DESC, (creditshigher<>'0' || creditslower<>'0'), creditslower");
		request.setAttribute("usergroups", usergroups);
		return mapping.findForward("memberadd");
	}
	@SuppressWarnings("unchecked")
	public ActionForward membersInit(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, String>> usergroups = dataBaseService.executeQuery("SELECT groupid, grouptitle FROM jrun_usergroups WHERE groupid NOT IN ('6', '7') ORDER BY (creditshigher<>'0' || creditslower<>'0'), creditslower , groupid");
		String setvalue = ForumInit.settings.get("extcredits");
		Map extcredits = dataParse.characterParse(setvalue, true);
		request.setAttribute("extcredits", extcredits);
		request.setAttribute("usergroups", usergroups);
		request.setAttribute("inited", "yes");
		return mapping.findForward("inited");
	}
	@SuppressWarnings("unchecked")
	public ActionForward modMembers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String>settings=ForumInit.settings;
		String sendmail = request.getParameter("sendmail");
		sendmail = sendmail==null?"1":sendmail;
		int count = Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) as count FROM jrun_validating WHERE status='0'").get(0).get("count"));
		int page =Math.max(Common.intval(request.getParameter("page")), 1);
		int memberperpage=Common.toDigit(settings.get("memberperpage"));
		Map<String,Integer> multiInfo=Common.getMultiInfo(count, memberperpage, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		Map<String,Object> multi=Common.multi(count, memberperpage, page, "admincp.jsp?action=modmembers&sendemail="+sendmail, 0, 10, true, false, null);
		request.setAttribute("multi", multi);
		List<Map<String, String>> validatelist = dataBaseService.executeQuery("select v.*,m.username,m.regdate,m.regip,m.email,m.groupid from jrun_validating as v left join jrun_members as m on v.uid=m.uid where v.status=0 limit "+start_limit+","+memberperpage);
		List<Map<String,String>> removelist = new ArrayList<Map<String,String>>();
		for (Map<String, String> validate : validatelist) {
			if (!"8".equals(validate.get("groupid"))) {
				dataBaseService.runQuery("delete from jrun_validating where uid="+ validate.get("uid"), true);
				removelist.add(validate);
			}
		}
		for(Map<String, String> validate : removelist){
			validatelist.remove(validate);
		}
		removelist = null;
		request.setAttribute("validatingList", validatelist);
		return mapping.findForward("modMembers");
	}
	@SuppressWarnings( { "deprecation", "unchecked" })
	public ActionForward userGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		String[] delIds = request.getParameterValues("delid");
		String[] updateIds = request.getParameterValues("updateid");
		Usergroups userGroup = null;
		if (type != null && "member".equals(type)) {
			try{
				if(submitCheck(request, "groupsubmit")){
					String[] newGroupTitles = request.getParameterValues("newgrouptitle");
					String[] newCreditHighters = request.getParameterValues("newcreditshigher");
					String[] newStarses = request.getParameterValues("newstars");
					int insertLength = newGroupTitles.length;
					String[] projects = request.getParameterValues("projectid");
					String[] groupTitles = request.getParameterValues("grouptitle");
					String[] creditsHighers = request.getParameterValues("creditshigher");
					String[] starses = request.getParameterValues("stars");
					String[] colors = request.getParameterValues("color");
					String[] groupAvatars = request.getParameterValues("groupavatar");
					int updateLength = 0;
					if (groupTitles != null) {
						updateLength = groupTitles.length;
					}
					List<String> idList = new ArrayList<String>();
					List<String> groupTitleList = new ArrayList<String>();
					List<String> creditsHigherList = new ArrayList<String>();
					List<String> starsList = new ArrayList<String>();
					List<String> colorList = new ArrayList<String>();
					List<String> groupAvatarList = new ArrayList<String>();
					List<String> projectList = new ArrayList<String>();
					for (int i = 0; i < insertLength; i++) {
						if (!newGroupTitles[i].equals("") && !newCreditHighters[i].equals("")) {
							idList.add("-1");
							groupTitleList.add(newGroupTitles[i]);
							int newcredit = Common.intval(newCreditHighters[i]);
							creditsHigherList.add(newcredit + "");
							starsList.add(newStarses[i]);
							colorList.add("");
							groupAvatarList.add("");
							projectList.add(projects[i]);
						}
					}
					for (int i = 0; i < updateLength; i++) {
						if (!creditsHighers[i].matches("-?\\d+")) {
							creditsHighers[i] = "0";
						}
						idList.add(updateIds[i]);
						groupTitleList.add(groupTitles[i]);
						creditsHigherList.add(creditsHighers[i]);
						starsList.add(starses[i]);
						colorList.add(colors[i]);
						groupAvatarList.add(groupAvatars[i]);
						projectList.add("0");
					}
					int zeroNum = 0, negativeNum = 0, size = groupTitleList.size();
					for (int i = 0; i < size; i++) {
						if (creditsHigherList.get(i).equals("0")) {
							zeroNum += 1;
						} else if (Integer.parseInt(creditsHigherList.get(i)) < 0) {
							negativeNum += 1;
						}
						for (int j = size - 1; j > i; j--) {
							if (creditsHigherList.get(i).equals(creditsHigherList.get(j))) {
								request.setAttribute("return", true);
								request.setAttribute("message_key","usergroups_update_credits_duplicate");
								return mapping.findForward("message");
							}
						}
					}
					if (zeroNum < 1 || negativeNum < 1) {
						request.setAttribute("return", true);
						request.setAttribute("message_key", "usergroups_update_credits_invalid");
						return mapping.findForward("message");
					}
					boolean canDel = false;
					if (delIds != null && delIds.length > 0) {
						for (int i = 0; i < delIds.length; i++) {
							List<Map<String, String>> usergroup = dataBaseService.executeQuery("select creditshigher,creditslower from jrun_usergroups where groupid="+ delIds[i]);
							int credits = Common.intval(usergroup.get(0).get("creditshigher"));
							if (credits < 0) {
								for (int k = 0; k < size; k++) {
									if (Integer.parseInt(creditsHigherList.get(k)) < 0&& !idList.get(k).equals(delIds[i])) {
										canDel = true;
									}
								}
								if (!canDel) {
									request.setAttribute("return", true);
									request.setAttribute("message_key","usergroups_update_credits_invalid");
									return mapping.findForward("message");
								}
							} else if (credits == 0) {
								for (int k = 0; k < size; k++) {
									if (Integer.parseInt(creditsHigherList.get(k)) == 0	&& !idList.get(k).equals(delIds[i])) {
										canDel = true;
									}
								}
								if (!canDel) {
									request.setAttribute("return", true);
									request.setAttribute("message_key","usergroups_update_credits_invalid");
									return mapping.findForward("message");
								}
							}
							dataBaseService.runQuery("delete from jrun_usergroups where groupid="+ delIds[i], true);
						}
						int count = 0;
						for(int i=0;i<delIds.length; i++){
							for(int j=0;j<idList.size();j++){
								if(delIds[i].equals(idList.get(j))){
									creditsHigherList.remove(j);
									groupTitleList.remove(j);
									starsList.remove(j);
									colorList.remove(j);
									groupAvatarList.remove(j);
									idList.remove(j);
									projectList.remove(j);
									count++;
									break;
								}
							}
						}
						size = size - count;
					}
					String temp = "";
					for (int i = 0; i < size; i++) {
						for (int j = size - 1; j > i; j--) {
							if (Integer.parseInt(creditsHigherList.get(i)) > Integer.parseInt(creditsHigherList.get(j))) {
								temp = creditsHigherList.get(i);
								creditsHigherList.set(i, creditsHigherList.get(j));
								creditsHigherList.set(j, temp);
								temp = groupTitleList.get(i);
								groupTitleList.set(i, groupTitleList.get(j));
								groupTitleList.set(j, temp);
								temp = starsList.get(i);
								starsList.set(i, starsList.get(j));
								starsList.set(j, temp);
								temp = colorList.get(i);
								colorList.set(i, colorList.get(j));
								colorList.set(j, temp);
								temp = groupAvatarList.get(i);
								groupAvatarList.set(i, groupAvatarList.get(j));
								groupAvatarList.set(j, temp);
								temp = idList.get(i);
								idList.set(i, idList.get(j));
								idList.set(j, temp);
								if (j < projectList.size()) {
									temp = projectList.get(i);
									projectList.set(i, projectList.get(j));
									projectList.set(j, temp);
								} else if (i < projectList.size()) {
									temp = projectList.get(projectList.size() - 1);
									projectList.set(projectList.size() - 1, projectList.get(i));
									projectList.set(i, temp);
								}
							}
						}
					}
					creditsHigherList.set(0, "-999999999");
					creditsHigherList.add("999999999");
					for (int i = 0; i < size; i++) {
						userGroup = userGroupService.findUserGroupById(new Short(idList.get(i)));
						if (userGroup == null) {
							short star = (short)Common.range(Common.intval(starsList.get(i)), 32767, 0);
							userGroup = new Usergroups();
							userGroup.setGrouptitle(groupTitleList.get(i));
							userGroup.setCreditshigher(Integer.parseInt(creditsHigherList.get(i)));
							userGroup.setStars(star);
							userGroup.setColor(colorList.get(i));
							userGroup.setGroupavatar(groupAvatarList.get(i));
							userGroup.setCreditslower(Integer.parseInt(creditsHigherList.get(i + 1)));
							userGroup.setMagicsdiscount((byte) 0);
							userGroup.setAllowmagics((byte) 0);
							userGroup.setMaxmagicsweight((short) 0);
							userGroup.setTradestick((byte) 0);
							userGroup.setReadaccess((short)1);
							userGroup.setAllowvisit((byte)1);
							if (projectList.get(i) != null && !"".equals(projectList.get(i)) && !projectList.get(i).equals("0")) {
								Projects project = userGroupService .findProjectsById(new Short(projectList.get(i)));
								Map projectMap = dataParse.characterParse(project .getValue(), false);
								userGroup = (Usergroups) setValues(userGroup,projectMap);
							}
							userGroupService.insertUserGroup(userGroup);
						} else {
							short star = (short)Common.range(Common.intval(starsList.get(i)), 32767, 0);
							userGroup.setGrouptitle(groupTitleList.get(i));
							userGroup.setCreditshigher(Integer.parseInt(creditsHigherList.get(i)));
							userGroup.setStars(star);
							userGroup.setColor(colorList.get(i));
							userGroup.setGroupavatar(groupAvatarList.get(i));
							userGroup.setCreditslower(Integer.parseInt(creditsHigherList.get(i + 1)));
							userGroupService.modifyUserGroup(userGroup);
						}
					}
					Cache.updateCache("usergroup");
					idList = null;
					groupTitleList = null;
					creditsHigherList = null;
					starsList = null;
					colorList = null;
					groupAvatarList = null;
					projectList = null;
					request.setAttribute("message_key", "usergroups_update_succeed");
					request.setAttribute("url_forward", "admincp.jsp?action=usergroups");
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		} else if(type != null && "special".equals(type)) {
			try{
				if(submitCheck(request, "groupsubmit")){
					String[] newGroupTitles = request.getParameterValues("newgrouptitle");
					int insertLength = newGroupTitles.length;
					String[] groupTitles = request.getParameterValues("grouptitle");
					String[] starses = request.getParameterValues("stars");
					String[] colors = request.getParameterValues("color");
					String[] groupAvatars = request.getParameterValues("groupavatar");
					String[] newstars = request.getParameterValues("newstars");
					String[] newcolor = request.getParameterValues("newcolor");
					String[] newgroupavatar = request.getParameterValues("newgroupavatar");
					int updateLength = 0;
					if (groupTitles != null) {
						updateLength = groupTitles.length;
					}
					List<String> idList = new ArrayList<String>();
					List<String> groupTitleList = new ArrayList<String>();
					List<String> starsList = new ArrayList<String>();
					List<String> colorList = new ArrayList<String>();
					List<String> groupAvatarList = new ArrayList<String>();
					for (int i = 0; i < insertLength; i++) {
						if (!newGroupTitles[i].equals("")) {
							idList.add("-1");
							groupTitleList.add(newGroupTitles[i]);
							starsList.add(newstars[i]);
							colorList.add(newcolor[i]);
							groupAvatarList.add(newgroupavatar[i]);
						}
					}
					for (int i = 0; i < updateLength; i++) {
						idList.add(updateIds[i]);
						groupTitleList.add(groupTitles[i]);
						starsList.add(starses[i]);
						colorList.add(colors[i]);
						groupAvatarList.add(groupAvatars[i]);
					}
					int size = groupTitleList.size();
					for (int i = 0; i < size; i++) {
						short star = (short)Common.range(Common.intval(starsList.get(i)), 32767, 0);
						String sql = "INSERT INTO jrun_usergroups(grouptitle,stars,color,groupavatar,type,magicsdiscount,allowmagics,maxmagicsweight,tradestick,allowvisit,readaccess)values('"
								+ Common.addslashes(groupTitleList.get(i))
								+ "','"
								+ star
								+ "','"
								+ Common.addslashes(colorList.get(i))
								+ "','"
								+ Common.addslashes(groupAvatarList.get(i))
								+ "','special','0','0','0','0','1','1')";
						if (!idList.get(i).equals("-1")) {
							sql = "UPDATE jrun_usergroups set grouptitle='"
									+ Common.addslashes(groupTitleList.get(i)) + "',stars='" + star
									+ "',color='" + Common.addslashes(colorList.get(i))
									+ "',groupavatar='" + Common.addslashes(groupAvatarList.get(i))
									+ "' where groupid=" + idList.get(i);
						}
						dataBaseService.runQuery(sql, true);
					}
					if (delIds != null && delIds.length != 0) {
						dataBaseService.runQuery("delete from jrun_usergroups where groupid in (" + Common.implodeids(delIds) + ")", true);
					}
					Cache.updateCache("usergroup");
					idList = null;
					groupTitleList = null;
					starsList = null;
					colorList = null;
					groupAvatarList = null;
					request.setAttribute("message_key","usergroups_update_succeed");
					request.setAttribute("url_forward", "admincp.jsp?action=usergroups");
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		} else if(type != null && "system".equals(type)) {
			try{
				if(submitCheck(request, "groupsubmit")){
					String[] groupTitles = request.getParameterValues("grouptitle");
					String[] starses = request.getParameterValues("stars");
					String[] colors = request.getParameterValues("color");
					String[] groupAvatars = request.getParameterValues("groupavatar");
					int updateLength = groupTitles.length;
					for (int i = 0; i < updateLength; i++) {
						short star = (short)Common.range(Common.intval(starses[i]), 32767, 0);
						dataBaseService.runQuery("update jrun_usergroups set grouptitle='"+ Common.addslashes(groupTitles[i]) + "',stars=" + star+ ",color='" + Common.addslashes(colors[i]) + "',groupavatar='"+ Common.addslashes(groupAvatars[i]) + "' where groupid="+ updateIds[i], true);
					}
					Cache.updateCache("usergroup");
					request.setAttribute("message_key","usergroups_update_succeed");
					request.setAttribute("url_forward", "admincp.jsp?action=usergroups");
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		}
		if (request.getParameter("do") != null) {
			Common.setResponseHeader(response);
			String groupid = request.getParameter("sgroupid");
			List<Map<String, String>> counts = dataBaseService.executeQuery("SELECT COUNT(*) as count FROM jrun_members WHERE groupid='"+ groupid + "'");
			int count = 0;
			if (counts != null && counts.size() > 0) {
				count = Common.toDigit(counts.get(0).get("count"));
			}
			List<Map<String, String>> memberlist = dataBaseService.executeQuery("select uid,username from jrun_members as m where m.groupid = "+ groupid + " limit 80");
			try {
				StringBuffer result = new StringBuffer();
				if (memberlist != null && memberlist.size() > 0) {
					for (Map<String, String> member : memberlist) {
						result.append("<span style=\"display: block; float: left; width: 8em; overflow: hidden; margin: 2px; height: 1.5em;\"><a href=\""+ request.getContextPath()+ "/space.jsp?action=viewpro&uid="+ member.get("uid") + "\" target=\"_blank\">"+ member.get("username") + "</a></span> ");
					}
					String ss = count > 80 ? "&nbsp;<a href=\"admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash="+Common.formHash(request)+"&usergroupids="+ groupid + "\">"+getMessage(request, "more")+"</a>": "";
					response.getWriter().write(result.toString());
					response.getWriter().write("<br>");
					response.getWriter().write("<div align=\"right\" style=\"clear: both;\"><br />"+getMessage(request, "usernum")+ count + ss + "</div>");
					response.getWriter().close();
				} else {
					response.getWriter().write("<br>");
					response.getWriter().write("<div align=\"right\" style=\"clear: both;\"><br />"+getMessage(request, "usernum")+"0</div>");
					response.getWriter().close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			List<Projects> projectslist = userGroupService.findProjectsByType("group");
			request.setAttribute("projectslist", projectslist);
			List<Map<String, String>> memberlist = dataBaseService.executeQuery("select groupid,grouptitle,creditshigher,creditslower,stars,color,groupavatar from jrun_usergroups where type='member' order by creditslower");
			List<Map<String, String>> speciallist = dataBaseService.executeQuery("select groupid,grouptitle,stars,color,groupavatar from jrun_usergroups where type='special'");
			List<Map<String, String>> systemlist = dataBaseService.executeQuery("select groupid,grouptitle,stars,color,groupavatar from jrun_usergroups where type='system'");
			request.setAttribute("memberusergoups", memberlist);
			request.setAttribute("systemlist", systemlist);
			request.setAttribute("spaciallist", speciallist);
			return mapping.findForward("userGroups");
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward adminGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Admingroups adminGroup = null;
		String groupId = null;
		String adminId = null;
		String [] actionarray = new String[]{"settings", "forumadd", "forumsedit", "forumsmerge",
				"forumdetail", "forumdelete", "forumcopy", "moderators", "threadtypes", "memberadd", "members:creditsubmit:editcreditsubmit:newsletterinit:newlettersubmit:todeletemember", "membersmerge:editmembersmerge",
				"toeditgroups:editgroups", "toaccess:access", "toeditcredits:editcredits", "toeditmedal:editmedal", "toedituserinfo:edituserinfo", "editprofilefields:profilefields", "ipban:editipban", "usergroups",
				"admingroups", "ranks:editranks", "announcements", "styles", "templates", "tpladd", "tpledit", "modmembers",
				"modthreads", "modreplies", "recyclebin", "tenpay", "orders", "forumlinks", "onlinelist", "medals",
				"censor", "jspruncodes", "tags", "smilies", "icons", "attachtypes", "adv", "advadd", "advedit", "export:exportData", "import:importData:importFile:importZipFile",
				"runquery", "optimize", "attachments", "counter", "threads:threadsbatch:threadssearch", "prune", "pmprune", "updatecache", "jswizard:gojssetting:jssetting", "creditwizard:toCreditExpression:toCreditPurpose",
				"fileperms", "crons:cronsedit", "google_config","baidu_config","pluginsconfig", "plugins", "pluginsedit", "pluginhooks", "pluginvars", "illegallog", "ratelog", "modslog", "medalslog",
				"banlog", "cplog", "creditslog", "errorlog","safety"};
		if ("yes".equals(request.getParameter("submit"))) {
			try{
				if(submitCheck(request, "groupsubmit")){
					adminId = request.getParameter("edit");
					if (adminId != null && !"".equals(adminId)) {
						List<Map<String,String>> usergrouplist = dataBaseService.executeQuery("SELECT groupid, radminid FROM jrun_usergroups WHERE groupid='"+adminId+"'");
						if(usergrouplist==null || usergrouplist.size()<=0){
							request.setAttribute("return", true);
							request.setAttribute("message_key","undefined_action");
							return mapping.findForward("message");
						}
						if(usergrouplist.get(0).get("radminid").equals("1")){
							Map<String,String> adminactions  = new HashMap<String,String>();
							for(String action:actionarray){
								String actionvalue = request.getParameter("disabledaction["+action+"]");
								if(actionvalue!=null&&actionvalue.equals("0")){
									adminactions.put(action, "1");
								}
							}
							String disabledaction = dataParse.combinationChar(adminactions);
							dataBaseService.runQuery("REPLACE INTO jrun_adminactions (admingid, disabledactions)VALUES ('"+adminId+"', '"+Common.addslashes(disabledaction)+"')",true);
						}else{
							adminGroup = userGroupService.findAdminGroupById(new Short(adminId));
							adminGroup = (Admingroups) Common.setValues(adminGroup, request);
							userGroupService.modifyAdminGroup(adminGroup);
							Cache.updateCache("usergroup","usergroup");
						}
						request.setAttribute("message_key", "a_member_admingroups_edit_succeed");
						request.setAttribute("url_forward", "admincp.jsp?action=admingroups");
						return mapping.findForward("message");
					}
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		}
		if (request.getParameter("edit") != null && request.getParameter("edit").matches("\\d+")) {
			groupId = request.getParameter("edit");
			List<Map<String, String>> admingroups = dataBaseService.executeQuery("select a.*,u.grouptitle,u.radminid,aa.disabledactions from jrun_admingroups as a left join jrun_usergroups as u on a.admingid=u.groupid left join jrun_adminactions as aa on aa.admingid=a.admingid where a.admingid="+ groupId);
			if(admingroups==null||admingroups.size()<=0){
				request.setAttribute("message_key","undefined_action");
				return mapping.findForward("message");
			}
			Map<String,String> admingroup = admingroups.get(0);
			request.setAttribute("edit", "yes");
			request.setAttribute("admingroups", admingroup);
			if(admingroup.get("radminid").equals("1")){
				String [] actionarrayname = new String[]{"<b>"+getMessage(request, "admingroups_edit_action_settings")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_forumadd")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_forumsedit")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_forumsmerge")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_forumdetail")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_forumdelete")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_forumcopy")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_moderators")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_threadtypes")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_memberadd")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_members")+"</b><br /><span class=\"smalltxt\">"+getMessage(request, "admingroups_edit_action_members_comment")+"</span>","<b>"+getMessage(request, "admingroups_edit_action_membersmerge")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_editgroups")+"</b><span class=\"smalltxt\">"+getMessage(request, "admingroups_edit_action_editgroups_comment")+"</span>","<b>"+getMessage(request, "admingroups_edit_action_access")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_editcredits")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_editmedals")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_memberprofile")+"</b><span class=\"smalltxt\">"+getMessage(request, "admingroups_edit_action_members_comment")+"</span>","<b>"+getMessage(request, "admingroups_edit_action_profilefields")+"</b>","<b>"+getMessage(request, "admingroups_edit_ban_ip")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_usergroups")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_admingroups")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_ranks")+"</b>","<b>"+getMessage(request, "admingroups_edit_post_announce")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_styles")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_templates")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_tpladd")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_tpledit")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_modmembers")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_modthreads")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_modreplies")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_recyclebin")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_alipay")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_orders")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_forumlinks")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_onlinelist")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_medals")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_censor")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_jspruncodes")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_tags")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_smilies")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_icons")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_attachtypes")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_adv")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_advadd")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_advedit")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_export")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_import")+"</b><span class=\"smalltxt\">"+getMessage(request, "admingroups_edit_action_editgroups_comment")+"</span>","<b>"+getMessage(request, "admingroups_edit_action_runquery")+"</b><span class=\"smalltxt\">"+getMessage(request, "admingroups_edit_action_editgroups_comment")+"</span>","<b>"+getMessage(request, "admingroups_edit_action_optimize")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_attachments")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_counter")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_threads")+"</b>","<b>"+getMessage(request, "admingroups_edit_mass_prune")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_pmprune")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_updatecache")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_jswizard")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_creditwizard")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_fileperms")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_crons")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_google_config")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_baidu_config")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_pluginsconfig")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_plugins")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_pluginsedit")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_pluginhooks")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_pluginvars")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_illegallog")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_ratelog")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_modslog")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_medalslog")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_banlog")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_cplog")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_creditslog")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_errorlog")+"</b>","<b>"+getMessage(request, "admingroups_edit_action_safety")+"</b>"};
				request.setAttribute("actionarrayname",actionarrayname);
				request.setAttribute("actionarray", actionarray);
				String disabledactions = admingroup.get("disabledactions");
				Map<String,String> disabledactionsMap = dataParse.characterParse(disabledactions, false);
				request.setAttribute("disabledactionsMap", disabledactionsMap);
			}
			return mapping.findForward("adminGroups");
		}
		List<Map<String, String>> admingrouplist = dataBaseService.executeQuery("select grouptitle,type,radminid,groupid from jrun_usergroups as ugp where ugp.radminid != 0 and ugp.groupid!=1");
		request.setAttribute("adminGroupList", admingrouplist);
		return mapping.findForward("adminGroups");
	}
	@SuppressWarnings("unused")
	public ActionForward profileFields(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Profilefields profile = null;
		if ("yes".equals(request.getParameter("submit"))) {
			try{
				if(submitCheck(request, "editsubmit")){
					if (request.getParameter("fieldid") != null&& request.getParameter("fieldid").matches("\\d+")) {
						profile = memberService.findProfileById(new Short(request.getParameter("fieldid")));
						String title = request.getParameter("title");
						String description = request.getParameter("description");
						String size = request.getParameter("size");
						String invisible = request.getParameter("invisible");
						String required = request.getParameter("required");
						String unchangeable = request.getParameter("unchangeable");
						String showinthread = request.getParameter("showinthread");
						String selective = request.getParameter("selective");
						String choices = request.getParameter("choices");
						if (title != null && !"".equals(title)) {
							profile.setTitle(title);
						}
						if (description != null) {
							profile.setDescription(description);
						}
						if (size != null && size.matches("\\d+")&& Integer.parseInt(size) < 256&& Integer.parseInt(size) > 0) {
							profile.setSize(new Short(size));
						}
						if (invisible != null && invisible.matches("0|1")) {
							profile.setInvisible(new Byte(invisible));
						}
						if (required != null && required.matches("0|1")) {
							profile.setRequired(new Byte(required));
						}
						if (unchangeable != null && unchangeable.matches("0|1")) {
							profile.setUnchangeable(new Byte(unchangeable));
						}
						if (showinthread != null && showinthread.matches("0|1")) {
							profile.setShowinthread(new Byte(showinthread));
						}
						if (selective != null && selective.matches("0|1")) {
							profile.setSelective(new Byte(selective));
						}
						if (choices != null) {
							profile.setChoices(choices);
						}
						memberService.modifyProfile(profile);
						request.setAttribute("message_key", "a_member_fields_edit_succeed");
						request.setAttribute("url_forward", "admincp.jsp?action=profilefields");
						return mapping.findForward("message");
					}
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		}
		if (request.getParameter("edit") != null&& request.getParameter("edit").matches("\\d+")) {
			Short fieldId = Short.parseShort(request.getParameter("edit"));
			profile = memberService.findProfileById(fieldId);
			request.setAttribute("profile", profile);
			request.setAttribute("edit", "yes");
			return mapping.findForward("profileFields");
		}
		try{
			if(submitCheck(request, "fieldsubmit")){
				String[] delIds = request.getParameterValues("delid");
				update: {
					String[] fieldIds = request.getParameterValues("fieldid");
					if (fieldIds != null && fieldIds.length != 0) {
						for (int i = 0, j = i + 1; i < fieldIds.length; i++, j++) {
							profile = memberService.findProfileById(new Short(fieldIds[i]));
							profile.setTitle(request.getParameter("title" + j) == null ? "": request.getParameter("title" + j));
							profile.setAvailable(new Byte(request.getParameter("available" + j) == null ? "0": request.getParameter("available" + j)));
							profile.setInvisible(new Byte(request.getParameter("invisible" + j) == null ? "0": request.getParameter("invisible" + j)));
							profile.setUnchangeable(new Byte(request.getParameter("unchangeable" + j) == null ? "0": request.getParameter("unchangeable" + j)));
							profile.setShowinthread(new Byte(request.getParameter("showinthread" + j) == null ? "0": request.getParameter("showinthread" + j)));
							if (request.getParameter("displayorder" + j) != null&& request.getParameter("displayorder" + j).matches("\\d+")) {
								profile.setDisplayorder(new Short(request.getParameter("displayorder" + j)));
							}
							memberService.modifyProfile(profile);
						}
					}
				}
				if (request.getParameter("newtitle") != null&& !"".equals(request.getParameter("newtitle"))) {
					profile = new Profilefields();
					profile.setTitle(request.getParameter("newtitle").trim());
					profile.setAvailable((byte) 1);
					profile.setChoices("");
					profile.setSize((short)50);
					memberService.insertProfile(profile);
					dataBaseService.runQuery("ALTER TABLE jrun_memberfields ADD field_"+ profile.getFieldid() + " varchar(50)", true);
				}
				if (delIds != null && delIds.length != 0) {
					for (String id : delIds) {
						Short fieldId = new Short(id.trim());
						memberService.deleteProfile(fieldId);
						dataBaseService.runQuery("ALTER TABLE jrun_memberfields DROP field_" + fieldId,true);
					}
				}
				Cache.updateCache("profilefields");
				request.setAttribute("message_key", "a_member_fields_edit_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=profilefields");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Profilefields> profileList = memberService.findAllProfilefields();
		request.setAttribute("profileList", profileList);
		return mapping.findForward("profileFields");
	}
	@SuppressWarnings("unchecked")
	public ActionForward creditSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm = (UserForm) form;
		HttpSession session  = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		Map<String,String> settings = ForumInit.settings;
		String extcredits = settings.get("extcredits");
		String creditsnotify = settings.get("creditsnotify");
		Map creditsnotifyMap = dataParse.characterParse(creditsnotify,false);
		try{
			if(submitCheck(request, "searchsubmit")){
				List<Map<String, String>> allgroups = dataBaseService.executeQuery("select groupid,grouptitle from jrun_usergroups order by radminid desc,groupid desc");
				request.setAttribute("allGroups", allgroups);
				String sql = memberService.returnsearsql(userForm, true,timeoffset);
				List<Map<String, String>> count = dataBaseService.executeQuery("select count(*) as count " + sql);
				String size = "0";
				if (count.size() > 0) {
					size = count.get(0).get("count");
				}
				request.setAttribute("creditsnotifyMap", creditsnotifyMap);
				Map extcreditMap = dataParse.characterParse(extcredits, true);
				Iterator it = extcreditMap.keySet().iterator();
				String array = "";
				while (it.hasNext()) {
					Object key = it.next();
					array += key + ",";
				}
				if (!array.equals("")) {
					array = array.substring(0, array.length() - 1);
				}
				request.setAttribute("array", array);
				session.setAttribute("cresql", sql);
				request.setAttribute("size", size);
				request.setAttribute("result", "yes");
				request.setAttribute("extcreditMap", extcreditMap);
				count = null;
				return mapping.findForward("creditSubmit");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String, String>> allgroups = dataBaseService.executeQuery("select groupid,grouptitle from jrun_usergroups order by radminid desc,groupid desc");
		Map extcreditMap = dataParse.characterParse(extcredits, true);
		Iterator it = extcreditMap.keySet().iterator();
		StringBuffer temparray = new StringBuffer();
		while (it.hasNext()) {
			Object key = it.next();
			temparray.append(","+key);
		}
		String array = "";
		if (temparray.length()>0) {
			array = temparray.substring(1);
		}
		request.setAttribute("array", array);
		request.setAttribute("creditsnotifyMap", creditsnotifyMap);
		request.setAttribute("allGroups", allgroups);
		request.setAttribute("extcreditMap", extcreditMap);
		return mapping.findForward("creditSubmit");
	}
	@SuppressWarnings("unchecked")
	public ActionForward editCredit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "updatecreditsubmit",true)){
				HttpSession session = request.getSession();
				String credql = (String) session.getAttribute("cresql");
				if(credql==null){
					Common.requestforward(response, "admincp.jsp?action=members&submitname=creditsubmit");
					return null;
				}
				String sendvia = request.getParameter("sendvia");
				if(sendvia.equals("email")&&request.getParameter("end")!=null){
					session.removeAttribute("cresql");
					request.setAttribute("message_key", "a_member_credits_notify_succeed");
					return mapping.findForward("message");
				}
				Map<String,String> settings=ForumInit.settings;
				List<Map<String, String>> memberlist = null;
				Members members = (Members) session.getAttribute("members");
				String updatecredittype = request.getParameter("updatecredittype");
				String sendcreditsletter = request.getParameter("sendcreditsletter");
				String creditsformula = settings.get("creditsformula");
				String subject = request.getParameter("subject");
				String message = request.getParameter("message");
				String pertask = request.getParameter("pertask");
				int perta = Common.toDigit(pertask);
				if (sendcreditsletter != null && sendcreditsletter.equals("1")) {
					if (subject == null || subject.equals("") || message == null|| message.equals("")) {
						request.setAttribute("return",true);
						request.setAttribute("message_key", "newsletter_sm_invalid");
						return mapping.findForward("message");
					}
				}
				int updatecredit[] = new int[8];
				StringBuffer extadd = new StringBuffer();
				if(!updatecredittype.equals("2")){
					 memberlist = dataBaseService.executeQuery("select uid "+ credql);
				}
				if (updatecredittype.equals("0")) {
					boolean flag = false;
					for (int i = 0; i < updatecredit.length; i++) {
						int exts = i + 1;
						String credits = request.getParameter("addextcredits[" + exts+ "]");
						if (credits != null) {
							if (!credits.equals("0")) {
								flag = true;
							}
						}
						credits = credits == null ? "0" : credits;
						extadd.append(",extcredits" + exts + "=extcredits" + exts + "+"+ credits);
					}
					if (flag) {
						if (!extadd.equals("")) {
							StringBuffer uids = new StringBuffer("0");
							for (Map<String, String> member : memberlist) {
								uids.append(",");
								uids.append(member.get("uid"));
							}
							dataBaseService.runQuery("update jrun_members set "+ extadd.substring(1) + ",credits=" + creditsformula+ " where uid in (" + uids.toString() + ")", true);
						}
					} else {
						request.setAttribute("return", true);
						request.setAttribute("message_key","a_member_credits_invalid");
						return mapping.findForward("message");
					}
				} else if (updatecredittype.equals("1")) {
					boolean flag = false;
					for (int i = 0; i < updatecredit.length; i++) {
						int exts = i + 1;
						String resetextcredits = request.getParameter("resetextcredits[" + exts + "]");
						if (resetextcredits != null) {
							extadd.append(",extcredits" + exts + "=0");
							flag = true;
						}
					}
					if (flag) {
						if (!extadd.equals("")) {
							StringBuffer uids = new StringBuffer("0");
							for (Map<String, String> member : memberlist) {
								uids.append(",");
								uids.append(member.get("uid"));
							}
							dataBaseService.runQuery("update jrun_members set "+ extadd.substring(1) + ",credits=" + creditsformula+ " where uid in (" + uids.toString() + ")", true);
						}
					} else {
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_member_credits_invalid");
						return mapping.findForward("message");
					}
				}
				if (sendcreditsletter != null && sendcreditsletter.equals("1")) {
					if (subject.equals("secends") && message.equals("secends")) {
						String creditsnotify = settings.get("creditsnotify");
						Map creditsnotifyMap = dataParse.characterParse(creditsnotify,	false);
						if (creditsnotifyMap != null&& creditsnotifyMap.get("subject") != null) {
							subject = (String) creditsnotifyMap.get("subject");
							message = (String) creditsnotifyMap.get("message");
						}
					} else {
						Map creditsnotifyMap = new HashMap();
						creditsnotifyMap.put("subject", subject);
						creditsnotifyMap.put("message", message);
						String value = dataParse.combinationChar(creditsnotifyMap);
						dataBaseService.runQuery("REPLACE INTO jrun_settings (variable, value) VALUES('creditsnotify','"+ Common.addslashes(value) + "')", true);
						settings.put("creditsnotify", value);
					}
					if (sendvia.equals("email")) {
						memberlist = dataBaseService.executeQuery("select username,newsletter,email "+ credql);
						StringBuffer tomails=new StringBuffer();
						for (Map<String, String> member : memberlist) {
							if(member.get("newsletter").equals("1")){
								tomails.append(","+member.get("username")+" <"+member.get("email")+">");
							}
						}
						if(tomails.length()>0){
							Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
							Mail mail = new Mail(mails);
							mail.sendMessage(mails.get("from"), tomails.substring(1), subject, message, null);
						}
						if (perta > 1) {
							String msg = getMessage(request, "a_member_newsletter_send")+getMessage(request, "newsletter_processing", "0",""+perta);
							request.setAttribute("message", msg);
							request.setAttribute("url_forward","admincp.jsp?action=editcreditsubmit&&updatecredittype=2&sendcreditsletter="+ sendcreditsletter+ "&subject=secends&message=secends&sendvia=email&updatecreditsubmit=yes&end=yes&formHash="+Common.formHash(request));
							memberlist = null;
							return mapping.findForward("message");
						} else {
							session.removeAttribute("cresql");
							memberlist = null;
							request.setAttribute("message_key", "a_member_credits_notify_succeed");
							return mapping.findForward("message");
						}
					} else {
						String username = members.getUsername();
						String uid = members.getUid() + "";
						int timestamp = (Integer)(request.getAttribute("timestamp"));
						StringBuffer sql = new StringBuffer("insert into jrun_pms (msgfrom,msgfromid,msgtoid,folder,new,subject,dateline,message,delstatus) values ");
						String begin = request.getParameter("begin");
						int beint = 0;
						if (begin != null) {
							beint = Integer.valueOf(begin);
						}
						memberlist = dataBaseService.executeQuery("select uid,newsletter,g.maxpmnum as maxpmnum "+ credql+" limit "+beint+","+perta);
						int pers = perta + beint;
						if (perta > 0) {
							int num = 0;
							String uids = "0";
							int size = Common.toDigit(dataBaseService.executeQuery("select count(*) as count "+credql).get(0).get("count"));
							for (Map<String, String> member:memberlist) {
								if (member.get("newsletter").equals("1")&& member.get("maxpmnum") != null&& !member.get("maxpmnum").equals("0")) {
									uids = uids + "," + member.get("uid");
									num++;
									sql.append("('" + username + "','" + uid + "','"+ member.get("uid") + "','inbox','1','"+ Common.addslashes(subject) + "','" + timestamp + "','"+ Common.addslashes(message) + "','0'),");
								}
							}
							dataBaseService.runQuery("update jrun_members set newpm = 1 where uid in ("+ uids + ")", true);
							if(num>0){
								dataBaseService.runQuery(sql.substring(0, sql.length() - 1), true);
							}
							if (pers >= size) {
								session.removeAttribute("cresql");
								memberlist = null;
								request.setAttribute("message_key", "a_member_credits_notify_succeed");
								return mapping.findForward("message");
							} else {
								String msg = getMessage(request, "a_member_newsletter_send")+":"+getMessage(request, "newsletter_processing", beint+"",pers+"");
								beint = pers;
								request.setAttribute("message", msg);
								request.setAttribute("url_forward","admincp.jsp?action=editcreditsubmit&updatecredittype=2&sendcreditsletter="+ sendcreditsletter+ "&subject=secends&message=secends&sendvia=pms&updatecreditsubmit=yes&formHash="+Common.formHash(request)+"&pertask="+ perta + "&begin=" + beint);
								return mapping.findForward("message");
							}
						} else {
							session.removeAttribute("cresql");
							request.setAttribute("message_key", "a_member_credits_notify_succeed");
							return mapping.findForward("message");
						}
					}
				}
				session.removeAttribute("cresql");
				memberlist = null;
				request.setAttribute("message_key", "a_member_credits_succeed");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=members&submitname=creditsubmit");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward membersMerge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		List<Members> sourceMembers = null;
		Members targetMember = null;
		StringBuffer nameString = new StringBuffer();
		if ("yes".equals(request.getParameter("hasconfirmed"))) {
			try{
				if(submitCheck(request, "confirmed")){
					sourceMembers = (List<Members>) session.getAttribute("sourceMembers");
					targetMember = (Members) session.getAttribute("targetMember");
					if(sourceMembers==null||targetMember==null){
						Common.requestforward(response, "admincp.jsp?action=membersmerge");
						return null;
					}
					session.removeAttribute("sourceMembers");
					session.removeAttribute("targetMember");
					for (Members member : sourceMembers) {
						memberService.mergeMembers(member, targetMember);
						nameString.append(","+member.getUsername());
					}
					request.setAttribute("message", getMessage(request, "a_member_merge_succeed", nameString.substring(1),targetMember.getUsername()));
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		} else {
			try{
				if(submitCheck(request, "mergesubmit")){
					String[] sourceNames = request.getParameterValues("sourcenames");
					String targetName = request.getParameter("targetname");
					sourceMembers = new ArrayList<Members>();
					targetMember = memberService.findByName(targetName);
					boolean flag = false;
					for (int i = 0; i < sourceNames.length; i++) {
						if (!"".equals(sourceNames[i])) {
							nameString.append("," + sourceNames[i]);
							Members member = memberService.findByName(sourceNames[i]);
							if (member != null) {
								flag = true;
								if (member.getGroupid() == 1) {
									request.setAttribute("return", true);
									request.setAttribute("message_key","a_member_dont_contain_admin_merge");
									return mapping.findForward("message");
								} else if (member.getUsername().equals(targetName)) {
									request.setAttribute("return", true);
									request.setAttribute("message_key","a_member_sameness");
									return mapping.findForward("message");
								} else {
									sourceMembers.add(member);
								}
							} else {
								request.setAttribute("return", true);
								request.setAttribute("message_key","a_member_merge_invalid");
								return mapping.findForward("message");
							}
						}
					}
					String nameStrings = "";
					if (nameString.length()>0) {
						nameStrings = nameString.substring(1);
					}
					if (!flag) {
						request.setAttribute("return", true);
						request.setAttribute("message_key","a_member_merge_invalid");
						return mapping.findForward("message");
					}
					if (targetMember == null) {
						request.setAttribute("return", true);
						request.setAttribute("message_key","a_member_merge_invalid");
						return mapping.findForward("message");
					}
					session.setAttribute("sourceMembers", sourceMembers);
					session.setAttribute("targetMember", targetMember);
					request.setAttribute("othermsg", "<input type='hidden' name='mergesubmit' value='yes'>");
					request.setAttribute("message", getMessage(request, "a_member_merge_confirm", nameStrings,targetName,nameStrings));
					request.setAttribute("url_forward", request.getContextPath()+ "/user.do?useraction=membersMerge");
					request.setAttribute("msgtype", "form");
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		}
		Common.requestforward(response, "admincp.jsp?action=membersmerge");
		return null;
	}
	@SuppressWarnings("unused")
	public ActionForward banIp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String timeoffset = (String)session.getAttribute("timeoffset");
		Map<String,String> settings = ForumInit.settings;
		Members members = (Members) session.getAttribute("members"); 
		boolean isAdmin = false;
		if (members != null) {
			if (1 == members.getAdminid()) {
				isAdmin = true;
			}
		}
		try{
			if(submitCheck(request, "ipbansubmit")){
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				String ip1 = request.getParameter("ip1new");
				String ip2 = request.getParameter("ip2new");
				String ip3 = request.getParameter("ip3new");
				String ip4 = request.getParameter("ip4new");
				String validitynew = request.getParameter("validitynew");
				String[] delids = request.getParameterValues("delid");
				update: {
					String[] ids = request.getParameterValues("bannedid");
					if (ids != null) {
						Banned banned = null;
						for (int i = 0; i < ids.length; i++) {
							String expiration = request.getParameter("expiration["+ ids[i] + "]");
							if (expiration != null && Common.datecheck(expiration)) {
								expiration = Common.dataToInteger(expiration.trim(),"yyyy-MM-dd",timeoffset)+"";
								if(Common.toDigit(expiration)>0){
									dataBaseService.runQuery("update jrun_banned set expiration='"+ expiration+ "' where id=" + ids[i].trim(), true);
								}else{
									dataBaseService.runQuery("delete from jrun_banned where id=" + ids[i].trim(), true);
								}
							}
						}
					}
				}
				if (delids != null && delids.length != 0) {
					for (String id : delids) {
						dataBaseService.runQuery("delete from jrun_banned where id="+ id, true);
					}
				}
				dataBaseService.runQuery("DELETE FROM jrun_banned WHERE expiration<'"+timestamp+"'",true);
				List<Map<String,String>> iplist = dataBaseService.executeQuery("select ip1,ip2,ip3,ip4,expiration from jrun_banned order by expiration");
				if (validitynew != null && validitynew.matches("\\d+") && ip1 != null&& ip1.matches("(\\d{1,3})|\\*") && ip2 != null&& ip2.matches("(\\d{1,3})|\\*") && ip3 != null&& ip3.matches("(\\d{1,3})|\\*") && ip4 != null&& ip4.matches("(\\d{1,3})|\\*")) {
					if (!isAdmin && (ip1 + ip2 + ip3 + ip4).contains("*")) {
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_member_ipban_nopermission");
						return mapping.findForward("message");
					}
					int days = Common.toDigit(request.getParameter("validitynew").trim());
					if(days>9125){
						days = 9125;
					}
					int expiration = timestamp+days*86400;
					ip1 = ip1.replace("*", "-1");
					ip2 = ip2.replace("*", "-1");
					ip3 = ip3.replace("*", "-1");
					ip4 = ip4.replace("*", "-1");
					for(Map<String,String> ips : iplist){
						if(ips.get("ip1").equals(ip1)&&ips.get("ip2").equals(ip2)&&ips.get("ip3").equals(ip3)&&ips.get("ip4").equals(ip4)){
							request.setAttribute("return", true);
							request.setAttribute("message_key", "ipban_invalid");
							return mapping.findForward("message");
						}
					}
					String onlineip = Common.get_onlineip(request);
					String[]ips = onlineip.split("\\.");
					if((ip1.equals("-1")||ip1.equals(ips[0]))&&(ip2.equals("-1")||ip2.equals(ips[1]))&&(ip3.equals("-1")||ip3.equals(ips[2]))&&(ip4.equals("-1")||ip4.equals(ips[3]))){
						request.setAttribute("return", true);
						request.setAttribute("message_key", "ipban_illegal");
						return mapping.findForward("message");
					}
					Map<String,String> newip = new HashMap<String,String>();
					newip.put("ip1", ip1);
					newip.put("ip2", ip2);
					newip.put("ip3", ip3);
					newip.put("ip4", ip4);
					newip.put("expiration", expiration+"");
					iplist.add(newip);
					dataBaseService.runQuery("insert into jrun_banned(ip1,ip2,ip3,ip4,admin,dateline,expiration)values('"+ ip1 + "','" + ip2 + "','" + ip3 + "','" + ip4+ "','" + members.getUsername() + "','" + timestamp+ "','" + expiration + "')", true);
				}
				StringBuffer buffer = new StringBuffer();
				boolean flag = true;
				for(Map<String,String> ips:iplist){
					String ip11 = ips.get("ip1");
					String ip12 = ips.get("ip2");
					String ip13 = ips.get("ip3");
					String ip14 = ips.get("ip4");
					ip11 = ip11.equals("-1")?"\\d+":ip11;
					ip12 = ip12.equals("-1")?"\\d+":ip12;
					ip13 = ip13.equals("-1")?"\\d+":ip13;
					ip14 = ip14.equals("-1")?"\\d+":ip14;
					buffer.append("|"+ip11+"\\."+ip12+"\\."+ip13+"\\."+ip14);
					if(flag){
						settings.put("ipban_expiration", ips.get("expiration"));
					}
					flag = false;
				}
				settings.put("ipbanned", buffer.length()>0?buffer.substring(1):"");
				request.setAttribute("message_key", "a_member_ipban_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=ipban");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> banlist = dataBaseService.executeQuery("select * from jrun_banned order by expiration");
		IPSeeker seeker = IPSeeker.getInstance();
		MessageResources mr = getResources(request);
		Locale locale = getLocale(request);
		for(Map<String,String> ips:banlist){
			String ip11 = ips.get("ip1");
			String ip12 = ips.get("ip2");
			String ip13 = ips.get("ip3");
			String ip14 = ips.get("ip4");
			String address = seeker.getAddress(ip11 + "."+ ip12 + "." + ip13 + "."+ ip14,mr,locale);
			ip11 = ip11.replace("-1", "*");
			ip12 = ip12.replace("-1", "*");
			ip13 = ip13.replace("-1", "*");
			ip14 = ip14.replace("-1", "*");
			ips.put("ipaddress", ip11+"."+ip12+"."+ip13+"."+ip14);
			ips.put("address", address);
		}
		request.setAttribute("bannedList", banlist);
		String ip = request.getParameter("ip");
		if(ip!=null&&ip.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$")){
			String ips[] = ip.split("\\.");
			request.setAttribute("ip1", ips[0]);
			request.setAttribute("ip2", ips[1]);
			request.setAttribute("ip3", ips[2]);
			request.setAttribute("ip4", ips[3]);
		}
		return mapping.findForward("banIp");
	}
	@SuppressWarnings("unchecked")
	public ActionForward banMember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "bansubmit")){
				String type = request.getParameter("type");
				String userName = request.getParameter("username");
				String banExpiry = request.getParameter("banexpiry");
				String delpost = request.getParameter("delpost");
				String reason = request.getParameter("reason");
				if (reason == null || reason.equals("")) {
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_member_edit_reason_invalid");
					return mapping.findForward("message");
				}
				String groupidnew = "";
				HttpSession session = request.getSession();
				Members members = (Members) session.getAttribute("members");
				boolean iswritlog = false;
				boolean bannew = false;
				Map<String,String> settings = ForumInit.settings;
				if (type != null && !"".equals(type) && userName != null&& !"".equals(userName) && banExpiry != null&& banExpiry.matches("\\d+")) {
					Members member = memberService.findByName(userName);
					if (member != null) {
						int timestamp = (Integer)(request.getAttribute("timestamp"));
						int times = timestamp;
						timestamp = timestamp+Integer.parseInt(banExpiry)*86400;
						String groupId = member.getGroupid()+"";
						List<Map<String,String>> usergroup = dataBaseService.executeQuery("select type from jrun_usergroups where groupid="+member.getGroupid());
						int adminid = member.getAdminid();
						if (usergroup.size()>0 && (usergroup.get(0).get("type").equals("system") && groupId.equals("1"))) {
							request.setAttribute("return", true);
							request.setAttribute("message_key","a_member_edit_illegal");
							return mapping.findForward("message");
						}
						if(adminid>0 && adminid<members.getAdminid()){
							request.setAttribute("return", true);
							request.setAttribute("message_key", "a_member_edit_illegal");
							return mapping.findForward("message");
						}
						String groupGroumes = "";
						HashMap usergroupmap = new HashMap();
							Memberfields memberfields = memberService.findMemberfieldsById(member.getUid());
							if (memberfields == null) {
								memberfields = new Memberfields();
								memberfields.setUid(member.getUid());
								memberfields.setBio("");
								memberfields.setSightml("");
								memberfields.setIgnorepm("");
								memberfields.setSpacename("");
								memberfields.setGroupterms("");
							}
							groupGroumes = memberfields.getGroupterms();
							if ("resume".equals(type)&&(member.getGroupid()==4||member.getGroupid()==5)) {
								iswritlog = true;
								List<Map<String, String>> list = dataBaseService.executeQuery("select groupid FROM jrun_usergroups as u WHERE u.type='member' AND u.creditshigher='0' OR (u.groupid NOT IN ('5', '6', '7') AND u.radminid<>'1' AND u.type<>'member') ORDER BY u.type DESC");
								groupidnew = list.size() > 0 ? list.get(0).get("groupid") : "10";
								if (!groupGroumes.equals("")) {
									Map temmap = dataParse.characterParse(groupGroumes,false);
									if (temmap.get("main") != null) {
										Map mapMap = (Map) temmap.get("main");
										String adminids = (String) (mapMap.get("adminid") == null ? "0" : mapMap.get("adminid"));
										String groupids = (String) (mapMap.get("groupid") == null ? "10" : mapMap.get("groupid"));
											if (mapMap.get("adminid") != null) {
												member.setAdminid(Byte.valueOf(adminids));
											}
											if (mapMap.get("groupid") != null) {
												member.setGroupid(Short.valueOf(groupids));
												groupidnew = groupids;
											}
											member.setGroupid(new Short(groupidnew));
											member.setAdminid(new Byte(adminids));
										if(mapMap.get("bktime")==null){
											temmap.remove("main");
										}else{
											mapMap.put("time", mapMap.get("bktime"));
											mapMap.remove("bktime");
											mapMap.put("groupid", mapMap.get("bkgroupid"));
											mapMap.remove("bkgroupid");
											mapMap.put("adminid", mapMap.get("bkadminid"));
											mapMap.remove("bkadminid");
											temmap.put("main", mapMap);
										}
										String grouptemes = dataParse.combinationChar(temmap);
										memberfields.setGroupterms(grouptemes);
									} else {
										member.setGroupid(new Short(groupidnew));
										member.setAdminid(new Byte("0"));
									}
								} else {
									member.setGroupid(new Short(groupidnew));
									member.setAdminid(new Byte("0"));
								}
							} else if ("post".equals(type)) {
								iswritlog = true;bannew = true;
								groupidnew = "4";
								if (banExpiry != null && !banExpiry.equals("0")) {
									Map mainmap = new HashMap();
									Map extmap = new HashMap();
									Map temmap = null;
									Short groupid = member.getGroupid();
									Byte adminids = member.getAdminid();
									boolean flag = true;
									mainmap.put("time", timestamp);
									if (!groupGroumes.equals("")) {
										temmap = dataParse.characterParse(groupGroumes,false);
										if (temmap.get("ext") != null) {
											extmap = (Map) temmap.get("ext");
										}
										if(temmap.get("main")!=null && (groupid==4||groupid==5)){
											Map mainmaps = (Map)temmap.get("main");
											mainmap.put("groupid", mainmaps.get("groupid")!=null?mainmaps.get("groupid"):groupid+"");
											mainmap.put("adminid", mainmaps.get("adminid")!=null?mainmaps.get("adminid"):adminids+"");
											if(mainmaps.get("bktime")!=null){
												mainmap.put("bktime", mainmaps.get("bktime"));
												mainmap.put("bkgroupid", mainmaps.get("bkgroupid"));
												mainmap.put("bkgroupid", mainmaps.get("bkgroupid"));
											}
											groupId = mainmaps.get("groupid")!=null?mainmaps.get("groupid")+"":groupid+"";
											flag = false;
										}
										if(temmap.get("main")!=null){
											Map mainmaps = (Map)temmap.get("main");
											if(mainmaps.get("time")!=null && mainmaps.get("bktime")==null){
												mainmap.put("bktime", mainmaps.get("time"));
												mainmap.put("bkgroupid", mainmaps.get("groupid"));
												mainmap.put("bkadminid", mainmaps.get("adminid"));
											}
										}
									}
									if(flag){
										mainmap.put("groupid", groupid + "");
										mainmap.put("adminid", adminids + "");
									}
									usergroupmap.put("main", mainmap);
									usergroupmap.put("ext", extmap);
									String grouptemes = dataParse.combinationChar(usergroupmap);
									memberfields.setGroupterms(grouptemes);
								} else {
									Map extmap = new HashMap();
									Map temmap = null;
									if (!groupGroumes.equals("")) {
										temmap = dataParse.characterParse(groupGroumes,false);
										if (temmap.get("ext") != null) {
											extmap = (Map) temmap.get("ext");
										}
										Map mainmaps = new HashMap();
										if(temmap.get("main")!=null){
											mainmaps = (Map)temmap.get("main");
										}
										if(mainmaps.get("bktime")==null && member.getGroupid()!=4 && member.getGroupid()!=5){
											if(mainmaps.get("time")!=null){
												mainmaps.put("bktime", mainmaps.get("time"));
											}
											if(mainmaps.get("groupid")!=null){
												mainmaps.put("bkgroupid", mainmaps.get("groupid"));
											}else{
												mainmaps.put("groupid",member.getGroupid()+"");
											}
											if(mainmaps.get("adminid")!=null){
												mainmaps.put("bkadminid", mainmaps.get("adminid"));
											}else{
												mainmaps.put("adminid",member.getAdminid()+"");
											}
										}
										if(mainmaps.get("time")!=null){
											mainmaps.remove("time");
										}
										usergroupmap.put("main", mainmaps);
										usergroupmap.put("ext", extmap);
										String grouptemes = dataParse.combinationChar(usergroupmap);
										memberfields.setGroupterms(grouptemes);
									}
								}
								member.setAdminid(new Byte("0"));
								member.setGroupid(new Short("4"));
							} else if ("visit".equals(type)) {
								iswritlog = true;bannew = true;
								groupidnew = "5";
								if (banExpiry != null && !banExpiry.equals("0")) {
									Map mainmap = new HashMap();
									Map extmap = new HashMap();
									Map temmap = null;
									boolean flag = true;
									mainmap.put("time", timestamp);
									if (!groupGroumes.equals("")) {
										temmap = dataParse.characterParse(groupGroumes,false);
										if (temmap.get("ext") != null) {
											extmap = (Map) temmap.get("ext");
										}
										if(temmap.get("main")!=null && (member.getGroupid()==4||member.getGroupid()==5)){
											flag = false;
											Map mainmaps = (Map)temmap.get("main");
											mainmap.put("groupid", mainmaps.get("groupid")!=null?mainmaps.get("groupid"):member.getGroupid()+"");
											mainmap.put("adminid", mainmaps.get("adminid")!=null?mainmaps.get("adminid"):member.getAdminid()+"");
											if(mainmaps.get("bktime")!=null){
												mainmap.put("bktime", mainmaps.get("bktime"));
												mainmap.put("bkgroupid", mainmaps.get("bkgroupid"));
												mainmap.put("bkgroupid", mainmaps.get("bkgroupid"));
											}
											groupId = mainmaps.get("groupid")!=null?mainmaps.get("groupid")+"":member.getGroupid()+"";
										}
										if(temmap.get("main")!=null){
											Map mainmaps = (Map)temmap.get("main");
											if(mainmaps.get("time")!=null && mainmaps.get("bktime")==null){
												mainmap.put("bktime", mainmaps.get("time"));
												mainmap.put("bkgroupid", mainmaps.get("groupid"));
												mainmap.put("bkadminid", mainmaps.get("adminid"));
											}
										}
									}
									if(flag){
										mainmap.put("groupid", member.getGroupid() + "");
										mainmap.put("adminid", member.getAdminid() + "");
									}
									usergroupmap.put("main", mainmap);
									usergroupmap.put("ext", extmap);
									String grouptemes = dataParse.combinationChar(usergroupmap);
									memberfields.setGroupterms(grouptemes);
								} else {
									Map extmap = new HashMap();
									Map temmap = null;
									if (!groupGroumes.equals("")) {
										temmap = dataParse.characterParse(groupGroumes,false);
										if (temmap.get("ext") != null) {
											extmap = (Map) temmap.get("ext");
										}
										Map mainmaps = new HashMap();
										if(temmap.get("main")!=null){
											mainmaps = (Map)temmap.get("main");
										}
										if(mainmaps.get("bktime")==null && member.getGroupid()!=5 && member.getGroupid()!=4){
											if(mainmaps.get("time")!=null){
												mainmaps.put("bktime", mainmaps.get("time"));
											}
											if(mainmaps.get("groupid")!=null){
												mainmaps.put("bkgroupid", mainmaps.get("groupid"));
											}else{
												mainmaps.put("groupid",member.getGroupid()+"");
											}
											if(mainmaps.get("adminid")!=null){
												mainmaps.put("bkadminid", mainmaps.get("adminid"));
											}else{
												mainmaps.put("adminid",member.getAdminid()+"");
											}
										}
										if(mainmaps.get("time")!=null){
											mainmaps.remove("time");
										}
										usergroupmap.put("main", mainmaps);
										usergroupmap.put("ext", extmap);
										String grouptemes = dataParse.combinationChar(usergroupmap);
										memberfields.setGroupterms(grouptemes);
									}
								}
								member.setAdminid(new Byte("0"));
								member.setGroupid(new Short("5"));
							}
							if ("1".equals(delpost)&&(members.getAdminid()==1||bannew)) {
								List<Map<String, String>> attalist = dataBaseService.executeQuery("select a.attachment,a.thumb,a.remote from jrun_attachments a where a.uid='"+ member.getUid() + "'");
								String path = JspRunConfig.realPath+ settings.get("attachdir")+"/";
								for (Map<String, String> attach : attalist) {
									Common.dunlink(attach.get("attachment"), Byte.valueOf(attach.get("thumb")), Byte.valueOf(attach.get("remote")), path);
								}
								attalist = null; 
								StringBuffer pidsdelete = new StringBuffer();
								StringBuffer tidsdelete = new StringBuffer();
								List<Map<String,String>> threadlist  = dataBaseService.executeQuery("SELECT pid, fid, tid, first FROM jrun_posts WHERE authorid='"+member.getUid()+"'");
								Map<String,String> fids = new HashMap<String,String>();
								for(Map<String,String>post:threadlist){
									if("1".equals(post.get("first"))){
										tidsdelete.append(","+post.get("tid"));
									}
									fids.put(post.get("fid"), "1");
									pidsdelete.append(","+post.get("pid"));
								}
								if(pidsdelete.length()>0){
									String pids = pidsdelete.substring(1);
									dataBaseService.runQuery("DELETE FROM jrun_attachments WHERE pid IN ("+pids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_posts WHERE pid IN ("+pids+")",true);
								}
								if(tidsdelete.length()>0){
									String tids = tidsdelete.substring(1);
									dataBaseService.runQuery("DELETE FROM jrun_attachments WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_threadsmod WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_threads WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_posts WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_polloptions WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_polls WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_rewardlog WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_trades WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_activities WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_activityapplies WHERE tid IN ("+tids+")",true);
									dataBaseService.runQuery("DELETE FROM jrun_typeoptionvars WHERE tid IN ("+tids+")",true);
								}
								if(!fids.keySet().isEmpty()){
									MessageResources resources = getResources(request);
									Locale locale = getLocale(request);
									Set<String> fidkey = fids.keySet();
									for(String fid:fidkey){
										Common.updateforumcount(fid,resources,locale);
									}
								}
							}
							if(iswritlog){
								String expirydate = "0";
								if (("visit".equals(type) || "post".equals(type)) && banExpiry != null && !banExpiry.equals("0")) {
									expirydate = timestamp + "";
								}
								StringBuffer buffer = new StringBuffer();
								buffer.append(times);
								buffer.append("\t");
								buffer.append(members.getUsername());
								buffer.append("\t");
								buffer.append(members.getGroupid());
								buffer.append("\t");
								buffer.append(Common.get_onlineip(request));
								buffer.append("\t");
								buffer.append(member.getUsername());
								buffer.append("\t");
								buffer.append(groupId);
								buffer.append("\t");
								buffer.append(groupidnew);
								buffer.append("\t");
								buffer.append(expirydate);
								buffer.append("\t");
								buffer.append(reason);
								Log.writelog("banlog",times, buffer.toString());
								memberService.modifyMember(member);
								memberService.modifyMemberfields(memberfields);
							}
						request.setAttribute("message_key", "a_member_edit_succeed");
						request.setAttribute("url_forward", request.getHeader("referer"));
						return mapping.findForward("message");
					} else {
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_member_edit_nonexistence");
						return mapping.findForward("message");
					}
				} else {
					request.setAttribute("message_key", "a_member_edit_succeed");
					request.setAttribute("url_forward", "admincp.jsp?action=banmember");
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=banmember");
		return null;
	}
	public ActionForward searchMembers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "seasubmit",true)){
				UserForm userForm = (UserForm) form;
				String currpage = request.getParameter("page");
				HttpSession session = request.getSession();
				String timeoffset=(String)session.getAttribute("timeoffset");
				String sql = "";
				if (currpage != null) {
					sql = (String) session.getAttribute("sql");
				} else {
					sql = memberService.returnsearsql(userForm, true,timeoffset);
				}
				if(sql==null){
					Common.requestforward(response, "admincp.jsp?action=members");
					return null;
				}
				int count = Integer.valueOf(dataBaseService.executeQuery("select count(*) as count " + sql).get(0).get("count"));
				session.setAttribute("sql", sql);
				int pages = 1;
				if (currpage != null) {
					pages = Common.toDigit(currpage);
				}
				int pagesize = 100;
				LogPage logpage = new LogPage(count, pagesize, pages);
				int beginsize = (pages - 1) * pagesize;
				if (beginsize > count) {
					beginsize = count;
				}
				sql = "select g.type as type,g.grouptitle as grouptitle,m.username as username,m.uid as uid,m.adminid as adminid,m.groupid as groupid,posts,credits "+ sql + " limit " + beginsize + ",100";
				List<Map<String, String>> displaylist = dataBaseService.executeQuery(sql);
				request.setAttribute("lpp", pagesize);
				request.setAttribute("logpage", logpage);
				request.setAttribute("memberList", displaylist);
				List<Map<String,String>> usergroupslist = dataBaseService.executeQuery("select groupid,grouptitle from jrun_usergroups where groupid in (1,2,3)");
				request.setAttribute("usergroupslist", usergroupslist);
				return mapping.findForward("memberEdit");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=members");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward deleteMembers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if ("yes".equals(request.getParameter("hasconfirmed"))) {
			try{
				if(submitCheck(request, "confirmed")){
					String uids = request.getParameter("uids");
					if(uids==null){
						Common.requestforward(response, "admincp.jsp?action=members");
						return null;
					}
					String count = request.getParameter("count");
					if ("yes".equals(request.getParameter("includepost"))) {
						Map<String,String> settings = ForumInit.settings;
						String path = JspRunConfig.realPath+ settings.get("attachdir")+"/";
						List<Map<String, String>> tids = dataBaseService.executeQuery("select t.tid,f.fid from jrun_threads as t left join jrun_forums as f ON t.fid=f.fid where t.authorid in ("+ uids + ")");
						StringBuffer tidbuffer = new StringBuffer();
						List<String> fids = new ArrayList<String>();
						for (Map<String, String> tid : tids) {
							tidbuffer.append("," + tid.get("tid"));
							fids.add(tid.get("fid"));
						}
						if(tidbuffer.length()>0){
							String tidsf = tidbuffer.substring(1);
							List<Map<String, String>> attachments = dataBaseService.executeQuery("select attachment,thumb,remote from jrun_attachments where tid in ("+ tidsf + ")");
							for (Map<String, String> atta : attachments) {
								Common.dunlink(atta.get("attachment"), Byte.valueOf(atta.get("thumb")),Byte.valueOf(atta.get("remote")), path);
							}
							dataBaseService.runQuery("delete from jrun_threads where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_threadsmod where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_relatedthreads where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_posts where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_polls where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_polloptions where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_trades where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_activities where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_activityapplies where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_debates where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_debateposts where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_attachments where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_favorites where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_mythreads where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_myposts where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_subscriptions where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_forumrecommend where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("delete from jrun_typeoptionvars where tid in ("+ tidsf + ")", true);
							dataBaseService.runQuery("DELETE FROM jrun_moderators WHERE uid IN ("+ uids + ")");
							MessageResources resources = getResources(request);
							Locale locale = getLocale(request);
							for(String fid:fids){
								Common.updateforumcount(fid,resources,locale);
							}
						}
						dataBaseService.runQuery("DELETE FROM jrun_access WHERE uid IN ('"+uids+"')", true);
						dataBaseService.runQuery("DELETE FROM jrun_memberfields WHERE uid IN ('"+uids+"')", true);
						dataBaseService.runQuery("DELETE FROM jrun_buddys WHERE uid IN ('"+uids+"') OR buddyid IN ('"+uids+"')", true);
						dataBaseService.runQuery("DELETE FROM jrun_favorites WHERE uid IN ('"+uids+"')", true);
						dataBaseService.runQuery("DELETE FROM jrun_moderators WHERE uid IN ('"+uids+"')", true);
						dataBaseService.runQuery("DELETE FROM jrun_pms WHERE msgfromid IN ('"+uids+"') OR msgtoid IN ('"+uids+"')", true);
						dataBaseService.runQuery("DELETE FROM jrun_subscriptions WHERE uid IN ('"+uids+"')", true);
						List<Map<String, String>> attachments = dataBaseService.executeQuery("SELECT uid, attachment, thumb, remote FROM jrun_attachments WHERE uid IN ('"+uids+"')");
						for (Map<String, String> atta : attachments) {
							Common.dunlink(atta.get("attachment"), Byte.valueOf(atta.get("thumb")),Byte.valueOf(atta.get("remote")), path);
						}
						dataBaseService.runQuery("DELETE FROM jrun_attachments WHERE uid IN ('"+uids+"')",true);
						dataBaseService.runQuery("DELETE FROM jrun_posts WHERE authorid IN ('"+uids+"')",true);
						dataBaseService.runQuery("DELETE FROM jrun_trades WHERE sellerid IN ('"+uids+"')",true);
					} else {
						dataBaseService.runQuery("DELETE FROM jrun_access WHERE uid IN (" + uids + ")",true);
						dataBaseService.runQuery("DELETE FROM jrun_memberfields WHERE uid IN (" + uids+ ")", true);
						dataBaseService.runQuery("DELETE FROM jrun_buddys WHERE uid IN (" + uids+ ") or buddyid in (" + uids + ")", true);
						dataBaseService.runQuery("DELETE FROM jrun_favorites WHERE uid IN ("+ uids + ")", true);
						dataBaseService.runQuery("DELETE FROM jrun_moderators WHERE uid IN (" + uids+ ")", true);
						dataBaseService.runQuery("DELETE FROM jrun_pms WHERE msgfromid IN (" + uids+ ") or msgtoid in (" + uids + ")", true);
						dataBaseService.runQuery("DELETE FROM jrun_subscriptions WHERE uid IN (" + uids+ ")", true);
					}
					dataBaseService.runQuery("delete from jrun_members where uid in ("+ uids + ")", true);
					request.setAttribute("message", getMessage(request, "a_member_delete_succeed", count));
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		}else{
			try{
				if(submitCheck(request, "delsubmit")){
					HttpSession session = request.getSession();
					String timeoffset=(String)session.getAttribute("timeoffset");
					UserForm userForm = (UserForm) form;
					String sql = memberService.returnsearsql(userForm, true,timeoffset);
					if(sql.indexOf(" where ")==-1){
						sql = sql +" where m.adminid<>1 and g.groupid<>1";
					}else{
						sql = sql +" and m.adminid<>1 and g.groupid<>1";
					}
					List<Map<String, String>> memberlist = dataBaseService.executeQuery("select uid " + sql);
					if (memberlist == null || memberlist.size() == 0) {
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_member_no_find_deluser");
						return mapping.findForward("message");
					} else {
						String uid = "";
						String comma = "";
						for (Map<String,String> member:memberlist) {
							uid += comma + member.get("uid");
							comma = ",";
						}
						String confirmInfo = getMessage(request, "a_member_delete_confirm", memberlist.size()+"");
						String otherInfo = "<input type='checkbox' name='includepost' value='yes' class='checkbox'>"+getMessage(request, "a_member_delete_post")+"<input type='hidden' name='uids' value='"+uid+"'><input type='hidden' name='count' value='"+memberlist.size()+"'>";
						String commitPath = request.getContextPath()+ "admincp.jsp?action=deletemember";
						request.setAttribute("isnewline", "yes");
						request.setAttribute("msgtype", "form");
						request.setAttribute("message", confirmInfo);
						request.setAttribute("othermsg", otherInfo);
						request.setAttribute("url_forward", commitPath);
						return mapping.findForward("message");
					}
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		}
		Common.requestforward(response, "admincp.jsp?action=members");
		return null;
	}
	public ActionForward editDeleteMembers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		try{
			if(submitCheck(request, "deletesubmit")){
				String uids[] = request.getParameterValues("uids");
				StringBuffer uid = new StringBuffer();String comma = "";
				if (uids != null) {
					for (int i = 0; i < uids.length; i++) {
						uid.append(comma + uids[i]);
						comma = ",";
					}
					List<Map<String, String>> memberList = dataBaseService.executeQuery("select count(*) as count from jrun_members where uid in ("+ uid + ")");
					String count = memberList.get(0).get("count");
					String confirmInfo = getMessage(request, "a_member_delete_confirm", count);
					String otherInfo = "<input type='checkbox' name='includepost' value='yes' class='checkbox'>"+getMessage(request, "a_member_delete_post")+"<input type='hidden' name='uids' value='"+uid+"'><input type='hidden' name='count' value='"+count+"'>";
					String commitPath = "admincp.jsp?action=deletemember";
					request.setAttribute("msgtype", "form");
					request.setAttribute("message", confirmInfo);
					request.setAttribute("othermsg", otherInfo);
					request.setAttribute("isnewline", "yes");
					request.setAttribute("url_forward", commitPath);
					return mapping.findForward("message");
				} else {
					request.setAttribute("return", true);
					request.setAttribute("message_key","a_member_no_find_deluser");
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=members");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward goEditGroupOnUser(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String uid = request.getParameter("memberid");
		List<Map<String, String>> members = dataBaseService.executeQuery("SELECT m.uid, m.username, m.adminid, m.groupid, m.groupexpiry, m.extgroupids, m.credits, mf.groupterms, u.type AS grouptype, u.grouptitle FROM jrun_members m LEFT JOIN jrun_memberfields mf ON mf.uid=m.uid LEFT JOIN jrun_usergroups u ON u.groupid=m.groupid WHERE m.uid='"+ uid + "'");
		if (members == null || members.size() == 0) {
			request.setAttribute("message_key", "a_member_edit_nonexistence");
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		Map<String, String> member = members.get(0);
		boolean isfounder = (Boolean) request.getAttribute("isfounder");
		if (!isfounder&& ("1".equals(member.get("adminid")) || "1".equals(member.get("groupid")))) {
			request.setAttribute("message_key", "a_member_super_edit_admin_allow");
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		List<Map<String, String>> usergrouplist = dataBaseService.executeQuery("SELECT groupid, radminid, type, grouptitle, creditshigher, creditslower FROM jrun_usergroups WHERE groupid NOT IN ('6', '7') ORDER BY creditshigher, groupid");
		List<Map<String, String>> systemgroups = new ArrayList<Map<String, String>>();
		List<Map<String, String>> specialgroups = new ArrayList<Map<String, String>>();
		List<Map<String, String>> membergroups = new ArrayList<Map<String, String>>();
		List<Map<String, String>> extgroups = new ArrayList<Map<String, String>>();
		List<Map<String, String>> usergroups = new ArrayList<Map<String, String>>();
		for (Map<String, String> usergroup : usergrouplist) {
			String type = usergroup.get("type");
			if ("system".equals(type)) {
				systemgroups.add(usergroup);
				usergroups.add(usergroup);
			} else if ("special".equals(type)) {
				specialgroups.add(usergroup);
				usergroups.add(usergroup);
			} else if ("member".equals(type)) {
				int creditshigher = Integer.valueOf(usergroup.get("creditshigher"));
				int creditslower = Integer.valueOf(usergroup.get("creditslower"));
				int credits = Common.toDigit(member.get("credits"));
				if (creditshigher <= credits&& credits < creditslower|| usergroup.get("groupid").equals(member.get("groupid"))) {
					membergroups.add(usergroup);
				}
				if (creditshigher == 0) {
					usergroups.add(usergroup);
				}
			}
			if ("special".equals(type)|| !"0".equals(usergroup.get("radminid"))) {
				extgroups.add(usergroup);
			}
		}
		List extList = new ArrayList();
		String extgr = member.get("extgroupids");
		String extggroup[] = extgr.split("\t");
		for (int j = 0; j < extgroups.size(); j++) {
			Map extUser = new HashMap();
			Map<String, String> extusergroup = extgroups.get(j);
			extUser.put(extusergroup, null);
			if (extggroup != null) {
				for (int i = 0; i < extggroup.length; i++) {
					if (extggroup[i].trim().equals(extusergroup.get("groupid"))) {
						extUser.put(extusergroup, "0");
					}
				}
			}
			extList.add(extUser);
		}
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String groupterms = member.get("groupterms");
		if (!groupterms.equals("")) {
			Map result = dataParse.characterParse(groupterms, false);
			if (result.get("main") != null) {
				Map mainMap = (Map) result.get("main");
				String adminid = (mainMap.get("adminid") == null ? "0" : String.valueOf(mainMap.get("adminid")));
				String groupid = (mainMap.get("groupid") == null ? "10": String.valueOf(mainMap.get("groupid")));
				if (mainMap.get("time") != null) {
					String time = (Integer) mainMap.get("time") + "";
					if (mainMap.get("adminid") != null) {
						request.setAttribute("adminid", adminid);
					}
					if (mainMap.get("groupid") != null) {
						request.setAttribute("groupid", groupid);
					}
					request.setAttribute("time", time);
					int day = Common.toDigit(time) - timestamp;
					day = (int)Math.ceil(day /(double)86400);
					request.setAttribute("day", day);
				}
			}
			if (result.get("ext") != null) {
				Map extMap = (Map) result.get("ext");
				Iterator it = extMap.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					int time = (Integer) extMap.get(key);
					int size = extgroups.size();
					for (int j = 0; j < size; j++) {
						Map<String, String> extusergroup = extgroups.get(j);
						HashMap extUser = (HashMap) extList.get(j);
						if (key.toString().trim().equals(extusergroup.get("groupid"))) {
							extUser.put(extusergroup, time);
						}
					}
				}
				extMap = null;
			}
		}
		if (membergroups.size() == 0) {
			membergroups = dataBaseService.executeQuery("SELECT groupid, grouptitle FROM jrun_usergroups WHERE type='member' AND creditshigher>='0' ORDER BY creditshigher LIMIT 1");
		}
		request.setAttribute("member", member);
		request.setAttribute("systemgroups", systemgroups);
		request.setAttribute("specialgroups", specialgroups);
		request.setAttribute("membergroups", membergroups);
		request.setAttribute("usergroups", usergroups);
		request.setAttribute("extusergroup", extList);
		return mapping.findForward("memberbyusergroup");
	}
	@SuppressWarnings( { "deprecation", "unchecked" })
		public ActionForward editMemberUsergroup(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		String memberId = request.getParameter("memberid");
		try{
			if(submitCheck(request, "editsubmit")){
				HashMap mainresult = new HashMap();
				HashMap extresult = new HashMap();
				boolean flag = false;
				int memId = Integer.parseInt(memberId);
				Members member = memberService.findMemberById(memId);
				String groupidnew = request.getParameter("groupidnew");
				HttpSession session = request.getSession();
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				String timeoffset=(String)session.getAttribute("timeoffset");
				String adminid = "0";String extadminid = "0";
				String expgroupidnew = request.getParameter("expgroupidnew");
				List<Map<String, String>> extUserGroup = dataBaseService.executeQuery("select groupid,radminid from jrun_usergroups as u where u.type='special' or u.radminid<>0 order by u.groupid");
				StringBuffer extgroupid = new StringBuffer();
				int size = extUserGroup.size();
				for (int i = 0; i < size; i++) {
					Map<String, String> extuser = extUserGroup.get(i);
					if(groupidnew.equals(extuser.get("groupid"))){
						adminid = extuser.get("radminid");
					}
					if(expgroupidnew.equals(extuser.get("groupid"))){
						extadminid = extuser.get("radminid");
					}
					String extgroupids = request.getParameter("extgroupidsnew["+ extuser.get("groupid") + "]");
					if (extgroupids != null) {
						String time = request.getParameter("extgroupexpirynew["+ extuser.get("groupid") + "]");
						if (time != null && !time.equals("")&& Common.datecheck(time)) {
							int times = Common.dataToInteger(time, "yyyy-MM-dd",timeoffset);
							if(times>timestamp){
								extresult.put(extgroupids, times);
							}
							extgroupid.append(extgroupids + "\t");
						} else {
							extgroupid.append(extgroupids + "\t");
						}
					}
				}
				String date = request.getParameter("expirytype");
				if (date.equals("days")) {
					flag = true;
					String expirydate = request.getParameter("expirydaysnew");
					int days = Common.toDigit(expirydate);
					if (expirydate != null && !expirydate.equals("") && days != 0) {
						int times = timestamp+ days*86400;
						if (expgroupidnew.equals(groupidnew)) {
							request.setAttribute("return", true);
							request.setAttribute("message_key", "a_member_edit_groups_illegal");
							return mapping.findForward("message");
						}
						if (expgroupidnew.equals("1") || expgroupidnew.equals("2") || expgroupidnew.equals("3")) {
							try {
								if (!expgroupidnew.equals("0")) {
									mainresult.put("groupid", expgroupidnew);
									mainresult.put("adminid", expgroupidnew);
								}
								mainresult.put("time", times);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							try {
								if (!expgroupidnew.equals("0")) {
									mainresult.put("groupid", expgroupidnew);
									mainresult.put("adminid", extadminid);
								}
								mainresult.put("time", times);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					String expirydate = request.getParameter("expirydatenew");
					if (expirydate != null && !expirydate.equals("")&& Common.datecheck(expirydate)) {
						if (expgroupidnew.equals(groupidnew)) {
							request.setAttribute("return", true);
							request.setAttribute("message_key", "a_member_edit_groups_illegal");
							return mapping.findForward("message");
						}
						int times =  Common.dataToInteger(expirydate, "yyyy-MM-dd",timeoffset);
						if(times>timestamp){
							if (expgroupidnew.equals("1") || expgroupidnew.equals("2")|| expgroupidnew.equals("3")) {
								if(!expgroupidnew.equals("0")) {
									mainresult.put("groupid", expgroupidnew);
									mainresult.put("adminid", expgroupidnew);
								}
								mainresult.put("time", times);
							}else{
								if(!expgroupidnew.equals("0")) {
									mainresult.put("groupid", expgroupidnew);
									mainresult.put("adminid", extadminid);
								}
								mainresult.put("time", times);
							}
						}
					}
				}
				String reason = request.getParameter("reason");
				member.setExtgroupids(extgroupid.toString().trim());
				HashMap hamap = new HashMap();
				if(!mainresult.isEmpty()){
					hamap.put("main", mainresult);
				}
				if(!extresult.isEmpty()){
					hamap.put("ext", extresult);
				}
				if(!hamap.isEmpty()){
					String res = dataParse.combinationChar(hamap);
					dataBaseService.runQuery("update jrun_memberfields set groupterms='"+ res + "' where uid=" + memberId, true);
				}else{
					dataBaseService.runQuery("update jrun_memberfields set groupterms='' where uid=" + memberId, true);
				}
				if (member.getGroupid() == 4 || member.getGroupid() == 5|| groupidnew.equals("4") || groupidnew.equals("5")) {
					String expirydate = request.getParameter("expirydatenew");
					String expiryday = request.getParameter("expirydaysnew");
					if (flag && expiryday != null && !expiryday.equals("0")) {
						expirydate = (timestamp + Common.toDigit(expiryday)*86400)+"";
					} else if (!flag && expirydate != null && !expirydate.equals("")&& Common.datecheck(expirydate)) {
						expirydate = Common.dataToInteger(expirydate,"yyyy-MM-dd",timeoffset)+ "";
					} else {
						expirydate = "0";
					}
					Members members = (Members) session.getAttribute("members");
					StringBuffer content = new StringBuffer();
					content.append(timestamp);
					content.append("\t");
					content.append(members.getUsername());
					content.append("\t");
					content.append(members.getGroupid());
					content.append("\t");
					content.append(Common.get_onlineip(request));
					content.append("\t");
					content.append(member.getUsername());
					content.append("\t");
					content.append(member.getGroupid());
					content.append("\t");
					content.append(groupidnew);
					content.append("\t");
					content.append(expirydate);
					content.append("\t");
					content.append(reason);
					Log.writelog("banlog", timestamp, content.toString());
				}
				if (groupidnew.equals("1") || groupidnew.equals("2")|| groupidnew.equals("3")) {
					member.setAdminid(new Byte(groupidnew));
				} else{
					member.setAdminid(new Byte(adminid));
				}
				member.setGroupid(Short.parseShort(groupidnew));
				memberService.modifyMember(member);
				member = null;
				extUserGroup = null;
				hamap = null;
				mainresult = null;
				extresult = null;
				request.setAttribute("message_key", "a_member_edit_groups_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=toeditgroups&memberid="+memberId);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=toEditgrouponuser&memberid="+memberId);
		return null;
	}
	public ActionForward goEditPurview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String memberId = request.getParameter("memberid");
		List<Map<String, String>> members = dataBaseService.executeQuery("select uid,username,groupid from jrun_members where uid="+ memberId);
		Map<String, String> member = members.get(0);
		members = null;
		boolean isfounder = (Boolean) request.getAttribute("isfounder");
		if (!isfounder && member.get("groupid").equals("1")) {
			request.setAttribute("message_key","noaccess_isfounder");
			return mapping.findForward("message");
		}
		List<Map<String, String>> forumslist = dataBaseService.executeQuery("select f.fid,a.allowview,a.allowpost,a.allowreply,a.allowgetattach,a.allowpostattach from jrun_forums as f left join jrun_access as a on f.fid=a.fid where f.type<>'group' and f.status=1 and a.uid="+ memberId);
		List<Map<String, String>> forums = dataBaseService.executeQuery("select f.fid,f.name,f.type,f.fup from jrun_forums as f where  f.status=1 ORDER BY f.type<>'group',f.displayorder");
		List<Map<String, String>> disforums = new ArrayList<Map<String, String>>();
		for (Map<String, String> forumi : forums) {
			if ("group".equals(forumi.get("type"))) {
				for (Map<String, String> forumj : forums) {
					if (forumi.get("fid").equals(forumj.get("fup"))&& "forum".equals(forumj.get("type"))) {
						boolean flag = false;
						for (Map<String, String> list : forumslist) {
							if (forumj.get("fid").equals(list.get("fid"))) {
								flag = true;
								forumj.put("allowview", list.get("allowview"));
								forumj.put("allowpost", list.get("allowpost"));
								forumj.put("allowreply", list.get("allowreply"));
								forumj.put("allowgetattach", list.get("allowgetattach"));
								forumj.put("allowpostattach", list.get("allowpostattach"));
								disforums.add(forumj);
							}
						}
						if (!flag) {
							disforums.add(forumj);
						}
						for (Map<String, String> forumk : forums) {
							if (forumj.get("fid").equals(forumk.get("fup"))&& "sub".equals(forumk.get("type"))) {
								boolean flags = false;
								for (Map<String, String> list : forumslist) {
									if (forumk.get("fid").equals(list.get("fid"))) {
										flag = true;
										forumk.put("allowview", list.get("allowview"));
										forumk.put("allowpost", list.get("allowpost"));
										forumk.put("allowreply", list.get("allowreply"));
										forumk.put("allowgetattach", list.get("allowgetattach"));
										forumk.put("allowpostattach", list.get("allowpostattach"));
										disforums.add(forumk);
									}
								}
								if (!flags) {
									disforums.add(forumk);
								}
							}
						}
					}
				}
			}
		}
		forumslist = null;
		forums = null;
		request.setAttribute("member", member);
		request.setAttribute("forumslist", disforums);
		return mapping.findForward("memberpurview");
	}
	public ActionForward editPurview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int memId = Integer.parseInt(request.getParameter("memberid"));
		try{
			if(submitCheck(request, "accesssubmit")){
				List<Map<String, String>> forumslist = dataBaseService.executeQuery("select f.fid from jrun_forums as f where f.type<>'group' and f.status=1");
				for (Map<String, String> forums : forumslist) {
					String defaultnew = request.getParameter("defaultnew["+ forums.get("fid") + "]");
					if (defaultnew == null) {
						String allowviewnew = request.getParameter("allowviewnew["+ forums.get("fid") + "]");
						String sql = "REPLACE INTO jrun_access(uid,fid,allowview,allowpost,allowreply,allowgetattach,allowpostattach)values('"+ memId + "','" + forums.get("fid") + "',";
						if (allowviewnew != null) {
							sql += "'1',";
						} else {
							sql += "'0',";
						}
						String allowpostnew = request.getParameter("allowpostnew["+ forums.get("fid") + "]");
						if (allowpostnew != null) {
							sql += "'1',";
						} else {
							sql += "'0',";
						}
						String allowreplynew = request.getParameter("allowreplynew["+ forums.get("fid") + "]");
						if (allowreplynew != null) {
							sql += "'1',";
						} else {
							sql += "'0',";
						}
						String allowgetattachnew = request.getParameter("allowgetattachnew[" + forums.get("fid") + "]");
						if (allowgetattachnew != null) {
							sql += "'1',";
						} else {
							sql += "'0',";
						}
						String allowpostattachnew = request.getParameter("allowpostattachnew[" + forums.get("fid") + "]");
						if (allowpostattachnew != null) {
							sql += "'1')";
						} else {
							sql += "'0')";
						}
						if (!(allowviewnew == null && allowpostnew == null&& allowreplynew == null && allowgetattachnew == null && allowpostattachnew == null)) {
							dataBaseService.runQuery(sql, true);
							dataBaseService.runQuery("update jrun_members set accessmasks=1 where uid="+memId,true);
						}else{
							dataBaseService.runQuery("delete from jrun_access where fid="+ forums.get("fid") + " and uid=" + memId, true);
						}
					} else {
						dataBaseService.runQuery("delete from jrun_access where fid="+ forums.get("fid") + " and uid=" + memId, true);
					}
				}
				forumslist = null;
				request.setAttribute("message_key", "a_member_access_succeed");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=toaccess&memberid="+memId);
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward goEditCredits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String memberId = request.getParameter("memberid");
		int memId = Integer.parseInt(memberId);
		Members member = memberService.findMemberById(memId);
		boolean isfounder = (Boolean) request.getAttribute("isfounder");
		if (!isfounder && member.getGroupid() == 1) {
			request.setAttribute("message_key","noaccess_isfounder");
			return mapping.findForward("message");
		}
		short groupid = member.getGroupid();
		List<Map<String, String>> usergroups = dataBaseService.executeQuery("select grouptitle,creditshigher,creditslower from jrun_usergroups where groupid="+ groupid);
		Map<String, String> usergroup = usergroups.get(0);
		usergroups = null;
		Map<String, String> settings = ForumInit.settings;
		String extcredits = settings.get("extcredits");
		Map extcreditMap = dataParse.characterParse(extcredits, true);
		String expressions = settings.get("creditsformula");
		settings = null;
		String[] creditsformula = { "digestposts", "posts", "oltime","pageviews", "extcredits1", "extcredits2", "extcredits3","extcredits4", "extcredits5", "extcredits6", "extcredits7","extcredits8" };
		String[] creditsvalue = { member.getDigestposts() + "",member.getPosts() + "", member.getOltime() + "",member.getPageviews() + "", "extcredits1", "extcredits2","extcredits3", "extcredits4", "extcredits5", "extcredits6","extcredits7", "extcredits8" };
		for (int i = 0; i < creditsformula.length; i++) {
			if (creditsformula[i].matches("extcredits[0-8]")) {
				String extcredit = creditsformula[i].substring(creditsformula[i].length() - 1);
				expressions = expressions.replaceAll(creditsformula[i],"extcredits[" + extcredit + "]");
			} else {
				expressions = expressions.replaceAll(creditsformula[i],creditsvalue[i]);
			}
		}
		int extcreditsm[] = new int[8];
		extcreditsm[0] = member.getExtcredits1();
		extcreditsm[1] = member.getExtcredits2();
		extcreditsm[2] = member.getExtcredits3();
		extcreditsm[3] = member.getExtcredits4();
		extcreditsm[4] = member.getExtcredits5();
		extcreditsm[5] = member.getExtcredits6();
		extcreditsm[6] = member.getExtcredits7();
		extcreditsm[7] = member.getExtcredits8();
		request.setAttribute("creditexpressions", expressions);
		request.setAttribute("extcreditMap", extcreditMap);
		request.setAttribute("member", member);
		request.setAttribute("usergroup", usergroup);
		request.setAttribute("extcreditsm", extcreditsm);
		return mapping.findForward("membercredits");
	}
	@SuppressWarnings("unchecked")
	public ActionForward editCredits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String memberId = request.getParameter("memberid");
		try{
			if(submitCheck(request, "creditsubmit")){
				int memId = Integer.parseInt(memberId);
				Members member = memberService.findMemberById(memId);
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				String reason = request.getParameter("reason");
				String extcreditsnew1 = request.getParameter("extcreditsnew[1]");
				String extcreditsnew2 = request.getParameter("extcreditsnew[2]");
				String extcreditsnew3 = request.getParameter("extcreditsnew[3]");
				String extcreditsnew4 = request.getParameter("extcreditsnew[4]");
				String extcreditsnew5 = request.getParameter("extcreditsnew[5]");
				String extcreditsnew6 = request.getParameter("extcreditsnew[6]");
				String extcreditsnew7 = request.getParameter("extcreditsnew[7]");
				String extcreditsnew8 = request.getParameter("extcreditsnew[8]");
				int extcredits1 = StringTOInt(extcreditsnew1);
				int extcredits2 = StringTOInt(extcreditsnew2);
				int extcredits3 = StringTOInt(extcreditsnew3);
				int extcredits4 = StringTOInt(extcreditsnew4);
				int extcredits5 = StringTOInt(extcreditsnew5);
				int extcredits6 = StringTOInt(extcreditsnew6);
				int extcredits7 = StringTOInt(extcreditsnew7);
				int extcredits8 =  StringTOInt(extcreditsnew8);
				HttpSession session = request.getSession();
				if (extcredits1 == member.getExtcredits1()&& extcredits2 == member.getExtcredits2()&& extcredits3 == member.getExtcredits3()&& extcredits4 == member.getExtcredits4()&& extcredits5 == member.getExtcredits5()&& extcredits6 == member.getExtcredits6()&& extcredits7 == member.getExtcredits7()&& extcredits8 == member.getExtcredits8()) {
					request.setAttribute("message_key", "a_member_edit_credits_succeed");
					request.setAttribute("url_forward", "admincp.jsp?action=toeditcredits&memberid="+memberId);
					return mapping.findForward("message");
				} else {
					if (reason == null || reason.equals("")) {
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_member_edit_reason_invalid");
						return mapping.findForward("message");
					} else {
						Members members = (Members) session.getAttribute("members");
						if (member.getExtcredits1() - extcredits1 != 0) {
							int result = extcredits1 - member.getExtcredits1();
							String rates = result + "";
							if (result > 0) {
								rates = "+" + result;
							}
							StringBuffer buffer = new StringBuffer();
							buffer.append(timestamp);
							buffer.append("\t");
							buffer.append(members.getUsername());
							buffer.append("\t");
							buffer.append(members.getGroupid());
							buffer.append("\t");
							buffer.append(member.getUsername());
							buffer.append("\t");
							buffer.append("1");
							buffer.append("\t");
							buffer.append(rates);
							buffer.append("\t");
							buffer.append("0");
							buffer.append("\t");
							buffer.append(reason);
							Log.writelog("ratelog",timestamp, buffer.toString());
							member.setExtcredits1(extcredits1);
						}
						if (member.getExtcredits2() - extcredits2 != 0) {
							int result = extcredits2 - member.getExtcredits2();
							String rates = result + "";
							if (result > 0) {
								rates = "+" + result;
							}
							StringBuffer buffer = new StringBuffer();
							buffer.append(timestamp);
							buffer.append("\t");
							buffer.append(members.getUsername());
							buffer.append("\t");
							buffer.append(members.getGroupid());
							buffer.append("\t");
							buffer.append(member.getUsername());
							buffer.append("\t");
							buffer.append("2");
							buffer.append("\t");
							buffer.append(rates);
							buffer.append("\t");
							buffer.append("0");
							buffer.append("\t");
							buffer.append(reason);
							Log.writelog("ratelog", timestamp, buffer.toString());
							member.setExtcredits2(extcredits2);
						}
						if (member.getExtcredits3() - extcredits3 != 0) {
							int result = extcredits3 - member.getExtcredits3();
							String rates = result + "";
							if (result > 0) {
								rates = "+" + result;
							}
							StringBuffer buffer = new StringBuffer();
							buffer.append(timestamp);
							buffer.append("\t");
							buffer.append(members.getUsername());
							buffer.append("\t");
							buffer.append(members.getGroupid());
							buffer.append("\t");
							buffer.append(member.getUsername());
							buffer.append("\t");
							buffer.append("3");
							buffer.append("\t");
							buffer.append(rates);
							buffer.append("\t");
							buffer.append("0");
							buffer.append("\t");
							buffer.append(reason);
							Log.writelog("ratelog", timestamp, buffer.toString());
							member.setExtcredits3(extcredits3);
						}
						if (member.getExtcredits4() - extcredits4 != 0) {
							int result = extcredits4 - member.getExtcredits4();
							String rates = result + "";
							if (result > 0) {
								rates = "+" + result;
							}
							StringBuffer buffer = new StringBuffer();
							buffer.append(timestamp);
							buffer.append("\t");
							buffer.append(members.getUsername());
							buffer.append("\t");
							buffer.append(members.getGroupid());
							buffer.append("\t");
							buffer.append(member.getUsername());
							buffer.append("\t");
							buffer.append("4");
							buffer.append("\t");
							buffer.append(rates);
							buffer.append("\t");
							buffer.append("0");
							buffer.append("\t");
							buffer.append(reason);
							Log.writelog("ratelog", timestamp, buffer.toString());
							member.setExtcredits4(extcredits4);
						}
						if (member.getExtcredits5() - extcredits5 != 0) {
							int result = extcredits5 - member.getExtcredits5();
							String rates = result + "";
							if (result > 0) {
								rates = "+" + result;
							}
							StringBuffer buffer = new StringBuffer();
							buffer.append(timestamp);
							buffer.append("\t");
							buffer.append(members.getUsername());
							buffer.append("\t");
							buffer.append(members.getGroupid());
							buffer.append("\t");
							buffer.append(member.getUsername());
							buffer.append("\t");
							buffer.append("5");
							buffer.append("\t");
							buffer.append(rates);
							buffer.append("\t");
							buffer.append("0");
							buffer.append("\t");
							buffer.append(reason);
							Log.writelog("ratelog", timestamp, buffer.toString());
							member.setExtcredits5(extcredits5);
						}
						if (member.getExtcredits6() - extcredits6 != 0) {
							int result = extcredits6 - member.getExtcredits6();
							String rates = result + "";
							if (result > 0) {
								rates = "+" + result;
							}
							StringBuffer buffer = new StringBuffer();
							buffer.append(timestamp);
							buffer.append("\t");
							buffer.append(members.getUsername());
							buffer.append("\t");
							buffer.append(members.getGroupid());
							buffer.append("\t");
							buffer.append(member.getUsername());
							buffer.append("\t");
							buffer.append("6");
							buffer.append("\t");
							buffer.append(rates);
							buffer.append("\t");
							buffer.append("0");
							buffer.append("\t");
							buffer.append(reason);
							Log.writelog("ratelog", timestamp, buffer.toString());
							member.setExtcredits6(extcredits6);
						}
						if (member.getExtcredits7() - extcredits7 != 0) {
							int result = extcredits7 - member.getExtcredits7();
							String rates = result + "";
							if (result > 0) {
								rates = "+" + result;
							}
							StringBuffer buffer = new StringBuffer();
							buffer.append(timestamp);
							buffer.append("\t");
							buffer.append(members.getUsername());
							buffer.append("\t");
							buffer.append(members.getGroupid());
							buffer.append("\t");
							buffer.append(member.getUsername());
							buffer.append("\t");
							buffer.append("7");
							buffer.append("\t");
							buffer.append(rates);
							buffer.append("\t");
							buffer.append("0");
							buffer.append("\t");
							buffer.append(reason);
							Log.writelog("ratelog", timestamp, buffer.toString());
							member.setExtcredits7(extcredits7);
						}
						if (member.getExtcredits8() - extcredits8 != 0) {
							int result = extcredits8 - member.getExtcredits8();
							String rates = result + "";
							if (result > 0) {
								rates = "+" + result;
							}
							StringBuffer buffer = new StringBuffer();
							buffer.append(timestamp);
							buffer.append("\t");
							buffer.append(members.getUsername());
							buffer.append("\t");
							buffer.append(members.getGroupid());
							buffer.append("\t");
							buffer.append(member.getUsername());
							buffer.append("\t");
							buffer.append("8");
							buffer.append("\t");
							buffer.append(rates);
							buffer.append("\t");
							buffer.append("0");
							buffer.append("\t");
							buffer.append(reason);
							Log.writelog("ratelog", timestamp, buffer.toString());
							member.setExtcredits8(extcredits8);
						}
						memberService.modifyMember(member);
						String creditsformula = ForumInit.settings.get("creditsformula");
						dataBaseService.runQuery("UPDATE jrun_members SET credits="+ creditsformula + " WHERE uid =" + member.getUid(),true);
						member = null;
						request.setAttribute("message_key", "a_member_edit_credits_succeed");
						request.setAttribute("url_forward", "admincp.jsp?action=toeditcredits&memberid="+memberId);
						return mapping.findForward("message");
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=toeditcredits&memberid="+memberId);
		return null;
	}
	public ActionForward goEditUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String memberId = request.getParameter("memberid");
		int memId = Integer.parseInt(memberId);
		Members member = memberService.findMemberById(memId);
		boolean isfounder = (Boolean) request.getAttribute("isfounder");
		if (!isfounder && member.getGroupid() == 1) {
			request.setAttribute("message_key","noaccess_isfounder");
			return mapping.findForward("message");
		}
		List<Map<String, String>> memberfield = dataBaseService.executeQuery("select * from jrun_memberfields where uid="+ memId);
		Map<String, String> memberfields = memberfield.get(0);
		memberfield = null;
		Onlinetime onlinetime = memberService.findOnlineTimeById(memId);
		List<Map<String, String>> styleslist = dataBaseService.executeQuery("SELECT styleid, name FROM jrun_styles WHERE available=1");
		List<Map<String, String>> profilelist = dataBaseService.executeQuery("select title,selective,choices,fieldid from jrun_profilefields where available=1");
		List<Map<String, String>> resultfile = new ArrayList<Map<String, String>>();
		if (profilelist.size() > 0) {
			for (Map<String, String> profile : profilelist) {
				if (profile.get("selective").equals("1")) {
					StringBuffer result = new StringBuffer("<select name='profile"+ profile.get("fieldid")+ "'><option value=''>&nbsp;</option>");
					String choose = profile.get("choices");
					String[] chosechild = choose.split("\n");
					if (chosechild.length > 0) {
						for (int i = 0; i < chosechild.length; i++) {
							String[] option = chosechild[i].split("=");
							String selected = "";
							String optionname = "&nbsp;";
							if (option.length == 2) {
								optionname = option[1];
							}
							if (option[0].trim().equals(memberfields.get("field_"+ profile.get("fieldid")))) {
								selected = "selected";
							}
							result.append("<option value='" + option[0]+ "' " + selected + ">" + optionname+ "</option>");
						}
					}
					result.append("</select>");
					profile.put("select", result.toString());
				} else {
					profile.put("select", memberfields.get("field_"+ profile.get("fieldid")));
				}
				resultfile.add(profile);
			}
		} else {
			resultfile = null;
		}
		profilelist = null;
		request.setAttribute("member", member);
		request.setAttribute("memberfield", memberfields);
		request.setAttribute("onlinetime", onlinetime);
		request.setAttribute("styleslist", styleslist);
		request.setAttribute("profilelist", resultfile);
		return mapping.findForward("memberinfo");
	}
	public ActionForward editUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String memberId = request.getParameter("memberid");
		try{
			if(submitCheck(request, "editsubmit")){
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				int memId = Integer.parseInt(memberId);
				Members member = memberService.findMemberById(memId);
				Memberfields memberfield = memberService.findMemberfieldsById(memId);
				Onlinetime onlinetime = memberService.findOnlineTimeById(memId);
				String username = request.getParameter("usernamenew");
				String passwordnew = request.getParameter("passwordnew");
				String clearquestion = request.getParameter("clearquestion");
				String clearspacecache = request.getParameter("clearspacecache");
				String nicknamenew = request.getParameter("nicknamenew");
				String gendernew = request.getParameter("gendernew");
				String emailnew = request.getParameter("emailnew");
				String postsnew = request.getParameter("postsnew");
				String digestpostsnew = request.getParameter("digestpostsnew");
				String pageviewsnew = request.getParameter("pageviewsnew");
				String totalnew = request.getParameter("totalnew");
				String thismonthnew = request.getParameter("thismonthnew");
				String regipnew = request.getParameter("regipnew");
				String regdatenew = request.getParameter("regdatenew");
				String lastvisitnew = request.getParameter("lastvisitnew");
				String lastipnew = request.getParameter("lastipnew");
				String sitenew = request.getParameter("sitenew");
				String qqnew = request.getParameter("qqnew");
				String icqnew = request.getParameter("icqnew");
				String yahoonew = request.getParameter("yahoonew");
				String msnnew = request.getParameter("msnnew");
				String taobaonew = request.getParameter("taobaonew");
				String alipaynew = request.getParameter("alipaynew");
				String locationnew = request.getParameter("locationnew");
				String bdaynew = request.getParameter("bdaynew");
				String avatarnew = request.getParameter("avatarnew");
				String avatarwidthnew = request.getParameter("avatarwidthnew");
				String avatarheightnew = request.getParameter("avatarheightnew");
				String bionew = request.getParameter("bionew");
				String signaturenew = request.getParameter("signaturenew");
				String styleidnew = request.getParameter("styleidnew");
				String tppnew = request.getParameter("tppnew");
				String pppnew = request.getParameter("pppnew");
				String cstatusnew = request.getParameter("cstatusnew");
				String timeformatnew = request.getParameter("timeformatnew");
				String timeoffsetnew = request.getParameter("timeoffsetnew");
				String pmsoundnew = request.getParameter("pmsoundnew");
				String invisiblenew = request.getParameter("invisiblenew");
				String showemailnew = request.getParameter("showemailnew");
				String newsletternew = request.getParameter("newsletternew");
				String ignorepmnew = request.getParameter("ignorepmnew");
				if (!member.getUsername().equals(username.trim())) {
					Map<String, String> settings = ForumInit.settings;
					String usernamenew = username.trim();
					String usernameold = member.getUsername();
					Members usermember = memberService.findByName(username.trim());
					String censoruser = settings.get("censoruser");
					if (Common.censoruser(usernamenew, censoruser)) {
						request.setAttribute("return", true);
						request.setAttribute("message_key", "profile_username_illegal");
						return mapping.findForward("message");
					}
					if (usermember != null) {
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_member_edit_duplicate");
						return mapping.findForward("message");
					}
					dataBaseService.runQuery("UPDATE jrun_announcements SET author='"+ usernamenew + "' WHERE author='" + usernameold + "'");
					dataBaseService.runQuery("UPDATE jrun_banned SET admin='"+ usernamenew + "' WHERE admin='" + usernameold + "'");
					dataBaseService.runQuery("UPDATE jrun_forums SET lastpost=REPLACE(lastpost, '\t"+ usernameold + "', '\t" + usernamenew + "')");
					dataBaseService.runQuery("UPDATE jrun_members SET username='"+ usernamenew + "' WHERE uid='" + member.getUid() + "'");
					dataBaseService.runQuery("UPDATE jrun_pms SET msgfrom='"+ usernamenew + "' WHERE msgfromid='" + member.getUid()+ "'");
					dataBaseService.runQuery("UPDATE jrun_posts SET author='"+ usernamenew + "' WHERE authorid='" + member.getUid()+ "'");
					dataBaseService.runQuery("UPDATE jrun_threads SET author='"+ usernamenew + "' WHERE authorid='" + member.getUid()+ "'");
					dataBaseService.runQuery("UPDATE jrun_threads SET lastposter='"+ usernamenew + "' WHERE lastposter='" + usernameold + "'");
					dataBaseService.runQuery("UPDATE jrun_threadsmod SET username='"+ usernamenew + "' WHERE uid='" + member.getUid() + "'");
				}
				if (clearspacecache.equals("1")) {
					memberService.deleteSpacecaches(memId);
				}
				int total = Math.min(16777215,Common.toDigit(totalnew));
				int thismonth = Common.range(Common.intval(thismonthnew), 65535, 0);
				if (onlinetime == null) {
					onlinetime = new Onlinetime();
					onlinetime.setUid(memId);
				}
				int updateonlintimes = Integer.valueOf(timestamp);
				onlinetime.setLastupdate(updateonlintimes);
				onlinetime.setTotal(total);
				onlinetime.setThismonth(thismonth);
				memberService.modifyOnlineTime(onlinetime);
				memberfield.setNickname(Common.cutstr(Common.htmlspecialchars(nicknamenew),30,null));
				memberfield.setSite(Common.cutstr(Common.htmlspecialchars(sitenew),75,null));
				memberfield.setAlipay(Common.cutstr(Common.htmlspecialchars(alipaynew),50,null));
				memberfield.setIcq(Common.cutstr(Common.htmlspecialchars(icqnew),12,null));
				memberfield.setQq(Common.cutstr(Common.htmlspecialchars(qqnew),12,null));
				memberfield.setYahoo(Common.cutstr(Common.htmlspecialchars(yahoonew),40,null));
				memberfield.setMsn(Common.cutstr(Common.htmlspecialchars(msnnew),40,null));
				memberfield.setTaobao(Common.cutstr(Common.htmlspecialchars(taobaonew),40,null));
				memberfield.setLocation(Common.cutstr(Common.htmlspecialchars(locationnew),30,null));
				memberfield.setCustomstatus(Common.cutstr(Common.htmlspecialchars(cstatusnew),30,null));
				memberfield.setAvatar(Common.cutstr(Common.htmlspecialchars(avatarnew),255,null));
				short avatarwidth = (short)Common.range(Common.intval(avatarwidthnew), 255, 0);
				short avatarheight = (short)Common.range(Common.intval(avatarheightnew), 255, 0);
				memberfield.setAvatarwidth(avatarwidth);
				memberfield.setAvatarheight(avatarheight);
				if (avatarnew == null || avatarnew.equals("")) {
					memberfield.setAvatarwidth((short) 0);
					memberfield.setAvatarheight((short) 0);
				}
				memberfield.setBio(Common.htmlspecialchars(bionew));
				memberfield.setSightml(Common.htmlspecialchars(signaturenew));
				memberfield.setIgnorepm(Common.htmlspecialchars(ignorepmnew));
				memberService.modifyMemberfields(memberfield);
				member.setUsername(username);
				if (passwordnew != null && !passwordnew.equals("")) {
					member.setPassword(Md5Token.getInstance().getLongToken(Md5Token.getInstance().getLongToken(passwordnew)+member.getSalt()));
				}
				if (clearquestion.equals("1")) {
					member.setSecques("");
				}
				member.setGender(Byte.parseByte(gendernew));
				member.setRegip(Common.cutstr(regipnew, 15, null));
				String timeoffset = (String)request.getSession().getAttribute("timeoffset");
				if(FormDataCheck.isValueDate(regdatenew)){
					member.setRegdate(Common.dataToInteger(regdatenew, "yyyy-MM-dd kk:mm",timeoffset));
				}
				member.setLastip(Common.cutstr(lastipnew, 15, null));
				if(FormDataCheck.isValueDate(lastvisitnew)){
					member.setLastvisit(Common.dataToInteger(lastvisitnew, "yyyy-MM-dd kk:mm",timeoffset));
				}
				int posts = Math.min(16777215, Common.toDigit(postsnew));
				int digestposts = Common.range(Common.intval(digestpostsnew), 65535, 0);
				member.setPosts(posts);
				member.setDigestposts(digestposts);
				int pageviews = Math.min(16777215,Common.toDigit(pageviewsnew));
				member.setPageviews(pageviews);
				if (Common.isEmail(emailnew)) {
					member.setEmail(emailnew);
				}
				if (Common.datecheck(bdaynew)) {
					member.setBday(bdaynew);
				} else {
					member.setBday("0000-00-00");
				}
				short tpp = (short)Common.range(Common.intval(tppnew), 255, 0);
				short ppp = (short)Common.range(Common.intval(pppnew), 255, 0);
				member.setTpp(tpp);
				member.setPpp(ppp);
				short styleid = Short.parseShort(styleidnew);
				member.setStyleid(styleid);
				byte timeformat = Byte.parseByte(timeformatnew);
				member.setTimeformat(timeformat);
				member.setTimeoffset(timeoffsetnew);
				member.setPmsound(Byte.parseByte(pmsoundnew));
				member.setShowemail(Byte.parseByte(showemailnew));
				member.setNewsletter(Byte.parseByte(newsletternew));
				member.setInvisible(Byte.parseByte(invisiblenew));
				memberService.modifyMember(member);
				List<Map<String, String>> profilelist = dataBaseService.executeQuery("select size,fieldid from jrun_profilefields where available=1");
				for (Map<String, String> profile : profilelist) {
					String pro = request.getParameter("profile"+ profile.get("fieldid"));
					pro = pro == null ? "" : pro;
					if (pro.length() > 50) {
						pro = pro.substring(0, 50);
					}
					dataBaseService.runQuery("update jrun_memberfields set field_"+ profile.get("fieldid") + "='" + Common.addslashes(pro) + "' where uid="+ memberId, true);
				}
				member = null;
				memberfield = null;
				profilelist = null;
				request.setAttribute("message_key", "a_member_edit_succeed");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=toedituserinfo&memberid="+memberId);
		return null;
	}
	public ActionForward goEditMedals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, String>> medalList = new ArrayList<Map<String, String>>();
		String memberId = request.getParameter("memberid");
		List<Map<String, String>> members = dataBaseService.executeQuery("select m.uid,m.username,mm.medals from jrun_members as m left join jrun_memberfields as mm on m.uid=mm.uid where m.uid="+ memberId);
		Map<String, String> member = members.get(0);
		members = null;
		List<Map<String, String>> medalslist = dataBaseService.executeQuery("select * from jrun_medals where available=1 order by medalid");
		String medals = member.get("medals");
		if (medalslist == null || medalslist.size() < 1) {
			request.setAttribute("message_key", "a_member_edit_medals_nonexistence");
			return mapping.findForward("message");
		} else {
			for (Map<String, String> medal : medalslist) {
				if (Common.matches(medals, "(^|\t)(" + medal.get("medalid")+ ")(\t|$)")) {
					medal.put("ismedal", "true");
				}
				medalList.add(medal);
			}
			medalslist = null;
			request.setAttribute("member", member);
			request.setAttribute("medalslist", medalList);
			return mapping.findForward("membermedals");
		}
	}
	public ActionForward editmedal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String memberId = request.getParameter("memberid");
		try{
			if(submitCheck(request, "medalsubmit")){
				HttpSession session = request.getSession();
				Members members = (Members) session.getAttribute("members");
				List<Map<String, String>> memberslist = dataBaseService.executeQuery("select m.uid,m.username,mm.medals from jrun_members as m left join jrun_memberfields as mm on m.uid=mm.uid where m.uid="+ memberId);
				Map<String, String> member = memberslist.get(0);
				memberslist = null;
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				String medals[] = request.getParameterValues("medals");
				String medalsresult = "";
				if (medals != null) {
					for (int i = 0; i < medals.length; i++) {
						medalsresult = medalsresult + medals[i] + "\t";
					}
				}
				String reason = request.getParameter("reason");
				if ((reason == null || reason.equals(""))&& !member.get("medals").equals(medalsresult)) {
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_member_edit_reason_invalid");
					return mapping.findForward("message");
				}
				String medalold = member.get("medals");
				String[] oldmedal = medalold.split("\t");
				String onlineip=Common.get_onlineip(request);
				if (medals != null) {
					if (!medalold.equals("")) {
						for (String medal : medals) {
							if (!Common.matches(medalold, "(^|\t)(" + medal + ")(\t|$)")) {
								String medalsLog = timestamp + "\t"+ members.getUsername() + "\t"+ onlineip + "\t"+ member.get("username") + "\t" + medal+ "\tgrant\t" + reason;
								Log.writelog("medalslog",timestamp, medalsLog);
							}
						}
						for (String medal : oldmedal) {
							if (!Common.matches(medalsresult, "(^|\t)(" + medal+ ")(\t|$)")) {
								String medalsLog = timestamp + "\t"+ members.getUsername() + "\t"+ onlineip + "\t"+ member.get("username") + "\t" + medal+ "\trevoke\t" + reason;
								Log.writelog("medalslog", timestamp, medalsLog);
							}
						}
					} else {
						for (String medal : medals) {
							String medalsLog = timestamp + "\t" + members.getUsername()+ "\t" + onlineip + "\t"+ member.get("username") + "\t" + medal + "\tgrant\t"+ reason;
							Log.writelog("medalslog", timestamp, medalsLog);
						}
					}
				} else if (!medalold.equals("")) {
					for (String medal : oldmedal) {
						String medalsLog = timestamp + "\t" + members.getUsername() + "\t"+ onlineip + "\t"+ member.get("username") + "\t" + medal + "\trevoke\t"+ reason;
						Log.writelog("medalslog", timestamp, medalsLog);
					}
				}
				dataBaseService.runQuery("update jrun_memberfields set medals='"+ medalsresult + "' where uid=" + memberId, true);
				request.setAttribute("message_key", "a_member_edit_medals_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=toeditmedal&memberid="+memberId);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=toeditmedal&memberid="+memberId);
		return null;
	}
	public ActionForward searchVlidateMemeber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "prunesubmit")){
				String submitmore = request.getParameter("submitmore"); 
				String regbefore = request.getParameter("regbefore"); 
				String modbefore = request.getParameter("modbefore"); 
				String regip = request.getParameter("regip"); 
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				int submittime = Common.toDigit(submitmore);
				int submitDate = Common.toDigit(regbefore);
				int moddate = Common.toDigit(modbefore);
				if (submitDate != 0) {
					submitDate = timestamp-submitDate*86400;
				}
				if (moddate != 0) {
					moddate = timestamp-moddate*86400;
				}
				if (regip == null) {
					regip = "";
				}
				String sql = memberService.searchValidate(submittime,submitDate, moddate, regip);
				List<Map<String,String>> validatelist = dataBaseService.executeQuery("select v.uid "+sql);
				int listsize = 0;
				if (validatelist != null && validatelist.size()> 0) {
					listsize = validatelist.size();
				}
				StringBuffer uids = new StringBuffer();
				if(validatelist!=null&&validatelist.size()>0){
					for(Map<String,String>validate:validatelist){
						uids.append(","+validate.get("uid"));
					}
				}
				String otherInfo = "<input type='hidden' name='uids' value='"+uids+"'><input type='hidden' name='count' value='"+listsize+"'>";
				String commitPath = "admincp.jsp?action=modmembers&delete=yes";
				request.setAttribute("msgtype", "form");
				request.setAttribute("othermsg", otherInfo);
				request.setAttribute("isnewline", "yes");
				request.setAttribute("message", getMessage(request, "a_member_delete_confirm", listsize+""));
				request.setAttribute("url_forward",commitPath);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=modmembers");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward deleteMemberAndValidate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "confirmed")){
				String uids = request.getParameter("uids");
				String count = request.getParameter("count");
				if(uids!=null&&uids.length()>0){
					dataBaseService.runQuery("DELETE FROM jrun_members WHERE uid in ("+uids.substring(1)+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_validating WHERE uid in ("+uids.substring(1)+")",true);
				}
				request.setAttribute("message", getMessage(request, "a_member_delete_succeed", count));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=modmembers");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward validateMembers(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		try{
			if(submitCheck(request, "modsubmit")){
				HttpSession session = request.getSession();
				String uids[] = request.getParameterValues("uid");
				StringBuffer uidin = new StringBuffer("0");
				if(uids!=null){
					for(String s:uids){
						uidin.append(","+s);
					}
				}
				Map<String,String> settings=ForumInit.settings;
				List<Map<String,String>> members=dataBaseService.executeQuery("SELECT v.*, m.uid, m.username, m.email, m.regdate FROM jrun_validating v, jrun_members m WHERE m.uid=v.uid AND m.groupid='8' and m.uid in ("+uidin+")");
				String sendemail = request.getParameter("sendemail");
				String subject=getMessage(request, "moderate_member_subject");
				String jsprun_userss = (String) session.getAttribute("jsprun_userss");
				String boardurl = (String) session.getAttribute("boardurl");
				String dateformat = settings.get("dateformat");
				String timeformat = settings.get("gtimeformat");
				String timeoffset = settings.get("timeoffset");
				Map<String,String> mails = dataParse.characterParse(settings.get("mail"), false);
				SimpleDateFormat sf=Common.getSimpleDateFormat(dateformat+" "+timeformat,timeoffset);
				Mail mail = new Mail(mails);
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				int newgroupid=10;
				List<Map<String,String>> usergroups=dataBaseService.executeQuery("SELECT groupid FROM jrun_usergroups WHERE creditshigher<=0 AND 0<creditslower LIMIT 1");
				if(usergroups!=null&&usergroups.size()>0){
					newgroupid=Integer.valueOf(usergroups.get(0).get("groupid"));
				}
				int validatenum = 0;
				int invalidatenum = 0;
				int deletenum = 0;
				for (Map<String,String> member : members) {
					String uid=member.get("uid");
					String operation = null; 
					String mods = request.getParameter("mod[" +uid + "]");
					String remark = request.getParameter("remark[" + uid+ "]");
					remark=remark!=null?Common.htmlspecialchars(remark):"";
					if (mods.equals("invalidate")) {
						dataBaseService.runQuery("UPDATE jrun_validating SET moddate='"+timestamp+"', admin='"+jsprun_userss+"', status='1', remark='"+Common.addslashes(remark)+"' WHERE uid='"+uid+"'",true);
						invalidatenum++;
						operation = getMessage(request, "invalidate");
					} else if (mods.equals("validate")) {
						dataBaseService.runQuery("update jrun_members SET adminid='0', groupid='"+newgroupid+"' WHERE uid='"+ uid+"'",true);
						dataBaseService.runQuery("DELETE FROM jrun_validating WHERE uid ='"+uid+"'",true);
						validatenum++;
						operation = getMessage(request, "validate");
					} else if (mods.equals("delete")) {
						dataBaseService.runQuery("DELETE FROM jrun_members WHERE uid ='"+uid+"'", true);
						dataBaseService.runQuery("DELETE FROM jrun_memberfields WHERE uid ='"+uid+"'", true);
						dataBaseService.runQuery("DELETE FROM jrun_validating WHERE uid ='"+uid+"'", true);
						deletenum++;
						operation = getMessage(request, "delete");
					}
					if(sendemail != null&&operation!=null){
						String username=member.get("username");
						String regdate=Common.gmdate(sf, Common.intval(member.get("regdate")));
						String submitdate=Common.gmdate(sf, Common.intval(member.get("submitdate")));
						String moddate=Common.gmdate(sf,timestamp);
						mail.sendMessage(mails.get("from"), username+" <"+member.get("email")+">", subject, getMessage(request, "moderate_member_message", settings.get("bbname"),username,regdate,submitdate,member.get("submittimes"),member.get("message"),operation,moddate,jsprun_userss,remark,boardurl), null);
					}
				}
				request.setAttribute("message", getMessage(request, "a_member_moderate_succeed", validatenum+"",invalidatenum+"",deletenum+""));
				request.setAttribute("url_forward", "admincp.jsp?action=modmembers");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=modmembers");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward userGroupInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Usergroups userGroup = null;
		String editId = request.getParameter("edit");
		userGroup = userGroupService.findUserGroupById(new Short(editId));
		String extcredits = ForumInit.settings.get("extcredits");
		Map extcredit = dataParse.characterParse(extcredits, true);
		String raterange = userGroup.getRaterange();
		String system = userGroup.getSystem();
		if (!system.equals("private")) {
			List sysgroupsp = new ArrayList();
			String[] rwes = system.split("\t");
			for (String ss : rwes) {
				sysgroupsp.add(ss);
			}
			request.setAttribute("systemgroup", sysgroupsp);
		}
		if (!raterange.equals("")) {
			String[] reaterang = raterange.split("\t");
			List ranglist = null;
			String key = "";
			HashMap rangresult = new HashMap();
			for (int i = 0, j = 0; i < reaterang.length; i++) {
				if ((i + 4) % 4 == 0) {
					key = reaterang[i];
					ranglist = new ArrayList();
				} else {
					j++;
					ranglist.add(reaterang[i]);
				}
				if (j % 3 == 0) {
					rangresult.put(key, ranglist);
				}
			}
			request.setAttribute("rangresult", rangresult);
		}
		request.setAttribute("projects", dataBaseService.executeQuery("SELECT id, name FROM jrun_projects WHERE type='group'"));
		request.setAttribute("extcredit", extcredit);
		request.setAttribute("userGroup", userGroup);
		return mapping.findForward("usergroupsinfo");
	}
	public ActionForward editUserGroupInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String userproject = request.getParameter("saveconfigsubmit");
		String groupid = request.getParameter("groupid");
		try{
			if(submitCheck(request, "detailsubmit")||submitCheck(request, "saveconfigsubmit")){
				Usergroups userGroup = null;
				userGroup = userGroupService.findUserGroupById(new Short(groupid.trim()));
				String radminid = request.getParameter("radminid");
				Map<String,String> settings = ForumInit.settings;
				if (userGroup.getType().equals("special")&&radminid != null&&!radminid.equals(userGroup.getRadminid()+"")) {
					userGroup.setRadminid(new Short(radminid));
					if (radminid.equals("0")) {
						dataBaseService.runQuery("delete from jrun_admingroups where admingid="+ userGroup.getGroupid(), true);
						dataBaseService.runQuery("update jrun_members set adminid=0 where groupid="+userGroup.getGroupid(),true);
					} else {
						if(radminid.equals("1")){
							dataBaseService.runQuery("replace into jrun_admingroups values('"+ userGroup.getGroupid() + "','1','1','3','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1')", true);
						}else{
							dataBaseService.runQuery("replace into jrun_admingroups(admingid) values('"+ userGroup.getGroupid() + "')", true);
						}
						dataBaseService.runQuery("update jrun_members set adminid="+radminid+" where groupid="+userGroup.getGroupid(),true);
					}
				}
				userGroup = (Usergroups) Common.setValues(userGroup, request);
				if (userGroup.getReadaccess() > 255) {
					userGroup.setReadaccess((short) 255);
				}
				if (userGroup.getMaxpostsperhour() > 255) {
					userGroup.setMaxpostsperhour((short) 255);
				}
				if (userGroup.getMaxrewardprice() > 65536) {
					userGroup.setMaxrewardprice((short) 65536);
				}
				if (userGroup.getMaxmagicsweight() > 65536) {
					userGroup.setMaxmagicsweight((short) 65536);
				}
				StringBuffer raterange = new StringBuffer();
				for (int i = 1; i <= 8; i++) {
					String raterange_allowrate = request.getParameter("raterange_allowrate[" + i + "]");
					if (raterange_allowrate != null && !raterange_allowrate.equals("")) {
						String raterange_1 = request.getParameter("raterange[1" + i+ "]");
						String raterange_2 = request.getParameter("raterange[2" + i+ "]");
						String raterange_3 = request.getParameter("raterange[3" + i+ "]");
						int raterange1 = Common.intval(raterange_1);
						int raterange2 = Common.intval(raterange_2);
						int raterange3 = Common.intval(raterange_3);
						if ((raterange2 - raterange1) <= 0 || (raterange3 - raterange2) < 0) {
							request.setAttribute("return", true);
							request.setAttribute("message_key", "usergroups_edit_rate_invalid");
							return mapping.findForward("message");
						}
						raterange.append(raterange_allowrate + "\t" + raterange1 + "\t" + raterange2 + "\t" + raterange3 + "\t");
					}
				}
				String system_public = request.getParameter("system_public");
				if (system_public != null && system_public.equals("1")) {
					if (!radminid.equals("0")) {
						request.setAttribute("message_key", "usergroups_edit_public_invalid");
						request.setAttribute("return", true);
						return mapping.findForward("message");
					} else {
						String creditstrans = settings.get("creditstrans");
						String system_dailyprice = request.getParameter("system_dailyprice");
						String system_minspan = request.getParameter("system_minspan");
						int dailyprice = Common.toDigit(system_dailyprice);
						int minspan = Common.toDigit(system_minspan);
						if ((creditstrans == null || creditstrans.equals("0"))&& dailyprice > 0) {
							request.setAttribute("return", true);
							request.setAttribute("message_key", "usergroups_edit_creditstrans_disabled");
							return mapping.findForward("message");
						} else {
							String system = "";
							if (dailyprice > 0) {
								system = dailyprice + "\t" + minspan;
							} else {
								system = "0" + "\t" + "0";
							}
							userGroup.setSystem(system);
						}
					}
				} else {
					userGroup.setSystem("private");
				}
				userGroup.setRaterange(raterange.toString());
				int maxattasize = userGroup.getMaxattachsize();
				maxattasize = maxattasize>16777215?16777215:maxattasize;
				userGroup.setMaxattachsize(maxattasize);
				String mintradeprice = request.getParameter("mintradeprice");
				String minrewardprice = request.getParameter("minrewardprice");
				userGroup.setMinrewardprice((short)Math.max(Common.intval(minrewardprice),1));
				userGroup.setMintradeprice((short)Math.max(Common.intval(mintradeprice),1));
				userGroupService.modifyUserGroup(userGroup);
				Cache.updateCache("usergroup");
				if (userproject != null) {
					boolean isfounder = (Boolean) request.getAttribute("isfounder");
					if (!isfounder) {
						request.setAttribute("message_key","noaccess_isfounder");
						return mapping.findForward("message");
					}
					request.setAttribute("usergroup", userGroup);
					return mapping.findForward("userproject");
				}
				request.setAttribute("message_key", "usergroups_edit_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=usergroups");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=tousergroupinfo&edit="+groupid);
		return null;
	}
	public ActionForward goEditRanks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List<Ranks> ranklist = memberService.findAllRanks();
		request.setAttribute("ranklist", ranklist);
		return mapping.findForward("ranks");
	}
	public ActionForward editRanks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "ranksubmit")){
				String[] delIds = request.getParameterValues("delid");
				String[] updateIds = request.getParameterValues("updateid");
				String[] newranktitle = request.getParameterValues("newranktitle");
				int insertLength = newranktitle.length;
				String[] newpostshigher = request.getParameterValues("newpostshigher");
				String[] newstars = request.getParameterValues("newstars");
				String[] newcolor = request.getParameterValues("newcolor");
				String[] starses = request.getParameterValues("stars");
				String[] colors = request.getParameterValues("color");
				String[] postshigher = request.getParameterValues("postshigher");
				String[] ranktitle = request.getParameterValues("ranktitle");
				int updateLength = ranktitle.length;
				List<String> idList = new ArrayList<String>();
				List<String> ranktitleList = new ArrayList<String>();
				List<String> starsList = new ArrayList<String>();
				List<String> colorList = new ArrayList<String>();
				List<String> postshigherList = new ArrayList<String>();
				for (int i = 0; i < insertLength; i++) {
					if (!newranktitle[i].equals("")) {
						idList.add("-1");
						int postshi = Common.toDigit(newpostshigher[i]);
						int star = Common.toDigit(newstars[i]);
						ranktitleList.add(newranktitle[i]);
						starsList.add(star + "");
						colorList.add(newcolor[i]);
						postshigherList.add(postshi + "");
					}
				}
				for (int i = 0; i < updateLength; i++) {
					idList.add(updateIds[i]);
					int postshi = Common.toDigit(postshigher[i]);
					int star = Common.toDigit(starses[i]);
					ranktitleList.add(ranktitle[i]);
					starsList.add(star + "");
					colorList.add(colors[i]);
					postshigherList.add(postshi + "");
				}
				int size = ranktitleList.size();
				for (int i = 0; i < size; i++) {
					String sql = "REPLACE INTO jrun_ranks(ranktitle,postshigher,stars,color)values('"
							+ Common.addslashes(ranktitleList.get(i))
							+ "','"
							+ postshigherList.get(i)
							+ "','"
							+ starsList.get(i)
							+ "','"
							+ Common.addslashes(colorList.get(i))
							+ "')";
					if (!idList.get(i).equals("-1")) {
						sql = "REPLACE INTO jrun_ranks(rankid,ranktitle,postshigher,stars,color)values('"
								+ idList.get(i)
								+ "','"
								+ Common.addslashes(ranktitleList.get(i))
								+ "','"
								+ postshigherList.get(i)
								+ "','"
								+ starsList.get(i)
								+ "','" + Common.addslashes(colorList.get(i)) + "')";
					}
					dataBaseService.runQuery(sql, true);
				}
				if (delIds != null && delIds.length != 0) {
					for (int i = 0; i < delIds.length; i++) {
						dataBaseService.runQuery("delete from jrun_ranks where rankid="+ delIds[i], true);
					}
				}
				idList = null;
				ranktitleList = null;
				starsList = null;
				colorList = null;
				postshigherList = null;
				request.setAttribute("message_key", "ranks_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=ranks");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=ranks");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward forUsergroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String editId = request.getParameter("edit");
		Usergroups userGroup = userGroupService.findUserGroupById(new Short(editId));
		String extcredits = ForumInit.settings.get("extcredits");
		String projectid = request.getParameter("projectid");
		Map extcredit = null;
		extcredit = dataParse.characterParse(extcredits, true);
		String raterange = userGroup.getRaterange();
		if (!raterange.equals("")) {
			String[] reaterang = raterange.split("\\p{Space}");
			List ranglist = null;
			String key = "";
			HashMap rangresult = new HashMap();
			for (int i = 0, j = 0; i < reaterang.length; i++) {
				if ((i + 4) % 4 == 0) {
					key = reaterang[i];
					ranglist = new ArrayList();
				} else {
					j++;
					ranglist.add(reaterang[i]);
				}
				if (j % 3 == 0) {
					rangresult.put(key, ranglist);
				}
			}
			request.setAttribute("rangresult", rangresult);
		}
		Projects projects = userGroupService.findProjectsById(Short.parseShort(projectid));
		if (projects != null) {
			Map userProject = dataParse.characterParse(projects.getValue(),false);
			userGroup = (Usergroups) setValues(userGroup, userProject);
		}
		request.setAttribute("projectid", projectid);
		request.setAttribute("projects", dataBaseService.executeQuery("SELECT id, name FROM jrun_projects WHERE type='group'"));
		request.setAttribute("extcredit", extcredit);
		request.setAttribute("userGroup", userGroup);
		return mapping.findForward("usergroupsinfo");
	}
	@SuppressWarnings("unchecked")
	private Object setValues(Object bean, Map fieldsMap) {
		try {
			Field[] fields = bean.getClass().getDeclaredFields();
			String paraName = "";
			String paraValue = "";
			String setMethod = "";
			for (int i = 0; i < fields.length; i++) {
				paraName = fields[i].getName();
				Object obj = fieldsMap.get(paraName);
				if (obj != null) {
					paraValue = obj.toString();
					if (paraValue != null && !"".equals(paraValue)) {
						setMethod = "set"+ paraName.substring(0, 1).toUpperCase()+ paraName.substring(1, paraName.length());
						Method method = bean.getClass().getMethod(setMethod,fields[i].getType());
						method.invoke(bean, Common.convert(paraValue, fields[i].getType()));
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}
	private Map<String, Object> getValues(Object bean, String[] fields,
			Map<String, Object> fieldsMap) {
		String paraName = "";
		String getMethod = "";
		try {
			Field[] beanFields = bean.getClass().getDeclaredFields();
			if (fieldsMap == null) {
				fieldsMap = new HashMap<String, Object>();
			}
			int fieldLength = fields.length;
			for (int i = 0; i < fieldLength; i++) {
				paraName = fields[i];
				Method method = null;
				Object paraValue = null;
				int beanFieldLength = beanFields.length;
				for (int j = 0; j < beanFieldLength; j++) {
					if (paraName.equals(beanFields[j].getName())) {
						getMethod = "get"+ paraName.substring(0, 1).toUpperCase()+ paraName.substring(1, paraName.length());
						method = bean.getClass().getMethod(getMethod);
						paraValue = method.invoke(bean, new Object[0]);
					}
				}
				if (method != null) {
					if (paraValue instanceof Short) {
						paraValue = paraValue.toString();
					}
					fieldsMap.put(paraName, paraValue);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return fieldsMap;
	}
	public ActionForward addUserProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String groupid = request.getParameter("usergroupid");
		try{
			if(submitCheck(request, "addsubmit")){
				Usergroups usergroup = userGroupService.findUserGroupById(new Short(groupid));
				String type = "group";
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				if (name == null || name.equals("")) {
					request.setAttribute("return", true);
					request.setAttribute("message_key", "project_no_title");
					return mapping.findForward("message");
				}
				String fieldoption[] = request.getParameterValues("fieldoption[]");
				String fieldoptions[] = { "readaccess", "allowvisit", "allowpost",
						"allowreply", "allowpostpoll", "allowpostreward",
						"allowposttrade", "allowpostactivity", "allowpostvideo",
						"allowdirectpost", "allowgetattach", "allowpostattach",
						"allowvote", "allowmultigroups", "allowsearch", "allowavatar",
						"allowcstatus", "allowuseblog", "allowinvisible",
						"allowtransfer", "allowsetreadperm", "allowsetattachperm",
						"allowhidecode", "allowhtml", "allowcusbbcode",
						"allowanonymous", "allownickname", "allowsigbbcode",
						"allowsigimgcode", "allowviewpro", "allowviewstats",
						"disableperiodctrl", "reasonpm", "maxprice", "maxpmnum",
						"maxsigsize", "maxattachsize", "maxsizeperday",
						"maxpostsperhour", "attachextensions", "raterange",
						"mintradeprice", "maxtradeprice", "minrewardprice",
						"maxrewardprice", "magicsdiscount", "allowmagics",
						"maxmagicsweight", "allowbiobbcode", "allowbioimgcode",
						"maxbiosize", "allowinvite", "allowmailinvite", "maxinvitenum",
						"inviteprice", "maxinviteday", "allowpostdebate", "tradestick","allowviewdigest"};
				String value = "";
				if (fieldoption != null && fieldoption[0].equals("all")) {
					Map<String, Object> result = new HashMap<String, Object>();
					result = getValues(usergroup, fieldoptions, result);
					value = dataParse.combinationChar(result);
				} else {
					Map<String, Object> result = new HashMap<String, Object>();
					result = getValues(usergroup, fieldoption, result);
					value = dataParse.combinationChar(result);
				}
				Projects project = new Projects();
				if (description != null) {
					project.setDescription(description);
				}
				project.setName(name);
				project.setType(type);
				project.setValue(value);
				userGroupService.insertProjects(project);
				request.setAttribute("message_key", "project_sava_succeed");
				request.setAttribute("url_forward","admincp.jsp?action=tousergroupinfo&edit=" + groupid);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=usergroups");
		return null;
	}
	public ActionForward editmembers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "membersubmit",true)){
				String uids = request.getParameter("uid");
				String username = request.getParameter("username");
				HttpSession session = request.getSession();
				Members member = (Members) session.getAttribute("members");
				byte adminid = member.getAdminid();
				Members editmember = null;
				int uid = Common.toDigit(uids);
				if (uid > 0) {
					editmember = memberService.findMemberById(uid);
				} else {
					editmember = memberService.findByName(username);
				}
				if (editmember == null) {
					request.setAttribute("message_key", "a_member_edit_nonexistence");
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (editmember.getAdminid() > 0 && adminid != 1) {
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_member_edit_illegal");
					return mapping.findForward("message");
				}
				Memberfields memberfield = memberService.findMemberfieldsById(editmember.getUid());
				String bio = memberfield.getBio();
				if (!bio.equals("")) {
					String[] bb = bio.split("\\s+");
					if (bb != null && bb.length > 0) {
						memberfield.setBio(bb[0]);
					}
				}
				request.setAttribute("editmember", editmember);
				request.setAttribute("editmemberfild", memberfield);
				return mapping.findForward("toeditmember");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=editmembers");
		return null;
	}
	public ActionForward editextmembers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "editsubmit")){
				String uid = request.getParameter("uid");
				String location = request.getParameter("location");
				String bionew = request.getParameter("bionew");
				String signaturenew = request.getParameter("signaturenew");
				location = location == null ? "" : location;
				bionew = bionew == null ? "" : bionew;
				signaturenew = signaturenew == null ? "" : signaturenew;
				dataBaseService.runQuery("UPDATE jrun_memberfields SET location='"+Common.addslashes(location)+"',bio='"+Common.addslashes(bionew)+"',sightml='"+Common.addslashes(signaturenew)+"' WHERE uid='"+uid+"'",true);
				request.setAttribute("message_key", "a_member_edit_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=editmembers");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=editmembers");
		return null;
	}
	public ActionForward tobanmember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String timeoffset= (String)session.getAttribute("timeoffset");
		String dateformat = (String)session.getAttribute("dateformat");
		String timeformat = (String)session.getAttribute("timeformat");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int [] days = new int[]{1, 3, 5, 7, 14, 30, 60, 90, 180, 365};
		String [] langday = new String[]{getMessage(request, "1_day"),getMessage(request, "3_day"),getMessage(request, "5_day"),getMessage(request, "7_day"),getMessage(request, "14_day"),getMessage(request, "30_day"),getMessage(request, "60_day"),getMessage(request, "90_day"),getMessage(request, "180_day"),getMessage(request, "365_day")};
		StringBuffer banexpirynew  = new StringBuffer("<select name=\"banexpiry\"><option value=0>"+getMessage(request, "0_day")+"&nbsp;</option>");
		SimpleDateFormat sf = Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
		for(int i=0;i<10;i++){
			String datedis = Common.gmdate(sf,timestamp+days[i]*86400);
			banexpirynew.append("<option value="+days[i]+">"+langday[i]+"&nbsp;("+datedis+")");
		}
		banexpirynew.append("</select>");
		request.setAttribute("banexpirynew", banexpirynew);
		String uid = request.getParameter("uid");
		if (uid == null) {
			request.setAttribute("usermap", null);
		} else {
			String sql = "select username,groupid,adminid from jrun_members m where uid="+ uid;
			List<Map<String, String>> usermap = dataBaseService.executeQuery(sql);
			if (usermap.size() > 0) {
				Map<String, String> users = usermap.get(0);
				if (Common.toDigit(users.get("adminid")) > 0) {
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_member_edit_illegal");
					return mapping.findForward("message");
				}
				request.setAttribute("usermap", users);
			} else {
				request.setAttribute("usermap", null);
			}
		}
		return mapping.findForward("banmember");
	}
	private int StringTOInt(String str){
		int count = 0;
		try{
			count = Integer.valueOf(str);
		}catch(Exception e){}
		return count;
	}
}