<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}" ${settings.forumjump==1&&settings.jsmenu_1>0?"class=dropmenu onmouseover=showMenu(this.id)":""}>${settings.bbname}</a> &raquo;
	<c:if test="${valueObject.beingSupperForum }">
	<a href="forumdisplay.jsp?fid=${valueObject.supperFid }">${valueObject.supperForumName }</a> &raquo;
	</c:if>
	<a href="forumdisplay.jsp?fid=${valueObject.fid }">${valueObject.forumName }</a> &raquo;
	<a href="viewthread.jsp?tid=${valueObject.topicId }">${valueObject.topicName }</a> &raquo; <bean:message key="admin_copy" />
</div>

<form method="post" action="topicadmin.jsp?action=copyOperating" id="postform">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="fid" value="${valueObject.fid }" />
	<input type="hidden" name="referer" value="" />
	<input type="hidden" name="tid" value="${valueObject.topicId }" />

	<div class="mainbox formbox">
		<h1>
			<bean:message key="admin_copy" />
		</h1>
		<table cellspacing="0" cellpadding="0" width="100%">


			<tr>
				<th>
					<bean:message key="username" />
				</th>
				<td>
					${jsprun_userss}
					<em class="tips">[<a href="logging.jsp?action=logout&amp;formhash=${formhash}"><bean:message key="member_logout" /></a>]</em>
				</td>
			</tr>

			<tr>
				<th>
					<bean:message key="admin_copy" />
				</th>
				<td>
					<select name="copyto">
						${valueObject.selectContent}
					</select>
				</td>
			</tr>
			<jsp:include flush="true" page="topicadmin_reason.jsp" />
			<tr class="btns">
				<th>
					&nbsp;
				</th>
				<td>

					<button class="submit" type="submit" name="copysubmit" id="postsubmit" value="true">
						<bean:message key="submitf" />
					</button>
					<bean:message key="post_submit_hotkey" />
				</td>
			</tr>
		</table>
	</div>
</form>
<jsp:directive.include file="footer.jsp" />