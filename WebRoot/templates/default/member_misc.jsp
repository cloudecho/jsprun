<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${empty errorInfo}">
		<div class="subtable" style="width: 100%" id="multipage">${multi.multipage}</div>
		<div class="spaceborder" style="clear: both">
			<table cellspacing="INNERBORDERWIDTH" cellpadding="1px" width="100%" align="center" style="table-layout: fixed">
				${avatarlist}
			</table>
		</div>
	</c:when>
	<c:otherwise>${errorInfo}</c:otherwise>
</c:choose>