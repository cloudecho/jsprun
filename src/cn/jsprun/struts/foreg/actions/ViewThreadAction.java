package cn.jsprun.struts.foreg.actions;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Memberfields;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Memberspaces;
import cn.jsprun.domain.Polloptions;
import cn.jsprun.domain.Polls;
import cn.jsprun.foreg.vo.ActivitappliesVO;
import cn.jsprun.foreg.vo.ViewThreadVO;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Jspruncode;
import cn.jsprun.utils.LogPage;
public class ViewThreadAction extends BaseAction {
	Map<String,String> attatypemap = new HashMap<String,String>();
	{
		attatypemap.put("text/css", "text.gif");
		attatypemap.put("application/zip", "zip.gif");
		attatypemap.put("text/plain", "text.gif");
		attatypemap.put("image/pjpeg", "image.gif");
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public ActionForward viewthreadpost(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		HttpSession session = request.getSession();
		int uid = (Integer)session.getAttribute("jsprun_uid");
		int tid =Common.toDigit(request.getParameter("tid"));
		String ajaxin = request.getParameter("inajax");
		List<Map<String,String>> threadpost = dataBaseService.executeQuery("select t.*,f.threadcaches,f.fup,f.name,f.allowmediacode,f.allowpostspecial,f.allowbbcode,f.allowimgcode,f.jammer,f.allowsmilies,f.displayorder as dis,f.type,f.allowspecialonly,f.allowshare,f.allowhtml,f.alloweditpost,f.autoclose,f.styleid from jrun_threads t left join jrun_forums f on t.fid=f.fid  where  t.displayorder>=0 and t.tid="+tid);
		if (threadpost == null || threadpost.size()<=0) {
			if(ajaxin!=null){
				Common.writeMessage(response,getMessage(request, "thread_nonexistence"),true);
				return null;
			}else{
				request.setAttribute("errorInfo", getMessage(request, "thread_nonexistence"));
				return mapping.findForward("showMessage");
			}
		}
		Map<String,String> threads = threadpost.get(0);
		threadpost=null;
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String dateformat = (String)session.getAttribute("dateformat");
		String timeoffset=(String)session.getAttribute("timeoffset");
		String timeformat = (String)session.getAttribute("timeformat");
		request.setAttribute("nowtime", timestamp);
		String cachethreadlife = settings.get("cachethreadlife");
		String page = request.getParameter("page");
		if(page==null){
			page="1";
		}
		if(!cachethreadlife.equals("0") && Common.toDigit(threads.get("threadcaches"))>0 && uid==0 && page.equals("1") && "0".equals(threads.get("special"))&&Common.empty(request.getParameter("authorid"))){
			if(viewthread_loadcache(timestamp, timeoffset, threads, settings, JspRunConfig.realPath+settings.get("cachethreaddir"), request, response))
			{
				return null;
			}
		}
		String highlight = request.getParameter("highlight");
		int highlightstatus = !Common.empty(highlight)&&!Common.empty(highlight.replace("+",""))?1:0;
		request.setAttribute("highlightstatus", highlightstatus);
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		int readaccess = Common.toDigit(usergroups.get("readaccess"));
		request.setAttribute("readaccess", readaccess);
		short styleid=Short.valueOf(threads.get("styleid"));
		if(styleid>0){
			request.setAttribute("styleid",styleid);
		}
		Members members = (Members)session.getAttribute("user");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		String modadd2 = "";String modadd1 = "";
		if(members!=null&&members.getAdminid()==3){
			modadd1 = ", m.uid AS ismoderator ";
			modadd2 = " LEFT JOIN jrun_moderators m ON m.uid="+uid+" AND m.fid=ff.fid ";
		}
		List<Map<String, String>> forumslist = dataBaseService.executeQuery("SELECT ff.modrecommend, ff.viewperm, a.allowview,a.allowgetattach,a.allowpostattach,a.allowreply,a.allowpost,ff.postperm, ff.typemodels,ff.threadtypes,ff.replyperm,ff.getattachperm,ff.postattachperm,ff.password,ff.formulaperm"+modadd1+" FROM  jrun_forumfields ff LEFT JOIN jrun_access a ON a.uid="+ uid+ " AND a.fid=ff.fid "+modadd2+"WHERE ff.fid = "+threads.get("fid"));
		Map<String, String> forumMap = forumslist.get(0);
		request.setAttribute("forumfield", forumMap);
		String modrecommend = forumMap.get("modrecommend");
		Map modrecommendMap = dataParse.characterParse(modrecommend, false);
		request.setAttribute("modrecommendMap", modrecommendMap);
		forumslist = null;
		String extgroupids=members!=null?members.getExtgroupids():null;
		if (Common.empty(forumMap.get("allowview"))) {
			if (Common.empty(forumMap.get("viewperm")) && readaccess==0) {
				if(ajaxin!=null){
					Common.writeMessage(response,getMessage(request, "group_nopermission", usergroups.get("grouptitle")),true);
					return null;
				}else{
					request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
					return mapping.findForward("nopermission");
				}
			} else if (!Common.empty(forumMap.get("viewperm"))&& !Common.forumperm(forumMap.get("viewperm"), groupid,extgroupids)) {
				if(ajaxin!=null){
					Common.writeMessage(response,getMessage(request, "forum_nopermission_2"),true);
					return null;
				}else{
					request.setAttribute("show_message", getMessage(request, "forum_nopermission_2"));
					return mapping.findForward("nopermission");
				}
			}
		}
		boolean allowgetattach = !Common.empty(forumMap.get("allowgetattach")) || (!usergroups.get("allowgetattach").equals("0") && "".equals(forumMap.get("getattachperm"))) || Common.forumperm(forumMap.get("getattachperm"), groupid,extgroupids);  
		request.setAttribute("allowgetattach",allowgetattach);
		boolean allowpostattach =  !Common.empty(forumMap.get("allowpostattach")) || (!usergroups.get("allowpostattach").equals("0") && "".equals(forumMap.get("postattachperm"))) || Common.forumperm(forumMap.get("postattachperm"), groupid,extgroupids);  
		request.setAttribute("allowpostattach", allowpostattach);
		boolean modertar = Common.ismoderator(forumMap.get("ismoderator"), members);
		request.setAttribute("modertar", modertar);
		String extcredit = settings.get("extcredits");
		Map<Integer,Map<String,String>> extcredits=dataParse.characterParse(extcredit, true);
		String formulaperm=forumMap.get("formulaperm");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if(formulaperm.length()>0){
			Map<String,String> messages=Common.forumformulaperm(formulaperm, members,modertar, extcredits,resources,locale);
			if(messages!=null){
				if(ajaxin!=null){
					Common.writeMessage(response,getMessage(request, "forum_permforum_nopermission", messages.get("formulamessage"),messages.get("usermsg")),true);
					return null;
				}else{
					request.setAttribute("show_message", getMessage(request, "forum_permforum_nopermission", messages.get("formulamessage"),messages.get("usermsg")));
					return mapping.findForward("nopermission");
				}
			}				
		}
		if(usergroups!=null && Common.toDigit(threads.get("readperm"))>readaccess && !modertar && !threads.get("authorid").equals(uid+"")){
			if(ajaxin!=null){
				Common.writeMessage(response,getMessage(request, "thread_nopermission",threads.get("readperm")),true);
				return null;
			}else{
				request.setAttribute("show_message", getMessage(request, "thread_nopermission", threads.get("readperm")));
				return mapping.findForward("nopermission");
			}
		}
		if(Common.toDigit(threads.get("digest"))>0 && usergroups.get("allowviewdigest").equals("0") && !threads.get("authorid").equals(uid+"")){
			if(ajaxin!=null){
				Common.writeMessage(response,getMessage(request, "thread_digest_nopermission", usergroups.get("grouptitle")),true);
				return null;
			}else{
				request.setAttribute("show_message", getMessage(request, "thread_digest_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			}
		}
		String password=forumMap.get("password");
		if(password!=null&&!"".equals(password))
		{
			 if(!password.equals(session.getAttribute("fidpw"+threads.get("fid")))){
				request.setAttribute("fid", Short.valueOf(threads.get("fid")));
				return mapping.findForward("toForumdisplay_passwd");
			}
		}
		boolean threadpay = false;
		if(Common.toDigit(threads.get("price"))>0 && threads.get("special").equals("0")){
			String maxchargespan = settings.get("maxchargespan");
			if(!maxchargespan.equals("0") && timestamp-Common.toDigit(threads.get("dateline"))>=Common.toDigit(maxchargespan)*3600){
				dataBaseService.runQuery("update jrun_threads set price=0 where tid="+tid,true);
				threads.put("price","0");
			}else{
				if(!modertar && !threads.get("authorid").equals(uid+"")){
					List<Map<String, String>> paylog = dataBaseService.executeQuery("SELECT tid FROM jrun_paymentlog WHERE tid="+tid+" AND uid="+uid);
					if(paylog==null || paylog.size()<=0){
						threadpay = true;
					}
					paylog = null;
				}
			}
		}
		Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
		String fromuid = !Common.empty(creditspolicys.get("promotion_visit"))&&uid>0?"&amp;fromuid="+uid:"";
		request.setAttribute("fromuid", fromuid);
		String user_agent = request.getHeader("User-Agent");
		request.setAttribute("isie", user_agent!=null&&user_agent.indexOf("MSIE")>=0);
		int creditstransid = Common.toDigit(settings.get("creditstrans")); 
		Map creditstrans=(Map)extcredits.get(creditstransid);
		request.setAttribute("creditstrans", creditstrans);
		String extcreditname = creditstrans!=null?(String)creditstrans.get("title"):"";
		String postperm=forumMap.get("postperm");
		boolean allowpost=(("".equals(postperm))&&Integer.valueOf(usergroups.get("allowpost"))>0)||((!"".equals(postperm))&&Common.forumperm(postperm, groupid,extgroupids))||!Common.empty(forumMap.get("allowpost"));
		boolean showpoll=false;
		boolean showtrade=false;
		boolean showreward=false;
		boolean showactivity=false;
		boolean showdebate=false;
		boolean showvideo=false;
		short allowpostspecial= Short.valueOf(threads.get("allowpostspecial"));
		if(allowpostspecial>0)
		{
			showpoll=(allowpostspecial&1)>0;
			showtrade=(allowpostspecial&2)>0;
			showreward=(allowpostspecial&4)>0;
			showactivity=(allowpostspecial&8)>0;
			showdebate=(allowpostspecial&16)>0;
			showvideo=(allowpostspecial&32)>0&&"1".equals(settings.get("videoopen"));
			request.setAttribute("showpoll",showpoll );
			request.setAttribute("showtrade",showtrade );
			request.setAttribute("showreward",showreward );
			request.setAttribute("showactivity",showactivity );
			request.setAttribute("showdebate",showdebate );
			request.setAttribute("showvideo",showvideo);
		}
		if(allowpost&&usergroups!=null)
		{
			request.setAttribute("allowpostpoll",usergroups.get("allowpostpoll").equals("1")&&showpoll);
			request.setAttribute("allowposttrade",usergroups.get("allowposttrade").equals("1")&&showtrade );
			request.setAttribute("allowpostreward",usergroups.get("allowpostreward").equals("1")&&showreward&&creditstrans!=null);
			request.setAttribute("allowpostactivity",usergroups.get("allowpostactivity").equals("1")&&showactivity );
			request.setAttribute("allowpostdebate",usergroups.get("allowpostdebate").equals("1")&&showdebate );
			request.setAttribute("allowpostvideo",usergroups.get("allowpostvideo").equals("1")&&showvideo);
		}
		request.setAttribute("allowpost", allowpost);
		Map threadtypes=dataParse.characterParse(forumMap.get("threadtypes"),true);
		request.setAttribute("threadtypes", threadtypes.size()>0?threadtypes:null);
		request.setAttribute("typemodels", dataParse.characterParse(forumMap.get("typemodels"),true));
		Map<Short,String> visitedforums=(Map<Short,String>)session.getAttribute("visitedforums");
		request.setAttribute("visitedforums",visitedforums!=null && visitedforums.size()>1?visitedforums:null);
		visitedforums=null;
		if(members!=null){
			Map<String,String> announcementsMap=(Map<String,String>)request.getAttribute("announcements");
			Map<Integer,Map> announcements=dataParse.characterParse(announcementsMap!=null?announcementsMap.get("announcements"):null,true);
			announcementsMap=null;
			List<Map<String,String>> pmlists=new ArrayList<Map<String,String>>();;
			if(announcements!=null&&announcements.size()>0)
			{
				int announcepm=0;
				Set<Integer> keys=announcements.keySet();
				String readapmid = CookieUtil.getCookie(request, "readapmid"); 
				String []readapmids = !Common.empty(readapmid)?readapmid.split("D"):null;
				for (Integer key : keys) {
					Map<String,String> announcement=announcements.get(key);
					if(announcement.get("type")!=null&&announcement.get("type").equals("2")&& !Common.in_array(readapmids, announcement.get("id")))
					{
						announcement.put("announce", "true");
						pmlists.add(announcement);
						announcepm++;
					}
				}
				announcements=null;
				request.setAttribute("announcepm", announcepm);
			}
			if(members.getNewpm()>0){
				String sql="SELECT pmid, msgfrom, msgfromid, subject, message FROM jrun_pms WHERE msgtoid="+members.getUid()+" AND folder='inbox' AND delstatus!='2' AND new='1'";
				List<Map<String,String>> maps=dataBaseService.executeQuery(sql);
				int newpmnum=maps!=null?maps.size():0;
				if(newpmnum>0&&newpmnum<=10)
				{
					pmlists.addAll(maps);
				}
				maps=null;
				request.setAttribute("newpmnum", newpmnum);
			}
			request.setAttribute("pmlists", pmlists.size()>0?pmlists:null);
		}
		String replyperm = forumMap.get("replyperm");
		boolean allowpostreply = ((threads.get("closed").equals("0")&&!checkautoclose(threads)) || modertar) && ((replyperm.equals("") && usergroups.get("allowreply").equals("1"))||(!replyperm.equals("")&&Common.forumperm(replyperm, groupid,extgroupids))||!Common.empty(forumMap.get("allowreply")));
		request.setAttribute("allowpostreply", allowpostreply);
		String postnocustom = settings.get("postnocustom");
		if(postnocustom!=null && !postnocustom.equals("")){
			request.setAttribute("postcustom", postnocustom.split("\n"));
		}
		if(ajaxin==null){
			viewthread_updateviews(settings.get("delayviewcount"),tid,JspRunConfig.realPath+"forumdata/cache/cache_threadviews.log");
		}
		request.setAttribute("attatype",attatypemap);
		request.setAttribute("tid", tid);
		request.setAttribute("fid", Short.valueOf(threads.get("fid")));
		Map<String, String> faqs = (Map<String, String>) request.getAttribute("faqs");
		request.setAttribute("faqs", dataParse.characterParse(faqs.get("faqs"), false));
		if("1".equals(settings.get("forumjump"))){
			List<Map<String,String>> forumlist=dataParse.characterParse(settings.get("forums"));
			if("1".equals(settings.get("jsmenu_1"))){
				request.setAttribute("forummenu", Common.forumselect(forumlist,groupid,extgroupids, threads.get("fid")));
			}
			else{
				request.setAttribute("forumselect", Common.forumselect(forumlist,false, false,groupid,extgroupids,null));
			}				
		}
		String extra=request.getParameter("extra");
		String forumname=threads.get("name");
		String navigation=null;
		String backtrack = null;
		if(ajaxin==null){   
			if(threads.get("type").equals("sub")){
				List<Map<String,String>> forumsmap = dataBaseService.executeQuery("select fid,name from jrun_forums where fid="+threads.get("fup"));
				String fupforumname=forumsmap.get(0).get("name");
				backtrack = "forumdisplay.jsp?fid="+threads.get("fid")+(extra!=null ? "&amp;"+extra.replaceAll("^(&amp;)*", ""):"");
				navigation="&raquo; <a href=\"forumdisplay.jsp?fid="+forumsmap.get(0).get("fid")+"\">"+fupforumname+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+threads.get("fid")+(extra!=null ? "&amp;"+extra.replaceAll("^(&amp;)*", ""):"")+"\">"+forumname+"</a>";
				forumsmap = null;
				request.setAttribute("navtitle",Common.strip_tags(threads.get("subject")+" - "+forumname+" - "+fupforumname+" - "));
			}else{
				backtrack = "forumdisplay.jsp?fid="+threads.get("fid")+(extra!=null ? "&amp;"+extra.replaceAll("^(&amp;)*", ""):"");
				navigation="&raquo; <a href=\"forumdisplay.jsp?fid="+threads.get("fid")+(extra!=null?"&amp;"+extra.replaceAll("^(&amp;)*", ""):"")+"\">"+forumname+"</a>";
				request.setAttribute("navtitle", Common.strip_tags(threads.get("subject")+" - "+forumname+" - "));
			}
			request.setAttribute("backtrack", backtrack);
			request.setAttribute("navigation", navigation.toString());
		}
		int showsettings=Integer.valueOf(settings.get("showsettings"));
		int showcustom = members!=null?members.getCustomshow():26;
		boolean showsignatures;boolean showavatars;boolean showimages;
		if(showcustom>=18){
			showcustom=showcustom%18;
			showsignatures=(showsettings&4)>0;
		}else{
			showsignatures=(showcustom&9)>0;
		}
		if(showcustom>=6){
			showcustom=showcustom%6;
			showavatars=(showsettings&2)>0;
		}else{
			showavatars=(showcustom&3)>0;
		}
		if(showcustom>=2){
			showimages=(showsettings&1)>0;
		}else{
			showimages=(showcustom&1)>0;
		}
		request.setAttribute("showimag", showimages);
		request.setAttribute("isshowsignatures", showsignatures);
		Map<String,String> ftpmap = dataParse.characterParse(settings.get("ftp"), false);
		String ftpurl = ftpmap.get("attachurl");
		ftpmap = null;
		Map<String,String> bbcodesres = (Map<String,String>)request.getAttribute("bbcodes");
		Map<String,Map<String,String>> bbcodes = dataParse.characterParse(bbcodesres.get("bbcodes"), false);
		String smilies_parse = ((Map<String,String>)request.getAttribute("smilies_parse")).get("smilies_parse");
		List<Map<String,String>> smilieslist = dataParse.characterParse(smilies_parse);
		String sql = null;
		String hsqlcount = null;
		String forwad = null;
		List<String> custom = new ArrayList<String>();
		String profileds = "";
		List<String> profields = new ArrayList<String>();
		List<Map<String,String>> profiledlist = null;
		if(ajaxin==null){
			profiledlist = dataBaseService.executeQuery("select fieldid,title from jrun_profilefields where available=1 and invisible=0 order by displayorder");
			for(Map<String,String> profiled :profiledlist){
				profileds = profileds+",mf.field_"+profiled.get("fieldid");
				profields.add("field_"+profiled.get("fieldid"));
			}
			custom.add("uid");custom.add("posts");custom.add("digest");
			custom.add("credits");
			Set<Integer> extcreSet = extcredits.keySet();
			for(Integer key:extcreSet){
				custom.add("extcredits"+key);
			}
			custom.addAll(profields);
			custom.add("readperm");custom.add("gender");custom.add("location");
			custom.add("oltime");custom.add("regtime");custom.add("lastdate");
		}
		request.setAttribute("subject", threads.get("subject"));
		int typeid = Common.toDigit(threads.get("typeid"));
		int threadtype = threadtypes.get("types")!=null&&((Map)threadtypes.get("types")).get(typeid)!=null?1:0;
		request.setAttribute("typeid", typeid);
		String typetemplate = "";
		Map<String,String> optiondata = new HashMap<String,String>();
		Map<String,Map<String,String>> optionlist = new TreeMap<String,Map<String,String>>();
		List optionMaplist = new ArrayList();
		if(typeid>0&&threadtype>0){
			String types = threadtypes.get("special")!=null && ((Map)threadtypes.get("special")).get(typeid)!=null?((Map)threadtypes.get("special")).get(typeid).toString():"0";
			if(Common.toDigit(types)>0){
				Common.include(request, response, servlet, "./forumdata/cache/threadtype_"+typeid+".jsp", null);
				Map<String, String> threadtypesf = (Map<String, String>) request.getAttribute("threadtype");
				if(threadtypesf!=null){
					Map<Integer, Map<String, String>> dtype = dataParse.characterParse(threadtypesf.get("dtype"),true);
					List<Map<String,String>> optionvalues = dataBaseService.executeQuery("SELECT optionid, value FROM jrun_typeoptionvars WHERE tid='"+tid+"'");
					for(Map<String,String> option:optionvalues){
						optiondata.put(option.get("optionid"), option.get("value"));
					}
					Set<Integer> dtypes = dtype.keySet();
					for(Integer optionid:dtypes){
						Map<String,String> option = dtype.get(optionid);
						Map<String,String> optionoption = new HashMap<String,String>();
						optionoption.put("title", option.get("title"));
						if(option.get("type").equals("checkbox")){
							String choicesvalues = optiondata.get(optionid+"")==null?"":optiondata.get(optionid+"");
							String [] choicesvalue = choicesvalues.split("\t");
							String [] choicesname = option.get("choices").split("\\\\n");
							Map<String,String> temp = new HashMap<String, String>();
							for(String key_value : choicesname){
								String[] key_valueArray =  key_value.split("=");
								temp.put(key_valueArray[0], key_valueArray[1]);
							}
							StringBuffer value = new StringBuffer();
							String tempS;
							for(String choiceid:choicesvalue){
								value.append("&nbsp;");
								if((tempS = temp.get(choiceid))!= null){
									value.append(tempS);
								}
							}
							optionoption.put("value",value.toString());
						}else if(Common.in_array(new String[]{"radio","select"},option.get("type"))){
							String [] choicesname = option.get("choices").split("\\\\n");
							Map<String,String> temp = new HashMap<String, String>();
							for(String key_value : choicesname){
								String[] key_valueArray =  key_value.split("=");
								temp.put(key_valueArray[0], key_valueArray[1]);
							}
							String tempS;
							String value = (tempS = temp.get(optiondata.get(optionid+"")))!= null ? tempS:"";
							optionoption.put("value",value);
						}else if(option.get("type").equals("image")){
							String maxwidth = option.get("maxwidth")!=null?"width=\""+option.get("maxwidth")+"\"":"";
							String maxheight = option.get("maxheight")!=null?"height=\""+option.get("maxheight")+"\"":"";
							String value = optiondata.get(optionid+"")!=null?"<a href=\""+optiondata.get(optionid+"")+"\" target=\"_blank\"><img src=\""+optiondata.get(optionid+"")+"\"  "+maxwidth+" "+maxheight+" border=\"0\"></a>":"";
							optionoption.put("value", value);
						}else if(option.get("type").equals("url")){
							String value = optiondata.get(optionid+"")!=null?"<a href=\""+optiondata.get(optionid+"")+"\" target=\"_blank\">"+optiondata.get(optionid+"")+"</a>":"";
							optionoption.put("value", value);
						}else if(option.get("type").equals("textarea")){
							String value = optiondata.get(optionid+"")!=null?Common.nl2br(optiondata.get(optionid+"")):"";
							optionoption.put("value", value);
						}else{
							optionoption.put("value", optiondata.get(optionid+""));
						}
						optionMaplist.add(optionoption);
						optionlist.put(option.get("identifier"), optionoption);
					}
					Set<String> optionkey = optionlist.keySet();
					if(!Common.empty(threadtypesf.get("dtypeTemplate"))){
						String dtypeTemplate = threadtypesf.get("dtypeTemplate");
						for(String key:optionkey){
							Map<String,String> option = optionlist.get(key);
							String value = option.get("value").equals("")?"none":option.get("value");
							dtypeTemplate = dtypeTemplate.replaceAll("\\{"+key+"\\}",option.get("title"));
							dtypeTemplate = dtypeTemplate.replaceAll("\\["+key+"value\\]",value);
						}
						typetemplate = dtypeTemplate;
					}
				}
				request.setAttribute("typetemplate", typetemplate);
				request.setAttribute("optionlist", optionMaplist);
			}
			String subject = Common.toDigit(threadtypes.get("listable").toString())>0?"<a href=\"forumdisplay.jsp?fid="+threads.get("fid")+"&amp;filter=type&amp;typeid="+threads.get("typeid")+"\">["+((Map)threadtypes.get("types")).get(typeid)+"]</a> "+threads.get("subject"):"["+((Map)threadtypes.get("types")).get(typeid)+"] "+threads.get("subject");
			threads.put("subject", subject);
		}
		int special = Common.intval(threads.get("special"));
		List<Map<String,String>> postlist = null;
		if(extra!=null){
			extra =Common.encode(extra);
		}
		request.setAttribute("extra", extra);
		if (special==0) {
			String url = "viewthread.jsp?tid="+tid+(extra!=null?"&amp;extra="+extra:"");
			int authorid = Common.toDigit(request.getParameter("authorid"));
			String sqldate = "";
			if (authorid>0) {
				sqldate = " and p.authorid='" + authorid+"'";
				url = url+"&authorid="+authorid;
			}
			forwad = "viewthread";
			sql = "select p.*,m.uid, m.username, m.groupid, m.adminid, m.regdate, m.lastactivity, m.posts, m.digestposts, m.oltime,m.pageviews, m.credits, m.extcredits1, m.extcredits2, m.extcredits3, m.extcredits4, m.extcredits5, m.extcredits6,m.extcredits7, m.extcredits8, m.email, m.gender, mf.nickname, mf.site,mf.icq, mf.qq, mf.yahoo, mf.msn, mf.taobao, mf.alipay, mf.location, mf.medals, mf.avatar, mf.avatarwidth,mf.avatarheight, mf.sightml AS signature, mf.customstatus, mf.spacename,u.stars,u.readaccess,u.grouptitle,u.type,u.creditshigher,u.creditslower,u.color,u.allowsigbbcode,u.allowsigimgcode,u.groupavatar"+profileds+" from jrun_posts as p left join jrun_members as m on p.authorid=m.uid left join jrun_memberfields as mf on mf.uid=m.uid left join jrun_usergroups as u on m.groupid=u.groupid where p.tid='"+tid+"'"+sqldate+ " and  p.invisible='0' order by p.first DESC,p.pid";
			hsqlcount = "select count(*) count from jrun_posts as p where p.tid='"+tid+"'"+ sqldate+" and p.invisible='0'";
			Map<String,Integer> pages = multi(request,response,uid,hsqlcount,url,null,0);
			postlist = dataBaseService.executeQuery(sql+" limit "+pages.get("beginsize")+","+pages.get("pagesize"));
			pages = null;
		}else if(special!=2&&special!=5){
			sql = "select p.*,m.uid, m.username, m.groupid, m.adminid, m.regdate, m.lastactivity, m.posts, m.digestposts, m.oltime,m.pageviews, m.credits, m.extcredits1, m.extcredits2, m.extcredits3, m.extcredits4, m.extcredits5, m.extcredits6,m.extcredits7, m.extcredits8, m.email, m.gender, mf.nickname, mf.site,mf.icq, mf.qq, mf.yahoo, mf.msn, mf.taobao, mf.alipay, mf.location, mf.medals, mf.avatar, mf.avatarwidth,mf.avatarheight, mf.sightml AS signature, mf.customstatus, mf.spacename,mf.buyercredit,mf.sellercredit,u.stars,u.readaccess,u.grouptitle,u.type,u.creditshigher,u.creditslower,u.color,u.allowsigbbcode,u.allowsigimgcode,u.groupavatar"+profileds+" from jrun_posts as p left join jrun_members as m on p.authorid=m.uid left join jrun_memberfields as mf on mf.uid=m.uid left join jrun_usergroups as u on m.groupid=u.groupid where p.tid='" + tid + "'  and  p.invisible='0' and p.first=1";
			postlist = dataBaseService.executeQuery(sql);
		}
		String dos = request.getParameter("do");
		request.setAttribute("thread", threads);
		if(special>0){
			if(!Common.empty(dos) && "viewspecialpost".equals(dos)) {
				return viewthread_special(request,response,mapping,threads,bbcodes,smilieslist,modertar,members,settings,extcreditname,showimages,uid,allowgetattach,readaccess,ftpurl,dateformat,timeformat,timeoffset,resources,locale);
			} else {
				switch(special) {
					case 1: forwad = viewthread_poll(request,tid,uid,settings,members);
					if(forwad == null){
						return mapping.findForward("showMessage");
					}
					break;
					case 2: return viewthread_trade(request,response,mapping,threads,settings,bbcodes,smilieslist,resources,locale,modertar,uid,members,showavatars,showsignatures,threadpay,allowgetattach,extcreditname,showimages,readaccess,ftpurl,dateformat,timeformat,creditstrans,timeoffset,tid,profiledlist,custom,profileds,highlightstatus,highlight);
					case 3: forwad = viewthread_reward(request,threads,members,settings,bbcodes,smilieslist,ftpurl,modertar,extcreditname,showimages,uid,allowgetattach,readaccess,dateformat,timeformat,timeoffset,tid,resources,locale,highlightstatus,highlight); break;
					case 4: {
						forwad = viewthread_activity(request,response,members,timestamp,tid,settings); 
						if(forwad == null){
							return mapping.findForward("showMessage");
						}
						break;
					}
					case 5: return viewthread_debate(request,response,mapping,threads,settings,bbcodes,smilieslist,resources,locale,modertar,uid,members,showavatars,showsignatures,threadpay,allowgetattach,extcreditname,showimages,readaccess,ftpurl,dateformat,timeformat,creditstrans,timeoffset,tid,profiledlist,custom,profileds,highlightstatus,highlight);
					case 6: break;
				}
			}
		}
		List<Map<String,String>> threadmod = dataBaseService.executeQuery("select username,dateline,action from jrun_threadsmod as m where m.tid="+tid+" order by m.dateline desc limit 1");
		if (threadmod!=null&&threadmod.size()>0) {
			Map<String,String> threadmodmap = threadmod.get(0);
			String actionname = getMessage(request,threadmodmap.get("action"));
			threadmodmap.put("action", actionname);
			request.setAttribute("threadmod", threadmodmap);
		} else {
			request.setAttribute("threadmod", null);
		}
		threadmod = null;
		List<ViewThreadVO> dispost = new ArrayList<ViewThreadVO>();
		if (postlist != null) {
			int userstatusby = Integer.valueOf(settings.get("userstatusby"));
			Map<Integer,Map<String,String>> ranks=null;
			if(userstatusby==2){
				ranks=dataParse.characterParse(((Map<String,String>)request.getAttribute("ranks")).get("ranks"),false);
			}
			String custominfo = settings.get("customauthorinfo");
			Map custominfoMap = dataParse.characterParse(custominfo, false);
			Map<String,String[]> custommap = getCreditsunit(extcredits);
			String extname[] = custommap.get("extname");
			String extunit[] = custommap.get("extunit");
			custommap = null;
			Map<String,Map<String,String>> medals=null;
			if(special==0){
				medals=dataParse.characterParse(((Map<String,String>)request.getAttribute("medals")).get("medals"),true);
			}
			int lastvisit=members!=null?members.getLastvisit():0;
			int size = postlist.size();
			int count =0;
			byte tagstatus = (byte)Common.toDigit(settings.get("tagstatus"));
			if(tagstatus==1){
				List<Map<String, String>> taglist = dataBaseService.executeQuery("SELECT tagname FROM jrun_threadtags WHERE tid="+tid);
				if(taglist==null || taglist.size()<=0){
					request.setAttribute("taglist", null);
				}else{
					StringBuffer metakeywords  = new StringBuffer();
					for(Map<String,String> tag:taglist){
						metakeywords.append(tag.get("tagname")+",");
					}
					request.setAttribute("metakeywords", metakeywords);
					request.setAttribute("taglist", taglist);
				}
				taglist = null;
			}
			StringBuffer ratelogpids=new StringBuffer();
			int ratelogrecord = Integer.valueOf(settings.get("ratelogrecord"));
			int parnum = Common.intval(settings.get("maxsmilies"));
			for (Map<String,String> postmap:postlist) {
				count++;
				if (special==0){ 
					if(ratelogrecord>0&&Integer.valueOf(postmap.get("ratetimes"))>0){
						ratelogpids.append(","+postmap.get("pid"));
					}
				}
				ViewThreadVO viewthreadvo= viewthread_procpost(request,threads,postmap,settings,medals,bbcodes,smilieslist,resources,locale,modertar,uid,members,userstatusby,ranks,count,showavatars,showsignatures,size,lastvisit,threadpay,allowgetattach,extcreditname,showimages,readaccess,ftpurl,dateformat,timeformat,creditstrans,timeoffset,tid,extname,extunit,custominfoMap,profiledlist,custom,parnum,highlightstatus,highlight);
				dispost.add(viewthreadvo);
			}
			if(ratelogpids.length()>0){
				Map<String,List<Map<String,String>>> ratelogs=new HashMap<String,List<Map<String,String>>>();
				List<Map<String,String>> ratelogslist = dataBaseService.executeQuery("select * from jrun_ratelog where pid IN ("+ratelogpids.substring(1)+") ORDER BY dateline DESC,extcredits ASC");
				for(Map<String,String> ratelog : ratelogslist){
					String pid=ratelog.get("pid");
					List<Map<String,String>> ratelogtemp=ratelogs.get(pid);
					if(ratelogtemp==null){
						ratelogtemp=new ArrayList<Map<String,String>>();
						ratelogs.put(pid, ratelogtemp);
					}
					if(ratelogtemp.size()<ratelogrecord){
						ratelogtemp.add(ratelog);
					}
				}
				request.setAttribute("ratelogs",ratelogs);
			}
			extunit=null;custominfoMap=null;
			request.setAttribute("extcredits",extname);
			if (special!=0) {
				ViewThreadVO viewthread = (ViewThreadVO) dispost.get(0);
				request.setAttribute("viewthread", viewthread);
			}
		}
		postlist = null;
		if(special==3 && Integer.valueOf(threads.get("price"))<0){
			threads.put("price", (int)(Math.abs(Double.valueOf(threads.get("price"))))+"");
		}
		if (dispost == null || dispost.size() == 0) {
			request.setAttribute("postlist", null);
		} else {
			request.setAttribute("postlist", dispost);
		}
		dispost = null;
		request.setAttribute("coloroptions", Common.COLOR_OPTIONS);
		int google_searchbox=Common.range(Common.intval(settings.get("google_searchbox")), 10, 0);
		int baidu_searchbox=Common.range(Common.intval(settings.get("baidu_searchbox")), 10, 0);
		request.setAttribute("google_searchbox", google_searchbox&4);
		request.setAttribute("baidu_searchbox", baidu_searchbox&4);
		request.setAttribute("CACHE_THREADS", true);
		return mapping.findForward(forwad);
	}
	@SuppressWarnings("unchecked")
	public ActionForward printable(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		int uid = (Integer)session.getAttribute("jsprun_uid");
		int tid = Common.toDigit(request.getParameter("tid"));
		String posthql = "select * from jrun_posts as p where p.tid=" + tid+" and p.invisible='0' order by p.first DESC,p.pid limit 100";
		List<Map<String,String>> threadpost = dataBaseService.executeQuery("select t.*,f.threadcaches,f.fup,f.name,f.allowmediacode,f.allowpostspecial,f.allowbbcode,f.allowimgcode,f.jammer,f.allowsmilies,f.displayorder as dis,f.type,f.allowspecialonly,f.allowshare,f.allowhtml,f.alloweditpost,f.autoclose from jrun_threads t left join jrun_forums f on t.fid=f.fid  where  t.displayorder>=0 and t.tid="+tid);
		if (threadpost == null || threadpost.size()<=0) {
			request.setAttribute("errorInfo", getMessage(request, "thread_nonexistence"));
			return mapping.findForward("showMessage");
		}
		Map<String,String> threads = threadpost.get(0);
		request.setAttribute("fid", Short.valueOf(threads.get("fid")));
		threadpost=null;
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		int readaccess = Common.toDigit(usergroups.get("readaccess"));
		request.setAttribute("readaccess", readaccess);
		Members members = (Members)session.getAttribute("user");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		String modadd2 = "";String modadd1 = "";
		if(members!=null&&members.getAdminid()==3){
			modadd1 = ", m.uid AS ismoderator ";
			modadd2 = " LEFT JOIN jrun_moderators m ON m.uid="+uid+" AND m.fid=ff.fid ";
		}
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		List<Map<String, String>> forumslist = dataBaseService.executeQuery("SELECT ff.modrecommend, ff.viewperm, a.allowview,a.allowgetattach,ff.postperm, ff.typemodels,ff.threadtypes,ff.replyperm,ff.getattachperm,ff.password,ff.formulaperm"+modadd1+" FROM  jrun_forumfields ff LEFT JOIN jrun_access a ON a.uid="+ uid+ " AND a.fid=ff.fid "+modadd2+"WHERE ff.fid = "+threads.get("fid"));
		Map<String, String> forumMap = forumslist.get(0);
		String modrecommend = forumMap.get("modrecommend");
		Map modrecommendMap = dataParse.characterParse(modrecommend, false);
		request.setAttribute("modrecommendMap", modrecommendMap);
		forumslist = null;
		String extgroupids=members!=null?members.getExtgroupids():null;
		if (Common.empty(forumMap.get("allowview"))) {
			if (Common.empty(forumMap.get("viewperm")) && (usergroups==null || usergroups.get("readaccess").equals("0"))) {
				request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			} else if (!Common.empty(forumMap.get("viewperm")) && !Common.forumperm(forumMap.get("viewperm"), groupid,extgroupids)) {
				request.setAttribute("show_message", getMessage(request, "forum_nopermission_2"));
				return mapping.findForward("nopermission");
			}
		}
		boolean allowgetattach = !Common.empty(forumMap.get("allowgetattach")) || (!usergroups.get("allowgetattach").equals("0") && forumMap.get("getattachperm").equals("")) || Common.forumperm(forumMap.get("getattachperm"), groupid,extgroupids);  
		request.setAttribute("allowgetattach",allowgetattach);
		boolean modertar = Common.ismoderator(forumMap.get("ismoderator"), members);
		request.setAttribute("modertar", modertar);
		String extcredit = settings.get("extcredits");
		Map<Integer,Map<String,String>> extcredits=dataParse.characterParse(extcredit, true);
		String formulaperm=forumMap.get("formulaperm");
		if(formulaperm.length()>0){
			Map<String,String> messages=Common.forumformulaperm(formulaperm, members,modertar, extcredits,resources,locale);
			if(messages!=null){
				request.setAttribute("show_message", getMessage(request, "forum_permforum_nopermission", messages.get("formulamessage"),messages.get("usermsg")));
				return mapping.findForward("nopermission");
			}				
		}
		if(usergroups!=null && Common.toDigit(threads.get("readperm"))>Common.toDigit(usergroups.get("readaccess")) && !modertar && !threads.get("authorid").equals(uid+"")){
			request.setAttribute("show_message", getMessage(request, "thread_nopermission", threads.get("readperm")));
			return mapping.findForward("nopermission");
		}
		if(Common.toDigit(threads.get("digest"))>0 && (usergroups==null || usergroups.get("allowviewdigest").equals("0")) && !threads.get("authorid").equals(uid+"")){
			request.setAttribute("show_message", getMessage(request, "thread_digest_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		String password=forumMap.get("password");
		if(password!=null&&!"".equals(password))
		{
			 if(!password.equals(session.getAttribute("fidpw"+threads.get("fid")))){
				request.setAttribute("fid", threads.get("fid"));
				return mapping.findForward("toForumdisplay_passwd");
			}
		}
		List<Map<String,String>> postlist = dataBaseService.executeQuery(posthql);
		String parnum = settings.get("maxsmilies");
		int parm = Common.intval(parnum);
		List list = new ArrayList();
		String smilies_parse = ((Map<String,String>)request.getAttribute("smilies_parse")).get("smilies_parse");
		List<Map<String,String>> smilieslist = dataParse.characterParse(smilies_parse);
		if(postlist!=null){
			String showjavacode = settings.get("showjavacode");
			for (Map<String,String> posts:postlist) {
				Map resmap = new HashMap();
				String message = posts.get("message").replace("$", Common.SIGNSTRING);
				Jspruncode jspruncode = new Jspruncode();
				message = jspruncode.parseCodeP(message, resources, locale,true);
				posts.put("message", message);
				String messagse = jspruncode(request,parm,threads,posts,null,smilieslist,showjavacode,resources,locale,members,modertar,jspruncode);
				messagse = replacemessage(messagse,jspruncode,Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(posts.get("htmlon"))<=0,0,null);
				posts.put("message", messagse);
				if(allowgetattach && Common.intval(posts.get("attachment"))>0){
					request.setAttribute("attatype", attatypemap);
					messagse = messagse.replaceAll("\\[attach\\]\\d+\\[/attach\\]", "");
					posts.put("message", messagse);
					List<Map<String,String>> attalist = dataBaseService.executeQuery("select * from jrun_attachments where pid="+posts.get("pid"));
					for(Map<String,String> attach:attalist){
						if(!attach.get("price").equals("0")){
							if(!modertar&&!attach.get("uid").equals(uid+"")){
								List<Map<String,String>> payloglist = dataBaseService.executeQuery("select uid from jrun_attachpaymentlog where aid="+attach.get("aid")+" and uid="+uid);
									if(payloglist==null || payloglist.size()<=0){
										attach.remove("attachment");
									}	
							}
						}
					}
					resmap.put(posts, attalist);
				}else{
					resmap.put(posts, null);
				}
				list.add(resmap);
			}
		}
		request.setAttribute("postlist", list);
		request.setAttribute("thread", threads);
		return mapping.findForward("printtable");
	}
	@SuppressWarnings("unchecked")
	public ActionForward tradeinfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int tid = Common.toDigit(request.getParameter("tid"));
		int pid = Common.toDigit(request.getParameter("pid"));
		request.setAttribute("attatype",attatypemap);
		HttpSession session = request.getSession();
		int uid = (Integer)session.getAttribute("jsprun_uid");
		Map<String, String> settings = ForumInit.settings;
		List<Map<String,String>> threadpost = dataBaseService.executeQuery("select t.*,f.threadcaches,f.fup,f.name,f.allowmediacode,f.allowpostspecial,f.allowbbcode,f.allowimgcode,f.jammer,f.allowsmilies,f.displayorder as dis,f.type,f.allowspecialonly,f.allowshare,f.allowhtml,f.alloweditpost,f.autoclose,f.styleid from jrun_threads t left join jrun_forums f on t.fid=f.fid  where  t.displayorder>=0 and t.tid="+tid);
		if (threadpost == null || threadpost.size()<=0) {
			request.setAttribute("errorInfo", getMessage(request, "thread_nonexistence"));
			return mapping.findForward("showMessage");
		}
		Map<String,String> threads = threadpost.get(0);
		request.setAttribute("fid", Short.valueOf(threads.get("fid")));
		threadpost=null;
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		int readaccess = Common.toDigit(usergroups.get("readaccess"));
		request.setAttribute("readaccess", readaccess);
		Members members = (Members)session.getAttribute("user");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		short styleid=Short.valueOf(threads.get("styleid"));
		if(styleid>0){
			request.setAttribute("styleid",styleid);
		}
		String modadd2 = "";String modadd1 = "";
		if(members!=null&&members.getAdminid()==3){
			modadd1 = ", m.uid AS ismoderator ";
			modadd2 = " LEFT JOIN jrun_moderators m ON m.uid="+uid+" AND m.fid=ff.fid ";
		}
		List<Map<String, String>> forumslist = dataBaseService.executeQuery("SELECT ff.modrecommend, ff.viewperm, a.allowview,a.allowreply,a.allowgetattach,ff.postperm, ff.typemodels,ff.threadtypes,ff.replyperm,ff.getattachperm,ff.password,ff.formulaperm"+modadd1+" FROM  jrun_forumfields ff LEFT JOIN jrun_access a ON a.uid="+ uid+ " AND a.fid=ff.fid "+modadd2+"WHERE ff.fid = "+threads.get("fid"));
		Map<String, String> forumMap = forumslist.get(0);
		String modrecommend = forumMap.get("modrecommend");
		Map modrecommendMap = dataParse.characterParse(modrecommend, false);
		request.setAttribute("modrecommendMap", modrecommendMap);
		forumslist = null;
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String extgroupids=members!=null?members.getExtgroupids():null;
		if (Common.empty(forumMap.get("allowview"))) {
			if (Common.empty(forumMap.get("viewperm")) && (usergroups==null || usergroups.get("readaccess").equals("0"))) {
				request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			} else if (!Common.empty(forumMap.get("viewperm"))&& !Common.forumperm(forumMap.get("viewperm"), groupid,extgroupids)) {
				request.setAttribute("show_message", getMessage(request, "forum_nopermission_2"));
				return mapping.findForward("nopermission");
			}
		}
		boolean allowgetattach = !Common.empty(forumMap.get("allowgetattach")) || (!usergroups.get("allowgetattach").equals("0") && forumMap.get("getattachperm").equals("")) || Common.forumperm(forumMap.get("getattachperm"), groupid,extgroupids);  
		request.setAttribute("allowgetattach",allowgetattach);
		boolean modertar = Common.ismoderator(forumMap.get("ismoderator"), members);
		request.setAttribute("modertar", modertar);
		String extcredit = settings.get("extcredits");
		Map<Integer,Map<String,String>> extcredits=dataParse.characterParse(extcredit, true);
		String formulaperm=forumMap.get("formulaperm");
		if(formulaperm.length()>0){
			MessageResources resources = getResources(request);
			Locale locale = getLocale(request);
			Map<String,String> messages=Common.forumformulaperm(formulaperm, members,modertar, extcredits,resources,locale);
			if(messages!=null){
				request.setAttribute("show_message", getMessage(request, "forum_permforum_nopermission", messages.get("formulamessage"),messages.get("usermsg")));
				return mapping.findForward("nopermission");
			}				
		}
		if(usergroups!=null && Common.toDigit(threads.get("readperm"))>Common.toDigit(usergroups.get("readaccess")) && !modertar && !threads.get("authorid").equals(uid+"")){
			request.setAttribute("show_message", getMessage(request, "thread_nopermission", threads.get("readperm")));
			return mapping.findForward("nopermission");
		}
		if(Common.toDigit(threads.get("digest"))>0 && usergroups.get("allowviewdigest").equals("0") && !threads.get("authorid").equals(uid+"")){
			request.setAttribute("show_message", getMessage(request, "thread_digest_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		String password=forumMap.get("password");
		if(password!=null&&!"".equals(password))
		{
			 if(!password.equals(session.getAttribute("fidpw"+threads.get("fid")))){
				request.setAttribute("fid", threads.get("fid"));
				return mapping.findForward("toForumdisplay_passwd");
			}
		}
		if(settings.get("forumjump").equals("1")){
			List<Map<String,String>> forumlist=dataParse.characterParse(settings.get("forums"));
			if("1".equals(settings.get("jsmenu_1"))){
				request.setAttribute("forummenu", Common.forumselect(forumlist,groupid,extgroupids, threads.get("fid")));
			}
			else{
				request.setAttribute("forumselect", Common.forumselect(forumlist,false, false,groupid,extgroupids,null));
			}				
		}
		String extra=request.getParameter("extra");
		String forumname=threads.get("name");
		Map<String,String> ftpmap = dataParse.characterParse(settings.get("ftp"), false);
		List<Map<String,String>> trades = dataBaseService.executeQuery("select * from jrun_trades where tid="+tid+" and pid="+pid+" order by displayorder");
		StringBuffer tradesaids = new StringBuffer();
		StringBuffer tradespids = new StringBuffer();
		Map<String,String> attachmaps = new HashMap<String,String>();
		for(Map<String,String>trade:trades){
			if(!trade.get("expiration").equals("0")){
				float expiration = ((float)Common.toDigit(trade.get("expiration"))-timestamp)/86400;
				if(expiration>0){
					double expirationhour = Math.floor((expiration-Math.floor(expiration))*24);
					trade.put("expirationhour", (int)expirationhour+"");
					trade.put("expiration", (int)Math.floor(expiration)+"");
				}else{
					trade.put("expiration", "-1");
				}
			}
			tradesaids.append(","+trade.get("aid"));
			tradespids.append(","+trade.get("pid"));
		}
		if(tradespids.length()>0){
			List<Map<String,String>> attachs = dataBaseService.executeQuery("SELECT a.* FROM jrun_attachments a WHERE a.pid IN ("+tradespids.substring(1)+")");
			for(Map<String,String> attach:attachs){
				if(attach.get("isimage").equals("1")&&tradesaids.length()>0&&Common.in_array(tradesaids.substring(1).split(","),attach.get("aid"))){
					String url = attach.get("remote").equals("1")?ftpmap.get("attachurl"):settings.get("attachurl");
					url = url+"/"+attach.get("attachment");
					url = attach.get("thumb").equals("1")?url+".thumb.jpg":url;
					attachmaps.put(attach.get("pid"), url);
				}
			}
		}
		request.setAttribute("attachmap", attachmaps);
		Map<String,String> trade = trades.size()>0?trades.get(0):null;
		request.setAttribute("trade", trade);
		request.setAttribute("thread", threads);
		Map tradetypes = dataParse.characterParse(settings.get("tradetypes"), false);
		request.setAttribute("tradetypes", tradetypes);
		String navigation=null;
		if(threads.get("type").equals("sub")){
			List<Map<String,String>> forumsmap = dataBaseService.executeQuery("select fid,name from jrun_forums where fid="+threads.get("fup"));
			String fupforumname=forumsmap.get(0).get("name");
			navigation="&raquo; <a href=\"forumdisplay.jsp?fid="+forumsmap.get(0).get("fid")+"\">"+fupforumname+"</a> &raquo; <a href=\"forumdisplay.jsp?fid="+threads.get("fid")+(extra!=null ? "&amp;"+extra.replaceAll("^(&amp;)*", ""):"")+"\">"+forumname+"</a>";
			forumsmap = null;
			request.setAttribute("navtitle",Common.strip_tags(trade!=null?trade.get("subject"):""+" - "));
		}else{
			navigation="&raquo; <a href=\"forumdisplay.jsp?fid="+threads.get("fid")+(extra!=null?"&amp;"+extra.replaceAll("^(&amp;)*", ""):"")+"\">"+forumname+"</a>";
			request.setAttribute("navtitle", Common.strip_tags(trade!=null?trade.get("subject"):""+" - "));
		}
		navigation = navigation+" &raquo; <a href=\"viewthread.jsp?tid="+tid+(extra!=null ? "&amp;"+extra.replaceAll("^(&amp;)*", ""):"")+"\">"+threads.get("subject")+"</a>";
		request.setAttribute("navigation", navigation.toString());
		String replyperm = forumMap.get("replyperm");
		boolean allowpostreply = ((threads.get("closed").equals("0")&&!checkautoclose(threads)) || modertar) && ((replyperm.equals("") && usergroups.get("allowreply").equals("1"))||(!replyperm.equals("")&&Common.forumperm(replyperm, groupid,extgroupids))||!Common.empty(forumMap.get("allowreply")));
		request.setAttribute("allowpostreply", allowpostreply);
		String dateformat = (String)session.getAttribute("dateformat");
		String timeoffset= (String)session.getAttribute("timeoffset");
		String timeformat = (String)session.getAttribute("timeformat");
		List<Map<String,String>> postlist = dataBaseService.executeQuery("SELECT p.*, m.uid,u.readaccess,m.username, m.groupid, m.adminid, m.regdate, m.lastactivity, m.posts, m.digestposts, m.oltime, m.pageviews, m.credits, m.extcredits1, m.extcredits2, m.extcredits3, m.extcredits4, m.extcredits5, m.extcredits6, m.extcredits7, m.extcredits8, m.email, m.gender, m.showemail, mf.nickname, mf.site, mf.icq, mf.qq, mf.yahoo, mf.msn, mf.taobao, mf.alipay, mf.location, mf.medals, mf.avatar, mf.avatarwidth, mf.avatarheight, mf.customstatus, mf.spacename, mf.buyercredit, mf.sellercredit FROM jrun_posts p LEFT JOIN jrun_members m ON m.uid=p.authorid LEFT JOIN jrun_memberfields mf ON mf.uid=m.uid LEFT JOIN jrun_usergroups as u ON m.groupid=u.groupid WHERE pid='"+pid+"'");
		Map<String,String> post = postlist.size()>0?postlist.get(0):null;
		if(post==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		request.setAttribute("post", post);
		int buycredit = Common.intval(post.get("buyercredit"));
		int shellcredit =  Common.intval(post.get("sellercredit"));
		String ec_credit = settings.get("ec_credit");
		Map buycreditMap = dataParse.characterParse(ec_credit, true);
		Map buysMap = (Map) buycreditMap.get("rank");
		String postbuycredit = "0";
		String postshellcredit = "0";
		if (buysMap != null) {
			Iterator<Entry> it = buysMap.entrySet().iterator();
			if(buycredit>0){
				while (it.hasNext()) {
					Entry temp = it.next();
					Object key = temp.getKey();
					if (buycredit <= Integer.valueOf(temp.getValue().toString())) {
						postbuycredit = key.toString();
						break;
					}
				}
			}
			if(shellcredit>0){
				while (it.hasNext()) {
					Object key = it.next();
					if (shellcredit <= Integer.valueOf(buysMap.get(key).toString())) {
						postshellcredit = key.toString();
						break;
					}
				}
			}
		}
		request.setAttribute("shellcredit", postshellcredit);
		request.setAttribute("buycredit", postbuycredit);
		String spacedata = settings.get("spacedata");
		Map spacemap = dataParse.characterParse(spacedata,false);
		request.setAttribute("spacedata", spacemap);
		List<String> custom = new ArrayList<String>();
		custom.add("uid");custom.add("posts");custom.add("digest");
		custom.add("credits");
		Set<Integer> extcreSet = extcredits.keySet();
		for(Integer key:extcreSet){
			custom.add("extcredits"+key);
		}
		List<Map<String,String>> profiledlist = dataBaseService.executeQuery("select fieldid,title from jrun_profilefields where available=1 and invisible=0 order by displayorder");
		String profileds = "";
		List<String> profields = new ArrayList<String>();
		for(Map<String,String> profiled :profiledlist){
			profileds = profileds+",mf.field_"+profiled.get("fieldid");
			profields.add("field_"+profiled.get("fieldid"));
		}
		custom.addAll(profields);
		custom.add("readperm");custom.add("gender");custom.add("location");
		custom.add("oltime");custom.add("regtime");custom.add("lastdate");
		String custominfo = settings.get("customauthorinfo");
		Map custominfoMap = dataParse.characterParse(custominfo, false);
		Map<String,String[]> custommap = getCreditsunit(extcredits);
		String extname[] = custommap.get("extname");
		String extunit[] = custommap.get("extunit");
		custommap = null;
		request.setAttribute("custominfo", customeinfo(request,extname,extunit,custominfoMap,post,profiledlist,custom,timeoffset,dateformat));
		int showsettings=Integer.valueOf(settings.get("showsettings"));
		int showcustom = members!=null?members.getCustomshow():26;
		boolean showimages;
		if(showcustom>=2){
			showimages=(showsettings&1)>0;
		}else{
			showimages=(showcustom&1)>0;
		}
		request.setAttribute("showimag", showimages);
		int creditstransid = Common.toDigit(settings.get("creditstrans")); 
		Map extcrditsMap = dataParse.characterParse(extcredit, true);
		Map creditstrans=(Map)extcrditsMap.get(creditstransid);
		request.setAttribute("creditstrans", creditstrans);
		String extcreditname = creditstrans!=null?(String)creditstrans.get("title"):"";
		String parnum = settings.get("maxsmilies");
		Map<String,String> bbcodesres = (Map<String,String>)request.getAttribute("bbcodes");
		Map<String,Map<String,String>> bbcodes = dataParse.characterParse(bbcodesres.get("bbcodes"), false);
		String smilies_parse = ((Map<String,String>)request.getAttribute("smilies_parse")).get("smilies_parse");
		List<Map<String,String>> smilieslist = dataParse.characterParse(smilies_parse);
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String showjavacode = settings.get("showjavacode");
		Jspruncode jspruncode = new Jspruncode();
		String message = jspruncode(request,Common.intval(parnum),threads,post,bbcodes,smilieslist,showjavacode,resources,locale,members,modertar,jspruncode);
		String ftpurl = ftpmap.get("attachurl");
		post.put("message", message);
		Map attamap = parseAttach(request,settings,extcreditname,post,showimages,uid,allowgetattach,readaccess,ftpurl,dateformat+" "+timeformat,timeoffset,modertar,tradesaids.length()>0?tradesaids.substring(1):null,false);
		message = (String)attamap.get("message");
		message = replacemessage(message,jspruncode,Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(post.get("htmlon"))<=0,0,null);
		post.put("message", message);
		int attachment = (Integer)attamap.get("attachment");
		if(attachment!=-1){
			post.put("attachment", attachment+"");
		}
		request.setAttribute("attaurl", attamap.get("attalist"));
		return mapping.findForward("trade_info");
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public ActionForward viewblog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		int tid = Common.toDigit(request.getParameter("tid"));
		HttpSession session = request.getSession();
		int uid = (Integer)session.getAttribute("jsprun_uid");
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		String spacestatus=settings.get("spacestatus");
		String bordurl = (String)session.getAttribute("boardurl");
		if(spacestatus.equals("0")){
			Common.requestforward(response, bordurl);
			return null;
		}
		List<Map<String,String>> threadpost = dataBaseService.executeQuery("select t.*,f.threadcaches,f.fup,f.name,f.allowmediacode,f.allowimgcode,f.allowpostspecial,f.allowbbcode,f.jammer,f.allowsmilies,f.displayorder as dis,f.type,f.allowspecialonly,f.allowshare,f.allowhtml,f.alloweditpost,f.autoclose from jrun_threads t left join jrun_forums f on t.fid=f.fid  where  t.displayorder>=0 and t.tid="+tid);
		if (threadpost == null || threadpost.size()<=0) {
			request.setAttribute("errorInfo", getMessage(request, "thread_nonexistence"));
			return mapping.findForward("showMessage");
		}
		Map<String,String> threads = threadpost.get(0);
		String dateformat = (String)session.getAttribute("dateformat");
		String timeoffset=(String)session.getAttribute("timeoffset");
		String timeformat = (String)session.getAttribute("timeformat");
		Memberspaces memberspace = spaceServer.findMemberspace(Common.toDigit(threads.get("authorid")));
		if(memberspace==null){
			request.setAttribute("errorInfo", getMessage(request, "space_no_unseal"));
			return mapping.findForward("showMessage");
		}
		request.setAttribute("memberspace", memberspace);
		request.setAttribute("fid", Short.valueOf(threads.get("fid")));
		threadpost=null;
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		int readaccess = Common.toDigit(usergroups.get("readaccess"));
		request.setAttribute("readaccess", readaccess);
		Members members = (Members)session.getAttribute("user");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		String modadd2 = "";String modadd1 = "";
		if(members!=null&&members.getAdminid()==3){
			modadd1 = ", m.uid AS ismoderator ";
			modadd2 = " LEFT JOIN jrun_moderators m ON m.uid="+uid+" AND m.fid=ff.fid ";
		}
		List<Map<String, String>> forumslist = dataBaseService.executeQuery("SELECT ff.modrecommend, ff.viewperm, a.allowview,a.allowreply,a.allowgetattach,ff.postperm, ff.typemodels,ff.threadtypes,ff.replyperm,ff.getattachperm,ff.password,ff.formulaperm"+modadd1+" FROM  jrun_forumfields ff LEFT JOIN jrun_access a ON a.uid="+ uid+ " AND a.fid=ff.fid "+modadd2+"WHERE ff.fid = "+threads.get("fid"));
		Map<String, String> forumMap = forumslist.get(0);
		String modrecommend = forumMap.get("modrecommend");
		Map modrecommendMap = dataParse.characterParse(modrecommend, false);
		request.setAttribute("modrecommendMap", modrecommendMap);
		forumslist = null;
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		request.setAttribute("nowtime", timestamp);
		String extgroupids=members!=null?members.getExtgroupids():null;
		if (Common.empty(forumMap.get("allowview"))) {
			if (Common.empty(forumMap.get("viewperm")) && (usergroups==null || usergroups.get("readaccess").equals("0"))) {
				request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			} else if (!Common.empty(forumMap.get("viewperm"))&& !Common.forumperm(forumMap.get("viewperm"), groupid,extgroupids)) {
				request.setAttribute("show_message", getMessage(request, "forum_nopermission_2"));
				return mapping.findForward("nopermission");
			}
		}
		boolean allowgetattach = !Common.empty(forumMap.get("allowgetattach")) || (!usergroups.get("allowgetattach").equals("0") && forumMap.get("getattachperm").equals("")) || Common.forumperm(forumMap.get("getattachperm"), groupid,extgroupids);  
		request.setAttribute("allowgetattach",allowgetattach);
		boolean modertar = Common.ismoderator(forumMap.get("ismoderator"), members);
		request.setAttribute("modertar", modertar);
		String extcredit = settings.get("extcredits");
		Map<Integer,Map<String,String>> extcredits=dataParse.characterParse(extcredit, true);
		String formulaperm=forumMap.get("formulaperm");
		if(formulaperm.length()>0){
			MessageResources resources = getResources(request);
			Locale locale = getLocale(request);
			Map<String,String> messages=Common.forumformulaperm(formulaperm, members,modertar, extcredits,resources,locale);
			if(messages!=null){
				request.setAttribute("show_message", getMessage(request, "forum_permforum_nopermission", messages.get("formulamessage"),messages.get("usermsg")));
				return mapping.findForward("nopermission");
			}				
		}
		if(usergroups!=null && Common.toDigit(threads.get("readperm"))>Common.toDigit(usergroups.get("readaccess")) && !modertar && !threads.get("authorid").equals(uid+"")){
			request.setAttribute("show_message", getMessage(request, "thread_nopermission", threads.get("readperm")));
			return mapping.findForward("nopermission");
		}
		if(Common.toDigit(threads.get("digest"))>0 && usergroups.get("allowviewdigest").equals("0") && !threads.get("authorid").equals(uid+"")){
			request.setAttribute("show_message", getMessage(request, "thread_digest_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		String password=forumMap.get("password");
		if(password!=null&&!"".equals(password))
		{
			 if(!password.equals(session.getAttribute("fidpw"+threads.get("fid")))){
				request.setAttribute("fid", threads.get("fid"));
				return mapping.findForward("toForumdisplay_passwd");
			}
		}
		Map<String,String> ftpmap = dataParse.characterParse(settings.get("ftp"), false);
		request.setAttribute("thread", threads);
		Map tradetypes = dataParse.characterParse(settings.get("tradetypes"), false);
		request.setAttribute("tradetypes", tradetypes);
		String replyperm = forumMap.get("replyperm");
		boolean allowpostreply = ((threads.get("closed").equals("0")&&!checkautoclose(threads)) || modertar) && ((replyperm.equals("") && usergroups.get("allowreply").equals("1"))||(!replyperm.equals("")&&Common.forumperm(replyperm, groupid,extgroupids))||!Common.empty(forumMap.get("allowreply")));
		request.setAttribute("allowpostreply", allowpostreply);
		List<Map<String, String>> taglist = dataBaseService.executeQuery("SELECT tagname FROM jrun_threadtags WHERE tid="+tid);
		if(taglist==null || taglist.size()<=0){
			request.setAttribute("taglist", null);
		}else{
			request.setAttribute("taglist", taglist);
		}
		Map<String, String> faqs = (Map<String, String>) request.getAttribute("faqs");
		request.setAttribute("faqs", dataParse.characterParse(faqs.get("faqs"), false));
		List<Map<String,String>> threadpostlist = dataBaseService.executeQuery("SELECT p.*, m.uid,u.readaccess,u.stars,u.grouptitle,m.username, m.groupid, m.adminid, m.regdate, m.lastactivity, m.posts, m.digestposts, m.credits, mf.medals, mf.spacename, mf.buyercredit, mf.sellercredit  FROM jrun_posts p LEFT JOIN jrun_members m ON m.uid=p.authorid LEFT JOIN jrun_memberfields mf ON mf.uid=m.uid LEFT JOIN jrun_usergroups as u ON m.groupid=u.groupid WHERE tid='"+tid+"' and p.first='1' order by pid limit 1");
		String countsql = "select count(*) as count from jrun_posts where tid='"+tid+"'";
		List<Map<String, String>> count = dataBaseService.executeQuery(countsql);
		int threadcount = Integer.valueOf(count.get(0).get("count"));threadcount--;
		int lpp = members != null && members.getPpp() > 0 ? members.getPpp(): Integer.valueOf(settings.get("postperpage"));
		int page = Math.max(Common.intval(request.getParameter("page")), 1);
		int startlimit = (page - 1) * lpp;
		if(startlimit>0&&startlimit>=threadcount){
			page=threadcount/lpp;
			startlimit=(page - 1) * lpp;
		}else if(startlimit<0){
			page=1;
			startlimit=0;
		}
		LogPage logpage = new LogPage(threadcount,lpp,page);
		request.setAttribute("logpage", logpage);
		request.setAttribute("lpp", lpp);
		request.setAttribute("currentPage", page);
		request.setAttribute("url", "blog.jsp?tid="+tid);
		List<Map<String,String>> postlist = dataBaseService.executeQuery("SELECT p.*, m.uid,u.readaccess,u.stars,u.grouptitle,m.username, m.groupid, m.adminid, m.regdate, m.lastactivity, m.posts, m.digestposts, m.credits, mf.medals, mf.spacename, mf.buyercredit, mf.sellercredit  FROM jrun_posts p LEFT JOIN jrun_members m ON m.uid=p.authorid LEFT JOIN jrun_memberfields mf ON mf.uid=m.uid LEFT JOIN jrun_usergroups as u ON m.groupid=u.groupid WHERE tid='"+tid+"' and p.first<>'1' order by pid limit "+startlimit+","+lpp);
		int showsettings=Integer.valueOf(settings.get("showsettings"));
		int showcustom = members!=null?members.getCustomshow():26;
		boolean showimages;
		if(showcustom>=2){
			showimages=(showsettings&1)>0;
		}else{
			showimages=(showcustom&1)>0;
		}
		request.setAttribute("showimag", showimages);
		int creditstransid = Common.toDigit(settings.get("creditstrans")); 
		Map extcrditsMap = dataParse.characterParse(extcredit, true);
		Map creditstrans=(Map)extcrditsMap.get(creditstransid);
		request.setAttribute("creditstrans", creditstrans);
		String extcreditname = creditstrans!=null?(String)creditstrans.get("title"):"";
		Map  attamaps = new HashMap();
		String smilies_parse = ((Map<String,String>)request.getAttribute("smilies_parse")).get("smilies_parse");
		List<Map<String,String>> smilieslist = dataParse.characterParse(smilies_parse);
		postlist.add(0, threadpostlist.get(0));
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		int parnum = Common.intval(settings.get("maxsmilies"));
		Map<String,String> bbcodesres = (Map<String,String>)request.getAttribute("bbcodes");
		Map<String,Map<String,String>> bbcodes = dataParse.characterParse(bbcodesres.get("bbcodes"), false);
		String showjavacode = settings.get("showjavacode");
		for(Map<String,String> post:postlist){
			String message = post.get("message").replace("$", Common.SIGNSTRING);
			Jspruncode jspruncode = new Jspruncode();
			message = jspruncode.parseCodeP(message, resources, locale,false);
			post.put("message", message);
			message = jspruncode(request,parnum,threads,post,bbcodes,smilieslist,showjavacode,resources,locale,members,modertar,jspruncode);
			String ftpurl = ftpmap.get("attachurl");
			post.put("message", message);
			Map attamap = parseAttach(request,settings,extcreditname,post,showimages,uid,allowgetattach,readaccess,ftpurl,dateformat+" "+timeformat,timeoffset,modertar,null,false);
			message = (String)attamap.get("message");
			message = replacemessage(message,jspruncode,Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(post.get("htmlon"))<=0,0,null);
			int attachment = (Integer)attamap.get("attachment");
			if(attachment!=-1){
				post.put("attachment", attachment+"");
			}
			post.put("message", message);
			attamaps.put(post.get("pid"), attamap.get("attalist"));
		}
		request.setAttribute("post", postlist.get(0));
		postlist.remove(0);
		request.setAttribute("postlist",postlist);
		request.setAttribute("attamaps", attamaps);
		String layout = memberspace.getLayout();
		Map menuMap = new HashMap();
		if (layout.indexOf("[myblogs]") != -1) {
			menuMap.put("myblogs", "ok");
		}
		if (layout.indexOf("[mythreads]") != -1) {
			menuMap.put("mythreads", "ok");
		}
		if (layout.indexOf("[myreplies]") != -1) {
			menuMap.put("myreplies", "ok");
		}
		if (layout.indexOf("[myrewards]") != -1) {
			menuMap.put("myrewards", "ok");
		}
		if (layout.indexOf("[mytrades]") != -1) {
			menuMap.put("mytrades", "ok");
		}
		if (layout.indexOf("[myfavforums]") != -1) {
			menuMap.put("myfavforums", "ok");
		}
		if (layout.indexOf("[myfavthreads]") != -1) {
			menuMap.put("myfavthreads", "ok");
		}
		request.setAttribute("menuMap", menuMap);
		Members member = (Members)memberService.findMemberById(Common.toDigit(threads.get("authorid")));
		Memberfields memberfield = memberService.findMemberfieldsById(Common.toDigit(threads.get("authorid")));
		request.setAttribute("member", member);
		request.setAttribute("memberfild", memberfield);
		request.setAttribute("attatype", attatypemap);
		return mapping.findForward("sapce_topic");
	}
	private String jammer(){
		StringBuffer randomstr = new StringBuffer();
		for(int i = 0; i < Common.rand(5, 15); i++) {
			randomstr.append((char)Common.rand(32, 59));
			randomstr.append(" ");
			randomstr.append((char)Common.rand(63, 126));
		}
		return Common.rand(1)>0?"<font class=\"displayfont\">"+randomstr+"</font><br />":"<br /><span style=\"display:none\">"+randomstr+"</span>";
	}
	private boolean checkautoclose(Map<String,String> threads){
		int timestamp = Common.time();
		String closedby=null;
		short autoclose=Short.valueOf(threads.get("autoclose"));
		if(autoclose!=0){
			closedby = autoclose > 0 ? "dateline" : "lastpost";
			autoclose=(short)(Math.abs(autoclose)*86400);
			if(timestamp-Integer.valueOf(threads.get(closedby))>autoclose){
				return true;
			}
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	private Map<String, Integer> multi(HttpServletRequest request,HttpServletResponse response, int uid, String sql, String url,String ajaxtarget,int lpps) {
		HttpSession session=request.getSession();
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		Members member = uid > 0 ? (Members)session.getAttribute("user") : null;
		List<Map<String, String>> count = dataBaseService.executeQuery(sql);
		int threadcount = Integer.valueOf(count.get(0).get("count"));
		int lpp = member != null && member.getPpp() > 0 ? member.getPpp(): Integer.valueOf(settings.get("postperpage"));
		lpp = lpps>0?lpps:lpp;
		int page = Math.max(Common.intval(request.getParameter("page")), 1);
		Map<String,Integer> multiInfo=Common.getMultiInfo(threadcount, lpp, page);
		page=multiInfo.get("curpage");
		Map<String,Object> multi=Common.multi(threadcount, lpp, page, url, 1000000000, 10, true, false, ajaxtarget);
		request.setAttribute("multi", multi);
		request.setAttribute("lpp", lpp);
		request.setAttribute("currentPage", page);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("beginsize", multiInfo.get("start_limit"));
		map.put("pagesize", lpp);
		return map;
	}
	private String replacemessage(String message,Jspruncode jspruncode,boolean allowhtml,int highlightstatus,String highlight){
		int count = jspruncode.getCodecount();
		List<String> codelist = jspruncode.getCodelist();
		for(int i=0;i<count;i++){
			String str = codelist.get(i);
			message = StringUtils.replace(message, "[\tJSPRUN_CODE_"+i+"\t]", str);
		}
		if(highlightstatus>0){
			Pattern p = Pattern.compile("(?s)(^|>)([^<]+)(?=<|$)");
			Matcher m = p.matcher(message);
			if(m.find()){
				StringBuffer b = new StringBuffer();
				String text = m.group(2);
				String prepend = m.group(1);
				text = text.replace("\\", "\\\\");
				text = text.replace("\\\"", "\"");
				String []highlightarray = highlight.split("\\+");
				for(String keyword:highlightarray){
					if(!"".equals(keyword)){
						text = text.replace(keyword, "<strong><font color=\"#FF0000\">"+keyword+"</font></strong>");
					}
				}
				prepend = prepend.replace("$",Common.SIGNSTRING);
				text = text.replace("$",Common.SIGNSTRING);
				m.appendReplacement(b, prepend+text);
				message = m.appendTail(b).toString();
			}
		}
		message = message.replace(Common.SIGNSTRING, "$");
		if(allowhtml){
			message = message.replaceAll("\t", "&nbsp; &nbsp; &nbsp; &nbsp; ").replace("   ", "&nbsp; &nbsp;").replace("  ", "&nbsp;&nbsp;");
		}
		return message;
	}
	@SuppressWarnings({ "unused", "unchecked" })
	private Map<String, List> customeinfo(HttpServletRequest request,String[]extname,String[] extunit, Map customMap,Map<String,String>usermap,List<Map<String,String>>profiled,List<String>custom,String timeoffset,String dateformat) {
		Map resultMap = new HashMap();
			List<String> profievalue = new ArrayList<String>();
			for(Map<String,String> profile:profiled){
				profievalue.add("<dt>"+profile.get("title")+"</dt><dd>"+(usermap.get("field_"+profile.get("fieldid"))==null?"":usermap.get("field_"+profile.get("fieldid")))+"</dd>");
			}
			List<String> customvalue = new ArrayList<String>();
			String location = (usermap.get("location")==null || usermap.get("location").equals("") ? "" : "<dt>"+getMessage(request, "location")+"</dt><dd>"+usermap.get("location")+"</dd>");
			byte gender = Byte.valueOf(usermap.get("gender"));
			String genders = "";
			if (gender == 1) {
				genders = getMessage(request, "a_member_edit_gender_male");
			} else if (gender == 2) {
				genders = getMessage(request, "a_member_edit_gender_female");
			} else{
				genders = getMessage(request, "a_member_edit_gender_secret");
			}
			customvalue.add("<dt>"+getMessage(request, "a_setting_uid")+"</dt><dd>"+usermap.get("uid")+"</dd>");
			customvalue.add("<dt>"+getMessage(request, "a_setting_posts")+"</dt><dd>"+usermap.get("posts")+"</dd>");
			customvalue.add("<dt>"+getMessage(request, "digest")+"</dt><dd>"+usermap.get("digestposts")+"</dd>");
			customvalue.add("<dt>"+getMessage(request, "credits")+"</dt><dd>"+usermap.get("credits")+"</dd>");
			for(int i=0;i<8;i++){
				if(extname[i]!=null){
					int temp = i+1;
					customvalue.add("<dt>"+extname[i]+"</dt><dd>"+usermap.get("extcredits"+temp)+extunit[i]+"</dd>");
				}
			}
			customvalue.addAll(profievalue);
			SimpleDateFormat sf = Common.getSimpleDateFormat(dateformat, timeoffset);
			customvalue.add("<dt>"+getMessage(request, "threads_readperm")+"</dt><dd>"+usermap.get("readaccess")+"</dd>");
			customvalue.add("<dt>"+getMessage(request, "gender")+"</dt><dd>"+genders+"</dd>");
			customvalue.add(location);
			customvalue.add("<dt>"+getMessage(request, "stats_onlinetime")+"</dt><dd>"+usermap.get("oltime")+"&nbsp;&nbsp;"+getMessage(request, "hr")+"</dd>");
			customvalue.add("<dt>"+getMessage(request, "a_setting_regtime")+"</dt><dd>"+Common.gmdate(sf, Common.toDigit(usermap.get("regdate")))+"</dd>");
			customvalue.add("<dt>"+getMessage(request, "lastvisit_2")+"</dt><dd>"+Common.gmdate(sf, Common.toDigit(usermap.get("lastactivity")))+"</dd>");
			List<String> leftlist = new ArrayList<String>();
			List<String> menulist = new ArrayList<String>();
			List<String> spacelist = new ArrayList<String>();
			int size = custom.size();
			if (customMap != null && customMap.get(0) != null) {
				Map<String,Map<String,String>>  customreMap = (Map<String,Map<String,String>>)customMap.get(0);
				for(int i=0;i<size;i++){
					String name = custom.get(i);
					String value = customvalue.get(i);
					if(!value.equals("")){
						Map<String,String> dismap = customreMap.get(name);
						if(dismap!=null&&dismap.get("left")!=null){
							leftlist.add(value);
						}
						if(dismap!=null&&dismap.get("menu")!=null){
							menulist.add(value);
						}
						if(dismap!=null&&dismap.get("special")!=null){
							spacelist.add(value);
						}
					}
				}
			}
			resultMap.put("left", leftlist);
			resultMap.put("menu", menulist);
			resultMap.put("special", spacelist);
			return resultMap;
	}
	private String jspruncode(HttpServletRequest request,int parnum,Map<String,String>threads,Map<String,String> postMap,Map<String,Map<String, String>> bbcodelist,List<Map<String,String>> smilieslist,String showjavacode,MessageResources resources,Locale locale,Members member,boolean ismod,Jspruncode jspruncode) {
		int uid = member==null?0:member.getUid();
		String message = postMap.get("message");
		if(Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(postMap.get("htmlon"))<=0){
			message = Common.htmlspecialchars(message);
			if("1".equals(threads.get("jammer"))&&postMap!=null&&!(uid+"").equals(postMap.get("authorid"))){
				int counts = 0;
				while(message.indexOf("\n")!=-1 && counts<25){
					message = StringUtils.replaceOnce(message, "\n", jammer());
					counts ++;
				}
			}
		}
		message = message.replace("$", Common.SIGNSTRING)+" ";
		if(Common.toDigit(threads.get("allowbbcode"))>0 && Common.intval(postMap.get("bbcodeoff"))<=0){
			if("0".equals(threads.get("special"))&&"1".equals(showjavacode)){
				message = jspruncode.parseCode(message,postMap.get("tid"));
			}
			message = jspruncode.parseCode(message,Integer.valueOf(postMap.get("pid")),resources.getMessage(locale,"copy_code"));
			message = jspruncode.parseJsprunCode(message, resources, locale);
			message = jspruncode.parsetable(message);
			message = jspruncode.parsemedia(message,Common.toDigit(threads.get("allowmediacode"))>0);
			message = jspruncode.parseCustomCode(message,bbcodelist);
			if(!"1".equals(postMap.get("first"))){
				message = message.replaceAll("(?s)\\s*\\[free\\][\n\r]*(.+?)[\n\r]*\\[/free\\]\\s*", "<div class=\"quote\"><h5>"+resources.getMessage(locale, "jspruncode_free")+":</h5><blockquote>$1</blockquote></div>");
			}
		}
		message = jspruncode.parseimg(message,Common.toDigit(threads.get("allowimgcode"))>0);
		if(Common.toDigit(threads.get("allowsmilies"))>0 && Common.intval(postMap.get("smileyoff"))<=0){
			message = relacesmile(message, parnum,smilieslist);
		}
		if(Common.toDigit(threads.get("allowbbcode"))>0 && Common.intval(postMap.get("bbcodeoff"))<=0){
			message = replacehide(request,message,threads,member,ismod);
		}
		message = message.replaceAll("\\n", "<br/>");
		message = message.replaceAll("(?i)<br/>(<TD[^>]*>|<TR[^>]*>|</TR[^>]*>)", "$1");
		message = message.replaceAll("<EM>", "<I>");
		message = message.replaceAll("</EM>", "</I>");
		return message;
	}
	private String relacesmile(String message, int parnum,List<Map<String,String>> smilieslist) {
		for(Map<String,String> smiles:smilieslist){
			if(message.indexOf(smiles.get("code")+" ")!=-1 || message.indexOf(" "+smiles.get("code"))!=-1){
				StringBuffer buffer = new StringBuffer(100);
				buffer.append("<img src='images/smilies/");
				buffer.append(smiles.get("directory"));
				buffer.append("/");
				buffer.append(smiles.get("url"));
				buffer.append("' smilieid='");
				buffer.append(smiles.get("id"));
				buffer.append("' border='0' alt='' /> ");
				message = StringUtils.replace(message, smiles.get("code"), buffer.toString(),parnum);
			}
		}
		smilieslist = null;
		return message;
	}
	private String getforumfounder(HttpServletRequest request,Map<String,String>settings,Map<String,String>userMap){
		if (userMap.get("adminid")!=null && userMap.get("adminid").equals("1")) {
			if(Common.matches(","+settings.get("forumfounders").trim()+",",","+userMap.get("uid")+",")){
				return getMessage(request, "a_setting_initiator");
			}
		}
		return "";
	}
	private void viewthread_updateviews(String delayviewcount,int tid,String path) {
		String timestamp = Common.time()+"";
		if((delayviewcount.equals("1")||delayviewcount.equals("3"))) {
			if(timestamp.substring(8).equals("00")) {
				updateviews(path);
			}
			FileWriter fo = null;BufferedWriter bf=null;
			try {
				fo = new FileWriter(path,true);
				bf = new BufferedWriter(fo);
				bf.write(""+tid);
				bf.newLine();
			}  catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					bf.close();fo.close();
				} catch (IOException e) {
				}
			}
		} else {
			dataBaseService.runQuery("update jrun_threads set views=views+1 where tid="+tid,true);
		}
	}
	private  void updateviews (String path){
		File file = new File(path);
		if(file!=null&&file.exists()){
			FileReader fr =null;
			BufferedReader br = null;
			try {
				Map<String,Integer> tids = new HashMap<String,Integer>();
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				String tid = null;
				Integer views;
				while((tid = br.readLine())!=null){
					views = tids.get(tid);
					if(views==null){
						views = 0;
					}
					tids.put(tid, views+1);
				}
				br.close();
				fr.close();
				file.delete();
				Set<Entry<String,Integer>> tidset = tids.entrySet();
				for(Entry<String,Integer> temp:tidset){
					dataBaseService.runQuery("update jrun_threads set views=views+"+temp.getValue()+" where tid = '"+temp.getKey()+"'",true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private boolean  viewthread_loadcache(int timestamp,String timeoffset,Map<String,String>threads,Map<String,String>settings,String cachedir,HttpServletRequest request,HttpServletResponse response) {
		int postperpage = Common.toDigit(settings.get("postperpage"));
		int cachethreadlife = Common.toDigit(settings.get("cachethreadlife"));
		double livedays = Math.ceil((timestamp-Common.toDigit(threads.get("dateline")))/(double)86400);
		double lastpostdays = Math.ceil((timestamp-Common.toDigit(threads.get("lastpost")))/(double)86400);
		double threadcachemark = 100 - (Common.toDigit(threads.get("dis"))*15+Common.toDigit(threads.get("digest"))*10+Math.min(Common.toDigit(threads.get("views"))/Math.max(livedays,10)*2,50)+Math.max(-10, (15-lastpostdays))+Math.min(Common.toDigit(threads.get("replies"))/postperpage*1.5,15));
		if(threadcachemark<Common.toDigit(threads.get("threadcaches"))){
			Object[]threadcache = (Object[])Common.getCacheInfo(threads.get("tid"), cachedir);
			String fileName = (String)threadcache[1];
			int fileModified=(Integer)threadcache[0];
			File file = new File(fileName);
			if(timestamp-fileModified>cachethreadlife){
				request.setAttribute("fileName",fileName);
				return false;
			}else{
				if(file.exists()&&file.length()>200){
					fileName = fileName.substring(JspRunConfig.realPath.length());
					try {
						request.getRequestDispatcher(fileName).include(request, response);
						if ("1".equals(settings.get("debug"))) {
							long starttime=(Long)request.getAttribute("starttime");
							long endtime=System.currentTimeMillis(); 
							String runTime=Common.number_format((endtime-starttime)/1000f, "0.000000");
							String debugtime = Common.gmdate("HH:mm:ss", fileModified, timeoffset);
							int gzipCompress = Common.intval(settings.get("gzipcompress"));
							String content = "<script type=\"text/javascript\">document.getElementById(\"debuginfo\").innerHTML = \"Update at "
									+ debugtime + ", Processed in "+runTime+" second(s)"+(gzipCompress >0 ? ", Gzip enabled" : "")+".\";</script>";
							response.getWriter().write(content);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return true;
				}
			}
		}
		return false;
	}
	private Map<String,String[]> getCreditsunit(Map<Integer,Map<String,String>> extmap) {
		Map<String,String[]> extnameunitmap = new HashMap<String,String[]>();
		Set<Integer> creditstSet = extmap.keySet();
		String extname[] = new String[8];
		String extunit[] = new String[8];
		for(Integer key : creditstSet){
			Map<String,String> excreditmap = extmap.get(key);
			extname[key-1] = excreditmap.get("title");
			extunit[key-1] = excreditmap.get("unit");
		}
		extnameunitmap.put("extname", extname);
		extnameunitmap.put("extunit", extunit);
		return extnameunitmap;
	}
	@SuppressWarnings("unchecked")
	private Map calcSumreson(List<Polloptions> list) {
		Map resonMap = new HashMap();
		int count = 0;
		for (Polloptions options : list) {
			count += options.getVotes();
			String voterids = options.getVoterids();
			if (!voterids.equals("")) {
				String[] ids = voterids.split("\t");
				for(String id:ids)
				{
					if(resonMap.get(id) == null)
					{
						resonMap.put(id, "reson");
					}
				}
			}
		}
		if(count==0){
			count=1;
		}
		int resonnum = resonMap.size();
		resonMap.put("num", resonnum);
		resonMap.put("ballot", count);
		return resonMap;
	}
	private String calcSparetime(HttpServletRequest request,int expiration) {
		String time = "";
		int nowtime=Common.time();
		int sparetime = expiration - nowtime;
		if(expiration==0){
			return "";
		}
		if (sparetime < 0) {
			time = getMessage(request, "poll_end_view");
		} else {
			int day = sparetime / 86400;
			sparetime = sparetime % 86400;
			int horse = sparetime / (60 * 60);
			sparetime = sparetime % (60 * 60);
			int minute = sparetime / 60;
			time = getMessage(request, "poll_count_down")+":  " + day + " "+getMessage(request, "ipban_days")+" " + horse + " "+getMessage(request, "hr")+" " + minute + " "+getMessage(request, "a_other_crons_minute");
		}
		return time;
	}
	@SuppressWarnings("unchecked")
	private Map parseAttach(HttpServletRequest request,Map<String,String>settings,String extcreditname,Map<String,String> postmap,boolean isshowimage,int uid,boolean allowgetattach,int readaccess,String ftpurl,String dateformat,String timeoffset,boolean modertar,String tradeaid,boolean threadpay){
		Map  amap = new HashMap();
		String attachrefcheck = settings.get("attachrefcheck");
		String attachimgpost = settings.get("attachimgpost");
		String message = postmap.get("message");
		StringBuffer aids = new StringBuffer();
		String sqladd = "";
		int isattach = -1;
		SimpleDateFormat sf = Common.getSimpleDateFormat(dateformat, timeoffset);
		if(!Common.empty(tradeaid)){
			sqladd = " and aid not in ("+tradeaid+")";
		}
		if(allowgetattach&&!(threadpay&&postmap.get("first").equals("1"))){
			String str = message;
			Pattern p = Pattern.compile("\\[attach\\](\\d+)\\[/attach\\]");
		    Matcher m = p.matcher(str);
		    while(m.find()){
		    	String aid = m.group(1);
		    	if(FormDataCheck.isNum(aid)){
					aids.append(aid+",");
				}
		    }
			List<Map<String,String>>attalist = dataBaseService.executeQuery("select aid,dateline,readperm,price,filename,description,filesize,attachment,downloads,isimage,uid,thumb,filetype,remote from jrun_attachments where pid='"+postmap.get("pid")+"'"+sqladd);
			if(attalist!=null&&attalist.size()>0){
				isattach = 0;
				List<Map<String,String>> attalist1 = new ArrayList<Map<String,String>>();
				String [] aidstr = aids.toString().split(",");
				for(Map<String,String>attamap:attalist){
					String aid = attamap.get("aid");
					 String filename = attamap.get("filename").replace('$', '&');
					 boolean flagprice = true; 
					 if(Common.toDigit(attamap.get("price"))>0){
						 if(!modertar&&!attamap.get("uid").equals(uid+"")){
							 List<Map<String,String>> payloglist = dataBaseService.executeQuery("select uid from jrun_attachpaymentlog where aid="+aid+" and uid="+uid);
							 if(payloglist==null || payloglist.size()<=0){
								message = message.replaceAll("\\[attach\\]"+aid+"\\[/attach\\]", "<img src=\"images/attachicons/image.gif\" border=\"0\" class=\"absmiddle\" alt=\"\" /> <strong>"+getMessage(request, "attach_pay")+": "+filename+"</strong>");
								flagprice = false;
							 }
							payloglist=null;
						}
					 }
					if(Common.in_array(aidstr, aid)&&flagprice){
						 String typeimeage = attatypemap.get(attamap.get("filetype"));
						 String filesize = getfilesize(Common.toDigit(attamap.get("filesize")));
						 String datetime = Common.gmdate(sf,Common.toDigit(attamap.get("dateline")));
						 String description = "";
						 String readperm = "";
						 String price = "";
						 String attaurl = attamap.get("remote").equals("1")?ftpurl:settings.get("attachurl");
						 if(!attamap.get("description").equals("")){
							 description = attamap.get("description")+"<br />";
						 }
						 if(Common.toDigit(attamap.get("readperm"))>0){
							 readperm = getMessage(request, "threads_readperm")+": "+attamap.get("readperm")+"<br />";
						 }
						 if(Common.toDigit(attamap.get("price"))>0){
							price = getMessage(request, "magics_price")+": "+extcreditname+attamap.get("price")+"  &nbsp;<a href=\"misc.jsp?action=viewattachpayments&amp;aid="+aid+"\" target=\"_blank\">["+getMessage(request, "pay_view")+"]</a>";
						 }
						 if(attamap.get("isimage").equals("1")){
							 String imageurl = attamap.get("attachment");
							 if(isshowimage && Common.toDigit(attamap.get("readperm"))<=readaccess && attachimgpost.equals("1")){
								 Map<String,String> styles = (Map<String,String>)request.getAttribute("styles");
								 if(attachrefcheck.equals("1")){
									 if(attamap.get("thumb").equals("1")){
										 message = message.replaceAll("\\[attach\\]"+aid+"\\[/attach\\]", "<span style=\"position: absolute; display: none\" id=\"attachs_"+aid+"\" onmouseover=\"showMenu(this.id, 0, 1)\"><img src=\""+styles.get("IMGDIR")+"/attachimg.gif\" border=\"0\"></span><a href=\"###zoom\"><img onclick=\"zoom(this, 'attachment.jsp?aid="+aid+"')\" src=\"attachment.jsp?aid="+aid+"&amp;noupdate=yes&amp;nothumb=yes\" border=\"0\" onmouseover=\"attachimginfo(this, 'attachs_"+aid+"', 1)\" onmouseout=\"attachimginfo(this, 'attachs_"+aid+"', 0, event)\" /></a><div class=\"t_attach\" id=\"attachs_"+aid+"_menu\" style=\"position: absolute; display: none\"><img src=\"images/attachicons/"+typeimeage+"\" border=\"0\" class=\"absmiddle\" alt=\"\" /> <a href=\"attachment.jsp?aid="+aid+"&amp;nothumb=yes\" target=\"_blank\"><strong>"+filename+"</strong></a> ("+filesize+")<br />"+description+price+"<div class=\"t_smallfont\">"+datetime+"</div></div>");
									 }else{
										message = message.replaceAll("\\[attach\\]"+aid+"\\[/attach\\]", "<span style=\"position: absolute; display: none\" id=\"attachs_"+aid+"\" onmouseover=\"showMenu(this.id, 0, 1)\"><img src=\""+styles.get("IMGDIR")+"/attachimg.gif\" border=\"0\"></span><img src=\"attachment.jsp?aid="+aid+"&amp;noupdate=yes\" border=\"0\" onload=\"attachimg(this, 'load')\" onmouseover=\"attachimginfo(this, 'attachs_"+aid+"', 1);attachimg(this, 'mouseover')\" onclick=\"zoom(this, 'attachment.jsp?aid="+aid+"')\" onmouseout=\"attachimginfo(this, 'attachs_"+aid+"', 0, event)\" alt=\"\" /><div class=\"t_attach\" id=\"attachs_"+aid+"_menu\" style=\"position: absolute; display: none\"><img src=\"images/attachicons/"+typeimeage+"\" border=\"0\" class=\"absmiddle\" alt=\"\" /> <a href=\"attachment.jsp?aid="+aid+"&amp;nothumb=yes\" target=\"_blank\"><strong>"+filename+"</strong></a> ("+filesize+")<br />"+description+price+"<div class=\"t_smallfont\">"+datetime+"</div></div>");
									 }
								}else{
									String attachment = attamap.get("attachment");
									 if(attamap.get("thumb").equals("1")){
										 imageurl = attachment+".thumb"+attachment.substring(attachment.lastIndexOf("."));
										 message = message.replaceAll("\\[attach\\]"+aid+"\\[/attach\\]", "<span style=\"position: absolute; display: none\" id=\"attachs_"+aid+"\" onmouseover=\"showMenu(this.id, 0, 1)\"><img src=\""+styles.get("IMGDIR")+"/attachimg.gif\" border=\"0\"></span><a href=\"###zoom\"><img onclick=\"zoom(this, '"+attaurl+"/"+attachment+"')\" src=\""+attaurl+"/"+imageurl+"\" border=\"0\" onmouseover=\"attachimginfo(this, 'attachs_"+aid+"', 1)\" onmouseout=\"attachimginfo(this, 'attachs_"+aid+"', 0, event)\" /></a><div class=\"t_attach\" id=\"attachs_"+aid+"_menu\" style=\"position: absolute; display: none\"><img src=\"images/attachicons/"+typeimeage+"\" border=\"0\" class=\"absmiddle\" alt=\"\" /> <a href=\"attachment.jsp?aid="+aid+"&amp;nothumb=yes\" target=\"_blank\"><strong>"+filename+"</strong></a> ("+filesize+")<br />"+description+price+"<div class=\"t_smallfont\">"+datetime+"</div></div>");
									 }else{
										 message = message.replaceAll("\\[attach\\]"+aid+"\\[/attach\\]", "<span style=\"position: absolute; display: none\" id=\"attachs_"+aid+"\" onmouseover=\"showMenu(this.id, 0, 1)\"><img src=\""+styles.get("IMGDIR")+"/attachimg.gif\" border=\"0\"></span><img src=\""+attaurl+"/"+imageurl+"\" border=\"0\" onload=\"attachimg(this, 'load')\" onmouseover=\"attachimginfo(this, 'attachs_"+aid+"', 1);attachimg(this, 'mouseover')\" onclick=\"zoom(this, '"+attaurl+"/"+attachment+"')\" onmouseout=\"attachimginfo(this, 'attachs_"+aid+"', 0, event)\" alt=\"\" /><div class=\"t_attach\" id=\"attachs_"+aid+"_menu\" style=\"position: absolute; display: none\"><img src=\"images/attachicons/"+typeimeage+"\" border=\"0\" class=\"absmiddle\" alt=\"\" /> <a href=\"attachment.jsp?aid="+aid+"&amp;nothumb=yes\" target=\"_blank\"><strong>"+filename+"</strong></a> ("+filesize+")<br />"+description+price+"<div class=\"t_smallfont\">"+datetime+"</div></div>");
									 }
								 }
							 }else{
								 message = message.replaceAll("\\[attach\\]"+aid+"\\[/attach\\]", "<img src=\"images/attachicons/"+typeimeage+"\" border=\"0\" class=\"absmiddle\" alt=\"\" /> <span style=\"white-space: nowrap\" id=\"attachs_"+aid+"\" onmouseover=\"showMenu(this.id)\"><a href=\"attachment.jsp?aid="+aid+"\" target=\"_blank\"><strong>"+filename+"</strong></a> ("+filesize+")</span><div class=\"t_attach\" id=\"attachs_"+aid+"_menu\" style=\"position: absolute; display: none\"><img src=\"images/attachicons/"+typeimeage+"\" border=\"0\" class=\"absmiddle\" alt=\"\" /> <a href=\"attachment.jsp?aid="+aid+"\" target=\"_blank\"><strong>"+filename+"</strong></a> ("+filesize+")<br />"+description+getMessage(request, "attach_downloads")+": "+attamap.get("downloads")+"<br />"+readperm+price+"<div class=\"t_smallfont\">"+datetime+"</div></div>");
							 }
						 }else{
							 message = message.replaceAll("\\[attach\\]"+aid+"\\[/attach\\]", "<img src=\"images/attachicons/"+typeimeage+"\" border=\"0\" class=\"absmiddle\" alt=\"\" /> <span style=\"white-space: nowrap\" id=\"attachs_"+aid+"\" onmouseover=\"showMenu(this.id)\"><a href=\"attachment.jsp?aid="+aid+"\" target=\"_blank\"><strong>"+filename+"</strong></a> ("+filesize+")</span><div class=\"t_attach\" id=\"attachs_"+aid+"_menu\" style=\"position: absolute; display: none\"><img src=\"images/attachicons/"+typeimeage+"\" border=\"0\" class=\"absmiddle\" alt=\"\" /> <a href=\"attachment.jsp?aid="+aid+"\" target=\"_blank\"><strong>"+filename+"</strong></a> ("+filesize+")<br />"+description+getMessage(request, "attach_downloads")+": "+attamap.get("downloads")+"<br />"+readperm+price+"<div class=\"t_smallfont\">"+datetime+"</div></div>");
						 } 
					}else{
						isattach = 1;
						String url = "";
						if(attamap.get("remote").equals("1")){
							url = ftpurl+"/"+attamap.get("attachment");
						}else{
							url = settings.get("attachurl")+"/"+attamap.get("attachment");
						}
						attamap.put("attachment", url);
						attamap.put("attachmentvalue", "");
						if(attamap.get("isimage").equals("1")){
							if(attamap.get("thumb").equals("1")){
								int index = attamap.get("attachment").lastIndexOf(".");
								attamap.put("attachmentvalue", attamap.get("attachment")+".thumb"+attamap.get("attachment").substring(index));
							}else{
								attamap.put("attachmentvalue", attamap.get("attachment"));
							}
						}
						if(!flagprice){
							attamap.put("isprice", "5");
							attamap.put("attachmentvalue","");
						}
						attalist1.add(attamap);
					}
				}
				amap.put("attalist", attalist1.size()>0?attalist1:null);
			}
			attalist = null;
		}
		message = message.replaceAll("\\[attach\\]\\d+\\[/attach\\]", "");
		amap.put("message", message);
		amap.put("attachment", isattach);
		return amap;
	}
	private String getfilesize(int size){
		String filesize = "";
		if(size<1024){
			filesize= size +" Bytes";
		}else if(size>1024 && size<1048576){
			filesize =  size/1024+" KB";
		}else{
			filesize = size/1048576+" MB";
		}
		return filesize;
	}
	private String replacethreadmessage(HttpServletRequest request,int price,Map creditstrans,String message,int paynum,int tid,boolean isdiv){
		message = message.replace("$", Common.SIGNSTRING);
		if(!isdiv){
			Pattern p = Pattern.compile("(?s)\\s*\\[free\\][\n\r]*(.+?)[\n\r]*\\[/free\\]\\s*");
		    Matcher m = p.matcher(message);
		    while(m.find()){
		    	message = message.replaceFirst("(?s)\\s*\\[free\\][\n\r]*(.+?)[\n\r]*\\[/free\\]\\s*", "<div class=\"quote\"><h5>"+getMessage(request, "jspruncode_free")+":</h5><blockquote>"+m.group(1)+"</blockquote></div>");
		    }
		}else{
			StringBuffer freemess = new StringBuffer();
			Pattern p = Pattern.compile("(?s)\\s*\\[free\\][\n\r]*(.+?)[\n\r]*\\[/free\\]\\s*");
		    Matcher m = p.matcher(message);
		    while(m.find()){
		    	freemess.append(m.group(1)+"<br/>");
		    }
		    message = freemess.toString();
			if(creditstrans!=null)
			{
				String unit = creditstrans.get("unit")==null?"":(String)creditstrans.get("unit");
				message += "</div><div style=\"width: 500px;\" class=\"notice\">"+getMessage(request, "pay_comment")+"</div><div class=\"box\" style=\"width: 500px;\"><table summary=\""+getMessage(request, "activity_info")+"\" cellpadding=\"0\" cellspacing=\"0\"><tr><th>"+getMessage(request, "a_post_threads_price")+"("+creditstrans.get("title")+"):</th><td>"+price+" "+unit+"</td></tr><tr><th>"+getMessage(request, "pay_buyers")+":</th><td>"+paynum+"&nbsp; <a href=\"misc.jsp?action=viewpayments&amp;tid="+tid+"\" target=\"_blank\">("+getMessage(request, "pay_view")+")</a></td></tr><tr><td colspan=\"2\" style=\"text-align: center\"><button class=\"submit\" value=\"true\" name=\"paysubmit\" type=\"button\" onclick=\"window.location.href='misc.jsp?action=pay&tid="+tid+"'\">"+getMessage(request, "pay")+"</button></td></tr></table></div>";	
			}
		}
		return message;
	}
	private String replacehide(HttpServletRequest request,String message,Map<String,String>threads,Members member,boolean ismodra){
		String uid = member==null?"0":member.getUid()+"";
		Pattern phide = Pattern.compile("(?s)\\[hide\\](.+?)\\[/hide\\]");
	    Matcher mhide = phide.matcher(message);
	    boolean flag = false;
	    while(mhide.find()){
	    	String hidemessage = mhide.group(1);
	    	List<Map<String,String>> postmessage = dataBaseService.executeQuery("select tid from jrun_posts where tid="+threads.get("tid")+" and authorid="+uid);
			if(ismodra || (postmessage!=null && postmessage.size()>0)){
				message = message.replaceFirst("(?s)\\[hide\\](.+?)\\[/hide\\]", "<div class='notice' style='width: 500px'>"+getMessage(request, "post_hide_reply")+"</div>"+hidemessage);
			}else{
				message = message.replaceFirst("(?s)\\[hide\\](.+?)\\[/hide\\]", "<div class='notice' style='width: 500px'>"+getMessage(request, "post_hide_reply_hidden")+"</div>");
				request.setAttribute("allowgetattach",false);
				flag = true;
			}
			postmessage = null;
		}
	    phide = Pattern.compile("(?s)\\[hide=(\\d+)\\](.+?)\\[/hide\\]");
	    mhide = phide.matcher(message);
	    while(mhide.find()){
	    	String credits = mhide.group(1);
	    	String hidemessage = mhide.group(2);
			int membercredit = member==null?0:member.getCredits();
			if(ismodra || membercredit>Common.intval(credits) || threads.get("authorid").equals(uid)){
				message = message.replaceFirst("(?s)\\[hide=(\\d+)\\](.+?)\\[/hide\\]", "<div class='notice' style='width: 500px'>"+getMessage(request, "post_hide_credits",credits)+"</div>"+hidemessage);
			}else{
				message = message.replaceFirst("(?s)\\[hide=(\\d+)\\](.+?)\\[/hide\\]", "<div class='notice' style='width: 500px'>"+getMessage(request, "post_hide_credits_hidden", credits)+"</div>");
				request.setAttribute("allowgetattach",false);
				flag = true;
			}
		}
	    if(flag){
	    	message = message.replaceAll("\\[attach\\]\\d+\\[/attach\\]", "[attach]***[/attach]");
	    }
		return message;
	}
	private ViewThreadVO viewthread_procpost(HttpServletRequest request,Map<String,String>threads,Map<String,String> postmap,Map<String,String>settings,Map<String,Map<String,String>> medals,Map<String,Map<String,String>> bbcodes,List<Map<String,String>> smilieslist,MessageResources resources,Locale locale,boolean modertar,int uid,Members member,int userstatusby,Map<Integer,Map<String,String>> ranks,int count,boolean showavatars,boolean showsignatures,int size,int lastvisit,boolean threadpay,boolean allowgetattach,String extcreditname,boolean showimages,int readaccess,String ftpurl,String dateformat,String timeformat,Map creditstrans,String timeoffset,int tid,String[]extname,String[]extunit,Map custominfoMap,List<Map<String,String>>profiledlist,List<String>custom,int parm,int highlightstatus,String highlight){
		ViewThreadVO viewthreadvo = new ViewThreadVO();
		if(postmap.get("username")!=null){
			String []result = Common.getgroupid(null, settings.get("creditsformula"), postmap, null);
			postmap.put("groupid", result[0]);
			postmap.put("grouptitle",result[1]);
			postmap.put("credits",result[2]);
			postmap.put("color", result[3]);
			postmap.put("stars", result[4]);
			postmap.put("groupavatar", result[5]);
			result = null;
		}
		if(postmap.get("taobao")!=null){
			postmap.put("taobao", Common.addslashes(postmap.get("taobao")));
		}
		if (threads.get("special").equals("0")){ 
			String medal = postmap.get("medals");
			if(medal!=null){
				if(medals!=null && medals.size()>0){
					List<Map<String,String>> medallists = new ArrayList<Map<String,String>>();
					for(int j=0;j<medals.size();j++){
						if(Common.matches(medal,"(^|\t)(" + medals.get(j).get("medalid") + ")(\t|$)")){
							medallists.add(medals.get(j));
						}
					}
					viewthreadvo.setMedalslist(medallists);
				}
			}
		}
		int ratings = (int)Math.ceil((double)Math.abs(Common.range(Common.intval(postmap.get("rate")),1000000,-1000000)) / (double)Common.toDigit(postmap.get("ratetimes")));
		if((Math.abs(Common.range(Common.intval(postmap.get("rate")),100000,-100000))<5 && ratings>2 )|| ratings>6){
			viewthreadvo.setRatings(2);
		}else{
			viewthreadvo.setRatings(ratings);
		}
		if (threads.get("special").equals("2")) {
			int buycredit = Common.intval(postmap.get("buyercredit"));
			int shellcredit =  Common.intval(postmap.get("sellercredit"));
			String ec_credit = settings.get("ec_credit");
			Map buycreditMap = dataParse.characterParse(ec_credit, true);
			Map buysMap = (Map) buycreditMap.get("rank");
			String postbuycredit = "0";
			String postshellcredit = "0";
			if (buysMap != null) {
				Iterator<Entry> it = buysMap.entrySet().iterator();
				if(buycredit>0){
					while (it.hasNext()) {
						Entry temp = it.next();
						Object key = temp.getKey();
						if (buycredit <= Integer.valueOf(temp.getValue().toString())) {
							postbuycredit = key.toString();
							break;
						}
					}
				}
				if(shellcredit>0){
					while (it.hasNext()) {
						Object key = it.next();
						if (shellcredit <= Integer.valueOf(buysMap.get(key).toString())) {
							postshellcredit = key.toString();
							break;
						}
					}
				}
			}
			request.setAttribute("shellcredit", postshellcredit);
			request.setAttribute("buycredit", postbuycredit);
		}
		String message = postmap.get("message").trim();
		if(threads.get("special").equals("2")){
			String tmp[] = message.split("\t\t\t");
			message = tmp[0];
		}
		postmap.put("message", message);
		String showjavacode = settings.get("showjavacode");
		Jspruncode jspruncode = new Jspruncode();
		message = jspruncode(request,parm,threads,postmap,bbcodes,smilieslist,showjavacode,resources,locale,member,modertar,jspruncode);
		if (userstatusby==1||"1".equals(postmap.get("groupid"))|| "2".equals(postmap.get("groupid"))||"3".equals(postmap.get("groupid")) || "spacial".equals(postmap.get("type"))) {
			String foruder = getforumfounder(request,settings,postmap);
			if(!foruder.equals("")){
				viewthreadvo.setHonor(foruder);
			}else{
				viewthreadvo.setHonor(postmap.get("grouptitle"));
			}
			viewthreadvo.setStars(postmap.get("stars"));
			viewthreadvo.setColor(postmap.get("color"));
		} else if (userstatusby==2&&ranks!=null) {
			for (int j = 1; j <= ranks.size(); j++) {
				Map<String,String> rank = ranks.get(j);
				if (Common.toDigit(postmap.get("posts")) >= Integer.valueOf(rank.get("postshigher"))) {
					viewthreadvo.setHonor(rank.get("ranktitle"));
					viewthreadvo.setStars(rank.get("stars"));
					viewthreadvo.setColor(rank.get("color"));
					break;
				}
			}
		}
		if (showavatars) {
			String avatars = postmap.get("avatar")!=null?postmap.get("avatar"):"";
			if ("".equals(avatars)){
				viewthreadvo.setAvatars("images/avatars/noavatar.gif");
			} else {
				viewthreadvo.setAvatars(avatars);
			}
		}
		if (showsignatures) {
			String sightml = postmap.get("signature")!=null?postmap.get("signature"):"";
			sightml = sightml.replace("$", Common.SIGNSTRING);
			if(Common.toDigit(postmap.get("allowsigbbcode"))>0){
				sightml = jspcode.parseJsprunCode(sightml,resources,locale);
			}
			if(Common.toDigit(postmap.get("allowsigimgcode"))>0){
				sightml = jspcode.parseimg(sightml, true);
			}
			sightml = sightml.replace(Common.SIGNSTRING, "$");
			viewthreadvo.setSignatures(sightml);
		}
		viewthreadvo.setNewpostanchor(Common.toDigit(postmap.get("dateline"))>lastvisit?"<a name='newpost'></a>":"");
		viewthreadvo.setLastpostanchor(count==size?"<a name='lastpost'></a>":"");
		String vtonlinestatus = settings.get("vtonlinestatus");
		if(vtonlinestatus.equals("2")){
			String onlinsql = "select uid from jrun_sessions as s where s.uid="+postmap.get("authorid")+" and s.invisible=0";
			List<Map<String,String>> onlist = dataBaseService.executeQuery(onlinsql);
			if(onlist!=null && onlist.size()>0){
				viewthreadvo.setOnlineauthors(1);
			}
			onlist = null;
		}
		postmap.put("message", message);
		Map attamap = parseAttach(request,settings,extcreditname,postmap,showimages,uid,allowgetattach,readaccess,ftpurl,dateformat+" "+timeformat,timeoffset,modertar,null,threadpay);
		message = (String)attamap.get("message");
		viewthreadvo.setAttaurl((List)attamap.get("attalist"));
		int attachment = (Integer)attamap.get("attachment");
		if(attachment!=-1){
			postmap.put("attachment", attachment+"");
		}
		int paynum = 0;
		if(threadpay){
			List<Map<String, String>> paycontlist = dataBaseService.executeQuery("select count(*) count from jrun_paymentlog WHERE tid="+tid);
			if(paycontlist!=null && paycontlist.size()>0){
				Map<String,String> paylogmap = paycontlist.get(0);
				paynum = Common.intval(paylogmap.get("count"));
				paylogmap = null;
			}
			paycontlist = null;
		}
		if(postmap.get("first").equals("1")){
			message = replacethreadmessage(request,Common.toDigit(threads.get("price")),creditstrans,message,paynum,tid,threadpay);
		}
		if(postmap.get("username")!=null){
			viewthreadvo.setCustominfo(customeinfo(request,extname,extunit,custominfoMap,postmap,profiledlist,custom,timeoffset,dateformat));
		}
		message = replacemessage(message,jspruncode,Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(postmap.get("htmlon"))<=0,highlightstatus,highlight);
		postmap.put("message", message);
		viewthreadvo.setUsermap(postmap);
		if(postmap.get("first").equals("1")&&!threads.get("special").equals("2")){
			String seodescription = postmap.get("message");
			seodescription = Common.cutstr(Common.htmlspecialchars(Common.strip_tags(seodescription)),150);
			request.setAttribute("seodescription", seodescription);
		}
		return viewthreadvo;
	}
	private String viewthread_poll(HttpServletRequest request,int tid,int uid,Map<String,String> settings,Members member){
		String forwad = "poll";
		Polls polls = pollService.findPollsBytid(tid);
		List<Polloptions> options = optionService.findPolloptionsBytid(tid);
		if(polls==null||options==null){
			request.setAttribute("errorInfo", getMessage(request, "thread_nonexistence"));
			return null;
		}
		request.setAttribute("pollMap", calcSumreson(options));
		request.setAttribute("sparetime",  calcSparetime(request,polls.getExpiration()));
		request.setAttribute("options", options);
		request.setAttribute("polls", polls);
		request.setAttribute("pagesize",  member != null && member.getPpp() > 0 ? member.getPpp(): Integer.valueOf(settings.get("postperpage")));
		String voterids = uid>0?uid+"":Common.get_onlineip(request);
		request.setAttribute("voterids", voterids);
		return forwad;
	}
	@SuppressWarnings("unchecked")
	private ActionForward viewthread_trade(HttpServletRequest request,HttpServletResponse response,ActionMapping mapping,Map<String,String>threads,Map<String,String>settings,Map<String,Map<String,String>> bbcodes,List<Map<String,String>> smilieslist,MessageResources resources,Locale locale,boolean modertar,int uid,Members member,boolean showavatars,boolean showsignatures,boolean threadpay,boolean allowgetattach,String extcreditname,boolean showimages,int readaccess,String ftpurl,String dateformat,String timeformat,Map creditstrans,String timeoffset,int tid,List<Map<String,String>>profiledlist,List<String>custom,String profileds,int highlightstatus,String highlight){
		String dos = request.getParameter("do");
		int  parnum = Common.intval(settings.get("maxsmilies"));
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		if(dos!=null){
			String url = "viewthread.jsp?tid=" + tid +"&do=" + dos;;
			String ajaxtarget=request.getParameter("ajaxtarget");
			if (threads.get("special").equals("2")) {
				Map tradetypes = dataParse.characterParse(settings.get("tradetypes"), false);
				request.setAttribute("tradetypes", tradetypes);
			}else{
				Common.writeMessage(response,getMessage(request, "undefined_action_return"),true);
				return null;
			}
			if (dos.equals("viewtradelist")) {
				String tradetypeid = request.getParameter("tradetypeid");
				List<Map<String,String>> types = dataBaseService.executeQuery("select typeid from jrun_trades where tid="+tid+" and displayorder<=0");
				boolean showtradetypemenu = false;
				for(Map<String,String> tradetype:types){
					showtradetypemenu = Common.toDigit(tradetype.get("typeid"))>0 && !showtradetypemenu;
				}
				request.setAttribute("threadtradetypes", types);
				String typeadd = !Common.empty(tradetypeid)?" and typeid="+Common.intval(tradetypeid)+" ":"";
				Map<String,Integer> pages = multi(request,response,uid,"SELECT count(*) as count FROM jrun_trades WHERE displayorder<=0 and tid='"+tid+"' "+typeadd,url,ajaxtarget,10);
				List<Map<String,String>> trades = dataBaseService.executeQuery("SELECT * FROM jrun_trades WHERE tid='"+tid+"' AND displayorder<=0  "+typeadd+" ORDER BY displayorder DESC LIMIT "+pages.get("beginsize")+","+pages.get("pagesize"));
				for(Map<String,String>trade:trades){
					if(!trade.get("expiration").equals("0")){
						float expiration = ((float)Common.toDigit(trade.get("expiration"))-timestamp)/86400;
						if(expiration>0){
							double expirationhour = Math.floor((expiration-Math.floor(expiration))*24);
							trade.put("expirationhour", (int)expirationhour+"");
							trade.put("expiration", (int)Math.floor(expiration)+"");
						}else{
							trade.put("expiration", "-1");
						}
					}
				}
				request.setAttribute("tradelist", trades);
				request.setAttribute("showtradetypemenu", showtradetypemenu);
				return mapping.findForward("tradelist");
			} else if (dos.equals("viewall") || dos.equals("viewpost") || dos.equals("viewtrade")) {
				String sqladd = dos.equals("viewall")?"where p.tid="+tid+" and p.invisible='0' and p.first='0'":(dos.equals("viewpost")?"LEFT JOIN jrun_trades tr ON p.pid=tr.pid WHERE p.tid='"+tid+"' AND p.invisible='0' AND tr.tid IS null AND p.first='0'":"INNER JOIN jrun_trades tr ON p.pid=tr.pid WHERE p.tid='"+tid+"' AND p.invisible='0'");
				Map<String,Integer> pages = multi(request,response,uid,"select count(*) as count from jrun_posts p "+sqladd,url,ajaxtarget,0);
				List<Map<String,String>> posts = dataBaseService.executeQuery("SELECT p.*,m.username,m.adminid,m.groupid,m.credits FROM jrun_posts p LEFT JOIN jrun_members m ON m.uid=p.authorid "+sqladd+" ORDER BY p.dateline LIMIT "+pages.get("beginsize")+","+pages.get("pagesize"));
				StringBuffer tradesaids = new StringBuffer();
				StringBuffer tradespids = new StringBuffer();
				Map  attamaps = new HashMap();
				Map  trademap = new HashMap();
				for(Map<String,String> post:posts){
					tradespids.append(","+post.get("pid"));
				}
				if((dos.equals("viewtrade") || dos.equals("viewall")) && tradespids.length()>0) {
					List<Map<String,String>> trades = dataBaseService.executeQuery("SELECT * FROM jrun_trades WHERE pid IN ("+tradespids.substring(1)+")");
					for(Map<String,String>trade:trades){
						if(!trade.get("expiration").equals("0")){
							float expiration = ((float)Common.toDigit(trade.get("expiration"))-timestamp)/86400;
							if(expiration>0){
								double expirationhour = Math.floor((expiration-Math.floor(expiration))*24);
								trade.put("expirationhour", (int)expirationhour+"");
								trade.put("expiration", (int)Math.floor(expiration)+"");
							}else{
								trade.put("expiration", "-1");
							}
						}
						tradesaids.append(","+trade.get("aid"));
						trademap.put(trade.get("pid"),trade);
					}
				}
				String showjavacode = settings.get("showjavacode");
				for(Map<String,String> post:posts){
					post.put("first", "0");
					Jspruncode jspruncode = new Jspruncode();
					String message = jspruncode(request,parnum,threads,post,bbcodes,smilieslist,showjavacode,resources,locale,member,modertar,jspruncode);
					post.put("message", message);
					Map  attamap = parseAttach(request,settings,extcreditname,post,showimages,uid,allowgetattach,readaccess,ftpurl,dateformat+" "+timeformat,timeoffset,modertar,tradesaids.length()>0?tradesaids.substring(1):null,threadpay);
					message = (String)attamap.get("message");
					post.put("message", replacemessage(message,jspruncode,Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(post.get("htmlon"))<=0,0,null));
					int attachment = (Integer)attamap.get("attachment");
					if(attachment!=-1){
						post.put("attachment", attachment+"");
					}
					attamaps.put(post.get("pid"), attamap.get("attalist"));
				}
				request.setAttribute("postno", settings.get("postno"));
				Map<String,String> attachmap = new HashMap<String,String>();
				if(tradespids.length()>0){
					List<Map<String,String>> attachs = dataBaseService.executeQuery("SELECT a.* FROM jrun_attachments a WHERE a.pid IN ("+tradespids.substring(1)+")");
					for(Map<String,String> attach:attachs){
						if(attach.get("isimage").equals("1")&&tradesaids.length()>0&&Common.in_array(tradesaids.substring(1).split(","),attach.get("aid"))){
							String urls = attach.get("remote").equals("1")?ftpurl:settings.get("attachurl");
							urls = urls+"/"+attach.get("attachment");
							urls = attach.get("thumb").equals("1")?urls+".thumb.jpg":urls;
							attachmap.put(attach.get("pid"), urls);
						}
					}
				}
				request.setAttribute("attachmap", attachmap);
				request.setAttribute("attatrademap", attamaps);
				request.setAttribute("trademap", trademap);
				request.setAttribute("postlist", posts);
				return mapping.findForward("viewreplay");
			}else if(dos!=null && dos.equals("viewfirstpost")){
				List<Map<String,String>> list = dataBaseService.executeQuery("SELECT p.*,m.username,m.adminid,m.groupid,m.credits FROM jrun_posts p LEFT JOIN jrun_members m ON m.uid=p.authorid WHERE tid='"+tid+"' AND p.invisible='0' AND p.first='1' LIMIT 1");
				Map<String,String> post = list.get(0);
				String message = post.get("message").trim();
				String tmp[] = message.split("\t\t\t");
				post.put("message", tmp.length==2?tmp[1]:tmp[0]);
				post.put("first", "0");
				Map  attamaps = new HashMap();
				Map  trademap = new HashMap();
				String showjavacode = settings.get("showjavacode");
				Jspruncode jspruncode = new Jspruncode();
				message = jspruncode(request,parnum,threads,post,bbcodes,smilieslist,showjavacode,resources,locale,member,modertar,jspruncode);
				post.put("message", message);
				Map  attamap = parseAttach(request,settings,extcreditname,post,showimages,uid,allowgetattach,readaccess,ftpurl,dateformat+" "+timeformat,timeoffset,modertar,null,threadpay);
				message = replacemessage((String)attamap.get("message"),jspruncode,Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(post.get("htmlon"))<=0,0,null);
				post.put("message", message);
				attamaps.put(post.get("pid"), attamap.get("attalist"));
				request.setAttribute("postno", settings.get("postno"));
				request.setAttribute("attatrademap", attamaps);
				request.setAttribute("trademap", trademap);
				request.setAttribute("postlist", list);
				return mapping.findForward("viewreplay");
			}
			return null;
		}else{
			String forwad = "trade";
			List<Map<String,String>> tradecount= dataBaseService.executeQuery("select count(*) as count from jrun_trades where tid = "+tid);
			int tradenum = tradecount!=null&&tradecount.size()>0?Common.toDigit(tradecount.get(0).get("count")):0;
			List<Map<String,String>> trades = dataBaseService.executeQuery("select * from jrun_trades where tid="+tid+" and displayorder>0 order by displayorder");
			StringBuffer tradesaids = new StringBuffer();
			StringBuffer tradespids = new StringBuffer();
			Map<String,String> attachmap = new HashMap<String,String>();
			int listcount = trades.size();
			int tradelist = tradenum - listcount;
			request.setAttribute("tradelist", tradelist);
			for(Map<String,String>trade:trades){
				if(!trade.get("expiration").equals("0")){
					float expiration = (Common.toDigit(trade.get("expiration"))-timestamp)/(float)86400;
					if(expiration>0){
						double expirationhour = Math.floor((expiration-Math.floor(expiration))*24);
						trade.put("expirationhour", (int)expirationhour+"");
						trade.put("expiration", (int)Math.floor(expiration)+"");
					}else{
						trade.put("expiration", "-1");
					}
				}
				tradesaids.append(","+trade.get("aid"));
				tradespids.append(","+trade.get("pid"));
			}
			if(tradespids.length()>0){
				List<Map<String,String>> attachs = dataBaseService.executeQuery("SELECT a.* FROM jrun_attachments a WHERE a.pid IN ("+tradespids.substring(1)+")");
				for(Map<String,String> attach:attachs){
					if(attach.get("isimage").equals("1")&&tradesaids.length()>0&&Common.in_array(tradesaids.substring(1).split(","),attach.get("aid"))){
						String url = attach.get("remote").equals("1")?ftpurl:settings.get("attachurl");
						url = url+"/"+attach.get("attachment");
						url = attach.get("thumb").equals("1")?url+".thumb.jpg":url;
						attachmap.put(attach.get("pid"), url);
					}
				}
			}
			request.setAttribute("tradenum", tradenum);
			request.setAttribute("attachmap", attachmap);
			request.setAttribute("trades", trades.size()>0?trades:null);
			List<Map<String,String>> threadmod = dataBaseService.executeQuery("select username,dateline,action from jrun_threadsmod as m where m.tid="+tid+" order by m.dateline desc limit 1");
			if (threadmod.size()>0) {
				Map<String,String> threadmodmap = threadmod.get(0);
				String actionname = getMessage(request,threadmodmap.get("action"));
				threadmodmap.put("action", actionname);
				request.setAttribute("threadmod", threadmodmap);
			} else {
				request.setAttribute("threadmod", null);
			}
			threadmod = null;
			String sql = "select p.*,m.uid, m.username, m.groupid, m.adminid, m.regdate, m.lastactivity, m.posts, m.digestposts, m.oltime,m.pageviews, m.credits, m.extcredits1, m.extcredits2, m.extcredits3, m.extcredits4, m.extcredits5, m.extcredits6,m.extcredits7, m.extcredits8, m.email, m.gender, mf.nickname, mf.site,mf.icq, mf.qq, mf.yahoo, mf.msn, mf.taobao, mf.alipay, mf.location, mf.medals, mf.avatar, mf.avatarwidth,mf.avatarheight, mf.sightml AS signature, mf.customstatus, mf.spacename,mf.buyercredit,mf.sellercredit,u.stars,u.readaccess,u.grouptitle,u.type,u.creditshigher,u.creditslower,u.color,u.allowsigbbcode,u.allowsigimgcode,u.groupavatar"+profileds+" from jrun_posts as p left join jrun_members as m on p.authorid=m.uid left join jrun_memberfields as mf on mf.uid=m.uid left join jrun_usergroups as u on m.groupid=u.groupid where p.tid='" + tid + "'  and  p.invisible='0' and p.first=1";
			List<Map<String,String>> postlist = dataBaseService.executeQuery(sql);
			if (postlist != null) {
				int userstatusby = Integer.valueOf(settings.get("userstatusby"));
				Map<Integer,Map<String,String>> ranks=null;
				if(userstatusby==2){
					ranks=dataParse.characterParse(((Map<String,String>)request.getAttribute("ranks")).get("ranks"),false);
				}
				String custominfo = settings.get("customauthorinfo");
				Map custominfoMap = dataParse.characterParse(custominfo, false);
				Map<Integer,Map<String,String>> extcredits=dataParse.characterParse(settings.get("extcredits"), true);
				Map<String,String[]> custommap = getCreditsunit(extcredits);
				String extname[] = custommap.get("extname");
				String extunit[] = custommap.get("extunit");
				custommap = null;
				int lastvisit=member!=null?member.getLastvisit():0;
				int size = postlist.size();
				int count =1;
				byte tagstatus = (byte)Common.toDigit(settings.get("tagstatus"));
				if(tagstatus==1){
					List<Map<String, String>> taglist = dataBaseService.executeQuery("SELECT tagname FROM jrun_threadtags WHERE tid="+tid);
					if(taglist==null || taglist.size()<=0){
						request.setAttribute("taglist", null);
					}else{
						StringBuffer metakeywords  = new StringBuffer();
						for(Map<String,String> tag:taglist){
							metakeywords.append(tag.get("tagname")+",");
						}
						request.setAttribute("metakeywords", metakeywords);
						request.setAttribute("taglist", taglist);
					}
					taglist = null;
				}
				if(postlist!=null&&postlist.size()>0){
					ViewThreadVO viewthreadvo= viewthread_procpost(request,threads,postlist.get(0),settings,null,bbcodes,smilieslist,resources,locale,modertar,uid,member,userstatusby,ranks,count,showavatars,showsignatures,size,lastvisit,threadpay,allowgetattach,extcreditname,showimages,readaccess,ftpurl,dateformat,timeformat,creditstrans,timeoffset,tid,extname,extunit,custominfoMap,profiledlist,custom,parnum,highlightstatus,highlight);
					request.setAttribute("viewthread", viewthreadvo);
				}
				request.setAttribute("extcredits",extname);
			}
			postlist = null;
			request.setAttribute("coloroptions", Common.COLOR_OPTIONS);
			int google_searchbox=Common.range(Common.intval(settings.get("google_searchbox")), 10, 0);
			int baidu_searchbox=Common.range(Common.intval(settings.get("baidu_searchbox")), 10, 0);
			request.setAttribute("google_searchbox", google_searchbox&4);
			request.setAttribute("baidu_searchbox", baidu_searchbox&4);
			return mapping.findForward(forwad);
		}
	}
	@SuppressWarnings("unchecked")
	private String viewthread_reward(HttpServletRequest request,Map<String,String> threads,Members members,Map<String,String>settings,Map<String,Map<String,String>> bbcodes,List<Map<String,String>> smilieslist,String ftpurl,boolean modertar,String extcreditname,boolean showimages,int uid,boolean allowgetattach,int readaccess,String dateformat,String timeformat,String timeoffset,int tid,MessageResources resources,Locale locale,int highlightstatus,String highlight){
		String forwad = "reward";
		request.setAttribute("pagesize",  members != null && members.getPpp() > 0 ? members.getPpp(): Integer.valueOf(settings.get("postperpage")));
		threads.put("endreward", "false");
		int parnum = Common.intval(settings.get("maxsmilies"));
		Map postmap = null;
		String showjavacode = settings.get("showjavacode");
		if(Common.toDigit(threads.get("price"))<=0){
			threads.put("endreward", "true");
			postmap = new HashMap();
			List<Map<String,String>> postlist = dataBaseService.executeQuery("select * from jrun_posts as p where p.tid='"+tid+"' and p.first<>1 order by dateline limit 1");
			if(postlist!=null&&postlist.size()>0){
				Map<String,String> post = postlist.get(0);
				Jspruncode jspruncode = new Jspruncode();
				String message = jspruncode(request,parnum,threads,post,bbcodes,smilieslist,showjavacode,resources,locale,members,modertar,jspruncode);
				post.put("message", message);
				Map attamap = parseAttach(request,settings,extcreditname,post,showimages,uid,allowgetattach,readaccess,ftpurl,dateformat+" "+timeformat,timeoffset,modertar,null,false);
				int attachment = (Integer)attamap.get("attachment");
				if(attachment!=-1){
					post.put("attachment", attachment+"");
				}
				message = (String)attamap.get("message");
				message = replacemessage(message,jspruncode,Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(post.get("htmlon"))<=0,highlightstatus,highlight);
				post.put("message", message);
				postmap.put(post, attamap.get("attalist"));
			}
		}
		request.setAttribute("postmap", postmap);
		return forwad;
	}
	@SuppressWarnings("unchecked")
	private String viewthread_activity(HttpServletRequest request,HttpServletResponse response,Members members,int timestamp,int tid,Map<String,String> settings){
		request.setAttribute("pagesize",  members != null && members.getPpp() > 0 ? members.getPpp(): Integer.valueOf(settings.get("postperpage")));
		List applist = new ArrayList();
		List<Map<String, String>> activitslist = dataBaseService.executeQuery("select * from jrun_activities as a where a.tid="+ tid);
		List<Map<String, String>> activiapplist = dataBaseService.executeQuery("select a.*,m.avatar from jrun_activityapplies as a left join jrun_memberfields as m on a.uid=m.uid where a.tid="+ tid);
		if(activitslist.size()<=0){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return null;
		}
		request.setAttribute("activitslist", activitslist.get(0));
		Map<String,String> activitsmap = activitslist.get(0);
		activitslist = null;
		if(Common.intval(activitsmap.get("expiration"))-timestamp<=0){
			request.setAttribute("activityclose", "true");
		}else{
			request.setAttribute("activityclose", "false");
		}
		if(activitsmap.get("expiration").equals("0")){
			request.setAttribute("activityclose", "");
		}
		activitsmap = null;
		if (activiapplist == null || activiapplist.size()<=0) {
			request.setAttribute("activiapplist", null);
		} else {
			for (Map<String,String> activityapp:activiapplist) {
				ActivitappliesVO appvo = new ActivitappliesVO();
				if (!activityapp.get("avatar").equals("")) {
					appvo.setAvatar(activityapp.get("avatar"));
				} else {
					appvo.setAvatar("images/avatars/noavatar.gif");
				}
				appvo.setUid(Common.intval(activityapp.get("uid")));
				appvo.setUsername(activityapp.get("username"));
				appvo.setVerified(activityapp.get("verified"));
				applist.add(appvo);
			}
			request.setAttribute("activiapplist", applist);
		}
		List<Map<String, String>> count = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_activityapplies WHERE tid="	+ tid + " and verified=1");
		int size = (count != null && count.size() > 0 ? Integer	.valueOf(count.get(0).get("count")) : 0);
		count = null;activiapplist=null;
		request.setAttribute("resoncount", size);
		return "activity";
	}
	@SuppressWarnings("unchecked")
	private ActionForward viewthread_debate(HttpServletRequest request,HttpServletResponse response,ActionMapping mapping,Map<String,String>threads,Map<String,String>settings,Map<String,Map<String,String>> bbcodes,List<Map<String,String>> smilieslist,MessageResources resources,Locale locale,boolean modertar,int uid,Members member,boolean showavatars,boolean showsignatures,boolean threadpay,boolean allowgetattach,String extcreditname,boolean showimages,int readaccess,String ftpurl,String dateformat,String timeformat,Map creditstrans,String timeoffset,int tid,List<Map<String,String>>profiledlist,List<String>custom,String profileds,int highlightstatus,String highlight){
		String dos = request.getParameter("do");
		int  parnum = Common.intval(settings.get("maxsmilies"));
		if(dos != null && dos.equals("viewdebatepost")){
			String url = "viewthread.jsp?tid=" + tid;
			url = url + "&do=" + dos;
			String ajaxtarget=request.getParameter("ajaxtarget");
			List ajaxdebatepostlist = new ArrayList();
			String stand = request.getParameter("stand");
			String debatesqladd = " and ";
			if (stand == null) {
				debatesqladd = "";
			} else if (stand.equals("0")) {
				url = url+"&stand=0";
				debatesqladd += "dp.stand = 0";
			} else if (stand.equals("1")) {
				url = url+"&stand=1";
				debatesqladd += "dp.stand = 1";
			} else {
				url = url+"&stand=2";
				debatesqladd += "dp.stand = 2";
			}
			String sqldate = "SELECT COUNT(*) count FROM jrun_debateposts dp where dp.tid="	+ tid + debatesqladd;
			Map<String, Integer> pages = multi(request, response,uid, sqldate, url, ajaxtarget,0);
			String sql = "SELECT p.*,m.uid,m.username,m.adminid,m.groupid,m.credits,dp.stand,dp.voters FROM jrun_posts p LEFT JOIN jrun_members m ON m.uid=p.authorid LEFT JOIN jrun_debateposts dp ON p.pid=dp.pid WHERE p.tid='"
					+ tid
					+ "' AND p.invisible='0' AND p.first='0' "
					+ debatesqladd
					+ " order by p.dateline LIMIT "
					+ pages.get("beginsize") + ", " + pages.get("pagesize");
			List<Map<String, String>> depapostlist = dataBaseService.executeQuery(sql);
			if (depapostlist != null) {
				String showjavacode = settings.get("showjavacode");
				for (Map<String,String> depaMap:depapostlist) {
					Map postmap = new HashMap();
					Jspruncode jspruncode = new Jspruncode();
					String messagse = jspruncode(request,parnum,threads,depaMap,bbcodes,smilieslist,showjavacode,resources,locale,member,modertar,jspruncode);
					depaMap.put("message", messagse);
					Map  attamap = parseAttach(request,settings,extcreditname,depaMap,showimages,uid,allowgetattach,readaccess,ftpurl,dateformat+" "+timeformat,timeoffset,modertar,null,threadpay);
					messagse = (String)attamap.get("message");
					int attachment = (Integer)attamap.get("attachment");
					if(attachment!=-1){
						depaMap.put("attachment", attachment+"");
					}
					messagse = replacemessage(messagse,jspruncode,Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(depaMap.get("htmlon"))<=0,0,null);
					depaMap.put("message", messagse);
					postmap.put(depaMap, attamap.get("attalist"));
					ajaxdebatepostlist.add(postmap);
				}
			}
			if (depapostlist != null && depapostlist.size() > 0) {
				request.setAttribute("postlist", ajaxdebatepostlist);
			} else {
				request.setAttribute("postlist", null);
			}
			depapostlist = null;
			request.setAttribute("postno", settings.get("postno"));
			return mapping.findForward("debatepost");
		}else{
			String forwad = "debate";
			request.setAttribute("pagesize",  member != null && member.getPpp() > 0 ? member.getPpp(): Integer.valueOf(settings.get("postperpage")));
			List<Map<String, String>> debatelist = dataBaseService.executeQuery("select * from jrun_debates as d where d.tid="+ tid);
			if(debatelist.size()<=0){
				request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
				return mapping.findForward("showMessage");
			}
			Map<String,String> debates = debatelist.get(0);
			request.setAttribute("debates", debates);
			request.setAttribute("bestdebater", debates.get("bestdebater").split("\t")[0]);
			debatelist = null;
			List<Map<String,String>> firststandlist = dataBaseService.executeQuery("SELECT stand FROM jrun_debateposts WHERE tid='"+tid+"' AND uid='"+uid+"' AND stand<>'0' ORDER BY dateline LIMIT 1");
			if(firststandlist!=null && firststandlist.size()>0){
				Map<String,String> firsta = firststandlist.get(0);
				String firstand = firsta.get("stand");
				request.setAttribute("firststand", firstand);
			}
			firststandlist = null;
				List<Map<String,String>> threadmod = dataBaseService.executeQuery("select username,dateline,action from jrun_threadsmod as m where m.tid="+tid+" order by m.dateline desc limit 1");
				if (threadmod.size()>0) {
					Map<String,String> threadmodmap = threadmod.get(0);
					String actionname = getMessage(request,threadmodmap.get("action"));
					threadmodmap.put("action", actionname);
					request.setAttribute("threadmod", threadmodmap);
				} else {
					request.setAttribute("threadmod", null);
				}
				threadmod = null;
				String sql = "select p.*,m.uid, m.username, m.groupid, m.adminid, m.regdate, m.lastactivity, m.posts, m.digestposts, m.oltime,m.pageviews, m.credits, m.extcredits1, m.extcredits2, m.extcredits3, m.extcredits4, m.extcredits5, m.extcredits6,m.extcredits7, m.extcredits8, m.email, m.gender, mf.nickname, mf.site,mf.icq, mf.qq, mf.yahoo, mf.msn, mf.taobao, mf.alipay, mf.location, mf.medals, mf.avatar, mf.avatarwidth,mf.avatarheight, mf.sightml AS signature, mf.customstatus, mf.spacename,mf.buyercredit,mf.sellercredit,u.stars,u.readaccess,u.grouptitle,u.type,u.creditshigher,u.creditslower,u.color,u.allowsigbbcode,u.allowsigimgcode,u.groupavatar"+profileds+" from jrun_posts as p left join jrun_members as m on p.authorid=m.uid left join jrun_memberfields as mf on mf.uid=m.uid left join jrun_usergroups as u on m.groupid=u.groupid where p.tid='" + tid + "'  and  p.invisible='0' and p.first=1";
				List<Map<String,String>>postlist = dataBaseService.executeQuery(sql);
				if (postlist != null) {
					int userstatusby = Integer.valueOf(settings.get("userstatusby"));
					Map<Integer,Map<String,String>> ranks=null;
					if(userstatusby==2){
						ranks=dataParse.characterParse(((Map<String,String>)request.getAttribute("ranks")).get("ranks"),false);
					}
					String custominfo = settings.get("customauthorinfo");
					Map custominfoMap = dataParse.characterParse(custominfo, false);
					Map<Integer,Map<String,String>> extcredits=dataParse.characterParse(settings.get("extcredits"), true);
					Map<String,String[]> custommap = getCreditsunit(extcredits);
					String extname[] = custommap.get("extname");
					String extunit[] = custommap.get("extunit");
					custommap = null;
					int lastvisit=member!=null?member.getLastvisit():0;
					int size = postlist.size();
					int count =1;
					byte tagstatus = (byte)Common.toDigit(settings.get("tagstatus"));
					if(tagstatus==1){
						List<Map<String, String>> taglist = dataBaseService.executeQuery("SELECT tagname FROM jrun_threadtags WHERE tid="+tid);
						if(taglist==null || taglist.size()<=0){
							request.setAttribute("taglist", null);
						}else{
							StringBuffer metakeywords  = new StringBuffer();
							for(Map<String,String> tag:taglist){
								metakeywords.append(tag.get("tagname")+",");
							}
							request.setAttribute("metakeywords", metakeywords);
							request.setAttribute("taglist", taglist);
						}
						taglist = null;
					}
					if (postlist.size()>0) {
						ViewThreadVO viewthread = viewthread_procpost(request,threads,postlist.get(0),settings,null,bbcodes,smilieslist,resources,locale,modertar,uid,member,userstatusby,ranks,count,showavatars,showsignatures,size,lastvisit,threadpay,allowgetattach,extcreditname,showimages,readaccess,ftpurl,dateformat,timeformat,creditstrans,timeoffset,tid,extname,extunit,custominfoMap,profiledlist,custom,parnum,highlightstatus,highlight);
						request.setAttribute("viewthread", viewthread);
					} 
					request.setAttribute("extcredits",extname);
				}
				postlist = null;
				request.setAttribute("thread", threads);
				request.setAttribute("coloroptions", Common.COLOR_OPTIONS);
				int google_searchbox=Common.range(Common.intval(settings.get("google_searchbox")), 10, 0);
				int baidu_searchbox=Common.range(Common.intval(settings.get("baidu_searchbox")), 10, 0);
				request.setAttribute("google_searchbox", google_searchbox&4);
				request.setAttribute("baidu_searchbox", baidu_searchbox&4);
				return mapping.findForward(forwad);
		}
	}
	@SuppressWarnings("unchecked")
	private ActionForward viewthread_special(HttpServletRequest request,HttpServletResponse response,ActionMapping mapping,Map<String,String> threads,Map<String,Map<String,String>> bbcodes,List<Map<String,String>> smilieslist,boolean modertar,Members member,Map<String,String> settings,String extcreditname,boolean showimage,int uid,boolean allowgetattach,int readaccess,String ftpurl,String dateformat,String timeformat,String timeoffset,MessageResources resources,Locale locale){
		String tid = request.getParameter("tid");
		String dos = request.getParameter("do");
		String url = "viewthread.jsp?tid=" + tid + "&do=" + dos;
		String ajaxtarget=request.getParameter("ajaxtarget");
		String sqlcount = null;
		String sql = null;
		if (threads.get("special").equals("1")) {
			sqlcount = "select count(*) as count from jrun_posts as p where p.tid=" + threads.get("tid") + " and p.first<>1  and  p.invisible='0'";
			sql = "select p.*,m.groupid,m.adminid from jrun_posts as p left join jrun_members m on p.authorid=m.uid where p.tid=" + threads.get("tid") + " and p.first<>1  and  p.invisible='0' order by pid";
		} else if (threads.get("special").equals("3")) {
			String pid = "0";
			if(Integer.valueOf(threads.get("price"))<=0){
				List<Map<String,String>> postlist = dataBaseService.executeQuery("select pid from jrun_posts as p where p.tid="+threads.get("tid")+" and p.first<>1 order by dateline limit 1");
				if(postlist!=null&&postlist.size()>0){
					pid = postlist.get(0).get("pid");
				}
				postlist = null;
			}
			sqlcount = "select count(*) as count from jrun_posts as p where p.tid=" + threads.get("tid") + " and p.first<>1 and  p.invisible='0' and p.pid<>"+pid;
			sql = "select p.*,m.groupid,m.adminid from jrun_posts as p left join jrun_members m on p.authorid=m.uid where p.tid=" + threads.get("tid") + " and p.first<>1 and  p.invisible='0' and p.pid<>"+pid+" order by p.dateline";
		} else if (threads.get("special").equals("4")) {
			sqlcount = "select count(*) as count from jrun_posts as p where p.tid=" + threads.get("tid") + " and p.first<>1 and  p.invisible='0'";
			sql = "select p.*,m.groupid,m.adminid from jrun_posts as p left join jrun_members m on p.authorid=m.uid where p.tid=" + threads.get("tid") + " and p.first<>1 and  p.invisible='0' order by p.dateline";
		} else{
			Common.writeMessage(response,getMessage(request, "undefined_action_return"),true);
			return null;
		}
		Map<String, Integer> pages = multi(request, response,uid,sqlcount, url, ajaxtarget,0);
		List<Map<String,String>> postlist = dataBaseService.executeQuery(sql+" limit "+pages.get("beginsize")+","+pages.get("pagesize"));
		int parnum = Common.intval(settings.get("maxsmilies"));
		List ajaxpostlist = new ArrayList();
		if (postlist != null) {
			String showjavacode = settings.get("showjavacode");
			for (Map<String,String> post : postlist) {
				Map postmap = new HashMap();
				Jspruncode jspruncode = new Jspruncode();
				String message = jspruncode(request,parnum,threads,post,bbcodes,smilieslist,showjavacode,resources,locale,member,modertar,jspruncode);
				post.put("message", message);
				Map attamap = parseAttach(request,settings,extcreditname,post,showimage,uid,allowgetattach,readaccess,ftpurl,dateformat+" "+timeformat,timeoffset,modertar,null,false);
				message = replacemessage((String)attamap.get("message"),jspruncode,Common.toDigit(threads.get("allowhtml"))<=0&&Common.toDigit(post.get("htmlon"))<=0,0,null);
				int attachment = (Integer)attamap.get("attachment");
				if(attachment!=-1){
					post.put("attachment", attachment+"");
				}
				post.put("message", message);
				postmap.put(post,attamap.get("attalist"));
				ajaxpostlist.add(postmap);
			}
		}
		if (postlist != null && postlist.size() > 0) {
			request.setAttribute("postlist", ajaxpostlist);
		} else {
			request.setAttribute("postlist", null);
		}
		request.setAttribute("postno", settings.get("postno"));
		return mapping.findForward("spacilpost");
	}
}