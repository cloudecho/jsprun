<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_forum_add"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr><td><ul><li><bean:message key="a_forum_add_tips"/></ul></td></tr>
	</tbody>
</table>
<br />
<c:if test="${empty type}">
<br />
<form method="post" action="admincp.jsp?action=forumadd&add=cateGroup">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="3"><bean:message key="a_forum_add_category"/></td></tr>
		<tr align="center">
			<td class="altbg1" width="15%"><bean:message key="name"/>:</td>
			<td class="altbg2" width="85%"><input type="text" name="name" value="<bean:message key="a_forum_add_category_name"/>" size="20" maxlength="50"></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="forumsubmit" value="<bean:message key='submit'/>"></center>
</form>
</c:if>
<c:if test="${empty type||type=='group'}">
<br />
<form method="post" action="admincp.jsp?action=forumadd&add=cateForum">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_forum_add_forum"/></td></tr>
		<tr align="center">
			<td class="altbg1" width="15%"><bean:message key="name"/>:</td>
			<td class="altbg2" width="85%"><input type="text" name="name" value="<bean:message key='a_forum_add_forum_name'/>" size="20" maxlength="50"></td>
		</tr>
		<tr align="center">
			<td class="altbg1" width="15%"><bean:message key="a_forum_add_parent_category"/>:</td>
			<td class="altbg2" width="85%">
				<select name="fup">
					<c:forEach items="${groups}" var="group"><option value="${group.fid}" ${fupid==group.fid?"selected":""}>${group.name}</option></c:forEach>
				</select>
			</td>
		</tr>
		<tr align="center">
			<td class="altbg1" width="15%"><bean:message key="a_forum_scheme"/>:</td>
			<td class="altbg2" width="85%">
				<select name="projectId">
					<option value="0" selected="selected"><bean:message key="none"/></option>
					<c:forEach items="${projects}" var="project"><option value="${project.id}">${project.name}</option></c:forEach>
				</select>
			</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="forumsubmit" value="<bean:message key='submit'/>"></center>
</form>
</c:if>
<c:if test="${empty type||type=='forum'}">
<br />
<form method="post" action="admincp.jsp?action=forumadd&add=cateSub">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="3"><bean:message key="a_forum_add_sub"/></td></tr>
		<tr align="center">
			<td class="altbg1" width="15%"><bean:message key="name"/>:</td>
			<td class="altbg2" width="28%"><input type="text" name="name" value="<bean:message key='a_forum_add_sub_name'/>" size="20" maxlength="50"></td>
		</tr>
		<tr align="center">
			<td class="altbg1" width="15%"><bean:message key="a_forum_add_parent_forum"/>:</td>
			<td class="altbg2" width="27%">
				<select name="fup">
					<c:forEach items="${groups}" var="group">
						<optgroup label="${group.name}">
							<c:forEach items="${forums}" var="forum">
								<c:if test="${group.fid==forum.fup}">
									<option value="${forum.fid}" ${fupid==forum.fid?"selected":""}>&nbsp; &nbsp; &gt;${forum.name}</option>
								</c:if>
							</c:forEach>
						</optgroup>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr align="center">
			<td class="altbg1" width="15%"><bean:message key="a_forum_scheme"/>:</td>
			<td class="altbg2" width="85%">
				<select name="projectId">
					<option value="0" selected="selected"><bean:message key="none"/></option>
					<c:forEach items="${projects}" var="project"><option value="${project.id}">${project.name}</option></c:forEach>
				</select>
			</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="forumsubmit" value="<bean:message key='submit'/>"></center>
</form>
<br />
</c:if>
<jsp:directive.include file="../cp_footer.jsp" />