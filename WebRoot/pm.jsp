<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${(jsprun_groupid!=null)?(jsprun_groupid):7}.jsp"/>
<c:set var="jsprun_action" value="101" scope="request"/>
<c:set var="accessing" value="pm" scope="request" />
<c:choose>
	<c:when test="${jsprun_uid==0}">
		<c:set var="show_message" value="not_loggedin" scope="request"/>
		<c:set var="propertyKey" value="true" scope="request" />
		<jsp:forward page="templates/default/nopermission.jsp"/>
	</c:when>
	<c:when test="${param.action==null||param.action==''}">
		<jsp:forward page="/pms.do?actions=toPmsInbox" />
	</c:when>
	<c:when test="${param.action=='view'}">
		<jsp:forward  page="/pms.do?actions=toView" />
	</c:when>
	<c:when test="${param.action=='send'}">
		<c:choose>
		<c:when test="${(param.uid==null||param.uid=='')&&param.inajax==1}">
			<jsp:forward page="/pms.do?actions=sendorajax" />
		</c:when>
		<c:when test="${param.submit=='yes'}">
			<jsp:forward page="/pms.do?actions=sendPms" />
		</c:when>
		<c:otherwise>
			<jsp:forward page="/pms.do?actions=toSendPms" />
		</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${param.action=='search'}">
	<c:set var="searchd" value="${searchid ==null ? param.searchid : searchid}"/>
		<c:choose>
			<c:when test="${searchd!=null}">
				<jsp:forward page="/pms.do?actions=searchPms&searchid=${searchd}" />
			</c:when>
			<c:when test="${param.to=='searchpms'}">
				<jsp:forward page="/pms.do?actions=searchPms" />
			</c:when>
			<c:otherwise>
				<jsp:forward page="/pms.do?actions=searchPmsallow" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${param.action=='archive'}">
		<c:if test="${param.pmid!=null}">
			<jsp:forward  page="/pms.do?actions=archive" />
		</c:if>
		<c:if test="${param.search!=null}">
			<jsp:forward  page="/pms.do?actions=archive" />
		</c:if>
		<c:if test="${param.pmid==null}">
			<jsp:forward  page="templates/default/pm.jsp?action=archive" />
		</c:if>
	</c:when>
	<c:when test="${param.action=='ignore'}">
		<jsp:forward  page="pms.do?actions=toignore" />
	</c:when>
	<c:when test="${param.action=='markunread'}">
		<jsp:forward  page="pms.do?actions=markunread" />
	</c:when>
	<c:when test="${param.action=='delete'}">
		<jsp:forward  page="/pms.do?actions=deletepms" />
	</c:when>
	<c:when test="${param.action=='announcearchive'}">
		<jsp:forward  page="/pms.do?actions=announcearchive" />
	</c:when>
	<c:when test="${param.action=='noprompt'}">
		<jsp:forward  page="/pms.do?actions=noprompt" />
	</c:when>
	<c:when test="${param.action=='saveignore'}">
		<jsp:forward  page="/pms.do?actions=saveignore" />
	</c:when>
</c:choose>

