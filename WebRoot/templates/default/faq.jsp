<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <c:choose><c:when test="${action==null}"><bean:message key="faq"/></c:when><c:otherwise><a href="faq.jsp"><bean:message key="faq"/></a> ${navigation}</c:otherwise></c:choose></div>
<c:choose>
	<c:when test="${empty action}">
		<table summary="FAQ" class="portalbox" cellpadding="0" cellspacing="1">
			<tr><c:forEach items="${faqparent}" var="parent"><td><h3>${parent.title}</h3><ul style="margin: 2px auto;"><c:forEach items="${faqsub[parent.id]}" var="sub"><li><a href="faq.jsp?action=message&id=${sub.id}">${sub.title}</a></li></c:forEach></ul></td></c:forEach></tr>
		</table>
	</c:when>
	<c:when test="${action=='message'}">
		<div class="box viewthread specialthread faq">
			<table summary="" cellpadding="0" cellspacing="0">
				<tr>
					<td class="postcontent"><h1>${faq.title}</h1><div class="postmessage">${faq.message}</div></td>
					<c:if test="${otherfaqs!=null}">
						<td valign="top" style="width: 260px; border: none;">
							<div class="box" style="margin: 8px; border: none;">
								<h4><bean:message key="faq_related"/></h4>
								<ul style="padding: 5px; line-height: 2em;"><c:forEach items="${otherfaqs}" var="other"><li><a href="faq.jsp?action=message&id=${other.id}">${other.title}</a></li></c:forEach></ul>
							</div>
						</td>
					</c:if>
				</tr>
			</table>
		</div>
	</c:when>
	<c:when test="${action=='search'}">
		<c:forEach items="${faqs}" var="faq">
		<div class="box viewthread specialthread faq">
		<div class="postcontent"><h1>${faq.title}</h1></div>
		<div class="postmessage">${faq.message}</div>
		</div><br />
		</c:forEach>
	</c:when>
</c:choose>
<div class="legend">
	<form method="post" action="faq.jsp?action=search&searchsubmit=yes">
		<bean:message key="faq_search"/> <input type="text" name="keyword" size="30" value="${keyword}" />
		<select name="searchtype"><option value="all"><bean:message key="faq_search_title_and_content"/></option><option value="title"><bean:message key="faq_search_title"/></option><option value="message"><bean:message key="faq_search_content"/></option></select>
		<button type="submit" name="searchsubmit"><bean:message key="submitf"/></button>
	</form>
</div>
<jsp:include flush="true" page="footer.jsp" />