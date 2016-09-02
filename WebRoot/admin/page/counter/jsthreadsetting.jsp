<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='../../admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_javascript" /></td></tr>
</table>
<br />
<a name="ace21f06b2c1d25b"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="2">
			<bean:message key="menu_tool_javascript" /> <a href="###" onclick="collapse_change('ace21f06b2c1d25b')"><img id="menuimg_ace21f06b2c1d25b" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
		</td>
	</tr>
	<tbody id="menu_ace21f06b2c1d25b" style="display: yes">
		<tr>
			<td class="altbg2">
				<a href="admincp.jsp?action=gojssetting"><bean:message key="header_basic" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="admincp.jsp?action=jswizard"><bean:message key="a_system_js_project" /></a>
			</td>
		</tr>
	</tbody>
</table>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" style="display : ${diaplay==null?'none' : ''}">
	<tr class="header">
		<td colspan="2">
			<bean:message key="preview" /> <a href="###" onclick="collapse_change('0896486085a06b32')"><img id="menuimg_0896486085a06b32" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
		</td>
	</tr>
	<tbody id="menu_0896486085a06b32" style="display:yes">
		<tr>
			<td class="altbg1">
				<textarea rows="3" style="width: 100%; word-break: break-all" onMouseOver="this.focus()" onFocus="this.select()" type="_moz">&lt;script language=&quot;JavaScript&quot; src=&quot;${boardurl}api/javascript.jsp?key=${inentifier==null?jsname: inentifier}&quot;&gt;&lt;/script&gt;</textarea>
				<div class="jswizard"><script type="text/javascript" src="${path}"></script></div><br />
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=jswizard&editjsthreads=yes">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="cbdea6df76e91c50"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="a_system_js_jstemplate" /> <a href="###" onclick="collapse_change('cbdea6df76e91c50')"><img id="menuimg_cbdea6df76e91c50" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
			</td>
		</tr>
<tbody id="menu_cbdea6df76e91c50" style="display: yes">
	<tr>
		<td class="altbg1" colspan="2"><bean:message key="a_system_js_forums_jstemplate_comment" />
			<a href="###" onclick="insertunit('(prefix)')">(prefix)</a><bean:message key="a_system_js_forums_jstemplate_comment16" />
			<a href="###" title="<bean:message key="a_system_js_exist_link" />" onclick="insertunit('(subject)')">(subject)</a>, <a title="<bean:message key="a_system_js_no_exist_link" />" href="###" onclick="insertunit('(subject_nolink)')">(subject_nolink)</a>, 
			<a title="<bean:message key="a_system_js_no_exist_link_exist_word" />" href="###" onclick="insertunit('(subject_full)')">(subject_full)</a><bean:message key="a_system_js_forums_jstemplate_comment17"/>
			<a href="###" onclick="insertunit('(message)')">(message)</a><bean:message key="a_system_js_forums_jstemplate_comment18" />
			<a href="###" onclick="insertunit('(forum)')">(forum)</a><bean:message key="a_system_js_forums_jstemplate_comment19" /><a href="###" onclick="insertunit('(author)')">(author)</a><bean:message key="a_system_js_forums_jstemplate_comment20" /><a href="###" onclick="insertunit('(dateline)')">(dateline)</a><bean:message key="a_system_js_forums_jstemplate_comment21" /><a href="###" onclick="insertunit('(lastposter)')">(lastposter)</a><bean:message key="a_system_js_forums_jstemplate_comment22" /><a href="###" onclick="insertunit('(lastpost)')">(lastpost)</a>	<bean:message key="a_system_js_forums_jstemplate_comment23" /><a href="###" onclick="insertunit('(replies)')">(replies)</a><bean:message key="a_system_js_forums_jstemplate_comment24" /><a href="###" onclick="insertunit('(views)')">(views)</a><bean:message key="a_system_js_forums_jstemplate_comment25" /><a href="###" onclick="insertunit('(link)')">(link)</a> <bean:message key="a_system_js_forums_jstemplate_comment5" />
			<br />
			<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('jstemplate', 1)">
			<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('jstemplate', 0)">
			<br />
<script>
function isUndefined(variable) {
	return typeof variable == 'undefined' ? true : false;
}
function insertunit(text) {
	$('jstemplate').focus();
	if(!isUndefined($('jstemplate').selectionStart)) {
		var opn = $('jstemplate').selectionStart + 0;
		$('jstemplate').value = $('jstemplate').value.substr(0, $('jstemplate').selectionStart) + text + $('jstemplate').value.substr($('jstemplate').selectionEnd);
	} else if(document.selection && document.selection.createRange) {
		var sel = document.selection.createRange();
		sel.text = text.replace(/\r?\n/g, '\r\n');
		sel.moveStart('character', -strlen(text));
	} else {
		$('jstemplate').value += text;
	}
}
</script>
	<c:choose>
		<c:when test="${inentifier==null}">
			<textarea cols="100" rows="5" id="jstemplate" name="parameter[jstemplate]" style="width: 95%;" type="_moz">${resultmap.parameter.jstemplate}</textarea>
		</c:when>
		<c:otherwise>
			<textarea cols="100" rows="5" id="jstemplate" name="parameter[jstemplate]" style="width: 95%;" type="_moz">(prefix)(subject)<br /></textarea>
		</c:otherwise>
	</c:choose>
	</td>
</tr>
</tbody>
</table>
<br />
<a name="afff689dbed0e867"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="2">
			<bean:message key="a_system_js_threads" /><a href="###" onclick="collapse_change('afff689dbed0e867')"><img id="menuimg_afff689dbed0e867" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
		</td>
	</tr>
	<tbody id="menu_afff689dbed0e867" style="display: yes">
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_jskey" /></b>	<br /><span class="smalltxt"><bean:message key="a_system_js_jskey_comment" /></span>
			</td>
			<td class="altbg2">
				<c:choose>
					<c:when test="${inentifier==null}">
						<input type="text" size="50" name="jskey" value="${jsname}">
					</c:when>
					<c:otherwise>
						<input type="text" size="50" name="jskey" value="${inentifier}">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_cachelife" /></b> <br />
				<span class="smalltxt"><bean:message key="a_system_js_cachelife_comment" /></span>
			</td>
			<td class="altbg2">
				<input type="text" size="50" name="parameter[cachelife]" value="${resultmap.parameter.cachelife}">
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="post_search_forum" /></b> <br />
				<span class="smalltxt"><bean:message key="a_system_js_threads_fids_comment" /></span>
			</td>
			<td class="altbg2">
				<select name="parameter[threads_forums]" size="5" multiple="multiple">
					<option value="all">
						<bean:message key="a_system_js_all_forums" />
					</option>
					<c:forEach items="${groups}" var="group">
						<c:if test="${group.status==1}">
							<optgroup label="${group.name}">
								<c:forEach items="${forums}" var="forum">
									<c:if test="${forum.status==1}">
										<c:if test="${group.fid==forum.fup}">
											<option value="${forum.fid}" ${forum.flag=='true'?'selected' : ''}>&nbsp; &gt; ${forum.name}</option>
											<c:forEach items="${subs}" var="sub">
												<c:if test="${sub.status==1}">
													<c:if test="${forum.fid==sub.fup}">
														<option value="${sub.fid}" ${sub.flag=='true'?'selected' : ''}>&nbsp; &nbsp; &nbsp; &gt; ${sub.name}</option>
													</c:if>
												</c:if>
											</c:forEach>
										</c:if>
									</c:if>
								</c:forEach>
							</optgroup>
						</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_startrow" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_startrow_comment" /></span>
			</td>
			<td class="altbg2">
				<c:choose>
					<c:when test="${inentifier!=null}">
						<input type="text" size="50" name="parameter[startrow]" value="0">
					</c:when>
					<c:otherwise>
						<input type="text" size="50" name="parameter[startrow]" value="${resultmap.parameter.startrow}">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_images_items" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_items_comment" /></span>
			</td>
			<td class="altbg2">
				<c:choose>
					<c:when test="${inentifier!=null}">
						<input type="text" size="50" name="parameter[items]" value="10">
					</c:when>
					<c:otherwise>
						<input type="text" size="50" name="parameter[items]" value="${resultmap.parameter.items}">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_maxlength" /></b>
				<br /><span class="smalltxt"><bean:message key="a_forum_edit_recommend_maxlength_comment" /></span>
			</td>
			<td class="altbg2">
				<c:choose>
					<c:when test="${inentifier!=null}">
						<input type="text" size="50" name="parameter[maxlength]" value="50">
					</c:when>
					<c:otherwise>
						<input type="text" size="50" name="parameter[maxlength]" value="${resultmap.parameter.maxlength}">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_fnamelength" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_fnamelength_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[fnamelength]" value="1" ${resultmap.parameter.fnamelength=='1'?'checked':''}>
				<bean:message key="yes" /> &nbsp; &nbsp;
				<input class="radio" type="radio" name="parameter[fnamelength]" value="0" ${empty resultmap||resultmap.parameter.fnamelength=='0'?'checked':''}>
				<bean:message key="no" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_picpre" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_picpre_comment" /></span>
			</td>
			<td class="altbg2">
				<input type="text" size="50" name="parameter[picpre]" value="${resultmap.parameter.picpre}">
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_tids" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_tids_comment" /></span>
			</td>
			<td class="altbg2">
				<input type="text" size="50" name="parameter[tids]" value="${resultmap.parameter.tids}">
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_keyword" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_keyword_comment" /></span>
			</td>
			<td class="altbg2">
				<input type="text" size="50" name="parameter[keyword]" value="${resultmap.parameter.keyword}">
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_tag" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_tag_comment" /></span>
			</td>
			<td class="altbg2">
				<input type="text" size="50" name="parameter[tag]" value="${resultmap.parameter.tag}">
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_typeids" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_typeids_comment" /></span>
			</td>
			<td class="altbg2">
				<select multiple="multiple" name="parameter[typeids]" size="5">
					<option value="all"><bean:message key="a_system_js_all_typeids" /></optoin>
						<c:forEach items="${threadtype}" var="threadtypes">
							<option value="${threadtypes.key.typeid}" ${threadtypes.value=='ok'?'selected' : ''}>${threadtypes.key.name}</optoin>
						</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_threadtype" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_threadtype_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[threadtype]" value="1" ${resultmap.parameter.threadtype=='1'?'checked':''}>
				<bean:message key="yes" /> &nbsp; &nbsp;
				<input class="radio" type="radio" name="parameter[threadtype]" value="0" ${empty resultmap||resultmap.parameter.threadtype=='0'?'checked':''}>
				<bean:message key="no" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_highlight" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_highlight_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[highlight]" value="1" ${resultmap.parameter.highlight=='1'?'checked':''}>
				<bean:message key="yes" /> &nbsp; &nbsp;
				<input class="radio" type="radio" name="parameter[highlight]" value="0" ${empty resultmap||resultmap.parameter.highlight=='0'?'checked':''}>
				<bean:message key="no" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_blog" /></b>
				<br /><span class="smalltxt"><bean:message key="a_system_js_threads_blog_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[blog]" value="1" ${resultmap.parameter.blog=='1'?'checked':''}>
				<bean:message key="yes" /> &nbsp; &nbsp;
				<input class="radio" type="radio" name="parameter[blog]" value="0" ${empty resultmap||resultmap.parameter.blog=='0'?'checked':''}>
				<bean:message key="no" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_special" /></b>
				<br /><span class="smalltxt"><bean:message key="stats_main_threads_count" /></span>
			</td>
			<c:forEach items="${resultmap.parameter.special}" var="map">
				<c:if test="${map.key=='1'}">
					<c:set scope="page" var="a" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='2'}">
					<c:set scope="page" var="b" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='3'}">
					<c:set scope="page" var="c" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='4'}">
					<c:set scope="page" var="d" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='5'}">
					<c:set scope="page" var="e" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='6'}">
					<c:set scope="page" var="f" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='0'}">
					<c:set scope="page" var="g" value="${map.value}"></c:set>
				</c:if>
			</c:forEach>
			<td class="altbg2">
				<input class="checkbox" type="checkbox" name="parameter[special]" value="1" ${a=='1'?'checked':''}>
				<bean:message key="threads_special_poll" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[special]" value="2" ${b=='1'?'checked':''}>
				<bean:message key="threads_special_trade" />
				<br />
				<input class="checkbox" type="checkbox" onclick="$('special_reward_ext').style.display = this.checked ? '' : 'none'" name="parameter[special]" value="3" ${c=='1'?'checked':''}>
				<bean:message key="threads_special_reward" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[special]" value="4" ${d=='1'?'checked':''}>
				<bean:message key="threads_special_activity" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[special]" value="5" ${e=='1'?'checked':''}>
				<bean:message key="special_5" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[special]" value="6" ${f=='1'?'checked':''}>
				<bean:message key="special_6" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[special]" value="0" ${g=='1'?'checked':''}>
				<bean:message key="a_system_js_special_0" />
			</td>
		</tr>
	</tbody>
	<tbody class="sub" id="special_reward_ext" style="display: ${c!='1'?'none':''}">
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_special_reward" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_system_js_threads_special_reward_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[rewardstatus]" value="0" ${resultmap.parameter.rewardstatus=='0'?'checked':''}>
				<bean:message key="all" />
				<br />
				<input class="radio" type="radio" name="parameter[rewardstatus]" value="1" ${resultmap.parameter.rewardstatus=='1'?'checked':''}>
				<bean:message key="a_system_js_threads_special_reward_1" />
				<br />
				<input class="radio" type="radio" name="parameter[rewardstatus]" value="2" ${resultmap.parameter.rewardstatus=='2'?'checked':''}>
				<bean:message key="a_system_js_threads_special_reward_2" />
			</td>
		</tr>
	</tbody>
	<tbody>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_digest" /></b>
				<br />
				<span class="smalltxt"><bean:message key="stats_main_threads_count" /></span>
			</td>
			<c:forEach items="${resultmap.parameter.digest}" var="map">
				<c:if test="${map.key=='1'}">
					<c:set scope="page" var="aa" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='2'}">
					<c:set scope="page" var="bb" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='3'}">
					<c:set scope="page" var="cc" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='0'}">
					<c:set scope="page" var="dd" value="${map.value}"></c:set>
				</c:if>
			</c:forEach>
			<td class="altbg2">
				<input class="checkbox" type="checkbox" name="parameter[digest]" value="1" ${aa=='1'?'checked':''}>
				<bean:message key="a_system_js_digest_1" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[digest]" value="2" ${bb=='1'?'checked':''}>
				<bean:message key="a_system_js_digest_2" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[digest]" value="3" ${cc=='1'?'checked':''}>
				<bean:message key="a_system_js_digest_3" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[digest]" value="0" ${dd=='1'?'checked':''}>
				<bean:message key="a_system_js_all_normal" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_stick" /></b>
				<br />
				<span class="smalltxt"><bean:message key="stats_main_threads_count" /></span>
			</td>
			<c:forEach items="${resultmap.parameter.stick}" var="map">
				<c:if test="${map.key=='1'}">
					<c:set scope="page" var="aaa" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='2'}">
					<c:set scope="page" var="bbb" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='3'}">
					<c:set scope="page" var="ccc" value="${map.value}"></c:set>
				</c:if>
				<c:if test="${map.key=='0'}">
					<c:set scope="page" var="ddd" value="${map.value}"></c:set>
				</c:if>
			</c:forEach>
			<td class="altbg2">
				<input class="checkbox" type="checkbox" name="parameter[stick]" value="1" ${aaa=='1'?'checked':''}>
				<bean:message key="a_system_js_stick_1" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[stick]" value="2" ${bbb=='1'?'checked':''}>
				<bean:message key="a_system_js_stick_2" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[stick]" value="3" ${ccc=='1'?'checked':''}>
				<bean:message key="a_system_js_stick_3" />
				<br />
				<input class="checkbox" type="checkbox" name="parameter[stick]" value="0" ${ddd=='1'?'checked':''}>
				<bean:message key="a_system_js_all_normal" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_newwindow" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_system_js_threads_newwindow_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[newwindow]" value="0" ${resultmap.parameter.newwindow=='0'?'checked':''}>
				<bean:message key="a_system_js_newwindow_self" />
				<br />
				<input class="radio" type="radio" name="parameter[newwindow]" value="1" ${empty resultmap||resultmap.parameter.newwindow=='1'?'checked':''}>
				<bean:message key="a_system_js_newwindow_blank" />
				<br />
				<input class="radio" type="radio" name="parameter[newwindow]" value="2" ${resultmap.parameter.newwindow=='2'?'checked':''}>
				<bean:message key="a_system_js_newwindow_main" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_threads_orderby" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_system_js_threads_orderby_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[orderby]" value="lastpost" ${empty resultmap.parameter.orderby || resultmap.parameter.orderby=='lastpost'?'checked':''}>
				<bean:message key="a_system_js_threads_orderby_lastpost" />
				<br />
				<input class="radio" type="radio" name="parameter[orderby]" value="dateline" ${resultmap.parameter.orderby=='dateline'?'checked':''}>
				<bean:message key="a_system_js_threads_orderby_dateline" />
				<br />
				<input class="radio" type="radio" name="parameter[orderby]" value="replies" ${resultmap.parameter.orderby=='replies'?'checked':''}>
				<bean:message key="a_system_js_threads_orderby_replies" />
				<br />
				<input class="radio" type="radio" name="parameter[orderby]" value="views" ${resultmap.parameter.orderby=='views'?'checked':''}>
				<bean:message key="a_system_js_threads_orderby_views" />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="a_system_js_charset" /></b>
				<br />
				<span class="smalltxt"><bean:message key="a_system_js_charsetr_comment" /></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="parameter[jscharset]" value="0" ${resultmap.parameter.jscharset=='0'||resultmap==null?'checked':'' }>
				<bean:message key="no" />
					<br />
					<input class="radio" type="radio" name="parameter[jscharset]" value="1" ${resultmap.parameter.jscharset=='1'?'checked':'' } >
					${charset} <br />
				</td>
			</tr>
		</tbody>
	</table>
<input type="hidden" name="edit" value="${jsname}">
<br />
<center>
<input class="button" type="submit" name="jssubmit" value="<bean:message key="a_system_js_preview" />">&nbsp; &nbsp;
<input class="button" type="button" onclick="this.form.preview.value=0;this.form.jssubmit.click()" value="<bean:message key="submit" />">
<input name="preview" type="hidden" value="1">
</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />