<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%><%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title>${settings.bbname}</title>
  <style type="text/css">
   body  { background-color: #FFFFFF; color: #000000; font-family: Verdana, Tahoma; font-size: 12px; margin: 20px; padding: 0px; }
   #largetext { font-size: 18px; font-weight: bold; margin-bottom: 10px; padding-top: 3px; width: auto; }
   #userinfo { font-size: 12px; color: #888888; text-align: right; width: auto; }
   #copyright { margin-top: 30px; font-size: 11px; text-align: center; }
   .wrapper { }
   .subject { font-size: 14px; font-weight: bold; padding: 3px; margin-bottom: 10px; border: 1px solid #A8A8A8; }
   .pm   { color: #000000; padding: 10px; margin-top:10px; border: 1px solid #888888; }
   .content { color: #888888; }
   .msgborder { margin: 2em; margin-top: 3px; padding: 10px; border: 1px solid #888888; word-break: break-all; }
  </style>
 </head>
 <body>
  <div id="wrapper">
   <div id="largetext"><bean:message key="pm_archive"/></div>
   <div id="userinfo"><a href="${boardurl}space.jsp?uid=${jsprun_uid}" target="_blank">${jsprun_userss} @ <jrun:showTime timeInt="${timestamp}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></a></div><br />
   <c:forEach items="${pmslist}" var="pms">
    <div class="pm">
     <div class="subject">${pms.subject}</div>
     <strong><bean:message key="time" />:</strong> <jrun:showTime timeInt="${pms.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}" /><br />
     <strong><bean:message key="pm_folders" />:</strong> <bean:message key="${pms.folder == 'outbox' ? 'pm_outbox' : 'pm_inbox'}"/><br />
     <strong><bean:message key="location_from" />:</strong> <c:choose><c:when test="${pms.msgfromid>0}"><a href="${boardurl}space.jsp?uid=${pms.msgfromid}">${pms.msgfrom}</a></c:when><c:otherwise><bean:message key="pm_systemmessage"/></c:otherwise></c:choose><br />
     <strong><bean:message key="to" />:</strong> <a href="${boardurl}space.jsp?uid=${pms.msgtoid}" target="_blank">${pms.username}</a><br /><br />
     <div class="content">${pms.message}</div>
    </div>
   </c:forEach>
  </div>
  <div id="copyright">
   Powered by <strong><a href="http://www.jsprun.net" target="_blank">Jsprun!</a> ${settings.version}</strong> &nbsp;&copy; 2007-2011
   <a href="http://www.jsprun.com" target="_blank">Jsprun Inc.</a>
  </div>
 </body>
</html>