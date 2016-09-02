<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_newsletter" /></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=newsletterinit">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="menu_tool_newsletter" /> - <bean:message key="a_member_search" />
			</td>
		</tr>

		<tr>
			<td class="altbg1" width="45%">
				<bean:message key="a_member_search_user" />
			</td>
			<td align="right" class="altbg2" width="40%">
				<bean:message key="case_insensitive" />
				<input type="checkbox" name="cins" value="yes" class="checkbox" checked>
				<br />
				<input type="text" name="username" size="40" value="">
			</td>
		</tr>

		<tr>
			<td class="altbg1" valign="top">
				<bean:message key="a_member_search_group" />
				<br />
				<bean:message key="a_member_search_comment" />
			</td>
			<td align="right" class="altbg2">
				<select name="usergroupids" size="5" multiple="multiple" style="width: 65%">
					<option value="all" selected>
						<bean:message key="unlimited" />
					</option>
					<c:forEach items="${allGroups}" var="group">
						<option value=${group.groupid}>${group.grouptitle}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="altbg1">
				&nbsp;
			</td>
			<td align="right" class="altbg2" style="text-align: right;">
				<input class="checkbox" type="checkbox" value="1" onclick="$('advanceoption').style.display = $('advanceoption').style.display == 'none' ? '' : 'none'; this.value = this.value == 1 ? 0 : 1; this.checked = this.value == 1 ? false : true">
				<bean:message key="more_options" /> &nbsp;
			</td>
		</tr>

		<tbody id="advanceoption" style="display: none">

			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_email" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="email" size="40" value="">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_system_credit_low" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="lower_credits" size="40" value="">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_system_credit_heigh" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="higher_credits" size="40" value="">
				</td>
			</tr>

	<c:forEach items="${extcreditMap}" var="extcredit">
			<tr>
				<td class="altbg1">
					${extcredit.value.title} <bean:message key="a_system_low" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="lower_extcredits${extcredit.key}" value="" size="40">
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					${extcredit.value.title} <bean:message key="a_system_heigh" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="higher_extcredits${extcredit.key}" value="" size="40">
				</td>
			</tr>
		</c:forEach>

			<tr>
				<td class="altbg1">
					<bean:message key="a_system_posts_low" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="lower_posts" value="" size="40">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_system_posts_heigh" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="higher_posts" size="40" value="">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_regip" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="regip" size="40" value="">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_lastip" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="lastip" size="40" value="">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_regdatebefore" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="regdatebefore" size="40" value="">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_regdateafter" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="regdateafter" size="40" value="">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_lastvisitbefore" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="lastvisitbefore" size="40" value="">
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_lastvisitafter" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="lastvisitafter" size="40" value="">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_lastpostbefore" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="lastpostbefore" size="40" value="">
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_lastpostafter" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="lastpostafter" size="40" value="">
				</td>
			</tr>

			<tr>
				<td class="altbg1">
					<bean:message key="a_member_search_birthday" />
				</td>
				<td align="right" class="altbg2">
					<input type="text" name="birthyear" size="5" value="">
					<bean:message key="year" />
					<select name="birthmonth">
						<option value="">
							&nbsp;
						</option>
						<option value="01">
							01
						</option>
						<option value="02">
							02
						</option>
						<option value="03">
							03
						</option>
						<option value="04">
							04
						</option>
						<option value="05">
							05
						</option>
						<option value="06">
							06
						</option>
						<option value="07">
							07
						</option>
						<option value="08">
							08
						</option>
						<option value="09">
							09
						</option>
						<option value="10">
							10
						</option>
						<option value="11">
							11
						</option>
						<option value="12">
							12
						</option>
					</select>
					<bean:message key="month" />
					<select name="birthday">
						<option value="">
							&nbsp;
						</option>
						<option value="01">
							01
						</option>
						<option value="02">
							02
						</option>
						<option value="03">
							03
						</option>
						<option value="04">
							04
						</option>
						<option value="05">
							05
						</option>
						<option value="06">
							06
						</option>
						<option value="07">
							07
						</option>
						<option value="08">
							08
						</option>
						<option value="09">
							09
						</option>
						<option value="10">
							10
						</option>
						<option value="11">
							11
						</option>
						<option value="12">
							12
						</option>
						<option value="13">
							13
						</option>
						<option value="14">
							14
						</option>
						<option value="15">
							15
						</option>
						<option value="16">
							16
						</option>
						<option value="17">
							17
						</option>
						<option value="18">
							18
						</option>
						<option value="19">
							19
						</option>
						<option value="20">
							20
						</option>
						<option value="21">
							21
						</option>
						<option value="22">
							22
						</option>
						<option value="23">
							23
						</option>
						<option value="24">
							24
						</option>
						<option value="25">
							25
						</option>
						<option value="26">
							26
						</option>
						<option value="27">
							27
						</option>
						<option value="28">
							28
						</option>
						<option value="29">
							29
						</option>
						<option value="30">
							30
						</option>
						<option value="31">
							31
						</option>
					</select>
					<bean:message key="day" />
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="searchsubmit" value="<bean:message key="menu_tool_newsletter" />">
	</center>
</form>

<c:if test="${result=='yes'}">
	<form action="admincp.jsp?action=newlettersubmit" method="post">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<br />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td colspan="9">
					<bean:message key="a_member_search_result" /> ${size}
				</td>
			</tr>
				<tr>
					<td class="altbg1">
						<bean:message key="subject" />:
					</td>
					<td class="altbg2">
						<input type="text" name="subject" size="80" value="${creditsnotifyMap.subject}" maxlength="75">
					</td>
				</tr>

				<tr>
					<td class="altbg1" valign="top">
						<bean:message key="message" />:
					</td>
					<td class="altbg2">
						<textarea cols="80" rows="10" name="message">${creditsnotifyMap.message}</textarea>
					</td>
				</tr>

				<tr>
					<td class="altbg1">
						<bean:message key="a_member_newsletter_send_via" />
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" value="email" name="sendvia" onclick="$('pertask').value = 1"> Email
						<input class="radio" type="radio" value="pm" checked name="sendvia" onclick="$('pertask').value = 100">
						<bean:message key="pm" />
					</td>
				</tr>
				<tr>
					<td class="altbg1">
						<bean:message key="a_member_newsletter_num" />
					</td>
					<td class="altbg2">
						<input type="text" name="pertask" id="pertask" value="100" size="10">
					</td>
				</tr>
		</table>
		<br />
		<center>
			<input class="button" type="submit" name="updatecreditsubmit" value="<bean:message key="submit" />">
		</center>
	</form>
</c:if>
<jsp:directive.include file="../cp_footer.jsp" />