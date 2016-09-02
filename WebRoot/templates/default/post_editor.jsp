<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<style>
.editor_tb{margin:5px 0 0;width:600px;height:26px;border:1px solid;border-color:#999 #CCC #CCC #999;background:#F7F7F7;border-bottom:none;}.editor_tb .right a{color:#09C;}.editor_tb .right{padding-right:10px;line-height:26px;}
.float_typeid{float:left;margin-right:6px;}.float_typeid select{float:left;height:20px;}.float_typeid a{display:block;overflow:hidden;text-indent:4px;padding-right:17px;width:77px;height:20px;*padding-top:2px;*height:18px;text-decoration:none !important;color:#444 !important;border:1px solid;border-color:#999 #CCC #CCC #999;background:#FFF url(../../${styles.IMGDIR}/newarow.gif) no-repeat 100% 0;}.float_typeid a:hover{text-decoration:none;border:1px solid #09C;background-position:100% -20px;}.float_postinfo .float_typeid a{line-height:20px;*line-height:18px;}.float_typeid ul{margin:-22px 0 0;border:1px solid #09C;background:#FFF url(../../${styles.IMGDIR}/newarow.gif) no-repeat 100% -20px;}* html .float_typeid ul{margin-top:-23px;}.float_typeid ul li{margin:0 4px;color:#444;cursor:pointer;}.float_typeid ul li:hover{color:#09C;}.newselect .current{font-weight:700;}.float_typeid select{width:94px;}
.smilieslist{text-align:center;padding:10px;border:1px solid #7FCAE2;background:#FEFEFE;}.smilieslist td{padding:8px;border:none;cursor:pointer;}.smilieslist_page{text-align:right;}
.smilieslist_table{left:-100px;top:0;*top:-1px;width:100px !important;height:100px;border:1px solid #E6E7E1;background:#FFF;}.smilieslist_preview{text-align:center;vertical-align:middle;}.smiliesgroup ul{margin:6px 0;padding:0 0 24px 8px;border-bottom:1px solid #DDD;}.smiliesgroup li{display:inline;}.smiliesgroup li a{float:left;margin-right:6px;padding:2px 10px;height:24px;he\ight:19px;border:1px solid #DDD;color:#09C;}.smiliesgroup li a.current{border-bottom-color:#FFF;background:#FFF;font-weight:bold;color:#444;}
.pags_act{float:left;}.pags_act a{display:inline !important;}.smilieslist_page a{display:inline;margin:0 4px;color:#09C;text-decoration:underline;}.right{float:right;}
</style>
<script type="text/javascript">
lang['post_jspruncode_code'] = '<bean:message key="post_jspruncode_input_code"/>';
lang['post_jspruncode_quote'] = '<bean:message key="post_jspruncode_input_quote"/>';
lang['post_jspruncode_free'] = '<bean:message key="post_jspruncode_input_free"/>';
lang['post_jspruncode_hide'] = '<bean:message key="post_jspruncode_input_hide"/>';
var editorcss = 'forumdata/cache/style_${styles.STYLEID}.css';
var editorcss_append = 'forumdata/cache/style_${styles.STYLEID}_append.css';
var TABLEBG = '${styles.TABLEBG}';
</script>
<th valign="top">
<label for="posteditor_textarea"><c:choose><c:when test="${special==1}"><bean:message key="poll_message"/></c:when><c:when test="${thread.special==2&&isfirstpost}"><bean:message key="post_trade_counterdesc"/></c:when><c:when test="${thread.special==2&&special == 2}"><bean:message key="post_trade_description"/></c:when><c:when test="${special==3&&allowpostreward}"><bean:message key="reward_message"/></c:when><c:when test="${special==4&&allowpostactivity}"><bean:message key="activity_description"/></c:when><c:when test="${special==5}"><c:choose><c:when test="${allowpostdebate&&isfirstpost||action == 'newthread'}"><bean:message key="poll_message"/></c:when><c:otherwise><bean:message key="debate_position"/></c:otherwise></c:choose></c:when><c:otherwise><bean:message key="message"/></c:otherwise></c:choose></label>
<div id="${editorid}_left" style='${advanceeditor?"":"display: none"}'>
<ul style='${advanceeditor?"":"display: none"}'>
	<li><bean:message key="post_html"/> <em><c:choose><c:when test="${forum.allowhtml>0||usergroups.allowhtml>0}"><bean:message key="available"/></c:when><c:otherwise><bean:message key="disable"/></c:otherwise></c:choose></em></li>
	<li><a href="faq.jsp?action=message&id=${faqs.smilies.id}" target="_blank">${faqs.smilies.keyword}</a> <em><c:choose><c:when test="${forum.allowsmilies>0}"><bean:message key="available"/></c:when><c:otherwise><bean:message key="disable"/></c:otherwise></c:choose></em></li>
	<li><a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank">${faqs.JspRuncode.keyword}</a> <em><c:choose><c:when test="${forum.allowbbcode>0}"><bean:message key="available"/></c:when><c:otherwise><bean:message key="disable"/></c:otherwise></c:choose></em></li>
	<li><bean:message key="post_imgcode"/> <em><c:choose><c:when test="${forum.allowimgcode>0}"><bean:message key="available"/></c:when><c:otherwise><bean:message key="disable"/></c:otherwise></c:choose></em></li>
</ul>
<hr>
<ul>
	<li><label><input type="checkbox" name="parseurloff" id="parseurloff" value="1" ${post.parseurloff>0?"checked=checked":""}/> <bean:message key="disable_url"/></label></li>
	<li><label><input type="checkbox" name="smileyoff" id="smileyoff" value="1" ${post.smileyoff>0?"checked=checked":""}/> <bean:message key="disable"/> <a href="faq.jsp?action=message&id=${faqs.smilies.id}" target="_blank">${faqs.smilies.keyword}</a></label></li>
	<li><label><input type="checkbox" name="bbcodeoff" id="bbcodeoff" value="1" ${post.bbcodeoff>0?"checked=checked":""}/> <bean:message key="disable"/> <a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank">${faqs.JspRuncode.keyword}</a></label></li>
	<c:if test="${settings.tagstatus>0&&(action=='newthread'||action=='edit'&&isfirstpost)}"><li><label><input type="checkbox" name="tagoff" id="tagoff" value="1" ${tagoffcheck} /> <bean:message key="disable"/> <bean:message key="tag_parse"/></label></li></c:if>
	<c:if test="${usergroups.allowhtml>0}"><li><label><input type="checkbox" name="htmlon" id="htmlon" value="1" ${post.htmlon>0?"checked=checked":""}/> <bean:message key="enabled"/> <bean:message key="post_html"/></label></li></c:if>
	<c:choose>
		<c:when test="${action!='edit'}"><c:if test="${allowanonymous}"><li><label><input type="checkbox" name="isanonymous" value="1" /> <bean:message key="post_anonymous"/></label></li></c:if></c:when>
		<c:otherwise><c:if test="${allowanonymous||post.anonymous>0}"><li><label><input type="checkbox" name="isanonymous" value="1" ${post.anonymous>0?"checked=checked":""}/> <bean:message key="post_anonymous"/></label></li></c:if></c:otherwise>
	</c:choose>
	<li><label><input type="checkbox" name="usesig" value="1" ${post.usesig>0||user.sigstatus>0?"checked=checked":""?"checked=checked":""}> <bean:message key="post_show_sig"/></label></li>
	<c:choose><c:when test="${action!='edit'}">
		<li><label><input type="checkbox" name="emailnotify" value="1" ${notifycheck}> <bean:message key="post_email_notify"/></label></li>
		<c:if test="${action=='newthread'}">
			<c:if test="${ismoderator&&(usergroups.allowdirectpost>0||forum.modnewposts<=0)}"><li><label><input type="checkbox" name="sticktopic" value="1" ${stickcheck}> <bean:message key="admin_stick"/></label></li><li><label><input type="checkbox" name="addtodigest" value="1" ${digestcheck}> <bean:message key="post_digest_thread"/></label></li></c:if>
			<c:if test="${usergroups.allowuseblog>0&&forum.allowshare>0}"><li><label><input type="checkbox" name="addtoblog" value="1" ${blogcheck}> <bean:message key="ABL"/></label></li></c:if>
		</c:if>
	</c:when><c:otherwise>
		<c:choose>
			<c:when test="${(isorigauthor||ismoderator)&&isfirstpost&&thread.replies<1}"><li><label><input type="checkbox" name="delete" value="1"> <b><bean:message key="post_delpost"/></b> <c:if test="${thread.special==3}"><bean:message key="reward_price_back"/></c:if></label></li></c:when>
			<c:when test="${!isfirstpost&&(isorigauthor||ismoderator)}"><li><label><input type="checkbox" name="delete" value="1"> <b><bean:message key="post_delpost"/></b></label></li></c:when>
		</c:choose>
		<c:if test="${auditstatuson>0}"><li><label><input type="checkbox" name="audit" value="1"> <b><bean:message key="auditstatuson"/></b></label></li></c:if>
	</c:otherwise></c:choose>
</ul>
</div>
</th>
<td valign="top"><div id="${editorid}"><c:if test="${settings.bbinsert>0}"><table summary="editor" id="editor" cellpadding="0" cellspacing="0">
<tr><td id="${editorid}_controls" class="editortoolbar"><table summary="Editor ToolBar" cellpadding="0" cellspacing="0"><tr>
	<td><a id="${editorid}_cmd_bold"><img src="images/common/bb_bold.gif" title="<bean:message key="admin_highlight_bold"/>" alt="B" /></a></td>
	<td><a id="${editorid}_cmd_italic"><img src="images/common/bb_italic.gif" title="<bean:message key="admin_highlight_italic"/>" alt="I" /></a></td>
	<td><a id="${editorid}_cmd_underline"><img src="images/common/bb_underline.gif" title="<bean:message key="admin_highlight_underline"/>" alt="U" /></a></td>
	<td><img src="images/common/bb_separator.gif" alt="|" /></td>
	<td><a id="${editorid}_popup_fontname" title="<bean:message key="post_jspruncode_fontname"/>"><span style="width: 110px; display: block; white-space: nowrap;" id="${editorid}_font_out" class="dropmenu"><bean:message key="post_jspruncode_fontname"/></span></a></td>
	<td><a id="${editorid}_popup_fontsize" title="<bean:message key="post_jspruncode_fontsize"/>"><span style="width: 30px; display: block;" id="${editorid}_size_out" class="dropmenu"><bean:message key="post_jspruncode_fontsize"/></span></a></td>
	<td><a id="${editorid}_popup_forecolor" title="<bean:message key="post_jspruncode_forecolor"/>"><span style="width: 30px; display: block;" class="dropmenu"><img src="images/common/bb_color.gif" width="21" height="16" alt="" /><br /><img src="images/common/bb_clear.gif" id="${editorid}_color_bar" alt="" style="background-color:black" width="21" height="4" /></span></a></td>
	<td><img src="images/common/bb_separator.gif" alt="|" /></td>
	<td><a id="${editorid}_cmd_justifyleft"><img src="images/common/bb_left.gif" title="<bean:message key="post_jspruncode_left"/>" alt="Align Left" /></a></td>
	<td><a id="${editorid}_cmd_justifycenter"><img src="images/common/bb_center.gif" title="<bean:message key="post_jspruncode_center"/>" alt="Align Center" /></a></td>
	<td><a id="${editorid}_cmd_justifyright"><img src="images/common/bb_right.gif" title="<bean:message key="post_jspruncode_right"/>" alt="Align Right" /></a></td>
	<td><img src="images/common/bb_separator.gif" alt="|" /></td>
	<td><a id="${editorid}_cmd_createlink"><img src="images/common/bb_url.gif" title="<bean:message key="post_jspruncode_url"/>" alt="Url" /></a></td>
	<td><a id="${editorid}_cmd_email"><img src="images/common/bb_email.gif" title="<bean:message key="post_jspruncode_email"/>" alt="Email" /></a></td>
	<td><a id="${editorid}_cmd_insertimage"><img src="images/common/bb_image.gif" title="<bean:message key="post_jspruncode_image"/>" alt="Image" /></a></td>
	<c:if test="${forum.allowmediacode>0}"><td><a id="${editorid}_popup_media"><img src="images/common/bb_media.gif" title="<bean:message key="post_jspruncode_media"/>" alt="Media" /></a></td></c:if>
	<td><img src="images/common/bb_separator.gif" alt="|" /></td>
	<td><a id="${editorid}_cmd_quote"><img src="images/common/bb_quote.gif" title="<bean:message key="post_jspruncode_quote"/>" alt="Quote" /></a></td>
	<c:if test="${settings.showjavacode==0||special>0}">
	<td><a id="${editorid}_cmd_code"><img src="images/common/bb_code.gif" title="<bean:message key="post_jspruncode_code"/>" alt="Code" /></a></td>
	</c:if>
	<c:if test="${settings.smileyinsert>0}"><td><a href="javascript:;" id="${editorid}smilies"  onmouseover="showMenu(this.id, true,0, 2)"><img src="images/common/smile.gif"></a></td></c:if>
</tr></table>
<table summary="Editor ToolBar" cellpadding="0" cellspacing="0" id="${editorid}_morebuttons0" style='${advanceeditor?"":"display: none"}'><tr>
	<td><a id="${editorid}_cmd_removeformat"><img src="images/common/bb_removeformat.gif" title="<bean:message key="post_jspruncode_removeformat"/>" alt="Rremove Format" /></a></td>
	<td><a id="${editorid}_cmd_unlink"><img src="images/common/bb_unlink.gif" title="<bean:message key="post_jspruncode_unlink"/>" alt="Unlink" /></a></td>
	<td><a id="${editorid}_cmd_undo"><img src="images/common/bb_undo.gif" title="<bean:message key="post_jspruncode_undo"/>" alt="Undo" /></a></td>
	<td><a id="${editorid}_cmd_redo"><img src="images/common/bb_redo.gif" title="<bean:message key="post_jspruncode_redo"/>" alt="Redo" /></a></td>
	<td><img src="images/common/bb_separator.gif" alt="|" /></td>
	<td><a id="${editorid}_cmd_insertorderedlist"><img src="images/common/bb_orderedlist.gif" title="<bean:message key="post_jspruncode_orderedlist"/>" alt="Ordered List" /></a></td>
	<td><a id="${editorid}_cmd_insertunorderedlist"><img src="images/common/bb_unorderedlist.gif" title="<bean:message key="post_jspruncode_unorderedlist"/>" alt="Unordered List" /></a></td>
	<td><a id="${editorid}_cmd_outdent"><img src="images/common/bb_outdent.gif" title="<bean:message key="post_jspruncode_outdent"/>" alt="Outdent" /></a></td>
	<td><a id="${editorid}_cmd_indent"><img src="images/common/bb_indent.gif" title="<bean:message key="post_jspruncode_indent"/>" alt="Indent" /></a></td>
	<td><a id="${editorid}_cmd_floatleft"><img src="images/common/bb_floatleft.gif" title="<bean:message key="post_jspruncode_floatleft"/>" alt="Float Left" /></a></td>
	<td><a id="${editorid}_cmd_floatright"><img src="images/common/bb_floatright.gif" title="<bean:message key="post_jspruncode_floatright"/>" alt="Float Right" /></a></td>
	<td><img src="images/common/bb_separator.gif" alt="|" /></td>
	<td><a id="${editorid}_cmd_table"><img src="images/common/bb_table.gif" title="<bean:message key="post_jspruncode_table"/>" alt="Table" /></a></td>
	<td><a id="${editorid}_cmd_free"><img src="images/common/bb_free.gif" title="<bean:message key="post_jspruncode_free"/>" alt="Free" /></a></td>
	<c:if test="${usergroups.allowhidecode>0}"><td><a id="${editorid}_cmd_hide"><img src="images/common/bb_hide.gif" title="<bean:message key="post_jspruncode_hide"/>" alt="Hide" /></a></td></c:if>
	<td><img src="images/common/bb_separator.gif" alt="|" /></td>
	<c:if test="${settings.showjavacode>0&&special==0}">
	<td><a id="${editorid}_cmd_javacode"><img src="images/common/bb_code_java.gif" title="<bean:message key="insert_code" arg0="Java"/>" alt="Code" /></a></td>
	<td><a id="${editorid}_cmd_jscriptcode"><img src="images/common/bb_code_js.gif" title="<bean:message key="insert_code" arg0="Javascript/JS"/>" alt="Code" /></a></td>
	<td><a id="${editorid}_cmd_sqlcode"><img src="images/common/bb_code_sql.gif" title="<bean:message key="insert_code" arg0="sql"/>" alt="Code" /></a></td>
	<td><a id="${editorid}_cmd_xmlcode"><img src="images/common/bb_code_xml.gif" title="<bean:message key="insert_code" arg0="xml,html,xhtml,xslt,xhtml"/>" alt="Code" /></a></td>
	<td><a id="${editorid}_cmd_csscode"><img src="images/common/bb_code_css.gif" title="<bean:message key="insert_code" arg0="css"/>" alt="Code" /></a></td>
	<td><a id="${editorid}_cmd_csharpcode"><img src="images/common/bb_code_csharp.gif" title="<bean:message key="insert_code" arg0="CSharp"/>" alt="Code" /></a></td>
	<td><img src="images/common/bb_separator.gif" alt="|" /></td>
	</c:if>
	<c:if test="${forum.allowbbcode>0&&usergroups.allowcusbbcode>0}">
		<c:forEach items="${bbcodes_display}" var="bbcode" varStatus="index">
			<td><a id="${editorid}_cmd_custom${bbcode.value.params}_${bbcode.key}"><img src="images/common/${bbcode.value.icon}" title="${bbcode.value.explanation}" alt="${bbcode.key}" /></a></td><c:if test="${index.count==5||index.count==25}"></tr></table><table summary="Editor ToolBar" cellpadding="0" cellspacing="0" id="${editorid}_morebuttons${index.count==5?1:2}" style='${advanceeditor?"":"display: none"}'><tr></c:if>
		</c:forEach>
	</c:if>
</tr></table>
<div id="${editorid}_switcher" class="editor_switcher_bar"><a id="${editorid}_buttonctrl" class="right"><c:choose><c:when test="${advanceeditor}"><bean:message key="editor_mode_simple"/></c:when><c:otherwise><bean:message key="editor_mode_all"/></c:otherwise></c:choose></a> <button type="button" id="bbcodemode" ><bean:message key="a_setting_editor_mode_jspruncode"/></button> <button type="button" id="wysiwygmode"><bean:message key="a_setting_editor_mode_wysiwyg"/></button></div></td></tr>
<tr><td class="editortoolbar">
<div class="popupmenu_popup fontname_menu" id="${editorid}_popup_fontname_menu" style="display: none"><ul unselectable="on"><c:forEach items="${fontoptions}" var="fontname"><li onclick="jspruncode('fontname', '${fontname}');hideMenu()" style="font-family: ${fontname}" unselectable="on">${fontname}</li></c:forEach></ul></div>
<div class="popupmenu_popup fontsize_menu" id="${editorid}_popup_fontsize_menu" style="display: none"><ul unselectable="on"><c:forEach begin="1" end="7" var="size"><li onclick="jspruncode('fontsize', ${size});hideMenu()" unselectable="on"><font size="${size}" unselectable="on">${size}</font></li></c:forEach></ul></div>
<c:if test="${forum.allowmediacode>0}"><div class="popupmenu_popup" id="${editorid}_popup_media_menu" style="width: 240px;display: none">
	<input type="hidden" id="${editorid}_mediatype" value="ra">
	<input type="hidden" id="${editorid}_mediaautostart" value="0">
	<table cellpadding="4" cellspacing="0" border="0" unselectable="on">
	<tr class="popupmenu_option"><td nowrap><bean:message key="post_jspruncode_media_url"/>:<br /><input id="${editorid}_mediaurl" size="40" value="" onkeyup="setmediatype('${editorid}')" /></td></tr>
	<tr class="popupmenu_option"><td nowrap>
		<label style="float:left;width:32%"><input type="radio" name="${editorid}_mediatyperadio" id="${editorid}_mediatyperadio_ra" onclick="$('${editorid}_mediatype').value = 'ra'" checked="checked">RA</label>
		<label style="float:left;width:32%"><input type="radio" name="${editorid}_mediatyperadio" id="${editorid}_mediatyperadio_wma" onclick="$('${editorid}_mediatype').value = 'wma'">WMA</label>
		<label style="float:left;width:32%"><input type="radio" name="${editorid}_mediatyperadio" id="${editorid}_mediatyperadio_mp3" onclick="$('${editorid}_mediatype').value = 'mp3'">MP3</label>
		<label style="float:left;width:32%"><input type="radio" name="${editorid}_mediatyperadio" id="${editorid}_mediatyperadio_rm" onclick="$('${editorid}_mediatype').value = 'rm'">RM/RMVB</label>
		<label style="float:left;width:32%"><input type="radio" name="${editorid}_mediatyperadio" id="${editorid}_mediatyperadio_wmv" onclick="$('${editorid}_mediatype').value = 'wmv'">WMV</label>
		<label style="float:left;width:32%"><input type="radio" name="${editorid}_mediatyperadio" id="${editorid}_mediatyperadio_mov" onclick="$('${editorid}_mediatype').value = 'mov'">MOV</label>
	</td></tr>
	<tr class="popupmenu_option"><td nowrap><label style="float:left;width:32%"><bean:message key="width"/>: <input id="${editorid}_mediawidth" size="5" value="400" /></label> <label style="float:left;width:32%"><bean:message key="high"/>: <input id="${editorid}_mediaheight" size="5" value="300" /></label> <label style="float:left;width:32%"><input type="checkbox" onclick="$('${editorid}_mediaautostart').value = this.checked ? 1 : 0"> <bean:message key="video_auto"/></label></td></tr>
	<tr class="popupmenu_option"><td align="center" colspan="2"><input type="button" size="8" value="<bean:message key="submitf"/>" onclick="setmediacode('${editorid}')"> <input type="button" onclick="hideMenu()" value="<bean:message key="cancelf"/>" /></td></tr>
	</table>
</div></c:if>
<div class="popupmenu_popup" id="${editorid}_popup_forecolor_menu" style="display: none"><table cellpadding="0" cellspacing="0" border="0" unselectable="on" style="width: auto;"><tr><c:forEach items="${coloroptions}" var="colorname" varStatus="index"><td class="editor_colornormal" onclick="jspruncode('forecolor', '${colorname}');hideMenu()" unselectable="on" onmouseover="colorContext(this, 'mouseover')" onmouseout="colorContext(this, 'mouseout')"><div style="background-color: ${colorname}" unselectable="on"></div></td>${index.count%8==0 ? "</tr><tr>" : ""}</c:forEach></tr></table></div>
</td></tr></table></c:if>
<table class="editor_text" summary="Message Textarea" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
	<tr><td><textarea class="autosave" name="message" rows="10" cols="60" style="width:99%; height:250px" tabindex="100" id="${editorid}message">${action=='edit' ? post.message : message}</textarea></td></tr>
</table>
<div id="${editorid}_bottom" style='${advanceeditor?"":"border-top: none;display: none"}'>
<table summary="Enitor Buttons" cellpadding="0" cellspacing="0" class="editor_button" style="border-top: none;">
	<tr>
		<td style="border-top: none;"><div class="editor_textexpand"><img src="images/common/bb_contract.gif" width="11" height="21" title="<bean:message key="post_jspruncode_contract"/>" alt="<bean:message key="post_jspruncode_contract"/>" id="${editorid}_contract" /><img src="images/common/bb_expand.gif" width="12" height="21" title="<bean:message key="post_jspruncode_expand"/>" alt="<bean:message key="post_jspruncode_expand"/>" id="${editorid}_expand" /></div></td>
		<td align="right" style="border-top: none;"><button type="button" id="checklength"><bean:message key="post_check_length"/></button> <button type="button" name="previewbutton" id="previewbutton" tabindex="102"><bean:message key="post_previewpost"/></button> <button type="button" tabindex="103" id="clearcontent"><bean:message key="post_topicreset"/></button></td>
	</tr>
</table>
<c:if test="${allowpostattach}"><table class="box" summary="Upload" cellspacing="0" cellpadding="0">
	<thead><tr><th><bean:message key="access_postattach"/></th><c:if test="${usergroups.allowsetattachperm>0}"><td class="nums"><bean:message key="threads_readperm"/></td></c:if><c:if test="${usergroups.maxprice>0&&extcredit!=null}"><td class="nums"><bean:message key="magics_price"/></td></c:if><td><bean:message key="description"/></td></tr></thead>
	<tbody id="attachbodyhidden" style="display:none"><tr>
		<th><input type="file" name="attach" /> <span id="localfile[]"></span> <input type="hidden" name="localid[]" /></th>
		<c:if test="${usergroups.allowsetattachperm>0}"><td class="nums"><input type="text" name="attachperm[]" value="0" size="1" /></td></c:if>
		<c:if test="${usergroups.maxprice>0&&extcredit!=null}"><td class="nums"><input type="text" name="attachprice[]" value="0" size="1" />${extcredit.unit}</td></c:if>
		<td><input type="text" name="attachdesc[]" size="25" /></td>
	</tr></tbody>
	<tbody id="attachbody"></tbody>
	<tr><td colspan="5" style="border-bottom: none;"><bean:message key="attachment_size"/>: <strong><c:choose><c:when test="${maxattachsize>0}"><bean:message key="lower_than"/> ${maxattachsize} kb </c:when><c:otherwise><bean:message key="size_no_limit"/></c:otherwise></c:choose><c:if test=""></c:if></strong><br />	<c:if test="${attachextensions!=''}"><bean:message key="attachment_allow_exts"/>: <strong>${attachextensions}</strong><br /></c:if><c:if test="${usergroups.maxprice>0&&extcredit!=null}"><bean:message key="magics_price"/>: <strong><bean:message key="post_price_comment"/> ${usergroups.maxprice} ${extcredit.unit}${extcredit.title}<c:if test="${settings.maxincperthread>0}"><bean:message key="post_price_income_comment"/> ${settings.maxincperthread} ${extcredit.unit}</c:if><c:if test="${settings.maxchargespan>0}"><bean:message key="post_price_charge_comment" arg0="${settings.maxchargespan}"/></c:if></strong></c:if></td></tr>
</table>
<div id="img_hidden" alt="1" style="position:absolute;top:-100000px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image');width:${settings.thumbwidth}px;height:${settings.thumbheight}px"></div></c:if>
</div>
</div></td>