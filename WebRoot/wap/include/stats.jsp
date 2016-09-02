<%@ page language="java" pageEncoding="UTF-8" contentType="text/vnd.wap.wml; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"></jsp:include>
<p>
<bean:message key="stats" /><br /><br />
<bean:message key="stats_members" />: ${valueObject.members }<br />
<bean:message key="stats_main_threads_count" />: ${valueObject.threads }<br />
<bean:message key="stats_main_posts_count" />: ${valueObject.posts }
</p>
<jsp:include page="footer.jsp"></jsp:include>