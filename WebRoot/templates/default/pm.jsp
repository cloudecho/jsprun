<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp" />
<script src="include/javascript/viewthread.js" type="text/javascript"></script>
<script type="text/javascript">
zoomstatus = parseInt(${settings.zoomstatus});
lang['copy_to_cutedition'] = '<bean:message key="copy_to_cutedition"/>';
</script>
<div class="container">
	<div id="foruminfo">
		<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo;<bean:message key="pm"/></div>
	</div>
	<div class="content">
		<c:choose>
			<c:when test="${param.action==null||param.action==''}"><jsp:include flush="true" page="pm_folder.jsp" /></c:when>
			<c:when test="${param.action=='view'}"><jsp:include flush="true" page="pm_view.jsp" /></c:when>
			<c:when test="${param.action=='send'}"><jsp:include flush="true" page="pm_send.jsp" /></c:when>
			<c:when test="${param.action=='search'}">
				<c:if test="${param.searchid==null}"><jsp:include flush="true" page="pm_search.jsp" /></c:if>
				<c:if test="${param.searchid!=null}"><jsp:include flush="true" page="pm_search_result.jsp" /></c:if>
			</c:when>
			<c:when test="${param.action=='archive'}"><jsp:include flush="true" page="pm_archive.jsp" /></c:when>
			<c:when test="${param.action=='ignore'}"><jsp:include flush="true" page="pm_ignore.jsp" /></c:when>
		</c:choose>
	</div>
	<div class="side"><jsp:include flush="true" page="personal_navbar.jsp" /></div>
</div>
<jsp:include flush="true" page="footer.jsp" />