<%@ page language="java" pageEncoding="UTF-8"%>
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
	<body onkeydown="if(event.keyCode==27) return false;">
		<div class="wrap"><div class="box message"><p><bean:message key="error_404"/></p> <p><a href="javascript:history.back()"><bean:message key="message_return"/></a></p></div></div>
	</body>
</html>