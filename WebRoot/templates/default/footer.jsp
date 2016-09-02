<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%><jsp:include flush="true" page="/footer.do?action=footer" /></div>
<c:if test="${settings.jsmenustatus>0&&(!(settings.bbclosed==1)||jsprun_adminid==1)}"><jsp:include flush="true" page="jsmenu.jsp" /></c:if>
<ul class="popupmenu_popup headermenu_popup" id="language_menu" style="display: none">
<c:forEach items="${languages}" var="language"><c:if test="${language.value.available>0}"><li><a href="#" onclick="changeLanguage('${language.value.language}')">${language.value.name}</a></li></c:if></c:forEach>
</ul>
<div id="ad_footerbanner1"></div><div id="ad_footerbanner2"></div><div id="ad_footerbanner3"></div>
<div id="footer"><div class="wrap">
 <div id="footlinks">
  <p><bean:message key="time_now" arg0="${timenow.offset}" arg1="${timenow.time}"/><c:if test="${not empty settings.icp}"> <a href="http://www.miibeian.gov.cn/" target="_blank">${settings.icp}</a></c:if></p>
  <p>
   <a href="member.jsp?action=clearcookies&formhash=${formhash}"><bean:message key="clear_cookies"/></a>
   - <a href="mailto:${settings.adminemail}"><bean:message key="contactus"/></a>
   - <a href="${settings.siteurl}" target="_blank">${settings.sitename}</a>
   <c:if test="${settings.archiverstatus>0}">- <a href="archiver/" target="_blank">Archiver</a></c:if>
   <c:if test="${settings.wapstatus>0}">- <a href="wap/" target="_blank">WAP</a></c:if>
   - <span class="scrolltop" onclick="window.scrollTo(0,0);">TOP</span>
   <c:if test="${settings.stylejump>0}">- <span id="styleswitcher" class="dropmenu" onmouseover="showMenu(this.id)"><bean:message key="menu_style" /></span>
    <script type="text/javascript">
     function setstyle(styleid) {
      if(${CURSCRIPT=='forumdisplay.jsp'}){location.href = 'forumdisplay.jsp?fid=${fid}&page=${page}&styleid=' + styleid;
      }else if(${CURSCRIPT=='viewthread.jsp'}){location.href = 'viewthread.jsp?tid=${tid}&page=${page}&styleid=' + styleid;
      }else{location.href = '${settings.indexname}?styleid=' + styleid;}
     }
    </script>
    <div id="styleswitcher_menu" class="popupmenu_popup" style="display: none;">
     <ul><c:forEach items="${forumStyles}" var="style"><li ${style.key==styleid ? "class=current" : ""}><a href="###" onclick="setstyle(${style.key})">${style.value}</a></li></c:forEach></ul>
    </div>
   </c:if>
  </p>
 </div>
 <a href="http://www.jsprun.net" target="_blank" title="Powered by JspRun!"><img src="${styles.IMGDIR}/jsprun_icon.gif" border="0"/></a>
 <p id="copyright">
  Powered by <strong><a href="http://www.jsprun.net" target="_blank">JspRun!</a></strong> <em>${settings.version}</em><c:if test="${settings.boardlicensed>0}"> <a href="http://www.jsprun.com/index.jsp?action=certificate&host=${pageContext.request.serverName}" target="_blank">Licensed</a></c:if>
  &copy; 2007-2011 <a href="http://www.jsprun.com" target="_blank">JspRun! Inc.</a>
 </p>
 <c:if test="${settings.debug>0}"><p id="debuginfo">Processed in ${debuginfo.time} second(s)<c:if test="${settings.gzipcompress>0}">, Gzip enabled</c:if>.</p></c:if>
</div></div>
<c:if test="${settings.frameon>0&&(CURSCRIPT=='index.jsp'||CURSCRIPT=='forumdisplay.jsp'||CURSCRIPT=='viewthread.jsp')&&frameon=='yes'}"><script type="text/javascript" src="include/javascript/iframe.js"></script></c:if>
<c:if test="${(advlist!=null || queryfloat!=null) && !(CURSCRIPT == 'viewthread.jsp' &&thread.digest == '-1')}"><jsp:include flush="true" page="adv.jsp" /></c:if>
</body>
</html>