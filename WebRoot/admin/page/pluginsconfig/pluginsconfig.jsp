<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_plugin_config" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td><bean:message key="a_extends_plugins_config_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=pluginsconfig">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="5%"><input class="checkbox" type="checkbox" name="chkall" class="header" onclick="checkall(this.form, 'delete')"><bean:message key="del" /></td>
			<td width="15%"><bean:message key="a_extends_plugins_name" /></td>
			<td width="15%"><bean:message key="a_extends_plugins_identifier" /></td>
			<td width="26%"><bean:message key="description" /></td>
			<td width="15%"><bean:message key="a_extends_plugins_directory" /></td>
			<td width="8%"><bean:message key="available" /></td>
			<td width="8%"><bean:message key="export" /></td>
			<td width="10%"><bean:message key="edit" /></td>
		</tr>
		<c:forEach items="${plugins}" var="plugin">
			<tr align="center">
				<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" value="${plugin.pluginid}"></td>
				<td class="altbg2"><b>${plugin.name}</b></td>
				<td class="altbg1">${plugin.identifier}</td>
				<td class="altbg2">${plugin.description}</td>
				<td class="altbg1">${plugin.directory}</td>
				<td class="altbg2"><input class="checkbox" type="checkbox" name="availablenew[${plugin.pluginid}]" value="1" ${(plugin.name==''||plugin.identifier=='')?"disabled" : (plugin.available>0?"checked" : "")}></td>
				<td class="altbg1"><a href="admincp.jsp?action=pluginsconfig&export=${plugin.pluginid}">[<bean:message key="download" />]</a></td>
				<td class="altbg2"><a href="admincp.jsp?action=pluginsedit&pluginid=${plugin.pluginid}">[<bean:message key="detail" />]</a></td>
			</tr>
		</c:forEach>
		<tr align="center" class="altbg1">
			<td><bean:message key="add_new" /></td>
			<td><input type='text' name="newname" size="12"></td>
			<td><input type='text' name="newidentifier" size="8"></td>
			<td colspan="6">&nbsp;</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="configsubmit" value="<bean:message key="submit" />"></center>
</form>
<br />
<form method="post" action="admincp.jsp?action=pluginsconfig">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td><bean:message key="a_extends_plugins_import" /></td>
		</tr>
		<tr>
			<td class="altbg1">
				<div align="center">
					<textarea name="plugindata" cols="80" rows="8"></textarea><br /><input class="checkbox" type="checkbox" name="ignoreversion" value="1"><bean:message key="a_extends_plugins_import_ignore_version" />
				</div>
			</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="importsubmit" value="<bean:message key="submit" />"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />