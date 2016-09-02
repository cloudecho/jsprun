<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h1><bean:message key="my"/></h1>
<div class="msgtabs"><strong><bean:message key="my_threads_last_five"/></strong></div>
<table cellspacing="0" cellpadding="0" width="100%">
	<thead><tr><td style="width: 50%"><bean:message key="subject"/></td><td class="time"><bean:message key="forum_name"/></td><td class="time"><bean:message key="a_post_threads_lastpost"/></td><td width="40"><bean:message key="a_system_check_status"/></td></tr></thead>
	<tbody>
		<c:forEach items="${threadlists}" var="threadlist"><tr>
			<td><c:if test="${threadlist.displayorder>=0}"><a href="viewthread.jsp?tid=${threadlist.tid}" target="_blank">${threadlist.subject}</a></c:if><c:if test="${threadlist.displayorder<0}">${threadlist.subject}</c:if></td>
			<td><a href="forumdisplay.jsp?fid=${threadlist.fid}" target="_blank">${threadlist.name}</a></td>
			<td><cite><a href="redirect.jsp?tid=${threadlist.tid}&goto=lastpost#lastpost">${threadlist.lastpost}</a><br />by <c:choose><c:when test="${threadlist.lastposter!=''}"><a href="space.jsp?username=${threadlist.lastposterenc}" target="_blank">${threadlist.lastposter}</a></c:when><c:otherwise><bean:message key="anonymous"/></c:otherwise></c:choose></cite></td>
			<td><c:choose><c:when test="${threadlist.displayorder==-1}"><bean:message key="forum_recyclebin"/></c:when><c:when test="${threadlist.displayorder==-2}"><bean:message key="my_threads_aduit"/></c:when><c:when test="${threadlist.closed == -1}"><bean:message key="closed"/></c:when><c:otherwise><bean:message key="my_threads_common"/></c:otherwise></c:choose></td>
		</tr></c:forEach>
		<c:if test="${empty threadlists}"><tr><td colspan="4"><bean:message key="forum_nothreads"/></td></tr></c:if>
	</tbody>
</table>
<div class="msgtabs"><strong><bean:message key="my_replys_last_five"/></strong></div>
<table cellspacing="0" cellpadding="0" width="100%">
	<thead><tr><td style="width: 50%"><bean:message key="a_post_attachments_thread"/></td><td class="time"><bean:message key="forum_name"/></td><td class="time"><bean:message key="a_post_threads_lastpost"/></td><td width="40"><bean:message key="a_system_check_status"/></td></tr></thead>
	<tbody>
		<c:forEach items="${postlists}" var="postlist"><tr>
			<td><c:if test="${postlist.invisible==0}"><a href="redirect.jsp?goto=findpost&pid=${postlist.pid}&ptid=${postlist.tid}" target="_blank"><c:if test="${postlist.subject!=''}">${postlist.subject}</c:if><c:if test="${postlist.subject==''}"><bean:message key="nosubject"/></c:if></a></c:if><c:if test="${postlist.invisible!=0}"><c:if test="${postlist.subject!=''}">${postlist.subject}</c:if><c:if test="${postlist.subject==''}"><bean:message key="nosubject"/></c:if></c:if></td>
			<td><a href="forumdisplay.jsp?fid=${postlist.fid}" target="_blank">${postlist.name}</a></td>
			<td><cite><a href="redirect.jsp?tid=${postlist.tid}&goto=lastpost#lastpost">${postlist.lastpost}</a><br />by <c:if test="${postlist.lastposter!=''}"><a href="space.jsp?username=${postlist.lastposterenc}" target="_blank">${postlist.lastposter}</a></c:if><c:if test="${postlist.lastposter==''}"><bean:message key="anonymous"/></c:if></cite></td>
			<td>
				<c:choose>
					<c:when test="${postlist.invisible==-1||postlist.invisible==-2}"><bean:message key="delete_wait"/></c:when>
					<c:otherwise><bean:message key="my_threads_common"/></c:otherwise>
				</c:choose>
			</td>
		</tr></c:forEach>
		<c:if test="${empty postlists}"><tr><td colspan="4"><bean:message key="forum_nothreads"/></td></tr></c:if>
	</tbody>
</table>