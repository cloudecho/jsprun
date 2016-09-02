<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_trade_rank" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">
			<h3>
				<bean:message key="stats_trade_rank" />
			</h3>
			<div class="msgtabs">
				<strong><bean:message key="trade_price_sort" /></strong>
			</div>
			<table cellspacing="0" cellpadding="0" width="100%" align="center">
				<thead>
					<tr>
						<td>
							<bean:message key="tradelog_trade_name" />
						</td>
						<td>
							<bean:message key="tradelog_seller" />
						</td>
						<td>
							<bean:message key="trade_totalprice" />(<bean:message key="rmb_yuan" />)
						</td>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${valueObject.tradesums}" var="tradesum">
					<tr>
					<td><a target="_blank" href="viewthread.jsp?do=tradeinfo&tid=${tradesum.value.tid }&pid=${tradesum.value.pid }">${tradesum.value.subject }</a></td>
					<td><a target="_blank" href="space.jsp?uid=${tradesum.value.sellerid }">${tradesum.value.seller }</a></td>
					<td>${tradesum.value.tradesum }</td>
					</tr>
				</c:forEach>
				</tbody>

			</table>
			<table cellspacing="0" cellpadding="0" width="100%" align="center">
				<div class="msgtabs">
					<strong><bean:message key="trace_number_sort" /></strong>
				</div>

				<thead>
					<tr>
						<td>
							<bean:message key="tradelog_trade_name" />
						</td>
						<td>
							<bean:message key="tradelog_seller" />
						</td>
						<td>
							<bean:message key="trace_sell_number" />
						</td>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${valueObject.totalitems}" var="totalitem">
					<tr>
					<td><a target="_blank" href="viewthread.jsp?do=tradeinfo&tid=${totalitem.value.tid }&pid=${totalitem.value.pid }">${totalitem.value.subject }</a></td>
					<td><a target="_blank" href="space.jsp?uid=${totalitem.value.sellerid }">${totalitem.value.seller }</a></td>
					<td>${totalitem.value.totalitems }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>


		<div class="notice">
			<bean:message key="stats_update" arg0="${valueObject.lastUpdate }" arg1="${valueObject.nextUpdate }" />
		</div>
	</div>
</div>

<jsp:include flush="true" page="footer.jsp" />
