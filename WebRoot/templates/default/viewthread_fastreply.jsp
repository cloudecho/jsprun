<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<script src="include/javascript/post.js" type="text/javascript"></script>
<c:if test="${allowpostreply && settings.fastreply==1}">
<script type="text/javascript">
	lang['last_page'] = '<bean:message key="last_page"/>';
	lang['next_page'] = '<bean:message key="next_page"/>';
	var postminchars = parseInt('10');
	var postmaxchars = parseInt('10000');
	var disablepostctrl = parseInt('1');
	function validate(theform) {
		if (theform.message.value == "" && theform.subject.value == "") {
			alert("<bean:message key="post_subject_and_message_isnull"/>");
			theform.message.focus();
			return false;
		} else if (theform.subject.value.length > 80) {
			alert("<bean:message key="post_subject_toolong"/>");
			theform.subject.focus();
			return false;
		}
		if (!disablepostctrl && ((postminchars != 0 && theform.message.value.length < postminchars) || (postmaxchars != 0 && theform.message.value.length > postmaxchars))) {
			alert("<bean:message key="post_message_length_invalid"/>\n\n<bean:message key="post_curlength"/>: "+theform.message.value.length+" <bean:message key="bytes"/>\n<bean:message key="board_allowed"/>: "+postminchars+" <bean:message key="to"/> "+postmaxchars+" <bean:message key="bytes"/>");
			return false;
		}
		if(!fetchCheckbox('parseurloff')) {
			theform.message.value = parseurl(theform.message.value, 'bbcode');
		}
		theform.replysubmit.disabled = true;
		return true;
	}
</script>
<form method="post" id="postform" action="post.jsp?action=reply&fid=${thread.fid}&tid=${thread.tid}&replysubmit=yes&formHash=${jrun:formHash(pageContext.request)}" onSubmit="return validate(this)" enctype="multipart/form-data">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="fastreplay" value="yes" />
	<input type="hidden" name="page" value="${pagesize}">
	<input type="hidden" name="extra" value="${param.extra}" />
	<div id="quickpost" class="box">
		<span class="headactions"><a href="member.jsp?action=credits&view=forum_reply&fid=${thread.fid}" target="_blank"><bean:message key="credits_policy_view"/></a></span>
		<h4><bean:message key="post_fastreply"/></h4>
		<div class="postoptions">
			<h5><bean:message key="options"/></h5>
			<p><label><input class="checkbox" type="checkbox" name="parseurloff" id="parseurloff" value="1"> <bean:message key="disable"/> <bean:message key="post_parseurl"/></label></p>
			<p><label><input class="checkbox" type="checkbox" name="smileyoff" id="smileyoff" value="1"> <bean:message key="disable"/><a href="faq.jsp?action=message&id=${faqs.smilies.id}" target="_blank">${faqs.smilies.keyword}</a></label></p>
			<p><label><input class="checkbox" type="checkbox" name="bbcodeoff" id="bbcodeoff" value="1"> <bean:message key="disable"/><a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank">${faqs.JspRuncode.keyword}</a></label></p>
			<c:if test="${usergroups.allowanonymous==1}"><p><label><input class="checkbox" type="checkbox" name="isanonymous" value="1"> <bean:message key="post_anonymous"/></label></p></c:if>
			<p><label><input class="checkbox" type="checkbox" name="usesig" value="1" > <bean:message key="post_show_sig"/></label></p>
			<p><label><input class="checkbox" type="checkbox" name="emailnotify" value="1"> <bean:message key="post_email_notify"/></label></p>
		</div>
		<div class="postform">
			<h5><bean:message key="subject"/>
				<c:if test="${thread.special==5}">
					<input type="hidden"  name="standhidden" value="${firststand}" >
					<select name="stand"<c:if test="${firststand==1 || firststand==2}">disabled</c:if>><option value="0" <c:if test="${firststand==0}">selected</c:if>><bean:message key="debate_neutral"/></option><option  value="1" <c:if test="${firststand==1}">selected</c:if>><bean:message key="debate_square"/></option><option  value="2" <c:if test="${firststand==2}">selected</c:if>><bean:message key="debate_opponent"/></option></select>
				</c:if>
				<input type="text" id="subject" name="subject" value="" tabindex="1">
			</h5>
			<p>
				<jsp:include flush="true" page="seditor.jsp"/>
				<div><textarea rows="7" cols="80" class="autosave" name="message" id="fastpostmessage" onkeydown="ctlent(event);savePos(this)" onkeyup="savePos(this)" onmousedown="savePos(this)" onmouseup="savePos(this)" onfocus="savePos(this)"  tabindex="2" style="width:597px;"></textarea></div>
			</p>
			<p class="btns">
				<button type="submit" name="replysubmit" id="postsubmit" value="replysubmit" tabindex="3"><bean:message key="post_topicsubmit"/> </button><bean:message key="post_submit_hotkey"/>&nbsp;
				<a href="###" id="previewpost" onclick="$('postform').action=$('postform').action + '&previewpost=yes&subject='+$('subject').value+'&message='+$('fastpostmessage').value;$('postform').submit();"><bean:message key="post_previewpost"/></a>&nbsp;
				<a href="###" id="restoredata" title="<bean:message key="post_autosave_last_restore"/>" onclick="loadData()"><bean:message key="post_autosave_restore"/></a>&nbsp;
				<a href="###" onclick="$('postform').reset()"><bean:message key="post_topicreset"/></a>
			</p>
		</div>
		<script type="text/javascript">
				var textobj = $('fastpostmessage');
				window.onbeforeunload = function () {saveData(textobj.value)};
				if(is_ie >= 5 || is_moz >= 2) {
					lang['post_autosave_none'] = "<bean:message key="post_autosave_none"/>";
					lang['post_autosave_confirm'] = "<bean:message key="post_autosave_confirm"/>";
				} else {
					$('restoredata').style.display = 'none';
				}
			</script>
	</div>
	<div id="dd" style="display:none"><input type="file" name="files"></div>
</form>
</c:if>
<script type="text/javascript">
		function modaction(action) {
			if(!action) {
				return;
			}
			if(in_array(action, ['delpost', 'banpost'])) {
				document.modactions.action = 'topicadmin.jsp?operation='+action+'&fid=${thread.fid}&moderates=${thread.tid}&page=${currentPage}&extra=${param.extra}';
				document.modactions.submit();
			} else if(in_array(action, ['delete', 'close','move','highlight','type','digest','stick','bump','recommend'])) {
				window.location=('topicadmin.jsp?operation='+ action +'&fid=${thread.fid}&moderates=${thread.tid}&sid=${sid}&extra=${param.extra}');
			} else if(action == 'repair' || action == 'removereward'){
				document.modactions.action = 'topicadmin.jsp?action='+ action +'&fid=${thread.fid}&moderates=${thread.tid}&page=${currentPage}&extra=${extra}&formHash=${jrun:formHash(pageContext.request)}';
				document.modactions.submit();
			}else{
				document.modactions.action = 'topicadmin.jsp?action='+ action +'&fid=${thread.fid}&moderates=${thread.tid}&page=${currentPage}&extra=${param.extra}';
				document.modactions.submit();
			}
		}
</script>
<c:if test="${modertar || requestScope.visitedforums!=null||(settings.forumjump==1&&settings.jsmenu_1==0)}">
<div id="footfilter" class="box">
<c:if test="${modertar}">
	<form action="#">
		<bean:message key="menu_moderation"/>:
		<select name="action" id="action" onchange="modaction(this.options[this.selectedIndex].value)">
			<option value="" selected><bean:message key="menu_moderation"/></option>
			<c:if test="${usergroups.allowdelpost>0}">
				<option value="delpost"><bean:message key="admin_delpost"/></option>
				<option value="delete"><bean:message key="admin_delthread"/></option>
			</c:if>
			<option value="banpost"><bean:message key="BNP"/></option>
			<option value="close"><bean:message key="admin_close"/></option>
			<option value="move"><bean:message key="admin_move"/></option>
			<c:if test="${thread.special==0}" ><option value="copy"><bean:message key="admin_copy"/></option></c:if>
			<option value="highlight"><bean:message key="admin_highlight"/></option>
			<option value="type"><bean:message key="menu_forum_threadtypes"/></option>
			<option value="digest"><bean:message key="admin_digest"/></option>
			<c:if test="${usergroups.allowstickthread>0}"><option value="stick"><bean:message key="admin_stick"/></option></c:if>
			<c:if test="${thread.price>0 && usergroups.allowrefund>0 && thread.special==0}"><option value="refund"><bean:message key="RFD"/></option></c:if>
			<c:if test="${thread.special==0}" >
				<option value="split"><bean:message key="admin_split"/></option>
				<option value="merge"><bean:message key="admin_merge"/></option>
			</c:if>
			<c:if test="${thread.special==3 && thread.price > 0}" ><option value="removereward"><bean:message key="RMR"/></option></c:if>
			<option value="bump"><bean:message key="admin_bump"/></option>
			<option value="repair"><bean:message key="admin_repair"/></option>
			<c:if test="${(modrecommendMap.open != null && modrecommendMap.open != '' && modrecommendMap.open!=0) && (modrecommendMap.sort != null && modrecommendMap.sort != '' && modrecommendMap.sort!=1)}" ><option value="recommend"><bean:message key="REC"/></option></c:if>
		</select>
	</form>
	</c:if>
	<c:if test="${settings.forumjump==1&&settings.jsmenu_1==0}">
		<select onchange="if(this.options[this.selectedIndex].value != '') {window.location=('forumdisplay.jsp?fid='+this.options[this.selectedIndex].value+'&sid=${sid}')}">
			<option value=""><bean:message key="forum_jump"/></option>
			${forumselect}
		</select>
	</c:if>
	<c:if test="${requestScope.visitedforums!=null}">
		<select onchange="if(this.options[this.selectedIndex].value != '')	window.location=('forumdisplay.jsp?fid='+this.options[this.selectedIndex].value+'&sid=${sid}')">
			<option value=""><bean:message key="visited_forums"/> ...</option>
			<c:forEach items="${requestScope.visitedforums}" var="visitedforum">
				<c:if test="${visitedforum.key!=thread.fid}"><option value="${visitedforum.key}">${visitedforum.value}</option></c:if>
			</c:forEach>
		</select>
	</c:if>
</div>
</c:if>
<c:if test="${settings.forumjump==1&&settings.jsmenu_1>0}"><div class="popupmenu_popup" id="forumlist_menu" style="display: none">${forummenu}</div></c:if>
<script src="include/javascript/msn.js" type="text/javascript"></script>