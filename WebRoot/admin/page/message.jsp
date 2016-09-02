<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="cp_header.jsp" />
<br /><br /><br /><br /><br /><br />
<table width="500" border="0" cellpadding="0" cellspacing="0" align="center" class="tableborder">
<tr class="header"><td><bean:message key="jsprun_message"/></td></tr>
<tr><td class="altbg2"><div align="center">
<c:choose>
	<c:when test="${msgtype=='form'}">
		<form method="post" action="${url_forward}">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="hasconfirmed" value="yes" />
			<br /><br /><br />
			<c:choose><c:when test="${empty message_key}">${message}</c:when><c:otherwise><bean:message key="${message_key}"/></c:otherwise></c:choose>
			<c:if test="${isnewline=='yes'}"><br /><br /></c:if>${othermsg}<br /><br /><br /><br />
			<input class="button" type="submit" name="confirmed" value="<bean:message key='ok'/>"> &nbsp; 
			<input class="button" type="button" value="<bean:message key='cancel'/>" onClick="changehref();">
		</form><br />
		<script type="text/javascript">
			function changehref() {
				if(${empty cancelurl}){
					history.go(-1);
				}else{
					location.href='${cancelurl}';
				}
			}
		</script>
	</c:when>
	<c:otherwise>
		<br /><br /><br />
		<c:choose><c:when test="${empty message_key}">${message}</c:when><c:otherwise><bean:message key="${message_key}"/></c:otherwise></c:choose>
		<c:choose>
			<c:when test="${!empty url_forward}"><br /><br /><br /><a href="${url_forward}"><bean:message key="message_redirect"/></a><script>setTimeout("redirect('${url_forward}');", 2000);</script></c:when>
			<c:when test="${request['retrun']}"><br /><br /><br /><a href="javascript:history.go(-1);" class="mediumtxt"><bean:message key="message_return"/></a></c:when>
		</c:choose>
		${othermsg}<br /><br />
	</c:otherwise>
</c:choose>
</div><br /><br />
</td></tr></table>
<br /><br /><br />
<jsp:directive.include file="cp_footer.jsp" />