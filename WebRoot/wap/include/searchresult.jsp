<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p><bean:message key="search_result" /><br />
<c:forEach items="${valueObject.threadInfoList}" var="threadInfo">
<a href="<%=encodIndex %>?action=thread&amp;tid=${threadInfo.tid }">#${threadInfo.number } ${threadInfo.subject }</a>(${threadInfo.views }/${threadInfo.replies })<br />
</c:forEach>
${valueObject.multipage }
</p>
<jsp:include page="footer.jsp"></jsp:include>