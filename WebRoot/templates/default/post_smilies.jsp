<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="include/javascript/post.js"></script>
<h4><span <c:if test="${smileytypesize>1}">id="${editorid}_popup_smileytypes" onmouseover="showMenu(this.id, true, 0, 2)" class="dropmenu"</c:if>>${smileytypes[typeid].name}</span></h4>
<table summary="smilies" cellpadding="0" cellspacing="0">
<tr align="center">
<c:forEach items="${smilies}" var="smiley" varStatus="index"><c:if test="${index.count<=smsize}"><td align="center" id="smilie_${smiley.id}_parent" onMouseover="smileyMenu(this)" onClick="insertSmiley('${smiley.id}')"><img src="images/smilies/${smileytypes[typeid].directory}/${smiley.url}" id="smilie_${smiley.id}" alt="${smiley.code}" title="${settings.smthumb}" width="${settings.smthumb}" height="${settings.smthumb}" border="0" /></td><c:if test="${index.count%settings.smcols==0&&index.count!=smsize}"></tr><tr height="${settings.smthumb+6}"></c:if></c:if></c:forEach>
</tr>
</table>
${multi.multipage}
<c:if test="${smileytypesize>1}">
	<ul unselectable="on" class="popupmenu_popup" id="${editorid}_popup_smileytypes_menu" style="display: none">
		<c:forEach items="${smileytypes}" var="smileytype"><li unselectable="on"><a href="post.jsp?action=smilies&typeid=${smileytype.key}&inajax=1" ajaxtarget="smilieslist">${smileytype.value.name}</a></li></c:forEach>
	</ul>
</c:if>