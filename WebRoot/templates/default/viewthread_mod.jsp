<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<script src="include/javascript/viewthread.js" type="text/javascript"></script>
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; ${navigation} &raquo; <bean:message key="thread_mod"/></div>
<div class="mainbox">
	<h1><bean:message key="thread_mod"/></h1>
	<table summary="<bean:message key="thread_mod"/>" cellspacing="0" cellpadding="0">
		<thead>
			<tr>
				<td><bean:message key="operator"/></td>
				<td class="time"><bean:message key="time"/></td>
				<td><bean:message key="operation"/></td>
				<td class="time"><bean:message key="validity"/></td>
			</tr>
		</thead>
		<c:forEach items="${modlist}" var="mods">
			<tr>
				<td><c:choose><c:when test="${mods.uid!=''}"><a href="space.jsp?uid=${mods.uid}" target="_blank">${mods.username}</a></c:when><c:otherwise><bean:message key="thread_moderations_cron"/></c:otherwise></c:choose></td>
				<td class="time"><jrun:showTime timeInt="${mods.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
				<td <c:if test="${mods.status==0}">style="text-decoration: line-through" disabled</c:if>> ${mods.action} </td>
				<td class="time" <c:if test="${mods.status==0}">style="text-decoration: line-through" disabled</c:if>><c:choose><c:when test="${mods.expiration!=''}"><jrun:showTime timeInt="${mods.expiration}" type="${dateformat}" timeoffset="${timeoffset}"/></c:when><c:when test="${mods.action=='STK'||mods.action=='HLT'||mods.action=='DIG'||mods.action=='CLS'||mods.action=='OPN'}"><bean:message key="thread_moderations_expiration_unlimit"/></c:when></c:choose></td>
			</tr>
		</c:forEach>
	</table>
</div>
<jsp:include flush="true" page="footer.jsp" />