<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr>
		<td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_creditwizard" /></td>
	</tr>
</table>
<br />
<jsp:include page="lead.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" align="center">
	<tr class="header">
		<td colspan="4"><bean:message key="a_setting_step_menu_1" /></td>
	</tr>
	<tr class="category">
		<td><bean:message key="credits_id" /></td>
		<td><bean:message key="credits_title" /></td>
		<td><bean:message key="a_setting_status" /></td>
		<td><bean:message key="edit" /></td>
	</tr>
	<c:forEach begin="1" end="8" var="ext">
		<input type="hidden" name="extcreditid" value="${ext}" />
		<tr align="center">
			<td class="altbg1">extcredits${ext}</td>
			<td class="altbg2">${extcredits[ext].title}<c:if test="${settings.creditstrans==ext}"><bean:message key="a_setting_iscreditstrans" /></c:if></td>
			<td class="altbg1">
			<c:choose>
			<c:when test="${!empty extcredits[ext].available}">
			<bean:message key="enabled" />
			</c:when>
			<c:otherwise>
			<bean:message key="a_setting_unavailable" />
			</c:otherwise>
			</c:choose>
			</td>
			<td class="altbg2"><a href="admincp.jsp?action=settings&do=particular&extcreditid=${ext}">[<bean:message key="a_setting_detail" />]</a><a href="admincp.jsp?action=settings&do=resetCredit&extcreditid=${ext}&extcredits=extcredits${ext}">[<bean:message key="a_setting_resetusercredit" />]</a></td>
		</tr>
	</c:forEach>
</table>
<jsp:directive.include file="../../cp_footer.jsp" />