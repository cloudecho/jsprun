<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<table summary="Trade List" cellspacing="0" cellpadding="0">
	<thead class="separation">
		<tr>
			<th>
			<c:choose>
				<c:when test="${showtradetypemenu}">
					<span id="tradetypes" class="dropmenu" onmouseover="showMenu(this.id)"><bean:message key="post_trade_alllist"/></span>
					<ul class="popupmenu_popup" id="tradetypes_menu" style="display: none;">
					<li><a href="viewthread.jsp?do=viewtradelist&tid=${thread.tid}" ajaxtarget="ajaxtradelist"><bean:message key="all"/></a></li>
					<c:forEach items="${threadtradetypes}" var="tradetype">
						<c:choose>
							<c:when test="${tradetype.typeid>0}"><li><a href="viewthread.jsp?do=viewtradelist&tid=${thread.tid}tid&tradetypeid=${tradetype.typeid}" ajaxtarget="ajaxtradelist">${tradetypes[tradetype.typeid]}</a></li></c:when>
							<c:otherwise><li><a href="viewthread.jsp?do=viewtradelist&tid=${thread.tid}tid&tradetypeid=0" ajaxtarget="ajaxtradelist"><bean:message key="space_trade_nonetype"/></a></li></c:otherwise>
						</c:choose>
					</c:forEach>
					</ul>
				</c:when>
				<c:otherwise><bean:message key="post_trade_alllist"/></c:otherwise>
			</c:choose>
			</th>
			<td class="price"><bean:message key="trade_price"/></td>
			<td class="nums"><bean:message key="num"/></td>
			<td class="time"><bean:message key="trade_remaindays"/></td>
		</tr>
	</thead>
	<c:forEach items="${tradelist}" var="trade">
		<tr>
			<th><c:if test="${trade.typeid>0}"><a href="search.jsp?srchtype=trade&srchtypeid=${trade.typeid}&searchsubmit=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank"><em>{tradetypes[trade.typeid]}</em></a> </c:if><a href="viewthread.jsp?do=tradeinfo&tid=${trade.tid}&pid=${trade.pid}" target="_blank">${trade.subject}</a></th>
			<td class="price"><strong>${trade.price}</strong> <bean:message key="rmb_yuan"/></td>
			<td class="nums">${trade.amount}</td>
			<td class="time">
			<c:choose>
				<c:when test="${trade.closed>0}"><em><bean:message key="trade_timeout"/></em></c:when>
				<c:when test="${trade.expiration>0}">${trade.expiration}<bean:message key="ipban_days"/>${trade.expirationhour}<bean:message key="hr"/></c:when>
				<c:when test="${trade.expiration==-1}"><em><bean:message key="trade_timeout"/></em></c:when>
			</c:choose>
			</td>
		</tr>
	</c:forEach>
</table>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>