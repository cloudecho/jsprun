<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_other_onlinelist"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header"><td><div style="float:left; margin-left:0px; padding-top:8px"><a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a></div><div style="float:right; margin-right:4px; padding-bottom:9px"><a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_${collapsed ? 'add' : 'reduce'}.gif" border="0"/></a></div></td></tr>
	<tbody id="menu_tip" style="display: ${collapsed ? 'none' : ''}">
						<tr>
							<td>
								<ul>
									<li>
										<bean:message key="a_other_onlinelist_tips1"/>
									</li>
								</ul>
								<ul>
									<li>
										<bean:message key="a_other_onlinelist_tips2"/>
									</li>
								</ul>
								<ul>
									<li>
										<bean:message key="a_other_onlinelist_tips3"/>
									</li>
								</ul>
							</td>
						</tr>
					</tbody>
				</table>
				<br />

				<form method="post" action="admincp.jsp?action=onlinelist">
					<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="tableborder">
						<tr class="header">
							<td>
								<bean:message key="display_order"/>
							</td>
							<td>
								<bean:message key="usergroups_title"/>
							</td>
							<td>
								<bean:message key="usergroups_title"/>
							</td>
							<td>
								<bean:message key="a_other_onlinelist_image"/>
							</td>
						</tr>

						
						<c:forEach items="${onlinelistVO_list}" var="onlinelistVO_object" varStatus="outForEachIndex">
							<input type="hidden" name="id" value="${onlinelistVO_object.groupid }">
							<input type="hidden" name="existent" value="${onlinelistVO_object.existent?1:0 }">
							<input type="hidden" name="titlehidden" value="${onlinelistVO_object.title }">
							<tr align="center">
								<td class="altbg1">
									<input type="text" size="3" name="displayorder" value="${onlinelistVO_object.displayorder }">
								</td>
								<td class="altbg2">
									${onlinelistVO_object.quondamTitle }
								</td>
								<td class="altbg1">
									<input type="text" size="15" name="title" value="${onlinelistVO_object.title }" maxlength="30">
								</td>
								<td class="altbg2">
									<input type="text" size="20" name="url" value="${onlinelistVO_object.url }" maxlength="30">
									<c:if test="${onlinelistVO_object.showImage}"><img src="images/common/${onlinelistVO_object.url }"></c:if>
								</td>
							</tr>
						</c:forEach>
						
					</table>
					<br />
					<center>
						<input class="button" type="submit" name="onlinesubmit"
							value="<bean:message key="submit"/>">
					</center>
				</form>
<jsp:directive.include file="../cp_footer.jsp" />