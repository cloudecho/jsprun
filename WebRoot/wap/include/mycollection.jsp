<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p><bean:message key="my_threads" /><br />
<c:forEach items="${valueObject.mythreadList}" var="mythread">
<a href="<%=encodIndex %>?action=thread&amp;tid=${mythread.tid }">${mythread.subject }</a><br />
</c:forEach>
<br /><bean:message key="my_replies" /><br />
<c:forEach items="${valueObject.mypostList}" var="mypost">
<a href="<%=encodIndex %>?action=thread&amp;tid=${mypost.tid }">${mypost.subject }</a><br />
</c:forEach>
<br /><bean:message key="my_fav_thread" /><br />
<c:forEach items="${valueObject.favthreadList}" var="favthread">
<a href="<%=encodIndex %>?action=thread&amp;tid=${favthread.tid }">${favthread.subject }</a><br />
</c:forEach>
<br /><bean:message key="my_fav_forum" /><br />
<c:forEach items="${valueObject.favforumList}" var="favforum">
<a href="<%=encodIndex %>?action=forum&amp;fid=${favforum.fid }">${favforum.name }</a><br />
</c:forEach>
</p>
<jsp:include page="footer.jsp"></jsp:include>