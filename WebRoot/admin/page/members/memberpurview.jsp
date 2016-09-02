<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="a_member_access_edit"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display:"><tr><td><bean:message key="a_member_access_tips"/></td></tr></tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=access&memberid=${member.uid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="7"><bean:message key="a_member_access_edit"/> - ${member.username}</td></tr>
		<tr class="category" align="center">
		<td><bean:message key="forum"/></td>
		<td><input class="checkbox" type="checkbox" name="chkall1" onclick="checkall(this.form, 'defaultnew', 'chkall1')"> <bean:message key="a_member_access_default"/></td>
		<td><input class="checkbox" type="checkbox" name="chkall2" onclick="checkall(this.form, 'allowviewnew', 'chkall2')"> <bean:message key="a_member_access_view"/></td>
		<td><input class="checkbox" type="checkbox" name="chkall3" onclick="checkall(this.form, 'allowpostnew', 'chkall3')"> <bean:message key="a_member_access_post"/></td>
		<td><input class="checkbox" type="checkbox" name="chkall4" onclick="checkall(this.form, 'allowreplynew', 'chkall4')"> <bean:message key="threads_replies"/></td>
		<td><input class="checkbox" type="checkbox" name="chkall5" onclick="checkall(this.form, 'allowgetattachnew', 'chkall5')"> <bean:message key="access_getattach"/></td>
		<td><input class="checkbox" type="checkbox" name="chkall6" onclick="checkall(this.form, 'allowpostattachnew', 'chkall6')"> <bean:message key="access_postattach"/></td></tr>
		<c:forEach items="${forumslist}" var="forum">
		<tr>
			<td class="altbg1" width="22%"><input class="checkbox" title="<bean:message key="select_all"/>" type="checkbox" name="chkallv${forum.fid}" onclick="checkallvalue(this.form, ${forum.fid}, 'chkallv${forum.fid}')">&nbsp;<c:if test="${forum.type=='sub'}">&nbsp;&nbsp;&nbsp;&nbsp;</c:if><a href="admincp.jsp?action=forumdetail&fid=${forum.fid}">${forum.name}</a></td>
			<c:choose>
			<c:when test="${forum.allowview==null}">
				<td class="altbg2" width="15%" align="center"><input class="checkbox" type="checkbox" name="defaultnew[${forum.fid}]" value="${forum.fid}" checked></td>
				<td class="altbg1" width="10%" align="center"><input class="checkbox" type="checkbox" name="allowviewnew[${forum.fid}]" value="${forum.fid}" ></td>
				<td class="altbg2" width="12%" align="center"><input class="checkbox" type="checkbox" name="allowpostnew[${forum.fid}]" value="${forum.fid}" ></td>
				<td class="altbg1" width="10%" align="center"><input class="checkbox" type="checkbox" name="allowreplynew[${forum.fid}]" value="${forum.fid}" ></td>
				<td class="altbg2" width="18%" align="center"><input class="checkbox" type="checkbox" name="allowgetattachnew[${forum.fid}]" value="${forum.fid}" ></td>
				<td class="altbg1" width="13%" align="center"><input class="checkbox" type="checkbox" name="allowpostattachnew[${forum.fid}]" value="${forum.fid}" ></td>
			</c:when>
			<c:otherwise>
				<td class="altbg2" width="15%" align="center"><input class="checkbox" type="checkbox" name="defaultnew[${forum.fid}]" value="${forum.fid}"></td>
				<td class="altbg1" width="10%" align="center"><input class="checkbox" type="checkbox" name="allowviewnew[${forum.fid}]" value="${forum.fid}" ${forum.allowview!=0?"checked":""}></td>
				<td class="altbg2" width="12%" align="center"><input class="checkbox" type="checkbox" name="allowpostnew[${forum.fid}]" value="${forum.fid}" ${forum.allowpost!=0?"checked":""}></td>
				<td class="altbg1" width="10%" align="center"><input class="checkbox" type="checkbox" name="allowreplynew[${forum.fid}]" value="${forum.fid}" ${forum.allowreply!=0?"checked":""}></td>
				<td class="altbg2" width="18%" align="center"><input class="checkbox" type="checkbox" name="allowgetattachnew[${forum.fid}]" value="${forum.fid}" ${forum.allowgetattach!=0?"checked":""}></td>
				<td class="altbg1" width="13%" align="center"><input class="checkbox" type="checkbox" name="allowpostattachnew[${forum.fid}]" value="${forum.fid}" ${forum.allowpostattach!=0?"checked":""}></td>
			</c:otherwise>
			</c:choose>
		</tr>
		</c:forEach>
	</table>
	<br />
	<center>
		<input class="button" type="reset" name="accesssubmit" value="<bean:message key="reset"/>">
		<input class="button" type="submit" name="accesssubmit" value="<bean:message key="submit"/>">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />