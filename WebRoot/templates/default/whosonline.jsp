<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a>&raquo; <bean:message key="whosonline"/></div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<div class="mainbox">
	<h1><bean:message key="whosonline"/></h1>
<c:choose>
	<c:when test="${usergroups.allowviewip>0}">
		<table summary="" cellpadding="0" cellspacing="0">
			<thead><tr><td><bean:message key="username"/></td><td class="time"><bean:message key="time"/></td><td><bean:message key="online_action"/></td><td><bean:message key="online_in_forum"/></td><td><bean:message key="a_post_attachments_thread"/></td><td class="time"><bean:message key="ip"/></td></tr></thead>
			<tbody><c:forEach items="${onlinelist}" var="online"><tr><td><c:choose><c:when test="${online.uid>0}"><a href="space.jsp?uid=${online.uid}">${online.username}</a></c:when><c:otherwise><bean:message key="guest"/></c:otherwise></c:choose></td><td class="time">${online.lastactivity}</td><td>${online.action}</td><td><c:if test="${online.fid>0}"><a href="forumdisplay.jsp?fid=${online.fid}">${online.name}</a></c:if></td><td><c:if test="${online.tid>0}"><a href="viewthread.jsp?tid=${online.tid}">${online.subject}</a></c:if></td><td>${online.ip}</td></tr></c:forEach></tbody>
		</table>
	</c:when>
	<c:otherwise>
		<table summary="" cellpadding="0" cellspacing="0">
			<thead><tr><td><bean:message key="username"/></td><td class="time"><bean:message key="time"/></td><td><bean:message key="online_action"/></td><td><bean:message key="online_in_forum"/></td><td><bean:message key="a_post_attachments_thread"/></td></tr></thead>
			<tbody><c:forEach items="${onlinelist}" var="online"><tr><td><c:choose><c:when test="${online.uid>0}"><a href="space.jsp?uid=${online.uid}">${online.username}</a></c:when><c:otherwise><bean:message key="guest"/></c:otherwise></c:choose></td><td class="time">${online.lastactivity}</td><td>${online.action}</td><td><c:if test="${online.fid>0}"><a href="forumdisplay.jsp?fid=${online.fid}">${online.name}</a></c:if></td><td><c:if test="${online.tid>0}"><a href="viewthread.jsp?tid=${online.tid}">${online.subject}</a></c:if></td></tr></c:forEach></tbody>
		</table>
	</c:otherwise>
</c:choose>
</div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<jsp:include flush="true" page="footer.jsp" />