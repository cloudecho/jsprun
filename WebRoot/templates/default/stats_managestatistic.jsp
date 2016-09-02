<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; <a href="stats.jsp"><bean:message key="stats" /></a> &raquo; <bean:message key="stats_modworks" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="stats_navbar.jsp" />
	</div>
	<div class="content">
		<div class="mainbox">

			<h1>
				<bean:message key="stats_modworks" /> - <bean:message key="stats_modworks_all" />
			</h1>
			<table width="100%" cellpadding="0" cellspacing="0">
				<thead>
					<tr align=center>
						<td width="8%">
						<c:choose><c:when test="${valueObject.showUsername}"><bean:message key="username" /></c:when><c:otherwise><bean:message key="time" /></c:otherwise></c:choose>
						</td>
						<c:forEach items="${valueObject.titleInfoList}" var="titleInfo">
						<td width="${valueObject.tdWidth }">${valueObject.titleInfoMap[titleInfo]}</td>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${valueObject.contentInfoList}" var="contentInfo">
						<tr align="center">
							<td>
							<c:choose>
								<c:when test="${valueObject.showUsername}">
										<a href="${pageContext.request.contextPath }/stats.jsp?type=modworks&before=${contentInfo.before }&amp;uid=${contentInfo.uid }"
											title="<bean:message key="stats_modworks_details" />">${contentInfo.username }</a>
								</c:when>
								<c:otherwise>
										${contentInfo.timeOfDay }
								</c:otherwise>
							</c:choose>
							</td>
								<c:forEach items="${valueObject.titleInfoList}" var="titleInfo">
										<c:choose>
											<c:when
												test="${contentInfo.columnOfNumberMapMap[titleInfo]==null||contentInfo.columnOfNumberMapMap[titleInfo].number==null||contentInfo.columnOfNumberMapMap[titleInfo].number==''||contentInfo.columnOfNumberMapMap[titleInfo].number=='0'}">
												<td>&nbsp;</td>
											</c:when>
											<c:otherwise>
												<td title="${contentInfo.columnOfNumberMapMap[titleInfo].title }"><em class="tips">${contentInfo.columnOfNumberMapMap[titleInfo].number }</em></td>
											</c:otherwise>
										</c:choose>
								</c:forEach>
						</tr>
					</c:forEach>
						<c:if test="${valueObject.showThisMonthManageStatistic}">
						<tr>
							<td><bean:message key="stats_modworks_thismonth" /></td>
								<c:forEach items="${valueObject.titleInfoList}" var="titleInfo">
										<c:choose>
											<c:when
												test="${valueObject.columnOfAllNumberMap[titleInfo]==null||valueObject.columnOfAllNumberMap[titleInfo].allNumber==null||valueObject.columnOfAllNumberMap[titleInfo].allNumber==''||valueObject.columnOfAllNumberMap[titleInfo].allNumber=='0'}">
												<td>&nbsp;</td>
											</c:when>
											<c:otherwise>
												<td title="${valueObject.columnOfAllNumberMap[titleInfo].titleAllPost }"><em class="tips">${valueObject.columnOfAllNumberMap[titleInfo].allNumber }</em></td>
											</c:otherwise>
										</c:choose>
										
								</c:forEach>
						</tr>
						</c:if>
				</tbody>
			</table>

			<table cellspacing="0" cellpadding="4" border="0" width="95%"
				align="center" class="tips">
				<tr>
					<td align="right">
						<bean:message key="stats_modworks_month" />: &nbsp;
						<c:forEach items="${valueObject.timeInfoList}" var="timeInfo">
							<c:choose>
								<c:when test="${timeInfo.nowTime}">
									<b>${timeInfo.time }</b>&nbsp; &nbsp;
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath }/stats.jsp?type=modworks&before=${timeInfo.before }&uid=${timeInfo.uid }">${timeInfo.time}</a>&nbsp;
						&nbsp;
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</td>
				</tr>
			</table>
			<br />
		</div>
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />
