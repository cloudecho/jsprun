<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"></jsp:include>
<p><bean:message key="my_phone" /><br />${valueObject.serverInfo }<br /><br />
<c:forEach items="${valueObject.otherInfoMap}" var="otherInfo">
<br />${otherInfo.key }:${otherInfo.value }
</c:forEach>
</p>
<jsp:include page="footer.jsp"></jsp:include>