<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="usergroups_edit"/></td></tr>
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
<script src="include/javascript/calendar.js" type="text/javascript"></script>
<form method="post" action="admincp.jsp?action=editgroups&memberid=${member.uid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="usergroups_edit"/> - ${member.username}</td>
		</tr>
		<tr class="altbg1">
			<td>
				<table cellspacing="0" cellpadding="10" width="100%" align="center">
					<tr>
						<td width="35%" valign="top">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
								<tr class="header">
									<td><bean:message key="menu_member_usergroups"/></td>
									<td><bean:message key="a_member_edit_groups_related_adminid"/></td>
								</tr>
								<tr><td class="category" colspan="2"><span class="bold"><bean:message key="usergroups_system"/></span></td></tr>
								<c:forEach items="${systemgroups}" var="systemgroup" varStatus="index">
									<tr class="${index.count%2==0?'altbg1':'altbg2'}">
										<td><input class="radio" type="radio" name="groupidnew" value="${systemgroup.groupid}" ${systemgroup.groupid==member.groupid?"checked":""}> ${systemgroup.grouptitle}</td>
										<td>${systemgroup.radminid!=0 ? systemgroup.grouptitle : "<span class=warning>X</span>"}</td>
									</tr>
								</c:forEach>
								<tr><td class="category" colspan="2"><span class="bold"><bean:message key="usergroups_special"/></span></td></tr>
								<c:forEach items="${specialgroups}" var="specialgroup" varStatus="index">
									<tr class="${index.count%2==0?'altbg1':'altbg2'}">
										<td><input class="radio" type="radio" name="groupidnew" value="${specialgroup.groupid}" ${specialgroup.groupid==member.groupid?"checked":""}> ${specialgroup.grouptitle}</td>
										<td>
											<c:choose>
												<c:when test="${specialgroup.radminid>0}">
													<c:choose>
														<c:when test="${specialgroup.radminid==1}"><bean:message key="usergroups_system_1"/></c:when>
														<c:when test="${specialgroup.radminid==2}"><bean:message key="usergroups_system_2"/></c:when>
														<c:when test="${specialgroup.radminid==3}"><bean:message key="usergroups_system_3"/></c:when>
													</c:choose>
												</c:when>
												<c:otherwise>
													<select name="spefd">
														<option value="0" ${member.adminid==0?"selected":""}><bean:message key="usergroups_system_0"/></option>
														<option value="1" ${member.adminid==1?"selected":""}><bean:message key="usergroups_system_1"/></option>
														<option value="2" ${member.adminid==2?"selected":""}><bean:message key="usergroups_system_2"/></option>
														<option value="3" ${member.adminid==3?"selected":""}><bean:message key="usergroups_system_3"/></option>
													</select>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
								<tr><td class="category" colspan="2"><span class="bold"><bean:message key="usergroups_member"/></span></td></tr>
								<c:forEach items="${membergroups}" var="membergroup" varStatus="index">
									<tr class="${index.count%2==0?'altbg1':'altbg2'}">
										<td><input class="radio" type="radio" name="groupidnew" value="${membergroup.groupid}" ${membergroup.groupid==member.groupid?"checked":""}> ${membergroup.grouptitle}</td>
										<td><span class="warning">X</span></td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td width="65%" align="right" valign="top">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
								<tr class="header"><td colspan="4"><bean:message key="a_member_edit_groups_extended"/></td></tr>
								<tr align="center" class="category">
									<td width="30%"><bean:message key="menu_member_usergroups"/></td>
									<td width="20%"><bean:message key="validity"/></td>
									<td width="30%"><bean:message key="menu_member_usergroups"/></td>
									<td width="20%"><bean:message key="validity"/></td>
								</tr>
								<c:forEach items="${extusergroup}" var="extusermap" varStatus="cou">
									<c:set scope="page" var="num" value="${cou.count}"></c:set>
									<c:forEach items="${extusermap}" var="extuser">
										<c:if test="${cou.count%2!=0}"><tr></c:if>
										<c:choose>
											<c:when test="${extuser.value!=null}">
												<td class="altbg2"><input class="checkbox" type="checkbox" name="extgroupidsnew[${extuser.key.groupid}]" value="${extuser.key.groupid}" checked> ${extuser.key.grouptitle}</td>
												<c:choose>
													<c:when test="${extuser.value!='0'}"> <td align="center" class="altbg2"> <input type="text" size="9" name="extgroupexpirynew[${extuser.key.groupid}]" value="<jrun:showTime timeInt="${extuser.value}" type="${dateformat}" />" onclick="showcalendar(event,this)"></td></c:when>
													<c:otherwise><td align="center" class="altbg2"><input type="text" size="9" name="extgroupexpirynew[${extuser.key.groupid}]" value="" onclick="showcalendar(event, this)"></td></c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<td class="altbg2"><input class="checkbox" type="checkbox" name="extgroupidsnew[${extuser.key.groupid}]" value="${extuser.key.groupid}"> ${extuser.key.grouptitle}</td>
												<td align="center" class="altbg2"><input type="text" size="9" name="extgroupexpirynew[${extuser.key.groupid}]" value="" onclick="showcalendar(event, this)"></td>
											</c:otherwise>
										</c:choose>
										<c:if test="${cou.count%2==0}"></tr></c:if>
									</c:forEach>
								</c:forEach>
								<c:if test="${num%2!=0}"><td colspan="2" class="altbg2"></td></c:if>
								<tr><td colspan="4" class="altbg2"><bean:message key="a_member_edit_groups_extended_comment"/></td></tr>
							</table>
							<br />
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
								<tr class="header"><td colspan="2"><bean:message key="validity"/></td></tr>
								<tr>
									<td class="altbg1" width="45%">
										<b><bean:message key="a_member_edit_groups_validity"/></b>
										<br />
										<span class="smalltxt"><bean:message key="a_member_edit_groups_validity_comment"/></span>
									</td>
									<td class="altbg2" width="40%">
										<c:choose>
											<c:when test="${time!=null}">
												<input class="radio" type="radio" name="expirytype" value="date" checked>
												<input type="text" name="expirydatenew" value="<jrun:showTime timeInt="${time}" type="${dateformat}" timeoffset="${timeoffset}"/>" size="15"> <bean:message key="a_member_edit_groups_validity_date"/><br />
												<input class="radio" type="radio" name="expirytype" value="days">
												<input type="text" name="expirydaysnew" value="${day}" size="15"> <bean:message key="a_member_edit_groups_validity_days"/><br />
											</c:when>
											<c:otherwise>
												<input class="radio" type="radio" name="expirytype" value="date" checked>
												<input type="text" name="expirydatenew" value="" size="15"> <bean:message key="a_member_edit_groups_validity_date"/><br />
												<input class="radio" type="radio" name="expirytype" value="days">
												<input type="text" name="expirydaysnew" value="" size="15"> <bean:message key="a_member_edit_groups_validity_days"/><br />
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td class="altbg1" width="45%"><b><bean:message key="a_member_edit_groups_orig_groupid"/></b></td>
									<td class="altbg2" width="40%">
										<select name="expadminidnew">
											<c:choose>
												<c:when test="${adminid!=null||time!=null}">
													<option value="0" ${adminid==0||adminid==null?"selected":""}><bean:message key="usergroups_system_0"/></option>
													<option value="1" ${adminid==1?"selected":""}><bean:message key="usergroups_system_1"/></option>
													<option value="2" ${adminid==2?"selected":""}><bean:message key="usergroups_system_2"/></option>
													<option value="3" ${adminid==3?"selected":""}><bean:message key="usergroups_system_3"/></option>
												</c:when>
												<c:otherwise>
													<option value="0" ${member.adminid==0?"selected":""}><bean:message key="usergroups_system_0"/></option>
													<option value="1" ${member.adminid==1?"selected":""}><bean:message key="usergroups_system_1"/></option>
													<option value="2" ${member.adminid==2?"selected":""}><bean:message key="usergroups_system_2"/></option>
													<option value="3" ${member.adminid==3?"selected":""}><bean:message key="usergroups_system_3"/></option>
												</c:otherwise>
											</c:choose>
										</select>
									</td>
								</tr>
								<tr>
									<td class="altbg1" width="45%"><b><bean:message key="a_member_edit_groups_orig_adminid"/></b></td>
									<td class="altbg2" width="40%">
										<select name="expgroupidnew">
											<option name="expgroupidnew" value="0"><bean:message key="usergroups_system_0"/></option>
											<c:choose>
												<c:when test="${groupid!=null}">
													<c:forEach items="${usergroups}" var="usergroup">
														<option name="expgroupidnew" value="${usergroup.groupid}" ${groupid==usergroup.groupid?"selected":""}>${usergroup.grouptitle}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach items="${usergroups}" var="usergroup">
														<option name="expgroupidnew" value="${usergroup.groupid}" ${member.groupid==usergroup.groupid && day==null?"selected":""}>${usergroup.grouptitle}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</td>
								</tr>
							</table>
							<br />
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
								<tr class="header"><td colspan="2"><bean:message key="a_member_edit_reason"/></td></tr>
								<tr>
									<td class="altbg1" width="45%">
										<b><bean:message key="a_member_edit_groups_ban_reason"/></b>
										<br />
										<span class="smalltxt"><bean:message key="a_member_edit_groups_ban_reason_comment"/></span>
									</td>
									<td class="altbg2" width="40%"><textarea name="reason" rows="5" cols="30"></textarea></td>
								</tr>
							</table>
							<br />
							<br />
							<center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>"></center>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
<jsp:directive.include file="../cp_footer.jsp" />