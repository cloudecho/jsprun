<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h1><bean:message key="my_poll"/></h1>
<ul class="tabs headertabs"><li ${type== 'poll'?"class=current":""}><a href="my.jsp?item=polls&type=poll${extrafid}"><bean:message key="my_poll_orig"/></a></li><li ${type== 'join'?"class=current":""}><a href="my.jsp?item=polls&type=join${extrafid}"><bean:message key="my_poll_apply"/></a></li></ul>
<table cellspacing="0" cellpadding="0" width="100%">
	<thead><tr><td><bean:message key="a_post_attachments_thread"/></td><td><bean:message key="forum_name"/></td><td>
		<c:choose>
			<c:when test="${type=='poll'}"><bean:message key="a_post_threads_lastpost"/></c:when>
			<c:otherwise><bean:message key="poll_dateline"/></c:otherwise>
		</c:choose>
	</td><td><bean:message key="a_system_check_status"/></td></tr></thead>
	<tbody>
		<c:forEach items="${polllists}" var="polllist"><tr>
			<td><a href="viewthread.jsp?tid=${polllist.tid}" target="_blank">${polllist.subject}</a></td>
			<td><a href="forumdisplay.jsp?fid=${polllist.fid}" target="_blank">${polllist.name}</a></td>
			<td><c:choose><c:when test="${type=='poll'}"><cite><a href="redirect.jsp?tid=${polllist.tid}&goto=lastpost#lastpost" target="_blank">${polllist.lastpost}</a><br />by <c:choose><c:when test="${polllist.lastposter!=''}"><a href="space.jsp?username=${polllist.lastposterenc}" target="_blank">${polllist.lastposter}</a></c:when><c:otherwise><bean:message key="anonymous"/></c:otherwise></c:choose></cite></c:when><c:when test="${type=='join'}"><cite>${polllist.dateline}</cite></c:when></c:choose></td>
			<td><c:choose><c:when test="${polllist.displayorder==-1}"><bean:message key="forum_recyclebin"/></c:when><c:when test="${polllist.displayorder==-2}"><bean:message key="my_threads_aduit"/></c:when><c:when test="${polllist.closed == -1}"><bean:message key="closed"/></c:when><c:otherwise><bean:message key="my_threads_common"/></c:otherwise></c:choose></td>
		</tr></c:forEach>
		<c:if test="${empty polllists}"><tr><td colspan="4"><bean:message key="forum_nothreads"/></td></tr></c:if>
	</tbody>
</table>