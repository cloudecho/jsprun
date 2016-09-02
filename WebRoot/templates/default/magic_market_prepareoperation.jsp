<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.include file="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="menu_magic_market" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="magic_navbar.jsp" />
	</div>
<div class="content">
	<form method="post" action="magic.jsp?action=marketOperating">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="magicMarketId" value="${valueObject.magicMarketId }" />
		<input type="hidden" name="operation" value="${valueObject.operation }" />
		<div class="mainbox">
			<h1>
				<c:choose>
					<c:when test="${valueObject.operation == 'buy' }"><bean:message key="magics_operation_buy" /></c:when>
					<c:otherwise><bean:message key="magics_operation_down" /></c:otherwise>
				</c:choose>
			</h1>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<td class="attriblist">
						<dl>
							<dt>
								<img src="${pageContext.request.contextPath }/images/magics/${valueObject.imageName }.gif" alt="${valueObject.magicName }">
							</dt>
							<dd class="name">
							${valueObject.magicName }
							</dd>
							<dd>
								${valueObject.magicInfo }
							</dd>
							<dd>
								<bean:message key="magics_price" />: ${valueObject.magicPrice } ${valueObject.magicUtil }
							</dd>
							<dd>
								<bean:message key="magics_weight" />: ${valueObject.magicWeight }
							</dd>
							<dd>
								<bean:message key="magics_shop_num" />: ${valueObject.magicStock }
							</dd>
						</dl>
					</td>
				</tr>
			</table>
		</div>
		<div id="footfilter" class="box">
			<label>
				<c:choose>
					<c:when test="${valueObject.operation == 'buy' }"><bean:message key="magics_operation_buy" /></c:when>
					<c:otherwise><bean:message key="magics_operation_down" /></c:otherwise>
				</c:choose>
				<bean:message key="num" />:
			<input name="magicnum" type="text" size="5" value="1" />
			</label>
			&nbsp;
			<button class="submit" type="submit" >
				<c:choose>
					<c:when test="${valueObject.operation == 'buy' }"><bean:message key="magics_operation_buy" /></c:when>
					<c:otherwise><bean:message key="magics_operation_down" /></c:otherwise>
				</c:choose>
			</button>
		</div>
	</form>
</div>
</div>
<jsp:directive.include file="footer.jsp" />