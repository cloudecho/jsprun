<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_creditwizard" /></td></tr>
</table>
<br />
<jsp:include page="lead.jsp"/>
<form method="post" action="admincp.jsp?action=settings&do=usergroupSetting&extcreditid=${extcreditid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="param" value="usergroupSettingCommit">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" align="center">
		<tr class="header">
			<jsp:include page="leadL2.jsp"/>
			<td class="altbg2" style="text-align: right">
				<select id="creditid" onchange="location.href='admincp.jsp?action=settings&do=usergroupSetting&extcreditid=' + this.value">
					<c:forEach items="${extcredits}" var="ext">
						<option value="${ext.key}" ${extcreditid== ext.key?"selected":""}>
							extcredits${ext.key}<c:if test="${!empty ext.value.title }">(${ext.value.title})</c:if>
						</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;
			</td>
		</tr>
		<tr class="category">
			<td colspan="2" class="altbg2"><bean:message key="a_setting_settingtype_usergroup_tips" />
			</td>
		</tr>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="4">
				<bean:message key="a_setting_forum_groupraterange" />
				<a href="###" onclick="collapse_change('a7da939466bebce2')">
				<img id="menuimg_a7da939466bebce2" src="${pageContext.request.contextPath }/images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> 
				</a>
			</td>
		</tr>
		<tbody id="menu_a7da939466bebce2" style="display: yes">
			<tr class="category">
				<td>
					<bean:message key="forum" />
				</td>
				<td>
					<bean:message key="usergroups_edit_raterange_min" />
				</td>
				<td>
					<bean:message key="usergroups_edit_raterange_max" />
				</td>
				<td>
					<bean:message key="usergroups_edit_raterange_mrpd" />
				</td>
			</tr>
			<c:forEach items="${usergroupVOList}" var="usergroupVO">
				<tr>
					<td class="altbg1" width="22%">
						<input type="hidden" name="groupidArray" value="${usergroupVO.groupid }">
						<input class="checkbox" type="checkbox" name="raterangestatus_${usergroupVO.groupid }" value="1"${usergroupVO.checked}>
						<a href="admincp.jsp?frames=yes&action=usergroups&edit=9" target="_blank">${usergroupVO.grouptitle }</a>
					</td>
					<td class="altbg2">
						<input type="text" name="minValue_${usergroupVO.groupid }" size="3" value="${usergroupVO.minValue }">
					</td>
					<td class="altbg1">
						<input type="text" name="maxValue_${usergroupVO.groupid }" size="3" value="${usergroupVO.maxValue }">
					</td>
					<td class="altbg2">
						<input type="text" name="hourMaxValue_${usergroupVO.groupid }" size="3" value="${usergroupVO.hourMaxValue }">
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br />
	<center>
		<input class="button" type="reset" name="settingsubmit" value="<bean:message key="reset" />">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../../cp_footer.jsp" />