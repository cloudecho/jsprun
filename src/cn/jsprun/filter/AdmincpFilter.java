package cn.jsprun.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class AdmincpFilter implements Filter {
	public void init(FilterConfig fc) throws ServletException {
	}
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)res;
		HttpSession session = request.getSession();
		if(session.getAttribute("members")==null||request.getRequestURI().matches(".*/admin.*")){
			response.sendRedirect(request.getContextPath()+"/admincp.jsp");
		}else{			
			chain.doFilter(req, res);
		}
	}
	public void destroy() {
	}
}
