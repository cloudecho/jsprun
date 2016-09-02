<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<c:if test="${edit!='yes'}">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_profile"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<div style="float:left; margin-left:0px; padding-top:8px">
				<a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a>
			</div>
			<div style="float:right; margin-right:4px; padding-bottom:9px">
				<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a>
			</div>
		</td>
	</tr>
	<tbody id="menu_tip" style="display:">
		<tr>
			<td>
				<bean:message key="a_member_edit_profilefields_tips"/>
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=editprofilefields">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="48">
				<input class="checkbox" type="checkbox" onclick="checkall(this.form,'delid')" name="chkall"> <bean:message key="del"/>
			</td>
			<td>
				<bean:message key="fields_title"/>
			</td>
			<td>
				<bean:message key="available"/>
			</td>
			<td>
				<bean:message key="hidden"/>
			</td>
			<td>
				<bean:message key="unchangeable"/>
			</td>
			<td>
				<bean:message key="show_in_thread"/>
			</td>
			<td>
				<bean:message key="display_order"/>
			</td>
			<td>
				<bean:message key="edit"/>
			</td>
		</tr>
		<c:if test="${profileList != null}">
			<c:forEach items="${profileList}" var="profile" varStatus="index">
				<input type="hidden" name="fieldid" value="${profile.fieldid}" />
				<tr align="center">
					<td class="altbg1">
						<input class="checkbox" type="checkbox" name="delid" value="${profile.fieldid}">
					</td>
					<td class="altbg2">
						<input type="text" size="18" name="title${index.count}" value="${profile.title}" maxlength="50">
					<td class="altbg1">
						<input class="checkbox" type="checkbox" name="available${index.count}" value="1" ${profile.available==1?'checked' : ''}>
					</td>
					<td class="altbg2">
						<input class="checkbox" type="checkbox" name="invisible${index.count}" value="1" ${profile.invisible==1?'checked' : ''}>
					</td>
					<td class="altbg1">
						<input class="checkbox" type="checkbox" name="unchangeable${index.count}" value="1" ${profile.unchangeable==1?'checked' : ''}>
					</td>
					<td class="altbg2">
						<input class="checkbox" type="checkbox" name="showinthread${index.count}" value="1" ${profile.showinthread==1?'checked' : ''}>
					</td>
					<td class="altbg1">
						<input type="text" size="2" name="displayorder${index.count}" value="${profile.displayorder}" maxlength="5">
					</td>
					<td class="altbg2">
						<a href="admincp.jsp?action=editprofilefields&edit=${profile.fieldid}">[<bean:message key="detail"/>]</a>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr align="center" class="altbg1">
			<td> <bean:message key="add_new"/> </td>
			<td> <input type='text' name="newtitle" size="18" maxlength="50"> </td>
			<td colspan="6"> &nbsp; </td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="fieldsubmit" value="<bean:message key="submit"/>">
	</center>
</form>
</c:if>
<c:if test="${edit=='yes'}">
	<form method="post" action="admincp.jsp?action=editprofilefields&submit=yes">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<input type="hidden" name="fieldid" value="${profile.fieldid}" />
		<a name="66186b0d43968083"></a>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td colspan="2">
					<bean:message key="fields_edit"/> - ${profile.title}
					<a href="###" onclick="collapse_change('66186b0d43968083')"><img id="menuimg_66186b0d43968083" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" />
					</a>
				</td>
			</tr>
			<tbody id="menu_66186b0d43968083" style="display: yes">
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="fields_edit_title"/></b>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="title" value="${profile.title}" maxlength="50">
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="fields_edit_desc"/></b>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="description"
							value="${profile.description}" maxlength="255">
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="fields_edit_size"/></b>
						<br />
						<span class="smalltxt"><bean:message key="fields_edit_size_comment"/></span>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="size" value="${profile.size}" maxlength="3">
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="fields_edit_invisible"/></b>
						<br />
						<span class="smalltxt"><bean:message key="fields_edit_invisible_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="invisible" value="1" ${profile.invisible==1?'checked' : ''}>
						<bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="invisible" value="0" ${profile.invisible==0?'checked' : ''}>
						<bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="fields_edit_required"/></b>
						<br />
						<span class="smalltxt"><bean:message key="fields_edit_required_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="required" value="1" ${profile.required==1?'checked' : ''}>
						<bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="required" value="0" ${profile.required==0?'checked' : ''}>
						<bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="fields_edit_unchangeable"/></b>
						<br />
						<span class="smalltxt"><bean:message key="fields_edit_unchangeable_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="unchangeable" value="1" ${profile.unchangeable==1?'checked' : ''}>
						<bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="unchangeable" value="0" ${profile.unchangeable==0?'checked' : ''}>
						<bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="show_in_thread"/>:</b>
						<br />
						<span class="smalltxt"><bean:message key="fields_edit_show_in_thread_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="showinthread" value="1" ${profile.showinthread==1?'checked' : ''}>
						<bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="showinthread" value="0" ${profile.showinthread==0?'checked' : ''}>
						<bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="fields_edit_selective"/></b>
						<br />
						<span class="smalltxt"><bean:message key="fields_edit_selective_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="selective" value="1" ${profile.selective==1?'checked' : ''}>
						<bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="selective" value="0" ${profile.selective==0?'checked' : ''}>
						<bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1" valign="top">
						<b><bean:message key="fields_edit_choices"/></b>
						<br />
						<span class="smalltxt"><bean:message key="fields_edit_choices_comment"/></span>
					</td>
					<td class="altbg2">
						<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('choicesnew', 1)">
						<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('choicesnew', 0)"> <br />
						<textarea rows="6" name="choices" id="choicesnew" cols="50" type="_moz">${profile.choices}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<br />
		<center>
			<input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>">
		</center>
	</form>
</c:if>
<jsp:directive.include file="../cp_footer.jsp" />