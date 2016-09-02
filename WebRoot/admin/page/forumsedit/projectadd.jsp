<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="project_scheme_add"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=projectadd&fid=${fid}&projectid=${project.id}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="e524b9c0c96b7418"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="project_scheme_save"/><a href="###" onclick="collapse_change('e524b9c0c96b7418')"><img id="menuimg_e524b9c0c96b7418" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_e524b9c0c96b7418" style="display: yes">
			<c:if test="${project!=null}">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="project_scheme_cover"/></b></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="coverwith" value="1"> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="coverwith" value="0" checked> <bean:message key="no"/>
				</td>
			</tr>
			</c:if>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="project_scheme_option"/></b><br /><span class="smalltxt"><bean:message key="project_scheme_option_comment"/></span></td>
				<td class="altbg2">
					<select name="fieldoption" size="10" multiple="multiple" style="width: 80%">
						<option value="all" selected><bean:message key="all"/></option>
						<option value="styleid"><bean:message key="a_forum_edit_style"/></option>
						<option value="allowsmilies"><bean:message key="a_forum_edit_smilies"/></option>
						<option value="allowhtml"><bean:message key="group_allowhtml"/></option>
						<option value="allowbbcode"><bean:message key="a_forum_edit_bbcode"/></option>
						<option value="allowimgcode"><bean:message key="a_forum_edit_imgcode"/></option>
						<option value="allowmediacode"><bean:message key="a_forum_edit_mediacode"/></option>
						<option value="allowanonymous"><bean:message key="a_forum_project_option_allowanonymous"/></option>
						<option value="allowshare"><bean:message key="a_forum_project_option_allowshare"/></option>
						<option value="allowpostspecial"><bean:message key="a_forum_project_option_allowpostspecial"/></option>
						<option value="allowspecialonly"><bean:message key="a_forum_project_option_allowspecialonly"/></option>
						<option value="alloweditrules"><bean:message key="a_forum_edit_rule_eidt"/></option>
						<option value="recyclebin"><bean:message key="menu_post_recyclebin"/></option>
						<option value="modnewposts"><bean:message key="a_forum_edit_modpost"/></option>
						<option value="jammer"><bean:message key="a_forum_edit_jammer"/></option>
						<option value="disablewatermark"><bean:message key="a_forum_edit_disablewatermark"/></option>
						<option value="inheritedmod"><bean:message key="a_forum_project_option_inheritedmod"/></option>
						<option value="autoclose"><bean:message key="a_forum_edit_autoclose"/></option>
						<option value="forumcolumns"><bean:message key="a_forum_sub_horizontal"/></option>
						<option value="threadcaches"><bean:message key="a_forum_threadcache"/></option>
						<option value="allowpaytoauthor"><bean:message key="a_forum_edit_trade_payto"/></option>
						<option value="alloweditpost"><bean:message key="a_forum_edit_alloweditpost"/></option>
						<option value="simple"><bean:message key="a_forum_project_option_simple"/></option>
						<option value="postcredits"><bean:message key="a_forum_project_option_postcredits"/></option>
						<option value="replycredits"><bean:message key="a_forum_project_option_replycredits"/></option>
						<option value="getattachcredits"><bean:message key="a_forum_project_option_getattachcredits"/></option>
						<option value="postattachcredits"><bean:message key="a_forum_project_option_postattachcredits"/></option>
						<option value="digestcredits"><bean:message key="a_forum_project_option_digestcredits"/></option>
						<option value="attachextensions"><bean:message key="a_forum_edit_attach_ext"/></option>
						<option value="formulaperm"><bean:message key="a_forum_formulaperm"/></option>
						<option value="viewperm"><bean:message key="a_forum_project_option_viewperm"/></option>
						<option value="postperm"><bean:message key="a_forum_project_option_postperm"/></option>
						<option value="replyperm"><bean:message key="a_forum_project_option_replyperm"/></option>
						<option value="getattachperm"><bean:message key="a_forum_project_option_getattachperm"/></option>
						<option value="postattachperm"><bean:message key="a_forum_project_option_postattachperm"/></option>
						<option value="keywords"><bean:message key="a_forum_edit_keyword"/></option>
						<option value="modrecommend"><bean:message key="a_forum_project_option_modrecommend"/></option>
						<option value="tradetypes"></option>
						<option value="typemodels"></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="project_scheme_title"/></b><br /><span class="smalltxt"><bean:message key="project_scheme_title_comment"/></span></td>
				<td class="altbg2"><input type="text" size="50" maxlength="50" name="name" value="${project.name}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top"><b><bean:message key="project_scheme_description"/></b><br /><span class="smalltxt"><bean:message key="project_scheme_description_comment"/></span></td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 0)">
					<br />
					<textarea rows="6" name="description" id="description" cols="50" onKeyDown="if (this.value.length>=255){event.returnValue=false}">${project.description}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center><input class="button" type="submit" name="addsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />