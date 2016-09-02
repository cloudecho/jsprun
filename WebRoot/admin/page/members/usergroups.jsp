<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<c:if test="${edit!='yes'}">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_usergroups"/></td></tr>
</table><br />
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
					<bean:message key="usergroups_tips"/>
				</td>
			</tr>
		</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=usergroups&type=member">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="8">
				<bean:message key="usergroups_member"/> - <bean:message key="usergroups_detail"/>
			</td>
		</tr>
		<tr class="category" align="center">
			<td width="48">
				<input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form)">
				<bean:message key="del"/>
			</td>
			<td>
				<bean:message key="usergroups_title"/>
			</td>
			<td>
				<bean:message key="a_member_creditshigher"/>
			</td>
			<td>
				<bean:message key="a_member_creditslower"/>
			</td>
			<td>
				<bean:message key="stars"/>
			</td>
			<td>
				<bean:message key="title_color"/>
			</td>
			<td>
				<bean:message key="usergroups_avatar"/>
			</td>
			<td>
				<bean:message key="edit"/>
			</td>
		</tr>
			<c:forEach items="${memberusergoups}" var="userGroup">
					<input type="hidden" name="updateid" value="${userGroup.groupid}" />
					<tr align="center">
						<td class="altbg1">
							<input class="checkbox" type="checkbox" name="delid" value="${userGroup.groupid}">
						</td>
						<td class="altbg2">
							<input type="text" size="12" name="grouptitle" value="${userGroup.grouptitle}" maxlength="30">
						</td>
						<td class="altbg1">
							<input type="text" size="6" name="creditshigher" value="${userGroup.creditshigher}">
						<td class="altbg2">
							${userGroup.creditslower}
						<td class="altbg1">
							<input type="text" size="2" name="stars" value="${userGroup.stars}" maxlength="2">
						</td>
						<td class="altbg2">
							<input type="text" size="6" name="color" value="${userGroup.color}" maxlength="7">
						</td>
						<td class="altbg1">
							<input type="text" size="12" name="groupavatar" value="${userGroup.groupavatar}" maxlength="60">
						</td>
						<td class="altbg2" nowrap>
							<a href="admincp.jsp?action=tousergroupinfo&edit=${userGroup.groupid}">[<bean:message key="detail"/>]</a>
						</td>
					</tr>
			</c:forEach>
		<tbody id="addnewusergroup">
			<tr align="center" class="altbg1">
				<td>
					<bean:message key="add_new"/>
					<a  href="###" onclick="newnode = $('addnewusergroup2tr').cloneNode(true); $('addnewusergroup').appendChild(newnode)">[+]</a>
				</td>
				<td>
					<input type="text" size="12" name="newgrouptitle" maxlength="30">
				</td>
				<td>
					<input type="text" size="6" name="newcreditshigher">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					<input type="text" size="2" name="newstars" maxlength="2">
				</td>
				<td align="right">
					<bean:message key="usergroups_scheme"/>
				</td>
				<td colspan="3">
					<select name="projectid" style="vertical-align: middle;">
						<option value="0" selected>
							<bean:message key="none"/>
						</option>
					<c:forEach items="${projectslist}" var="project">
						<option value="${project.id}">${project.name}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
		</tbody>
		<tbody id="addnewusergroup2" style="display: none;">
			<tr align="center" class="altbg1" id="addnewusergroup2tr">
				<td>
				</td>
				<td>
					<input type="text" size="12" name="newgrouptitle" maxlength="30">
				</td>
				<td>
					<input type="text" size="6" name="newcreditshigher">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					<input type="text" size="2" name="newstars" maxlength="2">
				</td>
				<td align="right">
					<bean:message key="usergroups_scheme"/>
				</td>
				<td colspan="3">
					<select name="projectid" style="vertical-align: middle;">
						<option value="0" selected>
							<bean:message key="none"/>
						</option>
					<c:forEach items="${projectslist}" var="project">
						<option value="${project.id}">${project.name}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="groupsubmit" value="<bean:message key="submit"/>">
		&nbsp;
	</center>
</form>
<br />

