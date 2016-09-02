<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_ecommerce_creditorder" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip');return false"><bean:message key="tips" /></a></div>
			<div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip');return false"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /></a></div>
		</td>
	</tr>
	<tbody id="menu_tip" style="display: ">
		<tr>
			<td><bean:message key="a_extends_orders_tips" />
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=orders">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_extends_search" /></td></tr>
		<tr><td class="altbg1"><bean:message key="a_extends_search_status" />:</td><td class="altbg2" align="right"><select name="orderstatus"><option value=""><bean:message key="a_extends_search_status_all" /></option><option value="1" ${statusselect['1']}><bean:message key="a_extends_orders_search_status_pending" /></option><option value="2" ${statusselect['2']}><bean:message key="a_extends_orders_search_status_auto_finished" /></option><option value="3" ${statusselect['3']}><bean:message key="a_extends_orders_search_status_manual_finished" /></option></select></td></tr>
		<tr><td class="altbg1" width="45%"><bean:message key="a_extends_search_id" />:</td><td class="altbg2" align="right"><input type="text" name="orderid" size="40" value="${orderid}"></td></tr>
		<tr><td class="altbg1"><bean:message key="a_extends_search_users" /></td><td class="altbg2" align="right"><input type="text" name="users" size="40" value="${users}"></td></tr>
		<tr><td class="altbg1"><bean:message key="a_extends_search_buyer" /></td><td class="altbg2" align="right"><input type="text" name="buyer" size="40" value="${buyer}"></td></tr>
		<tr><td class="altbg1"><bean:message key="a_extends_search_admin" /></td><td class="altbg2" align="right"><input type="text" name="admin" size="40" value="${admin}"></td></tr>
		<tr><td class="altbg1"><bean:message key="a_extends_search_submit_date" /></td><td class="altbg2" align="right"><input type="text" name="sstarttime" size="10" value="${sstarttime}"> - <input type="text" name="sendtime" size="10" value="${sendtime}"></td></tr>
		<tr><td class="altbg1"><bean:message key="a_extends_search_confirm_date" /></td><td class="altbg2" align="right"><input type="text" name="cstarttime" size="10" value="${cstarttime}"> - <input type="text" name="cendtime" size="10" value="${cendtime}"></td></tr>
	</table><br />
	<center><input class="button" type="submit" name="searchsubmit" value="<bean:message key="submit" />"></center>
</form>
<c:if test="${!empty searchsubmit}">
<form method="post" action="admincp.jsp?action=orders">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form)"><bean:message key="a_extends_validate" /></td><td><bean:message key="a_extends_search_id" /></td><td><bean:message key="a_extends_status" /></td><td><bean:message key="a_extends_username" /></td><td><bean:message key="a_extends_buyer" /></td><td><bean:message key="a_extends_amount" arg0="${title}"/></td><td><bean:message key="a_extends_price" /></td><td><bean:message key="a_extends_submitdate" /></td><td><bean:message key="a_extends_confirmdate" /></td></tr>
		<c:forEach items="${orders}" var="order">
			<tr align="center" class="smalltxt">
				<td class="altbg1"><input class="checkbox" type="checkbox" name="validate" value="${order.orderid}" ${order.status != 1 ? 'disabled' : ''}></td>
				<td class="altbg2">${order.orderid}</td>
				<td class="altbg1">${order.statusDetail}</td>
				<td class="altbg2"><a href="space.jsp?action=viewpro&uid=${order.uid}" target="_blank">${order.username}</a></td>
				<td class="altbg1"><a href="http://wpa.qq.com/msgrd?V=1&Uin=${order.buyer}&Site=JspRun!&Menu=yes">${order.buyer}</a></td>
				<td class="altbg2">${order.amount}</td>
				<td class="altbg1">${order.price}<bean:message key="rmb_yuan" /> </td>
				<td class="altbg2">${order.submitdate}</td>
				<td class="altbg1">${order.confirmdate}</td>
			</tr>
		</c:forEach>
	</table>
	${multi.multipage}<br />
	<center><input class="button" type="submit" name="ordersubmit" value="<bean:message key="submit" />"></center>
</form>
</c:if>
<jsp:directive.include file="../cp_footer.jsp" />