<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onClick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_plugin_edit" /></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=pluginvars&pluginid=${pluginvar.pluginid}&pluginvarid=${pluginvar.pluginvarid}">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<a name="2f100f94952e0588"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td colspan="2"><bean:message key="a_extends_plugins_edit_vars" /> - ${pluginvar.title}<a href="###" onclick="collapse_change('2f100f94952e0588')"><img id="menuimg_2f100f94952e0588" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
	<tbody id="menu_2f100f94952e0588" style="display: yes">
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_extends_plugins_edit_vars_title" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_vars_title_comment" /></span></td>
			<td class="altbg2"><input type="text" size="50" name="titlenew" value="${pluginvar.title}" ></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" valign="top"><b><bean:message key="a_extends_plugins_edit_vars_description" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_vars_description_comment" /></span></td>
			<td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('descriptionnew', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('descriptionnew', 0)"><br /><textarea  rows="6" name="descriptionnew" id="descriptionnew" cols="50">${pluginvar.description}</textarea></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_extends_plugins_edit_vars_type" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_vars_type_comment" /></span></td>
			<td class="altbg2"><select name="typenew"><c:forEach items="${types}" var="type"><option value="${type.key}" ${type.key==pluginvar.type?"selected":""}>${type.value}</option></c:forEach></select></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_extends_plugins_edit_vars_variable" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_vars_variable_comment" /></span></td>
			<td class="altbg2"><input type="text" size="50" name="variablenew" value="${pluginvar.variable}" ></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" valign="top"><b><bean:message key="a_extends_plugins_edit_vars_extra" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_vars_extra_comment" /></span></td>
			<td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('extranew', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('extranew', 0)"><br /><textarea  rows="6" name="extranew" id="extranew" cols="50">${pluginvar.extra}</textarea></td>
		</tr>
	</tbody>
</table>
<br />
<center><input class="button" type="submit" name="varsubmit" value="<bean:message key="submit" />"></center>
</form>
<br />
<jsp:directive.include file="../cp_footer.jsp" />