<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>

<h1><bean:message key="my_buddylist"/></h1>
<form method="post" action="my.jsp?item=buddylist">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table summary="<bean:message key="my_buddylist"/>" cellspacing="0" cellpadding="0" width="100%">
		<thead><tr><td class="selector"><bean:message key="del"/></td><td class="user"><bean:message key="username"/></td><td><bean:message key="my_buddylist_note"/></td><td class="time"><bean:message key="my_buddylist_time"/></td></tr></thead>
		<tbody>
			<c:forEach items="${buddylists}" var="buddylist"><tr>
				<td class="selector"><input class="checkbox" type="checkbox" name="delete" value="${buddylist.buddyid}" /></td>
				<td><a href="space.jsp?uid=${buddylist.buddyid}" target="_blank">${buddylist.username}</a></td>
				<td><input type="text" name="description[${buddylist.buddyid}]" style="width: 375px"  maxlength="255" value="${buddylist.description}"/></td>
				<td style="width: 200px">${buddylist.dateline}</td>
			</tr></c:forEach>
		</tbody>
		<tr><td class="selector"></td><td style="width: 200px"><bean:message key="add_new"/><input type="text" name="newbuddy" size="15" /></td><td style="width: 55%"><input type="text" name="newdescription" style="width: 375px" /></td><td>&nbsp;</td></tr>
		<tr class="btns"><td class="selector"></td><td colspan="3"><button class="submit" type="submit" name="buddysubmit" id="buddysubmit" value="true"><bean:message key="submitf"/></button></td></tr>
	</table>
</form>