<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<br />
<div class="mainbox threadlist">
	<ul class="tabs headertabs">
	<c:if test="${(param.filter=='thisweek'||param.filter=='thismonth'||param.filter=='halfyear'||param.filter=='before')&&(param.from=='buyer'||param.from=='seller')}"><li class="current"><a><c:choose><c:when test="${param.filter=='thisweek'}"><bean:message key="eccredit_1week"/></c:when><c:when test="${param.filter=='thismonth'}"><bean:message key="eccredit_1month"/></c:when><c:when test="${param.filter=='halfyear'}"><bean:message key="eccredit_6month"/></c:when><c:otherwise><bean:message key="eccredit_6monthbefore"/></c:otherwise></c:choose> <c:choose><c:when test="${param.from=='buyer'}"><bean:message key="eccredit_local_buyer"/></c:when><c:otherwise><bean:message key="eccredit_local_seller"/></c:otherwise></c:choose><c:choose><c:when test="${param.level=='good'}"><bean:message key="eccredit_good"/></c:when><c:when test="${param.level=='soso'}"><bean:message key="eccredit_soso"/></c:when><c:when test="${param.level=='bad'}"><bean:message key="eccredit_bad"/></c:when><c:otherwise><bean:message key="eccredit1"/></c:otherwise></c:choose></a></li></c:if>
	<c:choose><c:when test="${param.from==null}"><li class="current"><a><bean:message key="eccredit_list_all"/></a></li></c:when><c:otherwise><li><a href="eccredit.jsp?action=list&uid=${param.uid}" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);"><bean:message key="eccredit_list_all"/></a></li></c:otherwise></c:choose>
	<c:choose><c:when test="${param.from=='buyer'&&param.filter==null}"><li class="current"><a><bean:message key="eccredit_list_buyer"/></a></li></c:when><c:otherwise><li><a href="eccredit.jsp?action=list&uid=${param.uid}&from=buyer" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);"><bean:message key="eccredit_list_buyer"/></a></li></c:otherwise></c:choose>
	<c:choose><c:when test="${param.from=='seller'&&param.filter==null}"><li class="current"><a><bean:message key="eccredit_list_seller"/></a></li></c:when><c:otherwise><li><a href="eccredit.jsp?action=list&uid=${param.uid}&from=seller" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);"><bean:message key="eccredit_list_seller"/></a></li></c:otherwise></c:choose>
	<c:choose><c:when test="${param.from=='myself'}"><li class="current"><a><bean:message key="eccredit_list_other"/></a></li></c:when>	<c:otherwise><li><a href="eccredit.jsp?action=list&uid=${param.uid}&from=myself" onclick="ajaxget(this.href, 'ajaxrate', 'specialposts');doane(event);"><bean:message key="eccredit_list_other"/></a></li></c:otherwise></c:choose>
	</ul>
	<table width="100%" cellspacing="0" cellpadding="5" >
		<thead>
		<tr><td>&nbsp;</td><td><bean:message key="eccredit_content"/></td><td><bean:message key="eccredit_goodsname_seller"/></td><td><bean:message key="eccredit_tradeprice"/></td></tr></thead>
		<tbody>
		<c:choose>
			<c:when test="${!empty comments}">
				<c:forEach items="${comments}" var="comment">
					<tr><td>
					<c:choose>
						<c:when test="${comment.score==1}"><img src="images/rank/good.gif" border="0" width="14" height="16"> <font color="FF0000"><bean:message key="eccredit_good"/></font></c:when>
						<c:when test="${comment.score==0}"><img src="images/rank/soso.gif" border="0" width="14" height="16"> <font color="00FF00"><bean:message key="eccredit_soso"/></font></c:when>
						<c:otherwise><img src="images/rank/bad.gif" border="0" width="14" height="16"> <bean:message key="eccredit_bad"/></c:otherwise>
					</c:choose>
					</td><td>${comment.message} <em><jrun:showTime timeInt="${comment.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></em><br />
					<c:choose>
						<c:when test="${!empty comment.explanation}"><em><bean:message key="a_post_jspruncodes_edit_explanation"/> ${comment.explanation}</em></c:when>
						<c:when test="${jsprun_uid>0 && jsprun_uid==comment.rateeid&&comment.dateline>timestamp-30*86400}"><span id="ecce_${comment.id}"><a href="eccredit.jsp?action=explain&id=${comment.id}" id="ajax_${comment.id}_explain" onclick="ajaxmenu(event, this.id, 0, '', 0)">[ <bean:message key="eccredit_needexplanation"/> ]</a> <bean:message key="eccredit_explanationexpiration" arg0="${comment.expiration}"/></span></c:when>
					</c:choose>
				</td><td><a href="redirect.jsp?goto=findpost&special=trade&pid=${comment.pid}" target="_blank">${comment.subject}</a><br />
				<c:choose><c:when test="${param.from=='myself'}"><c:choose><c:when test="${comment.type>0}"><bean:message key="a_extends_tradelog_buyer"/></c:when><c:otherwise><bean:message key="tradelog_seller"/></c:otherwise></c:choose><a href="space.jsp?action=viewpro&uid=${comment.rateeid}" target="_blank">${comment.ratee}</a></c:when><c:otherwise><c:choose><c:when test="${comment.type>0}"><bean:message key="a_extends_tradelog_buyer"/></c:when><c:otherwise><bean:message key="tradelog_seller"/></c:otherwise></c:choose> <a href="space.jsp?action=viewpro&uid=${comment.rateeid}" target="_blank">${comment.rater}</a></c:otherwise></c:choose>
				</td><td>${comment.baseprice}</td></tr>
				</c:forEach>
			</c:when>
			<c:otherwise><tr><td colspan="4" align="center"><bean:message key="eccredit_nofound"/></td></tr></c:otherwise>
		</c:choose>
		</tbody>
	</table>
	</div>
<div class="pages_btns">${multi.multipage}</div>