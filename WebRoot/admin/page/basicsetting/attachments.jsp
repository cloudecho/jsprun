<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_attachments" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=attachments">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="ec360b9b526ef04a"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_attachments" /><a href="###" onclick="collapse_change('ec360b9b526ef04a')"><img id="menuimg_ec360b9b526ef04a" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_ec360b9b526ef04a" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_attachdir" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_attachdir_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="attachdir" value="${settings.attachdir}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_attachurl" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_attachurl_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="attachurl" value="${settings.attachurl}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_attachimgpost" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_attachimgpost_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="attachimgpost" value="1" checked><bean:message key="yes" />  &nbsp; &nbsp;
					<input class="radio" type="radio" name="attachimgpost" value="0" ${settings.attachimgpost!=1?"checked":""}><bean:message key="no" /> 
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_attachrefcheck" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_attachrefcheck_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="attachrefcheck" value="1" checked><bean:message key="yes" />  &nbsp; &nbsp;
					<input class="radio" type="radio" name="attachrefcheck" value="0" ${settings.attachrefcheck!=1?"checked":""}><bean:message key="no" /> 
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_attachsave" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_attachsave_comment" /></span>
				</td>
				<td class="altbg2">
					<html:select property="attachsave" name="attachsave" value="${settings.attachsave}" style="width: 75%">
						<html:option value="0"><bean:message key="a_setting_attachsave_default" /></html:option>
						<html:option value="1"><bean:message key="a_setting_attachsave_forum" /></html:option>
						<html:option value="2"><bean:message key="a_setting_attachsave_type" /></html:option>
						<html:option value="3"><bean:message key="a_setting_attachsave_month" /></html:option>
					</html:select>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="77827cdd83d070ba"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_pictureattachments" /><a href="###" onclick="collapse_change('77827cdd83d070ba')"><img id="menuimg_77827cdd83d070ba" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_77827cdd83d070ba" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_imagelib" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_imagelib_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="imagelib" checked value="0" onclick="$('imagelibext').style.display = 'none';"><bean:message key="default" />  &nbsp; &nbsp;
				</td>
			</tr>
		</tbody>
		<tbody>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_thumbstatus" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_thumbstatus_comment" /><br /><input type="button" onclick="$('settings').action='admincp.jsp?action=imagepreview&previewthumb=yes';$('settings').target='_blank';$('settings').submit();$('settings').action='admincp.jsp?action=settings&do=attachments&edit=yes';$('settings').target='';return false;" value="<bean:message key="a_setting_thumbstatus_preview" />">
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="thumbstatus" value="0" ${settings.thumbstatus==0 ? "checked" : ""}> <bean:message key="a_setting_thumbstatus_none" /><br />
					<input class="radio" type="radio" name="thumbstatus" value="1" ${settings.thumbstatus==1 ? "checked" : ""}> <bean:message key="a_setting_thumbstatus_add" /><br />
					<input class="radio" type="radio" name="thumbstatus" value="2" ${settings.thumbstatus==2 ? "checked" : ""}> <bean:message key="a_setting_thumbstatus_replace" /><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_thumbwidthheight" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_thumbwidthheight_comment" /></span>
				</td>
				<td class="altbg2">
					<input name="thumbwidth" size="20" value="${settings.thumbwidth}">
					<span style="vertical-align: middle">X</span>
					<input name="thumbheight" size="20" value="${settings.thumbheight}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_watermarkstatus" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_watermarkstatus_comment" /><br /><input type="button" onclick="$('settings').action='admincp.jsp?action=imagepreview';$('settings').target='_blank';$('settings').submit();$('settings').action='admincp.jsp?action=settings&do=attachments&edit=yes';$('settings').target='';return false;" value="<bean:message key="a_setting_watermarkstatus_preview" />">
				</td>
				<td class="altbg2">
					<table cellspacing="INNERBORDERWIDTH" cellpadding="1px" class="tableborder" style="margin-bottom: 3px; margin-top: 3px;">
						<tr><td colspan="3"><input class="radio" type="radio" name="watermarkstatus" value="0" checked><bean:message key="a_setting_watermarkstatus_none" /></td></tr>
						<tr align="center" class="altbg2">
							<td><input class="radio" type="radio" name="watermarkstatus" value="1" ${settings.watermarkstatus==1 ? "checked" : ""}> #1</td>
							<td><input class="radio" type="radio" name="watermarkstatus" value="2" ${settings.watermarkstatus==2 ? "checked" : ""}> #2</td>
							<td><input class="radio" type="radio" name="watermarkstatus" value="3" ${settings.watermarkstatus==3 ? "checked" : ""}> #3</td>
						</tr>
						<tr align="center" class="altbg2">
							<td><input class="radio" type="radio" name="watermarkstatus" value="4" ${settings.watermarkstatus==4 ? "checked" : ""}> #4</td>
							<td><input class="radio" type="radio" name="watermarkstatus" value="5" ${settings.watermarkstatus==5 ? "checked" : ""}> #5</td>
							<td><input class="radio" type="radio" name="watermarkstatus" value="6" ${settings.watermarkstatus==6 ? "checked" : ""}> #6</td>
						</tr>
						<tr align="center" class="altbg2">
							<td><input class="radio" type="radio" name="watermarkstatus" value="7" ${settings.watermarkstatus==7 ? "checked" : ""}> #7</td>
							<td><input class="radio" type="radio" name="watermarkstatus" value="8" ${settings.watermarkstatus==8 ? "checked" : ""}> #8</td>
							<td><input class="radio" type="radio" name="watermarkstatus" value="9" ${settings.watermarkstatus==9 ? "checked" : ""}> #9</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_watermarkminwidthheight" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_watermarkminwidthheight_comment" /></span>
				</td>
				<td class="altbg2">
					<input name="watermarkminwidth" size="20" value="${settings.watermarkminwidth}">
					<span style="vertical-align: middle">X</span>
					<input name="watermarkminheight" size="20" value="${settings.watermarkminheight}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_watermarktype" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_watermarktype_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" id="watermarktype" name="watermarktype" value="0" onclick="$('watermarktypeext').style.display = 'none';" ${settings.watermarktype==0 ? "checked" : ""}> <bean:message key="a_setting_watermarktype_gif" /><br />
					<input class="radio" type="radio" id="watermarktype" name="watermarktype" value="1" onclick="$('watermarktypeext').style.display = 'none';" ${settings.watermarktype==1 ? "checked" : ""}> <bean:message key="a_setting_watermarktype_png" /><br />
					<input class="radio" type="radio" id="watermarktype" name="watermarktype" value="2" onclick="$('watermarktypeext').style.display = '';" ${settings.watermarktype==2 ? "checked" : ""}> <bean:message key="a_setting_watermarktype_text" /><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_watermarktrans" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_watermarktrans_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="watermarktrans" value="${settings.watermarktrans}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_watermarkquality" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_watermarkquality_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="watermarkquality" value="${settings.watermarkquality}"></td>
			</tr>
		</tbody>
		<tbody class="sub" id="watermarktypeext"  style="display:${settings.watermarktype==2?'':'none'}">
		<tr>
			<td width="45%" class="altbg1" valign="top">
				<b><bean:message key="a_setting_watermarktext_text" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_watermarktext_text_comment" /></span>
			</td>
			<td class="altbg2">
				<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[watermarktext][text]', 1)">
				<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[watermarktext][text]', 0)"><br />
				<textarea rows="6" name="watermarktext[text]" id="settingsnew[watermarktext][text]" cols="50">${watermarktext.text }</textarea>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_watermarktext_fontpath" /></b><br />
				<span class="smalltxt"><bean:message key="a_setting_watermarktext_fontpath_comment" /></span>
			</td>
			<td class="altbg2"><input type="text" size="50" name="watermarktext[fontpath]" value="${watermarktext.fontpath }"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_watermarktext_size" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_watermarktext_size_comment" /></span>
			</td>
			<td class="altbg2"><input type="text" size="50" name="watermarktext[size]" value="${watermarktext.size }"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_watermarktext_angle" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_watermarktext_angle_comment" /></span>
			</td>
			<td class="altbg2"><input type="text" size="50" name="watermarktext[angle]" value="${watermarktext.angle }"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_watermarktext_color" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_watermarktext_color_comment" /></span>
			</td>
			<td class="altbg2">
				<script>
			function updatecolorpreview(obj) {
				var sp = $(obj + '_v').value.indexOf(' ');
				if(sp == -1) {
					var code = [$(obj + '_v').value];var codel = 1;
				} else {
					var code = [$(obj + '_v').value.substr(0, sp), $(obj + '_v').value.substr(sp + 1)];var codel = 2;
				}
				var css = '';
				for(i = 0;i < codel;i++) {
					if(code[i] != '') {
						if(code[i].substr(0, 1) == '#') {
							css += code[i] + ' ';
						} else {
							css += 'url("/' + code[i] + '") ';
						}
					}
				}
				$(obj).style.background = css;
			}
			</script>
				<input id="c1_v" type="text" size="50" value="${watermarktext.color }" name="watermarktext[color]" onchange="updatecolorpreview('c1')">
				<input id="c1" onclick="c1_frame.location='images/admincp/getcolor.htm?c1';showMenu('c1')" type="button" value="" style="width: 20px; background: ${watermarktext.color }">
				<span id="c1_menu" style="display: none" class="tableborder"><iframe name="c1_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_watermarktext_shadowx" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_watermarktext_shadowx_comment" /></span>
			</td>
			<td class="altbg2"><input type="text" size="50" name="watermarktext[shadowx]" value="${watermarktext.shadowx }"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_watermarktext_shadowy" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_watermarktext_shadowy_comment" /></span>
			</td>
			<td class="altbg2"><input type="text" size="50" name="watermarktext[shadowy]" value="${watermarktext.shadowy }"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_setting_watermarktext_shadowcolor" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_setting_watermarktext_shadowcolor_comment" /></span>
			</td>
			<td class="altbg2">
				<input id="c2_v" type="text" size="50" value="${watermarktext.shadowcolor }" name="watermarktext[shadowcolor]" onchange="updatecolorpreview('c2')">
				<input id="c2" onclick="c2_frame.location='images/admincp/getcolor.htm?c2';showMenu('c2')" type="button" value="" style="width: 20px; background:${watermarktext.shadowcolor } ">
				<span id="c2_menu" style="display: none" class="tableborder"><iframe name="c2_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
			</td>
		</tr>
		</tbody>
	</table>
	<br />
	<a name="7cdd72b545c705f1"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
