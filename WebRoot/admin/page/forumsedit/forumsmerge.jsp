<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_forum_merge"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=forumsmerge">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="3"><bean:message key="a_forum_merge"/></td></tr>
		<tr align="center">
			<td class="altbg1" width="40%"><bean:message key="source_forum"/>:</td>
			<td class="altbg2" width="45%">
				<select name="sourceId">
					<option value="0">&nbsp;&nbsp;> <bean:message key="select"/></option>
					<option value="0">&nbsp;</option>
					${forumselect}
				</select>
			</td>
		</tr>
		<tr align="center">
			<td class="altbg1" width="40%"><bean:message key="target_forum"/>:</td>
			<td class="altbg2" width="45%">
				<select name="targetId">
					<option value="0">&nbsp;&nbsp;> <bean:message key="select"/></option>
					<option value="0">&nbsp;</option>
					${forumselect}
				</select>
			</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="mergesubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />