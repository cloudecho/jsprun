<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<c:choose>
	<c:when test="${param.inajax==null}">
	<jsp:include flush="true" page="header.jsp" />
	<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; ${navigation} &raquo; <bean:message key="emailfriend"/></div>
	<form method="post" action="misc.jsp?action=emailfriend&sendsubmit=yes">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="tid" value="${tid}">
		<div class="mainbox formbox">
			<h1><bean:message key="emailfriend"/></h1>
			<table summary="Email Friend" cellspacing="0" cellpadding="0">
				<tr><th><bean:message key="emailfriend_receiver_email"/></th><td><input type="text" name="sendtoemail" size="25"></td></tr>
				<tr><th valign="top"><bean:message key="message"/></th><td><textarea rows="9" name="message" style="OVERFLOW: visible;HEIGHT:120px;width: 80%; word-break: break-all">${content}</textarea></td></tr>
				<tr class="btns"><th>&nbsp;</th><td><button name="sendsubmit" type="submit" class="submit" value="true"><bean:message key="submitf"/></button></td></tr>
			</table>
		</div>	
	</form>
	<jsp:include flush="true" page="footer.jsp" />	
	</c:when>
	<c:otherwise>
	<div class="ajaxform">
		<form method="post" action="misc.jsp?action=emailfriend&inajax=1&sendsubmit=yes" id="emailfriendform" name="emailfriendform">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="tid" value="${tid}">
			<table summary="Email Friend" cellspacing="0" cellpadding="0">
				<thead><tr><th><bean:message key="emailfriend"/></th><td align="right"><a href="javascript:hideMenu();"><img src="images/spaces/close.gif"  title="<bean:message key="closed"/>" /></a></td></tr></thead>
				<tr><th><bean:message key="emailfriend_receiver_email"/></th><td><input type="text" name="sendtoemail" size="25"></td></tr>
				<tr><th valign="top"><bean:message key="message"/></th><td><textarea rows="5" name="message" style="width: 400px;">${content}</textarea></td></tr>
				<tr class="btns"><th>&nbsp;</th><td><button name="sendsubmit" type="button" class="submit" onclick="ajaxpost('emailfriendform', 'emailfriend_menu');"><bean:message key="submitf"/></button></td></tr>
			</table>
		</form>
	</div>
	</c:otherwise>
</c:choose>