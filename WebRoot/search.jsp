<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/cache_forums.jsp"/>
<jsp:include page="forumdata/cache/cache_icons.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="jsprun_action" value="111" scope="request"/>
<c:set var="accessing" value="search" scope="request" />
<c:set var="action" value="${param.action}" />
<c:choose>
	<c:when test="${action=='list'}">
		<jsp:forward page="templates/default/memberlist.jsp" />
	</c:when>
	<c:when test="${param.srchtype=='threadtype'}">
		<jsp:forward page="/search.do?actions=toSearchByType" />
	</c:when>
	<c:when test="${param.action=='threadtype'}">
	<c:set var="searchd" value="${searchid!=null?(searchid): (param.searchid)}"/>
	<c:set var="typeid" value="${typeid!=null?(typeid): (param.typeid)}"/>
		<c:choose>
			<c:when test="${searchd!=null}">
				<jsp:forward page="/search.do?actions=searchbytype&searchid=${searchd}&typeid=${typeid}"/>
			</c:when>
			<c:otherwise>
				<jsp:forward page="/search.do?actions=searchbytype" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${param.action=='search' || param.searchsubmit=='yes'}">
	<c:set var="searchd" value="${searchid!=null?(searchid): (param.searchid)}"/>
	<c:set var="orderbys" value="${orderby!=null?(orderby): (param.orderby)}"/>
	<c:set var="ascdescs" value="${ascdesc!=null?(ascdesc): (param.ascdesc)}"/>
		<c:choose>
			<c:when test="${searchd!=null}">
				<jsp:forward page="/search.do?actions=search&searchid=${searchd}&orderby=${orderbys}&ascdesc=${ascdescs}" />
			</c:when>
			<c:otherwise>
				<jsp:forward page="/search.do?actions=search" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<jsp:forward page="/search.do?actions=toSearch" />
	</c:otherwise>
</c:choose>