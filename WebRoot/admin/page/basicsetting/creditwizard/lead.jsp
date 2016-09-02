<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td><bean:message key="menu_tool_creditwizard" /></td>
	</tr>
	<tr>
		<td><a href="admincp.jsp?action=creditwizard"><bean:message key="a_setting_step_menu_1" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=toCreditExpression"><bean:message key="a_setting_step_menu_2" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=toCreditPurpose"><bean:message key="a_setting_step_menu_3" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=settings&do=ecommerce&from=creditwizard"><bean:message key="a_setting_ecommerce" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=tenpay&from=creditwizard"><bean:message key="a_setting_cplog_action_alipay" /></a></td>
	</tr>
</table>
<br />
