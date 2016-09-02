<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<form method="post" action="pms.do?actions=deletepms&folder=${param.folder}">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<div class="mainbox">
		<h1><bean:message key="pm"/></h1>
		<jsp:include flush="true" page="pm_navbar.jsp" />
		<table summary="<bean:message key="pm_inbox"/>" cellspacing="0" cellpadding="0" id="pmlist">
			<thead><tr><td class="selector">&nbsp;</td><th><bean:message key="subject"/></th><td class="user">
			<c:choose>
				<c:when test="${param.folder=='outbox' || param.folder=='track'}"><bean:message key="to"/></c:when>
				<c:otherwise><bean:message key="location_from"/></c:otherwise>
			</c:choose>
			</td><td class="time"><bean:message key="time"/></td></tr></thead>
			<c:choose>
				<c:when test="${pmslist!=null || announcements!=null}">
					<c:forEach items="${announcements}" var="announcement">
						<tr id="pmrow_${announcement.id}">
							<td class="selector">&nbsp;&nbsp;</td>
							<td><a href="pm.jsp?action=view&amp;folder=announce&amp;pmid=${announcement.id}" onclick="showpm(event, this)" id="pm_view_${announcement.id}">${announcement.subject}</a></td>
							<td><bean:message key="a_other_ann_pms"/></td>
							<td><em><jrun:showTime timeInt="${announcement.starttime}" type="${dateformat}" timeoffset="${timeoffset}"/></em></td>
						</tr>
					</c:forEach>
					<c:forEach items="${pmslist}" var="pms">
						<tr id="pmrow_${pms.pmid}">
							<td class="selector"><input type="checkbox" name="delete[]" value="${pms.pmid}" /></td>
							<td <c:if test="${pms['new']>0}">style='font-weight:800'</c:if>>
							<c:choose>
								<c:when test="${param.folder=='outbox'}"><a href="pm.jsp?action=send&amp;folder=outbox&amp;pmid=${pms.pmid}">${pms.subject}</a></c:when>
								<c:when test="${param.folder=='track'}"><a href="pm.jsp?action=view&amp;folder=track&amp;pmid=${pms.pmid}" onclick="showpm(event, this)" id="pm_view_${pms.pmid}">${pms.subject}</a></c:when>
								<c:otherwise><a href="pm.jsp?action=view&amp;folder=inbox&amp;pmid=${pms.pmid}" onclick="showpm(event, this)" id="pm_view_${pms.pmid}">${pms.subject}</a></c:otherwise>
							</c:choose>
							</td>
							<td>
							<c:choose>
								<c:when test="${param.folder=='inbox' || param.folder==null}"><c:choose><c:when test="${pms.msgfromid>0}"><a href="space.jsp?uid=${pms.msgfromid}">${pms.msgfrom}</a></c:when><c:otherwise><bean:message key="pm_systemmessage"/></c:otherwise></c:choose></c:when>
								<c:otherwise><a href="space.jsp?uid=${pms.msgtoid}">${pms.username}</a></c:otherwise>
							</c:choose>
							</td>
							<td><em><jrun:showTime timeInt="${pms.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></em></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise><tr><td colspan="4"><bean:message key="search_nomatch"/></td></tr></c:otherwise>
			</c:choose>
		</table>
		<c:if test="${pmslist!=null}">
			<div class="footoperation">
				<label><input type="checkbox" id="chkall" name="chkall" onclick="checkall(this.form)" /> <bean:message key="select_all"/></label>
				<button type="submit" name="deletesubmit" value="true"><bean:message key="delete"/></button>
			</div>
		</c:if>
	</div>
</form>
<div class="notice"><bean:message key="pm_total"/>: <em id="pmtotalnum">${pmnum}</em> ,&nbsp; <bean:message key="pm_storage"/>: ${usergroups.maxpmnum}</div>
<c:if test="${!empty multi.multipage}"><div class="pages_btns">${multi.multipage}</div></c:if>
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