<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_cachethread" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=cachethread">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="8f3238ad0dcdf991"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_cachethread" /><a href="###" onclick="collapse_change('8f3238ad0dcdf991')"><img id="menuimg_8f3238ad0dcdf991" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_8f3238ad0dcdf991" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_cachethread_indexlife" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_cachethread_indexlife_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="cacheindexlife" value="${settings.cacheindexlife}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_cachethread_life" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_cachethread_life_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="cachethreadlife" value="${settings.cachethreadlife}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_cachethread_dir" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_cachethread_dir_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="cachethreaddir" value="${settings.cachethreaddir}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="879a35297a417aec"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_cachethread_coefficient_set" /><a href="###" onclick="collapse_change('879a35297a417aec')"><img id="menuimg_879a35297a417aec" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_879a35297a417aec" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_cachethread_coefficient" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_cachethread_coefficient_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="30" name="threadcaches" value="${settings.threadcaches}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_cachethread_coefficient_forum" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_cachethread_coefficient_forum_comment" /></span>
				</td>
				<td class="altbg2">
					<select name="forum" multiple="multiple" style="width: 70%" size="10">
						<option value="all"><bean:message key="all_forum" /></option>
						<option value="">&nbsp;</option>
						${forumselect}
					</select>
				</td>
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