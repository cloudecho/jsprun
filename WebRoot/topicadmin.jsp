<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="jsprun_action" value="201" scope="request"/>
<c:set var="action"
	value="${param.operation!=null?(param.operation): (param.action)}"
	scope="request" />
<c:choose>
	<c:when test="${action=='delpost'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=deletePost"/></c:when>
	<c:when test="${action=='delpostOperating'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=deletePostOperating"/></c:when>
	<c:when test="${action=='banpost'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=banPost"/></c:when>
	<c:when test="${action=='banpostOperating'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=banPostOperating"/></c:when>
	<c:when test="${action=='repair'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=repairOperating"/></c:when>
	<c:when test="${action=='removereward'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=removerewardOperating"/></c:when>
	<c:when test="${action=='copy'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=copy"/></c:when>
	<c:when test="${action=='copyOperating'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=operatingCopy"/></c:when>
	<c:when test="${action=='refund'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=operatingRefund"/></c:when>
	<c:when test="${action=='split'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=split"/></c:when>
	<c:when test="${action=='splitOperating'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=splitOperating"/></c:when>
	<c:when test="${action=='merge'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=merge"/></c:when>
	<c:when test="${action=='mergeOperating'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=mergeOperating"/></c:when>
	<c:when test="${action=='getip'}"><jsp:forward page="/modcp.do?actions=getip"/></c:when>
	<c:when test="${action=='moderate'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=operating"/></c:when>
	<c:when test="${action=='othermoderate'}"><jsp:forward page="/topicAdminAction.do?actionMonthName=otherOperating"/></c:when>
	<c:otherwise><jsp:forward page="/topicAdminAction.do?actionMonthName=moderate"/></c:otherwise>
</c:choose>