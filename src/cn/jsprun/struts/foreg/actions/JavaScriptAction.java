package cn.jsprun.struts.foreg.actions;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import cn.jsprun.service.DataBaseService;
import cn.jsprun.service.SystemToolService;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Md5Token;
public class JavaScriptAction extends DispatchAction {
	private DataBaseService dataBaseService = (DataBaseService) BeanFactory.getBean("dataBaseService");
	private DataParse dataParse = (DataParse) BeanFactory.getBean("dataParse");
	private SystemToolService systemToolServer = (SystemToolService) BeanFactory.getBean("systemToolServer");
	@SuppressWarnings("unchecked")
	public ActionForward jspreview(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		String jsstatus = settings.get("jsstatus");
		if(jsstatus==null||jsstatus.equals("0")){
			writeMessage(response,"document.write(\"<font color=red>The webmaster did not enable this feature.</font>\");");
			return null;
		}
		String jsrefdomains = settings.get("jsrefdomains");
		String httprefere = request.getHeader("Referer");
		if(jsrefdomains!=null&&!jsrefdomains.equals("")&&(httprefere==null||!in_array(request.getRemoteHost(),jsrefdomains,"\r\n"))){
			writeMessage(response,"document.write(\"<font color=red>Referer restriction is taking effect.</font>\");");
			return null;
		}
		String key = request.getParameter("key");
		if(Common.empty(key)){
			writeMessage(response,"document.write(\"<font color=red>Authentication failed.</font>\");");
			return null;
		}
		String value = settings.get("jswizard_"+key);
		if(value==null){
			writeMessage(response,"document.write(\"<font color=red>Authentication failed.</font>\");");
			return null;
		}
		String cachefiles = JspRunConfig.realPath+"forumdata/cache/javascript_"+key+".jsp";
		File file = new File(cachefiles);
		if(file.exists()){
			try {
				request.getRequestDispatcher("forumdata/cache/javascript_"+key+".jsp").include(request, response);
			} catch (ServletException e) {
			} catch (IOException e) {
			}
		}
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		Map<String,String> cachelife = (Map<String,String>)request.getAttribute("js_"+key);
		int expiration = cachelife==null?0:Common.toDigit(cachelife.get("expiration"));
		String datalist = cachelife==null?"":cachelife.get("datalist");
		String boardurl = (String)request.getSession().getAttribute("boardurl");
		if(cachelife==null || expiration<timestamp){
			Map valuemap = dataParse.characterParse(value, false);
			Map paramMap = (Map) valuemap.get("parameter");
			String type = valuemap.get("type")==null?"6":valuemap.get("type").toString();
			if(!type.equals("5")){
				int jscachelife = paramMap.get("cachelife")!=null&&!paramMap.get("cachelife").equals("")?Common.toDigit(paramMap.get("cachelife").toString()):Common.toDigit(settings.get("jscachelife"));
				datalist = jsmodule(type,paramMap,settings,boardurl);
				if(datalist==null){
					writeMessage(response,"document.write(\"<font color=red>Undefined action.</font>\");");
					return null;
				}
				if(key.endsWith("_preview")){
					datalist = "document.write(\""+datalist+"\");";
				}else{
					Map<String,String> datamap = new HashMap<String,String>();
					int expistring= timestamp+jscachelife;
					datamap.put("expiration", expistring+"");
					datalist = "document.write(\""+datalist+"\");";
					datamap.put("datalist", datalist);
					String date = arrayeval("js_"+key,datamap);
					updateCache(cachefiles,date,response);
				}
			}else{
				int jscachelife = paramMap.get("cachelife")!=null&&!paramMap.get("cachelife").equals("")?Common.toDigit(paramMap.get("cachelife").toString()):Common.toDigit(settings.get("jscachelife"));
				String jstemplate = (String) paramMap.get("jstemplate");
				Set<String> keyset = new HashSet<String>();
				if(Common.matches(jstemplate, "\\[module\\](.+?)\\[/module\\]")){
					int count = 0;
					while(Common.matches(jstemplate, "\\[module\\](.+?)\\[/module\\]")&&count<5){
						String modulekey = jstemplate.replaceFirst("\\[module\\](.+?)\\[/module\\]", "$1");
						keyset.add(modulekey);
						count++;
					}
				}
				for(String keys:keyset){
					String values = settings.get("jswizard_"+keys);
					if(!Common.empty(values)){
						Map keymap = dataParse.characterParse(values, false);
						Map keyparamMap = (Map) keymap.get("parameter");
						String types = keymap.get("type")==null?"6":keymap.get("type").toString();
						String datas = jsmodule(types,keyparamMap,settings,boardurl);
						if(datas==null){
							writeMessage(response,"document.write(\"<font color=red>Undefined action.</font>\");");
							return null;
						}
						String find = "[module]"+keys+"[/module]";
						jstemplate = StringUtils.replace(jstemplate, find, datas);
					}
				}
				if(key.indexOf("_preview")==-1){
					Map<String,String> datamap = new HashMap<String,String>();
					int expistring= timestamp+jscachelife;
					datamap.put("expiration", expistring+"");
					datalist = "document.write(\""+jstemplate+"\");";
					datamap.put("datalist", datalist);
					String date = arrayeval("js_"+key,datamap);
					updateCache(cachefiles,date,response);
				}else{
					datalist = "document.write(\""+jstemplate+"\");";
				}
			}
		}
		writeMessage(response,rewriteContent(datalist, boardurl));
		return null;
	}
	private boolean in_array(String host,String domain,String prex){
		String [] domains = domain.split(prex);
		if(domains!=null){
			for(String s:domains){
				if(s.indexOf(host)!=-1){
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
	private void writeMessage(HttpServletResponse response,String message){
		try {
			response.getWriter().write(message);
		} catch (IOException e) {
		}
	}
	private void updateCache(String cachefile,String date,HttpServletResponse response){
		File file = new File(cachefile);
		if(file.exists()){
			file.delete();
		}
		try {
			FileOutputStream fos=new FileOutputStream(cachefile,true);
			OutputStreamWriter os=new OutputStreamWriter(fos,JspRunConfig.CHARSET);
			BufferedWriter bw = new BufferedWriter(os);
			bw.write("<%--\n");
			bw.write("JspRun! cache file, DO NOT modify me!\n");
			bw.write("Created: " + new Date().toGMTString() + "\n");
			bw.write("Identify: "+ Md5Token.getInstance().getLongToken(cachefile) + "\n");
			bw.write("--%>\n");
			bw.write("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\""+JspRunConfig.CHARSET+"\"%>\n");
			bw.write(date);
			bw.flush();
			os.flush();
			fos.flush();
			bw.close();
			os.close();
			fos.close();
			bw=null;
		} catch (IOException e) {
			writeMessage(response,"document.write(\"Unable to write to cache file!<br />Please chmod ./forumdata/cache to 777 and try again.\");");
		}
	}
	private String arrayeval(String cachename, Map<String, String> map) {
		StringBuffer mapName = new StringBuffer("_DCACHE_");
		mapName.append(cachename);
		StringBuffer curdata = new StringBuffer();
		curdata.append("<%\n");
		curdata.append("Map<String,String> " + mapName+ "= new HashMap<String,String>();\n");
		if (map != null) {
			Set<Entry<String, String>> keys = map.entrySet();
			for (Entry<String, String> temp : keys) {
				String key = temp.getKey();
				String value = temp.getValue();
				if(value!=null)
				{
					value = value.replace("\"", "\\\"");
					value = value.replaceAll("\r\n", "\\\\n");
				}
				curdata.append(mapName + ".put(\"" + key + "\",\"" + value+ "\");\n");
			}
		}
		curdata.append("request.setAttribute(\"" + cachename + "\"," + mapName+ ");\n");
		curdata.append("%>\n");
		return curdata.toString();
	}
	@SuppressWarnings("unchecked")
	private String jsmodule(String type,Map paramMap,Map<String,String>settings,String boardurl){
		String datalist = "";
		String dateformat = !Common.empty(settings.get("jsdateformat"))?settings.get("jsdateformat") : settings.get("dateformat");
		String timeformat =	settings.get("timeformat").equals("1") ? "hh:mm a" : "HH:mm";
		String timeoffset = settings.get("timeoffset");
		int jscharset  = paramMap.get("jscharset")!=null?Common.toDigit(paramMap.get("jscharset").toString()):0;
		String jstemplate = (String) paramMap.get("jstemplate");
		String jstemplatebody = "";
		if(jstemplate.matches(".*\\[node\\].*\\[/node\\].*")){
			jstemplatebody = jstemplate;
			jstemplate = jstemplate.replaceAll(".*\\[node\\](.+?)\\[/node\\].*", "$1");
		}
		if(type.equals("0")){
			String tids = (String) paramMap.get("tids");
			String keyword = (String) paramMap.get("keyword");
			String blog = (String) paramMap.get("blog");
			String orderby = (String) paramMap.get("orderby");
			String startrow = (String) paramMap.get("startrow");
			String items = (String) paramMap.get("items");
			String maxlength = (String) paramMap.get("maxlength");
			String highlight = (String) paramMap.get("highlight");
			String picpre = (String) paramMap.get("picpre");
			Map threads_forumsMap = (Map) paramMap.get("threads_forums");
			String threadtype = (String) paramMap.get("threadtype");
			String rewardstatus = (String)paramMap.get("rewardstatus");
			String newwindow = (String)paramMap.get("newwindow");
			String LinkTarget	= "1".equals(newwindow) ? " target='_blank'" : ("2".equals(newwindow) ? " target='main'" : "");
			List<String> threads_forums = null;
			if (threads_forumsMap != null) {
				threads_forums = new ArrayList<String>();
				Iterator it = threads_forumsMap.keySet().iterator();
				while (it.hasNext()) {
					threads_forums.add(threads_forumsMap.get(it.next()).toString());
				}
			}
			Map typeidsMap = (Map) paramMap.get("typeids");
			List<String> typeids = null;
			if (typeidsMap != null) {
				typeids = new ArrayList<String>();
				Iterator it = typeidsMap.keySet().iterator();
				while (it.hasNext()) {
					typeids.add(typeidsMap.get(it.next()).toString());
				}
			}
			Map specialMap = (Map) paramMap.get("special");
			List<String> special = null;
			if (specialMap != null) {
				special = new ArrayList<String>();
				Iterator it = specialMap.keySet().iterator();
				while (it.hasNext()) {
					special.add(it.next().toString());
				}
			}
			Map digestMap = (Map) paramMap.get("digest");
			List<String> digest = null;
			if (digestMap != null) {
				digest = new ArrayList<String>();
				Iterator it = digestMap.keySet().iterator();
				while (it.hasNext()) {
					digest.add(it.next().toString());
				}
			}
			Map stickMap = (Map) paramMap.get("stick");
			List<String> stick = null;
			if (stickMap != null) {
				stick = new ArrayList<String>();
				Iterator it = stickMap.keySet().iterator();
				while (it.hasNext()) {
					stick.add(it.next().toString());
				}
			}
			StringBuffer sql = new StringBuffer("select t.tid,t.fid,t.subject,t.highlight,t.views,t.replies,t.lastpost,t.lastposter,t.dateline,t.author,t.authorid,p.message,f.name,d.name as typename FROM jrun_threads as t INNER JOIN jrun_posts p ON t.tid=p.tid INNER JOIN jrun_forums f ON t.fid=f.fid LEFT JOIN jrun_threadtags h ON t.tid=h.tid LEFT JOIN jrun_threadtypes d ON t.typeid=d.typeid WHERE p.first=1 AND t.readperm='0' AND t.displayorder>='0'");
			String and = " AND ";
			if (threads_forums != null && !threads_forums.get(0).equals("")&& !threads_forums.get(0).equals("all")) {
				sql.append(and);
				String fids = "";
				for (String fid : threads_forums) {
					fids += fid + ",";
				}
				fids = fids.substring(0, fids.length() - 1);
				sql.append(" t.fid in (" + fids + ") ");
			}
			if (tids != null && !tids.equals("")) {
				sql.append(and);
				sql.append(" t.tid in (" + tids + ") ");
			}
			if (keyword != null && !keyword.equals("")) {
				sql.append(and);
				String keys = "";
				keys = keyword.replace("*", "%");
				sql.append(" t.subject like '" + keys + "' ");
			}
			if (typeids != null && !typeids.get(0).equals("all")) {
				sql.append(and);
				StringBuffer typeidss = new StringBuffer();
				for (String typeid : typeids) {
					typeidss.append(","+typeid);
				}
				sql.append(" t.typeid in (" + typeidss.substring(1) + ") ");
			}
			if (blog != null && !blog.equals("")) {
				sql.append(and);
				sql.append("t.blog = " + blog);
			}
			if (special != null && !special.equals("")) {
				sql.append(and);
				String specialids = "";
				for (String specialid : special) {
					specialids += specialid + ",";
				}
				specialids = specialids.substring(0, specialids.length() - 1);
				sql.append(" t.special in (" + specialids + ") ");
			}
			if(rewardstatus!=null && !rewardstatus.equals("")){
				if(rewardstatus.equals("2")){
					sql.append(and);
					sql.append(" t.price>0 ");
				}else if(rewardstatus.equals("1")){
					sql.append(and);
					sql.append(" t.price<0 ");
				}
			}
			if (digest != null && !digest.equals("")) {
				sql.append(and);
				StringBuffer digests = new StringBuffer();
				for (String digestid : digest) {
					digests.append(","+digestid);
				}
				sql.append(" t.digest in (" + digests.substring(1) + ") ");
			}
			if (stick != null && !stick.equals("")) {
				sql.append(and);
				StringBuffer sticks = new StringBuffer();
				for (String stickid : stick) {
					sticks .append(","+stickid);
				}
				sql.append(" t.displayorder in (" + sticks.substring(1) + ") ");
			}
			if (orderby != null && !orderby.equals("")) {
				sql.append(" ORDER BY " + orderby+" DESC ");
			}
			int startrowcount = Common.toDigit(startrow);
			int itemscount =  Common.toDigit(items);
			List<Map<String,String>> threadlist = dataBaseService.executeQuery(sql.toString()+" limit "+startrowcount+","+itemscount);
			StringBuffer preebuffer = new StringBuffer();
			if (threadlist != null && threadlist.size()>0) {
				SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
				for (Map<String,String> thread:threadlist) {
					String subject = thread.get("subject");
					String fullsubject = thread.get("subject");
					if (Common.toDigit(maxlength) < subject.length()) {
						subject = subject.substring(0, Common.toDigit(maxlength))+ "...";
					}
					if (threadtype != null && threadtype.equals("1")) {
						subject = !Common.empty(thread.get("typename"))?"[" + thread.get("typename") + "]" + subject:subject;
						fullsubject = !Common.empty(thread.get("typename"))?"[" + thread.get("typename") + "]" + fullsubject:fullsubject;
					}
					String message = thread.get("message");
					message = Common.addslashes(Common.cutstr(Common.htmlspecialchars(Common.strip_tags(Common.nl2br(message)).replaceAll("(?s)(\\[.+\\])", "")),255,null)).replace("\'", "&nbsp;").replace("\n", "").replace("\r", "");
					String forumss = thread.get("name");
					forumss = forumss==null?null:Common.addslashes(forumss).replace("\'", "&nbsp;");
					String author = thread.get("author");
					String datetime = Common.gmdate(sdf_all, Integer.parseInt(thread.get("dateline")));
					String lastpost = Common.gmdate(sdf_all, Integer.parseInt(thread.get("lastpost")));
					String lastposter = thread.get("lastposter");
					String replices = thread.get("replies");
					String views = thread.get("views");
					String link = boardurl+"viewthread.jsp?tid="+thread.get("tid");
					String resultpreview = "";
					if (!Common.empty(highlight) && Common.toDigit(thread.get("highlight")) > 0) {
						Common.procThread(thread);
						String hight = thread.get("highlight").replace("\"", "'");
						resultpreview = jstemplate.replaceAll("\\(subject\\)","<a href='"+boardurl+"viewthread.jsp?tid="+thread.get("tid")+"'"+hight+LinkTarget+">" + subject+ "</a>");
					} else {
						resultpreview = jstemplate.replaceAll("\\(subject\\)","<a href='"+boardurl+"viewthread.jsp?tid="+thread.get("tid")+"'"+LinkTarget+">" + subject + "</a>");
					}
					resultpreview = resultpreview.replaceAll("\\(subject_nolink\\)", subject);
					resultpreview = resultpreview.replaceAll("\\(subject_full\\)", fullsubject);
					if (picpre != null && !picpre.equals("")) {
						resultpreview = resultpreview.replaceAll("\\(prefix\\)", "<img src='" + picpre + "' border='0' align='absmiddle'>");
					} else {
						resultpreview = resultpreview.replaceAll("\\(prefix\\)", "");
					}
					resultpreview = resultpreview.replaceAll("\\(message\\)",message);
					resultpreview = resultpreview.replaceAll("\\(forum\\)","<a href='"+boardurl+"forumdisplay.jsp?fid="+thread.get("fid")+"'"+LinkTarget+">" + forumss + "</a>");
					resultpreview = resultpreview.replaceAll("\\(author\\)","<a href='"+boardurl+"space.jsp?uid="+thread.get("authorid")+"'"+LinkTarget+">" + author + "</a>");
					resultpreview = resultpreview.replaceAll("\\(dateline\\)",datetime);
					resultpreview = resultpreview.replaceAll("\\(lastposter\\)", "<a href='"+boardurl+"space.jsp?action=viewpro&username="+Common.encode(lastposter)+"'"+LinkTarget+">" + lastposter+ "</a>");
					resultpreview = resultpreview.replaceAll("\\(lastpost\\)",lastpost);
					resultpreview = resultpreview.replaceAll("\\(replies\\)",replices);
					resultpreview = resultpreview.replaceAll("\\(views\\)",views);
					resultpreview = resultpreview.replaceAll("\\(link\\)", link);
					preebuffer.append(resultpreview);
				}
			}
			threadlist = null;
			datalist = preebuffer.toString();
		}else if(type.equals("1")){
			String orderby = (String) paramMap.get("orderby");
			String startrow = (String) paramMap.get("startrow");
			String items = (String) paramMap.get("items");
			String newwindow = (String)paramMap.get("newwindow");
			String LinkTarget	= "1".equals(newwindow) ? " target='_blank'" : ("2".equals(newwindow) ? " target='main'" : "");
			List<String> forums_forums = null;
			Map forumssMap = (Map) paramMap.get("forums_forums");
			if (forumssMap != null) {
				forums_forums = new ArrayList<String>();
				Iterator it = forumssMap.keySet().iterator();
				while (it.hasNext()) {
					forums_forums.add(forumssMap.get(it.next()).toString());
				}
			}
			StringBuffer sql = new StringBuffer("select fid,name,threads,posts,todayposts from jrun_forums as f ");
			String and = "";
			String where = " where ";
			if (forums_forums != null && !forums_forums.get(0).equals("")&& !forums_forums.get(0).equals("all")) {
				sql.append(where);
				where = " ";
				sql.append(and);
				and = " and ";
				String fids = "";
				for (String fid : forums_forums) {
					fids += fid + ",";
				}
				fids = fids.substring(0, fids.length() - 1);
				sql.append(" f.fup in (" + fids + ") ");
			}
			if (orderby != null && !orderby.equals("")) {
				if(orderby.equals("displayorder")){
					sql.append(" order by " + orderby);
				}else{
					sql.append(" order by " + orderby+" DESC ");
				}
			}
			int startrowcount = Common.toDigit(startrow);
			int itemscount = Common.toDigit(items);
			List<Map<String,String>>forumslist = dataBaseService.executeQuery(sql.toString()+" limit "+startrowcount+","+itemscount);
			StringBuffer preebuffer = new StringBuffer();
			if (forumslist != null && forumslist.size()>0) {
				for (Map<String,String> forums:forumslist) {
					String forumsname = forums.get("name");
					forumsname = Common.addslashes(forumsname).replace("\'", "&nbsp;");
					String threads = forums.get("threads");
					String posts = forums.get("posts");
					String todayposts = forums.get("todayposts");
					String link = boardurl+"forumdisplay.jsp?fid="+forums.get("fid");
					String resultpreview = jstemplate.replaceAll("\\(forumname\\)", "<a href='"+boardurl+"forumdisplay.jsp?fid="+forums.get("fid")+"'"+LinkTarget+">" + forumsname+ "</a>");
					resultpreview = resultpreview.replaceAll("\\(forumname_nolink\\)", forumsname);
					resultpreview = resultpreview.replaceAll("\\(threads\\)",threads);
					resultpreview = resultpreview.replaceAll("\\(posts\\)",posts);
					resultpreview = resultpreview.replaceAll("\\(todayposts\\)", todayposts);
					resultpreview = resultpreview.replaceAll("\\(link\\)", link);
					preebuffer.append(resultpreview);
				}
			}
			forumslist = null;
			datalist = preebuffer.toString();
		}else if(type.equals("2")){
			StringBuffer sql = new StringBuffer("select m.username,m.uid,m.regdate,m.credits,mm.avatar,mm.avatarwidth from jrun_members as m inner join jrun_memberfields as mm on m.uid=mm.uid");
			String orderby = (String) paramMap.get("orderby");
			String startrow = (String) paramMap.get("startrow");
			String items = (String) paramMap.get("items");
			String newwindow = (String)paramMap.get("newwindow");
			String LinkTarget	= "1".equals(newwindow) ? " target='_blank'" : ("2".equals(newwindow) ? " target='main'" : "");
			if (orderby != null && !orderby.equals("")) {
				sql.append(" order by " + orderby + " desc");
			}
			if(orderby != null && orderby.equals("todayposts")){
				sql = new StringBuffer("SELECT DISTINCT(p.author) AS username,p.authorid AS uid,COUNT(p.pid) AS postnum,mf.`avatar`,mf.`avatarwidth`,m.credits,m.regdate FROM `jrun_posts` p LEFT JOIN `jrun_memberfields` mf ON mf.`uid` = p.`authorid` LEFT JOIN jrun_members m ON m.uid=p.authorid WHERE p.`dateline`>="+(Common.time()-86400)+" AND p.`authorid`!='0' GROUP BY p.`author` ORDER BY `postnum` DESC");
			}
			int startrowcount = Common.toDigit(startrow);
			int itemscount = Common.toDigit(items);
			List<Map<String,String>> memberlist = dataBaseService.executeQuery(sql.toString()+" limit "+startrowcount+","+itemscount);
			StringBuffer preebuffer = new StringBuffer();
			if (memberlist != null&&memberlist.size()>0) {
				SimpleDateFormat sdf_dateformat=Common.getSimpleDateFormat(dateformat,timeoffset);
				for (Map<String,String> member:memberlist) {
					String membername = member.get("username");
					String values = member.get("credits");
					String today = Common.gmdate(sdf_dateformat, Integer.parseInt(member.get("regdate")));
					String resultpreview = jstemplate.replaceAll("\\(member\\)", "<a href='"+boardurl+"space.jsp?uid="+member.get("uid")+"'"+LinkTarget+">" + membername+ "</a>");
					String avatar = member.get("avatar");
					int avatarwidth = Common.intval(member.get("avatarwidth"));
					int seavatarwidth = Common.intval(settings.get("maxavatarpixel"));
					String windth = avatarwidth>0?"' width='"+(avatarwidth < seavatarwidth? avatarwidth : seavatarwidth):"";
					avatar = !Common.empty(avatar)?"<a href='"+boardurl+"space.jsp?uid="+member.get("uid")+"'"+LinkTarget+"><img src='"+(Common.matches(avatar, "(?i)^http://") ? avatar : boardurl+avatar)+windth+"' border=0 alt='' /></a>":"";
					resultpreview = resultpreview.replaceAll("\\(avatar\\)",avatar);
					resultpreview = resultpreview.replaceAll("\\(regdate\\)",today);
					resultpreview = resultpreview.replaceAll("\\(value\\)",values);
					preebuffer.append(resultpreview);
				}
			}
			memberlist = null;
			datalist = preebuffer.toString();
		}else if(type.equals("3")){
			Map forumsMap = (Map) paramMap.get("forums");
			Map membersMap = (Map) paramMap.get("members");
			Map onlineMap = (Map) paramMap.get("online");
			Map onlinemembersMap = (Map) paramMap.get("onlinemembers");
			Map postsMap = (Map) paramMap.get("posts");
			Map threadsMap = (Map) paramMap.get("threads");
			StringBuffer preebuffer = new StringBuffer();
			if (forumsMap.get("display") != null) {
				String resultpreview = jstemplate.replaceAll("\\(name\\)",forumsMap.get("title").toString());
				int forumscount = systemToolServer.findFourmsCount();
				resultpreview = resultpreview.replaceAll("\\(value\\)",forumscount + "");
				preebuffer.append(resultpreview);
			}
			if (threadsMap.get("display") != null) {
				String resultpreview = jstemplate.replaceAll("\\(name\\)",threadsMap.get("title").toString());
				int threadcount = systemToolServer.findThreadCount();
				resultpreview = resultpreview.replaceAll("\\(value\\)",	threadcount + "");
				preebuffer.append(resultpreview);
			}
			if (postsMap.get("display") != null) {
				String resultpreview = jstemplate.replaceAll("\\(name\\)",postsMap.get("title").toString());
				int postcount = systemToolServer.findPostCount();
				resultpreview = resultpreview.replaceAll("\\(value\\)",postcount + "");
				preebuffer.append(resultpreview);
			}
			if (membersMap.get("display") != null) {
				String resultpreview = jstemplate.replaceAll("\\(name\\)",membersMap.get("title").toString());
				int membercount = systemToolServer.findMembersCount();
				resultpreview = resultpreview.replaceAll("\\(value\\)",	membercount + "");
				preebuffer.append(resultpreview);
			}
			if (onlineMap.get("display") != null) {
				String resultpreview = jstemplate.replaceAll("\\(name\\)",onlineMap.get("title").toString());
				int onlinecount = systemToolServer.findSessionsCountByType(false);
				resultpreview = resultpreview.replaceAll("\\(value\\)",onlinecount + "");
				preebuffer.append(resultpreview);
			}
			if (onlinemembersMap.get("display") != null) {
				String resultpreview = jstemplate.replaceAll("\\(name\\)",onlinemembersMap.get("title").toString());
				int onlinememcount = systemToolServer.findSessionsCountByType(true);
				resultpreview = resultpreview.replaceAll("\\(value\\)",	onlinememcount + "");
				preebuffer.append(resultpreview);
			}
			datalist = preebuffer.toString();
		}else if(type.equals("4")){
			Map images_forumsMap = (Map) paramMap.get("images_forums");
			Map digestMap = (Map) paramMap.get("digest");
			String newwindow = (String)paramMap.get("newwindow");
			String LinkTarget	= "1".equals(newwindow) ? " target='_blank'" : ("2".equals(newwindow) ? " target='main'" : "");
			List<String> images_forums = null;
			if (images_forumsMap != null) {
				images_forums = new ArrayList<String>();
				Iterator it = images_forumsMap.keySet().iterator();
				while (it.hasNext()) {
					images_forums.add(images_forumsMap.get(it.next()).toString());
				}
			}
			List<String> digest = null;
			if (digestMap != null) {
				digest = new ArrayList<String>();
				Iterator it = digestMap.keySet().iterator();
				while (it.hasNext()) {
					digest.add(it.next().toString());
				}
			}
			String blog = (String) paramMap.get("blog");
			String startrow = (String) paramMap.get("startrow");
			String items = (String) paramMap.get("items");
			String maxheight = (String) paramMap.get("maxheight");
			String maxwidth = (String) paramMap.get("maxwidth");
			StringBuffer sql = new StringBuffer("select a.tid,a.filename,a.attachment,t.subject,a.remote,a.thumb,t.author,t.dateline,a.description FROM jrun_attachments a LEFT JOIN jrun_threads t ON a.tid=t.tid WHERE a.readperm = 0 ");
			String and = " AND ";
			if (images_forums != null && !images_forums.get(0).equals("")&& !images_forums.get(0).equals("all")) {
				sql.append(and);
				String fids = "";
				for (String fid : images_forums) {
					fids += fid + ",";
				}
				fids = fids.substring(0, fids.length() - 1);
				sql.append(" t.fid in (" + fids + ") ");
			}
			if (blog != null && !blog.equals("")) {
				sql.append(and);
				sql.append("t.blog = " + blog);
			}
			if (digest != null && !digest.equals("")) {
				sql.append(and);
				String digests = "";
				for (String digestid : digest) {
					digests += digestid + ",";
				}
				digests = digests.substring(0, digests.length() - 1);
				sql.append(" t.digest in (" + digests + ") ");
			}
			sql.append(" AND a.isimage = 1 AND t.displayorder>=0 GROUP BY a.tid ORDER BY a.dateline DESC,a.tid DESC");
			int startrowcount = Common.toDigit(startrow);
			int itemscount =  Common.toDigit(items);
			List<Map<String,String>> acctchmentlist = dataBaseService.executeQuery(sql.toString()+" limit "+startrowcount+","+itemscount);
			StringBuffer preebuffer = new StringBuffer();
			if (acctchmentlist != null && acctchmentlist.size()>0) {
				String imgsize = (!Common.empty(maxwidth) ? " width='"+maxwidth+"'" : "")+(!Common.empty(maxheight) ? " height='"+maxheight+"'" : "");
				Map<String,String> ftpmap = dataParse.characterParse(settings.get("ftp"), false);
				String attachurl = settings.get("attachurl");
				attachurl = Common.matches(attachurl, "(?i)^((https?|ftps?)://|www\\.)")?attachurl:boardurl+attachurl;
				SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat,timeoffset);
				for (Map<String,String> attach:acctchmentlist) {
					String subject = attach.get("subject");
					String dateline = Common.gmdate(sdf_all, Integer.parseInt(attach.get("dateline")));
					String image = attach.get("remote").equals("1")?ftpmap.get("attachurl")+"/"+attach.get("attachment"):attachurl+"/"+attach.get("attachment");
					image = attach.get("thumb").equals("1")?image+".thumb.jpg":image;
					String link = boardurl+"viewthread.jsp?tid="+attach.get("tid");
					String resultpreview = jstemplate.replaceAll("\\(image\\)",	"<a href="+link+LinkTarget+"><img src='" + image +"'"+ imgsize + " border='0' alt='"+(!Common.empty(attach.get("description")) ? attach.get("description")+"&#13&#10" : "")+subject+"&#13&#10"+attach.get("author")+dateline+"'></a>");
					resultpreview = resultpreview.replaceAll("\\(imgfile\\)",image);
					resultpreview = resultpreview.replaceAll("\\(subject\\)",subject);
					resultpreview = resultpreview.replaceAll("\\(link\\)", link);
					preebuffer.append(resultpreview);
				}
			}
			acctchmentlist = null;
			datalist = preebuffer.toString();
		}else{
			return null;
		}
		if(!jstemplatebody.equals("")){
			jstemplatebody = jstemplatebody.replaceFirst("\\[node\\](.+?)\\[/node\\]", datalist);
			datalist = jstemplatebody.replaceAll("\\[node\\](.+?)\\[/node\\]", "");
		}
		if(jscharset==1){
			try {
				if("GBK".equals(JspRunConfig.CHARSET)){
					datalist = new String(datalist.getBytes("GBK"),"UTF-8");
				}else{
					datalist = new String(datalist.getBytes("UTF-8"),"GBK");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return datalist;
	}
	private String rewriteContent(String content,String boardurl) {
		int reWriteStatus = Common.intval(ForumInit.settings.get("rewritestatus"));
		if (reWriteStatus <= 0) {
			return content ;
		}
		if ((reWriteStatus & 1) > 0) {
			content = rewriteURL("forum", content,
					"(?is)\\<a href\\=\'"+Common.pregQuote(boardurl, '/')+"forumdisplay\\.jsp\\?fid\\=(\\d+)(&amp;page\\=(\\d+))?\'([^\\>]*)\\>",
					boardurl);
		}
		if ((reWriteStatus & 2) > 0) {
			content = rewriteURL(
					"thread",
					content,
					"(?is)\\<a href\\=\'"+Common.pregQuote(boardurl, '/')+"viewthread\\.jsp\\?tid\\=(\\d+)(&amp;extra\\=page\\%3D(\\d+))?(&amp;page\\=(\\d+))?\'([^\\>]*)\\>",
					boardurl);
		}
		if ((reWriteStatus & 4) > 0) {
			content = rewriteURL("space", content,
					"(?is)\\<a href\\=\'"+Common.pregQuote(boardurl, '/')+"space\\.jsp\\?(uid\\=(\\d+)|username\\=([^&]+?))\'([^\\>]*)\\>",
					boardurl);
		}
		return content;
	}
	private String rewriteURL(String pre, String content, String regex,String boardurl) {
		Pattern pCode = Pattern.compile(regex);
		Matcher m = pCode.matcher(content);
		StringBuffer b = new StringBuffer();
		while (m.find()) {
			String url = null;
			if ("forum".equals(pre)) {
				String fid = m.group(1);
				int page = Common.intval(m.group(3));
				String extra = Common.stripslashes(m.group(4));
				url = fid + "-" + (page > 0 ? page : 1) + ".html\'" + extra + ">";
			} else if ("thread".equals(pre)) {
				String tid = m.group(1);
				int page = Common.intval(m.group(5));
				int prevPage = Common.intval(m.group(3));
				String extra = Common.stripslashes(m.group(6));
				url = tid + "-" + (prevPage > 0 ? prevPage : 1) + "-" + (page > 0 ? page : 1) + ".html\'"
						+ extra + ">";
			} else if ("space".equals(pre)) {
				int uid = Common.intval(m.group(2));
				String username = m.group(3);
				String extra = Common.stripslashes(m.group(4));
				url = (uid > 0 ? "uid-" + uid : "username-" + username) + ".html\'" + extra + ">";
			} 
			m.appendReplacement(b, "<a href=\'" +boardurl+ pre + "-" + Matcher.quoteReplacement(url));
		}
		m.appendTail(b);
		return b.toString();
	}
}