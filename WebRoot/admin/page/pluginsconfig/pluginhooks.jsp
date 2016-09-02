<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onClick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_plugin_edit" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="./images/admincp/menu_reduce.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display:">
		<tr>
			<td><bean:message key="a_extends_plugins_edit_hooks_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=pluginhooks&pluginid=${pluginhook.pluginid}&pluginhookid=${pluginhook.pluginhookid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="7d117e44e6af85d2"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_extends_plugins_edit_hooks" /> - ${pluginhook.title}<a href="###" onclick="collapse_change('7d117e44e6af85d2')"><img id="menuimg_7d117e44e6af85d2" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_7d117e44e6af85d2" style="display: yes">
			<tr>
				<td width="45%" class="altbg1" valign="top"><b><bean:message key="a_extends_plugins_edit_hooks_description" />:</b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_hooks_description_comment" /></span></td>
				<td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('descriptionnew', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('descriptionnew', 0)"><br /><textarea  rows="6" name="descriptionnew" id="descriptionnew" cols="50">${pluginhook.description}</textarea></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top"><b><bean:message key="a_extends_plugins_edit_hooks_code" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_hooks_code_comment" /></span></td>
				<td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('codenew', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('codenew', 0)"><br /><textarea  rows="6" name="codenew" id="codenew" cols="50">${pluginhook.code}</textarea></td>
			</tr>
		</tbody>
	</table>
	<br />
	<center><input class="button" type="submit" name="hooksubmit" value="<bean:message key="submit" />"></center>
</form>
<br />
<jsp:directive.include file="../cp_footer.jsp" />