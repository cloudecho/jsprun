<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/cache_register.jsp"/>
<jsp:include page="forumdata/cache/cache_profilefields.jsp"/>
<jsp:include page="forumdata/cache/cache_faqs.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="jsprun_action" value="5" scope="request"/>
<c:set var="accessing" value="register" scope="request"/>
<c:choose>
	<c:when test="${param.regsubmit=='yes'}">
		<jsp:forward page="/register.do?action=register"/>
	</c:when>
	<c:otherwise>
		<jsp:forward page="/register.do?action=toRegister"/>
	</c:otherwise>
</c:choose>