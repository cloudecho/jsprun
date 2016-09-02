<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="cn.jsprun.foreg.vo.topicadmin.BaseVO"%>
<jsp:directive.include file="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}" ${settings.forumjump==1&&settings.jsmenu_1>0?"class=dropmenu onmouseover=showMenu(this.id)":""}>${settings.bbname}</a> &raquo;
	<c:forEach items="${valueObject.fid_NameMapList}" var="fid_NameMap">
		<a href="forumdisplay.jsp?fid=${fid_NameMap.fid}">${fid_NameMap.fName}</a>&raquo;
	</c:forEach>
	<c:if test="${valueObject.showTopicName}">
	<a href="viewthread.jsp?tid=${valueObject.topicId}">${valueObject.topicName}</a>&raquo;
	</c:if>
	
	<c:choose>
		<c:when test="${action=='delete'||action=='delpost'||action=='deleteMirrorImage'}"><bean:message key="admin_delthread" /></c:when>
		<c:when test="${action=='move'}"><bean:message key="admin_move" /></c:when>
		<c:when test="${action=='highlight'}"><bean:message key="admin_highlight" /></c:when>
		<c:when test="${action=='type'}"><bean:message key="menu_forum_threadtypes" /></c:when>
		<c:when test="${action=='close'}"><bean:message key="admin_openclose" /></c:when>
		<c:when test="${action=='stick'}"><bean:message key="admin_stick_unstick" /></c:when>
		<c:when test="${action=='digest'}"><bean:message key="admin_digest_addremove" /></c:when>
		<c:when test="${action=='removereward'}"><bean:message key="RMR" /></c:when>
		<c:when test="${action=='bump'}"><bean:message key="admin_bump_down" /></c:when>
		<c:when test="${action=='recommend'}"><bean:message key="REC" /></c:when>
	</c:choose>
</div>
<form method="post"
	action="topicadmin.jsp?currentOperation=${action }&amp;operation=moderate"
	id="postform">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="fid" value="${valueObject.fid}" />
	<input type="hidden" name="referer" value="forumdisplay.jsp?fid=${valueObject.fid}" />
	<input type="hidden" name="pageInfo" value="${valueObject.pageInfo}">
	<div class="mainbox formbox">
		<h1>
			<c:choose>
				<c:when test="${action=='delete'||action=='delpost'||action=='deleteMirrorImage'}"><bean:message key="admin_delthread" /></c:when>
				<c:when test="${action=='move'}"><bean:message key="admin_move" /></c:when>
				<c:when test="${action=='highlight'}"><bean:message key="admin_highlight" /></c:when>
				<c:when test="${action=='type'}"><bean:message key="menu_forum_threadtypes" /></c:when>
				<c:when test="${action=='close'}"><bean:message key="admin_openclose" /></c:when>
				<c:when test="${action=='stick'}"><bean:message key="admin_stick_unstick" /></c:when>
				<c:when test="${action=='digest'}"><a href="member.jsp?action=credits&amp;view=digest" target="_blank"><bean:message key="credits_policy_view" /></a><bean:message key="admin_digest_addremove" /></c:when>
				<c:when test="${action=='removereward'}"><bean:message key="RMR" /></c:when>
				<c:when test="${action=='bump'}"><bean:message key="admin_bump_down" /></c:when>
				<c:when test="${action=='recommend'}"><bean:message key="REC" /></c:when>
			</c:choose>
		</h1>
		<table summary="Operating" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th><bean:message key="username" /></th>
					<td>${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout" /></a>]</td>
				</tr>
			</thead>
			<c:choose>
				<c:when test="${action=='move'}">
					<tr>
						<th><label for="moveto"><bean:message key="admin_move_target" /></label></th>
						<td>
							<select id="moveto" name="moveto">
								${valueObject.selectContent}
							</select>
						</td>
					</tr>
					<tr>
						<th><bean:message key="admin_move_type" /></th>
						<td>
							<label><input class="radio" type="radio" name="type" value="normal" checked="checked" /> <bean:message key="admin_move" /></label>
							<label><input class="radio" type="radio" name="type" value="redirect" /> <bean:message key="admin_move_redirect" /></label>
						</td>
					</tr>
				</c:when>
				<c:when test="${action=='highlight'}">
					<tr>
						<th><bean:message key="admin_highlight_style" /></th>
						<td>
							<label><input class="checkbox" type="checkbox" name="highlight_style" value="40" ${valueObject.bchecked?"checked":""}/> <strong style="font-weight: bold; color: #000;"><bean:message key="admin_highlight_bold" /></strong></label>&nbsp;
							<label><input class="checkbox" type="checkbox" name="highlight_style" value="20" ${valueObject.ichecked?"checked":""}/> <em style="font-style: italic;"><bean:message key="admin_highlight_italic" /></em></label>&nbsp;
							<label><input class="checkbox" type="checkbox" name="highlight_style" value="10" ${valueObject.uchecked?"checked":""}/> <span style="text-decoration: underline;"><bean:message key="admin_highlight_underline" /></span></label>
						</td>
					</tr>
					<tr>
						<th><bean:message key="admin_highlight_color" /></th>
						<td><c:forEach items="${colorArray}" var="color" varStatus="index">
							<label class="highlight"><input class="radio" type="radio" name="highlight_color" value="${index.index}" ${valueObject.highlight_color == index.index ?"checked":""} /><em style="background: ${index.index==0 ? styles.LINK : color};"></em></label>
						</c:forEach></td>
					</tr>
				</c:when>
				<c:when test="${action=='type'}">
					<tr>
						<th><bean:message key="admin_move_target" /></th>
						<td>
							<select name="typeid">
								<option value="0">&nbsp;</option>
								<c:forEach items="${valueObject.topicClassMap}" var="topicClass"><option value="${topicClass.key }">${topicClass.value }</option></c:forEach>
							</select>
						</td>
					</tr>
				</c:when>
				<c:when test="${action=='close'}">
					<tr>
						<th><bean:message key="operation" /></th>
						<td>
							<label><input class="radio" type="radio" name="close" value="0" ${valueObject.close== 0?'checked':'' }/> <bean:message key="admin_open" /></label>&nbsp;
							<label><input class="radio" type="radio" name="close" value="-1" ${valueObject.close== -1?'checked':'' }/> <bean:message key="admin_close" /></label>
						</td>
					</tr>
				</c:when>
				<c:when test="${action=='stick'}">
					<tr>
						<th><bean:message key="level" /></th>
						<td>
							<c:if test="${valueObject.showUnchain}"><label><input class="radio" type="radio" name="level" value="0" onclick="$('expirationarea').disabled=1" /> <bean:message key="UST" /> </label>&nbsp;</c:if>
							<label><input class="radio" type="radio" name="level" value="1" ${valueObject.level== '1'?'checked':''} onclick="$('expirationarea').disabled=0" /> <img src="${styles.IMGDIR}/pin_1.gif" alt="<bean:message key="admin_stick_1" />" /> <bean:message key="admin_stick_1" /></label>
							<c:if test="${valueObject.stickPurview>1}"><label><input class="radio" type="radio" name="level" value="2" ${valueObject.level== '2'?'checked':''} onclick="$('expirationarea').disabled=0" /> <img src="${styles.IMGDIR}/pin_2.gif" alt="<bean:message key="admin_stick_2" />" /> <bean:message key="admin_stick_2" /></label></c:if>
							<c:if test="${valueObject.stickPurview>2}"><label><input class="radio" type="radio" name="level" value="3" ${valueObject.level== '3'?'checked':''} onclick="$('expirationarea').disabled=0" /> <img src="${styles.IMGDIR}/pin_3.gif" alt="<bean:message key="admin_stick_3" />" /> <bean:message key="admin_stick_3" /></label></c:if>
						</td>
					</tr>
				</c:when>
				<c:when test="${action=='digest'}">
					<tr>
						<th><bean:message key="level" /></th>
						<td>
							<c:if test="${valueObject.showUnchain}">
								<label>
									<input class="radio" type="radio" name="level" value="0" />
									<bean:message key="UDG" />
								</label>&nbsp;
							</c:if>
							<label>
								<input class="radio" type="radio" name="level" value="1" ${valueObject.level== '1'?'checked':''} onclick="$('expiration').disabled=0" />
								<img src="${styles.IMGDIR}/digest_1.gif" alt="" />
							</label>
							<label>
								<input class="radio" type="radio" name="level" value="2" ${valueObject.level== '2'?'checked':''} onclick="$('expiration').disabled=0" />
								<img src="${styles.IMGDIR}/digest_2.gif" alt="" />
							</label>
							<label>
								<input class="radio" type="radio" name="level" value="3" ${valueObject.level== '3'?'checked':''} onclick="$('expiration').disabled=0" />
								<img src="${styles.IMGDIR}/digest_3.gif" alt="" />
							</label>
						</td>
					</tr>
				</c:when>
				<c:when test="${action=='removereward'}"></c:when>
				<c:when test="${action=='bump'}">
					<tr>
						<th><bean:message key="operation" /></th>
						<td>
							<label><input class="radio" type="radio" name="isbump" value="1" checked="checked" /> <bean:message key="admin_bump" /></label>
							&nbsp;
							<label><input class="radio" type="radio" name="isbump" value="0" /> <bean:message key="admin_down" /></label>
						</td>
					</tr>
				</c:when>
				<c:when test="${action=='recommend'}">
					<tr>
						<th><bean:message key="operation" /></th>
						<td>
							<label><input class="radio" type="radio" name="isrecommend" value="1" checked="checked" /> <bean:message key="REC" /></label>
							<label><input class="radio" type="radio" name="isrecommend" value="0" /> <bean:message key="URE" /></label>
						</td>
					</tr>
					<tr>
						<th><label for="recommendexpire"><bean:message key="validity" /></label></th>
						<td>
							<select id="recommendexpire" name="recommendexpire">
								<option value="86400"><bean:message key="1_day" /></option>
								<option value="259200"><bean:message key="3_day" /></option>
								<option value="432000"><bean:message key="5_day" /></option>
								<option value="604800"><bean:message key="7_day" /></option>
								<option value="2592000"><bean:message key="admin_recommend_month" /></option>
								<option value="7776000"><bean:message key="admin_recommend_month_three" /></option>
								<option value="15552000"><bean:message key="admin_recommend_month_six" /></option>
								<option value="31536000"><bean:message key="365_day" /></option>
							</select>
						</td>
					</tr>
				</c:when>
			</c:choose>
			<c:if test="${action=='highlight'||action=='stick'||action=='digest'||action=='close'||action=='move'||action=='delete'}">
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
				<script src="include/javascript/calendar.js" type="text/javascript"></script>
				<tr id="expirationarea">
					<th><label for="expiration">
					<c:choose>
						<c:when test="${action=='move'||action=='delete'}"><bean:message key="execute_time" /></c:when>
						<c:otherwise><bean:message key="validity" /></c:otherwise>
					</c:choose>
					</label></th>
					<td><input onclick="showcalendar(event, this${action=='move'||action=='delete'?"":", true"})" type="text" name="expiration" id="expiration" size="15" value="" /> 
					<c:choose>
						<c:when test="${action=='move'||action=='delete'}"><bean:message key="thread_moderations_expiration_comment2" arg0="${valueObject.minTime }"  arg1="${valueObject.maxTime }" /></c:when>
						<c:otherwise><bean:message key="thread_moderations_expiration_comment" arg0="${valueObject.minTime }"  arg1="${valueObject.maxTime }" /></c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:if>
			<jsp:include flush="true" page="topicadmin_reason.jsp" />
			<c:if test="${action=='highlight'||action=='stick'||action=='digest'}">
				<tr>
					<th><bean:message key="admin_next" /></th>
					<td>
						<label><input class="radio" type="radio" name="next" value="" checked="checked" /> <bean:message key="none" /></label>&nbsp;
						<c:if test="${action!='highlight'}"><label><input class="radio" type="radio" name="next" value="highlight" /> <bean:message key="admin_highlight" /></label>&nbsp;</c:if>
						<c:if test="${action!='stick'}"><label><input class="radio" type="radio" name="next" value="stick" /> <bean:message key="admin_stick_unstick" /></label>&nbsp;</c:if>
						<c:if test="${action!='digest'}"><label><input class="radio" type="radio" name="next" value="digest"> <bean:message key="admin_digest_addremove" /></label>&nbsp;</c:if>
					</td>
				</tr>
			</c:if>
			<tr class="btns">
				<th>&nbsp;</th>
				<td><button type="submit" name="modsubmit" id="postsubmit" value="true"><bean:message key="submitf" /></button> <bean:message key="post_submit_hotkey" />
			</tr>
		</table>
	</div>
	<c:choose>
		<c:when test="${valueObject.singleThread}">
			<input type="hidden" name="moderate_" value="${valueObject.threadId }" />
			<c:if test="${valueObject.showLogList}">
				<div class="mainbox">
					<h3><bean:message key="thread_mod" /></h3>
					<table summary="Log List" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<td><bean:message key="operator" /></td>
								<td><bean:message key="time" /></td>
								<td><bean:message key="operation" /></td>
								<td><bean:message key="validity" /></td>
							</tr>
						</thead>
						<c:forEach items="${valueObject.logList}" var="log">
						<tr>
							<td>
								<c:choose>
									<c:when test="${log.showUsername}"><a href="space.jsp?uid=${log.uid}" target="_blank">${log.username}</a></c:when>
									<c:otherwise><bean:message key="thread_moderations_cron" /></c:otherwise>
								</c:choose>
							</td>
							<td>${log.operationTime}</td>
							<td ${log.css}><strong>${log.operation}</strong></td>
							<td ${log.css}>${log.expiretion}</td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
		</c:when>
		<c:otherwise>
			<div class="mainbox threadlist">
				<table summary="Threads" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th><bean:message key="subject" /></th>
							<td class="author"><bean:message key="author" /></td>
							<td class="nums"><bean:message key="threads_replies" /></td>
							<td class="lastpost"><bean:message key="a_post_threads_lastpost" /></td>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${valueObject.threadInfoList}" var="threadInfo">
						<tr>
							<th><input type="checkbox" name="moderate_" value="${threadInfo.threadId}" checked="checked" /> <a href="viewthread.jsp?tid=${threadInfo.threadId}&page=${threadInfo.pageNumber }">${threadInfo.title}</a></th>
							<td class="author">
								<c:choose>
									<c:when test="${threadInfo.showAuthor}"><a href="space.jsp?uid=${threadInfo.authorId}">${threadInfo.authorName}</a></c:when>
									<c:otherwise><bean:message key="anonymous" /></c:otherwise>
								</c:choose>
							</td>
							<td class="nums">${threadInfo.replies}</td>
							<td class="lastpost">${threadInfo.lastpost}
								<cite>by 
									<c:choose>
										<c:when test="${threadInfo.showLastPoster}"><a href="space.jsp?username=<jrun:encoding value="${threadInfo.lastPosterName}"/>">${threadInfo.lastPosterName}</a></c:when>
										<c:otherwise><bean:message key="anonymous" /></c:otherwise>
									</c:choose> 
								</cite>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</form>
<jsp:directive.include file="footer.jsp" />