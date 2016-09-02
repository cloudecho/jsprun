<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_creditwizard" /></td></tr>
</table>
<br />
<jsp:include page="lead.jsp"/>
<br />
<br />
<br />
<br />
<br />
<br />
<table width="500" border="0" cellpadding="0" cellspacing="0"
	align="center" class="tableborder">
	<tr class="header">
		<td>
			<bean:message key="jsprun_message" />
		</td>
	</tr>
	<tr>
		<td class="altbg2">
			<div align="center">
				<br />
				<br />
				<br />
				<bean:message key="a_setting_resetusercredit_ok" />
				<br />
				<br />
				<br />
				<a href="admincp.jsp?action=creditwizard&step=1"><bean:message key="message_redirect" /></a>
				<script>setTimeout("redirect('admincp.jsp?action=creditwizard&step=1&sid=maSIgj');", 2000);</script>
				<br />
				<br />
			</div>
			<br />
			<br />
		</td>
	</tr>
</table>
<br />
<br />
<br />
<jsp:directive.include file="../../cp_footer.jsp" />