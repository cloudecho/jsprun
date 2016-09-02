<%@ page language="java" pageEncoding="UTF-8"%>



<%
	rs = statement.executeQuery("SELECT num FROM "+tablePrefix+"membermagics WHERE uid="+userid+" AND magicid="+magicid);
	if(rs.next()){
		Integer num = rs.getInt("num");
		if(num>1){
			statement.executeUpdate("UPDATE "+tablePrefix+"membermagics SET num=num-1 WHERE magicid='"+magicid+"' AND uid='"+userid+"'");
		}else{
			statement.execute("DELETE FROM "+tablePrefix+"membermagics WHERE uid="+userid+" AND magicid="+magicid);
		}
	}else{
		request.setAttribute("messageFromOperationMagic",mr.getMessage(locale,"user_unexist_this_magic"));
		return ;
	}
%>


<%
	if(nowtime>2147483647){
		nowtime=2147483647;
	}
	int targettid = tid==null?0:tid;
	int targetpid = pid==null?0:pid;
	targetuid = targetuid != null ? targetuid : (magicid.equals("2")?userid:0);
	statement.execute("INSERT INTO "+tablePrefix+"magiclog (uid, magicid, action, dateline, amount, price, targettid, targetpid, targetuid) VALUES ('"+userid+"', '"+magicid+"', '2', '"+nowtime+"', '1', '0','"+targettid+"', '"+targetpid+"', '"+targetuid+"')");
%>
