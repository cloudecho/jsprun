<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> ${navigation} &raquo; <bean:message key="a_forum_edit_perm_reply"/></div>
<script type="text/javascript">
var postminchars = parseInt('${settings.minpostsize}');
var postmaxchars = parseInt('${settings.maxpostsize}');
var disablepostctrl = parseInt('${usergroups.disablepostctrl}');
var bbinsert = parseInt('${settings.bbinsert}');
var seccodecheck = parseInt('${seccodecheck?1:0}');
var secqaacheck = parseInt('${secqaacheck?1:0}');
lang['board_allowed'] = '<bean:message key="board_allowed"/>';
lang['lento'] = '<bean:message key="lento"/>';
lang['bytes'] = '<bean:message key="bytes"/>';
lang['post_curlength'] = '<bean:message key="post_curlength"/>';
lang['post_subject_and_message_isnull'] = '<bean:message key="post_subject_and_message_isnull"/>';
lang['post_subject_toolong'] = '<bean:message key="post_subject_toolong"/>';
lang['post_message_length_invalid'] = '<bean:message key="post_message_length_invalid"/>';
</script>
<jsp:include flush="true" page="post_preview.jsp" />
<form method="post" id="postform" action="post.jsp?action=reply&amp;fid=${fid}&amp;tid=${tid}&amp;extra=${extra}&amp;replysubmit=yes&formHash=${jrun:formHash(pageContext.request)}" enctype="multipart/form-data">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="page" value="${param.page}">
	<div class="mainbox formbox">
		<h1><bean:message key="a_forum_edit_perm_reply"/></h1>
		<table summary="<bean:message key="a_forum_edit_perm_reply"/>" cellspacing="0" cellpadding="0">
			<thead><tr><th><bean:message key="username"/></th><td><c:choose><c:when test="${jsprun_uid>0}">${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</c:when><c:otherwise><bean:message key="guest"/> [<a href="logging.jsp?action=login"><bean:message key="member_login"/></a>]</c:otherwise></c:choose></td></tr></thead>
			<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode"/></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" tabindex="0" /> <em class="tips"><strong><bean:message key="seccode_click"/></strong> <bean:message key="seccode_refresh"/></em> <script type="text/javascript">var seccodedata = [${seccodedata['width']}, ${seccodedata['height']}, ${seccodedata['type']}];</script></td></tr></c:if>
			<c:if test="${secqaacheck}"><tr><th><label for="secanswer"><bean:message key="secqaa"/></label></th><td><div id="secquestion"></div> <input type="text" name="secanswer" id="secanswer" size="25" maxlength="50" tabindex="1" /><script type="text/javascript">ajaxget('ajax.do?action=updatesecqaa', 'secquestion');</script></td></tr></c:if>
			<c:if test="${special==5}"><tr><th><bean:message key="debate_position"/></th><td><c:choose><c:when test="${stand==0||stand==null}"><select name="stand" tabindex="2"><option value="0" selected><bean:message key="debate_neutral"/></option><option value="1"><bean:message key="debate_square"/></option><option value="2"><bean:message key="debate_opponent"/></option></select></c:when><c:when test="${stand==1}"><bean:message key="debate_square"/></c:when><c:when test="${stand==2}"><bean:message key="debate_opponent"/></c:when></c:choose></td></tr></c:if>
			<tr><th><label for="subject"><bean:message key="subject"/></label></th><td><input type="text" name="subject" id="subject" size="45" value="${subject}" tabindex="3" /> &nbsp;<em class="tips">(<bean:message key="optional"/>)</em></td></tr>
			<tr class="bottom"><jsp:include flush="true" page="post_editor.jsp" /></tr>
			<tr class="btns">
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="wysiwyg" id="${editorid}_mode" value="${editormode}" />
					<input type="hidden" name="fid" id="fid" value="${fid}" />
					<button type="submit" name="replysubmit" id="postsubmit" value="true" tabindex="300"><bean:message key="a_forum_edit_perm_reply"/></button>
					<em><bean:message key="post_submit_hotkey"/></em>&nbsp;&nbsp; &nbsp;<a href="###" id="restoredata" onclick="loadData()" title="<bean:message key="post_autosave_last_restore"/>"><bean:message key="post_autosave_restore"/></a>
				</td>
			</tr>
		</table>
	</div>
</form>
<div class="box">
	<h4><bean:message key="post_thread_review"/></h4>
	<div class="specialpost">
		<c:choose>
			<c:when test="${postlist==null}"><div class="specialpost"><div class="postmessage"><bean:message key="post_replies_toolong" arg0="${fid}" arg1="${tid}"/></div></div></c:when>
			<c:otherwise>
			<script src="include/javascript/viewthread.js" type="text/javascript">
				zoomstatus = parseInt(${settings.zoomstatus});
				lang['zoom_image'] = '<bean:message key="zoom_image"/>';
				lang['a_system_js_newwindow_blank'] = '<bean:message key="a_system_js_newwindow_blank"/>';
				lang['full_size'] = '<bean:message key="full_size"/>';
				lang['closed'] = '<bean:message key="closed"/>';
			</script>
				<c:forEach items="${postlist}" var="posts">
					<p class="postinfo">${posts.author} <bean:message key="poston"/> <jrun:showTime timeInt="${posts.dateline}" type="${dateformat} ${timeformat}"/></p><div class="postmessage">${posts.message}</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<jsp:include flush="true" page="post_js.jsp" />
<jsp:include flush="true" page="footer.jsp" />