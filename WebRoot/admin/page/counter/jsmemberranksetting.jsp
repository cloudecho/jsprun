<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_javascript" /></td></tr>
</table>
<br />
	<a name="ace21f06b2c1d25b"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="menu_tool_javascript" /> <a href="###" onclick="collapse_change('ace21f06b2c1d25b')"><img id="menuimg_ace21f06b2c1d25b" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" style="display : ${diaplay==null?'none' : ''}">
			<tr class="header">
				<td colspan="2">
					<bean:message key="preview" /> <a href="###" onclick="collapse_change('0896486085a06b32')"><img id="menuimg_0896486085a06b32" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
				</td>
			</tr>
		<tbody id="menu_0896486085a06b32" style="display:yes">
			<tr>
				<td class="altbg1">
					<textarea rows="3" style="width: 100%; word-break: break-all" onMouseOver="this.focus()" onFocus="this.select()" type="_moz">&lt;script language=&quot;JavaScript&quot; src=&quot;${boardurl}api/javascript.jsp?key=${inentifier==null?jsname: inentifier}&quot;&gt;&lt;/script&gt;</textarea>
					<div class="jswizard"><script type="text/javascript" src="${path}"></script></div><br />
				</td>
			</tr>
		</tbody>
	</table>
	<br />
<form method="post" action="admincp.jsp?action=jswizard&editjsmembers=yes">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<a name="cbdea6df76e91c50"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="2">
			<bean:message key="a_system_js_jstemplate" /> <a href="###" onclick="collapse_change('cbdea6df76e91c50')"><img id="menuimg_cbdea6df76e91c50" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
		</td>
	</tr>
	<tbody id="menu_cbdea6df76e91c50" style="display: yes">
		<tr>
			<td class="altbg1" colspan="2">
				<bean:message key="a_system_js_forums_jstemplate_comment" />
				<a href="###" onclick="insertunit('(member)')">(member)</a>
				<bean:message key="a_system_js_forums_jstemplate_comment10" />
				<a href="###" onclick="insertunit('(avatar)')">(avatar)</a>
				<bean:message key="a_system_js_forums_jstemplate_comment11" />
				<a href="###" onclick="insertunit('(regdate)')">(regdate)</a>
				<bean:message key="a_system_js_forums_jstemplate_comment12" />
				<a href="###" onclick="insertunit('(value)')">(value)</a>
				<bean:message key="a_system_js_forums_jstemplate_comment13" />
				<br />
				<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('jstemplate', 1)">
				<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('jstemplate', 0)">
				<br />
<script>
function isUndefined(variable) {
	return typeof variable == 'undefined' ? true : false;
}

function insertunit(text) {
	$('jstemplate').focus();
	if(!isUndefined($('jstemplate').selectionStart)) {
		var opn = $('jstemplate').selectionStart + 0;
		$('jstemplate').value = $('jstemplate').value.substr(0, $('jstemplate').selectionStart) + text + $('jstemplate').value.substr($('jstemplate').selectionEnd);
	} else if(document.selection && document.selection.createRange) {
		var sel = document.selection.createRange();
		sel.text = text.replace(/\r?\n/g, '\r\n');
		sel.moveStart('character', -strlen(text));
	} else {
		$('jstemplate').value += text;
	}
}
</script>
	<c:choose>
		<c:when test="${inentifier==null}">
			<textarea cols="100" rows="5" id="jstemplate" name="parameter[jstemplate]" style="width: 95%;" type="_moz">${resultmap.parameter.jstemplate}</textarea>
		</c:when>
		<c:otherwise>
			<textarea cols="100" rows="5" id="jstemplate" name="parameter[jstemplate]" style="width: 95%;" type="_moz">(member)<br /></textarea>
		</c:otherwise>
	</c:choose>
	</td>
</tr>
</tbody>
</table>
<br />
<a name="896da57ae8b5ccf4"></a>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="2">
			<bean:message key="a_system_js_memberrank" /><a href="###" onclick="collapse_change('896da57ae8b5ccf4')"><img id="menuimg_896da57ae8b5ccf4" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
		</td>
	</tr>
	<tbody id="menu_896da57ae8b5ccf4" style="display: yes">
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_jskey" /></b><br /><span class="smalltxt"><bean:message key="a_system_js_jskey_comment" /></span>
			</td>
			<td class="altbg2">
				<c:choose>
					<c:when test="${inentifier==null}">
						<input type="text" size="50" name="jskey" value="${jsname}">
					</c:when>
					<c:otherwise>
						<input type="text" size="50" name="jskey" value="${inentifier}">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_cachelife" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_system_js_cachelife_comment" /></span>
			</td>
			<td class="altbg2">
				<input type="text" size="50" name="parameter[cachelife]" value="${resultmap.parameter.cachelife}">
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_startrow" /></b><br />
				<span class="smalltxt"><bean:message key="a_system_js_threads_startrow_comment" /></span>
			</td>
			<td class="altbg2">
				<c:choose>
					<c:when test="${inentifier!=null}">
						<input type="text" size="50" name="parameter[startrow]" value="0">
					</c:when>
					<c:otherwise>
						<input type="text" size="50" name="parameter[startrow]" value="${resultmap.parameter.startrow}">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_images_items" /></b><br /><span class="smalltxt"><bean:message key="a_system_js_memberrank_items_comment" /></span>
			</td>
			<td class="altbg2">
				<c:choose>
					<c:when test="${inentifier!=null}">
						<input type="text" size="50" name="parameter[items]" value="10">
					</c:when>
					<c:otherwise>
						<input type="text" size="50" name="parameter[items]" value="${resultmap.parameter.items}">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_newwindow" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_newwindow_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[newwindow]" value="0" ${resultmap.parameter.newwindow=='0'?'checked':''}>
				<bean:message key="a_system_js_newwindow_self" /><br />
				<input class="radio" type="radio" name="parameter[newwindow]" value="1" ${empty resultmap||resultmap.parameter.newwindow=='1'?'checked':''}>
				<bean:message key="a_system_js_newwindow_blank" /><br />
				<input class="radio" type="radio" name="parameter[newwindow]" value="2" ${resultmap.parameter.newwindow=='2'?'checked':''}>
				<bean:message key="a_system_js_newwindow_main" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_memberrank_orderby" /></b><br />
				<span class="smalltxt"><bean:message key="a_system_js_memberrank_orderby_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[orderby]"	value="credits" ${empty resultmap||resultmap.parameter.orderby=='credits'?'checked':''}>
				<bean:message key="a_system_js_memberrank_orderby_credits" /><br />
				<input class="radio" type="radio" name="parameter[orderby]"	value="posts" ${resultmap.parameter.orderby=='posts'?'checked':''}>
				<bean:message key="a_system_js_memberrank_orderby_posts" /><br />
				<input class="radio" type="radio" name="parameter[orderby]" value="digestposts" ${resultmap.parameter.orderby=='digestposts'?'checked':''}>
				<bean:message key="a_system_js_memberrank_orderby_digestposts" /><br />
				<input class="radio" type="radio" name="parameter[orderby]" value="regdate" ${resultmap.parameter.orderby=='regdate'?'checked':''}>
				<bean:message key="a_system_js_memberrank_orderby_regdate" /><br />
				<input class="radio" type="radio" name="parameter[orderby]" value="todayposts" ${resultmap.parameter.orderby=='todayposts'?'checked':''}>
				<bean:message key="a_system_js_memberrank_orderby_todayposts" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_charset" /></b><br />
				<span class="smalltxt"><bean:message key="a_system_js_charsetr_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[jscharset]" value="0" ${resultmap.parameter.jscharset=='0'||resultmap==null?'checked':'' }>
				<bean:message key="none" />
				<br /><input class="radio" type="radio" name="parameter[jscharset]" value="1" ${resultmap.parameter.jscharset=='1'?'checked':'' } >
				${charset}<br />
			</td>
		</tr>
	</tbody>
</table>
	<input type="hidden" name="edit" value="${jsname}">
	<br />
	<center>
		<input class="button" type="submit" name="jssubmit" value="<bean:message key="a_system_js_preview" />">&nbsp; &nbsp;
		<input class="button" type="button" onclick="this.form.preview.value=0;this.form.jssubmit.click()" value="<bean:message key="submit" />">
		<input name="preview" type="hidden" value="1">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />