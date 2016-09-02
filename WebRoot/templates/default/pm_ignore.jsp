<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<form method="post" action="pm.jsp?action=saveignore">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox formbox">
		<h1> <bean:message key="pm_ignore"/> </h1>
		<jsp:include flush="true" page="pm_navbar.jsp" />
		<table summary="<bean:message key="pm_ignore"/>" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<textarea rows="5" cols="70" id="ignorelist" name="ignorelist" style="width: 98%;">${memberfild.ignorepm}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<button type="submit" class="submit" name="ignoresubmit" value="true">
						<bean:message key="submitf"/>
					</button>
				</td>
			</tr>
			<tr>
				<td>
					<div class="tisp">
						<bean:message key="pm_ignore_comment" arg0="{all}"/>
					</div>
				</td>
			</tr>
		</table>
	</div>
</form>