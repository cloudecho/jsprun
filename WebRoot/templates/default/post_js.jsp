<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<script type="text/javascript">
if(!(is_ie >= 5 || is_moz >= 2)) {
	$('restoredata').style.display = 'none';
}
var editorid = '${editorid}';
var textobj = $(editorid + 'message');
var wysiwyg = (is_ie || is_moz || (is_opera >= 9)) && parseInt('${editormode}') && bbinsert == 1 ? 1 : 0;
var allowswitcheditor = parseInt('${allowswitcheditor}');
var allowhtml = parseInt('${usergroups.allowhtml}');
var forumallowhtml = parseInt('${forum.allowhtml}');
var allowsmilies = parseInt('${forum.allowsmilies}');
var allowbbcode = parseInt('${forum.allowbbcode}');
var allowimgcode = parseInt('${forum.allowimgcode}');
var special = parseInt('${special ==null ? 0 : special}');
var BORDERCOLOR = "${styles.BORDERCOLOR}";
var ALTBG2 = "${styles.ALTBG2}";
var charset = "GBK";
var smilies = {};
<c:forEach items="${smilies_displays}" var="smilies_display">
	<c:forEach items="${smilies_display.value}" var="smiley">smilies['${smiley.key}'] = {"code" : "${smiley.value.code}", "url" : "${smileytypeMap[smilies_display.key].directory}/${smiley.value.url}"};</c:forEach>
</c:forEach>
lang['post_autosave_none']			= '<bean:message key="post_autosave_none"/>';
lang['post_autosave_confirm']		= '<bean:message key="post_autosave_confirm"/>';
lang['post_video_uploading']		= '<bean:message key="video_uploading"/>';
lang['post_video_vclass_required']	= '<bean:message key="video_vclass_required"/>';
lang['browse_reply']				= '<bean:message key="browse_reply"/>';
lang['browse_credits']				= '<bean:message key="browse_credits"/>';
lang['display']						= '<bean:message key="display"/>';
lang['post_jspruncode_fontname']	= '<bean:message key="post_jspruncode_fontname"/>';
lang['last_page'] = '<bean:message key="last_page"/>';
lang['next_page'] = '<bean:message key="next_page"/>';
var smcols = "${settings.smcols}";
</script>
<c:if test="${allowpostattach}">
	<script type="text/javascript">
	var thumbwidth = parseInt(${settings.thumbwidth});
	var thumbheight = parseInt(${settings.thumbheight});
	var extensions = '${attachextensions}';
	lang['post_attachment_ext_notallowed']	= '<bean:message key="post_attachment_ext_notallowed"/>';
	lang['post_attachment_img_invalid']	= '<bean:message key="post_attachment_img_invalid"/>';
	lang['post_attachment_deletelink']	= '<bean:message key="delete"/>';
	lang['post_attachment_insert']		= '<bean:message key="post_attachment_insert"/>';
	lang['post_attachment_insertlink']	= '<bean:message key="post_attachment_insertlink"/>';
	</script>
	<script type="text/javascript" src="include/javascript/post_attach.js"></script>
</c:if>
<script type="text/javascript">smilies_show('${editorid}smiliesdiv', '${settings.smcols}', wysiwyg, '${editorid}');</script>
<script type="text/javascript" src="include/javascript/post.js"></script>
<c:if test="${settings.bbinsert>0}">
	<script type="text/javascript">
	var fontoptions = new Array("仿宋_GB2312", "黑体", "楷体_GB2312", "宋体", "新宋体", "微软雅黑", "Trebuchet MS", "Tahoma", "Arial", "Impact", "Verdana", "Times New Roman");
	var custombbcodes = new Array();
	<c:if test="${forum.allowbbcode>0&&usergroups.allowcusbbcode>0}"><c:forEach items="${bbcodes_display}" var="bbcode" varStatus="index">custombbcodes["${bbcode.key}"]={'prompt':'${bbcode.value.prompt}'};</c:forEach></c:if>
		lang['enter_list_item']			= '<bean:message key="post_jspruncode_listitem"/>';
	lang['enter_link_url']			= '<bean:message key="post_jspruncode_linkurl"/>';
	lang['enter_image_url']			= '<bean:message key="post_jspruncode_imageurl"/>';
	lang['enter_email_link']		= '<bean:message key="post_jspruncode_emaillink"/>';
	lang['fontname']			= '<bean:message key="post_jspruncode_fontname"/>';
	lang['fontsize']			= '<bean:message key="post_jspruncode_fontsize"/>';
	lang['post_advanceeditor']		= '<bean:message key="editor_mode_all"/>';
	lang['post_simpleeditor']		= '<bean:message key="editor_mode_simple"/>';
	lang['submit']				= '<bean:message key="submitf"/>';
	lang['cancel']				= '<bean:message key="cancelf"/>';
	lang['tablerows']			= '<bean:message key="tablerows"/>';
	lang['tablecols']			= '<bean:message key="tablecols"/>';
	lang['tablewidth']			= '<bean:message key="tablewidth"/>';
	lang['bgcolor']				= '<bean:message key="bgcolor"/>';
	</script>
	<script type="text/javascript" src="include/javascript/editor.js"></script>
</c:if>
<script type="text/javascript" src="include/javascript/bbcode.js"></script>
<c:if test="${action=='edit'||action=='reply'&&repquote!=null}">
	<script type="text/javascript">
	if(wysiwyg) {
		editdoc.body.innerHTML = bbcode2html(textobj.value);
	}
	</script>
</c:if>
<script type="text/javascript" src="include/javascript/post_editor.js"></script>
<script type="text/javascript">
$(editorid + '_contract').onclick = function() {resizeEditor(-100)};
$(editorid + '_expand').onclick = function() {resizeEditor(100)};
$('checklength').onclick = function() {checklength($('postform'))};
$('previewbutton').onclick = function() {previewpost()};
$('clearcontent').onclick = function() {clearcontent()};
$('postform').onsubmit = function() {validate(this);if($('postsubmit').name != 'editsubmit') return false};
if( ${action=='newthread'}){
	$('subject').focus();
}
else{
	checkFocus();
	setCaretAtEnd();
}
</script>