<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onClick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_setting_credits" /></td></tr>
</table>
<br />
<form method="post" name="settings" id="settings" action="admincp.jsp?action=settings&do=credits">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="operation" value="credits">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td>
				<div style="float: left; margin-left: 0px; padding-top: 8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div>
				<div style="float: right; margin-right: 4px; padding-bottom: 9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /></a></div>
			</td>
		</tr>
		<tbody id="menu_tip" style="display: ">
			<tr>
				<td>
					<ul>
						<li><bean:message key="a_setting_credits_tips1" /><a href="admincp.jsp?action=creditwizard"><bean:message key="menu_tool_creditwizard" /></a><bean:message key="a_setting_interpunction1" /></li>
						<li><bean:message key="a_setting_credits_tips2" /><a href="admincp.jsp?action=settings&do=styles#customauthorinfo"><bean:message key="menu_setting_styles" /></a><bean:message key="a_setting_interpunction1" /><bean:message key="a_setting_credits_tips3" /></li>
					</ul>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<a name="dd4dad9e45938a02"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="a_setting_credits_scheme_title" /><a href="###" onclick="collapse_change('dd4dad9e45938a02')"><img id="menuimg_dd4dad9e45938a02" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_dd4dad9e45938a02" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_setting_credits_scheme" /></b></td>
				<td class="altbg2">
					<select name="projectid" onchange="window.location='admincp.jsp?action=settings&do=credits&projectid='+this.options[this.options.selectedIndex].value">
						<option value="0"><bean:message key="none" /></option>
						<logic:iterate id="pro" name="projects" scope="request">
							<option value="${pro.id}" ${pro.id==projectId?'selected':'' }>${pro.name}</option>
						</logic:iterate>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<script>
			function switchpolicy(obj, col) {
				var status = !obj.checked;
				$("policy" + col).disabled = status;
				var policytable = $("policytable");
				for(var row=2; row<14; row++) {
					if(is_opera) {
						policytable.rows[row].cells[col].firstChild.disabled = true;
					} else {
						policytable.rows[row].cells[col].disabled = status;
					}
				}
			}
		</script>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="9"><bean:message key="a_setting_credits_extended" /></td></tr>
		<tr align="center" class="category">
			<td><bean:message key="credits_id" /></td>
			<td><bean:message key="credits_title" /></td>
			<td><bean:message key="credits_unit" /></td>
			<td><bean:message key="a_setting_credits_ratio" /></td>
			<td><bean:message key="a_setting_credits_init" /></td>
			<td><bean:message key="a_setting_credits_available" /></td>
			<td><bean:message key="show_in_thread" /></td>
			<td><bean:message key="credits_inport" /></td>
			<td><bean:message key="credits_import" /></td>
		</tr>
		<c:forEach begin="1" end="8" var="ext">
			<tr align="center">
				<td class="altbg1">
					
					extcredits${ext}<input type="hidden" name="extcreditId" value="${ext}">
				</td>
				<td class="altbg2">
					
					<input type="text" size="8" name="extcredits_title_${ext}" value="${extcredits[ext].title}">
				</td>
				<td class="altbg1">
					
					<input type="text" size="5" name="extcredits_unit_${ext}" value="${extcredits[ext].unit}">
				</td>
				<td class="altbg2">
					
					<input type="text" size="3" name="extcredits_ratio_${ext}" value="${extcredits[ext].ratio==0.0||extcredits[ext].ratio==null ? 0 : extcredits[ext].ratio}" onkeyup="if(this.value != '0' && $('allowexchangeout1').checked == false && $('allowexchangein1').checked == false) {$('allowexchangeout1').checked = true;$('allowexchangein1').checked = true;} else if(this.value == '0') {$('allowexchangeout1').checked = false;$('allowexchangein1').checked = false;}">
				</td>
				<td class="altbg1">
					
					<input type="text" size="3" name="initcredits_${ext}" value="${initcredits[ext]}">
				</td>
				<td class="altbg2">
					
					<input class="checkbox" type="checkbox" name="extcredits_available_${ext}" value="1" ${!empty extcredits[ext].available &&extcredits[ext].available==1?"checked":""} >
				</td>
				<td class="altbg1">
					
					<input class="checkbox" type="checkbox" name="extcredits_showinthread_${ext}" value="1" ${!empty extcredits[ext].showinthread &&extcredits[ext].showinthread==1?"checked":""}>
				</td>
				<td class="altbg2">
					
					<input class="checkbox" type="checkbox" size="3" name="extcredits_allowexchangeout_${ext}" value="1" id="allowexchangeout1" ${!empty extcredits[ext].allowexchangeout &&extcredits[ext].allowexchangeout==1?"checked":""}>
				</td>
				<td class="altbg1">
					
					<input class="checkbox" type="checkbox" size="3" name="extcredits_allowexchangein_${ext}" value="1" id="allowexchangein1" ${!empty extcredits[ext].allowexchangein &&extcredits[ext].allowexchangein==1?"checked":""} >
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td class="altbg1" colspan="9"><bean:message key="a_setting_credits_extended_comment" />
			</td>
		</tr>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" id="policytable">
		<tr class="header"><td colspan="11"><bean:message key="a_setting_credits_policy" /></td></tr>
		<tr align="center" class="category">
			<td width="12%"><bean:message key="credits_id" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td id="policy1" class="category" align="center" ${!empty extcredits[ext].available && extcredits[ext].available==1? '': 'disabled'}> extcredit${ext}<br /><c:if test="${!empty extcredits[ext].title }">${extcredits[ext].title}</c:if></td>
			</c:forEach>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_post_comment" />">
			<td><bean:message key="a_setting_credits_policy_post" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td id="policy1" class="category" align="center">
					<c:if test="${empty creditspolicy.post[ext]}">
						<input type="text" size="2" name="creditspolicy_post_${ext}" value="0" ${!empty extcredits[ext].available && extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_post_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.post[ext]}">
						<input type="text" size="2" name="creditspolicy_post_${ext}" value="${creditspolicy.post[ext]}" ${!empty extcredits[ext].available && extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_post_${ext}" value="${creditspolicy.post[ext]}">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_reply_comment" />">
			<td><bean:message key="a_setting_credits_policy_reply" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td id="policy1" class="category" align="center">
					<c:if test="${empty creditspolicy.reply[ext]}">
						<input type="text" size="2" name="creditspolicy_reply_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_reply_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.reply[ext] }">
						<input type="text" size="2" name="creditspolicy_reply_${ext}" value="${creditspolicy.reply[ext] }" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_reply_${ext}" value="${creditspolicy.reply[ext] }">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_digest_comment" />">
			<td><bean:message key="a_setting_credits_policy_digest" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td id="policy1" class="category" align="center">
					<c:if test="${empty creditspolicy.digest[ext] }">
						<input type="text" size="2" name="creditspolicy_digest_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_digest_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.digest[ext] }">
						<input type="text" size="2" name="creditspolicy_digest_${ext}" value="${creditspolicy.digest[ext]}" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}	>
						<input type="hidden" size="2" name="hidden_creditspolicy_digest_${ext}" value="${creditspolicy.digest[ext]}">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_post_attach_comment" />">
			<td><bean:message key="a_setting_credits_policy_post_attach" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td id="policy1" class="category" align="center">
					<c:if test="${empty creditspolicy.postattach[ext] }">
						<input type="text" size="2" name="creditspolicy_postattach_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_postattach_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.postattach[ext] }">
						<input type="text" size="2" name="creditspolicy_postattach_${ext}" value="${creditspolicy.postattach[ext]}" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_postattach_${ext}" value="${creditspolicy.postattach[ext]}">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_get_attach_comment" />">
			<td><bean:message key="a_setting_credits_policy_get_attach" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td class="altbg2">
					<c:if test="${empty creditspolicy.getattach[ext] }">
						<input type="text" size="2" name="creditspolicy_getattach_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_getattach_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.getattach[ext] }">
						<input type="text" size="2" name="creditspolicy_getattach_${ext}" value="${creditspolicy.getattach[ext]}" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_getattach_${ext}" value="${creditspolicy.getattach[ext]}">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_send_pm_comment" />">
			<td><bean:message key="a_setting_credits_policy_send_pm" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td class="altbg2">
					<c:if test="${empty creditspolicy.pm[ext] }">
						<input type="text" size="2" name="creditspolicy_pm_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_pm_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.pm[ext] }">
						<input type="text" size="2" name="creditspolicy_pm_${ext}" value="${creditspolicy.pm[ext]}" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_pm_${ext}" value="${creditspolicy.pm[ext]}">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_search_comment" />">
			<td><bean:message key="a_setting_credits_policy_search" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td class="altbg2">
					<c:if test="${empty creditspolicy.search[ext]}">
						<input type="text" size="2" name="creditspolicy_search_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_search_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.search[ext]}">
						<input type="text" size="2" name="creditspolicy_search_${ext}" value="${creditspolicy.search[ext]}" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_search_${ext}" value="${creditspolicy.search[ext]}">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_promotion_visit_comment" />">
			<td><bean:message key="a_setting_credits_policy_promotion_visit" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td class="altbg2">
					<c:if test="${empty creditspolicy.promotion_visit[ext]}">
						<input type="text" size="2" name="creditspolicy_promotion_visit_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_promotion_visit_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.promotion_visit[ext] }">
						<input type="text" size="2" name="creditspolicy_promotion_visit_${ext}" value="${creditspolicy.promotion_visit[ext]}" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_promotion_visit_${ext}" value="${creditspolicy.promotion_visit[ext]}">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_promotion_register_comment" />">
			<td><bean:message key="a_setting_credits_policy_promotion_register" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td class="altbg2">
					<c:if test="${empty creditspolicy.promotion_register[ext]}">
						<input type="text" size="2" name="creditspolicy_promotion_register_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_promotion_register_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.promotion_register[ext] }">
						<input type="text" size="2" name="creditspolicy_promotion_register_${ext}" value="${creditspolicy.promotion_register[ext] }" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" size="2" name="hidden_creditspolicy_promotion_register_${ext}" value="${creditspolicy.promotion_register[ext] }">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_trade_comment" />">
			<td><bean:message key="a_setting_credits_policy_trade" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td class="altbg2">
					<c:if test="${empty creditspolicy.tradefinished[ext] }">
						<input type="text" size="2" name="creditspolicy_tradefinished_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" name="hidden_creditspolicy_tradefinished_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.tradefinished[ext]  }">
						<input type="text" size="2" name="creditspolicy_tradefinished_${ext}" value="${creditspolicy.tradefinished[ext] }" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" name="hidden_creditspolicy_tradefinished_${ext}" value="${creditspolicy.tradefinished[ext] }">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_policy_poll_comment" />">
			<td><bean:message key="a_setting_credits_policy_poll" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td class="altbg2">
					<c:if test="${empty creditspolicy.votepoll[ext]}">
						<input type="text" size="2" name="creditspolicy_votepoll_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" name="hidden_creditspolicy_votepoll_${ext}" value="0">
					</c:if>
					<c:if test="${!empty creditspolicy.votepoll[ext] }">
						<input type="text" size="2" name="creditspolicy_votepoll_${ext}" value="${creditspolicy.votepoll[ext]}" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
						<input type="hidden" name="hidden_creditspolicy_votepoll_${ext}" value="${creditspolicy.votepoll[ext]}">
					</c:if>
				</td>
			</c:forEach>
		</tr>
		<tr align="center" class="altbg1" title="<bean:message key="a_setting_credits_lowerlimit_comment" />">
			<td><bean:message key="a_setting_credits_lowerlimit" /></td>
			<c:forEach begin="1" end="8" var="ext">
				<td class="altbg2">
					<c:choose>
						<c:when test="${empty creditspolicy.lowerlimit[ext] }">
							<input type="text" size="2" name="creditspolicy_lowerlimit_${ext}" value="0" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
							<input type="hidden" name="hidden_creditspolicy_lowerlimit_${ext}" value="0">
						</c:when>
						<c:otherwise>
							<input type="text" size="2" name="creditspolicy_lowerlimit_${ext}" value="${creditspolicy.lowerlimit[ext]}" ${!empty extcredits[ext].available &&extcredits[ext].available==1? '': 'disabled'}>
							<input type="hidden" name="hidden_creditspolicy_lowerlimit_${ext}" value="${creditspolicy.lowerlimit[ext]}">
						</c:otherwise>
					</c:choose>
				</td>
			</c:forEach>
		</tr>
		<tr>
			<td class="altbg1" colspan="12"><bean:message key="a_setting_credits_policy_comment" />
			</td>
		</tr>
	</table>
	<br />
	<a name="0f70313e30033049"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="menu_setting_credits" /><a href="###" onclick="collapse_change('0f70313e30033049')"><img id="menuimg_0f70313e30033049" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_0f70313e30033049" style="display: yes">
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="a_setting_creditsformula" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_creditsformula_comment1" /><a href="admincp.jsp?action=toCreditExpression" target="_blank"><bean:message key="a_setting_creditsformula_comment2" /></a></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[creditsformula]', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('settingsnew[creditsformula]', 0)">
					<br />
					<textarea rows="6" name="creditsformula" id="settingsnew[creditsformula]" cols="50">${creditsformula}</textarea>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_creditstrans" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_creditstrans_comment" /></span>
				</td>
				<td class="altbg2">
					<select name="creditstrans">
						<option value="0"><bean:message key="none" /></option>
						<c:forEach begin="1" end="8" var="ext">
							<option value="${ext}" ${creditstrans==ext?"selected":""}>extcredits${ext}(${extcredits[ext].title})</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_creditstax" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_creditstax_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="creditstax" value="${creditstax}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_transfermincredits" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_transfermincredits_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="transfermincredits" value="${transfermincredits}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_exchangemincredits" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_exchangemincredits_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="exchangemincredits"
						value="${exchangemincredits}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxincperthread" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxincperthread_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="maxincperthread" value="${maxincperthread}">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_setting_maxchargespan" /></b>
					<br />
					<span class="smalltxt"><bean:message key="a_setting_maxchargespan_comment" /></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="maxchargespan" value="${maxchargespan}">
				</td>
			</tr>
		</tbody>
	</table>
	<br>
	<center>
		<input type="hidden" name="from" value=""><input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">&nbsp;&nbsp;&nbsp;<input name="projectsave" type="hidden" value=""><input class="button" type="button" onclick="$('settings').projectsave.value='projectsave';$('settings').settingsubmit.click()" value="<bean:message key="saveconf" />">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />