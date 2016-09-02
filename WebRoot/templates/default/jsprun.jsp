<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:include flush="true" page="header.jsp" />
<link href="include/css/keyboard.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
lang["kb_backSpace"] = '← <bean:message key="kb_backSpace"/>';
lang["kb_capsLockOff"] = '<bean:message key="kb_capsLockOff"/>';
lang["kb_capsLockOn"] = '<bean:message key="kb_capsLockOn"/>';
lang["kb_clear"] = '<bean:message key="kb_clear"/>';
lang["kb_enter"] = '←|\n<bean:message key="kb_enter"/>';
lang["kb_title"] = '<bean:message key="kb_title"/>';
</script>
<script src="include/javascript/keyboard.js" type="text/javascript"></script>
<div id="foruminfo"><div id="userinfo">
	<div id="nav"><c:choose><c:when test="${!empty gid||jsprun_uid==0}"><a href="${settings.indexname}">${settings.bbname}</a></c:when><c:otherwise><a href="space.jsp?action=viewpro&uid=${jsprun_uid}" class="dropmenu" id="creditlist" onmouseover="showMenu(this.id)">${jsprun_userss}</a></c:otherwise></c:choose><c:if test="${jsprun_uid>0&&settings.spacestatus==1}"> - <a href="space.jsp?uid=${jsprun_uid}" target="_blank"><bean:message key="space" /></a></c:if></div>
	<p><c:choose><c:when test="${jsprun_uid>0}"><c:if test="${usergroups.allowinvisible==1}"><bean:message key="a_post_tags_status" /> <span id="loginstatus"><a href="member.jsp?action=switchstatus" title="<c:choose><c:when test="${user.invisible==1}"><bean:message key="login_switch_normal_mode" /></c:when><c:otherwise><bean:message key="login_switch_invisible_mode" /></c:otherwise></c:choose>" onclick="ajaxget(this.href, 'loginstatus');doane(event);"><c:choose><c:when test="${user.invisible==1}"><bean:message key="login_invisible_mode" /></c:when><c:otherwise><bean:message key="login_normal_mode" /></c:otherwise></c:choose></a></span>,</c:if> <bean:message key="your_lastvisit" />: <em>${user_lastvisit}</em> ${(settings.google_status==1&&google_searchbox>0)||(settings.baidu_status==1&&baidu_searchbox>0)?"<br/>":"&nbsp;"} <a href="search.jsp?srchfrom=${newthreads}&searchsubmit=yes&formHash=${jrun:formHash(pageContext.request)}"><bean:message key="home_newthreads" /></a> <a href="member.jsp?action=markread" id="ajax_markread" onclick="ajaxmenu(event, this.id)"><bean:message key="mark_read" /></a></c:when>
	<c:when	test="${empty settings.passport_status}">
		<form id="loginform" method="post" name="login" action="logging.jsp?action=login&loginsubmit=true">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="cookietime" value="2592000" />
			<input type="hidden" name="accessing" value="logging" />
			<input type="hidden" name="loginfield" value="username" />
			<input type="text" id="username" name="username" size="15" maxlength="40" tabindex="1" value="<bean:message key="username" />" onclick="this.value = ''" />
			<input type="password" id="password" name="password" size="15" tabindex="2" onkeypress="if((event.keyCode ? event.keyCode : event.charCode) == 13) $('loginform').submit()" />
			<input type="button" style="margin-left: -27px;" class="keybord_button" onclick="jrunKeyBoard('password',this);" title="<bean:message key="keyboard_title"/>">
			<button name="userlogin" type="submit" value="true"><bean:message key="login" /></button>
		</form>
	</c:when></c:choose></p>
</div>
<div id="forumstats"><p><bean:message key="forum_todayposts" />: <em>${todayposts}</em>, <bean:message key="index_yesterday" />: <em>${postdata['0']}</em>, <bean:message key="index_maxday" />: <em>${postdata['1']}</em> &nbsp; <a href="digest.jsp"><bean:message key="digest_area" /></a> <c:if test="${settings.rssstatus==1}"><a href="rss.jsp?auth=${rssauth}" title="<bean:message key="rss_subscribe_all" />" target="_blank"><img src="images/common/xml.gif" alt="<bean:message key="rss_subscribe_all" />" /></a></c:if> </p><p><bean:message key="thread" />: <em>${threads}</em>, <bean:message key="a_setting_posts" />: <em>${posts}</em>, <bean:message key="members" />: <em>${settings.totalmembers}</em>, <bean:message key="welcome_newmember" /> <cite><a href="space.jsp?username=<jrun:encoding value="${settings.lastmember}"/>">${settings.lastmember}</a></cite></p></div>
<c:if test="${(settings.google_status==1&&google_searchbox>0)||(settings.baidu_status==1&&baidu_searchbox>0)}">
<script type="text/javascript">
	lang['webpage_search'] = '<bean:message key="webpage_search"/>';
	lang['site_search'] = '<bean:message key="site_search"/>';
	lang['search'] = '<bean:message key="search"/>';
</script>
<div id="headsearch" style="clear: both">
	<c:if test="${settings.google_status==1&&google_searchbox>0}"><script type="text/javascript" src="forumdata/cache/google_var.js"></script><script type="text/javascript" src="include/javascript/google.js"></script></c:if>
	<c:if test="${settings.baidu_status==1&&baidu_searchbox>0}"><script type="text/javascript" src="forumdata/cache/baidu_var.js"></script><script type="text/javascript" src="include/javascript/baidu.js"></script></c:if>
</div>
</c:if></div>
<c:if test="${empty gid&&announcements!=null}">
<div id="ann" onmouseover="annstop = 1" onmouseout="annstop = 0"><dl><dt><bean:message key="index_announcements" />:</dt><dd><div id="annbody"><ul id="annbodylis">${announcements}</ul></div></dd></dl></div>
<script type="text/javascript">
var anndelay = 3000;
var annst = 0;
var annstop = 0;
var annrowcount = 0;
var anncount = 0;
var annlis = $('annbody').getElementsByTagName("LI");
var annrows = new Array();
var annstatus;
function announcementScroll() {
if(annstop) {
annst = setTimeout('announcementScroll()', anndelay);
return;
}
if(!annst) {
var lasttop = -1;
for(i = 0;i < annlis.length;i++) {
if(lasttop != annlis[i].offsetTop) {
if(lasttop == -1) {
lasttop = 0;
}
annrows[annrowcount] = annlis[i].offsetTop - lasttop;
annrowcount++;
}
lasttop = annlis[i].offsetTop;
}
if(annrows.length == 1) {
$('ann').onmouseover = $('ann').onmouseout = null;
} else {
annrows[annrowcount] = annrows[1];
$('annbodylis').innerHTML += $('annbodylis').innerHTML;
annst = setTimeout('announcementScroll()', anndelay);
}
annrowcount = 1;
return;
}
if(annrowcount >= annrows.length) {
$('annbody').scrollTop = 0;
annrowcount = 1;
annst = setTimeout('announcementScroll()', anndelay);
} else {
anncount = 0;
announcementScrollnext(annrows[annrowcount]);
}
}
function announcementScrollnext(time) {
$('annbody').scrollTop++;
anncount++;
if(anncount != time) {
annst = setTimeout('announcementScrollnext(' + time + ')', 10);
} else {
annrowcount++;
annst = setTimeout('announcementScroll()', anndelay);
}
}
</script>
</c:if>
<c:if test="${empty gid&&pmlists!=null}"><div style="clear: both; margin-top: 5px" id="pmprompt"><jsp:include flush="true" page="pmprompt.jsp" /></div></c:if>
<div id="ad_text"></div>
<c:if test="${settings.tagstatus==1&&settings.hottags>0&&!empty tags.tags}">
<table summary="HeadBox" class="portalbox" cellpadding="0" cellspacing="1">
	<tr><td><div id="hottags"><h3><a href="tag.jsp" target="_blank"><bean:message key="a_post_tags_hot" /></a></h3>${tags.tags}</div></td></tr>
</table>
</c:if>
<c:forEach items="${catlists}" var="cat"><c:if test="${cat.forumscount>0}"><div class="mainbox forumlist">
	<span class="headactions"><c:if test="${!empty cat.moderators}"><bean:message key="forum_category_modedby" />: ${cat.moderators}</c:if> <img id="category_${cat.fid}_img" src="${styles.IMGDIR}/${cat.collapseimg}" title="<bean:message key="spread" />" alt="<bean:message key="spread" />" onclick="toggle_collapse('category_${cat.fid}');" /></span>
	<h3><a href="${settings.indexname}?gid=${cat.fid}">${cat.name}</a></h3><c:set var="category" value="category_${cat.fid}" scope="page"/>
	<table id="category_${cat.fid}" summary="category${cat.fid}" cellspacing="0" cellpadding="0" style="${collapseMap[category]}"><c:choose>
		<c:when test="${cat.forumcolumns>0}"><tbody><tr><c:forEach items="${forums[cat.fid]}" var="forum" varStatus="index"><th width="${cat.forumcolwidth}" ${forum.folder}><h2><a href="${forum.url}">${forum.name}</a><c:if test="${forum.todayposts>0}"><em> (<bean:message key="forum_todayposts" />: ${forum.todayposts})</em></c:if></h2><p><bean:message key="thread" />: ${forum.threads}, <bean:message key="forum_posts" />: ${forum.posts}</p><p><c:choose><c:when test="${forum.permission == 1}"><bean:message key="private_forum" /></c:when><c:otherwise><c:set var="lastpost" value="${lastposts[forum.fid]}" scope="page"/><bean:message key="a_post_threads_lastpost" />:	<c:choose><c:when test="${lastpost.tid>0}"><a href="redirect.jsp?tid=${lastpost.tid}&goto=lastpost#lastpost">${lastpost.dateline}</a> by <c:choose><c:when test="${lastpost.author == ''}"><bean:message key="anonymous" /></c:when><c:otherwise>${lastpost.author}</c:otherwise></c:choose></c:when><c:otherwise><bean:message key="never" /></c:otherwise></c:choose></c:otherwise></c:choose></p></th>${(index.count%(cat.forumcolumns))==0?"</tr></tbody><tbody><tr>":""}</c:forEach>${cat.endrows}</tr></tbody></c:when>
		<c:otherwise><thead class="category"><tr><th><bean:message key="forum_name" /></th><td class="nums"><bean:message key="thread" /></td><td class="nums"><bean:message key="forum_posts" /></td><td class="lastpost"><bean:message key="a_post_threads_lastpost" /></td></tr></thead><c:forEach items="${forums[cat.fid]}" var="forum"><tbody id="forum${forum.fid}"><tr><th ${forum.folder}>${forum.icon}<h2><a href="${forum.url}">${forum.name}</a><c:if test="${forum.todayposts>0&&empty forum.redirect}"><em> (<bean:message key="forum_todayposts" />: ${forum.todayposts})</em></c:if></h2><c:if test="${forum.description!=''}"><p>${forum.description}</p></c:if><c:if test="${forum.subforums!=''}"><p><bean:message key="sub_forums" />: ${forum.subforums}</p></c:if><c:if test="${forum.moderators!=''}"><c:choose><c:when test="${settings.moddisplay == 'flat'}"><p class="moderators"><bean:message key="usergroups_system_3" />: ${forum.moderators}</p></c:when><c:otherwise><span class="dropmenu" id="mod${forum.fid}" onmouseover="showMenu(this.id)"><bean:message key="usergroups_system_3" /></span><ul class="moderators popupmenu_popup" id="mod${forum.fid}_menu" style="display: none"><c:forTokens items="${forum.moderators}" delims="," var="moderator"><li>${moderator}</li></c:forTokens></ul></c:otherwise></c:choose></c:if></th><td class="nums">${empty forum.redirect ? forum.threads : "--"}</td><td class="nums">${empty forum.redirect ? forum.posts : "--"}</td><td class="lastpost"><c:choose><c:when test="${forum.permission == 1}"><bean:message key="private_forum" /></c:when><c:otherwise><c:set var="lastpost" value="${lastposts[forum.fid]}" scope="page"/><c:choose><c:when test="${!empty forum.redirect}">--</c:when><c:when test="${lastpost.tid>0}"><a href="redirect.jsp?tid=${lastpost.tid}&goto=lastpost#lastpost">${lastpost.subject}</a><cite>by <c:choose><c:when test="${lastpost.author == ''}"><bean:message key="anonymous" /></c:when><c:otherwise>${lastpost.author}</c:otherwise></c:choose> - ${lastpost.dateline}</cite></c:when><c:otherwise><bean:message key="never" /></c:otherwise></c:choose></c:otherwise></c:choose></td></tr></tbody></c:forEach></c:otherwise>
	</c:choose></table>
</div><div id="ad_intercat_${cat.fid}"></div></c:if></c:forEach>
<c:if test="${settings.forumlinkstatus==1&&forumlinks!=null}"><div class="box">
	<span class="headactions"><img id="forumlinks_img" src="${styles.IMGDIR}/${collapseimgMap['forumlinks']}" alt="" onclick="toggle_collapse('forumlinks');" /></span>
	<h4><bean:message key="menu_other_link" /></h4>
	<table summary="<bean:message key="menu_other_link" />" id="forumlinks" cellpadding="0" cellspacing="0" style="${collapseMap['forumlinks']}">
		<c:forEach items="${forumlinks}" var="link"><tr><td><c:if test="${link.value.type==1}"><img src="${link.value.logo}" alt="" class="forumlink_logo" /></c:if>${link.value.content }</td></tr></c:forEach>
	</table>
</div></c:if>
<c:if test="${empty gid&&(whosonlinestatus||settings.maxbdays>0)}"><div class="box" id="online">
	<c:choose><c:when test="${whosonlinestatus}"><c:choose><c:when test="${detailstatus}"><span class="headactions"><a href="${settings.indexname}?showoldetails=no#online" title="<bean:message key="closed" />"><img src="${styles.IMGDIR}/collapsed_no.gif" alt="<bean:message key="closed" />" /></a></span><h4><strong><a href="member.jsp?action=online"><bean:message key="a_system_js_stats_onlinemembers" /></a></strong> - <em>${onlinenum}</em> <bean:message key="onlines" /> - <em>${membercount}</em> <bean:message key="members" />(<em>${invisiblecount}</em> <bean:message key="index_invisibles" />), <em>${guestcount}</em> <bean:message key="index_guests" /> - <bean:message key="index_mostonlines" /> <em>${onlineinfo[0]}</em> <bean:message key="on" /> <em>${onlineinfo[1]}</em>.</h4></c:when><c:otherwise><span class="headactions"><a href="${settings.indexname}?showoldetails=yes#online" class="nobdr"><img src="${styles.IMGDIR}/collapsed_yes.gif" alt="" /></a></span><h4><strong><a href="member.jsp?action=online"><bean:message key="a_system_js_stats_onlinemembers" /></a></strong> - <bean:message key="total" /> <em>${onlinenum}</em> <bean:message key="onlines" /> - <bean:message key="index_mostonlines" /> <em>${onlineinfo[0]}</em> <bean:message key="on" /> <em>${onlineinfo[1]}</em>.</h4></c:otherwise></c:choose></c:when><c:otherwise><h4><strong><a href="member.jsp?action=online"><bean:message key="a_system_js_stats_onlinemembers" /></a></strong></h4></c:otherwise></c:choose>
	<c:if test="${settings.maxbdays>0}"><div id="bdayslist"><c:choose><c:when test="${!empty birthdays_index.todaysbdays}"><a href="member.jsp?action=list&type=birthdays"><bean:message key="todays_birthdays" /></a>: ${birthdays_index.todaysbdays}</c:when><c:otherwise><bean:message key="todays_birthdays_none" /></c:otherwise></c:choose></div></c:if>
	<c:if test="${whosonlinestatus}"><dl id="onlinelist"><dt>${onlinelist.legend}</dt><c:if test="${detailstatus}"><dd><ul class="userlist"><c:choose><c:when test="${whosonline!=null}"><c:forEach items="${whosonline}" var="online"><li title="<bean:message key="time" />: ${online.lastactivity}<%="\n" %> <bean:message key="operation" />: ${online.action}${online.fid}"><img src="images/common/${online.icon}" alt="" /> <c:choose><c:when test="${online.uid>0}"><a href="space.jsp?uid=${online.uid}">${online.username}</a></c:when><c:otherwise>${online.username}</c:otherwise></c:choose></li></c:forEach></c:when><c:otherwise><li style="width: auto"><bean:message key="online_only_guests" /></li></c:otherwise></c:choose></ul></dd></c:if></dl></c:if>
</div></c:if>
<div class="legend"><label><img src="${styles.IMGDIR}/forum_new.gif" alt="<bean:message key="forum_newposts" />" /><bean:message key="forum_newposts" /></label><label><img src="${styles.IMGDIR}/forum.gif" alt="<bean:message key="forum_nonewpost" />" /><bean:message key="forum_nonewpost" /></label></div>
<ul class="popupmenu_popup" id="creditlist_menu" style="display: none"><li><bean:message key="credits" />: ${user.credits}</li><c:forEach items="${extcredits}" var="extcredit"><li><c:set var="extcr" value="extcredits${extcredit.key}" />${extcredit.value.title}: ${user[extcr]} ${extcredit.value.unit}</li></c:forEach></ul>
<c:if test="${empty gid&&announcements!=null}"><script type="text/javascript">announcementScroll();</script></c:if>
<jsp:include flush="true" page="footer.jsp" />