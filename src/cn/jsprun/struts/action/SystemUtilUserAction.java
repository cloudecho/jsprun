package cn.jsprun.struts.action;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.form.UserForm;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.LogPage;
import cn.jsprun.utils.Mail;
import cn.jsprun.vo.logs.BanlogVO;
import cn.jsprun.vo.logs.CplogVO;
import cn.jsprun.vo.logs.CreditslogVO;
import cn.jsprun.vo.logs.ErrorlogVO;
import cn.jsprun.vo.logs.IllegallogVO;
import cn.jsprun.vo.logs.InviteslogVO;
import cn.jsprun.vo.logs.MagiclogVO;
import cn.jsprun.vo.logs.MedalslogVO;
import cn.jsprun.vo.logs.ModslogVO;
import cn.jsprun.vo.logs.RatelogVO;
public class SystemUtilUserAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward newletterInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userform = (UserForm) form;
		Map<String,String> settings = ForumInit.settings;
		String setting = settings.get("extcredits");
		Map extcreditMap  = dataParse.characterParse(setting,true);
		String creditsnotify = settings.get("creditsnotify");
		Map creditsnotifyMap = dataParse.characterParse(creditsnotify, false);
		try{
			if(submitCheck(request, "searchsubmit")){
				HttpSession session = request.getSession();
				String timeoffset=(String)session.getAttribute("timeoffset");
				List<Map<String,String>> allgroups = dataBaseService.executeQuery("select groupid,grouptitle from jrun_usergroups order by radminid desc,groupid desc");
				request.setAttribute("allGroups", allgroups);
				String sql = memberService.returnsearsql(userform, true,timeoffset);
				sql += sql.trim().endsWith("g.groupid")?" WHERE newsletter>0 " : " AND newsletter>0 ";
				List<Map<String,String>> count = dataBaseService.executeQuery("select count(*) as count "+sql);
				String size = "0";
				if(count.size()>0){
					size = count.get(0).get("count");
				}
				session.setAttribute("currsql", sql);
				request.setAttribute("size", size);
				request.setAttribute("result", "yes");
				count = null;
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> allgroups = dataBaseService.executeQuery("select groupid,grouptitle from jrun_usergroups order by radminid desc,groupid desc");
		request.setAttribute("allGroups", allgroups);
		request.setAttribute("creditsnotifyMap", creditsnotifyMap);
		request.setAttribute("extcreditMap", extcreditMap);
		return mapping.findForward("newlettersubmit");
	}
	@SuppressWarnings("unchecked")
	public ActionForward newlettersubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "updatecreditsubmit",true)){
				HttpSession session = request.getSession();
				Members members = (Members) session.getAttribute("members");
				String currsql = (String)session.getAttribute("currsql");
				if(currsql==null){
					Common.requestforward(response, "admincp.jsp?action=members&submitname=newslettersubmit");
					return null;
				}
				String sendvia = request.getParameter("sendvia");
				if(sendvia.equals("email")&&request.getParameter("end")!=null){
					session.removeAttribute("currsql");
					String msg = getMessage(request, "members_newsletter_succeed");
					request.setAttribute("message", msg);
					return mapping.findForward("message");
				}
				Map<String,String> settings = ForumInit.settings;
				String subject = request.getParameter("subject");
				String message = request.getParameter("message");
				String pertask = request.getParameter("pertask");
				int perta = Common.toDigit(pertask);
				if (subject == null || subject.equals("") || message == null || message.equals("")) {
					request.setAttribute("message", getMessage(request, "newsletter_sm_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				message = Common.cutstr(message, 40000, "");
				String begin = request.getParameter("begin");
				if(begin!=null&&message.equals("settings")){
					String creditsnotify = settings.get("creditsnotify");
					Map<String,String> creditsnotifyMap = dataParse.characterParse(creditsnotify, false);
					subject = creditsnotifyMap.get("subject");
					message = creditsnotifyMap.get("message");
				}else{
					Map creditsnotifyMap = new HashMap();
					creditsnotifyMap.put("subject", subject);
					creditsnotifyMap.put("message", message);
					String value = dataParse.combinationChar(creditsnotifyMap);
					dataBaseService.runQuery("REPLACE INTO jrun_settings (variable, value) VALUES('creditsnotify','"+Common.addslashes(value)+"')");
					settings.put("creditsnotify", value);
				}
				if (sendvia.equals("email")) {
					List<Map<String,String>> memberlist = dataBaseService.executeQuery("select username,newsletter,email "+currsql);
					StringBuffer tomails=new StringBuffer();
					for (Map<String, String> member : memberlist) {
						if(member.get("newsletter").equals("1")){
							tomails.append(","+member.get("username")+" <"+member.get("email")+">");
						}
					}
					if(tomails.length()>0){
						Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
						Mail mail = new Mail(mails);
						mail.sendMessage(mails.get("from"), tomails.substring(1), subject, message, null);
					}
					if (perta > 1) {
						String msg = getMessage(request, "a_system_members_send_message","0" ,String.valueOf(perta));
						request.setAttribute("message", msg);
						request.setAttribute("url_forward", "admincp.jsp?action=newlettersubmit&&updatecreditsubmit=yes&subject=settings&message=settings&sendvia=email&end=yes&formHash="+Common.formHash(request));
						return mapping.findForward("message");
					} else {
						session.removeAttribute("currsql");
						String msg = getMessage(request, "members_newsletter_succeed");
						request.setAttribute("message", msg);
						return mapping.findForward("message");
					}
				} else {
					int beint = 0;
					if(begin!= null){
						beint = Integer.valueOf(begin);
					}
					int count = Common.toDigit(dataBaseService.executeQuery("select count(*) as count "+currsql).get(0).get("count"));
					List<Map<String,String>> memberlist = dataBaseService.executeQuery("select uid,newsletter,g.maxpmnum as maxpmnum "+currsql+" limit "+beint+","+perta);
					String username = members.getUsername();
					String uid = members.getUid()+"";
					int timestamp = (Integer)(request.getAttribute("timestamp"));
					StringBuffer sql = new StringBuffer("insert into jrun_pms (msgfrom,msgfromid,msgtoid,folder,new,subject,dateline,message,delstatus) values ");
					if (perta > 0) {
						int num = 0;
						StringBuffer uids = new StringBuffer("0");
						for(Map<String,String> member:memberlist){
							if(member.get("newsletter").equals("1") && member.get("maxpmnum")!=null && !member.get("maxpmnum").equals("0")){
								uids.append(","+member.get("uid"));
								num++;
								sql.append("('"+username+"','"+uid+"','"+member.get("uid")+"','inbox','1','"+Common.addslashes(subject)+"','"+timestamp+"','"+Common.addslashes(message)+"','0'),");
							}
						}
						dataBaseService.runQuery("update jrun_members set newpm = 1 where uid in ("+uids+")");
						if(num>0){
							dataBaseService.runQuery(sql.substring(0,sql.length()-1),true);
						}
						if(perta+beint>=count){
							String msg = getMessage(request, "members_newsletter_succeed");
							session.removeAttribute("currsql");
							request.setAttribute("message", msg);
							return mapping.findForward("message");
						}else{
							String msg = getMessage(request, "a_system_members_send_message",String.valueOf(beint),String.valueOf((perta+beint)));
							beint = beint+perta;
							request.setAttribute("message", msg);
							request.setAttribute("url_forward", "admincp.jsp?action=newlettersubmit&updatecreditsubmit=yes&subject=settings&message=settings&sendvia=pms&formHash="+Common.formHash(request)+"&pertask="+perta+"&begin="+beint);
							return mapping.findForward("message");
						}
					}else{
						String msg = getMessage(request, "members_newsletter_succeed");
						session.removeAttribute("currsql");
						request.setAttribute("message", msg);
						return mapping.findForward("message");
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=members&submitname=newslettersubmit");
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward userLogRead(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		String extcred = ForumInit.settings.get("extcredits");
		int timestamp = (Integer)request.getAttribute("timestamp");
		Map<Integer,Map<String,String>> extcredits=dataParse.characterParse(extcred, true);
		List<String> ratelist = null;
		List resultlist = new ArrayList();
		List<Map<String,String>> usergrouplist = dataBaseService.executeQuery("select groupid,grouptitle from jrun_usergroups");
		String formhash = request.getParameter("caction");
		SimpleDateFormat sf = Common.getSimpleDateFormat("yy-MM-dd HH:mm", timeoffset);
		String keyword = request.getParameter("keyword");
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("lpp", 50);
			request.setAttribute("keys", keyword);
		}
		ratelist = Log.readlog("ratelog",timestamp,keyword);
		if (ratelist != null&&ratelist.size()>0) {
			int size = ratelist.size()-1;
			for (int i = size; i >= 0; i--) {
				RatelogVO rate = new RatelogVO();
				String rowstring = ratelist.get(i);
				String[] rowcontent = rowstring.split("\t");
				if(rowcontent.length>8){
					String time = Common.gmdate(sf, Common.toDigit(rowcontent[1]));
					rate.setOperateTime(time); 
					rate.setFirstUsername(rowcontent[2]); 
					rate.setSecondUsername(rowcontent[4]); 
					for(int j=0;j<usergrouplist.size();j++){
						if(usergrouplist.get(j).get("groupid").equals(rowcontent[3])){
							rate.setUsergroup(usergrouplist.get(j).get("grouptitle"));
						}
					}
					Map<String,String> extcredit = (Map<String,String>) extcredits.get(Common.intval(rowcontent[5]));
					String ischexiao = "";
					if(!rowcontent[7].equals("0") && rowcontent.length>10 && rowcontent[10].trim().equals("D")){
						ischexiao = getMessage(request, "a_system_members_rating_delete");
					}
					if (extcredit != null) {
						rate.setMarkValue(ischexiao+extcredit.get("title") + rowcontent[6]);
					} else {
						rate.setMarkValue(rowcontent[6]);
					}
					if (rowcontent[7].equals("0")) {
						rate.setTitle(getMessage(request, "a_system_members_rating_manual"));
						rate.setReason(rowcontent[8]);
					} else {
						rate.setTitle(rowcontent[8]);
						rate.setTid(convertInt(rowcontent[7]));
						if (rowcontent.length > 9) {
							rate.setReason(rowcontent[9]);
						} else {
							rate.setReason("");
						}
					}
					resultlist.add(rate);
				}
			}
		}
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("reteloglist", resultlist);
			return mapping.findForward("ratelog");
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			if (lpp == null) {
				lpp = "50";
			}
			request.setAttribute("lpp", lpp);
		}
		Map<String,Integer> multiPage = multi(request,resultlist.size());
		int pagesize = multiPage.get("pagesize");
		if(pagesize==0){
			return mapping.findForward("ratelog");
		}
		List displaylist = resultlist.subList(multiPage.get("beginsize"), multiPage.get("dissize"));
		request.setAttribute("reteloglist", displaylist);
		return mapping.findForward("ratelog");
	}
	private int convertInt(String s) {
		int num = 0;
		try {
			num = Integer.parseInt(s);
		} catch (Exception e) {
		}
		return num;
	}
	@SuppressWarnings("unchecked")
	public ActionForward medalsLogRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		SimpleDateFormat sf = Common.getSimpleDateFormat("yy-MM-dd HH:mm", timeoffset);
		int timestamp = (Integer)request.getAttribute("timestamp");
		String formhash = request.getParameter("caction");
		List<String> medalsLog = null;
		List resultlist = new ArrayList();
		List<Map<String,String>> medallist = dataBaseService.executeQuery("select * from jrun_medals where available=1");
		String keyword = request.getParameter("keyword");
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("lpp", 50);
			request.setAttribute("keys", keyword);
		}
		medalsLog = Log.readlog("medalslog", timestamp, keyword);
		if (medalsLog != null && medalsLog.size()>0) {
			int size = medalsLog.size()-1;
			for (int i = size; i >= 0; i--) {
				MedalslogVO medallog = new MedalslogVO();
				String rowstring = medalsLog.get(i);
				String[] rowcontent = rowstring.split("\t");
				String time = Common.gmdate(sf, Common.toDigit(rowcontent[1]));
				medallog.setOperateDate(time); 
				medallog.setFirstName(rowcontent[2]); 
				medallog.setSecondName(rowcontent[4]); 
				medallog.setIpAddress(rowcontent[3]);
				boolean flag = false;
				for(Map<String,String> medal:medallist){
					if(medal.get("medalid").equals(rowcontent[5].trim())){
						flag = true;
						medallog.setImgUrl(medal.get("image"));
						medallog.setMedalName(medal.get("name"));
					}
				}
				if(!flag){
					medallog.setMedalName(getMessage(request, "unavailable"));
				}
				if (rowcontent[6].equals("revoke")) {
					medallog.setOperate(getMessage(request, "a_system_members_edit_medals_revoke"));
				} else {
					medallog.setOperate(getMessage(request, "a_system_members_edit_medals_grant"));
				}
				if (rowcontent.length > 7) {
					medallog.setReason(rowcontent[7]);
				} else {
					medallog.setReason("");
				}
				resultlist.add(medallog);
			}
		}
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("medalsloglist", resultlist);
			return mapping.findForward("medalslog");
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			if (lpp == null) {
				lpp = "50";
			}
			request.setAttribute("lpp", lpp);
		}
		Map<String,Integer> multiPage = multi(request,resultlist.size());
		int pagesize = multiPage.get("pagesize");
		if(pagesize==0){
			return mapping.findForward("medalslog");
		}
		List displaylist = resultlist.subList(multiPage.get("beginsize"), multiPage.get("dissize"));
		request.setAttribute("medalsloglist", displaylist);
		return mapping.findForward("medalslog");
	}
	@SuppressWarnings("unchecked")
	public ActionForward banLogRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		String formhash = request.getParameter("caction");
		SimpleDateFormat sf = Common.getSimpleDateFormat("yy-MM-dd HH:mm", timeoffset);
		SimpleDateFormat sf1 = Common.getSimpleDateFormat("yy-MM-dd", timeoffset);
		int timestamp = (Integer)request.getAttribute("timestamp");
		List<String> banLog = null;
		List resultlist = new ArrayList();
		List<Map<String,String>> usergrouplist = dataBaseService.executeQuery("select groupid,grouptitle from jrun_usergroups");
		String keyword = request.getParameter("keyword");
		if(formhash != null && formhash.equals("keyword")){
			request.setAttribute("lpp", 50);
			request.setAttribute("keys", keyword);
		}
		banLog = Log.readlog("banlog", timestamp, keyword);
		if (banLog != null && banLog.size()>0) {
			int size = banLog.size()-1;
			for (int i = size; i >= 0; i--) {
				BanlogVO banlog = new BanlogVO();
				String rowstring = banLog.get(i);
				String[] rowcontent = rowstring.split("\t");
				if(rowcontent.length>8){
					String time = Common.gmdate(sf,Common.toDigit(rowcontent[1]));
					banlog.setOperateDate(time); 
					banlog.setFirstName(rowcontent[2]); 
					banlog.setSecondName(rowcontent[5]); 
					banlog.setIpAddress(rowcontent[4]);
					for (Map<String,String> usergrop : usergrouplist) {
						if (usergrop.get("groupid").equals(rowcontent[3])) {
							banlog.setUsergroup(usergrop.get("grouptitle"));
						}else if(usergrop.get("groupid").toString().equals(rowcontent[6])){
							banlog.setOldUsergroup(usergrop.get("grouptitle"));
						}else if(usergrop.get("groupid").toString().equals(rowcontent[7])){
							banlog.setNewUsergroup(usergrop.get("grouptitle"));
						}
					}
					if (!rowcontent[7].equals("4") && !rowcontent[7].equals("5")) {
						banlog.setOperate(getMessage(request, "a_system_members_banned_unban"));
					} else {
						banlog.setOperate(getMessage(request, "a_system_members_banned_ban"));
					}
					if (rowcontent[8].length()==10) {
						String times = Common.gmdate(sf1,Common.toDigit(rowcontent[8]));
						banlog.setPeriodDate(times);
					} else {
						banlog.setPeriodDate("");
					}
					if (rowcontent.length > 9) {
						banlog.setReason(rowcontent[9]);
					} else {
						banlog.setReason("");
					}
					resultlist.add(banlog);
				}
			}
		}
		if(formhash != null && formhash.equals("keyword")){
			request.setAttribute("banloglist", resultlist);
			return mapping.findForward("banlog");
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			if (lpp == null) {
				lpp = "50";
			}
			request.setAttribute("lpp", lpp);
		}
		Map<String,Integer> multiPage = multi(request,resultlist.size());
		int pagesize = multiPage.get("pagesize");
		if(pagesize==0){
			return mapping.findForward("banlog");
		}
		List displaylist = resultlist.subList(multiPage.get("beginsize"), multiPage.get("dissize"));
		request.setAttribute("banloglist", displaylist);
		return mapping.findForward("banlog");
	}
	@SuppressWarnings("unchecked")
	public ActionForward illegallogRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		String formhash = request.getParameter("caction");
		SimpleDateFormat sf = Common.getSimpleDateFormat("yy-MM-dd HH:mm", timeoffset);
		List<String> illegalLog = null;
		int timestamp = (Integer)request.getAttribute("timestamp");
		List resultlist = new ArrayList();
		String keyword = request.getParameter("keyword");
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("lpp", 50);
			request.setAttribute("keys", keyword);
		}
		illegalLog = Log.readlog("illegallog", timestamp, keyword);
		if (illegalLog != null && illegalLog.size()>0) {
			int size = illegalLog.size()-1;
			for (int i = size; i >= 0; i--) {
				String[] rowcontent = illegalLog.get(i).split("\t");
				if(rowcontent.length>5){
					IllegallogVO illegallog = new IllegallogVO();
					illegallog.setDatetimes(Common.gmdate(sf,Common.toDigit(rowcontent[1])));
					illegallog.setUsername(rowcontent[2]);
					illegallog.setPssword(rowcontent[3]);
					illegallog.setAiquwenda(rowcontent[4]);
					illegallog.setIpAddress(rowcontent[5]);
					resultlist.add(illegallog);
				}
			}
		}
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("illegaloglist", resultlist);
			return mapping.findForward("illegalog");
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			if (lpp == null) {
				lpp = "50";
			}
			request.setAttribute("lpp", lpp);
		}
		Map<String,Integer> multiPage = multi(request,resultlist.size());
		int pagesize = multiPage.get("pagesize");
		if(pagesize==0){
			return mapping.findForward("illegalog");
		}
		List displaylist = resultlist.subList(multiPage.get("beginsize"), multiPage.get("dissize"));
		request.setAttribute("illegaloglist", displaylist);
		return mapping.findForward("illegalog");
	}
	@SuppressWarnings("unchecked")
	public ActionForward cplogRead(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		String formhash = request.getParameter("caction");
		List<Map<String,String>> usergroups = dataBaseService.executeQuery("select groupid,grouptitle from jrun_usergroups");
		MessageResources mr=getResources(request);
		Locale locale=getLocale(request);
		List<String> cpLog = null;
		List resultlist = new ArrayList();
		int timestamp = (Integer)request.getAttribute("timestamp");
		SimpleDateFormat sf = Common.getSimpleDateFormat("yy-MM-dd HH:mm", timeoffset);
		String keyword = request.getParameter("keyword");
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("lpp", 50);
			request.setAttribute("keys", keyword);
		}
		cpLog = Log.readlog("cplog", timestamp, keyword);
		if (cpLog != null && cpLog.size() > 0) {
			int size = cpLog.size()-1;
			for (int i = size; i >= 0; i--) {
				CplogVO cplog = new CplogVO();
				String rowstring = cpLog.get(i);
				String[] rowcontent = rowstring.split("\t");
				if(rowcontent.length>=6 && rowcontent[1].length()==10 && Common.isNum(rowcontent[1])){
					String time = Common.gmdate(sf,Common.toDigit(rowcontent[1]));
					cplog.setOperaterDate(time);
					cplog.setIpAddress(rowcontent[4]);
					cplog.setUsername(rowcontent[2]);
					for (Map<String,String> usergrop : usergroups) {
						if (usergrop.get("groupid").equals(rowcontent[3])) {
							cplog.setUsergroups(usergrop.get("grouptitle"));
							break;
						}
					}
					if (rowcontent.length > 6) {
						cplog.setOthers(rowcontent[6]);
					} else {
						cplog.setOthers("");
					}
					cplog.setOperater(mr.getMessage(locale, "cplog_action_"+rowcontent[5]));
					resultlist.add(cplog);
				}
			}
		}
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("cploglist", resultlist);
			return mapping.findForward("cplog");
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			if (lpp == null) {
				lpp = "50";
			}
			request.setAttribute("lpp", lpp);
		}
		Map<String,Integer> multiPage = multi(request,resultlist.size());
		int pagesize = multiPage.get("pagesize");
		if(pagesize==0){
			return mapping.findForward("cplog");
		}
		List displaylist = resultlist.subList(multiPage.get("beginsize"), multiPage.get("dissize"));
		request.setAttribute("cploglist", displaylist);
		return mapping.findForward("cplog");
	}
	@SuppressWarnings("unchecked")
	public ActionForward modsRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		String formhash = request.getParameter("caction");
		MessageResources mr=getResources(request);
		Locale locale=getLocale(request);
		List<String> modsLog = null;
		int timestamp = (Integer)request.getAttribute("timestamp");
		List resultlist = new ArrayList();
		SimpleDateFormat sf = Common.getSimpleDateFormat("yy-MM-dd HH:mm", timeoffset);
		List<Map<String,String>> usergroups = dataBaseService.executeQuery("select groupid,grouptitle from jrun_usergroups");
		String keyword = request.getParameter("keyword");
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("lpp", 50);
			request.setAttribute("keys", keyword);
		}
		modsLog = Log.readlog("modslog", timestamp, keyword);
		if (modsLog != null && modsLog.size()>0) {
			int size = modsLog.size()-1;
			for (int i = size; i >= 0; i--) {
				ModslogVO modslog = new ModslogVO();
				String rowstring = modsLog.get(i);
				if(rowstring.length()>50){
					rowstring = rowstring.substring(14);
					String[] rowcontent = rowstring.split("\t");
					if(rowcontent.length>=9){
						String time = Common.gmdate(sf,Common.toDigit(rowcontent[0]));
						modslog.setOpaterDate(time);
						modslog.setIpaddress(rowcontent[3]);
						modslog.setUsername(rowcontent[1]);
						for (Map<String,String> usergrop : usergroups) {
							if (usergrop.get("groupid").equals(rowcontent[2])) {
								modslog.setUsergroup(usergrop.get("grouptitle"));
								break;
							}
						}
						modslog.setForum(rowcontent[5]);
						modslog.setForumid(rowcontent[4]);
						modslog.setThread(rowcontent[7]);
						modslog.setThreadid(rowcontent[6]);
						modslog.setOpertar(mr.getMessage(locale,rowcontent[8]));
						if (rowcontent.length >= 10) {
							modslog.setReason(rowcontent[9]);
						} else {
							modslog.setReason("");
						}
						resultlist.add(modslog);
					}
				}
			}
		}
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("modsloglist", resultlist);
			return mapping.findForward("modslog");
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			if (lpp == null) {
				lpp = "50";
			}
			request.setAttribute("lpp", lpp);
		}
		Map<String,Integer> multiPage = multi(request,resultlist.size());
		int pagesize = multiPage.get("pagesize");
		if(pagesize==0){
			return mapping.findForward("modslog");
		}
		List displaylist = resultlist.subList(multiPage.get("beginsize"), multiPage.get("dissize"));
		request.setAttribute("modsloglist", displaylist);
		return mapping.findForward("modslog");
	}
	@SuppressWarnings("unchecked")
	public ActionForward errorlogRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String formhash = request.getParameter("caction");
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		SimpleDateFormat sf = Common.getSimpleDateFormat("yy-MM-dd HH:mm", timeoffset);
		List<String> errorLog = null;
		List resultlist = new ArrayList();
		int timestamp = (Integer)request.getAttribute("timestamp");
		String keyword = request.getParameter("keyword");
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("lpp", 50);
			request.setAttribute("keys", keyword);
		}
		errorLog = Log.readlog("errorlog", timestamp, keyword);
		if (errorLog != null && errorLog.size() > 0) {
			int size = errorLog.size()-1;
			for (int i = size; i >= 0; i--) {
				ErrorlogVO errorlog = new ErrorlogVO();
				String rowstring = errorLog.get(i);
				String[] rowcontent = rowstring.split("\t");
				if(rowcontent.length>4){
					String time = Common.gmdate(sf,Common.toDigit(rowcontent[1]));
					errorlog.setDatetime(time);
					errorlog.setUsername(rowcontent[3]);
					errorlog.setType(rowcontent[2]);
					StringBuffer content = new StringBuffer();
					if (rowcontent.length > 4) {
						for (int j = 4; j < rowcontent.length; j++) {
							content.append(rowcontent[j]);
						}
					}
					errorlog.setContent(content.toString());
					resultlist.add(errorlog);
				}
			}
		}
		if (formhash != null && formhash.equals("keyword")) {
			request.setAttribute("errorloglist", resultlist);
			return mapping.findForward("errorlog");
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			if (lpp == null) {
				lpp = "50";
			}
			request.setAttribute("lpp", lpp);
		}
		Map<String,Integer> multiPage = multi(request,resultlist.size());
		int pagesize = multiPage.get("pagesize");
		if(pagesize==0){
			return mapping.findForward("errorlog");
		}
		List displaylist = resultlist.subList(multiPage.get("beginsize"), multiPage.get("dissize"));
		request.setAttribute("errorloglist", displaylist);
		return mapping.findForward("errorlog");
	}
	@SuppressWarnings("unchecked")
		public ActionForward creditslogRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String formhash = request.getParameter("caction");
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		SimpleDateFormat sf = Common.getSimpleDateFormat("yy-MM-dd HH:mm", timeoffset);
		String extcredits = ForumInit.settings.get("extcredits");
		Map extcreditsMap = dataParse.characterParse(extcredits,true);
		List resultList = new ArrayList();
		String sql = "";
		if (formhash != null && formhash.equals("keyword")) {
			String keyword = request.getParameter("keyword");
			String sqladd = "";
			if (keyword != null) {
				sqladd = "where fromto like '%"+Common.addslashes(keyword)+"%'";
			}
			sql = "select c.*,username from jrun_creditslog c left join jrun_members as m on c.uid=m.uid "+sqladd+" order by dateline desc";
			request.setAttribute("keys", keyword);
			request.setAttribute("lpp", 50);
		}
		if (formhash != null && formhash.equals("opertar")) {
			String operations[] = request.getParameterValues("operations");
			StringBuffer sqladd = new StringBuffer();
			if (operations != null) {
				sqladd.append("where c.operation in (");
				for(int i=0;i<operations.length;i++){
					sqladd.append("'"+operations[i]+"',");
				}
				sqladd.replace(0, sqladd.length()-1, ")");
			}
			sql = "select c.*,m.username from jrun_creditslog as c left join jrun_members as m on c.uid=m.uid "+sqladd+" order by c.dateline desc";
			request.setAttribute("operations", operations);
			request.setAttribute("lpp", 50);
		}
		if(formhash==null||(!formhash.equals("opertar")&&!formhash.equals("keyword"))){
			int tatalsize = Common.toDigit(dataBaseService.executeQuery("select count(*) as count from jrun_creditslog").get(0).get("count"));
			String pageSize = request.getParameter("lpp");
			String currpage = request.getParameter("page");
			int pages = 1;
			if (currpage != null) {
				pages = convertInt(currpage);
			}
			int pagesize = 50;
			if (pageSize != null) {
				pagesize = convertInt(pageSize);
			}
			if(pagesize==0){
				return mapping.findForward("creditslog");
			}
			LogPage logpage = new LogPage(tatalsize, pagesize, pages);
			int beginsize = (pages - 1) * pagesize;
			sql = "select c.*,m.username from jrun_creditslog as c left join jrun_members as m on c.uid=m.uid order by c.dateline desc limit "+beginsize+","+pagesize;
			request.setAttribute("lpp", pagesize);
			request.setAttribute("logpage", logpage);
		}
		List<Map<String,String>> creditsloglist = dataBaseService.executeQuery(sql);
		if (creditsloglist != null && creditsloglist.size()> 0) {
			for (Map<String,String> creditslog:creditsloglist) {
				CreditslogVO creditslogvo = new CreditslogVO();
				creditslogvo.setUsername(creditslog.get("username"));
				creditslogvo.setFromname(creditslog.get("fromto"));
				String datetime = Common.gmdate(sf,Common.toDigit(creditslog.get("dateline")));
				creditslogvo.setOpertarDate(datetime);
				creditslogvo.setSendNum(Common.toDigit(creditslog.get("send")));
				creditslogvo.setReceiverNum(Common.toDigit(creditslog.get("receive")));
				Map sendMap = (Map) extcreditsMap.get(Integer.valueOf(creditslog.get("sendcredits")));
				Map receiveMap = (Map) extcreditsMap.get(Integer.valueOf(creditslog.get("receivecredits")));
				if (sendMap != null) {
					String title = sendMap.get("title")==null?"":sendMap.get("title").toString();
					String unit = sendMap.get("unit")==null?"":sendMap.get("unit").toString();
					creditslogvo.setSendCritesNum(Integer.valueOf(creditslog.get("sendcredits")));
					creditslogvo.setSendCrites(title);
					creditslogvo.setSendunit(unit);
				}
				if (receiveMap != null) {
					String title = receiveMap.get("title")==null?"":receiveMap.get("title").toString();
					String unit = receiveMap.get("unit")==null?"":receiveMap.get("unit").toString();
					creditslogvo.setReceiveCritesNum(Integer.valueOf(creditslog.get("receivecredits")));
					creditslogvo.setReceiveCrites(title);
					creditslogvo.setReceiveuint(unit);
				}
				creditslogvo.setOpertar(getMessage(request, creditslog.get("operation")));
				resultList.add(creditslogvo);
			}
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			request.setAttribute("lpp", lpp);
		}
		List sendorreclist = sendAndreceiv(resultList);
		request.setAttribute("sendext", sendorreclist.get(0));
		request.setAttribute("receext", sendorreclist.get(1));
		sendorreclist = null;
		request.setAttribute("creditsMap", extcreditsMap);
		request.setAttribute("creditsloglist", resultList);
		return mapping.findForward("creditslog");
	}
	@SuppressWarnings( { "unchecked", "unused" })
	private List sendAndreceiv(List disp) {
		List sendorreceiv = new ArrayList();
		int sendExtcredits1 = 0;
		int sendExtcredits2 = 0;
		int sendExtcredits3 = 0;
		int sendExtcredits4 = 0;
		int sendExtcredits5 = 0;
		int sendExtcredits6 = 0;
		int sendExtcredits7 = 0;
		int sendExtcredits8 = 0;
		int receExtcredits1 = 0;
		int receExtcredits2 = 0;
		int receExtcredits3 = 0;
		int receExtcredits4 = 0;
		int receExtcredits5 = 0;
		int receExtcredits6 = 0;
		int receExtcredits7 = 0;
		int receExtcredits8 = 0;
		List sendextcredits = new ArrayList();
		List receextcredits = new ArrayList();
		for (int i = 0; i < disp.size(); i++) {
			CreditslogVO credits = (CreditslogVO) disp.get(i);
			if (credits.getSendCritesNum() == 1) {
				sendExtcredits1 = sendExtcredits1 + credits.getSendNum();
			}
			if (credits.getSendCritesNum() == 2) {
				sendExtcredits2 = sendExtcredits2 + credits.getSendNum();
			}
			if (credits.getSendCritesNum() == 3) {
				sendExtcredits3 = sendExtcredits3 + credits.getSendNum();
			}
			if (credits.getSendCritesNum() == 4) {
				sendExtcredits4 = sendExtcredits4 + credits.getSendNum();
			}
			if (credits.getSendCritesNum() == 5) {
				sendExtcredits5 = sendExtcredits5 + credits.getSendNum();
			}
			if (credits.getSendCritesNum() == 6) {
				sendExtcredits6 = sendExtcredits6 + credits.getSendNum();
			}
			if (credits.getSendCritesNum() == 7) {
				sendExtcredits7 = sendExtcredits7 + credits.getSendNum();
			}
			if (credits.getSendCritesNum() == 8) {
				sendExtcredits8 = sendExtcredits8 + credits.getSendNum();
			}
			if (credits.getReceiveCritesNum() == 1) {
				receExtcredits1 = receExtcredits1 + credits.getReceiverNum();
			}
			if (credits.getReceiveCritesNum() == 2) {
				receExtcredits2 = receExtcredits2 + credits.getReceiverNum();
			}
			if (credits.getReceiveCritesNum() == 3) {
				receExtcredits3 = receExtcredits3 + credits.getReceiverNum();
			}
			if (credits.getReceiveCritesNum() == 4) {
				receExtcredits4 = receExtcredits4 + credits.getReceiverNum();
			}
			if (credits.getReceiveCritesNum() == 5) {
				receExtcredits5 = receExtcredits5 + credits.getReceiverNum();
			}
			if (credits.getReceiveCritesNum() == 6) {
				receExtcredits6 = receExtcredits6 + credits.getReceiverNum();
			}
			if (credits.getReceiveCritesNum() == 7) {
				receExtcredits7 = receExtcredits7 + credits.getReceiverNum();
			}
			if (credits.getReceiveCritesNum() == 8) {
				receExtcredits8 = receExtcredits8 + credits.getReceiverNum();
			}
		}
		sendextcredits.add(sendExtcredits1);
		sendextcredits.add(sendExtcredits2);
		sendextcredits.add(sendExtcredits3);
		sendextcredits.add(sendExtcredits4);
		sendextcredits.add(sendExtcredits5);
		sendextcredits.add(sendExtcredits6);
		sendextcredits.add(sendExtcredits7);
		sendextcredits.add(sendExtcredits8);
		receextcredits.add(receExtcredits1);
		receextcredits.add(receExtcredits2);
		receextcredits.add(receExtcredits3);
		receextcredits.add(receExtcredits4);
		receextcredits.add(receExtcredits5);
		receextcredits.add(receExtcredits6);
		receextcredits.add(receExtcredits7);
		receextcredits.add(receExtcredits8);
		sendorreceiv.add(sendextcredits);
		sendorreceiv.add(receextcredits);
		return sendorreceiv;
	}
	@SuppressWarnings("unchecked")
	public ActionForward magiclogRead(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		boolean isfounder = (Boolean)request.getAttribute("isfounder");
		if(!isfounder){
			request.setAttribute("message", getMessage(request, "a_setting_not_createmen_access"));
			return mapping.findForward("message");
		}
		HttpSession session = request.getSession();
		String opertars[] = new String[]{getMessage(request, "a_system_logs_magic_operation_1"),getMessage(request, "a_system_logs_magic_operation_2"),getMessage(request, "a_system_logs_magic_operation_3"),getMessage(request, "a_system_logs_magic_operation_4"),getMessage(request, "a_system_logs_magic_operation_5"),""};
		String timeoffset=(String)session.getAttribute("timeoffset");
		String formhash = request.getParameter("caction");
		SimpleDateFormat sf = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffset);
		List<Map<String,String>> magiclist = dataBaseService.executeQuery("select magicid,name from jrun_magics where available=1"); 
		List resultList = new ArrayList();
		String sql = "";
		if (formhash != null && formhash.equals("magicname")) {
			String magicid = request.getParameter("magicid");
			String sqladd = "";
			if (magicid != null && !magicid.equals("0")) {
				sqladd = " where l.magicid="+magicid;
			}
			sql = "select l.action,l.dateline,l.amount,l.price,m.username,mm.name from jrun_magiclog as l left join jrun_members as m on l.uid=m.uid left join jrun_magics as mm on l.magicid=mm.magicid "+sqladd+" order by l.dateline desc";
			request.setAttribute("magicid", magicid);
			request.setAttribute("lpp", 50);
		}
		if (formhash != null && formhash.equals("opertar")) {
			String operations[] = request.getParameterValues("operations");
			StringBuffer actions = new StringBuffer();
			String sqladd = "";
			if (operations != null) {
				for(int j=0;j<operations.length;j++){
					actions.append(operations[j]+",");
				}
				if(actions.length()!=0){
					actions.deleteCharAt(actions.length()-1);
					sqladd = " where l.action in ("+actions.toString()+")";
				}
			}
			sql = "select l.action,l.dateline,l.amount,l.price,m.username,mm.name from jrun_magiclog as l left join jrun_members as m on l.uid=m.uid left join jrun_magics as mm on l.magicid=mm.magicid "+sqladd+" order by l.dateline desc";
			request.setAttribute("operations", operations);
			request.setAttribute("lpp", 50);
		}
		if(formhash==null||(!formhash.equals("opertar")&&!formhash.equals("magicname"))){
			int size = Common.toDigit(dataBaseService.executeQuery("select count(*) as count from jrun_magiclog").get(0).get("count"));
			String pageSize = request.getParameter("lpp");
			String currpage = request.getParameter("page");
			int pages = 1;
			if (currpage != null) {
				pages = convertInt(currpage);
			}
			int pagesize = 50;
			if (pageSize != null) {
				pagesize = convertInt(pageSize);
			}
			if(pagesize==0){
				return mapping.findForward("magiclog");
			}
			LogPage logpage = new LogPage(size, pagesize, pages);
			int beginsize = (pages - 1) * pagesize;
			sql = "select l.action,l.dateline,l.amount,l.price,m.username,mm.name from jrun_magiclog as l left join jrun_members as m on l.uid=m.uid left join jrun_magics as mm on l.magicid=mm.magicid order by l.dateline desc limit "+beginsize+","+pagesize;
			request.setAttribute("lpp", pagesize);
			request.setAttribute("logpage", logpage);
		}
		List<Map<String,String>> magicloglist = dataBaseService.executeQuery(sql);
		if (magicloglist != null && magicloglist.size() > 0) {
			for (Map<String,String> magiclog:magicloglist) {
				MagiclogVO magiclogVO = new MagiclogVO();
				magiclogVO.setUsername(magiclog.get("username"));
				magiclogVO.setMagicname(magiclog.get("name"));
				String datetime = Common.gmdate(sf, Common.toDigit(magiclog.get("dateline")));
				magiclogVO.setDatetime(datetime);
				magiclogVO.setAmount((short)Common.range(Common.intval(magiclog.get("amount")), 1000000, 0));
				magiclogVO.setPrice(Common.toDigit(magiclog.get("price")));
				magiclogVO.setOpertar(opertars[Common.toDigit(magiclog.get("action"))-1]);
				resultList.add(magiclogVO);
			}
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			request.setAttribute("lpp", lpp);
		}
		request.setAttribute("magicloglist", resultList);
		request.setAttribute("magicList",magiclist);
		return mapping.findForward("magiclog");
	}
	@SuppressWarnings("unchecked")
	public ActionForward inviteslogRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String status[] = {getMessage(request, "a_system_invite_status_1"),getMessage(request, "a_system_invite_status_2"),getMessage(request, "a_system_invite_status_3"),getMessage(request, "a_system_invite_status_4")};
		String timeoffset=(String)session.getAttribute("timeoffset");
		SimpleDateFormat sf = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffset);
		String formhash = request.getParameter("caction");
		if(formhash !=null && formhash.equals("delinvites")){
			try{
				if(submitCheck(request, "invitesubmit")){
					String deleid[] = request.getParameterValues("deleid");
					if(deleid!=null){
						StringBuffer sql = new StringBuffer("delete from jrun_invites where invitecode in (");
						for(int i=0;i<deleid.length;i++){
							sql.append("'");
							sql.append(deleid[i].trim());
							sql.append("'");
							sql.append(",");
						}
						String delsql = sql.substring(0,sql.length()-1);
						delsql = delsql + ")";
						dataBaseService.runQuery(delsql,true);
					}
				}
			}catch (Exception e) {
				request.setAttribute("message",e.getMessage());
				return mapping.findForward("message");
			}
		}
		List resultList = new ArrayList();
		String sql = "";
		if (formhash != null && formhash.equals("statuss")) {
			String operations[] = request.getParameterValues("status");
			StringBuffer actions = new StringBuffer();
			String sqladd = "";
			if (operations != null) {
				for(int j=0;j<operations.length;j++){
					actions.append(operations[j]+",");
				}
				if(actions.length()!=0){
					actions.deleteCharAt(actions.length()-1);
					sqladd = " where i.status in ("+actions.toString()+")";
				}
			}
			sql = "select i.*,m.username from jrun_invites as i left join jrun_members as m on i.uid=m.uid "+sqladd+" order by i.dateline desc";
			request.setAttribute("operations", operations);
			request.setAttribute("lpp", 50);
		}
		if(formhash==null||!formhash.equals("statuss")){
			int totalsize = Common.toDigit(dataBaseService.executeQuery("select count(*) as count from jrun_invites").get(0).get("count"));
			String pageSize = request.getParameter("lpp");
			String currpage = request.getParameter("page");
			int pages = 1;
			if (currpage != null) {
				pages = convertInt(currpage);
			}
			int pagesize = 50;
			if (pageSize != null) {
				pagesize = convertInt(pageSize);
			}
			if(pagesize==0){
				return mapping.findForward("inviteslog");
			}
			LogPage logpage = new LogPage(totalsize, pagesize, pages);
			int beginsize = (pages - 1) * pagesize;
			sql = "select i.*,m.username from jrun_invites as i left join jrun_members as m on i.uid=m.uid order by i.dateline desc limit "+beginsize+","+pagesize;
			request.setAttribute("lpp", pagesize);
			request.setAttribute("logpage", logpage);
		}
		List<Map<String,String>> inviteslist = dataBaseService.executeQuery(sql);
		if (inviteslist != null && inviteslist.size() != 0) {
			for (Map<String,String> invites:inviteslist) {
				InviteslogVO inviteslog = new InviteslogVO();
				inviteslog.setUsername(invites.get("username"));
				String buydatetime = Common.gmdate(sf,Common.toDigit(invites.get("dateline")));
				inviteslog.setBuytime(buydatetime);
				String regdate = Common.gmdate(sf,Common.toDigit(invites.get("expiration")));
				inviteslog.setTermtime(regdate);
				inviteslog.setBuyIp(invites.get("inviteip"));
				inviteslog.setInvitecode(invites.get("invitecode"));
				inviteslog.setStatus(status[Common.toDigit(invites.get("status"))-1]);
				resultList.add(inviteslog);
			}
		}
		if (formhash != null && formhash.equals("lpp")) {
			String lpp = request.getParameter("lpp");
			request.setAttribute("lpp", lpp);
		}
		request.setAttribute("inviteslist", resultList);
		return mapping.findForward("inviteslog");
	}
	private Map<String,Integer> multi(HttpServletRequest request,int size){
		Map<String,Integer> multiMap = new HashMap<String,Integer>();
		String pageSize = request.getParameter("lpp");
		String currpage = request.getParameter("page");
		int pages = 1;
		if (currpage != null) {
			pages = Common.toDigit(currpage);
		}
		int pagesize = 50;
		if (pageSize != null) {
			pagesize = Math.max(Common.intval(pageSize), 1);
		}
		LogPage logpage = new LogPage(size, pagesize, pages);
		int dissize = pages * pagesize;
		int beginsize = (pages - 1) * pagesize;
		if (beginsize > size) {
			beginsize = size;
		}
		if (dissize > size) {
			dissize = size;
		}
		request.setAttribute("lpp", pagesize);
		request.setAttribute("logpage", logpage);
		multiMap.put("beginsize", beginsize);
		multiMap.put("pagesize", pagesize);
		multiMap.put("dissize", dissize);
		return multiMap;
	}
}
