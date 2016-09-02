package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Pms;
import cn.jsprun.domain.Polloptions;
import cn.jsprun.domain.Polls;
import cn.jsprun.domain.Posts;
import cn.jsprun.domain.Threads;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.foreg.vo.topicadmin.OtherBaseVO;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.Mail;
import cn.jsprun.vo.logs.RatelogVO;
public class MiscAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward emailfriend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer)(session.getAttribute("jsprun_uid"));
		String boardurl = (String) session.getAttribute("boardurl");
		String message = "";
		String inajax = request.getParameter("inajax");
		if (jsprun_uid == 0) {
			message = getMessage(request, "not_loggedin");
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message, true);
				return null;
			}
		}
		int tid = Common.toDigit(request.getParameter("tid"));
		Map<String,String> settings=ForumInit.settings;
		List<Map<String,String>> threads = dataBaseService.executeQuery("select t.tid,t.subject,f.name,f.fup,f.fid,f.type from jrun_threads as t left join jrun_forums as f on t.fid=f.fid where t.tid='"+tid+"'");
		if(threads==null || threads.size()<=0){
			message = getMessage(request, "undefined_action_return");
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message, true);
				return null;
			}
		}
		Map<String,String> thread = threads.get(0);threads=null;
		try{
			if(submitCheck(request, "sendsubmit")){
				String jsprun_userss = (String)(session.getAttribute("jsprun_userss"));
				String sendtoemail = request.getParameter("sendtoemail");
				String mess = request.getParameter("message"); 
				if (Common.empty(sendtoemail)) {
					message = getMessage(request, "email_friend_invalid");
					if(inajax==null){
						request.setAttribute("errorInfo", message);
						return mapping.findForward("showMessage");
					}else{
						Common.writeMessage(response,message,true);
						return null;
					}
				}
				mess = mess==null?"":mess;
				message = getMessage(request, "email_friend_succeed");
				Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
				Mail mail=new Mail(mails);
				String fromemail = settings.get("bbname")+" <"+mails.get("from")+">";
				mail.sendMessage(fromemail,"<"+sendtoemail+">",getMessage(request, "email_to_friend_subject", settings.get("bbname"),jsprun_userss,thread.get("subject")),getMessage(request, "email_to_friend_message", settings.get("bbname"),jsprun_userss,mess,boardurl),null);
				if(inajax==null){
					request.setAttribute("successInfo", message);
					request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
					return mapping.findForward("showMessage");
				}else{
					Common.writeMessage(response,message,false);
					return null;
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
		String fromuid = !Common.empty(creditspolicys.get("promotion_visit")) ? "&amp;fromuid="+jsprun_uid : "";
		String navigation = "<a href=\"forumdisplay.jsp?fid="+thread.get("fid")+"\">"+thread.get("name")+"</a> &raquo; <a href=\"viewthread.jsp?tid="+thread.get("tid")+"\">"+thread.get("subject")+"</a>";
		if(thread.get("type").equals("sub")){
			Map<String,String> parentforum = dataBaseService.executeQuery("select fid,name from jrun_forums where fid="+thread.get("fup")).get(0);
			navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.get("fid")+"\">"+parentforum.get("name")+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+thread.get("fid")+"\">"+thread.get("name")+"</a> &raquo <a href=\"viewthread.jsp?tid="+thread.get("tid")+"\">"+thread.get("subject")+"</a>";
		}
		request.setAttribute("navigation", navigation);
		Members member = (Members)session.getAttribute("user");
		String content = getMessage(request, "emailfriend_message", settings.get("bbname"),thread.get("subject"),boardurl+ "viewthread.jsp?tid=" + tid+fromuid);
		request.setAttribute("member", member);
		request.setAttribute("content", content);
		request.setAttribute("tid", Integer.valueOf(tid));
		thread = null;
		return mapping.findForward("toemailfriend");
	}
	@SuppressWarnings("unchecked")
	public ActionForward report(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int tid = Common.toDigit(request.getParameter("tid"));
		int fid = Common.toDigit(request.getParameter("fid"));
		int pid = Common.toDigit(request.getParameter("pid"));
		String boardurl = (String) session.getAttribute("boardurl");
		String inajax = request.getParameter("inajax");
		int jsprun_uid = (Integer) (session.getAttribute("jsprun_uid")==null?0:session.getAttribute("jsprun_uid"));
		request.setAttribute("tid", Integer.valueOf(tid));
		request.setAttribute("fid", (short)fid);
		request.setAttribute("pid", pid);
		Map<String,String> settings = ForumInit.settings;
		String reportpost = settings.get("reportpost");
		String message = "";
		if (reportpost.equals("0")) {
			message = getMessage(request, "thread_report_disabled");
			if(inajax!=null){
				Common.writeMessage(response, message, true);
				return null;
			}else{
				request.setAttribute("errorInfo",message);
				return mapping.findForward("showMessage");
			}
		}
		if (jsprun_uid == 0) {
			message = getMessage(request, "not_loggedin");
			if(inajax!=null){
				Common.writeMessage(response, message, true);
				return null;
			}else{
				request.setAttribute("errorInfo",message);
				return mapping.findForward("showMessage");
			}
		}
		Threads tread = threadService.findByTid(tid);
		if (tread == null) {
			message = getMessage(request, "undefined_action_return");
			if(inajax!=null){
				Common.writeMessage(response, message, true);
				return null;
			}else{
				request.setAttribute("errorInfo",message);
				return mapping.findForward("showMessage");
			}
		}
		String floodctrls = settings.get("floodctrl");
		int floodctrl = convertInt(floodctrls) * 3;
		if (timestamp - tread.getLastpost() < floodctrl) {
			message = getMessage(request, "thread_report_flood_ctrl", floodctrl+"");
			if(inajax!=null){
				Common.writeMessage(response, message, true);
				return null;
			}else{
				request.setAttribute("errorInfo",message);
				return mapping.findForward("showMessage");
			}
		}
		try{
			if(submitCheck(request, "reportsubmit")){
				String reason = request.getParameter("reason");
				Members member = (Members)session.getAttribute("user");
				String page = request.getParameter("page");
				String posturl = boardurl + "viewthread.jsp?tid=" + tid + "&page="+page+"#pid"+ pid;
				String uids = "0";
				String adminids = "";
				String tos1 = request.getParameter("to1");
				String tos2 = request.getParameter("to2");
				String tos3 = request.getParameter("to3");
				if (tos1 != null || tos2 != null || tos3 != null) {
					if (tos3 != null) {
						String modertarhql = "select m.uid from jrun_moderators m where m.fid='"+ fid+"'";
						List<Map<String, String>> modertar = dataBaseService.executeQuery(modertarhql);
						if (modertar != null) {
							for (int i = 0; i < modertar.size(); i++) {
								Map modMap = modertar.get(i);
								uids += "," + modMap.get("uid");
							}
						}
						modertar = null;
					}
					if (uids.equals("0") || (convertInt(reportpost) >= 2 && tos2 != null)) {
						adminids += ",2";
					}
					if (reportpost.equals("3") && tos1 != null) {
						adminids += ",1";
					}
					if (!adminids.equals("")) {
						String modertarhql = "select m.uid from jrun_members m where adminid in ("+ adminids.substring(1) + ")";
						List<Map<String, String>> memberslist = dataBaseService.executeQuery(modertarhql);
						if(memberslist==null || memberslist.size()<=0){
							memberslist = dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE adminid='1'");
						}
						if (memberslist != null && memberslist.size()>0) {
							for (int i = 0; i < memberslist.size(); i++) {
								Map modMap = memberslist.get(i);
								uids += "," + modMap.get("uid");
							}
						}
						memberslist = null;
					}
					String modertarhql = "SELECT uid, ignorepm FROM jrun_memberfields  WHERE uid IN ("+ uids + ")";
					List<Map<String, String>> membersfildslist = dataBaseService.executeQuery(modertarhql);
					List<Pms> pmslist = new ArrayList<Pms>();
					if (membersfildslist != null) {
						String repex = "(^{ALL}$|(,|^)\\s*" + member.getUsername()+ "\\s*(,|$))";
						for (int i = 0; i < membersfildslist.size(); i++) {
							Map<String,String> memfildMap = membersfildslist.get(i);
							if (!Common.matches(memfildMap.get("ignorepm"),repex)) {
								Pms pms = new Pms();
								pms.setDateline(timestamp);
								pms.setFolder("inbox");
								pms.setMsgfrom(member.getUsername());
								pms.setMsgfromid(member.getUid());
								pms.setDelstatus(Byte.valueOf("0"));
								pms.setMessage(getMessage(request, "reportpost_message", member.getUsername(),posturl,reason));
								pms.setSubject(getMessage(request, "reportpost_subject", member.getUsername()));
								pms.setMsgtoid(convertInt(memfildMap.get("uid")));
								pms.setNew_(Byte.valueOf("1"));
								pmslist.add(pms);
							}
						}
					}
					membersfildslist = null;
					String updatemember = "update jrun_members set newpm=1 where uid=" + jsprun_uid;
					dataBaseService.runQuery(updatemember);
					pmsServer.insertPmsList(pmslist);
					if(inajax!=null){
						Common.writeMessage(response,getMessage(request, "ajax_thread_report_succeed"),false);
						return null;
					}else{
						request.setAttribute("successInfo", getMessage(request, "ajax_thread_report_succeed"));
						request.setAttribute("requestPath", "viewthread.jsp?tid="+tid+"&page="+page);
						return mapping.findForward("showMessage");
					}
				} else {
					message = getMessage(request, "thread_report_invalid");
					if(inajax!=null){
						Common.writeMessage(response, message, true);
						return null;
					}else{
						request.setAttribute("errorInfo",message);
						return mapping.findForward("showMessage");
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		if(inajax==null){
			Map<String,String> forum = dataBaseService.executeQuery("select fid,name,fup,type from jrun_forums where fid="+fid).get(0);
			String navigation = "<a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+forum.get("name")+"</a> &raquo; <a href=\"viewthread.jsp?tid="+tread.getTid()+"\">"+tread.getSubject()+"</a>";
			if(forum.get("type").equals("sub")){
				Map<String,String> parentforum = dataBaseService.executeQuery("select fid,name from jrun_forums where fid="+forum.get("fup")).get(0);
				navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.get("fid")+"\">"+parentforum.get("name")+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+forum.get("name")+"</a> &raquo <a href=\"viewthread.jsp?tid="+tread.getTid()+"\">"+tread.getSubject()+"</a>";
			}
			request.setAttribute("navigation", navigation);
		}
		return mapping.findForward("toreportpost");
	}
	@SuppressWarnings("unchecked")
	public ActionForward rate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings = ForumInit.settings;
		String reasons = settings.get("modreasons");
		if(reasons!=null && !reasons.equals("")){
			List<String> reasonlist = new ArrayList<String>();
			String rea[] = reasons.split("\n");
			for(int i=0;i<rea.length;i++){
				reasonlist.add(rea[i]);
			}
			OtherBaseVO othervo = new OtherBaseVO();
			othervo.setReasonList(reasonlist);
			request.setAttribute("valueObject", othervo);
		}
		String inajax = request.getParameter("inajax");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int tid = Common.toDigit(request.getParameter("tid"));
		int pid = Common.toDigit(request.getParameter("pid"));
		HttpSession session = request.getSession();
		String timeformat = settings.get("gtimeformat");
		String timeoffset = settings.get("timeoffset");
		String dateformat = settings.get("dateformat");
		String boardurl = (String) session.getAttribute("boardurl");
		String message = "";
		int jrun_uid = (Integer)session.getAttribute("jsprun_uid");
		if(jrun_uid==0){
			message = getMessage(request, "not_loggedin");
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message,true);
				return null;
			}
		}
		short jsprun_groupid = (Short) session.getAttribute("jsprun_groupid");
		byte jsprun_adminid = (Byte) session.getAttribute("jsprun_adminid");
		Members member = (Members)session.getAttribute("user");
		List<Map<String,String>> usergroups = dataBaseService.executeQuery("select raterange,grouptitle,reasonpm from jrun_usergroups where groupid="+jsprun_groupid);
		Map<String,String> usergroup = usergroups.get(0);
		usergroups = null;
		Posts post = postService.getPostsById(pid);
		String edittimelimit = settings.get("karmaratelimit");
		request.setAttribute("post", post);
		if(post==null){
			message = getMessage(request, "undefined_action_return");
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message,true);
				return null;
			}
		}
		tid = post.getTid();
		request.setAttribute("tid", post.getTid());
		request.setAttribute("pid", pid);
		boolean modertar = Common.ismoderator(post.getFid(), member);
		List<Map<String,String>> threadslist = dataBaseService.executeQuery("select t.tid,t.subject,f.fid,f.name,f.type,f.fup from jrun_threads as t left join jrun_forums as f on t.fid=f.fid where t.tid="+tid);
		if(threadslist==null||threadslist.size()<=0){
			message = getMessage(request, "undefined_action_return");
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message, true);
				return null;
			}
		}
		Map<String,String>threads=threadslist.get(0);
		if(inajax==null){
			request.setAttribute("thread", threads);
			String page = request.getParameter("page");
			request.setAttribute("page", page);
			String navigation = "<a href=\"forumdisplay.jsp?fid="+threads.get("fid")+"\">"+threads.get("name")+"</a> &raquo; <a href=\"viewthread.jsp?tid="+threads.get("tid")+"\">"+threads.get("subject")+"</a>";
			if(threads.get("type").equals("sub")){
				Forums parentforum = forumService.findById(Short.valueOf((threads.get("fup"))));
				navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.getFid()+"\">"+parentforum.getName()+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+threads.get("fid")+"\">"+threads.get("name")+"</a> &raquo <a href=\"viewthread.jsp?tid="+threads.get("tid")+"\">"+threads.get("subject")+"</a>";
			}
			request.setAttribute("navigation", navigation);
		}
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		if(jrun_uid==post.getAuthorid()){
			message = getMessage(request, "ajax_thread_rate_member_invalid");
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message,true);
				return null;
			}
		}
		if(!edittimelimit.equals("0")){
		if(!modertar && jrun_uid!=post.getAuthorid() && (timestamp-post.getDateline())<=convertInt(edittimelimit)*60){
			message = getMessage(request, "thread_rate_timelimit", edittimelimit);
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message,true);
				return null;
			}
		}
		}
		String dupkarmarate = settings.get("dupkarmarate");
		if(dupkarmarate!=null && dupkarmarate.equals("0")){
			String retelogsql = "SELECT pid FROM jrun_ratelog WHERE uid="+ jrun_uid+ " AND pid="+pid;
			List<Map<String, String>> retelist = dataBaseService.executeQuery(retelogsql);
			if(retelist!=null && retelist.size()>0){
				retelist = null;
				message = getMessage(request, "thread_rate_duplicate");
				if(inajax==null){
					request.setAttribute("errorInfo", message);
					return mapping.findForward("showMessage");
				}else{
					Common.writeMessage(response, message, true);
					return null;
				}
			}
		}
		String settind = settings.get("modratelimit");
		if (usergroup.get("raterange").equals("")) {
			message = getMessage(request, "group_nopermission");
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message,true);
				return null;
			}
		} else if (jsprun_adminid == 3 && !modertar && settind.equals("1")) {
			message =getMessage(request, "thread_rate_moderator_invalid");
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message, true);
				return null;
			}
		}
		int times = timestamp-86400;
		String retelogsql = "SELECT extcredits, SUM(ABS(score)) AS todayrate FROM jrun_ratelog WHERE uid='"+ jrun_uid + "' AND dateline>="+ times+" GROUP BY extcredits";
		List<Map<String, String>> retelist = dataBaseService.executeQuery(retelogsql);
		String raterange = usergroup.get("raterange");
		Map rangresult = new TreeMap();
		if (!raterange.equals("")) {
			String[] reaterang = raterange.split("\\s+");
			List ranglist = null;
			String key = "";
			for (int i = 0, j = 0; i < reaterang.length; i++) {
				if ((i + 4) % 4 == 0) {
					key = reaterang[i];
					ranglist = new ArrayList();
				} else {
					j++;
					if (j % 3 == 0) {
						if (retelist != null) {
							for (Map<String, String> retemap : retelist) {
								if (retemap.get("extcredits").equals(key)) {
									reaterang[i] = (convertInt(reaterang[i])- convertInt(retemap.get("todayrate")) + "");
								}
							}
						}
					}
					ranglist.add(reaterang[i]);
				}
				if (j % 3 == 0) {
					rangresult.put(key, ranglist);
				}
			}
		}
		retelist = null;
		String setting = settings.get("extcredits");
		Map<String,String[]> extnameMap = getCreditsName(setting);
		request.setAttribute("extnameMap", extnameMap);
		Map<Integer,Map<String,String>> extmap = dataParse.characterParse(setting, true);
		try{
			if(submitCheck(request, "ratesubmit")){
				String reason = request.getParameter("reason");
				reason = Common.cutstr(reason, 40, "");
				if(usergroup.get("reasonpm").equals("1")||usergroup.get("reasonpm").equals("3")){
					if(reason==null || reason.equals("")){
						message = getMessage(request, "admin_reason_invalid");
						if(inajax==null){
							request.setAttribute("errorInfo", message);
							return mapping.findForward("showMessage");
						}else{
							Common.writeMessage(response,message,true);
							return null;
						}
					}
				}
				Iterator<Entry<String,List>> it = rangresult.entrySet().iterator();
				double rate = post.getRate();
				double ratetime = post.getRatetimes();
				boolean flag = false;
				boolean isone = false;
				String ratelogs = "";
				Map<String,String> creditsarray = new TreeMap<String,String>();
				while (it.hasNext()) {
					Entry<String,List> temp = it.next();
					String key = temp.getKey();
					List values = temp.getValue();
					String scores = request.getParameter("score" + key);
					if (scores != null) {
						flag = true;
						int score = convertInt(scores);
						int max = convertInt(values.get(1).toString());
						int min = convertInt(values.get(0).toString());
						if (Math.abs(score) <= convertInt(values.get(2).toString())) {
							if(score==0 && !it.hasNext() && !isone){
								message = getMessage(request, "thread_rate_range_invalid");
								if(inajax==null){
									request.setAttribute("errorInfo", message);
									return mapping.findForward("showMessage");
								}else{
									Common.writeMessage(response,message,true);
									return null;
								}
							}
							if (score > max || score < min ) {
								message = getMessage(request, "thread_rate_range_invalid");
								if(inajax==null){
									request.setAttribute("errorInfo", message);
									return mapping.findForward("showMessage");
								}else{
									Common.writeMessage(response,message,true);
									return null;
								}
							} else {
								if(score!=0){
									isone = true;
									rate += score;
									double ratetimes = Math.ceil(Math.max(Math.abs(min), Math.abs(max)) / (double)5);
									if(ratetimes<=0){
										ratetimes = 1;
									}
									ratetime += ratetimes;
									creditsarray.put(key.toString(), score+"");
								}
							}
						} else {
							message = getMessage(request, "thread_rate_ctrl");
							if(inajax==null){
								request.setAttribute("errorInfo", message);
								return mapping.findForward("showMessage");
							}else{
								Common.writeMessage(response,message,true);
								return null;
							}
						}
						values = null;
					}
				}
				if (!flag) {
					message = getMessage(request, "thread_rate_range_invalid");
					if(inajax==null){
						request.setAttribute("errorInfo", message);
						return mapping.findForward("showMessage");
					}else{
						Common.writeMessage(response,message,true);
						return null;
					}
				}
				Set<String> crearrayKey = creditsarray.keySet();
				String[]creditsname = extnameMap.get("name");
				String[]creditsunit = extnameMap.get("unit");
				for(String key:crearrayKey){
					int score = Common.intval(creditsarray.get(key));
					rateCredits(post.getAuthorid(), score, Common.intval(key));
					String insertratelogsql = "insert into jrun_ratelog(pid,uid,username,extcredits,dateline,score,reason)values('"	+ pid + "','" + jrun_uid + "','" + member.getUsername() + "','" + key + "','" + timestamp + "','" + score + "','" + Common.addslashes(reason) + "')";
					dataBaseService.runQuery(insertratelogsql);
					String raterate = score+"";
					if(score>0){
						raterate = "+"+raterate;
					}
					String name = creditsname[Integer.valueOf(key)-1]==null?"":creditsname[Integer.valueOf(key)-1];
					String unit = creditsunit[Integer.valueOf(key)-1]==null?"":creditsunit[Integer.valueOf(key)-1];
					ratelogs = ratelogs +", "+ name+raterate+unit;
					String ratelog = timestamp+"\t"+member.getUsername()+"\t"+member.getGroupid()+"\t"+post.getAuthor()+"\t"+key+"\t"+raterate+"\t"+post.getTid()+"\t"+threads.get("subject")+"\t"+reason.trim();
					Log.writelog("ratelog", timestamp, ratelog);
				}
				String sendreasonpm = request.getParameter("sendreasonpm");
				if (sendreasonpm != null || usergroup.get("reasonpm").equals("2") || usergroup.get("reasonpm").equals("3")) {
					List<Map<String,String>> usergroupslist = dataBaseService.executeQuery("select maxpmnum from jrun_usergroups as u left join jrun_members as m on m.groupid=u.groupid where m.uid="+post.getAuthorid());
					if((usergroupslist!=null && usergroupslist.size()>0 && Integer.valueOf(usergroupslist.get(0).get("maxpmnum"))>0)||modertar){
						usergroupslist = null;
						List<Map<String,String>> forums = dataBaseService.executeQuery("select name from jrun_forums where fid="+threads.get("fid"));
						String buffermessage = getMessage(request, "rate_reason_message", boardurl,jrun_uid+"",member.getUsername(),Common.cutstr(post.getMessage(),2000),Common.gmdate(dateformat+" "+timeformat, post.getDateline(),timeoffset),threads.get("fid"),forums.get(0).get("name"),tid+"",page,post.getPid()+"",threads.get("subject"),ratelogs.substring(1),reason);
						Common.sendpm(post.getAuthorid()+"", getMessage(request, "rate_reason_subject"), buffermessage, member.getUid()+"", member.getUsername(), timestamp);
						dataBaseService.runQuery("update jrun_members as m set newpm='1' where m.uid="+post.getAuthorid());
					}
				}
				if(ratetime<=0){
					ratetime = 1;
				}else if(ratetime>255){
					ratetime = 255;
				}
				String updatepost = "update jrun_posts set rate = " + rate+ ",ratetimes = " + ratetime + " where pid=" + pid;
				dataBaseService.runQuery(updatepost);
				if (post.getFirst() == 1) {
					String ratedd = "0";
					if(rate>0){
						ratedd = "1";
					}else if(rate<0){
						ratedd = "-1";
					}
					String updatethread = "update jrun_threads set rate=" + ratedd+ " where tid=" + tid;
					dataBaseService.runQuery(updatethread);
				}
				if(inajax==null){
					request.setAttribute("successInfo", getMessage(request, "ajax_thread_rate_succeed"));
					request.setAttribute("requestPath", "viewthread.jsp?tid="+tid+"&page="+page);
					return mapping.findForward("showMessage");
				}else{
					Common.writeMessage(response,getMessage(request, "ajax_thread_rate_succeed"),false);
					return null;
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		Iterator it = rangresult.keySet().iterator();
		Map regMap = new TreeMap();
		while (it.hasNext()) {
			Object keys = it.next();
			if(extmap.get(convertInt(keys.toString()))!=null){
				List values = (List) rangresult.get(keys);
				List options = new ArrayList();
				int offset = (int) Math.abs(Math.ceil(((convertInt(values.get(1).toString()) - convertInt(values.get(0).toString())) / 32)));
				if(offset==0){
					offset = 1;
				}
				for (int p = convertInt(values.get(0).toString()); p <= convertInt(values.get(1).toString()); p += offset) {
					if(p!=0){
						options.add(p);
					}
				}
				values.remove(0);
				values.remove(0);
				values.add(options);
				regMap.put(keys, values);
			}
		}
		request.setAttribute("rangresult", regMap);
		if(usergroup.get("reasonpm").equals("1")||usergroup.get("reasonpm").equals("3")){
			request.setAttribute("reasons", getMessage(request, "admin_reason_required"));
		}
		return mapping.findForward("torate");
	}
	@SuppressWarnings("unused")
	private static int convertInt(String s) {
		int count = 0;
		try {
			count = Integer.valueOf(s);
		} catch (Exception e) {
		}
		return count;
	}
	@SuppressWarnings("unchecked")
	private Map getCreditsName(String extcredits) {
		Map<Integer,Map<String,String>> extmap = dataParse.characterParse(extcredits, true);
		Map<String, String[]> resultMap = new HashMap<String, String[]>();
		Set<Integer> creditstSet = extmap.keySet();
		String extname[] = new String[8];
		String extunit[] = new String[8];
		for(Integer key : creditstSet){
			Map<String,String> excreditmap = extmap.get(key);
			extname[key-1] = excreditmap.get("title");
			extunit[key-1] = excreditmap.get("unit");
		}
		resultMap.put("name", extname);
		resultMap.put("unit", extunit);
		return resultMap;
	}
	private void rateCredits(int uid, int score, int extcredits) {
		dataBaseService.runQuery("update jrun_members set extcredits"+extcredits+"=extcredits"+extcredits+"+"+score+" where uid="+uid);
	}
	private Object getValues(Object bean, String fieldName) {
		Object paraValue = null;
		try {
			String getMethod = "get" + fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1, fieldName.length());
			Method method = bean.getClass().getMethod(getMethod);
			paraValue = method.invoke(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paraValue;
	}
	public ActionForward blog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer)(session.getAttribute("jsprun_uid")==null?0:session.getAttribute("jsprun_uid"));
		int tid = Common.toDigit(request.getParameter("tid"));
		Members members = (Members)session.getAttribute("user");
		String inajax = request.getParameter("inajax");
		String message = "";
		Threads thread = threadService.findByTid(tid);
		if(jsprun_uid==0){
			message = getMessage(request, "not_loggedin");
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message,true);
				return null;
			}
		}
		String formHashValue = request.getParameter("formHash");
		if(!Common.formHash(request).equals(formHashValue)){
			if(inajax==null){
				request.setAttribute("resultInfo", getMessage(request, "submit_invalid"));
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, getMessage(request, "submit_invalid"),true);
				return null;
			}
		}
		short jsprun_groupid = (Short) session.getAttribute("jsprun_groupid");
		Usergroups usergroups = userGroupService.findUserGroupById(jsprun_groupid);
		Forums froms = forumService.findById(thread.getFid());
		if (jsprun_uid == 0 || (thread.getBlog() == 0 && (usergroups.getAllowuseblog() == 0 || froms.getAllowshare() == 0))) {
			message = getMessage(request, "group_nopermission", usergroups.getGrouptitle());
			if(inajax==null){
				request.setAttribute("errorInfo", message);
				return mapping.findForward("showMessage");
			}else{
				Common.writeMessage(response, message,true);
				return null;
			}
		}
		if (thread.getAuthorid() != jsprun_uid) {
			boolean modertar = Common.ismoderator(thread.getFid(), members);
			if (!modertar) {
				message = getMessage(request, "blog_add_illegal");
				if(inajax==null){
					request.setAttribute("errorInfo", message);
					return mapping.findForward("showMessage");
				}else{
					Common.writeMessage(response, message,true);
					return null;
				}
			}
		}
		int blog = thread.getBlog() == 0 ? 1 : 0;
		String updateblog = "update jrun_threads set blog=" + blog+ " where tid=" + tid;
		dataBaseService.runQuery(updateblog);
		message = getMessage(request, "blog_add_succeed");
		if(inajax==null){
			request.setAttribute("successInfo", message);
			request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
			return mapping.findForward("showMessage");
		}else{
			printmessage(response, message);
			return null;
		}
	}
	public ActionForward viewthreadmod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int tid = Common.toDigit(request.getParameter("tid"));
		List<Map<String,String>> threadss = dataBaseService.executeQuery("select t.tid,t.subject,f.fid,f.name,f.type,f.fup from jrun_threads as t left join jrun_forums as f on t.fid=f.fid where t.tid="+tid);
		if(threadss==null || threadss.size()<=0){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		Map<String,String> threads = threadss.get(0);threadss=null;
		String navigation = "<a href=\"forumdisplay.jsp?fid="+threads.get("fid")+"\">"+threads.get("name")+"</a> &raquo; <a href=\"viewthread.jsp?tid="+threads.get("tid")+"\">"+threads.get("subject")+"</a>";
		if(threads.get("type").equals("sub")){
			Forums parentforum = forumService.findById(Short.parseShort((threads.get("fup"))));
			navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.getFid()+"\">"+parentforum.getName()+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+threads.get("fid")+"\">"+threads.get("name")+"</a> &raquo; <a href=\"viewthread.jsp?tid="+threads.get("tid")+"\">"+threads.get("subject")+"</a>";
		}
		request.setAttribute("navigation", navigation);
		threads = null;
		List<Map<String,String>> modlist = dataBaseService.executeQuery("select * from jrun_threadsmod where tid='"+tid+"'");
		if (modlist == null||modlist.size()<=0) {
			request.setAttribute("errorInfo", getMessage(request, "threadmod_nonexistence"));
			return mapping.findForward("showMessage");
		} else {
			for(Map<String,String> mods:modlist){
				String actionname = getMessage(request,mods.get("action"));
				mods.put("action", actionname);
			}
			request.setAttribute("modlist", modlist);
		}
		return mapping.findForward("tomod");
	}
	@SuppressWarnings("unchecked")
	public ActionForward viewratings(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int tid = Common.toDigit(request.getParameter("tid"));
		int pid = Common.toDigit(request.getParameter("pid"));
		String ratelogsql = "SELECT * FROM jrun_ratelog WHERE pid='" + pid + "' ORDER BY dateline";
		List<Map<String, String>> rateloglist = dataBaseService.executeQuery(ratelogsql);
		Posts post = postService.getPostsById(pid);
		List<Map<String,String>> threadslist = dataBaseService.executeQuery("select t.tid,t.subject,t.price,f.fid,f.name,f.type,f.fup from jrun_threads as t left join jrun_forums as f on t.fid=f.fid where t.tid='"+tid+"'");
		if (post == null || rateloglist == null || rateloglist.size() <= 0) {
			request.setAttribute("errorInfo", getMessage(request, "thread_rate_log_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(threadslist==null || threadslist.size()<=0){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		Map<String,String>threads = threadslist.get(0);threadslist=null;
		String navigation = "<a href=\"forumdisplay.jsp?fid="+threads.get("fid")+"\">"+threads.get("name")+"</a> &raquo; <a href=\"viewthread.jsp?tid="+threads.get("tid")+"\">"+threads.get("subject")+"</a>";
		if(threads.get("type").equals("sub")){
			Forums parentforum = forumService.findById(Short.valueOf(threads.get("fup")));
			navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.getFid()+"\">"+parentforum.getName()+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+threads.get("fid")+"\">"+threads.get("name")+"</a> &raquo; <a href=\"viewthread.jsp?tid="+threads.get("tid")+"\">"+threads.get("subject")+"</a>";
		}
		request.setAttribute("navigation", navigation);
		Members member = memberService.findMemberById(post.getAuthorid());
		if(member==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		String banedsett = ForumInit.settings.get("bannedmessages");
		String banmessage = banedsett == null ? "" : banedsett;
		if (banmessage.equals("0")|| (banmessage.equals("1") && (member.getGroupid() != 4 && member.getGroupid() != 5))) {
			@SuppressWarnings("unused")
			String message = post.getMessage();
		} else {
		}
		String extcreits = ForumInit.settings.get("extcredits");
		Map<String, String[]> creditnameMap = getCreditsName(extcreits);
		List resultlist = new ArrayList();
		String[] creditsnames = creditnameMap.get("name");
		String[] creditsunits = creditnameMap.get("unit");
		for (int i = 0; i < rateloglist.size(); i++) {
			Map<String, String> ratelogmap = rateloglist.get(i);
			RatelogVO ratevo = new RatelogVO();
			ratevo.setFirstUsername(ratelogmap.get("username"));
			String creditname = creditsnames[convertInt(ratelogmap.get("extcredits")) - 1];
			String creditunit = creditsunits[convertInt(ratelogmap.get("extcredits")) - 1];
			String score = ratelogmap.get("score");
			if (score.indexOf("-") == -1) {
				score = "+" + score;
			}
			creditunit = creditunit==null?"":creditunit;
			creditname = creditname==null?"":creditname;
			ratevo.setMarkValue(creditname + score + creditunit);
			ratevo.setReason(ratelogmap.get("reason"));
			ratevo.setUid(Common.toDigit(ratelogmap.get("uid")));
			ratevo.setOperateTime(ratelogmap.get("dateline"));
			resultlist.add(ratevo);
		}
		request.setAttribute("thread", threads);
		request.setAttribute("post", post);
		request.setAttribute("rateloglist", resultlist);
		return mapping.findForward("rate_view");
	}
	@SuppressWarnings("unchecked")
	public ActionForward removerate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings=ForumInit.settings;
		String reasons = settings.get("modreasons");
		if(reasons!=null && !reasons.equals("")){
			List<String> reasonlist = new ArrayList<String>();
			String rea[] = reasons.split("\n");
			for(int i=0;i<rea.length;i++){
				reasonlist.add(rea[i]);
			}
			OtherBaseVO othervo = new OtherBaseVO();
			othervo.setReasonList(reasonlist);
			request.setAttribute("valueObject", othervo);
		}
		int tid = Common.toDigit(request.getParameter("tid"));
		int pid = Common.toDigit(request.getParameter("pid"));
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		Posts post = postService.getPostsById(pid);
		Threads thread = threadService.findByTid(tid);
		HttpSession session = request.getSession();
		String dateformat = settings.get("dateformat");
		String timeformat = settings.get("gtimeformat");
		String timeoffset = settings.get("timeoffset");
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		if(jsprun_uid==0){
			request.setAttribute("errorInfo", getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}
		Members member = (Members)session.getAttribute("user");
		boolean modertar = Common.ismoderator(post.getFid(), member);
		Usergroups usergroup = userGroupService.findUserGroupById(member.getGroupid());
		request.setAttribute("post", post);
		if (usergroup.getRaterange().equals("")) {
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		if(!modertar){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		Forums forums = forumService.findById(post.getFid());
		String navigation = "<a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo; <a href=\"viewthread.jsp?tid="+thread.getTid()+"\">"+thread.getSubject()+"</a>";
		if(forums.getType().equals("sub")){
			Forums parentforum = forumService.findById(forums.getFup());
			navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.getFid()+"\">"+parentforum.getName()+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo <a href=\"viewthread.jsp?tid="+thread.getTid()+"\">"+thread.getSubject()+"</a>";
		}
		request.setAttribute("navigation", navigation);
		String ratelogsql = "SELECT * FROM jrun_ratelog WHERE pid='" + pid+ "' ORDER BY dateline";
		List<Map<String, String>> rateloglist = dataBaseService.executeQuery(ratelogsql);
		Map<String, String[]> creditnameMap = getCreditsName(settings.get("extcredits"));
		String[] creditnames = creditnameMap.get("name");
		String[] creditunits = creditnameMap.get("unit");
		try{
			if(submitCheck(request, "ratesubmit")){
				String page = request.getParameter("page");
				String boardurl = (String) session.getAttribute("boardurl");
				String[] logidarray = request.getParameterValues("logidarray[]");
				String reason = request.getParameter("reason");
				if(usergroup.getReasonpm()==1||usergroup.getReasonpm()==3){
					if(reason==null || reason.equals("")){
						request.setAttribute("errorInfo", getMessage(request, "admin_reason_invalid"));
						return mapping.findForward("showMessage");
					}
				}
				int rate = post.getRate();
				String ratelogs = "";
				if (logidarray != null) {
					for (int i = 0; i < rateloglist.size(); i++) {
						Map<String, String> ratelogmap = rateloglist.get(i);
						for (int j = 0; j < logidarray.length; j++) {
							String[] logids = logidarray[j].split("\\s+");
							if (ratelogmap.get("uid").equals(logids[0])&& ratelogmap.get("extcredits").equals(logids[1])&& ratelogmap.get("dateline").equals(logids[2])) {
								dataBaseService.runQuery("update jrun_members set extcredits"+logids[1]+"=extcredits"+logids[1]+"-"+Common.range(Common.intval(ratelogmap.get("score")), 10000,-10000)+" where uid="+post.getAuthorid());
								rate = rate - Common.range(Common.intval(ratelogmap.get("score")), 10000, -10000);
								int rates = 0;
								rates = rates - Common.range(Common.intval(ratelogmap.get("score")), 10000,-10000);
								String reatenotes = rates+"";
								if(rates>0){
									reatenotes = "+"+rates;
								}
								String name = creditnames[Integer.valueOf(logids[1])-1]==null?"":creditnames[Integer.valueOf(logids[1])-1];
								String unit = creditunits[Integer.valueOf(logids[1])-1]==null?"":creditunits[Integer.valueOf(logids[1])-1];
								String deleteratelog = "delete from jrun_ratelog where pid=" + pid + " and uid=" + logids[0] + " and extcredits=" + logids[1] + " and dateline=" + logids[2];
								dataBaseService.runQuery(deleteratelog);
								ratelogs = ratelogs+","+name+reatenotes+unit;
								String ratelog = timestamp+"\t"+member.getUsername()+"\t"+member.getGroupid()+"\t"+post.getAuthor()+"\t"+logids[1]+"\t"+reatenotes+"\t"+post.getTid()+"\t"+thread.getSubject()+"\t"+reason.trim()+"\t"+"D";
								Log.writelog("ratelog", timestamp, ratelog);
							}
						}
					}
				}
				String updatepost = "update jrun_posts set rate = " + rate+ " where pid=" + pid;
				dataBaseService.runQuery(updatepost);
				if (post.getFirst() == 1) {
					String ratedd = "0";
					if(rate>0){
						ratedd = "1";
					}else if(rate<0){
						ratedd = "-1";
					}
					String updatethread = "update jrun_threads set rate=" + ratedd+ " where tid=" + tid;
					dataBaseService.runQuery(updatethread);
				}
				String sendreasonpm = request.getParameter("sendreasonpm");
				if (sendreasonpm != null || usergroup.getReasonpm()==2 || usergroup.getReasonpm()==3) {
					List<Pms> pmslist = new ArrayList<Pms>();
					List<Map<String,String>> usergroupslist = dataBaseService.executeQuery("select maxpmnum from jrun_usergroups as u left join jrun_members as m on m.groupid=u.groupid where m.uid="+post.getAuthorid());
					if((usergroupslist!=null && usergroupslist.size()>0 && Integer.valueOf(usergroupslist.get(0).get("maxpmnum"))>0)||modertar){
					Pms pms = new Pms();
					pms.setDateline(timestamp);
					pms.setDelstatus(Byte.valueOf("0"));
					pms.setFolder("inbox");
					String pmsmessage = getMessage(request, "rate_removereason_message", boardurl,jsprun_uid+"",member.getUsername(),Common.cutstr(post.getMessage(),2000),Common.gmdate(dateformat+" "+timeformat, post.getDateline(),timeoffset),forums.getFid()+"",forums.getName(),tid+"",page,post.getPid()+"",thread.getSubject(),(ratelogs.length()>0?ratelogs.substring(1):""),reason);
					pms.setMessage(pmsmessage);
					pms.setMsgfrom(member.getUsername());
					pms.setMsgfromid(member.getUid());
					pms.setMsgtoid(post.getAuthorid());
					pms.setNew_(Byte.valueOf("1"));
					pms.setSubject(getMessage(request, "rate_removereason_subject"));
					pmslist.add(pms);
					dataBaseService.runQuery("update jrun_members as m set newpm='1' where m.uid="+post.getAuthorid());
					}
					pmsServer.insertPmsList(pmslist);
				}
				request.setAttribute("successInfo", getMessage(request, "thread_rate_removesucceed"));
				request.setAttribute("requestPath", "viewthread.jsp?tid=" + tid+"&page="+page);
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		List resultlist = new ArrayList();
		for (int i = 0; i < rateloglist.size(); i++) {
			Map<String, String> ratelogmap = rateloglist.get(i);
			RatelogVO ratevo = new RatelogVO();
			ratevo.setFirstUsername(ratelogmap.get("username"));
			String creditname = creditnames[convertInt(ratelogmap.get("extcredits")) - 1];
			String creditunit = creditunits[convertInt(ratelogmap.get("extcredits")) - 1];
			String score = ratelogmap.get("score");
			if (score.indexOf("-") == -1) {
				score = "+" + score;
			}
			creditunit = creditunit==null?"":creditunit;
			creditname = creditname==null?"":creditname;
			ratevo.setMarkValue(creditname + score + creditunit);
			ratevo.setReason(ratelogmap.get("reason"));
			ratevo.setOperateTime(ratelogmap.get("dateline"));
			ratevo.setUid(convertInt(ratelogmap.get("uid")));
			ratevo.setExtcredits(ratelogmap.get("extcredits"));
			resultlist.add(ratevo);
		}
		request.setAttribute("rateloglist", resultlist);
		request.setAttribute("thread", thread);
		request.setAttribute("post", post);
		if(usergroup.getReasonpm()==1||usergroup.getReasonpm()==3){
			request.setAttribute("reasons", getMessage(request, "admin_reason_required"));
		}
		String page = request.getParameter("page");
		request.setAttribute("page",page);
		return mapping.findForward("torate");
	}
	@SuppressWarnings("unchecked")
	public ActionForward viewvote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int tid = Common.toDigit(request.getParameter("tid"));
		HttpSession session = request.getSession();
		int uid = (Integer)session.getAttribute("jsprun_uid");
		String modadd2 = "";String modadd1 = "";
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		byte adminid = (Byte)session.getAttribute("jsprun_adminid");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members members = (Members)session.getAttribute("user");
		int readaccess = Common.toDigit(usergroups.get("readaccess"));
		if(members!=null&&members.getAdminid()==3){
			modadd1 = ", m.uid AS ismoderator ";
			modadd2 = " LEFT JOIN jrun_moderators m ON m.uid="+uid+" AND m.fid=ff.fid ";
		}
		List<Map<String,String>> threadlist = dataBaseService.executeQuery("SELECT t.readperm, t.authorid, ff.viewperm, a.allowview,ff.password,ff.fid"+modadd1+" FROM  jrun_threads t LEFT JOIN jrun_forumfields ff ON t.fid=ff.fid LEFT JOIN jrun_access a ON a.uid="+ uid+ " AND a.fid=t.fid "+modadd2+"WHERE t.tid = "+tid);
		String ajaxin = request.getParameter("inajax");
		if (threadlist == null || threadlist.size()<=0) {
			if(ajaxin!=null){
				Common.writeMessage(response,getMessage(request, "thread_nonexistence"),true);
				return null;
			}else{
				request.setAttribute("errorInfo", getMessage(request, "thread_nonexistence"));
				return mapping.findForward("showMessage");
			}
		}
		Map<String,String> forum = threadlist.get(0);
		threadlist=null;
		String extgroupids=members!=null?members.getExtgroupids():null;
		if (Common.empty(forum.get("allowview"))) {
			if (Common.empty(forum.get("viewperm")) && readaccess==0) {
				if(ajaxin!=null){
					Common.writeMessage(response,getMessage(request, "group_nopermission", usergroups.get("grouptitle")),true);
					return null;
				}else{
					request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
					return mapping.findForward("nopermission");
				}
			} else if (!Common.empty(forum.get("viewperm"))&& !Common.forumperm(forum.get("viewperm"), groupid,extgroupids)) {
				if(ajaxin!=null){
					Common.writeMessage(response,getMessage(request, "forum_nopermission_2"),true);
					return null;
				}else{
					request.setAttribute("show_message", getMessage(request, "forum_nopermission_2"));
					return mapping.findForward("nopermission");
				}
			}
		}
		int threadreadperm = Common.toDigit(forum.get("readperm"));
		boolean modertar = Common.ismoderator(forum.get("ismoderator"), members);
		if(threadreadperm>0&&threadreadperm>readaccess&&!modertar&&!forum.get("authorid").equals(uid+"")){
			if(ajaxin!=null){
				Common.writeMessage(response,getMessage(request, "thread_nopermission",threadreadperm+""),true);
				return null;
			}else{
				request.setAttribute("show_message", getMessage(request, "thread_nopermission",threadreadperm+""));
				return mapping.findForward("nopermission");
			}
		}
		String password=forum.get("password");
		if(password!=null&&!"".equals(password))
		{
			 if(!password.equals(session.getAttribute("fidpw"+forum.get("fid")))){
				request.setAttribute("fid", Short.valueOf(forum.get("fid")));
				return mapping.findForward("toForumdisplay_passwd");
			}
		}
		int voterpp = 180;
		int page = Math.max(Common.intval(request.getParameter("page")), 1);
		String optionid = request.getParameter("polloptionid");
		String []arrvoterids = null;
		int start_limit = 0;
		if(Common.empty(optionid)) {
			StringBuffer voterids = new StringBuffer();
			List<Map<String,String>> polloptionlist = dataBaseService.executeQuery("SELECT voterids FROM jrun_polloptions WHERE tid='"+tid+"'");
			for(Map<String,String> polloption : polloptionlist){
				voterids.append((Common.empty(voterids)?"\t":"")+polloption.get("voterids").trim());
			}
			arrvoterids = voterids.toString().trim().split("\t");
			int num = arrvoterids.length;
			Map<String,Integer> multiInfo=Common.getMultiInfo(num, voterpp, page);
			start_limit = multiInfo.get("start_limit");
			String ajaxtarget=request.getParameter("ajaxtarget");
			Map<String,Object> multi=Common.multi(num, voterpp, page, "misc.jsp?action=viewvote&tid="+tid, 0, 10, true, false, ajaxtarget);
			request.setAttribute("multi", multi);
		} else if(adminid == 1) {
			String voterids = "";
			List<Map<String,String>> polloptionlist = dataBaseService.executeQuery("SELECT voterids FROM jrun_polloptions WHERE polloptionid='"+optionid+"'");
			voterids =polloptionlist.get(0).get("voterids");
			arrvoterids = voterids.trim().split("\t");
			int num = arrvoterids.length;
			Map<String,Integer> multiInfo=Common.getMultiInfo(num, voterpp, page);
			start_limit = multiInfo.get("start_limit");
			String ajaxtarget=request.getParameter("ajaxtarget");
			Map<String,Object> multi=Common.multi(num, voterpp, page, "misc.jsp?action=viewvote&tid="+tid+"&polloptionid="+optionid, 0, 10, true, false, ajaxtarget);
			request.setAttribute("multi", multi);
		}
		int size = arrvoterids.length;
		int temp = voterpp+start_limit;
		int length  = size>voterpp?(size-temp>=0?voterpp:size-start_limit):size;
		String []temp_arrvoterids = new String[length];
		for(int i=0,j=start_limit;i<voterpp&&j<size;i++,j++){
			temp_arrvoterids[i] = arrvoterids[j];
		}
		String voterids = Common.implode(temp_arrvoterids, ",");
		List<Map<String,String>> userlist = dataBaseService.executeQuery("SELECT uid, username FROM jrun_members WHERE uid IN ("+voterids+")");
		request.setAttribute("memberslist", userlist.size()>0?userlist:null);
		return mapping.findForward("viewthread_poll_voters");
	}
	@SuppressWarnings("unchecked")
	public ActionForward votepoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int tid = Common.toDigit(request.getParameter("tid"));
		String optionid[] = request.getParameterValues("pollanswers[]");
		int jsprun_uid = (Integer)(session.getAttribute("jsprun_uid")==null?0:session.getAttribute("jsprun_uid"));
		String voterids = jsprun_uid>0?jsprun_uid+"":Common.get_onlineip(request);
		short jsprun_groupid = (Short)session.getAttribute("jsprun_groupid");
		Usergroups usergroup = userGroupService.findUserGroupById(jsprun_groupid);
		if(usergroup==null || usergroup.getAllowvote()==0){
			request.setAttribute("errorInfo", getMessage(request, "group_nopermission", usergroup.getGrouptitle()));
			return mapping.findForward("showMessage");
		}
		Threads thread = threadService.findByTid(tid);
		if(thread.getClosed()==-1){
			request.setAttribute("errorInfo", getMessage(request, "thread_poll_closed"));
			return mapping.findForward("showMessage");
		}
		if(optionid==null){
			request.setAttribute("errorInfo", getMessage(request, "thread_poll_invalid"));
			return mapping.findForward("showMessage");
		}
		Polls polls = pollService.findPollsBytid(tid);
		if(polls==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		if(polls.getExpiration()!=0 && polls.getExpiration()<timestamp ){
			request.setAttribute("errorInfo", getMessage(request, "poll_overdue"));
			return mapping.findForward("showMessage");
		}
		if(polls.getMaxchoices()<optionid.length){
			request.setAttribute("errorInfo", getMessage(request, "poll_choose_most", polls.getMaxchoices()+""));
			return mapping.findForward("showMessage");
		}
		try{
			if(submitCheck(request, "pollsubmit")){
				List<Polloptions> optionlist = optionService.findPolloptionsBytid(tid);
				for (Polloptions option : optionlist) {
					String uid = option.getVoterids();
					if (!uid.equals("")) {
						String[] uidsall = uid.split("\\s+");
						for (int i = 0; i < uidsall.length; i++) {
							if(voterids.equals(uidsall[i])){
								request.setAttribute("errorInfo", getMessage(request, "thread_poll_voted"));
								return mapping.findForward("showMessage");
							}
						}
					}
				}
				StringBuffer optionids = new StringBuffer();
				for(int i=0;i<optionid.length;i++){
					optionids.append(","+optionid[i]);
				}
				if(optionids.length()>0){
					dataBaseService.runQuery("update jrun_polloptions set votes=votes+1,voterids=CONCAT(voterids,'"+voterids+"\t') where polloptionid in ( "+optionids.substring(1)+" )",true);
				}
				dataBaseService.runQuery("update jrun_threads set lastpost="+timestamp+" where tid="+tid);
				dataBaseService.runQuery("replace into jrun_myposts(uid,tid,dateline,special) values ('"+jsprun_uid+"','"+tid+"','"+timestamp+"','1')");
				if(jsprun_uid>0){
					Map<String,String> settings = ForumInit.settings;
					Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
					Map<Integer, Integer> postcredits=(Map<Integer,Integer>)creditspolicys.get("votepoll");
						Common.updatepostcredits("+", jsprun_uid,1, postcredits);
						Common.updatepostcredits(jsprun_uid, settings.get("creditsformula"));
				}
				if(Common.isshowsuccess(session, "thread_poll_succeed")){
					Common.requestforward(response, "viewthread.jsp?tid="+tid);
					return null;
				}else{
					request.setAttribute("successInfo", getMessage(request, "thread_poll_succeed"));
					request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
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
	public ActionForward activityapplies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int tid = Common.toDigit(request.getParameter("tid"));
		int jsprun_uid = (Integer)(session.getAttribute("jsprun_uid")==null?0:session.getAttribute("jsprun_uid"));
		if(jsprun_uid==0){
			request.setAttribute("errorInfo", getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}
		try{
			if(submitCheck(request, "activitysubmit")){
				String jsprun_user = (String)session.getAttribute("jsprun_userss");
				Threads thread = threadService.findByTid(tid);
				Forums forum = forumService.findById(thread.getFid());
				if(jsprun_uid==0 || forum.getStatus()==2){
					request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
					return mapping.findForward("showMessage");
				}
				String activitsql = "select * from jrun_activities as a where a.tid="+ tid;
				List<Map<String, String>> activitslist = dataBaseService.executeQuery(activitsql);
				Map<String,String> activit = activitslist.get(0);
				String expiration = activit.get("expiration");
				if(!expiration.equals("0") && convertInt(expiration)<timestamp){
					request.setAttribute("errorInfo", getMessage(request, "activity_stop"));
					return mapping.findForward("showMessage");
				}
				String acppql = "SELECT applyid FROM jrun_activityapplies WHERE tid='"+tid+"' and uid="+jsprun_uid;
				List<Map<String, String>> activitsapplist = dataBaseService.executeQuery(acppql);
				if(activitsapplist!=null && activitsapplist.size()>0){
					request.setAttribute("successInfo", getMessage(request, "activity_repeat_apply"));
					request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
					return mapping.findForward("showMessage");
				}
				String payment = request.getParameter("payment");
				String payvalue = request.getParameter("payvalue");
				String contact = request.getParameter("contact");
				String message = request.getParameter("message");
				message = message==null?"":message;
				int paymentvalue = payment.equals("0")?-1:convertInt(payvalue);
				dataBaseService.runQuery("INSERT INTO jrun_activityapplies (tid, username, uid, message, verified, dateline, payment, contact)	VALUES ('"+tid+"', '"+Common.addslashes(jsprun_user)+"', '"+jsprun_uid+"', '"+Common.addslashes(message)+"', '0', '"+timestamp+"', '"+paymentvalue+"', '"+Common.addslashes(contact)+"')");
				request.setAttribute("successInfo", getMessage(request, "activity_completion"));
				request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("resultInfo",getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	public ActionForward activityapplylist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int tid = Common.toDigit(request.getParameter("tid"));
		int jsprun_uid = (Integer)(session.getAttribute("jsprun_uid")==null?0:session.getAttribute("jsprun_uid"));
		String boardurl = (String)session.getAttribute("boardurl");
		Threads thread = threadService.findByTid(tid);
		try{
			if(submitCheck(request, "applylistsubmit")){
				String applyidarray[] = request.getParameterValues("applyidarray[]");
				if(applyidarray==null){
					request.setAttribute("errorInfo", getMessage(request, "activity_choice_applicant"));
					return mapping.findForward("showMessage");
				}else{
					StringBuffer ids = new StringBuffer("0");
					for(int i=0;i<applyidarray.length;i++){
						ids.append(","+applyidarray[i]);
					}
					String operation = request.getParameter("operation");
					List<Pms> pmslist = new ArrayList<Pms>();
					String acppql = "SELECT a.uid,maxpmnum FROM jrun_activityapplies a RIGHT JOIN jrun_members m USING(uid) left join jrun_usergroups as u on m.groupid=u.groupid WHERE a.applyid IN ( "+ids+" )";
					List<Map<String, String>> activitsapplist = dataBaseService.executeQuery(acppql);
					if(operation.equals("delete")){
						dataBaseService.runQuery("delete from jrun_activityapplies where applyid in ( "+ids+")");
						if(activitsapplist!=null && activitsapplist.size()>0){
							for(int i=0;i<activitsapplist.size();i++){
							Map<String,String> apps = activitsapplist.get(i);
								if(!apps.get("maxpmnum").equals("0")){
									Pms pms = new Pms();
									pms.setDateline(timestamp);
									pms.setDelstatus(Byte.valueOf("0"));
									pms.setFolder("inbox");
									pms.setMessage(getMessage(request, "activity_delete_message", thread.getSubject(),boardurl,tid+""));
									pms.setMsgfrom("System Message");
									pms.setMsgfromid(0);
									pms.setMsgtoid(convertInt(apps.get("uid")));
									pms.setNew_(Byte.valueOf("1"));
									pms.setSubject(getMessage(request, "activity_delete_subject"));
									pmslist.add(pms);
									dataBaseService.runQuery("update jrun_members set newpm=1 where uid="+apps.get("uid"));
								}
							}
						}
						pmsServer.insertPmsList(pmslist);
					}else{
						dataBaseService.runQuery("update jrun_activityapplies set verified=1 where applyid in ( "+ids+" )");
						if(activitsapplist!=null && activitsapplist.size()>0){
							for(int i=0;i<activitsapplist.size();i++){
								Map<String,String> apps = activitsapplist.get(i);
								if(!apps.get("maxpmnum").equals("0")){
									Pms pms = new Pms();
									pms.setDateline(timestamp);
									pms.setDelstatus(Byte.valueOf("0"));
									pms.setFolder("inbox");
									pms.setMessage(getMessage(request, "activity_apply_message", thread.getSubject(),boardurl,tid+""));
									pms.setMsgfrom("System Message");
									pms.setMsgfromid(0);
									pms.setMsgtoid(convertInt(apps.get("uid")));
									pms.setNew_(Byte.valueOf("1"));
									pms.setSubject(getMessage(request, "activity_apply_subject"));
									pmslist.add(pms);
									dataBaseService.runQuery("update jrun_members set newpm=1 where uid="+apps.get("uid"));
								}
							}
						}
						pmsServer.insertPmsList(pmslist);
					}
					request.setAttribute("successInfo", getMessage(request, "activity_auditing_completion"));
					request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
					return mapping.findForward("showMessage");
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("thread", thread);
		String activitsql = "select * from jrun_activities as a where a.tid="+ tid;
		List<Map<String, String>> activitslist = dataBaseService.executeQuery(activitsql);
		if(activitslist==null || activitslist.size()<=0 || thread.getSpecial()!=4){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		String sqlverified = thread.getAuthorid()==jsprun_uid ? "" : " and verified=1 ";
		String acppql = "SELECT * FROM jrun_activityapplies WHERE tid="+tid+sqlverified+" ORDER BY dateline DESC";
		List<Map<String, String>> activitsapplist = dataBaseService.executeQuery(acppql);
		if(activitsapplist!=null && activitsapplist.size()>0){
			request.setAttribute("activitsapplist", activitsapplist);
		}else{
			request.setAttribute("activitsapplist", null);
		}
		return mapping.findForward("activity_applylist");
	}
	public ActionForward getonlines(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		List<Map<String, String>> count = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_sessions");
		if(count!=null&&count.size()>0)
		{
			try {
				String num=count.get(0).get("count");
				response.getWriter().write(num!=null?num:"0");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public ActionForward debatevote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String formHashValue = request.getParameter("formHash");
		if(!Common.formHash(request).equals(formHashValue)){
			printmessage(response, getMessage(request, "submit_invalid"));
			return null;
		}
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int tid = Common.toDigit(request.getParameter("tid"));
		int pid = Common.toDigit(request.getParameter("pid"));
		int jsprun_uid = (Integer)(session.getAttribute("jsprun_uid")==null?0:session.getAttribute("jsprun_uid"));
		Threads thread = threadService.findByTid(tid);
		if(thread.getClosed()==-1){
			printmessage(response,getMessage(request, "thread_poll_closed"));
			return null;
		}
		if(jsprun_uid==0){
			printmessage(response, getMessage(request, "debate_poll_nopermission"));
			return null;
		}
		boolean isfirst = pid<=0?true:false;
		String debatesql = "select * from jrun_debates as d where d.tid="+ tid;
		List<Map<String, String>> debatelist = dataBaseService.executeQuery(debatesql);
		if(debatelist==null || debatelist.size()<=0){
			printmessage(response,getMessage(request, "debate_nofound"));
			return null;
		}
		Map<String,String> debate = debatelist.get(0);
		if(isfirst){
			String stand = request.getParameter("stand");
			String affirmvoterids = debate.get("affirmvoterids");
			String negavoterids = debate.get("negavoterids");
			if(stand.equals("1") || stand.equals("2")){
				if(strpos(affirmvoterids,jsprun_uid+"") || strpos(negavoterids,jsprun_uid+"")){
					printmessage(response,getMessage(request, "debate_poll_voted"));
					return null;
				}
			}
			if(stand.equals("1")){
				dataBaseService.runQuery("update jrun_debates set affirmvotes=affirmvotes+1");
				dataBaseService.runQuery("update jrun_debates set affirmvoterids=CONCAT(affirmvoterids, '"+jsprun_uid+"\t') WHERE tid='"+tid+"'");
			}else if(stand.equals("2")){
				dataBaseService.runQuery("update jrun_debates set negavotes=negavotes+1");
				dataBaseService.runQuery("update jrun_debates set negavoterids=CONCAT(negavoterids, '"+jsprun_uid+"\t') WHERE tid='"+tid+"'");
			}
		}else{
			String sql = "SELECT * FROM jrun_debateposts WHERE pid="+pid+" AND tid="+ tid;
			List<Map<String, String>> debatepostlist = dataBaseService.executeQuery(sql);
			if(debatepostlist==null || debatepostlist.size()<=0){
				printmessage(response,getMessage(request, "debate_nofound"));
				return null;
			}
			Map<String,String>debatepost = debatepostlist.get(0);
			if(debatepost.get("uid").equals(jsprun_uid+"")){
				printmessage(response,getMessage(request, "debate_poll_myself"));
				return null;
			}
			if(strpos(debatepost.get("voterids"),jsprun_uid+"")){
				printmessage(response,getMessage(request, "debate_poll_voted"));
				return null;
			}
			if(!debate.get("endtime").equals("0") && convertInt(debate.get("endtime"))<timestamp){
				printmessage(response,getMessage(request, "poll_end"));
				return null;
			}
			dataBaseService.runQuery("update jrun_debateposts set voters=voters+1,voterids=CONCAT(voterids, '"+jsprun_uid+"\t') WHERE pid='"+pid+"'");
		}
		printmessage(response,getMessage(request, "debate_poll_succeed"));
		return null;
	}
	private boolean strpos(String str,String target){
		if(str!=null && !str.equals("")){
			String []strs = str.split("\\s+");
			for(int i=0;i<strs.length;i++){
				if(strs[i].equals(target)){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	private void printmessage(HttpServletResponse response,String message){
		Common.setResponseHeader(response);
		try {
			response.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward viewpayments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		int creditstransid = Common.toDigit(settings.get("creditstrans")); 
		String extcredits = settings.get("extcredits");
		Map extcrditsMap = dataParse.characterParse(extcredits, true);
		Map creditstran=(Map)extcrditsMap.get(creditstransid);
		request.setAttribute("creditstrans", creditstran);
		int tid = Common.toDigit(request.getParameter("tid"));
		Threads thread = threadService.findByTid(tid);
		Forums forums = forumService.findById(thread.getFid());
		String navigation = "<a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo; <a href=viewthread.jsp?tid="+thread.getTid()+">"+thread.getSubject()+"</a>";
		if(forums.getType().equals("sub")){
			Forums parentforum = forumService.findById(forums.getFup());
			navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.getFid()+"\">"+parentforum.getName()+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo; <a href=viewthread.jsp?tid="+thread.getTid()+">"+thread.getSubject()+"</a>";
		}
		String paylogsql = "SELECT p.*, m.username FROM jrun_paymentlog p LEFT JOIN jrun_members m USING (uid) WHERE tid="+tid+" ORDER BY dateline";
		List<Map<String, String>> payloglist = dataBaseService.executeQuery(paylogsql);
		if(payloglist==null || payloglist.size()<=0){
			request.setAttribute("payloglist", null);
		}else{
			request.setAttribute("payloglist", payloglist);
		}
		request.setAttribute("navigation", navigation);
		return mapping.findForward("pay_view");
	}
	@SuppressWarnings("unchecked")
	public ActionForward pay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int tid = Common.toDigit(request.getParameter("tid"));
		HttpSession session = request.getSession();
		Map<String, String> settings = ForumInit.settings;
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		Threads thread = threadService.findByTid(tid);
		Forums forums = forumService.findById(thread.getFid());
		String navigation = "<a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo; <a href=viewthread.jsp?tid="+thread.getTid()+">"+thread.getSubject()+"</a>";
		if(forums.getType().equals("sub")){
			Forums parentforum = forumService.findById(forums.getFup());
			navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.getFid()+"\">"+parentforum.getName()+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo; <a href=viewthread.jsp?tid="+thread.getTid()+">"+thread.getSubject()+"</a>";
		}
		int jsprun_uid = (Integer)(session.getAttribute("jsprun_uid")==null?0:session.getAttribute("jsprun_uid"));
		String creditstrans = settings.get("creditstrans");
		if(creditstrans==null || creditstrans.equals("0")){
			request.setAttribute("errorInfo", getMessage(request, "magics_credits_no_open"));
			return mapping.findForward("showMessage");
		}
		if(thread.getPrice()<=0 || thread.getSpecial()!=0){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		if(jsprun_uid==0){
			request.setAttribute("show_message", getMessage(request, "group_nopermission", getMessage(request, "guest")));
			return mapping.findForward("nopermission");
		}
		Members member = (Members)session.getAttribute("user");
		int extcredit = (Integer)getValues(member, "extcredits"+creditstrans);
		int banlance = extcredit-thread.getPrice();
		request.setAttribute("banlance", banlance);
		if(banlance<0){
			request.setAttribute("errorInfo", getMessage(request, "credits_balance_insufficient", 0+""));
			return mapping.findForward("showMessage");
		}
		String paylogsql = "SELECT COUNT(*) count FROM jrun_paymentlog WHERE tid="+tid+" AND uid="+jsprun_uid;
		List<Map<String, String>> payloglist = dataBaseService.executeQuery(paylogsql);
		if(payloglist!=null && payloglist.size()>0 && Common.toDigit(payloglist.get(0).get("count"))>0){
			request.setAttribute("errorInfo", getMessage(request, "credits_buy_thread"));
			return mapping.findForward("showMessage");
		}
		String creditstax = settings.get("creditstax");
		double threaprice = Math.floor(thread.getPrice()*(1-Double.valueOf(creditstax)));
		request.setAttribute("threaprice", threaprice);
		request.setAttribute("thread", thread);
		request.setAttribute("navigation", navigation);
		try{
			if(submitCheck(request, "paysubmit")){
				boolean updateauthor = true;
				int  maxincperthread = convertInt(settings.get("maxincperthread"));
				if(maxincperthread>0){
					String sql = "SELECT SUM(netamount) sum FROM jrun_paymentlog WHERE tid="+tid;
					List<Map<String, String>> log = dataBaseService.executeQuery(sql);
					if(log!=null && log.size()>0){
						Map<String,String>paylogmap = log.get(0);
						if(convertInt(paylogmap.get("sum"))>maxincperthread){
							updateauthor = false;
						}
					}
				}
				if(updateauthor){
					dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"+"+threaprice+" WHERE uid="+thread.getAuthorid());
				}
				dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"-"+thread.getPrice()+" WHERE uid="+jsprun_uid);
				dataBaseService.runQuery("INSERT INTO jrun_paymentlog (uid, tid, authorid, dateline, amount, netamount)VALUES ('"+jsprun_uid+"', '"+tid+"', '"+thread.getAuthorid()+"', '"+timestamp +"', '"+thread.getPrice()+"', '"+threaprice+"')");
				request.setAttribute("successInfo", getMessage(request, "thread_pay_succeed"));
				request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		int creditstransid = Common.toDigit(creditstrans); 
		String extcredits = settings.get("extcredits");
		Map extcrditsMap = dataParse.characterParse(extcredits, true);
		Map creditstran=(Map)extcrditsMap.get(creditstransid);
		request.setAttribute("creditstrans", creditstran);
		return mapping.findForward("pay");
	}
	@SuppressWarnings("unchecked")
	public ActionForward bestanswer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "bestanswersubmit")){
				Map<String, String> settings = ForumInit.settings;
				int tid = Common.toDigit(request.getParameter("tid"));
				int pid = Common.toDigit(request.getParameter("pid"));
				HttpSession session = request.getSession();
				String boardurl = (String)session.getAttribute("boardurl");
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				String dateformat = settings.get("dateformat");
				String timeformat = settings.get("gtimeformat");
				String timeoffset = settings.get("timeoffset");
				int jsprun_uid = (Integer)(session.getAttribute("jsprun_uid")==null?0:session.getAttribute("jsprun_uid"));
				Posts post = postService.getPostsById(pid);
				Threads thread = threadService.findByTid(tid);
				if(!(thread.getSpecial().intValue()==3 && post!=null && thread.getAuthorid().intValue()==jsprun_uid && post.getAuthorid().intValue()!=thread.getAuthorid().intValue() && post.getFirst().intValue()==0 && jsprun_uid!=post.getAuthorid().intValue())){
					request.setAttribute("errorInfo", getMessage(request, "reward_cant_operate"));
					return mapping.findForward("showMessage");
				}else if(post.getAuthorid().intValue()==thread.getAuthorid().intValue()){
					request.setAttribute("errorInfo", getMessage(request, "reward_cant_self"));
					return mapping.findForward("showMessage");
				}else if(thread.getPrice()<0){
					request.setAttribute("errorInfo", getMessage(request, "reward_repeat_selection"));
					return mapping.findForward("showMessage");
				}
				String creditstrans = settings.get("creditstrans");
				dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"+"+thread.getPrice()+" WHERE uid='"+post.getAuthorid()+"'");
				dataBaseService.runQuery("DELETE FROM jrun_rewardlog WHERE tid='"+tid+"' and answererid='"+post.getAuthorid()+"'");
				dataBaseService.runQuery("UPDATE jrun_rewardlog SET answererid='"+post.getAuthorid()+"' WHERE tid='"+tid+"' and authorid='"+thread.getAuthorid()+"'");
				dataBaseService.runQuery("UPDATE jrun_threads SET price=-'"+thread.getPrice()+"' WHERE tid='"+tid+"'");
				dataBaseService.runQuery("UPDATE jrun_posts SET dateline="+thread.getDateline()+"+1 WHERE pid='"+pid+"'");
				List<Pms> pmslist = new ArrayList<Pms>();
				String jsprun_userss = (String)(session.getAttribute("jsprun_userss")==null?"":session.getAttribute("jsprun_userss"));
				List<Map<String,String>> usergroupmap = dataBaseService.executeQuery("select maxpmnum from jrun_usergroups as u left join jrun_members as m on u.groupid=m.groupid where m.uid="+thread.getAuthorid());
				if(usergroupmap!=null && usergroupmap.size()>0 && !"0".equals(usergroupmap.get(0).get("maxpmnum"))){
				Forums forum = forumService.findById(thread.getFid());
				if(jsprun_uid!=thread.getAuthorid()){
					Pms pms = new Pms();
					pms.setDateline(timestamp);
					pms.setDelstatus(Byte.valueOf("0"));
					pms.setFolder("inbox");
					pms.setMessage(getMessage(request, "reward_question_message", boardurl,jsprun_uid+"",jsprun_userss,thread.getTid()+"",thread.getSubject(),Common.gmdate(dateformat+" "+timeformat, thread.getDateline(),timeoffset),thread.getFid()+"",forum.getName()));
					pms.setMsgfrom(jsprun_userss);
					pms.setMsgfromid(jsprun_uid);
					pms.setMsgtoid(post.getAuthorid());
					pms.setNew_(Byte.valueOf("1"));
					pms.setSubject(getMessage(request, "reward_question_subject"));
					pmslist.add(pms);
				}
				Pms pms = new Pms();
				pms.setDateline(timestamp);
				pms.setDelstatus(Byte.valueOf("0"));
				pms.setFolder("inbox");
				pms.setMessage(getMessage(request, "reward_bestanswer_message", boardurl,jsprun_uid+"",jsprun_userss,thread.getTid()+"",thread.getSubject(),Common.gmdate(dateformat+" "+timeformat,thread.getDateline(),timeoffset),thread.getFid()+"",forum.getName()));
				pms.setMsgfrom(jsprun_userss);
				pms.setMsgfromid(jsprun_uid);
				pms.setNew_(Byte.valueOf("1"));
				pms.setSubject(getMessage(request, "reward_bestanswer_subject"));
				pms.setMsgtoid(post.getAuthorid());
				pmslist.add(pms);
				pmsServer.insertPmsList(pmslist);
				dataBaseService.runQuery("update jrun_members set newpm=1 where uid="+post.getAuthorid());
				}
				request.setAttribute("successInfo", getMessage(request, "reward_completion"));
				request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
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
	public ActionForward viewattachpayments(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		String creditstrans = settings.get("creditstrans");
		int creditstransid = Common.toDigit(creditstrans); 
		String extcredits = settings.get("extcredits");
		Map extcrditsMap = dataParse.characterParse(extcredits, true);
		Map creditstran=(Map)extcrditsMap.get(creditstransid);
		request.setAttribute("creditstrans", creditstran);
		int aid = Common.toDigit(request.getParameter("aid"));
		String sql = "SELECT a.*, m.username FROM jrun_attachpaymentlog a LEFT JOIN jrun_members m USING (uid) WHERE aid='"+aid+"' ORDER BY dateline";
		List<Map<String,String>> loglist = dataBaseService.executeQuery(sql);
		if(loglist==null || loglist.size()<=0){
			request.setAttribute("loglist", null);
		}else{
			request.setAttribute("loglist", loglist);
		}
		return mapping.findForward("attachpay_view");
	}
	@SuppressWarnings("unchecked")
	public ActionForward attachpay(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int aid = Common.toDigit(request.getParameter("aid"));
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		Members member = (Members)session.getAttribute("user");
		Map<String, String> settings = ForumInit.settings;
		String creditstrans = settings.get("creditstrans");
		double netprice = 0;
		List<Map<String,String>> attalist = null;
		if(aid<=0){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}else if(settings.get("creditstrans").equals("0")){
			request.setAttribute("errorInfo", getMessage(request, "magics_credits_no_open"));
			return mapping.findForward("showMessage");
		}else if(jsprun_uid==0){
			request.setAttribute("show_message", getMessage(request, "group_nopermission", getMessage(request, "guest")));
			return mapping.findForward("nopermission");
		}else{
			 attalist = dataBaseService.executeQuery("SELECT a.aid,a.tid, a.uid, a.price, a.filename, a.description, m.username  FROM jrun_attachments a LEFT JOIN jrun_members m ON a.uid=m.uid WHERE a.aid='"+aid+"'");
			 if(attalist==null || attalist.size()<=0 || convertInt(attalist.get(0).get("price"))<=0){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
				return mapping.findForward("showMessage");
			}
			int extcreditstrans = (Integer)getValues(member, "extcredits"+settings.get("creditstrans"));
			int banlance = extcreditstrans - convertInt(attalist.get(0).get("price"));
			if(banlance<=0){
				request.setAttribute("errorInfo",getMessage(request, "credits_balance_insufficient", 0+""));
				return mapping.findForward("showMessage");
			}
			request.setAttribute("banlance", banlance);
			List<Map<String,String>>logcount = dataBaseService.executeQuery("SELECT COUNT(*) as count FROM jrun_attachpaymentlog WHERE aid='"+aid+"' AND uid='"+jsprun_uid+"'");
			if(logcount!=null && logcount.size()>0 && !logcount.get(0).get("count").equals("0")){
				request.setAttribute("successInfo", getMessage(request, "attachment_yetpay"));
				request.setAttribute("requestPath", "attachment.jsp?aid="+aid);
				return mapping.findForward("showMessage");
			}
			netprice = Math.floor(Double.valueOf(attalist.get(0).get("price"))*(1-Double.valueOf(settings.get("creditstax"))));
		}
		try{
			if(submitCheck(request, "paysubmit")){
				int updateauthor = 1;
				int maxincperthread = convertInt(settings.get("maxincperthread"));
				if(maxincperthread>0){
					List<Map<String,String>> loglist = dataBaseService.executeQuery("SELECT SUM(netamount) as sum FROM jrun_attachpaymentlog WHERE aid='"+aid+"'");
					if(loglist!=null && loglist.size()>0 && convertInt(loglist.get(0).get("sum"))>maxincperthread){
						updateauthor = 0;
					}
				}
				if(updateauthor==1){
					dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"+"+netprice+" WHERE uid='"+attalist.get(0).get("uid")+"'");
				}
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"-"+attalist.get(0).get("price")+" WHERE uid='"+jsprun_uid+"'");
				dataBaseService.runQuery("insert into jrun_attachpaymentlog (uid, aid, authorid, dateline, amount, netamount) values ('"+jsprun_uid+"', '"+aid+"', '"+attalist.get(0).get("uid")+"', '"+timestamp+"', '"+attalist.get(0).get("price")+"', '"+netprice+"')");
				request.setAttribute("successInfo", getMessage(request, "attachment_buyd"));
				request.setAttribute("requestPath", "attachment.jsp?aid="+aid);
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		int creditstransid = Common.toDigit(creditstrans); 
		String extcredits = settings.get("extcredits");
		Map extcrditsMap = dataParse.characterParse(extcredits, true);
		Map creditstran=(Map)extcrditsMap.get(creditstransid);
		request.setAttribute("creditstrans", creditstran);
		request.setAttribute("atta", attalist.get(0));
		request.setAttribute("netprice", netprice);
		return mapping.findForward("attachpay");
	}
	@SuppressWarnings("unchecked")
	public ActionForward debateumpire(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int tid = Common.toDigit(request.getParameter("tid"));
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		String jsprun_userss = (String)session.getAttribute("jsprun_userss");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		List<Map<String,String>> debates = dataBaseService.executeQuery("select d.*,t.closed,t.subject,t.fid from jrun_debates d left join jrun_threads t on d.tid=t.tid where d.tid="+tid);
		if(debates==null || debates.size()<=0){
			request.setAttribute("errorInfo",getMessage(request, "debate_nofound"));
			return mapping.findForward("showMessage");
		}
		Map<String,String> debate = debates.get(0);
		debate.put("bestdebater", debate.get("bestdebater").split("\t")[0]);
		request.setAttribute("debate", debate);
		Forums forums = forumService.findById(Short.valueOf(debate.get("fid")));
		String navigation = "<a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo; <a href=\"viewthread.jsp?tid="+debate.get("tid")+"\">"+debate.get("subject")+"</a>";
		if(forums.getType().equals("sub")){
			Forums parentforum = forumService.findById(forums.getFup());
			navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.getFid()+"\">"+parentforum.getName()+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo <a href=\"viewthread.jsp?tid="+debate.get("tid")+"\">"+debate.get("subject")+"</a>";
		}
		request.setAttribute("navigation", navigation);
		debates = null;
		if(debate.get("closed").equals("-1") && timestamp-Common.toDigit(debate.get("endtime"))>3600){
			request.setAttribute("errorInfo", getMessage(request, "debate_umpire_edit_invalid"));
			return mapping.findForward("showMessage");
		}
		if(!jsprun_userss.equals(debate.get("umpire"))){
			request.setAttribute("errorInfo", getMessage(request, "debate_umpire_nopermission"));
			return mapping.findForward("showMessage");
		}
		try{
			if(submitCheck(request, "umpiresubmit")){
				String bestdebater = request.getParameter("bestdebater");
				String winner = request.getParameter("winner");
				String umpirepoint = request.getParameter("umpirepoint");
				if(bestdebater==null || bestdebater.equals("")){
					request.setAttribute("errorInfo", getMessage(request, "debate_umpire_nofound_bestdebater"));
					return mapping.findForward("showMessage");
				}else if(Common.empty(winner)){
					request.setAttribute("errorInfo", getMessage(request, "debate_umpire_nofound_winner"));
					return mapping.findForward("showMessage");
				}else if(Common.empty(umpirepoint)){
					request.setAttribute("errorInfo", getMessage(request, "debate_umpire_nofound_point"));
					return mapping.findForward("showMessage");
				}
				List<Map<String,String>> bestbater = dataBaseService.executeQuery("select uid from jrun_members where username=? limit 1",bestdebater);
				if(bestbater==null || bestbater.size()<=0 || bestbater.get(0).get("uid").equals("0")){
					request.setAttribute("errorInfo", getMessage(request, "debate_umpire_bestdebater_invalid"));
					return mapping.findForward("showMessage");
				}
				String bestdebateruid = bestbater.get(0).get("uid");
				List<Map<String,String>> debatepost = dataBaseService.executeQuery("SELECT stand FROM jrun_debateposts WHERE tid='"+tid+"' AND uid='"+bestdebateruid+"' AND stand>'0' AND uid<>'"+debate.get("uid")+"' AND uid<>'"+jsprun_uid+"' LIMIT 1");
				if(debatepost==null || debatepost.size()<=0){
					request.setAttribute("errorInfo", getMessage(request, "debate_umpire_bestdebater_invalid"));
					return mapping.findForward("showMessage");
				}
				String bestdebaterstand = debatepost.get(0).get("stand");
				List<Map<String,String>> depost = dataBaseService.executeQuery("SELECT SUM(voters) as voters, COUNT(*) as replies FROM jrun_debateposts WHERE tid='"+tid+"' AND uid='"+bestdebateruid+"'");
				String bestdebatervoters = depost.get(0).get("voters");
				String bestdebaterreplies = depost.get(0).get("replies");
				dataBaseService.runQuery("UPDATE jrun_threads SET closed='-1' WHERE tid='"+tid+"'");
				dataBaseService.runQuery("UPDATE jrun_debates SET umpirepoint='"+Common.addslashes(umpirepoint)+"', winner='"+Common.addslashes(winner)+"', bestdebater='"+Common.addslashes(bestdebater)+"\t"+bestdebateruid+"\t"+bestdebaterstand+"\t"+bestdebatervoters+"\t"+bestdebaterreplies+"', endtime='"+timestamp+"' WHERE tid='"+tid+"'");
				request.setAttribute("successInfo", getMessage(request, "debate_umpire_comment_succeed"));
				request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("tid", Integer.valueOf(tid));
		List<Map<String,String>> debateposts = dataBaseService.executeQuery("select sum(dp.voters) as voters,dp.stand,m.uid,m.username from jrun_debateposts dp left join jrun_members m ON m.uid=dp.uid WHERE dp.tid='"+tid+"' AND dp.stand<>0 GROUP BY m.uid ORDER BY voters DESC LIMIT 30");
		request.setAttribute("debateposts", debateposts);
		return mapping.findForward("debate_umpire");
	}
	@SuppressWarnings("unchecked")
	public ActionForward tradeorder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int tid = Common.toDigit(request.getParameter("tid"));
		int jsprun_uid = (Integer)request.getSession().getAttribute("jsprun_uid");
		List<Map<String,String>> threads = dataBaseService.executeQuery("select fid,tid,authorid,subject from jrun_threads where tid="+tid);
		if(threads==null || threads.size()<=0 || !threads.get(0).get("authorid").equals(jsprun_uid+"")){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		Map<String,String> thread = threads.get(0);
		request.setAttribute("threads", thread);
		List<Map<String,String>> trades = dataBaseService.executeQuery("SELECT * FROM jrun_trades WHERE tid='"+tid+"' ORDER BY displayorder");
		try{
			if(submitCheck(request, "tradesubmit")){
				int count = 0;
				Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
				String tradestick = usergroups.get("tradestick");
				for(Map<String,String> trade:trades){
					double displayordernew = Math.abs(Integer.valueOf(request.getParameter("displayorder["+trade.get("pid")+"]")));
					displayordernew = displayordernew>128?0:displayordernew;
					String stick = request.getParameter("stick["+trade.get("pid")+"]");
					if(!Common.empty(stick)){
						count ++ ;
						displayordernew = displayordernew==0?1:displayordernew;
					}
					if(Common.empty(stick)||displayordernew>0&&Common.toDigit(tradestick)<count){
						displayordernew = -1*(128-displayordernew);
					}
					dataBaseService.runQuery("update jrun_trades set displayorder='"+displayordernew+"' where tid="+tid+" and pid="+trade.get("pid"));
				} 
				request.setAttribute("successInfo", getMessage(request, "trade_displayorder_updated"));
				request.setAttribute("requestPath", "viewthread.jsp?tid="+tid);
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		Forums forums = forumService.findById(Short.valueOf(threads.get(0).get("fid")));
		String navigation = "<a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo; <a href=\"viewthread.jsp?tid="+thread.get("tid")+"\">"+thread.get("subject")+"</a>";
		if(forums.getType().equals("sub")){
			Forums parentforum = forumService.findById(forums.getFup());
			navigation = "<a href=\"forumdisplay.jsp?fid="+parentforum.getFid()+"\">"+parentforum.getName()+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+forums.getFid()+"\">"+forums.getName()+"</a> &raquo <a href=\"viewthread.jsp?tid="+thread.get("tid")+"\">"+thread.get("subject")+"</a>";
		}
		request.setAttribute("navigation", navigation);
		int stickcount = 0;
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		List<Map<String,String>> tradest = new ArrayList<Map<String,String>>();
		List<Map<String,String>> tradesstick = new ArrayList<Map<String,String>>();
		for(Map<String,String> trade:trades){
			stickcount = Common.toDigit(trade.get("displayorder"))>0?stickcount+1:stickcount;
			int displayorderview = Integer.valueOf(trade.get("displayorder"))<0?128+Integer.valueOf(trade.get("displayorder")):Integer.valueOf(trade.get("displayorder"));
			trade.put("displayorderview", displayorderview+"");
			if(!trade.get("expiration").equals("0")){
				float expiration = (Float.valueOf(trade.get("expiration"))-timestamp)/86400;
				if(expiration>0){
					double expirationhour = Math.floor((expiration-Math.floor(expiration))*24);
					trade.put("expirationhour", (int)expirationhour+"");
					trade.put("expiration", (int)Math.floor(expiration)+"");
				}else{
					trade.put("expiration", "-1");
				}
			}
			if(Common.toDigit(trade.get("displayorder"))<0){
				tradest.add(trade);
			}else{
				tradesstick.add(trade);
			}
		}
		tradesstick.addAll(tradest);
		request.setAttribute("stickcount", stickcount);
		request.setAttribute("trades",tradesstick);
		return mapping.findForward("trade_displayorder");
	}
}