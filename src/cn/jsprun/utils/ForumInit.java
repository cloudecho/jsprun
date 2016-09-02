package cn.jsprun.utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.servlet.ServletContext;
import cn.jsprun.dao.DataBaseDao;
public final class ForumInit{
	private static DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
	private static DataParse dataParse= (DataParse)BeanFactory.getBean("dataParse");
	public static Map<String, String> settings=null;
	public synchronized static void initServletContext(ServletContext context){
		settings = queryForumSetInfo();
		Map<String,Object> msgforward = dataParse.characterParse(settings.get("msgforward"), true);
		context.setAttribute("msgforward", msgforward);
    	context.setAttribute("settings", settings);
	}
	public static void setSettings(ServletContext context,Map<String, String> value){
		if(value==null){
			settings = queryForumSetInfo();
		}else{
			settings=value;
		}
		context.setAttribute("settings", settings);
	}
	@SuppressWarnings("unchecked")
	public static Map<String, String> queryForumSetInfo(){	
		List<Map<String,String>> dataList=dataBaseDao.executeQuery("SELECT * FROM jrun_settings");
		if(dataList==null){
			return null;
		}
		Map<String, String> datas = new HashMap<String, String>();
		for (Map<String, String> data : dataList) {
			datas.put(data.get("variable"), data.get("value"));
		}
		datas.put("extcredits_bak",datas.get("extcredits"));
		Map<Integer,Map> extcredits = dataParse.characterParse(datas.get("extcredits"), false);
		if(extcredits!=null&&extcredits.size()>0){
			int creditstrans=Integer.valueOf(datas.get("creditstrans"));
			Map<Integer,Map> exchcredits=new HashMap<Integer,Map>();
			Set<Integer> extcreditids=extcredits.keySet();
			boolean allowexchangein=false;
			boolean allowexchangeout=false;
			for(Integer extcreditid:extcreditids){
				Map extcredit=extcredits.get(extcreditid);
				String avalable = extcredit.get("available")==null?"":extcredit.get("available").toString();
				if("1".equals(avalable)){
					extcredit.remove("available");
					exchcredits.put(extcreditid, extcredit);
					Object obj=extcredit.get("ratio");
					if(obj==null){
						obj=0;
					}
					double ratio=Double.valueOf(obj.toString());
					if(ratio>0){
						if("1".equals(extcredit.get("allowexchangein"))){
							allowexchangein=true;
						}
						if("1".equals(extcredit.get("allowexchangeout"))){
							allowexchangeout=true;
						}
					}
				}
			}
			datas.put("exchangestatus", (allowexchangein&&allowexchangeout?"1":"0"));
			datas.put("transferstatus", (exchcredits.get(creditstrans)!=null?"1":"0"));
			datas.put("extcredits",dataParse.combinationChar(exchcredits));
		}
		extcredits=null;
		int jsmenustatus=Integer.valueOf(datas.get("jsmenustatus"));
		datas.put("jsmenu_1",String.valueOf(jsmenustatus&1));
		datas.put("jsmenu_2",String.valueOf(jsmenustatus&2));
		datas.put("jsmenu_3",String.valueOf(jsmenustatus&4));
		datas.put("jsmenu_4",String.valueOf(jsmenustatus&8));
		datas.put("gtimeformat", datas.get("timeformat").equals("1")?"hh:mm a":"HH:mm");
		datas.put("onlinehold", String.valueOf(Integer.valueOf(datas.get("onlinehold"))*60));
		datas.put("version", JspRunConfig.VERSION);
		datas.put("totalmembers",dataBaseDao.executeQuery("SELECT COUNT(*) count FROM jrun_members").get(0).get("count"));
		int forumcount=Integer.valueOf(dataBaseDao.executeQuery("SELECT COUNT(*) count FROM jrun_forums WHERE status=1 AND threadcaches>0").get(0).get("count"));
		datas.put("cachethreadon", forumcount>0?"1":"0");
		List<Map<String,String>> lastMember=dataBaseDao.executeQuery("SELECT username FROM jrun_members ORDER BY uid DESC LIMIT 1");
		String username=lastMember!=null&&lastMember.size()>0?lastMember.get(0).get("username").replace("\\", "\\\\"):"";
		lastMember=null;
		datas.put("lastmember", username);
		List<Map<String,String>> crons=dataBaseDao.executeQuery("SELECT nextrun FROM jrun_crons WHERE available=1 AND nextrun>'0' ORDER BY nextrun LIMIT 1");
		datas.put("cronnextrun", crons!=null&&crons.size()>0?crons.get(0).get("nextrun"):"0");
		Map<String,String> google=dataParse.characterParse(datas.get("google"), false);
		datas.put("google_status",google.get("status"));
		datas.put("google_searchbox",google.get("searchbox"));
		google=null;
		datas.remove("google");
		Map<String,String> baidu=dataParse.characterParse(datas.get("baidu"), false);
		datas.put("baidu_status",baidu.get("status"));
		datas.put("baidu_searchbox",baidu.get("searchbox"));
		baidu=null;
		datas.remove("baidu");
		List<Map<String,String>> styleList=dataBaseDao.executeQuery("SELECT styleid, name FROM jrun_styles WHERE available='1'");
		if(styleList!=null&&styleList.size()>0){
			Map<Integer,String> styles=new HashMap<Integer, String>();
			for (Map<String, String> style : styleList) {
				styles.put(Integer.valueOf(style.get("styleid")), style.get("name"));
			}
			datas.put("forumStyles", styles!=null?dataParse.combinationChar(styles):"");
		}
		styleList=null;
		Map globaladvs = Common.advertisement("all");
		datas.put("globaladvs", globaladvs.get("all")!=null?dataParse.combinationChar((Map) globaladvs.get("all")):"");
		datas.put("redirectadvs", globaladvs.get("redirect")!=null?dataParse.combinationChar((Map)globaladvs.get("redirect")):"");
		globaladvs=null;
		List<Map<String,String>> plugins=dataBaseDao.executeQuery("SELECT available, name, identifier, directory, datatables, modules FROM jrun_plugins where available='1'");
		if(plugins!=null&&plugins.size()>0){
			Map<String,Map<String,Map<String,String>>> pluginlinks = new HashMap<String,Map<String,Map<String,String>>>();
			Map<Integer,Map<String,String>> links = new TreeMap<Integer,Map<String,String>>();
			Map<Integer,Map<String,String>> includes = new TreeMap<Integer,Map<String,String>>();
			Map<Integer,Map<String,String>> jsmenus = new TreeMap<Integer,Map<String,String>>();
			for (Map<String, String> plugin : plugins) {
				Map<Integer,Map>  modules=dataParse.characterParse(plugin.get("modules"), false);
				if(modules!=null&&modules.size()>0){
					Set<Entry<Integer,Map>> keys =modules.entrySet();
					for (Entry<Integer,Map> temp : keys) {
						Map module=temp.getValue();
						int type= Common.toDigit((String)module.get("type"));
						String identifier=plugin.get("identifier");
						if(type==1){
							Map<String,String> link=new HashMap<String, String>();
							link.put("adminid", String.valueOf(module.get("adminid")));
							link.put("url","<a href=\""+module.get("url")+"\">"+module.get("menu")+"</a>");
							links.put(links.size(), link);
						}else if(type==2){
							String name=String.valueOf(module.get("name"));
							String adminid=String.valueOf(module.get("adminid"));
							Map<String,String> link=new HashMap<String, String>();
							link.put("adminid", adminid);
							link.put("url","<a href=\"plugin.jsp?identifier="+identifier+"&module="+name+"\">"+name+"</a>");
							links.put(links.size(), link);
							Map<String,Map<String,String>> pluginlink=pluginlinks.get(identifier);
							if(pluginlink==null){
								pluginlink=new HashMap<String, Map<String,String>>();
								pluginlinks.put(identifier, pluginlink);
							}
							Map<String,String> templink=new HashMap<String, String>();
							templink.put("adminid", adminid);
							templink.put("directory", String.valueOf(plugin.get("directory")));
							pluginlink.put(name, templink);
						}else if(type==4){
							Map<String,String> include=new HashMap<String, String>();
							include.put("adminid", String.valueOf(module.get("adminid")));
							include.put("script",plugin.get("directory")+module.get("name"));
							includes.put(includes.size(), include);
						}else if(type==5){
							Map<String,String> jsmenu=new HashMap<String, String>();
							jsmenu.put("adminid", String.valueOf(module.get("adminid")));
							jsmenu.put("url","<a href=\""+module.get("url")+"\">"+module.get("menu")+"</a>");
							jsmenus.put(jsmenus.size(), jsmenu);
						}else if(type==6){
							String name=String.valueOf(module.get("name"));
							String adminid=String.valueOf(module.get("adminid"));
							Map<String,String> jsmenu=new HashMap<String, String>();
							jsmenu.put("adminid", String.valueOf(module.get("adminid")));
							jsmenu.put("url","<a href=\"plugin.jsp?identifier="+identifier+"&module="+name+"\">"+name+"</a>");
							jsmenus.put(jsmenus.size(), jsmenu);
							Map<String,Map<String,String>> pluginlink=pluginlinks.get(identifier);
							if(pluginlink==null){
								pluginlink=new HashMap<String, Map<String,String>>();
								pluginlinks.put(identifier, pluginlink);
							}
							Map<String,String> templink=new HashMap<String, String>();
							templink.put("adminid", adminid);
							templink.put("directory", String.valueOf(plugin.get("directory")));
							pluginlink.put(name, templink);
						}
					}
				}
			}
			Map<String,Map> pluginstemp=new HashMap<String,Map>();
			if(links.size()>0){
				pluginstemp.put("links", links);
			}
			if(includes.size()>0){
				pluginstemp.put("includes", includes);
			}
			if(jsmenus.size()>0){
				pluginstemp.put("jsmenus", jsmenus);
			}
			datas.put("plugins",  pluginstemp.size()>0?dataParse.combinationChar(pluginstemp):"");
			datas.put("pluginlinks", pluginlinks.size()>0?dataParse.combinationChar(pluginlinks):"");
		}
		List<Map<String,String>> pluginhooks=dataBaseDao.executeQuery("SELECT ph.title, ph.code, p.identifier FROM jrun_plugins p LEFT JOIN jrun_pluginhooks ph ON ph.pluginid=p.pluginid AND ph.available='1' WHERE p.available='1' ORDER BY p.identifier");
		if(pluginhooks!=null&&pluginhooks.size()>0){
			Map<String,String> hooks = new HashMap<String,String>();
			for (Map<String, String> pluginhook : pluginhooks) {
				String title=pluginhook.get("title");
				String code=pluginhook.get("code");
				if(title!=null&&code!=null){
					hooks.put(pluginhook.get("identifier")+"_"+pluginhook.get("title"), pluginhook.get("code"));
				}
			}
			datas.put("hooks", hooks.size()>0?dataParse.combinationChar(hooks):"");
		}
		List<Map<String,String>> forumList=dataBaseDao.executeQuery("SELECT f.fid, f.type, f.name, f.fup, ff.viewperm FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid WHERE f.status=1 ORDER BY f.type, f.displayorder");
		Common.setForums(forumList);
		datas.put("forums", dataParse.combinationChar(forumList));
		int timestamp = Common.time();
		dataBaseDao.runQuery("DELETE FROM jrun_banned WHERE expiration<'"+timestamp+"'",true);
		List<Map<String,String>> iplist = dataBaseDao.executeQuery("select ip1,ip2,ip3,ip4,expiration from jrun_banned order by expiration");
		StringBuffer buffer = new StringBuffer();
		boolean flag = true;
		for(Map<String,String> ips:iplist){
			String ip11 = ips.get("ip1");
			String ip12 = ips.get("ip2");
			String ip13 = ips.get("ip3");
			String ip14 = ips.get("ip4");
			ip11 = ip11.equals("-1")?"\\d+":ip11;
			ip12 = ip12.equals("-1")?"\\d+":ip12;
			ip13 = ip13.equals("-1")?"\\d+":ip13;
			ip14 = ip14.equals("-1")?"\\d+":ip14;
			buffer.append("|"+ip11+"\\."+ip12+"\\."+ip13+"\\."+ip14);
			if(flag){
				datas.put("ipban_expiration", ips.get("expiration"));
			}
			flag = false;
		}
		datas.put("ipbanned", buffer.length()>0?buffer.substring(1):"");
		return datas;
	}	
}