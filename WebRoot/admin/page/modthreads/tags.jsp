<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_post_tags"/></td></tr>
</table>
<br />
<c:choose>
<c:when test="${empty notfirst}">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_post_tags"/></td></tr>
		<tr>
			<td class="altbg2">
				<bean:message key="a_post_tags_count"/> ${count} (<bean:message key="a_post_tags_status_1"/> ${closecount})<br /> <bean:message key="a_post_tags_hot"/><br />
				<c:forEach var="tag" items="${hottag}">
					<a href="tag.jsp?name=<jrun:encoding value="${tag.tagname}"/>" target="_blank">${tag.tagname}<em>(${tag.total})</em></a>
				</c:forEach>
			</td>
		</tr>
	</table>
	<br />
	<form method="post" action="admincp.jsp?action=tags&search=yes">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_post_tags_search"/></td></tr>
			<tr>
				<td class="altbg1" width="45%"><bean:message key="a_post_tag"/>:</td>
				<td align="right" class="altbg2" width="40%"><bean:message key="case_insensitive"/> <input type="checkbox" name="cins" value="1" class="checkbox">
				<br /><input type="text" name="tagname" size="40" value=""></td>
			</tr>
			<tr>
				<td class="altbg1" width="45%"><bean:message key="a_post_tags_threadnum_lower"/></td>
				<td align="right" class="altbg2" width="40%"><input type="text" name="threadnumlower" size="40" value=""></td>
			</tr>
			<tr>
				<td class="altbg1" width="45%"><bean:message key="a_post_tags_threadnum_higher"/></td>
				<td align="right" class="altbg2" width="40%"><input type="text" name="threadnumhigher" size="40" value=""></td>
			</tr>
			<tr>
				<td class="altbg1" width="45%"><bean:message key="a_post_tags_status"/></td>
				<td align="right" class="altbg2" width="40%">
					<input type="radio" name="status" value="-1" class="radio" checked> <bean:message key="all"/>&nbsp;
					<input type="radio" name="status" value="1" class="radio"> <bean:message key="a_post_tags_status_1"/>&nbsp;
					<input type="radio" name="status" value="0" class="radio"> <bean:message key="a_post_tags_status_0"/>
				</td>
			</tr>
		</table>
		<br />
		<center><input name="tagsearchsubmit" class="button" type="submit" value="<bean:message key="a_post_tags_search"/>"></center>
	</form>
</c:when>
<c:otherwise>
	<form method="post" action="admincp.jsp?action=tags&batch=yes">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="tagname" value="${tagname}">
		<input type="hidden" name="threadnumlower" value="${threadnumlower}">
		<input type="hidden" name="threadnumhigher" value="${threadnumhigher}">
		<input type="hidden" name="status" value="0">
		<input type="hidden" name="hiddenSB" value="${hiddenSB}" />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="3"><bean:message key="menu_post_tags"/></td></tr>
			<tr class="category">
				<td><bean:message key="a_post_tag"/></td>
				<td><bean:message key="stats_main_threads_count"/></td>
				<td width="300">
					<input class="button" type="button" value="<bean:message key="all_delete"/>" onclick="checkalloption(this.form, '-1')">
					<input class="button" type="button" value="<bean:message key="a_post_tags_all_close"/>" onclick="checkalloption(this.form, '1')">
					<input class="button" type="button" value="<bean:message key="a_post_tags_all_open"/>" onclick="checkalloption(this.form, '0')">
				</td>
			</tr>
			
			<c:if test="${logpage.totalSize > 100}">
				<div class="pages">
					<em>&nbsp;${logpage.totalSize}&nbsp;</em>
					
					<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
						<a href="admincp.jsp?action=tags&seachpage=yes&page=1&tagname=${tagname}&threadnumlower=${threadnumlower}&threadnumhigher=${threadnumhigher}&status=${status}&cins=${cins}" class="first">1 ...</a>
					</c:if>
					
					<c:if test="${logpage.currentPage != logpage.prePage}">
						<a href="admincp.jsp?action=tags&seachpage=yes&amp;page=${logpage.prePage}&tagname=${tagname}&threadnumlower=${threadnumlower}&threadnumhigher=${threadnumhigher}&status=${status}&cins=${cins}" class="prev">&lsaquo;&lsaquo;</a>
					</c:if>
					<c:choose>
						<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
							
							<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
								<c:choose>
									<c:when test="${logpage.currentPage == num}">
										<strong>${logpage.currentPage}</strong>
									</c:when>
									<c:otherwise>
										<a href="admincp.jsp?action=tags&seachpage=yes&amp;page=${num}&tagname=${tagname}&threadnumlower=${threadnumlower}&threadnumhigher=${threadnumhigher}&status=${status}&cins=${cins}">${num}</a>
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
												<a href="admincp.jsp?action=tags&seachpage=yes&amp;page=${num}&tagname=${tagname}&threadnumlower=${threadnumlower}&threadnumhigher=${threadnumhigher}&status=${status}&cins=${cins}">${num}</a>
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
														<a href="admincp.jsp?action=tags&seachpage=yes&amp;page=${num}&tagname=${tagname}&threadnumlower=${threadnumlower}&threadnumhigher=${threadnumhigher}&status=${status}&cins=${cins}">${num}</a>
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
														<a href="admincp.jsp?action=tags&seachpage=yes&amp;page=${num}&tagname=${tagname}&threadnumlower=${threadnumlower}&threadnumhigher=${threadnumhigher}&status=${status}&cins=${cins}">${num}</a>
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
						<a href="admincp.jsp?action=tags&seachpage=yes&amp;page=${logpage.nextPage}&tagname=${tagname}&threadnumlower=${threadnumlower}&threadnumhigher=${threadnumhigher}&status=${status}&cins=${cins}" class="next">&rsaquo;&rsaquo;</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
						<a href="admincp.jsp?action=tags&seachpage=yes&amp;page=${logpage.totalPage}&tagname=${tagname}&threadnumlower=${threadnumlower}&threadnumhigher=${threadnumhigher}&status=${status}&cins=${cins}" class="last">... ${logpage.totalPage}</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10}">
						<kbd>
							<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=tags&seachpage=yes&tagname=${tagname}&threadnumlower=${threadnumlower}&threadnumhigher=${threadnumhigher}&status=${status}&cins=${cins}&amp;page='+this.value; return false;}" />
						</kbd>
					</c:if>
			</c:if>
			
			<c:forEach items="${tagsList}" var="tag">
				<tr>
					<td class="altbg1"><a href="tag.jsp?name=<jrun:encoding value="${tag.tagname}"/>" target="_blank">${tag.tagname}</a></td>
					<td class="altbg2">${tag.total}</td>
					<td class="altbg1" style="text-align: center"><input name="tag[${tag.tagname}]" type="radio" class="radio" value="-1" ${tag.closed == -1?"checked":""}> <bean:message key="delete"/>&nbsp;<input name="tag[${tag.tagname}]" type="radio" class="radio" value="1" ${tag.closed == 1?"checked":""}> <bean:message key="a_post_tags_status_1"/>&nbsp;<input name="tag[${tag.tagname}]" type="radio" class="radio" value="0" ${tag.closed == 0?"checked":""}> <bean:message key="a_post_tags_status_0"/></td>
				</tr>
			</c:forEach>
		</table>
		<br />
		<center><input name="tagsubmit" class="button" type="submit" value="<bean:message key="submit"/>"></center>
	</form>
</c:otherwise>
</c:choose>
<jsp:directive.include file="../cp_footer.jsp" />