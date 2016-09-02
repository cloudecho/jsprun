<%@ page language="java" import="java.io.File,cn.jsprun.utils.*" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><bean:message key="board_message"/></title>
		<meta name="keywords" content="" />
		<meta name="description" content="JspRun!" />
		<meta name="generator" content="JspRun! ${settings.version}" />
		<meta name="author" content="JspRun! Team and JspRun UI Team" />
		<meta name="copyright" content="2007-2011 JspRun Inc." />
		<meta name="MSSmartTagsPreventParsing" content="True" />
		<meta http-equiv="MSThemeCompatible" content="Yes" />
		<link rel="SHORTCUT ICON" href="favicon.ico" />
	</head>
	<%
		ServletContext context=request.getSession().getServletContext();
		synchronized(context)
		{
			File root = new File(JspRunConfig.realPath + "./forumdata/cache/");
			if (!root.isDirectory()) {
				root.mkdir();
			}
			String cachename = "index.jsp";
			File[] files = root.listFiles();
			boolean flag = true;
			if (files != null) {
				for (File file : files) {
					String name = file.getName();
					if (Common.matches(name, cachename)) {
						flag = false;
					}
				}
			}
			if (flag) {
				ForumInit.initServletContext(context);
				Cache.updateCache();
				request.setAttribute("resultInfo","error_5001");
			}
			else{
				request.setAttribute("resultInfo","error_5002");
				exception.printStackTrace();
			}
		}
	 %>
	<body onkeydown="if(event.keyCode==27) return false;">
		<div class="wrap"><div class="box message"><p><bean:message key="${resultInfo}"/></p></div></div>
	</body>
</html>