<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onClick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_plugin_config" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="./images/admincp/menu_reduce.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display:">
		<tr>
			<td>
			<bean:message key="a_extends_plugins_edit_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<a name="common"></a>
<form method="post" action="admincp.jsp?action=pluginsedit&type=common&pluginid=${plugin.pluginid}">
	<a name="2f2a470a8d36789b"></a>
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_extends_plugins_edit" /> - ${plugin.name}<a href="###" onclick="collapse_change('2f2a470a8d36789b')"><img id="menuimg_2f2a470a8d36789b" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_2f2a470a8d36789b" style="display: yes">
			<tr>
				<td width="45%" class="altbg1" ><b><bean:message key="a_extends_plugins_edit_name" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_name_comment" /></span></td>
				<td class="altbg2"><input type="text" size="50" name="namenew" value="${plugin.name}" ></td>
			</tr>
			<c:if test="${plugin.copyright==''}">
				<tr>
					<td width="45%" class="altbg1" ><b><bean:message key="a_extends_plugins_edit_copyright" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_copyright_comment" /></span></td>
					<td class="altbg2"><input type="text" size="50" name="copyrightnew" value="${plugin.copyright}" ></td>
				</tr>
			</c:if>
			<tr>
				<td width="45%" class="altbg1" ><b><bean:message key="a_extends_plugins_edit_identifier" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_identifier_comment" /></span></td>
				<td class="altbg2"><input type="text" size="50" name="identifiernew" value="${plugin.identifier}" ></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" ><b><bean:message key="a_extends_plugins_edit_adminid" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_adminid_comment" /></span></td>
				<td class="altbg2">
					<select name="adminidnew">
						<option value="1" ${plugin.adminid==1?"selected":""}><bean:message key="usergroups_system_1" /></option>
						<option value="2" ${plugin.adminid==2?"selected":""}><bean:message key="usergroups_system_2" /></option>
						<option value="3" ${plugin.adminid==3?"selected":""}><bean:message key="usergroups_system_3" /></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" ><b><bean:message key="a_extends_plugins_edit_directory" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_directory_comment" /> </span></td>
				<td class="altbg2"><input type="text" size="50" name="directorynew" value="${plugin.directory}" ></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" ><b><bean:message key="a_extends_plugins_edit_datatables" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_datatables_comment" /></span></td>
				<td class="altbg2"><input type="text" size="50" name="datatablesnew" value="${plugin.datatables}" ></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top"><b><bean:message key="a_extends_plugins_edit_description" /></b><br /><span class="smalltxt"><bean:message key="a_extends_plugins_edit_description_comment" /></span></td>
				<td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('descriptionnew', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('descriptionnew', 0)"><br /><textarea  rows="6" name="descriptionnew" id="descriptionnew" cols="50">${plugin.description}</textarea></td>
			</tr>
		</tbody>
	</table>
	<br /><center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit" />"></center>
</form>
<br />
<a name="modules"></a>
<form method="post" action="admincp.jsp?action=pluginsedit&type=modules&pluginid=${plugin.pluginid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="7"><bean:message key="a_extends_plugins_edit_modules" /></td></tr>
		<tr class="category" align="center">
			<td width="45"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form,'delete')"><bean:message key="del" /></td>
			<td><bean:message key="a_extends_plugins_edit_modules_name" /></td>
			<td><bean:message key="a_extends_plugins_edit_modules_menu" /></td>
			<td><bean:message key="a_extends_plugins_edit_modules_menu_url" /></td>
			<td><bean:message key="a_extends_plugins_edit_modules_type" /></td>
			<td><bean:message key="a_extends_plugins_edit_modules_adminid" /></td>
			<td><bean:message key="display_order" /></td>
		</tr>
		<c:forEach items="${modules}" var="module">
			<tr class="altbg1" align="center">
				<td class="altbg1"><input class="checkbox" type="checkbox" name="delete[${module.key}]" value="${module.key}"></td>
				<td class="altbg2"><input type="text" size="15" name="namenew[${module.key}]" value="${module.value.name}"></td>
				<td class="altbg1"><input type="text" size="15" name="menunew[${module.key}]" value="${module.value.menu}"></td>
				<td class="altbg2"><input type="text" size="15" name="urlnew[${module.key}]" value="${module.value.url}"></td>
				<td class="altbg1"><select name="typenew[${module.key}]"><c:forEach items="${moduleoptions}" var="moduleoption"><option value="${moduleoption.key}" ${moduleoption.key == module.value.type ? "selected" : ""}>${moduleoption.value}</option></c:forEach></select></td>
				<td class="altbg2"><select name="adminidnew[${module.key}]"><c:forEach items="${moduleadmins}" var="moduleadmin"><option value="${moduleadmin.key}" ${moduleadmin.key == module.value.adminid ? "selected" : ""}>${moduleadmin.value}</option></c:forEach></select></td>
				<td class="altbg1"><input type="text" size="2" name="ordernew[${module.key}]" value="${module.value.displayorder}"></td>
			</tr>
		</c:forEach>
		<tr class="altbg1" align="center">
			<td><bean:message key="add_new" /></td><td><input type="text" size="15" name="newname"></td>
			<td><input type="text" size="15" name="newmenu"></td>
			<td><input type="text" size="15" name="newurl"></td>
			<td><select name="newtype"><option value="1"><bean:message key="a_extends_plugins_edit_modules_type_1" /></option><option value="2"><bean:message key="a_extends_plugins_edit_modules_type_2" /></option><option value="3"><bean:message key="a_extends_plugins_edit_modules_type_3" /></option><option value="4"><bean:message key="a_extends_plugins_edit_modules_type_4" /></option><option value="5"><bean:message key="a_extends_plugins_edit_modules_type_5" /></option><option value="5"><bean:message key="a_extends_plugins_edit_modules_type_6" /></option></select></td>
			<td><select name="newadminid"><option value="0"><bean:message key="usergroups_system_0" /></option><option value="1" selected><bean:message key="usergroups_system_1" /></option><option value="2"><bean:message key="usergroups_system_2" /></option><option value="3"><bean:message key="usergroups_system_3" /></option></select></td>
			<td><input type="text" size="2" name="neworder"></td>
		</tr>
	</table>
	<br /><center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit" />"></center>
