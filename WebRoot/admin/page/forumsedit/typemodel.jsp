<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<form method="post" action="admincp.jsp?action=typemodel">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" id="typelist">
		<tr class="header"><td colspan="7"><bean:message key="a_forum_threadtype_models"/></td></tr>
		<tr class="category" align="center">
			<td width="15%"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form,'delete')"><bean:message key="del"/></td>
			<td><bean:message key="name"/></td>
			<td><bean:message key="display_order"/></td>
			<td><bean:message key="edit"/></td>
		</tr>
		<c:forEach items="${typemodels}" var="typemodel">
		<tr align="center">
			<td class="altbg1"><input class="checkbox" type="checkbox" name="delete" value="${typemodel.id}" ${typemodel.type==1?"disabled":""}></td>
			<td class="altbg1"><input type="text" name="name[${typemodel.id}]" value="${typemodel.name}" maxlength="20"></td>
			<td class="altbg1"><input type="text" size="10" name="displayorder[${typemodel.id}]" value="${typemodel.displayorder}"></td>
			<td class="altbg1"><a href="admincp.jsp?action=modeldetail&modelid=${typemodel.id}">[<bean:message key="detail"/>]</a></td>
		</tr>
		</c:forEach>
		<tr align="center" class="altbg1">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" name="newname" maxlength="20"></td>
			<td><input type="text" size="10" name="newdisplayorder" value="0"></td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<center><input class="button" type="submit" name="modelsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />