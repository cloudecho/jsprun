<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="/footer.do?action=footer"/>
<div id="footer">
	<div>
		Powered by <a href="http://www.jsprun.net" target="_blank" style="color: blue"><b>JspRun!</b></a> ${settings.version} &nbsp;&copy; 2007-2011 <a href="http://www.jsprun.com" target="_blank">JspRun! Inc.</a>
		<c:if test="${settings.debug>0}"><br /><span id="debuginfo">Processed in ${debuginfo.time} second(s)<c:if test="${settings.gzipcompress>0}">, Gzip enabled</c:if>.</span></c:if>
	</div>
</div>
</body>
</html>