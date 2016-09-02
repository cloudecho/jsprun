<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${param.action=='tradenotify'}"><jsp:forward page="/notify.do?action=tradenotify" /></c:when>
	<c:when test="${param.action=='ordernotify'}"><jsp:forward page="/notify.do?action=ordernotify" /></c:when>
	<c:otherwise>Access Denied</c:otherwise>
</c:choose>