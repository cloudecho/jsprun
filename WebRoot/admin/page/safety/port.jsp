<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="memu_safety_port" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
			<tr>
				<td><bean:message key="a_safety_port_tips_comment" />
				</td>
			</tr>
		</tbody>
	</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=safety&do=port">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="a45b0b68771fc480"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_safety_port_change" /><a href="###" onclick="collapse_change('a45b0b68771fc480')"><img id="menuimg_a45b0b68771fc480" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
			<tbody id="menu_a45b0b68771fc480" style="display: yes" class="sub">
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="a_safety_port_choose_server_type" /></b>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="port" value="apache" onclick="$('hidden_apache').style.display = '';$('hidden_iis').style.display = 'none';$('hidden_tomcat').style.display = 'none';"><bean:message key="a_safety_port_update_appche" /> &nbsp; &nbsp;
						<input class="radio" type="radio" name="port" value="iis" onclick="$('hidden_iis').style.display = '';$('hidden_apache').style.display = 'none';$('hidden_tomcat').style.display = 'none';"><bean:message key="a_safety_port_update_iis" /> &nbsp; &nbsp;
						<input class="radio" type="radio" name="port" value="tomcat" onclick="$('hidden_tomcat').style.display = '';$('hidden_apache').style.display = 'none';$('hidden_iis').style.display = 'none';"><bean:message key="a_safety_port_update_tomcat" /> 
					</td>
				</tr>
		</tbody>
		<tbody class="sub" id="hidden_apache" style="display:none">
		<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="a_safety_port_target" /></b>
					<br />
						<span class="smalltxt"><bean:message key="a_safety_port_target_comment" /></span>
			   		 </td>
					<td class="altbg2">
						<input type="text" size="5" name="apache_port" value="">
			   		 </td>
				</tr>
			<tr>
				<td width="45%" class="altbg1">
				<b><bean:message key="a_safety_port_apache_file_path" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_safety_port_apache_file_path_comment" /></span>
			    </td>
				<td class="altbg2">
					<input type="text" size="50" name="apache_filepath" value="">
			    </td>
			</tr>
		</tbody>
			<tbody class="sub" id="hidden_tomcat" style="display:none">
			<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="a_safety_port_target" /></b>
					<br />
						<span class="smalltxt"><bean:message key="a_safety_port_target_comment" /></span>
			   		 </td>
					<td class="altbg2">
						<input type="text" size="5" name="tomcat_port" value="">
			   		 </td>
				</tr>
			<tr>
				<td width="45%" class="altbg1">
				<b><bean:message key="a_safety_port_tomcat_file_path" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_safety_port_tomcat_file_path_comment" /></span>
			    </td>
				<td class="altbg2">
					<input type="text" size="50" name="tomcat_filepath" value="">
			    </td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden_iis" style="display:none">
		<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="a_safety_port_target" /></b>
					<br />
						<span class="smalltxt"><bean:message key="a_safety_port_target_comment" /></span>
			   		 </td>
					<td class="altbg2">
						<input type="text" size="5" name="iis_port" value="">
			   		 </td>
				</tr>
			<tr>
				<td width="45%" class="altbg1">
				<b><bean:message key="a_safety_port_iis_file_path" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_safety_port_iis_file_path_comment" /></span>
			    </td>
				<td class="altbg2">
					<input type="text" size="50" name="iis_filepath" value="">
			    </td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input type="hidden" name="from" value="${from}">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />