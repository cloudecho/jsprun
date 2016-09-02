<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="cn.jsprun.utils.DataParse"/>
<%@ page import="cn.jsprun.utils.BeanFactory"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="cn.jsprun.domain.Members" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="cn.jsprun.utils.HibernateUtil" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Transaction" %>
<%@ page import="org.apache.struts.util.MessageResources"%>
<%@ page import="cn.jsprun.utils.Common"%>
<%! private final String tablePrefix = "jrun_"; %>
<%
boolean noException = false;
Integer targetuid = null;
Integer expiration = 0; 
boolean extra = false;
Members currentMember = (Members)session.getAttribute("user");
String magicid = request.getParameter("magicid");
Integer userid = (Integer)session.getAttribute("jsprun_uid");
targetuid = userid;

Integer pid = (Integer)request.getAttribute("targetPid");

Integer tid = (Integer)request.getAttribute("targetTid");

String targetUsername = (String)request.getAttribute("targetUsername");
SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
Session hibernateSession = sessionFactory.getCurrentSession();
Transaction transaction = hibernateSession.beginTransaction();	
Connection connection = null;
Statement statement = null;
ResultSet rs = null;
try{
	connection = hibernateSession.connection();
	connection.setAutoCommit(false);
	statement = connection.createStatement();
	
	Integer nowtime = (Integer)request.getAttribute("timestamp");
	
	
	rs = statement.executeQuery("SELECT price FROM "+tablePrefix+"magics WHERE magicid="+magicid);
	MessageResources mr = Common.getMessageResources(request);
	Locale locale = Common.getUserLocale(request);
	if(!rs.next()){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"magics_nonexistence"));
		return;
	}
	int magicsPrice = rs.getInt("price");
	int getmoney=Common.rand(1,(int)(magicsPrice*1.5));
	
	
	Map<String,String> settingMap = (Map<String,String>)application.getAttribute("settings");
	String extcreditsNum = settingMap.get("creditstrans");
	String creditsformula = settingMap.get("creditsformula");
	if(extcreditsNum==null||extcreditsNum.equals("")){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"undefined_action"));
		return;
	}
	
	StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tablePrefix+"members SET extcredits"+extcreditsNum+"=extcredits"+extcreditsNum+"+"+getmoney);
	if(creditsformula.indexOf("extcredits"+extcreditsNum)>=0){
		sqlBuffer.append(",credits="+creditsformula);
	}
	sqlBuffer.append(" WHERE uid="+userid);
	statement.executeUpdate(sqlBuffer.toString());
	
	
	String tempString = settingMap.get("extcredits");;
	Map map_level2 = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(tempString,false);
	Map map_level3 = ((Map)map_level2.get(Integer.valueOf(extcreditsNum)));
	String extcreditString = (String)map_level3.get("title");
	request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"magics_MOK_message",getmoney,extcreditString));
%>
<%@ include file="/include/magics/mLogAndmm.jsp" %>
<%
	connection.commit();
	noException = true;
}catch(Exception exception){
	connection.rollback();
	exception.printStackTrace();
	throw exception;
}finally{
	try{
		if(!noException&&connection!=null){
			connection.rollback();
		}
		if(rs!=null){
			rs.close();
			rs = null;
		}
		if(statement!=null){
			statement.close();
			statement = null;
		}
		if(connection != null){
			connection.close();
			connection = null;
		}
	}catch(Exception exception2){
		exception2.printStackTrace();
	}
}
%>