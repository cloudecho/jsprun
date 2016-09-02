package cn.jsprun.struts.action;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import cn.jsprun.domain.Members;
import cn.jsprun.utils.Base64;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.Config;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.Md5Token;
import cn.jsprun.utils.ZipUtil;
import cn.jsprun.vo.FieldVO;
import cn.jsprun.vo.TableStatusVO;
public class DataBaseManageAction extends BaseAction {
	public ActionForward updateForumCache(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		ServletContext context=servlet.getServletContext();
		ForumInit.initServletContext(context);
		String resultInfo=Cache.updateCache();
		if(resultInfo==null){
			resultInfo=getMessage(request, "a_system_update_cache_succeed");
		}else{
			resultInfo=getMessage(request, "a_system_update_cache_faild")+"<br>"+resultInfo;
		}
		request.setAttribute("message", resultInfo);
		return mapping.findForward("message");
	}
	public ActionForward toExport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List<String> tableNames = dataBaseService.findAllTableNames("jrun_");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String timeoffset = ForumInit.settings.get("timeoffset");
		request.setAttribute("tableNames", tableNames);
		request.setAttribute("randName",  Common.gmdate("yyMMdd", timestamp, timeoffset) + "_" + Common.getRandStr(8, false));
		return mapping.findForward("toExport");
	}
	@SuppressWarnings("unchecked")
	public ActionForward exportData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "exportsubmit",true)){
				String filename = request.getParameter("filename");
				if (filename.equals("")||filename.matches(".*\\.(exe|jsp|asp|aspx|cgi|fcgi|pl)$")) {
					request .setAttribute("message", getMessage(request, "a_system_database_export_filename_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				filename = filename.replaceAll("[/|\\\\|\\.]", "");
				List<String> tableNames = new ArrayList<String>();
				String type = request.getParameter("type");
				if ("jsprun".equals(type)) {
					tableNames = dataBaseService.findAllTableNames("jrun_");
				}else if ("custom".equals(type)) {
					String setup = request.getParameter("setup");
					if (setup == null || !"1".equals(setup)) {
						Map<String,String> setting=dataBaseService.executeQuery("SELECT value FROM jrun_settings WHERE variable='custombackup'").get(0);
						Map<Integer, String> map = dataParse.characterParse(setting.get("value"), false);
						if (map != null && map.size() > 0) {
							Iterator<Entry<Integer, String>> keys = map.entrySet().iterator();
							while (keys.hasNext()) {
								Entry<Integer, String> e = keys.next();
								tableNames.add(e.getValue());
							}
						}
					} else {
						String[] customtables = request.getParameterValues("customtables");
						String customtablesnew =null;
						if (customtables != null) {
							Map<Integer, String> map = new HashMap<Integer, String>(customtables.length);
							int length=customtables.length;
							for (int i = 0; i < length; i++) {
								map.put(i, customtables[i]);
								tableNames.add(customtables[i]);
							}
							customtablesnew =dataParse.combinationChar(map);
						}else{
							customtablesnew="";
						}
						dataBaseService.runQuery("REPLACE INTO jrun_settings (variable, value) VALUES ('custombackup', '"+customtablesnew+"')",true);
					}
				}
				if (tableNames.size() <= 0) {
					request.setAttribute("message", getMessage(request, "a_system_database_export_custom_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				String method = request.getParameter("method");
				long sizelimit = Common.toDigit(request.getParameter("sizelimit"));
				int extendins = Common.toDigit(request.getParameter("extendins"));
				String sqlcompat = request.getParameter("sqlcompat");
				String sqlcharset = request.getParameter("sqlcharset");
				String usehex = request.getParameter("usehex");
				int volume = Common.range(Common.intval(request.getParameter("volume")), 10000, 1);
				int tableid = Common.toDigit(request.getParameter("tableid"));
				int startfrom = Common.toDigit(request.getParameter("startfrom"));
				String realPath = JspRunConfig.realPath;
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				String encoded = timestamp + "," + JspRunConfig.VERSION + "," + type + ","+ method + "," + volume;
				String charset = null;
				String dumpcharset = null;
				switch (sqlcharset.length()) {
					case 3:
						charset = "GBK";
						dumpcharset = "gbk";
						break;
					case 4:
						charset = "UTF-8";
						dumpcharset = "utf8";
						break;
					default:
						charset = JspRunConfig.CHARSET;
						dumpcharset = JspRunConfig.CHARSET.replaceAll("-", "");
						break;
				}
				String version=dataBaseService.executeQuery("select version() as version").get(0).get("version");
				String setnames = (sqlcharset.length()>0&& version.compareTo("4.1") > 0 && (sqlcompat.length()==0 || sqlcompat.equals("MYSQL41"))) ? "SET NAMES '" + dumpcharset + "';\n\n": "";
				if (version.compareTo("4.1") > 0) {
					if (sqlcharset.length()>0) {
						dataBaseService.runQuery("SET NAMES '" + sqlcharset + "';\n\n",true);
					}
					if (sqlcompat.equals("MYSQL40")) {
						dataBaseService.runQuery("SET SQL_MODE='MYSQL40'",true);
					} else if (sqlcompat.equals("MYSQL41")) {
						dataBaseService.runQuery("SET SQL_MODE=''",true);
					}
				}
				Map<String,String> settings=ForumInit.settings;
				String backupdir = this.createBackupdir(realPath,settings);
				String backupfilename = backupdir + "/" + filename;
				if (method.equals("multivol")) {
					List<String> excepttables = new ArrayList<String>();
					excepttables.add("jrun_adminsessions");
					excepttables.add("jrun_failedlogins");
					excepttables.add("jrun_pmsearchindex");
					excepttables.add("jrun_relatedthreads");
					excepttables.add("jrun_rsscaches");
					excepttables.add("jrun_searchindex");
					excepttables.add("jrun_spacecaches");
					excepttables.add("jrun_sessions");
					StringBuffer sqldump = new StringBuffer();
					boolean complete = true;
					for (; complete && tableid < tableNames.size()&& sqldump.length() + 500 < sizelimit * 1000; tableid++) {
						Map map = dataBaseService.sqldumptable(excepttables, tableNames.get(tableid), startfrom, sqldump.length()+500,sizelimit*1000, complete, version, extendins, sqlcompat,dumpcharset, sqlcharset, usehex.equals("1") ? true: false);
						sqldump.append((StringBuffer)map.get("tabledump"));
						if (map.get("startfrom") != null) {
							startfrom = Common.toDigit(map.get("startfrom").toString());
						}
						if (map.get("complete") != null) {
							complete = (Boolean)map.get("complete");
						}
						if (complete) {
							startfrom = 0;
						}
					}
					if (!complete) {
						tableid--;
					}
					String dumpfileName = backupfilename + "-" + volume + ".sql";
					long usezip = Common.range(Common.intval(request.getParameter("usezip")), 2, 0);
					if (sqldump.length()>0) {
						String dateformat = settings.get("dateformat");
						String timeformat = settings.get("gtimeformat");
						String timeoffset = settings.get("timeoffset");
						String time = Common.gmdate(dateformat+" "+timeformat+" (z)", timestamp, timeoffset);
						StringBuffer dump=new StringBuffer();
						dump.append("# Identify: "+ Base64.encode(encoded, charset) + "\n");
						dump.append("# JspRun! Multi-Volume Data Dump Vol."+ volume+ "\n");
						dump.append("# Version: JspRun!_" + JspRunConfig.VERSION+"_" + charset+"\n");
						dump.append("# Time: "+ time+ "\n");
						dump.append("# Type: "+ type+ "\n");
						dump.append("# Table Prefix: jrun_\n");
						dump.append("# \n");
						dump.append("# JspRun! Home: http://www.jsprun.com\n");
						dump.append("# Please visit our website for newest infomation about JspRun!\n");
						dump.append("# --------------------------------------------------------\n\n\n");
						dump.append(setnames);
						File dumpfile = new File(realPath + dumpfileName);
						if (!dumpfile.exists()) {
							try {
								dumpfile.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						FileOutputStream fos=null;
						OutputStreamWriter osw=null;
						BufferedWriter bw = null;
						try {
							fos=new FileOutputStream(dumpfile);
							osw=new OutputStreamWriter(fos,charset);
							bw = new BufferedWriter(osw);
							bw.write(dump.toString());
							bw.write(sqldump.toString());
							bw.flush();
							osw.flush();
							fos.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							try {
								bw.close();
								osw.close();
								fos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}					
						}
						if (usezip==2) {
							try {
								dumpfileName = backupfilename + "-" + volume + ".zip";
								FileOutputStream zfos = new FileOutputStream(realPath+ dumpfileName);
								ZipOutputStream zos = new ZipOutputStream(zfos);
								ZipUtil.zipFile(dumpfile, zos);
								zos.close();
								fos.close();
								dumpfile.delete();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						dumpfile=null;
						request.setAttribute("message", getMessage(request, "a_system_database_export_multivol_redirect", String.valueOf(volume)));
						request.setAttribute("url_forward", request.getContextPath()+ "/database.do?action=exportData&exportsubmit=yes&type=" + type+ "&saveto=server&filename=" + filename+ "&method=" + method + "&sizelimit="+ sizelimit + "&volume=" + (++volume)+ "&tableid=" + tableid + "&startfrom="+ startfrom + "&extendins=" + extendins+ "&sqlcharset=" + sqlcharset + "&sqlcompat="+ sqlcompat + "&usehex=" + usehex + "&usezip="+ usezip+"&formHash="+Common.formHash(request));
						return mapping.findForward("message");
					} else {
						volume--;
						if (usezip==1) {
							try {
								String zipfilename = backupfilename + ".zip";
								FileOutputStream fos = new FileOutputStream(realPath+ zipfilename);
								ZipOutputStream zos = new ZipOutputStream(fos);
								for (int i = 1; i <= volume; i++) {
									File dumpfile = new File(realPath + backupfilename+ "-" + i + ".sql");
									ZipUtil.zipFile(dumpfile, zos);
									dumpfile.delete();
								}
								zos.close();
								fos.close();
								String resultInfo = getMessage(request, "a_system_database_export_zip_succeed", "<a href=\""+ request.getContextPath()+ zipfilename.replaceFirst("\\.", "") + "\">"+ zipfilename + "</a>");
								request.setAttribute("message", resultInfo);
								return mapping.findForward("message");
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							StringBuffer resultInfo = new StringBuffer(getMessage(request, "a_system_database_export_multivol_succeed" ,String.valueOf(volume)));
							for (int i = 1; i <= volume; i++) {
								dumpfileName = backupfilename + "-" + i+ (usezip==2 ? ".zip" : ".sql");
								resultInfo.append("<li><a href=\""+ request.getContextPath()+ dumpfileName.replaceFirst("\\.", "") + "\">"+ dumpfileName + "\n");
							}
							request.setAttribute("message", resultInfo);
							return mapping.findForward("message");
						}
					}
				}
				else {
					request.setAttribute("message",getMessage(request, "a_system_database_shell_fail"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		return this.toExport(mapping, form, request, response);
	}
	public ActionForward toImport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings=ForumInit.settings;
		String dbimport = settings.get("admincp_dbimport");
		if ("0".equals(dbimport)) {
			request.setAttribute("message",getMessage(request, "a_system_database_function_closed"));
			return mapping.findForward("message");
		}
		String realPath = JspRunConfig.realPath;
		String backupdir = this.createBackupdir(realPath,settings);
		File file = new File(realPath + backupdir);
		List<Map<String, String>> dumpfiles = new ArrayList<Map<String, String>>();
		File[] files = file.listFiles();
		if (files != null&&files.length>0) {
			HttpSession session=request.getSession();
			String timeoffset=(String)session.getAttribute("timeoffset");
			String timeformat=(String)session.getAttribute("timeformat");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for (File dumpfile : files) {
				if (dumpfile.isFile()) {
					String filename = dumpfile.getName();
					String jsprunversion = "";
					String type = "";
					String method = "";
					String volume = "";
					if (filename.matches(".*.sql$")) {
						String content =null;
						FileReader fr=null;
						BufferedReader br=null;
						try {
							fr=new FileReader(dumpfile);
							br = new BufferedReader(fr);
							content = br.readLine();							
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
						List<String> info = this.getStr(content,"^# Identify:\\s*(\\w+).*");
						if (info != null && info.size() > 0) {
							String fileInfo = info.get(0);
							fileInfo = Base64.decode(fileInfo.substring(fileInfo.indexOf(":") + 1).trim(), JspRunConfig.CHARSET);
							String[] infos = fileInfo.split(",");
							if (infos != null) {
								jsprunversion = infos[1];
								type = infos[2];
								method = infos[3];
								volume = infos[4];
							}
						} else {
							method = "shell";
						}
					} else if (filename.matches(".*.zip$")) {
						type = "zip";
					} else {
						continue;
					}
					Map<String, String> map = new HashMap<String, String>();
					map.put("filename", filename);
					map.put("filepath", backupdir.replace(".", "") + "/"+ filename);
					map.put("jsprunversion", jsprunversion);
					map.put("dateline", Common.gmdate(sdf_all, (int)(dumpfile.lastModified()/1000)));
					map.put("type", type);
					map.put("filesize", Common.sizeFormat(dumpfile.length()));
					map.put("method", method);
					map.put("volume", volume);
					dumpfiles.add(map);
				}
			}
		}
		request.setAttribute("backupdir", backupdir);
		request.setAttribute("dumpfiles", dumpfiles);
		request.setAttribute("version",JspRunConfig.VERSION);
		return mapping.findForward("toImport");
	}
	public ActionForward importData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("message", getMessage(request, "a_system_database_import_file_illegal"));
		return mapping.findForward("message");
	}
	public ActionForward importFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String realPath = JspRunConfig.realPath;
		try{
			if(submitCheck(request, "deletesubmit")){
				String[] delete = request.getParameterValues("delete");
				if (delete == null) {
					request.setAttribute("message", getMessage(request, "a_system_database_file_delete_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				} else {
					String resultInfo = null;
					boolean flag = true;
					for (String filePath : delete) {
						File file = new File(realPath + filePath);
						if (file.exists()) {
							if (!file.delete()) {
								flag = false;
								resultInfo = getMessage(request, "a_system_database_del_file_invalid");
								break;
							}
						} else {
							flag = false;
							resultInfo = getMessage(request, "a_system_database_file_no_exist");
							break;
						}
					}
					if (flag) {
						resultInfo = getMessage(request, "a_system_database_file_delete_succeed");
					}
					request.setAttribute("message", resultInfo);
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		try{
			if(submitCheck(request, "importsubmit",true)||submitCheck(request, "confirmed")){
				String server = request.getParameter("from");
				String autoimport = request.getParameter("autoimport");
				String datafile = "";
				String datafile_server = request.getParameter("datafile_server");
				if ("server".equals(server)) {
					datafile = realPath + datafile_server;
				}
				File dumpfile = new File(datafile);
				Map<String, String> dumpinfo = new HashMap<String, String>();
				StringBuffer sqldump = new StringBuffer();
				if (dumpfile.exists()) {
					FileInputStream fis=null;
					InputStreamReader isr=null;
					BufferedReader br = null;
					try {
						fis=new FileInputStream(dumpfile);
						isr=new InputStreamReader(fis,JspRunConfig.CHARSET);
						br = new BufferedReader(isr);
						String content = br.readLine();
						if (content != null) {
							List<String> info = this.getStr(content,"^# Identify:\\s*(\\w+).*");
							if (info != null && info.size() > 0) {
								String fileInfo = info.get(0);
								fileInfo = Base64.decode(fileInfo.substring(fileInfo.indexOf(":") + 1).trim(),JspRunConfig.CHARSET);
								String[] infos = fileInfo.split(",");
								if (infos != null) {
									dumpinfo.put("method", infos[3]);
									dumpinfo.put("volume", infos[4]);
								}
							}
						}
						while (content != null) {
							if (!content.matches("^#.*")) {
								sqldump.append(content+"\n");
							}
							content = br.readLine();
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
				} else {
					if (autoimport != null) {
						request.setAttribute("message", getMessage(request, "a_system_database_import_multivol_succeed"));
						return mapping.findForward("message");
					} else {
						request.setAttribute("message",getMessage(request, "a_system_database_import_file_illegal"));
						return mapping.findForward("message");
					}
				}
				if ("multivol".equals(dumpinfo.get("method"))) {
					if (sqldump.length()>0) {
						String[] sqls = sqldump.toString().split(";\n");
						if (sqls != null&&sqls.length>0) {
							Map<String,String> resultMap = null;
							Members members = (Members)request.getSession().getAttribute("members");
							String cMemberName = members == null?"":members.getUsername();
							String errorInfo = null;
							int timestamp = (Integer)(request.getAttribute("timestamp"));
							for (String sql : sqls) {
								sql=sql.trim();
								if(sql.length()>0){
									resultMap = dataBaseService.runQuery(sql);
									errorInfo = resultMap.get("error");
									if(resultMap!=null && errorInfo!=null){
										if(!"1062".equals(resultMap.get("errorCode"))){
											Log.writelog("errorlog",timestamp,timestamp+"\tMySQL\t"+cMemberName+"\t"+dumpfile+" : "+errorInfo +" - "+Common.cutstr(sql, 120));
										}
									}
								}
							}
						}
					}
					String delunzip = request.getParameter("delunzip");
					if (delunzip != null && dumpfile.exists()) {
						dumpfile.delete();
					}
					int index1 = datafile_server.lastIndexOf("-");
					int index2 = datafile_server.lastIndexOf("/");
					StringBuffer datafile_next = new StringBuffer(datafile_server);
					if (index1 > index2) {
						datafile_next = datafile_next.replace(datafile_next.lastIndexOf("-") + 1, datafile_next.lastIndexOf("."), String.valueOf(Integer.valueOf(datafile_next.substring(datafile_next.lastIndexOf("-") + 1, datafile_next.lastIndexOf("."))) + 1));
					}
					if ("1".equals(dumpinfo.get("volume"))) {
						request.setAttribute("msgtype", "form");
						request.setAttribute("message",getMessage(request, "a_system_database_import_multivol_prompt"));
						request.setAttribute("url_forward",request.getContextPath()+ "/database.do?action=importFile&from=server&datafile_server="+ datafile_next.toString()+ "&autoimport=yes&formHash="+Common.formHash(request)+"&importsubmit=yes"+ (request.getParameter("delunzip") != null ? "&delunzip=yes": ""));
						return mapping.findForward("message");
					} else if (autoimport != null) {
						request.setAttribute("message", getMessage(request, "a_system_database_import_multivol_redirect" ,dumpinfo.get("volume")));
						request.setAttribute("url_forward",request.getContextPath()+ "/database.do?action=importFile&from=server&datafile_server="+ datafile_next.toString()+ "&autoimport=yes&formHash="+Common.formHash(request)+"&importsubmit=yes"+ (request.getParameter("delunzip") != null ? "&delunzip=yes": ""));
						return mapping.findForward("message");
					} else {
						request.setAttribute("message", getMessage(request, "a_system_database_import_multivol_succeed"));
						return mapping.findForward("message");
					}
				} else if ("shell".equals(dumpinfo.get("method"))) {
				} else {
					request.setAttribute("message", getMessage(request, "a_system_database_import_format_illegal"));
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		return this.toImport(mapping, form, request, response);
	}
	public ActionForward importZipFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String realPath = JspRunConfig.realPath;
		String confirmed = request.getParameter("confirmed");
		String importsubmit = request.getParameter("importsubmit");
		if (importsubmit != null || confirmed != null) {
			String autoimport = request.getParameter("autoimport");
			String datafile_server = request.getParameter("datafile_server");
			String path = datafile_server.substring(0, datafile_server.lastIndexOf("/"));
			String zipFileName = realPath + datafile_server;
			try {
				File zipFile = new File(zipFileName);
				if (zipFile.exists()) {
					List<String> fileNames = ZipUtil.unZipFile(zipFile);
					if (fileNames != null && fileNames.size() > 0) {
						File dumpfile = new File(realPath + path + "/"+ fileNames.get(0));
						Map<String, String> dumpinfo = null;
						if (dumpfile.exists()) {
							FileReader fr=null;
							BufferedReader br=null;
							String content =null;
							try {
								fr = new FileReader(dumpfile);
								br = new BufferedReader(fr);
								content = br.readLine();
							} catch (Exception e) {
								e.printStackTrace();
							}finally{
								br.close();
								fr.close();
							}
							if (content != null) {
								List<String> info = this.getStr(content,"^# Identify:\\s*(\\w+).*");
								if (info != null && info.size() > 0) {
									String fileInfo = info.get(0);
									fileInfo = Base64.decode(fileInfo.substring(fileInfo.indexOf(":") + 1).trim(),JspRunConfig.CHARSET);
									String[] infos = fileInfo.split(",");
									if (infos != null) {
										dumpinfo = new HashMap<String, String>();
										dumpinfo.put("jsprunversion",infos[1]);
										dumpinfo.put("type", infos[2]);
										dumpinfo.put("method", infos[3]);
										dumpinfo.put("volume", infos[4]);
									}
								}
							}
						}
						String confirm = request.getParameter("confirm");
						if (confirm == null&& dumpinfo != null&& !dumpinfo.get("jsprunversion").equals(JspRunConfig.VERSION)) {
							request.setAttribute("msgtype", "form");
							request.setAttribute("message",getMessage(request, "a_system_database_import_confirm"));
							request.setAttribute("url_forward",request.getContextPath()+ "/database.do?action=importZipFile&from=server&datafile_server="+ datafile_server+ "&importsubmit=yes&confirm=yes");
							return mapping.findForward("message");
						}
						String info = "";
						if (dumpinfo != null) {
							info = datafile_server.substring(datafile_server.lastIndexOf("/") + 1)+ "<br/>"+getMessage(request, "version")+": "+ dumpinfo.get("jsprunversion")+ "<br/>"+getMessage(request, "type")+": "+ (dumpinfo.get("type").equals("jsprun") ? getMessage(request, "a_system_database_export_jsprun"): getMessage(request, "a_system_database_export_custom"))+ "<br/>"+getMessage(request, "a_system_database_method")+": "+ (dumpinfo.get("method").equals("multivol") ? getMessage(request, "a_system_database_multivol"): getMessage(request, "a_system_database_shell")) + "<br/><br/>";
						}
						int index1 = datafile_server.lastIndexOf("-");
						int index2 = datafile_server.lastIndexOf("/");
						StringBuffer datafile_next = new StringBuffer(datafile_server);
						String volume = null;
						if (index1 > index2) {
							volume = datafile_server.substring(datafile_server.lastIndexOf("-") + 1, datafile_server.lastIndexOf("."));
							datafile_next = datafile_next.replace(datafile_next.lastIndexOf("-") + 1,datafile_next.lastIndexOf("."),String.valueOf(Integer.valueOf(datafile_next.substring(datafile_next.lastIndexOf("-") + 1,datafile_next.lastIndexOf("."))) + 1));
						}
						if (autoimport==null&&(volume == null || !"1".equals(volume))) {
							String fileName = path + "/" + fileNames.get(0);
							request.setAttribute("msgtype", "form");
							request.setAttribute("message", getMessage(request, "a_system_database_import_unzip_confirm",info));
							request.setAttribute("url_forward",request.getContextPath()	+ "/database.do?action=importFile&from=server&datafile_server="+ fileName+ "&importsubmit=yes&delunzip=yes&formHash="+Common.formHash(request));
							return mapping.findForward("message");
						} else if ("1".equals(volume)) {
							request.setAttribute("msgtype", "form");
							request.setAttribute("message", getMessage(request, "a_system_database_import_multivol_unzip" ,info));
							request.setAttribute("url_forward",request.getContextPath()+ "/database.do?action=importZipFile&datafile_server="+ datafile_next.toString()+ "&autoimport=yes&importsubmit=yes&delunzip=yes");
							return mapping.findForward("message");
						} else if (autoimport != null) {
							request.setAttribute("message", getMessage(request, "a_system_database_import_multivol_unzip_redirect", volume));
							request.setAttribute("url_forward",request.getContextPath()+ "/database.do?action=importZipFile&datafile_server="+ datafile_next.toString()+ "&autoimport=yes&importsubmit=yes&delunzip=yes");
							return mapping.findForward("message");
						}
					} else {
						request.setAttribute("message",getMessage(request, "a_system_database_import_file_illegal"));
						return mapping.findForward("message");
					}
				} else {
					if (autoimport != null) {
						int index1 = datafile_server.lastIndexOf("-");
						int index2 = datafile_server.lastIndexOf("/");
						StringBuffer datafile = new StringBuffer(datafile_server);
						if (index1 > index2) {
							datafile = datafile.replace(datafile.lastIndexOf("-") + 1, datafile.lastIndexOf("."), "1");
						}
						datafile.replace(datafile.lastIndexOf("."), datafile.length(), ".sql");
						request.setAttribute("msgtype", "form");
						request.setAttribute("message",getMessage(request, "a_system_database_import_multivol_confirm"));
						request.setAttribute("url_forward",request.getContextPath()+ "/database.do?action=importFile&from=server&datafile_server="+ datafile.toString()+ "&autoimport=yes&importsubmit=yesimportsubmit=yes&delunzip=yes&formHash="+Common.formHash(request));
						request.setAttribute("cancelurl", "admincp.jsp?action=import");
						return mapping.findForward("message");
					} else {
						request.setAttribute("message",getMessage(request, "a_system_database_import_file_illegal"));
						return mapping.findForward("message");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.toImport(mapping, form, request, response);
	}
	private String createBackupdir(String realPath,Map<String,String> settings) {
		String backupdir = null;
		String oldbackupdir = settings.get("backupdir");
		if (oldbackupdir != null) {
			backupdir = "./forumdata/backup_"+oldbackupdir;
		}
		if (backupdir==null||!new File(realPath + backupdir).isDirectory()) {
			String randStr = Common.getRandStr(6, false);
			backupdir = "./forumdata/backup_" + randStr;
			try {
				if (new File(realPath + backupdir).mkdir()) {
					dataBaseService.runQuery("REPLACE INTO jrun_settings (variable, value) values ('backupdir', '"+randStr+"')");
					settings.put("backupdir", randStr);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return backupdir;
	}
	public ActionForward runquery(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "sqlsubmit")){
				String option = request.getParameter("option");
				String sql = null;
				if (option != null && "simple".equals(option)) {
					String queryselect = request.getParameter("queryselect");
					if (queryselect != null && !"".equals(queryselect)) {
						NodeList nl = this.getSimplequerie();
						if (nl != null) {
							for (int i = 0; i < nl.getLength(); i++) {
								Element element = (Element) nl.item(i);
								String id = element.getAttribute("id");
								if (id.equals(queryselect)) {
									StringBuffer sb = new StringBuffer(element.getAttribute("sql"));
									sb.replace(sb.indexOf("{"),sb.lastIndexOf("}") + 1, "jrun_");
									sql = sb.toString();
								}
							}
						}
					}
				} else {
					sql = request.getParameter("queries");
				}
				Map<String, String> map=null;
				if(sql!=null&&!"".equals(sql)){
					map = dataBaseService.runQuery(sql);
				}
				String num =null;
				if(map!=null){
					num = map.get("ok");
				}else{
					num="0";
				}
				if (num != null) {
					request.setAttribute("message", getMessage(request, "a_system_database_run_query_succeed", num));
				} else {
					request.setAttribute("message", getMessage(request, "a_system_database_run_query_invalid", map.get("error")));
				}
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<Integer, Map<Integer, String>> simplequeries = new TreeMap<Integer, Map<Integer, String>>();
		Map<Integer, String> upSimplequeries = new TreeMap<Integer, String>();
		NodeList nl = this.getSimplequerie();
		if (nl != null) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element element = (Element) nl.item(i);
				int id = Integer.parseInt(element.getAttribute("id"));
				int uid = Integer.parseInt(element.getAttribute("uid"));
				String comment = element.getAttribute("comment");
				if (0 == uid) {
					upSimplequeries.put(id, comment);
				} else {
					Map<Integer, String> map = simplequeries.get(uid);
					if (map == null) {
						map = new TreeMap<Integer, String>();
						simplequeries.put(uid, map);
					}
					map.put(id, comment);
				}
			}
		}
		request.setAttribute("simplequeries", simplequeries);
		request.setAttribute("upSimplequeries", upSimplequeries);
		return mapping.findForward("toRunquery");
	}
	public ActionForward optimize(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		long totalsize = 0;
		List<TableStatusVO> tableStatusVOs =null;
		try{
			if(submitCheck(request, "optimizesubmit")){
				String[] optimizetables = request.getParameterValues("optimizetables");
				if (optimizetables != null) {
					for (String obj : optimizetables) {
						dataBaseService.runQuery("OPTIMIZE TABLE " + obj,true);
					}
				}
				tableStatusVOs = dataBaseService.findTableStatus("SHOW TABLE STATUS LIKE 'jrun_%';");
				for (TableStatusVO statusVO : tableStatusVOs) {
					totalsize += statusVO.getData_length() + statusVO.getIndex_length();
				}
				request.setAttribute("type", "optimize");
				request.setAttribute("tableStatusVOs", tableStatusVOs);
				request.setAttribute("totalsize", Common.sizeFormat(totalsize));
				return mapping.findForward("toOptimize");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		tableStatusVOs = dataBaseService.findTableStatus("SHOW TABLE STATUS LIKE 'jrun_%';");
		for (TableStatusVO statusVO : tableStatusVOs) {
			if (statusVO.getData_free() > 0) {
				totalsize += statusVO.getData_length()+ statusVO.getIndex_length();
			}
		}
		request.setAttribute("tableStatusVOs", tableStatusVOs);
		request.setAttribute("totalsize", Common.sizeFormat(totalsize));
		return mapping.findForward("toOptimize");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toDbcheck(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		List<FieldVO> fieldVOs = dataBaseService.findTableFields("jrun_settings");
		if (fieldVOs == null || fieldVOs.size() <= 0) {
			request.setAttribute("info", "yes");
			request.setAttribute("dbcheck_permissions_invalid",getMessage(request, "a_system_dbcheck_permissions_invalid"));
			return mapping.findForward("toDbcheck");
		}
		if (!"yes".equals(request.getParameter("start"))) {
			request.setAttribute("info", "yes");
			request.setAttribute("dbcheck_checking", "admincp.jsp?action=moddbcheck&start=yes");
			return mapping.findForward("toDbcheck");
		}
		String realPath = JspRunConfig.realPath;
		File file = new File(realPath + "admin/jsprundb.md5");
		if (!file.exists()) {
			request.setAttribute("info", "yes");
			request.setAttribute("dbcheck_nofound_md5file", getMessage(request, "a_system_dbcheck_nofound_md5file"));
			return mapping.findForward("toDbcheck");
		} else {
			HttpSession session = request.getSession();
			String dbmd5 = null;
			String newdbmd5 = null;
			Map jsprundb = (Map) session.getAttribute("jsprundb");
			if (jsprundb == null) {
				StringBuffer jsprunContent = new StringBuffer();
				FileReader fr=null;
				BufferedReader br = null;
				try {
					fr=new FileReader(file);
					br = new BufferedReader(fr);
					do{
						jsprunContent.append(br.readLine());
					}while (br.ready());
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
				dbmd5 = jsprunContent.substring(0, 32);
				newdbmd5 = Md5Token.getInstance().getLongToken(String.valueOf(jsprunContent.substring(34).length()));
				jsprundb = dataParse.characterParse(jsprunContent.substring(34),false);
				session.setAttribute("dbmd5", dbmd5);
				session.setAttribute("newdbmd5", newdbmd5);
				session.setAttribute("jsprundb", jsprundb);
			}
			dbmd5 = session.getAttribute("dbmd5").toString();
			newdbmd5 = session.getAttribute("newdbmd5").toString();
			if (!dbmd5.equals(newdbmd5)) {
				request.setAttribute("info", "yes");
				request.setAttribute("dbcheck_modify_md5file",getMessage(request, "a_system_dbcheck_file_invalid"));
			}
			Map<Integer, String> settingsdata = (Map<Integer, String>) jsprundb.get(1);
			jsprundb = (Map)((Map) jsprundb.get(0)).get(0);
			List<Map<String,String>> settings=dataBaseService.executeQuery("SELECT variable FROM jrun_settings WHERE SUBSTRING(variable, 1, 9)<>'jswizard_' ORDER BY variable");
			List<String> settingsdatanew = new ArrayList<String>();
			if (settings != null) {
				for (Map<String,String> setting : settings) {
					settingsdatanew.add(setting.get("variable"));
				}
			}
			StringBuffer settingsdeldata=new StringBuffer(); 
			if (settingsdata != null) {
				Iterator<Entry<Integer, String>> key = settingsdata.entrySet().iterator();
				while (key.hasNext()) {
					Entry<Integer, String>e = key.next();
					String variable = e.getValue();
					if (!settingsdatanew.contains(variable)) {
						settingsdeldata.append(","+variable);
					}
				}
			}
			request.setAttribute("settingsdeldata",settingsdeldata.length() > 0 ? settingsdeldata.substring(1) :null);
			Config config = new Config(realPath + "/config.properties");
			String version=config.getValue("version");
			StringBuffer charseterrors =new StringBuffer();
			if (version.compareTo("4.1") > 0) {
				String dbcharset = config.getValue("dbcharset");
				if (dbcharset == null || dbcharset.equals("")) {
					dbcharset=JspRunConfig.CHARSET.replaceAll("-", "");
				}
				dbcharset = dbcharset.toUpperCase();
				List<TableStatusVO> tableStatusVOs = dataBaseService.findTableStatus("SHOW TABLE STATUS LIKE 'jrun_%';");
				for (TableStatusVO statusVO : tableStatusVOs) {
					if(statusVO.getCollation()!=null){
						int index=statusVO.getCollation().indexOf("_");
						if(index>=0){
							String tabledbcharset = statusVO.getCollation().substring(0,index);
							if (!dbcharset.equals(tabledbcharset.toUpperCase())) {
								charseterrors.append("<span style='float: left; width: 33%'>"+statusVO.getName() + "(" + tabledbcharset+ ")</span>");
							}
						}
					}
				}
				request.setAttribute("dbcharset", dbcharset);
				request.setAttribute("charseterrors",charseterrors.length()> 0 ? charseterrors.toString() : null);
			}
			boolean installexists=new File(realPath+"install/jsprun.sql").exists();
			List<String> tablenew = dataBaseService.findAllTableNames("jrun_");
			StringBuffer missingtables = new StringBuffer();
			Map<String, List<FieldVO>> addsMap = new TreeMap<String, List<FieldVO>>();
			Map<String, List<FieldVO>> modifysMap = new TreeMap<String, List<FieldVO>>();
			Map<String, List<String>> delsMap = new TreeMap<String, List<String>>();
			if (jsprundb != null) {
				Set<String> key = jsprundb.keySet();
				for (String obj : key) {
					String tableName = "jrun_" + obj;
					if (tablenew.contains(tableName)) {
						List<FieldVO> fieldsnew = dataBaseService.findTableFields(tableName);
						Map<String, Map<String, String>> jsprunfields = (Map<String, Map<String, String>>) jsprundb.get(obj);
						Set<String> jsprunfieldNames = jsprunfields.keySet();
						if (jsprunfieldNames != null) {
							for (String fieldName : jsprunfieldNames) {
								boolean flag = false;
								if (fieldsnew != null) {
									for (FieldVO fieldVO : fieldsnew) {
										if (fieldName.equals(fieldVO.getField())) {
											flag = true;
											break;
										}
									}
								}
								if (!flag) {
									List<String> fieldlists = delsMap.get(obj);
									if (fieldlists == null) {
										fieldlists = new ArrayList<String>();
									}
									fieldlists.add(fieldName);
									delsMap.put(obj, fieldlists);
								}
							}
						}
						if (fieldsnew != null) {
							for (FieldVO fieldVO : fieldsnew) {
								if (fieldVO.getAllowNull() == null|| fieldVO.getAllowNull().equals("")) {
									fieldVO.setAllowNull("NO");
								}
								if (fieldVO.getDefaultValue() == null|| fieldVO.getDefaultValue().equals("0")|| fieldVO.getDefaultValue().equals("0.00")) {
									fieldVO.setDefaultValue("");
								}
								List<FieldVO> fieldlists = null;
								if (jsprunfieldNames != null&& jsprunfieldNames.contains(fieldVO.getField())) {
									Map<String, String> jsprunfield = jsprunfields.get(fieldVO.getField());
									if (version.compareTo("4.1") < 0&& tableName == "sessions"&& fieldVO.getField() == "sid") {
										fieldVO.setType(fieldVO.getType().replaceAll(" binary", ""));
									}
									if (!jsprunfield.get("Type").equals(fieldVO.getType())|| !jsprunfield.get("Null").equals(fieldVO.getAllowNull())|| !jsprunfield.get("Extra").equals(fieldVO.getExtra())|| !jsprunfield.get("Default").equals(fieldVO.getDefaultValue())) {
										fieldlists = modifysMap.get(obj);
										if (fieldlists == null) {
											fieldlists = new ArrayList<FieldVO>();
										}
										fieldlists.add(fieldVO);
										modifysMap.put(obj, fieldlists);
									}
								} else {
									fieldlists = addsMap.get(obj);
									if (fieldlists == null) {
										fieldlists = new ArrayList<FieldVO>();
									}
									fieldlists.add(fieldVO);
									addsMap.put(obj, fieldlists);
								}
							}
						}
					} else {
						missingtables.append("<span style='float:left;width:33%'>"+((installexists ? "<input name='missingtable' type='checkbox' class='checkbox' value='"+obj+"'>" : "")+tableName)+"</span>");
					}
				}
			}
			request.setAttribute("tablepre", "jrun_");
			request.setAttribute("jsprundb", jsprundb);
			request.setAttribute("missingtables",missingtables.length() > 0 ? missingtables.toString() : null);
			request.setAttribute("addsMap", addsMap.size() > 0 ? addsMap : "");
			session.setAttribute("modifysMap", modifysMap);
			session.setAttribute("delsMap", delsMap);
			request.setAttribute("hasErrorField", modifysMap.size() > 0	|| delsMap.size() > 0 ? true : false);
			if (charseterrors.length() == 0 && missingtables.length() == 0&& addsMap.size() == 0 && modifysMap.size() == 0&& delsMap.size() == 0 && settingsdeldata.length() == 0) {
				request.setAttribute("info", "yes");
				request.setAttribute("dbcheck_ok", getMessage(request, "a_system_dbcheck_db_ok"));
				return mapping.findForward("toDbcheck");
			}
			return mapping.findForward("toDbcheck");
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward dbcheck(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String[] repair = request.getParameterValues("repair");
		String[] repairtable = request.getParameterValues("repairtable");
		String[] missingtable = request.getParameterValues("missingtable");
		String settingsdel = request.getParameter("setting[del]");
		if (repair == null && repairtable == null && missingtable == null&& !"1".equals(settingsdel)) {
			return this.toDbcheck(mapping, form, request, response);
		}
		try{
			if(submitCheck(request, "repairsubmit")){
				StringBuffer errors=new StringBuffer();
				HttpSession session = request.getSession();
				Map jsprundb = (Map) session.getAttribute("jsprundb");
				Map<Integer, String> settingsdata = (Map<Integer, String>) jsprundb.get(1);
				jsprundb = (Map) ((Map) jsprundb.get(0)).get(0);
				if (repairtable != null) {
					for (String tableName : repairtable) {
						Map<String, List<FieldVO>> modifysMap = (Map<String, List<FieldVO>>) session.getAttribute("modifysMap");
						Map<String, List<String>> delsMap = (Map<String, List<String>>) session.getAttribute("delsMap");
						List<FieldVO> fieldVO = modifysMap.get(tableName);
						if (fieldVO != null) {
							for (FieldVO obj : fieldVO) {
								Map<String, String> field = (Map) ((Map) jsprundb.get(tableName)).get(obj.getField());
								String error = dataBaseService.runQuery("ALTER TABLE jrun_"+ tableName+ " MODIFY COLUMN "+ field.get("Field")+ " "+ field.get("Type")+ " "+ (field.get("Null").equals("NO") ? "NOT NULL": "") + " " + field.get("Extra") + " "+((field.get("Null").equals("")&& field.get("Default").equals("")|| this.getStr(field.get("Type"), "text").size() == 0 || this.getStr(field.get("Extra"), "auto_increment").size() == 0) ? "": "DEFAULT '" + field.get("Default") + "';")).get("error");
								if (error != null) {
									errors.append("<br /><br />" + error);
								}
							}
						}
						List<String> fieldNames = delsMap.get(tableName);
						if (fieldNames != null) {
							for (String fieldName : fieldNames) {
								Map<String, String> field = (Map) ((Map) jsprundb.get(tableName)).get(fieldName);
								String error = dataBaseService.runQuery("ALTER TABLE jrun_"+ tableName+ " ADD COLUMN "+ field.get("Field")+ " "+ field.get("Type")+ " "+ (field.get("Null").equals("NO") ? "NOT NULL": "") + " " + field.get("Extra") + " "+((field.get("Null").equals("")&& field.get("Default").equals("")|| this.getStr(field.get("Type"), "text").size() == 0 || this.getStr(field.get("Extra"), "auto_increment").size() == 0) ? "": "DEFAULT '" + field.get("Default") + "';")).get("error");
								if (error != null) {
									errors.append("<br /><br />" + error);
								}
							}
						}
					}
				}
				if (repair != null) {
					for (String str : repair) {
						String[] fieldInfo = str.split("\\|");
						String tableName = fieldInfo[0];
						String fieldName = fieldInfo[1];
						String type = fieldInfo[2];
						Map<String, String> field = (Map) ((Map) jsprundb.get(tableName)).get(fieldName);
						String error = dataBaseService.runQuery("ALTER TABLE jrun_" + tableName + " "+ (type.equals("add") ? "ADD COLUMN" : "MODIFY COLUMN")+ " " + field.get("Field") + " " + field.get("Type")+ " "+ (field.get("Null").equals("NO") ? "NOT NULL" : "")+ " " + field.get("Extra") + " "+((field.get("Null").equals("")&& field.get("Default").equals("")|| this.getStr(field.get("Type"), "text").size() == 0 || this.getStr(field.get("Extra"), "auto_increment").size() == 0) ? "": "DEFAULT '" + field.get("Default") + "';")).get("error");
						if (error != null) {
							errors.append("<br /><br />" + error);
						}
					}
				}
				if (missingtable != null) {
					StringBuffer jsprunContent = (StringBuffer) session.getAttribute("jsprunContent");
					if (jsprunContent == null) {
						String realPath = JspRunConfig.realPath;
						File file = new File(realPath + "./install/jsprun.sql");
						FileReader fr=null;
						BufferedReader br = null;
						try {
							jsprunContent = new StringBuffer();
							fr=new FileReader(file);
							br = new BufferedReader(fr);
							String line = br.readLine();
							while (line != null) {
								jsprunContent.append(line);
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
						session.setAttribute("jsprunContent", jsprunContent);
					}
					for (String tableName : missingtable) {
						List<String> strs = this.getStr(jsprunContent.toString(), "CREATE TABLE jrun_" + tableName + "\\s\\(.+?;");
						if (strs != null && strs.size() > 0) {
							String error = dataBaseService.runQuery(strs.get(0)).get("error");
							if (error != null) {
								errors.append("<br /><br />" + error);
							}
						}
					}
				}
				if ("1".equals(settingsdel)) {
					List<Map<String,String>> settings=dataBaseService.executeQuery("SELECT variable FROM jrun_settings WHERE SUBSTRING(variable, 1, 9)<>'jswizard_' ORDER BY variable");
					List<String> settingsdatanew = new ArrayList<String>();
					if (settings != null) {
						for (Map<String,String> setting : settings) {
							settingsdatanew.add(setting.get("variable"));
						}
					}
					if (settingsdata != null) {
						Iterator<Entry<Integer, String>> key = settingsdata.entrySet().iterator();
						while (key.hasNext()) {
							Entry<Integer, String> e = key.next();
							String variable = e.getValue().trim();
							if (!settingsdatanew.contains(variable)) {
								String error = dataBaseService.runQuery( "insert into jrun_settings (variable, value) values('"+ variable + "','')").get("error");
								if (error != null) {
									errors.append("<br /><br />" + error);
								}
							}
						}
					}
				}
				request.setAttribute("info", "yes");
				if (errors.length()>0) {
					request.setAttribute("dbcheck_repair_error", errors.toString());
				} else {
					request.setAttribute("dbcheck_repair_completed", "admincp.jsp?action=moddbcheck");
				}
				return mapping.findForward("toDbcheck");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		return this.toDbcheck(mapping, form, request, response);
	}
	private List<String> getStr(String content, String regex) {
		List<String> strList = new ArrayList<String>();
		PatternCompiler compiler = new Perl5Compiler();
		Pattern pattern = null;
		try {
			pattern = compiler.compile(regex);
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		PatternMatcher m = new Perl5Matcher();
		if (m.contains(content, pattern)) {
			MatchResult result = m.getMatch();
			for (int i = 0; i < result.groups(); i++) {
				strList.add(result.group(i));
			}
		}
		return strList;
	}
	@SuppressWarnings("finally")
	private NodeList getSimplequerie() {
		String simplequerie = JspRunConfig.realPath+ "admin/page/counter/quickqueries.xml";
		File file = new File(simplequerie);
		NodeList nl = null;
		try {
			Document document =  DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
			nl = document.getElementsByTagName("simplequerie");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return nl;
		}
	}
}