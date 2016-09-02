<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_edit"/></td></tr>
</table>
					<br />
					<br />
					<form method="post" action="admincp.jsp?action=editextmembers">
						<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
						<input type="hidden" name="uid" value="${editmember.uid}">
						<a name="17a108221bce06a4"></a>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
							<tr class="header">
								<td colspan="2">
									<bean:message key="menu_member_edit"/> - ${editmember.username}
									<a href="###" onclick="collapse_change('17a108221bce06a4')"><img id="menuimg_17a108221bce06a4" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" />
									</a>
								</td>
							</tr>
							<tbody id="menu_17a108221bce06a4" style="display: yes">
								<tr>
									<td width="45%" class="altbg1">
										<b><bean:message key="a_member_edit_location"/></b>
									</td>
									<td class="altbg2">
										<input type="text" size="50" name="location" value="${editmemberfild.location}">
									</td>
								</tr>
								<tr>
									<td width="45%" class="altbg1" valign="top">
										<b><bean:message key="a_member_edit_bio"/></b>
									</td>
									<td class="altbg2">
										<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('bionew', 1)">
										<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('bionew', 0)">
										<br />
										<textarea rows="6" name="bionew" id="bionew" cols="50">${editmemberfild.bio}</textarea>
									</td>
								</tr>
								<tr>
									<td width="45%" class="altbg1" valign="top">
										<b><bean:message key="a_member_edit_signature"/></b>
									</td>
									<td class="altbg2">
										<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('signaturenew', 1)">
										<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('signaturenew', 0)">
										<br />
										<textarea rows="6" name="signaturenew" id="signaturenew" cols="50">${editmemberfild.sightml}</textarea>
									</td>
								</tr>
							</tbody>
						</table>
						<br />
						<br />
						<center>
							<input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>">
						</center>
					</form>
<jsp:directive.include file="../cp_footer.jsp" />