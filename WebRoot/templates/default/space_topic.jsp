<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="space_header.jsp" />
<script type="text/javascript" src="include/javascript/viewthread.js"></script>
<script type="text/javascript">
zoomstatus = parseInt(${settings.zoomstatus});
lang['zoom_image'] = '<bean:message key="zoom_image"/>';
lang['a_system_js_newwindow_blank'] = '<bean:message key="a_system_js_newwindow_blank"/>';
lang['full_size'] = '<bean:message key="full_size"/>';
lang['closed'] = '<bean:message key="closed"/>';
lang['copy_to_cutedition'] = '<bean:message key="copy_to_cutedition"/>';
</script>
<div class="outer">
	<table class="main" border="0" cellspacing="0">
		<tr>
			<td id="main_layout1">
				<div id="module_topic">
					<table class="module" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td class="header">
								<div class="title">
									<c:if test="${thread.special==1}"><a target="_blank" href="viewthread.jsp?tid=${thread.tid}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" border="0" /></a></c:if>
									<c:if test="${thread.special==2}"><a target="_blank" href="viewthread.jsp?tid=${thread.tid}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" border="0" /></a></c:if>
									<c:if test="${thread.special==3}">
										<c:choose>
											<c:when test="${thread.price>0}"><a target="_blank" href="viewthread.jsp?tid=${thread.tid}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" border="0" /></a></c:when>
											<c:when test="${thread.price<=0}"><a target="_blank" href="viewthread.jsp?tid=${thread.tid}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" border="0" /></a></c:when>
										</c:choose>
									</c:if>
									<c:if test="${thread.special==4}"><a target="_blank" href="viewthread.jsp?tid=${thread.tid}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" border="0" /></a></c:if>
									${thread.subject}
								</div>
								<div class="more">
									<a href="viewthread.jsp?action=printable&tid=${thread.tid}" target="_blank"><bean:message key="thread_printable"/></a> |
									<a href="misc.jsp?action=emailfriend&tid=${thread.tid}" target="_blank"><bean:message key="thread_email_friend"/></a>
									<c:choose>
										<c:when test="${jsprun_uid==thread.authorid || modertar}">
											| <a href="misc.jsp?action=blog&tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}"><bean:message key="blog_remove"/></a>
											| <a href="post.jsp?action=edit&fid=${thread.fid}&tid=${thread.tid}&pid=${post.pid}" target="_blank"><bean:message key="a_post_moderate_edit_post"/></a>
										</c:when>
										<c:otherwise>
											| <a href="misc.jsp?action=rate&tid=${thread.tid}&pid=${post.pid}&isblog=yes" target="_blank"><bean:message key="rate"/></a>
										</c:otherwise>
									</c:choose>
								</div>
							</td>
						</tr>
						<tr>
							<td class="message">
								<div class="dateline"><jrun:showTime timeInt="${thread.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></div>
								<br />
								<div style="float: right"><c:if test="${post.rate>0}"><img src="${styles.IMGDIR}/agree.gif" border="0" alt="" /></c:if></div>
								${post.message}
								<c:if test="${post.attachment>0}">
										<c:choose>
											<c:when test="${!allowgetattach}"><div class="notice" style="width: 500px"> <bean:message key="attachment"/>: <em><bean:message key="attach_nopermission"/></em> </div></c:when>
											<c:otherwise>
											<div class="attach msgbody">
											<div class="msgheader"><bean:message key="attachment"/></div>
											<div class="msgborder" style="padding: 0px; border-bottom: 0px;">
													<c:forEach items="${attamaps[post.pid]}" var="atta">
													<dl class="t_attachlist">
														<dt>
															<img src="images/attachicons/${attatype[atta.filetype]}" border="0" class="absmiddle" alt="" />
															<a href="attachment.jsp?aid=${atta.aid}">${atta.filename}</a>
															<em>(<jrun:showFileSize size="${atta.filesize}"/>)</em>
														</dt>
														<dd>
															<p><jrun:showTime timeInt="${atta.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/><c:if test="${settings.attachrefcheck==0 && atta.isimage<1}">,<bean:message key="attach_downloads"/>: ${atta.downloads}</c:if><c:if test="${atta.readperm>0}">,<bean:message key="threads_readperm"/>: ${atta.readperm}</c:if><c:if test="${atta.price>0}">,<bean:message key="a_post_threads_price"/>: ${atta.price}&nbsp;[<a href="misc.jsp?action=viewattachpayments&amp;aid=${atta.aid}" target="_blank"><bean:message key="pay_view"/></a>]&nbsp;<c:if test="${atta.isprice==5}">[<a href="misc.jsp?action=attachpay&amp;aid=${atta.aid}" target="_blank"><bean:message key="attachment_buy"/></a>]</c:if></c:if><c:if test="${atta.description!=''}"><br>${atta.description}</c:if></p>
															<c:if test="${showimag && settings.attachimgpost==1 && atta.isimage>0 && (readaccess >= atta.readperm || atta.authorid==jsprun_uid)}">
																<c:choose>
																	<c:when test="${settings.attachrefcheck==0}"><p><a href="#zoom"><img onclick="zoom(this, '${atta.attachment}')" src="${atta.attachmentvalue}" alt="${atta.filename}" /></a> </p></c:when>
																	<c:otherwise>
																		<c:choose>
																		<c:when test="${atta.thumb==1}"><p><a href="#zoom"><img onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" src="attachment.jsp?aid=${atta.aid}&amp;noupdate=yes" alt="${atta.filename}" /></a></p></c:when>
																		<c:otherwise><p> <img src="attachment.jsp?aid=${atta.aid}&amp;noupdate=yes" border="0" onload="attachimg(this, 'load')" onmouseover="attachimg(this, 'mouseover')" onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" alt="01.gif" /> </p></c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>
														</c:if>
														</dd>
													</dl>
													</c:forEach>
												</div>
												</div>
											</c:otherwise>
										</c:choose>
									</c:if>
								<c:if test="${post.first==1 && taglist!=null}">
									<p class="posttags">
										<bean:message key="thread_keywords"/>
										<c:forEach items="${taglist}" var="tags">
											<a href="tag.jsp?name=<jrun:encoding value="${tags.tagname}"/>" target="_blank">${tags.tagname}</a>
										</c:forEach>
									</p>
								</c:if>
							</td>
						</tr>
					</table>
				</div>
				<div id="module_topiccomment">
					<c:if test="${!empty postlist}">
						<table class="module" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td class="header"><div class="title"><bean:message key="blog_comments"/>(${thread.replies})</div></td>
							</tr>
							<tr>
								<td>
											
						<c:if test="${logpage.totalSize > lpp}">
							<div class="p_bar">
								<a class="p_total">&nbsp;${logpage.totalSize}&nbsp;</a>
								<a class="p_pages">&nbsp;${logpage.currentPage}/${logpage.totalPage}&nbsp;</a>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="${url}&amp;page=1" class="p_pages">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="${url}&amp;page=${logpage.prePage}" class="p_redirect">&lsaquo;&lsaquo;</a>
								</c:if>
								<c:choose>
									<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
										
										<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
											<c:choose>
												<c:when test="${logpage.currentPage == num}">
													<a class="p_curpage">${logpage.currentPage}</a>
												</c:when>
												<c:otherwise>
													<a href="${url}&amp;page=${num}" class="p_num">${num}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4}">
												
												<c:forEach var="num" begin="${logpage.totalPage-9}" end="${logpage.totalPage}" step="1">
													<c:choose>
														<c:when test="${logpage.currentPage == num}">
															<a class="p_curpage">${logpage.currentPage}</a>
														</c:when>
														<c:otherwise>
															<a href="${url}&amp;page=${num}" class="p_num">${num}</a>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${logpage.totalPage>10}">
													
														<c:forEach var="num" begin="1" end="10" step="1">
															<c:choose>
																<c:when test="${logpage.currentPage == num}">
																	<a class="p_curpage">${logpage.currentPage}</a>
																</c:when>
																<c:otherwise>
																	<a href="${url}&amp;page=${num}" class="p_num">${num}</a>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:when>
													<c:otherwise>
													
														<c:forEach var="num" begin="1" end="${logpage.totalPage}" step="1">
															<c:choose>
																<c:when test="${logpage.currentPage == num}">
																	<a class="p_curpage">${logpage.currentPage}</a>
																</c:when>
																<c:otherwise>
																	<a href="${url}&amp;page=${num}" class="p_num">${num}</a>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								
								<c:if test="${logpage.currentPage != logpage.nextPage}">
									<a href="${url}&amp;page=${logpage.nextPage}" class="p_redirect">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a href="${url}&amp;page=${logpage.totalPage}" class="p_pages">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"  onkeydown="if(event.keyCode==13) {window.location='${url}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
									<br />
									<c:forEach items="${postlist}" var="post">
										<br />
										<div class="message">
											<c:if test="${!empty post.subject}">
												<div class="comment_subject">${post.subject}</div>
											</c:if>
											<c:choose>
												<c:when test="${jsprun_adminid!=1 && settings.bannedmessages!=0 && (post.author==null || post.groupid==4 || post.groupid==5)}">
													<div class="notice" style="width: 500px">
														<bean:message key="message_banned"/>
													</div>
												</c:when>
												<c:when test="${jsprun_adminid!=1 && post.status==1}">
													<div class="notice" style="width: 500px">
														<bean:message key="message_single_banned"/>
													</div>
												</c:when>
												<c:otherwise>
													<div id="postmessage_${post.pid}" class="t_msgfont">
														${post.message}
													</div>
											<c:if test="${post.attachment>0}">
											<c:choose>
											<c:when test="${!allowgetattach}"><div class="notice" style="width: 500px"> <bean:message key="attachment"/>: <em><bean:message key="attach_nopermission"/></em> </div></c:when>
											<c:otherwise>
												<div class="attach msgbody">
												<div class="msgheader"><bean:message key="attachment"/></div>
												<div class="msgborder" style="padding: 0px; border-bottom: 0px;">
													<c:forEach items="${attamaps[post.pid]}" var="atta">
													<dl class="t_attachlist">
														<dt>
															<img src="images/attachicons/${attatype[atta.filetype]}" border="0" class="absmiddle" alt="" />
															<a href="attachment.jsp?aid=${atta.aid}">${atta.filename}</a>
															<em>(<jrun:showFileSize size="${atta.filesize}"/>)</em>
														</dt>
														<dd>
															<p><jrun:showTime timeInt="${atta.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/><c:if test="${settings.attachrefcheck==0 && atta.isimage<1}">,<bean:message key="attach_downloads"/>: ${atta.downloads}</c:if><c:if test="${atta.readperm>0}">,<bean:message key="threads_readperm"/>: ${atta.readperm}</c:if><c:if test="${atta.price>0}">,<bean:message key="a_post_threads_price"/>: ${atta.price}&nbsp;[<a href="misc.jsp?action=viewattachpayments&amp;aid=${atta.aid}" target="_blank"><bean:message key="pay_view"/></a>]&nbsp;<c:if test="${atta.isprice==5}">[<a href="misc.jsp?action=attachpay&amp;aid=${atta.aid}" target="_blank"><bean:message key="attachment_buy"/></a>]</c:if></c:if><c:if test="${atta.description!=''}"><br>${atta.description}</c:if></p>
															<c:if test="${showimag && settings.attachimgpost==1 && showatta!='false'}">
															<c:if test="${atta.isimage>0 && (readaccess >= atta.readperm || atta.authorid==jsprun_uid)}">
																<c:choose>
																	<c:when test="${settings.attachrefcheck==0}"><p><a href="#zoom"><img onclick="zoom(this, '${atta.attachment}')" src="${atta.attachmentvalue}" alt="${atta.filename}" /></a> </p></c:when>
																	<c:otherwise>
																		<c:choose>
																		<c:when test="${atta.thumb==1}"><p><a href="#zoom"><img onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" src="attachment.jsp?aid=${atta.aid}&amp;noupdate=yes" alt="${atta.filename}" /></a></p></c:when>
																		<c:otherwise><p> <img src="attachment.jsp?aid=${atta.aid}&amp;noupdate=yes" border="0" onload="attachimg(this, 'load')" onmouseover="attachimg(this, 'mouseover')" onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" alt="01.gif" /> </p></c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>
															</c:if>
														</c:if>
														</dd>
													</dl>
													</c:forEach>
												</div>
												</div>
											</c:otherwise>
										</c:choose>
									</c:if>
										</c:otherwise>
										</c:choose>
											<br style="clear: both">
											<br />
											<div class="author">
												<c:choose>
													<c:when test="${post.authorid!=0 && post.author!='' && post.anonymous<=0}">
														<a href="space.jsp?uid=${post.authorid}" class="bold" title="${post.grouptitle}&nbsp;&nbsp;<bean:message key="credits"/>: ${post.credits}&nbsp;<bean:message key="a_setting_posts"/>: ${post.posts}&nbsp;<bean:message key="register"/>:<jrun:showTime timeInt="${post.regdate}" type="${dateformat}" timeoffset="${timeoffset}"/>">${post.author}</a>
														<jrun:showstars num="${post.stars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${post.authorid==0}"><span class="bold"><bean:message key="guest"/></span></c:when>
															<c:when test="${post.authorid!=0 && post.author!='' && post.anonymous==1}"><span class="bold"><bean:message key="anonymous"/></span></c:when>
															<c:otherwise><span class="bold">${post.author}</span><span class="smalltxt"><bean:message key="member_deleted"/></span></c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</div>
											<div class="dateline"><jrun:showTime timeInt="${post.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></div>
										</div>
										<br />
									</c:forEach>
										
						<c:if test="${logpage.totalSize > lpp}">
							<div class="p_bar">
								<a class="p_total">&nbsp;${logpage.totalSize}&nbsp;</a>
								<a class="p_pages">&nbsp;${logpage.currentPage}/${logpage.totalPage}&nbsp;</a>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="${url}&amp;page=1" class="p_pages">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="${url}&amp;page=${logpage.prePage}" class="p_redirect">&lsaquo;&lsaquo;</a>
								</c:if>
								<c:choose>
									<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
										
										<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
											<c:choose>
												<c:when test="${logpage.currentPage == num}">
													<a class="p_curpage">${logpage.currentPage}</a>
												</c:when>
												<c:otherwise>
													<a href="${url}&amp;page=${num}" class="p_num">${num}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4}">
												
												<c:forEach var="num" begin="${logpage.totalPage-9}" end="${logpage.totalPage}" step="1">
													<c:choose>
														<c:when test="${logpage.currentPage == num}">
														<a class="p_curpage">${logpage.currentPage}</a>
														</c:when>
														<c:otherwise>
															<a href="${url}&amp;page=${num}" class="p_num">${num}</a>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${logpage.totalPage>10}">
													
														<c:forEach var="num" begin="1" end="10" step="1">
															<c:choose>
																<c:when test="${logpage.currentPage == num}">
																	<a class="p_curpage">${logpage.currentPage}</a>
																</c:when>
																<c:otherwise>
																	<a href="${url}&amp;page=${num}" class="p_num">${num}</a>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:when>
													<c:otherwise>
													
														<c:forEach var="num" begin="1" end="${logpage.totalPage}" step="1">
															<c:choose>
																<c:when test="${logpage.currentPage == num}">
																	<a class="p_curpage">${logpage.currentPage}</a>
																</c:when>
																<c:otherwise>
																	<a href="${url}&amp;page=${num}" class="p_num">${num}</a>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								
								<c:if test="${logpage.currentPage != logpage.nextPage}">
									<a href="${url}&amp;page=${logpage.nextPage}" class="p_redirect">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a href="${url}&amp;page=${logpage.totalPage}" class="p_pages">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"  onkeydown="if(event.keyCode==13) {window.location='${url}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
									<br />
								</td>
							</tr>
						</table>
					</c:if>
				</div>
				<c:choose>
					<c:when test="${allowpostreply && settings.fastreply==1}">
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
			alert('<bean:message key="post_message_length_invalid"/>'+'\n\n<bean:message key="post_curlength"/>: '+theform.message.value.length+'<bean:message key="bytes"/>\n<bean:message key="board_allowed"/>:'+postminchars+'<bean:message key="to"/>'+postmaxchars+'<bean:message key="bytes"/>');
			return false;
		}
		theform.replysubmit.disabled = true;
		return true;
	}
	var postSubmited = false;
	function ctlent(event) {
		if(postSubmited == false && (event.ctrlKey && event.keyCode == 13) || (event.altKey && event.keyCode == 83) && $('postsubmit')) {
			postSubmited = true;
			$('postsubmit').disabled = true;
			$('postform').submit();
		}
	}
	</script>
		<form id="postform" method="post" name="input" action="post.jsp?action=reply&amp;fid=${thread.fid}&amp;tid=${thread.tid}&amp;replysubmit=yes&formHash=${jrun:formHash(pageContext.request)}" onSubmit="return validate(this)" enctype="multipart/form-data">
					<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
					<input type="hidden" name="isblog" value="yes">
					<input type="hidden" name="page" value="${currentPage}">
						<table id="module_postcomment" align="center" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td colspan="2" class="header"><div class="reply"><a target="_blank" href="post.jsp?action=reply&fid=${thread.fid}&tid=${thread.tid}"><bean:message key="space_adv"/></a></div><bean:message key="blog_post_comment"/></td>
							</tr>
							<tr>
								<th><bean:message key="subject"/> (<bean:message key="optional"/>)</th>
								<td><input class="input" type="text" name="subject" value="" tabindex="1"></td>
							</tr>
							<tr>
								<th>
									<bean:message key="options"/><br />
									<input class="checkbox" type="checkbox" name="parseurloff" value="1"><bean:message key="disable"/> <bean:message key="post_parseurl"/><br />
									<input class="checkbox" type="checkbox" name="smileyoff" value="1"><bean:message key="disable"/> <a href="faq.jsp?action=message&id=${faqs.smilies.id}" target="_blank">${faqs.smilies.keyword}</a><br />
									<input class="checkbox" type="checkbox" name="bbcodeoff" value="1"><bean:message key="disable"/> <a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank">${faqs.JspRuncode.keyword}</a><br />
									<input class="checkbox" type="checkbox" name="usesig" value="1"><bean:message key="post_show_sig"/><br />
									<input class="checkbox" type="checkbox" name="emailnotify" value="1"><bean:message key="post_email_notify"/>
								</th>
								<td>
									<textarea rows="7" name="message" onKeyDown="ctlent(event);" tabindex="2"></textarea><br />
									<input class="button" type="submit" name="replysubmit" id="postsubmit" value="<bean:message key="blog_post_comment"/>" tabindex="3">	&nbsp;&nbsp;&nbsp;
									<input class="button" type="reset" name="topicsreset" value="<bean:message key="post_topicreset"/>" tabindex="4">&nbsp; &nbsp;<bean:message key="post_submit_hotkey"/>
								</td>
							</tr>
						</table>
					</form>
					</c:when>
					<c:otherwise>
						<table id="module_postcomment" align="center">
							<tr><td colspan="2" class="header"><bean:message key="blog_post_comment"/></td></tr>
							<tr><td><bean:message key="blog_closed"/></td></tr>
						</table>
					</c:otherwise>
				</c:choose>
				<div align="right"><a target="_blank" href="viewthread.jsp?tid=${thread.tid}"><bean:message key="full_version"/></a></div>
			</td>
		</tr>
	</table>
</div>
<jsp:include flush="true" page="space_footer.jsp" />