package cn.jsprun.struts.foreg.actions;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Mail;
import cn.jsprun.utils.Md5Token;
public class InviteAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward buyinvite(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		List<Map<String, String>> invitecount = dataBaseService.executeQuery("SELECT COUNT(*) as count FROM jrun_invites WHERE uid='"+jsprun_uid+"' AND dateline>'"+(timestamp-86400)+"' AND dateline<'"+timestamp+"'");
		int myinvitenum = 0;
		Map<String, String> usergroups = (Map<String, String>) request.getAttribute("usergroups");
		int maxinvitenum = Common.toDigit(usergroups.get("maxinvitenum"));
		if(invitecount!=null && invitecount.size()>0){
			 myinvitenum = Integer.valueOf(invitecount.get(0).get("count"));
			if(maxinvitenum!=0 && maxinvitenum==myinvitenum){
				request.setAttribute("errorInfo", getMessage(request, "invite_num_range_invalid"));
				return mapping.findForward("showMessage");
			}
			request.setAttribute("myinvitenum", myinvitenum);
		}
		Map<String, String> settings = ForumInit.settings;
		Map extcredits = dataParse.characterParse(settings.get("extcredits"), true);
		Integer creditstrans=Common.toDigit(settings.get("creditstrans"));
		request.setAttribute("extcredits", extcredits);
		request.setAttribute("creditstrans",creditstrans);
		try{
			if(submitCheck(request, "buysubmit")){
				int amount=Common.toDigit(request.getParameter("amount"));
				int inviteprice=Common.toDigit(usergroups.get("inviteprice"));
				int buyinvitecredit=amount>0?amount*inviteprice:0;
				if(amount<=0){
					request.setAttribute("errorInfo", getMessage(request, "invite_num_invalid"));
					return mapping.findForward("showMessage");
				}
				Members member = (Members)session.getAttribute("user");
				int credits = creditstrans>0?(Integer)getValues(member, "extcredits"+creditstrans):0;
				if(credits<buyinvitecredit&&buyinvitecredit>0){
					String title = ((Map)extcredits.get(creditstrans))==null?"":(String)((Map)extcredits.get(creditstrans)).get("title");
					request.setAttribute("errorInfo", getMessage(request, "invite_credits_no_enough",title));
					return mapping.findForward("showMessage");
				}else if(maxinvitenum>0&&(myinvitenum + amount) > maxinvitenum || amount > 50){
					request.setAttribute("errorInfo", getMessage(request, "invite_num_buy_range_invalid"));
					return mapping.findForward("showMessage");
				}else if(buyinvitecredit>0&&creditstrans==0){
					request.setAttribute("errorInfo", getMessage(request, "magics_credits_no_open"));
					return mapping.findForward("showMessage");
				}else{
					Md5Token md5=Md5Token.getInstance();
					int maxinviteday=Common.toDigit(usergroups.get("maxinviteday"));
					StringBuffer sql=new StringBuffer();
					sql.append("INSERT INTO jrun_invites(uid, dateline, expiration, inviteip, invitecode) VALUES ");
					for(int i=1;i<=amount;i++){
						String invitecode =md5.getLongToken(jsprun_uid+timestamp+Common.getRandStr(6, false)).substring(0,10)+Common.getRandStr(6, false);
						int expiration = timestamp+maxinviteday*86400;
						sql.append("('"+jsprun_uid+"', '"+timestamp+"', '"+expiration+"', '"+Common.get_onlineip(request)+"', '"+invitecode+"'),");
					}
					sql.deleteCharAt(sql.length()-1);
					dataBaseService.runQuery(sql.toString(),true);
					if(buyinvitecredit>0&&creditstrans>0){
						dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"-'"+buyinvitecredit+"' WHERE uid='"+jsprun_uid+"'",true);
						Common.updateMember(session, jsprun_uid);
					}
					request.setAttribute("successInfo", getMessage(request, "invite_buy_succeed"));
					request.setAttribute("requestPath", "invite.jsp");
					return mapping.findForward("showMessage");
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		return mapping.findForward("invite_get");
	}
	@SuppressWarnings("unchecked")
	public ActionForward availablelog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		String operation = request.getParameter("operation");
		if (operation == null) {
			operation = "availablelog";
		}
		Members member = (Members)session.getAttribute("user");
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		int page = Math.max(Common.intval(request.getParameter("page")), 1);
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));;
		int start_limit = (page - 1) * tpp;
		if(start_limit<0){
			page=1;
			start_limit=0;
		}
		String status = null;
		String sql = null;
		if(operation.equals("availablelog")){
			status = "1";
			sql = "SELECT dateline, expiration, invitecode, status	FROM jrun_invites WHERE uid='"+jsprun_uid+"' AND status IN ('1', '3') ORDER BY dateline DESC LIMIT "+start_limit+","+tpp;
		}else if(operation.equals("usedlog")){
			status = "2";
			sql = "SELECT i.dateline, i.expiration, i.invitecode, m.username, m.uid FROM jrun_invites i, jrun_members m WHERE i.uid='"+jsprun_uid+"' AND i.status='2' AND i.reguid=m.uid ORDER BY dateline DESC LIMIT "+start_limit+","+tpp;
		}else{
			status = "4";
			sql = "SELECT dateline, expiration, invitecode FROM jrun_invites WHERE uid='"+jsprun_uid+"' AND status='4' ORDER BY dateline DESC LIMIT "+start_limit+","+tpp;
		}
		List<Map<String, String>> inviteslogs = dataBaseService.executeQuery(sql);
		if(inviteslogs!=null && inviteslogs.size()>0){
			request.setAttribute("inviteslogs", inviteslogs);
		}
		List<Map<String, String>> invitecount = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_invites WHERE uid='"+jsprun_uid+"' AND status='"+status+"'");
		int invitenum = Integer.valueOf(invitecount.get(0).get("count"));
		Map<String,Object> multi=Common.multi(invitenum, tpp, page, "invite.jsp?action=toavailablelog&operation=" + operation, 0, 10, true, false, null);
		request.setAttribute("multi", multi);
		Common.setExtcredits(request);
		return mapping.findForward("invite_log");
	}
	private Object getValues(Object bean, String fieldName) {
		Object paraValue = null;
		try {
			String getMethod = "get" + fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1, fieldName.length());
			Method method = bean.getClass().getMethod(getMethod);
			paraValue = method.invoke(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paraValue;
	}
	@SuppressWarnings("unchecked")
	public ActionForward markinvite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		String undo = request.getParameter("do");
		String invitecode = request.getParameter("invitecode");
		int changestatus = undo==null?3:1;
		if(invitecode!=null){
			dataBaseService.runQuery("update jrun_invites set status='"+changestatus+"' WHERE uid='"+jsprun_uid+"' AND invitecode='"+invitecode+"'");
		}
		List<Map<String, String>> invite = dataBaseService.executeQuery("SELECT invitecode, dateline, expiration FROM jrun_invites WHERE uid='"+jsprun_uid+"' AND invitecode=?",invitecode);
		request.setAttribute("invite", invite.get(0));
		request.setAttribute("changestatus", changestatus);
		return mapping.findForward("invite_log");
	}
	@SuppressWarnings("unchecked")
	public ActionForward sendinvite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		String invitecode = request.getParameter("invitecode");
		if(usergroups==null || usergroups.get("allowmailinvite").equals("0")){
			request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		try{
			if(submitCheck(request, "sendsubmit")){
				Map<String,String> settings=ForumInit.settings;
				String jsprun_userss = (String)(session.getAttribute("jsprun_userss"));
				String sendtoemail = request.getParameter("sendtoemail");
				if(Common.empty(sendtoemail)){
					request.setAttribute("errorInfo", getMessage(request, "email_friend_invalid"));
					return mapping.findForward("showMessage");
				}
				List<Map<String, String>> invite = dataBaseService.executeQuery("SELECT invitecode FROM jrun_invites WHERE uid='"+jsprun_uid+"' and status='1' AND invitecode=?",invitecode);
				if(invite!=null && invite.size()>0){
					dataBaseService.runQuery("UPDATE jrun_invites SET status='3' WHERE uid='"+jsprun_uid+"' AND invitecode='"+invitecode+"'");
					Map<String,String> mails=dataParse.characterParse(settings.get("mail"), false);
					Mail mail=new Mail(mails);
					String message=request.getParameter("message");
					String boardurl = (String) session.getAttribute("boardurl");
					String fromemail = settings.get("bbname")+" <"+mails.get("from")+">";
					mail.sendMessage(fromemail,"<"+sendtoemail+">",getMessage(request, "email_to_invite_subject", jsprun_userss,settings.get("bbname")),getMessage(request, "email_to_invite_message", settings.get("bbname"),jsprun_userss,message,boardurl),null);
					request.setAttribute("successInfo", getMessage(request, "email_invite_succeed"));
					request.setAttribute("requestPath", "invite.jsp");
					return mapping.findForward("showMessage");
				}else{
					request.setAttribute("errorInfo", getMessage(request, "invite_invalid"));
					return mapping.findForward("showMessage");
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		Common.setExtcredits(request);
		Map<String,String> settings = ForumInit.settings;
		request.setAttribute("invitecode", invitecode);
		Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
		String fromuid = !Common.empty(creditspolicys.get("promotion_register")) ? "&amp;fromuid="+jsprun_uid : "";
		request.setAttribute("fromuid", fromuid);
		return mapping.findForward("invite_send");
	}
}