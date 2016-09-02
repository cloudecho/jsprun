<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/cache_faqs.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="jsprun_action" value="7" scope="request"/>
<c:set var="accessing" value="memcp" scope="request" />
<c:set var="memcpaction" value="${param.action!=null?(param.action):'memcp'}" scope="request" />
<c:choose>
	<c:when test="${jsprun_uid==0}">
		<c:set var="resultInfo" value="not_loggedin" scope="request"/>
		<c:set var="propertyKey" value="true" scope="request"/>
		<jsp:forward page="templates/default/showmessage.jsp"/>
	</c:when>
	<c:when test="${memcpaction=='profile'}">
		<c:set var="typeid" value="${param.typeid!=null?(param.typeid):'2'}" scope="request"/>
		<c:choose>
			<c:when test="${param.submit=='yes'}"><jsp:forward page="/ctrl.do?actions=editInfo&typeid=${typeid}"/></c:when>
			<c:otherwise><jsp:forward page="/ctrl.do?actions=toeditInfo" /></c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${memcpaction=='credits'}"><jsp:forward page="/ctrl.do?actions=credits" /></c:when>
	<c:when test="${memcpaction=='creditslog'}"><jsp:forward page="/ctrl.do?actions=creditslog" /></c:when>
	<c:when test="${memcpaction=='usergroups'}">
		<c:choose>
			<c:when test="${param.type!=null}"><jsp:forward page="/ctrl.do?actions=editUsergroupbytype" /></c:when>
			<c:otherwise><jsp:forward page="/ctrl.do?actions=tousergroup" /></c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${memcpaction=='spacemodule'}"><jsp:forward page="/space.do?action=toSpacemodule" /></c:when>
	<c:otherwise><jsp:forward page="/ctrl.do?actions=toControlhome" /></c:otherwise>
</c:choose>