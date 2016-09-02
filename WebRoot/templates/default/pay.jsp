<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; ${navigation} &raquo; <bean:message key="pay"/></div>
<form method="post" action="misc.jsp?action=pay&paysubmit=yes">
 <input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}"/>
	<div class="mainbox formbox">
		<h1><bean:message key="pay"/></h1>
		<table summary="<bean:message key="pay"/>" cellspacing="0" cellpadding="0">
			<tr>
				<th><bean:message key="username"/>:</th>
				<td>${jsprun_userss} <span class="smalltxt">[<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</span></td>
			</tr>
			<tr>
				<th><bean:message key="author"/>:</th>
				<td><a href="space.jsp?uid=${thread.authorid}">${thread.author}</a></td>
			</tr>
			<tr>
				<th><bean:message key="thread"/>:</th>
				<td><a href="viewthread.jsp?tid=${thread.tid}">${thread.subject}</a></td>
			</tr>
			<tr>
				<th valign="top"><bean:message key="price"/>(${creditstrans.title}):</th>
				<td>${thread.price}${creditstrans.unit}</td>
			</tr>
			<tr>
				<th valign="top"><bean:message key="pay_author_income"/>(${creditstrans.title}):</th>
				<td>${threaprice}${creditstrans.unit}</td>
			</tr>
			<tr>
				<th valign="top"><bean:message key="pay_balance"/>(${creditstrans.title}):</th>
				<td>${banlance} ${creditstrans.unit}</td>
			</tr>
			<tr class="btns">
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="tid" value="${thread.tid}" />
					<button class="submit" type="submit" name="paysubmit" value="true"><bean:message key="submitf"/></button>
				</td>
			</tr>
		</table>
	</div>
</form>
<jsp:include flush="true" page="footer.jsp" />