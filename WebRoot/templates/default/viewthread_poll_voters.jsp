<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<c:choose>
	<c:when test="${param.inajax==null}">
	<jsp:include flush="true" page="header.jsp" />
	<div id="nav">
		<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <bean:message key="poll_show_voter"/>
	</div>
	<div class="pages_btns">
		${multi.multipage}
	</div>
	<div class="mainbox">
		<h1><bean:message key="poll_view_voters"/></h1>
		<table summary="tagname" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<th>
					<ul class="userlist">
						<c:choose>
							<c:when test="${memberslist==null}"> <bean:message key="none"/> </c:when>
							<c:otherwise>
								<c:forEach items="${memberslist}" var="member">
									<li><a href="space.jsp?uid=${member.uid}" target="_blank">${member.username}</a></li>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</ul>
					</th>
				</tr>
			<tbody>
		</table>
	</div>
	<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
	<jsp:include flush="true" page="footer.jsp" />
	</c:when>
	<c:otherwise>
		<ul class="userlist">
		<c:choose>
			<c:when test="${memberslist==null}">
				<bean:message key="none"/>
			</c:when>
			<c:otherwise>
				<c:forEach items="${memberslist}" var="member">
					<li><a href="space.jsp?uid=${member.uid}" target="_blank">${member.username}</a></li>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</ul>
	<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
	</c:otherwise>
</c:choose>
