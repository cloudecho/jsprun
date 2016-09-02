<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include page="forumdata/cache/cache_faqs.jsp"/>
<jsp:include page="forumdata/cache/cache_viewthread.jsp"/>
<c:set var="jsprun_action" value="151" scope="request"/>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${jsprun_groupid}.jsp"/>
<jsp:forward page="/thread.do?actions=viewblog"/>
