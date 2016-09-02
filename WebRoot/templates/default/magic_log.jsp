<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}">${settings.bbname}</a> &raquo;
	<c:choose>
		<c:when test="${valueObject.operation=='uselog'}"><bean:message key="magics_log_use" /></c:when>
		<c:when test="${valueObject.operation=='buylog'}"><bean:message key="magics_log_buy" /></c:when>
		<c:when test="${valueObject.operation=='givelog'}"><bean:message key="magics_log_present" /></c:when>
		<c:when test="${valueObject.operation=='receivelog'}"><bean:message key="magics_log_receive" /></c:when>
		<c:when test="${valueObject.operation=='marketlog'}"><bean:message key="magics_log_market" /></c:when>
	</c:choose>
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="magic_navbar.jsp" />
	</div>
	<div class="content">
		<c:choose>
			<c:when test="${valueObject.operation=='uselog'}">
				<div class="mainbox">
					<h1>
						<bean:message key="magics_log_use" />
					</h1>
					<table summary="<bean:message key="magics_log_use" />" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<td>
									<bean:message key="name" />
								</td>
								<td class="time">
									<bean:message key="magics_dateline_use" />
								</td>
								<td>
									<bean:message key="magics_target_use" />
								</td>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${valueObject.showUseLogList}">
							<c:forEach items="${valueObject.magicUseLogList}" var="magicUseLog">
							<tr>
								<td>
								<a href="${magicUseLog.urlOnMagicName}" target="_blank">${magicUseLog.magicName}</a>
								</td>
								<td>
								${magicUseLog.operatingTime}
								</td>
								<td>
								<c:choose>
									<c:when test="${magicUseLog.drop}">
									<bean:message key="magics_operation_drop" />
									</c:when>
									<c:otherwise>
									<a href="${magicUseLog.urlOnSeeObject}" target="_blank"><bean:message key="magics_target" /></a>
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
							</c:forEach>
							</c:when>
							<c:otherwise>
							<tr>
								<td colspan="3">
								<bean:message key="magics_log_nonexistence" />
								</td>
							</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
			</c:when>
			<c:when test="${valueObject.operation=='buylog'}">
				<div class="mainbox">
					<h1>
						<bean:message key="magics_log_buy" />
					</h1>
					<table summary="<bean:message key="magics_log_buy" />" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<td>
									<bean:message key="name" />
								</td>
								<td class="time">
									<bean:message key="magics_dateline_buy" />
								</td>
								<td class="nums">
									<bean:message key="magics_amount_buy" />
								</td>
								<td>
									<bean:message key="magics_price_buy" />
								</td>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${valueObject.showBuyLogList}">
							<c:forEach items="${valueObject.magicBuyLogList}" var="magicBuyLog">
							<tr>
							<td>
								<a href="${magicBuyLog.urlOnMagicName}"
									target="_blank">${magicBuyLog.magicName}</a>
							</td>
							<td class="time">
								${magicBuyLog.operatingTime}
							</td>
							<td class="nums">
								${magicBuyLog.magicNum}
							</td>
							<td>
								${magicBuyLog.price} ${magicBuyLog.util}
							</td>
							</tr>
							</c:forEach>
							</c:when>
							<c:otherwise>
							<tr>
								<td colspan="4">
								<bean:message key="magics_log_nonexistence" />
								</td>
							</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
			</c:when>
			<c:when test="${valueObject.operation=='givelog'||valueObject.operation=='receivelog'}">
				<div class="mainbox">
					<h1>
						<c:choose><c:when test="${valueObject.operation=='givelog'}"><bean:message key="magics_log_present" /></c:when><c:otherwise><bean:message key="magics_log_receive" /></c:otherwise></c:choose>
					</h1>
					<table summary="<c:choose><c:when test="${valueObject.operation=='givelog'}"><bean:message key="magics_log_present" /></c:when><c:otherwise><bean:message key="magics_log_receive" /></c:otherwise></c:choose>" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<td>
									<bean:message key="name" />
								</td>
								<td class="time">
								<c:choose><c:when test="${valueObject.operation=='givelog'}"><bean:message key="magics_dateline_present" /></c:when><c:otherwise><bean:message key="magics_dateline_receive" /></c:otherwise></c:choose>
								</td>
								<td class="nums">
								<c:choose><c:when test="${valueObject.operation=='givelog'}"><bean:message key="magics_amount_present" /></c:when><c:otherwise><bean:message key="magics_amount_receive" /></c:otherwise></c:choose>
								</td>
								<td class="user">
								<c:choose><c:when test="${valueObject.operation=='givelog'}"><bean:message key="magics_target_present" /></c:when><c:otherwise><bean:message key="magics_target_receive" /></c:otherwise></c:choose>
								</td>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${valueObject.showGiveOrReceiveLogList}">
							<c:forEach	items="${valueObject.magicGiveOrReceiveLogList}" var="magicGiveOrReceiveLog">
							<tr>
								<td><a href="${magicGiveOrReceiveLog.urlOnMagicName}" target="_blank">${magicGiveOrReceiveLog.magicName}</a></td>
								<td class="time">${magicGiveOrReceiveLog.operatingTime}</td>
								<td class="nums">${magicGiveOrReceiveLog.magicNum}</td>
								<td class="user"><a href="${magicGiveOrReceiveLog.urlOnUsername}" target="_blank">${magicGiveOrReceiveLog.username}</a></td>
							</tr>
							</c:forEach>
							</c:when>
							<c:otherwise>
							<tr>
							<td colspan="4">
								<bean:message key="magics_log_nonexistence" />
							</td>
							</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
			</c:when>
			<c:when test="${valueObject.operation=='marketlog'}">
				<div class="mainbox">
					<h1>
						<bean:message key="magics_log_market" />
					</h1>
					<table summary="<bean:message key="magics_log_market" />" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<td>
									<bean:message key="name" />
								</td>
								<td class="time">
									<bean:message key="magics_dateline_operation" />
								</td>
								<td class="nums">
									<bean:message key="magics_amount_operation" />
								</td>
								<td>
									<bean:message key="magics_price_operation" />
								</td>
								<td>
									<bean:message key="operation" />
								</td>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${valueObject.showMagicMarketLogList}">
								<c:forEach items="${valueObject.magicMarketLogList}" var="magicMarketLog">
								<tr>
									<td><a href="${magicMarketLog.urlOnMagicName}" target="_blank">${magicMarketLog.magicName}</a></td>
									<td class="time">${magicMarketLog.operatingTime}</td>
									<td class="nums">${magicMarketLog.magicNum}</td>
									<td>${magicMarketLog.price} ${magicMarketLog.util}</td>
									<td>
									<c:choose>
										<c:when test="${magicMarketLog.operationInfo == '4'}">
											<bean:message key="magics_operation_sell" />
										</c:when>
										<c:when test="${magicMarketLog.operationInfo == '5'}">
											<bean:message key="attachment_buy" />
										</c:when>
										<c:otherwise>
											<bean:message key="magics_operation_down" />
										</c:otherwise>
									</c:choose>
									</td>
								</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
							<tr>
							<td colspan="5">
								<bean:message key="magics_log_nonexistence" />
							</td>
							</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
			</c:when>
		</c:choose>
		${valueObject.multipage }
	</div>
</div>
<jsp:include flush="true" page="footer.jsp" />