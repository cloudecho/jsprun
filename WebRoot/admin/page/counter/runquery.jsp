<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_database_query" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td><bean:message key="a_system_database_run_query_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=runquery">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="option" value="simple">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan=2><bean:message key="a_system_database_run_query_simply" /></td></tr>
		<tr class="altbg1">
			<td align="center">
				<center>
					<select name="queryselect" style="width: 45%">
						<c:forEach items="${simplequeries}" var="simplequerie">
							<optgroup label="${upSimplequeries[simplequerie.key]}">
						<c:forEach items="${simplequerie.value}" var="simple"><option value="${simple.key}">${simple.value}</option></c:forEach>
						</c:forEach>
					</select>
					&nbsp;&nbsp;<input type="submit" name="sqlsubmit" value="<bean:message key="submit" />">
				</center>
			</td>
		</tr>
	</table>
</form>
<br />
<c:if test="${settings.admincp_runquery>0}">
	<form method="post" action="admincp.jsp?action=runquery">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="option" value="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan=2><bean:message key="a_system_database_run_query" /></td></tr>
			<tr class="altbg1">
				<td valign="top">
					<div align="center">
						<br />
						<textarea cols="85" rows="10" name="queries"></textarea>
						<br />
						<br />
						<bean:message key="a_system_database_run_query_comment" />
					</div>
				</td>
			</tr>
		</table>
		<br />
		<center><input class="button" type="submit" name="sqlsubmit" value="<bean:message key="submit" />"></center>
	</form>
</c:if>
<jsp:directive.include file="../cp_footer.jsp" />