<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_post_moderate"/></td></tr>
</table>
<br />
	<style type="text/css">
		.mod_validate td{ background: #FFFFFF; }
		.mod_delete td{	background: #FFEBE7; }
		.mod_ignore td{	background: #EEEEEE; }
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
<form id="topicadmin" name="topicadmin" method="POST" action="topicadmin.jsp" target="_blank">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="moderates" value="">
	<input type="hidden" name="fid" value="">
	<input type="hidden" name="operation" value="">
</form>
<form method="post" action="admincp.jsp?action=modthreads&batch=yes&page=${postsPageForm.nextPage}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	
	<logic:notEmpty name="validateList">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td colspan="5"><bean:message key="moderate_validate_list"/></td>
			</tr>
			<tr class="category" align="center">
				<td>Tid</td>
				<td><bean:message key="subject"/></td>
				<td><bean:message key="author"/></td>
				<td><bean:message key="dateline"/></td>
				<td><bean:message key="front_moderation"/></td>
			</tr>
			<c:forEach var="vp" items="${validateList}">
				<tr>
					<td width="8%" class="altbg1">${vp.tid }</td>
					<td width="45%" class="altbg2"><a href="viewthread.jsp?tid=${vp.tid }" target="_blank"> ${vp.subject}</a></td>
					<td width="12%" class="altbg1"><a href="space.jsp?action=viewpro&uid=${vp.authorid }" target="_blank">${vp.author }</a></td>
					<td width="20%" class="altbg2"><jrun:showTime timeInt="${vp.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
					<td width="15%" class="altbg1">
						<select name="action2" id="action2" onchange="if(this.options[this.selectedIndex].value != '') {$('topicadmin').operation.value= this.options[this.selectedIndex].value; $('topicadmin').moderates.value=${vp.tid}; $('topicadmin').fid.value=${vp.fid}; $('topicadmin').submit();}">
							<option value="" selected><bean:message key="menu_moderation"/></option>
							<option value="delete"><bean:message key="admin_delthread"/></option>
							<option value="close"><bean:message key="admin_close"/></option>
							<option value="move"><bean:message key="admin_move"/></option>
							<option value="copy"><bean:message key="admin_copy"/></option>
							<option value="highlight"><bean:message key="admin_highlight"/></option>
							<option value="digest"><bean:message key="admin_digest"/></option>
							<option value="stick"><bean:message key="admin_stick"/></option>
							<option value="merge"><bean:message key="admin_merge"/></option>
							<option value="bump"><bean:message key="admin_bump"/></option>
							<option value="repair"><bean:message key="admin_repair"/></option>
						</select>
					</td>
				</tr>
			</c:forEach>
		</table>
	</logic:notEmpty>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="menu_post_modthreads"/> - <bean:message key="a_post_moderate_bound"/> -
				<select style="margin: 0px;" onchange="if(this.options[this.selectedIndex].value != '') {window.location=('admincp.jsp?action=modthreads&automod=yes&modfid=${pastsPage.fid }&filter='+this.options[this.selectedIndex].value+'&amp;sid=5ScwCd');}">
					<c:choose>
						<c:when test="${pastsPage.filter eq 'ignore' }">
							<option value="normal"><bean:message key="moderate_none"/></option>
							<option value="ignore" selected><bean:message key="moderate_ignore"/></option>
							<c:set var="fileter" value="ignore"/>
						</c:when>
						<c:otherwise>
							<option value="normal" selected><bean:message key="moderate_none"/></option>
							<option value="ignore"><bean:message key="moderate_ignore"/></option>
							<c:set var="fileter" value="normal"/>
						</c:otherwise>
					</c:choose>
				</select>
				- <bean:message key="a_post_moderate_forum"/> -
				<select style="margin: 0px;" onchange="if(this.options[this.selectedIndex].value != '') {window.location.href=('admincp.jsp?action=modthreads&automod=yes&filter=${pastsPage.filter}&modfid='+this.options[this.selectedIndex].value+'&amp;sid=5ScwCd');}">
					<option value="-1" selected><bean:message key="all_forum"/></option>
					<c:forEach var="forum" items="${forumList}">
						<option value="${forum.fid}" ${forum.fid==pastsPage.fid?"selected":""}>${forum.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="category">
				<input class="button" type="button" value="<bean:message key="moderate_all_validate"/>" onclick="checkalloption(this.form, 'validate')"> &nbsp;
				<input class="button" type="button" value="<bean:message key="all_delete"/>" onclick="checkalloption(this.form, 'delete')"> &nbsp;
				<input class="button" type="button" value="<bean:message key="all_ignore"/>" onclick="checkalloption(this.form, 'ignore')"> &nbsp;
			</td>
		</tr>
		
		<logic:empty name="postsPageForm" property="list">
			<td colspan="2" class="altbg1"><a href="admincp.jsp?action=modreplies"><bean:message key="a_post_moderate_threads_none"/></a></td>
		</logic:empty>
		
		<logic:notEmpty name="postsPageForm">
			<c:forEach var="posts" items="${postsPageForm.list}">
				<tr class="altbg1" id="mod_${posts.tid}_row1">
					<td width="15%" height="100%"><b><a href="space.jsp?action=viewpro&uid=${posts.authorid}" target="_blank">${posts.author}</a></b>(${posts.useip})</td>
					<td>
						<a href="forumdisplay.jsp?fid=${posts.fid}" target="_blank">${posts.name}</a>
						<b>&raquo;</b>
						<b>${posts.subject}</b>
					</td>
				</tr>
				<tr class="altbg2" id="mod_${posts.tid}_row2">
					<td valign="middle">
						<input class="radio" type="radio" name="${posts.tid}" id="mod_${posts.tid}_1" value="validate" checked onclick="mod_setbg(${posts.tid}, 'validate');"><bean:message key="validate"/><br/>
						<input class="radio" type="radio" name="${posts.tid}" id="mod_${posts.tid}_2" value="delete" onclick="mod_setbg(${posts.tid}, 'delete');"><bean:message key="delete"/><br />
						<input class="radio" type="radio" name="${posts.tid}" id="mod_${posts.tid}_3" value="ignore" onclick="mod_setbg(${posts.tid}, 'ignore'); "><bean:message key="ignore"/><br />
					</td>
					<td style="border-left: 1px #BBDCF1 solid; padding: 4px;">
						<div style="overflow: auto; overflow-x: hidden; height:120px; word-wrap:break-word; white-space:normal; width: 650px;">
							${posts.message}
						</div>
					</td>
				</tr>
				<tr class=altbg2 id="mod_${posts.tid}_row3">
					<td style="text-align: center; padding: 0px;">
						<jrun:showTime timeInt="${posts.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
					</td>
					<td style="border-left: 1px #BBDCF1 solid; padding: 2px 10px 2px 10px;">
						<a href="post.jsp?action=edit&fid=${posts.fid}&tid=${posts.tid}&pid=${posts.pid}&page=1&mod=edit" target="_blank"><bean:message key="a_post_moderate_edit_thread"/></a> &nbsp;&nbsp;|&nbsp;&nbsp; <bean:message key="moderate_reasonpm"/>&nbsp;
						<input type=text size=30 name=pm_${posts.tid} id=pm_${posts.tid} style="margin: 0px;">&nbsp;<input type="hidden" name="hidden_${posts.tid}" value="${posts.subject}">
						<input type="hidden" name="author_${posts.tid}" value="${posts.authorid}">
						<select style="margin: 0px;" onchange="$('pm_${posts.tid}').value=this.value">
							<option value=""><bean:message key="none"/></option>
							<option value="">--------</option>
							<c:forEach items="${modreasons}" var="modreason">
								<option value="${modreason}">${modreason}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:forEach>
					
			<c:if test="${postsPageForm.totalSize > 10}">
				<div class="pages">
					<em>&nbsp;${postsPageForm.totalSize}&nbsp;</em>
					
					<c:if test="${postsPageForm.totalPage>10 && postsPageForm.currentPage>=4}">
						<a href="admincp.jsp?action=modthreads&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=1" class="first">1 ...</a>
					</c:if>
					
					<c:if test="${postsPageForm.currentPage != 1}">
						<a href="admincp.jsp?action=modthreads&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${postsPageForm.currentPage-1}" class="prev">&lsaquo;&lsaquo;</a>
					</c:if>
					<c:choose>
						<c:when test="${postsPageForm.totalPage>10 && postsPageForm.currentPage>=4 && postsPageForm.totalPage-(postsPageForm.currentPage-2)>=10}">
							
							<c:forEach var="num" begin="${postsPageForm.currentPage-2}" end="${(postsPageForm.currentPage-2)+9}" step="1">
								<c:choose>
									<c:when test="${postsPageForm.currentPage == num}"><strong>${postsPageForm.currentPage}</strong></c:when>
									<c:otherwise><a href="admincp.jsp?action=modthreads&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${num}">${num}</a></c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${postsPageForm.totalPage>10 && postsPageForm.currentPage>=4}">
									
									<c:forEach var="num" begin="${postsPageForm.totalPage-9}" end="${postsPageForm.totalPage}" step="1">
										<c:choose>
											<c:when test="${postsPageForm.currentPage == num}"><strong>${postsPageForm.currentPage}</strong></c:when>
											<c:otherwise><a href="admincp.jsp?action=modthreads&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${num}">${num}</a></c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${postsPageForm.totalPage>10}">
										
											<c:forEach var="num" begin="1" end="10" step="1">
												<c:choose>
													<c:when test="${postsPageForm.currentPage == num}">
														<strong>${postsPageForm.currentPage}</strong>
													</c:when>
													<c:otherwise>
														<a href="admincp.jsp?action=modthreads&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${num}">${num}</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
										
											<c:forEach var="num" begin="1" end="${postsPageForm.totalPage}"
												step="1">
												<c:choose>
													<c:when test="${postsPageForm.currentPage == num}">
														<strong>${postsPageForm.currentPage}</strong>
													</c:when>
													<c:otherwise>
														<a href="admincp.jsp?action=modthreads&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${num}">${num}</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${postsPageForm.currentPage != postsPageForm.totalPage}">
						<a
							href="admincp.jsp?action=modthreads&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${postsPageForm.currentPage+1}"
							class="next">&rsaquo;&rsaquo;</a>
					</c:if>
					
					<c:if test="${postsPageForm.totalPage>10 && (postsPageForm.totalPage-postsPageForm.currentPage)>7}">
						<a
						href="admincp.jsp?action=modthreads&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page=${postsPageForm.totalPage}"
						class="last">... ${postsPageForm.totalPage}</a>
					</c:if>
					
					<c:if test="${postsPageForm.totalPage>10}">
						<kbd>
							<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=modthreads&searchpage=yes&filter=${fileter}&modfid=${pastsPage.fid}&amp;page='+this.value; return false;}" />
						</kbd>
					</c:if>
			</c:if>

		</logic:notEmpty>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="modsubmit" value="<bean:message key="submit"/>">
		
		<input type="hidden" name="auditing" value="${postsPageForm.sb}" />
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />