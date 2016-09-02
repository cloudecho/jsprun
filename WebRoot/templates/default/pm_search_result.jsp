<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<form method="post" action="pms.do?actions=deletepms">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox">
		<h1><bean:message key="pm_search"/></h1>
		<jsp:include flush="true" page="pm_navbar.jsp" />
		<table summary="lang pm_search" cellspacing="0" cellpadding="0">
			<thead><tr><td class="selector">&nbsp;</td><td><bean:message key="subject"/></td><td><bean:message key="pm_folders"/></td><td><bean:message key="location_from"/></td><td><bean:message key="to"/></td><td><bean:message key="time"/></td></tr></thead>
			<c:choose>
				<c:when test="${pmslist!=null}">
					<c:forEach items="${pmslist}" var="pms">
						<tr>
							<td class="selector"><input type="checkbox" name="delete[]" value="${pms.pmid}"></td>
							<td><a href="pm.jsp?action=view&folder=${param.srchfolder}&pmid=${pms.pmid}&highlight=${index.keywords}" target="_blank">${pms.subject}</a></td>
							<td><c:choose><c:when test="${pms.folder=='outbox'}"><bean:message key="pm_outbox"/></c:when><c:when test="${jsprun_uid == pms.msgtoid}"><bean:message key="pm_inbox"/></c:when><c:otherwise><bean:message key="a_system_invite_status_3"/></c:otherwise></c:choose></td>
							<td><c:choose><c:when test="${pms.msgfromid>0}"><a href="space.jsp?uid=${pms.msgfromid}">${pms.msgfrom}</a></c:when><c:otherwise><bean:message key="pm_systemmessage"/></c:otherwise></c:choose></td>
							<td><a href="space.jsp?uid=${pms.msgtoid}">${pms.username}</a></td>
							<td><jrun:showTime timeInt="${pms.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise><tr><td colspan="6"><bean:message key="search_nomatch"/></td></tr></c:otherwise>
			</c:choose>
		</table>
		<c:if test="${pmslist!=null}">
			<div class="footoperation">
				<label><input type="checkbox" id="chkall" name="chkall" onclick="checkall(this.form)" /> <bean:message key="select_all"/></label>
				<button type="submit" name="deletesubmit" value="true"><bean:message key="delete"/></button>
			</div>
		</c:if>
		<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
	</div>
</form>