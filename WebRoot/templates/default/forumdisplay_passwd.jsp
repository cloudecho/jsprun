<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> ${navigation}</div>
<form method="post" action="forumdisplay.jsp?pwverify=true&fid=${fid}">
	<div class="box message">
		<h1><bean:message key="forum_password_require" /></h1>
		<p style="text-align: center; margin: 3em 0;"><input type="password" name="pw" size="25"> &nbsp;<button class="submit" type="submit" name="loginsubmit" value="true"><bean:message key="submitf" /></button></p>
	</div>
</form>
<jsp:include flush="true" page="footer.jsp" />