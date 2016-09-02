<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onClick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_ecommerce_tradeorder" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan=6"><bean:message key="a_extends_search_status" /> <select style="vertical-align: middle" onchange="location.href='admincp.jsp?action=tradelog&filter=' + this.value"><option value='-1'><bean:message key="a_extends_tradelog_all_order" /></option>
			<c:forEach items="${statuss}" var="status"><option value="${status.key}" ${filter == status.key ? "selected" : ""}>${status.value}</option></c:forEach></select>
		</td>
	</tr>
	<tr class="altbg1"><td colspan=6"><bean:message key="a_extends_tradelog_order_count" /> ${num}<c:if test="${tradelog.pricesum>0.0}">, <bean:message key="a_extends_tradelog_trade_total" /> ${tradelog.pricesum} <bean:message key="rmb_yuan" />, <bean:message key="a_extends_tradelog_fee_total" /> ${tradelog.taxsum} <bean:message key="rmb_yuan" /></c:if></td></tr>
</table>
<br />
${multi.multipage}
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td align="center" width="10%"><bean:message key="a_extends_tradelog_trade_no" /></td>
		<td align="center" width="15%"><bean:message key="tradelog_trade_name" /></td>
		<td align="center" width="7%"><bean:message key="a_extends_tradelog_buyer" /></td>
		<td align="center" width="7%"><bean:message key="tradelog_seller" /></td>
		<td align="center" width="10%"><bean:message key="a_extends_tradelog_money" /></td>
		<td align="center" width="10%"><bean:message key="a_extends_tradelog_fee" /></td>
		<td align="center" width="20%"><bean:message key="a_extends_search_status" /></td>
	</tr>
	<c:forEach items="${tradeLogs}" var="tradeLog">
	<tr>
		<td align="center" class="altbg1">&nbsp;${tradeLog.tradeno}</td>
		<td align="center" class="altbg2"><a target="_blank" href="viewthread.jsp?do=tradeinfo&tid=${tradeLog.tid}&pid=${tradeLog.pid}">${tradeLog.subject}</a></td>
		<td align="center" class="altbg1"><a target="_blank" href="space.jsp?action=viewpro&uid=${tradeLog.buyerid}">${tradeLog.buyer}</a></td>
		<td align="center" class="altbg2"><a target="_blank" href="space.jsp?action=viewpro&uid=${tradeLog.sellerid}">${tradeLog.seller}</a></td>
		<td align="center" class="altbg1">${tradeLog.price}</td>
		<td align="center" class="altbg2">${tradeLog.tax}</td>
		<td align="center" class="altbg1"><a target="_blank" href="trade.jsp?orderid=${tradeLog.orderid}">${tradeLog.status}</a><br />${tradeLog.lastupdate}</td>
	</tr>
	</c:forEach>
</table>
${multi.multipage}
<jsp:include page="../cp_footer.jsp" />