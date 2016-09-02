<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_team" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<c:if test="${valueObject.beingAdmin}">
			<div class="mainbox">
			<h1>
				<bean:message key="stats_team" /> - <bean:message key="stats_team_admins" />
			</h1>
			<table summary="<bean:message key="stats_team_admins" />" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
					<c:forEach items="${valueObject.adminTableTitleList}" var="adminTableTitle">
						<td>
							${adminTableTitle }
						</td>
					</c:forEach>
					</tr>
				</thead>
				<%
				String username_properties = (String)request.getAttribute("username_properties");
				String thisMonthManage_properties = (String)request.getAttribute("thisMonthManage_properties");
				String onlineTotal_properties = (String)request.getAttribute("onlineTotal_properties");
				String onlineThisMonth_properties = (String)request.getAttribute("onlineThisMonth_properties");
				%>
				<c:forEach items="${valueObject.manageTeamMapList}" var="manageTeamMap">
					<tr>
					<c:forEach items="${valueObject.adminTableTitleList}" var="adminTableTitle">
						<td>
							<%
							String adminTableTitle = (String)pageContext.getAttribute("adminTableTitle");
							if(username_properties.equals(adminTableTitle)){
							%>
							<a href="space.jsp?uid=${manageTeamMap[adminTableTitle].uid }">${manageTeamMap[adminTableTitle].username }</a>
							<%
							}else if(thisMonthManage_properties.equals(adminTableTitle)){
							%>
							<a href="${pageContext.request.contextPath }/stats.jsp?type=modworks&uid=${manageTeamMap[adminTableTitle].uid }">${manageTeamMap[adminTableTitle].thisMonthManage }</a>
							<%
							}else if(onlineTotal_properties.equals(adminTableTitle) || onlineThisMonth_properties.equals(adminTableTitle)){
							%>
							 ${manageTeamMap[adminTableTitle] } <bean:message key="hr" />
							<%
							}else{
							%>
							 ${manageTeamMap[adminTableTitle] }
							<%
							}
							%>
						</td>
					</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</div>
		</c:if>
		<c:forEach items="${valueObject.forumTableGroupList}" var="forumTableGroup">
		<div class="mainbox">
			<h1>
				<h3><a href="index.jsp?gid=${forumTableGroup.groupId }">${forumTableGroup.groupName }</a></h3>
			</h1>
			<table summary="${forumTableGroup.groupId }" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
					<c:forEach items="${valueObject.forumTableTitleList}" var="forumTableTitle">
						<td>
							${forumTableTitle }
						</td>
					</c:forEach>
					</tr>
				</thead>
				<c:forEach items="${forumTableGroup.forumList}" var="forum" varStatus="varstatus">
					<tr>
					<c:if test="${forum.selectFroumName}">
						<td  class="altbg1" rowspan="${forum.rowspan}"  > 
							<a href="${pageContext.request.contextPath }/${forum.uri }">${forum.froumName }</a>
						</td>
					</c:if>
						<td><a href="space.jsp?uid=${forum.uid }">${forum.username }</a></td>
						<td>
							${forum.managerName }
						</td>
						<td>
							${forum.lastAccessTime }
						</td>
						<td>
							${forum.offDays }
						</td>
						<td>
							${forum.credits }
						</td>
						<td>
							${forum.posts }
						</td>
						<td>
							${forum.thisMonthPosts }
						</td>
						<td>
							<a href="${pageContext.request.contextPath }/stats.jsp?type=modworks&uid=${forum.uid }">${forum.thisMonthManage }</a>
						</td>
						<c:if test="${forum.showOnline}">
						<td>
							${forum.allTimeOnline } <bean:message key="hr" />
						</td>
						<td>
							${forum.thisMonthTimeOnline } <bean:message key="hr" />
						</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
			</div>
		</c:forEach>
		
		<div class="notice">
			<bean:message key="stats_update" arg0="${valueObject.lastTime }" arg1="${valueObject.nextTime }" />
		</div>
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />
