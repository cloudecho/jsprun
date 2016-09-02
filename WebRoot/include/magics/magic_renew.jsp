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
boolean extra = true;
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
	
	rs = statement.executeQuery("SELECT tid,fid,authorid,first,anonymous FROM "+tablePrefix+"posts WHERE pid="+pid);
	MessageResources mr = Common.getMessageResources(request);
	Locale locale = Common.getUserLocale(request);
	if(!rs.next()){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"posts_unexist"));
		return ;
	}
	tid = rs.getInt("tid");
	Integer posts_fid = rs.getInt("fid");
	Integer posts_authorid = rs.getInt("authorid");
	Byte posts_first = rs.getByte("first");
	Byte posts_anonymous = rs.getByte("anonymous");
	
	String posts_authorname = "";
	rs = statement.executeQuery("SELECT username FROM "+tablePrefix+"members WHERE uid="+posts_authorid);
	if(rs.next()){
		posts_authorname = rs.getString("username");
	}
	rs = statement.executeQuery("SELECT subject,author,replies,lastposter FROM "+tablePrefix+"threads WHERE tid="+tid);
	if(!rs.next()){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"posts_unexist"));
		return ;
	}
	String posts_thread_subject = rs.getString("subject");
	String posts_thread_author = rs.getString("author");
	Integer posts_thread_reply = rs.getInt("replies");
	String posts_thread_lastposter = rs.getString("lastposter");
	
	String lastposter = "";
	if(posts_first!=0){
		lastposter = posts_thread_reply>0?posts_thread_lastposter : posts_authorname;
	}else{
		lastposter = posts_authorname;
		posts_authorname = posts_thread_author;
	}
	statement.executeUpdate("UPDATE "+tablePrefix+"posts SET anonymous='0' WHERE pid="+pid);
	if(posts_first!=0){
%>
<%@ include file="/include/magics/updatemagicthreadlog.jsp" %>
<%
	}
	rs = statement.executeQuery("SELECT lastpost FROM "+tablePrefix+"forums WHERE fid="+posts_fid);
	if(!rs.next()){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"posts_unexist"));
		return ;
	}
	String[] lastpostArray = rs.getString("lastpost").split("\t");
	if(lastpostArray.length<4||(posts_thread_subject.equals(lastpostArray[1])&&"".equals(lastpostArray[3])&&posts_anonymous!=0)){
		statement.executeUpdate("UPDATE "+tablePrefix+"forums SET lastpost='"+tid+"\t"+posts_thread_subject+"\t"+nowtime+"\t"+lastposter+"' WHERE fid="+posts_fid);
	}
	statement.executeUpdate("UPDATE "+tablePrefix+"threads SET author='"+posts_authorname+"',lastposter='"+lastposter+"',moderated='1' WHERE tid="+tid);
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