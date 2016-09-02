package cn.jsprun.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import cn.jsprun.dao.DataBaseDao;
public final class Cache {
	private static DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
	private static DataParse dataParse= (DataParse)BeanFactory.getBean("dataParse");
	private static String host;
	private static Map<String, String> cacheScript = new HashMap<String, String>();
	static {
		cacheScript.put("admingroup", "admingroups");
		cacheScript.put("usergroup", "usergroups");
		cacheScript.put("archiver", "advs_archiver");
		cacheScript.put("register", "advs_register");
		cacheScript.put("forums", "forums");
		cacheScript.put("plugins", "plugins");
		cacheScript.put("style", "styles");
		cacheScript.put("faqs", "faqs");
		cacheScript.put("icons", "icons");
		cacheScript.put("secqaa", "secqaa");
		cacheScript.put("medals", "medals");
		cacheScript.put("ranks", "ranks");
		cacheScript.put("censor", "censor");
		cacheScript.put("google", "google");
		cacheScript.put("baidu", "baidu");
		cacheScript.put("index","announcements,forumlinks,advs_index,onlinelist,tags_index,birthdays_index");
		cacheScript.put("forumdisplay","announcements,announcements_forum,globalstick,onlinelist,advs_forumdisplay");
		cacheScript.put("viewthread","announcements,ranks,bbcodes,advs_viewthread,tags_viewthread,smilies_parse");
		cacheScript.put("post","bbcodes_display,smilies_display,smilies");
		cacheScript.put("profilefields","profilefields");
		cacheScript.put("threadtypes", "threadtypes");
		cacheScript.put("smilies_var", "smilies_var");
		cacheScript.put("spacesettings", "spacesettings");
	}
	private Cache(){
	}
	public static String updateCache(String... cacheNames){
		String message=null;
		try{
			Map<String,String> settings=ForumInit.settings;
			if (cacheNames != null&&cacheNames.length>0) {
				for (String cacheName : cacheNames) {
					String cname = cacheScript.get(cacheName);
					if (cname != null) {
						setCache(cname, cacheName, settings);
					}
				}
			}else{
				Set<String> keys = cacheScript.keySet();
				for (String key : keys) {
					if(!key.equals("google")||!key.equals("baidu")){
						String cname = cacheScript.get(key);
						setCache(cname, key,settings);
					}
				}
			}
		}catch (Exception e) {
			message="Message: ";
			if(e.getMessage()==null){
				message+=e;
			}else{
				message+=e.getMessage();
			}
			System.out.println("更新缓存失败,错误消息：");
			e.printStackTrace();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	private static void setCache(String cname, String cachename,Map<String,String> settings)throws Exception {
		String prefix = "cache_";
		boolean flag = false;
		String[] cnames = cname.split(",");
		for (String obj : cnames) {
			String cols=null, table=null, sql=null, conditions=null;
			if("forumlinks".equals(obj)||"onlinelist".equals(obj)){
				conditions="ORDER BY displayorder";
			}else if("forums".equals(obj)){
				Map<String, Map<String,String>> datas = new HashMap<String,  Map<String,String>>();
				List<Map<String,String>> forumList=dataBaseDao.executeQuery("SELECT f.fid, f.type, f.name, f.fup, ff.viewperm FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid WHERE f.status=1 AND f.type<>'group' ORDER BY f.type, f.displayorder");
				if(forumList!=null&&forumList.size()>0){
					Map<String,Map<String,String>> forums=new HashMap<String,Map<String,String>>(); 
					Map<String,Map<String,String>> subs=new HashMap<String,Map<String,String>>();
					for(Map<String,String> forum:forumList){
						forum.put("name", Common.strip_tags(forum.get("name")));
						String type=forum.get("type");
						if(type.equals("forum")){
							forums.put(forum.get("fid"), forum);
						}else if(type.equals("sub")){
							Map<String,String> upforum=forums.get(forum.get("fup"));
							if(upforum!=null){
								if(upforum.get("hasSub")==null){
									upforum.put("hasSub", "true");
								}								
							}
							subs.put(forum.get("fid"), forum);
						}
					}
					if(forums.size()>0){
						Set<String> forumids=forums.keySet();
						for(String forumid:forumids){
							Map<String,String> forum=forums.get(forumid);
							boolean hasSub="true".equals(forum.get("hasSub"));
							forum.remove("hasSub");
							datas.put(forumid, forum);
							if(hasSub){
								Set<String> subids=subs.keySet();
								for(String subid:subids){
									Map<String,String> sub=subs.get(subid);		
									if(sub.get("fup").equals(forumid)){
										datas.put(subid, sub);
									}
								}
							}
						}
					}
				}
				Map<String,String> data=new HashMap<String,String>();
				data.put("forums", dataParse.combinationChar(datas));
				writeToCacheFile(prefix + cachename,arrayeval(cachename, data), "", flag);
				break;
			}else if("plugins".equals(obj)){
				List<Map<String,String>> plugins=dataBaseDao.executeQuery("SELECT pluginid, available, adminid, name, identifier, datatables, directory, copyright, modules FROM jrun_plugins");
				if(plugins!=null&&plugins.size()>0){
					for(Map<String,String> plugin:plugins){
						List<Map<String,String>> queryvars=dataBaseDao.executeQuery("SELECT variable, value FROM jrun_pluginvars WHERE pluginid='"+plugin.get("pluginid")+"'");
						if(queryvars!=null&&queryvars.size()>0){
							Map<String,String> vars=new HashMap<String,String>();
							for(Map<String,String> var:queryvars){
								vars.put(var.get("variable"), var.get("value"));
							}
							plugin.put("vars", dataParse.combinationChar(vars));
						}
						writeToCacheFile("plugin_" + plugin.get("identifier"),arrayeval(plugin.get("identifier"), plugin), "", flag);
					}
				}
				break;
			}else if("smilies_parse".equals(obj)){
				table="smilies";
				sql = "select s.id,s.typeid,s.code,s.url,i.directory from jrun_smilies s left join jrun_imagetypes  i on s.typeid=i.typeid where s.type='smiley' order by LENGTH(s.code) DESC";
			}else if(obj.startsWith("tags_")){
				table="tags";
				int taglimit=Common.toDigit("viewthread".equals(obj.substring(5))?settings.get("viewthreadtags"):settings.get("hottags"));
				cols="tagname, total";
				conditions="WHERE closed=0 ORDER BY total DESC LIMIT "+taglimit;
			}else if("announcements".equals(obj)){
				int timestamp = Common.time();
				conditions="WHERE starttime<='"+timestamp+"' AND (endtime>='"+timestamp+"' OR endtime='0') ORDER BY displayorder, starttime DESC, id DESC";
			}else if("google".equals(obj)){
				table="settings";
				conditions="WHERE variable = 'google'";
			}else if("baidu".equals(obj)){
				table="settings";
				conditions="WHERE variable = 'baidu'";
			}else if("birthdays_index".equals(obj)){
				table="members";
				String timeoffset=settings.get("timeoffset");
				conditions="WHERE RIGHT(bday, 5)='"+Common.gmdate("MM-dd", Common.time(),timeoffset)+"' ORDER BY bday LIMIT "+Common.toDigit(settings.get("maxbdays"));
			}else if ("styles".equals(obj)) {
				table="stylevars";
				sql="SELECT sv.* FROM jrun_stylevars sv LEFT JOIN jrun_styles s ON s.styleid = sv.styleid WHERE s.available=1";
			}else if ("icons".equals(obj)) {
				table="smilies";
				conditions="WHERE type='icon' ORDER BY displayorder";
			}else if ("secqaa".equals(obj)) {
				int secqaanum=Integer.valueOf(dataBaseDao.executeQuery("SELECT COUNT(*) count FROM jrun_itempool").get(0).get("count"));
				int start_limit = secqaanum <= 10 ? 0 : Common.rand(secqaanum - 10);
				List<Map<String,String>> secqaas=dataBaseDao.executeQuery("SELECT question, answer FROM jrun_itempool LIMIT "+start_limit+", 10");
				Map<String,String> datas=new HashMap<String,String>();
				int size=secqaas.size();
				if(size>0){
					for (int i=0;i<size;i++) {
						Map<String,String> secqaa=secqaas.get(i);
						datas.put(String.valueOf(i), dataParse.combinationChar(secqaa));
					}
				}else{
					datas.put("0",null);
				}
				while((size=datas.size()) < 10) {
					datas.put(size+"", datas.get(Common.rand(size-1)+""));
				}
				writeToCacheFile(prefix + cachename,arrayeval(obj, datas), "", flag);
				break;
			}else if ("medals".equals(obj)) {
				table="medals";
				cols="medalid, name, image";
				conditions="WHERE available='1'";
			}else if ("censor".equals(obj)) {
				table="words";
			}else if ("faqs".equals(obj)) {
				conditions="WHERE identifier!='' AND keyword!=''";
			}else if("announcements_forum".equals(obj)){
				int timestamp = Common.time();
				table="announcements";
				conditions="WHERE type!=2 AND groups = '' AND starttime<='"+timestamp+"' ORDER BY displayorder, starttime DESC, id DESC LIMIT 1";
			}else if("globalstick".equals(obj)){
				table="forums";
				conditions ="WHERE status=1 AND type IN ('forum', 'sub') ORDER BY type";
			}else if("bbcodes".equals(obj)){
				table="bbcodes";
				conditions="WHERE available='1' AND icon!=''";
			}else if("ranks".equals(obj)){
				table="ranks";
				cols="ranktitle, postshigher, stars, color";
				conditions="ORDER BY postshigher DESC";
			}
			else if("bbcodes_display".equals(obj)){
				table="bbcodes";
				conditions="WHERE available='1' AND icon!=''";
			}else if("smilies_display".equals(obj)){
				table="imagetypes";
				conditions="WHERE type='smiley' ORDER BY displayorder";
			}else if("smilies".equals(obj)){
				table="smilies";
				sql="SELECT s.* FROM jrun_smilies s LEFT JOIN jrun_imagetypes t ON t.typeid=s.typeid WHERE s.type='smiley' AND s.code<>'' AND t.typeid IS NOT NULL ORDER BY LENGTH(s.code) DESC";
			}else if("profilefields".equals(obj)){
				table="profilefields";
				cols="fieldid, invisible, title, description, required, unchangeable, selective, choices,size";
				conditions="WHERE available='1' ORDER BY displayorder";
			}else if(obj.length()>5&&obj.substring(0,5).equals("advs_")){
				Map<String,String> datas=new HashMap<String,String>();
				Map advs = Common.advertisement(obj.substring(5));
				datas.put(obj.substring(0,4), advs!=null&&advs.size()>0?dataParse.combinationChar(advs):"");
				writeToCacheFile(prefix + cachename,arrayeval(obj.substring(0,4), datas), "", flag);
				flag = true;
				continue;
			}else if("smilies_var".equals(cachename)){
				String smrows = settings.get("smrows");
				String smcols = settings.get("smcols");
				int spp = Common.toDigit(smcols)*Common.toDigit(smrows);
				List<Map<String,String>>imagetypes = dataBaseDao.executeQuery("select typeid,name,directory from jrun_imagetypes order by displayorder");
				if(imagetypes!=null && imagetypes.size()>0){
					StringBuffer return_type = new StringBuffer("var smthumb = '20';var smilies_type = {};");
					StringBuffer return_datakey = new StringBuffer("var smilies_array = new Array();");
					String smilieDir=JspRunConfig.realPath+"images/smilies/";
					for(Map<String,String> stypes:imagetypes){
						List<Map<String,String>> smileslist = dataBaseDao.executeQuery("SELECT id, code, url FROM jrun_smilies WHERE type='smiley' AND code<>'' AND typeid='"+stypes.get("typeid")+"' ORDER BY displayorder");
						if(smileslist!=null&&smileslist.size()>0){
							int j=1;int i=0;
							return_type.append("smilies_type['"+stypes.get("typeid")+"'] = ['"+stypes.get("name").replace("'", "\\'")+"','"+stypes.get("directory").replace("'", "\\'")+"'];");
							return_datakey.append("smilies_array["+stypes.get("typeid")+"] = new Array();");
							return_datakey.append("smilies_array["+stypes.get("typeid")+"]["+j+"]=[");
							for(Map<String,String>smiles:smileslist){
								if(i >=spp) {
									return_datakey.deleteCharAt(return_datakey.length()-1);
									return_datakey.append("];");
									j++;
									return_datakey.append("smilies_array["+stypes.get("typeid")+"]["+j+"]=[");
									i = 0;
								}
								i++;
								String smileycode = smiles.get("code").replace("'", "\\'");
								String url = smiles.get("url").replace("'","\\'");
								int windth;
								File file = new File(smilieDir+stypes.get("directory")+"/"+url);
								if(file.exists()){
									windth = ImageIO.read(file).getWidth();
								}else{
									windth = 20;
								}
								return_datakey.append("['"+smiles.get("id")+"','"+smileycode+"','"+url+"','20','20','"+windth+"'],");
							}
							return_datakey.deleteCharAt(return_datakey.length()-1);
							return_datakey.append("];");
						}
					}
					writeToJsCacheFile("smilies", return_type.toString()+return_datakey.toString(), "_var");
				}
				break;
			}else if("spacesettings".equals(obj)){
				String initcredits = settings.get("initcredits");
				int lowercredits = Common.intval(initcredits);
				List<Map<String,String>> groupinfo = dataBaseDao.executeQuery("SELECT groupid FROM jrun_usergroups WHERE creditshigher<="+lowercredits+" AND creditslower>"+lowercredits+" ORDER BY creditshigher LIMIT 1");
				String groupid = "7";
				if(groupinfo!=null&&groupinfo.size()>0){
					groupid = groupinfo.get(0).get("groupid");
				}
				StringBuffer fids = new StringBuffer("0");
				List<Map<String,String>> forums = dataBaseDao.executeQuery("SELECT ff.fid, ff.viewperm, ff.formulaperm FROM jrun_forumfields ff,jrun_forums f WHERE f.fid=ff.fid AND f.status=1 AND ff.password=''");
				for(Map<String,String> forum : forums) {
					if((Common.empty(forum.get("viewperm")) || Common.in_array(forum.get("viewperm").split("\t"), groupid))) {
						fids.append(","+forum.get("fid"));
					}
				}
				Map<String,String> data = new HashMap<String,String>();
				data.put("infids", fids.toString());
				writeToCacheFile(prefix+cachename,arrayeval(cname, data), "", flag);
				break;
			}else if("usergroups".equals(obj)){
				sql = "SELECT * FROM jrun_usergroups u LEFT JOIN jrun_admingroups a ON u.groupid=a.admingid";
			}
			getDataList(sql,table!=null?table:obj,cols, conditions, cachename, obj,prefix, flag);
			flag = true;
		}
		if ("threadtypes".equals(cachename)) {
			Map<String, String> datas = new HashMap<String, String>();
			List<Map<String,String>> dataList=dataBaseDao.executeQuery("SELECT tv.displayorder, t.typeid, tt.optionid, tt.title, tt.type, tt.rules, tt.identifier, tt.description, tv.required, tv.unchangeable, tv.search FROM jrun_threadtypes t LEFT JOIN jrun_typevars tv ON t.typeid=tv.typeid	LEFT JOIN jrun_typeoptions tt ON tv.optionid=tt.optionid WHERE t.special='1' AND tv.available='1' AND tt.optionid is not null ORDER BY tv.displayorder");
			Map<Integer,Map<Integer,Map<String,String>>> typelists=new HashMap<Integer, Map<Integer,Map<String,String>>>();
			Map<Integer,String> templatedata=new HashMap<Integer,String>();
			if(dataList!=null&&dataList.size()>0){
				Map<Integer,Map<String,String>> typelist=null;
				for (Map<String, String> data : dataList) {
					Map<String,String> rules=dataParse.characterParse(data.get("rules"),false);
					Integer typeid=Integer.valueOf(data.get("typeid"));
					String type=data.get("type");
					typelist=typelists.get(typeid);
					if(typelist==null){
						typelist=new HashMap<Integer, Map<String,String>>();
					}
					if(rules!=null&&rules.size()>0){
						if("select".equals(type)||"checkbox".equals(type)||"radio".equals(type)){
							String[] choices=rules.get("choices").split("(\r\n|\n|\r)");
							StringBuffer temp=new StringBuffer();
							for (String choice:choices) {
								String[] items=choice.split("=");
								if(items.length==2){
									temp.append("\\n"+items[0].trim()+"="+items[1].trim());
								}
							}
							data.put("choices",temp.length()>0?temp.substring(2):"");
						}
						else if("text".equals(type)||"textarea".equals(type)){
							String maxLength=rules.get("maxlength");
							data.put("maxlength", Common.empty(maxLength)? null : maxLength);
						}
						else if("image".equals(type)){
							String maxWidth=rules.get("maxlength");
							String maxHeight=rules.get("maxlength");
							data.put("maxwidth",  Common.empty(maxWidth)? null : maxWidth);
							data.put("maxheight",  Common.empty(maxHeight)? null : maxHeight);
						}
						else if("number".equals(type)){
							String maxNum=rules.get("maxlength");
							String minNum=rules.get("maxlength");
							data.put("maxnum",  Common.empty(maxNum)? null : maxNum);
							data.put("minnum",  Common.empty(minNum)? null : minNum);
						}
					}
					data.remove("rules");
					typelist.put(Integer.valueOf(data.get("optionid")),data);
					typelists.put(typeid, typelist);
				}
			}
			dataList=dataBaseDao.executeQuery("SELECT typeid, template FROM jrun_threadtypes WHERE special='1'");
			if(dataList!=null&&dataList.size()>0){
				for (Map<String, String> data : dataList) {
					templatedata.put(Integer.valueOf(data.get("typeid")), data.get("template"));
				}
			}
			dataList=null;
			Set<Entry<Integer,Map<Integer,Map<String,String>>>> typeids=typelists.entrySet();
			for (Entry<Integer,Map<Integer,Map<String,String>>> temp : typeids) {
				Integer typeid = temp.getKey();
				datas.put("dtype",dataParse.combinationChar(temp.getValue()));
				datas.put("dtypeTemplate",templatedata.get(typeid));
				writeToCacheFile(String.valueOf(typeid),arrayeval("threadtype", datas), "threadtype_", false);
			}
		}
	}
	@SuppressWarnings("unchecked")
	private static void getDataList(String sql, String table,String cols,String conditions, String cachename, String cname, String prefix,boolean append) throws Exception {
		Map<String, String> datas = new HashMap<String, String>();
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		dataList = dataBaseDao.executeQuery(sql!=null?sql:"SELECT "+(cols==null?"*":cols)+" FROM jrun_" + table +(conditions!=null?" "+ conditions:""));
		if ("admingroups".equals(cname)) {
			for (Map<String, String> data : dataList) {
				writeToCacheFile(cachename + "_" + data.get("admingid"),arrayeval(cname, data), "", append);
			}
		} else if ("usergroups".equals(cname)) {
			for (Map<String, String> data : dataList) {
				Set<String> keys = data.keySet();
				for (String key : keys) {
					if(data.get(key)==null){
						data.put(key, "0");
					}
				}
				writeToCacheFile(cachename + "_" + data.get("groupid"),arrayeval(cname, data), "", append);
			}
		} else if ("styles".equals(cname)) {
			List<Map<String, String>> styles = dataBaseDao.executeQuery("SELECT s.styleid,s.name,s.available,s.templateid, t.directory as tpldir FROM jrun_styles s LEFT JOIN jrun_templates t ON s.templateid=t.templateid WHERE s.available=1");
			List<Map<String,String>> maxavatarpixel = dataBaseDao.executeQuery("select value from jrun_settings where variable='maxavatarpixel'");
			List<Map<String,String>>customauthorinfo = dataBaseDao.executeQuery("select value from jrun_settings where variable='customauthorinfo'");
			int maxavatarpic = maxavatarpixel.size()>0?Integer.valueOf(maxavatarpixel.get(0).get("value")):0;
			Map<String,String> maxsigrows = dataBaseDao.executeQuery("select value from jrun_settings where variable='maxsigrows'").get(0);
			Map custommap = dataParse.characterParse(customauthorinfo.get(0).get("value"), false);
			customauthorinfo=null;
			int left = 0;
			if (custommap != null && custommap.get(0) != null) {
				Map customreMap = (Map) custommap.get(0);
				Iterator its = customreMap.keySet().iterator();
				while(its.hasNext()){
					Object key = its.next();
					Map dismap = (Map)customreMap.get(key);
					if(dismap.get("left")!=null){
						left++;
					}
				}
			}
			for (Map<String, String> style : styles) {
				Set<String> keys = style.keySet();
				for (String key : keys) {
					datas.put(key.toUpperCase(), style.get(key));
				}
				String styleid = style.get("styleid");
				for (Map<String, String> data : dataList) {
					if (styleid.equals(data.get("styleid"))) {
						datas.put(data.get("variable").toUpperCase(), data.get("substitute"));
					}
				}
				datas.put("BGCODE", setCssBackGround(datas, "BGCOLOR"));
				datas.put("CATBGCODE", setCssBackGround(datas, "CATCOLOR"));
				datas.put("HEADERBGCODE", setCssBackGround(datas, "HEADERCOLOR"));
				datas.put("HEADERMENUBGCODE", setCssBackGround(datas,"HEADERMENU"));
				datas.put("PORTALBOXBGCODE", setCssBackGround(datas,"PORTALBOXBGCODE"));
				String boardimg = datas.get("BOARDIMG");
				if (boardimg.indexOf(",") > -1) {
					String[] flash = boardimg.split(",");
					flash[0] = flash[0].trim();
					if(!flash[0].matches("^http:\\/\\/.*")){
						flash[0] = datas.get("IMGDIR") + "/" + flash[0];
					}
					datas.put("BOARDLOGO","<embed src=\""+ flash[0]+ "\" width=\""+ flash[1].trim()+ "\" height=\""+ flash[2].trim()+ "\" type=\"application/x-shockwave-flash\"></embed>");
				} else {
					if(!boardimg.matches("^http:\\/\\/.*")){
						boardimg=datas.get("IMGDIR") + "/" + boardimg;
					}
					datas.put("BOARDIMG",boardimg);
					datas.put("BOARDLOGO", "<img src=\"" + boardimg	+ "\" border=\"0\" />");
				}
				datas.put("BOLD", datas.get("NOBOLD") != null&& !datas.get("NOBOLD").equals("") ? "normal" : "bold");
				datas.put("POSTMINHEIGHT", (maxavatarpic>300?300:maxavatarpic+left*20)+"");
				datas.put("MAXSIGROWS", maxsigrows.get("value"));
				writeToCssCache(datas);
				writeToCacheFile(cachename + "_" + styleid, arrayeval(cname, datas), "",append);
				datas.clear();
			}
			maxavatarpixel=null;
			maxsigrows=null;
		} else if ("icons".equals(cname)){
			for (Map<String, String> data : dataList) {
				datas.put(data.get("id"), data.get("url"));
			}
			writeToCacheFile(cachename, arrayeval(cname, datas), prefix, append);
		} else if ("medals".equals(cname)){
			Map<Integer,Map<String, String>> map = new HashMap<Integer,Map<String, String>>();
			int size= dataList.size();
			for (Integer i = 0; i < size; i++) {
				map.put(i, dataList.get(i));
			}
			datas.put(cname, dataParse.combinationChar(map.size() > 0 ? map	: null));
			writeToCacheFile(cachename, arrayeval(cname, datas), prefix, append);
		} else if ("censor".equals(cname)){
			StringBuffer banned=new StringBuffer();
			StringBuffer mod=new StringBuffer();
			Map<String,Map<String, String>> filters = new HashMap<String,Map<String, String>>();
			Map<String, String> finds = new HashMap<String, String>();
			Map<String, String> replaces = new HashMap<String, String>();
			String comma1 = "";String comma2 = "";
			for (Map<String, String> data : dataList) {
				String id=data.get("id");
				String find=data.get("find");
				String replacement=data.get("replacement");
				find=find.replaceAll("\\{(\\d+)\\}", ".{0,$1}");
				if("{BANNED}".equals(replacement)){
					banned.append(comma1+find);
					comma1 = "|";
				}else if("{MOD}".equals(replacement)){
					mod.append(comma2+find);
					comma2 = "|";
				}else{
					finds.put(id,find);
					replaces.put(id,replacement);
				}
			}
			if(finds.size()>0){
				filters.put("find", finds);
				filters.put("replace",replaces);
			}
			datas.put("filter", dataParse.combinationChar(filters.size()>0?filters:null));
			datas.put("banned", banned.length()>0?"("+banned+")":"");
			datas.put("mod", mod.length()>0?"("+mod+")":"");
			finds=null;
			replaces=null;
			filters=null;
			mod=null;
			banned=null;
			writeToCacheFile(cachename, arrayeval(cname, datas), prefix, append);
		}else if ("faqs".equals(cname)){
			Map<String, Map<String, String>> faqsmap = new HashMap<String, Map<String, String>>();
			for (Map<String, String> data : dataList) {
				if(!"".equals(data.get("identifier"))&&!"".equals(data.get("keyword")))
				{
					Map<String,String> map=new HashMap<String,String>();
					map.put("id", data.get("id"));
					map.put("keyword", data.get("keyword"));
					faqsmap.put(data.get("identifier"),map);
				}
			}
			datas.put(cname, dataParse.combinationChar(faqsmap.size() > 0 ? faqsmap: null));
			faqsmap=null;
			writeToCacheFile(cachename, arrayeval(cname, datas), prefix, append);
		}else if ("index".equals(cachename)){
			Map<Integer,Map<String, String>> map = new HashMap<Integer,Map<String, String>>();
			if ("forumlinks".equals(cname)) {
				Map<String,String> settings=ForumInit.settings;
				int forumlinkstatus=Common.toDigit(settings.get("forumlinkstatus"));
				if(forumlinkstatus>0){
					Map<String, String> forumlink = null;
					StringBuffer tightlink_text =new StringBuffer();
					StringBuffer tightlink_logo = new StringBuffer();
					for (Map<String, String> flink : dataList) {
						int id = Integer.valueOf(flink.get("id"));
						String name = flink.get("name");
						String url = flink.get("url");
						forumlink = new HashMap<String, String>();
						if (!"".equals(flink.get("description"))) {
							forumlink.put("content", "<h5><a href='" + url+ "' target='_blank'>" + name+ "</a></h5><p>" + flink.get("description") + "</p>");
							if (!"".equals(flink.get("logo"))) {
								forumlink.put("type", "1");
								forumlink.put("logo", flink.get("logo"));
							} else {
								forumlink.put("type", "2");
							}
							map.put(id, forumlink);
						} else {
							if (!"".equals(flink.get("logo"))) {
								tightlink_logo.append("<a href='" + url+ "' target='_blank'><img src='" + flink.get("logo")+ "' border='0' title='" + name+ "' /></a> ");
							} else {
								tightlink_text.append("<a href='" + url+ "' target='_blank'>[" + name+ "]</a> ");
							}
						}
					}
					if (tightlink_logo.length()>0 || tightlink_text.length()>0) {
						forumlink = new HashMap<String, String>();
						if(tightlink_logo.length()>0){
							tightlink_logo.append("<br />");
						}
						forumlink.put("type", "3");
						forumlink.put("content", tightlink_logo.append(tightlink_text).toString());
						map.put(0, forumlink);
					}
					tightlink_text=null;
					tightlink_logo=null;
					forumlink=null;
				}
				datas.put(cname, dataParse.combinationChar(map.size() > 0 ? map	: null));
			}else if ("onlinelist".equals(cname)) {
				StringBuffer legend=new StringBuffer();
				for(Map<String, String> list : dataList) {
					datas.put(list.get("groupid"), list.get("url"));
					legend.append("<img src=\"images/common/"+list.get("url")+"\" alt=\"\" /> "+list.get("title")+" &nbsp; &nbsp; &nbsp; ");
					if(Integer.valueOf(list.get("groupid"))==7){
						datas.put("guest", list.get("title"));
					}
				}
				datas.put("legend", legend.toString());
				legend=null;
			}else if("birthdays_index".equals(cname)){
				StringBuffer todaysbdays=new StringBuffer();
				for (Map<String, String> bdaymember : dataList) {
					todaysbdays.append("<a href=\"space.jsp?uid="+bdaymember.get("uid")+"\" target=\"_blank\" title=\""+bdaymember.get("bday")+"\">"+bdaymember.get("username")+"</a>, ");
				}
				int length=todaysbdays.length();
				datas.put("todaysbdays",length>=2?todaysbdays.substring(0,length-2):"");
			}else if("announcements".equals(cname)){
				int size=dataList.size();
				for (int i = 1; i <= size; i++) {
					Map<String,String> data=dataList.get(i-1);
					if(!"1".equals(data.get("type"))){
						data.remove("message");
					}
					map.put(i, data);
				}
				if(map.size() > 0){
					datas.put(cname,dataParse.combinationChar(map));
				}
			}else if("tags_index".equals(cname)){
				cname="tags";
				Map<String,String> settings=ForumInit.settings;
				int tagstatus=Common.toDigit(settings.get("tagstatus"));
				if(tagstatus>0){
					int hottags=Common.toDigit(settings.get("hottags"));
					StringBuffer tags=new StringBuffer();
					if(hottags>0&&dataList!=null&&dataList.size()>0){
						for(Map<String,String> tag:dataList){
							tags.append(" <a href=\"tag.jsp?name="+Common.encode(tag.get("tagname"))+"\" target=\"_blank\">"+tag.get("tagname")+"<em>("+tag.get("total")+")</em></a>");
						}
					}
					if(tags.length()>0){
						datas.put(cname, tags.substring(1));
					}
				}
			}
			writeToCacheFile(cachename, arrayeval(cname, datas), prefix, append);
			map=null;
		}else if ("forumdisplay".equals(cachename)) {
			Map<Integer, Map<String, String>> map = new HashMap<Integer, Map<String, String>>();
			if ("announcements_forum".equals(cname)) {
				cname="announcement";
				if(dataList!=null&&dataList.size()>0){
					Map<String,String> data=dataList.get(0);
					Set<String> keys=data.keySet();
					if(!"1".equals(data.get("type"))){
						keys.remove("message");
					}
					for (String key : keys) {
						datas.put(key, data.get(key));
					}
				}
			}else if("announcements".equals(cname)){
				int size=dataList.size();
				for (int i = 1; i <= size; i++) {
					Map<String,String> data=dataList.get(i-1);
					data.remove("message");
					map.put(i, data);
				}
				datas.put(cname, dataParse.combinationChar(map.size() > 0 ? map: null));
			}else if ("onlinelist".equals(cname)) {
				StringBuffer legend=new StringBuffer();
				for(Map<String, String> list : dataList) {
					datas.put(list.get("groupid"), list.get("url"));
					legend.append("<img src=\"images/common/"+list.get("url")+"\" alt=\"\" /> "+list.get("title")+" &nbsp; &nbsp; &nbsp; ");
					if(Integer.valueOf(list.get("groupid"))==7)
					{
						datas.put("guest", list.get("title"));
					}
				}
				datas.put("legend", legend.toString());
			}else if("globalstick".equals(cname)){
				Map<String, Map<String, String>> globalstick = new HashMap<String, Map<String, String>>();
				Map<String,String> fupMap=new HashMap<String, String>();
				Map<String,String> threadMap=new HashMap<String, String>();
				for (Map<String, String> list : dataList) {
					if(list.get("type").equals("forum")){
						fupMap.put(list.get("fid"),list.get("fup"));
					}
					else{
						fupMap.put(list.get("fid"),fupMap.get(list.get("fup")));
					}
				}
				List<Map<String,String>> threads= dataBaseDao.executeQuery("SELECT tid,fid,displayorder FROM jrun_threads WHERE displayorder IN (2, 3)");
				if(threads!=null&&threads.size()>0){
					for (Map<String, String> thread : threads) {
						if(thread.get("displayorder").equals("2")){
							StringBuffer tids=null;
							if(threadMap.get(fupMap.get(thread.get("fid")))==null){
								tids=new StringBuffer(thread.get("tid"));
							}else{
								tids=new StringBuffer(threadMap.get(fupMap.get(thread.get("fid")))+","+thread.get("tid"));
							}
							threadMap.put(fupMap.get(thread.get("fid")),tids.toString());
						}
						else{
							StringBuffer tids=null;
							if(threadMap.get("global")==null){
								tids=new StringBuffer(thread.get("tid"));
							}else{
								tids=new StringBuffer(threadMap.get("global")+","+thread.get("tid"));
							}
							threadMap.put("global", tids.toString());
						}
					}
				}
				Set<Entry<String,String>> keys=threadMap.entrySet(); 
				for (Entry<String,String> temp : keys) {
					String key = temp.getKey();
					String tids=temp.getValue();
					if(tids!=null){
						Map<String,String> categories=new HashMap<String, String>();
						categories.put("tids", tids);
						categories.put("count", String.valueOf(tids.split(",").length));
						globalstick.put(key, categories);
					}
				}
				datas.put(cname, dataParse.combinationChar(globalstick.size() > 0 ? globalstick	: null));
				globalstick=null;
				fupMap=null;
				threadMap=null;
			}else {
				int size= dataList.size();
				for (Integer i = 1; i <= size; i++) {
					map.put(i, dataList.get(i - 1));
				}
				datas.put(cname, dataParse.combinationChar(map.size() > 0 ? map	: null));
			}
			writeToCacheFile(cachename, arrayeval(cname, datas), prefix, append);
			map=null;
		}
		else if ("viewthread".equals(cachename)){
			if("bbcodes".equals(cname)){
				Map<String,Map<String,String>> bbcodeMap = new HashMap<String,Map<String,String>>();
				for (Map<String, String> list : dataList) {
					Map<String,String> codeMap = new HashMap<String,String>();
					codeMap.put(list.get("params"), list.get("replacement"));
					bbcodeMap.put(list.get("tag"), codeMap);
				}
				datas.put(cname, dataParse.combinationChar(bbcodeMap.size() > 0 ? bbcodeMap: null));
			}else if("announcements".equals(cname)){
				int size=dataList.size();
				Map<Integer, Map<String, String>> map = new HashMap<Integer, Map<String, String>>();
				for (int i = 1; i <= size; i++) {
					Map<String,String> data=dataList.get(i-1);
					data.remove("message");
					map.put(i, data);
				}
				datas.put(cname, dataParse.combinationChar(map.size() > 0 ? map: null));
				map=null;
			}else if("smilies_parse".equals(cname)){
				datas.put(cname, dataParse.combinationChar(dataList));
			}else{
				Map<Integer, Map<String, String>> map = new HashMap<Integer, Map<String, String>>();
				int size =dataList.size();
				for (Integer i = 1; i <= size; i++) {
					map.put(i, dataList.get(i - 1));
				}
				datas.put(cname, dataParse.combinationChar(map.size() > 0 ? map	: null));
				map=null;
			}
			String currdata = arrayeval(cname, datas);
			writeToCacheFile(cachename, currdata, prefix, append);
		}else if ("ranks".equals(cachename)){
			Map<Integer, Map<String, String>> map = new HashMap<Integer, Map<String, String>>();
			int size =dataList.size();
			for (Integer i = 1; i <= size; i++) {
				map.put(i, dataList.get(i - 1));
			}
			datas.put(cname, dataParse.combinationChar(map.size() > 0 ? map	: null));
			writeToCacheFile(cachename, arrayeval(cname, datas), prefix, append);
		}else if ("post".equals(cachename)) {
			Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
			if ("bbcodes_display".equals(cname)) {
				for (Map<String, String> bbcode : dataList) {
					map.put(bbcode.get("tag"), bbcode);
				}
				datas.put(cname, dataParse.combinationChar(map.size() > 0 ? map	: null));
				map=null;
			}else if("smilies_display".equals(cname)){
				Map<String, String> smileytypes = new HashMap<String, String>();
				for (Map<String, String> imagetype : dataList) {
					List<Map<String,String>> smileyList=dataBaseDao.executeQuery("SELECT id, code, url FROM jrun_smilies WHERE type='smiley' AND code<>'' AND typeid='"+imagetype.get("typeid")+"' ORDER BY displayorder");
					if(smileyList!=null&&smileyList.size()>0){
						imagetype.remove("displayorder");
						imagetype.remove("type");
						smileytypes.put(imagetype.get("typeid"),  dataParse.combinationChar(imagetype.size() > 0 ? imagetype: null));
						Map<String,Map<String,String>> smilies=new HashMap<String, Map<String,String>>();
						for (Map<String, String> smiley : smileyList) {
							smilies.put(smiley.get("id"), smiley);
						}
						datas.put(imagetype.get("typeid"), dataParse.combinationChar(smilies.size() > 0 ? smilies: null));;
					}
				}
				writeToCacheFile(cachename, arrayeval("smileytypes", smileytypes), prefix, append);
				smileytypes=null;
			}else if("smilies".equals(cname)){
				Map<String, String> searcharray = new HashMap<String, String>();
				Map<String, String> replacearray = new HashMap<String, String>();
				Map<String, String> typearray = new HashMap<String, String>();
				for (Map<String, String> smiley : dataList) {
					searcharray.put(smiley.get("id"), smiley.get("code"));
					replacearray.put(smiley.get("id"), smiley.get("url"));
					typearray.put(smiley.get("id"), smiley.get("typeid"));
				}
				datas.put("searcharray", dataParse.combinationChar(searcharray.size() > 0 ? searcharray: null));
				datas.put("replacearray", dataParse.combinationChar(replacearray.size() > 0 ? replacearray: null));
				datas.put("typearray", dataParse.combinationChar(typearray.size() > 0 ? typearray: null));
				searcharray=null;
				replacearray=null;
				typearray=null;
			}
			writeToCacheFile(cachename, arrayeval(cname, datas), prefix, append);
		}else if ("google".equals(cachename)) {
			if(dataList!=null&&dataList.size()>0){
				Map<String,String> googleInfo=dataList.get(0);
				Map<String,String> google=dataParse.characterParse(googleInfo.get("value"), false);
				writeToJsCacheFile(cachename, "var google_host=\""+host+"\";var google_charset=\""+JspRunConfig.CHARSET+"\";var google_hl=\""+google.get("lang")+"\";var google_lr=\""+(google.get("lang")!=null?"lang_"+google.get("lang"):"")+"\";", "_var");
				googleInfo=null;
				google=null;
			}
		}else if ("baidu".equals(cachename)) {
			if(dataList!=null&&dataList.size()>0){
				Map<String,String> baiduInfo=dataList.get(0);
				Map<String,String> baidu=dataParse.characterParse(baiduInfo.get("value"), false);
				writeToJsCacheFile(cachename, "var baidu_host=\""+host+"\";var baidu_charset=\""+JspRunConfig.CHARSET+"\";var baidu_hl=\""+baidu.get("lang")+"\";var baidu_lr=\""+(baidu.get("lang")!=null?"lang_"+baidu.get("lang"):"")+"\";", "_var");
				baiduInfo=null;
				baidu=null;
			}
		}else if("profilefields".equals(cachename)){
			if(dataList!=null&&dataList.size()>0){
				Map<String,Map<String,String>> fields_required=new TreeMap<String,Map<String,String>>();
				Map<String,Map<String,String>> fields_optional=new TreeMap<String,Map<String,String>>();
				for(Map<String,String> field:dataList){
					if("1".equals(field.get("selective"))){
						field.put("choices", field.get("choices").replaceAll("(\r\n|\r|\n)", ","));
					}else{
						field.remove("choices");
					}
					if("1".equals(field.get("required"))){
						fields_required.put("field_"+field.get("fieldid"), field);
					}else{
						fields_optional.put("field_"+field.get("fieldid"), field);
					}
				}
				datas.put("fields_required", dataParse.combinationChar(fields_required));
				datas.put("fields_optional", dataParse.combinationChar(fields_optional));
			}
			writeToCacheFile(cachename, arrayeval(cname, datas), prefix, append);
		}
		datas=null;
		dataList=null;
	}
	private static String arrayeval(String cachename, Map<String, String> map) {
		StringBuffer mapName = new StringBuffer("_DCACHE_");
		mapName.append(cachename);
		StringBuffer curdata = new StringBuffer();
		curdata.append("<%\n");
		curdata.append("Map<String,String> " + mapName+ "= new HashMap<String,String>();\n");
		if (map != null) {
			Set<Entry<String, String>> keys = map.entrySet();
			for (Entry<String, String> temp : keys) {
				String key = temp.getKey();
				String value = temp.getValue();
				if(value!=null){
					value = Common.addslashes(value);
					value = value.replaceAll("\r\n", "\\\\n");
				}
				curdata.append(mapName + ".put(\"" + key + "\",\"" + value+ "\");\n");
			}
		}
		curdata.append("request.setAttribute(\"" + cachename + "\"," + mapName+ ");\n");
		curdata.append("%>\n");
		return curdata.toString();
	}
	private static boolean writeToCssCache(Map<String, String> datas) throws Exception {
		String path=JspRunConfig.realPath;
		String[] csstemplates = { "css", "css_append" };
		String styleid = datas.get("STYLEID");
		String cachedir = path + "./forumdata/cache/";
		for (String templateName : csstemplates) {
			File file = new File(path + datas.get("TPLDIR")+"/"+ templateName + ".jsp");
			if(!file.exists())	{
				file = new File(path + "./templates/default/"+ templateName + ".jsp");
			}
			if (file.exists()) {
				StringBuffer cssdata = new StringBuffer();
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				while (br.ready()) {
					cssdata.append(br.readLine() + "\n");
				}
				br.close();
				fr.close();
				Set<String> keys = datas.keySet();
				String content = cssdata.toString();
				for (String key : keys) {
					content = content.replaceAll("\\{" + key + "\\}", datas.get(key));
				}
				content = content.replaceAll("<\\?.+?\\?>\\s*", "");
				String imgdir = datas.get("IMGDIR");
				content = !imgdir.matches("^http:\\/\\/.*") ? content.replace("url(\"" + imgdir, "url(\"../../" + imgdir) : content;
				content = !imgdir.matches("^http:\\/\\/.*") ? content.replace("url(" + imgdir, "url(../../" + imgdir) : content;
				String extra = "";
				if (templateName.length() > 3) {
					extra = templateName.substring(3);
				}
				FileOutputStream fos=new FileOutputStream(cachedir + "style_" + styleid+ extra + ".css");
				OutputStreamWriter os=new OutputStreamWriter(fos,JspRunConfig.CHARSET);
				BufferedWriter bw = new BufferedWriter(os);
				FileLock fl = fos.getChannel().tryLock();
				if (fl.isValid()) {
					bw.write(content);
					fl.release();
				}
				bw.flush();
				os.flush();
				fos.flush();
				bw.close();
				os.close();
				fos.close();
			} else {
				throw new Exception("Can not find the csstemplates files, please check directory "+datas.get("TPLDIR")+"/css.jsp and "+datas.get("TPLDIR")+"/css_append.jsp  ");
			}
			file=null;
		}
		return true;
	}
	@SuppressWarnings("deprecation")
	private static boolean writeToCacheFile(String script, String cachedata,String prefix, boolean append) throws Exception {
		String path=JspRunConfig.realPath;
		StringBuffer dir = new StringBuffer();
		dir.append(path);
		dir.append("./forumdata/cache/");
		File dirFile = new File(dir.toString());
		if (!dirFile.isDirectory()) {
			if (!dirFile.mkdir()) {
				throw new Exception("Can not write to cache files, please check directory ./forumdata/ and ./forumdata/cache/ .");
			}
		}
		try {
			FileOutputStream fos=new FileOutputStream(dir + prefix + script + ".jsp",append);
			OutputStreamWriter os=new OutputStreamWriter(fos,JspRunConfig.CHARSET);
			BufferedWriter bw = new BufferedWriter(os);
			FileLock fl = fos.getChannel().tryLock();
			if (fl.isValid()) {
				if (!append) {
					bw.write("<%--\n");
					bw.write("JspRun! cache file, DO NOT modify me!\n");
					bw.write("Created: " + new Date().toGMTString() + "\n");
					bw.write("Identify: "+ Md5Token.getInstance().getLongToken(prefix + prefix + cachedata) + "\n");
					bw.write("--%>\n");
					bw.write("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\""+JspRunConfig.CHARSET+"\"%>\n");
				}
				bw.write(cachedata);
				fl.release();
			}
			bw.flush();
			os.flush();
			fos.flush();
			bw.close();
			os.close();
			fos.close();
			bw=null;
		} catch (IOException e) {
			throw new Exception("Can not write to cache files, please check directory ./forumdata/ and ./forumdata/cache/ .");
		}
		dir=null;
		return true;
	}
	private static boolean writeToJsCacheFile(String script, String cachedata,	String postfix) throws Exception {
		String path=JspRunConfig.realPath;
		StringBuffer dir = new StringBuffer();
		dir.append(path);
		dir.append("./forumdata/cache/");
		File dirFile = new File(dir.toString());
		if (!dirFile.isDirectory()) {
			if (!dirFile.mkdir()) {
				throw new Exception("Can not write to cache files, please check directory ./forumdata/ and ./forumdata/cache/ .");
			}
		}
		try {
			FileOutputStream fos=new FileOutputStream(dir +  script + postfix + ".js");
			OutputStreamWriter os=new OutputStreamWriter(fos,JspRunConfig.CHARSET);
			BufferedWriter bw = new BufferedWriter(os);
			FileLock fl = fos.getChannel().tryLock();
			if (fl.isValid()) {
				bw.write(cachedata);
				fl.release();
			}
			bw.flush();
			os.flush();
			fos.flush();
			bw.close();
			os.close();
			fos.close();
		} catch (IOException e) {
			throw new Exception("Can not write to cache files, please check directory ./forumdata/ and ./forumdata/cache/ .");
		}
		dir=null;
		return true;
	}
	private static String setCssBackGround(Map<String, String> datas, String code) {
		String content=datas.get(code);
		if(content!=null){
			String[] codes = datas.get(code).split(" ");
			StringBuffer css =new StringBuffer("background: ");
			StringBuffer codevalue =new StringBuffer();
			int length=codes.length;
			for (int i = 0; i < length; i++) {
				if (!codes[i].equals("")) {
					if (codes[i].charAt(0) == '#') {
						css.append(codes[i].toUpperCase() + " ");
						codevalue.append(codes[i].toUpperCase());
					} else if (codes[i].matches("^http:\\/\\/.*")) {
						css.append("url(\"" + codes[i] + "\") ");
					} else {
						css.append("url(\"" + datas.get("IMGDIR") + "/" + codes[i]+ "\") ");
					}
				}
			}
			datas.put(code, codevalue.toString());
			return css.toString().trim();
		}else{
			return "";
		}
	}
	public static void setHost(HttpServletRequest request) {
		int port=request.getServerPort();
		String http_host=request.getServerName();
		if(port>0){
			http_host=http_host.concat(":"+port);
		}
		host=http_host;
	}
}