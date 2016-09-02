<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${valueObject.header.boardurl }" />
<title>${valueObject.header.navtitle} ${valueObject.header.bbname} ${valueObject.header.seotitle} - Powered by JspRun! Archiver</title>
${valueObject.header.seohead}
<meta name="keywords" content="JspRun!,JspRun,forums,bulletin board,${valueObject.header.seokeywords }" />
<meta name="description" content="${valueObject.header.meta_contentadd } ${valueObject.header.bbname } ${valueObject.header.seodescription }- JspRun! Archiver" />
<meta name="generator" content="JspRun! Archiver ${valueObject.header.version }" />
<meta name="author" content="JspRun! Team & JspRun UI Team" />
<meta name="copyright" content="2007-2011 JspRun Inc." />
<link rel="stylesheet" type="text/css" href="forumdata/cache/style_${valueObject.header.styleid }.css" />
<link rel="SHORTCUT ICON" href="../favicon.ico" />
<style type="text/css">


</style>
</head>
<body class="archiver">
<c:if test="${valueObject.header.haveHeaderbanner}"><div class="archiver_banner">${valueObject.header.headerbanner}</div></c:if>
<div class="wrap">