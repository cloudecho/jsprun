<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_magic_market"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=magicmarket">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="10"><bean:message key="menu_magic_market"/></td></tr>
		<tr align="center" class="category">
			<td width="6%"><input type="checkbox" name="chkall" class="checkbox" onclick="checkall(this.form)"><bean:message key="del"/></td>
			<td><bean:message key="name"/></td>
			<td><bean:message key="magics_seller"/></td>
			<td><bean:message key="price"/></td>
			<td><bean:message key="num"/></td>
			<td><bean:message key="magics_weight"/></td>
			<td width="40%"><bean:message key="description"/></td>
		</tr>
		<c:forEach items="${valueObject}" var="magicmarket_map">
			<input type="hidden" name="mid" value="${magicmarket_map.mid }">
			<tr align="center">
				<td class="altbg1"><input type="checkbox" class="checkbox" name="delete" value="${magicmarket_map.mid }"></td>
				<td class="altbg2">${magicmarket_map.name }</td>
				<td class="altbg1">${magicmarket_map.username }</td>
				<td class="altbg2"><input type="text" size="5" name="price" value="${magicmarket_map.price }"></td>
				<td class="altbg1"><input type="text" size="5" name="num" value="${magicmarket_map.num }"></td>
				<td class="altbg2">${magicmarket_map.allweight }</td>
				<td class="altbg1">${magicmarket_map.description }</td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<center><input type="submit" class="button" name="marketsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />