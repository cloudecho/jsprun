<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>JspRun! -JAVA.NO.1<bean:message key="admincp_community"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="include/javascript/common.js" type="text/javascript"></script>
<script src="include/javascript/iframe.js" type="text/javascript"></script>
<link rel="SHORTCUT ICON" href="favicon.ico" />
</head>
<body style="margin: 0px;overflow-x:hidden;overflow-y:hidden">
<div style="position: absolute;top: 0px;left: 0px; z-index: 2;height: 65px;width: 100%">
	<iframe frameborder="0" id="header" name="header" src="admincp.jsp?action=header&sid=${jsprun_sid}" scrolling="no" style="height: 65px; visibility: inherit; width: 100%; z-index: 1;"></iframe>
</div>
<table border="0" cellPadding="0" cellSpacing="0" height="100%" width="100%" style="table-layout: fixed;">
	<tr><td width="165" height="65"></td><td></td></tr>
	<tr>
		<td><iframe frameborder="0" id="menu" name="menu" src="admincp.jsp?action=menu&sid=${jsprun_sid}" scrolling="yes" style="height: 100%; visibility: inherit; width: 100%; z-index: 1;overflow: auto;"></iframe></td>
		<td><iframe frameborder="0" id="main" name="main" src="admincp.jsp?${empty extra ? 'action=home' : extra}&sid=${jsprun_sid}" scrolling="yes" style="height: 100%; visibility: inherit; width: 100%; z-index: 1;overflow: auto;"></iframe></td>
	</tr>
</table>
</body>
</html>