<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<script src="include/javascript/viewthread.js" type="text/javascript"></script>
<script type="text/javascript">
zoomstatus = parseInt(${settings.zoomstatus});
lang['zoom_image'] = '<bean:message key="zoom_image"/>';
lang['a_system_js_newwindow_blank'] = '<bean:message key="a_system_js_newwindow_blank"/>';
lang['full_size'] = '<bean:message key="full_size"/>';
lang['closed'] = '<bean:message key="closed"/>';
lang['copy_to_cutedition'] = '<bean:message key="copy_to_cutedition"/>';
</script>
<div id="foruminfo">
	<div id="nav"><a id="forumlist" href="${settings.indexname}" ${settings.forumjump==1&&settings.jsmenu_1>0?"class=dropmenu onmouseover=showMenu(this.id)":""}>${settings.bbname}</a> ${navigation} &raquo; ${subject}</div>
	<c:if test="${(settings.google_status==1&&google_searchbox>0)||(settings.baidu_status==1&&baidu_searchbox>0)}">
		<script type="text/javascript">
			lang['webpage_search'] = '<bean:message key="webpage_search"/>';
			lang['site_search'] = '<bean:message key="site_search"/>';
			lang['search'] = '<bean:message key="search"/>';
		</script>
		<div id="headsearch">
			<c:if test="${settings.google_status==1&&google_searchbox>0}"><script type="text/javascript" src="forumdata/cache/google_var.js"></script><script type="text/javascript" src="include/javascript/google.js"></script></c:if>
			<c:if test="${settings.baidu_status==1&&baidu_searchbox>0}"><script type="text/javascript" src="forumdata/cache/baidu_var.js"></script><script type="text/javascript" src="include/javascript/baidu.js"></script></c:if>
		</div></c:if>
</div>

<c:if test="${pmlists!=null}"><div class="maintable" id="pmprompt"><jsp:include flush="true" page="pmprompt.jsp" /></div></c:if>
<div id="ad_text"></div>
<script type="text/javascript">
function checkform(theform) {
	if (theform.contact.value == '') {
		alert('<bean:message key="activiy_linkman_input"/>');
		theform.contact.focus();
		return false;
	} else if (theform.contact.value.length > 200) {
		alert('<bean:message key="activiy_linkman_more"/>');
		theform.contact.focus();
		return false;
	} else if (theform.message.value.length > 200) {
		alert('<bean:message key="activiy_guest_more"/>');
		theform.message.focus();
		return false;
	}
	return true;
}
</script>
<div class="pages_btns">
	<div class="threadflow"><a href="redirect.jsp?fid=${thread.fid}&amp;tid=${thread.tid}&amp;goto=nextoldset"> &lsaquo;&lsaquo; <bean:message key="last_thread"/></a> | <a href="redirect.jsp?fid=${thread.fid}&amp;tid=${thread.tid}&amp;goto=nextnewset"><bean:message key="next_thread"/> &rsaquo;&rsaquo;</a></div>
	<span class="pageback" <c:if test="${requestScope.visitedforums!=null}"> id="visitedforums" onmouseover="$('visitedforums').id = 'visitedforumstmp';this.id = 'visitedforums';showMenu(this.id)" </c:if>><a href="${backtrack}" title="<bean:message key="return_forumdisplay"/>"><bean:message key="return_forumdisplay"/></a></span>
	<c:if test="${allowpostreply}"><span class="replybtn"><a href="post.jsp?action=reply&amp;fid=${thread.fid}&amp;tid=${thread.tid}&amp;extra=${extra}&page=${pagesize}"><img src="${styles.IMGDIR}/reply_${sessionScope['org.apache.struts.action.LOCALE']}.gif" border="0" alt="" /></a></span></c:if>
	<c:if test="${allowpost||jsprun_uid==0}"><span class="postbtn" id="newspecial" onmouseover="$('newspecial').id = 'newspecialtmp';this.id = 'newspecial';showMenu(this.id)"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}" title="<bean:message key="post_new"/>"><img src="${styles.IMGDIR}/newtopic_${sessionScope['org.apache.struts.action.LOCALE']}.gif" alt="<bean:message key="post_new"/>" /></a></span></c:if>
</div>
<c:if test="${allowposttrade||allowpostpoll||allowpostreward||allowpostactivity||allowpostdebate||allowpostvideo||forumfield.threadtypes!=''||jsprun_uid==0}">
	<ul class="popupmenu_popup newspecialmenu" id="newspecial_menu" style="display: none">
		<c:if test="${thread.allowspecialonly<=0}"><li><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}"><bean:message key="post_new"/></a></li></c:if>
		<c:if test="${allowpostpoll||jsprun_uid==0}"><li class="poll"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}&amp;special=1"><bean:message key="post_newthreadpoll"/></a></li></c:if>
		<c:if test="${allowposttrade||jsprun_uid==0}"><li class="trade"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}&amp;special=2"><bean:message key="post_newthreadtrade"/></a></li></c:if>
		<c:if test="${allowpostreward||jsprun_uid==0}"><li class="reward"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}&amp;special=3"><bean:message key="post_newthreadreward"/></a></li></c:if>
		<c:if test="${allowpostactivity||jsprun_uid==0}"><li class="activity"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}&amp;special=4"><bean:message key="post_newthreadactivity"/></a></li></c:if>
		<c:if test="${allowpostdebate||jsprun_uid==0}"><li class="debate"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}&amp;special=5"><bean:message key="post_newthreaddebate"/></a></li></c:if>
		<c:if test="${allowpostvideo||jsprun_uid==0}"><li class="video"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}&amp;special=6"><bean:message key="post_newthreadvideo"/></a></li></c:if>
		<c:if test="${threadtypes!=null&&thread.allowspecialonly<=0}">
			<c:forEach items="${threadtypes.types}" var="threadtype"><c:if test="${threadtypes.special[threadtype.key]==1&&threadtypes.show[threadtype.key]==1}"><li class="popupmenu_option"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}&amp;typeid=${threadtype.key}">${threadtype.value}</a></li></c:if></c:forEach>
		</c:if>
	</ul>
