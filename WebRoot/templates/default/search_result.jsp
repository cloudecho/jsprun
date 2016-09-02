<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="foruminfo">
	<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <bean:message key="search"/></div>
</div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<div class="mainbox threadlist">
	<h1><bean:message key="search"/></h1>
	<table summary="<bean:message key="search"/>" cellspacing="0" cellpadding="0">
		<thead><tr><td class="icon">&nbsp;</td><th><bean:message key="subject"/></th><td class="forum"><bean:message key="forum_name"/></td><td class="author"><bean:message key="author"/></td><td class="nums"><bean:message key="reply_see"/></td><td class="lastpost"><bean:message key="a_post_threads_lastpost"/></td></tr></thead>
		<c:choose>
			<c:when test="${threadlist!=null}">
				<c:forEach items="${threadlist}" var="thread">
					<tbody>
					<tr>
						<td class="icon">
							<c:choose>
								<c:when test="${thread.special==1}"><img src="${styles.IMGDIR}/pollsmall.gif" alt="<bean:message key="thread_poll"/>" /></c:when>
								<c:when test="${thread.special==2}"><img src="${styles.IMGDIR}/tradesmall.gif" alt="<bean:message key="thread_trade"/>" /></c:when>
								<c:when test="${thread.special==3}"><c:choose><c:when test="${thread.price>0}"><img src="${styles.IMGDIR}/rewardsmall.gif" alt="<bean:message key="thread_reward"/>" /></c:when><c:when test="${thread.price<0}"><img src="${styles.IMGDIR}/rewardsmallend.gif" alt="<bean:message key="thread_rewardend"/>" /></c:when></c:choose></c:when>
								<c:when test="${thread.special==4}"><img src="${styles.IMGDIR}/activitysmall.gif" alt="<bean:message key="thread_activity"/>" /></c:when>
								<c:when test="${thread.special==5}"><img src="${styles.IMGDIR}/debatesmall.gif" alt="<bean:message key="thread_debate"/>" /></c:when>
								<c:when test="${thread.special==6}"><img src="${styles.IMGDIR}/videosmall.gif" alt="<bean:message key="thread_video"/>" /></c:when>
								<c:otherwise><c:choose><c:when test="${icons[thread.iconid]!=null}"><img src="images/icons/${icons[thread.iconid]}" alt="Icon${thread.iconid}" class="icon" /></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></c:otherwise>
							</c:choose>
						</td>
						<th>
							<label><c:if test="${thread.displayorder==1||thread.displayorder==2||thread.displayorder==3}"><img src="${styles.IMGDIR}/pin_${thread.displayorder}.gif" alt="${threadsticky[3-(thread.displayorder)]}" /></c:if><c:if test="${thread.digest > 0}"><img src="${styles.IMGDIR}/digest_${thread.digest}.gif" alt="<bean:message key="digest"/> ${thread.digest}" /></c:if></label>
							<a href="viewthread.jsp?tid=${thread.tid}&highlight=${index.keywords}" target="_blank" ${thread.highlight}>${thread.subject}</a>
							<c:if test="${thread.attachment>0}"><img src="images/attachicons/common.gif" alt="<bean:message key="attachment"/>" /></c:if>
							<c:if test="${thread.multipage!=null&&thread.multipage!=''}"><span class="threadpages">${thread.multipage}</span></c:if>
						</th>
						<td class="forum"><a href="forumdisplay.jsp?fid=${thread.fid}">${thread.name}</a></td>
						<td class="author">
							<cite><c:choose><c:when test="${thread.authorid>0&&thread.author!=''}"><a href="space.jsp?uid=${thread.authorid}">${thread.author}</a></c:when><c:otherwise><c:choose><c:when test="${ismoderator}"><a href="space.jsp?uid=${thread.authorid}"><bean:message key="anonymous"/></a></c:when><c:otherwise><bean:message key="anonymous"/></c:otherwise></c:choose></c:otherwise></c:choose></cite>
							<em><jrun:showTime timeInt="${thread.dateline}" type="${dateformat}" replErTime="N/A"/></em>
						</td>
						<td class="nums"><strong>${thread.replies}</strong> / <em>${thread.views}</em></td>
						<td class="lastpost">
							<em><a href="redirect.jsp?tid=${thread.tid}&goto=lastpost&highlight=${index.keywords}#lastpost"><jrun:showTime timeInt="${thread.lastpost}" type="${dateformat} ${timeformat}" replErTime="N/A"/></a></em>
							<cite>by <c:choose><c:when test="${thread.lastposter!=''}"><a href="space.jsp?username=<jrun:encoding value="${thread.lastposter}"/>">${thread.lastposter}</a></c:when><c:otherwise><bean:message key="anonymous"/></c:otherwise></c:choose></cite>
						</td>
					</tr>
					</tbody>
				</c:forEach>
			</c:when>
			<c:otherwise><tr><td colspan="6"><bean:message key="search_nomatch"/></td></tr></c:otherwise>
		</c:choose>
	</table>
</div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
<jsp:include flush="true" page="footer.jsp" />