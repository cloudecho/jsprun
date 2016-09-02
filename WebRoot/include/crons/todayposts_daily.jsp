<%@ page language="java" import="java.util.*,cn.jsprun.utils.BeanFactory" pageEncoding="UTF-8"%>

<%@page import="cn.jsprun.dao.CronsDao"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="cn.jsprun.utils.ForumInit"%>
<%! 
	private final String tablepre = "jrun_";
	private CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
	
	private String getValue(String sql,String field,Connection connection) throws SQLException{
		List<Map<String,String>> tempList = cronsDao.executeQuery(connection,sql);
		if(tempList!=null&&tempList.size()>0){
			return tempList.get(0).get(field);
		}
		return null;
	}
%>
<%	
	int timestamp=(Integer)request.getAttribute("timestamp");
	Connection connection = (Connection)request.getAttribute("connection");
	
	String yesterdayposts =getValue("SELECT sum(todayposts) yesterdayposts FROM "+tablepre+"forums","yesterdayposts",connection);
	if(yesterdayposts == null){
		yesterdayposts = "0";
	}
	
	String historyposts = getValue("SELECT value FROM "+tablepre+"settings WHERE variable='historyposts'","value",connection);
	String[] hpostarray = null;
	if(historyposts==null){
		hpostarray=new String[]{"0","0"};
	}else{
		hpostarray = historyposts.split("\t");
		if(hpostarray==null||hpostarray.length<2){
			hpostarray=new String[]{"0","0"};
		}
	}
	historyposts = Integer.valueOf(hpostarray[1])<Integer.valueOf(yesterdayposts)?yesterdayposts+"\t"+yesterdayposts:yesterdayposts+"\t"+hpostarray[1];
	cronsDao.execute(connection,"UPDATE "+tablepre+"settings SET value='"+historyposts+"' WHERE variable ='historyposts'");
	cronsDao.execute(connection,"UPDATE "+tablepre+"forums SET todayposts='0'");
	ForumInit.settings.put("historyposts",historyposts);
	
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

