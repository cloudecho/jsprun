<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<thead>
	<tr>
	<td colspan="2">
		<bean:message key="stats_month_posts" />
	</td>
	</tr>
</thead>
<c:forEach items="${valueObject.subPostLog.ereryMonthPost}"
	var="pageInfo">
<tr>
	<th width="100">
		<strong>${pageInfo.information }</strong>
	</th>
	<td>
		<div class="optionbar">
			<div style="width: ${pageInfo.lineWidth }px">
				&nbsp;
			</div>
		</div>
		&nbsp;
		<strong>${pageInfo.num }</strong> 
		(${pageInfo.numPercent }%)
	</td>
</tr>
</c:forEach>
<thead>
	<tr>
	<td colspan="2">
		<bean:message key="stats_day_posts" />
	</td>
	</tr>
</thead>
<c:forEach items="${valueObject.subPostLog.ereryDayPost }" var="pageInfo">
	<tr>
		<th width="100">
			<strong>${pageInfo.information }</strong>
		</th>
		<td>
			<div class="optionbar">
				<div style="width: ${pageInfo.lineWidth }px">
					&nbsp;
				</div>
			</div>
			&nbsp;
			<strong>${pageInfo.num }</strong>
			(${pageInfo.numPercent }%)
		</td>
	</tr>
</c:forEach>
