package cn.jsprun.struts.action;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.form.AttachmentsForm;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.LogPage;
public class AttachmentsAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward fromAttachments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "searchsubmit")){
				AttachmentsForm attachementsForm = null;
				HttpSession session = request.getSession();
				if (form != null) {
					Map<String,String> settings = ForumInit.settings;
					int currentpage = 1;
					String page = request.getParameter("page");
					currentpage = page == null || page.equals("") ? 1 :  Integer.valueOf(page.trim());
					attachementsForm = (AttachmentsForm) form;
					String sql = attachmentsService.findByAttachmentsForm(attachementsForm);
					List<Map<String,String>> count = dataBaseService.executeQuery("select count(*) as count "+sql);
					int totalsize = Common.toDigit(count.get(0).get("count"));
					int currpage = currentpage;
					LogPage loginpage = new LogPage(totalsize,10,currpage);
					if(currentpage>loginpage.getTotalPage()){
						currentpage = loginpage.getTotalPage();
					}
					int beginsize = (currentpage-1)*10;
					session.setAttribute("attaforms",attachementsForm);
					List<Map<String,String>> list = dataBaseService.executeQuery("select a.aid,a.filename,a.attachment,a.downloads,a.remote,a.filesize,p.fid,p.author,t.tid,t.subject,f.name "+sql+" limit "+beginsize+",10");
					if(list!=null && list.size()>0){
						List<Map<String,String>> showlist = new ArrayList<Map<String,String>>(); 
						String path = JspRunConfig.realPath+settings.get("attachdir")+"/";
						Map<String,String> ftpmap = dataParse.characterParse(settings.get("ftp"), false);
						String attachurl = ftpmap.get("attachurl");
						ftpmap = null;
						for(Map<String,String> map:list){
							if(map.get("remote").equals("1")){
								File file = new File(attachurl+"/"+map.get("attachment"));
								if(!file.exists()){
									map.put("nomatched", getMessage(request, "a_post_attachments_far"));
								}
								map.put("attachment", attachurl+"/"+map.get("attachment"));
							}else{
								File file = new File(path+map.get("attachment"));
								if(!file.exists()){
									map.put("nomatched", getMessage(request, "a_post_attachments_lost"));
								}
								map.put("attachment", map.get("attachment"));
							}
							if(attachementsForm.getNomatched()==0||(attachementsForm.getNomatched()==1&&map.get("nomatched")!=null)){
								showlist.add(map);
							}
						}
						request.setAttribute("showlist", showlist);
					}else{
						request.setAttribute("showlist", list);
					}
					request.setAttribute("logpage", loginpage);
				}
				short groupid = (Short)session.getAttribute("jsprun_groupid");
				Members member = (Members)session.getAttribute("user");
				request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",attachementsForm.getInforum()+""));
				request.setAttribute("notfirst", "notfirst");
				return mapping.findForward("toAttachments");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=attachments");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward deleteAttachments(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse resposne) {
		try{
			if(submitCheck(request, "deletesubmit")){
				String[] deleteAids = request.getParameterValues("delete[]");
				if (deleteAids != null) {
					Map<String,String> settings = ForumInit.settings;
					String path = JspRunConfig.realPath+settings.get("attachdir");
					StringBuffer aids=new StringBuffer("0");
					for(String aid:deleteAids){
						aids.append(","+aid);
					}
					StringBuffer tids = new StringBuffer("0");
					StringBuffer pids = new StringBuffer("0");
					List<Map<String,String>> attachmap = dataBaseService.executeQuery("select tid,pid,attachment,thumb,remote from jrun_attachments where aid in ("+aids+")");
					for(Map<String,String> attach:attachmap){
						tids.append(","+attach.get("tid"));
						pids.append(","+attach.get("pid"));
						Common.dunlink(attach.get("attachment"), Byte.valueOf(attach.get("thumb")), Byte.valueOf(attach.get("remote")), path);
					}
					dataBaseService.runQuery("delete from jrun_attachments where aid in ("+aids+")");
					dataBaseService.runQuery("update jrun_posts set attachment='0' where pid in ("+pids+")");
					List<Map<String,String>> attachment = dataBaseService.executeQuery("SELECT tid FROM jrun_attachments WHERE tid IN ("+tids+") GROUP BY tid ORDER BY pid DESC");
					StringBuffer attachtids=new StringBuffer("0");
					for(Map<String,String> attach:attachment){
						attachtids.append(","+attach.get("tid"));
					}
					dataBaseService.runQuery("update jrun_threads set attachment='0' where tid in ("+tids+") and tid not in ("+attachtids+")");
					attachment = null;attachmap=null;
				}
				try {
					String shalert = getMessage(request, "a_post_attachments_edit_succeed");
					resposne.getWriter().write( "<script type='text/javascript'>alert('" + shalert + "');</script>");
					resposne.getWriter().write("<script>parent.$('attachforum').searchsubmit.click();</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(resposne, "admincp.jsp?action=attachments");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward pageAttachments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AttachmentsForm attachementsForm = (AttachmentsForm) request.getSession().getAttribute("attaforms");
		if (attachementsForm != null) {
			Map<String,String> settings = ForumInit.settings;
			int currentpage = 1;
			String page = request.getParameter("page");
			currentpage = page == null || page.equals("") ? 1 : Integer.valueOf(page.trim()) ;
			String sql = attachmentsService.findByAttachmentsForm(attachementsForm);
			List<Map<String,String>> count = dataBaseService.executeQuery("select count(*) as count "+sql);
			int totalsize = Common.toDigit(count.get(0).get("count"));
			LogPage loginpage = new LogPage(totalsize,10,currentpage);
			if(currentpage>loginpage.getTotalPage()){
				currentpage = loginpage.getTotalPage();
			}
			int beginsize = (currentpage-1)*10;
			List<Map<String,String>> list = dataBaseService.executeQuery("select a.aid,a.filename,a.attachment,a.downloads,a.remote,a.filesize,p.fid,p.author,t.tid,t.subject,f.name "+sql+" limit "+beginsize+",10");
			request.getSession().setAttribute("attaforms",attachementsForm);
			if(list!=null && list.size()>0){
				List<Map<String,String>> showlist = new ArrayList<Map<String,String>>(); 
				String path = JspRunConfig.realPath+settings.get("attachdir")+"/";
				Map<String,String> ftpmap = dataParse.characterParse(settings.get("ftp"), false);
				String attachurl = ftpmap.get("attachurl");
				ftpmap = null;
				for(Map<String,String> map:list){
					if(map.get("remote").equals("1")){
						File file = new File(attachurl+"/"+map.get("attachment"));
						if(!file.exists()){
							map.put("nomatched", getMessage(request, "a_post_attachments_far"));
						}
						map.put("attachment", attachurl+"/"+map.get("attachment"));
					}else{
						File file = new File(path+map.get("attachment"));
						if(!file.exists()){
							map.put("nomatched", getMessage(request, "a_post_attachments_lost"));
						}
						map.put("attachment", map.get("attachment"));
					}
					if(attachementsForm.getNomatched()==0||(attachementsForm.getNomatched()==1&&map.get("nomatched")!=null)){
						showlist.add(map);
					}
				}
				request.setAttribute("showlist", showlist);
			}else{
				request.setAttribute("showlist", list);
			}
			request.setAttribute("logpage", loginpage);
		}else{
			Common.requestforward(response, "admincp.jsp?action=attachments");
			return null;
		}
		HttpSession session = request.getSession();
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",attachementsForm.getInforum()+""));
		request.setAttribute("notfirst", "notfirst");
		return mapping.findForward("toAttachments");
	}
}
