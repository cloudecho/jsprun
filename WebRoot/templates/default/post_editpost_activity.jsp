<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> ${navigation} &raquo; <bean:message key="post_editpost_activity"/>
</div>
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
var attachments = new Array();
var bbinsert = parseInt('${settings.bbinsert}');
var attachimgurl = new Array();
var isfirstpost = parseInt('${isfirstpost?1:0}');
var special = parseInt('${special}');
var allowposttrade = parseInt('${usergroups.allowposttrade}');
var allowpostreward = parseInt('${usergroups.allowpostreward}');
var allowpostactivity = parseInt('${usergroups.allowpostactivity}');
lang['board_allowed'] = '<bean:message key="board_allowed"/>';
lang['lento'] = '<bean:message key="lento"/>';
lang['bytes'] = '<bean:message key="bytes"/>';
lang['post_curlength'] = '<bean:message key="post_curlength"/>';
lang['post_subject_and_message_isnull'] = '<bean:message key="post_subject_and_message_isnull"/>';
lang['post_subject_toolong'] = '<bean:message key="post_subject_toolong"/>';
lang['post_message_length_invalid'] = '<bean:message key="post_message_length_invalid"/>';
lang['post_activity_sort_null'] = '<bean:message key="post_activity_sort_null"/>';
lang['post_activity_fromtime_null'] = '<bean:message key="post_activity_fromtime_null"/>';
lang['post_activity_addr_null'] = '<bean:message key="post_activity_addr_null"/>';
</script>
<jsp:include flush="true" page="post_preview.jsp" />
<form method="post" id="postform" action="post.jsp?action=edit&amp;extra=${extra}&amp;editsubmit=yes&formHash=${jrun:formHash(pageContext.request)}" onSubmit="return validate(this)" enctype="multipart/form-data">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="page" value="${param.page}">
	<input type="hidden" name="isfirst" value="true">
	<input type="hidden" name="special" value="4">
	<div class="mainbox formbox">
		<h1><bean:message key="post_editpost_activity"/></h1>
		<table summary="" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th><bean:message key="username"/></th>
					<td>
						<c:choose>
							<c:when test="${jsprun_uid>0}">${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</c:when>
							<c:otherwise><bean:message key="guest"/> [<a href="logging.jsp?action=login"><bean:message key="member_login"/></a>]</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</thead>
			<thead>
				<tr>
					<th><bean:message key="activity_info"/></th>
					<td>&nbsp;</td>
				</tr>
			</thead>
			<tr>
				<th><label for="subject"><bean:message key="activity_name"/></label></th>
				<td>
					<c:if test="${threadtypes.types!=null}">${typeselect}</c:if>
					<input type="text" name="subject" id="subject" size="45" value="${post.subject}" tabindex="3" />
					<input type="hidden" name="origsubject" value="${post.subject}" />
					<input type="hidden" name="activity" value="yes" />
				</td>
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
					<input type="text" size="45" id="activityclass" name="activityclass" value="${activity.class}" tabindex="4" />
				</td>
			</tr>
			<tr>
				<th><bean:message key="activity_starttime"/></th>
				<td>
					<p>
						<label><input class="radio" type="radio" value="0" name="activitytime" onclick="$('certainstarttime').style.display='';$('uncertainstarttime').style.display='none';" tabindex="5" ${activity.starttimeto==0?"checked":""}/><bean:message key="activity_specific_time"/></label>
						<label><input class="radio" type="radio" value="1" name="activitytime" onclick="$('certainstarttime').style.display='none';$('uncertainstarttime').style.display=''" tabindex="5" ${activity.starttimeto!=0?"checked":""}/><bean:message key="activity_bound_time"/></label>
					</p>
					<span id="certainstarttime" style="display: ${activity.starttimeto!=0?'none':''}"><input onclick="showcalendar(event, this, true)" type="text" name="starttimefrom[0]" id="starttimefrom_0" size="15" value="<jrun:showTime timeInt="${activity.starttimefrom}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>" tabindex="5" /> </span>
					<span id="uncertainstarttime" style="display: ${activity.starttimeto==0?'none':''}"><input onclick="showcalendar(event, this, true)" type="text" name="starttimefrom[1]" id="starttimefrom_1" size="20" value="<jrun:showTime timeInt="${activity.starttimefrom}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>" tabindex="5" /> - 
					<c:choose><c:when test="${activity.starttimeto>0}"><input onclick="showcalendar(event, this, true)" type="text" name="starttimeto" size="20" value="<jrun:showTime timeInt="${activity.starttimeto}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>" tabindex="5" /></c:when><c:otherwise><input onclick="showcalendar(event, this, true)" type="text" name="starttimeto" size="20" value="" tabindex="5" /></c:otherwise></c:choose> </span>
					<em class="tips"><bean:message key="sample"/>:<jrun:showTime timeInt="${timestamp+86400}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></em>
				</td>
			</tr>
			<tr>
				<th><label for="activityplace"><bean:message key="activity_space"/></label></th>
				<td><input type="text" id="activityplace" name="activityplace" size="45" value="${activity.place}" tabindex="6" /></td>
			</tr>
			<tr>
				<th><label for="cost"><bean:message key="activity_payment"/></label></th>
				<td>
					<input onkeyup="checkvalue(this.value, 'costmessage')" type="text" id="cost" name="cost" size="8" value="${activity.cost}" tabindex="7" /><bean:message key="rmb_yuan"/>
					<span id="costmessage"></span>
					<script type="text/javascript">
					function checkvalue(value, message){
						if(!value.search(/^\d+$/)) {
							$(message).innerHTML = '';
						} else {
							$(message).innerHTML = '<b><bean:message key="input_invalid"/></b>';
						}
					}
				</script>
				</td>
			</tr>
			<tr><jsp:include flush="true" page="post_editor.jsp" /></tr>
			<c:if test="${settings.tagstatus>0}">
				<tr>
					<th><label for="tags"><bean:message key="post_tag"/></label></th>
					<td>
						<input size="45" type="text" id="tags" name="tags" value="${tags}" tabindex="200" />&nbsp;
						<span id="tagselect"></span><em class="tips"><bean:message key="tag_comment"/></em>
					</td>
				</tr>
			</c:if>
			<thead>
				<tr>
					<th><bean:message key="activity_info_ext"/></th>
					<td>&nbsp;</td>
				</tr>
			</thead>
			<tr>
				<th><bean:message key="activity_need_member"/>(<bean:message key="optional_info"/>)</th>
				<td>
					<input onkeyup="checkvalue(this.value, 'activitynumbermessage')" type="text" name="activitynumber" size="45" value="${activity.number}" tabindex="201">
					<span id="activitynumbermessage"></span>
				</td>
			</tr>
			<tr>
				<th><bean:message key="gender"/></th>
				<td>
					<label><input class="radio" type="radio" name="gender" value="0" tabindex="202" ${activity.gender==0?"checked":""}><bean:message key="unlimite"/></label>
					<label><input class="radio" type="radio" name="gender" value="1" tabindex="203" ${activity.gender==1?"checked":""}><bean:message key="a_member_edit_gender_male"/></label>
					<label><input class="radio" type="radio" name="gender" value="2" tabindex="204" ${activity.gender==2?"checked":""}><bean:message key="a_member_edit_gender_female"/></label>
				</td>
			</tr>
			<tr>
				<th><bean:message key="activity_totime"/>(<bean:message key="optional_info"/>)<br/><bean:message key="post_zero_is_nopermission"/></th>
				<td>
					<c:choose>
						<c:when test="${activity.expiration>0}"><input onclick="showcalendar(event, this, true)" type="text" name="activityexpiration" size="45" value="<jrun:showTime timeInt="${activity.expiration}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>" tabindex="205" /><bean:message key="sample"/>:<jrun:showTime timeInt="${timestamp+86400}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></c:when>
						<c:otherwise><input onclick="showcalendar(event, this, true)" type="text" name="activityexpiration" size="45" value="" tabindex="205" /><bean:message key="sample"/>:<jrun:showTime timeInt="${timestamp+86400}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></c:otherwise>
					</c:choose>
				</td>
			</tr>
			<thead>
				<tr>
					<th><bean:message key="magics_information"/></th>
					<td>&nbsp;</td>
				</tr>
			</thead>
			<c:if test="${usergroups.allowsetreadperm>0}">
				<tr>
					<th><bean:message key="readperm_thread"/></th>
					<td><input type="text" name="readperm" size="6" value="${thread.readperm}" tabindex="206" /> <span class="smalltxt">(<bean:message key="post_zero_is_nopermission"/>)</span></td>
				</tr>
			</c:if>
			<tr class="btns">
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="wysiwyg" id="${editorid}_mode" value="${editormode }" />
					<input type="hidden" name="fid" id="fid" value="${post.fid}">
					<input type="hidden" name="tid" value="${post.tid}">
					<input type="hidden" name="pid" value="${post.pid}">
					<button type="submit" class="submit" name="editsubmit" id="postsubmit" tabindex="300"><bean:message key="post_editpost_activity"/></button>
					<em><bean:message key="post_submit_hotkey"/></em>&nbsp;&nbsp; &nbsp;<a href="###" id="restoredata" onclick="loadData()" title="<bean:message key="post_autosave_last_restore"/>"><bean:message key="post_autosave_restore"/></a>
				</td>
			</tr>
		</table>
	</div>
<jsp:include flush="true" page="post_editpost_attachlist.jsp"/>
</form>
<jsp:include flush="true" page="post_js.jsp" />
<script type="text/javascript">
	if(${settings.bbinsert>0})
	{
		if(${editormode>0}){
			if(!(is_opera && is_opera < 9)) {textobj.value = bbcode2html(textobj.value);}
		}
		newEditor(wysiwyg);
	}
	checkFocus();
	setCaretAtEnd();
	if(!(is_ie >= 5 || is_moz >= 2)) {
		$('restoredata').style.display = 'none';
	}
</script>
<jsp:include flush="true" page="footer.jsp" />