<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<c:if test="${empty showpreview}">
<div class="mainbox viewthread" id="previewtable" style="display: ${previewdisplay}">
<h1><bean:message key="post_previewpost"/></h1>
<table summary="<bean:message key="post_previewpost"/>" cellspacing="0" cellpadding="0">
	<tr>
		<td class="postauthor"><c:choose><c:when test="${action == 'edit'}"><c:choose><c:when test="${post.authorid>0}"><a href="space.jsp?uid=${post.authorid}">${post.author}</a></c:when><c:otherwise><bean:message key="guest"/></c:otherwise></c:choose></c:when><c:otherwise><c:choose><c:when test="${jsprun_uid>0}">${jsprun_userss}</c:when><c:otherwise><bean:message key="guest"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td class="postcontent"><div class="postmessage" id="previewmessage"><h2>${subject}</h2>${message_preview}</div></td>
	</tr>
</table>
</div>
<br />
</c:if>