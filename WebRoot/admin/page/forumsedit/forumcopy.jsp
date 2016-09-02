<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr><td><bean:message key="a_forum_copy_tips"/></td></tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=forumcopy&source=${sourceForum.fid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="a_forum_copy"/> - <bean:message key="source_forum"/> - ${sourceForum.name} <a href="###" onclick="collapse_change('4742d8d67a40143f')"><img id="menuimg_4742d8d67a40143f" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
	<tbody id="menu_4742d8d67a40143f" style="display: yes">
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="target_forum"/>:</b><br/><span class="smalltxt"><bean:message key="a_forum_copy_target_comment"/></span></td>
			<td class="altbg2"><select name="target" size="10" multiple="multiple" style="width: 80%">${forumselect}</select></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_copy_options"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_copy_options_comment"/></span></td>
			<td class="altbg2">
				<select name="options" size="10" multiple="multiple" style="width: 80%">
					<optgroup label="<bean:message key='a_forum_copy_optgroup_normal'/>">
						<option value="modnewposts"><bean:message key="a_forum_copy_option_modnewpost"/></option>
						<option value="recyclebin"><bean:message key="a_forum_copy_option_recyclebin"/></option>
						<option value="allowshare"><bean:message key="a_forum_copy_option_allowshare"/></option>
						<option value="allowhtml"><bean:message key="a_forum_copy_option_allowhtml"/></option>
						<option value="allowbbcode"><bean:message key="a_forum_copy_option_allowbbcode"/></option>
						<option value="allowimgcode"><bean:message key="a_forum_copy_option_allowimgcode"/></option>
						<option value="allowmediacode"><bean:message key="a_forum_copy_option_allowmediacode"/></option>
						<option value="allowsmilies"><bean:message key="a_forum_edit_smilies"/></option>
						<option value="jammer"><bean:message key="a_forum_edit_jammer"/></option>
						<option value="allowanonymous"><bean:message key="a_forum_copy_option_allowanonymou"/></option>
						<option value="disablewatermark"><bean:message key="a_forum_copy_option_disablewatermark"/></option>
						<option value="allowpostspecial"><bean:message key="a_forum_copy_option_allowpostspecial"/></option>
					</optgroup>
					<optgroup label="<bean:message key='menu_setting_credits'/>">
						<option value="postcredits"><bean:message key="a_forum_copy_option_postcredit"/></option>
						<option value="replycredits"><bean:message key="a_forum_copy_option_replycredit"/></option>
					</optgroup>
					<optgroup label="<bean:message key='a_forum_copy_optgroup_access'/>">
						<option value="password"><bean:message key="a_forum_copy_option_password"/></option>
						<option value="viewperm"><bean:message key="a_forum_copy_option_viewperm"/></option>
						<option value="postperm"><bean:message key="a_forum_copy_option_postperm"/></option>
						<option value="replyperm"><bean:message key="a_forum_copy_option_replyperm"/></option>
						<option value="getattachperm"><bean:message key="a_forum_copy_option_getattachperm"/></option>
						<option value="postattachperm"><bean:message key="a_forum_copy_option_postattachperm"/></option>
						<option value="formulaperm"><bean:message key="a_forum_copy_option_formulaperm"/></option>
					</optgroup>
					<optgroup label="<bean:message key='header_other'/>">
						<option value="threadtypes"><bean:message key="menu_forum_threadtypes"/></option>
						<option value="attachextensions"><bean:message key="a_forum_copy_option_attachextension"/></option>
						<option value="modrecommend"><bean:message key="a_forum_copy_option_modrecommend"/></option>
						<option value="tradetypes"><bean:message key="a_forum_edit_trade_type"/></option>
					</optgroup>
				</select>
			</td>
		</tr>
	</tbody>
	</table>
	<br />
	<br />
	<center><input class="button" type="submit" name="copysubmit" value="<bean:message key='submit'/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />