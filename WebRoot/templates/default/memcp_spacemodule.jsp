<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/jrun-tag.tld" prefix="jrun"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><bean:message key="space_settings"/> - Powered by JspRun!</title>
		<link rel="stylesheet" type="text/css" id="stylecss" href="mspace/${memberspace.style}/style.css">
		<link rel="stylesheet" type="text/css" href="images/spaces/memcp.css">
		<script src="include/javascript/common.js" type="text/javascript"></script>
		<script src="include/javascript/bbcode.js" type="text/javascript"></script>
		<script src="include/javascript/menu.js" type="text/javascript"></script>
		<script src="include/javascript/drag.js" type="text/javascript"></script>
		<script src="include/javascript/drag_space.js" type="text/javascript"></script>
		<script type="text/javascript">
			var layout = ['${layout1}','${layout2}','${layout3}'];
			var space_layout_nocenter='<bean:message key="space_layout_nocenter"/>';
		</script>
		<link rel="SHORTCUT ICON" href="favicon.ico" />
	</head>
	<body>
		<form id="dragform" method="post" action="space.jsp?action=editspacemodule&editsubmit=yes">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
			<table id="panel" align="center">
				<tr>
					<th>
						<table class="menu" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td><span id="basemenu" onMouseOver="showMenu(this.id)"><a href="###"><bean:message key="header_basic"/></a></span></td>
								<td><span id="stylemenu" onMouseOver="showMenu(this.id)"><a href="###"><bean:message key="space_style_settings"/></a></span></td>
								<td><span id="layoutmenu" onMouseOver="showMenu(this.id)"><a href="###"><bean:message key="space_layout_settings"/></a></span></td>
								<td><span id="modulemenu" onMouseOver="showMenu(this.id)"><a href="###"><bean:message key="space_module_settings"/></a></span></td>
							</tr>
						</table>
					</th>
					<th align="right" width="300"><div class="save"><div><a href="###" onClick="previewLayout(${member.uid})"><img src="images/spaces/preview.gif" border="0" /> <bean:message key="preview"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="###" onClick="saveLayout()"><img src="images/spaces/save.gif" border="0" /> <bean:message key="space_save"/></a></div><b><bean:message key="space_memcp"/></b></div></th>
				</tr>
			</table>
			<input type="hidden" id="spacelayout0" name='spacelayout[0]' />
			<input type="hidden" id="spacelayout1" name='spacelayout[1]' />
			<input type="hidden" id="spacelayout2" name='spacelayout[2]' />
			<input type="hidden" name="spacesubmit" value="TRUE" />
			<div class="cp_menu" id="basemenu_menu" style="display: none">
				<div class="title"><div style="float:right"><img onClick="hideMenu()" class="dragdel" src="images/spaces/close.gif" /></div><bean:message key="header_basic"/></div>
				<table width="100%" style="table-layout: fixed">
					<tr><td width="25%"><bean:message key="space_name"/></td><td width="75%"><input type="text" maxlength="40" onKeyUp="previewtext('pre_title', this.value)" name="spacename" style="width: 100%" value="${memberfild.spacename}" /></td></tr>
					<tr><td width="25%"><bean:message key="space_desc"/></td><td width="75%"><input type="text" maxlength="100" onKeyUp="previewtext('pre_desc', this.value)" name="spacedescription" style="width: 100%" value="${memberspace.description}" /></td></tr>
				</table>
			</div>
			<div class="cp_menu" id="stylemenu_menu" style="display: none">
				<div class="title"><div style="float:right"><img onClick="hideMenu()" class="dragdel" src="images/spaces/close.gif" /></div><bean:message key="space_style_settings"/></div>
				<table width="100%">
					<tr>
						<td align="center">
							<a href="###" onClick="setStyle('default')"><img src="mspace/default/demo.gif" width="100" height="100" border="0" /></a><br />
							<input onclick="setStyle('default')" class="radio" type="radio" name="spacestyle" id="style_default" value="default" ${memberspace.style=='default'?'checked':''}/><bean:message key="space_style_1"/>
							<br />[<a href="space.jsp?uid=${member.uid}&style=default" target="_blank"><bean:message key="preview"/></a>]
						</td>
						<td align="center">
							<a href="###" onClick="setStyle('default6')"><img src="mspace/default6/demo.gif" width="100" height="100" border="0" /></a><br />
							<input onclick="setStyle('default6')" class="radio" type="radio" name="spacestyle" id="style_default6" value="default6" ${memberspace.style=='default6'?'checked':''}/><bean:message key="space_style_7"/>
							<br />[<a href="space.jsp?uid=${member.uid}&style=default6" target="_blank"><bean:message key="preview"/></a>]
						</td>
						<td align="center">
							<a href="###" onClick="setStyle('default1')"><img src="mspace/default1/demo.gif" width="100" height="100" border="0" /></a><br />
							<input onclick="setStyle('default1')" class="radio" type="radio" name="spacestyle" id="style_default1" value="default1" ${memberspace.style=='default1'?'checked':''} /><bean:message key="space_style_2"/>
							<br />[<a href="space.jsp?uid=${member.uid}&style=default1" target="_blank"><bean:message key="preview"/></a>]
						</td>
						<td align="center">
							<a href="###" onClick="setStyle('default2')"><img src="mspace/default2/demo.gif" width="100" height="100" border="0" /></a><br />
							<input onclick="setStyle('default2')" class="radio" type="radio" name="spacestyle" id="style_default2" value="default2" ${memberspace.style=='default2'?'checked':''}/><bean:message key="space_style_3"/>
							<br />[<a href="space.jsp?uid=${member.uid}&style=default2" target="_blank"><bean:message key="preview"/></a>]
						</td>
					</tr>
					<tr>
						<td align="center">
							<a href="###" onClick="setStyle('default3')"><img src="mspace/default3/demo.gif" width="100" height="100" border="0" /> </a><br />
							<input onclick="setStyle('default3')" class="radio" type="radio" name="spacestyle" id="style_default3" value="default3" ${memberspace.style=='default3'?'checked':''}/><bean:message key="space_style_4"/>
							<br />[<a href="space.jsp?uid=${member.uid}&style=default3" target="_blank"><bean:message key="preview"/></a>]
						</td>
						<td align="center">
							<a href="###" onClick="setStyle('default4')"><img src="mspace/default4/demo.gif" width="100" height="100" border="0" /> </a><br />
							<input onclick="setStyle('default4')" class="radio" type="radio" name="spacestyle" id="style_default4" value="default4" ${memberspace.style=='default4'?'checked':''} /><bean:message key="space_style_5"/>
							<br />[<a href="space.jsp?uid=${member.uid}&style=default4" target="_blank"><bean:message key="preview"/></a>]
						</td>
						<td align="center">
							<a href="###" onClick="setStyle('default5')"><img src="mspace/default5/demo.gif" width="100" height="100" border="0" /> </a><br />
							<input onclick="setStyle('default5')" class="radio" type="radio" name="spacestyle" id="style_default5" value="default5" ${memberspace.style=='default5'?'checked':''} /><bean:message key="space_style_6"/>
							<br />[<a href="space.jsp?uid=${member.uid}&style=default5" target="_blank"><bean:message key="preview"/></a>]
						</td>
						<td></td>
					</tr>
				</table>
			</div>
			<div class="cp_menu" id="layoutmenu_menu" style="display: none">
				<div class="title"><div style="float:right"><img onClick="hideMenu()" class="dragdel" src="images/spaces/close.gif" /></div><bean:message key="space_layout_settings"/></div>
				<table width="100%">
					<tr>
						<td align="center">
							<a href="###" onClick="leftSide()"><img src="images/spaces/layout1.gif" border="0" /></a><br />
							<input onClick="leftSide()" class="radio" type="radio" name="spaceside" id="side_1" value="1" ${memberspace.side==1?'checked':''} /><bean:message key="space_leftside"/>
							<a href="###" onClick="rightSide()"><img src="images/spaces/layout2.gif" border="0" /></a><br />
							<input onClick="rightSide()" class="radio" type="radio" name="spaceside" id="side_2" value="2" ${memberspace.side==2?'checked':''} /><bean:message key="space_rightside"/>
							<a href="###" onClick="bothSide()"><img src="images/spaces/layout3.gif" border="0" /></a><br />
							<input onClick="bothSide()" class="radio" type="radio" name="spaceside" id="side_0" value="0" ${memberspace.side==0?'checked':''} /><bean:message key="space_bothside"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="cp_menu" id="modulemenu_menu" style="display: none">
				<div class="title"><div style="float:right"><img onClick="hideMenu()" class="dragdel" src="images/spaces/close.gif" /></div><bean:message key="space_module_settings"/></div>
				<div><input type='checkbox' class="checkbox" id="check_userinfo" onclick="Drag.handler.check(0,'userinfo','<bean:message key="userinfo"/>','1')" value='' disabled /> <bean:message key="userinfo"/></div>
				<div><input type='checkbox' class="checkbox" id="check_calendar" onclick="Drag.handler.check(0,'calendar','<bean:message key="calendar"/>','1')" value='' /> <bean:message key="calendar"/></div>
				<c:if test="${spacemap.limitmyblogs!=0}"><div><input type='checkbox' class="checkbox" id="check_myblogs" onclick="Drag.handler.check(1,'myblogs','<bean:message key="myblogs"/>','')" value='' /> <bean:message key="myblogs"/></div></c:if>
				<c:if test="${spacemap.limitmytrades!=0}"><div><input type='checkbox' class="checkbox" id="check_mythreads" onclick="Drag.handler.check(1,'mythreads','<bean:message key="thread"/>','')" value='' /> <bean:message key="thread"/></div></c:if>
				<c:if test="${spacemap.limitmyreplies!=0}"><div><input type='checkbox' class="checkbox" id="check_myreplies" onclick="Drag.handler.check(1,'myreplies','<bean:message key="threads_replies"/>','')" value='' /> <bean:message key="threads_replies"/></div></c:if>
				<c:if test="${spacemap.limitmyrewards!=0}"><div><input type='checkbox' class="checkbox" id="check_myrewards" onclick="Drag.handler.check(1,'myrewards','<bean:message key="thread_reward"/>','')" value='' /> <bean:message key="thread_reward"/></div></c:if>
				<c:if test="${spacemap.limitmytrades!=0}"><div><input type='checkbox' class="checkbox" id="check_mytrades" onclick="Drag.handler.check(1,'mytrades','<bean:message key="mytrades"/>','')" value='' /> <bean:message key="mytrades"/></div></c:if>
				
				<c:if test="${spacemap.limitmyfriends!=0}"><div><input type='checkbox' class="checkbox" id="check_myfriends" onclick="Drag.handler.check(2,'myfriends','<bean:message key="myfriends"/>','1')" value='' /> <bean:message key="myfriends"/></div></c:if>
				<c:if test="${spacemap.limitmyfavforums!=0}"><div><input type='checkbox' class="checkbox" id="check_myfavforums" onclick="Drag.handler.check(2,'myfavforums','<bean:message key="myfavforums"/>','1')" value='' /> <bean:message key="myfavforums"/></div></c:if>
				<c:if test="${spacemap.limitmyfavthreads!=0}"><div><input type='checkbox' class="checkbox" id="check_myfavthreads" onclick="Drag.handler.check(2,'myfavthreads','<bean:message key="myfavthreads"/>','1')" value='' /> <bean:message key="myfavthreads"/></div></c:if>
			</div>
		</form>
		<div id="menu_top">
			<div class="bgleft"></div>
			<div class="bg"><span><bean:message key="space_welcome"/> ${member.username}&nbsp; &nbsp;<a href="pm.jsp" target="_blank"><bean:message key="pm"/></a> | <a href="index.jsp" target="_blank"><bean:message key="space_returnboard"/></a></span></div>
			<div class="bgright"></div>
		</div>
		<div id="header">
			<div id="headerbg" class="bg">
			<div id="pre_title_default" style="display:none">${member.username}<bean:message key="space_userspace"/></div>
			<div class="title" id="pre_title"> <c:choose><c:when test="${!empty memberfild.spacename}">${memberfild.spacename}</c:when> <c:otherwise>${member.username}<bean:message key="space_userspace"/></c:otherwise></c:choose></div>
			<div class="desc" id="pre_desc" style="overflow: hidden">${memberspace.description}</div>
			<div class="headerurl"><a href="space.jsp?uid=${member.uid}" class="spaceurl">${boardurl}space.jsp?uid=${member.uid}</a><a href="###"><bean:message key="space_copylink"/></a> | <a href="###"><bean:message key="space_addfav"/></a></div></div>
		</div>
		<div id="menu">
			<div class="block">
				<div style="float: left"><a href="space.jsp?uid=${member.uid}"><bean:message key="space_index"/></a> <c:if test="${usergroups.allowviewpro>0}">&nbsp;<a href="space.jsp?action=viewpro&uid=${member.uid}"><bean:message key="userinfo"/></a></c:if></div>
				<div id="menuitem_myblogs" style="float: left; display: none;">&nbsp;&nbsp;&nbsp;<a href="space.jsp?action=myblogs&uid=${member.uid}"><bean:message key="myblogs"/></a></div>
				<div id="menuitem_mythreads" style="float: left; display: none;">&nbsp;&nbsp;&nbsp;<a href="space.jsp?action=mythreads&uid=${member.uid}"><bean:message key="thread"/></a></div>
				<div id="menuitem_myreplies" style="float: left; display: none;">&nbsp;&nbsp;&nbsp;<a href="space.jsp?action=myreplies&uid=${member.uid}"><bean:message key="threads_replies"/></a></div>
				<div id="menuitem_myrewards" style="float: left; display: none;">&nbsp;&nbsp;&nbsp;<a href="space.jsp?action=myrewards&uid=${member.uid}"><bean:message key="thread_reward"/></a></div>
				<div id="menuitem_mytrades" style="float: left; display: none;">&nbsp;&nbsp;&nbsp;<a href="space.jsp?action=mytrades&uid=${member.uid}"><bean:message key="mytrades"/></a></div>
				<div id="menuitem_myvideos" style="float: left; display: none;">&nbsp;&nbsp;&nbsp;<a href="space.jsp?action=myvideos&uid=${member.uid}"><bean:message key="thread_video"/></a></div>
				<div id="menuitem_myfavforums" style="float: left; display: none;">&nbsp;&nbsp;&nbsp;<a href="space.jsp?action=myfavforums&uid=${member.uid}"><bean:message key="myfavforums"/></a></div>
				<div id="menuitem_myfavthreads" style="float: left; display: none;">&nbsp;&nbsp;&nbsp;<a href="space.jsp?action=myfavthreads&uid=${member.uid}"><bean:message key="myfavthreads"/></a></div>
				<span id="menuitem_userinfo"></span><span id="menuitem_calendar"></span><span id="menuitem_myfriends"></span>
			</div>
			<div class="control"><a href="###"><bean:message key="space_settings"/></a></div>
			<div class="icon"></div>
		</div>
		<div id="outer" class="outer">
			<table class="main" border="0" cellspacing="0" id="parentTable">
				<tr>
					<td id="main_layout0" style='${memberspace.side==2?"display:none":""}'>
						<c:forEach items="${layoutlist1}" var="layout">
							<div id="${layout}" class="dragdiv">
								<table class="module" width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr onMouseDown="Drag.dragStart(event, '${spacemodules[layout]}')"><td class="header"><div class="title">${spacelaguage[layout]}</div><c:if test="${layout!='userinfo'}"><div class="more"><img onClick="Drag.handler.del(${layout})" class="dragdel" src="images/spaces/close.gif" /></div></c:if></td></tr>
									<tr><td class="dragtext"><div>${spacelaguage[layout]}</div></td></tr> 
								</table>
							<script type="text/javascript">checkinit('${layout}');</script>
						  </div>
						</c:forEach> 
					</td>
					<td id="main_layout1">
						<c:forEach items="${layoutlist2}" var="layout">
							<div id="${layout}" class="dragdiv">
								<table class="module" width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr onMouseDown="Drag.dragStart(event, '${spacemodules[layout]}')"><td class="header"><div class="title">${spacelaguage[layout]}</div><div class="more"><img onClick="Drag.handler.del(${layout})" class="dragdel" src="images/spaces/close.gif" /></div></td></tr>
									<tr><td class="dragtext"><div>${spacelaguage[layout]}</div></td></tr>
								</table>
								<script type="text/javascript">checkinit('${layout}');</script>
							</div>
						</c:forEach>
					</td>
					<td id="main_layout2" align="right" style="display:${memberspace.side==1?'none':''}">
						<c:forEach items="${layoutlist3}" var="layout">
							<div id="${layout}" class="dragdiv">
								<table class="module" width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr onMouseDown="Drag.dragStart(event, '${spacemodules[layout]}')"><td class="header"><div class="title">${spacelaguage[layout]}</div><c:if test="${layout!='userinfo'}"><div class="more"><img onClick="Drag.handler.del(${layout})" class="dragdel" src="images/spaces/close.gif" /></div></c:if></td></tr>
									<tr><td class="dragtext"><div>${spacelaguage[layout]}</div></td></tr>
								</table>
								<script type="text/javascript">checkinit('${layout}');</script>
							</div>
						</c:forEach>
					</td>
				</tr>
			</table>
		</div>
		<jsp:include flush="true" page="/footer.do?action=footer"/>
		<div id="footer">
			<div>Powered by <a href="http://www.jsprun.net" target="_blank" style="color: blue"><b>JspRun!</b></a> ${settings.version} &nbsp;&copy; 2007-2011 <a href="http://www.jsprun.com" target="_blank">JspRun! Inc.</a>
			<c:if test="${settings.debug>0}"><br />Processed in ${debuginfo.time} second(s)<c:if test="${settings.gzipcompress>0}">, Gzip enabled</c:if></c:if>
		</div>
		</div>
		<div id="dragClone" style="display:none">
			<div id="[id]" class="dragDIV">
				<table class="module" width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr onMouseDown="Drag.dragStart(event,'[disable]');">
						<td class="header">
							<div class="title">[title]</div>
							<div class="more"><img onClick="Drag.handler.del([id])" class="dragDel" src="images/spaces/close.gif"></div>
						</td>
					</tr>
					<tr><td class="dragtext"><div>[title]</div></td></tr>
				</table>
			</div>
		</div>
		<script type="text/javascript">
		Drag.init(Space_Memcp);
		$('side_${memberspace.side}').checked = true;
		var tmp_spaceside = ${memberspace.side};
		var tmp_styledir = '${memberspace.style}';
		</script>
	</body>
</html>