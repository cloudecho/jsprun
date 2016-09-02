<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_database_import" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td><bean:message key="a_system_database_import_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<form name="restore" method="post" action="admincp.jsp?action=importData&formHash=${jrun:formHash(pageContext.request)}" enctype="multipart/form-data">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_system_database_import" /></td></tr>
		<tr>
			<td class="altbg1" width="40%"><input class="radio" type="radio" name="from" value="server" checked onclick="this.form.datafile_server.disabled=!this.checked;this.form.datafile.disabled=this.checked"><bean:message key="a_system_database_import_from_server" /></td>
			<td class="altbg2" width="45%"><input type="text" size="40" name="datafile_server" value="${backupdir}"></td>
		</tr>
		<tr>
			<td class="altbg1" width="40%"><input class="radio" type="radio" name="from" value="local" onclick="this.form.datafile_server.disabled=this.checked;this.form.datafile.disabled=!this.checked"><bean:message key="a_system_database_import_from_local" />
			</td>
			<td class="altbg2" width="45%"><input type="file" size="29" name="datafile" disabled></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="importsubmit" value="<bean:message key="submit" />"></center>
</form>
<br />
<form method="post" action="admincp.jsp?action=importFile">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="9"><bean:message key="a_system_database_export_file" /></td></tr>
		<tr align="center" class="category">
			<td width="48"><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form)"><bean:message key="del" /></td>
			<td><bean:message key="filename" /></td>
			<td><bean:message key="version" /></td>
			<td><bean:message key="time" /></td>
			<td><bean:message key="type" /></td>
			<td><bean:message key="size" /></td>
			<td><bean:message key="a_system_database_method" /></td>
			<td><bean:message key="a_system_database_volume" /></td>
			<td><bean:message key="operation" /></td>
		</tr>
		<c:forEach items="${dumpfiles}" var="dumpfile">
		<tr align="center">
			<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" value="${dumpfile['filepath']}"></td>
			<td class="altbg2"><a href="${pageContext.request.contextPath}${dumpfile['filepath']}">${dumpfile["filename"]}</a></td>
			<td class="altbg1">${dumpfile["jsprunversion"]}</td>
			<td class="altbg2">${dumpfile["dateline"]}</td>
			<td class="altbg1">
			<c:choose>
				<c:when test="${dumpfile['type']=='jsprun'}"><bean:message key="a_system_database_export_jsprun" /></c:when>
				<c:when test="${dumpfile['type']=='custom'}"><bean:message key="a_system_database_export_custom" /></c:when>
				<c:when test="${dumpfile['type']=='zip'}"><bean:message key="a_system_database_export_zip" /></c:when>
			</c:choose>
			</td>
			<td class="altbg2">${dumpfile["filesize"]}</td>
			<td class="altbg1">
			<c:choose>
				<c:when test="${dumpfile['method']=='shell'}">Shell</c:when>
				<c:when test="${dumpfile['method']=='multivol'}"><bean:message key="a_system_database_multivol" /></c:when>
			</c:choose>
			</td>
			<td class="altbg2">${dumpfile["volume"]}</td>
			<td class="altbg1">
			<c:choose>
				<c:when test="${dumpfile['type']=='zip'}"><a href="admincp.jsp?action=importZipFile&datafile_server=.${dumpfile['filepath']}&importsubmit=yes">[<bean:message key="a_system_database_import_unzip" />]</a></c:when>
				<c:when test="${dumpfile['type']!='zip'&& dumpfile['jsprunversion']!=version}"><a href="admincp.jsp?action=importFile&from=server&datafile_server=.${dumpfile['filepath']}&importsubmit=yes" onclick="return confirm('<bean:message key="a_system_database_import_confirm" />');"}>[<bean:message key="import" />]</a></c:when>
				<c:otherwise><a href="admincp.jsp?action=importFile&from=server&datafile_server=.${dumpfile['filepath']}&importsubmit=yes&formHash=${jrun:formHash(pageContext.request)}">[<bean:message key="import" />]</a></c:otherwise>
			</c:choose>
			</td>
		<tr>
		</c:forEach>
	</table>
	<br />
	<center><input class="button" type="submit" name="deletesubmit" value="<bean:message key="submit" />"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />