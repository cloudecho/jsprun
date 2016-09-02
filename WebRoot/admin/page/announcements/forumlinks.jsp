<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_link"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr><td><bean:message key="a_other_fl_tips" arg0="admincp.jsp?action=settings&do=styles"/></td></tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=forumlinks">
	<input type="hidden" name="useraction" value="updateForumLinks">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="6"><bean:message key="a_other_fl_edit"/></td></tr>
		<tr align="center" class="category">
			<td><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form)"><bean:message key="del"/></td>
			<td><bean:message key="display_order"/></td>
			<td><bean:message key="a_other_fl_edit_name"/></td>
			<td><bean:message key="a_other_fl_edit_url"/></td>
			<td><bean:message key="a_other_fl_edit_description"/></td>
			<td><bean:message key="a_other_fl_edit_logo"/></td>
		</tr>
	<c:forEach items="${flList}" var="flBean">
		<tr align="center">
			<td class="altbg1">
				<input type="hidden" name="fid" value="${flBean.id }">
				<input class="checkbox" type="checkbox" name="delete" value="${flBean.id }">
			</td>
			<td class="altbg2">
				<input type="hidden" name="displayorder_h" value="${flBean.displayorder }">
				<input type="text" size="3" name="displayorder" value="${flBean.displayorder }">
			</td>
			<td class="altbg1">
				<input type="hidden" name="name_h" value="${flBean.name }">
				<input type="text" size="15" name="name" value="${flBean.name }" maxlength="100">
			</td>
			<td class="altbg2">
				<input type="hidden" name="url_h" value="${flBean.url }">
				<input type="text" size="15" name="url" value="${flBean.url }" maxlength="100">
			</td>
			<td class="altbg1">
				<input type="hidden" name="description_h" value="${flBean.description }">
				<input type="text" size="15" name="description" value="${flBean.description }">
			</td>
			<td class="altbg2">
				<input type="hidden" name="logo_h" value="${flBean.logo }">
				<input type="text" size="15" name="logo" value="${flBean.logo }" maxlength="100">
			</td>
		</tr>
	</c:forEach>
		<tr class="altbg1" align="center">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" size="3" name="newdisplayorder"></td>
			<td><input type="text" size="15" name="newname"></td>
			<td><input type="text" size="15" name="newurl"></td>
			<td><input type="text" size="15" name="newdescription"></td>
			<td><input type="text" size="15" name="newlogo"></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="forumlinksubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />