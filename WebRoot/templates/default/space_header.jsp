<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><c:choose><c:when test="${!empty memberfild.spacename}">${memberfild.spacename}</c:when><c:otherwise>${member.username}<bean:message key="space_userspace"/></c:otherwise></c:choose><c:choose><c:when test="${CURSCRIPT == 'viewpro'}"> -  <bean:message key="somebodys_profile" arg0="${member.username}"/></c:when><c:otherwise>${titleextra}</c:otherwise></c:choose> - Powered by JspRun!</title>
	<meta name="keywords" content="${metakeywords}JspRun!,JspRun,forums,bulletin${settings.seokeywords}">
	<meta name="description" content="${settings.bbname} ${settings.seodescription} - JspRun!">
	<meta name="generator" content="JspRun! ${settings.version}">
	<meta name="author" content="JspRun! Team & Jsprun UI Team">
	<meta name="copyright" content="2007-2011 Jsprun Inc.">
	<meta name="MSSmartTagsPreventParsing" content="TRUE">
	<meta http-equiv="MSThemeCompatible" content="Yes">
	<link rel="SHORTCUT ICON" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" id="stylecss" href="mspace/${memberspace.style}/style.css">
	<style type="text/css">
.popupmenu_popup { text-align: left; line-height: 1.4em; padding: 10px; overflow: hidden; border: 0; background: #FFF; background-repeat: repeat-x; background-position: 0 1px;  }
img { border: 0; }
</style>
	<script type="text/javascript">var attackevasive = '${attackevasive==null ? 0 : attackevasive}';var IMGDIR = '${styles.IMGDIR}';</script>
	<script src="include/javascript/common.js" type="text/javascript"></script>
	<script src="include/javascript/menu.js" type="text/javascript"></script>
	<script src="include/javascript/ajax.js" type="text/javascript"></script>
	<script src="include/javascript/viewthread.js" type="text/javascript"></script>
	<script type="text/javascript">
	if(${settings.frameon>0}){
		if(parent.location != self.location) {
			parent.location = self.location;
		}
	}
	function addbookmark(url, site){
	if(is_ie) {
		window.external.addFavorite(url, site);
	} else {
		alert('Please press "Ctrl+D" to add bookmark');
	}
}
</script>
	</head>
	<body>
		<div id="append_parent"></div>
		<div id="ajaxwaitid" style="position: absolute;right: 0"></div>
		<div id="menu_top">
			<div class="bgleft"></div>
			<div class="bg"><span><bean:message key="space_welcome"/>
			<c:choose>
				<c:when test="${jsprun_userss==''}"><bean:message key="guest"/></c:when>
				<c:otherwise>${jsprun_userss}</c:otherwise>
			</c:choose>&nbsp; &nbsp;<c:if test="${jsprun_uid==0}"><a href="${settings.regname}">${settings.reglinkname}</a> | <a href="logging.jsp?action=login"><bean:message key="login"/></a> | </c:if><c:if test="${jsprun_uid>0}"><a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="menu_logout"/></a> | <a href="pm.jsp"><bean:message key="pm"/></a> | </c:if><a href="index.jsp"><bean:message key="space_returnboard"/></a> </span></div>
			<div class="bgright"></div>
		</div>
		<div id="header">
			<div class="bg">
				<div class="title"><c:choose><c:when test="${!empty memberfild.spacename}">${memberfild.spacename}</c:when><c:otherwise>${member.username}<bean:message key="space_userspace"/></c:otherwise></c:choose></div>
				<div class="desc">${memberspace.description}</div>
				<div class="headerurl"><a href="space.jsp?uid=${member.uid}" class="spaceurl">${boardurl}space.jsp?uid=${member.uid}</a> <a href="###" onclick="setcopy('${boardurl}space.jsp?uid=${member.uid}', '<bean:message key="post_copied"/>')"><bean:message key="space_copylink"/></a> | <a href="###"onclick="addbookmark('${boardurl}space.jsp?uid=${member.uid}', document.title)"><bean:message key="space_addfav"/></a></div>
			</div>
		</div>
		<div id="menu">
			<div class="block"><a href="space.jsp?uid=${member.uid}"><bean:message key="space_index"/></a>&nbsp; &nbsp;<c:if test="${usergroups.allowviewpro>0}"><a href="space.jsp?action=viewpro&uid=${member.uid}"><bean:message key="userinfo"/></a>&nbsp;&nbsp;&nbsp;</c:if><c:if test="${menuMap.myblogs=='ok'}"><a href="space.jsp?action=myblogs&uid=${member.uid}"><bean:message key="myblogs"/></a>&nbsp;&nbsp;&nbsp;</c:if><c:if test="${menuMap.mythreads=='ok'}"><a href="space.jsp?action=mythreads&uid=${member.uid}"><bean:message key="thread"/></a>&nbsp;&nbsp;&nbsp;</c:if><c:if test="${menuMap.myreplies=='ok'}"><a href="space.jsp?action=myreplies&uid=${member.uid}"><bean:message key="threads_replies"/></a>&nbsp;&nbsp;&nbsp;</c:if><c:if test="${menuMap.myrewards=='ok'}"><a href="space.jsp?action=myrewards&uid=${member.uid}"><bean:message key="thread_reward"/></a>&nbsp;&nbsp;&nbsp;</c:if><c:if test="${menuMap.mytrades=='ok'}"><a href="space.jsp?action=mytrades&uid=${member.uid}"><bean:message key="mytrades"/></a>&nbsp;&nbsp;&nbsp;</c:if><c:if test="${menuMap.myfavforums=='ok'}"><a href="space.jsp?action=myfavforums&uid=${member.uid}"><bean:message key="myfavforums"/></a>&nbsp;&nbsp;</c:if><c:if test="${menuMap.myfavthreads=='ok'}"><a href="space.jsp?action=myfavthreads&uid=${member.uid}"><bean:message key="myfavthreads"/></a>&nbsp;&nbsp;&nbsp;</c:if></div>
			<div class="control"><c:choose><c:when test="${jsprun_uid==member.uid}"><a href="memcp.jsp?action=spacemodule"><bean:message key="space_settings"/></a></c:when><c:otherwise><a href="space.jsp?uid=${jsprun_uid}"><bean:message key="space_my"/></a></c:otherwise></c:choose></div>
			<div class="icon"></div>
		</div>