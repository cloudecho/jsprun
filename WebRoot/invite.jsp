<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${jsprun_groupid}.jsp"/>
<c:set var="accessing" value="invite" scope="request" />
<c:set var="inviteAction" value="${param.action!=null?(param.action):'availablelog'}" scope="request"/>
<c:choose>
	<c:when test="${settings.regstatus<1}">
		<c:set var="propertyKey" value="true" scope="request" />
		<c:set var="errorInfo" value="invite_close" scope="request"/>
		<jsp:forward page="showmessage.jsp"/>
	</c:when>
	<c:when test="${jsprun_uid==0||usergroups.allowinvite==0}">
		<c:set value="${usergroups.grouptitle}" var="arg0_forKey" scope="request"/>
		<c:set var="show_message" value="group_nopermission" scope="request"/>
		<c:set var="propertyKey" value="true" scope="request" />
		<jsp:forward page="/templates/default/nopermission.jsp" />
	</c:when>
	<c:when test="${inviteAction=='buyinvite'}"><jsp:forward page="/invite.do?actions=buyinvite" /></c:when>
	<c:when test="${inviteAction=='markinvite'}"><jsp:forward page="/invite.do?actions=markinvite" /></c:when>
	<c:when test="${inviteAction=='sendinvite'}"><jsp:forward page="/invite.do?actions=sendinvite" /></c:when>
	<c:otherwise>
		<c:set var="operation" value="${param.operation!=null?(param.operation):'availablelog'}" scope="request"/>
		<jsp:forward page="/invite.do?actions=availablelog" />
	</c:otherwise>
</c:choose>