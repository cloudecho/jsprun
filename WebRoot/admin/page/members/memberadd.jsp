<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_add"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=memberadd">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_member_add"/></td></tr>
		<tr>
			<td class="altbg1" width="50%">
				<bean:message key="a_member_add_uid_range"/>
			</td>
			<td align="right" class="altbg2"><input type="text" name="uidlowerlimit" size="5"> - <input type="text" name="uidupperlimit" size="5"></td>
		</tr>
		<tr>
			<td class="altbg1"><bean:message key="username"/>:</td>
			<td align="right" class="altbg2"><input type="text" name="username"></td>
		</tr>
		<tr>
			<td class="altbg1"><bean:message key="password"/>:</td>
			<td align="right" class="altbg2"><input type="text" name="password"></td>
		</tr>
		<tr>
			<td class="altbg1"><bean:message key="email"/>:</td>
			<td align="right" class="altbg2"><input type="text" name="email"></td>
		</tr>
		<tr>
			<td class="altbg1"><bean:message key="menu_member_usergroups"/>:</td>
			<td align="right" class="altbg2">
				<select name="groupid">
					<c:forEach items="${usergroups}" var="usergroup">
						<option value="${usergroup.groupid}">${usergroup.grouptitle}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="altbg1"><bean:message key="a_member_add_email_notify"/></td>
			<td align="right" class="altbg2"><input class="checkbox" type="checkbox" name="emailnotify" value="yes"></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="addsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />