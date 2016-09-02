<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_log_medal" /></td></tr>
</table>
<br />
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableborder">
				<tr class="header">
					<td colspan="3">
						<bean:message key="menu_log_medal" />
					</td>
				</tr>
				<form method="post" action="admincp.jsp?action=medalslog">
					<input type="hidden" name="caction" value="lpp">
				<tr class="altbg2">
					<td width="25%">
						<bean:message key="a_system_logs_lpp" />
					</td>
					<td width="55%">
						<input type="text" name="lpp" size="40" maxlength="40" value="${lpp}">
					</td>
					<td width="20%">
						<input class="button" type="submit" value="<bean:message key="submit" />">
					</td>
				</tr>
				</form>
				<form method="post" action="admincp.jsp?action=medalslog">
					<input type="hidden" name="caction" value="keyword">
				<tr class="altbg2">
					<td>
						<bean:message key="a_system_logs_search" />
					</td>
					<td>
						<input type="text" name="keyword" size="40" value="${keys}">
					</td>
					<td>
						<input class="button" type="submit" value="<bean:message key="submit" />">
					</td>
				</tr>
				</form>
			</table>
			<br />
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableborder">
				<tr class="header">
					<td width="13%">
						<bean:message key="operator" />
					</td>
					<td width="13%">
						<bean:message key="ip" />
					</td>
					<td width="15%">
						<bean:message key="time" />
					</td>
					<td width="13%">
						<bean:message key="username" />
					</td>
					<td width="7%">
						<bean:message key="action" />
					</td>
					<td width="18%">
						<bean:message key="medals" />
					</td>
					<td width="21%">
						<bean:message key="reason" />
					</td>
				</tr>
						
						<c:if test="${logpage.totalSize > lpp}">
							<div class="pages">
								<em>&nbsp;${logpage.totalSize}&nbsp;</em>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=1"
										class="first">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
								</c:if>
								<c:choose>
									<c:when
										test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
										
										<c:forEach var="num" begin="${logpage.currentPage-2}"
											end="${(logpage.currentPage-2)+9}" step="1">
											<c:choose>
												<c:when test="${logpage.currentPage == num}">
													<strong>${logpage.currentPage}</strong>
												</c:when>
												<c:otherwise>
													<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${num}">${num}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when
												test="${logpage.totalPage>10 && logpage.currentPage>=4}">
												
												<c:forEach var="num" begin="${logpage.totalPage-9}"
													end="${logpage.totalPage}" step="1">
													<c:choose>
														<c:when test="${logpage.currentPage == num}">
															<strong>${logpage.currentPage}</strong>
														</c:when>
														<c:otherwise>
															<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<strong>${logpage.currentPage}</strong>
																</c:when>
																<c:otherwise>
																	<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${num}">${num}</a>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:when>
													<c:otherwise>
													
														<c:forEach var="num" begin="1" end="${logpage.totalPage}"
															step="1">
															<c:choose>
																<c:when test="${logpage.currentPage == num}">
																	<strong>${logpage.currentPage}</strong>
																</c:when>
																<c:otherwise>
																	<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
									<a
										href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${logpage.nextPage}"
										class="next">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a
									href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${logpage.totalPage}"
									class="last">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"
										onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=medalslog&lpp=${lpp}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
				<c:forEach items="${medalsloglist}" var="medals">
					<tr>
					<td width="13%" class="altbg1">
						<a href="space.jsp?action=viewpro&username=<jrun:encoding value="${medals.firstName}"/>" target="_blank">${medals.firstName}</a>
					</td>
					<td width="13%" class="altbg2">
						${medals.ipAddress}
					</td>
					<td width="15%" class="altbg1">
						${medals.operateDate}
					</td>
					<td width="13%" class="altbg2">
						<a href="space.jsp?action=viewpro&username=<jrun:encoding value="${medals.secondName}"/>" target="_blank">${medals.secondName}</a>
					</td>
					<td width="7%" class="altbg1">
						${medals.operate}
					</td>
					<c:choose>
						<c:when test="${medals.medalName==''}">
							<td width="18%" class="altbg2">
								<bean:message key="unavailable" />
							</td>
						</c:when>
						<c:otherwise>
						<td width="18%" class="altbg2">
							<img src="images/common/${medals.imgUrl}" align="center">&nbsp;${medals.medalName}
							</td>
						</c:otherwise>
					</c:choose>
					<td width="21%" class="altbg1">
						${medals.reason}
					</td>
				</tr>
				</c:forEach>
			</table>
									
						<c:if test="${logpage.totalSize > lpp}">
							<div class="pages">
								<em>&nbsp;${logpage.totalSize}&nbsp;</em>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=1"
										class="first">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
								</c:if>
								<c:choose>
									<c:when
										test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
										
										<c:forEach var="num" begin="${logpage.currentPage-2}"
											end="${(logpage.currentPage-2)+9}" step="1">
											<c:choose>
												<c:when test="${logpage.currentPage == num}">
													<strong>${logpage.currentPage}</strong>
												</c:when>
												<c:otherwise>
													<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${num}">${num}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when
												test="${logpage.totalPage>10 && logpage.currentPage>=4}">
												
												<c:forEach var="num" begin="${logpage.totalPage-9}"
													end="${logpage.totalPage}" step="1">
													<c:choose>
														<c:when test="${logpage.currentPage == num}">
															<strong>${logpage.currentPage}</strong>
														</c:when>
														<c:otherwise>
															<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<strong>${logpage.currentPage}</strong>
																</c:when>
																<c:otherwise>
																	<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${num}">${num}</a>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:when>
													<c:otherwise>
													
														<c:forEach var="num" begin="1" end="${logpage.totalPage}"
															step="1">
															<c:choose>
																<c:when test="${logpage.currentPage == num}">
																	<strong>${logpage.currentPage}</strong>
																</c:when>
																<c:otherwise>
																	<a href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
									<a
										href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${logpage.nextPage}"
										class="next">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a
									href="admincp.jsp?action=medalslog&lpp=${lpp}&amp;page=${logpage.totalPage}"
									class="last">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"
										onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=medalslog&lpp=${lpp}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
<jsp:directive.include file="../cp_footer.jsp" />