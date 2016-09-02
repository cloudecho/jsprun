<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h1><bean:message key="my_activities"/></h1>
<ul class="tabs"><li ${type== 'orig'?"class=dropmenu hover current":"class=dropmenu"}><a href="my.jsp?item=activities&type=orig${extrafid}" id="myorig" onmouseover="showMenu(this.id)"><bean:message key="my_activity_orig"/></a></li><li ${type== 'apply'?"class=dropmenu hover current":"class=dropmenu"}><a href="my.jsp?item=activities&type=apply${extrafid}" id="myapply" onmouseover="showMenu(this.id)"><bean:message key="my_activity_apply"/></a></li></ul>
<ul class="popupmenu_popup headermenu_popup" id="myorig_menu" style="display: none"><li><a href="my.jsp?item=activities&type=orig${extrafid}&filter="><bean:message key="my_activities_all"/></a></li><li><a href="my.jsp?item=activities&type=orig${extrafid}&ended=no"><bean:message key="my_activities_noexpiration"/></a></li><li><a href="my.jsp?item=activities&type=orig${extrafid}&ended=yes"><bean:message key="my_activities_expiration"/></a></li></ul>
<ul class="popupmenu_popup headermenu_popup" id="myapply_menu" style="display: none"><li><a href="my.jsp?item=activities&type=apply${extrafid}&filter="><bean:message key="my_activities_all"/></a></li><li><a href="my.jsp?item=activities&type=apply${extrafid}&ended=no"><bean:message key="my_activities_noexpiration"/></a></li><li><a href="my.jsp?item=activities&type=apply${extrafid}&ended=yes"><bean:message key="my_activities_expiration"/></a></li></ul>
<div class="msgtabs"><strong><c:choose><c:when test="${type=='orig'}"><bean:message key="my_activity_orig"/> &#8212; <c:choose><c:when test="${ended==''}"><bean:message key="my_activities_all"/></c:when><c:when test="${ended=='no'}"><bean:message key="my_activities_noexpiration"/></c:when><c:when test="${ended=='yes'}"><bean:message key="my_activities_expiration"/></c:when></c:choose></c:when><c:when test="${type=='apply'}"><bean:message key="my_activity_apply"/> &#8212; <c:choose><c:when test="${ended==''}"><bean:message key="my_activities_all"/></c:when><c:when test="${ended=='no'}"><bean:message key="my_activities_noexpiration"/></c:when><c:when test="${ended=='yes'}"><bean:message key="my_activities_expiration"/></c:when></c:choose></c:when></c:choose></strong></div>
<table cellspacing="0" cellpadding="0" width="100%" align="center">
	<thead><tr><td><bean:message key="activity_name"/></td><td><bean:message key="activity_starttime"/></td><td><bean:message key="activity_space"/></td><td><bean:message key="activity_payment"/></td><td><c:choose><c:when test="${type=='orig'}"><bean:message key="my_activities_stop_stauts"/></c:when><c:when test="${type=='apply'}"><bean:message key="activity_confirm_status"/></c:when></c:choose></td></tr></thead>
	<tbody><c:choose><c:when test="${type=='orig'}">
		<c:forEach items="${activitylists}" var="activitylist"><tr>
			<td><c:choose><c:when test="${activitylist.displayorder>=0}"><a href="viewthread.jsp?tid=${activitylist.tid}" target="_black">${activitylist.subject}</a></c:when><c:otherwise>${activitylist.subject}</c:otherwise></c:choose></td>
			<td>${activitylist.starttimefrom}</td><td>${activitylist.place}</td><td>${activitylist.cost}</td>
			<td>
				<c:choose>
					<c:when test="${activitylist.expiration>0&&timestamp>activitylist.expiration}"><bean:message key="my_activities_stop_stauts_yes"/></c:when>
					<c:otherwise><bean:message key="my_activities_stop_stauts_no"/></c:otherwise>
				</c:choose>
			</td>
		</tr></c:forEach>
		<c:if test="${empty activitylists}"><tr><td colspan="5"><bean:message key="my_activity_nonexistence"/></td></tr></c:if>
	</c:when><c:when test="${type=='apply'}">
		<c:forEach items="${activitylists}" var="activitylist"><tr>
			<td><c:choose><c:when test="${activitylist.displayorder>=0}"><a href="viewthread.jsp?tid=${activitylist.tid}" target="_black">${activitylist.subject}</a></c:when><c:otherwise>${activitylist.subject}</c:otherwise></c:choose></td>
			<td>${activitylist.starttimefrom}</td><td>${activitylist.place}</td><td>${activitylist.cost}</td>
			<td>
				<c:choose>
					<c:when test="${activitylist.verified>0}"><bean:message key="activity_confirm_status_yes"/></c:when>
					<c:otherwise><bean:message key="activity_confirm_status_no"/></c:otherwise>
				</c:choose>
			</td>
		</tr></c:forEach>
		<c:if test="${empty activitylists}"><tr><td colspan="5"><bean:message key="my_activity_nonexistence"/></td></tr></c:if>
	</c:when></c:choose></tbody>
</table>