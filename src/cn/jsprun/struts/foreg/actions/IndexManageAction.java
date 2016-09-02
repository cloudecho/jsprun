package cn.jsprun.struts.foreg.actions;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
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
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Md5Token;
public class IndexManageAction extends BaseAction {
	private String[] collapses=new String[]{"forumlinks","birthdays"};
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("CURSCRIPT", "index.jsp");
		request.setAttribute("jsprun_action","1");
		Map<String, String> settings = ForumInit.settings;
		HttpSession session = request.getSession();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int uid = (Integer) session.getAttribute("jsprun_uid");
		int cacheindexlife=Common.toDigit(settings.get("cacheindexlife"));
		int gid=Common.toDigit(request.getParameter("gid"));
		String showoldetails = request.getParameter("showoldetails");
		int frameon =Common.intval(settings.get("frameon"));
		if(cacheindexlife>0 && uid==0 && gid==0 && showoldetails==null&&(frameon==0||frameon>0&&!"yes".equals(request.getParameter("frameon")))){
			String realPath=JspRunConfig.realPath;
			Object[] indexcache = Common.getCacheInfo("0",realPath+settings.get("cachethreaddir"));
			Integer fileModified=(Integer)indexcache[0];
			String fileName=(String)indexcache[1];
			File file = new File(fileName);
			if(timestamp - fileModified > cacheindexlife){
				request.setAttribute("indexfileName", fileName);
			}else{
				if(file.exists()&&file.length()>200){
					String filename = fileName.substring(realPath.length());
					try {
						request.getRequestDispatcher(filename).include(request, response);
						if ("1".equals(settings.get("debug"))) {
							long starttime=(Long)request.getAttribute("starttime");
							long endtime=System.currentTimeMillis(); 
							String runTime=Common.number_format((endtime-starttime)/1000f, "0.000000");
							String timeoffset = (String) session.getAttribute("timeoffset");
							String debugtime = Common.gmdate("HH:mm:ss", fileModified, timeoffset);
							int gzipCompress = Common.intval(settings.get("gzipcompress"));
							String content = "<script type=\"text/javascript\">document.getElementById(\"debuginfo\").innerHTML = \"Update at "
									+ debugtime + ", Processed in "+runTime+" second(s)"+(gzipCompress >0 ? ", Gzip enabled" : "")+".\";</script>";
							response.getWriter().write(content);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
					return null;
				}
			}
		}
		Map<String,String> ftpmap = dataParse.characterParse(settings.get("ftp"), false);
		if(ftpmap.get("on").equals("1")){
			request.setAttribute("attachurl", ftpmap.get("attachurl"));
		}else{
			request.setAttribute("attachurl", settings.get("attachurl"));
		}
		if(showoldetails!=null) {
			CookieUtil.setCookie(request, response, "onlineindex", "yes".equals(showoldetails)?"1":"0", 31536000, true,settings);
		}
		request.setAttribute("google_searchbox", Integer.parseInt(settings.get("google_searchbox"))&1);
		request.setAttribute("baidu_searchbox", Integer.parseInt(settings.get("baidu_searchbox"))&1);
		String forumlinkstatus=settings.get("forumlinkstatus");
		if("1".equals(forumlinkstatus)){
			Map<String, String> forumlinks = (Map<String, String>) request.getAttribute("forumlinks");
			Map map = dataParse.characterParse(forumlinks.get("forumlinks"),true);
			request.setAttribute("forumlinks", map!=null&&map.size()>0 ? map : null);
		}
		String historyposts=settings.get("historyposts");
		request.setAttribute("postdata", historyposts.length()>0 ? historyposts.split("\t") : new String[]{"0", "0"});
		String jsprun_collapse=CookieUtil.getCookie(request, "jsprun_collapse", false,settings);
		Map<String,String> collapseMap=new HashMap<String,String>();
		Map<String,String> collapseimgMap=new HashMap<String,String>();
		for(String collapse:collapses){
			if(jsprun_collapse!=null&&jsprun_collapse.indexOf(collapse)>0){
				collapseMap.put(collapse, "display: none");
				collapseimgMap.put(collapse, "collapsed_yes.gif");
			}
			else{
				collapseMap.put(collapse, "");
				collapseimgMap.put(collapse, "collapsed_no.gif");
			}
		}
		String timeoffset=(String)session.getAttribute("timeoffset");
		String timeformat=(String)session.getAttribute("timeformat");
		String dateformat=(String)session.getAttribute("dateformat");
		SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
		byte accessmasks = 0;
		String extgroupids=null;
		int lastvisit=0;
		Members member =(Members)session.getAttribute("user");
		if(member!=null){
			accessmasks =member.getAccessmasks();
			extgroupids=member.getExtgroupids();
			lastvisit=member.getLastvisit();
			request.setAttribute("user_lastvisit", Common.gmdate(sdf_all,(lastvisit>0?lastvisit:(timestamp - 86400))));
			request.setAttribute("newthreads", Math.round((timestamp- lastvisit + 600f) / 1000f) * 1000);
		}
		short groupid=(Short)session.getAttribute("jsprun_groupid");
		String hideprivate=settings.get("hideprivate");
		int threads = 0, posts = 0, todayposts = 0;
		int subforumsindex=Common.toDigit(settings.get("subforumsindex"));
		if (gid>0) {
			List<Map<String, String>> maps = dataBaseService.executeQuery(accessmasks > 0 ? "SELECT f.fid, f.fup, f.type, f.name, f.threads, f.posts, f.todayposts, f.lastpost, f.inheritedmod, ff.description, ff.moderators, ff.icon, ff.viewperm, a.allowview FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid LEFT JOIN jrun_access a ON a.uid="+ uid+ " AND a.fid=f.fid WHERE f.status=1 AND (f.fid="+ gid+ " OR (f.fup="+ gid+ " AND f.type='forum')) ORDER BY f.type, f.displayorder": "SELECT f.fid, f.fup, f.type, f.name, f.threads, f.posts, f.todayposts, f.lastpost, f.inheritedmod, ff.description, ff.moderators, ff.icon, ff.viewperm FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) WHERE f.status=1 AND (f.fid="+ gid+ " OR (f.fup="+ gid+ " AND f.type='forum')) ORDER BY f.type, f.displayorder");
			if (maps != null && maps.size() > 0) {
				Map<String, List<Map<String, String>>> forums = new HashMap<String, List<Map<String, String>>>();
				List<Map<String, String>> groupsList = new ArrayList<Map<String, String>>();
				Map<String, Map<String, String>> lastposts = new HashMap<String, Map<String, String>>();
				Map<String, String> collapse = new HashMap<String, String>();
				StringBuffer fids=new StringBuffer();
				for (Map<String, String> forum : maps) {
					if (!"group".equals(forum.get("type"))) {
						threads += Integer.parseInt(forum.get("threads"));
						posts += Integer.parseInt(forum.get("posts"));
						todayposts += Integer.parseInt(forum.get("todayposts"));
						if (Common.forum(forum, hideprivate, groupid,lastvisit,extgroupids, lastposts,sdf_all)) {
							List<Map<String, String>> forumList = forums.get(forum.get("fup"));
							if (forumList == null) {
								forumList = new ArrayList<Map<String, String>>();
								forums.put(forum.get("fup"), forumList);
							}
							forum.put("subforums", "");
							forum.put("url","forumdisplay.jsp?fid="+forum.get("fid"));
							forumList.add(forum);
							for (Map<String, String> group : groupsList) {
								if (group.get("fid").equals(forum.get("fup")))	{
									group.put("forumscount",String.valueOf(Integer.parseInt(group.get("forumscount")) + 1));
								}
							}
							fids.append(","+forum.get("fid"));
						}
					}else{
						forum.put("collapseimg", "collapsed_no.gif");
						collapse.put("category_"+forum.get("fid"), "");
						forum.put("moderators", Common.moddisplay(forum.get("moderators"), "flag", false));
						forum.put("forumscount", "0");
						forum.put("forumcolumns", "0");
						groupsList.add(forum);
						request.setAttribute("navigation",Common.strip_tags("&raquo;"+forum.get("name")));
						request.setAttribute("navtitle",Common.strip_tags(forum.get("name")+"-"));
					}
				}
				maps=null;
				if(fids.length()>0){
					List<Map<String, String>> subs = dataBaseService.executeQuery("SELECT fid, fup, name, threads, posts, todayposts FROM jrun_forums WHERE status=1 AND fup IN ("+fids.substring(1)+") AND type='sub'");
					if (subs != null && subs.size() > 0) {
						for (Map<String, String> sub : subs) {
							Set<Entry<String, List<Map<String, String>>>> keys = forums.entrySet();
							for (Entry<String, List<Map<String, String>>> temp : keys) {
								List<Map<String, String>> forumList = temp.getValue();
								for (Map<String, String> forum : forumList) {
									if (forum.get("fid").equals(sub.get("fup"))){
										forum.put("threads",String.valueOf(Integer.parseInt(forum.get("threads"))+ Integer.parseInt(sub.get("threads"))));
										forum.put("posts",String.valueOf(Integer.parseInt(forum.get("posts"))+ Integer.parseInt(sub.get("posts"))));
										forum.put("todayposts",String.valueOf(Integer.parseInt(forum.get("todayposts"))+ Integer.parseInt(sub.get("todayposts"))));
										if (subforumsindex==1&& "2".equals(forum.get("permission"))) {
											forum.put("subforums",forum.get("subforums")+ "<a href=\"forumdisplay.jsp?fid="+ sub.get("fid")+ "\">" + sub.get("name")+ "</a>&nbsp;&nbsp;");
										}
									}
								}
							}
						}
						subs=null;
					}
				}
				request.setAttribute("catlists", groupsList);
				request.setAttribute("forums", forums);
				request.setAttribute("lastposts", lastposts);
				request.setAttribute("gid", gid);
			} else {
				request.setAttribute("errorInfo", getMessage(request, "forum_nonexistence"));
				return mapping.findForward("showMessage");
			}
		} else {
			List<Map<String, String>> pmlists = new ArrayList<Map<String, String>>();
			Map<String, String> announcementsMap = (Map<String, String>) request.getAttribute("announcements");
			Map<Integer, Map<String, String>> announcements = dataParse.characterParse(announcementsMap != null ? announcementsMap.get("announcements") : null,true);
			if(announcements!=null){
				int announcepm = 0;
				SimpleDateFormat sdf_dateformat=Common.getSimpleDateFormat(dateformat, timeoffset);
				StringBuffer announ=new StringBuffer();
				Set<Integer> annids=announcements.keySet();
				String readapmid = CookieUtil.getCookie(request, "readapmid"); 
				String []readapmids = !Common.empty(readapmid)?readapmid.split("D"):null;
				for (int annid:annids) {
					Map<String, String> announcement=announcements.get(annid);
					String groups=announcement.get("groups");
					if("".equals(groups)||(","+groups+",").contains(","+groupid+",")){
						String type=announcement.get("type");
						if("0".equals(type)){
							String id=announcement.get("id");
							announ.append("<li><a href='announcement.jsp?id="+id+"#"+id+"'>"+announcement.get("subject")+" <em>("+Common.gmdate(sdf_dateformat, Integer.parseInt(announcement.get("starttime")))+")</em></a></li>");
						}else if("1".equals(type)){
							announ.append("<li><a href='"+announcement.get("message")+"' target='_blank'>"+announcement.get("subject")+" <em>("+Common.gmdate(sdf_dateformat, Integer.parseInt(announcement.get("starttime")))+")</em></a></li>");
						}else if(uid>0&&"2".equals(type)&& !Common.in_array(readapmids, announcement.get("id"))){
							announcement.put("announce", "true");
							pmlists.add(announcement);
							announcepm++;
						}
					}
				}
				sdf_dateformat=null;
				request.setAttribute("announcements",announ.length()>0?announ.toString():null);
				request.setAttribute("announcepm", announcepm);
			}
			if (member != null && member.getNewpm() > 0) {
				String sql = "SELECT pmid, msgfrom, msgfromid, subject FROM jrun_pms WHERE msgtoid="+ member.getUid()+ " AND folder='inbox' AND delstatus!='2' AND new='1'";
				List<Map<String, String>> maps = dataBaseService.executeQuery(sql);
				int newpmnum = maps != null ? maps.size() : 0;
				if (newpmnum > 0 && newpmnum <= 10) {
					pmlists.addAll(maps);
				}
				request.setAttribute("newpmnum", newpmnum);
			}
			request.setAttribute("pmlists", pmlists.size() > 0 ? pmlists: null);
			request.setAttribute("extcredits", dataParse.characterParse(settings.get("extcredits"), true));
			List<Map<String, String>> maps = dataBaseService.executeQuery(accessmasks > 0 ? "SELECT f.fid, f.fup, f.type, f.name, f.threads, f.posts, f.todayposts, f.lastpost, f.inheritedmod, f.forumcolumns, f.simple, ff.description, ff.moderators, ff.icon, ff.viewperm, ff.redirect, a.allowview FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid LEFT JOIN jrun_access a ON a.uid="+ member.getUid()+ " AND a.fid=f.fid WHERE f.status=1 ORDER BY f.type, f.displayorder ": "SELECT f.fid, f.fup, f.type, f.name, f.threads, f.posts, f.todayposts, f.lastpost, f.inheritedmod, f.forumcolumns, f.simple, ff.description, ff.moderators, ff.icon, ff.viewperm, ff.redirect FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) WHERE f.status=1 ORDER BY f.type, f.displayorder ");
			Map<String, String> forumname = new HashMap<String, String>();
			if (maps != null && maps.size() > 0) {
				Map<String, List<Map<String, String>>> forums = new HashMap<String, List<Map<String, String>>>();
				List<Map<String, String>> groupsList = new ArrayList<Map<String, String>>();
				Map<String, Map<String, String>> lastposts = new HashMap<String, Map<String, String>>();
				for (Map<String, String> forumMap : maps) {
					String fid=forumMap.get("fid");
					forumname.put(fid,forumMap.get("name"));
					if ("group".equals(forumMap.get("type"))) {
						if(jsprun_collapse!=null&&jsprun_collapse.indexOf("category_"+fid)>0){
							collapseMap.put("category_"+fid, "display: none");
							forumMap.put("collapseimg","collapsed_yes.gif");
						}else{
							collapseMap.put("category_"+fid, "");
							forumMap.put("collapseimg", "collapsed_no.gif");
						}
						if (!"".equals(forumMap.get("moderators"))) {
							forumMap.put("moderators", Common.moddisplay(forumMap.get("moderators"), "flat", false));
						}
						forumMap.put("forumscount", "0");
						groupsList.add(forumMap);
					} else {
						threads += Integer.parseInt(forumMap.get("threads"));
						posts += Integer.parseInt(forumMap.get("posts"));
						todayposts += Integer.parseInt(forumMap.get("todayposts"));
						if ("forum".equals(forumMap.get("type"))) {
							if (Common.forum(forumMap, hideprivate, groupid,lastvisit,extgroupids, lastposts,sdf_all)) {
								List<Map<String, String>> forumList = forums.get(forumMap.get("fup"));
								if (forumList == null) {
									forumList = new ArrayList<Map<String, String>>();
									forums.put(forumMap.get("fup"), forumList);
								}
								forumMap.put("subforums", "");
								for (Map<String, String> group : groupsList) {
									if (group.get("fid").equals(forumMap.get("fup"))){
										group.put("forumscount",String.valueOf(Integer.parseInt(group.get("forumscount")) + 1));
									}
								}
								forumMap.put("url", "forumdisplay.jsp?fid="+fid);
								forumList.add(forumMap);
							}
						} else {
							Set<Entry<String, List<Map<String, String>>>> keys = forums.entrySet();
							for (Entry<String, List<Map<String, String>>> temp : keys) {
								List<Map<String, String>> forumList = temp.getValue();
								for (Map<String, String> forum : forumList) {
									if (forum.get("fid").equals(forumMap.get("fup"))){
										forum.put("threads",String.valueOf(Integer.parseInt(forum.get("threads"))+ Integer.parseInt(forumMap.get("threads"))));
										forum.put("posts",String.valueOf(Integer.parseInt(forum.get("posts"))+ Integer.parseInt(forumMap.get("posts"))));
										forum.put("todayposts",String.valueOf(Integer.parseInt(forum.get("todayposts"))+ Integer.parseInt(forumMap.get("todayposts"))));
										int simple=Integer.parseInt(forum.get("simple"));
										if (subforumsindex==1&& "2".equals(forum.get("permission"))&& !((simple & 16) > 0)|| (simple & 8) > 0) {
											forum.put("subforums",forum.get("subforums")+ "<a href=\"forumdisplay.jsp?fid="+ forumMap.get("fid")+ "\">"+ forumMap.get("name")+ "</a>&nbsp;&nbsp;");
										}
									}
								}
							}
						}
					}
				}
				maps=null;
				for (Map<String, String> category : groupsList) {
					int forumscount = Integer.parseInt(category.get("forumscount"));
					int forumcolumns = Integer.parseInt(category.get("forumcolumns"));
					if (forumscount > 0 && forumcolumns > 0) {
						category.put("forumcolwidth", Math.floor(100 / forumcolumns)+ "%");
						int colspan = forumscount % forumcolumns;
						if (colspan > 0) {
							StringBuffer endrows = new StringBuffer();
							while (forumcolumns - colspan > 0) {
								endrows.append("<td>&nbsp;</td>");
								colspan++;
							}
							category.put("endrows", endrows.toString());
						}
					}
				}
				request.setAttribute("catlists", groupsList);
				request.setAttribute("forums", forums);
				request.setAttribute("lastposts", lastposts);
			}
			int whosonlinestatus=Integer.parseInt(settings.get("whosonlinestatus"));
			if(whosonlinestatus==1||whosonlinestatus==3){
				request.setAttribute("whosonlinestatus",true);
				String[] onlineinfo=settings.get("onlinerecord").split("\t");
				String onlineusernum=CookieUtil.getCookie(request, "onlineusernum", true, settings);
				int onlinenum=0;
				if(onlineusernum==null){
					onlinenum=Integer.parseInt(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_sessions").get(0).get("count"));
					if(onlinenum>Integer.parseInt(onlineinfo[0])){
						String onlinerecord=onlinenum+"\t"+timestamp;
						onlineinfo[0]=String.valueOf(onlinenum);
						onlineinfo[1]=String.valueOf(timestamp);
						dataBaseService.runQuery("UPDATE jrun_settings SET value='"+onlinerecord+"' WHERE variable='onlinerecord'",true);
						settings.put("onlinerecord",onlinerecord);
					}
					CookieUtil.setCookie(request, response, "onlineusernum", String.valueOf(onlinenum), 300, true, settings);
				}else{
					onlinenum=Integer.parseInt(onlineusernum);
				}
				boolean detailstatus=false;
				if("yes".equals(showoldetails)){
					detailstatus=true;
				}else if(onlinenum < 500 && showoldetails==null){
					String onlineindex=CookieUtil.getCookie(request, "onlineindex", true,settings);
					detailstatus= (onlineindex==null && "0".equals(settings.get("whosonline_contract")) || "1".equals(onlineindex));
				}
				if(detailstatus){
					if(uid>0){
						Common.updatesession(request,settings);
					}
					Map<String, String> onlinelist = (Map<String, String>) request.getAttribute("onlinelist");
					int membercount=0, invisiblecount=0;
					int maxonlinelist = Integer.parseInt(settings.get("maxonlinelist"));
					if(maxonlinelist==0){
						maxonlinelist= 500;;
					}
					List<Map<String,String>> onlines=dataBaseService.executeQuery("SELECT uid, username, groupid, invisible, action, lastactivity, fid FROM jrun_sessions WHERE invisible='0'"+(onlinelist.get("7")!=null?"":" AND uid <> 0")+" ORDER BY uid DESC LIMIT "+maxonlinelist);
					if(onlines!=null&&onlines.size()>0){
						SimpleDateFormat sdf_timeformat=Common.getSimpleDateFormat(timeformat, timeoffset);
						List<Map<String,String>> onlinelists=new ArrayList<Map<String,String>>(onlines.size());
						for (Map<String, String> online : onlines) {
							if(Integer.parseInt(online.get("uid"))>0){
								membercount++;
								if(online.get("invisible").equals("1")){
									invisiblecount++;
									continue;
								}else{
									String icon=onlinelist.get(online.get("groupid"));
									online.put("icon", icon!=null?icon:onlinelist.get("0"));
								}
							}else{
								online.put("icon", onlinelist.get("7"));
								online.put("username", onlinelist.get("guest"));
							}
							online.put("fid",Integer.valueOf(online.get("fid"))>0?"\n "+getMessage(request, "forum_name")+": "+ Common.strip_tags(forumname.get(online.get("fid"))):"");
							online.put("action", getMessage(request,online.get("action")));
							online.put("lastactivity", Common.gmdate(sdf_timeformat, Integer.parseInt(online.get("lastactivity"))));
							onlinelists.add(online);
						}
						request.setAttribute("whosonline",onlinelists.size()>0?onlinelists:null);
					}
					if(onlinenum>maxonlinelist){
						membercount=Integer.parseInt(dataBaseService.executeQuery("SELECT (SELECT COUNT(*) FROM jrun_sessions) - (SELECT COUNT(*) FROM jrun_sessions WHERE uid = 0) AS count").get(0).get("count"));
						invisiblecount=Integer.parseInt(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_sessions WHERE invisible = '1'").get(0).get("count"));
					}
					if(onlinenum<membercount){
						onlinenum=Integer.parseInt(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_sessions").get(0).get("count"));
						CookieUtil.setCookie(request, response,"onlineusernum", String.valueOf(onlinenum), 300, true, settings);
					}
					request.setAttribute("membercount", membercount);
					request.setAttribute("invisiblecount", invisiblecount);
					request.setAttribute("guestcount", onlinenum - membercount);
				}
				request.setAttribute("onlinenum", onlinenum);
				onlineinfo[1]=Common.gmdate(dateformat, Integer.parseInt(onlineinfo[1]),timeoffset);
				request.setAttribute("onlineinfo",onlineinfo);
				request.setAttribute("detailstatus",detailstatus);
			}else{
				request.setAttribute("whosonlinestatus",false);
			}
		}
		request.setAttribute("collapseMap", collapseMap);
		request.setAttribute("collapseimgMap", collapseimgMap);
		request.setAttribute("posts", posts);
		request.setAttribute("threads", threads);
		request.setAttribute("todayposts", todayposts);
		String rssauth = "0";
		if("1".equals(settings.get("rssstatus"))){
			if(member!=null){
				Md5Token md5 = Md5Token.getInstance();
				rssauth = Common.encode(Common.authcode(member.getUid()+"\t\t"+md5.getLongToken(member.getPassword()+member.getSecques()).substring(0,8), "ENCODE", md5.getLongToken(settings.get("authkey")),null));
			}
			String boardurl = (String)session.getAttribute("boardurl");
			request.setAttribute("rsshead", "<link rel=\"alternate\" type=\"application/rss+xml\" title=\""+settings.get("bbname")+"\" href=\""+boardurl+"rss.jsp?auth="+rssauth+"\" />\n");
		}
		request.setAttribute("rssauth", rssauth);
		request.setAttribute("CACHE_INDEX", true);
		return mapping.findForward("toJsprun");
	}
}