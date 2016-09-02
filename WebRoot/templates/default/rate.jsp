<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<c:choose>
	<c:when test="${param.action=='rate'}">
		<c:choose>
			<c:when test="${param.inajax==null}">
				<jsp:include flush="true" page="header.jsp" />
				<div id="nav"><a href="${settings.indexname}">${settings.bbname}</a> &raquo; ${navigation} &raquo; <bean:message key="rate"/>
				</div>
				<form method="post" action="misc.jsp?action=rate" id="postform">
				    <input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
					<input type="hidden" name="page" value="${page}">
					<div class="mainbox formbox">
						<h1> <bean:message key="thread_rate"/> </h1>
						<table summary="<bean:message key="rate"/>" cellspacing="0" cellpadding="0">
							<thead>
								<tr>
									<th> <bean:message key="username"/> </th>
									<td>
										${jsprun_userss} [<a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]
									</td>
								</tr>
							</thead>
							<tr>
								<th> <bean:message key="author"/> </th>
								<td>
									<c:choose>
										<c:when test="${post.author!='' && post.anonymous==0}">
											<a href="space.jsp?uid=${post.authorid}">${post.author}</a></c:when>
										<c:otherwise> <bean:message key="anonymous"/> </c:otherwise>
									</c:choose>
								</td>
							</tr>

							<tr>
								<th> <bean:message key="subject"/> </th>
								<td>
									<a href="viewthread.jsp?tid=${tid}">${thread.subject}</a>
								</td>
							</tr>

							<tr>
								<th> <bean:message key="rate"/> </th>
								<td>
									<c:forEach items="${rangresult}" var="rates">
										<p>
										<select onchange="this.form.score${rates.key}.value=this.value" style="width: 8em">
											<option value="0">
												${extnameMap.name[rates.key-1]}
											</option>
											<option value="0">
												----
											</option>
											<c:forEach items="${rates.value[1]}" var="op">
												<option value="${op}">
													${op}
												</option>
											</c:forEach>
										</select>
										<input type="text" name="score${rates.key}" value="0" size="3" />
										${extnameMap.unit[rates.key-1]==' null'?'': (extnameMap.unit[rates.key-1])}
										<em class="tips">( <bean:message key="rate_quota_today"/> ${rates.value[0]} ${extnameMap.unit[rates.key-1]==' null'?'': (extnameMap.unit[rates.key-1])} )</em>
									</p>
									</c:forEach>
								</td>
							</tr>
							<jsp:include flush="true" page="topicadmin_reason.jsp"/>
							<tr class="btns">
								<th> &nbsp; </th>
								<td>
									<input type="hidden" name="tid" value="${tid}" />
									<input type="hidden" name="pid" value="${pid}" />
									<button type="submit" name="ratesubmit" value="true" id="postsubmit"> <bean:message key="submitf"/> </button> <bean:message key="post_submit_hotkey"/>
								</td>
							</tr>

						</table>
					</div>
				</form>
				<jsp:include flush="true" page="footer.jsp" />
			</c:when>
			<c:otherwise>
				<div class="ajaxform">
					<form method="post" action="misc.jsp?action=rate&inajax=1" id="ratepostform">
						<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
						<input type="hidden" name="page" value="${page}">
						<input type="hidden" name="ratesubmit" value="yes" />
						<table summary="<bean:message key="thread_rate"/>" cellspacing="0" cellpadding="0">
							<thead>
								<tr>
									<th> <bean:message key="thread_rate"/> </th>
									<td align="right">
										<a href="javascript:hideMenu();"><img src="images/spaces/close.gif"  title="<bean:message key="closed"/>" /> </a>
									</td>
								</tr>
							</thead>
							<tr>
								<th> <bean:message key="rate"/> </th>
								<td>
									<c:forEach items="${rangresult}" var="rates">
										<p>
										<select name="sele" onchange="this.form.score${rates.key}.value=this.value"
											style="width: 8em">
											<option value="0">
												${extnameMap.name[rates.key-1]}
											</option>
											<option value="0">
												----
											</option>
											<c:forEach items="${rates.value[1]}" var="op">
												<option value="${op}">
													${op}
												</option>
											</c:forEach>
										</select>
										<input type="text" name="score${rates.key}" value="0" size="3" />
										${extnameMap.unit[rates.key-1]==' null'?'': (extnameMap.unit[rates.key-1])}
										<em class="tips">( <bean:message key="rate_quota_today"/> ${rates.value[0]} ${extnameMap.unit[rates.key-1]==' null'?'': (extnameMap.unit[rates.key-1])} )</em>
									</p>
									</c:forEach>
								</td>
							</tr>
							<jsp:include flush="true" page="topicadmin_reason.jsp"/>
							<tr class="btns">
								<th> &nbsp; </th>
								<td>
									<input type="hidden" name="tid" value="${tid}" />
									<input type="hidden" name="pid" value="${pid}" />
									<button class="submit" type="button" value="true" name="postsubmit" onclick="ajaxpost('ratepostform', 'ajax_rate_${pid}_menu');"> <bean:message key="submitf"/> </button>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
	<jsp:include flush="true" page="header.jsp" />
		<div id="nav"><a href="${settings.indexname}"> ${settings.bbname} </a> &raquo; ${navigation} &raquo;  <bean:message key="removerate"/> </div>
		<form method="post" action="misc.jsp?action=removerate" id="postform">
			<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<input type="hidden" name="referer" value="${referer}" />
			<input type="hidden" name="page" value="${page}" />
			<div class="mainbox formbox">
				<h1> <bean:message key="removerate"/> </h1>
				<table summary="<bean:message key="removerate"/>" cellspacing="0" cellpadding="0">
					<thead>
						<th> <bean:message key="username"/> </th>
						<td>
							${jsprun_userss} [ <a href="logging.jsp?action=logout&formhash=${formhash}"><bean:message key="member_logout"/></a>]
						</td>
					</thead>
					<tr>
						<th> <bean:message key="author"/> </th>
						<td>
							<c:choose>
								<c:when test="${post.author!='' && post.anonymous<=0}">
									<a href="space.jsp?uid=${post.authorid}">${post.author}</a>
								</c:when>
								<c:otherwise><bean:message key="anonymous"/></c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th> <bean:message key="subject"/> </th>
						<td>
							<a href="viewthread.jsp?tid=${post.tid}">${thread.subject}</a>
						</td>
					</tr>
					<jsp:include flush="true" page="topicadmin_reason.jsp"/>
					<tr class="btns">
						<th> &nbsp; </th>
						<td>
							<input type="hidden" name="tid" value="${thread.tid}" />
							<input type="hidden" name="pid" value="${post.pid}" />
							<button type="submit" name="ratesubmit" value="true" id="postsubmit"> <bean:message key="submitf"/> </button> <bean:message key="post_submit_hotkey"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="mainbox formbox">
				<table summary="<bean:message key="removerate"/>" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<td> <input type="checkbox" name="chkall" onclick="checkall(this.form, 'logid')" /> <bean:message key="del"/> </td>
							<td> <bean:message key="username"/> </td>
							<td> <bean:message key="time"/> </td>
							<td> <bean:message key="credits"/> </td>
							<td> <bean:message key="reason_rate"/> </td>
						</tr>
					</thead>
					<c:forEach items="${rateloglist}" var="ratelog">
					<tr>
						<td>
							<input type="checkbox" name="logidarray[]" value="${ratelog.uid} ${ratelog.extcredits} ${ratelog.operateTime}" />
						</td>
						<td>
							<a href="space.jsp?uid=${ratelog.uid}">${ratelog.firstUsername}</a>
						</td>
						<td>
							<jrun:showTime timeInt="${ratelog.operateTime}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/>
						</td>
						<td>
							${ratelog.markValue}
						</td>
						<td>
							${ratelog.reason}
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</form>
		<jsp:include flush="true" page="footer.jsp" />
	</c:otherwise>
</c:choose>