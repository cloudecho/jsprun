package cn.jsprun.struts.action;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.form.PruneForm;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.LogPage;
public class PruneAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward fromPrune(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "searchsubmit")){
				HttpSession session=request.getSession();
				PruneForm pf = (PruneForm) form; 
				short groupid = (Short)session.getAttribute("jsprun_groupid");
				Members member = (Members)session.getAttribute("user");
				if(member!=null&&member.getAdminid()==3){
					StringBuffer fidsbuffer = new StringBuffer();
					List<Map<String,String>> fidslist = dataBaseService.executeQuery("SELECT fid FROM jrun_moderators WHERE uid='"+member.getUid()+"'");
					for(Map<String,String> fids:fidslist){
						fidsbuffer.append(","+fids.get("fid"));
					}
					if(fidsbuffer.length()>0){	
						pf.setFid(fidsbuffer.substring(1));
						List<Map<String,String>> forumList=dataBaseService.executeQuery("SELECT name from jrun_forums where fid in ( "+fidsbuffer.substring(1)+" ) ORDER BY type, displayorder");
						StringBuffer forumname = new StringBuffer();
						for(Map<String,String> forums:forumList){
							forumname.append(","+forums.get("name"));
						}
						if(forumname.length()>0){
							request.setAttribute("forumselect", forumname.substring(1));
						}else{
							request.setAttribute("forumselect", getMessage(request,"none"));
						}
					}else{
						pf.setFid("-1");
					}
				}else{
					request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",pf.getForums()+""));
				}
				String info = getMessage(request, "jsprun_message")+getMessage(request, "a_post_prune_condition_invalid");
				int currentpage = 1; 
				String page = request.getParameter("page");
				currentpage = Math.max(Common.intval(page), 1);
				String timeoffset=(String)session.getAttribute("timeoffset");
				SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd", timeoffset);
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				Members adminback = (Members) session.getAttribute("members");
				String bigStartTime = request.getParameter("bigstart"); 
				request.setAttribute("startTime", bigStartTime);
				if (adminback != null) {
					if (adminback.getAdminid() == 2 || adminback.getAdminid() == 3) {
						if (FormDataCheck.isValueString(pf.getStarttime())&& FormDataCheck.isLess(pf.getStarttime(),bigStartTime, dateFormat)) {
							info = getMessage(request, "jsprun_message")+getMessage(request, "a_post_prune_mod_range_illegal");
							request.setAttribute("myinfo", info);
							request.setAttribute("notfirst", "notfirst");
							return mapping.findForward("toPruneForum");
						}
					}
				}
				String isDisplay = "none";
				if (pf.isForm()) {
					info = getMessage(request, "jsprun_message")+getMessage(request, "a_post_prune_post_nonexistence");
					String sql = postsService.prunsql(pf,timeoffset);
					StringBuffer sb = new StringBuffer();
					String uidsql = "";
					if (pf.getUsers() != null && !pf.getUsers().equals("")) {
						StringBuffer uids = new StringBuffer("-1");
						String cins ="" ;
						if (pf.getCins()==1) {
							cins = " binary ";
						} else {
							cins = "";
						}
						String username = Common.addslashes(pf.getUsers());
						username = username.replace(",", "','");
						List<Map<String,String>> members = dataBaseService.executeQuery("select uid from jrun_members where "+cins+" username in ('"+username+"')");
						if(members!=null&&members.size()>0){
							for(Map<String,String> user:members){
								uids.append(","+user.get("uid"));
							}
						}
						uidsql = " AND p.authorid IN ("+uids+")";
					}
					String postsql = "select p.pid "+sql+uidsql;
					List<Map<String,String>> countlist = dataBaseService.executeQuery("select count(*) count "+sql+uidsql);
					int totalSize = Common.toDigit(countlist.get(0).get("count"));
					if(pf.getDetail()==1){
						int totalPage = 1;
						int pageSize = 10; 
						int startid = 0;
						if (totalSize > pageSize) {
							if (totalSize % pageSize == 0) {
								totalPage = (int) ((double) totalSize / (double) pageSize);
							} else {
								totalPage = (int) (1.0d + (double) totalSize / (double) pageSize);
							}
						} else {
							totalPage = 1;
						}
						if (currentpage > 0) {
							if (currentpage > totalPage) {
								currentpage = totalPage;
							}
							startid = pageSize * (currentpage - 1);
						}
						LogPage loginpage = new LogPage(totalSize,10,currentpage);
						request.setAttribute("logpage", loginpage);
						postsql = "select p.*,t.subject as tsubject "+sql+uidsql+" limit "+startid+",10";
					}
					List<Map<String,String>> postlist = dataBaseService.executeQuery(postsql);
					if (postlist != null) {
						Map<String, Map<String,String>> forumdatas = null;
						if(pf.getDetail()==1){
							Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
							Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
							if(forumMap!=null){
								forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
							}
						}
						for (Map<String,String> post:postlist) {
							sb.append(post.get("pid"));
							sb.append(",");
							if(pf.getDetail()==1&&forumdatas!=null){
								Map<String,String> forum = forumdatas.get(post.get("fid"));
								post.put("name", forum.get("name"));
							}
						}
					}
					request.setAttribute("sb", sb);
					if (pf.getDetail() == 1) {
						isDisplay = "block";
					}
					request.setAttribute("postsList", postlist);
					request.setAttribute("countPosts", totalSize);
					postlist = null;
				}
				if (member.getAdminid() != 1) {
					String endTime = Common.gmdate(dateFormat, timestamp);
					request.setAttribute("isAdmin", "admin");
					pf.setEndtime(endTime);
				}
				session.setAttribute("pf", pf);
				request.setAttribute("isDisplay", isDisplay);
				request.setAttribute("myinfo", info);
				request.setAttribute("notfirst", "notfirst");
				return mapping.findForward("toPruneForum");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=prune");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward batchPrune(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "prunesubmit")){
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				StringBuffer pidsSb = new StringBuffer();
				String sb = request.getParameter("sb");
				int uid = (Integer)request.getSession().getAttribute("jsprun_uid");
				Map<String,String> settings = ForumInit.settings;
				int postcount = 0;
				int threadcount = 0;
				if (sb != null && !sb.equals("")) {
					Object[] pids = sb.split(",");
					for (int i = 0; i < pids.length; i++) {
						String str = request.getParameter(toPIDArray(pids[i].toString()));
						if (str != null && !str.equals("")) {
							pidsSb.append(pids[i].toString());
							pidsSb.append(",");
						}
					}
					String donotupdatemember = request.getParameter("donotupdatemember");
					List<Map<String,String>> postmap = dataBaseService.executeQuery("SELECT pid,tid, first, authorid FROM jrun_posts WHERE pid IN ( "+pidsSb+"0 )");
					StringBuffer pidsdelete = new StringBuffer("0");
					StringBuffer tidsdelete = new StringBuffer("0");
					Map<String,Integer> tids = new HashMap<String,Integer>();
					for(Map<String,String>map:postmap){
						pidsdelete.append(","+map.get("pid"));postcount++;
						String tid = map.get("tid");
						if(map.get("first").equals("1")){
							tidsdelete.append(","+tid);threadcount++;
							tids.remove(tid);
						}else{
							int count = tids.get(tid)==null?0:tids.get(tid);
							tids.put(tid, count+1);
						}
					}
					List<Map<String,String>> attalist = dataBaseService.executeQuery("select attachment,thumb,remote from jrun_attachments where pid in ("+ pidsdelete +")");
					String servletpath = JspRunConfig.realPath+settings.get("attachdir")+"/";
					for(Map<String,String>atta:attalist){
						Common.dunlink(atta.get("attachment"), Byte.valueOf(atta.get("thumb")), Byte.valueOf(atta.get("remote")), servletpath);
					}
					attalist=null; 
					Set<Entry<String,Integer>> tidcount = tids.entrySet();
					for(Entry<String,Integer> temp:tidcount){
						int value = temp.getValue();
						dataBaseService.runQuery("update jrun_threads set replies=replies-"+value+" where tid='"+temp.getKey()+"'",true);
					}
					if (donotupdatemember == null) {
						Map creditspolicys=dataParse.characterParse(ForumInit.settings.get("creditspolicy"),false);
						Map<Integer, Integer> postcredits=(Map<Integer,Integer>)creditspolicys.get("reply");
						Map<Integer, Integer> threadcredits=(Map<Integer,Integer>)creditspolicys.get("post");
						String creditsformula = (ForumInit.settings.get("creditsformula"));
						creditspolicys=null;
						Map<String,Integer> threaduidmap = new HashMap<String,Integer>();
						Map<String,Integer> postuidmap = new HashMap<String,Integer>();
						for(Map<String,String>post:postmap){
							String authorid = post.get("authorid");
							if(post.get("first").equals("1")){
								int count = threaduidmap.get(authorid)==null?0:threaduidmap.get(authorid);
								threaduidmap.put(authorid, count+1);
							}else{
								int count = postuidmap.get(authorid)==null?0:postuidmap.get(authorid);
								postuidmap.put(authorid, count+1);
							}
						}
						Set<String> threads = threaduidmap.keySet();
						for(String uids:threads){
							int value = threaduidmap.get(uids);
							Common.updatepostcredits("-", Common.toDigit(uids), threadcredits, timestamp, value, creditsformula);
						}
						Set<String> posts = postuidmap.keySet();
						for(String uids:posts){
							int value = postuidmap.get(uids);
							Common.updatepostcredits("-", Common.toDigit(uids), postcredits, timestamp, value, creditsformula);
						}
					}
					dataBaseService.runQuery("DELETE FROM jrun_attachments WHERE pid IN ("+pidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_attachments WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_threadsmod WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_threads WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_posts WHERE pid IN ("+pidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_posts WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_polloptions WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_polls WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_rewardlog WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_trades WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_rewardlog WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_activities WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_activityapplies WHERE tid IN ("+tidsdelete+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_typeoptionvars WHERE tid IN ("+tidsdelete+")",true);
				}
				String fids = request.getParameter("fids");
				if(fids!=null && !fids.equals("") && !fids.equals("-1")){
					MessageResources resources = getResources(request);
					Locale locale = getLocale(request);
					Common.updateforumcount(fids,resources,locale);
				}
				Common.updatemodworks(settings, uid, timestamp, "DLP", postcount);
				settings = null;
				try {
					String shalert = getMessage(request, "a_post_prune_succeed", threadcount+"",postcount+"");
					response.getWriter().write( "<script type='text/javascript'>alert('" + shalert + "');</script>");
					response.getWriter().write("<script>parent.$('pruneforum').searchsubmit.click();</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=prune");
		return null;
	}
	private String toPIDArray(String pids) {
		StringBuffer sb = new StringBuffer("pidarray[");
		sb.append(pids);
		sb.append("]");
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	public ActionForward pagePrune(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		PruneForm pf = (PruneForm) session.getAttribute("pf"); 
		if(pf==null){
			Common.requestforward(response, "admincp.jsp?action=prune");
			return null;
		}
		request.setAttribute("startTime", pf.getStarttime());
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		String timeoffset=(String)session.getAttribute("timeoffset");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		if(member!=null&&member.getAdminid()==3){
			StringBuffer fidsbuffer = new StringBuffer();
			List<Map<String,String>> fidslist = dataBaseService.executeQuery("SELECT fid FROM jrun_moderators WHERE uid='"+member.getUid()+"'");
			for(Map<String,String> fids:fidslist){
				fidsbuffer.append(","+fids.get("fid"));
			}
			if(fidsbuffer.length()>0){	
				pf.setFid(fidsbuffer.substring(1));
				List<Map<String,String>> forumList=dataBaseService.executeQuery("SELECT name from jrun_forums where fid in ( "+fidsbuffer.substring(1)+" ) ORDER BY type,displayorder");
				StringBuffer forumname = new StringBuffer();
				for(Map<String,String> forums:forumList){
					forumname.append(","+forums.get("name"));
				}
				if(forumname.length()>0){
					request.setAttribute("forumselect", forumname.substring(1));
				}else{
					request.setAttribute("forumselect", getMessage(request, "none"));
				}
			}else{
				pf.setFid("-1");
			}
		}else{
			request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",pf.getForums()+""));
		}
		if(member!=null&&(member.getAdminid().intValue()==2||member.getAdminid().intValue()==3)){
			String endTime = Common.gmdate("yyyy-MM-dd", timestamp,timeoffset);
			pf.setEndtime(endTime);
		}
		int currentpage = 1;
		String page = request.getParameter("page");
		currentpage = page == null || page.equals("") ? 1 : Integer.valueOf(page.trim());
		if (pf.isForm()) {
			String sql = postsService.prunsql(pf,timeoffset);
			StringBuffer sb = new StringBuffer();
			String uidsql = "";
			if (pf.getUsers() != null && !pf.getUsers().equals("")) {
				StringBuffer uids = new StringBuffer("-1");
				String cins ="" ;
				if (pf.getCins()==1) {
					cins = " binary ";
				} else {
					cins = "";
				}
				String username = Common.addslashes(pf.getUsers());
				username = username.replace(",", "','");
				List<Map<String,String>> members = dataBaseService.executeQuery("select uid from jrun_members where "+cins+" username in ('"+username+"')");
				if(members!=null&&members.size()>0){
					for(Map<String,String> user:members){
						uids.append(","+user.get("uid"));
					}
				}
				uidsql = " AND p.authorid IN ("+uids+")";
			}
			List<Map<String,String>> countlist = dataBaseService.executeQuery("select count(*) count "+sql+uidsql);
			int totalSize = Common.toDigit(countlist.get(0).get("count"));
			int totalPage = 1;
			int pageSize = 10; 
			int startid = 0;
			if (totalSize > pageSize) {
				if (totalSize % pageSize == 0) {
					totalPage = (int) ((double) totalSize / (double) pageSize);
				} else {
					totalPage = (int) (1.0d + (double) totalSize / (double) pageSize);
				}
			} else {
				totalPage = 1;
			}
			if (currentpage > 0) {
				if (currentpage > totalPage) {
					currentpage = totalPage;
				}
				startid = pageSize * (currentpage - 1);
			}
			List<Map<String,String>> postlist = dataBaseService.executeQuery("select p.*,t.subject as tsubject "+sql+uidsql+" limit "+startid+",10");
			LogPage loginpage = new LogPage(totalSize,10,currentpage);
			request.setAttribute("logpage", loginpage);
			if (postlist != null) {
				Map<String, Map<String,String>> forumdatas = null;
				Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
				Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
				if(forumMap!=null){
					forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
				}
				for (Map<String,String> post:postlist) {
					sb.append(post.get("pid"));
					sb.append(",");
					if(forumdatas!=null){
						Map<String,String> forum = forumdatas.get(post.get("fid"));
						post.put("name", forum.get("name"));
					}
				}
			}
			request.setAttribute("sb", sb);
			request.setAttribute("postsList", postlist);
			request.setAttribute("countPosts", totalSize);
		}
		if (member.getAdminid() != 1) {
			request.setAttribute("isAdmin", "admin");
		}
		request.setAttribute("notfirst", "notfirst");
		return mapping.findForward("toPruneForum");
	}
}
