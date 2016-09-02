<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_serveropti" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=serveropti">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
			<tr><td><ul><li><bean:message key="a_setting_tips" /></ul></td></tr>
		</tbody>
	</table>
	<br />
	<a name="0ae508bee8c15886"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_serveropti" /><a href="###" onclick="collapse_change('0ae508bee8c15886')"><img id="menuimg_0ae508bee8c15886" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_0ae508bee8c15886" style="display: yes">
			<tr>
				<td class="altbg1" width="45%">
					<b><bean:message key="a_settings_gzipcompress" /></b>
					<br>
					<span class="smalltxt"><bean:message key="a_settings_gzipcompress_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" name="gzipcompress" value="1" type="radio" checked> <bean:message key="yes"/> &nbsp; &nbsp; 
					<input class="radio" name="gzipcompress" value="0" type="radio"${settings.gzipcompress!=1?" checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_delayviewcount" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_delayviewcount_comment" /></span>
				</td>
				<td class="altbg2">
					<select name="delayviewcount" style="width: 62%">
						<option value="0" ${settings.delayviewcount==0?"selected":""}><bean:message key="none" /></option>
						<option value="1" ${settings.delayviewcount==1?"selected":""}><bean:message key="a_setting_delayviewcount_thread" /></option>
						<option value="2" ${settings.delayviewcount==2?"selected":""}><bean:message key="a_setting_delayviewcount_attach" /></option>
						<option value="3" ${settings.delayviewcount==3?"selected":""}><bean:message key="a_setting_delayviewcount_thread_attach" /></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_nocacheheaders" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_nocacheheaders_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="nocacheheaders" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="nocacheheaders" value="0" ${settings.nocacheheaders!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_transsidstatus" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_transsidstatus_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="transsidstatus" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="transsidstatus" value="0" ${settings.transsidstatus!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxonlines" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxonlines_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxonlines" value="${settings.maxonlines}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_onlinehold" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_onlinehold_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="onlinehold"
						value="${onlinehold }">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_floodctrl" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_floodctrl_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="floodctrl" value="${settings.floodctrl}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="3b0c39e6a9701b1d"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_search" /><a href="###" onclick="collapse_change('3b0c39e6a9701b1d')"><img id="menuimg_3b0c39e6a9701b1d" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_3b0c39e6a9701b1d" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_searchctrl" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_searchctrl_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="searchctrl" value="${settings.searchctrl}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_maxspm" /> </i> </u> </b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxspm_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxspm" value="${settings.maxspm}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxsearchresults" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxsearchresults_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxsearchresults" value="${settings.maxsearchresults}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input type="hidden" name="from" value="">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />