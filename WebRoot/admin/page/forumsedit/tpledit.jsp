<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="a_forum_templates_edit"/></td></tr>
</table>
<br />
<script language="JavaScript">
var n = 0;
function displayHTML(obj) {
	win = window.open(" ", 'popup', 'toolbar = no, status = no, scrollbars=yes');
	win.document.write("" + obj.value + "");
}
function HighlightAll(obj) {
	obj.focus();
	obj.select();
	if(document.all) {
		obj.createTextRange().execCommand("Copy");
		window.status = '<bean:message key="a_forum_templates_edit_clickboard"/>';
		setTimeout("window.status=''", 1800);
	}
}
function findInPage(obj, str, noalert) {
	var txt, i, found;
	if(str == "") {
		return false;
	}
	if(document.layers) {
		if(!obj.find(str)) {
			while(obj.find(str, false, true)) {
				n++;
			}
		} else {
			n++;
		}
		if(n == 0 && !noalert) {
			alert('<bean:message key="a_forum_templates_edit_keyword_not_found"/>');
		}
	}
	if(document.all) {
		txt = obj.createTextRange();
		for(i = 0; i <= n && (found = txt.findText(str)) != false; i++) {
			txt.moveStart('character', 1);
			txt.moveEnd('textedit');
		}
		if(found) {
			txt.moveStart('character', -1);
			txt.findText(str);
			txt.select();
			txt.scrollIntoView();
			n++;
			return true;
		} else {
			if(n > 0) {
				n = 0;
				findInPage(obj, str, noalert);
			} else if(!noalert) {
				alert('<bean:message key="a_forum_templates_edit_keyword_not_found"/>');
			}
		}
	}
	return false;
}

</script>
<form method="post" action="admincp.jsp?action=modtpledit&templateid=${template.templateid}&fn=${fn}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="keyword" value="${keyword}">
	<input type="hidden" name="isLang" value="${isLang}">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><bean:message key="a_forum_templates_edit"/> - ${template.name} ${fn} - <bean:message key="a_forum_filecheck_filemtime"/>: ${filemtime}</td></tr>
		<c:if test="${isLang}">
		<tr>
			<td class="altbg1" align="center">
				<div align="center">
					<div style="overflow-x: hidden;overflow-y: scroll; width:100%; height: 300px">
						<table width="100%" border="0">
							<c:forEach items="${langs}" var="lang">
								<tr><td colspan="2"><b>${lang.key}</b></td></tr>
								<c:forEach items="${lang.value}" var="prop">
									<tr>
										<td width="100" style="border:0">${prop.key}</td>
										<td style="border:0"><textarea cols="100" rows="3" name="lang[${lang.key}][${prop.key}]" style="width: 95%;">${prop.value}</textarea></td>
									</tr>
								</c:forEach>
							</c:forEach>
						</table>
					</div>
				</div>
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="altbg1" align="center">
				<div align="center">
					<c:if test="${!isLang}"><textarea cols="100" rows="25" name="content" style="width: 95%;">${content}</textarea><br /></c:if>
					<input name="search" type="text" accesskey="t" size="20" onChange="n=0;">
					<input class="button" type="button" value="<bean:message key="search"/>" accesskey="f" onClick="findInPage(this.form.content, this.form.search.value)">&nbsp;&nbsp;&nbsp;
					<input class="button" type="button" value="<bean:message key="return"/>" accesskey="e" onClick="location.href='admincp.jsp?action=templates&edit=${template.templateid}&keyword=${keyword}'">
					<input class="button" type="button" value="<bean:message key="preview"/>" accesskey="p" onClick="displayHTML(this.form.content)">
					<input class="button" type="button" value="<bean:message key="copy"/>" accesskey="c" onClick="HighlightAll(this.form.content)">
					<c:if test="${settings.admincp_tpledit>0}">
					<c:if test="${template.templateid==1}"><input class="button" type="submit" name="editsubmit" onclick="return confirm('<bean:message key="a_forum_templates_edit_default_overwriteconfirm"/>')" value="<bean:message key="submit"/>"></c:if>
					<c:if test="${template.templateid!=1}"><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>"></c:if><br />
					<bean:message key="a_forum_templates_copyto_otherdirs"/>
					<select id="copyto" style="vertical-align: middle">
						<c:forEach items="${templates}" var="temp"><option value="${temp.templateid}">${temp.directory}</option></c:forEach>
					</select>
					<input style="vertical-align: middle" class="button" type="button" value="<bean:message key="a_forum_templates_start_copy"/>" accesskey="r" onclick="if($('copyto').value == 1 && confirm('<bean:message key="a_forum_templates_edit_default_overwriteconfirm"/>') || $('copyto').value != 1) location.href='forumsedit.do?action=tpledit&templateid=${template.templateid}&keyword=${keyword}&fn=${fn}&copytoid='+$('copyto').value+'&copyto=yes'">
					<c:if test="${nosame}">
						<input class="button" style="vertical-align: middle" type="button" value="<bean:message key="a_forum_templates_reset"/>" accesskey="r" onclick="location.href='admincp.jsp?action=tpledit&templateid=${template.templateid}&keyword=${keyword}&fn=${fn}&reset=yes'">
						<input class="button" style="vertical-align: middle" type="button" value="<bean:message key="a_forum_templates_check"/>" onclick="location.href='admincp.jsp?action=tpledit&templateid=${template.templateid}&fn=${fn}&keyword=${keyword}&checktpl=yes'">
					</c:if>
					</c:if>
				</div>
			</td>
		</tr>
	</table>
</form>
<jsp:include page="../cp_footer.jsp" />