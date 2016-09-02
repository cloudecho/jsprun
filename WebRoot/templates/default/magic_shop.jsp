<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="magics_shop" /></div>
<div class="container">
<div class="side"><jsp:include flush="true" page="magic_navbar.jsp" /></div>
<div class="content">
<c:choose><c:when test="${!isBuyAnyOne}">
<div class="mainbox">
<h1><bean:message key="magics_shop" /></h1>
<ul class="tabs">
	<li class="${ current=='all'?'current':''}"><a href="magic.jsp?action=shop"><bean:message key="all" /></a></li>
	<li class="${ current=='1'?'current':''}"><a href="magic.jsp?action=shop&amp;typeid=1"><bean:message key="magics_type_1" /></a></li>
	<li class="${current=='2'?'current':''}"><a href="magic.jsp?action=shop&amp;typeid=2"><bean:message key="magics_type_2" /></a></li>
	<li class="${ current=='3'?'current':''}"><a href="magic.jsp?action=shop&amp;typeid=3"><bean:message key="magics_type_3" /></a></li>
</ul>
<table summary="<bean:message key="magics_shop" />" cellspacing="0" cellpadding="0"><tr><c:forEach items="${magic_shopVOList}" var="magic_shopVO" varStatus="count">
	<td width="50%" class="attriblist"><dl>
		<dt><img src="images/magics/${magic_shopVO.imageName }.gif" alt="${magic_shopVO.magicName }" /></dt>
		<dd class="name">${magic_shopVO.magicName }</dd><dd>${magic_shopVO.magicInfo }</dd>
		<dd><bean:message key="magics_price" />: <b>${magic_shopVO.price }</b> ${magic_shopVO.extcredits } <bean:message key="magics_weight" />: <b>${magic_shopVO.weight }</b> <bean:message key="magics_shop_num" />: <b>${magic_shopVO.stock }</b> <bean:message key="magics_shop_salevolume" />: <b>${magic_shopVO.numOfSale }</b></dd>
		<dd><a href="magic.jsp?action=prepareShopping&amp;magicid=${magic_shopVO.id }"><bean:message key="magics_operation_buy" /></a></dd>
	</dl></td>${count.count%2==0?"</tr><tr>":""}
</c:forEach></tr></table>
</div>
</c:when><c:otherwise >
<form method="post" action="magic.jsp?action=shopping">
	<input type="hidden" name="operation" value="buy" />
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="magicid" value="${magic_shopVO.id }" />
	<input type="hidden" name="operatesubmit" value="yes" />
	<div class="mainbox">
		<h1><bean:message key="magics_shop" /></h1>
		<table cellspacing="0" cellpadding="0" width="100%" align="0">
			<tr><td rowspan="6" align="center" width="20%"><img src="images/magics/${magic_shopVO.imageName }.gif" alt="${magic_shopVO.magicName }" /><br /></td><td width="80%"><b>${magic_shopVO.magicName }</b></td></tr>
			<tr><td>${magic_shopVO.magicInfo }</td></tr>
			<tr><td><bean:message key="magics_price" />: ${magic_shopVO.price } ${magic_shopVO.extcredits } <bean:message key="magics_shop_num" />: ${magic_shopVO.stock } <bean:message key="magics_shop_salevolume" />: ${magic_shopVO.numOfSale } <bean:message key="magics_weight" />: ${magic_shopVO.weight }</td></tr>
			<tr><td><bean:message key="magics_permission" />: <font color=red> <c:choose><c:when test="${magic_shopVO.isUsable}"><bean:message key="magics_permission_yes" /></c:when><c:otherwise><bean:message key="magics_permission_no" /></c:otherwise></c:choose></font>	<br /><c:choose><c:when test="${magic_shopVO.type=='1'}"><bean:message key="magics_permission_forum" />: <c:choose><c:when test="${magic_shopVO.moduleListSize==0 }"><bean:message key="magics_no_forum" /></c:when><c:otherwise><c:forEach items="${magic_shopVO.moduleList }" var="model"><a href="forumdisplay.jsp?fid=${model.id }">${model.name }</a>&nbsp;&nbsp;</c:forEach></c:otherwise></c:choose></c:when><c:when test="${magic_shopVO.type=='2'}"><bean:message key="magics_permission_group" />: <c:choose><c:when test="${magic_shopVO.usergroupNameListSize==0 }"><bean:message key="magics_no_group" /></c:when><c:otherwise><c:forEach items="${magic_shopVO.usergroupNameList }" var="usergroupName">${usergroupName}&nbsp;&nbsp;</c:forEach></c:otherwise></c:choose></c:when></c:choose></td></tr>
			<tr><td width="10%"><bean:message key="magics_amount_buy" />: <input name="magicnum" type="text" size="5" value="1" />&nbsp; <c:if test="${allowMagics=='2'}"><input type="checkbox" value="1" name="sendToOtherUser" onclick="$('showgive').style.display = $('showgive').style.display == 'none' ? '' : 'none'; this.value = this.value == 1 ? 0 : 1; this.checked = this.value == 1 ? false : true" /> <bean:message key="magics_shop_present" /> <div id="showgive" style="display:none"><bean:message key="magics_target_present" />: <input name="tousername" type="text" size="5" /></div></c:if></td></tr>
			<tr><td><button class="submit" type="submit" name="operatesubmit" id="operatesubmit" value="true" tabindex="101"><bean:message key="magics_operation_buy" /></button></td></tr>
		</table>
	</div>
</form>
</c:otherwise></c:choose>
${multipage}
</div>
</div>
<jsp:directive.include file="footer.jsp" />