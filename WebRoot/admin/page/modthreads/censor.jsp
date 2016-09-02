<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_post_censor"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<div style="float: left; margin-left: 0px; padding-top: 8px">
				<a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a>
			</div>
			<div style="float: right; margin-right: 4px; padding-bottom: 9px">
				<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a>
			</div>
		</td>
	</tr>
	<tbody id="menu_tip" style="display: ">
		<tr>
			<td>
				<bean:message key="a_post_censor_tips"/>
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=censor&batch=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="8%">
				<input class="checkbox" type="checkbox" name="chkall" class="header" onclick="checkall(this.form)"> <bean:message key="del"/>
			</td>
			<td>
				<bean:message key="a_post_censor_word"/>
			</td>
			<td>
				<bean:message key="a_post_censor_replacement"/>
			</td>
			<td>
				<bean:message key="operator"/>
			</td>
		</tr>
		<c:forEach var="w" items="${wordslist}">
			<tr align="center">
				<td class="altbg1">
					<input class="checkbox" type="checkbox" name="delete[]" value="${w.id}">
				</td>
				<td class="altbg2">
					<input type="text" size="30" name="find[${w.id}]" value="${w.find}" onchange="findArray.value=findArray.value+this.name+','" maxlength="255">
				</td>
				<td class="altbg1">
					<input type="text" size="30" name="replace[${w.id}]" value="${w.replacement}" maxlength="255" onchange="replaceArray.value=replaceArray.value+this.name+','">
				</td>
				<td class="altbg2">
					${w.admin}
				</td>
			</tr>
		</c:forEach>
		<tr class="altbg1">
			<td align="center">
				<bean:message key="add_new"/>
			</td>
			<td align="center">
				<input type="text" size="30" name="newfind" maxlength="255">
			</td>
			<td align="center">
				<input type="text" size="30" name="newreplace" maxlength="255">
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
	</table>
	<div class="pages_btns">
			
		<c:if test="${logpage.totalSize > 30}">
			<div class="pages">
				<em>&nbsp;${logpage.totalSize}&nbsp;</em>
				
				<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
					<a href="admincp.jsp?action=censor&searchpage=yes&page=1" class="first">1 ...</a>
				</c:if>
				
				<c:if test="${logpage.currentPage != logpage.prePage}">
					<a href="admincp.jsp?action=censor&searchpage=yes&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
				</c:if>
				<c:choose>
					<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
						
						<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
							<c:choose>
								<c:when test="${logpage.currentPage == num}">
									<strong>${logpage.currentPage}</strong>
								</c:when>
								<c:otherwise>
									<a href="admincp.jsp?action=censor&searchpage=yes&amp;page=${num}">${num}</a>
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
											<a href="admincp.jsp?action=censor&searchpage=yes&amp;page=${num}">${num}</a>
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
													<a href="admincp.jsp?action=censor&searchpage=yes&amp;page=${num}">${num}</a>
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
													<a href="admincp.jsp?action=censor&searchpage=yes&amp;page=${num}">${num}</a>
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
					<a href="admincp.jsp?action=censor&searchpage=yes&amp;page=${logpage.nextPage}" class="next">&rsaquo;&rsaquo;</a>
				</c:if>
				
				<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
					<a href="admincp.jsp?action=censor&searchpage=yes&amp;page=${logpage.totalPage}" class="last">... ${logpage.totalPage}</a>
				</c:if>
				
				<c:if test="${logpage.totalPage>10}">
					<kbd>
						<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=censor&searchpage=yes&amp;page='+this.value; return false;}" />
					</kbd>
				</c:if>
		</c:if>
			
	</div>
	<br />
	<center>
		<input class="button" type="submit" name="censorsubmit" value="<bean:message key="submit"/>">
		<input type="hidden" name="findArray" vlaue="" />
		<input type="hidden" name="replaceArray" value="" />
	</center>
</form>
<br />
<form method="post" action="admincp.jsp?action=censor&batcharea=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="page" value="1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_post_censor_batch_add"/>
			</td>
		</tr>
		<tr>
			<td width="125" class="altbg1">
				<bean:message key="a_post_censor_batch_add_tips"/>
			</td>
			<td class="altbg2">
				<textarea style="width: 90%" rows="10" cols="80" name="addcensors"></textarea>
				<br />
				<c:if test="${members.adminid==1}">
					<input type="radio" class="radio" name="overwrite" value="2">
					<bean:message key="a_post_censor_batch_add_clear"/>
				</c:if>
				<input type="radio" class="radio" name="overwrite" value="1">
					<bean:message key="a_post_censor_batch_add_overwrite"/>
				<br />
				<input type="radio" class="radio" name="overwrite" value="0" checked>
				<bean:message key="a_post_censor_batch_add_no_overwrite"/>
				<br />
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="addcensorsubmit" value="<bean:message key="submit"/>">
	</center>
</form>
<br />
<jsp:directive.include file="../cp_footer.jsp" />