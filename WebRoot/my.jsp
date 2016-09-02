<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="jsprun_action" value="8" scope="request"/>
<c:set var="accessing" value="my" scope="request" />
<c:set var="myitem" value="${param.item}" scope="request" />
<c:set var="type" value="${param.type}" scope="request" />
<c:choose>
	<c:when test="${jsprun_uid==0}"><c:set var="propertyKey" value="true" scope="request" /><c:set var="show_message" value="not_loggedin" scope="request"/><jsp:forward page="/templates/default/nopermission.jsp" /></c:when>
	<c:when test="${myitem=='threads'}"><jsp:forward page="/my.do?item=toMyThreads" /></c:when>
	<c:when test="${myitem=='posts'}"><jsp:forward page="/my.do?item=toMyPosts" /></c:when>
	<c:when test="${myitem=='favorites'}"><jsp:forward page="/my.do?item=toMyFavorites" /></c:when>
	<c:when test="${myitem=='subscriptions'}"><jsp:forward page="/my.do?item=toMySubscriptions" /></c:when>
	<c:when test="${myitem=='grouppermission'}"><jsp:forward page="/my.do?item=toMyGrouppermission" /></c:when>
	<c:when test="${myitem=='polls'}"><jsp:forward page="/my.do?item=toMyPolls" /></c:when>
	<c:when test="${myitem=='tradestats'}"><jsp:forward page="/my.do?item=toMyTradestats" /></c:when>
	<c:when test="${myitem=='tradethreads'}"><jsp:forward page="/my.do?item=toMyTradethreads" /></c:when>
	<c:when test="${myitem=='selltrades'||myitem=='buytrades'}"><jsp:forward page="/my.do?item=toMyTrades" /></c:when>
	<c:when test="${myitem=='reward'}"><jsp:forward page="/my.do?item=toMyReward" /></c:when>
	<c:when test="${myitem=='activities'}"><jsp:forward page="/my.do?item=toMyActivities" /></c:when>
	<c:when test="${myitem=='debate'}"><jsp:forward page="/my.do?item=toMyDebate" /></c:when>
	<c:when test="${settings.videoopen>0&&myitem=='video'}"><jsp:forward page="/my.do?item=toMyVideo" /></c:when>
	<c:when test="${myitem=='buddylist'}"><jsp:forward page="/my.do?item=toMyBuddylist" /></c:when>
	<c:when test="${myitem=='promotion'}"><jsp:forward page="/my.do?item=toMyPromotion" /></c:when>
	<c:otherwise><jsp:forward page="/my.do?item=toMyIndex" /></c:otherwise>
</c:choose>