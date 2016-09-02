package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Searchindex;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Md5Token;
public class SearchThreadAction extends BaseAction {
	@SuppressWarnings("unused")
	private static int convertInt(String s) {
		int count = 0;
		try {
			count = Integer.valueOf(s);
		} catch (Exception e) {
		}
		return count;
	}
	@SuppressWarnings("unchecked")
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		if(usergroups==null || convertInt(usergroups.get("allowsearch"))<=0){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		HttpSession session=request.getSession();
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		String hottaglist = "";
		if(settings.get("tagstatus").equals("1")){
			List<Map<String,String>> taglist = dataBaseService.executeQuery("SELECT tagname FROM jrun_tags WHERE closed=0 ORDER BY total DESC LIMIT 5");
			for(Map<String,String> tags:taglist){
				String tag = "<a href=\"tag.jsp?name="+Common.encode(tags.get("tagname"))+"\" target=\"_blank\">"+tags.get("tagname")+"</a>";
				hottaglist = hottaglist+"&nbsp;"+tag;
			}
			taglist = null;
		}
		request.setAttribute("hottaglist", hottaglist);
		return mapping.findForward("tosearch");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toSearchByType(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		if(usergroups==null || usergroups.get("allowsearch").equals("0")){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		HttpSession session=request.getSession();
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		String fid = request.getParameter("srchfid");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",fid));
		String typesql = "select * from jrun_threadtypes as t where t.special='1' order by t.displayorder";
		List<Map<String,String>> typelist = dataBaseService.executeQuery(typesql);
		request.setAttribute("threadtype", typelist);
		request.setAttribute("typeid", request.getParameter("typeid"));
		return mapping.findForward("tosearchbytype");
	}
	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		Map<String,String> forumStr = (Map<String,String>)request.getAttribute("forums");
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		if(usergroups==null || usergroups.get("allowsearch").equals("0")){
			request.setAttribute("show_message", getMessage(request, "group_nopermission",usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		Map<String,Map<String,String>> forumMap=dataParse.characterParse(forumStr.get("forums"),false);
		request.setAttribute("threadsticky", settings.get("threadsticky").split(","));
		String srchtxt = request.getParameter("srchtxt"); 
		String srchuname = request.getParameter("srchuname"); 
		HttpSession session = request.getSession();
		int uid = (Integer)session.getAttribute("jsprun_uid");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		int cachelife_text = 3600;
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String orderby = request.getParameter("orderby");
		String ascdesc = request.getParameter("ascdesc");
		orderby = Common.in_array(new String[]{"dateline","replies","views"},orderby)?orderby:"lastpost";
		ascdesc = !Common.empty(ascdesc)&&ascdesc.equals("asc")?"asc":"desc";
		int searchid = Common.intval(request.getParameter("searchid"));
		if (searchid >0 ) {
			List<Map<String,String>> searchindex = dataBaseService.executeQuery("select tids,keywords from jrun_searchindex as i where i.searchid="+searchid);
			if(searchindex!=null && searchindex.size()>0){
			Map<String,String> index = searchindex.get(0);
			String tids = index.get("tids");
			if (tids.equals("0")) {
				request.setAttribute("threadlist", null);
			} else {
				int size = Common.toDigit(dataBaseService.executeQuery("select count(*) as count from jrun_threads as t where t.tid in ("+tids+") and t.displayorder>='0'").get(0).get("count"));
				int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
				int page =Math.max(Common.intval(request.getParameter("page")), 1);
				Map<String,Integer> multiInfo=Common.getMultiInfo(size, tpp, page);
				page=multiInfo.get("curpage");
				int start_limit=multiInfo.get("start_limit");
				String url = "search.jsp?action=search&searchid=" + searchid + "&orderby=" + orderby + "&ascdesc=" + ascdesc;
				Map<String,Object> multi=Common.multi(size, tpp, page, url,0, 10, true, false, null);
				request.setAttribute("multi", multi);
				String hql = "SELECT t.*,f.name FROM jrun_threads as t LEFT JOIN jrun_forums as f on t.fid=f.fid where t.tid IN ( "+tids+" ) AND t.displayorder>='0' order by " + orderby + " " + ascdesc+" limit "+start_limit+","+tpp;
				List<Map<String,String>> disgetThread = dataBaseService.executeQuery(hql);
				if (disgetThread != null) {
					int ppp = member != null&& member.getPpp() > 0 ? member.getPpp() : Integer.valueOf(settings.get("postperpage"));
					for (Map<String,String> thread : disgetThread) {
						Common.procThread(thread, ppp);
					}
					index.put("keywords", Common.encode(index.get("keywords")));
					request.setAttribute("index", searchindex.get(0));
					request.setAttribute("threadlist", disgetThread);
				} else {
					request.setAttribute("threadlist", null);
				}
			}
			}else{
				request.setAttribute("threadlist", null);
			}
			if(Common.isshowsuccess(session, "search_redirect")){
				return mapping.findForward("searchresult");
			}else if(request.getParameter("path")==null && request.getParameter("page")==null){
				request.setAttribute("successInfo", getMessage(request, "search_redirect"));
				request.setAttribute("requestPath", "search.jsp?action=search&searchid=" + searchid+ "&orderby=" + orderby + "&ascdesc=" + ascdesc+"&path=1");
				return mapping.findForward("showMessage");
			}else{
				return mapping.findForward("searchresult");
			}
		} else {
			String messages = null;
			Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
			Map<Integer, Integer> postcredits=(Map<Integer,Integer>)creditspolicys.get("search");
			Map extcredits = dataParse.characterParse(settings.get("extcredits"), true);
			Set<Entry<Integer, Integer>> keys = postcredits.entrySet();
			for (Entry<Integer, Integer> temp : keys) {
				Integer key = temp.getKey();
				Map extcreditmap = (Map)extcredits.get(key);
				if(extcreditmap!=null){
					int extcredit = member==null?0:(Integer)Common.getValues(member, "extcredits"+key);
					int getattacreditvalue = temp.getValue();
					String lowerlimit = extcreditmap.get("lowerlimit")==null?"0":String.valueOf(extcreditmap.get("lowerlimit"));
					if(getattacreditvalue!=0&&extcredit-getattacreditvalue<=Integer.valueOf(lowerlimit)){
						String unit = extcreditmap.get("unit")!=null?"":extcreditmap.get("unit").toString();
						String title = extcreditmap.get("title")!=null?"":extcreditmap.get("title").toString();
						messages =  getMessage(request, "credits_policy_num_lowerlimit", title,lowerlimit,unit);;
						break;
					}
				}
			}
			if(messages!=null){
				request.setAttribute("errorInfo", messages);
				return mapping.findForward("showMessage");
			}
			if(uid!=0){
				Common.updatepostcredits("-", uid,1, postcredits);
				Common.updatepostcredits(uid, settings.get("creditsformula"));
			}
			try{
				if(submitCheck(request, "searchsubmit",true)){
					String searchctrl = settings.get("searchctrl"); 
					String maxspm = settings.get("maxspm"); 
					String maxsearchresult = settings.get("maxsearchresults"); 
					String special[] = request.getParameterValues("special[]"); 
					String srchfrom = request.getParameter("srchfrom"); 
					String srchfid[] = request.getParameterValues("srchfid"); 
					String srchtype = request.getParameter("srchtype"); 
					 String srchfilter = request.getParameter("srchfilter"); 
					String before = request.getParameter("before"); 
					srchtype = srchtype==null?"":srchtype;
					srchfilter = srchfilter==null?"":srchfilter;
					before = before == null?"0":before;
					if(usergroups.get("allowsearch").equals("2")&& srchtype.equals("fulltext")) {
						MessageResources resources = getResources(request);
						Locale locale = getLocale(request);
						String message=Common.periodscheck(settings.get("searchbanperiods"), Byte.valueOf(usergroups.get("disableperiodctrl")),timestamp,settings.get("timeoffset"),resources,locale);
						if(message!=null)
						{
							request.setAttribute("show_message", message);
							return mapping.findForward("nopermission");
						}
					} else if(!srchtype.equals("title")&&!srchtype.equals("blog")&&!"trade".equals(srchtype)) {
						srchtype = "title";
					}
					String srchuid = request.getParameter("srchuid");
					boolean forumsarray = true;
					StringBuffer forums =new StringBuffer();
					if (srchfid == null || srchfid[0].trim().equals("all")) {
						Set<String> fids = forumMap.keySet();
						String extgroupids = member!=null?member.getExtgroupids():"";
						for(String fid:fids){
							Map<String,String> forum = forumMap.get(fid);
							if ((forum.get("viewperm").equals(""))||Common.forumperm(forum.get("viewperm"), groupid, extgroupids)) {
								forums.append(fid + ",");
							}
						}
					} else {
						for (int i = 0; i < srchfid.length; i++) {
							if(Common.intval(srchfid[i])>0){
								forumsarray = false;
								forums.append(srchfid[i] + ",");
							}
						}
					}
					if (forums.length()>0) {
						forums.deleteCharAt(forums.length()-1);
					}
					if(forums.toString().equals("all")){
						forums.delete(0, forums.length());
					}
					if(Common.empty(srchtxt) && Common.empty(srchuid) && Common.empty(srchuname) && Common.empty(srchfrom) && !Common.in_array(new String[]{"digest","top"}, srchfilter) && Common.empty(special)) {
						request.setAttribute("errorInfo", getMessage(request, "search_invalid"));
						return mapping.findForward("showMessage");
					}else if(srchfid!=null && !(srchfid.length>0 && Common.in_array(srchfid, "all")) && forumsarray) {
						request.setAttribute("errorInfo", getMessage(request, "search_forum_invalid"));
						return mapping.findForward("showMessage");
					} else if(Common.empty(forums)) {
						request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
						return mapping.findForward("nopermission");
					}
					srchtxt = srchtxt == null ? "" : srchtxt;
					srchuname = srchuname == null ? "" : srchuname;
					StringBuffer spaceb = new StringBuffer();
					if (special != null) {
						for (int i = 0; i < special.length; i++) {
							spaceb.append(","+Common.intval(special[i]));
						}
					}
					String space = "";
					if (spaceb.length()>0) {
						space = spaceb.substring(1);
					}
					if(srchuid==null){
						srchuid = "0";
					}
					Md5Token md5 = Md5Token.getInstance();
					String searchstring = md5.getLongToken(srchtype + "|" + srchtxt + "|"+srchuid+"|" + srchuname+ "|" + forums + "|" + srchfrom + "|" + before + "|"+ srchfilter + "|" + space);
					String searchindexhql = "select s.searchid from jrun_searchindex as s where s.searchstring = '"+ searchstring + "' and s.expiration >" + timestamp;
					List<Map<String,String>> searchindex = dataBaseService.executeQuery(searchindexhql);
					if (searchindex != null && searchindex.size()>0) {
						try {
							response.sendRedirect("search.jsp?action=search&searchid="+searchindex.get(0).get("searchid")+"&orderby="+orderby+"&ascdesc="+ascdesc);
						} catch (IOException e) {
						}
						return null;
					} else { 
						String ip = Common.get_onlineip(request);
						if (!searchctrl.equals("0")) {
							int times = timestamp - convertInt(searchctrl);
							String ctrlhql = "select searchid from jrun_searchindex as s where s.useip='"+ ip + "' and uid=" + uid + " and s.dateline >="+ times+" limit 1";
							List<Map<String,String>> index = dataBaseService.executeQuery(ctrlhql);
							if (index != null && index.size()>0) {
								String message = getMessage(request, "search_ctrl", searchctrl);
								request.setAttribute("errorInfo", message);
								return mapping.findForward("showMessage");
							}
							index = null;
						}
						if (!maxspm.equals("0")) {
							int times = timestamp - 60;
							String maxhql = "select count(*) from Searchindex as s where s.useip='" + ip + "' and uid=" + uid + " and s.dateline>=" + times;
							int count = searchService.findseachindexcountbyHql(maxhql);
							if (count >= convertInt(maxspm)) {
								String message = getMessage(request, "search_toomany", maxspm);
								request.setAttribute("errorInfo", message);
								return mapping.findForward("showMessage");
							}
						}
						StringBuffer threadhql = new StringBuffer();
						if (srchtype.equals("fulltext")) {
							threadhql.append("select t.tid from jrun_threads as t left join jrun_posts as p on t.tid=p.tid where t.displayorder>='0' and ");
						} else {
							threadhql.append("select t.tid from jrun_threads as t where t.displayorder>='0' and ");
						}
						if("trade".equals(srchtype)){
							threadhql.append(" t.special=2 and ");
						}else if("blog".equals(srchtype)){
							threadhql.append(" t.blog=1 and ");
						}
						if (!srchtxt.equals("")) { 
							srchtxt = srchtxt.replaceAll("\\*", "%");
							srchtxt = srchtxt.toLowerCase();
							if (Common.matches(srchtxt,"[and|\\+|&|\\s+]") && !Common.matches(srchtxt,"[or|\\|]")) {
								srchtxt = srchtxt.replaceAll("( and |&| )", "+");
								String[] keyword = srchtxt.split("\\+");
								for (int i = 0; i < keyword.length; i++) {
									if (srchtype.equals("fulltext")) {
										threadhql.append("p.message like '%" + Common.addslashes(keyword[i].trim())+ "%' and ");
									} else {
										threadhql.append("t.subject like '%" + Common.addslashes(keyword[i].trim())+ "%' and ");
									}
								}
							} else {
								srchtxt = srchtxt.replaceAll("( or |\\|)", "+");
								String[] keyword = srchtxt.split("\\+");
								if(srchtype.equals("fulltext")){
									threadhql.append("(");
									for (int i = 0; i < keyword.length; i++) {
										threadhql.append("p.message like '%" + Common.addslashes(keyword[i].trim())+ "%' or ");
									}
								}else{
									threadhql.append("(");
									for (int i = 0; i < keyword.length; i++) {
										threadhql.append("t.subject like '%" + Common.addslashes(keyword[i].trim())+ "%' or ");
									}
								}
							}
						}
						int lastor = threadhql.lastIndexOf("or ");
						if (lastor > 0) {
							threadhql.delete(lastor, threadhql.length());
							threadhql.append(") and ");
						}
						if (!srchuname.equals("")) { 
							StringBuffer uids = new StringBuffer();
							srchuname = srchuname.replaceAll("\\*", "%");
							String memhql = "select uid from jrun_members as m where m.username like '"+ Common.addslashes(srchuname.trim()) + "' limit 0,50";
							List<Map<String,String>> memberlist = dataBaseService.executeQuery(memhql);
							if (memberlist != null && memberlist.size() > 0) {
								for (Map<String,String> members : memberlist) {
									uids.append(","+members.get("uid"));
								}
							}
							if(uids.length()>0){
								if (srchtype.equals("fulltext")) {
									threadhql.append("p.authorid in ( " + uids.substring(1) + " ) and ");
								} else {
									threadhql.append("t.authorid in ( " + uids.substring(1) + " ) and ");
								}
							}else{
								threadhql.append("0 and ");
							}
						}
						if(srchuid!=null && !srchuid.equals("0")){
							threadhql.append("t.authorid="+Common.intval(srchuid)+" and ");
						}
						if (srchtype.equals("blog")) {
							threadhql.append("t.blog=1 and ");
						} else if (srchtype.equals("trade")) {
							threadhql.append("t.special=2 and ");
						}
						if (srchfilter.equals("digest")) {
							threadhql.append("t.digest > 0 and ");
						} else if (srchfilter.equals("top")) {
							threadhql.append("t.displayorder > 0 and ");
						}
						if (!space.equals("")) {
							threadhql.append("t.special in ( " + space+ " ) and ");
						}
						if (!forums.toString().equals("")) {
							threadhql.append("t.fid in ( " + forums.toString() + " ) and ");
						}
						if (srchfrom!=null && !srchfrom.equals("0")) {
							int datetime = timestamp- Integer.valueOf(srchfrom);
							if (before.equals("0")) {
								threadhql.append("t.lastpost >= " + datetime + " and ");
							} else {
								threadhql.append("t.lastpost <=" + datetime + " and ");
							}
						}
						int lastand = threadhql.lastIndexOf("and");
						if (lastand > 0) {
							threadhql.delete(lastand, threadhql.length());
						}
						threadhql.append("order by t." + orderby + " " + ascdesc+" limit 0,"+maxsearchresult);
						List<Map<String,String>> threadlist = dataBaseService.executeQuery(threadhql.toString());
						StringBuffer tids = new StringBuffer("0");
						int threadcount = 0;
						if (threadlist != null && threadlist.size() > 0) {
							threadcount = threadlist.size();
							for (Map<String,String> t : threadlist) {
								tids.append(","+t.get("tid"));
							}
						}
						threadlist = null;
						Searchindex searchdex = new Searchindex();
						searchdex.setDateline(timestamp);
						int exprtation = timestamp + cachelife_text;
						searchdex.setExpiration(exprtation);
						searchdex.setKeywords(srchtxt + "+" + srchuname);
						searchdex.setSearchstring(searchstring);
						searchdex.setThreads(Short.valueOf(threadcount + ""));
						searchdex.setThreadtypeid(Short.valueOf("0"));
						searchdex.setTids(tids.toString());
						searchdex.setUid(uid);
						searchdex.setUseip(ip);
						int searchids = searchService.insertSearchindex(searchdex);
						try {
							response.sendRedirect("search.jsp?action=search&searchid="+searchids+"&orderby="+orderby+"&ascdesc="+ascdesc);
						} catch (IOException e) {
						}
						return null;
					}
				}
			}catch (Exception e) {
				request.setAttribute("resultInfo",e.getMessage());
				return mapping.findForward("showMessage");
			}
			Common.requestforward(response, "search.jsp");
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward searchbytype(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int searchid = Common.intval(request.getParameter("searchid"));
		HttpSession session = request.getSession();
		Members member = (Members)session.getAttribute("user");
		Map<String, String> settings = ForumInit.settings;
		String dateformat = (String)session.getAttribute("dateformat");
		String timeoffset = (String)session.getAttribute("timeoffset");
		String timeformat = (String)session.getAttribute("timeformat");
		int uid = (Integer) session.getAttribute("jsprun_uid");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Map<String,String> forumStr = (Map<String,String>)request.getAttribute("forums");
		Map<String,Map<String,String>> forumMap=dataParse.characterParse(forumStr.get("forums"),false);
		if(searchid > 0){
			int typeid = Common.intval(request.getParameter("typeid"));
			int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
			int page = Math.max(1, Common.intval(request.getParameter("page")));
			int start_limit = (page - 1) * tpp;
			List<Map<String,String>> searchMap = dataBaseService.executeQuery("SELECT searchstring, keywords, threads, threadtypeid, tids FROM jrun_searchindex WHERE searchid='"+searchid+"' AND threadtypeid='"+typeid+"'");
			if(searchMap==null||searchMap.size()<=0) {
				request.setAttribute("errorInfo", getMessage(request, "search_id_invalid"));
				return mapping.findForward("showMessage");
			}
			Map<String,String> index =  searchMap.get(0);
			List<Map<String,String>> threadlist = new ArrayList<Map<String,String>>();
			List<String> optionlist = new LinkedList<String>();
			if(index.get("threads").equals("0")){
				request.setAttribute("resultlist", null);
			}else{
				List<Map<String,String>> infoMap = dataBaseService.executeQuery("SELECT tid, subject, dateline FROM jrun_threads WHERE tid IN ("+index.get("tids")+") AND displayorder>=0 ORDER BY dateline LIMIT "+start_limit+","+tpp);
				for(Map<String,String> info :infoMap){
					Map<String,String> threadMap = new HashMap<String,String>();
					threadMap.put("tid", info.get("tid"));
					String dateline = Common.gmdate(dateformat+" "+timeformat, Integer.valueOf(info.get("dateline")), timeoffset);
					threadMap.put("dateline", dateline);
					threadMap.put("subject", info.get("subject"));
					threadlist.add(threadMap);
				}
				Common.include(request, response, servlet,"/forumdata/cache/threadtype_"+index.get("threadtypeid")+".jsp", null);
				infoMap = dataBaseService.executeQuery("SELECT tid, optionid, value FROM jrun_typeoptionvars WHERE tid IN ("+index.get("tids")+")");
				Map<String, String> threadtype = (Map<String, String>) request.getAttribute("threadtype");
				Map<Integer, Map<String, String>> dtype = null;
				if (threadtype != null) {
					dtype = dataParse.characterParse(threadtype.get("dtype"),true);
				}
				Map<String,Map<Integer,String>> typelist = new HashMap<String,Map<Integer,String>>();
				for(Map<String,String> info : infoMap){
					Map<String,String> option = dtype.get(Integer.valueOf(info.get("optionid")));
					if(option!=null&&Common.intval(option.get("search"))>0){
						Map<Integer,String> typesMap = typelist.get(info.get("tid"));
						typesMap = typesMap==null? new TreeMap<Integer,String>():typesMap;
						typesMap.put(Integer.valueOf(info.get("optionid")),info.get("value"));
						typelist.put(info.get("tid"), typesMap);
						optionlist.add(option.get("title"));
					}
				}
				String choiceshow = "";
				List<Map<String,String>> resultlist = new ArrayList<Map<String,String>>();
				for(Map<String,String> thread:threadlist) {
					Map result = new HashMap ();
					result.put("tid", thread.get("tid"));
					result.put("subject", thread.get("subject"));
					result.put("dateline", thread.get("dateline"));
					Map<Integer,String> valueMap = typelist.get(thread.get("tid"));
					Set<Integer> optionidSet = valueMap!=null?valueMap.keySet():null;
					List<String> options = new ArrayList();
					if(optionidSet!=null){
						for(Integer optionid :  optionidSet){
							Map<String,String> option = dtype.get(optionid);
							if(Common.in_array(new String[]{"select","radio"}, option.get("type"))){
								options.add(option.get("choices").split("\\\\n")[Common.intval(valueMap.get(optionid))-1]);
							} else if(option.get("type").equals("checkbox")) {
								String choicevalues = valueMap.get(optionid)==null?"":valueMap.get(optionid);
								String []choices = choicevalues.split("\t");
								String []choicesoption = option.get("choices").split("\\\\n");
								for(String choiceid:choices){
									choiceshow += choicesoption[Common.intval(choiceid)-1]+"&nbsp;";
								}
								options.add(choiceshow);
							} else if(option.get("type").equals("image")) {
								@SuppressWarnings("unused")
								String maxwidth = Common.intval(option.get("maxwidth"))>0 ? "width=\""+option.get("maxwidth")+"\"" : "";
								@SuppressWarnings("unused")
								String maxheight = Common.intval(option.get("maxheight"))>0 ? "height=\""+option.get("maxheight")+"\"" : "";
								options.add("");
							} else if(option.get("type").equals("url")) {
								options.add("");
							} else {
								options.add(valueMap.get(optionid));
							}
						}
					}
					result.put("option", options);
					resultlist.add(result);
				}
				Map<String,Object> multi = Common.multi(Common.intval(index.get("threads")), tpp, page, "search.jsp?action=threadtype&searchid="+searchid+"&typeid="+typeid+"&searchsubmit=yes",0, 10, true, false, null);
				request.setAttribute("multi", multi);
				request.setAttribute("optionlist", optionlist);
				request.setAttribute("resultlist", resultlist);
			}
			int colspan = optionlist.size()+2;
			request.setAttribute("colspan", colspan);
			if(Common.isshowsuccess(session, "search_redirect")){
				return mapping.findForward("search_type");
			}else if(request.getParameter("path")==null && request.getParameter("page")==null){
				request.setAttribute("successInfo", getMessage(request, "search_redirect"));
				request.setAttribute("requestPath", "search.jsp?action=threadtype&searchid="+searchid+"&typeid="+typeid+"&searchsubmit=yes");
				return mapping.findForward("showMessage");
			}else{
				return mapping.findForward("search_type");
			}
		}else{
			String messages = null;
			Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
			Map<Integer, Integer> postcredits=(Map<Integer,Integer>)creditspolicys.get("search");
			Map extcredits = dataParse.characterParse(settings.get("extcredits"), true);
			Set<Integer> keys = postcredits.keySet();
			for (Integer key : keys) {
				Map extcreditmap = (Map)extcredits.get(key);
				if(extcreditmap!=null){
					int extcredit = member==null?0:(Integer)Common.getValues(member, "extcredits"+key);
					int getattacreditvalue = Integer.valueOf(postcredits.get(key)+"");
					String lowerlimit = extcreditmap.get("lowerlimit")==null?"0":String.valueOf(extcreditmap.get("lowerlimit"));
					if(getattacreditvalue!=0&&extcredit-getattacreditvalue<=Integer.valueOf(lowerlimit)){
						String unit = extcreditmap.get("unit")!=null?"":extcreditmap.get("unit").toString();
						String title = extcreditmap.get("title")!=null?"":extcreditmap.get("title").toString();
						messages =  getMessage(request, "credits_policy_num_lowerlimit", title,lowerlimit,unit);;
						break;
					}
				}
			}
			if(messages!=null){
				request.setAttribute("errorInfo", messages);
				return mapping.findForward("showMessage");
			}
			if(uid!=0){
				Common.updatepostcredits("-", uid, 1,postcredits);
				Common.updatepostcredits(uid, settings.get("creditsformula"));
			}
			try{
				if(submitCheck(request, "searchsubmit")){
					StringBuffer forums =new StringBuffer();
					String srchfid[] = request.getParameterValues("srchfid"); 
					String searchctrl = settings.get("searchctrl"); 
					int timestamp = (Integer)(request.getAttribute("timestamp"));
					String maxspm = settings.get("maxspm"); 
					String maxsearchresult = settings.get("maxsearchresults"); 
					Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
					String ip = Common.get_onlineip(request);
					boolean forumsarray = true;
					if (srchfid == null || srchfid[0].trim().equals("all")) {
						Set<String> fids = forumMap.keySet();
						String extgroupids = member!=null?member.getExtgroupids():"";
						for(String fid:fids){
							Map<String,String> forum = forumMap.get(fid);
							if ((forum.get("viewperm").equals(""))||Common.forumperm(forum.get("viewperm"), groupid, extgroupids)) {
								forums.append(fid + ",");
							}
						}
					} else {
						for (int i = 0; i < srchfid.length; i++) {
							if(Common.intval(srchfid[i])>0){
								forumsarray = false;
								forums.append(srchfid[i] + ",");
							}
						}
					}
					if (forums.length()>0) {
						forums.deleteCharAt(forums.length()-1);
					}
					if(forums.toString().equals("all")){
						forums.delete(0, forums.length());
					}
					String typeid=request.getParameter("typeid");
					Common.include(request, response, servlet, "/forumdata/cache/threadtype_"+typeid+".jsp", null);
					Map<String, String> threadtype = (Map<String, String>) request.getAttribute("threadtype");
					Map<Integer, Map<String, String>> dtype = null;
					if (threadtype != null) {
						dtype = dataParse.characterParse(threadtype.get("dtype"),true);
					}
					String srchoption = "";String tab = "";
					if(dtype!=null&&dtype.size() > 0){
						Set<Integer> dtypes = dtype.keySet();
						for(Integer optionid:dtypes){
							srchoption +=tab+optionid;
							tab = "\t";
						}
					}
					Md5Token md5 = Md5Token.getInstance();
					String searchstring = md5.getLongToken("type|"+srchoption);
					int searchindex = 0;
					List<Map<String,String>> indexMap = dataBaseService.executeQuery("SELECT searchid, dateline,('"+searchctrl+"'<>'0' AND "+(uid==0 ? "useip='"+ip+"'" : "uid='"+uid+"'")+" AND "+timestamp+"-dateline<"+searchctrl+") AS flood,(searchstring='"+searchstring+"' AND expiration>'"+timestamp+"') AS indexvalid FROM jrun_searchindex	WHERE ('"+searchctrl+"'<>'0' AND "+(uid==0 ? "useip='"+ip+"'" : "uid='"+uid+"'")+" AND "+timestamp+"-dateline<"+searchctrl+") OR (searchstring='"+searchstring+"' AND expiration>'"+timestamp+"') ORDER BY flood");
					for(Map<String,String> index : indexMap) {
						if(Common.intval(index.get("indexvalid"))>0 && Common.intval(index.get("dateline")) > 0) {
							searchindex = Integer.valueOf(index.get("searchid"));
							break;
						} else if(Common.intval(index.get("flood"))>0) {
								String message = getMessage(request, "search_ctrl", searchctrl);
								request.setAttribute("errorInfo", message);
								return mapping.findForward("showMessage");
						}
					}
					List<Map<String,String>> searchoption = null;
					if(dtype!=null&&dtype.size() > 0){
						searchoption = new ArrayList<Map<String,String>>();
						Set<Integer> dtypes = dtype.keySet();
						for(Integer optionid:dtypes){
							Map<String,String> options = new HashMap<String,String>();
							String type = request.getParameter("searchoption["+optionid+"][type]");
							String value = request.getParameter("searchoption["+optionid+"][value]");
							if("number".equals(type)){
								String condition = request.getParameter("searchoption["+optionid+"][condition]");
								options.put("condition", condition);
							}
							options.put("type", type);
							options.put("value", value);
							options.put("optionid", optionid+"");
							searchoption.add(options);
						}
					}
					if(searchindex>0) {
						searchid = searchindex;
					} else {
						if((searchoption==null || searchoption.size()<=0) && Common.empty(typeid)) {
							request.setAttribute("successInfo", getMessage(request, "search_threadtype_invalid"));
							request.setAttribute("requestPath", "search.jsp?srchtype=threadtype&typeid="+typeid+"&srchfid="+request.getParameter("fid"));
							return mapping.findForward("showMessage");
						} else if(srchfid!=null && !(srchfid.length>0 && Common.in_array(srchfid, "all")) && forumsarray) {
							request.setAttribute("successInfo", getMessage(request, "search_forum_invalid"));
							request.setAttribute("requestPath", "search.jsp?srchtype=threadtype&typeid="+typeid+"&srchfid="+request.getParameter("fid"));
							return mapping.findForward("showMessage");
						} else if(Common.empty(forums)) {
							request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
							return mapping.findForward("nopermission");
						}
						if (!maxspm.equals("0")) {
							int times = timestamp - 60;
							String maxhql = "select count(*) from Searchindex as s where s.useip='" + ip + "' and uid=" + uid + " and s.dateline>=" + times;
							int count = searchService.findseachindexcountbyHql(maxhql);
							if (count >= convertInt(maxspm)) {
								String message = getMessage(request, "search_toomany", maxspm);
								request.setAttribute("errorInfo", message);
								return mapping.findForward("showMessage");
							}
						}
						String  sqlsrch = ""; String or = "";
						if(!Common.empty(searchoption)&&searchoption.size()>0){
							for(Map<String,String> options:searchoption){
								if(!Common.empty(options.get("value"))){
									String sql = null;
									String type = options.get("type");
									if(Common.in_array(new String[]{"number","radio","select"}, type)){
										int value = Common.intval(options.get("value"));
										String exp = "=";
										if(options.get("condition")!=null){
											exp = options.get("condition").equals("1")?">":"<";
										}
										sql = "value"+exp+value;
									}else if("checkbox".equals(type)){
										sql = "value LIKE '%\t"+options.get("value")+"\t%'";
									}else{
										sql = "value LIKE '%"+options.get("value")+"%'";
									}
									sqlsrch += or+"(optionid='"+options.get("optionid")+"' AND "+sql+")";
									or = " OR ";
								}
							}
						}
						int threads = 0; String tids = "0";
						List<Map<String,String>> postMap = dataBaseService.executeQuery("SELECT tid, typeid FROM jrun_typeoptionvars WHERE (expiration='0' OR expiration>'"+timestamp+"') "+(!Common.empty(sqlsrch) ? "AND "+sqlsrch : "")+"");
						for(Map<String,String> post:postMap){
							if(post.get("typeid").equals(typeid)){
								tids += ","+post.get("tid");
							}
						}
						if(!Common.empty(forums)){
							postMap = dataBaseService.executeQuery("SELECT tid, closed FROM jrun_threads WHERE tid IN ("+tids+") AND fid IN ("+forums+") LIMIT "+maxsearchresult);
							tids = "0";
							for(Map<String,String> post:postMap){
								tids +=","+post.get("tid");
								threads++;
							}
						}
						searchid = dataBaseService.insert("INSERT INTO jrun_searchindex (keywords, searchstring, useip, uid, dateline, expiration, threads, threadtypeid, tids) VALUES ('', '"+searchstring+"', '"+ip+"', '"+uid+"', '"+timestamp+"', '0', '"+threads+"', '"+typeid+"', '"+tids+"')",true);
					}
					try {
						response.sendRedirect("search.jsp?action=threadtype&searchid="+searchid+"&typeid="+typeid+"&searchsubmit=yes");
					} catch (IOException e) {
					}
					return null;
				}
			}catch (Exception e) {
				request.setAttribute("resultInfo",e.getMessage());
				return mapping.findForward("showMessage");
			}
			Common.requestforward(response, "search.jsp?srchtype=threadtype");
			return null;
		}
	}
}