</c:if>
<div class="mainbox viewthread specialthread activitythread">
<span class="headactions">
	<c:if test="${jsprun_uid>0}"><a><bean:message key="readview" arg0="${thread.views+1}"/></a>
		<c:if test="${thread.authorid==jsprun_uid || modertar}">
			<c:choose><c:when test="${thread.blog==1}"><a href="misc.jsp?action=blog&amp;tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_blog" onclick="ajaxmenu(event, this.id, 2000, 'changestatus', 0)"><bean:message key="blog_remove"/></a></c:when><c:when test="${usergroups.allowuseblog==1 && thread.allowshare==1 && thread.authorid==jsprun_uid}"><a href="misc.jsp?action=blog&amp;tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_blog" onclick="ajaxmenu(event, this.id, 2000, 'changestatus', 0)"><bean:message key="ABL"/></a></c:when></c:choose>
			<script type="text/javascript">
				function changestatus(obj) {
					if(obj.innerHTML=='<bean:message key="blog_remove"/>'){
							obj.innerHTML = '<bean:message key="ABL"/>';
						}else{
							obj.innerHTML = '<bean:message key="blog_remove"/>';
					}
				}
			</script>
		</c:if>
		<a href="misc.jsp?action=emailfriend&amp;tid=${thread.tid}" id="emailfriend" onclick="ajaxmenu(event, this.id, 9000000, '', 0)" class="nobdr"><bean:message key="thread_email_friend"/></a>
		<a href="my.jsp?item=subscriptions&amp;subadd=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_subscription" onclick="ajaxmenu(event, this.id)"><bean:message key="thread_subscribe"/></a>
		<a href="my.jsp?item=favorites&amp;tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite" onclick="ajaxmenu(event, this.id)" class="notabs"><bean:message key="thread_favorite"/></a>
	</c:if></span><h6><bean:message key="threads_special_activity"/></h6>
	<c:if test="${threadmod!=null||thread.blog==1 && settings.spacestatus==1||thread.readperm>0}">
		<ins>
			<c:if test="${threadmod!=null}"><a href="misc.jsp?action=viewthreadmod&amp;tid=${thread.tid}" title="<bean:message key="thread_mod"/>" target="_blank"><bean:message key="thread_mod_by" arg0="${threadmod.username}"/><jrun:showTime timeInt="${threadmod.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>&nbsp;${threadmod.action}</a></c:if>
			<c:if test="${thread.blog==1 && settings.spacestatus==1}"><a href="space.jsp?action=myblogs&uid=${thread.authorid}" target="_blank"><bean:message key="thread_blog"/></a></c:if>
			<c:if test="${thread.readperm>0}"><bean:message key="readperm_thread"/> ${thread.readperm}</c:if>
		</ins>
	</c:if>
	<table summary="<bean:message key="threads_special_activity"/>" cellspacing="0" cellpadding="0">
		<tr><td class="postcontent"><c:if test="${activityclose=='true'}"><label><bean:message key="activity_close"/></label></c:if><h1>${thread.subject}</h1>
			<div class="postmessage">
					<c:choose><c:when test="${viewthread.usermap.rate>0}"><span class="postratings"><a href="misc.jsp?action=viewratings&amp;tid=${viewthread.usermap.tid}&amp;pid=${viewthread.usermap.pid}" title="<bean:message key="rate"/> ${viewthread.usermap.rate}"> <c:forEach begin="1" end="${viewthread.ratings}" step="1"><img src="${styles.IMGDIR}/agree.gif" border="0" alt=""/></c:forEach></a></span></c:when><c:when test="${viewthread.usermap.rate<0}"><span class="postratings"><a href="misc.jsp?action=viewratings&amp;tid=${viewthread.usermap.tid}&amp;pid=${viewthread.usermap.pid}" title="<bean:message key="rate"/> ${viewthread.usermap.rate}"> <c:forEach begin="1" end="${viewthread.ratings}" step="1"><img src="${styles.IMGDIR}/disagree.gif" border="0" alt=""/></c:forEach></a></span></c:when>
					</c:choose><h2><bean:message key="activity_message"/></h2>${viewthread.usermap.message}
					<div class="box"><h4><bean:message key="activity_info"/></h4>
						<table summary="<bean:message key="activity_info"/>" cellpadding="0" cellspacing="0">
							<tr><th><bean:message key="activiy_sort"/></th><td>${activitslist.class}</td></tr>
							<tr><th><bean:message key="activity_starttime"/></th><td><jrun:showTime timeInt="${activitslist.starttimefrom}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/><c:if test="${activitslist.starttimeto!=0}"><bean:message key="activity_start_between1"/>&nbsp;<jrun:showTime timeInt="${activitslist.starttimeto}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>&nbsp;&nbsp;<bean:message key="activity_start_between2"/></c:if></td>
							</tr>
							<tr><th><bean:message key="activity_space"/></th><td>${activitslist.place}</td></tr>
							<tr><th><bean:message key="activity_payment"/></th><td><bean:message key="activity_about_payment" arg0="${activitslist.cost}"/></td></tr>
							<tr><th><bean:message key="gender"/></th><td><c:choose><c:when test="${activitslist.gender==1}"><bean:message key="a_member_edit_gender_male"/></c:when><c:when test="${activitslist.gender==2}"><bean:message key="a_member_edit_gender_female"/></c:when><c:otherwise><bean:message key="unlimite"/></c:otherwise></c:choose></td>
							</tr><c:if test="${activitslist.expiration!=0}">
							<tr><th><bean:message key="activity_totime"/></th><td><jrun:showTime timeInt="${activitslist.expiration}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
							</tr></c:if>
						</table>
						<c:if test="${!activityclose}"><div class="box"><h4><bean:message key="activity_join"/></h4>
							<c:choose>
								<c:when test="${activiapplist==null}"><c:set value="null" var="app"></c:set></c:when>
								<c:otherwise>
								<c:forEach items="${activiapplist}" var="activeapp">
									<c:if test="${activeapp.uid==jsprun_uid}">
									<c:choose>
										<c:when test="${activeapp.verified==1}"><c:set value="yes" var="app"></c:set></c:when>
										<c:otherwise><c:set value="no" var="app"></c:set></c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
							</c:otherwise>
							</c:choose>
							<form id="activityjoin" name="activity" method="post" action="misc.jsp?action=activityapplies&amp;fid=${thread.fid}&amp;tid=${thread.tid}" onSubmit="return checkform(this)">
								<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
								<c:choose>
									<c:when test="${app!=null && app=='yes'}"><button type="button" class="insmsg"><bean:message key="activity_join_audit"/></button></c:when>
									<c:when test="${app!=null && app=='no'}"><button type="button" class="insmsg"><bean:message key="activity_wait"/></button></c:when>
									<c:otherwise>
										<table summary="<bean:message key="activity_join"/>" cellpadding="0" cellspacing="0">
											<tr>
												<th><bean:message key="activity_paytype"/></th>
												<td><p><label><input class="radio" type="radio" value="0" name="payment" checked="checked" /> <bean:message key="activity_pay_myself"/></label></p><p><label><input class="radio" type="radio" value="1" name="payment" /> <bean:message key="activity_would_payment"/></label><input name="payvalue" size="3"> <bean:message key="rmb_yuan"/></p>
												</td>
											</tr>
											<tr><th><bean:message key="activity_linkman"/></th><td><input type="text" name="contact" style="width:80%" maxlength="200" /></td></tr>
											<tr><th><bean:message key="leaveword"/></th><td><input type="text" name="message" style="width:80%" maxlength="200" /></td></tr><tr><th>&nbsp;</th><td><button class="submit" type="submit" name="activitysubmit" value="true"> <bean:message key="activity_my_join"/></button></td></tr>
										</table>
									</c:otherwise>
								</c:choose>
							</form>
						</div>
						</c:if>
						<c:if test="${activiapplist!=null}">
							<div class="box">
								<h4><bean:message key="activity_new_join"/></h4>
								<div class="avatarlist">
									<c:forEach items="${activiapplist}" var="activiapp">
										<dl><dt><a target="_blank" href="space.jsp?uid=${activiapp.uid}"><img onload="thumbImg(this)" style="padding: 3px" width="45" height="45" src="${activiapp.avatar}" border="0" alt="" /></a></dt><dd><a target="_blank" href="space.jsp?uid=${activiapp.uid}">${activiapp.username}</a></dd></dl>
									</c:forEach>
								</div>
							</div>
						</c:if>
						<c:if test="${settings.tagstatus==1&&viewthread.usermap.first==1 && taglist!=null}">
							<p class="posttags"><bean:message key="thread_keywords"/>	<c:forEach items="${taglist}" var="tags"><a href="tag.jsp?name=<jrun:encoding value="${tags.tagname}"/>" target="_blank">${tags.tagname}</a> </c:forEach></p>
						</c:if>
					</div>
				</div>
				<p class="postactions">
				<c:if test="${(usergroups.alloweditpost>0 && modertar) || thread.authorid==jsprun_uid}"><a href="post.jsp?action=edit&amp;fid=${viewthread.usermap.fid}&amp;tid=${viewthread.usermap.tid}&amp;pid=${viewthread.usermap.pid}&amp;page=1&amp;extra=${extra}"><bean:message key="edit"/></a></c:if>
				<c:if test="${usergroups.raterange!='' && viewthread.usermap!=null}">
				<a href="misc.jsp?action=rate&amp;tid=${viewthread.usermap.tid}&amp;pid=${viewthread.usermap.pid}&amp;page=1" id="ajax_rate_${viewthread.usermap.pid}" onclick="ajaxmenu(event, this.id, 9000000, null, 0)"><bean:message key="rate"/></a>
				</c:if>
				<c:if test="${jsprun_uid!=null && settings.magicstatus==1 && usergroups.allowmagics>0}"><a href="magic.jsp?action=user&amp;pid=${viewthread.usermap.pid}" target="_blank"><bean:message key="magics_use"/></a></c:if></p>
			</td>
			<td class="postauthor">
				<c:if test="${viewthread.avatars!=''}"><div class="avatar"><img class="avatar" src="${viewthread.avatars}" alt="" <c:if test="${viewthread.usermap.avatarwidth!='0'&& viewthread.usermap.avatarwidth!=''}">width="${viewthread.usermap.avatarwidth}" height="${viewthread.usermap.avatarheight}"</c:if>><c:if test="${viewthread.usermap.groupavatar!=''}"><br /><img src="${viewthread.usermap.groupavatar}" border="0" alt="" /></c:if></div>
				</c:if>
				<dl><dt><bean:message key="special_author"/></dt>
					<dd><a href="space.jsp?username=<jrun:encoding value="${thread.author}"/>" target="_blank">${thread.author}</a></dd>
					<dt><font color="${viewthread.color}">${viewthread.honor}</font></dt><dd>&nbsp;</dd>
					<dt><jrun:showstars num="${viewthread.stars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/></dt><dd>&nbsp;</dd>
					<dt><bean:message key="activity_already"/></dt><dd>${resoncount} <bean:message key="index_users"/></dd>
					<c:if test="${activitslist.number!=0}"><dt><bean:message key="activity_about_member"/></dt><dd>${activitslist.number-resoncount} <bean:message key="index_users"/></dd></c:if>
				</dl>
				<c:if test="${viewthread.usermap.qq!='' || viewthread.usermap.icq!='' || viewthread.usermap.yahoo!='' || viewthread.usermap.taobao!=''}">
					<p class="imicons">
						<c:if test="${viewthread.usermap.qq!=''}"><a href="http://wpa.qq.com/msgrd?V=1&amp;Uin=${viewthread.usermap.qq}&amp;Site=${settings.bbname}&amp;Menu=yes" target="_blank"><img src="${styles.IMGDIR}/qq.gif" alt="QQ" /></a></c:if>
						<c:if test="${viewthread.usermap.icq!=''}"><a href="http://wwp.icq.com/scripts/search.dll?to=${viewthread.usermap.icq}" target="_blank"><img src="${styles.IMGDIR}/icq.gif" alt="ICQ" /></a></c:if>
						<c:if test="${viewthread.usermap.yahoo!=''}"><a href="http://edit.yahoo.com/config/send_webmesg?.target=${viewthread.usermap.yahoo}&amp;.src=pg" target="_blank"><img src="${styles.IMGDIR}/yahoo.gif" alt="Yahoo!" /></a></c:if>
						<c:if test="${viewthread.usermap.taobao!=''}"><script type="text/javascript">document.write('<a target="_blank" href="http://amos1.taobao.com/msg.ww?v=2&amp;uid='+encodeURIComponent('${viewthread.usermap.taobao}')+'&s=2"><img src="${styles.IMGDIR}/taobao.gif" alt="<bean:message key="taobao"/>" /></a>');</script></c:if>
					</p>
				</c:if><c:forEach items="${viewthread.custominfo.special}" var="cus">${cus}</c:forEach>
			</td>
		</tr>
	</table>
</div>
<div id="ad_interthread"></div><div id="ajaxspecialpost"></div>
<script type="text/javascript">ajaxget('viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewspecialpost&page=${param.page}&rand='+Math.random(), 'ajaxspecialpost');</script>
<jsp:include flush="true" page="viewthread_fastreply.jsp" />
<c:if test="${settings.forumjump==1&&settings.jsmenu_1>0}"><div class="popupmenu_popup" id="forumlist_menu" style="display: none">${forummenu}</div></c:if>
<jsp:include flush="true" page="footer.jsp" />
