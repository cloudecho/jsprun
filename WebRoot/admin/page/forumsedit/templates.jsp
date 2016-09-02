<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_style_templates"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=templates">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="48"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form)"><bean:message key="del"/></td>
			<td><bean:message key="a_forum_templates_name"/></td>
			<td><bean:message key="ubiety_directory"/></td>
			<td><bean:message key="copyright"/></td>
			<td><bean:message key="edit"/></td>
		</tr>
		<c:forEach items="${templates}" var="template">
		<tr align="center">
			<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" ${template.templateid==1?"disabled":""} value="${template.templateid}"></td>
			<td class="altbg2"><input type="text" size="25" name="name[${template.templateid}]" maxlength="30" value="${template.name}"></td>
			<td class="altbg1"><input type="text" size="25" name="directory[${template.templateid}]" maxlength="100" value="${template.directory}"></td>
			<td class="altbg2">${template.copyright}</td>
			<td class="altbg1"><a href="admincp.jsp?action=templates&edit=${template.templateid}">[<bean:message key="detail"/>]</a></td>
		</tr>
		</c:forEach>
		<tr align="center" class="altbg1">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" size="25" name="newname" maxlength="30"></td>
			<td><input type="text" size="25" name="newdirectory" maxlength="100"></td>
			<td><input type="text" size="25" name="newcopyright" maxlength="100"></td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="tplsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />