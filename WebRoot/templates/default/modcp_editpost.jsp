<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<c:choose>
	<c:when test="${param.action=='editsubject'}">
		<input type="text" id="subject_${tid}" name="subject_${tid}" value="${subject}" size="60" maxlength="80" ondblclick="doane(event)" />
		<script type="text/javascript" reload="1">
			$('subject_${tid}').focus();
			$('subject_${tid}').onblur = function() {
				ajaxget('modcp.jsp?action=editsubject&tid=${tid}&fid=${fid}&editsubjectsubmit=yes&formHash=${jrun:formHash(pageContext.request)}&subjectnew=' + encodeURI(encodeURI($('subject_${tid}').value)), 'thread_${tid}', 'thread_${tid}', '<bean:message key="ajax_post"/>');
			}
			$('subject_${tid}').onkeydown = function(e) {
				e = e ? e : window.event;
				actualCode = e.keyCode ? e.keyCode : e.charCode;
				if(actualCode == 13) {
					ajaxget('modcp.jsp?action=editsubject&tid=${tid}&fid=${fid}&editsubjectsubmit=yes&formHash=${jrun:formHash(pageContext.request)}&subjectnew=' + encodeURI(encodeURI($('subject_${tid}').value)), 'thread_${tid}', 'thread_${tid}', '<bean:message key="ajax_post"/>');
					doane(e);
				} else if(actualCode == 27) {
					ajaxget('modcp.jsp?action=editsubject&tid=${tid}&fid=${fid}&editsubjectsubmit=yes&formHash=${jrun:formHash(pageContext.request)}&subjectnew=' +  encodeURI(encodeURI($('subject_${tid}').value)), 'thread_${tid}', 'thread_${tid}', '<bean:message key="ajax_post"/>');
				}
			}
		</script>
	</c:when>
	<c:otherwise>
		<textarea type="text" id="message_${post.pid}" name="message_${post.pid}" style="width: 80%; height: 200px; overflow: visible" ondblclick="doane(event)">${post.message}</textarea>
		<p style="margin: 5px; text-align: center;">
			<button type="button" value="true" class="submit" onclick="submitmessage('${post.pid}');"><bean:message key="submitf"/></button>
			&nbsp;&nbsp;
			<button type="button" class="submit" onclick="ajaxget('modcp.jsp?action=editmessage&pid=${post.pid}&tid=${post.tid}&editmessagesubmit=yes&inajax=1&do=notupdate&formHash=${jrun:formHash(pageContext.request)}&rand='+Math.random(), 'postmessage_${post.pid}')"><bean:message key="cancelf"/></button>
		</p>
	<script type="text/javascript">
		document.getElementById('message_${post.pid}').focus();
		function submitmessage(pid) {
		if(!$('messageform_'+pid)) {
			var messageform = document.createElement("form");
			messageform.id = 'messageform_'+pid;
			messageform.method = 'post';
			messageform.action = 'modcp.jsp?action=editmessage&pid='+pid+'&tid=${post.tid}&editmessagesubmit=yes&inajax=1&formHash=${jrun:formHash(pageContext.request)}';
			var messageforminput = document.createElement('input');
			messageforminput.id= 'messageforminput_'+pid;
			messageforminput.type= 'hidden';
			messageforminput.name = 'message';
			messageforminput.value = $('message_'+pid).value;
			messageform.appendChild(messageforminput);
			document.body.appendChild(messageform);
		} else {
			$('messageforminput_'+pid).value = $('message_'+pid).value;
		}
		ajaxpost('messageform_'+pid, 'postmessage_'+pid);
	}
	</script>
	</c:otherwise>
</c:choose>