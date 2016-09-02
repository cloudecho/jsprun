<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="space_header.jsp" />
<div class="outer">
	<table class="main" border="0" cellspacing="0">
		<tr>
			<td id="main_layout0">
				<jsp:include flush="true" page="space_module_userinfo.jsp" />
				<table class="module" cellpadding="0" cellspacing="0" border="0">
					<tr><td><jsp:include flush="true" page="space_module_calendar.jsp" /></td></tr>
				</table>
				<c:if test="${jsprun_uid==member.uid && usergroups.allowuseblog>0}">
					<table class="module" cellpadding="0" cellspacing="0" border="0">
						<tr><td class="header"><div class="title"><bean:message key="postblog"/></div></td></tr>
						<tr>
							<td>
								<div id="module_postblog">
									<form method="get" action="post.jsp">
										<input type="hidden" name="action" value="newthread">
										<input type="hidden" name="isblog" value="yes"> <br />
										<select name="fid" style="width: 92%">${forumselect}</select>
										<br /> <br />
										<input class="button" type="submit" value="<bean:message key="submit"/>">
										<br /> <br />
									</form>
								</div>
							</td>
						</tr>
					</table>
				</c:if>
				<table class="module" cellpadding="0" cellspacing="0" border="0">
					<tr><td class="header"><div class="title"><bean:message key="hotblog"/></div></td></tr>
					<tr>
						<td>
							<div id="module_hotblog">
								<ul>
									<c:forEach items="${blogthread}" var="blogs">
										<li><a href="blog.jsp?tid=${blogs.tid}" target="_blank" title="${blogs.views} <bean:message key="view"/>, ${blogs.replies} <bean:message key="blog_comments"/>">${blogs.subject}</a></li>
									</c:forEach>
								</ul>
							</div>
						</td>
					</tr>
				</table>
				<table class="module" cellpadding="0" cellspacing="0" border="0">
					<tr><td class="header"><div class="title"><bean:message key="lastpostblog"/></div></td></tr>
					<tr>
						<td>
							<div id="module_hotblog">
								<ul>
									<c:forEach items="${repblog}" var="blogs">
										<li><a href="blog.jsp?tid=${blogs.tid}" target="_blank" title="${blogs.views} <bean:message key="view"/>, ${blogs.replies} <bean:message key="blog_comments"/>">${blogs.subject}</a></li>
									</c:forEach>
								</ul>
							</div>
						</td>
					</tr>
				</table>
			</td>
			<td id="main_layout1">
				<table class="module" cellpadding="0" cellspacing="0" border="0">
					<tr><td class="header"><div class="title"><bean:message key="myblogs"/></div></td></tr>
					<tr>
						<td>
							<div id="module_myblogs">
								<div class="center">
									<c:forEach items="${myblogs}" var="blogs">
										<div class="center_subject">
											<ul>
												<li>
													<a target="_blank" href="blog.jsp?tid=${blogs.tid}">
														<c:choose>
															<c:when test="${blogs.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
															<c:when test="${blogs.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
															<c:when test="${blogs.special==3}">
																<c:choose>
																	<c:when test="${blogs.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																	<c:when test="${blogs.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																</c:choose>
															</c:when>
															<c:when test="${blogs.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
															<c:when test="${blogs.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
															<c:when test="${blogs.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
														</c:choose>
														<c:if test="${blogs.isattc}">
															<img src="images/attachicons/common.gif" border="0" alt="<bean:message key="attachment"/>" />
														</c:if>
														${blogs.subject}<c:if test="${blogs.isnew}"><a href="redirect.jsp?tid=${blogs.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /> </a></c:if> </a>
												</li>
											</ul>
										</div>
										<div class="center_lastpost"><a href="forumdisplay.jsp?fid=${blogs.fid}" target="_blank">${blogs.forums}</a> | ${blogs.operdate}</div>
										<div class="center_message">
											${blogs.message}
										</div>
										<div class="center_views">
											<c:if test="${jsprun_uid==member.uid}">
												<a target="_blank" href="post.jsp?action=edit&amp;fid=${blogs.fid}&amp;tid=${blogs.tid}&amp;pid=${blogs.pid}"><bean:message key="edit"/></a> |	
											</c:if> <bean:message key="view"/>(${blogs.viewnum}) | <a target="_blank" href="blog.jsp?tid=2"><bean:message key="blog_comments"/>(${blogs.replicenum})</a> |
											<a target="_blank" href="my.jsp?item=favorites&amp;tid=${blogs.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite_${blogs.tid}" onclick="ajaxmenu(event, this.id)"><bean:message key="thread_favorite"/></a>
										</div>
									</c:forEach>
								</div>
								<div class="line"></div>
										
						<c:if test="${logpage.totalSize > 10}">
							<div class="p_bar">
								<a class="p_total">&nbsp;${logpage.totalSize}&nbsp;</a>
								<a class="p_pages">&nbsp;${logpage.currentPage}/${logpage.totalPage}&nbsp;</a>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="space.jsp?action=myblogs&uid=1&begin=${begintime}&end=${endtime}&amp;page=1" class="p_pages">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="space.jsp?action=myblogs&uid=1&begin=${begintime}&end=${endtime}&amp;page=${logpage.prePage}" class="p_redirect">&lsaquo;&lsaquo;</a>
								</c:if>
								<c:choose>
									<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
										
										<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
											<c:choose>
												<c:when test="${logpage.currentPage == num}">
													<a class="p_curpage">${logpage.currentPage}</a>
												</c:when>
												<c:otherwise>
													<a href="space.jsp?action=myblogs&uid=1&begin=${begintime}&end=${endtime}&amp;page=${num}" class="p_num">${num}</a>
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
															<a href="space.jsp?action=myblogs&uid=1&begin=${begintime}&end=${endtime}&amp;page=${num}" class="p_num">${num}</a>
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
																	<a href="space.jsp?action=myblogs&uid=1&begin=${begintime}&end=${endtime}&amp;page=${num}" class="p_num">${num}</a>
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
																	<a href="space.jsp?action=myblogs&uid=1&begin=${begintime}&end=${endtime}&amp;page=${num}" class="p_num">${num}</a>
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
									<a href="space.jsp?action=myblogs&uid=1&begin=${begintime}&end=${endtime}&amp;page=${logpage.nextPage}" class="p_redirect">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a href="space.jsp?action=myblogs&uid=1&begin=${begintime}&end=${endtime}&amp;page=${logpage.totalPage}" class="p_pages">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"  onkeydown="if(event.keyCode==13) {window.location='space.jsp?action=myblogs&uid=1&begin=${begintime}&end=${endtime}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<jsp:include flush="true" page="space_footer.jsp" />
