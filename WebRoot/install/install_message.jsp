<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="install_header.jsp" />
<c:choose>
	<c:when test="${successInfo!=null}"><tr><td style="padding-top:100px; padding-bottom:100px"><table width="560" cellspacing="1" bgcolor="#000000" border="0" align="center"><tr bgcolor="#3A4273"><td width="20%" style="color: #FFFFFF; padding-left: 10px"><bean:message key="error_message"/></td></tr><tr align="center" bgcolor="#E3E3EA"><td class="message">${successInfo}<br /><br /><br /><a href="${url_forward}"><bean:message key="message_redirect"/></a><script>setTimeout("redirect('${url_forward}');", 1250);</script></td></tr></table></td></tr></c:when>
	<c:when test="${errorInfo!=null}"><tr><td style="padding-top:100px; padding-bottom:100px"><table width="560" cellspacing="1" bgcolor="#000000" border="0" align="center"><tr bgcolor="#3A4273"><td width="20%" style="color: #FFFFFF; padding-left: 10px"><bean:message key="error_message"/></td></tr><tr align="center" bgcolor="#E3E3EA"><td class="message">${errorInfo}<br /><br /><br /><a href="javascript:history.go(-1);" class="mediumtxt"><bean:message key="message_return"/></a></td></tr></table></td></tr></c:when>
	<c:otherwise><tr><td style="padding-top:100px; padding-bottom:100px"><table width="560" cellspacing="1" bgcolor="#000000" border="0" align="center"><tr bgcolor="#3A4273"><td width="20%" style="color: #FFFFFF; padding-left: 10px"><bean:message key="error_message"/></td></tr><tr align="center" bgcolor="#E3E3EA"><td class="message">${resultInfo}</td></tr></table></td></tr></c:otherwise>
</c:choose>
<jsp:include flush="true" page="install_footer.jsp" />