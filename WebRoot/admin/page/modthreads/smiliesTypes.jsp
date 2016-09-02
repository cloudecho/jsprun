<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="../cp_header.jsp" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="guide">
	<tr><td><a href="#" onclick="parent.menu.location='admincp.jsp?action=menu'; parent.main.location='admincp.jsp?action=home';return false;"><bean:message key="header_system"/></a>&nbsp;&raquo;&nbsp;<bean:message key="menu_post_smilies"/></td></tr>
</table>
<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
		<tr class="header">
			<td>
				<div style="float:left; margin-left:0px; padding-top:8px">
					<a href="###" onclick="collapse_change('tip')"><bean:message key="tips"/></a>
				</div>
				<div style="float:right; margin-right:4px; padding-bottom:9px">
					<a href="###" onclick="collapse_change('tip')"><img id="menuimg_tip" src="images/admincp/menu_reduce.gif" border="0" /> </a>
				</div>
			</td>
		</tr>
		<tbody id="menu_tip" style="display:">
			<tr>
				<td>
					<bean:message key="a_post_smilies_tips"/>
				</td>
			</tr>
		</tbody>
	</table>
	<br />
	<form method="post" action="admincp.jsp?action=smilies&update=yes">
		<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td colspan="6" align="left">
					<bean:message key="menu_post_smilies"/> - ${name}
				</td>
			</tr>
			<tr align="center" class="category">
				<td width="50">
					<input type="checkbox" name="chkall" onclick="checkall(this.form,'delete')" class="checkbox"> <bean:message key="del"/>
				</td>
				<td>
					<bean:message key="a_post_smilies_id"/>
				</td>
				<td>
					<bean:message key="display_order"/>
				</td>
				<td>
					<bean:message key="a_post_smilies_edit_code"/>
				</td>
				<td>
					<bean:message key="filename"/>
				</td>
				<td>
					<bean:message key="a_post_smilies_edit_image"/>
				</td>
			</tr>
				
		<c:if test="${totalsize > 10}">
			<div class="pages">
				<em>&nbsp;${totalsize}&nbsp;</em>
				
				<c:if test="${totalpage>10 && currentPage>=4}">
					<a href="admincp.jsp?action=smilies&search=yes&edit=${edit}&directory=${directory}&name=<jrun:encoding value="${name}"/>&amp;page=1" class="first">1 ...</a>
				</c:if>
				
				<c:if test="${currentPage != 1}">
					<a href="admincp.jsp?action=smilies&search=yes&edit=${edit}&directory=${directory}&name=<jrun:encoding value="${name}"/>&amp;page=${currentPage-1}" class="prev">&lsaquo;&lsaquo;</a>
				</c:if>
				<c:choose>
					<c:when test="${totalpage>10 && currentPage>=4 && totalpage-(currentPage-2)>=10}">
						
						<c:forEach var="num" begin="${currentPage-2}" end="${(currentPage-2)+9}" step="1">
							<c:choose>
								<c:when test="${currentPage == num}">
									<strong>${currentPage}</strong>
								</c:when>
								<c:otherwise>
									<a href="admincp.jsp?action=smilies&search=yes&edit=${edit}&directory=${directory}&name=<jrun:encoding value="${name}"/>/>&amp;page=${num}">${num}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${totalpage>10 && currentPage>=4}">
								
								<c:forEach var="num" begin="${totalpage-9}" end="${totalpage}" step="1">
									<c:choose>
										<c:when test="${currentPage == num}">
											<strong>${currentPage}</strong>
										</c:when>
										<c:otherwise>
											<a href="admincp.jsp?action=smilies&search=yes&edit=${edit}&directory=${directory}&name=<jrun:encoding value="${name}"/>&amp;page=${num}">${num}</a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${totalpage>10}">
									
										<c:forEach var="num" begin="1" end="10" step="1">
											<c:choose>
												<c:when test="${currentPage == num}">
													<strong>${currentPage}</strong>
												</c:when>
												<c:otherwise>
													<a href="admincp.jsp?action=smilies&search=yes&edit=${edit}&directory=${directory}&name=<jrun:encoding value="${name}"/>&amp;page=${num}">${num}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
									
										<c:forEach var="num" begin="1" end="${totalpage}" step="1">
											<c:choose>
												<c:when test="${currentPage == num}">
													<strong>${currentPage}</strong>
												</c:when>
												<c:otherwise>
													<a href="admincp.jsp?action=smilies&search=yes&edit=${edit}&directory=${directory}&name=<jrun:encoding value="${name}"/>&amp;page=${num}">${num}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				
				<c:if test="${currentPage != totalpage}">
					<a href="admincp.jsp?action=smilies&search=yes&edit=${edit}&directory=${directory}&name=<jrun:encoding value="${name}"/>&amp;page=${currentPage+1}" class="next">&rsaquo;&rsaquo;</a>
				</c:if>
				
				<c:if test="${totalpage>10 && (totalpage-currentPage)>7}">
					<a href="admincp.jsp?action=smilies&search=yes&edit=${edit}&directory=${directory}&name=<jrun:encoding value="${name}"/>&amp;page=${totalpage}" class="last">... ${totalpage}</a>
				</c:if>
				
				<c:if test="${totalpage>10}">
					<kbd>
						<input type="text" name="custompage" size="3" onkeydown="if(event.keyCode==13) {window.location='admincp.jsp?action=smilies&search=yes&edit=${edit}&directory=${directory}&name=<jrun:encoding value="${name}"/>&amp;page='+this.value; return false;}" />
					</kbd>
				</c:if>
		</c:if>

			<c:forEach var="s" items="${smilies}" varStatus="v">
				<tr align="center">
					<td class="altbg1">
						<input class="checkbox" type="checkbox" name="delete[]" value="${s.id}">
					</td>
					<td class="altbg2">
						${s.id}
					</td>
					<td class="altbg1">
						<input type="text" size="2" name="displayorder[${s.id}]" value="${s.displayorder }" maxlength="2">
					</td>
					<td class="altbg2">
						<input type="text" size="25" name="code[${s.id}]" value="${s.code}" id="code_${v.count}" smileyid="${s.id}" maxlength="30">
					</td>
					<td class="altbg1">
						<input type="hidden" value="${s.url }" id="url_${v.count}" > ${s.url}
					</td>
					<td class="altbg2">
						<img src="./images/smilies/${directory}/${s.url}" border="0" onload="if(this.height>30) {this.resized=true; this.height=30; this.title='<bean:message key="image_newwindow"/>';}" onmouseover="if(this.resized) this.style.cursor='pointer';" onclick="if(!this.resized) {return false;} else {window.open(this.src);}" />
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="6">
					<bean:message key="a_post_smilies_edit_add_code"/>
					<input type="text" size="2" value="<bean:message key="a_post_smilies_prefix"/>" id="prefix" onclick="clearinput(this, '<bean:message key="a_post_smilies_prefix"/>')" style="vertical-align: middle"> +
					<select id="middle" style="vertical-align: middle">
						<option value="1">
							<bean:message key="filename"/>
						</option>
						<option value="2">
							<bean:message key="a_post_milies_edit_order_radom"/>
						</option>
						<option value="3">
							<bean:message key="a_post_smilies_id"/>
						</option>
					</select>
					+ <input type="text" size="2" value="<bean:message key="a_post_smilies_suffix"/>" id="suffix" onclick="clearinput(this, '<bean:message key="a_post_smilies_suffix"/>')" style="vertical-align: middle">
					<button type="button" onclick="addsmileycodes('11', '');" style="vertical-align: middle">
						<bean:message key="apply"/>
					</button>
				</td>
			</tr>

		</table>
		<center>
		<input type="hidden" name="edit" value="${typeid}">
			<input type="hidden" name="directory" value="${directory}">
			<input type="hidden" name="name" value="${name}">
			<input type="hidden" name="hiddenids" value="${ids}">
			<input class="button" type="submit" name="editsubmit" value="<bean:message key="submit"/>">
			&nbsp;
			<input class="button" type="button" value="<bean:message key="return"/>" onclick="window.location='admincp.jsp?action=smilies'">
		</center>
	</form>
	<br />
	<script type="text/javascript">var IMGDIR = '${styles.IMGDIR}';var attackevasive = '0';</script>
	<div id="addsmilies">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
			<tr class="header">
				<td colspan="2" align="left">
					<bean:message key="a_post_smilies_add"/>
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<b><bean:message key="a_post_smilies_type"/>:</b>
				</td>
				<td class="altbg2">
					<c:out value="${name}"></c:out>
				</td>
			</tr>
			<tr>
				<td class="altbg1">
					<b><bean:message key="ubiety_directory"/>:</b>
					<br>
					<span class="smalltxt"><bean:message key="a_post_smilies_add_search"/></span>
				</td>
				<td class="altbg2"> ./images/smilies/${directory}
				</td>
			</tr>
		</table>
		<center>
			<input class="button" type="button" value="<bean:message key="search"/>" onclick="ajaxget('smilies.do?smiliesaction=ajaxSmilies&path=${directory}&typeid=${typeid}', 'addsmilies', 'addsmilies','auto');doane(event);">
		</center>
	</div>

<script type="text/javascript">
	function addsmileycodes(smiliesnum, pre) {
		smiliesnum = parseInt(smiliesnum);
		if(smiliesnum > 1) {
			for(var i = 1; i < smiliesnum; i++) {
				var prefix = trim($(pre + 'prefix').value); //前缀的值
				var suffix = trim($(pre + 'suffix').value); //后缀的值
				var page = parseInt(${currentPage});
				
				var middle = $(pre + 'middle').value == 1 ? $(pre + 'url_' + i).value.substr(0,$(pre + 'url_' + i).value.lastIndexOf('.')) : ($(pre + 'middle').value == 2 ? i + page * 10 : $(pre + 'code_' + i).attributes['smileyid'].nodeValue);
				
				if(!prefix || prefix == '<bean:message key="a_post_smilies_prefix"/>') {
					alert('<bean:message key="a_post_smilies_prefix_tips"/>');
					return;
				}
				suffix = !suffix || suffix == '<bean:message key="a_post_smilies_suffix"/>' ? '' : suffix;
				$(pre + 'code_' + i).value = prefix + middle + suffix;
			}
		}
	}
	//点击时，清空文本框
	function clearinput(obj, defaultval) {
		if(obj.value == defaultval) {
			obj.value = '';
		}
	}
</script>
<jsp:directive.include file="../cp_footer.jsp" />