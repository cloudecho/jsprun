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
			</td>
			<td id="main_layout1">
				<table class="module" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td class="header">
							<div class="title">
								<bean:message key="thread"/>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div id="module_mythreads">
								<table cellspacing="0" cellpadding="0" width="100%">
									<tr class="list_category">
										<td class="subject">
											<bean:message key="subject"/>
										</td>
										<td class="forum">
											<bean:message key="forum_name"/>
										</td>
										<td class="views">
											<bean:message key="reply_see"/>
										</td>
										<td class="lastpost">
											<bean:message key="a_post_threads_lastpost"/>
										</td>
									</tr>
									<c:forEach items="${mythread}" var="threads">
										<tr>
										<td class="subject">
											<a href="viewthread.jsp?tid=${threads.tid}" target="_blank">
											<c:choose>
												<c:when test="${threads.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
												<c:when test="${threads.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
												<c:when test="${threads.special==3}">
													<c:choose>
														<c:when test="${threads.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
														<c:when test="${threads.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
													</c:choose>
												</c:when>
												<c:when test="${threads.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
												<c:when test="${threads.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
												<c:when test="${threads.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
											</c:choose>
											<c:if test="${threads.isattc}">
												<img src="images/attachicons/common.gif" border="0" alt="<bean:message key="attachment"/>" />
											</c:if>
											${threads.subjcet}
											<c:if test="${threads.isnew}">
												<a href="redirect.jsp?tid=${threads.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /> </a>
											</c:if>
											</a>
										</td>
										<td class="forum"><a href="forumdisplay.jsp?fid=${threads.fid}" target="_blank">${threads.forums}</a></td>
										<td class="views">
											${threads.replaynum}/${threads.viewnum} 
										</td>
										<td class="lastpost">
											<a target="_blank" href="redirect.jsp?tid=${threads.tid}&amp;goto=lastpost#lastpost"><jrun:showTime timeInt="${threads.lastpost}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></a>
											<br />by<a href="space.jsp?username=<jrun:encoding value="${threads.lastposter}"/>" target="_blank">${threads.lastposter}</a>
										</td>
									</tr>
									</c:forEach>
								</table>
								<div class="line"></div>
										
						<c:if test="${logpage.totalSize > 10}">
							<div class="p_bar">
								<a class="p_total">&nbsp;${logpage.totalSize}&nbsp;</a>
								<a class="p_pages">&nbsp;${logpage.currentPage}/${logpage.totalPage}&nbsp;</a>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="space.jsp?action=mythreads&uid=${member.uid}&amp;page=1" class="p_pages">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="space.jsp?action=mythreads&uid=${member.uid}&amp;page=${logpage.prePage}" class="p_redirect">&lsaquo;&lsaquo;</a>
								</c:if>
								<c:choose>
									<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
										
										<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
											<c:choose>
												<c:when test="${logpage.currentPage == num}">
													<a class="p_curpage">${logpage.currentPage}</a>
												</c:when>
												<c:otherwise>
													<a href="space.jsp?action=mythreads&uid=${member.uid}&amp;page=${num}" class="p_num">${num}</a>
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
															<a href="space.jsp?action=mythreads&uid=${member.uid}&amp;page=${num}" class="p_num">${num}</a>
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
																	<a href="space.jsp?action=mythreads&uid=${member.uid}&amp;page=${num}" class="p_num">${num}</a>
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
																	<a href="space.jsp?action=mythreads&uid=${member.uid}&amp;page=${num}" class="p_num">${num}</a>
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
									<a href="space.jsp?action=mythreads&uid=${member.uid}&amp;page=${logpage.nextPage}" class="p_redirect">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a href="space.jsp?action=mythreads&uid=${member.uid}&amp;page=${logpage.totalPage}" class="p_pages">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"  onkeydown="if(event.keyCode==13) {window.location='space.jsp?action=mythreads&uid=${member.uid}&amp;page='+this.value; return false;}" />
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
