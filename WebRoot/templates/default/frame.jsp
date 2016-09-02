<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${navtitle} ${settings.bbname} ${settings.seotitle} - JAVA.NO.1<bean:message key="admincp_community" /> - Powered by JspRun!</title>
${settings.seohead}
<meta name="keywords" content="${metadescription}JspRun!,JspRun,forums,bulletin${settings.seokeywords}">
<meta name="description" content="${settings.bbname} ${settings.seodescription} - JspRun!">
<meta name="generator" content="JspRun! ${settings.version}" />
<meta name="author" content="JspRun! Team and JspRun UI Team" />
<meta name="copyright" content="2007-2011 JspRun Inc." />
<meta name="MSSmartTagsPreventParsing" content="TRUE">
<meta http-equiv="MSThemeCompatible" content="Yes">
<link rel="SHORTCUT ICON" href="favicon.ico" />
<style type="text/css">
body {
	margin: 0px;
}
#frameswitch {
	background: url(${styles.IMGDIR}/frame_switch.gif) no-repeat 0;
	cursor: pointer;
}
</style>
<script src="include/javascript/common.js" type="text/javascript"></script>
<script src="include/javascript/iframe.js" type="text/javascript"></script>
<c:set var="framewidthcss" value="${settings.framewidth-3}" scope="request"/>
<script type="text/javascript">
function framebutton(){
	var obj = document.getElementById('navigation');
	var frameswitch = document.getElementById('frameswitch');
	var switchbar = document.getElementById('switchbar');
	if(obj.style.display == 'none'){
		obj.style.display = '';
		switchbar.style.left = '${framewidthcss}px';
		frameswitch.style.backgroundPosition = '0';
	}else{
		obj.style.display = 'none';
		switchbar.style.left = '0px';
		frameswitch.style.backgroundPosition = '-11';
	}
}

if(top != self) {
	top.location = self.location;
}
</script>
</head>
<body scroll="no">
<table border="0" cellPadding="0" cellSpacing="0" height="100%" width="100%">
	<tr>
		<td align="center" id="navigation" valign="middle" name="frametitle" width="${settings.framewidth}"><iframe name="leftmenu" frameborder="0" src="leftmenu.jsp?frameon=no" scrolling="auto" style="height: 100%; visibility: inherit; width: ${settings.framewidth}px; z-index: 1" onkeydown="refreshrightframe(event)"></iframe></td>
		<td style="width: 100%">
			<table id="switchbar" border="0" cellPadding="0" cellSpacing="0" width="11" height="100%" style="position: absolute; left: ${framewidthcss}px; background-repeat: repeat-y; background-position: -${framewidthcss}px">
				<tr><td onclick="framebutton()"><img id="frameswitch" src="images/common/none.gif" alt="" border="0" width="11" height="49" /></td></tr>
			</table>
			<iframe frameborder="0" scrolling="yes" name="main" src="${newurl}" style="height: 100%; visibility: inherit; width: 100%; z-index: 1;" onkeydown="refreshrightframe(event)"></iframe>
		</td>
	</tr>
</table>
<c:if test="${param.frameon=='no'}">
	<script type="text/javascript">
		window.location='${newurl}';
	</script>
</c:if>
</body>
</html>