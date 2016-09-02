<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_styles" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=styles">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
			<tr><td><ul><li> <bean:message key="a_setting_tips" /></ul></td></tr>
		</tbody>
	</table>
	<br />
	<a name="6e955c8b78ec4734"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_style" /><a href="###" onclick="collapse_change('6e955c8b78ec4734')"><img id="menuimg_6e955c8b78ec4734" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_6e955c8b78ec4734" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_styleid" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_styleid_comment" /></span>
				</td>
				<td class="altbg2">
					<select name="styleid">
					<c:forEach items="${styleTemplages}" var="style"><option value="${style.styleid}" ${settings.styleid==style.styleid?"selected":""}>${style.name}</option></c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_stylejump" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_stylejump_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="stylejump" value="1" ${settings.stylejump==1 ? "checked" : ""}> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="stylejump" value="0" ${settings.stylejump==0 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_frameon" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_frameon_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="frameon" value="0" onclick="$('frameonext').style.display = 'none';" ${settings.frameon==0?"checked":""}> <bean:message key="closed" /><br />
					<input class="radio" type="radio" name="frameon" value="1" onclick="$('frameonext').style.display = '';" ${settings.frameon==1?"checked":""}> <bean:message key="a_setting_frameon_1" /><br />
					<input class="radio" type="radio" name="frameon" value="2" onclick="$('frameonext').style.display = '';" ${settings.frameon==2?"checked":""}> <bean:message key="a_setting_frameon_2" /><br />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="frameonext" style="display: ${settings.frameon==0?'none':''}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_framewidth" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_framewidth_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="framewidth" value="${settings.framewidth}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="3c75d2b6433667af"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_index" /><a href="###" onclick="collapse_change('3c75d2b6433667af')"><img id="menuimg_3c75d2b6433667af" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_3c75d2b6433667af" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_subforumsindex" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_subforumsindex_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="subforumsindex" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="subforumsindex" value="0" ${settings.subforumsindex!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_showlink_onhome" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_showlink_onhome_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="forumlinkstatus" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="forumlinkstatus" value="0" ${settings.forumlinkstatus!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><i><u><bean:message key="a_setting_index_members" /></u></i></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_index_members_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxbdays" value="${settings.maxbdays}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_moddisplay" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_moddisplay_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="moddisplay" value="flat" ${settings.moddisplay=='flat'?"checked":""}> <bean:message key="a_setting_moddisplay_flat" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="moddisplay" value="selectbox" ${settings.moddisplay=='selectbox'?"checked":""}> <bean:message key="a_setting_moddisplay_selectbox" /> &nbsp; &nbsp;
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_whosonline" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_whosonline_comment" /></span>
				</td>
				<td class="altbg2">
					<select name="whosonlinestatus" style="width: 58%">
						<option value="0" ${settings.whosonlinestatus==0?"selected":""}><bean:message key="a_setting_display_none" /></option>
						<option value="1" ${settings.whosonlinestatus==1?"selected":""}><bean:message key="a_setting_whosonline_index" /></option>
						<option value="2" ${settings.whosonlinestatus==2?"selected":""}><bean:message key="a_setting_whosonline_forum" /></option>
						<option value="3" ${settings.whosonlinestatus==3?"selected":""}><bean:message key="a_setting_whosonline_both" /></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_whosonline_contract" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_whosonline_contract_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="whosonline_contract" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="whosonline_contract" value="0" ${settings.whosonline_contract!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_online_more_members" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_online_more_members_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxonlinelist" value="${settings.maxonlinelist}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="54ba19dcee0e203a"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_forumdisplay" /><a href="###" onclick="collapse_change('54ba19dcee0e203a')"><img id="menuimg_54ba19dcee0e203a" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_54ba19dcee0e203a" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="page_tpp" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_tpp_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="topicperpage" value="${settings.topicperpage}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_threadmaxpages" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_threadmaxpages_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="threadmaxpages" value="${settings.threadmaxpages}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_hottopic" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_hottopic_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="hottopic" value="${settings.hottopic}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_fastpost" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_fastpost_comment2" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="fastpost" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="fastpost" value="0" ${settings.fastpost!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_reply_quickly" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_reply_quickly_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="fastreply" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="fastreply" value="0" ${settings.fastreply!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_globalstick" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_globalstick_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="globalstick" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="globalstick" value="0" ${settings.globalstick!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_stick" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_stick_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="threadsticky" value="${settings.threadsticky}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="37312f164c6f8745"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_viewthread" /><a href="###" onclick="collapse_change('37312f164c6f8745')"><img id="menuimg_37312f164c6f8745" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_37312f164c6f8745" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_ppp" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_ppp_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="postperpage" value="${settings.postperpage}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_starthreshold" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_starthreshold_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="starthreshold" value="${settings.starthreshold}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxsigrows" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxsigrows_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxsigrows" value="${settings.maxsigrows}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_rate_number" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_rate_number_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="ratelogrecord" value="${settings.ratelogrecord}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_show_signature" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="showsignatures" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="showsignatures" value="0" ${showsignatures?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_show_face" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="showavatars" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="showavatars" value="0" ${showavatars?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_show_images" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="showimages" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="showimages" value="0" ${showimages ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="is_parsecode" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="showjavacode" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="showjavacode" value="0" ${settings.showjavacode!=1?"checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_zoomstatus" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_zoomstatus_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="zoomstatus" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="zoomstatus" value="0" ${settings.zoomstatus!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_vtonlinestatus" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_vtonlinestatus_comment" /></span>
				</td>
				<td class="altbg2">
					<select name="vtonlinestatus" style="width: 55%">
						<option value="0" ${settings.vtonlinestatus==0?"selected":""}><bean:message key="a_setting_display_none" /></option>
						<option value="1" ${settings.vtonlinestatus==1?"selected":""}><bean:message key="a_setting_online_easy" /></option>
						<option value="2" ${settings.vtonlinestatus==2?"selected":""}><bean:message key="a_setting_online_exactitude" /></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_userstatusby" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_userstatusby_comment" /></span>
				</td>
				<td class="altbg2">
					<select name="userstatusby" style="width: 55%">
						<option value="0" ${settings.userstatusby==0?"selected":""}><bean:message key="a_setting_display_none" /></option>
						<option value="1" ${settings.userstatusby==1?"selected":""}><bean:message key="a_setting_userstatusby_usergroup" /></option>
						<option value="2" ${settings.userstatusby==2?"selected":""}><bean:message key="a_setting_userstatusby_rank" /></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_postno" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_postno_comment" /> <sup> # </sup></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="postno" value="${settings.postno}"></td>
			</tr>

			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_postnocustom" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_postnocustom_comment" /></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[postnocustom]', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[postnocustom]', 0)">
					<br />
					<textarea rows="6" name="postnocustom" id="settingsnew[postnocustom]" cols="50">${settings.postnocustom}</textarea>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxsmilies" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxsmilies_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxsmilies" value="${settings.maxsmilies}"></td>
			</tr>
		</tbody>
		<tbody>
			<tr>
				<td colspan="2" class="altbg1">
					<a name="customauthorinfo"></a><b><bean:message key="a_setting_customauthorinfo" /></b>
					<br />
					<bean:message key="a_setting_customauthorinfo_comment" />
				</td>
			</tr>
			<tr>
				<td colspan="2" style="padding: 0">
					<table width="100%" cellspacing="0">
						<tr class="category">
							<td width="25%">&nbsp;<input type="hidden" name="item" value="${items}"></td>
							<td width="25%"><bean:message key="a_setting_ordinary_thread" /></td>
							<td width="25%"><bean:message key="usergroups_specialthread" /></td>
							<td width="25%"><bean:message key="a_setting_username_menu" /></td>
						</tr>
						<c:forTokens items="${items}" delims="," var="item">
							<tr>
								<td>${authorinfoitems[item]}</td>
								<td><input name="customauthorinfo[${item}][left]" type="checkbox" class="checkbox" value="1" ${customauthorinfo[item]['left']>0 ? "checked" : ""}></td>
								<td><input name="customauthorinfo[${item}][special]" type="checkbox" class="checkbox" value="1" ${customauthorinfo[item]['special']>0 ? "checked" : ""}></td>
								<td><input name="customauthorinfo[${item}][menu]" type="checkbox" class="checkbox" value="1" ${customauthorinfo[item]['menu']>0 ? "checked" : ""}></td>
							</tr>
						</c:forTokens>
					</table>
				</td>
			</tr>
	</table>
	<br />
	<a name="99d66db7d672a4b2"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_member_list" /><a href="###" onclick="collapse_change('99d66db7d672a4b2')"><img id="menuimg_99d66db7d672a4b2" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_99d66db7d672a4b2" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_mpp" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_mpp_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="memberperpage" value="${settings.memberperpage}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_membermaxpages" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_membermaxpages_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="membermaxpages" value="${settings.membermaxpages}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="bb62ab8e9e812563"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_refresh" /><a href="###" onclick="collapse_change('bb62ab8e9e812563')"><img id="menuimg_bb62ab8e9e812563" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_bb62ab8e9e812563" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_refresh_refreshtime" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_refresh_refreshtime_comment" /></span>
				</td>

				<td class="altbg2"><input type="text" size="50" name="msgforward[refreshtime]" value="${msgforward.refreshtime}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_refresh_quick" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_refresh_quick_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="msgforward[quick]" value="1" onclick="$('hidden_settings_refresh_quick').style.display = '';" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="msgforward[quick]" value="0" onclick="$('hidden_settings_refresh_quick').style.display = 'none';" ${msgforward.quick!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden_settings_refresh_quick" style="display: ${msgforward.quick==0?'none':''}">
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_refresh_messages" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_refresh_messages_comment1" /><a href="faq.jsp?action=message&id=34" target="_bank"><bean:message key="a_setting_refresh_messages_comment2" /></a></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('msgforward[messages]', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('msgforward[messages]', 0)">
					<br />
					<textarea rows="6" name="msgforward[messages]" id="msgforward[messages]" cols="50" />${msgforward_message}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="769d4620d4fb5b13"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="other" /><a href="###" onclick="collapse_change('769d4620d4fb5b13')"><img id="menuimg_769d4620d4fb5b13" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_769d4620d4fb5b13" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_hideprivate" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_hideprivate_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="hideprivate" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;								
					<input class="radio" type="radio" name="hideprivate" value="0" ${settings.hideprivate!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_visitedforums" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_visitedforums_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="visitedforums" value="${settings.visitedforums}"></td>
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