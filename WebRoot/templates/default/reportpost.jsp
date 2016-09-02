<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<c:choose>
	<c:when test="${param.inajax==null}">
		<jsp:include flush="true" page="header.jsp" />
		<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; ${navigation} &raquo; <bean:message key="reportpost"/></div>
		<form method="post" name="input" action="misc.jsp?action=report" id="reportpostform">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="page" value="${param.page}">
		<div class="mainbox formbox">
			<h1><bean:message key="reportpost"/></h1>
			<table summary="<bean:message key="reportpost"/>" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th><bean:message key="username"/></th>
						<td>${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</td>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${reportpost==1}">
						<input type="hidden" name="to3" value="3" />
					</c:when>
					<c:otherwise>
						<tr>
						<th><bean:message key="reportpost_to"/></th>
						<td>
						<c:if test="${reportpost==3}"><label><input class="checkbox" type="checkbox" name="to1" value="1"> <bean:message key="admin"/></label> &nbsp;</c:if>
						<c:if test="${reportpost>=2}"><label><input class="checkbox" type="checkbox" name="to2" value="2"> <bean:message key="usergroups_system_2"/></label> &nbsp;</c:if>	
						<label><input class="checkbox" type="checkbox" name="to3" value="yes" checked="checked" /> <bean:message key="usergroups_system_3"/></label>	
						</td>
					</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<th valign="top"><label for="reason"><bean:message key="reportpost_suggestion"/></label></th>
					<td><textarea rows="9" id="reason" name="reason" style="width: 98%;"><bean:message key="reportpost_reason"/></textarea></td>
				</tr>
				<tr class="btns">
					<th>&nbsp;</th>
					<td>
						<input type="hidden" name="tid" value="${tid}" />
						<input type="hidden" name="fid" value="${fid}" />
						<input type="hidden" name="pid" value="${pid}" />
						<button class="submit" type="submit" name="reportsubmit" value="true"><bean:message key="reportpost"/></button>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<jsp:include flush="true" page="footer.jsp" />
	</c:when>
	<c:otherwise>
		<div class="ajaxform">
		<form method="post" action="misc.jsp?action=report&inajax=1&reportsubmit=yes" id="reportpostform">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="page" value="${param.page}">
			<table summary="<bean:message key="reportpost"/>" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th><bean:message key="reportpost"/></th>
						<td align="right"><a href="javascript:hideMenu();"><img src="images/spaces/close.gif"  title="<bean:message key="closed"/>" /></a></td>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${reportpost==1}">
						<input type="hidden" name="to3" value="3" />
					</c:when>
					<c:otherwise>
						<tr>
						<th><bean:message key="reportpost_to"/></th>
						<td>
						<c:if test="${reportpost==3}"><label><input class="checkbox" type="checkbox" name="to1" value="1"> <bean:message key="admin"/></label> &nbsp;</c:if>
						<c:if test="${reportpost>=2}"><label><input class="checkbox" type="checkbox" name="to2" value="2"> <bean:message key="usergroups_system_2"/></label> &nbsp;</c:if>	
						<label><input class="checkbox" type="checkbox" name="to3" value="yes" checked="checked" /> <bean:message key="usergroups_system_3"/></label>	
						</td>
					</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<th valign="top"><label for="reason"><bean:message key="reportpost_suggestion"/></label></th>
					<th><textarea rows="4" id="reason" name="reason" style="width: 300px; height: 5em"><bean:message key="reportpost_reason"/></textarea>
				</tr>
				<tr class="btns">
					<th>&nbsp;</th>
					<td>
						<input type="hidden" name="tid" value="${tid}" />
						<input type="hidden" name="fid" value="${fid}" />
						<input type="hidden" name="pid" value="${pid}" />
						<button class="submit" type="button" name="reportsubmit" value="true" onclick="ajaxpost('reportpostform', 'ajax_report_${pid}_menu');"><bean:message key="reportpost"/></button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	</c:otherwise>
</c:choose>