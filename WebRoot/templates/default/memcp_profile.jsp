<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<link href="include/css/keyboard.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
lang["kb_backSpace"] = '← <bean:message key="kb_backSpace"/>';
lang["kb_capsLockOff"] = '<bean:message key="kb_capsLockOff"/>';
lang["kb_capsLockOn"] = '<bean:message key="kb_capsLockOn"/>';
lang["kb_clear"] = '<bean:message key="kb_clear"/>';
lang["kb_enter"] = '←|\n<bean:message key="kb_enter"/>';
lang["kb_title"] = '<bean:message key="kb_title"/>';
</script>
<script src="include/javascript/keyboard.js" type="text/javascript"></script>
<div class="container">
<div id="foruminfo"><div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="memcp_profile"/></div></div>
<div class="content">
<script type="text/javascript">
lang['calendar_Sun'] = '<bean:message key="calendar_Sun"/>';
lang['calendar_Mon'] = '<bean:message key="calendar_Mon"/>';
lang['calendar_Tue'] = '<bean:message key="calendar_Tue"/>';
lang['calendar_Wed'] = '<bean:message key="calendar_Wed"/>';
lang['calendar_Thu'] = '<bean:message key="calendar_Thu"/>';
lang['calendar_Fri'] = '<bean:message key="calendar_Fri"/>';
lang['calendar_Sat'] = '<bean:message key="calendar_Sat"/>';
lang['old_month'] = '<bean:message key="old_month"/>';
lang['select_year'] = '<bean:message key="select_year"/>';
lang['select_month'] = '<bean:message key="select_month"/>';
lang['next_month'] = '<bean:message key="next_month"/>';
lang['calendar_hr'] = '<bean:message key="calendar_hr"/>';
lang['calendar_min'] = '<bean:message key="calendar_min"/>';
lang['calendar_month'] = '<bean:message key="calendar_month"/>';
lang['calendar_today'] = '<bean:message key="calendar_today"/>';
</script>
<script src="include/javascript/calendar.js" type="text/javascript"></script>
<script src="include/javascript/bbcode.js" type="text/javascript"></script>
<script type="text/javascript">
var charset = 'UTF-8';
var maxsigsize = parseInt('${usergroups.maxsigsize}');
var maxbiosize = parseInt('${usergroups.maxbiosize > 0 ? usergroups.maxbiosize : 200}');
var maxbiotradesize = parseInt('${settings.maxbiotradesize > 0 ? settings.maxbiotradesize : 400}');
var allowhtml = 0;
var forumallowhtml = 0;
var allowsmilies = 0;
var allowbbcode = 0;
var allowimgcode = 0;
var allowbiobbcode = parseInt('${usergroups.allowbiobbcode}');
var allowbioimgcode = parseInt('${usergroups.allowbioimgcode}');
var allowsigbbcode = parseInt('${usergroups.allowsigbbcode}');
var allowsigimgcode = parseInt('${usergroups.allowsigimgcode}');
function parseurl(str, mode) {
	str = str.replace(/([^>=\]"'\/]|^)((((https?|ftp):\/\/)|www\.)([\w\-]+\.)*[\w\-\u4e00-\u9fa5]+\.([\.a-zA-Z0-9]+|\u4E2D\u56FD|\u7F51\u7EDC|\u516C\u53F8)((\?|\/|:)+[\w\.\/=\?%\-&~`@':+!]*)+\.(jpg|gif|png|bmp))/ig, mode == 'html' ? '$1<img src="$2" border="0">' : '$1[img]$2[/img]');
	str = str.replace(/([^>=\]"'\/@]|^)((((https?|ftp|gopher|news|telnet|rtsp|mms|callto|bctp|ed2k):\/\/)|www\.)([\w\-]+\.)*[:\.@\-\w\u4e00-\u9fa5]+\.([\.a-zA-Z0-9]+|\u4E2D\u56FD|\u7F51\u7EDC|\u516C\u53F8)((\?|\/|:)+[\w\.\/=\?%\-&~`@':+!#]*)*)/ig, mode == 'html' ? '$1<a href="$2" target="_blank">$2</a>' : '$1[url]$2[/url]');
	str = str.replace(/([^\w>=\]:"'\.\/]|^)(([\-\.\w]+@[\.\-\w]+(\.\w+)+))/ig, mode == 'html' ? '$1<a href="mailto:$2">$2</a>' : '$1[email]$2[/email]');
	return str;
}
function validate(theform) {
if(${typeid==4}){
	if(mb_strlen(theform.signaturenew.value) > maxsigsize) {
		alert('<bean:message key="memcp_profile_sig_toolong" arg0="${usergroups.maxsigsize}"/>');
		return false;
	}
	if(mb_strlen(theform.bionew.value) > maxbiosize) {
		alert('<bean:message key="memcp_profile_bio_toolong" arg0="${usergroups.maxbiosize}"/>');
		return false;
	}
	if(mb_strlen(theform.biotradenew.value) > maxbiotradesize) {
		alert('<bean:message key="memcp_profile_biotrade_toolong" arg0="${settings.maxbiotradesize}"/>');
		return false;
	}
}
return true;
}
function previewavatar(url) {
if(url) {
	$('avatarpreview').innerHTML = '<img id="previewimg" /><br />';
	$('previewimg').src = url;
	if($('avatarwidthnew')) {
		$('avatarwidthnew').value = $('previewimg').clientWidth;
		$('avatarheightnew').value = $('previewimg').clientHeight;
	}
} else {
	$('avatarpreview').innerHTML = '';
}
}
</script>
<form name="reg" method="post" action="memcp.jsp?action=profile&typeid=${typeid}&submit=yes" onSubmit="return validate(this)" ${typeid=='4'?"enctype=multipart/form-data":""}>
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<div class="mainbox formbox">
<h1><bean:message key="memcp_profile"/></h1>
<ul class="tabs">
	<li ${typeid== 1?"class=current":""}><a href="memcp.jsp?action=profile&typeid=1"><bean:message key="memcp_profile_type_1"/></a></li>
	<li ${typeid== 2?"class=current":""}><a href="memcp.jsp?action=profile&typeid=2"><bean:message key="memcp_profile_type_2"/></a></li>
	<c:if test="${profilelist!=null || requiredfile!=null}"><li ${typeid== 3?"class=current":""}><a href="memcp.jsp?action=profile&typeid=3"><bean:message key="memcp_profile_type_3"/></a></li></c:if>
	<li ${typeid== 4?"class=current":""}><a href="memcp.jsp?action=profile&typeid=4"><bean:message key="memcp_profile_type_4"/></a></li>
	<li ${typeid== 5?"class=current":""}><a href="memcp.jsp?action=profile&typeid=5"><bean:message key="memcp_profile_type_5"/></a></li>
</ul>
<table summary="<bean:message key="memcp_profile"/>" cellspacing="0" cellpadding="0">
	<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode"/></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" /> <em class="tips"><strong><bean:message key="seccode_click"/></strong> <bean:message key="seccode_refresh"/></em><script type="text/javascript">var seccodedata = [${seccodedata['width']}, ${seccodedata['height']}, ${seccodedata['type']}];</script></td></tr></c:if>
	<c:choose><c:when test="${typeid==1}">
		<tr><th><label for="oldpassword"><bean:message key="old_password"/></label></th><td><input type="password" name="oldpassword" id="oldpassword" size="25" tabindex="1" /><input type="button" class="keybord_button" style="margin-left: -24px;" onclick="jrunKeyBoard('oldpassword',this);" title="<bean:message key="keyboard_title"/>"></td></tr>
		<tr><th><label for="newpassword"><bean:message key="new_password"/></label></th><td><input type="password" name="newpassword" id="newpassword" size="25" tabindex="2" /><input type="button" class="keybord_button" style="margin-left: -24px;" onclick="jrunKeyBoard('newpassword',this);" title="<bean:message key="keyboard_title"/>"></td></tr>
		<tr><th><label for="newpassword2"><bean:message key="new_password_confirm"/></label></th><td><input type="password" name="newpassword2" id="newpassword2"size="25" tabindex="3" /><input type="button" class="keybord_button" style="margin-left: -24px;" onclick="jrunKeyBoard('newpassword2',this);" title="<bean:message key="keyboard_title"/>"></td></tr>
		<tr><th><label for="emailnew"><bean:message key="email"/></label></th><td><input type="text" name="emailnew" id="emailnew" size="25" value="${user.email}" maxlength="40" tabindex="4" /></td></tr>
		<tr><th><label for="questionidnew"><bean:message key="security_question"/></label></th><td><select name="questionidnew" id="questionidnew" tabindex="5" ><c:if test="${user.secques!=''}"><option value="-1"><bean:message key="memcp_profile_security_keep"/></option></c:if><option value="0"><bean:message key="security_question_0"/></option><option value="1"><bean:message key="security_question_1"/></option><option value="2"><bean:message key="security_question_2"/></option><option value="3"><bean:message key="security_question_3"/></option><option value="4"><bean:message key="security_question_4"/></option><option value="5"><bean:message key="security_question_5"/></option><option value="6"><bean:message key="security_question_6"/></option><option value="7"><bean:message key="security_question_7"/></option></select> <em><bean:message key="memcp_profile_security_comment"/></em></td></tr>
		<tr><th><label for="answernew"><bean:message key="security_answer"/></label></th><td><input type="text" name="answernew" id="answernew" size="25" tabindex="6" /> <em><bean:message key="memcp_profile_security_answer_comment"/></em></td></tr>
	</c:when><c:when test="${typeid==2}">
		<c:if test="${usergroups.allownickname>0}"><tr><th><label for="nicknamenew"><bean:message key="nickname"/></label></th><td><input type="text" name="nicknamenew" id="nicknamenew" size="25" value="${memberfield.nickname}" maxlength="30"/></td></tr></c:if>
		<c:if test="${usergroups.allowcstatus>0}"><tr><th><label for="cstatusnew"><bean:message key="custom_status"/></label></th><td><input type="text" name="cstatusnew" id="cstatusnew" size="25" value="${memberfield.customstatus}" maxlength="30"/></td></tr></c:if>
		<tr><th><bean:message key="gender"/></th><td><label><input class="radio" type="radio" name="gendernew" value="1" ${user.gender==1 ? "checked" : ""}/> <bean:message key="a_member_edit_gender_male"/> &nbsp;</label> <label><input class="radio" type="radio" name="gendernew" value="2" ${user.gender==2 ? "checked" : ""}/> <bean:message key="a_member_edit_gender_female"/> &nbsp;</label> <label><input class="radio" type="radio" name="gendernew" value="0" ${user.gender==0 ? "checked" : ""}/> <bean:message key="a_member_edit_gender_secret"/></label></td></tr>
		<tr><th><label for="bdaynew"><bean:message key="birthday"/></label></th><td><input type="text" name="bdaynew" id="bdaynew" size="25" onclick="showcalendar(event, this)" onfocus="showcalendar(event, this);if(this.value=='0000-00-00')this.value=''" value="${user.bday}" /></td></tr>
		<tr><th><label for="locationnew"><bean:message key="location"/></label></th><td><input type="text" name="locationnew" id="locationnew" size="25" value="${memberfield.location}" maxlength="30"/></td></tr>
		<tr><th><label for="sitenew"><bean:message key="homepage"/></label></th><td><input type="text" name="sitenew" id="sitenew" size="25" value="${memberfield.site}" maxlength="75"/></td></tr>
		<tr><th><label for="qqnew">QQ</label></th><td><input type="text" name="qqnew" id="qqnew" size="25" value="${memberfield.qq}" maxlength="12"/></td></tr>
		<tr><th><label for="icqnew">ICQ</label></th><td><input type="text" name="icqnew" id="icqnew" size="25" value="${memberfield.icq}" maxlength="12"/></td></tr>
		<tr><th><label for="yahoonew">Yahoo</label></th><td><input type="text" name="yahoonew" id="yahoonew" size="25" value="${memberfield.yahoo}" maxlength="40"/></td></tr>
		<tr><th><label for="msnnew">MSN</label></th><td><input type="text" name="msnnew" id="msnnew" size="25" value="${memberfield.msn}" maxlength="40"/></td></tr>
		<tr><th><label for="taobaonew"><bean:message key="taobao"/></label></th><td><input type="text" name="taobaonew" id="taobaonew" size="25" value="${memberfield.taobao}" maxlength="40"/></td></tr>
		<tr><th><label for="alipaynew"><bean:message key="alipay"/></label></th><td><input type="text" name="alipaynew" id="alipaynew" size="25" value="${memberfield.alipay}" maxlength="50"/></td></tr>
	</c:when><c:when test="${typeid==3&&(profilelist!=null||requiredfile!=null)}">
		<c:if test="${requiredfile!=null}">
			<thead class="separation"><tr><td colspan="2"><bean:message key="required_info"/></td></tr></thead>
			<c:forEach items="${requiredfile}" var="profile"><tr><th>${profile.title}<br>${profile.description}</th><td><c:choose><c:when test="${profile.selective==1}">${profile.select}</c:when><c:otherwise><input type="text" name="profile${profile.fieldid}" value="${profile.select}" maxlength="50" ${profile.unchangeable==1 && profile.select!=null&&profile.select!=''?"disabled":""}/></c:otherwise></c:choose></td></tr></c:forEach>
		</c:if>
		<c:if test="${profilelist!=null}">
			<thead class="separation"><tr><td colspan="2"><bean:message key="optional_info"/></td></tr></thead>
			<c:forEach items="${profilelist}" var="profile"><tr><th>${profile.title}<br>${profile.description}</th><td><c:choose><c:when test="${profile.selective==1}">${profile.select}</c:when><c:otherwise><input type="text" name="profile${profile.fieldid}" value="${profile.select}" ${profile.unchangeable==1 && profile.select!=null&&profile.select!=''?"disabled":""}/></c:otherwise></c:choose></td></tr></c:forEach>
		</c:if>
	</c:when><c:when test="${typeid==4}">
		<c:choose>
			<c:when test="${usergroups.allowavatar==1}"><tr><th valign="top"><label for="urlavatar"><bean:message key="avatar"/></label></th><td><span id="avatarpreview"><c:if test="${!empty memberfield.avatar}"><img src="${memberfield.avatar}" /><br /></c:if></span> <input type="text" name="urlavatar" id="urlavatar" onchange="previewavatar(this.value)" size="25" value="${memberfield.avatar}" /> &nbsp; <a href="member.do?action=viewavatars&page=1" onclick="ajaxget(this.href, 'avatardiv');doane(event);"><bean:message key="memcp_avatar_list"/></a> <span id="statusid"></span> <div id="avatardiv" style="display: none; margin-top: 10px;"></div></td></tr></c:when>
			<c:when test="${usergroups.allowavatar==2}"><tr><th valign="top"><label for="urlavatar"><bean:message key="avatar"/></label></th><td><span id="avatarpreview"><c:if test="${!empty memberfield.avatar}"><img src="${memberfield.avatar}" width="${memberfield.avatarwidth}" height="${memberfield.avatarheight}"/><br /></c:if></span> <input type="text" name="urlavatar" id="urlavatar" onchange="previewavatar(this.value)" size="25" value="${memberfield.avatar}" /> &nbsp; <a href="member.do?action=viewavatars&page=1" onclick="ajaxget(this.href, 'avatardiv');doane(event);"><bean:message key="memcp_avatar_list"/></a> <div id="avatardiv" style="display: none; margin-top: 10px;"></div><br /><bean:message key="width"/>: <input type="text" name="avatarwidthnew" id="avatarwidthnew" size="1" value="${memberfield.avatarwidth==0 ? '*' : memberfield.avatarwidth}" /> &nbsp; <bean:message key="high"/>: <input type="text" name="avatarheightnew" id="avatarheightnew" size="1" value="${memberfield.avatarheight==0 ? '*' : memberfield.avatarheight}" /></td></tr></c:when>
			<c:when test="${usergroups.allowavatar==3}"><tr><th valign="top"><label for="urlavatar"><bean:message key="avatar"/></label></th><td><span id="avatarpreview"><c:if test="${!empty memberfield.avatar}"><img src="${memberfield.avatar}" width="${memberfield.avatarwidth}" height="${memberfield.avatarheight}"/><br /></c:if></span> <input type="text" name="urlavatar" id="urlavatar" onchange="previewavatar(this.value)" size="25" value="${memberfield.avatar}" /> &nbsp; <a href="member.do?action=viewavatars&page=1" onclick="ajaxget(this.href, 'avatardiv');doane(event);"><bean:message key="memcp_avatar_list"/></a> <div id="avatardiv" style="display: none; margin-top: 10px;"></div><br /><input type="file" name="customavatar" onchange="$('avatarwidthnew').value = $('avatarheightnew').value = '*';$('urlavatar').value = '';if(this.value) previewavatar('');" size="25" /><br /><bean:message key="width"/>: <input type="text" name="avatarwidthnew" id="avatarwidthnew" size="1" value="${memberfield.avatarwidth==0 ? '*' : memberfield.avatarwidth}" /> &nbsp; <bean:message key="high"/>: <input type="text" name="avatarheightnew" id="avatarheightnew" size="1" value="${memberfield.avatarheight==0 ? '*' : memberfield.avatarheight}" /></td></tr></c:when>
		</c:choose>
		<tr>
			<th valign="top"><label for="bionew"><bean:message key="bio"/> (${usergroups.maxbiosize>0? usergroups.maxbiosize : 200} <bean:message key="bytes_limited"/>)<br/><em><bean:message key="memcp_nocustomizebbcode"/><br /><br /><a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank">${faqs.JspRuncode.keyword}</a> <b><c:choose><c:when test="${usergroups.allowbiobbcode>0}"><bean:message key="available"/></c:when><c:otherwise><bean:message key="disable"/></c:otherwise></c:choose></b><br /><bean:message key="post_imgcode"/><b><c:choose><c:when test="${usergroups.allowbioimgcode>0}"><bean:message key="available"/></c:when><c:otherwise><bean:message key="disable"/></c:otherwise></c:choose></b><br/><br/><a href="###" onclick="allowbbcode = allowbiobbcode;allowimgcode = allowbioimgcode;$('biopreview').innerHTML = bbcode2html($('bionew').value)"><bean:message key="preview"/></a></em></label></th>
			<td><div id="biopreview"></div><textarea rows="8" cols="30" style="width: 380px" id="bionew" name="bionew" type="_moz">${bio}</textarea></td>
		</tr>
		<tr>
			<th valign="top"><label for="biotradenew"><bean:message key="tradeinfo"/> (${settings.maxbiotradesize > 0 ? settings.maxbiotradesize : 400} <bean:message key="bytes_limited"/>)<br /><em><bean:message key="memcp_nocustomizebbcode"/><br /><br /><a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank">${faqs.JspRuncode.keyword}</a> <b><bean:message key="available"/></b><br /><bean:message key="post_imgcode"/> <b><bean:message key="available"/></b><br /><br /><a href="###" onclick="allowbbcode = 1;allowimgcode = 1;$('biotradepreview').innerHTML = bbcode2html($('biotradenew').value)"><bean:message key="preview"/></a></em></label></th>
			<td><div id="biotradepreview"></div><textarea rows="8" cols="30" style="width: 380px" id="biotradenew" name="biotradenew">${shop}</textarea></td>
		</tr>
		<c:if test="${usergroups.maxsigsize>0}">
			<tr>
				<th valign="top"><label for="signaturenew"><bean:message key="signature"/> (${usergroups.maxsigsize} <bean:message key="bytes_limited"/>)<br /><em><bean:message key="memcp_nocustomizebbcode"/></em><br /><br /><em><a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank">${faqs.JspRuncode.keyword}</a> <b><c:choose><c:when test="${usergroups.allowsigbbcode>0}"><bean:message key="available"/></c:when><c:otherwise><bean:message key="disable"/></c:otherwise></c:choose></b><br /><bean:message key="post_imgcode"/> <b><c:choose><c:when test="${usergroups.allowsigimgcode>0}"><bean:message key="available"/></c:when><c:otherwise><bean:message key="disable"/></c:otherwise></c:choose></b><br /><br /><a href="###" onclick="allowbbcode = allowsigbbcode;allowimgcode = allowsigimgcode;$('signaturepreview').innerHTML = bbcode2html($('signaturenew').value)"><bean:message key="preview"/></a></em></label></th>
				<td><div id="signaturepreview"></div><textarea rows="8" cols="30" style="width: 380px" id="signaturenew" name="signaturenew" type="_moz">${memberfield.sightml}</textarea></td>
			</tr>
		</c:if>
	</c:when><c:when test="${typeid==5}">
		<tr><th><bean:message key="menu_style"/></th><td><select name="styleidnew"><option value="0"><bean:message key="use_default"/></option><c:forEach items="${forumStyles}" var="style"><option value="${style.key}" ${style.key==user.styleid||style.key==styleid ? "selected":""}>${style.value}</option></c:forEach></select></td></tr>
		<tr><th><bean:message key="tpp"/></th><td><select name="tppnew"><option value="0" selected="selected"><bean:message key="use_default"/></option><option value="10" ${user.tpp==10?"selected":""}>10</option><option value="20" ${user.tpp==20?"selected":""}>20</option><option value="30" ${user.tpp==30?"selected":""}>30</option></select></td></tr>
		<tr><th><bean:message key="ppp"/></th><td><select name="pppnew"><option value="0" selected="selected"><bean:message key="use_default"/></option><option value="5" ${user.ppp==5?"selected":""}>5</option><option value="10" ${user.ppp==10?"selected":""}>10</option><option value="15" ${user.ppp==15?"selected":""}>15</option></select></td></tr>
		<tr><th><bean:message key="memcp_profile_signature_conf"/></th><td><select name="ssnew"><option value="2" ${custom[2]==2?"selected":""}><bean:message key="use_default"/></option><option value="1" ${custom[2]==1?"selected":""}><bean:message key="memcp_profile_signature"/></option><option value="0" ${custom[2]==0?"selected":""}><bean:message key="memcp_profile_nosignature"/></option></select></td></tr>
		<tr><th><bean:message key="memcp_profile_avatar_conf"/></th><td><select name="sanew"><option value="2" ${custom[1]==2?"selected":""}><bean:message key="use_default"/></option><option value="1" ${custom[1]==1?"selected":""}><bean:message key="memcp_profile_avatar"/></option><option value="0" ${custom[1]==0?"selected":""}><bean:message key="memcp_profile_noavatar"/></option></select></td></tr>
		<tr><th><bean:message key="memcp_profile_image_conf"/><br /><em><bean:message key="memcp_profile_image_conf_comment"/></em></th><td><select name="sinew"><option value="2" ${custom[0]==2?"selected":""}><bean:message key="use_default"/></option><option value="1" ${custom[0]==1?"selected":""}><bean:message key="memcp_profile_image"/></option><option value="0" ${custom[0]==0?"selected":""}><bean:message key="memcp_profile_noimage"/></option></select></td></tr>
		<tr><th><bean:message key="memcp_profile_editor_mode"/></th><td><select name="editormodenew"><option value="2" ${user.editormode==2?"selected":""}><bean:message key="use_default"/></option><option value="0" ${user.editormode==0?"selected":""}><bean:message key="a_setting_editor_mode_jspruncode"/></option><option value="1" ${user.editormode==1?"selected":""}><bean:message key="a_setting_editor_mode_wysiwyg"/></option></select></td></tr>
		<tr><th><bean:message key="timeoffset"/></th><td>
		<select name="timeoffsetnew">
		<option value="9999" ${user.timeoffset=='9999' ? 'selected' : ''}><bean:message key="use_default"/></option>
		<c:forEach items="${timeZoneIDs}" var="timeZoneID">
     <option value="${timeZoneID.key}"${user.timeoffset == timeZoneID.key ? ' selected' : ''}>${timeZoneID.value[1]}</option></c:forEach>
    </select></td></tr>
		<tr><th><bean:message key="timeformat"/></th><td><label><input type="radio" value="0" name="timeformatnew" ${user.timeformat==0?"checked":""}/><bean:message key="default"/> &nbsp;</label> <label><input type="radio" value="1" name="timeformatnew" ${user.timeformat==1?"checked":""}/><bean:message key="a_member_edit_timeformat_12"/> &nbsp;</label> <label><input type="radio" value="2" name="timeformatnew" ${user.timeformat==2?"checked":""}/><bean:message key="a_member_edit_timeformat_24"/></label></td></tr>
		<tr><th><bean:message key="dateformat"/></th><td><select name="dateformatnew"><option value="0" ${user.dateformat==0?"selected":""}><bean:message key="default"/></option><c:forEach items="${userdateformats}" var="userdateformat" varStatus="index"><option value="${index.count}" ${index.count==user.dateformat?"selected":""}>${userdateformat}</option></c:forEach></select></td></tr>
		<tr><th><bean:message key="pmsound"/></th><td><label><input type="radio" value="0" name="pmsoundnew" ${user.pmsound==0?"checked":""}/><bean:message key="none"/> &nbsp;</label> <label><input type="radio" value="1" name="pmsoundnew" ${user.pmsound==1?"checked":""}/><a href="images/sound/pm_1.wav">#1</a> &nbsp;</label> <label><input type="radio" value="2" name="pmsoundnew" ${user.pmsound==2?"checked":""}/><a href="images/sound/pm_2.wav">#2</a> &nbsp;</label> <label><input type="radio" value="3" name="pmsoundnew" ${user.pmsound==3?"checked":""}/><a href="images/sound/pm_3.wav">#3</a></label></td></tr>
		<tr><th><bean:message key="other_options"/></th><td><c:if test="${usergroups.allowinvisible>0}"><label><input type="checkbox" name="invisiblenew" value="1" ${user.invisible>0?'checked':''} /> <bean:message key="online_invisible"/></label><br /></c:if><label><input type="checkbox" name="showemailnew" value="1" ${user.showemail>0?'checked' : ''} /> <bean:message key="show_email_addr"/></label><br /><label><input type="checkbox" name="newsletternew" value="1" ${user.newsletter>0?'checked' : ''}/> <bean:message key="allow_newsletter"/></label><br /></td></tr>
	</c:when></c:choose>
	<tr><th>&nbsp;</th><td><button type="submit" class="submit" name="editsubmit" id="editsubmit" value="true"><bean:message key="submitf"/></button></td></tr>
</table>
</div>
</form>
</div>
<div class="side"><jsp:include flush="true" page="personal_navbar.jsp" /></div>
</div>
<jsp:include flush="true" page="footer.jsp" />