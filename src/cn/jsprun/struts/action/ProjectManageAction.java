package cn.jsprun.struts.action;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Members;
import cn.jsprun.utils.Base64;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.JspRunConfig;
public class ProjectManageAction extends BaseAction {
	@SuppressWarnings("deprecation")
	public ActionForward toProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		boolean isfounder = (Boolean)request.getAttribute("isfounder");
		if(!isfounder){
			request.setAttribute("message", getMessage(request, "a_setting_not_createmen_access"));
			return mapping.findForward("message");
		}
		String export = request.getParameter("export");
		if(export==null){
			String type=request.getParameter("type");
			String typeadd = null;
			String selecttype = null;
			if(type!=null&&!"".equals(type)){
				typeadd = "WHERE type='"+type+"'";
				selecttype = "&amp;type="+type;
			}
			else{
				typeadd = "";
				selecttype = "";
			}
			int projectnum=Common.toDigit(dataBaseService.executeQuery("SELECT COUNT(*) AS count FROM jrun_projects "+typeadd).get(0).get("count"));
			int page=Math.max(Common.intval(request.getParameter("page")), 1);
			int perpage = 10;
			Map<String,Integer> multiInfo=Common.getMultiInfo(projectnum, perpage, page);
			page=multiInfo.get("curpage");
			int start_limit=multiInfo.get("start_limit");
			Map<String,Object> multi=Common.multi(projectnum, perpage, page, "admincp.jsp?action=project"+selecttype, 0, 10, true, false, null);
			request.setAttribute("multi", multi);
			List<Map<String,String>> projects=dataBaseService.executeQuery("SELECT id, name, type, description FROM jrun_projects "+typeadd+" LIMIT "+start_limit+","+perpage);
			request.setAttribute("projects", projects);
			request.setAttribute("type", type);
			return mapping.findForward("toProject");
		}else{
			List<Map<String,String>> projects=dataBaseService.executeQuery("SELECT name, type, description, value FROM jrun_projects WHERE id='"+export+"'");
			if(projects==null||projects.size()==0){
				request.setAttribute("message", getMessage(request, "a_system_project_import_f"));
				return mapping.findForward("message");
			}
			Map<String,String> project=projects.get(0);
			project.put("version",JspRunConfig.VERSION);
			exportData(request, response, "JspRun_project_"+Common.encodeText(request, (String)project.get("name"))+".txt", "Project Dump ("+project.get("type")+")", project);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward project(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "projectsubmit")||submitCheck(request, "importsubmit")){
				String importsubmit = request.getParameter("importsubmit");
				if(importsubmit==null){
					String []delete=request.getParameterValues("delete");
					if(delete!=null){
						dataBaseService.runQuery("DELETE FROM jrun_projects WHERE id IN ("+Common.implodeids(delete)+")", true);
					}
					String[] ids=request.getParameterValues("ids");
					if(ids!=null)
					{
						for (String id : ids) {
							String name=request.getParameter("name["+id+"]");
							String description=request.getParameter("description["+id+"]");
							dataBaseService.runQuery("UPDATE jrun_projects SET name='"+Common.addslashes((name.length()>50?name.substring(0,50):name))+"', description='"+Common.addslashes(description)+"' WHERE id='"+id+"'",true);
						}
					}
					request.setAttribute("message", getMessage(request, "a_system_project_update_forum"));
					request.setAttribute("url_forward", "admincp.jsp?action=project");
					return mapping.findForward("message");
				}else{
					String projectdata=request.getParameter("projectdata");
					projectdata=projectdata.replaceAll("(#.*\\s+)*", "");
					Map<String,String> project=dataParse.characterParse(Base64.decode(projectdata, JspRunConfig.CHARSET), false);
					if(project==null||project.size()==0){
						request.setAttribute("message", getMessage(request, "a_system_project_import_data_invalid"));
						request.setAttribute("return", true);
					}else if(!project.get("version").equals(JspRunConfig.VERSION)){
						request.setAttribute("message", getMessage(request, "a_system_project_export_version",project.get("version"),JspRunConfig.VERSION));
						request.setAttribute("return", true);
					}else{
						dataBaseService.runQuery("INSERT INTO jrun_projects (name, type, description, value) VALUES ('"+Common.addslashes(project.get("name"))+"', '"+project.get("type")+"', '"+Common.addslashes(project.get("description"))+"', '"+Common.addslashes(project.get("value"))+"')", true);
						request.setAttribute("message", getMessage(request, "a_system_project_import_s"));
						request.setAttribute("url_forward", "admincp.jsp?action=project");
					}
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=project");
		return null;
	}
	public ActionForward toProjectapply(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String type=request.getParameter("type");
		String projectid=request.getParameter("projectid");
		if("forum".equals(type)){
			HttpSession session=request.getSession();
			short groupid = (Short)session.getAttribute("jsprun_groupid");
			Members member = (Members)session.getAttribute("user");
			request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
		}
		else if("group".equals(type)){
			List<Map<String,String>> usergroups=dataBaseService.executeQuery("SELECT groupid, grouptitle FROM jrun_usergroups ORDER BY creditshigher");
			request.setAttribute("usergroups", usergroups);
		}
		else if("extcredit".equals(type)){
			request.setAttribute("projectid", projectid);
			return mapping.findForward("toCredit");
		}
		List<Map<String,String>> projects=dataBaseService.executeQuery("SELECT id, name, type FROM jrun_projects WHERE type='"+type+"'");
		request.setAttribute("projects", projects);
		request.setAttribute("type", type);
		request.setAttribute("projectid", projectid);
		return mapping.findForward("toProjectapply");
	}
	@SuppressWarnings({ "static-access", "unchecked" })
	public ActionForward projectapply(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "applysubmit")){
				String[] targetid=request.getParameterValues("targetid");
				if(targetid==null){
					request.setAttribute("message", getMessage(request, "a_system_project_target_item"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				String projectid=request.getParameter("projectid");
				List<Map<String,String>> projects=dataBaseService.executeQuery("SELECT type, value FROM jrun_projects WHERE id='"+projectid+"'");
				if(projects!=null&&projects.size()>0){
					Map<String,String> values = dataParse.characterParse(projects.get(0).get("value"),false);
					if(values==null){
						request.setAttribute("message", getMessage(request, "a_system_project_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					String applyids=Common.implodeids(targetid);
					String type=request.getParameter("type");
					if("forum".equals(type)){
						String[] table_forum_columns={"styleid", "allowsmilies", "allowhtml", "allowbbcode", "allowimgcode", "allowanonymous", "allowshare", "allowpostspecial", "alloweditrules", "allowpaytoauthor", "alloweditpost", "allowspecialonly", "modnewposts", "recyclebin", "jammer", "forumcolumns", "threadcaches", "disablewatermark", "autoclose", "simple"};
						String[] table_forumfield_columns={"attachextensions", "postcredits", "replycredits", "digestcredits", "postattachcredits", "getattachcredits", "viewperm", "postperm", "replyperm", "getattachperm", "postattachperm", "modrecommend", "formulaperm"};
						StringBuffer updatesql=new StringBuffer();
						for(String field:table_forum_columns){
							String value=values.get(field);
							if(value!=null){
								updatesql.append(", "+field+"='"+value+"'");
							}
						}
						if(updatesql.length()>0){
							dataBaseService.runQuery("UPDATE jrun_forums SET "+updatesql.substring(2)+" WHERE fid IN ("+applyids+")",true);
						}
						updatesql=new StringBuffer();
						for(String field:table_forumfield_columns){
							String value=values.get(field);
							if(value!=null){
								updatesql.append(", "+field+"='"+value+"'");
							}
						}
						if(updatesql.length()>0){
							dataBaseService.runQuery("UPDATE jrun_forumfields SET "+updatesql.substring(2)+" WHERE fid IN ("+applyids+")",true);
						}
					}
					else if("group".equals(type)){
						String[] usergroup_columns={"readaccess", "allowvisit", "allowpost", "allowreply", "allowpostpoll", "allowpostreward", "allowposttrade", "allowpostactivity", "allowpostvideo", "allowdirectpost", "allowgetattach", "allowpostattach", "allowvote", "allowmultigroups", "allowsearch", "allowavatar", "allowcstatus", "allowuseblog", "allowinvisible", "allowtransfer", "allowsetreadperm", "allowsetattachperm", "allowhidecode", "allowhtml", "allowcusbbcode", "allowanonymous", "allownickname", "allowsigbbcode", "allowsigimgcode", "allowviewpro", "allowviewstats", "disableperiodctrl", "reasonpm", "maxprice", "maxpmnum", "maxsigsize", "maxattachsize", "maxsizeperday", "maxpostsperhour", "attachextensions", "raterange", "mintradeprice", "maxtradeprice", "minrewardprice", "maxrewardprice", "magicsdiscount", "allowmagics", "maxmagicsweight", "allowbiobbcode", "allowbioimgcode", "maxbiosize","allowinvite", "allowmailinvite", "inviteprice", "maxinvitenum", "maxinviteday","allowviewdigest"};
						StringBuffer updatesql=new StringBuffer();
						for(String field:usergroup_columns){
							String value=String.valueOf(values.get(field));
							if(value!=null){
								updatesql.append(", "+field+"='"+value+"'");
							}
						}
						if(updatesql.length()>0){
							dataBaseService.runQuery("UPDATE jrun_usergroups SET "+updatesql.substring(2)+" WHERE groupid IN ("+applyids+")",true);
						}
					}
					request.setAttribute("message", getMessage(request, "a_system_project_scheme_succeed"));
					return mapping.findForward("message");
				}else{
					request.setAttribute("message", getMessage(request, "a_system_project_no_scheme"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=project");
		return null;
	}
}