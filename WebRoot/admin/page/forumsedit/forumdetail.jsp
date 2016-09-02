<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_forum_detail"/></td></tr>
</table><br />
<script language="javascript" type="text/javascript">
	var typenum = 1;
	function addtype() {
		if(typenum > 9) return;
		$('type_' + typenum).style.display = '';
		typenum++;
	}
	function isRadioChecked(name,value){
		if(value!=''){
			var radios=document.getElementsByName(name);
			if(radios!=null){
				var i=0;
				for(i;i<radios.length;i++){
					var radio=radios[i];
					if(radio.value == value && radio.type == 'radio' && radio.disabled != true) {
						radio.checked = true;
					}
				}
			}
		}
	}
	function isCheckboxChecked(name,value){
		if(value!=''){
			var checks=document.getElementsByName(name);
			if(checks!=null){
				var i=0;
				for(i;i<checks.length;i++){
					var check=checks[i];
					if(value=='1'||check.value == value && check.type == 'check' && check.disabled != true) {
						check.checked = true;
					}
					else if(value=='0'||check.value == value && check.type == 'check' && check.disabled != true){
						check.checked = false;
					}
				}
			}
		}
	}
	function isSelectChecked(name,value){
		if(value!=''){
			var selected=document.getElementsByName(name);
			if(selected!=null){	
				var i=0;
				for(i;i<selected.length;i++){
					var select=selected[i];
					select.value=value;
				}
			}
		}
	}
	function check(){
		var formulapermnew=document.getElementById("formulapermnew").value;
		var regex1=/^(\+|\-|\*|\/|\.|>|<|=|\d|\s|extcredits[1-8]|digestposts|posts|pageviews|oltime|and|or)+$/;
		var regex2=/(digestposts|posts|pageviews|oltime|extcredits[1-8])/g;
		var regex3=/((and|or)(\s)*(and|or))/g
		var regex4=/(^(\s)*(and|or))|((and|or)(\s)*$)/g;
		var regex5=/(and|or)/g;
		var regex6=/val(\s)*val/g;
		var val=1;
		var newformulaperm=formulapermnew.replace(regex2,"val");
		if(formulapermnew!="" && !regex1.test(formulapermnew)){
			document.getElementById("checkResult").value="false";
		}else if(regex3.test(formulapermnew)||regex4.test(formulapermnew)){
			document.getElementById("checkResult").value="false";
		}else if(!regex6.test(newformulaperm)){
			var values=newformulaperm.split(regex5);
			var i=0;
			for(i;i<values.length;i++){
				if(values[i]!='or'&&values[i]!='and'){
					eval(values[i]);
				}
			}
			document.getElementById("checkResult").value="true";
		}else{
			document.getElementById("checkResult").value="false";
		}
	}
</script>
<c:choose><c:when test="${forum.type=='group'}">
<form method="post" action="admincp.jsp?action=forumdetail&fid=${forum.fid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_forum_cat_detail"/> - ${forum.name}<a href="###" onclick="collapse_change('f32b51bd67bdcbb0')"><img id="menuimg_f32b51bd67bdcbb0" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
	<tbody id="menu_f32b51bd67bdcbb0" style="display: yes">
		<tr><td width="45%" class="altbg1"><b><bean:message key="a_forum_cat_name"/>:</b></td><td class="altbg2"><input type="text" size="50" name="name" value="<jrun:dhtmlspecialchars value="${forum.name}"/>"></td></tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_sub_horizontal"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_sub_horizontal_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="forumcolumns" value="${forum.forumcolumns}"></td>
		</tr>
	</tbody>
	</table><br />
	<center><input class="button" type="submit" name="detailsubmit" value="<bean:message key='submit'/>"></center>
