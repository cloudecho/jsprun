<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<div>
	<h2>
		<bean:message key="stats_options" />
	</h2>
	<ul>
		<li ${valueObject.navbar.type== "stats"||valueObject.navbar.type== null?"class=current":""}>
			<a href="stats.jsp"><bean:message key="stats_main" /></a>
		</li>
		<c:if test="${valueObject.navbar.statistic}">
		<li ${valueObject.navbar.type== "views"?"class=current":""}>
			<a href="stats.jsp?type=views"><bean:message key="stats_views" /></a>
		</li>		
		<li ${valueObject.navbar.type== "agent"?"class=current":""}>
			<a href="stats.jsp?type=agent"><bean:message key="stats_agent" /></a>
		</li>		
		<li ${valueObject.navbar.type== "posts"?"class=current":""}>
			<a href="stats.jsp?type=posts"><bean:message key="stats_posthist" /></a>
		</li>	
		</c:if>
		<li ${valueObject.navbar.type== "forumsrank"?"class=current":""}>
			<a href="stats.jsp?type=forumsrank"><bean:message key="stats_forums_rank" /></a>
		</li>
		<li ${valueObject.navbar.type== "threadsrank"?"class=current":""}>
			<a href="stats.jsp?type=threadsrank"><bean:message key="stats_threads_rank" /></a>
		</li>
		<li ${valueObject.navbar.type== "postsrank"?"class=current":""}>
			<a href="stats.jsp?type=postsrank"><bean:message key="stats_posts_rank" /></a>
		</li>
		<li ${valueObject.navbar.type== "creditsrank"?"class=current":""}>
			<a href="stats.jsp?type=creditsrank"><bean:message key="stats_credits_rank" /></a>
		</li>
		<li ${valueObject.navbar.type== "trade"?"class=current":""}>
			<a href="stats.jsp?type=trade"><bean:message key="stats_trade_rank" /></a>
		</li>
		<c:if test="${settings.oltimespan>0.0}">
		<li ${valueObject.navbar.type== "onlinetime"?"class=current":""}>
			<a href="stats.jsp?type=onlinetime"><bean:message key="stats_onlinetime" /></a>
		</li>
		</c:if>
		<li ${valueObject.navbar.type== "team"?"class=current":""}>
			<a href="stats.jsp?type=team"><bean:message key="stats_team" /></a>
		</li>
		<c:if test="${valueObject.navbar.modworkstatus}">
		<li ${valueObject.navbar.type== "modworks"?"class=current":""}>
			<a href="stats.jsp?type=modworks"><bean:message key="stats_modworks" /></a>
		</li>
		</c:if>
	</ul>
</div>