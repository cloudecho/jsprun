<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<input type="hidden" name="typeid" value="${param.typeid}" />
<input type="hidden" name="fid" value="${param.srchfid}" />
<table cellspacing="0" cellpadding="0" width="100%">
<c:forEach items="${optionlist}" var="option">
	<c:if test="${option.search>0}">
	<tr>
		<th>${option.title}</th>
		<td>
			<c:choose>
				<c:when test="${option.type=='calendar'}">
					<script type="text/javascript">
					lang['calendar_Sun'] = '<bean:message key="calendar_Sun"/>';
					lang['calendar_Mon'] = '<bean:message key="calendar_Mon"/>';
					lang['calendar_Tue'] = '<bean:message key="calendar_Tue"/>';
					lang['calendar_Wed'] = '<bean:message key="calendar_Wed"/>';
					lang['calendar_Thu'] = '<bean:message key="calendar_Thu"/>';
					lang['calendar_Fri'] = '<bean:message key="calendar_Fri"/>';
					lang['calendar_Sat'] = '<bean:message key="calendar_Sat"/>';
					lang['old_month'] = '<bean:message key="old_month"/>';
					lang['select_year'] = '<bean:message key="select_year"/>';
					lang['select_month'] = '<bean:message key="select_month"/>';
					lang['next_month'] = '<bean:message key="next_month"/>';
					lang['calendar_hr'] = '<bean:message key="calendar_hr"/>';
					lang['calendar_min'] = '<bean:message key="calendar_min"/>';
					lang['calendar_month'] = '<bean:message key="calendar_month"/>';
					lang['calendar_today'] = '<bean:message key="calendar_today"/>';
					</script>
					<script type="text/javascript" src="include/javascript/calendar.js"></script>
					<input type="text" name="searchoption[${option.optionid}][value]" size="45" value="${option.value}" onclick="showcalendar(event, this, false)"/>
				</c:when>
				<c:when test="${option.type=='number'}">
					<select name="searchoption[${option.optionid}][condition]">
						<option value="0"><bean:message key="equal_to"/></option>
						<option value="1"><bean:message key="more_than"/></option>
						<option value="2"><bean:message key="lower_than"/></option>
					</select>&nbsp;&nbsp;
					<input type="text" name="searchoption[${option.optionid}][value]" size="45" value="${option.value}" />
					<input type="hidden" name="searchoption[${option.optionid}][type]" value="number">
				</c:when>
				<c:when test="${option.type=='text'||option.type=='email'||option.type=='image'||option.type=='url'||option.type=='textarea'}">
					<input type="text" name="searchoption[${option.optionid}][value]" size="45" value="${option.value}" />
				</c:when>
				<c:when test="${option.type=='select'}">
					<select name="searchoption[${option.optionid}][value]">
						<c:forTokens items="${option.choices}" delims="\n" var="value" varStatus="index">
							<option value="${index.count}" ${option.value[index.count]}>${value}</option>
						</c:forTokens>
					</select>
					<input type="hidden" name="searchoption[${option.optionid}][type]" value="select">
				</c:when>
				<c:when test="${option.type=='radio'}">
					<c:forTokens items="${option.choices}" delims="\n" var="value" varStatus="index">
						<input type="radio" class="radio" name="searchoption[${option.optionid}][value]" size="45" value="${index.count}" ${option.value[index.count]} > ${value}
					</c:forTokens>
					<input type="hidden" name="searchoption[${option.optionid}][type]" value="radio">
				</c:when>
				<c:when test="${option.type=='checkbox'}">
					<c:forTokens items="${option.choices}" delims="\n" var="value" varStatus="index">
						<input type="checkbox" class="checkbox" name="searchoption[${option.optionid}][value]" size="45" value="${index.count}" ${option.value[index.count]} > ${value}
					</c:forTokens>
					<input type="hidden" name="searchoption[${option.optionid}][type]" value="checkbox">
				</c:when>
			</c:choose>
		</td>
	</tr>
	</c:if>
</c:forEach>
</table>