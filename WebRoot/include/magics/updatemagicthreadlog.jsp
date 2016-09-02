<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	rs = statement.executeQuery("SELECT identifier FROM "+tablePrefix+"magics WHERE magicid="+magicid);
	String identifier = "";
	if(rs.next()){
		identifier = rs.getString("identifier");
	}
	String username  = !extra ? currentMember.getUsername() : "";
	statement.execute("REPLACE INTO "+tablePrefix+"threadsmod (tid,uid,username,dateline,expiration,action,status,magicid) VALUES ('"+tid+"','"+userid+"','"+username+"','"+nowtime+"','"+expiration+"','"+identifier+"','1','"+magicid+"' )");
%>
