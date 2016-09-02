<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_cron"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td><bean:message key="a_other_crons_tips"/></td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=crons" name="form1">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="7%"><input class="checkbox" type="checkbox" name="chkall" class="header" onclick="checkall(this.form,'delete')"><bean:message key="del"/></td>
			<td><bean:message key="name"/></td>
			<td><bean:message key="available"/></td>
			<td><bean:message key="type"/></td>
			<td><bean:message key="a_other_crons_minute"/></td>
			<td width="5%"><bean:message key="hour"/></td>
			<td width="5%"><bean:message key="day"/></td>
			<td width="6%"><bean:message key="a_other_crons_week_day"/></td>
			<td><bean:message key="a_other_crons_last_run"/></td>
			<td><bean:message key="a_other_crons_next_run"/></td>
			<td><bean:message key="operation"/></td>
		</tr>
	<c:forEach items="${valueObject}" var="cronInfo">
		<tr align="center">
			<td class="altbg1"><input type="hidden" name="cronId" value="${cronInfo.cronid}"><input class="checkbox" type="checkbox" name="delete" value="${cronInfo.cronid}" ${cronInfo.type=='system'?"disabled":""}></td>
			<td class="altbg2"><input type="hidden" name="cronName" value="${cronInfo.name}"><input type="text" name="namenew" size="20" value="${cronInfo.name}" maxlength="50"><br /><b>${cronInfo.filename }</b></td>
			<td class="altbg1"><input type="hidden" name="cronAvailable" value="${cronInfo.available}"><input class="checkbox" type="checkbox" name="availablenew[${cronInfo.cronid}]" value="1" ${cronInfo.available == 1?"checked":""} ${cronInfo.disabled ?"disabled":""}></td>
			<td class="altbg2">
			<c:choose>
			<c:when test="${cronInfo.type=='system'}"><bean:message key="admingroups_type_system"/></c:when>
			<c:otherwise><bean:message key="custom"/></c:otherwise>
			</c:choose>
			</td>
			<td class="altbg1">${cronInfo.minute}</td>
			<td class="altbg2">${cronInfo.hour}</td>
			<td class="altbg1">${cronInfo.day}</td>
			<td class="altbg2">${cronInfo.weekday}</td>
			<td class="altbg1">${cronInfo.lastrun}</td>
			<td class="altbg2">${cronInfo.nextrun}</td>
			<td class="altbg1">
				<a href="admincp.jsp?action=cronsedit&cronsId=${cronInfo.cronid}">[<bean:message key="edit"/>]</a>
				<c:choose>
					<c:when test="${cronInfo.available==1}">
						<a href="admincp.jsp?action=crons&run=${cronInfo.cronid}&formHash=${jrun:formHash(pageContext.request)}">[<bean:message key="a_other_crons_run"/>]</a>
					</c:when>
					<c:otherwise>
						[<bean:message key="a_other_crons_run"/>]
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
		<tr align="center" class="altbg1">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" size="20" name="newname" maxlength="50"></td>
			<td colspan="9">&nbsp;</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="cronssubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />