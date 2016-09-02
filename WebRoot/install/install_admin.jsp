<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ include file="install_method.jsp" %>
<%
	MessageResources mr = Common.getMessageResources(request);
	Locale locale = Common.getUserLocale(request);
	Properties prop=(Properties)request.getAttribute("prop");
	String msg="<li>"+mr.getMessage(locale,"add_admin")+"</li>";
	String alert="";
	boolean quit=false;
	if(!mysql_connect(prop.getProperty("dbhost"),prop.getProperty("dbport"), prop.getProperty("dbuser"), prop.getProperty("dbpw"),mr,locale)) {
		msg += "<li>"+mysql_errno()+"</li>";
		quit = true;
	} else {
		String curr_mysql_version = mysql_get_server_info();
		if(curr_mysql_version.compareTo("3.23") < 0) {
			msg += "<li>"+mr.getMessage(locale,"mysql_version_323")+curr_mysql_version+". </li>";
			quit = true;
		}
		Map<Integer,String> sqlMap=new TreeMap<Integer,String>();
		sqlMap.put(1,"CREATE TABLE jrun_test (test TINYINT (3) UNSIGNED)");
		sqlMap.put(2,"INSERT INTO jrun_test (test) VALUES (1)");
		sqlMap.put(3,"SELECT * FROM jrun_test");
		sqlMap.put(4,"UPDATE jrun_test SET test='2' WHERE test='1'");
		sqlMap.put(5,"DELETE FROM jrun_test WHERE test='2'");
		sqlMap.put(6,"DROP TABLE jrun_test");
		Map<Integer,String> keyMap=new HashMap<Integer,String>();
		keyMap.put(1,mr.getMessage(locale,"dbpriv_createtable"));
		keyMap.put(2,mr.getMessage(locale,"dbpriv_insert"));
		keyMap.put(3,mr.getMessage(locale,"dbpriv_select"));
		keyMap.put(4,mr.getMessage(locale,"dbpriv_update"));
		keyMap.put(5,mr.getMessage(locale,"dbpriv_delete"));
		keyMap.put(6,mr.getMessage(locale,"dbpriv_droptable"));
		Set<Integer> keys=sqlMap.keySet();
		mysql_select_db(prop.getProperty("dbname"));
		for(Integer key:keys)
		{
			if(key==3){
				mysql_query(sqlMap.get(key));
			}
			else{
				mysql_update(sqlMap.get(key));
			}
			if(mysql_errno()!=null) {
				msg +="<li>"+keyMap.get(key)+"</li>";
				quit = true;
			}
		}
		ResultSet rs=mysql_query("SELECT COUNT(*) FROM "+prop.getProperty("tablepre")+"settings");
		if(rs!=null&&rs.next()) {
			msg += "<li><font color=\"#FF0000\">"+mr.getMessage(locale,"db_not_null")+"</font></li>";
			alert = " onsubmit=\"return confirm('"+mr.getMessage(locale,"db_drop_table_confirm")+"');\"";
		}
	}
	if(request.getParameter("submit")!=null)
	{
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if(!"".equals(username)&&!"".equals(email)&&!"".equals(password1)&&!"".equals(password2))
		{
			if(!password1.equals(password2))
			{
				msg += "<li><font color=\"#FF0000\">"+mr.getMessage(locale,"admin_password_invalid")+"</font></li>";
				quit = true;
			}else if(username.getBytes().length > 15 || Common.matches(username,"^$|^c:\\con\\con$|　|[,\"\\s\\t\\<\\>&]|^"+mr.getMessage(locale,"guest")+"|^Guest")) {
				msg = mr.getMessage(locale,"admin_username_invalid");
				quit = true;
			} else if(!Common.isEmail(email)) {
				msg = mr.getMessage(locale,"admin_email_invalid");
				quit = true;
			}
		}
		else{
			msg += "<li><font color=\"#FF0000\">"+mr.getMessage(locale,"admin_invalid")+"</font></li>";
			quit = true;
		}
		if(!quit){
			response.setStatus(response.SC_MOVED_PERMANENTLY);
			response.setHeader("Location","install.jsp?action=install&username="+Common.encode(Common.encode(username))+"&email="+email+"&password="+Md5Token.getInstance().getLongToken(password1));
		}

	}
%>
<jsp:include flush="true" page="install_header.jsp" />
<tr>
	<td>
		<b><bean:message key="a_member_edit_current_status"/>: </b><font color="#0000EE"> <bean:message key="check_env"/></font>
	</td>
</tr>
<tr>
	<td>
		<hr noshade align="center" width="100%" size="1">
	</td>
</tr>
<tr>
	<td>
		<br />
<tr>
	<td align="center">
		<table width="80%" cellspacing="1" bgcolor="#000000" border="0"
			align="center">
			<tr bgcolor="#3A4273">
				<td style="color: #FFFFFF; padding-left: 10px" width="32%">
					<bean:message key="board_message"/>
				</td>
			</tr>
			<tr>
				<td class="message">
					<%=msg %>
				</td>
			</tr>
		</table>
		<br />
	</td>
</tr>
<tr>
	<td align="center">
		<form method="post" action="install.jsp" <%=alert %>>
			<table width="80%" cellspacing="1" bgcolor="#000000" border="0"
				align="center">
				<tr bgcolor="#3A4273">
					<td style="color: #FFFFFF; padding-left: 10px" colspan="2">
						<bean:message key="add_admin"/>
					</td>
				</tr>
				<tr>
					<td class="altbg1" width="20%">
						&nbsp;<bean:message key="admin_username"/>
					</td>
					<td class="altbg2" width="80%">
						&nbsp;
						<input type="text" name="username" value="admin" size="30">
					</td>
				</tr>
				<tr>
					<td class="altbg1">
						&nbsp;<bean:message key="admin_email"/>
					</td>
					<td class="altbg2">
						&nbsp;
						<input type="text" name="email" value="name@domain.com" size="30">
					</td>
				</tr>
				<tr>
					<td class="altbg1">
						&nbsp;<bean:message key="admin_password"/>
					</td>
					<td class="altbg2">
						&nbsp;
						<input type="password" name="password1" size="30">
					</td>
				</tr>
				<tr>
					<td class="altbg1">
						&nbsp;<bean:message key="repeat_password"/>
					</td>
					<td class="altbg2">
						&nbsp;
						<input type="password" name="password2" size="30">
					</td>
				</tr>
			</table>
			<br />
			<input type="hidden" name="action" value="admin">
			<input type="button" name="submit" value=" <bean:message key="old_step"/> " style="height: 25"
				onclick="window.location='?action=config'">
			&nbsp;
			<input type="submit" name="submit" value=" <bean:message key="new_step"/> " style="height: 25">
		</form>
	</td>
</tr>