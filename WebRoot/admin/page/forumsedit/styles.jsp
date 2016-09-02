<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_style"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td>
				<bean:message key="a_forum_styles_tips" arg0="${boardurl}"/>
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=styles">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input name="updatecsscache" type="hidden" value="0">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="48"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form, 'delete')"><bean:message key="del"/></td>
			<td><bean:message key="project_scheme_title"/></td>
			<td><bean:message key="available"/></td>
			<td>styleID</td>
			<td><bean:message key="a_forum_styles_template"/></td>
			<td><bean:message key="export"/></td>
			<td><bean:message key="edit"/></td>
		</tr>
	<c:forEach items="${forumStyles}" var="style">
		<tr align="center">
			<td class="altbg1"><c:if test="${style.styleid!=styleid}"><input class="checkbox" type="checkbox" name="delete" value="${style.styleid}"></c:if></td>
			<td class="altbg2"><input type="text" name="name[${style.styleid}]" value="${style.name}" size="18" maxlength="20"></td>
			<td class="altbg1"><input class="checkbox" type="${style.styleid!=styleid?'checkbox':'hidden'}" name="available[${style.styleid}]" ${(style.styleid!=styleid)&&style.available==1?"checked":""}  value="1"></td>
			<td class="altbg2">${style.styleid}</td>
			<td class="altbg1">${style.tplname}</td>
			<td class="altbg2"><a href="admincp.jsp?action=styles&export=${style.styleid}">[<bean:message key="download"/>]</a></td>
			<td class="altbg1"><a href="admincp.jsp?action=styles&edit=${style.styleid}">[<bean:message key="detail"/>]</a></td>
		</tr>
	</c:forEach>
		<tr align="center" class="altbg1">
			<td class="altbg1"><bean:message key="add_new"/></td>
			<td><input type='text' name="newname" size="18" maxlength="20"></td>
			<td colspan="6">&nbsp;</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="stylesubmit" value="<bean:message key="submit"/>">&nbsp;&nbsp;<input onclick="this.form.updatecsscache.value = 1" class="button" type="submit" name="stylesubmit" value="<bean:message key="a_forum_styles_csscache_update"/>"></center>
</form>
<br />
<form method="post" action="admincp.jsp?action=styles">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><bean:message key="a_forum_styles_import"/></td></tr>
		<tr>
			<td class="altbg1">
				<div align="center">
					<textarea name="styledata" cols="80" rows="8"></textarea>
					<br />
					<input class="checkbox" type="checkbox" name="ignoreversion" value="1"><bean:message key="a_forum_styles_import_ignore_version"/>
				</div>
			</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="importsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />