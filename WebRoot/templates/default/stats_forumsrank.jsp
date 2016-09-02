<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo;<a href="stats.jsp"><bean:message key="stats" /></a> &raquo;<bean:message key="stats_forums_rank" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">
			<h1>
				<bean:message key="stats_forums_rank" />
			</h1>
			<table summary="<bean:message key="stats_forums_rank" />" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="stats_posts" /> <bean:message key="stats_rank" />
						</td>
						<td colspan="2">
							<bean:message key="threads_replies" /> <bean:message key="stats_rank" />
						</td>
						<td colspan="2">
							<bean:message key="stats_posts_thismonth" /> <bean:message key="stats_rank" />
						</td>
						<td colspan="2">
							<bean:message key="stats_posts_today" /> <bean:message key="stats_rank" />
						</td>
					</tr>
				</thead>
				<c:forEach items="${valueObject.forumCompositorMapList}" var="forumCompositorMap">
					<tr>
					<td><a href="forumdisplay.jsp?fid=${forumCompositorMap.thread.fid }" target="_blank">${forumCompositorMap.thread.name }</a></td>
					<td align="right">
						${forumCompositorMap.thread.num }
					</td>
					<td><a href="forumdisplay.jsp?fid=${forumCompositorMap.post.fid  }" target="_blank">${forumCompositorMap.thread.name }</a></td>
					<td align="right">
						${forumCompositorMap.post.num }
					</td>
					<td><a href="forumdisplay.jsp?fid=${forumCompositorMap.thread_thisMonth.fid }" target="_blank">${forumCompositorMap.thread_thisMonth.name }</a></td>
					<td align="right">
						${forumCompositorMap.thread_thisMonth.num }
					</td>
					<td><a href="forumdisplay.jsp?fid=${forumCompositorMap.thread_thisDay.fid }" target="_blank">${forumCompositorMap.thread_thisDay.name }</a></td>
					<td align="right">
						${forumCompositorMap.thread_thisDay.num }
					</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="notice">
			<bean:message key="stats_update" arg0="${valueObject.lastTime }" arg1="${valueObject.nextTime}" />
		</div>
	</div>
</div>

<jsp:include flush="true" page="footer.jsp" />
