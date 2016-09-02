<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;${navlang}</td></tr>
</table>
<br />
<c:if test="${special==0}">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td>
				<bean:message key="a_forum_threadtypes_tips"/>
			</td>
		</tr>
	</tbody>
</table>
<br />
</c:if>
<form method="post" action="admincp.jsp?action=threadtypes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="special" value="${special}">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="6">${navlang}</td></tr>
		<tr align="center" class="category">
			<td><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form)"><bean:message key="del"/></td>
			<td><bean:message key="name"/></td>
			<td><bean:message key="display_order"/></td>
			<td><bean:message key="description"/></td>
			<td><bean:message key="a_forum_threadtypes_forums"/></td>
			<c:if test="${special>0}"><td><bean:message key="detail"/></td></c:if>
		</tr>
		<c:forEach items="${threadtypes}" var="threadtype">
		<tr align="center">
			<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" value="${threadtype.typeid}"></td>
			<td class="altbg2"><input type="text" size="15" name="name[${threadtype.typeid}]" value="<jrun:dhtmlspecialchars value="${threadtype.name}"/>"></td>
			<td class="altbg1"><input type="text" size="2" name="displayorder[${threadtype.typeid}]" value="${threadtype.displayorder}"></td>
			<td class="altbg2"><input type="text" size="30" name="description[${threadtype.typeid}]" value="<jrun:dhtmlspecialchars value="${threadtype.description}"/>"></td>
			<td class="altbg1">${displayForums[threadtype.typeid]}<input type="hidden" name="fids[${threadtype.typeid}]" value="${displayfids[threadtype.typeid]}"></td>
			<c:if test="${special>0}"><td class="altbg2"><a href="admincp.jsp?action=typedetail&typeid=${threadtype.typeid}">[<bean:message key="detail"/>]</a></td></c:if>
		</tr>
		</c:forEach>
		<tr align="center" class="altbg1">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" name="newname" size="15" maxlength="255"></td>
			<td><input type="text" name="newdisplayorder" size="2" value="0"></td>
			<td><input type="text" name="newdescription" size="30" maxlength="255" value=""></td>
			<td>&nbsp;</td>
			<c:if test="${special>0}"><td>&nbsp;</td></c:if>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="typesubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />