<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_announce"/></td></tr>
</table>
<br />
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td><ul><li><bean:message key="a_other_ann_tips"/></li></ul></td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=announcements">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="8"><bean:message key="a_other_ann_edit"/></td></tr>
		<tr align="center" class="category">
			<td width="48"><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form)"><bean:message key="del"/></td>
			<td><bean:message key="author"/></td>
			<td><bean:message key="subject"/></td>
			<td><bean:message key="message"/></td>
			<td><bean:message key="a_other_ann_type"/></td>
			<td><bean:message key="start_time"/></td>
			<td><bean:message key="end_time"/></td>
			<td><bean:message key="display_order"/></td>
		</tr>
	<c:forEach items="${announcements}" var="announcement">
		<input type="hidden" name="annid" value="${announcement.id}">
		<tr align="center">
			<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" value="${announcement.id }" ${jsprun_adminid!=1&&announcement.author!= jsprun_userss?"disabled":""}></td>
			<td class="altbg2"><a href="space.jsp?action=viewpro&username=<jrun:encoding value="${announcement.author}"/>" target="_blank">${announcement.author}</a></td>
			<td class="altbg1"><a href="admincp.jsp?action=annedit&annid=${announcement.id}">${announcement.subject}</a></td>
			<td class="altbg2"><a href="admincp.jsp?action=annedit&annid=${announcement.id}">${announcement.message}</a></td>
			<td class="altbg1">
				<c:choose>
					<c:when test="${announcement.type==0}"><bean:message key="a_other_ann_words"/></c:when>
					<c:when test="${announcement.type==1}"><bean:message key="a_other_ann_url"/></c:when>
					<c:otherwise><bean:message key="a_other_ann_pms"/></c:otherwise>
				</c:choose>
			</td>
			<td class="altbg2">${announcement.starttime}</td>
			<td class="altbg1">${announcement.endtime}</td>
			<td class="altbg2"><input type="text" size="2" name="newdisplayorder" value="${announcement.displayorder}"></td>
		</tr>
		</c:forEach>
	</table>
	<br />
	<center><input class="button" type="submit" name="announcesubmit" value="<bean:message key="submit"/>"></center>
</form>
<br />
<form method="post" action="admincp.jsp?action=announcements">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_other_ann_add"/></td></tr>
		<tr>
			<td width="40%" class="altbg1"><b><bean:message key="subject"/>:</b></td>
			<td width="45%" class="altbg2"><input type="text" size="45" name="subject" maxlength="250"></td>
		</tr>
		<tr>
			<td width="40%" class="altbg1"><b><bean:message key="start_time"/>:</b><br /><bean:message key="a_other_ann_time_comment"/></td>
			<td width="45%" class="altbg2"><input type="text" size="45" name="starttime" value="${time}" onclick="showcalendar(event, this)"></td>
		</tr>
		<tr>
			<td width="40%" class="altbg1"><b><bean:message key="end_time"/></b><br /><bean:message key="a_other_ann_time_comment"/></td>
			<td width="45%" class="altbg2"><input type="text" size="45" name="endtime" onclick="showcalendar(event, this)"> <bean:message key="a_other_ann_end_time_comment"/></td>
		</tr>
		<tr>
			<td width="40%" class="altbg1" valign="top"><b><bean:message key="a_other_ann_type"/>:</b></td>
			<td width="45%" class="altbg2">
				<input name="type" class="radio" type="radio" value="0" checked> <bean:message key="a_other_ann_words"/>&nbsp;
				<input name="type" class="radio" type="radio" value="1"> <bean:message key="a_other_ann_url"/>&nbsp;
				<input name="type" class="radio" type="radio" value="2"> <bean:message key="a_other_ann_pms"/>
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
					<option value='' selected><bean:message key="all"/></option>
				<c:forEach items="${usergroups}" var="usergroup">
					<option value="${usergroup.groupid}">${usergroup.grouptitle}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td width="40%" class="altbg1" valign="top">
				<b><bean:message key="message"/>:</b><br />
				<bean:message key="a_other_ann_message_comment"/>
			</td>
			<td width="45%" class="altbg2"><textarea name="message" cols="60" rows="10"></textarea></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="addsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />