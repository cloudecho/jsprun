<%@ page language="java" import="java.util.*,java.io.*,cn.jsprun.utils.*,org.apache.struts.Globals,org.apache.struts.config.ModuleConfig" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ include file="install_method.jsp" %>
<jsp:include flush="true" page="install_header.jsp" />
<tr><td><b><bean:message key="a_member_edit_current_status"/>: </b><font color="#0000EE"> <bean:message key="check_config"/></font></td></tr>
<tr><td><hr noshade align="center" width="100%" size="1"></td></tr>
<tr><td><br /></td></tr>
<%
	MessageResources mr = Common.getMessageResources(request);
	Locale locale = Common.getUserLocale(request);
	String realPath=JspRunConfig.realPath;
	String configfile = "config.properties";
	String sqlfile = "./install/jsprun.sql";
	String lockfile = "./forumdata/install.lock";
	String attachdir = "./attachments";
	String attachurl = "attachments";
	boolean quit = false;
	String msg="";
	String curr_disk_space=null;
	long freeSpace=getFreeSpace("");
	if(freeSpace==-1){
		curr_disk_space=mr.getMessage(locale,"no_disk_space");
	}else{
		curr_disk_space = (freeSpace / (1024 * 1024))+"M";
	}
	File configFile=new File(realPath+configfile);
	request.setAttribute("configfile",configFile.canWrite());
	Map<String,String> checkdirarray = new HashMap<String,String>();
	checkdirarray.put("tpl","./templates");
	checkdirarray.put("avatar","./customavatars");
	checkdirarray.put("attach",attachdir);
	checkdirarray.put("forumdata","./forumdata");
	checkdirarray.put("ftemplate","./forumdata/templates");
	checkdirarray.put("cache","./forumdata/cache");
	checkdirarray.put("threadcache","./forumdata/threadcaches");
	checkdirarray.put("log","./forumdata/logs");
	Set<String> keys=checkdirarray.keySet();
	for(String key:keys)
	{
		if(dir_writeable(realPath+checkdirarray.get(key)))
		{
			checkdirarray.put(key,"true");
		}
		else{
			checkdirarray.put(key,"false");
			if(key.equals("tpl"))
			{
				msg += "<li>"+mr.getMessage(locale,"template_unwriteable")+"</li>";
			}
			if(key.equals("attach"))
			{
				msg += "<li>"+mr.getMessage(locale,"attach_unwriteable")+"</li>";
			}
			if(key.equals("avatar"))
			{
				msg += "<li>"+mr.getMessage(locale,"avatar_unwriteable")+"</li>";
			}
			if(key.equals("forumdata"))
			{
				msg += "<li>"+mr.getMessage(locale,"forumdata_unwriteable")+"</li>";
			}
			if(key.equals("femplate"))
			{
				msg += "<li>"+mr.getMessage(locale,"femplate_unwriteable")+"</li>";
			}
			if(key.equals("cache"))
			{
				msg += "<li>"+mr.getMessage(locale,"cache_unwriteable")+"</li>";
			}
			if(key.equals("threadcache"))
			{
				msg += "<li>"+mr.getMessage(locale,"threadcache_unwriteable")+"</li>";
			}
			if(key.equals("log"))
			{
				msg += "<li>"+mr.getMessage(locale,"log_unwriteable")+"</li>";
			}
			quit = true;
		}
	}
	ModuleConfig ac =(ModuleConfig) request.getAttribute(Globals.MODULE_KEY);
	request.setAttribute("maxupload", ac.getControllerConfig().getMaxFileSize());
	
	request.setAttribute("checkdirarray",checkdirarray);
	request.setAttribute("msg",msg);
	request.setAttribute("quit",quit);
 %>
<tr>
	<td align="center">
		<table width="80%" cellspacing="1" bgcolor="#000000" border="0"
			align="center">
			<tr bgcolor="#3A4273">
				<td style="color: #FFFFFF; padding-left: 10px" width="32%"><bean:message key="board_message"/></td>
			</tr>
			<tr>
				<td class="message">
					<c:choose>
						<c:when test="${quit}">${msg}</c:when>
						<c:otherwise>
							<bean:message key="preparation"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
		<br />
		<table width="80%" cellspacing="1" bgcolor="#000000" border="0" align="center">
			<tr class="header">
				<td></td>
				<td><bean:message key="env_required"/></td>
				<td><bean:message key="env_best"/></td>
				<td><bean:message key="env_current"/></td>
			</tr>
			<tr class="option">
				<td class="altbg1"><bean:message key="stats_os"/></td>
				<td class="altbg2"><bean:message key="unlimite"/></td>
				<td class="altbg1">UNIX/Linux/FreeBSD</td>
				<td class="altbg2"><%=System.getProperty("os.name")%></td>
			</tr>
			<tr class="option">
				<td class="altbg1">jdk <bean:message key="version"/></td>
				<td class="altbg2">1.5+</td>
				<td class="altbg1">1.5+</td>
				<td class="altbg2"><%=System.getProperty("java.version")%></td>
			</tr>
			<tr class="option">
				<td class="altbg1"><bean:message key="env_attach"/></td>
				<td class="altbg2"><bean:message key="unlimite"/></td>
				<td class="altbg1"><bean:message key="magics_permission_yes"/></td>
				<td class="altbg2"><bean:message key="attach_enabled"/> ${maxupload}</td>
			</tr>
			<tr class="option">
				<td class="altbg1"><bean:message key="env_mysql"/></td>
				<td class="altbg2"><bean:message key="support"/></td>
				<td class="altbg1"><bean:message key="support"/></td>
				<td class="altbg2"><bean:message key="support"/></td>
			</tr>
			<tr class="option">
				<td class="altbg1"><bean:message key="env_diskspace"/></td>
				<td class="altbg2">30M+</td>
				<td class="altbg1"><bean:message key="unlimite"/></td>
				<td class="altbg2"><%=curr_disk_space%></td>
			</tr>
		</table>
		<br />
		<table width="80%" cellspacing="1" bgcolor="#000000" border="0" align="center">
			<tr class="header">
				<td width="33%"><bean:message key="check_catalog_file_name"/></td>
				<td width="33%"><bean:message key="check_need_status"/></td>
				<td width="33%"><bean:message key="a_member_edit_current_status"/></td>
			</tr>
			<tr class="option">
				<td class="altbg1">config.properties</td>
				<td class="altbg2"><bean:message key="readable"/></td>
				<td class="altbg1"><c:choose><c:when test="${configfile=='true'}"><font color='#0000EE'><bean:message key="writeable"/></font></c:when><c:otherwise><font color='#FF0000'><bean:message key="unwriteable"/></font></c:otherwise></c:choose><br /></td>
			</tr>
			<tr class="option">
				<td class="altbg1">./templates</td>
				<td class="altbg2"><bean:message key="readable"/></td>
				<td class="altbg1"><c:choose><c:when test="${checkdirarray.tpl=='true'}"><font color='#0000EE'><bean:message key="writeable"/></font></c:when><c:otherwise><font color='#FF0000'><bean:message key="unwriteable"/></font></c:otherwise></c:choose><br /></td>
			</tr>
			<tr class="option">
				<td class="altbg1">./attachments</td>
				<td class="altbg2"><bean:message key="writeable"/></td>
				<td class="altbg1"><c:choose><c:when test="${checkdirarray.attach=='true'}"><font color='#0000EE'><bean:message key="writeable"/></font></c:when><c:otherwise><font color='#FF0000'><bean:message key="unwriteable"/></font></c:otherwise></c:choose><br /></td>
			</tr>
			<tr class="option">
				<td class="altbg1">./customavatars</td>
				<td class="altbg2"><bean:message key="writeable"/></td>
				<td class="altbg1"><c:choose><c:when test="${checkdirarray.avatar=='true'}"><font color='#0000EE'><bean:message key="writeable"/></font></c:when><c:otherwise><font color='#FF0000'><bean:message key="unwriteable"/></font></c:otherwise></c:choose><br /></td>
			</tr>
			<tr class="option">
				<td class="altbg1">./forumdata</td>
				<td class="altbg2"><bean:message key="writeable"/></td>
				<td class="altbg1"><c:choose><c:when test="${checkdirarray.forumdata=='true'}"><font color='#0000EE'><bean:message key="writeable"/></font></c:when><c:otherwise><font color='#FF0000'><bean:message key="unwriteable"/></font></c:otherwise></c:choose><br /></td>
			</tr>
			<tr class="option">
				<td class="altbg1">./forumdata/templates</td>
				<td class="altbg2"><bean:message key="writeable"/></td>
				<td class="altbg1"><c:choose><c:when test="${checkdirarray.ftemplate=='true'}"><font color='#0000EE'><bean:message key="writeable"/></font></c:when><c:otherwise><font color='#FF0000'><bean:message key="unwriteable"/></font></c:otherwise></c:choose><br /></td>
			</tr>
			<tr class="option">
				<td class="altbg1">./forumdata/cache</td>
				<td class="altbg2"><bean:message key="writeable"/></td>
				<td class="altbg1"><c:choose><c:when test="${checkdirarray.cache=='true'}"><font color='#0000EE'><bean:message key="writeable"/></font></c:when><c:otherwise><font color='#FF0000'><bean:message key="unwriteable"/></font></c:otherwise></c:choose><br /></td>
			</tr>
			<tr class="option">
				<td class="altbg1">./forumdata/threadcaches</td>
				<td class="altbg2"><bean:message key="writeable"/></td>
				<td class="altbg1"><c:choose><c:when test="${checkdirarray.threadcache=='true'}"><font color='#0000EE'><bean:message key="writeable"/></font></c:when><c:otherwise><font color='#FF0000'><bean:message key="unwriteable"/></font></c:otherwise></c:choose><br /></td>
			</tr>
			<tr class="option">
				<td class="altbg1">./forumdata/logs</td>
				<td class="altbg2"><bean:message key="writeable"/></td>
				<td class="altbg1"><c:choose><c:when test="${checkdirarray.log=='true'}"><font color='#0000EE'><bean:message key="writeable"/></font></c:when><c:otherwise><font color='#FF0000'><bean:message key="unwriteable"/></font></c:otherwise></c:choose><br /></td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td align="center">
		<br />
		<form method="post" action="install.jsp">
			<input type="hidden" name="action" value="config">
			<input type="button" name="submit" value=" <bean:message key="old_step"/> " style="height: 25" onclick="window.location='install.jsp'">
			&nbsp;
			<c:choose>
				<c:when test="${quit}"><input type="button" name="submit" value="<bean:message key="recheck_config"/>" style="height: 25" onclick="window.location='install.jsp?action=check'"></c:when>
				<c:otherwise><input type="submit" name="submit" value=" <bean:message key="new_step"/> " style="height: 25"></c:otherwise>
			</c:choose>
		</form>
	</td>
</tr>