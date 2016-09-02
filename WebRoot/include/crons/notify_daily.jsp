<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:directive.page import="cn.jsprun.utils.BeanFactory" />
<jsp:directive.page import="cn.jsprun.utils.Mail" />
<jsp:directive.page import="cn.jsprun.utils.DataParse" />
<jsp:directive.page import="cn.jsprun.utils.ForumInit" />
<%@page import="java.text.SimpleDateFormat"%>
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
	String timeoffset = (String)settings.get("timeoffset");
	SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd : HH:mm",timeoffset);
	String bbname =settings.get("bbname");
	String boardurl = (String)session.getAttribute("boardurl");
	List<Map<String, String>> subscriptions = cronsDao.executeQuery(connection,"SELECT t.tid, t.subject, t.author, t.lastpost, t.lastposter, t.views, t.replies, m.username, m.email FROM "+ tablepre+ "subscriptions s, "+ tablepre+ "members m, "+ tablepre+ "threads t WHERE s.lastpost>s.lastnotify AND m.uid=s.uid AND t.tid=s.tid");
	if (subscriptions != null && subscriptions.size() > 0) {
		MessageResources mr = Common.getMessageResources(request);
		Locale locale = Common.getUserLocale(request);
		Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
		Mail mail=new Mail(mails);
		for(Map<String, String> subscription:subscriptions){
			String context = mr.getMessage(locale,"email_notify_message",new String[]{bbname,boardurl,subscription.get("tid"),subscription.get("subject"),subscription.get("author"),subscription.get("views"),subscription.get("replies"),subscription.get("lastposter"),Common.gmdate(dateFormat,Integer.parseInt(subscription.get("lastpost")))});
			mail.sendMessage(mails.get("from"),subscription.get("username")+" <"+subscription.get("email")+">",mr.getMessage(locale,"email_notify_subject",subscription.get("subject")),context,null);
		}
	}
	cronsDao.execute(connection,"UPDATE "+tablepre+"subscriptions SET lastnotify='"+timestamp+"' WHERE lastpost>lastnotify");
			
	
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

