<%@ page language="java"  pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<form method="post" action="admincp.jsp?action=editusergroupinfo&groupid=${userGroup.groupid}">
<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
<a name="d6f648ec5c05e6d2"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="2"><bean:message key="usergroups_edit"/><a href="###" onclick="collapse_change('d6f648ec5c05e6d2')"><img id="menuimg_d6f648ec5c05e6d2" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
	</tr>
	<tbody id="menu_d6f648ec5c05e6d2" style="display: yes">
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_title"/></b></td>
			<td class="altbg2"><input type="text" size="50" name="grouptitle" value="${userGroup.grouptitle}" maxlength="30"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_scheme"/></b></td>
			<td class="altbg2">
				<select name="projectid" onchange="window.location='admincp.jsp?action=forusergroups&edit=${userGroup.groupid}&projectid='+this.options[this.options.selectedIndex].value">
					<option value="0" selected="selected"><bean:message key="none"/></option>
					<c:forEach items="${projects}" var="project">
						<option value="${project.id}" ${project.id==projectid?"selected":""}>${project.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<c:if test="${userGroup.type=='special'}">
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_radminid"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_radminid_comment"/></span></td>
				<td class="altbg2">
					<select name="radminid">
						<option value="0"><bean:message key="none"/></option>
						<option value="1" ${userGroup.radminid==1?"selected":""}><bean:message key="usergroups_system_1"/></option>
						<option value="2" ${userGroup.radminid==2?"selected":""}><bean:message key="usergroups_system_2"/></option>
						<option value="3" ${userGroup.radminid==3?"selected":""}><bean:message key="usergroups_system_3"/></option>
					</select>
				</td>
			</tr>
		</c:if>
	</tbody>
</table>
<br />
<c:if test="${userGroup.type=='special'}">
	<a name="49fac9afe359c2b8"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2"><bean:message key="usergroups_edit_system"/><a href="###" onclick="collapse_change('49fac9afe359c2b8')"><img id="menuimg_49fac9afe359c2b8" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
		</tr>
		<tbody id="menu_49fac9afe359c2b8" style="display: yes">
			<tr>
				<td colspan="2" class="altbg2">
					<bean:message key="usergroups_edit_system_comment"/>
				</td>
			</tr>
		</tbody>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_system_public"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_system_public_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="system_public" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
				<input class="radio" type="radio" name="system_public" value="0" ${systemgroup==null?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<c:choose>
			<c:when test="${systemgroup!=null}">
				<c:forEach items="${systemgroup}"  var="system" varStatus="index">
					<c:if test="${index.count==1}">
						<tr>
							<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_system_dailyprice"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_system_dailyprice_comment"/></span></td>
							<td class="altbg2"><input type="text" size="50" name="system_dailyprice" value="${system}"></td>
						</tr>
					</c:if>
					<c:if test="${index.count==2}">
						<tr>
							<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_system_minspan"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_system_minspan_comment"/></span></td>
							<td class="altbg2"><input type="text" size="50" name="system_minspan" value="${system}"></td>
						</tr>
					</c:if>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_system_dailyprice"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_system_dailyprice_comment"/></span></td>
					<td class="altbg2"><input type="text" size="50" name="system_dailyprice" value="0"></td>
				</tr>
				<tr>
					<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_system_minspan"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_system_minspan_comment"/></span></td>
					<td class="altbg2"><input type="text" size="50" name="system_minspan" value="0"></td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<br />
</c:if>
<a name="fc7692b098d134af"></a>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
	<tr class="header">
		<td colspan="2"><bean:message key="usergroups_edit_basic"/><a href="###" onclick="collapse_change('fc7692b098d134af')"><img id="menuimg_fc7692b098d134af" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a></td>
	</tr>
	<tbody id="menu_fc7692b098d134af" style="display: yes">
		<c:choose>
			<c:when test="${userGroup.groupid==7}">
				<input type="hidden" name="allowvisit" value="1">
			</c:when>
			<c:otherwise>
			<tr>
				<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_visit"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_visit_comment"/></span></td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowvisit" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowvisit" value="0" ${userGroup.allowvisit!=1?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			</c:otherwise>
		</c:choose>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_read_access"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_read_access_comment"/></span></td>
			<td class="altbg2"><input type="text" size="50" name="readaccess" value="${userGroup.readaccess}" maxlength="3"></td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_view_profile"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_view_profile_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="allowviewpro" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
				<input class="radio" type="radio" name="allowviewpro" value="0" ${userGroup.allowviewpro!=1?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_view_stats"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_view_stats_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="allowviewstats" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
				<input class="radio" type="radio" name="allowviewstats" value="0" ${userGroup.allowviewstats!=1?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_invisible"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_invisible_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="allowinvisible" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
				<input class="radio" type="radio" name="allowinvisible" value="0" ${userGroup.allowinvisible!=1?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_multigroups"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_multigroups_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="allowmultigroups" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
				<input class="radio" type="radio" name="allowmultigroups" value="0" ${userGroup.allowmultigroups!=1?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_allowtransfer"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_allowtransfer_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="allowtransfer" value="1" checked> <bean:message key="yes"/> &nbsp; &nbsp;
				<input class="radio" type="radio" name="allowtransfer" value="0" ${userGroup.allowtransfer!=1?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_search"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_search_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="allowsearch" value="0" ${userGroup.allowsearch==0?"checked":""}> <bean:message key="usergroups_edit_search_disable"/><br />
				<input class="radio" type="radio" name="allowsearch" value="1" ${userGroup.allowsearch==1?"checked":""}> <bean:message key="usergroups_edit_search_thread"/><br />
				<input class="radio" type="radio" name="allowsearch" value="2" ${userGroup.allowsearch==2?"checked":""}> <bean:message key="usergroups_edit_search_post"/><br />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_avatar"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_avatar_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="allowavatar" value="0" ${userGroup.allowavatar==0?"checked":""}> <bean:message key="usergroups_edit_avatar_disable"/><br />
				<input class="radio" type="radio" name="allowavatar" value="1" ${userGroup.allowavatar==1?"checked":""}> <bean:message key="usergroups_edit_avatar_board"/><br />
				<input class="radio" type="radio" name="allowavatar" value="2" ${userGroup.allowavatar==2?"checked":""}> <bean:message key="usergroups_edit_avatar_custom"/><br />
				<input class="radio" type="radio" name="allowavatar" value="3" ${userGroup.allowavatar==3?"checked":""}> <bean:message key="usergroups_edit_avatar_upload"/><br />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_reasonpm"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_reasonpm_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="reasonpm" value="0" ${userGroup.reasonpm==0?"checked":""}> <bean:message key="usergroups_edit_reasonpm_none"/><br />
				<input class="radio" type="radio" name="reasonpm" value="1" ${userGroup.reasonpm==1?"checked":""}> <bean:message key="usergroups_edit_reasonpm_reason"/><br />
				<input class="radio" type="radio" name="reasonpm" value="2" ${userGroup.reasonpm==2?"checked":""}> <bean:message key="usergroups_edit_reasonpm_pm"/><br />
				<input class="radio" type="radio" name="reasonpm" value="3" ${userGroup.reasonpm==3?"checked":""}> <bean:message key="usergroups_edit_reasonpm_both"/><br />
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1"><b><bean:message key="usergroups_edit_blog"/></b><br /><span class="smalltxt"><bean:message key="usergroups_edit_blog_comment"/></span></td>
			<td class="altbg2">
				<input class="radio" type="radio" name="allowuseblog" value="1" ${userGroup.allowuseblog==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
				<input class="radio" type="radio" name="allowuseblog" value="0" ${userGroup.allowuseblog==0?"checked":""}> <bean:message key="no"/>
			</td>
		</tr>
		<tr>
		   <td width="45%" class="altbg1">
				 <b><bean:message key="usergroups_edit_nickname"/></b>
				 <br />
				 <span class="smalltxt"><bean:message key="usergroups_edit_nickname_comment"/></span>
			 </td>
			 <td class="altbg2">
			    <input class="radio" type="radio" name="allownickname" value="1" ${userGroup.allownickname==1?"checked":""} > <bean:message key="yes"/> &nbsp; &nbsp;
				 <input class="radio" type="radio" name="allownickname" value="0" ${userGroup.allownickname==0?"checked":""}> <bean:message key="no"/>
			  </td>
	   </tr>
		 <tr>
			 <td width="45%" class="altbg1">
			 <b><bean:message key="usergroups_edit_cstatus"/></b>
			 <br />
			 <span class="smalltxt"><bean:message key="usergroups_edit_cstatus_comment"/></span>
			 </td>
			 <td class="altbg2">
			 <input class="radio" type="radio" name="allowcstatus" value="1" ${userGroup.allowcstatus==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
			 <input class="radio" type="radio" name="allowcstatus" value="0" ${userGroup.allowcstatus==0?"checked":""}> <bean:message key="no"/>
			 </td>
		 </tr>
		 <tr>
			 <td width="45%" class="altbg1">
				 <b><bean:message key="usergroups_edit_disable_periodctrl"/></b>
				 <br />
				 <span class="smalltxt"><bean:message key="usergroups_edit_disable_periodctrl_comment"/></span>
			 </td>
			 <td class="altbg2">
				 <input class="radio" type="radio" name="disableperiodctrl" value="1" ${userGroup.disableperiodctrl==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
				 <input class="radio" type="radio" name="disableperiodctrl" value="0" ${userGroup.disableperiodctrl==0?"checked":""}> <bean:message key="no"/>
			 </td>
			 </tr>
		 <tr>
			 <td width="45%" class="altbg1">
				 <b><bean:message key="usergroups_edit_max_pm_num"/></b>
				 <br />
				 <span class="smalltxt"><bean:message key="usergroups_edit_max_pm_num_comment"/></span>
				 </td>
			 <td class="altbg2">
				 <input type="text" size="50" name="maxpmnum" value="${userGroup.maxpmnum}" maxlength="5">
			 </td>
		 </tr>
		 <tr>
			 <td width="45%" class="altbg1">
					 <b><bean:message key="usergroups_edit_hour_posts"/></b>
					 <br />
					 <span class="smalltxt"><bean:message key="usergroups_edit_hour_posts_comment"/></span>
			 </td>
			 <td class="altbg2">
				 <input type="text" size="50" name="maxpostsperhour" value="${userGroup.maxpostsperhour}" maxlength="3">
			 </td>
		 </tr>
  </table>
	  <br />
	 <a name="bbbc225db9ab0ecd"></a>
	 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		 <tr class="header">
			 <td colspan="2">
			 <bean:message key="usergroups_specialthread"/> <a href="###" onclick="collapse_change('bbbc225db9ab0ecd')"><img id="menuimg_bbbc225db9ab0ecd" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /></a>
			 </td>
			 </tr>
			 <tbody id="menu_bbbc225db9ab0ecd" style="display: yes">
				  <tr>
					  <td width="45%" class="altbg1">
						 <b><bean:message key="usergroups_edit_post_digest"/></b>  <br />
							 <span class="smalltxt"><bean:message key="usergroups_edit_post_digest_comment"/></span>
					 </td>
					  <td class="altbg2">
							<input class="radio" type="radio" name="allowviewdigest" value="1" ${userGroup.allowviewdigest==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
							<input class="radio" type="radio" name="allowviewdigest" value="0" ${userGroup.allowviewdigest==0?"checked":""}> <bean:message key="no"/>
					 </td>
			 </tr>
			 <tr>
				<td width="45%" class="altbg1">
					 <b><bean:message key="usergroups_special_activity"/></b><br />
					<span class="smalltxt"><bean:message key="usergroups_special_activity_comment"/></span>
				</td>
				<td class="altbg2">
					 <input class="radio" type="radio" name="allowpostactivity" value="1" ${userGroup.allowpostactivity==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
					 <input class="radio" type="radio" name="allowpostactivity" value="0" ${userGroup.allowpostactivity==0?"checked":""}> <bean:message key="no"/>
				</td>
			 </tr>
			 <tr>
				<td width="45%" class="altbg1">
				 <b><bean:message key="usergroups_edit_post_poll"/></b> <br />
				 <span class="smalltxt"><bean:message key="usergroups_edit_post_poll_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowpostpoll" value="1" ${userGroup.allowpostpoll==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
					 <input class="radio" type="radio" name="allowpostpoll" value="0" ${userGroup.allowpostpoll==0?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_edit_vote"/></b><br />
					<span class="smalltxt"><bean:message key="usergroups_edit_vote_comment"/></span>
				</td>
				<td class="altbg2">
					 <input class="radio" type="radio" name="allowvote" value="1" ${userGroup.allowvote==1?"checked":""} > <bean:message key="yes"/> &nbsp; &nbsp;
					 <input class="radio" type="radio" name="allowvote" value="0" ${userGroup.allowvote==0?"checked":""} > <bean:message key="no"/>
				</td>
		 </tr>
		 <tr>
			 <td width="45%" class="altbg1">
				 <b><bean:message key="usergroups_special_reward"/></b>  <br />
				 <span class="smalltxt"><bean:message key="usergroups_special_reward_comment"/></span>
			 </td>
			 <td class="altbg2">
				 <input class="radio" type="radio" name="allowpostreward" value="1" ${userGroup.allowpostreward==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
				 <input class="radio" type="radio" name="allowpostreward" value="0" ${userGroup.allowpostreward==0?"checked":""}> <bean:message key="no"/>
			 </td>
		 </tr>
		 <tr>
		 <td width="45%" class="altbg1">
			<b><bean:message key="usergroups_special_reward_min"/></b> <br />
			<span class="smalltxt"><bean:message key="usergroups_special_reward_min_comment"/></span>
		 </td>
		 <td class="altbg2">
			<input type="text" size="50" name="minrewardprice" value="${userGroup.minrewardprice}" maxlength="5">
		 </td>
		 </tr>
		 <tr>
			 <td width="45%" class="altbg1">
				 <b><bean:message key="usergroups_special_reward_max"/></b> <br />
				 <span class="smalltxt"><bean:message key="usergroups_special_reward_max_comment"/></span>
			 </td>
			 <td class="altbg2">
				 <input type="text" size="50" name="maxrewardprice" value="${userGroup.maxrewardprice}" maxlength="5">
			 </td>
			 </tr>
			 <tr>
			 <td width="45%" class="altbg1">
				 <b><bean:message key="usergroups_special_trade"/></b>
				<br />
				<span class="smalltxt"><bean:message key="usergroups_special_trade_comment"/></span>
			 </td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowposttrade" value="1" ${userGroup.allowposttrade==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowposttrade" value="0" ${userGroup.allowposttrade==0?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_special_trade_min"/></b>
					<br />
					<span class="smalltxt"><bean:message key="usergroups_special_trade_min_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="mintradeprice" value="${userGroup.mintradeprice}" maxlength="5">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_special_trade_max"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_special_trade_max_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="maxtradeprice" value="${userGroup.maxtradeprice}" maxlength="5">
				</td>
			</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="usergroups_special_trade_stick"/></b> <br />
				<span class="smalltxt"><bean:message key="usergroups_special_trade_stick_comment"/></span>
			</td>
			<td class="altbg2">
				<input type="text" size="50" name="tradestick" value="${userGroup.tradestick}" maxlength="1">
			</td>
		</tr>
		<tr>
			<td width="45%" class="altbg1">
				<b><bean:message key="usergroups_special_debate"/></b> <br />
				<span class="smalltxt"><bean:message key="usergroups_special_debate_comment"/></span>
			</td>
			<td class="altbg2">
				<input class="radio" type="radio" name="allowpostdebate" value="1" ${userGroup.allowpostdebate==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
				<input class="radio" type="radio" name="allowpostdebate" value="0" ${userGroup.allowpostdebate==0?"checked":""}> <bean:message key="no"/>
			</td>
				</tr>
	</table>
		<br />
		<a name="6168f78ed2edb6d5"></a>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td colspan="2">
					<bean:message key="menu_post"/>
					<a href="###" onclick="collapse_change('6168f78ed2edb6d5')"><img id="menuimg_6168f78ed2edb6d5" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
				</td>
			</tr>
			<tbody id="menu_6168f78ed2edb6d5" style="display: yes">
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_post"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_post_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowpost" value="1" ${userGroup.allowpost==1?"checked":""} > <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowpost" value="0" ${userGroup.allowpost==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_reply"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_reply_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowreply" value="1" ${userGroup.allowreply==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowreply" value="0" ${userGroup.allowreply==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_direct_post"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_direct_post_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowdirectpost" value="0" ${userGroup.allowdirectpost==0?"checked":""}>
						<bean:message key="usergroups_edit_direct_post_none"/> <br />
						<input class="radio" type="radio" name="allowdirectpost" value="1" ${userGroup.allowdirectpost==1?"checked":""}>
						<bean:message key="usergroups_edit_direct_post_reply"/> <br />
						<input class="radio" type="radio" name="allowdirectpost" value="2" ${userGroup.allowdirectpost==2?"checked":""}>
						<bean:message key="usergroups_edit_direct_post_thread"/> <br />
						<input class="radio" type="radio" name="allowdirectpost" value="3" ${userGroup.allowdirectpost==3?"checked":""}>
						<bean:message key="usergroups_edit_direct_post_all"/> <br />
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_anonymous"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_anonymous_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowanonymous" value="1" ${userGroup.allowanonymous==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowanonymous" value="0" ${userGroup.allowanonymous==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_set_read_perm"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_set_read_perm_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowsetreadperm" value="1" ${userGroup.allowsetreadperm==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowsetreadperm" value="0" ${userGroup.allowsetreadperm==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_maxprice"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_maxprice_comment"/></span>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="maxprice" value="${userGroup.maxprice}" maxlength="5">
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_hide_code"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_hide_code_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowhidecode" value="1" ${userGroup.allowhidecode==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowhidecode" value="0" ${userGroup.allowhidecode==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_html"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_html_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowhtml" value="1" ${userGroup.allowhtml==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowhtml" value="0" ${userGroup.allowhtml==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_custom_bbcode"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_custom_bbcode_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowcusbbcode" value="1" ${userGroup.allowcusbbcode==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowcusbbcode" value="0" ${userGroup.allowcusbbcode==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_bio_bbcode"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_bio_bbcode_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowbiobbcode" value="1" ${userGroup.allowbiobbcode==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowbiobbcode" value="0" ${userGroup.allowbiobbcode==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_bio_img_code"/></b>
						<br />
						<span class="smalltxt"><bean:message key="usergroups_edit_bio_img_code_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowbioimgcode" value="1" ${userGroup.allowbioimgcode==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowbioimgcode" value="0" ${userGroup.allowbioimgcode==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_max_bio_size"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_max_bio_size_comment"/></span>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="maxbiosize" value="${userGroup.maxbiosize}" maxlength="5">
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_sig_bbcode"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_sig_bbcode_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowsigbbcode" value="1" ${userGroup.allowsigbbcode==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowsigbbcode" value="0" ${userGroup.allowsigbbcode==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_sig_img_code"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_sig_img_code_comment"/></span>
					</td>
					<td class="altbg2">
						<input class="radio" type="radio" name="allowsigimgcode" value="1" ${userGroup.allowsigimgcode==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
						<input class="radio" type="radio" name="allowsigimgcode" value="0" ${userGroup.allowsigimgcode==0?"checked":""}> <bean:message key="no"/>
					</td>
				</tr>
				<tr>
					<td width="45%" class="altbg1">
						<b><bean:message key="usergroups_edit_max_sig_size"/></b> <br />
						<span class="smalltxt"><bean:message key="usergroups_edit_max_sig_size_comment"/></span>
					</td>
					<td class="altbg2">
						<input type="text" size="50" name="maxsigsize" value="${userGroup.maxsigsize }" maxlength="5">
					</td>
				</tr>
		</table>
	<br />
	<a name="60dd83be5999b6f8"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="usergroups_edit_attachment"/>
				<a href="###" onclick="collapse_change('60dd83be5999b6f8')"><img id="menuimg_60dd83be5999b6f8" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
			</td>
		</tr>
		<tbody id="menu_60dd83be5999b6f8" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_edit_get_attach"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_edit_get_attach_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowgetattach" value="1" ${userGroup.allowgetattach==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowgetattach" value="0" ${userGroup.allowgetattach==0?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_edit_post_attach"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_edit_post_attach_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowpostattach" value="1" ${userGroup.allowpostattach==1?"checked":""}> <bean:message key="yes"/>  &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowpostattach" value="0" ${userGroup.allowpostattach==0?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_edit_set_attach_perm"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_edit_set_attach_perm_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowsetattachperm" value="1" ${userGroup.allowsetattachperm==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowsetattachperm" value="0" ${userGroup.allowsetattachperm==0?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_edit_max_attach_size"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_edit_max_attach_size_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="maxattachsize" value="${userGroup.maxattachsize }" maxlength="8">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_edit_max_size_per_day"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_edit_max_size_per_day_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="maxsizeperday" value="${userGroup.maxsizeperday}" maxlength="9">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_edit_attach_ext"/></b>
					<br />
					<span class="smalltxt"><bean:message key="usergroups_edit_attach_ext_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="attachextensions" value="${userGroup.attachextensions}" maxlength="100">
				</td>
			</tr>
	</table>
	<br />
	<a name="d98cae9071cc3521"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="usergroups_magic"/>
				<a href="###" onclick="collapse_change('d98cae9071cc3521')"><img id="menuimg_d98cae9071cc3521" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
			</td>
		</tr>
		<tbody id="menu_d98cae9071cc3521" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_magic_permission"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_magic_permission_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowmagics" value="0" ${userGroup.allowmagics==0?"checked":""}>
					<bean:message key="usergroups_magic_unallowed"/> <br />
					<input class="radio" type="radio" name="allowmagics" value="1" ${userGroup.allowmagics==1?"checked":""}>
					<bean:message key="usergroups_magic_allow"/> <br />
					<input class="radio" type="radio" name="allowmagics" value="2" ${userGroup.allowmagics==2?"checked":""}>
					<bean:message key="usergroups_magic_allow_and_pass"/> <br />
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_magic_discount"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_magic_discount_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="magicsdiscount" value="${userGroup.magicsdiscount}" maxlength="1">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_magic_max"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_magic_max_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="maxmagicsweight" value="${userGroup.maxmagicsweight}" maxlength="5">
				</td>
			</tr>
	</table>
	<br />
	<a name="a56e2c77b257f342"></a>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tableborder">
		<tr class="header">
			<td colspan="2">
				<bean:message key="usergroups_invite"/>
				<a href="###" onclick="collapse_change('a56e2c77b257f342')"><img id="menuimg_a56e2c77b257f342" src="./images/admincp/menu_reduce.gif" border="0" style="float: right; *margin-top: -12px; margin-right: 8px;" /> </a>
			</td>
		</tr>
		<tbody id="menu_a56e2c77b257f342" style="display: yes">
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_invite_permission"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_invite_permission_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowinvite" value="1" ${userGroup.allowinvite==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowinvite" value="0" ${userGroup.allowinvite==0?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_invitesend_permission"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_invitesend_permission_comment"/></span>
				</td>
				<td class="altbg2">
					<input class="radio" type="radio" name="allowmailinvite" value="1" ${userGroup.allowmailinvite==1?"checked":""}> <bean:message key="yes"/> &nbsp; &nbsp;
					<input class="radio" type="radio" name="allowmailinvite" value="0" ${userGroup.allowmailinvite==0?"checked":""}> <bean:message key="no"/>
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_invite_price"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_invite_price_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="inviteprice" value="${userGroup.inviteprice}" maxlength="5">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_invite_buynum"/></b>
					<br />
					<span class="smalltxt"><bean:message key="usergroups_invite_buynum_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="maxinvitenum" value="${userGroup.maxinvitenum}" maxlength="2">
				</td>
			</tr>
			<tr>
				<td width="45%" class="altbg1">
					<b><bean:message key="usergroups_invite_maxinviteday"/></b> <br />
					<span class="smalltxt"><bean:message key="usergroups_invite_maxinviteday_comment"/></span>
				</td>
				<td class="altbg2">
					<input type="text" size="50" name="maxinviteday" value="${userGroup.maxinviteday}" maxlength="5">
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td colspan="6">
				<bean:message key="usergroups_edit_credits"/>-<bean:message key="usergroups_edit_raterange"/>
			</td>
		</tr>
		<tr align="center" class="category">
			<td> &nbsp; </td>
			<td>
				<bean:message key="credits_id"/>
			</td>
			<td>
				<bean:message key="credits_title"/>
			</td>
			<td>
				<bean:message key="usergroups_edit_raterange_min"/>
			</td>
			<td>
				<bean:message key="usergroups_edit_raterange_max"/>
			</td>
			<td>
				<bean:message key="usergroups_edit_raterange_mrpd"/>
			</td>
		</tr>
		<c:forEach items="${extcredit}" var="ext">
		<tr align="center">
		<c:set scope="page" value="ok" var="rangvalues"></c:set>
		<c:forEach items="${rangresult}" var="rang">
		<c:if test="${rang.key==ext.key}">
			<c:set scope="page" value="${rang.value}" var="rangvalues"></c:set>
		</c:if>
		</c:forEach>
		<c:choose>
			<c:when test="${rangvalues!='ok'}">
			<td class="altbg1">
				<input class="checkbox" type="checkbox" name="raterange_allowrate[${ext.key}]" value="${ext.key}" checked>
			</td>
			<td class="altbg2">
				extcredits${ext.key}
			</td>
			<td class="altbg1">
				${ext.value.title}
			</td>
			<c:forEach items="${rangvalues}" var="rangvalue" varStatus="index">
				<c:choose>
					<c:when test="${index.count==2}">
					<td class="altbg1">
					<input type="text" name="raterange[${index.count}${ext.key}]" size="3" value="${rangvalue}">
					</td>
					</c:when>
					<c:otherwise>
					<td class="altbg2">
					<input type="text" name="raterange[${index.count}${ext.key}]" size="3" value="${rangvalue}">
					</td>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			</c:when>
			<c:otherwise>
			<td class="altbg1">
				<input class="checkbox" type="checkbox" name="raterange_allowrate[${ext.key}]" value="${ext.key}">
			</td>
			<td class="altbg2">
				extcredits${ext.key}
			</td>
			<td class="altbg1">
				${ext.value.title}
			</td>
			<td class="altbg2">
				<input type="text" name="raterange[1${ext.key}]" size="3" value="">
			</td>
			<td class="altbg1">
				<input type="text" name="raterange[2${ext.key}]" size="3" value="">
			</td>
			<td class="altbg2">
				<input type="text" name="raterange[3${ext.key}]" size="3" value="">
			</td>
			</c:otherwise>
		</c:choose>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="6" class="altbg2">
				<bean:message key="usergroups_edit_raterange_comment"/>
			</td>
		</tr>
	</table>
	<br />
	<center>
		<input class="button" type="submit" name="detailsubmit" value="<bean:message key="submit"/>">
		&nbsp;&nbsp;&nbsp;
		<input class="button" type="submit" name="saveconfigsubmit" value="<bean:message key="saveconf"/>">
	</center>
</form>
<jsp:directive.include file="../cp_footer.jsp" />