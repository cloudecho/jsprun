<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <bean:message key="invite_get"/></div>
<div class="container">
	<div class="content">
		<form method="post" action="invite.jsp?action=sendinvite">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="sendsubmit" value="yes">
			<input type="hidden" name="invitecode" value="${invitecode}" />
			<div class="mainbox formbox">
				<h1><bean:message key="emailinvite"/></h1>
				<table summary="<bean:message key="emailinvite"/>" cellspacing="0" cellpadding="0">
					<tr>
						<th><label for="sendtoemail"><bean:message key="emailfriend_receiver_email"/></label></th>
						<td><input type="text" name="sendtoemail" id="sendtoemail" size="25" /></td>
					</tr>
					<tr>
						<th valign="top"><label for="message"><bean:message key="message"/></label></th>
						<td><textarea cols="70" rows="8" name="message" id="message" style="width: 98%;"><bean:message key="emailinvite_message" arg0="${settings.bbname}" arg1="${boardurl}${settings.regname}?invitecode=${invitecode}${fromuid}"/></textarea></td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td><button type="submit" class="submit" name="sendsubmit" value="sendsubmit"><bean:message key="send"/></button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="side"><jsp:include flush="true" page="personal_navbar.jsp" /></div>
</div>
<jsp:include flush="true" page="footer.jsp" />