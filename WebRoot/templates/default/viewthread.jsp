<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="header.jsp" />
<script src="include/javascript/viewthread.js" type="text/javascript"></script>
<script type="text/javascript">
zoomstatus = parseInt(${settings.zoomstatus});
lang['zoom_image'] = '<bean:message key="zoom_image"/>';
lang['a_system_js_newwindow_blank'] = '<bean:message key="a_system_js_newwindow_blank"/>';
lang['full_size'] = '<bean:message key="full_size"/>';
lang['closed'] = '<bean:message key="closed"/>';
lang['copy_to_cutedition'] = '<bean:message key="copy_to_cutedition"/>';
lang['copy_code'] = '<bean:message key="copy_code"/>';
</script>
<c:if test="${settings.showjavacode>0}">
<link type="text/css" rel="stylesheet" href="include/css/shCore.css"/>
	<link type="text/css" rel="stylesheet" href="include/css/shThemeDefault.css"/>
   <script type="text/javascript">
   	lang['view'] = '<bean:message key="view"/>';
   	lang['thread_printable'] = '<bean:message key="thread_printable"/>';
   </script>
   <script type="text/javascript" src="include/js/shCore.js"></script> 
   <script type="text/javascript" src="include/js/shBrushBash.js"></script> 
   <script type="text/javascript" src="include/js/shBrushJava.js"></script>  
   <script type="text/javascript" src="include/js/shBrushXml.js"></script>  
   <script type="text/javascript" src="include/js/shBrushSql.js"></script>  
   <script type="text/javascript" src="include/js/shBrushCss.js"></script>  
   <script type="text/javascript" src="include/js/shBrushJScript.js"></script>  
   <script type="text/javascript" src="include/js/shBrushCSharp.js"></script>  
   <script type="text/javascript">
		SyntaxHighlighter.config.clipboardSwf = 'include/js/clipboard.swf';
		SyntaxHighlighter.all();
	</script>
</c:if> 
<div id="foruminfo">
	<div id="nav"><a id="forumlist" href="${settings.indexname}" ${settings.forumjump=='1'&&settings.jsmenu_1>0 ? "class=dropmenu onmouseover=showMenu(this.id)":""}>${settings.bbname}</a> ${navigation} &raquo; ${subject}</div>
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
<div id="append_parent"></div><div id="ajaxwaitid"></div>
<div class="pages_btns">
	<div class="threadflow"><a href="redirect.jsp?fid=${thread.fid}&tid=${thread.tid}&goto=nextoldset" title="<bean:message key="last_thread"/>"> &lsaquo;&lsaquo;<bean:message key="last_thread"/> </a> | <a href="redirect.jsp?fid=${thread.fid}&tid=${thread.tid}&goto=nextnewset" title="<bean:message key="next_thread"/>"> <bean:message key="next_thread"/>&rsaquo;&rsaquo;</a></div>
	${multi.multipage}
	<span class="pageback" <c:if test="${requestScope.visitedforums!=null}"> id="visitedforums" onmouseover="$('visitedforums').id = 'visitedforumstmp';this.id = 'visitedforums';showMenu(this.id)" </c:if>><a href="${backtrack}" title="<bean:message key="return_forumdisplay"/>"><bean:message key="return_forumdisplay"/></a></span>
	<c:if test="${allowpostreply}"><span class="replybtn"><a href="post.jsp?action=reply&fid=${thread.fid}&tid=${thread.tid}&extra=${extra}&page=${lpp}"><img src="${styles.IMGDIR}/reply_${sessionScope['org.apache.struts.action.LOCALE']}.gif" border="0" alt="" /> </a> </span></c:if>
	<c:if test="${allowpost||jsprun_uid==0}"><span class="postbtn" id="newspecial" onmouseover="$('newspecial').id = 'newspecialtmp';this.id = 'newspecial';showMenu(this.id)"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}" title="<bean:message key="post_new"/>"><img src="${styles.IMGDIR}/newtopic_${sessionScope['org.apache.struts.action.LOCALE']}.gif" alt="<bean:message key="post_new"/>" /></a></span></c:if>
</div>
<form method="post" name="modactions">
	<input type="hidden" name="formhash" value="b29e1c42" />
	<div class="mainbox viewthread">
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
				<a href="my.jsp?item=favorites&tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite" onclick="ajaxmenu(event, this.id, 3000, 0)"><bean:message key="thread_favorite"/></a>
				<a href="my.jsp?item=subscriptions&subadd=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_subscription" onclick="ajaxmenu(event, this.id, 3000, null, 0)"><bean:message key="thread_subscribe"/></a>
				<a href="misc.jsp?action=emailfriend&tid=${thread.tid}" id="emailfriend" onclick="ajaxmenu(event, this.id, 9000000, null, 0)"><bean:message key="thread_email_friend"/></a>
			</c:if>
			<a href="viewthread.jsp?action=printable&tid=${thread.tid}" target="_blank" class="notabs"><bean:message key="thread_printable"/></a>
		</span>
		<h1>${thread.subject}</h1>
		<ins>
			<c:if test="${thread.price>0}"><a href="misc.jsp?action=viewpayments&tid=${thread.tid}"><bean:message key="price_thread"/> ${creditstrans.title} <strong>${thread.price}</strong> ${creditstrans.unit}</a></c:if>
			<c:if test="${threadmod!=null}"><a href="misc.jsp?action=viewthreadmod&tid=${thread.tid}" title="<bean:message key="thread_mod"/>" target="_blank"><bean:message key="thread_mod_by" arg0="${threadmod.username}"/> <jrun:showTime timeInt="${threadmod.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}" />&nbsp;${threadmod.action}</a></c:if>
			<c:if test="${thread.blog==1 && settings.spacestatus==1}"><a href="space.jsp?action=myblogs&uid=${thread.authorid}" target="_blank"><bean:message key="thread_blog"/></a></c:if>
			<c:if test="${thread.readperm>0}"><bean:message key="readperm_thread"/> ${thread.readperm}</c:if>
		</ins>
		<c:if test="${highlightstatus>0}"><ins><a href="viewthread.jsp?tid=${tid}&amp;page=${currentPage}" style="font-weight: normal"><bean:message key="disable_highlight"/></a></ins></c:if>
		<c:forEach items="${postlist}" var="post" varStatus="in">
			<c:if test="${in.count>1}"><div class="mainbox viewthread"></c:if>
			<table id="pid${post.usermap.pid}" summary="pid${post.usermap.pid}" cellspacing="0" cellpadding="0">
				<tr>
					<td class="postauthor">
						${post.newpostanchor}${post.lastpostanchor}<cite>
						<c:if test="${usergroups.allowviewip==1 && (thread.digest>=0 || post.usermap.first==0)}"><label><a href="topicadmin.jsp?action=getip&fid=${post.usermap.fid}&tid=${post.usermap.tid}&pid=${post.usermap.pid}" id="ajax_getip_${post.usermap.pid}" onclick="ajaxmenu(event, this.id, 10000, null, 0);doane(event);" title="<bean:message key="admin_getip"/>">IP</a></label></c:if> 
						<c:choose>
						<c:when test="${post.usermap.username!=null && post.usermap.anonymous!=1}"><a href="space.jsp?uid=${post.usermap.uid}" target="_blank" id="userinfo${in.count}" class="dropmenu" onmouseover="showMenu(this.id)">${post.usermap.username}</a>
						</cite>
						<c:if test="${post.usermap.nickname!='' && usergroups.allownickname>0}"><p>${post.usermap.nickname}</p></c:if>
						<c:if test="${post.avatars!=null}"><div class="avatar"><img class="avatar" src="${post.avatars}" alt="" <c:if test="${post.usermap.avatarwidth!='0'&& post.usermap.avatarwidth!=''}">width="${post.usermap.avatarwidth}" height="${post.usermap.avatarheight}"</c:if>><c:if test="${!empty post.usermap.groupavatar}"><br /><img src="${post.usermap.groupavatar}" border="0" alt="" /></c:if></div></c:if>
						<p><em><font color="${post.color}">${post.honor}</font></em></p>
						<p><jrun:showstars num="${post.stars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/></p>
						<c:if test="${post.usermap.customstatus!='' && usergroups.allowcstatus>0}"><p class="customstatus">${post.usermap.customstatus}</p></c:if>
						<c:if test="${!empty post.custominfo.left}"><dl class="profile"><c:forEach items="${post.custominfo.left}" var="cus">${cus}</c:forEach></dl></c:if>	
						<c:if test="${!empty post.medalslist}"><p><c:forEach items="${post.medalslist}" var="medals"><img src="images/common/${medals.image}" title="${medals.name}" /></c:forEach></p></c:if>
						<ul>
								<c:if test="${settings.spacestatus==1}"><li class="space"><c:choose><c:when test="${post.usermap.spacename!=''}"><a href="space.jsp?uid=${post.usermap.uid}" target="_blank" title="${post.usermap.spacename}"></c:when><c:otherwise><a href="space.jsp?uid=${post.usermap.uid}" target="_blank" title="${post.usermap.username}<bean:message key="space_userspace"/>"></c:otherwise></c:choose> <bean:message key="150"/></a></li></c:if>
								<li class="pm"><a href="pm.jsp?action=send&uid=${post.usermap.uid}&pid=${post.usermap.pid}" target="_blank" id="ajax_uid_${post.usermap.pid}" onclick="ajaxmenu(event, this.id, 9000000, null, 0)"><bean:message key="send_pm"/></a></li>
								<li class="buddy"><a href="my.jsp?item=buddylist&newbuddyid=${post.usermap.uid}&buddysubmit=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank" id="ajax_buddy_${post.usermap.pid}" onclick="ajaxmenu(event, this.id, null, 0)"><bean:message key="add_to_buddylist"/></a></li>
								<c:if test="${settings.vtonlinestatus!=0 && post.usermap.authorid!=0}">
									<c:choose>
										<c:when test="${(settings.vtonlinestatus==1 && nowtime-post.usermap.lastactivity<=10800 && post.usermap.invisible==0) || (post.onlineauthors==1)}"><li class="online"><bean:message key="online"/></li></c:when>
										<c:otherwise><li class="offline"><bean:message key="offline"/></li></c:otherwise>
									</c:choose>
								</c:if>
						</ul>
						</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${post.usermap.authorid==0}"><a href="javascript:;"><bean:message key="guest"/> <em>${post.usermap.useip}</em></a><bean:message key="unregistered"/></cite><bean:message key="unregistered"/> </c:when>
									<c:when test="${post.usermap.anonymous>0}"><c:choose><c:when test="${jsprun_groupid<=3}"><a href="space.jsp?uid=${post.usermap.authorid}" target="_blank"><bean:message key="member_anonymous"/></cite></a><bean:message key="member_anonymous"/></c:when><c:otherwise><bean:message key="member_anonymous"/></cite><bean:message key="member_anonymous"/></c:otherwise></c:choose></c:when>
									<c:otherwise>${post.usermap.author}<bean:message key="member_deleted"/></cite>${post.usermap.author}<bean:message key="member_deleted"/> </c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="postcontent" <c:if test="${modertar && usergroups.alloweditpost==1 &&  (post.usermap.adminid==null||post.usermap.adminid<=0 ||jsprun_adminid<=post.usermap.adminid) && (thread.digest>=0 || post.usermap.first!=1)}">ondblclick="ajaxget('modcp.jsp?action=editmessage&pid=${post.usermap.pid}&tid=${post.usermap.tid}&rand='+Math.random(), 'postmessage_${post.usermap.pid}')"</c:if>>
						<div class="postinfo">
							<strong title="<bean:message key="post_copylink"/>" id="postnum_${post.usermap.pid}" onclick="setcopy('${boardurl}viewthread.jsp?tid=${post.usermap.tid}&page=${currentPage}${fromuid}#pid${post.usermap.pid}', '<bean:message key="post_copied"/>')">
							<c:choose>
								<c:when test="${currentPage>1}"> ${empty postcustom[in.index+lpp*(currentPage-1)] ? lpp*(currentPage-1)+in.count : postcustom[in.index+lpp*(currentPage-1)]}${empty postcustom[in.index+(currentPage-1)*lpp] ? settings.postno : ""}</c:when>
								<c:otherwise>${empty postcustom[in.index] ? in.count : postcustom[in.index]} ${empty postcustom[in.index] ? settings.postno : ""}</c:otherwise>
							</c:choose></strong>
							<em onclick="$('postmessage_${post.usermap.pid}').className='t_bigfont'"><bean:message key="big"/></em>
							<em onclick="$('postmessage_${post.usermap.pid}').className='t_msgfont'"><bean:message key="middle"/></em>
							<em	onclick="$('postmessage_${post.usermap.pid}').className='t_smallfont'"><bean:message key="small"/></em>
							<c:if test="${thread.price>=0 || post.usermap.first==1}"><bean:message key="poston"/> <jrun:showTime timeInt="${post.usermap.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>&nbsp;</c:if>
							<c:if test="${post.usermap.authorid!=0 && post.usermap.anonymous<1}">
								<c:choose>
									<c:when test="${param.authorid==null}"><a href="viewthread.jsp?tid=${post.usermap.tid}&page=${currentPage}&authorid=${post.usermap.uid}"><bean:message key="thread_show_author"/></a></c:when>
									<c:otherwise><a href="viewthread.jsp?tid=${post.usermap.tid}&page=${currentPage}"><bean:message key="thread_show_all"/></a></c:otherwise>
								</c:choose>
							</c:if>
						</div>
						<div id="ad_thread2_${in.index}"></div>
						<div class="postmessage defaultpost">
							<c:choose>
								<c:when test="${post.usermap.rate>0}"><span class="postratings"><a href="misc.jsp?action=viewratings&tid=${post.usermap.tid}&pid=${post.usermap.pid}" title="<bean:message key="rate"/> ${post.usermap.rate}"> <c:forEach begin="1" end="${post.ratings}" step="1"><img src="${styles.IMGDIR}/agree.gif" border="0" alt=""/></c:forEach></a></span></c:when>
								<c:when test="${post.usermap.rate<0}"><span class="postratings"><a href="misc.jsp?action=viewratings&tid=${post.usermap.tid}&pid=${post.usermap.pid}" title="<bean:message key="rate"/> ${post.usermap.rate}"> <c:forEach begin="1" end="${post.ratings}" step="1"><img src="${styles.IMGDIR}/disagree.gif" border="0" alt=""/></c:forEach></a></span></c:when>
							</c:choose>
							<div id="ad_thread3_${in.index}"></div><div id="ad_thread4_${in.index}"></div>
							<c:if test="${post.usermap.subject!=''}"><h2>${post.usermap.subject}</h2></c:if>
							<c:if test="${typetemplate=='' && (!empty optionlist) && post.usermap.first==1 && post.usermap.status==0}">
							 <div class="box typeoption">
								<h4><bean:message key="menu_threadtype"/> - ${threadtypes.types[typeid]}</h4>
								<table summary="<bean:message key="menu_threadtype"/>" cellpadding="0" cellspacing="0">
								<c:forEach items="${optionlist}" var="option"><tr><th>${option.title}</th><td>${!empty option.value?option.value:"-"}</td></tr></c:forEach>
								</table>
							</div>
							</c:if>
							<c:choose>
								<c:when test="${post.usermap.authorid!=0 && jsprun_adminid!=1 && settings.bannedmessages!=0 && (post.usermap.username==null || post.usermap.groupid==4 || post.usermap.groupid==5)}"><div class="notice" style="width: 500px"><bean:message key="message_banned"/></div></c:when>
								<c:when test="${jsprun_adminid!=1 && post.usermap.status==1}"><div class="notice" style="width: 500px"><bean:message key="message_single_banned"/></div></c:when>
								<c:otherwise>
								<c:choose>
									<c:when test="${post.usermap.authorid!=0 && settings.bannedmessages!=0 && (post.usermap.username==null || post.usermap.groupid==4 || post.usermap.groupid==5)}"><div class="notice" style="width: 500px"><bean:message key="admin_message_banned"/></div></c:when>
									<c:when test="${post.usermap.status==1}"><div class="notice" style="width: 500px"><bean:message key="admin_message_single_banned"/></div></c:when>
								</c:choose>
								<c:if test="${post.usermap.first==1 && typetemplate!=''}">${typetemplate}</c:if>
								<div id="postmessage_${post.usermap.pid}" class="t_msgfont">${post.usermap.message}</div>
							<c:if test="${post.usermap.attachment>0}">
							<c:choose>
								<c:when test="${!allowgetattach}"><div class="notice" style="width: 500px"><bean:message key="attachment"/>: <em><bean:message key="attach_nopermission"/></em></div></c:when>
								<c:otherwise>
									<div class="box postattachlist">
										<h4><bean:message key="attachment"/></h4>
										<c:forEach items="${post.attaurl}" var="atta">
											<dl class="t_attachlist">
												<dt><img src="images/attachicons/${attatype[atta.filetype]}" border="0" class="absmiddle" alt="" /> <a href="attachment.jsp?aid=${atta.aid}">${atta.filename}</a> <em>(<jrun:showFileSize size="${atta.filesize}" />)</em></dt>
												<dd>
													<p><jrun:showTime timeInt="${atta.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/><c:if test="${settings.attachrefcheck==0 || atta.attachmentvalue==''}">,<bean:message key="attach_downloads"/>: ${atta.downloads}</c:if><c:if test="${atta.readperm>0}">,<bean:message key="threads_readperm"/>: ${atta.readperm}</c:if><c:if test="${atta.price>0}">,<bean:message key="magics_price"/>: ${atta.price}&nbsp;[<a href="misc.jsp?action=viewattachpayments&aid=${atta.aid}" target="_blank"><bean:message key="pay_view"/></a>]&nbsp;<c:if test="${atta.isprice==5}">[<a href="misc.jsp?action=attachpay&aid=${atta.aid}" target="_blank"><bean:message key="attachment_buy"/></a>]</c:if></c:if><c:if test="${atta.description!=''}"><br>${atta.description}</c:if></p>
													<c:if test="${showimag && settings.attachimgpost==1 && atta.attachmentvalue!='' && (readaccess >= atta.readperm || post.usermap.authorid==jsprun_uid)}">
													<c:set scope="page" var="isatta" value="yes"></c:set>
													<c:choose>
														<c:when test="${settings.attachrefcheck==0}">
														<p>
														<c:choose>
															<c:when test="${atta.thumb==1}"><a href="#zoom"><img onclick="zoom(this, '${atta.attachment}')" src="${atta.attachmentvalue}" alt="${atta.filename}" /></a></c:when>
															<c:otherwise><img src="${atta.attachmentvalue}" border="0" onload="attachimg(this, 'load')" onmouseover="attachimg(this, 'mouseover')" onclick="zoom(this, '${atta.attachment}')" alt="${atta.filename}" /></c:otherwise>
														</c:choose>
														</p></c:when>
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
						</c:otherwise>
						</c:choose>
						<c:if test="${settings.tagstatus==1&&post.usermap.first==1 && taglist!=null}"><p class="posttags"><bean:message key="thread_keywords"/> <c:forEach items="${taglist}" var="tags"><a href="tag.jsp?name=<jrun:encoding value="${tags.tagname}"/>" target="_blank">${tags.tagname}</a> </c:forEach></p></c:if>
						<c:if test="${ratelogs[post.usermap.pid]!=null&&post.usermap.rate!=0}">
							<fieldset>
								<legend><a href="misc.jsp?action=viewratings&tid=${post.usermap.tid}&pid=${post.usermap.pid}" title="<bean:message key="rate_view"/>"><bean:message key="thread_rate_log_lately"/></a></legend>
								<ul>
									<c:forEach items="${ratelogs[post.usermap.pid]}" var="ratelog">
									<li><cite><a href="space.jsp?uid=${ratelog.uid}" target="_blank">${ratelog.username}</a></cite>${extcredits[ratelog.extcredits-1]} <strong>${ratelog.score>0?"+":""}${ratelog.score}</strong><em>${ratelog.reason}</em> <jrun:showTime timeInt="${ratelog.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></li>
									</c:forEach>
								</ul>
							</fieldset>
						</c:if>
						</div>
						<c:if test="${isshowsignatures && post.signatures!=''}"><div class="signatures" style="maxHeightIE: ${settings.maxsigrows}px;">${post.signatures}</div></c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td class="postauthor">
						<div class="popupmenu_popup userinfopanel" id="userinfo${in.count}_menu" style="display: none;">
							<c:if test="${post.usermap.qq!='' || post.usermap.icq!='' || post.usermap.yahoo!='' || post.usermap.taobao!=''}">
								<div class="imicons">
									<c:if test="${post.usermap.qq!=''}"><a href="http://wpa.qq.com/msgrd?V=1&Uin=${post.usermap.qq}&Site=${settings.bbname}&Menu=yes" target="_blank"><img src="${styles.IMGDIR}/qq.gif" alt="QQ" /></a></c:if>
									<c:if test="${post.usermap.icq!=''}"><a href="http://wwp.icq.com/scripts/search.dll?to=${post.usermap.icq}" target="_blank"><img src="${styles.IMGDIR}/icq.gif" alt="ICQ" /></a></c:if>
									<c:if test="${post.usermap.yahoo!=''}"><a href="http://edit.yahoo.com/config/send_webmesg?.target=${post.usermap.yahoo}&.src=pg" target="_blank"><img src="${styles.IMGDIR}/yahoo.gif" alt="Yahoo!" /> </a></c:if>
									<c:if test="${post.usermap.taobao!=''}"><script type="text/javascript">document.write('<a target="_blank" href="http://amos1.taobao.com/msg.ww?v=2&uid='+encodeURIComponent('${post.usermap.taobao}')+'&s=2"><img src="${styles.IMGDIR}/taobao.gif" alt="<bean:message key="taobao"/>" /></a>');</script></c:if>
								</div>
							</c:if>
							<dl><c:forEach items="${post.custominfo.menu}" var="cus">${cus}</c:forEach></dl>
							<c:if test="${post.usermap.site!=''}"><p><a href="${post.usermap.site}" target="_blank"><bean:message key="member_homepage"/></a></p></c:if>
							<p><a href="space.jsp?action=viewpro&uid=${post.usermap.uid}" target="_blank"><bean:message key="member_viewpro"/></a></p>
							<c:if test="${usergroups.allowedituser==1}"><p><c:choose><c:when test="${jsprun_adminid==1}"><a href="admincp.jsp?action=members&uids=${post.usermap.uid}&searchsubmit=yes&frames=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank"><bean:message key="menu_member_edit"/></a></c:when><c:otherwise><a href="admincp.jsp?action=editmember&uid=${post.usermap.uid}&membersubmit=yes&frames=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank"><bean:message key="menu_member_edit"/></a></c:otherwise></c:choose></p></c:if>
							<c:if test="${usergroups.allowbanuser==1}"><p><a href="admincp.jsp?action=banmember&uid=${post.usermap.uid}&submitname=yes&frames=yes" target="_blank"><bean:message key="menu_member_ban"/></a></p></c:if>
						</div>
					</td>
					<td class="postcontent">
						<div class="postactions" style="${isie? 'height: 100%':''}">
							<c:if test="${usergroups.allowdelpost>0 &&modertar}"><c:choose><c:when test="${post.usermap.first==1 && thread.digest==-1}"><input type="checkbox" disabled="disabled" /></c:when><c:otherwise><input type="checkbox" name="topiclist[]" value="${post.usermap.pid}" /></c:otherwise></c:choose></c:if>
							<p>
								<c:if test="${((modertar && usergroups.alloweditpost==1 &&  (post.usermap.adminid==null || post.usermap.adminid<=0 ||jsprun_adminid<=post.usermap.adminid))||(jsprun_uid==post.usermap.uid && thread.alloweditpost>0)) && (thread.digest>=0 || post.usermap.first!=1)}"><a href="post.jsp?action=edit&fid=${post.usermap.fid}&tid=${post.usermap.tid}&pid=${post.usermap.pid}&page=${currentPage}&extra=${extra}"><bean:message key="edit"/></a></c:if>
								<c:if test="${allowpostreply}"><a href="post.jsp?action=reply&fid=${post.usermap.fid}&tid=${post.usermap.tid}&repquote=${post.usermap.pid}&extra=${extra}&page=${currentPage}"><bean:message key="reply_quote"/></a></c:if>
								<c:if test="${jsprun_uid!=0 && settings.magicstatus==1 && usergroups.allowmagics>0}"><a href="magic.jsp?action=user&pid=${post.usermap.pid}" target="_blank"><bean:message key="magics_use"/></a></c:if>
								<c:if test="${settings.reportpost>0 && jsprun_uid!=0}"><a href="misc.jsp?action=report&fid=${post.usermap.fid}&tid=${post.usermap.tid}&pid=${post.usermap.pid}&page=${currentPage}" id="ajax_report_${post.usermap.pid}" onclick="ajaxmenu(event, this.id, 9000000, null, 0)"><bean:message key="reportpost"/></a></c:if>
								<c:if test="${usergroups.raterange!='' && post.usermap!=null}"><a href="misc.jsp?action=rate&tid=${post.usermap.tid}&pid=${post.usermap.pid}&page=${currentPage}" id="ajax_rate_${post.usermap.pid}" onclick="ajaxmenu(event, this.id, 9000000, null, 0)"><bean:message key="rate"/></a></c:if>
								<c:if test="${modertar && (post.usermap.rate!=0 || ratetimes>0)}"><a href="misc.jsp?action=removerate&tid=${thread.tid}&pid=${post.usermap.pid}&page=${currentPage}"><bean:message key="removerate"/></a></c:if>
								<c:if test="${allowpostreply}"><a href="###" onclick="fastreply('<bean:message key="post_fastreply_author"/>', 'postnum_${post.usermap.pid}')"><bean:message key="threads_replies"/></a></c:if>
								<c:if test="${usergroups.allowdelpost>0 && modertar}">
									<c:choose>
										<c:when test="${thread.digest>=0 && usergroups.allowdelpost>0 && post.usermap.first==1}"><a href="topicadmin.jsp?operation=delete&fid=${thread.fid}&moderates=${thread.tid}&sid=${sid}&extra=${extra}"><bean:message key="delete"/></a></c:when>
										<c:otherwise><a href="topicadmin.jsp?operation=delpost&fid=${thread.fid}&moderates=${thread.tid}&page=${currentPage}&topiclist[]=${post.usermap.pid}&extra=${extra }"><bean:message key="delete"/></a></c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${usergroups.allowbanpost==1 && modertar}"><a href="topicadmin.jsp?operation=banpost&fid=${thread.fid}&moderates=${thread.tid}&page=${currentPage}&topiclist[]=${post.usermap.pid}&extra=${extra }"><bean:message key="admin_ban"/></a></c:if>
								<strong onclick="scroll(0,0)" title="<bean:message key="top"/>">TOP</strong>
							</p>
							<div id="ad_thread1_${in.index}"></div>
						</div>
					</td>
				</tr>
			</table></div>
		<c:if test="${post.usermap.first>0&&thread.replies>0}"><div id="ad_interthread"></div></c:if>
	</c:forEach>
</form>
<div class="pages_btns">
	<div class="threadflow"><a href="redirect.jsp?fid=${thread.fid}&tid=${thread.tid}&goto=nextoldset" title="<bean:message key="last_thread"/>"> &lsaquo;&lsaquo;<bean:message key="last_thread"/> </a> | <a href="redirect.jsp?fid=${thread.fid}&tid=${thread.tid}&goto=nextnewset" title="<bean:message key="next_thread"/>"> <bean:message key="next_thread"/>&rsaquo;&rsaquo;</a></div>
	${multi.multipage}
	<span class="pageback" <c:if test="${requestScope.visitedforums!=null}"> id="visitedforums" onmouseover="$('visitedforums').id = 'visitedforumstmp';this.id = 'visitedforums';showMenu(this.id)" </c:if>><a href="${backtrack}" title="<bean:message key="return_forumdisplay"/>"><bean:message key="return_forumdisplay"/></a></span>
	<c:if test="${allowpostreply}"><span class="replybtn"><a href="post.jsp?action=reply&fid=${thread.fid}&tid=${thread.tid}&extra=${extra}&page=${lpp}"><img src="${styles.IMGDIR}/reply_${sessionScope['org.apache.struts.action.LOCALE']}.gif" border="0" alt="" /> </a> </span></c:if>
	<c:if test="${allowpost||jsprun_uid==null}"><span class="postbtn" id="newspecial" onmouseover="$('newspecial').id = 'newspecialtmp';this.id = 'newspecial';showMenu(this.id)"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}" title="<bean:message key="post_new"/>"><img src="${styles.IMGDIR}/newtopic_${sessionScope['org.apache.struts.action.LOCALE']}.gif" alt="<bean:message key="post_new"/>" /></a></span></c:if>
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
		<c:forEach items="${threadtypes.types}" var="threadtype"><c:if test="${threadtypes.special[threadtype.key]==1&&threadtypes.show[threadtype.key]==1}"><li class="popupmenu_option"><a href="post.jsp?action=newthread&fid=${thread.fid}&page=${currentPage}&typeid=${threadtype.key}">${threadtype.value}</a></li></c:if></c:forEach>
	</c:if>
</ul>
</c:if>
<script src="include/javascript/post.js" type="text/javascript"></script>
<c:if test="${allowpostreply && settings.fastreply==1}">
<script type="text/javascript">
lang['last_page'] = '<bean:message key="last_page"/>';
lang['next_page'] = '<bean:message key="next_page"/>';
</script>
<script type="text/javascript">
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
	<input type="hidden" name="page" value="${lpp}" />
	<input type="hidden" name="extra" value="${extra}" />
	<div id="quickpost" class="box">
		<span class="headactions"><a href="member.jsp?action=credits&view=forum_reply&fid=${thread.fid}" target="_blank"><bean:message key="credits_policy_view"/></a> </span>
		<h4><bean:message key="post_fastreply"/></h4>
		<div class="postoptions">
			<h5><bean:message key="options"/></h5>
			<p><label><input class="checkbox" type="checkbox" name="parseurloff" id="parseurloff" value="1" /> <bean:message key="disable"/> <bean:message key="post_parseurl"/></label></p>
			<p><label><input class="checkbox" type="checkbox" name="smileyoff" id="smileyoff" value="1" /> <bean:message key="disable"/> <a href="faq.jsp?action=message&id=${faqs.smilies.id}" target="_blank">${faqs.smilies.keyword}</a></label></p>
			<p><label><input class="checkbox" type="checkbox" name="bbcodeoff" id="bbcodeoff" value="1" /> <bean:message key="disable"/> <a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank">${faqs.JspRuncode.keyword}</a></label></p>
			<c:if test="${usergroups.allowanonymous==1}"><p><label><input class="checkbox" type="checkbox" name="isanonymous" value="1" /> <bean:message key="post_anonymous"/></label></p></c:if>
			<p><label><input class="checkbox" type="checkbox" name="usesig" value="1" /> <bean:message key="post_show_sig"/></label></p>
			<p><label><input class="checkbox" type="checkbox" name="emailnotify" value="1" /><bean:message key="post_email_notify"/></label></p>
		</div>
		<div class="postform">
			<h5 style="width:580px;"><label><bean:message key="subject"/>&nbsp;&nbsp;</label><input type="text" name="subject" value="" tabindex="1" id="subject"></h5>
			<p>
				<jsp:include flush="true" page="seditor.jsp"/>
				<div><textarea rows="7" cols="80" class="autosave" name="message" id="fastpostmessage" onkeydown="ctlent(event);savePos(this)" onkeyup="savePos(this)" onmousedown="savePos(this)" onmouseup="savePos(this)" onfocus="savePos(this)"  tabindex="2" style="width:597px;"></textarea></div>
			</p>
			<c:if test="${allowpostattach}"><p><div>
				<table class="box" cellspacing="0" cellpadding="0" style="width:600px;">
					<thead><tr><c:if test="${usergroups.allowsetattachperm>0}"><td class="nums"><bean:message key="threads_readperm"/></td></c:if><td>&nbsp;<bean:message key="description"/></td><td><bean:message key="access_postattach"/></td></tr></thead>
					<tbody id="attachbodyhidden" style="display:none"><tr>
						<c:if test="${usergroups.allowsetattachperm>0}"><td class="nums"><input type="text" name="attachperm[]" value="0" size="1" /></td></c:if>
						<td>&nbsp;<input type="text" name="attachdesc[]" size="15" /></td>
						<td><input type="file" name="attach" /> <span id="localfile[]"></span> <input type="hidden" name="localid[]" /></td>
					</tr></tbody>
					<tbody id="attachbody"></tbody>
					<tr><td colspan="3" style="border-bottom: none;"><bean:message key="attachment_size"/>: <strong><c:choose><c:when test="${usergroups.maxattachsize>0}"><bean:message key="lower_than"/> ${usergroups.maxattachsize/1000} kb </c:when><c:otherwise><bean:message key="size_no_limit"/></c:otherwise></c:choose><c:if test=""></c:if></strong>&nbsp;&nbsp;<c:if test="${usergroups.attachextensions!=''}"><bean:message key="attachment_allow_exts"/>: <strong>${usergroups.attachextensions}</strong><br /></c:if></td></tr>
				</table>
			</div></p></c:if>
			<p class="btns"><button type="submit" name="replysubmit" id="postsubmit" value="replysubmit" tabindex="3"><bean:message key="a_forum_edit_perm_reply"/></button> <bean:message key="post_submit_hotkey"/>&nbsp; <a href="###" id="previewpost" onclick="$('postform').action=$('postform').action + '&previewpost=yes&subject='+$('subject').value+'&message='+$('fastpostmessage').value;$('postform').submit();"><bean:message key="post_previewpost"/></a>&nbsp; <a href="###" id="restoredata" title="<bean:message key="post_autosave_last_restore"/>" onclick="loadData()"><bean:message key="post_autosave_restore"/></a>&nbsp; <a href="###" onclick="$('postform').reset()"><bean:message key="post_topicreset"/></a></p>
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
</form>
</c:if>
<script type="text/javascript">
function modaction(action) {
	if(!action) {
		return;
	}
	if(in_array(action, ['delpost', 'banpost'])) {
		document.modactions.action = 'topicadmin.jsp?operation='+action+'&fid=${thread.fid}&moderates=${thread.tid}&page=${currentPage}&extra=${extra}';
		document.modactions.submit();
	} else if(in_array(action, ['delete', 'close','move','highlight','type','digest','stick','bump','recommend'])) {
		window.location=('topicadmin.jsp?operation='+ action +'&fid=${thread.fid}&moderates=${thread.tid}&sid=${sid}&extra=${extra}');
	} else if(action == 'repair'){
		document.modactions.action = 'topicadmin.jsp?action='+ action +'&fid=${thread.fid}&moderates=${thread.tid}&page=${currentPage}&extra=${extra}&formHash=${jrun:formHash(pageContext.request)}';
		document.modactions.submit();
	} else{
		document.modactions.action = 'topicadmin.jsp?action='+ action +'&fid=${thread.fid}&moderates=${thread.tid}&page=${currentPage}&extra=${extra}';
		document.modactions.submit();
	}
}
var extensions = '${usergroups.attachextensions}';
</script>
<c:if test="${modertar || requestScope.visitedforums!=null ||(settings.forumjump==1 && settings.jsmenu_1==0)}">
<div id="footfilter" class="box">
	<c:if test="${modertar}">
	<form action="#">
		<bean:message key="menu_moderation"/>:
		<select name="action" id="action" onchange="modaction(this.options[this.selectedIndex].value)">
			<option value="" selected><bean:message key="menu_moderation"/></option>
			<c:if test="${usergroups.allowdelpost>0}">
				<option value="delpost"><bean:message key="admin_delpost"/></option>
				<c:if test="${thread.digest>=0 && usergroups.allowdelpost>0}"><option value="delete"><bean:message key="admin_delthread"/></option></c:if>
			</c:if>
			<c:if test="${usergroups.allowbanpost==1}"><option value="banpost"><bean:message key="BNP"/></option></c:if>
			<c:if test="${thread.digest>=0}">
				<option value="close"><bean:message key="admin_close"/></option>
				<option value="move"><bean:message key="admin_move"/></option>
				<option value="copy"><bean:message key="admin_copy"/></option>
				<option value="highlight"><bean:message key="admin_highlight"/></option>
				<option value="type"><bean:message key="menu_forum_threadtypes"/></option>
				<option value="digest"><bean:message key="admin_digest"/></option>
				<c:if test="${usergroups.allowstickthread>0}"><option value="stick"><bean:message key="admin_stick"/></option></c:if>
				<c:if test="${thread.price>0 && usergroups.allowrefund>0 && thread.special==0}"><option value="refund"><bean:message key="RFD"/></option></c:if>
				<option value="split"><bean:message key="admin_split"/></option>
				<option value="merge"><bean:message key="admin_merge"/></option>
				<option value="bump"><bean:message key="admin_bump"/></option>
				<option value="repair"><bean:message key="admin_repair"/></option>
				<c:if test="${(modrecommendMap.open != null && modrecommendMap.open != '' && modrecommendMap.open!=0) && (modrecommendMap.sort != null && modrecommendMap.sort != '' && modrecommendMap.sort!=1)}" ><option value="recommend"><bean:message key="REC"/></option></c:if>
			</c:if>
		</select>
	</form>
	</c:if>
	<c:if test="${settings.forumjump==1&&settings.jsmenu_1==0}"><select onchange="if(this.options[this.selectedIndex].value != '') {window.location=('forumdisplay.jsp?fid='+this.options[this.selectedIndex].value+'&sid=${sid}')}"><option value=""><bean:message key="forum_jump"/></option>${forumselect}</select></c:if>
	<c:if test="${requestScope.visitedforums!=null}"><select onchange="if(this.options[this.selectedIndex].value != '') window.location=('forumdisplay.jsp?fid='+this.options[this.selectedIndex].value+'&sid=${sid}')"><option value=""><bean:message key="visited_forums"/></option><c:forEach items="${requestScope.visitedforums}" var="visitedforum"><c:if test="${visitedforum.key!=thread.fid}"><option value="${visitedforum.key}">${visitedforum.value}</option></c:if></c:forEach></select></c:if>
</div>
</c:if>
<script type="text/javascript">
var maxpage = ${multi.maxpage>0?(multi.maxpage):1};
if(maxpage > 1) {
	document.onkeyup = function(e){
		e = e ? e : window.event;
		var tagname = is_ie ? e.srcElement.tagName : e.target.tagName;
		if(tagname == 'INPUT' || tagname == 'TEXTAREA') return;
		actualCode = e.keyCode ? e.keyCode : e.charCode;
		if(${currentPage<multi.maxpage})
		{
			if(actualCode == 39) {
				window.location = 'viewthread.jsp?tid=${thread.tid}&extra=${extra}&page=${currentPage+1}';
			}
		}
		if(${currentPage>1})
		{
			if(actualCode == 37) {
				window.location = 'viewthread.jsp?tid=${thread.tid}&extra=${extra}&page=${currentPage-1}';
			}
		}
	}
}
</script>
<script src="include/javascript/msn.js" type="text/javascript"></script>
<c:if test="${allowpostreply && settings.fastreply==1 && allowpostattach}">
	<script type="text/javascript">
			lang['post_attachment_ext_notallowed']	= '<bean:message key="post_attachment_ext_notallowed"/>';
			lang['post_attachment_img_invalid']	= '<bean:message key="post_attachment_img_invalid"/>';
			lang['post_attachment_deletelink']	= '<bean:message key="delete"/>';
			lang['post_attachment_insert']		= '<bean:message key="post_attachment_insert"/>';
			lang['post_attachment_insertlink']	= '<bean:message key="post_attachment_insertlink"/>';
	</script>
<script src="include/javascript/postfast_attach.js" type="text/javascript"></script>
</c:if>
<c:if test="${settings.forumjump==1&&settings.jsmenu_1>0}"><div class="popupmenu_popup" id="forumlist_menu" style="display: none">${forummenu}</div></c:if>
<jsp:directive.include file="footer.jsp" />