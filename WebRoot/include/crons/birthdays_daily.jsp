<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:directive.page import="cn.jsprun.utils.Cache"/>
<jsp:directive.page import="cn.jsprun.utils.BeanFactory"/>
<jsp:directive.page import="cn.jsprun.utils.DataParse" />
<jsp:directive.page import="cn.jsprun.utils.Mail"/>
<jsp:directive.page import="cn.jsprun.utils.ForumInit" />
<%@page import="cn.jsprun.utils.Common"%>
<%@page import="cn.jsprun.dao.CronsDao"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.apache.struts.util.MessageResources"%>
<%@page import="java.util.Locale"%>
<%! 
	private String tablepre = "jrun_"; 
	private CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
	private DataParse dataParse = (DataParse) BeanFactory.getBean("dataParse");
	
%>
<%
	int timestamp=(Integer)request.getAttribute("timestamp");
	Connection connection = (Connection)request.getAttribute("connection");
	Map<String,String> settings=ForumInit.settings;
	int maxbdays=Integer.valueOf(settings.get("maxbdays"));
	if(maxbdays>0){
		Cache.updateCache("index");
	}
	int bdaystatus=Integer.valueOf(settings.get("bdaystatus"));
	if(bdaystatus>0){
		MessageResources mr = Common.getMessageResources(request);
		Locale locale = Common.getUserLocale(request);
		String boardurl = (String)session.getAttribute("boardurl");
		String timeoffset = settings.get("timeoffset");
		String today = Common.gmdate("MM-dd",timestamp,timeoffset);
		List<Map<String,String>> members = cronsDao.executeQuery(connection,"SELECT uid, username, email, bday FROM "+tablepre+"members WHERE RIGHT(bday, 5)='"+today+"' ORDER BY bday");
		if(members!=null&&members.size()>0){
			String bbname=settings.get("bbname");
			Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
			Mail mail=new Mail(mails);
			for(Map<String,String> member:members){
				String countent =mr.getMessage(locale,"birthday_message",bbname,boardurl);
				mail.sendMessage(mails.get("from"),member.get("username")+" <"+member.get("email")+">",mr.getMessage(locale,"birthday_subject"),countent,null);
			}
		}
	}

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
