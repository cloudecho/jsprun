<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<jsp:include flush="true" page="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}"> ${settings.bbname} </a> ${navigation} &raquo; <bean:message key="post_editpost_tradegoods"/>
</div>
<script type="text/javascript">
lang['calendar_Sun'] = '<bean:message key="calendar_Sun"/>';
lang['calendar_Mon'] = '<bean:message key="calendar_Mon"/>';
lang['calendar_Tue'] = '<bean:message key="calendar_Tue"/>';
lang['calendar_Wed'] = '<bean:message key="calendar_Wed"/>';
lang['calendar_Thu'] = '<bean:message key="calendar_Thu"/>';
lang['calendar_Fri'] = '<bean:message key="calendar_Fri"/>';
lang['calendar_Sat'] = '<bean:message key="calendar_Sat"/>';
lang['old_month'] = '<bean:message key="old_month"/>';
lang['select_year'] = '<bean:message key="select_year"/>';
lang['select_month'] = '<bean:message key="select_month"/>';
lang['next_month'] = '<bean:message key="next_month"/>';
lang['calendar_hr'] = '<bean:message key="calendar_hr"/>';
lang['calendar_min'] = '<bean:message key="calendar_min"/>';
lang['calendar_month'] = '<bean:message key="calendar_month"/>';
lang['calendar_today'] = '<bean:message key="calendar_today"/>';
</script>
<script src="include/javascript/calendar.js" type="text/javascript"></script>
<script type="text/javascript">
var postminchars = parseInt('${settings.minpostsize}');
var postmaxchars = parseInt('${settings.maxpostsize}');
var disablepostctrl = parseInt('${usergroups.disablepostctrl}');
var typerequired = parseInt('${threadtypes.required}');
var attachments = new Array();
var bbinsert = parseInt('${settings.bbinsert}');
var tradepost = parseInt('${special == 2?1:0}');
var isfirstpost = parseInt('${isfirstpost?1:0}');
var attachimgurl = new Array();
var allowposttrade = parseInt('${usergroups.allowposttrade}');
lang['board_allowed'] = '<bean:message key="board_allowed"/>';
lang['lento'] = '<bean:message key="lento"/>';
lang['bytes'] = '<bean:message key="bytes"/>';
lang['post_curlength'] = '<bean:message key="post_curlength"/>';
lang['post_subject_and_message_isnull'] = '<bean:message key="post_subject_and_message_isnull"/>';
lang['post_subject_toolong'] = '<bean:message key="post_subject_toolong"/>';
lang['post_message_length_invalid'] = '<bean:message key="post_message_length_invalid"/>';
lang['post_trade_alipay_null'] = '<bean:message key="post_trade_alipay_null"/>';
lang['post_trade_goodsname_null'] = '<bean:message key="post_trade_goodsname_null"/>';
lang['post_trade_price_null'] = '<bean:message key="post_trade_price_null"/>';
</script>
<jsp:include flush="true" page="post_preview.jsp" />
<form method="post" id="postform" action="post.jsp?action=edit&amp;fid=${thread.fid}&amp;tid=${thread.tid}&amp;pid=${post.pid}&amp;extra=${extra}&amp;editsubmit=yes&formHash=${jrun:formHash(pageContext.request)}" enctype="multipart/form-data">
	<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
	<input type="hidden" name="page" value="${page}" />
	<div class="mainbox formbox">
		<h1> <bean:message key="post_editpost_tradegoods"/> </h1>
		<table summary="Edit Trade" cellspacing="0" cellpadding="0" id="specialpost">
			<thead>
				<tr> <th> <bean:message key="username"/> </th>
					<td>
					<c:choose>
							<c:when test="${jsprun_uid>0}">${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]</c:when>
							<c:otherwise><bean:message key="guest"/> [<a href="logging.jsp?action=login"><bean:message key="member_login"/></a>]</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</thead>
			<tr>
				<th> <label for="subject"> <bean:message key="subject"/> </label> </th>
				<td>
					<c:if test="${threadtypes.types!=null}">${typeselect}</c:if>
					<input type="text" name="subject" id="subject" size="45" value="${post.subject}" tabindex="3" />
					<input type="hidden" name="origsubject" value="${post.subject}" />
				</td>
			</tr>
			<c:choose>
				<c:when test="${special==2 }">
					<thead>
						<tr>
						<th><bean:message key="post_goodsinfo"/></th>
						<td>&nbsp;</td>
						</tr>
					</thead>
					<jsp:include flush="true" page="post_trade.jsp" />
				</c:when>
				<c:otherwise>
					<tr><jsp:include flush="true" page="post_editor.jsp" /></tr>
				</c:otherwise>
			</c:choose>
			<c:if test="${isfirstpost}">
				<c:choose>
					<c:when test="${special==2 }">
						<thead>
							<tr>
							<th><bean:message key="post_goodsinfo"/></th>
							<td>&nbsp;</td>
							</tr>
						</thead>
					</c:when>
					<c:otherwise>
						<c:if test="${settings.tagstatus>0}">
							<tr>
							<th><label for="tags"><bean:message key="post_tag"/></label></th>
								<td>
									<input size="45" type="input" id="tags" name="tags" value="${tags}" tabindex="95" />&nbsp; <span id="tagselect"></span> <em class="tips"><bean:message key="tag_comment"/></em>
								</td>
							</tr>
						</c:if>
						<tr>
							<th><label for="aboutthread"><bean:message key="post_trade_aboutcounter"/></label></th>
							<td><textarea name="aboutcounter" id="aboutcounter" rows="10" cols="20" style="width:99%; height:60px" tabindex="96">${aboutcounter}</textarea></td>
						</tr>
					</c:otherwise>
				</c:choose>
					<c:if test="${usergroups.allowsetreadperm>0}">
					<tr>
						<th><label for="readperm"><bean:message key="readperm_thread"/></label></th>
						<td><input type="text" id="readperm" name="readperm" size="6" value="${thread.readperm}" /> (<bean:message key="post_zero_is_nopermission"/>)</td>
					</tr>
					</c:if>
			</c:if>
			<tr class="btns">
				<th>&nbsp;</th>
				<td>
					<input type="hidden" name="special" value="2">
					<input type="hidden" name="isshop" value="${special==2}">
					<input type="hidden" name="isfirst" value="${isfirstpost}">
					<input type="hidden" name="page" value="${param.page}">
					<input type="hidden" name="wysiwyg" id="${editorid}_mode" value="${editormode}" />
					<input type="hidden" name="fid" id="fid" value="${fid}" />
					<input type="hidden" name="tid" value="${thread.tid}" />
					<input type="hidden" name="pid" value="${post.pid}" />
					<input type="hidden" name="postsubject" value="${post.subject}" />
					<button type="submit" name="editsubmit" id="postsubmit" value="true" tabindex="101"> <c:choose> <c:when test="${special==2}"> <bean:message key="post_editpost_tradegoods"/> </c:when> <c:when test="${isfirstpost}"> <bean:message key="post_editpost_trade"/> </c:when> <c:otherwise> <bean:message key="post_editpost_tradegoods"/> </c:otherwise> </c:choose> </button>
					<input type="hidden" name="wysiwyg" id="${editorid}_mode" value="${editormode}" />
					<em><bean:message key="post_submit_hotkey"/></em>&nbsp;&nbsp;
					&nbsp;<a href="###" id="restoredata" onclick="loadData()" title="<bean:message key="post_autosave_last_restore"/>"><bean:message key="post_autosave_restore"/></a>
				</td>
			</tr>
		</table>
	</div>
<jsp:include flush="true" page="post_editpost_attachlist.jsp"/>
</form>
<jsp:include flush="true" page="post_js.jsp" />
<jsp:include flush="true" page="footer.jsp" />
