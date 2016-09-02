<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:directive.page import="cn.jsprun.utils.BeanFactory"/>
<jsp:directive.page import="cn.jsprun.dao.OtherSetDao"/>
<jsp:directive.page import="cn.jsprun.domain.Magics"/>
<%@page import="cn.jsprun.dao.CronsDao"%>
<%@page import="java.sql.Connection"%>
<%@page import="cn.jsprun.utils.Common"%>
<%@page import="cn.jsprun.utils.ForumInit"%>
<%!
	private String tablepre = "jrun_"; 
	private CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
	private OtherSetDao osDao = (OtherSetDao) BeanFactory.getBean("otherSetDao");
	
%>
<%
	int timestamp=(Integer)request.getAttribute("timestamp");
	Connection connection = (Connection)request.getAttribute("connection");
	List<Magics> magicOperationList = new ArrayList<Magics>();
	List<Magics> magicsList = osDao.getAvailableMagics();
	if(magicsList!=null){
		for(int i = 0;i<magicsList.size();i++){
			Magics magics = magicsList.get(i);
			if(magics!=null&&magics.getSupplytype()!=0&&magics.getSupplynum()!=0&&magics.getNum()==0){
				magicOperationList.add(magics);
			}
		}
	}
	Calendar calendar = Common.getCalendar(ForumInit.settings.get("timeoffset"));
	Integer dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	Integer dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	for(int i = 0;i<magicOperationList.size();i++){
		Magics magics = magicOperationList.get(i);
		boolean autosupply = false;
		if(magics.getSupplytype() == 1) {
				autosupply = true;
		} else if(magics.getSupplytype() == 2 && dayOfWeek==1){
				autosupply = true;
		} else if(magics.getSupplytype() == 3 && dayOfMonth==1){
				autosupply = true;
		}
		if(!autosupply) {
			magics.setNum(magics.getNum()+magics.getSupplynum());
		}
	}
	osDao.updateMagics(magicOperationList);

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