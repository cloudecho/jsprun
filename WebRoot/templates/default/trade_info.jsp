<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<script type="text/javascript" src="include/javascript/viewthread.js"></script>
<script type="text/javascript">
lang['zoom_image'] = '<bean:message key="zoom_image"/>';
lang['a_system_js_newwindow_blank'] = '<bean:message key="a_system_js_newwindow_blank"/>';
lang['full_size'] = '<bean:message key="full_size"/>';
lang['closed'] = '<bean:message key="closed"/>';
lang['copy_to_cutedition'] = '<bean:message key="copy_to_cutedition"/>';
zoomstatus = parseInt(${settings.zoomstatus});
</script>
<div id="nav">
	<a id="forumlist" href="${settings.indexname}" ${settings.forumjump==1&&settings.jsmenu_1>0?"class=dropmenu onmouseover=showMenu(this.id)":""}>${settings.bbname}</a> ${navigation} &raquo; ${trade.subject}
</div>
<div class="mainbox viewthread tradeinfo">
	<h1>${trade.subject}</h1>
	<div class="tradethumb">
	<c:choose>
		<c:when test="${attachmap[trade.pid]!=null}">
			<a href="###"><img src="${attachmap[trade.pid]}" onclick="zoom(this, '${attachmap[trade.pid]}')" onload="thumbImg(this)" width="${settings.tradeimagewidth}" height="${settings.tradeimageheight}" alt="${trade.subject}" /></a>
		</c:when>
		<c:otherwise>
			<img src="${styles.IMGDIR}/trade_nophoto.gif" alt="${trade.subject}" />
		</c:otherwise>
	</c:choose>
	</div>
	<div class="tradeattribute" style="width: 30%">
		<dl>
		<c:if test="${trade.costprice>0.0}"><dt><bean:message key="trade_costprice"/>:</dt><dd><del>${trade.costprice} <bean:message key="rmb_yuan"/></del></dd></c:if>
		<dt><bean:message key="trade_price"/>:</dt>
		<dd><strong>${trade.price}</strong>&nbsp;<bean:message key="rmb_yuan"/></dd>
		<dt><bean:message key="trade_transport"/>:</dt>
		<dd>
			<c:if test="${trade.transport==1}"><bean:message key="post_trade_transport_seller"/></c:if>
			<c:if test="${trade.transport==2 || trade.transport==4}">
				<c:if test="${trade.transport==4}"><bean:message key="post_trade_transport_physical"/></c:if>
				<c:choose>
					<c:when test="${trade.ordinaryfee>0 || trade.expressfee>0 || trade.emsfee>0}">
						<c:if test="${trade.ordinaryfee>0}"><bean:message key="post_trade_transport_mail"/>${trade.ordinaryfee}<bean:message key="rmb_yuan"/></c:if>
						<c:if test="${trade.expressfee>0}"><bean:message key="post_trade_transport_express"/>${trade.expressfee}<bean:message key="rmb_yuan"/></c:if>
						<c:if test="${trade.emsfee>0}">EMS${trade.emsfee}<bean:message key="rmb_yuan"/></c:if>
					</c:when>
					<c:when test="${trade.transport==2}">
						<bean:message key="post_trade_transport_none"/>
					</c:when>
				</c:choose>
			</c:if>
			<c:if test="${trade.transport==3}"><bean:message key="post_trade_transport_virtual"/></c:if>
		</dd>
		<c:if test="${!empty trade.locus}"><dt><bean:message key="trade_locus"/>:</dt><dd>${trade.locus}</dd></c:if>
		</dl>
		<c:if test="${post.authorid!=jsprun_uid && thread.closed==0 && trade.closed==0}">
			<c:if test="${trade.amount>0}"><a href="###" onclick="window.open('trade.jsp?tid=${post.tid}&pid=${post.pid}','','')"><img src="${styles.IMGDIR}/trade_buy_${sessionScope['org.apache.struts.action.LOCALE']}.gif" border="0" /></a> &nbsp;
				<a href="###" onclick="window.open('pm.jsp?action=send&uid=${trade.sellerid}&tradepid=${post.pid}','','')"><img src="${styles.IMGDIR}/trade_pm_${sessionScope['org.apache.struts.action.LOCALE']}.gif" border="0" /></a><br /><br />
			</c:if>
		</c:if>
		<c:if test="${trade.account>0}"><bean:message key="post_trade_supportalipay"/><br /><br /></c:if>
		<dl>
			<dt><bean:message key="trade_type"/>:</dt>
			<dd>
				<c:if test="${trade.quality==1}"><bean:message key="trade_new"/></c:if>
				<c:if test="${trade.quality==2}"><bean:message key="trade_old"/></c:if>
				<c:if test="${trade.itemtype==1}"><bean:message key="thread_trade"/></c:if>
				<c:if test="${trade.itemtype==2}"><bean:message key="trade_type_service"/></c:if>
				<c:if test="${trade.itemtype==3}"><bean:message key="trade_type_auction"/></c:if>
				<c:if test="${trade.itemtype==4}"><bean:message key="trade_type_donate"/></c:if>
				<c:if test="${trade.itemtype==5}"><bean:message key="trade_type_compensate"/></c:if>
				<c:if test="${trade.itemtype==6}"><bean:message key="trade_type_bonus"/></c:if>
			</dd>
			<dt><bean:message key="trade_remaindays"/>:</dt>
			<dd>
			<c:choose>
				<c:when test="${trade.closed==1}"><em><bean:message key="trade_timeout"/></em></c:when>
				<c:when test="${trade.expiration>0}">${trade.expiration}<bean:message key="ipban_days"/>${trade.expirationhour}<bean:message key="hr"/></c:when>
				<c:when test="${trade.expiration==-1}"><em><bean:message key="trade_timeout"/></em></c:when>
				<c:otherwise>&nbsp;</c:otherwise>
			</c:choose>
			</dd>
			<dt><bean:message key="post_trade_number"/>:</dt><dd>${trade.amount}</dd>
			<dt><bean:message key="post_trade_buynumber"/>:</dt><dd>${trade.totalitems}</dd>
		</dl>
	</div>
	<div class="box sellerinfo postauthor">
		<h4><bean:message key="trade_sellerinfo"/>: <a href="space.jsp?uid=${post.uid}">${post.username}</a></h4>
		<dl>
			<dt><bean:message key="eccredit_sellerinfo"/></dt><dd>${shellcredit} <a href="eccredit.jsp?uid=${post.uid}" target="_blank"><img src="images/rank/seller/${shellcredit}.gif" border="0" class="absmiddle"></a></dd>
			<dt><bean:message key="eccredit_buyerinfo"/></dt><dd>${buycredit} <a href="eccredit.jsp?uid=${post.uid}" target="_blank"><img src="images/rank/buyer/${buycredit}.gif" border="0" class="absmiddle"></a></dd>
			<c:if test="${spacedata.limitmytrades>0}">
				<dt><a href="space.jsp?action=mytrades&uid=${post.uid}" target="_blank"><bean:message key="trade_view_space"/></a></dt><dd>&nbsp;</dd>
			</c:if>
			<dt><a href="viewthread.jsp?tid=${post.tid}"><bean:message key="trade_view_all"/></a></dt><dd>&nbsp;</dd>
		</dl>
			<c:if test="${post.qq!='' || post.icq!='' || post.yahoo!='' || post.taobao!=''}">
				<p class="imicons">
					<c:if test="${post.qq!=''}"><a href="http://wpa.qq.com/msgrd?V=1&amp;Uin=${post.qq}&amp;Site=${settings.bbname}&amp;Menu=yes" target="_blank"><img src="${styles.IMGDIR}/qq.gif" alt="QQ" /></a></c:if>
					<c:if test="${post.icq!=''}"><a href="http://wwp.icq.com/scripts/search.dll?to=${post.icq}" target="_blank"><img src="${styles.IMGDIR}/icq.gif" alt="ICQ" /> </a></c:if>
					<c:if test="${post.yahoo!=''}"><a href="http://edit.yahoo.com/config/send_webmesg?.target=${post.yahoo}&amp;.src=pg" target="_blank"><img src="${styles.IMGDIR}/yahoo.gif" alt="Yahoo!" /></a></c:if>
					<c:if test="${post.taobao!=''}"><script type="text/javascript">document.write('<a target="_blank" href="http://amos1.taobao.com/msg.ww?v=2&amp;uid='+encodeURIComponent('${post.taobao}')+'&amp;s=2"><img src="${styles.IMGDIR}/taobao.gif" alt="<bean:message key="taobao"/>" /></a>');</script></c:if>
				</p>
			</c:if>
			<dl class="profile"><c:forEach items="${custominfo.special}" var="cus">${cus}</c:forEach></dl>
	</div>
	<p class="postinfo"><bean:message key="trade_goodsinfo"/></p>
	<div class="postmessage">
		${post.message}
		<c:if test="${post.attachment>0}">
			<c:choose>
				<c:when test="${!allowgetattach}">
					<div class="notice" style="width: 500px">
						<bean:message key="attachment"/>:<em><bean:message key="attach_nopermission"/></em>
					</div>
				</c:when>
				<c:otherwise>
					<div class="box postattachlist">
						<h4><bean:message key="attachment"/></h4>
						<c:forEach items="${attaurl}" var="atta">
							<dl class="t_attachlist">
								<dt>
									<img src="images/attachicons/${attatype[atta.filetype]}" border="0" class="absmiddle" alt="" />
									<a href="attachment.jsp?aid=${atta.aid}">${atta.filename}</a>
									<em>(<jrun:showFileSize size="${atta.filesize}"/>)</em>
								</dt>
								<dd>
									<p> <jrun:showTime timeInt="${atta.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
										<c:if test="${settings.attachrefcheck==0 && atta.isimage<1}">,<bean:message key="attach_downloads"/>: ${atta.downloads}</c:if><c:if test="${atta.readperm>0}">,<bean:message key="threads_readperm"/>: ${atta.readperm}</c:if><c:if test="${atta.price>0}">,<bean:message key="magics_price"/>: ${atta.price}&nbsp;[<a href="misc.jsp?action=viewattachpayments&amp;aid=${atta.aid}" target="_blank"><bean:message key="pay_view"/></a>]&nbsp;<c:if test="${atta.isprice==5}">[<a href="misc.jsp?action=attachpay&amp;aid=${atta.aid}" target="_blank"><bean:message key="attachment_buy"/></a>]</c:if></c:if><c:if test="${atta.description!=''}"><br>${atta.description}</c:if>
									</p>
									<c:if test="${showimag && settings.attachimgpost==1 && atta.isimage>0 && (readaccess >= atta.readperm || atta.authorid==jsprun_uid)}">
									<c:choose>
									<c:when test="${settings.attachrefcheck==0}"><p><a href="#zoom"><img onclick="zoom(this, '${atta.attachment}')" src="${atta.attachmentvalue}" alt="${atta.filename}" /></a>
									</p></c:when>
									<c:otherwise>
										<c:choose>
										<c:when test="${atta.thumb==1}">
										<p><a href="#zoom"><img onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" src="attachment.jsp?aid=${atta.aid}&amp;noupdate=yes" alt="${atta.filename}" /></a></p>
										</c:when>
										<c:otherwise>
											<p> <img src="attachment.jsp?aid=${atta.aid}&amp;noupdate=yes" border="0" onload="attachimg(this, 'load')" onmouseover="attachimg(this, 'mouseover')" onclick="zoom(this, 'attachment.jsp?aid=${atta.aid}')" alt="01.gif" /> </p>
										</c:otherwise>
										</c:choose>
									</c:otherwise>
									</c:choose>
									</c:if>
								</dd>
							</dl>
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
			</c:if>
	</div>
	<div class="postactions">
		<strong onclick="scroll(0,0)">TOP</strong>&nbsp;
		<c:if test="${post.authorid==jsprun_uid}"><a href="my.jsp?item=selltrades&tid=${post.tid}&pid=${post.pid}" target="_blank"><bean:message key="trade_show_log"/></a>&nbsp;</c:if>
		<c:if test="${(usergroups.alloweditpost>0 && modertar&&(post.adminid<=0 ||jsprun_adminid<=post.adminid)) || (thread.alloweditpost>0 && thread.authorid==jsprun_uid)}">
			<a href="post.jsp?action=edit&amp;fid=${post.fid}&amp;tid=${post.tid}&amp;pid=${post.pid}&amp;page=1&amp;extra=page%3D1"><bean:message key="edit"/></a>&nbsp;
		</c:if>
		<c:if test="${allowpostreply}"><a href="post.jsp?action=reply&amp;fid=${post.fid}&amp;tid=${post.tid}&amp;repquote=${post.pid}&amp;extra=${extra}&amp;page=${page}"><bean:message key="reply_quote"/></a></c:if>
	</div>
</div>
<c:if test="${settings.forumjump==1&&settings.jsmenu_1>0}">
	<div class="popupmenu_popup" id="forumlist_menu" style="display: none">${forummenu}</div>
</c:if>
<jsp:include flush="true" page="footer.jsp" />