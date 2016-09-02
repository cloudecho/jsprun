<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_language" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr><td><ul>
			<li><bean:message key="a_setting_language_set_comment1" /></li>
			<li><bean:message key="a_setting_language_set_comment2" arg0="WEB-INF\\classes\\cn\\jsprun\\struts\\"/></li>
		</ul></td></tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=settings&do=language">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="6"><bean:message key="a_setting_language_edit" /></td></tr>
		<tr align="center" class="category">
			<td><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form)"><bean:message key="del" /></td>
			<td><bean:message key="display_order" /></td>
			<td><bean:message key="available" /></td>
			<td><bean:message key="a_setting_language_name" /></td>
			<td><bean:message key="a_setting_language_id" /></td>
			<td><bean:message key="default" /></td>
		</tr>
		<c:forEach items="${languages}" var="language">
			<tr align="center">
				<td class="altbg1">
					<input type="hidden" name="id" value="${language.key}">
					<input class="checkbox" type="checkbox" name="delete" value="${language.key}">
				</td>
				<td class="altbg2"><input type="text" size="3" name="displayorder_${language.key}" value="${language.key}"></td>
				<td class="altbg1"><input class="checkbox" type="checkbox" name="available_${language.key}" ${language.value.available>0 ? "checked" : ""} value="1"></td>
				<td class="altbg2"><input type="text" size="20" name="name_${language.key}" value="<jrun:dhtmlspecialchars value="${language.value.name}"/>" maxlength="10"></td>
				<td class="altbg1"><input type="text" size="20" name="language_${language.key}" value="${language.value.language}" maxlength="10"></td>
				<td class="altbg2"><input class="radio" type="radio" name="default" value="${language.key}" ${language.value.default>0 ? "checked" : ""}></td>
			</tr>
		</c:forEach>
		<tr class="altbg1" align="center">
			<td><bean:message key="add_new" /></td>
			<td><input type="text" size="3" name="newdisplayorder"></td>
			<td><input class="checkbox" type="checkbox" name="newavailable" checked value="1"></td>
			<td><input type="text" size="20" name="newname"></td>
			<td><input type="text" size="20" name="newlanguage"></td>
			<td></td>
		</tr>
		<tr class="altbg1">
			<td colspan="6"><bean:message key="a_setting_language_set_comment3" /></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="languagesubmit" value="<bean:message key="submit" />"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />