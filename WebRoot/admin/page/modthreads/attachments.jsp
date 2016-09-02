<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<script type="text/javascript">
function checkForm(form){
	var overcheck = false;
	for(var i=0;i<form.elements.length;i++){
		if(form.elements[i].checked == true){
			overcheck = true;
		}
	}
	if(overcheck == false){
	 	alert('<bean:message key="a_post_attachments_edit_invalid"/>');
	 	return;
	}
}
//全选，删除
function checkall(form, prefix, checkall) {
	var checkall = checkall ? checkall : 'chkall';
	for(var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if(e.name && e.name != checkall && (!prefix || (prefix && e.name.match(prefix)))) {
			e.checked = form.elements[checkall].checked;
		}
	}
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_maint_attachements"/></td></tr>
</table>
<br>
	<form method="post" action="admincp.jsp?action=attachments&search=yes" name="attachforum" id="attachforum" style="display:<c:if test="${notfirst!=null}">none</c:if>">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="page" value="${logpage.currentPage}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="tableborder">
			<tr class="header">
				<td colspan="2">
					<bean:message key="menu_maint_attachements"/>
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_post_attachments_nomatched"/>
				</td>
				<td class="altbg2" align="right">
					<input class="checkbox" type="checkbox" name="nomatched" value="1">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="post_search_forum"/>
				</td>
				<td class="altbg2" align="right">
					<select name="inforum">
						<option value="0">
							&nbsp;&nbsp;&gt; <bean:message key="all"/>
						</option>
						<option value="-1">
							&nbsp;
						</option>
						${forumselect}
					</select>
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_post_attachments_sizeless"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="sizeless" size="40" value="${attaforms.sizeless}">
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_post_attachments_sizemore"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="sizemore" size="40" value="${attaforms.sizemore}">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_post_attachments_dlcountless"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="dlcountless" size="40" value="${attaforms.dlcountless}">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_post_attachments_dlcountmore"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="dlcountmore" size="40" value="${attaforms.dlcountmore}">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_post_attachments_daysold"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="daysold" size="40" value="${attaforms.daysold}">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_post_attachments_filename"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="filename" size="40" value="${attaforms.filename}">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_post_attachments_keyword"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="keywords" size="40" value="${attaforms.keywords}">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_post_attachments_author"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="author" size="40" value="${attaforms.author}">
				</td>
			</tr>
		</table>
		<br />
		<center>
			<input class="button" type="submit" name="searchsubmit" value="<bean:message key="submit"/>">
		</center>
	</form>
<br />
<c:if test="${notfirst!=null}">
	<form method="post" action="admincp.jsp?action=attachments&delete=yes" target="attachmentframe">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td width="8%">
					<input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form)">
					<bean:message key="del"/>
				</td>
				<td width="15%" align="center">
					<bean:message key="a_post_attachments_name"/>
				</td>
				<td width="27%" align="center">
					<bean:message key="filename"/>
				</td>
				<td width="15%" align="center">
					<bean:message key="author"/>
				</td>
				<td width="17%" align="center">
					<bean:message key="a_post_attachments_thread"/>
				</td>
				<td width="13%" align="center">
					<bean:message key="size"/>
				</td>
				<td width="13%" align="center">
					<bean:message key="download"/>
				</td>
			</tr>
			
			<c:if test="${logpage.totalSize > 10}">
				<div class="pages">
					<em>&nbsp;${logpage.totalSize}&nbsp;</em>
					
					<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
						<a href="admincp.jsp?action=attachments&searchpage=yes&amp;page=1" class="first">1 ...</a>
					</c:if>
					
					<c:if test="${logpage.currentPage != logpage.prePage}">
						<a href="admincp.jsp?action=attachments&searchpage=yes&amp;page=${logpage.prePage}" class="prev">&lsaquo;&lsaquo;</a>
					</c:if>
					<c:choose>
						<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
							
							<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
								<c:choose>
									<c:when test="${logpage.currentPage == num}">
										<strong>${logpage.currentPage}</strong>
									</c:when>
									<c:otherwise>
										<a href="admincp.jsp?action=attachments&searchpage=yes&amp;page=${num}">${num}</a>
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
												<a href="admincp.jsp?action=attachments&searchpage=yes&amp;page=${num}">${num}</a>
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
														<a href="admincp.jsp?action=attachments&searchpage=yes&amp;page=${num}">${num}</a>
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
														<a href="admincp.jsp?action=attachments&searchpage=yes&amp;page=${num}">${num}</a>
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
						<a href="admincp.jsp?action=attachments&searchpage=yes&amp;page=${logpage.nextPage}" class="next">&rsaquo;&rsaquo;</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
						<a href="admincp.jsp?action=attachments&searchpage=yes&amp;page=${logpage.totalPage}" class="last">... ${logpage.totalPage}</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10}">
						<kbd>
							<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=attachments&searchpage=yes&amp;page='+this.value; return false;}" />
						</kbd>
					</c:if>
			</c:if>
			
			<c:forEach var="a" items="${showlist}">
				<tr>
					<td class="altbg1" align="center" valign="middle">
						<input class="checkbox" type="checkbox" id="ch" name="delete[]" value="${a.aid}">
					</td>
					<td class="altbg2" align="center">
						<b>${a.filename}</b> <br />
					</td>
					<td class="altbg1" align="center">
						<c:choose>
							<c:when test="${a.nomatched!=null}">
								<b>${a.nomatched}</b>
							</c:when>
							<c:otherwise>
								<b><a href="attachment.jsp?aid=${a.aid}" target="_blank">[<bean:message key="a_post_attachments_download"/>]</a> </b>
							</c:otherwise>
						</c:choose>
						<br />
						<a href="${a.remote==1?'' : settings.attachurl}${a.remote==1?'' : '/'}${a.attachment}" class="smalltxt" target="_blank"><jrun:showStr str="${a.attachment}" len="30" /></a>
					</td>
					<td class="altbg2" align="center">
						${a.author}
					</td>
					<td class="altbg1" valign="middle">
						<a href="viewthread.jsp?tid=${a.tid}" target="_blank"><b>${a.subject}</b> </a>
						<br />
						<bean:message key="forum"/>:
						<a href="forumdisplay.jsp?fid=${a.fid}" target="_blank">${a.name}</a>
					</td>
					<td class="altbg2" valign="middle" align="center">
						<jrun:showFileSize size="${a.filesize}" />
					</td>
					<td class="altbg1" valign="middle" align="center">
						${a.downloads}
					</td>
				</tr>
			</c:forEach>
		</table>
		<br />
		<br />
		<center>
			<input class="button" type="submit" name="deletesubmit" value="<bean:message key="submit"/>" onClick="checkForm(this.form)">
		</center>
	</form>
	<iframe name="attachmentframe" style="display:none"></iframe>
</c:if>
<jsp:directive.include file="../cp_footer.jsp" />