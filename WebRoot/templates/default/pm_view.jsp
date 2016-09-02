<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<c:set var="accessing" value="pm" scope="request"/>
<div class="mainbox viewthread">
	<h1>${pmsd.subject}</h1>
	<table summary="Read PM" cellspacing="0" cellpadding="0">
		<tr>
			<td class="postcontent">
				<p class="postinfo">
					<bean:message key="time"/>:<jrun:showTime timeInt="${pmsd.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>,&nbsp;
					<bean:message key="location_from"/>:<c:choose><c:when test="${pmsd.msgfromid>0}"><a href="space.jsp?uid=${pmsd.msgfromid}">${pmsd.msgfrom}</a></c:when><c:otherwise><bean:message key="pm_systemmessage"/></c:otherwise></c:choose>,&nbsp;
					<bean:message key="to"/>:<a href="space.jsp?uid=${pmsd.msgtoid}">${member.username}</a>
				</p>
				<div class="postmessage">${message}</div>
				<p class="postactions">
					<a href="###" onclick="history.go(-1);"><bean:message key="return"/></a>
					<c:if test="${(param.folder=='inbox'||pmsd.folder=='inbox')&&pmsd.msgfromid>0}">- <a href="pm.jsp?action=send&pmid=${pmsd.pmid}&do=reply"><bean:message key="threads_replies"/></a></c:if>
					 - <a href="pm.jsp?action=send&pmid=${pmsd.pmid}&do=forward"><bean:message key="forward"/></a>
					<c:if test="${param.folder=='inbox'||pmsd.folder=='inbox'}"> - <a href="pm.jsp?action=markunread&pmid=${pmsd.pmid}" id="ajax_markunread_${pmsd.pmid}" onclick="ajaxmenu(event, this.id)"><bean:message key="pm_mark_unread"/></a></c:if>
					 - <a href="pm.jsp?action=archive&pmid=${pmsd.pmid}"><bean:message key="download"/></a>
					- <a href="pm.jsp?action=delete&pmid=${pmsd.pmid}&folder=${param.folder}&formHash=${jrun:formHash(pageContext.request)}&deletesubmit=yes"><bean:message key="delete"/></a>
				</p>
			</td>
		</tr>
	</table>
</div>