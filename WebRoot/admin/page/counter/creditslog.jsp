<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_log_credit" /></td></tr>
</table>
<br />
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableborder">
				<tr class="header">
					<td colspan="3">
						<bean:message key="menu_log_credit" />
					</td>
				</tr>
				<form method="post" action="admincp.jsp?action=creditslog">
				<input type="hidden" name="caction" value="lpp">
				<tr class="altbg2">
					<td width="20%">
						<bean:message key="a_system_logs_lpp" />
					</td>
					<td width="45%">
						<input type="text" name="lpp" size="40" maxlength="40" value="${lpp}">
					</td>
					<td width="20%">
						<input class="button" type="submit" value="<bean:message key="submit" />">
					</td>
				</tr>
				</form>

				<form method="post" action="admincp.jsp?action=creditslog">
					<input type="hidden" name="caction" value="keyword">
				<tr class="altbg1">
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

				<form method="post" action="admincp.jsp?action=creditslog">
					<input type="hidden" name="caction" value="opertar">
					<c:forEach items="${operations}" var="opertion">
						<c:if test="${opertion=='TFR'}">
							<c:set scope="page" var="TFR" value="${opertion}"></c:set>
						</c:if>
						<c:if test="${opertion=='RCV'}">
							<c:set scope="page" var="RCV" value="${opertion}"></c:set>
						</c:if>
						<c:if test="${opertion=='EXC'}">
							<c:set scope="page" var="EXC" value="${opertion}"></c:set>
						</c:if>
						<c:if test="${opertion=='UGP'}">
							<c:set scope="page" var="UGP" value="${opertion}"></c:set>
						</c:if>
						<c:if test="${opertion=='AFD'}">
							<c:set scope="page" var="AFD" value="${opertion}"></c:set>
						</c:if>
					</c:forEach>
				<tr class="altbg2">
					<td>
						<bean:message key="action" />
					</td>
					<td>
						<input class="checkbox" type="checkbox" name="operations"
							value="TFR" <c:if test="${TFR=='TFR'}">checked</c:if>>
						<bean:message key="TFR" /> &nbsp;
						<input class="checkbox" type="checkbox" name="operations"
							value="RCV" <c:if test="${RCV=='RCV'}">checked</c:if>>
						<bean:message key="RCV" /> &nbsp;
						<input class="checkbox" type="checkbox" name="operations"
							value="EXC" <c:if test="${EXC=='EXC'}">checked</c:if>>
						<bean:message key="EXC" /> &nbsp;
						<input class="checkbox" type="checkbox" name="operations"
							value="UGP" <c:if test="${UGP=='UGP'}">checked</c:if>>
						<bean:message key="UGP" /> &nbsp;
						<input class="checkbox" type="checkbox" name="operations"
							value="AFD" <c:if test="${AFD=='AFD'}">checked</c:if>>
						<bean:message key="AFD" /> &nbsp;
					</td>
					<td>
						<input class="button" type="submit" value="<bean:message key="submit" />">
					</td>
				</tr>
				</form>
			</table>
			<br />
			<br />

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableborder">
				<tr class="header">
					<td width="16%">
						<bean:message key="username" />
					</td>
					<td width="16%">
						<bean:message key="a_system_logs_credit_fromto" />
					</td>
					<td width="17%">
						<bean:message key="time" />
					</td>
					<td width="16%">
						<bean:message key="a_system_logs_credit_send" />
					</td>
					<td width="15%">
						<bean:message key="a_system_logs_credit_receive" />
					</td>
					<td width="20%">
						<bean:message key="action" />
					</td>
				</tr>
						
						<c:if test="${logpage.totalSize > lpp}">
							<div class="pages">
								<em>&nbsp;${logpage.totalSize}&nbsp;</em>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=1"
										class="first">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
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
													<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
															<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
										href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${logpage.nextPage}"
										class="next">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a
									href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${logpage.totalPage}"
									class="last">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"
										onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=creditslog&lpp=${lpp}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
				<c:forEach items="${creditsloglist}" var="creditslog">
					<tr>
					<td width="16%" class="altbg1">
						${creditslog.username}
					</td>
					<td width="16%" class="altbg2">
						${creditslog.fromname}
					</td>
					<td width="17%" class="altbg1">
						${creditslog.opertarDate}
					</td>
					<td width="16%" class="altbg2">
						${creditslog.sendCrites}&nbsp;${creditslog.sendNum}&nbsp;${creditslog.sendunit}
					</td>
					<td width="15%" class="altbg1">
						${creditslog.receiveCrites}&nbsp;${creditslog.receiverNum}&nbsp;${creditslog.receiveuint}
					</td>
					<td width="20%" class="altbg2">
						${creditslog.opertar}
					</td>
				</tr>
				</c:forEach>
				<tr class="category" align="right">
					<td colspan="6">
						<b><bean:message key="a_system_logs_credit_send_total" /></b>
						<c:forEach items="${sendext}" var="send" varStatus="index">
						<c:if test="${send!=0}">${creditsMap[index.count]['title']}&nbsp;${send}&nbsp;${creditsMap[index.count]['unit']}&nbsp;</c:if>
						</c:forEach>
						<b>|</b>
						<b><bean:message key="a_system_logs_credit_receive_total" /></b>
						<c:forEach items="${receext}" var="rec" varStatus="index">
						<c:if test="${rec!=0}">${creditsMap[index.count]['title']}&nbsp;${rec}&nbsp;${creditsMap[index.count]['unit']}&nbsp;</c:if>
						</c:forEach>
					</td>
				</tr>
			</table>
					
						<c:if test="${logpage.totalSize > lpp}">
							<div class="pages">
								<em>&nbsp;${logpage.totalSize}&nbsp;</em>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=1"
										class="first">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
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
													<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
															<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
										href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${logpage.nextPage}"
										class="next">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a
									href="admincp.jsp?action=creditslog&lpp=${lpp}&amp;page=${logpage.totalPage}"
									class="last">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"
										onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=creditslog&lpp=${lpp}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
<jsp:directive.include file="../cp_footer.jsp" />