<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_space" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=space">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="d3799a36b50e3bbd"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_space" /><a href="###" onclick="collapse_change('d3799a36b50e3bbd')"><img id="menuimg_d3799a36b50e3bbd" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_d3799a36b50e3bbd" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_spacestatus" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" checked name="spacestatus" value="1" onclick="$('hidden_settings_spacestatus').style.display = '';"> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" ${settings.spacestatus!=1?"checked":""} name="spacestatus" value="0" onclick="$('hidden_settings_spacestatus').style.display = 'none';"> <bean:message key="no" />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden_settings_spacestatus" style="display: ${settings.spacestatus==1?'':'none'}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacecachelife" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacecachelife_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="cachelife" value="${spacedata.cachelife}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacelimitmythreads" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacelimitmythreads_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="limitmythreads" value="${spacedata.limitmythreads}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacelimitmyreplies" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacelimitmyreplies_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="limitmyreplies" value="${spacedata.limitmyreplies}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacelimitmyrewards" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacelimitmyrewards_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="limitmyrewards" value="${spacedata.limitmyrewards}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacelimitmytrades" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacelimitmytrades_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="limitmytrades" value="${spacedata.limitmytrades}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacelimitmyblogs" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacelimitmyblogs_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="limitmyblogs" value="${spacedata.limitmyblogs}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacelimitmyfriends" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacelimitmyfriends_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="limitmyfriends" value="${spacedata.limitmyfriends}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacelimitmyfavforums" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacelimitmyfavforums_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="limitmyfavforums" value="${spacedata.limitmyfavforums}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacelimitmyfavthreads" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacelimitmyfavthreads_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="limitmyfavthreads" value="${spacedata.limitmyfavthreads}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_spacetextlength" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_spacetextlength_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="textlength" value="${spacedata.textlength}"></td>
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