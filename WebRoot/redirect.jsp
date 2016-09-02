<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid)!=null?(jsprun_groupid):7}.jsp"/>
<c:if test="${jsprun_adminid>0}">
	<jsp:include page="forumdata/cache/admingroup_${jsprun_adminid}.jsp"/>
</c:if>
<c:set var="goto" value="${param.goto}" scope="request"/>
<c:choose>
	<c:when test="${goto=='findpost'}">
		<jsp:forward page="/redirect.do?action=findpost"/>
	</c:when>
	<c:when test="${goto=='lastpost'}">
		<jsp:forward page="/redirect.do?action=lastpost"/>
	</c:when>
	<c:when test="${goto=='newpost'}">
		<jsp:forward page="/redirect.do?action=newpost"/>
	</c:when>
	<c:when test="${goto=='nextnewset'}">
		<jsp:forward page="/redirect.do?action=newset"/>
	</c:when>
	<c:when test="${goto=='nextoldset'}">
		<jsp:forward page="/redirect.do?action=newset"/>
	</c:when>
	<c:otherwise>
		<c:set var="errorInfo" value="undefined_action_return" scope="request"/>
		<c:set var="propertyKey" value="true" scope="request" />
		<jsp:forward page="templates/default/showmessage.jsp"/>
	</c:otherwise>
</c:choose>