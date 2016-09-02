<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_ranks"/></td></tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td>
			<div style="float:left; margin-left:0px; padding-top:8px">
				<a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a>
			</div>
			<div style="float:right; margin-right:4px; padding-bottom:9px">
				<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" />
				</a>
			</div>
		</td>
	</tr>
	<tbody id="menu_tip" style="display:">
		<tr>
			<td>
				<bean:message key="ranks_tips"/>
			</td>
		</tr>
	</tbody>
</table>
<br />
<form method="post" action="admincp.jsp?action=editranks">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td width="48">
				<input class="checkbox" type="checkbox" name="chkall" onclick="checkall(this.form)"> <bean:message key="del"/>
			</td>
			<td>
				<bean:message key="ranks_title"/>
			</td>
			<td>
				<bean:message key="ranks_postshigher"/>
			</td>
			<td>
				<bean:message key="stars"/>
			</td>
			<td>
				<bean:message key="title_color"/>
			</td>
		</tr>
		<c:forEach items="${ranklist}" var="rank">
		<input type="hidden" name="updateid" value="${rank.rankid}">
		<tr align="center">
			<td class="altbg1">
				<input class="checkbox" type="checkbox" name="delid" value="${rank.rankid}">
			</td>
			<td class="altbg2">
				<input type="text" size="12" name="ranktitle" value="${rank.ranktitle}" maxlength="30">
			</td>
			<td class="altbg1">
				<input type="text" size="6" name="postshigher" value="${rank.postshigher}" maxlength="7">
			<td class="altbg1">
				<input type="text" size="2" name="stars" value="${rank.stars}" maxlength="2">
			</td>
			<td class="altbg2">
				<input type="text" size="6" name="color" value="${rank.color}" maxlength="7">
			</td>
		</tr>
		</c:forEach>
		<tr align="center" class="altbg1">
			<td>
				<bean:message key="add_new"/>
			</td>
			<td>
				<input type="text" size="12" name="newranktitle" maxlength="30">
			</td>
			<td>
				<input type="text" size="6" name="newpostshigher" maxlength="7">
			</td>
			<td>
				<input type="text" size="2" name="newstars" maxlength="2">
			</td>
			<td>
				<input type="text" size="6" name="newcolor" maxlength="7">
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="ranksubmit" value="<bean:message key="submit"/>">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />