<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<c:choose>
<c:when test="${param.inajax>0}">
<script type="text/javascript" reload="1">
function ajaxerror() {
	if(${jsprun_uid==0}){
		if(${propertyKey!=null}){
			alert('<bean:message key="${show_message}" />');
		}else{
			alert('${show_message}');
		}
	}else{
		alert('<bean:message key="nopermission_loggedin" />');
	}
}
ajaxerror();
</script>
</c:when>
<c:otherwise>
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
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; ${param.inajax}<bean:message key="board_message" /></div>
<div class="box message">
	<h1>${settings.bbname} <bean:message key="board_message" /></h1>
	<p><bean:message key="nopermission" /></p>
	<c:if test="${show_message!=null}"><p><b><c:choose><c:when test="${propertyKey != null && propertyKey}"><c:choose><c:when test="${arg0_forKey != null }" ><bean:message key="${show_message}" arg0="${arg0_forKey}" /></c:when><c:otherwise><bean:message key="${show_message}" /></c:otherwise></c:choose></c:when><c:otherwise>${show_message}</c:otherwise></c:choose></b></p></c:if>
	<p><c:choose><c:when test="${jsprun_uid>0}"><bean:message key="nopermission_loggedin" /></c:when><c:otherwise><bean:message key="nopermission_no_loggedin" /></c:otherwise></c:choose></p>
	<c:if test="${jsprun_uid==0}">
		<form name="login" method="post" action="logging.jsp?action=login">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="referer" value="${referer}">
			<input type="hidden" name="cookietime" value="2592000">
			<div class="box" style="width: 60%; margin: 10px auto;">
				<table cellspacing="0" cellpadding="4" width="100%">
					<thead><tr><td colspan="2"><bean:message key="member_login" /></td></tr></thead>
					<tbody>
						<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode" /></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" tabindex="1" /> <em class="tips"><strong><bean:message key="seccode_click" /></strong> <bean:message key="seccode_refresh" /></em><script type="text/javascript">var seccodedata = [${seccodedata["width"]}, ${seccodedata["height"]},${seccodedata["type"]}];</script></td></tr></c:if>
						<tr><td onclick="document.login.username.focus();"><label><input type="radio" name="loginfield" value="username" checked="checked" /><bean:message key="username" /></label> <label><input type="radio" name="loginfield" value="uid" /><bean:message key="a_setting_uid" /></label></td><td><input type="text" name="username" size="25" maxlength="40" tabindex="2" /> &nbsp;<em class="tips"><a href="register.jsp"><bean:message key="register" /></a></em></td></tr>
						<tr><td><bean:message key="passwordf" /></td><td><input type="password" name="password" id="password" size="25" tabindex="3" /><input type="button" class="keybord_button" style="margin-left: -24px;" onclick="jrunKeyBoard('password',this);" title="<bean:message key="keyboard_title"/>">&nbsp; &nbsp;<em class="tips"><a href="member.jsp?action=lostpasswd"><bean:message key="lostpassword" /></a></em></td></tr>
						<tr><td><bean:message key="security_question" /></td><td><select name="questionid" tabindex="4"><option value="0">&nbsp;</option><option value="1"><bean:message key="security_question_1" /></option><option value="2"><bean:message key="security_question_2" /></option><option value="3"><bean:message key="security_question_3" /></option><option value="4"><bean:message key="security_question_4" /></option><option value="5"><bean:message key="security_question_5" /></option><option value="6"><bean:message key="security_question_6" /></option><option value="7"><bean:message key="security_question_7" /></option></select></td></tr>
						<tr><td><bean:message key="security_answer" /></td><td><input type="text" name="answer" size="25" tabindex="5" /></td></tr>
						<tr><td></td><td><button class="submit" type="submit" name="loginsubmit" id="loginsubmit" value="true" tabindex="6"><bean:message key="member_login" /></button></td></tr>
					</tbody>
				</table>
			</div>
		</form>
	</c:if>
</div>
<jsp:include flush="true" page="footer.jsp?isRedirect=true" />
</c:otherwise>
</c:choose>