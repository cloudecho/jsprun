<%@ page language="java" import="java.io.File,cn.jsprun.utils.Config,cn.jsprun.utils.JspRunConfig,cn.jsprun.utils.Common" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.apache.struts.util.MessageResources"%>
<%@page import="java.util.Locale"%>
<%
	try{
		MessageResources mr = Common.getMessageResources(request);
		Locale locale = Common.getUserLocale(request);
		request.setAttribute("version",JspRunConfig.VERSION);
		String realPath=JspRunConfig.realPath;
		String lockname = "./forumdata/install.lock";
		File lockfile=new File(realPath+lockname);
		if(lockfile!=null&&lockfile.canWrite())
		{
			request.setAttribute("resultInfo",mr.getMessage(locale,"lock_exists"));
			%><jsp:forward page="install/install_message.jsp"/><%
		}
		Config localConfig=new Config(realPath+"/config.properties");
		String dbcharset=localConfig.getValue("dbcharset");
		dbcharset=!dbcharset.equals("")?dbcharset:localConfig.getValue("charset").replaceAll("-","");
		request.setAttribute("dbcharset",dbcharset);
		request.setAttribute("prop",localConfig.getProperties());
		String action=request.getParameter("action");
		if(action!=null&&(action.equals("check")||action.equals("config")))
		{
			File file=new File(realPath+"/config.properties");
			if(file!=null&&file.canWrite())
			{
				request.setAttribute("write_error",false);
			}
			else{
				request.setAttribute("write_error",true);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<c:choose>
	<c:when test="${param.action=='changeLanguage'}"><jsp:forward page="install/install_change_language.jsp"/></c:when>
	<c:when test="${param.action=='license'}"><jsp:forward page="install/install.jsp"/></c:when>
	<c:when test="${param.action=='check'}"><jsp:forward page="install/install_check.jsp"/></c:when>
	<c:when test="${param.action=='config'}"><jsp:forward page="install/install_config.jsp"/></c:when>
	<c:when test="${param.action=='admin'}"><jsp:forward page="install/install_admin.jsp"/></c:when>
	<c:when test="${param.action=='install'}"><jsp:forward page="install/install_install.jsp"/></c:when>
	<c:when test="${param.action=='showMessage'}"><jsp:forward page="install/install_message.jsp"/></c:when>
	<c:otherwise><jsp:forward page="install/install_language.jsp"/></c:otherwise>
</c:choose>