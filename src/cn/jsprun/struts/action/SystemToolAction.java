package cn.jsprun.struts.action;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Settings;
import cn.jsprun.domain.Threadtypes;
import cn.jsprun.utils.Base64;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.vo.JsTypeVo;
public class SystemToolAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward jsinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings = ForumInit.settings;
		String []categorys = {"jsthreads","jsforums","jsmemberrank","jsstats","jsimages","jscustom"};
		String []types = {getMessage(request, "a_system_js_threads"),getMessage(request, "a_system_js_forums"),getMessage(request, "a_system_js_memberrank"),getMessage(request, "stats"),getMessage(request, "a_system_js_images"),getMessage(request, "custom")};
		String jsstatus = settings.get("jsstatus");
		request.setAttribute("status", jsstatus);
		if(jsstatus != null && jsstatus.equals("1")){
			List<Settings> list = systemToolServer.findSettingsLikeVariable("jswizard_");
			List<JsTypeVo> targetList = new ArrayList<JsTypeVo>();
			if (list != null) {
				for (Settings setting : list) {
					String variable = setting.getVariable();
					String identifier = variable.replaceFirst("jswizard_", "");
					Map nowMap = dataParse.characterParse(setting.getValue(),false);
					int type = (Integer) nowMap.get("type");
					JsTypeVo jtype = new JsTypeVo();
					jtype.setCategory(categorys[type]);
					jtype.setType(types[type]);
					jtype.setIdentifier(identifier);
					targetList.add(jtype);
				}
			}
			list = null;
			request.setAttribute("settinglist", targetList);
			return mapping.findForward("javascript");
		}else{
			return mapping.findForward("jssetting");
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward gojsSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings = ForumInit.settings;
		String jsstatus = settings.get("jsstatus");
		if (jsstatus != null && jsstatus.equals("1")) {
			String cacle = settings.get("jscachelife");
			String format = settings.get("jsdateformat");
			String refdomain = settings.get("jsrefdomains");
			request.setAttribute("cacle", cacle);
			request.setAttribute("format", format);
			request.setAttribute("refdomain", refdomain);
		}
		settings = null;
		request.setAttribute("status", jsstatus);
		return mapping.findForward("jssetting");
	}
	@SuppressWarnings("unchecked")
	public ActionForward jsSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String jsstatus = request.getParameter("jsstatus");
				Map<String,String> settings = ForumInit.settings;
				if (jsstatus.equals("0")) {
					dataBaseService.runQuery("update jrun_settings set value='0' where variable='jsstatus'");
					settings.put("jsstatus", "0");
					request.setAttribute("message", getMessage(request, "a_system_jr_update_s"));
					request.setAttribute("url_forward", request.getHeader("referer"));
					return mapping.findForward("message");
				} else {
					String jscachelife = request.getParameter("jscachelife");
					int jscachelifeint = Common.toDigit(jscachelife);
					String jsdateformat = request.getParameter("jsdateformat");
					String refdomain = request.getParameter("jsrefdomains");
					if(Common.empty(jsdateformat)){
						try{
						SimpleDateFormat df = new SimpleDateFormat(jsdateformat);
						df.parse(Common.time()+"000");
						df = null;
						}catch(Exception e){
						}
						dataBaseService.runQuery("update jrun_settings set value='"+Common.addslashes(jsdateformat)+"' where variable='jsdateformat'");
						settings.put("jsdateformat", jsdateformat);
					}
					dataBaseService.runQuery("update jrun_settings set value='1' where variable='jsstatus'");
					dataBaseService.runQuery("update jrun_settings set value='"+jscachelifeint+"' where variable='jscachelife'");
					dataBaseService.runQuery("update jrun_settings set value='"+Common.addslashes(refdomain)+"' where variable='jsrefdomains'");
					settings.put("jsstatus", "1");
					settings.put("jscachelife", jscachelifeint+"");
					settings.put("jsrefdomains", refdomain);
					request.setAttribute("message", getMessage(request, "a_system_jr_update_s"));
					request.setAttribute("url_forward", request.getHeader("referer"));
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=jswizard");
		return null;
	}
	private int convertInt(String ss) {
		int target = 0;
		try {
			target = Integer.valueOf(ss);
		} catch (Exception e) {
		}
		return target;
	}
	@SuppressWarnings("unchecked")
	public ActionForward jsthreads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String inentifier = request.getParameter("inentifier");
		request.setAttribute("jsname", inentifier);
		request.setAttribute("charset", getCharset());
		Map<String,String> settings = ForumInit.settings;
		if (inentifier != null) {
			String keys = "jswizard_" + inentifier;
			String values = settings.get(keys);
			if(values==null){
				request.setAttribute("message_key", "undefined_action_return");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Map result = dataParse.characterParse(values,false);
			List<Map<String,String>> groups = dataBaseService.executeQuery("select fid,name,status from jrun_forums where type='group'");
			List<Map<String,String>> forums = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='forum'");
			List<Map<String,String>> subs = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='sub'");
			List<Threadtypes> threadtype = threadTypeServer.getAllThreadtypes();
			request.setAttribute("resultmap", result);
			Map forumMap = (Map) ((Map) result.get("parameter")).get("threads_forums");
			List<Map<String,String>> forumslist = new ArrayList<Map<String,String>>();
			List<Map<String,String>> subslist = new ArrayList<Map<String,String>>();
			for (Map<String,String> sub : subs) {
				if (forumMap != null) {
					Iterator it = forumMap.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						String value = forumMap.get(key).toString();
						if (sub.get("fid").equals(value)) {
							sub.put("flag", "true");
						}
					}
				}
				subslist.add(sub);
			}
			for (Map<String,String> forum : forums) {
				if (forumMap != null) {
					Iterator it = forumMap.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						String value = forumMap.get(key).toString();
						if (value.equals(forum.get("fid"))) {
							forum.put("flag", "true");
						}
					}
				}
				forumslist.add(forum);
			}
			Map threadTypeMap = new HashMap();
			Map typeMap = (Map) ((Map) result.get("parameter")).get("typeids");
			for (Threadtypes types : threadtype) {
				boolean flag = false;
				if (typeMap != null) {
					Iterator it = typeMap.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						String value = typeMap.get(key).toString();
						if (value.equals(String.valueOf(types.getTypeid()))) {
							flag = true;
						}
					}
				}
				if (flag) {
					threadTypeMap.put(types, "ok");
				} else {
					threadTypeMap.put(types, "");
				}
			}
			forums = null;subs=null;threadtype=null;forumMap=null;
			request.setAttribute("groups", groups);
			request.setAttribute("forums", forumslist);
			request.setAttribute("subs", subslist);
			request.setAttribute("threadtype", threadTypeMap);
			String path = request.getContextPath()+"/api/javascript.jsp?key="+inentifier;
			request.setAttribute("path", path);
			request.setAttribute("diaplay", "yes");
		} else {
			List<Map<String,String>> groups = dataBaseService.executeQuery("select fid,name,status from jrun_forums where type='group'");
			List<Map<String,String>> forums = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='forum'");
			List<Map<String,String>> subs = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='sub'");
			List<Threadtypes> threadtype = threadTypeServer.getAllThreadtypes();
			Map threadTypeMap = new HashMap();
			for (Threadtypes types : threadtype) {
				threadTypeMap.put(types, "");
			}
			request.setAttribute("groups", groups);
			request.setAttribute("forums", forums);
			request.setAttribute("subs", subs);
			request.setAttribute("threadtype", threadTypeMap);
			String inentname = "threads_" + Common.getRandStr(3, false);
			request.setAttribute("inentifier", inentname);
			request.setAttribute("diaplay", null);
		}
		return mapping.findForward("jsthreadsetting");
	}
	@SuppressWarnings("unchecked")
	public ActionForward editjsthreads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "jssubmit")){
				Map<String,String> settings = ForumInit.settings;
				String jstemplate = request.getParameter("parameter[jstemplate]"); 
				String jskey = request.getParameter("jskey"); 
				jskey = Common.cutstr(jskey, 22,null);
				String cachelife = request.getParameter("parameter[cachelife]"); 
				String threads_forums[] = request.getParameterValues("parameter[threads_forums]"); 
				String startrow = request.getParameter("parameter[startrow]"); 
				String items = request.getParameter("parameter[items]");
				String maxlength = request.getParameter("parameter[maxlength]"); 
				String fnamelength = request.getParameter("parameter[fnamelength]"); 
				String picpre = request.getParameter("parameter[picpre]"); 
				String tids = request.getParameter("parameter[tids]"); 
				String keyword = request.getParameter("parameter[keyword]"); 
				String tag = request.getParameter("parameter[tag]"); 
				String typeids[] = request.getParameterValues("parameter[typeids]"); 
				String threadtype = request.getParameter("parameter[threadtype]"); 
				String highlight = request.getParameter("parameter[highlight]"); 
				String blog = request.getParameter("parameter[blog]"); 
				String[] special = request.getParameterValues("parameter[special]"); 
				String rewardstatus = request.getParameter("parameter[rewardstatus]"); 
				String digest[] = request.getParameterValues("parameter[digest]"); 
				String stick[] = request.getParameterValues("parameter[stick]"); 
				String newwindow = request.getParameter("parameter[newwindow]"); 
				String orderby = request.getParameter("parameter[orderby]"); 
				String jscharset = request.getParameter("parameter[jscharset]"); 
				HashMap<String,Object> resultMap = new HashMap<String,Object>();
				HashMap<String,Object> parameterMap = new HashMap<String,Object>();
				parameterMap.put("blog", blog);
				parameterMap.put("cachelife", cachelife);
				if (digest != null) {
					Map<Integer,String> digestsubMap = new HashMap<Integer,String>();
					for (String s : digest) {
						digestsubMap.put(new Integer(s), "1");
					}
					parameterMap.put("digest", digestsubMap);
				}
				parameterMap.put("fnamelength", fnamelength);
				parameterMap.put("highlight", highlight);
				parameterMap.put("items", items);
				parameterMap.put("jscharset", jscharset);
				parameterMap.put("jstemplate", jstemplate);
				parameterMap.put("keyword", keyword);
				parameterMap.put("maxlength", maxlength);
				parameterMap.put("newwindow", newwindow);
				parameterMap.put("orderby", orderby);
				parameterMap.put("picpre", picpre);
				parameterMap.put("rewardstatus", rewardstatus);
				if (special != null) {
					Map<Integer,String> specialsubMap = new HashMap<Integer,String>();
					for (String s : special) {
						specialsubMap.put(new Integer(s), "1");
					}
					parameterMap.put("special", specialsubMap);
				}
				parameterMap.put("startrow", startrow);
				if (stick != null) {
					Map<Integer,String> sticksubMap = new HashMap<Integer,String>();
					for (String s : stick) {
						sticksubMap.put(new Integer(s), "1");
					}
					parameterMap.put("stick", sticksubMap);
				}
				parameterMap.put("tag", tag);
				if (threads_forums != null) {
					Map<Integer,String> threads_forumsubMap = new HashMap<Integer,String>();
					for (int i = 0; i < threads_forums.length; i++) {
						threads_forumsubMap.put(i, threads_forums[i]);
					}
					parameterMap.put("threads_forums", threads_forumsubMap);
				}
				parameterMap.put("threadtype", threadtype);
				parameterMap.put("tids", tids);
				if (typeids != null) {
					Map<Integer,String> typeidssubMap = new HashMap<Integer,String>();
					for (int i = 0; i < typeids.length; i++) {
						typeidssubMap.put(i, typeids[i]);
					}
					parameterMap.put("typeids", typeidssubMap);
				}
				resultMap.put("parameter", parameterMap);
				resultMap.put("type", new Integer("0"));
				String resultvalue = dataParse.combinationChar(resultMap);
				String edit = request.getParameter("edit");
				String view = edit!=null?edit:jskey;
				if((edit!=null&&!isjskey(edit))||(jskey!=null&&!isjskey(jskey))){
					request.setAttribute("message", getMessage(request, "a_system_js_edit_identifier_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				String preview = request.getParameter("preview");
				if (preview.equals("1")) {
					request.setAttribute("charset", getCharset());
					List<Map<String,String>> groups = dataBaseService.executeQuery("select fid,name,status from jrun_forums where type='group'");
					List<Map<String,String>> forums = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='forum'");
					List<Map<String,String>> subs = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='sub'");
					List<Threadtypes> threadtypes = threadTypeServer.getAllThreadtypes();
					Map forumMap = (Map) ((Map) resultMap.get("parameter")).get("threads_forums");
					List<Map<String,String>> forumslist = new ArrayList<Map<String,String>>();
					List<Map<String,String>> subslist = new ArrayList<Map<String,String>>();
					for (Map<String,String> sub : subs) {
						if (forumMap != null) {
							Iterator it = forumMap.keySet().iterator();
							while (it.hasNext()) {
								Object key = it.next();
								String value = forumMap.get(key).toString();
								if (sub.get("fid").equals(value)) {
									sub.put("flag", "true");
								}
							}
						}
						subslist.add(sub);
					}
					for (Map<String,String> forum : forums) {
						if (forumMap != null) {
							Iterator it = forumMap.keySet().iterator();
							while (it.hasNext()) {
								Object key = it.next();
								String value = forumMap.get(key).toString();
								if (value.equals(forum.get("fid"))) {
									forum.put("flag", "true");
								}
							}
						}
						forumslist.add(forum);
					}
					Map threadTypeMap = new HashMap();
					Map typeMap = (Map) ((Map) resultMap.get("parameter")).get("typeids");
					for (Threadtypes types : threadtypes) {
						boolean flag = false;
						if (typeMap != null) {
							Iterator it = typeMap.keySet().iterator();
							while (it.hasNext()) {
								Object key = it.next();
								String value = typeMap.get(key).toString();
								if (value.equals(String.valueOf(types.getTypeid()))) {
									flag = true;
								}
							}
						}
						if (flag) {
							threadTypeMap.put(types, "ok");
						} else {
							threadTypeMap.put(types, "");
						}
					}
					forums = null;subs=null;threadtypes=null;forumMap=null;
					request.setAttribute("groups", groups);
					request.setAttribute("forums", forumslist);
					request.setAttribute("subs", subslist);
					request.setAttribute("threadtype", threadTypeMap);
					settings.put("jswizard_"+view+"_preview", resultvalue);
					String path = request.getContextPath()+"/api/javascript.jsp?key="+view+"_preview";
					request.setAttribute("path", path);
					request.setAttribute("resultmap", resultMap);
					request.setAttribute("jsname", jskey);
					request.setAttribute("diaplay", "yes");
					return mapping.findForward("jsthreadsetting");
				} else {
					String value = settings.get("jswizard_"+jskey);
					ForumInit.initServletContext(servlet.getServletContext());
					settings = ForumInit.settings;
					if (edit != null && !edit.equals("") && value!=null) {
						dataBaseService.runQuery("update jrun_settings set value='"+Common.addslashes(resultvalue)+"' where variable='jswizard_"+edit+"'");
						settings.put("jswizard_"+edit, resultvalue);
						String path = JspRunConfig.realPath+"forumdata/cache/javascript_"+edit+".jsp";
						File file = new File(path);
						if(file.exists()){
							file.delete();
						}
						request.setAttribute("message", getMessage(request, "a_system_js_s"));
						request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
						return mapping.findForward("message");
					} else {
						if (value == null) {
							dataBaseService.runQuery("insert into jrun_settings(variable,value)values('jswizard_"+jskey+"','"+Common.addslashes(resultvalue)+"')");
							settings.put("jswizard_"+jskey, resultvalue);
							request.setAttribute("message", getMessage(request, "a_system_js_s"));
							request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
							return mapping.findForward("message");
						} else {
							request.setAttribute("message", getMessage(request, "a_system_js_id_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=jswizard");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward jsforums(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String inentifier = request.getParameter("inentifier");
		request.setAttribute("jsname", inentifier);
		request.setAttribute("charset", getCharset());
		Map<String,String> settings = ForumInit.settings;
		if (inentifier != null) {
			String keys = "jswizard_" + inentifier;
			String values = settings.get(keys);
			if(values==null){
				request.setAttribute("message_key", "undefined_action_return");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Map result = dataParse.characterParse(values,false);
			List<Map<String,String>> groups = dataBaseService.executeQuery("select fid,name,status from jrun_forums where type='group'");
			request.setAttribute("resultmap", result);
			Map forumMap = (Map) ((Map) result.get("parameter")).get("forums_forums");
			List<Map<String,String>> groupmaplist = new ArrayList<Map<String,String>>();
			for (Map<String,String> forum : groups) {
				if (forumMap != null) {
					Iterator it = forumMap.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						String value = forumMap.get(key).toString();
						if (value.equals(forum.get("fid"))) {
							forum.put("flag", "true");
						}
					}
				}
				groupmaplist.add(forum);
			}
			groups = null;
			request.setAttribute("groups", groupmaplist);
			String path = request.getContextPath()+"/api/javascript.jsp?key="+inentifier;
			request.setAttribute("path", path);
			request.setAttribute("diaplay", "yes");
		} else {
			List<Map<String,String>> groups = dataBaseService.executeQuery("select fid,name,status from jrun_forums where type='group'");
			request.setAttribute("groups", groups);
			String inentname = "forums_" + Common.getRandStr(3, false);
			request.setAttribute("inentifier", inentname);
			request.setAttribute("diaplay", null);
		}
		return mapping.findForward("jsforumsetting");
	}
	@SuppressWarnings("unchecked")
	public ActionForward editjsforums(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "jssubmit")){
				Map<String,String> settings = ForumInit.settings;
				String jstemplate = request.getParameter("parameter[jstemplate]"); 
				String jskey = request.getParameter("jskey"); 
				jskey = Common.cutstr(jskey, 22,null);
				String cachelife = request.getParameter("parameter[cachelife]"); 
				String forums_forums[] = request.getParameterValues("parameter[forums_forums]"); 
				String startrow = request.getParameter("parameter[startrow]"); 
				String items = request.getParameter("parameter[items]");
				String orderby = request.getParameter("parameter[orderby]"); 
				String jscharset = request.getParameter("parameter[jscharset]"); 
				String newwindow = request.getParameter("parameter[newwindow]"); 
				HashMap<String,Object> resultMap = new HashMap<String,Object>();
				HashMap<String,Object> parameterMap = new HashMap<String,Object>();
				parameterMap.put("cachelife", cachelife);
				parameterMap.put("items", items);
				parameterMap.put("jscharset", jscharset);
				parameterMap.put("jstemplate", jstemplate);
				parameterMap.put("newwindow", newwindow);
				parameterMap.put("orderby", orderby);
				parameterMap.put("startrow", startrow);
				if (forums_forums != null) {
					Map<Integer,String> forums_forumsubMap = new HashMap<Integer,String>();
					for (int i = 0; i < forums_forums.length; i++) {
						forums_forumsubMap.put(i, forums_forums[i]);
					}
					parameterMap.put("forums_forums", forums_forumsubMap);
				}
				resultMap.put("parameter", parameterMap);
				resultMap.put("type", new Integer("1"));
				String resultvalue = dataParse.combinationChar(resultMap);
				String edit = request.getParameter("edit");
				String preview = request.getParameter("preview");
				String view = edit!=null?edit:jskey;
				if((edit!=null&&!isjskey(edit))||(jskey!=null&&!isjskey(jskey))){
					request.setAttribute("message", getMessage(request, "a_system_js_edit_identifier_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (preview.equals("1")) {
					request.setAttribute("charset", getCharset());
					List<Map<String,String>> groups = dataBaseService.executeQuery("select fid,name,status from jrun_forums where type='group'");
					Map forumMap = (Map)parameterMap.get("forums_forums");
					List<Map<String,String>> groupmaplist = new ArrayList<Map<String,String>>();
					for (Map<String,String> forum : groups) {
						if (forumMap != null) {
							Iterator it = forumMap.keySet().iterator();
							while (it.hasNext()) {
								Object key = it.next();
								String value = forumMap.get(key).toString();
								if (value.equals(forum.get("fid"))) {
									forum.put("flag", "true");
								}
							}
						}
						groupmaplist.add(forum);
					}
					groups = null;
					request.setAttribute("groups", groupmaplist);
					settings.put("jswizard_"+view+"_preview", resultvalue);
					String path = request.getContextPath()+"/api/javascript.jsp?key="+view+"_preview";
					request.setAttribute("path", path);
					request.setAttribute("resultmap", resultMap);
					request.setAttribute("jsname", jskey);
					request.setAttribute("diaplay", "yes");
					return mapping.findForward("jsforumsetting");
				} else {
					String value = settings.get("jswizard_"+jskey);
					ForumInit.initServletContext(servlet.getServletContext());
					settings = ForumInit.settings;
					if (edit != null && !edit.equals("") && value!=null) {
						dataBaseService.runQuery("update jrun_settings set value='"+Common.addslashes(resultvalue)+"' where variable='jswizard_"+edit+"'");
						settings.put("jswizard_"+edit, resultvalue);
						String path = JspRunConfig.realPath+"forumdata/cache/javascript_"+edit+".jsp";
						File file = new File(path);
						if(file.exists()){
							file.delete();
						}
						request.setAttribute("message", getMessage(request, "a_system_js_s"));
						request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
						return mapping.findForward("message");
					} else {
						if (value == null) {
							Settings setting = new Settings();
							setting.setVariable("jswizard_" + jskey);
							setting.setValue(resultvalue);
							systemToolServer.saveSetting(setting);
							settings.put("jswizard_"+jskey, resultvalue);
							request.setAttribute("message",getMessage(request, "a_system_js_s") );
							request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
							return mapping.findForward("message");
						} else {
							request.setAttribute("message", getMessage(request, "a_system_js_id_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=jswizard");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward jsmemberrank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String inentifier = request.getParameter("inentifier");
		request.setAttribute("jsname", inentifier);
		request.setAttribute("charset", getCharset());
		Map<String,String> settings = ForumInit.settings;
		if (inentifier != null) {
			String keys = "jswizard_" + inentifier;
			String value = settings.get(keys);
			if(value==null){
				request.setAttribute("message_key", "undefined_action_return");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Map result = dataParse.characterParse(value,false);
			request.setAttribute("resultmap", result);
			String path = request.getContextPath()+"/api/javascript.jsp?key="+inentifier;
			request.setAttribute("path", path);
			request.setAttribute("diaplay", "yes");
		} else {
			String inentname = "memberrank_" + Common.getRandStr(3, false);
			request.setAttribute("inentifier", inentname);
			request.setAttribute("diaplay", null);
		}
		return mapping.findForward("jsmemberranksetting");
	}
	public ActionForward editjsmembers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "jssubmit")){
				Map<String,String> settings = ForumInit.settings;
				String jstemplate = request.getParameter("parameter[jstemplate]"); 
				String jskey = request.getParameter("jskey"); 
				jskey = Common.cutstr(jskey, 22,null);
				String cachelife = request.getParameter("parameter[cachelife]"); 
				String startrow = request.getParameter("parameter[startrow]"); 
				String items = request.getParameter("parameter[items]");
				String orderby = request.getParameter("parameter[orderby]"); 
				String jscharset = request.getParameter("parameter[jscharset]"); 
				String newwindow = request.getParameter("parameter[newwindow]"); 
				HashMap<String,Object> resultMap = new HashMap<String,Object>();
				HashMap<String,Object> parameterMap = new HashMap<String,Object>();
				parameterMap.put("cachelife", cachelife);
				parameterMap.put("items", items);
				parameterMap.put("jscharset", jscharset);
				parameterMap.put("jstemplate", jstemplate);
				parameterMap.put("newwindow", newwindow);
				parameterMap.put("orderby", orderby);
				parameterMap.put("startrow", startrow);
				resultMap.put("parameter", parameterMap);
				resultMap.put("type", new Integer("2"));
				String resultvalue = dataParse.combinationChar(resultMap);
				String edit = request.getParameter("edit");
				String preview = request.getParameter("preview");
				String view = edit!=null?edit:jskey;
				if((edit!=null&&!isjskey(edit))||(jskey!=null&&!isjskey(jskey))){
					request.setAttribute("message", getMessage(request, "a_system_js_edit_identifier_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (preview.equals("1")) {
					request.setAttribute("charset", getCharset());
					settings.put("jswizard_"+view+"_preview", resultvalue);
					String path = request.getContextPath()+"/api/javascript.jsp?key="+view+"_preview";
					request.setAttribute("path", path);
					request.setAttribute("resultmap", resultMap);
					request.setAttribute("jsname", jskey);
					request.setAttribute("diaplay", "yes");
					return mapping.findForward("jsmemberranksetting");
				} else {
					String value = settings.get("jswizard_"+jskey);
					ForumInit.initServletContext(servlet.getServletContext());
					settings = ForumInit.settings;
					if (edit != null && !edit.equals("") && value!=null) {
						dataBaseService.runQuery("update jrun_settings set value='"+Common.addslashes(resultvalue)+"' where variable='jswizard_"+edit+"'");
						settings.put("jswizard_"+edit, resultvalue);
						String path = JspRunConfig.realPath+"forumdata/cache/javascript_"+edit+".jsp";
						File file = new File(path);
						if(file.exists()){
							file.delete();
						}
						request.setAttribute("message", getMessage(request, "a_system_js_s"));
						request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
						return mapping.findForward("message");
					} else {
						if (value == null) {
							Settings settingt = new Settings();
							settingt.setVariable("jswizard_" + jskey);
							settingt.setValue(resultvalue);
							systemToolServer.saveSetting(settingt);
							settings.put("jswizard_"+jskey, resultvalue);
							request.setAttribute("message", getMessage(request, "a_system_js_s"));
							request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
							return mapping.findForward("message");
						} else {
							request.setAttribute("message", getMessage(request, "a_system_js_id_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=jswizard");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward jsstats(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String inentifier = request.getParameter("inentifier");
		request.setAttribute("jsname", inentifier);
		request.setAttribute("charset", getCharset());
		Map<String,String> settings = ForumInit.settings;
		if (inentifier != null) {
			String keys = "jswizard_" + inentifier;
			String values = settings.get(keys);
			if(values==null){
				request.setAttribute("message_key", "undefined_action_return");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Map result = dataParse.characterParse(values,false);
			request.setAttribute("resultmap", result);
			String path = request.getContextPath()+"/api/javascript.jsp?key="+inentifier;
			request.setAttribute("path", path);
			request.setAttribute("diaplay", "yes");
		} else {
			String inentname = "stats_" + Common.getRandStr(3, false);
			request.setAttribute("inentifier", inentname);
			request.setAttribute("diaplay", null);
		}
		return mapping.findForward("jstatssetting");
	}
	public ActionForward editjsstats(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "jssubmit")){
				Map<String,String> settings = ForumInit.settings;
				String jstemplate = request.getParameter("parameter[jstemplate]"); 
				String jskey = request.getParameter("jskey"); 
				jskey = Common.cutstr(jskey, 22,null);
				String cachelife = request.getParameter("parameter[cachelife]"); 
				String jscharset = request.getParameter("parameter[jscharset]"); 
				String forumsdisplay = request.getParameter("parameter[forums][display]"); 
				String forumstitle = request.getParameter("parameter[forums][title]"); 
				String threaddisplay = request.getParameter("parameter[threads][display]"); 
				String threadtitle = request.getParameter("parameter[threads][title]"); 
				String postsdisplay = request.getParameter("parameter[posts][display]"); 
				String poststitle = request.getParameter("parameter[posts][title]"); 
				String memberdisplay = request.getParameter("parameter[members][display]"); 
				String membertitle = request.getParameter("parameter[members][title]"); 
				String onlinedisplay = request.getParameter("parameter[online][display]"); 
				String onlinetitle = request.getParameter("parameter[online][title]"); 
				String onlinmemdis = request.getParameter("parameter[onlinemembers][display]"); 
				String onlinmemtitle = request.getParameter("parameter[onlinemembers][title]"); 
				HashMap<String,Object> resultMap = new HashMap<String,Object>();
				HashMap<String,Object> parameterMap = new HashMap<String,Object>();
				parameterMap.put("cachelife", cachelife);
				parameterMap.put("jscharset", jscharset);
				parameterMap.put("jstemplate", jstemplate);
				HashMap<String,String> forumsMap = new HashMap<String,String>();
				if (forumsdisplay != null) {
					forumsMap.put("display", "1");
				}
				forumsMap.put("title", forumstitle);
				parameterMap.put("forums", forumsMap);
				HashMap<String,String> threadsMap = new HashMap<String,String>();
				if (threaddisplay != null) {
					threadsMap.put("display", "1");
				}
				threadsMap.put("title", threadtitle);
				parameterMap.put("threads", threadsMap);
				HashMap<String,String> postMap = new HashMap<String,String>();
				if (postsdisplay != null) {
					postMap.put("display", "1");
				}
				postMap.put("title", poststitle);
				parameterMap.put("posts", postMap);
				HashMap<String,String> membersMap = new HashMap<String,String>();
				if (memberdisplay != null) {
					membersMap.put("display", "1");
				}
				membersMap.put("title", membertitle);
				parameterMap.put("members", membersMap);
				HashMap<String,String> onlineMap = new HashMap<String,String>();
				if (onlinedisplay != null) {
					onlineMap.put("display", "1");
				}
				onlineMap.put("title", onlinetitle);
				parameterMap.put("online", onlineMap);
				HashMap<String,String> onlinmemMap = new HashMap<String,String>();
				if (onlinmemdis != null) {
					onlinmemMap.put("display", "1");
				}
				onlinmemMap.put("title", onlinmemtitle);
				parameterMap.put("onlinemembers", onlinmemMap);
				resultMap.put("parameter", parameterMap);
				resultMap.put("type", new Integer("3"));
				String resultvalue = dataParse.combinationChar(resultMap);
				String edit = request.getParameter("edit");
				String preview = request.getParameter("preview");
				String view = edit!=null?edit:jskey;
				if((edit!=null&&!isjskey(edit))||(jskey!=null&&!isjskey(jskey))){
					request.setAttribute("message", getMessage(request, "a_system_js_edit_identifier_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (preview.equals("1")) {
					request.setAttribute("charset", getCharset());
					settings.put("jswizard_"+view+"_preview", resultvalue);
					String path = request.getContextPath()+"/api/javascript.jsp?key="+view+"_preview";
					request.setAttribute("path", path);
					request.setAttribute("resultmap", resultMap);
					request.setAttribute("jsname", jskey);
					request.setAttribute("diaplay", "yes");
					return mapping.findForward("jstatssetting");
				} else {
					String value = settings.get("jswizard_"+jskey);
					ForumInit.initServletContext(servlet.getServletContext());
					settings = ForumInit.settings;
					if (edit != null && !edit.equals("") && value!=null) {
						dataBaseService.runQuery("update jrun_settings set value='"+Common.addslashes(resultvalue)+"' where variable='jswizard_"+edit+"'");
						settings.put("jswizard_"+edit, resultvalue);
						String path = JspRunConfig.realPath+"forumdata/cache/javascript_"+edit+".jsp";
						File file = new File(path);
						if(file.exists()){
							file.delete();
						}
						request.setAttribute("message", getMessage(request, "a_system_js_s"));
						request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
						return mapping.findForward("message");
					} else {
						if (value == null) {
							Settings setting = new Settings();
							setting.setVariable("jswizard_" + jskey);
							setting.setValue(resultvalue);
							systemToolServer.saveSetting(setting);
							settings.put("jswizard_"+jskey, resultvalue);
							request.setAttribute("message", getMessage(request, "a_system_js_s"));
							request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
							return mapping.findForward("message");
						} else {
							request.setAttribute("message", getMessage(request, "a_system_js_id_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=jswizard");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward jsimages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String inentifier = request.getParameter("inentifier");
		request.setAttribute("jsname", inentifier);
		request.setAttribute("charset", getCharset());
		Map<String,String> settings =ForumInit.settings;
		if (inentifier != null) {
			String keys = "jswizard_" + inentifier;
			String values = settings.get(keys);
			if(values==null){
				request.setAttribute("message_key", "undefined_action_return");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Map result = dataParse.characterParse(values,false);
			List<Map<String,String>> groups = dataBaseService.executeQuery("select fid,name,status from jrun_forums where type='group'");
			List<Map<String,String>> forums = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='forum'");
			List<Map<String,String>> subs = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='sub'");
			request.setAttribute("resultmap", result);
			Map forumMap = (Map) ((Map) result.get("parameter")).get("images_forums");
			List<Map<String,String>> forumslist = new ArrayList<Map<String,String>>();
			List<Map<String,String>>sublist = new ArrayList<Map<String,String>>();
			for (Map<String,String> sub : subs) {
				if (forumMap != null) {
					Iterator it = forumMap.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						String value = forumMap.get(key).toString();
						if (sub.get("fid").equals(value)) {
							sub.put("flag", "true");
						}
					}
				}
				sublist.add(sub);
			}
			for (Map<String,String> forum : forums) {
				if (forumMap != null) {
					Iterator it = forumMap.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						String value = forumMap.get(key).toString();
						if (value.equals(forum.get("fid"))) {
							forum.put("flag", "true");
						}
					}
				}
				forumslist.add(forum);
			}
			forums = null;subs=null;
			request.setAttribute("groups", groups);
			request.setAttribute("forums", forumslist);
			request.setAttribute("subs", sublist);
			String path = request.getContextPath()+"/api/javascript.jsp?key="+inentifier;
			request.setAttribute("path", path);
			request.setAttribute("diaplay", "yes");
		} else {
			List<Map<String,String>> groups = dataBaseService.executeQuery("select fid,name,status from jrun_forums where type='group'");
			List<Map<String,String>> forums = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='forum'");
			List<Map<String,String>> subs = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='sub'");
			request.setAttribute("groups", groups);
			request.setAttribute("forums", forums);
			request.setAttribute("subs", subs);
			String inentname = "images_" + Common.getRandStr(3, false);
			request.setAttribute("inentifier", inentname);
			request.setAttribute("diaplay", null);
		}
		return mapping.findForward("jsimagesetting");
	}
	@SuppressWarnings("unchecked")
	public ActionForward editjsimages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "jssubmit")){
				Map<String,String> settings = ForumInit.settings;
				String jstemplate = request.getParameter("parameter[jstemplate]"); 
				String jskey = request.getParameter("jskey"); 
				jskey = Common.cutstr(jskey, 22,null);
				String cachelife = request.getParameter("parameter[cachelife]"); 
				String images_forums[] = request.getParameterValues("parameter[images_forums]"); 
				String startrow = request.getParameter("parameter[startrow]"); 
				String items = request.getParameter("parameter[items]");
				String blog = request.getParameter("parameter[blog]"); 
				String digest[] = request.getParameterValues("parameter[digest]"); 
				String newwindow = request.getParameter("parameter[newwindow]"); 
				String jscharset = request.getParameter("parameter[jscharset]"); 
				String maxwidth = request.getParameter("parameter[maxwidth]"); 
				String maxheight = request.getParameter("parameter[maxheight]"); 
				HashMap<String,Object> resultMap = new HashMap<String,Object>();
				HashMap<String,Object> parameterMap = new HashMap<String,Object>();
				parameterMap.put("blog", blog);
				parameterMap.put("cachelife", cachelife);
				if (digest != null) {
					Map<Integer,String> digestsubMap = new HashMap<Integer,String>();
					for (String s : digest) {
						digestsubMap.put(new Integer(s), "1");
					}
					parameterMap.put("digest", digestsubMap);
				}
				parameterMap.put("maxwidth", maxwidth);
				parameterMap.put("maxheight", maxheight);
				parameterMap.put("items", items);
				parameterMap.put("jscharset", jscharset);
				parameterMap.put("jstemplate", jstemplate);
				parameterMap.put("newwindow", newwindow);
				parameterMap.put("startrow", startrow);
				if (images_forums != null) {
					Map<Integer,String> images_forumsubMap = new HashMap<Integer,String>();
					for (int i = 0; i < images_forums.length; i++) {
						images_forumsubMap.put(i, images_forums[i]);
					}
					parameterMap.put("images_forums", images_forumsubMap);
				}
				resultMap.put("parameter", parameterMap);
				resultMap.put("type", new Integer("4"));
				String resultvalue = dataParse.combinationChar(resultMap);
				String edit = request.getParameter("edit");
				String preview = request.getParameter("preview");
				String view = edit!=null?edit:jskey;
				if((edit!=null&&!isjskey(edit))||(jskey!=null&&!isjskey(jskey))){
					request.setAttribute("message", getMessage(request, "a_system_js_edit_identifier_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (preview.equals("1")) {
					request.setAttribute("charset", getCharset());
					List<Map<String,String>> groups = dataBaseService.executeQuery("select fid,name,status from jrun_forums where type='group'");
					List<Map<String,String>> forums = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='forum'");
					List<Map<String,String>> subs = dataBaseService.executeQuery("select fid,name,fup,status from jrun_forums where type='sub'");
					Map<Integer,String> forumMap = (Map<Integer,String>) parameterMap.get("images_forums");
					List<Map<String,String>> forumslist = new ArrayList<Map<String,String>>();
					List<Map<String,String>>sublist = new ArrayList<Map<String,String>>();
					for (Map<String,String> sub : subs) {
						if (forumMap != null) {
							Iterator it = forumMap.keySet().iterator();
							while (it.hasNext()) {
								Object key = it.next();
								String value = forumMap.get(key).toString();
								if (sub.get("fid").equals(value)) {
									sub.put("flag", "true");
								}
							}
						}
						sublist.add(sub);
					}
					for (Map<String,String> forum : forums) {
						if (forumMap != null) {
							Iterator it = forumMap.keySet().iterator();
							while (it.hasNext()) {
								Object key = it.next();
								String value = forumMap.get(key).toString();
								if (value.equals(forum.get("fid"))) {
									forum.put("flag", "true");
								}
							}
						}
						forumslist.add(forum);
					}
					forums = null;subs=null;
					request.setAttribute("groups", groups);
					request.setAttribute("forums", forumslist);
					request.setAttribute("subs", sublist);
					settings.put("jswizard_"+view+"_preview", resultvalue);
					String path = request.getContextPath()+"/api/javascript.jsp?key="+view+"_preview";
					request.setAttribute("path", path);
					request.setAttribute("resultmap", resultMap);
					request.setAttribute("jsname", jskey);
					request.setAttribute("diaplay", "yes");
					return mapping.findForward("jsimagesetting");
				} else {
					String value = settings.get("jswizard_"+jskey);
					ForumInit.initServletContext(servlet.getServletContext());
					settings = ForumInit.settings;
					if (edit != null && !edit.equals("") && value!=null) {
						dataBaseService.runQuery("update jrun_settings set value='"+Common.addslashes(resultvalue)+"' where variable='jswizard_"+edit+"'");
						settings.put("jswizard_"+edit, resultvalue);
						String path = JspRunConfig.realPath+"forumdata/cache/javascript_"+edit+".jsp";
						File file = new File(path);
						if(file.exists()){
							file.delete();
						}
						request.setAttribute("message", getMessage(request, "a_system_js_s"));
						request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
						return mapping.findForward("message");
					} else {
						if (value == null) {
							Settings setting = new Settings();
							setting.setVariable("jswizard_" + jskey);
							setting.setValue(resultvalue);
							systemToolServer.saveSetting(setting);
							settings.put("jswizard_"+jskey, resultvalue);
							request.setAttribute("message", getMessage(request, "a_system_js_s"));
							request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
							return mapping.findForward("message");
						} else {
							request.setAttribute("message", getMessage(request, "a_system_js_id_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=jswizard");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward jscustom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String inentifier = request.getParameter("inentifier");
		request.setAttribute("jsname", inentifier);
		request.setAttribute("charset", getCharset());
		Map<String,String> settings = ForumInit.settings;
		if (inentifier != null) {
			String path = request.getContextPath()+"/api/javascript.jsp?key="+inentifier;
			inentifier = "jswizard_" + inentifier;
			String values = settings.get(inentifier);
			if(values==null){
				request.setAttribute("message_key", "undefined_action_return");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Map result = dataParse.characterParse(values,false);
			request.setAttribute("resultmap", result);
			request.setAttribute("path", path);
			request.setAttribute("diaplay", "yes");
		} else {
			String inentname = "custom_" + Common.getRandStr(3, false);
			request.setAttribute("inentifier", inentname);
			request.setAttribute("diaplay", null);
		}
		return mapping.findForward("jscoustemsetting");
	}
	public ActionForward editjscoustem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "jssubmit")){
				Map<String,String> settings = ForumInit.settings;
				String jstemplate = request.getParameter("parameter[jstemplate]"); 
				String jskey = request.getParameter("jskey"); 
				jskey = Common.cutstr(jskey, 22,null);
				String cachelife = request.getParameter("parameter[cachelife]"); 
				String jscharset = request.getParameter("parameter[jscharset]"); 
				HashMap<String,Object> resultMap = new HashMap<String,Object>();
				HashMap<String,Object> parameterMap = new HashMap<String,Object>();
				parameterMap.put("cachelife", cachelife);
				parameterMap.put("jscharset", jscharset);
				parameterMap.put("jstemplate", jstemplate);
				resultMap.put("parameter", parameterMap);
				resultMap.put("type", new Integer("5"));
				String resultvalue = dataParse.combinationChar(resultMap);
				String edit = request.getParameter("edit");
				String view = edit!=null?edit:jskey;
				String preview = request.getParameter("preview");
				if((edit!=null&&!isjskey(edit))||(jskey!=null&&!isjskey(jskey))){
					request.setAttribute("message", getMessage(request, "a_system_js_edit_identifier_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (preview.equals("1")) {
					request.setAttribute("charset", getCharset());
					settings.put("jswizard_"+view+"_preview", resultvalue);
					String path = request.getContextPath()+"/api/javascript.jsp?key="+view+"_preview";
					request.setAttribute("path", path);
					request.setAttribute("resultmap", resultMap);
					request.setAttribute("jsname", jskey);
					request.setAttribute("diaplay", "yes");
					return mapping.findForward("jscoustemsetting");
				} else {
					String value = settings.get("jswizard_"+jskey);
					ForumInit.initServletContext(servlet.getServletContext());
					settings = ForumInit.settings;
					if (edit != null && !edit.equals("") && value!=null) {
						dataBaseService.runQuery("update jrun_settings set value='"+Common.addslashes(resultvalue)+"' where variable='jswizard_"+edit+"'");
						request.setAttribute("message", getMessage(request, "a_system_js_s"));
						settings.put("jswizard_"+edit, resultvalue);
						settings.remove("jswizard_"+edit+"_preview");
						String path = JspRunConfig.realPath+"forumdata/cache/javascript_"+edit+".jsp";
						File file = new File(path);
						if(file.exists()){
							file.delete();
						}
						request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
						return mapping.findForward("message");
					} else {
						if (value == null) {
							Settings setting = new Settings();
							setting.setVariable("jswizard_" + jskey);
							setting.setValue(resultvalue);
							systemToolServer.saveSetting(setting);
							settings.put("jswizard_"+jskey, resultvalue);
							settings.remove("jswizard_"+jskey+"_preview");
							request.setAttribute("message", getMessage(request, "a_system_js_s"));
							request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
							return mapping.findForward("message");
						} else {
							request.setAttribute("message", getMessage(request, "a_system_js_id_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=jswizard");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward jswizard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "jsexportsubmit")||submitCheck(request, "jsdelsubmit")||submitCheck(request, "importsubmit")){
				String[] keyarray = request.getParameterValues("keyarray");
				String jsdelsubmit = request.getParameter("jsdelsubmit");
				String jsexportsubmit = request.getParameter("jsexportsubmit");
				String importsubmit = request.getParameter("importsubmit");
				Map<String,String> settings = ForumInit.settings;
				if (jsdelsubmit != null) {
					if (keyarray != null) {
						StringBuffer buffer = new StringBuffer();
						for (String s : keyarray) {
							buffer.append("','jswizard_"+s);
							settings.remove("jswizard_"+s);
						}
						if(buffer.length()>0){
							dataBaseService.runQuery("delete from jrun_settings WHERE variable IN ("+buffer.substring(2)+"')");
						}
						request.setAttribute("message", getMessage(request, "a_system_js_s"));
						request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
						return mapping.findForward("message");
					}
				}else if(jsexportsubmit!=null){
					if(keyarray!=null){
						String keys = Common.implode(keyarray, "','jswizard_");
						Map<String,String> exportDatas = new HashMap<String,String>();
						List<Map<String,String>> jswizards = dataBaseService.executeQuery("SELECT * FROM jrun_settings WHERE variable IN ('jswizard_"+keys+"')");
						for (Map<String, String> jswizard : jswizards) {
							Map<String,Object> value=dataParse.characterParse(jswizard.get("value"), false);
							int type =(Integer)value.get("type");
							Map parameter=(Map)value.get("parameter");
							switch (type) {
								case 0:
									parameter.remove("threads_forums");
									parameter.remove("tids");
									parameter.remove("typeids");
									break;
								case 1:
									parameter.remove("forums_forums");
									break;
								case 4:
									parameter.remove("images_forums");
									break;
							}
							exportDatas.put(jswizard.get("variable").replaceAll("^jswizard_", ""), dataParse.combinationChar(value));
						}
						int timestamp = (Integer)(request.getAttribute("timestamp"));
						String timeoffset = settings.get("timeoffset");
						String name = Common.gmdate("yyyyMMdd", timestamp, timeoffset);
						exportData(request, response, "JspRun_jswizard_"+name+".txt", "JSWizard Dump", exportDatas);
						return null;
					}
				}else if(importsubmit!=null){
					String importrewrite = request.getParameter("importrewrite");
					String importdata = request.getParameter("importdata");
					importdata=importdata.replaceAll("(#.*\\s+)*", "");
					Map<String,String> importMap=dataParse.characterParse(Base64.decode(importdata, JspRunConfig.CHARSET), false);
					if(importMap==null||importMap.size()==0)
					{
						request.setAttribute("message", getMessage(request, "a_system_styles_import_data_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					Set<String> keyset = importMap.keySet();
					StringBuffer keybuffer = new StringBuffer();
					for(String key:keyset){
						keybuffer.append("','jswizard_"+key);
					}
					if(!importrewrite.equals("2")){
						if(keybuffer.length()>0){
							List<Map<String,String>> jswizardlist = dataBaseService.executeQuery("SELECT variable FROM jrun_settings WHERE variable IN ("+keybuffer.substring(2)+"')");
							for(Map<String,String> jswizard:jswizardlist){
								String key = jswizard.get("variable").replaceAll("^jswizard_", "");
								if("1".equals(importrewrite)){
									importMap.remove(key);
								}else{
									request.setAttribute("message", getMessage(request, "a_system_js_import_exist",key));
									request.setAttribute("return", true);
									return mapping.findForward("message");
								}
							}
						}
					}
					for(String key:keyset){
						String value = importMap.get(key);
						settings.put("jswizard_"+key, value);
						dataBaseService.runQuery("REPLACE INTO jrun_settings (variable, value) VALUES ('jswizard_"+key+"', '"+Common.addslashes(value)+"')",true);
					}
					request.setAttribute("message", getMessage(request, "a_system_js_s"));
					request.setAttribute("url_forward", "admincp.jsp?action=jswizard");
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=jswizard");
		return null;
	}
	public ActionForward digestsubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "digestsubmit",true)){
				String pertasks = request.getParameter("pertask");
				String currents = request.getParameter("current");
				int processed = 0;
				int current = 0;
				if (currents != null) {
					current = convertInt(currents);
				}
				int pertask = convertInt(pertasks);
				int pers = pertask + current;
				processed = 0;
				processed = systemToolServer.memberdigestposts(current, pertask, processed);
				if (processed != 0) {
					request.setAttribute("message", getMessage(request, "a_system_counter_digest")+": "+getMessage(request, "newsletter_processing" ,String.valueOf(current),String.valueOf(pers)));
					current = pers;
					request.setAttribute("url_forward", "admincp.jsp?action=counter&digest=yes&digestsubmit=yes&formHash="+Common.formHash(request)+"&pertask=" + pertask+ "&current=" + current);
					return mapping.findForward("message");
				} else {
					request.setAttribute("message", getMessage(request, "a_system_counter_digest_succeed"));
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=counter");
		return null;
	}
	public ActionForward membersubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "membersubmit",true)){
				String pertasks = request.getParameter("pertask");
				String currents = request.getParameter("current");
				int processed = 0;
				int current = 0;
				if (currents != null) {
					current = convertInt(currents);
				}
				int pertask = convertInt(pertasks);
				int pers = pertask + current;
				processed = 0;
				processed = systemToolServer.memberposts(current, pertask, processed);
				if (processed != 0) {
					request.setAttribute("message", getMessage(request, "a_system_counter_member")+": "+getMessage(request, "newsletter_processing" ,String.valueOf(current),String.valueOf(pers)));
					current = pers;
					request.setAttribute("url_forward", "admincp.jsp?action=counter&member=yes&membersubmit=yes&formHash="+Common.formHash(request)+"&pertask=" + pertask+ "&current=" + current);
					return mapping.findForward("message");
				} else {
					request.setAttribute("message", getMessage(request, "a_system_counter_member_succeed"));
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=counter");
		return null;
	}
	public ActionForward threadsubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "threadsubmit",true)){
				String pertasks = request.getParameter("pertask");
				String currents = request.getParameter("current");
				int processed = 0;
				int current = 0;
				if (currents != null) {
					current = convertInt(currents);
				}
				int pertask = convertInt(pertasks);
				int pers = pertask + current;
				processed = 0;
				processed = systemToolServer.threadposts(current, pertask, processed);
				if (processed != 0) {
					request.setAttribute("message", getMessage(request, "a_system_counter_thread")+": "+getMessage(request, "newsletter_processing",String.valueOf(current),String.valueOf(pers)));
					current = pers;
					request.setAttribute("url_forward", "admincp.jsp?action=counter&thread=yes&threadsubmit=yes&formHash="+Common.formHash(request)+"&pertask=" + pertask + "&current=" + current);
					return mapping.findForward("message");
				} else {
					request.setAttribute("message", getMessage(request, "a_system_counter_thread_succeed"));
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=counter");
		return null;
	}
	public ActionForward movedthreadsubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			if(submitCheck(request, "movedthreadsubmit")){
				String pertasks = request.getParameter("pertask");
				int processed = 0;
				int current = 0;
				int pertask = convertInt(pertasks);
				do {
					processed = 0;
					processed = systemToolServer.movedthreads(current, pertask,processed);
					current = current + pertask;
				} while (processed != 0);
				request.setAttribute("message", getMessage(request, "a_system_counter_moved_thread_succeed"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=counter");
		return null;
	}
	public ActionForward cleanupsubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "cleanupsubmit",true)){
				String pertasks = request.getParameter("pertask");
				String currents = request.getParameter("current");
				int processed = 0;
				int current = 0;
				if (currents != null) {
					current = convertInt(currents);
				}
				int pertask = convertInt(pertasks);
				int pers = pertask + current;
				processed = systemToolServer.cleanupthreads(current, pertask, processed);
				if (processed != 0){
					request.setAttribute("message", getMessage(request, "a_system_counter_moved_favorites_logs")+": "+getMessage(request, "newsletter_processing",String.valueOf(current),String.valueOf(pers)));
					current = pers;
					request.setAttribute("url_forward", "admincp.jsp?action=counter&cleanup=yes&cleanupsubmit=yes&formHash="+Common.formHash(request)+"&pertask=" + pertask+ "&current=" + current);
					return mapping.findForward("message");
				}else{
					request.setAttribute("message", getMessage(request, "a_system_counter_moved_favorites_logs_succeed"));
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=counter");
		return null;
	}
	public ActionForward forumsubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "forumsubmit",true)){
				String pertasks = request.getParameter("pertask");
				String currents = request.getParameter("current");
				int processed = 0;
				int current = 0;
				if (currents != null) {
					current = convertInt(currents);
				}
				int pertask = convertInt(pertasks);
				int pers = pertask + current;
				processed = systemToolServer.forumpost(current, pertask, processed);
				if (processed != 0){
					request.setAttribute("message", getMessage(request, "a_system_counter_forum")+": "+getMessage(request, "newsletter_processing",String.valueOf(current),String.valueOf(pers)));
					current = pers;
					request.setAttribute("url_forward", "admincp.jsp?action=counter&forum=yes&forumsubmit=yes&formHash="+Common.formHash(request)+"&pertask=" + pertask+ "&current=" + current);
					return mapping.findForward("message");
				}else{
					request.setAttribute("message", getMessage(request, "a_system_counter_forum_succeed"));
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=counter");
		return null;
	}
	private boolean isjskey(String key) {
		if(key.length()>0){
			return Common.matches(key, "^[a-zA-Z0-9_]+$");
		}else{
			return true;
		}
	}
	private String getCharset(){
		if(JspRunConfig.CHARSET.equals("GBK")){
			return "UTF-8";
		}else{
			return "GBK";
		}
	}
}
