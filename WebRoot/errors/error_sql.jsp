<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Database exception</title>
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
		<div class="wrap">
			<div class="box message">
				<h1>Database exception</h1>
				<p><font color="red">JspRun! info:</font>${errorinfo}</p>
				<p><bean:message key="error_sql"/></p>
				<br>
				Time: <script type="text/javascript"> var now=new Date(); 
				document.write(now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" "+now.getHours()+":"+now.getMinutes())</script>
			</div>
		</div>
	</body>
</html>