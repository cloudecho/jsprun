<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p>
<c:if test="${!valueObject.failedAnswer}">
<bean:message key="login_username" />:<input type="text" name="username" value="" maxlength="15" format="M*m" /><br />
<bean:message key="passwordf" />: <input type="password" name="password" value="" format="M*m" /><br />
</c:if>
<bean:message key="security_question" />:
<select name="questionid">
<option value="0"><bean:message key="security_question_0" /></option>
<option value="1"><bean:message key="security_question_1" /></option>
<option value="2"><bean:message key="security_question_2" /></option>
<option value="3"><bean:message key="security_question_3" /></option>
<option value="4"><bean:message key="security_question_4" /></option>
<option value="5"><bean:message key="security_question_5" /></option>
<option value="6"><bean:message key="security_question_6" /></option>
<option value="7"><bean:message key="security_question_7" /></option>
</select><br />
<bean:message key="security_answer" />: <input type="answer" name="answer" value="" format="M*m" /><br />
<anchor title="<bean:message key="submitf" />"><bean:message key="submitf" />
<go method="post" href="<%=encodIndex %>?action=login&amp;sid=${valueObject.sid }">
<postfield name="questionid" value="$(questionid)" />
<postfield name="answer" value="$(answer)" />
<c:choose>
<c:when test="${!valueObject.failedAnswer}">
<postfield name="username" value="$(username)" />
<postfield name="password" value="$(password)" />
</c:when>
<c:otherwise>
<postfield name="username" value="${valueObject.username}" />
<postfield name="loginauth" value="${valueObject.loginauth}" />
</c:otherwise>
</c:choose>
<c:if test="">
</c:if>
</go></anchor></p>
<jsp:include page="footer.jsp"></jsp:include>