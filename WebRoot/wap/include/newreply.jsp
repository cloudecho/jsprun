<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>

<p><bean:message key="message" />:<input type="text" name="message" value="" format="M*m" /><br />
<anchor title="<bean:message key="submitf" />"><bean:message key="submitf" />
<go method="post" href="<%=encodIndex %>?action=post&amp;do=reply&amp;fid=${valueObject.fid }&amp;tid=${valueObject.tid }&amp;sid=${valueObject.sid }">
<postfield name="subject" value="$(subject)" />
<postfield name="message" value="$(message)" />
<postfield name="formhash" value="${valueObject.formhash }" />
</go></anchor><br /><br />
<a href="<%=encodIndex %>?action=thread&amp;tid=${valueObject.tid }"><bean:message key="return_thread" /></a><br />
<a href="<%=encodIndex %>?action=forum&amp;fid=${valueObject.fid }"><bean:message key="return_forum" /></a></p>
<jsp:include page="footer.jsp"></jsp:include>