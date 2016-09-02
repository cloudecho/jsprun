<%@ page language="java" import="cn.jsprun.utils.Common;" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose><c:when test="${fid!=null}"><c:set var="target" value="forum_${fid}" scope="page"/></c:when><c:otherwise><c:set var="target" value="0" scope="page"/></c:otherwise></c:choose>
<div style="display: none" id="ad_none"><div id="ad_headerbanner_none">${advlist.headerbanner[target]!=null?advitems[advlist.headerbanner[target]]: advitems[advlist.headerbanner['all']]}</div>
<c:if test="${advlist.text[target]!=null}"><div class="ad_text" id="ad_text_none"><table summary="Text Ad" cellpadding="0" cellspacing="1">${advlist.text[target]}</table></div></c:if>
<c:if test="${CURSCRIPT=='index.jsp'&&advlist.intercat!=null}"><c:forEach items="${catlists}" var="cat"><c:choose><c:when test="${advlist.intercat[(cat.fid)]!=null&&advitems[advlist.intercat['0']]!=null}"><c:choose><c:when test="<%=Common.rand(1)==1%>"><div class="ad_column" id="ad_intercat_${cat.fid}_none">${advitems[advlist.intercat[(cat.fid)]]}</div></c:when><c:otherwise><div class="ad_column" id="ad_intercat_${cat.fid}_none">${advitems[advlist.intercat['0']]}</div></c:otherwise></c:choose></c:when><c:when test="${advlist.intercat[(cat.fid)]!=null}"><div class="ad_column" id="ad_intercat_${cat.fid}_none">${advitems[advlist.intercat[(cat.fid)]]}</div></c:when><c:when test="${advitems[advlist.intercat['0']]!=null}"><div class="ad_column" id="ad_intercat_${cat.fid}_none">${advitems[advlist.intercat['0']]}</div></c:when></c:choose></c:forEach></c:if>
<c:if test="${CURSCRIPT=='viewthread.jsp'}">
	<c:if test="${!(special>0)}"><c:forEach items="${postlist}" var="post" varStatus="index"><div class="ad_textlink1" id="ad_thread1_${index.index}_none">${advthreads.thread1[index.index]}</div><div class="ad_textlink2" id="ad_thread2_${index.index}_none">${advthreads.thread2[index.index]}</div><div class="ad_pip" id="ad_thread3_${index.index}_none">${advthreads.thread3[index.index]}</div></c:forEach></c:if>
	<div class="ad_column" id="ad_interthread_none">${thread.replies>0?(advlist.interthread[target]!=null?advitems[advlist.interthread[target]]: advitems[advlist.interthread['all']]):""}</div>
</c:if>
<div class="ad_footerbanner" id="ad_footerbanner1_none">${advlist.footerbanner1[target]!=null?advitems[advlist.footerbanner1[target]]: advitems[advlist.footerbanner1['all']]}</div>
<div class="ad_footerbanner" id="ad_footerbanner2_none">${advlist.footerbanner2[target]!=null?advitems[advlist.footerbanner2[target]]: advitems[advlist.footerbanner2['all']]}</div>
<div class="ad_footerbanner" id="ad_footerbanner3_none">${advlist.footerbanner3[target]!=null?advitems[advlist.footerbanner3[target]]: advitems[advlist.footerbanner3['all']]}</div></div>
<script type="text/javascript">
var ad_divs = $('ad_none').getElementsByTagName('div');
var ad_obj = null;
for(var i = 0; i < ad_divs.length; i++) {
	if(ad_divs[i].id.substr(0, 3) == 'ad_' && (ad_obj = $(ad_divs[i].id.substr(0, ad_divs[i].id.length - 5))) && ad_divs[i].innerHTML) {
		ad_obj.innerHTML = ad_divs[i].innerHTML;
		ad_obj.className = ad_divs[i].className;
	}
}
$('ad_none').parentNode.removeChild($('ad_none'));
</script>
<c:if test="${advlist.float!=null||advlist.couplebanner!=null}">
<div align="left"  style="clear: both;">
	<script type="text/javascript" src="include/javascript/floatadv.js"></script>
	<script type="text/javascript">
	if(${advlist.float[target]!=null}){
		theFloaters.addItem('floatAdv1',24,'document.documentElement.clientHeight-${advitems[advlist.float[target]]['floath']}','<div style="position: absolute; left: 6px; top: 6px;">${advitems[advlist.float[target]]['code']} <br /><img src=\"images/common/advclose_${sessionScope['org.apache.struts.action.LOCALE']}.gif\" onMouseOver=\"this.style.cursor=\'pointer\'\" onClick=\"closeBanner();\"></div>');
	}
	else if(${advlist.float['all']!=null}){
		theFloaters.addItem('floatAdv1',24,'document.documentElement.clientHeight-${advitems[advlist.float['all']]['floath']}','<div style="position: absolute; left: 6px; top: 6px;">${advitems[advlist.float['all']]['code']} <br /><img src=\"images/common/advclose_${sessionScope['org.apache.struts.action.LOCALE']}.gif\" onMouseOver=\"this.style.cursor=\'pointer\'\" onClick=\"closeBanner();\"></div>');
	}
	if(${advlist.couplebanner[target]!=null}){
		theFloaters.addItem('coupleBannerL',24,0,'<div style="position: absolute; left: 6px; top: 6px;">${advitems[advlist.couplebanner[target]]} <br /><img src=\"images/common/advclose_${sessionScope['org.apache.struts.action.LOCALE']}.gif\" onMouseOver=\"this.style.cursor=\'pointer\'\" onClick=\"closeBanner();\"></div>');
		theFloaters.addItem('coupleBannerR','window.screen.width-24',0,'<div style="position: absolute; right: 6px; top: 6px;">${advitems[advlist.couplebanner[target]]} <br /><img src=\"images/common/advclose_${sessionScope['org.apache.struts.action.LOCALE']}.gif\" onMouseOver=\"this.style.cursor=\'pointer\'\" onClick=\"closeBanner();\"></div>');
	}
	else if(${advlist.couplebanner['all']!=null}){
		theFloaters.addItem('coupleBannerL',24,0,'<div style="position: absolute; left: 6px; top: 6px;">${advitems[advlist.couplebanner['all']]} <br /><img src=\"images/common/advclose_${sessionScope['org.apache.struts.action.LOCALE']}.gif\" onMouseOver=\"this.style.cursor=\'pointer\'\" onClick=\"closeBanner();\"></div>');
		theFloaters.addItem('coupleBannerR','window.screen.width-24',0,'<div style="position: absolute; right: 6px; top: 6px;">${advitems[advlist.couplebanner['all']]} <br /><img src=\"images/common/advclose_${sessionScope['org.apache.struts.action.LOCALE']}.gif\" onMouseOver=\"this.style.cursor=\'pointer\'\" onClick=\"closeBanner();\"></div>');
	}
	theFloaters.play();
	</script>
</div>
</c:if>