<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<h1><bean:message key="post_my_advisit"/></h1>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr><td><bean:message key="my_promotion_url"/></td></tr>
	<tr><td><bean:message key="post_promotion_url1"/><input type="text" onclick="this.select();setcopy(this.value, '<bean:message key="post_copied"/>');" value="${boardurl}?fromuid=${user.uid}" size="50" /></td></tr>
	<tr><td><bean:message key="post_promotion_url2"/><input type="text" onclick="this.select();setcopy('${boardurl}?fromuser=<jrun:encoding value="${user.username}"/>', '<bean:message key="post_copied"/>');" value="${boardurl}?fromuser=${user.username}" size="50" /></td></tr>
	<tr>
		<td>
			<em>
				<ul>
					<li><bean:message key="credit_promotion"/></li>
					<c:if test="${!empty creditspolicy.promotion_visit}"><li><bean:message key="credit_promotion_visit"/> <font color="red">&nbsp;<c:forEach items="${creditspolicy.promotion_visit}" var="promotion_visit">${extcredits[promotion_visit.key].title} +${promotion_visit.value}&nbsp;</c:forEach></font></li></c:if>
					<c:if test="${!empty creditspolicy.promotion_register}"><li><bean:message key="credit_promotion_register"/> <font color="red">&nbsp;<c:forEach items="${creditspolicy.promotion_register}" var="promotion_register">${extcredits[promotion_register.key].title} +${promotion_register.value}&nbsp;</c:forEach></font></li></c:if>
				</ul> 
			</em>
		</td>
	</tr>
</table>