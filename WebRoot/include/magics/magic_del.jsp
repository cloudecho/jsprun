<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.io.File"%>
<%@ page import="cn.jsprun.domain.Members"%>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="cn.jsprun.utils.HibernateUtil" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Transaction" %>
<%@ page import="org.apache.struts.util.MessageResources"%>
<%@ page import="cn.jsprun.utils.Common"%>
<%@page import="cn.jsprun.utils.JspRunConfig"%>
<%! private final String tablePrefix = "jrun_"; %>
<%
boolean noException = false;
Integer targetuid = null;
Integer expiration = 0; 
boolean extra = false;
Members currentMember = (Members)session.getAttribute("user");
String magicid = request.getParameter("magicid");
Integer userid = (Integer)session.getAttribute("jsprun_uid");

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

	
	rs = statement.executeQuery("SELECT tid,fid,authorid, first FROM "+tablePrefix+"posts WHERE pid="+pid);
	MessageResources mr = Common.getMessageResources(request);
	Locale locale = Common.getUserLocale(request);
	if(rs.next()){
		Integer tid_p = rs.getInt("tid");
		Integer fid_p = rs.getInt("fid");
		Integer authorid = rs.getInt("authorid");
		byte first = rs.getByte("first");
		if(authorid.intValue()!=userid){
			request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"magics_operation_nopermission"));
			return;
		}
		String realPath = JspRunConfig.realPath;
		if(first==1){
			rs = statement.executeQuery("SELECT replies FROM "+tablePrefix+"threads WHERE tid="+tid_p);
			int replies = 0;
			if(rs.next()){
				replies = rs.getInt("replies");
			}
			rs = statement.executeQuery("SELECT attachment,thumb FROM "+tablePrefix+"attachments WHERE tid="+tid_p);
			while(rs.next()){
				String attachment = rs.getString("attachment");
				byte thumb = rs.getByte("thumb");
				File file = new File(realPath+"/"+attachment);
				if(file.isFile()){
					file.delete();
				}
				if(thumb==1){
					String imagePostfix = attachment.substring(attachment.lastIndexOf("."),attachment.length());
					file = new File(realPath+"/"+attachment+".thumb"+imagePostfix);
					if(file.isFile()){
						file.delete();
					}
				}
			}
			String[] tableNames = {"threads", "threadsmod", "relatedthreads", "posts", "polls", "polloptions", "trades", "activities", "activityapplies", "attachments", "favorites", "mythreads", "myposts", "subscriptions", "debates", "debateposts", "typeoptionvars", "forumrecommend"};
			for(String tableName : tableNames){
				statement.execute("DELETE FROM "+tablePrefix+tableName+" WHERE tid='"+tid_p+"'");
			}
			
			rs = statement.executeQuery("SELECT tid,dateline,author FROM "+tablePrefix+"posts WHERE fid="+fid_p+" AND invisible=0 ORDER BY dateline DESC LIMIT 1");
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
			sqlBuffer.append("WHERE f.fid="+fid_p);
			statement.executeUpdate(sqlBuffer.toString());
			
		}else {
			rs = statement.executeQuery("SELECT attachment,thumb FROM "+tablePrefix+"attachments WHERE pid="+pid);
			while(rs.next()){
				String attachment = rs.getString("attachment");
				byte thumb = rs.getByte("thumb");
				File file = new File(realPath+"/"+attachment);
				if(file.isFile()){
					file.delete();
				}
				if(thumb==1){
					String imagePostfix = attachment.substring(attachment.lastIndexOf("."),attachment.length());
					file = new File(realPath+"/"+attachment+".thumb"+imagePostfix);
					if(file.isFile()){
						file.delete();
					}
				}
			}
			statement.execute("DELETE FROM "+tablePrefix+"posts WHERE pid="+pid);
			statement.execute("DELETE FROM "+tablePrefix+"myposts WHERE pid="+pid);
			statement.execute("DELETE FROM "+tablePrefix+"attachments WHERE pid="+pid);
			
			
			rs = statement.executeQuery("SELECT tid,dateline,author FROM "+tablePrefix+"posts WHERE fid="+fid_p+" AND invisible=0 ORDER BY dateline DESC LIMIT 1");
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tablePrefix+"forums AS f SET f.posts=f.posts-1, ");
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
			sqlBuffer.append("WHERE f.fid="+fid_p);
			statement.executeUpdate(sqlBuffer.toString());
			
		}
	}else{
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"magics_faild"));
		return;
	}

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