<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="action" value="${param.action}" />
<c:choose>
	<c:when test="${action=='emailfriend'}">
		<c:set var="jsprun_action" value="122" scope="request"/>
		<jsp:forward page="/misc.do?actions=emailfriend" />
	</c:when>
	<c:when test="${action=='report'}">
		<c:set var="jsprun_action" value="123" scope="request"/>
		<jsp:forward page="/misc.do?actions=report" />
	</c:when>
	<c:when test="${action=='rate'}">
		<c:set var="jsprun_action" value="71" scope="request"/>
		<jsp:forward page="/misc.do?actions=rate" />
	</c:when>
	<c:when test="${action=='blog'}">
		<jsp:forward page="/misc.do?actions=blog" />
	</c:when>
	<c:when test="${action=='viewthreadmod'}">
		<jsp:forward page="/misc.do?actions=viewthreadmod" />
	</c:when>
	<c:when test="${action=='viewratings'}">
		<c:set var="jsprun_action" value="72" scope="request"/>
		<jsp:forward page="/misc.do?actions=viewratings"/>
	</c:when>
	<c:when test="${action=='removerate'}">
		<c:set var="jsprun_action" value="71" scope="request"/>
		<jsp:forward page="/misc.do?actions=removerate"/>
	</c:when>
	<c:when test="${action=='viewvote'}">
		<jsp:forward page="/misc.do?actions=viewvote"/>
	</c:when>
	<c:when test="${action=='votepoll'}">
		<jsp:forward page="/misc.do?actions=votepoll"/>
	</c:when>
	<c:when test="${action=='activityapplies'}">
		<jsp:forward page="/misc.do?actions=activityapplies"/>
	</c:when>
	<c:when test="${action=='activityapplylist'}">
		<jsp:forward page="/misc.do?actions=activityapplylist"/>
	</c:when>
	<c:when test="${action=='debatevote'}">
		<jsp:forward page="/misc.do?actions=debatevote"/>
	</c:when>
	<c:when test="${action=='viewpayments'}">
		<c:set var="jsprun_action" value="82" scope="request"/>
		<jsp:forward page="/misc.do?actions=viewpayments"/>
	</c:when>
	<c:when test="${action=='viewattachpayments'}">
		<c:set var="jsprun_action" value="82" scope="request"/>
		<jsp:forward page="/misc.do?actions=viewattachpayments"/>
	</c:when>
	<c:when test="${action=='pay'}">
		<c:set var="jsprun_action" value="81" scope="request"/>
		<jsp:forward page="/misc.do?actions=pay"/>
	</c:when>
	<c:when test="${action=='getonlines'}">
		<jsp:forward page="/misc.do?actions=getonlines"/>
	</c:when>
	<c:when test="${action=='bestanswer'}">
		<jsp:forward page="/misc.do?actions=bestanswer"/>
	</c:when>
	<c:when test="${action=='attachpay'}">
	<c:set var="jsprun_action" value="81" scope="request"/>
		<jsp:forward page="/misc.do?actions=attachpay"/>
	</c:when>
	<c:when test="${action=='debateumpire'}">
	<c:set var="jsprun_action" value="81" scope="request"/>
		<jsp:forward page="/misc.do?actions=debateumpire"/>
	</c:when>
	<c:when test="${action=='tradeorder'}">
		<jsp:forward page="/misc.do?actions=tradeorder"/>
	</c:when>
	<c:otherwise>
		<c:set var="propertyKey" value="true" scope="request" />
		<c:set var="errorInfo" value="undefined_action_return" scope="request"/>
		<jsp:forward page="templates/default/showmessage.jsp"/>
	</c:otherwise>
</c:choose>