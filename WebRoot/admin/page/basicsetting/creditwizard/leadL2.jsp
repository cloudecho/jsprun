<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<td colspan="2">
	<a href="admincp.jsp?action=creditwizard&step=1"><bean:message key="a_setting_step_menu_1" /></a> - extcredits${extcreditid}(${extcredit.title })
</td>
</tr>
<tr class="category">
	<td class="altbg2">
		<a href="admincp.jsp?action=settings&do=particular&extcreditid=${extcreditid}"><bean:message key="a_setting_settingtype_global" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=settings&do=bankuaiSetting&extcreditid=${extcreditid}"><bean:message key="menu_forum" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=settings&do=usergroupSetting&extcreditid=${extcreditid}"><bean:message key="a_setting_settingtype_usergroup" /></a>
	</td>