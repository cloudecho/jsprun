<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_advertisement"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=adv">
	<input type="hidden" name="step" value="1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><bean:message key="a_other_adv_add"/></td></tr>
		<tr>
			<td class="category">
				<bean:message key="a_other_adv_title"/><input style="vertical-align: middle" type="text" name="title" value="" size="25" maxlength="50"> &nbsp;&nbsp; <bean:message key="a_other_adv_edit_style"/>
				<select style="vertical-align: middle" name="style">
					<option value="code"><bean:message key="a_other_adv_style_code"/></option>
					<option value="text"><bean:message key="a_other_adv_style_text"/></option>
					<option value="image"><bean:message key="a_post_smilies_edit_image"/></option>
					<option value="flash"><bean:message key="a_other_adv_style_flash"/></option>
				</select>
				&nbsp;&nbsp;
				<select onchange="if(this.options[this.selectedIndex].value) {this.form.submit()}" style="vertical-align: middle" name="type">
					<option value=""><bean:message key="a_other_adv_type"/></option>
					<option value="headerbanner"><bean:message key="a_other_adv_type_headerbanner"/></option>
					<option value="footerbanner"><bean:message key="a_other_adv_type_footerbanner"/></option>
					<option value="text"><bean:message key="a_other_adv_type_text"/></option>
					<option value="thread"><bean:message key="a_other_adv_type_thread"/></option>
					<option value="interthread"><bean:message key="a_other_adv_type_interthread"/></option>
					<option value="float"><bean:message key="a_other_adv_type_float"/></option>
					<option value="couplebanner"><bean:message key="a_other_adv_type_couplebanner"/></option>
					<option value="intercat"><bean:message key="a_other_adv_type_intercat"/></option>
				</select>
			</td>
		</tr>
	</table>
	<br />
</form>
<form method="post" action="admincp.jsp?action=adv" onsubmit="if(this.title.value=='<bean:message key="a_other_adv_inputtitle"/>') this.title.value=''">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>${multi.multipage}</td>
			<td style="text-align: right;">
				<input style="vertical-align: middle" type="text" name="title" value="<c:choose><c:when test="${empty valueObject.queryTitle }"><bean:message key="a_other_adv_inputtitle"/></c:when><c:otherwise>${valueObject.queryTitle}</c:otherwise></c:choose>" size="15" onclick="this.value=''"> &nbsp;&nbsp;
				<select style="vertical-align: middle" name="starttime">
					<option value=""><bean:message key="start_time"/></option>
					<option value="0" ${valueObject.queryTime == '0'?"selected":""}><bean:message key="all"/></option>
					<option value="86400" ${valueObject.queryTime == '86400'?"selected":""}><bean:message key="1_day"/></option>
					<option value="604800" ${valueObject.queryTime == '604800'?"selected":""}><bean:message key="7_day"/></option>
					<option value="2592000" ${valueObject.queryTime == '2592000'?"selected":""}><bean:message key="30_day"/></option>
					<option value="7776000" ${valueObject.queryTime == '7776000'?"selected":""}><bean:message key="90_day"/></option>
					<option value="15552000" ${valueObject.queryTime == '15552000'?"selected":""}><bean:message key="180_day"/></option>
					<option value="31536000" ${valueObject.queryTime == '31536000'?"selected":""}><bean:message key="365_day"/></option>
				</select>
				&nbsp;&nbsp;
				<select style="vertical-align: middle" name="type">
					<option value=""><bean:message key="a_other_adv_type"/></option>
					<option value="0" ${valueObject.queryType == '0'?"selected":""}><bean:message key="all"/></option>
					<option value="headerbanner" ${valueObject.queryType == 'headerbanner'?"selected":""}><bean:message key="a_other_adv_type_headerbanner"/></option>
					<option value="footerbanner" ${valueObject.queryType == 'footerbanner'?"selected":""}><bean:message key="a_other_adv_type_footerbanner"/></option>
					<option value="text" ${valueObject.queryType == 'text'?"selected":""}><bean:message key="a_other_adv_type_text"/></option>
					<option value="thread" ${valueObject.queryType == 'thread'?"selected":""}><bean:message key="a_other_adv_type_thread"/></option>
					<option value="interthread" ${valueObject.queryType == 'interthread'?"selected":""}><bean:message key="a_other_adv_type_interthread"/></option>
					<option value="float" ${valueObject.queryType == 'float'?"selected":""}><bean:message key="a_other_adv_type_float"/></option>
					<option value="couplebanner" ${valueObject.queryType == 'couplebanner'?"selected":""}><bean:message key="a_other_adv_type_couplebanner"/></option>
					<option value="intercat" ${valueObject.queryType == 'intercat'?"selected":""}><bean:message key="a_other_adv_type_intercat"/></option>
				</select>
				<select style="vertical-align: middle" name="orderby">
					<option value=""><bean:message key="a_other_adv_orderby"/></option>
					<option value="starttime" ${valueObject.queryOrderby == 'starttime'?"selected":""}><bean:message key="a_other_adv_addtime"/></option>
					<option value="type" ${valueObject.queryOrderby == 'type'?"selected":""}><bean:message key="a_other_adv_type"/></option>
					<option value="displayorder" ${valueObject.queryOrderby == 'displayorder'?"selected":""}><bean:message key="display_order"/></option>
				</select>
				&nbsp;&nbsp;
				<input class="button" type="submit" name="searchsubmit" value="<bean:message key="search"/>" style="vertical-align: middle">
			</td>
		</tr>
	</table>
