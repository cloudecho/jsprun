<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> ${navigation}&raquo; <c:choose><c:when test="${special == 1}"><bean:message key="post_newthread_poll"/></c:when><c:when test="${special == 3}"><bean:message key="post_newthread_reward"/></c:when><c:when test="${special == 5}"><bean:message key="post_newthread_debate"/></c:when><c:otherwise><bean:message key="post_new"/></c:otherwise></c:choose></div>
<c:if test="${special == 4||special == 5}">
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
</c:if>
<script type="text/javascript">
var postminchars = parseInt('${settings.minpostsize}');
var postmaxchars = parseInt('${settings.maxpostsize}');
var disablepostctrl = parseInt('${usergroups.disablepostctrl}');
var typerequired = parseInt('${threadtypes.required}');
var bbinsert = parseInt('${settings.bbinsert}');
var seccodecheck = parseInt('${seccodecheck?1:0}');
var secqaacheck = parseInt('${secqaacheck?1:0}');
var special = parseInt('${special}');
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
lang['post_reward_credits_null'] = '<bean:message key="post_reward_credits_null"/>';
</script>
<jsp:include flush="true" page="post_preview.jsp" />
<form method="post" id="postform" action="post.jsp?action=newthread&fid=${fid}&page=${page}&topicsubmit=yes&formHash=${jrun:formHash(pageContext.request)}" enctype="multipart/form-data">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<input type="hidden" name="isblog" value="${isblog}" />
<input type="hidden" name="frombbs" value="1" />
<c:if test="${special>0}"><input type="hidden" name="special" value="${special}" /></c:if>
<div class="mainbox formbox">
	<span class="headactions"><a class="notabs" href="member.jsp?action=credits&view=forum_post&fid=${fid}" target="_blank"><bean:message key="credits_policy_view"/></a> </span>
	<h1><c:choose><c:when test="${special == 1}"><bean:message key="post_newthread_poll"/></c:when><c:when test="${special == 3}"><bean:message key="post_newthread_reward"/></c:when><c:when test="${special == 5}"><bean:message key="post_newthread_debate"/></c:when><c:otherwise><bean:message key="post_new"/></c:otherwise></c:choose></h1>
	<table summary="post" cellspacing="0" cellpadding="0" id="newpost">
		<thead><tr><th><bean:message key="username"/></th><td><c:choose><c:when test="${jsprun_uid>0}">${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</c:when><c:otherwise><bean:message key="guest"/> [<a href="logging.jsp?action=login"><bean:message key="member_login"/></a>]</c:otherwise></c:choose></td></tr></thead>
		<c:if test="${seccodecheck}"><tr><th><label for="seccodeverify"><bean:message key="seccode"/></label></th><td><div id="seccodeimage"></div> <input type="text" onfocus="updateseccode();this.onfocus = null" id="seccodeverify" name="seccodeverify" size="8" maxlength="4" tabindex="0" /> <em class="tips"><strong><bean:message key="seccode_click"/></strong> <bean:message key="seccode_refresh"/></em><script type="text/javascript">var seccodedata = [${seccodedata['width']}, ${seccodedata['height']}, ${seccodedata['type']}];</script></td></tr></c:if>
		<c:if test="${secqaacheck}"><tr><th><label for="secanswer"><bean:message key="secqaa"/></label></th><td><div id="secquestion"></div> <input type="text" name="secanswer" id="secanswer" size="25" maxlength="50" tabindex="1" /><script type="text/javascript">ajaxget('ajax.do?action=updatesecqaa', 'secquestion');</script></td></tr></c:if>
		<c:if test="${special == 3&&allowpostreward}">
			<tr>
				<th><bean:message key="reward_price"/><c:if test="${extcredit.title!=''}">(${extcredit.title})</c:if></th>
				<td>
					<input onkeyup="getrealprice(this.value)" type="text" name="rewardprice" size="6" value="${usergroups.minrewardprice}" tabindex="2" />
					<em class="tips"> <bean:message key="reward_tax_after"/>: <span id="realprice">0</span> ${extcredit.unit} (<bean:message key="reward_low"/> ${usergroups.minrewardprice} ${extcredit.unit}<c:if test="${usergroups.maxrewardprice>0}"> - ${usergroups.maxrewardprice} ${extcredit.unit}</c:if></em>)
				</td>
			</tr>
			<script type="text/javascript">
				$('realprice').innerHTML = parseInt($('postform').rewardprice.value) + parseInt(Math.ceil( $('postform').rewardprice.value * ${settings.creditstax} ));
				function getrealprice(price){
					if(!price.search(/^\d+$/) ) {
						n = Math.ceil(parseInt(price) + price * ${settings.creditstax});
						if(price > 32767) {
							$('realprice').innerHTML = '<b><bean:message key="reward_price_overflow"/></b>';
						} else if(price < 1 || (0 > 0 && price > 0)) {
							$('realprice').innerHTML = '<b><bean:message key="reward_price_bound"/></b>';
						} else {
							$('realprice').innerHTML = n;
						}
					}else{
						$('realprice').innerHTML = '<b><bean:message key="input_invalid"/></b>';
					}
				}
			</script>
		</c:if>
		<tr>
			<th style="border-bottom: 0"><label for="subject"><bean:message key="subject"/></label></th>
			<td style="border-bottom: 0">${typeselect } <input type="text" name="subject" id="subject" size="45" value="${subject}" tabindex="3"/></td>
		</tr>
		<tbody id="threadtypes"></tbody>
		<c:if test="${special==6&&allowpostvideo}">
			<tr>
				<th style="border-bottom: 0" valign="top">
					<label for="uploaddiv"><input type="radio" name="visup" value="1" checked="checked" onclick="$('uploaddiv').innerHTML = getVideoPlayer(0)"><bean:message key="post_newthreadvideo"/></label><br />
					<label for="recorddiv"><input type="radio" name="visup" value="0" onclick="$('uploaddiv').innerHTML = getVideoPlayer(1)"><bean:message key="video_record"/></label>
				</th>
				<td style="border-bottom: 0">
					<div id="uploaddiv"></div>
					<input type="checkbox" name="vautoplay" value="1"><bean:message key="video_auto"/>
					<input type="checkbox" name="vshare" value="1" checked><bean:message key="video_share"/><br /><em><bean:message key="video_limit_size"/>: 100M</em><br /><em><bean:message key="video_format_supported"/>: .flv .mpg .m4v .mpeg .mpe .vod .wmv .wm .rm .rmvb .avi .asx .ra .ram .asf .3gp .mov .mp4</em>
					<input type="hidden" name="vid" id="vid" />
					<input type="hidden" name="subjectu8" id="subjectu8">
					<input type="hidden" name="tagsu8" id="tagsu8">
					<script type="text/javascript">
					function setVideoInfo(vid) {
						$('vid').value = vid;
					}
					function getVideoPlayer(isup) {
						if(!isup) {
							var s = '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="351" height="30" title="aa" id="vidPlayer">';
							s += '<param name="movie" value=\'http://union.bokecc.com/flash/jsprun2/VideoDuke.swf?siteid=FFDF6D92D780353E&code=vcIaf6IBhuqgd3UpcX%2BZJYlrLtE9%2F0TmZjGDdZ%2F9XuxULQLuP4nxlwZnULc\'/>';
							s += '<param name="quality" value="high" />';
							s += '<param name="allowScriptAccess" value="always" />';
							s += '<param name="allownetworking" value="all" />';
							s += '<embed src=\'http://union.bokecc.com/flash/jsprun2/VideoDuke.swf?siteid=FFDF6D92D780353E&code=vcIaf6IBhuqgd3UpcX%2BZJYlrLtE9%2F0TmZjGDdZ%2F9XuxULQLuP4nxlwZnULc\' quality="high" allowScriptAccess="always" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="351" height="30"></embed>';
							s += '</object>';
						} else {
							var s = '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="351" height="237" title="aa" id="vidPlayer">';
							s += '<param name="movie" value=\'http://union.bokecc.com/flash/jsprun2/VideoRecord.swf?siteid=FFDF6D92D780353E&code=vcIaf6IBhuqgd3UpcX%2BZJYlrLtE9%2F0TmZjGDdZ%2F9XuxULQLuP4nxlwZnULc\'/>';
							s += '<param name="quality" value="high" />';
							s += '<param name="allowScriptAccess" value="always" />';
							s += '<param name="allownetworking" value="all" />';
							s += '<embed src=\'http://union.bokecc.com/flash/jsprun2/VideoRecord.swf?siteid=FFDF6D92D780353E&code=vcIaf6IBhuqgd3UpcX%2BZJYlrLtE9%2F0TmZjGDdZ%2F9XuxULQLuP4nxlwZnULc\' quality="high" allowScriptAccess="always" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="351" height="237"></embed>';
							s += '</object>';
						}
						return s;
					}
					$('uploaddiv').innerHTML = getVideoPlayer(0);
					</script>
				</td>
			</tr>
			<tr>
				<th style="border-bottom: 0" valign="top"><bean:message key="menu_video_class"/></th>
				<td style="border-bottom: 0" valign="top">
					<style type="text/css">
#vclassesdiv {
	list-style: none;
}

