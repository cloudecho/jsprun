<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div class="container">
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="a_setting_jsmenu_enable_memcp"/></div>
<div class="content">
<table id="memberinfo" class="portalbox" cellpadding="0" cellspacing="1"><tr>
<td class="memberinfo_avatar">
	<c:choose>
		<c:when test="${!empty memberfiled.avatar}"><img class="avatar" src="${memberfiled.avatar}" alt="" width="${memberfiled.avatarwidth}" height="${memberfiled.avatarheight}"/></c:when>
		<c:otherwise><img class="avatar" src="images/avatars/noavatar.gif" alt="" /></c:otherwise>
	</c:choose>
	<p><a href="space.jsp?action=viewpro&uid=${member.uid}">${member.username}</a></p>
</td>
<td class="memberinfo_forum">
	<ul>
		<li><label><bean:message key="a_setting_uid"/>:</label> ${member.uid}</li>
		<li><label><bean:message key="menu_member_usergroups"/>:</label> ${usergroups.grouptitle}<c:if test="${settings.regverify==1&&member.groupid==8}">&nbsp; [ <a href="member.jsp?action=emailverify"><bean:message key="memcp_email_reverify"/></a> ]</c:if></li>
		<li><label><bean:message key="regdate"/>:</label> <jrun:showTime timeInt="${member.regdate}" type="${dateformat}" timeoffset="${timeoffset}"/></li>
		<li><label><bean:message key="a_member_edit_regip"/></label> <c:if test="${member.regip!=''}">${member.regip}&nbsp;${addressMap[member.regip]}</c:if></li>
		<li><label><bean:message key="a_member_edit_lastip"/></label> <c:if test="${member.lastip!=''}">${member.lastip}&nbsp;${addressMap[member.lastip]}</c:if></li>
		<li><label><bean:message key="a_member_edit_lastvisit"/></label> <jrun:showTime timeInt="${member.lastvisit}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></li>
		<li><label><bean:message key="a_post_threads_lastpost"/>:</label> <jrun:showTime timeInt="${member.lastpost}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></li>
	</ul>
</td>
<td class="memberinfo_forum" style="width: 12em;">
	<ul>
		<li><bean:message key="credits"/>: ${member.credits}</li>
		<c:forEach items="${extcreditsMap}" var="extcredit"><li><c:choose><c:when test="${extcredit.key==settings.creditstrans}">${extcredit.value.title}: <span style="font-weight: bold;">${extcreditvalue[extcredit.key]}</span> ${extcredit.value.unit}</c:when><c:otherwise>${extcredit.value.title}: ${extcreditvalue[extcredit.key]} ${extcredit.value.unit}</c:otherwise></c:choose></li></c:forEach>
		<li><bean:message key="a_setting_posts"/>: ${member.posts}</li>
		<li><bean:message key="digest"/>: ${member.digestposts}</li>
	</ul>
</td>
</tr></table>
<c:if test="${member.groupid==8&&settings.regverify==2}">
<div class="mainbox formbox">
<h1><bean:message key="memcp_validating"/></h1>
<form method="post" action="member.jsp?action=regverify">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="validateid" value="${validate.uid}" />
	<table summary="" cellpadding="0" cellspacing="0">
		<thead><tr><th>&nbsp;</th><td><bean:message key="memcp_validating_info" arg0="${validate.submittimes}"/></td></tr></thead>
		<tr><th><bean:message key="a_member_edit_current_status"/></th><td><strong>
		<c:choose>
			<c:when test="${validate.status==1}"><bean:message key="memcp_validating_status_1"/></c:when>
			<c:otherwise><bean:message key="memcp_validating_status_0"/></c:otherwise>
		</c:choose>
		</strong></td></tr>
		<c:if test="${validate.admin!=''}"><tr><th><bean:message key="memcp_validating_admin"/></th><td><a href="space.jsp?username=<jrun:encoding value="${validate.admin}"/>">${validate.admin}</a></td></tr></c:if>
		<c:if test="${validate.moddate!=0}"><tr><th><bean:message key="memcp_validating_time"/></th><td><jrun:showTime timeInt="${validate.moddate}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td></tr></c:if>
		<c:if test="${validate.remark!=''}"><tr><th><bean:message key="memcp_validating_remark"/></th><td>${validate.remark}</td></tr></c:if>
		<tr><th valign="top"><label for="regmessage"><bean:message key="a_member_moderate_message"/></label></th><td><textarea rows="5" cols="50" id="regmessage" name="regmessage">${validate.message}</textarea></td></tr>
		<c:if test="${validate.status==1}"><tr class="btns"><th>&nbsp;</th><td><button type="submit" class="submit" name="verifysubmit" id="verifysubmit" value="true"><bean:message key="submitf"/></button></td></tr></c:if>
	</table>
</form>
</div>
</c:if>
<div class="mainbox">
<h3><bean:message key="memcp_last_5_pm"/></h3>
<table summary="<bean:message key="memcp_last_5_pm"/>" cellpadding="0" cellspacing="0">
	<thead><tr><th><bean:message key="subject"/></th><td class="user"><bean:message key="location_from"/></td><td class="time"><bean:message key="time"/></td></tr></thead>
	<tbody>
		<c:forEach items="${pmsList}" var="pms"><tr>
			<th><a href="pm.jsp?action=view&pmid=${pms.pmid}" target="_blank">${pms.subject}</a></th>
			<td class="user"><c:choose><c:when test="${pms.msgfromid>0}"><a href="space.jsp?uid=${pms.msgfromid}">${pms.msgfrom}</a></c:when><c:otherwise><bean:message key="pm_systemmessage"/></c:otherwise></c:choose></td>
			<td class="time"><jrun:showTime timeInt="${pms.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
		</tr></c:forEach>
		<c:if test="${empty pmsList}"><tr><th colspan="3"><bean:message key="memcp_nopm"/></th></tr></c:if>
	</tbody>
</table>
</div>
<div class="mainbox">
<h3><bean:message key="memcp_last_5_creditslog"/></h3>
<table summary="<bean:message key="memcp_last_5_creditslog"/>" cellspacing="0" cellpadding="0">
	<thead><tr><td class="user"><bean:message key="a_system_logs_credit_fromto"/></td><td class="time"><bean:message key="time"/></td><td class="nums"><bean:message key="a_system_logs_credit_send"/></td><td class="nums"><bean:message key="a_system_logs_credit_receive"/></td><td><bean:message key="operation"/></td></tr></thead>
	<tbody>
		<c:forEach items="${resultlist}" var="clog"><tr>
			<td class="user"><a href="space.jsp?username=<jrun:encoding value="${clog.fromname}"/>">${clog.fromname}</a></td>
			<td class="time">${clog.opertarDate}</td>
			<td class="nums"><c:if test="${clog.sendNum>0}">${clog.sendCrites} ${clog.sendNum} ${clog.sendunit}</c:if></td>
			<td class="nums"><c:if test="${clog.receiverNum>0}">${clog.receiveCrites} ${clog.receiverNum} ${clog.receiveuint}</c:if></td>
			<td>${clog.opertar}</td>
		</tr></c:forEach>
		<c:if test="${empty resultlist}"><tr><td colspan="5"><bean:message key="memcp_credits_log_none"/></td></tr></c:if>
	</tbody>
</table>
</div>
</div>
<div class="side"><jsp:include flush="true" page="personal_navbar.jsp" /></div>
</div>
<jsp:include flush="true" page="footer.jsp" />