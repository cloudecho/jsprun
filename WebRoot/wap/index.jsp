<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../forumdata/cache/usergroup_${jsprun_groupid}.jsp"/>
<jsp:include page="../forumdata/cache/cache_forums.jsp"/>
<jsp:include page="../forumdata/cache/cache_censor.jsp"/>
<c:if test="${jsprun_adminid>0}"><jsp:include page="../forumdata/cache/admingroup_${jsprun_adminid}.jsp"/></c:if>
<c:choose>
<c:when test="${param.action != null}">
	<c:choose>
		<c:when test="${param.action == 'home' || param.action == 'login' || param.action == 'register' || param.action == 'search' || param.action == 'stats' || param.action == 'my' || param.action == 'myphone' || param.action == 'goto' || param.action == 'forum' || param.action == 'thread' || param.action == 'post' || param.action == 'pm'}">
			<c:choose>
				<c:when test="${param.action == 'goto'}"><jsp:forward page="/wap.do?actionMethod=goTo"/></c:when>
				<c:otherwise><jsp:forward page="/wap.do?actionMethod=${param.action}"/></c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise><jsp:forward page="/wap/include/accessdenied.jsp"/></c:otherwise>
	</c:choose>
</c:when>
<c:otherwise><jsp:forward page="/wap.do?actionMethod=home"/></c:otherwise>
</c:choose>