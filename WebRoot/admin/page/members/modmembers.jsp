<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_modmembers"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float: left; margin-left: 0px; padding-top: 8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float: right; margin-right: 4px; padding-bottom: 9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /></a></div></td></tr>
	<tbody id="menu_tip" style="display: ">
		<tr><td><bean:message key="a_member_moderate_tips"/></td></tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=modmembers&search=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_member_moderate_prune"/></td></tr>
		<tr>
			<td class="altbg1"><bean:message key="a_member_moderate_prune_submitmore"/></td>
			<td align="right" class="altbg2"><input type="text" name="submitmore" size="40" value="5"></td>
		</tr>
		<tr>
			<td class="altbg1"><bean:message key="a_member_moderate_prune_regbefore"/></td>
			<td align="right" class="altbg2"><input type="text" name="regbefore" size="40" value="30"></td>
		</tr>
		<tr>
			<td class="altbg1"><bean:message key="a_member_moderate_prune_modbefore"/></td>
			<td align="right" class="altbg2"><input type="text" name="modbefore" size="40" value="15"></td>
		</tr>
		<tr>
			<td class="altbg1"><bean:message key="a_member_search_regip"/></td>
			<td align="right" class="altbg2"><input type="text" name="regip" size="40"></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="prunesubmit" value="<bean:message key="submit"/>"></center>
</form>
<br />
<form method="post" action="admincp.jsp?action=modmembers&validate=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	${multi.multipage}
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="5"><bean:message key="menu_member_modmembers"/></td></tr>
		<tr class="category">
			<td colspan="5">
				<table cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td style="border: none">
							<input class="button" type="button" value="<bean:message key="moderate_all_invalidate"/>" onclick="checkalloption(this.form, 'invalidate')">&nbsp;
							<input class="button" type="button" value="<bean:message key="moderate_all_validate"/>" onclick="checkalloption(this.form, 'validate')">&nbsp;
							<input class="button" type="button" value="<bean:message key="all_delete"/>" onclick="checkalloption(this.form, 'delete')">&nbsp;
							<input class="button" type="button" value="<bean:message key="all_ignore"/>" onclick="checkalloption(this.form, 'ignore')">
						</td>
						<td align="right" style="border: none"><input class="checkbox" type="checkbox" name="sendemail" value="1" checked> <bean:message key="a_member_moderate_email"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr align="center" class="header">
			<td width="10%"><bean:message key="operation"/></td>
			<td width="25%"><bean:message key="a_member_edit_info"/></td>
			<td width="20%"><bean:message key="a_member_moderate_message"/></td>
			<td width="25%"><bean:message key="a_member_moderate_info"/></td>
			<td width="20%"><bean:message key="a_member_moderate_remark"/></td>
		</tr>
		<c:if test="${validatingList!=null}">
			<c:forEach items="${validatingList}" var="validate">
			<input type="hidden" name="uid" value="${validate.uid}">
				<tr class="smalltxt">
					<td class="altbg2">
						<input class="radio" type="radio" name="mod[${validate.uid}]" value="invalidate"> <bean:message key="invalidate"/><br />
						<input class="radio" type="radio" name="mod[${validate.uid}]" value="validate" checked> <bean:message key="validate"/><br />
						<input class="radio" type="radio" name="mod[${validate.uid}]" value="delete"> <bean:message key="delete"/><br />
						<input class="radio" type="radio" name="mod[${validate.uid}]" value="ignore"> <bean:message key="ignore"/>
					</td>
					<td class="altbg1">
						<b><a href="space.jsp?action=viewpro&uid=${validate.uid}" target="_blank">${validate.username}</a> </b>
						<br />
						<bean:message key="a_member_edit_regdate"/>
						<jrun:showTime timeInt="${validate.regdate}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
						<br />
						<bean:message key="a_member_edit_regip"/> ${validate.regip}
						<br />
						<bean:message key="a_member_edit_email"/> ${validate.email}
					</td>
					<td class="altbg2" align="center">
						<textarea rows="4" name="remarks" style="width: 95%; word-break: break-all" type="_moz">${validate.message}</textarea>
					</td>
					<td class="altbg1">
						<bean:message key="a_member_moderate_submit_times"/> ${validate.submittimes}
						<br />
						<bean:message key="a_member_moderate_submit_time"/>:
						<jrun:showTime timeInt="${validate.submitdate}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
						<br />
						<bean:message key="a_member_moderate_admin"/>: ${validate.admin}
						<br />
						<bean:message key="a_member_moderate_mod_time"/>:
						<jrun:showTime timeInt="${validate.moddate}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
					</td>
					<td class="altbg1" align="center">
						<textarea rows="4" name="remark[${validate.uid}]" style="width: 95%; word-break: break-all" type="_moz">${validate.remark}</textarea>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	${multi.multipage}
	<br />
	<center><input class="button" type="submit" name="modsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />