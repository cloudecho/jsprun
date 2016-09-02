<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<html>
	<head>
		<title>JspRun! - JAVA.NO.1<bean:message
				key="admincp_community" /> Installation Wizard ${version}</title>
		<link rel="SHORTCUT ICON" href="favicon.ico" />
		<link rel="stylesheet" type="text/css" href="install/style.css">
	</head>
	<body bgcolor="#3A4273" text="#000000">
		<form method="post" action="install.jsp">
			<table width="95%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" align="center">
				<tr>
					<td>
						<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
							<tr>
								<td class="install" height="30" valign="bottom">
									<font color="#FF0000">&gt;&gt;</font> JspRun! ${version}&nbsp;<bean:message key="install_wizard" />
								</td>
							</tr>
							<tr>
								<td><hr noshade align="center" width="100%" size="1"></td>
							</tr>
							<tr>
								<td><b><bean:message key="a_member_edit_current_status"/>: </b><font color="#0000EE">JspRun! <bean:message key="select_forum_language"/></font></td>
							</tr>
							<tr>
								<td><hr noshade align="center" width="100%" size="1"></td>
							</tr>
							<tr>
								<td align="center" height="473" style="padding-bottom:60px;">
									<table width="60%" cellspacing="1" bgcolor="#000000" border="0" align="center">
										<tr bgcolor="#3A4273">
											<td style="color: #FFFFFF; padding-left: 10px" width="32%"> <bean:message key="select_forum_language_title"/>:</td>
										</tr>
										<tr>
											<td class="message" align="center">
												<ul>
													<li><bean:message key="select_forum_language_tip"/>
												</ul>
												<select name="language">
													<option value="zh_cn" selected="selected"><bean:message key="language_zh_cn"/></option>
													<option value="zh_tw"><bean:message key="language_zh_tw"/></option>
												</select>
												<input type="hidden" name="action" value="changeLanguage" /><br>
											</td>
										</tr>
									</table><br>
									<input type="submit" name="submit" value="<bean:message key="new_step"/>" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>