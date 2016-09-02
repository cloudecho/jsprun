<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_creditwizard" /></td></tr>
</table>
<br />
<jsp:include page="lead.jsp"/>
<form method="post" action="admincp.jsp?action=settings&do=particular&extcreditid=${valueObject.currentExtcredit.id}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" align="center">
		<tr class="header">
			<td colspan="2">
				<a href="admincp.jsp?action=creditwizard"><bean:message key="a_setting_step_menu_1" /></a>${valueObject.currentCreditsName_nc }
			</td>
		</tr>
		<tr class="category">
			<td class="altbg2">
				<a href="admincp.jsp?action=settings&do=particular&extcreditid=${valueObject.currentExtcredit.id}"><bean:message key="a_setting_settingtype_global" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="admincp.jsp?action=settings&do=bankuaiSetting&extcreditid=${valueObject.currentExtcredit.id}"><bean:message key="menu_forum" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="admincp.jsp?action=settings&do=usergroupSetting&extcreditid=${valueObject.currentExtcredit.id}"><bean:message key="a_setting_settingtype_usergroup" /></a>
			</td>
			<td class="altbg2" style="text-align: right">
				<select id="creditid" onchange="location.href='admincp.jsp?action=settings&do=particular&extcreditid=' + this.value">
					<c:forEach items="${valueObject.credits_id_nameMap}" var="credits_id_name">
						<option value="${credits_id_name.key}" ${valueObject.currentExtcredit.id== credits_id_name.key?"selected":""}>
							${credits_id_name.value }
						</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;
			</td>
		</tr>
		<tr class="category">
			<td colspan="2" class="altbg2">
				<bean:message key="a_setting_settingtype_global_tips" />
			</td>
		</tr>
	</table>
	<br />
	<a name="42b5c4e09c9ab972"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_setting_credits_extended" />
				<a href="###" onclick="collapse_change('42b5c4e09c9ab972')"><img id="menuimg_42b5c4e09c9ab972" src="${pageContext.request.contextPath}/images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
			</td>
		</tr>
		<tbody id="menu_42b5c4e09c9ab972" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credit_title" /></b>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="title" value="${valueObject.currentExtcredit.title}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_unit" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_unit_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="unit" value="${valueObject.currentExtcredit.unit}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_ratio" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_extended_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="ratio" value="${valueObject.currentExtcredit.ratio}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_init" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_init_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="initcredits" value="${valueObject.currentExtcredit.initcredit}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_available" /></b>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="available" value="1" ${valueObject.currentExtcredit.available?"checked":""}>
					<bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="available" value="0" ${!valueObject.currentExtcredit.available?"checked":""}>
					<bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="show_in_thread" />:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_show_in_thread_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="showinthread" value="1" ${valueObject.currentExtcredit.showinthread?"checked":""}>
					<bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="showinthread" value="0" ${!valueObject.currentExtcredit.showinthread?"checked":""}>
					<bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_creditwizard_outport" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_creditwizard_outport_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowexchangeout" value="1" ${valueObject.currentExtcredit.allowexchangeout?"checked":""}>
					<bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowexchangeout" value="0" ${!valueObject.currentExtcredit.allowexchangeout?"checked":""}>
					<bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="credits_import" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_creditwizard_allow_inport_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowexchangein" value="1" ${valueObject.currentExtcredit.allowexchangein?"checked":""}>
					<bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowexchangein" value="0" ${!valueObject.currentExtcredit.allowexchangein?"checked":""}>
					<bean:message key="no" />
				</td>
			</tr>
	</table>
	<br />
	<a name="cbbd51065505ee3d"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_setting_credits_policy" />
				<a href="###" onclick="collapse_change('cbbd51065505ee3d')"><img id="menuimg_cbbd51065505ee3d" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
			</td>
		</tr>
		<tbody id="menu_cbbd51065505ee3d" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_post" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_post_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="post" value="${valueObject.creditspolicy_post}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_reply" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_reply_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="reply" value="${valueObject.creditspolicy_reply}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_digest" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_digest_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="digest" value="${valueObject.creditspolicy_digest}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_post_attach" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_post_attach_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="postattach" value="${valueObject.creditspolicy_postattach}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_get_attach" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_get_attach_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="getattach" value="${valueObject.creditspolicy_getattach}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_send_pm" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_send_pm_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="pm" value="${valueObject.creditspolicy_pm}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_search" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_search_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="search" value="${valueObject.creditspolicy_search}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_promotion_visit" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_promotion_visit_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="promotion_visit" value="${valueObject.creditspolicy_promotion_visit}" >
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_promotion_register" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_promotion_register_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="promotion_register" value="${valueObject.creditspolicy_promotion_register}" >
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_trade2" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_trade_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="tradefinished" value="${valueObject.creditspolicy_tradefinished}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_policy_poll2" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_policy_poll_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="votepoll" value="${valueObject.creditspolicy_votepoll}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_credits_lowerlimit" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_credits_lowerlimit_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="lowerlimit" value="${valueObject.creditspolicy_lowerlimit}">
				</td>
			</tr>
			<tr>
				<td colspan="2" class="altbg1">
					<bean:message key="a_setting_credits_policy_comment" />
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input class="button" type="reset" name="settingsubmit" value="<bean:message key="reset" />">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../../cp_footer.jsp" />