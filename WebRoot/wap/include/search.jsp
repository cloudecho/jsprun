<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p><bean:message key="search_wap" /><br />
<bean:message key="a_forum_templates_keyword" />: <input type="text" name="srchtxt" value="" maxlength="15" format="M*m" /><br />
<bean:message key="username" />: <input type="text" name="srchuname" value="" format="M*m" /><br />
<anchor title="<bean:message key="submitf" />"><bean:message key="submitf" />
<go method="post" href="<%=encodIndex %>?action=search&amp;do=submit">
<postfield name="sid" value="${valueObject.sid }" />
<postfield name="srchtxt" value="$(srchtxt)" />
<postfield name="srchuname" value="$(srchuname)" />
</go></anchor></p>
<jsp:include page="footer.jsp"></jsp:include>