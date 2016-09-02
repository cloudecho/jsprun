<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header_inc.jsp"%>
<div id="nav"><a href="archiver/">${valueObject.bbname}</a></div>
<h1><a href="${valueObject.link}" target="_blank"><bean:message key="full_version" />: ${valueObject.title}</a></h1>
<ul class="archiver_forumlist">
	<c:forEach items="${valueObject.groupList}" var="group">
		<li>
			<h3>${group.name}</h3>
			<ul><c:forEach items="${group.forumList}" var="forum">
				<li>
					<a href="archiver/${valueObject.qm}fid-${forum.fid}.html">${forum.name}</a>
					<ul><c:forEach items="${forum.forumList}" var="subForum"><li><a href="archiver/${valueObject.qm}fid-${subForum.fid }.html">${subForum.name}</a></li></c:forEach></ul>
				</li>
			</c:forEach></ul>
		</li>
	</c:forEach>
</ul>
<%@include file="foot_inc.jsp"%>