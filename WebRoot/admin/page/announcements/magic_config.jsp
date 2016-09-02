<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_magic"/></td></tr>
</table>
<br />
<form method="post" name="settings" action="admincp.jsp?action=magic_config">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="5190e8a345857204"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_other_magics_config"/><a href="###" onclick="collapse_change('5190e8a345857204')"><img id="menuimg_5190e8a345857204" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_5190e8a345857204" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_other_magics_open"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_magics_open_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="magicstatus" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="magicstatus" value="0" ${settings.magicstatus !=1?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_other_magics_market_open"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_magics_market_open_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="magicmarket" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="magicmarket" value="0" ${settings.magicmarket !=1?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_other_magics_market_percent"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_magics_market_percent_comment"/></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxmagicprice" value="${settings.maxmagicprice }"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<center><input type="submit" class="button" name="magicsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />