<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp" />
<div id="foruminfo"><div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; 
<c:choose>
	<c:when test="${type!='birthdays'}"><bean:message key="member_list"/></c:when>
	<c:otherwise><bean:message key="todays_birthdays_members"/></c:otherwise>
</c:choose>
</div></div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<div class="mainbox">
	<h3><c:choose><c:when test="${type!='birthdays'}"><bean:message key="member_list"/></c:when><c:otherwise><bean:message key="todays_birthdays_members"/></c:otherwise></c:choose></h3>
	<table summary="<c:choose><c:when test="${type!='birthdays'}"><bean:message key="member_list"/></c:when><c:otherwise><bean:message key="todays_birthdays_members"/></c:otherwise></c:choose>" cellspacing="0" cellpadding="0">
		<thead><tr><td><a href="member.jsp?action=list&order=username"><bean:message key="username"/></a></td><td><bean:message key="a_setting_uid"/></td><td><a href="member.jsp?action=list&order=gender"><bean:message key="gender"/></a></td><td><a href="member.jsp?action=list&order=regdate"><bean:message key="regdate"/></a></td><td><bean:message key="lastvisit"/></td><td><bean:message key="a_setting_posts"/></td><c:choose><c:when test="${type!='birthdays'}"><td><a href="member.jsp?action=list&order=credits"><bean:message key="credits"/></a></td></c:when><c:otherwise><td><a href="member.jsp?action=list&type=birthdays"><bean:message key="todays_birthdays"/></a></td></c:otherwise></c:choose></tr></thead>
		<tbody>
		<c:forEach items="${memberList}" var="member"><tr><td><a href="space.jsp?uid=${member.uid}" class="bold">${member.username}</a></td><td >${member.uid}</td><td><c:choose><c:when test="${member.gender==1}"><bean:message key="a_member_edit_gender_male"/></c:when><c:when test="${member.gender==2}"><bean:message key="a_member_edit_gender_female"/></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td><td>${member.regdate}</td><td>${member.lastvisit}</td><td>${member.posts}</td><td>${type=='birthdays'?  member.bday : member.credits}</td></tr></c:forEach>
		</tbody>
	</table>
</div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<c:if test="${type!='birthdays'}"><div id="footfilter" class="box">
	<form method="post" action="member.jsp?action=list">
		<input type="hidden" name="formhash" value="${formhash}" />
		<input type="text" size="15" name="srchmem" />
		&nbsp;<button type="submit"><bean:message key="search"/></button>
	</form>
	<bean:message key="a_other_adv_orderby"/>: <a href="member.jsp?action=list&amp;order=regdate"><bean:message key="regdate"/></a> - <a href="member.jsp?action=list&amp;order=username"><bean:message key="username"/></a> - <a href="member.jsp?action=list&amp;order=credits"><bean:message key="credits"/></a> - <a href="member.jsp?action=list&amp;order=gender"><bean:message key="gender"/></a> - <a href="member.jsp?action=list&amp;type=admins"><bean:message key="admin_status"/></a>
</div></c:if>
<jsp:include flush="true" page="footer.jsp" />