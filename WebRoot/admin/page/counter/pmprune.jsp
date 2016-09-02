<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_pmprune" /></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=pmprune&batch=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_system_prune_pm" />
			</td>
		</tr>
		<tr>
			<td class="altbg1">
				<bean:message key="a_system_prune_pm_ignore_new" />
			</td>
			<td class="altbg2" align="right">
				<input class="checkbox" type="checkbox" name="ignorenew" value="1">
			</td>
		</tr>

		<tr>
			<td class="altbg1">
				<bean:message key="a_system_prune_pm_day" />
			</td>
			<td class="altbg2" align="right">
				<input type="text" name="days" size="7">
			</td>
		</tr>

		<tr>
			<td class="altbg1">
				<bean:message key="a_system_prune_pm_user" />
			</td>
			<td class="altbg2" align="right">
				<bean:message key="case_insensitive" />
				<input class="checkbox" type="checkbox" name="cins" value="1">
				<br />
				<input type="text" name="users" size="40">
			</td>
		</tr>

		<tr>
			<td class="altbg1">
				<bean:message key="a_system_prune_search_keywords" />
			</td>
			<td class="altbg2" align="right">
				<input class="radio" type="radio" name="srchtype" value="title" checked>
				<bean:message key="a_system_prune_search_title" /> &nbsp;
				<input class="radio" type="radio" name="srchtype" value="fulltext">
				<bean:message key="search_fulltext" />
				<br />
				<input type="text" name="srchtxt" size="40" maxlength="40">
				<br />
				<bean:message key="a_system_pruce_rules" />
			</td>
		</tr>

	</table>
	<br />
	<center>
		<input class="button" type="submit" name="prunesubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />