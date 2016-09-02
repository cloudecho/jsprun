<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="search"/></div>
<form method="post" action="search.jsp?action=search">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<div class="mainbox formbox">
	<span class="headactions"><a href="member.jsp?action=credits&view=search" target="_blank"><bean:message key="credits_policy_view"/></a></span>
	<h1><bean:message key="search"/></h1>
	<table summary="<bean:message key="search"/>" cellspacing="0" cellpadding="0">
		<tr><th><label for="srchtxt"><bean:message key="a_forum_templates_keyword"/></label></th><td><input type="text" id="srchtxt" name="srchtxt" size="45" maxlength="40" /> <c:if test="${settings.tagstatus==1}"><p>${hottaglist}</p></c:if></td><td><bean:message key="search_keywords_comment"/></td></tr>
		<tr><th><label for="srchname"><bean:message key="username"/></label></th><td><input type="text" id="srchname" name="srchuname" size="45" maxlength="40" /></td><td><bean:message key="search_username_comment"/></td></tr>
		<tr><th>&nbsp;</th><td><button class="submit" type="submit" name="searchsubmit" value="true"><bean:message key="search"/></button></td><td>&nbsp;</td></tr>
	</table>
	<table summary="<bean:message key="search_option"/>" cellspacing="0" cellpadding="0">
		<thead><tr><th><bean:message key="search_option"/></th><td>&nbsp;</td></tr></thead>
		<tr><th><bean:message key="search_mode"/></th><td><label><input type="radio" name="srchtype" onclick="orderbyselect(1)" value="title" checked="checked" /> <bean:message key="search_title"/></label> <label><input type="radio" name="srchtype" onclick="orderbyselect(1)" value="blog" /> <bean:message key="search_blog"/></label> <label><input type="radio" name="srchtype" onclick="orderbyselect(2)" value="trade" /> <bean:message key="search_trade"/></label> <label><input type="radio" name="srchtype" onclick="window.location=('search.jsp?srchtype=threadtype')" value="trade" /> <bean:message key="search_info"/></label> <label><input type="radio" name="srchtype" onclick="orderbyselect(1)" value="fulltext" ${usergroups.allowsearch!=2 ? "disabled" : ""}/> <bean:message key="search_fulltext"/></label></td></tr>
		<tr><td><bean:message key="search_thread_range"/></td><td><label><input type="radio" name="srchfilter" value="all" checked="checked"/> <bean:message key="threads_all"/></label> <label><input type="radio" name="srchfilter" value="digest"/> <bean:message key="search_thread_range_digest"/></label> <label><input type="radio" name="srchfilter" value="top"/> <bean:message key="search_thread_range_top"/></label></td></tr>
		<tbody id="specialtr1"><tr><td><bean:message key="usergroups_specialthread"/></td><td> <label><input type="checkbox" name="special[]" value="1" /> <bean:message key="threads_special_poll"/></label> <label><input type="checkbox" name="special[]" value="2" /> <bean:message key="threads_special_trade"/></label> <label><input type="checkbox" name="special[]" value="3" /> <bean:message key="threads_special_reward"/></label> <label><input type="checkbox" name="special[]" value="4" /> <bean:message key="threads_special_activity"/></label> <label><input type="checkbox" name="special[]" value="5" /> <bean:message key="special_5"/></label> <label><input type="checkbox" name="special[]" value="6" /> <bean:message key="special_6"/></label> </td></tr></tbody>
		<tbody id="specialtr2" style="display: none"><tr><td><bean:message key="search_tradetype"/></td><td><select name="srchtypeid"><option value=""><bean:message key="all"/></option></select></td></tr></tbody>
		<tr><th><label for="srchfrom"><bean:message key="search_time"/></label></th><td><select id="srchfrom" name="srchfrom"><option value="0"><bean:message key="search_any_date"/></option><option value="86400"><bean:message key="pm_archive_from_1_days"/></option><option value="172800"><bean:message key="pm_archive_from_2_days"/></option><option value="432000"><bean:message key="pm_archive_from_7_days"/></option><option value="1296000"><bean:message key="pm_archive_from_30_days"/></option><option value="5184000"><bean:message key="pm_archive_from_90_days"/></option><option value="8640000"><bean:message key="pm_archive_from_180_days"/></option><option value="31536000"><bean:message key="pm_archive_from_365_days"/></option></select> <label><input type="radio" name="before" value="0" checked="checked" /> <bean:message key="search_newer"/></label> <label><input type="radio" name="before" value="1" /> <bean:message key="search_older"/></label></td></tr>
		<tr><td><label for="orderby"><bean:message key="search_orderby"/></label></td><td><select id="orderby1" name="orderby"><option value="lastpost" selected="selected"><bean:message key="a_forum_edit_lastpost"/></option><option value="dateline"><bean:message key="a_forum_edit_starttime"/></option><option value="replies"><bean:message key="a_forum_edit_replies"/></option><option value="views"><bean:message key="a_forum_edit_views"/></option></select> <select id="orderby2" name="orderby" style="position: absolute; display: none" disabled><option value="dateline" selected="selected"><bean:message key="a_forum_edit_starttime"/></option><option value="price"><bean:message key="post_trade_price"/></option><option value="expiration"><bean:message key="trade_remaindays"/></option></select> <label><input type="radio" name="ascdesc" value="asc" /> <bean:message key="a_forum_edit_asc"/></label> <label><input type="radio" name="ascdesc" value="desc" checked="checked" /> <bean:message key="a_forum_edit_desc"/></label></td></tr>
		<tr><td valign="top"><label for="srchfid"><bean:message key="search_range"/></label></td><td><select id="srchfid" name="srchfid" multiple="multiple" size="10" style="width: 26em;"><option value="all" selected="selected"><bean:message key="search_all_forums"/></option><option value="all">&nbsp;</option>${forumselect}</select></td></tr>
		<tr><th>&nbsp;</th><td><button class="submit" type="submit" name="searchsubmit" value="true"><bean:message key="search"/></button></td></tr>
	</table>
</div>
</form>
<script type="text/javascript">
function orderbyselect(ordertype) {
	$('orderby1').style.display = 'none';
	$('orderby1').style.position = 'absolute';
	$('orderby1').disabled = true;
	$('specialtr1').style.display = 'none';
	$('orderby2').style.display = 'none';
	$('orderby2').style.position = 'absolute';
	$('orderby2').disabled = true;
	$('specialtr2').style.display = 'none';
	$('orderby' + ordertype).style.display = '';
	$('orderby' + ordertype).style.position = 'static';
	$('orderby' + ordertype).disabled = false;
	$('specialtr' + ordertype).style.display = '';
}
</script>
<jsp:include flush="true" page="footer.jsp" />