<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<script type="text/javascript">
	function checkalloverwrite(form, prefix, checkall) {
		var checkall = checkall ? checkall : 'chkall';
		for(var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];
			if(e.name && e.name != checkall && (!prefix || (prefix && e.name==prefix))) {
				e.checked = form.elements[checkall].checked;
			}
		}
	}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_magic"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
		<tr>
			<td>
				<ul>
					<li><bean:message key="a_other_magics_edit_tips1"/></li>
					<li><bean:message key="a_other_magics_edit_tips2"/></li>
				</ul>
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=magicedit&magicid=${magicBean.magicid }">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="ed07009919904a7f"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header"><td colspan="2"><bean:message key="a_other_magics_edit"/> - ${magicBean.name }<a href="###" onclick="collapse_change('ed07009919904a7f')"><img id="menuimg_ed07009919904a7f" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
		<tbody id="menu_ed07009919904a7f" style="display: yes">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_other_magics_edit_name"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="newname" value="${magicBean.name }" maxlength="50"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_other_magics_edit_identifier"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="newidentifier" value="${magicBean.identifier }" maxlength="40"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_other_magics_edit_type"/></b></td>
				<td class="altbg2">
					<select name="newtype">
						<option value="1" ${magicBean.type == 1?"selected":""}><bean:message key="magics_type_1"/></option>
						<option value="2" ${magicBean.type == 2?"selected":""}><bean:message key="magics_type_2"/></option>
						<option value="3" ${magicBean.type == 3?"selected":""}><bean:message key="a_other_magics_type_3"/></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_other_magics_edit_price"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="newprice" value="${magicBean.price }"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_other_magics_edit_num"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="newnum" value="${magicBean.num }"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="a_other_magics_edit_weight"/></b></td>
				<td class="altbg2"><input type="text" size="50" name="newweight" value="${magicBean.weight }"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_other_magics_edit_supplytype"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_magics_edit_supplytype_comment"/></span>
				</td>
				<td class="altbg2">
					<input name="newsupplytype" type="radio" class="radio" value="0" ${magicBean.supplytype==0?"checked":""}>&nbsp;<bean:message key="a_other_magics_goods_stack_none"/><br />
					<input name="newsupplytype" type="radio" class="radio" value="1" ${magicBean.supplytype==1?"checked":""}>&nbsp;<bean:message key="a_other_magics_goods_stack_day"/><br />
					<input name="newsupplytype" type="radio" class="radio" value="2" ${magicBean.supplytype==2?"checked":""}>&nbsp;<bean:message key="a_other_magics_goods_stack_week"/><br />
					<input name="newsupplytype" type="radio" class="radio" value="3" ${magicBean.supplytype==3?"checked":""}>&nbsp;<bean:message key="a_other_magics_goods_stack_month"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_other_magics_edit_supplynum"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_magics_edit_supplynum_comment"/></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="newsupplynum" value="${magicBean.supplynum }"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="a_other_magics_edit_filename"/></b>
					<br />
					<span class="smalltxt"><bean:message key="a_other_magics_edit_filename_comment"/></span>
				</td>
				<td class="altbg2"><input type="text" size="50" name="newfilename" value="${magicBean.filename }"></td>
			</tr>
			<tr>
				<td width="45%" class="altbg1" valign="top"><b><bean:message key="a_other_magics_edit_description"/></b></td>
				<td class="altbg2">
					<img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('descriptionnew', 1)">
					<img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('descriptionnew', 0)"><br />
					<textarea rows="6" name="newdescription" id="descriptionnew" cols="50" onKeyDown='if (this.value.length>=255){event.returnValue=false}'>${magicBean.description }</textarea>
				</td>
			</tr>
		</table>
		<br />
		<a name="69afa6de60600034"></a>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header"><td colspan="2"><bean:message key="a_other_magics_edit_perm"/><a href="###" onclick="collapse_change('69afa6de60600034')"><img id="menuimg_69afa6de60600034" src="images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td></tr>
			<tbody id="menu_69afa6de60600034" style="display: yes">
				<tr>
					<td width="15%" class="altbg1" valign="top"><b><br /><bean:message key="a_other_magics_edit_usergroupperm"/><br /><input class="checkbox" type="checkbox" name="chkall1" onclick="checkalloverwrite(this.form, 'usergroupsperm', 'chkall1')"> <span><bean:message key="select_all"/></span><br /></b></td>
					<td class="altbg2">
						<table cellspacing="0" cellpadding="0" border="0" width="100%" align="center">
						<c:forEach items="${userGroupList}" var="usergroup" varStatus="status">
							<c:if test="${status.count%4==1}"><tr></c:if>
							<td style="border:0px"><input type="checkbox" class="checkbox" name="usergroupsperm" value="${usergroup.groupid }" ${usergroupsperm[usergroup.groupid]!=null?"checked":""}> ${usergroup.grouptitle}</td>
							<c:if test="${(status.count)%4==0}"></tr></c:if>
						</c:forEach>
						</table>
					</td>
				</tr>
				<c:choose>
					<c:when test="${magicBean.type == 1}">
						<tr>
							<td width="15%" class="altbg1" valign="top"><b><br /><bean:message key="a_other_magics_edit_forumperm"/><br /><input class="checkbox" type="checkbox" name="chkall3" onclick="checkall(this.form, 'forumperm', 'chkall3')"> <span><bean:message key="select_all"/></span><br /><br /></b></td>
							<td class="altbg2">
								<table cellspacing="0" cellpadding="0" border="0" width="100%" align="center">
									<tr>
										<c:forEach items="${forumList}" var="forum" varStatus="indexSign">
											<td style="border:0px"><input type="checkbox" class="checkbox" name="forumperm[]" value="${forum.fid }" ${forumperm[forum.fid]!=null?"checked":"" }> ${forum.name}</td>
											<c:if test="${indexSign.count%4==0}">
											</tr>
											<tr>
											</c:if>
										</c:forEach>
									</tr>
								</table>
							</td>
						</tr>
					</c:when>
				<c:otherwise>
					<tr>
						<td width="15%" class="altbg1" valign="top"><b><br /><bean:message key="magics_permission_group"/><br /><input class="checkbox" type="checkbox" name="chkall2" onclick="checkall(this.form, 'tousergroupsperm', 'chkall2')"> <span><bean:message key="select_all"/></span><br /><br /></b></td>
						<td class="altbg2">
						<table cellspacing="0" cellpadding="0" border="0" width="100%" align="center">
							<c:forEach items="${userGroupList}" var="usergroup" varStatus="status">
								<c:if test="${status.count%4==1}">
									<tr>
								</c:if>
								<td style="border:0px"><input type="checkbox" class="checkbox" name="tousergroupsperm" value="${usergroup.groupid }" ${tousergroupsperm[usergroup.groupid]!=null?"checked":""}> ${usergroup.grouptitle }</td>
								<c:if test="${(status.count)%4==0}">
									</tr>
								</c:if>
							</c:forEach>
						</table>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<br />
	<center><input type="submit" class="button" name="magiceditsubmit" value="<bean:message key="submit"/>"></center>
</form>
<br />
<jsp:directive.include file="../cp_footer.jsp" />