<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<script src="include/javascript/viewthread.js" type="text/javascript"></script>
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="view_profile"/></div>
<div class="mainbox viewthread specialthread">
	<h6><bean:message key="space_profile"/></h6>
	<table summary="Profile" cellspacing="0" cellpadding="0">
		<tr>
			<td class="postcontent">
				<h1> <bean:message key="somebodys_profile" arg0="${userinfo.username}"/></h1>
				<table summary="Profile" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<td colspan="2" align="center">
								[ <a href="eccredit.jsp?uid=${member.uid}" target="_blank"><bean:message key="a_extends_ec_credit"/></a> ]&nbsp;
								<c:if test="${usergroups.allowmagics>0&&settings.magicstatus>0}">[ <a href="magic.jsp?action=user&amp;username=<jrun:encoding value="${member.username}"/>" target="_blank"><bean:message key="magics_use"/></a> ]&nbsp;</c:if>
								[ <a href="search.jsp?srchuid=${member.uid}&amp;srchfid=all&amp;srchfrom=0&amp;searchsubmit=yes&formHash=${jrun:formHash(pageContext.request)}"><bean:message key="search_posts"/></a> ]&nbsp; 
								<c:if test="${usergroups.allowedituser==1||usergroups.allowbanuser==1}">
									<c:choose>
										<c:when test="${jsprun_adminid==1}">[ <a href="admincp.jsp?action=members&uids=${member.uid}&searchsubmit=yes&frames=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank"><bean:message key="menu_member_edit"/></a> ]&nbsp;</c:when>
										<c:otherwise>[ <a href="admincp.jsp?action=editmember&uid=${member.uid}&membersubmit=yes&frames=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank"><bean:message key="menu_member_edit"/></a> ]&nbsp;</c:otherwise>
									</c:choose>
									[ <a href="admincp.jsp?action=banmember&uid=${member.uid}&membersubmit=yes&frames=yes" target="_blank"><bean:message key="menu_member_ban"/></a> ]&nbsp;
								</c:if>
								<c:if test="${member.adminid>0&&settings.modworkstatus>0}">[ <a href="stats.jsp?type=modworks&uid=${member.uid}"><bean:message key="moderations"/></a> ]</c:if>
							</td>
						</tr>
					</thead>
					<tr>
						<th><bean:message key="a_setting_uid"/>:</th>
						<td>${member.uid}</td>
					</tr>
					<tr>
						<th><bean:message key="regdate"/>:</th>
						<td><jrun:showTime timeInt="${member.regdate}" type="${dateformat}" timeoffset="${timeoffset}"/></td>
					</tr>
					<c:if test="${usergroups.allowviewip>0&&allowip}">
						<tr>
							<th><bean:message key="home_online_regip"/>:</th>
							<td>${member.regip} ${empty member.regip?'' : ipaddress2}</td>
						</tr>
						<tr>
							<th><bean:message key="a_member_edit_lastip"/></th>
							<td>${member.lastip} ${empty member.lastip?'' : ipaddress}</td>
						</tr>
					</c:if>
					<tr>
						<th><bean:message key="a_member_edit_lastvisit"/></th>
						<td><jrun:showTime timeInt="${member.lastvisit}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
					</tr>
					<tr>
						<th><bean:message key="a_post_threads_lastpost"/>:</th>
						<td><jrun:showTime timeInt="${member.lastpost}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
					</tr>
					<tr>
						<th><bean:message key="a_member_edit_pageviews"/>:</th>
						<td>${member.pageviews}</td>
					</tr>
					<tr>
						<th valign="top"><bean:message key="stats_onlinetime"/>:</th>
						<td><bean:message key="onlinetime_total"/> <span class="bold"><fmt:formatNumber scope="page" maxFractionDigits="2" value="${onlitime.total/60}" var="total" />${total}</span> <bean:message key="hr"/>, <bean:message key="onlinetime_thismonth"/> <span class="bold"><fmt:formatNumber scope="page" maxFractionDigits="2" value="${onlitime.thismonth/60}" var="mouth" />${mouth}</span> <bean:message key="hr"/> <jrun:showstars num="${onlinstars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/><br /> <bean:message key="onlinetime_upgrade" arg0="${olupgrade}"/></td>
					</tr>
					<c:if test="${modertarlist!=null}">
						<tr>
							<th><bean:message key="usergroups_system_3"/>:</th>
							<td><c:forEach items="${modertarlist}" var="modertar" varStatus="in"><c:if test="${in.index!=0}">,</c:if><a href="forumdisplay.jsp?fid=${modertar.fid}">${modertar.name}</a></c:forEach></td>
						</tr>
					</c:if>
					<thead><tr><td colspan="2" style="line-height: 3px; font-size: 3px;">&nbsp;</td></tr></thead>
					<c:if test="${medallist!=null}">
						<tr>
							<th><bean:message key="medals"/>:</th>
							<td>${medallist}</td>
						</tr>
					</c:if>
					<c:if test="${usergroups.allownickname>0}">
						<tr>
							<th><bean:message key="a_member_edit_nickname"/></th>
							<td>
								<c:choose>
									<c:when test="${memberfild.nickname==''}"><bean:message key="none"/></c:when>
									<c:otherwise>${memberfild.nickname}</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:if>
					<tr>
						<th valign="top"><bean:message key="menu_member_usergroups"/>:</th>
						<td><font color="${usergroup.color}">
							<c:choose>
								<c:when test="${isfounder}"><bean:message key="a_setting_initiator"/></c:when>
								<c:otherwise>${users.grouptitle}</c:otherwise>
							</c:choose>
						</font>&nbsp;<jrun:showstars num="${users.groupstars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/> ${users.grouptime}</td>
					</tr>
					<c:if test="${users.extgroup!=''}">
						<tr>
							<th><bean:message key="extgroups"/>:</th>
							<td>${users.extgroup}</td>
						</tr>
					</c:if>
					<tr>
						<th><bean:message key="menu_member_ranks"/>:</th>
						<td><font color="${users.rankcolor}">${users.rankname}</font>&nbsp;<jrun:showstars num="${users.stars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/></td>
					</tr>
					<tr>
						<th><bean:message key="threads_readperm"/>:</th>
						<td>${usergroup.readaccess}</td>
					</tr>
					<tr>
						<th><bean:message key="credits"/>:</th>
						<td>${member.credits}</td>
					</tr>
					<c:forEach items="${extcredits}" var="extcredit">
						<c:set var="extcr" value="extcredits${extcredit.key}" />
						<tr>
							<th>${extcredit.value.title}:</th>
							<td>${member[extcr]}&nbsp;${extcredit.value.unit}</td>
						</tr>
					</c:forEach>
					<tr>
						<th><bean:message key="a_setting_posts"/>:</th>
						<td>${users.postnum}</td>
					</tr>
					<tr>
						<th><bean:message key="posts_per_day"/>:</th>
						<td>${users.everyposts} <bean:message key="a_setting_posts"/></td>
					</tr>
					<tr>
						<th><bean:message key="digest"/>:</th>
						<td>${member.digestposts} <bean:message key="a_setting_posts"/></td>
					</tr>
					<thead><tr><td colspan="2" style="line-height: 3px; font-size: 3px;">&nbsp;</td></tr></thead>
					<tr>
						<th><bean:message key="a_member_edit_gender"/></th>
						<td>${users.sex}</td>
					</tr>
					<tr>
						<th><bean:message key="a_member_edit_location"/></th>
						<td>${memberfild.location}</td>
					</tr>
					<tr>
						<th><bean:message key="a_member_edit_bday"/></th>
						<td>${member.bday}</td>
					</tr>
					${profiledis}
				</table>
			</td>
			<td class="postauthor">
				<div class="avatar">
				<c:choose>
					<c:when test="${userinfo.avoras!=''}"><img src="${userinfo.avoras}" alt="" height="${userinfo.height}" width="${userinfo.width}"/></c:when>
					<c:otherwise><img src="images/avatars/noavatar.gif" alt="" /></c:otherwise>
				</c:choose>
				</div>
				<ul>
					<li class="pm"><a href="pm.jsp?action=send&amp;uid=${member.uid}" target="_blank"><bean:message key="send_pm"/></a></li>
					<li class="buddy"><a href="my.jsp?item=buddylist&amp;newbuddyid=${member.uid}&amp;buddysubmit=yes&formHash=${jrun:formHash(pageContext.request)}" id="ajax_buddy" onclick="ajaxmenu(event, this.id)"><bean:message key="add_to_buddylist"/></a></li>
				</ul>
				<dl class="profile">
					<c:if test="${memberfild.site!=''}">
						<dt><bean:message key="homepage"/>:</dt>
						<dd><a href="${memberfild.site}" target="_blank">${memberfild.site}</a></dd>
					</c:if>
					<c:if test="${member.showemail>0}">
						<dt><bean:message key="email"/>:</dt>
						<dd><a href="mailto:${member.email}">${member.email}</a></dd>
					</c:if>
					<c:if test="${memberfild.qq!=''}">
						<dt><bean:message key="a_member_edit_qq"/></dt>
						<dd><a href="http://wpa.qq.com/msgrd?V=1&amp;Uin=${memberfild.qq}&amp;Site=JspRun!&amp;Menu=yes" target="_blank"><img src="http://wpa.qq.com/pa?p=1:${memberfild.qq}:4" border="0" alt="QQ" />${memberfild.qq}</a></dd>
					</c:if>
					<c:if test="${memberfild.icq!='' && memberfild.icq!=null}">
						<dt><bean:message key="a_member_edit_icq"/></dt>
						<dd>${memberfild.icq}</dd>
					</c:if>
					<c:if test="${memberfild.yahoo!='' && memberfild.yahoo!=null}">
						<dt><bean:message key="a_member_edit_yahoo"/></dt>
						<dd>${memberfild.yahoo}</dd>
					</c:if>
					<c:if test="${memberfild.msn!='' && memberfild.msn !=null}">
						<dt><bean:message key="a_member_edit_msn"/></dt>
						<dd>${memberfild.msn}</dd>
					</c:if>
					<c:if test="${memberfild.taobao!=''}">
						<dt><bean:message key="a_member_edit_taobao"/></dt>
						<dd><script type="text/javascript">document.write('<a target="_blank" href="http://amos1.taobao.com/msg.ww?v=2&amp;uid='+encodeURIComponent('${memberfild.taobao}')+'&amp;s=2"><img src="http://amos1.taobao.com/online.ww?v=2&amp;uid='+encodeURIComponent('${memberfild.taobao}')+'&amp;s=1" alt="<bean:message key="a_member_edit_taobao"/>" border="0" /></a>');</script></dd>
					</c:if>
					<c:if test="${memberfild.alipay!=null && memberfild.alipay!=''}">
						<dt><bean:message key="a_member_edit_alipay"/></dt>
						<dd><a href="https://www.tenpay.com/" target="_blank">${memberfild.alipay}</a></dd>
					</c:if>
					<dt><bean:message key="trade_buyer_rate"/>:</dt>
					<dd>${memberfild.buyercredit}<a href="eccredit.jsp?uid=${member.uid}" target="_blank"><img src="images/rank/buyer/${buycredit}.gif" border="0" class="absmiddle"></a></dd>
					<dt><bean:message key="trade_seller_rate"/>:</dt>
					<dd>${memberfild.sellercredit}<a href="eccredit.jsp?uid=${member.uid}" target="_blank"><img src="images/rank/seller/${shellcredit}.gif" border="0" class="absmiddle"> </a></dd>
				</dl>
			</td>
		</tr>
	</table>
</div>
<jsp:include flush="true" page="footer.jsp" />