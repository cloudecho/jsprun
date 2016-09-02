<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_medal"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=editmedal&memberid=${member.uid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="3"><bean:message key="menu_other_medal"/> - ${member.username}</td></tr>
		<tr class="category" align="center"><td><bean:message key="medals_image"/></td><td><bean:message key="name"/></td><td><bean:message key="a_member_medals_grant"/></td></tr>
		<c:forEach items="${medalslist}" var="medal">
		<tr align="center">
		<td class="altbg1"><img src="images/common/${medal.image}"></td>
		<td class="altbg2">${medal.name}</td>
		<c:choose>
		<c:when test="${medal.ismedal=='true'}">
		<td class="altbg1"><input class="checkbox" type="checkbox" name="medals" value="${medal.medalid}" checked></td>
		</c:when>
		<c:otherwise>
		<td class="altbg1"><input class="checkbox" type="checkbox" name="medals" value="${medal.medalid}" ></td>
		</c:otherwise>
		</c:choose>
		</tr>
		</c:forEach>
	</table><br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_member_edit_reason"/></td></tr>
		<tr><td class="altbg1" width="45%"><b><bean:message key="a_member_edit_medals_reason"/></b><br /><span class="smalltxt"><bean:message key="a_member_edit_medals_reason_comment"/></span></td>
		<td class="altbg2" width="40%"><textarea name="reason" rows="5" cols="30"></textarea></td></tr>
	</table><br /><center>
	<input class="button" type="submit" name="medalsubmit" value="<bean:message key="submit"/>">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />