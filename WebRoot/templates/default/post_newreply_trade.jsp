<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> ${navigation} &raquo; <bean:message key="trade_add_post"/></div>
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
<form method="post" id="postform" action="post.jsp?action=reply&fid=${fid}&tid=${tid}&extra=${extra}&replysubmit=yes&formHash=${jrun:formHash(pageContext.request)}" enctype="multipart/form-data">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<input type="hidden" name="trade" value="yes" />
<input type="hidden" name="subject" value="" />
<input type="hidden" name="page" value="${param.page}">
<div class="mainbox formbox">
	<span class="headactions"><a href="member.jsp?action=credits&view=forum_reply&fid=${fid}" target="_blank"><bean:message key="credits_policy_view"/></a></span>
	<h1> <bean:message key="trade_add_post"/> </h1>
	<table summary="<bean:message key="trade_add_post"/>" cellspacing="0" cellpadding="0">
		<thead><tr><th><bean:message key="post_goodsinfo"/></th><td>&nbsp;</td></tr></thead>
		<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode"/></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" tabindex="0" /> <em class="tips"><strong><bean:message key="seccode_click"/></strong> <bean:message key="seccode_refresh"/></em><script type="text/javascript">var seccodedata = [${seccodedata['width']}, ${seccodedata['height']}, ${seccodedata['type']}];</script></td></tr></c:if>
		<c:if test="${secqaacheck}"><tr><th><label for="secanswer"><bean:message key="secqaa"/></label></th><td><div id="secquestion"></div> <input type="text" name="secanswer" id="secanswer" size="25" maxlength="50" tabindex="1" /> <script type="text/javascript">ajaxget('ajax.do?action=updatesecqaa', 'secquestion');</script></td></tr></c:if>
		<jsp:include flush="true" page="post_trade.jsp"/>
		<tr class="btns">
			<th>&nbsp;</th>
			<td>
				<input type="hidden" name="wysiwyg" id="${editorid}_mode" value="${editormode}" />
				<input type="hidden" name="fid" id="fid" value="${fid}" />
				<input type="hidden" name="special"  value="2" />
				<button type="submit" name="replysubmit" id="postsubmit" value="true" tabindex="101"><bean:message key="trade_add_post"/></button>
				<em><bean:message key="post_submit_hotkey"/></em>&nbsp;&nbsp; &nbsp;<a href="###" id="restoredata" onclick="loadData()" title="<bean:message key="post_autosave_last_restore"/>"><bean:message key="post_autosave_restore"/></a>
			</td>
		</tr>
	</table>
</div>
</form>
<jsp:include flush="true" page="post_js.jsp" />
<jsp:include flush="true" page="footer.jsp" />