<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<link href="include/css/keyboard.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
lang["kb_backSpace"] = '← <bean:message key="kb_backSpace"/>';
lang["kb_capsLockOff"] = '<bean:message key="kb_capsLockOff"/>';
lang["kb_capsLockOn"] = '<bean:message key="kb_capsLockOn"/>';
lang["kb_clear"] = '<bean:message key="kb_clear"/>';
lang["kb_enter"] = '←|\n<bean:message key="kb_enter"/>';
lang["kb_title"] = '<bean:message key="kb_title"/>';
</script>
<script src="include/javascript/keyboard.js" type="text/javascript"></script>
<div class="container">
	<div id="foruminfo"><div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="memcp_credits"/></div></div>
	<div class="content"><div class="mainbox formbox">
		<h1><bean:message key="memcp_credits"/></h1>
		<ul class="tabs">
			<c:if test="${settings.exchangestatus>0}"><li ${operation== "exchange"?"class=current":""}><a href="memcp.jsp?action=credits&operation=exchange"><bean:message key="EXC"/></a></li></c:if>
			<c:if test="${settings.transferstatus>0&&usergroups.allowtransfer>0}"><li ${operation== "transfer"?"class=current":""}><a href="memcp.jsp?action=credits&operation=transfer"><bean:message key="memcp_credits_transfer"/></a></li></c:if>
			<c:if test="${settings.ec_ratio>0}"><li ${operation== "addfunds"?"class=current":""}><a href="memcp.jsp?action=credits&operation=addfunds"><bean:message key="credit_payment"/></a></li></c:if>
		</ul>
		<c:choose><c:when test="${operation == 'transfer'}">
			<form id="creditsform" method="post" action="memcp.jsp?action=credits">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<input type="hidden" name="operation" value="transfer" />
				<table summary="<bean:message key="memcp_credits_transfer"/>" cellspacing="0" cellpadding="0" width="100%">
					<tbody>
						<tr><th><label for="password"><bean:message key="passwordf"/></label></th><td><input type="password" size="15" name="password" id="password" /><input type="button" class="keybord_button" style="margin-left: 2px;" onclick="jrunKeyBoard('password',this);" title="<bean:message key="keyboard_title"/>">&nbsp;</td></tr>
						<tr><th><label for="to"><bean:message key="to"/></label></th><td><input type="text" size="15" name="to" id="to" /></td></tr>
						<tr><th><label for="amount">${extcredits[creditstrans].title}</label></th><td><input type="text" size="15" id="amount" name="amount" value="0" onkeyup="calcredit()" /> ${extcredits[creditstrans].unit}</td></tr>
						<tr><th><bean:message key="memcp_credits_transfer_min_balance"/></th><td>${settings.transfermincredits} ${extcredits[creditstrans].unit}</td></tr>
						<tr><th><bean:message key="credits_tax"/></th><td>${taxpercent}</td></tr>
						<tr><th><bean:message key="memcp_credits_income"/></th><td><span id="desamount">0</span> ${extcredits[creditstrans].unit}</td></tr>
						<tr><th valign="top"><label for="transfermessage"><bean:message key="memcp_credits_transfer_message"/></label></th><td><textarea name="transfermessage" id="transfermessage" rows="6" style="width: 85%;"></textarea><div class="tips"><bean:message key="memcp_credits_transfer_message_comment"/></div></td></tr>
					</tbody>
					<tr><th>&nbsp;</th><td><bean:message key="memcp_credits_transfer_comment"/></td></tr>
					<tr class="btns"><th>&nbsp;</th><td><button class="submit" type="submit" name="creditssubmit" id="creditssubmit" value="true" onclick="return confirm('<bean:message key="memcp_credits_confirm"/>');" tabindex="1"><bean:message key="submitf"/></button></td></tr>
				</table>
			</form>
			<script type="text/javascript">
				function calcredit() {
					var amount = parseInt($('amount').value);
					$('desamount').innerHTML = !isNaN(amount) ? Math.floor(amount * (1 - ${settings.creditstax})) : 0;
				}
			</script>
		</c:when><c:when test="${operation == 'exchange'}">
			<form id="creditsform" method="post" action="memcp.jsp?action=credits">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<input type="hidden" name="operation" value="exchange">
				<table summary="<bean:message key="EXC"/>"  cellspacing="0" cellpadding="0" width="100%">
					<tbody>
						<tr><th><label for="password"><bean:message key="passwordf"/></label></th><td><input type="password" size="15" name="password" id="password"/><input type="button" class="keybord_button" style="margin-left: 4px;" onclick="jrunKeyBoard('password',this);" title="<bean:message key="keyboard_title"/>">&nbsp;</td></tr>
						<tr><th><label for="amount"><bean:message key="a_system_logs_credit_send"/></label></th><td><input type="text" size="15" name="amount" id="amount" value="0" onkeyup="calcredit();" />&nbsp;&nbsp;<select name="fromcredits" onChange="calcredit();"><c:forEach items ="${extcredits}" var="credit"><c:if test="${credit.value.allowexchangeout>0 && credit.value.ratio>0.0}"><option value="${credit.key}" unit="${credit.value.unit}" title="${credit.value.title}" ratio="${credit.value.ratio}">${credit.value.title}</option></c:if></c:forEach></select></td></tr>
						<tr><th><label for="desamount"><bean:message key="a_system_logs_credit_receive"/></label></th><td><input type="text" size="15" id="desamount" value="0" disabled />&nbsp;&nbsp;<select name="tocredits" onChange="calcredit();"><c:forEach items ="${extcredits}" var="credit"><c:if test="${credit.value.allowexchangein>0 && credit.value.ratio>0.0}"><option value="${credit.key}" unit="${credit.value.unit}" title="${credit.value.title}" ratio="${credit.value.ratio}">${credit.value.title}</option></c:if></c:forEach></select></td></tr>
						<tr><th><bean:message key="memcp_credits_exchange_ratio"/></th><td><span class="bold">1</span><span id="orgcreditunit"></span><span id="orgcredittitle"></span><bean:message key="credit_exchange"/><span class="bold" id="descreditamount"></span><span id="descreditunit"></span><span id="descredittitle"></span></td></tr>
						<tr><th><bean:message key="memcp_credits_exchange_min_balance"/></th><td>${settings.exchangemincredits}</td></tr>
						<tr><th><bean:message key="credits_tax"/></th><td>${taxpercent}</td></tr>
						<tr><th>&nbsp;</th><td><bean:message key="memcp_credits_exchange_comment"/></td></tr>
					</tbody>
					<tr class="btns"><th>&nbsp;</th><td><button class="submit" type="submit" name="creditssubmit" id="creditssubmit" value="true" onclick="return confirm('<bean:message key="memcp_credits_confirm"/>');" tabindex="2"><bean:message key="submitf"/></button></td></tr>
				</table>
			</form>
			<script type="text/javascript">
			function calcredit() {
				with($('creditsform')) {
					fromcredit = fromcredits[fromcredits.selectedIndex];
					tocredit = tocredits[tocredits.selectedIndex];
					var ratio = Math.round(((fromcredit.getAttribute('ratio') / tocredit.getAttribute('ratio')) * 100)) / 100;
					$('orgcreditunit').innerHTML = fromcredit.getAttribute('unit');
					$('orgcredittitle').innerHTML = fromcredit.getAttribute('title');
					$('descreditunit').innerHTML = tocredit.getAttribute('unit');
					$('descredittitle').innerHTML = tocredit.getAttribute('title');
					$('descreditamount').innerHTML = ratio;
					$('amount').value = $('amount').value.toInt();
					if(fromcredit.getAttribute('title') != tocredit.getAttribute('title') && $('amount').value != 0) {
						$('desamount').value = Math.floor(fromcredit.getAttribute('ratio') / tocredit.getAttribute('ratio') * $('amount').value * (1 - ${settings.creditstax}));
					} else {
						$('desamount').value = $('amount').value;
					}
				}
			}
			String.prototype.toInt = function() {
				var s = parseInt(this);
				return isNaN(s) ? 0 : s;
			}
			calcredit();
			</script>
		</c:when><c:when test="${operation=='addfunds'}">
			<form id="creditsform" method="post" action="memcp.jsp?action=credits" target="_blank">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<input type="hidden" name="operation" value="addfunds" />
				<table summary="<bean:message key="credit_payment"/>" cellspacing="0" cellpadding="0" width="100%">
					<tbody>
						<tr><th><bean:message key="memcp_credits_addfunds_rules"/></th><td><bean:message key="memcp_credits_addfunds_rules_ratio"/> = ${extcredits[creditstrans].title} <b>${settings.ec_ratio}</b> ${extcredits[creditstrans].unit}<c:if test="${settings.ec_mincredits>0}"><br /><bean:message key="memcp_credits_addfunds_rules_min"/> ${extcredits[creditstrans].title} <b>${settings.ec_mincredits}</b> ${extcredits[creditstrans].unit}</c:if><c:if test="${settings.ec_maxcredits>0}"><br /><bean:message key="memcp_credits_addfunds_rules_max"/> ${extcredits[creditstrans].title} <b>${settings.ec_maxcredits}</b> ${extcredits[creditstrans].unit}</c:if><c:if test="${settings.ec_maxcreditspermonth>0}"><br /><bean:message key="memcp_credits_addfunds_rules_month"/> ${extcredits[creditstrans].title} <b>${settings.ec_maxcreditspermonth}</b> ${extcredits[creditstrans].unit}</c:if></td></tr>
						<tr><th>${extcredits[creditstrans].title} <bean:message key="memcp_credits_addfunds_amount"/></th><td><input type="text" size="15" id="amount" name="amount" value="0" onkeyup="calcredit()" /> ${extcredits[creditstrans].unit}</td></tr>
						<tr><th><bean:message key="memcp_credits_addfunds_caculate"/></th><td><bean:message key="memcp_credits_addfunds_caculate_radio"/></td></tr>
						<tr><th>&nbsp;</th><td><bean:message key="memcp_credits_addfunds_comment"/></td></tr>
					</tbody>
					<tr class="btns"><th>&nbsp;</th><td><button class="submit" type="submit" name="creditssubmit" id="creditssubmit" value="true" tabindex="3"><bean:message key="submitf"/></button></td></tr>
				</table>
			</form>
			<script type="text/javascript">
				function calcredit() {
					var amount = parseInt($('amount').value);
					$('desamount').innerHTML = !isNaN(amount) ? Math.round(((amount / ${settings.ec_ratio}) * 10)) / 10 : 0;
				}
			</script>
		</c:when></c:choose>
	</div></div>
	<div class="side"><jsp:include flush="true" page="personal_navbar.jsp" /></div>
</div>
<jsp:include flush="true" page="footer.jsp" />