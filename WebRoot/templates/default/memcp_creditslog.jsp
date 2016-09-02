<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div class="container">
	<div id="foruminfo"><div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="memcp_credits_log"/></div></div>
	<div class="content"><div class="mainbox formbox">
		<h1><bean:message key="memcp_credits_log"/></h1>
		<ul class="tabs headertabs">
			<li ${operation== 'creditslog'?"class=current":""}><a href="memcp.jsp?action=creditslog&operation=creditslog"><bean:message key="memcp_credits_log_transaction"/></a></li>
			<li ${operation== 'paymentlog'?"class=current":""}><a href="memcp.jsp?action=creditslog&operation=paymentlog"><bean:message key="memcp_credits_log_payment"/></a></li>
			<li ${operation== 'incomelog'?"class=current":""}><a href="memcp.jsp?action=creditslog&operation=incomelog"><bean:message key="memcp_credits_log_income"/></a></li>
			<li ${operation== 'rewardpaylog'?"class=current":""}><a href="memcp.jsp?action=creditslog&operation=rewardpaylog"><bean:message key="memcp_reward_log_payment"/></a></li>
			<li ${operation== 'rewardincomelog'?"class=current":""}><a href="memcp.jsp?action=creditslog&operation=rewardincomelog"><bean:message key="memcp_reward_log_income"/></a></li>
		</ul>
		<c:choose><c:when test="${operation=='creditslog'}">
			<table summary="<bean:message key="memcp_credits_log_transaction"/>" cellspacing="0" cellpadding="0" width="100%" align="center">
				<thead><tr><td><bean:message key="a_system_logs_credit_fromto"/></td><td><bean:message key="time"/></td><td width="15%"><bean:message key="a_system_logs_credit_send"/></td><td><bean:message key="a_system_logs_credit_receive"/></td><td><bean:message key="operation"/></td></tr></thead>
				<tbody>
					<c:forEach items="${loglist}" var="log"><tr><td><a href="space.jsp?username=${log.fromtoenc}">${log.fromto}</a></td><td>${log.dateline}</td><td>${log.send}</td><td>${log.receive}</td><td>${log.operation}</td></tr></c:forEach>
					<c:if test="${empty loglist}"><tr><td colspan="5"><bean:message key="memcp_credits_log_none"/></td></tr></c:if>
				</tbody>
			</table>
			<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
		</c:when><c:when test="${operation=='paymentlog'}">
			<table summary="<bean:message key="memcp_credits_log_payment"/>" cellspacing="0" cellpadding="0" width="100%" align="center">
				<thead><tr><td><bean:message key="subject"/></td><td class="user"><bean:message key="author"/></td><td class="time"><bean:message key="a_forum_edit_starttime"/></td><td><bean:message key="forum_name"/></td><td><bean:message key="memcp_credits_log_payment_dateline"/></td><td><bean:message key="a_post_threads_price"/></td><td><bean:message key="pay_author_income"/></td></tr></thead>
				<tbody>
					<c:forEach items="${loglist}" var="log"><tr><td><a href="viewthread.jsp?tid=${log.tid}">${log.subject}</a></td><td><a href="space.jsp?uid=${log.authorid}">${log.author}</a></td><td>${log.tdateline}</td><td><a href="forumdisplay.jsp?fid=${log.fid}">${log.name}</a></td><td>${log.dateline}</td><c:choose><c:when test="${empty log.amount && empty log.netamount}"><bean:message key="memcp_credits_log_payment_refunded"/></c:when><c:otherwise><td>${log.amount}</td><td>${log.netamount}</td></c:otherwise></c:choose></tr></c:forEach>
					<c:if test="${empty loglist}"><tr><td colspan="7"><bean:message key="memcp_credits_log_none"/></td></tr></c:if>
				</tbody>
			</table>
			<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
		</c:when><c:when test="${operation=='incomelog'}">
			<table summary="<bean:message key="memcp_credits_log_income"/>" cellspacing="0" cellpadding="0" width="100%" align="center">
				<thead><tr><td align="left"><bean:message key="subject"/></td><td><bean:message key="a_forum_edit_starttime"/></td><td><bean:message key="forum_name"/></td><td><bean:message key="a_system_logs_invite_buyer"/></td><td><bean:message key="memcp_credits_log_payment_dateline"/></td><td><bean:message key="a_post_threads_price"/></td><td><bean:message key="pay_author_income"/></td></tr></thead>
				<tbody>
					<c:forEach items="${loglist}" var="log"><tr><td><a href="viewthread.jsp?tid=${log.tid}">${log.subject}</a></td><td>${log.tdateline}</td><td><a href="forumdisplay.jsp?fid=${log.fid}">${log.name}</a></td><td><a href="space.jsp?uid=${log.uid}">${log.username}</a></td><td>${log.dateline}</td><c:choose><c:when test="${empty log.amount && empty log.netamount}"><bean:message key="memcp_credits_log_payment_refunded"/></c:when><c:otherwise><td>${log.amount}</td><td>${log.netamount}</td></c:otherwise></c:choose></tr></c:forEach>
					<c:if test="${empty loglist}"><tr><td colspan="7"><bean:message key="memcp_credits_log_none"/></td></tr></c:if>
				</tbody>
			</table>
			<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
		</c:when><c:when test="${operation=='rewardpaylog'}">
			<table summary="<bean:message key="memcp_reward_log_payment"/>" cellspacing="0" cellpadding="0" width="100%" align="center">
				<thead><tr><td><bean:message key="subject"/></td><td><bean:message key="a_forum_edit_starttime"/></td><td><bean:message key="forum_name"/></td><td><bean:message key="memcp_reward_log_payment_answerer"/></td><td><bean:message key="memcp_reward_total"/></td><td><bean:message key="memcp_reward_fact"/></td></tr></thead>
				<tbody>
					<c:forEach items="${loglist}" var="log"><tr><td><a href="viewthread.jsp?tid=${log.tid}">${log.subject}</a></td><td>${log.dateline}</td><td><a href="forumdisplay.jsp?fid=${log.fid}">${log.name}</a></td><td><a href="space.jsp?uid=${log.uid}">${log.username}</a></td><td>${log.price}</td><td>${log.netamount}</td></tr></c:forEach>
					<c:if test="${empty loglist}"><tr><td colspan="6"><bean:message key="memcp_credits_log_none"/></td></tr></c:if>
				</tbody>
			</table>
			<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
		</c:when><c:when test="${operation=='rewardincomelog'}">
			<table summary="<bean:message key="memcp_reward_log_income"/>" cellspacing="0" cellpadding="0" width="100%" align="center">
				<thead><tr><td><bean:message key="subject"/></td><td><bean:message key="a_forum_edit_starttime"/></td><td><bean:message key="forum_name"/></td><td><bean:message key="memcp_reward_log_income_author"/></td><td><bean:message key="memcp_reward_total"/></td></tr></thead>
				<tbody>
					<c:forEach items="${loglist}" var="log"><tr><td><a href="viewthread.jsp?tid=${log.tid}">${log.subject}</a></td><td>${log.dateline}</td><td><a href="forumdisplay.jsp?fid=${log.fid}">${log.name}</a></td><td><a href="space.jsp?uid=${log.uid}">${log.username}</a></td><td>${log.price}</td></tr></c:forEach>
					<c:if test="${empty loglist}"><tr><td colspan="5"><bean:message key="memcp_credits_log_none"/></td></tr></c:if>
				</tbody>
			</table>
			<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
		</c:when></c:choose>
	</div></div>
	<div class="side"><jsp:include flush="true" page="personal_navbar.jsp" /></div>
</div>
<jsp:include flush="true" page="footer.jsp" />