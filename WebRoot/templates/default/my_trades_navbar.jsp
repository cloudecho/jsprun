<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h1><bean:message key="my_trades"/></h1>
<ul class="tabs ${myitem =='tradestats'?'headertabs':''}">
	<li ${myitem =='tradestats'?"class=current":""}><a href="my.jsp?item=tradestats${extrafid}"><bean:message key="my_trade_stat"/></a></li>
	<li ${myitem =='buytrades'?"class=dropmenu hover current":"class=dropmenu"}><a href="my.jsp?item=buytrades${extrafid}" id="buytrades" onmouseover="showMenu(this.id)"><bean:message key="my_trade_buys"/></a></li>
	<li ${myitem =='tradethreads'||myitem=='selltrades'?"class=dropmenu hover current":"class=dropmenu"}><a href="my.jsp?item=tradethreads${extrafid}" id="tradethreads" onmouseover="showMenu(this.id)"><bean:message key="my_trade_selles"/></a></li>
	<li><a href="eccredit.jsp?uid=${jsprun_uid}" target="_blank"><bean:message key="a_extends_ec_credit"/></a></li>
</ul>
<ul class="popupmenu_popup headermenu_popup" id="buytrades_menu" style="display: none">
	<li><a href="my.jsp?item=buytrades${extratid}${extrafid}${extrasrchkey}"><bean:message key="my_trade_trading"/></a></li>
	<li><a href="my.jsp?item=buytrades${extratid}${extrafid}${extrasrchkey}&filter=attention"><bean:message key="my_trade_attention"/></a></li>
	<li><a href="my.jsp?item=buytrades${extratid}${extrafid}${extrasrchkey}&filter=eccredit"><bean:message key="my_trade_eccredit"/></a></li>
	<li><a href="my.jsp?item=buytrades${extratid}${extrafid}${extrasrchkey}&filter=success"><bean:message key="my_trade_success"/></a></li>
	<li><a href="my.jsp?item=buytrades${extratid}${extrafid}${extrasrchkey}&filter=refund"><bean:message key="my_trade_refund"/></a></li>
	<li><a href="my.jsp?item=buytrades${extratid}${extrafid}${extrasrchkey}&filter=closed"><bean:message key="my_trade_closed"/></a></li>
	<li><a href="my.jsp?item=buytrades${extratid}${extrafid}${extrasrchkey}&filter=unstart"><bean:message key="my_trade_unstart"/></a></li>
	<li><a href="my.jsp?item=buytrades${extratid}${extrafid}${extrasrchkey}&filter=all"><bean:message key="my_trade_all"/></a></li>
</ul>
<ul class="popupmenu_popup headermenu_popup" id="tradethreads_menu" style="display: none">
	<li><a href="my.jsp?item=selltrades${extratid}${extrafid}${extrasrchkey}"><bean:message key="my_trade_trading"/></a></li>
	<li><a href="my.jsp?item=selltrades${extratid}${extrafid}${extrasrchkey}&filter=attention"><bean:message key="my_trade_attention"/></a></li>
	<li><a href="my.jsp?item=selltrades${extratid}${extrafid}${extrasrchkey}&filter=eccredit"><bean:message key="my_trade_eccredit"/></a></li>
	<li><a href="my.jsp?item=selltrades${extratid}${extrafid}${extrasrchkey}&filter=success"><bean:message key="my_trade_success"/></a></li>
	<li><a href="my.jsp?item=selltrades${extratid}${extrafid}${extrasrchkey}&filter=refund"><bean:message key="my_trade_refund"/></a></li>
	<li><a href="my.jsp?item=selltrades${extratid}${extrafid}${extrasrchkey}&filter=closed"><bean:message key="my_trade_closed"/></a></li>
	<li><a href="my.jsp?item=selltrades${extratid}${extrafid}${extrasrchkey}&filter=unstart"><bean:message key="my_trade_unstart"/></a></li>
	<li><a href="my.jsp?item=selltrades${extratid}${extrafid}${extrasrchkey}&filter=all"><bean:message key="my_trade_all"/></a></li>
	<li><a href="my.jsp?item=tradethreads${extratid}${extrafid}${extrasrchkey}"><bean:message key="my_trade_view_thread"/></a></li>
</ul>