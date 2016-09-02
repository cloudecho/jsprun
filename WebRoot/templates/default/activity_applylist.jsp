<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<div class="box">
	<ul class="tabs">
		<li><a href="viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewspecialpost&page=${param.page}&rand='+Math.random()" onclick="ajaxget(this.href, 'ajaxspecialpost', 'ajaxspecialpost');doane(event);"><bean:message key="special_tab_activity"/></a></li>
		<li class="current"><a href="misc.jsp?action=activityapplylist&tid=${thread.tid}&rand='+Math.random()" onclick="ajaxget(this.href, 'ajaxspecialpost', 'ajaxspecialpost');doane(event);"><bean:message key="activity_join_list"/></a></li>
	</ul>
	<form id="applylistform" method="post" action="misc.jsp?action=activityapplylist&tid=${thread.tid}">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="operation" value="" />
		<c:choose>
			<c:when test="${activitsapplist!=null}">
				<table summary="Apply List" cellspacing="0" cellpadding="5">
				<thead><tr><c:if test="${thread.authorid==jsprun_uid}"><td>&nbsp;</td></c:if><td><bean:message key="activity_join_members"/></td><c:if test="${thread.authorid==jsprun_uid}"><td><bean:message key="activity_linkman"/></td></c:if><td><bean:message key="leaveword"/></td><td><bean:message key="activity_payment_2"/></td><td><bean:message key="activity_jointime"/></td><c:if test="${thread.authorid==jsprun_uid}"><td><bean:message key="a_system_check_status"/></td></c:if></tr></thead>
				<c:forEach items="${activitsapplist}" var="applist"><tr>
					<c:if test="${thread.authorid==jsprun_uid}"><td><input class="checkbox" type="checkbox" name="applyidarray[]" value="${applist.applyid}" /></td></c:if>
					<td><a target="_blank" href="space.jsp?uid=${applist.uid}">${applist.username}</a></td>
					<c:if test="${thread.authorid==jsprun_uid}"><td>${applist.contact}</td></c:if>
					<td>${applist.message}</td>
					<td><c:choose><c:when test="${applist.payment>=0}">${applist.payment}<bean:message key="rmb_yuan"/></c:when><c:otherwise><bean:message key="activity_self"/></c:otherwise></c:choose></td>
					<td><jrun:showTime timeInt="${applist.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
					<c:if test="${thread.authorid==jsprun_uid}"><td><c:choose><c:when test="${applist.verified==1}"><bean:message key="activity_allow_join"/></c:when><c:otherwise><bean:message key="activity_cant_audit"/></c:otherwise></c:choose></td></c:if>
				</tr></c:forEach>
			</table>
			<c:if test="${thread.authorid==jsprun_uid}">
				<div class="footoperation">
					<label><input class="checkbox header" type="checkbox" name="chkall" onclick="checkall(this.form, 'applyid')" /> <bean:message key="select_all"/></label>&nbsp;&nbsp;
					<button type="submit" value="true" name="applylistsubmit" onclick="$('applylistform').operation.value='delete'"><bean:message key="delete"/></button>&nbsp;&nbsp;
					<button class="submit" type="submit" value="true" name="applylistsubmit"><bean:message key="confirm"/></button>
				</div>
			</c:if>
			</c:when>
			<c:otherwise><div class="specialpost"><bean:message key="none"/></div></c:otherwise>
		</c:choose>
	</form>
</div>