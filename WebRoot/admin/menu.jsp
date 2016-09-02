<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html><head>
<link rel="stylesheet" type="text/css" id="css" href="images/admincp/admincp.css">
<script src="include/javascript/common.js" type="text/javascript"></script>
<script src="include/javascript/iframe.js" type="text/javascript"></script>
<link rel="SHORTCUT ICON" href="favicon.ico" />
<script>
var collapsed = getcookie('${tablepre}collapse');
function collapse_change(menucount) {
	if($('menu_' + menucount).style.display == 'none') {
		$('menu_' + menucount).style.display = '';collapsed = collapsed.replace('[' + menucount + ']' , '');
		$('menuimg_' + menucount).src = 'images/admincp/menu_reduce.gif';
	} else {
		$('menu_' + menucount).style.display = 'none';collapsed += '[' + menucount + ']';
		$('menuimg_' + menucount).src = 'images/admincp/menu_add.gif';
	}
	setcookie('${tablepre}collapse', collapsed, 2592000);
}
</script>
</head>
<body style="margin:5px!important;margin:3px;">
<c:choose><c:when test="${members.adminid==1}"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;"><tr class="leftmenutext"><td><div align="center"><a href="${settings.indexname}" target="_blank"><bean:message key="header_home"/></a>&nbsp;&nbsp;<a href="#"  onClick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_admin"/></a></div></td></tr></table><div id="home">
<table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(1)"><img id="menuimg_1" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(1)"><bean:message key="menu_general"/></a></td></tr>
	<tbody id="menu_1" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=forumsedit" target="main"><bean:message key="menu_forum_edit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=members" target="main"><bean:message key="menu_member_edit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=banmember" target="main"><bean:message key="menu_member_ban"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=announcements" target="main"><bean:message key="menu_other_announce"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=threads" target="main"><bean:message key="menu_maint_threads"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=prune" target="main"><bean:message key="PRN"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=modthreads" target="main"><bean:message key="menu_post_modthreads"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=modreplies" target="main"><bean:message key="menu_post_modreplies"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(2)"><img id="menuimg_2" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(2)"><bean:message key="menu_log"/></a></td></tr>
	<tbody id="menu_2" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=illegallog" target="main"><bean:message key="menu_log_login"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=ratelog" target="main"><bean:message key="menu_log_rating"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=creditslog" target="main"><bean:message key="menu_log_credit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=modslog" target="main"><bean:message key="menu_log_mod"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=medalslog" target="main"><bean:message key="menu_log_medal"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=banlog" target="main"><bean:message key="menu_log_ban"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=cplog" target="main"><bean:message key="menu_log_admincp"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=magiclog" target="main"><bean:message key="menu_log_magic"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=invitelog" target="main"><bean:message key="menu_log_invite"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=errorlog" target="main"><bean:message key="menu_log_error"/></a></td></tr>
	</table></td></tr></tbody>
</table></div><div id="basic" style="display: none"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(3)"><img id="menuimg_3" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(3)"><bean:message key="menu_setting"/></a></td></tr>
	<tbody id="menu_3" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=settings&do=basic" target="main"><bean:message key="header_basic"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=access" target="main"><bean:message key="menu_setting_access"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=styles" target="main"><bean:message key="menu_setting_styles"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=seo" target="main"><bean:message key="menu_setting_seo"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=cachethread" target="main"><bean:message key="menu_setting_cachethread"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=functions" target="main"><bean:message key="menu_setting_functions"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=credits" target="main"><bean:message key="menu_setting_credits"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=serveropti" target="main"><bean:message key="menu_setting_serveropti"/></a></td></tr>
		<c:if test="${isfounder}"><tr><td><a href="admincp.jsp?action=settings&do=mail" target="main"><bean:message key="menu_setting_mail"/></a></td></tr></c:if>
		<tr><td><a href="admincp.jsp?action=settings&do=seccode" target="main"><bean:message key="menu_setting_seccode"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=secqaa" target="main"><bean:message key="menu_setting_secqaa"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=datetime" target="main"><bean:message key="menu_setting_datetime"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=permissions" target="main"><bean:message key="menu_setting_permissions"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=attachments" target="main"><bean:message key="menu_setting_attachments"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=language" target="main"><bean:message key="menu_setting_language"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=wap" target="main"><bean:message key="menu_setting_wap"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=settings&do=space" target="main"><bean:message key="menu_setting_space"/></a></td></tr>
	</table></td></tr></tbody>
