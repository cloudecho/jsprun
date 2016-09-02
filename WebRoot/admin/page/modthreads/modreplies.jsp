<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_post_modreplies"/></td></tr>
</table>
<br />
<style type="text/css">
.mod_validate td{ background: #FFFFFF; }
.mod_delete td{	background: #FFEBE7; }
.mod_ignore td{ background: #EEEEEE; }
</style>
<script type="text/javascript">
function mod_setbg(tid, value) {
	if(value == 'validate') {
		$('mod_' + tid + '_row1').className = 'altbg1';
		$('mod_' + tid + '_row2').className = 'altbg2';
		$('mod_' + tid + '_row3').className = 'altbg2';
	}else {
		$('mod_' + tid + '_row1').className = 'mod_' + value;
		$('mod_' + tid + '_row2').className = 'mod_' + value;
		$('mod_' + tid + '_row3').className = 'mod_' + value;
	}
}
</script>
<form method="post" action="admincp.jsp?action=modreplies&batch=yes&page=${modrepliesPageForm.nextPage}">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<input type="hidden" name="filter" value="normal">
<input type="hidden" name="modfid" value="0">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="menu_post_modreplies"/> - <bean:message key="a_post_moderate_bound"/> -
				<select style="margin: 0px;" onchange="if(this.options[this.selectedIndex].value != '') {window.location=('admincp.jsp?action=modreplies&automod=yes&modfid=${pastsPage.fid}&filter='+this.options[this.selectedIndex].value+'&amp;sid=5ScwCd');}">
					<c:choose>
						<c:when test="${pastsPage.filter eq 'ignore' }">
							<option value="normal">
								<bean:message key="moderate_none"/>
							</option>
							<option value="ignore" selected>
								<bean:message key="moderate_ignore"/>
							</option>
							<c:set var="fileter" value="ignore"/>
						</c:when>
						<c:otherwise>
							<option value="normal" selected>
								<bean:message key="moderate_none"/>
							</option>
							<option value="ignore">
								<bean:message key="moderate_ignore"/>
							</option>
							<c:set var="fileter" value="normal"/>
						</c:otherwise>
					</c:choose>
				</select>
				- <bean:message key="a_post_moderate_forum"/> -
				<select style="margin: 0px;" onchange="if(this.options[this.selectedIndex].value != '') {window.location=('admincp.jsp?action=modreplies&automod=yes&modfid='+this.options[this.selectedIndex].value+'&amp;sid=5ScwCd');}">
					<option value="-1" selected>
						<bean:message key="all_forum"/>
					</option>
					<c:forEach var="forum" items="${forumList}">
						<option value="${forum.fid}" ${forum.fid==pastsPage.fid?"selected":""}>${forum.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="line-height:32px;" class="p_header">
				<input class="button" type="button" value="<bean:message key="moderate_all_validate"/>" onclick="checkalloption(this.form, 'validate')"> &nbsp;
				<input class="button" type="button" value="<bean:message key="all_delete"/>" onclick="checkalloption(this.form, 'delete')"> &nbsp;
				<input class="button" type="button" value="<bean:message key="all_ignore"/>" onclick="checkalloption(this.form, 'ignore')">
			</td>
		</tr>
		<logic:empty name="modrepliesPageForm" property="list">
			<tr>
				<td colspan="2" class="altbg1">
					<a href="admincp.jsp?action=modthreads"><bean:message key="a_post_moderate_posts_none"/></a>
				</td>
			</tr>
		</logic:empty>
		<logic:notEmpty name="modrepliesPageForm">
			<c:forEach var="posts" items="${modrepliesPageForm.list}">
				<tr class="altbg1" id="mod_${posts.pid}_row1">
					<td width="15%" height="100%">
						<b><a href="space.jsp?action=viewpro&uid=${posts.authorid}" target="_blank">${posts.author}</a></b>(${posts.useip})
					</td>
					<td>
						<a href="forumdisplay.jsp?fid=${posts.fid}" target="_blank">${posts.name}</a>
						<b>&raquo;</b>
						<a href="viewthread.jsp?tid=${posts.tid}" target="_blank">${posts.threadsubject}</a>
						<b>&raquo;</b>
						<c:choose>
							<c:when test="${empty posts.subject}">
								<i><bean:message key="nosubject"/></i>
							</c:when>
							<c:otherwise>
								<b>${posts.subject}</b>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr class="altbg2" id="mod_${posts.pid}_row2">
					<td valign="middle">
						<input class="radio" type="radio" name="${posts.pid}" id="mod_${posts.pid}_1" value="validate" checked onclick="mod_setbg(${posts.pid}, 'validate');"><bean:message key="validate"/><br />
						<input class="radio" type="radio" name="${posts.pid}" id="mod_${posts.pid}_2" value="delete" onclick="mod_setbg(${posts.pid}, 'delete');"><bean:message key="delete"/><br />
						<input class="radio" type="radio" name="${posts.pid}" id="mod_${posts.pid}_3" value="ignore" onclick="mod_setbg(${posts.pid}, 'ignore'); "><bean:message key="ignore"/>
					</td>
					<td style="border-left: 1px #BBDCF1 solid; padding: 4px;">
						<div style="overflow: auto; overflow-x: hidden; height:120px; word-wrap:break-word; white-space:normal; width: 650px;">
							${posts.message}
						</div>
					</td>
				</tr>
				<tr class=altbg2 id="mod_${posts.pid}_row3">
					<td style="text-align: center; padding: 0px;">
					<jrun:showTime timeInt="${posts.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
					</td>
					<td style="border-left: 1px #BBDCF1 solid; padding: 2px 10px 2px 10px;">
						<a href="post.jsp?action=edit&fid=${posts.fid}&tid=${posts.tid}&pid=${posts.pid}&page=1&mod=edit" target="_blank"><bean:message key="a_post_moderate_edit_post"/></a> &nbsp;&nbsp;|&nbsp;&nbsp; <bean:message key="moderate_reasonpm"/>&nbsp;
						<input type=text size=30 name=pm_${posts.pid} id=pm_${posts.pid} style="margin: 0px;">&nbsp;
						<input type="hidden" name="hidden_${posts.pid}" value="${posts.message}"><input type="hidden" name="author_${posts.pid}" value="${posts.authorid}"><input type="hidden" name="threadsubject" value="${posts.threadsubject}">
						<input type="hidden" name="tid_${posts.pid}" value="${posts.tid}">
						<select style="margin: 0px;" onchange="$('pm_${posts.pid}').value=this.value">
							<option value=""><bean:message key="none"/></option>
							<option value="">--------</option>
							<c:forEach items="${modreasons}" var="modreason">
								<option value="${modreason}">${modreason}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:forEach>
					
			<c:if test="${modrepliesPageForm.totalSize > 10}">
				<div class="pages">
					<em>&nbsp;${modrepliesPageForm.totalSize}&nbsp;</em>
					
					<c:if test="${modrepliesPageForm.totalPage>10 && modrepliesPageForm.currentPage>=4}">
						<a href="admincp.jsp?action=modreplies&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=1" class="first">1 ...</a>
					</c:if>
					
					<c:if test="${modrepliesPageForm.currentPage != 1}">
						<a href="admincp.jsp?action=modreplies&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${modrepliesPageForm.currentPage-1}" class="prev">&lsaquo;&lsaquo;</a>
					</c:if>
					<c:choose>
						<c:when test="${modrepliesPageForm.totalPage>10 && modrepliesPageForm.currentPage>=4 && modrepliesPageForm.totalPage-(modrepliesPageForm.currentPage-2)>=10}">
							
							<c:forEach var="num" begin="${modrepliesPageForm.currentPage-2}" end="${(modrepliesPageForm.currentPage-2)+9}" step="1">
								<c:choose>
									<c:when test="${modrepliesPageForm.currentPage == num}">
										<strong>${modrepliesPageForm.currentPage}</strong>
									</c:when>
									<c:otherwise>
										<a href="admincp.jsp?action=modreplies&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${num}">${num}</a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${modrepliesPageForm.totalPage>10 && modrepliesPageForm.currentPage>=4}">
									
									<c:forEach var="num" begin="${modrepliesPageForm.totalPage-9}" end="${modrepliesPageForm.totalPage}" step="1">
										<c:choose>
											<c:when test="${modrepliesPageForm.currentPage == num}">
												<strong>${modrepliesPageForm.currentPage}</strong>
											</c:when>
											<c:otherwise>
												<a href="admincp.jsp?action=modreplies&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${num}">${num}</a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${modrepliesPageForm.totalPage>10}">
										
											<c:forEach var="num" begin="1" end="10" step="1">
												<c:choose>
													<c:when test="${modrepliesPageForm.currentPage == num}">
														<strong>${modrepliesPageForm.currentPage}</strong>
													</c:when>
													<c:otherwise>
														<a href="admincp.jsp?action=modreplies&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${num}">${num}</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
										
											<c:forEach var="num" begin="1" end="${modrepliesPageForm.totalPage}" step="1">
												<c:choose>
													<c:when test="${modrepliesPageForm.currentPage == num}">
														<strong>${modrepliesPageForm.currentPage}</strong>
													</c:when>
													<c:otherwise>
														<a href="admincp.jsp?action=modreplies&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${num}">${num}</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${modrepliesPageForm.currentPage != modrepliesPageForm.totalPage}">
						<a href="admincp.jsp?action=modreplies&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${modrepliesPageForm.currentPage+1}" class="next">&rsaquo;&rsaquo;</a>
					</c:if>
					
					<c:if test="${modrepliesPageForm.totalPage>10 && (modrepliesPageForm.totalPage-modrepliesPageForm.currentPage)>7}">
						<a href="admincp.jsp?action=modreplies&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${modrepliesPageForm.totalPage}" class="last">... ${modrepliesPageForm.totalPage}</a>
					</c:if>
					
					<c:if test="${modrepliesPageForm.totalPage>10}">
						<kbd>
							<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=modreplies&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page='+this.value; return false;}" />
						</kbd>
					</c:if>
			</c:if>

		</logic:notEmpty>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="modsubmit" value="<bean:message key="submit"/>">
		<input type="hidden" name="auditing" value="${modrepliesPageForm.sb}" />
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />