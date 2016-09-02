<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<table class="module" cellpadding="0" cellspacing="0" border="0">
	<tr><td class="header"><div class="title"><bean:message key="userinfo"/></div></td></tr>
	<tr>
		<td>
			<div id="module_userinfo">
				<div class="status"><bean:message key="a_post_tags_status"/> 
				<span>
				<c:choose>
					<c:when test="${userinfo.isonline && member.invisible==0}"><bean:message key="online"/></c:when>
					<c:otherwise><bean:message key="offline"/></c:otherwise>
				</c:choose>
				</span></div>
				<div class="info">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed; overflow: hidden">
						<tr>
							<td align="center">
								<c:choose>
									<c:when test="${!empty userinfo.avoras}"><img src="${userinfo.avoras}" alt="" height="${userinfo.height}" width="${userinfo.width}"/></c:when>
									<c:otherwise><img src="images/avatars/noavatar.gif" alt=""/></c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
				</div>
				<div class="username">
					${userinfo.username}
					<c:if test="${memberfild.nickname!=''}"><br />${memberfild.nickname}</c:if>
				</div>
				<div class="operation">
					<img src="mspace/default/sendmail.gif" alt="" /><a target="_blank" href="pm.jsp?action=send&amp;uid=${member.uid}"><bean:message key="send_pm"/></a>
					<img src="mspace/default/buddy.gif" alt="" /><a target="_blank" href="my.jsp?item=buddylist&amp;newbuddyid=${member.uid}&amp;buddysubmit=yes&formHash=${jrun:formHash(pageContext.request)}" id="ajax_buddy" onclick="ajaxmenu(event, this.id)"><bean:message key="add_to_buddylist"/></a>
				</div>
				<c:if test="${!empty userinfo.bios}">
				<div class="more">
					<br />
					${userinfo.bios}
				</div>
				</c:if>
			</div>
		</td>
	</tr>
</table>