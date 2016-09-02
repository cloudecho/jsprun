<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp"/>
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="search"/></div>
<form method="post" action="search.jsp?action=threadtype">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<div class="mainbox formbox">
	<span class="headactions"><a href="member.jsp?action=credits&amp;view=search" target="_blank"><bean:message key="credits_policy_view"/></a></span>
	<h1><bean:message key="search_info_more"/></h1>
	<table summary="<bean:message key="search"/>" cellspacing="0" cellpadding="0">
		<tr><td style="border-bottom: 0px"><label for="typeid"><bean:message key="menu_threadtype"/></label></td><td style="border-bottom: 0px"><select name="typeid" onchange="ajaxget('post.jsp?action=threadtypes&typeid='+this.options[this.selectedIndex].value+'&operate=1&sid=${sid}', 'threadtypes', 'threadtypeswait')"><option value="0"><bean:message key="none"/></option><c:forEach items="${threadtype}" var="types"><option value="${types.typeid}"  ${typeid==(types.typeid)?"selected":""}>${types.name}</option></c:forEach></select> <span id="threadtypeswait"></span></td></tr>
		<tbody id="threadtypes"></tbody>
		<tr><td valign="top"><label for="srchfid"><bean:message key="search_range"/></label></td><td><select id="srchfid" name="srchfid" multiple="multiple" size="10" style="width: 26em;"><option value="all" ${empty param.srchfid?"selected":""}><bean:message key="search_all_forums"/></option><option value="">&nbsp;</option>${forumselect}</select></td></tr>
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
if(${typeid>0}){
	ajaxget('post.jsp?action=threadtypes&typeid=${typeid}&operate=1&inajax=1&sid=${sid}', 'threadtypes', 'threadtypeswait');
}
</script>
<jsp:include flush="true" page="footer.jsp" />