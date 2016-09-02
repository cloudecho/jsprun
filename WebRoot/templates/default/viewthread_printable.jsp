<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<html>
<head>
<title>${settings.bbname} - Powered by JspRun!</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="SHORTCUT ICON" href="favicon.ico" />
<style type="text/css">
body 	   {margin: 10px 80px;}
body,table {font-size: ${styles.FONTSIZE}; font-family: ${styles.FONT};}
</style>
<script type="text/javascript" src="include/javascript/common.js"></script>
<script type="text/javascript" src="include/javascript/menu.js"></script>
</head>
<body>
<img src="${styles.BOARDIMG}" alt="Board logo" border="0" /><br /><br />
<b><bean:message key="subject"/>: </b>${thread.subject} <b><a href="###" onclick="this.style.visibility='hidden';window.print();this.style.visibility='visible'">[<bean:message key="thread_print"/>]</a></b><br />
<c:forEach items="${postlist}" var="posts">
	<c:forEach items="${posts}" var="post">
		<hr noshade size="2" width="100%" color="#808080">
		<b><bean:message key="author"/>: </b>${post.key.author}&nbsp; &nbsp; <b><bean:message key="time"/>: </b><jrun:showTime timeInt="${post.key.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
		&nbsp; &nbsp; <b><bean:message key="subject"/>: </b>${post.key.subject}
		<br /><br />
		${post.key.message}
		<c:if test="${post.value!=null}">
			<c:forEach items="${post.value}" var="atta">
				<br /><br /><img src="images/attachicons/${attatype[atta.filetype]}" border="0" class="absmiddle" alt="" />	<c:choose><c:when test="${atta.isimage>0}"><bean:message key="attach_img"/></c:when><c:otherwise><bean:message key="attachment"/></c:otherwise></c:choose>:  <b>${atta.filename}</b> (<jrun:showTime timeInt="${atta.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>,<jrun:showFileSize size="${atta.filesize}"/>) / <bean:message key="attach_download_count"/> ${atta.downloads}<br />${boardurl}attachment.jsp?aid=${atta.aid}<br /><br /><c:if test="${settings.attachimgpost>0&&atta.attachment!=null&&atta.isimage>0}"><img src="attachments/${atta.attachment}" border="0" onload="if(this.width >screen.width*0.8) this.width=screen.width*0.8" alt="" /><br /><br /><br /></c:if><br>
			</c:forEach>
		</c:if>
	</c:forEach>
</c:forEach>
<br /><br /><br /><br /><hr noshade size="2" width="100%" color="${styles.BORDERCOLOR}">
<table cellspacing="0" cellpadding="0" border="0" width="95%" align="center" style="font-size: ${styles.SMFONTSIZE}; font-family: ${styles.SMFONT}">
<tr><td><bean:message key="welcometo"/> ${settings.bbname} (${boardurl})</td>
<td align="right">Powered by JspRun! ${settings.version}</td></tr></table>
</body>
</html>