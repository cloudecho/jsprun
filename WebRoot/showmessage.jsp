<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${param.action=='nopermission'}"><jsp:forward page="templates/default/nopermission.jsp"/></c:when>
	<c:otherwise><jsp:forward page="templates/default/showmessage.jsp"/></c:otherwise>
</c:choose>