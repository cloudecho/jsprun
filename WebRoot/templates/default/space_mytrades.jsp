<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="space_header.jsp" />
<div class="outer">
	<table class="main" border="0" cellspacing="0">
		<tr>
			<td id="main_layout0">
				<jsp:include flush="true" page="space_module_userinfo.jsp" />
				<table class="module" cellpadding="0" cellspacing="0" border="0">
					<tr><td class="header"><div class="title"><bean:message key="mytradetypes"/></div></td></tr>
					<tr>
						<td>
							<div id="module_userinfo">
								<ul>
									<li><a href="space.jsp?uid=${member.uid}&amp;action=mytrades"><bean:message key="space_trade_home"/></a></li>
									<li><a href="space.jsp?uid=${member.uid}&amp;action=mytrades&amp;tradetypeid=all"><bean:message key="space_trade_all"/></a></li>
									<li><a href="space.jsp?uid=${member.uid}&amp;action=mytrades&amp;tradetypeid=stick"><bean:message key="space_trade_stick"/></a></li>
									<li><a href="space.jsp?uid=${member.uid}&amp;action=mytrades&amp;tradetypeid=0"><bean:message key="space_trade_nonetype"/></a></li>
								</ul>
							</div>
						</td>
					</tr>
				</table>
				<table class="module" cellpadding="0" cellspacing="0" border="0">
					<tr><td class="header"><div class="title"><bean:message key="tradeinfo"/></div></td></tr>
					<tr>
						<td> <div id="module_userinfo"> <div class="more"> ${tradeinfo} </div> </div> </td>
					</tr>
				</table>
			</td>
			<td id="main_layout1">
				<table class="module" cellpadding="0" cellspacing="0" border="0">
					<tr><td class="header"><div class="title"><bean:message key="mytrades"/></div></td></tr>
					<tr>
						<td>
							<div id="module_mytrades">
							<c:forEach items="${trades}" var="shop" varStatus="num">
								<div style="float: left;width: 30%; margin:5px;text-align: center">
									<table cellspacing="0" cellpadding="0" style="width: 80%">
										<tr>
											<td height="100" align="center" valign="top"><a href="viewthread.jsp?do=tradeinfo&amp;tid=${shop.tid}&amp;pid=${shop.pid}" target="_blank"> <img border="0" src="${shop.attachment}" onload="thumbImg(this)" width="96" height="96" alt="${shop.subject}" /> </a></td>
										</tr>
									</table>
									<div class="item" style="height: 100px">
										<a class="subject" href="viewthread.jsp?do=tradeinfo&amp;tid=${shop.tid}&amp;pid=${shop.pid}" target="_blank">${shop.subject}</a>
										<c:if test="${shop.costprice>0.0}">
										<br />
										<bean:message key="post_trade_costprice"/>:
										<span style="text-decoration: line-through">${shop.costprice}</span> <bean:message key="rmb_yuan"/>
										</c:if>
										<br />
										<bean:message key="post_trade_price"/>:
										<span class="price">${shop.price}</span> <bean:message key="rmb_yuan"/>
										<br />
										<c:choose>
											<c:when test="${shop.closed>0}"><span class="expiration"><bean:message key="trade_timeout"/></span></c:when>
											<c:when test="${shop.expiration>0}"><span class="expiration"><bean:message key="trade_remain"/> ${shop.expiration}<bean:message key="ipban_days"/></span></c:when>
											<c:when test="${shop.expiration==-1}"><span class="expiration"><bean:message key="trade_timeout"/></span></c:when>
										</c:choose>
									</div>
								</div>
								<c:if test="${num.count%3==0}">
									<br>
								</c:if>
							</c:forEach>
							</div>
						</td>
					</tr>
				</table>
				<table class="module" cellpadding="0" cellspacing="0" border="0" style="display:${empty mytrades?'none':''}">
					<tr><td class="header"><div class="title"><bean:message key="mycounters"/></div></td></tr>
					<tr>
						<td>
							<div id="module_mythreads">
								<table cellspacing="0" cellpadding="0" width="100%">
									<tr class="list_category">
										<td class="subject"><bean:message key="subject"/></td>
										<td class="forum"><bean:message key="forum_name"/></td>
										<td class="views"><bean:message key="reply_see"/></td>
									</tr>
									<c:forEach items="${mytrades}" var="trades">
										<tr>
											<td class="subject"><a href="viewthread.jsp?tid=${trades.tid}" target="_blank"> ${trades.subjcet}</a></td>
											<td class="forum"><a href="forumdisplay.jsp?fid=${trades.fid}" target="_blank">${trades.forums}</a></td>
											<td class="views">${trades.replaynum} / ${trades.viewnum}</td>
										</tr>
									</c:forEach>
								</table>
								<div class="line"></div>
								
								<c:if test="${logpage.totalSize > 10}">
									<div class="p_bar">
										<a class="p_total">&nbsp;${logpage.totalSize}&nbsp;</a>
										<a class="p_pages">&nbsp;${logpage.currentPage}/${logpage.totalPage}&nbsp;</a>
										
										<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
											<a href="space.jsp?action=mytrades&uid=${member.uid}&amp;page=1" class="p_pages">1 ...</a>
										</c:if>
										
										<c:if test="${logpage.currentPage != logpage.prePage}">
											<a href="space.jsp?action=mytrades&uid=${member.uid}&amp;page=${logpage.prePage}" class="p_redirect">&lsaquo;&lsaquo;</a>
										</c:if>
										<c:choose>
											<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
												
												<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
													<c:choose>
														<c:when test="${logpage.currentPage == num}">
															<a class="p_curpage">${logpage.currentPage}</a>
														</c:when>
														<c:otherwise>
															<a href="space.jsp?action=mytrades&uid=${member.uid}&amp;page=${num}" class="p_num">${num}</a>
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
																	<a href="space.jsp?action=mytrades&uid=${member.uid}&amp;page=${num}" class="p_num">${num}</a>
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
																			<a href="space.jsp?action=mytrades&uid=${member.uid}&amp;page=${num}" class="p_num">${num}</a>
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
																			<a href="space.jsp?action=mytrades&uid=${member.uid}&amp;page=${num}" class="p_num">${num}</a>
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
											<a href="space.jsp?action=mytrades&uid=${member.uid}&amp;page=${logpage.nextPage}" class="p_redirect">&rsaquo;&rsaquo;</a>
										</c:if>
										
										<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
											<a href="space.jsp?action=mytrades&uid=${member.uid}&amp;page=${logpage.totalPage}" class="p_pages">... ${logpage.totalPage}</a>
										</c:if>
										
										<c:if test="${logpage.totalPage>10}">
											<kbd>
												<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='space.jsp?action=mytrades&uid=${member.uid}&amp;page='+this.value; return false;}" />
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