<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
 <tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_datetime" /></td></tr>
</table><br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=datetime">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
 <a name="ac07747e7db4a4cc"></a>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
  <tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_datetime" /><a href="###" onclick="collapse_change('ac07747e7db4a4cc')"><img id="menuimg_ac07747e7db4a4cc" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
  <tbody id="menu_ac07747e7db4a4cc" style="display: yes">
   <tr>
    <td width="45%" class="altbg1">
     <b><bean:message key="a_setting_dateformat" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_dateformat_comment" /></span>
    </td>
    <td class="altbg2"><input type="text" size="50" name="dateformat" value="${settings.dateformat}"></td>
   </tr>
   <tr>
    <td width="45%" class="altbg1"><b><bean:message key="a_setting_timeformat" /></b></td>
    <td class="altbg2">
     <input class="radio" type="radio" name="timeformat" value="2" checked> 24 <bean:message key="hour" />
     <input class="radio" type="radio" name="timeformat" value="1" ${settings.timeformat!=2?"checked":""}> 12 <bean:message key="hour" />
    </td>
   </tr>
   <tr>
    <td width="45%" class="altbg1">
     <b><bean:message key="a_setting_timeoffset" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_timeoffset_comment" /></span>
    </td>
    <td class="altbg2"><select name="timeoffset"><c:forEach items="${timeZoneIDs}" var="timeZoneID">
     <option value="${timeZoneID.key}"${settings.timeoffset==timeZoneID.key ? " selected" : ""}>${timeZoneID.value[1]}</option></c:forEach>
    </select></td>
   </tr>
   <tr>
    <td width="45%" class="altbg1" valign="top">
     <b><bean:message key="a_setting_customformat" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_customformat_comment" /></span>
    </td>
    <td class="altbg2">
     <img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[userdateformat]', 1)">
     <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[userdateformat]', 0)">
     <br /><textarea rows="6" name="userdateformat" id="settingsnew[userdateformat]" cols="50">${settings.userdateformat }</textarea>
    </td>
   </tr>
  </tbody>
 </table><br />
 <a name="1cfc3d3d34bf77fc"></a>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
  <tr class="header"><td colspan="2"><bean:message key="a_setting_subtitle_periods" /><a href="###" onclick="collapse_change('1cfc3d3d34bf77fc')"><img id="menuimg_1cfc3d3d34bf77fc" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
  <tbody id="menu_1cfc3d3d34bf77fc" style="display: yes">
   <tr>
    <td width="45%" class="altbg1" valign="top">
     <b><bean:message key="a_setting_visitbanperiods" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_visitbanperiods_comment" /></span>
    </td>
    <td class="altbg2">
     <img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[visitbanperiods]', 1)">
     <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[visitbanperiods]', 0)">
     <br /><textarea rows="6" name="visitbanperiods" id="settingsnew[visitbanperiods]" cols="50">${settings.visitbanperiods}</textarea>
    </td>
   </tr>
   <tr>
    <td width="45%" class="altbg1" valign="top">
     <b><bean:message key="a_setting_postbanperiods" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_postbanperiods_comment" /></span>
    </td>
    <td class="altbg2">
     <img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[postbanperiods]', 1)">
     <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[postbanperiods]', 0)">
     <br /><textarea rows="6" name="postbanperiods" id="settingsnew[postbanperiods]" cols="50">${settings.postbanperiods }</textarea>
    </td>
   </tr>
   <tr>
    <td width="45%" class="altbg1" valign="top">
     <b><bean:message key="a_setting_postmodperiods" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_postmodperiods_comment" /></span>
    </td>
    <td class="altbg2">
     <img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[postmodperiods]', 1)">
     <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[postmodperiods]', 0)">
     <br /><textarea rows="6" name="postmodperiods" id="settingsnew[postmodperiods]" cols="50">${settings.postmodperiods}</textarea>
    </td>
   </tr>
   <tr>
    <td width="45%" class="altbg1" valign="top">
     <b><bean:message key="a_setting_ban_downtime" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_ban_downtime_comment" /></span>
    </td>
    <td class="altbg2">
     <img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[attachbanperiods]', 1)">
     <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[attachbanperiods]', 0)">
     <br /><textarea rows="6" name="attachbanperiods" id="settingsnew[attachbanperiods]" cols="50">${settings.attachbanperiods }</textarea>
    </td>
   </tr>
   <tr>
    <td width="45%" class="altbg1" valign="top">
     <b><bean:message key="a_setting_searchbanperiods" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_searchbanperiods_comment" /></span>
    </td>
    <td class="altbg2">
     <img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[searchbanperiods]', 1)">
     <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[searchbanperiods]', 0)">
     <br /><textarea rows="6" name="searchbanperiods" id="settingsnew[searchbanperiods]" cols="50">${settings.searchbanperiods}</textarea>
    </td>
   </tr>
  </tbody>
 </table><br />
 <center>
  <input type="hidden" name="from" value="${from}">
  <input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
 </center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />