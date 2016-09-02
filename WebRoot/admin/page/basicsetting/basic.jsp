<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
 <tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="header_basic"/></td></tr>
</table><br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=basic">
 <input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
 <a name="a45b0b68771fc480"></a>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
  <tr class="header"><td colspan="2"><bean:message key="header_basic"/><a href="###" onclick="collapse_change('a45b0b68771fc480')"><img id="menuimg_a45b0b68771fc480" src="images/admincp/menu_reduce.gif" border="0" style="float: right; margin-top: 2px; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
  <tbody id="menu_a45b0b68771fc480" style="display: yes">
   <tr>
    <c:choose>
     <c:when test="${isfounder}">
      <td class=altbg1 width="45%">
      	<b><bean:message key="a_setting_initiator" />:</b>
      	<br /><bean:message key="a_setting_initiator_register_info1" />
      	<br /><span class=smalltxt><bean:message key="a_setting_initiator_register_info2" /></span>
      </td>
      <td class=altbg2><input size=50 name="forumfounders" value="${settings.forumfounders}"></td>
     </c:when>
     <c:otherwise><td colspan="2" class="altbg1" width="45%"><span><b><bean:message key="a_setting_master" /><c:forEach items="${founderlist}" var="founder">${founder.username}&nbsp;&nbsp;&nbsp;</c:forEach></b></span></td></c:otherwise>
    </c:choose>
   </tr>
   <tr>
    <td width="45%" class="altbg1">
     <b><bean:message key="a_setting_bbname" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_bbname_comment" /></span>
    </td>
    <td class="altbg2"><input type="text" size="50" name="bbname" value="<jrun:dhtmlspecialchars value="${settings.bbname}"/>"></td>
   </tr>
   <tr>
    <td width="45%" class="altbg1">
     <b><bean:message key="a_setting_sitename" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_sitename_comment" /></span>
    </td>
    <td class="altbg2"><input type="text" size="50" name="sitename" value="<jrun:dhtmlspecialchars value="${settings.sitename}"/>"></td>
   </tr>
   <tr>
    <td width="45%" class="altbg1">
     <b><bean:message key="a_setting_siteurl" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_siteurl_comment" /></span>
    </td>
    <td class="altbg2"><input type="text" size="50" name="siteurl" value="<jrun:dhtmlspecialchars value="${settings.siteurl}"/>"></td>
   </tr>
   <tr>
    <td width="45%" class="altbg1">
     <b><bean:message key="a_setting_index_name" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_index_name_comment" /></span>
    </td>
    <td class="altbg2"><input type="text" size="50" name="indexname" value="<jrun:dhtmlspecialchars value="${settings.indexname}"/>"></td>
   </tr>
   <tr>
    <td width="45%" class="altbg1">
     <b><bean:message key="a_setting_icp" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_icp_comment" /></span>
    </td>
    <td class="altbg2"><input type="text" size="50" name="icp" value="<jrun:dhtmlspecialchars value="${settings.icp}"/>"></td>
   </tr>
   <tr>
    <td width="45%" class="altbg1">
     <b><bean:message key="a_setting_boardlicensed" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_boardlicensed_comment" /></span>
    </td>
    <td class="altbg2">
     <input class="radio" type="radio" name="boardlicensed" value="1" checked><bean:message key="yes" /> &nbsp; &nbsp;
     <input class="radio" type="radio" name="boardlicensed" value="0" ${settings.boardlicensed!=1?"checked":""}><bean:message key="no" />
    </td>
   </tr>
   <tr>
    <td width="45%" class="altbg1">
     <b><bean:message key="a_setting_bbclosed" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_bbclosed_comment" /></span>
    </td>
    <td class="altbg2">
     <input class="radio" type="radio" name="bbclosed" value="1" checked><bean:message key="yes" /> &nbsp; &nbsp;
     <input class="radio" type="radio" name="bbclosed" value="0" ${settings.bbclosed!=1?"checked":""}><bean:message key="no" />
    </td>
   </tr>
   <tr>
    <td width="45%" class="altbg1" valign="top">
     <b><bean:message key="a_setting_closedreason" /></b>
     <br /><span class="smalltxt"><bean:message key="a_setting_closedreason_comment" /></span>
    </td>
    <td class="altbg2">
     <img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('closedreason', 1)"/>
     <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('closedreason', 0)"/>
     <br /><textarea rows="6" name="closedreason" id="closedreason" cols="50">${settings.closedreason}</textarea>
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