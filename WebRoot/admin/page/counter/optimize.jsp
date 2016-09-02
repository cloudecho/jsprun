<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_database_optimize" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr><td><bean:message key="a_system_database_optimize_tips" /></td></tr>
	</tbody>
</table>
<br />
<form name="optimize" method="post" action="admincp.jsp?action=optimize">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td><input class="checkbox" type="checkbox" name="chkall" class="header" onclick="checkall(this.form)" checked><bean:message key="a_system_database_optimize_opt" /></td><td><bean:message key="a_system_database_optimize_table_name" /></td><td><bean:message key="type" /></td><td><bean:message key="a_system_database_optimize_rows" /></td><td><bean:message key="value" /></td><td><bean:message key="a_system_database_optimize_index" /></td><td><bean:message key="a_system_database_optimize_frag" /></td></tr>
		<c:choose>
			<c:when test="${type=='optimize'}"><c:forEach items="${tableStatusVOs}" var="tableStatusVO"><tr><td class="altbg1" align="center"><c:choose><c:when test="${tableStatusVO.data_free>0}"><bean:message key="no" /></c:when><c:otherwise><bean:message key="yes" /></c:otherwise></c:choose></td><td class="altbg2" align="center">${tableStatusVO.name}</td><td class="altbg1" align="center">${tableStatusVO.engine}</td><td class="altbg2" align="center">${tableStatusVO.rows}</td><td class="altbg1" align="center">${tableStatusVO.data_length}</td><td class="altbg2" align="center">${tableStatusVO.index_length}</td><td class="altbg1" align="center">${tableStatusVO.data_free}</td></tr></c:forEach></c:when>
			<c:otherwise><c:forEach items="${tableStatusVOs}" var="tableStatusVO"><c:if test="${tableStatusVO.data_free>0}"><tr><td class="altbg1" align="center"><input class="checkbox" type="checkbox" name="optimizetables" value="${tableStatusVO.name}" checked></td><td class="altbg2" align="center">${tableStatusVO.name}</td><td class="altbg1" align="center">${tableStatusVO.engine}</td><td class="altbg2" align="center">${tableStatusVO.rows}</td><td class="altbg1" align="center">${tableStatusVO.data_length}</td><td class="altbg2" align="center">${tableStatusVO.index_length}</td><td class="altbg1" align="center">${tableStatusVO.data_free}</td></tr></c:if></c:forEach></c:otherwise>
		</c:choose>
		<tr><td colspan="7" align="right"><c:choose><c:when test="${totalsize!=''}"><bean:message key="size" /> ${totalsize}</c:when><c:otherwise><bean:message key="a_system_database_optimize_done" /></c:otherwise></c:choose></td></tr>
	</table>
	<c:if test="${type!='optimize'&&totalsize!=''}"><br /><center><input class="button" type="submit" name="optimizesubmit" value="<bean:message key="submit" />"></center></c:if>
</form>
<jsp:directive.include file="../cp_footer.jsp" />