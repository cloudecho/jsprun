<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="my_trades_navbar.jsp" />
<table cellspacing="0" cellpadding="0" width="100%">
	<c:if test="${goodsbuyer>0}">
		<thead><tr><td colspan="2"><bean:message key="my_trade_buyer_info"/></td></tr></thead>
		<tr>
			<th><a href="my.jsp?item=buytrades${extrafid}&filter=trading"><bean:message key="my_trade_view_buy"/></a></th>
			<td>
				<a href="my.jsp?item=buytrades${extrafid}"><bean:message key="my_trade_buyer_trading" arg0="${goodsbuyer}"/></a><br />
				<c:if test="${buyerattend>0}">
					<a href="my.jsp?item=buytrades${extrafid}&ilter=attention"><bean:message key="my_trade_need_attend_sell" arg0="${buyerattend}"/></a><br />
					<div class="lighttxt" style="padding-left: 1em"><bean:message key="my_trade_include"/>: <div style="padding-left: 2em"><c:if test="${attendstatus['1']>0}">${attendstatus['1']} <bean:message key="my_trade_include_1"/><br /></c:if><c:if test="${attendstatus['5']>0}">${attendstatus['5']} <bean:message key="my_trade_include_5"/><br /></c:if><c:if test="${attendstatus['11']>0}">${attendstatus['11']} <bean:message key="my_trade_include_11"/><br /></c:if><c:if test="${attendstatus['12']>0}">${attendstatus['12']} <bean:message key="my_trade_include_12"/><br /></c:if></div></div>
				</c:if>
				<c:if test="${eccreditbuyer>0}"><a href="my.jsp?item=buytrades${extrafid}&filter=eccredit"><bean:message key="my_trade_need_seller_eccredit" arg0="${eccreditbuyer}"/></a></c:if>
			</td>
		</tr>
	</c:if>
	<c:if test="${goodsseller>0}">
		<thead><tr><td colspan="2"><bean:message key="my_trade_seller_info"/></td></tr></thead>
		<tbody>
			<tr><th><a href="my.jsp?item=tradethreads${extrafid}"><bean:message key="my_trade_view_thread"/></a></th><td><a href="my.jsp?item=tradethreads${extrafid}"><bean:message key="my_trade_seller_trading" arg0="${goodsseller}"/></a></td></tr>
			<tr>
				<th><a href="my.jsp?item=selltrades${extrafid}&filter=trading"><bean:message key="my_trade_view_sell"/></a></th>
				<td>
					<c:if test="${sellerattend>0}">
						<a href="my.jsp?item=selltrades${extrafid}&filter=attention"><bean:message key="my_trade_need_attend_sell" arg0="${sellerattend}"/></a><br />
						<div class="lighttxt" style="padding-left: 1em"><bean:message key="my_trade_include"/>: <div style="padding-left: 2em"><c:if test="${attendstatus['2']>0}">${attendstatus['2']} <bean:message key="my_trade_include_2"/><br /></c:if><c:if test="${attendstatus['4']>0}">${attendstatus['4']} <bean:message key="my_trade_include_4"/><br /></c:if><c:if test="${attendstatus['10']>0}">${attendstatus['10']}<bean:message key="my_trade_include_10"/><br /></c:if><c:if test="${attendstatus['13']>0}">${attendstatus['13']} <bean:message key="my_trade_include_13"/><br /></c:if></div></div>
					</c:if>
					<c:if test="${eccreditseller>0}"><a href="my.jsp?item=selltrades${extrafid}&filter=eccredit"><bean:message key="my_trade_need_seller_eccredit" arg0="${eccreditseller}"/></a></c:if>
				</td>
			</tr>
		</tbody>
	</c:if>
	<c:if test="${buystats.tradesum>0.0||sellstats.tradesum>0.0}">
		<thead><tr><td colspan="2"><bean:message key="my_trade_stat"/></td></tr></thead>
		<tbody><tr><td colspan="2"><c:if test="${buystats.tradesum>0.0}"><bean:message key="my_trade_buyer_total"/>: ${buystats.totalitems}<br /><bean:message key="my_trade_buyer_sum"/>: ${buystats.tradesum} <bean:message key="rmb_yuan"/><br /></c:if><c:if test="${sellstats.tradesum>0.0}"><bean:message key="my_trade_seller_total"/>: ${sellstats.totalitems}<br /><bean:message key="my_trade_seller_sum"/>: ${sellstats.tradesum} <bean:message key="rmb_yuan"/><br /></c:if></td></tr></tbody>
	</c:if>
</table>