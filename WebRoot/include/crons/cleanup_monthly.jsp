<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:directive.page import="cn.jsprun.utils.BeanFactory"/>
<%@page import="cn.jsprun.dao.CronsDao"%>
<%@page import="java.sql.Connection"%>
<%! 
	private String tablepre = "jrun_"; 
	private CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
%>
<%
	int timestamp=(Integer)request.getAttribute("timestamp");
	Connection connection = (Connection)request.getAttribute("connection");
	String myrecorddaysValue = null;
	List<Map<String,String>> tempList = cronsDao.executeQuery(connection,"SELECT value FROM "+tablepre+"settings WHERE variable='myrecorddays'");
	if(tempList!=null&&tempList.size()!=0){
		Map<String,String> tempMap = tempList.get(0);
		if(tempMap!=null){
			myrecorddaysValue = tempMap.get("value");
		}
	}
	int myrecorddays = timestamp - Integer.valueOf(myrecorddaysValue) * 86400;
	
	cronsDao.execute(connection,"DELETE FROM "+tablepre+"mythreads WHERE dateline<"+myrecorddays);
	
	cronsDao.execute(connection,"DELETE FROM "+tablepre+"myposts WHERE dateline<"+myrecorddays);
	
	cronsDao.execute(connection,"DELETE FROM "+tablepre+"invites WHERE dateline<"+(timestamp - 2592000)+" AND status='4'");
	
	cronsDao.execute(connection,"TRUNCATE "+tablepre+"relatedthreads");

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