</form>
<br />

<a name="hooks"></a>
<form method="post" action="admincp.jsp?action=pluginsedit&type=hooks&pluginid=${plugin.pluginid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="6"><bean:message key="a_extends_plugins_edit_hooks" /></td></tr>
		<tr class="category" align="center">
			<td width="45"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form,'delete')"><bean:message key="del" /></td>
			<td width="20%"><bean:message key="a_extends_plugins_hooks_title" /></td>
			<td width="25%"><bean:message key="a_extends_plugins_hooks_callback" /></td>
			<td width="25%"><bean:message key="a_extends_plugins_edit_hooks_description" /></td>
			<td width="45"><bean:message key="available" /></td>
			<td><bean:message key="edit" /></td>
		</tr>
		<c:forEach items="${pluginhooks}" var="pluginhook">
			<tr align="center">
				<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" value="${pluginhook.pluginhookid}"></td>
				<td class="altbg2"><input type="text" name="titlenew[${pluginhook.pluginhookid}]" size="15" value="${pluginhook.title}"></td>
				<td class="altbg1"><input type="text" name="hookevalcode${pluginhook.pluginhookid}" id="hookevalcode${pluginhook.pluginhookid}" size="30" value="${pluginhook.available>0?pluginhook.evalcode : 'N/A'}" readonly></td>
				<td class="altbg2">${pluginhook.description}</td>
				<td class="altbg1"><input class="checkbox" type="checkbox" name="availablenew[${pluginhook.pluginhookid}]" value="1" ${pluginhook.available>0?"checked" : ""} onclick="if(this.checked) {$('hookevalcode${pluginhook.pluginhookid}').value='${pluginhook.evalcode}';}else{$('hookevalcode${pluginhook.pluginhookid}').value='N/A';}"></td>
				<td class="altbg2"><a href="admincp.jsp?action=pluginhooks&pluginid=${plugin.pluginid}&pluginhookid=${pluginhook.pluginhookid}">[<bean:message key="edit" />]</a></td>
			</tr>
		</c:forEach>
		<tr class="altbg1" align="center">
			<td><bean:message key="add_new" /></td>
			<td><input type="text" name="newtitle" size="15"></td>
			<td colspan="4">&nbsp;</td>
		</tr>
	</table>
	<br /><center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit" />"></center>
</form><br />

<a name="vars"></a>
<form method="post" action="admincp.jsp?action=pluginsedit&type=vars&pluginid=${plugin.pluginid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td colspan="6"><bean:message key="a_extends_plugins_edit_vars" /></td></tr>
	<tr class="category" align="center">
		<td width="45"><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form,'delete')"><bean:message key="del" /></td>
		<td><bean:message key="a_extends_plugins_vars_title" /></td>
		<td><bean:message key="a_extends_plugins_vars_variable" /></td>
		<td><bean:message key="a_extends_plugins_vars_type" /></td>
		<td><bean:message key="display_order" /></td>
		<td><bean:message key="edit" /></td>
	</tr>
	<c:forEach items="${pluginvars}" var="pluginvar">
		<tr align="center">
			<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" value="${pluginvar.pluginvarid}"></td>
			<td class="altbg2">${pluginvar.title}</td>
			<td class="altbg1">${pluginvar.variable}</td>
			<td class="altbg2">${types[pluginvar.type]}</td>
			<td class="altbg1"><input type="text" size="2" name="displayordernew[${pluginvar.pluginvarid}]" value="${pluginvar.displayorder}"></td>
			<td class="altbg2"><a href="admincp.jsp?action=pluginvars&pluginid=${plugin.pluginid}&pluginvarid=${pluginvar.pluginvarid}">[<bean:message key="detail" />]</a></td>
		</tr>
	</c:forEach>
	<tr align="center" class="altbg1">
		<td><bean:message key="add_new" /></td>
		<td><input type="text" size="15" name="newtitle"></td>
		<td><input type="text" size="15" name="newvariable"></td>
		<td><select name="newtype"><option value="number"><bean:message key="a_forum_threadtype_edit_number" /></option><option value="text" selected><bean:message key="a_forum_threadtype_edit_text" /></option><option value="textarea"><bean:message key="a_forum_threadtype_edit_textarea" /></option><option value="radio"><bean:message key="a_extends_plugins_edit_vars_type_radio" /></option><option value="select"><bean:message key="a_forum_threadtype_edit_select" /></option><option value="color"><bean:message key="a_extends_plugins_edit_vars_type_color" /></option></seletc></td>
		<td><input type="text" size="2" name="newdisplayorder" value="0"></td>
		<td>&nbsp;</td>
	</tr>
	</table>
	<br /><center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit" />"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />