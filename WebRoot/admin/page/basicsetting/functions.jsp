<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<script language="javascript" type="text/javascript">
	var extcredits=new Array(8);
	<c:forEach items="${extcredits}" var="extcredit">
		<c:if test="${extcredit.value.available==1}">extcredits[${extcredit.key-1}]='${extcredit.value.title}';</c:if>
		<c:if test="${extcredit.value.available==null||extcredit.value.available!=1}">extcredits[${extcredit.key-1}]='<bean:message key="a_setting_creditsformula_extcredits" />${extcredit.key}';</c:if>
	</c:forEach>
	function isUndefined(variable) {
		return typeof variable == 'undefined' ? true : false;
	}
	function insertunit(formulapermnew,mqualification_id,formulapermexp,text, textend) {
		$(formulapermnew).focus();
		textend = isUndefined(textend) ? '' : textend;
		if(!isUndefined($(formulapermnew).selectionStart)) {
			var opn = $(formulapermnew).selectionStart + 0;
			if(textend != '') {
				text = text + $(formulapermnew).value.substring($(formulapermnew).selectionStart, $(formulapermnew).selectionEnd) + textend;
			}
			$(formulapermnew).value = $(formulapermnew).value.substr(0, $(formulapermnew).selectionStart) + text + $(formulapermnew).value.substr($(formulapermnew).selectionEnd);
		} else if(document.selection && document.selection.createRange) {
			var sel = document.selection.createRange();
			if(textend != '') {
				text = text + sel.text + textend;
			}
			sel.text = text.replace(/\r?\n/g, '\r\n');
			sel.moveStart('character', -strlen(text));
		} else {
			$(formulapermnew).value += text;
		}
		formulaexp(formulapermnew,mqualification_id,formulapermexp);
	}
	
	var formulafind = new Array('digestposts', 'posts', 'oltime', 'pageviews');
	var formulareplace = new Array('<u><bean:message key="digestposts" /></u>','<u><bean:message key="posts" /></u>','<u><bean:message key="a_setting_creditsformula_oltime" /></u>','<u><bean:message key="pageviews" /></u>');
	function formulaexp(formulapermnew,mqualification_id,formulapermexp) {
		var result = $(formulapermnew).value;
		result = result.replace(/extcredits1/g, '<u>'+extcredits[0]+'</u>');
		result = result.replace(/extcredits2/g, '<u>'+extcredits[1]+'</u>');
		result = result.replace(/extcredits3/g, '<u>'+extcredits[2]+'</u>');
		result = result.replace(/extcredits4/g, '<u>'+extcredits[3]+'</u>');
		result = result.replace(/extcredits5/g, '<u>'+extcredits[4]+'</u>');
		result = result.replace(/extcredits6/g, '<u>'+extcredits[5]+'</u>');
		result = result.replace(/extcredits7/g, '<u>'+extcredits[6]+'</u>');
		result = result.replace(/extcredits8/g, '<u>'+extcredits[7]+'</u>');
		result = result.replace(/digestposts/g, '<u><bean:message key="digestposts" /></u>');result = result.replace(/posts/g, '<u><bean:message key="posts" /></u>');result = result.replace(/oltime/g, '<u><bean:message key="a_setting_creditsformula_oltime" /></u>');result = result.replace(/pageviews/g, '<u><bean:message key="pageviews" /></u>');result = result.replace(/and/g, '&nbsp;&nbsp;<bean:message key="and" />&nbsp;&nbsp;');result = result.replace(/or/g, '&nbsp;&nbsp;<bean:message key="or" />&nbsp;&nbsp;');result = result.replace(/>=/g, '&ge;');result = result.replace(/<=/g, '&le;');	$(formulapermexp).innerHTML = result;
		$(mqualification_id).value = $(formulapermnew).value;
	}
	function changeeditqf(editqf_t,flp_id_t,mqualification_id_t){
		<c:forEach items="${medalMapList}" var="medalMap">
		var editqf_${medalMap.medalid } = document.getElementById('editqf_${medalMap.medalid }');
		editqf_${medalMap.medalid }.style.display = 'none';
		</c:forEach>
		var flp_id = document.getElementById(flp_id_t);
		var mqualification_id = document.getElementById(mqualification_id_t);
		editqfvc(flp_id_t,mqualification_id_t);
		var editqf = document.getElementById(editqf_t);
		editqf.style.display = '';
	}
	function editqfvc(flp_id_t,mqualification_id_t){
		var flp_id = document.getElementById(flp_id_t);
		var mqualification_id = document.getElementById(mqualification_id_t);
		flp_id.value = mqualification_id.value;
	}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_functions" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=functions">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
			<tr><td><ul><li><bean:message key="a_setting_tips" /></ul></td></tr>
		</tbody>
	</table>
	<br />
	<a name="b5b886f998a5da83"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_menu" /><a href="###" onclick="collapse_change('b5b886f998a5da83')"><img id="menuimg_b5b886f998a5da83" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_b5b886f998a5da83" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_jsmenu" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_jsmenu_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="forumjump" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="forumjump" value="0" ${settings.forumjump!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_jsmenu_enable" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_jsmenu_enable_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="checkbox" type="checkbox" name="jsmenustatus0" value="1" ${jsmenustatus0}> <bean:message key="a_setting_jsmenu_enable_jump" /><br />
					<input class="checkbox" type="checkbox" name="jsmenustatus1" value="2" ${jsmenustatus1}> <bean:message key="a_setting_jsmenu_enable_memcp" /><br />
					<input class="checkbox" type="checkbox" name="jsmenustatus2" value="4" ${jsmenustatus2}> <bean:message key="a_setting_jsmenu_enable_stat" /><br />
					<input class="checkbox" type="checkbox" name="jsmenustatus3" value="8" ${jsmenustatus3}> <bean:message key="my" /><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_pluginjsmenu" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_pluginjsmenu_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="pluginjsmenu" value="${settings.pluginjsmenu}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="adb4cc177937ac1d"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_editor" /><a href="###" onclick="collapse_change('adb4cc177937ac1d')"><img id="menuimg_adb4cc177937ac1d" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_adb4cc177937ac1d" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_editor_mode_default" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="editoroptions_editer" value="0" ${settings.editoroptions < 2 ? "checked" : ""}> <bean:message key="a_setting_editor_mode_jspruncode" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="editoroptions_editer" value="2" ${settings.editoroptions >=2 ? "checked" : ""}> <bean:message key="a_setting_editor_mode_wysiwyg" /> &nbsp; &nbsp;
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_editor_swtich_enable" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_editor_swtich_enable_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="editoroptions_changer" ${settings.editoroptions%2==1 ? "checked" : ""} value="1"> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="editoroptions_changer" ${settings.editoroptions%2==0 ? "checked" : ""} value="0"> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_bbinsert" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_bbinsert_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="bbinsert" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="bbinsert" value="0" ${settings.bbinsert!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_smileyinsert" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_smileyinsert_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="smileyinsert" value="1" onclick="$('hidden_settings_smileyinsert').style.display = '';" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="smileyinsert" value="0" onclick="$('hidden_settings_smileyinsert').style.display = 'none';" ${settings.smileyinsert!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden_settings_smileyinsert" style="display: ${settings.smileyinsert==0?'none':''}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_smthumb" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_smthumb_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="smthumb" value="${settings.smthumb}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_smcols" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_smcols_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="smcols" value="${settings.smcols}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_smrows" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_smrows_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="smrows" value="${settings.smrows}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="9835580d96a54b2d"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_stat" /><a href="###" onclick="collapse_change('9835580d96a54b2d')"><img id="menuimg_9835580d96a54b2d" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_9835580d96a54b2d" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_statstatus" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_statstatus_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="statstatus" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="statstatus" value="0" ${settings.statstatus!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_statscachelife" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_statscachelife_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="statscachelife" value="${settings.statscachelife}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_pvfrequence" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_pvfrequence_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="pvfrequence" value="${settings.pvfrequence}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_oltimespan" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_oltimespan_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="oltimespan" value="${settings.oltimespan}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="2c0c4ed8a0465d28"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_mod" /><a href="###" onclick="collapse_change('2c0c4ed8a0465d28')"><img id="menuimg_2c0c4ed8a0465d28" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_2c0c4ed8a0465d28" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_modworkstatus" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_modworkstatus_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="modworkstatus" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="modworkstatus" value="0" ${settings.modworkstatus!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_maxmodworksmonths" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxmodworksmonths_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="maxmodworksmonths" value="${settings.maxmodworksmonths}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_myfunction_savetime" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_myfunction_savetime_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="myrecorddays" value="${settings.myrecorddays}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_losslessdel" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_losslessdel_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="losslessdel" value="${settings.losslessdel}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_modreasons" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_modreasons_comment" /></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('modreasons', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('modreasons', 0)">
					<br />
					<textarea rows="6" name="modreasons" id="modreasons" cols="50">${settings.modreasons}</textarea>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_bannedmessages" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_bannedmessages_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="bannedmessages" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="bannedmessages" value="0" ${settings.bannedmessages!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="subtitle_tags"></a><a name="cc871b9c129f1ebf"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_tags" /><a href="###" onclick="collapse_change('cc871b9c129f1ebf')"><img id="menuimg_cc871b9c129f1ebf" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_cc871b9c129f1ebf" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_tagstatus" /></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="tagstatus" value="1" checked onclick="$('hidden_settings_tagstatus').style.display = '';"> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="tagstatus" value="0" ${settings.tagstatus!=1?"checked":""} onclick="$('hidden_settings_tagstatus').style.display = 'none';"> <bean:message key="no" />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden_settings_tagstatus" style="display: ${settings.tagstatus==1?'':'none'}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_index_hottags" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_index_hottags_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="hottags" value="${settings.hottags}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_viewthtrad_hottags" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_viewthtrad_hottags_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="viewthreadtags" value="${settings.viewthreadtags}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="769d4620d4fb5b13"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="5"><bean:message key="other" /><a href="###" onclick="collapse_change('769d4620d4fb5b13')"><img id="menuimg_769d4620d4fb5b13" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_769d4620d4fb5b13" style="display: yes">
			<tr>
				<td width="45%" class="altbg1" colspan="3">
					<b><u><i><bean:message key="a_setting_rssstatus" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_rssstatus_comment" /></span>
				</td>
				<td class="altbg2" colspan="2">
					<input class="radio" type="radio" name="rssstatus" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="rssstatus" value="0" ${settings.rssstatus!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="50%" class="altbg1" colspan="3">
					<b><u><i><bean:message key="a_setting_rssttl" /></i> </u>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_rssttl_comment" /></span>
				</td>
				<td class="altbg2" colspan="2"><input type="text" size="50" name="rssttl" value="${settings.rssttl}" /></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" colspan="3">
					<b><bean:message key="a_setting_csscache" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_csscache_comment" /></span>
				</td>
				<td class="altbg2" colspan="2">
					<input class="radio" type="radio" name="allowcsscache" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowcsscache" value="0" ${settings.allowcsscache!=1?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" colspan="3">
					<b><bean:message key="a_setting_send_birthday" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_send_birthday_comment" /></span>
				</td>
				<td class="altbg2" colspan="2">
					<input class="radio" type="radio" name="bdaystatus" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="bdaystatus" value="0" ${settings.bdaystatus!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" colspan="3">
					<b><bean:message key="a_setting_debug" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_debug_comment" /></span>
				</td>
				<td class="altbg2" colspan="2">
					<input class="radio" type="radio" name="debug" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="debug" value="0" ${settings.debug!=1 ? "checked" : ""}> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top" colspan="3">
					<b><bean:message key="a_setting_activity_type" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_activity_type_comment" /></span>
				</td>
				<td class="altbg2" colspan="2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('activitytype', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('activitytype', 0)">
					<br />
					<textarea rows="6" name="activitytype" id="activitytype" cols="50">${settings.activitytype}</textarea>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" colspan="3">
					<b><bean:message key="a_setting_medals_confer" /></b>
					<br />
					<span class="smalltxt">
					<bean:message key="a_setting_medals_confer_comment1" /><br>
					<bean:message key="a_setting_medals_confer_comment2" />&nbsp;<a href="admincp.jsp?action=crons" target="_blank"><bean:message key="menu_other_cron" /></a>&nbsp;<bean:message key="a_setting_medals_confer_comment3" /><br>
					<bean:message key="a_setting_medals_confer_comment4" />
					</span>
				</td>
				<td class="altbg2" colspan="2">
					<input class="radio" type="radio" name="honorset" onclick="$('hidden_settings_honorset').style.display = '';" value="1" ${settings.honorset==1 ? "checked" : ""}> <bean:message key="a_setting_auto" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="honorset" onclick="$('hidden_settings_honorset').style.display = '';" value="2" ${settings.honorset==2 ? "checked" : ""}> <bean:message key="a_setting_hand" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="honorset" onclick="$('hidden_settings_honorset').style.display = 'none';" value="0" ${settings.honorset == 0 || settings.honorset == null ? "checked" : "" }> <bean:message key="closed" />
				</td>
			</tr>
			<tbody class="sub" id="hidden_settings_honorset" style="display: ${settings.honorset == 0 || settings.honorset == null ? 'none' : '' }">
			<tr>
				<td width="10%" class="altbg1">
					<span class="smalltxt"><bean:message key="a_setting_auto_confer" /></span>
				</td>
				<td width="25%" class="altbg1">
					<span class="smalltxt"><bean:message key="a_setting_medalname" /></span>
				</td>
				<td width="10%" class="altbg1">
					&nbsp;
				</td>
				<td width="35%" class="altbg2">
					<span class="smalltxt"><bean:message key="a_setting_qualification" /></span>
				</td>
				<td width="20%" class="altbg2">
					<span class="smalltxt"><bean:message key="a_setting_confer_reason" /></span>
				</td>
			</tr>
			<c:forEach items="${medalMapList}" var="medalMap">
			<tr>
				<td width="10%" class="altbg1">
					<input class="checkbox" type="checkbox" name="au_medalid" value="${medalMap.medalid }" align="right" ${medalMap.checked == '1' ?'checked':''}  />
				</td>
				<td width="25%" class="altbg1">
					<span style="height: 20">${medalMap.name }</span>
				</td>
				<td width="10%" class="altbg1" align="left">
					<img src="images/common/${medalMap.image }">
				</td>
				<td width="35%" class="altbg2">
					<input type="text" name="mqualification_${medalMap.medalid }" id="mqualification_${medalMap.medalid }" value="${medalMap.qualification }" style="width:85%;" onkeyup="editqfvc('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }');" onmousedown="changeeditqf('editqf_${medalMap.medalid }','flp_${medalMap.medalid }','mqualification_${medalMap.medalid }');formulaexp('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }');"/> 
				</td>
				<td width="20%" class="altbg2">
					<input type="text" name="mreason_${medalMap.medalid }" value="${medalMap.reason }" />
				</td>
			</tr>
			<tr id="editqf_${medalMap.medalid }" style="display:none;">
			<td colspan="5" >
				<div style="width:98%" class="formulaeditor" align="center">
				<div>
					<c:forEach items="${extcredits}" var="extcredit"><c:choose><c:when test="${extcredit.value.available==1}"><a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }','extcredits${extcredit.key}')">${extcredit.value.title}</a> &nbsp;</c:when><c:otherwise><a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }','extcredits${extcredit.key}')"><bean:message key="a_setting_creditsformula_extcredits" />${extcredit.key}</a> &nbsp;</c:otherwise></c:choose></c:forEach>
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' digestposts ')"><bean:message key="digestposts" /></a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' posts ')"><bean:message key="posts" /></a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' oltime ')"><bean:message key="a_setting_creditsformula_oltime" /></a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' pageviews ')"><bean:message key="pageviews" /></a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' + ')">&nbsp;+&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' - ')">&nbsp;-&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' * ')">&nbsp;*&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' / ')">&nbsp;/&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' > ')">&nbsp;>&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' >= ')">&nbsp;>=&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' < ')">&nbsp;<&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' <= ')">&nbsp;<=&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' = ')">&nbsp;=&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' (', ') ')">&nbsp;(&nbsp;)&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' and ')">&nbsp;<bean:message key="and" />&nbsp;</a>&nbsp;
					<a href="###" onclick="insertunit('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }',' or ')">&nbsp;<bean:message key="or" />&nbsp;</a>&nbsp;
					<br />
					<span id="fmlpp_${medalMap.medalid }"></span>
				</div>
				<textarea name="formulaperm" id="flp_${medalMap.medalid }" style="width:100%" rows="2" onkeyup="formulaexp('flp_${medalMap.medalid }','mqualification_${medalMap.medalid }','fmlpp_${medalMap.medalid }');" type="_moz">${forumlaperms}</textarea>
				</div>
			</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<center>
		<input type="hidden" name="from" value="">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />