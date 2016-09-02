<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> ${navigation} &raquo; <bean:message key="post_newthread_activity"/></div>
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
<script type="text/javascript">
var postminchars = parseInt('${settings.minpostsize}');
var postmaxchars = parseInt('${settings.maxpostsize}');
var disablepostctrl = parseInt('${usergroups.disablepostctrl}');
var typerequired = parseInt('${threadtypes.required}');
var bbinsert = parseInt('${settings.bbinsert}');
var seccodecheck = parseInt('${seccodecheck}');
var secqaacheck = parseInt('${secqaacheck}');
var special = 4;
var isfirstpost = 1;
var allowposttrade = parseInt('${allowposttrade?1:0}');
var allowpostreward = parseInt('${allowpostreward?1:0}');
var allowpostactivity = parseInt('${allowpostactivity?1:0}');
lang['board_allowed'] = '<bean:message key="board_allowed"/>';
lang['lento'] = '<bean:message key="lento"/>';
lang['bytes'] = '<bean:message key="bytes"/>';
lang['post_curlength'] = '<bean:message key="post_curlength"/>';
lang['post_subject_and_message_isnull'] = '<bean:message key="post_subject_and_message_isnull"/>';
lang['post_subject_toolong'] = '<bean:message key="post_subject_toolong"/>';
lang['post_message_length_invalid'] = '<bean:message key="post_message_length_invalid"/>';
lang['post_type_isnull'] = '<bean:message key="post_type_isnull"/>';
lang['post_activity_sort_null'] = '<bean:message key="post_activity_sort_null"/>';
lang['post_activity_fromtime_null'] = '<bean:message key="post_activity_fromtime_null"/>';
lang['post_activity_addr_null'] = '<bean:message key="post_activity_addr_null"/>';
</script>
<jsp:include flush="true" page="post_preview.jsp" />
<form method="post" id="postform" action="post.jsp?action=newthread&fid=${fid}&page=${page}&topicsubmit=yes&formHash=${jrun:formHash(pageContext.request)}" enctype="multipart/form-data">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<input type="hidden" name="isblog" value="${isblog}" />
<input type="hidden" name="special" value="4" />
<div class="mainbox formbox">
	<span class="headactions"><a href="member.jsp?action=credits&view=forum_post&fid=${fid}" target="_blank"><bean:message key="credits_policy_view"/></a> </span>
	<h1><bean:message key="post_newthread_activity"/></h1>
	<table summary="<bean:message key="post_newthread_activity"/>" cellspacing="0" cellpadding="0">
		<thead><tr><th><bean:message key="username"/></th><td><c:choose><c:when test="${jsprun_uid>0}">${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</c:when><c:otherwise><bean:message key="guest"/> [<a href="logging.jsp?action=login"><bean:message key="member_login"/></a>]</c:otherwise></c:choose></td></tr></thead>
		<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode"/></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" tabindex="0" /> <em class="tips"><strong><bean:message key="seccode_click"/></strong> <bean:message key="seccode_refresh"/></em><script type="text/javascript">var seccodedata = [${seccodedata['width']}, ${seccodedata['height']}, ${seccodedata['type']}];</script></td></tr></c:if>
		<c:if test="${secqaacheck}"><tr><th><label for="secanswer"><bean:message key="secqaa"/></label></th><td><div id="secquestion"></div> <input type="text" name="secanswer" id="secanswer" size="25" maxlength="50" tabindex="1" /><script type="text/javascript">ajaxget('ajax.do?action=updatesecqaa', 'secquestion');</script></td></tr></c:if>
		<thead><tr><th><bean:message key="activity_info"/></th><td>&nbsp;</td></tr></thead>
		<tr>
			<th><label for="subject"><bean:message key="activity_name"/></label></th>
			<td>${typeselect } <input type="text" name="subject" id="subject" size="45" value="${subject}" tabindex="3" /></td>
		</tr>
		<tr>
			<th><label for="activityclass"><bean:message key="activiy_sort"/></label></th>
			<td>
				<c:if test="${activitytypelist!=null}">
					<select onchange="this.form.activityclass.value=this.value" tabindex="4">
						<option value=""></option>
						<c:forEach items="${activitytypelist}" var="type"><option value="${type}">${type}</option></c:forEach>
					</select>
				</c:if>
				<input type="text" size="29" maxlength="45" id="activityclass" name="activityclass" tabindex="4" />
			</td>
		</tr>
		<tr>
			<th><bean:message key="activity_starttime"/></th>
			<td>
				<p><label><input class="radio" type="radio" value="0" name="activitytime" onclick="$('certainstarttime').style.display='';$('uncertainstarttime').style.display='none';" checked="checked" tabindex="5" /><bean:message key="activity_specific_time"/></label> <label><input class="radio" type="radio" value="1" name="activitytime" onclick="$('certainstarttime').style.display='none';$('uncertainstarttime').style.display=''" tabindex="5" /><bean:message key="activity_bound_time"/></label></p>
				<span id="certainstarttime"><input onclick="showcalendar(event, this, true)" type="text" name="starttimefrom[0]" id="starttimefrom_0" size="15" value="" tabindex="5" /></span>
				<span id="uncertainstarttime" style="display: none"> <input onclick="showcalendar(event, this, true)" type="text" name="starttimefrom[1]" id="starttimefrom_1" size="20" value="" tabindex="5" /> - <input onclick="showcalendar(event, this, true)" type="text" name="starttimeto" size="20" value="" tabindex="5" /></span> <em class="tips"><bean:message key="sample"/>:${sampletimestamp}</em>
			</td>
		</tr>
		<tr>
			<th><label for="activityplace"><bean:message key="activity_space"/></label></th>
			<td><input type="text" size="45" name="activityplace" id="activityplace" tabindex="6" /></td>
		</tr>
		<tr>
			<th><label for="cost"><bean:message key="activity_payment_2"/></label></th>
			<td><input onkeyup="checkvalue(this.value, 'costmessage')" type="text" name="cost" id="cost" size="8" value="0" tabindex="7" /> <bean:message key="rmb_yuan"/> <span id="costmessage"></span></td>
		</tr>
		<tr><jsp:include flush="true" page="post_editor.jsp"/></tr>
		<c:if test="${settings.tagstatus>0}">
			<tr>
				<th><label for="tags"><bean:message key="post_tag"/></label></th>
				<td><input size="45" type="text" id="tags" name="tags" value="" tabindex="200" />&nbsp; <span id="tagselect"></span><em class="tips"> <bean:message key="tag_comment"/></em></td>
			</tr>
		</c:if>
		<thead><tr><td colspan="2"><bean:message key="activity_info_ext"/></td></tr></thead>
		<tr>
			<th><label for="activitycity"><bean:message key="activity_city"/>(<bean:message key="optional_info"/>)</label></th>
			<td><input type="text" size="45" maxlength="45" name="activitycity" id="activitycity" tabindex="201"></td>
		</tr>
		<tr>
			<th><label for="activitynumber"><bean:message key="activity_need_member"/>(<bean:message key="optional_info"/>)</label></th>
			<td><input onkeyup="checkvalue(this.value, 'activitynumbermessage')" type="text" name="activitynumber" id="activitynumber" size="45" value="" tabindex="202" /><span id="activitynumbermessage"></span></td>
		</tr>
		<tr>
			<th><bean:message key="gender"/></th>
			<td><label><input type="radio" name="gender" value="0" tabindex="203" checked /><bean:message key="unlimite"/></label> <label><input type="radio" name="gender" value="1" tabindex="204" /><bean:message key="a_member_edit_gender_male"/></label> <label><input type="radio" name="gender" value="2" tabindex="205" /><bean:message key="a_member_edit_gender_female"/></label></td>
		</tr>
		<tr>
			<th><label for="activityexpiration"><bean:message key="activity_totime"/>(<bean:message key="optional_info"/>)</label></th>
			<td><input onclick="showcalendar(event, this, true)" type="text" name="activityexpiration" id="activityexpiration" size="45" value="" tabindex="206" /> <em class="tips"><bean:message key="sample"/>:${sampletimestamp}</em></td>
		</tr>
		<thead><tr><td colspan="2"><bean:message key="magics_information"/><input id="advshow" type="checkbox" onclick="showadv()" tabindex="207" /></td></tr></thead>
		<tbody id="adv" style="display: none">
			<c:if test="${usergroups.allowsetreadperm>0}">
				<tr>
					<th><label for="readperm"><bean:message key="readperm_thread"/></label></th>
					<td><input type="text" name="readperm" id="readperm" size="6" value="${readperm}" tabindex="208" /> <em class="tips">(<bean:message key="post_zero_is_nopermission"/>)</em></td>
				</tr>
			</c:if>
		</tbody>
		<tr class="btns">
			<th>&nbsp;</th>
			<td>
				<input type="hidden" name="wysiwyg" id="${editorid}_mode" value="${editormode }" />
				<button type="submit" class="submit" name="topicsubmit" id="postsubmit" value="true" tabindex="300"><bean:message key="post_newthread_activity"/></button>
				<em><bean:message key="post_submit_hotkey"/></em>&nbsp;&nbsp; &nbsp;<a href="###" id="restoredata" onclick="loadData()" title="<bean:message key="post_autosave_last_restore"/>"><bean:message key="post_autosave_restore"/></a>
			</td>
		</tr>
	</table>
</div>
</form>
<jsp:include flush="true" page="post_js.jsp" />
<script type="text/javascript">
function showadv() {
	if($("advshow").checked == true) {
		$("adv").style.display = "";
	} else {
		$("adv").style.display = "none";
	}
}
function checkvalue(value, message){
	if(!value.search(/^\d+$/)) {
		$(message).innerHTML = '';
	} else {
		$(message).innerHTML = '<b><bean:message key="input_invalid"/></b>';
	}
}
</script>
<jsp:include flush="true" page="footer.jsp" />