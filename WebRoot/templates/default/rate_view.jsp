<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; ${navigation} &raquo; <bean:message key="rate_view"/></div>
<c:if test="${thread.price<=0 && post.message!=''}">
<div class="spaceborder" style="clear: both">
	<table width="100%" align="center">
	<tr class="header"><td colspan="2"><bean:message key="rate_view_post"/></td></tr>
	<tr class="altbg1">
	<td rowspan="2" valign="top" width="20%"><span class="bold">
	<c:choose><c:when test="${post.author!='' && post.anonymous<=0}"><a href="space.jsp?uid=${post.authorid}">${post.author}</a></c:when><c:otherwise><bean:message key="anonymous"/></c:otherwise></c:choose>
	</span><br /><br /></td>
	<td class="smalltxt"><jrun:showTime timeInt="${post.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td></tr>
	<tr class="altbg1"><td>
	<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed; word-wrap: break-word">
	<tr><td><span class="bold"><span class="smalltxt">${post.subject}</span></span><br /><br />${post.message}</td></tr></table></td></tr>
	</table></div><br />
</c:if>
<div class="mainbox">
	<h1><bean:message key="rate_view"/></h1>
	<table summary="<bean:message key="rate_view"/>" cellspacing="0" cellpadding="0">
		<thead>
			<tr>
				<td><bean:message key="credits"/></td>
				<td class="user"><bean:message key="username"/></td>
				<td class="time"><bean:message key="time"/></td>
				<td><bean:message key="reason"/></td>
			</tr>
		</thead>
		<c:forEach items="${rateloglist}" var="retelog">
		<tr>
			<td>${retelog.markValue}</td>
			<td class="user"><a href="space.jsp?uid=${retelog.uid}">${retelog.firstUsername}</a></td>
			<td class="time"><jrun:showTime timeInt="${retelog.operateTime}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
			<td>${retelog.reason}</td>
		</tr>
		</c:forEach>
	</table>
</div>
<jsp:include flush="true" page="footer.jsp" />