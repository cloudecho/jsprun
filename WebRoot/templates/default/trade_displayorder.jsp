<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a id="forumlist" href="${settings.indexname}">${settings.bbname}</a> &raquo; ${navigation} &raquo; ${threads.subject}
</div>
<script type="text/javascript">
	var max_obj = ${usergroups.tradestick};
	var p = ${stickcount};
	function checkbox(obj) {
		if(obj.checked) {
			p++;
			for (var i = 0; i < $('tradeform').elements.length; i++) {
				var e = tradeform.elements[i];
				if(p == max_obj) {
					if(e.name.match('stick') && !e.checked) {
						e.disabled = true;
					}
				}
			}
		} else {
			p--;
			for (var i = 0; i < $('tradeform').elements.length; i++) {
				var e = tradeform.elements[i];
				if(e.name.match('stick') && e.disabled) {
					e.disabled = false;
				}
			}
		}
	}
</script>
<form id="tradeform" method="post" action="misc.jsp?action=tradeorder&tid=${threads.tid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox">
		<h1><bean:message key="trade_displayorder"/></h1>
		<table summary="trade_displayorder" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<td class="nums"><bean:message key="display_order"/></td>
					<td><bean:message key="thread_email_friend"/></td>
					<td width="48">&nbsp;</td>
					<td><bean:message key="tradelog_trade_name"/></td>
					<td><bean:message key="post_trade_number"/></td>
					<td><bean:message key="post_trade_costprice"/></td>
					<td><bean:message key="post_trade_price"/></td>
					<td><bean:message key="trade_remaindays"/></td>
				</tr>
			</thead>
			<c:forEach items="${trades}" var="trade">
			<tr>
				<td class="nums"><input size="1" name="displayorder[${trade.pid}]" value="${trade.displayorderview}" /></td>
				<td><input class="checkbox" type="checkbox" onclick="checkbox(this)" name="stick[${trade.pid}]" value="yes" ${trade.displayorder>0?'checked':''} ${usergroups.tradestick<=stickcount&&trade.displayorder<=0?'disabled':''}/></td>
				<td align="center">
				<c:choose>
					<c:when test="${trade.aid>0}">
						<a href="viewthread.jsp?do=tradeinfo&tid=${threads.tid}&pid=${trade.pid}" target="_blank"><img class="absmiddle" src="attachment.jsp?aid=${trade.aid}" onload="thumbImg(this)" width="48" height="48" alt="${trade.subject}" /></a>
					</c:when>
					<c:otherwise>
					<a href="viewthread.jsp?do=tradeinfo&tid=${threads.tid}&pid=${trade.pid}" target="_blank"><img class="absmiddle" src="${styles.IMGDIR}/trade_nophotosmall.gif" onload="thumbImg(this)" width="48" height="48" alt="${trade.subject}" /></a>
					</c:otherwise>
				</c:choose>
				</td>
				<td>
					<a href="viewthread.jsp?do=tradeinfo&amp;tid=${threads.tid}&amp;pid=${trade.pid}" target="_blank">${trade.subject}</a>
					[ <a href="post.jsp?action=edit&amp;fid=${threads.fid}&amp;tid=${threads.tid}&amp;pid=${trade.pid}&amp;page=1&amp;extra=${extra}" target="_blank"><bean:message key="edit"/></a> ]
				</td>
				<td>${trade.amount}</td>
				<td>
				<c:if test="${trade.costprice>0.0}"><del>${trade.costprice}</del> <bean:message key="rmb_yuan"/></c:if>
				</td>
				<td>${trade.price}<bean:message key="rmb_yuan"/></td>
				<td>
					<c:choose>
						<c:when test="${trade.closed>0}">
							<em><bean:message key="trade_timeout"/></em>
						</c:when>
						<c:when test="${trade.expiration>0}">
							${trade.expiration}<bean:message key="ipban_days"/>${trade.expirationhour}<bean:message key="hr"/>
						</c:when>
						<c:when test="${trade.expiration==-1}">
							<em><bean:message key="trade_timeout"/></em>
						</c:when>
					</c:choose>
				</td>
			</tr>
			</c:forEach>
		</table>
		<div class="footoperation">
			<label><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form, 'stick')" /><bean:message key="select_all"/></label>
			<button class="submit" type="submit" name="tradesubmit" value="true"><bean:message key="trade_update_order"/></button>
			&nbsp;&nbsp;<bean:message key="trade_update_stickmax"/> ${usergroups.tradestick}
		</div>
	</div>
</form>
<jsp:include flush="true" page="footer.jsp" />
