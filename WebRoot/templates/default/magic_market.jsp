<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="menu_magic_market" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="magic_navbar.jsp" />
	</div>
	<div class="content">
			<div class="mainbox">
				<span class="headactions"><a
					href="magic.jsp?action=market&operation=myMagics"><bean:message key="magics_market_my" /></a> </span>
				<h1>
					<bean:message key="menu_magic_market" />
				</h1>
				<table summary="<bean:message key="menu_magic_market" />" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<td>
								<bean:message key="name" />
							</td>
							<td width="30%">
								<bean:message key="magics_function" />
							</td>
							<td class="nums">
								<bean:message key="magics_price" />
							</td>
							<td class="nums">
								<bean:message key="num" />
							</td>
							<td class="nums">
								<bean:message key="magics_single_weight" />
							</td>
							<td class="user">
								<bean:message key="magics_seller" />
							</td>
							<td>
								<bean:message key="operation" />
							</td>
						</tr>
					</thead>
					<c:choose>
						<c:when test="${valueObject.magicInfoListSize==0}">
						<tr>
							<td colspan="7">
							<bean:message key="magics_market_nonexistence" />
							</td>
						</tr>
						</c:when>
						<c:otherwise>
						<c:forEach items="${valueObject.magicInfoList}" var="magicInfo">
						<tr>
							<td>
								<a href="magic.jsp?action=prepareShopping&operation=buy&magicid=${magicInfo.magicId }" target="_blank">
								${magicInfo.magicName }
								</a>
							</td>
							<td>
								${magicInfo.magicFunction }
							</td>
							<td class="nums">
								${magicInfo.magicPrice }&nbsp;${valueObject.magicUnit }
							</td>
							<td class="nums">
								${magicInfo.magicNumber }
							</td>
							<td class="nums">
								${magicInfo.magicWeight }
							</td>
							<td class="user">
								<a href="space.jsp?&uid=${magicInfo.sellerId }" target="_blank">
								${magicInfo.sellerName }
								</a>
							</td>
							<td>
								<a href="magic.jsp?action=marketPrepareOperation&magicid=${magicInfo.magicId }&debusOrBuy=${magicInfo.debusOrBuy }&magicMarketId=${magicInfo.magicMarketId }">
								<c:choose><c:when test="${magicInfo.debusOrBuy=='buy'}"><bean:message key="magics_operation_buy" /></c:when><c:otherwise><bean:message key="magics_operation_down" /></c:otherwise></c:choose>
								</a>
							</td>
						</tr>
						</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			${multipage}<br><br>
			<c:if test="${valueObject.selectFind}">
			<form method="post" action="magic.jsp?action=market">
			<div id="footfilter" class="box">
				<select name="magicidFromSelect">
					<option value="">
						<bean:message key="name" />
					</option>
					<c:forEach items="${valueObject.magicOfDBList}" var="magicOfDB">
					<option value="${magicOfDB.magicId }" ${valueObject.selectMagicId==magicOfDB.magicId?'selected':'' }>
						${magicOfDB.magicName }
					</option>
					</c:forEach>
				</select>
				<select name="orderby">
					<option value="">
						<bean:message key="magics_market_nonexistence" />
					</option>
					<option value="price" ${valueObject.selectOrderby=='price'?'selected':''}>
						<bean:message key="magics_price" />
					</option>
					<option value="num" ${valueObject.selectOrderby=='num'?'selected':''}>
						<bean:message key="num" />
					</option>
				</select>
				<select name="ascdesc">
					<option value="">
						<bean:message key="a_other_adv_orderby" />
					</option>
					<option value="ASC" ${valueObject.selectAscdesc=='ASC'?'selected':''}>
						<bean:message key="a_forum_edit_asc" />
					</option>
					<option value="DESC" ${valueObject.selectAscdesc=='DESC'?'selected':''}>
						<bean:message key="a_forum_edit_desc" />
					</option>
				</select>
				&nbsp;
				<input type="hidden" name="operation" value="find">
				<button class="submit" type="submit" name="searchsubmit">
					<bean:message key="search" />
				</button>
			</div>
			</form>
		</c:if>
	</div>
</div>

<jsp:include flush="true" page="footer.jsp" />