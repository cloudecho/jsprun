<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_medal"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr><td><ul><li><bean:message key="a_other_medals_tips"/></li></ul></td></tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=medals">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="useraction" value="updateForumMedals">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="6"><bean:message key="menu_other_medal"/></td></tr>
		<tr align="center" class="category">
			<td><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form, 'delete')"><bean:message key="del"/></td>
			<td><bean:message key="name"/></td>
			<td><bean:message key="available"/></td>
			<td><bean:message key="medals_image"/></td>
		</tr>
	<c:forEach items="${medalBeanList}" var="medal">
		<input type="hidden" name="medalid" value="${medal.medalid }">
		<tr align="center">
			<td class="altbg1" width="48"><input class="checkbox" type="checkbox" name="delete" value="${medal.medalid }"></td>
			<td class="altbg2"><input type="text" size="30" name="name" value="${medal.name }" maxlength="50"></td>
			<td class="altbg1"><input class="checkbox" type="checkbox" name="available${medal.medalid }" value="1" ${medal.available == 1?"checked":""}></td>
			<td class="altbg2"><input type="text" size="25" name="image" value="${medal.image }" maxlength="30"> <img src="images/common/${medal.image }"></td>
		</tr>
	</c:forEach>
		<tr class="altbg1" align="center">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" size="30" name="newname" maxlength="50"></td>
			<td><input class="checkbox" type="checkbox" name="newavailable" value="1"></td>
			<td><input type="text" size="25" name="newimage" maxlength="30"></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="medalsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />