<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<script type="text/javascript">
	var ids = 2;
</script>
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> ${navigation}&raquo; <c:choose><c:when test="${action=='newthread'}"><bean:message key="post_new"/></c:when><c:when test="${action=='reply'}"><bean:message key="a_forum_edit_perm_reply"/></c:when></c:choose></div>
<form id="seccodeform" method="post" action="${path}" enctype="multipart/form-data">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="usesig" value="${usesig}" />
	<input type="hidden" name="subject" value="${subject}" />
	<input type="hidden" name="message" value="${message}" />
	<input type="hidden" name="stand" value="${stand}" />
	<div class="mainbox formbox">
		<c:if test="${seccodecheck}">
			<table summary="<bean:message key="post_seccode"/>" cellspacing="0" cellpadding="0">
				<thead><tr><th><bean:message key="post_seccode"/></th><td>&nbsp;</td></tr></thead>
				<tr><th><bean:message key="seccode"/></th><td><div id="seccodeimage"><bean:message key="seccode_click"/></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" /> <bean:message key="seccode_refresh"/><script type="text/javascript">var seccodedata = [${seccodedata['width']}, ${seccodedata['height']}, ${seccodedata['type']}];</script></td></tr>
			</table>
		</c:if>
		<c:if test="${secqaacheck}">
			<table summary="<bean:message key="post_secqaa"/>" cellspacing="0" cellpadding="0">
				<thead><tr><th><bean:message key="post_secqaa"/></th><td>&nbsp;</td></tr></thead>
				<tr><th><bean:message key="secqaa"/></th><td><div id="secquestion"></div> <input type="text" name="secanswer" size="25" maxlength="50"><script type="text/javascript">ajaxget('ajax.do?action=updatesecqaa', 'secquestion');</script></td></tr>
			</table>
		</c:if>
		<c:if test="${allowpostattach}">
			<table summary="<bean:message key="access_postattach"/>" cellspacing="0" cellpadding="0">
				<thead><tr><th><bean:message key="access_postattach"/></th><td><c:choose><c:when test="${maxattachsize>0}"><bean:message key="lower_than"/> ${maxattachsize} kb </c:when><c:otherwise><bean:message key="size_no_limit"/></c:otherwise></c:choose><c:if test="${attachextensions!=''}">, <bean:message key="attachment_allow_exts"/>: ${attachextensions}</c:if></td></tr></thead>
				<tr>
					<th>&nbsp;</th>
					<td>
						<table summary="" cellpadding="0" cellspacing="0">
							<tbody id="attachbody" style=""><tr><td><c:if test="${usergroups.allowsetreadperm>0}"><bean:message key="threads_readperm"/>: <input type="text" name="attachperm[]" value="0" size="1" />&nbsp; &nbsp;</c:if><bean:message key="description"/>: <input type="text" name="attachdesc[]" size="15" />&nbsp; &nbsp;<bean:message key="attachment"/>: <input type="file" name="attachfile1" size="15" /></td></tr></tbody>
							<tr>
								<td style="border: none;">
									<button type="button" onclick="newnode = $('attachbody').firstChild.cloneNode(true); newnode.firstChild.childNodes[1].value = ''; newnode.firstChild.childNodes[3].value = ''; newnode.firstChild.childNodes[5].name='attachfile'+ids;newnode.firstChild.childNodes[5].value='';ids++;$('attachbody').appendChild(newnode)">&nbsp;+&nbsp;</button>&nbsp;
									<button type="button" onclick="$('attachbody').childNodes.length > 1 && $('attachbody').lastChild ? $('attachbody').removeChild($('attachbody').lastChild) : 0;">&nbsp;-&nbsp;</button>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</c:if>
		<table><tr><th>&nbsp;</th><td><button class="submit" type="submit"><bean:message key="submitf"/></button></td></tr></table>
	</div>
</form>
<jsp:include flush="true" page="footer.jsp" />