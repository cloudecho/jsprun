<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<c:set scope="page" value="${param.action}" var="inviteAction"></c:set>
<c:choose>
	<c:when test="${inviteAction=='markinvite'}">
		<tr>
			<td><input type="text" value="${invite.invitecode}" readonly="readonly" onclick="this.select();setcopy('${invite.invitecode}', '<bean:message key="invite_copy_code_success" arg0="${invite.invitecode}"/>')" style="font-weight:${changestatus==1?'bold':'normal'}" title="<bean:message key="copy"/>" /></td>
			<td class="time"><jrun:showTime timeInt="${invite.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
			<td class="time"><jrun:showTime timeInt="${invite.expiration}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
			<td>
			<c:choose>
				<c:when test="${changestatus==1}"><a href="###" onclick="setcopy('${boardurl}${settings.regname}?invitecode=${invite.invitecode}', '<bean:message key="invite_copy_link_success" arg0="${boardurl}" arg1="${settings.regname}" arg2="${invite.invitecode}"/>')"><bean:message key="copy"/></a> | <c:if test="${usergroups.allowmailinvite>0}"><a href="invite.jsp?action=sendinvite&invitecode=${invite.invitecode}"><bean:message key="send"/></a> |</c:if> <a href="invite.jsp?action=markinvite&invitecode=${invite.invitecode}" id="ajax_markinvite_${invite.invitecode}" onclick="ajaxget(this.href, 'invite_${invite.invitecode}', 'ajaxwaitid');doane(event);"><bean:message key="invite_mark"/></a></c:when>
				<c:otherwise><a href="invite.jsp?action=markinvite&invitecode=${invite.invitecode}&do=undo" id="ajax_markinvite_${invite.invitecode}" onclick="ajaxget(this.href, 'invite_${invite.invitecode}', 'ajaxwaitid');doane(event);"><bean:message key="invite_unmark"/></a></c:otherwise>
			</c:choose>
			</td>
		</tr>
	</c:when>
	<c:otherwise>
		<div id="nav">
			<a href="${settings.indexname}">${settings.bbname}</a>&raquo;
			<c:choose>
				<c:when test="${empty inviteAction}"><bean:message key="invite"/></c:when>
				<c:otherwise><a href="invite.jsp"><bean:message key="invite"/></a> &raquo; <c:choose><c:when test="${inviteAction=='buyinvite'}"><bean:message key="invite_get"/></c:when><c:when test="${inviteAction=='sendinvite'}"><bean:message key="invite_send"/></c:when><c:when test="${inviteAction=='availablelog' && operation=='availablelog'}"><bean:message key="invite_available"/></c:when><c:when test="${inviteAction=='availablelog' && operation=='invalidlog'}"><bean:message key="invite_invalid_2"/></c:when><c:when test="${inviteAction=='availablelog' && operation=='usedlog'}"><bean:message key="invite_used"/></c:when></c:choose></c:otherwise>
			</c:choose>
		</div>
		<div class="container">
			<div class="content">
				<form method="post" action="invite.jsp?action=availablelog&operation=${operation}">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<div class="mainbox invitecodelist">
					<h1><bean:message key="invite_logs"/></h1>
					<ul class="tabs headertabs">
						<li ${operation== 'availablelog'?"class=current":""}><a href="invite.jsp?action=availablelog&operation=availablelog"><bean:message key="invite_available"/></a></li>
						<li ${operation== 'invalidlog'?"class=current":""}><a href="invite.jsp?action=availablelog&operation=invalidlog"><bean:message key="invite_invalid_2"/></a></li>
						<li ${operation== 'usedlog'?"class=current":""}><a href="invite.jsp?action=availablelog&operation=usedlog"><bean:message key="invite_used"/></a></li>
					</ul>
					<table cellspacing="0" cellpadding="0" summary="<bean:message key="invite_logs"/>">
						<c:choose>
							<c:when test="${operation=='availablelog' || operation=='invalidlog'}">
								<thead><tr><td><bean:message key="a_system_invite_code"/></td><td class="time"><bean:message key="invite_buy_dateline"/></td><td class="time"><bean:message key="invite_expiration"/></td><c:if test="${operation=='availablelog'}"><td><bean:message key="operation"/></td></c:if></tr></thead>
								<c:forEach items="${inviteslogs}" var="invite">
									<tbody id="invite_${invite.invitecode}">
										<tr>
											<td><input type="text" value="${invite.invitecode}" readonly="readonly" onclick="this.select();setcopy('${invite.invitecode}', '<bean:message key="invite_copy_code_success" arg0="${invite.invitecode}" />')" style="font-weight:${invite.status==1?'bold':'normal'}" title="<bean:message key="copy"/>" /></td>
											<td class="time"><jrun:showTime timeInt="${invite.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td> 
											<td class="time"><jrun:showTime timeInt="${invite.expiration}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
											<c:if test="${operation=='availablelog'}">
												<td>
													<a href="###" onclick="setcopy('${boardurl}${settings.regname}?invitecode=${invite.invitecode}', '<bean:message key="invite_copy_link_success" arg0="${boardurl}" arg1="${settings.regname}" arg2="${invite.invitecode}"/>')"><bean:message key="copy"/></a> |
													<c:choose>
														<c:when test="${invite.status=='1'}"><c:if test="${usergroups.allowmailinvite==1}"><a href="invite.jsp?action=sendinvite&invitecode=${invite.invitecode}"><bean:message key="send"/></a> |</c:if> <a href="invite.jsp?action=markinvite&invitecode=${invite.invitecode}" id="ajax_markinvite_${invite.invitecode}" onclick="ajaxget(this.href, 'invite_${invite.invitecode}', 'ajaxwaitid');doane(event);"><bean:message key="invite_mark"/></a></c:when>
														<c:otherwise><a href="invite.jsp?action=markinvite&invitecode=${invite.invitecode}&do=undo" id="ajax_markinvite_${invite.invitecode}" onclick="ajaxget(this.href, 'invite_${invite.invitecode}', 'ajaxwaitid');doane(event);"><bean:message key="invite_unmark"/></a></c:otherwise>
													</c:choose>
												</td>
											</c:if>
										</tr>
									</tbody>
								</c:forEach>
								<c:if test="${inviteslogs==null}"><tr><td colspan="4"><bean:message key="magics_log_nonexistence"/><c:if test="${operation=='availablelog'}"><a href="invite.jsp?action=buyinvite"><bean:message key="invite_get_code"/></a></c:if></td></tr></c:if>
							</c:when>
							<c:when test="${operation=='usedlog'}">
								<thead><tr><td><bean:message key="invite_regsiter"/></td><td class="time"><bean:message key="invite_buy_dateline"/></td><td class="time"><bean:message key="a_system_invite_code"/></td></tr></thead>
								<tbody>
									<c:forEach items="${inviteslogs}" var="invite"><tr><td><a href="space.jsp?uid=${invite.uid}" target="_blank">${invite.username}</a></td><td class="time"><jrun:showTime timeInt="${invite.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td><td class="time">${invite.invitecode}</td></tr></c:forEach>
									<c:if test="${inviteslogs==null}"><tr><td colspan="3"><bean:message key="magics_log_nonexistence"/></td></tr></c:if>
								</tbody>
								</c:when>
							</c:choose>
						</table>
					</div>
					<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
				</form>
			</div>
			<div class="side"><jsp:include flush="true" page="personal_navbar.jsp"></jsp:include></div>
		</div>
	</c:otherwise>
</c:choose>
<jsp:include flush="true" page="footer.jsp" />