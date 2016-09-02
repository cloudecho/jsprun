<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_updatecounter" /></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=counter&forum=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_system_counter_forum" />
			</td>
		</tr>
		<tr class="altbg2">
			<td width="15%">
				<bean:message key="a_system_counter_amount" />
			</td>
			<td>
				<input type="text" name="pertask" value="15">
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="forumsubmit" value="<bean:message key="submit" />">
		&nbsp;
		<input class="button" type="reset" value="<bean:message key="reset" />">
	</center>
</form>
<br />

<form method="post" action="admincp.jsp?action=counter&digest=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_system_counter_digest" />
			</td>
		</tr>
		<tr class="altbg2">
			<td width="15%">
				<bean:message key="a_system_counter_amount" />
			</td>
			<td>
				<input type="text" name="pertask" value="1000">
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="digestsubmit" value="<bean:message key="submit" />">
		&nbsp;
		<input class="button" type="reset" value="<bean:message key="reset" />">
	</center>
</form>
<br />

<form method="post" action="admincp.jsp?action=counter&member=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_system_counter_member" />
			</td>
		</tr>
		<tr class="altbg2">
			<td width="15%">
				<bean:message key="a_system_counter_amount" />
			</td>
			<td>
				<input type="text" name="pertask" value="1000">
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="membersubmit" value="<bean:message key="submit" />">
		&nbsp;
		<input class="button" type="reset" value="<bean:message key="reset" />">
	</center>
</form>
<br />

<form method="post" action="admincp.jsp?action=counter&thread=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_system_counter_thread" />
			</td>
		</tr>
		<tr class="altbg2">
			<td width="15%">
				<bean:message key="a_system_counter_amount" />
			</td>
			<td>
				<input type="text" name="pertask" value="500">
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="threadsubmit" value="<bean:message key="submit" />">
		&nbsp;
		<input class="button" type="reset" value="<bean:message key="reset" />">
	</center>
</form>
<br />

<form method="post" action="admincp.jsp?action=counter&movedthread=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_system_counter_moved_thread" />
			</td>
		</tr>
		<tr class="altbg2">
			<td width="15%">
				<bean:message key="a_system_counter_amount" />
			</td>
			<td>
				<input type="text" name="pertask" value="100">
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="movedthreadsubmit" value="<bean:message key="submit" />">
		&nbsp;
		<input class="button" type="reset" value="<bean:message key="reset" />">
	</center>
</form>
<br />

<form method="post" action="admincp.jsp?action=counter&cleanup=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_system_counter_moved_favorites_logs" />
			</td>
		</tr>
		<tr class="altbg2">
			<td width="15%">
				<bean:message key="a_system_counter_amount" />
			</td>
			<td>
				<input type="text" name="pertask" value="100">
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="cleanupsubmit" value="<bean:message key="submit" />">
		&nbsp;
		<input class="button" type="reset" value="<bean:message key="reset" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />