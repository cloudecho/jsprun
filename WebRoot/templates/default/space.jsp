<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="space_header.jsp" />
		<div class="outer">
			<table class="main" border="0" cellspacing="0">
				<tr>
					<c:if test="${memberspace.side!=2}">
					<td id="main_layout0">
						<c:forEach items="${layoutlist1}" var="left">
							<c:if test="${left=='userinfo'}">
								<jsp:include flush="true" page="space_module_userinfo.jsp" />
							</c:if>
							<c:if test="${left=='calendar'}">
								<jsp:include flush="true" page="space_module_calendar.jsp" />
							</c:if>
							<c:if test="${left=='myreplies'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="threads_replies"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myreplies&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myreplies">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap1.myreplies}" var="replies">
															<li><a href="viewthread.jsp?tid=${replies.tid}" target="_blank">
																	<c:choose>
																		<c:when test="${replies.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
																		<c:when test="${replies.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
																		<c:when test="${replies.special==3}">
																			<c:choose>
																				<c:when test="${replies.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																				<c:when test="${replies.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																			</c:choose>
																		</c:when>
																		<c:when test="${replies.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
																		<c:when test="${replies.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
																		<c:when test="${replies.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
																	</c:choose> 
																	<c:if test="${replies.isattc}">
																		<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																	</c:if>${replies.subject}<c:if test="${replies.isnew}">
																		<a href="redirect.jsp?tid=${replies.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /> </a>
																	</c:if> </a>
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myfavforums'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="myfavforums"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myfavforums&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myfavforums">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap1.myfavforums}" var="forums">
															<li><a href="forumdisplay.jsp?fid=${forums.fid}" target="_blank">${forums.name}</a></li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myfriends'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="myfriends"/>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myfriends">
												<c:forEach items="${layoutMap1.myfriends}" var="myfried">
													<div class="friend">
														<ul>
															<li>
																<a href="space.jsp?uid=${myfried.uid}" target="_blank">${myfried.username}</a>
															</li>
														</ul>
													</div>
													<div class="space">
														<a href="space.jsp?uid=${myfried.uid}" target="_blank">${myfried.username}<bean:message key="space_userspace"/></a>
													</div>
												</c:forEach>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myfavthreads'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="myfavthreads"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myfavthreads&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myfavthreads">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap1.myfavthreads}" var="mythread">
															<li>
															<a href="viewthread.jsp?tid=${mythread.tid}" target="_blank">
															<c:choose>
															<c:when test="${mythread.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
															<c:when test="${mythread.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
															<c:when test="${mythread.special==3}">
																<c:choose>
																	<c:when test="${mythread.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																	<c:when test="${mythread.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																</c:choose>
															</c:when>
															<c:when test="${mythread.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
															<c:when test="${mythread.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
															<c:when test="${mythread.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
														</c:choose>
														 <c:if test="${mythread.attachment>0}">
															<img src="images/attachicons/common.gif" border="0" alt="<bean:message key="attachment"/>" />
														</c:if> ${mythread.subject}
														<c:if test="${mythread.lastpost>member.lastvisit}">
															<a href="redirect.jsp?tid=${mythread.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /> </a>
														</c:if> </a> (${mythread.replies})
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myvideos'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="thread_video"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myvideos&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myvideos">
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myblogs'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="myblogs"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myblogs&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myblogs">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap1.myblogs}" var="myblogs">
															<li>
																<a href="viewthread.jsp?tid=${myblogs.tid}" target="_blank">
																<c:choose>
																<c:when test="${myblogs.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
																<c:when test="${myblogs.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
																<c:when test="${myblogs.special==3}">
																	<c:choose>
																		<c:when test="${myblogs.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																		<c:when test="${myblogs.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																	</c:choose>
																</c:when>
																<c:when test="${myblogs.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
																<c:when test="${myblogs.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
																<c:when test="${myblogs.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
																</c:choose> 
																<c:if test="${myblogs.isattc}">
																	<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																</c:if>${myblogs.subject}
																<c:if test="${myblogs.isnew}">
																		<a href="redirect.jsp?tid=${myblogs.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /></a>
																</c:if> </a>
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='mythreads'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="thread"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=mythreads&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_mythreads">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap1.mythreads}" var="mythreads">
															<li>
																<a href="viewthread.jsp?tid=${mythreads.tid}" target="_blank">
																	<c:choose>
																	<c:when test="${mythreads.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
																	<c:when test="${mythreads.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
																	<c:when test="${mythreads.special==3}">
																		<c:choose>
																			<c:when test="${mythreads.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																			<c:when test="${mythreads.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																		</c:choose>
																	</c:when>
																	<c:when test="${mythreads.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
																	<c:when test="${mythreads.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
																	<c:when test="${mythreads.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
																	</c:choose> 
																	<c:if test="${mythreads.isattc}">
																		<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																	</c:if>${mythreads.subject}<c:if test="${mythreads.isnew}">
																		<a href="redirect.jsp?tid=${mythreads.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /></a>
																	</c:if> </a>
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myrewards'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="thread_reward"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myrewards&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myrewards">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap1.myrewards}" var="myrewards">
															<li>
															<a href="viewthread.jsp?tid=${myrewards.tid}" target="_blank">
																	<c:if test="${myrewards.isattc}">
																	<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																	</c:if>${myrewards.subject}<a href="redirect.jsp?tid=${myrewards.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" />
																</a> </a>
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='mytrades'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="mytrades"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=mytrades&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_mytrades">
												<c:forEach items="${layoutMap1.mytrades}" var="mytrades">
													<div class="item">
														<span class="side_price"><span class="price">${mytrades.price}</span> <bean:message key="rmb_yuan"/></span>
														<a class="side_subject" href="viewthread.jsp?do=tradeinfo&amp;tid=${mytrades.tid}&amp;pid=${mytrades.pid}" target="_blank">${mytrades.subject}</a>
														<br />
													</div>
												</c:forEach>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
						</c:forEach>
					</td>
					</c:if>
					<td id="main_layout1">
						<c:forEach items="${layoutlist2}" var="right">
							<c:if test="${right=='myblogs'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="myblogs"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myblogs&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myblogs">
												<div class="center">
													<c:forEach items="${layoutMap2.myblogs}" var="blog">
														<div class="center_subject">
															<ul>
																<li>
																	<a target="_blank" href="blog.jsp?tid=${blog.tid}">
																	<c:choose>
																	<c:when test="${blog.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
																	<c:when test="${blog.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
																	<c:when test="${blog.special==3}">
																		<c:choose>
																			<c:when test="${blog.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																			<c:when test="${blog.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																		</c:choose>
																	</c:when>
																	<c:when test="${blog.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
																	<c:when test="${blog.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
																	<c:when test="${blog.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
																	</c:choose>
																		<c:if test="${blog.isattc}">
																			<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																		</c:if>${blog.subject}</a>
																		<c:if test="${blog.isnew}">
																			<a href="redirect.jsp?tid=${myblogs.tid}&amp;goto=newpost#newpost" target="_blank"> <img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /> </a>
																		</c:if>
																</li>
															</ul>
														</div>
														<div class="center_lastpost"><a href="forumdisplay.jsp?fid=${blog.fid}" target="_blank">${blog.forums}</a>  | ${blog.operdate}</div>
														<div class="center_message">
															${blog.message}
														</div>
														<div class="center_views">
															<a target="_blank" href="post.jsp?action=edit&amp;fid=${blog.fid}&amp;tid=${blog.tid}&amp;pid=${blog.pid}"><bean:message key="edit"/></a> | <bean:message key="view"/>(${blog.viewnum}) |
															<a target="_blank" href="blog.jsp?tid=${blog.tid}"><bean:message key="blog_comments" />(${blog.replicenum})</a> | <a target="_blank" href="my.jsp?item=favorites&amp;tid=${blog.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite_b${blog.tid}" onclick="ajaxmenu(event, this.id)"><bean:message key="thread_favorite"/></a>
														</div>
													</c:forEach>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${right=='myreplies'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="threads_replies"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myreplies&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myreplies">
												<div class="center">
													<c:forEach items="${layoutMap2.myreplies}" var="myreplies">
														<div class="center_subject">
															<ul>
																<li>
																<a href="viewthread.jsp?tid=${myreplies.tid}" target="_blank">
																	<c:choose>
																		<c:when test="${myreplies.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
																		<c:when test="${myreplies.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
																		<c:when test="${myreplies.special==3}">
																			<c:choose>
																				<c:when test="${myreplies.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																				<c:when test="${myreplies.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																			</c:choose>
																		</c:when>
																		<c:when test="${myreplies.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
																		<c:when test="${myreplies.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
																		<c:when test="${myreplies.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
																	</c:choose>
																	 <c:if test="${myreplies.isattc}">
																			<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																		</c:if>${myreplies.subject} <c:if test="${myreplies.isnew}">
																			<a href="redirect.jsp?tid=${myreplies.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /> </a>
																		</c:if> </a>
																</li>
															</ul>
														</div>
														<div class="center_lastpost"><a href="forumdisplay.jsp?fid=${myreplies.fid}" target="_blank">${myreplies.forums}</a>|${myreplies.operdate}</div>
														<div class="center_message">
															${myreplies.message}
														</div>
														<div class="center_views">
															<bean:message key="view"/>(${myreplies.viewnum}) |
															<a target="_blank" href="blog.jsp?tid=${myreplies.tid}"><bean:message key="threads_replies"/>(${myreplies.replicenum})</a>|
															<a target="_blank" href="my.jsp?item=favorites&amp;tid=${myreplies.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite_b${myreplies.tid}" onclick="ajaxmenu(event, this.id)"><bean:message key="thread_favorite"/></a>
														</div>
													</c:forEach>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${right=='mythreads'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="thread"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=mythreads&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_mythreads">
												<div class="center">
													<c:forEach items="${layoutMap2.mythreads}" var="thread">
														<div class="center_subject">
															<ul>
																<li>
																	<a href="viewthread.jsp?tid=${thread.tid}" target="_blank">
																	 <c:choose>
																		<c:when test="${thread.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
																		<c:when test="${thread.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
																		<c:when test="${thread.special==3}">
																			<c:choose>
																				<c:when test="${thread.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																				<c:when test="${thread.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																			</c:choose>
																		</c:when>
																		<c:when test="${thread.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
																		<c:when test="${thread.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
																		<c:when test="${thread.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
																	 </c:choose>
																	  <c:if test="${thread.isattc}">
																			<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																		</c:if>${thread.subject} <c:if test="${thread.isnew}">
																			<a href="redirect.jsp?tid=${thread.tid}&amp;goto=newpost#newpost" target="_blank">
																			<img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" />
																			</a></c:if> </a>
																</li>
															</ul>
														</div>
														<div class="center_lastpost"><a href="forumdisplay.jsp?fid=${thread.fid}" target="_blank">${thread.forums}</a>|<a target="_blank" href="redirect.jsp?tid=${thread.tid}&amp;goto=lastpost#lastpost">${thread.operdate}</a></div>
														<div class="center_message">
															${thread.message}
														</div>
														<div class="center_views">
															<a target="_blank" href="post.jsp?action=edit&amp;fid=${thread.fid}&amp;tid=${thread.tid}&amp;pid=${thread.pid}"><bean:message key="edit"/></a>| <bean:message key="view"/>(${thread.viewnum}) |<a href="viewthread.jsp?tid=${thread.tid}" target="_blank"><bean:message key="threads_replies"/>(${thread.replicenum})</a> |
															<a target="_blank" href="my.jsp?item=favorites&amp;tid=${thread.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite_t2" onclick="ajaxmenu(event, this.id)"><bean:message key="thread_favorite"/></a>
														</div>
													</c:forEach>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${right=='myrewards'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="thread_reward"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myrewards&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myrewards">
												<div class="center">
													<c:forEach items="${layoutMap2.myrewards}" var="wards">
														<div class="center_subject">
															<ul>
																<li>
																<a href="viewthread.jsp?tid=${wards.tid}" target="_blank">
																<c:if test="${wards.isattc}"> <img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" 	border="0" />
																</c:if>${wards.subject}</a>
																</li>
															</ul>
														</div>
														<div class="center_lastpost"><a href="forumdisplay.jsp?fid=${wards.fid}" target="_blank">${wards.forums}</a>|${wards.rewards}</div>
														<div class="center_message">
															${wards.message}
														</div>
														<div class="center_views">
															<bean:message key="view"/>(${wards.viewnum}) |
															<a href="viewthread.jsp?tid=${wards.tid}" target="_blank">
															<bean:message key="threads_replies"/>(${wards.replicenum})</a> | <a target="_blank" href="my.jsp?item=favorites&amp;tid=${wards.tid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite_w${wards.tid}" onclick="ajaxmenu(event, this.id)"><bean:message key="thread_favorite"/></a>
														</div>
													</c:forEach>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${right=='mytrades'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="mytrades"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=mytrades&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_mytrades">
												<c:forEach items="${layoutMap2.mytrades}" var="mytrades">
													<div
														style="float: left;width: 30%; margin:5px;text-align: center">
														<table cellspacing="0" cellpadding="0" style="width: 80%">
															<tr>
																<td height="100" align="center" valign="top">
																	<a href="viewthread.jsp?do=tradeinfo&amp;tid=${mytrades.tid}&amp;pid=${mytrades.pid}" target="_blank"> <img border="0" src="${mytrades.attachment}" onload="thumbImg(this)" width="96" height="96" alt="${mytrades.subject}" /> </a>
																</td>
															</tr>
														</table>
														<div class="item" style="height: 100px">
															<a class="subject" href="viewthread.jsp?do=tradeinfo&amp;tid=${mytrades.tid}&amp;pid=${mytrades.pid}" target="_blank">${mytrades.subject}</a>
															<br /> <c:if test="${mytrades.costprice>0.0}"><bean:message key="post_trade_costprice"/>: <span style="text-decoration: line-through">${mytrades.costprice}</span> <bean:message key="rmb_yuan"/> <br /></c:if> <bean:message key="post_trade_price"/>: <span class="price">${mytrades.price}</span> <bean:message key="rmb_yuan"/> <br /> <c:choose>
														<c:when test="${mytrades.closed>0}">
															<span class="expiration"><bean:message key="trade_timeout"/> </span>
														</c:when>
														<c:when test="${mytrades.expiration>0}">
															<span class="expiration"><bean:message key="trade_remain"/> ${mytrades.expiration}<bean:message key="ipban_days"/></span>
														</c:when>
														<c:when test="${mytrades.expiration==-1}">
															<span class="expiration"><bean:message key="trade_timeout"/> </span>
														</c:when>
														</c:choose>
														</div>
													</div>
												</c:forEach>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
						</c:forEach>
					</td>
					<c:if test="${memberspace.side!=1}">
					<td id="main_layout2">
						<c:forEach items="${layoutlist3}" var="left">
							<c:if test="${left=='userinfo'}">
								<jsp:include flush="true" page="space_module_userinfo.jsp" />
							</c:if>
							<c:if test="${left=='calendar'}">
								<jsp:include flush="true" page="space_module_calendar.jsp" />
							</c:if>
							<c:if test="${left=='myreplies'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="threads_replies"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myreplies&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myreplies">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap3.myreplies}" var="replies">
															<li><a href="viewthread.jsp?tid=${replies.tid}" target="_blank">
																	<c:choose>
																		<c:when test="${replies.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
																		<c:when test="${replies.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
																		<c:when test="${replies.special==3}">
																			<c:choose>
																				<c:when test="${replies.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																				<c:when test="${replies.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																			</c:choose>
																		</c:when>
																		<c:when test="${replies.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
																		<c:when test="${replies.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
																		<c:when test="${replies.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
																	</c:choose> 
																	<c:if test="${replies.isattc}">
																		<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																	</c:if>${replies.subject}<c:if test="${replies.isnew}">
																		<a href="redirect.jsp?tid=${replies.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /> </a>
																	</c:if> </a>
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myfavforums'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="myfavforums"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myfavforums&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myfavforums">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap3.myfavforums}" var="forums">
															<li><a href="forumdisplay.jsp?fid=${forums.fid}" target="_blank">${forums.name}</a></li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myfriends'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="myfriends"/>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myfriends">
												<c:forEach items="${layoutMap3.myfriends}" var="myfried">
													<div class="friend">
														<ul>
															<li>
																<a href="space.jsp?uid=${myfried.uid}" target="_blank">${myfried.username}</a>
															</li>
														</ul>
													</div>
													<div class="space">
														<a href="space.jsp?uid=${myfried.uid}" target="_blank">${myfried.username}<bean:message key="space_userspace"/></a>
													</div>
												</c:forEach>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myfavthreads'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="myfavthreads"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myfavthreads&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myfavthreads">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap3.myfavthreads}" var="mythread">
															<li>
															<a href="viewthread.jsp?tid=${mythread.tid}" target="_blank">
															<c:choose>
															<c:when test="${mythread.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
															<c:when test="${mythread.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
															<c:when test="${mythread.special==3}">
																<c:choose>
																	<c:when test="${mythread.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																	<c:when test="${mythread.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																</c:choose>
															</c:when>
															<c:when test="${mythread.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
															<c:when test="${mythread.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
															<c:when test="${mythread.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
														</c:choose>
														 <c:if test="${mythread.attachment>0}">
															<img src="images/attachicons/common.gif" border="0" alt="<bean:message key="attachment"/>" />
														</c:if> ${mythread.subject}
														<c:if test="${mythread.lastpost>member.lastvisit}">
															<a href="redirect.jsp?tid=${mythread.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /> </a>
														</c:if> </a> (${mythread.replies})
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myvideos'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="thread_video"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myvideos&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myvideos">
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myblogs'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="myblogs"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myblogs&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myblogs">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap3.myblogs}" var="myblogs">
															<li>
																<a href="viewthread.jsp?tid=${myblogs.tid}" target="_blank">
																<c:choose>
																<c:when test="${myblogs.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
																<c:when test="${myblogs.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
																<c:when test="${myblogs.special==3}">
																	<c:choose>
																		<c:when test="${myblogs.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																		<c:when test="${myblogs.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																	</c:choose>
																</c:when>
																<c:when test="${myblogs.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
																<c:when test="${myblogs.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
																<c:when test="${myblogs.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
																</c:choose> 
																<c:if test="${myblogs.isattc}">
																	<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																</c:if>${myblogs.subject}
																<c:if test="${myblogs.isnew}">
																	<a href="redirect.jsp?tid=${myblogs.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /></a>
																</c:if> </a>
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='mythreads'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="thread"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=mythreads&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_mythreads">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap3.mythreads}" var="mythreads">
															<li>
																<a href="viewthread.jsp?tid=${mythreads.tid}" target="_blank">
																	<c:choose>
																	<c:when test="${mythreads.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
																	<c:when test="${mythreads.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
																	<c:when test="${mythreads.special==3}">
																		<c:choose>
																			<c:when test="${mythreads.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when>
																			<c:when test="${mythreads.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when>
																		</c:choose>
																	</c:when>
																	<c:when test="${mythreads.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
																	<c:when test="${mythreads.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
																	<c:when test="${mythreads.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
																	</c:choose> 
																	<c:if test="${mythreads.isattc}">
																		<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																	</c:if>${mythreads.subject}<c:if test="${mythreads.isnew}">
																		<a href="redirect.jsp?tid=${mythreads.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" /></a>
																	</c:if> </a>
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='myrewards'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="thread_reward"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=myrewards&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_myrewards">
												<div class="side">
													<ul>
														<c:forEach items="${layoutMap3.myrewards}" var="myrewards">
															<li>
															<a href="viewthread.jsp?tid=${myrewards.tid}" target="_blank">
																	<c:if test="${myrewards.isattc}">
																	<img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" border="0" />
																	</c:if>${myrewards.subject}<a href="redirect.jsp?tid=${myrewards.tid}&amp;goto=newpost#newpost" target="_blank"><img src="${styles.IMGDIR}/firstnew.gif" border="0" alt="" />
																</a> </a>
															</li>
														</c:forEach>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
							<c:if test="${left=='mytrades'}">
								<table class="module" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td class="header">
											<div class="title">
												<bean:message key="mytrades"/>
											</div>
											<div class="more">
												<a href="space.jsp?action=mytrades&uid=${member.uid}"><bean:message key="more"/></a>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div id="module_mytrades">
												<c:forEach items="${layoutMap3.mytrades}" var="mytrades">
													<div class="item">
														<span class="side_price"><span class="price">${mytrades.price}</span> <bean:message key="rmb_yuan"/></span>
														<a class="side_subject" href="viewthread.jsp?do=tradeinfo&amp;tid=${mytrades.tid}&amp;pid=${mytrades.pid}" target="_blank">${mytrades.subject}</a>
														<br />
													</div>
												</c:forEach>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
						</c:forEach>
					</td>
					</c:if>
				</tr>
			</table>
		</div>
<jsp:include flush="true" page="space_footer.jsp" />