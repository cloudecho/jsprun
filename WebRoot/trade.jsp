<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:choose>
	<c:when test="${jsprun_uid==0}"><c:set var="propertyKey" value="true" scope="request" /><c:set var="show_message" value="not_loggedin" scope="request"/><jsp:forward page="/templates/default/nopermission.jsp" /></c:when>
	<c:otherwise><jsp:forward page="/trade.do?action=trade"/></c:otherwise>
</c:choose>