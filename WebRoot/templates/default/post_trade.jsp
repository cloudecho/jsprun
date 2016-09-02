<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<script type="text/javascript">
	lang['post_trade_costprice_is_number'] = '<bean:message key="post_trade_costprice_is_number"/>';
	lang['post_trade_price_is_number'] = '<bean:message key="post_trade_price_is_number"/>';
	lang['post_trade_amount_is_number'] = '<bean:message key="post_trade_amount_is_number"/>';
</script>
<input type="hidden" name="trade" value="yes" />
<tr>
	<th style="border-bottom: 0"><label for="item_name"><bean:message key="tradelog_trade_name"/></label></th>
	<td style="border-bottom: 0"><input type="text" id="item_name" name="item_name" size="30" value="${trade.subject}" tabindex="50" /></td>
</tr>
<tr><td id="threadtypes" colspan="2" style="border: 0px; padding: 0px"></td></tr>
<tr>
	<th><label for="item_quality"><bean:message key="trade_type"/></label></th>
	<td>
		<select id="item_quality" name="item_quality" tabindex="51">
			<option value="1" ${trade.quality==1? "selected=selected":""}><bean:message key="trade_new"/></option>
			<option value="2" ${trade.quality==2? "selected=selected":""}><bean:message key="trade_old"/></option>
		</select>

		<select name="item_type" tabindex="52">
			<option value="1" ${trade.itemtype==1? "selected=selected":""}><bean:message key="thread_trade"/></option>
			<option value="2" ${trade.itemtype==2? "selected=selected":""}><bean:message key="trade_type_service"/></option>
			<option value="3" ${trade.itemtype==3? "selected=selected":""}><bean:message key="trade_type_auction"/></option>
			<option value="4" ${trade.itemtype==4? "selected=selected":""}><bean:message key="trade_type_donate"/></option>
			<option value="5" ${trade.itemtype==5? "selected=selected":""}><bean:message key="trade_type_compensate"/></option>
			<option value="6" ${trade.itemtype==6? "selected=selected":""}><bean:message key="trade_type_bonus"/></option>
		</select>
	</td>
</tr>
<c:if test="${allowpostattach}">
	<tr>
		<th><bean:message key="post_trade_picture"/></th>
		<td>
			<input type="file" name="tradeattach" class="absmiddle" size="30" onchange="attachpreview(this, 'tradeattach_preview', 80, 80)" tabindex="53" />
			<div id="tradeattach_preview">
				<c:if test="${!empty trade.attachment}">
					<a href="${url}/${trade.attachment}" target="_blank">
					<c:choose>
						<c:when test="${trade.thumb>0}"><img height="80" src="${url}/${trade.attachment}.thumb.jpg" border="0" alt="" /></c:when>
						<c:otherwise><img height="80" src="${url}/${trade.attachment}" border="0" alt="" /></c:otherwise>
					</c:choose>
					</a>
				</c:if>
			</div>
			<c:if test="${!empty trade.attachment}"><input name="tradeaid" type="hidden" value="${trade.aid}"></c:if>
			<div id="tradeattach_preview_hidden" style="position: absolute; top: -100000px; filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image'); width: 80px; height: 80px"></div>
		</td>
	</tr>
</c:if>
<tr><jsp:include flush="true" page="post_editor.jsp" /></tr>
<c:if test="${(action=='newthread'||(action == 'edit'&&isfirstpost))&&settings.tagstatus>0}">
	<tr>
		<th><label for="tags"><bean:message key="a_post_tag"/></label></th>
		<td>
			<input size="45" type="text" id="tags" name="tags" value="${threadtags}" tabindex="154" />&nbsp;
			<span id="tagselect"></span>
			<em class="tips"><bean:message key="tag_comment"/></em>
		</td>
	</tr>
</c:if>
<thead>
	<tr>
		<th><bean:message key="post_tradeinfo"/></th>
		<td>&nbsp;</td>
	</tr>
</thead>
<tr>
	<th><label for="item_costprice"><bean:message key="post_trade_costprice"/></label></th>
	<td><input type="text" id="item_costprice" name="item_costprice" size="30" value="${trade.costprice}" tabindex="155" /></td>
</tr>
<tr>
	<th><label for="item_price"><bean:message key="post_trade_price"/></label></th>
	<td><input type="text" id="item_price" name="item_price" size="30" value="${trade.price}" tabindex="156" /> <em class="tips"><c:choose><c:when test="${usergroups.mintradeprice>0&&usergroups.maxtradeprice>0}"><bean:message key="tarde_price_bound"/> ${usergroups.mintradeprice} <bean:message key="rmb_yuan"/> - ${usergroups.maxtradeprice} <bean:message key="rmb_yuan"/></c:when><c:otherwise><bean:message key="trade_min_price"/> ${usergroups.mintradeprice} <bean:message key="rmb_yuan"/></c:otherwise></c:choose></em></td>
</tr>
<tr>
	<th><label for="item_locus"><bean:message key="post_trade_locus"/></label></th>
	<td><input type="text" id="item_locus" name="item_locus" size="30" value="${trade.locus}" tabindex="157" /></td>
