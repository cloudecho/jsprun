<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<script language="javascript" type="text/javascript">
	function copyoption(s1, s2) {
		var s1 = $(s1);
		var s2 = $(s2);
		var len = s1.options.length;
		for(var i=0; i<len; i++) {
			op = s1.options[i];
			if(op.selected == true && !optionexists(s2, op.value)) {
				o = op.cloneNode(true);
				s2.appendChild(o);
			}
		}
	}
				
	function optionexists(s1, value) {
		var len = s1.options.length;
		for(var i=0; i<len; i++) {
			if(s1.options[i].value == value) {
				return true;
			}
		}
		return false;
	}

	function removeoption(s1) {
		var s1 = $(s1);
		var len = s1.options.length;
		for(var i=s1.options.length - 1; i>-1; i--) {
			op = s1.options[i];
			if(op.selected && op.selected == true) {
				s1.removeChild(op);
			}
		}
		return false;
	}
						
	function selectalloption(s1) {
		var s1 = $(s1);
		var len = s1.options.length;
		for(var i=s1.options.length - 1; i>-1; i--) {
			op = s1.options[i];
			op.selected = true;
		}
	}
</script>
<form method="post" action="admincp.jsp?action=modeldetail&modelid=${typemodel.id}" onsubmit="selectalloption('moptselect');">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="a_forum_threadtype_models_basic_setting"/></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="name"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="newname" value="${typemodel.name}" maxlength="20"></td>
		</tr>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="a_forum_threadtype_models_option_setting"/></td>
		</tr>
		<c:if test="${typemodel.type==1}">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_forum_threadtype_models_option_model"/></b></td>
				<td class="altbg2">
					<select name="o" size="8" multiple="multiple" style="width: 222px">
						<c:forEach items="${sysoptions}" var="sysoption">
						<option value="${sysoption.optionid}">${sysoption.title}</option>
						</c:forEach>
					</select>
					<br />
				</td>
			</tr>
		</c:if>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_threadtype_models_option_user"/></b></td>
			<td class="altbg2">
				<select name="customoptions" size="8" multiple="multiple" style="width: 222px" id="moptselect">
					<c:forEach items="${customoptions}" var="customoption">
					<option value="${customoption.optionid}">${customoption.title}</option>
					</c:forEach>
				</select>
				<br />
				<a href="###" onclick="removeoption('moptselect')">[<bean:message key="del"/>]</a>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_threadtype_models_option_system"/></b></td>
			<td class="altbg2">
				<select name="coptselect" size="8" multiple="multiple" style="width: 222px" id="coptselect">
					<c:forEach items="${classoptions}" var="classoption">
					<option value="${classoption.optionid}">${classoption.title}</option>
					</c:forEach>
				</select>
				<br />
				<a href="###" onclick="copyoption('coptselect', 'moptselect')">[<bean:message key="a_forum_threadtype_models_option_copy"/>]</a>
			</td>
		</tr>
	</table>
	<br />
	<center><input class="button" type="submit" name="modeldetailsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:include page="../cp_footer.jsp" />