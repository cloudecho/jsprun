<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_ecommerce_credit" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<div style="float: left; margin-left: 0px; padding-top: 8px"><a href="###" onclick="collapse_change('tip');return false"><bean:message key="tips" /></a></div>
			<div style="float: right; margin-right: 4px; padding-bottom: 9px"><a href="###" onclick="collapse_change('tip');return false"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /></a></div>
		</td>
	</tr>
	<tbody id="menu_tip" style="display:">
		<tr><td><bean:message key="a_extends_ec_credit_tips" /></td></tr>
	</tbody>
</table>
<br />
<form method="post" name="settings" action="admincp.jsp?action=ec_credit">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="3464e07e1cec7e5d"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_extends_ec_credit" /><a href="###" onclick="collapse_change('3464e07e1cec7e5d')"><img id="menuimg_3464e07e1cec7e5d" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_3464e07e1cec7e5d" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_extends_ec_credit_maxcreditspermonth" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_extends_ec_credit_maxcreditspermonth_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxcreditspermonth" value="${maxcreditspermonth}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="5"><bean:message key="a_extends_ec_credit_rank" /></td></tr>
		<tr class="category" align="center">
			<td><bean:message key="a_extends_ec_credit_rank" /></td>
			<td><bean:message key="a_extends_ec_credit_mincredits" /></td>
			<td><bean:message key="a_extends_ec_credit_maxcredits" /></td>
			<td><bean:message key="a_extends_ec_credit_sellericon" /></td>
			<td><bean:message key="a_extends_ec_credit_buyericon" /></td>
		</tr>
		<c:forEach items="${ranks}" var="rank">
			<tr align="center">
				<td class="altbg1">${rank.key}</td>
				<td class="altbg2"><input type="text" size="12" name="rank" value="${rank.value}"></td>
				<td class="altbg1"><jrun:showrank ranks="${ranks}" num="${rank.key}"/></td>
				<td class="altbg2"><img src="images/rank/seller/${rank.key}.gif" border="0"></td>
				<td class="altbg1"><img src="images/rank/buyer/${rank.key}.gif" border="0"></td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="creditsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />