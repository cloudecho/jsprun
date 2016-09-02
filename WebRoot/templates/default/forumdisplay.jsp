<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="foruminfo">
	<div id="headsearch">
	<c:if test="${(settings.google_status==1&&google_searchbox>0)||(settings.baidu_status==1&&baidu_searchbox>0)}">
		<script type="text/javascript">
			lang['webpage_search'] = '<bean:message key="webpage_search"/>';
			lang['site_search'] = '<bean:message key="site_search"/>';
			lang['search'] = '<bean:message key="search"/>';
		</script>
	</c:if>
	<c:if test="${settings.google_status==1&&google_searchbox>0}"><script type="text/javascript" src="forumdata/cache/google_var.js"></script><script type="text/javascript" src="include/javascript/google.js"></script></c:if>
	<c:if test="${settings.baidu_status==1&&baidu_searchbox>0}"><script type="text/javascript" src="forumdata/cache/baidu_var.js"></script><script type="text/javascript" src="include/javascript/baidu.js"></script></c:if>
	<p>
		<c:if test="${forum.rules!=''}"><span id="rules_link" style="${collapse['rules_link']}"><a href="###" onclick="$('rules_link').style.display = 'none';toggle_collapse('rules', 1);<c:if test="${recommendlist!=null}">$('recommendlist').className = 'rules';</c:if>"><bean:message key="forum_rules" /></a> |</span></c:if>
		<c:if test="${recommendlist!=null}"><span id="recommendlist_link" style="${collapse['recommendlist_link']}"><a href="###" onclick="$('recommendlist_link').style.display = 'none';toggle_collapse('recommendlist', 1)"><bean:message key="a_forum_copy_option_modrecommend" /></a> |</span></c:if>
		<a href="my.jsp?item=favorites&fid=${fid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite" onclick="ajaxmenu(event, this.id)"><bean:message key="forum_favorite" /></a> | <a href="my.jsp?item=threads&srchfid=${fid}"><bean:message key="show_mytopics" /></a>
		<c:if test="${usergroups.allowmodpost>0&&forum.modnewposts>0}">| <a href="admincp.jsp?action=modthreads&frames=yes" target="_blank"><bean:message key="menu_post_modthreads" /></a></c:if>
		<c:if test="${forum.modnewposts== 2}">| <a href="admincp.jsp?action=modreplies&frames=yes" target="_blank"><bean:message key="menu_post_modreplies" /></a></c:if>
		<c:if test="${usergroups.admingid==1&&forum.recyclebin>0}">| <a href="admincp.jsp?action=recyclebin&frames=yes" target="_blank"><bean:message key="forum_recyclebin" /></a></c:if>
		<c:if test="${settings.rssstatus>0}"><a href="rss.jsp?fid=${fid}&auth=${rssauth }" target="_blank"><img src="images/common/xml.gif" border="0"class="absmiddle" alt="<bean:message key="rss_subscribe_all" />" /></a></c:if>
	</p></div>
	<div id="nav"><p><a id="forumlist" href="${settings.indexname}" ${settings.forumjump==1&&settings.jsmenu_1>0?"class=dropmenu onmouseover=showMenu(this.id)":""}>${settings.bbname}</a> ${navigation}</p><c:if test="${forum.description!=''}"><p><bean:message key="forum_introduce" />: ${forum.description}</p></c:if><p><bean:message key="usergroups_system_3" />: <c:choose><c:when test="${moderatedby!=''}">${moderatedby}</c:when><c:otherwise><bean:message key="forum_opening" /></c:otherwise></c:choose></p></div>
</div>
<c:if test="${forum.rules!=''||recommendlist!=null}">
	<table summary="Rules and Recommend" class="portalbox" cellpadding="0" cellspacing="1"><tr>
		<c:if test="${forum.rules!=''}"><td id="rules" style="${collapse['rules']}">
			<span class="headactions recommendrules"><img id="rules_img" src="${styles.IMGDIR}/collapsed_no.gif" title="<bean:message key="spread" />" alt="<bean:message key="spread" />" onclick="$('rules_link').style.display = '';toggle_collapse('rules', 1);<c:if test="${recommendlist!=null}">$('recommendlist').className = '';</c:if>" /></span>
			<h3><bean:message key="forum_rules" /></h3>	${forum.rules}
		</td></c:if>
		<c:if test="${recommendlist!=null}"><td id="recommendlist" ${forum.rules!=''&&(empty collapse['rules'])?"class=rules":""} style="${forum.rules!=''?'width: 50%;':''}${collapse['recommendlist']}">
			<span class="headactions recommendrules"><img id="recommendlist_img" src="${styles.IMGDIR}/collapsed_no.gif" title="<bean:message key="spread" />" alt="<bean:message key="spread" />" onclick="$('recommendlist_link').style.display ='';toggle_collapse('recommendlist', 1);" /></span><h3> <bean:message key="a_forum_copy_option_modrecommend" /> <c:if test="${ismoderator&&modrecommend.sort!=1}"><em>[<a href="admincp.jsp?action=forumrecommend&fid=${fid}&frames=yes" target="_blank"><bean:message key="forum_recommend_admin" /></a>]</em></c:if></h3><ul><c:forEach items="${recommendlist}" var="thread"> <li> <cite><a href="space.jsp?uid=${thread.authorid}" target="_blank">${thread.author}</a>: </cite><a href="viewthread.jsp?tid=${thread.tid}" target="_blank">${thread.subject}</a></li></c:forEach></ul>
		</td></c:if>
	</tr></table>
</c:if>
<c:if test="${pmlists!=null}"><div class="maintable" id="pmprompt"><jsp:include flush="true" page="pmprompt.jsp" /></div></c:if>
<c:if test="${subforums!=null}"><jsp:include flush="true" page="forumdisplay_subforum.jsp" /></c:if>
<div id="ad_text"></div>
<div class="pages_btns">
	${multi.multipage}
	<span class="pageback" <c:if test="${requestScope.visitedforums!=null}"> id="visitedforums" onmouseover="$('visitedforums').id = 'visitedforumstmp';this.id = 'visitedforums';showMenu(this.id)" </c:if>><a href="${settings.indexname}" title="<bean:message key="return_home" />"><bean:message key="return_home" /></a></span>
	<c:if test="${allowpost||jsprun_uid==0}"><span class="postbtn" id="newspecial" onmouseover="$('newspecial').id = 'newspecialtmp';this.id = 'newspecial';showMenu(this.id)"><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}" title="<bean:message key="post_new" />"><img src="${styles.IMGDIR}/newtopic_${sessionScope["org.apache.struts.action.LOCALE"]}.gif" alt="<bean:message key="post_new" />" /></a></span></c:if>
</div>
<c:if test="${allowposttrade||allowpostpoll||allowpostreward||allowpostactivity||allowpostdebate||allowpostvideo||forum.threadtypes!=''||jsprun_uid==0}">
<ul class="popupmenu_popup newspecialmenu" id="newspecial_menu" style="display: none">
	<c:if test="${forum.allowspecialonly<=0}"><li><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}"><bean:message key="post_new" /></a></li></c:if>
	<c:if test="${allowpostpoll||jsprun_uid==0}"><li class="poll"><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}&special=1"><bean:message key="post_newthreadpoll" /></a></li></c:if>
	<c:if test="${allowposttrade||jsprun_uid==0}"><li class="trade"><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}&special=2"><bean:message key="post_newthreadtrade" /></a></li></c:if>
	<c:if test="${allowpostreward||jsprun_uid==0}"><li class="reward"><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}&special=3"><bean:message key="post_newthreadreward" /></a></li></c:if>
	<c:if test="${allowpostactivity||jsprun_uid==0}"><li class="activity"><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}&special=4"><bean:message key="post_newthreadactivity" /></a></li></c:if>
	<c:if test="${allowpostdebate||jsprun_uid==0}"><li class="debate"><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}&special=5"><bean:message key="post_newthreaddebate" /></a></li></c:if>
	<c:if test="${allowpostvideo||jsprun_uid==0}"><li class="video"><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}&special=6"><bean:message key="post_newthreadvideo" /></a></li></c:if>
	<c:if test="${threadtypes!=null&&forum.allowspecialonly<=0}">
		<c:forEach items="${threadtypes.types}" var="threadtype"><c:if test="${threadtypes.special[threadtype.key]==1&&threadtypes.show[threadtype.key]==1}"><li class="popupmenu_option"><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}&typeid=${threadtype.key}">${threadtype.value}</a></li></c:if></c:forEach>
	</c:if>
</ul>
</c:if>
<div id="headfilter"><ul class="tabs">
	<li ${filter==null? "class=current":""}><a href="forumdisplay.jsp?fid=${fid}"><bean:message key="all" /></a></li>
	<li ${filter== 'digest'?"class=current":""}><a href="forumdisplay.jsp?fid=${fid}&filter=digest"><bean:message key="digest" /></a></li>
	<c:if test="${showpoll}"><li ${filter== 'poll'?"class=current":""}><a href="forumdisplay.jsp?fid=${fid}&filter=poll"><bean:message key="thread_poll" /></a></li></c:if>
	<c:if test="${showtrade}"><li ${filter== 'trade'?"class=current":""}><a href="forumdisplay.jsp?fid=${fid}&filter=trade"><bean:message key="thread_trade" /></a></li></c:if>
	<c:if test="${showreward}"><li ${filter== 'reward'?"class=current":""}><a href="forumdisplay.jsp?fid=${fid}&filter=reward"><bean:message key="thread_reward" /></a></li></c:if>
	<c:if test="${showactivity}"><li ${filter== 'activity'?"class=current":""}><a href="forumdisplay.jsp?fid=${fid}&filter=activity"><bean:message key="thread_activity" /></a></li></c:if>
	<c:if test="${showdebate}"><li ${filter== 'debate'?"class=current":""}><a href="forumdisplay.jsp?fid=${fid}&filter=debate"><bean:message key="thread_debate" /></a></li></c:if>
	<c:if test="${showvideo}"><li ${filter== 'video'?"class=current":""}><a href="forumdisplay.jsp?fid=${fid}&filter=video"><bean:message key="thread_video" /></a></li></c:if>
</ul></div>
<c:if test="${typeid!=null&&threadtypes.special[typeid]==1}"><div style="float: right; margin-top: -24px; margin-right: 10px;"><a href="search.jsp?srchtype=threadtype&typeid=${typeid}&srchfid=${fid}" target="_blank"><bean:message key="search_threadtype" arg0="${threadtypes.types[typeid]}" /></a></div></c:if>
<div class="mainbox threadlist">
<c:if test="${threadtypes!=null&&threadtypes.listable==1}"><div class="headactions">
	<c:forEach items="${threadtypes.flat}" var="threadtype"><c:choose><c:when test="${typeid!=threadtype.key}"><a href="forumdisplay.jsp?fid=${fid}&filter=type&typeid=${threadtype.key}">${threadtype.value}</a></c:when><c:otherwise><strong>${threadtype.value}</strong></c:otherwise></c:choose></c:forEach>
	<c:if test="${threadtypes.selectbox!=null}">
		<span id="threadtypesmenu" class="dropmenu" onmouseover="showMenu(this.id)"><bean:message key="admin_type_more" /></span>
		<div class="popupmenu_popup" id="threadtypesmenu_menu" style="display: none"><ul><c:forEach items="${threadtypes.selectbox}" var="threadtype"><li><c:choose><c:when test="${typeid!=threadtype.key}"><a href="forumdisplay.jsp?fid=${fid}&filter=type&typeid=${threadtype.key}&sid=${sid}">${threadtype.value}</a></c:when><c:otherwise><strong>${threadtype.value}</strong></c:otherwise></c:choose></li></c:forEach></ul></div>
	</c:if>
</div></c:if>
<h1><a href="forumdisplay.jsp?fid=${forum.fid}" class="bold">${forum.name}</a></h1>
<form method="post" name="moderate" action="topicadmin.jsp?action=moderate&fid=${forum.fid}">
<input type="hidden" name="fromWhere" value="forumdisplay" />
<input type="hidden" name="pageInfo" value="${url}&page=${page}"/>
<table summary="forum_${forum.fid}" id="forum_${forum.fid}" cellspacing="0" cellpadding="0">
<thead class="category"><tr><th colspan="3" class="caption"><bean:message key="subject" /></th><td class="author"><bean:message key="author" /></td><td class="nums"><bean:message key="reply_see" /></td><td class="lastpost"><bean:message key="a_post_threads_lastpost" /></td></tr></thead>
<c:if test="${page==1&&announcement.id!=null}"><tr><td class="folder"><img src="${styles.IMGDIR}/ann_icon.gif" alt="announcement" /></td><td class="icon">&nbsp;</td><th><b><bean:message key="index_announcements" />:&nbsp;</b><c:choose><c:when test="${announcement.type==0}"><a href="announcement.jsp?id=${announcement.id}#${announcement.id}" target="_blank">${announcement.subject}</a></c:when><c:otherwise><a href="${announcement.message}" target="_blank">${announcement.subject}</a></c:otherwise></c:choose><td class="author"><cite><a href="space.jsp?action=viewpro&username=<jrun:encoding value="${announcement.author}"/>">${announcement.author}</a></cite><em>${announcement.starttime}</em></td><td class="nums">-</td><td class="lastpost">-</td></tr></c:if>
<c:choose><c:when test="${threadlists!=null}"><c:forEach items="${threadlists}" var="thread" varStatus="index"><c:if test="${separatepos==index.count}"></table><table summary="forum_${fid}" id="forum_${fid}" cellspacing="0" cellpadding="0"><thead class="separation"><tr><td>&nbsp;</td><td>&nbsp;</td><td colspan="4"><bean:message key="forum_normal_threads" /></td></tr></thead></c:if>
<tr id="${thread.id}" ${thread.displaystyle}>
<td class="folder"><a href="viewthread.jsp?tid=${thread.tid}&amp;extra=${extra}" title="<bean:message key="target_blank" />" target="_blank"><img src="${styles.IMGDIR}/folder_${thread.folder}.gif" /></a></td>
<td class="icon"><c:choose><c:when test="${thread.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll" />" /></c:when><c:when test="${thread.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade" />" /></c:when><c:when test="${thread.special==3}"><c:choose><c:when test="${thread.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward" />" /></c:when><c:when test="${thread.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend" />" /></c:when></c:choose></c:when><c:when test="${thread.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity" />" /></c:when><c:when test="${thread.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate" />" /></c:when><c:when test="${thread.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video" />" /></c:when><c:otherwise><c:choose><c:when test="${icons[thread.iconid]!=null}"><img src="images/icons/${icons[thread.iconid]}" alt="Icon${thread.iconid}" class="icon" /></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></c:otherwise></c:choose></td>
<th class="${thread.folder}" <c:if test="${ismoderator}"> ondblclick="ajaxget('modcp.jsp?action=editsubject&tid=${thread.tid}&fid=${thread.fid}', 'thread_${thread.tid}', 'specialposts');doane(event);"</c:if>>
	<label><c:choose><c:when test="${thread.rate>0}"><img src="${styles.IMGDIR}/agree.gif"/></c:when><c:when test="${thread.rate<0}"><img src="${styles.IMGDIR}/disagree.gif"/></c:when></c:choose><c:if test="${thread.displayorder==1||thread.displayorder==2||thread.displayorder==3}"><img src="${styles.IMGDIR}/pin_${thread.displayorder}.gif" alt="${threadsticky[3-(thread.displayorder)]}" /></c:if><c:if test="${thread.digest > 0}"><img src="${styles.IMGDIR}/digest_${thread.digest}.gif" alt="<bean:message key="digest" /> ${thread.digest}" /></c:if></label>
	<c:if test="${ismoderator}"><c:choose><c:when test="${thread.fid==fid&&thread.digest>=0}"><input class="checkbox" type="checkbox" name="moderates" value="${thread.tid}" /></c:when><c:otherwise><input class="checkbox" type="checkbox" disabled="disabled" /></c:otherwise></c:choose></c:if>
	<c:if test="${thread.moved>0}"><c:choose><c:when test="${ismoderator}"><a href="topicadmin.jsp?operation=deleteMirrorImage&moderates=${thread.moved}&fid=${forum.fid}&fromWhere=${forumdisplayadd}&threadpage=${page}"><bean:message key="MOV" />:</a></c:when><c:otherwise><bean:message key="MOV" />:</c:otherwise></c:choose></c:if>
	${thread.type} <span id="thread_${thread.tid}"><a href="viewthread.jsp?tid=${thread.tid}&amp;extra=${extra}"${thread.highlight}>${thread.subject}</a></span>
	<c:if test="${thread.readperm>0}"> - [<bean:message key="threads_readperm" /> <span class="bold">${thread.readperm}</span>]</c:if> <c:choose><c:when test="${thread.price>0}"><c:choose><c:when test="${thread.special == 3}">- [<bean:message key="thread_reward" /> </c:when><c:otherwise>- [<bean:message key="magics_price" /> </c:otherwise></c:choose>${extcredits[creditstrans].title} <span class="bold">${thread.price}</span> ${extcredits[creditstrans].unit}]</c:when><c:when test="${thread.special==3&&thread.price<0}">- [<bean:message key="a_system_js_threads_special_reward_1" />]</c:when></c:choose> <c:if test="${thread.attachment>0}"><img src="images/attachicons/common.gif" alt="<bean:message key="attachment" />" class="attach" /></c:if> <c:if test="${!empty thread.multipage}"><span class="threadpages">${thread.multipage}</span></c:if> <c:if test="${thread['new']==1}"><a href="redirect.jsp?tid=${thread.tid}&goto=newpost#newpost" class="new">New</a></c:if>
</th>
<td class="author"><cite><c:choose><c:when test="${thread.authorid>0&&thread.author!=''}"><a href="space.jsp?action=viewpro&uid=${thread.authorid}">${thread.author}</a></c:when><c:otherwise><c:choose><c:when test="${ismoderator}"><a href="space.jsp?action=viewpro&uid=${thread.authorid}"><bean:message key="anonymous" /></a></c:when><c:otherwise><bean:message key="anonymous" /></c:otherwise></c:choose></c:otherwise></c:choose></cite><em>${thread.dateline}</em></td><td class="nums"><strong>${thread.replies}</strong> / <em>${thread.views}</em></td><td class="lastpost"><em><a href="redirect.jsp?tid=${thread.tid}&goto=lastpost#lastpost">${thread.lastpost}</a></em><cite>by <c:choose><c:when test="${thread.lastposter!=''}"><a href="space.jsp?action=viewpro&username=<jrun:encoding value="${thread.lastposter}"/>">${thread.lastposter}</a></c:when><c:otherwise><bean:message key="anonymous" /></c:otherwise></c:choose></cite></td>
</tr></c:forEach></c:when>
<c:otherwise><tbody><tr><th colspan="6"><bean:message key="forum_nothreads" /></th></tr></tbody></c:otherwise>
</c:choose>
</table>
<c:if test="${ismoderator&&threadlists!=null}"><div class="footoperation">
<input type="hidden" name="operation" />
<label><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form, 'moderate')" /> <bean:message key="select_all" /></label>
<c:if test="${usergroups.allowdelpost>0}"><button onclick="modthreads('delete')"><bean:message key="admin_delthread" /></button></c:if> <button onclick="modthreads('move')"><bean:message key="admin_move" /></button> <button onclick="modthreads('highlight')"><bean:message key="admin_highlight" /></button> <c:if test="${forum.threadtypes!=null&&forum.threadtypes!=''}"> <button onclick="modthreads('type')"><bean:message key="menu_forum_threadtypes" /></button></c:if> <button onclick="modthreads('close')"><bean:message key="admin_openclose" /></button> <button onclick="modthreads('bump')"><bean:message key="admin_bump_down" /></button><c:if test="${usergroups.allowstickthread>0}"> <button onclick="modthreads('stick')"><bean:message key="admin_stick_unstick" /></button></c:if> <button onclick="modthreads('digest')"><bean:message key="admin_digest_addremove" /></button><c:if test="${modrecommend.open>0&&modrecommend.sort!=1}"> <button type="button" onclick="modthreads('recommend')"><bean:message key="REC" /></button></c:if>
<script type="text/javascript">
function modthreads(operation) {
	document.moderate.operation.value = operation;
	document.moderate.submit();
}
</script>
</div></c:if>
</form>
</div>
<div class="pages_btns">
	<c:if test="${allowpost||jsprun_uid==0}"><span class="postbtn" id="newspecial" onmouseover="$('newspecial').id = 'newspecialtmp';this.id = 'newspecial';showMenu(this.id)"><a href="post.jsp?action=newthread&fid=${fid}&extra=${extra}" title="<bean:message key="post_new" />"><img src="${styles.IMGDIR}/newtopic_${sessionScope["org.apache.struts.action.LOCALE"]}.gif" alt="<bean:message key="post_new" />" /></a></span></c:if>
	${multi.multipage}
	<span class="pageback" <c:if test="${requestScope.visitedforums!=null}"> id="visitedforums" onmouseover="$('visitedforums').id = 'visitedforumstmp';this.id = 'visitedforums';showMenu(this.id)" </c:if>><a href="${settings.indexname}" title="<bean:message key="return_home" />"><bean:message key="return_home" /></a></span>
</div>
<c:if test="${settings.fastpost>0&&allowpost}">
<script type="text/javascript">
lang['last_page'] = '<bean:message key="last_page"/>';
lang['next_page'] = '<bean:message key="next_page"/>';
</script>
<script src="include/javascript/post.js" type="text/javascript"></script>
<script type="text/javascript">
var postminchars = parseInt('10');
var postmaxchars = parseInt('10000');
var disablepostctrl = parseInt('1');
var typerequired = parseInt('');
function validate(theform) {
	if (theform.typeid && theform.typeid.options[theform.typeid.selectedIndex].value == 0 && typerequired) {
		alert("<bean:message key="post_type_isnull_forum" />");
		theform.typeid.focus();
		return false;
	} else if (theform.subject.value == "" || theform.message.value == "") {
		alert("<bean:message key="post_subject_and_message_isnull" />");
		theform.subject.focus();
		return false;
	} else if (theform.subject.value.length > 80) {
		alert("<bean:message key="post_subject_toolong" />");
		theform.subject.focus();
		return false;
	}
	if (!disablepostctrl && ((postminchars != 0 && theform.message.value.length < postminchars) || (postmaxchars != 0 && theform.message.value.length > postmaxchars))) {
		alert("<bean:message key="post_message_length_invalid_info" arg0="theform.message.value.length"  arg1="postminchars" arg2="postmaxchars" />");
		return false;
	}
	if(!fetchCheckbox('parseurloff')) {
		theform.message.value = parseurl(theform.message.value, 'bbcode');
	}
	theform.topicsubmit.disabled = true;
	return true;
}
</script>
<form method="post" id="postform" action="post.jsp?action=newthread&fid=${fid}&extra=${extra}&topicsubmit=yes&formHash=${jrun:formHash(pageContext.request)}" onSubmit="return validate(this)" enctype="multipart/form-data">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div id="quickpost" class="box">
		<span class="headactions"><a href="member.jsp?action=credits&view=forum_post&fid=${fid}" target="_blank"><bean:message key="credits_policy_view" /></a></span>
		<h4><bean:message key="post_fastpost" /></h4>
		<div class="postoptions">
			<h5><bean:message key="options" /></h5>
			<p><label><input class="checkbox" type="checkbox" name="parseurloff" id="parseurloff" value="1" /> <bean:message key="disable_url" /></label></p>
			<p><label><input class="checkbox" type="checkbox" name="smileyoff" id="smileyoff" value="1" /> <bean:message key="disable" /></label><a href="faq.jsp?action=message&id=${faqs.smilies.id}" target="_blank"> ${faqs.smilies.keyword}</a></p>
			<p><label><input class="checkbox" type="checkbox" name="bbcodeoff" id="bbcodeoff" value="1" /> <bean:message key="disable" /></label><a href="faq.jsp?action=message&id=${faqs.JspRuncode.id}" target="_blank"> ${faqs.JspRuncode.keyword}</a></p>
			<c:if test="${usergroups.allowanonymous>0||forum.allowanonymous>0}"><p><label><input class="checkbox" type="checkbox" name="isanonymous" value="1" /> <bean:message key="post_anonymous" /></label></p></c:if>
			<p><label><input class="checkbox" type="checkbox" name="usesig" value="1" ${user!=null&&user.sigstatus==1? "checked=checked ":""} /> <bean:message key="post_show_sig" /></label></p>
			<p><label><input class="checkbox" type="checkbox" name="emailnotify" value="1" /> <bean:message key="post_email_notify" /></label></p>
			<c:if test="${usergroups.allowuseblog>0&&forum.allowshare>0}"><p><label><input class="checkbox" type="checkbox" name="addtoblog" value="1" /> <bean:message key="ABL" /></label></p></c:if>
		</div>
		<div class="postform">
			<h5>
				<label for="subject"><bean:message key="subject" /></label>
				<c:if test="${threadtypes.types!=null}">
					<select name="typeid" onchange="if(this.options[this.selectedIndex].className) {this.form.action=this.form.action + '&previewpost=yes&showpreview=no';this.form.submit();}">
						<option value="0">&nbsp;</option>
						<c:forEach items="${threadtypes.types}" var="threadtype"><option value="${threadtype.key}" ${threadtypes.special[threadtype.key]==1? "class=special":""}>${threadtype.value}</option></c:forEach>
					</select>
				</c:if>
				<span id="threadtypeswait"></span>
				<input type="text" id="subject" name="subject" tabindex="1" />
			</h5>
			<div id="threadtypes"></div>
			<p>
				<jsp:include flush="true" page="seditor.jsp"></jsp:include>
				<div><textarea rows="7" cols="80" class="autosave" name="message" id="fastpostmessage" onkeydown="ctlent(event);savePos(this)" onkeyup="savePos(this)" onmousedown="savePos(this)" onmouseup="savePos(this)" onfocus="savePos(this)"  tabindex="2" style="width:596px;"></textarea></div>
			</p>
			<c:if test="${allowpostattach}">
				<p><div><table class="box" cellspacing="0" cellpadding="0" style="width:600px;">
					<thead><tr><c:if test="${usergroups.allowsetattachperm>0}"><td class="nums"><bean:message key="threads_readperm" /></td></c:if><td>&nbsp;<bean:message key="description" /></td><td><bean:message key="access_postattach" /></td></tr></thead>
					<tbody id="attachbodyhidden" style="display:none"><tr>
						<c:if test="${usergroups.allowsetattachperm>0}"><td class="nums"><input type="text" name="attachperm[]" value="0" size="1" /></td></c:if>
						<td>&nbsp;<input type="text" name="attachdesc[]" size="15" /></td>
						<td><input type="file" name="attach" /> <span id="localfile[]"></span> <input type="hidden" name="localid[]" /></td>
					</tr></tbody>
					<tbody id="attachbody"></tbody>
					<tr><td colspan="3" style="border-bottom: none;"><bean:message key="attachment_size" />: <strong><c:choose><c:when test="${usergroups.maxattachsize>0}"> <bean:message key="lower_than"/> ${usergroups.maxattachsize/1000} kb </c:when><c:otherwise><bean:message key="size_no_limit" /></c:otherwise></c:choose><c:if test=""></c:if></strong>&nbsp;&nbsp;<c:if test="${usergroups.attachextensions!=''}"><bean:message key="attachment_allow_exts" />: <strong>${usergroups.attachextensions}</strong><br /></c:if></td></tr>
				</table></div></p>
			</c:if>
			<p class="btns"><button type="submit" name="topicsubmit" id="postsubmit" tabindex="3"><bean:message key="post_topicsubmit" /></button><bean:message key="post_submit_hotkey" />&nbsp;<a href="###" id="previewpost" onclick="$('postform').action=$('postform').action + '&previewpost=yes&subject='+$('subject').value+'&message='+$('fastpostmessage').value;$('postform').submit();"><bean:message key="post_previewpost" /></a>&nbsp;<a href="###" id="restoredata" title="<bean:message key="post_autosave_last_restore" />" onclick="loadData()"><bean:message key="post_autosave_restore" /></a>&nbsp;<a href="###" onclick="$('postform').reset()"><bean:message key="post_topicreset" /></a></p>
		</div>
		<script type="text/javascript">
			var textobj = $('fastpostmessage');
			window.onbeforeunload = function () {saveData(textobj.value)};
			if(is_ie >= 5 || is_moz >= 2) {
				lang['post_autosave_none'] = "<bean:message key="post_autosave_none" />";
				lang['post_autosave_confirm'] = "<bean:message key="post_autosave_confirm" />";
			} else {
				$('restoredata').style.display = 'none';
			}
		</script>
	</div>
</form>
</c:if>
<c:if test="${whosonlinestatus}"><div class="box">
	<c:choose>
		<c:when test="${detailstatus}"><span class="headactions"><a href="forumdisplay.jsp?fid=${fid}&page=${page}&showoldetails=no#online"><img src="${styles.IMGDIR}/collapsed_no.gif" alt="" /></a></span><h4><bean:message key="forum_activeusers" /></h4><ul class="userlist"><c:forEach items="${whosonline}" var="online"><li title="<bean:message key="time" />: ${online.lastactivity}<%="\n" %> <bean:message key="operation" />: ${online.action}<%="\n" %> <bean:message key="forum_name" />: ${forumname}"><img src="images/common/${online.icon}" alt="" />  <c:choose><c:when test="${online.uid>0}"><a href="space.jsp?uid=${online.uid}">${online.username}</a></c:when><c:otherwise>${online.username}</c:otherwise></c:choose></li></c:forEach></ul></c:when>
		<c:otherwise><span class="headactions"><a href="forumdisplay.jsp?fid=${fid}&page=${page}&showoldetails=yes#online" class="nobdr"><img src="${styles.IMGDIR}/collapsed_yes.gif" alt="" /></a></span><h4><bean:message key="forum_activeusers" /></h4></c:otherwise>
	</c:choose>
</div></c:if>
<div id="footfilter" class="box">
<form method="get" action="forumdisplay.jsp">
	<input type="hidden" name="fid" value="${fid}" />
	<c:choose><c:when test="${filter == 'digest'||filter == 'type'}"><input type="hidden" name="filter" value="${filter}" /><input type="hidden" name="typeid" value="${typeid}" /></c:when><c:otherwise><bean:message key="view" /> <select name="filter"><option value="0" ${checked["0"]}><bean:message key="threads_all" /></option><option value="86400" ${checked["86400"]}><bean:message key="last_1_days" /></option><option value="172800" ${checked["172800"]}><bean:message key="last_2_days" /></option><option value="604800" ${checked["604800"]}><bean:message key="last_7_days" /></option><option value="2592000" ${checked["2592000"]}><bean:message key="last_30_days" /></option><option value="7948800" ${checked["7948800"]}><bean:message key="last_90_days" /></option><option value="15897600" ${checked["15897600"]}><bean:message key="last_180_days" /></option><option value="31536000" ${checked["31536000"]}><bean:message key="last_356_days" /></option></select></c:otherwise></c:choose>
	<bean:message key="a_other_adv_orderby" /> <select name="orderby"><option value="lastpost" ${checked["lastpost"]}><bean:message key="a_forum_edit_lastpost" /></option><option value="dateline" ${checked["dateline"]}><bean:message key="a_forum_edit_starttime" /></option><option value="replies" ${checked["replies"]}><bean:message key="a_forum_edit_replies" /></option><option value="views" ${checked["views"]}><bean:message key="a_forum_edit_views" /></option></select>
	<select name="ascdesc"><option value="DESC" ${checked["DESC"]}><bean:message key="a_forum_edit_desc" /></option><option value="ASC" ${checked["ASC"]}><bean:message key="a_forum_edit_asc" /></option></select>
	&nbsp;<button type="submit"><bean:message key="submitf" /></button>
</form>
<c:if test="${settings.forumjump==1&&settings.jsmenu_1==0}"><select onchange="if(this.options[this.selectedIndex].value != '') {window.location=('forumdisplay.jsp?fid='+this.options[this.selectedIndex].value+'&sid=${sid}')}"><option value=""><bean:message key="forum_jump" /></option>${forumselect}</select></c:if>
<c:if test="${requestScope.visitedforums!=null}"><select onchange="if(this.options[this.selectedIndex].value != '') window.location=('forumdisplay.jsp?fid='+this.options[this.selectedIndex].value+'&sid=${sid}')"><option value=""><bean:message key="visited_forums" /></option><c:forEach items="${requestScope.visitedforums}" var="visitedforum"><c:if test="${visitedforum.key!=fid}"><option value="${visitedforum.key}">${visitedforum.value}</option></c:if></c:forEach></select></c:if>
</div>
<div class="legend"><label><img src="${styles.IMGDIR}/folder_new.gif" alt="<bean:message key="thread_newposts" />" /><bean:message key="thread_newposts" /></label><label><img src="${styles.IMGDIR}/folder_common.gif" alt="<bean:message key="thread_nonewpost" />" /><bean:message key="thread_nonewpost" /></label><label><img src="${styles.IMGDIR}/folder_hot.gif" alt="<bean:message key="thread_more_replies" />" /><bean:message key="thread_more_replies" /></label><label><img src="${styles.IMGDIR}/folder_lock.gif" alt="<bean:message key="admin_close" />" /><bean:message key="admin_close" /></label></div>
<c:if test="${settings.forumjump==1&&settings.jsmenu_1>0}"><div class="popupmenu_popup" id="forumlist_menu" style="display: none">${forummenu}</div></c:if>
<script type="text/javascript">
var maxpage = ${multi.maxpage>0 ? multi.maxpage : 1};
if(maxpage > 1) {
	document.onkeyup = function(e){
	e = e ? e : window.event;
	var tagname = is_ie ? e.srcElement.tagName : e.target.tagName;
	if(tagname == 'INPUT' || tagname == 'TEXTAREA') return;
	actualCode = e.keyCode ? e.keyCode : e.charCode;
	if(${page<multi.maxpage}){
		if(actualCode == 39) {window.location = 'forumdisplay.jsp?fid=${fid}${forumdisplayadd}&page=${page+1}';}
	}
	if(${page>1}){
		if(actualCode == 37) {window.location = 'forumdisplay.jsp?fid=${fid}${forumdisplayadd}&page=${page-1}';}
	}
	}
}
</script>
<c:if test="${settings.fastpost>0&&allowpost && allowpostattach}">
<script type="text/javascript">
lang['post_attachment_ext_notallowed']	= '<bean:message key="post_attachment_ext_notallowed" />';
lang['post_attachment_img_invalid']	= '<bean:message key="post_attachment_img_invalid" />';
lang['post_attachment_deletelink']	= '<bean:message key="delete" />';
lang['post_attachment_insert']		= '<bean:message key="post_attachment_insert" />';
lang['post_attachment_insertlink']	= '<bean:message key="post_attachment_insertlink" />';
var extensions = '${usergroups.attachextensions}';
</script>
<script src="include/javascript/postfast_attach.js" type="text/javascript"></script>
</c:if>
<jsp:include flush="true" page="footer.jsp" />