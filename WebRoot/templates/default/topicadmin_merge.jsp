<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}" ${settings.forumjump==1&&settings.jsmenu_1>0?"class=dropmenu onmouseover=showMenu(this.id)":""}>${settings.bbname}</a> &raquo;
	<c:if test="${valueObject.beingSupperForum }">
	<a href="forumdisplay.jsp?fid=${valueObject.supperFid }">${valueObject.supperForumName }</a> &raquo;
	</c:if>
	<a href="forumdisplay.jsp?fid=${valueObject.fid }">${valueObject.forumName }</a> &raquo;
	<a href="viewthread.jsp?tid=${valueObject.topicId }">${valueObject.topicName }</a> &raquo; <bean:message key="admin_merge" />
</div>

<form method="post" action="topicadmin.jsp?action=mergeOperating" id="postform">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="fid" value="${valueObject.fid }" />
	<input type="hidden" name="tid" value="${valueObject.topicId }" />
	<div class="mainbox formbox">
		<h1>
			<bean:message key="admin_merge" />
		</h1>
		<table cellspacing="0" cellpadding="0" width="100%">


			<tr>
				<th>
					<bean:message key="username" />
				</th>
				<td>
					${jsprun_userss}
					<em class="tips">[<a
						href="logging.jsp?action=logout&amp;formhash=${formhash}"><bean:message key="member_logout" /></a>]</em>
				</td>
			</tr>

			<tr>
				<th>
					<bean:message key="admin_merge_tid" />
				</th>
				<td>
					<input type="text" name="othertid" size="10" />
					&nbsp;
					<em class="tips"><bean:message key="admin_merge_tid_comment" /></em>
				</td>
			</tr>
			<jsp:include flush="true" page="topicadmin_reason.jsp" />
			<tr class="btns">
				<th>
					&nbsp;
				</th>
				<td>
					<button class="submit" type="submit" name="mergesubmit"
						id="postsubmit" value="true">
						<bean:message key="admin_merge" />
					</button>
					<bean:message key="post_submit_hotkey" />
				</td>
			</tr>

		</table>
	</div>

</form>
<jsp:directive.include file="footer.jsp" />