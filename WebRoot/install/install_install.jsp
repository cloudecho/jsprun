<%@ page language="java" import="java.util.*,cn.jsprun.utils.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ include file="install_method.jsp"%>
<jsp:include flush="true" page="install_header.jsp" />
<tr>
	<td><b><bean:message key="a_member_edit_current_status"/>: </b><font color="#0000EE"> <bean:message key="start_install"/></font></td>
</tr>
<tr>
	<td><hr noshade align="center" width="100%" size="1"></td>
</tr>
<tr>
	<td align="center">
		<br />
		<script type="text/javascript">
			function showmessage(message) {
				document.getElementById('notice').value += message + "\r\n";
			}
		</script>
		<textarea name="notice" style="width: 80%; height: 400px" readonly id="notice"></textarea>
		<br />
		<br />
		<input type="button" name="submit" value=" <bean:message key="install_in_processed"/> " disabled style="height: 25" id="laststep" onclick="window.location='index.jsp'">
		<br />
		<br />
		<br />
	</td>
</tr>
<jsp:include flush="true" page="install_footer.jsp" />
<%
	try {
		String realPath=JspRunConfig.realPath;
		String username = Common.decode(request.getParameter("username"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int salt = Common.rand(100000, 999999);
		password = Md5Token.getInstance().getLongToken(password+salt);
		MessageResources mr = Common.getMessageResources(request);
		Locale locale = Common.getUserLocale(request);
		Properties prop=(Properties)request.getAttribute("prop");
		connect(prop.getProperty("dbhost"),prop.getProperty("dbport"),prop.getProperty("dbname"),prop.getProperty("dbuser"),prop.getProperty("dbpw"),mr,locale);
		File file=new File(realPath+session.getAttribute("sqlFile"));
		StringBuffer sqlSB=new StringBuffer();
		if(file!=null&&file.canRead()){
			InputStream in=new FileInputStream(realPath+session.getAttribute("sqlFile"));
			InputStreamReader ir=new InputStreamReader(in,JspRunConfig.CHARSET);
			BufferedReader br=new BufferedReader(ir);
			String sqlContent=br.readLine();
			while(sqlContent!=null)
			{
				if((!sqlContent.startsWith("--"))&&(!sqlContent.startsWith("#")))
				{
					sqlSB.append(sqlContent+"\n");
				}
				sqlContent=br.readLine();
			}
			br.close();
			ir.close();
			in.close();
		}
		String tablepre=prop.getProperty("tablepre");
		runquery(response,sqlSB.toString(),tablepre,(String)request.getAttribute("dbcharset"),mr,locale);
		Calendar calendar=Calendar.getInstance();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		
		String backupdir=Common.getRandStr(6,false);
		File backupdirfile=new File(realPath+"/forumdata/backup_"+backupdir);
		backupdirfile.mkdir();
		String authkey =Md5Token.getInstance().getLongToken(request.getServerName()+prop.getProperty("dbhost")+prop.getProperty("dbuser")+prop.getProperty("dbpw")+prop.getProperty("dbname")+username+password+String.valueOf(timestamp).substring(0,6)).substring(8,14)+Common.getRandStr(10,false);
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		String siteuniqueid =chars.charAt(calendar.get(Calendar.YEAR)%60)+chars.charAt(calendar.get(Calendar.MONTH))+chars.charAt(calendar.get(Calendar.DATE))+chars.charAt(calendar.get(Calendar.AM_PM))+chars.charAt(calendar.get(Calendar.HOUR))+chars.charAt(calendar.get(Calendar.MINUTE))+Md5Token.getInstance().getLongToken(request.getLocalAddr()+timestamp).substring(0,4)+Common.getRandStr(6,false);
		mysql_update("REPLACE INTO "+tablepre+"settings (variable, value) VALUES ('authkey', '"+authkey+"')");
		mysql_update("REPLACE INTO "+tablepre+"settings (variable, value) VALUES ('siteuniqueid', '"+siteuniqueid+"')");
		
		mysql_update("REPLACE INTO "+tablepre+"settings (variable, value) VALUES ('backupdir', '"+backupdir+"')");
		mysql_update("REPLACE INTO "+tablepre+"settings (variable, value) VALUES ('attachdir', './attachments')");
		mysql_update("REPLACE INTO "+tablepre+"settings (variable, value) VALUES ('authkey', '"+authkey+"')");
		mysql_update("REPLACE INTO "+tablepre+"settings (variable, value) VALUES ('attachurl', 'attachments')");
		mysql_update("DELETE FROM "+tablepre+"members");
		mysql_update("DELETE FROM "+tablepre+"memberfields");
		mysql_update("INSERT INTO "+tablepre+"members VALUES ('1','"+username+"','"+password+"','','0','1','1','0','','hidden','"+timestamp+"','"+Common.get_onlineip(request)+"','0','"+timestamp+"','"+timestamp+"','0','0','1','0','0','0','0','0','0','0','0','0','0','"+email+"','0000-00-00','0','0','0','0','0','0','0','1','1','0','9999','0','0','2','26','"+salt+"');");
		mysql_update("INSERT INTO jrun_memberfields VALUES ('1','','','','','','','','','','','','','0','0','','','','','','','0','0')");
		mysql_update("UPDATE "+tablepre+"crons SET lastrun='0', nextrun='"+(timestamp)+"'");
		mysql_update("ALTER TABLE "+tablepre+"typeoptions AUTO_INCREMENT=3001");
		
		loginit(response,"ratelog",timestamp,mr,locale);
		loginit(response,"illegallog",timestamp,mr,locale);
		loginit(response,"modslog",timestamp,mr,locale);
		loginit(response,"cplog",timestamp,mr,locale);
		loginit(response,"errorlog",timestamp,mr,locale);
		loginit(response,"banlog",timestamp,mr,locale);
		
		dir_clear(response,realPath,"./forumdata/templates",mr,locale);
		dir_clear(response,realPath,"./forumdata/cache",mr,locale);
		dir_clear(response,realPath,"./forumdata/threadcaches",mr,locale);
		
		new File(realPath+"./forumdata/install.lock").createNewFile();
		HibernateUtil.rebuildSessionFactory();
		ServletContext context=request.getSession().getServletContext();
		ForumInit.initServletContext(context);
		Cache.updateCache();
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<script type="text/javascript">document.getElementById("laststep").disabled = false; </script>
<script type="text/javascript">document.getElementById("laststep").value = '<bean:message key="install_succeed"/>'; </script>