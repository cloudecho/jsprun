<%@ page language="java"  pageEncoding="UTF-8"%>
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
				<c:if test="${status==1}"><a href="admincp.jsp?action=jswizard"><bean:message key="a_system_js_project" /></a></c:if>
			</td>
		</tr>
	</tbody>
</table>
<br />
<a name="744dc8cc14392c19"></a>
<form method="post" action="admincp.jsp?action=jssetting">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="2">
			<bean:message key="a_setting_javascript" />
			<a href="###" onclick="collapse_change('b3ef2d8de937b768')"><img id="menuimg_b3ef2d8de937b768" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" />
			</a>
		</td>
	</tr>
	<tbody id="menu_b3ef2d8de937b768" style="display: yes">
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_jsstatus" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_jsstatus_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="jsstatus" value="1" ${status==1?'checked':''} onclick="$('hidden_settings_jsstatus').style.display = '';">
				<bean:message key="yes" /> &nbsp; &nbsp;
				<input class="radio" type="radio" name="jsstatus" value="0" ${status==0?'checked':''} onclick="$('hidden_settings_jsstatus').style.display = 'none';">
				<bean:message key="no" />
			</td>
		</tr>
	</tbody>
	<tbody class="sub" id="hidden_settings_jsstatus" style="display: ${status==0?'none':''}">
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_jscachelife" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_jscachelife_comment" /></span>
			</td>
			<td class="altbg2">
				<input type="text" size="50" name="jscachelife" value="${cacle}">
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_jsdateformat" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_jsdateformat_comment" /></span>
			</td>
			<td class="altbg2">
				<input type="text" size="50" name="jsdateformat" value="${format}">
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" valign="top">
				<b><bean:message key="a_setting_jsrefdomains" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_jsrefdomains_comment" /></span>
			</td>
			<td class="altbg2">
				<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('jsrefdomains', 1)">
				<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('jsrefdomains', 0)">
				<br />
				<textarea rows="6" name="jsrefdomains" id="jsrefdomains" cols="50">${refdomain}</textarea>
			</td>
		</tr>
	</tbody>
</table>
<br />
<center>
	<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />