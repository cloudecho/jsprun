<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<style>
	.float_typeid{float:left;margin-right:6px;}.float_typeid select{float:left;height:20px;}.float_typeid a{display:block;overflow:hidden;text-indent:4px;padding-right:17px;width:77px;height:20px;*padding-top:2px;*height:18px;text-decoration:none !important;color:#444 !important;border:1px solid;border-color:#999 #CCC #CCC #999;background:#FFF url(../../${styles.IMGDIR}/newarow.gif) no-repeat 100% 0;}.float_typeid a:hover{text-decoration:none;border:1px solid #09C;background-position:100% -20px;}.float_postinfo .float_typeid a{line-height:20px;*line-height:18px;}.float_typeid ul{margin:-22px 0 0;border:1px solid #09C;background:#FFF url(../../${styles.IMGDIR}/newarow.gif) no-repeat 100% -20px;}* html .float_typeid ul{margin-top:-23px;}.float_typeid ul li{margin:0 4px;color:#444;cursor:pointer;}.float_typeid ul li:hover{color:#09C;}.newselect .current{font-weight:700;}.float_typeid select{width:94px;}
	.smilieslist{text-align:center;padding:10px;border:1px solid #7FCAE2;background:#FEFEFE;}.smilieslist td{padding:8px;border:none;cursor:pointer;}.smilieslist_page{text-align:right;}
	.smilieslist_table{left:-100px;top:1;*top:-1px;width:100px !important;height:100px;border:1px solid #E6E7E1;background:#FFF;}.smilieslist_preview{text-align:center;vertical-align:middle;z-index:1;}.smiliesgroup ul{margin:6px 0;padding:0 0 24px 8px;border-bottom:1px solid #DDD;}.smiliesgroup li{display:inline;}.smiliesgroup li a{float:left;margin-right:6px;padding:2px 10px;height:24px;height:19px;border:1px solid #DDD;color:#09C;}.smiliesgroup li a.current{border-bottom-color:#FFF;background:#FFF;font-weight:bold;color:#444;}
	.pags_act{float:left;}.pags_act a{display:inline !important;}.smilieslist_page a{display:inline;margin:0 4px;color:#09C;text-decoration:underline;}.right{float:right;}
</style>
<div class="editor_tb">
<span class="right">
	<c:if test="${allowpostreply}">
	<a href="post.jsp?action=reply&amp;fid=${thread.fid}&amp;tid=${thread.tid}&amp;extra=page%3D1&page=${lpp}"><bean:message key="advancereply"/></a> 
	<span class="pipe">|</span></c:if>
	<c:if test="${allowpost||jsprun_uid==null}"><span id="newspecials" onmouseover="$('newspecials').id = 'newspecialtmp';this.id = 'newspecials';showMenu(this.id)"><a href="post.jsp?action=newthread&amp;fid=${thread.fid>0 ? thread.fid : fid}&page=${currentPage}" title="<bean:message key="post_new"/>"><bean:message key="post_new"/></a> </span></c:if>
</span>
<div>
<a href="javascript:;" title="<bean:message key="admin_highlight_bold"/>" class="tb_bold" onclick="seditor_insertunit('fastpost', '[b]', '[/b]')"><img src="images/common/bb_bold.gif"></a> 
	<a href="javascript:;" title="<bean:message key="post_jspruncode_forecolor"/>" class="tb_color" id="fastpostforecolor" onmouseover="showMenu(this.id, true, 0, 2)"><img src="images/common/bb_color.gif"></a>
	<span class="popupmenu_popup tb_color" id="fastpostforecolor_menu" style="display: none;"><table cellpadding="0" cellspacing="0" border="0" unselectable="on" style="width: auto;"><tr><c:forEach items="${coloroptions}" var="colorname" varStatus="index"><td class="editor_colornormal"><input type="button" style="background-color: ${colorname}" onclick="seditor_insertunit('fastpost', '[color=${colorname}]', '[/color]')"/></td>${index.count%8==0 ? "</tr><tr>" : ""}</c:forEach></tr></table></span>
	<a href="javascript:;" title="<bean:message key="post_jspruncode_image"/>" class="tb_img" onclick="seditor_insertunit('fastpost', '[img]', '[/img]')"><img src="images/common/bb_image.gif"></a>
	<a href="javascript:;" title="<bean:message key="post_jspruncode_url"/>" class="tb_link" onclick="seditor_insertunit('fastpost', '[url]', '[/url]')"><img src="images/common/bb_url.gif"></a>
	<a href="javascript:;" title="<bean:message key="post_jspruncode_quote"/>" class="tb_quote" onclick="seditor_insertunit('fastpost', '[quote]', '[/quote]')"><img src="images/common/bb_quote.gif"></a>
	<a href="javascript:;" title="<bean:message key="post_jspruncode_code"/>" class="tb_code" onclick="seditor_insertunit('fastpost', '[code]', '[/code]')"><img src="images/common/bb_code.gif"></a>
	<c:if test="${settings.smileyinsert>0}">
	<a href="javascript:;" class="tb_smilies" id="fastpostsmilies"  onmouseover="showMenu(this.id, true, 0, 2)"><img src="images/common/smile.gif"></a>
	<div><script type="text/javascript">smilies_show('fastpostsmiliesdiv', '${settings.smcols}', 0, 'fastpost');</script></div>
	</c:if>
	<a id="c3" class="tb_colora" onmouseover="c3_frame.location='images/common/getcolor.htm?c3';showMenu('c3')" title="<bean:message key="post_custom_color"/>"><img src="images/common/bb_color.gif"></a>
	<span id="c3_menu" style="display: none" class="tableborder"><iframe name="c3_frame" src="" frameborder="0" width="164" height="184" scrolling="no"></iframe></span>
</div>
</div>
<c:if test="${allowposttrade||allowpostpoll||allowpostreward||allowpostactivity||allowpostdebate||allowpostvideo||forumfield.threadtypes!=''||jsprun_uid==0}">
	<ul class="popupmenu_popup newspecialmenu" id="newspecials_menu" style="display: none">
		<c:if test="${thread.allowspecialonly<=0 || forum.allowspecialonly<=0}"><li><a href="post.jsp?action=newthread&amp;fid=${thread.fid>0 ? thread.fid : fid}&page=${currentPage}"><bean:message key="post_new"/></a></li></c:if>
		<c:if test="${allowpostpoll||jsprun_uid==0}"><li class="poll"><a href="post.jsp?action=newthread&amp;fid=${thread.fid>0 ? thread.fid : fid}&page=${currentPage}&amp;special=1"><bean:message key="post_newthreadpoll"/></a></li></c:if>
		<c:if test="${allowposttrade||jsprun_uid==0}"><li class="trade"><a href="post.jsp?action=newthread&amp;fid=${thread.fid>0 ? thread.fid : fid}&page=${currentPage}&amp;special=2"><bean:message key="post_newthreadtrade"/></a></li></c:if>
		<c:if test="${allowpostreward||jsprun_uid==0}"><li class="reward"><a href="post.jsp?action=newthread&amp;fid=${thread.fid>0 ? thread.fid : fid}&page=${currentPage}&amp;special=3"><bean:message key="post_newthreadreward"/></a></li></c:if>
		<c:if test="${allowpostactivity||jsprun_uid==0}"><li class="activity"><a href="post.jsp?action=newthread&amp;fid=${thread.fid>0 ? thread.fid : fid}&page=${currentPage}&amp;special=4"><bean:message key="post_newthreadactivity"/></a></li></c:if>
		<c:if test="${allowpostdebate||jsprun_uid==0}"><li class="debate"><a href="post.jsp?action=newthread&amp;fid=${thread.fid>0 ? thread.fid : fid}&page=${currentPage}&amp;special=5"><bean:message key="post_newthreaddebate"/></a></li></c:if>
		<c:if test="${allowpostvideo||jsprun_uid==0}"><li class="video"><a href="post.jsp?action=newthread&amp;fid=${thread.fid>0 ? thread.fid : fid}&page=${currentPage}&amp;special=6"><bean:message key="post_newthreadvideo"/></a></li></c:if>
		<c:if test="${threadtypes!=null&&thread.allowspecialonly<=0}">
			<c:forEach items="${threadtypes.types}" var="threadtype"><c:if test="${threadtypes.special[threadtype.key]==1&&threadtypes.show[threadtype.key]==1}"><li class="popupmenu_option"><a href="post.jsp?action=newthread&amp;fid=${thread.fid>0 ? thread.fid : fid}&page=${currentPage}&amp;typeid=${threadtype.key}">${threadtype.value}</a></li></c:if></c:forEach>
		</c:if>
	</ul>
</c:if>