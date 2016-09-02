package cn.jsprun.utils;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class CookieUtil {
	public static String getCookie(HttpServletRequest request,String var){
		return getCookie(request, var, false, null);
	}
	@SuppressWarnings("deprecation")
	public static String getCookie(HttpServletRequest request,String var,boolean prefix,Map<String,String> settings){
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			if(settings!=null){
				var=(prefix?settings.get("cookiepre"):"")+var;
			}
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(var)) {
					return cookie.getValue();
				} 
			}
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String var,String value,int life){
		setCookie(request, response, var, value, life, false, null);
	}
	@SuppressWarnings("deprecation")
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String var,String value,int life,boolean prefix,Map<String,String> settings){
		String cookiedomain=null;
		String cookiepath=null;
		if(settings!=null){
			cookiedomain=settings.get("cookiedomain");
			cookiepath=settings.get("cookiepath");
			var=(prefix?settings.get("cookiepre"):"")+var;
		}
		Cookie cookie=new Cookie(var,value);
		cookie.setMaxAge(life);
		if(cookiedomain!=null&&!"".equals(cookiedomain)){
			cookie.setDomain(cookiedomain);
		}
		if(cookiepath!=null&&!"".equals(cookiepath)){
			cookie.setPath(cookiepath);
		}
		cookie.setSecure(request.getServerPort()==443?true:false);
		response.addCookie(cookie);
	}
	@SuppressWarnings("unchecked")
	public static void clearCookies(HttpServletRequest request,HttpServletResponse response,Map<String,String> settings){
		setCookie(request, response, "sid", "", 0, true,settings);
		setCookie(request, response, "auth", "", 0, true,settings);
		setCookie(request, response, "uid", "", 0, true,settings);
		setCookie(request, response, "visitedfid", "", 0, true,settings);
		setCookie(request, response, "onlinedetail", "", 0, false,settings);
		HttpSession session=request.getSession();
		session.removeAttribute("jsprun_sid");
		session.setAttribute("jsprun_uid", 0);
		session.setAttribute("jsprun_userss", "");
		session.setAttribute("jsprun_pw", "");
		session.removeAttribute("user");
		Common.setDateformat(session, settings);
		session.setAttribute("jsprun_groupid",(short)7);
		session.setAttribute("jsprun_adminid",(byte)0);
		session.setAttribute("styleid", settings.get("styleid"));
		request.setAttribute("refresh", "true");
	}
}