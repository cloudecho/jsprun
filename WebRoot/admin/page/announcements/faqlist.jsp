<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_faq"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=faqlist">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" 	class="tableborder">
		<tr class="header">
			<td><input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form)"><bean:message key="del"/></td>
			<td><bean:message key="a_other_faq_thread"/></td>
			<td><bean:message key="display_order"/></td>
			<td><bean:message key="a_other_faq_sortup"/></td>
			<td><bean:message key="detail"/></td>
		</tr>
	<c:forEach items="${valueObject.allFaqList}" var="faqInfo">
		<tr class="altbg1">
			<td class="altbg1"><input type="hidden" name="fid" value="${faqInfo.id }"><input class="checkbox" type="checkbox" name="delete" value="${faqInfo.id }" ${faqInfo.ableToDelete?"":"disabled"}></td>
			<td class="altbg2" style='${faqInfo.topper?"": "padding-left:45px" }'><input type="hidden" name="title_h" value="${faqInfo.title }"><input type="text" size="30" name="title" value="${faqInfo.title }" maxlength="50"></td>
			<td class="altbg1"><input type="hidden" size="3" name="displayorder_h" value="${faqInfo.displayorder }"><input type="text" size="3" name="displayorder" value="${faqInfo.displayorder }"></td>
			<td class="altbg2">${faqInfo.topperTitle}	</td>
			<td class="altbg1">[<a href="admincp.jsp?action=faqdetail&id=${faqInfo.id }"><bean:message key="detail"/></a>]</td>
		</tr>
	</c:forEach>
		<tr class="altbg1" align="center">
			<td><bean:message key="add_new"/></td>
			<td><input type="text" size="30" name="newtitle" maxlength="50"></td>
			<td><input type="text" size="3" name="newdisplayorder"></td>
			<td>
				<select name="newfpid">
					<option value="0"><bean:message key="none"/></option>
					<c:forEach items="${valueObject.topperFaqList}" var="topperFaq">
						<option value="${topperFaq.id }">${topperFaq.title }</option>
					</c:forEach>
				</select>
			</td>
			<td></td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="faqsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />