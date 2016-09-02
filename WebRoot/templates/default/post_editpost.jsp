<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> ${navigation} &raquo;
	<c:choose>
		<c:when test="${isfirstpost&& special == 1}"><bean:message key="post_editpost_poll"/></c:when>
		<c:when test="${isfirstpost&& special == 3}"><bean:message key="post_editpost_reward"/></c:when>
		<c:when test="${isfirstpost&& special == 5}"><bean:message key="post_editpost_debate"/></c:when>
		<c:otherwise><bean:message key="a_post_moderate_edit_post"/></c:otherwise>
	</c:choose>
</div>
<c:if test="${special==4 || special==5}">
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
var attachments = new Array();
var bbinsert = parseInt('${settings.bbinsert}');
var attachimgurl = new Array();
var isfirstpost = parseInt('${isfirstpost=='true'?1:0}');
var special = parseInt('${special}');
var allowposttrade = parseInt('${usergroups.allowposttrade}');
var allowpostreward = parseInt('${usergroups.allowpostreward}');
var allowpostactivity = parseInt('${usergroups.allowpostactivity}');
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
<form method="post" id="postform" action="post.jsp?action=edit&amp;editsubmit=yes&formHash=${jrun:formHash(pageContext.request)}" onSubmit="return validate(this)"; enctype="multipart/form-data">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox formbox">
		<h1>
			<c:choose>
				<c:when test="${isfirstpost&& special == 1}"><bean:message key="post_editpost_poll"/></c:when>
				<c:when test="${isfirstpost&&special == 3}"><bean:message key="post_editpost_reward"/></c:when>
				<c:when test="${isfirstpost&&special == 5}"><bean:message key="post_editpost_debate"/></c:when>
				<c:otherwise><bean:message key="a_post_moderate_edit_post"/></c:otherwise>
			</c:choose>
		</h1>
		<table summary="Edit Post" cellspacing="0" cellpadding="0" id="editpost">
			<thead>
				<c:if test="${jsprun_uid>0}">
				<tr>
					<th><bean:message key="username"/></th>
					<td>${user.username} <em class="tips">[<a href="logging.jsp?action=logout&amp;formhash=${formhash}"><bean:message key="member_logout"/></a>]</em></td>
				</tr>
				</c:if>
			</thead>
			<c:if test="${special == 3&&isfirstpost}">
				<tr>
					<th><bean:message key="reward_price"/>(${extcredit.title})</th>
					<td>
					<c:choose>
						<c:when test="${thread.price>0}">
							<input onkeyup="getrealprice(this.value)" type="text" name="rewardprice" size="6" value="${realprice}" tabindex="2" />
							<bean:message key="reward_tax_add"/>: <span id="realprice">0</span> ${extcredit.unit} (<bean:message key="reward_low"/> ${usergroups.minrewardprice} ${extcredit.unit}<c:if test="${usergroups.maxrewardprice>0}"> - ${usergroups.maxrewardprice} ${extcredit.unit}</c:if>)
						</c:when>
						<c:when test="${thread.price<0&&ismoderator}"><input type="text" name="rewardprice" size="6" value="${realprice}" tabindex="2" /></c:when>
						<c:otherwise><input onkeyup="getrealprice(this.value)" type="hidden" name="rewardprice" size="6" value="${realprice}" tabindex="2" />${realprice} ${extcredit.unit}</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<c:if test="${thread.price>0}">
					<script type="text/javascript">
						$('realprice').innerHTML = 0;
						function getrealprice(price){
							if(!price.search(/^\d+$/) ) {
								n = parseInt(price) + Math.ceil(parseInt(price * ${settings.creditstax})) - (parseInt(${thread.price}) + Math.ceil(parseInt(${thread.price} * ${settings.creditstax})));
								if(price > 32767) {
									$('realprice').innerHTML = '<b><bean:message key="reward_price_overflow"/></b>';
								} else if (price < ${thread.price}) {
									$('realprice').innerHTML = '<b><bean:message key="reward_cant_fall"/></b>';
								}else if (price < ${usergroups.minrewardprice} || (${usergroups.maxrewardprice} > 0 && price > ${usergroups.maxrewardprice})) {
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
			</c:if>
			<tr>
				<th style="border-bottom: 0;"><bean:message key="subject"/></th>
				<td style="border-bottom: 0;">
					<c:if test="${isfirstpost&&threadtypes.types!=null}">${typeselect}</c:if>
					<c:choose>
						<c:when test="${special==3&&!ismoderator&&isfirstpost&&thread.replies>0}"><input type="hidden" name="subject" id="subject" size="45" value="${post.subject}" tabindex="3" />${post.subject}</c:when>
						<c:otherwise><input type="text" name="subject" id="subject" size="45" value="${post.subject}" tabindex="3" /></c:otherwise>
					</c:choose>
					<input type="hidden" name="origsubject" value="${post.subject}" />
					<c:if test="${special==6}">
						<input type="hidden" name="subjectu8" value="" />
						<input type="hidden" name="tagsu8" value="" />
						<input type="hidden" name="vid" value="1" />
					</c:if>
				</td>
			</tr>
			<c:if test="${special==1 && isfirstpost && (usergroups.alloweditpoll==1 || thread.authorid==jsprun_uid)}">
				<input type="hidden" name="polls" value="yes">
				<tr><th><bean:message key="poll_days_valid"/></th><td><input type="text" name="expiration" value="${sparetime}" size="6" tabindex="4" /> <em class="tips">(<bean:message key="post_zero_is_nopermission"/>)</em></td></tr>
				<tr>
					<th valign="top"><bean:message key="post_poll_options"/><br />
						<bean:message key="post_poll_comment" arg0="${settings.maxpolloptions}"/><br /><br />
						<input type="checkbox" name="visibilitypoll" value="1" tabindex="4" ${polls.visible==1?"checked":""}/>
						<bean:message key="poll_submit_after"/>
						<br />
						<input type="checkbox" name="multiplepoll" value="1" tabindex="5" onclick="this.checked?$('maxchoicescontrol').style.display='':$('maxchoicescontrol').style.display='none';"  <c:if test="${polls.multiple==1}">checked</c:if>/>
						<bean:message key="post_poll_allowmultiple"/>
						<br />
						<c:if test="${sparetime!='poll_finish'}">
						<input type="checkbox" name="close" value="1" />
						<bean:message key="poll_close"/>
						<br />
						</c:if>
						<span id="maxchoicescontrol" style="display:${polls.multiple==1?'':'none'}"><bean:message key="poll_max_options"/>:<input type="text" name="maxchoices" value="${polls.maxchoices}" size="5" /> <br /></span>
					</th>
					<td>
						<bean:message key="display_order"/>&nbsp;<a id="addpolloptlink" href="#" onclick="addpollopt()">[<bean:message key="poll_option_add"/>]</a>
						<br />
						<c:forEach items="${options}" var="option">
							<input type="hidden" name="polloptionid[]" value="${option.polloptionid}">
							<input type="text" name="displayorder[]" value="${option.displayorder}" size="5" tabindex="6" style="text-align:right">
							<input type="text" name="polloption[]" value="${option.polloption}" tabindex="7" size="55" />
							<br />
						</c:forEach>
						<span id="addpolloptindex"></span>
					</td>
				</tr>
				<script type="text/javascript">
					var max = ${optionsize};
					function addpollopt() {
						if(max < ${settings.maxpolloptions}) {
							max++;
							var optrow='<input type="text" name="displayordernew" value="" size="5" style="text-align:right">&nbsp;<input type="text" name="polloptionnew" value="" size="55"><br />';
							$('addpolloptindex').innerHTML = $('addpolloptindex').innerHTML + optrow;
							if(max == ${settings.maxpolloptions}) {
								$('addpolloptlink').disabled=true;
							}
						}
					}
				</script>
			</c:if>
			<tbody id="threadtypes"></tbody>
			<tr><jsp:include flush="true" page="post_editor.jsp" /></tr>
			<c:if test="${isfirstpost}">
				<c:if test="${settings.tagstatus>0}">
					<tr>
						<th><label for="tags"><bean:message key="post_tag"/></label></th>
						<td><input size="45" type="text" id="tags" name="tags" value="${tags}" tabindex="200" /> &nbsp;<span id="tagselect"></span><em class="tips"> <bean:message key="tag_comment"/></em></td>
					</tr>
				</c:if>
				<c:if test="${special==5}">
					<tr>
						<th><bean:message key="debate_square_point"/></th>
						<td><textarea name="affirmpoint" rows="10" cols="20" style="width:99%; height:60px" tabindex="201" onkeydown="ctlent(event)">${debates.affirmpoint}</textarea></td>
					</tr>
					<tr>
						<th><bean:message key="debate_opponent_point"/></th>
						<td><textarea name="negapoint" rows="10" cols="20" style="width:99%; height:60px" tabindex="202" onkeydown="ctlent(event)">${debates.negapoint}</textarea></td>
					</tr>
					<tr>
						<th><bean:message key="endtime"/></th>
						<td>
							<c:choose>
								<c:when test="${debates.endtime>0}"><input onclick="showcalendar(event, this, true)" type="text" name="endtime" size="45" value="<jrun:showTime timeInt="${debates.endtime}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>" tabindex="203" /></c:when>
								<c:otherwise><input onclick="showcalendar(event, this, true)" type="text" name="endtime" size="45" value="" tabindex="203" /></c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th><bean:message key="debate_umpire"/></th>
						<td><input type="text" name="umpire" size="45" tabindex="204" onblur="checkuserexists(this.value, 'checkuserinfo')" value="${debates.umpire}" /><span id="checkuserinfo"></span></td>
					</tr>
				</c:if>
				<thead><tr><th><bean:message key="magics_information"/></th><td>&nbsp;</td></tr></thead>
				<c:if test="${usergroups.allowsetreadperm>0}">
					<tr>
						<th><bean:message key="readperm_thread"/></th>
						<td><input type="text" name="readperm" size="6" value="${thread.readperm}" tabindex="205" /> <em class="tips">(<bean:message key="post_zero_is_nopermission"/>)</em></td>
					</tr>
				</c:if>
				<c:if test="${usergroups.maxprice>0&&special==0&&extcredit!=null}">
					<tr>
						<th><bean:message key="a_post_threads_price"/>(${extcredit.title})</th>
						<td>
							<c:choose>
								<c:when test="${thread.price==-1&&settings.maxchargespan>0 && (timestamp - thread.dateline >= settings.maxchargespan * 3600)}"><input type="text" name="price" size="6" value="${thread.price==-1?0: thread.price}" tabindex="206" disabled /> <em class="tips">${extcredit.unit} (<c:choose><c:when test="${thread.price==-1}"><bean:message key="post_price_refunded"/></c:when><c:otherwise><bean:message key="post_price_free"/></c:otherwise></c:choose>)</em></c:when>
								<c:otherwise><input type="text" name="price" size="6" value="${thread.price==-1?0: thread.price}" tabindex="206" /> <em class="tips">${extcredit.unit} (<bean:message key="post_price_comment"/> ${usergroups.maxprice} ${extcredit.unit}<c:if test="${settings.maxincperthread>0}"><bean:message key="post_price_income_comment"/> ${settings.maxincperthread>0} ${extcredit.unit}</c:if><c:if test="${settings.maxchargespan>0}"><bean:message key="post_price_charge_comment" arg0="${settings.maxchargespan}"/></c:if>) <bean:message key="post_price_free_comment"/></em></c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
				<c:if test="${!(special>0)}">
					<tr>
						<th><bean:message key="a_post_jspruncodes_icon"/></th>
						<td>
							<label><input class="radio" type="radio" name="iconid" value="0" checked="checked" tabindex="208" /> <bean:message key="none"/></label>
							<c:forEach items="${icons}" var="icon"><input class="radio" type="radio" name="iconid" value="${icon.key}" ${thread.iconid==icon.key?"checked":""}/><img src="images/icons/${icon.value}" alt="" /> </c:forEach>
							<br />
						</td>
					</tr>
				</c:if>
			</c:if>
			<tr class="btns">
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="special" value="${special}">
					<input type="hidden" name="isfirst" value="${isfirstpost}">
					<input type="hidden" name="page" value="${param.page}">
					<input type="hidden" name="wysiwyg" id="${editorid}_mode" value="${editormode}" />
					<input type="hidden" name="fid" id="fid" value="${fid}" />
					<input type="hidden" name="tid" value="${thread.tid}" />
					<input type="hidden" name="pid" value="${post.pid}" />
					<input type="hidden" name="postsubject" value="${post.subject}" />
					<button type="submit" name="editsubmit" id="postsubmit" value="true" tabindex="300">
						<c:choose>
							<c:when test="${isfirstpost&& special == 1}"><bean:message key="post_editpost_poll"/></c:when>
							<c:when test="${isfirstpost&& special == 3}"><bean:message key="post_editpost_reward"/></c:when>
							<c:when test="${isfirstpost&& special == 5}"><bean:message key="post_editpost_debate"/></c:when>
							<c:otherwise><bean:message key="a_post_moderate_edit_post"/></c:otherwise>
						</c:choose>
					</button>
					<em><bean:message key="post_submit_hotkey"/></em>&nbsp;&nbsp; &nbsp;<a href="###" id="restoredata" onclick="loadData()" title="<bean:message key="post_autosave_last_restore"/>"><bean:message key="post_autosave_restore"/></a>
				</td>
			</tr>
		</table>
	</div>
<jsp:include flush="true" page="post_editpost_attachlist.jsp"/>
</form>
<jsp:include flush="true" page="post_js.jsp" />
<script type="text/javascript">
	function checkuserexists(username, objname) {
		var x = new Ajax();
		username = is_ie && document.charset == 'UTF-8' ? encodeURIComponent(username) : username;
		x.get('ajax.jsp?inajax=1&action=checkuserexists&username=' + username, function(s){
			var obj = $(objname);
			obj.innerHTML = s;
		});
	}
	if(${thread.typeid>0&&isfirstpost}){
		ajaxget('post.jsp?action=threadtypes&tid=${thread.tid}&fid=${thread.fid}&typeid=${thread.typeid}&themeid=1', 'threadtypes', 'threadtypeswait');
	}
	</script>
<jsp:include flush="true" page="footer.jsp" />