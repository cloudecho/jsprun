<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="space_header.jsp" />
<div class="outer">
	<table class="main" border="0" cellspacing="0">
		<tr>
			<td id="main_layout0"><jsp:include flush="true" page="space_module_userinfo.jsp" /></td>
			<td id="main_layout1">
				<div id="module_userdetails">
					<table class="module" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td class="header">
								<div class="title"><bean:message key="space_userdetails"/></div>
								<div class="more">
									<c:if test="${jsprun_uid==member.uid}"><a href="memcp.jsp?action=profile" target="_blank"><bean:message key="memcp_profile"/></a></c:if>
									<a href="eccredit.jsp?uid=${member.uid}" target="_blank"><bean:message key="a_extends_ec_credit"/></a>
									<c:if test="${usergroups.allowmagics>0&&settings.magicstatus>0}"><a href="magic.jsp?action=user&amp;username=<jrun:encoding value="${user.username}"/>" target="_blank"><bean:message key="magics_use"/></a></c:if>
									<a href="search.jsp?srchuid=${member.uid}&amp;srchfid=all&amp;srchfrom=0&amp;searchsubmit=yes&formHash=${jrun:formHash(pageContext.request)}"><bean:message key="search_posts"/></a>
									<c:if test="${usergroups.allowedituser==1||usergroups.allowbanuser==1}">
										<c:choose>
											<c:when test="${jsprun_adminid==1}"><a href="admincp.jsp?action=members&uids=${member.uid}&searchsubmit=yes&frames=yes&seasubmit=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank"><bean:message key="menu_member_edit"/></a></c:when>
											<c:otherwise><a href="admincp.jsp?action=editmember&uid=${member.uid}&membersubmit=yes&frames=yes&formHash=${jrun:formHash(pageContext.request)}" target="_blank"><bean:message key="menu_member_edit"/></a></c:otherwise>
										</c:choose>
										<a href="admincp.jsp?action=banmember&uid=${member.uid}&membersubmit=yes&frames=yes" target="_blank"><bean:message key="menu_member_ban"/></a>
									</c:if>
									<c:if test="${member.adminid>0&&settings.modworkstatus>0}"><a href="stats.jsp?type=modworks&uid=${member.uid}"><bean:message key="moderations"/></a></c:if>
								</div>
							</td>
						</tr>
					</table>
					<table class="info" border="0" cellspacing="0" cellpadding="1px" width="100%">
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
								<th><bean:message key="a_member_edit_regip"/></th>
								<td>${member.regip} ${empty member.regip?'' : ipaddress2}</td>
							</tr>
							<tr>
								<th><bean:message key="a_member_edit_lastip"/></th>
								<td>${member.lastip} ${empty member.lastip?'' : ipaddress}</td>
							</tr>
						</c:if>
						<tr>
							<th><bean:message key="a_member_edit_lastvisit"/></th>
							<td><jrun:showTime timeInt="${member.lastvisit}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}" /></td>
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
							<td><bean:message key="onlinetime_total"/> <span class="bold"><fmt:formatNumber scope="page" maxFractionDigits="2" value="${onlitime.total/60}" var="total" />${total}</span> <bean:message key="hr"/>, <bean:message key="onlinetime_thismonth"/> <span class="bold"><fmt:formatNumber scope="page" maxFractionDigits="2" value="${onlitime.thismonth/60}" var="mouth" />${mouth}</span> <bean:message key="hr"/> <jrun:showstars num="${onlinstars}" starthreshold="${settings.starthreshold}" imgdir="${styles.IMGDIR}"/><br /><bean:message key="onlinetime_upgrade" arg0="${olupgrade}"/></td>
						</tr>
						<c:if test="${modertarlist!=null}">
							<tr>
								<th><bean:message key="usergroups_system_3"/>:</th>
								<td><c:forEach items="${modertarlist}" var="modertar" varStatus="in"><c:if test="${in.index!=0}">,</c:if><a href="forumdisplay.jsp?fid=${modertar.fid}">${modertar.name}</a></c:forEach></td>
							</tr>
						</c:if>
						<tr><td colspan="2"><hr class="line" size="0"></td></tr>
						<c:if test="${medallist!=null}">
						<tr>
							<th><bean:message key="medals"/>:</th>
							<td>${medallist}</td>
						</tr>
						</c:if>
						<tr>
							<th><bean:message key="menu_member_usergroups"/>:</th>
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
						<tr>
							<td colspan="2"><hr class="line" size="0"></td>
						</tr>
						<tr>
							<th><bean:message key="a_member_edit_gender"/></th>
							<td>${users.sex}</td>
						</tr>
						<c:if test="${memberfild.location!=null && memberfild.location!=''}">
						<tr>
							<th><bean:message key="a_member_edit_location"/></th>
							<td>${memberfild.location}</td>
						</tr>
						</c:if>
						<tr>
							<th><bean:message key="a_member_edit_bday"/></th>
							<td>${member.bday}</td>
						</tr>
						<c:if test="${memberfild.site!=''}">
							<tr>
								<th>
									<bean:message key="homepage"/>:
								</th>
								<td>
									<a href="${memberfild.site}" target="_blank">${memberfild.site}</a>
								</td>
							</tr>
						</c:if>
						<c:if test="${member.showemail>0}">
							<tr>
								<th><bean:message key="email"/>:</th>
								<td><a href="mailto:${member.email}">${member.email}</a></td>
							</tr>
						</c:if>
						<c:if test="${memberfild.qq!=''}">
							<tr>
								<th><bean:message key="a_member_edit_qq"/></th>
								<td><a href="http://wpa.qq.com/msgrd?V=1&amp;Uin=${memberfild.qq}&amp;Site=JspRun!&amp;Menu=yes" target="_blank"><img src="http://wpa.qq.com/pa?p=1:${memberfild.qq}:4" border="0" alt="QQ" />${memberfild.qq}</a></td>
							</tr>
						</c:if>
						<c:if test="${memberfild.icq!='' && memberfild.icq!=null}">
							<tr>
								<th><bean:message key="a_member_edit_icq"/></th>
								<td>${memberfild.icq}</td>
							</tr>
						</c:if>
						<c:if test="${memberfild.yahoo!='' && memberfild.yahoo!=null}">
							<tr>
								<th><bean:message key="a_member_edit_yahoo"/></th>
								<td>${memberfild.yahoo}</td>
							</tr>
						</c:if>
						<c:if test="${memberfild.msn!='' && memberfild.msn !=null}">
							<tr>
								<th><bean:message key="a_member_edit_msn"/></th>
								<td>${memberfild.msn}</td>
							</tr>
						</c:if>
						<c:if test="${memberfild.taobao!=''}">
							<tr>
								<th><bean:message key="a_member_edit_taobao"/></th>
								<td><script type="text/javascript">document.write('<a target="_blank" href="http://amos1.taobao.com/msg.ww?v=2&amp;uid='+encodeURIComponent('${memberfild.taobao}')+'&amp;s=2"><img src="http://amos1.taobao.com/online.ww?v=2&amp;uid='+encodeURIComponent('${memberfild.taobao}')+'&amp;s=1" alt="<bean:message key="taobao"/>" border="0" /></a>');</script></td>
							</tr>
						</c:if>
						<c:if test="${memberfild.alipay!=null && memberfild.alipay!=''}">
							<tr>
								<th><bean:message key="a_member_edit_alipay"/></th>
								<td><a href="https://www.tenpay.com/" target="_blank">${memberfild.alipay}</a></td>
							</tr>
						</c:if>
						<tr>
							<th><bean:message key="trade_buyer_rate"/>:</th>
							<td>${memberfild.buyercredit} <a href="eccredit.jsp?uid=${member.uid}" target="_blank"><img src="images/rank/buyer/${buycredit}.gif" border="0" class="absmiddle"></a></td>
						</tr>
						<tr>
							<th><bean:message key="trade_seller_rate"/>:</th>
							<td>${memberfild.sellercredit} <a href="eccredit.jsp?uid=${member.uid}" target="_blank"><img src="images/rank/seller/${shellcredit}.gif" border="0" class="absmiddle"></a></td>
						</tr>
						${profiledis}
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
<jsp:include flush="true" page="space_footer.jsp" />