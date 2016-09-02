<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_post_recyclebin"/></td></tr>
</table>
<br />
<script type="text/javascript">
lang['calendar_Sun'] = '<bean:message key="calendar_Sun"/>';
lang['calendar_Mon'] = '<bean:message key="calendar_Mon"/>';
lang['calendar_Tue'] = '<bean:message key="calendar_Tue"/>';
lang['calendar_Wed'] = '<bean:message key="calendar_Wed"/>';
lang['calendar_Thu'] = '<bean:message key="calendar_Thu"/>';
lang['calendar_Fri'] = '<bean:message key="calendar_Fri"/>';
lang['calendar_Sat'] = '<bean:message key="calendar_Sat"/>';
lang['old_month'] = '<bean:message key="old_month"/>';
lang['select_year'] = '<bean:message key="select_year"/>';
lang['select_month'] = '<bean:message key="select_month"/>';
lang['next_month'] = '<bean:message key="next_month"/>';
lang['calendar_hr'] = '<bean:message key="calendar_hr"/>';
lang['calendar_min'] = '<bean:message key="calendar_min"/>';
lang['calendar_month'] = '<bean:message key="calendar_month"/>';
lang['calendar_today'] = '<bean:message key="calendar_today"/>';
</script>
<script src="include/javascript/calendar.js" type="text/javascript"></script>
<form action="admincp.jsp?action=recyclebin&search=yes" method="post">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td colspan="2">
					<bean:message key="a_post_recyclebin_search"/>
				</td>
			</tr>
			<tr>
				<td class="altbg1" width="45%">
					<bean:message key="post_search_forum"/>
				</td>
				<td class="altbg2" align="right">
					<select name="inforum">
						<option value="0">
							&nbsp;&nbsp;&gt;<bean:message key="select"/>
						</option>
						${forumselect}
					</select>
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_post_recyclebin_search_author"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="authors" size="40" value="${recyclebinform.authors }">
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="post_search_keyword"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="keywords" size="40" value="${recyclebinform.keywords }">
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_post_recyclebin_search_admin"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="admins" size="40" value="${recyclebinform.admins }">
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_post_recyclebin_search_post_time"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="pstarttime" size="10" value="${recyclebinform.pstarttime }" onclick="showcalendar(event, this)">
					-
					<input type="text" name="pendtime" size="10" value="${recyclebinform.pendtime }" onclick="showcalendar(event, this)">
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_post_recyclebin_search_mod_time"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="mstarttime" size="10" value="${recyclebinform.mstarttime }" onclick="showcalendar(event, this)">
					-
					<input type="text" name="mendtime" size="10" value="${recyclebinform.mendtime }" onclick="showcalendar(event, this)">
				</td>
			</tr>
		</table>
		<br />
		<center>
			<input class="button" type="submit" name="searchsubmit" value="<bean:message key="submit"/>">
		</center>
	</form>

	<form method="post" action="admincp.jsp?action=recyclebin&deleteold=yes&prune=yes">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td colspan="2">
					<bean:message key="a_post_recyclebin_prune"/>
				</td>
			</tr>
			<tr>
				<td class="altbg1" width="45%">
					<bean:message key="a_post_recyclebin_prune_days"/>
				</td>
				<td class="altbg2" align="right">
					<input type="text" name="days" size="40" value="30">
				</td>
			</tr>
		</table>
		<br />
		<center>
			<input class="button" type="submit" name="rbsubmit" value="<bean:message key="submit"/>">
		</center>
	</form>
	<br />
	<c:if test="${notfirst != null}">
	<form method="post" action="admincp.jsp?action=recyclebin&batch=yes">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td colspan="2">
					<logic:notEmpty name="showlist">
						<bean:size id="count" name="showlist" />
					</logic:notEmpty>
					<bean:message key="a_post_recyclebin_result"/> ${count== null ?0: count}
				</td>
			</tr>
			<tr>
				<td colspan="2" class="category">
					<input class="button" type="button" value="<bean:message key="all_delete"/>" onclick="checkalloption(this.form, 'delete')"> &nbsp;
					<input class="button" type="button" value="<bean:message key="a_post_recyclebin_all_undelete"/>" onclick="checkalloption(this.form, 'undelete')"> &nbsp;
					<input class="button" type="button" value="<bean:message key="all_ignore"/>" onclick="checkalloption(this.form, 'ignore')">
				</td>
			</tr>
								
		<c:if test="${totalsize > 10}">
			<div class="pages">
				<em>&nbsp;${totalsize}&nbsp;</em>
				
				<c:if test="${totalpage>10 && currentpage>=4}">
					<a href="admincp.jsp?action=recyclebin&searchpage=yes&amp;page=1" class="first">1 ...</a>
				</c:if>
				
				<c:if test="${currentpage != 1}">
					<a href="admincp.jsp?action=recyclebin&searchpage=yes&amp;page=${currentpage-1}" class="prev">&lsaquo;&lsaquo;</a>
				</c:if>
				<c:choose>
					<c:when test="${totalpage>10 && currentpage>=4 && totalpage-(currentpage-2)>=10}">
						
						<c:forEach var="num" begin="${currentpage-2}" end="${(currentpage-2)+9}" step="1">
							<c:choose>
								<c:when test="${currentpage == num}">
									<strong>${currentpage}</strong>
								</c:when>
								<c:otherwise>
									<a href="admincp.jsp?action=recyclebin&searchpage=yes&amp;page=${num}">${num}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${totalpage>10 && currentpage>=4}">
								
								<c:forEach var="num" begin="${totalpage-9}" end="${totalpage}" step="1">
									<c:choose>
										<c:when test="${currentpage == num}">
											<strong>${currentpage}</strong>
										</c:when>
										<c:otherwise>
											<a href="admincp.jsp?action=recyclebin&searchpage=yes&amp;page=${num}">${num}</a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${totalpage>10}">
									
										<c:forEach var="num" begin="1" end="10" step="1">
											<c:choose>
												<c:when test="${currentpage == num}">
													<strong>${currentpage}</strong>
												</c:when>
												<c:otherwise>
													<a href="admincp.jsp?action=recyclebin&searchpage=yes&amp;page=${num}">${num}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
									
										<c:forEach var="num" begin="1" end="${totalpage}" step="1">
											<c:choose>
												<c:when test="${currentpage == num}">
													<strong>${currentpage}</strong>
												</c:when>
												<c:otherwise>
													<a href="admincp.jsp?action=recyclebin&searchpage=yes&amp;page=${num}">${num}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				
				<c:if test="${currentpage != totalpage}">
					<a href="admincp.jsp?action=recyclebin&searchpage=yes&amp;page=${currentpage+1}" class="next">&rsaquo;&rsaquo;</a>
				</c:if>
				
				<c:if test="${totalpage>10 && (totalpage-currentpage)>7}">
					<a href="admincp.jsp?action=recyclebin&searchpage=yes&amp;page=${totalpage}" class="last">... ${totalpage}</a>
				</c:if>
				
				<c:if test="${totalpage>10}">
					<kbd>
						<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=recyclebin&searchpage=yes&amp;page='+this.value; return false;}" />
					</kbd>
				</c:if>
		</c:if>

			<c:forEach var="r" items="${showlist}">
				<tr class="altbg2">
					<td rowspan="2" valign="top" width="15%" height="100%">
						<table cellspacing="0" cellpadding="0" border="0" width="100%" height="100%">
							<tr>
								<td valign="top" style="border:none">
									<a href="space.jsp?action=viewpro&uid=${r.authorid}" target="_blank"><b>${r.author}</b>
								</td>
							</tr>
							<tr>
								<td style="border:none">
									<input class="radio" type="radio" name="mod[${r.tid}]"	value="delete" checked>
									<bean:message key="delete"/>
									<br />
									<input class="radio" type="radio" name="mod[${r.tid}]" value="undelete">
									<bean:message key="undelete"/>
									<br />
									<input class="radio" type="radio" name="mod[${r.tid}]" value="ignore">
									<bean:message key="ignore"/>
									<br />
									<br />
									<bean:message key="threads_replies"/> ${r.replies}
									<br />
									<bean:message key="a_post_threads_views"/> ${r.views}
									<br /> <br />
									<jrun:showTime timeInt="${r.dateline}" type="${dateformat}" timeoffset="${timeoffset}"/>
									<br>
									<jrun:showTime timeInt="${r.dateline}" type="${timeformat}" timeoffset="${timeoffset}"/>
								</td>
							</tr>
						</table>
					</td>
					<td style="border:none">
						<a href="forumdisplay.jsp?fid=${r.fid}" target="_blank"> ${r.name} </a>
						<b>&raquo;</b>
						<b>${r.subject}</b>
					</td>
				</tr>
				<tr class="altbg2">
					<td>
						<div style="border-style: dotted; border-width: 1px; border-color: #9DB3C5; padding: 5px; overflow:auto; word-wrap:break-word; white-space:normal; width: 650px; height:150px">
							<div align="right" style="width: 97%">
								<bean:message key="operator"/>:
								<a href="space.jsp?action=viewpro&uid=${r.uid}" target="_blank">${r.username}</a>
								<bean:message key="a_post_recyclebin_delete_time"/>:
								<jrun:showTime timeInt="${r.mdateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}" />
							</div>
							${r.message}
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br />
		<logic:notEmpty name="showlist">
			<center>
				<input class="button" type="submit" name="rbsubmit" value="<bean:message key="submit"/>">
				<input type="hidden" name="hiddentids" value="${hiddentids}" />
			</center>
		</logic:notEmpty>
	</form>
</c:if>
<jsp:directive.include file="../cp_footer.jsp" />