<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${jsprun_groupid}.jsp"/>
<c:set var="accessing" value="tag" scope="request" />
<c:set var="action" value="${param.action}" />
<jsp:forward page="/attach.do?actions=attachment" />