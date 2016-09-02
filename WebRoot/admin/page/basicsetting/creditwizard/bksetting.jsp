<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_tool_creditwizard" /></td></tr>
</table>
<br />
<jsp:include page="lead.jsp"/>
<form method="post" action="admincp.jsp?action=settings&do=bankuaiSetting&extcreditid=${extcreditid}">
	<input type="hidden" name="param" value="bankuaiSettingCommit">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder" align="center">
		<tr class="header">
			<jsp:include page="leadL2.jsp"/>
			<td class="altbg2" style="text-align: right">
				<select id="creditid" onchange="location.href='admincp.jsp?action=settings&do=bankuaiSetting&extcreditid=' + this.value">
					<c:forEach items="${extcredits}" var="ext">
						<option value="${ext.key}" ${extcreditid== ext.key?"selected":""}>extcredits${ext.key}<c:if test="${!empty ext.value.title}">(${ext.value.title})</c:if></option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;
			</td>
		</tr>
		<tr class="category">
			<td colspan="2" class="altbg2">
				<bean:message key="a_setting_settingtype_forum_tips" />
			</td>
		</tr>
	</table>
	<br />
	<a name="f0f5d109d8c287f0"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="7">
				<bean:message key="a_setting_forum_creditspolicy" />
				<a href="###" onclick="collapse_change('f0f5d109d8c287f0')">
				<img id="menuimg_f0f5d109d8c287f0" src="${pageContext.request.contextPath }/images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> 
				</a>
			</td>
		</tr>
		<tbody id="menu_f0f5d109d8c287f0" style="display: yes">
			<tr class="category">
				<td>
					<bean:message key="forum" />
				</td>
				<td>
					<input class="checkbox" type="checkbox" name="chkall1" onclick="checkall(this.form, 'postcreditsstatus', 'chkall1')">
					<bean:message key="a_setting_credits_policy_post" />
				</td>
				<td>
					<input class="checkbox" type="checkbox" name="chkall2" onclick="checkall(this.form, 'replycreditsstatus', 'chkall2')">
					<bean:message key="a_setting_credits_policy_reply" />
				</td>
				<td>
					<input class="checkbox" type="checkbox" name="chkall3" onclick="checkall(this.form, 'digestcreditsstatus', 'chkall3')">
					<bean:message key="a_setting_credits_policy_digest" />
				</td>
				<td>
					<input class="checkbox" type="checkbox" name="chkall4" onclick="checkall(this.form, 'postattachcreditsstatus', 'chkall4')">
					<bean:message key="a_setting_credits_policy_post_attach" />
				</td>
				<td>
					<input class="checkbox" type="checkbox" name="chkall5" onclick="checkall(this.form, 'getattachcreditsstatus', 'chkall5')">
					<bean:message key="a_setting_credits_policy_get_attach" />
				</td>
			</tr>
			<c:forEach items="${bankuaiList}" var="bankuai" varStatus="count">
				<tr>
					<td class="altbg1" width="22%">
						
						<input type="hidden" name="bankuaiIdArray" value="${bankuai.fid }">
						<input class="checkbox" title="<bean:message key="select_all" />" type="checkbox" name="chkallv${bankuai.fid }" onclick="checkallvalue(this.form, ${bankuai.fid }, 'chkallv${bankuai.fid}')">
						${bankuai.type=='forum'?"&nbsp;":"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"}
						<a href="admincp.jsp?frames=yes&action=forumdetail&fid=${bankuai.fid }" target="_blank">${bankuai.name }</a>
					</td>
					<td class="altbg2">
						
						<input class="checkbox" type="checkbox" name="postcreditsstatus_${bankuai.fid}" value="${bankuai.fid}" ${(bankuai.postcredits!=null&&bankuai.postcredits!='')?'checked':''}>
						&nbsp;
						<input type="text" name="postcredits_${bankuai.fid }" size="2" value="${(bankuai.postcredits==null||bankuai.postcredits=='')?0: bankuai.postcredits}">
					</td>
					<td class="altbg1">
						
						<input class="checkbox" type="checkbox" name="replycreditsstatus_${bankuai.fid }" value="${bankuai.fid }" ${(bankuai.replycredits!=null&&bankuai.replycredits!='')?'checked':''}>
						&nbsp;
						<input type="text" name="replycredits_${bankuai.fid }" size="2" value="${(bankuai.replycredits==null||bankuai.replycredits=='')?0: bankuai.replycredits}">
					</td>
					<td class="altbg2">
						
						<input class="checkbox" type="checkbox" name="digestcreditsstatus_${bankuai.fid }" value="${bankuai.fid }" ${(bankuai.digestcredits!=null&&bankuai.digestcredits!='')?'checked':''}>
						&nbsp;
						<input type="text" name="digestcredits_${bankuai.fid }" size="2" value="${(bankuai.digestcredits==null||bankuai.digestcredits=='')?0: bankuai.digestcredits}">
					</td>
					<td class="altbg1">
						
						<input class="checkbox" type="checkbox" name="postattachcreditsstatus_${bankuai.fid }" value="${bankuai.fid }" ${(bankuai.postattachcredits!=null&&bankuai.postattachcredits!='')?'checked':'' }>
						&nbsp;
						<input type="text" name="postattachcredits_${bankuai.fid }" size="2" value="${(bankuai.postattachcredits==null||bankuai.postattachcredits=='')?0:bankuai.postattachcredits }">
					</td>
					<td class="altbg2">
						
						<input class="checkbox" type="checkbox" name="getattachcreditsstatus_${bankuai.fid }" value="${bankuai.fid }" ${(bankuai.getattachcredits!=null&&bankuai.getattachcredits!='')?'checked':'' }>
						&nbsp;
						<input type="text" name="getattachcredits_${bankuai.fid }" size="2" value="${(bankuai.getattachcredits==null||bankuai.getattachcredits=='')?0:bankuai.getattachcredits }">
					</td>
				</tr>
			</c:forEach>
	</table>
	<br />
	<center>
		<input class="button" type="button" value="<bean:message key="a_setting_return" />" onclick="location.href='admincp.jsp?action=creditwizard'">
		<input class="button" type="reset" name="settingsubmit" value="<bean:message key="reset" />">
		<input class="button" type="submit" name="settingsubmit" value="<bean:message key="submit" />">
	</center>
</form>
<jsp:directive.include file="../../cp_footer.jsp" />