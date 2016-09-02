<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_faq"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=faqdetail&id=${faq.id }">
	<input type="hidden" name="fpid" value="${faq.fpid }">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="03429e38aba788e5"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_other_faq_edit"/><a href="###" onclick="collapse_change('a45b0b68771fc480')"><img id="menuimg_a45b0b68771fc480" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_03429e38aba788e5" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_other_faq_title"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="newtitle" value="${faq.title }" maxlength="50"></td>
			</tr>
		<c:if test="${faq.fpid >0}">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_other_faq_sortup"/></b></td>
				<td class="altbg2">
					<select name="newfpid">
						<option value="0"><bean:message key="none"/></option>
					<c:forEach items="${faqs}" var="obj">
						<option value="${obj.id}" ${faq.fpid == obj.id? "selected":""} >${obj.title }</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_other_faq_identifier"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_faq_identifier_comment"/></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="newidentifier" value="${faq.identifier }" maxlength="20"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_other_faq_keywords"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_faq_keywords_comment"/></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="newkeyword" value="${faq.keyword }" maxlength="50"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_other_faq_content"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_faq_content_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('newmessage', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('newmessage', 0)">
					<br />
					<textarea rows="6" name="newmessage" id="newmessage" cols="50">${faq.message }</textarea>
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<br />
	<center><input class="button" type="submit" name="detailsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />