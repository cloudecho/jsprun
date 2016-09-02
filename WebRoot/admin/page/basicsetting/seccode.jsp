<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_seccode" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=seccode">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
			<tr>
				<td>
					<ul>
						<li><bean:message key="a_setting_seccode_tips3" />
						<li><bean:message key="a_setting_seccode_tips4" />
					</ul>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="b731097ca1d6cb40"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_seccode" /><a href="###" onclick="collapse_change('b731097ca1d6cb40')"><img id="menuimg_b731097ca1d6cb40" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_b731097ca1d6cb40" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_seccodestatus" /></b><br /><span class="smalltxt"><bean:message key="a_setting_seccodestatus_comment" /><br /><span style="cursor: pointer" id="seccodeimage"></span><br/><a href="###" onclick="updateseccode()"><bean:message key="a_setting_flush" /></a></span><script language="JavaScript">var seccodedata = [${seccodedata["width"]}, ${seccodedata["height"]},${seccodedata["type"]}];updateseccode()</script></td>
				<td class="altbg2">
					<input class="checkbox" type="checkbox" ${seccodestatus0} name="seccodestatus0" value="1"> <bean:message key="a_setting_seccodestatus_register" /><br />
					<input class="checkbox" type="checkbox" ${seccodestatus1} name="seccodestatus1" value="2"> <bean:message key="a_setting_seccodestatus_login" /><br />
					<input class="checkbox" type="checkbox" ${seccodestatus2} name="seccodestatus2" value="4"> <bean:message key="a_setting_seccodestatus_post" /><br />
					<input class="checkbox" type="checkbox" ${seccodestatus3} name="seccodestatus3" value="8"> <bean:message key="pm_send" /><br />
					<input class="checkbox" type="checkbox" ${seccodestatus4} name="seccodestatus4" value="16"> <bean:message key="a_setting_seccodestatus_profile" /><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_seccodeminposts" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_seccodeminposts_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="minposts" value="${seccodedata.minposts}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_seccodeloginfailedcount" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_seccodeloginfailedcount_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="loginfailedcount" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;									
					<input class="radio" type="radio" name="loginfailedcount" value="0" ${seccodedata.loginfailedcount!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_seccodewidth" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_seccodewidth_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="width" value="${seccodedata.width }"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_seccodeheight" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_seccodeheight_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="height" value="${seccodedata.height}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input type="hidden" name="from" value="${from}">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />