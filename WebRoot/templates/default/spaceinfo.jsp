<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<div class="postauthor userinfopanel" style="border: 0;padding: 0">
	<p style="float: right"><a href="javascript:hideMenu();"><img src="${styles.IMGDIR}/close.gif" alt="<bean:message key="closed" />" title="<bean:message key="closed" />" /></a></p>
	<p><b>${member.username}</b></p>
	<c:if test="${!empty memberfild.nickname}"><p>${memberfild.nickname}</p></c:if>
	<p><em>
	<c:choose>
		<c:when test="${isfounder}"><bean:message key="a_setting_initiator"/></c:when>
		<c:otherwise>${usergroup.grouptitle}</c:otherwise>
	</c:choose>
	</em></p>
	<p><jrun:showstars num="${usergroup.stars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/></p>
	<c:if test="${memberfild.qq!='' || memberfild.icq!='' || memberfild.yahoo!='' || memberfild.taobao!=''}">
	<div class="imicons">
		<c:if test="${memberfild.qq!=''}"><a href="http://wpa.qq.com/msgrd?V=1&amp;Uin=${memberfild.qq}&amp;Site=Jsprun~&amp;Menu=yes" target="_blank"><img src="${styles.IMGDIR}/qq.gif" alt="QQ" /></a></c:if>
		<c:if test="${memberfild.icq!=''}"><a href="http://wwp.icq.com/scripts/search.dll?to=${memberfild.icq}" target="_blank"><img src="${styles.IMGDIR}/icq.gif" alt="ICQ" /></a></c:if>
		<c:if test="${memberfild.yahoo!=''}"><a href="http://edit.yahoo.com/config/send_webmesg?.target=${memberfild.yahoo}&amp;.src=pg" target="_blank"><img src="${styles.IMGDIR}/yahoo.gif" alt="Yahoo!" /></a></c:if>
	</div>
	</c:if>
	<c:forEach items="${custominfo.menu}" var="cus">${cus}<br></c:forEach>
	<c:if test="${memberfild.site!=''}"><p><a href="${memberfild.site}" target="_blank"><bean:message key="member_homepage"/></a></p></c:if>
	<c:if test="${usergroups.allowviewpro>0}"><p><a href="space.jsp?action=viewpro&uid=${member.uid}" target="_blank"><bean:message key="member_viewpro"/></a></p></c:if>
	<c:if test="${usergroups.allowedituser>0}"><p><c:choose> <c:when test="${jsprun_adminid==1}"><a href="admincp.jsp?action=members&uids=${member.uid}&searchsubmit=yes&frames=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank"><bean:message key="menu_member_edit"/></a></c:when> <c:otherwise><a href="admincp.jsp?action=editmember&uid=${member.uid}&membersubmit=yes&frames=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank"><bean:message key="menu_member_edit"/></a></c:otherwise></c:choose></p></c:if>
	<c:if test="${usergroups.allowbanuser>0}"><p><a href="admincp.jsp?action=banmember&amp;uid=${member.uid}&amp;membersubmit=yes&amp;frames=yes" target="_blank"><bean:message key="menu_member_ban"/></a></p></c:if>
</div>