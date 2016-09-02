<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="credits_policy"/></div>
<div class="mainbox">
	<h1><bean:message key="credits_policy"/></h1>
	<table summary="<bean:message key="credits_policy"/>" cellspacing="0" cellpadding="0">
		<thead><tr><th>&nbsp;</th><c:forEach items="${extmap}" var="extcredits"><td>${extcredits.value.title}</td></c:forEach></tr></thead>
		<c:forEach items="${result}" var="extcredit"><tr><th>${extcredit[0]}</th><c:forEach items="${extmap}" var="ext"><td>${extcredit[ext.key]}</td></c:forEach></tr></c:forEach>
	</table>
</div>
<div class="notice">
	<p><bean:message key="credits_policy_lowerlimit"/>: <bean:message key="credits_policy_lowerlimit_comment"/></p>
	<p><bean:message key="a_setting_creditsformula"/>${settings.creditsformulaexp}</p>
</div>
<jsp:include flush="true" page="footer.jsp" />