<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="foruminfo">
	<div id="headsearch">
		<p>
			<a href="my.jsp?item=favorites&fid=${fid}&formHash=${jrun:formHash(pageContext.request)}" id="ajax_favorite" onclick="ajaxmenu(event, this.id)"><bean:message key="forum_favorite" /></a> | <a href="my.jsp?item=threads&srchfid=${fid}"><bean:message key="show_mytopics" /></a> | <a href="forumdisplay.jsp?fid=${fid}&filter=digest"><bean:message key="digest" /></a>
			<c:if test="${usergroups.allowmodpost>0&&forum.modnewposts>0}">| <a href="admincp.jsp?action=modthreads&frames=yes" target="_blank"><bean:message key="menu_post_modthreads" /></a></c:if>
			<c:if test="${forum.modnewposts== 2}">| <a href="admincp.jsp?action=modreplies&frames=yes" target="_blank"><bean:message key="menu_post_modreplies" /></a></c:if> <c:if test="${usergroups.admingid==1&&forum.recyclebin>0}">| <a href="admincp.jsp?action=recyclebin&frames=yes" target="_blank"><bean:message key="forum_recyclebin" /></a></c:if>
			<c:if test="${settings.rssstatus>0}"><a href="rss.jsp?fid=${fid}&auth=yDv6OqFOBicRJwSrmAEKd5BAGGE" target="_blank"><img src="images/common/xml.gif" border="0" class="absmiddle" alt="<bean:message key="rss_subscribe_all" />" /></a></c:if>
		</p>
	</div>
	<div id="nav">
		<p><a id="forumlist" href="${settings.indexname}" ${settings.forumjump ==1 && settings.jsmenu_1 > 0 ? "class=dropmenu onmouseover=showMenu(this.id)":""}>${settings.bbname}</a> ${navigation}</p>
		<c:if test="${forum.description!=''}"><p><bean:message key="forum_introduce" />: ${forum.description}</p></c:if><p><bean:message key="usergroups_system_3" />: <c:choose><c:when test="${moderatedby!=''}">${moderatedby}</c:when><c:otherwise><bean:message key="forum_opening" /></c:otherwise></c:choose></p>
	</div>
</div>
<c:if test="${forum.rules!=''||recommendlist!=null}">
	<table summary="Rules and Recommend" class="portalbox" cellpadding="0" cellspacing="1">
		<tr>
			<c:if test="${forum.rules!=''}"><td><h3><bean:message key="forum_rules" /></h3>${forum.rules}</td></c:if>
			<c:if test="${recommendlist!=null}">
				<td id="recommendlist">
					<h3><bean:message key="a_forum_copy_option_modrecommend" /><c:if test="${ismoderator&&modrecommend.sort!=1}"><em>[<a href="admincp.jsp?action=forumrecommend&fid=${fid}&frames=yes" target="_blank"><bean:message key="forum_recommend_admin" /></a>]</em></c:if></h3>
					<ul><c:forEach items="${recommendlist}" var="thread"><li><cite><a href="space.jsp?uid=${thread.authorid}" target="_blank">${thread.author}</a>:</cite><a href="viewthread.jsp?tid=${thread.tid}" target="_blank">${thread.subject}</a></li></c:forEach></ul>
				</td>
			</c:if>
		</tr>
	</table>
</c:if>
<c:if test="${newpmexists!=null||announcepm>0}"><div class="maintable" id="pmprompt"><jsp:include flush="true" page="pmprompt.jsp" /></div></c:if>
<div id="ad_text"></div>
<c:if test="${subforums!=null}"><jsp:include flush="true" page="forumdisplay_subforum.jsp" /></c:if>
<div class="legend"><label><img src="${styles.IMGDIR}/forum_new.gif" alt="<bean:message key="forum_newposts" />" /><bean:message key="forum_newposts" /></label><label><img src="${styles.IMGDIR}/forum.gif" alt="<bean:message key="forum_nonewpost" />" /><bean:message key="forum_nonewpost" /></label></div>
<jsp:include flush="true" page="footer.jsp" />