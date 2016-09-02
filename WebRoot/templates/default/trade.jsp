<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="trade_confirm_buy" /></div>
<script src="include/javascript/viewthread.js" type="text/javascript"></script>
<script type="text/javascript">
zoomstatus = parseInt(${settings.zoomstatus});
var feevalue = 0;
var price = ${trade.price};
lang['zoom_image'] = '<bean:message key="zoom_image"/>';
lang['a_system_js_newwindow_blank'] = '<bean:message key="a_system_js_newwindow_blank"/>';
lang['full_size'] = '<bean:message key="full_size"/>';
lang['closed'] = '<bean:message key="closed"/>';
lang['copy_to_cutedition'] = '<bean:message key="copy_to_cutedition"/>';
</script>

<form method="post" id="tradepost" name="tradepost" action="trade.jsp?action=trade&tid=${trade.tid}&pid=${trade.pid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox viewthread tradeinfo">
		<h1>${trade.subject}</h1>
		<div class="tradethumb"><a href="viewthread.jsp?do=tradeinfo&tid=${trade.tid}&pid=${trade.pid}">
			<c:choose>
				<c:when test="${trade.aid>0}"><img src="attachment.jsp?aid=${trade.aid}" onload="thumbImg(this)" width="80" height="80" alt="${trade.subject}" /></c:when>
				<c:otherwise><img src="${styles.IMGDIR}/trade_nophotosmall.gif" alt="${trade.subject}" /></c:otherwise>
			</c:choose>
			</a>
		</div>
		<div class="tradeattribute">
			<dl>
				<dt><bean:message key="trade_price" />:</dt>
				<dd><strong>${trade.price}</strong>&nbsp;<bean:message key="rmb_yuan" /></dd>
				<c:if test="${!empty trade.locus}"><dt><bean:message key="post_trade_locus" />:</dt><dd>${trade.locus}</dd></c:if>
				<dt><bean:message key="tradelog_seller" />:</dt>
				<dd><a href="space.jsp?action=viewpro&uid=${trade.sellerid}" target="_blank">${trade.seller}</a> <c:if test="${!empty trade.account}"><a href="http://shop1.paipai.com/cgi-bin/credit_info?uin=${trade.account}" target="_blank"><img src="${styles.IMGDIR}/tenpaysmall.gif" border="0" /><bean:message key="payto_creditinfo" /></a></c:if></dd>
			</dl>
		</div>
	</div>
