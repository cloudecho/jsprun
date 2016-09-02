<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.jsprun.domain.Members"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
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
Integer userid = currentMember.getUid();

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
	expiration = nowtime + 86400;

	statement.executeUpdate("UPDATE "+tablePrefix+"threads SET displayorder='1', moderated='1' WHERE tid="+tid);

	MessageResources mr = Common.getMessageResources(request);
	Locale locale = Common.getUserLocale(request);
%>
<%@ include file="/include/magics/mLogAndmm.jsp" %>
<%@ include file="/include/magics/updatemagicthreadlog.jsp" %>
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
