<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="action" value="${param.action}" />
<c:choose>
	<c:when test="${action=='list'}">
		<jsp:forward page="/eccredit.do?action=list" />
	</c:when>
	<c:when test="${action=='rate'}">
		<jsp:forward page="/eccredit.do?action=rate" />
	</c:when>
	<c:when test="${action=='explain'}">
		<jsp:forward page="/eccredit.do?action=explain" />
	</c:when>
	<c:otherwise>
		<c:set var="jsprun_action" value="62" scope="request"/>
		<jsp:forward page="/eccredit.do?action=showEccredit" />
	</c:otherwise>
</c:choose>
