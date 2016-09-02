<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="a_extends_ec_credit"/></div>
<div class="mainbox viewthread">
	<h1>${member.username} <bean:message key="eccredit_members"/></h1>
	<table summary="eccredit" cellspacing="0" cellpadding="0">
		<tr>
			<td class="postcontent">
				<p class="postinfo"><bean:message key="eccredit_sellerinfo"/></p>
				<div class="postmessage">
					<table cellspacing="0" cellpadding="5">
						<thead><tr><td>&nbsp;</td><td><bean:message key="eccredit_1week"/></td><td><bean:message key="eccredit_1month"/></td><td><bean:message key="eccredit_6month"/></td><td><bean:message key="eccredit_6monthbefore"/></td><td><bean:message key="eccredit_total"/></td></tr></thead>
						<tr>
							<td><img src="images/rank/good.gif" border="0" width="14" height="16"><font color="red"><bean:message key="eccredit_good"/></font></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=thisweek&level=good#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.thisweek.good}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=thismonth&level=good#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.thismonth.good}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=halfyear&level=good#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.halfyear.good}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=before&level=good#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.before.good}</a></td>
							<td>${caches.sellercredit.all.good}</td>
						</tr>
						<tr>
							<td><img src="images/rank/soso.gif" border="0" width="14" height="16"><font color="green"><bean:message key="eccredit_soso"/></font></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=thisweek&level=soso#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.thisweek.soso}</a></td> 
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=thismonth&level=soso#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.thismonth.soso}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=halfyear&level=soso#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.halfyear.soso}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=before&level=soso#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.before.soso}</a></td>
							<td>${caches.sellercredit.all.soso}</td>
						</tr>
						<tr>
							<td><img src="images/rank/bad.gif" border="0" width="14" height="16"><bean:message key="eccredit_bad"/></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=thisweek&level=bad#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.thisweek.bad}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=thismonth&level=bad#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.thismonth.bad}</a></td> 
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=halfyear&level=bad#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.halfyear.bad}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=before&level=bad#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.before.bad}</a></td>
							<td>${caches.sellercredit.all.bad}</td>
						</tr>
						<tr>
							<td><bean:message key="eccredit_total"/></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=thisweek#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.thisweek.total}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=thismonth#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.thismonth.total}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=halfyear#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.halfyear.total}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=buyer&filter=before#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.sellercredit.before.total}</a></td>
							<td>${caches.sellercredit.all.total}</td>
						</tr>
					</table>
				</div>
				<p class="postinfo"><bean:message key="eccredit_buyerinfo"/></p>
				<div class="postmessage">
					<table cellspacing="0" cellpadding="5">
						<thead><tr><td>&nbsp;</td><td><bean:message key="eccredit_1week"/></td><td><bean:message key="eccredit_1month"/></td><td><bean:message key="eccredit_6month"/></td><td><bean:message key="eccredit_6monthbefore"/></td><td><bean:message key="eccredit_total"/></td></tr></thead>
						<tr>
							<td><img src="images/rank/good.gif" border="0" width="14" height="16"><font color="red"><bean:message key="eccredit_good"/></font></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=thisweek&level=good#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.thisweek.good}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=thismonth&level=good#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.thismonth.good}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=halfyear&level=good#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.halfyear.good}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=before&level=good#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.before.good}</a></td>
							<td>${caches.buyercredit.all.good} </td>
						</tr>
						<tr>
							<td><img src="images/rank/soso.gif" border="0" width="14" height="16"><font color="green"><bean:message key="eccredit_soso"/></font></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=thisweek&level=soso#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.thisweek.soso}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=thismonth&level=soso#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.thismonth.soso}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=halfyear&level=soso#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.halfyear.soso}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=before&level=soso#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.before.soso}</a></td>
							<td>${caches.buyercredit.all.soso}</td>
						</tr>
						<tr>
							<td><img src="images/rank/bad.gif" border="0" width="14" height="16"><bean:message key="eccredit_bad"/></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=thisweek&level=bad#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.thisweek.bad}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=thismonth&level=bad#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.thismonth.bad}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=halfyear&level=bad#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.halfyear.bad}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=before&level=bad#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.before.bad}</a></td>
							<td>${caches.buyercredit.all.bad}</td>
						</tr>
						<tr>
							<td><bean:message key="eccredit_total"/></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=thisweek#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.thisweek.total}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=thismonth#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.thismonth.total}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=halfyear#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.halfyear.total}</a></td>
							<td><a href="eccredit.jsp?action=list&uid=${member.uid}&from=seller&filter=before#" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);">${caches.buyercredit.before.total}</a></td>
							<td>${caches.buyercredit.all.total}</td>
						</tr>
					</table>
				</div>
			</td>
			<td class="postauthor">
				${member.avatar}
				<bean:message key="special_author"/>: <a href="space.jsp?uid=${member.uid}">${member.username}</a>
				<c:if test="${!empty member.alipay}"><a href="https://www.tenpay.com" target="_blank"><img src="${styles.IMGDIR}/tenpaysmall.gif" border="0" alt="<bean:message key="payto_creditinfo"/>" /></a></c:if>
				<c:if test="${!empty member.taobao}"><br /><script type="text/javascript">document.write('<a target="_blank" href="http://amos1.taobao.com/msg.ww?v=2&uid='+encodeURIComponent('${member.taobao}')+'&s=2"><img src="http://amos1.taobao.com/online.ww?v=2&uid='+encodeURIComponent('${member.taobao}')+'&s=1" alt="<bean:message key="taobao"/>" border="0" /></a>&nbsp;');</script></c:if>
				<div align="left">
					<bean:message key="eccredit_sellerinfo"/>: ${member.sellercredit} <img src="images/rank/seller/${shellcredit}.gif" border="0" class="absmiddle"><br />
					<bean:message key="eccredit_buyerinfo"/>: ${member.buyercredit} <img src="images/rank/buyer/${buycredit}.gif" border="0" class="absmiddle"><br />
					<bean:message key="eccredit_sellerpercent"/>:${sellerpercent}%<br />
					<bean:message key="eccredit_buyerpercent"/>: ${buyerpercent}%<br />
					<bean:message key="regdate"/>:<jrun:showTime timeInt="${member.regdate}" type="${dateformat}" timeoffset="${timeoffset}"/>
				</div>
			</td>
		</tr>
	</table>
</div>
<br />
<div id="ajaxrate"></div>
<script type="text/javascript">ajaxget('eccredit.jsp?action=list&uid=${member.uid}&rand='+Math.random(), 'ajaxrate');var explainmenu='ajax_explain_menu';</script>
<jsp:include flush="true" page="footer.jsp" />