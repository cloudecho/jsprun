package cn.jsprun.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
public class AccessDenied_wml implements Filter {
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/wap/include/accessdenied.jsp");
		dispatcher.forward(request, response);
	}
	public void init(FilterConfig arg0) throws ServletException {
	}
}
