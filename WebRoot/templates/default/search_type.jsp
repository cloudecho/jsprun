<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp"/>
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="search_info"/></div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<div class="mainbox threadlist">
<h1><bean:message key="search_info"/></h1>
<table cellspacing="0" cellpadding="0" width="100%">
<thead>
<tr>
<td><bean:message key="subject"/></td>
<c:forEach items="${optionlist}" var="var">
	<td style="width: 10%">${var}</td>
</c:forEach>
<td style="width: 15%"><bean:message key="a_forum_edit_starttime"/></td>
</tr>
</thead>
<tbody>
<c:choose>
	<c:when test="${!empty resultlist}">
		<c:forEach items="${resultlist}" var="value">
			<tr style="height: 30px">
			<td><a href="viewthread.jsp?tid=${value.tid}" target="_blank">${value.subject}</a></td>
			<c:forEach items="${value.option}" var="var">
				<td style="width: 10%"><c:if test="${!empty var}">${var}</c:if>&nbsp;</td>
			</c:forEach>
			<td style="width: 15%">${value.dateline}</td>
		</tr>
		</c:forEach>
	</c:when>
	<c:otherwise><tr><td colspan="${colspan}"><bean:message key="search_nomatch"/></td></tr></c:otherwise>
</c:choose>
</tbody>
</table>
</div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<jsp:include flush="true" page="footer.jsp" />