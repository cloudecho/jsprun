<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<c:if test="${!empty attachmentlist}">
	<div class="mainbox">
		<h3><bean:message key="attachment"/></h3>
		<table summary="<bean:message key="attachment"/>" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<td class="selector"><bean:message key="del"/></td>
					<td>aid</td>
					<td><bean:message key="attachment"/></td>
					<td class="time"><bean:message key="attachment_date"/></td>
					<td class="nums" nowrap="nowrap"><bean:message key="attachment_size"/></td>
					<td class="nums" nowrap="nowrap"><bean:message key="attach_downloads"/></td>
					<td nowrap="nowrap"><bean:message key="threads_readperm"/></td>
					<td><bean:message key="magics_price"/></td>
					<td><bean:message key="description"/></td>
				</tr>
			</thead>
			<c:forEach items="${attachmentlist}" var="atta">
				<tr>
					<td class="selector"><input class="checkbox" type="checkbox" name="deleteaid[]" value="${atta.aid}"></td>
					<td nowrap="nowrap">
						<c:choose>
							<c:when test="${atta.isimage==1}"><a href="###" onclick="insertAttachimgTag('${atta.aid}','')" title="<bean:message key="post_attachment_insert"/>">${atta.aid}</a></c:when>
							<c:otherwise><a href="###" onclick="insertAttachTag('${atta.aid}')" title="<bean:message key="post_attachment_insert"/>">${atta.aid}</a></c:otherwise>
						</c:choose>
					</td>
					<td>
					<div id="attach${atta.aid}">
					<a href="###" class="right" onclick="attachupdate('${atta.aid}')">[<bean:message key="update"/>]</a>
					<c:choose>
					<c:when test="${atta.isimage==1}"><a href="###" class="right" onclick="insertAttachimgTag('${atta.aid}','')" title="<bean:message key="post_attachment_insert"/>">[<bean:message key="post_attachment_insertlink"/>]</a></c:when>
					<c:otherwise><a href="###" class="right" onclick="insertAttachTag('${atta.aid}')" title="<bean:message key="post_attachment_insert"/>">[<bean:message key="post_attachment_insertlink"/>]</a></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${atta.isimage==1}">
							<c:choose>
								<c:when test="${editormode>0||allowswitcheditor>0}">
									<span id="imgpreview_${atta.aid}" onmouseover="showMenu(this.id, 0, 0, 1, 0)"><img src="images/attachicons/${attatypemap[atta.filetype]}"> <a href="attachment.jsp?aid=${atta.aid}&noupdate=yes&nothumb=yes" target="_blank">${atta.filename}</a></span>
									<div class="popupmenu_popup" id="imgpreview_${atta.aid}_menu" style="position:absolute;top:-100000px;margin-left: 20px;text-align: center"><img id="preview_${atta.aid}" onload="if(this.width < ${settings.thumbwidth}) this.style.width = 'auto'; else this.style.width = '${settings.thumbwidth}px';$('imgpreview_${atta.aid}_menu').style.width = this.clientWidth" src="${atta.attachment}" /></div>
								</c:when>
								<c:otherwise>
									<img src="images/attachicons/${attatypemap[atta.filetype]}"> <a href="attachment.jsp?aid=${atta.aid}&noupdate=yes&nothumb=yes" target="_blank">${atta.filename}</a>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
						<img src="images/attachicons/${attatypemap[atta.filetype]}"><a href="attachment.jsp?aid=${atta.aid}" target="_blank">${atta.filename}</a>
						</c:otherwise>
					</c:choose>
					</div>
					<span id="attachupdate${atta.aid}"><input type="hidden" name="add[${atta.aid}]" value="1"></span>
					</td>
					<td class="time"><jrun:showTime timeInt="${atta.dateline}" type="${dateformat} ${timeformat}" timeoffset="${timeoffset}"/></td>
					<td class="nums"><jrun:showFileSize size="${atta.filesize}"/></td>
					<td class="nums">${atta.downloads}</td>
					<td><input type="text" size="3" name="attachperm[${atta.aid}]" value="${atta.readperm}"></td>
					<td><input type="text" size="3" name="attachprice[${atta.aid}]" value="${atta.price}"></td>
					<td><input type="text" size="25" name="attachdesc[${atta.aid}]" value="${atta.description}"></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>