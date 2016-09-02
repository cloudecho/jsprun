<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onClick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="a_forum_styles_edit"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=styles&edit=${style.styleid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="37c05c6bd6606399"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="a_forum_styles_edit"/> - ${style.name}<a href="###" onclick="collapse_change('37c05c6bd6606399')"><img id="menuimg_37c05c6bd6606399" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_37c05c6bd6606399" style="display: yes">
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_name"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_name_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="name" value="${style.name}" maxlength="20"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_styles_edit_tpl"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_tpl_comment"/></span></td>
				<td class="altbg2">
					<select name="templateid" style="width: 55%"><c:forEach items="${templates}" var="template"><option value="${template.templateid}" ${style.templateid==template.templateid?"selected":""}>${template.name}</option></c:forEach></select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_styles_edit_smileytype"/></b></td>
				<td class="altbg2">
					<select name="stylevar[${stylevaridsMap['STYPEID']}]" style="width: 55%">
						<c:forEach items="${imagetypes}" var="imagetype"><option value="${imagetype.typeid}" ${stylevarsubsMap['STYPEID']==imagetype.typeid?"selected":""}>${imagetype.name}</option></c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_logo"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_logo_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['BOARDIMG']}]" value="${stylevarsubsMap['BOARDIMG']}"></td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_imgdir"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['IMGDIR']}]" value="${stylevarsubsMap['IMGDIR']}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="8491f1f27f29cfa7"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="a_forum_styles_edit_font_color"/><a href="###" onclick="collapse_change('8491f1f27f29cfa7')"><img id="menuimg_8491f1f27f29cfa7" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_8491f1f27f29cfa7" style="display: yes">
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_font"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_font_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['FONT']}]" value="${stylevarsubsMap['FONT']}"></td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_fontsize"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_fontsize_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['FONTSIZE']}]" value="${stylevarsubsMap['FONTSIZE']}"></td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_msgfontsize"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_fontsize_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['MSGFONTSIZE']}]" value="${stylevarsubsMap['MSGFONTSIZE']}"></td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_msgbigsize"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_msgbigsize_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['MSGBIGSIZE']}]" value="${stylevarsubsMap['MSGBIGSIZE']}"></td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_msgsmallsize"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_msgsmallsize_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['MSGSMALLSIZE']}]" value="${stylevarsubsMap['MSGSMALLSIZE']}"></td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_smfont"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_font_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['SMFONT']}]" value="${stylevarsubsMap['SMFONT']}"></td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_smfontsize"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_fontsize_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['SMFONTSIZE']}]" value="${stylevarsubsMap['SMFONTSIZE']}"></td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_link"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
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
							css += 'url("${stylevarsubsMap['IMGDIR']}/' + code[i] + '") ';
						}
					}
				}
				$(obj).style.background = css;
			}
			</script>
				<input id="c1_v" type="text" size="50" value="${stylevarsubsMap['LINK']}" name="stylevar[${stylevaridsMap['LINK']}]" onchange="updatecolorpreview('c1')">
				<input id="c1" onclick="c1_frame.location='images/admincp/getcolor.htm?c1';showMenu('c1')" type="button" value="" style="width: 20px;background: ${subsMap['LINK']}">
				<span id="c1_menu" style="display: none" class="tableborder"><iframe name="c1_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span></td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_highlightlink"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
				<td class="altbg2">
					<input id="c2_v" type="text" size="50" value="${stylevarsubsMap['HIGHLIGHTLINK']}" name="stylevar[${stylevaridsMap['HIGHLIGHTLINK']}]" onchange="updatecolorpreview('c2')">
					<input id="c2" onclick="c2_frame.location='images/admincp/getcolor.htm?c2';showMenu('c2')" type="button" value="" style="width: 20px;background: ${subsMap['HIGHLIGHTLINK']}">
					<span id="c2_menu" style="display: none" class="tableborder"><iframe name="c2_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
				</td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_headertext"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
				<td class="altbg2">
					<input id="c3_v" type="text" size="50" value="${stylevarsubsMap['HEADERTEXT']}" name="stylevar[${stylevaridsMap['HEADERTEXT']}]" onchange="updatecolorpreview('c3')">
					<input id="c3" onclick="c3_frame.location='images/admincp/getcolor.htm?c3';showMenu('c3')" type="button" value="" style="width: 20px;background: ${subsMap['HEADERTEXT']}">
					<span id="c3_menu" style="display: none" class="tableborder"><iframe name="c3_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
				</td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_tabletext"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
				<td class="altbg2">
					<input id="c4_v" type="text" size="50" value="${stylevarsubsMap['TABLETEXT']}" name="stylevar[${stylevaridsMap['TABLETEXT']}]" onchange="updatecolorpreview('c4')">
					<input id="c4" onclick="c4_frame.location='images/admincp/getcolor.htm?c4';showMenu('c4')" type="button" value="" style="width: 20px;background: ${subsMap['TABLETEXT']}">
					<span id="c4_menu" style="display: none" class="tableborder"><iframe name="c4_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
				</td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_text"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c5_v" type="text" size="50" value="${stylevarsubsMap['TEXT']}" name="stylevar[${stylevaridsMap['TEXT']}]" onchange="updatecolorpreview('c5')">
										<input id="c5" onclick="c5_frame.location='images/admincp/getcolor.htm?c5';showMenu('c5')" type="button" value="" style="width: 20px;background: ${subsMap['TEXT']}">
										<span id="c5_menu" style="display: none" class="tableborder"><iframe name="c5_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_lighttext"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c6_v" type="text" size="50" value="${stylevarsubsMap['LIGHTTEXT']}" name="stylevar[${stylevaridsMap['LIGHTTEXT']}]" onchange="updatecolorpreview('c6')">
										<input id="c6" onclick="c6_frame.location='images/admincp/getcolor.htm?c6';showMenu('c6')" type="button" value="" style="width: 20px;background: ${subsMap['LIGHTTEXT']}">
										<span id="c6_menu" style="display: none" class="tableborder"><iframe name="c6_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
						</table>
						<br />
						<a name="d582433e9a735be0"></a>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
							<tr class="header">
								<td colspan="2"><bean:message key="a_forum_styles_edit_table"/><a href="###" onclick="collapse_change('d582433e9a735be0')"><img id="menuimg_d582433e9a735be0" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
							</tr>
							<tbody id="menu_d582433e9a735be0" style="display: yes">
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_maintablewidth"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_maintablewidth_comment"/></span></td>
									<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['MAINTABLEWIDTH']}]" value="${stylevarsubsMap['MAINTABLEWIDTH']}"></td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_tablespace"/></b></td>
									<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['TABLESPACE']}]" value="${stylevarsubsMap['TABLESPACE']}"></td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_tablebg"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c7_v" type="text" size="50" value="${stylevarsubsMap['TABLEBG']}" name="stylevar[${stylevaridsMap['TABLEBG']}]" onchange="updatecolorpreview('c7')">
										<input id="c7" onclick="c7_frame.location='images/admincp/getcolor.htm?c7';showMenu('c7')" type="button" value="" style="width: 20px;background: ${subsMap['TABLEBG']}">
										<span id="c7_menu" style="display: none" class="tableborder"><iframe name="c7_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_borderwidth"/></b></td>
									<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['BORDERWIDTH']}]" value="${stylevarsubsMap['BORDERWIDTH']}"></td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_bordercolor"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c8_v" type="text" size="50" value="${stylevarsubsMap['BORDERCOLOR']}" name="stylevar[${stylevaridsMap['BORDERCOLOR']}]" onchange="updatecolorpreview('c8')">
										<input id="c8" onclick="c8_frame.location='images/admincp/getcolor.htm?c8';showMenu('c8')" type="button" value="" style="width: 20px;background: ${subsMap['BORDERCOLOR']}">
										<span id="c8_menu" style="display: none" class="tableborder"><iframe name="c8_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_bgcolor"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_bgcolor_comment"/></span></td>
									<td class="altbg2">
										<input id="c9_v" type="text" size="50" value="${stylevarsubsMap['BGCOLOR']}" name="stylevar[${stylevaridsMap['BGCOLOR']}]" onchange="updatecolorpreview('c9')">
										<input id="c9" onclick="c9_frame.location='images/admincp/getcolor.htm?c9';showMenu('c9')" type="button" value="" style="width: 20px;background: ${subsMap['BGCOLOR']}">
										<span id="c9_menu" style="display: none" class="tableborder"><iframe name="c9_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_headercolor"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_bgcolor_comment"/></span></td>
									<td class="altbg2">
										<input id="c10_v" type="text" size="50" value="${stylevarsubsMap['HEADERCOLOR']}" name="stylevar[${stylevaridsMap['HEADERCOLOR']}]" onchange="updatecolorpreview('c10')">
										<input id="c10" onclick="c10_frame.location='images/admincp/getcolor.htm?c10';showMenu('c10')" type="button" value="" style="width: 20px;background: ${subsMap['HEADERCOLOR']}">
										<span id="c10_menu" style="display: none" class="tableborder"><iframe name="c10_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_catcolor"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_bgcolor_comment"/></span></td>
									<td class="altbg2">
										<input id="c11_v" type="text" size="50" value="${stylevarsubsMap['CATCOLOR']}" name="stylevar[${stylevaridsMap['CATCOLOR']}]" onchange="updatecolorpreview('c11')">
										<input id="c11" onclick="c11_frame.location='images/admincp/getcolor.htm?c11';showMenu('c11')" type="button" value="" style="width: 20px;background: ${subsMap['CATCOLOR']}">
										<span id="c11_menu" style="display: none" class="tableborder"><iframe name="c11_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_catborder"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c12_v" type="text" size="50" value="${stylevarsubsMap['CATBORDER']}" name="stylevar[${stylevaridsMap['CATBORDER']}]" onchange="updatecolorpreview('c12')">
										<input id="c12" onclick="c12_frame.location='images/admincp/getcolor.htm?c12';showMenu('c12')" type="button" value="" style="width: 20px;background: ${subsMap['CATBORDER']}">
										<span id="c12_menu" style="display: none" class="tableborder"><iframe name="c12_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_portalboxbgcode"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_bgcolor_comment"/></span></td>
									<td class="altbg2">
										<input id="c13_v" type="text" size="50" value="${stylevarsubsMap['PORTALBOXBGCODE']}" name="stylevar[${stylevaridsMap['PORTALBOXBGCODE']}]" onchange="updatecolorpreview('c13')">
										<input id="c13" onclick="c13_frame.location='images/admincp/getcolor.htm?c13';showMenu('c13')" type="button" value="" style="width: 20px;background: ${subsMap['PORTALBOXBGCODE']}">
										<span id="c13_menu" style="display: none" class="tableborder"><iframe name="c13_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_altbg1"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_altbg1_comment"/></span></td>
									<td class="altbg2">
										<input id="c14_v" type="text" size="50" value="${stylevarsubsMap['ALTBG1']}" name="stylevar[${stylevaridsMap['ALTBG1']}]" onchange="updatecolorpreview('c14')">
										<input id="c14" onclick="c14_frame.location='images/admincp/getcolor.htm?c14';showMenu('c14')" type="button" value="" style="width: 20px;background: ${subsMap['ALTBG1']}">
										<span id="c14_menu" style="display: none" class="tableborder"><iframe name="c14_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_altbg2"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_altbg2_comment"/></span></td>
									<td class="altbg2">
										<input id="c15_v" type="text" size="50" value="${stylevarsubsMap['ALTBG2']}" name="stylevar[${stylevaridsMap['ALTBG2']}]" onchange="updatecolorpreview('c15')">
										<input id="c15" onclick="c15_frame.location='images/admincp/getcolor.htm?c15';showMenu('c15')" type="button" value="" style="width: 20px;background: ${subsMap['ALTBG2']}">
										<span id="c15_menu" style="display: none" class="tableborder"><iframe name="c15_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_bgborder"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c16_v" type="text" size="50" value="${stylevarsubsMap['BGBORDER']}" name="stylevar[${stylevaridsMap['BGBORDER']}]" onchange="updatecolorpreview('c16')">
										<input id="c16" onclick="c16_frame.location='images/admincp/getcolor.htm?c16';showMenu('c16')" type="button" value="" style="width: 20px;background: ${subsMap['BGBORDER']}">
										<span id="c16_menu" style="display: none" class="tableborder"><iframe name="c16_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_noticebg"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c17_v" type="text" size="50" value="${stylevarsubsMap['NOTICEBG']}" name="stylevar[${stylevaridsMap['NOTICEBG']}]" onchange="updatecolorpreview('c17')">
										<input id="c17" onclick="c17_frame.location='images/admincp/getcolor.htm?c17';showMenu('c17')" type="button" value="" style="width: 20px;background: ${subsMap['NOTICEBG']}">
										<span id="c17_menu" style="display: none" class="tableborder"><iframe name="c17_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_noticeborder"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c18_v" type="text" size="50" value="${stylevarsubsMap['NOTICEBORDER']}" name="stylevar[${stylevaridsMap['NOTICEBORDER']}]" onchange="updatecolorpreview('c18')">
										<input id="c18" onclick="c18_frame.location='images/admincp/getcolor.htm?c18';showMenu('c18')" type="button" value="" style="width: 20px;background: ${subsMap['NOTICEBORDER']}">
										<span id="c18_menu" style="display: none" class="tableborder"><iframe name="c18_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_noticetext"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c19_v" type="text" size="50" value="${stylevarsubsMap['NOTICETEXT']}" name="stylevar[${stylevaridsMap['NOTICETEXT']}]" onchange="updatecolorpreview('c19')">
										<input id="c19" onclick="c19_frame.location='images/admincp/getcolor.htm?c19';showMenu('c19')" type="button" value="" style="width: 20px;background: ${subsMap['NOTICETEXT']}">
										<span id="c19_menu" style="display: none" class="tableborder"><iframe name="c19_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_commonboxborder"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c20_v" type="text" size="50" value="${stylevarsubsMap['COMMONBOXBORDER']}" name="stylevar[${stylevaridsMap['COMMONBOXBORDER']}]" onchange="updatecolorpreview('c20')">
										<input id="c20" onclick="c20_frame.location='images/admincp/getcolor.htm?c20';showMenu('c20')" type="button" value="" style="width: 20px;background: ${subsMap['COMMONBOXBORDER']}">
										<span id="c20_menu" style="display: none" class="tableborder"><iframe name="c20_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_commonboxbg"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c21_v" type="text" size="50" value="${stylevarsubsMap['COMMONBOXBG']}" name="stylevar[${stylevaridsMap['COMMONBOXBG']}]" onchange="updatecolorpreview('c21')">
										<input id="c21" onclick="c21_frame.location='images/admincp/getcolor.htm?c21';showMenu('c21')" type="button" value="" style="width: 20px;background: ${subsMap['COMMONBOXBG']}">
										<span id="c21_menu" style="display: none" class="tableborder"><iframe name="c21_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_boxspace"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_boxspace_comment"/></span></td>
									<td class="altbg2"><input type="text" size="50" name="stylevar[${stylevaridsMap['BOXSPACE']}]" value="${stylevarsubsMap['BOXSPACE']}"></td>
								</tr>
						</table>
						<br />
						<a name="f601cfce9ca8cfe0"></a>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
							<tr class="header"><td colspan="2"><bean:message key="a_forum_styles_other_table"/><a href="###" onclick="collapse_change('f601cfce9ca8cfe0')"><img id="menuimg_f601cfce9ca8cfe0" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
							<tbody id="menu_f601cfce9ca8cfe0" style="display: yes">
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_inputborder"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
									<td class="altbg2">
										<input id="c22_v" type="text" size="50" value="${stylevarsubsMap['INPUTBORDER']}" name="stylevar[${stylevaridsMap['INPUTBORDER']}]" onchange="updatecolorpreview('c22')">
										<input id="c22" onclick="c22_frame.location='images/admincp/getcolor.htm?c22';showMenu('c22')" type="button" value="" style="width: 20px;background: ${subsMap['INPUTBORDER']}">
										<span id="c22_menu" style="display: none" class="tableborder"><iframe name="c22_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_headermenu"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_bgcolor_comment"/></span></td>
									<td class="altbg2">
										<input id="c23_v" type="text" size="50" value="${stylevarsubsMap['HEADERMENU']}" name="stylevar[${stylevaridsMap['HEADERMENU']}]" onchange="updatecolorpreview('c23')">
										<input id="c23" onclick="c23_frame.location='images/admincp/getcolor.htm?c23';showMenu('c23')" type="button" value="" style="width: 20px;background: ${subsMap['HEADERMENU']}">
										<span id="c23_menu" style="display: none" class="tableborder"><iframe name="c23_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
									</td>
								</tr>
								<tr>
									<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_headermenutext"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
				<td class="altbg2">
					<input id="c24_v" type="text" size="50" value="${stylevarsubsMap['HEADERMENUTEXT']}" name="stylevar[${stylevaridsMap['HEADERMENUTEXT']}]" onchange="updatecolorpreview('c24')">
					<input id="c24" onclick="c24_frame.location='images/admincp/getcolor.htm?c24';showMenu('c24')" type="button" value="" style="width: 20px;background: ${subsMap['HEADERMENUTEXT']}">
					<span id="c24_menu" style="display: none" class="tableborder"><iframe name="c24_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
				</td>
			</tr>
			<tr>
				<td width="55%" class="altbg1" valign="top"><b><bean:message key="a_forum_styles_edit_framebgcolor"/></b><br /><span class="smalltxt"><bean:message key="a_forum_styles_edit_link_comment"/></span></td>
				<td class="altbg2">
					<input id="c25_v" type="text" size="50" value="${stylevarsubsMap['FRAMEBGCOLOR']}" name="stylevar[${stylevaridsMap['FRAMEBGCOLOR']}]" onchange="updatecolorpreview('c25')">
					<input id="c25" onclick="c25_frame.location='images/admincp/getcolor.htm?c25';showMenu('c25')" type="button" value="" style="width: 20px;background: ${subsMap['FRAMEBGCOLOR']}">
					<span id="c25_menu" style="display: none" class="tableborder"><iframe name="c25_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="48"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form)"><bean:message key="del"/></td>
			<td><bean:message key="a_forum_styles_edit_variable"/></td>
			<td><bean:message key="a_forum_styles_edit_subst"/></td>
		</tr>
		${customstylevars}
		<tr align="center" class="altbg1">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" name="newvariable" size="20"></td>
			<td><textarea name="newsubstitute" cols="50" rows="2"></textarea></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />