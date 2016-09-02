<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_magic"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td><ul><li><bean:message key="a_other_magics_tips"/></li></ul></td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=magic">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="10"><bean:message key="a_other_magics_edit"/></td></tr>
		<tr align="center" class="category">
			<td><input type="checkbox" name="chkall" class="checkbox" onclick="checkall(this.form,'delete')"><bean:message key="del"/></td>
			<td><bean:message key="display_order"/></td>
			<td><bean:message key="name"/></td>
			<td><bean:message key="type"/></td>
			<td><bean:message key="price"/></td>
			<td><bean:message key="num"/></td>
			<td><bean:message key="description"/></td>
			<td><bean:message key="a_other_magics_identifier"/></td>
			<td><bean:message key="available"/></td>
			<td><bean:message key="detail"/></td>
		</tr>
		<c:forEach items="${magiclist}" var="magic">
			<tr align="center">
				<td class="altbg1"><input type="checkbox" class="checkbox" name="delete" value="${magic.magicid}"></td>
				<td class="altbg2"><input type="text" size="3" name="displayorder_${magic.magicid}" value="${magic.displayorder}"></td>
				<td class="altbg1"><input type="text" size="10" name="name_${magic.magicid}" value="${magic.name}" maxlength="50"></td>
				<td class="altbg2"><a href="admincp.jsp?action=magic&typeid=${magic.type}"><c:choose><c:when test="${magic.type==1}"><bean:message key="magics_type_1"/></c:when><c:when test="${magic.type==2}"><bean:message key="magics_type_2"/></c:when><c:otherwise><bean:message key="a_other_magics_type_3"/></c:otherwise></c:choose></a></td>
				<td class="altbg1"><input type="text" size="5" name="price_${magic.magicid}" value="${magic.price}"></td>
				<td class="altbg2"><input type="text" size="5" name="num_${magic.magicid}" value="${magic.num}"></td>
				<td class="altbg1"><input type="text" size="25" name="description_${magic.magicid}" value="${magic.description}" maxlength="255"></td>
				<td class="altbg2"><input type="hidden" name="identifier" value="${magic.identifier}" maxlength="40">${magic.identifier}</td>
				<td class="altbg1">
				<c:choose>
					<c:when test="${empty magic.filename || empty magic.identifier}"><input type="checkbox" class="checkbox" name="available_${magic.magicid}" value="1" disabled="disabled"></c:when>
					<c:when test="${magic.available==1}"><input type="checkbox" class="checkbox" name="available_${magic.magicid}" value="1" checked="checked"></c:when>
					<c:otherwise><input type="checkbox" class="checkbox" name="available_${magic.magicid}" value="1"></c:otherwise>
				</c:choose>
				</td>
				<td class="altbg2"><a href="admincp.jsp?action=magicedit&magicid=${magic.magicid }">[<bean:message key="detail"/>]</a></td>
			</tr>
		</c:forEach>
		<tr class="altbg1" align="center">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" size="3" name="newdisplayorder"></td>
			<td><input type="text" size="10" name="newname"></td>
			<td>
				<select name="newtype">
					<option value="1" selected><bean:message key="magics_type_1"/></option>
					<option value="2"><bean:message key="magics_type_2"/></option>
					<option value="3"><bean:message key="a_other_magics_type_3"/></option>
				</select>
			</td>
			<td><input type="text" size="5" name="newprice"></td>
			<td><input type="text" size="5" name="newnum" maxlength="50"></td>
			<td><input type="text" size="25" name="newdescription" maxlength="255"></td>
			<td><input type="text" size="5" name="newidentifier" maxlength="40"></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<br />
	<center><input type="submit" class="button" name="magicsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />