<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<p><bean:message key="goto" /><br />
<input title="url" name="url" type="text" value="http://" /><br />
<anchor title="<bean:message key="submitf" />"><bean:message key="submitf" /><go href="<%=encodIndex %>?action=goto&amp;url=$(url:escape)" /></anchor></p>
<jsp:include page="footer.jsp"></jsp:include>
