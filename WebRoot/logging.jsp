<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${jsprun_groupid}.jsp"/>
<c:set var="jsprun_action" value="6" scope="request"/>
<c:set var="accessing" value="logging" scope="request"/>
<c:set var="action" value="${param.action}" />
<c:choose>
	<c:when test="${action=='logout'&&param.formhash!=null}">
		<jsp:forward page="/logging.do?action=logout" />
	</c:when>
	<c:when test="${action=='login'&& param.loginsubmit==null}">
		<jsp:forward page="/logging.do?action=toLogin" />
	</c:when>
	<c:when test="${action=='login'&& param.loginsubmit!=null}">
		<jsp:forward page="/logging.do?action=login" />
	</c:when>
	<c:otherwise>
		<c:set var="propertyKey" value="true" scope="request" />
		<c:set var="errorInfo" value="undefined_action_return" scope="request"/>
		<jsp:forward page="templates/default/showmessage.jsp"/>
	</c:otherwise>
</c:choose>