<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p><bean:message key="pm_to" />:<input type="text" name="msgto" value="${valueObject.msgfrom }" maxlength="15" format="M*m" /><br />
<bean:message key="subject" />:<input type="text" name="subject" value="${valueObject.subject }" maxlength="70" format="M*m" /><br />
<bean:message key="message" />:<input type="text" name="message" value="" format="M*m" /><br />
<anchor title="<bean:message key="submitf" />"><bean:message key="submitf" />
<go method="post" href="<%=encodIndex %>?action=pm&amp;do=send&amp;sid=${valueObject.sid }">
<postfield name="msgto" value="$(msgto)" />
<postfield name="subject" value="$(subject)" />
<postfield name="message" value="$(message)" />
<postfield name="formhash" value="${valueObject.formhash }" />
</go></anchor>
<br /><br /><a href="<%=encodIndex %>?action=pm"><bean:message key="pm_home" /></a></p>
<jsp:include page="footer.jsp"></jsp:include>