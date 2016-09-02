<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="a_post_forums_recommend"/></td></tr>
</table>
<br />
<c:choose>
	<c:when test="${!empty forums}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_post_forums_recommend"/></td></tr>
			<tr class="altbg2"><td><bean:message key="forum"/>:</td><td>${forums}</td></tr>
		</table>
		<br />
		<center></center>
	</c:when>
	<c:otherwise>
		<form method="post" action="admincp.jsp?action=forumrecommend">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="fid" value="${fid}">
			<input type="hidden" name="page" value="${page}">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
				<tr class="header"><td colspan="10"><bean:message key="a_post_forums_recommend"/></td></tr>
				<tr align="center" class="category">
					<td width="5%"><input type="checkbox" name="chkall" class="checkbox" onclick="checkall(this.form)"><bean:message key="del"/></td>
					<td width="8%"><bean:message key="display_order"/></td><td width="12%"><bean:message key="forums_name"/></td><td width="40%"><bean:message key="subject"/></td>
					<td width="10%"><bean:message key="author"/></td><td width="10%"><bean:message key="a_post_forums_recommend_mod"/></td><td width="15%"><bean:message key="a_post_forums_recommend_expiration"/></td>
				</tr>
				<c:forEach items="${threadlist}" var="thread">
					<tr align="center">
						<td class="altbg1"><input type="hidden" name="tids" value="${thread.tid}"><input type="checkbox" class="checkbox" name="delete" value="${thread.tid}"></td>
						<td class="altbg2"><input type="text" size="3" name="displayorder_${thread.tid}" value="${thread.displayorder}"></td>
						<td class="altbg1">${thread.name}</td>
						<td class="altbg2"><a href="viewthread.jsp?tid=${thread.tid}" target="_blank">${thread.subject}</a></td>
						<td class="altbg1"><a href="space.jsp?uid=${thread.authorid}" target="_blank">${thread.author}</td>
						<td class="altbg2">${thread.username}</td>
						<td class="altbg1">${thread.expiration}</td>
					</tr>
				</c:forEach>
			</table>
			${multi.multipage}
			<br />
			<center><input type="submit" class="button" name="recommendsubmit" value="<bean:message key="submit"/>"></center>
		</form>
	</c:otherwise>
</c:choose>
<br />
<jsp:directive.include file="../cp_footer.jsp" />