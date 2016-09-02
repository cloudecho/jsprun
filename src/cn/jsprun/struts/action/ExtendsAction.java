package cn.jsprun.struts.action;
import java.io.File;
import java.lang.management.ManagementFactory;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.api.Tenpayapi;
import cn.jsprun.domain.Members;
import cn.jsprun.utils.Base64;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.vo.SearchEngineVO;
import com.sun.management.OperatingSystemMXBean;
public class ExtendsAction extends BaseAction {
	public ActionForward getSevrerInfo_SysType(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String sysType = System.getProperty("os.name");
		request.setAttribute("message", getMessage(request, "a_extends_current_system",sysType));
		return mapping.findForward("message");
	}
	public ActionForward getSevrerInfo_CUPCount(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("message", getMessage(request, "a_extends_current_cupinfo",String.valueOf(Runtime.getRuntime().availableProcessors())));
		return mapping.findForward("message");
	}
	public ActionForward getSevrerInfo_MemoryInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setAttribute("message", getMessage(request, "a_extends_current_memroyinfo",this.getPhysicalMemorySize()));
		}catch (Exception e) {
			request.setAttribute("message", "当前webContainer无法获取当前服务器的内存大小！");
		}
		return mapping.findForward("message");
	}
	@SuppressWarnings("unchecked")
	public ActionForward plugins(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String edit=request.getParameter("edit");
		String identifier=request.getParameter("identifier");
		if(edit==null&&identifier==null){
			List<Map<String,String>> plugins=dataBaseService.executeQuery("SELECT p.*, pv.pluginvarid FROM jrun_plugins p LEFT JOIN jrun_pluginvars pv USING(pluginid) GROUP BY p.pluginid ORDER BY p.available DESC, p.pluginid");
			if(plugins!=null&&plugins.size()>0){
				HttpSession session=request.getSession();
				byte adminid=(Byte)session.getAttribute("jsprun_adminid");
				StringBuffer editstr=null;
				for (Map<String, String> plugin : plugins) {
					byte plugin_adminid=Byte.valueOf(plugin.get("adminid"));
					if(plugin_adminid==0||plugin_adminid>=adminid){
						plugin.put("disabled", "");
						editstr=new StringBuffer(plugin.get("pluginvarid")!=null?"<a href=\"admincp.jsp?action=plugins&edit="+plugin.get("pluginid")+"\">["+getMessage(request, "a_extends_plugins_settings")+"]</a> ":"");
						Map<Integer,Map> modules=dataParse.characterParse(plugin.get("modules"), false);
						if(modules!=null&&modules.size()>0){
							Iterator<Entry<Integer,Map>> keys =modules.entrySet().iterator();
							while (keys.hasNext()) {
								Entry<Integer,Map> e = keys.next();
								Map module=e.getValue();
								byte module_adminid=Byte.valueOf((String)module.get("adminid"));
								if("3".equals((String)module.get("type"))&&(module_adminid==0||module_adminid>=adminid)){
									editstr.append("<a href=\"admincp.jsp?action=plugins&identifier="+plugin.get("identifier")+"&mod="+module.get("name")+"\">["+getMessage(request, "a_extends_plugins_settings_module")+": "+module.get("menu")+"]</a> ");
								}
							}
						}
						plugin.put("edit", editstr.toString());
					}else{
						plugin.put("disabled", "disabled");
						plugin.put("edit", "["+getMessage(request, "detail")+"]");
					}
				}
				request.setAttribute("plugins", plugins);
			}
			return mapping.findForward("toplugins");
		}else{
			List<Map<String,String>> plugins=dataBaseService.executeQuery("SELECT * FROM jrun_plugins WHERE "+(identifier!=null ? "identifier='"+identifier+"'" : "pluginid='"+edit+"'"));
			if(plugins==null||plugins.size()==0){
				request.setAttribute("message", getMessage(request, "undefined_action"));
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Map<String,String> plugin=plugins.get(0);
			edit=plugin.get("pluginid");
			Map<String,Map<String,String>> pluginvarsMap=new HashMap<String,Map<String,String>>();
			List<Map<String,String>> pluginvars=dataBaseService.executeQuery("SELECT * FROM jrun_pluginvars WHERE pluginid='"+edit+"' ORDER BY displayorder");
			for(Map<String,String> pluginvar:pluginvars){
				pluginvarsMap.put(pluginvar.get("variable"), pluginvar);
			}
			String mod=request.getParameter("mod");
			if(mod==null){
				HttpSession session=request.getSession();
				byte adminid=(Byte)session.getAttribute("jsprun_adminid");
				byte plugin_adminid=Byte.valueOf(plugin.get("adminid"));
				if((plugin_adminid>0&&adminid>plugin_adminid)||pluginvarsMap.size()==0){
					request.setAttribute("message", getMessage(request, "noaccess"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				try{
					if(submitCheck(request, "editsubmit")){
						for (Map<String, String> var : pluginvars) {
							String variable=var.get("variable");
							String value=request.getParameter("varsnew["+variable+"]");
							if(value!=null){
								if("number".equals(var.get("type"))){
									value=FormDataCheck.getDoubleString(value);
								}
								dataBaseService.runQuery("UPDATE jrun_pluginvars SET value='"+Common.addslashes(value)+"' WHERE pluginid='"+edit+"' AND variable='"+variable+"'");
							}
						}
						Cache.updateCache("plugins");
						request.setAttribute("message",getMessage(request, "a_extends_plugins_settings_succeed"));
						request.setAttribute("url_forward", "admincp.jsp?action=plugins");
						return mapping.findForward("message");
					}
				}catch (Exception e) {
					request.setAttribute("message",e.getMessage());
					return mapping.findForward("message");
				}
				for (Map<String, String> var : pluginvars) {
					var.put("variable", "varsnew["+var.get("variable")+"]");
					if("number".equals(var.get("type"))){
						var.put("type", "text");
					}else if("select".equals(var.get("type"))){
						StringBuffer type=new StringBuffer();
						type.append("<select name=\""+var.get("variable")+"\">\n");
						String[] extras=var.get("extra").split("\n");
						if(extras!=null&&extras.length>0){
							for (String extra : extras) {
								String key=null;
								String option=extra.trim();
								if(extra.indexOf("=")==-1){
									key=option;
								}else{
									String[] item=option.split("=");
									key=item[0].trim();
									option=item[1].trim();
								}
								type.append("<option value=\""+Common.htmlspecialchars(key)+"\" "+(key.equals(var.get("value"))? "selected" :"")+">"+option+"</option>\n");
							}
						}
						type.append("</select>\n");
						var.put("type",type.toString());
						var.put("variable", "");
						var.put("value", "");
					}
				}
				request.setAttribute("edit",edit);
				request.setAttribute("plugin",plugin);
				request.setAttribute("pluginvars",pluginvars);
				return mapping.findForward("toplugins_var_edit");
			}else{
				String modfile=null;
				Map<Integer,Map>  modules=dataParse.characterParse(plugin.get("modules"), false);
				if(modules!=null&&modules.size()>0){
					HttpSession session=request.getSession();
					byte adminid=(Byte)session.getAttribute("jsprun_adminid");
					Set<Entry<Integer,Map>> keys =modules.entrySet();
					for (Entry<Integer,Map> temp : keys) {
						Map module=temp.getValue();
						byte module_adminid=Byte.valueOf((String)module.get("adminid"));
						if("3".equals((String)module.get("type"))&&module.get("name").equals(mod)&&(module_adminid==0||module_adminid>=adminid)){
							modfile="/plugins/"+plugin.get("directory")+mod+".inc.jsp";
							break;
						}
					}
				}
				if(modfile!=null){
					File file=null;
					try {
						file=new File(JspRunConfig.realPath+modfile);
						if(file.exists()){
							request.getRequestDispatcher(modfile).forward(request, response);
							return null;
						}else{
							request.setAttribute("message", getMessage(request, "a_extends_plugins_settings_module_nonexistence",modfile));
							return mapping.findForward("message");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						file=null;
					}
				}
				request.setAttribute("message", getMessage(request, "undefined_action"));
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward pluginsedit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String pluginid=request.getParameter("pluginid");
		List<Map<String,String>> plugins=dataBaseService.executeQuery("SELECT * FROM jrun_plugins WHERE pluginid='"+pluginid+"'");
		if(plugins==null||plugins.size()==0){
			request.setAttribute("message", getMessage(request, "undefined_action"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		Map<String,String> plugin=plugins.get(0);
		Map<Integer,Map> modules=dataParse.characterParse(plugin.get("modules"), false);
		try{
			if(submitCheck(request, "editsubmit")){
				String type=request.getParameter("type");
				if("common".equals(type)){
					String namenew=Common.htmlspecialchars(request.getParameter("namenew").trim());
					String directorynew=Common.htmlspecialchars(request.getParameter("directorynew"));
					String identifiernew=request.getParameter("identifiernew").trim();
					String datatablesnew=Common.htmlspecialchars(request.getParameter("datatablesnew").trim());
					String descriptionnew=Common.htmlspecialchars(request.getParameter("descriptionnew"));
					String copyrightnew=plugin.get("copyright").equals("")?Common.htmlspecialchars(request.getParameter("copyrightnew")):plugin.get("copyright");
					int adminidnew=Common.toDigit(request.getParameter("adminidnew"));
					if(adminidnew<=0||adminidnew>3){
						adminidnew=1;
					}
					if(namenew.length()==0){
						request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_name_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}else if(!isplugindir(directorynew)){
						request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_directory_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}else if(!identifiernew.equals(plugin.get("identifier"))){
						List<Map<String,String>> pluginstemp=dataBaseService.executeQuery("SELECT pluginid FROM jrun_plugins WHERE identifier='"+identifiernew+"' LIMIT 1");
						if(pluginstemp!=null&&pluginstemp.size()>0||!this.ispluginkey(identifiernew)){
							request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_identifier_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
					dataBaseService.runQuery("UPDATE jrun_plugins SET adminid='"+adminidnew+"', name='"+Common.addslashes(namenew)+"', identifier='"+Common.addslashes(identifiernew)+"', description='"+Common.addslashes(descriptionnew)+"', datatables='"+Common.addslashes(datatablesnew)+"', directory='"+Common.addslashes(directorynew)+"', copyright='"+Common.addslashes(copyrightnew)+"' WHERE pluginid='"+pluginid+"'",true);
				}else if("modules".equals(type)){
					Map<Integer,Map> modulesnew=new HashMap<Integer,Map>();
					int i=0;
					String newname=request.getParameter("newname").trim();
					Map newmodule=new HashMap();
					if(newname.length()!=0){
						newmodule.put("name", newname);
						newmodule.put("menu", request.getParameter("newmenu"));
						newmodule.put("url", request.getParameter("newurl"));
						newmodule.put("type", request.getParameter("newtype"));
						newmodule.put("adminid", request.getParameter("newadminid"));
						newmodule.put("displayorder", Common.intval(request.getParameter("neworder")));
						modulesnew.put(i, newmodule);
					}
					if(modules!=null&&modules.size()>0){
						Set<Entry<Integer,Map>> keys =modules.entrySet();
						for (Entry<Integer,Map> temp : keys) {
							Integer key = temp.getKey();
							String moduleid=request.getParameter("delete["+key+"]");
							if(moduleid==null){
								Map module=temp.getValue();
								Map modulenew=new HashMap();
								modulenew.put("name", request.getParameter("namenew["+key+"]"));
								modulenew.put("menu", request.getParameter("menunew["+key+"]"));
								modulenew.put("url", request.getParameter("urlnew["+key+"]"));
								modulenew.put("type", request.getParameter("typenew["+key+"]"));
								int adminidnew=Common.toDigit(request.getParameter("adminidnew["+key+"]"));
								modulenew.put("adminid", (adminidnew>=0&&adminidnew<=3)?adminidnew+"":module.get("adminid"));
								modulenew.put("displayorder",Common.intval(request.getParameter("ordernew["+key+"]")));
								modulesnew =sort(modulesnew,modulenew);
								i++;
							}
						}
					}
					List<String> namesarray=new ArrayList<String>();
					Set<Integer> keys=modulesnew.keySet();
					for (Integer key : keys) {
						Map module=modulesnew.get(key);
						String name=(String)module.get("name");
						if(!ispluginkey(name)){
							request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_modules_name_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}else if(namesarray.contains(name)){
							request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_modules_duplicated"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						namesarray.add(name);
						module.put("menu", ((String)module.get("menu")).trim());
						module.put("url", ((String)module.get("url")).trim());
						int adminidnew=Common.toDigit((String)module.get("adminid"));
						module.put("adminid", (adminidnew>=0&&adminidnew<=3)?adminidnew+"":1);
						int moduletype=Integer.valueOf((String)module.get("type"));
						if(moduletype==1||moduletype==5){
							if("".equals((String)module.get("url"))){
								request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_modules_url_invalid"));
								request.setAttribute("return", true);
								return mapping.findForward("message");
							}
						}else if(moduletype==2||moduletype==3||moduletype==6){
							if("".equals((String)module.get("menu"))){
								request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_modules_menu_invalid"));
								request.setAttribute("return", true);
								return mapping.findForward("message");
							}
							module.remove("url");
						}else if(moduletype==4){
							module.remove("menu");
							module.remove("url");
						}else{
							request.setAttribute("message", getMessage(request, "undefined_action"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
					dataBaseService.runQuery("UPDATE jrun_plugins SET modules='"+Common.addslashes(dataParse.combinationChar(modulesnew))+"' WHERE pluginid='"+pluginid+"'",true);
				}else if("hooks".equals(type)){
					String[] delete=request.getParameterValues("delete");
					if(delete!=null){
						dataBaseService.runQuery("DELETE FROM jrun_pluginhooks WHERE pluginid='"+pluginid+"' AND pluginhookid IN ("+Common.implodeids(delete)+")");
					}
					List<Map<String,String>> pluginhooks=dataBaseService.executeQuery("SELECT pluginhookid,title FROM jrun_pluginhooks WHERE pluginid='"+plugin.get("pluginid")+"'");
					List<String> hooktitles=new ArrayList<String>();
					if(pluginhooks!=null&&pluginhooks.size()>0){
						for (Map<String, String> pluginhook : pluginhooks) {
							String pluginhookid=pluginhook.get("pluginhookid");
							String val=request.getParameter("titlenew["+pluginhookid+"]");
							if(!ispluginkey(val)||hooktitles.contains(val)){
								request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_hooks_title_invalid"));
								request.setAttribute("return", true);
								return mapping.findForward("message");
							}
							hooktitles.add(val);
							dataBaseService.runQuery("UPDATE jrun_pluginhooks SET title='"+Common.htmlspecialchars(val)+"', available='"+Common.toDigit(request.getParameter("availablenew["+pluginhookid+"]"))+"' WHERE pluginid='"+pluginid+"' AND pluginhookid='"+pluginhookid+"'",true);
						}
					}
					String newtitle=request.getParameter("newtitle").trim();
					if(newtitle.length()>0){
						if(!ispluginkey(newtitle)||(hooktitles.size()>0&&hooktitles.contains(newtitle))){
							request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_hooks_title_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						dataBaseService.runQuery("INSERT INTO jrun_pluginhooks (pluginid, title, description, code, available) VALUES ('"+pluginid+"', '"+Common.htmlspecialchars(newtitle)+"', '', '', 0)",true);
					}
				}else if("vars".equals(type)){
					String[] delete=request.getParameterValues("delete");
					if(delete!=null){
						dataBaseService.runQuery("DELETE FROM jrun_pluginvars WHERE pluginid='"+pluginid+"' AND pluginvarid IN ("+Common.implodeids(delete)+")");
					}
					List<Map<String,String>> pluginvars=dataBaseService.executeQuery("SELECT pluginvarid FROM jrun_pluginvars WHERE pluginid='"+plugin.get("pluginid")+"' ORDER BY displayorder,pluginvarid");
					for (Map<String, String> pluginvar : pluginvars) {
						String pluginvarid=pluginvar.get("pluginvarid");
						String displayordernew=request.getParameter("displayordernew["+pluginvarid+"]");
						if(displayordernew!=null){
							dataBaseService.runQuery("UPDATE jrun_pluginvars SET displayorder='"+Common.range(Common.intval(displayordernew), 127, -128)+"' WHERE pluginid='"+pluginid+"' AND pluginvarid='"+pluginvarid+"'",true);
						}
					}
					request.setAttribute("pluginvars",pluginvars);
					String newtitle=Common.htmlspecialchars(request.getParameter("newtitle").trim());
					String newvariable=request.getParameter("newvariable").trim();
					if(newtitle.length()>0&&newvariable.length()>0){
						List<Map<String,String>> pluginvar=dataBaseService.executeQuery("SELECT pluginvarid FROM jrun_pluginvars WHERE pluginid='"+pluginid+"' AND variable='"+Common.addslashes(newvariable)+"' LIMIT 1");
						if(pluginvar!=null&&pluginvar.size()>0||Common.strlen(newvariable)>40||!ispluginkey(newvariable)){
							request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_var_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						dataBaseService.runQuery("INSERT INTO jrun_pluginvars (pluginid, displayorder, title, variable, type,value,extra) VALUES ('"+pluginid+"', '"+request.getParameter("newdisplayorder")+"', '"+Common.addslashes(newtitle)+"', '"+Common.addslashes(newvariable)+"', '"+request.getParameter("newtype")+"','','')",true);
					}
				}
				Cache.updateCache("plugins");
				ForumInit.initServletContext(servlet.getServletContext());
				request.setAttribute("message",getMessage(request, "a_extends_plugins_edit_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=pluginsedit&pluginid="+pluginid+"#"+type);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		request.setAttribute("plugin", plugin);
		Map<Integer,String> moduleoptions=new HashMap<Integer,String>();
		moduleoptions.put(1, getMessage(request, "a_extends_plugins_edit_modules_type_1"));
		moduleoptions.put(2, getMessage(request, "a_extends_plugins_edit_modules_type_2"));
		moduleoptions.put(3, getMessage(request, "a_extends_plugins_edit_modules_type_3"));
		moduleoptions.put(4, getMessage(request, "a_extends_plugins_edit_modules_type_4"));
		moduleoptions.put(5, getMessage(request, "a_extends_plugins_edit_modules_type_5"));
		moduleoptions.put(6, getMessage(request, "a_extends_plugins_edit_modules_type_6"));
		request.setAttribute("moduleoptions",moduleoptions);
		Map<Integer,String> moduleadmins=new HashMap<Integer,String>();
		moduleadmins.put(0, getMessage(request, "usergroups_system_0"));
		moduleadmins.put(1, getMessage(request, "usergroups_system_1"));
		moduleadmins.put(2, getMessage(request, "usergroups_system_2"));
		moduleadmins.put(3, getMessage(request, "usergroups_system_3"));
		request.setAttribute("moduleadmins",moduleadmins);
		Map<String, String> types = new HashMap<String, String>();
		types.put("number", getMessage(request, "a_forum_threadtype_edit_number"));
		types.put("text", getMessage(request, "a_forum_threadtype_edit_text"));
		types.put("textarea", getMessage(request, "a_forum_threadtype_edit_textarea"));
		types.put("radio", getMessage(request, "a_extends_plugins_edit_vars_type_radio"));
		types.put("select", getMessage(request, "a_forum_threadtype_edit_select"));
		types.put("color", getMessage(request, "a_extends_plugins_edit_vars_type_color"));
		request.setAttribute("types",types);
		request.setAttribute("modules",modules);
		List<Map<String,String>> pluginhooks=dataBaseService.executeQuery("SELECT pluginhookid, title, description, available FROM jrun_pluginhooks WHERE pluginid='"+plugin.get("pluginid")+"'");
		for (Map<String, String> pluginhook : pluginhooks) {
			pluginhook.put("description", Common.nl2br(Common.cutstr(pluginhook.get("description"), 50)));
			pluginhook.put("evalcode", "${hooks."+plugin.get("identifier")+"_"+pluginhook.get("title")+"};");
		}
		request.setAttribute("pluginhooks",pluginhooks);
		List<Map<String,String>> pluginvars=dataBaseService.executeQuery("SELECT * FROM jrun_pluginvars WHERE pluginid='"+plugin.get("pluginid")+"' ORDER BY displayorder,pluginvarid");
		request.setAttribute("pluginvars",pluginvars);
		return mapping.findForward("topluginsedit");
	}
	public ActionForward pluginhooks(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String pluginid=request.getParameter("pluginid");
		String pluginhookid=request.getParameter("pluginhookid");
		List<Map<String,String>> pluginhooks=dataBaseService.executeQuery("SELECT p.pluginid,ph.pluginhookid,ph.title,ph.description,ph.code FROM jrun_plugins p, jrun_pluginhooks ph WHERE p.pluginid='"+pluginid+"' AND ph.pluginid=p.pluginid AND ph.pluginhookid='"+pluginhookid+"'");
		if(pluginhooks==null||pluginhooks.size()==0) {
			request.setAttribute("message", getMessage(request, "undefined_action"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		Map<String,String> pluginhook=pluginhooks.get(0);
		try{
			if(submitCheck(request, "hooksubmit")){
				String descriptionnew=Common.htmlspecialchars(request.getParameter("descriptionnew").trim());
				String codenew=request.getParameter("codenew").trim();
				dataBaseService.runQuery("UPDATE jrun_pluginhooks SET description='"+Common.addslashes(descriptionnew)+"', code='"+Common.addslashes(codenew)+"' WHERE pluginid='"+pluginid+"' AND pluginhookid='"+pluginhookid+"'",true);
				ForumInit.initServletContext(servlet.getServletContext());
				request.setAttribute("message",getMessage(request, "a_extends_plugins_edit_hooks_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=pluginsedit&pluginid="+pluginid);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		request.setAttribute("pluginhook",pluginhook);
		return mapping.findForward("topluginhooks");
	}
	public ActionForward pluginvars(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String pluginid=request.getParameter("pluginid");
		String pluginvarid=request.getParameter("pluginvarid");
		List<Map<String,String>> pluginvars=dataBaseService.executeQuery("SELECT * FROM jrun_plugins p, jrun_pluginvars pv WHERE p.pluginid='"+pluginid+"' AND pv.pluginid=p.pluginid AND pv.pluginvarid='"+pluginvarid+"'");
		if(pluginvars==null||pluginvars.size()==0) {
			request.setAttribute("message", getMessage(request, "undefined_action"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		Map<String,String> pluginvar=pluginvars.get(0);
		try{
			if(submitCheck(request, "varsubmit")){
				String titlenew=Common.cutstr(Common.htmlspecialchars(request.getParameter("titlenew").trim()), 25, null);
				String descriptionnew=Common.cutstr(Common.htmlspecialchars(request.getParameter("descriptionnew").trim()), 255, null);
				String variablenew=request.getParameter("variablenew").trim();
				String extranew=Common.htmlspecialchars(request.getParameter("extranew").trim());
				if(titlenew.length()==0){
					request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_var_title_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}else if(!variablenew.equals(pluginvar.get("variable"))){
					List<Map<String,String>> pluginvartemp=dataBaseService.executeQuery("SELECT pluginvarid FROM jrun_pluginvars WHERE variable='"+Common.addslashes(variablenew)+"'");
					if(pluginvartemp!=null&&pluginvartemp.size()>0||variablenew.length()==0||Common.strlen(variablenew)>40||!ispluginkey(variablenew)){
						request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_var_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
				}
				dataBaseService.runQuery("UPDATE jrun_pluginvars SET title='"+Common.addslashes(titlenew)+"', description='"+Common.addslashes(descriptionnew)+"', type='"+request.getParameter("typenew")+"', variable='"+Common.addslashes(variablenew)+"', extra='"+Common.addslashes(extranew)+"' WHERE pluginid='"+pluginid+"' AND pluginvarid='"+pluginvarid+"'",true);
				Cache.updateCache("plugins");
				request.setAttribute("message",getMessage(request, "a_extends_plugins_edit_vars_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=pluginsedit&pluginid="+pluginid);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String, String> types = new HashMap<String, String>();
		types.put("number", getMessage(request, "a_forum_threadtype_edit_number"));
		types.put("text", getMessage(request, "a_forum_threadtype_edit_text"));
		types.put("textarea", getMessage(request, "a_forum_threadtype_edit_textarea"));
		types.put("radio", getMessage(request, "a_extends_plugins_edit_vars_type_radio"));
		types.put("select", getMessage(request, "a_forum_threadtype_edit_select"));
		types.put("color", getMessage(request, "a_extends_plugins_edit_vars_type_color"));
		request.setAttribute("types",types);
		request.setAttribute("pluginvar",pluginvar);
		return mapping.findForward("topluginvars");
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ActionForward pluginsconfig(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String export=request.getParameter("export");
		if(export!=null){
			List<Map<String,String>> plugins=dataBaseService.executeQuery("SELECT * FROM jrun_plugins WHERE pluginid='"+export+"'");
			if(plugins==null||plugins.size()==0){
				request.setAttribute("message", getMessage(request, "undefined_action"));
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Map<String,String> plugin=plugins.get(0);
			plugin.remove("pluginid");
			Map pluginMap=new HashMap();
			pluginMap.put("plugin", plugin);
			pluginMap.put("version", JspRunConfig.VERSION);
			List<Map<String,String>> pluginHooks = dataBaseService.executeQuery("SELECT available, title, description, code FROM jrun_pluginhooks WHERE pluginid='"+export+"'");
			if(pluginHooks!=null&&pluginHooks.size()>0){
				Map<Integer,Map<String,String>> hooks = new HashMap<Integer,Map<String,String>>(pluginHooks.size());
				for(Map<String,String> hook:pluginHooks){
					hooks.put(hooks.size(), hook);
				}
				pluginMap.put("hooks", hooks);
			}
			List<Map<String,String>> pluginVars=dataBaseService.executeQuery("SELECT displayorder, title, description, variable, type, value, extra FROM jrun_pluginvars WHERE pluginid='"+export+"'");
			if(pluginVars!=null&&pluginVars.size()>0){
				Map<Integer,Map<String,String>> vars = new HashMap<Integer,Map<String,String>>(pluginVars.size());
				for(Map<String,String> var:pluginVars){
					vars.put(vars.size(), var);
				}
				pluginMap.put("vars", vars);
			}
			exportData(request, response, "JspRun_plugin_"+plugin.get("identifier")+".txt", "Plugin Dump", pluginMap);
			return null;
		}
		try{
			if(submitCheck(request, "configsubmit")){
				String[] delete=request.getParameterValues("delete");
				if(delete!=null){
					String ids=Common.implodeids(delete);
					dataBaseService.runQuery("DELETE FROM jrun_plugins WHERE pluginid IN ("+ids+")");
					dataBaseService.runQuery("DELETE FROM jrun_pluginvars WHERE pluginid IN ("+ids+")");
				}
				List<Map<String,String>> plugins=dataBaseService.executeQuery("SELECT pluginid FROM jrun_plugins");
				if(plugins!=null&&plugins.size()>0){
					for (Map<String, String> plugin : plugins) {
						String pluginid=plugin.get("pluginid");
						dataBaseService.runQuery("UPDATE jrun_plugins SET available='"+Common.toDigit(request.getParameter("availablenew["+pluginid+"]"))+"' WHERE pluginid='"+pluginid+"'",true);
					}
				}
				String newname=request.getParameter("newname").trim();
				String newidentifier=request.getParameter("newidentifier").trim();
				if(newname.length()>0||newidentifier.length()>0){
					if(newname.length()==0){
						request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_name_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					plugins=dataBaseService.executeQuery("SELECT pluginid FROM jrun_plugins WHERE identifier='"+newidentifier+"' LIMIT 1");
					if(plugins!=null&&plugins.size()>0||!this.ispluginkey(newidentifier)){
						request.setAttribute("message", getMessage(request, "a_extends_plugins_edit_identifier_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					dataBaseService.runQuery("INSERT INTO jrun_plugins (name, identifier, available,description,datatables,directory,copyright,modules) VALUES ('"+Common.addslashes(Common.htmlspecialchars(newname))+"', '"+Common.addslashes(newidentifier)+"', '0','','','','','')", true);
				}
				Cache.updateCache("plugins");
				ForumInit.initServletContext(servlet.getServletContext());
				request.setAttribute("message",getMessage(request, "a_extends_plugins_edit_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=pluginsconfig");
				return mapping.findForward("message");
			} else if(submitCheck(request, "importsubmit")){
				String ignoreversion=request.getParameter("ignoreversion");
				String plugindata=request.getParameter("plugindata");
				plugindata=plugindata.replaceAll("(#.*\\s+)*", "");
				Map pluginMap=dataParse.characterParse(Base64.decode(plugindata, JspRunConfig.CHARSET),false);
				if(pluginMap==null||pluginMap.size()==0||pluginMap.get("plugin")==null){
					request.setAttribute("message", getMessage(request, "a_extends_plugins_import_data_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}else if(ignoreversion==null&&!pluginMap.get("version").toString().equals(JspRunConfig.VERSION)){
					request.setAttribute("message", getMessage(request, "a_extends_plugins_import_version_invalid",(String)pluginMap.get("version"),JspRunConfig.VERSION));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				Map<String,String> plugin=(Map<String,String>)pluginMap.get("plugin");
				String identifier=plugin.get("identifier");
				List<Map<String,String>> plugins=dataBaseService.executeQuery("SELECT pluginid FROM jrun_plugins WHERE identifier='"+identifier+"' LIMIT 1");
				if(plugins!=null&&plugins.size()>0){
					request.setAttribute("message", getMessage(request, "a_extends_plugins_import_identifier_duplicated",identifier));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				StringBuffer sql1=new StringBuffer();
				StringBuffer sql2=new StringBuffer();
				Iterator<Entry<String,String>> keys=plugin.entrySet().iterator();
				while(keys.hasNext()){
					Entry<String,String> e = keys.next();
					sql1.append(","+e.getKey());
					sql2.append(",'"+Common.addslashes(e.getValue())+"'");
				}
				int pluginid=dataBaseService.insert("INSERT INTO jrun_plugins ("+sql1.substring(1)+") VALUES ("+sql2.substring(1)+")", true);
				String[] temp={"hooks","vars"};
				for (String str : temp) {
					Map<Integer,Map<String,String>> pluginconfig=(Map<Integer,Map<String,String>>)pluginMap.get(str);
					if(pluginconfig!=null){
						Set<Entry<Integer,Map<String,String>>> configKeys=pluginconfig.entrySet();
						for(Entry<Integer,Map<String,String>> tempd:configKeys){
							sql1=new StringBuffer("pluginid");
							sql2=new StringBuffer("'"+pluginid+"'");
							Map<String,String> config=tempd.getValue();
							Set<String>keyse = config.keySet();
							for (String key : keyse) {
								sql1.append(","+key);
								sql2.append(",'"+Common.addslashes(config.get(key))+"'");
							}
							dataBaseService.runQuery("INSERT INTO jrun_plugin"+str+" ("+sql1+") VALUES ("+sql2+")");
						}
					}
				}
				Cache.updateCache("plugins");
				ForumInit.initServletContext(servlet.getServletContext());
				request.setAttribute("message",getMessage(request, "a_extends_plugins_import_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=pluginsconfig");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> plugins=dataBaseService.executeQuery("SELECT * FROM jrun_plugins order by pluginid");
		request.setAttribute("plugins", plugins);
		return mapping.findForward("topluginsconfig");
	}
	@SuppressWarnings("unchecked")
	public ActionForward showSearchEngine(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		String action = request.getParameter("do");
		if(action==null){
			request.setAttribute("message", getMessage(request, "undefined_action"));
			return mapping.findForward("message");
		}
		SearchEngineVO searchEngineVO = new SearchEngineVO();
		String variable = null;
		if(action.equals("google_config")){
			searchEngineVO.setEngineName("Google");
			variable = "google";
		}else if(action.equals("baidu_config")){
			searchEngineVO.setEngineName("Baidu");
			variable = "baidu";
		}else{
			request.setAttribute("message", getMessage(request, "undefined_action"));
			return mapping.findForward("message");
		}
		List<Map<String,String>> tempML = dataBaseService.executeQuery("SELECT value FROM jrun_settings WHERE variable='"+variable+"'");
		String tempS = tempML != null && tempML.size()>0 ? tempML.get(0).get("value") : ""; 
		Map<String,String> tempMap = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(tempS,false);
		searchEngineVO.setLanguage(tempMap.get("lang")==null?"":(String)tempMap.get("lang"));
		searchEngineVO.setShow(tempMap.get("searchbox")==null?0:Integer.valueOf(tempMap.get("searchbox")));
		searchEngineVO.setStatus(tempMap.get("status")==null?0:Integer.valueOf(tempMap.get("status")));
		request.setAttribute("valueObject", searchEngineVO);
		return mapping.findForward("go_searchEngine");
	}
	@SuppressWarnings("unchecked")
	public ActionForward searchEngine(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		try{
			if(submitCheck(request, "googlesubmit")){
				String engineName = request.getParameter("engineName");
				String status = request.getParameter("googlenew[status]");
				String lang = request.getParameter("googlenew[lang]");
				String[] searchboxArray = request.getParameterValues("googlenew[searchbox]");
				if(engineName==null||status==null||(engineName.equals("Google")&&lang==null)){
					request.setAttribute("message", getMessage(request, "undefined_action"));
					return mapping.findForward("message");
				}
				Integer searchbox = 0;
				if(searchboxArray!=null){
					for(int i = 0;i<searchboxArray.length;i++){
						try{
							searchbox += Integer.valueOf(searchboxArray[i]);
						}catch(NumberFormatException exception){
							request.setAttribute("message", getMessage(request, "undefined_action"));
							return mapping.findForward("message");
						}
					}
				}
				Map<String,String> maptemp = new HashMap<String,String>();
				maptemp.put("status", status);
				maptemp.put("lang", lang);
				maptemp.put("searchbox", String.valueOf(searchbox));
				String value = ((DataParse)BeanFactory.getBean("dataParse")).combinationChar(maptemp);
				String variable = null;
				if(engineName.equals("Google")){
					variable = "google";
				}else if(engineName.equals("Baidu")){
					variable= "baidu";
				}else{
					request.setAttribute("message", getMessage(request, "undefined_action"));
					return mapping.findForward("message");
				}
				Map<String,String> oldSettingMap = ForumInit.settings;
				Map<String,String> newSettingMap = new HashMap<String, String>();
				putValue(variable, value, oldSettingMap, newSettingMap);
				updateSettings(newSettingMap, oldSettingMap);
				try {
					Cache.updateCache(variable);
					List<Map<String,String>> tempMapList = dataBaseService.executeQuery("SELECT * FROM jrun_settings WHERE variable IN('google','baidu')");
					Map<String,String> datas = new HashMap<String, String>();
					if(tempMapList != null){
						for (Map<String, String> data : tempMapList) {
							datas.put(data.get("variable"), data.get("value"));
						}
					}
					Map<String,String> tempMap=dataParse.characterParse(datas.get("google"), false);
					oldSettingMap.put("google_status",tempMap.get("status"));
					oldSettingMap.put("google_searchbox",tempMap.get("searchbox"));
					tempMap = dataParse.characterParse(datas.get("baidu"), false);
					oldSettingMap.put("baidu_status",tempMap.get("status"));
					oldSettingMap.put("baidu_searchbox",tempMap.get("searchbox"));
					tempMap=null;
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("message", getMessage(request, "a_extends_plugins_search_s",engineName));
				return mapping.findForward("message");
			}else{
				request.setAttribute("message", getMessage(request, "undefined_action"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
	}
	public ActionForward tenpay(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		try{
			if(submitCheck(request, "tenpaysubmit")){
				String variables[] = {"ec_account","ec_key"};
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
					String value =request.getParameter(variable);
					this.putValue(variable, value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				request.setAttribute("message", getMessage(request, "a_extends_plugins_caifutong_s"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		return mapping.findForward("totenpay");
	}
	@SuppressWarnings("unchecked")
	public ActionForward ec_credit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		Map<String,String> oldSettings=ForumInit.settings;
		try{
			if(submitCheck(request, "creditsubmit")){
				Map<String,Object> ec_credits=new HashMap<String,Object>();
				ec_credits.put("maxcreditspermonth", Common.toDigit(request.getParameter("maxcreditspermonth")));
				String[] ranks=request.getParameterValues("rank");
				Map<Object,Object> rank=new TreeMap<Object,Object>();
				if(ranks!=null) {
					int size=ranks.length;
					for (int i = 1; i <= size; i++) {
						int mincredits=Common.toDigit(ranks[i-1]);
						if(i==1&&mincredits <= 0){
							request.setAttribute("message", getMessage(request, "a_extends_ecommerce_invalidcredit"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}else if(i>1&&mincredits<=((Integer)rank.get(i-1))){
							request.setAttribute("message", getMessage(request, "a_extends_ecommerce_must_larger",String.valueOf(i)));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						rank.put(i, mincredits);
					}
				} else {
					rank.put(1, 4);
					rank.put(2, 11);
					rank.put(3, 41);
					rank.put(4, 91);
					rank.put(5, 151);
					rank.put(6, 251);
					rank.put(7, 501);
					rank.put(8, 1001);
					rank.put(9, 2001);
					rank.put(10, 5001);
					rank.put(11, 10001);
					rank.put(12, 20001);
					rank.put(13, 50001);
					rank.put(14, 100001);
					rank.put(15, 200001);
				}
				ec_credits.put("rank", rank);
				Map<String,String> settings=new HashMap<String,String>();
				this.putValue("ec_credit", dataParse.combinationChar(ec_credits), oldSettings, settings);
				this.updateSettings(settings,oldSettings);
				request.setAttribute("message", getMessage(request, "a_extends_plugins_caifutong_s"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String ec_credit=oldSettings.get("ec_credit");
		Map<String,Object> ec_credits=new HashMap<String,Object>();
		Map<Object,Object> ranks=null;
		int maxcreditspermonth=6;
		if(ec_credit!=null&&ec_credit.length()>0){
			ec_credits=dataParse.characterParse(ec_credit, true);
			maxcreditspermonth=(Integer)ec_credits.get("maxcreditspermonth");
			ranks=(Map<Object,Object>)ec_credits.get("rank");
		}else {
			ranks=new TreeMap<Object,Object>();
			ranks.put(1, 4);
			ranks.put(2, 11);
			ranks.put(3, 41);
			ranks.put(4, 91);
			ranks.put(5, 151);
			ranks.put(6, 251);
			ranks.put(7, 501);
			ranks.put(8, 1001);
			ranks.put(9, 2001);
			ranks.put(10, 5001);
			ranks.put(11, 10001);
			ranks.put(12, 20001);
			ranks.put(13, 50001);
			ranks.put(14, 100001);
			ranks.put(15, 200001);
		}
		request.setAttribute("ranks",ranks);
		request.setAttribute("maxcreditspermonth",maxcreditspermonth);
		return mapping.findForward("toec_credit");
	}
	@SuppressWarnings("unchecked")
	public ActionForward orders(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		String orderstatus=request.getParameter("orderstatus");
		orderstatus = orderstatus == null ? "" : orderstatus;
		String orderid=request.getParameter("orderid");
		orderid = orderid == null ? "" : orderid;
		String users=request.getParameter("users");
		users = users == null ? "" : users.trim();
		String buyer=request.getParameter("buyer");
		buyer = buyer == null ? "" : buyer;
		String admin=request.getParameter("admin");
		admin = admin == null ? "" : admin;
		String sstarttime=request.getParameter("sstarttime");
		sstarttime = sstarttime == null ? "" : sstarttime;
		String sendtime=request.getParameter("sendtime");
		sendtime = sendtime == null ? "" : sendtime;
		String cstarttime=request.getParameter("cstarttime");
		cstarttime = cstarttime == null ? "" : cstarttime;
		String cendtime=request.getParameter("cendtime");
		cendtime = cendtime == null ? "" : cendtime;
		Map<String,String> oldSettings=ForumInit.settings;
		int creditstrans=Integer.valueOf(oldSettings.get("creditstrans"));
		int ec_ratio=Integer.valueOf(oldSettings.get("ec_ratio"));
		if(creditstrans==0||ec_ratio==0){
			request.setAttribute("message", getMessage(request, "a_extends_orders_disabled"));
			return mapping.findForward("message");
		}
		try{
			if(submitCheck(request, "ordersubmit")){
				String[] validate=request.getParameterValues("validate");
				if(validate!=null) {
					HttpSession session = request.getSession();
					String timeoffset=(String)session.getAttribute("timeoffset");
					int timestamp = (Integer)(request.getAttribute("timestamp"));
					SimpleDateFormat dateFormat = Common.getSimpleDateFormat((String)session.getAttribute("dateformat")+" "+(String)session.getAttribute("timeformat"), timeoffset);
					String confirmdate=Common.gmdate(dateFormat, timestamp);
					List<Map<String,String>> orders=dataBaseService.executeQuery("SELECT o.*, m.username FROM jrun_orders o LEFT JOIN jrun_members m USING (uid) WHERE orderid IN ('"+Common.implodeids(validate).replaceAll(",", "','")+"') AND status='1'");
					if(orders!=null&&orders.size()>0){
						StringBuffer orderids=new StringBuffer();
						String boardurl=(String)session.getAttribute("boardurl");
						String jsprun_user=(String)session.getAttribute("jsprun_userss");
						Map<String,Map> extcredits = dataParse.characterParse(oldSettings.get("extcredits"), true);
						Map creditstran=extcredits.get(creditstrans);
						String creditsformula=oldSettings.get("creditsformula");
						for (Map<String, String> order : orders) {
							dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"+"+order.get("amount")+",credits="+creditsformula+" WHERE uid='"+order.get("uid")+"'", true);
							dataBaseService.runQuery("INSERT INTO jrun_creditslog (uid, fromto, sendcredits, receivecredits, send, receive, dateline, operation) VALUES ('"+order.get("uid")+"', '"+Common.addslashes(order.get("username"))+"', '"+creditstrans+"', '"+creditstrans+"', '0', '"+order.get("amount")+"', '"+timestamp+"', 'AFD')", true);
							orderids.append(",'"+order.get("orderid")+"'");
							String submitdate=Common.gmdate(dateFormat, Integer.valueOf(order.get("submitdate")));
							String message=getMessage(request, "a_extends_addfunds_message1",order.get("orderid"),submitdate,confirmdate,order.get("price"),String.valueOf(creditstran.get("title")),order.get("amount"),String.valueOf(creditstran.get("unit")))+"[url="+boardurl+"memcp.jsp?action=creditslog&operation=creditslog]"+getMessage(request, "a_extends_addfunds_message2")+"[/url]"+getMessage(request, "a_extends_addfunds_message3");
							Common.sendpm(order.get("uid"), getMessage(request, "a_extends_addfunds_subject"), Common.addslashes(message), "0", "System Message", timestamp);
						}
						dataBaseService.runQuery("UPDATE jrun_orders SET status='3', admin='"+Common.addslashes(jsprun_user)+"', confirmdate='"+timestamp+"' WHERE orderid IN ("+orderids.substring(1)+")", true);
					}
				}
				request.setAttribute("message",getMessage(request, "a_extends_orders_validate_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=orders&searchsubmit=yes&orderstatus="+orderstatus+"&orderid="+orderid+"&users="+users+"&buyer="+buyer+"&admin="+admin+"&sstarttime="+sstarttime+"&sendtime="+sendtime+"&cstarttime="+cstarttime+"&cendtime="+cendtime+"&formHash="+Common.formHash(request));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		try{
			if(submitCheck(request, "searchsubmit",true)){
				String searchsubmit=request.getParameter("searchsubmit");
				HttpSession session = request.getSession();
				String timeoffset=(String)session.getAttribute("timeoffset");
				StringBuffer sql=new StringBuffer();
				if(orderstatus.length()>0){
					sql.append(" AND o.status='"+orderstatus+"'");
				}
				if(orderid.length()>0){
					sql.append(" AND o.orderid='"+orderid+"'");
				}
				if(users.length()>0){
					sql.append("AND m.username IN ('"+Common.addslashes(users).replaceAll(" ", "").replaceAll(",", "','")+"')");
				}
				if(buyer.length()>0){
					sql.append(" AND o.buyer='"+Common.addslashes(buyer)+"'");
				}
				if(admin.length()>0){
					sql.append(" AND o.admin='"+Common.addslashes(admin)+"'");
				}
				String pattern="yyyy-MM-dd";
				if(sstarttime.length()>0){
					sql.append(" AND o.submitdate>='"+(Common.dataToInteger(sstarttime,pattern,timeoffset))+"'");
				}
				if(sendtime.length()>0){
					sql.append(" AND o.submitdate<'"+(Common.dataToInteger(sendtime,pattern,timeoffset))+"'");
				}
				if(cstarttime.length()>0){
					sql.append(" AND o.confirmdate>='"+(Common.dataToInteger(cstarttime,pattern,timeoffset))+"'");
				}
				if(cendtime.length()>0){
					sql.append(" AND o.confirmdate<'"+(Common.dataToInteger(cendtime,pattern,timeoffset))+"'");
				}
				Members member = (Members) session.getAttribute("user");
				int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(oldSettings.get("topicperpage"));
				int page =Math.max(Common.intval(request.getParameter("page")),1);
				int ordercount=Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_orders o,jrun_members m WHERE m.uid=o.uid "+sql).get(0).get("count"));
				Map<String,Integer> multiInfo=Common.getMultiInfo(ordercount, tpp, page);
				page=multiInfo.get("curpage");
				int start_limit=multiInfo.get("start_limit");
				Map<String,Object> multi=Common.multi(ordercount, tpp, page, "admincp.jsp?action=orders&searchsubmit=yes&orderstatus="+orderstatus+"&orderid="+orderid+"&users="+users+"&buyer="+buyer+"&admin="+admin+"&sstarttime="+sstarttime+"&sendtime="+sendtime+"&cstarttime="+cstarttime+"&cendtime="+cendtime, 0, 10, true, false, null);
				request.setAttribute("multi", multi);
				List<Map<String,String>> orders = dataBaseService.executeQuery("SELECT o.*, m.username FROM jrun_orders o, jrun_members m WHERE m.uid=o.uid "+sql+" ORDER BY o.submitdate DESC LIMIT "+start_limit+","+tpp);
				if (orders != null && orders.size() > 0) {
					Map<Integer,Map> extcredits = dataParse.characterParse(oldSettings.get("extcredits"), true);
					Map creditstran=extcredits.get(creditstrans);
					request.setAttribute("title", creditstran.get("title"));
					String unit=" "+creditstran.get("unit");
					SimpleDateFormat dateFormat = Common.getSimpleDateFormat((String)session.getAttribute("dateformat")+" "+(String)session.getAttribute("timeformat"), timeoffset);
					for (Map<String,String> order : orders) {
						int status=Integer.valueOf(order.get("status"));
						switch (status) {
							case 1:
								order.put("statusDetail", getMessage(request, "a_extends_orders_search_status_pending"));
								break;
							case 2:
								order.put("statusDetail", "<b>"+getMessage(request, "a_extends_orders_search_status_auto_finished")+"</b>");
								break;
							case 3:
								order.put("statusDetail", "<b>"+getMessage(request, "a_extends_orders_search_status_manual_finished")+"</b><br />(<a href=\"space.jsp?action=viewpro&username="+Common.encode(order.get("admin"))+"\" target=\"_blank\">"+order.get("admin")+"</a>)");
								break;
						}
						order.put("amount",order.get("amount")+unit);
						order.put("submitdate", Common.gmdate(dateFormat, Integer.valueOf(order.get("submitdate"))));
						int confirmdate=Integer.valueOf(order.get("confirmdate"));
						order.put("confirmdate", confirmdate>0?Common.gmdate(dateFormat, confirmdate):"N/A");
					}
					request.setAttribute("orders", orders);
				}
				Map<String,String> statusselect=new HashMap<String,String>();
				statusselect.put(orderstatus, "selected");
				request.setAttribute("statusselect", statusselect);
				request.setAttribute("orderid", orderid);
				request.setAttribute("users", users);
				request.setAttribute("buyer", buyer);
				request.setAttribute("admin", admin);
				request.setAttribute("sstarttime", sstarttime);
				request.setAttribute("sendtime", sendtime);
				request.setAttribute("cstarttime", cstarttime);
				request.setAttribute("cendtime", cendtime);
				request.setAttribute("searchsubmit", searchsubmit);
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		return mapping.findForward("toorders");
	}
	@SuppressWarnings("unchecked")
	public ActionForward tradelog(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response){
		HttpSession session = request.getSession();
		Members member = (Members) session.getAttribute("user");
		Map<String,String> settings=ForumInit.settings;
		String temp=request.getParameter("filter");
		int filter=temp==null?-1:Math.max(Common.intval(request.getParameter("filter")),-1);
		String sqlfilter=filter>=0?"WHERE status='"+filter+"'":"";
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		Map<Integer,String> statuss = (Map<Integer,String>)Tenpayapi.trade_getstatus(0, -1,resources,locale);
		request.setAttribute("statuss", statuss);
		List<Map<String,String>> tradelog=dataBaseService.executeQuery("SELECT sum(price) as pricesum, sum(tax) as taxsum FROM jrun_tradelog status "+sqlfilter);
		request.setAttribute("tradelog", tradelog.get(0));
		List<Map<String,String>> count=dataBaseService.executeQuery("SELECT COUNT(*) as count FROM jrun_tradelog "+sqlfilter);
		int num = Integer.valueOf(count.get(0).get("count"));
		int page=Math.max(Common.intval(request.getParameter("page")),1);
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
		Map<String,Integer> multiInfo=Common.getMultiInfo(num, tpp, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		Map<String,Object> multi=Common.multi(num, tpp, page, "admincp.jsp?action=tradelog&filter="+filter, 0, 10, true, false, null);
		request.setAttribute("multi", multi);
		List<Map<String,String>> tradeLogs=dataBaseService.executeQuery("SELECT * FROM jrun_tradelog "+sqlfilter+" ORDER BY lastupdate DESC LIMIT "+start_limit+", "+tpp);
		if(tradeLogs!=null&&tradeLogs.size()>0){
			String timeoffset = (String)session.getAttribute("timeoffset");
			String dateformat = (String)session.getAttribute("dateformat");
			String timeformat = (String)session.getAttribute("timeformat");
			SimpleDateFormat sdf_all = Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for (Map<String, String> tradeLog : tradeLogs) {
				tradeLog.put("status", (String)Tenpayapi.trade_getstatus(Integer.valueOf(tradeLog.get("status")),resources,locale));
				tradeLog.put("lastupdate", Common.gmdate(sdf_all, Integer.parseInt(tradeLog.get("lastupdate"))));
				byte offline = Byte.parseByte(tradeLog.get("offline"));
				if(offline>0){
					tradeLog.put("tradeno", getMessage(request, "a_extends_tradeLog_offline"));
				}
			}
			request.setAttribute("tradeLogs", tradeLogs);
		}
		request.setAttribute("filter", filter);
		request.setAttribute("num", num);
		return mapping.findForward("totradelog");
	}
	private void putValue(String variable,String value,Map<String,String> oldSettings,Map<String,String> settings){
		if(value!=null&&!value.equals(oldSettings.get(variable))){
			settings.put(variable,value);
		}
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
	private boolean isplugindir(String dir){
		return dir==null||dir.length()==0||(!Common.matches(dir, "\\.\\.|[\\\\]+$")&&dir.endsWith("/"));
	}
	private boolean ispluginkey(String key) {
		return key!=null&&key.length()>0&&Common.matches(key, "^[a-zA-Z]+[a-zA-Z0-9_]*$");
	}
	@SuppressWarnings("unchecked")
	private Map<Integer,Map> sort(Map<Integer,Map> modules,Map modulenew){
		int displayordernew=(Integer)modulenew.get("displayorder");
		Map<Integer,Map> modulesnew=new HashMap<Integer,Map>();
		boolean caninsert=true;
		Set<Entry<Integer,Map>> keys=modules.entrySet();
		for (Entry<Integer,Map> temp : keys) {
			Map module=temp.getValue();
			int ordertemp=(Integer)module.get("displayorder");
			if(displayordernew<=ordertemp&&caninsert){
				caninsert=false;
				modulesnew.put(modulesnew.size(), modulenew);
			}
			modulesnew.put(modulesnew.size(), module);
		}
		if(caninsert){
			modulesnew.put(modulesnew.size(), modulenew);
		}
		return modulesnew;
	}
	private String getPhysicalMemorySize() throws Exception{
		ClassLoader cl=ClassLoader.getSystemClassLoader();
		cl.loadClass("com.sun.management.OperatingSystemMXBean");
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		return String.valueOf((operatingSystemMXBean.getTotalPhysicalMemorySize()/1024/1024));
	}
}