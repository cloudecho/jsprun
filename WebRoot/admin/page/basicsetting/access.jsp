<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_access"/></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=access">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<input type="hidden" name="updateTesttt" value="access" />
	<a name="2d7ca6b1b167f4e7"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_register"/><a href="###" onclick="collapse_change('2d7ca6b1b167f4e7')"><img id="menuimg_2d7ca6b1b167f4e7" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_2d7ca6b1b167f4e7" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_regstatus"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_regstatus_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="regstatus" value="0" onclick="$('showinvite').style.display = 'none';" ${settings.regstatus==0?"checked":""}> <bean:message key="a_setting_register_close"/><br />
					<input class="radio" type="radio" name="regstatus" value="1" onclick="$('showinvite').style.display = 'none';" ${settings.regstatus==1?"checked":""}> <bean:message key="a_setting_register_open"/><br />
					<input class="radio" type="radio" name="regstatus" value="2" onclick="$('showinvite').style.display = '';" ${settings.regstatus==2?"checked":""}> <bean:message key="a_setting_register_invite"/><br />
					<input class="radio" type="radio" name="regstatus" value="3" onclick="$('showinvite').style.display = '';" ${settings.regstatus==3?"checked":""}> <bean:message key="a_setting_register_open_invite"/><br />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="showinvite" style="display: ${settings.regstatus >1 ?'':'none'}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_register_invite_credit"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_register_invite_credit_comment"/></span>
				</td>
				<td class="altbg2">
					<select name="inviterewardcredit">
						<option value="0"><bean:message key="none"/></option><c:forEach items="${extcredits}" var="extcredit"><option value="${extcredit.key}" ${inviteconfig.inviterewardcredit==extcredit.key?"selected":""}>extcredit${extcredit.key}<c:if test="${extcredit.value.available==1}">(${extcredit.value.title})</c:if></option></c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_register_invite_addcredit"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_register_invite_addcredit_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="inviteaddcredit" value="${inviteconfig.inviteaddcredit}" onfocus="this.value='${inviteconfig.inviteaddcredit}'" onKeyUp="this.value=this.value.replace(/\D/gi,'');">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_register_invite_invitedcredit"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_register_invite_invitedcredit_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="invitedaddcredit" value="${inviteconfig.invitedaddcredit }" onfocus="this.value='${inviteconfig.invitedaddcredit }'" onKeyUp="this.value=this.value.replace(/\D/gi,'');">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_register_invite_addfriend"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_register_invite_addfriend_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="inviteaddbuddy" value="1" checked><bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="inviteaddbuddy" value="0" ${inviteconfig.inviteaddbuddy!=1?"checked":""}><bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_register_invite_group"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_register_invite_group_comment"/></span>
				</td>
				<td class="altbg2">
					<select name="invitegroupid">
						<option value="0" ${inviteconfig.invitegroupid == 0 ? "selected" : ""}><bean:message key="usergroups_system_0"/></option><c:forEach items="${specialGroups}" var="specialGroup"><option value="${specialGroup.groupid}" ${inviteconfig.invitegroupid == specialGroup.groupid?"selected":""}>${specialGroup.grouptitle}</option></c:forEach>
					</select>
				</td>
			</tr>
		</tbody>
		<tbody>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_reg_name"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_reg_name_comment"/></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="regname" value="${settings.regname}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_reglink_name"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_reglink_name_comment"/></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="reglinkname" value="${settings.reglinkname}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_register_advanced"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_register_advanced_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="regadvance" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="regadvance" value="0" ${settings.regadvance!=1?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_censoruser"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_censoruser_comment"/> </span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('censoruser', 1)"/>
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('censoruser', 0)"/>
					<br />
					<textarea rows="6" name="censoruser" id="censoruser" cols="50">${settings.censoruser}</textarea>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_regverify"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_regverify_comment"/></span>
				</td>
				<td class="altbg2">
					<select name="regverify" style="width: 55%">
						<option value="0" ${settings.regverify==0?"selected":""}><bean:message key="none"/></option>
						<option value="1" ${settings.regverify==1?"selected":""}><bean:message key="a_setting_regverify_email"/></option>
						<option value="2" ${settings.regverify==2?"selected":""}><bean:message key="a_setting_regverify_manual"/></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_doublee"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_doublee_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="doublee" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="doublee" value="0" ${settings.doublee!=1?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_email_allowurl"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_email_allowurl_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('accessemail', 1)"/>
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('accessemail', 0)"/>
					<br />
					<textarea rows="6" name="accessemail" id="accessemail" cols="50">${settings.accessemail}</textarea>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_censoremail"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_censoremail_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('censoremail', 1)"/>
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('censoremail', 0)"/>
					<br />
					<textarea rows="6" name="censoremail" id="censoremail" cols="50">${settings.censoremail}</textarea>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_regctrl"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_regctrl_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="regctrl" value="${settings.regctrl}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_regfloodctrl"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_regfloodctrl_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="regfloodctrl" value="${settings.regfloodctrl}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_newbiespan"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_newbiespan_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="newbiespan" value="${settings.newbiespan}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_welcomemsg"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_welcomemsg_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="welcomemsg" value="0" onclick="$('welcomemsgext').style.display = 'none';" ${settings.welcomemsg==0?"checked":""}> <bean:message key="a_setting_welcomemsg_nosend"/><br />
					<input class="radio" type="radio" name="welcomemsg" value="1" onclick="$('welcomemsgext').style.display = '';" ${settings.welcomemsg==1?"checked":""}> <bean:message key="a_setting_welcomemsg_pm"/><br />
					<input class="radio" type="radio" name="welcomemsg" value="2" onclick="$('welcomemsgext').style.display = '';" ${settings.welcomemsg==2?"checked":""}> <bean:message key="a_setting_welcomemsg_email"/><br />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="welcomemsgext" style="display: ${settings.welcomemsg==0?'none':''}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_welcomemsgtitle"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_welcomemsgtitle_comment"/> </span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="welcomemsgtitle" value="${settings.welcomemsgtitle}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_welcomemsgtxt"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_welcomemsgtxt_comment" /></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[welcomemsgtxt]', 1)"/>
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[welcomemsgtxt]', 0)"/>
					<br />
					<textarea rows="6" name="welcomemsgtxt" id="settingsnew[welcomemsgtxt]" cols="50">${settings.welcomemsgtxt}</textarea>
				</td>
			</tr>
		</tbody>
		<tbody>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_bbrules"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_bbrules_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="bbrules" value="1" onclick="$('hidden_settings_bbrules').style.display = '';" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="bbrules" value="0" onclick="$('hidden_settings_bbrules').style.display = 'none';" ${settings.bbrules!=1?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden_settings_bbrules" style="display: ${settings.bbrules==1?'':'none'}">
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_bbrulestxt"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_bbrulestxt_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('bbrulestxt', 1)"/>
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('bbrulestxt', 0)"/>
					<br />
					<textarea rows="6" name="bbrulestxt" id="bbrulestxt" cols="50">${settings.bbrulestxt}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="3bb6adfa126d9b99"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_access"/><a href="###" onclick="collapse_change('3bb6adfa126d9b99')"><img id="menuimg_3bb6adfa126d9b99" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a></td></tr>
		<tbody id="menu_3bb6adfa126d9b99" style="display: yes">
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_ipregctrl"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_ipregctrl_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('ipregctrl', 1)"/>
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('ipregctrl', 0)"/>
					<br />
					<textarea rows="6" name="ipregctrl" id="ipregctrl" cols="50">${settings.ipregctrl}</textarea>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_ipaccess"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_ipaccess_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('ipaccess', 1)"/>
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('ipaccess', 0)"/>
					<br />
					<textarea rows="6" name="ipaccess" id="ipaccess" cols="50">${settings.ipaccess}</textarea>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_adminipaccess"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_adminipaccess_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[adminipaccess]', 1)"/>
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[adminipaccess]', 0)"/>
					<br />
					<textarea rows="6" name="adminipaccess" id="settingsnew[adminipaccess]" cols="50">${settings.adminipaccess}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input type="hidden" name="from" value="${from}">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit"/>">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />