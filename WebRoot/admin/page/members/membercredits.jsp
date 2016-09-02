<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="a_member_edit_credits"/></td></tr>
</table><br /><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div>
	<div style="float:right; margin-right:4px; padding-bottom:9px"> <a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a> </div>
	</td></tr><tbody id="menu_tip" style="display:">
		<tr><td><bean:message key="a_member_credits_tips"/></td></tr></tbody></table><br />
			<form name="input" method="post" action="admincp.jsp?action=editcredits&memberid=${member.uid}">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
						<tr class="header"> <td colspan="10"> <bean:message key="a_member_edit_credits"/> - ${member.username}(${usergroup.grouptitle}) </td> </tr>
							<tr class="category" align="center">
								<td width="14%"> <bean:message key="a_member_edit_credits_ranges"/> </td> <td width="14%"> <bean:message key="credits"/> </td>
								<c:forEach begin="1" end="8" var="ext">
									<c:choose>
										<c:when test="${extcreditMap[ext]!=null}"> <td width="9%"> ${extcreditMap[ext].title} </td> </c:when>
										<c:otherwise> <td width="9%"> extcredits${ext} </td> </c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
							<tr align="center">
								<td class="altbg1">
									${usergroup.creditshigher}~${usergroup.creditslower}
								</td>
								<td class="altbg2">
									<input type="text" name="jscredits" id="jscredits" value="${member.credits}" size="3" disabled="disabled">
								</td>
								<c:forEach begin="1" end="8" var="ext">
									<c:choose>
										<c:when test="${extcreditMap[ext]!=null}">
											<td class="altbg1">
												<input type="text" size="3" name="extcreditsnew[${ext}]" id="extcreditsnew[${ext}]" value="${extcreditsm[ext-1]}" onkeyup="membercredits()">
											</td>
										</c:when>
										<c:otherwise>
											<td class="altbg1">
												<input name="extcreditsnew[${ext}]" type="text" size="3" value="N/A" disabled>
											</td>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
						</table>
						<br />
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="tableborder">
							<tr class="header">
								<td colspan="2"> <bean:message key="a_member_edit_reason"/> </td>
							</tr>
							<tr>
								<td class="altbg1" width="45%">
									<b><bean:message key="a_member_edit_credits_reason"/></b>
									<br />
									<span class="smalltxt"><bean:message key="a_member_edit_credits_reason_comment"/></span>
								</td>
								<td class="altbg2" width="40%">
									<textarea name="reason" rows="5" cols="30" style="width: 90%"></textarea>
								</td>
							</tr>
						</table>
						<br />
						<center>
						<script type="text/javascript">
							var extcredits = new Array();
							function membercredits() {
								var credits = 0;
								for(var i = 1; i <= 8; i++) {
									e = $('extcreditsnew['+i+']');
									if(e && parseInt(e.value)) {
										extcredits[i] = parseInt(e.value);
									} else {
										extcredits[i] = 0;
									}
								}
								$('jscredits').value = Math.round(${creditexpressions});
							}
						</script>
						<input class="button" type="submit" name="creditsubmit" value="<bean:message key="submit"/>">
						</center>
					</form>
<jsp:directive.include file="../cp_footer.jsp" />
