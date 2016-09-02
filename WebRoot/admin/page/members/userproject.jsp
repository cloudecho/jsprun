<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="project_scheme_add"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=adduserproject&usergroupid=${usergroup.groupid}">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<a name="82b3bf8a504b9235"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
<tr class="header">
<td colspan="2"><bean:message key="project_scheme_save"/><a href="###" onclick="collapse_change('82b3bf8a504b9235')"><img  id="menuimg_82b3bf8a504b9235" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px;  margin-right: 8px;" /></a>
</td> </tr>
<tbody id="menu_82b3bf8a504b9235" style="display: yes">
<tr><td width="45%" class="altbg1" ><b><bean:message key="project_scheme_option"/></b><br /><span class="smalltxt"><bean:message key="project_scheme_option_comment"/>
</span></td><td class="altbg2"><select name="fieldoption[]" size="10" multiple="multiple" style="width: 80%">
<option value="all" selected><bean:message key="all"/></option>
<option value="readaccess"><bean:message key="threads_readperm"/></option>
<option value="allowvisit"><bean:message key="project_option_group_allowvisit"/></option>
<option value="allowpost"><bean:message key="project_option_group_allowpost"/></option>
<option value="allowreply"><bean:message key="project_option_group_allowreply"/></option>
<option value="allowpostpoll"><bean:message key="project_option_group_allowpostpoll"/></option>
<option value="allowpostreward"><bean:message key="project_option_group_allowpostreward"/></option>
<option value="allowposttrade"><bean:message key="project_option_group_allowposttrade"/></option>
<option value="allowpostactivity"><bean:message key="project_option_group_allowpostactivity"/></option>
<option value="allowpostvideo"><bean:message key="project_option_group_allowpostvideo"/></option>
<option value="allowdirectpost"><bean:message key="project_option_group_allowdirectpost"/></option>
<option value="allowgetattach"><bean:message key="project_option_group_allowgetattach"/></option>
<option value="allowpostattach"><bean:message key="project_option_group_allowpostattach"/></option>
<option value="allowvote"><bean:message key="project_option_group_allowvote"/></option>
<option value="allowmultigroups"><bean:message key="project_option_group_allowmultigroups"/></option>
<option value="allowsearch"><bean:message key="project_option_group_allowsearch"/></option>
<option value="allowavatar"><bean:message key="project_option_group_allowavatar"/></option>
<option value="allowcstatus"><bean:message key="project_option_group_allowcstatus"/></option>
<option value="allowuseblog"><bean:message key="project_option_group_allowuseblog"/></option>
<option value="allowinvisible"><bean:message key="project_option_group_allowinvisible"/></option>
<option value="allowtransfer"><bean:message key="project_option_group_allowtransfer"/></option>
<option value="allowsetreadperm"><bean:message key="project_option_group_allowsetreadperm"/></option>
<option value="allowsetattachperm"><bean:message key="project_option_group_allowsetattachperm"/></option>
<option value="allowhidecode"><bean:message key="project_option_group_allowhidecode"/></option>
<option value="allowhtml"><bean:message key="group_allowhtml"/></option>
<option value="allowcusbbcode"><bean:message key="project_option_group_allowcusbbcode"/></option>
<option value="allowanonymous"><bean:message key="project_option_group_allowanonymous"/></option>
<option value="allownickname"><bean:message key="project_option_group_allownickname"/></option>
<option value="allowsigbbcode"><bean:message key="project_option_group_allowsigbbcode"/></option>
<option value="allowsigimgcode"><bean:message key="project_option_group_allowsigimgcode"/></option>
<option value="allowviewpro"><bean:message key="project_option_group_allowviewpro"/></option>
<option value="allowviewstats"><bean:message key="project_option_group_allowviewstats"/></option>
<option value="disableperiodctrl"><bean:message key="project_option_group_disableperiodctrl"/></option>
<option value="reasonpm"><bean:message key="project_option_group_reasonpm"/></option>
<option value="maxprice"><bean:message key="project_option_group_maxprice"/></option>
<option value="maxpmnum"><bean:message key="project_option_group_maxpmnum"/></option>
<option value="maxsigsize"><bean:message key="project_option_group_maxsigsize"/></option>
<option value="maxattachsize"><bean:message key="project_option_group_maxattachsize"/></option>
<option value="maxsizeperday"><bean:message key="project_option_group_maxsizeperday"/></option>
<option value="maxpostsperhour"><bean:message key="project_option_group_maxpostsperhour"/></option>
<option value="attachextensions"><bean:message key="project_option_group_attachextensions"/></option>
<option value="raterange"><bean:message key="usergroups_edit_raterange"/></option>
<option value="mintradeprice"><bean:message key="project_option_group_mintradeprice"/></option>
<option value="maxtradeprice"><bean:message key="project_option_group_maxtradeprice"/></option>
<option value="minrewardprice"><bean:message key="project_option_group_minrewardprice"/></option>
<option value="maxrewardprice"><bean:message key="project_option_group_maxrewardprice"/></option>
<option value="magicsdiscount"><bean:message key="project_option_group_magicsdiscount"/></option>
<option value="allowmagics"><bean:message key="project_option_group_allowmagics"/></option>
<option value="maxmagicsweight"><bean:message key="project_option_group_maxmagicsweight"/></option>
<option value="allowbiobbcode"><bean:message key="project_option_group_allowbiobbcode"/></option>
<option value="allowbioimgcode"><bean:message key="project_option_group_allowbioimgcode"/></option>
<option value="maxbiosize"><bean:message key="project_option_group_maxbiosize"/></option>
<option value="allowinvite"><bean:message key="project_option_group_allowinvite"/></option>
<option value="allowmailinvite"><bean:message key="project_option_group_allowmailinvite"/></option>
<option value="maxinvitenum"><bean:message key="project_option_group_maxinvitenum"/></option>
<option value="inviteprice"><bean:message key="project_option_group_inviteprice"/></option>
<option value="maxinviteday"><bean:message key="project_option_group_maxinviteday"/></option>
<option value="allowpostdebate"><bean:message key="project_option_group_allowpostdebate"/></option>
<option value="tradestick"><bean:message key="project_option_group_tradestick"/></option>
<option value="allowviewdigest"><bean:message key="project_option_group_allowviewdigest"/></option>
</select></td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="project_scheme_title"/></b><br /><span class="smalltxt"><bean:message key="project_scheme_title_comment"/></span></td><td class="altbg2"><input type="text" size="50" name="name" value="" maxlength="50">
</td></tr><tr><td width="45%" class="altbg1" valign="top"><b><bean:message key="project_scheme_description"/></b><br /><span class="smalltxt"><bean:message key="project_scheme_description_comment"/></span></td><td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 0)"><br /><textarea  rows="6" name="description" id="description" cols="50" onKeyDown='if (this.value.length>=255){event.returnValue=false}'></textarea></td></tr></tbody></table><br /><center><input class="button" type="submit" name="addsubmit" value="<bean:message key="submit"/>"></center></form>
<jsp:directive.include file="../cp_footer.jsp" />