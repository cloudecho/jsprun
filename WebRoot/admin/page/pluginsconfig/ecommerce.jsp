<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="a_setting_ecommerce" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=ecommerce">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<c:if test="${param.from=='creditwizard'}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td><bean:message key="menu_tool_creditwizard" /></td></tr>
			<tr><td><a href="admincp.jsp?action=creditwizard"><bean:message key="a_setting_step_menu_1" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=toCreditExpression"><bean:message key="a_setting_step_menu_2" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=toCreditPurpose"><bean:message key="a_setting_step_menu_3" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=settings&do=ecommerce&from=creditwizard"><bean:message key="a_setting_ecommerce" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="admincp.jsp?action=tenpay&from=creditwizard"><bean:message key="a_setting_cplog_action_alipay" /></a></td></tr>
		</table>
		<br />
	</c:if>
	<a name="d72c9fd6cc9d97a8"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_ecommerce_sub_credittrade" /><a href="###" onclick="collapse_change('d72c9fd6cc9d97a8')"><img id="menuimg_d72c9fd6cc9d97a8" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_d72c9fd6cc9d97a8" style="display: yes">
			<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_alipay_ratio" /></b><br /><span class="smalltxt"><bean:message key="a_setting_alipay_ratio_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ec_ratio" value="${settings.ec_ratio}"></td></tr>
			<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_alipay_mincredits" /></b><br /><span class="smalltxt"><bean:message key="a_setting_alipay_mincredits_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ec_mincredits" value="${settings.ec_mincredits}"></td></tr>
			<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_alipay_maxcredits" /></b><br /><span class="smalltxt"><bean:message key="a_setting_alipay_maxcredits_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ec_maxcredits" value="${settings.ec_maxcredits}"></td></tr>
			<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_alipay_maxcreditspermonth" /></b><br /><span class="smalltxt"><bean:message key="a_setting_alipay_maxcreditspermonth_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ec_maxcreditspermonth" value="${settings.ec_maxcreditspermonth}"></td></tr>
		</tbody>
	</table>
	<br />
	<a name="a4e6de9347995ed5"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_ecommerce_sub_goodstrade" /><a href="###" onclick="collapse_change('a4e6de9347995ed5')"><img id="menuimg_a4e6de9347995ed5" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_a4e6de9347995ed5" style="display: yes">
			<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_trade_biosize" /></b><br /><span class="smalltxt"><bean:message key="a_setting_trade_biosize_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="maxbiotradesize" value="${settings.maxbiotradesize}"></td></tr>
			<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_trade_imagewidthheight" /></b><br /><span class="smalltxt"><bean:message key="a_setting_trade_imagewidthheight_comment" /></span></td><td class="altbg2"><input name="tradeimagewidth" size="20" value="${settings.tradeimagewidth}"> <span style="vertical-align: middle">X</span> <input name="tradeimageheight" size="20" value="${settings.tradeimageheight}"></td></tr>
			<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_trade_type" /></b><br /><span class="smalltxt"><bean:message key="a_setting_trade_type_comment" /></span></td><td class="altbg2">${tradetypeselect}</td></tr>
		</tbody>
	</table>
	<br />
	<center><input type="hidden" name="from" value="creditwizard"><input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />