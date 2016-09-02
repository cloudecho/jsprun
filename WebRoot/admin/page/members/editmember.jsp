<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_edit"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=editmember">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_member_edit"/> - <bean:message key="a_member_search"/></td></tr>
		<tr class="altbg2">
			<td><bean:message key="a_member_edit_username"/></td>
			<td><input type="text" name="username"></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="membersubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />
