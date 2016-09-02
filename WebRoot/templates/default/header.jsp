<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<jsp:include flush="true" page="/footer.do?action=header"/>
<c:if test="${!empty plugins.includes}"><c:forEach items="${plugins.includes}" var="module"><c:if test="${module.value.adminid==0||(module.value.adminid>0 && jsprun_adminid > 0 && module.value.adminid >= jsprun_adminid)}"><jrun:include value="/plugins/${module.value.script}.inc.jsp" defvalue=""/></c:if></c:forEach></c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<title>${navtitle} ${settings.bbname} ${settings.seotitle} - JAVA.NO.1<bean:message key="admincp_community" /> - Powered by JspRun!</title>
${settings.seohead}
<meta name="keywords" content="${metakeywords}${settings.seokeywords}" />
<meta name="description" content="${metadescription} ${settings.bbname} ${empty seodescription?settings.seodescription : seodescription} - JspRun!" />
<meta name="generator" content="JspRun! ${settings.version}" />
<meta name="author" content="JspRun! Team and JspRun UI Team" />
<meta name="copyright" content="2007-2011 JspRun Inc." />
<meta name="MSSmartTagsPreventParsing" content="True" />
<meta http-equiv="MSThemeCompatible" content="Yes" />
<meta http-equiv="x-ua-compatible" content="ie=7" />
<link rel="SHORTCUT ICON" href="favicon.ico" />
<link rel="archives" title="${settings.bbname}"	href="${boardurl}archiver/" />
${rsshead}${settings.extrahead}
<c:if test="${!empty requestPath}"><meta http-equiv="refresh" content="${msgforward.refreshtime} url=${requestPath}"></c:if>
<script type="text/javascript">
if(${requestScope.refresh=='true'}&&${frameon=='yes'}&&top != self){
parent.leftmenu.location.reload();
}
var IMGDIR = '${styles.IMGDIR}';var attackevasive = '${(attackevasive!=null)?(attackevasive):0}';var gid = 0;gid = parseInt('${(thisgid!=null)?(thisgid):0}');var fid = parseInt('${fid}');var tid = parseInt('${tid}');
</script>
<c:remove var="refresh" scope="request"/>
<script src="include/javascript/common.js" type="text/javascript"></script>
<script src="include/javascript/menu.js" type="text/javascript"></script>
<script src="include/javascript/ajax.js" type="text/javascript"></script>
<style type="text/css">
	@import url(forumdata/cache/style_${styles.STYLEID}.css);
	@import url(forumdata/cache/style_${styles.STYLEID}_append.css);
	body {
		width:1003px;
		margin-left:auto; 
		margin-right:auto; 
	}
	</style>
