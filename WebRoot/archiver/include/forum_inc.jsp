<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header_inc.jsp"%>
<c:choose>
	<c:when test="${valueObject.refuse}">
		<div id="nav"><a href="archiver/">${valueObject.bbname }</a></div>
		<div><bean:message key="forum_nonexistence_archiver" /></div>
	</c:when>
	<c:otherwise>
		<div id="nav">
			<a href="archiver/">${valueObject.bbname}</a> &raquo;
			<c:if test="${valueObject.navsub }"><a href="archiver/${valueObject.qm}fid-${valueObject.superForum.fid}.html">${valueObject.superForum.name}</a> &raquo;</c:if>
			<a href="archiver/${valueObject.qm}fid-${valueObject.currentForum.fid}.html">${valueObject.currentForum.name}</a>
		</div>
		<h1><a href="${valueObject.link}" target="_blank"><bean:message key="full_version" />: ${valueObject.title}</a></h1>
		<ul class="archiver_threadlist" type="1" start="${valueObject.start}">
			<c:forEach items="${valueObject.threadList}" var="thread">
				<li><a href="archiver/${valueObject.qm}tid-${thread.tid }.html">${thread.subject}</a> <em>(${thread.replies } <bean:message key="replies" />)</em></li>
			</c:forEach>
		</ul>
		<div class="archiver_pages">
			<c:if test="${valueObject.multi_inc.multi}"><%@ include file="multi.jsp"%></c:if>
		</div>
	</c:otherwise>
</c:choose>
<%@include file="foot_inc.jsp"%>