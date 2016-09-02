<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}" ${settings.forumjump==1&&settings.jsmenu_1>0?"class=dropmenu onmouseover=showMenu(this.id)":""}>${settings.bbname}</a> &raquo;
	<c:if test="${valueObject.beingSupperForum }">
	<a href="forumdisplay.jsp?fid=${valueObject.supperFid }">${valueObject.supperForumName }</a> &raquo;
	</c:if>
	<a href="forumdisplay.jsp?fid=${valueObject.fid}">${valueObject.forumName}
	</a> 
	&raquo;
	<a href="viewthread.jsp?tid=${valueObject.topicId}">${valueObject.topicName}</a> 
	&raquo;
	<bean:message key="admin_delposts" />
</div>
<form method="post"
	action="topicadmin.jsp?action=delpostOperating"
	id="postform">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="tid" value="${valueObject.topicId}" />
	<input type="hidden" name="currentPage" value="${valueObject.currentPage }">
	<input type="hidden" name="threadPage" value="${valueObject.threadPage }">
	<c:forEach items="${valueObject.postsIdList}" var="postsId">
		<input type="hidden" name="postIdArray" value="${postsId}"/>
	</c:forEach>
	<div class="mainbox formbox">
		<h1>
			<bean:message key="admin_delposts" />
		</h1>
		<table summary="Operating" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th>
						<bean:message key="username" />
					</th>
					<td>
						${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout" /></a>]
					</td>
				</tr>
			</thead>
			
			<jsp:include flush="true" page="topicadmin_reason.jsp" />
			<tr class="btns">
				<th>
					&nbsp;
				</th>
				<td>
					<button type="submit" name="delpsubmit" id="postsubmit" value="true">
					${valueObject.sbuttonInfo}
					</button>
					<bean:message key="post_submit_hotkey" />
				</td>
			</tr>
		</table>
	</div>
</form>
<jsp:directive.include file="footer.jsp" />