<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<c:choose>
	<c:when test="${param.inajax==1}">
		<bean:message key="admin_getip_this"/> <b>${ip}</b> ${address}
		<c:if test="${banip==1}">
		<br />
		<a href="admincp.jsp?action=ipban&ip=${ip}&frames=yes" target="_blank">[ <bean:message key="admin_ban_this_ip"/> ]</a> &nbsp;
		</c:if>
		<a href="###close" onclick="hideMenu()">[ <bean:message key="closed"/> ]</a>
	</c:when>
	<c:otherwise>
	<jsp:include flush="true" page="header.jsp" />
		<div id="nav">
			<a href="${settings.indexname}">${settings.bbname}</a> &raquo; ${navigation} &raquo; <bean:message key="admin_getip"/>
		</div>
		<div class="mainbox formbox">
			<h1> <bean:message key="admin_getip"/> </h1>
			<table cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<bean:message key="admin_getip_this"/>
						<b>${ip}</b> ${address}
					</td>
				<tr>
					<td>
						<c:if test="${banip==1}">
						<a href="admincp.jsp?action=ipban&ip=${ip}&frames=yes" target="_blank">[ <bean:message key="admin_ban_this_ip"/> ]</a> &nbsp;
						</c:if>
						<a href="###" onclick="history.go(-1)">[ <bean:message key="go_back"/> ]</a>
					</td>
				</tr>
			</table>
		</div>
		<jsp:include flush="true" page="footer.jsp" />
	</c:otherwise>
</c:choose>

