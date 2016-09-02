<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_agent" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">
			<h1>
				<bean:message key="stats_agent" />
			</h1>
			<table summary="<bean:message key="stats_agent" />" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="stats_os" />
						</td>
					</tr>
				</thead>
				<c:forEach items="${valueObject.operatingSystemList}" var="operatingSystem">
				<tr>
					<th width="100">
						<strong><img src="${pageContext.request.contextPath }/images/stats/${operatingSystem.imageName }.gif" border="0"/> ${operatingSystem.information }</strong>
					</th>
					<td>
						<div class="optionbar">
							<div style="width:${operatingSystem.lineWidth }px">
								&nbsp;
							</div>
						</div>
						&nbsp;
						<strong>${operatingSystem.num }</strong> (${operatingSystem.numPercent }%)
					</td>
				</tr>
				</c:forEach>

				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="stats_browser" />
						</td>
					</tr>
				</thead>
				<c:forEach items="${valueObject.browserList}" var="browser">
					<tr>
					<th width="100">
						<strong><img src="${pageContext.request.contextPath }/images/stats/${browser.imageName }.gif" border="0"/> ${browser.information }</strong>
					</th>
					<td>
						<div class="optionbar">
							<div style="width:${browser.lineWidth }px">
								&nbsp;
							</div>
						</div>
						&nbsp;
						<strong>${browser.num }</strong> (${browser.numPercent }%)
					</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />