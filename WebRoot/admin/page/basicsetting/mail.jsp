<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_mail" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=mail">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" id="operation" name="operation" value="mail">
	<a name="ce6532c015751ae2"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_mail" /><a href="###" onclick="collapse_change('ce6532c015751ae2')"><img id="menuimg_ce6532c015751ae2" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_ce6532c015751ae2" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_mail_send" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" id="mailsendRadio2" name="mailsend" value="2" ${mail.mailsend==2?'checked':'' } onclick="$('hidden1').style.display = '';$('hidden2').style.display = '';"> <bean:message key="a_setting_mail_send_2" />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden1" style="display: ${mail.mailsend==1?'none':''}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_mail_server" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_mail_server_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="server" value="${mail.server}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_mail_port" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_mail_port_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="port" value="${mail.port}"></td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden2" style="display: ${mail.mailsend==1||mail.mailsend==3?'none':''}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_mail_auth" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_mail_auth_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="auth" value="1" ${mail.auth==1?'checked':''}> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="auth" value="0" ${mail.auth==0?'checked':''}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_mail_from" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_mail_from_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="from" value="${mail.from}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_mail_username" /></b></td>
				<td class="altbg2"><input type="text" size="50" name="auth_username" value="${mail.auth_username}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_mail_password" /></b></td>
				<td class="altbg2"><input type="text" size="50" name="auth_password" value="${mail.auth_password}"></td>
			</tr>
		</tbody>
		<tbody>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_mail_delimiter" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_mail_delimiter_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="maildelimiter" value="1" ${mail.maildelimiter==1?'checked':''}> <bean:message key="a_setting_mail_delimiter_crlf" /><br />
					<input class="radio" type="radio" name="maildelimiter" value="0" ${mail.maildelimiter==0?'checked':''}> <bean:message key="a_setting_mail_delimiter_lf" /><br />
					<input class="radio" type="radio" name="maildelimiter" value="2" ${mail.maildelimiter==2?'checked':''}> <bean:message key="a_setting_mail_delimiter_cr" /><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_mail_includeuser" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_mail_includeuser_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="mailusername" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="mailusername" value="0" ${mail.mailusername!=1?'checked':''}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_mail_silent" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="sendmail_silent" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="sendmail_silent" value="0" ${mail.sendmail_silent!=1?'checked':''}><bean:message key="no" /> 
				</td>
			</tr>
	</table>
	<br />
	<a name="2b2c0796bbca39d4"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_mail_test" /><a href="###" onclick="collapse_change('2b2c0796bbca39d4')"><img id="menuimg_2b2c0796bbca39d4" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_2b2c0796bbca39d4" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_mail_test_from" /></b></td>
				<td class="altbg2"><input type="text" size="50" name="test_from" value=""></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_mail_test_to" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_mail_test_to_comment" /></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('test_to', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('test_to', 0)">
					<br />
					<textarea rows="6" name="test_to" id="test_to" cols="50"></textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input type="hidden" name="from" value="${from}"><input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />"  onclick="operation.value='mail'">&nbsp;&nbsp;&nbsp;<input class="button" type="submit" name="mailcheck" value="<bean:message key="a_setting_mailcheck" />" onclick="operation.value='mailcheck'">
		<iframe name="mailcheckiframe" style="display: none"></iframe>
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />