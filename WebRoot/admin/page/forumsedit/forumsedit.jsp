<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_forum_edit"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td><bean:message key="a_forum_tips"/></td>
		</tr>
	</tbody>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><bean:message key="menu_forum_edit"/></td></tr>
	<tr>
		<td class="altbg1">
			<br />
			<form method="post" action="admincp.jsp?action=forumsedit">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				${forums}
				<br />
				<center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>"></center>
				<br />
			</form>
		</td>
	</tr>
</table>
<jsp:include page="../cp_footer.jsp" />