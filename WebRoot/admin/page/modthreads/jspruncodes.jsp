<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="post_jspruncode"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td>
				<div style="float:left; margin-left:0px; padding-top:8px">
					<a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a>
				</div>
				<div style="float:right; margin-right:4px; padding-bottom:9px">
					<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a>
				</div>
			</td>
		</tr>
		<tbody id="menu_tip" style="display:">
			<tr>
				<td>
					<bean:message key="a_post_jspruncodes_edit_tips"/>
				</td>
			</tr>
		</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=jspruncodes&batch=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="6">
				<bean:message key="a_post_jspruncodes_edit"/>
			</td>
		</tr>
		<tr align="center" class="category">
			<td width="5%">
				<input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form,'delete')">
				<bean:message key="del"/>
			</td>
			<td>
				<bean:message key="a_post_tag"/>
			<td>
				<bean:message key="a_post_jspruncodes_icon_file"/>
			</td>
			<td>
				<bean:message key="a_post_jspruncodes_icon"/>
			</td>
			<td>
				<bean:message key="available"/>
			</td>
			<td>
				<bean:message key="edit"/>
			</td>
		</tr>
		<c:forEach var="code" items="${bbcodelist}">
			<tr align="center">
				<td class="altbg1">
					<input class="checkbox" type="checkbox" name="delete[]" value="${code.id}">
				</td>
				<td class="altbg2">
					<input type="text" size="15" name="tagnew[${code.id}]" value="${code.tag}" maxlength="100" onchange="updateTag.value = updateTag.value+this.name+','">
				</td>
				<td class="altbg1">
					<input type="text" size="25" name="iconnew[${code.id}]" value="${code.icon}" maxlength="255" onchange="updateIcon.value = updateIcon.value+this.name+','">
				</td>
				<td class="altbg2">
					<img src="images/common/${code.icon}" border="0">
				</td>
				<td class="altbg1">
					<c:choose>
						<c:when test="${code.available==1}">
							<input class="checkbox" type="checkbox" name="availablenew[${code.id}]" value="1" checked/>
						</c:when>
						<c:otherwise>
							<input class="checkbox" type="checkbox" name="availablenew[${code.id}]" value="1"/>
						</c:otherwise>
					</c:choose>
				</td>
				<td class="altbg2">
					<a href="admincp.jsp?action=jspruncodes&child=yes&edit=${code.id}">[<bean:message key="detail"/>]</a>
				</td>
			</tr>
		</c:forEach>
		<tr class="altbg1" align="center">
			<td>
				<bean:message key="add_new"/>
			</td>
			<td>
				<input type="text" size="15" name="newtag" maxlength="100">
			</td>
			<td>
				<input type="text" size="25" name="newicon" maxlength="255">
			</td>
			<td colspan="3">
				&nbsp;
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="bbcodessubmit" value="<bean:message key="submit"/>">
		<input type="hidden" name="hiddenSB" value="${hiddensb}" />
		<input type="hidden" name="updateTag" value="" />
		<input type="hidden" name="updateIcon" value="" />
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />