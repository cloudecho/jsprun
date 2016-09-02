<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h1><bean:message key="my_debate"/></h1>
<ul class="tabs headertabs"><li ${type== 'orig'?"class=current":""}><a href="my.jsp?item=debate&type=orig${extrafid}"><bean:message key="my_debate_orig"/></a></li><li ${type== 'apply'?"class=current":""}><a href="my.jsp?item=debate&type=apply${extrafid}"><bean:message key="my_debate_apply"/></a></li></ul>
<table cellspacing="0" cellpadding="0" width="100%">
	<thead><tr><td><bean:message key="a_post_attachments_thread"/></td><td><bean:message key="forum_name"/></td>
	<td class="time">
		<c:choose>
			<c:when test="${type=='orig'}"><bean:message key="a_post_threads_lastpost"/></c:when>
			<c:otherwise><bean:message key="debate_dateline"/></c:otherwise>
		</c:choose>
	</td><td><bean:message key="a_system_check_status"/></td></tr></thead>
	<tbody>
		<c:forEach items="${debatelists}" var="debatelist"><tr>
			<td><a href="viewthread.jsp?tid=${debatelist.tid}" target="_blank">${debatelist.subject}</a></td>
			<td><a href="forumdisplay.jsp?fid=${debatelist.fid}" target="_blank">${debatelist.name}</a></td>
			<td><em><c:choose><c:when test="${type=='orig'}"> <a href="redirect.jsp?tid=${debatelist.tid}&goto=lastpost#lastpost" target="_blank">${debatelist.lastpost}</a><br />by	<c:choose><c:when test="${debatelist.lastposter!=''}"><a href="space.jsp?username=${debatelist.lastposterenc}" target="_blank">${debatelist.lastposter}</a></c:when><c:otherwise><bean:message key="anonymous"/></c:otherwise></c:choose></c:when><c:otherwise>${debatelist.dateline}</c:otherwise></c:choose></em></td>
			<td><c:choose><c:when test="${debatelist.displayorder==-1}"><bean:message key="forum_recyclebin"/></c:when><c:when test="${debatelist.displayorder==-2}"><bean:message key="my_threads_aduit"/></c:when><c:when test="${debatelist.closed == -1}"><bean:message key="closed"/></c:when><c:otherwise><bean:message key="my_threads_common"/></c:otherwise></c:choose></td>
		</tr></c:forEach>
		<c:if test="${empty debatelists}"><tr><td colspan="4"><bean:message key="forum_nothreads"/></td></tr></c:if>
	</tbody>
</table>