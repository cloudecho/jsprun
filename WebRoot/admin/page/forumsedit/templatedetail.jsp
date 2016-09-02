<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="a_forum_templates_maint"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td colspan="3"><bean:message key="a_forum_templates_maint"/> - ${template.name}</td></tr>
	<form method="post" action="admincp.jsp?action=tpladd&edit=${template.templateid}">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<tr class="altbg2">
			<td width="25%"><bean:message key="a_forum_templates_maint_new"/></td>
			<td width="55%"><input type="text" name="name" size="40" maxlength="40"></td>
			<td width="20%"><input class="button" type="submit" value="<bean:message key="submit"/>"></td>
		</tr>
	</form>
	<form method="post" action="admincp.jsp?action=templates&edit=${template.templateid}">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<tr class="altbg1">
			<td><bean:message key="a_forum_templates_maint_search"/></td>
			<td><input type="text" name="keyword" size="40"></td>
			<td><input class="button" type="submit" value="<bean:message key="submit"/>"></td>
		</tr>
	</form>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td>
		<c:choose>
			<c:when test="${template.templateid==1}">
				<bean:message key="a_forum_templates_edit_default_comment"/>
			</c:when>
			<c:otherwise>
				<bean:message key="a_forum_templates_edit_nondefault_comment"/>
			</c:otherwise>
		</c:choose>
			</td>
		</tr>
	</tbody>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><bean:message key="a_forum_templates_select"/> <c:if test="${keyword !=''}">- <bean:message key="a_forum_templates_keyword"/> <i>${keyword}</i> - <a href="admincp.jsp?action=templates&edit=${template.templateid}">[<bean:message key="a_forum_templates_view_all"/>]</a></c:if></td></tr>
	<tr class="altbg1">
		<td>
			<ul>
				<li><b>JspRun! <bean:message key="a_forum_templates_language_pack"/></b>
				<c:forEach items="${languageFiles}" var="languagefile"><ul><li>${languagefile.key} &nbsp;<a href="admincp.jsp?action=tpledit&templateid=${template.templateid}&fn=${languagefile.value}">[<bean:message key="edit"/>]</a></ul></c:forEach>
			</ul>
			<ul>
				<li><b>JspRun! <bean:message key="a_forum_templates_html"/></b>
				<c:forEach items="${jspFiles}" var="jspFile">
					<ul>
						<li><b>${jspFile.key}</b>
						<ul>
							<c:forEach items="${jspFile.value}" var="name">
							<li>
							<font color="${colors[name.key]}">${name.key}</font> &nbsp;
							<c:choose>
								<c:when test="${settings.admincp_tpledit>0}">
								<a href="admincp.jsp?action=tpledit&templateid=${template.templateid}&fn=${name.value}&keyword=${keyword}&nosame=${colors[name.key]=='#FF0000'?'true':''}">[<bean:message key="edit"/>]</a>
								<a href="admincp.jsp?action=tpledit&templateid=${template.templateid}&fn=${name.value}&delete=yes">[<bean:message key="delete"/>]</a>
								<c:if test="${colors[name.key]=='#FF0000'}"><a href="admincp.jsp?action=tpledit&templateid=${template.templateid}&fn=${name.value}&reset=yes">[<bean:message key="a_forum_templates_reset"/>]</a></c:if>
								</c:when>
								<c:otherwise>
								<a href="admincp.jsp?action=tpledit&templateid=${template.templateid}&fn=${name.value}&keyword=${keyword}">[<bean:message key="view"/>]</a>
								</c:otherwise>
							</c:choose>
							</c:forEach>
						</ul>
					</ul>
				</c:forEach>
			</ul>
		</td>
	</tr>
</table>
<jsp:include page="../cp_header.jsp" />