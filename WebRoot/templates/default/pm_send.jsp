<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<form method="post" id="postform" action="pm.jsp?action=send&submit=yes" onSubmit="return validate(this)">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="do" value="${param['do']}"/>
	<input type="hidden" name="pmssubmit" value="true" />
	<div class="mainbox formbox">
		<h1><bean:message key="pm_send"/></h1>
		<jsp:include flush="true" page="pm_navbar.jsp" />
		<table summary="<bean:message key="pm_send"/>" cellspacing="0" cellpadding="0" id="pmlist">
			<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode"/></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" /> <em class="tips"><strong><bean:message key="seccode_click"/></strong> <bean:message key="seccode_refresh"/></em><script type="text/javascript">var seccodedata = [${seccodedata['width']}, ${seccodedata['height']}, ${seccodedata['type']}];</script></td></tr></c:if>
			<c:if test="${secqaacheck}"><tr><th><label for="secanswer"><bean:message key="secqaa"/></label></th><td><div id="secquestion"></div> <input type="text" name="secanswer" id="secanswer" size="25"maxlength="50" tabindex="1" /> <script type="text/javascript">ajaxget('ajax.do?action=updatesecqaa', 'secquestion');</script></td></tr></c:if>
			<tr><th><label for="msgto"><bean:message key="to"/></label></th><td><input type="text" id="msgto" name="msgto" size="65" value="${member.username}" tabindex="2" /></td></tr>
			<c:if test="${buddylist!=null}"><tr><th id="buddy"><label><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form, 'msgtobuddys')" tabindex="3" /><bean:message key="pm_to_buddy"/></label></th><td><ul class="userlist"><c:forEach items="${buddylist}" var="user"><li><label><input class="checkbox" type="checkbox" name="msgtobuddys[]" value="${user.buddyid}" /> ${user.username}</label></li></c:forEach></ul></td></tr></c:if>
			<tr><th><label for="subject"><bean:message key="subject"/></label></th><td><input type="text" id="subject" name="subject" size="65" value="${pms.subject}" tabindex="4" maxlength="75"/></td></tr>
			<tr>
				<th valign="top"><label for="pm_textarea"><bean:message key="message"/></label> <c:if test="${settings.smileyinsert>0}"><div id="smilieslist"></div><script type="text/javascript">ajaxget('post.jsp?action=smilies', 'smilieslist');</script></c:if></th>
				<td><textarea id="pm_textarea" class="autosave" rows="15" cols="10" name="message" style="width: 95%;" onKeyDown="ctlent(event);" tabindex="5">${pms.message}</textarea><br /><label><input type="checkbox" name="saveoutbox" value="1" tabindex="6" /><bean:message key="pm_send_save_outbox"/></label></td>
			</tr>
			<tr class="btns"><th>&nbsp;</th><td><button type="submit" class="submit" name="pmssubmit" id="postsubmit" value="true" tabindex="7"><bean:message key="submitf"/></button> <em><bean:message key="post_submit_hotkey"/></em> &nbsp;<a href="###" id="restoredata" onclick="loadData()" title="<bean:message key="post_autosave_last_restore"/>"><bean:message key="post_autosave_restore"/></a></td></tr>
		</table>
	</div>
</form>
<script type="text/javascript" src="include/javascript/post.js"></script>
<script type="text/javascript">
	var wysiwyg = bbinsert = 0;
	lang['post_autosave_none'] = "<bean:message key="post_autosave_none"/>";
	lang['post_autosave_confirm'] = "<bean:message key="post_autosave_confirm"/>";
	function validate(theform) {
		if (theform.subject.value == '' || theform.message.value == '') {
			alert("<bean:message key="post_subject_and_message_isnull"/>");
			theform.subject.focus();
			return false;
		} else if (theform.subject.value.length > 75) {
			alert("<bean:message key="post_subject_toolong"/>");
			theform.subject.focus();
			return false;
		}
		theform.message.value = parseurl(theform.message.value, 'bbcode');
		theform.pmssubmit.disabled = true;
		return true;
	}
	checkFocus();
	setCaretAtEnd();
	var textobj = $('pm_textarea');
	if(!(is_ie >= 5 || is_moz >= 2)) {
		$('restoredata').style.display = 'none';
	}
</script>