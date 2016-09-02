<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="menu_other_announce" /></div>
<script src="include/javascript/viewthread.js" type="text/javascript"></script>
<script type="text/javascript">
zoomstatus = parseInt(${settings.zoomstatus});
function toggle_collapse(objimg,objname) {
	var img= $(objimg);
	var obj = $(objname);		
	if(obj) {
		if(obj.style.display == ''){
			img.src="${styles.IMGDIR}/arrow_right_big.gif";
			img.alt="<bean:message key="outspread" />";
			obj.style.display='none';
		}else{
			img.src="${styles.IMGDIR}/arrow_down_big.gif";
			img.alt="<bean:message key="draw_in" />";
			obj.style.display='';
		}
	}
}
</script>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<c:forEach items="${announcements}" var="announcement"><div class="box">
	<div class="specialpost" id="${announcement.id}">
		<div class="postinfo"><span style="float: left;"><a href="#${announcement.id}" onclick="toggle_collapse('img${announcement.id}','announce${announcement.id}');"><img id="img${announcement.id}" src="${styles.IMGDIR}/arrow_right_big.gif" alt="<bean:message key="outspread" />">&nbsp;${announcement.subject}</a></span> <bean:message key="author" />:<a href="space.jsp?username=${announcement.authorenc}">${announcement.author}</a> &nbsp; <bean:message key="start_time" />: ${announcement.starttime}&nbsp; <bean:message key="endtime" />: ${announcement.endtime}</div>
		<div class="postmessage" id="announce${announcement.id}" style="display: none">${announcement.message}</div>
	</div>
</div></c:forEach>				
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<c:if test="${!empty annid}"><script type="text/javascript">toggle_collapse('img${annid}','announce${annid}');</script></c:if>
<jsp:include flush="true" page="footer.jsp" />