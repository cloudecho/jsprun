<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p><bean:message key="my" /><br /><br />
<bean:message key="a_setting_uid" />: ${valueObject.uid }<br />
<bean:message key="username" />: ${valueObject.username }<br />
<bean:message key="gender" />: ${valueObject.gender }<br />
<c:if test="${valueObject.showBirthday }">
<bean:message key="birthday" />: ${valueObject.birthday }<br />
</c:if>
<c:if test="${valueObject.showLocation}">
<bean:message key="location" />: ${valueObject.location }<br />
</c:if>
<c:if test="${valueObject.showBio}">
<bean:message key="bio" />: ${valueObject.bio }<br />
</c:if>
<c:if test="${valueObject.sameMember }">
<a href="<%=encodIndex %>?action=myphone"><bean:message key="my_phone" /></a><br />
<a href="<%=encodIndex %>?action=my&amp;do=fav"><bean:message key="my_favorites" /></a><br />
<a href="<%=encodIndex %>?action=pm"><bean:message key="pm" /></a>
</c:if>
</p>
<jsp:include page="footer.jsp"></jsp:include>