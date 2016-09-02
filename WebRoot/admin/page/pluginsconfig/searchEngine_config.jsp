<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system" /></a>&nbsp;&raquo;&nbsp;${valueObject.engineName }<bean:message key="header_basic" /></td></tr>
</table>
<br />
			<form method="post" name="settings" action="admincp.jsp?action=searchEngine">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<input type="hidden" name="engineName" value="${valueObject.engineName }">
				<a name="4ffbd8999bc88f6e"></a>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="tableborder">
					<tr class="header">
						<td colspan="2">
							${valueObject.engineName } <bean:message key="a_setting_subtitle_search" />
							<a href="###" onclick="collapse_change('4ffbd8999bc88f6e')"><img
									id="menuimg_4ffbd8999bc88f6e"
									src="${pageContext.request.contextPath}/images/admincp/menu_reduce.gif" border="0"
									style="float: right; *margin-top: -12px; margin-right: 8px;" />
							</a>
						</td>
					</tr>
					<tbody id="menu_4ffbd8999bc88f6e" style="display: yes">
						<tr>
							<td width="45%" class="altbg1">
								<b><bean:message key="a_extends_search_start" arg0="${valueObject.engineName }" /></b>
							</td>
							<td class="altbg2">
								<input class="radio" type="radio" name="googlenew[status]"
									value="1" ${(valueObject.status==1)?"checked":"" }>
								<bean:message key="yes" /> &nbsp; &nbsp;
								<input class="radio" type="radio" name="googlenew[status]"
									value="0" ${(valueObject.status==0)?"checked":"" }>
								<bean:message key="no" />
							</td>
						</tr>
						<tr>
							<td width="45%" class="altbg1">
								<b><bean:message key="a_extends_searchbox" arg0="${valueObject.engineName }" /></b>
								<br />
								<span class="smalltxt"><bean:message key="a_extends_searchbox_comment" /></span>
							</td>
							<td class="altbg2">
								<input class="checkbox" type="checkbox"
									name="googlenew[searchbox]" value="1" ${(valueObject.show==1||valueObject.show==3||valueObject.show==5||valueObject.show==7)?"checked":"" }>
								<bean:message key="a_extends_searchbox_index" />
								<br />
								<input class="checkbox" type="checkbox"
									name="googlenew[searchbox]" value="2" ${(valueObject.show==2||valueObject.show==3||valueObject.show==6||valueObject.show==7)?"checked":"" }>
								<bean:message key="a_extends_searchbox_forumdisplay" />
								<br />
								<input class="checkbox" type="checkbox"
									name="googlenew[searchbox]" value="4" ${(valueObject.show==4||valueObject.show==5||valueObject.show==6||valueObject.show==7)?"checked":"" }>
								<bean:message key="a_extends_searchbox_viewthread" />
							</td>
						</tr>
						<c:if test="${valueObject.engineName eq 'Google' }">
						<tr>
							<td width="45%" class="altbg1">
								<b><bean:message key="a_extends_search_lang" /></b>
								<br />
								<span class="smalltxt"><bean:message key="a_extends_search_lang_comment" /></span>
							</td>
							<td class="altbg2">
								<input class="radio" type="radio" name="googlenew[lang]"
									value="" ${(valueObject.language eq '')?"checked":"" } >
								<bean:message key="a_extends_search_lang_any" />
								<br />
								<input class="radio" type="radio" name="googlenew[lang]"
									value="en" ${(valueObject.language eq 'en')?"checked":"" }>
								<bean:message key="a_extends_search_lang_en" />
								<br />
								<input class="radio" type="radio" name="googlenew[lang]"
									value="zh-CN" ${(valueObject.language eq 'zh-CN')?"checked":"" }>
								<bean:message key="a_extends_search_lang_zh-CN" />
								<br />
								<input class="radio" type="radio" name="googlenew[lang]"
									value="zh-TW" ${(valueObject.language eq 'zh-TW')?"checked":"" }>
								<bean:message key="a_extends_search_lang_zh-TW" />
								<br />
							</td>
						</tr>
						</c:if>
					</tbody>
				</table>
				<br />
				<center>
					<input class="button" type="submit" name="googlesubmit" value="<bean:message key="submit" />">
				</center>
			</form>
<jsp:directive.include file="../cp_footer.jsp" />