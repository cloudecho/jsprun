<%@ page language="java" import="java.util.Properties,cn.jsprun.utils.Config" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ include file="install_method.jsp" %>
<%
	MessageResources mr = Common.getMessageResources(request);
	Locale locale = Common.getUserLocale(request);
	Boolean write_error=(Boolean)request.getAttribute("write_error");
	String inputreadonly = write_error!=null&&write_error? "readonly" : "";
	String msg = "<li>"+mr.getMessage(locale,"config_comment")+"</li>";
	Properties prop=(Properties)request.getAttribute("prop");
	if(request.getParameter("saveconfig")!=null)
	{	
		prop.setProperty("dbhost",request.getParameter("dbhost"));
		prop.setProperty("dbport",request.getParameter("dbport"));
		prop.setProperty("dbuser",request.getParameter("dbuser"));
		prop.setProperty("dbpw",request.getParameter("dbpw"));
		prop.setProperty("dbname",request.getParameter("dbname"));
		prop.setProperty("adminemail",request.getParameter("adminemail"));
		prop.setProperty("tablepre",request.getParameter("tablepre"));
		String dbname=prop.getProperty("dbname");
		boolean quit=false;
		if(dbname==null||dbname.equals(""))
		{
			msg += "<li>"+mr.getMessage(locale,"dbname_invalid")+"</li>";
			quit = true;
		}
		else{
			if(!mysql_connect(prop.getProperty("dbhost"),prop.getProperty("dbport"), prop.getProperty("dbuser"), prop.getProperty("dbpw"),mr,locale)) {
				msg += "<li>"+mysql_errno()+"</li>";
				quit = true;
			} else {
				if(mysql_get_server_info().compareTo("4.1")>0) {
					mysql_update("CREATE DATABASE IF NOT EXISTS `"+prop.getProperty("dbname")+"` DEFAULT CHARACTER SET "+request.getAttribute("dbcharset"));
				} else {
					mysql_update("CREATE DATABASE IF NOT EXISTS `"+prop.getProperty("dbname")+"`");
				}
				if(mysql_errno()!=null) {
					msg += "<li>"+mysql_errno()+"</li>";
					quit = true;
				}
				mysql_close();
			}
		}
		if(prop.getProperty("tablepre").indexOf(".")>0) {
			msg += "<li>"+mr.getMessage(locale,"tablepre_invalid")+"</li>";
			quit = true;
		}

		if(!quit){
			if(!write_error) {
				Config localConfig=new Config(JspRunConfig.realPath+"config.properties");
				localConfig.setProperties(prop);
				localConfig.saveProperties("Config Info");
			}
			response.setStatus(response.SC_MOVED_PERMANENTLY);
			response.setHeader("Location","install.jsp?action=admin");
		}
	}
 %>
<jsp:include flush="true" page="install_header.jsp" />
<tr><td><b><bean:message key="a_member_edit_current_status"/>: </b><font color="#0000EE"> <bean:message key="edit_config"/></font></td></tr>
<tr><td><hr noshade align="center" width="100%" size="1"></td></tr>
<tr><td><br /></td></tr>
<tr>
	<td align="center">
		<table width="80%" cellspacing="1" bgcolor="#000000" border="0" align="center">
			<tr bgcolor="#3A4273"><td style="color: #FFFFFF; padding-left: 10px" width="32%"><bean:message key="board_message"/></td></tr>
			<tr><td class="message"><%=msg%></td></tr>
		</table>
		<br />
		<form method="post" action="install.jsp">
			<table width="80%" cellspacing="1" bgcolor="#000000" border="0" align="center">
				<tr class="header">
					<td width="20%"><bean:message key="variable"/></td>
					<td width="30%"><bean:message key="value_install"/></td>
					<td width="50%"><bean:message key="comment"/></td>
				</tr>
				<tr>
					<td class="altbg1">&nbsp;<span class="redfont"><bean:message key="dbhost"/></span></td>
					<td class="altbg2"><input type="text" name="dbhost" value="${prop.dbhost}" <%=inputreadonly%> size="30"></td>
					<td class="altbg1">&nbsp;<bean:message key="dbhost_comment"/></td>
				</tr>
				<tr>
					<td class="altbg1">&nbsp;<span class="redfont"><bean:message key="dbport"/></span></td>
					<td class="altbg2"><input type="text" name="dbport" value="${prop.dbport}" <%=inputreadonly%> size="30"></td>
					<td class="altbg1">&nbsp;<bean:message key="dbport_comment"/></td>
				</tr>
				<tr>
					<td class="altbg1">&nbsp;<bean:message key="dbuser"/></td>
					<td class="altbg2"><input type="text" name="dbuser" value="${prop.dbuser}" <%=inputreadonly%> size="30"></td>
					<td class="altbg1">&nbsp;<bean:message key="dbuser_comment"/></td>
				</tr>
				<tr>
					<td class="altbg1">&nbsp;<bean:message key="dbpw"/></td>
					<td class="altbg2"><input type="password" name="dbpw" value="${prop.dbpw}" <%=inputreadonly%> size="30"></td>
					<td class="altbg1">&nbsp;<bean:message key="dbpw_comment"/></td>
				</tr>
				<tr>
					<td class="altbg1">&nbsp;<bean:message key="dbname"/></td>
					<td class="altbg2"><input type="text" name="dbname" value="${prop.dbname}" <%=inputreadonly%> size="30"></td>
					<td class="altbg1">&nbsp;<bean:message key="dbname_comment"/></td>
				</tr>
				<tr>
					<td class="altbg1">&nbsp;<bean:message key="install_email"/></td>
					<td class="altbg2"><input type="text" name="adminemail" value="${prop.adminemail}" <%=inputreadonly%>	size="30"></td>
					<td class="altbg1">&nbsp;<bean:message key="email_comment"/></td>
				</tr>
				<tr>
					<td class="altbg1">&nbsp;<span class="redfont"><bean:message key="tablepre"/></span></td>
					<td class="altbg2"><input type="text" name="tablepre" value="${prop.tablepre}" readonly size="30"></td>
					<td class="altbg1">&nbsp;<bean:message key="tablepre_comment"/></td>
				</tr>
			</table>
			<br />
			<input type="hidden" name="action" value="config">
			<input type="hidden" name="saveconfig" value="1">
			<input type="button" name="submit" value=" <bean:message key="old_step"/> " style="height: 25" onclick="window.location='?action=check'">
			&nbsp;
			<input type="submit" name="submit" value=" <bean:message key="new_step"/> " style="height: 25">
		</form>
	</td>
</tr>