<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${jsprun_groupid}.jsp"/>
<c:if test="${jsprun_adminid>0}">
	<jsp:include page="forumdata/cache/admingroup_${jsprun_adminid}.jsp"/>
</c:if>
<c:set var="accessing" value="magic" scope="request" />
<c:set var="magicaction" value="${param.action!=null?(param.action):'shop'}"
	scope="request" />
<c:choose>
	
	<c:when test="${magicaction=='market'}">
		<c:set var="jsprun_action" value="172" scope="request"/>
		<jsp:forward page="magicMarket.do?actionMethodName=showMagics" />
	</c:when>
	<c:when test="${magicaction=='marketPrepareOperation'}">
		<c:set var="jsprun_action" value="172" scope="request"/>
		<jsp:forward page="magicMarket.do?actionMethodName=marketPrepareOperation"/>
	</c:when>
	<c:when test="${magicaction=='marketOperating'}">
		<c:set var="jsprun_action" value="172" scope="request"/>
		<jsp:forward page="magicMarket.do?actionMethodName=marketOperating"/>
	</c:when>



	
	<c:when test="${magicaction=='user'}">
		<c:set var="jsprun_action" value="171" scope="request"/>
		<jsp:forward page="myMagicBoxModule.do?actionMethodName=showMagicBox" />
	</c:when>
	<c:when test="${magicaction=='prepareOperation'}">
		<c:set var="jsprun_action" value="171" scope="request"/>
		<jsp:forward page="myMagicBoxModule.do?actionMethodName=prepareOperation" />
	</c:when>
	<c:when test="${magicaction=='operating'}">
		<c:set var="jsprun_action" value="171" scope="request"/>
		<jsp:forward page="myMagicBoxModule.do?actionMethodName=operating" />
	</c:when>

	<c:when test="${magicaction=='magicLog'}">
		<c:set var="jsprun_action" value="173" scope="request"/>
		<jsp:forward page="magicLog.do?actionMethodName=${param.operation}" />
	</c:when>

	<c:when test="${magicaction=='shopping'}">
		<c:set var="jsprun_action" value="170" scope="request"/>
		<jsp:forward page="magicModule.do?actionMethodName=shopping" />
	</c:when>
	<c:when test="${magicaction=='prepareShopping'}">
		<c:set var="jsprun_action" value="170" scope="request"/>
		<jsp:forward page="magicModule.do?actionMethodName=prepareShopping" />
	</c:when>
	<c:otherwise>
		<c:set var="jsprun_action" value="170" scope="request"/>
		<jsp:forward page="magicModule.do?actionMethodName=shop" />
	</c:otherwise>

</c:choose>
