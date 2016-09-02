<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@page import="cn.jsprun.utils.DataParse"%>
<%@page import="cn.jsprun.utils.BeanFactory"%>
<%@page import="cn.jsprun.utils.ForumInit"%>
<%@page import="cn.jsprun.utils.Common"%>
<%@page import="cn.jsprun.dao.CronsDao"%>
<%@page import="java.sql.Connection"%>
<%@page import="cn.jsprun.utils.Log"%>
<%@page import="org.apache.struts.util.MessageResources"%>
<%@page import="java.util.Locale"%>
<%@page import="cn.jsprun.utils.JspRunConfig"%>
<%! 
	private String tablepre = "jrun_"; 
	private CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
	private DataParse dataParse = (DataParse) BeanFactory.getBean("dataParse");
%>
<%
	int timestamp=(Integer)request.getAttribute("timestamp");
	Connection connection = (Connection)request.getAttribute("connection");
	Map<String,String> settings=ForumInit.settings;
	String honorset = settings.get("honorset");
	if(honorset!=null&&honorset.equals("1")){ 
		String honorvalue = settings.get("honorvalue");
		Map<String,Map<String,String>> honorvalueDP = (Map<String,Map<String,String>>)dataParse.characterParse(honorvalue, false);
		MessageResources mr = Common.getMessageResources(request);
		Locale locale = Common.getUserLocale(request);
		List<String> logStringList = new ArrayList<String>();
		String path = JspRunConfig.realPath + "forumdata/logs";
		StringBuffer uids = new StringBuffer();
		StringBuffer logBuffer = new StringBuffer();
		for(Map.Entry<String,Map<String,String>> tempMap : honorvalueDP.entrySet()){
			
			Map<String,String> honorvalueIMP = tempMap.getValue();
			String checked = honorvalueIMP.get("checked");
			if("1".equals(checked)){
				String medalId = tempMap.getKey();
				String qualification = honorvalueIMP.get("qualification");
				String reason = honorvalueIMP.get("reason");
				
				qualification = qualification.replace("extcredits", "m.extcredits").replace("oltime", "m.oltime").replace("pageviews", "m.pageviews").replaceAll("posts", "temp").replace("digesttemp", "m.digestposts").replace("temp", "m.posts");
				List<Map<String, String>> memberslist = cronsDao.executeQuery(connection,"select m.uid,m.username,mm.medals from "+tablepre+"members as m left join "+tablepre+"memberfields as mm on m.uid=mm.uid where "+qualification);
				boolean exist = false;
				uids.delete(0, uids.length());
				uids.append("0");
				if(memberslist!=null){
					for(Map<String,String> memberIfMap : memberslist){
						String mmMedals = memberIfMap.get("medals");
						exist = false;
						if(mmMedals!=null && !mmMedals.equals("")){
							String[] mmMedalsArray = mmMedals.split("\t");
							for(String mmMedal : mmMedalsArray){
								if(mmMedal.equals(medalId)){
									exist = true;
									break;
								}
							}
						}
						if(!exist){
							uids.append(",").append(memberIfMap.get("uid"));
							int lbl = logBuffer.length();
							if(lbl>1024000){
								Log.writelog("medalslog",timestamp,logBuffer.toString(),true);
								logBuffer.delete(0, lbl);
							}
							logBuffer.append("<?JSP exit;?>\t");
							logBuffer.append(timestamp);
							logBuffer.append("\t");
							logBuffer.append(mr.getMessage(locale,"script_execute"));
							logBuffer.append("\t");
							logBuffer.append(Common.get_onlineip(request));
							logBuffer.append("\t");
							logBuffer.append(memberIfMap.get("username"));
							logBuffer.append("\t");
							logBuffer.append(medalId);
							logBuffer.append("\tgrant\t");
							logBuffer.append(reason.replaceAll("\n", " "));
							logBuffer.append("\n");
						}
					}
				}
				String uidInfo = uids.toString();
				if(!uidInfo.equals("0")){
					cronsDao.execute(connection,"UPDATE "+tablepre+"memberfields SET medals=concat(medals,'"+medalId+"\t') where uid IN(" + uidInfo+")");
				}
			}
		}
		if(logBuffer.length()>0){
			Log.writelog("medalslog",timestamp,logBuffer.toString(),true);
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