<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div class="container">
	<div id="foruminfo"><div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <bean:message key="memcp_usergroups"/></div></div>
	<div class="content">
		<div class="mainbox formbox">
			<h1><bean:message key="memcp_usergroups"/></h1>
			<c:if test="${type==null}">
				<form method="post" action="memcp.jsp?action=usergroups&type=main">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
					<table summary="<bean:message key="memcp_usergroups"/>" cellspacing="0" cellpadding="0" width="100%" align="center">
						<thead><tr><td><bean:message key="memcp_usergroups_title"/></td><td><bean:message key="memcp_usergroups_dailyprice"/></td><td><bean:message key="memcp_usergroups_minspan"/></td><td class="time"><bean:message key="a_post_forums_recommend_expiration"/></td><td><bean:message key="extgroups"/></td><td><bean:message key="memcp_usergroups_join_main"/></td></tr></thead>
						<c:forEach items="${grouplist}" var="groups">
							<tr>
								<td><c:choose><c:when test="${groups.isspacal}"><u>${groups.grouptitle}</u></c:when><c:when test="${groups.isextgroup}"><b><i>${groups.grouptitle}</i></b></c:when><c:otherwise>${groups.grouptitle}</c:otherwise></c:choose></td>
								<td>${groups.price}</td>
								<td>${groups.days}</td>
								<td><jrun:showTime timeInt="${groups.dateline}" type="${dateformat}" timeoffset="${timeoffset}"/></td>
								<td>
								<c:choose>
									<c:when test="${groups.iscurrgroup}">
										<c:choose>
											<c:when test="${groups.isextgroup}"><a href="memcp.jsp?action=usergroups&type=toextexit&edit=${groups.groupid}&formHash=${jrun:formHash(pageContext.request)}&groupsubmit=true">[<bean:message key="menu_logout"/>]</a></c:when>
											<c:otherwise><a href="memcp.jsp?action=usergroups&type=toextadd&edit=${groups.groupid}&formHash=${jrun:formHash(pageContext.request)}&groupsubmit=true"><b>[<bean:message key="memcp_usergroups_join"/>]</b></a></c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>&nbsp;</c:otherwise>
								</c:choose>
								</td>
								<td><input type="radio" name="groupidnew" value="${groups.groupid}" ${groups.ismaingroup?"checked":""} ${inadmin || groups.isaddgroup || groups.groupid==1 || groups.groupid==2 || groups.groupid==3?"disabled":""}/></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="6">
								<bean:message key="memcp_usergroups_comment"/>
							</td>
						</tr>
						<tr class="btns"><td colspan="6"><button class="submit" type="submit" name="groupsubmit" id="groupsubmit" value="true" tabindex="1"><bean:message key="submitf"/></button></td>
					</table>
				</form>
			</c:if>
			<c:if test="${type=='toextexit'}">
				<form method="post" action="memcp.jsp?action=usergroups&type=toextexit&submit=yes&edit=${param.edit}">
					<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
					<table summary="<bean:message key="memcp_usergroups"/>" cellspacing="0" cellpadding="0" width="100%" align="center">
						<thead><tr><td colspan="2"><bean:message key="menu_logout"/></td></tr></thead>
						<tbody>
							<tr><th><bean:message key="username"/>:</th><td>${membername} <span class="smalltxt">[<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</span></td></tr>
							<tr><th><bean:message key="menu_member_usergroups"/>:</th><td>${grouptitle}</td></tr>
						</tbody>
					</table>
					<p class="btns"><button type="submit" name="groupsubmit" id="groupsubmit" value="true" tabindex="2"><bean:message key="submitf"/></button></p>
				</form>
			</c:if>
			<c:if test="${type=='toextadd'}">
				<form method="post" action="memcp.jsp?action=usergroups&type=toextadd&submit=yes&edit=${param.edit}">
					<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
					<table summary="<bean:message key="memcp_usergroups"/>" cellspacing="0" cellpadding="0" width="100%" align="center">
						<thead><tr><td colspan="2"><bean:message key="memcp_usergroups_join"/></td></tr></thead>
						<tr><th><bean:message key="username"/>:</th><td>${membername} <span class="smalltxt">[<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</span></td></tr>
						<tr><th><bean:message key="menu_member_usergroups"/>:</th><td>${grouptitle}</td></tr>
						<tr><th><bean:message key="memcp_usergroups_dailyprice"/>:</th><td>${price}</td></tr>
						<tr><th><bean:message key="memcp_usergroups_minspan"/>:</th><td>${days} <bean:message key="ipban_days"/></td></tr>
						<tr><th><bean:message key="magics_dateline_buy"/>:</th><td><input type="text" size="5" name="days" value="${days}" /> <bean:message key="ipban_days"/></td></tr>
						<tr><td colspan="2"><bean:message key="memcp_usergroups_join_comment" arg0="${pricenum}" arg1="${days}" /></td></tr>
					</table>
					<p class="btns"><button type="submit" name="groupsubmit" id="groupsubmit"value="true" tabindex="2"><bean:message key="submitf"/></button></p>
				</form>
			</c:if>
		</div>
	</div>
	<div class="side"><jsp:include flush="true" page="personal_navbar.jsp" /></div>
</div>
<jsp:include flush="true" page="footer.jsp" />