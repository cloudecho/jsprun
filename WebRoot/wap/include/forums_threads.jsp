<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<c:choose>
<c:when test="${valueObject.showForum}">
<p><bean:message key="home_forums" /><br />
<c:forEach items="${valueObject.forumList}" var="forum">
<a href="<%=encodIndex %>?action=forum&amp;fid=${forum.fid }">${forum.name }</a><br/>
</c:forEach>
${valueObject.multipage}
</p>
</c:when>
<c:otherwise>
<p>${valueObject.forumName }<br />
<a href="<%=encodIndex %>?action=post&amp;do=newthread&amp;fid=${valueObject.forumId }"><bean:message key="post_new" /></a>
<a href="<%=encodIndex %>?action=forum&amp;do=digest&amp;fid=${valueObject.forumId }"><bean:message key="digest" /></a><br /><br />
<bean:message key="forum_list" /><a href="<%=encodIndex %>?action=forum&amp;fid=${valueObject.forumId }"><bean:message key="reload" /></a><br />
<c:forEach items="${valueObject.threadList}" var="thread">
<a href="<%=encodIndex %>?action=thread&amp;tid=${thread.tid}">#${thread.number } ${thread.subject }</a>${thread.prefix }<br />
<small>${thread.author } <bean:message key="back" />${thread.replies } <bean:message key="point" />${thread.views }</small><br />
</c:forEach>
${valueObject.multipage}
<c:if test="${valueObject.subfrums!='' }">
<br /><br /><bean:message key="forum_sublist" /><br />
${valueObject.subfrums }
</c:if>
<c:if test="${valueObject.allowsearch}">
<br /><br /><a href="<%=encodIndex %>?action=post&amp;do=newthread&amp;fid=${valueObject.forumId }"><bean:message key="post_new" /></a><br />
<a href="<%=encodIndex %>?action=my&amp;do=fav&amp;favid=${valueObject.forumId }&amp;type=forum"><bean:message key="my_addfav" /></a><br />
<input type="text" name="srchtxt" value="" size="6" format="M*m" emptyok="true"/>
<anchor title="submit"><bean:message key="search" />
<go method="post" href="<%=encodIndex %>?action=search&amp;srchfid=${valueObject.forumId }&amp;do=submit&amp;sid=${valueObject.footerVO.sid }">
<postfield name="srchtxt" value="$(srchtxt)" />
</go></anchor><br />
</c:if>
</p>
</c:otherwise>
</c:choose>
<jsp:include page="footer.jsp"></jsp:include>