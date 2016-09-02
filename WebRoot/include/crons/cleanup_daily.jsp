<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:directive.page import="cn.jsprun.utils.BeanFactory" />
<jsp:directive.page import="cn.jsprun.utils.Cache" />
<jsp:directive.page import="java.io.File"/>
<%@page import="cn.jsprun.dao.CronsDao"%>
<%@page import="java.sql.Connection"%>
<%@page import="cn.jsprun.utils.Common"%>
<%@page import="cn.jsprun.dao.DataBaseDao"%>
<%@page import="cn.jsprun.utils.DataParse"%>
<%@page import="cn.jsprun.utils.JspRunConfig"%>
<%! 
	private String tablepre = "jrun_"; 
	private CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));

	private boolean removedir(String cachethreaddir,boolean bool){ 
		cachethreaddir = wipespecial(cachethreaddir);
		File file = new File( cachethreaddir);
		if(!file.isDirectory()){
			return false;
		}else{
			File[] fileArray = file.listFiles();
			for(int i = 0;i<fileArray.length;i++){
				File fileTemp = fileArray[i];
				if(fileTemp.isDirectory()){
					removedir(fileTemp.getPath(),false);
				}else{
					fileTemp.delete();
				}
			}
		}
		if(!bool){
			if(file.delete()){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	private String wipespecial(String cachethreaddir){
		return cachethreaddir.replace("..","").replace("\n","").replace("\r","");
	}
	
	private Map advertisement(String range,DataParse dataParse)
	{
		Map advs=new HashMap();
		int timestamp = Common.time();
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		List<Map<String,String>> advertisements=dataBaseDao.executeQuery("SELECT advid,type,targets,parameters,code FROM "+tablepre+"advertisements WHERE available=1 AND starttime<='"+timestamp+"' and (endtime ='0' or endtime >='"+timestamp+"') ORDER BY displayorder");
		if(advertisements!=null&&advertisements.size()>0)
		{
			Map<String,String> itemsMap=new HashMap<String, String>();
			Map<String,Map<String,String>> typesMap=new HashMap<String, Map<String,String>>();
			for (Map<String, String> adv : advertisements) {
				String type=adv.get("type");
				String advid=adv.get("advid");
				String code=adv.get("code").replaceAll("\r\n", " ");
				code=code.replace("\\", "\\\\");
				Map<String,String> parameters=new HashMap<String,String>();
				if("footerbanner".equals(type)||"thread".equals(type))
				{
					parameters=dataParse.characterParse(adv.get("parameters"), false);
					type+=(parameters.get("position")!=null&&parameters.get("position").matches("^(2|3)$")?parameters.get("position"):"1");
				}
				adv.put("targets", (adv.get("targets").equals("")||adv.get("targets").equals("all"))?(type.equals("text")?"forum":(type.length()>6&&type.substring(0,6).equals("thread")?"forum":"all")):adv.get("targets"));
				String[] targets=adv.get("targets").split("\t");
				if(targets!=null&&targets.length>0)
				{
					for (String target : targets) {
						target=("0".equals(target)?"index":("all".equals(target)||"index".equals(target)||"forumdisplay".equals(target)||"viewthread".equals(target)||"register".equals(target)||"redirect".equals(target)||"archiver".equals(target)?target:("forum".equals(target)?"forum_all":"forum_"+target)));
						if((("forumdisplay".equals(range)&&!("thread".equals(adv.get("type"))||"interthread".equals(adv.get("type"))))||"viewthread".equals(range))&&(target.length()>6&&target.substring(0,6).equals("forum_")))
						{
							if("thread".equals(adv.get("type")))
							{
								String displayorder=parameters.get("displayorder");
								String []displayorders=displayorder!=null&&!displayorder.trim().equals("")?displayorder.split("\t"):new String[]{"0"};
								for (String postcount : displayorders) {
									postcount=postcount.trim();
									Map<String,String> targetMap=typesMap.get(type+"_"+postcount);
									if(targetMap==null)
									{
										targetMap=new HashMap<String, String>();
									}
									targetMap.put(target, targetMap.get(target)!=null?targetMap.get(target)+","+advid:advid);
									typesMap.put(type+"_"+postcount, targetMap);
								}
							}
							else{
								Map<String,String> targetMap=typesMap.get(type);
								if(targetMap==null)
								{
									targetMap=new HashMap<String, String>();
								}
								targetMap.put(target, targetMap.get(target)!=null?targetMap.get(target)+","+advid:advid);
								typesMap.put(type, targetMap);
							}
							itemsMap.put(advid, code);
						}
						else if("all".equals(range)&&("all".equals(target)||"redirect".equals(target)))
						{
							Map targetMap=(Map)advs.get(target);
							if(targetMap==null)
							{
								targetMap=new HashMap();
							}
							Map<String,Map<String,String>> typeMap=(Map<String,Map<String,String>>)targetMap.get("type");
							Map<String,String> itemMap=(Map<String,String>)targetMap.get("items");
							if(typeMap==null)
							{
								typeMap= new HashMap<String,Map<String,String>>();
								itemMap= new HashMap<String,String>();
							}
							Map<String,String> typeitems=typeMap.get(type);
							if(typeitems==null)
							{
								typeitems=new HashMap<String, String>();
							}
							typeitems.put("all", typeitems.get("all")!=null?typeitems.get("all")+","+advid:advid);
							typeMap.put(type, typeitems);
							itemMap.put(advid, code);
							if(typeMap.size()>0&&itemMap.size()>0)
							{
								targetMap.put("type", typeMap);
								targetMap.put("items", itemMap);
							}
							if(targetMap.size()>0)
							{
								advs.put(target, targetMap);
							}
							typeMap=null;
						}else if("index".equals(range)&&"intercat".equals(type))
						{
							parameters=dataParse.characterParse(adv.get("parameters"), false);
							String position=parameters.get("position");
							if(position==null||position.equals(""))
							{
								position="0";
							}
							String[] positions=position.trim().split(",");
							Map<String,String> positionMap=(Map<String,String>)typesMap.get(type);
							if(positionMap==null)
							{
								positionMap=new HashMap<String,String>();
							}
							for (String obj : positions) {
								positionMap.put(obj.trim(), positionMap.get(obj.trim())!=null?positionMap.get(obj.trim())+","+advid:advid);
								itemsMap.put(advid, code);
							}
							typesMap.put(type, positionMap);
						}
						else if(target.equals(range)||("index".equals(range)&&"forum_all".equals(target)))
						{
							Map<String,String> advtypeMap=(Map<String,String>)typesMap.get(type);
							if(advtypeMap==null)
							{
								advtypeMap=new HashMap<String,String>();
							}
							advtypeMap.put("0",advtypeMap.get("0")!=null?advtypeMap.get("0")+","+advid:advid);
							itemsMap.put(advid,code);
							typesMap.put(type, advtypeMap);
						}
					}
				}
				if(itemsMap.size()>0&&typesMap.size()>0)
				{
					advs.put("items", itemsMap);
					advs.put("type", typesMap);
				}
			}
			itemsMap=null;
			typesMap=null;
		}
		advertisements=null;
		return advs;
	}
	
%>

<%	
	int timestamp=(Integer)request.getAttribute("timestamp");
	Connection connection = (Connection)request.getAttribute("connection");
	cronsDao.execute(connection,"UPDATE "+tablepre+"advertisements SET available='0' WHERE endtime>'0' AND endtime<='"+ timestamp + "'");
	
	DataParse dataParse = ((DataParse)BeanFactory.getBean("dataParse"));
	Map globaladvs = advertisement("all",dataParse);
	Map<String,String> settingMap = (Map<String,String>)application.getAttribute("settings");
	settingMap.put("globaladvs", globaladvs.get("all")!=null?dataParse.combinationChar((Map) globaladvs.get("all")):"");
	settingMap.put("redirectadvs", globaladvs.get("redirect")!=null?dataParse.combinationChar((Map)globaladvs.get("redirect")):"");
	
	String[] cacheArray = {"archiver", "register", "index", "forumdisplay", "viewthread" };
	Cache.updateCache(cacheArray);
	cronsDao.execute(connection,"TRUNCATE "+tablepre+"searchindex");
	cronsDao.execute(connection,"DELETE FROM "+tablepre+"threadsmod WHERE dateline<"+(timestamp-31536000));
	cronsDao.execute(connection,"DELETE FROM "+tablepre+"subscriptions WHERE lastpost<"+(timestamp-7776000));
	cronsDao.execute(connection,"DELETE FROM "+tablepre+"forumrecommend WHERE expiration<"+timestamp);
	cronsDao.execute(connection,"UPDATE "+tablepre+"trades SET closed='1' WHERE expiration<>0 AND expiration<"+timestamp);
	cronsDao.execute(connection,"DELETE FROM "+tablepre+"tradelog WHERE status=0 AND lastupdate<'"+(timestamp - 5 * 86400)+"'");
	List<Map<String,String>> tempList2 = cronsDao.executeQuery(connection,"SELECT COUNT(*) count FROM "+tablepre+"forums WHERE status=1 AND threadcaches>0");
	if(tempList2!=null&&tempList2.size()>0){
		Map<String,String> tempMap = tempList2.get(0);
		if(tempMap!=null){
			int cachethreadon = Integer.valueOf(tempMap.get("count"));
			if(cachethreadon>0){
				List<Map<String,String>> tempList4 = cronsDao.executeQuery(connection,"SELECT value FROM "+tablepre+"settings WHERE variable='cachethreaddir'");
				if(tempList4!=null&&tempList4.size()>0){
					Map<String,String> tempMap2 = tempList4.get(0);
					if(tempMap2!=null){
						String tempValue = tempMap2.get("value");
						if(tempValue!=null&&!tempValue.equals("")){
							removedir(JspRunConfig.realPath+tempValue,true);
						}
					}
				}
			}
		}
	}
	
	List<Map<String,String>> tempList3 = cronsDao.executeQuery(connection,"SELECT value FROM "+tablepre+"settings WHERE variable='regstatus'");
	if(tempList3!=null&&tempList3.size()>0){
		Map<String,String> tempMap = tempList3.get(0);
		if(tempMap!=null){
			Integer tempValue = Integer.valueOf(tempMap.get("value"));
			if(tempValue>1){
				cronsDao.execute(connection,"UPDATE "+tablepre+"invites SET status='4' WHERE expiration<"+timestamp+" AND status IN ('1', '3')");
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

