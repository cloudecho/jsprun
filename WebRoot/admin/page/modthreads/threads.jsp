<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_maint_threads"/></td></tr>
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
<form method="post" action="admincp.jsp?action=threadsbatch" name="searchforum" id="searchforum">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="page" value="${currentpage}">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"> <td colspan="2"> <bean:message key="a_post_threads_search"/> </td> </tr>
			<tr> <td class="altbg1"> <bean:message key="a_post_threads_search_detail"/> </td> <td class="altbg2" align="right"> <input class="checkbox" type="checkbox" name="detail" value="1" ${tfs.detail==1?'checked' : ''}/> </td> </tr>
			<tr>
				<td class="altbg1"> <bean:message key="post_search_forum"/> </td>
				<td class="altbg2" align="right">
					<select name="inforum">
						<option value="0"> &nbsp;&nbsp;&gt; <bean:message key="all"/> </option>
						<option value="0"> &nbsp; </option> ${forumselect}
					</select>
				</td>
			</tr>
			<tr>
				<td class="altbg1"> <bean:message key="post_search_time"/> </td>
				<td class="altbg2" align="right">
					<input type="text" name="starttime" size="10" onclick="showcalendar(event, this);" value="${tfs.starttime}"> - <input type="text" name="endtime" size="10" onclick="showcalendar(event, this);" value="${tfs.endtime}">
				</td>
			</tr>
			<tr>
				<td class="altbg1"> <bean:message key="a_post_threads_search_user"/> </td>
				<td class="altbg2" align="right"> <bean:message key="case_insensitive"/> <input type="checkbox" name="cins" value="1"/> <br /> <input type="text" name="users" size="40" value="${tfs.users}"> </td>
			</tr>
			<tr>
				<td class="altbg1"> <bean:message key="post_search_keyword"/> </td>
				<td class="altbg2" align="right"> <input type="text" name="keywords" size="40" value="${tfs.keywords}"> </td>
			</tr>
			<tr>
				<td class="altbg1"> &nbsp; </td>
				<td align="right" class="altbg2" style="text-align: right;"> <input class="checkbox" type="checkbox" value="1" onclick="$('advanceoption').style.display = $('advanceoption').style.display == 'none' ? '' : 'none'; this.value = this.value == 1 ? 0 : 1; this.checked = this.value == 1 ? false : true"> <bean:message key="more_options"/> &nbsp; </td>
			</tr>
			<tbody id="advanceoption" style="display: none">
				<tr> <td class="altbg1"> <bean:message key="a_post_threads_search_type"/> </td>
					<td class="altbg2" align="right">
						<select name="intype"> <option value="0"> &nbsp;&nbsp;&gt; <bean:message key="all"/> </option>
							<option value="0"> &nbsp;&nbsp;&gt; <bean:message key="a_post_threads_search_type_none"/> </option>
							<c:forEach var="t" items="${threadtype}">
								<option value="${t.typeid}"> &nbsp;&nbsp;&gt; ${t.name}(${t.description}) </option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <bean:message key="a_post_threads_search_viewless"/></td>
					<td class="altbg2" align="right">
					<input type="text" name="viewsless" size="40" value="${tfs.viewsless <= 0?'' : tfs.viewsless}">
					</td>
				</tr>
				<tr>
					<td class="altbg1"><bean:message key="a_post_threads_search_viewmore"/></td>
					<td class="altbg2" align="right">
					<input type="text" name="viewsmore" size="40" value="${tfs.viewsmore <= 0?'' : tfs.viewsmore}">
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <bean:message key="a_post_threads_search_replyless"/> </td>
					<td class="altbg2" align="right">
					<input type="text" name="repliesless" size="40" value="${tfs.repliesless <= 0?'' : tfs.repliesless}">
					</td>
				</tr>
				<tr>
					<td class="altbg1"><bean:message key="a_post_threads_search_replymore"/></td>
					<td class="altbg2" align="right">
					<input type="text" name="repliesmore" size="40" value="${tfs.repliesmore <= 0?'' : tfs.repliesmore}">
					</td>
				</tr>
				<tr>
					<td class="altbg1"><bean:message key="a_post_threads_search_readpermmore"/></td>
					<td class="altbg2" align="right">
					<input type="text" name="readpermmore" size="40" value="${tfs.readpermmore <= 0? '' : tfs.readpermmore}">
					</td>
				</tr>
				<tr>
					<td class="altbg1"><bean:message key="a_post_threads_search_pricemore"/></td>
					<td class="altbg2" align="right"><input type="text" name="pricemore" size="40" value="${tfs.pricemore <= 0?'' : tfs.pricemore}"></td>
				</tr>
				<tr>
					<td class="altbg1"><bean:message key="a_post_threads_search_noreplyday"/></td>
					<td class="altbg2" align="right"><input type="text" name="noreplydays" size="40" value="${tfs.noreplydays <= 0?'' : tfs.noreplydays}"></td>
				</tr>
				<tr>
					<td class="altbg1"> <bean:message key="a_post_threads_special"/></td>
					<td class="altbg2" align="right">
						<input class="radio" type="radio" name="specialthread" value="0" onclick="$('showspecial').style.display='none'" checked> <bean:message key="unlimited"/>&nbsp;
						<input class="radio" type="radio" name="specialthread" value="1" onclick="$('showspecial').style.display=''"> <bean:message key="a_post_threads_search_include_yes"/>&nbsp;
						<input class="radio" type="radio" name="specialthread" value="2" onclick="$('showspecial').style.display=''"> <bean:message key="a_post_threads_search_include_no"/>
						<div id="showspecial" style="display:none">
							<input class="checkbox" type="checkbox" name="special" value="1"> <bean:message key="threads_special_poll"/>&nbsp;
							<input class="checkbox" type="checkbox" name="special" value="2"> <bean:message key="threads_special_trade"/>&nbsp;
							<input class="checkbox" type="checkbox" name="special" value="3"> <bean:message key="threads_special_reward"/>&nbsp;
							<input class="checkbox" type="checkbox" name="special" value="4"> <bean:message key="threads_special_activity"/>&nbsp;
							<input class="checkbox" type="checkbox" name="special" value="5"> <bean:message key="special_5"/>&nbsp;
							<input class="checkbox" type="checkbox" name="special" value="6"> <bean:message key="special_6"/>&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <bean:message key="a_post_threads_search_sticky"/></td>
					<td class="altbg2" align="right">
						<input class="radio" type="radio" name="sticky" value="0" ${empty tfs.sticky  || tfs.sticky == 0 ? 'checked' : ''}> <bean:message key="unlimited"/>&nbsp;
						<input class="radio" type="radio" name="sticky" value="1" ${tfs.sticky == 1?'checked' : ''} > <bean:message key="a_post_threads_search_include_yes"/>&nbsp;
						<input class="radio" type="radio" name="sticky" value="2" ${tfs.sticky == 2?'checked' : ''}> <bean:message key="a_post_threads_search_include_no"/> 
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <bean:message key="a_post_threads_search_digest"/></td>
					<td class="altbg2" align="right">
						<input class="radio" type="radio" name="digest" value="0" ${tfs.digest == 0?'checked' : ''}> <bean:message key="unlimited"/>&nbsp;
						<input class="radio" type="radio" name="digest" value="1" ${tfs.digest == 1?'checked' : ''}> <bean:message key="a_post_threads_search_include_yes"/>&nbsp;
						<input class="radio" type="radio" name="digest" value="2" ${tfs.digest == 2?'checked' : ''}> <bean:message key="a_post_threads_search_include_no"/>
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <bean:message key="a_post_threads_search_blog"/></td>
					<td class="altbg2" align="right">
						<input class="radio" type="radio" name="blog" value="0" ${empty  tfs.blog ||tfs.blog == 0 ?'checked' : ''}> <bean:message key="unlimited"/>&nbsp;
						<input class="radio" type="radio" name="blog" value="1" ${tfs.blog == 1?'checked' : ''}> <bean:message key="a_post_threads_search_include_yes"/>&nbsp;
						<input class="radio" type="radio" name="blog" value="2" ${tfs.blog == 2?'checked' : ''}> <bean:message key="a_post_threads_search_include_no"/> 
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <bean:message key="a_post_threads_search_attach"/></td>
					<td class="altbg2" align="right">
						<input class="radio" type="radio" name="attach" value="0" ${empty tfs.attach || tfs.attach == 0?'checked' : ''}><bean:message key="unlimited"/>&nbsp;
						<input class="radio" type="radio" name="attach" value="1" ${tfs.attach == 1?'checked' : ''}> <bean:message key="a_post_threads_search_include_yes"/>&nbsp;
						<input class="radio" type="radio" name="attach" value="2" ${tfs.attach == 2?'checked' : ''}> <bean:message key="a_post_threads_search_include_no"/>
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <bean:message key="a_post_threads_rate"/></td>
					<td class="altbg2" align="right">
						<input class="radio" type="radio" name="rate" value="0" ${empty tfs.rate || tfs.rate == 0?'checked' : ''}> <bean:message key="unlimited"/>&nbsp;
						<input class="radio" type="radio" name="rate" value="1" ${tfs.rate == 1?'checked' : ''}> <bean:message key="a_post_threads_search_include_yes"/>&nbsp;
						<input class="radio" type="radio" name="rate" value="2" ${tfs.rate == 2?'checked' : ''}> <bean:message key="a_post_threads_search_include_no"/>
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <bean:message key="a_post_threads_highlight"/></td>
					<td class="altbg2" align="right">
						<input class="radio" type="radio" name="highlight" value="0" ${empty tfs.highlight || tfs.highlight == 0?'checked' : ''}> <bean:message key="unlimited"/>&nbsp;
						<input class="radio" type="radio" name="highlight" value="1" ${tfs.highlight == 1?'checked' : ''}> <bean:message key="a_post_threads_search_include_yes"/>&nbsp;
						<input class="radio" type="radio" name="highlight" value="2" ${tfs.highlight == 2?'checked' : ''}> <bean:message key="a_post_threads_search_include_no"/>
					</td>
				</tr>
				<logic:notEmpty name="tfs"> <c:remove var="tfs" /> </logic:notEmpty>
			</tbody>
		</table>
		<br />
		<center>
			<input class="button" type="submit" name="searchsubmit" value="<bean:message key="submit"/>">
		</center>
	</form>
</td>
</tr>
</table>
	<form method="post" action="admincp.jsp?action=threads&disposal=yes" target="threadframe">
		
		<logic:notEmpty name="nofirst">
			<logic:empty name="threadsList">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
					<tr class="header"> <td colspan="2"> <bean:message key="a_post_threads_result"/> 0 </td> </tr>
					<tr> <td class="altbg2" colspan="2"> <bean:message key="a_post_threads_thread_nonexistence"/> </td> </tr>
				</table>
			</logic:empty>
		</logic:notEmpty>
		
		<logic:notEmpty name="threadsList">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
				<tr class="header"> <td colspan="2"> <bean:message key="a_post_threads_result"/>${totalsize} </td> </tr>
				<input type="hidden" name="tids" value="${accountTid}">
				<input type="hidden" name="fids" value="${fids}">
				<tr>
					<td class="altbg1"> <input class="radio" type="radio" name="operation" value="moveforum" onclick="this.form.modsubmit.disabled=false;"> <bean:message key="a_post_threads_move_forum"/> </td>
					<td class="altbg2"> <select name="toforum"> ${forumselect} </select> </td>
				</tr>
				<tr>
					<td class="altbg1"> <input class="radio" type="radio" name="operation" value="movetype" onclick="this.form.modsubmit.disabled=false;"> <bean:message key="a_post_threads_move_type"/> </td>
					<td class="altbg2">
						<select name="totype"> <option value="0"> &nbsp;&nbsp;&gt; <bean:message key="a_post_threads_search_type_none"/> </option>
							<c:forEach var="t" items="${threadtype}"> <option value="${t.typeid }"> &nbsp;&nbsp;&gt; ${t.name}(${t.description}) </option> </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <input class="radio" type="radio" name="operation" value="delete" onclick="this.form.modsubmit.disabled=false;"> <bean:message key="a_post_threads_delete"/> </td>
					<td class="altbg2"> <input class="checkbox" type="checkbox" name="donotupdatemember" value="1" checked> <bean:message key="post_no_update_member"/> </td>
				</tr>
				<tr>
					<td class="altbg1"> <input class="radio" type="radio" name="operation" value="stick" onclick="this.form.modsubmit.disabled=false;"> <bean:message key="a_post_threads_stick"/> </td>
					<td class="altbg2">
						<input class="radio" type="radio" name="stick_level" value="0" checked> <bean:message key="a_post_threads_remove"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="stick_level" value="1"> <bean:message key="forums_stick_one"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="stick_level" value="2"> <bean:message key="forums_stick_two"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="stick_level" value="3"> <bean:message key="forums_stick_three"/>
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <input class="radio" type="radio" name="operation" value="adddigest" onclick="this.form.modsubmit.disabled=false;"> <bean:message key="a_post_threads_add_digest"/> </td>
					<td class="altbg2">
						<input class="radio" type="radio" name="digest_level" value="0" checked> <bean:message key="a_post_threads_remove"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="digest_level" value="1"> <bean:message key="forums_digest_one"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="digest_level" value="2"> <bean:message key="forums_digest_two"/>&nbsp; &nbsp;
						<input class="radio" type="radio" name="digest_level" value="3"> <bean:message key="forums_digest_three"/>
					</td>
				</tr>
				<tr>
					<td class="altbg1"> <input class="radio" type="radio" name="operation" value="addstatus" onclick="this.form.modsubmit.disabled=false;"> <bean:message key="a_post_threads_open_close"/> </td>
					<td class="altbg2">
						<input class="radio" type="radio" name="status" value="0" checked> <bean:message key="open"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="status" value="-1"> <bean:message key="closed"/> &nbsp; &nbsp;
				</tr>
				<tr>
					<td class="altbg1"> <input class="radio" type="radio" name="operation" value="deleteattach" onclick="this.form.modsubmit.disabled=false;"> <bean:message key="a_post_threads_delete_attach"/>
					</td> <td class="altbg2"> &nbsp; </td>
				</tr>
			</table>
			<br />
			<div class="top3" id="thewait" style='display:${isDisplay}'>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
					<tr class="header">
						<td> <input name="chkall" type="checkbox" class="checkbox" checked onclick="checkall(this.form, 'tidarray', 'chkall')"> </td>
						<td> <bean:message key="subject"/> </td>
						<td> <bean:message key="forum"/> </td>
						<td> <bean:message key="author"/> </td>
						<td nowrap> <bean:message key="threads_replies"/> </td>
						<td nowrap> <bean:message key="a_post_threads_views"/> </td>
						<td> <bean:message key="a_post_threads_lastpost"/> </td>
					</tr>
							
						<c:if test="${totalsize > 10}">
							<div class="pages">
								<em>&nbsp;${totalsize}&nbsp;</em>
								
								<c:if test="${totalpage>10 && currentpage>=4}">
									<a href="admincp.jsp?action=threadssearch&amp;page=1" class="first">1 ...</a>
								</c:if>
								
								<c:if test="${currentpage != 1}">
									<a href="admincp.jsp?action=threadssearch&amp;page=${currentpage-1}" class="prev">&lsaquo;&lsaquo;</a>
								</c:if>
								<c:choose>
									<c:when test="${totalpage>10 && currentpage>=4 && totalpage-(currentpage-2)>=10}">
										
										<c:forEach var="num" begin="${currentpage-2}" end="${(currentpage-2)+9}" step="1">
											<c:choose>
												<c:when test="${currentpage == num}">
													<strong>${currentpage}</strong>
												</c:when>
												<c:otherwise>
													<a href="admincp.jsp?action=threadssearch&amp;page=${num}">${num}</a>
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
															<a href="admincp.jsp?action=threadssearch&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=threadssearch&amp;page=${num}">${num}</a>
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
																	<a href="admincp.jsp?action=threadssearch&amp;page=${num}">${num}</a>
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
									<a href="admincp.jsp?action=threadssearch&amp;page=${currentpage+1}" class="next">&rsaquo;&rsaquo;</a>
								</c:if>
								
								<c:if test="${totalpage>10 && (totalpage-currentpage)>7}">
									<a href="admincp.jsp?action=threadssearch&amp;page=${totalpage}" class="last">... ${totalpage}</a>
								</c:if>
								
								<c:if test="${totalpage>10}">
									<kbd> <input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=threadssearch&amp;page='+this.value; return false;}" /> </kbd>
								</c:if>
						</c:if>
			
					<c:forEach var="t" items="${threadsList}">
						<tr>
							<td align="center" class="altbg1">
								<input class="checkbox" type="checkbox" name="tidarray[${t.tid }]" value="${t.tid }" checked>
							<td class="altbg2">
								<a href="viewthread.jsp?tid=${t.tid}" target="_blank"> ${t.subject} </a>
								<b> <c:if test="${t.readperm > 0}"> -[<bean:message key="threads_readperm"/> ${t.readperm}]
								</c:if> <c:if test="${t.price >0 }">  - [<bean:message key="a_post_threads_price"/> ${t.price}]
								</c:if> </b>
							</td>
							<td class="altbg1">
								<a href="forumdisplay.jsp?fid=${t.fid}" target="_blank">${t.name}</a>
							</td>
							<td align="center" class="altbg2">
								<a href="space.jsp?action=viewpro&uid=${t.authorid}" target="_blank">${t.author }</a>
							</td>
							<td align="center" class="altbg1">
								${t.replies}
							</td>
							<td align="center" class="altbg2">
								${t.views}
							</td>
							<td align="center" class="altbg1">
								<jrun:showTime timeInt="${t.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</logic:notEmpty>
		<c:if test="${nofirst!=null}">
			<center>
				<input class="button" type="submit" name="modsubmit" value="<bean:message key="submit"/>"  disabled>
			</center>
		</c:if>
	</form>
	<iframe name="threadframe" style="display:none"></iframe>
<jsp:directive.include file="../cp_footer.jsp" />