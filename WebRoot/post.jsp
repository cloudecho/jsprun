<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include page="forumdata/cache/cache_faqs.jsp"/>
<jsp:include page="forumdata/cache/cache_post.jsp"/>
<jsp:include page="forumdata/cache/cache_icons.jsp"/>
<jsp:include page="forumdata/cache/cache_censor.jsp"/>
<jsp:include page="forumdata/cache/cache_viewthread.jsp"/>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid)!=null?(jsprun_groupid):7}.jsp"/>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<c:if test="${jsprun_adminid>0}"><jsp:include page="forumdata/cache/admingroup_${jsprun_adminid}.jsp"/></c:if>
<c:set var="action" value="${param.action}" scope="request"/>
<c:choose>
	<c:when test="${action=='newthread'&&(param.topicsubmit)==null}">
		<c:set var="jsprun_action" value="11" scope="request"/>
		<jsp:forward page="/post.do?actions=toNewthread" />
	</c:when>
	<c:when test="${action=='newthread'&&(param.topicsubmit)!=null}">
		<jsp:forward page="/post.do?actions=newthread" />
	</c:when>
	<c:when test="${action=='reply'}">
		<c:set var="jsprun_action" value="12" scope="request"/>
		<jsp:forward page="/post.do?actions=reply" />
	</c:when>
	<c:when test="${action=='edit' && param.editsubmit!=null}">
		<jsp:forward page="/post.do?actions=edit" />
	</c:when>
	<c:when test="${action=='edit' && param.editsubmit==null}">
		<c:set var="jsprun_action" value="13" scope="request"/>
		<jsp:forward page="/post.do?actions=toedit" />
	</c:when>
	<c:when test="${action=='import'}">
		<jsp:forward page="/post.do?actions=import" />
	</c:when>
	<c:when test="${action=='newtrade'}">
		<c:set var="jsprun_action" value="11" scope="request"/>
		<jsp:forward page="/post.do?actions=newtrade" />
	</c:when>
	<c:when test="${action=='smilies'&&settings.smileyinsert>0}">
		<jsp:forward page="/post.do?actions=showSmilies" />
	</c:when>
	<c:when test="${action=='threadtypes'}">
		<jsp:forward page="/post.do?actions=threadtypes" />
	</c:when>
	<c:otherwise>
		<c:set var="errorInfo" value="undefined_action_return" scope="request"/>
		<c:set var="propertyKey" value="true" scope="request" />
		<jsp:forward page="templates/default/showmessage.jsp"/>
	</c:otherwise>
</c:choose>