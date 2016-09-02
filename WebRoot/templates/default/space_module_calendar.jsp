<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<table class="module" cellpadding="0" cellspacing="0" border="0">
	<tr><td class="header"><div class="title"><bean:message key="calendar"/></div></td></tr>
	<tr>
		<td>
			<table id="module_calendar" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
				<tr class="header">
					<td colspan="7">
						<table cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td width="30%" align="right"><a href="space.jsp?action=myblogs&uid=${member.uid}&begin=${beforbegintime}&end=${beforendtime}">&laquo;</a></td>
								<td width="40%" align="center" nowrap>${year}-${month}</td>
								<td width="30%" align="left"><c:if test="${afterbegintime!=null}"><a href="space.jsp?action=myblogs&uid=${member.uid}&begin=${afterbegintime}&end=${afterendtime}">&raquo;</a></c:if></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr class="header1" align="center">
					<td><bean:message key="day"/></td>
					<td><bean:message key="week_mon"/></td>
					<td><bean:message key="week_tues"/></td>
					<td><bean:message key="week_wed"/></td>
					<td><bean:message key="week_thurs"/></td>
					<td><bean:message key="week_fri"/></td>
					<td><bean:message key="week_sat"/></td>
				</tr>
				<tr class="row" align="center">
				<c:forEach begin="1" end="${week}" varStatus="ins">
					<td>&nbsp;</td>
					<c:set scope="page" value="${ins.count}" var="num"></c:set>
				</c:forEach>
				<c:forEach begin="1" end="${days}" var="dis" varStatus="ins">
					<c:choose>
						<c:when test="${blogs[dis]!=null}"><td><a href="space.jsp?action=myblogs&uid=${member.uid}&begin=${blogs[dis]['dateline']}&end=${blogs[dis]['dateline']+86400}" title="${blogs[dis]['num']} ">${dis}</a></td></c:when>
						<c:otherwise><td>${dis}</td></c:otherwise>
					</c:choose>
					<c:if test="${(num+ins.count)%7==0}">
						</tr><tr class="row" align="center">
					</c:if>
				</c:forEach>
				</tr>
			</table>
		</td>
	</tr>
</table>