<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_ban"/></td></tr>
</table><br />
			<form method="post" action="admincp.jsp?action=editbanmember">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<a name="ae0417b11045b751"></a>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="tableborder">
					<tr class="header">
						<td colspan="2">
							<bean:message key="menu_member_ban"/><c:if test="${usermap!=null}">-${usermap.username}</c:if>
							<a href="###" onclick="collapse_change('ae0417b11045b751')"><img
									id="menuimg_ae0417b11045b751" src="images/admincp/menu_reduce.gif" border="0"
									style="float: right; *margin-top: -12px; margin-right: 8px;" />
							</a>
						</td>
					</tr>
					<tbody id="menu_ae0417b11045b751" style="display: yes">
						<tr>
							<td width="45%" class="altbg1">
								<b><bean:message key="a_member_edit_username"/></b>
								<br />
								<span class="smalltxt"><bean:message key="a_member_edit_username_comment"/></span>
							</td>
							<td class="altbg2">
								<input type="text" size="50" name="username" value="${usermap.username}">
							</td>
						</tr>
						<tr>
							<td width="45%" class="altbg1">
								<b><bean:message key="a_member_edit_ban"/></b>
								<br />
								<span class="smalltxt"><bean:message key="a_member_edit_ban_comment"/></span>
							</td>
							<td class="altbg2">
								<input type="radio" name="type" value="resume" class="radio" ${usermap.groupid!=4 && usermap.groupid!=5?'checked' : ''}>
								<bean:message key="a_member_edit_ban_none"/><c:if test="${usermap!=null &&usermap.groupid!=4 && usermap.groupid!=5}">(<bean:message key="a_member_edit_current_status"/>)</c:if>
								<br />
								<input type="radio" name="type" value="post" class="radio" ${usermap.groupid==4?'checked' : ''}>
								<bean:message key="usergroups_system_4"/><c:if test="${usermap.groupid==4}">(<bean:message key="a_member_edit_current_status"/>)</c:if>
								<br />
								<input type="radio" name="type" value="visit" class="radio" ${usermap.groupid==5?'checked' : ''}>
								<bean:message key="usergroups_system_5"/><c:if test="${usermap.groupid==5}">(<bean:message key="a_member_edit_current_status"/>)</c:if>
							</td>
						</tr>
						<tr>
							<td width="45%" class="altbg1">
								<b><bean:message key="a_member_edit_ban_validity"/></b>
								<br />
								<span class="smalltxt"><bean:message key="a_member_edit_ban_validity_comment"/></span>
							</td>
							<td class="altbg2">
								${banexpirynew}
							</td>
						</tr>
						<c:if test="${members.adminid==1||members.adminid==2}">
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="a_member_edit_ban_delpost"/></b>
									<br />
									<span class="smalltxt"><bean:message key="a_member_edit_ban_delpost_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="delpost" value="1">
									<bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="delpost" value="0" checked>
									<bean:message key="no"/>
								</td>
							</tr>
						</c:if>
						<tr>
							<td width="45%" class="altbg1" valign="top">
								<b><bean:message key="a_member_edit_ban_reason"/></b>
								<br />
								<span class="smalltxt"><bean:message key="a_member_edit_ban_reason_comment"/></span>
							</td>
							<td class="altbg2">
								<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('reason', 1)">
								<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('reason', 0)">
								<br />
								<textarea rows="6" name="reason" id="reason" cols="50"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
				<br />
				<center>
					<input class="button" type="submit" name="bansubmit" value="<bean:message key="submit"/>">
				</center>
			</form>
<jsp:directive.include file="../cp_footer.jsp" />
