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
				<form method="post"
					action="admincp.jsp?action=settings&do=resetCredit&extcreditid=${resetCredit.extcreditid}&extcredits=extcredits${resetCredit.extcreditid}">
					<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
					<input type="hidden" name="param" value="resetCreditCommit">
					<input type="hidden" name="resetValue" value="${resetCredit.resetValue}">
					<input type="hidden" name="extcreditsName" value="${resetCredit.extcreditsName}">
					<br />
					<br />
					<br />
					<bean:message key="a_setting_resetusercredit_warning" arg0="${resetCredit.extcreditsName}" arg1="${resetCredit.resetValue}" />
					<br />
					<br />
					<br />
					<br />
					<input class="button" type="submit" name="confirmed" value=" <bean:message key="ok" /> ">
					&nbsp;
					<input class="button" type="button" value=" <bean:message key="cancel" /> " onClick="history.go(-1);">
				</form>
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