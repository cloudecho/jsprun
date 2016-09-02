<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p><bean:message key="pm_list" /><br />
<c:choose>
<c:when test="${valueObject.showList}">
<c:forEach items="${valueObject.pmInfoList}" var="pmInfo">
<a href="<%=encodIndex %>?action=pm&amp;do=view&amp;pmid=${pmInfo.pmid }">#${pmInfo.number } 
<c:if test="${pmInfo.unread}">
(<bean:message key="pm_unread" />)
</c:if>
${pmInfo.subject }
</a><br />
<small>
${pmInfo.dateline}<br />
${pmInfo.msgfrom}
</small><br />
</c:forEach>
${valueObject.wapmulti }
<br /><a href="<%=encodIndex %>?action=pm&amp;do=send"><bean:message key="pm_send" /></a>
</c:when>
<c:otherwise>
<bean:message key="pm_nonexistence" />
</c:otherwise>
</c:choose>
<br /><br /><a href="<%=encodIndex %>?action=pm"><bean:message key="pm_home" /></a></p>
<jsp:include page="footer.jsp"></jsp:include>