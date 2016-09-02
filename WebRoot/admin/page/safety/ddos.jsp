<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="memu_safety_ddos" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
			<tr>
				<td><bean:message key="a_safety_ddos_tips_comment" />
				</td>
			</tr>
		</tbody>
	</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=safety&do=ddos">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="a45b0b68771fc480"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="header_basic" /><a href="###" onclick="collapse_change('a45b0b68771fc480')"><img id="menuimg_a45b0b68771fc480" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_a45b0b68771fc480" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="memu_safety_ddos" />:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_safety_ddos_comment1" /></span>
					<br/><font color="red"><bean:message key="a_safety_comment" /></font>
				</td>
				<td class="altbg2">
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input type="hidden" name="from" value="${from}">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />" disabled="disabled">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />