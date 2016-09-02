<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h1><bean:message key="show_mytopics"/></h1>
<ul class="tabs headertabs"><li class="current"><a href="my.jsp?item=threads${extrafid}"><bean:message key="my_threads"/></a></li><li><a href="my.jsp?item=posts${extrafid}"><bean:message key="my_replies"/></a></li></ul>
<table cellspacing="0" cellpadding="0" width="100%" summary="<bean:message key="my_threads"/>">
	<thead><tr><td style="width: 40%"><bean:message key="subject"/></td><td><bean:message key="forum_name"/></td><td><bean:message key="a_post_threads_lastpost"/></td><td><bean:message key="a_system_check_status"/></td></tr></thead>
	<tbody>
		<c:forEach items="${threadlists}" var="threadlist"><tr>
			<td><c:choose><c:when test="${threadlist.displayorder>=0}"><a href="viewthread.jsp?tid=${threadlist.tid}" target="_blank">${threadlist.subject}</a></c:when><c:otherwise>${threadlist.subject}</c:otherwise></c:choose></td>
			<td><a href="forumdisplay.jsp?fid=${threadlist.fid}" target="_blank">${threadlist.name}</a></td>
			<td><cite><a href="redirect.jsp?tid=${threadlist.tid}&goto=lastpost#lastpost">${threadlist.lastpost}</a><br />by <c:choose><c:when test="${threadlist.lastposter!=''}"><a href="space.jsp?username=${threadlist.lastposterenc}" target="_blank">${threadlist.lastposter}</a></c:when><c:otherwise><bean:message key="anonymous"/></c:otherwise></c:choose></cite></td>
			<td><c:choose><c:when test="${threadlist.displayorder==-1}"><bean:message key="forum_recyclebin"/></c:when><c:when test="${threadlist.displayorder==-2}"><bean:message key="my_threads_aduit"/></c:when><c:when test="${threadlist.closed == -1}"><bean:message key="closed"/></c:when><c:otherwise><bean:message key="my_threads_common"/></c:otherwise></c:choose></td>
		</tr></c:forEach>
		<c:if test="${empty threadlists}"><tr><td colspan="4"><bean:message key="forum_nothreads"/></td></tr></c:if>
	</tbody>
</table>