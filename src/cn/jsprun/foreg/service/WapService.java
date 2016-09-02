package cn.jsprun.foreg.service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.util.MessageResources;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.domain.Members;
import cn.jsprun.foreg.vo.wap.FooterVO;
import cn.jsprun.foreg.vo.wap.ForumInfo;
import cn.jsprun.foreg.vo.wap.Forums_threadsVO;
import cn.jsprun.foreg.vo.wap.GoToVo;
import cn.jsprun.foreg.vo.wap.HeaderVO;
import cn.jsprun.foreg.vo.wap.HomeVO;
import cn.jsprun.foreg.vo.wap.LoginVO;
import cn.jsprun.foreg.vo.wap.MessageVO;
import cn.jsprun.foreg.vo.wap.MyCollectionVO;
import cn.jsprun.foreg.vo.wap.MyPhoneVO;
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
import cn.jsprun.foreg.vo.wap.ThreadInfo;
import cn.jsprun.foreg.vo.wap.ThreadVO;
import cn.jsprun.foreg.vo.wap.HomeVO.Forum;
import cn.jsprun.foreg.vo.wap.PmListVO.PmInfo;
import cn.jsprun.foreg.vo.wap.ThreadVO.PostsInfo;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
public class WapService {
	private final static String tablePre = "jrun_";
	public boolean validateWap(HttpServletRequest request,HttpServletResponse response,Map<String,String> settingMap,Members memberInSession,String formhashInSession,String sid,MessageResources mr,Locale locale){
		String user_agent = request.getHeader("user-agent");
		if(user_agent != null && user_agent.toLowerCase().matches(".*(mozilla|m3gate|winwap|openwave).*")) {
			String boardUrl = (String)request.getSession().getAttribute("boardurl");
			try {
				response.sendRedirect(boardUrl+"index.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		String action = request.getParameter("action");
		action = action == null ? "home" : action;
		if(action.equals("goto")){
			String url = request.getParameter("url");
			if(url!=null&&!url.equals("")){
				try {
					response.sendRedirect(url);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		}
		if(settingMap.get("wapstatus").equals("0")){
			forwardToMessage(request, response, settingMap, mr.getMessage(locale, "wap_disabled"), null, settingMap.get("bbname"), memberInSession,action,  formhashInSession,sid);
			return false;
		}
		if(!settingMap.get("bbclosed").equals("0")){
			forwardToMessage(request, response, settingMap, mr.getMessage(locale, "board_closed_wap"), null, settingMap.get("bbname"), memberInSession,action,  formhashInSession,sid);
			return false;
		}
		if(!action.equals("home")&&!action.equals("login")&&!action.equals("register")&&!action.equals("search")
				&&!action.equals("stats")&&!action.equals("my")&&!action.equals("myphone")&&!action.equals("goto")
				&&!action.equals("forum")&&!action.equals("thread")&&!action.equals("post")&&!action.equals("pm")){
			forwardToMessage(request, response, settingMap, mr.getMessage(locale, "undefined_action"), null, settingMap.get("bbname"), memberInSession,action,  formhashInSession,sid);
			return false;
		}
		return true;
	}
	public HomeVO getHomeVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String allowsearch,String formhash,String sid){
		HomeVO homeVO = new HomeVO();
		setHeaderVO(homeVO.getHeaderVO(), settingMap.get("bbname"));
		setFooterVO(request, settingMap, homeVO.getFooterVO(), currentMember, "home", formhash,sid);
		homeVO.setIsLogin(currentMember!=null);
		homeVO.setSid(sid);
		homeVO.setBbname(settingMap.get("bbname"));
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		String sql = null;
		if(currentMember!=null){
			if(allowsearch!=null&&!allowsearch.equals("")&&!allowsearch.equals("0")){
				homeVO.setAllowsearch(true);
				int timestamp = (Integer)request.getAttribute("timestamp");
				int lastvisit =  currentMember.getLastvisit();
				int newthreads = Math.round((timestamp - lastvisit + 600) / 1000F) * 1000;
				homeVO.setNewthreads(newthreads);
			}
			byte accessmasks = currentMember.getAccessmasks();
			if(accessmasks>0){
				sql = "SELECT f.fid, f.name, ff.viewperm, a.allowview FROM "+tablePre+"forums f " +
						"LEFT JOIN "+tablePre+"forumfields ff ON ff.fid=f.fid " +
						"LEFT JOIN "+tablePre+"access a ON a.uid='"+currentMember.getUid()+"' AND a.fid=f.fid " +
						"WHERE f.status=1 AND f.type='forum' ORDER BY f.displayorder,f.fid";
			}
		}
		if(sql==null){
			sql = "SELECT f.fid, f.name, ff.viewperm FROM "+tablePre+"forums f " +
					"LEFT JOIN "+tablePre+"forumfields ff USING(fid) " +
					"WHERE f.status=1 AND f.type='forum' ORDER BY f.displayorder,f.fid";
		}
		List<Map<String,String>> forumInfoMapList = dataBaseDao.executeQuery(sql);
		if(forumInfoMapList!=null){
			List<Forum> forumList = homeVO.getForumList();
			short groupid = currentMember!=null?currentMember.getGroupid():7;
			int sign = 1;
			for(Map<String,String> forumInfoMap : forumInfoMapList){
				if(forumInfoMap!=null){
					String viewperm = forumInfoMap.get("viewperm");
					if(viewperm.equals("") || ("\t"+viewperm+"\t").indexOf("\t"+groupid+"\t") >=0 ){
						Forum forum = homeVO.getForum();
						forum.setFid(forumInfoMap.get("fid"));
						forum.setName(Common.strip_tags(Common.htmlspecialchars(forumInfoMap.get("name"))));
						forumList.add(forum);
						if(sign++ == 10){
							break;
						}
					}
				}
			}
			homeVO.setExistMoreForum(sign>10);
		}
		int onlinenum=Integer.parseInt(dataBaseDao.executeQuery("SELECT COUNT(*) as count FROM jrun_sessions").get(0).get("count"));
		int guestCount=Integer.parseInt(dataBaseDao.executeQuery("SELECT COUNT(*) as count FROM jrun_sessions WHERE uid = 0").get(0).get("count"));
		int memberCount = onlinenum - guestCount;
		homeVO.setGuestCount(guestCount);
		homeVO.setMemberCount(memberCount);
		return homeVO;
	}
	public LoginVO getLoginVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid,boolean failedAnswer,String username,String loginauth){
		LoginVO loginVO = new LoginVO();
		loginVO.setSid(sid);
		loginVO.setFailedAnswer(failedAnswer);
		loginVO.setUsername(username);
		loginVO.setLoginauth(loginauth);
		setHeaderVO(loginVO.getHeaderVO(), settingMap.get("bbname"));
		setFooterVO(request, settingMap, loginVO.getFooterVO(), currentMember, "login", formhash,sid);
		return loginVO;
	}
	public boolean loginCheck(String onlineIp,int timestamp){
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>> flMapList = dataBaseDao.executeQuery("SELECT count, lastupdate FROM "+tablePre+"failedlogins WHERE ip=?",onlineIp);
		int count = 0;
		if(flMapList!=null && flMapList.size()>0){
			Map<String,String> flMap = flMapList.get(0);
			int lastupdate = Integer.parseInt(flMap.get("lastupdate"));
			if(timestamp - lastupdate > 900){
				count = 4;
			}else{
				count = Math.max(0, Integer.parseInt(flMap.get("count")));
			}
		}else{
			count = 4;
		}
		if(count == 4){
			dataBaseDao.execute("REPLACE INTO "+tablePre+"failedlogins (ip, count, lastupdate) VALUES (?, '1', '"+timestamp+"')",onlineIp);
			dataBaseDao.executeDelete("DELETE FROM "+tablePre+"failedlogins WHERE lastupdate<"+(timestamp-901));
		}
		return count!=0;
	}
	public void forwardToMessage(HttpServletRequest request,HttpServletResponse response,Map<String,String> settingMap, String message,Map<String,String> forwardMap,String headerVO_title,Members currentMembers,String action,String formhash,String sid){
		MessageVO messageVO = new MessageVO();
		setMessageVO(request,response,settingMap,messageVO, message, forwardMap, headerVO_title,currentMembers,action, formhash,sid);
		request.setAttribute("valueObject", messageVO);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/wap/include/message.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public RegisterVO getRegisterVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid){
		RegisterVO registerVO = new RegisterVO();
		setHeaderVO(registerVO.getHeaderVO(), settingMap.get("bbname"));
		setFooterVO(request, settingMap, registerVO.getFooterVO(), currentMember, "register", formhash,sid);
		return registerVO;
	}
	public Forums_threadsVO getPartOfF(HttpServletRequest request,HttpServletResponse response,String pageString,Map<String,String> settingMap,Members currentMember,int jsprun_uid,short groupid,String formhash,String sid,MessageResources mr,Locale locale,String timeoffset){
		Forums_threadsVO forums_threadsVO = new Forums_threadsVO();
		setHeaderVO(forums_threadsVO.getHeaderVO(), settingMap.get("bbname"));
		setFooterVO(request, settingMap, forums_threadsVO.getFooterVO(), currentMember, "forum", formhash, sid);
		forums_threadsVO.setShowForum(true);
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		int page = 1;
		if(pageString != null && !pageString.equals("")){
			try{
				page = Math.max(1, Integer.parseInt(pageString));
			}catch(NumberFormatException exception){
				exception.printStackTrace();
			}
		}
		int waptpp = Integer.parseInt(settingMap.get("waptpp"));
		int start_limit = (page-1)*waptpp;
		String query = "SELECT COUNT(*) AS ct FROM "+tablePre+"forums WHERE status=1 AND type='forum'";
		int forumcount = 0;
		List<Map<String,String>> resultTemp = dataBaseDao.executeQuery(query);
		if(resultTemp!=null&&resultTemp.size()>0){
			forumcount = Integer.parseInt(resultTemp.get(0).get("ct"));
		}
		byte accessmasks = currentMember != null ? currentMember.getAccessmasks() : 0;
		String sql =  accessmasks > 0 ? "SELECT f.fid, f.name, f.lastpost, ff.viewperm, ff.moderators, ff.icon, a.allowview FROM "+tablePre+"forums f " +
				"LEFT JOIN "+tablePre+"forumfields ff ON ff.fid=f.fid " +
						"LEFT JOIN "+tablePre+"access a ON a.uid='"+jsprun_uid+"' AND a.fid=f.fid " +
								"WHERE f.status=1 AND f.type='forum' ORDER BY f.displayorder,f.fid LIMIT "+start_limit+", "+waptpp
			: "SELECT f.fid, f.name, f.lastpost, ff.viewperm, ff.moderators, ff.icon FROM "+tablePre+"forums f " +
					"LEFT JOIN "+tablePre+"forumfields ff USING(fid) " +
					"WHERE f.status=1 AND f.type='forum' ORDER BY f.displayorder,f.fid LIMIT "+start_limit+", "+waptpp;
		List<Map<String,String>> forumMapList = dataBaseDao.executeQuery(sql);
		if(forumMapList!=null){
			List<ForumInfo> forumList = forums_threadsVO.getForumList();
			String hideprivate=settingMap.get("hideprivate");
			int lastvisit=currentMember!=null?currentMember.getLastvisit():0;
			String extgroupid=currentMember!=null?currentMember.getExtgroupids():null;
			Map<String, Map<String, String>> lastposts = new TreeMap<String, Map<String, String>>();
			String timeformat=settingMap.get("timeformat");
			if(timeformat.equals("1")){
				timeformat = "hh:mm a";
			}else{
				timeformat = "HH:mm";
			}
			String dateformat=settingMap.get("dateformat");
			SimpleDateFormat sdf_all = Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for(Map<String,String> forumMap : forumMapList){
				String viewperm = forumMap.get("viewperm");
				if(Common.forum(forumMap, hideprivate, groupid, lastvisit, extgroupid, lastposts,sdf_all)&&
						(viewperm.equals("")||("\t"+viewperm.trim()+"\t").contains("\t"+groupid+"\t"))){
					ForumInfo forumInfo = forums_threadsVO.getForumInfo();
					forumInfo.setFid(forumMap.get("fid"));
					forumInfo.setName(Common.strip_tags(Common.htmlspecialchars(forumMap.get("name"))));
					forumList.add(forumInfo);
				}
			}
		}
		forums_threadsVO.setMultipage(wapmulti(forumcount, waptpp, page, response.encodeURL("index.jsp?action=forum"),mr,locale));
		return forums_threadsVO;
	}
	public Forums_threadsVO getPartOfT(HttpServletRequest request,HttpServletResponse response,String pageString,String dow,short fid,short groupid,Map<String,String> settingMap,Map<String,Map<String,String>> forums,Map<String,String> groupCache,Members currentMember,String formhash,String sid,MessageResources mr,Locale locale){
		Forums_threadsVO forums_threadsVO = new Forums_threadsVO();
		setHeaderVO(forums_threadsVO.getHeaderVO(), settingMap.get("bbname"));
		setFooterVO(request, settingMap, forums_threadsVO.getFooterVO(), currentMember, "forum", formhash, sid);
		List<ThreadInfo> threadList = forums_threadsVO.getThreadList();
		Map<String,String> forum = forums.get(fid+"");
		forums_threadsVO.setForumName(forum.get("name"));
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		int waptpp = Integer.parseInt(settingMap.get("waptpp"));
		int page = 1;
		if(pageString != null && !pageString.equals("")){
			try{
				page = Math.max(1, Integer.parseInt(pageString));
			}catch(NumberFormatException exception){
				exception.printStackTrace();
			}
		}
		int start_limit = (page-1)*waptpp;
		int number = start_limit;
		dow = dow==null||dow.equals("")||dow.equals("0")?"":"digest";
		String filteradd = dow.equals("digest") ? "AND digest>'0'" : "";
		int threadcount = 0;
		List<Map<String,String>> tempResult = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tablePre+"threads WHERE fid='"+fid+"' "+filteradd+" AND displayorder>='0'");
		if(tempResult!=null&&tempResult.size()>0){
			threadcount = Integer.parseInt(tempResult.get(0).get("ct"));
		}
		String thread_prefix = "";
		List<Map<String,String>> threadMapList = dataBaseDao.executeQuery("SELECT * FROM "+tablePre+"threads " +
				"WHERE fid='"+fid+"' "+filteradd+" AND displayorder>='0' " +
				"ORDER BY displayorder DESC, lastpost DESC LIMIT "+start_limit+", "+waptpp);
		if(threadMapList!=null&&threadMapList.size()>0){
			for(Map<String,String> thread : threadMapList){
				thread_prefix = Integer.parseInt(thread.get("displayorder"))>0?mr.getMessage(locale, "forum_thread_sticky"):"";
				thread_prefix += Integer.parseInt(thread.get("digest"))>0?mr.getMessage(locale, "forum_thread_digest"):"";
				ThreadInfo threadInfo = forums_threadsVO.getThreadInfo();
				threadInfo.setAuthor(thread.get("author"));
				threadInfo.setNumber((++number)+"");
				threadInfo.setPrefix(thread_prefix);
				threadInfo.setReplies(thread.get("replies"));
				String subject = thread.get("subject");
				threadInfo.setSubject(Common.strip_tags(subject.length()>30?Common.htmlspecialchars(subject.substring(0,30)):Common.htmlspecialchars(subject)));
				threadInfo.setTid(thread.get("tid"));
				threadInfo.setViews(thread.get("views"));
				threadList.add(threadInfo);
			}
		}
		forums_threadsVO.setMultipage(wapmulti(threadcount, waptpp, page, response.encodeURL("index.jsp?action=forum&amp;fid="+fid+"&amp;sid="+sid),mr,locale));
		StringBuffer subfrums = new StringBuffer();
		if(!dow.equals("digest")){
			String fidString = fid+"";
			for(Entry<String,Map<String,String>>  entry : forums.entrySet()){
				String subFid = entry.getKey();
				Map<String,String> subforum = entry.getValue();
				if(subforum.get("type").equals("sub")&&subforum.get("fup").equals(fidString) && (subforum.get("viewperm").equals("")||("\t"+subforum.get("viewperm").trim()+"\t").contains("\t"+groupid+"\t"))){
					subfrums.append("<a href=\""+response.encodeURL("index.jsp")+"?action=forum&amp;fid="+subFid+"\">"+Common.strip_tags(subforum.get("name"))+"</a><br />");
				}
			}
		}
		forums_threadsVO.setForumId(fid+"");
		forums_threadsVO.setSubfrums(subfrums.toString());
		String allowsearch = groupCache.get("allowsearch");
		forums_threadsVO.setAllowsearch(allowsearch!=null&&!allowsearch.equals("")&&!allowsearch.equals("0"));
		return forums_threadsVO;
	}
	public ThreadVO getThreadVO(HttpServletRequest request,HttpServletResponse response,Members currentMember,Map<String,String> thread,Map<String,String> settingMap,String formhash,String sid,String dow,String pageString,int offset,String pid,int start,Map<String,String> userGroupCache,String timeoffset,MessageResources mr,Locale locale){
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		ThreadVO threadVO = new ThreadVO();
		setHeaderVO(threadVO.getHeaderVO(), settingMap.get("bbname"));
		setFooterVO(request, settingMap, threadVO.getFooterVO(), currentMember, "thread", formhash, sid);
		String wapdateformat = settingMap.get("wapdateformat");
		String timeformat = settingMap.get("timeformat");
		if(timeformat.equals("1")){
			timeformat = "hh:mm a";
		}else{
			timeformat = "HH:mm";
		}
		int wapmps = Integer.parseInt(settingMap.get("wapmps"));
		String tid = thread.get("tid");
		threadVO.setTid(tid);
		threadVO.setSubject(Common.htmlspecialchars(Common.strip_tags(thread.get("subject"))));
		String threadposts = "";
		if (dow.equals("")) {
			threadVO.setViewThread(true);
			threadVO.setAuthorid(thread.get("authorid"));
			threadVO.setAuthor(Common.strip_tags(thread.get("author")));
			SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat(wapdateformat+" "+timeformat, timeoffset);
			threadVO.setDateline(Common.gmdate(simpleDateFormat, Integer.parseInt(thread.get("dateline"))));
			int page = 1;
			if(pageString != null && !pageString.equals("")){
				try{
					page = Math.max(1, Integer.parseInt(pageString));
				}catch(NumberFormatException exception){
					exception.printStackTrace();
				}
			}
			int wapppp = Integer.parseInt(settingMap.get("wapppp"));
			int start_limit = (page - 1)*wapppp;
			int number = start_limit;
			int end_limit = 0;
			if(page < 2 ){
				end_limit = wapppp + 1;
			} else {
				start_limit = start_limit + 1;
				end_limit = wapppp;
			}
			List<Map<String,String>> postsMapList = dataBaseDao.executeQuery("SELECT * FROM "+tablePre+"posts " +
					"WHERE tid='"+tid+"' AND invisible='0' " +
					"ORDER BY dateline LIMIT "+start_limit+", "+end_limit);
			int offset_last = 0;
			int offset_next = 0;
			boolean breaked = false;
			List<Map<String,String>> postlist = new ArrayList<Map<String,String>>();
			String fid = null;
			for(Map<String,String> postsMap : postsMapList){
				postsMap.put("message", wapcode(postsMap.get("message"),mr,locale));
				if(postsMap.get("first").equals("1")){
					if(offset > 0){
						offset_last = (offset - wapmps);
						postsMap.put("message", substr(postsMap.get("message"), offset));
					}else {
						offset = 0;
					}
					int mLen = 0;
					String tempMessage = postsMap.get("message");
					try {
						mLen = tempMessage.getBytes("GBK").length;
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if(mLen > wapmps){
						postsMap.put("message", wapcutstr(tempMessage,wapmps));
						offset_next = offset + wapmps;
						breaked = true;
					}else{
						breaked = false;
					}
					if(postsMap.get("anonymous").equals("1")){
						postsMap.put("author", mr.getMessage(locale, "anonymous"));
					}
					threadposts += postsMap.get("message").trim();
					fid = postsMap.get("fid");
				}else{
					postlist.add(postsMap);
				}
			}
			threadVO.setThreadposts(Common.strip_tags(threadposts).replaceAll("\r|\n", "<br />"));
			threadVO.setExistNextPage(breaked);
			threadVO.setExistLastPage(offset!=0);
			threadVO.setFid(fid);
			threadVO.setOffset_next(offset_next);
			threadVO.setOffset_last(offset_last);
			List<PostsInfo> postsList = threadVO.getPostsList();
			int waptlength = 30;
			if (postlist.size() > 0) {
				String replies = thread.get("replies");
				threadVO.setReplies(replies);
				for(Map<String,String> posts : postlist){
					PostsInfo postsInfo = threadVO.getPostsInfoInstance();
					postsInfo.setAuthor(posts.get("anonymous").equals("1")?mr.getMessage(locale, "anonymous"):posts.get("author"));
					postsInfo.setDateline(Common.gmdate(simpleDateFormat, Integer.parseInt(posts.get("dateline"))));
					postsInfo.setMessage(Common.strip_tags(wapcutstr(posts.get("message"), waptlength)));
					postsInfo.setNumber(++number);
					postsInfo.setPid(posts.get("pid"));
					postsList.add(postsInfo);
				}
				threadVO.setWapmulti(wapmulti(Integer.parseInt(replies), wapppp, page, response.encodeURL("index.jsp?action=thread&amp;tid="+tid),mr,locale));
			}
		} else {
			List<Map<String,String>> postMapList = dataBaseDao.executeQuery("SELECT * FROM "+tablePre+"posts WHERE pid=? AND invisible='0'",pid);
			boolean breaked = false;
			if(postMapList!=null && postMapList.size()>0){
				Map<String,String> postMap = postMapList.get(0);
				if (offset >0){
					postMap.put("message", substr(postMap.get("message"), offset));
				}
				int postMessgeLen = 0 ;
				try {
					postMessgeLen = postMap.get("message").getBytes("GBK").length;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if(postMessgeLen - wapmps > 0){
					postMap.put("message", wapcutstr(postMap.get("message"), wapmps));
					breaked = true;
				}
				if(postMap.get("anonymous").equals("1")){
					postMap.put("author", mr.getMessage(locale, "anonymous"));
				}
				postMap.put("message", wapcode(postMap.get("message"),mr,locale));
				PostsInfo postsInfo = threadVO.getPostsInfo();
				postsInfo.setPid(postMap.get("pid"));
				postsInfo.setAnonymous(postMap.get("anonymous").equals("1"));
				postsInfo.setAuthor(Common.strip_tags(postMap.get("author")));
				postsInfo.setAuthorid(postMap.get("authorid"));
				postsInfo.setMessage(Common.strip_tags(postMap.get("message").trim()).replaceAll("\n|\r", "<br />"));
				threadVO.setExistNextPage(breaked);
				threadVO.setExistLastPage(offset!=0);
				threadVO.setOffset_next(offset + wapmps);
				threadVO.setOffset_last(offset - wapmps);
			}
		}
		String allowreply = userGroupCache.get("allowreply");
		threadVO.setAllowreply(!allowreply.equals(""));
		threadVO.setFid(thread.get("fid"));
		threadVO.setTid(thread.get("tid"));
		threadVO.setSid(sid);
		threadVO.setFormhash(formhash);
		return threadVO;
	}
	public GoToVo getGoToVo(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid){
		GoToVo goToVo = new GoToVo();
		setHeaderVO(goToVo.getHeaderVO(), settingMap.get("bbname"));
		setFooterVO(request, settingMap, goToVo.getFooterVO(), currentMember, "goto", formhash, sid);
		return goToVo;
	}
	public NewThreadVO getNewThreadVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid,String fid,String threadtypesString,DataParse dataParse){
		NewThreadVO newThreadVO = new NewThreadVO();
		setFooterVO(request, settingMap, newThreadVO.getFooterVO(), currentMember, "post", formhash, sid);
		setHeaderVO(newThreadVO.getHeaderVO(), settingMap.get("bbname"));
		Map threadtypesMap = dataParse.characterParse(threadtypesString, false);
		Object tempRequired = threadtypesMap.get("required");
		if(tempRequired!=null && !tempRequired.toString().equals("") && !tempRequired.toString().equals("0")){
			newThreadVO.setThreadtypes(threadtypesMap);
		}
		newThreadVO.setFid(fid);
		newThreadVO.setFormhash(formhash);
		return newThreadVO;
	}
	public NewReplyVO getNewReplyVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid,String fid,String tid){
		NewReplyVO newReplyVO = new NewReplyVO();
		setFooterVO(request, settingMap, newReplyVO.getFooterVO(), currentMember, "post", formhash, sid);
		setHeaderVO(newReplyVO.getHeaderVO(), settingMap.get("bbname"));
		newReplyVO.setFid(fid);
		newReplyVO.setFormhash(formhash);
		newReplyVO.setTid(tid);
		newReplyVO.setSid(sid);
		return newReplyVO;
	}
	public MyVO getMyVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid,Map<String,String> memberMap,int uid,int jsprun_uid,String username,MessageResources mr,Locale locale){
		MyVO myVO = new MyVO();
		myVO.setUid(uid+"");
		myVO.setUsername(username);
		setFooterVO(request, settingMap, myVO.getFooterVO(), currentMember, "my", formhash, sid);
		setHeaderVO(myVO.getHeaderVO(), settingMap.get("bbname"));
		String gender = memberMap.get("gender");
		if(gender.equals("1")){
			myVO.setGender(mr.getMessage(locale, "a_member_edit_gender_male"));
		}else if(gender.equals("2")){
			myVO.setGender(mr.getMessage(locale, "a_member_edit_gender_female"));
		}else{
			myVO.setGender(mr.getMessage(locale, "a_member_edit_gender_secret"));
		}
		String bday = memberMap.get("bday");
		if(bday!=null && !bday.equals("") && !bday.equals("0000-00-00")){
			myVO.setBirthday(bday);
		}
		String location = memberMap.get("location");
		if(location!=null && !location.equals("")){
			myVO.setLocation(location);
		}
		String bio = memberMap.get("bio");
		if(bio!=null && !bio.equals("")){
			myVO.setBio(bio);
		}
		myVO.setSameMember(uid == jsprun_uid);
		return myVO;
	}
	public MyCollectionVO getMyCollectionVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid,int jsprun_uid){
		MyCollectionVO myCollectionVO = new MyCollectionVO();
		setFooterVO(request, settingMap, myCollectionVO.getFooterVO(), currentMember, "my", formhash, sid);
		setHeaderVO(myCollectionVO.getHeaderVO(), settingMap.get("bbname"));
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>> mythreadList = dataBaseDao.executeQuery("SELECT m.*, t.subject FROM "+tablePre+"mythreads m, "+tablePre+"threads t " +
				"WHERE m.uid = '"+jsprun_uid+"' AND m.tid = t.tid ORDER BY m.dateline DESC LIMIT 0, 3");
		if(mythreadList!=null ){
			for(Map<String,String> tempMap : mythreadList){
				tempMap.put("subject", Common.cutstr(tempMap.get("subject"), 15, null));
			}
		}
		myCollectionVO.setMythreadList(mythreadList);
		List<Map<String,String>> mypostList = dataBaseDao.executeQuery("SELECT m.*, t.subject FROM "+tablePre+"myposts m, "+tablePre+"threads t " +
				"WHERE m.uid = '"+jsprun_uid+"' AND m.tid = t.tid ORDER BY m.dateline DESC LIMIT 0, 3");
		if(mypostList!=null ){
			for(Map<String,String> tempMap : mypostList){
				tempMap.put("subject", Common.cutstr(tempMap.get("subject"), 15, null));
			}
		}
		myCollectionVO.setMypostList(mypostList);
		List<Map<String,String>> favthreadList = dataBaseDao.executeQuery("SELECT t.tid, t.subject FROM "+tablePre+"favorites fav, "+tablePre+"threads t " +
				"WHERE fav.tid=t.tid AND t.displayorder>='0' AND fav.uid='"+jsprun_uid+"' ORDER BY t.lastpost DESC LIMIT 0, 3");
		if(favthreadList!=null ){
			for(Map<String,String> tempMap : favthreadList){
				tempMap.put("subject", Common.cutstr(tempMap.get("subject"), 24, null));
			}
		}
		myCollectionVO.setFavthreadList(favthreadList);
		List<Map<String,String>> favforumList = dataBaseDao.executeQuery("SELECT f.fid, f.name FROM "+tablePre+"favorites fav, "+tablePre+"forums f WHERE fav.uid='"+jsprun_uid+"' AND fav.fid=f.fid ORDER BY f.displayorder DESC LIMIT 0, 3");
		myCollectionVO.setFavforumList(favforumList);
		return myCollectionVO;
	}
	public MyPhoneVO getMyPhoneVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid){
		MyPhoneVO myPhoneVO = new MyPhoneVO();
		setFooterVO(request, settingMap, myPhoneVO.getFooterVO(), currentMember, "myphone", formhash, sid);
		setHeaderVO(myPhoneVO.getHeaderVO(), settingMap.get("bbname"));
		String user_agent = request.getHeader("user-agent");
		user_agent = user_agent != null ? user_agent.toLowerCase() : "";
		myPhoneVO.setServerInfo(user_agent);
		Map<String,String> otherInfoMap = new HashMap<String, String>();
		Enumeration<String> headerInfo = request.getHeaderNames();
		while(headerInfo.hasMoreElements()){
			String tempName = headerInfo.nextElement();
			otherInfoMap.put(tempName, request.getHeader(tempName));
		}
		myPhoneVO.setOtherInfoMap(otherInfoMap);
		return myPhoneVO;
	}
	public SearchVO getSearchVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid){
		SearchVO searchVO = new SearchVO();
		setHeaderVO(searchVO.getHeaderVO(), settingMap.get("bbname"));
		setFooterVO(request, settingMap, searchVO.getFooterVO(), currentMember, "search", formhash, sid);
		searchVO.setSid(sid);
		return searchVO;
	}
	public SearchResultVO getSearchResultVO(HttpServletRequest request,HttpServletResponse response,Map<String,String> settingMap,Members currentMember,String formhash,String sid,Map<String,String> index,int waptpp,int start_limit,int page,int searchnum,String searchid,MessageResources mr,Locale locale){
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		SearchResultVO searchResultVO = new SearchResultVO();
		setHeaderVO(searchResultVO.getHeaderVO(), settingMap.get("bbname"));
		setFooterVO(request, settingMap, searchResultVO.getFooterVO(), currentMember, "search", formhash, sid);
		List<Map<String,String>> threadMapList = dataBaseDao.executeQuery("SELECT * FROM "+tablePre+"threads WHERE tid IN ("+index.get("tids")+") AND displayorder>='0' ORDER BY dateline DESC LIMIT "+start_limit+", "+waptpp);
		List<ThreadInfo> threadInfoList = searchResultVO.getThreadInfoList();
		int number = start_limit;
		if(threadMapList!=null && threadMapList.size()>0){
			for(Map<String,String> thread : threadMapList){
				ThreadInfo threadInfo = searchResultVO.geThreadInfo();
				threadInfo.setTid(thread.get("tid"));
				threadInfo.setSubject(Common.cutstr(thread.get("subject"), 24, null));
				threadInfo.setNumber((++number)+"");
				threadInfo.setViews(thread.get("views"));
				threadInfo.setReplies(thread.get("replies"));
				threadInfoList.add(threadInfo);
			}
		}
		searchResultVO.setMultipage(wapmulti(searchnum, waptpp, page, response.encodeURL("index.jsp?action=search&amp;searchid="+searchid+"&amp;do=submit&amp;sid="+sid),mr,locale));
		return searchResultVO;
	}
	public StatsVO getStatsVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid){
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		StatsVO statsVO = new StatsVO();
		setFooterVO(request, settingMap, statsVO.getFooterVO(), currentMember, "stats", formhash, sid);
		setHeaderVO(statsVO.getHeaderVO(), settingMap.get("bbname"));
		String totalmembers = settingMap.get("totalmembers");
		statsVO.setMembers(totalmembers);
		List<Map<String,String>> tempMapList = dataBaseDao.executeQuery("SELECT SUM(threads) AS threads, SUM(posts) AS posts FROM "+tablePre+"forums WHERE status=1");
		if(tempMapList!=null && tempMapList.size()>0){
			Map<String,String> tempMap = tempMapList.get(0);
			statsVO.setPosts(tempMap.get("posts"));
			statsVO.setThreads(tempMap.get("threads"));
		}else{
			statsVO.setPosts("0");
			statsVO.setThreads("0");
		}
		return statsVO;
	}
	public PmVO getPmVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid,int jsprun_uid){
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		PmVO pmVO = new PmVO();
		setFooterVO(request, settingMap, pmVO.getFooterVO(), currentMember, "pm", formhash, sid);
		setHeaderVO(pmVO.getHeaderVO(), settingMap.get("bbname"));
		List<Map<String,String>> tempMapList = dataBaseDao.executeQuery("SELECT COUNT(*) AS num, new FROM "+tablePre+"pms WHERE msgtoid='"+jsprun_uid+"' AND folder='inbox' GROUP BY new='0'");
		int num_unread = 0;
		int num_read = 0;
		if(tempMapList!=null && tempMapList.size()>0){
			for(Map<String,String> tempMap : tempMapList){
				if(tempMap.get("new").equals("0")){
					num_read = Integer.parseInt(tempMap.get("num"));
				}else{
					num_unread = Integer.parseInt(tempMap.get("num"));
				}
			}
		}
		pmVO.setNum_all((num_unread+num_read)+"");
		pmVO.setNum_unread(num_unread+"");
		return pmVO;
	}
	public PmListVO getPmListVO(HttpServletRequest request,HttpServletResponse response,Map<String,String> settingMap,Members currentMember,String formhash,String sid,int jsprun_uid,String unreadFR,String pageString,String dow,MessageResources mr,Locale locale,String timeoffset){
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		PmListVO pmListVO = new PmListVO();
		setFooterVO(request, settingMap, pmListVO.getFooterVO(), currentMember, "pm", formhash, sid);
		setHeaderVO(pmListVO.getHeaderVO(), settingMap.get("bbname"));
		int waptpp = Integer.parseInt(settingMap.get("waptpp"));
		String unreadadd = unreadFR==null? "" : "AND new>'0'";
		String pageadd = unreadFR == null? "" : "&amp;unread=yes";
		int page = pageString == null || pageString.equals("") ? 0 : Integer.parseInt(pageString);
		page = Math.max(1, page);
		int start_limit = (page-1)*waptpp ;
		int number = start_limit;
		int totalpms = 0;
		List<Map<String,String>> pmsMapList = dataBaseDao.executeQuery("SELECT COUNT(*) AS cnt FROM "+tablePre+"pms WHERE msgtoid='"+jsprun_uid+"' AND folder='inbox' "+unreadadd);
		if(pmsMapList==null || pmsMapList.size()==0){
			return pmListVO;
		}else{
			totalpms = Integer.parseInt(pmsMapList.get(0).get("cnt"));
		}
		pmsMapList = dataBaseDao.executeQuery("SELECT pmid, new, msgfrom, subject, dateline FROM "+tablePre+"pms " +
				"WHERE msgtoid='"+jsprun_uid+"' AND folder='inbox' "+unreadadd+" " +
				"ORDER BY dateline DESC " +
				"LIMIT "+start_limit+", "+waptpp);
		if(pmsMapList!=null && pmsMapList.size()>0){
			List<PmInfo> pmInfoList = pmListVO.getPmInfoList();
			String wapdateformat = settingMap.get("wapdateformat");
			String timeformat = settingMap.get("timeformat");
			if(timeformat.equals("1")){
				timeformat = "hh:mm a";
			}else{
				timeformat = "HH:mm";
			}
			SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat(wapdateformat+" "+timeformat, timeoffset);
			for(Map<String,String> tempMap : pmsMapList){
				PmInfo pmInfo = pmListVO.getPmInfo();
				pmInfo.setPmid(tempMap.get("pmid"));
				pmInfo.setNumber(++number);
				pmInfo.setUnread(unreadFR==null && !tempMap.get("new").equals("0"));
				pmInfo.setSubject(Common.cutstr(tempMap.get("subject"), 30, null));
				pmInfo.setMsgfrom(tempMap.get("msgfrom"));
				pmInfo.setDateline(simpleDateFormat.format(Long.parseLong(tempMap.get("dateline"))*1000L));
				pmInfoList.add(pmInfo);
			}
		}
		String indexEncode = response.encodeURL("index.jsp");
		String wapmulti = wapmulti(totalpms, waptpp, page, indexEncode+"?action=pm&amp;do="+dow+pageadd,mr,locale);
		pmListVO.setWapmulti(wapmulti);
		return pmListVO;
	}
	public PmViewVO getPmViewVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid,String pmid,int jsprun_uid,String timeoffset){
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		PmViewVO pmViewVO = new PmViewVO();
		setFooterVO(request, settingMap, pmViewVO.getFooterVO(), currentMember, "pm", formhash, sid);
		setHeaderVO(pmViewVO.getHeaderVO(), settingMap.get("bbname"));
		List<Map<String,String>> pmsMapList = dataBaseDao.executeQuery("SELECT * FROM "+tablePre+"pms WHERE pmid=? AND msgtoid='"+jsprun_uid+"' AND folder='inbox'",pmid);
		if(pmsMapList == null || pmsMapList.size()==0){
			return pmViewVO;
		}else{
			pmViewVO.setExistPm(true);
		}
		String wapdateformat = settingMap.get("wapdateformat");
		String timeformat = settingMap.get("timeformat");
		if(timeformat.equals("1")){
			timeformat = "hh:mm a";
		}else{
			timeformat = "HH:mm";
		}
		Map<String,String> pm = pmsMapList.get(0);
		pmViewVO.setSubject(pm.get("subject"));
		pmViewVO.setMsgfrom(pm.get("msgfrom"));
		pmViewVO.setMessage(Common.nl2br(Common.htmlspecialchars(pm.get("message"))));
		pmViewVO.setDateline(Common.gmdate(wapdateformat+" "+timeformat, Integer.parseInt(pm.get("dateline")), timeoffset));
		pmViewVO.setPmid(pmid);
		dataBaseDao.execute("UPDATE "+tablePre+"pms SET new='0' WHERE pmid=?",pmid);
		return pmViewVO;
	}
	public PmSendVO getPmSendVO(HttpServletRequest request,Map<String,String> settingMap,Members currentMember,String formhash,String sid,String pmid,int jsprun_uid){
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		PmSendVO pmSendVO = new PmSendVO();
		setFooterVO(request, settingMap, pmSendVO.getFooterVO(), currentMember, "pm", formhash, sid);
		setHeaderVO(pmSendVO.getHeaderVO(), settingMap.get("bbname"));
		pmSendVO.setSid(sid);
		pmSendVO.setFormhash(formhash);
		if(pmid!=null){
			List<Map<String,String>> tempMapList = dataBaseDao.executeQuery("SELECT msgfrom, subject FROM "+tablePre+"pms WHERE pmid=? AND msgtoid='"+jsprun_uid+"' AND folder='inbox'",pmid);
			if(tempMapList!=null && tempMapList.size()>0){
				Map<String,String> tempMap = tempMapList.get(0);
				pmSendVO.setMsgfrom(tempMap.get("msgfrom"));
				pmSendVO.setSubject(tempMap.get("subject"));
			}
		}
		return pmSendVO;
	}
	private String wapmulti(int num, int perpage, int curpage, String mpurl,MessageResources mr,Locale locale){
		String multipage = "";
		mpurl += mpurl.contains("?") ? "&amp;" : "?";
		if(num > perpage){
			int page = 3;
			int offset = 2;
			int from = 0;
			int to = 0;
			int realpages = (int)Math.ceil(num*1.0/perpage);
			int pages = realpages;
			if(page > pages){
				from = 1;
				to = pages;
			}else{
				from = curpage - offset;
				to = from + page -1;
				if(from < 1){
					to = curpage + 1 - from;
					from = 1;
					if(to - from < page){
						to = page;
					}
				}else if(to > pages){
					from = pages - page + 1;
					to = pages;
				}
			}
			multipage = (curpage - offset > 1 && pages > page ? "<a href=\""+mpurl+"page=1\">"+mr.getMessage(locale, "home")+"</a>" : "")+
			(curpage > 1 ? " <a href=\""+mpurl+"page="+(curpage - 1)+"\">"+mr.getMessage(locale, "last_page")+"</a>" : "");
			for(int i = from; i<= to ;i++){
				multipage += i == curpage ? " "+i : " <a href=\""+mpurl+"page="+i+"\">"+i+"</a>";
			}
			multipage += (curpage < pages ? " <a href=\""+mpurl+"page="+(curpage + 1)+"\">"+mr.getMessage(locale, "next_page")+"</a>" : "")+
			(to < pages ? " <a href=\""+mpurl+"page="+pages+"\">"+mr.getMessage(locale, "end_page")+"</a>" : "");
			multipage += realpages > page ?
					"<br />"+curpage+"/"+realpages+mr.getMessage(locale, "page")+"<input type=\"text\" name=\"page\" size=\"2\" emptyok=\"true\" /> "+
					"<anchor title=\"submit\">"+mr.getMessage(locale, "turn_page")+"<go method=\"post\" href=\""+mpurl+"\">"+
					"<postfield name=\"page\" value=\"$(page)\" />"+
					"</go></anchor>" : "";
		}
		return multipage;
	}
	private void setHeaderVO(HeaderVO headerVO,String title){
		headerVO.setTitle(title);
	}
	private void setFooterVO(HttpServletRequest request,Map<String,String> settingMap,FooterVO footerVO,Members currentMember,String action,String formhash,String sid){
		HttpSession session = request.getSession();
		int timestamp=(Integer)request.getAttribute("timestamp");
		String timeoffset=(String)session.getAttribute("timeoffset");
		boolean isLogin = currentMember!=null; 
		String userName = isLogin ? currentMember.getUsername() : "";
		boolean isNotHome = action!=null&&!action.equals("home");
		String wapdateformat = settingMap.get("wapdateformat");
		String timeformat = settingMap.get("timeformat");
		if(timeformat.equals("1")){
			timeformat = "hh:mm a";
		}else{
			timeformat = "HH:mm";
		}
		String time = Common.gmdate(wapdateformat+" "+timeformat, timestamp, timeoffset);
		footerVO.setSid(sid);
		footerVO.setFormhash(formhash);
		footerVO.setIsLogin(isLogin);
		footerVO.setIsNotHome(isNotHome);
		footerVO.setTime(time);
		footerVO.setUserName(userName);
		Common.updatesession(request, settingMap);
	}
	private void setMessageVO(HttpServletRequest request,HttpServletResponse response,Map<String,String> settingMap,MessageVO messageVO,String message,Map<String,String> forwardMap,String headerVO_title,Members currentMember,String action,String footerVO_formhash,String sid){
		HeaderVO headerVO = messageVO.getHeaderVO();
		FooterVO footerVO = messageVO.getFooterVO();
		messageVO.setMessage(message);
		if(forwardMap!=null){
			messageVO.setForward(true);
			messageVO.setForwardLink(response.encodeURL(forwardMap.get("link")));
			messageVO.setForwardTitle(forwardMap.get("title"));
		}
		setHeaderVO(headerVO, headerVO_title);
		setFooterVO(request, settingMap, footerVO, currentMember, action, footerVO_formhash,sid);
	}
	private String wapcode(String targetString,MessageResources mr,Locale locale){
		targetString = targetString.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");
		String regex = "\\[hide\\](.+?)\\[\\/hide\\]";
		targetString = targetString.replaceAll(regex, mr.getMessage(locale, "post_hide_reply_hidden_wap"));
		for(int i = 0;i<5;i++){
			targetString = targetString.replaceAll("\\[(\\w+)[^\\]]*?\\](.*?)\\[\\/\\\\1\\]", "\\2");
		}
		return targetString;
	}
	private String wapcutstr(String targetString , int length){
		int tarLen = 0;
		try {
			tarLen = targetString.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(tarLen>length){
			int targetLen = targetString.length();
			int temp = 0;
			StringBuffer strcut = new StringBuffer();
			for(int i = 0;i<targetLen;i++){
				if(temp >= length ){
					break;
				}else{
					temp++;
				}
				char tempC = targetString.charAt(i);
				strcut.append(tempC);
				if(tempC<0||tempC>127){
					temp++;
				}
			}
			return strcut.toString() + " ..";
		}else{
			return targetString;
		}
	}
	private String substr(String targetString,int offset){
		boolean countDown = offset < 0 ;
		int stringLen = targetString.length();
		int sign = 0;
		int i = 0;
		if(countDown){
			i = stringLen - 1;
			for(;i>=0;i--){
				if(sign <= offset ){
					break;
				}
				sign-- ;
				char temp = targetString.charAt(i);
				if(temp<0 || temp>127){
					sign --;
				}
			}
		}else{
			for(;i<stringLen;i++){
				if(sign>=offset){
					break;
				}
				sign ++;
				char temp = targetString.charAt(i);
				if(temp<0 || temp>127){
					sign ++;
				}
			}
		}
		return targetString.substring(i);
	}
}
