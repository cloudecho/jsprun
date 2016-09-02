<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<div>
<h2><bean:message key="my_admin"/></h2>
<ul><c:choose><c:when test="${accessing=='pm'}">
	<li class="side_on first"><h3><a href="pm.jsp"><bean:message key="pm"/></a></h3></li>
	<li><h3><a href="my.jsp"><bean:message key="my"/></a></h3></li>
	<li><h3><a href="memcp.jsp"><bean:message key="a_setting_jsmenu_enable_memcp"/></a></h3></li>
	<c:if test="${settings.regstatus>1}"><li><h3><a href="invite.jsp"><bean:message key="invite"/></a></h3></li></c:if>
</c:when><c:when test="${accessing=='my'}">
<li><h3><a href="pm.jsp"><bean:message key="pm"/></a></h3></li>
<li class="side_on"><h3><bean:message key="my"/></h3>
	<ul>
		<li ${myitem== "threads"||myitem== "posts"?"class=current":""}><a href="my.jsp?item=threads${extrafid}"><bean:message key="show_mytopics"/></a></li>
		<li ${myitem== "favorites"?"class=current":""}><a href="my.jsp?item=favorites&amp;type=thread${extrafid}"><bean:message key="my_favorites"/></a></li>
		<li ${myitem== "subscriptions"?"class=current":""}><a href="my.jsp?item=subscriptions&amp;type=forum${extrafid}"><bean:message key="my_subscriptions"/></a></li>
		<li ${myitem== "grouppermission"?"class=current":""}><a href="my.jsp?item=grouppermission"><bean:message key="my_permissions"/></a></li>
		<li ${myitem== "polls"?"class=current":""}><a href="my.jsp?item=polls&amp;type=poll${extrafid}"><bean:message key="my_poll"/></a></li>
		<li ${myitem== "tradestats"||myitem== "tradethreads"||myitem=="selltrades"||myitem== "buytrades"?"class=current":""}><a href="my.jsp?item=tradestats${extrafid}"><bean:message key="my_trades"/></a></li>
		<li ${myitem== "reward"?"class=current":""}><a href="my.jsp?item=reward&amp;type=stats${extrafid}"><bean:message key="my_rewards"/></a></li>
		<li ${myitem== "activities"?"class=current":""}><a href="my.jsp?item=activities&amp;type=orig${extrafid}"><bean:message key="my_activities"/></a></li>
		<li ${myitem== "debate"?"class=current":""}><a href="my.jsp?item=debate&amp;type=orig${extrafid}"><bean:message key="my_debate"/></a></li>
		<c:if test="${settings.videoopen>0}"><li ${myitem== "video"?"class=current":""}><a href="my.jsp?item=video&type=orig${extrafid}"><bean:message key="my_video"/></a></li></c:if>
		<li ${myitem== "buddylist"?"class=current":""}><a href="my.jsp?item=buddylist${extrafid}"><bean:message key="my_buddylist"/></a></li>
		<c:if test="${promotion}"><li ${myitem== "promotion"?"class=current":""}><a href="my.jsp?item=promotion${extrafid}"><bean:message key="post_my_advisit"/></a></li></c:if>
		<c:if test="${settings.spacestatus>0}"><li><a href="space.jsp?uid=${jsprun_uid}" target="_blank"><bean:message key="150"/></a></li></c:if>
	</ul>
</li>
<li><h3><a href="memcp.jsp"><bean:message key="a_setting_jsmenu_enable_memcp"/></a></h3></li>
<c:if test="${settings.regstatus>1}"><li><h3><a href="invite.jsp"><bean:message key="invite"/></a></h3></li></c:if>
</c:when><c:when test="${accessing=='memcp'}">
<li><h3><a href="pm.jsp"><bean:message key="pm"/></a></h3></li>
<li><h3><a href="my.jsp"><bean:message key="my"/></a></h3></li>
<li class="side_on"><h3><bean:message key="a_setting_jsmenu_enable_memcp"/></h3>
	<ul>
		<li ${memcpaction== "memcp"?"class=current":""}><a href="memcp.jsp"><bean:message key="memcp_home"/></a></li>
		<li ${memcpaction== "profile"?"class=current":""}><a href="memcp.jsp?action=profile"><bean:message key="memcp_profile"/></a></li>
		<c:if test="${settings.exchangestatus>0 || settings.transferstatus>0&&usergroups.allowtransfer>0 || settings.ec_ratio>0}"><li ${memcpaction== "credits"?"class=current":""}><a href="memcp.jsp?action=credits"><bean:message key="memcp_credits"/></a></li></c:if>
		<li ${memcpaction== "creditslog"?"class=current":""}><a href="memcp.jsp?action=creditslog"><bean:message key="memcp_credits_log"/></a></li>
		<c:if test="${usergroups.allowmultigroups>0}"><li ${memcpaction== "usergroups"?"class=current":""}><a href="memcp.jsp?action=usergroups"><bean:message key="memcp_usergroups"/></a></li></c:if>
		<c:if test="${settings.spacestatus>0}"><li><a href="memcp.jsp?action=spacemodule" target="_blank"><bean:message key="space_settings"/></a>	</li></c:if>
	</ul>
</li>
<c:if test="${settings.regstatus>1}"><li><h3><a href="invite.jsp"><bean:message key="invite"/></a></h3></li></c:if>
</c:when><c:when test="${accessing=='invite'&&settings.regstatus>1}">
<li><h3><a href="pm.jsp"><bean:message key="pm"/></a></h3></li>
<li><h3><a href="my.jsp"><bean:message key="my"/></a></h3></li>
<li><h3><a href="memcp.jsp"><bean:message key="a_setting_jsmenu_enable_memcp"/></a></h3></li>
<li class="side_on last"><h3><bean:message key="invite"/></h3>
	<ul>
		<li ${inviteAction== "buyinvite"?"class=current":""}><a href="invite.jsp?action=buyinvite"><bean:message key="invite_get"/></a></li>
		<li ${inviteAction== "availablelog"?"class=current":""}><a href="invite.jsp?action=availablelog"><bean:message key="invite_logs"/></a></li>
	</ul>
</li>
</c:when></c:choose></ul></div>
<div class="credits_info"><h2><bean:message key="memcp_credits_info"/></h2><ul><li><bean:message key="credits"/>: ${user.credits}</li><c:forEach items="${extcredits}" var="extcredit"><li><c:set var="extcr" value="extcredits${extcredit.key}" /><c:choose><c:when test="${extcredit.key==settings.creditstrans}">${extcredit.value.title}: <span style="font-weight: bold;">${user[extcr]}</span> ${extcredit.value.unit}</c:when><c:otherwise>${extcredit.value.title}: ${user[extcr]} ${extcredit.value.unit}</c:otherwise></c:choose></li></c:forEach></ul></div>