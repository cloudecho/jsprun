<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="header.jsp" />
<div id="nav">
	<a href="${settings.indexname}">${settings.bbname}</a> &raquo; <bean:message key="magics_user" />
</div>
<div class="container">
	<div class="side">
		<jsp:include flush="true" page="magic_navbar.jsp" />
	</div>
	<div class="content">
		<c:if test="${valueObject.operation==null||valueObject.operation==''}">
			<div class="mainbox">
				<h1>
					<bean:message key="magics_user" />
				</h1>
				<ul class="tabs">
					<li class="${ valueObject.current=='all'?'current':''}">
						<a href="magic.jsp?action=user&amp;pid=0"><bean:message key="all" /></a>
					</li>
					<li class="${ valueObject.current=='1'?'current':''}">
						<a href="magic.jsp?action=user&amp;typeid=1&amp;pid=0"><bean:message key="magics_type_1" /></a>
					</li>
					<li class="${ valueObject.current=='2'?'current':''}">
						<a href="magic.jsp?action=user&amp;typeid=2&amp;pid=0"><bean:message key="magics_type_2" /></a>
					</li>
					<li class="${ valueObject.current=='3'?'current':''}">
						<a href="magic.jsp?action=user&amp;typeid=3&amp;pid=0"><bean:message key="magics_type_3" /></a>
					</li>
				</ul>
				<table summary="<bean:message key="magics_user" />" cellspacing="0" cellpadding="0">
					<c:forEach items="${valueObject.magicInfoList}" var="magicInfo"
						varStatus="count">
						<c:if test="${count.count%2==1}">
							<tr>
						</c:if>
						<td width="50%" class="attriblist">
							<dl>
								<dt>
									<img src="images/magics/${magicInfo.imageName }.gif"
										alt="${magicInfo.magicName }" />
								</dt>
								<dd class="name">
									${magicInfo.magicName }
								</dd>
								<dd>
									${magicInfo.magicExplaining }
								</dd>
								<dd>
									<bean:message key="num" />:
									<b>${magicInfo.magicCount }</b> <bean:message key="magics_user_totalnum" />:
									<b>${magicInfo.allMagicWeight }</b>
								</dd>
								<dd>
									<a href="magic.jsp?action=prepareOperation&amp;operation=use&amp;magicid=${magicInfo.magicId }&amp;pid=0&amp;username="><bean:message key="magics_operation_use" /></a>&nbsp;|&nbsp;
									<c:if test="${valueObject.selectSendASell}">
									<a href="magic.jsp?action=prepareOperation&amp;operation=give&amp;magicid=${magicInfo.magicId }"><bean:message key="magics_operation_present" /></a>&nbsp;|&nbsp;
									</c:if>
									<a href="magic.jsp?action=prepareOperation&amp;operation=drop&amp;magicid=${magicInfo.magicId }"><bean:message key="magics_operation_drop" /></a>&nbsp;|&nbsp;
									<c:if test="${valueObject.selectSendASell}">
									<a href="magic.jsp?action=prepareOperation&amp;operation=sell&amp;magicid=${magicInfo.magicId }"><bean:message key="magics_operation_sell" /></a>&nbsp;
									</c:if>
								</dd>
							</dl>
						</td>
						<c:if test="${count.count%2==0}">
							</tr>
						</c:if>
					</c:forEach>
					<c:if test="${!valueObject.haveMagic}">
					<tr>
						<td>
						&nbsp;&nbsp;&nbsp;<bean:message key="magics_shop_nonexistence1" />&nbsp;<a href="magic.jsp?action=shop"><u><bean:message key="here" /></u></a>&nbsp;<bean:message key="magics_shop_nonexistence2" />
						</td>
					</tr>
					</c:if>
				</table>
			</div>
			${multipage}
		</c:if>
		
		<c:if
			test="${valueObject.operation=='give'||valueObject.operation=='use'||valueObject.operation=='sell'||valueObject.operation=='drop'}">
			<form method="post" action="magic.jsp?action=operating">
				<input type="hidden" name="formHash" value="${jrun:formHash(pageContext.request)}" />
				<input type="hidden" name="magicid" value="${valueObject.magicId}" />
				<div class="mainbox">
					<h1>
						<c:choose>
							<c:when test="${valueObject.operation=='use'}">
								<bean:message key="magics_operation_use" />
								<input type="hidden" name="operation" value="use" />
							</c:when>
							<c:when test="${valueObject.operation=='give'}">
								<bean:message key="magics_operation_present" />
								<input type="hidden" name="operation" value="give" />
							</c:when>
							<c:when test="${valueObject.operation=='drop'}">
								<bean:message key="magics_operation_drop" />
								<input type="hidden" name="operation" value="drop" />
							</c:when>
							<c:when test="${valueObject.operation=='sell'}">
								<bean:message key="magics_operation_sell" />
								<input type="hidden" name="operation" value="sell" />
							</c:when>
						</c:choose>
					</h1>
					<table summary="" cellspacing="0" cellpadding="0">
						<tr>
							<td class="attriblist">
								<dl>
									<dt>
										<img src="images/magics/${valueObject.imageName }.gif"
											alt="${valueObject.magicName }" />
									</dt>
									<dd>
										${valueObject.magicName }
									</dd>
									<dd>
										${valueObject.magicExplaining }
									</dd>
									<dd>
										<bean:message key="num" />: ${valueObject.magicCount } <bean:message key="magics_user_totalnum" />:
										${valueObject.allMagicWeight }
									</dd>
									<dd>
										<bean:message key="magics_permission" />:
										<font color="red"> ${valueObject.usable } </font>
									</dd>
									<dd>
									<c:choose>
										<c:when test="${valueObject.magicType=='1'}">
										<bean:message key="magics_permission_forum" />: 
										<c:choose>
											<c:when test="${valueObject.moduleListSize==0 }">
										<bean:message key="magics_no_forum" />
											</c:when>
											<c:otherwise>
												<c:forEach items="${valueObject.moduleList }" var="model">
													<a href="${pageContext.request.contextPath }/forumdisplay.jsp?fid=${model.id }">${model.name}</a>&nbsp;&nbsp;
												</c:forEach>
											</c:otherwise>
										</c:choose>
										</c:when>
										<c:when test="${valueObject.magicType=='2'}">
										<bean:message key="magics_permission_group" />: 
										<c:choose>
											<c:when test="${valueObject.usergroupNameListSize==0 }">
										<bean:message key="magics_no_group" />
											</c:when>
											<c:otherwise>
												<c:forEach items="${valueObject.usergroupNameList }" var="usergroupName">
											${usergroupName}&nbsp;&nbsp;
											</c:forEach>
											</c:otherwise>
										</c:choose>
										</c:when>
									</c:choose>
									</dd>
								</dl>
							</td>
						</tr>
						<c:if test="${valueObject.operation!='use'}">
							<tr>
								<td width="10%">
									<bean:message key="num" />:
									<input name="magicnum" type="text" size="5" value="1" />
									&nbsp;&nbsp;
									<c:choose>
										<c:when test="${valueObject.operation=='sell'}">
										 <bean:message key="magics_price" />:
										<input name="price" type="text" size="5" />
										</c:when>
										<c:when test="${valueObject.operation=='give'}">
										<bean:message key="magics_target_present" />:
										<input name="tousername" type="text" size="5" />
										</c:when>
									</c:choose>
								</td>
							</tr>
						</c:if>
						<tr class="btns">
							<td colspan="2">
								<c:if test="${valueObject.operation=='use'}">
									<div class="mainbox">
										<h1>
											<bean:message key="magics_about" />
										</h1>
										<table summary="" cellspacing="0" cellpadding="0">
											<tr>
												<th>
													${valueObject.operationInfo1 }
												</th>
												<c:if test="${valueObject.displayText}">
												<th>
													<input type="text" size="30" name="${valueObject.textName }" value="${valueObject.textName=='targetUsername'?'':0 }" />
												</th>
												</c:if>
											</tr>
											<c:if test="${valueObject.showOperationInfo2 }">
												<tr>
													<th>
														${valueObject.operationInfo2 }
													</th>
													<th>
														<c:choose>
															<c:when test="${valueObject.isChangeColor}">
																<table border="0" cellspacing="0" cellpadding="0">
																	<tr>
																		<c:forEach items="${valueObject.colorList}"
																			var="color" varStatus="count">
																			<td>
																				<input type="radio" class="radio"
																					name="highlight_color" value="${count.count }"
																					${count.count==1?'checked':''} />
																			</td>
																			<td width="20" bgcolor="${color }">
																				&nbsp;
																			</td>
																		</c:forEach>
																	</tr>
																</table>
															</c:when>
															<c:otherwise>
																<select name="moveto">
																	${valueObject.selectContent}
																</select>
															</c:otherwise>
														</c:choose>
													</th>
												</tr>
											</c:if>
										</table>
									</div>
								</c:if>
								<c:choose>
									<c:when test="${valueObject.operation=='use'}">
										<button class="submit" type="submit" name="operatesubmit" id="operatesubmit" value="true">
											<bean:message key="magics_operation_use" />
										</button>
									</c:when>
									<c:when test="${valueObject.operation=='give'}">
										<button class="submit" type="submit" name="operatesubmit" id="operatesubmit" value="true" onclick="return confirm('<bean:message key="magics_confirm" />');">
											<bean:message key="magics_operation_present" />
										</button>
									</c:when>
									<c:when test="${valueObject.operation=='drop'}">
										<button class="submit" type="submit" name="operatesubmit" id="operatesubmit" value="true" onclick="return confirm('<bean:message key="magics_confirm" />');">
											<bean:message key="magics_operation_drop" />
										</button>
									</c:when>
									<c:when test="${valueObject.operation=='sell'}">
										<button class="submit" type="submit" name="operatesubmit" id="operatesubmit" value="true" onclick="return confirm('<bean:message key="magics_confirm" />');">
											<bean:message key="magics_operation_sell" />
										</button>
									</c:when>
								</c:choose>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</c:if>
	</div>
</div>
<jsp:directive.include file="footer.jsp" />