<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="a_system_project_global_forum" /></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=editprojectapply&projectid=${projectid}&type=${type}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="43528e1cfd2b5f9e"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_system_project_forum_scheme" /><a href="###" onclick="collapse_change('43528e1cfd2b5f9e')"><img id="menuimg_43528e1cfd2b5f9e" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_43528e1cfd2b5f9e" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="project_scheme_title" /></b>
					<br />
					<span class="smalltxt"><bean:message key="project_scheme_title_comment" /></span>
				</td>
				<td class="altbg2">
					<select name="projectid">
						<option value="0" selected="selected"><bean:message key="none" /></option>
						<c:forEach items="${projects}" var="project">
							<option value="${project.id}" ${project.id==projectid?"selected":""}>${project.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${type=='forum'}">
						<td width="45%" class="altbg1">
							<b><bean:message key="a_system_project_target_forum" /></b>
							<br />
							<span class="smalltxt"><bean:message key="a_forum_copy_target_comment" /></span>
						</td>
						<td class="altbg2">
							<select name="targetid" size="10" multiple="multiple" style="width: 80%">
								${forumselect}
							</select>
						</td>
					</c:when>
					<c:otherwise>
						<td width="45%" class="altbg1"><b><bean:message key="a_system_project_target_usergroup" /></b></td>
						<td class="altbg2">
							<select name="targetid" size="10" multiple="multiple" style="width: 80%">
							<c:forEach items="${usergroups}" var="group">
								<option value="${group.groupid}">${group.grouptitle}</option>
							</c:forEach>
							</select>
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</tbody>
	</table>
	<br />
	<center><input class="button" type="submit" name="applysubmit" value="<bean:message key="submit" />"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />