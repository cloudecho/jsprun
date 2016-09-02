<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a>&raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_main" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">
			<h3>
				<bean:message key="stats_main_member" />
			</h3>
			<table summary="<bean:message key="stats_main_member" />" cellspacing="0" cellpadding="0">
				<tr>
					<th>
						<bean:message key="stats_main_members" />
					</th>
					<td>
						${valueObject.membersNum }
					</td>
					<th>
						<bean:message key="stats_main_posters" />
					</th>
					<td>
						${valueObject.membersOfPostsNum }
					</td>
				</tr>

				<tr>
					<th>
						<bean:message key="stats_main_admins" />
					</th>
					<td>
						${valueObject.memberOfManageNum }
					</td>
					<th>
						<bean:message key="stats_main_nonposters" />
					</th>
					<td>
						${valueObject.membersOfNoPostsNum }
					</td>
				</tr>

				<tr>
					<th>
						<bean:message key="stats_main_new" />
					</th>
					<td>
						${valueObject.newMemberName }
					</td>
					<th>
						<bean:message key="stats_main_posters_percent" />
					</th>
					<td>
						${valueObject.postsNum_allNum }%
					</td>
				</tr>

				<tr>
					<th>
						<bean:message key="stats_main_topposter" />
					</th>
					<td>
						<c:choose>
							<c:when test="${valueObject.beingBestMem}">
								${valueObject.bestMem}
								<em title="<bean:message key="posts" />">(${valueObject.bestMemPosts })</em>
							</c:when>
							<c:otherwise>
								${valueObject.bestMem}
							</c:otherwise>
						</c:choose>
						
					</td>
					<th>
						<bean:message key="stats_main_posts_avg" />
					</th>
					<td>
						${valueObject.avg_everyBodyPost }
					</td>
				</tr>

			</table>
		</div>
		<div class="mainbox">
			<h3>
				<bean:message key="stats" />
			</h3>
			<table summary="<bean:message key="stats" />" cellspacing="0" cellpadding="0">
				<tr>
					<th>
						<bean:message key="stats_main_forums_count" />
					</th>
					<td>
						${valueObject.formsCount }
					</td>
					<th>
						<bean:message key="stats_main_nppd" />
					</th>
					<td>
						${valueObject.avg_addPostsEveryDay }
					</td>
					<th>
						<bean:message key="stats_main_hot_forum" />
					</th>
					<td>
						<a href="forumdisplay.jsp?fid=${valueObject.bestModuleID }" target="_blank">${valueObject.bestModule}</a>
					</td>
				</tr>

				<tr>
					<th>
						<bean:message key="stats_main_threads_count" />
					</th>
					<td>
						${valueObject.threadNum }
					</td>
					<th>
						<bean:message key="stats_main_nmpd" />
					</th>
					<td>
						${valueObject.avg_loginEveryDay }
					</td>
					<th>
						<bean:message key="stats_main_threads_count" />
					</th>
					<td>
						${valueObject.bestModuleThreadNum }
					</td>
				</tr>

				<tr>
					<th>
						<bean:message key="stats_main_posts_count" />
					</th>
					<td>
						${valueObject.postsNum }
					</td>
					<th>
						<bean:message key="stats_main_todays_newposts" />
					</th>
					<td>
						${valueObject.addPostsInLast24 }
					</td>
					<th>
						<bean:message key="stats_main_posts_count" />
					</th>
					<td>
						${valueObject.bestModulePostsNum }
					</td>
				</tr>

				<tr>
					<th>
						<bean:message key="stats_main_rpt" />
					</th>
					<td>
						${valueObject.avg_returnPostsEyeryThread }
					</td>
					<th>
						<bean:message key="stats_main_members_count" />
					</th>
					<td>
						${valueObject.addMemberInLast24 }
					</td>
					<th>
						<bean:message key="stats_main_board_activity" />
					</th>
					<td>
						${valueObject.bestModuleActivityInfo }
					</td>
				</tr>
			</table>
		</div>
		<c:if test="${valueObject.showFluxSurvey }">
			<div class="mainbox">
				<h3>
					<bean:message key="stats_main_pageview" />
				</h3>
				<table summary="<bean:message key="stats_main_pageview" />" cellspacing="0" cellpadding="0">
					<tr>
						<th>
							<bean:message key="stats_main_total_pageview" />
						</th>
						<td>
							${valueObject.allPageFlux }
						</td>
						<th>
							<bean:message key="stats_main_maxpv_month" />
						</th>
						<td>
							${valueObject.accessMaxNum }
						</td>
					</tr>

					<tr>
						<th>
							<bean:message key="stats_main_total_visitors" />
						</th>
						<td>
							${valueObject.accesserNum }<bean:message key="stats_main_person_time" />
						</td>
						<th>
							<bean:message key="stats_main_total_pageview_month" />
						</th>
						<td>
							${valueObject.allPageFluxOfMonth }
						</td>
					</tr>

					<tr>
						<th>
							<bean:message key="members" />
						</th>
						<td>
							${valueObject.memberOfAccess }
						</td>
						<th>
							<bean:message key="stats_main_period_of_time" />
						</th>
						<td>
							${valueObject.accessTime }
						</td>
					</tr>

					<tr>
						<th>
							<bean:message key="guest" />
						</th>
						<td>
							${valueObject.visitorOfAccess }
						</td>
						<th>
							<bean:message key="stats_main_total_pageview_time" />
						</th>
						<td>
							${valueObject.accessTimeAllFlux }
						</td>
					</tr>

					<tr>
						<th>
							<bean:message key="stats_main_pv_avg" />
						</th>
						<td>
							${valueObject.avg_scanEverybody }
						</td>
						<th>
							&nbsp;
						</th>
						<td>
							&nbsp;
						</td>
					</tr>

				</table>
			</div>
		</c:if>
		<div class="mainbox">
			<h3>
				<bean:message key="stats_main_month" />
			</h3>
			<table summary="<bean:message key="stats_main_month" />" cellpadding="0" cellspacing="0">
				<c:choose>
					<c:when test="${valueObject.showFluxSurvey}">
						<c:forEach items="${valueObject.monthFlux}" var="pageInfo">
							<tr>
								<th width="100">
									<strong>${pageInfo.information }</strong>
								</th>
								<td>
									<div class="optionbar"><div style="width: ${pageInfo.lineWidth }px">&nbsp;</div></div>
									&nbsp;<strong>${pageInfo.num }</strong>(${pageInfo.numPercent }%)
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<jsp:include page="stats_subpostlog.jsp"></jsp:include>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<div class="notice">
			<bean:message key="stats_update" arg0="${valueObject.lastTime }" arg1="${valueObject.nextTime}" />
		</div>
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />