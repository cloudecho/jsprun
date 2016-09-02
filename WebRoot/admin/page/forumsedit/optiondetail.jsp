<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="a_forum_threadtype_infotypes_option"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=optiondetail&optionid=${typeoption.optionid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_forum_threadtype_infotypes_option_config"/><a href="###" onclick="collapse_change('6e6cacb7ef77694a')"><img id="menuimg_6e6cacb7ef77694a" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_6e6cacb7ef77694a" style="display: yes">
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="name"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="title" value="<jrun:dhtmlspecialchars value="${typeoption.title}"/>" maxlength="100"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="a_forum_threadtype_variable"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="identifier" value="${typeoption.identifier}" maxlength="40"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="type"/></b></td>
			<td class="altbg2">
				<select id="type" name="type" onchange="var styles, key;styles=new Array('number','text','radio', 'checkbox', 'textarea', 'select', 'image'); for(key in styles) {var obj=$('style_'+styles[key]); obj.style.display=styles[key]==this.options[this.selectedIndex].value?'':'none';}">
					<option value="number"><bean:message key="a_forum_threadtype_edit_number"/></option>
					<option value="text"><bean:message key="a_forum_threadtype_edit_text"/></option>
					<option value="radio"><bean:message key="a_forum_threadtype_edit_radio"/></option>
					<option value="checkbox"><bean:message key="a_forum_threadtype_edit_checkbox"/></option>
					<option value="textarea"><bean:message key="a_forum_threadtype_edit_textarea"/></option>
					<option value="select"><bean:message key="a_forum_threadtype_edit_select"/></option>
					<option value="calendar"><bean:message key="a_forum_threadtype_edit_calendar"/></option>
					<option value="email"><bean:message key="a_forum_threadtype_edit_email"/></option>
					<option value="url"><bean:message key="a_forum_threadtype_edit_url"/></option>
					<option value="image"><bean:message key="a_forum_threadtype_edit_image"/></option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" valign="top"><b><bean:message key="fields_edit_desc"/></b></td>
			<td class="altbg2">
				<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 1)">
				<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('description', 0)">
				<br />
				<textarea rows="6" name="description" id="description" cols="50" onKeyDown="if (this.value.length>=255){event.returnValue=false}">${typeoption.description}</textarea>
			</td>
		</tr>
		</tbody>
	</table>
	<br />
	<div id="style_number" style="display: none" class="maintablediv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_forum_threadtype_edit_number"/></td></tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="fields_edit_maxnum"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="rules[number][maxnum]" value="${rulesMap.maxnum}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="fields_edit_minnum"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="rules[number][minnum]" value="${rulesMap.minnum}"></td>
			</tr>
		</table>
		<br />
	</div>
	<div id="style_text" style="display: none" class="maintablediv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_forum_threadtype_edit_text"/></td></tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="fields_edit_textmax"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="rules[text][maxlength]" value="${rulesMap.maxlength}"></td>
			</tr>
		</table>
		<br />
	</div>
	<div id="style_textarea" style="display: none" class="maintablediv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_forum_threadtype_edit_textarea"/></td></tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="fields_edit_textmax"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="rules[textarea][maxlength]" value="${rulesMap.maxlength}"></td>
			</tr>
		</table>
		<br />
	</div>
	<div id="style_select" style="display: none" class="maintablediv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_forum_threadtype_edit_select"/></td></tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="fields_edit_choices"/></b>
					<br />
					<span class="smalltxt"><bean:message key="fields_edit_choices_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('rules[select][choices]', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('rules[select][choices]', 0)">
					<br />
					<textarea rows="6" name="rules[select][choices]" id="rules[select][choices]" cols="50">${rulesMap.choices}</textarea>
				</td>
			</tr>
		</table>
		<br />
	</div>
	<div id="style_radio" style="display: none" class="maintablediv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_forum_threadtype_edit_radio"/></td></tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="fields_edit_choices"/></b>
					<br />
					<span class="smalltxt"><bean:message key="fields_edit_choices_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('rules[radio][choices]', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('rules[radio][choices]', 0)">
					<br />
					<textarea rows="6" name="rules[radio][choices]" id="rules[radio][choices]" cols="50">${rulesMap.choices}</textarea>
				</td>
			</tr>
		</table>
		<br />
	</div>
	<div id="style_checkbox" style="display: none" class="maintablediv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_forum_threadtype_edit_checkbox"/></td></tr>
			<tr>
				<td width="45%" class="altbg1" valign="top">
					<b><bean:message key="fields_edit_choices"/></b>
					<br />
					<span class="smalltxt"><bean:message key="fields_edit_choices_comment"/></span>
				</td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('rules[checkbox][choices]', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('rules[checkbox][choices]', 0)">
					<br />
					<textarea rows="6" name="rules[checkbox][choices]" id="rules[checkbox][choices]" cols="50">${rulesMap.choices}</textarea>
				</td>
			</tr>
		</table>
		<br />
	</div>
	<div id="style_image" style="display: none" class="maintablediv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_forum_threadtype_edit_image"/></td></tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="fields_edit_images_weight"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="rules[image][maxwidth]" value="${rulesMap.maxwidth}"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="fields_edit_images_height"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="rules[image][maxheight]" value="${rulesMap.maxheight}"></td>
			</tr>
		</table>
	</div>
	<center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>"></center>
</form>
<script language="javascript" type="text/javascript">
	var selected=document.getElementById("type");
	selected.value="${typeoption.type}";
	var obj=$('style_'+selected.value);
	if(obj!=null){
		obj.style.display="";
	}
</script>
<jsp:include page="../cp_footer.jsp" />