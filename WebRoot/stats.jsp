<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="jsprun_action" value="131" scope="request"/>
<c:set var="accessing" value="stats" scope="request" />
<c:set var="statstype" value="${param.type}" scope="request" />
<c:choose>
	<c:when test="${statstype=='views'}">
		<jsp:forward page="/statistic.do?actionMethodName=fluxStatistic" />
	</c:when>
	<c:when test="${statstype=='agent'}">
		<jsp:forward page="/statistic.do?actionMethodName=softWareOfUser" />
	</c:when>
	<c:when test="${statstype=='posts'}">
		<jsp:forward page="/statistic.do?actionMethodName=postsLog" />
	</c:when>
	<c:when test="${statstype=='forumsrank'}">
		<jsp:forward page="/statistic.do?actionMethodName=forumCompositor" />
	</c:when>
	<c:when test="${statstype=='threadsrank'}">
		<jsp:forward page="/statistic.do?actionMethodName=threadCompositor" />
	</c:when>
	<c:when test="${statstype=='postsrank'}">
		<jsp:forward page="/statistic.do?actionMethodName=postsCompositor" />
	</c:when>
	<c:when test="${statstype=='creditsrank'}">
		<jsp:forward page="/statistic.do?actionMethodName=creditCompositor" />
	</c:when>
	<c:when test="${statstype=='modworks'}">
		<jsp:forward page="/statistic.do?actionMethodName=manageStatistic" />
	</c:when>
	<c:when test="${statstype=='trade'}">
		<jsp:forward page="/statistic.do?actionMethodName=tradeCompositor" />
	</c:when>
	<c:when test="${statstype=='onlinetime'}">
		<jsp:forward page="/statistic.do?actionMethodName=onlineTimeCompositor" />
	</c:when>
	<c:when test="${statstype=='team'}">
		<jsp:forward page="/statistic.do?actionMethodName=manageTeam" />
	</c:when>
	<c:otherwise>
		<jsp:forward page="/statistic.do?actionMethodName=selectBaseInfo" />
	</c:otherwise>
</c:choose>
