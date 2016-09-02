<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_post_attachtypes"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<div style="float: left; margin-left: 0px; padding-top: 8px">
				<a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a>
			</div>
			<div style="float: right; margin-right: 4px; padding-bottom: 9px">
				<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a>
			</div>
		</td>
	</tr>
	<tbody id="menu_tip" style="display: ">
		<tr>
			<td>
				<bean:message key="a_post_attachtypes_tips"/>
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=attachtypes&batch=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="8%">
				<input class="checkbox" type="checkbox" name="chkall" class="header" onclick="checkall(this.form)">
				<bean:message key="del"/>
			</td>
			<td>
				<bean:message key="a_post_attachtypes_ext"/>
			</td>
			<td>
				<bean:message key="a_post_attachtypes_maxsize"/>
			</td>
		</tr>
			<c:forEach var="a" items="${attatypelist}">
				<tr align="center">
					<td class="altbg1">
						<input class="checkbox" type="checkbox" name="delete[]" value="${a.id}">
					</td>
					<td class="altbg2">
						<input type="text" size="10" name="extension[${a.id}]" value="${a.extension}" maxlength="12" onchange="extensionArray.value=extensionArray.value+this.name+','" />
					</td>
					<td class="altbg1">
						<input type="text" size="15" name="maxsize[${a.id}]" value="${a.maxsize}" maxlength="9" onchange="maxsizeArray.value=maxsizeArray.value+this.name+','" />
					</td>
				</tr>
			</c:forEach>
		<input type="hidden" name="extensionArray" value="" />
		<input type="hidden" name="maxsizeArray" value="" />
		<tr class="altbg1">
			<td align="center">
				<bean:message key="add_new"/>
			</td>
			<td align="center">
				<input type="text" size="10" name="newextension" value="" maxlength="12">
			</td>
			<td align="center">
				<input type="text" size="15" name="newmaxsize" value="" maxlength="9">
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="typesubmit" value="<bean:message key="submit"/>">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />