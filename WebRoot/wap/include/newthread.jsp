<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String encodIndex = response.encodeURL("index.jsp");
%>
<jsp:include page="header.jsp"></jsp:include>
<c:if test="${valueObject.threadtypes!=null && valueObject.threadtypes.types !=null}">
<select name="typeid" onchange="ajaxget('post.jsp?action=threadtypes&typeid='+this.options[this.selectedIndex].value+'&fid=${fid}&sid=${jsprun_sid}&rand='+Math.random(), 'threadtypes', 'threadtypeswait')">
	<option value="0">&nbsp;</option>
	<c:forEach items="${valueObject.threadtypes.types}" var="threadtype">
	<option value="${threadtype.key}" ${threadtypes.special[threadtype.key]==1? "class=special":""} ${threadtype.key==0?"selected":""}>${threadtype.value}</option>
	</c:forEach>
</select>
<span id="threadtypeswait"></span>
</c:if>
<bean:message key="subject" />: <input type="text" name="subject" value="" maxlength="80" format="M*m" /><br />
<bean:message key="message" />: <input type="text" name="message" value="" format="M*m" /><br />
<anchor title="<bean:message key="submitf" />"><bean:message key="submitf" />
<go method="post" href="<%=encodIndex %>?action=post&amp;do=newthread&amp;fid=${valueObject.fid }&amp;sid=${valueObject.sid }">
<postfield name="subject" value="$(subject)" />
<postfield name="message" value="$(message)" />
<postfield name="formhash" value="${valueObject.formhash }" />
<c:if test="${valueObject.threadtypes!=null && valueObject.threadtypes.type !=null}">
<postfield name="typeid" value="$(typeid)" />
</c:if>
</go></anchor>
<br /><br />
<a href="<%=encodIndex %>?action=forum&amp;fid=${valueObject.fid }"><bean:message key="return_forum" /></a>
<jsp:include page="footer.jsp"></jsp:include>
