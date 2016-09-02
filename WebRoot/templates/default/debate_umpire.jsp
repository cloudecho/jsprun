<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; ${navigation} &raquo; <bean:message key="debate_umpirecomment"/></div>
<form method="post" id="postform" action="misc.jsp?action=debateumpire&tid=${tid}" onsubmit="return validate(this)">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="page" value="page" />
	<div class="mainbox formbox">
		<h1><bean:message key="debate_umpirecomment"/></h1>
		<table summary="<bean:message key="debate_umpirecomment"/>" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th><bean:message key="username"/></th>
					<td>${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</td>
				</tr>
			</thead>

			<tr>
				<th><bean:message key="special_5"/></th>
				<td>${debate.subject}</td>
			</tr>

			<tr>
				<th><bean:message key="debate_winner"/></th>
				<td>
					<label><input type="radio" name="winner" value="1" class="radio" <c:if test="${debate.winner==1}">checked</c:if> id="winner1" /> <bean:message key="debate_square"/></label>
					<label><input type="radio" name="winner" value="2" class="radio" <c:if test="${debate.winner==2}">checked</c:if> id="winner2" /> <bean:message key="debate_opponent"/></label>
					<label><input type="radio" name="winner" value="3" class="radio" <c:if test="${debate.winner==3}">checked</c:if> id="winner3" /> <bean:message key="debate_draw"/></label>
				</td>
			</tr>

			<tr>
				<th><label for="bestdebater"><bean:message key="debate_bestdebater"/></label></th>
				<td>
					<input type="text" name="bestdebater" value="${debate.bestdebater}" size="20" id="bestdebater" />
					<select onchange="$('bestdebater').value=this.options[this.options.selectedIndex].value">
						<option value=""><strong><bean:message key="debate_recommend_list"/></strong></option>
						<option value="">------------------------------</option>
						<c:forEach items="${debateposts}" var="dpost">
							<option value="${dpost.username}"  <c:if test="${dpost.username==dpost.bestdebater}">selected</c:if>>${dpost.username}( ${dpost.voters} <bean:message key="debate_poll"/>,  <c:choose><c:when test="${dpost.stand==1}"><bean:message key="debate_square"/></c:when><c:when test="${dpost.stand==2}"><bean:message key="debate_opponent"/></c:when></c:choose>)</option>
						</c:forEach>
					</select><em class="tips">(<bean:message key="debate_list_nonexistence"/>)</em>
				</td>
			</tr>

			<tr>
				<th valign="top"><label for="umpirepoint"><bean:message key="debate_umpirepoint"/></label></th>
				<td><textarea id="umpirepoint" name="umpirepoint" style="width:98%; height: 200px;">${debate.umpirepoint}</textarea></td>
			</tr>

			<tr class="btns">
				<th>&nbsp;</th>
				<td><button type="submit" name="umpiresubmit" value="true" class="submit"><bean:message key="submitf"/></button></td>
			</tr>
		</table>
	</div>
</form>

<script type="text/javascript">
function validate(theform) {
	if(theform.bestdebater.value == '') {
		alert('<bean:message key="debate_bestdebater_nonexistence"/>');
		return false;
	} else if($('winner1').checked == false && $('winner2').checked == false && $('winner3').checked == false) {
		alert('<bean:message key="debate_winner_nonexistence"/>');
		return false;
	} else if(theform.umpirepoint.value == '') {
		alert('<bean:message key="debate_umpirepoint_nonexistence"/>');
		return false;
	}
	return true;
}
</script>
<jsp:include flush="true" page="footer.jsp" />