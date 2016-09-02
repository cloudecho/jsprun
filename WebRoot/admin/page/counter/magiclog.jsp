<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
<tr class="header"><td colspan="3"><bean:message key="menu_log_magic" /></td></tr>
<form method="post" action="admincp.jsp?action=magiclog">
<input type="hidden" name="caction" value="lpp">
<tr class="altbg2"><td width="20%"><bean:message key="a_system_logs_lpp" /></td>
<td width="62%"><input type="text" name="lpp" size="40" maxlength="40" value="${lpp}"></td>
<td width="18%"><input class="button" type="submit" value="<bean:message key="submit" />"></td></tr>
</form>
<form method="post" action="admincp.jsp?action=magiclog">
<input type="hidden" name="caction" value="magicname">
<tr class="altbg1"><td><bean:message key="a_other_magics_type" /></td><td>
<select name="magicid">
<option value="0"><bean:message key="a_other_magics_type_all" /></option>
<c:forEach items="${magicList}" var="magic">
	<option value="${magic.magicid}" <c:if test="${magicid==magic.magicid}">selected</c:if>>${magic.name}</option>
</c:forEach>
</select></td>
<td><input class="button" type="submit" value="<bean:message key="submit" />"></td></tr>
</form>
<form method="post" action="admincp.jsp?action=magiclog">
<input type="hidden" name="caction" value="opertar">
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
						<c:if test="${opertion=='5'}">
							<c:set scope="page" var="e" value="${opertion}"></c:set>
						</c:if>
		</c:forEach>
<tr class="altbg2"><td><bean:message key="action" /></td>
<td>
<input class="checkbox" type="checkbox" name="operations" value="1"  <c:if test="${a=='1'}">checked</c:if>> <bean:message key="a_system_logs_magic_operation_1" /> &nbsp;
 <input class="checkbox" type="checkbox" name="operations" value="2" <c:if test="${b=='2'}">checked</c:if>> <bean:message key="a_system_logs_magic_operation_2" /> &nbsp; 
 <input class="checkbox" type="checkbox" name="operations" value="3" <c:if test="${c=='3'}">checked</c:if>> <bean:message key="a_system_logs_magic_operation_3" /> &nbsp; 
 <input class="checkbox" type="checkbox" name="operations" value="4" <c:if test="${d=='4'}">checked</c:if>> <bean:message key="a_system_logs_magic_operation_4" /> &nbsp;
  <input class="checkbox" type="checkbox" name="operations" value="5" <c:if test="${e=='5'}">checked</c:if>> <bean:message key="a_system_logs_magic_operation_5" /> &nbsp;
 </td>
<td><input class="button" type="submit" value="<bean:message key="submit" />"></td></tr>
</form>
</table><br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
<tr class="header">
<td width="16%"><bean:message key="username" /></td>
<td width="16%"><bean:message key="name" /></td>
<td width="17%"><bean:message key="time" /></td>
<td width="16%"><bean:message key="num" /></td>
<td width="15%"><bean:message key="price" /></td>
<td width="20%"><bean:message key="action" /></td>
</tr>
			
						<c:if test="${logpage.totalSize > lpp}">
							<div class="pages">
								<em>&nbsp;${logpage.totalSize}&nbsp;</em>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=1"
										class="first">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
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
													<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
															<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
										href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${logpage.nextPage}"
										class="next">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a
									href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${logpage.totalPage}"
									class="last">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"
										onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=magiclog&lpp=${lpp}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
<c:forEach items="${magicloglist}" var="magiclog">
	<tr align="center">
<td class="altbg1">
<a href="space.jsp?action=viewpro&username=<jrun:encoding value="${magiclog.username}"/>" target="_blank">${magiclog.username}
</td>
<td class="altbg2">${magiclog.magicname}</td>
<td class="altbg1">${magiclog.datetime}</td>
<td class="altbg2">${magiclog.amount}</td>
<td class="altbg1">${magiclog.price}</td>
<td class="altbg2">${magiclog.opertar}</td>
</tr>
</c:forEach>
</table>
			
						<c:if test="${logpage.totalSize > lpp}">
							<div class="pages">
								<em>&nbsp;${logpage.totalSize}&nbsp;</em>
								
								<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=1"
										class="first">1 ...</a>
								</c:if>
								
								<c:if test="${logpage.currentPage != logpage.prePage}">
									<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
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
													<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
															<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${num}">${num}</a>
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
										href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${logpage.nextPage}"
										class="next">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
									<a
									href="admincp.jsp?action=magiclog&lpp=${lpp}&amp;page=${logpage.totalPage}"
									class="last">... ${logpage.totalPage}</a>
								</c:if>
								
								<c:if test="${logpage.totalPage>10}">
									<kbd>
									<input type="text" name="custompage" size="3"
										onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=magiclog&lpp=${lpp}&amp;page='+this.value; return false;}" />
								</kbd>
								</c:if>
						</c:if>
			
<jsp:directive.include file="../cp_footer.jsp" />