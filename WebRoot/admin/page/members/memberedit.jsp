<%@ page language="java"  pageEncoding="UTF-8" %>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_edit"/></td></tr>
</table>
		<br />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td>
					<div style="float:left; margin-left:0px; padding-top:8px">
						<a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a>
					</div>
					<div style="float:right; margin-right:4px; padding-bottom:9px">
						<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a>
					</div>
				</td>
			</tr>
			<tbody id="menu_tip" style="display:">
				<tr>
					<td>
						<bean:message key="a_member_tips"/>
					</td>
				</tr>
			</tbody>
		</table>
		<br />
		<form method="post" action="admincp.jsp?action=todeletemember">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" style="table-layout: fixed">
			<tr align="center" class="header">
				<td width="48">
					<input type="checkbox" name="chkall" onclick="checkall(this.form)" class="checkbox"><bean:message key="del"/>
				</td>
				<td width="100">
					<bean:message key="username"/>
				</td>
				<td width="60">
					<bean:message key="credits"/>
				</td>
				<td width="60">
					<bean:message key="posts"/>
				</td>
				<td width="80">
					<bean:message key="menu_member_admingroups"/>
				<td width="80">
					<bean:message key="menu_member_usergroups"/>
				</td>
				<td width="250">
					<bean:message key="edit"/>
				</td>
			</tr>
					
			<c:if test="${logpage.totalSize > lpp}">
				<div class="pages">
					<em>&nbsp;${logpage.totalSize}&nbsp;</em>
					
					<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
						<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=1" class="first">1 ...</a>
					</c:if>
					
					<c:if test="${logpage.currentPage != logpage.prePage}">
						<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
					</c:if>
					<c:choose>
						<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
							
							<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
								<c:choose>
									<c:when test="${logpage.currentPage == num}">
										<strong>${logpage.currentPage}</strong>
									</c:when>
									<c:otherwise>
										<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${num}">${num}</a>
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
												<strong>${logpage.currentPage}</strong>
											</c:when>
											<c:otherwise>
												<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${num}">${num}</a>
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
														<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${num}">${num}</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
										
											<c:forEach var="num" begin="1" end="${logpage.totalPage}" step="1">
												<c:choose>
													<c:when test="${logpage.currentPage == num}">
														<strong>${logpage.currentPage}</strong>
													</c:when>
													<c:otherwise>
														<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${num}">${num}</a>
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
						<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${logpage.nextPage}" class="next">&rsaquo;&rsaquo;</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
						<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${logpage.totalPage}" class="last">... ${logpage.totalPage}</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10}">
						<kbd>
						<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page='+this.value; return false;}" />
					</kbd>
					</c:if>
			</c:if>

			<c:forEach items="${memberList}" var="member">
				<tr align="center" class="smalltxt">
					<td class="altbg1">
						<input type="checkbox" name="uids" value="${member.uid}" class="checkbox" ${member.groupid==1?'disabled':''}/>
					</td>
					<td class="altbg2">
						<a href="space.jsp?action=viewpro&uid=${member.uid}" target="_blank"> ${member.username} </a>
					</td>
					<td class="altbg1">
						${member.credits}
					</td>
					<td class="altbg2">
						${member.posts}
					</td>
					<td class="altbg1">
						<b> <c:forEach items="${usergroupslist}" var="usergroup">
							<c:if test="${usergroup.groupid==member.adminid}">${usergroup.grouptitle}</c:if>
						</c:forEach> </b>
					</td>
					<td class="altbg2">
						<c:choose>
							<c:when test="${member.type=='system'}">
								<b>${member.grouptitle}
								</b>
							</c:when>
							<c:when test="${member.type=='member'}">
								${member.grouptitle}
							</c:when>
							<c:when test="${member.type=='special'}">
								<em>${member.grouptitle} </em>
							</c:when>
						</c:choose>
					</td>
					<td class="altbg1">
						<a href="admincp.jsp?action=toeditgroups&memberid=${member.uid}">[<bean:message key="menu_member_usergroups"/>]</a>
						<a href="admincp.jsp?action=toaccess&memberid=${member.uid}">[<bean:message key="access"/>]</a>
						<a href="admincp.jsp?action=toeditcredits&memberid=${member.uid}">[<bean:message key="credits"/>]</a>
						<a href="admincp.jsp?action=toeditmedal&memberid=${member.uid}">[<bean:message key="medals"/>]</a>
						<a href="admincp.jsp?action=toedituserinfo&memberid=${member.uid}">[<bean:message key="detail"/>]</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="pages_btns">
				
			<c:if test="${logpage.totalSize > lpp}">
				<div class="pages">
					<em>&nbsp;${logpage.totalSize}&nbsp;</em>
					
					<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
						<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=1" class="first">1 ...</a>
					</c:if>
					
					<c:if test="${logpage.currentPage != logpage.prePage}">
						<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
					</c:if>
					<c:choose>
						<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
							
							<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
								<c:choose>
									<c:when test="${logpage.currentPage == num}">
										<strong>${logpage.currentPage}</strong>
									</c:when>
									<c:otherwise>
										<a href="admincp.jsp?action=members&searchsubmit=yes&formHash=${jrun:formHash(pageContext.request)}&seasubmit=yes&lpp=${lpp}&amp;page=${num}">${num}</a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${logpage.totalPage>10 && logpage.currentPage>=4}">
									
									<c:forEach var="num" begin="${logpage.totalPage-9}" end="${logpage.totalPage}" step="1">
										<c:choose>
											<c:when test="${logpage.currentPage == num}">
												<strong>${logpage.currentPage}</strong>
											</c:when>
											<c:otherwise>
												<a href="admincp.jsp?action=members&searchsubmit=yes&formHash=${jrun:formHash(pageContext.request)}&seasubmit=yes&lpp=${lpp}&amp;page=${num}">${num}</a>
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
														<a href="admincp.jsp?action=members&searchsubmit=yes&formHash=${jrun:formHash(pageContext.request)}&seasubmit=yes&lpp=${lpp}&amp;page=${num}">${num}</a>
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
														<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${num}">${num}</a>
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
						<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${logpage.nextPage}" class="next">&rsaquo;&rsaquo;</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
						<a href="admincp.jsp?action=members&searchsubmit=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}&lpp=${lpp}&amp;page=${logpage.totalPage}" class="last">... ${logpage.totalPage}</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10}">
						<kbd>
						<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=members&searchsubmit=yes&formHash=${jrun:formHash(pageContext.request)}&seasubmit=yes&lpp=${lpp}&amp;page='+this.value; return false;}" />
					</kbd>
					</c:if>
			</c:if>
		
		</div>
		<br />
			<center><input class="button" type="submit" name="deletesubmit" value="<bean:message key="submit"/>"></center>
		</form>
<jsp:directive.include file="../cp_footer.jsp" />