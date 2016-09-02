package cn.jsprun.foreg.service;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.apache.struts.util.MessageResources;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.dao.StatsDao;
import cn.jsprun.dao.StatvarsDao;
import cn.jsprun.domain.Stats;
import cn.jsprun.domain.StatsId;
import cn.jsprun.domain.Statvars;
import cn.jsprun.domain.StatvarsId;
import cn.jsprun.foreg.vo.statistic.CompositorInfo;
import cn.jsprun.foreg.vo.statistic.PageInfo;
import cn.jsprun.foreg.vo.statistic.PageInfoWithImage;
import cn.jsprun.foreg.vo.statistic.Stats_CompositorVO;
import cn.jsprun.foreg.vo.statistic.Stats_agentVO;
import cn.jsprun.foreg.vo.statistic.Stats_creditComposidorVO;
import cn.jsprun.foreg.vo.statistic.Stats_mainVO;
import cn.jsprun.foreg.vo.statistic.Stats_forumsrankVO;
import cn.jsprun.foreg.vo.statistic.Stats_manageStatisticVO;
import cn.jsprun.foreg.vo.statistic.Stats_manageTeamVO;
import cn.jsprun.foreg.vo.statistic.Stats_navbarVO;
import cn.jsprun.foreg.vo.statistic.Stats_onlineCompositorVO;
import cn.jsprun.foreg.vo.statistic.Stats_postsLogVO;
import cn.jsprun.foreg.vo.statistic.Stats_tradeCompositorVO;
import cn.jsprun.foreg.vo.statistic.Stats_viewsVO;
import cn.jsprun.foreg.vo.statistic.SubPostlog;
import cn.jsprun.foreg.vo.statistic.Stats_creditComposidorVO.LineObject;
import cn.jsprun.foreg.vo.statistic.Stats_creditComposidorVO.LineObject.CreditInfo;
import cn.jsprun.foreg.vo.statistic.Stats_manageStatisticVO.ContentInfo;
import cn.jsprun.foreg.vo.statistic.Stats_manageStatisticVO.TimeInfo;
import cn.jsprun.foreg.vo.statistic.Stats_manageTeamVO.ForumTableGroup;
import cn.jsprun.foreg.vo.statistic.Stats_manageTeamVO.ForumTableGroup.Forum;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.ForumInit;
public class StatvarsService {
	private final static String tableprefix = "jrun_";
	private static DecimalFormat df = new DecimalFormat("0.00");
	public boolean allowAccess(Map<String,String> groupMap){
		String allowviewstatString = groupMap.get("allowviewstats");
		if(allowviewstatString!=null){
			Byte allowviewstats = Byte.valueOf(allowviewstatString);
			if(allowviewstats==1){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	public boolean checkUserid(String uid){
		List<Map<String,String>> tempML = ((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeQuery("SELECT username FROM "+tableprefix+"members WHERE uid=? AND adminid>'0'",uid);
		return tempML != null && tempML.size()>0;
	}
	public Stats_mainVO getFinalStats_mainVO(int timestamp,String timeoffsetSession,MessageResources mr,Locale locale){
		Map<String, String> settingsMap = ForumInit.settings;
		String tempS = settingsMap.get("statstatus");
		boolean statstatus = tempS!=null && !"".equals(tempS) && !"0".equals(tempS);
		tempS = settingsMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !"".equals(tempS) && !"0".equals(tempS);
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		Map<String,String> statvars = getFinalMap("main",dataBaseDao);
		tempS = statvars.get("lastupdate");
		int lastupdate = tempS == null ? 0 : Integer.parseInt(tempS);
		Double statscachelife = Double.valueOf(settingsMap.get("statscachelife"));
		statscachelife = statscachelife * 60;
		List<String> newstatvars = new ArrayList<String>();
		if(timestamp - lastupdate > statscachelife){
			statvars.clear();
			statvars.put("lastupdate", timestamp+"");
			newstatvars.add("'main', 'lastupdate', '"+timestamp+"'");
		}
		Stats_mainVO stats_mainVO = new Stats_mainVO();
		tempS = statvars.get("forums");
		if(tempS != null){
			stats_mainVO.setFormsCount(tempS);
		}else{
			tempS = getForumCount(dataBaseDao);
			stats_mainVO.setFormsCount(tempS);
			newstatvars.add("'main', 'forums', '"+tempS+"'");
		}
		tempS = statvars.get("threads");
		if(tempS != null){
			stats_mainVO.setThreadNum(tempS);
		}else{
			tempS = getThreadCount(dataBaseDao);
			stats_mainVO.setThreadNum(tempS);
			newstatvars.add("'main', 'threads', '"+tempS+"'");
		}
		tempS = statvars.get("runtime");
		String tempS2 = statvars.get("posts");
		if(tempS != null && tempS2 != null){
			stats_mainVO.setRuntime(tempS);
			stats_mainVO.setPostsNum(tempS2);
		}else{
			Map<String,String> tm = getRunTimeAdPost(dataBaseDao);
			tempS = tm.get("rt");
			tempS2 = tm.get("ct");
			stats_mainVO.setRuntime(tempS);
			stats_mainVO.setPostsNum(tempS2);
			newstatvars.add("'main', 'runtime', '"+tempS+"'");
			newstatvars.add("'main', 'posts', '"+tempS2+"'");
		}
		tempS = statvars.get("members");
		if(tempS!=null){
			stats_mainVO.setMembersNum(tempS);
		}else{
			tempS = getMemberCount(dataBaseDao);
			stats_mainVO.setMembersNum(tempS);
			newstatvars.add("'main', 'members', '"+tempS+"'");
		}
		tempS = statvars.get("postsaddtoday");
		if(tempS != null){
			stats_mainVO.setAddPostsInLast24(tempS);
		}else{
			tempS = getAddPostsInLast24(dataBaseDao,timestamp);
			stats_mainVO.setAddPostsInLast24(tempS);
			newstatvars.add("'main', 'postsaddtoday', '"+tempS+"'");
		}
		tempS = statvars.get("membersaddtoday");
		if(tempS != null){
			stats_mainVO.setAddMemberInLast24(tempS);
		}else{
			tempS = getAddMemberInLast24(dataBaseDao,timestamp);
			stats_mainVO.setAddMemberInLast24(tempS);
			newstatvars.add("'main', 'membersaddtoday', '"+tempS+"'");
		}
		tempS = statvars.get("admins");
		if(tempS != null){
			stats_mainVO.setMemberOfManageNum(tempS);
		}else{
			tempS = getAdminNum(dataBaseDao);
			stats_mainVO.setMemberOfManageNum(tempS);
			newstatvars.add("'main', 'admins', '"+tempS+"'");
		}
		tempS = statvars.get("memnonpost");
		if(tempS != null){
			stats_mainVO.setMembersOfNoPostsNum(tempS);
		}else{
			tempS = getNonPost(dataBaseDao);
			stats_mainVO.setMembersOfNoPostsNum(tempS);
			newstatvars.add("'main', 'memnonpost', '"+tempS+"'");
		}
		tempS = statvars.get("hotforum");
		if(tempS != null){
			Map<String,String> beanModuleInfo = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(tempS,false);
			stats_mainVO.setBestModule(beanModuleInfo.get("name"));
			stats_mainVO.setBestModuleID(beanModuleInfo.get("fid"));
			stats_mainVO.setBestModuleThreadNum(beanModuleInfo.get("threads"));
			stats_mainVO.setBestModulePostsNum(beanModuleInfo.get("posts"));
		}else{
			Map<String,String> tm = getHotforumInfo(dataBaseDao);
			stats_mainVO.setBestModule(tm.get("name"));
			stats_mainVO.setBestModuleID(tm.get("fid"));
			stats_mainVO.setBestModuleThreadNum(tm.get("threads"));
			stats_mainVO.setBestModulePostsNum(tm.get("posts"));
			newstatvars.add("'main', 'hotforum', '"+((DataParse)BeanFactory.getBean("dataParse")).combinationChar(tm)+"'");
		}
		tempS = statvars.get("bestmem");
		tempS2 = statvars.get("bestmemposts");
		if(tempS != null && tempS2 != null){
			stats_mainVO.setBestMem(tempS);
			stats_mainVO.setBestMemPosts(tempS2);
		}else{
			Map<String,String> tm = getBestMenInfo(dataBaseDao,timestamp);
			tempS = tm.get("author");
			tempS2 = tm.get("posts");
			stats_mainVO.setBestMem(tempS);
			stats_mainVO.setBestMemPosts(tempS2);
			newstatvars.add("'main', 'bestmem', '"+tempS+"'");
			newstatvars.add("'main', 'bestmemposts', '"+tempS2+"'");
		}
		stats_mainVO.setNewMemberName(settingsMap.get("lastmember")); 
		stats_mainVO.setShowFluxSurvey(statstatus); 
		String timeoffsetSetting = settingsMap.get("timeoffset");
		SimpleDateFormat formatToDate = Common.getSimpleDateFormat("yyyyMM", timeoffsetSetting);
		SimpleDateFormat formatToString = Common.getSimpleDateFormat("yyyy "+mr.getMessage(locale, "year_stats")+" MM "+mr.getMessage(locale, "month_stats"),timeoffsetSetting);
		StatsDao statsDao=((StatsDao)BeanFactory.getBean("statsDao"));
		if(statstatus){
			Map<String,Stats> mapTemp = statsDao.workForFluxStatistic();
			Stats statsMonth = mapTemp.get("month");
			String accessMaxMonth = null;
			String accessMaxMonthNum = null;
			if(statsMonth!=null){
				accessMaxMonth = statsMonth.getId().getVariable();
				Date date = null;
				try {
					date = formatToDate.parse(accessMaxMonth);
					accessMaxMonth = formatToString.format(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				date = null;
				accessMaxMonthNum = statsMonth.getCount().toString();
			}
			stats_mainVO.setAllPageFlux(mapTemp.get("hits").getCount().toString());
			stats_mainVO.setMemberOfAccess(mapTemp.get("members").getCount().toString());
			stats_mainVO.setVisitorOfAccess(mapTemp.get("guests").getCount().toString());
			stats_mainVO.setAccessMaxNum(accessMaxMonth);
			stats_mainVO.setAllPageFluxOfMonth(accessMaxMonthNum);
			String maxHour = mapTemp.get("hour").getId().getVariable();
			stats_mainVO.setAccessTime(maxHour+" - "+(Integer.parseInt(maxHour)+1));
			stats_mainVO.setAccessTimeAllFlux(mapTemp.get("hour").getCount().toString());
			List<Stats> statsList = statsDao.getStatsByType("month");
			statsDao=null;
			formatToString.applyPattern("yyyy - MM ");
			String monthTemp = null;
			PageInfo pageInfo = null;
			float tempf = 0F;
			float hits = (float)(mapTemp.get("hits").getCount());
			List<PageInfo> pageInfoList = stats_mainVO.getMonthFlux();
			if(statsList != null){
				float maxPercent = 0F;
				try {
					for(Stats tempStats : statsList){
						pageInfo = new PageInfo();
						tempf = (tempStats.getCount()/hits)*100;
						maxPercent = tempf>maxPercent?tempf:maxPercent;
						monthTemp = formatToString.format(formatToDate.parse(tempStats.getId().getVariable()));
						pageInfo.setNum(tempStats.getCount().toString());
						pageInfo.setNumPercent(number_format(mapTemp.get("hits").getCount(), tempStats.getCount()));
						pageInfo.setInformation(monthTemp);
						pageInfoList.add(pageInfo);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				for(PageInfo pageInfo2 : pageInfoList){
					setPageInfoMaxLengh(maxPercent, pageInfo2);
				}
			}
		}else{
			StatsId statsId = new StatsId();
			statsId.setType("total");
			statsId.setVariable("hits");
			Stats stats = statsDao.getStatsById(statsId);
			statsDao=null;
			statsId= null;
			stats_mainVO.setAllPageFlux(stats.getCount().toString());
			setSubPostlog(stats_mainVO.getSubPostLog(), newstatvars,dataBaseDao,timestamp);
		}
		updateNewStatvars(newstatvars, dataBaseDao);
		String lastupdateString = statvars.get("lastupdate");
		statvars = null;
		int lastUpdate = lastupdateString!=null?Integer.parseInt(lastupdateString):0;
		int nextUpdate = lastUpdate + statscachelife.intValue();
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffsetSession);
		stats_mainVO.setLastTime(Common.gmdate(dateFormat, lastUpdate));
		stats_mainVO.setNextTime(Common.gmdate(dateFormat, nextUpdate));
		setNavbarVO(stats_mainVO.getNavbar(), statstatus, modworkstatus, "stats");
		return stats_mainVO;
	}
	public Stats_forumsrankVO getForumCompositor(int timestamp,String timeoffsetSession){
		Map<String, String> settingMap = ForumInit.settings;
		Double statscachelife = Double.valueOf(settingMap.get("statscachelife"));
		statscachelife = statscachelife * 60;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("0")&& !tempS.equals("");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		List<String> newstatvars = new ArrayList<String>();
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		Map<String,String> statvars = getFinalMap("forumsrank",dataBaseDao);
		tempS = statvars.get("lastupdate");
		int lastupdate = tempS == null ? 0 : Integer.parseInt(tempS);
		if(timestamp - lastupdate > statscachelife){
			statvars.clear();
			statvars.put("lastupdate", timestamp+"");
			newstatvars.add("'forumsrank', 'lastupdate', '"+timestamp+"'");
		}
		DataParse dataParse = ((DataParse)BeanFactory.getBean("dataParse"));
		Map<Integer,Map<String,String>> threads = null;
		Map<Integer,Map<String,String>> posts = null;
		Map<Integer,Map<String,String>> thismonth = null;
		Map<Integer,Map<String,String>> today = null;
		List<Map<String,String>> tempML = null;
		int tempI = 0;
		tempS = statvars.get("threads");
		if(tempS!=null){
			threads = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT fid, name, threads FROM "+tableprefix+"forums WHERE status=1 AND type<>'group' ORDER BY threads DESC LIMIT 0, 20");
			if(tempML!=null){
				threads = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					threads.put(tempI, tempM);
					tempI++;
				}
			}
			statvars.put("forums", tempI+"");
			newstatvars.add("'forumsrank', 'threads', '"+dataParse.combinationChar(threads)+"'");
			newstatvars.add("'forumsrank', 'forums', '"+tempI+"'");
		}
		tempS = statvars.get("forums");
		if(tempS == null || tempS.equals("") || tempS.equals("0")){
			statvars.put("forums", "20");
		}
		tempS = statvars.get("posts");
		if(tempS!=null){
			posts = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT fid, name, posts FROM "+tableprefix+"forums WHERE status=1 AND type<>'group' ORDER BY posts DESC LIMIT 0, "+statvars.get("forums"));
			if(tempML!=null){
				tempI = 0;
				posts = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					posts.put(tempI, tempM);
					tempI++;
				}
			}
			newstatvars.add("'forumsrank', 'posts', '"+dataParse.combinationChar(posts)+"'");
		}
		tempS = statvars.get("thismonth");
		if(tempS != null){
			thismonth = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT DISTINCT(p.fid) AS fid, f.name, COUNT(pid) AS posts FROM "+tableprefix+"posts p " +
					"LEFT JOIN "+tableprefix+"forums f USING (fid) " +
					"WHERE dateline>='"+(timestamp-86400*30)+"' AND invisible='0' AND f.status='1' AND authorid>'0' " +
					"GROUP BY p.fid ORDER BY posts DESC LIMIT 0, "+statvars.get("forums"));
			if(tempML!=null){
				tempI = 0;
				thismonth = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					thismonth.put(tempI, tempM);
					tempI++;
				}
			}
			newstatvars.add("'forumsrank', 'thismonth', '"+dataParse.combinationChar(thismonth)+"'");
		}
		tempS = statvars.get("today");
		if(tempS != null){
			today = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT DISTINCT(p.fid) AS fid, f.name, COUNT(pid) AS posts FROM "+tableprefix+"posts p " +
					"LEFT JOIN "+tableprefix+"forums f USING (fid) " +
					"WHERE dateline>='"+(timestamp-86400)+"' AND invisible='0' AND f.status='1' AND authorid>'0' " +
					"GROUP BY p.fid ORDER BY posts DESC LIMIT 0, "+statvars.get("forums"));
			if(tempML!=null){
				tempI = 0;
				today = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					today.put(tempI, tempM);
					tempI++;
				}
			}
			newstatvars.add("'forumsrank', 'today', '"+dataParse.combinationChar(today)+"'");
		}
		Stats_forumsrankVO stats_forumsrankVO = new Stats_forumsrankVO();
		stats_forumsrankVO.setForumCompositorMapList(threads , posts, thismonth, today);
		tempI = Integer.parseInt(statvars.get("lastupdate"));
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm",timeoffsetSession);
		stats_forumsrankVO.setLastTime(Common.gmdate(dateFormat, tempI));
		stats_forumsrankVO.setNextTime(Common.gmdate(dateFormat, (tempI+statscachelife.intValue())));
		setNavbarVO(stats_forumsrankVO.getNavbar(), statstatus, modworkstatus, "forumsrank");
		updateNewStatvars(newstatvars, dataBaseDao);
		return stats_forumsrankVO;
	}
	public Stats_viewsVO getFinalStats_viewsVO(){
		Map<String, String> settingMap = ForumInit.settings;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		DataBaseDao dataBaseDao=((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		List<Map<String,String>> totalResultML = dataBaseDao.executeQuery("SELECT st.count FROM "+tableprefix+"stats AS st WHERE st.type='total' AND st.variable='hits'");
		float total_count = 0;
		if(totalResultML.size()>0){
			total_count = Float.parseFloat(totalResultML.get(0).get("count"));
		}
		List<Map<String,String>> tempML = dataBaseDao.executeQuery("SELECT * FROM "+tableprefix+"stats AS st WHERE st.type='week' OR st.type='hour'");
		dataBaseDao = null;
		List<Map<String,String>> weekResultML = new ArrayList<Map<String,String>>();
		List<Map<String,String>> hourResultML = new ArrayList<Map<String,String>>();
		if(tempML!=null){
			for(Map<String,String> tempM : tempML){
				if(tempM!=null){
					if("hour".equals(tempM.get("type"))){
						hourResultML.add(tempM);
					}else{
						weekResultML.add(tempM);
					}
				}
			}
		}
		tempML = null;
		Stats_viewsVO stats_viewsVO = new Stats_viewsVO();
		List<PageInfo> pageInfo_week = stats_viewsVO.getPageInfoWeekList();
		String[] weekTime = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		String[] hourTime = {"0 AM","1 AM","2 AM","3 AM","4 AM","5 AM","6 AM",
							 "7 AM","8 AM","9 AM","10 AM","11 AM","12 AM","1 PM",
							 "2 PM","3 PM","4 PM","5 PM","6 PM","7 PM","8 PM",
							 "9 PM","10 PM","11 PM"};
		Map<String,String> mapTemp2 = null;
		float maxPercent_week = 0F;
		int foreachSize = weekResultML.size();
		PageInfo pageInfo = null;
		for(int i = 0;i<foreachSize;i++){
			if(i==0){
				float tempD = 0F;
				for(Map<String,String> mapTemp : weekResultML){
					tempD = Float.parseFloat(mapTemp.get("count"));
					maxPercent_week = tempD>maxPercent_week?tempD:maxPercent_week;
				}
				maxPercent_week = Float.valueOf(number_format(total_count, maxPercent_week));
			}
			mapTemp2 = weekResultML.get(i);
			pageInfo = new PageInfo();
			pageInfo.setNum(mapTemp2.get("count"));
			pageInfo.setNumPercent(number_format(total_count, Float.parseFloat(mapTemp2.get("count"))));
			pageInfo.setInformation(weekTime[Integer.parseInt(mapTemp2.get("variable"))]);
			setPageInfoMaxLengh(maxPercent_week, pageInfo);
			pageInfo_week.add(pageInfo);
		}
		pageInfo_week = null;
		weekResultML = null;
		List<PageInfo> pageInfo_hour = stats_viewsVO.getPageInfoHourList();
		float maxPercent_hour = 0F;
		foreachSize = hourResultML.size();
		for(int i = 0;i<foreachSize;i++){
			if(i==0){
				float tempD = 0F;
				for(Map<String,String> mapTemp : hourResultML){
					tempD = Float.parseFloat(mapTemp.get("count"));
					maxPercent_hour = tempD>maxPercent_hour?tempD:maxPercent_hour;
				}
				maxPercent_hour = Float.valueOf(number_format(total_count, maxPercent_hour));
			}
			mapTemp2 = hourResultML.get(i);
			pageInfo = new PageInfo();
			pageInfo.setNum(mapTemp2.get("count"));
			pageInfo.setNumPercent(number_format(total_count, Float.parseFloat(mapTemp2.get("count"))));
			pageInfo.setInformation(hourTime[Integer.parseInt(mapTemp2.get("variable"))]);
			setPageInfoMaxLengh(maxPercent_hour, pageInfo);
			pageInfo_hour.add(pageInfo);
		}
		mapTemp2 = null;
		pageInfo_hour = null;
		hourResultML = null;
		pageInfo = null;
		setNavbarVO(stats_viewsVO.getNavbar(), statstatus, modworkstatus, "views");
		return stats_viewsVO;
	}
	public Stats_agentVO getFinalStats_agentVO(){
		Map<String, String> settingMap = ForumInit.settings;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		DataBaseDao dataBaseDao=((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		List<Map<String,String>> tempML = dataBaseDao.executeQuery("SELECT * FROM "+tableprefix+"stats AS st WHERE st.type='os' OR st.type='browser'");
		dataBaseDao = null;
		List<Map<String,String>> osResultML = new ArrayList<Map<String,String>>();
		List<Map<String,String>> browserResultML = new ArrayList<Map<String,String>>();
		if(tempML!=null){
			for(Map<String,String> tempM : tempML){
				if(tempM!=null){
					if("os".equals(tempM.get("type"))){
						osResultML.add(tempM);
					}else{
						browserResultML.add(tempM);
					}
				}
			}
		}
		tempML = null;
		Stats_agentVO stats_agentVO = new Stats_agentVO();
		List<PageInfoWithImage> operatingSystemList = stats_agentVO.getOperatingSystemList();
		PageInfoWithImage pageInfoWithImage = null;
		Map<String,String> tempMap = null;
		float numPercent_system = 0F;
		float maxPercent = 0F;
		float tempD = 0F;
		for(int i = 0;i<osResultML.size();i++){
			if(i==0){
				for(Map<String,String> tempM : osResultML){
					tempD = Float.parseFloat(tempM.get("count"));
					numPercent_system += tempD;
					maxPercent = tempD>maxPercent?tempD:maxPercent;
				}
				maxPercent = Float.parseFloat(number_format(numPercent_system, maxPercent));
			}
			pageInfoWithImage = new PageInfoWithImage();
			tempMap = osResultML.get(i);
			pageInfoWithImage.setInformation(tempMap.get("variable"));
			pageInfoWithImage.setNum(tempMap.get("count"));
			pageInfoWithImage.setNumPercent(number_format(numPercent_system, Float.parseFloat(tempMap.get("count"))));
			setPageInfoMaxLengh(maxPercent, pageInfoWithImage);
			operatingSystemList.add(pageInfoWithImage);
		}
		operatingSystemList = null;
		List<PageInfoWithImage> browserList = stats_agentVO.getBrowserList();
		float numPercent_browser = 0F;
		float maxPercent_browser = 0F;
		for(int i = 0;i<browserResultML.size();i++){
			if(i==0){
				for(Map<String,String> tempM : browserResultML){
					tempD = Float.parseFloat(tempM.get("count"));
					numPercent_browser += tempD;
					maxPercent_browser = tempD>maxPercent_browser?tempD:maxPercent_browser;
				}
				maxPercent_browser = Float.parseFloat(number_format(numPercent_browser, maxPercent_browser));
			}
			pageInfoWithImage = new PageInfoWithImage();
			tempMap = browserResultML.get(i);
			pageInfoWithImage.setInformation(tempMap.get("variable"));
			pageInfoWithImage.setNum(tempMap.get("count"));
			pageInfoWithImage.setNumPercent(number_format(numPercent_browser, Float.parseFloat(tempMap.get("count"))));
			setPageInfoMaxLengh(maxPercent_browser, pageInfoWithImage);
			browserList.add(pageInfoWithImage);
		}
		pageInfoWithImage = null;
		tempMap = null;
		browserList = null;
		setNavbarVO(stats_agentVO.getNavbar(), statstatus, modworkstatus, "agent");
		return stats_agentVO;
	}
	public Stats_postsLogVO getFinalStats_postsLogVO(int timestamp){
		Map<String,String> settingMap = ForumInit.settings;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		Stats_postsLogVO stats_postsLogVO = new Stats_postsLogVO();
		setNavbarVO(stats_postsLogVO.getNavbar(), statstatus, modworkstatus, "posts");
		List<String> newstatvars = new ArrayList<String>();
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		setSubPostlog(stats_postsLogVO.getSubPostLog(), newstatvars, dataBaseDao, timestamp);
		updateNewStatvars(newstatvars, dataBaseDao);
		return stats_postsLogVO;
	}
	public Stats_CompositorVO getFinalStats_threadCompositorVO(){
		Map<String,String> settingMap = ForumInit.settings;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>> list_views = dataBaseDao.executeQuery("SELECT views, tid, subject FROM "+tableprefix+"threads WHERE displayorder>='0' ORDER BY views DESC LIMIT 0, 20");
		List<Map<String,String>> list_replies = dataBaseDao.executeQuery("SELECT replies, tid, subject FROM "+tableprefix+"threads WHERE displayorder>='0' ORDER BY replies DESC LIMIT 0, 20");
		dataBaseDao=null;
		Stats_CompositorVO stats_threadCompositorVO = new Stats_CompositorVO();
		List<Map<String,CompositorInfo>> threadCompositorMapList = stats_threadCompositorVO.getCompositorMapList();
		CompositorInfo threadInfo_views = null;
		Map<String,CompositorInfo> mapTemp_result = null;
		Map<String,String> tempMap_views = null;
		Map<String,String> tempMap_replies = null;
		CompositorInfo threadInfo_replies = null;
		for(int i = 0;i<list_views.size();i++){
			mapTemp_result = new HashMap<String, CompositorInfo>();
			tempMap_views = list_views.get(i);
			threadInfo_views = new CompositorInfo();
			threadInfo_views.setCompositorSign(tempMap_views.get("tid"));
			threadInfo_views.setCompositorName(Common.cutstr(tempMap_views.get("subject"), 45));
			threadInfo_views.setCompositorNum(tempMap_views.get("views"));
			mapTemp_result.put("views", threadInfo_views);
			tempMap_replies = list_replies.get(i);
			threadInfo_replies = new CompositorInfo();
			threadInfo_replies.setCompositorSign(tempMap_replies.get("tid"));
			threadInfo_replies.setCompositorName(Common.cutstr(tempMap_replies.get("subject"),50));
			threadInfo_replies.setCompositorNum(tempMap_replies.get("replies"));
			mapTemp_result.put("replies", threadInfo_replies);
			threadCompositorMapList.add(mapTemp_result);
		}
		threadInfo_views = null;
		mapTemp_result = null;
		tempMap_views = null;
		tempMap_replies = null;
		threadInfo_replies = null;
		threadCompositorMapList = null;
		setNavbarVO(stats_threadCompositorVO.getNavbar(), statstatus, modworkstatus, "threadsrank");
		return stats_threadCompositorVO;
	}
	public Stats_CompositorVO getFinalStats_postsCompositorVO(int timestamp){
		Map<String,String> settingMap = ForumInit.settings;
		Double statscachelife = Double.valueOf(settingMap.get("statscachelife"));
		statscachelife = statscachelife * 60;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("0")&& !tempS.equals("");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		Map<String,String> statvars = getFinalMap("postsrank",dataBaseDao);
		tempS = statvars.get("lastupdate");
		int lastupdate = tempS == null ? 0 : Integer.parseInt(tempS);
		List<String> newstatvars = new ArrayList<String>();
		if(timestamp - lastupdate > statscachelife){
			statvars.clear();
			statvars.put("lastupdate", timestamp+"");
			newstatvars.add("'postsrank', 'lastupdate', '"+timestamp+"'");
		}
		Map<Integer,Map<String,String>> posts = null;
		Map<Integer,Map<String,String>> digestposts = null;
		Map<Integer,Map<String,String>> thismonth = null;
		Map<Integer,Map<String,String>> today = null;
		DataParse dataParse = ((DataParse)BeanFactory.getBean("dataParse"));
		List<Map<String,String>> tempML = null;
		int tempI = 0;
		tempS = statvars.get("posts");
		if(tempS != null){
			posts = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT username, uid, posts FROM "+tableprefix+"members ORDER BY posts DESC LIMIT 0, 20");
			if(tempML != null){
				posts = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					posts.put(tempI++, tempM);
				}
			}
			newstatvars.add("'postsrank', 'posts', '"+dataParse.combinationChar(posts)+"'");
		}
		tempI = 0;
		tempS = statvars.get("digestposts");
		if(tempS != null){
			digestposts = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT username, uid, digestposts FROM "+tableprefix+"members ORDER BY digestposts DESC LIMIT 0, 20");
			if(tempML != null){
				digestposts = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					digestposts.put(tempI++, tempM);
				}
			}
			newstatvars.add("'postsrank', 'digestposts', '"+dataParse.combinationChar(digestposts)+"'");
		}
		tempI = 0;
		tempS = statvars.get("thismonth");
		if(tempS != null){
			thismonth = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT DISTINCT(author) AS username, COUNT(pid) AS posts " +
					"FROM "+tableprefix+"posts WHERE dateline>='"+(timestamp-86400*30)+"' AND invisible='0' AND authorid>'0' " +
					"GROUP BY author ORDER BY posts DESC LIMIT 0, 20");
			if(tempML != null){
				thismonth = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					thismonth.put(tempI++, tempM);
				}
			}
			newstatvars.add("'postsrank', 'thismonth', '"+dataParse.combinationChar(thismonth)+"'");
		}
		tempI = 0;
		tempS = statvars.get("today");
		if(tempS != null){
			today = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT DISTINCT(author) AS username, COUNT(pid) AS posts " +
					"FROM "+tableprefix+"posts WHERE dateline >='"+(timestamp-86400)+"' AND invisible='0' AND authorid>'0' " +
					"GROUP BY author ORDER BY posts DESC LIMIT 0, 20");
			if(tempML != null){
				today = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					today.put(tempI++, tempM);
				}
			}
			newstatvars.add("'postsrank', 'today', '"+dataParse.combinationChar(today)+"'");
		}
		Stats_CompositorVO stats_CompositorVO = new Stats_CompositorVO();
		List<Map<String,CompositorInfo>> compositorMapList = stats_CompositorVO.getCompositorMapList();
		Map<String,CompositorInfo> mapResult = null;
		Map<String,String> stringMapTemp_posts = null;
		Map<String,String> stringMapTemp_digestposts = null;
		Map<String,String> stringMapTemp_thismonth = null;
		Map<String,String> stringMapTemp_today = null;
		CompositorInfo compositorInfo = null;
		for(int i = 0;i<posts.size();i++){
			mapResult = new HashMap<String, CompositorInfo>();
			stringMapTemp_posts = posts.get(i);
			compositorInfo = new CompositorInfo();
			compositorInfo.setCompositorName(stringMapTemp_posts.get("username"));
			compositorInfo.setCompositorNum(stringMapTemp_posts.get("posts"));
			compositorInfo.setCompositorSign(stringMapTemp_posts.get("uid"));
			mapResult.put("allPosts", compositorInfo);
			stringMapTemp_digestposts = digestposts.get(i);
			if(stringMapTemp_digestposts!=null){
				compositorInfo = new CompositorInfo();
				compositorInfo.setCompositorName(stringMapTemp_digestposts.get("username"));
				compositorInfo.setCompositorNum(stringMapTemp_digestposts.get("digestposts"));
				compositorInfo.setCompositorSign(stringMapTemp_digestposts.get("uid"));
				mapResult.put("prime", compositorInfo);
			}
			stringMapTemp_thismonth = thismonth.get(i);
			if(stringMapTemp_thismonth!=null){
				compositorInfo = new CompositorInfo();
				compositorInfo.setCompositorName(stringMapTemp_thismonth.get("username"));
				compositorInfo.setCompositorNum(stringMapTemp_thismonth.get("posts"));
				mapResult.put("dayOf30", compositorInfo);
			}
			stringMapTemp_today = today.get(i);
			if(stringMapTemp_today!=null){
				compositorInfo = new CompositorInfo();
				compositorInfo.setCompositorName(stringMapTemp_today.get("username"));
				compositorInfo.setCompositorNum(stringMapTemp_today.get("posts"));
				mapResult.put("hourOf24", compositorInfo);
			}
			compositorMapList.add(mapResult);
		}
		mapResult = null;
		stringMapTemp_posts = null;
		stringMapTemp_digestposts = null;
		stringMapTemp_thismonth = null;
		stringMapTemp_today = null;
		compositorInfo = null;
		compositorMapList = null;
		setNavbarVO(stats_CompositorVO.getNavbar(), statstatus, modworkstatus, "postsrank");
		updateNewStatvars(newstatvars, dataBaseDao);
		return stats_CompositorVO;
	}
	public Stats_creditComposidorVO getFinalStats_creditCompositorVO(int timestamp,MessageResources mr,Locale locale){
		Map<String,String> settingMap = ForumInit.settings;
		Double statscachelife = Double.valueOf(settingMap.get("statscachelife"));
		statscachelife = statscachelife * 60;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("0")&& !tempS.equals("");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		Map<String,String> statvars = getFinalMap("creditsrank",dataBaseDao);
		tempS = statvars.get("lastupdate");
		int lastupdate = tempS == null ? 0 : Integer.parseInt(tempS);
		List<String> newstatvars = new ArrayList<String>();
		if(timestamp - lastupdate > statscachelife){
			statvars.clear();
			statvars.put("lastupdate", timestamp+"");
			newstatvars.add("'creditsrank', 'lastupdate', '"+timestamp+"'");
		}
		Map<Integer,Map<String,String>> credits = null;
		Map<Integer,Map<Integer,Map<String,String>>> extendedcredits = null;
		List<Map<String,String>> tempML = null;
		int tempI = 0;
		DataParse dataParse = ((DataParse)BeanFactory.getBean("dataParse"));
		tempS = statvars.get("credits");
		if(tempS != null){
			credits = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT username, uid, credits FROM "+tableprefix+"members ORDER BY credits DESC LIMIT 0, 20");
			if(tempML!=null){
				credits = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					credits.put(tempI++, tempM);
				}
			}
			newstatvars.add("'creditsrank', 'credits', '"+dataParse.combinationChar(credits)+"'");
		}
		Map<Integer,Map<String,Object>> extcredits = dataParse.characterParse(settingMap.get("extcredits"), false);
		tempS = statvars.get("extendedcredits");
		if(tempS != null){
			extendedcredits = dataParse.characterParse(tempS, false);
		}else{
			extendedcredits = new HashMap<Integer, Map<Integer,Map<String,String>>>();
			int id = 0;
			Map<Integer,Map<String,String>> tempM2 = null;
			for(Entry<Integer, Map<String,Object>> entry : extcredits.entrySet()){
				id = entry.getKey();
				tempI = 0;
				tempM2 = new HashMap<Integer, Map<String,String>>();
				tempML = dataBaseDao.executeQuery("SELECT username, uid, extcredits"+id+" AS credits FROM "+tableprefix+"members ORDER BY extcredits"+id+" DESC LIMIT 0, 20");
				if(tempML!=null){
					for(Map<String,String> tempM : tempML){
						tempM2.put(tempI++, tempM);
					}
				}
				extendedcredits.put(id, tempM2);
			}
			newstatvars.add("'creditsrank', 'extendedcredits', '"+dataParse.combinationChar(extendedcredits)+"'");
		}
		Stats_creditComposidorVO stats_creditComposidorVO = new Stats_creditComposidorVO();
		List<String> creditNameTopList = stats_creditComposidorVO.getCreditNameTopList();
		creditNameTopList.add(mr.getMessage(locale, "credits"));
		List<String> creditNameDownList = stats_creditComposidorVO.getCreditNameDownList();
		List<Integer> integerTempListTop = new ArrayList<Integer>();
		Map<Integer,String> tempMapDown = new HashMap<Integer,String>();
		Integer extcreditsCount = extcredits.size();
		Integer nowExtcreditsCount = 0;
		for(Entry<Integer, Map<String,Object>> extcreditsEntry : extcredits.entrySet()){
			Integer key = extcreditsEntry.getKey();
			Map<String,Object> map = extcreditsEntry.getValue();
			nowExtcreditsCount++;
			if(extcreditsCount<5){
				integerTempListTop.add(key);
				creditNameTopList.add((String)map.get("title"));
			}else if(extcreditsCount==5){
				if(nowExtcreditsCount<3){
					integerTempListTop.add(key);
					creditNameTopList.add((String)map.get("title"));
				}else{
					tempMapDown.put(key, (String)map.get("title"));
					creditNameDownList.add((String)map.get("title"));
				}
			}else if(extcreditsCount>5&&extcreditsCount<8){
				if(nowExtcreditsCount<4){
					integerTempListTop.add(key);
					creditNameTopList.add((String)map.get("title"));
				}else{
					tempMapDown.put(key, (String)map.get("title"));
					creditNameDownList.add((String)map.get("title"));
				}
			}else{
				if(nowExtcreditsCount<5){
					integerTempListTop.add(key);
					creditNameTopList.add((String)map.get("title"));
				}else{
					tempMapDown.put(key, (String)map.get("title"));
					creditNameDownList.add((String)map.get("title"));
				}
			}
		}
		List<LineObject> lineObjectTopList = stats_creditComposidorVO.getLineObjectTopList();
		List<Map<String,Map<String,String>>> downMapList =  stats_creditComposidorVO.getDownMapList();
		for(int i = 0;i<credits.size();i++){
			Map<String,String> mapTempInI = credits.get(i);
			LineObject lineObject = new Stats_creditComposidorVO.LineObject();
			List<CreditInfo> creditInfoList = lineObject.getCreditInfoList();
			CreditInfo creditInfo_credits = new LineObject.CreditInfo();
			creditInfo_credits.setCreditNum(mapTempInI.get("credits"));
			creditInfo_credits.setUsername(mapTempInI.get("username"));
			creditInfoList.add(creditInfo_credits);
			for(int j = 0;j<integerTempListTop.size();j++){
				Map<Integer,Map<String,String>> extendedcreditsMapIn = extendedcredits.get(integerTempListTop.get(j));
				CreditInfo creditInfo_extendedcredits = new LineObject.CreditInfo();
				Map<String,String> extendedcreditsMapIn2 = extendedcreditsMapIn.get(i);
				creditInfo_extendedcredits.setCreditNum(extendedcreditsMapIn2.get("credits"));
				creditInfo_extendedcredits.setUsername(extendedcreditsMapIn2.get("username"));
				creditInfoList.add(creditInfo_extendedcredits);
			}
			lineObjectTopList.add(lineObject);
			Map<String, Map<String,String>> mapTemp = new HashMap<String, Map<String,String>>();
			Iterator<Entry<Integer,String>> iteratorTemp = tempMapDown.entrySet().iterator();
			while(iteratorTemp.hasNext()){
				Entry<Integer,String> e = iteratorTemp.next();
				Integer mapKeyInteger = e.getKey();
				Map<Integer,Map<String,String>> extendedcreditsMapIn = extendedcredits.get(mapKeyInteger);
				Map<String,String> extendedcreditsMapIn2 = extendedcreditsMapIn.get(i);
				Map<String,String> resultMap = new HashMap<String, String>();
				resultMap.put("username", extendedcreditsMapIn2.get("username"));
				resultMap.put("creditNum",extendedcreditsMapIn2.get("credits"));
				mapTemp.put(e.getValue(), resultMap);
			}
			downMapList.add(mapTemp);
		}
		setNavbarVO(stats_creditComposidorVO.getNavbar(), statstatus, modworkstatus, "creditsrank");
		updateNewStatvars(newstatvars, dataBaseDao);
		return stats_creditComposidorVO;
	}
	public Stats_onlineCompositorVO getFinalStats_onlineCompositorVO (int timestamp,String timeoffsetSession){
		Map<String,String> settingMap = ForumInit.settings;
		Double statscachelife = Double.valueOf(settingMap.get("statscachelife"));
		statscachelife = statscachelife * 60;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("0")&& !tempS.equals("");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<String> newstatvars = new ArrayList<String>();
		Map<String,String> statvars = getFinalMap("onlines",dataBaseDao);
		tempS = statvars.get("lastupdate");
		int lastupdate = tempS == null ? 0 : Integer.parseInt(tempS);
		if(timestamp - lastupdate > statscachelife){
			statvars.clear();
			statvars.put("lastupdate", timestamp+"");
			newstatvars.add("'onlines', 'lastupdate', '"+timestamp+"'");
		}
		DataParse dataParse = ((DataParse)BeanFactory.getBean("dataParse"));
		Stats_onlineCompositorVO stats_onlineCompositorVO = new Stats_onlineCompositorVO();
		List<Map<String, Map<String,String>>> compositorMapList = stats_onlineCompositorVO.getCompositorMapList();
		Map<Integer,Map<String,String>> total = null;
		Map<Integer,Map<String,String>> thismonth = null;
		List<Map<String,String>> tempML = null;
		tempS = statvars.get("total");
		int tempI = 0;
		if(tempS != null){
			total = dataParse.characterParse(tempS, false);
		}else{
			total = new HashMap<Integer, Map<String,String>>();
			tempML = dataBaseDao.executeQuery("SELECT o.uid, m.username, o.total AS time " +
					"FROM "+tableprefix+"onlinetime o " +
					"LEFT JOIN "+tableprefix+"members m USING (uid) " +
					"ORDER BY o.total DESC LIMIT 20");
			if(tempML!=null){
				for(Map<String,String> tempM : tempML){
					tempM.put("time", (Math.round(Double.parseDouble(tempM.get("time"))/60*100)/100D)+"");
					total.put(tempI++, tempM);
				}
			}
			newstatvars.add("'onlines', 'total', '"+dataParse.combinationChar(total)+"'");
		}
		tempS = statvars.get("thismonth");
		tempI = 0;
		if(tempS != null){
			thismonth = dataParse.characterParse(tempS, false);
		}else{
			thismonth = new HashMap<Integer, Map<String,String>>();
			Calendar calendar = Common.getCalendar(settingMap.get("timeoffset"));
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			tempML = dataBaseDao.executeQuery("SELECT o.uid, m.username, o.thismonth AS time " +
					"FROM "+tableprefix+"onlinetime o, "+tableprefix+"members m " +
					"WHERE o.uid=m.uid AND m.lastactivity>='"+(calendar.getTimeInMillis()/1000)+"' " +
					"ORDER BY o.thismonth DESC LIMIT 20");
			if(tempML!=null){
				for(Map<String,String> tempM : tempML){
					tempM.put("time", (Math.round(Double.parseDouble(tempM.get("time"))/60*100)/100D)+"");
					thismonth.put(tempI++, tempM);
				}
			}
			newstatvars.add("'onlines', 'thismonth', '"+dataParse.combinationChar(thismonth)+"'");
		}
		Map<String,Map<String,String>> resultMap = null;
		Map<String,String> totalMapTemp = null;
		Map<String, String> totalResultMap = null;
		Map<String,String> thismonthMapTemp = null;
		Map<String, String> thismonthResultMap = null;
		if(total!=null ){
			tempI = total.size();
			for(int i = 0;i<tempI;i++){
				resultMap = new HashMap<String, Map<String,String>>();
				totalMapTemp = total.get(i);
				totalResultMap = new HashMap<String, String>();
				totalResultMap.put("username", totalMapTemp.get("username"));
				totalResultMap.put("uid", totalMapTemp.get("uid"));
				totalResultMap.put("onlineTime", totalMapTemp.get("time"));
				resultMap.put("total", totalResultMap);
				if(thismonth!=null){
					thismonthMapTemp = thismonth.get(i);
					if(thismonthMapTemp!=null){
						thismonthResultMap = new HashMap<String, String>();
						thismonthResultMap.put("username", (String)thismonthMapTemp.get("username"));
						thismonthResultMap.put("uid", (String)thismonthMapTemp.get("uid"));
						thismonthResultMap.put("onlineTime", thismonthMapTemp.get("time").toString());
						resultMap.put("thisMonth", thismonthResultMap);
					}
				}
				compositorMapList.add(resultMap);
			}
		}
		compositorMapList = null;
		resultMap = null;
		totalMapTemp = null;
		totalResultMap = null;
		thismonthMapTemp = null;
		thismonthResultMap = null;
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffsetSession);
		tempI = Integer.parseInt(statvars.get("lastupdate"));
		stats_onlineCompositorVO.setLastTime(Common.gmdate(dateFormat, tempI));
		stats_onlineCompositorVO.setNextTime(Common.gmdate(dateFormat, (tempI+statscachelife.intValue())));
		setNavbarVO(stats_onlineCompositorVO.getNavbar(), statstatus, modworkstatus, "onlinetime");
		updateNewStatvars(newstatvars, dataBaseDao);
		return stats_onlineCompositorVO;
	}
	public Stats_manageTeamVO getFinalStats_manageTeamVO (int timestamp,String timeoffsetSession,MessageResources mr,Locale locale){
		Map<String,String> settingMap = ForumInit.settings;
		Double statscachelife = Double.valueOf(settingMap.get("statscachelife"));
		statscachelife = statscachelife * 60;
		boolean oltimespan = Double.valueOf(settingMap.get("oltimespan"))>0;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("0")&& !tempS.equals("");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<String> newstatvars = new ArrayList<String>(); 
		DataParse dataParse = ((DataParse)BeanFactory.getBean("dataParse"));
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("", settingMap.get("timeoffset"));
		Map<String,String> statvars = getFinalMap("team" ,dataBaseDao);
		tempS = statvars.get("lastupdate");
		int lastupdate = tempS == null ? 0 : Integer.parseInt(tempS);
		if(timestamp - lastupdate > statscachelife){
			statvars.clear();
			statvars.put("lastupdate", timestamp+"");
			newstatvars.add("'team', 'lastupdate', '"+timestamp+"'");
		}
		Map<Integer,Map<Integer,Map<String,String>>> forums = null;
		Map<Integer,Map<Integer,String>> moderators = null;
		Map<Integer,Map<String,String>> members = null;
		Map<Integer,Map<String,String>> categories = null;
		Map<Integer,String> admins = null;
		double avgoffdays = 0D;
		double avgthismonthposts = 0D;
		double avgtotalol = 0D;
		double avgthismonthol = 0D;
		double avgmodactions = 0D;
		Map<String,Object> team = null;
		tempS = statvars.get("team");
		int tempI = 0;
		if(tempS != null){
			team = dataParse.characterParse(tempS, false);
			forums = (Map<Integer,Map<Integer,Map<String,String>>>)team.get("forums");
			categories = (Map<Integer,Map<String,String>>)team.get("categories");
			admins = (Map<Integer,String>)team.get("admins");
			moderators = (Map<Integer,Map<Integer,String>>)team.get("moderators");
			members = (Map<Integer,Map<String,String>>)team.get("members");
			avgoffdays = Float.parseFloat(String.valueOf(team.get("avgoffdays")));
			avgthismonthposts = Float.parseFloat(String.valueOf(team.get("avgthismonthposts")));
			avgtotalol = Float.parseFloat(String.valueOf(team.get("avgtotalol")));
			avgthismonthol = Float.parseFloat(String.valueOf(team.get("avgthismonthol")));
			avgmodactions = Float.parseFloat(String.valueOf(team.get("avgmodactions")));
		}else{
			team = new HashMap<String, Object>();
			forums = new HashMap<Integer, Map<Integer,Map<String,String>>>();
			moderators = new HashMap<Integer, Map<Integer,String>>();
			members = new HashMap<Integer, Map<String,String>>();
			categories = new HashMap<Integer, Map<String,String>>();
			Map<Integer,Integer> fuptemp = new HashMap<Integer, Integer>();;
			Map<String,String> tempM_SS = new HashMap<String, String>();
			tempM_SS.put("fid", "0");
			tempM_SS.put("fup", "0");
			tempM_SS.put("type", "group");
			tempM_SS.put("name", settingMap.get("bbname"));
			categories.put(0, tempM_SS);
			Map<String,String> uids = new TreeMap<String, String>();
			List<Map<String,String>> tempML = dataBaseDao.executeQuery("SELECT fid, uid FROM "+tableprefix+"moderators WHERE inherited='0' ORDER BY displayorder");
			if(tempML!=null){
				Map<Integer,String> tempM_IS = null;
				for(Map<String,String> moderator : tempML){
					tempS = moderator.get("uid");
					tempM_IS = moderators.get(Integer.parseInt(moderator.get("fid")));
					if(tempM_IS == null){
						tempM_IS = new HashMap<Integer, String>();
						moderators.put(Integer.parseInt(moderator.get("fid")), tempM_IS);
					}
					tempM_IS.put(tempM_IS.size(), tempS);
					uids.put(tempS, tempS);
				}
			}
			String oltimeadd1 = "";
			String oltimeadd2 = "";
			if(oltimespan){
				oltimeadd1 = ", o.thismonth AS thismonthol, o.total AS totalol";
				oltimeadd2 = "LEFT JOIN "+tableprefix+"onlinetime o ON o.uid=m.uid";
			}
			int totaloffdays = 0;
			int totalol = 0;
			int totalthismonthol = 0;
			tempML = dataBaseDao.executeQuery("SELECT m.uid, m.username, m.adminid, m.lastactivity, m.credits, m.posts "+oltimeadd1+" " +
					"FROM "+tableprefix+"members m "+oltimeadd2+" " +
					"WHERE m.uid IN ("+Common.sImplode(uids)+") OR m.adminid IN (1, 2) ORDER BY m.adminid");
			admins = new HashMap<Integer, String>();
			if(tempML!=null){
				dateFormat.applyPattern("yyyyM");
				tempI = 0;
				int offdays = 0;
				double tempD = 0D;
				for(Map<String,String> member : tempML){
					tempS = member.get("adminid");
					if("1".equals(tempS) || "2".equals(tempS)){
						admins.put(tempI++, member.get("uid"));
					}
					offdays = (timestamp - Integer.parseInt(member.get("lastactivity")))/86400;
					member.put("offdays", offdays+"");
					totaloffdays += offdays;
					if(oltimespan){
						tempS = member.get("totalol");
						if(tempS == null){
							tempS = "0";
						}
						tempD = (Math.round(Double.parseDouble(tempS)/60*100) / 100D);
						member.put("totalol", tempD+"");
						totalol += tempD;
						if(dateFormat.format(Long.parseLong(member.get("lastactivity"))*1000L).equals(dateFormat.format(timestamp*1000L))){
							tempS = member.get("thismonthol");
							if(tempS == null){
								tempS = "0";
							}
							tempD = (Math.round(Double.parseDouble(tempS)*100/60) / 100D);
							tempS = tempD+"";
						}else{
							tempD = 0D;
							tempS = "0";
						}
						member.put("thismonthol", tempS);
						totalthismonthol += tempD;
					}
					tempS = member.get("uid");
					members.put(Integer.valueOf(tempS), member);
					uids.put(tempS, tempS);
				}
			}
			int totalthismonthposts = 0;
			tempML = dataBaseDao.executeQuery("SELECT authorid, COUNT(*) AS posts FROM "+tableprefix+"posts " +
					"WHERE dateline>="+(timestamp-86400*30)+" AND authorid IN ("+Common.sImplode(uids)+") AND invisible='0' GROUP BY authorid");
			if(tempML!=null){
				for(Map<String,String> post : tempML){
					tempS = post.get("posts");
					tempM_SS = members.get(Integer.parseInt(post.get("authorid")));
					if(tempM_SS != null){
						tempM_SS.put("thismonthposts", tempS);
					}
					totalthismonthposts += Integer.parseInt(tempS);
				}
			}
			int totalmodactions = 0;
			if(modworkstatus){
				dateFormat.applyPattern("yyyy-MM-01");
				tempS = dateFormat.format(timestamp*1000L);
				tempML = dataBaseDao.executeQuery("SELECT uid, SUM(count) AS actioncount FROM "+tableprefix+"modworks " +
						"WHERE dateline>='"+tempS+"' GROUP BY uid");
				if(tempML!=null){
					for(Map<String,String> member : tempML){
						tempS = member.get("actioncount");
						tempM_SS = members.get(Integer.valueOf(member.get("uid")));
						if(tempM_SS != null){
							tempM_SS.put("modactions", tempS);
						}
						totalmodactions += Integer.parseInt(tempS);
					}
				}
			}
			tempML = dataBaseDao.executeQuery("SELECT fid, fup, type, name, inheritedmod FROM "+tableprefix+"forums WHERE status=1 ORDER BY type, displayorder");
			if(tempML!=null){
				Map<Integer,Map<String,String>> tempM_I_SS = null;
				Map<Integer,String> tempM_IS = null;
				int temp_fid = 0;
				int temp_fup = 0;
				for(Map<String,String> forum : tempML){
					temp_fid = Integer.valueOf(forum.get("fid"));
					temp_fup = Integer.valueOf(forum.get("fup"));
					tempM_IS = moderators.get(temp_fid);
					if(tempM_IS == null){
						forum.put("moderators", "0");
					}else{
						forum.put("moderators", tempM_IS.size()+"");
					}
					tempS = forum.get("type");
					if("group".equals(tempS)){
						categories.put(temp_fid, forum);
						tempM_I_SS = forums.get(temp_fid);
						if(tempM_I_SS == null){
							tempM_I_SS = new HashMap<Integer, Map<String,String>>();
							forums.put(temp_fid, tempM_I_SS);
						}
						tempM_I_SS.put(temp_fid, forum);
						tempI = temp_fid;
					}else if("forum".equals(tempS)){
						tempM_I_SS = forums.get(temp_fup);
						if(tempM_I_SS == null){
							tempM_I_SS = new HashMap<Integer, Map<String,String>>();
							forums.put(temp_fup, tempM_I_SS);
						}
						tempM_I_SS.put(temp_fid, forum);
						fuptemp.put(temp_fid, temp_fup);
						tempI = temp_fup;
					}else if("sub".equals(tempS)){
						tempM_I_SS = forums.get(fuptemp.get(temp_fup));
						if(tempM_I_SS == null){
							tempM_I_SS = new HashMap<Integer, Map<String,String>>();
							forums.put(fuptemp.get(temp_fup), tempM_I_SS);
						}
						tempM_I_SS.put(temp_fid, forum);
						tempI = fuptemp.get(temp_fup);
					}
					if(moderators.get(temp_fid)!=null){
						tempM_SS = categories.get(tempI);
						if(tempM_SS!=null){
							tempM_SS.put("moderating", "1");
						}
					}
				}
			}
			List<Integer> removeKL = new ArrayList<Integer>();
			for(Entry<Integer,Map<String,String>> tempEntry : categories.entrySet()){
				tempM_SS = tempEntry.getValue();
				if(tempM_SS.get("moderating")==null){
					removeKL.add(tempEntry.getKey());
				}
			}
			for(Integer fid : removeKL){
				categories.remove(fid);
			}
			tempI = members.size();
			avgoffdays = (double)totaloffdays/tempI;
			avgthismonthposts = (double)totalthismonthposts / tempI;
			avgtotalol = (double)totalol/tempI;
			avgthismonthol = (double)totalthismonthol/tempI;
			avgmodactions = (double)totalmodactions/tempI;
			team.put("categories", categories);
			team.put("forums", forums);
			team.put("admins", admins);
			team.put("moderators", moderators);
			team.put("members", members);
			team.put("avgoffdays", avgoffdays);
			team.put("avgthismonthposts", avgthismonthposts);
			team.put("avgtotalol", avgtotalol);
			team.put("avgthismonthol", avgthismonthol);
			team.put("avgmodactions", avgmodactions);
			newstatvars.add("'team', 'team', '"+dataParse.combinationChar(team)+"'");
		}
		Stats_manageTeamVO stats_manageTeamVO = new Stats_manageTeamVO();
		List<ForumTableGroup> forumTableGroupList = stats_manageTeamVO.getForumTableGroupList();
		boolean beingAdmin = false;
		List<Map<String,Object>> manageTeamMapList = stats_manageTeamVO.getManageTeamMapList();
		dateFormat.applyPattern("dd/MM/yyyy HH:mm");
		double avgthismonthposts_2 = avgthismonthposts /2;
		double avgthismonthol_2 = avgthismonthol / 2;
		double avgtotalol_2 = avgtotalol /2;
		double avgmodactions_2 = avgmodactions/2;
		Map<String,String> member = null;
		String lastactivity = null;
		String adminid = null;
		Map<String,Object> finalMap_members = null;
		Map<String,String> usernameMap = null;
		Map<String,String> map_thisMonthManage = null;
		for(Entry<Integer,Map<String,String>> tempM : members.entrySet()){
			member = tempM.getValue();
			tempS = member.get("lastactivity");
			tempS = tempS == null ? "0" : tempS;
			lastactivity = Common.gmdate(dateFormat, Integer.parseInt(tempS));
			adminid = member.get("adminid");
			finalMap_members = new HashMap<String, Object>();
			usernameMap = new HashMap<String, String>();
			usernameMap.put("uid", member.get("uid"));
			usernameMap.put("username", member.get("username"));
			finalMap_members.put(mr.getMessage(locale, "username"), usernameMap);
			finalMap_members.put(mr.getMessage(locale, "lastvisit"), lastactivity);
			tempS = member.get("offdays");
			tempS = tempS==null?"0":tempS;
			finalMap_members.put(mr.getMessage(locale, "stats_team_offdays"), Integer.valueOf(tempS)>avgoffdays?"<b><i>"+tempS+"</i></b>":tempS);
			finalMap_members.put(mr.getMessage(locale, "credits"), member.get("credits"));
			finalMap_members.put(mr.getMessage(locale, "a_setting_posts"), member.get("posts"));
			tempS = member.get("thismonthposts");
			tempS = tempS==null?"0":tempS;
			finalMap_members.put(mr.getMessage(locale, "stats_posts_thismonth"), Integer.valueOf(tempS)<avgthismonthposts_2?"<b><i>"+tempS+"</i></b>":tempS);
			if(modworkstatus){
				map_thisMonthManage = new HashMap<String,String>();
				map_thisMonthManage.put("uid", member.get("uid"));
				tempS = member.get("modactions");
				tempS = tempS == null ? "0" : tempS;
				map_thisMonthManage.put("thisMonthManage", tempS);
				finalMap_members.put(mr.getMessage(locale, "stats_modworks_thismonth"), map_thisMonthManage);
			}
			if(oltimespan){
				tempS = member.get("totalol");
				tempS = tempS==null?"0":tempS;
				finalMap_members.put(mr.getMessage(locale, "onlinetime_total"), Double.parseDouble(tempS)<avgtotalol_2?"<b><i>"+tempS+"</i></b>":tempS);
				tempS = member.get("thismonthol");
				tempS = tempS==null?"0":tempS;
				finalMap_members.put(mr.getMessage(locale, "onlinetime_thismonth"), Double.parseDouble(tempS)<avgthismonthol_2?"<b><i>"+tempS+"</i></b>":tempS);
			}
			if(adminid.equals("1")){
				beingAdmin = true;
				finalMap_members.put(mr.getMessage(locale, "admin_status"), mr.getMessage(locale, "admin"));
				manageTeamMapList.add(finalMap_members);
			}else if(adminid.equals("2")){
				finalMap_members.put(mr.getMessage(locale, "admin_status"), mr.getMessage(locale, "usergroups_system_2"));
				manageTeamMapList.add(finalMap_members);
			}
		}
		manageTeamMapList = null;
		finalMap_members = null;
		usernameMap = null;
		map_thisMonthManage = null;
		ForumTableGroup forumTableGroup = null;
		Integer fid = null;
		Map<String,String> mapTemp_categories = null;
		Map<Integer, Map<String, String>> mapTemp_forumTemp = null;
		Map<Integer,String> mapTemp_moderators = null;
		Map<String, String> mapIn_forum = null;
		List<Forum> forumList = null;
		tempI = 0;
		Forum forum = null;
		for (Entry<Integer,Map<String,String>> tempEntry : categories.entrySet()) {
			forumTableGroup = new Stats_manageTeamVO.ForumTableGroup();
			mapTemp_categories = tempEntry.getValue();
			fid = Integer.valueOf(mapTemp_categories.get("fid"));
			mapTemp_forumTemp = forums.get(fid);
			for (Entry<Integer, Map<String, String>> tempEntry2 : mapTemp_forumTemp.entrySet()) {
				for (Entry<Integer,Map<Integer,String>> tempEntry3 : moderators.entrySet()) {
					if (tempEntry2.getKey().intValue() == tempEntry3.getKey().intValue()) {
						mapTemp_moderators = tempEntry3.getValue();
						mapIn_forum = tempEntry2.getValue();
						forumTableGroup.setGroupName((String) mapTemp_categories.get("name"));
						forumTableGroup.setGroupId(fid + "");
						forumList = forumTableGroup.getForumList();
						tempI = mapTemp_moderators.size();
						for (int i = 0; i < tempI; i++) {
							forum = new ForumTableGroup.Forum();
							forum.setShowOnline(oltimespan);
							if(mapTemp_moderators.get(i) == null){
								continue;
							}
							member = members.get(Integer.valueOf(mapTemp_moderators.get(i)));
							if(member==null){
								continue;
							}
							adminid = (String) member.get("adminid");
							lastactivity = Common.gmdate(dateFormat, Integer.parseInt(member.get("lastactivity")));
							forum.setSelectFroumName(i==0);
							forum.setFroumName(mapIn_forum.get("name"));
							if (mapIn_forum.get("type").equals("group")) {
								forum.setUri("index.jsp?gid=" + fid);
							} else {
								forum.setUri("forumdisplay.jsp?fid=" + mapIn_forum.get("fid"));
							}
							tempS = member.get("username");
							if (Integer.valueOf(mapIn_forum.get("inheritedmod")) > 0) {
								tempS = "<b>" + tempS + "</b>";
							}
							forum.setUid(member.get("uid"));
							forum.setUsername(tempS);
							forum.setLastAccessTime(lastactivity);
							tempS = member.get("offdays");
							tempS = tempS == null?"0":tempS;
							forum.setOffDays(Integer.valueOf(tempS)>avgoffdays?"<b><i>"+tempS+"</i></b>":tempS);
							forum.setCredits(member.get("credits"));
							forum.setPosts(member.get("posts"));
							tempS = member.get("thismonthposts");
							tempS = tempS == null?"0":tempS;
							forum.setThisMonthPosts(Integer.valueOf(tempS)<avgthismonthposts_2?"<b><i>"+tempS+"</i></b>":tempS);
							if (modworkstatus) {
								tempS = member.get("modactions");
								tempS = tempS == null ? "0" : tempS;
								forum.setThisMonthManage( Integer.parseInt(tempS)< avgmodactions_2 ? "<b><i>"+tempS+"</i></b>" : tempS );
							} else {
								forum.setThisMonthManage("N/A");
							}
							if (oltimespan) {
								tempS = member.get("totalol");
								tempS = tempS == null?"0":tempS;
								forum.setAllTimeOnline(Double.parseDouble(tempS)<avgtotalol_2?"<b><i>"+tempS+"</i></b>":tempS);
								tempS = member.get("thismonthol");
								tempS = tempS == null?"0":tempS;
								forum.setThisMonthTimeOnline(Double.parseDouble(tempS)<avgthismonthol_2?"<b><i>"+tempS+"</i></b>":tempS);
							}
							if (adminid.equals("1")) {
								beingAdmin = true;
								forum.setManagerName(mr.getMessage(locale, "admin"));
							} else if (adminid.equals("2")) {
								forum.setManagerName(mr.getMessage(locale, "usergroups_system_2"));
							} else if (adminid.equals("3")) {
								forum.setManagerName(mr.getMessage(locale, "usergroups_system_3"));
							}
							forum.setRowspan(mapIn_forum.get("moderators"));
							forumList.add(forum);
						}
					}
				}
			}
			forumTableGroupList.add(forumTableGroup);
		}
		forumTableGroup = null;
		fid = null;
		mapTemp_categories = null;
		mapTemp_forumTemp = null;
		mapTemp_moderators = null;
		mapIn_forum = null;
		forumList = null;
		forum = null;
		tempS = null;
		member = null;
		lastactivity = null;
		adminid = null;
		String[] adminTableTitleArray = null;
		if(beingAdmin){
			if(modworkstatus&&oltimespan){
				adminTableTitleArray = new String[]{mr.getMessage(locale, "username"),
						mr.getMessage(locale, "admin_status"),mr.getMessage(locale, "lastvisit"),mr.getMessage(locale, "stats_team_offdays"),mr.getMessage(locale, "credits"),
						mr.getMessage(locale, "a_setting_posts"),mr.getMessage(locale, "stats_posts_thismonth"),mr.getMessage(locale, "stats_modworks_thismonth"),mr.getMessage(locale, "onlinetime_total"),mr.getMessage(locale, "onlinetime_thismonth")};
			}else if(modworkstatus&&!oltimespan){
				adminTableTitleArray = new String[]{mr.getMessage(locale, "username"),
						mr.getMessage(locale, "admin_status"),mr.getMessage(locale, "lastvisit"),mr.getMessage(locale, "stats_team_offdays"),mr.getMessage(locale, "credits"),
						mr.getMessage(locale, "a_setting_posts"),mr.getMessage(locale, "stats_posts_thismonth"),mr.getMessage(locale, "stats_modworks_thismonth")};
			}else if(!modworkstatus&&oltimespan){
				adminTableTitleArray = new String[]{mr.getMessage(locale, "username"),
						mr.getMessage(locale, "admin_status"),mr.getMessage(locale, "lastvisit"),mr.getMessage(locale, "stats_team_offdays"),mr.getMessage(locale, "credits"),
						mr.getMessage(locale, "a_setting_posts"),mr.getMessage(locale, "stats_posts_thismonth"),mr.getMessage(locale, "onlinetime_total"),mr.getMessage(locale, "onlinetime_thismonth")};
			}else{
				adminTableTitleArray = new String[]{mr.getMessage(locale, "username"),
						mr.getMessage(locale, "admin_status"),mr.getMessage(locale, "lastvisit"),mr.getMessage(locale, "stats_team_offdays"),mr.getMessage(locale, "credits"),
						mr.getMessage(locale, "a_setting_posts"),mr.getMessage(locale, "stats_posts_thismonth")};
			}
		}
		List<String> adminTableTitleList = stats_manageTeamVO.getAdminTableTitleList();
		if(adminTableTitleArray!=null){
			for(int i = 0;i<adminTableTitleArray.length;i++){
				adminTableTitleList.add(adminTableTitleArray[i]);
			}
		}
		adminTableTitleList = null;
		adminTableTitleArray = null;
		String[] forumTableTitleArray = null;
		if(oltimespan){
			forumTableTitleArray = new String[]{mr.getMessage(locale, "forum_name"),
					mr.getMessage(locale, "username"), mr.getMessage(locale, "admin_status"), mr.getMessage(locale, "lastvisit"), mr.getMessage(locale, "stats_team_offdays"), 
					mr.getMessage(locale, "credits"), mr.getMessage(locale, "a_setting_posts"), mr.getMessage(locale, "stats_posts_thismonth"), mr.getMessage(locale, "stats_modworks_thismonth"),mr.getMessage(locale, "onlinetime_total"),mr.getMessage(locale, "onlinetime_thismonth")};
		}else{
			forumTableTitleArray = new String[]{mr.getMessage(locale, "forum_name"),
					mr.getMessage(locale, "username"), mr.getMessage(locale, "admin_status"), mr.getMessage(locale, "lastvisit"), mr.getMessage(locale, "stats_team_offdays"), 
					mr.getMessage(locale, "credits"), mr.getMessage(locale, "a_setting_posts"), mr.getMessage(locale, "stats_posts_thismonth"), mr.getMessage(locale, "stats_modworks_thismonth")};
		}
		List<String> forumTableTitleList = stats_manageTeamVO.getForumTableTitleList();
		for(int i = 0;i<forumTableTitleArray.length;i++){
			forumTableTitleList.add(forumTableTitleArray[i]);
		}
		forumTableTitleList = null;
		forumTableTitleArray = null;
		SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffsetSession);
		tempI = Integer.parseInt(statvars.get("lastupdate"));
		stats_manageTeamVO.setLastTime(Common.gmdate(simpleDateFormat, tempI));
		stats_manageTeamVO.setNextTime(Common.gmdate(simpleDateFormat, (tempI+statscachelife.intValue())));
		setNavbarVO(stats_manageTeamVO.getNavbar(), statstatus, modworkstatus, "team");
		updateNewStatvars(newstatvars, dataBaseDao);
		return stats_manageTeamVO;
	}
	public Stats_tradeCompositorVO getFinalStats_tradeCompositorVO(int timestamp,String timeoffsetSession){
		Map<String,String> settingMap = ForumInit.settings;
		Double statscachelife = Double.valueOf(settingMap.get("statscachelife"));
		statscachelife = statscachelife * 60;
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("0")&& !tempS.equals("");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<String> newstatvars = new ArrayList<String>(); 
		DataParse dataParse = ((DataParse)BeanFactory.getBean("dataParse"));
		Map<String,String> statvars = getFinalMap("trade",dataBaseDao);
		tempS = statvars.get("lastupdate");
		int lastupdate = tempS == null ? 0 : Integer.parseInt(tempS);
		if(timestamp - lastupdate > statscachelife){
			statvars.clear();
			statvars.put("lastupdate", timestamp+"");
			newstatvars.add("'trade', 'lastupdate', '"+timestamp+"'");
		}
		Map<Integer,Map<String,String>> tradesums = null;
		List<Map<String,String>> tempML = null;
		tempS = statvars.get("tradesums");
		int tempI = 0;
		if(tempS != null){
			tradesums = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT subject, tid, pid, seller, sellerid, sum(tradesum) as tradesum FROM "+tableprefix+"trades WHERE tradesum>0 GROUP BY sellerid ORDER BY tradesum DESC LIMIT 10");
			if(tempML !=null){
				tradesums = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					tradesums.put(tempI++, tempM);
				}
			}
			newstatvars.add("'trade', 'tradesums', '"+dataParse.combinationChar(tradesums)+"'");
		}
		Map<Integer,Map<String,String>> totalitems = null;
		tempS = statvars.get("totalitems");
		tempI = 0;
		if(tempS != null){
			totalitems = dataParse.characterParse(tempS, false);
		}else{
			tempML = dataBaseDao.executeQuery("SELECT subject, tid, pid, seller, sellerid, sum(totalitems) as totalitems FROM "+tableprefix+"trades WHERE totalitems>0 GROUP BY sellerid ORDER BY totalitems DESC LIMIT 10");
			if(tempML !=null){
				totalitems = new HashMap<Integer, Map<String,String>>();
				for(Map<String,String> tempM : tempML){
					totalitems.put(tempI++, tempM);
				}
			}
			newstatvars.add("'trade', 'totalitems', '"+dataParse.combinationChar(totalitems)+"'");
		}
		Stats_tradeCompositorVO stats_tradeCompositorVO = new Stats_tradeCompositorVO();
		stats_tradeCompositorVO.setTotalitems(totalitems);
		stats_tradeCompositorVO.setTradesums(tradesums);
		tempI = Integer.parseInt(statvars.get("lastupdate"));
		SimpleDateFormat dateFormatUpdateTime = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffsetSession);
		stats_tradeCompositorVO.setLastUpdate(Common.gmdate(dateFormatUpdateTime, tempI));
		stats_tradeCompositorVO.setNextUpdate(Common.gmdate(dateFormatUpdateTime, (tempI+statscachelife.intValue())));
		setNavbarVO(stats_tradeCompositorVO.getNavbar(), statstatus, modworkstatus, "trade");
		updateNewStatvars(newstatvars, dataBaseDao);
		return stats_tradeCompositorVO;
	}
	public Stats_manageStatisticVO getFinalStats_manageStatisticVO(String beforeIR,String uidFromRequest,MessageResources mr,Locale locale){
		Map<String,String> settingMap = ForumInit.settings;
		if(uidFromRequest==null){
			uidFromRequest="";
		}
		StatvarsDao statvarsDao = (StatvarsDao)BeanFactory.getBean("statvarsDao");
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		int maxmodworksmonthsInteger = Integer.parseInt(settingMap.get("maxmodworksmonths"));
		Stats_manageStatisticVO stats_manageStatisticVO = new Stats_manageStatisticVO();
		stats_manageStatisticVO.setShowUsername(uidFromRequest.equals(""));
		int before =0;
		if(beforeIR!=null && !beforeIR.equals("")){
			before = Integer.valueOf(beforeIR);
		}
		before = before > 0 && before <=  maxmodworksmonthsInteger ? before : 0 ;
		Map<String,String> mergeActions = new HashMap<String, String>();
		mergeActions.put("OPN", "CLS");mergeActions.put("ECL", "CLS");mergeActions.put("UEC", "CLS");
		mergeActions.put("EOP", "CLS");mergeActions.put("UEO", "CLS");mergeActions.put("UDG", "DIG");
		mergeActions.put("EDI", "DIG");mergeActions.put("UED", "DIG");mergeActions.put("UST", "STK");
		mergeActions.put("EST", "STK");mergeActions.put("UES", "STK");mergeActions.put("DLP", "DEL");
		mergeActions.put("PRN", "DEL");mergeActions.put("UDL", "DEL");mergeActions.put("UHL", "HLT");
		mergeActions.put("EHL", "HLT");mergeActions.put("UEH", "HLT");mergeActions.put("SPL", "MRG");
		mergeActions.put("ABL", "EDT");mergeActions.put("RBL", "EDT");
		List<TimeInfo> timeInfoList = stats_manageStatisticVO.getTimeInfoList();
		int finalMonth = 0;
		int finalYear = 0;
		String startTime = ""; 
		String endTime = ""; 
		Integer dayCount = 0; 
		String thismonth = null; 
		Calendar calendar = Common.getCalendar(settingMap.get("timeoffset"));
		int nowDateInteger = (int)(calendar.getTimeInMillis()/1000);
		int nowMonth = calendar.get(Calendar.MONTH)+1;
		int nowYear = calendar.get(Calendar.YEAR);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		for(int i = 0;i<=maxmodworksmonthsInteger;i++){
			if(i!=0){
				calendar.add(Calendar.MONTH, -1);
			}
			finalMonth = calendar.get(Calendar.MONTH)+1;
			finalYear = calendar.get(Calendar.YEAR);
			String yearAndMonth = finalMonth<10?finalYear+"-0"+finalMonth:finalYear+"-"+finalMonth;
			TimeInfo timeInfo = new Stats_manageStatisticVO.TimeInfo();
			timeInfo.setBefore(i+"");
			timeInfo.setTime(yearAndMonth);
			timeInfo.setNowTime(i==before);
			timeInfo.setUid(uidFromRequest);
			timeInfoList.add(timeInfo);
			if (i == before) {
				Integer endMonth = finalMonth+1;
				Integer endYear  = null;
				if(endMonth>12){
					endMonth = 1;
					endYear = finalYear+1;
				}else{
					endYear = finalYear;
				}
				startTime = yearAndMonth+"-01";
				endTime = endMonth<10?endYear+"-0"+endMonth+"-01":endYear+"-"+endMonth+"-01";
				if(before==0){
					dayCount = nowDay;
				}else{
					dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				}
				thismonth = yearAndMonth;
			}
		}
		timeInfoList = null;
		int expireMonth = nowMonth-maxmodworksmonthsInteger;
		int expireYear = 0;
		if(expireMonth<=0){
			expireYear = nowYear-(1-(expireMonth/12));
			expireMonth = expireMonth%12+12;
		}else{
			expireYear = nowYear;
		}
		String expireTime = expireYear+((expireMonth<10)?"-0":"-")+expireMonth;
		boolean bool_needToUpdate = false;
		String variable = before==0?"thismonth":startTime;
		List<ContentInfo> contentInfoList = stats_manageStatisticVO.getContentInfoList();
		if(uidFromRequest.equals("")){
			Map finalMap = new HashMap();
			List<Map<String,String>> list_statvars = dataBaseDao.executeQuery("SELECT type, variable, value FROM "+tableprefix+"statvars WHERE type='modworks' AND variable='"+variable+"'");
			if(list_statvars.size()>0){
				Map<String,String> resultMap_statvars = list_statvars.get(0);
				String value_statvars = resultMap_statvars.get("value");
				Map valueMap_statvars = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(value_statvars, false);
				String thisMonthInValueMap = valueMap_statvars.get("thismonth")==null?"":(String)valueMap_statvars.get("thismonth");
				Integer lastUpdateInValueMap = valueMap_statvars.get("lastupdate")==null?0:(Integer)valueMap_statvars.get("lastupdate");
				if(before==0&&(nowDateInteger - lastUpdateInValueMap > Double.parseDouble(settingMap.get("statscachelife"))*60)||(!thisMonthInValueMap.equals(startTime))){
					bool_needToUpdate = true;
				}else{
					finalMap = valueMap_statvars;
				}
			}else{
				bool_needToUpdate = true;
			}
			if(bool_needToUpdate){
				StringBuffer uids = new StringBuffer("0"); 
				Integer totalmodactions = 0; 
				Integer avgmodactioncount = 0;	
				List<Map<String,String>> list_members = dataBaseDao.executeQuery("SELECT uid, username, adminid FROM "+tableprefix+"members WHERE adminid IN (1, 2, 3) ORDER BY adminid, uid");
				Map halfFinal_level1 = null;
				for(int i = 0;i<list_members.size();i++){
					halfFinal_level1 = new HashMap();
					Map<String,String> resultMap_members = list_members.get(i);
					String uid = (String)resultMap_members.get("uid");
					String username = (String)resultMap_members.get("username");
					halfFinal_level1.put("uid", uid);
					halfFinal_level1.put("username", username);
					halfFinal_level1.put("adminid", (String)resultMap_members.get("adminid"));
					finalMap.put(Integer.valueOf(resultMap_members.get("uid")), halfFinal_level1);
					uids.append(", "+resultMap_members.get("uid"));
				}
				List<Map<String,String>> list_modwords = dataBaseDao.executeQuery("SELECT uid, modaction, SUM(count) AS count, SUM(posts) AS posts FROM "+tableprefix+"modworks WHERE uid IN ("+uids+") AND dateline>='"+startTime+"' AND dateline<'"+endTime+"' GROUP BY uid, modaction");
				for(int i = 0;i<list_modwords.size();i++){
					Map<String,String> resultMap_modwords = list_modwords.get(i);
					halfFinal_level1 = (Map)finalMap.get(Integer.valueOf(resultMap_modwords.get("uid")));
					if(halfFinal_level1==null){
						continue;
					}
					Integer lastTotal = halfFinal_level1.get("total")==null?0:(Integer)halfFinal_level1.get("total");
					Integer nowTotal = Integer.valueOf(resultMap_modwords.get("count"));
					Integer thisUserTotal = lastTotal+nowTotal;
					totalmodactions += nowTotal;
					String modactionFromDB = resultMap_modwords.get("modaction");
					if(mergeActions.get(modactionFromDB)!=null){
						resultMap_modwords.put("modaction", mergeActions.get(modactionFromDB));
					}
					if(thisUserTotal!=0){
						halfFinal_level1.put("total", thisUserTotal);
					}
					Map<String,Integer> halfFinal_level2 = (Map)halfFinal_level1.get(resultMap_modwords.get("modaction"));
					Integer halfFinal_level2_lastCount = 0;
					Integer halfFinal_level2_lastPosts = 0;
					Integer halfFinal_level2_nowCount = nowTotal;
					Integer halfFinal_level2_nowPosts = Integer.valueOf(resultMap_modwords.get("posts"));
					if(halfFinal_level2 == null){
						halfFinal_level2 = new HashMap<String, Integer>();
					}else{
						halfFinal_level2_lastCount = halfFinal_level2.get("count");
						halfFinal_level2_lastPosts = halfFinal_level2.get("posts");
					}
					Integer halfFinal_level2_resultCount = halfFinal_level2_lastCount+halfFinal_level2_nowCount;
					Integer halfFinal_level2_resultPosts = halfFinal_level2_lastPosts+halfFinal_level2_nowPosts;
					if(halfFinal_level2_resultCount!=0||halfFinal_level2_resultPosts!=0){
						halfFinal_level2.put("count", halfFinal_level2_resultCount);
						halfFinal_level2.put("posts", halfFinal_level2_resultPosts);
					}
					if(halfFinal_level2.size()>0){
						halfFinal_level1.put(resultMap_modwords.get("modaction"), halfFinal_level2);
					}
				}
				avgmodactioncount = Math.round((float)totalmodactions/finalMap.size());
				Iterator<Integer> iterator_finalMapKey = finalMap.keySet().iterator();
				for(int i = 0;i<list_members.size();i++){
					Map<String,String> resultMap_members = list_members.get(i);
					Integer nowUid = Integer.valueOf(resultMap_members.get("uid"));
					halfFinal_level1 = (Map)finalMap.get(nowUid);
					halfFinal_level1.put("totalactions", halfFinal_level1.get("totalactions")==null?0:(Integer)halfFinal_level1.get("totalactions"));
					Integer temp = halfFinal_level1.get("total")==null?0:(Integer)halfFinal_level1.get("total");
					String username = (String)halfFinal_level1.get("username");
					halfFinal_level1.put("username", temp<avgmodactioncount/2?"<b><i>"+username+"</i></b>":username);
				}
				halfFinal_level1 = null;
				Statvars statvars = new Statvars();
				StatvarsId statvarsId = new StatvarsId();
				List<Statvars> listTemp = new ArrayList<Statvars>();
				statvarsId.setType("modworks");
				statvarsId.setVariable(variable);
				statvars.setId(statvarsId);
				statvarsId = null;
				if(before==0){
					finalMap.put("thismonth", startTime);
					finalMap.put("lastupdate", nowDateInteger);
				}else{
					String sql_delete1 = "DELETE FROM "+tableprefix+"statvars WHERE type='modworks' AND variable<'"+expireTime+"'";
					String sql_delete2 = "DELETE FROM "+tableprefix+"modworks WHERE dateline<'"+expireTime+"-01"+"'";
					dataBaseDao.executeDelete(sql_delete1);
					dataBaseDao.executeDelete(sql_delete2);
				}
				statvars.setValue(((DataParse)BeanFactory.getBean("dataParse")).combinationChar(finalMap));
				listTemp.add(statvars);
				statvars = null;
				statvarsDao.updateStatvarsForMain(listTemp);
			}
			Iterator iterator_finalMap = finalMap.keySet().iterator();
			Object key = null;
			while(iterator_finalMap.hasNext()){
				key = iterator_finalMap.next();
				if(key instanceof String){
					continue;
				}
				ContentInfo contentInfo = new Stats_manageStatisticVO.ContentInfo();
				Map<String,Map<String,String>> columnOfNumberMapMap = contentInfo.getColumnOfNumberMapMap();
				contentInfo.setBefore(before+"");
				Map halfFinal_level1 = (Map)finalMap.get(key);
				Iterator iterator_halfFinal_level1 = halfFinal_level1.keySet().iterator();
				while(iterator_halfFinal_level1.hasNext()){
					Object key_halfFinal_level1 = iterator_halfFinal_level1.next();
					if(key_halfFinal_level1 instanceof String){
						String finalKey = (String)key_halfFinal_level1;
						if(finalKey.equals("uid")){
							contentInfo.setUid((String)halfFinal_level1.get("uid"));
						}else if(finalKey.equals("username")){
							contentInfo.setUsername((String)halfFinal_level1.get("username"));
						}else if(finalKey.equals("adminid")){
						}else if(finalKey.equals("total")){
						}else if(finalKey.equals("totalactions")){
						}else{
							Map level2Map = (Map)halfFinal_level1.get(finalKey);
							Map<String,String> tempMap = new HashMap<String, String>();
							tempMap.put("number",level2Map.get("count").toString());
							tempMap.put("title",mr.getMessage(locale, "post2")+level2Map.get("posts").toString());
							columnOfNumberMapMap.put(finalKey, tempMap);
						}
					}
				}
				contentInfoList.add(contentInfo);
			}
			key = null;
		}else{
			stats_manageStatisticVO.setShowThisMonthManageStatistic(true);
			List<Map<String,String>> list_modwords = dataBaseDao.executeQuery("SELECT uid, modaction, dateline, count, posts FROM "+tableprefix+"modworks WHERE uid=? AND dateline>='"+startTime+"' AND dateline<'"+endTime+"'",uidFromRequest);
			Map<String,Map<String,String>> columnOfAllNumberMap = stats_manageStatisticVO.getColumnOfAllNumberMap();
			for(int i = 0;i<dayCount;i++){
				String timeOfDay = thismonth+(i+1<10?("-0"+(i+1)):("-"+(i+1)));
				ContentInfo contentInfo = new Stats_manageStatisticVO.ContentInfo();
				Map<String,Map<String,String>> columnOfNumberMapMap = contentInfo.getColumnOfNumberMapMap();
				contentInfo.setTimeOfDay(timeOfDay);
				String count = null;
				String posts = null;
				String actionType = null;
				for(Map<String,String> resultMap : list_modwords){
					if(resultMap.get("dateline").equals(timeOfDay)){
						count = resultMap.get("count");
						posts = resultMap.get("posts");
						actionType = resultMap.get("modaction");
						if(mergeActions.get(actionType)!=null){
							actionType = mergeActions.get(actionType);
						}
						Map<String,String> tempMap = columnOfNumberMapMap.get(actionType);
						if(tempMap==null){
							tempMap = new HashMap<String, String>();
							tempMap.put("number", count);
							tempMap.put("title",mr.getMessage(locale, "post2")+posts);
							columnOfNumberMapMap.put(actionType, tempMap);
						}else{
							String lastCount = tempMap.get("number");
							tempMap.put("number",(Integer.valueOf(count)+Integer.valueOf(lastCount))+"");
							String lastPostString = tempMap.get("title");
							Integer lastPostInteger = Integer.valueOf(lastPostString.substring(3));
							tempMap.put("title",mr.getMessage(locale, "post2")+(lastPostInteger+Integer.valueOf(posts)));
						}
						Map<String,String> mapTemp = columnOfAllNumberMap.get(actionType);
						if(mapTemp==null){
							mapTemp = new HashMap<String, String>();
							mapTemp.put("allNumber", count);
							mapTemp.put("titleAllPost",mr.getMessage(locale, "post2")+posts);
							columnOfAllNumberMap.put(actionType, mapTemp);
						}else{
							String lastCount = mapTemp.get("allNumber");
							mapTemp.put("allNumber",(Integer.valueOf(count)+Integer.valueOf(lastCount))+"");
							String lastPostString = mapTemp.get("titleAllPost");
							Long lastPostInteger = Long.valueOf(lastPostString.substring(3,lastPostString.length()));
							mapTemp.put("titleAllPost",mr.getMessage(locale, "post2")+(lastPostInteger+Integer.valueOf(posts)));
						}
					}
				}
				contentInfoList.add(contentInfo);
			}
		}
		List<String> modactioncode = new ArrayList<String>();
		modactioncode.add("EDT");modactioncode.add("DEL");modactioncode.add("DLP");
		modactioncode.add("PRN");modactioncode.add("UDL");modactioncode.add("DIG");
		modactioncode.add("UDG");modactioncode.add("EDI");modactioncode.add("UED");
		modactioncode.add("CLS");modactioncode.add("OPN");modactioncode.add("ECL");
		modactioncode.add("UEC");modactioncode.add("EOP");modactioncode.add("UEO");
		modactioncode.add("STK");modactioncode.add("UST");modactioncode.add("EST");
		modactioncode.add("UES");modactioncode.add("SPL");modactioncode.add("MRG");
		modactioncode.add("HLT");modactioncode.add("UHL");modactioncode.add("EHL");
		modactioncode.add("UEH");modactioncode.add("BMP");modactioncode.add("DWN");
		modactioncode.add("MOV");modactioncode.add("CPY");modactioncode.add("TYP");
		modactioncode.add("RFD");modactioncode.add("MOD");modactioncode.add("ABL");
		modactioncode.add("RBL");modactioncode.add("RMR");modactioncode.add("BNP");
		modactioncode.add("UBN");modactioncode.add("REC");modactioncode.add("URE");
		List<String> titleInfoList = stats_manageStatisticVO.getTitleInfoList();
		Map<String,String> titleInfoMap = stats_manageStatisticVO.getTitleInfoMap();
		for(String modactioncodeString : modactioncode){
			if(mergeActions.get(modactioncodeString)==null){
				titleInfoList.add(modactioncodeString);
				titleInfoMap.put(modactioncodeString, mr.getMessage(locale,modactioncodeString));
			}
		}
		for(ContentInfo contentInfo : contentInfoList){
			Map<String,Map<String,String>> columnOfNumberMapMap = contentInfo.getColumnOfNumberMapMap();
			for(String titleInfo : titleInfoList){
				if(columnOfNumberMapMap.get(titleInfo)==null){
					Map<String,String> tempMap = new HashMap<String, String>();
					tempMap.put("number","0");
					tempMap.put("title","");
					columnOfNumberMapMap.put(titleInfo, tempMap);
				}
			}
		}
		contentInfoList = null;
		String tdWidth = Math.round((90 / titleInfoList.size()))+"%";
		stats_manageStatisticVO.setTdWidth(tdWidth);
		String tempS = settingMap.get("statstatus");
		boolean statstatus = tempS!=null && !tempS.equals("0")&& !tempS.equals("");
		tempS = settingMap.get("modworkstatus");
		boolean modworkstatus = tempS!=null && !tempS.equals("") && !tempS.equals("0");
		setNavbarVO(stats_manageStatisticVO.getNavbar(), statstatus, modworkstatus, "modworks");
		statvarsDao=null;
		dataBaseDao=null;
		return stats_manageStatisticVO;
	}
	private void setNavbarVO(Stats_navbarVO stats_navbarVO,boolean statstatus,boolean modworkstatus,String type){
		stats_navbarVO.setStatistic(statstatus);
		stats_navbarVO.setModworkstatus(modworkstatus);
		stats_navbarVO.setType(type);
	}
	private Map<String,String> getFinalMap(String type,DataBaseDao dataBaseDao){
		List<Map<String,String>> resultML = dataBaseDao.executeQuery("SELECT sta.variable, sta.value FROM "+tableprefix+"statvars AS sta WHERE sta.type='"+type+"'");
		Map<String,String> statvars = new HashMap<String,String>();
		for (Map<String,String> statvarsMap : resultML) {
			statvars.put(statvarsMap.get("variable"), statvarsMap.get("value"));
		}
		return statvars;
	}
	private Map<String,String> getRunTimeAdPost(DataBaseDao dataBaseDao){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct, (MAX(dateline)-MIN(dateline))/86400 AS rt FROM "+tableprefix+"posts");
		if(rml!=null && rml.size()>0){
			Map<String,String> tempM = rml.get(0);
			if(tempM.get("ct")==null){
				tempM.put("ct", "0");
			}
			if(tempM.get("rt")==null){
				tempM.put("rt", "0");
			}
			return tempM;
		}else{
			Map<String,String> tm = new HashMap<String, String>();
			tm.put("ct", "0");
			tm.put("rt", "0");
			return tm;
		}
	}
	private String getForumCount(DataBaseDao dataBaseDao){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tableprefix+"forums WHERE type IN ('forum', 'sub') AND status=1");
		if(rml!=null && rml.size()>0){
			return rml.get(0).get("ct");
		}else{
			return "0";
		}
	}
	private String getThreadCount(DataBaseDao dataBaseDao){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tableprefix+"threads WHERE displayorder>='0'");
		if(rml!=null && rml.size()>0){
			return rml.get(0).get("ct");
		}else{
			return "0";
		}
	}
	private String getMemberCount(DataBaseDao dataBaseDao){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tableprefix+"members");
		if(rml!=null && rml.size()>0){
			return rml.get(0).get("ct");
		}else{
			return "0";
		}
	}
	private String  getAddPostsInLast24(DataBaseDao dataBaseDao,int timestamp){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tableprefix+"posts WHERE dateline>='"+(timestamp - 86400)+"' AND invisible='0'");
		if(rml!=null && rml.size()>0){
			return rml.get(0).get("ct");
		}else{
			return "0";
		}
	}
	private String getAddMemberInLast24(DataBaseDao dataBaseDao,int timestamp){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tableprefix+"members WHERE regdate>='"+(timestamp - 86400)+"'");
		if(rml!=null && rml.size()>0){
			return rml.get(0).get("ct");
		}else{
			return "0";
		}
	}
	private String getAdminNum(DataBaseDao dataBaseDao){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tableprefix+"members WHERE adminid>'0'");
		if(rml!=null && rml.size()>0){
			return rml.get(0).get("ct");
		}else{
			return "0";
		}
	}
	private String getNonPost(DataBaseDao dataBaseDao){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tableprefix+"members WHERE posts='0'");
		if(rml!=null && rml.size()>0){
			return rml.get(0).get("ct");
		}else{
			return "0";
		}
	}
	private Map<String,String> getHotforumInfo(DataBaseDao dataBaseDao){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT posts, threads, fid, name FROM "+tableprefix+"forums WHERE status=1 ORDER BY posts DESC LIMIT 1");
		if(rml!=null && rml.size()>0){
			return rml.get(0);
		}else{
			Map<String,String> tm = new HashMap<String, String>();
			tm.put("name", "");
			tm.put("posts", "0");
			tm.put("threads", "0");
			tm.put("fid", "0");
			return tm;
		}
	}
	private Map<String,String> getBestMenInfo(DataBaseDao dataBaseDao,int timestamp){
		List<Map<String,String>> rml = dataBaseDao.executeQuery("SELECT author, COUNT(*) AS posts FROM "+tableprefix+"posts WHERE dateline>='"+(timestamp-86400)+"' AND invisible='0' AND authorid>'0' GROUP BY author ORDER BY posts DESC LIMIT 1");
		if(rml!=null && rml.size()>0){
			Map<String,String> rm = rml.get(0);
			String author = rm.get("author");
			rm.put("author", "<a href=\"space.jsp?username="+Common.encode(author)+"\"><strong>"+author+"</strong></a>");
			return rml.get(0);
		}else{
			Map<String,String> tm = new HashMap<String, String>();
			tm.put("author", "None");
			tm.put("posts", "0");
			return tm;
		}
	}
	private String number_format(float total,float num){
		return df.format(total>0?(num/total)*100:0);
	}
	private void setSubPostlog(SubPostlog subPostlog,List<String> newstatvars,DataBaseDao dataBaseDao,int timestamp){
		String timeoffsetSetting = ForumInit.settings.get("timeoffset");
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyyMMdd", timeoffsetSetting);
		int maxmonthposts = 0;
		int maxdayposts = 0;
		Map<String,String> stats_monthposts = new TreeMap<String, String>();
		Map<String,String> stats_dayposts = new TreeMap<String,String>();
		stats_dayposts.put("starttime", Common.gmdate(dateFormat, timestamp - 86400 * 30));
		dataBaseDao.execute("DELETE FROM "+tableprefix+"statvars WHERE type='dayposts' AND variable<'"+stats_dayposts.get("starttime")+"'");
		List<PageInfo> ereryMonthPost = subPostlog.getEreryMonthPost();
		List<PageInfo> ereryDayPost = subPostlog.getEreryDayPost();
		PageInfo pageInfo = null;
		float tempAllMF = 0;
		float tempAllDF = 0;
		String tempS = null;
		List<Map<String,String>> tempMapList = dataBaseDao.executeQuery("SELECT * FROM "+tableprefix+"statvars WHERE type='monthposts' OR type='dayposts' ORDER BY variable");
		if(tempMapList!=null){
			for(Map<String,String> variable : tempMapList){
				if("monthposts".equals(variable.get("type"))){
					tempS = variable.get("variable");
					stats_monthposts.put(variable.get("variable"), variable.get("value"));
					if("starttime".equals(tempS)){
						continue;
					}
					pageInfo = new PageInfo();
					if(tempS.length() == 6){
						tempS = tempS.substring(0,4)+" - "+tempS.substring(4);
					}
					pageInfo.setInformation(tempS);
					tempS = variable.get("value");
					tempS = tempS!=null&&!tempS.equals("") ? tempS : "0";
					pageInfo.setNum(tempS);
					tempAllMF += Integer.parseInt(tempS);
					ereryMonthPost.add(pageInfo);
				}else{
					tempS = variable.get("value");
					tempS = tempS!=null&&!tempS.equals("") ? tempS : "0";
					tempAllDF += Integer.parseInt(tempS);
					pageInfo = new PageInfo();
					pageInfo.setNum(tempS);
					tempS = variable.get("variable");
					if(tempS.length() == 8){
						tempS = tempS.substring(0,4)+" - "+tempS.substring(4,6)+" - "+tempS.substring(6);
					}
					pageInfo.setInformation(tempS);
					ereryDayPost.add(pageInfo);
					stats_dayposts.put(variable.get("variable"), variable.get("value"));
				}
			}
		}
		dateFormat.applyPattern("yyyy-MM-01");
		if(stats_monthposts.get("starttime") == null){
			tempMapList = dataBaseDao.executeQuery("SELECT MIN(dateline) AS mdl FROM "+tableprefix+"posts");
			String starttime = "0";
			if(tempMapList!=null && tempMapList.size()>0){
				starttime = tempMapList.get(0).get("mdl");
				if(starttime == null){
					starttime = "0";
				}
			}
			if(starttime.equals("0")||starttime.equals("")){
				stats_monthposts.put("starttime", Common.gmdate(dateFormat, timestamp));
			}else{
				stats_monthposts.put("starttime", Common.gmdate(dateFormat, Integer.parseInt(starttime)));
			}
			newstatvars.add("'monthposts', 'starttime', '"+stats_monthposts.get("starttime")+"'");
		}
		dateFormat.applyPattern("yyyy-MM-dd");
		int dateline = 0;
		try {
			dateline = (int)(dateFormat.parse(stats_monthposts.get("starttime")).getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dateFormat.applyPattern("yyyyMM");
		Calendar calendar = Common.getCalendar(timeoffsetSetting);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		long nowMonthFirstDay = calendar.getTimeInMillis()/1000;
		calendar.setTimeInMillis(dateline*1000L);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String month = null;
		int tempI = 0;
		for(;dateline<nowMonthFirstDay;){
			month = Common.gmdate(dateFormat, dateline);
			if(stats_monthposts.get(month)==null){
				tempMapList = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tableprefix+"posts WHERE dateline > "+dateline+" AND dateline<= "+(dateline+2592000)+" AND invisible='0'");
				if(tempMapList!=null && tempMapList.size()>0){
					tempS = tempMapList.get(0).get("ct");
					tempS = tempS!=null&&!tempS.equals("") ? tempS : "0";
					tempAllMF += Integer.parseInt(tempS);
					pageInfo = new PageInfo();
					pageInfo.setNum(tempS);
					tempS = month;
					if(tempS.length() == 6){
						tempS = tempS.substring(0,4)+" - "+tempS.substring(4);
					}
					pageInfo.setInformation(tempS);
					ereryMonthPost.add(pageInfo);
					stats_monthposts.put(month, tempMapList.get(0).get("ct"));
					newstatvars.add("'monthposts', '"+month+"', '"+stats_monthposts.get(month)+"'");
				}
			}
			tempI = Integer.parseInt(stats_monthposts.get(month));
			if(tempI > maxmonthposts){
				maxmonthposts = tempI;
			}
			calendar.add(Calendar.MONTH, 1);
			dateline = (int)(calendar.getTimeInMillis()/1000);
		}
		calendar.setTimeInMillis((timestamp - 86400 * 30)*1000L);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		dateline = (int)(calendar.getTimeInMillis()/1000);
		calendar.setTimeInMillis((timestamp)*1000L);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		int nowDayTime = (int)(calendar.getTimeInMillis()/1000);
		String day = null;
		dateFormat.applyPattern("yyyyMMdd");
		for(;dateline < nowDayTime; dateline += 86400){
			day = Common.gmdate(dateFormat, dateline);
			if(stats_dayposts.get(day) == null){
				tempMapList = dataBaseDao.executeQuery("SELECT COUNT(*) AS ct FROM "+tableprefix+"posts WHERE dateline > "+dateline+" AND dateline<="+(dateline+86400)+" AND invisible='0'");
				if(tempMapList!=null && tempMapList.size()>0){
					tempS = tempMapList.get(0).get("ct");
					tempS = tempS!=null&&!tempS.equals("") ? tempS : "0";
					tempAllDF += Integer.parseInt(tempS);
					pageInfo = new PageInfo();
					pageInfo.setNum(tempS);
					tempS = day;
					if(tempS.length() == 8){
						tempS = tempS.substring(0,4)+" - "+tempS.substring(4,6)+" - "+tempS.substring(6);
					}
					pageInfo.setInformation(tempS);
					ereryDayPost.add(pageInfo);
					stats_dayposts.put(day, tempMapList.get(0).get("ct"));
					newstatvars.add("'dayposts', '"+day+"', '"+stats_dayposts.get(day)+"'");
				}
			}
			tempI = Integer.parseInt(stats_dayposts.get(day));
			if(tempI > maxdayposts){
				maxdayposts = tempI;
			}
		}
		float maxPercent = (maxmonthposts / tempAllMF) * 100;
		for(PageInfo pageInfo_ : ereryMonthPost){
			pageInfo_.setNumPercent(number_format(tempAllMF,Float.parseFloat(pageInfo_.getNum())));
			setPageInfoMaxLengh(maxPercent, pageInfo_);
		}
		maxPercent = (maxdayposts / tempAllDF) * 100;
		for(PageInfo pageInfo_ : ereryDayPost){
			pageInfo_.setNumPercent(number_format(tempAllDF,Float.parseFloat(pageInfo_.getNum())));
			setPageInfoMaxLengh(maxPercent, pageInfo_);
		}
	}
	private void setPageInfoMaxLengh(float maxPercent,PageInfo pageInfo){
		pageInfo.setMaxLengh((int)(37000/maxPercent));
	}
	private void updateNewStatvars(List<String> newstatvars,DataBaseDao dataBaseDao){
		StringBuffer newdata = new StringBuffer();
		if(newstatvars!=null && newstatvars.size()>0){
			for(String tempS : newstatvars){
				newdata.append(tempS+"),(");
			}
			dataBaseDao.execute("REPLACE INTO "+tableprefix+"statvars (type, variable, value) VALUES ("+newdata.substring(0, newdata.length()-3)+")");
		}
	}
}
