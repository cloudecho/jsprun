package cn.jsprun.filter;
import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.GZIPResponseWrapper;
public class GZIPFilter implements Filter {
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Map<String, String> settings = ForumInit.settings;
		int gzipCompress = settings != null ? Common.intval(settings.get("gzipcompress")) : 0;
		boolean inAjax = Common.empty(request.getParameter("inajax"));
		String accessPath = request.getRequestURI();
		if (gzipCompress == 1 && req instanceof HttpServletRequest && inAjax && !accessPath.contains("/wap/")) {
			String ae = request.getHeader("accept-encoding");
			if (ae != null && ae.indexOf("gzip") != -1) {
				GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
				chain.doFilter(req, wrappedResponse);
				wrappedResponse.finishResponse();
				return;
			}
		}
		chain.doFilter(req, res);
	}
	public void init(FilterConfig filterConfig) {
	}
	public void destroy() {
	}
}