<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="forumdata/cache/usergroup_${jsprun_groupid}.jsp"/>
<c:if test="${jsprun_adminid>0}">
	<jsp:include page="forumdata/cache/admingroup_${jsprun_adminid}.jsp"/>
</c:if>
<c:set var="accessing" value="member" scope="request" />
<c:set var="action" value="${param.action}" />
<c:choose>
	<c:when test="${action=='online'}">
		<c:set var="jsprun_action" value="31" scope="request"/>
		<jsp:forward page="member.do?action=online" />
	</c:when>
	<c:when test="${action=='list'}">
		<c:set var="jsprun_action" value="41" scope="request"/>
		<jsp:forward page="member.do?action=list" />
	</c:when>
	<c:when test="${action=='activate'&&param.uid>0&&(!empty param.id)}">
		<jsp:forward page="member.do?action=activate" />
	</c:when>
	<c:when test="${action=='lostpasswd'&&param.lostpwsubmit==null}">
		<c:set var="jsprun_action" value="141" scope="request"/>
		<jsp:forward page="templates/default/lostpasswd.jsp" />
	</c:when>
	<c:when test="${action=='lostpasswd'&& param.lostpwsubmit!=null}">
		<c:set var="jsprun_action" value="141" scope="request"/>
		<jsp:forward page="member.do?action=lostpasswd" />
	</c:when>
	<c:when test="${action=='switchstatus'}">
		<jsp:forward page="member.do?action=switchstatus" />
	</c:when>
	<c:when test="${action=='markread'}">
		<jsp:forward page="member.do?action=markread" />
	</c:when>
	<c:when test="${action=='credits'}">
		<jsp:forward page="member.do?action=credits" />
	</c:when>
	<c:when test="${action=='regverify'}">
		<jsp:forward page="member.do?action=regverify" />
	</c:when>
	<c:when test="${action=='emailverify'}">
		<jsp:forward page="member.do?action=emailverify" />
	</c:when>
	<c:when test="${action=='getpasswd'&&param.uid!=null&&param.id!=null}">
		<c:set var="jsprun_action" value="141" scope="request"/>
		<jsp:forward page="member.do?action=toGetpasswd" />
	</c:when>
	<c:when test="${action=='clearcookies'&&param.formhash!=null&&(param.formhash==formhash||jsprun_uid==0)}">
		<jsp:forward page="member.do?action=clearcookies" />
	</c:when>
	<c:otherwise>
		<c:set var="propertyKey" value="true" scope="request" />
		<c:set var="errorInfo" value="undefined_action_return" scope="request"/>
		<jsp:forward page="templates/default/showmessage.jsp"/>
	</c:otherwise>
</c:choose>
