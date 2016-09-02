<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<input type="hidden" name="typeid" size="45" value="${selecttypeid}" />
<table cellspacing="0" cellpadding="0" width="100%">
<c:if test="${threadtypes.expiration[selecttypeid]>0}">
	<tr>
		<th><bean:message key="threadtype_expiration"/>(<bean:message key="required"/>)</th>
		<td>
			<select name="typeexpiration">
				<option value="259200"><bean:message key="three_days"/></option>
				<option value="432000"><bean:message key="five_days"/></option>
				<option value="604800"><bean:message key="seven_days"/></option>
				<option value="2592000"><bean:message key="one_month"/></option>
				<option value="7776000"><bean:message key="three_months"/></option>
				<option value="15552000"><bean:message key="180_day"/></option>
				<option value="31536000"><bean:message key="one_year"/></option>
			</select>
		</td>
	</tr>
</c:if>
<c:forEach items="${optionlist}" var="option">
	<tr>
		<th>${option.title}
			<c:if test="${option.maxnum!=null}">(<bean:message key="maxnum"/> ${option.maxnum})</c:if>
			<c:if test="${option.minnum!=null}">(<bean:message key="minnum"/> ${option.minnum})</c:if>
			<c:if test="${option.maxlength!=null}">(<bean:message key="maxlength"/> ${option.maxlength})</c:if>
			<c:if test="${option.required>0}">(<bean:message key="required"/>)</c:if>
			<c:if test="${option.unchangeable>0}">(<bean:message key="unchangeable"/>)</c:if>
			<c:if test="${option.description!=''}"><br />${option.description}</c:if>
		</th>
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
					<input type="text" name="typeoption[${option.identifier}]" size="45" value="${option.value}" onclick="showcalendar(event, this, false)" ${option.unchangeable}/>
				</c:when>
				<c:when test="${option.type=='number'||option.type=='text'||option.type=='email'||option.type=='image'||option.type=='url'}">
					<input type="text" name="typeoption[${option.identifier}]" size="45" value="${option.value}" ${option.unchangeable}/>
				</c:when>
				<c:when test="${option.type=='select'}">
					<select name="typeoption[${option.identifier}]" ${option.unchangeable}>
						<c:forTokens items="${option.choices}" delims="\n" var="value" varStatus="index">
							<c:forTokens items="${value}" delims="=" var="key_value" varStatus="inerIndex">
								<c:if test="${inerIndex.index == 0}">
									<c:set var="_key" value="${key_value}"></c:set>
								</c:if>
								<c:if test="${inerIndex.index == 1}">
									<c:set var="_value" value="${key_value}"></c:set>
								</c:if>
							</c:forTokens>
							<option value="${_key}" ${option.value[_key]}>${_value}</option>
					</c:forTokens>
					</select>
				</c:when>
				<c:when test="${option.type=='radio'}">
					<c:forTokens items="${option.choices}" delims="\n" var="value" varStatus="index">
						<c:forTokens items="${value}" delims="=" var="key_value" varStatus="inerIndex">
							<c:if test="${inerIndex.index == 0}">
								<c:set var="_key" value="${key_value}"></c:set>
							</c:if>
							<c:if test="${inerIndex.index == 1}">
								<c:set var="_value" value="${key_value}"></c:set>
							</c:if>
						</c:forTokens>
						<input type="radio" class="radio" name="typeoption[${option.identifier}]" size="45" value="${_key}" ${option.value[_key]} ${option.unchangeable}> ${_value}
					</c:forTokens>
				</c:when>
				<c:when test="${option.type=='checkbox'}">
					<c:forTokens items="${option.choices}" delims="\n" var="value" varStatus="index">
						<c:forTokens items="${value}" delims="=" var="key_value" varStatus="inerIndex">
							<c:if test="${inerIndex.index == 0}">
								<c:set var="_key" value="${key_value}"></c:set>
							</c:if>
							<c:if test="${inerIndex.index == 1}">
								<c:set var="_value" value="${key_value}"></c:set>
							</c:if>
						</c:forTokens>
						<input type="checkbox" class="checkbox" name="typeoption[${option.identifier}]" size="45" value="${_key}" ${option.value[_key]} ${option.unchangeable}> ${_value}
					</c:forTokens>
				</c:when>
				<c:when test="${option.type=='textarea'}">
					<textarea name="typeoption[${option.identifier}]" rows="10" cols="20" style="width:99%; height:60px" ${option.unchangeable}>${option.value}</textarea>
				</c:when>
			</c:choose>
		</td>
	</tr>
</c:forEach>
</table>