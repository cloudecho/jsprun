<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="install_header.jsp" />
<tr><td><b><bean:message key="a_member_edit_current_status"/>: </b><font color="#0000EE"><bean:message key="show_license"/></font></td></tr>
<tr><td><hr noshade align="center" width="100%" size="1"></td></tr>
<tr>
	<td>
		<br />
		<table width="90%" cellspacing="1" bgcolor="#000000" border="0" align="center">
			<tr>
				<td class="altbg1">
					<table width="99%" cellspacing="1" border="0" align="center">
						<tr>
							<td><bean:message key="license"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td align="center">
		<br />
		<form method="post" action="install.jsp">
			<input type="hidden" name="action" value="check">
			<input type="submit" name="submit" value="<bean:message key="agreement_yes"/>" style="height: 25">&nbsp;
			<input type="button" name="exit" value="<bean:message key="agreement_no"/>" style="height: 25" onclick="javascript: window.close();">
		</form>
	</td>
</tr>