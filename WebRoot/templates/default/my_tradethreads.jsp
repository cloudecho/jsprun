<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="my_trades_navbar.jsp" />
<form action="my.jsp?item=tradethreads" method="post">
	<div class="msgtabs">
		<div class="mysearch"><bean:message key="tradelog_trade_name"/> <input name="srchkey" value="${srchkey}" size="27"> &nbsp;<button type="submit" name="submit" value="true"><bean:message key="search"/></button></div>
		<strong><bean:message key="my_trade_selles"/>&#8212;&#8212;<bean:message key="my_trade_view_thread"/><c:if test="${tid>0||!empty srchkey}">[<a href="my.jsp?item=${myitem}${extrafid}"><bean:message key="my_trade_view_allthread"/></a>]</c:if></strong>
	</div>
</form>
<table cellspacing="0" cellpadding="0" width="100%">
	<thead><tr><td colspan="2"><bean:message key="tradelog_trade_name"/></td><td><bean:message key="my_trade_show"/></td><td><bean:message key="my_trade_sale_quantity"/></td><td><bean:message key="my_trade_stock_quantity"/></td><td><bean:message key="my_trade_amount_all"/></td><td><bean:message key="my_trade_last"/></td></tr></thead>
	<c:forEach items="${tradelists}" var="tradelist"><tr height="80">
		<td width="80" align="center"><a href="viewthread.jsp?do=tradeinfo&tid=${tradelist.tid}&pid=${tradelist.pid}" target="_blank"><c:choose><c:when test="${tradelist.aid>0}"><img class="absmiddle" src="attachment.jsp?aid=${tradelist.aid}&noupdate=yes" onload="thumbImg(this)" width="80" height="80" alt="" border="0" /></c:when><c:otherwise><img class="absmiddle" src="${styles.IMGDIR}/trade_nophotosmall.gif" alt="" border="0" /></c:otherwise></c:choose></a></td>
		<td class="subject"><a href="viewthread.jsp?do=tradeinfo&tid=${tradelist.tid}&pid=${tradelist.pid}" target="_blank">${tradelist.subject}</a><br /><c:choose><c:when test="${tradelist.closed==1}"><span class="lighttxt"><bean:message key="trade_timeout"/></span></c:when><c:when test="${tradelist.expiration>0}"><span class="lighttxt"><bean:message key="trade_remain"/> ${tradelist.expiration}<bean:message key="ipban_days"/>${tradelist.expirationhour}<bean:message key="hr"/></span></c:when><c:when test="${tradelist.expiration==-1}"><span class="lighttxt"><bean:message key="trade_timeout"/></span></c:when></c:choose></td>
		<td><a href="my.jsp?item=selltrades&tid=${tradelist.tid}&pid=${tradelist.pid}" target="_blank"><bean:message key="enter"/></a></td><td>${tradelist.totalitems}</td><td>${tradelist.amount}</td><td>${tradelist.tradesum}</td>
		<td><c:choose><c:when test="${tradelist.lastbuyer!=''}"><a href="redirect.jsp?tid=${tradelist.tid}&goto=lastpost#lastpost">${tradelist.lastupdate}</a><br />by <a href="space.jsp?username=${tradelist.lastbuyererenc}" target="_blank">${tradelist.lastbuyer}</a></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td>
	</tr></c:forEach>
	<c:if test="${empty tradelists}"><tr><td colspan="6"><bean:message key="forum_nothreads"/></td></tr></c:if>
</table>