package cn.jsprun.filter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.jsprun.utils.Common;
public class ArchiverFilter implements Filter {
	public void destroy() {
	}
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		String requestURI = request.getRequestURI();
		String contextName = request.getContextPath();
		String fid = null;
		String page = null;
		String tid = null;
		if(requestURI.trim().equals(contextName+"/archiver/")){
			String queryString = request.getQueryString();
			if(queryString == null || queryString.equals("")){
				fc.doFilter(req, res);
			}else{
				if(queryString.matches("^fid-([0-9]+)\\.html$")){
					fid = queryString.substring(4,queryString.indexOf(".html"));
				}else if(queryString.matches("^fid-([0-9]+)-page-([0-9]+)\\.html$")){
					int _2 = queryString.indexOf("-", 4);
					fid = queryString.substring(4,_2);
					page = queryString.substring(_2+6,queryString.indexOf(".html"));
				}else if(queryString.matches("^tid-([0-9]+)\\.html$")){
					tid = queryString.substring(4,queryString.indexOf(".html"));
				}else if(queryString.matches("^tid-([0-9]+)-page-([0-9]+)\\.html$")){
					int _2 = queryString.indexOf("-", 4);
					tid = queryString.substring(4,_2);
					page = queryString.substring(_2+6,queryString.indexOf(".html"));
				}else{
					HttpServletResponse response = (HttpServletResponse) res;
					Common.setResponseHeader(response);
					PrintWriter out=response.getWriter();
					out.write("Access Denied");
					out.flush();
					out.close();
					return;
				}
				StringBuffer forwardB = new StringBuffer("/archiver/index.jsp?");
				if(fid!=null){
					forwardB.append("fid="+fid+"&");
				}
				if(tid!=null){
					forwardB.append("tid="+tid+"&");
				}
				if(page!=null){
					forwardB.append("page="+page);
				}
				request.getRequestDispatcher(forwardB.toString()).forward(req, res);
				return ;
			}
		}else{
			fc.doFilter(req, res);
		}
	}
	public void init(FilterConfig arg0) throws ServletException {
	}
}
