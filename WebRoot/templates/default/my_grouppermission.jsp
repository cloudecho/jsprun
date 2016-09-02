<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<h1><bean:message key="permission_group_search"/></h1>
<ul class="tabs headertabs">
	<li ${empty type? "class=current":"" }><a href="my.jsp?item=grouppermission"><bean:message key="my_permissions"/></a></li>
	<li ${type== 'member'?"class=current dropmenu ":"class=dropmenu" }><a href="###" id="membergroup" onmouseover="showMenu(this.id)"><bean:message key="usergroups_member"/></a></li>
	<li ${type== 'system'?"class=current dropmenu ":"class=dropmenu" }><a href="###" id="systemgroup" onmouseover="showMenu(this.id)"><bean:message key="usergroups_system"/></a></li>
	<c:if test="${!empty specialGroups}"><li ${type== 'special'?"class=current dropmenu":"class=dropmenu" }><a href="###" id="specialgroup" onmouseover="showMenu(this.id)"><bean:message key="usergroups_special"/></a></li></c:if>
</ul>
<ul class="popupmenu_popup headermenu_popup" id="membergroup_menu" style="display: none">${memberGroups}</ul>
<ul class="popupmenu_popup headermenu_popup" id="systemgroup_menu" style="display: none">${systemGroups}</ul>
<c:if test="${!empty specialGroups}"><ul class="popupmenu_popup headermenu_popup" id="specialgroup_menu"	style="display: none">${specialGroups}</ul></c:if>
<table cellspacing="0" cellpadding="0" width="100%">
	<thead><tr><td colspan="2"><bean:message key="menu_member_usergroups"/></td></tr></thead>
	<tr><th><bean:message key="memcp_usergroups_title"/>:</th><td>${usergroup.grouptitle}</td></tr>
	<tr><th><bean:message key="permission_member_level"/>:</th><td><c:choose><c:when test="${usergroup.stars>0}"><jrun:showstars num="${usergroup.stars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/></c:when><c:otherwise><img src="${styles.IMGDIR}/check_error.gif" /></c:otherwise></c:choose></td></tr>
	<thead><tr><td colspan="2"><bean:message key="admingroups_settings_admin"/></td></tr></thead>
	<tr><th width="50%"><bean:message key="admingroups_settings_admin"/>:</th><td width="50%"><c:choose><c:when test="${usergroup.radminid==1||usergroup.radminid==2}"><bean:message key="permission_modoptions_allfourm"/></c:when><c:when test="${usergroup.radminid==3}"><bean:message key="permission_modoptions_partforum"/></c:when><c:otherwise><img src="${styles.IMGDIR}/check_error.gif" /></c:otherwise></c:choose></td></tr>
	<c:if test="${usergroup.radminid>0}">
		<tr><th><bean:message key="admingroups_edit_edit_post"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.alloweditpost==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_edit_poll"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.alloweditpoll==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_stick_thread"/></th><td><c:choose><c:when test="${usergroup.allowstickthread==1}"><bean:message key="admingroups_edit_stick_thread_1"/></c:when><c:when test="${usergroup.allowstickthread==2}"><bean:message key="admingroups_edit_stick_thread_2"/></c:when><c:when test="${usergroup.allowstickthread==3}"><bean:message key="admingroups_edit_stick_thread_3"/></c:when><c:otherwise><img src="${styles.IMGDIR}/check_error.gif" /></c:otherwise></c:choose></td></tr>
		<tr><th><bean:message key="admingroups_edit_mod_post"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowmodpost==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_del_post"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowdelpost==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_mass_prune"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowmassprune==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_censor_word"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowcensorword==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_view_ip"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowviewip==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_ban_ip"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowbanip==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_action_members"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowedituser==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_ban_user"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowbanuser==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_mod_user"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowmoduser==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_post_announce"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowpostannounce==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_view_log"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowviewlog==1?'right':'error' }.gif" /></td></tr>
		<tr><th><bean:message key="admingroups_edit_disable_postctrl"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.disablepostctrl==1?'right':'error' }.gif" /></td></tr>
	</c:if>
	<thead><tr><td colspan="2"><bean:message key="usergroups_edit_basic"/></td></tr></thead>
	<tr><th><bean:message key="usergroups_edit_visit"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowvisit==1?'right':'error' }.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_read_access"/></th><td>${usergroup.readaccess}</td></tr>
	<tr><th><bean:message key="usergroups_edit_view_profile"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowviewpro==1?'right':'error' }.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_invisible"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowinvisible==1?'right':'error' }.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_search"/></th><td><c:choose><c:when test="${usergroup.allowsearch==0}"><bean:message key="usergroups_edit_search_disable"/></c:when><c:when test="${usergroup.allowsearch==1}"><bean:message key="usergroups_edit_search_thread"/></c:when><c:otherwise><bean:message key="permission_basic_search_content"/></c:otherwise></c:choose></td></tr>
	<tr><th><bean:message key="usergroups_edit_avatar"/></th><td><c:choose><c:when test="${usergroup.allowavatar==0}"><bean:message key="permission_basic_disable_face"/></c:when><c:when test="${usergroup.allowavatar==1}"><bean:message key="permission_basic_forum_face"/></c:when><c:otherwise><bean:message key="permission_basic_custom_face"/></c:otherwise></c:choose></td></tr>
	<tr><th><bean:message key="usergroups_edit_blog"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowuseblog==1?'right':'error' }.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_nickname"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allownickname==1?'right':'error' }.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_cstatus"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowcstatus==1?'right':'error' }.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_max_pm_num"/></th><td>${usergroup.maxpmnum}</td></tr>
	<thead><tr><td colspan="2"><bean:message key="menu_post"/></td></tr></thead>
	<tr><th><bean:message key="usergroups_edit_post"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowpost==1?'right':'error' }.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_reply"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowreply==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_post_poll"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowpostpoll==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_vote"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowvote==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="allow_post_reward"/>:</th><td><img src="${styles.IMGDIR}/check_${usergroup.allowpostreward==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="allow_post_activity"/>:</th><td><img src="${styles.IMGDIR}/check_${usergroup.allowpostactivity==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="allow_post_debate"/>:</th><td><img src="${styles.IMGDIR}/check_${usergroup.allowpostdebate==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="allow_post_trade"/>:</th><td><img src="${styles.IMGDIR}/check_${usergroup.allowposttrade==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="allow_post_video"/>:</th><td><img src="${styles.IMGDIR}/check_${usergroup.allowpostvideo==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="permission_post_max_signature"/>:</th><td>${usergroup.maxsigsize} <bean:message key="bytes"/></td></tr>
	<tr><th><bean:message key="usergroups_edit_sig_bbcode"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowsigbbcode==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_sig_img_code"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowsigimgcode==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="permission_post_max_bio"/>:</th><td>${usergroup.maxbiosize>0 ? usergroup.maxbiosize : 200} <bean:message key="bytes"/></td></tr>
	<tr><th><bean:message key="usergroups_edit_bio_bbcode"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowbiobbcode==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_bio_img_code"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowbioimgcode==1?'right':'error'}.gif" /></td></tr>
	<thead><tr><td colspan="2"><bean:message key="usergroups_edit_attachment"/></td></tr></thead>
	<tr><th><bean:message key="usergroups_edit_get_attach"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowgetattach==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_post_attach"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowpostattach==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="usergroups_edit_set_attach_perm"/></th><td><img src="${styles.IMGDIR}/check_${usergroup.allowsetattachperm==1?'right':'error'}.gif" /></td></tr>
	<tr><th><bean:message key="project_option_group_maxattachsize"/>:</th><td><c:choose><c:when test="${usergroup.maxattachsize>0}">${usergroup.maxattachsize} KB</c:when><c:otherwise><bean:message key="permission_attachment_nopermission"/></c:otherwise></c:choose></td></tr>
	<tr><th><bean:message key="project_option_group_maxsizeperday"/>:</th><td><c:choose><c:when test="${usergroup.maxsizeperday>0}">${usergroup.maxsizeperday} KB</c:when><c:otherwise><bean:message key="permission_attachment_nopermission"/></c:otherwise></c:choose></td></tr>
	<tr><th><bean:message key="usergroups_edit_attach_ext"/></th>
	<td>
	<c:choose>
	<c:when test="${usergroup.allowpostattach==1}">
		<c:choose>
			<c:when test="${usergroup.attachextensions==''}"><bean:message key="permission_attachment_nopermission"/></c:when>
			<c:otherwise>${usergroup.attachextensions}</c:otherwise>
		</c:choose>
	</c:when><c:otherwise><img src="${styles.IMGDIR}/check_error.gif" /></c:otherwise></c:choose></td></tr>
</table>