<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<form method="post" action="pm.jsp?action=archive&search=yes" target="_blank">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox formbox">
		<h1><bean:message key="pm_archive"/></h1>
		<jsp:include flush="true" page="pm_navbar.jsp" />
		<table summary="<bean:message key="pm_archive"/>" cellspacing="0" cellpadding="0">
			<tr>
				<th> <bean:message key="pm_folders"/> </th>
				<td>
					<label> <input type="radio" name="folder" value="inbox" checked="checked" /> <bean:message key="pm_inbox"/> </label>
					<label> <input type="radio" name="folder" value="outbox" /> <bean:message key="pm_outbox"/> </label>
				</td>
			</tr>
			<tr>
				<th> <bean:message key="pm_archive_from"/> </th>
				<td>
					<select name="days">
						<option value="86400" selected="selected">
							<bean:message key="pm_archive_from_1_days"/>
						</option>
						<option value="172800">
							<bean:message key="pm_archive_from_2_days"/>
						</option>
						<option value="432000">
							<bean:message key="pm_archive_from_7_days"/>
						</option>
						<option value="1296000">
							<bean:message key="pm_archive_from_30_days"/>
						</option>
						<option value="5184000">
							<bean:message key="pm_archive_from_90_days"/>
						</option>
						<option value="8640000">
							<bean:message key="pm_archive_from_180_days"/>
						</option>
						<option value="31536000">
							<bean:message key="pm_archive_from_365_days"/>
						</option>
						<option value="0">
							<bean:message key="search_any_date"/>
						</option>
					</select>
					<label> <input type="radio" name="newerolder" value="0" checked="checked" /> <bean:message key="search_newer"/> </label>
					<label> <input type="radio" name="newerolder" value="1" /> <bean:message key="search_older"/> </label>
				</td>
			</tr>

			<tr>
				<th> <label for="amount"><bean:message key="pm_archive_amount"/></label> </th>
				<td>
					<select id="amount" name="amount">
						<option value="10" selected="selected">10</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="40">40</option>
						<option value="50">50</option>
						<option value="0"><bean:message key="all"/></option>
					</select>
				</td>
			</tr>
			<tr>
				<th> &nbsp; </th>
				<td> <label> <input class="checkbox" type="checkbox" name="delete" value="1" /> <bean:message key="pm_archive_delete"/> </label> </td>
			</tr>
			<tr>
				<th> &nbsp; </th>
				<td> <button type="submit" class="submit" name="archivesubmit" value="true"> <bean:message key="pm_archive"/> </button> </td>
			</tr>
		</table>
	</div>
</form>