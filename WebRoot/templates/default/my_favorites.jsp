<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<h1><bean:message key="my_favorites"/></h1>
<ul class="tabs headertabs"><li ${type== 'thread'?"class=current":""}><a href="my.jsp?item=favorites&type=thread${extrafid}"><bean:message key="my_favorite_threads"/></a></li><li ${type== 'forum'?"class=current":""}><a href="my.jsp?item=favorites&type=forum${extrafid}"><bean:message key="myfavforums"/></a></li></ul>
<form method="post" action="my.jsp?item=favorites&type=${type}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<c:choose><c:when test="${type=='thread'}">
		<table cellspacing="0" cellpadding="0" width="100%" align="center" summary="<bean:message key="my_favorite_threads"/>">
			<thead class="separation"><tr><td align="center" width="48"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form)"><bean:message key="del"/></td><td><bean:message key="subject"/></td><td><bean:message key="forum_name"/></td><td><bean:message key="threads_replies"/></td><td><bean:message key="a_post_threads_lastpost"/></td></tr></thead>
			<tbody>
				<c:forEach items="${favlists}" var="favlist"><tr>
					<td><input class="checkbox" type="checkbox" name="delete" value="${favlist.tid}" /></td>
					<td><a href="viewthread.jsp?tid=${favlist.tid}" target="_blank">${favlist.subject}</a></td>
					<td><a href="forumdisplay.jsp?fid=${favlist.fid}" target="_blank">${favlist.name}</a></td>
					<td>${favlist.replies}</td>
					<td><cite><a href="redirect.jsp?tid=${favlist.tid}&goto=lastpost#lastpost">${favlist.lastpost}</a><br/>by <c:choose><c:when test="${favlist.lastposter!=''}"><a href="space.jsp?username=${favlist.lastposterenc}" target="_blank">${favlist.lastposter}</a></c:when><c:otherwise><bean:message key="anonymous"/></c:otherwise></c:choose></cite></td>
				</tr></c:forEach>
				<c:if test="${empty favlists}"><tr><td colspan="5"><bean:message key="memcp_nofavs"/></td></tr></c:if>
			</tbody>
		</table>
	</c:when><c:when test="${type=='forum'}">
		<table cellspacing="0" cellpadding="0" width="100%" summary="<bean:message key="myfavforums"/>">
			<thead class="separation"><tr><td align="center" width="48"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form)"/><bean:message key="del"/></td><td><bean:message key="forum_name"/></td><td><bean:message key="thread"/></td><td><bean:message key="forum_posts"/></td><td><bean:message key="forum_todayposts"/></td></tr></thead>
			<tbody>
				<c:forEach items="${favlists}" var="favlist"><tr>
					<td><input class="checkbox" type="checkbox" name="delete" value="${favlist.fid}" /></td>
					<td><a href="forumdisplay.jsp?fid=${favlist.fid}" target="_blank">${favlist.name}</a></td>
					<td>${favlist.threads}</td><td>${favlist.posts}</td><td>${favlist.todayposts}</td>
				</tr></c:forEach>
				<c:if test="${empty favlists}"><tr><td colspan="5"><bean:message key="memcp_nofavs"/></td></tr></c:if>
			</tbody>
		</table>
	</c:when></c:choose>
	<p class="btns"><button type="submit" class="submit" name="favsubmit" value="true"><bean:message key="submitf"/></button></p>
</form>