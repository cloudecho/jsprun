<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_secqaa" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=secqaa">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
			<tr><td><ul><li><bean:message key="a_setting_secqaa_tips" /></li></ul></td></tr>
		</tbody>
	</table>
	<br />
	<a name="45bd774b0690a1c8"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_secqaa" /><a href="###" onclick="collapse_change('45bd774b0690a1c8')"><img id="menuimg_45bd774b0690a1c8" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_45bd774b0690a1c8" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_secqaa_status" /></b><br /><span class="smalltxt"><bean:message key="a_setting_secqaa_status_comment" /></span></td>
				<td class="altbg2">
					<input class="checkbox" type="checkbox" name="status0" value="1"${status0}> <bean:message key="a_setting_seccodestatus_register" /><br />
					<input class="checkbox" type="checkbox" name="status1" value="2"${status1}> <bean:message key="a_setting_seccodestatus_post" /><br />
					<input class="checkbox" type="checkbox" name="status2" value="4"${status2}> <bean:message key="pm_send" /><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_secqaa_minposts" /></b><br /><span class="smalltxt"><bean:message key="a_setting_secqaa_minposts_comment" /></span></td>
				<td class="altbg2"><input type="text" size="50" name="minposts" value="${secqaa.minposts }"></td>
			</tr>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="3"><bean:message key="a_setting_secqaa_qaa" /></td></tr>
		<tr class="category">
			<td><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form, 'delete')"><bean:message key="del" /></td>
			<td><bean:message key="a_setting_secqaa_question" /></td>
			<td><bean:message key="a_setting_secqaa_answer" /></td>
		</tr>
		<c:forEach items="${itempools}" var="itempool">
		<tr align="center"><td class="altbg1" ><input type="hidden" name="ids" value="${itempool.id}"><input class="checkbox" type="checkbox" name="delete" value="${itempool.id}"></td><td class="altbg1"><textarea name="question[${itempool.id}]" rows="3" cols="60"><jrun:dhtmlspecialchars value="${itempool.question}"/></textarea></td><td class="altbg2"><input type="text" name="answer[${itempool.id}]" size="30" maxlength="50" value="${itempool.answer}"></td></tr>
		</c:forEach>
		<c:if test="${!empty multi.multipage}"><tr><td colspan="5">${multi.multipage}</td></tr></c:if>
		<tbody id="secqaabody"><tr align="center"><td class="altbg1"><bean:message key="add_new" /><a href="###" onclick="newnode = $('secqaabodyhidden').firstChild.cloneNode(true); $('secqaabody').appendChild(newnode)">[+]</a></td><td class="altbg1"><textarea name="newquestions" rows="3" cols="60"></textarea></td><td class="altbg2"><input type="text" name="newanswers" size="30" maxlength="50"></td></tr></tbody><tbody id="secqaabodyhidden" style="display:none"><tr align="center"><td class="altbg1"></td><td class="altbg1"><textarea name="newquestions" rows="3" cols="60"></textarea></td><td class="altbg2"><input type="text" name="newanswers" size="30" maxlength="50"></td></tr></tbody>
		<tr><td colspan=3><bean:message key="a_setting_secqaa_comment" /></td></tr>
	</table>
	<br />
	<center>
		<input type="hidden" name="from" value="${from}">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />