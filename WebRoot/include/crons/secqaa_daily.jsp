<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:directive.page import="cn.jsprun.utils.BeanFactory"/>
<jsp:directive.page import="cn.jsprun.utils.DataParse"/>
<jsp:directive.page import="cn.jsprun.utils.Cache"/>
<%@page import="cn.jsprun.dao.CronsDao"%>
<%@page import="java.sql.Connection"%>
<%!
	private String tablepre = "jrun_"; 
	private CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
	
%>
<%
	int timestamp=(Integer)request.getAttribute("timestamp");
	Connection connection = (Connection)request.getAttribute("connection");
	List<Map<String,String>> tempList = cronsDao.executeQuery(connection,"SELECT value FROM "+tablepre+"settings WHERE variable='secqaa'");
	if(tempList!=null&&tempList.size()!=0){
		Map<String,String> tempMap = tempList.get(0);
		if(tempMap!=null){
			String secqaa = tempMap.get("value");
			Map dataParseMap = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(secqaa,false);
			if(dataParseMap!=null){
				int status = (Integer)dataParseMap.get("status");
				if(status>0){
					Cache.updateCache("secqaa");
				}
			}
		}
	}

	RequestDispatcher dispatcher = request.getRequestDispatcher("/include/crons/setNextrun.jsp");
	try {
		dispatcher.include(request, response);
	} catch (Exception e) {
		e.printStackTrace();
	} 
	Map<String,String> crons = (Map<String,String>)request.getAttribute("crons");
	if("0".equals(crons.get("available"))){
		cronsDao.execute(connection,"UPDATE "+tablepre+"crons SET available='0' WHERE cronid="+crons.get("cronid"));
	}else{
		cronsDao.execute(connection,"UPDATE "+tablepre+"crons SET lastrun='"+timestamp+"',nextrun='"+crons.get("nextrun")+"' WHERE cronid="+crons.get("cronid"));
	}
%>
