<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<div style="width: 400px" class="popupmenu_option" id="ajax_explain_menu">
	<a href="###" onclick="hideMenu()"><img align="right" src="${styles.IMGDIR}/close.gif" border="0" alt="" /></a><br />
	<form method="post" id="explainform_${param.id}" action="eccredit.jsp?action=explain&amp;explainsubmit=yes">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="id" value="${param.id}" />
		<textarea name="explanation" rows="5" cols="50" style="width: 98%;overflow: hidden" /></textarea><br /><br />
		<button class="submit" type="button" name="explainsubmit" value="true" onclick="ajaxpost('explainform_${param.id}', 'ajax_explain_menu')"><bean:message key="submitf"/></button>
	</form>
</div>
