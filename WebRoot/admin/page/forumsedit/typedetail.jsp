<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_forum_threadtypes"/></td></tr>
</table>
<br />
<form method="post"	action="admincp.jsp?action=typedetail&typeid=${threadtype.typeid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_forum_threadtype_models"/></td></tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_threadtype_models_select"/></b><br /><span class="smalltxt"><bean:message key="a_forum_threadtype_models_select_comment"/></span></td>
			<td class="altbg2"><select name="modelid" onchange="window.location=('admincp.jsp?action=typedetail&typeid=${threadtype.typeid}&modelid='+this.value+'')"><option value="0"><bean:message key="none"/></option>${typemodelopt}</select></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_threadtype_infotypes_validity"/></b><br /><span class="smalltxt"><bean:message key="a_forum_threadtype_infotypes_validity_comment"/></span></td>
			<td class="altbg2"><input type="radio" class="radio" name="expiration" value="1" ${threadtype.expiration==1?"checked":""}><bean:message key="yes"/> &nbsp; &nbsp; <input type="radio" class="radio" name="expiration" value="0" ${threadtype.expiration==0?"checked":""}><bean:message key="no"/></td>
		</tr>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td>${threadtype.name} - <bean:message key="a_forum_threadtype_infotypes_add_option"/></td></tr>
		<tr class="altbg1" align="center"><td id="classlist"></td></tr>
		<tr class="altbg1"><td id="optionlist"></td></tr>
		<tr>
			<td width="100%">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder" id="typelist">
					<tr class="header"><td colspan="10">${threadtype.name} - <bean:message key="a_forum_threadtype_infotypes_exist_option"/></td></tr>
					<tr class="category" align="center">
						<td width="10%"><input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form,'delete')"><bean:message key="del"/></td>
						<td width="10%"><bean:message key="name"/></td>
						<td width="15%"><bean:message key="type"/></td>
						<td width="8%"><bean:message key="available"/></td>
						<td width="8%"><bean:message key="required"/></td>
						<td width="8%"><bean:message key="unchangeable"/></td>
						<td width="8%"><bean:message key="a_forum_threadtype_infotypes_search"/></td>
						<td width="10%"><bean:message key="display_order"/></td>
						<td width="10%"><bean:message key="a_forum_threadtype_infotypes_add_template"/></td>
						<td width="10%"><bean:message key="edit"/></td>
					</tr>
				<c:forEach var="option" items="${typeoptions}">
					<tr>
						<td colspan="10" style="border: 0px; padding: 0px;" id="optionid${option.optionid}">
							<table width="100%" cellspacing="0" cellpadding="0" style="margin:0px;">
								<tr align="center">
									<td class="altbg1" width="10%"><input class="checkbox" type="checkbox" name="delete" value="${option.optionid}" ${option.model>0?"disabled":""}><input type="hidden" name="allselect" value="${option.optionid}"></td>
									<td class="altbg2" width="10%">${option.title}</td>
									<td class="altbg1" width="15%">${types[option.type]}</td>
									<td class="altbg2" width="8%"><input class="checkbox" type="checkbox" name="available[${option.optionid}]" value="1" ${option.available>0?"checked":""} ${option.model>0?"disabled":""}></td>
									<td class="altbg1" width="8%"><input class="checkbox" type="checkbox" name="required[${option.optionid}]" value="1" ${option.required>0?"checked":""} ${option.model>0?"disabled":""}></td>
									<td class="altbg2" width="8%"><input class="checkbox" type="checkbox" name="unchangeable[${option.optionid}]" value="1" ${option.unchangeable>0?"checked":""}></td>
									<td class="altbg1" width="8%"><input class="checkbox" type="checkbox" name="search[${option.optionid}]" value="1" ${option.search>0?"checked":""}></td>
									<td class="altbg2" width="10%"><input type="text" size="2" name="displayorder[${option.optionid}]" value="${option.displayorder}"></td>
									<td class="altbg1" width="10%"><a href="###" onclick="insertvar('${option.identifier}');doane(event);return false;">[<bean:message key="a_forum_threadtype_infotypes_add_template"/>]</a></td>
									<td class="altbg2" width="10%"><a href="admincp.jsp?action=optiondetail&optionid=${option.optionid}">[<bean:message key="edit"/>]</a></td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	<center><input class="button" type="submit" name="typedetailsubmit" value="<bean:message key="submit"/>">&nbsp;&nbsp;&nbsp;</center>
	<a name="template"><br />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td>${threadtype.name} - <bean:message key="a_forum_threadtype_infotypes_template"/></td></tr>
			<tr class="altbg1">
				<td>
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('typetemplate', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('typetemplate', 0)"><br />
					<textarea cols="100" rows="5" id="typetemplate" name="typetemplate" style="width: 95%;">${threadtype.template}</textarea>
					<br /><b><bean:message key="a_forum_threadtype_infotypes_template"/></b><br />
					<bean:message key="a_forum_threadtype_infotypes_template_tips"/>
					<c:if test="${!empty previewtemplate}"><br /><fieldset style="padding: 1em; margin: 1em;"><legend><b><bean:message key="template_preview"/></b></legend>${previewtemplate}</fieldset></c:if>
				</td>
			</tr>
		</table><br /> 
	</a>
	<center>
		<input class="button" type="submit" name="typedetailsubmit" value="<bean:message key="submit"/>">&nbsp;&nbsp;&nbsp;
		<input class="button" type="submit" name="typepreviewsubmit" value="<bean:message key="template_preview"/>">
	</center>
</form>
<script type="text/javascript">
var optionids = new Array();
${jsoptionids}
function insertvar(text) {
	$('typetemplate').focus();
	selection = document.selection;
	if(selection && selection.createRange) {
		var sel = selection.createRange();
		sel.text = '<li><b>{' + text + '}</b>: [' + text + "value]</li>\r\n";
		sel.moveStart('character', -strlen(text));
	} else {
		$('typetemplate').value += '<li><b>{' + text + '}<b>: [' + text + "value]</li>\r\n";
	}
}
function checkedbox() {
	var tags = $('optionlist').getElementsByTagName('input');
	for(var i=0; i<tags.length; i++) {
		if(in_array(tags[i].value, optionids)) {
			tags[i].checked = true;
		}
	}
}
function insertoption(optionid) {
	var x = new Ajax();
	x.optionid = optionid;
	x.get('admincp.jsp?action=typedetail&operation=typelist&inajax=1&optionid=' + optionid, function(s, x) {
		if(!in_array(x.optionid, optionids)) {
			var otr = $('typelist').insertRow(-1);
			var otd = otr.insertCell(-1);
			otd.colSpan = 10;
						
			otd.id = 'optionid' + optionid;
			otd.style.border = '0px';
			otd.style.padding = '0px';
			otd.style.margin = '0px';
			otd.style.width="100%";
			otd.innerHTML = '<TABLE width="100%" cellspacing="0" cellpadding="0" style="margin: 0px;">' + s + '</TABLE>';
			optionids.push(x.optionid);
		} else {
			if(is_ie){
				$('optionid' + x.optionid).parentNode.removeNode(true);
			} else {
				$('optionid' + x.optionid).parentNode.removeChild($('optionid' + x.optionid));
			}
			for(var i=0; i<optionids.length; i++) {
				if(optionids[i] == x.optionid) {
					optionids[i] = 0;
				}
			}
		}
	});
}
</script>
<script type="text/javascript">ajaxget('admincp.jsp?action=typedetail&operation=classlist&typeid=${threadtype.typeid}', 'classlist');</script>
<script type="text/javascript">ajaxget('admincp.jsp?action=typedetail&operation=optionlist&typeid=${threadtype.typeid}&classid=1', 'optionlist', 'optionlist', 'Loading...', '', checkedbox);</script>
<jsp:include page="../cp_footer.jsp" />