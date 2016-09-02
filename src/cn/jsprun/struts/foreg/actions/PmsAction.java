package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Memberfields;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Pms;
import cn.jsprun.domain.Pmsearchindex;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Jspruncode;
public class PmsAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward toPmsInbox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String folder = request.getParameter("folder");
		String hqlnum = "select count(*) count from jrun_pms as p where p.msgtoid = "+ uid + " and p.folder='inbox' and p.delstatus<>2 and p.new>0";
		int num = Common.toDigit(dataBaseService.executeQuery(hqlnum).get(0).get("count"));
		request.getSession().setAttribute("num", num);
		int pm_inbox = Common.toDigit(dataBaseService.executeQuery("select count(*) count from jrun_pms as p where p.msgtoid = " + uid+ " and p.folder='inbox' and p.delstatus<>2").get(0).get("count"));
		int pm_outbox = Common.toDigit(dataBaseService.executeQuery("SELECT COUNT(*) FROM jrun_pms WHERE msgfromid='"+uid+"' AND folder='outbox'").get(0).get("count"));
		String hqlcount = "";
		String pmshql = "";
		if (folder == null || folder.equals("inbox")) {
			hqlcount = "select count(*) count from jrun_pms as p where p.msgtoid = " + uid+ " and p.folder='inbox' and p.delstatus<>2";
			pmshql = "select p.*,m.username from jrun_pms as p left join jrun_members as m on p.msgtoid=m.uid where p.msgtoid = "+ uid + " and p.folder='inbox' and p.delstatus<>2 order by p.dateline desc";
		} else if (folder.equals("outbox")) {
			hqlcount = "select count(*) count from jrun_pms as p where p.msgfromid = " + uid + " and p.folder='outbox' and p.delstatus<>2";
			pmshql = "select p.*,m.username from jrun_pms as p left join jrun_members as m on p.msgtoid=m.uid where p.msgfromid = " + uid + " and p.folder='outbox' and p.delstatus<>2 order by p.dateline desc";
		} else {
			hqlcount = "select count(*) count from jrun_pms as p where p.msgfromid = " + uid + " and p.folder='inbox' and p.delstatus<>1";
			pmshql = "select p.*,m.username from jrun_pms as p left join jrun_members as m on p.msgtoid=m.uid where p.msgfromid = " + uid + " and p.folder='inbox' and p.delstatus<>1 order by p.dateline desc";
		}
		folder = folder==null?"inbox":folder;
		Members member=(Members)session.getAttribute("user");
		Map<String, String> settings = ForumInit.settings;
		int pmnum=Integer.valueOf(dataBaseService.executeQuery(hqlcount).get(0).get("count"));
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
		int page =Math.max(Common.intval(request.getParameter("page")),1);
		Map<String,Integer> multiInfo=Common.getMultiInfo(pmnum, tpp, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		Map<String,Object> multi=Common.multi(pmnum, tpp, page, "pm.jsp?folder=" + folder, 0, 10, true, false, null);
		request.setAttribute("pmnum", pm_inbox+pm_outbox);
		request.setAttribute("multi", multi);
		List<Map<String,String>> pmslist = dataBaseService.executeQuery(pmshql+" limit "+start_limit+","+tpp);
		request.setAttribute("pmslist", pmslist);
		if (pmslist == null || pmslist.size() < 1) {
			request.setAttribute("pmslist", null);
		}
		if(folder == null || folder.equals("inbox")){
			String groupid = member.getGroupid()+"";
			String sql = "SELECT * FROM jrun_announcements WHERE type=2 AND starttime<='"+timestamp+"' AND (endtime='0' OR endtime>'"+timestamp+"') ORDER BY displayorder, starttime DESC, id DESC";
			List<Map<String,String>> announlists = dataBaseService.executeQuery(sql);
			if(announlists!=null && announlists.size()>0){
				List<Map<String,String>> announcements = new ArrayList<Map<String,String>>();
				for(Map<String,String> announ:announlists){
					if(Common.empty(announ.get("groups")) || Common.in_array(announ.get("groups").split(","),groupid)) {
						announcements.add(announ);
					}
				}
				request.setAttribute("announcements", announcements);
			}else{
				request.setAttribute("announcements", null);
			}
		}
		setExtcredits(request);
		return mapping.findForward("todisplay");
	}
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
	public ActionForward toView(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String folder = request.getParameter("folder");
		int pmid = Common.intval(request.getParameter("pmid"));
		int num=0;
		String inajax = request.getParameter("inajax");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		int timestamp = (Integer)request.getAttribute("timestamp");
		int uid = (Integer) session.getAttribute("jsprun_uid");
		if(folder!=null && folder.equals("announce")){
			String sql = "SELECT * FROM jrun_announcements WHERE id='"+pmid+"' AND type=2 AND starttime<='"+timestamp+"' AND (endtime='0' OR endtime>'"+timestamp+"')";
			List<Map<String,String>> announlists = dataBaseService.executeQuery(sql);
			if(announlists==null||announlists.size()<=0){
				if(inajax!=null){
					Common.writeMessage(response, getMessage(request, "pm_nonexistencenowap"), true);
					return null;
				}else{
					request.setAttribute("resultInfo", getMessage(request, "pm_nonexistencenowap"));
					return mapping.findForward("showMessage");
				}
			}
			Map<String,String> announ = announlists.get(0);
			if(!Common.empty(announ.get("groups")) && !Common.in_array(announ.get("groups").split(","),groupid+"")) {
				if(inajax!=null){
					Common.writeMessage(response, getMessage(request, "pm_nonexistencenowap"), true);
					return null;
				}else{
					request.setAttribute("resultInfo", getMessage(request, "pm_nonexistencenowap"));
					return mapping.findForward("showMessage");
				}
			}
			request.setAttribute("announ", announ);
			request.setAttribute("message", announ.get("message"));
			String readapmid = CookieUtil.getCookie(request, "readapmid");
			if(!Common.empty(readapmid)){
				if(!Common.in_array(readapmid.split("D"), pmid)){
					CookieUtil.setCookie(request, response, "readapmid", readapmid+"D"+pmid, 15552000);
				}
			}else{
				CookieUtil.setCookie(request, response, "readapmid", String.valueOf(pmid), 15552000);
			}
		}else{
			int id = pmid;
			Pms pms = pmsServer.findPmsBypmid(id);
			if(pms==null){
				if(inajax!=null){
					Common.writeMessage(response, getMessage(request, "pm_nonexistencenowap"), true);
					return null;
				}else{
					request.setAttribute("resultInfo", getMessage(request, "pm_nonexistencenowap"));
					return mapping.findForward("showMessage");
				}
			}
			if(pms.getMsgtoid()!=uid&&pms.getMsgfromid()!=uid){
				if(inajax!=null){
					Common.writeMessage(response, getMessage(request, "pm_nonexistencenowap"), true);
					return null;
				}else{
					request.setAttribute("resultInfo", getMessage(request, "pm_nonexistencenowap"));
					return mapping.findForward("showMessage");
				}
			}
			if(folder!=null && !folder.equals("track")&&pms.getNew_()>0){
				pms.setNew_(Byte.valueOf("0"));
			}
			MessageResources resources = getResources(request);
			Locale locale = getLocale(request);
			String message = pms.getMessage().replace("$", Common.SIGNSTRING);
			message = Common.htmlspecialchars(message);
			Jspruncode jspruncode = new Jspruncode();
			message = jspruncode.parseCodeP(message,resources,locale,false);
			message = jspruncode.parseJsprunCode(message,resources,locale);
			message = jspruncode.parseimg(message,true);
			message = relacesmile(message);
			message = message.replaceAll("\\n", "<br/>");
			int count = jspruncode.getCodecount();
			List<String> codelist = jspruncode.getCodelist();
			for(int i=0;i<count;i++){
				String str = codelist.get(i);
				message = StringUtils.replace(message, "[\tJSPRUN_CODE_"+i+"\t]", str);
			}
			String highlight = request.getParameter("highlight");
			int highlightstatus = !Common.empty(highlight)&&!Common.empty(highlight.replace("+",""))?1:0;
			if(highlightstatus>0){
				Pattern p = Pattern.compile("(?s)(^|>)([^<]+)(?=<|$)");
				Matcher m = p.matcher(message);
				if(m.find()){
					StringBuffer b = new StringBuffer();
					String text = m.group(2);
					String prepend = m.group(1);
					text = text.replace("\\\"", "\"");
					String []highlightarray = highlight.split("\\+");
					for(String keyword:highlightarray){
						if(!"".equals(keyword)){
							text = text.replace(keyword, "<strong><font color=\"#FF0000\">"+keyword+"</font></strong>");
						}
					}
					m.appendReplacement(b, prepend+text);
					message = m.appendTail(b).toString();
				}
			}
			message = message.replace(Common.SIGNSTRING, "$");
			Members member = memberService.findMemberById(pms.getMsgtoid());
			request.setAttribute("member", member);
			request.setAttribute("message", message);
			request.setAttribute("pmsd", pms);
			pmsServer.updatePms(pms);
			String hqlnum = "select count(*) from Pms as p where p.msgtoid = " + pms.getMsgtoid()+ " and p.folder='inbox' and p.delstatus<>2 and p.new_>0";
			num = pmsServer.findPmsCountByHql(hqlnum);
			request.getSession().setAttribute("num", num);
			setExtcredits(request);
		}
		if (inajax != null) {
			Map<String, String> usergroup = (Map<String, String>) request.getAttribute("usergroups");
			String hqlcount = "select count(*) from Pms as p where p.msgtoid = "+ uid + " and p.folder='inbox' and p.delstatus<>2";
			int count = pmsServer.findPmsCountByHql(hqlcount);
			String pmmax = usergroup.get("maxpmnum");
			if ((!pmmax.equals("0") && convertInt(pmmax) < count)||(pmmax.equals("0")&&10<count)) {
				request.setAttribute("message", getMessage(request, "pm_box_isfull"));
				request.setAttribute("dis", "ok");
			} else {
				request.setAttribute("dis", "tt");
			}
			if(folder.equals("inbox")&&num==0){
				dataBaseService.runQuery("update jrun_members set newpm=0 where uid="+uid);
			}
			return mapping.findForward("toview");
		} else {
			return mapping.findForward("todisplay");
		}
	}
	private String relacesmile(String message) {
		List<Map<String,String>> smilieslist = dataBaseService.executeQuery("select s.id,s.typeid,s.code,s.url,i.directory from jrun_smilies s left join jrun_imagetypes  i on s.typeid=i.typeid where s.type='smiley' order by LENGTH(s.code) DESC");
		for(Map<String,String> smiles:smilieslist){
			if(message.indexOf(smiles.get("code"))!=-1){
				StringBuffer buffer = new StringBuffer();
				buffer.append("<img src='images/smilies/");
				buffer.append(smiles.get("directory"));
				buffer.append("/");
				buffer.append(smiles.get("url"));
				buffer.append("' smilieid='");
				buffer.append(smiles.get("id"));
				buffer.append("' border='0' alt='' /> ");
				message = StringUtils.replace(message, smiles.get("code"), buffer.toString());
			}
		}
		smilieslist=null;
		return message;
	}
	public ActionForward markunread(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int pmid = Common.intval(request.getParameter("pmid"));
		List<Map<String,String>> tempML = dataBaseService.executeQuery("select msgtoid from jrun_pms where pmid="+pmid);
		Common.setResponseHeader(response);
		if(tempML.size() > 0){
			String msgtoid = tempML.get(0).get("msgtoid");
			dataBaseService.runQuery("update jrun_pms set new=1 where pmid="+pmid);
			String hqlnum = "select count(*) from Pms as p where p.msgtoid = " + msgtoid + " and p.folder='inbox' and p.delstatus<>2 and p.new_>0";
			int num = pmsServer.findPmsCountByHql(hqlnum);
			request.getSession().setAttribute("num", num);
			try {
				response.getWriter().write(getMessage(request, "pm_mark_unread_succ"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().write(getMessage(request, "pm_mark_unread_err1"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward toSendPms(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int pmid = Common.intval(request.getParameter("pmid"));
		String action = request.getParameter("do");
		String uids = request.getParameter("uid");
		String inajax = request.getParameter("inajax");
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		String boardurl = (String)session.getAttribute("boardurl");
		Map<String, String> settings = ForumInit.settings;
		Members user = (Members) session.getAttribute("user");
		String dateformat = settings.get("dateformat");
		String timeformat = settings.get("gtimeformat");
		String timeoffset = settings.get("timeoffset");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		if(inajax==null){
			byte jsprun_adminid = (Byte) session.getAttribute("jsprun_adminid");
			int lastpost=user!=null?user.getLastpost():0;
			int newbiespan=Common.toDigit(settings.get("newbiespan"));
			if(jsprun_adminid==0 && newbiespan>0 && (lastpost==0 || timestamp - lastpost < newbiespan * 3600)) {
				int regdate=user!=null?user.getRegdate():0;
				if(timestamp - regdate < newbiespan * 3600) {
					request.setAttribute("errorInfo", getMessage(request, "pm_newbie_span", String.valueOf(newbiespan)));
					return mapping.findForward("showMessage");
				}
			}
			if(usergroups==null || usergroups.get("maxpmnum").equals("0")){
				request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			}
			Map seccodedata = dataParse.characterParse(settings.get("seccodedata"), false);
			int minposts = Common.toDigit(String.valueOf(seccodedata.get("minposts")));
			int seccodestatus = Common.range(Common.intval(settings.get("seccodestatus")), 255, 0);
			boolean seccodecheck = (seccodestatus & 8) > 0 && (minposts <= 0 || user.getPosts() < minposts);
			request.setAttribute("seccodecheck", seccodecheck);
			request.setAttribute("seccodedata", seccodedata);
			Map secqaa = dataParse.characterParse(settings.get("secqaa"),false);
			minposts = Common.toDigit(String.valueOf(secqaa.get("minposts")));
			int secqaastatus =(Integer)(secqaa.get("status"));
			boolean secqaacheck = (secqaastatus & 4) > 0 && (minposts <= 0 || user.getPosts() < minposts);
			request.setAttribute("secqaacheck", secqaacheck);
		}
		if (uids == null) {
			List<Map<String,String>>buddylist = dataBaseService.executeQuery("select b.buddyid,m.username from jrun_buddys as b left join jrun_members as m on b.buddyid=m.uid where b.uid = "+uid);
			if (buddylist != null && buddylist.size() > 0) {
				request.setAttribute("buddylist", buddylist);
			} else {
				request.setAttribute("buddylist", null);
			}
			int pid = pmid;
			if (action != null&&pid>0) {
				Pms pms = pmsServer.findPmsBypmid(pid);
				if(pms!=null){
					String subject = pms.getSubject();
					if(subject.indexOf("Re:")!=-1 || subject.indexOf("Fw:")!=-1){
						subject = subject.substring(3);
					}
					if (action.equals("reply")) { 
						pms.setSubject("Re:" +subject);
						Members members = memberService.findMemberById(pms.getMsgfromid());
						String message = pms.getMessage();
						message = "[b]"+getMessage(request, "pm_orig_message")+":[/b] [url="+session.getAttribute("boardurl")+"pm.jsp?action=view&folder=inbox&pmid="+pms.getPmid()+"]"+subject+"[/url]\n"+"[quote]"+message.replaceAll("(?si)(\\[quote\\])(.*)(\\[/quote\\])", "").trim()+"[/quote]";
						pms.setMessage(message);
						request.setAttribute("member",members);
						request.setAttribute("pms", pms);
					} else { 
						subject = "Fw:" + subject;
						String message = pms.getMessage();
						Members tomember = memberService.findMemberById(pms.getMsgtoid());
						message = "[b]"+getMessage(request, "pm_orig_message")+"[/b] [url="+session.getAttribute("boardurl")+"pm.jsp?action=send&pmid="+pms.getPmid()+"&do=reply]("+getMessage(request, "threads_replies")+")[/url]\n"+"[b]"+getMessage(request, "location_from")+":[/b] [url="+session.getAttribute("boardurl")+"space.jsp?uid="+pms.getMsgfromid()+"]"+pms.getMsgfrom()+"[/url]\n"+"[b]"+getMessage(request, "to")+":[/b] [url="+session.getAttribute("boardurl")+"space.jsp?uid="+pms.getMsgtoid()+"]"+tomember.getUsername()+"[/url]\n"+"[b]"+getMessage(request, "time")+":[/b]"+Common.gmdate(dateformat+" "+timeformat, pms.getDateline(),timeoffset)+"[quote]"+message+"[/quote]";
						pms.setMessage(message);
						pms.setSubject(subject);
						request.setAttribute("pms", pms);
					}
				}
			} else {
				if (pid>0) {
					Pms pms = pmsServer.findPmsBypmid(pid);
					Members member = memberService.findMemberById(pms.getMsgtoid());
					request.setAttribute("member", member);
					request.setAttribute("pms", pms);
				}
			}
			setExtcredits(request);
			return mapping.findForward("todisplay");
		} else if(inajax!=null){
			if(uid==0){
				Common.writeMessage(response,getMessage(request, "not_loggedin"),true);
				return null;
			}
			if(Common.toDigit(usergroups.get("maxpmnum"))<=0){
				Common.writeMessage(response, getMessage(request, "nopermission_loggedin"), true);
				return null;
			}
			String pid = request.getParameter("pid");
			request.setAttribute("pid", pid);
			Members members = memberService.findMemberById(convertInt(uids));
			request.setAttribute("tosendmembers",members);
			return mapping.findForward("tosendajax");
		}else{
			int tradepid = Common.intval(request.getParameter("tradepid"));
			if(tradepid>0){
				List<Map<String,String>> trades = dataBaseService.executeQuery("SELECT * FROM jrun_trades WHERE pid='"+tradepid+"'");
				if(trades.size()>0){
					Map<String,String>trade = trades.get(0);
					String subject = getMessage(request, "post_trade_pm_subject")+trade.get("subject");
					String message = "[url="+boardurl+"viewthread.jsp?do=tradeinfo&tid="+trade.get("tid")+"&pid="+tradepid+"]"+trade.get("subject")+"[/url]\n";
					message += Common.toDigit(trade.get("costprice"))>0?getMessage(request, "post_trade_costprice")+":"+trade.get("costprice")+"\n":"";
					message += getMessage(request, "post_trade_price_pm")+":"+trade.get("price")+"\n";
					message += getMessage(request, "post_trade_transport_type")+":";
					String transport = trade.get("transport");
					if(transport.equals("1")){
						message += getMessage(request, "post_trade_transport_seller");
					}else if(transport.equals("2")){
						message += getMessage(request, "post_trade_transport_buyer");
					}else if(transport.equals("3")){
						message += getMessage(request, "post_trade_transport_virtual");
					}else if(transport.equals("4")){
						message += getMessage(request, "post_trade_transport_physical");
					}
					if(transport.equals("1") || transport.equals("2")||transport.equals("4")){
						if(Common.toDigit(trade.get("ordinaryfee"))>0){
							message += ","+getMessage(request, "post_trade_transport_mail")+" "+trade.get("ordinaryfee")+" "+getMessage(request, "rmb_yuan");
						}
						if(Common.toDigit(trade.get("expressfee"))>0){
							message += ","+getMessage(request, "post_trade_transport_express")+" "+trade.get("expressfee")+" "+getMessage(request, "rmb_yuan");
						}
						if(Common.toDigit(trade.get("emsfee"))>0){
							message += ",EMS "+trade.get("emsfee")+" "+getMessage(request, "rmb_yuan");
						}
					}
					message +="\n"+getMessage(request, "post_trade_locus_pm")+":"+trade.get("locus")+"\n\n";
					message += getMessage(request, "magics_amount_buy")+": \n";
					message += getMessage(request, "post_trade_pm_wishprice")+": \n";
					message += getMessage(request, "post_trade_pm_reason")+": \n";
					Pms pms = new Pms();
					pms.setSubject(subject);
					pms.setMessage(message);
					request.setAttribute("pms", pms);
				}
			}
			List<Map<String,String>>buddylist = dataBaseService.executeQuery("select b.buddyid,m.username from jrun_buddys as b left join jrun_members as m on b.buddyid=m.uid where b.uid = "+uid);
			if (buddylist != null && buddylist.size() > 0) {
				request.setAttribute("buddylist", buddylist);
			} else {
				request.setAttribute("buddylist", null);
			}
			Members members = memberService.findMemberById(convertInt(uids));
			request.setAttribute("member",members);
			setExtcredits(request);
			return mapping.findForward("todisplay");
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward sendPms(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		Members fromMember = (Members)session.getAttribute("user");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		Map<String,String> settings = ForumInit.settings;
		String msgto = request.getParameter("msgto");
		String msgtobuddys[] = request.getParameterValues("msgtobuddys[]");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		byte jsprun_adminid = (Byte) session.getAttribute("jsprun_adminid");
		int lastpost=fromMember!=null?fromMember.getLastpost():0;
		int newbiespan=Common.toDigit(settings.get("newbiespan"));
		if(jsprun_adminid==0 && newbiespan>0 && (lastpost==0 || timestamp - lastpost < newbiespan * 3600)) {
			int regdate=fromMember!=null?fromMember.getRegdate():0;
			if(timestamp - regdate < newbiespan * 3600) {
				request.setAttribute("errorInfo", getMessage(request, "pm_newbie_span", String.valueOf(newbiespan)));
				return mapping.findForward("showMessage");
			}
		}
		if(Common.empty(subject)||Common.empty(message))
		{
			request.setAttribute("errorInfo", getMessage(request, "post_subject_and_message_isnull"));
			return mapping.findForward("showMessage");
		}
		String saveoutbox = request.getParameter("saveoutbox");
		Map seccodedata = dataParse.characterParse(settings.get("seccodedata"),false);
		int minposts = Common.toDigit(String.valueOf(seccodedata.get("minposts")));
		int seccodestatus = Common.range(Common.intval(settings.get("seccodestatus")), 255, 0);
		boolean seccodecheck = (seccodestatus & 8) > 0&& (minposts <= 0 || fromMember.getPosts() < minposts);
		Map secqaa = dataParse.characterParse(settings.get("secqaa"),false);
		minposts = Common.toDigit(String.valueOf(secqaa.get("minposts")));
		int secqaastatus = (Integer)secqaa.get("status");
		boolean secqaacheck = (secqaastatus & 4) > 0 && (minposts <= 0 || fromMember.getPosts() < minposts);
		String seccodeverify = request.getParameter("seccodeverify");
		String secanswer = request.getParameter("secanswer");
		if (seccodecheck) {
			if (!seccodeverify.equalsIgnoreCase((String)request.getSession().getAttribute("rand"))) {
				request.setAttribute("errorInfo", getMessage(request, "submit_seccode_invalid"));
				return mapping.findForward("showMessage");
			}
		}
		if (secqaacheck) {
			if (!secanswer.trim().equals(request.getSession().getAttribute("answer").toString())) {
				request.setAttribute("errorInfo", getMessage(request, "submit_secqaa_invalid"));
				return mapping.findForward("showMessage");
			}
		}
		try{
			if(submitCheck(request, "pmssubmit")){
				String ss = "";
				List<Map<String,String>> wordlist = dataBaseService.executeQuery("select * from jrun_words");
				if(wordlist!=null && wordlist.size()>0){
					for(Map<String,String> word :wordlist){
						if(Common.matches(message,word.get("find"))){
							if(word.get("replacement").equals("{BANNED}")){
								request.setAttribute("errorInfo", getMessage(request, "word_banned"));
								return mapping.findForward("showMessage");
							}else if(word.get("replacement").equals("{MOD}")){
							}else{
								message = message.replaceAll(word.get("find"),word.get("replacement"));
							}
						}
						if(Common.matches(subject,word.get("find"))){
							if(word.get("replacement").equals("{BANNED}")){
								request.setAttribute("errorInfo", getMessage(request, "word_banned"));
								return mapping.findForward("showMessage");
							}else if(word.get("replacement").equals("{MOD}")){
							}else{
								subject = subject.replaceAll(word.get("find"),word.get("replacement"));
							}
						}
					}
				}
				wordlist = null;
				message = Common.cutstr(message, 40000, "");
				subject = Common.htmlspecialchars(subject);
				List<Pms> pmslist = new ArrayList();
				List<Map<String,String>> members = dataBaseService.executeQuery("select m.uid,m.username,mm.ignorepm,u.maxpmnum from jrun_members m left join jrun_memberfields mm on m.uid=mm.uid left join jrun_usergroups as u on m.groupid=u.groupid where m.username=?",Common.addslashes(msgto.trim()));
				if ((msgto != null && !msgto.equals("") && members != null && members.size()>0) || msgtobuddys != null) {
					if (msgto != null && !msgto.equals("") && members.size()>0) {
						Map<String,String> member = members.get(0);
						String ignore = member.get("ignorepm");
						if (!ignore.equals("")) {
							if (ignore.toLowerCase().matches("\\{all\\}")) {
								ss = getMessage(request, "pm_send_ignore", member.get("username"));
							} else {
								String ignores[] = ignore.split(",");
								for (int i = 0; i < ignores.length; i++) {
									if (fromMember.getUsername().equals(ignores[i].trim())) {
										ss = getMessage(request, "pm_send_ignore", member.get("username"));
									}
								}
							}
						}
						if (member.get("maxpmnum").equals("0")) {
							ss = getMessage(request, "pm_send_ignore", member.get("username"));
						}
						Pms pms = new Pms();
						pms.setSubject(subject);
						pms.setDateline(timestamp);
						pms.setDelstatus(Byte.valueOf("0"));
						if (saveoutbox != null) {
							pms.setFolder("outbox");
						} else {
							pms.setFolder("inbox");
						}
						pms.setMessage(message);
						pms.setMsgfrom(fromMember.getUsername());
						pms.setMsgfromid(fromMember.getUid());
						pms.setMsgtoid(Common.toDigit(member.get("uid")));
						pms.setNew_(Byte.valueOf("1"));
						dataBaseService.runQuery("update jrun_members set newpm=1 where uid="+member.get("uid"));
						pmslist.add(pms);
					}
					if (msgtobuddys != null) {
						for (int i = 0; i < msgtobuddys.length; i++) {
							List<Map<String,String>> memberslist = dataBaseService.executeQuery("select m.uid,m.username,mm.ignorepm,u.maxpmnum from jrun_members m left join jrun_memberfields mm on m.uid=mm.uid left join jrun_usergroups as u on m.groupid=u.groupid where m.uid=?",msgtobuddys[i]);
							Map<String,String> member = memberslist.get(0);; memberslist=null;
							String ignore = member.get("ignorepm");
							if (!ignore.equals("")) {
								if (ignore.indexOf("{all}")!=-1) {
									ss = getMessage(request, "pm_send_ignore", member.get("username"));
								} else {
									String ignores[] = ignore.split(",");
									for (int j = 0; j < ignores.length; j++) {
										if (fromMember.getUsername().equals(ignores[j].trim())) {
											ss = getMessage(request, "pm_send_ignore", member.get("username"));
										}
									}
								}
							}
							if (member.get("maxpmnum").equals("0")) {
								ss = getMessage(request, "pm_send_ignore", member.get("username"));
							}
							Pms pms = new Pms();
							pms.setSubject(subject);
							pms.setDateline(timestamp);
							pms.setDelstatus(Byte.valueOf("0"));
							if (saveoutbox != null) {
								pms.setFolder("outbox");
							} else {
								pms.setFolder("inbox");
							}
							pms.setMessage(message);
							pms.setMsgfrom(fromMember.getUsername());
							pms.setMsgfromid(fromMember.getUid());
							pms.setMsgtoid(Common.toDigit(member.get("uid")));
							pms.setNew_(Byte.valueOf("1"));
							dataBaseService.runQuery("update jrun_members set newpm=1 where uid="+member.get("uid"));
							pmslist.add(pms);
						}
					}
				} else {
					ss = getMessage(request, "pm_send_nonexistence");
				}
				if (ss.equals("")) {
					if (saveoutbox != null) {
						request.setAttribute("successInfo", getMessage(request, "pm_saved_succeed"));
						request.setAttribute("requestPath", "pm.jsp?folder=outbox");
						pmsServer.insertPmsList(pmslist);
					} else {
						String messages = null;
						Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
						Map<Integer, Integer> postcredits=(Map<Integer,Integer>)creditspolicys.get("pm");
						Map extcredits = dataParse.characterParse(settings.get("extcredits"), true);
						Set<Entry<Integer,Integer>> keys = postcredits.entrySet();
						for (Entry<Integer,Integer> temp : keys) {
							Integer key = temp.getKey();
							Map extcreditmap = (Map)extcredits.get(key);
							if(extcreditmap!=null){
								int extcredit = fromMember==null?0:(Integer)Common.getValues(fromMember, "extcredits"+key);
								int getattacreditvalue = temp.getValue();
								String lowerlimit = extcreditmap.get("lowerlimit")==null?"0":String.valueOf(extcreditmap.get("lowerlimit"));
								if(getattacreditvalue!=0&&extcredit-getattacreditvalue<=Integer.valueOf(lowerlimit)){
									String unit = extcreditmap.get("unit")!=null?"":extcreditmap.get("unit").toString();
									String title = extcreditmap.get("title")!=null?"":extcreditmap.get("title").toString();
									messages = getMessage(request, "credits_policy_num_lowerlimit", title,lowerlimit,unit);
									break;
								}
							}
						}
						if(messages==null && uid!=0){
							Common.updatepostcredits("-", uid, 1,postcredits);
							Common.updatepostcredits(uid, settings.get("creditsformula"));
						}else{
							request.setAttribute("errorInfo", messages);
							return mapping.findForward("showMessage");
						}
						pmsServer.insertPmsList(pmslist);
						if(Common.isshowsuccess(session, "pm_send_succeed")){
							Common.requestforward(response, "pm.jsp?folder=inbox");
							return null;
						}else{
							request.setAttribute("successInfo", getMessage(request, "pm_send_succeed"));
							request.setAttribute("requestPath", "pm.jsp?folder=inbox");
						}
					}
				} else {
					request.setAttribute("errorInfo", ss);
				}
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("resultInfo",getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	@SuppressWarnings("unchecked")
	public ActionForward searchPms(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setExtcredits(request);
		Map<String, String> settings = ForumInit.settings;
		String srchtxt = request.getParameter("srchtxt"); 
		String srchuname = request.getParameter("srchuname"); 
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		int cachelife_text = 3600;
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String searchid = request.getParameter("searchid");
		String orderby = request.getParameter("orderby");
		String ascdesc = request.getParameter("ascdesc");
		orderby = "msgfrom".equals(orderby) ? "msgfrom" : "dateline";
		ascdesc = "asc".equals(ascdesc) ? "asc" : "desc";
		if (searchid != null && !searchid.equals("")) {
			String srchfolder = request.getParameter("srchfolder"); 
			Pmsearchindex searchindex = pmsServer.findPmsearchindexById(convertInt(searchid));
			String pmids = searchindex.getPmids();
			if (pmids.equals("0")) {
				request.setAttribute("pmslist", null);
			} else {
				Members member=(Members)session.getAttribute("user");
				int pmnum=Integer.valueOf(dataBaseService.executeQuery("select count(*) count from jrun_pms as p where p.pmid in ( " + pmids + " )").get(0).get("count"));
				int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
				int page =Math.max(Common.intval(request.getParameter("page")), 1);
				Map<String,Integer> multiInfo=Common.getMultiInfo(pmnum, tpp, page);
				page=multiInfo.get("curpage");
				int start_limit=multiInfo.get("start_limit");
				Map<String,Object> multi=Common.multi(pmnum, tpp, page, "pm.jsp?action=search&searchid=" + searchid + "&orderby=" + orderby + "&ascdesc=" + ascdesc + "&srchfolder=" + srchfolder, 0, 10, true, false, null);
				request.setAttribute("multi", multi);
				request.setAttribute("searchid", searchid);
				String hql = "select p.*,m.username from jrun_pms as p left join jrun_members as m on p.msgtoid=m.uid  where p.pmid in ( " + pmids + " ) order by " + orderby + " " + ascdesc+" limit "+start_limit+","+tpp;
				List<Map<String,String>> pmslist = dataBaseService.executeQuery(hql);
				if (pmslist != null && pmslist.size()>0) {
					searchindex.setKeywords(Common.encode(searchindex.getKeywords()));
					request.setAttribute("index", searchindex);
					request.setAttribute("pmslist", pmslist);
				} else {
					request.setAttribute("pmslist", null);
				}
			}
			return mapping.findForward("todisplay");
		} else {
			try{
				if(submitCheck(request, "searchsubmit")){
					String srchtype = request.getParameter("srchtype"); 
					Map<String,String> usergroups=(Map<String,String>)request.getAttribute("usergroups");
					if(usergroups.get("allowsearch").equals("2")&& srchtype.equals("fulltext")) {
						MessageResources resources = getResources(request);
						Locale locale = getLocale(request);
						String message=Common.periodscheck(settings.get("searchbanperiods"), Byte.valueOf(usergroups.get("disableperiodctrl")),timestamp,settings.get("timeoffset"),resources,locale);
						if(message!=null)
						{
							request.setAttribute("show_message", message);
							return mapping.findForward("nopermission");
						}
					} else if(!srchtype.equals("title")||!srchtype.equals("blog")) {
						srchtype = "title";
					}
					srchtxt = srchtxt == null ? "" : srchtxt;
					srchuname = srchuname == null ? "" : srchuname;
					if (srchtxt.equals("")&&srchuname.equals("")) {
						request.setAttribute("errorInfo", getMessage(request, "search_invalid"));
						return mapping.findForward("showMessage");
					}
					String searchctrl = settings.get("searchctrl"); 
					String maxspm = settings.get("maxspm"); 
					String maxsearchresult = settings.get("maxsearchresults"); 
					String srchfolder = request.getParameter("srchfolder"); 
					String srchread = request.getParameter("srchread"); 
					String srchunread = request.getParameter("srchunread"); 
					String srchfrom = request.getParameter("srchfrom"); 
					String before = request.getParameter("before"); 
					srchread = srchread == null ? "0" : "1";
					srchunread = srchunread == null ? "0" : "1";
					String searchstring = uid + "|" + srchfolder + "|" + srchtype + "|"+ srchtxt + "|" + srchuname + "|" + srchread + "|"+ srchunread + "|" + srchfrom + "|" + before;
					String pmsindexhql = "select searchid from jrun_pmsearchindex as p where p.uid = "+ uid +" AND p.searchstring = '"+ Common.addslashes(searchstring) + "' and p.expiration >" + timestamp;
					List<Map<String,String>> pmssearchindex = dataBaseService.executeQuery(pmsindexhql);
					if (pmssearchindex != null && pmssearchindex.size()>0) {
						try {
							response.sendRedirect("pm.jsp?action=search&searchid="+ pmssearchindex.get(0).get("searchid") + "&orderby=" + orderby + "&ascdesc="+ ascdesc+"&srchfolder=" + srchfolder);
						} catch (IOException e) {
						}
						return null;
					} else {
						if (!searchctrl.equals("0")) {
							int times = timestamp - convertInt(searchctrl);
							String ctrlhql = "select searchid from jrun_pmsearchindex as p where p.uid=" + uid + " and p.dateline >=" + times+" limit 1";
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
							String maxhql = "select count(*) from Pmsearchindex as p where p.uid="+ uid + " and p.dateline>=" + times;
							int count = pmsServer.findPmsCountByHql(maxhql);
							if (count >= convertInt(maxspm)) {
								String message = getMessage(request, "search_toomany", maxspm);
								request.setAttribute("errorInfo", message);
								return mapping.findForward("showMessage");
							}
						}
						String pmshql = "select pmid from jrun_pms as p where p.delstatus <>2 and ";
						if (!srchtxt.equals("")) { 
							srchtxt = srchtxt.replaceAll("\\*", "%");
							srchtxt = srchtxt.toLowerCase();
							if (Common.matches(srchtxt,"[and|\\+|&|\\s+]") && !Common.matches(srchtxt,"[or|\\|]")) {
								srchtxt = srchtxt.replaceAll("( and |&| )", "+");
								String[] keyword = srchtxt.split("\\+");
								if (srchtype.equals("title")) {
									for (int i = 0; i < keyword.length; i++) {
										pmshql += "p.subject like '%" + Common.addslashes(keyword[i].trim()) + "%' and ";
									}
								} else {
									for (int i = 0; i < keyword.length; i++) {
										pmshql += "p.message like '%" + Common.addslashes(keyword[i].trim()) + "'% and ";
									}
								}
							} else {
								srchtxt = srchtxt.replaceAll("( or |\\|)", "+");
								String[] keyword = srchtxt.split("\\+");
								if (srchtype.equals("title")) {
									pmshql = pmshql + "(";
									for (int i = 0; i < keyword.length; i++) {
										pmshql += "p.subject like '%" + Common.addslashes(keyword[i].trim()) + "%' or ";
									}
								} else {
									pmshql = pmshql + "(";
									for (int i = 0; i < keyword.length; i++) {
										pmshql += "p.message like '%" + Common.addslashes(keyword[i].trim()) + "%' or ";
									}
								}
							}
						}
						int lastor = pmshql.lastIndexOf("or");
						if (lastor > 0) {
							pmshql = pmshql.substring(0, lastor);
							pmshql = pmshql + ") and ";
						}
						if (!srchuname.equals("")) { 
							StringBuffer uids = new StringBuffer();
							srchuname = srchuname.replaceAll("\\*", "%");
							String memhql = "select uid from jrun_members as m where m.username like '"+ Common.addslashes(srchuname.trim()) + "' limit 50";
							List<Map<String,String>> memberlist = dataBaseService.executeQuery(memhql);
							if (memberlist != null && memberlist.size() > 0) {
								for (Map<String,String> member : memberlist) {
									uids.append(","+member.get("uid"));
								}
							}
							if(uids.length()>0){
								if (srchfolder.equals("inbox")) {
									pmshql += "p.msgtoid = " + uid + " AND  p.msgtoid in ( " + uids.substring(1)+ " ) and folder='inbox' and ";
								} else if (srchfolder.equals("outbox")) {
									pmshql += "p.msgfromid=" + uid + " AND p.msgtoid in ( " +uids.substring(1)+ " ) and folder='outbox' and ";
								} else {
									pmshql += "p.msgfromid=" + uid + " AND folder='inbox' and p.msgfromid in ( " +uids.substring(1)+ " ) and ";
								}
							}else{
								pmshql +="0 and ";
							}
						} else {
							if (srchfolder.equals("inbox")) {
								pmshql += "p.msgtoid = " + uid + " AND folder='inbox' and ";
							} else if (srchfolder.equals("outbox")) {
								pmshql += "p.msgfromid=" + uid + " AND folder='outbox' and ";
							} else {
								pmshql += "p.msgfromid=" + uid + " AND folder='inbox' and ";
							}
						}
						if (srchread.equals("0") || srchunread.equals("0")) { 
							if (srchread.equals("1")) {
								pmshql += "p.new=0 and ";
							}
							if (srchunread.equals("1")) {
								pmshql += "p.new >0 and ";
							}
						}
						if (!srchfrom.equals("0")) {
							int datetime = timestamp- Integer.valueOf(srchfrom);
							if (before.equals("0")) {
								pmshql += "p.dateline >= " + datetime + " and ";
							} else {
								pmshql += "p.dateline <" + datetime + " and ";
							}
						}
						int lastand = pmshql.lastIndexOf("and");
						if (lastand > 0) {
							pmshql = pmshql.substring(0, lastand);
						}
						pmshql += "order by " + orderby + " " + ascdesc+" limit 0,"+maxsearchresult;
						List<Map<String,String>> pmslist = dataBaseService.executeQuery(pmshql);
						String pmsids = "0";
						int pmscount = 0;
						if (pmslist != null && pmslist.size() > 0) {
							pmscount = pmslist.size();
							for (Map<String,String> pms : pmslist) {
								pmsids += ","+pms.get("pmid");
							}
						}
						Pmsearchindex pmsserch = new Pmsearchindex();
						pmsserch.setDateline(timestamp);
						int exprtation = timestamp + cachelife_text;
						pmsserch.setExpiration(exprtation);
						pmsserch.setKeywords(srchtxt + "+" + srchuname);
						pmsserch.setPmids(pmsids);
						pmsserch.setPms(Short.valueOf(pmscount + ""));
						pmsserch.setUid(uid);
						pmsserch.setSearchstring(searchstring);
						int searchids = pmsServer.insertPmsearchindex(pmsserch);
						try {
							response.sendRedirect("pm.jsp?action=search&searchid="+ searchids + "&orderby=" + orderby + "&ascdesc="+ ascdesc+"&srchfolder=" + srchfolder);
						} catch (IOException e) {}
						return null;
					}
				}
			}catch (Exception e) {
				request.setAttribute("resultInfo",e.getMessage());
				return mapping.findForward("showMessage");
			}
			Common.requestforward(response, "pm.jsp?action=search");
			return null;
		}
	}
	public ActionForward archive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String timeoffset=(String)session.getAttribute("timeoffset");
		int pmid = Common.intval(request.getParameter("pmid"));
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		try{
			if(submitCheck(request, "archivesubmit")||pmid >0){
				if (pmid >0) {
					List<Map<String,String>> pmslist = dataBaseService.executeQuery("select p.*,m.username from jrun_pms as p left join jrun_members as m on p.msgtoid=m.uid where p.pmid="+pmid);
					Map<String,String> pms = pmslist.get(0);
					String message = pms.get("message").replace("$", Common.SIGNSTRING);
					message = Common.htmlspecialchars(message);
					Jspruncode jspruncode = new Jspruncode();
					message = jspruncode.parseCodeP(message,resources,locale,false);
					message = jspruncode.parseJsprunCode(message, resources, locale);
					message = jspruncode.parseimg(message,true);
					message = relacesmile(message);
					message = message.replaceAll("\\n", "<br/>");
					int count = jspruncode.getCodecount();
					List<String> codelist = jspruncode.getCodelist();
					for(int i=0;i<count;i++){
						String str = codelist.get(i);
						message = StringUtils.replace(message, "[\tJSPRUN_CODE_"+i+"\t]", str);
					}
					message = message.replace(Common.SIGNSTRING, "$");
					pms.put("message",message);
					pmslist.clear();
					pmslist.add(pms);
					request.setAttribute("pmslist", pmslist);
				} else {
					String folder = request.getParameter("folder");
					String amount = request.getParameter("amount");
					String delete = request.getParameter("delete");
					String days = request.getParameter("days");
					String newerolder = request.getParameter("newerolder");
					String pmshql = "select p.*,m.username from jrun_pms as p left join jrun_members as m on p.msgtoid=m.uid where p.delstatus<>2 and ";
					if ("inbox".equals(folder)) {
						pmshql += "p.msgtoid = " + uid + " AND folder='inbox' and ";
					} else if ("outbox".equals(folder)) {
						pmshql += "p.msgfromid=" + uid + " AND folder='outbox' and ";
					}
					if (days!=null&&!days.equals("0")) {
						int datetime = timestamp - Integer.valueOf(days);
						if (newerolder.equals("0")) {
							pmshql += "p.dateline >= " + datetime + " and ";
						} else {
							pmshql += "p.dateline <" + datetime + " and ";
						}
					}
					int lastand = pmshql.lastIndexOf("and");
					if (lastand > 0) {
						pmshql = pmshql.substring(0, lastand);
					}
					int max = convertInt(amount);
					if (max == 0) {
						max = 1000;
					}
					pmshql += " order by p.dateline desc limit 0,"+max;
					List<Map<String,String>> pmslist = dataBaseService.executeQuery(pmshql);
					StringBuffer pmids = new StringBuffer();
					if (pmslist != null && pmslist.size()>0) {
						for (Map<String,String> pms : pmslist) {
							String message = pms.get("message").replace("$", Common.SIGNSTRING);
							message = Common.htmlspecialchars(message);
							Jspruncode jspruncode = new Jspruncode();
							message = jspruncode.parseCodeP(message,resources,locale,false);
							message = jspruncode.parseJsprunCode(message,resources,locale);
							message = jspruncode.parseimg(message,true);
							message = relacesmile(message);
							message = message.replaceAll("\\n", "<br/>");
							int count = jspruncode.getCodecount();
							List<String> codelist = jspruncode.getCodelist();
							for(int i=0;i<count;i++){
								String str = codelist.get(i);
								message = StringUtils.replace(message, "[\tJSPRUN_CODE_"+i+"\t]", str);
							}
							message = message.replace(Common.SIGNSTRING, "$");
							pms.put("message", message);
							pmids.append(","+pms.get("pmid"));
						}
						request.setAttribute("pmslist", pmslist);
					}
					if(delete != null && pmids.length()>0){
						String deleteadd = "inbox".equals(folder)?" and delstatus='1'":"";
						dataBaseService.runQuery("DELETE FROM jrun_pms WHERE pmid IN ("+pmids.substring(1)+") "+deleteadd,true);
						if(!deleteadd.equals("")) {
							dataBaseService.runQuery("UPDATE jrun_pms SET delstatus='2' WHERE pmid IN ("+pmids.substring(1)+")",true);
						}
					}
				}
				Members member = (Members)session.getAttribute("user");
				if(member==null){
					request.setAttribute("show_message", getMessage(request, "group_nopermission", getMessage(request, "guest")));
					return mapping.findForward("nopermission");
				}
				String username = member.getUsername();
				String filename = "PM_"+Common.encodeText(request, username)+"_"+Common.gmdate("yyMMdd_HHmm", timestamp,timeoffset)+".htm";
				request.setAttribute("timestamp", timestamp);
				response.setHeader("Content-Type", "application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename="+ filename);
				response.setHeader("Expires", "0");
				response.setHeader("Pragma", "no-cache");
				return mapping.findForward("archives");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("resultInfo",getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	public ActionForward toignore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		setExtcredits(request);
		int uid = (Integer) session.getAttribute("jsprun_uid");
		Memberfields membefile = memberService.findMemberfieldsById(uid);
		request.setAttribute("memberfild", membefile);
		return mapping.findForward("todisplay");
	}
	public ActionForward saveignore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "ignoresubmit")){
				HttpSession session = request.getSession();
				int uid = (Integer) session.getAttribute("jsprun_uid");
				String ignorelist = request.getParameter("ignorelist");
				if (ignorelist != null) {
					dataBaseService.runQuery("update jrun_memberfields set ignorepm='"+Common.addslashes(ignorelist)+"' where uid="+uid);
				}
				if(Common.isshowsuccess(session, "pm_ignore_succeed")){
					Common.requestforward(response, "pm.jsp?folder=inbox");
					return null;
				}else{
					request.setAttribute("successInfo", getMessage(request, "pm_ignore_succeed"));
					request.setAttribute("requestPath", "pm.jsp?folder=inbox");
					return mapping.findForward("showMessage");
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		Common.requestforward(response, "pm.jsp?action=ignore");
		return null;
	}
	public ActionForward deletepms(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int jsprun_uid = (Integer)request.getSession().getAttribute("jsprun_uid");
		String folder = request.getParameter("folder");
		folder = folder==null||folder.equals("")?"inbox":folder;
		try{
			if(submitCheck(request, "deletesubmit",true)){
				String pmid = request.getParameter("pmid");
				String pmssql = "";
				String delete[] = request.getParameterValues("delete[]");
				if(delete!=null){
					for (int i = 0; i < delete.length; i++) {
						pmid = pmid+","+delete[i];
					}
					pmssql = "pmid in ( "+Common.addslashes(pmid)+" )";
				}else if(pmid!=null&&Common.isNum(pmid)){
					pmssql = "pmid = '"+Common.intval(pmid)+"'";
				}
				if(!pmssql.equals("")){
					String sql = "";
					String msg_field = "";
					int deletestatus = 0;
					if(folder.equals("inbox")) {
						sql = "folder='inbox' AND msgtoid='"+jsprun_uid+"' AND "+pmssql+" AND (delstatus=1 OR msgfromid=0)";
						msg_field = "msgtoid";
						deletestatus = 2;
					} else if(folder.equals("track")) {
						sql = "folder='inbox' AND msgfromid='"+jsprun_uid+"' AND "+pmssql+" AND delstatus=2";
						msg_field = "msgfromid";
						deletestatus = 1;
					} else {
						sql = "folder='outbox' AND msgfromid='"+jsprun_uid+"' AND "+pmssql;
						msg_field = "msgfromid";
					}
					dataBaseService.runQuery("DELETE FROM jrun_pms WHERE "+sql,true);
					if(deletestatus>0){
						dataBaseService.runQuery("UPDATE jrun_pms SET delstatus='"+deletestatus+"' WHERE "+msg_field+"='"+jsprun_uid+"' AND "+pmssql,true);
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		String inajax = request.getParameter("inajax");
		if(inajax!=null){
			Common.setResponseHeader(response);
			try {
				response.getWriter().write(getMessage(request, "pm_delete_succeed"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		if(Common.isshowsuccess(request.getSession(), "pm_delete_succeed")){
			Common.requestforward(response, "pm.jsp?folder="+folder);
			return null;
		}else{
			request.setAttribute("successInfo", getMessage(request, "pm_delete_succeed"));
			request.setAttribute("requestPath", "pm.jsp?folder="+folder);
			return mapping.findForward("showMessage");
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward searchPmsallow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setExtcredits(request);
		Map<String, String> usergroup = (Map<String, String>) request.getAttribute("usergroups");
		String allowsearch = usergroup.get("allowsearch");
		if (allowsearch.equals("0")) {
			request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroup.get("grouptitle")));
			return mapping.findForward("nopermission");
		} else {
			return mapping.findForward("searchpms");
		}
	}
	@SuppressWarnings("unchecked")
	private void setExtcredits(HttpServletRequest request) {
		Map<String, String> settings = ForumInit.settings;
		request.setAttribute("extcredits", dataParse.characterParse(settings.get("extcredits"),true));
	}
	public ActionForward sendorajax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String,String> settings = ForumInit.settings;
		int uid = (Integer) session.getAttribute("jsprun_uid");
		Members fromMember = memberService.findMemberById(uid);
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String msgto = request.getParameter("msgto");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		byte jsprun_adminid = (Byte) session.getAttribute("jsprun_adminid");
		int lastpost=fromMember!=null?fromMember.getLastpost():0;
		int newbiespan=Common.toDigit(settings.get("newbiespan"));
		if(jsprun_adminid==0 && newbiespan>0 && (lastpost==0 || timestamp - lastpost < newbiespan * 3600)) {
			int regdate=fromMember!=null?fromMember.getRegdate():0;
			if(timestamp - regdate < newbiespan * 3600) {
				Common.writeMessage(response,getMessage(request, "ajax_pm_newbie_span", String.valueOf(newbiespan)),true);
				return null;
			}
		}
		String ss = "";
		if(subject==null || subject.equals("") || message==null || message.equals("")){
			ss =  getMessage(request, "ajax_pm_send_invalid");
			Common.writeMessage(response,ss,true);
			return null;
		}
		try{
			if(submitCheck(request, "pmssubmit")){
				List<Pms> pmslist = new ArrayList<Pms>();
				List<Map<String,String>> wordlist = dataBaseService.executeQuery("select * from jrun_words");
				if(wordlist!=null && wordlist.size()>0){
					for(Map<String,String> word :wordlist){
						if(Common.matches(message,word.get("find"))){
							if(word.get("replacement").equals("{BANNED}")){
								Common.writeMessage(response,getMessage(request, "word_banned"),true);
								return null;
							}else if(word.get("replacement").equals("{MOD}")){
							}else{
								message = message.replaceAll(word.get("find"),word.get("replacement"));
							}
						}
						if(Common.matches(subject,word.get("find"))){
							if(word.get("replacement").equals("{BANNED}")){
								Common.writeMessage(response,getMessage(request, "word_banned"),true);
								return null;
							}else if(word.get("replacement").equals("{MOD}")){
							}else{
								subject = subject.replaceAll(word.get("find"),word.get("replacement"));
							}
						}
					}
				}
				wordlist = null;
				message = Common.cutstr(message, 40000, "");
				subject = Common.htmlspecialchars(subject);
				Members members = msgto==null?null:memberService.findByName(msgto.trim());
				if (msgto != null && !msgto.equals("") && members != null) {
					Usergroups usergroup = userGroupService.findUserGroupById(members.getGroupid());
					Memberfields memberfild = memberService.findMemberfieldsById(members.getUid());
					String ignore = memberfild.getIgnorepm();
						if (!ignore.equals("")) {
							if (ignore.equals("{all}")) {
								ss = getMessage(request, "pm_send_ignore", members.getUsername());
								Common.writeMessage(response,ss,true);
								return null;
							} else {
								String ignores[] = ignore.split(",");
								for (int i = 0; i < ignores.length; i++) {
									if (msgto.equals(ignores[i])) {
										ss = getMessage(request, "pm_send_ignore", members.getUsername());
										Common.writeMessage(response,ss,true);
										return null;
									}
								}
							}
						}
						if (usergroup.getMaxpmnum() == 0) {
							ss = getMessage(request, "pm_send_ignore", members.getUsername());
							Common.writeMessage(response,ss,true);
							return null;
						}
						Pms pms = new Pms();
						pms.setSubject(subject);
						pms.setDateline(timestamp);
						pms.setDelstatus(Byte.valueOf("0"));
						pms.setFolder("inbox");
						pms.setMessage(message);
						pms.setMsgfrom(fromMember.getUsername());
						pms.setMsgfromid(fromMember.getUid());
						pms.setMsgtoid(members.getUid());
						pms.setNew_(Byte.valueOf("1"));
						members.setNewpm(Byte.valueOf("1"));
						memberService.modifyMember(members);
						pmslist.add(pms);
				} else {
					ss = getMessage(request, "pm_send_nonexistence");
					Common.writeMessage(response,ss,true);
					return null;
				}
				if (ss.equals("")) {
					pmsServer.insertPmsList(pmslist);
					Common.writeMessage(response,getMessage(request, "ajax_pm_send_succeed"),false);
					return null;
				} 
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		Common.writeMessage(response,getMessage(request, "undefined_action_return"),false);
		return null;
	}
	public ActionForward announcearchive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		String jsprun_userss = (String)session.getAttribute("jsprun_userss");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String timeoffset= (String)session.getAttribute("timeoffset");
		int pmid = Common.intval(request.getParameter("pmid"));
		if (pmid >0 ) {
			String sql="SELECT a.starttime as dateline,'"+getMessage(request, "a_other_ann_pms")+"' as msgfrom,'0' as msgfromid,a.subject,'"+jsprun_userss+"' as username,"+uid+" as msgtoid,'inbox' as folder,a.message from jrun_announcements a where id="+pmid;
			List<Map<String,String>> announlists=dataBaseService.executeQuery(sql);
			request.setAttribute("pmslist", announlists);
		} 
		String filename = "AnnouncePM_"+Common.encodeText(request, jsprun_userss)+"_"+Common.gmdate("yyMMdd_HHmm", timestamp,timeoffset)+".htm";
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="+ filename);
		response.setHeader("Expires", "0");
		response.setHeader("Pragma", "no-cache");
		return mapping.findForward("archives");
	}
	public ActionForward noprompt(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		int uid=(Integer)session.getAttribute("jsprun_uid");
		dataBaseService.runQuery("UPDATE jrun_pms SET new='2' WHERE msgtoid='"+uid+"' AND folder='inbox' AND delstatus!='2' AND new='1'", true);
		dataBaseService.runQuery("UPDATE jrun_members SET newpm='0' WHERE uid='"+uid+"'", true);
		return null;
	}
}