<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header_inc.jsp"%>
<c:choose>
	<c:when test="${valueObject.refuse}">
		<div id="nav"><a href="archiver/">${valueObject.bbname}</a></div><br />
		<div class="simpletable smalltxt"><div class="subtable altbg2"><br /><bean:message key="thread_nonexistence_inc" /><br /><br /></div></div>
	</c:when>
	<c:otherwise>
		<div id="nav">
			<a href="archiver/">${valueObject.bbname}</a> &raquo;
			<c:if test="${valueObject.navsub}"><a href="archiver/${valueObject.qm}fid-${valueObject.superForum.fid }.html">${valueObject.superForum.name}</a> <b>&raquo;</b></c:if>
			<a href="archiver/${valueObject.qm}fid-${valueObject.currentForum.fid}.html">${valueObject.currentForum.name}</a> &raquo; ${valueObject.threadSubject}
		</div>
		<h1><a href="${valueObject.link}" target="_blank"><bean:message key="full_version" />: ${valueObject.title}</a></h1>
		<c:forEach items="${valueObject.postsList}" var="post">
			<div class="archiver_post">
				<p><cite>${post.author}</cite> ${post.dateline}</p>
				<div class="archiver_postbody">${post.message}</div>
			</div>
		</c:forEach>
		<div class="archiver_pages">
			<c:if test="${valueObject.multi_inc.multi}"><%@ include file="multi.jsp"%></c:if>
		</div>
	</c:otherwise>
</c:choose>
<%@include file="foot_inc.jsp"%>