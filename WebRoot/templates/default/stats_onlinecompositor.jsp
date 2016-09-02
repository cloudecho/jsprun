<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_onlinetime" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">
			<h1>
				<bean:message key="stats_onlinetime" />
			</h1>
			<table summary="<bean:message key="stats_onlinetime" />" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="stats_onlinetime_total" />
						</td>
						<td colspan="2">
							<bean:message key="stats_onlinetime_thismonth" />
						</td>
					</tr>
				</thead>
				<c:forEach items="${valueObject.compositorMapList}" var="compositorMap">
					<tr>
					<td><a href="space.jsp?uid=${compositorMap.total.uid }" target="_blank">${compositorMap.total.username }</a>&nbsp;</td>
					<td align="right">
						${compositorMap.total.onlineTime }
					</td>
					<td><a href="space.jsp?uid=${compositorMap.thisMonth.uid }" target="_blank">${compositorMap.thisMonth.username }</a>&nbsp;</td>
					<td align="right">
						${compositorMap.thisMonth.onlineTime }
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<div class="notice">
			<bean:message key="stats_update" arg0="${valueObject.lastTime }" arg1="${valueObject.nextTime }" />
		</div>
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />
