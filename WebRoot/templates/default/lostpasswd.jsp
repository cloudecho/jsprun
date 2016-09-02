<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="lostpassword"/></div>
<form method="post" action="member.jsp?action=lostpasswd">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox formbox">
		<h1><bean:message key="lostpassword"/></h1>
		<table summary="<bean:message key="lostpassword"/>" cellspacing="0" cellpadding="0">
			<tr>
				<th><label for="username"><bean:message key="username"/></label></th>
				<td><input type="text" id="username" name="username" size="25" /></td>
			</tr>
			<tr>
				<th><label for="email"><bean:message key="email"/></label></th>
				<td><input type="text" id="email" name="email" size="25" /></td>
			</tr>
			<tr>
				<th><label for="questionid"><bean:message key="security_question"/></label></th>
				<td>
					<select id="questionid" name="questionid">
						<option value="0">&nbsp;</option>
						<option value="1"><bean:message key="security_question_1"/></option>
						<option value="2"><bean:message key="security_question_2"/></option>
						<option value="3"><bean:message key="security_question_3"/></option>
						<option value="4"><bean:message key="security_question_4"/></option>
						<option value="5"><bean:message key="security_question_5"/></option>
						<option value="6"><bean:message key="security_question_6"/></option>
						<option value="7"><bean:message key="security_question_7"/></option>
					</select>
				</td>
			</tr>
			<tr>
				<th><label for="answer"><bean:message key="security_answer"/></label></th>
				<td><input type="text" name="answer" size="25" /></td>
			</tr>
			<tr class="btns">
				<th>&nbsp;</th>
				<td><button type="submit" class="submit" name="lostpwsubmit" value="true"><bean:message key="submitf"/></button></td>
			</tr>
		</table>
	</div>
</form>
<jsp:include flush="true" page="footer.jsp" />