<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<form method="post" action="pm.jsp?action=search&to=searchpms">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox formbox">
		<h1><bean:message key="pm_search"/></h1>
		<jsp:include flush="true" page="pm_navbar.jsp" />
		<table summary="<bean:message key="pm_search"/>" cellspacing="0" cellpadding="0">
			<thead><tr><th><bean:message key="search"/></th><td>&nbsp;</td></tr></thead>
			<tr>
				<th><label for="srchtxt"><bean:message key="a_forum_templates_keyword"/></label></th>
				<td colspan="2">
					<input type="text" id="srchtxt" name="srchtxt" size="25" maxlength="40" />
					<div class="tips"><bean:message key="search_keywords_comment"/></div>
				</td>
			</tr>
			<tr>
				<th><label for="srchname"><bean:message key="pm_search_username"/></label></th>
				<td colspan="2">
					<input type="text" id="srchname" name="srchuname" size="25" maxlength="40" />
					<div class="tips"><bean:message key="pm_search_username_comment"/></div>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><button type="submit" class="submit" name="searchsubmit" value="true"><bean:message key="search"/></button></td>
			</tr>
			<thead><tr><th><bean:message key="search_option"/></th><td>&nbsp;</td></tr></thead>
			<tr>
				<th><label for="srchfolder"><bean:message key="search_range"/></label></th>
				<td>
					<select id="srchfolder" name="srchfolder">
						<option value="inbox"><bean:message key="pm_inbox"/></option>
						<option value="outbox"><bean:message key="pm_outbox"/></option>
						<option value="track"><bean:message key="a_system_invite_status_3"/></option>
					</select>
					<label><input type="radio" name="srchtype" value="title" checked="checked" /> <bean:message key="search_title"/></label>
					<label><input type="radio" name="srchtype" value="fulltext" /><bean:message key="search_fulltext"/></label>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<label><input type="checkbox" name="srchread" value="1" checked="checked" /> <bean:message key="pm_search_read"/></label>
					<label><input type="checkbox" name="srchunread" value="1" checked="checked" /> <bean:message key="pm_search_unread"/></label>
				</td>
			</tr>
			<tr>
				<th><label for="srchfrom"><bean:message key="search_time"/></label></th>
				<td>
					<select id="srchfrom" name="srchfrom">
						<option value="0"><bean:message key="search_any_date"/></option>
						<option value="86400"><bean:message key="pm_archive_from_1_days"/></option>
						<option value="172800"><bean:message key="pm_archive_from_2_days"/></option>
						<option value="432000"><bean:message key="pm_archive_from_7_days"/></option>
						<option value="1296000"><bean:message key="pm_archive_from_30_days"/></option>
						<option value="5184000"><bean:message key="pm_archive_from_90_days"/></option>
						<option value="8640000"><bean:message key="pm_archive_from_180_days"/></option>
						<option value="31536000"><bean:message key="pm_archive_from_365_days"/></option>
					</select>
					<label><input type="radio" name="before" value="0" checked="checked" /><bean:message key="search_newer"/></label>
					<label><input type="radio" name="before" value="1" /><bean:message key="search_older"/></label>
				</td>
			</tr>
			<tr>
				<th><label for="orderby"><bean:message key="search_orderby"/></label></th>
				<td>
					<select id="orderby" name="orderby"><option value="dateline"><bean:message key="pm_search_orderby_dateline"/></option><option value="msgfrom"><bean:message key="pm_search_orderby_from"/></option></select>
					<label><input type="radio" name="ascdesc" value="asc" /> <bean:message key="a_forum_edit_asc"/></label>
					<label><input type="radio" name="ascdesc" value="desc" checked="checked"/> <bean:message key="a_forum_edit_desc"/></label>
				</td>
			</tr>
			<tr><th>&nbsp;</th><td><button type="submit" class="submit" name="searchsubmit" value="true"><bean:message key="search"/></button></td></tr>
		</table>
	</div>
</form>