<form method="post" action="admincp.jsp?action=usergroups&type=special">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="7">
				<bean:message key="usergroups_special"/> - <bean:message key="usergroups_detail"/>
			</td>
		</tr>
		<tr class="category" align="center">
			<td width="48">
				<input class="checkbox" type="checkbox" name="chkall" class="category" onclick="checkall(this.form)">
				<bean:message key="del"/>
			</td>
			<td nowrap width="20%">
				<bean:message key="usergroups_title"/>
			</td>
			<td nowrap width="20%">
				<bean:message key="stars"/>
			</td>
			<td nowrap width="15%">
				<bean:message key="title_color"/>
			</td>
			<td nowrap width="15%">
				<bean:message key="usergroups_avatar"/>
			</td>
			<td nowrap width="10%">
				<bean:message key="view"/>
			</td>
			<td nowrap width="10%">
				<bean:message key="edit"/>
			</td>
		</tr>
			<c:forEach items="${spaciallist}" var="userGroup">
					<input type="hidden" name="updateid" value="${userGroup.groupid}" />
					<tr align="center" valign="top">
						<td class="altbg1">
							<input class="checkbox" type="checkbox" name="delid" value="${userGroup.groupid}">
						</td>
						<td class="altbg2">
							<input type="text" size="12" name="grouptitle" value="${userGroup.grouptitle}" maxlength="30">
						</td>
						<td class="altbg1">
							<input type="text" size="2" name="stars" value="${userGroup.stars}" maxlength="2">
						</td>
						<td class="altbg2">
							<input type="text" size="6" name="color" value="${userGroup.color}" maxlength="7">
						</td>
						<td class="altbg1">
							<input type="text" size="12" name="groupavatar" value="${userGroup.groupavatar}" maxlength="60">
						</td>
						<td class="altbg2" nowrap>
							<a href="admincp.jsp?action=usergroups&sgroupid=${userGroup.groupid}&do=viewsgroup" onclick="ajaxget(this.href, 'sgroup_${userGroup.groupid}', 'sgroup_${userGroup.groupid}','auto');doane(event);">[<bean:message key="view"/>]</a>
						</td>
						<td class="altbg1" nowrap>
							<a href="admincp.jsp?action=tousergroupinfo&edit=${userGroup.groupid}">[<bean:message key="detail"/>]</a>
						</td>
					</tr>
					<tr>
						<td colspan="7" id="sgroup_${userGroup.groupid}" style="display: none"></td>
					</tr>
			</c:forEach>
		<tbody id="addnewspecialgroup">
			<tr class="altbg1" align="center">
				<td>
					<bean:message key="add_new"/>
					<a href="###" onclick="newnode = $('addnewspecialgroup2tr').cloneNode(true); $('addnewspecialgroup').appendChild(newnode)">[+]</a>
				</td>
				<td>
					<input type="text" size="12" name="newgrouptitle" maxlength="30">
				</td>
				<td>
					<input type="text" size="2" name="newstars" maxlength="2">
				</td>
				<td>
					<input type="text" size="6" name="newcolor" maxlength="7">
				</td>
				<td>
					<input type="text" size="12" name="newgroupavatar" maxlength="60">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</tbody>
		<tbody id="addnewspecialgroup2" style="display: none;">
			<tr align="center" class="altbg1" id="addnewspecialgroup2tr">
				<td>
					&nbsp;
				</td>
				<td>
					<input type="text" size="12" name="newgrouptitle" maxlength="30">
				</td>
				<td>
					<input type="text" size="2" name="newstars" maxlength="2">
				</td>
				<td>
					<input type="text" size="6" name="newcolor" maxlength="7">
				</td>
				<td>
					<input type="text" size="12" name="newgroupavatar" maxlength="60">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="groupsubmit" value="<bean:message key="submit"/>">
	</center>
</form>
<br />
<form method="post" action="admincp.jsp?action=usergroups&type=system">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="6">
				<bean:message key="usergroups_system"/> - <bean:message key="usergroups_detail"/>
			</td>
		</tr>
		<tr class="category" align="center">
			<td>
				<bean:message key="usergroups_title"/>
			</td>
			<td>
				<bean:message key="usergroups_status"/>
			</td>
			<td>
				<bean:message key="stars"/>
			</td>
			<td>
				<bean:message key="title_color"/>
			<td>
				<bean:message key="usergroups_avatar"/>
			</td>
			<td>
				<bean:message key="edit"/>
			</td>
		</tr>
			<c:forEach items="${systemlist}" var="userGroup">
					<input type="hidden" name="updateid" value="${userGroup.groupid}" />
					<tr align="center">
						<td class="altbg2">
							<input type="text" size="12" name="grouptitle" value="${userGroup.grouptitle}" maxlength="30">
						</td>
						<td class="altbg1">
							${userGroup.grouptitle}
						</td>
						<td class="altbg2">
							<input type="text" size="2" name="stars" value="${userGroup.stars}" maxlength="2">
						</td>
						<td class="altbg1">
							<input type="text" size="6" name="color" value="${userGroup.color}" maxlength="7">
						</td>
						<td class="altbg2">
							<input type="text" size="12" name="groupavatar" value="${userGroup.groupavatar}" maxlength="60">
						</td>
						<td class="altbg1" nowrap>
							<a href="admincp.jsp?action=tousergroupinfo&edit=${userGroup.groupid}">[<bean:message key="detail"/>]</a>
						</td>
					</tr>
			</c:forEach>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="groupsubmit" value="<bean:message key="submit"/>">
	</center>
</form>
</c:if>
<jsp:directive.include file="../cp_footer.jsp" />