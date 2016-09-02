<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_database_dbcheck" /></td></tr>
</table>
<br />
<c:choose>
	<c:when test="${info=='yes'}">
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<table width="500" border="0" cellpadding="0" cellspacing="0" align="center" class="tableborder">
			<tr class="header"><td><bean:message key="jsprun_message" /></td></tr>
			<tr>
				<td class="altbg2">
					<div align="center">
						<br />
						<br />
						<br />
						<c:choose>
							<c:when test="${dbcheck_permissions_invalid!=null}">${dbcheck_permissions_invalid}</c:when>
							<c:when test="${dbcheck_checking!=null}">
								<bean:message key="a_system_dbcheck_checking" />
								<br />
								<br />
								<br />
								<a href="${dbcheck_checking}"><bean:message key="message_redirect" /></a>
								<script>setTimeout("redirect('${dbcheck_checking}&sid=uZHWtU');", 2000);</script>
							</c:when>
							<c:when test="${dbcheck_nofound_md5file!=null}">${dbcheck_nofound_md5file}</c:when>
							<c:when test="${dbcheck_modify_md5file!=null}">${dbcheck_modify_md5file}</c:when>
							<c:when test="${dbcheck_ok!=null}">${dbcheck_ok}</c:when>
							<c:when test="${dbcheck_repair_error!=null}"><bean:message key="a_system_database_bcheck_faild" /><br />${dbcheck_repair_error}</c:when>
							<c:when test="${dbcheck_repair_completed!=null}"><bean:message key="a_system_database_bcheck_repair_completed" /><br />
								<br />
								<br />
								<a href="${dbcheck_repair_completed}"><bean:message key="message_redirect" /></a>
								<script>setTimeout("redirect('${dbcheck_repair_completed}&sid=JFPoS8');", 2000);</script>
							</c:when>
						</c:choose>
						<br />
						<br />
					</div>
					<br />
					<br />
				</td>
			</tr>
		</table>
		<br />
		<br />
		<br />
	</c:when>
	<c:otherwise>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
			<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
				<tr><td><bean:message key="a_system_dbcheck_tips" /></td></tr>
			</tbody>
		</table>
		<br />
		<script type="text/javascript">
			function setrepaircheck(obj, form, table) {
				eval('var rem = /^' + table + '\\|.+?\\|modify$/;');
				eval('var rea = /^' + table + '\\|.+?\\|add$/;');
				for(var i = 0; i < form.elements.length; i++) {
					var e = form.elements[i];
					if(e.type == 'checkbox' && e.name == 'repair') {
						if(rem.exec(e.value) != null) {
							if(obj.checked) {
								e.checked = false;
								e.disabled = true;
							} else {
								e.checked = false;
								e.disabled = false;
			
							}
						}
						if(rea.exec(e.value) != null) {
							if(obj.checked) {
								e.checked = true;
								e.disabled = false;
							} else {
								e.checked = false;
								e.disabled = false;
							}
						}
					}
				}
			}
		</script>
		<c:if test="${hasErrorField||missingtables!=null||settingsdeldata!=null}">
		<form method="post" action="admincp.jsp?action=moddbcheck&start=yes">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="modifysMap" value="${modifysMap}">
			<input type="hidden" name="delsMap" value="${delsMap}">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
				<c:if test="${hasErrorField}">
					<tr class="header"><td colspan="3"><bean:message key="a_system_dbcheck_errorfields_tables" /></td></tr>
					<tr>
						<td class="altbg1" colspan="3">
							<br />
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
								<c:forEach items="${jsprundb}" var="jsprun">
									<c:if test="${modifysMap[jsprun.key]!=null||delsMap[jsprun.key]!=null}">
										<tr class="header">
											<td width="40%"><b>${tablepre}${jsprun.key}</b> <bean:message key="a_system_dbcheck_field" /></td>
											<td width="40%"><bean:message key="a_system_dbcheck_org_field" /></td>
											<td width="20%"><bean:message key="a_system_check_status" /></td>
										</tr>
									</c:if>
									<c:if test="${modifysMap[jsprun.key]!=null}">
										<c:forEach items="${modifysMap[jsprun.key]}" var="modifyField">
											<tr class="altbg2">
												<td>
													<input name="repair" class="checkbox" type="checkbox" value="${jsprun.key}|${modifyField.field}|modify">
													<b>${modifyField.field}</b> ${modifyField.type}${modifyField.allowNull=="NO" ? "NOT NULL" : ""}default'${modifyField.defaultValue}' ${modifyField.extra} 
											 	</td>
												<td><b>${jsprun.value[modifyField.field]["Field"]}</b> ${jsprun.value[modifyField.field]["Type"]} ${jsprun.value[modifyField.field]["Null"]=="NO"?"NOT NULL":""} default '${jsprun.value[modifyField.field]["Default"]}' ${jsprun.value[modifyField.field]["Extra"]}</td>
												<td><font color="#FF0000"><bean:message key="a_system_dbcheck_modify" /></font></td>
											</tr>
										</c:forEach>
										<tr class="altbg1">
											<td colspan="3">
												<input onclick="setrepaircheck(this, this.form, '${jsprun.key}')" name="repairtable" class="checkbox" type="checkbox" value="${jsprun.key}">
												<b><bean:message key="a_system_dbcheck_repairtable" /></b>
											</td>
										</tr>
									</c:if>
									<c:if test="${delsMap[jsprun.key]!=null}">
										<c:forEach items="${delsMap[jsprun.key]}" var="delFieldName">
											<tr class="altbg2">
												<td>
													<input name="repair" class="checkbox" type="checkbox" value="${jsprun.key}|${delFieldName}|add">
													<strike><b>${delFieldName}</b> </strike>
												</td>
												<td>
													<b>${jsprun.value[delFieldName]["Field"]}</b>
													${jsprun.value[delFieldName]["Type"]}
													${jsprun.value[delFieldName]["Null"]=="NO"?"NOT NULL":""}
													${jsprun.value[delFieldName]["Default"]}
													${jsprun.value[delFieldName]["Extra"]}
												</td>
												<td><font color="#0000FF"><bean:message key="a_system_dbcheck_delete" /></font></td>
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</table>
							<br />
						</td>
					</tr>
				</c:if>
				<c:if test="${missingtables!=null}">
					<tr class="header"><td colspan="3"><bean:message key="a_system_dbcheck_missing_tables" /></td></tr>
					<tr class="altbg1"><td colspan="3">${missingtables}</td></tr>
				</c:if>
				<c:if test="${settingsdeldata!=null}">
					<tr class="header"><td colspan="3"><bean:message key="a_system_dbcheck_settings" /></td></tr>
					<tr class="altbg1"><td colspan="3"><input name="setting[del]" class="checkbox" type="checkbox" value="1">${settingsdeldata}<br /></td></tr>
				</c:if>
			</table>
			<br />
			<center><input type="submit" class="button" name="repairsubmit" value="<bean:message key="a_system_dbcheck_repair" />"></center>
		</form>
		</c:if>
		<c:if test="${charseterrors!=null}">
			<br />
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
				<tr class="header"><td colspan="3"><bean:message key="a_system_dbcheck_charseterror_tables" /> (<bean:message key="a_system_dbcheck_charseterror_notice" /> ${dbcharset})</td></tr>
				<tr class="altbg1"><td colspan="3">${charseterrors}</td></tr>
			</table>
		</c:if>
		<c:if test="${addsMap!=''}">
			<br />
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
				<tr class="header"><td colspan="3"><bean:message key="a_system_dbcheck_userfields" /></td></tr>
				<c:forEach items="${addsMap}" var="addMap">
				<tr class="category"><td><b>${tablepre}${addMap.key}</b> <bean:message key="a_system_dbcheck_new_field" /></td></tr>
					<c:forEach items="${addMap.value}" var="field">
					<tr><td class="altbg1">&nbsp;&nbsp;&nbsp;&nbsp;<b>${field.field}</b> ${field.type} ${field.allowNull=="NO"?"NOT NULL":""} ${field.defaultValue} ${field.extra}</td></tr>
					</c:forEach>
				</c:forEach>
			</table>
		</c:if>
	</c:otherwise>
</c:choose>
<jsp:directive.include file="../cp_footer.jsp" />