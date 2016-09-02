<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_plugin_edit" /></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=plugins&edit=${edit}">
	<a name="720c54e1ae73b07b"></a>
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_extends_plugins_settings" /> - ${plugin.name}<a href="###" onclick="collapse_change('720c54e1ae73b07b')"><img id="menuimg_720c54e1ae73b07b" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_720c54e1ae73b07b" style="display: yes">
			<c:set var="coloridcount" value="0" scope="page"/>
			<c:forEach items="${pluginvars}" var="pluginvar">
			<tr>
				<td width="45%" class="altbg1" ><b>${pluginvar.title}</b><br />${pluginvar.description}</td>
				<td class="altbg2">
					<c:choose>
						<c:when test="${pluginvar.type=='radio'}"><input class="radio" type="radio" name="${pluginvar.variable}" value="1" checked> <bean:message key="yes" /> &nbsp; &nbsp; <input class="radio" type="radio" name="${pluginvar.variable}" value="0" ${pluginvar.value>0?"":"checked"}> <bean:message key="no" /></c:when>
						<c:when test="${pluginvar.type=='color'}">
							<c:set var="coloridcount" value="${coloridcount+1}" scope="page"/>
							<input id="c${coloridcount}_v" type="text" size="50" value="${pluginvar.value}" name="${pluginvar.variable}" onchange="updatecolorpreview('c${coloridcount}')">
							<input id="c${coloridcount}" onclick="c${coloridcount}_frame.location='images/admincp/getcolor.htm?c${coloridcount}';showMenu('c${coloridcount}')" type="button" value="" style="width: 20px;background: ${pluginvar.value}"><span id="c${coloridcount}_menu" style="display: none" class="tableborder"><iframe name="c${coloridcount}_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
						</c:when>
						<c:when test="${pluginvar.type=='text'}"><input type="text" size="50" name="${pluginvar.variable}" value="${pluginvar.value}"></c:when>
						<c:when test="${pluginvar.type=='textarea'}"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('${pluginvar.variable}', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('${pluginvar.variable}', 0)"><br /><textarea  rows="6" name="${pluginvar.variable}" id="${pluginvar.variable}" cols="50">${pluginvar.value}</textarea></c:when>
						<c:otherwise>${pluginvar.type}</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
<br />
<script>
	function updatecolorpreview(obj) {
		var sp = $(obj + '_v').value.indexOf(' ');
		if(sp == -1) {
			var code = [$(obj + '_v').value];var codel = 1;
		} else {
			var code = [$(obj + '_v').value.substr(0, sp), $(obj + '_v').value.substr(sp + 1)];var codel = 2;
		}
		var css = '';
		for(i = 0;i < codel;i++) {
			if(code[i] != '') {
				if(code[i].substr(0, 1) == '#') {
					css += code[i] + ' ';
				} else {
					css += 'url("/' + code[i] + '") ';
				}
			}
		}
		$(obj).style.background = css;
	}
</script>
<center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit" />"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />