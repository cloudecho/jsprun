<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_log_invite" /></td></tr>
</table>
<br />
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableborder">
				<tr class="header">
					<td colspan="3">
						<bean:message key="menu_log_invite" />
					</td>
				</tr>
				<form method="post" action="admincp.jsp?action=invitelog">
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
				<form method="post" action="admincp.jsp?action=invitelog">
					<input type="hidden" name="caction" value="statuss">
					<c:forEach items="${operations}" var="opertion">
						<c:if test="${opertion=='1'}">
							<c:set scope="page" var="a" value="${opertion}"></c:set>
						</c:if>
						<c:if test="${opertion=='2'}">
							<c:set scope="page" var="b" value="${opertion}"></c:set>
						</c:if>
						<c:if test="${opertion=='3'}">
							<c:set scope="page" var="c" value="${opertion}"></c:set>
						</c:if>
						<c:if test="${opertion=='4'}">
							<c:set scope="page" var="d" value="${opertion}"></c:set>
						</c:if>
					</c:forEach>
				<tr class="altbg2">
					<td>
						<bean:message key="action" />
					</td>
					<td>
						<input class="checkbox" type="checkbox" name="status" value="1"  <c:if test="${a=='1'}">checked</c:if>>
						<bean:message key="a_system_invite_status_1" /> &nbsp;
						<input class="checkbox" type="checkbox" name="status" value="2"  <c:if test="${b=='2'}">checked</c:if>>
						<bean:message key="a_system_invite_status_2" /> &nbsp;
						<input class="checkbox" type="checkbox" name="status" value="3"  <c:if test="${c=='3'}">checked</c:if>>
						<bean:message key="a_system_invite_status_3" /> &nbsp;
						<input class="checkbox" type="checkbox" name="status" value="4"  <c:if test="${d=='4'}">checked</c:if>>
						<bean:message key="a_system_invite_status_4" /> &nbsp;
					</td>
					<td>
						<input class="button" type="submit" value="<bean:message key="submit" />">
					</td>
				</tr>
				</form>
			</table>
			<br />
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
				<form method="post" action="admincp.jsp?action=invitelog">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<input type="hidden" name="caction" value="delinvites">
				<tr class="header">
					<td width="7%">
						<input type="checkbox" name="chkall" class="checkbox" onclick="checkall(this.form)"><bean:message key="del" />
					</td>
					<td width="8%">
						<bean:message key="a_system_logs_invite_buyer" />
					</td>
					<td width="15%">
						<bean:message key="magics_dateline_buy" />
					</td>
					<td width="15%">
						<bean:message key="a_post_forums_recommend_expiration" />
					</td>
					<td width="12%">
						<bean:message key="a_system_logs_invite_ip" />
					</td>
					<td width="20%">
						<bean:message key="a_system_invite_code" />
					</td>
					<td width="18%">
						<bean:message key="a_system_check_status" />
					</td>
				</tr>
							
						<c:if test="${logpage.totalSize > lpp}">
							<div class="pages">
								<em>&nbsp;${logpage.totalSize}&nbsp;</em>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="admincp.jsp?action=invitelog&lpp=${lpp}&amp;page=1"
										class="first">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="admincp.jsp?action=invitelog&lpp=${lpp}&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
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
													<a href="admincp.jsp?action=invitelog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
															<a href="admincp.jsp?action=invitelog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=invitelog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=invitelog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
										href="admincp.jsp?action=invitelog&lpp=${lpp}&amp;page=${logpage.nextPage}"
										class="next">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a
									href="admincp.jsp?action=invitelog&lpp=${lpp}&amp;page=${logpage.totalPage}"
									class="last">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"
										onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=invitelog&lpp=${lpp}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
				<c:forEach items="${inviteslist}" var="invites">
						<tr class="center">
					<td width="7%">
						<input type="checkbox" name="deleid" class="checkbox" value="${invites.invitecode}">
					</td>
					<td width="8%">
						<a href="space.jsp?action=viewpro&username=<jrun:encoding value="${invites.username}"/>" target="_blank">${invites.username}</a>
					</td>
					<td width="15%">
						${invites.buytime}
					</td>
					<td width="15%">
						${invites.termtime}
					</td>
					<td width="12%">
						${invites.buyIp}
					</td>
					<td width="20%">
						${invites.invitecode}
					</td>
					<td width="18%">
						${invites.status}
					</td>
				</tr>
				</c:forEach>	
			</table>
			<br />
			<center>
				<input class="button" type="submit" name="invitesubmit" value="<bean:message key="submit" />">
			</center>
			</form>
<jsp:directive.include file="../cp_footer.jsp" />