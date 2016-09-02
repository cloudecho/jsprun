<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
<head>
<link rel="stylesheet" type="text/css" id="css" href="images/admincp/admincp.css"/>
<script>
var menus = new Array('basic', 'forums', 'users', 'posts', 'extends', 'others', 'safety', 'tools', 'home');
function togglemenu(id) {
	if(parent.menu) {
		for(k in menus) {
			if(parent.menu.document.getElementById(menus[k])) {
				parent.menu.document.getElementById(menus[k]).style.display = menus[k] == id ? '' : 'none';
			}
		}
	}
}
function sethighlight(n) {
	var lis = document.getElementsByTagName('li');
	for(var i = 0; i < lis.length; i++) {
		lis[i].id = '';
	}
	lis[n].id = 'menuon';
}
</script>
<script src="include/javascript/common.js" type="text/javascript"></script>
<script src="include/javascript/menu.js" type="text/javascript"></script>
</head><body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="topmenubg"><tr>
<td rowspan="2" width="160px"><div class="logo"><a href="http://www.jsprun.net/" target="_blank"><img src="images/admincp/logo.gif" alt="JspRun!" class="logoimg" border="0"/></a> <span class="editiontext">JspRun! <span class="editionnumber">${settings.version}</span><br /><bean:message key="admincp"/></span></div></td>
<td><div class="topmenu"><ul>
<c:choose><c:when test="${members.adminid==1}">
<li><span><a href="#" onclick="sethighlight(0); togglemenu('basic'); parent.main.location='admincp.jsp?action=settings&do=basic';return false;"><bean:message key="header_basic"/></a></span></li>
<li><span><a href="#" onclick="sethighlight(1); togglemenu('forums'); parent.main.location='admincp.jsp?action=forumsedit';return false;"><bean:message key="header_forum"/></a></span></li>
<li><span><a href="#" onclick="sethighlight(2); togglemenu('users'); parent.main.location='admincp.jsp?action=members';return false;"><bean:message key="menu_member"/></a></span></li>
<li><span><a href="#" onclick="sethighlight(3); togglemenu('posts'); parent.main.location='admincp.jsp?action=modthreads';return false;"><bean:message key="header_post"/></a></span></li>
<li><span><a href="#" onclick="sethighlight(4); togglemenu('extends'); parent.main.location='admincp.jsp?action=google_config';return false;"><bean:message key="header_extend"/></a></span></li>
<li><span><a href="#" onclick="sethighlight(5); togglemenu('others'); parent.main.location='admincp.jsp?action=announcements';return false;"><bean:message key="header_other"/></a></span></li>
<li><span><a href="#" onclick="sethighlight(6); togglemenu('safety'); parent.main.location='admincp.jsp?action=safety&do=basic';return false;"><bean:message key="header_safety"/></a></span></li>
<li><span><a href="#" onclick="sethighlight(7); togglemenu('tools'); parent.main.location='admincp.jsp?action=export';return false;"><bean:message key="header_tool"/></a></span></li>
</c:when><c:otherwise>
<li><span><a href="#" onclick="parent.location='index.jsp'"><bean:message key="header_home"/></a></span></li>
<li><span><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home&sid=${jsprun_sid}';return false;"><bean:message key="header_admin"/></a></span></li>
<li><span><a href="admincp.jsp?action=logout&sid=${jsprun_sid}" target="_top"><bean:message key="menu_logout"/></a></span></li>
</c:otherwise></c:choose>
</ul></div></td>
</tr></table>
</body></html>