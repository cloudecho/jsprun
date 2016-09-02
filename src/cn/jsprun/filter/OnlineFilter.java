package cn.jsprun.filter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Members;
import cn.jsprun.service.DataBaseService;
import cn.jsprun.service.MemberService;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.HibernateUtil;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Md5Token;
public class OnlineFilter implements Filter {
	private DataParse dataParse=(DataParse)BeanFactory.getBean("dataParse");
	public void init(FilterConfig fc) throws ServletException {}
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		request.setAttribute("starttime",System.currentTimeMillis());
		int timestamp=Common.time();
		request.setAttribute("timestamp",timestamp);
		String accessPath = request.getRequestURI();
		if (accessPath.endsWith("install.jsp")) {
			if(accessPath.endsWith("install/install.jsp")){
				response.sendRedirect("../install.jsp");
			}else{
				chain.doFilter(request, response);
			}
			return;
		}
		int index = accessPath.lastIndexOf("/");
		if(index != -1){
			accessPath=accessPath.substring(index + 1);
		}
		if(accessPath.startsWith("forum-")){
			accessPath="forumdisplay.jsp";
		}else if(accessPath.startsWith("thread-")){
			accessPath="viewthread.jsp";
		}
		request.setAttribute("CURSCRIPT",accessPath);
		if(HibernateUtil.getSessionFactory() == null){
			request.setAttribute("errorinfo", HibernateUtil.getMessage());
			request.getRequestDispatcher("/errors/error_sql.jsp").forward(request, response);
			return;
		}
		Map<String,String> settings=ForumInit.settings;
		if(settings==null){
			initForum(request.getSession().getServletContext());
			settings=ForumInit.settings;
			File file = new File(JspRunConfig.realPath + "./forumdata/cache/cache_index.jsp");
			if(!file.isFile()){
				Cache.updateCache();
			}
		}
		int attackevasive=Common.toDigit(settings.get("attackevasive"));
		if(attackevasive>0&&this.security(request, response,timestamp, attackevasive)){
			return;
		}
		if("1".equals(settings.get("nocacheheaders"))){
			response.setHeader("Pragma","no-cache");   
			response.setHeader("Cache-Control","private, post-check=0, pre-check=0, max-age=0");   
			response.setDateHeader("Expires",0);
		}
		HttpSession httpSession = request.getSession();
		Map<Integer,Map<String,Object>> languages=dataParse.characterParse(settings.get("languages"), true);
		request.setAttribute("languages", languages);
		Locale locale=(Locale)httpSession.getAttribute("org.apache.struts.action.LOCALE");
		if(locale==null){
			String lang=null;
			Iterator<Entry<Integer,Map<String,Object>>> itor = languages.entrySet().iterator();
			while(itor.hasNext()){
				Entry<Integer,Map<String,Object>> e = itor.next();   
				Map<String,Object> language = e.getValue();
				if((Integer)language.get("default")>0&&(Integer)language.get("available")>0){
					lang=(String)language.get("language");
					break;
				}
			}
			if(lang!=null){
				String[] langs=lang.split("_");
				Locale newLocal=null;
				if(langs.length>1){
					newLocal=new Locale(langs[0],langs[1]);
				}else{
					newLocal=new Locale(langs[0]);
				}
				httpSession.setAttribute("org.apache.struts.action.LOCALE",newLocal);
			}
		}
		if (httpSession.getAttribute("timeoffset") == null) {
			Common.setDateformat(httpSession,settings);
		}
		if (request.getParameter("styleid") != null&&!"admincp.jsp".equals(accessPath)) {
			httpSession.setAttribute("styleid", request.getParameter("styleid"));
		}
		if (httpSession.getAttribute("boardurl") == null) {
			String port = request.getServerPort()==80?"":":"+request.getServerPort();
			httpSession.setAttribute("boardurl", (request.getScheme().concat("://").concat(request.getServerName())+port).concat(request.getContextPath()).concat("/"));
		}
		String jsprun_sid = (String) httpSession.getAttribute("jsprun_sid");
		Integer jsprun_uid = (Integer) httpSession.getAttribute("jsprun_uid");
		String sid = CookieUtil.getCookie(request, "sid", true,settings);
		if (sid == null && jsprun_sid == null || sid != null&& sid.equals("")) {
			sid = Common.getRandStr(6, false);
			CookieUtil.setCookie(request, response, "sid", sid, 604800,true,settings);
			httpSession.setAttribute("jsprun_sid", sid);
		} else if ((sid == null && jsprun_sid != null) || sid != null&& jsprun_sid != null && !jsprun_sid.equals(sid)) {
			sid = jsprun_sid;
			CookieUtil.setCookie(request, response, "sid", jsprun_sid,604800, true,settings);
		} else if (sid != null && jsprun_sid == null) {
			httpSession.setAttribute("jsprun_sid", sid);
		}
		if (jsprun_uid == null) {
			jsprun_uid = 0;
			String jsprun_userss = null;
			short groupid = 7;
			byte adminid = 0;
			String uid = CookieUtil.getCookie(request, "uid", true,settings);
			if (uid != null) {
				MemberService memberService  = (MemberService) BeanFactory.getBean("memberService");
				Members member = memberService.findMemberById(Common.toDigit(uid));
				if (member != null) {
					String validateAuth = Md5Token.getInstance().getLongToken(member.getPassword() + "\t"+ member.getSecques() + "\t"+ member.getUid());
					if (validateAuth.equals(CookieUtil.getCookie(request, "auth",true,settings))) {
						jsprun_uid = member.getUid();
						jsprun_userss = member.getUsername();
						groupid = member.getGroupid();
						adminid = member.getAdminid();
						httpSession.setAttribute("user", member);
						Common.setDateformat(httpSession, settings);
						httpSession.setAttribute("jsprun_pw", member.getPassword());
						if(member.getStyleid()>0){
							httpSession.setAttribute("styleid",member.getStyleid().toString());
						}
					}
				}
			} else {
				CookieUtil.setCookie(request, response, "uid", String.valueOf(jsprun_uid), 604800, true,settings);
			}
			httpSession.setAttribute("jsprun_uid", jsprun_uid);
			httpSession.setAttribute("jsprun_userss", jsprun_userss!=null?jsprun_userss:"");
			httpSession.setAttribute("jsprun_groupid", groupid);
			httpSession.setAttribute("jsprun_adminid", adminid);
			httpSession.setAttribute("formhash", Common.getRandStr(8, false));
		}
		Common.sessionExists(request,response,sid,jsprun_uid,settings);
		Common.calcGroup(httpSession,request,response,settings);
		if(Common.allowAccessBbs(request, response, httpSession, settings,accessPath)){
			return;
		}
		Common.setFtpValue(settings.get("ftp"), settings.get("authkey"));
		String statstatus = settings.get("statstatus");
		if(statstatus!=null&&statstatus.equals("1")&&request.getParameter("inajax")==null){
			Common.stats(request);
		}
		Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
		if(!Common.empty(creditspolicys.get("promotion_visit")) || !Common.empty(creditspolicys.get("promotion_register"))){
			request.setAttribute("promotion", true);
		}
		String fromuid = request.getParameter("fromuid");
		String fromuser = request.getParameter("fromuser");
		if(!Common.empty(fromuid) || !Common.empty(fromuser)){
			boolean promotion = (Boolean)request.getAttribute("promotion");
			if(promotion){
				String jsprun_userss = httpSession.getAttribute("jsprun_userss")!=null?(String)httpSession.getAttribute("jsprun_userss"):"";
				Common.promotion(request, response, settings, fromuid, fromuser, jsprun_uid, jsprun_userss, creditspolicys);
			}
		}
		chain.doFilter(request, response);
	}
	public void destroy() {
	}
	private synchronized void initForum(ServletContext context){
		if(ForumInit.settings==null){
			ForumInit.initServletContext(context);
		}
	}
	private boolean security(HttpServletRequest request,HttpServletResponse response,int timestamp,int attackevasive){
		boolean attackevasive1 =(attackevasive&1)>0;
		boolean attackevasive2 =(attackevasive&2)>0;
		boolean attackevasive4 =(attackevasive&4)>0;
		boolean attackevasive8 =(attackevasive&8)>0;
		int lastrequest=0;
		if(attackevasive1||attackevasive4){
			lastrequest=Common.toDigit(CookieUtil.getCookie(request, "lastrequest"));
			CookieUtil.setCookie(request, response, "lastrequest", String.valueOf(timestamp), timestamp + 816400);
		}
		if(attackevasive1){
			if(timestamp-lastrequest<1){
				this.securitymessage(request,response, "attachsave_1_subject", "attachsave_1_message",true,false);
				return true;
			}
		}
		if(attackevasive2&&(request.getHeader("x-forwarded-for")!=null||request.getHeader("via")!=null)){
			this.securitymessage(request,response, "attachsave_2_subject", "attachsave_2_message",false,false);
			return true;
		}
		if(attackevasive4){
			if(lastrequest==0||timestamp-lastrequest>60){
				this.securitymessage(request,response, "attachsave_4_subject", "attachsave_4_message",true,false);
				return true;
			}
		}
		if(attackevasive8){
			String questionanswer=null;
			String questiontime=null;
			String secqcode=CookieUtil.getCookie(request, "secqcode");
			if(secqcode!=null){
				String[] secqcodes=secqcode.split(",");
				if(secqcodes!=null&&secqcodes.length>=2){
					questionanswer=secqcodes[0];
					questiontime=secqcodes[1];
				}
			}
			String attackevasive_answer=CookieUtil.getCookie(request, "attackevasive_answer");
			if(questionanswer==null||questiontime==null||!questionanswer.equals(attackevasive_answer)){
				String secqsubmit=request.getParameter("secqsubmit");
				String answer=request.getParameter("answer");
				if(secqsubmit==null||secqsubmit!=null&&!Md5Token.getInstance().getLongToken(answer).equals(attackevasive_answer)){
					CookieUtil.setCookie(request, response, "secqcode", ","+timestamp, timestamp + 816400);
					String attackevasive_question=updatesecqaa(request, response);
					if(attackevasive_question!=null){
						this.securitymessage(request,response,updatesecqaa(request, response),"<input type='text' name='answer' size='8' maxlength='150' /><input class='button' type='submit' name='secqsubmit' value=' Submit ' />", false, true);
						return true;
					}
				}
				else{
					CookieUtil.setCookie(request, response, "secqcode", attackevasive_answer+","+timestamp, timestamp + 816400);
				}
			}
		}
		return false;
	}
	private void securitymessage(HttpServletRequest request,HttpServletResponse response,String subject,String message,boolean reload,boolean form){
		MessageResources mr = Common.getMessageResources(request);
		Locale locale = Common.getUserLocale(request);
		Map<String,String> scuritylang=new HashMap<String, String>();
		scuritylang.put("attachsave_1_subject", mr.getMessage(locale, "attachsave_1_subject"));
		scuritylang.put("attachsave_1_message", mr.getMessage(locale, "attachsave_1_message"));
		scuritylang.put("attachsave_2_subject", mr.getMessage(locale, "attachsave_2_subject"));
		scuritylang.put("attachsave_2_message", mr.getMessage(locale, "attachsave_2_message"));
		scuritylang.put("attachsave_4_subject", mr.getMessage(locale, "attachsave_4_subject"));
		scuritylang.put("attachsave_4_message", mr.getMessage(locale, "attachsave_4_message"));
		try {
			Common.setResponseHeader(response);
			boolean inajax=false;
			subject=scuritylang.get(subject)!=null?scuritylang.get(subject):subject;
			PrintWriter out=response.getWriter();
			if(inajax){
			}else{
				out.write("<html>");
				out.write("<head>");
				out.write("<title>"+subject+"</title>");
				out.write("</head>");
				out.write("<body bgcolor='#FFFFFF'>");
				if(reload) {
					out.write("<script language='JavaScript'>");
					out.write("function reload() {");
					out.write("	document.location.reload();");
					out.write("}");
					out.write("setTimeout('reload()', 1001);");
					out.write("</script>");
				}
				if(form) {
					out.write("<form action='"+request.getRequestURI()+"' method='POST' target='_top'>");
				}
				out.write("<table cellpadding='0' cellspacing='0' border='0' width='700' align='center' height='85%'>");
				out.write("  <tr align='center' valign='middle'>");
				out.write("    <td>");
				out.write("    <table cellpadding='10' cellspacing='0' border='0' width='80%' align='center' style='font-family: Verdana, Tahoma; color: #666666; font-size: 11px'>");
				out.write("    <tr>");
				out.write("      <td valign='middle' align='center' bgcolor='#EBEBEB'>");
				out.write("     	<br /><br /> <b style='font-size: 16px'>"+subject+"</b> <br /><br />");
				out.write(scuritylang.get(message)!=null?scuritylang.get(message):message);
				out.write("        <br /><br />");
				out.write("      </td>");
				out.write("    </tr>");
				out.write("    </table>");
				out.write("    </td>");
				out.write("  </tr>");
				out.write("</table>");
				if(form) {
					out.write("</form>");
				}
				out.write("</body>");
				out.write("</html>");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String updatesecqaa(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String,String>> itempools=((DataBaseService) BeanFactory.getBean("dataBaseService")).executeQuery("SELECT question,answer FROM jrun_itempool ORDER BY rand() LIMIT 1");
		String attackevasive_question=null;
		if(itempools!=null&&itempools.size()>0){
			attackevasive_question=itempools.get(0).get("question");
			CookieUtil.setCookie(request, response, "attackevasive_answer",Md5Token.getInstance().getLongToken(itempools.get(0).get("answer")), 31536000);
		}
		return attackevasive_question;
	}
}