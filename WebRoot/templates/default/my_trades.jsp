<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="my_trades_navbar.jsp" />
<form action="my.jsp?item=${myitem}${extratid}${extrafid}&filter=${filter}" method="post">
	<div class="msgtabs">
		<div class="mysearch"><bean:message key="tradelog_trade_name"/> <input name="srchkey" value="${srchkey}" size="27"> &nbsp;<button class="submit" type="submit" name="submit" value="true"><bean:message key="search"/></button></div>
		<strong><c:choose><c:when test="${myitem=='buytrades'}"><bean:message key="my_trade_buys"/></c:when><c:otherwise><bean:message key="my_trade_selles"/></c:otherwise></c:choose> &#8212;&#8212;<c:choose><c:when test="${empty filter}"><bean:message key="my_trade_trading"/></c:when><c:when test="${filter=='attention'}"><bean:message key="my_trade_attention"/></c:when><c:when test="${filter=='eccredit'}"><bean:message key="my_trade_eccredit"/></c:when><c:when test="${filter=='success'}"><bean:message key="my_trade_success"/></c:when><c:when test="${filter=='closed'}"><bean:message key="my_trade_closed"/></c:when><c:when test="${filter=='refund'}"><bean:message key="my_trade_refund"/></c:when><c:when test="${filter=='unstart'}"><bean:message key="my_trade_unstart"/></c:when><c:when test="${filter=='all'}"><bean:message key="my_trade_all"/></c:when></c:choose> <c:if test="${tid>0||!empty srchkey}">[<a href="my.jsp?item=${myitem}${extrafid}&filter=${filter}"><bean:message key="my_trade_view_all"/></a>]</c:if></strong>
	</div>
</form>
<table cellspacing="0" cellpadding="0" width="100%">
	<thead><tr><td colspan="2"><bean:message key="tradelog_trade_name"/></td><td><bean:message key="my_trade_thread"/></td><td>
	<c:choose>
		<c:when test="${myitem=='buytrades'}"><bean:message key="tradelog_seller"/></c:when>
		<c:otherwise><bean:message key="a_extends_tradelog_buyer"/></c:otherwise>
	</c:choose>
	</td><td><bean:message key="my_trade_amount"/></td><td><bean:message key="my_trade_status"/></td><c:if test="${filter=='success'||filter=='refund'||filter=='eccredit'}"><td><bean:message key="a_extends_ec_credit"/></td></c:if></tr></thead>
	<tbody>
		<c:forEach items="${tradelists}" var="tradelog"><tr height="80" class="row">
			<td width="80" align="center"><a href="viewthread.jsp?do=tradeinfo&tid=${tradelog.tid}&pid=${tradelog.pid}" target="_blank"><c:choose><c:when test="${tradelog.aid>0}"><img class="absmiddle" src="attachment.jsp?aid=${tradelog.aid}&noupdate=yes" onload="thumbImg(this)" width="80" height="80" alt="" border="0" /></c:when><c:otherwise><img class="absmiddle" src="${styles.IMGDIR}/trade_nophotosmall.gif" alt="" border="0" /></c:otherwise></c:choose></a></td>
			<td><a target="_blank" href="trade.jsp?orderid=${tradelog.orderid}">${tradelog.subject}</a></td>
			<td><a target="_blank" href="viewthread.jsp?do=tradeinfo&tid=${tradelog.tid}&pid=${tradelog.pid}">${tradelog.threadsubject}</a></td>
			<td><c:choose><c:when test="${myitem=='selltrades'}"><c:choose><c:when test="${tradelog.buyerid>0}"><a target="_blank" href="space.jsp?uid=${tradelog.buyerid}">${tradelog.buyer}</a></c:when><c:otherwise>${tradelog.buyer}</c:otherwise></c:choose></c:when><c:otherwise><a target="_blank" href="space.jsp?uid=${tradelog.sellerid}">${tradelog.seller}</a></c:otherwise></c:choose></td>
			<td>${tradelog.price}</td>
			<td><a target="_blank" href="trade.jsp?orderid=${tradelog.orderid}"><c:choose><c:when test="${tradelog.attend!=null}"><b>${tradelog.status}</b></c:when><c:otherwise>${tradelog.status}</c:otherwise></c:choose></a><br />${tradelog.lastupdate}</td>
			<c:if test="${filter=='success'||filter=='refund'||filter=='eccredit'}"><td align="center"><c:choose><c:when test="${tradelog.ratestatus == 3}"><bean:message key="eccredit_post_between"/></c:when><c:when test="${(myitem=='buytrades'&& tradelog.ratestatus == 1)||(myitem == 'selltrades' && tradelog.ratestatus == 2)}"><bean:message key="eccredit_post_waiting"/></c:when><c:otherwise><c:if test="${(myitem=='buytrades'&& tradelog.ratestatus == 2)||(myitem == 'selltrades' && tradelog.ratestatus == 1)}"><bean:message key="eccredit_post_already"/><br /></c:if><c:choose><c:when test="${myitem=='buytrades'}"><a href="eccredit.jsp?action=rate&orderid=${tradelog.orderid}&type=0" target="_blank"><bean:message key="eccredit1"/></a></c:when><c:otherwise><a href="eccredit.jsp?action=rate&orderid=${tradelog.orderid}&type=1" target="_blank"><bean:message key="eccredit1"/></a></c:otherwise></c:choose></c:otherwise></c:choose></td></c:if>
		</tr></c:forEach>
		<c:if test="${empty tradelists}"><tr><td colspan="7"><bean:message key="my_trade_nonexistence"/></td></tr></c:if>
	</tbody>
</table>