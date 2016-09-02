<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<h1><bean:message key="my_subscriptions"/></h1>
<ul class="tabs headertabs"><li class="current"><a href="my.jsp?item=subscriptions&type=forum${extrafid}" class="current"><bean:message key="my_subscription_threads"/></a></li></ul>
<form method="post" action="my.jsp?item=subscriptions">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table cellspacing="0" cellpadding="0" width="100%" summary="<bean:message key="my_subscription_threads"/>">
		<thead><tr><td width="48"><input type="checkbox" name="chkall" class="header checkbox" onclick="checkall(this.form)"/><bean:message key="del"/></td><td><bean:message key="subject"/></td><td><bean:message key="forum_name"/></td><td><bean:message key="threads_replies"/></td><td><bean:message key="a_post_threads_lastpost"/></td></tr></thead>
		<tbody>
			<c:forEach items="${sublists}" var="sublist"><tr>
				<td><input class="checkbox" type="checkbox" name="delete" value="${sublist.tid}" /></td>
				<td><a href="viewthread.jsp?tid=${sublist.tid}" target="_blank">${sublist.subject}</a></td>
				<td><a href="forumdisplay.jsp?fid=${sublist.fid}" target="_blank">${sublist.name}</a></td>
				<td>${sublist.replies}</td>
				<td><cite><a href="redirect.jsp?tid=${sublist.tid}&goto=lastpost#lastpost">${sublist.lastpost}</a><br/>by <c:choose><c:when test="${sublist.lastposter!=''}"><a href="space.jsp?username=${sublist.lastposterenc}" target="_blank">${sublist.lastposter}</a></c:when><c:otherwise><bean:message key="anonymous"/></c:otherwise></c:choose></cite></td>
			</tr></c:forEach>
			<c:if test="${empty sublists}"><tr><td colspan="5"><bean:message key="memcp_nosubs"/></td></tr></c:if>
		</tbody>
	</table>
	<p class="btns"><button type="submit" class="submit" name="subsubmit" value="true"><bean:message key="submitf"/></button></p>
</form>