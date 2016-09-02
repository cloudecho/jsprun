<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/cache_icons.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="accessing" value="tag" scope="request" />
<c:set var="action" value="${param.action}" />
<c:choose>
	<c:when test="${action=='list'}">
		<jsp:forward page="templates/default/memberlist.jsp" />
	</c:when>
	<c:when test="${param.name!=null}">
		<jsp:forward page="/tags.do?actions=toThreadtags" />
	</c:when>
	<c:otherwise>
		<jsp:forward page="/tags.do?actions=toDistags" />
	</c:otherwise>
</c:choose>