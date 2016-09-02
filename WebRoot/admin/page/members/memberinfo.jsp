<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_member_edit"/></td></tr>
</table>
<br />
<form method="post" action="admincp.jsp?action=edituserinfo&memberid=${member.uid}">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<a name="17a108221bce06a4"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="menu_member_edit"/> - ${member.username}<a href="###" onclick="collapse_change('17a108221bce06a4')"><img id="menuimg_17a108221bce06a4" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_17a108221bce06a4" style="display: yes">
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_username"/></b><br /><span class="smalltxt"><bean:message key="a_member_edit_username_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="usernamenew" value="${member.username}" maxlength="15"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_password"/></b><br /><span class="smalltxt"><bean:message key="a_member_edit_password_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="passwordnew" value="" ></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_clearquestion"/></b><br /><span class="smalltxt"><bean:message key="a_member_edit_clearquestion_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="clearquestion" value="1" checked  > <bean:message key="yes"/> &nbsp; &nbsp; 
				<input class="radio" type="radio" name="clearquestion" value="0"   > <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_clearspacecache"/></b><br /><span class="smalltxt"><bean:message key="a_member_edit_clearspacecache_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="clearspacecache" value="1"   > <bean:message key="yes"/> &nbsp; &nbsp; 
				<input class="radio" type="radio" name="clearspacecache" value="0" checked  > <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_nickname"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="nicknamenew" value="${memberfield.nickname}" maxlength="30"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_gender"/></b></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="gendernew" value="1" ${member.gender==1?"checked":""}> <bean:message key="a_member_edit_gender_male"/>
				<input class="radio" type="radio" name="gendernew" value="2" ${member.gender==2?"checked":""}> <bean:message key="a_member_edit_gender_female"/> 
				<input class="radio" type="radio" name="gendernew" value="0" ${member.gender==0?"checked":""}> <bean:message key="a_member_edit_gender_secret"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_email"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="emailnew" value="${member.email}" maxlength="40"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_posts"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="postsnew" value="${member.posts}" maxlength="8"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_digestposts"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="digestpostsnew" value="${member.digestposts}" maxlength="6"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_pageviews"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="pageviewsnew" value="${member.pageviews}" maxlength="8"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_online_total"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="totalnew" value="${onlinetime.total}" maxlength="8"></td></tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_online_thismonth"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="thismonthnew" value="${onlinetime.thismonth}" maxlength="6"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_regip"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="regipnew" value="${member.regip}" maxlength="15"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_regdate"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="regdatenew" value="<jrun:showTime  timeInt="${member.regdate}" type="yyyy-MM-dd kk:mm" timeoffset="${timeoffset}"/>"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_lastvisit"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="lastvisitnew" value="<jrun:showTime timeInt="${member.lastvisit}" type="yyyy-MM-dd kk:mm" timeoffset="${timeoffset}"/>" ></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_lastip"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="lastipnew" value="${member.lastip}" maxlength="15"></td>
		</tr>
	</table>
	<br />
	<a name="cbe459cf57bb2ae4"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
	<td colspan="2"><bean:message key="a_member_edit_info"/><a href="###" onclick="collapse_change('cbe459cf57bb2ae4')"><img id="menuimg_cbe459cf57bb2ae4" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
	</td>
	</tr>
	<tbody id="menu_cbe459cf57bb2ae4" style="display: yes">
	<tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_site"/></b></td><td class="altbg2"><input type="text" size="50" name="sitenew" value="${memberfield.site}" maxlength="75">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_qq"/></b></td><td class="altbg2"><input type="text" size="50" name="qqnew" value="${memberfield.qq}" maxlength="12">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_icq"/></b></td><td class="altbg2"><input type="text" size="50" name="icqnew" value="${memberfield.icq}" maxlength="12">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_yahoo"/></b></td><td class="altbg2"><input type="text" size="50" name="yahoonew" value="${memberfield.yahoo}" maxlength="40">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_msn"/></b></td><td class="altbg2"><input type="text" size="50" name="msnnew" value="${memberfield.msn}" maxlength="40">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_taobao"/></b></td><td class="altbg2"><input type="text" size="50" name="taobaonew" value="${memberfield.taobao}" maxlength="40">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_alipay"/></b></td><td class="altbg2"><input type="text" size="50" name="alipaynew" value="${memberfield.alipay}" maxlength="50">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_location"/></b></td><td class="altbg2"><input type="text" size="50" name="locationnew" value="${memberfield.location}" maxlength="30">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_bday"/></b></td><td class="altbg2"><input type="text" size="50" name="bdaynew" value="${member.bday}" >
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_avatar"/></b></td><td class="altbg2"><input type="text" size="50" name="avatarnew" value="${memberfield.avatar}" maxlength="255">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_avatar_width"/></b></td><td class="altbg2"><input type="text" size="50" name="avatarwidthnew" value="${memberfield.avatarwidth}" maxlength="3">
	</td></tr><tr><td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_avatar_height"/></b></td><td class="altbg2"><input type="text" size="50" name="avatarheightnew" value="${memberfield.avatarheight}" maxlength="3">
	</td></tr><tr><td width="45%" class="altbg1" valign="top"><b><bean:message key="a_member_edit_bio"/></b></td><td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('bionew', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('bionew', 0)"><br /><textarea  rows="6" name="bionew" id="bionew" cols="50">${memberfield.bio}</textarea></td></tr><tr><td width="45%" class="altbg1" valign="top"><b><bean:message key="a_member_edit_signature"/></b></td><td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('signaturenew', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('signaturenew', 0)"><br /><textarea  rows="6" name="signaturenew" id="signaturenew" cols="50">${memberfield.sightml}</textarea></td></tr></table><br /><a name="445565c871fa2c14"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="memcp_profile_type_5"/><a href="###" onclick="collapse_change('445565c871fa2c14')"><img id="menuimg_445565c871fa2c14" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_445565c871fa2c14" style="display: yes">
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_style"/></b></td>
			<td class="altbg2">
				<select name="styleidnew">
					<option value="0"><bean:message key="use_default"/></option>
					<c:forEach items="${styleslist}" var="style">
						<option value="${style.styleid}" ${style.styleid==member.styleid?"selected":""}>${style.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="page_tpp"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="tppnew" value="${member.tpp}" maxlength="3"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_ppp"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="pppnew" value="${member.ppp}" maxlength="3"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_cstatus"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="cstatusnew" value="${memberfield.customstatus}" maxlength="30"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_timeformat"/></b></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="timeformatnew" value="0" ${member.timeformat==0?"checked":""}> <bean:message key="default"/> &nbsp; 
				<input class="radio" type="radio" name="timeformatnew" value="1" ${member.timeformat==1?"checked":""}> <bean:message key="a_member_edit_timeformat_12"/> &nbsp;  
				<input class="radio" type="radio" name="timeformatnew" value="2" ${member.timeformat==2?"checked":""}> <bean:message key="a_member_edit_timeformat_24"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_timeoffset"/></b><br /><span class="smalltxt"><bean:message key="a_member_edit_timeoffset_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="timeoffsetnew" value="${member.timeoffset}" maxlength="4"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_pmsound"/></b></td>
			<td class="altbg2">
				<input class="radio" type="radio" value="0" name="pmsoundnew" ${member.pmsound==0?"checked":""}><bean:message key="none"/> &nbsp; 
				<input class="radio" type="radio" value="1" name="pmsoundnew" ${member.pmsound==1?"checked":""}><a href="images/sound/pm_1.wav">#1</a> &nbsp;
				<input class="radio" type="radio" value="2" name="pmsoundnew" ${member.pmsound==2?"checked":""}><a href="images/sound/pm_2.wav">#2</a> &nbsp; 
				<input class="radio" type="radio" value="3" name="pmsoundnew" ${member.pmsound==3?"checked":""}><a href="images/sound/pm_3.wav">#3</a>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_invisible"/></b></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="invisiblenew" value="1" ${member.invisible==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp; 
				<input class="radio" type="radio" name="invisiblenew" value="0" ${member.invisible==0?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_showemail"/></b></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="showemailnew" value="1" ${member.showemail==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp; 
				<input class="radio" type="radio" name="showemailnew" value="0" ${member.showemail==0?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" ><b><bean:message key="a_member_edit_newsletter"/></b></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="newsletternew" value="1" ${member.newsletter==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp; 
				<input class="radio" type="radio" name="newsletternew" value="0" ${member.newsletter==0?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1" valign="top"><b><bean:message key="a_member_edit_ignorepm"/></b></td>
			<td class="altbg2"><img src="images/admincp/zoomin.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('ignorepmnew', 1)"> <img src="images/admincp/zoomout.gif" onmouseover="this.style.cursor='pointer'" onclick="zoomtextarea('ignorepmnew', 0)"><br /><textarea  rows="6" name="ignorepmnew" id="ignorepmnew" cols="50">${memberfield.ignorepm}</textarea></td>
		</tr>
	</table>
	<br />
	<a name="9e8d41009b529cbf"></a>
	<c:if test="${profilelist!=null}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
			<td colspan="2"><bean:message key="a_member_edit_profilefield"/><a href="###" onclick="collapse_change('9e8d41009b529cbf')"><img id="menuimg_9e8d41009b529cbf" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
			</td>
			</tr>
			<tbody id="menu_9e8d41009b529cbf" style="display: yes">
				<c:forEach items="${profilelist}" var="profile">
				<tr><td width="45%" class="altbg1" ><b>${profile.title}</b></td><td class="altbg2">
				<c:choose>
					<c:when test="${profile.selective==1}">${profile.select}</c:when>
					<c:otherwise><input type="text" name="profile${profile.fieldid}" value="${profile.select}" maxlength="50"/></c:otherwise>
				</c:choose>
				</td></tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<br />
	<center><input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>"></center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />