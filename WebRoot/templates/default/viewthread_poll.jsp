<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		</div>
	</c:if>
</div>
<div id="ad_text"></div>

<c:if test="${pmlists!=null}"><div class="maintable" id="pmprompt"><jsp:include flush="true" page="pmprompt.jsp" /></div></c:if>
<script type="text/javascript">
	var max_obj = ${polls.maxchoices==1?10: polls.maxchoices};
	var p = 0;
	function checkbox(obj) {
		if(obj.checked) {
			p++;
			for (var i = 0; i < $('poll').elements.length; i++) {
				var e = $('poll').elements[i];
				if(p == max_obj) {
					if(e.name.match('pollanswers') && !e.checked) {
						e.disabled = true;
					}
				}
			}
		} else {
			p--;
			for (var i = 0; i < $('poll').elements.length; i++) {
				var e = $('poll').elements[i];
				if(e.name.match('pollanswers') && e.disabled) {
					e.disabled = false;
				}
			}
		}
	}
</script>
<div class="pages_btns">
	<div class="threadflow"><a href="redirect.jsp?fid=${thread.fid}&amp;tid=${thread.tid}&amp;goto=nextoldset"> &lsaquo;&lsaquo; <bean:message key="last_thread"/></a> | <a href="redirect.jsp?fid=${thread.fid}&amp;tid=${thread.tid}&amp;goto=nextnewset"><bean:message key="next_thread"/> &rsaquo;&rsaquo;</a></div>
	<span class="pageback" <c:if test="${requestScope.visitedforums!=null}"> id="visitedforums" onmouseover="$('visitedforums').id = 'visitedforumstmp';this.id = 'visitedforums';showMenu(this.id)" </c:if>><a href="${backtrack}" title="<bean:message key="return_forumdisplay"/>"><bean:message key="return_forumdisplay"/></a></span>
	<c:if test="${allowpostreply}">
		<span class="replybtn"><a href="post.jsp?action=reply&amp;fid=${thread.fid}&amp;tid=${thread.tid}&amp;extra=${extra}&page=${pagesize}"><img src="${styles.IMGDIR}/reply_${sessionScope['org.apache.struts.action.LOCALE']}.gif" border="0" alt=""/></a></span>
	</c:if>
	<c:if test="${allowpost||jsprun_uid==0}">
		<span class="postbtn" id="newspecial" onmouseover="$('newspecial').id = 'newspecialtmp';this.id = 'newspecial';showMenu(this.id)"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}" title="<bean:message key="post_new"/>"><img src="${styles.IMGDIR}/newtopic_${sessionScope['org.apache.struts.action.LOCALE']}.gif" alt="<bean:message key="post_new"/>"/></a></span>
	</c:if>
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
			<c:forEach items="${threadtypes.types}" var="threadtype">
				<c:if test="${threadtypes.special[threadtype.key]==1&&threadtypes.show[threadtype.key]==1}"><li class="popupmenu_option"><a href="post.jsp?action=newthread&amp;fid=${thread.fid}&page=${currentPage}&amp;typeid=${threadtype.key}">${threadtype.value}</a></li></c:if>
			</c:forEach>
		</c:if>
	</ul>
