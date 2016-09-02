<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<c:choose>
<c:when test="${valueObject.viewThread}">
<p><bean:message key="subject" />:${valueObject.subject }<br />
<bean:message key="author" />:<a href="<%=encodIndex %>?action=my&amp;uid=${valueObject.authorid }">${valueObject.author }</a><br />
<bean:message key="time" />:${valueObject.dateline }<br /><br />
${valueObject.threadposts }
<c:if test="${valueObject.existNextPage}">
<br /><a href="<%=encodIndex %>?action=thread&amp;tid=${valueObject.tid }&amp;offset=${valueObject.offset_next }"><bean:message key="next_page" /></a> 
</c:if>
<c:if test="${valueObject.existLastPage}">
<a href="<%=encodIndex %>?action=thread&amp;tid=${valueObject.tid }&amp;offset=${valueObject.offset_last }"><bean:message key="last_page" /></a>
</c:if>
<br />
<br /><a href="<%=encodIndex %>?action=post&amp;do=reply&amp;fid=${valueObject.fid }&amp;tid=${valueObject.tid }"><bean:message key="post_reply" /></a>|<a href="<%=encodIndex %>?action=post&amp;do=newthread&amp;fid=${valueObject.fid }"><bean:message key="post_new" /></a>
<c:if test="${valueObject.existPosts}">
<br /><br /><bean:message key="thread_replylist" /> (${valueObject.replies})<br />
<c:forEach items="${valueObject.postsList}" var="post">
<a href="<%=encodIndex %>?action=thread&amp;do=reply&amp;tid=${valueObject.tid }&amp;pid=${post.pid }">#${post.number } ${post.message }</a>
<br />[${post.author } ${post.dateline }]<br />
</c:forEach>
</c:if>

</c:when>
<c:otherwise>
<p><bean:message key="thread_replylist" /><a href="<%=encodIndex %>?action=thread&amp;tid=${valueObject.tid }">${valueObject.subject }</a><br />
<bean:message key="author" />:
<c:choose>
<c:when test="${valueObject.postsInfo.anonymous}">
<bean:message key="anonymous" />
</c:when>
<c:otherwise>
<a href="<%=encodIndex %>?action=my&amp;uid=${valueObject.postsInfo.authorid}">${valueObject.postsInfo.author}</a>
</c:otherwise>
</c:choose>
<br />
<br />${valueObject.postsInfo.message}
<c:if test="${valueObject.existNextPage}">
<br /><a href="<%=encodIndex %>?action=thread&amp;do=reply&amp;tid=${valueObject.tid }&amp;pid=${valueObject.postsInfo.pid }&amp;offset=${valueObject.offset_next }"><bean:message key="next_page" /></a> 
</c:if>
<c:if test="${valueObject.existLastPage}">
<a href="<%=encodIndex %>?action=thread&amp;do=reply&amp;tid=${valueObject.tid }&amp;pid=${valueObject.postsInfo.pid }&amp;offset=${valueObject.offset_last }"><bean:message key="last_page" /></a>
</c:if>
</c:otherwise>
</c:choose>
<br />
${valueObject.wapmulti }
<c:if test="${valueObject.allowreply}">
<br />
<input type="text" name="message" value="" size="6" emptyok="true"/>
<anchor title="<bean:message key="submitf" />"><bean:message key="thread_quickreply" />
<go method="post" href="<%=encodIndex %>?action=post&amp;do=reply&amp;fid=${valueObject.fid }&amp;tid=${valueObject.tid }&amp;sid=${valueObject.sid }">
<postfield name="message" value="$(message)"/>
<postfield name="formhash" value="${valueObject.formhash }" />
</go></anchor><br />
<a href="<%=encodIndex %>?action=my&amp;do=fav&amp;favid=${valueObject.tid }&amp;type=thread"><bean:message key="my_addfav" /></a><br />
</c:if>
<br />&lt;&lt;<a href="<%=encodIndex %>?action=goto&amp;do=next&amp;tid=${valueObject.tid }&amp;fid=${valueObject.fid }"><bean:message key="next_thread" /></a>
<br />&gt;&gt;<a href="<%=encodIndex %>?action=goto&amp;do=last&amp;tid=${valueObject.tid }&amp;fid=${valueObject.fid }"><bean:message key="last_thread" /></a><br />
<a href="<%=encodIndex %>?action=forum&amp;fid=${valueObject.fid }"><bean:message key="return_forum" /></a></p>
<jsp:include page="footer.jsp"></jsp:include>