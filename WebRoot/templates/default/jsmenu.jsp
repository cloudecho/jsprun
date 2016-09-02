<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><c:if test="${settings.jsmenu_2>0}">
<ul class="popupmenu_popup headermenu_popup" id="memcp_menu" style="display: none">
<li><a href="memcp.jsp"><bean:message key="memcp_home" /></a></li>
<li><a href="memcp.jsp?action=profile"><bean:message key="memcp_profile" /></a></li><c:if test="${settings.exchangestatus>0 || settings.transferstatus>0&&usergroups.allowtransfer>0 || settings.ec_ratio>0}">
<li><a href="memcp.jsp?action=credits"><bean:message key="memcp_credits" /></a></li></c:if>
<li><a href="memcp.jsp?action=creditslog"><bean:message key="memcp_credits_log" /></a></li>
<li><a href="memcp.jsp?action=usergroups"><bean:message key="memcp_usergroups" /></a></li><c:if test="${settings.spacestatus>0}">
<li><a href="memcp.jsp?action=spacemodule" target="_blank"><bean:message key="space_settings" /></a></li></c:if>
</ul></c:if><c:if test="${!empty plugins.jsmenus}">
<ul class="popupmenu_popup headermenu_popup" id="plugin_menu" style="display: none">
<c:forEach items="${plugins.jsmenus}" var="module"><c:if test="${module.value.adminid==0||(module.value.adminid>0 && jsprun_adminid > 0 && module.value.adminid >= jsprun_adminid)}"><li>${module.value.url}</li></c:if></c:forEach>
</ul></c:if><c:if test="${settings.jsmenu_3>0}">
<ul class="popupmenu_popup headermenu_popup" id="stats_menu" style="display: none">
<li><a href="stats.jsp"><bean:message key="stats_main" /></a></li><c:if test="${settings.statstatus>0}">
<li><a href="stats.jsp?type=views"><bean:message key="stats_views" /></a></li><li><a href="stats.jsp?type=agent"><bean:message key="stats_agent" /></a></li><li><a href="stats.jsp?type=posts"><bean:message key="posts_log" /></a></li></c:if>
<li><a href="stats.jsp?type=forumsrank"><bean:message key="stats_forums_rank" /></a></li>
<li><a href="stats.jsp?type=threadsrank"><bean:message key="stats_threads_rank" /></a></li>
<li><a href="stats.jsp?type=postsrank"><bean:message key="stats_posts_rank" /></a></li>
<li><a href="stats.jsp?type=creditsrank"><bean:message key="stats_credits_rank" /></a></li>
<li><a href="stats.jsp?type=trade"><bean:message key="stats_trade_rank" /></a></li><c:if test="${settings.oltimespan>0.0}">
<li><a href="stats.jsp?type=onlinetime"><bean:message key="stats_onlinetime" /></a></li></c:if>
<li><a href="stats.jsp?type=team"><bean:message key="stats_team" /></a></li><c:if test="${settings.modworkstatus>0.0}">
<li><a href="stats.jsp?type=modworks"><bean:message key="stats_modworks" /></a></li></c:if>
</ul></c:if><c:if test="${jsprun_uid>0&&settings.jsmenu_4>0}">
<ul class="popupmenu_popup headermenu_popup" id="my_menu" style="display: none">
<li><a href="my.jsp?item=threads"><bean:message key="show_mytopics" /></a></li>
<li><a href="my.jsp?item=favorites&type=thread"><bean:message key="my_favorites" /></a></li>
<li><a href="my.jsp?item=subscriptions"><bean:message key="my_subscriptions" /></a></li>
<li><a href="my.jsp?item=grouppermission"><bean:message key="my_permissions" /></a></li>
<li><a href="my.jsp?item=polls&type=poll"><bean:message key="my_poll" /></a></li>
<li><a href="my.jsp?item=tradestats"><bean:message key="my_trades" /></a></li>
<li><a href="my.jsp?item=reward&type=stats"><bean:message key="my_rewards" /></a></li>
<li><a href="my.jsp?item=activities&type=orig&ended=no"><bean:message key="my_activities" /></a></li>
<li><a href="my.jsp?item=debate&type=orig"><bean:message key="my_debate" /></a></li><c:if test="${settings.videoopen>0}">
<li><a href="my.jsp?item=video"><bean:message key="my_video" /></a></li></c:if>
<li><a href="my.jsp?item=buddylist"><bean:message key="my_buddylist" /></a></li><c:if test="${promotion}">
<li><a href="my.jsp?item=promotion"><bean:message key="post_my_advisit" /></a></li></c:if><c:if test="${settings.spacestatus>0}">
<li><a href="space.jsp?uid=${jsprun_uid}" target="_blank"><bean:message key="150" /></a></li></c:if>
</ul></c:if>