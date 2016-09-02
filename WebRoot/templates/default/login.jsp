<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="member_login" /></div>
<form method="post" name="login" action="logging.jsp?action=login">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<input type="hidden" name="referer" value="${referer}"/>
<div class="mainbox formbox">
	<span class="headactions"><a href="faq.jsp?action=message&id=3" target="_blank"><bean:message key="login_help" /></a></span>
	<h1><bean:message key="member_login" /></h1>
	<table summary="<bean:message key="member_login" />" cellspacing="0" cellpadding="0">
		<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode" /></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null"id="seccodeverify" name="seccodeverify" size="8" maxlength="4"tabindex="1" /> <em class="tips"><strong><bean:message key="seccode_click" /></strong> <bean:message key="seccode_refresh" /></em><script type="text/javascript">var seccodedata = [${seccodedata["width"]}, ${seccodedata["height"]},${seccodedata["type"]}];</script></td></tr></c:if>
		<tr><th onclick="document.login.username.focus();"><label><input class="radio" type="radio" name="loginfield" value="username" tabindex="2" checked="checked" /><bean:message key="username" /></label> <label><input class="radio" type="radio" name="loginfield" value="uid" tabindex="3" /><bean:message key="a_setting_uid" /></label></th><td><input type="text" id="username" name="username" size="25" maxlength="40" tabindex="4" /><a href="register.jsp"> <bean:message key="register_now" /></a></td></tr>
		<tr><th><label for="password"><bean:message key="passwordf" /></label></th><td><input type="password" id="password" name="password" size="25" tabindex="5" /><input type="button" style="margin-left: -23px;" class="keybord_button" onclick="jrunKeyBoard('password',this);" title="<bean:message key="keyboard_title"/>">&nbsp;<a href="member.jsp?action=lostpasswd"> <bean:message key="lostpassword" /></a></td></tr>
		<tr><th><label for="questionid"><bean:message key="security_question" /></label></th><td><select id="questionid" name="questionid" tabindex="6"><option value="0"><bean:message key="security_question_0" /></option><option value="1"><bean:message key="security_question_1" /></option><option value="2"><bean:message key="security_question_2" /></option><option value="3"><bean:message key="security_question_3" /></option><option value="4"><bean:message key="security_question_4" /></option><option value="5"><bean:message key="security_question_5" /></option><option value="6"><bean:message key="security_question_6" /></option><option value="7"><bean:message key="security_question_7" /></option></select></td></tr>
		<tr><th><label for="answer"><bean:message key="security_answer" /></label></th><td><input type="text" id="answer" name="answer" size="25" tabindex="7" /> <bean:message key="login_secques_comment" /></td></tr>
		<tr><th><bean:message key="login_cookie_time" /></th><td><label><input class="radio" type="radio" name="cookietime" value="315360000" tabindex="8" /> <bean:message key="0_day" /></label> <label><input class="radio" type="radio" name="cookietime" value="2592000" tabindex="9" checked="checked" /> <bean:message key="30_day" /></label> <label><input class="radio" type="radio" name="cookietime" value="86400" tabindex="10" /> <bean:message key="1_day" /></label> <label><input class="radio" type="radio" name="cookietime" value="3600" tabindex="11" /> <bean:message key="login_one_hour" /></label> <label><input class="radio" type="radio" name="cookietime" value="0" tabindex="12" /> <bean:message key="login_this_task" /></label></td></tr>
		<tr><th><label for="loginmode"><bean:message key="a_member_edit_invisible" /></label></th><td><select id="loginmode" name="loginmode" tabindex="13"><option value=""><bean:message key="use_default" /></option><option value="normal"><bean:message key="login_normal_mode" /></option><option value="invisible"><bean:message key="login_invisible_mode" /></option></select></td></tr>
		<tr><th><label for="styleid"><bean:message key="menu_style" /></label></th><td><select id="styleid" name="styleid" tabindex="14"><option value=""><bean:message key="use_default" /></option><c:forEach items="${forumStyles}" var="style"><option value="${style.key}">${style.value}</option></c:forEach></select></td></tr>
		<tr><th>&nbsp;</th><td><button class="submit" type="submit" name="loginsubmit" value="true" tabindex="100"><bean:message key="submitf" /></button></td></tr>
	</table>
</div>
</form>
<script type="text/javascript">
var cookietimes=document.getElementsByName("cookietime");
var i=0;
for(i;i<5;i++){
	if(cookietimes[i].value=='${cookietime}'){
		cookietimes[i].checked="checked";
	}
}
document.login.username.focus();
var mydate = new Date();
var mytimestamp = parseInt(mydate.valueOf() / 1000);
if(Math.abs(mytimestamp - ${timestamp}) > 86400) {
	window.alert('<bean:message key="login_timeoffset" arg0="${thetimenow}" />');
}
</script>
<jsp:include flush="true" page="footer.jsp" />