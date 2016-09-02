<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@page import="cn.jsprun.foreg.vo.wap.MessageVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
MessageVO messageVO = (MessageVO)request.getAttribute("valueObject");
String forwardLink = response.encodeURL(messageVO.getForwardLink());
%>
<jsp:include page="header.jsp"></jsp:include>
<p>
${valueObject.message }
<c:if test="${valueObject.forward}">
<br /><a href="<%=forwardLink %>">${valueObject.forwardTitle}</a>
</c:if>
</p>
<jsp:include page="footer.jsp"></jsp:include>