</head>
<body onkeydown="if(event.keyCode==27) return false;">		
<div id="append_parent"></div><div id="ajaxwaitid"></div>
<div class="wrap">
<div id="header"><h2><a href="${settings.indexname}" title="${settings.bbname}">${styles.BOARDLOGO}</a></h2><div id="ad_headerbanner"></div></div>
<div id="menu">
<c:if test="${settings.frameon>0}"><span class="frameswitch">
<script type="text/javascript">
if(top == self) {
if(${settings.frameon==2&&(CURSCRIPT=='index.jsp'||CURSCRIPT=='forumdisplay.jsp'||CURSCRIPT=='viewthread.jsp')&&(requestScope.frameon!='no'&&sessionScope.frameon=='yes'||requestScope.frameon==null&&sessionScope.frameon==null)}){
top.location = 'frame.jsp?frameon=yes&referer='+escape(self.location);
}
document.write('<a href="frame.jsp?frameon=yes" target="_top" class="frameon"><bean:message key="frameon_column" /><\/a>');
} else {
document.write('<a href="frame.jsp?frameon=no" target="_top" class="frameoff"><bean:message key="frameon_flat" /><\/a>');
}
</script></span></c:if>
<ul>
<c:choose><c:when test="${jsprun_uid>0}"><li><cite><a href="space.jsp?action=viewpro&uid=${jsprun_uid}">${jsprun_userss}</a></cite></li><li><a href="logging.jsp?action=logout&formhash=${formhash}" class="notabs"><bean:message key="menu_logout" /></a></li></c:when><c:otherwise><li ${accessing == "register"?"class=current":""}><a href="${settings.regname}" class="notabs">${settings.reglinkname}</a></li><li ${accessing== "logging"?"class=current":""}><a href="logging.jsp?action=login"><bean:message key="login" /></a></li></c:otherwise></c:choose>
<c:if test="${jsprun_uid>0 && usergroups.maxpmnum>0}"><li ${accessing== "pm"?"class=current":""}><a href="pm.jsp" target="_blank"><bean:message key="pm" /></a></li></c:if>
<c:if test="${settings.memliststatus>0}"><li ${accessing== "member"?"class=current":""}><a href="member.jsp?action=list"><bean:message key="members" /></a></li></c:if>
<c:if test="${usergroups.allowsearch>0}"><li ${accessing== "search"?"class=current":""}><a href="search.jsp"><bean:message key="search" /></a></li></c:if>
<c:if test="${settings.tagstatus>0}"><li ${accessing== "tag"?"class=current":""}><a href="tag.jsp"><bean:message key="a_post_tag" /></a></li></c:if>
<c:if test="${!empty plugins.links}"><c:forEach items="${plugins.links}" var="module"><c:if test="${module.value.adminid==0||(module.value.adminid>0 && jsprun_adminid > 0 && module.value.adminid >= jsprun_adminid)}"><li>${module.value.url}</li></c:if></c:forEach></c:if>
<c:if test="${jsprun_uid>0}">
<c:choose><c:when test="${settings.jsmenu_4>0}"><li id="my" onmouseover="showMenu(this.id)" ${accessing=="my"?"class=dropmenu current":"class=dropmenu"}><a href="my.jsp"><bean:message key="my" /></a></li></c:when><c:otherwise><li><a href="my.jsp?item=threads"><bean:message key="show_mytopics" /></a></li><li><a href="my.jsp?item=grouppermission"><bean:message key="my_permissions" /></a></li></c:otherwise></c:choose>
<c:choose><c:when test="${settings.jsmenu_2>0}"><li id="memcp" onmouseover="showMenu(this.id)" ${accessing=="memcp"?"class=dropmenu current":"class=dropmenu"}><a href="memcp.jsp"><bean:message key="a_setting_jsmenu_enable_memcp" /></a></li></c:when><c:otherwise><li ${accessing=="memcp"?"class=current":""}><a href="memcp.jsp"><bean:message key="a_setting_jsmenu_enable_memcp" /></a></li></c:otherwise></c:choose>
<c:if test="${settings.regstatus>1}"><li ${accessing== "invite"?"class=current":""}><a href="invite.jsp"><bean:message key="invite" /></a></li></c:if>
<c:if test="${settings.magicstatus>0}"><li ${accessing== "magic"?"class=current":""}><a href="magic.jsp"><bean:message key="magics_title" /></a></li></c:if></c:if>
<c:if test="${!empty plugins.jsmenus}"><li id="plugin" class="dropmenu" onmouseover="showMenu(this.id)"><a href="#">${settings.pluginjsmenu}</a></li></c:if>
<c:if test="${usergroups.allowviewstats>0}"><c:choose><c:when test="${settings.jsmenu_3>0}"><li id="stats" onmouseover="showMenu(this.id)" ${accessing=="stats"?"class=dropmenu current":"class=dropmenu"}><a href="stats.jsp"><bean:message key="a_setting_jsmenu_enable_stat" /></a></li></c:when><c:otherwise><li id="stats" onmouseover="showMenu(this.id)" ${accessing=="stats"?"class=current":""}><a href="stats.jsp"><bean:message key="a_setting_jsmenu_enable_stat" /></a></li></c:otherwise></c:choose></c:if>
<c:if test="${jsprun_uid>0&&(jsprun_adminid==1||jsprun_adminid==2||jsprun_adminid==3)}"><li><a href="admincp.jsp" target="_blank"><bean:message key="admincp" /></a></li></c:if>
<li ${accessing== "faq"?"class=current":""}><a href="faq.jsp"><bean:message key="faq" /></a></li>
<li id="language" onmouseover="showMenu(this.id)" class="dropmenu"><a><bean:message key="select_language" /></a></li>
</ul>
</div>