package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.api.Tenpayapi;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
public class MyManageAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward toMyIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String threadadd = null;
		String postadd = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			postadd = "AND p.fid='" + srchfid + "'";
		}else{
			threadadd = "";
			postadd = "";
		}
		List<Map<String, String>> threadlists = dataBaseService.executeQuery("SELECT m.tid, t.subject, t.fid, t.displayorder, t.lastposter, t.lastpost, t.closed ,f.name FROM jrun_mythreads m, jrun_threads t ,jrun_forums f WHERE m.uid="+ uid+ " AND m.tid=t.tid AND t.fid=f.fid "+ threadadd+ " ORDER BY m.dateline DESC LIMIT 5");
		this.setAttribute(request, session, threadlists, "threadlists");
		List<Map<String, String>> postlists = dataBaseService.executeQuery("SELECT m.tid,m.pid, p.fid, p.invisible,t.subject,t.lastposter,t.lastpost,f.name FROM jrun_myposts m INNER JOIN jrun_posts p ON p.pid=m.pid "+ postadd+ " INNER JOIN jrun_threads t ON t.tid=m.tid ,jrun_forums f WHERE m.uid="+ uid+ " AND p.fid=f.fid ORDER BY m.dateline DESC LIMIT 5");
		this.setAttribute(request, session, postlists, "postlists");
		request.removeAttribute("myitem");
		return mapping.findForward("toMy");
	}
	public ActionForward toMyThreads(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			threadadd ="";
			extrafid="";
		}		
		Map<String, Integer> multi = this.multi(request, response,uid, "SELECT COUNT(*) count FROM jrun_mythreads m, jrun_threads t WHERE m.uid=" + uid + " " + threadadd+ " AND m.tid=t.tid","my.jsp?item=threads" + extrafid);
		List<Map<String, String>> threadlists = dataBaseService.executeQuery("SELECT m.tid, t.subject, t.fid, t.displayorder, t.lastposter, t.lastpost, t.closed ,f.name FROM jrun_mythreads m, jrun_threads t ,jrun_forums f WHERE m.uid="+ uid+ " AND m.tid=t.tid AND t.fid=f.fid "+ threadadd+ "ORDER BY m.dateline DESC LIMIT "+ multi.get("start_limit")+ ", " + multi.get("perpage"));
		this.setAttribute(request, session, threadlists, "threadlists");
		return mapping.findForward("toMy");
	}
	public ActionForward toMyPosts(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String postadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			postadd = "AND p.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			postadd = "";
			extrafid = "";
		}
		Map<String, Integer> multi = this.multi(request, response, uid, "SELECT COUNT(*) count FROM jrun_myposts m INNER JOIN jrun_posts p ON p.pid=m.pid " + postadd + " INNER JOIN jrun_threads t ON t.tid=m.tid WHERE m.uid = " + uid,"my.jsp?item=posts" + extrafid);
		List<Map<String, String>> postlists = dataBaseService.executeQuery("SELECT m.uid, m.tid, m.pid, p.fid, p.invisible, p.dateline,t.subject,t.lastposter,t.lastpost,f.name FROM jrun_myposts m INNER JOIN jrun_posts p ON p.pid=m.pid "+ postadd+ " INNER JOIN jrun_threads t ON t.tid=m.tid ,jrun_forums f WHERE m.uid="+ uid+ " AND p.fid=f.fid ORDER BY m.dateline DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
		this.setAttribute(request, session, postlists, "postlists");
		return mapping.findForward("toMy");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMyFavorites(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String threadadd = null;
		String extrafid =null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			threadadd = "";
			extrafid = "";
		}
		Map<String, String> settings = ForumInit.settings;
		String favsubmit = request.getParameter("favsubmit");
		int tid = Common.intval(request.getParameter("tid"));
		int fid = Common.intval(request.getParameter("fid"));
		String type = request.getParameter("type");
		String ftid = "thread".equals(type) || tid >0 ? "tid" : "fid";
		type = "thread".equals(type) || tid >0 ? "thread" : "forum";
		if ((tid > 0 || fid > 0) && favsubmit == null) {
			Common.setResponseHeader(response);
			String formHashValue = request.getParameter("formHash");
			if(!Common.formHash(request).equals(formHashValue)){
				Common.writeMessage(response, getMessage(request, "submit_invalid"),true);
				return null;
			}
			List<Map<String, String>> count = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_favorites WHERE uid='" + uid + "' AND " + ftid + ">'0'");
			int size = (count != null && count.size() > 0 ? Integer.valueOf(count.get(0).get("count")) : 0);
			int maxfavorites = Integer.valueOf(settings.get("maxfavorites"));
			String message = null;
			if (size >= maxfavorites) {
				message = getMessage(request, "favorite_is_full");
			} else {
				int value = "thread".equals(type) || tid > 0 ? tid : fid;
				count = dataBaseService.executeQuery("SELECT COUNT(*) count  FROM jrun_favorites WHERE uid='" + uid + "' AND " + ftid + "="+ value);
				size = (count != null && count.size() > 0 ? Integer.valueOf(count.get(0).get("count")) : 0);
				if (size > 0) {
					message = getMessage(request, "favorite_exists");
				} else {
					dataBaseService.runQuery("INSERT INTO jrun_favorites (uid, " + ftid+ ") VALUES ('" + uid + "', '" + value + "')",true);
					message = getMessage(request, "favorite_add_succeed");
				}
			}
			try {
				response.getWriter().write(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return mapping.findForward("null");
		} else {
			try{
				if(submitCheck(request, "favsubmit")){
					String[] delete = request.getParameterValues("delete");
					if (delete != null) {
						StringBuffer ids=new StringBuffer(delete.length);
						for (String id : delete) {
							ids.append(Common.intval(id)+",");
						}
						if (ids.length()>0) {
							ids.deleteCharAt(ids.length()-1);
							dataBaseService.runQuery("DELETE FROM jrun_favorites WHERE uid=" + uid + " AND " + ftid+ " IN ( " + ids + " )",true);
						}
						delete=null;
						ids=null;
					}
					request.setAttribute("successInfo", getMessage(request, "favorite_update_succeed"));
					request.setAttribute("requestPath","my.jsp?item=favorites&type=" + type);
					return mapping.findForward("showMessage");
				}
			}catch (Exception e) {
				request.setAttribute("resultInfo",e.getMessage());
				return mapping.findForward("showMessage");
			}
			if ("forum".equals(type)) {
				Map<String, Integer> multi = this.multi(request, response,uid, "SELECT COUNT(*) count FROM jrun_favorites fav, jrun_forums f WHERE fav.uid = " + uid+ " AND fav.fid=f.fid", "my.jsp?item=favorites&type=forum");
				List<Map<String, String>> favlists = dataBaseService.executeQuery("SELECT f.fid, f.name, f.threads, f.posts, f.todayposts, f.lastpost FROM jrun_favorites fav, jrun_forums f WHERE fav.fid=f.fid AND fav.uid="+ uid+ " ORDER BY f.lastpost DESC LIMIT "+ multi.get("start_limit")+ ", "+ multi.get("perpage"));
				request.setAttribute("favlists", favlists != null&& favlists.size() > 0 ? favlists : null);
				favlists=null;
				return mapping.findForward("toMy");					
			} else {
				Map<String, Integer> multi = this.multi(request, response,uid, "SELECT COUNT(*) count FROM jrun_favorites fav, jrun_threads t	WHERE fav.uid = " + uid+ " AND fav.tid=t.tid AND t.displayorder>='0'"+ threadadd, "my.jsp?item=favorites&type=thread"+ extrafid);
				List<Map<String, String>> favlists = dataBaseService.executeQuery("SELECT t.tid, t.fid, t.subject, t.replies, t.lastpost, t.lastposter, f.name FROM jrun_favorites fav, jrun_threads t, jrun_forums f WHERE fav.tid=t.tid AND t.displayorder>='0' AND fav.uid="	+ uid+ " AND t.fid=f.fid "+ threadadd+ " ORDER BY t.lastpost DESC LIMIT "+ multi.get("start_limit")+ ", "+ multi.get("perpage"));
				this.setAttribute(request, session, favlists, "favlists");
				return mapping.findForward("toMy");
			}
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMySubscriptions(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			threadadd = "";
			extrafid = "";
		}
		int subadd = Common.intval(request.getParameter("subadd"));
		if (subadd>0) {
			Common.setResponseHeader(response);
			String formHashValue = request.getParameter("formHash");
			if(!Common.formHash(request).equals(formHashValue)){
				Common.writeMessage(response, getMessage(request, "submit_invalid"),true);
				return null;
			}
			List<Map<String, String>> sublists = dataBaseService.executeQuery("SELECT price FROM jrun_threads WHERE tid="+subadd);
			if(sublists!=null){
				List<Map<String, String>> paymentlist = dataBaseService.executeQuery("SELECT tid FROM jrun_paymentlog WHERE tid='"+subadd+"' AND uid="+uid);
				if(paymentlist==null){
					try {
						response.getWriter().write(getMessage(request, "subscription_nopermission"));
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				paymentlist=null;
				List<Map<String, String>> countlist = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_subscriptions WHERE uid="+uid);
				String maxsuscription = ForumInit.settings.get("maxsubscriptions");
				if(countlist!=null && Integer.valueOf(countlist.get(0).get("count"))>=Integer.valueOf(maxsuscription)){
					try {
						response.getWriter().write(getMessage(request, "subscription_is_full"));
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				List<Map<String, String>> subscriptlist = dataBaseService.executeQuery("SELECT tid FROM jrun_subscriptions WHERE tid='"+subadd+"' AND uid='"+uid+"' LIMIT 1");
				if(subscriptlist!=null && subscriptlist.size()>0){
					try {
						response.getWriter().write(getMessage(request, "subscription_exists"));
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					dataBaseService.runQuery("INSERT INTO jrun_subscriptions (uid, tid,lastnotify) VALUES ('"+uid+"', '"+subadd+"', '0')",true);
					try {
						response.getWriter().write(getMessage(request, "subscription_add_succeed"));
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		} else {
			try{
				if(submitCheck(request, "subsubmit")){
					String[] delete = request.getParameterValues("delete");
					if (delete != null) {
						StringBuffer tids=new StringBuffer();
						for (String tid : delete) {
							tids.append(Common.intval(tid)+",");
						}
						int length=tids.length();
						if (length>0) {
							tids.deleteCharAt(length-1);
							dataBaseService.runQuery("DELETE FROM jrun_subscriptions WHERE uid=" + uid+ " AND tid IN ( " + tids + " )",true);
						}
					}
					request.setAttribute("successInfo",getMessage(request, "subscription_update_succeed"));
					request.setAttribute("requestPath","my.jsp?item=subscriptions");
					return mapping.findForward("showMessage");
				}
			}catch (Exception e) {
				request.setAttribute("resultInfo",e.getMessage());
				return mapping.findForward("showMessage");
			}
			Map<String, Integer> multi = this.multi(request, response, uid,"SELECT COUNT(*) count FROM jrun_subscriptions s, jrun_threads t WHERE s.uid = " + uid+ " AND s.tid=t.tid " + threadadd, "my.jsp?item=subscriptions" + extrafid);
			List<Map<String, String>> sublists = dataBaseService.executeQuery("SELECT t.tid, t.fid, t.subject, t.replies, t.lastpost, t.lastposter, f.name FROM jrun_subscriptions s, jrun_threads t, jrun_forums f	WHERE t.tid=s.tid AND t.displayorder>='0' AND f.fid=t.fid AND s.uid="+ uid+ " "+ threadadd+ " ORDER BY t.lastpost DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
			this.setAttribute(request, session, sublists, "sublists");
			return mapping.findForward("toMy");
		}
	}
	public ActionForward toMyGrouppermission(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		String searchgroupid = request.getParameter("searchgroupid");
		short groupid = 0;
		if(searchgroupid!=null){
			groupid=(short)Common.toDigit(searchgroupid);
		}else{
			HttpSession session = request.getSession();
			groupid=(Short) session.getAttribute("jsprun_groupid");
		}
		this.setExtcredits(request);
		List<Map<String, String>> grouplists = dataBaseService.executeQuery("SELECT groupid, type, grouptitle FROM jrun_usergroups ORDER BY (creditshigher<>'0' || creditslower<>'0'), creditslower");
		StringBuffer memberGroups = new StringBuffer();
		StringBuffer systemGroups = new StringBuffer();
		StringBuffer specialGroups = new StringBuffer();
		for (Map<String, String> group : grouplists) {
			String grouptype = group.get("type");
			if ("member".equals(grouptype)) {
				memberGroups.append("<li><a href=\"my.jsp?item=grouppermission&amp;type="+grouptype+"&amp;searchgroupid="+group.get("groupid")+"\">"+group.get("grouptitle")+"</a></li>");
			} else if ("system".equals(grouptype)) {
				systemGroups.append("<li><a href=\"my.jsp?item=grouppermission&amp;type="+grouptype+"&amp;searchgroupid="+group.get("groupid")+"\">"+group.get("grouptitle")+"</a></li>");
			} else if ("special".equals(grouptype)) {
				specialGroups.append("<li><a href=\"my.jsp?item=grouppermission&amp;type="+grouptype+"&amp;searchgroupid="+group.get("groupid")+"\">"+group.get("grouptitle")+"</a></li>");
			}
		}
		request.setAttribute("memberGroups", memberGroups.toString());
		request.setAttribute("systemGroups", systemGroups.toString());
		request.setAttribute("specialGroups",specialGroups.toString());
		grouplists=null;
		List<Map<String,String>> usergroups=dataBaseService.executeQuery("SELECT * FROM jrun_usergroups u LEFT JOIN jrun_admingroups a ON u.groupid=a.admingid WHERE u.groupid='"+groupid+"'");
		if (usergroups != null&&usergroups.size()>0) {
			Map<String,String> usergroup=usergroups.get(0);
			usergroup.put("maxattachsize", String.valueOf(Integer.valueOf(usergroup.get("maxattachsize"))/1000));
			usergroup.put("maxsizeperday", String.valueOf(Integer.valueOf(usergroup.get("maxsizeperday"))/1000));
			request.setAttribute("usergroup", usergroup);
			request.setAttribute("type",request.getParameter("type"));
			return mapping.findForward("toMy");
		} else {
			request.setAttribute("errorInfo", getMessage(request, "usergroups_nonexistence"));
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward toMyPolls(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			threadadd = "";
			extrafid = "";
		}
		String type = request.getParameter("type");
		if ("poll".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,"SELECT COUNT(*) count FROM jrun_mythreads m,  jrun_threads t WHERE m.uid="+ uid + " AND m.special='1' " + threadadd+ " AND m.tid=t.tid", "my.jsp?item=polls&type=poll" + extrafid);
			List<Map<String, String>> polllists = dataBaseService.executeQuery("SELECT t.tid, t.subject, t.fid, t.displayorder, t.closed, t.lastposter, t.lastpost, f.name	FROM jrun_threads t, jrun_mythreads m ,jrun_forums f WHERE m.uid="+ uid+ " AND m.tid=t.tid AND t.fid=f.fid AND m.special='1' "+ threadadd+ " ORDER BY m.dateline DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
			this.setAttribute(request, session, polllists, "polllists");
		} else if ("join".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,"SELECT COUNT(*) count FROM jrun_myposts m, jrun_threads t	WHERE m.uid=" + uid+ " AND m.special='1' " + threadadd + " AND m.tid=t.tid", "my.jsp?item=polls&type=join" + extrafid);
			List<Map<String, String>> polllists = dataBaseService.executeQuery("SELECT m.dateline, t.tid, t.fid, t.subject, t.displayorder, t.closed, f.name FROM jrun_myposts m, jrun_threads t ,jrun_forums f WHERE m.uid="+ uid+ " AND m.tid=t.tid AND t.fid=f.fid AND m.special='1' "+ threadadd+ " ORDER BY m.dateline DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
			if(polllists!=null&&polllists.size()>0){
				String timeoffset=(String)session.getAttribute("timeoffset");
				String timeformat=(String)session.getAttribute("timeformat");
				String dateformat=(String)session.getAttribute("dateformat");
				SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
				for (Map<String, String> thread : polllists) {
					thread.put("dateline", Common.gmdate(sdf_all, Integer.valueOf(thread.get("dateline"))));
				}
				request.setAttribute("polllists", polllists);
			}
		}
		return mapping.findForward("toMy");
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public ActionForward toMyPromotion(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
		if(Common.empty(creditspolicys.get("promotion_visit")) && Common.empty(creditspolicys.get("promotion_register"))){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			extrafid = "";
		}
		request.setAttribute("creditspolicy", creditspolicys);
		return mapping.findForward("toMy");
	}
	public ActionForward toMyActivities(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String threadadd = null;
		String extrafid =null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			threadadd = "";
			extrafid = "";
		}
		String type = request.getParameter("type");
		String ended = request.getParameter("ended");
		ended = ended != null && (ended.equals("yes") || ended.equals("no")) ? ended: "";
		String sign = null;
		String ascadd = null;
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		if ("yes".equals(ended)) {
			sign = " AND starttimefrom<'" + timestamp + "'";
			ascadd = "DESC";
		} else if ("no".equals(ended)) {
			sign = " AND starttimefrom>='" + timestamp + "'";
			ascadd="";
		}else{
			sign="";
			ascadd="";
		}
		if ("orig".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,"SELECT COUNT(*) count FROM jrun_activities a LEFT JOIN jrun_threads t USING(tid) WHERE a.uid=" + uid+ " AND t.special='4' " + threadadd + sign, "my.jsp?item=activities&type=orig&ended=" + ended+ extrafid);
			List<Map<String, String>> activitylists = dataBaseService.executeQuery("SELECT a.tid,a.cost,a.starttimefrom,a.place,a.expiration,t.subject,t.displayorder FROM jrun_activities a LEFT JOIN jrun_threads t USING(tid) WHERE a.uid=" + uid+ " AND t.special='4' " + threadadd + sign+ " ORDER BY starttimefrom " + ascadd + " LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
			if(activitylists!=null&&activitylists.size()>0){
				String timeoffset=(String)session.getAttribute("timeoffset");
				String timeformat=(String)session.getAttribute("timeformat");
				String dateformat=(String)session.getAttribute("dateformat");
				SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
				for (Map<String, String> thread : activitylists) {
					thread.put("starttimefrom", Common.gmdate(sdf_all, Integer.valueOf(thread.get("starttimefrom"))));
				}
				request.setAttribute("activitylists", activitylists);
			}
		} else if ("apply".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,"SELECT COUNT(*) count FROM jrun_activityapplies aa LEFT JOIN jrun_activities a USING(tid) LEFT JOIN jrun_threads t USING(tid) WHERE aa.uid=" + uid + " "+ threadadd + sign, "my.jsp?item=activities&type=apply&ended=" + ended+ extrafid);
			List<Map<String, String>> activitylists = dataBaseService.executeQuery("SELECT aa.verified, aa.tid, starttimefrom, a.place, a.cost, t.subject,t.displayorder FROM jrun_activityapplies aa LEFT JOIN jrun_activities a USING(tid) LEFT JOIN jrun_threads t USING(tid) WHERE aa.uid="+ uid+ " "+ threadadd+ sign+ " ORDER BY starttimefrom "+ ascadd+ " LIMIT "+ multi.get("start_limit")+ ", "+ multi.get("perpage"));
			if(activitylists!=null&&activitylists.size()>0){
				String timeoffset=(String)session.getAttribute("timeoffset");
				String timeformat=(String)session.getAttribute("timeformat");
				String dateformat=(String)session.getAttribute("dateformat");
				SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
				for (Map<String, String> thread : activitylists) {
					thread.put("starttimefrom", Common.gmdate(sdf_all, Integer.valueOf(thread.get("starttimefrom"))));
				}
				request.setAttribute("activitylists", activitylists);
			}
		} else {
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		request.setAttribute("ended", ended);
		request.setAttribute("timestamp", timestamp);
		return mapping.findForward("toMy");
	}
	public ActionForward toMyTradestats(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String extrafid =null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}
		List<Map<String, String>> results = dataBaseService.executeQuery("SELECT COUNT(*) AS totalitems, SUM(price) AS tradesum FROM jrun_tradelog WHERE buyerid="+ uid+ " AND status IN ( "+ Tenpayapi.trade_typestatus("successtrades")+ " )");
		request.setAttribute("buystats", results.get(0) != null&& results.get(0).get("tradesum") != null ? results.get(0): null);
		results = dataBaseService.executeQuery("SELECT COUNT(*) AS totalitems, SUM(price) AS tradesum FROM jrun_tradelog WHERE sellerid=" + uid+ " AND status IN ( " + Tenpayapi.trade_typestatus("successtrades")+ " )");
		request.setAttribute("sellstats", results.get(0) != null&& results.get(0).get("tradesum") != null ? results.get(0): null);
		Map<String, Integer> attendstatus = new HashMap<String, Integer>();
		results = dataBaseService.executeQuery("SELECT status FROM jrun_tradelog WHERE buyerid="+ uid + " AND status IN ( " + Tenpayapi.trade_typestatus("buytrades")+ " )");
		int buyerattend = results != null ? results.size() : 0;
		if (buyerattend > 0) {
			for (Map<String, String> result : results) {
				String status = result.get("status");
				Integer sum = attendstatus.get(status);
				if (sum != null) {
					attendstatus.put(status, sum + 1);
				} else {
					attendstatus.put(status, 1);
				}
			}
		}
		request.setAttribute("buyerattend", buyerattend);
		results = dataBaseService.executeQuery("SELECT status FROM jrun_tradelog WHERE sellerid="+ uid + " AND status IN ( " + Tenpayapi.trade_typestatus("selltrades")+ " )");
		int sellerattend = results != null ? results.size() : 0;
		if (sellerattend > 0) {
			for (Map<String, String> result : results) {
				String status = result.get("status");
				Integer sum = attendstatus.get(status);
				if (sum != null) {
					attendstatus.put(status, sum + 1);
				} else {
					attendstatus.put(status, 1);
				}
			}
		}
		request.setAttribute("sellerattend", sellerattend);
		request.setAttribute("attendstatus", attendstatus);
		results = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_tradelog WHERE buyerid=" + uid + " AND status IN ( "+ Tenpayapi.trade_typestatus("tradingtrades") + " )");
		request.setAttribute("goodsbuyer",results.get(0).get("count"));
		results = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_trades WHERE sellerid=" + uid + " AND closed='0'");
		request.setAttribute("goodsseller",results.get(0).get("count"));
		results = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_tradelog WHERE buyerid=" + uid + " AND status IN ( "+ Tenpayapi.trade_typestatus("eccredittrades")+ " ) AND (ratestatus=0 OR ratestatus=2)");
		request.setAttribute("eccreditbuyer",results.get(0).get("count"));
		results = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_tradelog WHERE sellerid=" + uid + " AND status IN ( "+ Tenpayapi.trade_typestatus("eccredittrades")+ " ) AND (ratestatus=0 OR ratestatus=1)");
		request.setAttribute("eccreditseller",results.get(0).get("count"));
		return mapping.findForward("toMy");
	}
	public ActionForward toMyTrades(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			threadadd = "";
			extrafid = "";
		}
		String filter = request.getParameter("filter");
		String myitem = request.getAttribute("myitem").toString();
		String srchkey = request.getParameter("srchkey");
		int tid =Common.toDigit(request.getParameter("tid"));
		int pid =Common.toDigit(request.getParameter("pid"));
		String sqlfield = "selltrades".equals(myitem) ? "sellerid" : "buyerid";
		String sqlfilter = "";
		String typestatus = "";
		if ("attention".equals(filter)) {
			typestatus = myitem;
		} else if ("eccredit".equals(filter)) {
			typestatus = "eccredittrades";
			sqlfilter += myitem.equals("selltrades") ? " AND (tl.ratestatus=0 OR tl.ratestatus=1) ": " AND (tl.ratestatus=0 OR tl.ratestatus=2) ";
		} else if ("all".equals(filter)) {
			typestatus = "";
		} else if ("success".equals(filter)) {
			typestatus = "successtrades";
		} else if ("closed".equals(filter)) {
			typestatus = "closedtrades";
		} else if ("refund".equals(filter)) {
			typestatus = "refundtrades";
		} else if ("unstart".equals(filter)) {
			typestatus = "unstarttrades";
		} else {
			typestatus = "tradingtrades";
			filter = "";
		}
		sqlfilter += typestatus.length()>0 ? " AND tl.status IN ( "+ Tenpayapi.trade_typestatus(typestatus) + " )" : "";
		String sqlkey =null;
		String extrasrchkey = null;
		if (srchkey != null) {
			sqlkey = " AND tl.subject like \'%" + Common.addslashes(srchkey).replace('*', '%')+ "%\'";
			extrasrchkey = "&srchkey=" + Common.encode(srchkey);
		} else {
			sqlkey = "";
			extrasrchkey = "";
		}
		String sqltid = tid >0 ? " AND tl.tid=" + tid+ (pid >0 ? " AND tl.pid=" + pid : "") : "";
		String extratid = tid >0 ? "&tid=" + tid+ (pid >0 ? "&pid" + pid : "") : "";
		Map<String, Integer> multi = this.multi(request, response, uid, "SELECT COUNT(*) count FROM jrun_tradelog tl, jrun_threads t WHERE tl.tid=t.tid AND tl." + sqlfield+ "=" + uid + threadadd + sqltid + sqlkey + sqlfilter,"my.jsp?item=" + myitem + extratid + extrafid+ (filter != null ? "&filter=" + filter : "")+ extrafid + extrasrchkey);
		List<Map<String, String>> tradelists = dataBaseService.executeQuery("SELECT tl.*, tr.aid, t.subject AS threadsubject FROM jrun_tradelog tl, jrun_threads t, jrun_trades tr WHERE tl.tid=t.tid AND tr.pid=tl.pid AND tr.tid=tl.tid AND tl."+ sqlfield + "=" + uid + threadadd + sqltid + sqlkey+ sqlfilter + " ORDER BY tl.lastupdate DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
		if (tradelists != null && tradelists.size() > 0) {
			MessageResources resources = getResources(request);
			Locale locale = getLocale(request);
			String timeoffset=(String)session.getAttribute("timeoffset");
			String timeformat=(String)session.getAttribute("timeformat");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for (Map<String, String> tradelog : tradelists) {
				int status=Integer.valueOf(tradelog.get("status"));
				tradelog.put("lastupdate", Common.gmdate(sdf_all, Integer.valueOf(tradelog.get("lastupdate"))));
				tradelog.put("attend", Tenpayapi.trade_typestatus(myitem, status)?"true":null);
				tradelog.put("status", (String)Tenpayapi.trade_getstatus(status,resources,locale));
			}
			request.setAttribute("tradelists", tradelists);
		}
		request.setAttribute("extratid", extratid);
		request.setAttribute("filter", filter);
		request.setAttribute("tid", tid);
		request.setAttribute("srchkey", srchkey);
		request.setAttribute("extrasrchkey", extrasrchkey);
		return mapping.findForward("toMy");
	}
	public ActionForward toMyTradethreads(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			extrafid = "";
		}
		String srchkey = request.getParameter("srchkey");
		int tid =Common.toDigit(request.getParameter("tid"));
		String sqlkey = null;
		String extrasrchkey = null;
		if (srchkey != null&&srchkey.length()>0) {
			sqlkey = "AND subject like \'%" + Common.addslashes(srchkey).replace('*', '%') + "%\'";
			extrasrchkey = "&srchkey=" + Common.encode(srchkey);
			srchkey=Common.htmlspecialchars(srchkey);
		} else {
			sqlkey = "";
			extrasrchkey = "";
		}
		String sqltid = tid >0 ? "AND tid=" + tid : "";
		String extratid = tid >0 ? "&tid=" + tid : "";
		Map<String, Integer> multi = this.multi(request, response, uid, "SELECT COUNT(*) count FROM jrun_trades WHERE sellerid=" + uid + " " + sqltid + " " + sqlkey, "my.jsp?item=tradethreads"+ extratid + extrafid + extrasrchkey);
		List<Map<String, String>> tradelists = dataBaseService.executeQuery("SELECT * FROM jrun_trades WHERE sellerid=" + uid+ " " + sqltid + " " + sqlkey+ " ORDER BY tradesum DESC, totalitems DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		if (tradelists != null && tradelists.size() > 0) {
			String timeoffset=(String)session.getAttribute("timeoffset");
			String timeformat=(String)session.getAttribute("timeformat");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for (Map<String, String> tradethread : tradelists) {
				tradethread.put("lastupdate", Common.gmdate(sdf_all, Integer.valueOf(tradethread.get("lastupdate"))));
				tradethread.put("lastbuyererenc", Common.encode(tradethread.get("lastbuyer")));
				double expiration=Integer.valueOf(tradethread.get("expiration"));
				if (expiration>0) {
					expiration = (expiration - timestamp) / 86400d;
					if (expiration > 0) {
						tradethread.put("expirationhour", String.valueOf((int) Math.floor((expiration - Math.floor(expiration)) * 24)));
						tradethread.put("expiration", String.valueOf((int) Math.floor(expiration)));
					} else {
						tradethread.put("expiration", "-1");
					}
				}
			}
			request.setAttribute("tradelists", tradelists);
		}
		request.setAttribute("tid", tid);
		request.setAttribute("srchkey", srchkey);
		request.setAttribute("extrasrchkey", extrasrchkey);
		request.setAttribute("extratid", extratid);
		return mapping.findForward("toMy");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMyReward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			extrafid = "";
		}
		Map<String, String> settings = ForumInit.settings;
		Map extcredits = dataParse.characterParse( settings.get("extcredits"),false);
		String creditstrans = settings.get("creditstrans");
		request.setAttribute("extcredits", extcredits);
		request.setAttribute("creditstrans", creditstrans != null&& !creditstrans.equals("") ? Integer.valueOf(creditstrans) : 0);
		String type = request.getParameter("type");
		String filter = request.getParameter("filter");
		List<Map<String, String>> rewardloglists = null;
		if ("stats".equals(type)) {
			Map<String, String> questions = new HashMap<String, String>();
			Map<String, String> answers = new HashMap<String, String>();
			List<Map<String, String>> results = null;
			Map<String, String> map = null;
			results = dataBaseService.executeQuery("SELECT COUNT(*) total, SUM(ABS(netamount)) totalprice FROM jrun_rewardlog WHERE authorid=" + uid);
			map = results != null && results.size() > 0 ? results.get(0) : null;
			questions.put("total", map != null ? map.get("total") : "0");
			questions.put("totalprice", map != null&& map.get("totalprice") != null ? map.get("totalprice"): "0");
			results = dataBaseService.executeQuery("SELECT COUNT(*) solved FROM jrun_rewardlog WHERE authorid=" + uid + " and answererid>0");
			map = results != null && results.size() > 0 ? results.get(0) : null;
			questions.put("solved", map != null ? map.get("solved") : "0");
			questions.put("percent", number_format(questions.get("total"),questions.get("solved")));
			request.setAttribute("questions", questions);
			results = dataBaseService.executeQuery("SELECT COUNT(*) total FROM jrun_rewardlog WHERE answererid=" + uid);
			map = results != null && results.size() > 0 ? results.get(0) : null;
			answers.put("total", map != null ? map.get("total") : "0");
			results = dataBaseService.executeQuery("SELECT COUNT(*) tids, SUM(ABS(t.price)) totalprice FROM jrun_rewardlog r LEFT JOIN jrun_threads t USING(tid) WHERE r.authorid>0 and r.answererid="+ uid);
			map = results != null && results.size() > 0 ? results.get(0) : null;
			answers.put("adopted", map != null && map.get("tids") != null ? map.get("tids") : "0");
			answers.put("totalprice", map != null&& map.get("totalprice") != null ? map.get("totalprice"): "0");
			answers.put("percent", number_format(answers.get("total"), answers.get("adopted")));
			request.setAttribute("answers", answers);
		} else if("question".equals(type)) {
			filter = filter != null&& (filter.equals("solved") || filter.equals("unsolved")) ? filter: "";
			String sqlfilter = filter.equals("") ? "" : (filter.equals("solved") ? " AND r.answererid>0": " AND r.answererid=0");
			Map<String, Integer> multi = multi(request, response, uid,  "SELECT COUNT(*) count FROM jrun_rewardlog r WHERE authorid=" + uid + sqlfilter,"my.jsp?item=reward&type=question&filter=" + filter);
			rewardloglists = dataBaseService.executeQuery("SELECT r.tid, r.answererid, r.dateline,r.netamount, t.subject, t.price, m.uid, m.username, f.fid, f.name FROM jrun_rewardlog r LEFT JOIN jrun_threads t ON t.tid=r.tid	LEFT JOIN jrun_forums f ON f.fid=t.fid LEFT JOIN jrun_members m ON m.uid=r.answererid WHERE r.authorid="+ uid+ sqlfilter+ " ORDER BY r.dateline DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
		} else if ("answer".equals(type)) {
			filter = filter != null&& (filter.equals("adopted") || filter.equals("unadopted")) ? filter: "";
			String sqlfilter = filter.equals("") ? "" : (filter.equals("adopted") ? " AND r.authorid>0": " AND r.authorid=0");
			Map<String, Integer> multi = multi(request, response, uid, "SELECT COUNT(*) count FROM jrun_rewardlog r WHERE answererid=" + uid + sqlfilter,"my.jsp?item=reward&type=answer&filter=" + filter);
			rewardloglists = dataBaseService.executeQuery("SELECT r.tid, r.authorid, r.dateline, t.subject, t.price, m.uid, m.username, f.fid, f.name FROM jrun_rewardlog r LEFT JOIN jrun_threads t ON t.tid=r.tid	LEFT JOIN jrun_forums f ON f.fid=t.fid LEFT JOIN jrun_members m ON m.uid=t.authorid	WHERE r.answererid="+ uid+ sqlfilter+ " ORDER BY r.dateline DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
		} else {
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		if (rewardloglists != null && rewardloglists.size() > 0) {
			String timeoffset=(String)session.getAttribute("timeoffset");
			String timeformat=(String)session.getAttribute("timeformat");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for (Map<String, String> rewardlog : rewardloglists) {
				rewardlog.put("dateline", Common.gmdate(sdf_all, Integer.valueOf(rewardlog.get("dateline"))));
				rewardlog.put("price", String.valueOf(Math.abs(Short.valueOf(rewardlog.get("price")))));
			}
			request.setAttribute("rewardloglists", rewardloglists);
		}
		request.setAttribute("filter", filter);
		return mapping.findForward("toMy");
	}
	public ActionForward toMyDebate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		String type = request.getParameter("type");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			threadadd = "";
			extrafid = "";
		}
		if ("orig".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,"SELECT COUNT(*) count FROM jrun_mythreads m, jrun_threads t WHERE m.uid="+ uid + " AND m.special='5' " + threadadd+ " AND m.tid=t.tid", "my.jsp?item=debate&type=orig" + extrafid);
			List<Map<String, String>> debatelists = dataBaseService.executeQuery("SELECT t.tid, t.subject, t.fid, t.displayorder, t.closed, t.lastposter, t.lastpost, f.name	FROM jrun_threads t, jrun_mythreads m ,jrun_forums f WHERE m.uid="+ uid+ " AND m.tid=t.tid AND t.fid=f.fid AND m.special='5' "+ threadadd+ " ORDER BY m.dateline DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
			this.setAttribute(request, session, debatelists, "debatelists");
		} else if ("apply".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,"SELECT COUNT(*) count FROM jrun_myposts m, jrun_threads t	WHERE m.uid=" + uid+ " AND m.special='5' " + threadadd + " AND m.tid=t.tid", "my.jsp?item=debate&type=apply" + extrafid);
			List<Map<String, String>> debatelists = dataBaseService.executeQuery("SELECT m.dateline, t.tid, t.fid, t.subject, t.displayorder, t.closed, f.name FROM jrun_myposts m, jrun_threads t ,jrun_forums f WHERE m.uid="+ uid+ " AND m.tid=t.tid AND t.fid=f.fid AND m.special='5' "+ threadadd+ " ORDER BY m.dateline DESC LIMIT "+ multi.get("start_limit") + ", " + multi.get("perpage"));
			if(debatelists!=null&&debatelists.size()>0){
				String timeoffset=(String)session.getAttribute("timeoffset");
				String timeformat=(String)session.getAttribute("timeformat");
				String dateformat=(String)session.getAttribute("dateformat");
				SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
				for (Map<String, String> debatelist : debatelists) {
					debatelist.put("dateline", Common.gmdate(sdf_all, Integer.valueOf(debatelist.get("dateline"))));
				}
				request.setAttribute("debatelists", debatelists);
			}
			debatelists=null;
		}
		return mapping.findForward("toMy");
	}
	public ActionForward toMyVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String extrafid=null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			extrafid = "";
		}
		Map<String, Integer> multi = this.multi(request, response, uid, "SELECT COUNT(*) count FROM jrun_videos WHERE uid=" + uid,"my.jsp?item=video");
		List<Map<String, String>> videolists = dataBaseService.executeQuery("SELECT * FROM jrun_videos WHERE uid='" + uid+ "' ORDER BY dateline DESC LIMIT " + multi.get("start_limit")+ ", " + multi.get("perpage"));
		int videonum = videolists.size();
		int colspan = videonum % 2;
		StringBuffer videoendrows = new StringBuffer();
		if (colspan > 0) {
			while ((colspan - 2) < 0) {
				videoendrows.append("<td></td>");
				colspan++;
			}
			videoendrows.append("</tr>");
		}
		if(videolists!=null&&videolists.size()>0){
			String timeoffset=(String)session.getAttribute("timeoffset");
			String timeformat=(String)session.getAttribute("timeformat");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for (Map<String, String> videolist : videolists) {
				videolist.put("dateline", Common.gmdate(sdf_all, Integer.valueOf(videolist.get("dateline"))));
			}
			request.setAttribute("videolists", videolists);
		}
		request.setAttribute("videoendrows", videoendrows.toString());
		videolists=null;
		videoendrows=null;
		return mapping.findForward("toMy");
	}
	public ActionForward toMyBuddylist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short)Common.range(Common.intval(request.getParameter("srchfid")),32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}else{
			extrafid = "";
		}
		try{
			if(submitCheck(request, "buddysubmit",true)){
				String[] delete = request.getParameterValues("delete");
				if (delete != null) {
					StringBuffer buddyids=new StringBuffer();
					for (String buddyid : delete) {
						buddyids.append(buddyid+",");
					}
					int length=buddyids.length();
					if (length>0) {
						buddyids.deleteCharAt(length-1);
						dataBaseService.runQuery("DELETE FROM jrun_buddys WHERE uid=" + uid + " AND buddyid IN ( "+ buddyids + " )",true);
					}
				}
				List<Map<String, String>> buddys = dataBaseService.executeQuery("SELECT buddyid,description FROM jrun_buddys WHERE uid=" + uid);
				if (buddys != null && buddys.size() > 0) {
					for (Map<String, String> buddy : buddys) {
						String description = request.getParameter("description["+ buddy.get("buddyid") + "]");
						if (description != null) {
							description = description.length() > 255 ? description.substring(0, 255) : description;
							if (!description.equals(buddy.get("description"))) {
								dataBaseService.runQuery("UPDATE jrun_buddys SET description='" + Common.addslashes(description)+ "' WHERE uid=" + uid + " AND buddyid="+ buddy.get("buddyid"),true);
							}
						}
					}
				}
				String jsprun_userss = session.getAttribute("jsprun_userss").toString();
				String newbuddy = request.getParameter("newbuddy");
				String newbuddyid = request.getParameter("newbuddyid");
				if (newbuddyid != null) {
					Common.setResponseHeader(response);
				}
				if ((newbuddy != null && !"".equals(newbuddy) && !newbuddy.equals(jsprun_userss))|| (newbuddyid != null && !newbuddyid.equals(uid+""))) {
					byte jsprun_adminid = (Byte) session.getAttribute("jsprun_adminid");
					if (jsprun_adminid == 0) {
						if (buddys != null && buddys.size() > 20) {
							if (newbuddyid != null) {
								try {
									response.getWriter().write(getMessage(request, "buddy_add_toomany"));
								} catch (IOException e) {
									e.printStackTrace();
								}
								return null;
							} else {
								request.setAttribute("resultInfo", getMessage(request, "buddy_add_toomany"));
								return mapping.findForward("showMessage");
							}
						}
					}
					List<Map<String, String>> members = dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE "+ (newbuddyid == null ? "username='" + Common.addslashes(newbuddy) + "'": "uid=" + newbuddyid));
					if (members != null && members.size() > 0) {
						String buddyid = members.get(0).get("uid");
						boolean flag = false;
						if (buddys != null && buddys.size() > 0) {
							for (Map<String, String> buddy : buddys) {
								if (buddyid.equals(buddy.get("buddyid"))) {
									flag = true;
								}
							}
						}
						if (flag) {
							if (newbuddyid != null) {
								try {
									response.getWriter().write(getMessage(request, "buddy_add_invalid"));
								} catch (IOException e) {
									e.printStackTrace();
								}
								return null;
							} else {
								request.setAttribute("resultInfo", getMessage(request, "buddy_add_invalid"));
								return mapping.findForward("showMessage");
							}
						} else {
							int timestamp = (Integer)(request.getAttribute("timestamp"));
							String newdescription = request.getParameter("newdescription");
							dataBaseService.runQuery("INSERT INTO jrun_buddys (uid, buddyid, dateline, description) VALUES ("+ uid+ ", "+ buddyid+ ", '"+ timestamp+ "', '"+ (newdescription != null ? (newdescription.length() > 255 ? Common.addslashes(newdescription.substring(0, 255)): Common.addslashes(newdescription)): "") + "')",true);
						}
					} else {
						if (newbuddyid != null) {
							try {
								response.getWriter().write(getMessage(request, "username_nonexistence"));
							} catch (IOException e) {
								e.printStackTrace();
							}
							return null;
						} else {
							request.setAttribute("errorInfo", getMessage(request, "username_nonexistence"));
							return mapping.findForward("showMessage");
						}
					}
				}
				if (newbuddyid != null) {
					try {
						response.getWriter().write(getMessage(request, "buddy_update_succeed"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				} else {
					if(Common.isshowsuccess(session, "buddy_update_succeed")){
						Common.requestforward(response, "my.jsp?item=buddylist");
						return null;
					}else{
						request.setAttribute("successInfo", getMessage(request, "buddy_update_succeed"));
						request.setAttribute("requestPath", "my.jsp?item=buddylist");
						return mapping.findForward("showMessage");
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		List<Map<String, String>> buddylists = dataBaseService.executeQuery("SELECT b.buddyid,b.dateline, b.description,m.username FROM jrun_buddys b,jrun_members m WHERE b.uid="+ uid+ " AND m.uid=b.buddyid ORDER BY dateline DESC");
		if(buddylists!=null&&buddylists.size()>0){
			String timeoffset=(String)session.getAttribute("timeoffset");
			String timeformat=(String)session.getAttribute("timeformat");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for (Map<String, String> videolist : buddylists) {
				videolist.put("dateline", Common.gmdate(sdf_all, Integer.valueOf(videolist.get("dateline"))));
			}
			request.setAttribute("buddylists", buddylists);
		}
		return mapping.findForward("toMy");
	}
	@SuppressWarnings("unchecked")
	private void setExtcredits(HttpServletRequest request) {
		request.setAttribute("extcredits", dataParse.characterParse(ForumInit.settings.get("extcredits"),true));
	}
	@SuppressWarnings("unchecked")
	private Map<String, Integer> multi(HttpServletRequest request,HttpServletResponse response, int uid, String sql, String url) {
		HttpSession session=request.getSession();
		Map<String, String> settings = ForumInit.settings;
		Members member = uid > 0 ? (Members)session.getAttribute("user") : null;
		List<Map<String, String>> count = dataBaseService.executeQuery(sql);
		int threadcount = Integer.valueOf(count.get(0).get("count"));
		Long threadmaxpages =Long.valueOf(settings.get("threadmaxpages"));
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
		int page = Common.range(Common.intval(request.getParameter("page")),threadmaxpages.intValue(), 1);
		Map<String,Integer> multiInfo=Common.getMultiInfo(threadcount, tpp, page);
		page=multiInfo.get("curpage");
		Map<String,Object> multi=Common.multi(threadcount, tpp, page, url, threadmaxpages.intValue(), 10, true, false, null);
		request.setAttribute("multi", multi);
		multiInfo.put("perpage", tpp);
		return multiInfo;
	}
	private String number_format(String total, String num) {
		return Common.number_format((Float.valueOf(total) > 0 ? Float.valueOf(num)/ Float.valueOf(total) * 100 : 0), "0.00")+ "%";
	}
	private void setAttribute(HttpServletRequest request,HttpSession session,List<Map<String,String>> lists,String attributeName){
		if(lists!=null&&lists.size()>0){
			String timeoffset=(String)session.getAttribute("timeoffset");
			String timeformat=(String)session.getAttribute("timeformat");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for (Map<String, String> list : lists) {
				list.put("lastpost", Common.gmdate(sdf_all, Integer.valueOf(list.get("lastpost"))));
				list.put("lastposterenc",Common.encode(list.get("lastposter")));
			}
			request.setAttribute(attributeName, lists);
		}
	}
}