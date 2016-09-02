<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_creditwizard" /></td></tr>
</table>
<br />
<jsp:include page="lead.jsp"/>
<script>
function isUndefined(variable) {
	return typeof variable == 'undefined' ? true : false;
}

function insertunit(text, textend) {
	document.getElementById('creditsformulanew').focus();
	textend = isUndefined(textend) ? '' : textend;
	if(!isUndefined(document.getElementById('creditsformulanew').selectionStart)) {
		var opn = document.getElementById('creditsformulanew').selectionStart + 0;
		if(textend != '') {
			text = text + document.getElementById('creditsformulanew').value.substring(document.getElementById('creditsformulanew').selectionStart, document.getElementById('creditsformulanew').selectionEnd) + textend;
		}
		document.getElementById('creditsformulanew').value = document.getElementById('creditsformulanew').value.substr(0, document.getElementById('creditsformulanew').selectionStart) + text + document.getElementById('creditsformulanew').value.substr(document.getElementById('creditsformulanew').selectionEnd);
	} else if(document.selection && document.selection.createRange) {
		var sel = document.selection.createRange();
		if(textend != '') {
			text = text + sel.text + textend;
		}
		sel.text = text.replace(/\r?\n/g, '\r\n');
		sel.moveStart('character', -strlen(text));
	} else {
		document.getElementById('creditsformulanew').value += text;
	}
	formulaexp();
}
var formulafind = new Array('digestposts', 'posts', 'oltime', 'pageviews');
var formulareplace = new Array('<u><bean:message key="digestposts" /></u>','<u><bean:message key="posts" /></u>','<u><bean:message key="a_setting_creditsformula_oltime" /></u>','<u><bean:message key="pageviews" /></u>');
function formulaexp() {
	var result = document.getElementById('creditsformulanew').value;
	<c:forEach items="${extcredits}" var="ext" varStatus="ind">
		<c:set value="${ext.value}" var="k"></c:set>
		<c:if test="${empty k.title }">
			result = result.replace(/extcredits${ext.key}/g, '<u><bean:message key="a_setting_creditsformula_extcredits" />${ext.key}</u>');
		</c:if>
		<c:if test="${!empty k.title }">
			result = result.replace(/extcredits${ext.key}/g, '<u>${k.title}</u>');
		</c:if>
	</c:forEach>
result = result.replace(/digestposts/g, '<u><bean:message key="digestposts" /></u>');
result = result.replace(/posts/g, '<u><bean:message key="posts" /></u>');
result = result.replace(/oltime/g, '<u><bean:message key="a_setting_creditsformula_oltime" /></u>');
result = result.replace(/pageviews/g, '<u><bean:message key="pageviews" /></u>');
document.getElementById('creditsformulaexp').innerHTML = '<u><bean:message key="a_setting_creditsformula_credits" /></u>=' + result;
}
</script>
<form method="post" action="admincp.jsp?action=toCreditExpression">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="49ea602525c7ba54"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_setting_step_menu_2" />
				<a href="###" onclick="collapse_change('49ea602525c7ba54')"><img id="menuimg_49ea602525c7ba54" src="${pageContext.request.contextPath}/images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
			</td>
		</tr>
		<tbody id="menu_49ea602525c7ba54" style="display: yes">
			<tr>
				<td colspan="2" class="altbg1">
					<b><bean:message key="a_setting_creditsformula" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_current_formula_comment" /></span>
					<br />
					<img src="${pageContext.request.contextPath }/images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('creditsformulanew', 1)">
					<img src="${pageContext.request.contextPath }/images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('creditsformulanew', 0)">
					<div style="width: 90%" class="formulaeditor">
						<div>
							<c:forEach items="${extcredits}" var="ext" varStatus="ind">
								<c:choose>
									<c:when test="${empty ext.value.title }"><a href="###" onclick="insertunit('extcredits${ext.key}')"><bean:message key="a_setting_creditsformula_extcredits" />${ext.key}</a>&nbsp;</c:when>
									<c:otherwise><a href="###" onclick="insertunit('extcredits${ext.key}')">${ext.value.title}</a>&nbsp;</c:otherwise>
								</c:choose>
							</c:forEach>
							<br />
							<a href="###" onclick="insertunit('digestposts')"><bean:message key="digestposts" /></a>&nbsp;
							<a href="###" onclick="insertunit('posts')"><bean:message key="posts" /></a>&nbsp;
							<a href="###" onclick="insertunit('oltime')"><bean:message key="a_setting_creditsformula_oltime" /></a>&nbsp;
							<a href="###" onclick="insertunit('pageviews')"><bean:message key="pageviews" /></a>&nbsp;
							<a href="###" onclick="insertunit('+')">&nbsp;+&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit('-')">&nbsp;-&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit('*')">&nbsp;*&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit('/')">&nbsp;/&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit('(', ')')">&nbsp;(&nbsp;)&nbsp;</a>
							<br />
							<span id="creditsformulaexp">${settings.creditsformulaexp}</span>
						</div>
						<textarea name="creditsformulanew" id="creditsformulanew" style="width: 100%" rows="3" onkeyup="formulaexp()">${settings.creditsformula}</textarea>
					</div>
					<br />
					<bean:message key="a_setting_current_formula_notice" />
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../../cp_footer.jsp" />