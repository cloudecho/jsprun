<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<html>
<head>
<title>${navtitle} ${settings.bbname} ${settings.seotitle} - JAVA.NO.1<bean:message key="admincp_community" /> - Powered by JspRun!</title>
<meta name="keywords" content="${metadescription}JspRun!,JspRun,forums,bulletin${settings.seokeywords}" />
<meta name="description" content="${settings.bbname} ${settings.seodescription} - JspRun!" />
<meta name="generator" content="JspRun! ${settings.version}" />
<meta name="MSSmartTagsPreventParsing" content="TRUE" />
<meta http-equiv="MSThemeCompatible" content="Yes" />
<link rel="SHORTCUT ICON" href="favicon.ico" />
<link rel="archives" title="${settings.bbname}" href="${boardurl}archiver/" />
${settings.extrahead}
<c:choose><c:when test="${settings.allowcsscache>0}"><link rel="stylesheet" type="text/css" id="css" href="forumdata/cache/style_${styles.STYLEID}.css" /></c:when><c:otherwise><style type="text/css"><jsp:include page="css.jsp"/></style></c:otherwise></c:choose>
<style type="text/css">
body {
	margin: 0px;
	padding: 2px;
	background: ${styles.FRAMEBGCOLOR};
	background-image: url(${styles.IMGDIR}/frame_bg.gif);
	background-attachment: fixed;
	background-position: right;
	background-repeat: repeat-y;
}
.out {
	padding: 0.2em;
	width: 96%;
	height: 100%;
	overflow: auto;
	text-align: left;
	overflow-x: hidden;
}

.tree {
	font: ${styles.FONTSIZE} ${styles.FONT};
	color: ${styles.TEXT};
	white-space: nowrap;
	padding-left: 0.4em;
}
.tree img {
	border: 0px;
	vertical-align: middle;
}
.tree a.node, .tree a.checked {
	white-space: nowrap;
	padding: 1px 2px 1px 2px;
}
.tree a.node:hover, .tree a.checked:hover {
	text-decoration: underline;
}
.tree a.checked {
	background: ${styles.FRAMEBGCOLOR};
	color: ${styles.TABLETEXT};
}
.tree .clip {
	overflow: hidden;
}
h1 { padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-weight: bold; line-height: 1.4em; }
h2 { padding-left: 10px; border-bottom: 1px solid #DDD; font-weight: normal; margin-bottom: 2em;}
h3 { border: 1px solid ${styles.CATBORDER}; padding: 3px 5px; background: ${styles.CATCOLOR} no-repeat 3px 50%; margin-top: 2em; padding-top: 0.4em; font-weight: normal;}
strong { font-weight: bold; }
</style>
<script type="text/javascript">var IMGDIR = '${styles.IMGDIR}';var attackevasive = '${attackevasive==null ? 0 : attackevasive}';</script>
<script type="text/javascript" src="include/javascript/common.js"></script>
<script type="text/javascript" src="include/javascript/tree.js"></script>
<script type="text/javascript" src="include/javascript/ajax.js"></script>
<script type="text/javascript" src="include/javascript/iframe.js"></script>
</head>
<body>
<div class="out">
<h1>${settings.bbname}</h1>
<h2><c:choose><c:when test="${jsprun_uid>0}"><span style="font-weight: bold;"><a href="space.jsp?uid=${jsprun_uid}" target="_blank">${jsprun_userss}</a>:&nbsp;</span> <a href="###" onclick="parent.main.location = 'logging.jsp?action=logout&amp;formhash=${formhash}&referer=' + escape(parent.main.location)">[<bean:message key="menu_logout" />]</a></c:when><c:otherwise><strong><bean:message key="guest" />:&nbsp;</strong><a href="${settings.regname}" target="main" onclick="this.href += '?referer='+ escape(parent.main.location)">[${settings.reglinkname}]</a> <a href="logging.jsp?action=login" target="main" onclick="this.href += '&referer='+ escape(parent.main.location)">[<bean:message key="login" />]</a></c:otherwise></c:choose></h2>
<script type="text/javascript">
var tree = new dzTree('tree');
tree.addNode(0, -1, '<bean:message key="header_home" />', '${settings.indexname}', 'main', true);
tree.addNode(99999998, 0, '<bean:message key="digest_area" />', 'digest.jsp', 'main', true);
tree.addNode(99999999, 0, '<bean:message key="home_newthreads" />', 'search.jsp?srchfrom=87000&searchsubmit=yes&formHash=${jrun:formHash(pageContext.request)}', 'main', true);
<c:forEach items="${forumlist}" var="forumdata">tree.addNode('${forumdata.fid}', '${forumdata.fup}', '${forumdata.name}', "${forumdata.type=='group'&&haschild[forumdata.fid]?'index.jsp?gid':'forumdisplay.jsp?fid'}=${forumdata.fid}", 'main', false);</c:forEach>
tree.show();
</script>
<h3><bean:message key="whosonline" />: <a href="member.jsp?action=online" target="main"><strong id="onlinenum"></strong>&nbsp;<bean:message key="index_users" /></a></h3>
</div>
<script type="text/javascript">
ajaxget('misc.jsp?action=getonlines&inajax=1', 'onlinenum');
window.setInterval("ajaxget('misc.jsp?action=getonlines&inajax=1', 'onlinenum', '')", 180000);
</script>
</body>
</html>