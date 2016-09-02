<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_cron"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td><bean:message key="a_other_crons_edit_tips"/></td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=cronsedit&cronsId=${cronsFromAction.cronid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_other_crons_edit"/> - ${cronsFromAction.name }<a href="###" onclick="collapse_change('a45b0b68771fc480')"><img id="menuimg_a45b0b68771fc480" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_c2b2d3d75df4c8ee" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_other_crons_week_day"/>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_crons_edit_weekday_comment"/></span>
				</td>
				<td class="altbg2">
					<select name="weekdaynew">
						<option value="-1" ${cronsFromAction.weekday==-1?"selected":"" }>*</option>
						<option value="0" ${cronsFromAction.weekday==0?"selected":"" }><bean:message key="a_other_crons_week_day_0"/></option>
						<option value="1" ${cronsFromAction.weekday==1?"selected":"" }><bean:message key="a_other_crons_week_day_1"/></option>
						<option value="2" ${cronsFromAction.weekday==2?"selected":"" }><bean:message key="a_other_crons_week_day_2"/></option>
						<option value="3" ${cronsFromAction.weekday==3?"selected":"" }><bean:message key="a_other_crons_week_day_3"/></option>
						<option value="4" ${cronsFromAction.weekday==4?"selected":"" }><bean:message key="a_other_crons_week_day_4"/></option>
						<option value="5" ${cronsFromAction.weekday==5?"selected":"" }><bean:message key="a_other_crons_week_day_5"/></option>
						<option value="6" ${cronsFromAction.weekday==6?"selected":"" }><bean:message key="a_other_crons_week_day_6"/></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="day"/>:</b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_crons_edit_day_comment"/></span>
				</td>
				<td class="altbg2">
					<select name="daynew">
					<c:forEach var="hourIndex" begin="0" end="31">
						<option value="${hourIndex==0?hourIndex-1: hourIndex}" ${cronsFromAction.day==hourIndex?"selected":"" }>${hourIndex==0?"*":hourIndex }</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
									<td width="45%" class="altbg1">
										<b><bean:message key="hr"/></b>
										<br />
										<span class="smalltxt"><bean:message key="a_other_crons_edit_hour_comment"/></span>
									</td>
									<td class="altbg2">
										<select name="hournew">
											<c:forEach var="hourIndex" begin="0" end="24">
											<option value="${hourIndex-1}" ${cronsFromAction.hour==hourIndex-1?"selected":"" }>
												${hourIndex==0?"*": hourIndex-1}
											</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td width="45%" class="altbg1">
										<b><bean:message key="a_other_crons_minute"/>:</b>
										<br />
										<span class="smalltxt"><bean:message key="a_other_crons_edit_minute_comment"/></span>
									</td>
									<td class="altbg2">
									<c:forEach begin="0" end="11" var="outNowIndex" varStatus="optionSign">
									<c:if test="${outNowIndex==6}">
										<br>
									</c:if>
									<select name="selectMinute">
										<c:forEach begin="0" end="60" var="nowIndex">
										<c:if test="${outNowIndex < selectedMinuteSize}">
											<c:forEach var="selectedMinuteTemp" items="${selectedMinute}" varStatus="otherOptionSign">
											<c:if test="${selectedMinuteTemp==nowIndex-1}">
												<c:if test="${optionSign.index == otherOptionSign.index}">
													<c:set var="tempSign_" value="selected" scope="page" />
												</c:if>
											</c:if>
											</c:forEach>
										</c:if>
										<option value="${nowIndex-1 }"${tempSign_}>
										<c:remove var="tempSign_" scope="page" />
										<c:choose>
										<c:when test="${nowIndex==0}">
											*
										</c:when>
										<c:when test="${nowIndex > 0 and nowIndex < 11}">
											0${nowIndex-1}
										</c:when>
										<c:otherwise>
											${nowIndex-1}
										</c:otherwise>
										</c:choose>
										</option>
										</c:forEach>
									</select>
									</c:forEach>
									</td>
								</tr>
								<tr>
									<td width="45%" class="altbg1">
										<b><bean:message key="a_other_crons_edit_filename"/></b>
										<br />
										<span class="smalltxt"><bean:message key="a_other_crons_edit_filename_comment"/></span>
									</td>
									<td class="altbg2">
										<input type="text" size="50" name="filenamenew" value="${cronsFromAction.filename }" maxlength="50">
									</td>
								</tr>
							</tbody>
						</table>
						<br />
	<center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />