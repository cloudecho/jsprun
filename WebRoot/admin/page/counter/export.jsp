<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_database_export" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td><bean:message key="a_system_database_export_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<form name="backup" method="post" action="admincp.jsp?action=exportData">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="setup" value="1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_system_database_export_type" /></td></tr>
		<tr>
			<td class="altbg1" width="40%"><input class="radio" type="radio" value="jsprun" name="type" onclick="$('showtables').style.display='none'" checked> <bean:message key="a_system_database_export_jsprun" /></td>
			<td class="altbg2" width="45%"></td>
		</tr>
		<tr>
			<td class="altbg1"><input class="radio" type="radio" value="custom" name="type" onclick="$('showtables').style.display=''"> <bean:message key="a_system_database_export_custom" /></td>
			<td class="altbg2"><bean:message key="a_system_database_export_custom_comment" /></td>
		</tr>
		<tbody id="showtables" style="display: none">
			<tr>
				<td class="altbg2" colspan="2">
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
						<tr>
							<td colspan="4">
								<b><bean:message key="a_system_database_export_jsprun_table" /></b>
								<input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form, 'customtables')" checked>
								<b><bean:message key="select_all" /></b>
							</td>
						</tr>
						<tr><c:forEach items="${tableNames}" var="tableName" varStatus="k"><td><input class="checkbox" type="checkbox" name="customtables" value="${tableName}" checked> ${tableName}</td>${k.count%4==0?"</tr><tr>":""}</c:forEach></tr>
					</table>
				</td>
			</tr>
		</tbody>
		<tr>
			<td class="altbg1">&nbsp;</td>
			<td align="right" class="altbg2" style="text-align: right;"><input class="checkbox" type="checkbox" value="1" onclick="$('advanceoption').style.display = $('advanceoption').style.display == 'none' ? '' : 'none'; this.value = this.value == 1 ? 0 : 1; this.checked = this.value == 1 ? false : true"><bean:message key="more_options" /> &nbsp;</td>
		</tr>
		<tbody id="advanceoption" style="display: none;">
			<tr class="header"><td colspan="2"><bean:message key="a_system_database_export_method" /></td></tr>
			<tr>
				<td class="altbg1"><input class="radio" type="radio" name="method" value="shell" onclick="if(0) {if(this.form.sqlcompat[2].checked==true) this.form.sqlcompat[0].checked=true; this.form.sqlcompat[2].disabled=true; this.form.sizelimit.disabled=true;} else {this.form.sqlcharset[0].checked=true; for(var i=1; i<=5; i++) {if(this.form.sqlcharset[i]) this.form.sqlcharset[i].disabled=true;}}"><bean:message key="a_system_database_export_shell" /></td>
				<td class="altbg2">&nbsp;</td>
			</tr>
			<tr>
				<td class="altbg1"><input class="radio" type="radio" name="method" value="multivol" checked onclick="this.form.sqlcompat[2].disabled=false; this.form.sizelimit.disabled=false; for(var i=1; i<=5; i++) {if(this.form.sqlcharset[i]) this.form.sqlcharset[i].disabled=false;}"><bean:message key="a_system_database_export_multivol" /></td>
				<td class="altbg2"><input type="text" size="40" name="sizelimit" value="2048"></td>
			</tr>
			<tr class="header"><td colspan="2"><bean:message key="a_system_database_export_options" /></td></tr>
			<tr>
				<td class="altbg1">&nbsp;<bean:message key="a_system_database_export_options_extended_insert" /></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="extendins" value="1"> <bean:message key="yes" /> &nbsp;
					<input class="radio" type="radio" name="extendins" value="0" checked> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td class="altbg1">&nbsp;<bean:message key="a_system_database_export_options_sql_compatible" /></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="sqlcompat" value="" checked> <bean:message key="default" /> &nbsp;
					<input class="radio" type="radio" name="sqlcompat" value="MYSQL40"> MySQL 3.23/4.0.x &nbsp;
					<input class="radio" type="radio" name="sqlcompat" value="MYSQL41"> MySQL 4.1.x/5.x &nbsp;
				</td>
			</tr>
			<tr>
				<td class="altbg1">&nbsp;<bean:message key="a_system_database_export_options_charset" /></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="sqlcharset" value="" checked> <bean:message key="default" /> &nbsp;
					<input class="radio" type="radio" name="sqlcharset" value="gbk"> GBK &nbsp;
					<input class="radio" type="radio" name="sqlcharset" value="utf8"> UTF8
				</td>
			</tr>
			<tr>
				<td class="altbg1">&nbsp;<bean:message key="a_system_database_export_usehex" /></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="usehex" value="1" checked> <bean:message key="yes" /> &nbsp;
					<input class="radio" type="radio" name="usehex" value="0"> <bean:message key="no" />
				</td>
			</tr>
			<tr>
				<td class="altbg1">&nbsp;<bean:message key="a_system_database_export_usezip" /></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="usezip" value="1"> <bean:message key="a_system_database_export_zip_1" /><br/>
					<input class="radio" type="radio" name="usezip" value="2"> <bean:message key="a_system_database_export_zip_2" /><br/>
					<input class="radio" type="radio" name="usezip" value="0" checked> <bean:message key="a_system_database_export_zip_3" />
				</td>
			</tr>
			<tr>
				<td class="altbg1">&nbsp;<bean:message key="a_system_database_export_filename" /></td>
				<td class="altbg2"><input type="text" size="40" name="filename" value="${randName}"> .sql</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="exportsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />