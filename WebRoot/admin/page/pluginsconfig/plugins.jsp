<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onClick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_plugin_edit" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="./images/admincp/menu_reduce.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display:">
		<tr><td><ul><li><bean:message key="a_extends_plugins_tips" /></ul></td></tr>
	</tbody>
</table>
<br />
<c:forEach items="${plugins}" var="plugin">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" ${plugin.disabled}>
		<tr class="header"><td colspan="2">${plugin.name} <c:if test="${plugin.available==0}">(<bean:message key="a_extends_plugins_unavailable" />)</c:if></td></tr>
		<tr><td width="20%" class="altbg1"><bean:message key="description" />:</td><td class="altbg2">${plugin.description}</td></tr>
		<tr><td width="20%" class="altbg1"><bean:message key="copyright" />:</td><td class="altbg2">${plugin.copyright}</td></tr>
		<tr><td width="20%" class="altbg1"><bean:message key="edit" />:</td><td class="altbg2">${plugin.edit}</td></tr>
	</table><br />
</c:forEach>
<jsp:include page="../cp_footer.jsp" />