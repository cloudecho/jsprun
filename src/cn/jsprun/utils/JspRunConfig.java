package cn.jsprun.utils;
import javax.servlet.http.HttpServletRequest;
public class JspRunConfig {
	public static final String CHARSET="UTF-8";
	public static final String VERSION="6.0.0";
	public static final int RELEASE=20110516;
	public static String realPath=null;
	public static void setRealPath(HttpServletRequest request) {
		realPath = request.getSession().getServletContext().getRealPath("/");
		if (realPath == null) {
			try {
				realPath = request.getSession().getServletContext().getResource("/").getPath();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!realPath.endsWith("/"))realPath = realPath + "/";
	}
}
