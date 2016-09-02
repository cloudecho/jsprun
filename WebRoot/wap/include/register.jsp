<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p><bean:message key="username" />:<input type="text" name="username" value="" maxlength="15" /><br />
<bean:message key="passwordf" />: <input type="password" name="password" value="" /><br />
<bean:message key="email_c" />: <input type="text" name="email" value="" /><br />
<anchor title="<bean:message key="submitf" />"><bean:message key="submitf" />
<go method="post" href="<%=encodIndex %>?action=register&amp;">
<postfield name="username" value="$(username)" />
<postfield name="password" value="$(password)" />
<postfield name="email" value="$(email)" />
</go></anchor></p>
<jsp:include page="footer.jsp"></jsp:include>