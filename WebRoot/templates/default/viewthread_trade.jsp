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
		</div>
	</c:if>
</div>

<c:if test="${pmlists!=null}"><div class="maintable" id="pmprompt"><jsp:include flush="true" page="pmprompt.jsp" /></div></c:if>
<div class="pages_btns">
	<div class="threadflow"><a href="redirect.jsp?fid=${thread.fid}&tid=${thread.tid}&goto=nextoldset"> &lsaquo;&lsaquo; <bean:message key="last_thread"/></a> | <a href="redirect.jsp?fid=${thread.fid}&tid=${thread.tid}&goto=nextnewset"><bean:message key="next_thread"/> &rsaquo;&rsaquo;</a></div>
	<span class="pageback" <c:if test="${requestScope.visitedforums!=null}"> id="visitedforums" onmouseover="$('visitedforums').id = 'visitedforumstmp';this.id = 'visitedforums';showMenu(this.id)" </c:if>><a href="${backtrack}" title="<bean:message key="return_forumdisplay"/>"><bean:message key="return_forumdisplay"/></a></span>
	<c:if test="${allowpostreply}"><span class="replybtn"><a href="post.jsp?action=reply&fid=${thread.fid}&tid=${thread.tid}&extra=${extra}"><img src="${styles.IMGDIR}/reply_${sessionScope['org.apache.struts.action.LOCALE']}.gif" border="0" alt="" /></a></span></c:if>
	<c:if test="${allowpost||jsprun_uid==0}"><span class="postbtn" id="newspecial" onmouseover="$('newspecial').id = 'newspecialtmp';this.id = 'newspecial';showMenu(this.id)"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}" title="<bean:message key="post_new"/>"><img src="${styles.IMGDIR}/newtopic_${sessionScope['org.apache.struts.action.LOCALE']}.gif" alt="<bean:message key="post_new"/>" /></a></span></c:if>
</div>
<c:if test="${allowposttrade||allowpostpoll||allowpostreward||allowpostactivity||allowpostdebate||allowpostvideo||forumfield.threadtypes!=''||jsprun_uid==0}">
	<ul class="popupmenu_popup newspecialmenu" id="newspecial_menu" style="display: none">
		<c:if test="${thread.allowspecialonly<=0}"><li><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}"><bean:message key="post_new"/></a></li></c:if>
		<c:if test="${allowpostpoll||jsprun_uid==0}"><li class="poll"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}&special=1"><bean:message key="post_newthreadpoll"/></a></li></c:if>
		<c:if test="${allowposttrade||jsprun_uid==0}"><li class="trade"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}&special=2"><bean:message key="post_newthreadtrade"/></a></li></c:if>
		<c:if test="${allowpostreward||jsprun_uid==0}"><li class="reward"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}&special=3"><bean:message key="post_newthreadreward"/></a></li></c:if>
		<c:if test="${allowpostactivity||jsprun_uid==0}"><li class="activity"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}&special=4"><bean:message key="post_newthreadactivity"/></a></li></c:if>
		<c:if test="${allowpostdebate||jsprun_uid==0}"><li class="debate"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}&special=5"><bean:message key="post_newthreaddebate"/></a></li></c:if>
		<c:if test="${allowpostvideo||jsprun_uid==0}"><li class="video"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}&special=6"><bean:message key="post_newthreadvideo"/></a></li></c:if>
		<c:if test="${threadtypes!=null&&thread.allowspecialonly<=0}">
			<c:forEach items="${threadtypes.types}" var="threadtype">
				<c:if test="${threadtypes.special[threadtype.key]==1&&threadtypes.show[threadtype.key]==1}"><li class="popupmenu_option"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}&typeid=${threadtype.key}">${threadtype.value}</a></li></c:if>
			</c:forEach>
		</c:if>
	</ul>
</c:if>
<div class="mainbox viewthread specialthread tradethread">
	<span class="headactions">
		<c:if test="${jsprun_uid!=0}">
			<a><bean:message key="readview" arg0="${thread.views+1}"/></a>
			<c:if test="${thread.authorid==jsprun_uid || modertar}">
				<c:choose>
					<c:when test="${thread.blog==1}"><a href="misc.jsp?action=blog&tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_blog" onclick="ajaxmenu(event, this.id, 2000, 'changestatus', 0)"><bean:message key="blog_remove"/></a></c:when>
					<c:when test="${usergroups.allowuseblog==1 && thread.allowshare==1 && thread.authorid==jsprun_uid}"><a href="misc.jsp?action=blog&tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_blog" onclick="ajaxmenu(event, this.id, 2000, 'changestatus', 0)"><bean:message key="ABL"/></a></c:when>
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
			<a href="misc.jsp?action=emailfriend&tid=${thread.tid}" id="emailfriend" onclick="ajaxmenu(event, this.id, 9000000, '', 0)" class="nobdr"><bean:message key="thread_email_friend"/></a>
			<a href="my.jsp?item=subscriptions&subadd=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_subscription" onclick="ajaxmenu(event, this.id)"><bean:message key="thread_subscribe"/></a>
			<a href="my.jsp?item=favorites&tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite" onclick="ajaxmenu(event, this.id)" class="notabs"><bean:message key="thread_favorite"/></a>
		</c:if>
	</span>
	<h6><bean:message key="threads_special_trade"/></h6>
	<ins>
		<c:if test="${threadmod!=null}"><a href="misc.jsp?action=viewthreadmod&tid=${thread.tid}" title="<bean:message key="thread_mod"/>" target="_blank"><bean:message key="thread_mod_by" arg0="${threadmod.username}"/><jrun:showTime timeInt="${threadmod.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>&nbsp;${threadmod.action}</a></c:if>
		<c:if test="${thread.blog==1 && settings.spacestatus==1}"><a href="space.jsp?action=myblogs&uid=${thread.authorid}" target="_blank"><bean:message key="thread_blog"/></a></c:if>
		<c:if test="${thread.readperm>0}"><bean:message key="readperm_thread"/> ${thread.readperm}</c:if>
	</ins>
	<table summary="" cellpadding="0" cellspacing="0">
		<tr>
			<td class="postcontent">
				<label><bean:message key="post_trade_totalnumber"/>: <strong>${tradenum}</strong></label><h1>${thread.subject}</h1>
				<div class="postmessage">
					<c:choose>
						<c:when test="${viewthread.usermap.rate>0}"><span class="postratings"><a href="misc.jsp?action=viewratings&tid=${viewthread.usermap.tid}&pid=${viewthread.usermap.pid}" title="<bean:message key="rate"/> ${viewthread.usermap.rate}"> <c:forEach begin="1" end="${viewthread.ratings}" step="1"><img src="${styles.IMGDIR}/agree.gif" border="0" alt=""/></c:forEach></a></span></c:when>
						<c:when test="${viewthread.usermap.rate<0}"><span class="postratings"><a href="misc.jsp?action=viewratings&tid=${viewthread.usermap.tid}&pid=${viewthread.usermap.pid}" title="<bean:message key="rate"/> ${viewthread.usermap.rate}"> <c:forEach begin="1" end="${viewthread.ratings}" step="1"><img src="${styles.IMGDIR}/disagree.gif" border="0" alt=""/></c:forEach></a></span></c:when>
					</c:choose>
					${viewthread.usermap.message}<br />
					<c:choose>
						<c:when test="${tradenum>0}">
							<c:if test="${trades!=null}">
							<div class="box tradethumblist">
								<h4><bean:message key="space_trade_stick"/></h4>
								<c:forEach items="${trades}" var="trade">
									<c:if test="${trade.displayorder>0}">
										<dl>
											<dt></dt>
											<dd class="thumblist" onclick="window.open('viewthread.jsp?do=tradeinfo&tid=${thread.tid}&pid=${trade.pid}','','')">
												<c:choose>
													<c:when test="${attachmap[trade.pid]!=null}"><img src="${attachmap[trade.pid]}" onload="thumbImg(this)" width="96" height="96" alt="${trade.subject}"></c:when>
													<c:otherwise><img src="${styles.IMGDIR}/trade_nophoto.gif" onload="thumbImg(this)" width="96" height="96" alt="${trade.subject}"></c:otherwise>
												</c:choose>
											</dd>
											<dd>
												<p class="tradename"><a href="viewthread.jsp?do=tradeinfo&tid=${thread.tid}&pid=${trade.pid}" target="_blank">${trade.subject}</a></p>
												<c:if test="${trade.costprice>0.0}"><p><bean:message key="trade_costprice"/> <del>${trade.costprice}</del> <bean:message key="rmb_yuan"/></p></c:if>
												<p><bean:message key="trade_price"/> <strong>${trade.price}</strong> <bean:message key="rmb_yuan"/></p>
												<p>
												<c:choose>
													<c:when test="${trade.closed>0}"><em><bean:message key="trade_timeout"/></em></c:when>
													<c:when test="${trade.expiration>0}">${trade.expiration}<bean:message key="ipban_days"/>${trade.expirationhour}<bean:message key="hr"/></c:when>
													<c:when test="${trade.expiration==-1}"><em><bean:message key="trade_timeout"/></em></c:when>
												</c:choose>
												</p>
											</dd>
										</dl>
									</c:if>
								</c:forEach>
							</div>
							</c:if>
							<c:if test="${tradelist>0}">
								<div id="ajaxtradelist"></div>
								<script type="text/javascript">ajaxget('viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewtradelist&page=1&rand='+Math.random(), 'ajaxtradelist');</script>
							</c:if>
							<c:if test="${jsprun_uid==thread.authorid}">
								<button type="button" onclick="window.open('my.jsp?item=selltrades&tid=${thread.tid}','','')"><bean:message key="trade_show_log"/></button>&nbsp;&nbsp;
								<button type="button" onclick="location.href='misc.jsp?action=tradeorder&tid=${thread.tid}'"><bean:message key="trade_displayorder"/></button>&nbsp;&nbsp;
								<c:if test="${usergroups.allowposttrade==1}"><button type="button" onclick="location.href='post.jsp?action=reply&fid=${thread.fid}&tid=${thread.tid}&addtrade=yes&extra=${extra}'"><bean:message key="trade_add_post"/></button></c:if>
							</c:if>
							<c:if test="${settings.tagstatus==1&&viewthread.usermap.first==1 && taglist!=null}">
								<p class="posttags"><bean:message key="thread_keywords"/> <c:forEach items="${taglist}" var="tags"><a href="tag.jsp?name=<jrun:encoding value="${tags.tagname}"/>" target="_blank">${tags.tagname}</a></c:forEach> </p>
							</c:if>
						</c:when>
						<c:otherwise>
							<div class="notice"><bean:message key="trade_nogoods"/></div>
							<c:if test="${jsprun_uid==thread.authorid && usergroups.allowposttrade==1}"><br /><button type="button" onclick="location.href='post.jsp?action=reply&fid=${thread.fid}&tid=${thread.tid}&addtrade=yes&extra=${extra}'"><bean:message key="trade_add_post"/></button></c:if>
						</c:otherwise>
					</c:choose>
				</div>
			</td>
			<td class="postauthor">
				<c:if test="${viewthread.avatars!=''}"><div class="avatar"><img class="avatar" src="${viewthread.avatars}" alt="" <c:if test="${viewthread.usermap.avatarwidth!='0'&& viewthread.usermap.avatarwidth!=''}">width="${viewthread.usermap.avatarwidth}" height="${viewthread.usermap.avatarheight}"</c:if>><c:if test="${!empty viewthread.usermap.groupavatar}"><br /><img src="${viewthread.usermap.groupavatar}" border="0" alt="" /></c:if></div></c:if>
				<dl>
					<dt><bean:message key="tradelog_seller"/></dt><dd><a href="space.jsp?username=<jrun:encoding value="${viewthread.usermap.username}"/>" target="_blank">${viewthread.usermap.username}</a></dd>
					<dt>${viewthread.honor}</dt><dd>&nbsp;</dd>
					<dt><jrun:showstars num="${viewthread.stars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/></dt><dd>&nbsp;</dd>
					<dt><bean:message key="eccredit_sellerinfo"/></dt><dd>${viewthread.usermap.sellercredit}<a href="eccredit.jsp?uid=${viewthread.usermap.uid}" target="_blank"><img src="images/rank/seller/${shellcredit}.gif" border="0" class="absmiddle"></a></dd>
					<dt><bean:message key="eccredit_buyerinfo"/></dt><dd>${viewthread.usermap.buyercredit}<a href="eccredit.jsp?uid=${viewthread.usermap.uid}" target="_blank"><img src="images/rank/buyer/${buycredit}.gif" border="0" class="absmiddle"></a></dd>
					<dt><a href="space.jsp?action=mytrades&uid=${viewthread.usermap.uid}" target="_blank"><bean:message key="trade_view_space"/></a></dt><dd>&nbsp;</dd>
				</dl>
				<c:if test="${viewthread.usermap.qq!='' || viewthread.usermap.icq!='' || viewthread.usermap.yahoo!='' || viewthread.usermap.taobao!=''}">
					<p class="imicons">
						<c:if test="${viewthread.usermap.qq!=''}"><a href="http://wpa.qq.com/msgrd?V=1&Uin=${viewthread.usermap.qq}&Site=Jsprun~&Menu=yes" target="_blank"><img src="${styles.IMGDIR}/qq.gif" alt="QQ" /></a></c:if>
						<c:if test="${viewthread.usermap.icq!=''}"><a href="http://wwp.icq.com/scripts/search.dll?to=${viewthread.usermap.icq}" target="_blank"><img src="${styles.IMGDIR}/icq.gif" alt="ICQ" /></a></c:if>
						<c:if test="${viewthread.usermap.yahoo!=''}"><a href="http://edit.yahoo.com/config/send_webmesg?.target=${viewthread.usermap.yahoo}&.src=pg" target="_blank"><img src="${styles.IMGDIR}/yahoo.gif" alt="Yahoo!" /> </a></c:if>
						<c:if test="${viewthread.usermap.taobao!=''}"><script type="text/javascript">document.write('<a target="_blank" href="http://amos1.taobao.com/msg.ww?v=2&uid='+encodeURIComponent('${viewthread.usermap.taobao}')+'&s=2"><img src="${styles.IMGDIR}/taobao.gif" alt="<bean:message key="taobao"/>" /></a>');</script></c:if>
					</p>
				</c:if>
				<c:forEach items="${viewthread.custominfo.special}" var="cus"> ${cus} </c:forEach>
			</td>
		</tr>
		<tr>
			<td class="postcontent">
				<p class="postactions">
					<c:if test="${(usergroups.alloweditpost>0 && modertar) || thread.authorid==jsprun_uid}"><a href="post.jsp?action=edit&fid=${viewthread.usermap.fid}&tid=${viewthread.usermap.tid}&pid=${viewthread.usermap.pid}&page=1&extra=${extra}"><bean:message key="edit"/></a></c:if>
					<c:if test="${usergroups.raterange!='' && viewthread.usermap!=null}"><a href="misc.jsp?action=rate&tid=${viewthread.usermap.tid}&pid=${viewthread.usermap.pid}&page=1" id="ajax_rate_${viewthread.usermap.pid}" onclick="ajaxmenu(event, this.id, 9000000, null, 0)"><bean:message key="rate"/></a></c:if>
					<c:if test="${jsprun_uid!=null && settings.magicstatus==1 && usergroups.allowmagics>0}"><a href="magic.jsp?action=user&pid=${viewthread.usermap.pid}" target="_blank"><bean:message key="magics_use"/></a></c:if>
				</p>
			</td>
			<td class="postauthor"></td>
		</tr>
	</table>
</div>
<div id="ad_interthread"></div>
<c:if test="${tradenum>0}">
	<div id="ajaxspecialpost"></div>
	<script type="text/javascript">ajaxget('viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewall&page=1&rand='+Math.random(), 'ajaxspecialpost');</script>
</c:if>
<jsp:include flush="true" page="viewthread_fastreply.jsp" />
<c:if test="${settings.forumjump==1&&settings.jsmenu_1>0}"><div class="popupmenu_popup" id="forumlist_menu" style="display: none">${forummenu}</div></c:if>
<jsp:include flush="true" page="footer.jsp" />