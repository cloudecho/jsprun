package cn.jsprun.struts.action;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Attachtypes;
import cn.jsprun.domain.Bbcodes;
import cn.jsprun.domain.Imagetypes;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Posts;
import cn.jsprun.domain.Smilies;
import cn.jsprun.domain.Words;
import cn.jsprun.struts.form.ModrepliesPageForm;
import cn.jsprun.struts.form.PostsForm;
import cn.jsprun.struts.form.PostsPageForm;
import cn.jsprun.struts.form.ThreadsForm;
import cn.jsprun.utils.Base64;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.LogPage;
public class PostsAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward auditingNewThreads(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String filter = request.getParameter("filter");
		String modfid = request.getParameter("modfid");
		PostsForm pf = new PostsForm();
		Members member = (Members)request.getSession().getAttribute("members");
		if (!Common.empty(filter)) {
			pf.setFilter(filter.trim());
		}
		if (!Common.empty(modfid)) {
			if(member.getAdminid()==3&&Common.toDigit(modfid)>0){
				List<Map<String,String>> moderator = dataBaseService.executeQuery("select fid from jrun_moderators where fid='"+modfid+"' and uid='"+member.getUid()+"'");
				if(moderator==null || moderator.size()<=0){
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_post_moderate_no_access_this");
					return mapping.findForward("message");
				}
			}
			pf.setFid(Short.valueOf(modfid));
		}
		request.setAttribute("pastsPage", pf); 
		PostsPageForm ppf = postsService.fidnByForums(pf); 
		String sql="SELECT fid, name FROM jrun_forums WHERE type <> 'group' AND status=1";
		if(member.getAdminid()==3){
			sql = "SELECT m.fid, f.name FROM jrun_moderators m LEFT JOIN jrun_forums f ON f.fid=m.fid  WHERE m.uid='"+member.getUid()+"'";
		}
		List<Map<String,String>> forumList=dataBaseService.executeQuery(sql);
		if(forumList==null||forumList.size()<=0){
			request.setAttribute("return", true);
			request.setAttribute("message_key", "a_post_moderate_no_access_this");
			return mapping.findForward("message");
		}
		request.setAttribute("forumList", forumList);
		if(member.getAdminid()==3&&pf.getFid()<=0){
			StringBuffer fids = new StringBuffer();
			for(Map<String,String> forums:forumList){
				fids.append(","+forums.get("fid"));
			}
			String displayorder = "";
			if (pf.getFilter().equals("normal")) {
				displayorder = "-2";
			}
			if (pf.getFilter().equals("ignore")) {
				displayorder = "-3";
			}
			String currsql = "select count(*) count from jrun_threads as t where t.displayorder=" + displayorder+ " t.fid in ("+fids.substring(1)+")";
			String queryStr = "select t.tid, t.fid, t.author, t.authorid, t.subject, t.dateline,p.pid, p.message, p.useip, p.attachment from jrun_threads t LEFT JOIN jrun_posts p ON p.tid=t.tid WHERE t.displayorder=" + displayorder+ " t.fid in ("+fids.substring(1)+") GROUP BY t.tid ORDER BY t.dateline DESC ";
			ppf = new PostsPageForm(currsql,queryStr);
		}
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
		Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
		Map<String, Map<String,String>> forumdatas = null;
		if(forumMap!=null){
			forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
		}
		ppf.setList(request.getContextPath(),resources,locale,forumdatas);
		request.setAttribute("postsPageForm", ppf); 
		String modreasons=ForumInit.settings.get("modreasons");
		request.setAttribute("modreasons",modreasons!=null?modreasons.split("\r\n"):"");
		return mapping.findForward("tothreads");
	}
	@SuppressWarnings("unchecked")
	public ActionForward batchModthreads(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		try{
			if(submitCheck(request, "modsubmit")){
				Map<String,String> settings = ForumInit.settings;
				HttpSession session = request.getSession();
				Members members = (Members) session.getAttribute("members");
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				List<String> validateList = new ArrayList<String>(); 
				StringBuffer deleteids = new StringBuffer();
				List<String> ignoreList = new ArrayList<String>(); 
				String auditing = request.getParameter("auditing");
				String[] audiArr = auditing.split(",");
				String boardurl = (String)request.getSession().getAttribute("boardurl");
				for (int i = 0; i < audiArr.length; i++) {
					String postsValue = request.getParameter(audiArr[i]);
					if (postsValue != null) {
						if (postsValue.equals("validate")) {
							List<Map<String,String>> threadpostlist = dataBaseService.executeQuery("select t.tid,t.fid,t.subject,t.digest,t.authorid,t.author,t.dateline,p.anonymous from jrun_threads t left join jrun_posts p on t.tid=p.tid where p.first=1 and t.tid="+audiArr[i]);
							Map<String,String>threadpost = threadpostlist.get(0);
							threadpostlist = null;
							List<Map<String,String>> forumsfildlist = dataBaseService.executeQuery("select f.fup,f.type,ff.postcredits,ff.digestcredits from jrun_forums f left join jrun_forumfields ff on f.fid=ff.fid where f.fid="+threadpost.get("fid"));
							Map<String,String> forummap = forumsfildlist.get(0);
							forumsfildlist = null;
							int authorid=Common.toDigit(threadpost.get("authorid"));
							String userName=null;
							if(authorid>0){
								Map<Integer, Integer> postcredits = dataParse.characterParse(forummap.get("postcredits"),false);
								String creditspolicy = settings.get("creditspolicy");
								Map creditspolicys=dataParse.characterParse(creditspolicy,false);
								if(postcredits==null||postcredits.size()<=0)
								{
									postcredits=(Map<Integer,Integer>)creditspolicys.get("post");
								}
								if (Common.toDigit(threadpost.get("digest")) > 0) {
									Map<Integer, Integer> digestcredits = dataParse.characterParse(forummap.get("digestcredits"),false);
									if(digestcredits==null||digestcredits.size()<=0)
									{
										digestcredits=(Map<Integer,Integer>)creditspolicys.get("digest");
									}
									if (Common.toDigit(threadpost.get("digest")) > 0) {
										Set<Integer> keys = digestcredits.keySet();
										for (Integer key : keys) {
											postcredits.put(key,(postcredits.get(key) != null ? postcredits.get(key) : 0)+ digestcredits.get(key));
										}
									}
								}
								Common.updatepostcredits("+", authorid, postcredits, Common.toDigit(threadpost.get("dateline")));
								Common.updatepostcredits(authorid, settings.get("creditsformula"));
								if(Common.toDigit(threadpost.get("anonymous"))>0){
									userName = "";
								}else{
									userName=threadpost.get("author");
								}
							}else{
								userName=getMessage(request, "anonymous");
							}
							String subject = threadpost.get("subject").replaceAll("\t", " ");
							String lastpost = threadpost.get("tid") + "\t" +Common.cutstr(Common.addslashes(subject), 40, null) + "\t" + threadpost.get("dateline") + "\t"+ userName;
							dataBaseService.runQuery("UPDATE jrun_forums set lastpost='"+lastpost+"',threads=threads+1,posts=posts+1 where fid="+threadpost.get("fid"));
							if (forummap.get("type").equals("sub")) {
								dataBaseService.runQuery("UPDATE jrun_forums SET lastpost='"+ lastpost + "' WHERE fid=" + forummap.get("fup"));
							}
							String pmsMessage = request.getParameter(toPmsPostspid(audiArr[i])).trim();
							if (!Common.empty(pmsMessage)) {
								Common.sendpm(authorid+"", getMessage(request,"modthreads_validate_subject"), getMessage(request, "modthreads_validate_message", boardurl,audiArr[i],subject,pmsMessage),  members.getUid()+"", members.getUsername(), timestamp);
							}
							validateList.add(audiArr[i]);
						}
						if (postsValue.equals("delete")) {
							String pms = request.getParameter(toPmsPostspid(audiArr[i])).trim();
							if (!Common.empty(pms)) {
								String subject = request.getParameter(toPostsSubject(audiArr[i])).trim();
								String authorid = request.getParameter(toAuthor(audiArr[i])).trim();
								Common.sendpm(authorid+"", getMessage(request,"modthreads_delete_subject"), getMessage(request, "modthreads_delete_message",subject,pms), members.getUid()+"", members.getUsername(), timestamp);
							}
							deleteids.append(audiArr[i]+",");
						}
						if (postsValue.equals("ignore")) {
							ignoreList.add(audiArr[i]);
						}
					}
				}
				if(deleteids.length()>0){
					deleteids.deleteCharAt(deleteids.length()-1);
					List<Map<String,String>> attalist = dataBaseService.executeQuery("select attachment,thumb,remote from jrun_attachments where tid in ("+ deleteids.toString() +")");
					String servletpath = JspRunConfig.realPath+settings.get("attachdir")+"/";
					for(Map<String,String>atta:attalist){
						Common.dunlink(atta.get("attachment"), Byte.valueOf(atta.get("thumb")), Byte.valueOf(atta.get("remote")), servletpath);
					}
					attalist=null;
					dataBaseService.runQuery("DELETE FROM jrun_attachments WHERE tid IN ("+deleteids.toString()+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_posts WHERE tid IN ("+deleteids.toString()+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_polloptions WHERE tid IN ("+deleteids.toString()+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_polls WHERE tid IN ("+deleteids.toString()+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_trades WHERE tid IN ("+deleteids.toString()+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_threads WHERE tid IN ("+deleteids.toString()+")",true);
				}
				postsService.ignorePostsIDArray(ignoreList);
				List<Posts> valiPostsList = postsService.validatePostsIDArray(validateList);
				if (valiPostsList != null && valiPostsList.size() > 0)
				request.setAttribute("validateList", valiPostsList);
				validateList = null;
				ignoreList =null;
				request.setAttribute("notfirst", "notfirst");
				String sql="SELECT fid, name FROM jrun_forums WHERE type <> 'group' AND status=1";
				if(members.getAdminid()==3){
					sql = "SELECT m.fid, f.name FROM jrun_moderators m LEFT JOIN jrun_forums f ON f.fid=m.fid  WHERE m.uid='"+members.getUid()+"'";
				}
				StringBuffer fids = new StringBuffer();
				List<Map<String,String>> forumList=dataBaseService.executeQuery(sql);
				request.setAttribute("forumList", forumList);
				if(members.getAdminid()==3){
					for(Map<String,String> forums : forumList){
						 fids.append(","+forums.get("fid"));
					}
				}
				PostsPageForm ppf = new PostsPageForm(fids.toString());
				MessageResources resources = getResources(request);
				Locale locale = getLocale(request);
				Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
				Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
				Map<String, Map<String,String>> forumdatas = null;
				if(forumMap!=null){
					forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
				}
				ppf.setList(request.getContextPath(),resources,locale,forumdatas);
				request.setAttribute("postsPageForm", ppf);
				String modreasons=settings.get("modreasons");
				request.setAttribute("modreasons",modreasons!=null?modreasons.split("\r\n"):"");
				return mapping.findForward("tothreads");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=modthreads");
		return null;
	}
	private String toPmsPostspid(String audiArr) {
		StringBuffer sb = new StringBuffer("pm_");
		sb.append(audiArr);
		return sb.toString();
	}
	private String toPostsSubject(String audiArr) {
		StringBuffer sb = new StringBuffer("hidden_");
		sb.append(audiArr);
		return sb.toString();
	}
	private String toAuthor(String audiArr) {
		StringBuffer sb = new StringBuffer("author_");
		sb.append(audiArr);
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	public ActionForward pagePosts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Members member = (Members)request.getSession().getAttribute("members");
		String page = request.getParameter("page").trim();
		String filter = request.getParameter("filter");
		String modfid = request.getParameter("modfid");
		PostsForm pf = new PostsForm();
		if (!Common.empty(filter)) {
			pf.setFilter(filter.trim());
		}
		if (!Common.empty(modfid)) {
			if(member.getAdminid()==3&&Common.toDigit(modfid)>0){
				List<Map<String,String>> moderator = dataBaseService.executeQuery("select fid from jrun_moderators where fid='"+modfid+"' and uid='"+member.getUid()+"'");
				if(moderator==null || moderator.size()<=0){
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_post_moderate_no_access_this");
					return mapping.findForward("message");
				}
			}
			pf.setFid(Short.valueOf(modfid));
		}
		request.setAttribute("pastsPage", pf); 
		PostsPageForm ppf = postsService.fidnByForums(pf); 
		String sql="SELECT f.fid,f.name FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) WHERE f.type <>'group' AND f.status>0";
		if(member.getAdminid()==3){
			sql = "SELECT m.fid, f.name FROM jrun_moderators m LEFT JOIN jrun_forums f ON f.fid=m.fid  WHERE m.uid='"+member.getUid()+"'";
		}
		List<Map<String,String>> forumList=dataBaseService.executeQuery(sql);
		if(forumList==null||forumList.size()<=0){
			request.setAttribute("return", true);
			request.setAttribute("message_key", "a_post_moderate_no_access_all");
			return mapping.findForward("message");
		}
		request.setAttribute("forumList", forumList);
		if(member.getAdminid()==3&&pf.getFid()<=0){
			StringBuffer fids = new StringBuffer();
			for(Map<String,String> forums:forumList){
				fids.append(","+forums.get("fid"));
			}
			String displayorder = "";
			if (pf.getFilter().equals("normal")) {
				displayorder = "-2";
			}
			if (pf.getFilter().equals("ignore")) {
				displayorder = "-3";
			}
			String currsql = "select count(*) count from jrun_threads as t where t.displayorder=" + displayorder+ " AND t.fid in ("+fids.substring(1)+")";
			String queryStr = "select t.tid, t.fid, t.author, t.authorid, t.subject, t.dateline,p.pid, p.message, p.useip, p.attachment from jrun_threads t LEFT JOIN jrun_posts p ON p.tid=t.tid WHERE t.displayorder=" + displayorder+ " ADN t.fid in ("+fids.substring(1)+") GROUP BY t.tid ORDER BY t.dateline DESC ";
			ppf = new PostsPageForm(currsql,queryStr);
		}
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
		Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
		Map<String, Map<String,String>> forumdatas = null;
		if(forumMap!=null){
			forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
		}
		ppf.setCurrentPage(Integer.valueOf(page)); 
		ppf.setList(request.getContextPath(),resources,locale,forumdatas);
		request.setAttribute("postsPageForm", ppf); 
		String modreasons=ForumInit.settings.get("modreasons");
		request.setAttribute("modreasons",modreasons!=null?modreasons.split("\r\n"):"");
		return mapping.findForward("tothreads");
	}
	@SuppressWarnings("unchecked")
	public ActionForward pageModreplies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Members member = (Members)request.getSession().getAttribute("members");
		String page = request.getParameter("page").trim();
		String filter = request.getParameter("filter");
		String modfid = request.getParameter("modfid");
		PostsForm pf = new PostsForm();
		if (!Common.empty(filter)) {
			pf.setFilter(filter.trim());
		}
		if (!Common.empty(modfid)) {
			if(member.getAdminid()==3&&Common.toDigit(modfid)>0){
				List<Map<String,String>> moderator = dataBaseService.executeQuery("select fid from jrun_moderators where fid='"+modfid+"' and uid='"+member.getUid()+"'");
				if(moderator==null || moderator.size()<=0){
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_post_moderate_no_access_this");
					return mapping.findForward("message");
				}
			}
			pf.setFid(Short.valueOf(modfid));
		}
		request.setAttribute("pastsPage", pf);
		ModrepliesPageForm mpf = postsService.fidnByModreplies(pf);
		String sql="SELECT f.fid,f.name FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) WHERE f.type <>'group' AND f.status>0";
		if(member.getAdminid()==3){
			sql = "SELECT m.fid, f.name FROM jrun_moderators m LEFT JOIN jrun_forums f ON f.fid=m.fid  WHERE m.uid='"+member.getUid()+"'";
		}
		List<Map<String,String>> forumList=dataBaseService.executeQuery(sql);
		if(forumList==null||forumList.size()<=0){
			request.setAttribute("return", true);
			request.setAttribute("message_key", "a_post_moderate_no_access_all");
			return mapping.findForward("message");
		}
		request.setAttribute("forumList", forumList);
		if(member.getAdminid()==3&&pf.getFid()<=0){
			StringBuffer fids = new StringBuffer();
			for(Map<String,String> forums: forumList){
				fids.append(","+forums.get("fid"));
			}
			String invisible = "";
			if (pf.getFilter().equals("normal")) {
				invisible="-2";
			}
			if (pf.getFilter().equals("ignore")) {
				invisible="-3";
			}
			String countsql = "select count(*) count from jrun_posts as p where p.first=0 and p.invisible="+invisible+" and p.fid in ("+fids.substring(1)+")";
			String querysql = "select p.*,t.subject as threadsubject from jrun_posts as p left join jrun_threads as t on t.tid=p.tid where p.first=0 and p.invisible="+invisible+" and p.fid in ("+fids.substring(1)+")";
			mpf = new ModrepliesPageForm(countsql,querysql);
		}
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		mpf.setCurrentPage(Integer.valueOf(page));
		Map<String, Map<String,String>> forumdatas = null;
		Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
		Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
		if(forumMap!=null){
			forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
		}
		mpf.setList(request.getContextPath(),resources,locale,forumdatas);
		request.setAttribute("modrepliesPageForm", mpf);
		String modreasons=ForumInit.settings.get("modreasons");
		request.setAttribute("modreasons",modreasons!=null?modreasons.split("\r\n"):"");
		return mapping.findForward("toreplies");
	}
	@SuppressWarnings("unchecked")
	public ActionForward auditingNewModreplies(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Members member = (Members)request.getSession().getAttribute("members");
		String filter = request.getParameter("filter");
		String modfid = request.getParameter("modfid");
		PostsForm pf = new PostsForm();
		if (!Common.empty(filter)) {
			pf.setFilter(filter.trim());
		}
		if (!Common.empty(modfid)) {
			if(member.getAdminid()==3&&Common.toDigit(modfid)>0){
				List<Map<String,String>> moderator = dataBaseService.executeQuery("select fid from jrun_moderators where fid='"+modfid+"' and uid='"+member.getUid()+"'");
				if(moderator==null || moderator.size()<=0){
					request.setAttribute("return",true);
					request.setAttribute("message_key", "a_post_moderate_no_access_this");
					return mapping.findForward("message");
				}
			}
			pf.setFid(Short.valueOf(modfid));
		}
		request.setAttribute("pastsPage", pf);
		String sql="SELECT f.fid,f.name FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) WHERE f.type <>'group' AND f.status>0";
		if(member.getAdminid()==3){
			sql = "SELECT m.fid, f.name FROM jrun_moderators m LEFT JOIN jrun_forums f ON f.fid=m.fid  WHERE m.uid='"+member.getUid()+"'";
		}
		List<Map<String,String>> forumList=dataBaseService.executeQuery(sql);
		if(forumList==null||forumList.size()<=0){
			request.setAttribute("return", true);
			request.setAttribute("message_key", "a_post_moderate_no_access_all");
			return mapping.findForward("message");
		}
		request.setAttribute("forumList", forumList);
		ModrepliesPageForm mpf = postsService.fidnByModreplies(pf);
		if(member.getAdminid()==3&&pf.getFid()<=0){
			StringBuffer fids = new StringBuffer();
			for(Map<String,String> forums: forumList){
				fids.append(","+forums.get("fid"));
			}
			String invisible = "";
			if (pf.getFilter().equals("normal")) {
				invisible="-2";
			}
			if (pf.getFilter().equals("ignore")) {
				invisible="-3";
			}
			String countsql = "select count(*) count from jrun_posts as p where p.first=0 and p.invisible="+invisible+" and p.fid in ("+fids.substring(1)+")";
			String querysql = "select p.*,t.subject as threadsubject from jrun_posts as p left join jrun_threads as t on t.tid=p.tid where p.first=0 and p.invisible="+invisible+" and p.fid in ("+fids.substring(1)+")";
			mpf = new ModrepliesPageForm(countsql,querysql);
		}
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		Map<String, Map<String,String>> forumdatas = null;
		Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
		Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
		if(forumMap!=null){
			forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
		}
		mpf.setList(request.getContextPath(),resources,locale,forumdatas);
		request.setAttribute("modrepliesPageForm", mpf);
		String modreasons=ForumInit.settings.get("modreasons");
		request.setAttribute("modreasons",modreasons!=null?modreasons.split("\r\n"):"");
		return mapping.findForward("toreplies");
	}
	@SuppressWarnings("unchecked")
	public ActionForward workAllModreplies(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		try{
			if(submitCheck(request, "modsubmit")){
				HttpSession session = request.getSession();
				Map<String,String> settings = ForumInit.settings;
				int num = 0;
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				Members members = (Members)session.getAttribute("members");
				List<Posts> validateList = new ArrayList<Posts>();
				List<String> deleteList = new ArrayList<String>();
				List<String> ignoreList = new ArrayList<String>();
				String auditing = request.getParameter("auditing");
				String[] audiArr = auditing.split(",");
				String barckurl = (String)session.getAttribute("boardurl");
				for (int i = 0; i < audiArr.length; i++) {
					String postsWork = request.getParameter(audiArr[i]);
					if (postsWork != null) {
						if (postsWork.equals("validate")) {
							num++;
							String pmsMessage = request.getParameter(toPmsPostspid(audiArr[i])).trim();
							Posts post = postsService.getPostsById(convertInt(audiArr[i]));
							if (pmsMessage != null && !pmsMessage.equals("")) {
								String message = request.getParameter(toPostsSubject(audiArr[i])).trim();
								String authorid = request.getParameter(toAuthor(audiArr[i])).trim();
								Common.sendpm(authorid, getMessage(request, "modreplies_validate_subject"), getMessage(request, "modreplies_validate_message",barckurl,post.getTid()+"",message,pmsMessage), members.getUid()+"", members.getUsername(), timestamp);
							}
							List<Map<String,String>> forumsfildlist = dataBaseService.executeQuery("select f.fup,f.type,ff.postcredits from jrun_forums f left join jrun_forumfields ff on f.fid=ff.fid where f.fid="+post.getFid());
							Map<String,String> forummap = forumsfildlist.get(0);
							forumsfildlist = null;
							String creditspolicy =settings.get("creditspolicy");
							Map creditspolicys=dataParse.characterParse(creditspolicy,false);
							Map<Integer, Integer> postcredits = dataParse.characterParse(forummap.get("replycredits"),false);
							if(postcredits==null||postcredits.size()<=0)
							{
								postcredits=(Map<Integer,Integer>)creditspolicys.get("reply");
							}
							String author=null;
							if(post.getAuthorid()>0)
							{
								Common.updatepostcredits("+", post.getAuthorid(), postcredits, post.getDateline());
								Common.updatepostcredits( post.getAuthorid(), settings.get("creditsformula"));
								if(post.getAnonymous()>0){
									author="";
								}else{
									author=post.getAuthor();
								}
							}
							else{
								author=getMessage(request, "anonymous");
							}
							String subject = request.getParameter("threadsubject").replaceAll("\t", " ");
							subject = Common.htmlspecialchars(subject);
							String lastpost = post.getTid() + "\t" +Common.cutstr(Common.addslashes(subject), 40, null) + "\t" + post.getDateline() + "\t"+author;
							dataBaseService.runQuery("update jrun_threads set lastpost="+post.getDateline()+",lastposter='"+author+"',replies=replies+1 where tid="+post.getTid());
							dataBaseService.runQuery("update jrun_forums set lastpost='"+lastpost+"',posts=posts+1 where fid="+post.getFid());
							if(forummap.get("type").equals("sub")){
								dataBaseService.runQuery("update jrun_forums set lastpost='"+lastpost+"',posts=posts+1 where fid="+forummap.get("fup"));
							}
							validateList.add(post);
						}
						if (postsWork.equals("delete")) {
							String pms = request.getParameter(toPmsPostspid(audiArr[i]));
							if (pms != null && !pms.equals("")) {
								String tid = request.getParameter("tid_"+audiArr[i]);
								String message = request.getParameter(toPostsSubject(audiArr[i])).trim();
								String authorid = request.getParameter(toAuthor(audiArr[i])).trim();
								Common.sendpm(authorid, getMessage(request, "modreplies_delete_subject"), getMessage(request, "modreplies_delete_message",barckurl,tid,message,pms), members.getUid()+"", members.getUsername(), timestamp);
							}
							deleteList.add(audiArr[i]);
						}
						if (postsWork.equals("ignore")) {
							ignoreList.add(audiArr[i]);
						}
					}
				}
				if(deleteList.size()>0){
					StringBuffer pids = new StringBuffer();
					for(String pid:deleteList){
						pids.append(pid+",");
					}
					pids.deleteCharAt(pids.length()-1);
					List<Map<String,String>>attalist = dataBaseService.executeQuery("SELECT attachment,thumb,remote FROM jrun_attachments WHERE pid IN ("+pids.toString()+")");
					if(attalist!=null && attalist.size()>0){
						String path = JspRunConfig.realPath+settings.get("attachdir")+"/";
						for(Map<String,String>atta:attalist){
							Common.dunlink(atta.get("attachment"), Byte.valueOf(atta.get("thumb")), Byte.valueOf(atta.get("remote")), path);
						}
						attalist=null;
					}
				}
				postsService.deleteModrepliesIDArray(deleteList);
				postsService.ignoreModrepliesIDArray(ignoreList);
				postsService.validateModrepliesIDArray(validateList);
				validateList=null;deleteList=null;ignoreList=null;
				String successInfo = getMessage(request, "a_post_moderate_replies_succeed", ""+num);
				request.setAttribute("message", successInfo);
				request.setAttribute("url_forward", "admincp.jsp?action=modreplies");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=modreplies");
		return null;
	}
	public ActionForward toThreadsForum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		String forumid = null;
		if(session.getAttribute("threadsForm")!=null){
			forumid = ((ThreadsForm)session.getAttribute("threadsForm")).getInforum()+"";
		}
		List<Map<String,String>> threadtypelist = dataBaseService.executeQuery("select typeid,name,description from jrun_threadtypes");
		request.setAttribute("threadtype", threadtypelist);
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",forumid));
		return mapping.findForward("toThreadsForum");
	}
	public ActionForward toPruneForum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		if(member!=null&&member.getAdminid()==3){
			StringBuffer fidsbuffer = new StringBuffer();
			List<Map<String,String>> fidslist = dataBaseService.executeQuery("SELECT fid FROM jrun_moderators WHERE uid='"+member.getUid()+"'");
			for(Map<String,String> fids:fidslist){
				fidsbuffer.append(","+fids.get("fid"));
			}
			if(fidsbuffer.length()>0){
				List<Map<String,String>> forumList=dataBaseService.executeQuery("SELECT name from jrun_forums where  fid in ( "+fidsbuffer.substring(1)+" ) ORDER BY type, displayorder");
				StringBuffer forumname = new StringBuffer();
				for(Map<String,String> forums:forumList){
					forumname.append(","+forums.get("name"));
				}
				if(forumname.length()>0){
					request.setAttribute("forumselect", forumname.substring(1));
				}else{
					request.setAttribute("forumselect", getMessage(request, "none"));
				}
			}
		}else{
			request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
		}
		String timeoffset=(String)session.getAttribute("timeoffset");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		if (member != null) {
			if (member.getAdminid() != 1) {
				request.setAttribute("isAdmin", "admin");
			}
		}
		String satrtTime = Common.gmdate("yyyy-MM-dd", timestamp-86400*7,timeoffset); 
		String endTime = Common.gmdate("yyyy-MM-dd", timestamp,timeoffset);
		request.setAttribute("startTime", satrtTime);
		request.setAttribute("endTime", endTime);
		session.removeAttribute("pf");
		return mapping.findForward("toPruneForum");
	}
	public ActionForward forumrecommend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String fid = request.getParameter("fid");
		HttpSession session=request.getSession();
		byte adminid = (Byte)session.getAttribute("jsprun_adminid");
		int uid = (Integer)session.getAttribute("jsprun_uid");
		if(fid==null){
			List<Map<String,String>> forumlist=null;
			if(adminid==3){
				forumlist=dataBaseService.executeQuery("SELECT f.name, f.fid FROM jrun_moderators m, jrun_forums f	WHERE m.uid='"+uid+"' AND m.fid=f.fid ORDER BY f.displayorder");
			}else{
				forumlist=dataBaseService.executeQuery("SELECT name, fid FROM jrun_forums WHERE type!='group' ORDER BY displayorder");
			}
			StringBuffer forums =new StringBuffer();
			forums.append("<select onchange=\"window.location=(\'admincp.jsp?action=forumrecommend&amp;fid=\'+this.options[this.selectedIndex].value);\"><option value=\"\">"+getMessage(request, "none")+"</option>");
			for (Map<String, String> forum : forumlist) {
				forums.append("<option value=\""+forum.get("fid")+"\">"+Common.strip_tags(forum.get("name"))+"</option>");
			}
			forums.append("</select>");
			request.setAttribute("forums", forums);
			return mapping.findForward("toforumrecommend");
		}else{
			int page = Math.max(Common.intval(request.getParameter("page")),1);
			try{
				if(submitCheck(request, "recommendsubmit")){
					String[] delete=request.getParameterValues("delete");
					if(delete!=null){
						String threadtids=Common.implodeids(delete);
						if(adminid>2){
							List<Map<String,String>> tids=dataBaseService.executeQuery("SELECT tid FROM jrun_forumrecommend WHERE fid='"+fid+"' AND moderatorid='"+uid+"' AND tid IN ("+threadtids+")");
							StringBuffer tempThreadtids=new StringBuffer("0");
							for (Map<String, String> tid : tids) {
								tempThreadtids.append(","+tid.get("tid"));
							}
							threadtids = tempThreadtids.toString();
						}
						dataBaseService.runQuery("DELETE FROM jrun_forumrecommend WHERE tid IN ("+threadtids+")", true);
					}
					String[] tids=request.getParameterValues("tids");
					if(tids!=null){
						for (String tid : tids) {
							int displayorder=Common.range(Common.intval(request.getParameter("displayorder_"+tid)), 127, -128);
							dataBaseService.runQuery("UPDATE jrun_forumrecommend SET displayorder='"+displayorder+"' WHERE tid='"+tid+"'",true);
						}
					}
					request.setAttribute("message_key", "a_post_forums_recommend_succeed");
					request.setAttribute("url_forward", "admincp.jsp?action=forumrecommend&amp;fid="+fid+"&amp;page="+page);
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
			String useradd="";
			if(adminid==3){
				int count=Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_moderators WHERE uid='"+uid+"' AND fid='"+fid+"'").get(0).get("count"));
				if(count==0){
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_post_forums_recommend_error");
					return mapping.findForward("message");
				}
				useradd="AND moderatorid='"+uid+"'";
			}
			Map<String, String> settings = ForumInit.settings;
			Members member = uid > 0 ? (Members)session.getAttribute("user") : null;
			int threadcount = Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_forumrecommend WHERE fid='"+fid+"' "+useradd).get(0).get("count"));
			int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
			Map<String,Integer> multiInfo=Common.getMultiInfo(threadcount, tpp, page);
			page=multiInfo.get("curpage");
			int start_limit=multiInfo.get("start_limit");
			Map<String,Object> multi=Common.multi(threadcount, tpp, page, "admincp.jsp?action=forumrecommend&fid="+fid, 0, 10, true, false, null);
			request.setAttribute("multi", multi);
			List<Map<String,String>> threadlist=dataBaseService.executeQuery("SELECT c.*, m.username, m.uid ,f.name FROM jrun_forumrecommend c LEFT JOIN jrun_forums f ON c.fid=f.fid LEFT JOIN jrun_members m ON c.moderatorid=m.uid WHERE f.fid='"+fid+"' "+useradd+" LIMIT "+start_limit+","+tpp);
			if(threadlist!=null&&threadlist.size()>0){
				String timeoffset=(String)session.getAttribute("timeoffset");
				String timeformat=(String)session.getAttribute("timeformat");
				String dateformat=(String)session.getAttribute("dateformat");
				SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat,timeoffset);
				for (Map<String, String> thread : threadlist) {
					thread.put("author", thread.get("author").length()>0 ? thread.get("author") : getMessage(request, "guest"));
					thread.put("username", thread.get("username")!=null ? "<a href=\"space.jsp?uid="+thread.get("uid")+"\" target=\"_blank\">"+thread.get("username")+"</a>" : "System");
					int expiration=Integer.valueOf(thread.get("expiration"));
					thread.put("expiration",expiration>0?Common.gmdate(sdf_all, Integer.valueOf(thread.get("expiration"))) : getMessage(request, "nolimit"));
				}
				request.setAttribute("threadlist", threadlist);
			}
			request.setAttribute("page",page);
			request.setAttribute("fid",fid);
			return mapping.findForward("toforumrecommend");
		}
	}
	public ActionForward batchWords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "censorsubmit")){
				HttpSession session = request.getSession();
				List<Words> updateFindList = new ArrayList<Words>();
				List<Words> updateReplaceList = new ArrayList<Words>();
				String hiddenFindArray = request.getParameter("findArray");
				String hiddenReplaceArray = request.getParameter("replaceArray");
				updateFindList = updateListWord(request, hiddenFindArray,updateFindList, true);
				updateReplaceList = updateListWord(request, hiddenReplaceArray,updateReplaceList, false);
				wordsService.updateWordsList(updateFindList, updateReplaceList);
				String[] deleteArray = request.getParameterValues("delete[]");
				wordsService.delteCollection(deleteArray);
				String newFind = request.getParameter("newfind");
				String newreplace = request.getParameter("newreplace");
				if (newFind != null && !newFind.equals("")&&newFind.getBytes().length < 3) {
					request.setAttribute("return", true);
					request.setAttribute("message_key", "a_post_censor_keywords_tooshort");
					return mapping.findForward("message");
				}
				Members members = (Members)session.getAttribute("members");
				String username = members!=null?members.getUsername():"";
				wordsService.saveWords(getWords(newFind, newreplace,username));
				updateFindList = null;updateReplaceList=null;deleteArray=null;
				Cache.updateCache("censor");
				request.setAttribute("message_key", "a_post_censor_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=censor");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=censor");
		return null;
	}
	@SuppressWarnings("deprecation")
	public ActionForward downWords(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
		response.setHeader("Last-Modified",Common.gmdate("EEE, d MMM yyyy HH:mm:ss", timestamp, "0")+" GMT");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Content-Disposition","attachment; filename=CensorWords.txt");
		response.setHeader("Content-Type","application/octet-stream");
		List<Map<String,String>> wordslist = dataBaseService.executeQuery("SELECT find, replacement FROM jrun_words ORDER BY id");
		if(wordslist!=null && wordslist.size()>0){
			try {
				StringBuffer exportContent=new StringBuffer();
				for(Map<String,String> words:wordslist){
					String replacement = words.get("replacement").replace("*", "");
					String str = words.get("find")+(replacement.equals("")?"":"="+replacement)+"\n";
					exportContent.append(str);
				}
				byte[] content=exportContent.toString().getBytes(JspRunConfig.CHARSET);
				response.setHeader("Content-Length", String.valueOf(content.length));
				OutputStream os=response.getOutputStream();
				os.write(content);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public ActionForward pageWords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int currentpage = 1;
		String page = request.getParameter("page");
		currentpage = page == null || page.equals("") ? 1 : cn.jsprun.utils.FormDataCheck.isNum(page) ? Integer.valueOf(page) : 1;
		int totalsize = Common.toDigit(dataBaseService.executeQuery("select count(*) as count from jrun_words").get(0).get("count"));
		LogPage loginpage = new LogPage(totalsize,30,currentpage);
		int beginsize = (currentpage-1)*30;
		List<Map<String,String>> wordslist = dataBaseService.executeQuery("select * from jrun_words limit "+beginsize+",30");
		request.setAttribute("wordslist",wordslist);
		request.setAttribute("logpage", loginpage);
		return mapping.findForward("towords");
	}
	private Words getWords(String find, String replace,String username) {
		if (find == null || find.equals(""))
			return null;
		if (replace == null || replace.equals(""))
			replace = "";
		Words words = new Words();
		words.setAdmin(username);
		words.setFind(find);
		words.setReplacement(replace);
		return words;
	}
	private List<Words> updateListWord(HttpServletRequest request,
			String hidden, List<Words> updateList, boolean find) {
		if (hidden != null && !hidden.equals("")) {
			Object[] findArray = hidden.split(",");
			if (findArray.length >= 1) {
				for (int i = 0; i < findArray.length; i++) {
					String findWeb = findArray[i].toString();
					if (findWeb != null && findWeb.length() > 3) {
						String updatefind = request.getParameter(findWeb);
						if (updatefind != null && !updatefind.equals("")) {
							Words w = new Words();
							w.setId(Short.valueOf(findWeb.substring(findWeb.lastIndexOf("[") + 1, findWeb.lastIndexOf("]"))));
							if (find == true) {
								w.setFind(updatefind);
							} else {
								w.setReplacement(updatefind.trim());
							}
							updateList.add(w);
						}
					}
				}
			}
			findArray = null;
		}
		return updateList;
	}
	public ActionForward batchWordsTextarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "addcensorsubmit")){
				int ignore = 0;
				List<Words> saveList = new ArrayList<Words>();
				HttpSession session = request.getSession(); 
				String overwrite = null; 
				String addcensors = request.getParameter("addcensors");
				Members members = (Members)session.getAttribute("members");
				String username = members==null?"":members.getUsername();
				overwrite = request.getParameter("overwrite");
				byte adminid= (Byte)session.getAttribute("jsprun_adminid");
				if (addcensors == null || addcensors.equals("")) {
					Common.requestforward(response, "admincp.jsp?action=censor");
					return null;
				}
				if(adminid==1 && overwrite.equals("2")){
					dataBaseService.runQuery("TRUNCATE jrun_words");
				}
				Object[] strArray = addcensors.trim().split("\\n");
				for (int i = 0; i < strArray.length; i++) {
					Words w = new Words();
					w.setAdmin(username);
					Object[] s = strArray[i].toString().trim().split("=");
					String find = s[0].toString().trim();
					if (s.length == 1) {
						w.setFind(find); 
						w.setReplacement("**");
					}
					if (s.length >= 2) {
						String replacement = s[1].toString().trim();
						w.setFind(find);
						w.setReplacement(replacement);
					}
					if (find != null && !find.equals("") && find.getBytes().length >= 3) {
						saveList.add(w);
					} else {
						ignore++;
					}
				}
				int save = 0; 
				int update = 0;
				if (overwrite.equals("0")) {
					Integer[] countNum = wordsService.saveWordsList(saveList);
					save = countNum[0];
					ignore = ignore + countNum[1];
				}
				if (overwrite.equals("1")) {
					Integer[] countNum = wordsService.updateAndSave(saveList);
					save = countNum[0];
					update = countNum[1];
				}
				if (overwrite.equals("2")) {
					save = wordsService.deleteAndSave(saveList);
				}
				String successInfo = getMessage(request, "a_post_censor_batch_add_succeed", save+"",update+"",ignore+"");
				strArray = null;
				Cache.updateCache("censor");
				request.setAttribute("message", successInfo);
				request.setAttribute("url_forward", "admincp.jsp?action=censor");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=censor");
		return null;
	}
	public ActionForward batchAttachtypes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "typesubmit")){
				List<Attachtypes> extendsionList = new ArrayList<Attachtypes>();
				List<Attachtypes> maxsizeList = new ArrayList<Attachtypes>();
				String updateExtends = request.getParameter("extensionArray");
				extendsionList = getAttachtypesExtensionList(request, updateExtends);
				attachtypesService.updateExtensionList(extendsionList);
				String updateMaxsize = request.getParameter("maxsizeArray");
				maxsizeList = getAttachtypesMaxsizeList(request, updateMaxsize);
				attachtypesService.updateMaxsizeList(maxsizeList);
				String[] ids = request.getParameterValues("delete[]");
				if (ids != null) {
					attachtypesService.deleteList(ids);
				}
				String extension = request.getParameter("newextension");
				String maxsize = request.getParameter("newmaxsize");
				if (FormDataCheck.isValueString(extension)) {
					if (attachtypesService.isSave(extension)) {
						attachtypesService.saveAttachtypes(getAttachtypes(extension,maxsize));
					} else {
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_post_attachtypes_duplicate");
						return mapping.findForward("message");
					}
				}
				extendsionList = null;maxsizeList=null;
				request.setAttribute("message_key", "a_post_attachtypes_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=attachtypes");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=attachtypes");
		return null;
	}
	private Attachtypes getAttachtypes(String extension, String maxsize) {
		Attachtypes a = new Attachtypes();
		a.setExtension(extension);
		try {
			a.setMaxsize(Integer.valueOf(maxsize));
		} catch (NumberFormatException nfe) {
			a.setMaxsize(0);
		}
		if (extension != null && !extension.equals("")) {
			return a;
		}
		return null;
	}
	private List<Attachtypes> getAttachtypesExtensionList(
			HttpServletRequest request, String updateString) {
		List<Attachtypes> list = new ArrayList<Attachtypes>();
		if (updateString != null && !updateString.equals("")) {
			Object[] findArray = updateString.split(",");
			if (findArray.length >= 1) {
				for (int i = 0; i < findArray.length; i++) {
					String findWeb = findArray[i].toString();
					if (findWeb != null) {
						String updatefind = request.getParameter(findWeb);
						if (updatefind != null && !updatefind.equals("")) {
							Attachtypes attachtypes = new Attachtypes();
							attachtypes.setId(Short.valueOf(findWeb.substring(findWeb.lastIndexOf("[") + 1, findWeb.lastIndexOf("]"))));
							updatefind = updatefind.trim();
							attachtypes.setExtension(updatefind);
							list.add(attachtypes);
						}
					}
				}
			}
			findArray = null;
		}
		return list;
	}
	private List<Attachtypes> getAttachtypesMaxsizeList(
			HttpServletRequest request, String updateString) {
		List<Attachtypes> list = new ArrayList<Attachtypes>();
		if (updateString != null && !updateString.equals("")) {
			Object[] findArray = updateString.split(",");
			if (findArray.length >= 1) {
				for (int i = 0; i < findArray.length; i++) {
					String findWeb = findArray[i].toString();
					if (findWeb != null) {
						String updatefind = request.getParameter(findWeb);
						if (updatefind != null) {
							Attachtypes attachtypes = new Attachtypes();
							attachtypes.setId(Short.valueOf(findWeb.substring(findWeb.lastIndexOf("[") + 1, findWeb.lastIndexOf("]"))));
							try {
								attachtypes.setMaxsize(Integer.valueOf(updatefind));
							} catch (NumberFormatException nfe) {
								attachtypes.setMaxsize(0);
							}
							list.add(attachtypes);
						}
					}
				}
			}
			findArray = null;
		}
		return list;
	}
	public ActionForward addIcons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "iconsubmit")){
				String[] num = request.getParameterValues("addcheck[]");
				List<Smilies> list = new ArrayList<Smilies>();
				if (num != null) {
					for (int i = 0; i < num.length; i++) {
						int number = Integer.valueOf(num[i]);
						Smilies s = new Smilies();
						String url = request.getParameter("addurl[" + number + "]");
						String display = request.getParameter("adddisplayorder["+ number + "]");
						short dis = (short)Common.range(Common.intval(display), 127, 0);
						s.setTypeid(Short.valueOf("0"));
						s.setDisplayorder(dis);
						s.setType("icon");
						s.setCode("");
						s.setUrl(url);
						if (url != null) {
							list.add(s);
						}
					}
					smilieService.saveList(list);
				}
				list = null;
				Cache.updateCache("icons");
				request.setAttribute("message_key", "a_post_thread_icon_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=icons");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=icons");
		return null;
	}
	public ActionForward batchIcons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "iconsubmit")){
				String updateicons = request.getParameter("updateicons");
				smilieService.updateDisplayorderIcons(getSmiliesDisplayorder(request,updateicons));
				String[] deleteIcons = request.getParameterValues("delete[]");
				smilieService.deleteIcons(deleteIcons);
				Cache.updateCache("icons");
				request.setAttribute("message_key", "a_post_thread_icon_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=icons");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=icons");
		return null;
	}
	private List<Smilies> getSmiliesDisplayorder(HttpServletRequest request,String icons) {
		List<Smilies> list = new ArrayList<Smilies>();
		if (icons != null && !icons.equals("")) {
			Object[] findArray = icons.split(",");
			if (findArray.length >= 1) {
				for (int i = 0; i < findArray.length; i++) {
					String findWeb = findArray[i].toString();
					if (findWeb != null) {
						String updatefind = request.getParameter(findWeb);
						if (updatefind != null) {
							Smilies smilies = new Smilies();
							smilies.setId(Short.valueOf(findWeb.substring(findWeb.lastIndexOf("[") + 1, findWeb.lastIndexOf("]"))));
							try {
								Short s = Short.valueOf(updatefind);
								if (s > 127) {
									smilies.setDisplayorder(Short.valueOf("127"));
								} else {
									smilies.setDisplayorder(s);
								}
							} catch (NumberFormatException nfe) {
								smilies.setDisplayorder(Short.valueOf("0"));
							}
							list.add(smilies);
						}
					}
				}
			}
			findArray = null;
		}
		return list;
	}
	public ActionForward toAttachments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		session.removeAttribute("attaforms");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
		return mapping.findForward("toAttachments");
	}
	public ActionForward batchBbcodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "bbcodessubmit")){
				String[] deleteIds = request.getParameterValues("delete[]");
				String hiddenCount = request.getParameter("hiddenSB");
				String newtag = request.getParameter("newtag"); 
				String newicon = request.getParameter("newicon");
				String hiddenTag = request.getParameter("updateTag");
				String hiddenIcon = request.getParameter("updateIcon");
				List<Bbcodes> tagList = new ArrayList<Bbcodes>(); 
				List<Bbcodes> iconList = new ArrayList<Bbcodes>();
				List<Bbcodes> availableList = new ArrayList<Bbcodes>();
				if (hiddenCount != null && !hiddenCount.equals("")) {
					String[] ids = hiddenCount.split(",");
					if (ids != null && ids.length >= 1) {
						for (int i = 0; i < ids.length; i++) {
							String tag = request.getParameter(getAvailable(ids[i]));
							Bbcodes bbcodes = new Bbcodes();
							if (tag != null && !tag.equals("")) {
								if (tag.equals("1")) {
									bbcodes.setAvailable(Byte.valueOf("1"));
								} else {
									bbcodes.setAvailable(Byte.valueOf("0"));
								}
							} else {
								bbcodes.setAvailable(Byte.valueOf("0"));
							}
							bbcodes.setId(Integer.valueOf(ids[i]));
							availableList.add(bbcodes);
						}
					}
					bbcodesService.updateAvailableArray(availableList);
				}
				if (hiddenTag != null && !hiddenTag.equals("")) {
					String[] updateTags = hiddenTag.split(",");
					if (updateTags != null && updateTags.length >= 1) {
						for (int i = 0; i < updateTags.length; i++) {
							String tag = request.getParameter(updateTags[i]);
							if (tag != null && !tag.equals("")) {
								if(!Common.matches(tag, "^[0-9a-z]+$") && tag.length()<20){
									request.setAttribute("return", true);
									request.setAttribute("message_key", "a_post_jspruncodes_edit_tag_invalid");
									return mapping.findForward("message");
								}
								Bbcodes bbcodes = new Bbcodes();
								bbcodes.setTag(tag);
								bbcodes.setId(Integer.valueOf(updateTags[i].substring(updateTags[i].indexOf("[") + 1, updateTags[i].indexOf("]"))));
								tagList.add(bbcodes);
							}
						}
					}
					bbcodesService.updateTagArray(tagList);
				}
				if (hiddenIcon != null && !hiddenIcon.equals("")) {
					String[] updateTags = hiddenIcon.split(",");
					if (updateTags != null && updateTags.length >= 1) {
						for (int i = 0; i < updateTags.length; i++) {
							String tag = request.getParameter(updateTags[i]);
							if (tag != null && !tag.equals("")) {
								Bbcodes bbcodes = new Bbcodes();
								bbcodes.setIcon(tag);
								bbcodes.setId(Integer.valueOf(updateTags[i].substring(updateTags[i].indexOf("[") + 1, updateTags[i].indexOf("]"))));
								iconList.add(bbcodes);
							}
						}
					}
					bbcodesService.updateIconArray(iconList);
				}
				if (newtag != null && !newtag.equals("") && newicon != null) {
					if(!Common.matches(newtag, "^[0-9a-z]+$") && newtag.length()<20){
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_post_jspruncodes_edit_tag_invalid");
						return mapping.findForward("message");
					}
					Bbcodes code = new Bbcodes();
					code.setTag(newtag.trim());
					code.setIcon(newicon.trim());
					code.setAvailable(Byte.valueOf("0"));
					code.setReplacement("");
					code.setExample("");
					code.setExplanation("");
					code.setParams(Byte.valueOf("1"));
					code.setPrompt("");
					code.setNest(Short.valueOf("1"));
					bbcodesService.saveBbcodes(code);
				}
				if (deleteIds != null) {
					bbcodesService.deleteArray(deleteIds);
				}
				deleteIds = null;tagList = null;iconList=null;availableList=null;
				Cache.updateCache("post");
				request.setAttribute("message_key","a_post_jspruncodes_edit_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=jspruncodes");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=jspruncodes");
		return null;
	}
	public ActionForward toJsprunCodeChild(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		String edit = request.getParameter("edit");
		Bbcodes bbcodes = null;
		int id = Integer.valueOf(edit); 
		bbcodes = bbcodesService.findByID(id);
		String prompt = bbcodes.getPrompt();
		prompt = prompt.replaceAll("\t", "\r\n");
		bbcodes.setPrompt(prompt);
		request.setAttribute("jspruncode", bbcodes);
		return mapping.findForward("toJspRunCodeChild");
	}
	public ActionForward tojspruncode(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String,String>> bbcodelist = dataBaseService.executeQuery("select id,available,tag,icon from jrun_bbcodes");
		request.setAttribute("bbcodelist", bbcodelist);
		StringBuffer sb = new StringBuffer();
		if (bbcodelist != null && bbcodelist.size() > 0) {
			for(Map<String,String> bbcode:bbcodelist){
				sb.append(bbcode.get("id")+",");
			}
		}
		request.setAttribute("hiddensb", sb);
		return mapping.findForward("tojspruncode");
	}
	public ActionForward updateJsprunCodeChild(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse resposne) {
		try{
			if(submitCheck(request, "editsubmit")){
				Bbcodes b = new Bbcodes();
				b.setId(Integer.valueOf(request.getParameter("jspruncodeID")));
				b.setAvailable(Byte.valueOf(request.getParameter("available")));
				b.setIcon(request.getParameter("icon"));
				b.setReplacement(request.getParameter("replacementnew"));
				b.setExample(request.getParameter("examplenew"));
				b.setExplanation(request.getParameter("explanationnew"));
				String paramsnew = request.getParameter("paramsnew");
				String tagname = request.getParameter("tagnew");
				String nest = request.getParameter("nestnew");
				short nestnum = (short)Common.range(Common.intval(nest), 255, 0); 
				byte param = (byte)Common.range(Common.intval(paramsnew), 255, 0); 
				if(tagname==null || tagname.equals("") || !Common.matches(tagname, "^[0-9a-z]+$")){
					request.setAttribute("return", true);
					request.setAttribute("message_key","a_post_jspruncodes_edit_tag_invalid");
					return mapping.findForward("message");
				}else if(param<1 || param>3 || nestnum<1 || nestnum>3){
					request.setAttribute("return", true);
					request.setAttribute("message_key","a_post_jspruncodes_edit_range_invalid");
					return mapping.findForward("message");
				}
				b.setTag(tagname);
				b.setParams(param);
				String promptnew = request.getParameter("promptnew");
				promptnew = promptnew==null?"":promptnew;
				promptnew = promptnew.replaceAll("\r\n", "\t");
				b.setPrompt(promptnew);
				b.setNest(nestnum);
				bbcodesService.updateBbcodes(b);
				Cache.updateCache("post");
				request.setAttribute("message_key", "a_post_jspruncodes_edit_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=jspruncodes");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(resposne, "admincp.jsp?action=jspruncodes");
		return null;
	}
	private String getAvailable(String ids) {
		StringBuffer sb = new StringBuffer("availablenew[");
		sb.append(ids);
		sb.append("]");
		return sb.toString();
	}
	public ActionForward toRecyclebin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
		session.removeAttribute("recyclebinform");
		return mapping.findForward("toRecyclebin");
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ActionForward toSmilies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String export = request.getParameter("export");
		String importsubmit = request.getParameter("importsubmit");
		if(export==null && importsubmit==null){
			List<Map<String,String>> dateList = dataBaseService.executeQuery("select i.name,i.displayorder,i.typeid,i.directory,(select count(*) from jrun_smilies where typeid=i.typeid) as totalCount from jrun_imagetypes as i where i.type = 'smiley'");
			List<String> showList = new ArrayList<String>();
			String filePath = JspRunConfig.realPath+"images/smilies";
			File f = new File(filePath);
			String[] fs = f.list();
			if (fs != null && fs.length > 0) {
				for (int i = 0; i < fs.length; i++) {
					File fsi = new File(filePath+"/"+fs[i]);
					if (fsi.isDirectory()) {
						showList.add(fsi.getName());
					}
				}
				if (showList != null && showList.size() > 0 && dateList != null && dateList.size() > 0) {
					for (int j = 0; j < dateList.size(); j++) {
						for (int i = 0; i < showList.size(); i++) {
							if (dateList.get(j).get("directory").equals(showList.get(i))) {
								showList.remove(i);
							}
						}
					}
				}
				request.setAttribute("showList", showList);
			}
			request.setAttribute("datelist", dateList);
			return mapping.findForward("toSmilies");
		}else if(export!=null){
			List<Map<String,String>> imageTypes=dataBaseService.executeQuery("SELECT name, directory FROM jrun_imagetypes WHERE typeid='"+export+"' AND type='smiley'");
			if(imageTypes==null||imageTypes.size()==0){
				request.setAttribute("message_key", "a_post_smilies_export_invalid");
				return mapping.findForward("message");
			}
			Map imageType=imageTypes.get(0);
			imageType.put("version",JspRunConfig.VERSION);
			Map<Integer,Map<String,String>> smileyMap=new TreeMap<Integer,Map<String,String>>();
			List<Map<String,String>> smilies=dataBaseService.executeQuery("SELECT typeid, displayorder, code, url FROM jrun_smilies WHERE type='smiley' AND typeid='"+export+"'");
			if(smilies!=null&&smilies.size()>0){
				for (Map<String,String> smiley : smilies) {
					smileyMap.put(smileyMap.size(), smiley);
				}
			}
			imageType.put("smilies", smileyMap);
			exportData(request, response, "JspRun_smilies_"+Common.encodeText(request, (String)imageType.get("name"))+".txt", "Smilies Dump", imageType);
			return null;
		}else {
			try{
				if(submitCheck(request, "importsubmit")){
					String smiliesdata=request.getParameter("smiliesdata");
					smiliesdata=smiliesdata.replaceAll("(#.*\\s+)*", "");
					Map smile=dataParse.characterParse(Base64.decode(smiliesdata, JspRunConfig.CHARSET), false);
					if(smile==null||smile.size()==0){
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_post_smilies_import_data_invalid");
						return mapping.findForward("message");
					}
					String version=(String)smile.get("version");
					if(!smile.get("version").equals(JspRunConfig.VERSION)){
						request.setAttribute("message", getMessage(request, "a_post_smilies_export_version",version,JspRunConfig.VERSION));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					boolean renamed=false;
					List<Imagetypes> imagetypes = imagetypesService.findImagetypeByName(smile.get("name").toString());
					if(imagetypes!=null && imagetypes.size()>0){
						smile.put("name", smile.get("name").toString()+"_"+Common.getRandStr(4,false));
						renamed = true;
					}
					Imagetypes imagetype = new Imagetypes();
					imagetype.setName(smile.get("name").toString());
					imagetype.setDisplayorder(Short.valueOf("0"));
					imagetype.setDirectory(smile.get("directory").toString());
					imagetype.setType("smiley");
					if(!imagetypesService.addImagetype(imagetype)){
						request.setAttribute("return", true);
						request.setAttribute("message_key", "a_post_smilies_import_data_invalid");
						return mapping.findForward("message");
					}
					short typeid = imagetype.getTypeid();
					Map<Integer,Map<String,String>> smileMap = (Map<Integer,Map<String,String>>)smile.get("smilies");
					if(smileMap!=null){
						Iterator<Entry<Integer,Map<String,String>>> its = smileMap.entrySet().iterator();
						while(its.hasNext()){
							Entry<Integer,Map<String,String>> temp = its.next();
							Map<String,String> smiley = temp.getValue();
							Smilies smilies = new Smilies();
							smilies.setCode(smiley.get("code"));
							smilies.setType("smiley");
							smilies.setUrl(smiley.get("url"));
							smilies.setTypeid(typeid);
							smilies.setDisplayorder(Short.valueOf(smiley.get("displayorder")));
							smilieService.addSmilies(smilies);
						}
					}
					String successInfo = null;
					Cache.updateCache("post");
					if(renamed){
						successInfo="a_post_smilies_import_succeed_renamed";
					}else{
						successInfo="a_post_smilies_import_succeed";
					}
					request.setAttribute("message_key", successInfo);
					request.setAttribute("url_forward", "admincp.jsp?action=smilies");
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
			Common.requestforward(response, "admincp.jsp?action=smilies");
			return null;
		}
	}
	public ActionForward toIcoes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<Smilies> dateList = null;
		List<Smilies> showList = new ArrayList<Smilies>();
		dateList = smilieService.getIcons();
		String filePath = JspRunConfig.realPath+"images/icons";
		File f = new File(filePath);
		String repxl = "gif|jpg|png|bmp|jpeg";
		String[] fs = f.list();
		if (fs != null && fs.length > 0) {
			for (int i = 0; i < fs.length; i++) {
				int index = fs[i].lastIndexOf(".");
				if(index!=-1 && Common.matches(fs[i].substring(index).toLowerCase(),repxl)){
				File fsi = new File(filePath+"/"+fs[i]);
				if (fsi.exists() && !fsi.isDirectory()) {
					Smilies icons = new Smilies();
					icons.setUrl(fs[i]);
					showList.add(icons);
				}
				}
			}
			if (showList != null && showList.size() > 0 && dateList != null
					&& dateList.size() > 0) {
				for (int j = 0; j < dateList.size(); j++) {
					for (int i = 0; i < showList.size(); i++) {
						if (dateList.get(j).getUrl().equals(showList.get(i).getUrl())) {
							showList.remove(i);
						}
					}
				}
			}
			request.setAttribute("fileIcons", showList);
		}
		request.setAttribute("dateList", dateList);
		return mapping.findForward("toIcons");
	}
	@SuppressWarnings("unchecked")
	public ActionForward tomodreplies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Members member = (Members)request.getSession().getAttribute("members");
		String sql="SELECT f.fid,f.name FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) WHERE f.type <>'group' AND f.status>0";
		if(member.getAdminid()==3){
			sql = "SELECT m.fid, f.name FROM jrun_moderators m LEFT JOIN jrun_forums f ON f.fid=m.fid  WHERE m.uid='"+member.getUid()+"'";
		}
		List<Map<String,String>> forumList=dataBaseService.executeQuery(sql);
		if(forumList==null||forumList.size()<=0){
			request.setAttribute("return", true);
			request.setAttribute("message_key", "a_post_moderate_no_access_all");
			return mapping.findForward("message");
		}
		StringBuffer fids = new StringBuffer();
		if(member.getAdminid()==3){
			for(Map<String,String> forums : forumList){
				fids.append(","+forums.get("fid"));
			}
		}
		request.setAttribute("forumList", forumList);
		String modreasons= ForumInit.settings.get("modreasons");
		request.setAttribute("modreasons",modreasons!=null?modreasons.split("\r\n"):"");
		ModrepliesPageForm mod = new ModrepliesPageForm(fids.toString());
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		Map<String, Map<String,String>> forumdatas = null;
		Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
		Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
		if(forumMap!=null){
			forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
		}
		mod.setList(request.getContextPath(),resources,locale,forumdatas);
		request.setAttribute("modrepliesPageForm", mod);
		return mapping.findForward("toreplies");
	}
	@SuppressWarnings("unchecked")
	public ActionForward tomodthreads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Members members = (Members)request.getSession().getAttribute("members");
		String sql="SELECT f.fid,f.name FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) WHERE f.type <>'group' AND f.status>0";
		StringBuffer fids = new StringBuffer();
		if(members.getAdminid()==3){
			sql = "SELECT m.fid, f.name FROM jrun_moderators m LEFT JOIN jrun_forums f ON f.fid=m.fid  WHERE m.uid='"+members.getUid()+"'";
		}
		List<Map<String,String>> forumList = dataBaseService.executeQuery(sql);
		if(forumList==null||forumList.size()<=0){
			request.setAttribute("return", true);
			request.setAttribute("message_key", "a_post_moderate_no_access_all");
			return mapping.findForward("message");
		}
		if(members.getAdminid()==3){
			for(Map<String,String> forums : forumList){
				fids.append(","+forums.get("fid"));
			}
		}
		request.setAttribute("forumList", forumList);
		String modreasons=ForumInit.settings.get("modreasons");
		request.setAttribute("modreasons",modreasons!=null?modreasons.split("\r\n"):"");
		PostsPageForm mod = new PostsPageForm(fids.toString());
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
		Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
		Map<String, Map<String,String>> forumdatas = null;
		if(forumMap!=null){
			forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
		}
		mod.setList(request.getContextPath(),resources,locale,forumdatas);
		request.setAttribute("postsPageForm", mod);
		return mapping.findForward("tothreads");
	}
	private int convertInt(String str){
		int count = 0;
		try{
			count = Integer.valueOf(str);
		}catch(Exception e){
		}
		return count;
	}
	@SuppressWarnings("unchecked")
	public ActionForward toTags(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings = ForumInit.settings;
		String tagstatus = settings.get("tagstatus");   
		String hottags = settings.get("hottags");     
		if(!tagstatus.equals("1")){
			request.setAttribute("message_key", "a_post_tags_not_open");
			request.setAttribute("url_forward", "admincp.jsp?action=settings&do=functions");
			return mapping.findForward("message");
		}
		List<Map<String,String>> count = dataBaseService.executeQuery("select count(*) as count from jrun_tags");
		List<Map<String,String>> closecount = dataBaseService.executeQuery("select count(*) as count from jrun_tags where closed=1");
		List<Map<String,String>> hottag = dataBaseService.executeQuery("select * from jrun_tags where closed=0 order by total desc limit "+hottags);
		request.setAttribute("count", count.get(0).get("count"));
		request.setAttribute("closecount",closecount.get(0).get("count"));
		request.setAttribute("hottag", hottag);
		closecount = null;count=null;settings=null;
		return mapping.findForward("totags");
	}
	public ActionForward toWords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int totalsize = Common.toDigit(dataBaseService.executeQuery("select count(*) as count from jrun_words").get(0).get("count"));
		LogPage loginpage = new LogPage(totalsize,30,1);
		List<Map<String,String>> wordslist = dataBaseService.executeQuery("select * from jrun_words limit 30");
		request.setAttribute("wordslist",wordslist);
		request.setAttribute("logpage", loginpage);
		return mapping.findForward("towords");
	}
	public ActionForward toAttachtype(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<Map<String,String>> attatypelist = dataBaseService.executeQuery("select * from jrun_attachtypes");
		request.setAttribute("attatypelist", attatypelist);
		return mapping.findForward("toattatype");
	}
}