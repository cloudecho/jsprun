<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_posts_rank" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">
			<h1>
				<bean:message key="stats_posts_rank" />
			</h1>
			<table summary="<bean:message key="stats_posts_rank" />" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td colspan="2">
							<bean:message key="stats_posts" /> <bean:message key="stats_rank" />
						</td>
						<td colspan="2">
							<bean:message key="stats_digest_posts" /> <bean:message key="stats_rank" />
						</td>
						<td colspan="2">
							<bean:message key="stats_posts_thismonth" /> <bean:message key="stats_rank" />
						</td>
						<td colspan="2">
							<bean:message key="stats_posts_today" /> <bean:message key="stats_rank" />
						</td>
					</tr>
				</thead>
				<c:forEach items="${valueObject.compositorMapList}" var="postsCompositorMap">
					<tr>
					<td><a href="space.jsp?username=<jrun:encoding value="${postsCompositorMap.allPosts.compositorName}"/>" target="_blank">${postsCompositorMap.allPosts.compositorName}</a> &nbsp;</td>
					<td align="right">
						${postsCompositorMap.allPosts.compositorNum }
					</td>
					<td><a href="space.jsp?username=<jrun:encoding value="${postsCompositorMap.prime.compositorName}"/>" target="_blank">${postsCompositorMap.prime.compositorName}</a></td>
					<td align="right">
						${postsCompositorMap.prime.compositorNum }
					</td>
					<td><a href="space.jsp?username=<jrun:encoding value="${postsCompositorMap.dayOf30.compositorName}"/>" target="_blank">${postsCompositorMap.dayOf30.compositorName}</a></td>
					<td align="right">
						${postsCompositorMap.dayOf30.compositorNum }
					</td>
					<td><a href="space.jsp?username=<jrun:encoding value="${postsCompositorMap.hourOf24.compositorName}"/>" target="_blank">${postsCompositorMap.hourOf24.compositorName}</a></td>
					<td align="right">
						${postsCompositorMap.hourOf24.compositorNum }
					</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />
