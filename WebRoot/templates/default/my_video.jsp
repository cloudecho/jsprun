<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h1><bean:message key="my_video"/></h1>
<table cellspacing="0" cellpadding="0" width="100%">
	<c:choose>
		<c:when test="${videolists!=null}">
			<tr>
			<c:forEach items="${videolists}" var="video" varStatus="index">
				<td width="50%" class="attriblist">
					<dl>
						<dt><a href="viewthread.jsp?tid=${video.tid}" target="_blank"><img src="${video.vthumb}" alt="${video.vtitle}" width="124" height="94" /></a></dt>
						<dd class="name">${video.vtitle}</dd>
						<dd>{lang dateline}: ${video.dateline}</dd>
						<dd><bean:message key="video_views"/>: ${video.vview}</dd>
						<c:if test="${video.vtime>0}"><dd><bean:message key="video_time"/>: ${video.vtime} <bean:message key="a_other_crons_minute"/></dd></c:if>
						<dd>{lang video_operate}: <c:if test="${video.pid>0&&video.tid>0}"><a href="viewthread.jsp?tid=${video.tid}" target="_blank">{lang video_view}</a></c:if><a href="###" onclick="setcopy('[video={${video.vautoplay}]${video.vid}[/video]', '<bean:message key="video_copy_code"/>')"><bean:message key="video_copy"/></a></dd>
					</dl>
				</td>${index.count%2==0?"</tr><tr>":""}
			</c:forEach>
			$videoendrows
		</c:when>
		<c:otherwise><tr><td colspan="5"><bean:message key="memcp_novideo"/></td></tr></c:otherwise>
	</c:choose>
</table>