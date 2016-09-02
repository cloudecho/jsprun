<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<div class="box" id="pmprompt">
	<c:if test="${user.pmsound>0}"><bgsound src="images/sound/pm_${user.pmsound}.wav" /></c:if>
	<span class="headactions"> <a href="pm.jsp" target="_blank"><bean:message key="pm_new_detail"/></a><c:if test="${user.newpm>0}"><a href="pm.jsp?action=noprompt" onclick="ajaxget(this.href+'&rand='+Math.random(), 'pmprompt', null, null, 'none');doane(event);"><bean:message key="pm_new_ignore"/></a></c:if></span>
	<h4><bean:message key="pm_new_youhave"/><c:if test="${newpmnum>0}"><bean:message key="pm_new" arg0="${newpmnum}"/>&nbsp;</c:if><c:if test="${announcepm>0}"><bean:message key="pm_newannounce" arg0="${announcepm}"/></c:if></h4>
	<table summary="New PM" cellspacing="0" cellpadding="5">
		<c:if test="${pmlists!=null}">
			<c:forEach items="${pmlists}" var="pm">
			<tbody id="pmrow_${pm.pmid}">
				<tr>
					<td width="13%" nowrap valign="top">
						<span class="bold"><bean:message key="location_from"/>: </span>
						<c:choose>
							<c:when test="${pm.announce!=null}"><bean:message key="a_other_ann_pms"/></c:when>
							<c:when test="${pm.msgfromid>0}"><a href="space.jsp?uid=${pm.msgfromid}">${pm.msgfrom}</a></c:when>
							<c:otherwise><bean:message key="pm_systemmessage"/></c:otherwise>
						</c:choose>
					</td>
					<td>
						<span class="bold" nowrap><bean:message key="subject"/>:</span>
						<c:choose>
							<c:when test="${pm.announce!=null}"><a href="pm.jsp?action=view&amp;folder=announce&amp;pmid=${pm.id}" onclick="showpm(event, this)" id="pm_view_${pm.id}">${pm.subject}</a></c:when>
							<c:otherwise><a href="pm.jsp?action=view&amp;folder=inbox&amp;pmid=${pm.pmid}&rand='"+Math.random() onclick="showpm(event, this)" id="pm_view_${pm.pmid}">${pm.subject}</a></c:otherwise>
						</c:choose>
					</td>
				</tr>
			</tbody>
			</c:forEach>
		</c:if>
	</table>
</div>

<script type="text/javascript">
	lastpmid = null;
	function hidelastpm(pmid) {
		if(lastpmid && lastpmid != pmid) {
			changedisplay($('pm_'+lastpmid), 'none');
		}
		lastpmid = pmid;
	}
</script>
<script type="text/javascript">
	var prepmdiv = '';
	function showpm(event, obj) {
		var url = obj.href + '&inajax=1&rand='+Math.random();
		var currpmdiv = obj.id + '_div';
		if(!$(currpmdiv)) {
			var x = new Ajax();
			x.get(url, function(s) {
				evalscript(s);
				//debug 确定表格和当前所在行，插入行，列。
				var table1 = obj.parentNode.parentNode.parentNode.parentNode;
				var row1 = table1.insertRow(obj.parentNode.parentNode.rowIndex + 1);
				row1.id = currpmdiv;
				row1.className = 'row';
				var cell1 = row1.insertCell(0);
				cell1.innerHTML = '&nbsp;';
				cell1.className = 'pmmessage';
				var cell2 = row1.insertCell(1);
				cell2.colSpan = '3';
				cell2.innerHTML = s;
				cell2.className = 'pmmessage';
				if(prepmdiv) {
					$(prepmdiv).style.display = 'none';
				}
				changestatus(obj);
				prepmdiv = currpmdiv;
			})
		} else {
			if($(currpmdiv).style.display == 'none') {
				$(currpmdiv).style.display = '';
				changestatus(obj);
				if(prepmdiv) {
					$(prepmdiv).style.display = 'none';
				}
				prepmdiv = currpmdiv;
			} else {
				$(currpmdiv).style.display = 'none';
				prepmdiv = '';
			}
		}
		doane(event);
	}
</script>