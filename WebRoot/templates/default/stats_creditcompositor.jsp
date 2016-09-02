<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_credits_rank" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">
			<h1>
				<bean:message key="stats_credits_rank" />
			</h1>
			<table summary="<bean:message key="stats_credits_rank" />" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
					<c:forEach items="${valueObject.creditNameTopList}" var="creditName">
						<td colspan="2">
							${creditName } <bean:message key="stats_rank" />
						</td>
					</c:forEach>
					</tr>
				</thead>
				<c:forEach items="${valueObject.lineObjectTopList}" var="lineObjectTop">
					<tr>
					<c:forEach items="${lineObjectTop.creditInfoList}" var="creditInfoTop">
					<td><a href="space.jsp?username=<jrun:encoding value="${creditInfoTop.username}"/>" target="_blank">${creditInfoTop.username}</a></td>
					<td align="right">
						${creditInfoTop.creditNum }
					</td>
					</c:forEach>
					</tr>
				</c:forEach>
			</table>
			<br />
			<table summary="<bean:message key="stats_credits_rank" />" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
					<c:forEach items="${valueObject.creditNameDownList}" var="creditNameDown">
						<td colspan="2">
							${creditNameDown } <bean:message key="stats_rank" />
						</td>
					</c:forEach>
					</tr>
				</thead>
				<c:forEach items="${valueObject.downMapList}" var="downMap">
					<tr>
					<c:forEach items="${valueObject.creditNameDownList}" var="creditNameDown">
					<td><a href="space.jsp?username=<jrun:encoding value="${downMap[creditNameDown].username}"/>" target="_blank">${downMap[creditNameDown].username }</a></td>
					<td align="right">
						${downMap[creditNameDown].creditNum }
					</td>
					</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />
