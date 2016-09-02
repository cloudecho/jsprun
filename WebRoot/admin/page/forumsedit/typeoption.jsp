<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="a_forum_threadtype_infotypes_option"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=typeoption&typeid=${typeid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="classid" value="${classid}">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="6"><bean:message key="a_forum_threadtype_manage"/></td></tr>
		<tr class="category" align="center">
			<td><c:forEach items="${upTypeoptions}" var="upTypeoption">
			<c:if test="${upTypeoption.optionid==classid}"><c:set var="title" value="${upTypeoption.title}" scope="page"/></c:if>
			<input class="button" type="button" value="${upTypeoption.title}" onclick="window.location='admincp.jsp?action=typeoption&classid=${upTypeoption.optionid}';">&nbsp;
			</c:forEach></td>
		</tr>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="6"><bean:message key="a_forum_threadtype_manage"/> - ${title}</td></tr>
		<tr class="category" align="center">
			<td width="45"><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form,'delete')"><bean:message key="del"/></td>
			<td><bean:message key="name"/></td>
			<td><bean:message key="a_forum_threadtype_variable"/></td>
			<td><bean:message key="type"/></td>
			<td><bean:message key="display_order"/></td>
			<td><bean:message key="edit"/></td>
		</tr>
		<c:forEach items="${typeoptions}" var="typeoption">
		<tr align="center">
			<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" value="${typeoption.optionid}"></td>
			<td class="altbg2"><input type="text" size="15" name="title[${typeoption.optionid}]" value="<jrun:dhtmlspecialchars value="${typeoption.title}"/>" maxlength="100"></td>
			<td class="altbg1">${typeoption.identifier}<input type="hidden" name="identifier[${typeoption.optionid}]" value="${typeoption.identifier}"></td>
			<td class="altbg2">${types[typeoption.type]}</td>
			<td class="altbg1"><input type="text" size="2" name="displayorder[${typeoption.optionid}]" value="${typeoption.displayorder}"></td>
			<td class="altbg2"><a href="admincp.jsp?action=optiondetail&optionid=${typeoption.optionid}">[<bean:message key="detail"/>]</a></td>
		</tr>
		</c:forEach>
		<tr align="center" class="altbg1">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" size="15" name="newtitle" maxlength="100"></td>
			<td><input type="text" size="15" name="newidentifier" maxlength="40"></td>
			<td>
				<select name="newtype">
					<option value="number"><bean:message key="a_forum_threadtype_edit_number"/></option>
					<option value="text" selected><bean:message key="a_forum_threadtype_edit_text"/></option>
					<option value="textarea"><bean:message key="a_forum_threadtype_edit_textarea"/></option>
					<option value="radio"><bean:message key="a_forum_threadtype_edit_radio"/></option>
					<option value="checkbox"><bean:message key="a_forum_threadtype_edit_checkbox"/></option>
					<option value="select"><bean:message key="a_forum_threadtype_edit_select"/></option>
					<option value="calendar"><bean:message key="a_forum_threadtype_edit_calendar"/></option>
					<option value="email"><bean:message key="a_forum_threadtype_edit_email"/></option>
					<option value="image"><bean:message key="a_forum_threadtype_edit_image"/></option>
					<option value="url"><bean:message key="a_forum_threadtype_edit_url"/></option>
				</select>
			</td>
			<td><input type="text" size="2" name="newdisplayorder" value="0"></td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="typeoptionsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />