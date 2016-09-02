<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp" />
<div id="foruminfo">
	<div id="headsearch">
		<form method="get" action="tag.jsp">
			<input type="text" name="name" /> &nbsp;<button type="submit"><bean:message key="search"/></button>
		</form>
	</div>
	<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <a href="tag.jsp"><bean:message key="a_post_tag"/></a></div>
</div>
<div class="mainbox">
	<h1><bean:message key="a_post_tags_hot"/></h1>
	<ul class="taglist">
	<c:forEach items="${hottaglist}" var="hottag"><li><a href="tag.jsp?name=${hottag.tagnameenc}" target="_blank">${hottag.tagname}</a><em>(${hottag.total})</em></li></c:forEach>
	<c:if test="${empty hottaglist}"><li><bean:message key="tag_nofound"/></li></c:if>
	</ul>
</div>
<div class="mainbox">
	<h3><bean:message key="randtags"/></h3>
	<ul class="taglist">
	<c:forEach items="${randtaglist}" var="randtag"><li><a href="tag.jsp?name=${randtag.tagnameenc}" target="_blank">${randtag.tagname}</a><em>(${randtag.total})</em></li></c:forEach>
	<c:if test="${empty randtaglist}"><li><bean:message key="tag_nofound"/></li></c:if>
	</ul>
</div>
<jsp:include flush="true" page="footer.jsp" />