</tr>
<tr>
	<th><label for="item_number"><bean:message key="post_trade_number"/></label></th>
	<td><input type="text" id="item_number" name="item_number" size="30" value="${trade.amount}" tabindex="158" /></td>
</tr>
<c:choose>
	<c:when test="${settings.ec_account!=''}">
		<tr>
			<th><label for="paymethod"><bean:message key="post_trade_paymethod"/></label></th>
			<td><input type="radio" id="paymethod" name="paymethod"	onclick="$('tradeaccount').style.display = ''" value="1" ${trade.account!=""?"checked":""}/> <bean:message key="trade_pay_alipay"/> <input type="radio" id="paymethod" name="paymethod"	onclick="$('tradeaccount').style.display = 'none'" value="0" ${trade.account==""?"checked=checked":""}/> <bean:message key="a_extends_tradelog_offline"/></td>
		</tr>
		<tbody id="tradeaccount" style='${trade.account==""?"display:none":""}'>
			<tr>
				<th><label for="seller"><bean:message key="post_trade_seller"/></label></th>
				<td><input type="text" id="seller" name="seller" size="30" value="${trade.account}" /></td>
			</tr>
		</tbody>
	</c:when>
	<c:otherwise><input type="hidden" id="seller" name="seller" value="" /></c:otherwise>
</c:choose>
<tr>
	<th valign="top"><bean:message key="post_trade_transport_type"/></th>
	<td>
		<label><input class="radio" type="radio" name="transport" value="3" tabindex="160" ${trade.transport== 3?"checked=checked":""} onclick="$('logisticssetting').style.display='none'" /> <bean:message key="post_trade_transport_virtual"/></label>
		<label><input class="radio" type="radio" name="transport" value="1" tabindex="161" ${trade.transport== 1?"checked=checked":""} onclick="$('logisticssetting').style.display=''" /> <bean:message key="post_trade_transport_seller"/></label>
		<label><input class="radio" type="radio" name="transport" value="2" tabindex="162" ${trade.transport== 2?"checked=checked":""} onclick="$('logisticssetting').style.display=''" /> <bean:message key="post_trade_transport_buyer"/></label>
		<label><input class="radio" type="radio" name="transport" value="4" tabindex="163" ${trade.transport== 4?"checked=checked":""} onclick="$('logisticssetting').style.display=''" /> <bean:message key="trade_type_transport_physical"/></label>
	</td>
</tr>
<tbody id="logisticssetting" style="display:${trade.transport== 3?'none':''}">
	<tr>
		<th valign="top"><bean:message key="trade_transport"/></th>
		<td>
			<bean:message key="post_trade_transport_mail"/> <input type="text" name="postage_mail" size="3" value="${trade.ordinaryfee}" tabindex="164" /> <bean:message key="rmb_yuan"/> <em class="tips">(<bean:message key="post_trade_transport_mail_unit"/>)</em><br />
			<bean:message key="post_trade_transport_express"/> <input type="text" name="postage_express" size="3" value="${trade.expressfee}" tabindex="165" /> <bean:message key="rmb_yuan"/> <em class="tips">(<bean:message key="post_trade_transport_express_unit"/>)</em><br />
			EMS <input type="text" name="postage_ems" size="3" value="${trade.emsfee}" tabindex="166" /> <bean:message key="rmb_yuan"/> <em class="tips">(<bean:message key="post_trade_transport_ems_unit"/>)</em><br />
		</td>
	</tr>
</tbody>
<c:if test="${action=='edit'}">
	<tr>
		<th><bean:message key="trade_start"/></th>
		<td>
			<label><input class="radio" type="radio" name="closed" value="0" tabindex="167" ${trade.closed == 0?"checked=checked":""}><bean:message key="yes"/></label>&nbsp;
			<label><input class="radio" type="radio" name="closed" value="1" tabindex="168" ${trade.closed == 1?"checked=checked":""}><bean:message key="no"/></label>
		</td>
	</tr>
</c:if>
<tr>
	<th><label for="item_expiration"><bean:message key="validity"/></label></th>
	<td>
		<input onclick="showcalendar(event, this, false)" type="text" id="item_expiration" name="item_expiration" size="30" value="${trade.expiration}" tabindex="169">
		<select onchange="this.form.item_expiration.value = this.value">
			<option value=''></option>
			<option value=''><bean:message key="thread_moderations_expiration_unlimit"/></option>
			<option value='${expiration_7days}'><bean:message key="seven_days"/></option>
			<option value='${expiration_14days}'><bean:message key="fourteen_days"/></option>
			<option value='${expiration_month}'><bean:message key="one_month"/></option>
			<option value='${expiration_3months}'><bean:message key="three_months"/></option>
			<option value='${expiration_halfyear}'><bean:message key="180_day"/></option>
			<option value='${expiration_year}'><bean:message key="one_year"/></option>
		</select>
	</td>
</tr>
<script type="text/javascript">
	if(${trade.price>0.0&&tradetaxtype == 2})
	{
		$('realtax').innerHTML = Math.ceil(${trade.price * (tradetaxs / 100)});
	}
</script>