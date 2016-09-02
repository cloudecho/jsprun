<%@ page language="java" pageEncoding="UTF-8" contentType="text/xml"%><?xml version="1.0" encoding="UTF-8"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
<c:when test="${valueObject == null}">
<c:set var="resultInfo" value="Access Denied" scope="request"></c:set>
<jsp:forward page="/showmessage.jsp"></jsp:forward>
</c:when>
<c:when test="${valueObject.notAccess}">
<c:set var="resultInfo" value="RSS Disabled" scope="request"></c:set>
<jsp:forward page="/showmessage.jsp"></jsp:forward>
</c:when>
<c:when test="${valueObject.forumError}">
<c:set var="resultInfo" value="Specified forum not found" scope="request"></c:set>
<jsp:forward page="/showmessage.jsp"></jsp:forward>
</c:when>
<c:otherwise>
<rss version="2.0">
<channel>
<c:choose>
<c:when test="${valueObject.accessIndex}">
<title>${valueObject.bbname }</title>
<link>${valueObject.boardurl}${valueObject.indexname }</link>
<description>Latest ${valueObject.num} threads of all forums</description>
</c:when>
<c:otherwise>
<title>${valueObject.bbname } - ${valueObject.forumname }</title>
<link>${valueObject.boardurl}forumdisplay.jsp?fid=${valueObject.rssfid }</link>
<description>Latest ${valueObject.num} threads of ${valueObject.forumname }</description>
</c:otherwise>
</c:choose>
<copyright>Copyright(C) ${valueObject.bbname }</copyright>
<generator>JspRun! Board by JspRun Inc.</generator>
<lastBuildDate>${valueObject.nowTime }</lastBuildDate>
<ttl>${valueObject.ttl }</ttl>
<image>
<url>${valueObject.boardurl}images/logo.gif</url>
<title>${valueObject.bbname }</title>
<link>${valueObject.boardurl}</link>
</image>
<c:forEach items="${valueObject.threadList}" var="threadInfo">
<item>
<title>${threadInfo.subject }</title>
<link>${valueObject.boardurl}viewthread.jsp?tid=${threadInfo.tid }</link>
<description><![CDATA[${threadInfo.description } ]]></description>
<category>${threadInfo.forum }</category>
<author>${threadInfo.author }</author>
<pubDate>${threadInfo.dateline }</pubDate>
</item>
</c:forEach>
</channel>
</rss>
</c:otherwise>
</c:choose>