</form>
</c:when><c:otherwise>
<form method="post" action="admincp.jsp?action=forumdetail&fid=${forum.fid}" onsubmit="return check();">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}"><tr><td><bean:message key="a_forum_edit_tips"/></td></tr></tbody>
	</table><br />
	<a name="5dd06f7f4cd8c362"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_forum_detail"/><a href="###" onclick="collapse_change('5dd06f7f4cd8c362')"><img id="menuimg_5dd06f7f4cd8c362" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_5dd06f7f4cd8c362" style="display: yes"><tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_scheme"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_scheme_comment"/></span></td>
			<td class="altbg2">
				<select name="projectId" onchange="window.location='admincp.jsp?action=forumdetail&fid=${forum.fid}&projectId='+this.value">
					<option value="0" selected="selected"><bean:message key="none"/></option>
					<c:forEach items="${projects}" var="project"><option value="${project.id}">${project.name}</option></c:forEach>
				</select>
			</td>
		</tr></tbody>
	</table><br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_forum_basic_setting"/><a href="###" onclick="collapse_change('846ed2c056ad9a1f')"><img id="menuimg_846ed2c056ad9a1f" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_846ed2c056ad9a1f" style="display: yes">
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="forums_name"/>:</b></td>
			<td class="altbg2"><input type="text" size="50" name="name" value="<jrun:dhtmlspecialchars value="${forum.name}"/>"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_display"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_display_comment"/></span></td>
			<td class="altbg2"><input class="radio" type="radio" name="status" value="1"> <bean:message key="yes"/> &nbsp; &nbsp; <input class="radio" type="radio" name="status" value="0"> <bean:message key="no"/></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_perm_passwd"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_perm_passwd_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="password" value="${forumfield.password}" maxlength="12"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_up"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_up_comment"/></span></td>
			<td class="altbg2"><select name="fup">${fupselect}</select></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_redirect"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_redirect_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="redirect" value="${forumfield.redirect}"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_icon"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_icon_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="icon" value="${forumfield.icon}" maxlength="255"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" valign="top"><b><bean:message key="a_forum_edit_description"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_description_comment"/></span></td>
			<td class="altbg2"> <img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 0)"><br /><textarea rows="6" name="description" id="descriptionnew" cols="50" type="_moz"><jrun:dhtmlspecialchars value="${forumfield.description}"/></textarea></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" valign="top"><b><bean:message key="a_forum_edit_rules"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_rules_comment"/></span></td>
			<td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('rules', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('rules', 0)"><br /><textarea rows="6" name="rules" id="rules" cols="50" type="_moz"><jrun:dhtmlspecialchars value="${forumfield.rules}"/></textarea></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_keyword"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_keyword_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="keywords" value="${forumfield.keywords}"></td>
		</tr>
	</table><br />
	<a name="53f462dfa98993b8"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_forum_extend_conf"/><a href="###" onclick="collapse_change('53f462dfa98993b8')"><img id="menuimg_53f462dfa98993b8" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_53f462dfa98993b8" style="display: yes">
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_style"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_style_comment"/></span></td>
			<td class="altbg2"><select name="styleid">
				<option value="0" selected="selected"><bean:message key="use_default"/></option>
				<c:forEach items="${styleTemplages}" var="style"><option value="${style.styleid}" ${forum.styleid == style.styleid ? "selected" : ""}>${style.name}</option></c:forEach>
			</select></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_sub_horizontal"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_sub_horizontal_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="forumcolumns" value="${forum.forumcolumns}"></td>
		</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_subforumsindex"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_subforumsindex_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="subforumsindex" ${subforumsindex==-1 ? "checked" : ""} value="-1"> <bean:message key="default"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="subforumsindex" ${subforumsindex==1 ? "checked" : ""} value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="subforumsindex" ${subforumsindex==0 ? "checked" : ""} value="0"> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_simple"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_simple_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="simple" ${simple==1 ? "checked" : ""} value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="simple" ${simple==0 ? "checked" : ""} value="0"> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><u><i><bean:message key="a_forum_edit_defaultorderfield"/>:</i></u></b><br /><span class="smalltxt"><bean:message key="a_forum_edit_defaultorderfield_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="defaultorderfield" value="0" ${defaultorderfield==0 ? "checked" : ""}> <bean:message key="a_forum_edit_lastpost"/><br />
					<input class="radio" type="radio" name="defaultorderfield" value="1" ${defaultorderfield==1 ? "checked" : ""}> <bean:message key="a_forum_edit_starttime"/><br />
					<input class="radio" type="radio" name="defaultorderfield" value="2" ${defaultorderfield==2 ? "checked" : ""}> <bean:message key="a_forum_edit_replies"/><br />
					<input class="radio" type="radio" name="defaultorderfield" value="3" ${defaultorderfield==3 ? "checked" : ""}> <bean:message key="a_forum_edit_views"/><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><u><i><bean:message key="a_forum_edit_defaultorder"/>:</i></u></b><br /><span class="smalltxt"><bean:message key="a_forum_edit_defaultorder_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="defaultorder" value="0" checked> <bean:message key="a_forum_edit_desc"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="defaultorder" value="1" ${defaultorder==1 ? "checked" : ""}> <bean:message key="a_forum_edit_asc"/> &nbsp; &nbsp;
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_threadcache"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_threadcache_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="threadcaches" value="${forum.threadcaches}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_rule_eidt"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_rule_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="alloweditrules" value="0"> <bean:message key="a_forum_edit_rule_html_none"/><br />
					<input class="radio" type="radio" name="alloweditrules" value="1"> <bean:message key="a_forum_edit_rule_html_no"/><br />
					<input class="radio" type="radio" name="alloweditrules" value="2"> <bean:message key="a_forum_edit_rule_html_yes"/><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_recommend"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_recommend_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="modrecommend[open]" value="1" onclick="$('hidden_forums_edit_recommend').style.display = '';"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="modrecommend[open]" value="0" onclick="$('hidden_forums_edit_recommend').style.display = 'none';" checked> <bean:message key="no"/>
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden_forums_edit_recommend" style="display: none">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_recommend_sort"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_recommend_sort_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="modrecommend[sort]" value="0"> <bean:message key="a_forum_edit_recommend_sort_manual"/><br />
					<input class="radio" type="radio" name="modrecommend[sort]" value="1"> <bean:message key="a_forum_edit_recommend_sort_auto"/><br />
					<input class="radio" type="radio" name="modrecommend[sort]" value="2"> <bean:message key="a_forum_edit_recommend_sort_mix"/><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_recommend_orderby"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_recommend_orderby_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="modrecommend[orderby]" value="0"> <bean:message key="a_forum_edit_recommend_orderby_dateline"/><br />
					<input class="radio" type="radio" name="modrecommend[orderby]" value="1"> <bean:message key="a_forum_edit_recommend_orderby_lastpost"/><br />
					<input class="radio" type="radio" name="modrecommend[orderby]" value="2"> <bean:message key="a_forum_edit_recommend_orderby_views"/><br />
					<input class="radio" type="radio" name="modrecommend[orderby]" value="3"> <bean:message key="a_forum_edit_recommend_orderby_replies"/><br />
					<input class="radio" type="radio" name="modrecommend[orderby]" value="4"> <bean:message key="a_forum_edit_recommend_orderby_digest"/><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_recommend_num"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_recommend_num_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="modrecommend[num]" value="${modrecommend['num']}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_recommend_maxlength"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_recommend_maxlength_comment"/></span></td>
				<td class="altbg2"> <input type="text" size="50" name="modrecommend[maxlength]" value="${modrecommend['maxlength']}"> </td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_recommend_cachelife"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_recommend_cachelife_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="modrecommend[cachelife]" value="${modrecommend['cachelife']}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_recommend_dateline"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_recommend_dateline_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="modrecommend[dateline]" value="${modrecommend['dateline']}"></td>
			</tr>
		</tbody>
		<tbody>
	</table>
	<br />
	<a name="fef7fd6db321cb29"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_forum_edit_options"/><a href="###" onclick="collapse_change('fef7fd6db321cb29')"><img id="menuimg_fef7fd6db321cb29" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_fef7fd6db321cb29" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_modpost"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_modpost_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="modnewposts" value="0"> <bean:message key="none"/><br />
					<input class="radio" type="radio" name="modnewposts" value="1"> <bean:message key="menu_post_modthreads"/><br />
					<input class="radio" type="radio" name="modnewposts" value="2"> <bean:message key="a_forum_edit_modpost_post"/><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_alloweditpost"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_alloweditpost_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="alloweditpost" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="alloweditpost" value="0"> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="menu_post_recyclebin"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_recyclebin_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="recyclebin" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="recyclebin" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="group_allowhtml"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_html_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowhtml" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowhtml" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_bbcode"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_bbcode_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowbbcode" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowbbcode" value="0"> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_imgcode"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_imgcode_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowimgcode" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowimgcode" value="0"> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_mediacode"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_mediacode_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowmediacode" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowmediacode" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_smilies"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_smilies_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowsmilies" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowsmilies" value="0"> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_jammer"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_jammer_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="jammer" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="jammer" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_anonymous"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_anonymous_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowanonymous" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowanonymous" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_disablewatermark"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_disablewatermark_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="disablewatermark" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="disablewatermark" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_allowpostspecial"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_allowpostspecial_comment"/></span></td>
				<td class="altbg2">
					<input class="checkbox" type="checkbox" name="allowpostspecial[1]" value="1" checked> <bean:message key="threads_special_poll"/><br />
					<input class="checkbox" type="checkbox" name="allowpostspecial[2]" value="1" checked> <bean:message key="threads_special_trade"/><br />
					<input class="checkbox" type="checkbox" name="allowpostspecial[3]" value="1" checked> <bean:message key="threads_special_reward"/><br />
					<input class="checkbox" type="checkbox" name="allowpostspecial[4]" value="1" checked> <bean:message key="threads_special_activity"/><br />
					<input class="checkbox" type="checkbox" name="allowpostspecial[5]" value="1" checked> <bean:message key="special_5"/><br />
					<input class="checkbox" type="checkbox" name="allowpostspecial[6]" value="1" checked> <bean:message key="special_6"/><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_allowspecialonly"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_allowspecialonly_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowspecialonly" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowspecialonly" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<c:if test="${!empty tradetypeselect}">
			<tr><td width="45%" class="altbg1" ><b><bean:message key="a_forum_edit_trade_type"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_trade_type_comment"/></span></td><td class="altbg2">${tradetypeselect}</td></tr>
			</c:if>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_trade_payto"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_trade_payto_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowpaytoauthor" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowpaytoauthor" value="0"> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_autoclose"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_autoclose_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="autoclose" value="0" checked onclick="this.form.autoclosetime.disabled=true;"> <bean:message key="a_forum_edit_autoclose_none"/><br />
					<input class="radio" type="radio" name="autoclose" value="1" ${autoclose==1?'checked':''} onclick="this.form.autoclosetime.disabled=false;"> <bean:message key="a_forum_edit_autoclose_dateline"/><br />
					<input class="radio" type="radio" name="autoclose" value="-1" ${autoclose==-1?'checked':''} onclick="this.form.autoclosetime.disabled=false;"> <bean:message key="a_forum_edit_autoclose_lastpost"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">	<b><bean:message key="a_forum_edit_autoclose_time"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_autoclose_time_comment"/></span></td>
				<td class="altbg2"><input type="text" size="30" value="${autoclosetime}" name="autoclosetime" ${autoclosetime==0?"disabled":""}></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_attach_ext"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_attach_ext_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" name="attachextensions" value="${forumfield.attachextensions}"></td>
			</tr>
	</table>
	<br />
	<a name="80b93d26d0c54f90"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="7"><bean:message key="menu_setting_credits"/><a href="###" onclick="collapse_change('80b93d26d0c54f90')"><img id="menuimg_80b93d26d0c54f90" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_80b93d26d0c54f90" style="display: yes">
			<tr class="category">
				<td><bean:message key="credits_id"/></td>
				<td><bean:message key="credits_title"/></td>
				<td><bean:message key="a_setting_credits_policy_post"/></td>
				<td><bean:message key="a_setting_credits_policy_reply"/></td>
				<td><bean:message key="a_setting_credits_policy_digest"/></td>
				<td><bean:message key="a_setting_credits_policy_post_attach"/></td>
				<td><bean:message key="a_setting_credits_policy_get_attach"/></td>
			</tr>
			<c:forEach items="${extcredits}" var="extcredit">
				<c:if test="${extcredit.value.available==1}">
					<tr align="center">
						<td class="altbg1" width="10%">extcredits${extcredit.key}</td>
						<td class="altbg2" width="10%">${extcredit.value.title}</td>
						<td class="altbg1" width="12%"><input type="text" size="2" name="postcredits[${extcredit.key}]" value="${postcredits[extcredit.key]}"></td>
						<td class="altbg2" width="12%"><input type="text" size="2" name="replycredits[${extcredit.key}]" value="${replycredits[extcredit.key]}"></td>
						<td class="altbg1" width="12%"><input type="text" size="2" name="digestcredits[${extcredit.key}]" value="${digestcredits[extcredit.key]}"></td>
						<td class="altbg2" width="12%"><input type="text" size="2" name="postattachcredits[${extcredit.key}]" value="${postattachcredits[extcredit.key]}"></td>
						<td class="altbg1" width="12%"><input type="text" size="2" name="getattachcredits[${extcredit.key}]" value="${getattachcredits[extcredit.key]}"></td>
					</tr>
				</c:if>
			</c:forEach>
			<tr><td colspan="7"><bean:message key="a_forum_edit_credits_comment"/><br /><a href="member.jsp?action=credits&view=forum_post&fid=${forum.fid}" target="_blank"><bean:message key="a_forum_edit_credits_preview"/></a></td></tr>
		</tbody>
	</table>
	<br />
	<a name="ad973ce320df9c50"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_forum_threadtypes"/> <a href="###" onclick="collapse_change('ad973ce320df9c50')"><img id="menuimg_ad973ce320df9c50" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_ad973ce320df9c50" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_threadtypes_status"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_threadtypes_status_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="threadtypes[status]" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="threadtypes[status]" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_threadtypes_required"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_threadtypes_required_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="threadtypes[required]" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="threadtypes[required]" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_threadtypes_listable"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_threadtypes_listable_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="threadtypes[listable]" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="threadtypes[listable]" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_edit_threadtypes_prefix"/>:</b><br /><span class="smalltxt"><bean:message key="a_forum_edit_threadtypes_prefix_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="threadtypes[prefix]" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="threadtypes[prefix]" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="10%"><bean:message key="a_forum_edit_cat_name"/></td>
			<td width="25%"><bean:message key="a_forum_sort_note"/></td>
			<td width="10%"><bean:message key="not_use"/></td>
			<td width="15%"><bean:message key="a_forum_threadtypes_usecols"/></td>
			<td width="15%"><bean:message key="a_forum_threadtypes_usechoice"/></td>
			<td width="20%"><bean:message key="a_forum_threadtypes_show"/></td>
		</tr>
		<c:if test="${threadtypes==null}">
			<tr><td class="altbg1" colspan="6"><bean:message key="a_forum_edit_threadtypes_options_null"/></td></tr>
		</c:if>
		<c:forEach items="${threadtypes}" var="threadtype">
			<c:if test="${threadtype.special!=1||hasTypevars[threadtype.typeid]=='true'}">
				<tr align="center">
					<td class="altbg1">${threadtype.name}</td>
					<td class="altbg2">${threadtype.description}<c:if test="${threadtype.special==1}">&nbsp;<bean:message key="menu_threadtype"/></c:if></td>
					<td class="altbg1"><input class="radio" type="radio" name="threadtypes[options][${threadtype.typeid}]" value="0" checked></td>
					<td class="altbg2"><input class="radio" type="radio" name="threadtypes[options][${threadtype.typeid}]" value="1"></td>
					<td class="altbg1"><input class="radio" type="radio" name="threadtypes[options][${threadtype.typeid}]" value="2"></td>
					<td class="altbg2"><input class="checkbox" type="checkbox" name="threadtypes[show][${threadtype.typeid}]" value="3" ${threadtype.special!=1?"disabled":""}></td>
				</tr>
				<script language="javascript" type="text/javascript">
					isRadioChecked("threadtypes[options][${threadtype.typeid}]","${threadtypesMap['options'][threadtype.typeid]}");
					isCheckboxChecked("threadtypes[show][${threadtype.typeid}]","${threadtypesMap['show'][threadtype.typeid]}");
				</script>
			</c:if>
		</c:forEach>
		<tr><td colspan="6"><bean:message key="add_new"/><a href="###" onclick="addtype()">[+]</a></td></tr>
		<c:forEach begin="0" end="9" step="1" varStatus="index">
		<tbody id="type_${index.index}" style='${index.index>0?"display: none":""}'>
			<tr align="center">
				<td class="altbg1"><input type="text" name="newname[${index.index}]" size="15"></td>
				<td class="altbg2"><input type="text" name="newdescription[${index.index}]" size="15"></td>
				<td class="altbg1"><input class="radio" type="radio" name="newoptions[${index.index}]" value="0"></td>
				<td class="altbg2"><input class="radio" type="radio" name="newoptions[${index.index}]" value="1" checked></td>
				<td class="altbg1"><input class="radio" type="radio" name="newoptions[${index.index}]" value="2"></td>
				<td class="altbg1"><input class="checkbox" type="checkbox" name="newoptions[${index.index}]" value="2"></td>
			</tr>
		</tbody>
		</c:forEach>
		<tr><td colspan="6"><bean:message key="a_forum_threadtypes_comment"/></td></tr>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td><bean:message key="a_forum_edit_perm"/></td>
			<td><input class="checkbox" type="checkbox" name="chkall1" onclick="checkall(this.form, 'viewperm', 'chkall1')"><bean:message key="a_forum_edit_perm_view"/></td>
			<td><input class="checkbox" type="checkbox" name="chkall2" onclick="checkall(this.form, 'postperm', 'chkall2')"><bean:message key="post_new"/></td>
			<td><input class="checkbox" type="checkbox" name="chkall3" onclick="checkall(this.form, 'replyperm', 'chkall3')"><bean:message key="a_forum_edit_perm_reply"/></td>
			<td><input class="checkbox" type="checkbox" name="chkall4" onclick="checkall(this.form, 'getattachperm', 'chkall4')"><bean:message key="access_getattach"/></td>
			<td><input class="checkbox" type="checkbox" name="chkall5" onclick="checkall(this.form, 'postattachperm', 'chkall5')"><bean:message key="access_postattach"/></td>
		</tr>
		<c:forEach items="${usergroups}" var="usergroup">
			<tr>
				<td class="altbg1"><input class="checkbox" title="<bean:message key='select_all'/>" type="checkbox" name="chkallv${usergroup.groupid}" onclick="checkallvalue(this.form, ${usergroup.groupid}, 'chkallv${usergroup.groupid}')"> ${usergroup.grouptitle}</td>
				<td class="altbg2"><input class="checkbox" type="checkbox" name="viewperm" ${viewpermMap[usergroup.groupid]?'checked':''} value="${usergroup.groupid}"></td>
				<td class="altbg1"><input class="checkbox" type="checkbox" name="postperm" ${postpermMap[usergroup.groupid]?'checked':''} value="${usergroup.groupid}"></td>
				<td class="altbg2"><input class="checkbox" type="checkbox" name="replyperm" ${replypermMap[usergroup.groupid]?'checked':''} value="${usergroup.groupid}"></td>
				<td class="altbg1"><input class="checkbox" type="checkbox" name="getattachperm" ${getattachpermMap[usergroup.groupid]?'checked':''} value="${usergroup.groupid}"></td>
				<td class="altbg2"><input class="checkbox" type="checkbox" name="postattachperm" ${postattachpermMap[usergroup.groupid]?'checked':''} value="${usergroup.groupid}"></td>
			</tr>
		</c:forEach>
		<tr><td colspan="6"><bean:message key="a_forum_edit_perm_comment"/></td></tr>
		<tr class="header"><td colspan="6"><b><bean:message key="a_forum_edit_access_mask"/>:</b></td></tr>
		<tr><td class="altbg1"><bean:message key="a_forum_edit_perm_view"/>:</td><td class="altbg2" colspan="5">${viewaccess}</td></tr>
		<tr><td class="altbg1"><bean:message key="post_new"/>:</td><td class="altbg2" colspan="5">${postaccess}</td></tr>
		<tr><td class="altbg1"><bean:message key="a_forum_edit_perm_reply"/>:</td><td class="altbg2" colspan="5">${allowreply}</td></tr>
		<tr><td class="altbg1"><bean:message key="access_getattach"/>:</td><td class="altbg2" colspan="5">${allowgetattach}</td></tr>
		<tr><td class="altbg1"><bean:message key="access_postattach"/>:</td><td class="altbg2" colspan="5">${allowpostattach}</td></tr>
	</table>
	<br />
	<a name="a65d308e169d9410"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="a_forum_formulaperm"/><a href="###" onclick="collapse_change('a65d308e169d9410')"><img id="menuimg_a65d308e169d9410" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_a65d308e169d9410" style="display: yes">
			<script language="javascript" type="text/javascript">
				var extcredits=new Array(8);
			</script>
			<c:forEach items="${extcredits}" var="extcredit">
				<c:if test="${extcredit.value.available==1}"><script language="javascript" type="text/javascript">extcredits[${extcredit.key-1}]='${extcredit.value.title}';</script></c:if>
				<c:if test="${extcredit.value.available==null||extcredit.value.available!=1}"><script language="javascript" type="text/javascript">extcredits[${extcredit.key-1}]='<bean:message key="a_setting_creditsformula_extcredits"/>${extcredit.key}';</script></c:if>
			</c:forEach>
			<script language="javascript" type="text/javascript">
				function isUndefined(variable) {
					return typeof variable == 'undefined' ? true : false;
				}
				function insertunit(text, textend) {
					$('formulapermnew').focus();
					textend = isUndefined(textend) ? '' : textend;
					if(!isUndefined($('formulapermnew').selectionStart)) {
						var opn = $('formulapermnew').selectionStart + 0;
						if(textend != '') {
							text = text + $('formulapermnew').value.substring($('formulapermnew').selectionStart, $('formulapermnew').selectionEnd) + textend;
						}
						$('formulapermnew').value = $('formulapermnew').value.substr(0, $('formulapermnew').selectionStart) + text + $('formulapermnew').value.substr($('formulapermnew').selectionEnd);
					} else if(document.selection && document.selection.createRange) {
						var sel = document.selection.createRange();
						if(textend != '') {
							text = text + sel.text + textend;
						}
						sel.text = text.replace(/\r?\n/g, '\r\n');
						sel.moveStart('character', -strlen(text));
					} else {
						$('formulapermnew').value += text;
					}
					formulaexp();
				}
				
				var formulafind = new Array('digestposts', 'posts', 'oltime', 'pageviews');
				var formulareplace = new Array('<u><bean:message key="digestposts"/></u>','<u><bean:message key="posts"/></u>','<u><bean:message key="a_setting_creditsformula_oltime"/></u>','<u><bean:message key="pageviews"/></u>');
				function formulaexp() {
					var result = $('formulapermnew').value;
					result = result.replace(/extcredits1/g, '<u>'+extcredits[0]+'</u>');
					result = result.replace(/extcredits2/g, '<u>'+extcredits[1]+'</u>');
					result = result.replace(/extcredits3/g, '<u>'+extcredits[2]+'</u>');
					result = result.replace(/extcredits4/g, '<u>'+extcredits[3]+'</u>');
					result = result.replace(/extcredits5/g, '<u>'+extcredits[4]+'</u>');
					result = result.replace(/extcredits6/g, '<u>'+extcredits[5]+'</u>');
					result = result.replace(/extcredits7/g, '<u>'+extcredits[6]+'</u>');
					result = result.replace(/extcredits8/g, '<u>'+extcredits[7]+'</u>');
					result = result.replace(/digestposts/g, '<u><bean:message key="digestposts"/></u>');
					result = result.replace(/posts/g, '<u><bean:message key="posts"/></u>');
					result = result.replace(/oltime/g, '<u><bean:message key="a_setting_creditsformula_oltime"/></u>');
					result = result.replace(/pageviews/g, '<u><bean:message key="pageviews"/></u>');
					result = result.replace(/and/g, '&nbsp;&nbsp;<bean:message key="and"/>&nbsp;&nbsp;');
					result = result.replace(/or/g, '&nbsp;&nbsp;<bean:message key="or"/>&nbsp;&nbsp;');
					result = result.replace(/>=/g, '&ge;');
					result = result.replace(/<=/g, '&le;');
					$('formulapermexp').innerHTML = result;
				}
			</script>
			<tr>
				<td colspan="2" class="altbg1">
					<span class="smalltxt"><bean:message key="a_forum_formulaperm_comment"/></span>
					<br />
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('formulapermnew', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('formulapermnew', 0)">
					<div style="width:90%" class="formulaeditor">
						<div>
							<c:forEach items="${extcredits}" var="extcredit"><c:choose><c:when test="${extcredit.value.available==1}"><a href="###" onclick="insertunit('extcredits${extcredit.key}')">${extcredit.value.title}</a> &nbsp;</c:when><c:otherwise><a href="###" onclick="insertunit('extcredits${extcredit.key}')"><bean:message key="a_setting_creditsformula_extcredits"/>${extcredit.key}</a> &nbsp;</c:otherwise></c:choose></c:forEach><br />
							<a href="###" onclick="insertunit(' digestposts ')"><bean:message key="digestposts"/></a>&nbsp;
							<a href="###" onclick="insertunit(' posts ')"><bean:message key="posts"/></a>&nbsp;
							<a href="###" onclick="insertunit(' oltime ')"><bean:message key="a_setting_creditsformula_oltime"/></a>&nbsp;
							<a href="###" onclick="insertunit(' pageviews ')"><bean:message key="pageviews"/></a>&nbsp;
							<a href="###" onclick="insertunit(' + ')">&nbsp;+&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' - ')">&nbsp;-&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' * ')">&nbsp;*&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' / ')">&nbsp;/&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' > ')">&nbsp;>&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' >= ')">&nbsp;>=&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' < ')">&nbsp;<&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' <= ')">&nbsp;<=&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' = ')">&nbsp;=&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' (', ') ')">&nbsp;(&nbsp;)&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' and ')">&nbsp;<bean:message key="and"/>&nbsp;</a>&nbsp;
							<a href="###" onclick="insertunit(' or ')">&nbsp;<bean:message key="or"/>&nbsp;</a>&nbsp;<br />
							<span id="formulapermexp"></span>
						</div>
						<textarea name="formulaperm" id="formulapermnew" style="width:100%" rows="3" onkeyup="formulaexp()" type="_moz">${forumlaperms}</textarea>
						<input type="hidden" id="checkResult" name="checkResult" value="false">
					</div>
					<script>formulaexp()</script>
					<br /><bean:message key="a_setting_current_formula_notice"/>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="detailsubmit" value="<bean:message key='submit'/>">&nbsp;&nbsp;&nbsp;<input class="button" type="submit" name="saveconfigsubmit" value="<bean:message key='saveconf'/>">
	</center>
</form>
<script language="javascript" type="text/javascript">
	isRadioChecked("status",'${forum.status}');
	isRadioChecked("alloweditrules",'${forum.alloweditrules}');
	isRadioChecked("modnewposts",'${forum.modnewposts}');
	isRadioChecked("alloweditpost",'${forum.alloweditpost}');
	isRadioChecked("recyclebin",'${forum.recyclebin}');
	isRadioChecked("allowshare",'${forum.allowshare}');
	isRadioChecked("allowhtml",'${forum.allowhtml}');
	isRadioChecked("allowbbcode",'${forum.allowbbcode}');
	isRadioChecked("allowimgcode",'${forum.allowimgcode}');
	isRadioChecked("allowmediacode",'${forum.allowmediacode}');
	isRadioChecked("allowsmilies",'${forum.allowsmilies}');
	isRadioChecked("jammer",'${forum.jammer}');
	isRadioChecked("allowanonymous",'${forum.allowanonymous}');
	isRadioChecked("disablewatermark",'${forum.disablewatermark}');
	isRadioChecked("allowspecialonly",'${forum.allowspecialonly}');
	isRadioChecked("allowpaytoauthor",'${forum.allowpaytoauthor}');
	isRadioChecked("autoclose",'${forum.autoclose}');
		
	isRadioChecked("threadtypes[status]","${threadtypesMap['status']}");
	isRadioChecked("threadtypes[required]","${threadtypesMap['required']}");
	isRadioChecked("threadtypes[listable]","${threadtypesMap['listable']}");
	isRadioChecked("threadtypes[prefix]","${threadtypesMap['prefix']}");
	isSelectChecked("projectId",'${proId}');
	isSelectChecked("fup",'${forum.fup}');

	isCheckboxChecked("allowpostspecial[1]","${allowpostspecials[0]}");
	isCheckboxChecked("allowpostspecial[2]","${allowpostspecials[1]}");
	isCheckboxChecked("allowpostspecial[3]","${allowpostspecials[2]}");
	isCheckboxChecked("allowpostspecial[4]","${allowpostspecials[3]}");
	isCheckboxChecked("allowpostspecial[5]","${allowpostspecials[4]}");
	isCheckboxChecked("allowpostspecial[6]","${allowpostspecials[5]}");

	isRadioChecked("modrecommend[open]","${modrecommend['open']}");
	isRadioChecked("modrecommend[sort]","${modrecommend['sort']}");
	isRadioChecked("modrecommend[orderby]","${modrecommend['orderby']}");
	if('${modrecommend['open']}'=='1')
	{
		$('hidden_forums_edit_recommend').style.display = '';
	}
</script>
</c:otherwise></c:choose>
<jsp:directive.include file="../cp_footer.jsp" />