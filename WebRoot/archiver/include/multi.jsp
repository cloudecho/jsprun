<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<bean:message key="page" />:
<c:forEach begin="${valueObject.multi_inc.pageStart}" end="${valueObject.multi_inc.pageEnd}" var="pageNum">
	<c:choose>
		<c:when test="${pageNum==valueObject.multi_inc.nowPage}"><strong>[${pageNum}]</strong></c:when>
		<c:otherwise><a href="archiver/${valueObject.multi_inc.link}-page-${pageNum}.html">${pageNum}</a></c:otherwise>
	</c:choose>
</c:forEach>