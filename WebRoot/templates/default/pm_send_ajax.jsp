<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<div class="ajaxform" style="width: 500px;">
	<form method="post" id="postpmform" name="postpmform" action="pm.jsp?action=send&inajax=1">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="pmssubmit" value="yes" />
		<table cellspacing="0" cellpadding="0" width="100%">
			<thead>
				<tr>
					<th><bean:message key="pm_send"/> </th>
					<td align="right">
						<a href="javascript:hideMenu();"><img src="images/spaces/close.gif"  title="<bean:message key="closed"/>" /> </a>
					</td>
				</tr>
			</thead>
			<tr>
				<th> <bean:message key="to"/> </th>
				<td> <input type="text" name="msgto" size="65" value="${tosendmembers.username}" /> </td>
			</tr>
			<tr>
				<th> <bean:message key="subject"/> </th>
				<td> <input type="text" name="subject" size="65" /> </td>
			</tr>
			<tr>
				<th> <bean:message key="message"/> <br /> <br /> </th>
				<td>
					<textarea id="pm_textarea" rows="6" cols="10" name="message" style="width: 100%; height:100px; word-break: break-all" onKeyDown="ctlent(event);"></textarea>
					<br />
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<button name="pmssubmit" type="button" class="submit" value="true" onclick="ajaxpost('postpmform', 'ajax_uid_${pid}_menu')"> <bean:message key="submitf"/> </button>
				</td>
		</table>
	</form>
</div>
