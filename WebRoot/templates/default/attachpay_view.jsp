<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <bean:message key="pay_view"/></div>
<div class="mainbox">
	<h1><bean:message key="pay_view"/></h1>
	<table summary="<bean:message key="pay_view"/>" cellspacing="0" cellpadding="0">
		<thead>
			<tr>
				<td><bean:message key="username"/></td>
				<td><bean:message key="time"/></td>
				<td>${creditstrans.title}</td>
			</tr>
		</thead>
		<c:choose>
			<c:when test="${loglist!=null}">
				<c:forEach items="${loglist}" var="paylog">
					<tr>
					<td><a href="space.jsp?uid=${paylog.uid}">${paylog.username}</a></td>
					<td><jrun:showTime timeInt="${paylog.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
					<td>${paylog.amount}${creditstrans.unit}</td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td colspan="3"><bean:message key="attachment_buy_not"/></td></tr>
			</c:otherwise>
		</c:choose>
	</table>
</div>
<jsp:include flush="true" page="footer.jsp" />