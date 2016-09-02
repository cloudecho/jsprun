<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="PRN"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
<tr class="header">
	<td>
		<div style="float:left; margin-left:0px; padding-top:8px"> <a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a> </div>
		<div style="float:right; margin-right:4px; padding-bottom:9px"> <a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a>
		</div> </td> </tr> <tbody id="menu_tip" style="display:"> <tr> <td> <bean:message key="a_post_prune_tips"/> </td> </tr> </tbody> 
</table> <br />
<script type="text/javascript">
function page(number) {
	$('pruneforum').page.value=number;
	$('pruneforum').searchsubmit.click();
}
function checkallvalue(form, value, checkall) {
	var checkall = checkall ? checkall : 'chkall';
	for(var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if(e.type == 'checkbox' && e.value == value) {
			e.checked = form.elements[checkall].checked;
		}
	}
}
</script>
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
	<form method="post" action="admincp.jsp?action=prune&search=yes" name="pruneforum" id="pruneforum">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		
		<input type="hidden" name="bigstart" value="${startTime}">
		<input type="hidden" name="page" value="${logpage.currentPage}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"> <td colspan="2"> <bean:message key="a_post_prune_search"/> </td> </tr>
			<tr> <td class="altbg1"> <bean:message key="a_post_prune_search_detail"/> </td>
				<td align="right" class="altbg2">
					<input class="checkbox" type="checkbox" name="detail" value="1" checked>
				</td>
			</tr>
			<tr>
				<td class="altbg1"> <bean:message key="post_search_forum"/> </td>
				<td align="right" class="altbg2">
					<c:choose>
						<c:when test="${members.adminid==1||members.adminid==2}">
						<select name="forums">
						<option value=0> &nbsp;&nbsp;&gt; <bean:message key="select"/> </option>
						<option value=0> &nbsp; </option>
						${forumselect}
						</select></c:when>
						<c:otherwise>${forumselect}</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="post_search_time"/>
				</td>
				<td align="right" class="altbg2">
				<input type="text" name="starttime" size="10" value="${(not empty pf)?pf.starttime : startTime}" onclick="showcalendar(event, this)"> - <input type="text" name="endtime" size="10" value="${(not empty pf)?pf.endtime : endTime}" ${empty isAdmin? "onclick=showcalendar(event, this)" : "disabled"}>
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_post_prune_search_user"/>
				</td>
				<td align="right" class="altbg2"> <bean:message key="case_insensitive"/>
				<input class="checkbox" type="checkbox" name="cins" value="1" ${pf.cins == 1?'checked' : ''}>
					<br /> <input type="text" name="users" value="${pf.users}" size="40">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_post_prune_search_ip"/>
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="useip" value="${pf.useip}" size="40">
				</td>
			</tr>

			<tr>
				<td class="altbg1"> <bean:message key="a_post_prune_search_keyword"/> </td>
				<td align="right" class="altbg2">
					<input type="text" name="keywords" value="${pf.keywords}" size="40">
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_post_prune_search_lengthlimit"/>
				</td>
				<td align="right" class="altbg2"> <input type="text" name="lengthlimit" value="${pf.lengthlimit}" size="40">
				</td>
			</tr>
		</table>
		<br />
		<center> <input class="button" type="submit" name="searchsubmit" value="<bean:message key="submit"/>"> </center>
	</form>
	<br />
	<br />
	<logic:notEmpty name="notfirst">
		<form method="post" action="admincp.jsp?action=prune&batch=yes" target="pruneframe">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<logic:empty name="postsList">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
					<tr class="header"> <td colspan="2"> <bean:message key="a_post_prune_result"/> 0 </td> </tr>
					<tr> <td class="altbg2"> <b>${myinfo}</b> </td> </tr>
				</table>
			</logic:empty>
			<logic:notEmpty name="postsList">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" height="67">
					<tr class="header"> <td colspan="2"> <bean:message key="a_post_prune_result"/> ${countPosts} </td>
					</tr>
					<tr>
						<td class="altbg1"> <bean:message key="PRN"/> </td>
						<td class="altbg2"> <input class="checkbox" type="checkbox" name="donotupdatemember" value="1" checked onclick="this.form.prunesubmit.disabled=false;"> <bean:message key="post_no_update_member"/>
						</td>
					</tr>
				</table> <br /> <br />
				<div class="top3" id="thewait" style='display:${isDisplay}'>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="tableborder">
						<tr class="header">
							<td> <input name="chkall" type="checkbox" class="checkbox" checked onclick="checkall(this.form, 'pidarray', 'chkall')"> <bean:message key="del"/> </td>
							<td> <bean:message key="subject"/> </td>
							<td> <bean:message key="message"/> </td>
							<td> <bean:message key="forum"/> </td>
							<td> <bean:message key="author"/> </td>
							<td> <bean:message key="time"/> </td>
						</tr>
			 		
			<c:if test="${logpage.totalSize > 10}">
				<div class="pages">
					<em>&nbsp;${logpage.totalSize}&nbsp;</em>
					
					<c:if test="${logpage.totalPage>10 && logpage.currentPage>=4}">
						<a href="admincp.jsp?action=prune&searchpage=yes&page=1&forums=${pf.forums}&starttime=${pf.starttime}&endtime=${pf.endtime}&users=${pf.users}&useip=${pf.useip}&keywords=${pf.keywords}&lengthlimit=${pf.lengthlimit}" class="first">1 ...</a>
					</c:if>
					
					<c:if test="${logpage.currentPage != logpage.prePage}">
						<a href="admincp.jsp?action=prune&searchpage=yes&amp;page=${logpage.prePage}&forums=${pf.forums}&starttime=${pf.starttime}&endtime=${pf.endtime}&users=${pf.users}&useip=${pf.useip}&keywords=${pf.keywords}&lengthlimit=${pf.lengthlimit}" class="prev">&lsaquo;&lsaquo;</a>
					</c:if>
					<c:choose>
						<c:when test="${logpage.totalPage>10 && logpage.currentPage>=4 && logpage.totalPage-(logpage.currentPage-2)>=10}">
							
							<c:forEach var="num" begin="${logpage.currentPage-2}" end="${(logpage.currentPage-2)+9}" step="1">
								<c:choose>
									<c:when test="${logpage.currentPage == num}">
										<strong>${logpage.currentPage}</strong>
									</c:when>
									<c:otherwise>
										<a href="admincp.jsp?action=prune&searchpage=yes&amp;page=${num}&forums=${pf.forums}&starttime=${pf.starttime}&endtime=${pf.endtime}&users=${pf.users}&useip=${pf.useip}&keywords=${pf.keywords}&lengthlimit=${pf.lengthlimit}">${num}</a>
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
												<a href="admincp.jsp?action=prune&searchpage=yes&amp;page=${num}&forums=${pf.forums}&starttime=${pf.starttime}&endtime=${pf.endtime}&users=${pf.users}&useip=${pf.useip}&keywords=${pf.keywords}&lengthlimit=${pf.lengthlimit}">${num}</a>
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
														<a href="admincp.jsp?action=prune&searchpage=yes&amp;page=${num}&forums=${pf.forums}&starttime=${pf.starttime}&endtime=${pf.endtime}&users=${pf.users}&useip=${pf.useip}&keywords=${pf.keywords}&lengthlimit=${pf.lengthlimit}">${num}</a>
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
														<a href="admincp.jsp?action=prune&searchpage=yes&amp;page=${num}&forums=${pf.forums}&starttime=${pf.starttime}&endtime=${pf.endtime}&users=${pf.users}&useip=${pf.useip}&keywords=${pf.keywords}&lengthlimit=${pf.lengthlimit}">${num}</a>
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
						<a href="admincp.jsp?action=prune&searchpage=yes&amp;page=${logpage.nextPage}&forums=${pf.forums}&starttime=${pf.starttime}&endtime=${pf.endtime}&users=${pf.users}&useip=${pf.useip}&keywords=${pf.keywords}&lengthlimit=${pf.lengthlimit}" class="next">&rsaquo;&rsaquo;</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10 && (logpage.totalPage-logpage.currentPage)>7}">
						<a href="admincp.jsp?action=prune&searchpage=yes&amp;page=${logpage.totalPage}&forums=${pf.forums}&starttime=${pf.starttime}&endtime=${pf.endtime}&users=${pf.users}&useip=${pf.useip}&keywords=${pf.keywords}&lengthlimit=${pf.lengthlimit}" class="last">... ${logpage.totalPage}</a>
					</c:if>
					
					<c:if test="${logpage.totalPage>10}">
						<kbd>
							<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=prune&searchpage=yes&forums=${pf.forums}&starttime=${pf.starttime}&endtime=${pf.endtime}&users=${pf.users}&useip=${pf.useip}&keywords=${pf.keywords}&lengthlimit=${pf.lengthlimit}&amp;page='+this.value; return false;}" />
						</kbd>
					</c:if>
			</c:if>

				<c:forEach var="p" items="${postsList}">
					<tr>
						<td align="center" class="altbg1">
							<input class="checkbox" type="checkbox" name="pidarray[${p.pid}]" value="1" checked>
							<input type="hidden" name="first_${p.first}" value="${p.first }">
						<td class="altbg2"> <a href="redirect.jsp?goto=findpost&pid=${p.pid}&ptid=${p.tid}" target="_blank"><jrun:showStr str="${p.tsubject}" len="27" /> </a> </td>
						<td class="altbg1"> &nbsp;<jrun:showStr str="${p.message}" len="30" />&nbsp; </td>
						<td class="altbg2"> <a href="forumdisplay.jsp?fid=${p.fid}" target="_blank">${p.name}</a> </td>
						<td align="center" class="altbg1"> <a href="space.jsp?action=viewpro&uid=${p.authorid}" target="_blank">${p.author}</a> </td>
						<td align="center" class="altbg2">
							<jrun:showTime timeInt="${p.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
						</td>
					</tr>
				</c:forEach>
				<input type="hidden" name="sb" value="${sb}">
				<input type="hidden" name="fids" value="${pf.forums}">
			</table>
		</div>
	</logic:notEmpty>
	<br />
	<center>
		<input class="button" type="submit" name="prunesubmit" value="<bean:message key="submit"/>" ${empty postsList?'disabled':''}>
	</center>
</form>
<iframe name="pruneframe" style="display:none"></iframe>
</logic:notEmpty>
<jsp:directive.include file="../cp_footer.jsp" />