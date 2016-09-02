package cn.jsprun.struts.action;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Projects;
import cn.jsprun.foreg.service.PostOperating;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FinalProperty;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.ImageUtil;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.Mail;
import cn.jsprun.utils.Md5Token;
import cn.jsprun.vo.basic.BKSettingVO;
import cn.jsprun.vo.basic.CreditsParticularInfo;
import cn.jsprun.vo.basic.ResetCredit;
import cn.jsprun.vo.basic.SaveMethodVO;
import cn.jsprun.vo.basic.UsergroupSettingVO;
public class BasicSettingsAction extends BaseAction {
	private static final String tablePrefix = "jrun_";
	public ActionForward basic(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> oldSettings=ForumInit.settings;
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = {"forumfounders","bbname", "sitename", "indexname", "siteurl", "icp","boardlicensed", "bbclosed", "closedreason" };
				Map<String,String> settings=new HashMap<String,String>();
				for(String variable:variables){
					String value=request.getParameter(variable);
					if(value!=null){
						if(variable.equals("forumfounders")){
							StringBuffer uids = new StringBuffer();
							String forumfounders[] = value.split(",");
							for(String forumfounder:forumfounders){
								int uid=Common.toDigit(forumfounder);
								if(uid>0){
									uids.append(","+uid);
								}
							}
							StringBuffer valuebuffer = new StringBuffer();
							if(uids.length()>0){
								List<Map<String,String>> founders = dataBaseService.executeQuery("select uid,adminid from jrun_members where uid in ("+uids.substring(1)+")");
								if(founders!=null&&founders.size()>0){
									for(Map<String,String> founder :founders ){
										if(founder.get("adminid").equals("1")){
											valuebuffer.append(","+founder.get("uid"));
										}
									}
								}
							}
							value = valuebuffer.length()>0?valuebuffer.substring(1):"";
						}
						this.putValue(variable,value.trim(), oldSettings, settings);
					}
				}
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		boolean isfounder=(Boolean)request.getAttribute("isfounder");
		if(!isfounder){
			String forumfounder = oldSettings.get("forumfounders");
			if(forumfounder!=null&&forumfounder.length()>0){
				List<Map<String,String>> founders = dataBaseService.executeQuery("select username from jrun_members where uid in ("+forumfounder+")");
				request.setAttribute("founderlist", founders);
			}
		}
		return mapping.findForward("setting_basic");
	}
	@SuppressWarnings("unchecked")
	public ActionForward access(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String pagedata[] = { "regstatus", "regname", "reglinkname", "regadvance", "censoruser", "regverify", "doublee", "accessemail", "censoremail", "regctrl", "regfloodctrl", "newbiespan", "welcomemsg", "bbrules", "bbrulestxt", "welcomemsgtitle", "welcomemsgtxt", "ipregctrl", "ipaccess", "adminipaccess" };
				String invitedata[] = { "inviterewardcredit", "inviteaddcredit", "invitedaddcredit", "inviteaddbuddy", "invitegroupid" };
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				String ip=Common.get_onlineip(request);
				for(String variable:pagedata){
					String value=request.getParameter(variable).trim();
					if ((!value.equals(""))&&("ipaccess".equals(variable)|| "adminipaccess".equals(variable))&&!Common.ipaccess(ip, value)) {
						request.setAttribute("message", getMessage(request, "a_setting_ipaccess_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}else if("regname".equals(variable) ||"reglinkname".equals(variable)){
						value = Common.htmlspecialchars(value);
					}else if("regctrl".equals(variable) ||"regfloodctrl".equals(variable) ||"newbiespan".equals(variable)){
						value =String.valueOf(Common.toDigit(value));
					}
					this.putValue(variable, value, oldSettings, settings);
				}
				Map<String, String> hm = new HashMap<String, String>();
				for (int i = 0; i < invitedata.length; i++) {
					String data = request.getParameter(invitedata[i]);
					hm.put(invitedata[i], data);
				}
				this.putValue("inviteconfig", dataParse.combinationChar(hm), oldSettings, settings);
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> oldSettings=ForumInit.settings;
		Map inviteconfig=dataParse.characterParse(oldSettings.get("inviteconfig"), false);
		Map extcredits=dataParse.characterParse(oldSettings.get("extcredits_bak"), true);
		List<Map<String,String>> specialGroups=dataBaseService.executeQuery("SELECT groupid, grouptitle FROM jrun_usergroups WHERE type='special'");
		request.setAttribute("inviteconfig", inviteconfig);
		request.setAttribute("extcredits", extcredits);
		request.setAttribute("specialGroups", specialGroups);
		return mapping.findForward("setting_access");
	}
	@SuppressWarnings("unchecked")
	public ActionForward styles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = { "styleid", "stylejump", "frameon", "framewidth","subforumsindex", "forumlinkstatus", "maxbdays", "moddisplay","whosonlinestatus", "whosonline_contract", "maxonlinelist","topicperpage", "threadmaxpages", "hottopic", "fastpost","fastreply","globalstick", "threadsticky", "postperpage", "starthreshold","maxsigrows", "ratelogrecord","showsettings","showjavacode","zoomstatus","vtonlinestatus", "userstatusby", "postno","postnocustom","maxsmilies","memberperpage", "membermaxpages", "hideprivate","visitedforums" };
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
					if("showsettings".equals(variable)){
						int showsignatures=Integer.valueOf(request.getParameter("showsignatures"));
						int showavatars=Integer.valueOf(request.getParameter("showavatars"));
						int showimages=Integer.valueOf(request.getParameter("showimages"));
						if(showsignatures>0){
							showsignatures=4;
						}
						if(showavatars>0){
							showavatars=2;
						}
						this.putValue(variable, String.valueOf(showsignatures+showavatars+showimages), oldSettings, settings);
						this.putValue("showimages", String.valueOf(showimages), oldSettings, settings);
					}else if(Common.matches(variable, "^(maxonlinelist|threadmaxpages|hottopic|starthreshold|maxsigrows|maxsmilies|membermaxpages|visitedforums)$")) {
						this.putValue(variable, String.valueOf(Common.toDigit(request.getParameter(variable))), oldSettings, settings);
					}else if(Common.matches(variable, "^(topicperpage|postperpage|memberperpage)$")) {
						Long value=Long.valueOf(Common.toDigit(request.getParameter(variable)));
						if(value>0){
							this.putValue(variable, value.toString(), oldSettings, settings);
						}
					}else{
						this.putValue(variable, request.getParameter(variable), oldSettings, settings);
					}
				}
				Map<String,Map<String,String>> customauthorinfo=new HashMap<String,Map<String,String>>();
				String[] items=request.getParameter("item").split(",");
				String types[] = { "left", "special", "menu" };
				for (String item:items) {
					Map<String,String> authorinfo=new HashMap<String,String>();
					for (String type:types) {
						String value = request.getParameter("customauthorinfo["+ item + "][" + type + "]");
						if ("1".equals(value)) {
							authorinfo.put(type, value);
						}
					}
					if(authorinfo.size()>0){
						customauthorinfo.put(item, authorinfo);
					}
				}
				Map data = new HashMap();
				data.put(0, customauthorinfo);
				this.putValue("customauthorinfo", dataParse.combinationChar(data), oldSettings, settings);
				byte msgquick = Byte.valueOf(request.getParameter("msgforward[quick]"));
				String message = request.getParameter("msgforward[messages]");
				String refreshtime = String.valueOf(Common.toDigit(request.getParameter("msgforward[refreshtime]")));
				Map<Integer,String> messages = new TreeMap<Integer,String>();
				String mess[] = message.split("\n");
				for (int j = 0; j < mess.length; j++) {
					if (!"".equals(mess[j].trim())){
						messages.put(j,mess[j].trim());
					}
				}
				Map msgforward = new TreeMap();
				msgforward.put("messages", messages);
				msgforward.put("quick", msgquick);
				msgforward.put("refreshtime", refreshtime);
				this.putValue("msgforward", dataParse.combinationChar(msgforward), oldSettings, settings);
				this.updateSettings(settings,oldSettings);
				servlet.getServletContext().setAttribute("msgforward", msgforward);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settings=ForumInit.settings;
		List<Map<String,String >> styleTemplages=dataBaseService.executeQuery("SELECT styleid, name FROM jrun_styles WHERE available=1");
		request.setAttribute("styleTemplages", styleTemplages);
		int showsettings=Integer.valueOf(settings.get("showsettings"));
		request.setAttribute("showsignatures", (showsettings&4)==0);
		request.setAttribute("showavatars", (showsettings&2)==0);
		request.setAttribute("showimages", (showsettings&1)==0);
		StringBuffer items=new StringBuffer();
		Map<String,String> authorinfoitems=new TreeMap<String,String>();
		authorinfoitems.put("uid", getMessage(request, "a_setting_uid"));
		authorinfoitems.put("posts",  getMessage(request, "a_setting_posts"));
		authorinfoitems.put("digest",  getMessage(request, "digest"));
		authorinfoitems.put("credits",  getMessage(request, "credits"));
		items.append("uid,posts,digest,credits");
		Map<Integer,Map> extcredits=dataParse.characterParse(settings.get("extcredits_bak"), true);
		Iterator<Entry<Integer,Map>> extcreditids=extcredits.entrySet().iterator();
		while(extcreditids.hasNext()){
			Entry<Integer,Map> e = extcreditids.next();
			Map extcredit=e.getValue();
			Integer extcreditid = e.getKey();
			if("1".equals(extcredit.get("showinthread"))){
				authorinfoitems.put("extcredits"+extcreditid, (String)extcredit.get("title"));
				items.append(",extcredits"+extcreditid);
			}
		}
		List<Map<String,String>> profilefields=dataBaseService.executeQuery("SELECT fieldid,title FROM jrun_profilefields WHERE available='1' AND invisible='0' AND showinthread='1' ORDER BY displayorder");
		if(profilefields!=null&&profilefields.size()>0){
			for(Map<String,String> profilefield:profilefields){
				authorinfoitems.put("field_"+profilefield.get("fieldid"), profilefield.get("title"));
				items.append(",field_"+profilefield.get("fieldid"));
			}
		}
		authorinfoitems.put("readperm", getMessage(request, "threads_readperm"));
		authorinfoitems.put("gender", getMessage(request, "gender"));
		authorinfoitems.put("location", getMessage(request, "location"));
		authorinfoitems.put("oltime", getMessage(request, "stats_onlinetime"));
		authorinfoitems.put("regtime", getMessage(request, "a_setting_regtime"));
		authorinfoitems.put("lastdate", getMessage(request, "a_setting_lastdate"));
		items.append(",readperm,gender,location,oltime,regtime,lastdate");
		Map<String,Map<String,String>> customauthorinfo=(Map<String,Map<String,String>>)dataParse.characterParse(settings.get("customauthorinfo"), true).get(0);
		request.setAttribute("authorinfoitems",authorinfoitems);
		request.setAttribute("customauthorinfo",customauthorinfo);
		request.setAttribute("items",items);
		Map msgforward=(Map<String,Object>)servlet.getServletContext().getAttribute("msgforward");
		StringBuffer message=new StringBuffer();
		Map<Integer,String> messages= (Map<Integer,String>)msgforward.get("messages");
		if(messages!=null&&messages.size()>0){
			Set<Entry<Integer,String>> keys=messages.entrySet();
			for(Entry<Integer,String> temp:keys){
				message.append("\n"+temp.getValue());
			}
		}
		request.setAttribute("msgforward_message", message.length()>0?message.substring(1):"");
		return mapping.findForward("setting_styles");
	}
	public ActionForward seo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[]={"archiverstatus","rewritestatus","rewritecompatible","seotitle","seokeywords","seodescription","seohead","baidusitemap","baidusitemap_life"};					
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
					String value=request.getParameter(variable);	    	
					if("rewritestatus".equals(variable)){
						int sum=0;
						for(int j=0;j<5;j++){
							String rewritestatus=request.getParameter(variable+j);
							if(rewritestatus!=null){
								sum=sum+Integer.valueOf(rewritestatus);
							}
						}
						value=String.valueOf(sum);
					}
					else if("baidusitemap_life".equals(variable)){
						value=String.valueOf(Common.range(Common.intval(value), 24, 1));
					}
					this.putValue(variable, value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settings=ForumInit.settings;
		int rewritestatus=Common.toDigit(settings.get("rewritestatus"));
		Common.setChecked(request, "rewritestatus", 5, rewritestatus);
		return mapping.findForward("setting_seo");
	}
	public ActionForward cachethread(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String[] variables =new String[]{"cacheindexlife", "cachethreadlife","cachethreaddir", "threadcaches" };
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for(String variable:variables){
					String value=request.getParameter(variable);			
					if("cachethreaddir".equals(variable)){
						File file = new File(JspRunConfig.realPath+value);
						if (!file.isDirectory()) {
							request.setAttribute("message", getMessage(request, "a_setting_dir_noexists", value));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}else if("threadcaches".equals(variable)){
						value=String.valueOf(Common.range(Common.intval(value), 100, 0));
						String forum[]=request.getParameterValues("forum");
						if(forum!=null&&forum.length>0){
							StringBuffer fids=new StringBuffer("0");
							for(String fid:forum){
								if("all".equals(fid)){
									fids=null;
									break;
								}
								else if(!"".equals(fid)) {
									fids.append(","+fid);
								}
							}
							String sql=null;
							if(fids==null){
								sql="update jrun_forums set threadcaches= "	+ value;
							}
							else{
								sql="update jrun_forums set threadcaches="+ value + " where fid in ("+fids+")";
							}
							dataBaseService.runQuery(sql,true);
						}
					}
					else{
						value=Common.toDigit(value)+"";
					}			
					this.putValue(variable, value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		HttpSession session=request.getSession();
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
		return mapping.findForward("setting_cachethread");
	}
	@SuppressWarnings("unchecked")
	public ActionForward functions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = { "forumjump", "jsmenustatus", "pluginjsmenu","editoroptions","bbinsert","smileyinsert", "smthumb", "smcols", "smrows", "statstatus","statscachelife", "pvfrequence", "oltimespan", "modworkstatus","maxmodworksmonths", "myrecorddays", "losslessdel","modreasons", "bannedmessages", "tagstatus", "hottags","viewthreadtags", "rssstatus", "rssttl", "allowcsscache","bdaystatus", "debug", "activitytype"};
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for(String variable:variables){
					String value=request.getParameter(variable);
					if ("jsmenustatus".equals(variable)) {
						int sum = 0;
						for (int j = 0; j < 4; j++) {
							String par = request.getParameter(variable + j);
							if (par != null)
								sum = sum + Integer.valueOf(par);
						}
						value = String.valueOf(sum);
						oldSettings.put("jsmenu_1",String.valueOf(sum&1));
						oldSettings.put("jsmenu_2",String.valueOf(sum&2));
						oldSettings.put("jsmenu_3",String.valueOf(sum&4));
						oldSettings.put("jsmenu_4",String.valueOf(sum&8));
					}else if("editoroptions".equals(variable)||"editoroptions_changer".equals(variable)){
						int editoroptions_editer=Integer.valueOf(request.getParameter("editoroptions_editer"));
						int editoroptions_changer=Integer.valueOf(request.getParameter("editoroptions_changer"));
						value=String.valueOf(editoroptions_editer+editoroptions_changer);
					}else if(Common.matches(variable, "^(smcols|statscachelife|pvfrequence|oltimespan|maxmodworksmonths|losslessdel|rssttl)$")){
						value = Double.valueOf(FormDataCheck.getDoubleString(value)).toString();
						if(value.endsWith(".0")){
							value = value.substring(0,value.length()-2);
						}
					}else if("smthumb".equals(variable)){
						long smthumb=Common.toDigit(value);
						value =String.valueOf(smthumb);
						if(!(smthumb>=20&&smthumb<=40)){
							value = "20";
						}
					}else if("myrecorddays".equals(variable)){
						long myrecorddays=Common.toDigit(value);
						value =String.valueOf(myrecorddays);
						if(myrecorddays<1){
							value = "30";
						}
					}
					this.putValue(variable, value, oldSettings, settings);
				}
				String honorset = request.getParameter("honorset");
				this.putValue("honorset", honorset, oldSettings, settings);
				String[] medalIdArray = request.getParameterValues("au_medalid");
				List<Map<String,String>> medalMapList = dataBaseService.executeQuery("SELECT medalid FROM "+tablePrefix+"medals WHERE available");
				Map<String,Map<String,String>> honorvalueTDP = new HashMap<String, Map<String,String>>(); 
				boolean checked_b = false;
				for(Map<String,String> medalMap : medalMapList){
					checked_b = false;
					String medalid = medalMap.get("medalid");
					Map<String,String> honorvalueIMP = new HashMap<String, String>();
					honorvalueTDP.put(medalid, honorvalueIMP);
					if(medalIdArray!=null){
						for(String medalIdFR : medalIdArray){
							if(medalid.equals(medalIdFR)){
								checked_b = true;
								break;
							}
						}
					}
					honorvalueIMP.put("checked", checked_b?"1":"0");
					String qualification = request.getParameter("mqualification_"+medalid);
					if (checked_b && (qualification==null||qualification.trim().equals("")||!validateQualification(qualification))) {
						request.setAttribute("message",getMessage(request, "a_setting_creditsformula_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					honorvalueIMP.put("qualification", qualification);
					honorvalueIMP.put("reason", request.getParameter("mreason_"+medalid));
				}
				String honorvalue = dataParse.combinationChar(honorvalueTDP);
				this.putValue("honorvalue", honorvalue, oldSettings, settings);
				this.updateSettings(settings,oldSettings);
				if("2".equals(honorset)&& medalIdArray!=null){
					Members currentMember = (Members) request.getSession().getAttribute("members");
					int timestamp = (Integer)(request.getAttribute("timestamp"));
					StringBuffer uids = new StringBuffer();
					StringBuffer logBuffer = new StringBuffer();
					for(String medalId : medalIdArray){
						uids.delete(0, uids.length());
						uids.append("0");
						Map<String,String> honorvalueIMP = honorvalueTDP.get(medalId);
						String qualification = honorvalueIMP.get("qualification");
						String reason = honorvalueIMP.get("reason");
						qualification = qualification.replace("extcredits", "m.extcredits").replace("oltime", "m.oltime").replace("pageviews", "m.pageviews").replaceAll("posts", "temp").replace("digesttemp", "m.digestposts").replace("temp", "m.posts");
						List<Map<String, String>> memberslist = dataBaseService.executeQuery("select m.uid,m.username,mm.medals from "+tablePrefix+"members as m left join "+tablePrefix+"memberfields as mm on m.uid=mm.uid where "+qualification);
						boolean exist = false;
						if(memberslist!=null){
							String onlineip=Common.get_onlineip(request);
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
									logBuffer.append(currentMember.getUsername().replaceAll("\n", " "));
									logBuffer.append("\t");
									logBuffer.append(onlineip);
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
							dataBaseService.execute("UPDATE "+tablePrefix+"memberfields SET medals=concat(medals,'"+medalId+"\t') where uid IN(" + uidInfo+")");
						}
					}
					if(logBuffer.length()>0){
						Log.writelog("medalslog",timestamp,logBuffer.toString(),true);
					}
				}
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settings=ForumInit.settings;
		int jsmenustatus=Integer.valueOf(settings.get("jsmenustatus"));
		Common.setChecked(request, "jsmenustatus", 4, jsmenustatus);
		List<Map<String,String>> medalMapList = dataBaseService.executeQuery("SELECT medalid,name,image FROM "+tablePrefix+"medals WHERE available='1'");
		String honorvalue = settings.get("honorvalue");
		Map<String,Map<String,String>> honorvalueDP = (Map<String,Map<String,String>>)dataParse.characterParse(honorvalue, false);
		String tempString = null;
		for(Map<String,String> medalMap : medalMapList){
			medalMap.put("name", medalMap.get("name").replace("\"", "&quot;"));
			medalMap.put("image", medalMap.get("image").replace("\"", "&quot;"));
			Map<String,String> tempMap = honorvalueDP.get(medalMap.get("medalid"));
			if(tempMap!=null){
				tempString = tempMap.get("qualification");
				tempString = tempString !=null ? tempString.replace("\"", "&quot;"):null;
				medalMap.put("qualification", tempString);
				tempString = tempMap.get("reason");
				tempString = tempString !=null ? tempString.replace("\"", "&quot;"):null;
				medalMap.put("reason", tempString);
				medalMap.put("checked", tempMap.get("checked"));
			}else{
				medalMap.put("qualification", "");
				medalMap.put("reason", getMessage(request, "a_setting_auto_send"));
				medalMap.put("checked", "0");
			}
		}
		Map extcredits = dataParse.characterParse(settings.get("extcredits_bak"),true);
		request.setAttribute("medalMapList", medalMapList);
		request.setAttribute("extcredits", extcredits);
		return mapping.findForward("setting_functions");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toCredits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String projectsave = request.getParameter("projectsave");
				String projectId = request.getParameter("projectid");
				Map<String,String> oldSettingMap = ForumInit.settings;
				Map<String,String> newSettingMap = new HashMap<String,String>();
				Map<Integer, Map> extcreditsMap = new HashMap<Integer, Map>();
				Map<String, Map> creditspolicyMap = new HashMap<String, Map>();
				Map<Integer, Integer> creditspolicyValue_post = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_reply = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_digest = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_postattach = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_getattach = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_pm = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_search = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_promotion_visit = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_promotion_register = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_tradefinished = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_votepoll = new TreeMap<Integer, Integer>();
				Map<Integer, Integer> creditspolicyValue_lowerlimit = new TreeMap<Integer, Integer>();
				String[] extcreditId = request.getParameterValues("extcreditId");
				StringBuffer initcreditBuffer = new StringBuffer();
				String creditstrans = request.getParameter("creditstrans"); 
				Map<String,String> creditMap = new HashMap<String, String>();
				for (int i = 0; i < extcreditId.length; i++) {
					Integer extcreditsKey = new Integer(extcreditId[i]);
					String extcredits_title = request.getParameter("extcredits_title_" + extcreditId[i]);
					String extcredits_unit = request.getParameter("extcredits_unit_" + extcreditId[i]); 
					String extcredits_ratio = request.getParameter("extcredits_ratio_" + extcreditId[i]); 
					extcredits_ratio = FormDataCheck.turnToDoubleString(extcredits_ratio);
					String extcredits_available = request.getParameter("extcredits_available_" + extcreditId[i]); 
					if (extcredits_available == null && creditstrans.equals(extcreditId[i])) {
						request.setAttribute("message", getMessage(request, "a_setting_creditstrans_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					String extcredits_showinthread = request.getParameter("extcredits_showinthread_" + extcreditId[i]); 
					String extcredits_allowexchangeout = request.getParameter("extcredits_allowexchangeout_" + extcreditId[i]); 
					String extcredits_allowexchangein = request.getParameter("extcredits_allowexchangein_" + extcreditId[i]);
					String creditspolicy_lowerlimit = request.getParameter("creditspolicy_lowerlimit_" + extcreditId[i]); 
					if (creditspolicy_lowerlimit == null) {
						creditspolicy_lowerlimit = "0";
					}else{
						creditspolicy_lowerlimit = getTrueString(creditspolicy_lowerlimit);
					}
					Map<String, Object> extcreditsValue = new TreeMap<String, Object>();
					extcreditsValue.put("title", extcredits_title);
					extcreditsValue.put("unit", extcredits_unit);
					extcreditsValue.put("ratio", Double.valueOf(extcredits_ratio));
					extcreditsValue.put("available", extcredits_available);
					extcreditsValue.put("showinthread", extcredits_showinthread);
					extcreditsValue.put("allowexchangeout", extcredits_allowexchangeout);
					extcreditsValue.put("allowexchangein", extcredits_allowexchangein);
					extcreditsValue.put("lowerlimit", Integer.valueOf(creditspolicy_lowerlimit));
					extcreditsMap.put(extcreditsKey, extcreditsValue);
					String creditspolicy_post = request.getParameter("creditspolicy_post_" + extcreditId[i]); 
					if (creditspolicy_post != null) {
						creditspolicy_post = getTrueString(creditspolicy_post);
					}
					String creditspolicy_reply = request.getParameter("creditspolicy_reply_" + extcreditId[i]); 
					if (creditspolicy_reply != null) {
						creditspolicy_reply = getTrueString(creditspolicy_reply);
					}
					String creditspolicy_digest = request.getParameter("creditspolicy_digest_" + extcreditId[i]); 
					if (creditspolicy_digest != null) {
						creditspolicy_digest = getTrueString(creditspolicy_digest);
					}
					String creditspolicy_postattach = request.getParameter("creditspolicy_postattach_" + extcreditId[i]); 
					if (creditspolicy_postattach != null) {
						creditspolicy_postattach = getTrueString(creditspolicy_postattach);
					}
					String creditspolicy_getattach = request.getParameter("creditspolicy_getattach_" + extcreditId[i]); 
					if (creditspolicy_getattach != null) {
						creditspolicy_getattach = getTrueString(creditspolicy_getattach);
					}
					String creditspolicy_pm = request.getParameter("creditspolicy_pm_" + extcreditId[i]); 
					if (creditspolicy_pm != null) {
						creditspolicy_pm = getTrueString(creditspolicy_pm);
					}
					String creditspolicy_search = request.getParameter("creditspolicy_search_" + extcreditId[i]); 
					if (creditspolicy_search != null) {
						creditspolicy_search = getTrueString(creditspolicy_search);
					}
					String creditspolicy_promotion_visit = request.getParameter("creditspolicy_promotion_visit_" + extcreditId[i]);
					if (creditspolicy_promotion_visit != null) {
						creditspolicy_promotion_visit = getTrueString(creditspolicy_promotion_visit);
					}
					String creditspolicy_promotion_register = request.getParameter("creditspolicy_promotion_register_" + extcreditId[i]); 
					if (creditspolicy_promotion_register != null) {
						creditspolicy_promotion_register = getTrueString(creditspolicy_promotion_register);
					}
					String creditspolicy_tradefinished = request.getParameter("creditspolicy_tradefinished_" + extcreditId[i]); 
					if (creditspolicy_tradefinished != null) {
						creditspolicy_tradefinished = getTrueString(creditspolicy_tradefinished);
					}
					String creditspolicy_votepoll = request.getParameter("creditspolicy_votepoll_" + extcreditId[i]); 
					if (creditspolicy_votepoll != null) {
						creditspolicy_votepoll = getTrueString(creditspolicy_votepoll);
					}
					String initcreditString = request.getParameter("initcredits_" + extcreditId[i]); 
					initcreditString = FormDataCheck.getNumberFromFormOfDisplayorder(initcreditString);
					try {
						Integer.valueOf(initcreditString);
					} catch (Exception exception) {
						if (initcreditString.startsWith("-")) {
							initcreditString = Integer.valueOf(Integer.MIN_VALUE).toString();
						} else {
							initcreditString = Integer.valueOf(Integer.MAX_VALUE).toString();
						}
					}
					if (i + 1 == extcreditId.length) {
						initcreditBuffer = initcreditBuffer.append(initcreditString);
					} else {
						initcreditBuffer = initcreditBuffer.append(initcreditString + ",");
					}
					creditMap.put("extcredits"+extcreditId[i], initcreditString);
					if(creditspolicy_post != null && !"0".equals(creditspolicy_post)){
						creditspolicyValue_post.put(extcreditsKey, Integer.valueOf(creditspolicy_post));
					}
					if(creditspolicy_reply != null && !"0".equals(creditspolicy_reply)){
						creditspolicyValue_reply.put(extcreditsKey, Integer.valueOf(creditspolicy_reply));
					}
					if(creditspolicy_digest != null && !"0".equals(creditspolicy_digest)){
						creditspolicyValue_digest.put(extcreditsKey, Integer.valueOf(creditspolicy_digest));
					}
					if(creditspolicy_postattach != null && !"0".equals(creditspolicy_postattach)){
						creditspolicyValue_postattach.put(extcreditsKey, Integer.valueOf(creditspolicy_postattach));
					}
					if(creditspolicy_getattach != null && !"0".equals(creditspolicy_getattach)){
						creditspolicyValue_getattach.put(extcreditsKey, Integer.valueOf(creditspolicy_getattach));
					}
					if(creditspolicy_pm != null && !"0".equals(creditspolicy_pm)){
						creditspolicyValue_pm.put(extcreditsKey, Integer.valueOf(creditspolicy_pm));
					}
					if(creditspolicy_search != null && !"0".equals(creditspolicy_search)){
						creditspolicyValue_search.put(extcreditsKey, Integer.valueOf(creditspolicy_search));
					}
					if(creditspolicy_promotion_visit != null && !"0".equals(creditspolicy_promotion_visit)){
						creditspolicyValue_promotion_visit.put(extcreditsKey, Integer.valueOf(creditspolicy_promotion_visit));
					}
					if(creditspolicy_promotion_register != null && !"0".equals(creditspolicy_promotion_register)){
						creditspolicyValue_promotion_register.put(extcreditsKey, Integer.valueOf(creditspolicy_promotion_register));
					}
					if (creditspolicy_tradefinished != null && !"0".equals(creditspolicy_tradefinished)) {
						creditspolicyValue_tradefinished.put(extcreditsKey, Integer.valueOf(creditspolicy_tradefinished));
					}
					if (creditspolicy_votepoll != null && !"0".equals(creditspolicy_votepoll)) {
						creditspolicyValue_votepoll.put(extcreditsKey, Integer.valueOf(creditspolicy_votepoll));
					}
					if (creditspolicy_lowerlimit != null && !"0".equals(creditspolicy_lowerlimit)) {
						creditspolicyValue_lowerlimit.put(extcreditsKey, Integer.valueOf(creditspolicy_lowerlimit));
					}
				}
				creditspolicyMap.put("post", creditspolicyValue_post);
				creditspolicyMap.put("reply", creditspolicyValue_reply);
				creditspolicyMap.put("digest", creditspolicyValue_digest);
				creditspolicyMap.put("postattach", creditspolicyValue_postattach);
				creditspolicyMap.put("getattach", creditspolicyValue_getattach);
				creditspolicyMap.put("pm", creditspolicyValue_pm);
				creditspolicyMap.put("search", creditspolicyValue_search);
				creditspolicyMap.put("promotion_visit", creditspolicyValue_promotion_visit);
				creditspolicyMap.put("promotion_register", creditspolicyValue_promotion_register);
				creditspolicyMap.put("tradefinished", creditspolicyValue_tradefinished);
				creditspolicyMap.put("votepoll", creditspolicyValue_votepoll);
				creditspolicyMap.put("lowerlimit", creditspolicyValue_lowerlimit);
				String extcredits_bak = dataParse.combinationChar(extcreditsMap);
				putValue("extcredits",extcredits_bak, oldSettingMap, newSettingMap);
				putValue("creditspolicy", dataParse.combinationChar(creditspolicyMap), oldSettingMap, newSettingMap);
				String creditsformula = request.getParameter("creditsformula"); 
				if (creditsformula==null||creditsformula.trim().equals("")||!validateExpressions(creditsformula)) {
					request.setAttribute("message",getMessage(request, "a_setting_creditsformula_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				putValue("creditsformula", creditsformula, oldSettingMap, newSettingMap);
				PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
				int totalCredit = postOperating.getTotalCredits(creditsformula, creditMap);
				initcreditBuffer.insert(0, ",");
				initcreditBuffer.insert(0, totalCredit);
				putValue("initcredits", initcreditBuffer.toString(), oldSettingMap, newSettingMap);
				putValue("creditsformulaexp", trunExpression(creditsformula,extcreditsMap,request), oldSettingMap, newSettingMap);
				putValue("creditstrans", creditstrans, oldSettingMap, newSettingMap);
				String creditstax = request.getParameter("creditstax"); 
				creditstax = FormDataCheck.turnToDoubleString(creditstax);
				Double double1 = Double.valueOf(creditstax);
				if (double1 >= 1) {
					creditstax = "0";
				}
				if (double1 < 0) {
					creditstax = "0";
				}
				putValue("creditstax", creditstax, oldSettingMap, newSettingMap);
				String transfermincredits = request.getParameter("transfermincredits");
				if (!transfermincredits.equals("INF")&& !transfermincredits.equals("-INF")) {
					transfermincredits = Double.valueOf(FormDataCheck.getDoubleString(transfermincredits)).toString();
				}
				if (transfermincredits.equals("Infinity")) {
					transfermincredits = "INF";
				}
				if (transfermincredits.equals("-Infinity")) {
					transfermincredits = "-INF";
				}
				if (transfermincredits.endsWith(".0")) {
					transfermincredits = transfermincredits.substring(0,
							transfermincredits.length() - 2);
				}
				putValue("transfermincredits", transfermincredits, oldSettingMap, newSettingMap);
				String exchangemincredits = request.getParameter("exchangemincredits");
				if (!exchangemincredits.equals("INF")&& !exchangemincredits.equals("-INF")) {
					exchangemincredits = Double.valueOf(FormDataCheck.getDoubleString(exchangemincredits)).toString();
				}
				if (exchangemincredits.equals("Infinity")) {
					exchangemincredits = "INF";
				}
				if (exchangemincredits.equals("-Infinity")) {
					exchangemincredits = "-INF";
				}
				if (exchangemincredits.endsWith(".0")) {
					exchangemincredits = exchangemincredits.substring(0,exchangemincredits.length() - 2);
				}
				putValue("exchangemincredits", exchangemincredits, oldSettingMap, newSettingMap);
				String maxincperthread = request.getParameter("maxincperthread"); 
				if (!maxincperthread.equals("INF") && !maxincperthread.equals("-INF")) {
					maxincperthread = Double.valueOf(
							FormDataCheck.getDoubleString(maxincperthread)).toString();
				}
				if (maxincperthread.equals("Infinity")) {
					maxincperthread = "INF";
				}
				if (maxincperthread.equals("-Infinity")) {
					maxincperthread = "-INF";
				}
				if (maxincperthread.endsWith(".0")) {
					maxincperthread = maxincperthread.substring(0, maxincperthread
							.length() - 2);
				}
				putValue("maxincperthread", maxincperthread, oldSettingMap, newSettingMap);
				String maxchargespan = request.getParameter("maxchargespan");
				if (!maxchargespan.equals("INF") && !maxchargespan.equals("-INF")) {
					maxchargespan = Double.valueOf(FormDataCheck.getDoubleString(maxchargespan)).toString();
				}
				if (maxchargespan.equals("Infinity")) {
					maxchargespan = "INF";
				}
				if (maxchargespan.equals("-Infinity")) {
					maxchargespan = "-INF";
				}
				if (maxchargespan.endsWith(".0")) {
					maxchargespan = maxchargespan.substring(0,maxchargespan.length() - 2);
				}
				putValue("maxchargespan", maxchargespan, oldSettingMap, newSettingMap);
				updateSettings(newSettingMap,oldSettingMap);
				oldSettingMap.put("extcredits_bak", extcredits_bak);
				updateExtcredits(oldSettingMap);
				if(newSettingMap.get("creditsformula")!=null){
					String hql = "UPDATE Members SET credits ="+creditsformula;
					memberService.updateMembers(hql);
				}
				if (projectsave.equals("projectsave")) {
					boolean isfounder = (Boolean)request.getAttribute("isfounder");
					if(!isfounder){
						request.setAttribute("message", getMessage(request, "noaccess_isfounder"));
						return mapping.findForward("message");
					}
					if (!projectId.equals("0")) {
						Short id = new Short(projectId);
						Projects project = creSetSer.findBuId(id);
						SaveMethodVO saveMethodVO = new SaveMethodVO();
						Map map = dataParse.characterParse(project.getValue(), true);
						Map map2 = (Map) map.get("savemethod");
						Collection<String> collection = map2.values();
						Iterator<String> iterator = collection.iterator();
						while (iterator.hasNext()) {
							String temp = iterator.next();
							if (temp.equals("1")) {
								saveMethodVO.setProjectSetting(1);
							}
							if (temp.equals("2")) {
								saveMethodVO.setExpressionsSetting(1);
							}
							if (temp.equals("3")) {
								saveMethodVO.setUseSetting(1);
							}
						}
						request.setAttribute("saveMethodVO", saveMethodVO);
						request.setAttribute("project", project);
					}
					return mapping.findForward("saveProject");
				}
				request.setAttribute("message", getMessage(request, "a_setting_baseset_success"));
				request.setAttribute("url_forward", request.getContextPath() + FinalProperty.FROM_BasicSettingsAction_GO_credits);
				return mapping.findForward("message");
			} else if(submitCheck(request, "addsubmit")){
				String[] savemethod = request.getParameterValues("savemethod"); 
				Map<String,String> settingMap = ForumInit.settings;
				Map<String,Object> outMap = new TreeMap<String, Object>();
				Map<Integer,String> savemethodMap = new TreeMap<Integer,String>();
				if (savemethod == null) {
					request.setAttribute("message", getMessage(request, "a_setting_project_no_item"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				} else {
					for (int i = 0; i < savemethod.length; i++) {
						if (savemethod[i].equals("all")) { 
							savemethodMap.put(0, "1");
							savemethodMap.put(1, "2");
							savemethodMap.put(2, "3");
							String extcredits = settingMap.get("extcredits_bak");
							outMap.put("extcredits", extcredits);
							String creditspolicy = settingMap.get("creditspolicy");
							outMap.put("creditspolicy", creditspolicy);
							String creditsformula = settingMap.get("creditsformula");
							outMap.put("creditsformula", creditsformula);
							String creditstrans = settingMap.get("creditstrans");
							outMap.put("creditstrans", creditstrans);
							String creditstax = settingMap.get("creditstax");
							outMap.put("creditstax", creditstax);
							String transfermincredits = settingMap.get("transfermincredits");
							outMap.put("transfermincredits", transfermincredits);
							String exchangemincredits = settingMap.get("exchangemincredits");
							outMap.put("exchangemincredits", exchangemincredits);
							String maxincperthread = settingMap.get("maxincperthread");
							outMap.put("maxincperthread", maxincperthread);
							String maxchargespan = settingMap.get("maxchargespan");
							outMap.put("maxchargespan", maxchargespan);
							break;
						} else if (savemethod[i].equals("1")) { 
							savemethodMap.put(i, "1");
							String extcredits = settingMap.get("extcredits_bak");
							outMap.put("extcredits", extcredits);
							String creditspolicy = settingMap.get("creditspolicy");
							outMap.put("creditspolicy", creditspolicy);
							if (outMap.get("creditsformula") == null) {
								outMap.put("creditsformula", null);
							}
							if (outMap.get("creditstrans") == null) {
								outMap.put("creditstrans", null);
							}
							if (outMap.get("creditstax") == null) {
								outMap.put("creditstax", null);
							}
							if (outMap.get("transfermincredits") == null) {
								outMap.put("transfermincredits", null);
							}
							if (outMap.get("exchangemincredits") == null) {
								outMap.put("exchangemincredits", null);
							}
							if (outMap.get("maxincperthread") == null) {
								outMap.put("maxincperthread", null);
							}
							if (outMap.get("maxchargespan") == null) {
								outMap.put("maxchargespan", null);
							}
						} else if (savemethod[i].equals("2")) { 
							savemethodMap.put(i, "2");
							String creditsformula = settingMap.get("creditsformula");
							outMap.put("creditsformula", creditsformula);
							if (outMap.get("extcredits") == null) {
								String extcredits = settingMap.get("extcredits_bak");
								extcredits = clearMap(extcredits);
								outMap.put("extcredits", extcredits);
							}
							if (outMap.get("creditspolicy") == null) {
								String creditspolicy = settingMap.get("creditspolicy");
								creditspolicy = clearMap(creditspolicy);
								outMap.put("creditspolicy", creditspolicy);
							}
							if (outMap.get("creditstrans") == null) {
								outMap.put("creditstrans", null);
							}
							if (outMap.get("creditstax") == null) {
								outMap.put("creditstax", null);
							}
							if (outMap.get("transfermincredits") == null) {
								outMap.put("transfermincredits", null);
							}
							if (outMap.get("exchangemincredits") == null) {
								outMap.put("exchangemincredits", null);
							}
							if (outMap.get("maxincperthread") == null) {
								outMap.put("maxincperthread", null);
							}
							if (outMap.get("maxchargespan") == null) {
								outMap.put("maxchargespan", null);
							}
						} else if (savemethod[i].equals("3")) { 
							savemethodMap.put(i, "3");
							String creditstrans = settingMap.get("creditstrans");
							outMap.put("creditstrans", creditstrans);
							String creditstax = settingMap.get("creditstax");
							outMap.put("creditstax", creditstax);
							String transfermincredits = settingMap.get("transfermincredits");
							outMap.put("transfermincredits", transfermincredits);
							String exchangemincredits = settingMap.get("exchangemincredits");
							outMap.put("exchangemincredits", exchangemincredits);
							String maxincperthread = settingMap.get("maxincperthread");
							outMap.put("maxincperthread", maxincperthread);
							String maxchargespan = settingMap.get("maxchargespan");
							outMap.put("maxchargespan", maxchargespan);
							if (outMap.get("creditsformula") == null) {
								outMap.put("creditsformula", null);
							}
							if (outMap.get("extcredits") == null) {
								String extcredits = settingMap.get("extcredits_bak");
								extcredits = clearMap(extcredits);
								outMap.put("extcredits", extcredits);
							}
							if (outMap.get("creditspolicy") == null) {
								String creditspolicy = settingMap.get("creditspolicy");
								creditspolicy = clearMap(creditspolicy);
								outMap.put("creditspolicy", creditspolicy);
							}
						}
					}
					outMap.put("savemethod", savemethodMap);
				}
				String value = dataParse.combinationChar(outMap);
				String name = request.getParameter("name"); 
				if (name == null || name.equals("")) {
					request.setAttribute("message", getMessage(request, "project_no_title"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (name.length() > 50) {
					name = name.substring(0, 50);
				}
				String projectid = request.getParameter("projectid"); 
				String coverwith = request.getParameter("coverwith"); 
				String description = request.getParameter("description"); 
				if (projectid != null && coverwith.equals("1")) {
					dataBaseService.execute("UPDATE "+tablePrefix+"projects SET name='"+Common.addslashes(name)+"',type='extcredit',description='"+Common.addslashes(description)+"',value='"+Common.addslashes(value)+"' WHERE id="+projectid);
				} else { 
					dataBaseService.execute("INSERT INTO "+tablePrefix+"projects VALUES(DEFAULT,'"+Common.addslashes(name)+"','extcredit','"+Common.addslashes(description)+"','"+Common.addslashes(value)+"')");
				}
				request.setAttribute("message", getMessage(request, "project_sava_succeed"));
				request.setAttribute("url_forward", request.getContextPath() + FinalProperty.FROM_BasicSettingsAction_GO_credits);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String projectId = request.getParameter("projectid");
		Map extcredits = null;
		Map creditspolicy = null;
		String creditsformula = null;
		String creditstrans = null;
		String creditstax = null;
		String transfermincredits = null;
		String exchangemincredits = null;
		String maxincperthread = null;
		String maxchargespan = null;
		if (projectId == null || projectId.equals("0")) {
			Map<String,String> settingMap = ForumInit.settings;
			extcredits = dataParse.characterParse(settingMap.get("extcredits_bak"), true);
			creditspolicy = dataParse.characterParse(settingMap.get("creditspolicy"), true);
			creditsformula = settingMap.get("creditsformula");
			creditstrans = settingMap.get("creditstrans");
			creditstax = settingMap.get("creditstax");
			transfermincredits = settingMap.get("transfermincredits");
			exchangemincredits = settingMap.get("exchangemincredits");
			maxincperthread = settingMap.get("maxincperthread");
			maxchargespan = settingMap.get("maxchargespan");
		} else {
			Short id = new Short(projectId);
			Projects project = creSetSer.findBuId(id);
			if (project != null) {
				String str = project.getValue();
				Map temp = dataParse.characterParse(str,  true);
				extcredits = dataParse.characterParse((String) temp.get("extcredits"),true);
				creditspolicy = dataParse.characterParse((String) temp.get("creditspolicy"), true);
				creditstrans = (String) temp.get("creditstrans");
				creditsformula = (String) temp.get("creditsformula");
				creditstax = (String) temp.get("creditstax");
				transfermincredits = (String) temp.get("transfermincredits");
				exchangemincredits = (String) temp.get("exchangemincredits");
				maxincperthread = (String) temp.get("maxincperthread");
				maxchargespan = (String) temp.get("maxchargespan");
			}
		}
		request.setAttribute("projectId", projectId);
		request.setAttribute("extcredits", extcredits);
		request.setAttribute("creditspolicy", creditspolicy);
		request.setAttribute("creditstrans", creditstrans);
		request.setAttribute("creditsformula", creditsformula);
		request.setAttribute("creditstax", creditstax);
		request.setAttribute("transfermincredits", transfermincredits);
		request.setAttribute("exchangemincredits", exchangemincredits);
		request.setAttribute("maxincperthread", maxincperthread);
		request.setAttribute("maxchargespan", maxchargespan);
		request.setAttribute("projects", dataBaseService.executeQuery("SELECT id,name FROM "+tablePrefix+"projects WHERE type='extcredit'"));
		request.setAttribute("initcredits", ForumInit.settings.get("initcredits").split(","));
		return mapping.findForward("credit");
	}
	public ActionForward creditsGuide(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settingsMap = ForumInit.settings;
		request.setAttribute("extcredits", dataParse.characterParse(settingsMap.get("extcredits_bak"), true));
		return mapping.findForward("tocreditWizard");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toCreditParticularse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String creditId = request.getParameter("extcreditid");
				Integer creditid = Integer.valueOf(creditId);
				Map<String,String> oldSettingMap = ForumInit.settings;
				String creditstrans = oldSettingMap.get("creditstrans");
				if(creditstrans.equals(creditId)&&"0".equals(request.getParameter("available"))){
					request.setAttribute("message", getMessage(request, "a_setting_startup_extendscredit"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				Map<String,String> newSettingMap = new HashMap<String,String>();
				Map<Integer,Map<String,Object>> extcredits = encapsulationCredit(request,creditid,oldSettingMap.get("extcredits_bak"));
				String newExtcredits = dataParse.combinationChar(extcredits);
				putValue("extcredits", newExtcredits, oldSettingMap, newSettingMap);
				Map<String,Map<Integer,Object>> creditspolicyMap = encapsulationCreditspolicy(request,creditid,oldSettingMap);
				String creditspolicyValue = dataParse.combinationChar(creditspolicyMap);
				putValue("creditspolicy", creditspolicyValue, oldSettingMap, newSettingMap);
				String initString = encapsulationInitcredits(request.getParameter("initcredits"),creditid,oldSettingMap);
				String[] initcreditsArray = initString.split(",");
				Map<String,String> creditMap = new HashMap<String, String>();
				for(int i = 1;i<9;i++){
					creditMap.put("extcredits"+i, initcreditsArray[i]);
				}
				String creditsformula = oldSettingMap.get("creditsformula");
				PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
				int totalCredit = postOperating.getTotalCredits(creditsformula, creditMap);
				putValue("initcredits", totalCredit+initString.substring(initString.indexOf(",")), oldSettingMap, newSettingMap);
				updateSettings(newSettingMap, oldSettingMap);
				oldSettingMap.put("extcredits_bak", newExtcredits);
				updateExtcredits(oldSettingMap);
				request.setAttribute("message", getMessage(request, "a_setting_creditwizard_succeed"));
				request.setAttribute("url_forward", request.getHeader("Referer"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String creditId = request.getParameter("extcreditid");
		Map<String,String> settingMap = ForumInit.settings;
		Map<Integer,Map<String,Object>> extcredits = dataParse.characterParse(settingMap.get("extcredits_bak"), true);
		Map<String,Map<Integer,Object>> creditspolicy = dataParse.characterParse(settingMap.get("creditspolicy"), true);
		request.setAttribute("valueObject", getCreditsParticularInfo(creditId,settingMap.get("initcredits"), extcredits, creditspolicy));
		return mapping.findForward("tocreditparticularse");
	}
	@SuppressWarnings("unchecked")
	public ActionForward bankuaiSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String creditId = request.getParameter("extcreditid"); 
				int creditid = Integer.parseInt(creditId);
				String[] bankuaiIdArray = request.getParameterValues("bankuaiIdArray");
				StringBuffer fidBuffer = new StringBuffer("0");
				for(String bankuaiId : bankuaiIdArray){
					fidBuffer.append(","+bankuaiId);
				}
				List<Map<String,String>> forumsInfoMapList = dataBaseService.executeQuery("SELECT fid,postcredits,replycredits,digestcredits,postattachcredits,getattachcredits FROM "+tablePrefix+"forumfields WHERE fid IN("+fidBuffer.toString()+")");
				for (String bankuaiId : bankuaiIdArray) {
					boolean bool = true;
					Map<String,String> forumsInfoMap = null;
					for (Map<String,String> tempMap:forumsInfoMapList) {
						if (tempMap.get("fid").equals(bankuaiId)) {
							forumsInfoMap = tempMap;
							break;
						}
					}
					if (forumsInfoMap != null) {
						Map tempMap = null;
						String postcreditsstatus_ = request.getParameter("postcreditsstatus_" + bankuaiId);
						String postcreditsInDS = forumsInfoMap.get("postcredits"); 
						String textName1 = "postcredits_" + bankuaiId;
						tempMap = getResultString(request, postcreditsstatus_,
								postcreditsInDS, creditid, textName1);
						bool = bool&&(Boolean) tempMap.get("bool");
						String postcreditsResult = (String) tempMap.get("result"); 
						String replycreditsstatus_ = request
								.getParameter("replycreditsstatus_" + bankuaiId);
						String replycreditsInDS = forumsInfoMap.get("replycredits"); 
						String textName2 = "replycredits_" + bankuaiId;
						tempMap = getResultString(request, replycreditsstatus_,
								replycreditsInDS, creditid, textName2);
						bool = bool&&(Boolean) tempMap.get("bool");
						String replycreditsResult = (String) tempMap.get("result");
						String digestcreditsstatus_ = request
								.getParameter("digestcreditsstatus_"
										+ bankuaiId);
						String digestcreditsInDS = forumsInfoMap.get("digestcredits");
						String textName3 = "digestcredits_" + bankuaiId;
						tempMap = getResultString(request, digestcreditsstatus_,
								digestcreditsInDS, creditid, textName3);
						bool = bool&&(Boolean) tempMap.get("bool");
						String digestcreditsResult = (String) tempMap.get("result");
						String postattachcreditsstatus_ = request
								.getParameter("postattachcreditsstatus_"
										+ bankuaiId);
						String postattachcreditsInDS = forumsInfoMap.get("postattachcredits"); 
						String textName4 = "postattachcredits_" + bankuaiId;
						tempMap = getResultString(request, postattachcreditsstatus_,
								postattachcreditsInDS, creditid, textName4);
						bool = bool&&(Boolean) tempMap.get("bool");
						String postattachcreditsResult = (String) tempMap.get("result");
						String getattachcreditsstatus_ = request
								.getParameter("getattachcreditsstatus_"
										+ bankuaiId);
						String getattachcreditsInDS = forumsInfoMap.get("getattachcredits"); 
						String textName5 = "getattachcredits_" + bankuaiId;
						tempMap = getResultString(request, getattachcreditsstatus_,
								getattachcreditsInDS, creditid, textName5);
						bool = bool&&(Boolean) tempMap.get("bool");
						String getattachcreditsResult = (String) tempMap.get("result");
						if (!bool) {
							dataBaseService.execute("UPDATE "+tablePrefix+"forumfields SET postcredits='"+postcreditsResult+"',replycredits='"+replycreditsResult+"',digestcredits='"+digestcreditsResult+"',postattachcredits='"+postattachcreditsResult+"',getattachcredits='"+getattachcreditsResult+"' WHERE fid="+bankuaiId);
						}
					}
				}
				request.setAttribute("message", getMessage(request, "a_setting_creditwizard_succeed"));
				request.setAttribute("url_forward",request.getContextPath()+ FinalProperty.FROM_BasicSettingsAction_GO_bksetting+"&extcreditid="+creditId);
				request.getSession().setAttribute("extcreditidFromBankuaiSettingCommit", creditId);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String creditId = request.getParameter("extcreditid");
		if (creditId == null) {
			HttpSession session = request.getSession();
			creditId = (String) session.getAttribute("extcreditidFromBankuaiSettingCommit");
			session.removeAttribute("extcreditidFromBankuaiSettingCommit");
		}
		int creditid = Integer.parseInt(creditId);
		List<BKSettingVO> forumsList = getBKSettingVOList(creditid);
		beforeGoBKsetting_jsp(request, creditid, forumsList);
		return mapping.findForward("bksetting");
	}
	@SuppressWarnings("unchecked")
	private List<BKSettingVO> getBKSettingVOList(int creditid){
		List<BKSettingVO> bKSettingVOList = new ArrayList<BKSettingVO>();
		List<Map<String,String>> forumsMapList = dataBaseService.executeQuery("SELECT f.fid,f.fup,f.type,f.name,ff.postcredits,ff.replycredits,ff.getattachcredits,ff.postattachcredits,ff.digestcredits FROM "+tablePrefix+"forums AS f LEFT JOIN "+tablePrefix+"forumfields AS ff ON f.fid=ff.fid WHERE f.fup<>0");
		if(forumsMapList!=null){
			BKSettingVO bkSettingVO = null;
			BKSettingVO subBKSettingVO = null;
			Map tempMap = null;
			Integer tempByte = null;
			String postcredits = null;
			String replycredits = null;
			String getattachcredits = null;
			String postattachcredits = null;
			String digestcredits = null;
			String forumFid = null;
			String subFup = null;
			String type = null;
			Map<String,String> forumsMap = null;
			int forumsMapListSize = forumsMapList.size();
			for(int i = 0; i<forumsMapListSize;i++){
				forumsMap = forumsMapList.get(i);
				bkSettingVO = new BKSettingVO();
				type = forumsMap.get("type");
				if(type.equals("forum")){
					forumFid = forumsMap.get("fid");
					bkSettingVO.setFid(forumFid);
					bkSettingVO.setFup(forumsMap.get("fup"));
					bkSettingVO.setType(type);
					bkSettingVO.setName(forumsMap.get("name"));
					postcredits = forumsMap.get("postcredits");
					tempMap = dataParse.characterParse(postcredits, false);
					tempByte = (Integer)tempMap.get(creditid);
					if(tempByte!=null){
						bkSettingVO.setPostcredits(tempByte.toString());
					}
					replycredits = forumsMap.get("replycredits");
					tempMap = dataParse.characterParse(replycredits, false);
					tempByte = (Integer)tempMap.get(creditid);
					if(tempByte!=null){
						bkSettingVO.setReplycredits(tempByte.toString());
					}
					getattachcredits= forumsMap.get("getattachcredits");
					tempMap = dataParse.characterParse(getattachcredits, false);
					tempByte = (Integer)tempMap.get(creditid);
					if(tempByte!=null){
						bkSettingVO.setGetattachcredits(tempByte.toString());
					}
					postattachcredits = forumsMap.get("postattachcredits");
					tempMap = dataParse.characterParse(postattachcredits, false);
					tempByte = (Integer)tempMap.get(creditid);
					if(tempByte!=null){
						bkSettingVO.setPostattachcredits(tempByte.toString());
					}
					digestcredits = forumsMap.get("digestcredits");
					tempMap = dataParse.characterParse(digestcredits, false);
					tempByte = (Integer)tempMap.get(creditid);
					if(tempByte!=null){
						bkSettingVO.setDigestcredits(tempByte.toString());
					}
					bKSettingVOList.add(bkSettingVO);
					for(int j = 0;j<forumsMapListSize;j++){
						forumsMap = forumsMapList.get(j);
						subFup = forumsMap.get("fup");
						if(subFup.equals(forumFid)){
							subBKSettingVO = new BKSettingVO();
							subBKSettingVO.setFid(forumsMap.get("fid"));
							subBKSettingVO.setFup(subFup);
							subBKSettingVO.setType(forumsMap.get("type"));
							subBKSettingVO.setName(forumsMap.get("name"));
							postcredits = forumsMap.get("postcredits");
							tempMap = dataParse.characterParse(postcredits, false);
							tempByte = (Integer)tempMap.get(creditid);
							if(tempByte!=null){
								subBKSettingVO.setPostcredits(tempByte.toString());
							}
							replycredits = forumsMap.get("replycredits");
							tempMap = dataParse.characterParse(replycredits, false);
							tempByte = (Integer)tempMap.get(creditid);
							if(tempByte!=null){
								subBKSettingVO.setReplycredits(tempByte.toString());
							}
							getattachcredits= forumsMap.get("getattachcredits");
							tempMap = dataParse.characterParse(getattachcredits, false);
							tempByte = (Integer)tempMap.get(creditid);
							if(tempByte!=null){
								subBKSettingVO.setGetattachcredits(tempByte.toString());
							}
							postattachcredits = forumsMap.get("postattachcredits");
							tempMap = dataParse.characterParse(postattachcredits, false);
							tempByte = (Integer)tempMap.get(creditid);
							if(tempByte!=null){
								subBKSettingVO.setPostattachcredits(tempByte.toString());
							}
							digestcredits = forumsMap.get("digestcredits");
							tempMap = dataParse.characterParse(digestcredits, false);
							tempByte = (Integer)tempMap.get(creditid);
							if(tempByte!=null){
								subBKSettingVO.setDigestcredits(tempByte.toString());
							}
							bKSettingVOList.add(subBKSettingVO);
						}
					}
				}
			}
		}
		return bKSettingVOList;
	}
	@SuppressWarnings("unchecked")
	public ActionForward usergroupSetting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String creditId = request.getParameter("extcreditid");
				List<Map<String,String>> ugInfoMapList = dataBaseService.executeQuery("SELECT groupid,grouptitle,raterange FROM "+tablePrefix+"usergroups ORDER By radminid DESC,groupid DESC");
				for (Map<String,String> ugInfoMap : ugInfoMapList) {
					String groupId = ugInfoMap.get("groupid");
					String checkBox = request.getParameter("raterangestatus_"+ groupId);
					String minValue = request.getParameter("minValue_"+ groupId);
					String maxValue = request.getParameter("maxValue_"+ groupId);
					String hourMaxValue = request.getParameter("hourMaxValue_"+ groupId);
					if (checkBox != null) {
						String grouptitle = ugInfoMap.get("grouptitle");
						minValue = FormDataCheck
								.getNumberFromFormOfDisplayorder(minValue);
						minValue = (Long.valueOf(minValue) < -999 ? "-999" : minValue);
						maxValue = FormDataCheck
								.getNumberFromFormOfDisplayorder(maxValue);
						maxValue = (Long.valueOf(maxValue) > 999 ? "999" : maxValue);
						hourMaxValue = FormDataCheck
								.getNumberFromFormOfDisplayorder(hourMaxValue);
						hourMaxValue = (Long.valueOf(hourMaxValue) > 99999 ? "99999"
								: hourMaxValue);
						Long longMinValue = Long.valueOf(minValue);
						Long longMaxValue = Long.valueOf(maxValue);
						Long longHourMaxValue = Long.valueOf(hourMaxValue);
						if (longMinValue >= longMaxValue
								|| longHourMaxValue < longMaxValue
								|| longHourMaxValue + longMinValue < 0) {
							request.setAttribute("message",getMessage(request, "a_setting_creditwizard_rate_invalid", grouptitle));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
					if (minValue != null) {
						String raterange = ugInfoMap.get("raterange");
						if (raterange != null && !raterange.equals("")) {
							String[] raterangeArray = raterange.split("\t");
							int count = raterangeArray.length / 4;
							boolean haveExtcredit = false;
							int sign = 0;
							for (int j = 0; j < count; j++) {
								String extcreditid = raterangeArray[j * 4];
								if (extcreditid.equals(creditId)) {
									haveExtcredit = true;
									sign = j;
									break;
								}
							}
							if (haveExtcredit) {
								if (checkBox == null) {
									StringBuffer buffer = new StringBuffer();
									for (int j = 0; j < raterangeArray.length; j++) {
										if (j != sign * 4 && j != sign * 4 + 1
												&& j != sign * 4 + 2
												&& j != sign * 4 + 3) {
											buffer.append(raterangeArray[j] + "\t");
										}
									}
									String result = buffer.toString();
									if (result.endsWith("\t")) {
										result = result.substring(0,
												result.length() - 1);
									}
									dataBaseService.execute("UPDATE "+tablePrefix+"usergroups SET raterange='"+result+"' WHERE groupid="+groupId);
								} else {
									String minValueDB = raterangeArray[sign * 4 + 1];
									String maxValueDB = raterangeArray[sign * 4 + 2];
									String hourMaxValueDB = raterangeArray[sign * 4 + 3];
									if (!minValue.equals(minValueDB)
											|| !maxValue.equals(maxValueDB)
											|| !hourMaxValue.equals(hourMaxValueDB)) {
										StringBuffer buffer = new StringBuffer();
										for (int j = 0; j < raterangeArray.length; j++) {
											if (j == sign * 4 + 1) {
												buffer.append(minValue + "\t");
											} else if (j == sign * 4 + 2) {
												buffer.append(maxValue + "\t");
											} else if (j == sign * 4 + 3) {
												buffer.append(hourMaxValue + "\t");
											} else {
												buffer.append(raterangeArray[j] + "\t");
											}
										}
										String result = buffer.toString();
										if (result.endsWith("\t")) {
											result = result.substring(0, result
													.length() - 1);
										}
										dataBaseService.execute("UPDATE "+tablePrefix+"usergroups SET raterange='"+result+"' WHERE groupid="+groupId);
									}
								}
							} else {
								if (checkBox != null) {
									StringBuffer buffer = new StringBuffer(raterange);
									buffer.append("\t" + creditId + "\t" + minValue
											+ "\t" + maxValue + "\t" + hourMaxValue);
									dataBaseService.execute("UPDATE "+tablePrefix+"usergroups SET raterange='"+buffer.toString()+"' WHERE groupid="+groupId);
								}
							}
						} else {
							if (checkBox != null) {
								StringBuffer buffer = new StringBuffer(raterange);
								buffer.append(creditId + "\t" + minValue + "\t"
										+ maxValue + "\t" + hourMaxValue);
								dataBaseService.execute("UPDATE "+tablePrefix+"usergroups SET raterange='"+buffer.toString()+"' WHERE groupid="+groupId);
							}
						}
					}
				}
				request.setAttribute("message", getMessage(request, "a_setting_creditwizard_succeed"));
				request.setAttribute("url_forward",request.getContextPath()+ FinalProperty.FROM_BasicSettingsAction_GO_usergroupsetting);
				request.getSession().setAttribute("extcreditidFromUsergroupSettingCommit", creditId);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String creditId = request.getParameter("extcreditid");
		if (creditId == null) {
			HttpSession session = request.getSession();
			creditId = (String) session.getAttribute(
					"extcreditidFromUsergroupSettingCommit");
			session.removeAttribute(
					"extcreditidFromUsergroupSettingCommit");
		}
		int creditid = Integer.parseInt(creditId);
		List<UsergroupSettingVO> usergroupSettingVOList = getUsergroupSettingVOList(creditId);
		Map extcredits = dataParse.characterParse(ForumInit.settings.get("extcredits_bak"), true);
		Map extcredit = (Map) extcredits.get(creditid);
		request.setAttribute("extcredits", extcredits);
		request.setAttribute("usergroupVOList", usergroupSettingVOList);
		request.setAttribute("extcreditid", creditid);
		request.setAttribute("extcredit", extcredit);
		return mapping.findForward("usergroupsetting");
	}
	public ActionForward resetCredit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "confirmed")){
				String extcreditsName = request.getParameter("extcreditsName");
				String resetValue = request.getParameter("resetValue");
				String creditsformula = ForumInit.settings.get("creditsformula");
				try {
					creSetSer.resetUserCredits(extcreditsName, Integer.valueOf(resetValue),creditsformula);
				} catch (Exception exception) {
					request.setAttribute("message", getMessage(request, "a_setting_database_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				request.setAttribute("message", getMessage(request, "a_setting_creditwizard_succeed"));
				request.setAttribute("url_forward", request.getContextPath() + FinalProperty.FROM_BasicSettingsAction_GO_resetcredit);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String extcreditid = request.getParameter("extcreditid");
		String extcredits = request.getParameter("extcredits");
		int extcreditidInt = Integer.parseInt(extcreditid);
		Map<String,String> settingMap = ForumInit.settings;;
		String initcredits = settingMap.get("initcredits");
		String resetValue = "0";
		if(initcredits!=null&&initcredits.length()>0){
			String[] initcreditArray = initcredits.split(",");
			if(initcreditArray!=null&&initcreditArray.length>extcreditidInt){
				resetValue = initcreditArray[extcreditidInt];
			}
		}
		ResetCredit resetCredit = new ResetCredit(extcredits, resetValue,extcreditid);
		request.setAttribute("resetCredit", resetCredit);
		return mapping.findForward("resetCredit");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toCreditExpression(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String creditsformulanew = request.getParameter("creditsformulanew");
				if (creditsformulanew==null||creditsformulanew.trim().equals("")||!validateExpressions(creditsformulanew)) {
					request.setAttribute("message", getMessage(request, "a_setting_creditsformula_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				Map<String,String> oldSettingMap = ForumInit.settings;
				Map<String,String> newSettingMap = new HashMap<String, String>();
				putValue("creditsformula", creditsformulanew, oldSettingMap, newSettingMap);
				Map<Integer,Map> extcredits = dataParse.characterParse(oldSettingMap.get("extcredits_bak"), false);
				putValue("creditsformulaexp", trunExpression(creditsformulanew,extcredits,request), oldSettingMap, newSettingMap);
				updateSettings(newSettingMap, oldSettingMap);
				if(newSettingMap.get("creditsformula")!=null){
					dataBaseService.execute("UPDATE "+tablePrefix+"members SET credits ="+creditsformulanew);
				}
				request.setAttribute("message", getMessage(request, "a_setting_creditwizard_succeed"));
				request.setAttribute("url_forward", request.getHeader("Referer"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settingMap = ForumInit.settings;
		Map extcredits = dataParse.characterParse(settingMap.get("extcredits_bak"),true);
		request.setAttribute("extcredits", extcredits);
		return mapping.findForward("tocreditExpression");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toCreditPurpose(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String[] creditPurpose = { "creditstax", "exchangemincredits",
						"transfermincredits", "maxincperthread",
						"maxchargespan", "creditstrans" };
				Map<String,String> oldSettingMap = ForumInit.settings;
				Map<String,String> newSettingMap = new HashMap<String, String>();
				for (int i = 0; i < creditPurpose.length; i++) {
					String tempCredit = creditPurpose[i];
					String value = request.getParameter(tempCredit+"new");
					if (value != null) {
						if ("creditstax".equals(tempCredit)) {
							value = FormDataCheck.turnToDoubleString(value);
							Double double1 = Double.valueOf(value);
							if (double1 >= 1) {
								value = "0";
							}
							if (double1 < 0) {
								value = "0";
							}
						} else{
							if (!"creditstrans".equals(tempCredit)) {
								if (!value.equals("INF") && !value.equals("-INF")) {
									value = Double.valueOf(
											FormDataCheck.getDoubleString(value))
											.toString();
								}
								if (value.equals("Infinity")) {
									value = "INF";
								}
								if (value.equals("-Infinity")) {
									value = "-INF";
								}
								if (value.endsWith(".0")) {
									value = value.substring(0, value.length() - 2);
								}
							} 
						}
					} else {
						value = "0";
					}
					putValue(tempCredit, value, oldSettingMap, newSettingMap);
				}
				updateSettings(newSettingMap, oldSettingMap);
				request.setAttribute("message", getMessage(request, "a_setting_creditwizard_succeed"));
				request.setAttribute("url_forward", request.getHeader("Referer"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settingMap = ForumInit.settings;
		Map extcredits = dataParse.characterParse(settingMap.get("extcredits_bak"),  true);
		String creditstransValue = settingMap.get("creditstrans");
		request.setAttribute("creditstransValue", creditstransValue);
		request.setAttribute("extcredits", extcredits);
		return mapping.findForward("tocreditPurpose");
	}
	@SuppressWarnings("unchecked")
	public ActionForward ecommerce(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = {"ec_ratio","ec_mincredits","ec_maxcredits","ec_maxcreditspermonth","maxbiotradesize","tradeimagewidth","tradeimageheight","tradetypes"};
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				String value =null;
				for (String variable: variables) {
					if("tradetypes".equals(variable)) {
						String[] tradetypes=request.getParameterValues("tradetypes");
						Map<String,String> tradetypesmap=null;
						if(tradetypes!=null){
							tradetypesmap=new TreeMap<String, String>();
							List<Map<String,String>> threadtypes=dataBaseService.executeQuery("SELECT typeid,name FROM jrun_threadtypes WHERE typeid IN ("+Common.implodeids(tradetypes)+") ORDER BY displayorder");
							for (Map<String,String> threadtype:threadtypes) {
								tradetypesmap.put(threadtype.get("typeid"), threadtype.get("name"));
							}
						}
						value=tradetypesmap!=null?dataParse.combinationChar(tradetypesmap):"";
					}else{
						value=String.valueOf(Common.toDigit(request.getParameter(variable)));
					}
					this.putValue(variable, value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settings=ForumInit.settings;
		List<Map<String,String>> threadtypes=dataBaseService.executeQuery("SELECT typeid,name FROM jrun_threadtypes WHERE special='1' ORDER BY displayorder");
		if(threadtypes!=null&&threadtypes.size()>0){
			Map<Integer,String> tradetypes=dataParse.characterParse(settings.get("tradetypes"), false);
			StringBuffer tradetypeselect=new StringBuffer();
			for (Map<String, String> threadtype : threadtypes) {
				String typeid=threadtype.get("typeid");
				tradetypeselect.append("<input class=\"checkbox\" type=\"checkbox\" name=\"tradetypes\" value=\""+typeid+"\" "+(tradetypes.containsKey(Integer.valueOf(typeid)) ? "checked" : "")+"> "+threadtype.get("name")+"<br />");
			}
			request.setAttribute("tradetypeselect", tradetypeselect.toString());
		}
		return mapping.findForward("setting_ecommerce");
	}
	public ActionForward serveropti(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = {"gzipcompress", "delayviewcount","nocacheheaders", "transsidstatus", "maxonlines", "onlinehold","loadctrl", "floodctrl", "searchctrl", "maxspm","maxsearchresults" };
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
					String value=request.getParameter(variable);	
					if ("maxonlines".equals(variable)) {
						double maxonlines = 0;
						try {
							maxonlines = Double.valueOf(value);
						} catch (Exception exception) {
							request.setAttribute("message", "ERROR~!");
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						if (maxonlines < 0 || maxonlines > 65535) {
							request.setAttribute("message", getMessage(request, "a_setting_max_olmembers_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						double oldmaxonlines=Double.valueOf(oldSettings.get("maxonlines"));
						if(maxonlines!=oldmaxonlines){
							dataBaseService.runQuery("ALTER TABLE jrun_sessions MAX_ROWS="+maxonlines,true);
						}
						if(maxonlines<oldmaxonlines){
							dataBaseService.runQuery("DELETE FROM jrun_sessions",true);
						}
					} else if ("onlinehold".equals(variable)) {
						long onlinehold=Common.toDigit(value);
						if (onlinehold < 1) {
							onlinehold = 15;
						} 
						value = String.valueOf(onlinehold);
					} else if ("loadctrl|floodctrl|searchctrl|maxspm|maxsearchresults".contains(variable)) {
						value = Double.valueOf(FormDataCheck.getDoubleString(value)).toString();
						if (value.endsWith(".0")) {
							value = value.substring(0, value.length() - 2);
						}
					}
					this.putValue(variable, value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				String onlinehold = settings.get("onlinehold");
				if(onlinehold!=null){
					oldSettings.put("onlinehold", String.valueOf((Integer.parseInt(onlinehold)*60)));
				}
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settingMap = ForumInit.settings;
		String onlineholdString = settingMap.get("onlinehold");
		if(onlineholdString!=null){
			double onlinehold = Double.parseDouble(onlineholdString);
			onlinehold = (onlinehold / 60);
			int onlineholdInt = (int)onlinehold;
			if(onlineholdInt == onlinehold){
				onlineholdString = String.valueOf(onlineholdInt);
			}else{
				onlineholdString = String.valueOf(onlinehold);
			}
			request.setAttribute("onlinehold", onlineholdString);
		}
		return mapping.findForward("setting_serveropti");
	}
	@SuppressWarnings("unchecked")
	public ActionForward mail(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		Map<String,String> oldSettings=ForumInit.settings;
		try{
			if(submitCheck(request, "settingsubmit")||submitCheck(request, "mailcheck")){
				String operation=request.getParameter("operation");
				if("mailcheck".equals(operation)){
					int timestamp = (Integer)(request.getAttribute("timestamp"));
					String timeoffset=(String)oldSettings.get("timeoffset");
					String date=Common.gmdate("yyyy-MM-dd HH:mm:ss", timestamp, timeoffset);;
					String host = request.getParameter("server");
					int port = Common.toDigit(request.getParameter("port"));
					String auth = request.getParameter("auth");
					String auth_username = request.getParameter("auth_username");
					String auth_password = request.getParameter("auth_password");
					String test_from = request.getParameter("test_from");
					String test_to = request.getParameter("test_to");
					String title = getMessage(request, "a_setting_mailcheck_title_2") +" @ "+ date;
					String textBody = getMessage(request, "a_setting_mailcheck_message_2") +" "+ test_from + getMessage(request, "a_setting_mailcheck_date") +" "+ date;
					Mail mail=new Mail(host, port, auth, auth_username, auth_password,"1");
					String[] test_tos=test_to.split(",");
					String alertmsg = mail.sendMessage(test_from, test_tos[0], title, getMessage(request, "a_setting_mailcheck_method_1")+"\n\n\n"+textBody, null);
					alertmsg = mail.sendMessage(test_from, test_to, title,getMessage(request, "a_setting_mailcheck_method_2")+"\n\n\n"+textBody, null);
					if (alertmsg==null) {
						alertmsg=getMessage(request,"a_setting_mailcheck_success_1")+" "+title+" "+getMessage(request, "a_setting_mailcheck_success_2");
					}else {
						alertmsg=getMessage(request, "a_setting_mailcheck_error")+alertmsg;
					}
					request.setAttribute("message", alertmsg);
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}else{
					String[] variables = {"mailsend", "server", "port", "auth","from", "auth_username", "auth_password", "maildelimiter","mailusername", "sendmail_silent" };
					Map<String,String> settings=new HashMap<String,String>();
					Map<String,String> mail = new HashMap<String,String>();
					for (String variable: variables) {
						mail.put(variable, request.getParameter(variable));
					}
					this.putValue("mail", dataParse.combinationChar(mail), oldSettings, settings);
					this.updateSettings(settings,oldSettings);
					return this.getForward(mapping, request);
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> mail=dataParse.characterParse(oldSettings.get("mail"), false);
		request.setAttribute("mail", mail);
		return mapping.findForward("setting_mail");
	}
	public ActionForward seccode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = { "seccodestatus", "seccodedata" };
				String secdatas[] = { "minposts", "loginfailedcount", "width", "height","type", "background", "adulterate", "ttf", "angle", "color","size", "shadow", "animator" };
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				String value =null;
				for (String variable: variables) {
					if ("seccodestatus".equals(variable)) {
						int sum = 0;
						for (int j = 0; j < 5; j++) {
							String seccodestatus = request.getParameter(variable + j);
							if (seccodestatus != null){
								sum = sum + Integer.valueOf(seccodestatus);
							}
						}
						value = sum + "";
					} else {
						HashMap<String, String> seccodedata = new HashMap<String, String>();
						for (String secdata:secdatas) {
							String secvalue = request.getParameter(secdata);
							if("width".equals(secdata)){
								secvalue=Common.range(Common.intval(secvalue), 200, 100)+"";
							}else if("height".equals(secdata)){
								secvalue=Common.range(Common.intval(secvalue), 80, 50)+"";
								secvalue = FormDataCheck.getNumberFromForm(secvalue);
							}
							seccodedata.put(secdata, secvalue);
						}
						value = dataParse.combinationChar(seccodedata);
					}
					this.putValue(variable, value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settings=ForumInit.settings;
		int seccodestatus=Integer.valueOf(settings.get("seccodestatus"));
		Common.setChecked(request, "seccodestatus", 5, seccodestatus);
		request.setAttribute("seccodedata", dataParse.characterParse(settings.get("seccodedata"), false));
		return mapping.findForward("setting_seccode");
	}
	@SuppressWarnings("unchecked")
	public ActionForward secqaa(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = { "status", "minposts" };
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				Map<String, Object> hm = new HashMap<String, Object>();
				for (String variable: variables) {
					if ("status".equals(variable)) {
						int sum = 0;
						for (int j = 0; j < 3; j++) {
							String par = request.getParameter(variable + j);
							if (par != null)
								sum = sum + Integer.parseInt(par);
						}
						hm.put(variable, sum);
					} else {
						hm.put(variable, request.getParameter(variable));
					}
				}
				this.putValue("secqaa", dataParse.combinationChar(hm), oldSettings, settings);
				this.updateSettings(settings,oldSettings);
				String[] delete=request.getParameterValues("delete");
				if(delete!=null){
					dataBaseService.runQuery("DELETE FROM jrun_itempool WHERE id IN ("+Common.implodeids(delete)+")", true);
				}
				String[] ids=request.getParameterValues("ids");
				if(ids!=null){
					for(String id:ids){
						String question=request.getParameter("question["+id+"]").trim();
						String answer=Common.htmlspecialchars(request.getParameter("answer["+id+"]").trim());
						if(!"".equals(question)&&!"".equals(answer)){
							dataBaseService.runQuery("UPDATE jrun_itempool SET question='"+Common.addslashes(question)+"', answer='"+Common.addslashes((answer.length()>50?answer.substring(0,50):answer))+"' WHERE id='"+id+"'", true);
						}
					}
				}
				String[] newquestions=request.getParameterValues("newquestions");
				String[] newanswers=request.getParameterValues("newanswers");
				if(newquestions!=null&&newanswers!=null){
					StringBuffer sql=new StringBuffer();
					sql.append("INSERT INTO	jrun_itempool (type,question, answer) VALUES ");
					boolean sign=false;
					int length=newquestions.length-1;
					for(int i=0;i<length;i++){
						if(!"".equals(newquestions[i])&&!"".equals(newanswers[i])){
							sql.append("('0','"+Common.addslashes(newquestions[i])+"', '"+Common.addslashes((newanswers[i].length()>50?newanswers[i].substring(0,50):newanswers[i]))+"'),");
							sign=true;
						}
					}
					if(sign){
						sql.deleteCharAt(sql.length()-1);
						dataBaseService.runQuery(sql.toString(),true);
					}
				}
				Cache.updateCache("secqaa");
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settings=ForumInit.settings;
		Map<String,Object> secqaa=dataParse.characterParse(settings.get("secqaa"), false);
		Common.setChecked(request, "status", 3, (Integer)secqaa.get("status"));
		request.setAttribute("secqaa", secqaa);
		List<Map<String, String>> count = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_itempool");
		int secqaanums = Integer.valueOf(count.get(0).get("count"));
		int perpage = 10;
		int page =Math.max(Common.intval(request.getParameter("page")), 1);
		Map<String,Integer> multiInfo=Common.getMultiInfo(secqaanums, perpage, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		Map<String,Object> multi=Common.multi(secqaanums, perpage, page, "admincp.jsp?action=settings&do=secqaa", 0, 10, true, false, null);
		request.setAttribute("multi", multi);
		List<Map<String,String>> itempools=dataBaseService.executeQuery("SELECT * FROM jrun_itempool LIMIT "+start_limit+","+perpage); 
		request.setAttribute("itempools", itempools);
		return mapping.findForward("setting_secqaa");
	}
	public ActionForward datetime(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = { "dateformat", "timeformat", "timeoffset","userdateformat", "visitbanperiods", "postbanperiods","postmodperiods", "attachbanperiods", "searchbanperiods",};
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
					String value = request.getParameter(variable);
					if("dateformat".equals(variable)||"userdateformat".equals(variable)){
						value = Common.htmlspecialchars(value);
					}else if("timeformat".equals(variable)){
						oldSettings.put("gtimeformat", "1".equals(value) ? "hh:mm a" : "HH:mm");
					}else if("visitbanperiods|postbanperiods|postmodperiods|attachbanperiods|searchbanperiods".contains(variable)){
						String[] periods=value.split("\n");
						StringBuffer periodTemp = new StringBuffer();
						for(String period:periods){
							if(period.trim().matches("^\\d{1,2}\\:\\d{2}\\-\\d{1,2}\\:\\d{2}$")){
								periodTemp.append("\n"+period.trim());
							}
						}
						value =(periodTemp.length()>0?periodTemp.substring(1):"");
					}
					this.putValue(variable, value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		request.setAttribute("timeZoneIDs", Common.getTimeZoneIDs());
		return mapping.findForward("setting_datetime");
	}
	public ActionForward permissions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[]={"memliststatus","reportpost","minpostsize","maxpostsize","maxfavorites","maxsubscriptions","maxavatarsize","maxavatarpixel","maxpolloptions","edittimelimit","editedby","karmaratelimit","modratelimit","dupkarmarate"};					
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
				    String value=request.getParameter(variable);	    
				    if(Common.matches(variable, "^(edittimelimit|karmaratelimit)$")){
				    	if(value!=null){
				    		value = Double.valueOf(FormDataCheck.getDoubleString(value)).toString();
				    		if(value.contains(".")){
				    			StringBuffer buffer = new StringBuffer(value);
				    			while(true){
				    				if(buffer.charAt(buffer.length()-1)=='.'){
				    					buffer.deleteCharAt(buffer.length()-1);
				    					break;
				    				}else if(buffer.charAt(buffer.length()-1)=='0'){
				    					buffer.deleteCharAt(buffer.length()-1);
				    				}else{
				    					break;
				    				}
				    			}
				    			value = buffer.toString();
				    		}
				    	}
				    }else if("|minpostsize|maxpostsize|maxavatarsize|maxavatarpixel|maxpolloptions|maxfavorites|maxsubscriptions|".contains(variable)){
				    	value = Common.toDigit(value)+"";
				    }
				    this.putValue(variable, value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		return mapping.findForward("setting_permissions");
	}
	@SuppressWarnings("unchecked")
	public ActionForward attachments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = {"attachdir", "attachurl", "attachimgpost","attachrefcheck", "attachsave", "imagelib", "imageimpath","thumbstatus", "thumbwidth", "thumbheight", "watermarkstatus","watermarkminwidth", "watermarkminheight", "watermarktype","watermarktrans", "watermarkquality"  };
				String watermarktexts[] = {"text", "fontpath", "size", "angle","color", "shadowx", "shadowy", "shadowcolor"};
				String ftps [] = {"on","ssl","host","port","username","password","pasv","attachdir","attachurl","timeout","mirror","allowedexts","disallowedexts","minsize","hideurl","isinstall","activeurl"};
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
					String value = request.getParameter(variable);
					if("thumbwidth".equals(variable)||"thumbheight".equals(variable)||"watermarkminwidth".equals(variable)||"watermarkminheight".equals(variable)){
						value = Common.toDigit(value)+"";
					}else if("watermarktrans".equals(variable)||"watermarkquality".equals(variable)){
						value = Double.valueOf(FormDataCheck.getDoubleString(value)).toString();
						if(value.endsWith(".0")){
							value = value.substring(0,value.length()-2);
						}
					}
					this.putValue(variable, value, oldSettings, settings);
				}
				Map watermarktext = new HashMap();
				String realPath = JspRunConfig.realPath;
				for (String variable: watermarktexts) {
					String temp = "watermarktext["+ variable + "]";
					if("watermarktext[size]".equals(temp)||"watermarktext[angle]".equals(temp)||"watermarktext[shadowx]".equals(temp)||"watermarktext[shadowy]".equals(temp)){
						watermarktext.put(variable, Common.toDigit(request.getParameter(temp)));
					}else{
						String watervalue = request.getParameter(temp).trim();
						if(!"".equals(watervalue)&&"watermarktext[fontpath]".equals(temp)){
							boolean bool = false;
							File file = new File(realPath+"/images/fonts/ch/"+watervalue);
							if(file.isFile()){
								bool = true;
							}else{
								file = new File(realPath+"/images/fonts/en/"+watervalue);
								if(file.isFile()){
									bool = true;
								}
							}
							file=null;
							if(!bool){
								request.setAttribute("message", getMessage(request, "a_setting_watermarkpreview_fontpath_error"));
								request.setAttribute("return", true);
								return mapping.findForward("message");
							}
						}
						watermarktext.put(variable, watervalue);
					}
				}
				this.putValue("watermarktext",dataParse.combinationChar(watermarktext), oldSettings, settings);
				String ftp = oldSettings.get("ftp");
				Map<String,String> ftpmap = dataParse.characterParse(ftp, false);
				for(String variable:ftps){
					String temp = "ftp["+variable+"]";
					String ftpvalue = request.getParameter(temp);
					if(variable.equals("password")){
						if(!ftpvalue.matches(".{1}\\*\\*\\*\\*\\*\\*\\*\\*.{1}")){
							ftpvalue = Common.authcode(ftpvalue, "ENCODE", Md5Token.getInstance().getLongToken(oldSettings.get("authkey")),"utf-8");
							ftpmap.put(variable, ftpvalue);
						}
					}else{
						ftpmap.put(variable, ftpvalue);
					}
				}
				this.putValue("ftp", dataParse.combinationChar(ftpmap), oldSettings, settings);
				this.updateSettings(settings,oldSettings);
				ftputil.setFtpValues(ftpmap.get("host"),ftpmap.get("username"),Common.authcode(ftpmap.get("password"),"DECODE",Md5Token.getInstance().getLongToken(oldSettings.get("authkey")),"utf-8"),ftpmap.get("attachdir"), Common.toDigit(ftpmap.get("port")),ftpmap.get("ssl"), 0,ftpmap.get("pasv"));
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String watermarktext =ForumInit.settings.get("watermarktext");
		request.setAttribute("watermarktext",dataParse.characterParse(watermarktext, false));
		String ftp = ForumInit.settings.get("ftp");
		String authorkey = ForumInit.settings.get("authkey");
		Map<String,String>ftpMap = dataParse.characterParse(ftp, false);
		String passwords = ftpMap.get("password")==null||ftpMap.get("password").equals("")?"":Common.authcode(ftpMap.get("password"), "DECODE", Md5Token.getInstance().getLongToken(authorkey),"utf-8");
		ftpMap.put("password", passwords.equals("")?"":passwords.substring(0,1)+"********"+passwords.substring(passwords.length()-1));
		request.setAttribute("ftp", ftpMap);
		return mapping.findForward("setting_attachments");
	}
	public ActionForward imagepreview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		boolean isfounder = (Boolean)request.getAttribute("isfounder");
		if(!isfounder){
			request.setAttribute("message", getMessage(request, "a_setting_not_createmen_access"));
			return mapping.findForward("message");
		}
		String previewthumb=request.getParameter("previewthumb");
		if("yes".equals(previewthumb)){
			String thumbstatus = request.getParameter("thumbstatus");
			if (thumbstatus == null || thumbstatus.equals("0")) {
				request.setAttribute("message", getMessage(request, "a_setting_thumbpreview_error"));
				return mapping.findForward("message");
			} else {
				String realPath=JspRunConfig.realPath;
				String imagePath = realPath+"images/admincp/watermarkpreview.jpg";
				File file = new File(imagePath);
				if (!file.isFile()) {
					request.setAttribute("message", getMessage(request, "a_setting_thumbpreview_createerror"));
					return mapping.findForward("message");
				} else {
					String imagelib = request.getParameter("imagelib");
					int thumbwidth = Common.toDigit(request.getParameter("thumbwidth"));
					int thumbheight = Common.toDigit(request.getParameter("thumbheight"));
					if (thumbwidth==0 || thumbheight==0) {
						request.setAttribute("message", getMessage(request, "a_setting_thumbpreview_createerror"));
						return mapping.findForward("message");
					} else if (imagelib.equals("0")) {
						try {
							String result=ImageUtil.createZoomImage(imagePath,realPath+"/forumdata/watermark_temp.jpg", thumbwidth, thumbheight);
							if(result!=null){
								String[] mess = result.split("\t");
								if(mess.length==3){
									request.setAttribute("message", getMessage(request, mess[0], mess[1],mess[2]));
								}else{
									request.setAttribute("message_key", result);
								}
								return mapping.findForward("message");
							}
							request.setAttribute("imageUri", request.getContextPath()+"/forumdata/watermark_temp.jpg");
						}catch (Exception exception) {
							exception.printStackTrace();
						}
					} else {
						request.setAttribute("message", getMessage(request, "a_setting_imageMageck_unuse"));
						return mapping.findForward("message");
					}
				}
			}
			request.setAttribute("pageInfo", getMessage(request, "a_setting_imagepreview_thumb"));
		}else{
			String watermarkstatus = request.getParameter("watermarkstatus");
			if (watermarkstatus == null || watermarkstatus.equals("0")) {
				request.setAttribute("message", getMessage(request,"a_setting_watermarkpreview_error"));
				return mapping.findForward("message");
			} else {
				String watermarkminwidth = request.getParameter("watermarkminwidth");
				int watermarkMinWidth = Common.toDigit(watermarkminwidth);
				String watermarkminheight=request.getParameter("watermarkminheight");
				int watermarkminHeight = Common.toDigit(watermarkminheight);
				String watermarktrans = request.getParameter("watermarktrans");
				watermarktrans = FormDataCheck.getDoubleString(watermarktrans);
				float transparency =  Double.valueOf(watermarktrans).floatValue();
				String watermarkquality = request.getParameter("watermarkquality");
				watermarkquality = FormDataCheck.getDoubleString(watermarkquality);
				float imageQuality = Double.valueOf(watermarkquality).floatValue();
				String realPath=JspRunConfig.realPath;
				String baseImagePath = realPath+"images/admincp/watermarkpreview.jpg";
				String outputImagePath =realPath+"/forumdata/watermark_temp.jpg";
				String watermarktype = request.getParameter("watermarktype");
				if (watermarktype.equals("2")) {
					String watermarktext_text = request.getParameter("watermarktext[text]");
					String watermarktext_fontpath = request.getParameter("watermarktext[fontpath]");
					String fontpath1 = realPath+"images/fonts/ch/"+watermarktext_fontpath;
					String fontpath2 = realPath+"images/fonts/en/"+watermarktext_fontpath;
					String watermarktext_fontpath_true = null;
					if(!watermarktext_fontpath.endsWith(".ttf")){
						request.setAttribute("message", getMessage(request, "a_setting_watermarkpreview_fontpath_error"));
						return mapping.findForward("message");
					}else {
						File file1 = new File(fontpath1);
						File file2 = new File(fontpath2);
						if(file1.isFile()){
							watermarktext_fontpath_true = fontpath1;
						}else if(file2.isFile()){
							watermarktext_fontpath_true = fontpath2;
						}else {
							request.setAttribute("message", getMessage(request, "a_setting_watermarkpreview_fontpath_error"));
							return mapping.findForward("message");
						}
					}
					int watermarktext_sizeInt =Common.toDigit(request.getParameter("watermarktext[size]"));
					int watermarktext_angleInt = Common.toDigit(request.getParameter("watermarktext[angle]"));
					String watermarktext_color = request.getParameter("watermarktext[color]");
					int watermarktext_shadowxInt = Common.toDigit(request.getParameter("watermarktext[shadowx]"));
					int watermarktext_shadowyInt = Common.toDigit(request.getParameter("watermarktext[shadowy]"));
					String watermarktext_shadowcolor = request.getParameter("watermarktext[shadowcolor]");
					String watermarktext_translatex = request.getParameter("watermarktext[translatex]");
					String watermarktext_translatey = request.getParameter("watermarktext[translatey]");
					String watermarktext_skewx = request.getParameter("watermarktext[skewx]");
					String watermarktext_skewy = request.getParameter("watermarktext[skewy]");
					try{
						String result=ImageUtil.createWaterMarkWithCharacter(baseImagePath, outputImagePath,watermarkMinWidth, watermarktext_text, watermarktext_fontpath_true, watermarktext_sizeInt, watermarktext_angleInt, watermarktext_color, watermarktext_shadowxInt, watermarktext_shadowyInt, watermarktext_shadowcolor, watermarktext_translatex, watermarktext_translatey, watermarktext_skewx,watermarktext_skewy,imageQuality, watermarkstatus, transparency);
						if(result!=null){
							String[] mess = result.split("\t");
							if(mess.length==3){
								request.setAttribute("message", getMessage(request, mess[0], mess[1],mess[2]));
							}else{
								request.setAttribute("message_key", result);
							}
							return mapping.findForward("message");
						}else{
							request.setAttribute("imageUri", request.getContextPath()+"/forumdata/watermark_temp.jpg");
						}
					}catch(IOException exception){
						request.setAttribute("message", getMessage(request, "a_setting_watermark_error"));
						return mapping.findForward("message");
					}
				} else {
					String waterImagePath = null;
					if (watermarktype.equals("1")) {
						waterImagePath = realPath+"images/common/watermark.png";
					} else {
						waterImagePath = realPath+"images/common/watermark.gif";
					}
					String imagelib = request.getParameter("imagelib");
					if (imagelib.equals("0")) {
						try {
							String result=ImageUtil.createWaterMarkWithImage(baseImagePath,outputImagePath, waterImagePath,watermarkstatus, transparency, imageQuality,watermarkMinWidth,watermarkminHeight);
							if(result!=null){
								String[] mess = result.split("\t");
								if(mess.length==3){
									request.setAttribute("message", getMessage(request, mess[0], mess[1],mess[2]));
								}else{
									request.setAttribute("message_key", result);
								}
								return mapping.findForward("message");
							}else{
								request.setAttribute("imageUri", request.getContextPath()+"/forumdata/watermark_temp.jpg");
							}
						}catch (IOException exception) {
							request.setAttribute("message", getMessage(request, "a_setting_watermark_error"));
							return mapping.findForward("message");
						}
					} else {
						request.setAttribute("message", getMessage(request, "a_setting_imageMageck_unuse"));
						return mapping.findForward("message");
					}
				}
			}
			request.setAttribute("pageInfo", getMessage(request, "a_setting_imagepreview_watermark"));
		}
		return mapping.findForward("showimage");
	}
	@SuppressWarnings("unchecked")
	public ActionForward language(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> oldSettings=ForumInit.settings;
		Map<Integer,Map<String,Object>> languages=dataParse.characterParse(oldSettings.get("languages"), true);
		if(languages==null){
			languages=new TreeMap<Integer,Map<String,Object>>();
		}
		try{
			if(submitCheck(request, "languagesubmit")){
				String[] delete=request.getParameterValues("delete");
				if(delete!=null){
					for (String obj : delete) {
						languages.remove(Integer.valueOf(obj));
					}
				}
				Map<Integer,Map<String,Object>> newlanguages=new TreeMap<Integer,Map<String,Object>>();
				if(languages.size()>0){
					int olddefault=Common.intval(request.getParameter("default"));
					Iterator<Entry<Integer,Map<String,Object>>> keys=languages.entrySet().iterator();
					while (keys.hasNext()) {
						Entry<Integer,Map<String,Object>> e = keys.next();
						Integer key = e.getKey();
						int displayorder=Common.toDigit(request.getParameter("displayorder_"+key));
						if(newlanguages.keySet().contains(displayorder)){
							request.setAttribute("message", getMessage(request, "a_setting_order_cannot_repeat"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						Map<String,Object> language=e.getValue();
						language.put("available", Common.intval(request.getParameter("available_"+key)));
						language.put("name", request.getParameter("name_"+key));
						language.put("language", request.getParameter("language_"+key));
						language.put("default", olddefault==key?1:0);
						newlanguages.put(displayorder, language);
					}
				}
				Map<String,String> settings=new HashMap<String,String>();
				String newname=request.getParameter("newname");
				if(newname.length()>0){
					int newdisplayorder=Common.toDigit(request.getParameter("newdisplayorder"));
					if(newlanguages.keySet().contains(newdisplayorder)){
						request.setAttribute("message", getMessage(request, "a_setting_order_cannot_repeat"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					int newavailable=Common.intval(request.getParameter("newavailable"));
					String newlanguage=request.getParameter("newlanguage");
					Map<String,Object> language=new HashMap<String,Object>();
					language.put("available", newavailable);
					language.put("name", newname);
					language.put("language", newlanguage);
					language.put("default", 0);
					newlanguages.put(newdisplayorder, language);
				}
				this.putValue("languages", dataParse.combinationChar(newlanguages), oldSettings, settings);
				this.updateSettings(settings,oldSettings);
				request.setAttribute("message", getMessage(request, "a_setting_language_success"));
				request.setAttribute("url_forward", "admincp.jsp?action=settings&do=language");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		request.setAttribute("languages",languages);
		return mapping.findForward("setting_language");
	}
	public ActionForward wap(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[]={"wapstatus","wapregister","wapcharset","waptpp","wapppp","wapdateformat","wapmps"};
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
					String value=request.getParameter(variable);	
					if("waptpp".endsWith(variable)||"wapppp".endsWith(variable)||"wapmps".endsWith(variable)){
						value=Common.toDigit(value)+"";
					}else if("wapdateformat".equals(variable)){
						value = Common.htmlspecialchars(value);
					}
					this.putValue(variable, value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		return mapping.findForward("setting_wap");
	}
	public ActionForward space(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[] = {"cachelife", "limitmythreads","limitmyreplies", "limitmyrewards", "limitmytrades","limitmyvideos", "limitmyblogs", "limitmyfriends","limitmyfavforums", "limitmyfavthreads", "textlength" };
				Map<String, String> hm = new HashMap<String, String>();
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
					hm.put(variable, request.getParameter(variable));
				}
				this.putValue("spacestatus", request.getParameter("spacestatus"), oldSettings, settings);
				this.putValue("spacedata", dataParse.combinationChar(hm), oldSettings, settings);
				this.updateSettings(settings,oldSettings);
				return this.getForward(mapping, request);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settings=ForumInit.settings;
		request.setAttribute("spacedata", dataParse.characterParse(settings.get("spacedata"), false));
		return mapping.findForward("setting_space");
	}
	private List<UsergroupSettingVO> getUsergroupSettingVOList(String creditId) {
		List<UsergroupSettingVO> usergroupSettingVOList = new ArrayList<UsergroupSettingVO>();
		List<Map<String,String>> ugInfoMapList = dataBaseService.executeQuery("SELECT groupid,grouptitle,raterange FROM "+tablePrefix+"usergroups ORDER By radminid DESC,groupid DESC");
		for (Map<String,String> ugInfoMap : ugInfoMapList) {
			String groupId = ugInfoMap.get("groupid");
			String grouptitle = ugInfoMap.get("grouptitle");
			UsergroupSettingVO usergroupSettingVO = new UsergroupSettingVO();
			usergroupSettingVO.setGroupid(groupId);
			usergroupSettingVO.setGrouptitle(grouptitle);
			String raterange = ugInfoMap.get("raterange");
			String checked = "";
			String minValue = "";
			String maxValue = "";
			String hourMaxValue = "";
			if (raterange != null && !raterange.equals("")) {
				String regex = "\t";
				String[] temp = raterange.split(regex);
				int len = temp.length;
				int cont = len / 4;
				for (int j = 0; j < cont; j++) {
					if (temp[j * 4].equals(creditId)) {
						checked = "checked";
						minValue = temp[j * 4 + 1];
						maxValue = temp[j * 4 + 2];
						hourMaxValue = temp[j * 4 + 3];
						break;
					}
				}
			}
			usergroupSettingVO.setHourMaxValue(hourMaxValue);
			usergroupSettingVO.setMaxValue(maxValue);
			usergroupSettingVO.setMinValue(minValue);
			usergroupSettingVO.setChecked(checked);
			usergroupSettingVOList.add(usergroupSettingVO);
		}
		return usergroupSettingVOList;
	}
	@SuppressWarnings("unchecked")
	private Map<Integer,Map<String,Object>> encapsulationCredit(HttpServletRequest request,Integer creditid,String extcredits) {
		Map<Integer,Map<String,Object>> extcreditTotal = dataParse.characterParse(extcredits, true);
		String extcreditData[] = { "title", "unit", "ratio","lowerlimit","available", "showinthread", "allowexchangeout","allowexchangein" };
		Map<String,Object> map = new TreeMap<String,Object>();
		for (int i = 0; i < extcreditData.length; i++) {
			String creditValue = request.getParameter(extcreditData[i]);
			if ("ratio".equals(extcreditData[i]) || "lowerlimit".equals(extcreditData[i])) {
				if (!FormDataCheck.isNum(creditValue)) {
					creditValue = "0";
				}
				if("ratio".equals(extcreditData[i])){
					map.put("ratio", Double.valueOf(creditValue));
				}else{
					map.put("lowerlimit", Integer.valueOf(creditValue));
				}
			}else{
				map.put(extcreditData[i], creditValue);
			}
		}
		extcreditTotal.put(creditid, map);
		return extcreditTotal;
	}
	@SuppressWarnings("unchecked")
	private Map<String,Map<Integer,Object>> encapsulationCreditspolicy(HttpServletRequest request,Integer creditid,Map<String,String> settingMap) {
		String creditspolicyValue = settingMap.get("creditspolicy");
		Map<String,Map<Integer,Object>> creditspolicyMap = dataParse.characterParse(creditspolicyValue,  true);
		String creditspolicyData[] = { "post", "reply", "digest", "postattach",
				"getattach", "pm", "search", "promotion_visit",
				"promotion_register", "tradefinished", "votepoll", "lowerlimit" };
		String creditspolicyFRequest = null;
		for (int i = 0; i < creditspolicyData.length; i++) {
			creditspolicyFRequest = request.getParameter(creditspolicyData[i]);
			Integer value = 0;
			try{
				value = Integer.valueOf(creditspolicyFRequest);
			}catch(NumberFormatException exception){
				value = 0;
			}
			creditspolicyMap.get(creditspolicyData[i]).put(creditid, value);
		}
		return creditspolicyMap;
	}
	private String encapsulationInitcredits(String initcredits,Integer creditid,Map<String,String> settingMap) {
		String initString = settingMap.get("initcredits");
		String[] init = initString.split(",");
		StringBuffer initValue = new StringBuffer();
		if (init != null) {
			for (int i = 0; i < init.length; i++) {
				if (i == creditid){
					initValue.append(initcredits+",");
				}else{
					initValue.append(init[i]+",");
				}
			}
		}
		int initValueLen = initValue.length();
		return initValueLen>0?initValue.substring(0, initValueLen-1):"";
	}
	private String getTrueString(String num) {
		if(null == num)
			return null;
		num = FormDataCheck.getNumberFromFormOfDisplayorder(num);
		long longNum = Long.valueOf(num);
		if (longNum > 99) {
			num = "99";
		}
		if (longNum < -99) {
			num = "-99";
		}
		return num;
	}
	private boolean validateExpressions(String expression) {
		String reg = "[\\*/\\+]";
		String reg2 = "\\-";
		String reg3 = "[\\-]?[\\(]*[\\-]?extcredits[1-8][\\)]*";
		String reg4 = "\\(";
		String reg5 = "[\\-]?[\\(]*[\\-]?[0-9]+[\\)]*";
		String reg5_ = "[\\-]?[\\(]*[\\-]?[0-9]*.[0-9]+[\\)]*";
		String reg6 = "[\\-]?[\\(]*[\\-]?posts[\\)]*";
		String reg7 = "[\\-]?[\\(]*[\\-]?digestposts[\\)]*";
		String reg8 = "[\\-]?[\\(]*[\\-]?oltime[\\)]*";
		String reg9 = "[\\-]?[\\(]*[\\-]?pageviews[\\)]*";
		String[] temp = expression.split(reg); 
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].trim().equals("") || temp[i].trim().endsWith("-")) {
				return false;
			}
		}
		for (int i = 0; i < temp.length; i++) {
			String[] temp2 = temp[i].trim().split(reg2);
			int sign = 0;
			for (int j = 0; j < temp2.length; j++) {
				String temp2v = temp2[j].trim();
				if ((!temp2v.matches(reg3) && !temp2v.matches(reg4)
						&& !temp2v.matches(reg5) && !temp2v.matches(reg5_)
						&& !temp2v.matches(reg6) && !temp2v.matches(reg7)
						&& !temp2v.matches(reg8) && !temp2v.matches(reg9))
						&& !temp2v.equals("")) {
					return false;
				}
				if (sign == 2) {
					return false;
				}
				if (temp2v.equals("")) {
					sign++;
				}
				if (sign == 1 && !temp2v.equals("")) {
					sign--;
				}
			}
		}
		boolean boolFront = true;
		StringBuffer buffer = new StringBuffer(expression);
		int chrIndex = 0;
		for (int i = 0; i < buffer.length(); i++) {
			char chr = buffer.charAt(i);
			if (boolFront && chr == ')') {
				return false;
			}
			if (chr == '(') {
				chrIndex = i;
				boolFront = false;
			}
			if (!boolFront && chr == ')') {
				buffer.replace(chrIndex, chrIndex + 1, "V");
				buffer.replace(i, i + 1, "V");
				boolFront = true;
				i = -1;
			}
		}
		if (!boolFront) {
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	private String clearMap(String targetString) {
		Map temp = dataParse.characterParse(targetString,  true);
		Set keySet = temp.keySet();
		Iterator iterator = keySet.iterator();
		Map temp2 = new TreeMap();
		while (iterator.hasNext()) {
			temp2.put(iterator.next(), null);
		}
		return targetString = dataParse.combinationChar(temp2);
	}
	@SuppressWarnings("unchecked")
	private void beforeGoBKsetting_jsp(HttpServletRequest request, int creditid,
			List<BKSettingVO> forumsList) {
		Map extcredits = dataParse.characterParse(ForumInit.settings.get("extcredits_bak"), true);
		Map extcredit = (Map) extcredits.get(creditid);
		request.setAttribute("extcredits", extcredits);
		request.setAttribute("extcreditid", creditid);
		request.setAttribute("extcredit", extcredit);
		request.setAttribute("bankuaiList", forumsList);
	}
	@SuppressWarnings("unchecked")
	private Map getResultString(HttpServletRequest request,String checkBoxValue, String dsStringValue,int creditid,String textName) {
		String result = null;
		boolean bool = true;
		if (checkBoxValue != null) {
			String postcredits_ = request.getParameter(textName);
			postcredits_ = FormDataCheck
					.getNumberFromFormOfDisplayorder(postcredits_);
			Integer creditsI = null;
			if (Long.valueOf(postcredits_) > 99) {
				creditsI = 99;
			} else if (Long.valueOf(postcredits_) < -99) {
				creditsI = -99;
			}else{
				creditsI = Integer.valueOf(postcredits_);
			}
			Map postcreditsMap = null;
			if (!dsStringValue.equals("")) {
				postcreditsMap = dataParse.characterParse(dsStringValue,true);
				Object postcreditsOB = postcreditsMap.get(creditid);
				Integer postcredits = postcreditsOB == null ? null : Integer.valueOf(String.valueOf(postcreditsOB)) ;
				if (postcredits == null || !creditsI.equals(postcredits)) {
					bool = false;
					postcreditsMap.put(creditid, creditsI);
				}
			} else {
				bool = false;
				postcreditsMap = new TreeMap();
				postcreditsMap.put(creditid, creditsI);
			}
			result = dataParse.combinationChar(postcreditsMap);
		} else {
			Map postcreditsMap = null;
			if (!dsStringValue.equals("")) {
				postcreditsMap = dataParse.characterParse(dsStringValue,
						true);
				String postcredits = String.valueOf(postcreditsMap.get(creditid));
				if (!postcredits.equals("null")) {
					bool = false;
					postcreditsMap.remove(creditid);
				}
				result = dataParse.combinationChar(postcreditsMap);
			} else {
				result = "";
			}
		}
		Map map = new HashMap();
		map.put("bool", bool);
		map.put("result", result);
		return map;
	}
	@SuppressWarnings("unchecked")
	private String trunExpression(String expression,Map<Integer, Map> extcreditsMap,HttpServletRequest request){
		expression = "<u>"+getMessage(request, "a_setting_creditsformula_credits")+"</u>="+
									expression.replace("digestposts", "<u>"+getMessage(request, "digestposts")+"</u>").
									replace("posts", "<u>"+getMessage(request, "posts")+"</u>").
									replace("oltime", "<u>"+getMessage(request, "a_setting_creditsformula_oltime")+"</u>").
									replace("pageviews", "<u>"+getMessage(request, "pageviews")+"</u>");
		if(extcreditsMap!=null){
			for(Entry<Integer,Map> entry : extcreditsMap.entrySet()){
				Integer key = entry.getKey();
				Map value = entry.getValue();
				switch(key){
				case 1: expression = expression.replace("extcredits1", "<u>"+value.get("title")+"</u>");break;
				case 2: expression = expression.replace("extcredits2", "<u>"+value.get("title")+"</u>");break;
				case 3: expression = expression.replace("extcredits3", "<u>"+value.get("title")+"</u>");break;
				case 4: expression = expression.replace("extcredits4", "<u>"+value.get("title")+"</u>");break;
				case 5: expression = expression.replace("extcredits5", "<u>"+value.get("title")+"</u>");break;
				case 6: expression = expression.replace("extcredits6", "<u>"+value.get("title")+"</u>");break;
				case 7: expression = expression.replace("extcredits7", "<u>"+value.get("title")+"</u>");break;
				case 8: expression = expression.replace("extcredits8", "<u>"+value.get("title")+"</u>");break;
				}
			}
		}
		return expression;
	}
	private void updateSettings(Map<String,String> settings,Map<String,String> oldSettings){
		if(settings!=null&&settings.size()>0){
			Set<String> variables=settings.keySet();
			StringBuffer sql=new StringBuffer();
			sql.append("REPLACE INTO jrun_settings (variable, value) VALUES ");
			for(String variable:variables){
				sql.append("('"+variable+"', '"+Common.addslashes(settings.get(variable))+"'),");
			}
			sql.deleteCharAt(sql.length()-1);
			dataBaseService.runQuery(sql.toString(),true);
			oldSettings.putAll(settings);
			ForumInit.setSettings(this.getServlet().getServletContext(), oldSettings);
		}
	}
	private void putValue(String variable,String value,Map<String,String> oldSettings,Map<String,String> settings){
		if(value!=null&&!value.equals(oldSettings.get(variable))){
			settings.put(variable,value);
		}
	}
	private ActionForward getForward(ActionMapping mapping,HttpServletRequest request){
		request.setAttribute("message", getMessage(request, "a_setting_baseset_success"));
		request.setAttribute("url_forward",request.getHeader("Referer"));
		return mapping.findForward("message");
	}
	private CreditsParticularInfo getCreditsParticularInfo(String creditId,String initcredits,Map<Integer,Map<String,Object>> extcredits,Map<String,Map<Integer,Object>> creditspolicy){
		Integer currentExtcreditId = Integer.valueOf(creditId);
		Map<String,Object> currentExtcredit = extcredits.get(currentExtcreditId);
		if(currentExtcredit==null){
			currentExtcredit = new HashMap<String, Object>();
		}
		String tempTitle = (String)currentExtcredit.get("title");
		String title = tempTitle==null?"":tempTitle;
		String tempUnit = (String)currentExtcredit.get("unit");
		String unit = tempUnit==null?"":tempUnit;
		String avaiableString = (String)currentExtcredit.get("available");
		boolean available = avaiableString!=null&&avaiableString.equals("1");
		String allowexchangeinString = (String)currentExtcredit.get("allowexchangein");
		boolean allowexchangein = allowexchangeinString!=null&&allowexchangeinString.equals("1");
		String allowexchangeoutString = (String)currentExtcredit.get("allowexchangeout");
		boolean allowexchangeout = allowexchangeoutString!=null&&allowexchangeoutString.equals("1");
		String showwinthreadString = (String)currentExtcredit.get("showinthread");
		boolean showinthread = showwinthreadString!=null&&showwinthreadString.equals("1");
		Object tempLowerlimit = currentExtcredit.get("lowerlimit");
		String lowerlimit = tempLowerlimit==null?"0":String.valueOf(tempLowerlimit);
		Object tempRatio = currentExtcredit.get("ratio");
		String ratio = tempRatio==null?"0":String.valueOf(tempRatio);
		String initcredit = "0";
		if(initcredits!=null&&!initcredits.equals("")){
			String[] initcreditArray = initcredits.split(",");
			if(initcreditArray.length>currentExtcreditId){
				initcredit = initcreditArray[currentExtcreditId];
			}
		}
		CreditsParticularInfo creditsParticularInfo = new CreditsParticularInfo(creditId,title,unit,available,allowexchangein,allowexchangeout,showinthread,lowerlimit,ratio,initcredit);
		Map<String,String> credits_id_nameMap = creditsParticularInfo.getCredits_id_nameMap();
		for(int i = 1;i<9;i++){
			String tempTile = null;
			Map<String,Object> tempMap = (Map<String,Object>)extcredits.get(i);
			if(tempMap!=null){
				tempTile = (String)tempMap.get("title");
			}
			credits_id_nameMap.put(i+"", tempTile==null||tempTile.equals("")?"extcredits"+i : "extcredits"+i+"("+tempTile+")");
		}
		Object object = creditspolicy.get("digest").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_digest(object==null?"0":object.toString());
		object = creditspolicy.get("getattach").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_getattach(object==null?"0":object.toString());
		object = creditspolicy.get("lowerlimit").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_lowerlimit(object==null?"0":object.toString());
		object = creditspolicy.get("pm").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_pm(object==null?"0":object.toString());
		object = creditspolicy.get("post").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_post(object==null?"0":object.toString());
		object = creditspolicy.get("postattach").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_postattach(object==null?"0":object.toString());
		object = creditspolicy.get("promotion_register").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_promotion_register(object==null?"0":object.toString());
		object = creditspolicy.get("promotion_visit").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_promotion_visit(object==null?"0":object.toString());
		object = creditspolicy.get("reply").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_reply(object==null?"0":object.toString());
		object = creditspolicy.get("search").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_search(object==null?"0":object.toString());
		object = creditspolicy.get("tradefinished").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_tradefinished(object==null?"0":object.toString());
		object = creditspolicy.get("votepoll").get(currentExtcreditId);
		creditsParticularInfo.setCreditspolicy_votepoll(object==null?"0":object.toString());
		return creditsParticularInfo;
	}
	@SuppressWarnings("unchecked")
	public ActionForward ftpcheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> oldSettings=ForumInit.settings;
		FTPClient fc = ftputil.getFTPClient();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String alertmsg = "";
		String testdir = Md5Token.getInstance().getLongToken("jsprun"+timestamp);
		testdir = testdir.substring(12,20);
		String testfile = "jspruntest.txt";
		String attachsave = request.getParameter("attachsave");
		String attach_dir = JspRunConfig.realPath+request.getParameter("attachdir");
		if(!attachsave.equals("0")){
			attach_dir +="/"+testdir;
			File file = new File(attach_dir);
			if(!file.mkdir()){
				alertmsg = getMessage(request, "a_setting_makedir_invalid");
			}
		}
		if(alertmsg.equals("")){
			File file = new File(attach_dir+"/"+testfile);
			try {
				if(!file.createNewFile()){
					alertmsg = getMessage(request, "a_setting_upload_invalid");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			file = new File(attach_dir);
			file.deleteOnExit();
		}
		if(alertmsg.equals("")){
			String password = request.getParameter("ftp[password]");
			if(!Common.empty(password)){
				Map<String,String> ftpmap = dataParse.characterParse(oldSettings.get("ftp"), false);
				String ftppassword = Common.authcode(ftpmap.get("password"), "DECODE", Md5Token.getInstance().getLongToken(oldSettings.get("authkey")),"utf-8");
				int pwlen = password.length();
				if(password.charAt(0)==ftppassword.charAt(0)&&password.charAt(pwlen-1)==ftppassword.charAt(ftppassword.length()-1)&& password.substring(1,pwlen-1).equals("********")){
					password = ftppassword;
				}
			}
			String ftppasv = request.getParameter("ftp[pasv]");
			String ftphost = request.getParameter("ftp[host]");
			String ftpusername = request.getParameter("ftp[username]");
			String ftpattachdir = request.getParameter("ftp[attachdir]");
			int ftpport = Common.toDigit(request.getParameter("ftp[port]"));
			String ftpssl = request.getParameter("ftp[ssl]");
			ftputil.setFtpValues(ftphost,ftpusername,password,ftpattachdir,ftpport,ftpssl,0,ftppasv);
			alertmsg = ftputil.connectToServer(fc);
			if(!alertmsg.equals("")){
				alertmsg = getMessage(request, alertmsg);
			}
		}
		if(alertmsg.equals("")){
			if(!ftputil.dftp_mkdir(testdir,fc)){
				alertmsg = getMessage(request, "a_setting_remote_mderr");
			}else{
				if(!ftputil.dftp_site(testdir,fc)){
					alertmsg = getMessage(request, "a_setting_remote_chmoderr");
				}
				ftputil.dftp_chdir(testdir,fc);
				if(!ftputil.put(JspRunConfig.realPath+"./robots.txt",testfile,fc)){
					alertmsg = getMessage(request, "a_setting_remote_uperr");
					ftputil.dftp_delete(testfile,fc);
					ftputil.dftp_rmdir(testdir,fc);
				}else{
					String attachurl = request.getParameter("ftp[attachurl]");
					attachurl = attachurl==null?"":attachurl+"/"+testdir+"/"+testfile;
					if(!ftputil.readfile(attachurl, null)){
						alertmsg = getMessage(request, "a_setting_remote_geterr");
						ftputil.dftp_delete(testfile,fc);
						ftputil.dftp_rmdir(testdir,fc);
					}else{
						String isinstall = request.getParameter("ftp[isinstall]");
						String activeurl = request.getParameter("ftp[activeurl]");
						if(isinstall.equals("1")){
							if(Common.empty(activeurl)){
								alertmsg = getMessage(request, "a_setting_remote_url_invalid");
							}
							String path = activeurl+"/paser.do?filepath="+Common.encode(testfile)+"&filename="+Common.encode(testfile)+"&path="+Common.encode(attachurl.substring(activeurl.length()))+"&encode="+JspRunConfig.CHARSET+"&test=test";
							if(!ftputil.readfile(path, null)){
								alertmsg = getMessage(request, "a_setting_remote_attplug_invalid");
								ftputil.dftp_delete(testfile,fc);
								ftputil.dftp_rmdir(testdir,fc);
							}else if(!ftputil.dftp_delete(testfile,fc)){
								alertmsg = getMessage(request, "a_setting_remote_delerr");
							}else{
								ftputil.dftp_rmdir(testdir,fc);
								alertmsg = getMessage(request, "a_setting_remote_ok");
							}
						}else if(!ftputil.dftp_delete(testfile,fc)){
							alertmsg = getMessage(request, "a_setting_remote_delerr");
						}else{
							ftputil.dftp_rmdir(testdir,fc);
							alertmsg = getMessage(request, "a_setting_remote_ok");
						}
					}
				}
			}
		}
		try {
			ftputil.closeFtpConnect(fc);
			response.getWriter().write( "<script type='text/javascript'>alert('" + alertmsg + "');</script>");
			response.getWriter().write("<script type='text/javascript'>parent.$('settings').action='admincp.jsp?action=settings&do=attachments';parent.$('settings').target='_self'</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private boolean validateQualification(String qualification){
		if(qualification==null || (qualification = qualification.trim()).length()<3){
			return false;
		}
		String reg = "and|or";
		String[] subq = qualification.split(reg);
		int subLen = subq.length;
		if(subLen == 1){
			return validate_0(qualification);
		}else{
			for(int i = 0;i<subLen;i++){
				String subs = subq[i].trim();
				if(subs.equals("")){
					return false;
				}
				char temp = subs.charAt(0);
				if(temp == '='||temp == '<'||temp == '>'){
					return false;
				}
				temp = subs.charAt(subs.length()-1);
				if(temp == '='||temp == '<'||temp == '>'){
					return false;
				}
				if(!validate_0(subs)){
					return false;
				}
			}
		}
		return true;
	}
	private boolean validate_0(String qualification){
		if(qualification == null || (qualification = qualification.trim()).length()<3){
			return false;
		}
		int temp1 = qualification.indexOf(">=");
		StringBuffer leftString = new StringBuffer();
		StringBuffer rightString = new StringBuffer();
		if(temp1>0){
			if(!validate_1(qualification, temp1, leftString, rightString)){
				return false;
			}
		}else{
			temp1 = qualification.indexOf("<=");
			if(temp1>0){
				if(!validate_1(qualification, temp1, leftString, rightString)){
					return false;
				}
			}else{
				char tempc = 0;
				int sign = 0;
				for(int i = qualification.length();i>0;){
					tempc = qualification.charAt(--i);
					if(tempc=='='||tempc=='<'||tempc=='>'){
						temp1 = i;
						sign++;
					}
					if(sign>1){
						return false;
					}
				}
				if(sign!=1){
					return false;
				}else{
					leftString.append(qualification.substring(0,temp1));
					rightString.append(qualification.substring(temp1+2));
				}
			}
		}
		return validateExpressions(leftString.toString()) && validateExpressions(rightString.toString());
	}
	private boolean validate_1(String qualification,int temp1,StringBuffer leftString,StringBuffer rightString){
		leftString.append(qualification.substring(0,temp1));
		rightString.append(qualification.substring(temp1+2));
		if(leftString.length()==0||rightString.length()==0){
			return false;
		}
		char temp = 0;
		for(int i = leftString.length();i>0;){
			temp = leftString.charAt(--i);
			if(temp=='='||temp=='>'||temp=='<'){
				return false;
			}
		}
		for(int i = leftString.length();i>0;){
			temp = leftString.charAt(--i);
			if(temp=='='||temp=='>'||temp=='<'){
				return false;
			}
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	private void updateExtcredits(Map<String,String> settings){
		Map<Integer,Map> extcredits = dataParse.characterParse(settings.get("extcredits_bak"), false);
		if(extcredits!=null&&extcredits.size()>0){
			int creditstrans=Integer.valueOf(settings.get("creditstrans"));
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
			settings.put("exchangestatus", (allowexchangein&&allowexchangeout?"1":"0"));
			settings.put("transferstatus", (exchcredits.get(creditstrans)!=null?"1":"0"));
			settings.put("extcredits",dataParse.combinationChar(exchcredits));
		}
	}
}