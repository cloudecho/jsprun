<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<link href="include/css/keyboard.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
lang["kb_backSpace"] = '← <bean:message key="kb_backSpace"/>';
lang["kb_capsLockOff"] = '<bean:message key="kb_capsLockOff"/>';
lang["kb_capsLockOn"] = '<bean:message key="kb_capsLockOn"/>';
lang["kb_clear"] = '<bean:message key="kb_clear"/>';
lang["kb_enter"] = '←|\n<bean:message key="kb_enter"/>';
lang["kb_title"] = '<bean:message key="kb_title"/>';
</script>
<script src="include/javascript/keyboard.js" type="text/javascript"></script>
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="getpassword"/></div>
<form method="post" action="member.jsp?action=getpasswd&amp;uid=${uid}&amp;id=${id}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox">
		<h1><bean:message key="getpassword"/></h1>
		<table cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th><bean:message key="username"/>:</th>
				<td>${username}</td>
			</tr>
			<tr>
				<th><bean:message key="new_password"/>:</th>
				<td><input type="password" name="newpasswd1" id="newpasswd1" size="25" /><input type="button"  class="keybord_button" style="margin-left: -24px;" onclick="jrunKeyBoard('newpasswd1',this);" title="<bean:message key="keyboard_title"/>">&nbsp;</td>
			</tr>
			<tr>
				<th><bean:message key="new_password_confirm"/>:</th>
				<td><input type="password" name="newpasswd2" id="newpasswd2" size="25" /><input type="button" class="keybord_button" style="margin-left: -24px;" onclick="jrunKeyBoard('newpasswd2',this);" title="<bean:message key="keyboard_title"/>">&nbsp;</td>
			</tr>
			<tr class="btns">
				<th></th>
				<td><button type="submit" class="submit" name="getpwsubmit" value="true"><bean:message key="submitf"/></button></td>
			</tr>
		</table>
	</div>
</form>
<jsp:include flush="true" page="footer.jsp" />