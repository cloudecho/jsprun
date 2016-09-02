package cn.jsprun.filter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileLock;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.WapperedResponse;
public class FileCaptureFilter implements Filter {
	public void init(FilterConfig fc) throws ServletException {
	}
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		Integer uid = (Integer) session.getAttribute("jsprun_uid");
		Map<String, String> settings = ForumInit.settings;
		String url = request.getRequestURI();
		if(url!=null&&url.endsWith("attachment.jsp")){
			chain.doFilter(request, response);
			return;
		}
		if (settings != null && uid != null) {
			WapperedResponse wapper = new WapperedResponse(response);
			chain.doFilter(request, wapper);
			String contentType = response.getContentType();
			if (contentType != null && contentType.startsWith("application/octet-stream")) {
				byte[] content = wapper.getByteData();
				ServletOutputStream out = response.getOutputStream();
				out.write(content);
				out.flush();
			} else {
				String content = wapper.getStringData().trim();
				int reWriteStatus = Common.intval(settings.get("rewritestatus"));
				if (reWriteStatus > 0) {
					content = rewriteContent(content, reWriteStatus);
				}
				if (uid == 0) {
					String accessPath = request.getRequestURI();
					int index = accessPath.lastIndexOf("/");
					if (index != -1 && accessPath.indexOf("archiver") == -1 && accessPath.indexOf("wap") == -1) {
						accessPath = accessPath.substring(index + 1);
					}
					if (request.getQueryString() == null && (accessPath.equals("index.jsp") || accessPath.equals("") || accessPath.equals(settings.get("indexname")))) {
						int cacheindexlife = Common.toDigit(settings.get("cacheindexlife"));
						if (cacheindexlife > 0) {
							boolean iswrite = request.getAttribute("CACHE_INDEX")!=null?true:false; 
							if(request.getAttribute("indexfileName")!=null&&iswrite){
								String indexfileName = (String)request.getAttribute("indexfileName");
								writeFile(indexfileName, content);
							}
						}
					} else if (accessPath.equals("viewthread.jsp") || accessPath.startsWith("thread-")) {
						int cachethreadlife = Common.toDigit(settings.get("cachethreadlife"));
						if (cachethreadlife > 0) {
							boolean iswrite = request.getAttribute("CACHE_THREADS")!=null?true:false; 
							if(request.getAttribute("fileName")!=null&&iswrite){
								String fileName = (String)request.getAttribute("fileName");
								writeFile(fileName, content);
							}
						}
					}
				}
				PrintWriter out = response.getWriter();
				out.write(content);
				out.flush();
			}
		} else {
			chain.doFilter(request, response);
		}
	}
	private String rewriteContent(String content, int reWriteStatus) throws IOException {
		if ((reWriteStatus & 1) > 0) {
			content = rewriteURL("forum", content,
					"(?is)\\<a href\\=\"forumdisplay\\.jsp\\?fid\\=(\\d+)(&amp;page\\=(\\d+))?\"([^\\>]*)\\>");
		}
		if ((reWriteStatus & 2) > 0) {
			content = rewriteURL(
					"thread",
					content,
					"(?is)\\<a href\\=\"viewthread\\.jsp\\?tid\\=(\\d+)(&amp;extra\\=page\\%3D(\\d+))?(&amp;page\\=(\\d+))?\"([^\\>]*)\\>");
		}
		if ((reWriteStatus & 4) > 0) {
			content = rewriteURL("space", content,
					"(?is)\\<a href\\=\"space\\.jsp\\?(uid\\=(\\d+)|username\\=([^&]+?))\"([^\\>]*)\\>");
		}
		if ((reWriteStatus & 8) > 0) {
			content = rewriteURL("tag", content,
					"(?is)\\<a href\\=\"tag\\.jsp\\?name\\=([^&]+?)\"([^\\>]*)\\>");
		}
		return content;
	}
	private String rewriteURL(String pre, String content, String regex) {
		Pattern pCode = Pattern.compile(regex);
		Matcher m = pCode.matcher(content);
		StringBuffer b = new StringBuffer();
		while (m.find()) {
			String url = null;
			if ("forum".equals(pre)) {
				String fid = m.group(1);
				int page = Common.intval(m.group(3));
				String extra = Common.stripslashes(m.group(4));
				url = fid + "-" + (page > 0 ? page : 1) + ".html\"" + extra + ">";
			} else if ("thread".equals(pre)) {
				String tid = m.group(1);
				int page = Common.intval(m.group(5));
				int prevPage = Common.intval(m.group(3));
				String extra = Common.stripslashes(m.group(6));
				url = tid + "-" + (prevPage > 0 ? prevPage : 1) + "-" + (page > 0 ? page : 1) + ".html\""
						+ extra + ">";
			} else if ("space".equals(pre)) {
				int uid = Common.intval(m.group(2));
				String username = m.group(3);
				String extra = Common.stripslashes(m.group(4));
				url = (uid > 0 ? "uid-" + uid : "username-" + username) + ".html\"" + extra + ">";
			} else if ("tag".equals(pre)) {
				String name = m.group(1);
				String extra = Common.stripslashes(m.group(2));
				url = name + ".html\"" + extra + ">";
			}
			m.appendReplacement(b, "<a href=\"" + pre + "-" + Matcher.quoteReplacement(url));
		}
		m.appendTail(b);
		return b.toString();
	}
	public void writeFile(String fileName, String content) throws IOException {
		FileOutputStream out = new FileOutputStream(fileName);
		OutputStreamWriter fwout = new OutputStreamWriter(out, JspRunConfig.CHARSET);
		BufferedWriter bw = new BufferedWriter(fwout);
		FileLock fl = out.getChannel().tryLock();
		if (fl.isValid()) {
			bw.write(content);
			fl.release();
		}
		bw.close();
		fwout.close();
		out.close();
		bw = null;
		fwout = null;
		out = null;
	}
	public void destroy() {
	}
}