</form>
<form method="post" action="admincp.jsp?action=adv">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="3%" nowrap><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form,'delete')"><bean:message key="del"/></td>
			<td width="5%" nowrap><bean:message key="available"/></td>
			<td width="8%" nowrap><bean:message key="display_order"/></td>
			<td width="15%" nowrap><bean:message key="subject"/></td>
			<td width="12%" nowrap><bean:message key="type"/></td>
			<td width="5%" nowrap><bean:message key="a_other_adv_style"/></td>
			<td width="10%" nowrap><bean:message key="start_time"/></td>
			<td width="10%" nowrap><bean:message key="end_time"/></td>
			<td width="20%" nowrap><bean:message key="a_other_adv_targets"/></td>
			<td width="6%" nowrap><bean:message key="edit"/></td>
		</tr>
	<c:forEach items="${valueObject.advertisementList}" var="advertisement">
		<c:choose>
		<c:when test="${advertisement.overdue}">
		<c:set var="line_through" value="text-decoration: line-through" scope="page"></c:set>
		</c:when>
		<c:otherwise>
		<c:set var="line_through" value="" scope="page"></c:set>
		</c:otherwise>
		</c:choose>
		<tr align="center" >
			<td class="altbg1"><input type="hidden" name="id" value="${advertisement.id }"><input class="checkbox" type="checkbox" name="delete" value="${advertisement.id }"></td>
			<td class="altbg2"><input class="checkbox" type="checkbox" name="available${advertisement.id }" value="1" ${advertisement.userable?"checked":""} ></td>
			<td class="altbg1"><input type="text" size="2" name="displayorder" value="${advertisement.displayorder }"></td>
			<td class="altbg2"><input type="text" size="15" name="title" value="${advertisement.title }" maxlength="50"></td>
			<td class="altbg1" style="${line_through}"><c:if test="${advertisement.type != ''}" ><bean:message key="${advertisement.type}" /> </c:if></td>
			<td class="altbg2" style="${line_through}"><c:if test="${advertisement.style != ''}" ><bean:message key="${advertisement.style}" /> </c:if></td>
			<td class="altbg1" style="${line_through}">${advertisement.starttime}</td>
			<td class="altbg2" style="${line_through}">
			<c:choose>
			<c:when test="${advertisement.endtimeExist}">${advertisement.endtime}</c:when>
			<c:otherwise><bean:message key="unlimited" /></c:otherwise>
			</c:choose></td>
			<td class="altbg1" style="${line_through}">${advertisement.targets}</td>
			<td class="altbg2" style="${line_through}"><a href="admincp.jsp?action=advedit&advid=${advertisement.id}">[<bean:message key="detail"/>]</a></td>
		</tr>
	</c:forEach>
	</table>
	${multi.multipage}<br />
	<center><input class="button" type="submit" name="advsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />