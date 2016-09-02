<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../include/jsprun_version.jsp"%>
<jsp:directive.include file="cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
 <tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="header_admin"/></td></tr>
</table><br />
<form method="post" action="admincp.jsp?action=home">
 <input type="hidden" name="securyservice" value="yes">
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
  <tr class="header"><td colspan="3"><bean:message key="home_security_tips"/></td></tr>
<c:choose>
 <c:when test="${members.adminid==1}">
  <tr class="altbg1">
   <td><bean:message key="home_security_service"/></td>
   <td colspan="2"><li><bean:message key="home_security_service_info"/></li></td>
  </tr>
  <tr class="altbg1">
   <td width="20%"><bean:message key="home_security_advise"/></td>
   <td colspan="2">
    <c:if test="${empty members.secques}"><li><bean:message key="home_security_secques"/></li></c:if>
    <c:if test="${empty settings.forumfounders}"><li><bean:message key="home_security_nofounder"/></li></c:if>
    <c:if test="${settings.admincp_checkip==0}"><li><bean:message key="home_security_checkip"/></li></c:if>
    <c:if test="${settings.admincp_tpledit==1}"><li><bean:message key="home_security_tpledit"/></li></c:if>
    <c:if test="${settings.admincp_runquery==1}"><li><bean:message key="home_security_runquery"/></li></c:if>
   </td>
  </tr>
 </c:when>
 <c:when test="${empty members.secques}">
  <tr class="altbg1">
   <td width="20%"><bean:message key="home_security_advise"/></td>
   <td colspan="2"><li><bean:message key="home_security_secques"/></li></td>
  </tr>
 </c:when>
</c:choose>
 </table>
</form><br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
 <tr class="header"><td><bean:message key="home_onlines"/></td></tr>
 <tr><td><c:forEach items="${adminSessions}" var="adminSession" varStatus="index">${index.index>0 ? ",&nbsp;" :""}<c:choose><c:when test="${adminSession.errorcount>-1}"><i><a href="space.jsp?action=viewpro&uid=${adminSession.uid}" target="_blank" title="<bean:message key='time'/>: ${adminSession.dateline}&#10;<c:if test='${adminSession.errorcount>-1}'><bean:message key='home_onlines_errors'/>: ${adminSession.errorcount}&#10;</c:if><c:if test='${usergroups.allowviewip>0&&(members.adminid<=adminSession.adminid||adminSession.adminid<=0)}'><bean:message key='home_online_regip'/>: ${adminSession.regip}&#10;<bean:message key='home_onlines_ip'/>: ${adminSession.ip}</c:if>">${adminSession.username}</a></i></c:when><c:otherwise><a href="space.jsp?action=viewpro&uid=${adminSession.uid}" target="_blank" title="<bean:message key='time'/>: ${adminSession.dateline}&#10;<c:if test='${usergroups.allowviewip>0&&(members.adminid<=adminSession.adminid||adminSession.adminid<=0)}'><bean:message key='home_online_regip'/>: ${adminSession.regip}&#10;<bean:message key='home_onlines_ip'/>: ${adminSession.ip}</c:if>">${adminSession.username}</a></c:otherwise></c:choose></c:forEach></td></tr>
</table><br />
<div id="boardnews"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
 <tr class="header"><td colspan="3"><bean:message key="home_stuff"/></td></tr>
 <c:choose>
  <c:when test="${members.adminid==1}">
   <form method="post" action="admincp.jsp?action=members">
    <input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
    <input type="hidden" name="seasubmit" value="yes">
    <tr class="altbg1">
     <td><bean:message key="menu_member_edit"/></td>
     <td><input type="text" size="30" name="username"></td>
     <td><input class="button" type="submit" name="searchsubmit" value="<bean:message key='submit'/>"></td>
    </tr>
   </form>
   <form method="post" action="admincp.jsp?action=forumdetail">
    <input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
    <tr class="altbg2">
     <td><bean:message key="menu_forum_edit"/></td>
     <td>
      <select name="fid">
       <option value="0">&nbsp;&nbsp;> <bean:message key="select"/></option>
       ${forumSelect}
      </select>
     </td>
     <td><input class="button" type="submit" value="<bean:message key='submit'/>"></td>
    </tr>
   </form>
   <form method="post" action="admincp.jsp?action=tousergroupinfo">
    <input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
    <tr class="altbg1">
     <td><bean:message key="usergroups_edit"/></td>
     <td>
      <select name="edit"><c:forEach items="${userGroups}" var="userGroup">
        <option value="${userGroup.groupid}">${userGroup.grouptitle}</option></c:forEach>
      </select>
     </td>
     <td><input class="button" type="submit" value="<bean:message key="submit"/>"></td>
    </tr>
   </form>
  </c:when>
  <c:when test="${usergroups.allowedituser==1}">
   <form method="post" action="admincp.jsp?action=editmember">
    <input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
    <tr class="altbg1">
     <td><bean:message key="menu_member_edit"/></td>
     <td><input type="text" size="30" name="username"></td>
     <td><input class="button" type="submit" name="membersubmit" value="<bean:message key="submit"/>"></td>
    </tr>
   </form>
  </c:when>
 </c:choose>
</table><br />
<form method="post" action="admincp.jsp?action=home">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
  <tr class="header"><td colspan="8"><bean:message key="home_announce"/></td></tr>
  <tr class="category">
   <td width="5%"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form, 'delete');"><bean:message key="del"/></td>
   <td width="8%"><bean:message key="username"/></td>
   <td width="8%"><bean:message key="time"/></td>
   <td width="30%"><bean:message key="message"/></td>
   <td width="8%" nowrap><bean:message key="usergroups_system_1"/></td>
   <td width="8%" nowrap><bean:message key="usergroups_system_2"/></td>
   <td width="8%" nowrap><bean:message key="usergroups_system_3"/></td>
   <td width="10%"><bean:message key="validity"/></td>
  </tr>
  <c:forEach items="${adminNotes}" var="adminNote">
   <tr class="center">
    <td class="altbg1"><input class="checkbox" type="checkbox" name="delete" value="${adminNote.id}" ${members.username==adminNote.admin|| adminNote.adminid >= members.adminid ? "" : "disabled"}></td>
    <td class="altbg2"><a href="space.jsp?action=viewpro&username=${adminNote.adminEnc}" target="_blank">${adminNote.admin}</a></td>
    <td class="altbg1">${adminNote.dateline}</td>
    <td class="altbg2"><b>${adminNote.message}</b></td>
    <td class="altbg1">${adminNote.access1}</td>
    <td class="altbg2">${adminNote.access2}</td>
    <td class="altbg1">${adminNote.access3}</td>
    <td class="altbg2">${adminNote.expiration}</td>
   </tr>
  </c:forEach>
  <tr align="center">
   <td class="altbg1"><bean:message key="add_new"/></td>
   <td class="altbg2" colspan="3"><textarea name="newmessage" rows="2" style="width: 95%; word-break: break-all"></textarea></td>
   <td class="altbg1"><input class="checkbox" type="checkbox" name="newaccess[1]" value="1" checked ${members.groupid==1?"disabled":""}></td>
   <td class="altbg2"><input class="checkbox" type="checkbox" name="newaccess[2]" value="1" checked ${members.groupid==2?"disabled":""}></td>
   <td class="altbg1"><input class="checkbox" type="checkbox" name="newaccess[3]" value="1" checked ${members.groupid==3?"disabled":""}></td>
   <td class="altbg2">
    <input type="text" name="newexpiration" size="8" value="${newexpiration}">
    <input class="button" type="submit" name="notesubmit" value="<bean:message key="submit"/>">
   </td>
  </tr>
 </table>
</form><br />
<c:if test="${members.adminid==1}">
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
  <tr class="header"><td colspan="2"><bean:message key="home_sys_info"/></td></tr>
  <tr class="altbg2"><td width="25%"><bean:message key="home_jsprun_version"/></td><td>JspRun! ${JSPRUN_VERSION} Release ${JSPRUN_RELEASE} <a href="http://www.jsprun.net/forumdisplay.jsp?fid=7" target="_blank">[ <bean:message key="home_check_newversion"/> ]</a></td></tr>
  <tr class="altbg1"><td width="25%"><bean:message key="home_environment"/></td><td>${sysType} / ${jkdversion} </td></tr>
  <tr class="altbg2"><td><bean:message key="home_database"/></td><td>${mysqlversion}</td></tr>
  <tr class="altbg1"><td><bean:message key="home_upload_perm"/></td><td>${maxupload}</td></tr>
  <tr class="altbg2"><td><bean:message key="home_database_size"/></td><td><jrun:showFileSize size="${totalsize}" /></td></tr>
  <tr class="altbg1"><td><bean:message key="home_attach_size"/></td><td><c:choose><c:when test="${attasize!=null}"><jrun:showFileSize size="${attasize}"/></c:when><c:otherwise><a href="admincp.jsp?action=home&attasize=1">[ <bean:message key="detail"/> ]</a></c:otherwise></c:choose></td></tr>
  <tr class="altbg2"><td><bean:message key="menu_tool_fileperm"/></td><td><a href='admincp.jsp?action=fileperms'>[ <bean:message key="check"/> ]</a></td></tr>
 </table><br />
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
 <tr class="header"><td colspan="2">JspRun! <bean:message key="home_dev"/></td></tr>
 <tr class="altbg2"><td width="25%"><bean:message key="home_dev_copyright"/></td><td class="smalltxt"> <a href="http://www.jsprun.com" target="_blank"><bean:message key="company_full_name" /> (JspRun! Inc.)</a></td></tr>
 <tr class="altbg1"><td><bean:message key="home_dev_enterprise_site"/></td><td class="smalltxt"><a href="http://www.jsprun.com" target="_blank">http://www.jsprun.com</a></td></tr>
 <tr class="altbg2"><td><bean:message key="home_dev_project_site"/></td><td class="smalltxt"><a href="http://www.jsprun.com" target="_blank">http://www.jsprun.com</a></td></tr>
 <tr class="altbg1"><td><bean:message key="home_dev_community"/></td><td class="smalltxt"><a href="http://www.jsprun.net" target="_blank">http://www.jsprun.net</a></td></tr>
</table><br />
<jsp:directive.include file="cp_footer.jsp" />
<c:if test="${members.adminid==1}"><script src="http://www.jsprun.com/new.jsp?jsprun_version=${JSPRUN_VERSION}&jsprun_release=${JSPRUN_RELEASE}" type="text/javascript" charset="UTF-8"></script></c:if>