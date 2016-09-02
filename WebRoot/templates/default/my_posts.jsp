<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h1><bean:message key="show_mytopics"/></h1>
<ul class="tabs headertabs"><li><a href="my.jsp?item=threads${extrafid}"><bean:message key="my_threads"/></a></li><li class="current"><a href="my.jsp?item=posts${extrafid}"><bean:message key="my_replies"/></a></li></ul>
<table cellspacing="0" cellpadding="0" width="100%" summary="<bean:message key="my_replies"/>">
	<thead><tr><td><bean:message key="a_post_attachments_thread"/></td><td><bean:message key="forum_name"/></td><td><bean:message key="a_post_threads_lastpost"/></td><td><bean:message key="a_system_check_status"/></td></tr></thead>
	<tbody>
		<c:forEach items="${postlists}" var="postlist"><tr>
			<td><a href="redirect.jsp?goto=findpost&pid=${postlist.pid}&ptid=${postlist.tid}" target="_blank">${postlist.subject}</a></td>
			<td><a href="forumdisplay.jsp?fid=${postlist.fid}" target="_blank">${postlist.name}</a></td>
			<td><cite><a href="redirect.jsp?tid=${postlist.tid}&goto=lastpost#lastpost">${postlist.lastpost}</a><br /> by <c:if test="${postlist.lastposter!=''}"><a href="space.jsp?username=${postlist.lastposterenc}" target="_blank">${postlist.lastposter}</a></c:if><c:if test="${postlist.lastposter==''}"><bean:message key="anonymous"/></c:if></cite></td>
			<td>
				<c:choose>
					<c:when test="${postlist.invisible==-1||postlist.invisible==-2}"><bean:message key="forum_recyclebin"/></c:when>
					<c:otherwise><bean:message key="my_threads_common"/></c:otherwise>
				</c:choose>
			</td>
		</tr></c:forEach>
		<c:if test="${empty postlists}"><tr><td colspan="4"><bean:message key="forum_nothreads"/></td></tr></c:if>
	</tbody>
</table>