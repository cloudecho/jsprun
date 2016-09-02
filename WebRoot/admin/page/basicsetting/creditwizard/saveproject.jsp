<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="project_scheme_add" /></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=settings&do=credits">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="project_scheme_save" />
				<a href="###" onclick="collapse_change('e524b9c0c96b7418')">
				<img id="menuimg_e524b9c0c96b7418" src="${pageContext.request.contextPath}/images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" />
				</a>
			</td>
		</tr>
		<tbody id="menu_e524b9c0c96b7418" style="display: yes">
			<c:if test="${project!=null}">
				<input type="hidden" name="projectid" value="${project.id }">
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="project_scheme_cover" /></b>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="coverwith" value="1">
						<bean:message key="yes" /> &nbsp; &nbsp;
						<input class="radio" type="radio" name="coverwith" value="0" checked>
						<bean:message key="no" />
					</td>
				</tr>
			</c:if>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="project_scheme_option" /></b>
					<br />
					<span class="smalltxt"><bean:message key="project_scheme_option_comment" /></span>
				</td>
				<td class="altbg2">
					<select name="savemethod" size="10" multiple="multiple"
						style="width: 80%">
						<option value="all">
							<bean:message key="all" />
						</option>
						<option value="1" ${saveMethodVO.projectSetting==1?'selected':'' }>
							<bean:message key="a_setting_step_menu_1" />
						</option>
						<option value="2" ${saveMethodVO.expressionsSetting==1?'selected':'' }>
							<bean:message key="a_setting_step_menu_2" />
						</option>
						<option value="3" ${saveMethodVO.useSetting==1?'selected':'' }>
							<bean:message key="a_setting_step_menu_3" />
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="project_scheme_title" /></b>
					<br />
					<span class="smalltxt"><bean:message key="project_scheme_title_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="name" value="${project.name }" maxlength="50">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="project_scheme_description" /></b>
					<br />
					<span class="smalltxt"><bean:message key="project_scheme_description_comment" /></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 0)">
					<br />
					<textarea rows="6" name="description" id="description" cols="50" onKeyDown='if (this.value.length>=255){event.returnValue=false}'>${project.description }</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="addsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../../cp_footer.jsp" />