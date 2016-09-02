<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="invite_get"/></div>
<div class="container">
	<div class="content">
		<form method="post" action="invite.jsp?action=buyinvite">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="buysubmit" value="yes" />
			<div class="notice">
				<ul><c:if test="${usergroups.maxinvitenum>0}"><bean:message key="invite_num_tips" arg0="${usergroups.maxinvitenum}" arg1="${myinvitenum}"/></c:if><bean:message key="invite_expiration_tips" arg0="${usergroups.maxinviteday}"/></ul>
			</div>
			<div class="mainbox formbox">
				<h1><bean:message key="invite_get"/></h1>
				<table cellspacing="0" cellpadding="0" summary="<bean:message key="invite_get"/>">
					<tr><th><label for="amount"><bean:message key="magics_amount_buy"/></label></th><td><input type="text" size="15" id="amount" name="amount" value="0" onkeyup="calcredit()" /> <em class="tips">( <bean:message key="invite_code_price"/> <strong>${usergroups.inviteprice} ${extcredits[creditstrans].title}</strong> )</em></td></tr>
					<tr><th><bean:message key="invite_buy_credit"/></th><td><strong id="desamount">0</strong> ${extcredits[creditstrans].title}</td></tr>
					<tr><th>&nbsp;</th><td><button type="submit" class="submit" name="buysubmit" value="buysubmit"><bean:message key="invite_buy"/></button></td></tr>
				</table>
			</div>
			<script type="text/javascript">
			function calcredit() {
				var amount = parseInt($('amount').value);
				$('desamount').innerHTML = !isNaN(amount) ? Math.floor(amount * ${usergroups.inviteprice}) : 0;
			}
			</script>
		</form>
	</div>
	<div class="side"><jsp:include flush="true" page="personal_navbar.jsp" /></div>
</div>
<jsp:include flush="true" page="footer.jsp" />