</table></div><div id="forums" style="display: none"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(4)"><img id="menuimg_4" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(4)"><bean:message key="menu_forum"/></a></td></tr>
	<tbody id="menu_4" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=forumadd" target="main"><bean:message key="menu_forum_add"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=forumsedit" target="main"><bean:message key="menu_forum_edit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=forumsmerge" target="main"><bean:message key="menu_forum_merge"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=threadtypes" target="main"><bean:message key="menu_forum_threadtypes"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(5)"><img id="menuimg_5" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(5)"><bean:message key="menu_threadtype"/></a></td></tr>
	<tbody id="menu_5" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=threadtypes&special=1" target="main"><bean:message key="menu_threadtype_type"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=typemodel" target="main"><bean:message key="menu_threadtype_model"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=typeoption" target="main"><bean:message key="menu_threadtype_option"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(6)"><img id="menuimg_6" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(6)"><bean:message key="menu_style"/></a></td></tr>
	<tbody id="menu_6" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=styles" target="main"><bean:message key="menu_style"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=templates" target="main"><bean:message key="menu_style_templates"/></a></td></tr>
	</table></td></tr></tbody>
</table></div><div id="users" style="display: none"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(7)"><img id="menuimg_7" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(7)"><bean:message key="menu_member"/></a></td></tr>
	<tbody id="menu_7" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=memberadd" target="main"><bean:message key="menu_member_add"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=members" target="main"><bean:message key="menu_member_edit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=banmember" target="main"><bean:message key="menu_member_ban"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=membersmerge" target="main"><bean:message key="menu_member_merge"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=ipban" target="main"><bean:message key="menu_member_ipban"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=members&submitname=creditsubmit" target="main"><bean:message key="menu_member_credit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=modmembers" target="main"><bean:message key="menu_member_modmembers"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=profilefields" target="main"><bean:message key="menu_member_profile"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(8)"><img id="menuimg_8" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(8)"><bean:message key="menu_member_groups"/></a></td></tr>
	<tbody id="menu_8" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=admingroups" target="main"><bean:message key="menu_member_admingroups"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=usergroups" target="main"><bean:message key="menu_member_usergroups"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=ranks" target="main"><bean:message key="menu_member_ranks"/></a></td></tr>
	</table></td></tr></tbody>
</table></div><div id="posts" style="display: none"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(9)"><img id="menuimg_9" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(9)"><bean:message key="menu_post_moderate"/></a></td></tr>
	<tbody id="menu_9" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=modthreads" target="main"><bean:message key="menu_post_modthreads"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=modreplies" target="main"><bean:message key="menu_post_modreplies"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(10)"><img id="menuimg_10" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(10)"><bean:message key="menu_maint"/></a></td></tr>
	<tbody id="menu_10" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=threads" target="main"><bean:message key="menu_maint_threads"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=prune" target="main"><bean:message key="PRN"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=attachments" target="main"><bean:message key="menu_maint_attachements"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=forumrecommend" target="main"><bean:message key="REC"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(11)"><img id="menuimg_11" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(11)"><bean:message key="menu_post"/></a></td></tr>
	<tbody id="menu_11" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=jspruncodes" target="main"><bean:message key="post_jspruncode"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=tags" target="main"><bean:message key="menu_post_tags"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=censor" target="main"><bean:message key="menu_post_censor"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=smilies" target="main"><bean:message key="menu_post_smilies"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=icons" target="main"><bean:message key="menu_post_icons"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=attachtypes" target="main"><bean:message key="menu_post_attachtypes"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=recyclebin" target="main"><bean:message key="menu_post_recyclebin"/></a></td></tr>
	</table></td></tr></tbody>
</table></div><div id="extends" style="display: none"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(12)"><img id="menuimg_12" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(12)"><bean:message key="menu_server"/></a></td></tr>
	<tbody id="menu_12" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=getSevrerInfo_SysType" target="main"><bean:message key="menu_server_systype"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=getSevrerInfo_CUPCount" target="main"><bean:message key="menu_server_cpu"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=getSevrerInfo_MemoryInfo" target="main"><bean:message key="menu_server_memory"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(13)"><img id="menuimg_13" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(13)"><bean:message key="menu_extend_ip"/></a></td></tr>
	<tbody id="menu_13" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=areaquery" target="main"><bean:message key="menu_extend_ip_see"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(14)"><img id="menuimg_14" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(14)"><bean:message key="menu_plugin"/></a></td></tr>
	<tbody id="menu_14" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=plugins" target="main"><bean:message key="menu_plugin_edit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=pluginsconfig" target="main"><bean:message key="menu_plugin_config"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(15)"><img id="menuimg_15" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(15)"><bean:message key="menu_searchengine"/></a></td></tr>
	<tbody id="menu_15" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=google_config" target="main"><bean:message key="menu_searchengine_google"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=baidu_config" target="main"><bean:message key="menu_searchengine_baidu"/></a></td></tr>
	</table></td></tr></tbody>
</table>

<table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(18)"><img id="menuimg_18" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(18)"><bean:message key="menu_ecommerce"/></a></td></tr>
	<tbody id="menu_18" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=settings&do=ecommerce" target="main"><bean:message key="header_basic"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=tenpay" target="main"><bean:message key="menu_ecommerce_tenpay"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=ec_credit" target="main"><bean:message key="menu_ecommerce_credit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=orders" target="main"><bean:message key="menu_ecommerce_creditorder"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=tradelog" target="main"><bean:message key="menu_ecommerce_tradeorder"/></a></td></tr>
	</table></td></tr></tbody>
</table></div><div id="others" style="display: none"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(19)"><img id="menuimg_19" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(19)"><bean:message key="menu_magic"/></a></td></tr>
	<tbody id="menu_19" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=magic_config" target="main"><bean:message key="menu_magic_config"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=magic" target="main"><bean:message key="menu_magic_edit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=magicmarket" target="main"><bean:message key="menu_magic_market"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(20)"><img id="menuimg_20" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(20)"><bean:message key="header_other"/></a></td></tr>
	<tbody id="menu_20" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=announcements" target="main"><bean:message key="menu_other_announce"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=medals" target="main"><bean:message key="menu_other_medal"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=adv" target="main"><bean:message key="menu_other_advertisement"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=forumlinks" target="main"><bean:message key="menu_other_link"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=crons" target="main"><bean:message key="menu_other_cron"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=faqlist" target="main"><bean:message key="menu_other_faq"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=onlinelist" target="main"><bean:message key="menu_other_onlinelist"/></a></td></tr>
	</table></td></tr></tbody>
</table></div><div id="tools" style="display: none"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(21)"><img id="menuimg_21" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(21)"><bean:message key="menu_tool"/></a></td></tr>
	<tbody id="menu_21" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=members&submitname=newslettersubmit" target="main"><bean:message key="menu_tool_newsletter"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=updatecache" target="main"><bean:message key="menu_tool_updatecache"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=counter" target="main"><bean:message key="menu_tool_updatecounter"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=jswizard" target="main"><bean:message key="menu_tool_javascript"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=creditwizard" target="main"><bean:message key="menu_tool_creditwizard"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=fileperms" target="main"><bean:message key="menu_tool_fileperm"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=filecheck" target="main"><bean:message key="menu_tool_filecheck"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=pmprune" target="main"><bean:message key="menu_tool_pmprune"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=project" target="main"><bean:message key="menu_tool_scheme"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(22)"><img id="menuimg_22" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(22)"><bean:message key="menu_database"/></a></td></tr>
	<tbody id="menu_22" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=export" target="main"><bean:message key="menu_database_export"/></a></td></tr>
		<c:if test="${settings.admincp_dbimport>0}"><tr><td><a href="admincp.jsp?action=import" target="main"><bean:message key="menu_database_import"/></a></td></tr></c:if>
		<tr><td><a href="admincp.jsp?action=runquery" target="main"><bean:message key="menu_database_query"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=optimize" target="main"><bean:message key="menu_database_optimize"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=dbcheck" target="main"><bean:message key="menu_database_dbcheck"/></a></td></tr>
	</table></td></tr></tbody>
</table><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(23)"><img id="menuimg_23" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(23)"><bean:message key="menu_log"/></a></td></tr>
	<tbody id="menu_23" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=illegallog" target="main"><bean:message key="menu_log_login"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=ratelog" target="main"><bean:message key="menu_log_rating"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=creditslog" target="main"><bean:message key="menu_log_credit"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=modslog" target="main"><bean:message key="menu_log_mod"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=medalslog" target="main"><bean:message key="menu_log_medal"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=banlog" target="main"><bean:message key="menu_log_ban"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=cplog" target="main"><bean:message key="menu_log_admincp"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=magiclog" target="main"><bean:message key="menu_log_magic"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=invitelog" target="main"><bean:message key="menu_log_invite"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=errorlog" target="main"><bean:message key="menu_log_error"/></a></td></tr>
	</table></td></tr></tbody>
</table></div><div id="safety" style="display: none"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(24)"><img id="menuimg_24" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(24)"><bean:message key="memu_safety"/></a></td></tr>
	<tbody id="menu_24" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=safety&do=basic" target="main"><bean:message key="header_basic"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=safety&do=cc" target="main"><bean:message key="memu_safety_cc"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=safety&do=ddos" target="main"><bean:message key="memu_safety_ddos"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=safety&do=port" target="main"><bean:message key="memu_safety_port"/></a></td></tr>
	</table></td></tr></tbody>
</table></div><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist"><tr class="leftmenutext"><td><div style="margin-left:48px;"><a href="admincp.jsp?action=logout" target="_top"><bean:message key="menu_logout"/></a></div></td></tr></table>
</c:when><c:otherwise>
<table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(1)"><img id="menuimg_1" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(1)"><bean:message key="menu_moderation"/></a></td></tr>
	<tbody id="menu_1" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=forumrules" target="main"><bean:message key="menu_forum_rule"/></a></td></tr>
		<c:if test="${usergroups.allowedituser==1}"><tr><td><a href="admincp.jsp?action=editmembers" target="main"><bean:message key="menu_member_edit"/></a></td></tr></c:if>
		<c:if test="${usergroups.allowbanuser==1}"><tr><td><a href="admincp.jsp?action=banmember" target="main"><bean:message key="menu_member_ban"/></a></td></tr></c:if>
		<c:if test="${usergroups.allowbanip==1}"><tr><td><a href="admincp.jsp?action=ipban" target="main"><bean:message key="menu_member_ipban"/></a></td></tr></c:if>
		<c:if test="${usergroups.allowpostannounce==1}"><tr><td><a href="admincp.jsp?action=announcements" target="main"><bean:message key="menu_other_announce"/></a></td></tr></c:if>
		<c:if test="${usergroups.allowcensorword==1}"><tr><td><a href="admincp.jsp?action=censor" target="main"><bean:message key="menu_post_censor"/></a></td></tr></c:if>
		<c:if test="${usergroups.allowmassprune==1}"><tr><td><a href="admincp.jsp?action=prune" target="main"><bean:message key="PRN"/></a></td></tr></c:if>
		<tr><td><a href="admincp.jsp?action=forumrecommend" target="main"><bean:message key="REC"/></a></td></tr>
	</table></td></tr></tbody>
</table><c:if test="${usergroups.allowmoduser==1 || usergroups.allowmodpost==1}"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(2)"><img id="menuimg_2" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(2)"><bean:message key="menu_post_moderate"/></a></td></tr>
	<tbody id="menu_2" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<c:if test="${usergroups.allowmoduser==1}"><tr><td><a href="admincp.jsp?action=modmembers" target="main"><bean:message key="menu_member_modmembers"/></a></td></tr></c:if>
		<c:if test="${usergroups.allowmodpost==1}">
			<tr><td><a href="admincp.jsp?action=modthreads" target="main"><bean:message key="menu_post_modthreads"/></a></td></tr>
			<tr><td><a href="admincp.jsp?action=modreplies" target="main"><bean:message key="menu_post_modreplies"/></a></td></tr>
		</c:if>
	</table></td></tr></tbody>
</table></c:if><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(3)"><img id="menuimg_3" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(3)"><bean:message key="menu_plugin"/></a></td></tr>
	<tbody id="menu_3" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=plugins" target="main"><bean:message key="menu_plugin"/></a></td></tr>
	</table></td></tr></tbody>
</table><c:if test="${usergroups.allowviewlog==1}"><table width="146" border="0" cellspacing="0" align="center" cellpadding="0" class="leftmenulist" style="margin-bottom: 5px;">
	<tr class="leftmenutext"><td><a href="###" onclick="collapse_change(4)"><img id="menuimg_4" src="images/admincp/menu_reduce.gif" border="0"/></a>&nbsp;<a href="###" onclick="collapse_change(4)"><bean:message key="menu_log"/></a></td></tr>
	<tbody id="menu_4" style="display:"><tr class="leftmenutd"><td><table border="0" cellspacing="0" cellpadding="0" class="leftmenuinfo">
		<tr><td><a href="admincp.jsp?action=ratelog" target="main"><bean:message key="menu_log_rating"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=modslog" target="main"><bean:message key="menu_log_mod"/></a></td></tr>
		<tr><td><a href="admincp.jsp?action=banlog" target="main"><bean:message key="menu_log_ban"/></a></td></tr>
	</table></td></tr></tbody>
</table></c:if>
</c:otherwise></c:choose>
</body></html>