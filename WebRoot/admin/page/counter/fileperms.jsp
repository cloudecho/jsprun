<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_fileperm" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<div style="float:left; margin-left:0px; padding-top:8px">
				<a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a>
			</div>
			<div style="float:right; margin-right:4px; padding-bottom:9px">
				<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" />
				</a>
			</div>
		</td>
	</tr>
	<tbody id="menu_tip" style="display:">
		<tr>
			<td><bean:message key="a_system_fileperms_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<bean:message key="a_system_fileperms_check" />
		</td>
	</tr>
	<c:choose>
		<c:when test="${fallfilelist==null}">
			<tr>
		<td class="altbg1">
			<br />
			<ul>
				<li>
					<bean:message key="a_system_fileperms_check_ok" /> </ul>
				<br />
			</td>
		</tr>
		</c:when>
		<c:otherwise>
			<tr>
		<td class="altbg1">
			<br />
			<ul>
			<c:forEach items="${fallfilelist}" var="fallfile">
				<li style="color:red"><bean:message key="a_system_fileperms_unwritable" arg0="${fallfile}" />
			</c:forEach>
			</ul>
				<br />
			</td>
		</tr>
		</c:otherwise>
	</c:choose>
</table>
<jsp:directive.include file="../cp_footer.jsp" />