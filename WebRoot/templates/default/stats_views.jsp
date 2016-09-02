<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_views" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">
			<h1>
				<bean:message key="stats_views" />
			</h1>
			<table summary="<bean:message key="stats_week" />" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="stats_week" />
						</td>
					</tr>
				</thead>
				<c:forEach items="${valueObject.pageInfoWeekList }" var="pageInfoWeek">
				<tr>
					<th width="100">
						${pageInfoWeek.information }
					</th>
					<td>
						<div class="optionbar">
							<div style="width: ${pageInfoWeek.lineWidth }px">
								&nbsp;
							</div>
						</div>
						&nbsp;
						<strong>${pageInfoWeek.num }</strong> (${pageInfoWeek.numPercent }%)
					</td>
				</tr>
				</c:forEach>
				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="stats_hour" />
						</td>
					</tr>
				</thead>
				<c:forEach items="${valueObject.pageInfoHourList }" var="pageInfoHour">
				<tr>
					<th width="100">
						${pageInfoHour.information }
					</th>
					<td>
						<div class="optionbar">
							<div style="width: ${pageInfoHour.lineWidth }px">
								&nbsp;
							</div>
						</div>
						&nbsp;
						<strong>${pageInfoHour.num }</strong> (${pageInfoHour.numPercent }%)
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />