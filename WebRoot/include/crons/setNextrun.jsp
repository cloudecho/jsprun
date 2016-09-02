<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.jsprun.utils.Common"%>
<%@page import="cn.jsprun.utils.ForumInit"%>
<%
	Map<String,String> crons = (Map<String,String>)request.getAttribute("crons");
	String minute = crons.get("minute"); 
	if(minute.equals("")){
		crons.put("available","0");
		return ;
	}
	short hour = Short.parseShort(crons.get("hour"));
	short day = Short.parseShort(crons.get("day"));
	byte weekDay = Byte.parseByte(crons.get("weekday"));
	Calendar calendar = Common.getCalendar(ForumInit.settings.get("timeoffset"));
	calendar.set(Calendar.SECOND, 0);
	Date date = calendar.getTime();
	
	String[] minuteArray = minute.split("\t");
	int minuteLengh = minuteArray.length;
	int[] minuteIntArray = new int[minuteLengh];
	for(int i = 0;i<minuteLengh;i++){
		minuteIntArray[i] = Integer.parseInt(minuteArray[i]);
	}
	Arrays.sort(minuteIntArray);
	int nowMinute = calendar.get(Calendar.MINUTE);
	int minMinute = 0;
	int maxMinute = 0;
	int nextRunM = 0;
	if(minuteLengh>0){
		minMinute=minuteIntArray[0];
		maxMinute=minuteIntArray[minuteLengh-1];
	}
	if(nowMinute>=maxMinute){
		nextRunM = minMinute;
		if(hour==-1){
			calendar.add(Calendar.HOUR_OF_DAY, 1);
		}
	}else{
		for(int tempMinute : minuteIntArray){
			if(tempMinute>nowMinute){
				nextRunM = tempMinute;
				break;
			}
		}
	}
	calendar.set(Calendar.MINUTE, nextRunM);
	
	
	if(hour>-1){
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		
		if(!calendar.getTime().after(date)&&weekDay == -1&&day == -1){
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
	}
	
	
	if (weekDay > -1) {
		calendar.set(Calendar.DAY_OF_WEEK, weekDay+1);
		
		if(!calendar.getTime().after(date)){
			calendar.add(Calendar.WEEK_OF_MONTH, 1);
		}
	} else {
		if (day > -1) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
			
			if(!calendar.getTime().after(date)){
				calendar.add(Calendar.MONTH, 1);
			}
		}
	}
	
	crons.put("nextrun",(int)(calendar.getTimeInMillis()/1000)+"");
%>