<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p>${valueObject.bbname }<br />

<c:if test="${valueObject.isLogin}">
	<c:if test="${valueObject.allowsearch}">
<br /><a href="<%=encodIndex%>?action=search&amp;srchfrom=${valueObject.newthreads }&amp;do=submit&amp;sid=${valueObject.sid }"><bean:message key="home_newthreads" /></a><br /><a href="<%=encodIndex%>?action=search&amp;sid=${valueObject.sid }"><bean:message key="search" /></a><br />
	</c:if>
<a href="<%=encodIndex%>?action=my&amp;do=fav&amp;sid=${valueObject.sid }"><bean:message key="my_addfav" /></a><br />
<a href="<%=encodIndex%>?action=my&amp;sid=${valueObject.sid }"><bean:message key="my" /></a><br />
</c:if>
<br /><bean:message key="home_forums" /><br />
<c:forEach items="${valueObject.forumList}" var="forum">
<a href="<%=encodIndex%>?action=forum&amp;fid=${forum.fid }&amp;sid=${valueObject.sid }">${forum.name }</a><br/>
</c:forEach>
<c:if test="${valueObject.existMoreForum}">
<a href="<%=encodIndex%>?action=forum&amp;sid=${valueObject.sid }"><bean:message key="more" /></a><br /><br />
</c:if>
<bean:message key="home_tools" /><br />
<a href="<%=encodIndex%>?action=stats&amp;sid=${valueObject.sid }"><bean:message key="stats" /></a><br />
<a href="<%=encodIndex%>?action=goto&amp;sid=${valueObject.sid }"><bean:message key="goto" /></a>
<c:if test="${valueObject.allowsearch}">
<br /><br /><input type="text" name="srchtxt" value="" size="8" emptyok="true" />
<anchor title="submit"><bean:message key="search" />,
<go method="post" href="<%=encodIndex%>?action=search&amp;do=submit">
<postfield name="srchtxt" value="$(srchtxt)" />
</go>
</anchor>
</c:if>
<br /><br />
<bean:message key="home_online" />${valueObject.memberCount + valueObject.guestCount }(${valueObject.memberCount } <bean:message key="members" />)</p>
<jsp:include page="footer.jsp"></jsp:include>