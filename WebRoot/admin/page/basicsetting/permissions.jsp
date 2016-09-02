<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_permissions" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=permissions">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="3fea38d0180b5fe6"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_permissions" /><a href="###" onclick="collapse_change('3fea38d0180b5fe6')"><img id="menuimg_3fea38d0180b5fe6" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_3fea38d0180b5fe6" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_memliststatus" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="memliststatus" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="memliststatus" value="0" ${settings.memliststatus!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_reportpost" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_reportpost_comment" /></span>
				</td>
				<td class="altbg2">
					<html:select property="reportpost" value="${settings.reportpost}" name="reportpost" style="width: 55%">
						<html:option value="0"><bean:message key="a_setting_reportpost_none" /></html:option>
						<html:option value="1"><bean:message key="a_setting_reportpost_level_1" /></html:option>
						<html:option value="2"><bean:message key="a_setting_reportpost_level_2" /></html:option>
						<html:option value="3"><bean:message key="a_setting_reportpost_level_3" /></html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_minpostsize" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_minpostsize_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="minpostsize" value="${settings.minpostsize}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxpostsize" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxpostsize_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxpostsize" value="${settings.maxpostsize}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_favorite_storage" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_favorite_storage_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxfavorites" value="${settings.maxfavorites}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_subscriptions" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_subscriptions_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxsubscriptions" value="${settings.maxsubscriptions}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxavatarsize" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxavatarsize_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxavatarsize" value="${settings.maxavatarsize}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxavatarpixel" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxavatarpixel_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxavatarpixel" value="${settings.maxavatarpixel}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxpolloptions" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxpolloptions_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxpolloptions" value="${settings.maxpolloptions}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_edittimelimit" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_edittimelimit_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="edittimelimit" value="${settings.edittimelimit}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_editby" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_editby_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="editedby" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="editedby" value="0" ${settings.editedby!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="5950469c3cc55717"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_rate" /><a href="###" onclick="collapse_change('5950469c3cc55717')"><img id="menuimg_5950469c3cc55717" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_5950469c3cc55717" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_karmaratelimit" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_modratelimit" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="karmaratelimit" value="${settings.karmaratelimit}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_modratelimit" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_modratelimit_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="modratelimit" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="modratelimit" value="0" ${settings.modratelimit!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_dupkarmarate" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_dupkarmarate_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="dupkarmarate" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="dupkarmarate" value="0" ${settings.dupkarmarate!=1?"checked":""}> <bean:message key="no" />
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