#vclassesdiv li {
	width: 80px;
	float: left;
}
</style>
					<ul id="vclassesdiv">
						
					</ul>
				</td>
			</tr>
		</c:if>
		<c:if test="${special==1&&allowpostpoll}">
			<tr>
				<th><label for="expiration"><bean:message key="poll_days_valid"/></label></th>
				<td><input type="text" name="expiration" id="expiration" value="0" size="6" tabindex="4" /><em class="tips">(<bean:message key="post_zero_is_nopermission"/>)</em></td>
			</tr>
			<tr>
				<th valign="top"><bean:message key="post_poll_options"/><br /><bean:message key="post_poll_comment" arg0="${settings.maxpolloptions}"/><br /><br /><input type="checkbox" name="visiblepoll" value="1" /> <bean:message key="poll_submit_after"/><br /><input type="checkbox" name="multiplepoll" value="1" onclick="this.checked?$('maxchoicescontrol').style.display='':$('maxchoicescontrol').style.display='none';" /> <bean:message key="post_poll_allowmultiple"/><br /><span id="maxchoicescontrol" style="display: none"><bean:message key="poll_max_options"/>:<input type="text" name="maxchoices" value="${settings.maxpolloptions}" size="5"><br /></span></th>
				<td><textarea rows="8" name="polloptions" style="width: 600px; word-break: break-all" tabindex="5">${polloptions}</textarea></td>
			</tr>
		</c:if>
		<tr><jsp:include flush="true" page="post_editor.jsp" /></tr>
		<c:if test="${settings.tagstatus>0}">
			<tr>
				<th><label for="tags"><bean:message key="post_tag"/></label></th>
				<td><input size="45" type="text" id="tags" name="tags" value="" tabindex="200" />&nbsp; <span id="tagselect"></span><em class="tips"> <bean:message key="tag_comment"/></em></td>
			</tr>
		</c:if>
		<c:if test="${special==5}">
			<tr>
				<th><label class="affirmpoint"><bean:message key="debate_square_point"/></label></th>
				<td><textarea name="affirmpoint" id="affirmpoint" rows="10" cols="20" style="width: 99%; height: 60px" tabindex="201" onkeydown="ctlent(event)"></textarea></td>
			</tr>
			<tr>
				<th><label class="negapoint"><bean:message key="debate_opponent_point"/></label></th>
				<td><textarea name="negapoint" id="negapoint" rows="10" cols="20" style="width: 99%; height: 60px" tabindex="202" onkeydown="ctlent(event)"></textarea></td>
			</tr>
		</c:if>
		<thead>
			<tr>
				<th>&nbsp;</th>
				<td><label><input id="advshow" class="checkbox" type="checkbox" onclick="showadv()" tabindex="203" /><bean:message key="magics_information"/></label></td>
			</tr>
		</thead>
		<tbody id="adv" style="display: none">
			<c:if test="${special==5}">
				<tr>
					<th><label for="endtime"><bean:message key="endtime"/></label></th>
					<td><input onclick="showcalendar(event, this, true)" type="text" name="endtime" size="45" value="" tabindex="204" /></td>
				</tr>
				<tr>
					<th><label for="umpire"><bean:message key="debate_umpire"/></label></th>
					<td><input type="text" name="umpire" id="umpire" size="45" tabindex="205" onblur="checkuserexists(this.value, 'checkuserinfo')" value="${jsprun_userss}" /> <span id="checkuserinfo"></span></td>
				</tr>
			</c:if>
			<c:if test="${usergroups.allowsetreadperm>0}">
				<tr>
					<th><label for="readperm"><bean:message key="readperm_thread"/></label></th>
					<td><input type="text" name="readperm" id="readperm" size="6" value="${readperm}" tabindex="206" /> <em class="tips">(<bean:message key="post_zero_is_nopermission"/>)</em></td>
				</tr>
			</c:if>
			<c:if test="${usergroups.maxprice>0&&!(special>0)&&extcredit!=null}">
				<tr>
					<th><label for="price"><bean:message key="magics_price"/>(${extcredit.title})</label></th>
					<td><input type="text" name="price" id="price" size="6" value="${price}" tabindex="207" /> <em class="tips">${extcredit.unit} (<bean:message key="post_price_comment"/> ${usergroups.maxprice} ${extcredit.unit}<c:if test="${settings.maxincperthread>0}"><bean:message key="post_price_income_comment"/> ${settings.maxincperthread>0} ${extcredit.unit}</c:if><c:if test="${settings.maxchargespan>0}"><bean:message key="post_price_charge_comment"  arg0="${settings.maxchargespan}"/></c:if>) <bean:message key="post_price_free_comment"/></em></td>
				</tr>
			</c:if>
			<c:if test="${special==0}">
				<tr>
					<th><bean:message key="a_post_jspruncodes_icon"/></th>
					<td><label><input class="radio" type="radio" name="iconid" value="0" checked="checked" tabindex="208" /> <bean:message key="none"/></label> <c:forEach items="${icons}" var="icon"><input class="radio" type="radio" name="iconid" value="${icon.key}" /><img src="images/icons/${icon.value}" alt="" /> </c:forEach></td>
				</tr>
			</c:if>
		</tbody>
		<tr class="btns">
			<th>&nbsp;</th>
			<td>
				<input type="hidden" name="wysiwyg" id="${editorid}_mode" value="${editormode}" />
				<button type="submit" name="topicsubmit" id="postsubmit" value="true" tabindex="300"><c:choose><c:when test="${special == 1}"><bean:message key="post_newthread_poll"/></c:when><c:when test="${special == 3}"><bean:message key="post_newthread_reward"/></c:when><c:when test="${special == 5}"><bean:message key="post_newthread_debate"/></c:when><c:otherwise><bean:message key="post_new"/></c:otherwise></c:choose></button>
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
function checkuserexists(username, objname) {
	var x = new Ajax();
	username = is_ie && document.charset == 'UTF-8' ? encodeURIComponent(username) : username;
	x.get('ajax.do?inajax=1&action=checkuserexists&username=' + username, function(s){
		var obj = $(objname);
		obj.innerHTML = s;
	});
}
if(${typeid>0}){
	ajaxget('post.jsp?action=threadtypes&typeid=${typeid}&fid=${fid}&inajax=1', 'threadtypes', 'threadtypeswait');
}
</script>
<jsp:include flush="true" page="post_js.jsp" />
<jsp:include flush="true" page="footer.jsp" />