</c:if>
<div class="mainbox viewthread specialthread pollthread">
	<span class="headactions"> <c:if test="${jsprun_uid!=0}">
		<a><bean:message key="readview" arg0="${thread.views+1}"/></a>
			<c:if test="${thread.authorid==jsprun_uid || modertar}">
					<c:choose>
						<c:when test="${thread.blog==1}"><a href="misc.jsp?action=blog&amp;tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_blog" onclick="ajaxmenu(event, this.id, 2000, 'changestatus', 0)"><bean:message key="blog_remove"/></a></c:when>
						<c:when test="${usergroups.allowuseblog==1 && thread.allowshare==1 && thread.authorid==jsprun_uid}"><a href="misc.jsp?action=blog&amp;tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_blog" onclick="ajaxmenu(event, this.id, 2000, 'changestatus', 0)"><bean:message key="ABL"/></a></c:when>
					</c:choose>
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
			</c:if></span>
			<h6> <bean:message key="threads_special_poll"/> </h6>
			<ins>
				<c:if test="${threadmod!=null}"><a href="misc.jsp?action=viewthreadmod&amp;tid=${thread.tid}" title="<bean:message key="thread_mod"/>" target="_blank"><bean:message key="thread_mod_by" arg0="${threadmod.username}"/><jrun:showTime timeInt="${threadmod.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>&nbsp;${threadmod.action}</a></c:if>
				<c:if test="${thread.blog==1 && settings.spacestatus==1}"><a href="space.jsp?action=myblogs&uid=${thread.authorid}" target="_blank"><bean:message key="thread_blog"/></a></c:if>
				<c:if test="${thread.readperm>0}"><bean:message key="readperm_thread"/> ${thread.readperm}</c:if>
			</ins>
	<table summary="" cellspacing="0" cellpadding="0">
		<tr>
			<td class="postcontent">
				<c:if test="${sparetime!=''}"><label>${sparetime}</label></c:if>
				<h1>${thread.subject}</h1>
				<div class="postmessage">
				<c:choose>
					<c:when test="${viewthread.usermap.rate>0}">
						<span class="postratings"><a href="misc.jsp?action=viewratings&amp;tid=${viewthread.usermap.tid}&amp;pid=${viewthread.usermap.pid}" title="<bean:message key="rate"/> ${viewthread.usermap.rate}"> <c:forEach begin="1" end="${viewthread.ratings}" step="1"><img src="${styles.IMGDIR}/agree.gif" border="0" alt=""/></c:forEach></a></span>
					</c:when>
					<c:when test="${viewthread.usermap.rate<0}">
						<span class="postratings"><a href="misc.jsp?action=viewratings&amp;tid=${viewthread.usermap.tid}&amp;pid=${viewthread.usermap.pid}" title="<bean:message key="rate"/> ${viewthread.usermap.rate}"> <c:forEach begin="1" end="${viewthread.ratings}" step="1"><img src="${styles.IMGDIR}/disagree.gif" border="0" alt=""/></c:forEach></a></span>
					</c:when>
				</c:choose>
				${viewthread.usermap.message}<br/>
				<form id="poll" name="poll" method="post" action="misc.jsp?action=votepoll&amp;fid=${viewthread.usermap.fid}&amp;tid=${viewthread.usermap.tid}">
					<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
						<div class="box pollpanel">
							<h4>
								<span><bean:message key="poll_yourchoice"/> <c:choose><c:when test="${polls.maxchoices==1}">( <bean:message key="poll_single"/> )</c:when><c:otherwise>(<bean:message key="poll_multiple"/>: <bean:message key="poll_more_than" arg0="${polls.maxchoices}"/>)</c:otherwise></c:choose></span>
								<a href="misc.jsp?action=viewvote&amp;tid=${thread.tid}&amp;sid=nu9yV7&amp;page=1" onclick="ajaxget(this.href, 'viewallvoterdiv', 'viewallvoterdiv');doane(event);"><bean:message key="poll_voterscount"/>${pollMap.num}</a> &nbsp;
							</h4>
							<div id="viewallvoterdiv" style="display: none">&nbsp;</div>
							<table summary="poll panel" cellspacing="0" cellpadding="0" style="clear: both;">
								<c:forEach items="${options}" var="option" varStatus="in">
									<tr>
										<c:if test="${pollMap[voterids]==null && (polls.expiration>=nowtime || polls.expiration==0)}">
											<c:if test="${usergroups.allowvote==1}">
												<td class="selector">
													<c:choose>
														<c:when test="${polls.maxchoices==1}"><input class="radio" type="radio" name="pollanswers[]" value="${option.polloptionid}" onclick='checkbox(this)' /></c:when>
														<c:otherwise><input class="checkbox" type="checkbox" name="pollanswers[]" value="${option.polloptionid}" onclick='checkbox(this)' /></c:otherwise>
													</c:choose>
												</td>
											</c:if>
										</c:if>
										<td style="width: 20%;">${in.count}. &nbsp;${option.polloption}</td>
										<td>
											<c:choose>
												<c:when test="${polls.visible==0}">
													<div class="optionbar"><div style="width: ${option.votes/pollMap.ballot*305}px;">&nbsp;</div></div>
													<p>${option.votes}<fmt:formatNumber value="${(option.votes/pollMap.ballot)*100}" var="op" maxFractionDigits="2" />(${op}%)</p>
												</c:when>
												<c:otherwise>
												<c:if test="${jsprun_groupid==1 || modertar || jsprun_uid==thread.authorid || pollMap[voterids]!=null}">
													<div class="optionbar"><div style="width: ${option.votes/pollMap.ballot*305}px;"> &nbsp;</div></div>
													<p>${option.votes}<fmt:formatNumber value="${(option.votes/pollMap.ballot)*100}" var="op" maxFractionDigits="2" />(${op}%)</p>
												</c:if>
												</c:otherwise>
											</c:choose>
											<c:if test="${jsprun_adminid==1 && option.votes>0}"><div id="viewvoterframe${option.polloptionid}" style="display:none">&nbsp;</div></c:if>
											</td>
											<td style="width: 120px;">
												<c:if test="${jsprun_adminid==1 && option.votes>0}">
													( <a href="misc.jsp?action=viewvote&tid=${thread.tid}&polloptionid=${option.polloptionid}" onclick="ajaxget(this.href, 'viewvoterframe${option.polloptionid}', 'viewvoterframe${option.polloptionid}', null, 'auto');doane(event);"><bean:message key="poll_show_voter"/> </a> )
												</c:if>
										</td>
									</tr>
								</c:forEach>
								<c:if test="${pollMap[voterids]==null && (polls.expiration>=nowtime || polls.expiration==0)}">
									<c:if test="${usergroups.allowvote==1}"><td colspan="3"><button class="submit" type="submit" name="pollsubmit" id="pollsubmit" value="true"><bean:message key="submitf"/></button></td></c:if></c:if>
							</table>
						</div>
					</form>
						<c:if test="${viewthread.usermap.attachment>0}">
							<c:choose>
								<c:when test="${!allowgetattach}"><div class="notice" style="width: 500px"><bean:message key="attachment"/> : <em><bean:message key="attach_nopermission"/></em></div></c:when>
								<c:otherwise>
									<div class="box postattachlist">
										<h4> <bean:message key="attachment"/> </h4>
										<c:forEach items="${viewthread.attaurl}" var="atta">
											<dl class="t_attachlist">
												<dt><img src="images/attachicons/${attatype[atta.filetype]}" border="0" class="absmiddle" alt="" /> <a href="attachment.jsp?aid=${atta.aid}">${atta.filename}</a> <em>(<jrun:showFileSize size="${atta.filesize}" />)</em></dt>
												<dd>
													<p><jrun:showTime timeInt="${atta.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/><c:if test="${settings.attachrefcheck==0 || atta.attachmentvalue==''}">,<bean:message key="attach_downloads"/>: ${atta.downloads}</c:if><c:if test="${atta.readperm>0}">,<bean:message key="threads_readperm"/>: ${atta.readperm}</c:if><c:if test="${atta.price>0}">,<bean:message key="magics_price"/>: ${atta.price}&nbsp;[<a href="misc.jsp?action=viewattachpayments&aid=${atta.aid}" target="_blank"><bean:message key="pay_view"/></a>]&nbsp;<c:if test="${atta.isprice==5}">[<a href="misc.jsp?action=attachpay&aid=${atta.aid}" target="_blank"><bean:message key="attachment_buy"/></a>]</c:if></c:if><c:if test="${atta.description!=''}"><br>${atta.description}</c:if></p>
													<c:if test="${showimag && settings.attachimgpost==1 && atta.attachmentvalue!='' && (readaccess >= atta.readperm || viewthread.usermap.authorid==jsprun_uid)}">
													<c:set scope="page" var="isatta" value="yes"></c:set>
													<c:choose>
														<c:when test="${settings.attachrefcheck==0}">
														<p>
														<c:choose>
															<c:when test="${atta.thumb==1}"><a href="#zoom"><img onclick="zoom(this, '${atta.attachment}')" src="${atta.attachmentvalue}" alt="${atta.filename}" /></a></c:when>
															<c:otherwise><img src="${atta.attachmentvalue}" border="0" onload="attachimg(this, 'load')" onmouseover="attachimg(this, 'mouseover')" onclick="zoom(this, '${atta.attachment}')" alt="${atta.filename}" /></c:otherwise>
														</c:choose>
														</p>
														</c:when>
														<c:otherwise>
														<c:choose>
														<c:when test="${atta.thumb==1}"><p><a href="#zoom"><img onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" src="attachment.jsp?aid=${atta.aid}&noupdate=yes" alt="${atta.filename}" /></a></p></c:when>
														<c:otherwise><p><img src="attachment.jsp?aid=${atta.aid}&noupdate=yes" border="0" onload="attachimg(this, 'load')" onmouseover="attachimg(this, 'mouseover')" onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" alt="${atta.filename}" /></p></c:otherwise>
														</c:choose>
														</c:otherwise>
													</c:choose>
													</c:if>
												</dd>
											</dl>
										</c:forEach>
									</div>
								</c:otherwise>
							</c:choose>
							</c:if>
							<c:if test="${settings.tagstatus==1&&viewthread.usermap.first==1 && taglist!=null}">
							<p class="posttags"><bean:message key="thread_keywords"/><c:forEach items="${taglist}" var="tags"> <a href="tag.jsp?name=<jrun:encoding value="${tags.tagname}"/>" target="_blank">${tags.tagname}</a>  </c:forEach> </p>
						</c:if>
				</div>
			</td>
			<td class="postauthor">
				<c:if test="${viewthread.avatars!=''}">
					<div class="avatar">
						<img class="avatar" src="${viewthread.avatars}" alt="" <c:if test="${viewthread.usermap.avatarwidth!='0'&& viewthread.usermap.avatarwidth!=''}">width="${viewthread.usermap.avatarwidth}" height="${viewthread.usermap.avatarheight}"</c:if>><c:if test="${viewthread.usermap.groupavatar!=''}"><br /><img src="${viewthread.usermap.groupavatar}" border="0" alt="" /></c:if>
					</div>
				</c:if>
				<dl>
					<dt><bean:message key="special_author"/></dt>
					<dd><a href="space.jsp?username=<jrun:encoding value="${thread.author}"/>" target="_blank">${thread.author}</a></dd>
					<dt><font color="${viewthread.color}">${viewthread.honor}</font></dt>
					<dd>&nbsp;</dd>
					<dt><jrun:showstars num="${viewthread.stars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/></dt>
					<dd>&nbsp;</dd>
					<dt><bean:message key="activity_starttime"/></dt>
					<dd><jrun:showTime timeInt="${thread.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></dd>
				</dl>
				<c:if test="${viewthread.usermap.qq!='' || viewthread.usermap.icq!='' || viewthread.usermap.yahoo!='' || viewthread.usermap.taobao!=''}">
					<p class="imicons">
						<c:if test="${viewthread.usermap.qq!=''}"><a href="http://wpa.qq.com/msgrd?V=1&amp;Uin=${viewthread.usermap.qq}&amp;Site=${settings.bbname}&amp;Menu=yes" target="_blank"><img src="${styles.IMGDIR}/qq.gif" alt="QQ" /></a></c:if>
						<c:if test="${viewthread.usermap.icq!=''}"><a href="http://wwp.icq.com/scripts/search.dll?to=${viewthread.usermap.icq}" target="_blank"><img src="${styles.IMGDIR}/icq.gif" alt="ICQ" /></a></c:if>
						<c:if test="${viewthread.usermap.yahoo!=''}"><a href="http://edit.yahoo.com/config/send_webmesg?.target=${viewthread.usermap.yahoo}&amp;.src=pg" target="_blank"><img src="${styles.IMGDIR}/yahoo.gif" alt="Yahoo!" /> </a></c:if>
						<c:if test="${viewthread.usermap.taobao!=''}"><script type="text/javascript">document.write('<a target="_blank" href="http://amos1.taobao.com/msg.ww?v=2&amp;uid='+encodeURIComponent('${viewthread.usermap.taobao}')+'&amp;s=2"><img src="${styles.IMGDIR}/taobao.gif" alt="<bean:message key="taobao"/>" /></a>');</script></c:if>
					</p>
				</c:if>
				<c:forEach items="${viewthread.custominfo.special}" var="cus">${cus}</c:forEach>
			</td>
		</tr>
		<tr>
			<td class="postcontent">
				<c:if test="${(usergroups.alloweditpoll>0 && modertar) || thread.authorid==jsprun_uid}"><a href="post.jsp?action=edit&amp;fid=${viewthread.usermap.fid}&amp;tid=${viewthread.usermap.tid}&amp;pid=${viewthread.usermap.pid}&amp;page=1&amp;extra=${extra}"><bean:message key="edit"/></a></c:if>
				<c:if test="${usergroups.raterange!='' && viewthread.usermap!=null}"><a href="misc.jsp?action=rate&amp;tid=${viewthread.usermap.tid}&amp;pid=${viewthread.usermap.pid}&amp;page=1" id="ajax_rate_${viewthread.usermap.pid}" onclick="ajaxmenu(event, this.id, 9000000, null, 0)"><bean:message key="rate"/></a></c:if>
				<c:if test="${jsprun_uid!=null && settings.magicstatus==1 && usergroups.allowmagics>0}"> <a href="magic.jsp?action=user&amp;pid=${viewthread.usermap.pid}" target="_blank"><bean:message key="magics_use"/></a> </c:if>
			</td>
			<td class="postauthor">&nbsp;</td>
		</tr>
	</table>
</div>
<div id="ad_interthread"></div>
<div id="ajaxspecialpost"></div>
<script type="text/javascript">ajaxget('viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewspecialpost&page=${param.page}&rand='+Math.random(), 'ajaxspecialpost');</script>
<jsp:include flush="true" page="viewthread_fastreply.jsp" />
<c:if test="${settings.forumjump==1&&settings.jsmenu_1>0}"><div class="popupmenu_popup" id="forumlist_menu" style="display: none">${forummenu}</div></c:if>
<jsp:include flush="true" page="footer.jsp" />