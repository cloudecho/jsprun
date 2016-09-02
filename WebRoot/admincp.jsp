<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${jsprun_adminid>0&&user.secques==''&&settings.admincp_forcesecques>0}">
	<c:set var="message_key" value="secques_invalid" scope="request"/>
	<jsp:forward page="/admin/page/message.jsp"/>
</c:if>
<jsp:include page="forumdata/cache/usergroup_${jsprun_groupid}.jsp"/>
<c:choose>
	<c:when test="${param.action=='logout'}"><jsp:forward page="/admincp.do?adminaction=logout"/></c:when>
	<c:otherwise>
		<%request.setAttribute("extra",request.getQueryString()); %>
		<jsp:forward page="/admincp.do?adminaction=admincp"/>
	</c:otherwise>
</c:choose>