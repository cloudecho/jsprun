package cn.jsprun.struts.action;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.service.CheckFile;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Md5Token;
import cn.jsprun.vo.logs.FilesVo;
public class SystemFileAction extends BaseAction {
	public ActionForward checkFileperms(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CheckFile chekfile = new CheckFile();
		String path = JspRunConfig.realPath;
		String filepath1 = path+ "forumdata/cache";
		String filepath2 = path+ "forumdata/logs";
		String filepath3 = path+ "forumdata/templates";
		String filepath4 = path+ "templates";
		List<String> fallfile = new ArrayList<String>();
		fallfile = chekfile.checkFilePopedom(filepath1, fallfile,path);
		fallfile = chekfile.checkFilePopedom(filepath2, fallfile,path);
		fallfile = chekfile.checkFilePopedom(filepath3, fallfile,path);
		fallfile = chekfile.checkFilePopedom(filepath4, fallfile,path);
		request.setAttribute("fallfilelist", fallfile);
		if (fallfile == null || fallfile.size() == 0) {
			request.setAttribute("fallfilelist", null);
		}
		return mapping.findForward("fileperms");
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public ActionForward checkFileIntegrity(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		String filechek = request.getParameter("filechek");
		if(filechek!=null && filechek.equals("yes")){
			boolean isfounder = (Boolean)request.getAttribute("isfounder");
			if(!isfounder){
				request.setAttribute("message", getMessage(request,"a_setting_not_createmen_access"));
				return mapping.findForward("message");
			}
			request.setAttribute("message", getMessage(request,"a_system_filecheck_checking"));
			request.setAttribute("url_forward", request.getContextPath()+"/sysfile.do?fileaction=checkFileIntegrity&filechek=no");
			return mapping.findForward("message");
		}else{
			HttpSession session = request.getSession();
			String dateformat = (String)session.getAttribute("dateformat");
			String timeformat = (String)session.getAttribute("timeformat");
			String timeoffset= (String)session.getAttribute("timeoffset");
			int timestamp = (Integer)(request.getAttribute("timestamp"));
			List listresult = new ArrayList();
			CheckFile chekfile = new CheckFile();
			int delcount = 0;
			int addcount = 0;
			int modifycount = 0;
			String realPath=JspRunConfig.realPath;
			String filepath =realPath + "admin/jsprunfiles.md5";
			List<String> filelist = chekfile.displist(filepath);
			if (filelist == null) {
				request.setAttribute("message", getMessage(request,"a_system_filecheck_no_exist_file"));
				return mapping.findForward("message");
			}
			String[] filedirs = { "./","templates/default/","admin/", "admin/page/","admin/page/announcements/", "admin/page/basicsetting/",
					"admin/page/basicsetting/creditwizard/", "admin/page/counter/","admin/page/forumsedit/", "admin/page/members/", 
					"admin/page/modthreads/","admin/page/pluginsconfig/","admin/page/safety/","api/","archiver/","archiver/include/",
					"errors/","include/","include/crons/","include/javascript/","include/magics/","wap/","wap/include/"};
			for (String filedir : filedirs) {
				int modifynum = 0;
				int addnum = 0;
				int delnum = 0;
				List<FilesVo> resultlist = new ArrayList<FilesVo>();
				List<String> targetlist = chekfile.displistBydir(filelist, filedir);
				String source = realPath+ filedir;
				File file = new File(source);
				File[] sourcelist = null;
				if (file.isDirectory()) {
					sourcelist = file.listFiles();
					for (int i = 0; i < targetlist.size(); i++) {
						String files = (String) targetlist.get(i);
						String[] filearray = files.split("\\s\\*");
						if (filearray != null && filearray.length > 1) {
							filearray[1] = filearray[1];
						}
						String filespath = realPath+ filearray[1];
						File filenew = new File(filespath);
						boolean flag = false;
						boolean modify = false;
						for (File fi : sourcelist) {
							if (filenew.equals(fi)) {
								String targetlength = Md5Token.getInstance().getLongToken(fi.length() + "");
								if (!targetlength.equals(filearray[0])) {
									FilesVo filevo = new FilesVo();
									filevo.setFilename(fi.getName());
									filevo.setFilebyte(fi.length() + "");
									long lastModified = fi.lastModified();
									String date = Common.gmdate(dateformat+" "+timeformat, (int)(lastModified/1000), timeoffset);
									if(timestamp-(lastModified/1000)<7*86400){
										filevo.setModifyDate("<b>"+date+"</b>");
									}else{
										filevo.setModifyDate(date);
									}
									filevo.setStatus("<font color='red'>"+getMessage(request,"a_system_filecheck_modify")+"</font>");
									modifycount++;
									modifynum++;
									resultlist.add(filevo);
									modify = true;
								}else{
									flag = true;
								}
							}
						}
						if (flag) {
							FilesVo filevo = new FilesVo();
							filevo.setFilename(filenew.getName());
							filevo.setFilebyte(filenew.length() + "");
							long lastModified = filenew.lastModified();
							String date = Common.gmdate(dateformat+" "+timeformat, (int)(lastModified/1000), timeoffset);
							if(timestamp-(lastModified/1000)<7*86400){
								filevo.setModifyDate("<b>"+date+"</b>");
							}else{
								filevo.setModifyDate(date);
							}
							filevo.setStatus(getMessage(request,"a_system_filecheck_check_ok"));
							resultlist.add(filevo);
						} else if(!modify){
							delcount++;
							delnum++;
							FilesVo filevo = new FilesVo();
							filevo.setFilename(filenew.getName());
							filevo.setStatus("<font color='blue'>"+getMessage(request,"a_system_filecheck_delete")+"</font>");
							resultlist.add(filevo);
						}
					}
					for (File fi : sourcelist) {
						boolean flag = false;
						for (int i = 0; i < targetlist.size(); i++) {
							String files = (String) targetlist.get(i);
							String[] filearray = files.split("\\s\\*");
							filearray[1] = filearray[1];
							String filespath = realPath+ filearray[1];
							File filenew = new File(filespath);
							if (fi.equals(filenew)) {
								flag = true;
							}
						}
						if (!flag && !fi.isDirectory()) {
							if(Common.matches(fi.getName(), "(jsp|js|htm)$")){
								addcount++;
								addnum++;
								FilesVo filevo = new FilesVo();
								filevo.setFilename(fi.getName());
								filevo.setFilebyte(fi.length() + "");
								long lastModified = fi.lastModified();
								String date = Common.gmdate(dateformat+" "+timeformat, (int)(lastModified/1000), timeoffset);
								if(timestamp-(lastModified/1000)<7*86400){
									filevo.setModifyDate("<b>"+date+"</b>");
								}else{
									filevo.setModifyDate(date);
								}
								filevo.setStatus("<font color='green'>"+getMessage(request,"a_system_filecheck_unknown")+"</font>");
								resultlist.add(filevo);
							}
						}
					}
					String propeter = "";
					if (addnum > 0) {
						propeter = propeter + getMessage(request,"a_system_filecheck_unknown")+": " + addnum;
					}
					if (delnum > 0) {
						propeter = propeter + getMessage(request,"a_system_filecheck_delete")+": " + delnum;
					}
					if (modifynum > 0) {
						propeter = propeter + getMessage(request,"a_system_filecheck_modify")+": " + modifynum;
					}
					if(filedir.equals("")){
						filedir = "./";
					}
					listresult.add("<td>" + filedir+ "</td><td colspan='3' style='text-align:right'>"+ propeter + "</td>");
					listresult.add(resultlist);
				}
			}
			request.setAttribute("listresult", listresult);
			request.setAttribute("result", getMessage(request,"a_system_filecheck_modify")+": " + modifycount + "&nbsp; "+getMessage(request,"a_system_filecheck_delete")+": " + delcount + "&nbsp;"+getMessage(request,"a_system_filecheck_unknown")+":" + addcount);
			return mapping.findForward("fileintegrity");
		}
	}
}