package cn.jsprun.struts.action;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Forumfields;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Styles;
import cn.jsprun.domain.Templates;
import cn.jsprun.domain.Threadtypes;
import cn.jsprun.domain.Typemodels;
import cn.jsprun.domain.Typeoptions;
import cn.jsprun.domain.Typevars;
import cn.jsprun.domain.TypevarsId;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.utils.Base64;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
public class ForumManageAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward forumadd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "forumsubmit")){
				boolean flag = false;
				String successInfo =null;
				String errorInfo = null;
				String name = request.getParameter("name");
				if (name != null && name.length() <= 50) {
					Forums forum = new Forums();
					Forumfields forumfield = new Forumfields();
					forum.setName(name);
					forum.setStatus((byte)1);
					forum.setSimple((short)0);
					forumfield.setDescription("");
					forumfield.setFormulaperm("");
					forumfield.setModerators("");
					forumfield.setRules("");
					forumfield.setThreadtypes("");
					forumfield.setViewperm("");
					forumfield.setPostperm("");
					forumfield.setReplyperm("");
					forumfield.setGetattachperm("");
					forumfield.setPostattachperm("");
					forumfield.setKeywords("");
					forumfield.setModrecommend("");
					forumfield.setTradetypes("");
					forumfield.setTypemodels("");
					String add = request.getParameter("add");
					if ("cateGroup".equals(add)) {
						forum.setFup((short)0);
						forum.setType("group");
						successInfo = getMessage(request, "a_forum_add_sort_s",name);
						errorInfo = getMessage(request, "a_forum_add_sort_f",name);
					}
					else {
						short fup = (short)Common.intval(request.getParameter("fup"));
						if(fup==0){
							errorInfo = getMessage(request, "a_forum_noparent",name);
							request.setAttribute("message", errorInfo);
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						String projectId = request.getParameter("projectId");
						forum.setAllowsmilies(Byte.valueOf("1"));
						forum.setAllowbbcode(Byte.valueOf("1"));
						forum.setAllowimgcode(Byte.valueOf("1"));
						forum.setAllowshare(Byte.valueOf("1"));
						forum.setAllowpostspecial(Short.valueOf("127"));
						if (projectId!=null) {
							List<Map<String,String>> projects=dataBaseService.executeQuery("SELECT value FROM jrun_projects WHERE id='"+projectId+"'");
							if(projects!=null&&projects.size()>0){
								Map map = dataParse.characterParse(projects.get(0).get("value"),false);
								forum = (Forums) this.setValues(forum, map);
								forumfield = (Forumfields) this.setValues(forumfield, map);
							}
						}
						forum.setFup(fup);
						if ("cateForum".equals(add)) {
							forum.setType("forum");
						} else if ("cateSub".equals(add)) {
							forum.setType("sub");
						}
						successInfo = getMessage(request, "a_forum_add_sort_s",name);
						errorInfo = getMessage(request, "a_forum_add_sort_f",name);
					}
					if (forumService.addForum(forum)) {
						forumfield.setFid(forum.getFid());
						forumfieldService.addForumfield(forumfield);
						if(!"cateGroup".equals(add)){
							this.copyModerator(forum.getFid(), forum.getFup(), (byte)1);
						}
						flag = true;
					}
				} else {
					errorInfo = getMessage(request, "a_forum_add_sort_f_errorinfo");
				}
				if (flag) {
					Cache.updateCache("forums");
					request.setAttribute("message",successInfo);
					request.setAttribute("url_forward","admincp.jsp?action=forumsedit");
				} else {
					request.setAttribute("message", errorInfo);
					request.setAttribute("return", true);
				}
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String fupid = request.getParameter("fupid");
		List<Map<String,String>> forumList=dataBaseService.executeQuery("SELECT fid, name, type, fup FROM jrun_forums WHERE type<>'sub' ORDER BY displayorder");
		List<Map<String,String>> groups = new ArrayList<Map<String,String>>();
		List<Map<String,String>> forums = new ArrayList<Map<String,String>>();
		if(forumList!=null){
			for(Map<String,String> forum:forumList)
			{
				forum.put("name", Common.strip_tags(forum.get("name")));
				String type=forum.get("type");
				if("group".equals(type)){
					groups.add(forum);
				}else if("forum".equals(type)){
					forums.add(forum);
				}
				if(fupid!=null&&forum.get("fid").equals(fupid)){
					request.setAttribute("fupid", fupid);
					request.setAttribute("type", type);
				}
			}
		}
		request.setAttribute("groups", groups);
		request.setAttribute("forums", forums);
		request.setAttribute("projects", dataBaseService.executeQuery("SELECT id, name FROM jrun_projects WHERE type='forum'"));
		return mapping.findForward("toAddForum");
	}
	public ActionForward forumsedit(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		try{
			if(submitCheck(request, "editsubmit")){
				List<Map<String,String>> forums=dataBaseService.executeQuery("SELECT fid FROM jrun_forums");
				if (forums != null) {
					for (Map<String,String> forum : forums) {
						String disPlayOrder = request.getParameter(forum.get("fid"));
						if (disPlayOrder != null) {					
							dataBaseService.runQuery("UPDATE jrun_forums SET displayorder='"+toDisPlayOrder(disPlayOrder)+"' WHERE fid='"+forum.get("fid")+"'",true);
						}
					}
				}
				request.setAttribute("message", getMessage(request, "a_forum_set_update_success"));
				request.setAttribute("url_forward", "admincp.jsp?action=forumsedit");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> forumList=dataBaseService.executeQuery("SELECT f.fid, f.type, f.status, f.name, f.fup, f.displayorder, f.inheritedmod, ff.moderators FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) ORDER BY f.type<>'group', f.displayorder");
		if(forumList!=null)
		{
			StringBuffer forums=new StringBuffer();
			for(Map<String,String> forumi:forumList){
				if("group".equals(forumi.get("type"))){
					forums.append("<ul>"+showforum(forumi,forumi.get("type"),"index.jsp",request));
					for(Map<String,String> forumj:forumList){
						if(forumi.get("fid").equals(forumj.get("fup"))&&"forum".equals(forumj.get("type"))){
							forums.append("<ul>"+showforum(forumj,forumj.get("type"),"index.jsp",request));
								for(Map<String,String> forumk:forumList){
									if(forumj.get("fid").equals(forumk.get("fup"))&&"sub".equals(forumk.get("type"))){
										forums.append("<ul>"+showforum(forumk,forumk.get("type"),"index.jsp",request)+"</ul>");
									}
								}
							forums.append("</ul>");
						}
					}
					forums.append("</ul>");
				}
			}
			request.setAttribute("forums", forums);
		}
		return mapping.findForward("toEditForum");
	}
	public ActionForward forumsmerge(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		int source = Common.toDigit(request.getParameter("sourceId"));
		int target = Common.toDigit(request.getParameter("targetId"));
		try{
			if(!submitCheck(request, "mergesubmit")||source==target){
				HttpSession session=request.getSession();
				short groupid = (Short)session.getAttribute("jsprun_groupid");
				Members member = (Members)session.getAttribute("user");
				request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
				return mapping.findForward("toMergeForum");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> forums=dataBaseService.executeQuery("SELECT fid FROM jrun_forums WHERE fid IN ('"+source+"', '"+target+"') AND type<>'group'");
		if (forums!=null&&forums.size()!=2) {
			request.setAttribute("message", getMessage(request, "a_forum_forum_no_exist"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		List<Map<String,String>> upforum=dataBaseService.executeQuery("SELECT fid FROM jrun_forums WHERE fup='"+source+"'");
		if (upforum != null && upforum.size() > 0) {
			request.setAttribute("message",getMessage(request, "a_forum_update_errorinfo"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		dataBaseService.runQuery("UPDATE jrun_threads SET fid='"+target+"' WHERE fid='"+source+"'",true);
		dataBaseService.runQuery("UPDATE jrun_posts SET fid='"+target+"' WHERE fid='"+source+"'",true);
		Map<String,String> forum=dataBaseService.executeQuery("SELECT threads, posts FROM jrun_forums WHERE fid='"+source+"'").get(0);
		dataBaseService.runQuery("UPDATE jrun_forums SET threads=threads+"+forum.get("threads")+", posts=posts+"+forum.get("posts")+" WHERE fid='"+target+"'",true);
		dataBaseService.runQuery("UPDATE jrun_access SET fid='"+target+"' WHERE fid='"+source+"'",true);
		dataBaseService.runQuery("DELETE FROM jrun_forums WHERE fid='"+source+"'",true);
		dataBaseService.runQuery("DELETE FROM jrun_forumfields WHERE fid='"+source+"'",true);
		dataBaseService.runQuery("DELETE FROM jrun_moderators WHERE fid='"+source+"'",true);
		request.setAttribute("message", getMessage(request, "a_forum_unite_s"));
		request.setAttribute("url_forward","admincp.jsp?action=forumsedit");
		return mapping.findForward("message");
	}
	@SuppressWarnings("unchecked")
	public ActionForward forumdetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		short fid = Short.valueOf(request.getParameter("fid"));
		try{
			if(submitCheck(request, "detailsubmit")||submitCheck(request, "saveconfigsubmit")){
				Forums forum = forumService.findById(fid);
				String name = request.getParameter("name");
				if (name.length() > 50) {
					request.setAttribute("message", getMessage(request, "a_forum_add_sort_f_errorinfo"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if ("group".equals(forum.getType())) {
					forum.setName(name);
					forum.setForumcolumns(toForumColumns(request.getParameter("forumcolumns")));
					forumService.updateForum(forum);
				} else {
					String checkResult = request.getParameter("checkResult");
					if (!"true".equals(checkResult)) {
						request.setAttribute("message", getMessage(request, "a_forum_popedom_expression_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					short fup = Short.valueOf(request.getParameter("fup"));
					short oldfup=forum.getFup();
					Forums upForum = forumService.findById(fup);
					Forumfields forumfield = forumfieldService.findById(fid);
					forum = (Forums) this.setValues(forum, request);
					if(oldfup!=fup){
						Map<String,String> forumcount=dataBaseService.executeQuery("SELECT count(*) AS count FROM jrun_forums WHERE fup='"+fid+"'").get(0);
						if(forumcount!=null&&!"0".equals(forumcount.get("count"))){
							request.setAttribute("message", getMessage(request, "a_forum_up_down_error"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
					forumfield = (Forumfields) this.setValues(forumfield, request);
					forum.setType(upForum.getType().equals("group") ? "forum": "sub");
					short simple = Short.valueOf(request.getParameter("simple"));
					int subforumsindex = Integer.valueOf(request.getParameter("subforumsindex"));
					int defaultorderfield = Integer.valueOf(request.getParameter("defaultorderfield"));
					int defaultorder = Integer.valueOf(request.getParameter("defaultorder"));
					subforumsindex = subforumsindex == -1 ? 0: (subforumsindex == 1 ? 8 : 16);
					defaultorderfield = defaultorderfield * 64;
					defaultorder = defaultorder * 32;
					simple += subforumsindex;
					simple += defaultorderfield;
					simple += defaultorder;
					forum.setSimple(simple);
					short allowpostspecial = 0;
					for (int i = 0; i < 6; i++) {
						String postspecial = request.getParameter("allowpostspecial[" + (i + 1) + "]");
						if (postspecial != null) {
							allowpostspecial += Math.pow(2, i)* Short.valueOf(postspecial);
						}
					}
					forum.setAllowpostspecial(allowpostspecial);
					Map<String,String> settings=ForumInit.settings;
					String allowpostspecialtrade=request.getParameter("allowpostspecial[2]");
					String tradetypes=settings.get("tradetypes");
					if(tradetypes!=null&&tradetypes.length()>0&&allowpostspecialtrade!=null) {
						Map<String,String> tradetypesmap=dataParse.characterParse(tradetypes, true);
						String[] tradetypesnews=request.getParameterValues("tradetypesnew");
						if(tradetypesnews==null||tradetypesnews.length==tradetypesmap.size()){
							forumfield.setTradetypes("");
						}else{
							Map<Integer,String> tradetypestemp=new TreeMap<Integer, String>();
							for (String tradetype : tradetypesnews) {
								tradetypestemp.put(tradetypestemp.size(), tradetype);
							}
							forumfield.setTradetypes(dataParse.combinationChar(tradetypestemp));
						}
					}else{
						forumfield.setTradetypes("");
					}
					Map modrecommend = forumfield.getModrecommend().equals("") ? new HashMap(): dataParse.characterParse(forumfield.getModrecommend(),false);
					String modrecommend_open = request.getParameter("modrecommend[open]");
					modrecommend.put("open", modrecommend_open);
					if ("1".equals(modrecommend_open)) {
						String modrecommend_sort = request.getParameter("modrecommend[sort]");
						String modrecommend_orderby =  request.getParameter("modrecommend[orderby]");
						String modrecommend_num = request.getParameter("modrecommend[num]");
						String modrecommend_maxlength = request.getParameter("modrecommend[maxlength]");
						String modrecommend_cachelife = request.getParameter("modrecommend[cachelife]");
						String modrecommend_dateline = request.getParameter("modrecommend[dateline]");
						modrecommend.put("sort", "".equals(modrecommend_sort)?"0":modrecommend_sort);
						modrecommend.put("orderby", "".equals(modrecommend_orderby)?"0":modrecommend_orderby);
						modrecommend.put("num", Common.toDigit("".equals(modrecommend_num) ? "10": modrecommend_num));
						modrecommend.put("maxlength", Common.toDigit(modrecommend_maxlength));
						modrecommend.put("cachelife", Common.toDigit("".equals(modrecommend_cachelife) ? "900": modrecommend_cachelife));
						modrecommend.put("dateline", Common.toDigit(modrecommend_dateline));
					}
					forumfield.setModrecommend(dataParse.combinationChar(modrecommend));
					int autoclose = Integer.valueOf(request.getParameter("autoclose"));
					short autoclosetime = 0;
					if (autoclose != 0) {
						autoclosetime = (short)(Common.range(Common.intval(request.getParameter("autoclosetime")), 32768, -32768)* autoclose);
					}
					forum.setAutoclose(autoclosetime);
					Map postcredits = null;
					Map replycredits = null;
					Map digestcredits = null;
					Map postattachcredits = null;
					Map getattachcredits = null;
					Map extcredits = dataParse.characterParse(settings.get("extcredits_bak"),true);
					Set<Integer> keys = extcredits.keySet();
					for (Integer key : keys) {
						postcredits = toMap(postcredits, request, key,"postcredits");
						replycredits = toMap(replycredits, request, key,"replycredits");
						digestcredits = toMap(digestcredits, request, key,"digestcredits");
						postattachcredits = toMap(postattachcredits, request, key,"postattachcredits");
						getattachcredits = toMap(getattachcredits, request, key,"getattachcredits");
					}
					forumfield.setPostcredits(postcredits == null ? "" : dataParse.combinationChar(postcredits));
					forumfield.setReplycredits(replycredits == null ? "" : dataParse.combinationChar(replycredits));
					forumfield.setDigestcredits(digestcredits == null ? "": dataParse.combinationChar(digestcredits));
					forumfield.setPostattachcredits(postattachcredits == null ? "": dataParse.combinationChar(postattachcredits));
					forumfield.setGetattachcredits(getattachcredits == null ? "": dataParse.combinationChar(getattachcredits));
					Map threadtypesMap = new HashMap();
					byte required = Byte.valueOf(request.getParameter("threadtypes[required]"));
					byte listable = Byte.valueOf(request.getParameter("threadtypes[listable]"));
					byte prefix = Byte.valueOf(request.getParameter("threadtypes[prefix]"));
					Map types = new TreeMap();
					Map selectboxs = new TreeMap();
					Map flats = new TreeMap();
					Map specials = new TreeMap();
					Map shows = new TreeMap();
					Map expirations = new TreeMap();
					Map modelids = new TreeMap();
					List<Threadtypes> threadtypes = threadtypeService.findAll();
					if (threadtypes != null && threadtypes.size() > 0) {
						for (Threadtypes threadtype : threadtypes) {
							String options = request.getParameter("threadtypes[options]["+ threadtype.getTypeid() + "]");
							String show = request.getParameter("threadtypes[show]["+ threadtype.getTypeid() + "]");
							if (options != null && !"".equals(options)&& !"0".equals(options)) {
								types.put(threadtype.getTypeid().intValue(),threadtype.getName());
								specials.put(threadtype.getTypeid().intValue(),threadtype.getSpecial().toString());
								expirations.put(threadtype.getTypeid().intValue(),threadtype.getExpiration().toString());
								modelids.put(threadtype.getTypeid().intValue(),threadtype.getModelid().toString());
								if ("1".equals(options)) {
									flats.put(threadtype.getTypeid().intValue(),threadtype.getName());
								} else if ("2".equals(options)) {
									selectboxs.put(threadtype.getTypeid().intValue(), threadtype.getName());
								}
								if (show != null) {
									shows.put(threadtype.getTypeid().intValue(), 1);
								} else {
									shows.put(threadtype.getTypeid().intValue(), 0);
								}
							}
						}
					}
					for (int i = 0; i < 10; i++) {
						String newname = request.getParameter("newname[" + i + "]");
						if (newname != null && !"".equals(newname)) {
							List<Threadtypes> threadtypeList = threadtypeService.findByProperty("name", newname);
							if (threadtypeList == null && threadtypeList.size() > 0) {
							} else {
								String newdescription = request.getParameter("newdescription[" + i + "]");
								newname = newname.length() > 255 ? newname.substring(0, 255) : newname;
								Threadtypes threadtype = new Threadtypes();
								threadtype.setName(newname);
								threadtype.setDescription(newdescription);
								threadtype.setTemplate("");
								if (threadtypeService.addThreadtype(threadtype)) {
									String[] newoptions = request.getParameterValues("newoptions[" + i+ "]");
									if (!"0".equals(newoptions)) {
										types.put(threadtype.getTypeid().intValue(),threadtype.getName());
										if ("1".equals(newoptions)) {
											flats.put(threadtype.getTypeid().intValue(), threadtype.getName());
										} else if ("2".equals(newoptions)) {
											selectboxs.put(threadtype.getTypeid().intValue(), threadtype.getName());
										}
									}
								}
							}
						}
					}
					if (types != null && types.size() > 0) {
						threadtypesMap.put("required", required);
						threadtypesMap.put("listable", listable);
						threadtypesMap.put("prefix", prefix);
						threadtypesMap.put("types", types);
						threadtypesMap.put("selectbox",selectboxs.size() > 0 ? selectboxs : null);
						threadtypesMap.put("flat", flats.size() > 0 ? flats : null);
						threadtypesMap.put("special",specials.size() > 0 ? specials : null);
						threadtypesMap.put("show", shows.size() > 0 ? shows : null);
						threadtypesMap.put("expiration",expirations.size() > 0 ? expirations : null);
						threadtypesMap.put("modelid",modelids.size() > 0 ? modelids : null);
					}
					forumfield.setThreadtypes(threadtypesMap.size() > 0 ? dataParse.combinationChar(threadtypesMap) : "");
					forumfield.setViewperm(this.toString(request.getParameterValues("viewperm")));
					forumfield.setPostperm(this.toString(request.getParameterValues("postperm")));
					forumfield.setReplyperm(this.toString(request.getParameterValues("replyperm")));
					forumfield.setGetattachperm(this.toString(request.getParameterValues("getattachperm")));
					forumfield.setPostattachperm(this.toString(request.getParameterValues("postattachperm")));
					String[] props = new String[] { "digestposts", "posts","pageviews", "oltime", "extcredits1", "extcredits2","extcredits3", "extcredits4", "extcredits5","extcredits6", "extcredits7", "extcredits8" };
					String forumlaperm1 = request.getParameter("formulaperm");
					String forumlaperm2 = forumlaperm1;
					for (String oldProp : props) {
						String newProp = "\\$_DSESSION['" + oldProp + "']";
						forumlaperm2=forumlaperm2.replaceAll(oldProp+"[^']", newProp);
					}
					Map forumlaperms = new TreeMap();
					forumlaperms.put(0, forumlaperm1);
					forumlaperms.put(1, forumlaperm2);
					forumfield.setFormulaperm(dataParse.combinationChar(forumlaperms));
					forumService.updateForum(forum);
					forumfieldService.updateForumfield(forumfield);
					Cache.updateCache("forums");
					if (submitCheck(request, "saveconfigsubmit")) {
						boolean isfounder = (Boolean)request.getAttribute("isfounder");
						if(!isfounder){
							request.setAttribute("message", getMessage(request, "a_setting_not_createmen_access"));
							return mapping.findForward("message");
						}
						List<Map<String,String>> projects=dataBaseService.executeQuery("SELECT id,name,description FROM jrun_projects WHERE id='"+request.getParameter("projectId")+"'");
						request.setAttribute("fid", fid);
						request.setAttribute("project", projects!=null&&projects.size()>0?projects.get(0):null);
						return mapping.findForward("toProjectadd");
					}
				}
				request.setAttribute("message", getMessage(request, "a_forum_set_update_success"));
				request.setAttribute("url_forward","admincp.jsp?action=forumsedit");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Forums forum = forumService.findById(fid);
		if(forum==null){
			request.setAttribute("message", getMessage(request, "a_forum_forum_no_exist"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		if (!"group".equals(forum.getType())) {
			String projectId = request.getParameter("projectId");
			Forumfields forumfield = forumfieldService.findById(fid);
			if (projectId != null) {
				List<Map<String,String>> projects=dataBaseService.executeQuery("SELECT value FROM jrun_projects WHERE id='"+projectId+"'");
				if(projects!=null&&projects.size()>0){
					Map map = dataParse.characterParse(projects.get(0).get("value"),false);
					forum = (Forums) this.setValues(forum, map);
					forumfield = (Forumfields) this.setValues(forumfield, map);
				}
			} else {
				projectId = "0";
			}
			request.setAttribute("proId", projectId);
			request.setAttribute("projects", dataBaseService.executeQuery("SELECT id, name FROM jrun_projects WHERE type='forum'"));
			request.setAttribute("styleTemplages", dataBaseService.executeQuery("SELECT styleid, name FROM jrun_styles WHERE available=1"));
			short simple = forum.getSimple();
			int subforumsindex = -1;
			int defaultorderfield = 0;
			int defaultorder = 0;
			defaultorderfield = simple / 64;
			simple %= 64;
			defaultorder = simple / 32;
			simple %= 32;
			if (simple >= 16) {
				subforumsindex = 0;
				simple -= 16;
			}
			if (simple >= 8) {
				subforumsindex = 1;
				simple -= 8;
			}
			request.setAttribute("simple", simple);
			request.setAttribute("subforumsindex", subforumsindex);
			request.setAttribute("defaultorderfield", defaultorderfield);
			request.setAttribute("defaultorder", defaultorder);
			Map modrecommend = dataParse.characterParse(forumfield.getModrecommend(),false);
			request.setAttribute("modrecommend", modrecommend);
			int autoclosetime = forum.getAutoclose();
			int autoclose = 0;
			if (autoclosetime < 0) {
				autoclose = -1;
				autoclosetime = Math.abs(autoclosetime);
			} else if (autoclosetime > 0) {
				autoclose = 1;
			}
			request.setAttribute("autoclose", autoclose);
			request.setAttribute("autoclosetime", autoclosetime);
			Map<String,String> settings=ForumInit.settings;
			Map extcredits = dataParse.characterParse(settings.get("extcredits_bak"),true);
			Map postcredits = dataParse.characterParse(forumfield.getPostcredits(), false);
			Map replycredits =  dataParse.characterParse(forumfield.getReplycredits(), false);
			Map digestcredits =  dataParse.characterParse(forumfield.getDigestcredits(),false);
			Map postattachcredits =  dataParse.characterParse(forumfield.getPostattachcredits(),false);
			Map getattachcredits = dataParse.characterParse(forumfield.getGetattachcredits(), false);
			request.setAttribute("extcredits", extcredits);
			request.setAttribute("postcredits", postcredits);
			request.setAttribute("replycredits", replycredits);
			request.setAttribute("digestcredits", digestcredits);
			request.setAttribute("postattachcredits", postattachcredits);
			request.setAttribute("getattachcredits", getattachcredits);
			List<Threadtypes> threadtypes = threadtypeService.findAll();
			Map hasTypevars = new HashMap();
			if (threadtypes != null) {
				for (Threadtypes threadtype : threadtypes) {
					if(threadtype.getSpecial()==1){
						Short typeid = threadtype.getTypeid();
						Map<String,String> typevars=dataBaseService.executeQuery("SELECT count(*) count	FROM jrun_typevars WHERE typeid="+typeid).get(0);
						if (typevars != null && Integer.valueOf(typevars.get("count"))>0) {
							hasTypevars.put(typeid, true);
						}
					}
				}
			}
			Map threadtypesMap = null;
			if (!"".equals(forumfield.getThreadtypes())) {
				Map options = new HashMap();
				Map show = new HashMap();
				threadtypesMap =  dataParse.characterParse(forumfield.getThreadtypes(), false);
				Map flats = (Map) threadtypesMap.get("flat");
				Map selectboxs = (Map) threadtypesMap.get("selectbox");
				Map shows = (Map) threadtypesMap.get("show");
				if (flats != null) {
					Set<Integer> keys = flats.keySet();
					for (Integer key : keys) {
						options.put(key.shortValue(), 1);
					}
				}
				if (selectboxs != null) {
					Set<Integer> keys = selectboxs.keySet();
					for (Integer key : keys) {
						options.put(key.shortValue(), 2);
					}
				}
				if (shows != null) {
					Iterator<Entry> ite = shows.entrySet().iterator();
					while (ite.hasNext()) {
						Entry e = ite.next();
						Integer key = (Integer)e.getKey();
						show.put(key.shortValue(), e.getValue());
					}
				}
				threadtypesMap.put("options", options);
				threadtypesMap.put("show", show);
				if (options != null && options.size() > 0) {
					threadtypesMap.put("status", 1);
				}
			}
			request.setAttribute("threadtypes", threadtypes!=null&&threadtypes.size()>0?threadtypes:null);
			request.setAttribute("hasTypevars", hasTypevars);
			request.setAttribute("threadtypesMap", threadtypesMap);
			int allowpostspecial = forum.getAllowpostspecial();
			if (allowpostspecial < 127) {
				int[] allowpostspecials = new int[6];
				for (int i = 0; i <= 5; i++) {
	                allowpostspecials[i] = allowpostspecial%2;
	                allowpostspecial = allowpostspecial>>1;
	            }
				request.setAttribute("allowpostspecials", allowpostspecials);
			}
			String tradetypes=settings.get("tradetypes");
			if(tradetypes!=null&&tradetypes.length()>0) {
				Map<String,String> forumtradetypes=dataParse.characterParse(forumfield.getTradetypes(), true);
				Map<String,String> tradetypesmap=dataParse.characterParse(tradetypes, true);
				StringBuffer tradetypeselect = new StringBuffer();
				Set<String> typeids=tradetypesmap.keySet();
				for (String typeid : typeids) {
					tradetypeselect.append("<input class=\"checkbox\" type=\"checkbox\" name=\"tradetypesnew\" value=\""+typeid+"\" "+(forumtradetypes.size()==0 || forumtradetypes.containsValue(typeid) ? "checked" : "")+"> "+tradetypesmap.get(typeid)+"<br />");
				}
				request.setAttribute("tradetypeselect", tradetypeselect);
			}
			List<Typemodels> typemodels = typemodelService.findAll();
			Map typemodelids = new HashMap();
			Map typemodelsMaps =  dataParse.characterParse(forumfield.getTypemodels(), false);
			if (typemodelsMaps != null) {
				Iterator keys = typemodelsMaps.keySet().iterator();
				while (keys.hasNext()) {
					typemodelids.put(((Integer) keys.next()).shortValue(),"true");
				}
			}
			request.setAttribute("typemodels", typemodels);
			request.setAttribute("typemodelids", typemodelids);
			List<Map<String,String>> usergroups=dataBaseService.executeQuery("SELECT groupid,grouptitle FROM jrun_usergroups");
			Map viewpermMap = this.toMap(new HashMap<String,Boolean>(), forumfield.getViewperm().split("\t"));
			Map postpermMap = this.toMap(new HashMap<String,Boolean>(), forumfield.getPostperm().split("\t"));
			Map replypermMap = this.toMap(new HashMap<String,Boolean>(), forumfield.getReplyperm().split("\t"));
			Map getattachpermMap = this.toMap(new HashMap<String,Boolean>(), forumfield.getGetattachperm().split("\t"));
			Map postattachpermMap = this.toMap(new HashMap<String,Boolean>(), forumfield.getPostattachperm().split("\t"));
			request.setAttribute("usergroups", usergroups);
			request.setAttribute("viewpermMap", viewpermMap);
			request.setAttribute("postpermMap", postpermMap);
			request.setAttribute("replypermMap", replypermMap);
			request.setAttribute("getattachpermMap", getattachpermMap);
			request.setAttribute("postattachpermMap", postattachpermMap);
			List<Map<String,String>> accessList=dataBaseService.executeQuery("SELECT m.username, a.* FROM jrun_access a LEFT JOIN jrun_members m USING (uid) WHERE fid='"+fid+"'");
			if (accessList != null && accessList.size() > 0) {
				StringBuffer viewaccess = new StringBuffer();
				StringBuffer postaccess = new StringBuffer();
				StringBuffer replyaccess = new StringBuffer();
				StringBuffer getattachaccess = new StringBuffer();
				StringBuffer postattachaccess = new StringBuffer();
				for (Map<String,String> access : accessList) {
					String member = ", <a href=\"admincp.jsp?action=toaccess&memberid="+access.get("uid")+"\">"+access.get("username")+"</a>";
					if("1".equals(access.get("allowview"))){
						viewaccess.append(member);
					}
					if("1".equals(access.get("allowpost"))){
						postaccess.append(member);
					}
					if("1".equals(access.get("allowreply"))){
						replyaccess.append(member);
					}
					if("1".equals(access.get("allowgetattach"))){
						getattachaccess.append(member);
					}
					if("1".equals(access.get("allowpostattach"))){
						postattachaccess.append(member);
					}
				}
				request.setAttribute("viewaccess",viewaccess.length()>0?viewaccess.substring(2):null);
				request.setAttribute("postaccess",postaccess.length()>0?postaccess.substring(2):null);
				request.setAttribute("replyaccess",replyaccess.length()>0?replyaccess.substring(2):null);
				request.setAttribute("getattachaccess",getattachaccess.length()>0?getattachaccess.substring(2):null);
				request.setAttribute("postattachaccess",postattachaccess.length()>0?postattachaccess.substring(2):null);
			}
			Map forumlaperms =  dataParse.characterParse(forumfield.getFormulaperm(),false);
			List<Map<String,String>> forums=dataBaseService.executeQuery("SELECT fid, type, name, fup FROM jrun_forums WHERE fid<>'"+fid+"' AND type<>'sub' ORDER BY displayorder");
			if(forums!=null&&forums.size()>0){
				StringBuffer fupselect =new StringBuffer();
				for(Map<String,String> forum1:forums){
					if("group".equals(forum1.get("type"))){
						int forum1_fid=Integer.valueOf(forum1.get("fid"));
						fupselect.append("<option value='"+forum1_fid+"'"+(forum1_fid==forum.getFup()?" selected":"")+">"+forum1.get("name")+"</option>");
						for(Map<String,String> forum2:forums){
							if("forum".equals(forum2.get("type"))&&forum2.get("fup").equals(forum1.get("fid"))){
								int forum2_fid=Integer.valueOf(forum2.get("fid"));
								fupselect.append("<option value='"+forum2_fid+"'"+(forum2_fid==forum.getFup()?" selected":"")+">&nbsp; &gt; "+forum2.get("name")+"</option>");
							}
						}
					}
				}
				for(Map<String,String> forum0:forums){
					if("forum".equals(forum0.get("type"))&&forum0.get("fup").equals("0")){
						int forum0_fid=Integer.valueOf(forum0.get("fid"));
						fupselect.append("<option value='"+forum0_fid+"'"+(forum0_fid==forum.getFup()?" selected":"")+">"+forum0.get("name")+"</option>");
					}
				}
				request.setAttribute("fupselect", fupselect.toString());
			}
			request.setAttribute("forumlaperms", forumlaperms.get(0));
			request.setAttribute("forumfield", forumfield);
		}
		request.setAttribute("forum", forum);
		return mapping.findForward("toForumDetail");
	}
	public ActionForward projectadd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		short fid = Short.valueOf(request.getParameter("fid"));
		try{
			if(submitCheck(request, "addsubmit")){
				String projectid = request.getParameter("projectid");
				String coverwith = request.getParameter("coverwith");
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String[] fieldoption = request.getParameterValues("fieldoption");
				String[] fields = null;
				if ("all".equals(fieldoption[0])) {
					fields = new String[] { "styleid", "allowsmilies", "allowhtml",
							"allowbbcode", "allowimgcode", "allowmediacode",
							"allowanonymous", "allowshare", "allowpostspecial",
							"allowspecialonly", "alloweditrules", "recyclebin",
							"modnewposts", "jammer", "disablewatermark",
							"inheritedmod", "autoclose", "forumcolumns",
							"threadcaches", "allowpaytoauthor", "alloweditpost",
							"simple", "postcredits", "replycredits",
							"getattachcredits", "postattachcredits", "digestcredits",
							"attachextensions", "formulaperm", "viewperm", "postperm",
							"replyperm", "getattachperm", "postattachperm", "keywords", "modrecommend", "tradetypes",
							"typemodels" };
				} else {
					fields = fieldoption;
				}
				if (name == null || "".equals(name)) {
					request.setAttribute("message", getMessage(request, "project_no_title"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				Forums forum = forumService.findById(fid);
				Forumfields forumfields = forumfieldService.findById(fid);
				Map<String, Object> value = new TreeMap<String, Object>();
				value = this.getValues(forum, fields, value);
				value = this.getValues(forumfields, fields, value);
				if ("1".equals(coverwith)&&!"".equals(projectid)) {
					dataBaseService.runQuery("UPDATE jrun_projects SET name='"+ Common.addslashes((name.length() > 50 ? name.substring(0, 50) : name))+"', description='"+Common.addslashes(description)+"', value='"+dataParse.combinationChar(value)+"' WHERE id='"+projectid+"'",true);
				}else{
					dataBaseService.runQuery("INSERT INTO jrun_projects (name, type, description, value) VALUES ('"+ Common.addslashes((name.length() > 50 ? name.substring(0, 50) : name))+"', 'forum', '"+Common.addslashes(description)+"', '"+dataParse.combinationChar(value)+"')",true);
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		request.setAttribute("message", getMessage(request, "project_sava_succeed"));
		request.setAttribute("url_forward","admincp.jsp?action=forumdetail&fid=" + fid);
		return mapping.findForward("message");
	}
	public ActionForward forumcopy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String source = request.getParameter("source");
		try{
			if(submitCheck(request, "copysubmit")){
				String[] target = request.getParameterValues("target");
				StringBuffer fids=new StringBuffer();
				fids.append("0");
				if(target!=null){
					for(String fid:target){
						if(!fid.equals(source)){
							fids.append(","+fid);
						}
					}
				}
				if (fids.length()<=1) {
					request.setAttribute("message", getMessage(request, "a_forum_no_choose_copy_target_forum"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				Map<String,String> forumoptions=null;
				String[] options = request.getParameterValues("options");
				if (options == null) {
					request.setAttribute("message", getMessage(request, "a_forum_no_choose_copy_project"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}else{
					String forumopt=",modnewposts,recyclebin,allowshare,allowhtml,allowbbcode,allowimgcode,allowmediacode,allowsmilies,jammer,allowanonymous,disablewatermark,allowpostspecial,";
					String forumfieldopt=",postcredits,replycredits,password,viewperm,postperm,replyperm,getattachperm,postattachperm,formulaperm,threadtypes,attachextensions,modrecommend,tradetypes,";
					StringBuffer forumSb=new StringBuffer();
					StringBuffer forumfieldSb=new StringBuffer();
					for(String option:options){
						if(forumopt.contains(","+option+",")){
							forumSb.append(","+option);
						}else if(forumfieldopt.contains(","+option+",")){
							forumfieldSb.append(","+option);
						}
					}
					forumoptions=new HashMap<String,String>();
					forumoptions.put("jrun_forums", forumSb.length()>0?forumSb.substring(1):null);
					forumoptions.put("jrun_forumfields", forumfieldSb.length()>0?forumfieldSb.substring(1):null);
				}
				Iterator<Entry<String,String>> tables=forumoptions.entrySet().iterator();
				while(tables.hasNext()){
					Entry<String,String> e = tables.next();
					String table = e.getKey();
					String option=e.getValue();
					if(option!=null){
						Map<String,String> sourceforum=dataBaseService.executeQuery("SELECT "+option+" FROM "+table+" WHERE fid='"+source+"'").get(0);
						if(sourceforum!=null&&sourceforum.size()>0){
							StringBuffer updatequery=new StringBuffer();
							updatequery.append("fid=fid");
							Set<Entry<String,String>> keys =sourceforum.entrySet();
							for(Entry<String,String> temp:keys){
								String key = temp.getKey();
								updatequery.append(","+key+"='"+temp.getValue()+"'");
							}
							dataBaseService.runQuery("UPDATE "+table+" SET "+updatequery+" WHERE fid IN ("+fids+")",true);
						}else{
							request.setAttribute("message", getMessage(request, "a_forum_no_choose_copy_target_forum"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
				}
				Cache.updateCache("forums");
				request.setAttribute("message", getMessage(request, "a_forum_set_copy_s"));
				request.setAttribute("url_forward","admincp.jsp?action=forumsedit");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> sourceForum=dataBaseService.executeQuery("SELECT fid,name,type FROM jrun_forums WHERE fid='"+source+"'");
		if (sourceForum!=null&&sourceForum.size()>0&&!sourceForum.get(0).get("type").equals("group")) {
			HttpSession session=request.getSession();
			short groupid = (Short)session.getAttribute("jsprun_groupid");
			Members member = (Members)session.getAttribute("user");
			request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
			request.setAttribute("sourceForum", sourceForum.get(0));
			return mapping.findForward("toCopyForum");
		}else{
			request.setAttribute("message", getMessage(request, "a_forum_no_choose_copy_source_forum"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward removeForum(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		short fid = Short.valueOf(request.getParameter("fid"));
		Map<String,String> forumcount=dataBaseService.executeQuery("SELECT count(*) AS count FROM jrun_forums WHERE fup='"+fid+"'").get(0);
		if(forumcount!=null&&!"0".equals(forumcount.get("count"))){
			request.setAttribute("message",getMessage(request, "a_forum_down_forum_no_empty"));
			request.setAttribute("return",true);
			return mapping.findForward("message");
		}else {
			try{
				if(submitCheck(request, "confirmed")){
					List<Map<String,String>> threadList=dataBaseService.executeQuery("SELECT tid FROM jrun_threads WHERE fid='"+fid+"'");
					StringBuffer tids=new StringBuffer();
					tids.append("0");
					if(threadList!=null&&threadList.size()>0){
						for (Map<String, String> map : threadList) {
							tids.append(","+map.get("tid"));
						}
					}
					if(tids.length()>1)
					{
						List<Map<String,String>> attachmentList=dataBaseService.executeQuery("SELECT attachment, thumb, remote FROM jrun_attachments WHERE tid IN ("+tids+")");
						if(attachmentList!=null&&attachmentList.size()>0)
						{	
							Map<String,String> settings=ForumInit.settings;
							String filePath= JspRunConfig.realPath+settings.get("attachdir");
							for (Map<String, String> attachment : attachmentList) {
								Common.dunlink(attachment.get("attachment"), Byte.valueOf(attachment.get("thumb")), Byte.valueOf(attachment.get("remote")), filePath);
							}
						}
						String []tableNames={"threads", "threadsmod", "relatedthreads", "posts", "polls", "polloptions", "trades", "activities", "activityapplies", "debates", "debateposts", "videos", "attachments", "favorites", "mythreads", "myposts", "subscriptions", "typeoptionvars", "forumrecommend"};
						for (String tableName : tableNames) {
							dataBaseService.runQuery("DELETE FROM jrun_"+tableName+" WHERE tid IN ("+tids+")",true);
						}
					}
					dataBaseService.runQuery("DELETE FROM jrun_forums WHERE fid='"+fid+"'",true);
					dataBaseService.runQuery("DELETE FROM jrun_forumfields WHERE fid='"+fid+"'",true);
					dataBaseService.runQuery("DELETE FROM jrun_moderators WHERE fid='"+fid+"'",true);
					dataBaseService.runQuery("DELETE FROM jrun_access WHERE fid='"+fid+"'",true);
					Cache.updateCache("forums");
					request.setAttribute("message", getMessage(request, "a_forum_delete_s"));
					request.setAttribute("url_forward", "admincp.jsp?action=forumsedit");
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
			request.setAttribute("message",getMessage(request, "a_forum_delete_affirm"));
			request.setAttribute("msgtype","form");
			request.setAttribute("url_forward", request.getContextPath()+ "/forumsedit.do?action=removeForum&fid=" + fid);
			return mapping.findForward("message");
		}
	}
	public ActionForward moderators(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		short fid = Short.valueOf(request.getParameter("fid"));
		try{
			if(submitCheck(request, "modsubmit")){
				String newmoderator = request.getParameter("newmoderator");
				String newInheritedmod = request.getParameter("newInheritedmod");
				String[] delete= request.getParameterValues("delete");
				byte inheritedmodnew=0;
				if(newInheritedmod!=null){
					inheritedmodnew=Byte.valueOf(newInheritedmod);
				}
				Forums forum = forumService.findById(fid);
				if("group".equals(forum.getType())){
					inheritedmodnew=1;
				}
				else if("sub".equals(forum.getType())){
					inheritedmodnew=0;
				}
				List<Map<String,String>> moderators=dataBaseService.executeQuery("SELECT uid,displayorder FROM jrun_moderators where fid='"+fid+"'");
				if (moderators != null) {
					for (Map<String,String> moderator : moderators) {
						String value = request.getParameter("displayorder_"+moderator.get("uid"));
						if (value!=null&&!value.equals(moderator.get("displayorder"))) {
							dataBaseService.runQuery("UPDATE jrun_moderators SET displayorder='"+toDisPlayOrder(value)+"' WHERE fid='"+fid+"' AND uid='"+moderator.get("uid")+"'");
						}
					}
				}
				if(delete!=null||(newmoderator != null && !newmoderator.equals(""))||forum.getInheritedmod()!=inheritedmodnew){
					StringBuffer fidarray=new StringBuffer();
					fidarray.append("0");
					if("group".equals(forum.getType())){
						List<Map<String,String>> forumlist=dataBaseService.executeQuery("SELECT fid FROM jrun_forums WHERE type='forum' AND fup='"+fid+"'");
						if(forumlist!=null&&forumlist.size()>0){
							for (Map<String, String> map : forumlist) {
								fidarray.append(","+map.get("fid"));
							}
							List<Map<String,String>> sublist=dataBaseService.executeQuery("SELECT fid FROM jrun_forums WHERE type='sub' AND fup IN ("+fidarray+")");
							if(sublist!=null&&sublist.size()>0)
							{
								for (Map<String, String> map : sublist) {
									fidarray.append(","+map.get("fid"));
								}
							}
						}
					}
					else if("forum".equals(forum.getType()))
					{
						List<Map<String,String>> sublist=dataBaseService.executeQuery("SELECT fid FROM jrun_forums WHERE type='sub' AND fup='"+fid+"'");
						if(sublist!=null&&sublist.size()>0)
						{
							for (Map<String, String> map : sublist) {
								fidarray.append(","+map.get("fid"));
							}
						}
					}
					if(delete!=null){
						String deleteuids ="0";
						for (String uid : delete) {
							deleteuids+=","+uid;
							dataBaseService.runQuery("DELETE FROM jrun_moderators WHERE uid='"+uid+"' AND ((fid='"+fid+"' AND inherited='0') OR (fid IN ("+fidarray+") AND inherited='1'))",true);
						}
						Map<Short,Usergroups> usergroups=new HashMap<Short, Usergroups>();
						List<Usergroups> usergroupList=userGroupService.findAllGroups();
						if(usergroupList!=null&&usergroupList.size()>0){
							for (Usergroups usergroup : usergroupList) {
								usergroups.put(usergroup.getGroupid(), usergroup);
							}
						}
						StringBuffer excludeuids=new StringBuffer("0");
						List<Map<String,String>> moderatorslist=dataBaseService.executeQuery("SELECT uid FROM jrun_moderators WHERE uid IN ("+deleteuids+")");
						if(moderatorslist!=null&&moderatorslist.size()>0){
							for (Map<String, String> map : moderatorslist) {
								excludeuids.append(","+map.get("uid"));
							}
						}
						List<Map<String,String>> memberlist=dataBaseService.executeQuery("SELECT uid, groupid, credits FROM jrun_members WHERE uid IN ("+deleteuids+") AND uid NOT IN ("+excludeuids+") AND adminid NOT IN (1,2)");
						if(memberlist!=null&&memberlist.size()>0){
							for (Map<String, String> member : memberlist) {
								int adminidnew=0;
								int groupidnew=7;
								short groupid=Short.valueOf(member.get("groupid"));
								Usergroups usergroup=usergroups.get(groupid);
								if(usergroup.getType().equals("special")&&usergroup.getRadminid()!=3){
									adminidnew=-1;
									groupidnew=groupid;
								}
								else{
									Iterator<Entry<Short,Usergroups>> keys=usergroups.entrySet().iterator();
									while (keys.hasNext()) {
										Entry<Short,Usergroups> temp = keys.next();
										Short key = temp.getKey();
										Usergroups group=temp.getValue();
										Short credits=Short.valueOf(member.get("credits"));
										if(group.getType().equals("member")&&credits>=group.getCreditshigher()&&credits<group.getCreditslower())
										{
											groupidnew=key;
											break;
										}
									}
								}
								dataBaseService.runQuery("UPDATE jrun_members SET adminid='"+adminidnew+"', groupid='"+groupidnew+"' WHERE uid='"+member.get("uid")+"'",true);
							}
						}
					}
					StringBuffer newmodarray=new StringBuffer();
					newmodarray.append("0");
					StringBuffer origmodarray=new StringBuffer();
					origmodarray.append("0");
					if(forum.getInheritedmod()!=inheritedmodnew){
						List<Map<String,String>> moderatorlist=dataBaseService.executeQuery("SELECT uid FROM jrun_moderators WHERE fid='"+fid+"' AND inherited='0'");
						if(moderatorlist!=null&&moderatorlist.size()>0){
							for (Map<String, String> map : moderatorlist) {
								origmodarray.append(","+map.get("uid"));
								if(forum.getInheritedmod()==0&&inheritedmodnew>0){
									newmodarray.append(","+map.get("uid"));
								}
							}
							if(forum.getInheritedmod()>0&&inheritedmodnew==0)
							{
								dataBaseService.runQuery("DELETE FROM jrun_moderators WHERE uid IN ("+origmodarray+") AND fid IN ("+fidarray+") AND inherited='1'",true);
							}
						}
					}
					short newdisplayorder = toDisPlayOrder(request.getParameter("newdisplayorder"));
					if (!newmoderator.equals("")) {
						List<Map<String,String>> members=dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE username='"+Common.addslashes(newmoderator)+"'");
						if (members != null&&members.size()>0) {
							String uid=members.get(0).get("uid");
							newmodarray.append(","+uid);
							dataBaseService.runQuery("UPDATE jrun_members SET groupid='3' WHERE uid='"+uid+"' AND adminid NOT IN (1,2,3,4,5,6,7,8,-1)",true);
							dataBaseService.runQuery("UPDATE jrun_members SET adminid='3' WHERE uid='"+uid+"' AND adminid NOT IN (1,2)",true);
							dataBaseService.runQuery("REPLACE INTO jrun_moderators (uid, fid, displayorder, inherited) VALUES ('"+uid+"', '"+fid+"', '"+newdisplayorder+"', '0')",true);
						} else {
							request.setAttribute("message", getMessage(request, "a_member_edit_nonexistence"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
					String[] newmods=newmodarray.toString().split(",");
					for (String uid : newmods) {
						uid=uid.trim();
						if(!uid.equals("0"))
						{
							dataBaseService.runQuery("REPLACE INTO jrun_moderators (uid, fid, displayorder, inherited) VALUES ('"+uid+"', '"+fid+"', '"+newdisplayorder+"', '0')",true);
							if(inheritedmodnew>0)
							{
								String[] fids=fidarray.toString().split(",");
								for (String ifid : fids) {
									ifid=ifid.trim();
									if(!ifid.equals("0"))
									{
										dataBaseService.runQuery("REPLACE INTO jrun_moderators (uid, fid, inherited) VALUES ('"+uid+"', '"+ifid+"', '1')",true);
									}
								}
							}
						}
					}
					forum.setInheritedmod(inheritedmodnew);
					forumService.updateForum(forum);
				}
				List<Map<String,String>> moderatorList=dataBaseService.executeQuery("SELECT m.username FROM jrun_members m, jrun_moderators mo WHERE mo.fid='"+fid+"' AND mo.inherited='0' AND m.uid=mo.uid ORDER BY mo.displayorder");
				StringBuffer moderatorstr=new StringBuffer();
				if(moderatorList!=null&&moderatorList.size()>0)
				{
					for (Map<String, String> moderator : moderatorList) {
						moderatorstr.append("\t"+Common.addslashes(moderator.get("username")));
					}
				}
				dataBaseService.runQuery("UPDATE jrun_forumfields SET moderators='"+(moderatorstr.length()>0?moderatorstr.substring(1):"")+"' WHERE fid='"+fid+"'",true);
				request.setAttribute("message", getMessage(request, "a_forum_forumman_set_s"));
				request.setAttribute("url_forward","admincp.jsp?action=moderators&fid="+fid);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> forum=dataBaseService.executeQuery("SELECT fid,name,type FROM jrun_forums WHERE fid='"+fid+"'").get(0);
		List<Map<String,String>> moderators=dataBaseService.executeQuery("SELECT m.username, mo.* FROM jrun_members m, jrun_moderators mo WHERE mo.fid='"+fid+"' AND m.uid=mo.uid ORDER BY mo.inherited, mo.displayorder");
		request.setAttribute("moderators", moderators);
		request.setAttribute("forum", forum);
		return mapping.findForward("toModerators");
	}
	public ActionForward forumrules(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String fid = request.getParameter("fid");
		HttpSession session=request.getSession();
		byte adminid = (Byte)session.getAttribute("jsprun_adminid");
		int uid = (Integer)session.getAttribute("jsprun_uid");
		if(fid==null){
			List<Map<String,String>> forums=null;
			if(adminid==2){
				forums=dataBaseService.executeQuery("SELECT fid, name FROM jrun_forums WHERE alloweditrules>'0' AND type IN ('forum', 'sub')");
			}else{
				forums=dataBaseService.executeQuery("SELECT f.fid, f.name, m.uid FROM jrun_forums f LEFT JOIN jrun_moderators m ON m.uid='"+uid+"' AND m.fid=f.fid WHERE alloweditrules>'0' AND f.type IN ('forum', 'sub')");
			}
			StringBuffer options=new StringBuffer();
			if(forums!=null&&forums.size()>0){
				for(Map<String,String> forum:forums){
					if(forum.get("uid")!=null||adminid==2){
						options.append("<option value=\""+forum.get("fid")+"\">"+Common.strip_tags(forum.get("name"))+"</option>");
					}
				}
			}
			request.setAttribute("options", options);
			return mapping.findForward("toForumrules");
		}else{
			boolean access=false;
			if(adminid==2){
				access=true;
			}else if(adminid==3){
				List<Map<String,String>> moderatorss=dataBaseService.executeQuery("SELECT uid FROM jrun_moderators WHERE uid='"+uid+"' AND fid='"+fid+"'");
				if(moderatorss!=null&&moderatorss.size()>0){
					access=true;
				}
			}
			List<Map<String,String>> forums=dataBaseService.executeQuery("SELECT f.fid, f.name, f.alloweditrules, ff.rules FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING (fid) WHERE f.fid='"+fid+"' AND alloweditrules>'0' AND type IN ('forum', 'sub')");
			if(!access){
				request.setAttribute("message", getMessage(request, "a_forum_admin_not_allow_edit_forum"));
				return mapping.findForward("message");
			}
			if(forums==null||forums.size()==0){
				request.setAttribute("message", getMessage(request, "a_forum_admin_not_allow_edit_forum"));
				return mapping.findForward("message");
			}else{
				Map<String,String> forum=forums.get(0);
				try{
					if(submitCheck(request, "rulessubmit")){
						String rulesnew=request.getParameter("rulesnew");
						int alloweditrules=Integer.valueOf(forum.get("alloweditrules"));
						if(alloweditrules!=2){
							rulesnew = Common.htmlspecialchars(rulesnew);
						}
						dataBaseService.runQuery("UPDATE jrun_forumfields SET rules='"+Common.addslashes(rulesnew)+"' WHERE fid='"+fid+"'",true);
						request.setAttribute("message", getMessage(request, "a_forum_rule_s"));
						return mapping.findForward("message");
					}
				}catch (Exception e) {
					request.setAttribute("message",e.getMessage());
					return mapping.findForward("message");
				}
				request.setAttribute("forum",forum);
				return mapping.findForward("toForumrules");
			}
		}
	}
	@SuppressWarnings("unchecked")
	private Map<String,Boolean> toMap(Map<String,Boolean> map, String[] groupids) {
		if (groupids == null) {
			return null;
		}
		for (int i = 0; i < groupids.length; i++) {
			if (!"".equals(groupids[i])) {
				map.put(groupids[i], true);
			}
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	private String toString(String[] groupids) {
		StringBuffer sb = new StringBuffer("");
		if (groupids != null && groupids.length > 0) {
			sb.append("\t");
			for (int i = 0; i < groupids.length; i++) {
				if (!"".equals(groupids[i])) {
					sb.append(groupids[i] + "\t");
				}
			}
		}
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	private Map toMap(Map map, HttpServletRequest request, Integer key,
			String fieldName) {
		if (fieldName != null && !"".equals(fieldName)) {
			String value = request.getParameter(fieldName + "[" + key + "]");
			if (value != null && !"".equals(value)) {
				if (map == null) {
					map = new TreeMap();
				}
				map.put(key, Math.min(Common.intval(value), 99));
			}
		}
		return map;
	}
	private void copyModerator(short fid, short fup, byte inherited) {
		Map<String,String> upForum=dataBaseService.executeQuery("SELECT inheritedmod FROM jrun_forums WHERE fid='"+fup+"'").get(0);
		List<Map<String,String>> moderators=dataBaseService.executeQuery("SELECT uid, inherited FROM jrun_moderators WHERE fid='"+fup+"'");
		if (moderators != null&&moderators.size()>0) {
			StringBuffer sql=new StringBuffer();
			sql.append("REPLACE INTO jrun_moderators (uid, fid, inherited)VALUES");
			boolean flag=false;
			for (Map<String,String> moderator : moderators) {
				if ("1".equals(upForum.get("inheritedmod"))||"1".equals(moderator.get("inherited"))) {
					sql.append(" ('"+moderator.get("uid")+"', '"+fid+"', '1'),");
					flag=true;
				}
			}
			if(flag){
				sql.deleteCharAt(sql.length()-1);
			}
			dataBaseService.runQuery(sql.toString(),true);
		}
	}
	private String showforum(Map<String,String> forum,String type,String indexname,HttpServletRequest request)
	{
		return  "<li><a href=\""+("group".equals(type)?indexname+"?gid="+forum.get("fid") : "forumdisplay.jsp?fid="+forum.get("fid"))+"\" target=\"_blank\"><b>"+forum.get("name")+"</b><span class=\"smalltxt\">"+("1".equals(forum.get("status")) ?"" : " ("+getMessage(request, "hidden")+")")+"</span></a> - "+getMessage(request, "display_order")+": <input type=\"text\" name=\""+forum.get("fid")+"\" value=\""+forum.get("displayorder")+"\" size=\"1\"> - "+(!"sub".equals(type)?"<a href=\"admincp.jsp?action=forumadd&fupid="+forum.get("fid")+"\" title=\""+getMessage(request, "a_forum_add_comment")+"\">["+getMessage(request, "a_forum_add")+"]</a> " : "")+"<a href=\"admincp.jsp?action=forumdetail&fid="+forum.get("fid")+"\" title=\""+getMessage(request, "a_forum_edit_comment")+"\">["+getMessage(request, "edit")+"]</a> "+(!"group".equals(type)?"<a href=\"admincp.jsp?action=forumcopy&source="+forum.get("fid")+"\" title=\""+getMessage(request, "a_forum_copy_comment")+"\">["+getMessage(request, "a_forum_copy")+"]</a> " :"")+"<a href=\"admincp.jsp?action=forumdelete&fid="+forum.get("fid")+"\" title=\""+getMessage(request, "a_forum_delete_comment")+"\">["+getMessage(request, "delete")+"]</a> - <a href=\"admincp.jsp?action=moderators&fid="+forum.get("fid")+"\" title=\""+getMessage(request, "a_forum_moderators_comment")+"\">["+getMessage(request, "usergroups_system_3")+(!"".equals(forum.get("moderators"))?("1".equals(forum.get("inheritedmod"))?":&nbsp;<B>"+forum.get("moderators").replaceAll("\t", ",&nbsp;")+"</B>":":&nbsp;"+forum.get("moderators").replaceAll("\t", ",&nbsp;")):"")+"]</a><br /></li>";
	}
	private Map<String, Object> getValues(Object bean, String[] fields,Map<String, Object> fieldsMap) {
		try {
			Field[] beanFields = bean.getClass().getDeclaredFields();
			if (fieldsMap == null) {
				fieldsMap = new HashMap<String, Object>();
			}
			int fieldLength = fields.length;
			String paraName = null;
			String getMethod = null;
			for (int i = 0; i < fieldLength; i++) {
				paraName = fields[i];
				Method method = null;
				Object paraValue = null;
				int beanFieldLength = beanFields.length;
				for (int j = 0; j < beanFieldLength; j++) {
					if (paraName.equals(beanFields[j].getName())) {
						getMethod = "get"+ paraName.substring(0, 1).toUpperCase()+ paraName.substring(1, paraName.length());
						method = bean.getClass().getMethod(getMethod);
						paraValue = method.invoke(bean, new Object[0]);
					}
				}
				if (method != null) {
					if (paraValue instanceof Short) {
						paraValue = String.valueOf(paraValue);
					}
					fieldsMap.put(paraName, paraValue);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return fieldsMap;
	}
	@SuppressWarnings("unchecked")
	private Object setValues(Object bean, Map fieldsMap) {
		try {
			Field[] fields = bean.getClass().getDeclaredFields();
			String paraName = null;
			String paraValue = null;
			String setMethod = null;
			int fieldLength = fields.length;
			for (int i = 0; i < fieldLength; i++) {
				paraName = fields[i].getName();
				Object obj = fieldsMap.get(paraName);
				if (obj != null) {
					paraValue = obj.toString();
					if (paraValue != null && !"".equals(paraValue)) {
						setMethod = "set"+ paraName.substring(0, 1).toUpperCase()+ paraName.substring(1, paraName.length());
						Method method = bean.getClass().getMethod(setMethod,fields[i].getType());
						method.invoke(bean, Common.convert(paraValue, fields[i].getType()));
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	private Object setValues(Object bean, HttpServletRequest request) {
		try {
			Field[] fields = bean.getClass().getDeclaredFields();
			String paraName = null;
			String paraValue = null;
			String setMethod = null;
			int fieldLength = fields.length;
			for (int i = 0; i < fieldLength; i++) {
				paraName = fields[i].getName();
				Object obj = request.getParameter((paraName));
				if (obj != null) {
					paraValue = obj.toString();
					if (paraValue != null) {
						if ("forumcolumns".equals(paraName)) {
							paraValue = toForumColumns(paraValue).toString();
						}
						if ("displayorder".equals(paraName)) {
							paraValue = String.valueOf(toDisPlayOrder(paraValue));
						}
						if ("threadcaches".equals(paraName)) {
							paraValue = Common.range(Common.intval(paraValue), 100, 0)+"";
						}
						if ("formulaperm".equals(paraName)) {
							continue;
						}
						setMethod = "set"+ paraName.substring(0, 1).toUpperCase()+ paraName.substring(1, paraName.length());
						Method method = bean.getClass().getMethod(setMethod,fields[i].getType());
						method.invoke(bean, Common.convert(paraValue, fields[i].getType()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	@SuppressWarnings("unchecked")
	public ActionForward threadtypes(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		short special = (short)Common.toDigit(request.getParameter("special"));
		try{
			if(submitCheck(request, "typesubmit")){
				String[] delete = request.getParameterValues("delete");
				if (delete != null) {
					String deleteids=Common.implodeids(delete);
					dataBaseService.runQuery("DELETE FROM jrun_typeoptionvars WHERE typeid IN ("+deleteids+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_tradeoptionvars WHERE typeid IN ("+deleteids+")",true);
					dataBaseService.runQuery("DELETE FROM jrun_typevars WHERE typeid IN ("+deleteids+")",true);
					Map<String,String> resultInfo=dataBaseService.runQuery("DELETE FROM jrun_threadtypes WHERE typeid IN ("+deleteids+") AND special='"+special+"'");
					int rows=Common.toDigit(resultInfo.get("ok"));
					if(rows>0){
						dataBaseService.runQuery("UPDATE jrun_threads SET typeid='0' WHERE typeid IN ("+deleteids+")");
						for(String typeid:delete){
							String fidstr=request.getParameter("fids["+typeid+"]");
							if(fidstr!=null&&fidstr.length()>0){
								String[] fids=fidstr.split(",");
								for(String fid:fids){
									Forumfields forumfield = forumfieldService.findById(Short.valueOf(fid));
									String threadtypes = forumfield.getThreadtypes();
									if (!"".equals(threadtypes)) {
										Map map = dataParse.characterParse(threadtypes,false);
										if (map != null) {
											Map newMap = new HashMap();
											Iterator mapKeys = map.keySet().iterator();
											while (mapKeys.hasNext()) {
												Object mapKey = mapKeys.next();
												if (map.get(mapKey) instanceof Map) {
													Map mapValue = (Map) map.get(mapKey);
													Set keys = mapValue.keySet();
													Iterator iters = keys.iterator();
													while (iters.hasNext()) {
														Object key = iters.next();
														if (key.equals(Integer.valueOf(typeid))) {
															mapValue.remove(key);
															break;
														}
													}
													newMap.put(mapKey, mapValue);
												}else{
													newMap.put(mapKey, map.get(mapKey));
												}
											}
											threadtypes = dataParse.combinationChar(newMap);
											forumfield.setThreadtypes(threadtypes);
											forumfieldService.updateForumfield(forumfield);
										}
									}
								}
							}
						}
					}
				}
				List<Map<String,String>> threadtypes=dataBaseService.executeQuery("SELECT typeid FROM jrun_threadtypes WHERE special ='"+special+"'");
				if(threadtypes!=null&&threadtypes.size()>0){
					for(Map<String,String> threadtype:threadtypes){
						String typeid=threadtype.get("typeid");
						String name = request.getParameter("name["+ typeid + "]");
						if (name != null) {
							dataBaseService.runQuery("UPDATE jrun_threadtypes SET name='"+Common.addslashes(name.trim())+"', description='"+Common.addslashes(request.getParameter("description["+ typeid + "]"))+"', displayorder='"+toDisPlayOrder(request.getParameter("displayorder["+typeid + "]"))+"' WHERE typeid='"+typeid+"'");
						}
					}
				}
				String toURL = null;
				String newname = request.getParameter("newname");
				if (!"".equals(newname)) {
					List<Map<String,String>> type=dataBaseService.executeQuery("SELECT typeid FROM jrun_threadtypes WHERE name='"+Common.addslashes(newname)+"'");
					if (type != null && type.size() > 0) {
						request.setAttribute("message", getMessage(request, "a_forum_add_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					String newdisplayorder = request.getParameter("newdisplayorder");
					String newdescription = request.getParameter("newdescription");
					if (newname.length() > 255) {
						newname = newname.substring(0, 255);
					}
					Threadtypes threadtype = new Threadtypes();
					threadtype.setName(newname);
					threadtype.setDisplayorder(toDisPlayOrder(newdisplayorder));
					threadtype.setDescription(newdescription);
					threadtype.setSpecial(special);
					threadtype.setTemplate("");
					threadtypeService.addThreadtype(threadtype);
					toURL = "admincp.jsp?action=typedetail&typeid="+ threadtype.getTypeid();
				}
				if (special == 0) {
					toURL = "admincp.jsp?action=threadtypes";
				} else if ("".equals(newname)) {
					toURL = "admincp.jsp?action=threadtypes&special=1";
				}
				request.setAttribute("message", getMessage(request, "a_forum_add_threadtypes_s"));
				request.setAttribute("url_forward",toURL);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<Short,StringBuffer> displayForums=new HashMap<Short,StringBuffer>();
		Map<Short,StringBuffer> displayfids=new HashMap<Short,StringBuffer>();
		List<Map<String,String>> forums=dataBaseService.executeQuery("SELECT f.fid, f.name, ff.threadtypes FROM jrun_forums f , jrun_forumfields ff WHERE ff.threadtypes<>'' AND f.fid=ff.fid");
		if(forums!=null){
			for(Map<String,String> forum:forums){
				Map<Integer,String> types = (Map) dataParse.characterParse(forum.get("threadtypes"), false).get("types");
				if (types != null) {
					Set<Integer> typeids = types.keySet();
					for (Integer typeid : typeids) {
						StringBuffer displayForum=displayForums.get(typeid.shortValue());
						StringBuffer fid=displayfids.get(typeid.shortValue());
						if(displayForum==null){
							displayForum=new StringBuffer();
							fid=new StringBuffer();
							displayForums.put(typeid.shortValue(), displayForum);
							displayfids.put(typeid.shortValue(), fid);
						}else{
							displayForum.append(",&nbsp;");
							fid.append(",");
						}
						displayForum.append("<a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\" target=\"_blank\">"+forum.get("name")+"</a> [<a href=\"admincp.jsp?action=forumdetail&fid="+forum.get("fid")+"\">"+getMessage(request, "edit")+"</a>]");
						fid.append(forum.get("fid"));
					}
				}
			}
		}
		request.setAttribute("threadtypes",threadtypeService.findBySpecial(special));
		request.setAttribute("displayForums", displayForums);
		request.setAttribute("displayfids", displayfids);
		request.setAttribute("special", special);
		request.setAttribute("navlang", special>0?getMessage(request, "menu_threadtype_type"):getMessage(request, "menu_forum_threadtypes"));
		return mapping.findForward("toThreadtypes");
	}
	@SuppressWarnings("unchecked")
	public ActionForward typedetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		try{
			if(!submitCheck(request, "typedetailsubmit")&&!submitCheck(request, "typepreviewsubmit")){
				String operation = request.getParameter("operation");
				if (operation != null) {
					Common.setResponseHeader(response);
					try {
						PrintWriter out=response.getWriter();
						if ("classlist".equals(operation)) {
							List<Map<String,String>> typeoptions=dataBaseService.executeQuery("SELECT optionid, title FROM jrun_typeoptions WHERE classid='0' ORDER BY displayorder");
							if (typeoptions != null&&typeoptions.size()>0) {
								StringBuffer typestyle=new StringBuffer();
								for (Map<String,String> typeoption : typeoptions) {
									typestyle.append("<input class=\"button\" type=\"button\" value=\"");
									typestyle.append(typeoption.get("title"));
									typestyle.append("\" onclick=\"ajaxget('admincp.jsp?action=typedetail&operation=optionlist&typeid=");
									typestyle.append(request.getParameter("typeid"));
									typestyle.append("&classid=");
									typestyle.append(typeoption.get("optionid"));
									typestyle.append("', 'optionlist', 'optionlist', 'Loading...', '', checkedbox)\"> &nbsp;");
								}
								out.write(typestyle.toString());
								typestyle=null;
							}
						}
						else if ("optionlist".equals(operation)) {
							String classid = request.getParameter("classid");
							String  typeid = request.getParameter("typeid");
							List<Map<String,String>> typevars=dataBaseService.executeQuery("SELECT optionid FROM jrun_typevars WHERE typeid='"+typeid+"'");
							List<String> typeids=null;
							if(typevars!=null&&typevars.size()>0){
								typeids=new ArrayList<String>(typevars.size());
								for(Map<String,String> typevar:typevars){
									typeids.add(typevar.get("optionid"));
								}
							}else{
								 typeids=new ArrayList<String>();
							}
							List<Map<String,String>> typeoptions=dataBaseService.executeQuery("SELECT optionid,title FROM jrun_typeoptions WHERE classid='"+classid+"' ORDER BY displayorder");
							if (typeoptions != null&&typeoptions.size()>0) {
								for (Map<String,String> typeoption : typeoptions) {
									out.write("<input "+ (typeids.contains(typeoption.get("optionid"))?"checked":"")+ " class=\"checkbox\" type=\"checkbox\" name=\"typeselect\" value=\""+ typeoption.get("optionid")+ "\" onclick=\"insertoption(this.value);\">"+ typeoption.get("title")+ "&nbsp;&nbsp");
								}
							}
						}
						else if ("typelist".equals(operation)) {
							String optionid = request.getParameter("optionid");
							List<Map<String,String>> typeoptions=dataBaseService.executeQuery("SELECT optionid,title,identifier,type FROM jrun_typeoptions WHERE optionid='"+optionid+"' LIMIT 1");
							if (typeoptions != null) {
								Map<String,String> typeoption=typeoptions.get(0);
								out.write("<tr>");
								out.write("<td colspan='10' style='border: 0px; padding: 0px;'	id='optionid${typevar.id.optionid}'>");
								out.write("<TABLE width='100%' cellspacing='0' cellpadding='0'	style='margin:0px;'>");
								out.write("<tr align='center'>");
								out.write("<td class='altbg1' width='10%'><input class='checkbox' type='checkbox' name='delete' value='");
								out.write(typeoption.get("optionid"));
								out.write("'><input type='hidden' name='allselect' value='");
								out.write(typeoption.get("optionid"));
								out.write("'></td><td class='altbg2' width='10%'>");
								out.write(typeoption.get("title"));
								out.write("</td><td class='altbg1' width='15%'>");
								out.write(getMessage(request, "a_forum_threadtype_edit_"+typeoption.get("type")));
								out.write("</td><td class='altbg2' width='8%'><input class='checkbox' type='checkbox' name='available[");
								out.write(typeoption.get("optionid"));
								out.write("]' value='1' checked></td><td class='altbg1' width='8%'><input class='checkbox' type='checkbox' name='required[");
								out.write(typeoption.get("optionid"));
								out.write("]' value='1'></td><td class='altbg2' width='8%'><input class='checkbox' type='checkbox' name='unchangeable[");
								out.write(typeoption.get("optionid"));
								out.write("]' value='1'></td><td class='altbg1' width='8%'><input class='checkbox' type='checkbox' name='search[");
								out.write(typeoption.get("optionid"));
								out.write("]' value='1'></td><td class='altbg2' width='10%'><input type='text' size='2' name='displayorder[");
								out.write(typeoption.get("optionid"));
								out.write("]' value='0'></td><td class='altbg1' width='10%'><a href='###' onclick='insertvar(\"");
								out.write(typeoption.get("identifier"));
								out.write("\");doane(event);return false;'>["+getMessage(request, "a_forum_threadtype_infotypes_add_template")+"]</a></td><td class='altbg2' width='10%'><a href='admincp.jsp?action=optiondetail&optionid=");
								out.write(typeoption.get("optionid"));
								out.write("'>["+getMessage(request, "edit")+"]</a></td></tr></TABLE></td></tr>");
							}
						}
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				} else {
					short typeid = Short.valueOf(request.getParameter("typeid"));
					int modelid = Math.max(Common.intval(request.getParameter("modelid")), -1);
					Map<String,String> threadtype=dataBaseService.executeQuery("SELECT typeid, name, template, modelid, expiration FROM jrun_threadtypes WHERE typeid='"+typeid+"'").get(0);
					if(modelid>=0){
						threadtype.put("modelid", String.valueOf(modelid));
					}else{
						modelid=Integer.valueOf(threadtype.get("modelid"));
					}
					List<Map<String,String>> typemodels = dataBaseService.executeQuery("SELECT id, name, options, customoptions FROM jrun_typemodels ORDER BY displayorder");
					Map<String,String> existoption=new HashMap<String,String>();
					if(typemodels!=null&&typemodels.size()>0){
						StringBuffer typemodelopt=new StringBuffer();
						for (Map<String, String> typemodel : typemodels) {
							int id=Integer.valueOf(typemodel.get("id"));
							if(id==modelid){
								String customoption=typemodel.get("customoptions").trim();
								if(customoption.length()>0){
									String[] customoptions= customoption.split("\t");
									for (String str : customoptions) {
										existoption.put(str, "0");
									}
								}
								String option=typemodel.get("options").trim();
								if(option.length()>0){
									String[] options= option.split("\t");
									for (String str : options) {
										existoption.put(str, "1");
									}
								}
							}
							typemodelopt.append("<option value=\""+id+"\" "+(id==modelid? "selected" : "")+">"+typemodel.get("name")+"</option>");
						}
						request.setAttribute("typemodelopt", typemodelopt);
					}
					Map<String,String> optiontitle=new HashMap<String,String>();
					List<Map<String,String>> showoptions=dataBaseService.executeQuery("SELECT t.optionid, t.displayorder, t.available, t.required, t.unchangeable, t.search, tt.title, tt.type, tt.identifier FROM jrun_typevars t, jrun_typeoptions tt WHERE t.typeid='"+typeid+"' AND t.optionid=tt.optionid ORDER BY t.displayorder");
					if(typemodels!=null&&typemodels.size()>0){
						StringBuffer jsoptionids=new StringBuffer();
						for (Map<String, String> option : showoptions) {
							jsoptionids.append("optionids.push("+option.get("optionid")+");\r\n");
							optiontitle.put(option.get("identifier"), option.get("title"));
						}
						request.setAttribute("jsoptionids", jsoptionids);
					}
					if(existoption.size()>0) {
						StringBuffer optionids=new StringBuffer();
						Set<String> keys=existoption.keySet();
						for (String optionid : keys) {
							optionids.append(","+optionid);
						}
						List<Map<String,String>> typeoptions=dataBaseService.executeQuery("SELECT optionid,title,description,identifier,type FROM jrun_typeoptions WHERE optionid IN ("+optionids.substring(1)+")");
						for (Map<String, String> option : typeoptions) {
							String optionid=option.get("optionid");
							option.put("required", existoption.get(optionid));
							option.put("available", "1");
							option.put("unchangeable", "0");
							option.put("model", "1");
							boolean flag=true;
							for (Map<String, String> showoption : showoptions) {
								if(optionid.equals(showoption.get("optionid"))){
									showoption.putAll(option);
									flag=false;
									break;
								}
							}
							if(flag){
								option.put("displayorder", "0");
								showoptions.add(option);
							}
						}
					}
					String previewtemplate=threadtype.get("template");
					if(previewtemplate.length()>0) {
						Set<String> keys=optiontitle.keySet();
						for (String key : keys) {
							previewtemplate=previewtemplate.replaceAll("\\{"+key+"\\}", optiontitle.get(key));
							previewtemplate=previewtemplate.replaceAll("\\["+key+"value\\]", optiontitle.get(key)+getMessage(request, "value"));
						}
						previewtemplate=previewtemplate.replaceAll("\\[|\\]", "!");
						request.setAttribute("previewtemplate",previewtemplate);
					}
					request.setAttribute("threadtype", threadtype);
					request.setAttribute("typeoptions", showoptions);
					request.setAttribute("types", getTypeMap(request));
					return mapping.findForward("toTypedetail");
				}
			}else{
				short typeid = (short)Common.toDigit(request.getParameter("typeid"));
				short modelid=(short)Common.toDigit(request.getParameter("modelid"));
				String expiration = request.getParameter("expiration");
				String typetemplate = request.getParameter("typetemplate");
				dataBaseService.runQuery("UPDATE jrun_threadtypes SET special='1', modelid='"+modelid+"', template='"+Common.addslashes(typetemplate)+"', expiration='"+expiration+"' WHERE typeid='"+typeid+"'",true);
				if(submitCheck(request, "typedetailsubmit")){
					Typemodels typemodel = null;
					if (modelid!=0) {
						typemodel = typemodelService.findById(modelid);
					}
					List<String> optionids = new ArrayList<String>();
					String[] typeselect = request.getParameterValues("allselect");
					if (typeselect != null) {
						for (String obj : typeselect) {
							optionids.add(obj);
						}
					}
					if (typemodel != null) {
						String[] options = typemodel.getCustomoptions().split("\t");
						for (String obj : options) {
							if (!"".equals(obj.trim())) {
								optionids.add(obj);
							}
						}
						options = typemodel.getOptions().split("\t");
						for (String obj : options) {
							if (!"".equals(obj)) {
								optionids.add(obj);
							}
						}
					}
					String[] delete = request.getParameterValues("delete");
					if (delete != null) {
						String ids=Common.implodeids(delete);
						dataBaseService.runQuery("DELETE FROM jrun_typevars WHERE typeid='"+typeid+"' AND optionid IN ("+ids+")",true);
						for (String obj : delete) {
							optionids.remove(obj);
						}
					}
					int size = optionids.size();
					if (delete!=null||size>0) {
						for (int i = 0; i < size; i++) {
							String available = request.getParameter("available["+ optionids.get(i) + "]");
							String required = request.getParameter("required["+ optionids.get(i) + "]");
							String unchangeable = request.getParameter("unchangeable["+ optionids.get(i) + "]");
							String search = request.getParameter("search["+ optionids.get(i) + "]");
							String displayorder = request.getParameter("displayorder["+ optionids.get(i) + "]");
							Typevars typevar = null;
							TypevarsId typevarsId = null;
							typevar = typevarService.findByTO(typeid, Short.valueOf(optionids.get(i)));
							if (typemodel != null) {
								if (isModelOption(optionids.get(i), typemodel, true)) {
									available = "1";
									required = "0";
								}
								if (isModelOption(optionids.get(i), typemodel, false)) {
									available = "1";
									required = "1";
								}
							}
							if (available == null) {
								available = "0";
							}
							if (required == null) {
								required = "0";
							}
							if (unchangeable == null) {
								unchangeable = "0";
							}
							if (search == null) {
								search = "0";
							}
							if (typevar != null) {
								typevarsId = typevar.getId();
								typevarService.removeTypevar(typevar);
							} else {
								typevar = new Typevars();
								typevarsId = new TypevarsId();
								typevarsId.setTypeid(typeid);
								typevarsId.setOptionid(Short.valueOf(optionids.get(i)));
							}
							typevarsId.setAvailable(Byte.valueOf(available));
							typevarsId.setRequired(Byte.valueOf(required));
							typevarsId.setUnchangeable(Byte.valueOf(unchangeable));
							typevarsId.setSearch(Byte.valueOf(search));
							typevarsId.setDisplayorder(toDisPlayOrder(displayorder));
							typevar.setId(typevarsId);
							if (typevar != null) {
								typevarService.updateTypevar(typevar);
							} else {
								typevarService.addTypevar(typevar);
							}
						}
						try {
							String result=Cache.updateCache("style");
							if(result!=null){
								request.setAttribute("message", getMessage(request, "a_forum_fenlei_s_css_f"));
								return mapping.findForward("message");
							}
						} catch (Exception e) {
							request.setAttribute("message", e.getMessage());
							return mapping.findForward("message");
						}
						request.setAttribute("message", getMessage(request, "a_forum_fenlei_s"));
						request.setAttribute("url_forward","admincp.jsp?action=typedetail&typeid="+typeid);
						return mapping.findForward("message");
					} else{
						request.setAttribute("message", getMessage(request, "a_forum_fenlei_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
				} else if(submitCheck(request, "typepreviewsubmit")){
					try {
						response.sendRedirect("admincp.jsp?action=typedetail&typeid="+typeid+"#template");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}else{
					return null;
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
	}
	public ActionForward typemodel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "modelsubmit")){
				String[] delete = request.getParameterValues("delete");
				if (delete != null) {
					String ids=Common.implodeids(delete);
					dataBaseService.runQuery("DELETE FROM jrun_typemodels WHERE id IN ("+ids+")");
				}
				List<Map<String,String>> typemodels=dataBaseService.executeQuery("SELECT id FROM jrun_typemodels ORDER BY displayorder");
				if (typemodels != null) {
					for (Map<String,String> typemodel : typemodels) {
						String name=request.getParameter("name[" + typemodel.get("id")+ "]");
						if (name.length() > 20) {
							name = name.substring(0, 20);
						}
						dataBaseService.runQuery("UPDATE jrun_typemodels SET displayorder='"+toDisPlayOrder(request.getParameter("displayorder[" + typemodel.get("id")+ "]"))+"', name='"+Common.addslashes(name)+"' WHERE id='"+typemodel.get("id")+"'",true);
					}
				}
				String newname = request.getParameter("newname");
				if (!"".equals(newname)) {
					String newdisplayorder = request.getParameter("newdisplayorder");
					if (newname.length() > 20) {
						newname = newname.substring(0, 20);
					}
					dataBaseService.runQuery("INSERT INTO jrun_typemodels (name, displayorder, type,options,customoptions) VALUES ('"+Common.addslashes(newname)+"', '"+toDisPlayOrder(newdisplayorder)+"', '0','','')",true);
				}
				request.setAttribute("message", getMessage(request, "a_forum_fenlei_update"));
				request.setAttribute("url_forward","admincp.jsp?action=typemodel");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> typemodels=dataBaseService.executeQuery("SELECT id,name,displayorder,type FROM jrun_typemodels ORDER BY displayorder");
		request.setAttribute("typemodels", typemodels);
		return mapping.findForward("toTypemodel");
	}
	public ActionForward modeldetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		short modelid = (short)Common.toDigit(request.getParameter("modelid"));
		try{
			if(submitCheck(request, "modeldetailsubmit")){
				String newname = request.getParameter("newname");
				String[] customoptions = request.getParameterValues("customoptions");
				StringBuffer customoption = new StringBuffer();
				if (customoptions != null) {
					int length=customoptions.length;
					for (int i = 0; i < length; i++) {
						customoption.append("\t"+customoptions[i]);
					}
				}
				if (newname.length() > 20) {
					newname = newname.substring(0, 20);
				}
				dataBaseService.runQuery("UPDATE jrun_typemodels SET name='"+Common.addslashes(newname)+"', customoptions='"+(customoption.length()>0?customoption.substring(1):"")+"' WHERE id='"+modelid+"'",true);
				request.setAttribute("message", getMessage(request, "a_forum_fenlei_update"));
				request.setAttribute("url_forward","admincp.jsp?action=modeldetail&modelid="+modelid);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> typemodels=dataBaseService.executeQuery("SELECT id,name,type,options,customoptions FROM jrun_typemodels WHERE id='"+modelid+"'");
		if(typemodels!=null&&typemodels.size()>0){
			Map<String,String> typemodel =typemodels.get(0);
			List<Map<String,String>> classoptions=dataBaseService.executeQuery("SELECT optionid,title FROM jrun_typeoptions WHERE classid!='0' ORDER BY displayorder,optionid");
			List<Map<String,String>> customoptions=dataBaseService.executeQuery("SELECT optionid,title FROM jrun_typeoptions WHERE optionid IN ("+Common.implodeids(typemodel.get("customoptions").split("\t"))+")");
			if("1".equals(typemodel.get("type"))){
				List<Map<String,String>> sysoptions=dataBaseService.executeQuery("SELECT optionid,title FROM jrun_typeoptions WHERE optionid IN ("+Common.implodeids(typemodel.get("options").split("\t"))+")");
				request.setAttribute("sysoptions", sysoptions);
			}
			request.setAttribute("classoptions", classoptions);
			request.setAttribute("customoptions", customoptions);
			request.setAttribute("typemodel", typemodel);
			return mapping.findForward("toModeldetail");
		}else{
			request.setAttribute("message", getMessage(request, "undefined_action"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
	}
	public ActionForward typeoption(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String classid = request.getParameter("classid");
		try{
			if(submitCheck(request, "typeoptionsubmit")){
				String newtitle = request.getParameter("newtitle");
				String[] delete = request.getParameterValues("delete");
				if (delete != null) {
					dataBaseService.runQuery("DELETE FROM jrun_typeoptions WHERE optionid IN ("+Common.implodeids(delete)+")",true);
				}
				List<Map<String,String>> typeoptions=dataBaseService.executeQuery("SELECT optionid FROM jrun_typeoptions WHERE classid='"+classid+"'");
				if (typeoptions != null) {
					for (Map<String,String> typeoption : typeoptions) {
						String title = request.getParameter("title["+ typeoption.get("optionid") + "]");
						if (title != null) {
							dataBaseService.runQuery("UPDATE jrun_typeoptions SET displayorder='"+toDisPlayOrder(request.getParameter("displayorder["+  typeoption.get("optionid") + "]"))+"', title='"+Common.addslashes(title)+"' WHERE optionid='"+typeoption.get("optionid")+"'",true);
						}
					}
				}
				if (!"".equals(newtitle)) {
					String newidentifier = request.getParameter("newidentifier");
					if ("".equals(newidentifier)) {
						request.setAttribute("message", getMessage(request, "a_forum_fenlei_name_empty"));
						request.setAttribute("url_forward","admincp.jsp?action=typeoption&classid="+ classid);
						return mapping.findForward("message");
					}
					List<Map<String,String>> typeoption=dataBaseService.executeQuery("SELECT optionid FROM jrun_typeoptions WHERE identifier='"+Common.addslashes(newidentifier)+"' LIMIT 1");
					if (typeoption!= null&&typeoption.size()>0) {
						request.setAttribute("message", getMessage(request, "a_forum_fenlei_name_double"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if (!newidentifier.matches("^([a-zA-Z])+\\w*$")) {
						request.setAttribute("message", getMessage(request, "a_forum_fenlei_invalid_info"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					String newdisplayorder = request.getParameter("newdisplayorder");
					String newtype = request.getParameter("newtype");
					if (newtitle.length() > 100) {
						newtitle = newtitle.substring(0, 100);
					}
					if (newidentifier.length() > 40) {
						newidentifier = newidentifier.substring(0, 40);
					}
					dataBaseService.runQuery("INSERT INTO jrun_typeoptions (classid, displayorder, title,description, identifier, type,rules) VALUES ('"+classid+"', '"+toDisPlayOrder(newdisplayorder)+"','"+Common.addslashes(newtitle)+"','', '"+Common.addslashes(newidentifier)+"', '"+newtype+"','')",true);
				}
				request.setAttribute("message", getMessage(request, "a_forum_fenlei_s"));
				request.setAttribute("url_forward","admincp.jsp?action=typeoption&classid="+classid);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> upTypeoptions= dataBaseService.executeQuery("SELECT optionid,title FROM jrun_typeoptions WHERE classid=0 ORDER BY displayorder");
		if(classid==null){
			if(upTypeoptions!=null&&upTypeoptions.size()>0){
				classid=upTypeoptions.get(0).get("optionid");
			}
		}
		List<Typeoptions> typeoptions = typeoptionService.findByClassId(Short.valueOf(classid));
		request.setAttribute("upTypeoptions",upTypeoptions);
		request.setAttribute("typeoptions", typeoptions);
		request.setAttribute("classid", classid);
		request.setAttribute("types", getTypeMap(request));
		return mapping.findForward("toTypeoption");
	}
	@SuppressWarnings("unchecked")
	public ActionForward optiondetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String optionid = request.getParameter("optionid");
		try{
			if(submitCheck(request, "editsubmit")){
				String title = request.getParameter("title").trim();
				String identifier = request.getParameter("identifier").trim();
				String description = request.getParameter("description").trim();
				if ("".equals(title) || "".equals(identifier)) {
					request.setAttribute("message", getMessage(request, "a_forum_fenlei_name_empty"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (!identifier.matches("^([a-zA-Z])+\\w*$")) {
					request.setAttribute("message", getMessage(request, "a_forum_fenlei_invalid_info"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				List<Map<String,String>> typeoptions=dataBaseService.executeQuery("SELECT optionid FROM jrun_typeoptions WHERE identifier='"+identifier+"' AND optionid!='"+optionid+"' LIMIT 1");
				if (typeoptions != null && typeoptions.size()>0) {
					request.setAttribute("message", getMessage(request, "a_forum_fenlei_name_double"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				String type = request.getParameter("type");
				if (title.length() > 100) {
					title = title.substring(0, 100);
				}
				if (identifier.length() > 40) {
					identifier = identifier.substring(0, 40);
				}
				String[] properties = new String[] { "maxnum", "minnum", "maxlength","choices", "maxwidth", "maxheight" };
				Map<Object, Object> rulesMap = new HashMap<Object, Object>();
				for (String obj : properties) {
					Object value = request.getParameter("rules[" + type + "][" + obj+ "]");
					if (value != null) {
						rulesMap.put(obj, value);
					}
				}
				dataBaseService.runQuery("UPDATE jrun_typeoptions SET title='"+Common.addslashes(title)+"', description='"+Common.addslashes(description)+"', identifier='"+Common.addslashes(identifier)+"', type='"+type+"', rules='"+dataParse.combinationChar(rulesMap)+"' WHERE optionid='"+optionid+"'",true);
				request.setAttribute("message", getMessage(request, "a_forum_fenlei_update_s"));
				request.setAttribute("url_forward","admincp.jsp?action=optiondetail&optionid="+optionid);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Typeoptions typeoption = typeoptionService.findById(Short.valueOf(optionid));
		if(typeoption==null){
			request.setAttribute("message", getMessage(request, "undefined_action"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		Map rulesMap = dataParse.characterParse(typeoption.getRules(),false);
		request.setAttribute("typeoption", typeoption);
		request.setAttribute("rulesMap", rulesMap);
		return mapping.findForward("toOptiondetail");
	}
	private boolean isModelOption(String optionid, Typemodels typemodel,boolean isCustom) {
		String[] optionids = null;
		if (isCustom) {
			optionids = typemodel.getCustomoptions().split("\t");
		} else {
			optionids = typemodel.getOptions().split("\t");
		}
		if (!Common.empty(optionids)) {
			int length = optionids.length;
			for (int i = 0; i < length; i++) {
				if (optionids[i].equals(optionid)) {
					return true;
				}
			}
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public ActionForward styles(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String export = request.getParameter("export");
		if(export!=null){
			List<Map<String,String>> styles=dataBaseService.executeQuery("SELECT s.name, s.templateid, t.name AS tplname, t.directory, t.copyright FROM jrun_styles s LEFT JOIN jrun_templates t ON t.templateid=s.templateid WHERE styleid='"+export+"'");
			if(styles==null||styles.size()==0){
				request.setAttribute("message", getMessage(request, "a_forum_style_no_exist"));
				return mapping.findForward("message");
			}
			Map style=styles.get(0);
			style.put("version",JspRunConfig.VERSION);
			Map<String,String> stylevarMap=new HashMap<String, String>();
			List<Map<String,String>> stylevars=dataBaseService.executeQuery("SELECT variable,substitute FROM jrun_stylevars WHERE styleid='"+export+"'");
			if(stylevars!=null&&stylevars.size()>0){
				for (Map<String,String> stylevar : stylevars) {
					stylevarMap.put(stylevar.get("variable"),stylevar.get("substitute"));
				}
			}
			style.put("style", stylevarMap);
			exportData(request, response, "JspRun_style_"+Common.encodeText(request, (String)style.get("name"))+".txt", "Style Dump", style);
			return null;
		}else{
			Map<String,String> settings=ForumInit.settings;
			String stylesubmit=request.getParameter("stylesubmit");
			String importsubmit=request.getParameter("importsubmit");
			String edit = request.getParameter("edit");
			if(stylesubmit==null&&importsubmit==null&&edit==null){
				List<Map<String,String>> styles=dataBaseService.executeQuery("SELECT s.styleid, s.available, s.name, t.name AS tplname, t.copyright FROM jrun_styles s LEFT JOIN jrun_templates t ON t.templateid=s.templateid");
				request.setAttribute("styleid", settings.get("styleid"));
				request.setAttribute("forumStyles", styles);
				return mapping.findForward("toStyles");
			}
			String successInfo =null;
			String requestPath = null;
			try{
				if(submitCheck(request, "stylesubmit")||submitCheck(request, "importsubmit")){
					if(stylesubmit!=null){
						String updatecsscache = request.getParameter("updatecsscache");
						if("1".equals(updatecsscache)){
							try {
								String result=Cache.updateCache("style");
								if(result==null){
									ForumInit.initServletContext(servlet.getServletContext());
									successInfo = getMessage(request, "a_forum_css_updated");
								}else{
									request.setAttribute("message", getMessage(request, "a_forum_css_update_f"));
									return mapping.findForward("message");
								}
							} catch (Exception e) {
								request.setAttribute("message", e.getMessage());
								return mapping.findForward("message");
							}
						}else{
							String[] delete = request.getParameterValues("delete");
							if (delete != null) {
								String ids=Common.implodeids(delete);
								dataBaseService.runQuery("DELETE FROM jrun_styles WHERE styleid IN ("+ids+")",true);
								dataBaseService.runQuery("DELETE FROM jrun_stylevars WHERE styleid IN ("+ids+")",true);
								dataBaseService.runQuery("UPDATE jrun_members SET styleid='0' WHERE styleid IN ("+ids+")",true);
								dataBaseService.runQuery("UPDATE jrun_forums SET styleid='0' WHERE styleid IN ("+ids+")",true);
								dataBaseService.runQuery("UPDATE jrun_sessions SET styleid='"+settings.get("styleid")+"' WHERE styleid IN ("+ids+")",true);
							}
							List<Map<String,String>> styles=dataBaseService.executeQuery("SELECT styleid FROM jrun_styles");
							if (styles != null) {
								for (Map<String,String> style : styles) {
									String name = request.getParameter("name["+ style.get("styleid") + "]");
									if (name != null) {
										dataBaseService.runQuery("UPDATE jrun_styles SET name='"+Common.addslashes((name.length() > 20 ? name.substring(0, 20) : name))+"', available='"+Common.range(Common.intval(request.getParameter("available["+ style.get("styleid") + "]")), 1, 0)+"' WHERE styleid='"+style.get("styleid")+"'", true);
									}
								}
							}
							String newname = request.getParameter("newname");
							if (newname != null && !"".equals(newname)) {
								String[] predefinedvars = {"available", "bgcolor", "altbg1", "altbg2", "link", "bordercolor", "headercolor", "headertext", "catcolor",
										"tabletext", "text", "borderwidth", "tablespace", "fontsize", "msgfontsize", "msgbigsize", "msgsmallsize",
										"font", "smfontsize", "smfont", "boardimg", "imgdir","maintablewidth", "stypeid", "bgborder",
										"catborder", "inputborder", "lighttext", "headermenu", "headermenutext", "framebgcolor",
										"noticebg", "commonboxborder", "tablebg", "highlightlink", "commonboxbg", "boxspace", "portalboxbgcode",
										"noticeborder", "noticetext"};
								Styles style = new Styles();
								style.setName((newname.length() > 20 ? newname.substring(0, 20): newname));
								style.setAvailable(Byte.valueOf("1"));
								style.setTemplateid(Short.valueOf("1"));
								styleService.addStyle(style);
								StringBuffer sql=new StringBuffer();
								sql.append("INSERT INTO jrun_stylevars (styleid, variable,substitute) VALUES ");
								for (String variable : predefinedvars) {
									sql.append("('"+style.getStyleid()+"', '"+variable+"',''),");
								}
								sql.deleteCharAt(sql.length()-1);
								dataBaseService.runQuery(sql.toString(),true);
							}
						}
					}
					else if(importsubmit!=null){
						String ignoreversion=request.getParameter("ignoreversion");
						String styledata=request.getParameter("styledata");
						styledata=styledata.replaceAll("(#.*\\s+)*", "");
						Map styleMap=dataParse.characterParse(Base64.decode(styledata, JspRunConfig.CHARSET),false);
						if(styleMap==null||styleMap.size()==0){
							request.setAttribute("message", getMessage(request, "a_system_styles_import_data_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}else if(ignoreversion==null&&!styleMap.get("version").toString().equals(JspRunConfig.VERSION)){
							request.setAttribute("message", getMessage(request, "a_forum_interface_version_invalid",String.valueOf(styleMap.get("version")),JspRunConfig.VERSION));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						boolean renamed=false;
						short templateid=(short)Common.toDigit((String)styleMap.get("templateid"));
						if(templateid!=1){
							String directory=styleMap.get("directory").toString();
							String templatedir=JspRunConfig.realPath+directory;
							File file=new File(templatedir);
							if(!file.isDirectory()){
								if(!file.mkdir()){
									String basedir=directory;
									request.setAttribute("message",  getMessage(request, "a_forum_stencil_dir_invalid",basedir,directory));
									request.setAttribute("return", true);
									return mapping.findForward("message");
								}
							}
							String tplname= styleMap.get("tplname").toString();
							Map<String,String> count=dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_templates WHERE name='"+tplname+"'").get(0);
							if(count!=null&&Common.toDigit(count.get("count"))>0){
								styleMap.put("tplname", tplname+"_"+Common.getRandStr(4,false));
								renamed=true;
							}
							Templates template=new Templates();
							template.setName(styleMap.get("tplname").toString());
							template.setDirectory(directory);
							template.setCopyright(styleMap.get("copyright").toString());
							if(!templateService.addTemplate(template)){
								request.setAttribute("message", getMessage(request, "a_system_styles_import_data_invalid"));
								request.setAttribute("return", true);
								return mapping.findForward("message");
							}
							templateid=template.getTemplateid();
						}else{
							templateid=1;
						}
						String name=styleMap.get("name").toString();
						Map<String,String> count=dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_styles WHERE name='"+Common.addslashes(name)+"'").get(0);
						if(count!=null&&Common.toDigit(count.get("count"))>0){
							styleMap.put("name", name+"_"+Common.getRandStr(4,false));
							renamed=true;
						}
						Styles style=new Styles();
						style.setName(styleMap.get("name").toString());
						style.setTemplateid(templateid);
						if(!styleService.addStyle(style))
						{
							request.setAttribute("message", getMessage(request, "a_system_styles_import_data_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						short styleidnew=style.getStyleid();
						Map<String,String> stylevarMap=(Map<String,String>)styleMap.get("style");
						if(stylevarMap!=null&&stylevarMap.size()>0){
							StringBuffer sql=new StringBuffer();
							sql.append("INSERT INTO jrun_stylevars (styleid, variable,substitute) VALUES ");
							Iterator<Entry<String,String>> variables=stylevarMap.entrySet().iterator();
							while (variables.hasNext()) {
								Entry<String,String> temp = variables.next();
								sql.append("('"+styleidnew+"', '"+temp.getKey()+"','"+Common.addslashes(temp.getValue())+"'),");
							}
							sql.deleteCharAt(sql.length()-1);
							dataBaseService.runQuery(sql.toString(),true);
						}
						Cache.updateCache("style");
						if(renamed){
							successInfo=getMessage(request, "a_forum_stencil_import_s");
						}
						else{
							successInfo=getMessage(request, "a_forum_stencil_import_s1");
						}
					}
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
			if(edit != null){
				try{
					if(submitCheck(request, "editsubmit")){
						String name = request.getParameter("name");
						String templateid =request.getParameter("templateid");
						dataBaseService.runQuery("UPDATE jrun_styles SET name='"+Common.addslashes((name.length() > 20 ? name.substring(0, 20) : name))+"', templateid='"+templateid+"' WHERE styleid='"+edit+"'",true);
						String[] deletes = request.getParameterValues("delete");
						if (deletes != null) {
							dataBaseService.runQuery("DELETE FROM jrun_stylevars WHERE stylevarid IN ("+Common.implodeids(deletes)+") AND styleid='"+edit+"'",true);
						}
						List<Map<String,String>> stylevars=dataBaseService.executeQuery("SELECT stylevarid FROM jrun_stylevars WHERE styleid='"+edit+"'");
						if (stylevars != null&&stylevars.size()>0) {
							for (Map<String,String> stylevar : stylevars) {
								String substitute = request.getParameter("stylevar["+ stylevar.get("stylevarid") + "]");
								if (substitute == null) {
									substitute = "";
								}
								dataBaseService.runQuery("UPDATE jrun_stylevars SET substitute='"+Common.addslashes(substitute)+"' WHERE stylevarid='"+stylevar.get("stylevarid")+"' AND styleid='"+edit+"'",true);
							}
						}
						String newvariable = request.getParameter("newvariable");
						String newsubstitute = request.getParameter("newsubstitute");
						if (!"".equals(newvariable) && !"".equals(newsubstitute)) {
							if(!newvariable.matches("^([a-zA-Z])+\\w*$")){
								request.setAttribute("message", getMessage(request, "a_forum_add_replace_name_invalid"));
								request.setAttribute("return", true);
								return mapping.findForward("message");
							}
							Map<String,String> count=dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_stylevars WHERE variable='"+newvariable+"' AND styleid='"+edit+"'").get(0);
							if(!"0".equals(count.get("count"))){
								request.setAttribute("message", getMessage(request, "a_forum_add_replace_name_invalid2"));
								request.setAttribute("return", true);
								return mapping.findForward("message");
							}
							dataBaseService.runQuery("INSERT INTO jrun_stylevars (styleid, variable, substitute) VALUES ('"+edit+"', '"+newvariable.toLowerCase()+"', '"+Common.addslashes(newsubstitute)+"')",true);
							requestPath = "admincp.jsp?action=styles&edit=" + edit;
						}
					}
				}catch (Exception e) {
					request.setAttribute("message",e.getMessage());
					return mapping.findForward("message");
				}
				List<Map<String,String>> styles=dataBaseService.executeQuery("SELECT * FROM jrun_styles WHERE styleid='"+edit+"'");
				if(styles==null||styles.size()==0){
					request.setAttribute("message", getMessage(request, "undefined_action"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				Map<String,String> style=styles.get(0);
				List<Map<String,String>> stylevars=dataBaseService.executeQuery("SELECT * FROM jrun_stylevars WHERE styleid='"+edit+"'");
				if (stylevars != null) {
					Map<String, String> stylevaridsMap = new HashMap<String, String>();
					Map<String, String> stylevarsubsMap = new HashMap<String, String>();
					StringBuffer customstylevars=new StringBuffer();
					String variables=",AVAILABLE,COMMONBOXBORDER,NOTICEBG,TABLEBG,HIGHLIGHTLINK,COMMONBOXBG,BGCOLOR,ALTBG1,ALTBG2,LINK,BORDERCOLOR,HEADERCOLOR,HEADERTEXT,TABLETEXT,TEXT,CATCOLOR,BORDERWIDTH,FONTSIZE,TABLESPACE,MSGFONTSIZE,MSGBIGSIZE,MSGSMALLSIZE,FONT,SMFONTSIZE,SMFONT,BGBORDER,MAINTABLEWIDTH,IMGDIR,BOARDIMG,INPUTBORDER,CATBORDER,LIGHTTEXT,FRAMEBGCOLOR,HEADERMENU,HEADERMENUTEXT,BOXSPACE,PORTALBOXBGCODE,NOTICEBORDER,NOTICETEXT,STYPEID,";
					for (Map<String,String> stylevar : stylevars) {
						String variable = stylevar.get("variable").toUpperCase();
						if (variables.contains(","+variable+",")) {
							String value=stylevar.get("substitute");
							stylevaridsMap.put(variable, stylevar.get("stylevarid"));
							stylevarsubsMap.put(variable, value);
						} else {
							customstylevars.append("<tr align='center'><td class='altbg1'><input class='checkbox' type='checkbox' name='delete' value='"+stylevar.get("stylevarid")+"'></td><td class='altbg2'><b>{"+variable+"}</b></td><td class='altbg1'><textarea name='stylevar["+stylevar.get("stylevarid")+"]' cols='50' rows='2'>"+stylevar.get("substitute")+"</textarea></td></tr>");
						}
					}
					Map<String, String> subsMap = new HashMap<String, String>();
					String imgdir=stylevarsubsMap.get("IMGDIR");
					Set<Entry<String, String>> keys=stylevarsubsMap.entrySet();
					for (Entry<String, String> temp : keys) {
						String value=temp.getValue();
						String[] codes=value.split(" ");
						StringBuffer background=new StringBuffer();
						for (String code : codes) {
							if(code.length()>0){
								if(code.startsWith("#")){
									background.append(code.toUpperCase()+" ");
								}else if(code.matches("^http:\\/\\/.*")){
									background.append("url(\'"+code+"\'");
								}else{
									background.append("url(\'"+imgdir+"/"+code+"\')");
								}
							}
						}
						subsMap.put(temp.getKey(),background.toString());
					}
					request.setAttribute("stylevaridsMap", stylevaridsMap);
					request.setAttribute("stylevarsubsMap", stylevarsubsMap);
					request.setAttribute("subsMap", subsMap);
					request.setAttribute("customstylevars", customstylevars);
				}
				List<Map<String,String>> templates=dataBaseService.executeQuery("SELECT templateid, name FROM jrun_templates");
				List<Map<String,String>> imagetypes=dataBaseService.executeQuery("SELECT typeid, name FROM jrun_imagetypes where type='smiley' ORDER BY displayorder");
				request.setAttribute("style", style);
				request.setAttribute("templates", templates);
				request.setAttribute("imagetypes", imagetypes);
				return mapping.findForward("toStyledetail");
			}
			request.setAttribute("message",  successInfo!=null?successInfo:getMessage(request, "a_forum_interface_project_s"));
			request.setAttribute("url_forward",requestPath!=null?requestPath:"admincp.jsp?action=styles");
			return mapping.findForward("message");
		}
	}
	public ActionForward templates(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String realPath = JspRunConfig.realPath;
		String edit = request.getParameter("edit");
		if (edit != null) {
			List<Map<String,String>> templates=dataBaseService.executeQuery("SELECT templateid,name,directory FROM jrun_templates WHERE templateid='"+edit+"'");
			if(templates==null||templates.size()==0){
				request.setAttribute("message", getMessage(request, "undefined_action"));
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Map<String,String> template=templates.get(0);
			String directory=template.get("directory");
			File dirFile=new File(realPath + directory);
			if(!dirFile.isDirectory()){
				request.setAttribute("message", getMessage(request, "a_forum_stencil_dir_no_exist1",directory));
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			String keyword = request.getParameter("keyword");
			if (keyword == null) {
				keyword = "";
			}
			Map<String, String> languageFiles = new TreeMap<String, String>();
			Map<String, Map<String, String>> jspFiles = new TreeMap<String, Map<String, String>>();
			Map<String, String> colors = new HashMap<String, String>();
			File[] files = dirFile.listFiles();
			if (files != null) {
				File defaultFile =null;
				String defaultdirectory=realPath+ "./templates/default/";
				for (File file : files) {
					String fileName = file.getName();
					String type = fileName.substring(fileName.lastIndexOf(".") + 1);
					if ("properties".equals(type.toLowerCase())) {
						languageFiles.put(fileName.substring(0, fileName.indexOf(".")), fileName);
					}else if ("jsp".equals(type.toLowerCase())&& this.searchByKeyword(file, keyword)) {
						String prefix = null;
						String prefixName = fileName.substring(0, fileName.indexOf("."));
						int index = fileName.indexOf("_");
						if (index > 0) {
							prefix = fileName.substring(0, index);
						} else {
							prefix = prefixName;
						}
						Map<String, String> htmFile = jspFiles.get(prefix);
						if (htmFile == null) {
							htmFile = new TreeMap<String, String>();
							jspFiles.put(prefix, htmFile);
						}
						htmFile.put(prefixName, fileName);
						defaultFile = new File(defaultdirectory + fileName);
						if (defaultFile.exists()) {
							if (this.compareFile(file, defaultFile)) {
								colors.put(prefixName, "#000000");
							} else {
								colors.put(prefixName, "#FF0000");
							}
						} else {
							colors.put(prefixName, "#00FF00");
						}
					}
				}
			}
			request.setAttribute("template",template);
			request.setAttribute("languageFiles", languageFiles);
			request.setAttribute("jspFiles", jspFiles);
			request.setAttribute("colors", colors);
			request.setAttribute("keyword", keyword);
			return mapping.findForward("toTemplatedetail");
		}else{
			try{
				if(submitCheck(request, "tplsubmit")){
					List<Templates> templates = templateService.findAll();
					if (templates != null) {
						for (Templates template : templates) {
							String name = request.getParameter("name["+ template.getTemplateid() + "]");
							if (name != null) {
								String directory = request.getParameter("directory["+ template.getTemplateid() + "]");
								if (!new File(realPath + directory).isDirectory()) {
									request.setAttribute("message", getMessage(request, "a_forum_stencil_dir_no_exist1",directory));
									request.setAttribute("return", true);
									return mapping.findForward("message");
								}else if(template.getTemplateid()==1&&!"./templates/default".equals(directory)){
									request.setAttribute("message", getMessage(request, "a_forum_not_to_update_default_stencil"));
									request.setAttribute("return", true);
									return mapping.findForward("message");
								}
								template.setName(name);
								template.setDirectory(directory);
								templateService.updateTemplate(template);
							}
						}
					}
					String newname = request.getParameter("newname").trim();
					if (!"".equals(newname)) {
						String newdirectory = request.getParameter("newdirectory").trim();
						String newcopyright = request.getParameter("newcopyright").trim();
						if("".equals(newdirectory)){
							request.setAttribute("message", getMessage(request, "a_forum_template_dir_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}else if (!new File(realPath + newdirectory).isDirectory()) {
							request.setAttribute("message", getMessage(request, "a_forum_stencil_dir_no_exist1",newdirectory));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						newname = newname.length() > 20 ? newname.substring(0, 20): newname;
						dataBaseService.runQuery("INSERT INTO jrun_templates (name, directory, copyright) VALUES ('"+Common.addslashes(newname)+"', '"+Common.addslashes(newdirectory)+"', '"+Common.addslashes(newcopyright)+"')",true);
					}
					String[] delete = request.getParameterValues("delete");
					if (delete != null) {
						String ids=Common.implodeids(delete);
						dataBaseService.runQuery("DELETE FROM jrun_templates WHERE templateid IN ("+ids+") AND templateid<>'1'");
						dataBaseService.runQuery("UPDATE jrun_styles SET templateid='1' WHERE templateid IN ("+ids+")");
					}
					Cache.updateCache("style");
					request.setAttribute("message", getMessage(request, "a_forum_template_update_s"));
					request.setAttribute("url_forward","admincp.jsp?action=templates");
					return mapping.findForward("message");
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
			List<Templates> templates = templateService.findAll();
			request.setAttribute("templates", templates);
			return mapping.findForward("toTemplates");
		}
	}
	public ActionForward tpladd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String edit = request.getParameter("edit");
		String name = request.getParameter("name");
		Templates template = templateService.findById(Short.valueOf(edit));
		File file = new File(JspRunConfig.realPath + template.getDirectory() + "/" + name + ".jsp");
		if (file.exists()) {
			request.setAttribute("message", getMessage(request, "a_forum_template_add_exist_name"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		String message=null;
		try {
			if (!file.createNewFile()) {
				message= getMessage(request, "a_forum_write_error",template.getDirectory() + "/" + name);
			} else {
				message= getMessage(request, "a_forum_template_add_s");
				request.setAttribute("url_forward", "admincp.jsp?action=tpledit&templateid=" + edit + "&fn=" + file.getName());
			}
		} catch (IOException e) {
			message=e.getMessage();
			request.setAttribute("return", true);
		}
		request.setAttribute("message", message);
		return mapping.findForward("message");
	}
	public ActionForward toTpledit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String templateid = request.getParameter("templateid");
		String fn = request.getParameter("fn");
		if ("yes".equals(request.getParameter("delete"))) {
			request.setAttribute("msgtype", "form");
			request.setAttribute("message", getMessage(request, "a_forum_template_delete_affirm",fn));
			request.setAttribute("url_forward",request.getContextPath()+ "/forumsedit.do?action=tpledit&delete=yes&templateid="+ templateid + "&fn=" + fn);
			return mapping.findForward("message");
		}
		List<Map<String,String>> template=dataBaseService.executeQuery("SELECT templateid,name,directory FROM jrun_templates WHERE templateid='"+templateid+"'");
		if(template==null||template.size()==0){
			request.setAttribute("message", getMessage(request, "a_forum_template_no_exist"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		Map<String,String> tpl=template.get(0);
		String keyword = request.getParameter("keyword");
		if (keyword == null) {
			keyword = "";
		}if ("yes".equals(request.getParameter("reset"))) {
			request.setAttribute("msgtype", "form");
			request.setAttribute("message", getMessage(request, "a_forum_template_comeback_default",tpl.get("directory") + "/" + fn));
			request.setAttribute("url_forward",request.getContextPath()	+ "/forumsedit.do?action=tpledit&reset=yes&templateid="+ templateid + "&fn=" + fn + "&keyword=" + keyword);
			return mapping.findForward("message");
		}
		File file = new File(JspRunConfig.realPath + tpl.get("directory") + "/" + fn);
		if(!file.exists()){
			request.setAttribute("message", getMessage(request, "a_forum_write_error",tpl.get("directory")+"/"+fn));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		boolean isLang = false;
		if ("properties".equals(fn.substring(fn.lastIndexOf(".") + 1))) {
			isLang = true;
			Map<String, Map<Object, String>> langs = new TreeMap<String, Map<Object, String>>();
			FileReader fr=null;
			BufferedReader br = null;
			try {
				langs.put("actioncode", new TreeMap<Object, String>());
				langs.put("language", new TreeMap<Object, String>());
				String langKey = "";
				fr=new FileReader(file);
				br = new BufferedReader(fr);
				String line = br.readLine();
				while (line != null) {
					if (line.matches("^\\s*\\$.*")) {
						String key = line.substring(line.indexOf("$") + 1);
						int index = key.indexOf(" ");
						if (index != -1) {
							key = key.substring(0, index);
						}
						langs.put(key, new TreeMap<Object, String>());
						langKey = key;
					} else if (line.matches(".*=>.*")) {
						String key = line.substring(0, line.indexOf("=>")).trim();
						String value = line.substring(line.indexOf("=>")).trim();
						value = value.substring(value.indexOf("'") + 1, value.lastIndexOf("'"));
						Map<Object, String> map = langs.get(langKey);
						int index = key.indexOf("'");
						if (map == null) {
							map = new TreeMap<Object, String>();
						}
						if (index != -1) {
							key = key.substring(index + 1, key.lastIndexOf("'"));
							map.put(key, value);
						}
						else{
							map.put(Integer.valueOf(key), value);
						}
						langs.put(langKey, map);
					}
					line = br.readLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			request.setAttribute("langs", langs);
		} else {
			FileInputStream fis=null;
			InputStreamReader isr=null;
			BufferedReader br = null;
			StringBuffer content = new StringBuffer();
			try {
				fis=new FileInputStream(file);
				isr=new InputStreamReader(fis,JspRunConfig.CHARSET);
				br = new BufferedReader(isr);
				while (br.ready()){
					content.append(br.readLine()+"\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
					isr.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			request.setAttribute("nosame", request.getParameter("nosame"));
			request.setAttribute("content", content.toString().replaceAll("</textarea>", "&lt;/textarea&gt;"));
		}
		List<Map<String,String>> templates=dataBaseService.executeQuery("SELECT templateid, directory FROM jrun_templates WHERE templateid!='"+templateid+"' GROUP BY directory");
		request.setAttribute("keyword", keyword);
		request.setAttribute("template", tpl);
		request.setAttribute("templates", templates);
		request.setAttribute("fn", fn);
		request.setAttribute("isLang", isLang);
		HttpSession session=request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		String timeformat=(String)session.getAttribute("timeformat");
		String dateformat=(String)session.getAttribute("dateformat");
		request.setAttribute("filemtime", Common.gmdate(dateformat+" "+timeformat, (int)(file.lastModified()/1000), timeoffset));
		return mapping.findForward("toTpledit");
	}
	public ActionForward tpledit(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String templateid = request.getParameter("templateid");
		List<Map<String,String>> srctpl=dataBaseService.executeQuery("SELECT directory FROM jrun_templates WHERE templateid='"+templateid+"'");
		if(srctpl==null||srctpl.size()==0){
			request.setAttribute("message", getMessage(request, "a_forum_template_no_exist"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		String realPath = JspRunConfig.realPath;
		String fn = request.getParameter("fn");
		String keyword = request.getParameter("keyword");
		if (keyword == null) {
			keyword = "";
		}
		String directory=srctpl.get(0).get("directory");
		File file = new File(realPath + directory + "/" + fn);
		String hasconfirmed = request.getParameter("hasconfirmed");
		try{
			if(submitCheck(request, "confirmed")){
				if (file.exists()) {
					String delete = request.getParameter("delete");
					if ("yes".equals(delete)) {
						if (file.delete()) {
							request.setAttribute("message", getMessage(request, "a_forum_template_delete_s"));
							request.setAttribute("url_forward","admincp.jsp?action=templates&edit="+ templateid);
							return mapping.findForward("message");
						} else {
							request.setAttribute("message", getMessage(request, "a_forum_write_error",directory + "/" + fn));
							return mapping.findForward("message");
						}
					}
					String reset = request.getParameter("reset");
					if ("yes".equals(reset)) {
						File sourceFile = new File(realPath + "templates/default/"+ fn);
						if (this.copeFile(sourceFile, file)) {
							request.setAttribute("message", getMessage(request, "a_forum_template_comeback_s"));
							request.setAttribute("url_forward","admincp.jsp?action=templates&edit="+ templateid + "&keyword=" + keyword);
							return mapping.findForward("message");
						} else {
							request.setAttribute("message", getMessage(request, "a_forum_write_error",directory + "/" + fn));
							return mapping.findForward("message");
						}
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		try{
			if(submitCheck(request, "editsubmit")){
				String isLang = request.getParameter("isLang");
				if ("true".equals(isLang)) {
					FileReader fr=null;
					BufferedReader br = null;
					FileWriter fw=null;
					BufferedWriter bw = null;
					File dest = null;
					try {
						String langKey = "";
						String path = file.getPath();
						String type = path.substring(path.lastIndexOf("."));
						path = path.substring(0, path.lastIndexOf("\\")) + "temp"+ type;
						dest = new File(path);
						file.renameTo(dest);
						fr = new FileReader(dest);
						br = new BufferedReader(fr);
						fw=new FileWriter(new File(realPath+ directory + "/" + fn));
						bw = new BufferedWriter(fw);
						String line = br.readLine();
						while (line != null) {
							if (line.matches("^\\s*\\$.*")) {
								String key = line.substring(line.indexOf("$") + 1);
								int index = key.indexOf(" ");
								if (index != -1) {
									key = key.substring(0, index);
								}
								langKey = key;
							}
							if (line.matches(".*=>.*")) {
								String key = line.substring(0, line.indexOf("=>")).trim();
								int index = key.indexOf("'");
								if (index != -1) {
									key = key.substring(index + 1, key.lastIndexOf("'"));
								}
								String newvalue = request.getParameter("lang["+ langKey + "][" + key + "]");
								if (!key.matches("^\\d+$")) {
									key = "'" + key + "'";
								}
								bw.write("\t" + key + " => '" + newvalue + "',\n");
							} else {
								bw.write(line);
							}
							line = br.readLine();
						}
						bw.flush();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							br.close();
							fr.close();
							fw.close();
							bw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					dest.delete();
				} else {
					String content = request.getParameter("content");
					FileOutputStream fos=null;
					OutputStreamWriter osw=null;
					BufferedWriter bw = null;
					try {
						fos=new FileOutputStream(file);;
						osw=new OutputStreamWriter(fos,JspRunConfig.CHARSET);
						bw = new BufferedWriter(osw);
						bw.write(content);
						bw.flush();
						osw.flush();
						fos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							bw.close();
							osw.close();
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					content=null;
				}
				request.setAttribute("message", getMessage(request, "a_forum_template_edit_s"));
				request.setAttribute("url_forward","admincp.jsp?action=templates&edit="+ templateid + "&keyword=" + keyword);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String copyto = request.getParameter("copyto");
		if (copyto != null) {
			String copytoid = request.getParameter("copytoid");
			if (!"".equals(copytoid)) {
				List<Map<String,String>> desctpl=dataBaseService.executeQuery("SELECT directory FROM jrun_templates WHERE templateid='"+copytoid+"'");
				if(desctpl==null||desctpl.size()==0){
					request.setAttribute("message", getMessage(request, "a_forum_template_no_exist"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				String descdirectory=desctpl.get(0).get("directory");
				File descDir = new File(realPath + descdirectory);
				if(!descDir.isDirectory()){
					request.setAttribute("message", getMessage(request, "a_forum_stencil_dir_no_exist1",descdirectory));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				File newfilename = new File(realPath + descdirectory+ "/" + fn);
				if (hasconfirmed == null && newfilename.exists()) {
					request.setAttribute("msgtype", "form");
					request.setAttribute("message", getMessage(request, "a_forum_target_file_exist",descdirectory + "/" + fn));
					request.setAttribute("url_forward",request.getContextPath()	+ "/forumsedit.do?action=tpledit&copyto=yes&templateid="+ templateid + "&fn=" + fn+ "&copytoid=" + copytoid+ "&keyword=" + keyword);
					return mapping.findForward("message");
				}
				if (this.copeFile(file, newfilename)) {
					request.setAttribute("message", getMessage(request, "a_forum_template_copy_s",fn,descdirectory));
					request.setAttribute("url_forward","admincp.jsp?action=tpledit&templateid="+ templateid + "&fn=" + fn + "&keyword=" + keyword);
					return mapping.findForward("message");
				} else {
					request.setAttribute("message", getMessage(request, "a_forum_write_error",directory + "/" + fn));
					return mapping.findForward("message");
				}
			}
		}
		request.setAttribute("message", getMessage(request, "undefined_action"));
		request.setAttribute("return", true);
		return mapping.findForward("message");
	}
	private boolean compareFile(File file1, File file2) {
		if (file1 == null || file2 == null || !file1.exists()|| !file2.exists()) {
			return false;
		}
		if (file1.getPath().equals(file2.getPath())) {
			return true;
		}
		FileReader fr1=null;
		FileReader fr2=null;
		BufferedReader br1 = null;
		BufferedReader br2 = null;
		try {
			fr1=new FileReader(file1);
			fr2=new FileReader(file2);
			br1 = new BufferedReader(fr1);
			br2 = new BufferedReader(fr2);
			StringBuffer content1=new StringBuffer();
			StringBuffer content2=new StringBuffer();
			while (br1.ready() && br2.ready()) {
				content1.replace(0, content1.length(), br1.readLine());
				content2.replace(0, content2.length(), br2.readLine());
				if (!content1.toString().equals(content2.toString())) {
					return false;
				}
			}
			if(br1.ready()||br2.ready()){
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br1.close();
				fr1.close();
				br2.close();
				fr2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	private boolean copeFile(File sourceFile, File targetFile) {
		if (sourceFile == null || !sourceFile.exists() || targetFile == null) {
			return false;
		}
		if (sourceFile.getPath().equals(targetFile.getPath())) {
			return true;
		}
		FileInputStream fis=null;
		InputStreamReader fsr=null;
		BufferedReader br=null;
		FileOutputStream fos=null;
		OutputStreamWriter osw=null;
		BufferedWriter bw = null;
		try {
			fis=new FileInputStream(sourceFile);
			fsr=new InputStreamReader(fis,JspRunConfig.CHARSET);
			br=new BufferedReader(fsr);
			fos=new FileOutputStream(targetFile);;
			osw=new OutputStreamWriter(fos,JspRunConfig.CHARSET);
			bw = new BufferedWriter(osw);
			while(br.ready()){
				bw.write(br.readLine());
				bw.newLine();
			}
			bw.flush();
			osw.flush();
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				bw.close();
				osw.close();
				fos.close();
				br.close();
				fsr.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	private boolean searchByKeyword(File file, String keyword) {
		if (file == null || !file.exists()) {
			return false;
		}
		if (keyword == null || "".equals(keyword)) {
			return true;
		}
		FileReader fr=null;
		BufferedReader br = null;
		try {
			fr=new FileReader(file);
			br = new BufferedReader(fr);
			StringBuffer sb=new StringBuffer();
			while (br.ready()){
				sb.replace(0, sb.length(),br.readLine());
				if (sb.length()>0 && sb.indexOf(keyword) != -1) {
					br.close();
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	private short toDisPlayOrder(String value) {
		return (short)Common.range(Common.intval(value), 127, -128);
	}
	private Short toForumColumns(String value) {
		return (short)Common.range(Common.intval(value),255, 0);
	}
	private Map<String,String> getTypeMap(HttpServletRequest request){
		Map<String, String> types = new HashMap<String, String>();
		types.put("number", getMessage(request, "a_forum_threadtype_edit_number"));
		types.put("text", getMessage(request, "a_forum_threadtype_edit_text"));
		types.put("radio", getMessage(request, "a_forum_threadtype_edit_radio"));
		types.put("checkbox", getMessage(request, "a_forum_threadtype_edit_checkbox"));
		types.put("textarea", getMessage(request, "a_forum_threadtype_edit_textarea"));
		types.put("select", getMessage(request, "a_forum_threadtype_edit_select"));
		types.put("calendar", getMessage(request, "a_forum_threadtype_edit_calendar"));
		types.put("email", getMessage(request, "a_forum_threadtype_edit_email"));
		types.put("url", getMessage(request, "a_forum_threadtype_edit_url"));
		types.put("image", getMessage(request, "a_forum_threadtype_edit_image"));
		return types;
	}
}