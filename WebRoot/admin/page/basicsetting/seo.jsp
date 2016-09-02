<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_seo" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=seo">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
		<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
			<tr><td><ul><li><bean:message key="a_setting_tips" /></ul></td></tr>
		</tbody>
	</table>
	<br />
	<a name="304e38a0fab24f26"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="menu_setting_seo" /><a href="###" onclick="collapse_change('304e38a0fab24f26')"><img id="menuimg_304e38a0fab24f26" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_304e38a0fab24f26" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_archiverstatus" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_archiverstatus_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="archiverstatus" value="0" ${settings.archiverstatus==0?"checked":""}> <bean:message key="closed" /><br />
					<input class="radio" type="radio" name="archiverstatus" value="1" ${settings.archiverstatus==1?"checked":""}> <bean:message key="a_setting_archiverstatus_full" /><br />
					<input class="radio" type="radio" name="archiverstatus" value="2" ${settings.archiverstatus==2?"checked":""}> <bean:message key="a_setting_archiverstatus_searchengine" /><br />
					<input class="radio" type="radio" name="archiverstatus" value="3" ${settings.archiverstatus==3?"checked":""}> <bean:message key="a_setting_archiverstatus_browser" /><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><u><i><bean:message key="a_setting_rewritestatus" /></i> </u> </b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_rewritestatus_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="checkbox" type="checkbox" ${rewritestatus0} name="rewritestatus0" value="1"> <bean:message key="a_setting_rewritestatus_forumdisplay" /><br />
					<input class="checkbox" type="checkbox" ${rewritestatus1} name="rewritestatus1" value="2"> <bean:message key="a_setting_rewritestatus_viewthread" /><br />
					<input class="checkbox" type="checkbox" ${rewritestatus2} name="rewritestatus2" value="4"> <bean:message key="a_setting_rewritestatus_space" /><br />
					<input class="checkbox" type="checkbox" ${rewritestatus3} name="rewritestatus3" value="8"> <bean:message key="a_setting_rewritestatus_tag" /><br />
					<input class="checkbox" type="checkbox" ${rewritestatus4} name="rewritestatus4" value="16"> <bean:message key="a_setting_rewritestatus_archiver" /><br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_seotitle" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_seotitle_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="seotitle" value="${settings.seotitle}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_seokeywords" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_seokeywords_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="seokeywords" value="${settings.seokeywords}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_seodescription" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_seodescription_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="seodescription" value="${settings.seodescription}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_seohead" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_seohead_comment" /></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[seohead]', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[seohead]', 0)">
					<br />
					<textarea rows="6" name="seohead" id="settingsnew[seohead]" cols="50">${settings.seohead}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="0d217a9b86bcdf72"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_sitemap" /><a href="###" onclick="collapse_change('0d217a9b86bcdf72')"><img id="menuimg_0d217a9b86bcdf72" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_0d217a9b86bcdf72" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_sitemap_baidu_open" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_sitemap_baidu_open_comment" /></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="baidusitemap" value="1" onclick="$('hidden_settings_sitemap_baidu_open').style.display = '';" checked> <bean:message key="yes" /> &nbsp; &nbsp;
					<input class="radio" type="radio" name="baidusitemap" value="0" onclick="$('hidden_settings_sitemap_baidu_open').style.display = 'none';" ${settings.baidusitemap==0?"checked":""}> <bean:message key="no" />
				</td>
			</tr>
		</tbody>
		<tbody class="sub" id="hidden_settings_sitemap_baidu_open" style="display: ${settings.baidusitemap==1?'':'none'}">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_sitemap_baidu_expire" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_sitemap_baidu_expire_comment" /></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="baidusitemap_life" value="${settings.baidusitemap_life}"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input type="hidden" name="from" value="">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />