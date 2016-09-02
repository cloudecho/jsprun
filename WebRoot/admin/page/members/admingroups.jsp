<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<c:if test="${edit!='yes'}">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_admingroups"/></td></tr>
</table>
		<br />
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
					<tr class="header">
						<td>
							<div style="float:left; margin-left:0px; padding-top:8px">
								<a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a>
							</div>
							<div style="float:right; margin-right:4px; padding-bottom:9px">
								<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a>
							</div>
						</td>
					</tr>
					<tbody id="menu_tip" style="display:">
						<tr>
							<td> <bean:message key="admingroups_tips"/> </td>
						</tr>
					</tbody>
				</table>
				<br />
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
					<tr class="header">
						<td>
							<bean:message key="name"/>
						</td>
						<td>
							<bean:message key="type"/>
						</td>
						<td>
							<bean:message key="admingroups_level"/>
						</td>
						<td>
							<bean:message key="header_basic"/>
						</td>
						<td>
							<bean:message key="admingroups_settings_admin"/>
						</td>
					</tr>
					<c:forEach items="${adminGroupList}" var="userGroup">
						<tr align="center">
							<td class="altbg1">
								${userGroup.grouptitle}
							</td>
							<td class="altbg2">
								<c:choose>
									<c:when test="${userGroup.type=='system'}"><bean:message key="admingroups_type_system"/></c:when>
									<c:otherwise><bean:message key="custom"/></c:otherwise>
								</c:choose>
							</td>
							<td class="altbg1">
								<c:if test="${userGroup.radminid==1}"><bean:message key="usergroups_system_1"/></c:if>
								<c:if test="${userGroup.radminid==2}"><bean:message key="usergroups_system_2"/></c:if>
								<c:if test="${userGroup.radminid==3}"><bean:message key="usergroups_system_3"/></c:if>
							</td>
							<td class="altbg2">
								<a href="admincp.jsp?action=tousergroupinfo&edit=${userGroup.groupid}">[<bean:message key="edit"/>]</a>
							</td>
							<td class="altbg1">
								<a href="admincp.jsp?action=admingroups&edit=${userGroup.groupid}">[<bean:message key="edit"/>]</a>
							</td>
						</tr>
					</c:forEach>
				</table>
</c:if>
<c:if test="${edit=='yes'}">
				<br />
				<br />
				<form method="post" action="admincp.jsp?action=admingroups&edit=${admingroups.admingid}">
					<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
					<input type="hidden" name="submit" value="yes">
					<a name="d2ca223eaff26e5d"></a>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
						<tr class="header">
							<td colspan="2">
								<bean:message key="admingroups_edit"/> - ${admingroups.grouptitle}
								<a href="###" onclick="collapse_change('d2ca223eaff26e5d')"><img id="menuimg_d2ca223eaff26e5d" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" />
								</a>
							</td>
						</tr>
						<tbody id="menu_d2ca223eaff26e5d" style="display: yes">
							<c:choose>
								<c:when test="${admingroups.radminid==1}">
									<c:forEach items="${actionarray}" var="action" varStatus="index">
										<tr>
										<td width="45%" class="altbg1">
											${actionarrayname[index.index]}
										</td>
										<td class="altbg2">
											<input class="radio" type="radio" name="disabledaction[${action}]" value="1" ${disabledactionsMap[action]==null?'checked':''}> <bean:message key="yes"/> &nbsp; &nbsp;
											<input class="radio" type="radio" name="disabledaction[${action}]" value="0" ${disabledactionsMap[action]!=null?'checked':''}><bean:message key="no"/>
										</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
							<tr>
								<td width="45%" class="altbg1">
								<b><bean:message key="admingroups_edit_edit_post"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_edit_post_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="alloweditpost" value="1" ${admingroups.alloweditpost==1?'checked':''}> <bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="alloweditpost" value="0" ${admingroups.alloweditpost==0?'checked':''}> <bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_edit_poll"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_edit_poll_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="alloweditpoll" value="1" ${admingroups.alloweditpoll==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="alloweditpoll" value="0" ${admingroups.alloweditpoll==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_stick_thread"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_stick_thread_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowstickthread" value="0" ${admingroups.allowstickthread==0?'checked' : ''}><bean:message key="admingroups_edit_stick_thread_none"/><br />
									<input class="radio" type="radio" name="allowstickthread" value="1" ${admingroups.allowstickthread==1?'checked' : ''}><bean:message key="admingroups_edit_stick_thread_1"/><br />
									<input class="radio" type="radio" name="allowstickthread" value="2" ${admingroups.allowstickthread==2?'checked' : ''}><bean:message key="admingroups_edit_stick_thread_2"/><br />
									<input class="radio" type="radio" name="allowstickthread" value="3" ${admingroups.allowstickthread==3?'checked' : ''}><bean:message key="admingroups_edit_stick_thread_3"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_mod_post"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_mod_post_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowmodpost" value="1" ${admingroups.allowmodpost==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowmodpost" value="0" ${admingroups.allowmodpost==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_del_post"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_del_post_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowdelpost" value="1" ${admingroups.allowdelpost==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowdelpost" value="0" ${admingroups.allowdelpost==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_ban_post"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_ban_post_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowbanpost" value="1" ${admingroups.allowbanpost==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowbanpost" value="0" ${admingroups.allowbanpost==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_mass_prune"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_mass_prune_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowmassprune" value="1" ${admingroups.allowmassprune==1?'checked' : ''}><bean:message key="yes"/>&nbsp; &nbsp;
									<input class="radio" type="radio" name="allowmassprune" value="0" ${admingroups.allowmassprune==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_refund"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_refund_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowrefund" value="1" ${admingroups.allowrefund==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowrefund" value="0" ${admingroups.allowrefund==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_censor_word"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_censor_word_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowcensorword" value="1" ${admingroups.allowcensorword==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowcensorword" value="0" ${admingroups.allowcensorword==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_view_ip"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_view_ip_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowviewip" value="1" ${admingroups.allowviewip==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowviewip" value="0"  ${admingroups.allowviewip==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_ban_ip"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_ban_ip_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowbanip" value="1" ${admingroups.allowbanip==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowbanip" value="0" ${admingroups.allowbanip==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_action_members"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_edit_user_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowedituser" value="1" ${admingroups.allowedituser==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowedituser" value="0" ${admingroups.allowedituser==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_ban_user"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_ban_user_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowbanuser" value="1" ${admingroups.allowbanuser==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowbanuser" value="0" ${admingroups.allowbanuser==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_mod_user"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_mod_user_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowmoduser" value="1" ${admingroups.allowmoduser==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowmoduser" value="0" ${admingroups.allowmoduser==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_post_announce"/></b><br />	<span class="smalltxt"><bean:message key="admingroups_edit_post_announce_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowpostannounce" value="1" ${admingroups.allowpostannounce==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowpostannounce" value="0" ${admingroups.allowpostannounce==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_view_log"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_view_log_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="allowviewlog" value="1" ${admingroups.allowviewlog==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="allowviewlog" value="0" ${admingroups.allowviewlog==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
							<tr>
								<td width="45%" class="altbg1">
									<b><bean:message key="admingroups_edit_disable_postctrl"/></b><br /><span class="smalltxt"><bean:message key="admingroups_edit_disable_postctrl_comment"/></span>
								</td>
								<td class="altbg2">
									<input class="radio" type="radio" name="disablepostctrl" value="1" ${admingroups.disablepostctrl==1?'checked' : ''}><bean:message key="yes"/> &nbsp; &nbsp;
									<input class="radio" type="radio" name="disablepostctrl" value="0" ${admingroups.disablepostctrl==0?'checked' : ''}><bean:message key="no"/>
								</td>
							</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<br />
					<center>
						<input class="button" type="submit" name="groupsubmit" value="<bean:message key="submit"/>">
					</center>
				</form>
</c:if>
<jsp:directive.include file="../cp_footer.jsp" />