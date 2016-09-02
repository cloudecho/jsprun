<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <bean:message key="register" /></div>
<c:choose><c:when test="${bbrules=='1' && rulesubmit==null}">
<form name="bbrules" method="post" action="${regname}">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<input type="hidden" name="referer" value="index.jsp" />
<div class="mainbox formbox">
	<h1><bean:message key="register" /></h1>
	<table cellspacing="0" cellpadding="0" width="100%" align="center" class="register">
		<tbody><tr><td><br />${bbrulestxt}<br /><br /></td></tr></tbody>
		<tr class="btns" style="height: 40px"><td align="center" id="rulebutton"><bean:message key="rulemessage" /></td></tr>
	</table>
</div>
</form>
<script type="text/javascript">
var secs = 9;
var wait = secs * 1000;
$('rulebutton').innerHTML = "<bean:message key="rulemessage" /> (" + secs + ")";
for(i = 1; i <= secs; i++) {
	window.setTimeout("update(" + i + ")", i * 1000);
}
window.setTimeout("timer()", wait);
function update(num, value) {
	if(num == (wait/1000)) {
		$('rulebutton').innerHTML = "<bean:message key="rulemessage" />";
	} else {
		printnr = (wait / 1000) - num;
		$('rulebutton').innerHTML = "<bean:message key="rulemessage" /> (" + printnr + ")";
	}
}
function timer() {
	$('rulebutton').innerHTML = '<button type="submit" id="rulesubmit" name="rulesubmit" value="true"><bean:message key="agree" /></button> &nbsp; <button type="button" onclick="location.href=\'index.jsp\'"><bean:message key="disagree" /></button>';
}
</script>
</c:when><c:otherwise>
<script type="text/javascript">
	function showadv() {
		if(document.register.advshow.checked == true) {
			$("adv").style.display = "";
		} else {
			$("adv").style.display = "none";
		}
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
<form method="post" name="register" action="${regname}?regsubmit=yes" onSubmit="this.regsubmit.disabled=true;">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<div class="mainbox formbox">
	<span class="headactions"><a href="member.jsp?action=credits&view=promotion_register" target="_blank"><bean:message key="credits_policy_view" /></a></span>
	<h1><bean:message key="register" /></h1>
	<table summary="<bean:message key="register" />" cellspacing="0" cellpadding="0">
		<thead><tr><th><bean:message key="required_info" /></th><td>&nbsp;</td></tr></thead>
		<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode" /> *</label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" onBlur="checkseccode()" tabindex="1" /> <em class="tips" id="checkseccodeverify"><strong><bean:message key="seccode_click" /></strong> <bean:message key="seccode_refresh" /></em><script type="text/javascript">var seccodedata = [${seccodedata["width"]}, ${seccodedata["height"]},${seccodedata["type"]}];</script></td></tr></c:if>
		<c:if test="${secqaaStatus==1}"><tr><th><label for="secanswer"><bean:message key="secqaa" /> *</label></th><td><p id="secquestion">Loading question</p> <input type="text" name="secanswer" size="25" maxlength="50" id="secanswer" onBlur="checksecanswer()" tabindex="2" /> <span id="checksecanswer">&nbsp;</span><script type="text/javascript">ajaxget('ajax.do?action=updatesecqaa&inajax=1', 'secquestion');</script></td></tr></c:if>
		<tr><th><label for="username"><bean:message key="username" /> *</label></th><td><input type="text" id="username" name="username" size="25" maxlength="15" onBlur="checkusername()" tabindex="3" /> <span id="checkusername">&nbsp;</span></td></tr>
		<tr><th><label for="password"><bean:message key="passwordf" /> *</label></th><td><input type="password" name="password" size="25" id="password" onBlur="checkpassword()" tabindex="4" /><input type="button" class="keybord_button" style="margin-left: -24px;" onclick="jrunKeyBoard('password',this);" title="<bean:message key="keyboard_title"/>">&nbsp; <span id="checkpassword">&nbsp;</span></td></tr>
		<tr><th><label for="password2"><bean:message key="password_confirm" /> *</label></th><td><input type="password" name="password2" size="25" id="password2" onBlur="checkpassword2()" tabindex="5" /> <input type="button" class="keybord_button" style="margin-left: -26px;" onclick="jrunKeyBoard('password2',this);" title="<bean:message key="keyboard_title"/>">&nbsp;<span id="checkpassword2">&nbsp;</span></td></tr>
		<tr><th><label for="email"><bean:message key="email" /> *</label></th><td><input type="text" name="email" size="25" id="email" onBlur="checkemail()" tabindex="6" /> <span id="checkemail"><a href="http://im.live.cn/" target="_blank"><bean:message key="register_email_recommend" /></a><c:if test="${regverify==1}">&nbsp; <bean:message key="register_email_comment" /></c:if> <c:choose><c:when test="${!empty accessemail}">&nbsp; <bean:message key="register_email_invalid" arg0="${accessemail}"  /></c:when><c:when test="${!empty censoremail}">&nbsp; <bean:message key="register_email_censor" arg0="${censoremail}" /></c:when></c:choose></span></td></tr>
		<c:if test="${regstatus >1}"><tr><th><label for="invitecode"><bean:message key="a_system_invite_code" /> ${regstatus==2?"*":""}</label></th><td><input type="text" name="invitecode" size="25" maxlength="16" value="${param.invitecode}" id="invitecode" onBlur="checkinvitecode()" tabindex="7" /> <span id="checkinvitecode"></span></td></tr></c:if>
		<c:if test="${fromuser!=null}"><tr><th><bean:message key="register_from" /></th><td><input type="text" name="fromuser" size="25" value="${fromuser}" disabled="disabled" tabindex="9" /></td></tr></c:if>
		<c:forEach items="${fields_required}" var="field">
			<tr>
				<th>${field.value.title}  *<c:if test="${empty field.value.description}"><br />${field.value.description}</c:if></th>
				<td>
					<c:choose><c:when test="${field.value.selective>0}">
						<select name="${field.key}new" tabindex="10">
							<option value="">- <bean:message key="select" /> -</option>
							<c:forTokens items="${field.value.choices}" delims="," var="choice"><c:set var="cho" value="${fn:split(choice,'=')}" scope="page"/><option value="${cho[0]}">${cho[1]}</option></c:forTokens>
						</select></c:when>
						<c:otherwise><input type="text" name="${field.key}new" size="25" value="" tabindex="10" /></c:otherwise>
					</c:choose>
					<c:if test="${field.value.unchangeable>0}">&nbsp;<bean:message key="memcp_profile_unchangeable_comment" /></c:if>
				</td>
			</tr>					
		</c:forEach>
		<c:if test="${regverify==2}"><tr><th><bean:message key="a_member_moderate_message" /> *</th><td><textarea rows="4" cols="30" name="regmessage" tabindex="11"></textarea></td></tr></c:if>
		<tr><th><label for="advshow"><bean:message key="register_advanced_options" /></label></th><td><label><input id="advshow" name="advshow" class="checkbox" ${advcheck} type="checkbox" value="1" onclick="showadv()" tabindex="12" /><bean:message key="register_advanced_options_comment" /></label></td></tr>
	</table>
	<table summary="<bean:message key="register" /> <bean:message key="register_advanced_options" />" cellspacing="0" cellpadding="0" id="adv" style="display: ${advdisplay};">
		<thead><tr><th><bean:message key="optional_info" /></th><td>&nbsp;</td></tr></thead>
		<tr><th><label for="questionid"><bean:message key="security_question" /></label></th><td><select id="questionid" name="questionid" tabindex="13"><option value="0"><bean:message key="security_question_0" /></option><option value="1"><bean:message key="security_question_1" /></option><option value="2"><bean:message key="security_question_2" /></option><option value="3"><bean:message key="security_question_3" /></option><option value="4"><bean:message key="security_question_4" /></option><option value="5"><bean:message key="security_question_5" /></option><option value="6"><bean:message key="security_question_6" /></option><option value="7"><bean:message key="security_question_7" /></option></select> <bean:message key="memcp_profile_security_comment" /></td></tr>
		<tr><th><label for="answer"><bean:message key="security_answer" /></label></th><td><input type="text" id="answer" name="answer" size="25" tabindex="14" /></td></tr>
		<c:forEach items="${fields_optional}" var="field">
			<tr>
				<th>${field.value.title}<br />${field.value.description}</th>
				<td>
					<c:choose><c:when test="${field.value.selective>0}">
						<select name="${field.key}new" tabindex="10">
							<option value="">- <bean:message key="select" /> -</option>
							<c:forTokens items="${field.value.choices}" delims="," var="choice"><c:set var="cho" value="${fn:split(choice,'=')}" scope="page"/><option value="${cho[0]}">${cho[1]}</option></c:forTokens>
						</select></c:when>
						<c:otherwise><input type="text" name="${field.key}new" size="25" value="" tabindex="10" /></c:otherwise>
					</c:choose>
					<c:if test="${field.value.unchangeable>0}">&nbsp;<bean:message key="memcp_profile_unchangeable_comment" /></c:if>
				</td>
			</tr>					
		</c:forEach>
		<c:if test="${groupinfo.allownickname>0}"><tr><th><bean:message key="nickname" /></th><td><input type="text" name="nickname" size="25" maxlength="30" tabindex="16" /></td></tr></c:if>
		<tr><th><bean:message key="gender" /></th><td><label><input type="radio" name="gender" value="1" tabindex="17" /> <bean:message key="a_member_edit_gender_male" /></label> <label><input type="radio" name="gender" value="2" tabindex="18" /> <bean:message key="a_member_edit_gender_female" /></label> <label><input type="radio" name="gender" value="0" tabindex="19"checked="checked"> <bean:message key="a_member_edit_gender_secret" /></label></td></tr>
		<tr><th><label for="bday"><bean:message key="birthday" /></label></th><td><input type="text" id="bday" name="bday" size="25" onclick="showcalendar(event, this)" onfocus="showcalendar(event, this);if(this.value=='0000-00-00')this.value=''" value="0000-00-00" tabindex="20" /></td></tr>
		<tr><th><label for="loaction"><bean:message key="location" /></label></th><td><input type="text" id="loaction" name="location" size="25" tabindex="21" /></td></tr>
		<tr><th><label for="site"><bean:message key="homepage" /></label></th><td><input type="text" id="site" name="site" size="25" tabindex="22" /></td></tr>
		<tr><th><label for="qq"><bean:message key="qq" /></label></th><td><input type="text" id="qq" name="qq" size="25" tabindex="23" /></td></tr>
		<tr><th><label for="msn"><bean:message key="msn" /></label></th><td><input type="text" name="msn" size="25" tabindex="8" /><span id="checkmsn"><a href="http://im.live.cn/" target="_blank"><bean:message key="register_msn_download" /></a> </span></td></tr>
		<tr><th><label for="icq"><bean:message key="icq" /></label></th><td><input type="text" id="icq" name="icq" size="25" tabindex="24" /></td></tr>
		<tr><th><label for="yahoo"><bean:message key="yahoo" /></label></th><td><input type="text" id="yahoo" name="yahoo" size="25" tabindex="25" /></td></tr>
		<tr><th><label for="taobao"><bean:message key="taobao" /></label></th><td><input type="text" id="taobao" name="taobao" size="25" tabindex="26" /></td></tr>
		<tr><th><label for="alipay"><bean:message key="alipay" /></label></th><td><input type="text" id="alipay" name="alipay" size="25" tabindex="27" /></td></tr>
		<tr><th valign="top"><label for="bio"><bean:message key="bio" /></label></th><td><textarea rows="5" cols="30" id="bio" name="bio" tabindex="28"></textarea></td></tr>
		<thead><tr><th><bean:message key="board_options" /></th><td>&nbsp;</td></tr></thead>
		<tr><th><label for="styleid"><bean:message key="menu_style" /></label></th><td><select id="styleidnew" name="styleid" tabindex="29"><option value="0"><bean:message key="use_default" /></option><c:forEach items="${forumStyles}" var="style"><option value="${style.key}">${style.value}</option></c:forEach></select></td></tr>
		<tr><th><label for="tpp"><bean:message key="tpp" /></label></th><td><select id="tpp" name="tpp" tabindex="30"><option value="0"><bean:message key="use_default" /></option><option value="10">10</option><option value="20">20</option><option value="30">30</option></select></td></tr>
		<tr><th><label for="ppp"><bean:message key="ppp" /></label></th><td><select id="ppp" name="ppp" tabindex="31"><option value="0"><bean:message key="use_default" /></option><option value="5">5</option><option value="10">10</option><option value="15">15</option></select></td></tr>
		<tr>
			<th><label for="timeoffset"><bean:message key="timeoffset" /></label></th>
			<td><select id="timeoffset" name="timeoffset" tabindex="32">
				<option value="9999" selected="selected"><bean:message key="use_default" /></option>
				<c:forEach items="${timeZoneIDs}" var="timeZoneID">
     				<option value="${timeZoneID.key}">${timeZoneID.value[1]}</option>
     			</c:forEach>
			</select></td>
		</tr>
		<tr><th><label><bean:message key="timeformat" /></label></th><td><label><input type="radio" value="0" name="timeformat" tabindex="33" checked="checked" /><bean:message key="default" /></label> <label><input type="radio" value="1" name="timeformat" tabindex="34" /><bean:message key="a_member_edit_timeformat_12" /></label> <label><input type="radio" value="2" name="timeformat" tabindex="35" /><bean:message key="a_member_edit_timeformat_24" /></label></td></tr>
		<tr><th><label for="dateformat"><bean:message key="dateformat" /></label></th><td><select id="dateformat" name="dateformat" tabindex="36"><option value="0"><bean:message key="default" /></option><c:forEach items="${dateformats}" var="dateformat" varStatus="index"><option value="${index.count}">${dateformat}</option></c:forEach></select></td></tr>
		<tr><th><bean:message key="pmsound" /></th><td><label><input type="radio" value="0" name="pmsound" /><bean:message key="none" /></label> <input type="radio" value="1" name="pmsound" tabindex="37" checked/><a href="images/sound/pm_1.wav" />#1 </a> <input type="radio" value="2" name="pmsound" tabindex="38"><a href="images/sound/pm_2.wav" />#2 </a> <input type="radio" value="3" name="pmsound" tabindex="39"><a href="images/sound/pm_3.wav" />#3 </a></td></tr>
		<c:if test="${groupinfo.allowcstatus>0}"><tr><th><bean:message key="custom_status" /></th><td><input type="text" name="customstatus" size="25" maxlength="30" tabindex="40" /></td></tr></c:if>
		<tr><th><bean:message key="other_options" /></th><td><c:if test="${groupinfo.allowinvisible==1}"><input type="checkbox" name="invisible" value="1" tabindex="41" /> <bean:message key="online_invisible" /><br /></c:if><input type="checkbox" name="showemail" value="1" tabindex="42" checked="checked" />	<bean:message key="show_email_addr" /> <br /><input type="checkbox" name="newsletter" value="1" tabindex="43" checked="checked" /> <bean:message key="allow_newsletter" /><br /></td></tr>
		<c:choose>
			<c:when test="${groupinfo.allowavatar==1}"><tr><th><bean:message key="avatar" /></th><td><span id="avatarpreview"></span> <input type="text" name="urlavatar" id="urlavatar" size="25" tabindex="44" /><a href="member.do?action=viewavatars&page=1" onclick="ajaxget(this.href, 'avatardiv');doane(event);"><bean:message key="memcp_avatar_list" /></a> <div id="avatardiv" style="display: none; margin-top: 10px;"></div></td></tr></c:when>
			<c:when test="${groupinfo.allowavatar==2}"><tr><th><bean:message key="avatar" /></th><td><span id="avatarpreview"></span> <input type="text" name="urlavatar" id="urlavatar" size="25" tabindex="44" /> <a href="member.do?action=viewavatars&page=1" onclick="ajaxget(this.href, 'avatardiv');doane(event);"><bean:message key="memcp_avatar_list" /></a> <div id="avatardiv" style="display: none; margin-top: 10px;"></div><br /><bean:message key="width" />: <input type="text" name="avatarwidthnew" size="1" value="*" /> &nbsp; <bean:message key="high" />: <input type="text" name="avatarheightnew" size="1" value="*" /></td></tr></c:when>
		</c:choose>
		<c:if test="${groupinfo.maxsigsize>0}"><tr><th><bean:message key="signature" />(${groupinfo.maxsigsize} <bean:message key="bytes_limited" />)	<br /><br /><a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank">${faqs.JspRuncode.keyword}</a> <c:choose><c:when test="${groupinfo.allowsigbbcode>0}"><bean:message key="available" /></c:when><c:otherwise><bean:message key="disable" /></c:otherwise></c:choose><br />[img] <bean:message key="a_other_adv_style_code" /><c:choose><c:when test="${groupinfo.allowsigimgcode>0}"><bean:message key="available" /></c:when><c:otherwise><bean:message key="disable" /></c:otherwise></c:choose></th><td><textarea rows="4" cols="30" name="signature" tabindex="45"></textarea></td></tr></c:if>
	</table>
	<table summary="Submit Button" cellpadding="0" cellspacing="0"><tr><th>&nbsp;</th><td><button class="submit" type="submit" name="regsubmit" value="true" tabindex="100"><bean:message key="submitf" /></button></td></tr></table>
</div>
</form>
<script type="text/javascript">
var profile_seccode_invalid = '<bean:message key="register_profile_seccode_invalid" />';
var profile_secanswer_invalid = '<bean:message key="register_profile_secqaa_invalid" />';
var profile_username_toolong = '<bean:message key="register_profile_username_toolong" />';
var profile_username_tooshort = '<bean:message key="register_profile_profile_username_tooshort" />';
var profile_username_illegal = '<bean:message key="register_profile_username_illegal" />';
var profile_passwd_illegal = '<bean:message key="register_profile_passwd_illegal" />';
var profile_passwd_notmatch = '<bean:message key="register_profile_passwd_notmatch" />';
var profile_email_illegal = '<bean:message key="register_profile_email_illegal" />';
var profile_email_invalid = '<bean:message key="register_profile_email_invalid" arg0="${accessemail}" />';
var profile_email_censor = '<bean:message key="register_profile_email_censor" arg0="${censoremail}" />';
var profile_email_msn = '<a href="https://accountservices.passport.net/ppnetworkhome.srf?vv=600&lc=2052" target="_blank"><bean:message key="register_profile_email_msn" /></a>';
var doublee = parseInt('1');
var lastseccode = lastsecanswer = lastusername = lastpassword = lastemail = lastinvitecode = '';
var xml_http_building_link = '<bean:message key="xml_http_building_link" />';
var xml_http_sending = '<bean:message key="xml_http_sending" />';
var xml_http_loading = '<bean:message key="xml_http_loading" />';
var xml_http_load_failed = '<bean:message key="xml_http_load_failed" />';
var xml_http_data_in_processed = '<bean:message key="xml_http_data_in_processed" />';
$('username').focus();
function showAvatar(page) {
	var x = new Ajax('XML', 'statusid');
	x.get('member.jsp?action=viewavatars&page='+page, function(s){
		$("avatardiv").innerHTML = s;
		if($('multipage')) {
			var multiChildNodes = $('multipage').firstChild.childNodes;
			for(k in multiChildNodes) {
				if(multiChildNodes[k].href) {
					var r = multiChildNodes[k].href.match(/page=(\d*)/);
					var currpage = parseInt(r[1]);
	 				if(multiChildNodes) {
						multiChildNodes[k].href = isNaN(currpage) ? '' : 'javascript:showAvatar("'+currpage+'")';
					}
				}
			}
		}
	});
}
function checkseccode(){
	var seccodeverify = $('seccodeverify').value;
	if(seccodeverify == lastseccode){
		return;
	}else {
		lastseccode = seccodeverify;
	}
	var cs = $('checkseccodeverify');
	if(${seccodedata['type']!='1'}){
		if(!(/[0-9A-Za-z]{4}/.test(seccodeverify))){
			warning(cs, profile_seccode_invalid);
			return;
		}
	}
	else{
		if(seccodeverify.length != 2){
			warning(cs, profile_seccode_invalid);
			return;
		}
	}
	ajaxresponse('checkseccodeverify', 'action=checkseccode&seccodeverify=' + encodeURI(encodeURI(seccodeverify)));
}
function checksecanswer() {
    var secanswer = $('secanswer').value;
	if(secanswer == lastsecanswer) {
		return;
	} else {
		lastsecanswer = secanswer;
	}
	ajaxresponse('checksecanswer', 'action=checksecanswer&secanswer=' + encodeURI(encodeURI(secanswer)));
}
function checkusername() {
	var username = trim($('username').value);
	if(username == lastusername) {
		return;
	} else {
		lastusername = username;
	}
	var cu = $('checkusername');
	var unlen = username.replace(/[^\x00-\xff]/g, "**").length;
	if(unlen < 3 || unlen > 15) {
		warning(cu, unlen < 3 ? profile_username_tooshort : profile_username_toolong);
		return;
	}
    ajaxresponse('checkusername', 'action=checkusername&username=' + encodeURI(encodeURI(username)));
}
function checkpassword(confirm) {
	var password = $('password').value;
	if(!confirm && password == lastpassword) {
		return;
	} else {
		lastpassword = password;
	}
	var cp = $('checkpassword');
	if(password == '' || /[\'\"\\]/.test(password)) {
		warning(cp, profile_passwd_illegal);
		return false;
	} else {
		cp.style.display = 'none';
		if(!confirm) {
			checkpassword2(true);
		}
		return true;
	}
}
function checkpassword2(confirm) {
	var password = $('password').value;
	var password2 = $('password2').value;
	var cp2 = $('checkpassword2');
	if(password2 != '') {
		checkpassword(true);
	}
	if(password == '' || (confirm && password2 == '')) {
		cp2.style.display = 'none';
		return;
	}
	if(password != password2) {
		warning(cp2, profile_passwd_notmatch);
	} else {
		cp2.style.display = 'none';
	}
}
function checkemail() {
	var email = trim($('email').value);
	if(email == lastemail) {
		return;
	} else {
		lastemail = email;
	}
	var ce = $('checkemail');
	var accessemail = '';
	var censoremail = '';
	var accessexp = accessemail != '' ? /()$/i : null;
	var censorexp = censoremail != '' ? /()$/i : null;
	illegalemail = !(/^[\-\.\w]+@[\.\-\w]+(\.\w+)+$/.test(email));
	invalidemail = accessemail != '' ? !accessexp.test(email) : censoremail != '' && censorexp.test(email);
	if(illegalemail || invalidemail) {
		warning(ce, illegalemail ? profile_email_illegal : (accessemail != '' ? profile_email_invalid : profile_email_censor));
		return;
	}
	if(!(/@(msn|hotmail|live)\.com$/.test(email))) {
		$('checkemail').style.display = '';
		$('checkemail').innerHTML = ' &nbsp; ' + profile_email_msn;
		return;
	}
	if(${doublee!=""&&doublee!="0"}) {
		ajaxresponse('checkemail', 'action=checkemail&email=' + email);
	} else {
		ce.innerHTML = '<img src="${styles.IMGDIR}/check_right.gif" width="13" height="13">';
	}
}
function checkinvitecode() {
	var invitecode = trim($('invitecode').value);
	if(invitecode == lastinvitecode) {
		return;
	} else {
		lastinvitecode = invitecode;
	}
    ajaxresponse('checkinvitecode', 'action=checkinvitecode&invitecode=' + invitecode);
}
function trim(str) {
	return str.replace(/^\s*(.*?)[\s\n]*$/g, '$1');
}
function ajaxresponse(objname, data) {
	var x = new Ajax('HTML', objname);
    x.get('ajax.do?inajax=1&' + data, function(s){
    	var obj = $(objname);
    	if(s == 'succeed') {
    	   	obj.style.display = '';
    	    obj.innerHTML = '<img src="${styles.IMGDIR}/check_right.gif" width="13" height="13">';
    		obj.className = "warning";
    	} else {
    		warning(obj, s);
    	}
   });
}
function warning(obj, msg) {
	if((ton = obj.id.substr(5, obj.id.length)) != 'password2') {
		$(ton).select();
	}
	obj.style.display = '';
	obj.innerHTML = '<img src="${styles.IMGDIR}/check_error.gif" width="13" height="13"> &nbsp; ' + msg;
	obj.className = "warning";
}
</script>
</c:otherwise></c:choose>
<jsp:include flush="true" page="footer.jsp" />