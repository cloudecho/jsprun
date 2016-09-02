<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<br />
${message}
<c:if test="${dis!='ok'}">
<div class="postactions" style="margin-top: 12px;">
	<c:choose>
		<c:when test="${param.folder!='announce'}">
		<c:if test="${param.folder=='inbox' && pmsd.msgfromid>0}">
			<a href="pm.jsp?action=send&pmid=${pmsd.pmid}&do=reply" target="_blank"><bean:message key="threads_replies"/></a> -
		</c:if>
	 <a href="pm.jsp?action=send&pmid=${pmsd.pmid}&do=forward" target="_blank"><bean:message key="forward"/></a>
	 <c:if test="${param.folder=='inbox'}">
	  - <a href="pm.jsp?action=markunread&pmid=${pmsd.pmid}" id="ajax_markunread_${pmsd.pmid}" onclick="ajaxmenu(event, this.id, 1000, 'markunread', 0)"><bean:message key="pm_mark_unread"/></a>
	</c:if>
	 - <a href="pm.jsp?action=archive&pmid=${pmsd.pmid}"><bean:message key="download"/></a>
	- <a href="pm.jsp?action=delete&pmid=${pmsd.pmid}&folder=${param.folder}&formHash=${jrun:formHash(pageContext.request)}&deletesubmit=yes" id="ajax_delete_${pmsd.pmid}" onclick="ajaxmenu(event, this.id, 1000, 'deletepm', 0)"><bean:message key="delete"/></a> -
	<a href="###" onclick="closepm(${pmsd.pmid})"><bean:message key="closed"/></a>
		</c:when>
		<c:otherwise>
		<a href="pm.jsp?action=announcearchive&pmid=${announ.id}"><bean:message key="download"/></a>-
		<a href="###" onclick="closepm(${announ.id})"><bean:message key="closed"/></a>
		</c:otherwise>
	</c:choose>
</div>
</c:if>
<script type="text/javascript">
function deletepm(obj) {
	var pmid = obj.substr(obj.lastIndexOf('_') + 1);
	$('pmrow_' + pmid).style.display = 'none';
	$('pm_view_' + pmid + '_div').style.display = 'none';
}

function markunread(obj) {
	var pmid = obj.substr(obj.lastIndexOf('_') + 1);
	$('pm_view_' + pmid).parentNode.style.fontWeight = 800;//'<b>' + $('pm_view' + pmid).innerHTML + '</b>';
	$('pm_view_' + pmid + '_div').style.display = 'none';
	prepmdiv = '';
	window.location.reload();
}

function changestatus(obj) {
	if(obj.parentNode.style.fontWeight == 800) {
		obj.parentNode.style.fontWeight = 200;
	}
}

function closepm(pmid) {
	changedisplay($('pm_view_' + pmid + '_div'), 'none');
	prepmdiv = '';
}
</script>
