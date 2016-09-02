<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="cn.jsprun.domain.Posts"/>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="cn.jsprun.domain.Members"%>
<%@ page import="org.apache.struts.util.MessageResources"%>
<%@ page import="cn.jsprun.utils.Common"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="cn.jsprun.utils.HibernateUtil" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Transaction" %>
<%!private final String tablePrefix = "jrun_";  %>
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
	

	rs = statement.executeQuery("SELECT tid,fid,author,authorid,first,dateline,anonymous FROM "+tablePrefix+"posts WHERE pid="+pid);
	MessageResources mr = Common.getMessageResources(request);
	Locale locale = Common.getUserLocale(request);
	if(rs.next()){
		tid = rs.getInt("tid");
		Short fid_p = rs.getShort("fid");
		String author_p = rs.getString("author");
		Integer authorid_p = rs.getInt("authorid");
		Byte first_p = rs.getByte("first");
		Integer dateline_p = rs.getInt("dateline");
		Byte anonymous_p = rs.getByte("anonymous");
		
		if(authorid_p.intValue() != userid){
			request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"magics_operation_nopermission"));
			return;
		}
		
		rs = statement.executeQuery("SELECT tid,subject,author,replies,lastposter FROM "+tablePrefix+"threads WHERE tid="+tid);
		String subject = "";
		String author = "";
		Integer replies = 0;
		String lastposter = "";
		if(rs.next()){
			subject = rs.getString("subject");
			author = rs.getString("author");
			replies = rs.getInt("replies");
			lastposter = rs.getString("lastposter");
			
			if(first_p==1){
				author = "";
				lastposter = replies > 0 ? lastposter : "";
			}else{
				lastposter = "";
			}
		}else{
			request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"can_not_getthreads_with_pid"));
			return;
		}
		statement.executeUpdate("UPDATE "+tablePrefix+"posts SET anonymous='1' WHERE pid="+pid);
		
		

		String[] lastpost_f = new String[4];
		rs = statement.executeQuery("SELECT lastpost FROM "+tablePrefix+"forums WHERE fid="+fid_p);
		if(rs.next()){
			String[] tempArray = rs.getString("lastpost").split("\t");
			System.arraycopy(tempArray,0,lastpost_f,0,tempArray.length);
		}
		if(dateline_p.toString().equals(lastpost_f[2])&&(author_p.equals(lastpost_f[3])||("".equals(lastpost_f[3])&&anonymous_p==1))){
			String lastpost = tid+"\t"+subject+"\t"+nowtime+"\t"+lastposter;
			statement.executeUpdate("UPDATE "+tablePrefix+"forums SET lastpost='"+lastpost+"' WHERE fid='"+fid_p+"'");
		}
		statement.executeUpdate("UPDATE "+tablePrefix+"threads SET lastposter='"+lastposter+"', moderated='1' WHERE tid='"+tid+"'");
		if(first_p==1){
%>
		<%@ include file="/include/magics/updatemagicthreadlog.jsp" %>
<%
		}

	}else{
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"can_not_getposts_with_pid"));
		return;
	}
	
	Posts posts = (Posts)hibernateSession.get(Posts.class,pid);
	
	posts.setAnonymous((byte)1);
	hibernateSession.update(posts);
	
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