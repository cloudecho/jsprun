package cn.jsprun.struts.action;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
import cn.jsprun.struts.form.PageForm;
import cn.jsprun.struts.form.ThreadsForm;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
public class ThreadsAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward batchThreads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "searchsubmit")){
				int currentpage = 1;
				String page = request.getParameter("page");
				currentpage = Math.max(Common.intval(page), 1);
				ThreadsForm tf = (ThreadsForm) form;
				HttpSession session = request.getSession();
				String timeoffset=(String)session.getAttribute("timeoffset");
				if (tf.isFindBy()) {
					String sql = threadsService.batchsql(tf,timeoffset);
					List<Map<String,String>>countlist = dataBaseService.executeQuery("select count(*) count "+sql);
					String threadsql = "select t.tid "+sql;
					int totalSize = Common.toDigit(countlist.get(0).get("count")); 
					if(tf.getDetail()==1){
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
						if (currentpage < 0) {
						} else {
							if (currentpage > totalPage) {
								currentpage = totalPage;
							}
							startid = pageSize * (currentpage - 1);
						}
						request.setAttribute(PageForm.CURRENTPAGE, currentpage); 
						request.setAttribute(PageForm.TOTALPAGE, totalPage);
						threadsql = "select t.* "+sql+"limit "+startid+","+pageSize;
					}
					List<Map<String,String>> threads = dataBaseService.executeQuery(threadsql);
					request.setAttribute(PageForm.TOTALSIZE, totalSize);
					if (threads != null && threads.size()>0) {
						Map<String, Map<String,String>> forumdatas = null;
						if(tf.getDetail()==1){
							Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
							Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
							if(forumMap!=null){
								forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
							}
						}
						StringBuffer sb = new StringBuffer("");
						for (Map<String,String> thread:threads) {
							sb.append(thread.get("tid"));
							sb.append(",");
							if(tf.getDetail()==1&&forumdatas!=null){
								Map<String,String> forum = forumdatas.get(thread.get("fid"));
								thread.put("name", forum.get("name"));
							}
						}
						request.setAttribute("accountTid", sb);
						request.setAttribute("threadsList", threads);
					}
				}
				String isDisplay = "none";
				if (tf.getDetail() == 1) {
					isDisplay = "block";
				}
				request.setAttribute("isDisplay", isDisplay);
				request.setAttribute("tfs", tf); 
				request.setAttribute("fids", Integer.valueOf(tf.getInforum()));
				request.setAttribute("nofirst", true);
				session.setAttribute("threadsForm", tf);
				return mapping.findForward("threads");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=threads");
		return null;
	}
	private String toTidarray(String tid) {
		StringBuffer sb = new StringBuffer("tidarray[");
		sb.append(tid);
		sb.append("]");
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	public ActionForward disposalThreads(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		try{
			if(submitCheck(request, "modsubmit")){
				String tids = request.getParameter("tids");
				String operation = request.getParameter("operation");
				StringBuffer disposalTids = new StringBuffer();
				Object[] tidArray = tids != null ? tids.split(",") : null;
				Map<String,String> settings = ForumInit.settings;
				int num = 0;
				for (int i = 0; i < tidArray.length; i++) {
					String tid = request.getParameter(toTidarray(tidArray[i].toString()));
					if (tid!=null && tid.equals(tidArray[i].toString())) {
						num++;
						disposalTids.append(tid);
						disposalTids.append(",");
					}
				}
				disposalTids.append("0");
				if (operation.equals("moveforum")) {
					String toforum = request.getParameter("toforum");
					dataBaseService.runQuery("UPDATE jrun_threads SET fid='"+toforum+"' WHERE tid in("+disposalTids.toString()+")");
					dataBaseService.runQuery("UPDATE jrun_posts SET fid='"+toforum+"' WHERE tid in("+disposalTids.toString()+")");
					MessageResources resources = getResources(request);
					Locale locale = getLocale(request);
					String fid = request.getParameter("fids");
					if(fid!=null && !fid.equals("-1") && !fid.equals("")){
						Common.updateforumcount(fid,resources,locale);
					}
					Common.updateforumcount(toforum,resources,locale);
				}
				if (operation.equals("movetype")) {
					String totype = request.getParameter("totype");
					dataBaseService.runQuery("UPDATE jrun_threads SET typeid='"+totype+"' WHERE tid in("+disposalTids.toString()+")");
				}
				if (operation.equals("delete")) {
					String donotupdatemember = request.getParameter("donotupdatemember");
					List<Map<String,String>> attalist = dataBaseService.executeQuery("select attachment,thumb,remote from jrun_attachments where tid in ( "+disposalTids.toString()+" )");
					if(attalist!=null && attalist.size()>0){
						String servletpath = JspRunConfig.realPath+settings.get("attachdir")+"/";
						for(Map<String,String> attas :attalist){
							Common.dunlink(attas.get("attachment"),Byte.valueOf(attas.get("thumb")),Byte.valueOf(attas.get("remote")), servletpath);
						}
					}
					attalist=null;
					if (!"1".equals(donotupdatemember)) {
						List<Map<String,String>> postmap = dataBaseService.executeQuery("SELECT first, authorid FROM jrun_posts WHERE tid in ("+disposalTids.toString()+")");
						String creditspolicy = settings.get("creditspolicy");
						Map creditspolicys=dataParse.characterParse(creditspolicy,false);
						String creditsformula = settings.get("creditsformula");
						int timestamp = (Integer)(request.getAttribute("timestamp"));
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
						Map<Integer, Integer> postcredits =(Map<Integer,Integer>)creditspolicys.get("post");
						Set<String> threads = threaduidmap.keySet();
						for(String uid:threads){
							int value = threaduidmap.get(uid);
							Common.updatepostcredits("-", Common.toDigit(uid), postcredits, timestamp, value, creditsformula);
						}
						postcredits =(Map<Integer,Integer>)creditspolicys.get("reply");
						Set<String> posts = postuidmap.keySet();
						for(String uid:posts){
							int value = postuidmap.get(uid);
							Common.updatepostcredits("-", Common.toDigit(uid), postcredits, timestamp, value, creditsformula);
						}
					} 
					dataBaseService.runQuery("delete from jrun_attachments where tid in ("+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_threads  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_polloptions  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_polls  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_rewardlog  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_trades  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_activities  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_activityapplies  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_debates  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_debateposts  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_threadsmod  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_relatedthreads  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_typeoptionvars  where tid in ( "+disposalTids.toString()+" )",true);
					dataBaseService.runQuery("delete from jrun_posts  where tid in ( "+disposalTids.toString()+" )",true);
					String fid = request.getParameter("fids");
					if(fid!=null && !fid.equals("-1") && !fid.equals("")){
						MessageResources resources = getResources(request);
						Locale locale = getLocale(request);
						Common.updateforumcount(fid,resources,locale);
					}
				}
				if (operation.equals("stick")) {
					String stick_level = request.getParameter("stick_level");
					dataBaseService.runQuery("update jrun_threads set displayorder='"+stick_level+"' where tid in ( "+disposalTids.toString()+" )");
				}
				if (operation.equals("adddigest")) {
					String adddigest = request.getParameter("digest_level");
					String creditspolicy = ForumInit.settings.get("creditspolicy");
					String creditsformula =ForumInit.settings.get("creditsformula");
					Map creditspolicys=dataParse.characterParse(creditspolicy,false);
					List<Map<String,String>> threads = dataBaseService.executeQuery("SELECT t.tid, t.authorid, t.digest,f.digestcredits FROM jrun_threads as t left join jrun_forumfields as f on t.fid=f.fid WHERE t.tid in ( "+disposalTids.toString()+" )");
					for(Map<String,String> thread:threads){
						Map<Integer, Integer> postcredits = new HashMap<Integer,Integer>();
						int digest = Common.toDigit(adddigest)-Common.toDigit(thread.get("digest"));
						Map<Integer, Integer> digestcredits = dataParse.characterParse(thread.get("digestcredits"),false);
						if(digestcredits==null||digestcredits.size()<=0)
						{
							digestcredits=(Map<Integer,Integer>)creditspolicys.get("digest");
						}
						Iterator<Entry<Integer,Integer>> keys = digestcredits.entrySet().iterator();
						try{
							while (keys.hasNext()) {
								Entry<Integer,Integer> temp = keys.next();
								int value = temp.getValue().intValue()*Math.abs(digest);
								postcredits.put(temp.getKey(),value);
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						String opertion = "+";
						if(digest<0){
							opertion = "-";
						}
						StringBuffer creditsadd = new StringBuffer();
						Set<Entry<Integer,Integer>> keyss = postcredits.entrySet();
						for (Entry<Integer,Integer> temp : keyss) {
							Integer key = temp.getKey();
							creditsadd.append(", extcredits" + key + "= extcredits" + key+ opertion + temp.getValue());
						}
						String digestposts = "";
						if(!adddigest.equals("0") && thread.get("digest").equals("0")){
							digestposts = "digestposts=digestposts+1";
						}else if(adddigest.equals("0") && !thread.get("digest").equals("0")){
							digestposts = "digestposts=digestposts-1";
						}else{
							digestposts = "digestposts=digestposts";
						}
						dataBaseService.runQuery("UPDATE jrun_members SET "+digestposts + creditsadd+ " WHERE uid =" + thread.get("authorid"),true);
						Common.updatepostcredits(Common.toDigit(thread.get("authorid")), creditsformula);
					}
					threads = null;creditspolicys=null;
					threadsService.adddigest(Integer.valueOf(adddigest), disposalTids.toString());
				}
				if (operation.equals("addstatus")) {
					String status = request.getParameter("status");
					dataBaseService.runQuery("update jrun_threads set closed='"+status+"' where tid in ( "+disposalTids.toString()+" )");
				}
				if (operation.equals("deleteattach")) {
					List<Map<String,String>> attalist = dataBaseService.executeQuery("select attachment,thumb,remote from jrun_attachments where tid in ( "+disposalTids.toString()+" )");
					if(attalist!=null && attalist.size()>0){
						String servletpath = JspRunConfig.realPath+settings.get("attachdir")+"/";
						for(Map<String,String> attas :attalist){
							Common.dunlink(attas.get("attachment"), Byte.valueOf(attas.get("thumb")), Byte.valueOf(attas.get("remote")), servletpath);
						}
					}
					attalist = null;
					dataBaseService.runQuery("delete from jrun_attachments  where tid in ( "+disposalTids.toString()+" )");
					dataBaseService.runQuery("update jrun_threads set attachment='0'  where tid in ( "+disposalTids.toString()+" )");
					dataBaseService.runQuery("update jrun_posts set attachment='0' where tid in ( "+disposalTids.toString()+" )");
				}
				tidArray = null;
				try {
					String shalert = getMessage(request, "a_post_threads_succeed");
					response.getWriter().write( "<script type='text/javascript'>alert('" + shalert + "');</script>");
					response.getWriter().write("<script>parent.$('searchforum').searchsubmit.click();</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=threads");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward pageThreads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Integer currentpage = 1;
		String page = request.getParameter("page");
		currentpage = page==null ? 1 : Integer.parseInt(page);
		ThreadsForm tf = (ThreadsForm)request.getSession().getAttribute("threadsForm");
		if(tf==null){
			Common.requestforward(response, "admincp.jsp?action=threads");
			return null;
		}
		HttpSession session = request.getSession();
		String timeoffset= (String)session.getAttribute("timeoffset");
		if (tf.isFindBy()) {
			String sql = threadsService.batchsql(tf,timeoffset);
			List<Map<String,String>>countlist = dataBaseService.executeQuery("select count(*) count "+sql);
			int totalSize = Common.toDigit(countlist.get(0).get("count")); 
			int totalPage = 1;
			int pageSize = 10; 
			int startid = 0;
			if (totalSize > pageSize) {
				if (totalSize % pageSize == 0) {
					totalPage = (int) ((double) totalSize / (double) pageSize);
				} else {
					totalPage = (int) (1.0d + (double) totalSize/ (double) pageSize);
				}
			} else {
				totalPage = 1;
			}
			if (currentpage < 0) {
			} else {
				if (currentpage > totalPage) {
					currentpage = totalPage;
				}
				startid = pageSize * (currentpage - 1);
			}
			List<Map<String,String>>threads = dataBaseService.executeQuery("select t.* "+sql+"limit "+startid+","+pageSize);
			request.setAttribute(PageForm.CURRENTPAGE, currentpage); 
			request.setAttribute(PageForm.TOTALPAGE, totalPage);
			request.setAttribute(PageForm.TOTALSIZE, totalSize);
			if (threads != null && threads.size()>0) {
				Map<String, Map<String,String>> forumdatas = null;
				Common.include(request, response, "./forumdata/cache/cache_forums.jsp", "forums");
				Map<String,String> forumMap = (Map<String,String>)request.getAttribute("forums");
				if(forumMap!=null){
					forumdatas = (Map<String, Map<String,String>>)dataParse.characterParse(forumMap.get("forums"), false);
				}
				StringBuffer sb = new StringBuffer("");
				for (Map<String,String> thread:threads) {
					sb.append(thread.get("tid"));
					sb.append(",");
					if(forumdatas!=null){
						Map<String,String> forum = forumdatas.get(thread.get("fid"));
						thread.put("name", forum.get("name"));
					}
				}
				request.setAttribute("accountTid", sb);
				request.setAttribute("threadsList", threads);
			}
		}
		String isDisplay = "none";
		if (tf.getDetail() == 1) {
			isDisplay = "block";
		}
		request.setAttribute("isDisplay", isDisplay);
		request.setAttribute("tfs", tf); 
		request.setAttribute("nofirst", true);
		request.setAttribute("fids", Integer.valueOf(tf.getInforum()));
		return mapping.findForward("threads");
	}
}
