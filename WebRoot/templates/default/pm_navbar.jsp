<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<ul ${param.action==null||param.action=='search'?"class=tabs headertabs":"class=tabs"}>
	<li ${param.action== "send"?"class=current sendpm":"class=sendpm"}><a href="pm.jsp?action=send"><bean:message key="pm_send"/></a></li>
	<li ${param.folder== "inbox"||(param.action==null&&param.folder==null)?"class=current":""}><a href="pm.jsp?folder=inbox"><bean:message key="pm_inbox"/>[<span id="pm_unread">${num}</span>]</a></li>
	<li ${param.folder== "outbox"&&param.action==null?"class=current":""}><a href="pm.jsp?folder=outbox"><bean:message key="pm_outbox"/></a></li>
	<li ${param.folder== "track"&&param.action==null?"class=current":""}><a href="pm.jsp?folder=track"><bean:message key="a_system_invite_status_3"/></a></li>
	<li ${param.action=="search"?"class=current":""}><a href="pm.jsp?action=search"><bean:message key="pm_search"/></a></li>
	<li ${param.action== "archive"?"class=current":""}><a href="pm.jsp?action=archive"><bean:message key="pm_archive"/></a></li>
	<li ${param.action== "ignore"?"class=current":""}><a href="pm.jsp?action=ignore"><bean:message key="pm_ignore"/></a></li>
</ul>
