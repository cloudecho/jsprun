<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="trade_order" /></div>
<form method="post" id="tradepost" name="tradepost" action="trade.jsp?orderid=${tradelog.orderid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox formbox">
		<table summary="<bean:message key="trade_order" />" cellspacing="0" cellpadding="0">
			<thead><tr><td colspan="2"><c:choose><c:when test="${tradelog.offline>0}"><bean:message key="a_extends_tradelog_offline" /></c:when><c:otherwise><bean:message key="trade_pay_alipay" /></c:otherwise></c:choose>
			</td></tr></thead>
			<tr>
				<th><bean:message key="a_system_check_status" /></th>
				<td>${tradelog.statusview} (<jrun:showTime timeInt="${tradelog.lastupdate}" replErTime="N/A" timeoffset="${timeoffset}" type="${dateformat} ${timeformat}" />)</td>
			</tr>
			<c:if test="${tradelog.offline>0&&!empty offlinenexts}">
				<tr>
					<th><label for="password"><bean:message key="trade_password" /></label></th>
					<td><input id="password" name="password" type="password" /><input type="button" class="keybord_button" style="margin-left: -24px;" onclick="jrunKeyBoard('password',this);" title="<bean:message key="keyboard_title"/>">&nbsp;</td>
				</tr>
				<tr>
					<th><label for="message"><bean:message key="leaveword" /></label>
					<div class="tips">${trade_message} <bean:message key="trade_seller_remark_comment" arg0="200"/></div>
					</th>
					<td>
						<textarea id="buyermsg" id="message" name="message" style="width: 95%" rows="3"></textarea>
					</td>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<td>
						<c:forEach items="${offlinenexts}" var="offlinenext"><button class="submit" type="button" onclick="$('tradepost').offlinestatus.value = '${offlinenext.key}';$('offlinesubmit').click();">${offlinenext.value}</button>&nbsp;</c:forEach>
						<input type="hidden" name="offlinestatus" value="" />
						<input type="submit" id="offlinesubmit" name="offlinesubmit" style="display: none" />
					</td>
				</tr>
			</c:if>
			<c:choose>
				<c:when test="${isratestatus}">
					<tr>
						<c:choose>
							<c:when test="${tradelog.ratestatus == 3}"><th><bean:message key="eccredit_post_between" /></th><td>&nbsp;</td></c:when>
							<c:when test="${(tradelog.buyerid==jsprun_uid&&tradelog.ratestatus == 1)||(tradelog.sellerid==jsprun_uid&&tradelog.ratestatus == 2)}"><th><bean:message key="eccredit_post_waiting" /></th><td>&nbsp;</td></c:when>
							<c:otherwise>
								<th><c:choose><c:when test="${(tradelog.buyerid==jsprun_uid&&tradelog.ratestatus == 2)||(tradelog.sellerid==jsprun_uid&&tradelog.ratestatus == 1)}"><bean:message key="eccredit_post_already" /></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></th>
								<td>
									<c:choose>
										<c:when test="${tradelog.buyerid==jsprun_uid}"><button class="submit" type="button" onclick="window.open('eccredit.jsp?action=rate&orderid=${tradelog.orderid}&type=0', '', '')"><bean:message key="eccredit1" /></button></c:when>
										<c:when test="${tradelog.sellerid==jsprun_uid}"><button class="submit" type="button" onclick="window.open('eccredit.jsp?action=rate&orderid=${tradelog.orderid}&type=1', '', '')"><bean:message key="eccredit1" /></button></c:when>
									</c:choose>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:when>
				<c:when test="${tradelog.offline==0}">
					<tr>
						<th><bean:message key="trade_alipay_tradeurl" /></th>
						<td>
							<c:choose>
								<c:when test="${tradelog.status == 0&&tradelog.buyerid==jsprun_uid}"><button class="submit" type="button" name="" onclick="window.open('trade.jsp?orderid=${tradelog.orderid}&pay=yes','','')"><bean:message key="trade_online_pay" /></button></c:when>
								<c:otherwise><button class="submit" type="button" onclick="window.open('${loginurl}', '', '')"><bean:message key="trade_order_status" /></button></c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:when>
			</c:choose>
		</table>
	</div>
	<div class="mainbox formbox">
		<h1><bean:message key="trade_order" /></h1>
			<table summary="<bean:message key="trade_order" />" cellspacing="0" cellpadding="0">
				<tr>
					<th><bean:message key="trade_payment" /></th>
					<td><strong>${tradelog.price}</strong> <bean:message key="rmb_yuan" /> <c:if test="${tradelog.status == 0}"><em class="tips">(<bean:message key="trade_payment_comment" />)</em></c:if></td>
				</tr>
				<c:if test="${tradelog.tradeno>0}">
					<tr>
						<th><bean:message key="a_extends_tradelog_trade_no" /></th>
						<td><a href="${loginurl}" target="_blank">${tradelog.tradeno}</a></td>
					</tr>
				</c:if>
				<tr>
					<th><bean:message key="a_extends_tradelog_buyer" /></th>
					<td>
						<a href="space.jsp?action=viewpro&uid=${tradelog.buyerid}" target="_blank">${tradelog.buyer}</a>
						<c:if test="${tradelog.buyerid!=jsprun_uid}">&nbsp;<a href="pm.jsp?action=send&uid=${tradelog.buyerid}" target="_blank">[<bean:message key="send_pm" />]</a></c:if>
					</td>
				</tr>
				<tr>
					<th><bean:message key="tradelog_seller" /></th>
					<td>
						<a href="space.jsp?action=viewpro&uid=${tradelog.sellerid}" target="_blank">${tradelog.seller}</a>
						<c:if test="${!empty tradelog.selleraccount}">&nbsp;<a href="http://shop1.paipai.com/cgi-bin/credit_info?uin=${tradelog.selleraccount}" style="vertical-align: middle;" target="_blank"><img src="${styles.IMGDIR}/tenpaysmall.gif" border="0" alt="<bean:message key="payto_creditinfo" />" /></a></c:if>
						<c:if test="${tradelog.sellerid!=jsprun_uid}">&nbsp;<a href="pm.jsp?action=send&uid=${tradelog.sellerid}" target="_blank">[<bean:message key="send_pm" />]</a></c:if>
					</td>
				</tr>
				<tr>
					<th><bean:message key="tradelog_trade_name" /></th>
					<td><a href="viewthread.jsp?do=tradeinfo&tid=${tradelog.tid}&pid=${tradelog.pid}" target="_blank">${tradelog.subject}</a></td>
				</tr>
				<c:if test="${tradelog.status == 0&&tradelog.sellerid==jsprun_uid}">
					<tr>
						<th><label for="newprice"><bean:message key="trade_baseprice" /></label></th>
						<td><input id="newprice" name="newprice" value="${tradelog.baseprice}"> <bean:message key="rmb_yuan" /></td>
					</tr>
				</c:if>
				<tr>
					<th><label for="newnumber"><bean:message key="magics_amount_buy" /></label></th>
					<td><c:choose><c:when test="${tradelog.status == 0&&tradelog.buyerid==jsprun_uid}"><input id="newnumber" name="newnumber" value="${tradelog.number}"></c:when><c:otherwise>${tradelog.number}</c:otherwise></c:choose></td>
				</tr>
				<tr>
					<th><bean:message key="post_trade_transport_type" /></th>
					<td>
						<c:choose>
							<c:when test="${tradelog.transport == 1}"><bean:message key="post_trade_transport_seller" /></c:when>
							<c:when test="${tradelog.transport == 2}"><bean:message key="post_trade_transport_buyer" /></c:when>
							<c:when test="${tradelog.transport == 3}"><bean:message key="post_trade_transport_virtual" /></c:when>
							<c:when test="${tradelog.transport == 4}"><bean:message key="post_trade_transport_physical" /></c:when>
						</c:choose>
						&nbsp;<bean:message key="trade_transport" /> <c:choose><c:when test="${tradelog.status == 0&&tradelog.sellerid==jsprun_uid}"><input name="newfee" value="${tradelog.transportfee}"></c:when><c:otherwise>${tradelog.transportfee}&nbsp;</c:otherwise></c:choose> <bean:message key="rmb_yuan" />
					</td>
				</tr>
				<c:choose>
					<c:when test="${tradelog.status == 0&&tradelog.buyerid==jsprun_uid}">
						<tr>
							<th><label for="newbuyername"><bean:message key="trade_buyername" /></label></th>
							<td><input id="newbuyername" name="newbuyername" value="${tradelog.buyername}" maxlength="50"></td>
						</tr>
						<tr>
							<th><label for="newbuyercontact"><bean:message key="trade_buyercontact" /></label></th>
							<td><input id="newbuyercontact" name="newbuyercontact" value="${tradelog.buyercontact}" maxlength="50"></td>
						</tr>
						<tr>
							<th><label for="newbuyerzip"><bean:message key="trade_buyerzip" /></label></th>
							<td><input id="newbuyerzip" name="newbuyerzip" value="${tradelog.buyerzip}" maxlength="10"></td>
						</tr>
						<tr>
							<th><label for="newbuyerphone"><bean:message key="trade_buyerphone" /></label></th>
							<td><input id="newbuyerphone" name="newbuyerphone" value="${tradelog.buyerphone}" maxlength="20"></td>
						</tr>
						<tr>
							<th><label for="newbuyermobile"><bean:message key="trade_buyermobile" /></label></th>
							<td><input id="newbuyermobile" name="newbuyermobile" value="${tradelog.buyermobile}" maxlength="20"></td>
						</tr>
						<tr>
							<th><label for="newbuyermsg"><bean:message key="trade_seller_remark" /></label></th>
							<td><textarea id="newbuyermsg" name="newbuyermsg" style="width: 95%" rows="3">${tradelog.buyermsg}</textarea></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th><label for="newbuyername"><bean:message key="trade_buyername" /></label></th>
							<td>${tradelog.buyername}</td>
						</tr>
						<tr>
							<th><label for="newbuyercontact"><bean:message key="trade_buyercontact" /></label></th>
							<td>${tradelog.buyercontact}</td>
						</tr>
						<tr>
							<th><label for="newbuyerzip"><bean:message key="trade_buyerzip" /></label></th>
							<td>${tradelog.buyerzip}</td>
						</tr>
						<tr>
							<th><label for="newbuyerphone"><bean:message key="trade_buyerphone" /></label></th>
							<td>${tradelog.buyerphone}</td>
						</tr>
						<tr>
							<th><label for="newbuyermobile"><bean:message key="trade_buyermobile" /></label></th>
							<td>${tradelog.buyermobile}</td>
						</tr>
						<tr>
							<th><label for="newbuyermsg"><bean:message key="trade_seller_remark" /></label></th>
							<td>${tradelog.buyermsg}</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:if test="${tradelog.status == 0}">
					<tr>
						<th>&nbsp;</th>
						<td><button class="submit" type="submit" name="tradesubmit" value="true"><bean:message key="trade_submit_order" /></button></td>
					</tr>
				</c:if>
		</table>
	</div>
	<c:if test="${tradelog.offline>0&&!empty messages}">
		<div class="mainbox formbox">
			<h1><bean:message key="leaveword" /></h1>
			<table summary="<bean:message key="leaveword" />" cellspacing="0" cellpadding="0">
				<c:forEach items="${messages}" var="message">
					<tr>
						<th valign="top">
							<a href="space.jsp?action=viewpro&uid=${message[0]}" target="_blank">${message[1]}</a><br />
							<div class="tips"><jrun:showTime timeInt="${message[2]}" timeoffset="${timeoffset}" type="${dateformat} ${timeformat}" replErTime="N/A"/></div>
						</th>
						<td>${message[3]}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
</form>
<jsp:include flush="true" page="footer.jsp" />