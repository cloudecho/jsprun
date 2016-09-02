<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_ipban"/></td></tr>
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
		<td>
			<bean:message key="a_member_ipban_tips"/>
		</td>
	</tr>
</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=editipban">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="48">
				<input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form)">
				<bean:message key="del"/>
			</td>
			<td>
				<bean:message key="ip"/>
			</td>
			<td>
				<bean:message key="a_member_ipban_location"/>
			</td>
			<td>
				<bean:message key="operator"/>
			</td>
			<td>
				<bean:message key="start_time"/>
			</td>
			<td>
				<bean:message key="end_time"/>
			</td>
		</tr>
		<c:forEach items="${bannedList}" var="banned">
			<input type="hidden" name="bannedid" value="${banned.id}" />
			<tr align="center">
				<td class="altbg1">
					<input class="checkbox" type="checkbox" name="delid" value="${banned.id}" ${members.adminid!=1 && members.username!=banned.admin?'disabled':''}>
				</td>
				<td class="altbg2">
					${banned.ipaddress}
				</td>
				<td class="altbg1">
					${banned.address}
				</td>
				<td class="altbg2">
					${banned.admin}
				</td>
				<td class="altbg1">
					<jrun:showTime timeInt="${banned.dateline}" type="${dateformat}"/>
				</td>
				<td class="altbg2">
					<input type="text" size="10" name="expiration[${banned.id}]" ${members.adminid!=1 && members.username!=banned.admin?'disabled' : ''} value="<jrun:showTime timeInt="${banned.expiration}" type="${dateformat}"/>">
				</td>
			</tr>
		</c:forEach>
		<tr align="center" class="altbg1">
			<td>
				<bean:message key="add_new"/>
			</td>
			<td colspan="3">
				<b> <input type="text" name="ip1new" value="${ip1}" size="3" maxlength="3"> . <input type="text" name="ip2new" value="${ip2}" size="3" maxlength="3"> . <input type="text" name="ip3new" value="${ip3}" size="3" maxlength="3"> . <input type="text" name="ip4new" value="${ip4}" size="3" maxlength="3">
				</b>
			</td>
			<td colspan="2">
				<bean:message key="validity"/>:
				<input type="text" name="validitynew" value="30" size="3">
				<bean:message key="day"/>
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="ipbansubmit" value="<bean:message key="submit"/>">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />