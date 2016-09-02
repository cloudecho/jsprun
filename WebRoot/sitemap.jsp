<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/cache_forums.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<jsp:forward page="/sitemap.do?action=querysitmap" />