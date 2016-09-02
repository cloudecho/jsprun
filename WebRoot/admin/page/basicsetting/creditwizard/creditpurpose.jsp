<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<jsp:directive.include file="../../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_creditwizard" /></td></tr>
</table>
<br />
<jsp:include page="lead.jsp"/>
<form method="post" action="admincp.jsp?action=toCreditPurpose" id="creditpurpose">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="ea4f01196671c04f"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_setting_step_menu_3" />
				<a href="###" onclick="collapse_change('49ea602525c7ba54')"><img id="menuimg_49ea602525c7ba54" src="${pageContext.request.contextPath}/images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
			</td>
		</tr>
		<tbody id="menu_49ea602525c7ba54" style="display: yes">
			<tr class="category">
				<td colspan="2">
					<b><bean:message key="a_setting_creditstrans2" /></b>
					<br />
					<bean:message key="a_setting_creditstrans_comment2" />
				</td>
			<tr>
			<tr>
				<td class="altbg1" width="45%">
					<b><bean:message key="a_setting_creditstrans" /></b>
				</td>
				<td class="altbg2">
					<select onchange="$('allowcreditstrans').style.display = this.value != 0 ? '' : 'none'" name="creditstransnew">
						<option value="0">
							<bean:message key="none" />
						</option>
						<c:forEach items="${extcredits}" var="ext">
							<c:if test="${!empty ext.value.title &&!empty ext.value.available}">
								<option value="${ext.key}" ${creditstransValue==ext.key?'selected':'' }>
									extcredits${ext.key}(${ext.value.title})
								</option>
							</c:if>
						</c:forEach>
					</select>
			</tr>
			<tr>
				<td class="altbg1" width="45%">
					<b><bean:message key="a_setting_creditstax" /></b>
					<br />
					<bean:message key="a_setting_creditstax_comment" />
				</td>
				<td class="altbg2">
					<input name="creditstaxnew" id="creditstaxnew" type="text" size="8"
						value="${settings.creditstax}">
					<br />
					<input name="creditstaxradio" class="radio" type="radio" value="0.01" onclick="$('creditstaxnew').value = this.value" ${settings.creditstax==0.01?'checked':''} >
					<bean:message key="low" /> (0.01)
					<br />
					<input name="creditstaxradio" class="radio" type="radio" value="0.1" onclick="$('creditstaxnew').value = this.value" ${settings.creditstax==0.1?'checked':''} >
					<bean:message key="middle" /> (0.1)
					<br />
					<input name="creditstaxradio" class="radio" type="radio" value="0.5" onclick="$('creditstaxnew').value = this.value" ${settings.creditstax==0.5?'checked':''} >
					<bean:message key="high" /> (0.5)
				</td>
			</tr>

			<tr class="category">
				<td colspan="2">
					<b><bean:message key="a_setting_exchange" /></b>
					<br />
					<bean:message key="a_setting_exchange_comment" />
				</td>
			<tr>
			<tr>
				<td class="altbg1" width="45%">
					<b><bean:message key="a_setting_exchangemincredits" /></b>
				</td>
				<td class="altbg2">
					<input name="exchangemincreditsnew" id="exchangemincreditsnew" type="text" size="8" value="${settings.exchangemincredits }">
					<br />
					<input name="exchangemincreditsradio" class="radio" type="radio" value="100" onclick="$('exchangemincreditsnew').value = this.value" ${settings.exchangemincredits==100?'checked':''} >
					<bean:message key="low" /> (100)
					<br />
					<input name="exchangemincreditsradio" class="radio" type="radio" value="1000" onclick="$('exchangemincreditsnew').value = this.value" ${settings.exchangemincredits==1000?'checked':''} >
					<bean:message key="middle" /> (1000)
					<br />
					<input name="exchangemincreditsradio" class="radio" type="radio" value="5000" onclick="$('exchangemincreditsnew').value = this.value" ${settings.exchangemincredits==5000?'checked':''} >
					<bean:message key="high" /> (5000)
				</td>
			</tr>
	</table>

	<div id="allowcreditstrans" style="display: ${creditstransValue == 0 ? 'none' : ''}">
		<br />
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="tableborder">
			<tr class="header">
				<td colspan="2">
					<b><bean:message key="a_setting_allowcreditstrans" /></b>
				</td>
			</tr>

			<tr class="category">
				<td colspan="2">
					<b><bean:message key="a_setting_transfer" /></b>
					<br />
					<bean:message key="a_setting_transfer_comment" />
				</td>
			<tr>
			<tr>
				<td class="altbg1" width="45%">
					<b><bean:message key="a_setting_transfermincredits" /></b>
				</td>
				<td class="altbg2">
					<input name="transfermincreditsnew" id="transfermincreditsnew" type="text" size="8" value="${settings.transfermincredits}">
					<br />
					<input name="transfermincreditsradio" class="radio" type="radio" value="100" onclick="$('transfermincreditsnew').value = this.value" ${settings.transfermincredits=='100'?'checked':''} >
					<bean:message key="low" /> (100)
					<br />
					<input name="transfermincreditsradio" class="radio" type="radio" value="1000" onclick="$('transfermincreditsnew').value = this.value" ${settings.transfermincredits=='1000'?'checked':''}>
					<bean:message key="middle" /> (1000)
					<br />
					<input name="transfermincreditsradio" class="radio" type="radio" value="5000" onclick="$('transfermincreditsnew').value = this.value" ${settings.transfermincredits=='5000'?'checked':''}>
					<bean:message key="high" /> (5000)
				</td>
			</tr>

			<tr class="category">
				<td colspan="2">
					<b><bean:message key="a_setting_sell" /></b>
					<br />
					<bean:message key="a_setting_sell_comment" />
				</td>
			<tr>
			<tr>
				<td class="altbg1" width="45%">
					<b><bean:message key="a_setting_maxincperthread" /></b>
				</td>
				<td class="altbg2">
					<input name="maxincperthreadnew" id="maxincperthreadnew" type="text" size="8" value="${settings.maxincperthread}">
					<br />
					<input name="maxincperthreadradio" class="radio" type="radio" value="0" onclick="$('maxincperthreadnew').value = this.value" ${settings.maxincperthread=='0'?'checked':''}>
					<bean:message key="nolimit" /> (0)
					<br />
					<input name="maxincperthreadradio" class="radio" type="radio" value="10" onclick="$('maxincperthreadnew').value = this.value" ${settings.maxincperthread=='10'?'checked':''}>
					<bean:message key="low" /> (10)
					<br />
					<input name="maxincperthreadradio" class="radio" type="radio" value="50" onclick="$('maxincperthreadnew').value = this.value" ${settings.maxincperthread=='50'?'checked':''}>
					<bean:message key="middle" /> (50)
					<br />
					<input name="maxincperthreadradio" class="radio" type="radio" value="100" onclick="$('maxincperthreadnew').value = this.value" ${settings.maxincperthread=='100'?'checked':''}>
					<bean:message key="high" /> (100)
				</td>
			</tr>
			<tr>
				<td class="altbg1" width="45%">
					<b><bean:message key="a_setting_maxchargespan" /></b>
				</td>
				<td class="altbg2">
					<input name="maxchargespannew" id="maxchargespannew" type="text" size="8" value="${settings.maxchargespan}">
					<br />
					<input name="maxchargespanradio" class="radio" type="radio" value="0" onclick="$('maxchargespannew').value = this.value" ${settings.maxchargespan=='0'?'checked':''}>
					<bean:message key="nolimit" /> (0)
					<br />
					<input name="maxchargespanradio" class="radio" type="radio" value="5" onclick="$('maxchargespannew').value = this.value" ${settings.maxchargespan=='5'?'checked':''}>
					<bean:message key="low" /> (5)
					<br />
					<input name="maxchargespanradio" class="radio" type="radio" value="24" onclick="$('maxchargespannew').value = this.value" ${settings.maxchargespan=='24'?'checked':''}>
					<bean:message key="middle" /> (24)
					<br />
					<input name="maxchargespanradio" class="radio" type="radio" value="48" onclick="$('maxchargespannew').value = this.value" ${settings.maxchargespan=='48'?'checked':''}>
					<bean:message key="high" /> (48)
				</td>
			</tr>

			<tr class="category">
				<td colspan="2">
					<b><bean:message key="a_setting_reward" /></b>
					<br />
					<bean:message key="a_setting_reward_comment" />
				</td>
			</tr>

			<tr class="category">
				<td colspan="2">
					<b><bean:message key="a_setting_ratio" /></b>
					<br />
					<bean:message key="a_setting_ratio_comment" />
				</td>
			</tr>
			<tr class="category">
				<td colspan="2">
					<b><bean:message key="a_setting_trade" /></b>
					<br />
					<bean:message key="a_setting_trade_comment" />
				</td>
			</tr>
		</table>
	</div>
	<br />
	<center>
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../../cp_footer.jsp" />