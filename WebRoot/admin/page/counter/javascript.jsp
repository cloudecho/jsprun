<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_javascript" /></td></tr>
</table>
<br />
<a name="ace21f06b2c1d25b"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="2">
			<bean:message key="menu_tool_javascript" />
			<a href="###" onclick="collapse_change('ace21f06b2c1d25b')"><img id="menuimg_ace21f06b2c1d25b" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" />
			</a>
		</td>
	</tr>
	<tbody id="menu_ace21f06b2c1d25b" style="display: yes">
		<tr>
			<td class="altbg2">
				<a href="admincp.jsp?action=gojssetting"><bean:message key="header_basic" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="admincp.jsp?action=jswizard"><bean:message key="a_system_js_project" /></a>
			</td>
		</tr>
	</tbody>
</table>
<br />
<a name="744dc8cc14392c19"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="2">
			<bean:message key="a_system_js_addjs" />
			<a href="###" onclick="collapse_change('744dc8cc14392c19')"><img id="menuimg_744dc8cc14392c19" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" />
			</a>
		</td>
	</tr>
	<tbody id="menu_744dc8cc14392c19" style="display: yes">
		<tr>
			<td class="altbg2">
				<input type="button" class="button" value="<bean:message key="a_system_js_threads" />" onclick="location.href='admincp.jsp?action=jswizard&jsthreads=yes'">
				&nbsp;&nbsp;
				<input type="button" class="button" value="<bean:message key="a_system_js_forums" />" onclick="location.href='admincp.jsp?action=jswizard&jsforums=yes'">
				&nbsp;&nbsp;
				<input type="button" class="button" value="<bean:message key="a_system_js_memberrank" />" onclick="location.href='admincp.jsp?action=jswizard&jsmemberrank=yes'">
				&nbsp;&nbsp;
				<input type="button" class="button" value="<bean:message key="stats" />" onclick="location.href='admincp.jsp?action=jswizard&jsstats=yes'">
				&nbsp;&nbsp;
				<input type="button" class="button" value="<bean:message key="a_system_js_images" />" onclick="location.href='admincp.jsp?action=jswizard&jsimages=yes'">
				&nbsp;&nbsp;
				<input type="button" class="button" value="<bean:message key="custom" />" onclick="location.href='admincp.jsp?action=jswizard&jscustom=yes'">
				&nbsp;&nbsp;
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=jswizard&editjswizard=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="3%">
				<input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form,'keyarray')">
			</td>
			<td>
				<bean:message key="a_system_js_key" />
			</td>
			<td>
				<bean:message key="type" />
			</td>
		</tr>
		<c:forEach items="${settinglist}" var="setting">
			<tr>
			<td class="altbg1">
				<input class="checkbox" type="checkbox" name="keyarray" value="${setting.identifier}">
			<td class="altbg2">
				<a href="admincp.jsp?action=jswizard&${setting.category}=yes&inentifier=${setting.identifier}">${setting.identifier}</a>
			</td>
			<td class="altbg1">
				${setting.type}
			</td>
		</tr>
		</c:forEach>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="jsdelsubmit" value="<bean:message key="a_system_js_delete" />">
		&nbsp; &nbsp;
		<input class="button" type="submit" name="jsexportsubmit" value="<bean:message key="a_system_js_download" />">
	</center>
</form>

<br />
<form method="post" action="admincp.jsp?action=jswizard&editjswizard=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td>
				<bean:message key="a_system_js_import_stick" />
			</td>
		</tr>
		<tr>
			<td class="altbg1">
				<div align="center">
					<textarea name="importdata" cols="80" rows="8"></textarea>
					<br />
					<input class="checkbox" type="radio" name="importrewrite" value="0" checked> <bean:message key="a_system_js_import_default" />&nbsp;
					<input class="checkbox" type="radio" name="importrewrite" value="1"> <bean:message key="a_system_js_import_norewrite" />&nbsp;
					<input class="checkbox" type="radio" name="importrewrite" value="2"> <bean:message key="a_system_js_import_rewrite" />
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="importsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />