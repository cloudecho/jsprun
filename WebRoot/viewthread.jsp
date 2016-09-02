<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include page="forumdata/cache/cache_viewthread.jsp"/>
<jsp:include page="forumdata/cache/cache_medals.jsp"/>
<jsp:include page="forumdata/cache/cache_faqs.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<c:set var="jsprun_action" value="3" scope="request"/>
<c:if test="${jsprun_uid==0||settings.frameon>0}"><c:set var="referer" value="${pageContext.request.requestURI}?${pageContext.request.queryString}" scope="request"/></c:if>
<c:choose>
	<c:when test="${param.action=='printable'}"><jsp:forward page="thread.do?actions=printable"/></c:when>
	<c:when test="${param['do']=='tradeinfo'}"><jsp:forward page="thread.do?actions=tradeinfo"/></c:when>
	<c:otherwise><jsp:forward page="thread.do?actions=viewthreadpost"/></c:otherwise>
</c:choose>