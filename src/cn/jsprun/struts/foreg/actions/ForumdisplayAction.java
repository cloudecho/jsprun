package cn.jsprun.struts.foreg.actions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Md5Token;
public class ForumdisplayAction extends BaseAction {
	private Map<String, Integer> specialtype = new HashMap<String, Integer>();
	private List<String> orderfields=new ArrayList<String>();
	private List<String> order=new ArrayList<String>();
	{
		specialtype.put("poll", 1);
		specialtype.put("trade", 2);
		specialtype.put("reward", 3);
		specialtype.put("activity", 4);
		specialtype.put("debate", 5);
		specialtype.put("video", 6);
		orderfields.add("lastpost");
		orderfields.add("dateline");
		orderfields.add("replies");
		orderfields.add("views");
		order.add("DESC");
		order.add("ASC");
	}
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("jsprun_action", "2");
		short fid = (short)Common.toDigit(request.getParameter("fid"));
		HttpSession session = request.getSession();
		Members member = (Members) session.getAttribute("user");
		String modadd1 = null, modadd2 =null;
		if(member!=null&&member.getAdminid()==3){
			modadd1 = ", m.uid AS ismoderator ";
			modadd2 = " LEFT JOIN jrun_moderators m ON m.uid='"+member.getUid()+"' AND m.fid=f.fid ";
		}else{
			modadd1 = "";
			modadd2 = "";
		}
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		List<Map<String,String>> forums=dataBaseService.executeQuery("SELECT f.*, ff.*,a.allowpost,a.allowpostattach,a.allowview"+modadd1+" FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid LEFT JOIN jrun_access a ON a.uid='"+jsprun_uid+"' AND a.fid=f.fid "+modadd2+"WHERE f.fid='"+fid+"'");
		if (forums == null||forums.size()==0) {
			request.setAttribute("errorInfo", getMessage(request, "forum_nonexistence"));
			return mapping.findForward("showMessage");
		}
		Map<String, String> faqs = (Map<String, String>) request.getAttribute("faqs");
		request.setAttribute("faqs", dataParse.characterParse(faqs.get("faqs"), false));
		Map<String,String> forum=forums.get(0);
		Map<String, String> settings = ForumInit.settings;
		String type=forum.get("type");
		String redirect=null;
		if(forum.get("redirect").length()>0) {
			redirect=forum.get("redirect");
		}else if("group".equals(type)) {
			redirect=settings.get("indexname") + "?gid" + fid;
		}
		if (redirect!=null) {
			try {
				response.sendRedirect(redirect);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		request.setAttribute("fid", fid);
		String showoldetails=request.getParameter("showoldetails");
		if(showoldetails!=null) {
			CookieUtil.setCookie(request, response, "onlineforum", "yes".equals(showoldetails)?"1":"0", 31536000, true,settings);
		}
		short styleid=Short.valueOf(forum.get("styleid"));
		if(styleid>0){
			request.setAttribute("styleid",styleid);
		}
		String navtitle=null;
		String name=forum.get("name");
		short fup=Short.valueOf(forum.get("fup"));
		if(type.equals("forum")){
			request.setAttribute("navigation","&raquo; "+name);
			navtitle=Common.strip_tags(name);
		}
		else{
			Map<String,String> sub=dataBaseService.executeQuery("SELECT name FROM jrun_forums WHERE fid="+fup+" limit 1").get(0);
			String subName=Common.strip_tags(sub.get("name"));
			request.setAttribute("navigation","&raquo; <a href=\"forumdisplay.jsp?fid="+fup+"\">"+subName+"</a> &raquo; "+name);
			navtitle=Common.strip_tags(name)+" - "+subName;
		}
		request.setAttribute("navtitle", navtitle+" - ");
		request.setAttribute("metakeywords", forum.get("keywords").length()>0?forum.get("keywords"):Common.strip_tags(name));
		request.setAttribute("metadescription", forum.get("description").length()>0?Common.strip_tags(forum.get("description")):Common.strip_tags(name));
		request.setAttribute("google_searchbox", Integer.parseInt(settings.get("google_searchbox"))&2);
		request.setAttribute("baidu_searchbox", Integer.parseInt(settings.get("baidu_searchbox"))&2);
		String extgroupid=member!=null?member.getExtgroupids():null;
		short groupid=(Short)session.getAttribute("jsprun_groupid");
		String viewperm=forum.get("viewperm");
		int creditstrans=Integer.parseInt(settings.get("creditstrans"));
		if(viewperm.length()>0&&!Common.forumperm(viewperm, groupid, extgroupid)&&Common.empty(forum.get("allowview"))){
			request.setAttribute("show_message", getMessage(request, "forum_nopermission_2"));
			return mapping.findForward("nopermission");
		}
		boolean ismoderator=Common.ismoderator(forum.get("ismoderator"), member);
		request.setAttribute("ismoderator",ismoderator);
		Map<Integer,Map<String,String>> extcredits=dataParse.characterParse(settings.get("extcredits"), false);
		String formulaperm=forum.get("formulaperm");
		if(formulaperm.length()>0){
			MessageResources resources = getResources(request);
			Locale locale = getLocale(request);
			Map<String,String> messages=Common.forumformulaperm(formulaperm, member,ismoderator, extcredits,resources,locale);
			if(messages!=null){
				request.setAttribute("show_message", getMessage(request, "forum_permforum_nopermission",messages.get("formulamessage"),messages.get("usermsg")));
				return mapping.findForward("nopermission");
			}				
		}
		String password=forum.get("password");
		if(password.length()>0){
			String pwverify=request.getParameter("pwverify");
			if("true".equals(pwverify)){
				String pw=request.getParameter("pw");
				if(password.equals(pw)){
					CookieUtil.setCookie(request, response, "fidpw"+fid, pw, 31536000, true, settings);
					session.setAttribute("fidpw"+fid, password);
					request.setAttribute("successInfo", getMessage(request, "forum_passwd_correct"));
					request.setAttribute("requestPath", "forumdisplay.jsp?fid="+fid);
					return mapping.findForward("showMessage");
				}else{
					request.setAttribute("resultInfo", getMessage(request, "forum_passwd_incorrect"));
					return mapping.findForward("showMessage");
				}
			}
			String pw=CookieUtil.getCookie(request, "fidpw"+fid, true, settings);
			if(!password.equals(pw)){
				return mapping.findForward("toForumdisplay_passwd");
			}
		}
		request.setAttribute("moderatedby", Common.moddisplay(forum.get("moderators"),"forumdisplay", false));
		int timestamp = (Integer)request.getAttribute("timestamp");
		String jsprun_collapse=CookieUtil.getCookie(request, "jsprun_collapse", false,settings);
		Map<String,String> collapse=new HashMap<String,String>();
		if(forum.get("rules").length()>0) {
			if(jsprun_collapse!=null&&jsprun_collapse.indexOf("rules")>0){
				collapse.put("rules", "display: none");
				collapse.put("rules_link", "");
			}
			else{
				collapse.put("rules", "");
				collapse.put("rules_link", "display: none");
			}
			forum.put("rules", Common.nl2br(forum.get("rules")));
		}
		Map modrecommend = dataParse.characterParse(forum.get("modrecommend"), false);
		if (modrecommend != null && "1".equals(modrecommend.get("open"))) {
			request.setAttribute("modrecommend", modrecommend);
			List<Map<String, String>> recommendlist=updateRecommend(fid,modrecommend, false, timestamp);
			if(recommendlist.size()>0){
				if(jsprun_collapse!=null&&jsprun_collapse.indexOf("recommendlist")>0){
					collapse.put("recommendlist", "display: none");
					collapse.put("recommendlist_link", "");
				}
				else{
					collapse.put("recommendlist", "");
					collapse.put("recommendlist_link", "display: none");
				}
				request.setAttribute("recommendlist", recommendlist);
			}
		}
		if(member!=null){
			Map<String,String> announcementsMap=(Map<String,String>)request.getAttribute("announcements");
			Map<Integer,Map> announcements=dataParse.characterParse(announcementsMap!=null?announcementsMap.get("announcements"):null,true);
			List<Map<String,String>> pmlists=new ArrayList<Map<String,String>>();;
			if(announcements!=null&&announcements.size()>0){
				int announcepm=0;
				Set<Integer> keys=announcements.keySet();
				String readapmid = CookieUtil.getCookie(request, "readapmid"); 
				String []readapmids = !Common.empty(readapmid)?readapmid.split("D"):null;
				for (Integer key : keys) {
					Map<String,String> announcement=announcements.get(key);
					if(announcement.get("type")!=null&&announcement.get("type").equals("2")&& !Common.in_array(readapmids, announcement.get("id"))){
						announcement.put("announce", "true");
						pmlists.add(announcement);
						announcepm++;
					}
				}
				request.setAttribute("announcepm", announcepm);
			}
			if(member.getNewpm()>0){
				List<Map<String,String>> maps=dataBaseService.executeQuery("SELECT pmid, msgfrom, msgfromid, subject, message FROM jrun_pms WHERE msgtoid="+member.getUid()+" AND folder='inbox' AND delstatus!='2' AND new='1'");
				int newpmnum=maps!=null?maps.size():0;
				if(newpmnum>0&&newpmnum<=10){
					pmlists.addAll(maps);
				}
				request.setAttribute("newpmnum", newpmnum);
			}
			request.setAttribute("pmlists", pmlists.size()>0?pmlists:null);
		}
		String timeoffset=(String)session.getAttribute("timeoffset");
		String timeformat=(String)session.getAttribute("timeformat");
		String dateformat=(String)session.getAttribute("dateformat");
		SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
		byte accessmasks = member != null ? member.getAccessmasks() : 0;
		String sql = accessmasks > 0 ? "SELECT f.fid, f.fup, f.type, f.name, f.threads, f.posts, f.todayposts, f.lastpost, f.inheritedmod, f.forumcolumns, f.simple, ff.description, ff.moderators, ff.icon, ff.viewperm, ff.redirect, a.allowview FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid LEFT JOIN jrun_access a ON a.uid="+ jsprun_uid+ " AND a.fid=f.fid WHERE fup="+ fid+ " AND f.status=1 AND type='sub' ORDER BY f.displayorder": "SELECT f.fid, f.fup, f.type, f.name, f.threads, f.posts, f.todayposts, f.lastpost, f.inheritedmod, f.forumcolumns, f.simple, ff.description, ff.moderators, ff.icon, ff.viewperm, ff.redirect FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) WHERE fup="+ fid+ " AND f.status=1 AND type='sub' ORDER BY f.displayorder";
		List<Map<String, String>> maps = dataBaseService.executeQuery(sql);
		if (maps != null && maps.size() > 0) {
			Map<String, Map<String, String>> lastposts = new TreeMap<String, Map<String, String>>();
			List<Map<String, String>> subforums = new ArrayList<Map<String, String>>();
			String hideprivate=settings.get("hideprivate");
			int lastvisit=member!=null?member.getLastvisit():0;
			for (Map<String, String> forumMap : maps) {
				if (Common.forum(forumMap, hideprivate, groupid, lastvisit, extgroupid, lastposts,sdf_all)) {
					subforums.add(forumMap);
				}
			}
			short forumcolumns=Short.valueOf(forum.get("forumcolumns"));
			if (forumcolumns > 0) {
				int colspan = subforums.size() % forumcolumns;
				if (colspan > 0) {
					StringBuffer endrows = new StringBuffer();
					while (forumcolumns - colspan > 0) {
						endrows.append("<td>&nbsp;</td>");
						colspan++;
					}
					request.setAttribute("endrows", endrows);						
				}
				request.setAttribute("forumcolwidth", Math.floor(100 / forumcolumns)+ "%");
			}
			Map<String,String> collapseimg=new HashMap<String,String>();
			if(jsprun_collapse!=null&&jsprun_collapse.indexOf("subforum_"+fid)>0){
				collapse.put("subforum", "display: none");
				collapseimg.put("subforum", "collapsed_yes.gif");
			}
			else{
				collapse.put("subforum", "");
				collapseimg.put("subforum", "collapsed_no.gif");
			}
			request.setAttribute("collapseimg", collapseimg);
			request.setAttribute("lastposts", lastposts);
			request.setAttribute("subforums",subforums.size() > 0 ? subforums : null);
		}
		if(settings.get("forumjump").equals("1")){
			List<Map<String,String>> forumlist=dataParse.characterParse(settings.get("forums"));
			if("1".equals(settings.get("jsmenu_1"))){
				request.setAttribute("forummenu", Common.forumselect(forumlist,groupid, extgroupid, String.valueOf(fid)));
			}
			else{
				request.setAttribute("forumselect", Common.forumselect(forumlist,false, false, groupid, extgroupid,null));
			}				
		}
		request.setAttribute("forum", forum);
		request.setAttribute("threadsticky", settings.get("threadsticky").split(","));
		short simple = Short.valueOf(forum.get("simple"));
		if((simple & 1)>0) {
			return mapping.findForward("toForumdisplay_simple");
		}
		int defaultorderfield = simple / 64;
		simple %= 64;
		int defaultorder = simple / 32;
		simple %= 32;
		int subforumsindex = -1;
		if (simple >= 16) {
			subforumsindex = 1;
			simple -= 16;
		}
		if (simple >= 8) {
			subforumsindex = 0;
			simple -= 8;
		}
		request.setAttribute("simple", simple);
		request.setAttribute("subforumsindex", subforumsindex);
		request.setAttribute("defaultorderfield", defaultorderfield);
		request.setAttribute("defaultorder", defaultorder);
		String filteradd = null;
		StringBuffer forumdisplayadd = new StringBuffer();
		String filter = request.getParameter("filter");
		int typeid = Common.range(Common.intval(request.getParameter("typeid")), 255, 0);
		if (filter == null) {
			filteradd=" ";
			filter = "";
		}else if ("digest".equals(filter)) {
			forumdisplayadd.append("&amp;filter=digest");
			filteradd = " AND digest>'0'";
		}else if("type".equals(filter)&& typeid >0) {
			forumdisplayadd.append("&amp;filter=type&amp;typeid="+typeid);
			filteradd = " AND typeid="+typeid;
		} else if (filter.matches("^\\d+$")) {
			forumdisplayadd.append("&amp;filter=" + filter);
			filteradd = Integer.parseInt(filter) > 0 ? " AND lastpost>='"+ (timestamp - Integer.parseInt(filter)) + "'": "";
		} else if (specialtype.get(filter) != null) {
			forumdisplayadd.append("&amp;filter=" + filter);
			filteradd = " AND special='" + specialtype.get(filter)+ "'";
		} else {
			filteradd=" ";
			filter = "";
		}
		List<Map<String, String>> count = null;
		if(filter.equals("")){
			count = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_threads WHERE fid="+ fid +" AND displayorder IN (0, 1)");
		}else{
			count = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_threads WHERE fid=" + fid + filteradd+ " AND displayorder>='0'");
		}
		int threadcount = Integer.parseInt(count.get(0).get("count"));
		String orderby=request.getParameter("orderby");
		String ascdesc=request.getParameter("ascdesc");
		if(orderby!=null&&orderfields.contains(orderby)){
			forumdisplayadd.append("&amp;orderby="+orderby);
		}else{
			orderby=orderfields.get(defaultorderfield);
		}
		if(ascdesc!=null&&order.contains(ascdesc)){
			forumdisplayadd.append("&amp;ascdesc="+ascdesc);
		}else{
			ascdesc=order.get(defaultorder);
		}
		Map<String,String> checked=new HashMap<String,String>();
		checked.put(filter, "selected='selected'");
		checked.put(orderby, "selected='selected'");
		checked.put(ascdesc, "selected='selected'");
		request.setAttribute("checked", checked);
		Map threadtypes=dataParse.characterParse(forum.get("threadtypes"), true);
		request.setAttribute("threadtypes", threadtypes.size()>0?threadtypes:null);			
		int globalstick=Common.range(Common.intval(settings.get("globalstick")), 255, 0);
		String thisgid = String.valueOf(type.equals("forum")? fup: forumService.findById(fup).getFup());
		request.setAttribute("thisgid", thisgid);
		int stickycount=0;
		StringBuffer stickytids=new StringBuffer();
		if(globalstick>0) {
			Map<String,String> globalstickMap=(Map<String,String>)request.getAttribute("globalstick");
			if(globalstickMap!=null){
				Map<String,Map<String,String>> globalsticks=dataParse.characterParse(globalstickMap.get("globalstick"),false);
				if(globalsticks!=null){
					Map<String,String> global=globalsticks.get("global");
					Map<String,String> categories=globalsticks.get(thisgid);
					if(global!=null){
						stickytids.append(global.get("tids"));
						stickycount=Integer.parseInt(global.get("count"));
					}
					if(categories!=null){
						stickytids.append(stickytids.length()==0?categories.get("tids"):","+categories.get("tids"));
						stickycount+=Integer.parseInt(categories.get("count"));
					}
				}
			}
		}
		boolean filterbool = filter!=null &&"|digest|type|activity|poll|trade|reward|debate|video|".contains("|"+filter+"|");
		threadcount += filterbool ? 0 : stickycount;
		Long threadmaxpages =Long.valueOf(settings.get("threadmaxpages"));
		int page = Math.max(Common.intval(request.getParameter("page")), 1);
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.parseInt(settings.get("topicperpage"));
		Map<String,Integer> multiInfo=Common.getMultiInfo(threadcount, tpp, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		String extra=Common.encode("page="+page+ forumdisplayadd);
		String url="forumdisplay.jsp?fid=" + fid + forumdisplayadd;
		request.setAttribute("extra",extra);
		Map<String,Object> multi=Common.multi(threadcount, tpp, page,url, threadmaxpages.intValue(), 10, true, false, null);
		request.setAttribute("page",page);
		request.setAttribute("url",url);
		request.setAttribute("multi", multi);
		String displayorderadd=!filterbool&&stickycount>0?"t.displayorder IN (0, 1)":"t.displayorder IN (0, 1, 2, 3)";
		String querysticky=null;
		String query=null;
		if((start_limit>0&&start_limit>stickycount)||stickycount==0||filterbool){
			query = "SELECT t.* FROM jrun_threads t WHERE t.fid='"+fid+"' "+filteradd+" AND "+displayorderadd+" ORDER BY t.displayorder DESC, t."+orderby+" "+ascdesc+" LIMIT "+(filterbool ? start_limit : start_limit - stickycount)+", "+tpp;
		}
		else{
			querysticky="SELECT t.* FROM jrun_threads t WHERE t.tid IN ("+stickytids+") AND t.displayorder IN (2, 3) ORDER BY displayorder DESC, "+orderby+" "+ascdesc+" LIMIT "+start_limit+", "+(stickycount - start_limit < tpp ? stickycount - start_limit:tpp);
			if(tpp-stickycount+start_limit>0){
				query = "SELECT t.* FROM jrun_threads t WHERE t.fid="+fid+" "+filteradd+" AND "+displayorderadd+" ORDER BY displayorder DESC, "+orderby+" "+ascdesc+" LIMIT "+(tpp - stickycount + start_limit);
			}
		}
		Map<String,String> announcement=(Map<String,String>)request.getAttribute("announcement");
		if(announcement!=null&&announcement.size()>0){
			announcement.put("starttime", Common.gmdate(dateformat, Integer.parseInt(announcement.get("starttime")),timeoffset));
			request.setAttribute("announcement",announcement);
		}
		List<Map<String, String>> threadlists=new ArrayList<Map<String,String>>();
		if(querysticky!=null){
			threadlists = dataBaseService.executeQuery(querysticky);
		}
		if(query!=null){
			threadlists.addAll(dataBaseService.executeQuery(query));
		}
		if (threadlists != null && threadlists.size() > 0) {
			Map types=null;
			if(threadtypes.size()>0){
				types=(Map)threadtypes.get("types");
			}
			int postperpage=Integer.parseInt(settings.get("postperpage"));
			int ppp= Byte.valueOf(forum.get("threadcaches")) > 0 &&jsprun_uid==0?postperpage:(member != null&& member.getPpp() > 0 ? member.getPpp() :postperpage);
			SimpleDateFormat sdf_dateformat=Common.getSimpleDateFormat(dateformat, timeoffset);
			String closedby=null;
			short autoclose=Short.valueOf(forum.get("autoclose"));
			if(autoclose!=0){
				closedby = autoclose > 0 ? "dateline" : "lastpost";
				autoclose=(short)(Math.abs(autoclose)*86400);
			}
			int separatepos = 0;
			for (Map<String, String> thread : threadlists) {
				int thread_typeid=Integer.parseInt(thread.get("typeid"));
				if(thread_typeid>0&&!Common.empty(threadtypes.get("prefix"))&&types.get(thread_typeid)!=null){
					thread.put("type", "<em>[<a href=\"forumdisplay.jsp?fid="+fid+"&amp;filter=type&amp;typeid="+thread_typeid+"\">"+types.get(thread_typeid)+"</a>]</em>");
				}
				int replies = Integer.parseInt(thread.get("replies"));
				int special = Integer.parseInt(thread.get("special"));
				int topicpages = special > 0 ? replies : replies + 1;
				if (topicpages > ppp) {
					StringBuffer pagelinks =new StringBuffer();
					topicpages = (int)(Math.ceil((double)topicpages/(double)ppp));
					for (int i = 1; i <= 6 && i <= topicpages; i++) {
						pagelinks.append("<a href=\"viewthread.jsp?tid="+ thread.get("tid")+"&amp;extra="+extra+"&amp;page=" + i + "\">" + i + "</a> ");
					}
					if (topicpages > 6) {
						pagelinks.append(" .. <a href=\"viewthread.jsp?tid="+ thread.get("tid")+"&amp;extra="+extra+"&amp;page=" + topicpages + "\">"+ topicpages + "</a> ");
					}
					thread.put("multipage", " &nbsp; " + pagelinks);
				}
				Common.procThread(thread);
				int closed = Integer.parseInt(thread.get("closed"));
				if (closed != 0	||( autoclose!=0&& (timestamp- Integer.parseInt(thread.get(closedby)) > autoclose))) {
					thread.put("new", "0");
					if (closed > 0) {
						thread.put("moved", thread.get("tid"));
						thread.put("tid", String.valueOf(closed));
						thread.put("replies", "-");
						thread.put("views", "-");
					}
					thread.put("folder", "lock");
				} else {
					thread.put("folder", "common");
					if (member != null&& member.getLastvisit() < Integer.parseInt(thread.get("lastpost"))) {
						thread.put("new", "1");
						thread.put("folder", "new");
					} else {
						thread.put("new", "0");
					}
					if (Integer.parseInt(thread.get("replies"))>Integer.parseInt(thread.get("views"))) {
						thread.put("views", thread.get("replies"));
					}
					if (Integer.parseInt(thread.get("replies"))>Integer.parseInt(settings.get("hottopic"))) {
						thread.put("folder", "hot");
					}
				}
				thread.put("dateline", Common.gmdate(sdf_dateformat,Integer.parseInt(thread.get("dateline"))));
				thread.put("lastpost", Common.gmdate(sdf_all, Integer.parseInt(thread.get("lastpost"))));
				int displayorder = Integer.parseInt(thread.get("displayorder"));
				if (displayorder == 1 || displayorder == 2|| displayorder == 3) {
					thread.put("id", "stickthread_" + thread.get("tid"));
					separatepos++;
				}else {
					thread.put("id", "normalthread_" + thread.get("tid"));
				}
			}
			separatepos = separatepos>0 ? separatepos +1: (announcement!=null&&announcement.size()>0? 1 : 0);
			request.setAttribute("threadlists", threadlists != null&& threadlists.size() > 0 ? threadlists : null);
			request.setAttribute("creditstrans",creditstrans);
			request.setAttribute("extcredits", extcredits);
			request.setAttribute("separatepos", separatepos);
		}
		String postperm=forum.get("postperm");
		Map<String, String> usergroups = (Map<String, String>) request.getAttribute("usergroups");
		boolean allowpostattach =  !Common.empty(forum.get("allowpostattach")) || (!usergroups.get("allowpostattach").equals("0") && forum.get("postattachperm").equals("")) || Common.forumperm(forum.get("postattachperm"), groupid,extgroupid);  
		request.setAttribute("allowpostattach", allowpostattach);
		boolean allowpost=(("".equals(postperm))&&Integer.parseInt(usergroups.get("allowpost"))>0)||((!"".equals(postperm))&&Common.forumperm(postperm, groupid, extgroupid))||!Common.empty(forum.get("allowpost"));
		boolean showpoll=false;
		boolean showtrade=false;
		boolean showreward=false;
		boolean showactivity=false;
		boolean showdebate=false;
		boolean showvideo=false;
		short allowpostspecial= Short.valueOf(forum.get("allowpostspecial"));
		if(allowpostspecial>0){
			showpoll=(allowpostspecial&1)>0;
			showtrade=(allowpostspecial&2)>0;
			showreward=(allowpostspecial&4)>0;
			showactivity=(allowpostspecial&8)>0;
			showdebate=(allowpostspecial&16)>0;
			showvideo=(allowpostspecial&32)>0&&"1".equals(settings.get("videoopen"));
			request.setAttribute("showpoll",showpoll );
			request.setAttribute("showtrade",showtrade );
			request.setAttribute("showreward",showreward );
			request.setAttribute("showactivity",showactivity );
			request.setAttribute("showdebate",showdebate );
			request.setAttribute("showvideo",showvideo);
		}
		if(allowpost){
			request.setAttribute("allowpostpoll",usergroups.get("allowpostpoll").equals("1")&&showpoll);
			request.setAttribute("allowposttrade",usergroups.get("allowposttrade").equals("1")&&showtrade );
			request.setAttribute("allowpostreward",usergroups.get("allowpostreward").equals("1")&&showreward&& extcredits.get(creditstrans)!=null);
			request.setAttribute("allowpostactivity",usergroups.get("allowpostactivity").equals("1")&&showactivity );
			request.setAttribute("allowpostdebate",usergroups.get("allowpostdebate").equals("1")&&showdebate );
			request.setAttribute("allowpostvideo",usergroups.get("allowpostvideo").equals("1")&&showvideo);
		}
		request.setAttribute("allowpost", allowpost);
		request.setAttribute("typemodels", dataParse.characterParse(forum.get("typemodels"), true));
		request.setAttribute("forumdisplayadd", forumdisplayadd);
		request.setAttribute("filter", filter);
		request.setAttribute("typeid", typeid);
		int whosonlinestatus=Integer.parseInt(settings.get("whosonlinestatus"));
		if(whosonlinestatus==2||whosonlinestatus==3){
			request.setAttribute("whosonlinestatus",true);
			String[] onlineinfo=settings.get("onlinerecord").split("\t");
			boolean detailstatus=false;
			if("yes".equals(showoldetails)){
				detailstatus=true;
			}else if(Integer.parseInt(onlineinfo[0]) < 500 && showoldetails==null){
				String onlineforum=CookieUtil.getCookie(request, "onlineforum", true,settings);
				detailstatus= (onlineforum==null && "0".equals(settings.get("whosonline_contract")) || "1".equals(onlineforum));
			}
			if(detailstatus){
				Common.updatesession(request,settings);
				request.setAttribute("forumname", Common.strip_tags(name));
				Map<String, String> onlinelist = (Map<String, String>) request.getAttribute("onlinelist");
				List<Map<String,String>> onlines=dataBaseService.executeQuery("SELECT uid, username, groupid, invisible, action, lastactivity, fid FROM jrun_sessions WHERE "+(onlinelist.get("7")!=null?"":"uid > 0 AND ")+"fid="+fid+"  AND invisible='0'");
				if(onlines!=null&&onlines.size()>0){
					SimpleDateFormat sdf_timeformat=Common.getSimpleDateFormat(timeformat, timeoffset);
					for (Map<String, String> online : onlines) {
						if(Integer.parseInt(online.get("uid"))>0){
							String icon=onlinelist.get(online.get("groupid"));
							online.put("icon", icon!=null?icon:onlinelist.get("0"));
						}else{
							online.put("icon", onlinelist.get("7"));
							online.put("username", onlinelist.get("guest"));
						}
						online.put("action", getMessage(request,online.get("action")));
						online.put("lastactivity",Common.gmdate(sdf_timeformat, Integer.parseInt(online.get("lastactivity"))));
					}
					request.setAttribute("whosonline", onlines);
				}
			}
			request.setAttribute("detailstatus",detailstatus);
		}else{
			request.setAttribute("whosonlinestatus",false);
		}
		int visitedforumcount=Integer.parseInt(settings.get("visitedforums"));
		if(visitedforumcount>0){
			Map<Short,String> visitedforums=(Map<Short,String>)session.getAttribute("visitedforums");
			if(visitedforums==null){
				visitedforums=new LinkedHashMap<Short,String>();
			}
			visitedforums.put(fid, name);
			if(visitedforums.size()>visitedforumcount+1){
				Set<Short> keys=visitedforums.keySet();
				for (Short key : keys) {
					visitedforums.remove(key);
					break;
				}
			}
			session.setAttribute("visitedforums", visitedforums);
			request.setAttribute("visitedforums",visitedforums.size()>1?visitedforums:null);
		}
		request.setAttribute("collapse", collapse);
		if(allowpost&&"1".equals(settings.get("fastpost"))){
			request.setAttribute("coloroptions", Common.COLOR_OPTIONS);
		}
		String rssauth = "0";
		if("1".equals(settings.get("rssstatus"))){
			if(member!=null){
				Md5Token md5 = Md5Token.getInstance();
				rssauth = Common.encode(Common.authcode(member.getUid()+"\t"+fid+"\t"+md5.getLongToken(member.getPassword()+member.getSecques()).substring(0,8), "ENCODE", md5.getLongToken(settings.get("authkey")),null));
			}
			String boardurl = (String)session.getAttribute("boardurl");
			request.setAttribute("rsshead", "<link rel=\"alternate\" type=\"application/rss+xml\" title=\""+settings.get("bbname")+" - "+navtitle+"\" href=\""+boardurl+"rss.jsp?fid="+fid+"&amp;auth="+rssauth+"\" />\n");
		}
		request.setAttribute("rssauth", rssauth);
		return mapping.findForward("toForumdisplay");
	}
	@SuppressWarnings("unchecked")
	private List<Map<String, String>> updateRecommend(short fid,Map modrecommend, boolean force, int timestamp) {
		int maxlength=Integer.parseInt(modrecommend.get("maxlength").toString());
		List<Map<String, String>> recommendlist = new ArrayList<Map<String, String>>();
		int num = Integer.parseInt(modrecommend.get("num").toString());
		List<Map<String, String>> recommends = dataBaseService.executeQuery("SELECT * FROM jrun_forumrecommend WHERE fid="+ fid + " ORDER BY displayorder LIMIT 0, " + num);
		if (recommends != null) {
			for (Map<String, String> recommend : recommends) {
				int expiration = Integer.parseInt(recommend.get("expiration"));
				if ((expiration > 0 && expiration > timestamp)|| expiration == 0) {
					String subject= recommend.get("subject");
					recommend.put("subject", maxlength>0?Common.cutstr(subject, maxlength):subject);
					recommendlist.add(recommend);
				}
			}
		}
		int cachelife = Integer.parseInt(modrecommend.get("cachelife").toString());
		Integer updatetime=(Integer) modrecommend.get("updatetime");
		if(updatetime==null){
			updatetime=0;
		}
		if ("1".equals(modrecommend.get("sort"))&& (recommendlist.size() == 0|| (timestamp - updatetime) > cachelife || force)) {
			if (recommendlist.size() > 0) {
				dataBaseService.runQuery("DELETE FROM jrun_forumrecommend WHERE fid=" + fid,true);
			}
			recommendlist.clear();
			int dataline = Integer.parseInt(modrecommend.get("dateline").toString());
			String conditions = dataline > 0 ? " AND dateline>"+ (timestamp - dataline * 3600) : "";
			int orderid = Integer.parseInt(modrecommend.get("orderby").toString());
			String orderby = null;
			switch (orderid) {
				case 1:
					orderby = "lastpost"; break;
				case 2:
					orderby = "views"; break;
				case 3:
					orderby = "replies"; break;
				case 4:
					orderby = "digest"; break;
				default:
					orderby = "dateline"; break;
			}
			StringBuffer addthread = new StringBuffer();
			int i = 0;
			List<Map<String, String>> threads = dataBaseService.executeQuery("SELECT fid, tid, author, authorid, subject FROM jrun_threads WHERE fid=" + fid + " AND displayorder>='0' "+ conditions + " ORDER BY " + orderby + " DESC LIMIT 0, "+ num);
			if (threads != null && threads.size() > 0) {
				for (Map<String, String> thread : threads) {
					String subject= thread.get("subject");
					thread.put("subject", maxlength>0&&subject.length()>maxlength?subject.substring(0,maxlength)+" ...":subject);
					recommendlist.add(thread);
					addthread.append("('" + thread.get("fid") + "', '"+ thread.get("tid") + "', '" + i + "', '"+ Common.addslashes(thread.get("subject")) + "', '"+ Common.addslashes(thread.get("author")) + "', '"+ thread.get("authorid") + "', '0', '0')"+", ");
					i++;
				}
			}
			if (addthread.length()>0) {
				dataBaseService.runQuery("REPLACE INTO jrun_forumrecommend (fid, tid, displayorder, subject, author, authorid, moderatorid, expiration) VALUES "+ addthread.substring(0,addthread.length()-2),true);
				modrecommend.put("updatetime", timestamp);
				dataBaseService.runQuery("UPDATE jrun_forumfields SET modrecommend='"+ dataParse.combinationChar(modrecommend)+ "' WHERE fid=" + fid,true);
			}
		}
		return recommendlist;
	}
}