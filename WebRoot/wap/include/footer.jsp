<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<p>${valueObject.footerVO.time }<br />
<c:if test="${valueObject.footerVO.isNotHome}">
<anchor title="confirm"><bean:message key="return" /><prev/></anchor> <a href="<%=encodIndex %>"><bean:message key="home" /></a><br />
</c:if>
<c:choose>
<c:when test="${valueObject.footerVO.isLogin}">
<a href="<%=encodIndex %>?action=login&amp;logout=yes&amp;formhash=${valueObject.footerVO.formhash }">${valueObject.footerVO.userName }:<bean:message key="menu_logout" /></a>
</c:when>
<c:otherwise>
<a href="<%=encodIndex %>?action=login"><bean:message key="login" /> </a> <a href="<%=encodIndex %>?action=register"><bean:message key="register" /></a>
</c:otherwise>
</c:choose>
<br /><br />
<small>Powered by JspRun!</small>
</p>
</card>
</wml>