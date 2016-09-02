<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="header_basic" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
			<tr>
				<td><bean:message key="a_safety_base_tips_comment" />
				</td>
			</tr>
		</tbody>
	</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=safety&do=basic">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="a45b0b68771fc480"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="header_basic" /><a href="###" onclick="collapse_change('a45b0b68771fc480')"><img id="menuimg_a45b0b68771fc480" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_a45b0b68771fc480" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_manager_email" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_manager_email_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="adminemail" value="${settings.adminemail}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_data_error_log" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_data_error_log_common" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="dbreport" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="dbreport" value="0" ${settings.dbreport!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_error_info" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_close_error_info" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="errorreport" value="0" ${settings.errorreport==0?"checked":""}> <bean:message key="a_safety_close_all" /><br />
					<input class="radio" type="radio" name="errorreport" value="1" ${settings.errorreport==1?"checked":""}> <bean:message key="a_safety_send_to_admin" /><br />
					<input class="radio" type="radio" name="errorreport" value="2" ${settings.errorreport==2?"checked":""}> <bean:message key="a_safety_no_close" /><br />
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="a45bsdfv771fc480"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_safety_admincp_set" /><a href="###" onclick="collapse_change('a45bsdfv771fc480')"><img id="menuimg_a45bsdfv771fc480" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_a45bsdfv771fc480" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_answer" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_answer_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="admincp_forcesecques" value="1" checked> <bean:message key="a_safety_yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="admincp_forcesecques" value="0" ${settings.admincp_forcesecques!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_validate_ip" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_validate_ip_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="admincp_checkip" value="1" checked> <bean:message key="a_safety_yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="admincp_checkip" value="0" ${settings.admincp_checkip!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_templates_edit" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_templates_edit_online" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="admincp_tpledit" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="admincp_tpledit" value="0" ${settings.admincp_tpledit!=1?"checked":""}> <bean:message key="a_safety_no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_data_optimize" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_data_optimize_execute_sql" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="admincp_runquery" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="admincp_runquery" value="0" ${settings.admincp_runquery!=1?"checked":""}> <bean:message key="a_safety_no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_data_reset" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_data_reset_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="admincp_dbimport" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="admincp_dbimport" value="0" ${settings.admincp_dbimport!=1?"checked":""}> <bean:message key="a_safety_no" />
				</td>
			</tr>
		</tbody>
	</table>
	<br/>
	<a name="a45b0b35972fc480"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_safety_cookie_set" /><a href="###" onclick="collapse_change('a45b0b35972fc480')"><img id="menuimg_a45b0b35972fc480" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_a45b0b35972fc480" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_cookie_pre" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_cookie_pre_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="cookiepre" value="${settings.cookiepre}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_safety_cookie_area" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_cookie_area_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="cookiedomain" value="${settings.cookiedomain}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="ca_safety_cookie_path" /></b>
					<br />
					<span class="smalltxt"><bean:message key="ca_safety_cookie_path_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="cookiepath" value="${settings.cookiepath}"></td>
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