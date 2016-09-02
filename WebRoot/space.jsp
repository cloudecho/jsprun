<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include page="forumdata/cache/cache_ranks.jsp"/>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${jsprun_groupid}.jsp"/>
<c:if test="${jsprun_adminid>0}"><jsp:include page="forumdata/cache/admingroup_${jsprun_adminid}.jsp"/></c:if>
<c:set var="action" value="${param.action}" scope="request" />
<c:choose>
	<c:when test="${settings.spacestatus==0 && param.inajax!=1}"><jsp:forward page="/space.do?action=toUserinfo" /></c:when>
	<c:when test="${action=='viewpro'}">
		<c:set var="jsprun_action" value="61" scope="request"/>
		<jsp:forward page="/space.do?action=toUserinfo" />
	</c:when>
	<c:when test="${action=='mythreads'}"><jsp:forward page="/space.do?action=toMythreads" /></c:when>
	<c:when test="${action=='myblogs'}"><jsp:forward page="/space.do?action=toMyblogs" /></c:when>
	<c:when test="${action=='myreplies'}"><jsp:forward page="/space.do?action=toMyreplay" /></c:when>
	<c:when test="${action=='myfavforums'}"><jsp:forward page="/space.do?action=toMyfavforums" /></c:when>
	<c:when test="${action=='spaceinfo'}"><jsp:forward page="templates/default/spaceinfo.jsp" /></c:when>
	<c:when test="${action=='myrewards'}"><jsp:forward page="/space.do?action=toMyrewards" /></c:when>
	<c:when test="${action=='mytrades'}"><jsp:forward page="/space.do?action=toMytrades" /></c:when>
	<c:when test="${action=='myfavthreads'}"><jsp:forward page="/space.do?action=toMyfavthreads" /></c:when>
	<c:when test="${action=='editspacemodule'}"><jsp:forward page="/space.do?action=editSpacemodule" /></c:when>
	<c:when test="${param.inajax==1}"><jsp:forward page="/space.do?action=toCustominfo" /></c:when>
	<c:otherwise><jsp:forward page="/space.do?action=toSpace" /></c:otherwise>
</c:choose>