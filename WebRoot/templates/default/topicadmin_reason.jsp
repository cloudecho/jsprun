<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	var postSubmited = false;
	function ctlent(event) {
		if(postSubmited == false && (event.ctrlKey && event.keyCode == 13) || (event.altKey && event.keyCode == 83) && $('postsubmit')) {
			postSubmited = true;
			$('postsubmit').click();
			$('postsubmit').disabled = true;
		}
	}
</script>
<tr>
	<th valign="top"><label for="reason"><bean:message key="admin_reason" /></label></th>
	<td style="height: 9em;">
		<select name="selectreason" size="6" style="height: 100px; width: 8em" onchange="this.form.reason.value=this.value">
			<option value=""><bean:message key="custom" /></option>
			<option value="">--------</option>
			<c:forEach items="${valueObject.reasonList}" var="reason"><option value="${reason}">${empty reason ? '--------' : reason }</option></c:forEach>
		</select>
		<textarea id="reason" name="reason" style="height: 100px; width: 22em" onKeyDown="ctlent(event);"></textarea>
		<c:if test="${valueObject.necesseryInfo || reasons!=null}">
			<input type="hidden" name="isNecessary" value="isNecessary" />
			<div class="tips"><bean:message key="admin_reason_required" /></div>
		</c:if>
	</td>
</tr>
<tr>
	<th>&nbsp;</th>
	<td>
		<label>
		<c:choose>
		<c:when test="${valueObject.necessaryToSendMessage}" >
			<input type="checkbox" name="sendreasonpm" value="1" checked disabled/>
			<input type="hidden" name="sendreasonpm" value="1" >
		</c:when>
		<c:otherwise><input type="checkbox" name="sendreasonpm" value="1"/></c:otherwise>
		</c:choose>
		<bean:message key="admin_pm" />
		</label>
	</td>
</tr>