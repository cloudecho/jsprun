<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
	<form method="post" action="admincp.jsp?action=jspruncodes&batchchild=yes&edit=1">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<a name="620e7084dea6550a"></a>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="tableborder">
			<tr class="header">
				<td colspan="2">
					<bean:message key="a_post_jspruncodes_edit"/> - ${jspruncode.tag}
					
					<input type="hidden" name="jspruncodeID" value="${jspruncode.id}">
					
					<input type="hidden" name="available" value="${jspruncode.available}">
					
					<input type="hidden" name="icon" value="${jspruncode.icon}">
					<a href="###" onclick="collapse_change('620e7084dea6550a')"><img id="menuimg_620e7084dea6550a" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" />
					</a>
				</td>
			</tr>
			<tbody id="menu_620e7084dea6550a" style="display: yes">
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="a_post_jspruncodes_edit_tag"/></b>
						<br />
						<span class="smalltxt"><bean:message key="a_post_jspruncodes_edit_tag_comment"/></span>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="tagnew" value="${jspruncode.tag}" maxlength="100">
						${Referer}
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1" valign="top">
						<b><bean:message key="a_post_jspruncodes_edit_replacement"/></b>
						<br />
						<span class="smalltxt"><bean:message key="a_post_jspruncodes_edit_replacement_comment"/></span>
					</td>
					<td class="altbg2">
						<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('replacementnew', 1)">
						<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('replacementnew', 0)">
						<br />
						<textarea rows="6" name="replacementnew" id="replacementnew" cols="50" type="_moz">${jspruncode.replacement}</textarea>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="a_post_jspruncodes_edit_example"/></b> <br />
						<span class="smalltxt"><bean:message key="a_post_jspruncodes_edit_example_comment"/></span>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="examplenew" value="${jspruncode.example}" maxlength="255">
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="a_post_jspruncodes_edit_explanation"/></b> <br />
						<span class="smalltxt"><bean:message key="a_post_jspruncodes_edit_explanation_comment"/></span>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="explanationnew" value="${jspruncode.explanation}" >
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="a_post_jspruncodes_edit_params"/></b>
						<br />
						<span class="smalltxt"><bean:message key="a_post_jspruncodes_edit_params_comment"/></span>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="paramsnew" value="${jspruncode.params }" maxlength="1">
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1" valign="top">
						<b><bean:message key="a_post_jspruncodes_edit_prompt"/></b>
						<br />
						<span class="smalltxt"><bean:message key="a_post_jspruncodes_edit_prompt_comment"/></span>
					</td>
					<td class="altbg2">
						<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('promptnew', 1)">
						<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('promptnew', 0)">
						<br />
						<textarea rows="6" name="promptnew" id="promptnew" cols="50" type="_moz">${jspruncode.prompt}</textarea>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="a_post_jspruncodes_edit_nest"/></b> <br />
						<span class="smalltxt"><bean:message key="a_post_jspruncodes_edit_nest_comment"/></span>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="nestnew" value="${jspruncode.nest }" maxlength="1">
					</td>
				</tr>
			</tbody>
		</table>
		<br />
		<center>
			<input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>">
		</center>
	</form>
<jsp:directive.include file="../cp_footer.jsp" />