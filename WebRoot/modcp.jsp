<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/cache_viewthread.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="accessing" value="tag" scope="request" />
<c:set var="action" value="${param.action}" />
<c:choose>
	<c:when test="${action=='editmessage'}">
		<jsp:forward page="/modcp.do?actions=editmessage" />
	</c:when>
	<c:when test="${action=='editsubject'}">
		<jsp:forward page="/modcp.do?actions=editsubject" />
	</c:when>
</c:choose>