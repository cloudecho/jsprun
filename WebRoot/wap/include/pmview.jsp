<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<c:choose>
<c:when test="${!valueObject.existPm}">
<bean:message key="pm_nonexistence" />
</c:when>
<c:otherwise>
<p><bean:message key="subject" />:${valueObject.subject }<br />
<bean:message key="location_from" />:${valueObject.msgfrom }<br />
<bean:message key="time" />:${valueObject.dateline }<br />
<br />${valueObject.message }<br /><br />
<a href="<%=encodIndex %>?action=pm&amp;do=send&amp;pmid=${valueObject.pmid }"><bean:message key="threads_replies" /></a>
<a href="<%=encodIndex %>?action=pm&amp;do=delete&amp;pmid=${valueObject.pmid }"><bean:message key="delete" /></a><br /><br />
<a href="<%=encodIndex %>?action=pm&amp;do=list"><bean:message key="pm_all" /></a>
<br /><br /><a href="<%=encodIndex %>?action=pm"><bean:message key="pm_home" /></a></p>
</c:otherwise>
</c:choose>
<jsp:include page="footer.jsp"></jsp:include>