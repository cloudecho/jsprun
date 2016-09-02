<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_scheme" /></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=editproject">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><bean:message key="a_system_project_forum_scheme_sort" /></td></tr>
		<tr>
			<td>
				<input class="button" type="button" value="<bean:message key="a_system_project_forum_scheme" />" onclick="window.location='admincp.jsp?action=project&type=forum';">&nbsp;
				<input class="button" type="button" value="<bean:message key="a_system_project_group_scheme" />" onclick="window.location='admincp.jsp?action=project&type=group';">&nbsp;
				<input class="button" type="button" value="<bean:message key="a_system_project_extcredit_scheme" />" onclick="window.location='admincp.jsp?action=project&type=extcredit';">&nbsp;
			</td>
		</tr>
	</table>
	<br />
	${multi.multipage}
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td width="5%"><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form)"><bean:message key="del" /></td><td width="20%"><bean:message key="name" /></td><td width="15%"><bean:message key="type" /></td><td width="40%"><bean:message key="description" /></td><td width="10%"><bean:message key="export" /></td><td width="10%"><bean:message key="detail" /></td></tr>
		<c:forEach items="${projects}" var="project">
			<tr align="center">
				<td class="altbg2"><input type="hidden" size="15" name="ids" value="${project.id}"><input class="checkbox" type="checkbox" name="delete" value="${project.id}"></td>
				<td class="altbg2"><input type="text" size="15" name="name[${project.id}]" value="${project.name}"></td>
				<td class="altbg2">
					<c:choose>
						<c:when test="${project.type=='forum'}"><bean:message key="a_system_project_forum_scheme" /></c:when>
						<c:when test="${project.type=='extcredit'}"><bean:message key="a_system_project_extcredit_scheme" /></c:when>
						<c:when test="${project.type=='group'}"><bean:message key="a_system_project_group_scheme" /></c:when>
					</c:choose>
				</td>
				<td class="altbg2"><input type="text" size="40" name="description[${project.id}]" value="${project.description}"></td>
				<td class="altbg2">[<a href="admincp.jsp?action=project&export=${project.id}"><bean:message key="download" /></a>]</td>
				<td class="altbg2">[<a href="admincp.jsp?action=projectapply&projectid=${project.id}&type=${project.type}"><bean:message key="apply" /></a>]</td>
			</tr>
		</c:forEach>
	</table>
	${multi.multipage}<br/>
	<center><input class="button" type="submit" name="projectsubmit" value="<bean:message key="submit" />"></center>
</form>
<br />
<form method="post" action="admincp.jsp?action=editproject">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><bean:message key="a_system_project_import_stick" /></td></tr>
		<tr><td class="altbg1"><div align="center"><textarea name="projectdata" cols="80" rows="8"></textarea></div></td></tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="importsubmit" value="<bean:message key="submit" />"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />