<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onClick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_ecommerce_tenpay" /></td></tr>
</table>
<br />
<c:if test="${param.from=='creditwizard'}">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><bean:message key="menu_tool_creditwizard" /></td></tr>
		<tr><td><a href="admincp.jsp?action=creditwizard"><bean:message key="a_setting_step_menu_1" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=toCreditExpression"><bean:message key="a_setting_step_menu_2" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=toCreditPurpose"><bean:message key="a_setting_step_menu_3" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=settings&do=ecommerce&from=creditwizard"><bean:message key="a_setting_ecommerce" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=tenpay&from=creditwizard"><bean:message key="a_setting_cplog_action_alipay" /></a></td></tr>
	</table>
	<br />
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<div style="float: left; margin-left: 0px; padding-top: 8px"><a href="###" onclick="collapse_change('tip');return false"><bean:message key="tips" /></a></div>
			<div style="float: right; margin-right: 4px; padding-bottom: 9px"><a href="###" onclick="collapse_change('tip');return false"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /></a></div>
		</td>
	</tr>
	<tbody id="menu_tip" style="display: ">
		<tr>
			<td><bean:message key="a_extends_ecommerce_tenpay_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" name="settings" action="admincp.jsp?action=tenpay">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="e049508a7c2e51d2"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="a_setting_cplog_action_alipay" /><a href="###" onclick="collapse_change('e049508a7c2e51d2');return false"><img id="menuimg_e049508a7c2e51d2" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_e049508a7c2e51d2" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_extends_tenpay_account" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_extends_tenpay_account_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="ec_account"value="${settings.ec_account}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_extends_tenpay_key" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_extends_tenpay_key_comment" /></span>
				</td>
				<td class="altbg2"><input type="password" size="50" name="ec_key" value="${settings.ec_key}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<center><input class="button" type="submit" name="tenpaysubmit" value="<bean:message key="submit" />"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />