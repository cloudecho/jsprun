<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%
	
	String language = request.getParameter("language");
	Locale locale = null; 
	String sqlFile = null;
	if("zh_cn".equals(language)){
		locale = new Locale("zh","cn");
		sqlFile = "./install/jsprun_zh_CN.sql";
	}else if("zh_tw".equals(language)){
		locale = new Locale("zh","tw");
		sqlFile = "./install/jsprun_zh_TW.sql";
	}else if("en".equals(language)){
		locale = new Locale("en");
		sqlFile = "./install/jsprun_en.sql";
		
	}else{
		locale = (Locale)pageContext.getServletContext().getAttribute(Globals.LOCALE_KEY);
		sqlFile = "./install/jsprun_zh_CN.sql";
	}
	session.setAttribute(Globals.LOCALE_KEY, locale);
	session.setAttribute("sqlFile", sqlFile);
	
	
	request.getRequestDispatcher("../install.jsp?action=license").forward(request, response);
%>