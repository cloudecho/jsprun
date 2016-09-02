<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_threads_rank" />
</div>

<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">
			<h1>
				<bean:message key="stats_threads_rank" />
			</h1>
			<table summary="<bean:message key="stats_threads_rank" />" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="stats_threads_most_views" />
						</td>
						<td colspan="2">
							<bean:message key="stats_threads_most_replies" />
						</td>
					</tr>
				</thead>
				<c:forEach items="${valueObject.compositorMapList}" var="threadCompositorMap">
					<tr>
					<td><a href="viewthread.jsp?tid=${threadCompositorMap.views.compositorSign }">${threadCompositorMap.views.compositorName }</a>&nbsp;</td>
					<td align="right">
						${threadCompositorMap.views.compositorNum }
					</td>
					<td><a href="viewthread.jsp?tid=${threadCompositorMap.replies.compositorSign }">${threadCompositorMap.replies.compositorName }</a>&nbsp;</td>
					<td align="right">
						${threadCompositorMap.replies.compositorNum }
					</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />