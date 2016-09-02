<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:directive.page import="cn.jsprun.utils.BeanFactory"/>
<jsp:directive.page import="cn.jsprun.dao.PromotionsDao"/>
<jsp:directive.page import="cn.jsprun.domain.Promotions"/>
<jsp:directive.page import="cn.jsprun.dao.MembersDao"/>
<jsp:directive.page import="cn.jsprun.domain.Members"/>
<jsp:directive.page import="cn.jsprun.utils.DataParse"/>
<jsp:directive.page import="java.lang.reflect.Method"/>
<%@page import="cn.jsprun.dao.CronsDao"%>
<%@page import="java.sql.Connection"%>
<%!
	private String tablepre = "jrun_"; 
	private CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
	private PromotionsDao promotionsDao = (PromotionsDao)BeanFactory.getBean("promotionsDao");
	private MembersDao membersDao = (MembersDao)BeanFactory.getBean("memberDao");
	private void updateCredits(Map<Integer,Integer> uid_countMap,Map promotion_visitMap){
		List<Integer> membersIdList = new ArrayList<Integer>();
		Iterator<Integer> iterator = uid_countMap.keySet().iterator();
		while(iterator.hasNext()){
			Integer key = iterator.next();
			membersIdList.add(key);
		}
		List<Members> membersList = membersDao.getMemberListWithMemberIdList(membersIdList);
		for(int i = 0;i<membersList.size();i++){
			Members members = membersList.get(i);
			Iterator<Integer> iterator2 = promotion_visitMap.keySet().iterator();
			while(iterator2.hasNext()){
				Integer kye = iterator2.next();
				try{
					Method method_get = Members.class.getMethod("getExtcredits"+kye);
					Method method_set = Members.class.getMethod("setExtcredits"+kye,Integer.class);
					method_set.invoke(members,((Integer)method_get.invoke(members)+uid_countMap.get(members.getUid())*(Integer)promotion_visitMap.get(kye)));
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}
		}
		membersDao.updateMembers(membersList);
	}
%>
<%
	int timestamp=(Integer)request.getAttribute("timestamp");
	Connection connection = (Connection)request.getAttribute("connection");
	
	Map promotion_visitMap = null;
	List<Map<String,String>> tempList = cronsDao.executeQuery(connection,"SELECT value FROM "+tablepre+"settings WHERE variable='creditspolicy'");
	if(tempList!=null&&tempList.size()!=0){
		Map<String,String> tempMap = tempList.get(0);
		if(tempMap!=null){
			String value = tempMap.get("value");
			Map stringToMap = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(value,false);
			promotion_visitMap = (Map)stringToMap.get("promotion_visit");
		}
	}
	if(promotion_visitMap!=null){
		List<Promotions> promotionsList = promotionsDao.getAllPromotions();
		List<Integer> uidList = new ArrayList<Integer>();
		List<String> usernameList = new ArrayList<String>();
		if(promotionsList!=null){
			for(int i = 0;i<promotionsList.size();i++){
				Promotions promotions = promotionsList.get(i);
				if(promotions.getUid()!=null&&promotions.getUid()>0){
					uidList.add(promotions.getUid());
				}else{
					usernameList.add(promotions.getUsername());
				}
			}
		}
		if(uidList.size()>0||usernameList.size()>0){
			if(usernameList.size()>0){
				List<Members> membersList = membersDao.getMembersByNames(usernameList);
				for(int i = 0;i<membersList.size();i++){
					uidList.add(membersList.get(i).getUid());
				}
			}
			Map<Integer,Integer> uid_countMap = new HashMap<Integer,Integer>();
			for(int i = 0;i<uidList.size();i++){
				Integer uid = uidList.get(i);
				if(uid_countMap.get(uid)==null){
					uid_countMap.put(uid,1);
				}else{
					uid_countMap.put(uid,uid_countMap.get(uid)+1);
				}
			}
			updateCredits(uid_countMap,promotion_visitMap);
			cronsDao.execute(connection,"DELETE FROM "+tablepre+"promotions");
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