<div class="mainbox formbox">
		<h3><bean:message key="trade_confirm_buy" /></h3>
		<table summary="<bean:message key="trade_confirm_buy" />" cellspacing="0" cellpadding="0">
			<tr>
				<th><bean:message key="trade_credits_total" /></th>
				<td><strong id="caculate"></strong>&nbsp;<bean:message key="rmb_yuan" /></td>
			</tr>
			<tr>
				<th><label for="number"><bean:message key="magics_amount_buy" /></label></th>
				<td><input type="text" id="number" name="number" onkeyup="calcsum()" value="1" /></td>
			</tr>
			<tr>
				<th><bean:message key="post_trade_transport_type" /></th>
				<td>
					<p>
					<c:choose>
						<c:when test="${trade.transport == 1}"><input type="hidden" name="transport" value="1"><bean:message key="post_trade_transport_seller" /></c:when>
						<c:when test="${trade.transport == 2}"><input type="hidden" name="transport" value="2"><bean:message key="post_trade_transport_buyer" /></c:when>
						<c:when test="${trade.transport == 3}"><input type="hidden" name="transport" value="3"><bean:message key="post_trade_transport_virtual" /></c:when>
						<c:when test="${trade.transport == 4}"><input type="hidden" name="transport" value="4"><bean:message key="post_trade_transport_physical" /></c:when>
					</c:choose>
					</p>
					<c:if test="${trade.transport == 1||trade.transport == 2||trade.transport == 4}">
						<c:if test="${trade.ordinaryfee > 0}"> <label><input class="radio" type="radio" name="fee" value="1" checked="checked" <c:if test="${trade.transport == 2}">onclick="feevalue = ${trade.ordinaryfee};calcsum()"</c:if> /> <bean:message key="post_trade_transport_mail" /> ${trade.ordinaryfee} <bean:message key="rmb_yuan" /></label><c:if test="${trade.transport == 2}"><script type="text/javascript">feevalue = ${trade.ordinaryfee}</script></c:if></c:if>
						<c:if test="${trade.expressfee > 0}"> <label><input class="radio" type="radio" name="fee" value="3" checked="checked" <c:if test="${trade.transport == 2}">onclick="feevalue = ${trade.expressfee};calcsum()"</c:if> /> <bean:message key="post_trade_transport_express" /> ${trade.expressfee} <bean:message key="rmb_yuan" /></label><c:if test="${trade.transport == 2}"><script type="text/javascript">feevalue = ${trade.expressfee}</script></c:if></c:if>
						<c:if test="${trade.emsfee > 0}"> <label><input class="radio" type="radio" name="fee" value="2" checked="checked" <c:if test="${trade.transport == 2}">onclick="feevalue = ${trade.emsfee};calcsum()"</c:if> /> <bean:message key="ems" /> ${trade.emsfee} <bean:message key="rmb_yuan" /></label><c:if test="${trade.transport == 2}"><script type="text/javascript">feevalue = ${trade.emsfee}</script></c:if></c:if>
					</c:if>
				</td>
			</tr>
			<tr>
				<th><bean:message key="post_trade_paymethod" /></th>
				<td>
					<c:choose>
						<c:when test="${jsprun_uid==0}"><label><input type="hidden" name="offline" value="0" checked="checked" /> <bean:message key="trade_pay_alipay" /></label></c:when>
						<c:when test="${empty trade.account}"><input type="hidden" name="offline" value="1" checked="checked" /> <bean:message key="a_extends_tradelog_offline" /></c:when>
						<c:otherwise><label><input type="radio" class="radio" name="offline" value="0" checked="checked" /> <bean:message key="trade_pay_alipay" /></label> <label><input type="radio" class="radio" name="offline" value="1" /> <bean:message key="a_extends_tradelog_offline" /></label></c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th><label for="buyername"><bean:message key="trade_buyername" /></label></th>
				<td><input type="text" id="buyername" name="buyername" maxlength="50" value="${lastbuyerinfo.buyername}" /></td>
			</tr>
			<tr>
				<th><label for="buyercontact"><bean:message key="trade_buyercontact" /></label></th>
				<td><input type="text" id="buyercontact" name="buyercontact" maxlength="100" size="80" value="${lastbuyerinfo.buyercontact}" /></td>
			</tr>
			<tr>
				<th><label for="buyerzip"><bean:message key="trade_buyerzip" /></label></th>
				<td><input type="text" id="buyerzip" name="buyerzip" maxlength="10" value="${lastbuyerinfo.buyerzip}" /></td>
			</tr>
			<tr>
				<th><label for="buyerphone"><bean:message key="trade_buyerphone" /></label></th>
				<td><input type="text" id="buyerphone" name="buyerphone" maxlength="20" value="${lastbuyerinfo.buyerphone}" /></td>
			</tr>
			<tr>
				<th><label for="buyermobile"><bean:message key="trade_buyermobile" /></label></th>
				<td><input type="text" id="buyermobile" name="buyermobile" maxlength="20" value="${lastbuyerinfo.buyermobile}" /></td>
			</tr>
			<tr>
				<th valign="top"><label for="buyermsg"><bean:message key="trade_seller_remark" /></label><div class="tips"><bean:message key="trade_seller_remark_comment" arg0="200"/></div></th>
				<td><textarea id="buyermsg" name="buyermsg" style="width: 95%" rows="3">${lastbuyerinfo.buyermsg}</textarea></td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<button class="submit" type="submit" name="tradesubmit" value="true"><bean:message key="trade_buy_confirm" /></button>
					<c:if test="${jsprun_uid==0}"><em class="tips"><bean:message key="trade_guest_alarm" /></em></c:if>
				</td>
			</tr>
		</table>
	</div>
</form>

<script type="text/javascript">
function calcsum() {
	$('caculate').innerHTML = (price * $('tradepost').number.value + feevalue);
}
calcsum();
</script>
<jsp:include flush="true" page="footer.jsp" />