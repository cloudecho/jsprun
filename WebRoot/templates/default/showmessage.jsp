<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <bean:message key="board_message" /></div>
<div class="box message">
	<h1>${settings.bbname} <bean:message key="board_message" /></h1>
	<c:choose>
		<c:when test="${successInfo!=null}"><p><c:choose><c:when test="${propertyKey !=null && propertyKey}" ><bean:message key="${successInfo}" /></c:when><c:otherwise>${successInfo}</c:otherwise></c:choose></p><p><a href="${requestPath}"><bean:message key="message_redirect" /></a></p></c:when>
		<c:when test="${errorInfo!=null}"><p><c:choose><c:when test="${propertyKey !=null && propertyKey}" ><bean:message key="${errorInfo}" /></c:when><c:otherwise>${errorInfo}</c:otherwise></c:choose></p><p><a href="javascript:history.back()"><bean:message key="message_return" /></a></p></c:when>
		<c:when test="${resultInfo!=null}"><p><c:choose><c:when test="${propertyKey !=null && propertyKey}"><bean:message key="${resultInfo}" /></c:when><c:otherwise>${resultInfo}</c:otherwise></c:choose></p></c:when>
	</c:choose>
</div>
<jsp:include flush="true" page="footer.jsp?isRedirect=true" />