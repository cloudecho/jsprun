<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../cp_header.jsp" />
<script type="text/javascript">
function change(arears){
	$('arearsy').innerHTML = '<img id="dis" src="servlet/jree?arears=' + arears + '"/>';
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="a_extends_area_search" /></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /></a></div></td>
	</tr>
	<tbody id="menu_tip" style="display:">
		<tr>
			<td>
				<ul>
					<li><bean:message key="a_extends_area_search_explain1" /></li>
					<li><bean:message key="a_extends_area_search_explain2" /></li>
				</ul>
			</td>
		</tr>
	</tbody>
</table>
<br />
<br/>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="3"><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tp')"><bean:message key="a_extends_area_search_memberinfo" /></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tp')"><img id="menuimg_tp" src="images/admincp/menu_reduce.gif" border="0" /></a></div></td>
		</tr>
		<tbody id="menu_tp">
			<tr>
				<td><input class="radio" type="radio" name="arear" onclick="change('changjiang');"><bean:message key="a_extends_area_search_bychangjiang" /></td>
				<td><input class="radio" type="radio" name="arear" onclick="change('huanghe');"><bean:message key="a_extends_area_search_byhuanghe" /></td>
				<td><input class="radio" type="radio" name="arear" onclick="change('yunyin');"><bean:message key="a_extends_area_search_bybusinessman" /></td>
			</tr>
		</tbody>
	</table>
	<br />
	<center><div id="arearsy"></div></center>
<jsp:include page="../cp_footer.jsp" />