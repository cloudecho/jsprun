package cn.jsprun.struts.foreg.actions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.foreg.vo.archiver.Foot_inc;
import cn.jsprun.foreg.vo.archiver.Forum;
import cn.jsprun.foreg.vo.archiver.Forum_inc;
import cn.jsprun.foreg.vo.archiver.Header_inc;
import cn.jsprun.foreg.vo.archiver.Index_inc;
import cn.jsprun.foreg.vo.archiver.Multi_inc;
import cn.jsprun.foreg.vo.archiver.Thread_inc;
import cn.jsprun.foreg.vo.archiver.WithHeaderAndFoot;
import cn.jsprun.foreg.vo.archiver.Forum_inc.Thread;
import cn.jsprun.foreg.vo.archiver.Index_inc.Forums;
import cn.jsprun.foreg.vo.archiver.Thread_inc.Posts;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
public class Archiver extends BaseAction {
	private final static String tablepre = "jrun_";
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String kw_spiders = "^.*Bot.*|.*Crawl.*|.*Spider.*$";
		String kw_browsers = "^.*MSIE.*|.*Netscape.*|.*Opera.*|.*Konqueror.*|.*Mozilla.*$";
		String kw_searchengines = "^.*google.*|.*yahoo.*|.*msn.*|.*baidu.*|.*yisou.*|.*sogou.*|.*iask.*|.*zhongsou.*|.*sohu.*|.*sina.*|.*163.*$";
		Map<String,String> settingMap = ForumInit.settings;
		Map<String,String> _DCACHE_advsMap = (Map<String,String>)request.getAttribute("advs");
		Map<String,String> forumsMap = (Map<String,String>)request.getAttribute("forums");
		Map<String,Map<String,String>> forumsMap_catch = dataParse.characterParse(forumsMap.get("forums"), false);
		String archiverstatus = settingMap.get("archiverstatus");
		if(archiverstatus==null||archiverstatus.equals("")||archiverstatus.equals("0")){
			request.setAttribute("resultInfo", "Sorry, JspRun! Archiver is not available.");
			return mapping.findForward("showMessage");
		}else if(settingMap.get("bbclosed").equals("1")){
			request.setAttribute("resultInfo", "Sorry, the bulletin board has been closed temporarily.");
			return mapping.findForward("showMessage");
		}
		String path = request.getContextPath();
		String boardurl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String navtitle = "";
		String meta_contentadd = "";
		int fid = 0;
		int page = 0;
		int tid = 0;
		String rewritestatus = settingMap.get("rewritestatus");
		String qm = (Integer.parseInt(rewritestatus) & 16) !=0 ?"":"?";
		Map<String,String> fullversion = new HashMap<String, String>();
		fullversion.put("title", settingMap.get("bbname"));
		fullversion.put("link", settingMap.get("indexname"));
		String fidString = request.getParameter("fid");
		if(fidString!=null){
			fid = Integer.parseInt(fidString);
		}
		String pageStrign = request.getParameter("page");
		if(pageStrign!=null){
			page = Integer.parseInt(pageStrign);
		}
		String tidString = request.getParameter("tid");
		if(tidString!=null){
			tid = Integer.parseInt(tidString);
		}
		String forward = null;
		if(tid!=0){
			forward = "viewthread.jsp?tid="+tid;
		}else if(fid!=0){
			forward = "forumdisplay.jsp?fid="+fid;
		}else {
			forward = "index.jsp";
		}
		int archiverstatus_int = Integer.parseInt(archiverstatus);
		String user_agent = request.getHeader("user-agent");
		String referer = request.getHeader("referer");
		if(archiverstatus_int!=1
				&& (
						user_agent == null
						||
						!user_agent.matches(kw_spiders)	
					)
				&& (
						(archiverstatus_int == 2 && referer!=null &&referer.matches(kw_searchengines)) 
						||
						(archiverstatus_int == 3 && user_agent.matches(kw_browsers))
				   )
		   ){
			response.sendRedirect(boardurl+forward);
			return null;
		}
		String globaladvs = settingMap.get("globaladvs");
		Map<String,Map<String,String>> advList = null;
		Map<String,String> advitems = null;
		if(globaladvs!=null&&!globaladvs.equals("")&&!globaladvs.equals("0")||_DCACHE_advsMap!=null&&_DCACHE_advsMap.size()>0){
			Map<String,Map> advMap = showAdvertisements(_DCACHE_advsMap, settingMap);
			advList = advMap.get("advlist");
			advitems = advMap.get("advitems");
		}
		if(tid!=0){
			Map<String,String> style_catch = (Map<String,String>)request.getAttribute("styles");
			Thread_inc thread_inc = executeThread_inc(tid, page, qm, navtitle, meta_contentadd, boardurl, forumsMap_catch, style_catch, fullversion, settingMap, advList, advitems,(String)request.getSession().getAttribute("timeoffset"),request);
			request.setAttribute("valueObject", thread_inc);
			return mapping.findForward("thread_inc");
		}else if(fid!=0){
			Forum_inc forum_inc = executeForum_inc(fid, page, navtitle, qm, boardurl, meta_contentadd, settingMap, advList,advitems,forumsMap_catch, fullversion,request);
			String redirect = forum_inc.getRedirect();
			if(redirect!=null){
				response.sendRedirect(redirect);
				return null;
			}
			request.setAttribute("valueObject", forum_inc);
			return mapping.findForward("forum_inc");
		}else {
			Index_inc index_inc = executeIndex_inc(settingMap, advList, advitems, fullversion, boardurl, meta_contentadd, navtitle,qm);
			request.setAttribute("valueObject", index_inc);
			return mapping.findForward("index_inc");
		}
	}
	private Index_inc executeIndex_inc(Map<String,String> settingMap,Map<String,Map<String,String>> advList,Map<String,String> advitems,Map<String,String> fullversion,String boardurl,String meta_contentadd,String navtitle,String qm){
		Index_inc index_inc = new Index_inc();
		index_inc.setQm(qm);
		index_inc.setFullversion(fullversion);
		index_inc.setSettingMap(settingMap);
		List<Forums> groupList = index_inc.getGroupList();
		String table = "forums f";
		String cols = "f.fid, f.type, f.name, f.fup, f.simple, ff.viewperm, ff.formulaperm";
		String conditions = "LEFT JOIN "+tablepre+"forumfields ff ON ff.fid=f.fid WHERE f.status=1 ORDER BY f.type, f.displayorder";
		String sql = "SELECT "+cols+" FROM "+tablepre+table+" "+conditions;
		List<Map<String,String>> forumsList = dataBaseService.executeQuery(sql);
		Map<String,List<Map<String,String>>> forums = new HashMap<String, List<Map<String,String>>>();
		Map<String,List<Map<String,String>>> subforums = new HashMap<String, List<Map<String,String>>>();
		List<Map<String,String>> categories = new ArrayList<Map<String,String>>();
		Map<String,String> tempMap = new HashMap<String, String>();
		tempMap.put("fid", "0");
		tempMap.put("name", settingMap.get("bbname"));
		categories.add(tempMap);
		for(Map<String,String> forum : forumsList){
			if(forumperm(forum.get("viewperm"))){
				String type = forum.get("type");
				if(type.equals("group")){
					tempMap = new HashMap<String, String>();
					tempMap.put("fid", forum.get("fid"));
					tempMap.put("name", Common.strip_tags(forum.get("name")));
					categories.add(tempMap);
				}else if(type.equals("sub") ){
					String upForum_fid = forum.get("fup");
					List<Map<String,String>> tempList_sbuForum = subforums.get(upForum_fid);
					if(tempList_sbuForum==null){
						tempList_sbuForum = new ArrayList<Map<String,String>>();
						subforums.put(upForum_fid, tempList_sbuForum);
					}
					tempList_sbuForum.add(forum);
				}else{
					String group_fid = forum.get("fup");
					List<Map<String,String>> tempList_forum = forums.get(group_fid);
					if(tempList_forum==null){
						tempList_forum = new ArrayList<Map<String,String>>();
						forums.put(group_fid, tempList_forum);
					}
					tempList_forum.add(forum);
				}
			}
		}
		for(Map<String,String> category : categories){
			String group_fid = category.get("fid");
			List<Map<String,String>> tempList_forums = forums.get(group_fid);
			if(tempList_forums!=null){
				Forums forums_group = index_inc.getForums();
				forums_group.setName(category.get("name"));
				groupList.add(forums_group);
				List<Forums> forumsList_inGroup = forums_group.getForumList();
				for(Map<String,String> tempMap_forums : tempList_forums){
					String forum_fid = tempMap_forums.get("fid");
					Forums forums_forums = index_inc.getForums();
					forums_forums.setFid(forum_fid);
					forums_forums.setName(Common.strip_tags(tempMap_forums.get("name")));
					forumsList_inGroup.add(forums_forums);
					List<Map<String,String>> tempList_subFroums = subforums.get(forum_fid);
					if(tempList_subFroums!=null){
						List<Forums> subForumsList_inForum = forums_forums.getForumList();
						for(Map<String,String> tempMap_subForum : tempList_subFroums){
							Forums forums_subForums = index_inc.getForums();
							forums_subForums.setFid(tempMap_subForum.get("fid"));
							forums_subForums.setName(Common.strip_tags(tempMap_subForum.get("name")));
							subForumsList_inForum.add(forums_subForums);
						}
					}
				}
			}
		}
		setHeaderAndFooter(index_inc, advList, advitems, settingMap, fullversion, boardurl, meta_contentadd, navtitle);
		return index_inc;
	}
	private Forum_inc executeForum_inc(int fid,int page,String navtitle,String qm,String boardurl,String meta_contentadd,Map<String,String> settingMap ,Map<String,Map<String,String>> advList,Map<String,String> advitems,Map<String,Map<String,String>> forumsMap_catch,Map<String,String> fullversion,HttpServletRequest request){
		Forum_inc forum_inc = new Forum_inc();
		forum_inc.setBbname(settingMap.get("bbname"));
		List<Thread> threadList = forum_inc.getThreadList();
		String sql = "SELECT * FROM "+tablepre+"forums f " +
				"LEFT JOIN "+tablepre+"forumfields ff USING (fid) " +
				"WHERE f.fid="+fid+" AND f.status=1 AND f.type<>'group' AND ff.password=''";
		List<Map<String,String>> forumMapList = dataBaseService.executeQuery(sql);
		if(forumMapList!=null&&forumMapList.size()!=0){
			Map<String,String> forumMap = forumMapList.get(0);
			String redirect = forumMap.get("redirect");
			if(redirect!=null&&!"".equals(redirect)){
				forum_inc.setRedirect(redirect);
				return forum_inc;
			}
			if(forumperm(forumMap.get("viewperm"))&&forumformulaperm(forumMap.get("formulaperm"))){
				Forum currentForum = new Forum();
				currentForum.setFid(fid+"");
				currentForum.setName(forumMap.get("name"));
				forum_inc.setCurrentForum(currentForum);
				boolean navsub = forumMap.get("type").equals("sub");
				if(navsub){
					String superFid = forumMap.get("fup");
					String superForumName = Common.strip_tags(forumsMap_catch.get(superFid).get("name"));
					navtitle = " - "+superForumName;
					Forum superForum = new Forum();
					superForum.setFid(superFid);
					superForum.setName(superForumName);
					forum_inc.setSuperForum(superForum);
				}
				page = Math.max(1, page);
				navtitle += Common.strip_tags(forumMap.get("name"))+"("+getMessage(request, "page")+" "+page+") - ";
				fullversion.put("title", forumMap.get("name"));
				fullversion.put("link", "forumdisplay.jsp?fid="+fid);
				int tpp = Integer.parseInt(settingMap.get("topicperpage")) *2;
				int start = (page - 1) * tpp;
				sql = "SELECT * FROM "+tablepre+"threads WHERE fid='"+fid+"' AND displayorder>='0' ORDER BY displayorder DESC, lastpost DESC LIMIT "+start+", "+tpp;
				List<Map<String,String>> threadMapList = dataBaseService.executeQuery(sql);
				if(threadMapList!=null){
					for(Map<String,String> threadMap : threadMapList){
						Thread thread = forum_inc.getThread();
						thread.setReplies(threadMap.get("replies"));
						thread.setSubject(threadMap.get("subject"));
						thread.setTid(threadMap.get("tid"));
						threadList.add(thread);
					}
				}
				forum_inc.setFullversion(fullversion);
				forum_inc.setNavsub(navsub);
				forum_inc.setQm(qm);
				forum_inc.setStart(start);
				Multi_inc multi_inc = new Multi_inc(page,10,Integer.valueOf(forumMap.get("threads")),tpp,qm+"fid-"+fid);
				forum_inc.setMulti_inc(multi_inc);
			}else{
				forum_inc.setRefuse(true);
			}
		}else{
			forum_inc.setRefuse(true);
		}
		setHeaderAndFooter(forum_inc, advList, advitems, settingMap, fullversion, boardurl, meta_contentadd, navtitle);
		return forum_inc;
	}
	private Thread_inc executeThread_inc(int tid,int page,String qm,String navtitle,String meta_contentadd,String boardurl,Map<String,Map<String,String>> forumsMap_catch,Map<String,String> style_catch,Map<String,String> fullversion,Map<String,String> settingMap,Map<String,Map<String,String>> advList,Map<String,String> advitems,String timeoffset,HttpServletRequest request){
		Thread_inc thread_inc = new Thread_inc();
		String sql = "SELECT * FROM "+tablepre+"threads t " +
				"LEFT JOIN "+tablepre+"forums f ON f.fid=t.fid " +
				"LEFT JOIN "+tablepre+"forumfields ff ON ff.fid=f.fid " +
				"WHERE t.tid="+tid+" AND t.readperm='0' AND t.price<=0 AND t.displayorder>=0 " +
				"AND f.status=1 AND ff.password=''";
		List<Map<String,String>> t_f_ff_MapList = dataBaseService.executeQuery(sql);
		page = Math.max(1, page);
		if(t_f_ff_MapList!=null&&t_f_ff_MapList.size()>0){
			Map<String,String> t_f_ff_Map = t_f_ff_MapList.get(0);
			String viewperm = t_f_ff_Map.get("viewperm");
			String formulaperm = t_f_ff_Map.get("formulaperm");
			Forum currentForum = new Forum();
			currentForum.setFid(t_f_ff_Map.get("fid"));
			currentForum.setName(t_f_ff_Map.get("name"));
			String threadSubject = t_f_ff_Map.get("subject");
			if((viewperm==null||viewperm.equals("") || (forumperm(viewperm))) || !forumformulaperm(formulaperm)){
				navtitle = threadSubject+"("+getMessage(request, "page")+" "+page+") ";
				boolean navsub = t_f_ff_Map.get("type").equals("sub");
				if(navsub){
					String superForumId = t_f_ff_Map.get("fup");
					String superForumName = Common.strip_tags(forumsMap_catch.get(superForumId).get("name"));
					Forum superForum = new Forum();
					superForum.setFid(superForumId);
					superForum.setName(superForumName);
					thread_inc.setSuperForum(superForum);
					navtitle += " - "+superForumName;
				}
				navtitle += " - "+Common.strip_tags(t_f_ff_Map.get("name"))+" - ";
				fullversion.put("title", threadSubject);
				fullversion.put("link", "viewthread.jsp?tid="+tid);
				int ppp = Integer.parseInt(settingMap.get("postperpage")) * 2;
				int start = (page - 1) * ppp;
				sql = "SELECT p.pid, p.author, p.dateline, p.subject, p.message, p.anonymous, p.status, m.groupid " +
						"FROM "+tablepre+"posts p " +
						"LEFT JOIN "+tablepre+"members m ON p.authorid=m.uid " +
						"WHERE p.tid='"+tid+"' AND p.invisible='0' " +
						"ORDER BY dateline LIMIT "+start+", "+ppp;
				List<Map<String,String>> post_memberMapList = dataBaseService.executeQuery(sql);
				if(post_memberMapList!=null&&post_memberMapList.size()>0){
					Map<String,String> post_member_thread = post_memberMapList.get(0);
					String tempGroupid = post_member_thread.get("groupid");
					if(tempGroupid==null||tempGroupid.equals("4")||tempGroupid.equals("5")||tempGroupid.equals("6")){
						post_member_thread.put("message", getMessage(request, "post_banned"));
					}else if(post_member_thread.get("status").equals("1")){
						post_member_thread.put("message", getMessage(request, "post_single_banned"));
					}
					meta_contentadd = Common.cutstr(Common.strip_tags(post_member_thread.get("message").replace("\r", "").replace("\n", "").replace("\t", "")), 200);
					String dateFormat = settingMap.get("dateformat");
					String timeFormat = settingMap.get("timeformat");
					List<Posts> postsList = thread_inc.getPostsList();
					SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat(dateFormat+" "+timeFormat, timeoffset);
					for(Map<String,String> post_memberMap : post_memberMapList){
						Posts posts = thread_inc.getPosts();
						tempGroupid = post_memberMap.get("groupid");
						if(tempGroupid==null||tempGroupid.equals("4")||tempGroupid.equals("5")||tempGroupid.equals("6")){
							post_memberMap.put("message", getMessage(request, "post_banned"));
						}else if(post_memberMap.get("status").equals("1")){
							post_memberMap.put("message", getMessage(request, "post_single_banned"));
						}
						String dateline = Common.gmdate(simpleDateFormat, Integer.parseInt(post_memberMap.get("dateline")));
						String postSubject = post_memberMap.get("subject");
						String message = post_memberMap.get("message");
						String tempMessage = "";
						if(postSubject!=null&&!postSubject.equals("")){
							tempMessage = "<h2>"+postSubject+"</h2>";
						}
						message = message.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("\t", "&nbsp; &nbsp; &nbsp; &nbsp; ").replace("   ", "&nbsp; &nbsp;").replace("  ", "&nbsp;&nbsp;");
						message = message.replaceAll("^&amp;(#\\d{3,5};)$", "&\\1").replaceAll("\\[hide=?\\d*\\](.+?)\\[\\/hide\\]", "<b>**** Hidden Message *****</b>");
						message = tempMessage + Common.nl2br(message);
						String jammer = t_f_ff_Map.get("jammer");
						if(jammer!=null&&!jammer.equals("")&&!jammer.equals("0")){
							message = message.replaceAll("^\\<br \\/\\>$", jammer(style_catch.get("ALTBG2")));
						}
						String anonymous = post_memberMap.get("anonymous");
						String author = post_memberMap.get("author");
						if(anonymous==null||Integer.parseInt(anonymous)>0){
							author = getMessage(request, "anonymous");
						}
						posts.setAuthor(author);
						posts.setDateline(dateline);
						posts.setMessage(message);
						postsList.add(posts);
					}
				}
				thread_inc.setBbname(settingMap.get("bbname"));
				thread_inc.setCurrentForum(currentForum);
				thread_inc.setNavsub(navsub);
				thread_inc.setQm(qm);
				thread_inc.setThreadSubject(threadSubject);
				thread_inc.setFullversion(fullversion);
				setHeaderAndFooter(thread_inc, advList, advitems, settingMap, fullversion, boardurl, meta_contentadd, navtitle);
				Multi_inc multi_inc = new Multi_inc(page,10,Integer.valueOf(t_f_ff_Map.get("replies")),ppp,qm+"tid-"+tid);
				thread_inc.setMulti_inc(multi_inc);
			}else{
				thread_inc.setRefuse(true);
			}
		}else{
			thread_inc.setRefuse(true);
		}
		return thread_inc;
	}
	private void setHeaderAndFooter(WithHeaderAndFoot withHeaderAndFoot,Map<String,Map<String,String>> advList,Map<String,String> advitems,Map<String,String> settingMap,Map<String,String> fullversion,String boardurl,String meta_contentadd,String navtitle){
		Header_inc header_inc = new Header_inc();
		header_inc.setBoardurl(boardurl);
		header_inc.setMeta_contentadd(meta_contentadd);
		header_inc.setNavtitle(navtitle);
		header_inc.setSettingMap(settingMap);
		Foot_inc foot_inc = new Foot_inc();
		foot_inc.setFullversion(fullversion);
		foot_inc.setVersion(settingMap.get("version"));
		if(advList != null && advitems!=null){
			Map<String,String> temp = advList.get("headerbanner");
			if(temp!=null){
				String headerbanner = advitems.get(temp.get("0"));
				if(headerbanner==null){
					headerbanner = advitems.get(temp.get("all"));
				}
				header_inc.setHeaderbanner(headerbanner);
			}
			temp = advList.get("footerbanner1");
			if(temp!=null){
				String footerbanner1 = advitems.get(temp.get("0"));
				if(footerbanner1==null){
					footerbanner1 = advitems.get(temp.get("all"));
				}
				foot_inc.setFooterbanner1(footerbanner1);
			}
			temp = advList.get("footerbanner2");
			if(temp!=null){
				String footerbanner2 = advitems.get(temp.get("0"));
				if(footerbanner2==null){
					footerbanner2 = advitems.get(temp.get("all"));
				}
				foot_inc.setFooterbanner2(footerbanner2);
			}
			temp = advList.get("footerbanner3");
			if(temp!=null){
				String footerbanner3 = advitems.get(temp.get("0"));
				if(footerbanner3==null){
					footerbanner3 = advitems.get(temp.get("all"));
				}
				foot_inc.setFooterbanner3(footerbanner3);
			}
		}
		withHeaderAndFoot.setHeader(header_inc);
		withHeaderAndFoot.setFooter(foot_inc);
	}
	private boolean forumperm(String viewperm){
		return  viewperm.equals("")  || viewperm.indexOf("\t7\t") >=0;
	}
	private boolean forumformulaperm(String formula){
		if(formula==null||formula.equals("")){
			return true;
		}
		Map<Integer,String> formulaMap = dataParse.characterParse(formula, false);
		formula = formulaMap.get(1);
		if(formula==null||formula.equals("")||formula.equals("0")){
			return true;
		}
		formula = formula.replaceAll("\\$_DSESSION\\['((extcredits[1-8])|(posts)|(oltime)|(digestposts)|(pageviews))'\\]", "0").trim();
		int equalMark = formula.indexOf("=");
		int gtMark = formula.indexOf(">");
		int ltMark = formula.indexOf("<");
		if((equalMark<0&&gtMark<0&&ltMark<0)||(equalMark>=0)&&(gtMark>=0)&&(ltMark>=0)){
			return false;
		}else{
			String[] expiressions = null;
			String sign = "";
			if(gtMark>0){
				equalMark = formula.indexOf("=",gtMark+1);
				if(equalMark>0){
					expiressions = formula.split(">\\s*=");
					sign=">=";
				}else{
					expiressions = formula.split(">");
					sign=">";
				}
			}else if(ltMark>0){
				equalMark = formula.indexOf("=",ltMark+1);
				if(equalMark>0){
					expiressions = formula.split("<\\s*=");
					sign="<=";
				}else{
					expiressions = formula.split("<");
					sign="<";
				}
			}else{
				int secEqualMark = formula.indexOf("=",equalMark+1);
				if(secEqualMark>0){
					expiressions = formula.split("=\\s*=");
					sign="==";
				}else {
					return true;
				}
			}
			double r1 = Double.parseDouble(excute(new StringBuffer(expiressions[0].replace(" ", ""))));
			double r2 = Double.parseDouble(excute(new StringBuffer(expiressions[1].replace(" ", ""))));
			if(sign.equals(">=")){
				return r1 >= r2;
			}else if(sign.equals(">")){
				return r1 > r2;
			}else if(sign.equals("<=")){
				return r1 <= r2;
			}else if(sign.equals("<")){
				return r1 < r2;
			}else{
				return r1 == r2;
			}
		}
	}
	private String jammer(String ALTBG2){
		StringBuffer randomStr = new StringBuffer();
		for(int i = 0;i<Common.rand(5, 15);i++){
			randomStr.append((char)Common.rand(0, 59));
			randomStr.append((char)Common.rand(63, 126));
		}
		if(Common.rand(1)>0){
			return "<font style=\"font-size:0px;color:"+ALTBG2+"\">"+randomStr+"</font><br />";
		}else{
			return "<br /><span style=\"display:none\">"+randomStr+"</span>";
		}
	}
	private String excute(StringBuffer expiression){
		String tempS;
		if(expiression==null||(tempS = expiression.toString()).equals("") || tempS.equals("0")){
			return "0";
		}
		int indexF = expiression.lastIndexOf("(");
		if(indexF<0){
			return count(expiression.toString());
		}else{
			int indexA = expiression.indexOf(")", indexF); 
			return excute(expiression.replace(indexF, indexA+1, count(expiression.substring(indexF+1, indexA)))); 
		}
	}
	private String count(String expiression){
		StringBuffer tempBuffer = null;
		int indexM = expiression.indexOf("*");
		if(indexM<0){ 
			int indexD = expiression.indexOf("/");
			if(indexD<0){ 
				int indexA = expiression.indexOf("+");
				if(indexA<0){
					int indexS = expiression.indexOf("-");
					if(indexS>0){
						tempBuffer = getResult(indexS, expiression, "-");
					}else if(indexS==0){
						StringBuffer buffer = new StringBuffer(expiression);
						indexS = buffer.indexOf("-", 1);
						if(indexS>1){
							tempBuffer = getResult(indexS, expiression, "-");
						}else if(indexS==1){ 
							tempBuffer = new StringBuffer(buffer.replace(0, 2, "").toString());
						}else { 
							tempBuffer = buffer;
						}
					}else{
						return expiression;
					}
				}else{
					tempBuffer = getResult(indexA, expiression, "+");
				}
			}else{
				tempBuffer = getResult(indexD, expiression, "/");
			}
		}else{
			tempBuffer = getResult(indexM, expiression, "*");
		}
		if(tempBuffer.indexOf("*")>0
				||tempBuffer.indexOf("/")>0
				||tempBuffer.indexOf("+")>0
				||tempBuffer.indexOf("-")>0
				||(tempBuffer.indexOf("-")==0
						&&tempBuffer.indexOf("-",1)>0)){
			return count(tempBuffer.toString());
		}else{
			return tempBuffer.toString();
		}
	}
	private StringBuffer getResult(int index,String expiression,String sign){
		StringBuffer tempBuffer = new StringBuffer(expiression);
		String leftNumber = getLeftNumber(index, expiression);
		String rightNumber = getRightNumber(index, expiression);
		Double result = null;
		if(sign.equals("*")){
			result = Double.valueOf(leftNumber)*Double.valueOf(rightNumber);
		}else if(sign.equals("/")){
			result = Double.valueOf(leftNumber)/Double.valueOf(rightNumber);
		}else if(sign.equals("+")){
			result = Double.valueOf(leftNumber)+Double.valueOf(rightNumber);
		}else if(sign.equals("-")){
			result = Double.valueOf(leftNumber)-Double.valueOf(rightNumber);
		}
		int firstIndexOfLeftNumber = index-leftNumber.length();
		int lastIndexOfRightNumber = index+rightNumber.length();
		tempBuffer.replace(firstIndexOfLeftNumber, lastIndexOfRightNumber+1, result.toString());
		return tempBuffer;
	}
	private String getLeftNumber(int singIndex,String expiression){
		StringBuffer tempBuffer = new StringBuffer();
		for(int i = singIndex-1;i>-1;i--){
			char temp = expiression.charAt(i);
			if(temp==' '){
				continue;
			}
			if(Character.isDigit(temp)||temp=='.'){
				tempBuffer.insert(0, temp);
			}else {
				if(temp=='-'&&(i==0||!Character.isDigit(expiression.charAt(i-1)))){
					tempBuffer.insert(0, temp);
				}else{
					break;
				}
			}
		}
		return tempBuffer.toString();
	}
	private String getRightNumber(int singIndex,String expiression){
		StringBuffer tempBuffer = new StringBuffer();
		for(int i = singIndex+1;i<expiression.length();i++){
			char temp = expiression.charAt(i);
			if(temp==' '){
				continue;
			}
			if(i==singIndex+1&&temp=='-'){
				tempBuffer.append(temp);
				continue;
			}
			if(Character.isDigit(temp)||temp=='.'){
				tempBuffer.append(temp);
			}else {
				break;
			}
		}
		return tempBuffer.toString();
	}
	private Map<String,Map> showAdvertisements(Map<String,String> _DCACHE_advsMap,Map<String,String> settings){
		Map<String,Map> resultMap = new HashMap<String, Map>();
		Map globaladvs=dataParse.characterParse(settings.get("globaladvs"),false);
		Map<String,Map<String,String>> advarray=new HashMap<String, Map<String,String>>();
		Map<String,String> advitems=new HashMap<String, String>();
		if(_DCACHE_advsMap!=null){
			Map _DCACHE_advs =dataParse.characterParse(_DCACHE_advsMap.get("advs"),false);
			_DCACHE_advsMap=null;
			Map<String,Map<String,String>> advs=(Map<String,Map<String,String>>)_DCACHE_advs.get("type");
			advitems=(Map<String,String>)_DCACHE_advs.get("items");
			if(advitems==null){
				advitems=new HashMap<String, String>();
			}
			if(globaladvs!=null&&globaladvs.size()>0){
				if(advs==null){
					advs=new HashMap<String,Map<String,String>>();
				}
				Map<String,Map<String,String>> types=(Map<String,Map<String,String>>)globaladvs.get("type");
				if(types!=null){
					Set<Entry<String,Map<String,String>>> keys=types.entrySet();
					for (Entry<String,Map<String,String>> temp : keys) {
						String type = temp.getKey();
						Map<String,String> advitem=advs.get(type);
						Map<String,String> typeitems=temp.getValue();
						if(advitem!=null&&advitem.size()>0){
							Set<String> objs=advitem.keySet();
							for (String obj : objs) {
								String advids=advitem.get(obj.trim());
								String advid=typeitems.get("all");
								if(advids!=null){
									advids+=","+advid;
									advitem.put(obj.trim(), advids);
								}else{
									advitem.put(obj.trim(), advid);
								}
							}
						}else{
							advitem=new HashMap<String, String>();
							advitem.putAll(typeitems);
						}
						advs.put(type, advitem);
					}
					advitems.putAll((Map<String,String>)globaladvs.get("items"));
				}
				types=null;
			}
			advarray=advs;
			advs=null;
		}
		else{
			if(globaladvs!=null&&globaladvs.size()>0){
				advarray=(Map<String,Map<String,String>>)globaladvs.get("type");
				advitems =(Map<String,String>)globaladvs.get("items");
				if(advitems==null){
					advitems=new HashMap<String, String>();
				}
			}
		}
		globaladvs=null;
		Map<String,Map<String,String>> advlist=new HashMap<String, Map<String,String>>();
		if(advarray!=null&&advarray.size()>0){
			Map<String,Map<Integer,String>> advthreads=new HashMap<String, Map<Integer,String>>();
			Iterator<Entry<String,Map<String,String>>> keys =advarray.entrySet().iterator();
			while (keys.hasNext()) {
				Entry<String,Map<String,String>> temp = keys.next();
				String advtype = temp.getKey();
				Map<String,String> advcodes=temp.getValue();
				Set<String> objs=advcodes.keySet();
				if(advtype.length()>5&&advtype.substring(0,6).equals("thread")){
					Map<Integer,String> advtypes=advthreads.get(advtype);
					if(advtypes==null){
						advtypes=new HashMap<Integer, String>();
					}
					int ppp=Common.toDigit(settings.get("wapppp"));
					for (int i = 1; i <= ppp; i++) {
						String advid=advcodes.get(String.valueOf(i));
						if(advid==null){
							advid=advcodes.get("0");
						}
						if(advid!=null){
							String[] advids=advid.split(",");
							advtypes.put(i-1, advitems.get(advids[Common.rand(advids.length-1)]));
						}
					}
					advthreads.put(advtype, advtypes);
				}
				else if("intercat".equals(advtype)){
					for (String obj : objs) {
						String[] advid=advcodes.get(obj).split(",");
						advcodes.put(obj, advid[Common.rand(advid.length-1)]);
					}
					advlist.put("intercat",advcodes);
				}else{
					if("text".equals(advtype)){
						for (String obj : objs) {
							String[] advids=advcodes.get(obj).split(",");
							int advcols=0;
							int advcount=advids.length;
							if(advcount>5){
								int minfillpercent=0;
								for (int cols = 5; cols >=3; cols--) {
									int remainder=advcount%cols;
									if(remainder==0){
										advcols=cols;
										break;
									}
									else if(remainder/cols>minfillpercent){
										minfillpercent=remainder / cols;
										advcols=cols;
									}
								}
							}else{
								advcols=advcount;
							}
							StringBuffer advtypestr=new StringBuffer();
							int size = (int)(advcols * Math.ceil(advcount / (double)advcols));
							for (int i = 0; i < size; i++) {
								advtypestr.append(((i + 1) % advcols == 1 || advcols == 1 ? "<tr>" : "")+"<td width=\""+(100 / advcols)+"%\">"+(advids[i]!=null? advitems.get(advids[i]) : "&nbsp;")+"</td>"+((i + 1) % advcols == 0 ? "</tr>\n" :""));
							}
							advcodes.put(obj, advtypestr.toString());
						}
						advlist.put(advtype, advcodes);
					}
					else{
						for (String obj : objs) {
							String[] advid=advcodes.get(obj).split(",");
							advcodes.put(obj, advid[Common.rand(advid.length-1)]);
						}
						advlist.put(advtype, advcodes);
					}
				}
			}
		}
		resultMap.put("advlist", advlist);
		resultMap.put("advitems", advitems);
		return resultMap;
	}
}
