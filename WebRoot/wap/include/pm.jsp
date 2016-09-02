<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p>
<a href="<%=encodIndex %>?action=pm&amp;do=list&amp;unread=yes"><bean:message key="pm_unread_2" />(${valueObject.num_unread })</a><br />
<a href="<%=encodIndex %>?action=pm&amp;do=list"><bean:message key="pm_all" />(${valueObject.num_all })</a><br />
<a href="<%=encodIndex %>?action=pm&amp;do=send"><bean:message key="pm_send" /></a><br />
<a href="<%=encodIndex %>?action=pm&amp;do=delete"><bean:message key="pm_delete_all" /></a></p>
<jsp:include page="footer.jsp"></jsp:include>