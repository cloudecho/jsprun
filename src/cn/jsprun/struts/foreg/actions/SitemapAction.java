package cn.jsprun.struts.foreg.actions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Forums;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
public class SitemapAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward querysitmap(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		Map<String,String> forumStr = (Map<String,String>)request.getAttribute("forums");
		Map<String,Map<String,String>> forumMap=dataParse.characterParse(forumStr.get("forums"), false);
		int maxitemnum = 500;
		HttpSession session = request.getSession();
		String boardurl = (String)session.getAttribute("boardurl");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String baidusitemap = settings.get("baidusitemap");
		if(baidusitemap.equals("0")){
			try {
				response.getWriter().write("Baidu Sitemaps is closed!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		String sitePath="forumdata/sitemap.xml";
		String sitemapfile = JspRunConfig.realPath+sitePath;
		File sitemap = new File(sitemapfile);
		long xmlfiletime = 0;
		if(sitemap.exists()){
			xmlfiletime = sitemap.lastModified();
		}
		int baidusitemap_life = convertInt(settings.get("baidusitemap_life"));
		response.setHeader("Content-type:", "application/xml");
		String xmlcontent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+"<document xmlns:bbs=\"http://www.baidu.com/search/bbs_sitemap.xsd\">\n";
		if(timestamp-xmlfiletime>=baidusitemap_life*3600){
			xmlfiletime = timestamp - baidusitemap_life*3600;
			String timeoffset = (String)session.getAttribute("timeoffset");
			SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss", timeoffset);
			String fidarray = "0";
			String adminemail = settings.get("adminemail");
			List<Forums> forumlist = forumService.findAll();
			for(Forums f:forumlist){
				if(sitemapforumperm(f,forumMap)){
					fidarray = fidarray + "," + f.getFid();
				}
			}
			String sql = "SELECT tid, fid, subject, dateline, lastpost, replies, views, digest FROM jrun_threads WHERE dateline > "+(xmlfiletime+"").substring(0,10)+" AND fid IN ("+fidarray+") AND displayorder >=0 LIMIT "+maxitemnum;
			List<Map<String,String>> threadlist = dataBaseService.executeQuery(sql);
			xmlcontent = xmlcontent+"<webSite>"+boardurl+"</webSite>\n"+
		"	<webMaster>"+adminemail+"</webMaster>\n"+
		"	<updatePeri>"+baidusitemap_life+"</updatePeri>\n"+
		"	<updatetime>"+Common.gmdate(simpleDateFormat, timestamp)+"</updatetime>\n"+
		"	<version>JspRun! "+JspRunConfig.VERSION+"</version>\n";
			int rewritestatus = Common.intval(settings.get("rewritestatus"));
			for(Map<String,String> thread : threadlist){
				int dateline = Common.toDigit(thread.get("dateline"));
				int lastpost = Common.toDigit(thread.get("lastpost"));
				xmlcontent += "	<item>\n"+
				"		<link>"+boardurl+((rewritestatus & 2) > 0 ? "thread-"+thread.get("tid")+"-1-1.html" : "viewthread.jsp?tid="+thread.get("tid"))+"</link>\n"+
				"		<title>"+thread.get("subject")+"</title>\n"+
				"		<pubDate>"+Common.gmdate(simpleDateFormat, dateline)+"</pubDate>\n"+
				"		<bbs:lastDate>"+Common.gmdate(simpleDateFormat,lastpost)+"</bbs:lastDate>\n"+
				"		<bbs:reply>"+thread.get("replies")+"</bbs:reply>\n"+
				"		<bbs:hit>"+thread.get("views")+"</bbs:hit>\n"+
				"		<bbs:boardid>"+thread.get("fid")+"</bbs:boardid>\n"+
				"		<bbs:pick>"+(thread.get("digest").equals("") ? 0 : 1)+"</bbs:pick>\n"+
				"	</item>\n";
			}
			xmlcontent += "</document>";
			try {
				OutputStream out = new FileOutputStream(sitemapfile);
				OutputStreamWriter ot = new OutputStreamWriter(out,JspRunConfig.CHARSET);
				ot.write(xmlcontent);
				ot.close();
				out.close();
				request.getRequestDispatcher(sitePath).forward(request, response);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}else{
			try {
				request.getRequestDispatcher(sitePath).forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@SuppressWarnings("unused")
	private static int convertInt(String s) {
		int count = 0;
		try {
			count = Integer.parseInt(s);
		} catch (Exception e) {
		}
		return count;
	}
	private boolean sitemapforumperm(Forums f,Map<String,Map<String,String>> forumMap){
		if(!f.getType().equals("group")){
			String viewperm = forumMap.get(String.valueOf(f.getFid())).get("viewperm");
			return  (viewperm.equals("")||(!viewperm.equals("") && Common.forumperm(viewperm, Short.valueOf("7"), "")));
		}
		return false;
	}
}