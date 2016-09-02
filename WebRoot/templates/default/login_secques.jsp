<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="member_login" /></div>
<form method="post" action="logging.jsp?action=login">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<input type="hidden" name="username" value="${username}" />
<input type="hidden" name="loginauth" value="${loginauth}" />
<input type="hidden" name="loginmode" value="${loginmode}" />
<input type="hidden" name="styleid" value="${styleid}" />
<input type="hidden" name="cookietime" value="${cookietime}" />
<div class="mainbox formbox">
	<h1><bean:message key="member_login" /></h1>
	<c:if test="${login_secques!=null}"><ins class="logininfo">${login_secques}</ins></c:if>
	<table summary="<bean:message key="member_login" />" cellspacing="0" cellpadding="0">
		<tbody>
			<tr><th><bean:message key="username" /></th><td><input type="text" size="25" name="username" value="${username}" disabled /></td></tr>
			<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode" /></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" tabindex="1" /> <em class="tips"><strong><bean:message key="seccode_click" /></strong> <bean:message key="seccode_refresh" /></em><script type="text/javascript">var seccodedata = [${seccodedata["width"]}, ${seccodedata["height"]},${seccodedata["type"]}];</script></td></tr></c:if>
			<c:if test="${login_secques!=null}">
				<tr><th><bean:message key="security_question" /></th><td><select name="questionid" tabindex="3"><option value="0"><bean:message key="security_question_0" /></option><option value="1"><bean:message key="security_question_1" /></option><option value="2"><bean:message key="security_question_2" /></option><option value="3"><bean:message key="security_question_3" /></option><option value="4"><bean:message key="security_question_4" /></option><option value="5"><bean:message key="security_question_5" /></option><option value="6"><bean:message key="security_question_6" /></option><option value="7"><bean:message key="security_question_7" /></option></select></td></tr>
				<tr><th><bean:message key="security_answer" /></th><td><input type="text" name="answer" size="25" tabindex="4" /></td></tr>
			</c:if>
			<tr><th>&nbsp;</th><td><button type="submit" class="submit" name="loginsubmit" value="true"><bean:message key="member_login" /></button></td></tr>
		</tbody>
	</table>
</div>
</form>
<jsp:include flush="true" page="footer.jsp" />