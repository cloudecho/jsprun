<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<div class="box specialpostcontainer">
	<ul class="tabs">
		<c:if test="${thread.special==1 || thread.special==6}"><li class="current"><a href="viewthread.js?fid=${thread.fid}&tid=${thread.tid}&do=viewspecialpost" onclick="ajaxget(this.href+'&rand='+Math.random(), 'ajaxspecialpost', 'specialposts');doane(event);"><bean:message key="special_tab_poll"/></a></li></c:if>
		<c:if test="${thread.special==3}"><li class="current"><a href="viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewspecialpost" onclick="ajaxget(this.href+'&rand='+Math.random(), 'ajaxspecialpost', 'specialposts');doane(event);"><bean:message key="special_tab_activity"/></a></li></c:if>
		<c:if test="${thread.special==4}"><li class="current"><a href="viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewspecialpost" onclick="ajaxget(this.href+'&rand='+Math.random(), 'ajaxspecialpost', 'specialposts');doane(event);"><bean:message key="special_tab_activity"/></a></li><li><a href="misc.jsp?action=activityapplylist&tid=${thread.tid}" onclick="ajaxget(this.href+'&rand='+Math.random(), 'ajaxspecialpost', 'specialposts');doane(event);"><bean:message key="activity_join_list"/></a></li></c:if>
	</ul>
	<form method="post" name="modactions">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<c:choose>
			<c:when test="${postlist!=null}">
				<c:forEach items="${postlist}" var="posts" varStatus="in">
				<c:forEach items="${posts}" var="post">
				<div class="specialpost">
					<div class="postinfo">
						<h2><a class="dropmenu" style="font-weight: normal;" href="space.jsp?uid=${post.key.authorid}" target="_blank" onclick="ajaxmenu(event, this.id, 0, '', 1, 3, 0)" id="author_${post.key.pid}">${post.key.author}</a> <jrun:showTime timeInt="${post.key.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></h2>
						<strong title="<bean:message key="post_copylink"/>" id="postnum_${post.key.pid}" onclick="setcopy('${boardurl}viewthread.jsp?tid=${post.key.tid}&page=${currentPage}${fromuid}#pid${post.key.pid}', '<bean:message key="post_copied"/>')">
							<c:choose>
								<c:when test="${currentPage>1}"> ${empty postcustom[in.count+lpp*(currentPage-1)] ? lpp*(currentPage-1)+in.count : postcustom[in.count+lpp*(currentPage-1)]}${empty postcustom[in.count+(currentPage-1)*lpp] ? settings.postno : ""}</c:when>
								<c:otherwise>${empty postcustom[in.count] ? in.count : postcustom[in.count]} ${empty postcustom[in.count] ? settings.postno : ""}</c:otherwise>
							</c:choose>	
						</strong>
						<c:if test="${thread.special==3 && (modertar || thread.authorid==jsprun_uid) && jsprun_uid!=post.key.authorid && post.key.authorid!=thread.authorid && post.key.first==0 && thread.price>0}"><label onclick="setanswer(${post.key.pid})"><bean:message key="reward_bestanswer"/></label></c:if>
						<c:if test="${(modertar && usergroups.alloweditpost>0 && (post.key.adminid<=0 ||jsprun_adminid<=post.key.adminid)) || (thread.alloweditpost>0 && jsprun_uid!=0 && post.key.authorid==jsprun_uid)}"><a href="post.jsp?action=edit&fid=${post.key.fid}&tid=${post.key.tid}&pid=${post.key.pid}&page=1&extra=${extra}"><bean:message key="edit"/></a></c:if>
						<c:if test="${allowpostreply}"><a href="post.jsp?action=reply&fid=${post.key.fid}&tid=${post.key.tid}&repquote=${post.key.pid}&extra=${extra}&page=${lpp}"><bean:message key="reply_quote"/></a></c:if>
						<c:if test="${modertar && usergroups.allowdelpost>0}"><input class="checkbox" type="checkbox" name="topiclist[]" value="${post.key.pid}" /></c:if>
					</div>
					<div class="postmessage">
						<h2>${post.key.subject}</h2>
						<c:choose>
							 <c:when test="${jsprun_adminid!=1 && settings.bannedmessages!=0 && (post.key.author==null || post.key.groupid==4 || post.key.groupid==5)}"><div class="notice" style="width: 500px"><bean:message key="message_banned"/></div></c:when>
							<c:when test="${jsprun_adminid!=1 && post.key.status==1}"><div class="notice" style="width: 500px"><bean:message key="message_single_banned"/></div></c:when>
							<c:otherwise>
							<div class="t_msgfont">${post.key.message}</div>
							<c:if test="${post.key.attachment>0}">
							<c:choose>
								<c:when test="${!allowgetattach}">
									<div class="notice" style="width: 500px">
										<bean:message key="attachment"/>:<em><bean:message key="attach_nopermission"/></em>
									</div>
								</c:when>
								<c:otherwise>
									<div class="box postattachlist">
										<h4><bean:message key="attachment"/></h4>
										<c:forEach items="${post.value}" var="atta">
											<dl class="t_attachlist">
												<dt><img src="images/attachicons/${attatype[atta.filetype]}" border="0" class="absmiddle" alt="" /> <a href="attachment.jsp?aid=${atta.aid}">${atta.filename}</a> <em>(<jrun:showFileSize size="${atta.filesize}" />)</em></dt>
												<dd>
													<p><jrun:showTime timeInt="${atta.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/><c:if test="${settings.attachrefcheck==0 || atta.attachmentvalue==''}">,<bean:message key="attach_downloads"/>: ${atta.downloads}</c:if><c:if test="${atta.readperm>0}">,<bean:message key="threads_readperm"/>: ${atta.readperm}</c:if><c:if test="${atta.price>0}">,<bean:message key="magics_price"/>: ${atta.price}&nbsp;[<a href="misc.jsp?action=viewattachpayments&aid=${atta.aid}" target="_blank"><bean:message key="pay_view"/></a>]&nbsp;<c:if test="${atta.isprice==5}">[<a href="misc.jsp?action=attachpay&aid=${atta.aid}" target="_blank"><bean:message key="attachment_buy"/></a>]</c:if></c:if><c:if test="${atta.description!=''}"><br>${atta.description}</c:if></p>
													<c:if test="${showimag && settings.attachimgpost==1 && atta.attachmentvalue!='' && (readaccess >= atta.readperm || post.key.authorid==jsprun_uid)}">
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
					</div>
				</div>
			</c:forEach>
			</c:forEach>
			</c:when>
			<c:otherwise><div class="specialpost"><div class="postmessage"><bean:message key="none"/></div></div></c:otherwise>
		</c:choose>
	</form>
</div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>