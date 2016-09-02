<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="a_extends_ec_credit"/></div>
<form method="post" action="eccredit.jsp?action=rate" id="postform">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="referer" value="${referer}" />
	<div class="mainbox">
		<h1><bean:message key="eccredit_post"/></h1>
		<table cellspacing="0" cellpadding="0" width="100%" align="center">
			<tr>
				<th><bean:message key="eccredit_retee"/></th>
				<td><c:choose><c:when test="${jsprun_uid==order.buyerid}"><a href="space.jsp?action=viewpro&uid=${order.sellerid}" target="_blank">${order.seller}</a></c:when><c:otherwise><a href="space.jsp?action=viewpro&uid=${order.buyerid}" target="_blank">${order.buyer}</a></c:otherwise></c:choose></td>
			</tr>
			<tr><th><bean:message key="eccredit_tradegoods"/></th><td><a href="redirect.jsp?goto=findpost&pid=${order.pid}" target="_blank">${order.subject}</a></td></tr>
			<tr>
				<th><bean:message key="rate"/></th>
				<td>
					<input name="score" value="1" type="radio" class="radio" checked /> <font color="#ff0000"><img src="images/rank/good.gif" border="0" width="14" height="16"><strong><bean:message key="eccredit_good"/></strong><c:choose><c:when test="${order.offline==0}"><bean:message key="eccredit_good_comment"/></c:when></c:choose></font>&nbsp;&nbsp;
					<input name="score" value="0" type="radio" class="radio"> <font color="green"><img src="images/rank/soso.gif" border="0" width="14" height="16"><strong><bean:message key="eccredit_soso"/></strong><c:choose><c:when test="${order.offline==0}"><bean:message key="eccredit_soso_comment"/></c:when></c:choose></font>&nbsp;&nbsp;
					<input name="score" value="-1" type="radio" class="radio"> <img src="images/rank/bad.gif" border="0" width="14" height="16"><strong><bean:message key="eccredit_bad"/></strong><c:choose><c:when test="${order.offline==0}"><bean:message key="eccredit_bad_comment"/></c:when></c:choose>
					<c:if test="${order.offline>0}">&nbsp;&nbsp;(<bean:message key="eccredit_offline"/>)</c:if>
				</td>
			</tr>
			<tr><th><bean:message key="eccredit1"/></th><td><textarea name="message" rows="5" cols="67" maxlength="50"></textarea></td></tr>
			<tr class="btns">
				<th></th>
				<td>
					<input type="hidden" name="orderid" value="${param.orderid}">
					<input type="hidden" name="type" value="${param.type}">
					<button type="submit" class="submit" id="postsubmit" name="ratesubmit" value="true"><bean:message key="submitf"/></button><bean:message key="post_submit_hotkey"/>
				</td>
			</tr>
		</table>
	</div>
</form>
<jsp:include flush="true" page="footer.jsp" />