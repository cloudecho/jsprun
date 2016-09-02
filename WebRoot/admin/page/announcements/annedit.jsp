<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
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
<script type="text/javascript" src="include/javascript/calendar.js"></script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_announce"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=annedit&annid=${announcement.id}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_other_ann_edit"/></td></tr>
		<tr>
			<td width="21%" class="altbg1"><b><bean:message key="subject"/>:</b></td>
			<td width="79%" class="altbg2"><input type="text" size="45" name="newsubject" value="${announcement.subject }" maxlength="250"></td>
		</tr>
		<tr>
			<td width="21%" class="altbg1">
				<b><bean:message key="start_time"/>:</b>
				<br />
				<bean:message key="a_other_ann_time_comment"/>
			</td>
			<td width="79%" class="altbg2"><input type="text" size="45" name="newstarttime" value="${announcement.starttime}" onclick="showcalendar(event, this)"></td>
		</tr>
		<tr>
			<td width="21%" class="altbg1">
				<b><bean:message key="end_time"/>:</b>
				<br />
				<bean:message key="a_other_ann_time_comment"/>
			</td>
			<td width="79%" class="altbg2"><input type="text" size="45" name="newendtime" value="${announcement.endtime}" onclick="showcalendar(event, this)"> <bean:message key="a_other_ann_end_time_comment"/></td>
		</tr>
		<tr>
			<td width="40%" class="altbg1" valign="top"><b><bean:message key="a_other_ann_type"/></b></td>
			<td width="45%" class="altbg2">
				<input name="newtype" class="radio" type="radio" value="0" ${announcement.type == 0?"checked":""}> <bean:message key="a_other_ann_words"/>&nbsp;
				<input name="newtype" class="radio" type="radio" value="1" ${announcement.type == 1?"checked":""}> <bean:message key="a_other_ann_url"/>&nbsp;
				<input name="newtype" class="radio" type="radio" value="2" ${announcement.type == 2?"checked":""}> <bean:message key="a_other_ann_pms"/>
			</td>
		</tr>
		<tr>
			<td width="40%" class="altbg1" valign="top">
				<b><bean:message key="menu_member_usergroups"/>:</b>
				<br />
				<bean:message key="a_other_ann_usergroup_comment"/>
			</td>
			<td width="45%" class="altbg2">
				<select name="usergroupid" size="5" multiple="multiple" style="width: 65%">
					<option value='' ${announcement.groups==''?"selected":""} ><bean:message key="all"/></option>
					<c:forEach items="${usergroups}" var="usergroup">
						<option value="${usergroup.groupid}" ${groupSelect[usergroup.groupid]}>${usergroup.grouptitle}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td width="21%" class="altbg1" valign="top">
				<b><bean:message key="message"/>:</b><br />
				<bean:message key="a_other_ann_message_comment"/>
			</td>
			<td width="79%" class="altbg2"><textarea name="newmessage" cols="60" rows="10">${announcement.message }</textarea></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />