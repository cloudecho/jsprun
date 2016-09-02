<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<h2><bean:message key="menu_magic" /></h2>
	<ul><li ${magicaction== "shop"?"class=current":""}><a href="magic.jsp?action=shop"><bean:message key="magics_shop" /></a></li>
		<c:if test="${valueObject.openmarket}"><li ${magicaction== "market"?"class=current":""}><a href="magic.jsp?action=market"><bean:message key="menu_magic_market" /></a></li></c:if>
		<li ${magicaction== "user"?"class=current":""}><a href="magic.jsp?action=user"><bean:message key="magics_user" /></a></li>
		<c:if test="${settings.ec_ratio>0}"><li><a href="memcp.jsp?action=credits&operation=addfunds" target="_blank"><bean:message key="credit_payment" /></a></li></c:if>
	</ul>
	<h2><bean:message key="magics_log" /></h2>
	<ul>
		<li ${magicaction== "magicLog"&&param.operation== "useLog"?"class=current":""}><a href="magic.jsp?action=magicLog&operation=useLog"><bean:message key="magics_log_use" /></a></li>
		<li ${magicaction== "magicLog"&&param.operation== "buyLog"?"class=current":""}><a href="magic.jsp?action=magicLog&operation=buyLog"><bean:message key="magics_log_buy" /></a></li>
		<li ${magicaction== "magicLog"&&param.operation=="giveLog"?"class=current":""}><a href="magic.jsp?action=magicLog&operation=giveLog"><bean:message key="magics_log_present" /></a></li>
		<li ${magicaction== "magicLog"&&param.operation=="receiveLog"?"class=current":""}><a href="magic.jsp?action=magicLog&operation=receiveLog"><bean:message key="magics_log_receive" /></a></li>
		<li ${magicaction== "magicLog"&&param.operation=="marketLog"?"class=current":""}><a href="magic.jsp?action=magicLog&operation=marketLog"><bean:message key="magics_log_market" /></a></li>
	</ul>
	<c:if test="${valueObject.agio!=0||valueObject.allowMagicWeigth>0}">
	<h2><bean:message key="magics_information" /></h2>
	<ul>
		<c:if test="${valueObject.agio!=0}"><li><bean:message key="magics_discount" />: ${valueObject.agio} <bean:message key="discount" /></li></c:if>
		<c:if test="${valueObject.allowMagicWeigth>0}"><li><bean:message key="magics_capacity" />: ${valueObject.magicWeigthNow }/${valueObject.allowMagicWeigth }</li></c:if>
	</ul>
	</c:if>
</div>
<div class="credits_info">
	<h2><bean:message key="memcp_credits_info" /></h2>
	<ul><li><bean:message key="credits" />: ${valueObject.scoring }</li><c:forEach items="${valueObject.otherScoringList }" var="otherScoring"><li>${otherScoring.name }: <c:choose><c:when test="${otherScoring.business}"><span style="font-weight: bold;">${otherScoring.value }</span></c:when><c:otherwise>${otherScoring.value }</c:otherwise></c:choose> ${otherScoring.nuit }</li></c:forEach></ul>
</div>