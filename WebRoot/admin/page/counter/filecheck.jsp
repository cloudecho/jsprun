<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_filecheck" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<div style="float:left; margin-left:0px; padding-top:8px">
				<a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a>
			</div>
			<div style="float:right; margin-right:4px; padding-bottom:9px">
				<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a>
			</div>
		</td>
	</tr>
	<tbody id="menu_tip" style="display:">
		<tr>
			<td><bean:message key="a_system_filecheck_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="4">
			<bean:message key="a_system_filecheck_completed" />
		</td>
	</tr>
	<tr>
		<td colspan="4">
			${result}
		</td>
	</tr>
	<c:forEach items="${listresult}" var="result" varStatus="index">
		<c:if test="${index.count%2!=0}">
			<tr class="header">
				${result}
			<tr>
			<tr class="category">
				<td>
					<bean:message key="filename" />
				</td>
				<td style="text-align:right">
					<bean:message key="size" />&nbsp;&nbsp;
				</td>
				<td>
					<bean:message key="a_forum_filecheck_filemtime" />
				</td>
				<td>
					<bean:message key="a_system_check_status" />
				</td>
			</tr>
		</c:if>
		<c:if test="${index.count%2==0}">
			<c:forEach items="${result}" var="disresult">
				<tr>
					<td class="altbg1">
						${disresult.filename}
					</td>
					<td class="altbg2" style="text-align:right">
						${disresult.filebyte} Bytes&nbsp;&nbsp;
					</td>
					<td class="altbg1">
						${disresult.modifyDate}
					</td>
					<td class="altbg2">
						${disresult.status}
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</c:forEach>
</table>
<jsp:directive.include file="../cp_footer.jsp" />