<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> ${navigation} &raquo; <bean:message key="post_newthread_trade"/></div>
<script type="text/javascript">
lang['calendar_Sun'] = '<bean:message key="calendar_Sun"/>';
lang['calendar_Mon'] = '<bean:message key="calendar_Mon"/>';
lang['calendar_Tue'] = '<bean:message key="calendar_Tue"/>';
lang['calendar_Wed'] = '<bean:message key="calendar_Wed"/>';
lang['calendar_Thu'] = '<bean:message key="calendar_Thu"/>';
lang['calendar_Fri'] = '<bean:message key="calendar_Fri"/>';
lang['calendar_Sat'] = '<bean:message key="calendar_Sat"/>';
lang['old_month'] = '<bean:message key="old_month"/>';
lang['select_year'] = '<bean:message key="select_year"/>';
lang['select_month'] = '<bean:message key="select_month"/>';
lang['next_month'] = '<bean:message key="next_month"/>';
lang['calendar_hr'] = '<bean:message key="calendar_hr"/>';
lang['calendar_min'] = '<bean:message key="calendar_min"/>';
lang['calendar_month'] = '<bean:message key="calendar_month"/>';
lang['calendar_today'] = '<bean:message key="calendar_today"/>';
</script>
<script type="text/javascript" src="include/javascript/calendar.js"></script>
<script type="text/javascript">
var postminchars = parseInt('${settings.minpostsize}');
var postmaxchars = parseInt('${settings.maxpostsize}');
var disablepostctrl = parseInt('${usergroups.disablepostctrl}');
var typerequired = parseInt('${threadtypes.required}');
var bbinsert = parseInt('${settings.bbinsert}');
var seccodecheck = parseInt('${seccodecheck}');
var secqaacheck = parseInt('${secqaacheck}');
var special = 2;
var tradepost = 1;
var isfirstpost = 1;
var allowposttrade = parseInt('${allowposttrade?1:0}');
var allowpostreward = parseInt('${allowpostreward?1:0}');
var allowpostactivity = parseInt('${allowpostactivity?1:0}');
lang['board_allowed'] = '<bean:message key="board_allowed"/>';
lang['lento'] = '<bean:message key="lento"/>';
lang['bytes'] = '<bean:message key="bytes"/>';
lang['post_curlength'] = '<bean:message key="post_curlength"/>';
lang['post_subject_and_message_isnull'] = '<bean:message key="post_subject_and_message_isnull"/>';
lang['post_subject_toolong'] = '<bean:message key="post_subject_toolong"/>';
lang['post_message_length_invalid'] = '<bean:message key="post_message_length_invalid"/>';
lang['post_type_isnull'] = '<bean:message key="post_type_isnull"/>';
lang['post_trade_alipay_null'] = '<bean:message key="post_trade_alipay_null"/>';
lang['post_trade_goodsname_null'] = '<bean:message key="post_trade_goodsname_null"/>';
lang['post_trade_price_null'] = '<bean:message key="post_trade_price_null"/>';
lang['post_trade_addr_null'] = '<bean:message key="post_trade_addr_null"/>';
</script>
<jsp:include flush="true" page="post_preview.jsp" />
<form method="post" id="postform" action="post.jsp?action=newtrade&fid=${fid}&page=${page}&topicsubmit=yes&formHash=${jrun:formHash(pageContext.request)}" enctype="multipart/form-data">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<input type="hidden" name="isblog" value="${isblog}" />
<input type="hidden" name="frombbs" value="1" />
<input type="hidden" name="special" value="2" />
<div class="mainbox formbox">
	<span class="headactions"><a href="member.jsp?action=credits&view=forum_post&fid=${fid}" target="_blank"><bean:message key="credits_policy_view"/></a></span>
	<h1><bean:message key="post_newthread_trade"/></h1>
	<table summary="post" cellspacing="0" cellpadding="0">
		<thead><tr><th><bean:message key="username"/></th><td><c:choose><c:when test="${jsprun_uid>0}">${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</c:when><c:otherwise><bean:message key="guest"/> [<a href="logging.jsp?action=login"><bean:message key="member_login"/></a>]</c:otherwise></c:choose></td></tr></thead>
		<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode"/></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" tabindex="0" /> <em class="tips"><strong><bean:message key="seccode_click"/></strong> <bean:message key="seccode_refresh"/></em><script type="text/javascript">var seccodedata = [${seccodedata['width']}, ${seccodedata['height']}, ${seccodedata['type']}];</script></td></tr></c:if>
		<c:if test="${secqaacheck}"><tr><th><label for="secanswer"><bean:message key="secqaa"/></label></th><td><div id="secquestion"></div> <input type="text" name="secanswer" id="secanswer" size="25" maxlength="50" tabindex="1" /><script type="text/javascript">ajaxget('ajax.do?action=updatesecqaa', 'secquestion');</script></td></tr></c:if>
		<tr>
			<th><label for="subject"><bean:message key="subject"/></label></th>
			<td>${typeselect} <input type="text" name="subject" id="subject" size="45" value="${subject}" tabindex="3" /></td>
		</tr>
		<thead><tr><th><bean:message key="post_trade_counterinfo"/></th><td>&nbsp;</td></tr></thead>
		<tr>
			<th><label for="threaddesc"><bean:message key="post_trade_counterdesc"/></label></th>
			<td><textarea name="counterdesc" id="counterdesc" rows="10" cols="20" style="width:99%; height:60px" tabindex="4"></textarea></td>
		</tr>
		<tr>
			<th><label for="aboutthread"><bean:message key="post_trade_aboutcounter"/></label></th>
			<td><textarea name="aboutcounter" id="aboutcounter" rows="10" cols="20" style="width:99%; height:60px" tabindex="5"></textarea></td>
		</tr>
		<thead><tr><th><bean:message key="post_goodsinfo"/></th><td>&nbsp;</td></tr></thead>
		<jsp:include flush="true" page="post_trade.jsp" />
		<thead><tr><th>&nbsp;</th><td><label><input id="advshow" class="checkbox" type="checkbox" onclick="showadv()" tabindex="201" /><bean:message key="magics_information"/></label></td></tr></thead>
		<tbody id="adv" style="display: none">
		<c:if test="${usergroups.allowsetreadperm>0}">
			<tr>
				<th><label for="readperm"><bean:message key="readperm_thread"/></label></th>
				<td><input type="text" name="readperm" id="${readperm}" size="6" value="0" tabindex="202" /> <em class="tips">(<bean:message key="post_zero_is_nopermission"/>)</em></td>
			</tr>
		</c:if>
		</tbody>
		<tr class="btns">
			<th>&nbsp;</th>
			<td>
				<input type="hidden" name="wysiwyg" id="${editorid}_mode" value="${editormode}" />
				<button type="submit" name="topicsubmit" id="postsubmit" value="true" tabindex="300"><bean:message key="post_newthread_trade"/></button>
				<em><bean:message key="post_submit_hotkey"/></em>&nbsp;&nbsp; &nbsp;<a href="###" id="restoredata" onclick="loadData()" title="<bean:message key="post_autosave_last_restore"/>"><bean:message key="post_autosave_restore"/></a>
			</td>
		</tr>
	</table>
</div><br />
</form>
<script type="text/javascript">
function showadv() {
	if($("advshow").checked == true) {
		$("adv").style.display = "";
	} else {
		$("adv").style.display = "none";
	}
}
</script>
<jsp:include flush="true" page="post_js.jsp" />
<jsp:include flush="true" page="footer.jsp" />