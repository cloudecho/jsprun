<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="a_forum_moderators_edit"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td>
				<bean:message key="a_forum_moderators_tips"/>
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=moderators&fid=${forum.fid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="4"><bean:message key="a_forum_moderators_edit"/> - ${forum.name}</td></tr>
		<tr align="center" class="category">
			<td><bean:message key="del"/></td>
			<td><bean:message key="username"/></td>
			<td><bean:message key="display_order"/></td>
			<td><bean:message key="a_forum_moderators_inherited"/></td>
		</tr>
		<c:forEach items="${moderators}" var="moderator">
		<tr align="center">
			<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" ${moderator.inherited==0?"readonly":"disabled"} value="${moderator.uid}"></td>
			<td class="altbg2"><a href="space.jsp?action=viewpro&uid=${moderator.uid}" target="_blank">${moderator.username}</a></td>
			<td class="altbg1"><input type="text" name="displayorder_${moderator.uid}" value="${moderator.displayorder}" size="2"></td>
			<td class="altbg2"><c:choose> <c:when test="${moderator.inherited==0}"><bean:message key="no"/></c:when> <c:otherwise><b><bean:message key="yes"/></b></c:otherwise> </c:choose> </td>
		</tr>
		</c:forEach>
		<tr align="center">
			<td class="altbg1"><bean:message key="add_new"/></td>
			<td class="altbg2"><input type="text" name="newmoderator" size="20"></td>
			<td class="altbg1"><input type="text" name="newdisplayorder" size="2" value="0"></td>
			<td class="altbg2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="4" class="altbg2">
			<c:choose>
				<c:when test="${forum.type=='group'}"><input class="checkbox" type="checkbox"name="newInheritedmod" value="1" checked="checked" disabled="disabled"></c:when>
				<c:when test="${forum.type=='forum'}"><input class="checkbox" type="checkbox"name="newInheritedmod" value="1" ${forum.inheritedmod==1?"checked=":""}></c:when>
				<c:when test="${forum.type=='sub'}"><input class="checkbox" type="checkbox"name="newInheritedmod" value="0" disabled="disabled"></c:when>
			</c:choose>
			<bean:message key="a_forum_moderators_inherit"/>
			</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="modsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />