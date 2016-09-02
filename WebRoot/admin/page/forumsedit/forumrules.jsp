<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_forum_rule"/></td></tr>
</table>
<br />
<c:choose>
	<c:when test="${forum==null}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="menu_forum_edit"/></td></tr>
			<tr class="altbg2">
				<td><bean:message key="forum"/>:</td>
				<td>
					<select onchange="window.location=('admincp.jsp?action=forumrules&amp;fid='+this.options[this.selectedIndex].value);">
						<option value=""><bean:message key="none"/></option>
						${options}
					</select>
				</td>
			</tr>
		</table>
		<br />
		<br />
	</c:when>
	<c:otherwise>
		<form method="post" action="admincp.jsp?action=forumrules&fid=${forum.fid}">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
				<tr class="header"><td colspan="2"><bean:message key="menu_forum_edit"/> - ${forum.name}</td></tr>
				<tr class="altbg2"><td valign="top"><span class="bold"><bean:message key="a_forum_edit_rules"/>:</span><br /><c:choose><c:when test="${forum.alloweditrules==1}"><bean:message key="a_forum_edit_rule_html_no"/></c:when><c:otherwise><bean:message key="a_forum_edit_rule_html_yes"/></c:otherwise></c:choose><?=$comment?></td>
				<td><textarea name="rulesnew" rows="5" cols="60"><jrun:dhtmlspecialchars value="${forum.rules}"/></textarea></td></tr>
			</table>
			<br />
			<center><input class="button" type="submit" name="rulessubmit" value="<bean:message key="submit"/>"></center>
		</form>
		<br />
	</c:otherwise>
</c:choose>
<jsp:include page="../cp_footer.jsp" />