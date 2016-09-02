<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<h1><bean:message key="my_rewards"/></h1>
<ul class="tabs">
	<li ${type== 'stats'?"class=current":""}><a href="my.jsp?item=reward&type=stats${extrafid}"><bean:message key="my_reward_stat"/></a></li>
	<li ${type== 'question'?"class=dropmenu hover current":"class=dropmenu"}><a href="my.jsp?item=reward&type=question${extrafid}" id="myquestion" onmouseover="showMenu(this.id)"><bean:message key="my_reward_questions"/></a></li>
	<li ${type== 'answer'?"class=dropmenu hover current":"class=dropmenu"}><a href="my.jsp?item=reward&type=answer${extrafid}" id="myanswer" onmouseover="showMenu(this.id)"><bean:message key="my_reward_answers"/></a></li>
</ul>
<ul class="popupmenu_popup headermenu_popup" id="myquestion_menu" style="display: none"><li><a href="my.jsp?item=reward&type=question${extrafid}&filter="><bean:message key="my_reward_all_question"/></a></li><li><a href="my.jsp?item=reward&type=question${extrafid}&filter=solved"><bean:message key="my_reward_question_solved"/></a></li><li><a href="my.jsp?item=reward&type=question${extrafid}&filter=unsolved"><bean:message key="my_reward_question_unsolved"/></a></li></ul>
<ul class="popupmenu_popup headermenu_popup" id="myanswer_menu" style="display: none"><li><a href="my.jsp?item=reward&type=answer${extrafid}&filter="><bean:message key="my_reward_all_answer"/></a></li><li><a href="my.jsp?item=reward&type=answer${extrafid}&filter=adopted"><bean:message key="my_reward_answer_adopted"/></a></li><li><a href="my.jsp?item=reward&type=answer${extrafid}&filter=unadopted"><bean:message key="my_reward_answer_unadopted"/></a></li></ul>
<div class="msgtabs" style='${type== "stats"? "display: none;":""}'><strong><c:choose><c:when test="${type=='question'}"><bean:message key="my_reward_questions"/> &#8212; <c:choose><c:when test="${filter==''}"><bean:message key="my_reward_all_question"/></c:when><c:when test="${filter=='solved'}"><bean:message key="my_reward_question_solved"/></c:when><c:when test="${filter=='unsolved'}"><bean:message key="my_reward_question_unsolved"/></c:when></c:choose></c:when><c:when test="${type=='answer'}"><bean:message key="my_reward_answers"/> &#8212; <c:choose><c:when test="${filter==''}"><bean:message key="my_reward_all_answer"/></c:when><c:when test="${filter=='adopted'}"><bean:message key="my_reward_answer_adopted"/></c:when><c:when test="${filter=='unadopted'}"><bean:message key="my_reward_answer_unadopted"/></c:when></c:choose></c:when></c:choose></strong></div>
<table cellspacing="0" cellpadding="0" width="100%">
	<c:if test="${type!='stats'}"><thead><tr><td><bean:message key="my_reward_name"/></td><td><bean:message key="my_reward_forum"/></td><td>
	<c:choose>
		<c:when test="${type=='question'}"><bean:message key="memcp_reward_log_payment_answerer"/></c:when>
		<c:otherwise><bean:message key="memcp_reward_log_income_author"/></c:otherwise>
	</c:choose>
	</td><td><bean:message key="memcp_reward_total"/></td><c:if test="${type=='question'}"><td><bean:message key="my_reward_real_payment"/></td></c:if><td><bean:message key="my_reward_status"/></td><td><bean:message key="my_reward_dateline"/></td></tr></thead></c:if>
	<tbody>
		<c:choose>
			<c:when test="${type=='stats'}">
				<tr><th><bean:message key="my_reward_question_number"/>:</th><td>${questions.total} <bean:message key="my_reward_unit"/></td></tr>
				<tr><th><bean:message key="my_reward_question_solved_number"/>:</th><td>${questions.solved} <bean:message key="my_reward_unit"/></td></tr>
				<tr><th><bean:message key="my_reward_question_solved_per"/>:</th><td>${questions.percent}</td></tr>
				<tr><th><bean:message key="my_reward_question_price"/>(${extcredits[creditstrans].title}):</th><td>${questions.totalprice} ${extcredits[creditstrans].unit}</td></tr>
				<tr><th><bean:message key="my_reward_answer_number"/>:</th><td>${answers.total} <bean:message key="my_reward_unit"/></td></tr>
				<tr><th><bean:message key="my_reward_answer_adopted_number"/>:</th><td>${answers.adopted} <bean:message key="my_reward_unit"/></td></tr>
				<tr><th><bean:message key="my_reward_answer_adopted_per"/>:</th><td>${answers.percent}</td></tr>
				<tr><th><bean:message key="my_reward_anser_price"/>(${extcredits[creditstrans].title}):</th><td>${answers.totalprice} ${extcredits[creditstrans].unit}</td></tr>
			</c:when>
			<c:when test="${type=='question'}">
				<c:forEach items="${rewardloglists}" var="rewardloglist"><tr>
					<td><a href="viewthread.jsp?tid=${rewardloglist.tid}">${rewardloglist.subject}</a></td>
					<td><a href="forumdisplay.jsp?fid=${rewardloglist.fid}">${rewardloglist.name}</a></td>
					<td><c:choose><c:when test="${rewardloglist.uid!=null}"><a href="space.jsp?uid=${rewardloglist.uid}">${rewardloglist.username}</a></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td>
					<td>${extcredits[creditstrans].title} ${rewardloglist.price} ${extcredits[creditstrans].unit}</td>
					<td>${extcredits[creditstrans].title} ${rewardloglist.netamount} ${extcredits[creditstrans].unit}</td>
					<td>
						<c:choose>
							<c:when test="${rewardloglist.answererid>0}"><bean:message key="a_system_js_threads_special_reward_1"/></c:when>
							<c:otherwise><bean:message key="a_system_js_threads_special_reward_2"/></c:otherwise>
						</c:choose>
					</td>
					<td class="time">${rewardloglist.dateline}</td>
				</tr></c:forEach>
				<c:if test="${empty rewardloglists}"><tr><td colspan="7"><bean:message key="my_reward_nonexistence"/></td></tr></c:if>
			</c:when>
			<c:when test="${type=='answer'}">
				<c:forEach items="${rewardloglists}" var="rewardloglist"><tr>
					<td><a href="viewthread.jsp?tid=${rewardloglist.tid}">${rewardloglist.subject}</a></td>
					<td><a href="forumdisplay.jsp?fid=${rewardloglist.fid}">${rewardloglist.name}</a></td>
					<td><c:choose><c:when test="${rewardloglist.uid!=null}"><a href="space.jsp?uid=${rewardloglist.uid}">${rewardloglist.username}</a></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td>
					<td>${extcredits[creditstrans].title} ${rewardloglist.price} ${extcredits[creditstrans].unit}</td>
					<td>
						<c:choose>
							<c:when test="${rewardloglist.authorid>0}"><bean:message key="my_reward_adopted"/></c:when>
							<c:otherwise><bean:message key="my_reward_unadopted"/></c:otherwise>
						</c:choose>
					</td>
					<td class="time">${rewardloglist.dateline}</td>
				</tr></c:forEach>
				<c:if test="${empty rewardloglists}"><tr><td colspan="7"><bean:message key="my_reward_nonexistence"/></td></tr></c:if>
			</c:when>
			<c:otherwise><tr><td><bean:message key="my_reward_nofind"/></td></tr></c:otherwise>
		</c:choose>
	</tbody>
</table>