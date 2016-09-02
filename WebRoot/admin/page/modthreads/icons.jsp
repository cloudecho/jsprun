<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_post_icons"/></td></tr>
</table>
<br />
		<form method="post" action="admincp.jsp?action=icons&batch=yes">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
				<tr class="header">
					<td colspan="5">
						<bean:message key="a_post_smilies_edit_icon"/>
					</td>
				</tr>
				<tr align="center" class="category">
					<td width="50">
						<input type="checkbox" name="chkall" onclick="checkall(this.form, 'delete')" class="checkbox">
						<bean:message key="del"/>
					</td>
					<td>
						<bean:message key="display_order"/>
					</td>
					<td colspan="2">
						<bean:message key="filename"/>
					</td>
					<td>
						<bean:message key="a_post_smilies_edit_image"/>
					</td>
				</tr>
				<c:forEach var="icon" items="${dateList}">
					<tr align="center">
						<td class="altbg1">
							<input class="checkbox" type="checkbox" name="delete[]" value="${icon.id}" />
						</td>
						<td class="altbg2">
							<input type="text" size="2" name="displayorder[${icon.id}]" value="${icon.displayorder}" maxlength="2" onchange="updateicons.value=updateicons.value+this.name+','" />
						</td>
						<td class="altbg1" colspan="2">
							${icon.url}
						</td>
						<td class="altbg2">
							<img src="images/icons/${icon.url}" />
						</td>
					</tr>
				</c:forEach>
			</table>
			<br />
			<center>
				<input type="hidden" name="updateicons" value="" />
				<input class="button" type="submit" name="iconsubmit" value="<bean:message key="submit"/>" />
			</center>
		</form>
		<br />
		<form method="post" action="admincp.jsp?action=icons&add=yes">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
				<tr class="header">
					<td colspan="5">
						<bean:message key="a_post_icon_add"/>
					</td>
				</tr>
				<tr align="center" class="category">
					<td width="50">
						<input type="checkbox" name="chkall2" onclick="checkall(this.form, 'addcheck', 'chkall2')" class="checkbox">
						<bean:message key="enabled"/>
					</td>
					<td>
						<bean:message key="display_order"/>
					</td>
					<td colspan="2">
						<bean:message key="filename"/>
					</td>
					<td>
						<bean:message key="a_post_smilies_edit_image"/>
					</td>
				</tr>
				<c:if test="${!empty fileIcons}">
					<bean:size id="size" name="fileIcons" collection="${fileIcons}"/>
					<input type="hidden" name="counts" value="${count}">
					<c:forEach var="icon" items="${fileIcons}" varStatus="fi">
						<tr align="center">
							<td class="altbg1">
								<input type="checkbox" name="addcheck[]" class="checkbox" value="${fi.count}">
							</td>
							<td class="altbg2">
								<input type="text" size="2" name="adddisplayorder[${fi.count}]" value="0" maxlength="2">
							</td>
							<td class="altbg1" colspan="2">
								<input type="text" size="35" name="addurl[${fi.count}]" value="${icon.url}" readonly />
							</td>
							<td class="altbg2">
								<img src="images/icons/${icon.url}">
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			<center>
				<input class="button" type="submit" name="iconsubmit" value="<bean:message key="submit"/>">
			</center>
			<br />
		</form>
<jsp:directive.include file="../cp_footer.jsp" />