<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jrun:include value="/forumdata/cache/style_${styleid}.jsp" defvalue="/forumdata/cache/style_${settings.styleid}.jsp"/>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<div class="box specialpostcontainer">
	<ul class="tabs">
		<li ${param['do']==null || param.do=='viewall'?"class=current":""}><a href="viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewall&rand='+Math.random()" onclick="ajaxget(this.href, 'ajaxspecialpost', 'ajaxspecialpost');doane(event);"><bean:message key="all"/></a></li>
		<li ${param['do']=='viewpost'?"class=current":""}><a href="viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewpost&rand='+Math.random()" onclick="ajaxget(this.href, 'ajaxspecialpost', 'ajaxspecialpost');doane(event);"><bean:message key="trade_viewreply"/></a></li>
		<li ${param['do']=='viewtrade'?"class=current":""}><a href="viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewtrade&rand='+Math.random()" onclick="ajaxget(this.href, 'ajaxspecialpost', 'ajaxspecialpost');doane(event);"><bean:message key="trade_viewtrade"/></a></li>
		<li ${param['do']=='viewfirstpost'?"class=current":""}><a href="viewthread.jsp?fid=${thread.fid}&tid=${thread.tid}&do=viewfirstpost&rand='+Math.random()" onclick="ajaxget(this.href, 'ajaxspecialpost', 'ajaxspecialpost');doane(event);"><bean:message key="post_trade_aboutcounter"/></a></li>
		<li id="ad_relatedtrade" style="display: none"${param['do']=='viewrelatedtrade'?"class=current":""}></li>
	</ul>
	<form method="post" name="modactions">
		<c:choose>
			<c:when test="${param.do!='viewrelatedtrade'&&!empty postlist}">
				<c:forEach items="${postlist}" var="post" varStatus="in">
					<div class="specialpost">
					 	<div class="postinfo">
							<h2><a class="dropmenu" href="space.jsp?uid=${post.authorid}" style="font-weight: normal;" target="_blank" onclick="ajaxmenu(event, this.id, 0, '', 1, 3, 0)" id="author_${post.pid}">${post.author}</a><jrun:showTime timeInt="${post.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></h2>
							<strong title="<bean:message key="post_copylink"/>" id="postnum_${post.pid}" onclick="setcopy('${boardurl}viewthread.jsp?tid=${post.tid}&page=${currentPage}${fromuid}#pid${post.pid}', '<bean:message key="post_copied"/>')">
								<c:choose>
									<c:when test="${currentPage>1}"> ${empty postcustom[in.count+lpp*(currentPage-1)] ||  param.do!='viewpost'? lpp*(currentPage-1)+in.count : postcustom[in.count+lpp*(currentPage-1)]}${empty postcustom[in.count+(currentPage-1)*lpp]||param.do!='viewpost' ? settings.postno : ""}</c:when>
									<c:otherwise>${empty postcustom[in.count]||param.do!='viewpost' ? in.count : postcustom[in.count]} ${empty postcustom[in.count] || param.do!='viewpost'? settings.postno : ""}</c:otherwise>
								</c:choose>	
							</strong>
							<c:if test="${(usergroups.alloweditpost>0 && modertar&&(post.adminid<=0 ||jsprun_adminid<=post.adminid)) || (thread.alloweditpost>0 && thread.authorid==jsprun_uid)}">&nbsp;<a href="post.jsp?action=edit&fid=${thread.fid}&tid=${thread.tid}&pid=${post.pid}&page=1&extra=${extra}"><bean:message key="edit"/></a></c:if>
							<c:if test="${allowpostreply}"><a href="post.jsp?action=reply&fid=${thread.fid}&tid=${thread.tid}&repquote=${post.pid}&extra=${extra}&page=1"><bean:message key="reply_quote"/></a></c:if>
							<c:if test="${modertar && usergroups.allowdelpost>0}"><input class="checkbox" type="checkbox" name="topiclist[]" value="${post.pid}" /></c:if>
						</div>
						<div class="postmessage">
							<c:if test="${!empty post.subject}"><h2>${post.subject}</h2></c:if>
							 <c:if test="${trademap[post.pid]!=null}">
								<c:set var="trade" value="${trademap[post.pid]}"/>
								<div class="tradeinfo">
									<div class="tradethumb">
										<c:choose>
											<c:when test="${attachmap[trade.pid]!=null}">
											<a href="###"><img src="${attachmap[trade.pid]}" onclick="zoom(this, '${attachmap[trade.pid]}')" onload="thumbImg(this)" width="${settings.tradeimagewidth}" height="${settings.tradeimageheight}" alt="${trade.subject}" /></a></c:when>
											<c:otherwise><img src="${styles.IMGDIR}/trade_nophoto.gif" alt="${trade.subject}" /></c:otherwise>
										</c:choose>
										<br /><a href="viewthread.jsp?do=tradeinfo&tid=${thread.tid}&pid=${post.pid}" target="_blank"><em><bean:message key="trade_fullinfo"/></em></a>
									</div>
									<div class="tradeattribute" style="width: 40%">
									<h3><c:if test="${trade.typeid>0}"><em>${tradetypes[trade.typeid]}</em> </c:if>${trade.subject}</h3>
									<dl>
									<c:if test="${trade.costprice>0.0}"><dt><bean:message key="trade_costprice"/>:</dt><dd><del>${trade.costprice} <bean:message key="rmb_yuan"/></del></dd></c:if>
									<dt><bean:message key="trade_price"/>:</dt>
									<dd><strong>${trade.price}</strong>&nbsp;<bean:message key="rmb_yuan"/></dd>
									<dt><bean:message key="trade_transport"/>:</dt>
									<dd>
										<c:if test="${trade.transport==1}"><bean:message key="post_trade_transport_seller"/></c:if>
										<c:if test="${trade.transport==2 || trade.transport==4}">
											<c:if test="${trade.transport==4}"><bean:message key="post_trade_transport_physical"/></c:if>
											<c:choose>
												<c:when test="${trade.ordinaryfee>0 || trade.expressfee>0 || trade.emsfee>0}">
													<c:if test="${trade.ordinaryfee>0}"><bean:message key="post_trade_transport_mail"/>${trade.ordinaryfee}<bean:message key="rmb_yuan"/></c:if>
													<c:if test="${trade.expressfee>0}"><bean:message key="post_trade_transport_express"/>${trade.expressfee}<bean:message key="rmb_yuan"/></c:if>
													<c:if test="${trade.emsfee>0}">EMS${trade.emsfee}<bean:message key="rmb_yuan"/></c:if>
												</c:when>
												<c:when test="${trade.transport==2}">
													<bean:message key="post_trade_transport_none"/>
												</c:when>
											</c:choose>
										</c:if>
									<c:if test="${trade.transport==3}"><bean:message key="post_trade_transport_virtual"/></c:if>
									</dd>
									<dt><bean:message key="num"/>:</dt><dd>${trade.amount}</dd>
									</dl>
									<c:if test="${post.authorid!=jsprun_uid && thread.closed==0 && trade.closed==0}">
										<c:if test="${trade.amount>0}"><a href="###" onclick="window.open('trade.jsp?tid=${thread.tid}&pid=${post.pid}','','')"><img src="${styles.IMGDIR}/trade_buy_${sessionScope['org.apache.struts.action.LOCALE']}.gif" border="0" /></a> &nbsp;</c:if>
										<a href="###" onclick="window.open('pm.jsp?action=send&uid=${trade.sellerid}&tradepid=${post.pid}','','')"><img src="${styles.IMGDIR}/trade_pm_${sessionScope['org.apache.struts.action.LOCALE']}.gif" border="0" /></a>
									</c:if>
									</div>
								</div>
							</c:if>
							<c:choose>
								<c:when test="${jsprun_adminid!=1 && settings.bannedmessages!=0 && (post.author==null || post.groupid==4 || post.groupid==5)}"><div class="notice" style="width: 500px"><bean:message key="message_banned"/></div></c:when>
								<c:when test="${jsprun_adminid!=1 && post.status==1}"><div class="notice" style="width: 500px"> <bean:message key="message_single_banned"/></div></c:when>
								<c:otherwise>
									<div class="t_msgfont">${post.message}</div>
									<c:if test="${post.attachment>0}">
										<c:choose>
											<c:when test="${!allowgetattach}"><div class="notice" style="width: 500px"> <bean:message key="attachment"/>: <em><bean:message key="attach_nopermission"/></em> </div></c:when>
											<c:otherwise>
												<div class="box postattachlist">
													<h4><bean:message key="attachment"/></h4>
													<c:forEach items="${attatrademap[post.pid]}" var="atta">
													<dl class="t_attachlist">
														<dt>
															<img src="images/attachicons/${attatype[atta.filetype]}" border="0" class="absmiddle" alt="" />
															<a href="attachment.jsp?aid=${atta.aid}">${atta.filename}</a><em>(<jrun:showFileSize size="${atta.filesize}"/>)</em>
														</dt>
														<dd>
															<p><jrun:showTime timeInt="${atta.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/><c:if test="${settings.attachrefcheck==0 && atta.isimage<1}">,<bean:message key="attach_downloads"/>: ${atta.downloads}</c:if><c:if test="${atta.readperm>0}">,<bean:message key="threads_readperm"/>: ${atta.readperm}</c:if><c:if test="${atta.price>0}">,<bean:message key="magics_price"/>: ${atta.price}&nbsp;[<a href="misc.jsp?action=viewattachpayments&aid=${atta.aid}" target="_blank"><bean:message key="pay_view"/></a>]&nbsp;<c:if test="${atta.isprice==5}">[<a href="misc.jsp?action=attachpay&aid=${atta.aid}" target="_blank"><bean:message key="attachment_buy"/></a>]</c:if></c:if><c:if test="${atta.description!=''}"><br>${atta.description}</c:if></p>
															<c:if test="${showimag && settings.attachimgpost==1 && atta.isimage>0 && (readaccess >= atta.readperm || atta.authorid==jsprun_uid)}">
																<c:choose>
																	<c:when test="${settings.attachrefcheck==0}"><p><a href="#zoom"><img onclick="zoom(this, '${atta.attachment}')" src="${atta.attachmentvalue}" alt="${atta.filename}" /></a> </p></c:when>
																	<c:otherwise>
																		<c:choose>
																		<c:when test="${atta.thumb==1}"><p><a href="#zoom"><img onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" src="attachment.jsp?aid=${atta.aid}&noupdate=yes" alt="${atta.filename}" /></a></p></c:when>
																		<c:otherwise><p> <img src="attachment.jsp?aid=${atta.aid}&noupdate=yes" border="0" onload="attachimg(this, 'load')" onmouseover="attachimg(this, 'mouseover')" onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" alt="01.gif" /> </p></c:otherwise>
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
			</c:when>
			<c:when test="${param['do']=='viewrelatedtrade'}"><div class="specialpost"><div class="postmessage" id="relatedtrades"></div></div>${relatedtrades}</c:when>
			<c:otherwise><div class="specialpost"><div class="postmessage"><bean:message key="none"/></div></div></c:otherwise>
		</c:choose>
	</form>
</div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>