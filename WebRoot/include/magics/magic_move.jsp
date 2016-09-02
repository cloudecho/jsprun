<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="cn.jsprun.utils.Common"%>
<%@ page import="cn.jsprun.domain.Members"%>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="cn.jsprun.utils.HibernateUtil" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Transaction" %>
<%@ page import="org.apache.struts.util.MessageResources"%>
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
String moveto = request.getParameter("moveto");
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
	MessageResources mr = Common.getMessageResources(request);
	Locale locale = Common.getUserLocale(request);
	
	if(tid==null||moveto==null){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"magics_faild"));
		return;
	}
	


	rs = statement.executeQuery("SELECT fid,authorid,special,replies FROM "+tablePrefix+"threads WHERE tid="+tid);
	if(!rs.next()){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"threads_unexist"));
		return;
	}
	Short oldFid = rs.getShort("fid");
	Integer authorid = rs.getInt("authorid");
	Byte special = rs.getByte("special");
	Integer replies = rs.getInt("replies");
	if(authorid != userid.intValue()){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"magics_operation_nopermission"));
		return;
	}
	if(special!=0){
		rs = statement.executeQuery("SELECT allowpostspecial FROM "+tablePrefix+"forums WHERE fid='"+moveto+"'");
		Integer allowpostspecial = 0;
		if(rs.next()){
			allowpostspecial = rs.getInt("allowpostspecial");
		}
		if((special&allowpostspecial)<=0){
			request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"admin_move_nopermission"));
			return ;
		}
	}

	rs = statement.executeQuery("SELECT postperm FROM "+tablePrefix+"forumfields WHERE fid='"+moveto+"'");
	String postperm = "";
	if(rs.next()){
		postperm = rs.getString("postperm");
	}
	
	Map<String,String> usergroupMap = (Map<String,String>)request.getAttribute("usergroups");
	
	String allowpost = usergroupMap.get("allowpost");
	if((postperm==null||postperm.equals(""))&&(allowpost==null||allowpost.equals("")||allowpost.equals("0"))){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"group_nopermission",usergroupMap.get("grouptitle")));
		return;
	}else if(postperm!=null&&!postperm.equals("")&&!Common.forumperm(postperm,currentMember.getGroupid(),currentMember.getExtgroupids())){
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"post_forum_newthread_nopermission"));
		return;
	}
	
	statement.executeUpdate("UPDATE "+tablePrefix+"threads SET fid='"+moveto+"',moderated='1' WHERE tid="+tid);
	statement.executeUpdate("UPDATE "+tablePrefix+"posts SET fid='"+moveto+"' WHERE tid='"+tid+"'");


	
	rs = statement.executeQuery("SELECT tid,dateline,author FROM "+tablePrefix+"posts WHERE fid="+oldFid+" AND invisible=0 ORDER BY dateline DESC LIMIT 1");
	StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tablePrefix+"forums AS f SET f.threads=f.threads-1, f.posts=f.posts-"+(replies+1)+", ");
	if(rs.next()){
		Integer lastTid = rs.getInt("tid");
		Integer lastDateline = rs.getInt("dateline");
		String lastAuthor = rs.getString("author");
		String lastThreadSubject = "";
		rs = statement.executeQuery("SELECT subject FROM "+tablePrefix+"threads WHERE tid="+lastTid);
		if(rs.next()){
			lastThreadSubject = rs.getString("subject");
		}
		sqlBuffer.append("f.lastpost='"+lastTid+"\t"+lastThreadSubject+"\t"+lastDateline+"\t"+lastAuthor+"' ");
	}else{
		sqlBuffer.append("f.lastpost='' ");
	}
	sqlBuffer.append("WHERE f.fid="+oldFid);
	statement.executeUpdate(sqlBuffer.toString());
	
	
	
	rs = statement.executeQuery("SELECT tid,dateline,author FROM "+tablePrefix+"posts WHERE fid="+moveto+" AND invisible=0 ORDER BY dateline DESC LIMIT 1");
	StringBuffer sqlBuffer_moveto = new StringBuffer("UPDATE "+tablePrefix+"forums AS f SET f.threads=f.threads+1, f.posts=f.posts+"+(replies+1)+", ");
	if(rs.next()){
		Integer lastTid = rs.getInt("tid");
		Integer lastDateline = rs.getInt("dateline");
		String lastAuthor = rs.getString("author");
		String lastThreadSubject = "";
		rs = statement.executeQuery("SELECT subject FROM "+tablePrefix+"threads WHERE tid="+lastTid);
		if(rs.next()){
			lastThreadSubject = rs.getString("subject");
		}
		sqlBuffer_moveto.append("f.lastpost='"+lastTid+"\t"+lastThreadSubject+"\t"+lastDateline+"\t"+lastAuthor+"' ");
	}else{
		sqlBuffer_moveto.append("f.lastpost='' ");
	}
	sqlBuffer_moveto.append("WHERE f.fid="+moveto);
	statement.executeUpdate(sqlBuffer_moveto.toString());
	

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