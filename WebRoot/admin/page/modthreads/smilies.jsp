<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
 <tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_post_smilies"/></td></tr>
</table><br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr><td><bean:message key="a_post_smileytypes_tips"/></td></tr>
	</tbody>
</table><br />
<form method="post" action="admincp.jsp?action=smilies&batch=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="48"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form)"><bean:message key="del" /></td>
			<td><bean:message key="a_post_smilies_type" /></td>
			<td><bean:message key="display_order" /></td>
			<td><bean:message key="ubiety_directory" /></td>
			<td><bean:message key="a_post_smilies_nums" /></td>
			<td><bean:message key="export" /></td>
			<td><bean:message key="edit" /></td>
		</tr>
		<c:forEach var="i" items="${datelist}">
			<input type="hidden" name="typeid" value="${i.typeid}">
			<tr align="center">
				<td class="altbg1">
					<c:choose>
						<c:when test="${i.totalCount>0}"><input class="checkbox" type="checkbox" name="delete[]" value="${i.typeid}" disabled></c:when>
						<c:otherwise><input class="checkbox" type="checkbox" name="delete[]" value="${i.typeid}"></c:otherwise>
					</c:choose>
				</td>
				<td class="altbg2"><input type="text" name="namenew[${i.typeid}]" value="${i.name}" size="15" maxlength="20"></td>
				<td class="altbg1"><input type="text" name="displayordernew[${i.typeid}]" value="${i.displayorder}" size="2" maxlength="2"></td>
				<td class="altbg2">./images/smilies/${i.directory}</td>
				<td class="altbg1">${i.totalCount}</td>
				<td class="altbg2"><a href="admincp.jsp?action=smilies&export=${i.typeid}">[<bean:message key="download" />]</a></td>
				<td class="altbg1"><a href="admincp.jsp?action=smilies&search=yes&edit=${i.typeid}&directory=${i.directory}">[<bean:message key="detail" />]</a></td>
			</tr>
		</c:forEach>
		<logic:empty name="showList">
			<tr>
				<td class="altbg1"><bean:message key="add_new" /></td>
				<td class="altbg2" colspan="6"><bean:message key="a_post_smiliesupload_tips" /></td>
			</tr>
		</logic:empty>
		<bean:size id="size" collection="${showList}" scope="page" />
		<input type="hidden" name="showcount" value="${size}" />
		<logic:notEmpty name="showList">
			<c:forEach var="s" items="${showList}" varStatus="x">
				<c:choose>
					<c:when test="${x.count==1}">
						<tr>
							<td class="altbg1"><bean:message key="add_new" /></td>
							<td class="altbg2"><input type="text" name="newname[${x.count}]" size="15" value="" maxlength="20"></td>
							<td class="altbg1"><input type="text" name="newdisplayorder[${x.count}]" size="2" value="" maxlength="2"></td>
							<td class="altbg2">./images/smilies/${s} <input type="hidden" name="newdirectory[${x.count}]" value="${s}"></td>
							<td colspan="3">&nbsp;</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="altbg1">&nbsp;</td>
							<td class="altbg2"><input type="text" name="newname[${x.count }]" size="15" maxlength="20"></td>
							<td class="altbg1"><input type="text" name="newdisplayorder[${x.count }]" maxlength="2" size="2"></td>
							<td class="altbg2">./images/smilies/${s} <input type="hidden" name="newdirectory[${x.count }]" value="${s }"></td>
							<td colspan="3">&nbsp;</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</logic:notEmpty>
	</table><br />
	<center>
		<input class="button" type="submit" name="smiliessubmit" value="<bean:message key="submit"/>">
	</center>
</form><br />
<form method="post" action="admincp.jsp?action=smilies">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><bean:message key="a_post_smilies_import" /></td></tr>
		<tr><td class="altbg1"><div align="center"><textarea name="smiliesdata" cols="80" rows="8"></textarea><br /></div></td></tr>
	</table><br />
	<center>
		<input class="button" type="submit" name="importsubmit" value="<bean:message key="submit"/>">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />