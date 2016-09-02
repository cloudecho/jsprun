<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo;<bean:message key="attachment_buy"/></div>
<form method="post" action="misc.jsp?action=attachpay&paysubmit=yes">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox formbox">
		<h1><bean:message key="attachment_buy"/></h1>
		<table summary="<bean:message key="attachment_buy"/>" cellspacing="0" cellpadding="0">
			<tr>
				<th><bean:message key="username"/>:</th>
				<td>${jsprun_userss} <span class="smalltxt">[<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</span></td>
			</tr>
			<tr>
				<th><bean:message key="author"/>:</th>
				<td><a href="space.jsp?uid=${atta.uid}">${atta.username}</a></td>
			</tr>
			<tr>
				<th><bean:message key="attachment"/>:</th>
				<td>${atta.filename}<c:if test="${atta.description!=''}">${atta.description}</c:if></td>
			</tr>
			<tr>
				<th valign="top"><bean:message key="a_post_threads_price"/>(${creditstrans.title})</th>
				<td>${atta.price}${creditstrans.unit}</td>
			</tr>
			<tr>
				<th valign="top"><bean:message key="pay_author_income"/>(${creditstrans.title})</th>
				<td>${netprice}${creditstrans.unit}</td>
			</tr>
			<tr>
				<th valign="top"><bean:message key="pay_balance"/>(${creditstrans.title})</th>
				<td>${banlance} ${creditstrans.unit}</td>
			</tr>
			<tr class="btns">
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="aid" value="${atta.aid}" />
					<button class="submit" type="submit" name="paysubmit" value="true"><bean:message key="submitf"/></button>
				</td>
			</tr>
		</table>
	</div>
</form>
<jsp:include flush="true" page="footer.jsp" />