<tr class="header">
<td colspan="2"><bean:message key="a_setting_remote" /><a href="###" onclick="collapse_change('7cdd72b545c705f1')"><img id="menuimg_7cdd72b545c705f1" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
</td>
</tr>
<tbody id="menu_7cdd72b545c705f1" style="display: ">
<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_enabled" /></b></td><td class="altbg2"><input class="radio" type="radio" name="ftp[on]" value="1" ${ftp.on==1?'checked':''} onclick="$('ftpext').style.display = '';$('ftpcheckbutton').style.display = '';"><bean:message key="yes" />  &nbsp; &nbsp; <input class="radio" type="radio" name="ftp[on]" value="0" ${ftp.on==0?'checked':''} onclick="$('ftpext').style.display = 'none';$('ftpcheckbutton').style.display = 'none';"><bean:message key="no" />  &nbsp; &nbsp; </td></tr></tbody><tbody class="sub" id="ftpext" style="display:${ftp.on==1?'':'none'} "><tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_enabled_ssl" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_enabled_ssl_comment" /></span></td><td class="altbg2"><input class="radio" type="radio" name="ftp[ssl]" value="1" ${ftp.ssl==1?'checked':''}> <bean:message key="yes" /> &nbsp; &nbsp; 
<input class="radio" type="radio" name="ftp[ssl]" value="0" ${ftp.ssl==0 ? 'checked' : ''}  > <bean:message key="no" /> 
</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_ftp_host" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_ftp_host_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ftp[host]" value="${ftp.host}" >
</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_ftp_port" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_ftp_port_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ftp[port]" value="${ftp.port}" >
</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_ftp_user" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_ftp_user_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ftp[username]" value="${ftp.username}" >
</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_ftp_pass" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_ftp_pass_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ftp[password]" value="${ftp.password}" >
</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_ftp_pasv" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_ftp_pasv_comment" /></span></td><td class="altbg2"><input class="radio" type="radio" name="ftp[pasv]" value="1"   ${ftp.pasv==1 ? 'checked' : ''}> <bean:message key="yes" /> &nbsp; &nbsp; 
<input class="radio" type="radio" name="ftp[pasv]" value="0" ${ftp.pasv==0 || ftp.pasv==null ? 'checked' : ''}  ><bean:message key="no" /> 
</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_dir" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_dir_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ftp[attachdir]" value="${ftp.attachdir}" >
</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_url" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_url_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ftp[attachurl]" value="${ftp.attachurl}" >
</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_hide_dir" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_hide_dir_comment" /></span></td><td class="altbg2"><input class="radio" type="radio" name="ftp[hideurl]" value="1"  ${ftp.hideurl==1 ? 'checked' : ''}> <bean:message key="yes" /> &nbsp; &nbsp; 
<input class="radio" type="radio" name="ftp[hideurl]" value="0" ${ftp.hideurl == 0 ? 'checked' : ''}  ><bean:message key="no" /> 
</td></tr>
<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_ftp_install_plugin" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_ftp_note" /></span></td><td class="altbg2"><input class="radio" type="radio" name="ftp[isinstall]" value="1"  ${ftp.isinstall==1 ? 'checked' : ''} onclick="$('activeurl').style.display = '';"> <bean:message key="yes" /> &nbsp; &nbsp; 
<input class="radio" type="radio" name="ftp[isinstall]" value="0" ${ftp.isinstall == 0 || ftp.isinstall==null ? 'checked' : ''}  onclick="$('activeurl').style.display = 'none';"> <bean:message key="no" />
</td></tr><tr style="display:${ftp.isinstall==1 ? '' : 'none'}" id="activeurl"><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_ftp_domainname" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_ftp_domainname_explain" /></span></td><td class="altbg2"><input type="text" size="50" name="ftp[activeurl]" value="${ftp.activeurl}" >
</td></tr>
<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_remote_timeout" /></b><br /><span class="smalltxt"><bean:message key="a_setting_remote_timeout_comment" /></span></td><td class="altbg2"><input type="text" size="50" name="ftp[timeout]" value="${ftp.timeout}" >
</td></tr>
<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_att_mirror_set" /></b><br /><span class="smalltxt"><bean:message key="a_setting_att_mirror_set_explain" /></span></td><td><input class="radio" type="radio" name="ftp[mirror]" value="1" ${ftp.mirror==1 || ftp.mirror==null ? 'checked' : ''}><bean:message key="a_setting_att_mirror_set_content1" /> &nbsp;&nbsp;<br><input class="radio" type="radio" name="ftp[mirror]" value="2" ${ftp.mirror == 2 ? 'checked' : ''}><bean:message key="a_setting_att_mirror_set_content2" /> </td></tr>
<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_allow_attname" /></b><br /><span class="smalltxt"><bean:message key="a_setting_allow_attname_explain" /></span></td><td><textarea  rows="3" onkeyup="textareasize(this)" name="ftp[allowedexts]" id="ftp[allowedexts]" cols="46" class="tarea" '..'>${ftp.allowedexts}</textarea></td></tr>
<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_forbid_attname" /></b><br /><span class="smalltxt"><bean:message key="a_setting_forbid_attname_explain" /></span></td><td><textarea  rows="3" onkeyup="textareasize(this)" name="ftp[disallowedexts]" id="ftp[disallowedexts]" cols="46" class="tarea" '..'>${ftp.disallowedexts}</textarea></td></tr>
<tr><td width="45%" class="altbg1" ><b><bean:message key="a_setting_att_minsize" /></b><br /><span class="smalltxt"><bean:message key="a_setting_att_minsize_explain" /></span></td><td><input name="ftp[minsize]" value="${ftp.minsize}" type="text" size="50"/></td></tr>
</tbody></table><br /><center><input type="hidden" name="from" value="${from}"><input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />"><span id="ftpcheckbutton" style="display:${ftp.on==1?'':'none'}">&nbsp;&nbsp;&nbsp;<input class="button" type="submit" name="ftpcheck" value="<bean:message key="a_setting_remote_ftpcheck" />" onclick="this.form.action='admincp.jsp?action=ftpcheck';this.form.target='ftpcheckiframe';"></span><iframe name="ftpcheckiframe" style="display: none"></iframe></center></form>
<jsp:directive.include file="../cp_footer.jsp" />