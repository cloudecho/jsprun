<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_wap" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=wap">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="8dc76a7e57c49e49"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_wap" /><a href="###" onclick="collapse_change('a45b0b68771fc480')"><img id="menuimg_a45b0b68771fc480" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_8dc76a7e57c49e49" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_wapstatus" /></b>
					<br /><span class="smalltxt"><bean:message key="a_setting_wapstatus_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="wapstatus" value="1" checked onclick="$('hidden_settings_wapstatus').style.display = '';"> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="wapstatus" value="0" ${settings.wapstatus!=1?"checked":""} onclick="$('hidden_settings_wapstatus').style.display = 'none';"> <bean:message key="no" />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden_settings_wapstatus" style="display: ${settings.wapstatus==1?'':'none'}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_wap_register" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_wap_register_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" checked name="wapregister" value="1"> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" ${settings.wapregister!=1?"checked":""} name="wapregister" value="0"> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_wapcharset" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_wapcharset_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="wapcharset" value="1" checked> UTF-8 &nbsp; &nbsp;
					<input class="radio" type="radio" name="wapcharset" value="2" ${settings.wapcharset!=1?"checked":""}> UNICODE &nbsp; &nbsp;
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_waptpp" /></b></td>
				<td class="altbg2"><input type="text" size="50" name="waptpp" value="${settings.waptpp}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_wapppp" /></b></td>
				<td class="altbg2"><input type="text" size="50" name="wapppp" value="${settings.wapppp}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_wapdateformat" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_jsdateformat_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="wapdateformat" value="<jrun:dhtmlspecialchars value="${settings.wapdateformat}"/>"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_wapmps" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_wapmps_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="wapmps" value="${settings.wapmps}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input type="hidden" name="from" value="">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />