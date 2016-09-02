package cn.jsprun.struts.foreg.actions;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Forumfields;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.JspRunConfig;
public class AttachmentAction extends BaseAction {
	@SuppressWarnings("unused")
	private int convertInt(String s) {
		int count = 0;
		try {
			count = Integer.valueOf(s);
		} catch (Exception e) {
		}
		return count;
	}
	@SuppressWarnings("unchecked")
	public ActionForward attachment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("jsprun_action", "14");
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		Members member = (Members)session.getAttribute("user");
		byte jsprun_adminid = (Byte) session.getAttribute("jsprun_adminid");
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		String attachdir = settings.get("attachdir");
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		if(settings.get("attachrefcheck").equals("1") && request.getHeader("Referer")!=null && request.getHeader("Referer").indexOf(request.getServerName()) < 0){
			String message = getMessage(request, "attachment_referer_invalid");
			request.setAttribute("resultInfo", message);
			return mapping.findForward("showMessage");
		}
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String modadd2 = "";String modadd1 = "";
		if(member!=null&&member.getAdminid()==3){
			modadd1 = ", m.uid AS ismoderator ";
			modadd2 = " LEFT JOIN jrun_moderators m ON m.uid='"+uid+"' AND m.fid=t.fid ";
		}
		String message=Common.periodscheck(settings.get("attachbanperiods"), (byte)Common.toDigit(usergroups.get("disableperiodctrl")),(int)(System.currentTimeMillis()/1000),settings.get("timeoffset"),resources,locale);
		if(message!=null)
		{
			request.setAttribute("show_message", message);
			return mapping.findForward("nopermission");
		}
		int aid = Common.toDigit(request.getParameter("aid"));
		String attachsql = "select a.*,t.special,t.fid,t.price as threadprice "+modadd1+" from jrun_attachments a left join jrun_threads as t on a.tid=t.tid and displayorder>='0'"+modadd2+" where a.aid='"+aid+"'";
		List<Map<String, String>> attacllist = dataBaseService.executeQuery(attachsql);
		if(attacllist==null || attacllist.size()==0){
			message = getMessage(request, "attachment_nonexistence");
			request.setAttribute("resultInfo", message);
			return mapping.findForward("showMessage");
		}
		Map<String,String> attaMap = attacllist.get(0);
		attacllist = null;
		if(attaMap.get("special")==null){
			message = getMessage(request, "attachment_nonexistence");
			request.setAttribute("resultInfo", message);
			return mapping.findForward("showMessage");
		}
		Map<String,String> ftpmap = null;
		boolean hideurl = false;
		String noupdate = request.getParameter("noupdate");
		boolean remote = attaMap.get("remote").equals("1");
		if(remote){
			ftpmap = dataParse.characterParse(settings.get("ftp"), false);
			String ftpurl = ftpmap.get("attachurl");
			hideurl = ftpmap.get("hideurl").equals("1") || (attaMap.get("isimage").equals("1") && !Common.empty(noupdate) && settings.get("attachimgpost").equals("1") && !ftpurl.equals("") && ftpmap.get("attachurl").substring(0,3).toLowerCase().equals("ftp"));
		}
		boolean ispaid = false;
		if(Common.intval(usergroups.get("allowgetattach"))>0 && (Common.toDigit(attaMap.get("readperm"))>0 && Common.toDigit(attaMap.get("readperm"))>Common.toDigit(usergroups.get("readaccess"))) && jsprun_adminid<=0 && !(uid>0 && Common.intval(attaMap.get("uid"))==uid)){
			message = getMessage(request, "attachment_forum_nopermission");
			request.setAttribute("resultInfo", message);
			return mapping.findForward("showMessage");
		}
		if(attaMap.get("special").equals("0") && convertInt(attaMap.get("threadprice"))>0 && (uid==0 || (!attaMap.get("uid").equals(uid+"")&& jsprun_adminid<=0))){
			String paylogsql = "select uid from jrun_paymentlog where uid="+uid+" and tid="+attaMap.get("tid");
			List<Map<String, String>> paymentlog = dataBaseService.executeQuery(paylogsql);
			if(paymentlog==null || paymentlog.size()<=0){
				message = getMessage(request, "attachment_payto");
				request.setAttribute("successInfo", message);
				request.setAttribute("requestPath", "viewthread.jsp?tid="+attaMap.get("tid"));
				return mapping.findForward("showMessage");
			}else{
				ispaid = true;    
			}
		}
		boolean modertar = Common.ismoderator(attaMap.get("ismoderator"), member);
		boolean payrequired = false;
		if(!attaMap.get("price").equals("0") && !attaMap.get("uid").equals(uid+"")){
			if(!modertar){
				if(uid==0){
					payrequired = true;
				}else{
					String attaloghql = "SELECT uid FROM jrun_attachpaymentlog WHERE uid='"+uid+"' AND aid="+aid;
					List<Map<String, String>> attapaymentlog = dataBaseService.executeQuery(attaloghql);
					if(attapaymentlog==null || attapaymentlog.size()<=0){
						payrequired = true;
					}
				}
				if(payrequired){
					request.setAttribute("successInfo",getMessage(request, "attachement_payto_attach"));
					request.setAttribute("requestPath", "misc.jsp?action=attachpay&aid="+aid);
					return mapping.findForward("showMessage");
				}
			}
		}
		List<Map<String,String>> forumlist = dataBaseService.executeQuery("SELECT f.viewperm, f.getattachperm, f.getattachcredits, a.allowgetattach FROM jrun_forumfields f LEFT JOIN jrun_access a ON a.uid='"+uid+"' AND a.fid=f.fid WHERE f.fid='"+attaMap.get("fid")+"'");
		if(!ispaid&&forumlist.size()>0) {
			String extgroupids=member!=null?member.getExtgroupids():null;
			short groupid = (Short)session.getAttribute("jsprun_groupid");
			Map<String,String> forum = forumlist.get(0);
			if(Common.empty(forum.get("allowgetattach"))) {
				String getattachperm = forum.get("getattachperm");
				String viewperm = forum.get("viewperm");
				if(getattachperm.equals("") && usergroups.get("allowgetattach").equals("0")) {
					request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
					return mapping.findForward("nopermission");
				} else if((!getattachperm.equals("") && !Common.forumperm(getattachperm, groupid,extgroupids)) || (!viewperm.equals("") && !Common.forumperm(viewperm, groupid,extgroupids))) {
					message = getMessage(request, "attachment_forum_nopermission");
					request.setAttribute("show_message", message);
					return mapping.findForward("nopermission");
				}
			}
		}
		String file = attaMap.get("attachment");
		String remotefile = file;
		if(attaMap.get("thumb").equals("1") && noupdate!=null){
			int filetypeint = file.lastIndexOf(".");
			file = file+".thumb"+file.substring(filetypeint);
			remotefile = file;
		}
		String filepath = JspRunConfig.realPath+attachdir+"/"+file;
		File files = new File(filepath);
		if(!files.exists()){
			filepath = JspRunConfig.realPath+attachdir+"/"+attaMap.get("attachment");
			files = new File(filepath);
		}
		if(!files.exists()&&!remote){
			message = getMessage(request, "attachment_nonexistence");
			request.setAttribute("resultInfo", message);
			return mapping.findForward("showMessage");
		}
		if(!attaMap.get("isimage").equals("1")){
			message = updateattacredits(request,Short.valueOf(attaMap.get("fid")),uid,1,settings,member);
		    if(message!=null){
		    	request.setAttribute("resultInfo", message);
		    	return mapping.findForward("showMessage");
		    }
		    viewthread_updateviews(settings.get("delayviewcount"),aid+"",JspRunConfig.realPath+"forumdata/cache/cache_attachviews.log");
		}
		if(remote && !hideurl) {
			String attachurl = ftpmap.get("attachurl");
			String activeurl = ftpmap.get("activeurl");
			String path = ftpmap.get("attachurl")+"/"+remotefile;
			if(!Common.empty(ftpmap.get("isinstall")) && ftpmap.get("isinstall").equals("1")){
				if(!Common.empty(activeurl)&& attachurl.indexOf(activeurl)!=-1){
					String flenames = attaMap.get("filename");
					path = activeurl+"/paser.do?encode="+JspRunConfig.CHARSET+"&filename="+encode(encode(flenames))+"&size="+attaMap.get("filesize")+"&filepath="+remotefile+"&path="+attachurl.substring(activeurl.length());
				}
			}
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", path);	
			response.setHeader("Connection", "close");
			return null;
		}
		String filename = attaMap.get("filename");
		filename = "\""+Common.encodeText(request, filename)+"\"";
		if(attaMap.get("isimage").equals("1") && !Common.empty(noupdate)) {
			response.setHeader("Content-Disposition", "inline; filename="+ filename);
		} else {
			response.setHeader("Content-Disposition", "attachment; filename="+ filename);
		}
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Pragma", "no-store");
		response.setHeader("Robots", "none");
		response.setHeader("Content-Length",attaMap.get("filesize"));
		response.setHeader("Connection","close");
		if(remote){
			getremotefile(ftpmap,attachdir,remotefile,response);
		}else{
			try {
				getlocalfile(response.getOutputStream(),filepath);
			} catch (IOException e) {
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private String updateattacredits(HttpServletRequest request,short fid, Integer uid,int count,Map<String,String>settings,Members member) {
		String message = null;
		Forumfields forumfield = forumfieldService.findById(fid);
		Map extcredits = dataParse.characterParse(settings.get("extcredits"), true);
		Map<Integer, Integer> postcredits = dataParse.characterParse(forumfield.getGetattachcredits(),false);
		if(postcredits==null||postcredits.size()<=0){
			Map creditspolicys= dataParse.characterParse(settings.get("creditspolicy"),false);
			postcredits=(Map<Integer,Integer>)creditspolicys.get("getattach");
			creditspolicys=null;
		}
		Set<Entry<Integer, Integer>> keys = postcredits.entrySet();
		for (Entry<Integer, Integer> keymap : keys) {
			Integer key = keymap.getKey();
			Map extcreditmap = (Map)extcredits.get(key);
			if(extcreditmap!=null){
				int extcredit = member==null?0:(Integer)Common.getValues(member, "extcredits"+key);
				int getattacreditvalue = keymap.getValue();
				String lowerlimit = extcreditmap.get("lowerlimit")==null?"0":String.valueOf(extcreditmap.get("lowerlimit"));
				if(getattacreditvalue!=0 && extcredit-getattacreditvalue<=Integer.valueOf(lowerlimit)){
					String unit = extcreditmap.get("unit")!=null?"":extcreditmap.get("unit").toString();
					message =  getMessage(request, "credits_policy_num_lowerlimit", extcreditmap.get("title").toString(),lowerlimit,unit);
					break;
				}
			}
		}
		if(uid!=0 && message==null){
			Common.updatepostcredits("-", uid,1, postcredits);
			Common.updatepostcredits(uid,settings.get("creditsformula"));
		}
		extcredits = null;
		postcredits=null;
		return message;
	}
	private void viewthread_updateviews(String delayviewcount,String aid,String path) {
		String timestamp = Common.time()+"";
		if((delayviewcount.equals("2")||delayviewcount.equals("3"))) {
			if(timestamp.substring(8).equals("00")) {
				updateviews(path);
			}
			try {
				FileWriter fo = new FileWriter(path,true);
				BufferedWriter bf = new BufferedWriter(fo);
				bf.write(aid);
				bf.newLine();
				bf.close();
				fo.close();
			}  catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String updateatta = "update jrun_attachments set downloads = downloads+1 where aid="+aid;
			dataBaseService.runQuery(updateatta,true);
		}
	}
	private  void updateviews(String path){
		File file = new File(path);
		if(file!=null&&file.exists()){
			FileReader fr =null;
			BufferedReader br = null;
			try {
				Map<String,Integer> aids = new HashMap<String,Integer>();
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				String aid = null;
				Integer views;
				while((aid = br.readLine())!=null){
					views = aids.get(aid);
					if(views==null){
						views = 0;
					}
					aids.put(aid, views+1);
				}
				br.close();
				fr.close();
				file.delete();
				Set<Entry<String,Integer>> aidset = aids.entrySet();
				for(Entry<String,Integer> aidtd:aidset){
					dataBaseService.runQuery("update jrun_attachments set downloads=downloads+"+aidtd.getValue()+" where aid = '"+aidtd.getKey()+"'",true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void getremotefile(Map<String,String> ftpmap,String attachdir,String filepath,HttpServletResponse response){
		try {
			OutputStream os = response.getOutputStream();
			if(ftputil.readfile(ftpmap.get("attachurl")+"/"+filepath, response.getOutputStream())){
				return;
			}else{
				FTPClient fc = ftputil.getFTPClient();
				if(!ftputil.connectToServer(fc).equals("")){
					ftputil.closeFtpConnect(fc);
					return;
				}
				String filestemp[] = filepath.split("/");
				String tempname = JspRunConfig.realPath+attachdir+"/"+filestemp[0];
				File files = new File(tempname);
				if(!files.exists()){
					files.mkdirs();
				}
				ftputil.dftp_chdir(filestemp[0],fc);
				tempname = tempname+"/"+filestemp[1];
				if(ftputil.get(tempname, filestemp[1],fc)){
					getlocalfile(os,tempname);
				}
				files = new File(tempname);
				if(files.exists()){
					files.delete();
				}
				ftputil.closeFtpConnect(fc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void getlocalfile(OutputStream  os,String filepath){
		FileInputStream fis = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		 try
		    {
		        fis = new java.io.FileInputStream(filepath);
		        in = new BufferedInputStream(fis,4096);
		        out = new BufferedOutputStream(os,4096);
		        int count = 0;
				byte[] buffer = new byte[4096];
				while((count = in.read(buffer))>0){
					out.write(buffer,0,count); 
			    }
				out.flush();
		        buffer = null;
		    }
		    catch ( Exception e )
		    {
		    }finally{
		    	if(out != null){
					try {
						out.close();
					} catch (IOException e) {
					}
				}
		    	if(os != null){
					try {
						os.close();
					} catch (IOException e) {
					}
				}
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
					}
				}
				if(fis != null){
					try {
						fis.close();
					} catch (IOException e) {
					}
				}
		    }
	}
	private static String encode(String s) {
		try {
			return URLEncoder.encode(s,"gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
}