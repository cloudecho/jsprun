package cn.jsprun.struts.action;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Members;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.Md5Token;
public class AdmincpAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward admincp(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String,String> settings=ForumInit.settings;
		byte adminid = (Byte)session.getAttribute("jsprun_adminid");
		int cpaccess = 1;
		int errorcount=0;
		String onlineip = Common.get_onlineip(request);
		int uid = (Integer) session.getAttribute("jsprun_uid");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		if (adminid <= 0) {
			cpaccess = 0;
		} else {
			String adminipaccess = settings.get("adminipaccess");
			if (!"".equals(adminipaccess)&&adminid == 1 &&!Common.ipaccess(onlineip,adminipaccess)) {
				cpaccess=2;
			}else{
				String addonlineip = Common.toDigit(settings.get("admincp_checkip"))>0? " AND ip='"+onlineip+"'" : "";
				List<Map<String,String>> errorcounts=dataBaseService.executeQuery("SELECT errorcount FROM jrun_adminsessions WHERE uid='"+uid+"'"+addonlineip+" AND dateline>'"+(timestamp-1800)+"'");
				if(errorcounts!=null&&errorcounts.size()>0){
					errorcount=Integer.valueOf(errorcounts.get(0).get("errorcount"));
					if(errorcount==-1){
						dataBaseService.runQuery("UPDATE jrun_adminsessions SET dateline='"+timestamp+"' WHERE uid='"+uid+"'",true);
						cpaccess = 3;
					} else if (errorcount <=3) {
						cpaccess = 1;
					}else{
						cpaccess = 0;
					}
				}else{
					dataBaseService.runQuery("DELETE FROM jrun_adminsessions WHERE uid='"+uid+"' OR dateline<'"+(timestamp-1800)+"'",true);
					dataBaseService.runQuery("INSERT INTO jrun_adminsessions (uid, ip, dateline, errorcount) VALUES ('"+uid+"', '"+onlineip+"', '"+timestamp+"', '0')",true);
					cpaccess = 1;
				}
			}
		}
		String jsprun_userss=(String)session.getAttribute("jsprun_userss");
		if(jsprun_userss==null){
			jsprun_userss="";
		}
		String action=request.getParameter("action");
		Members user  = (Members)session.getAttribute("user");
		if(action!=null&&!("|main|header|menu|illegallog|ratelog|modslog|medalslog|creditslog|banlog|cplog|errorlog|".contains("|"+action+"|"))){
			String extra=null;
			switch(cpaccess){
				case 0:
					extra="PERMISSION DENIED";
					break;
				case 1:
					extra="AUTHENTIFICATION(ERROR #"+errorcount+")";
					break;
				case 2:
					extra="IP ACCESS DENIED";
					break;
				case 3:
					StringBuffer extraBuffer=new StringBuffer();
					Map<String,String[]> map=request.getParameterMap();
					if(map!=null&&map.size()>0){
						Iterator<Entry<String,String[]>> keys = map.entrySet().iterator();
						while(keys.hasNext()){
							Entry<String,String[]> e= keys.next();
							String key = e.getKey();
							if(!("|action|adminaction|sid|formhash|admin_password|".contains("|"+key+"|"))){
								String[] values=e.getValue();
								if(values!=null){
									int length=values.length;
									if(length>1){
										extraBuffer.append("; "+key+"=Array(");
										for(int i=0;i<length;i++){
											extraBuffer.append(i+"="+Common.cutstr(values[i], 15, null)+"; ");
										}
										extraBuffer.append(")");
									}else if(!"".equals(values[0])){
										extraBuffer.append("; "+key+"=");
										extraBuffer.append(Common.cutstr(values[0], 15, null));
									}
								}
							}
						}
					}
					extra=extraBuffer.length()>2?Common.htmlspecialchars(extraBuffer.substring(2)):"";
					break;
			}
			Log.writelog("cplog", timestamp, timestamp + "\t" + jsprun_userss + "\t" + adminid + "\t" + onlineip + "\t" + action + "\t"+("home".equals(action)? "" :extra));
		}
		if(cpaccess==0){
			CookieUtil.clearCookies(request, response, settings);
			this.loginmsg(request, new StringBuffer(getMessage(request, "noaccess")), null, null, null, null);
			return mapping.findForward("toAdmincp");
		}else if(cpaccess==1){
			String admin_password=request.getParameter("admin_password");
			String password = admin_password!=null?Md5Token.getInstance().getLongToken(Md5Token.getInstance().getLongToken(admin_password)+user.getSalt()):"";
			if(!password.equals(session.getAttribute("jsprun_pw"))){
				if(admin_password!=null){
					dataBaseService.runQuery("UPDATE jrun_adminsessions SET errorcount=errorcount+1 WHERE uid='"+uid+"'",true);
					Log.writelog("cplog", timestamp, timestamp + "\t" + jsprun_userss + "\t" + adminid + "\t" + onlineip + "\t" + action + "\tAUTHENTIFICATION(PASSWORD)");
				}
				loginmsg(request,null, null, "login", (String)session.getAttribute("jsprun_sid"),(String)session.getAttribute("jsprun_userss"));
				return mapping.findForward("toAdmincp");
			}else{
				dataBaseService.runQuery("UPDATE jrun_adminsessions SET errorcount='-1' WHERE uid='"+uid+"'",true);
				String extra=(String)request.getAttribute("extra");
				this.loginmsg(request, new StringBuffer(getMessage(request, "login_succeed")), "admincp.jsp?"+extra, null, null, null);
				String url_forward=request.getParameter("url_forward");
				if(!Common.empty(url_forward)) {
					try {
						response.getWriter().write("<meta http-equiv=refresh content=\"0;URL="+url_forward+"\">");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}
				return mapping.findForward("toAdmincp");
			}
		}else if(cpaccess==2){
			this.loginmsg(request, new StringBuffer(getMessage(request, "noaccess_ip")), null, null, null, null);
			return mapping.findForward("toAdmincp");
		}
		request.setAttribute("isfounder", Common.isFounder(settings, user));
		session.setAttribute("members", user);
		String frames=request.getParameter("frames");
		String sid=request.getParameter("sid");
		String url=null;
		if((action==null||"yes".equals(frames))&&sid==null){
			url="/admin/index.jsp";
		}else if("header".equals(action)){
			url="/admin/header.jsp";
		}else if("menu".equals(action)){
			url="/admin/menu.jsp";
		}else{
			url="admin/page/main.jsp";
		}
		if(usergroups.get("radminid").equals("1")){
			List<Map<String,String>> dactionarray = dataBaseService.executeQuery("SELECT disabledactions FROM jrun_adminactions WHERE admingid='"+usergroups.get("groupid")+"'");
			if(dactionarray!=null&&dactionarray.size()>0){
				Map<String,String> dactionarrayMap = dataParse.characterParse(dactionarray.get(0).get("disabledactions"),false);
				Set<String> actionskey = dactionarrayMap.keySet();
				for(String key:actionskey){
					String keys[] = key.split(":");
					for(String s:keys){
						if(s.equals(action)){
							request.setAttribute("message_key","action_noaccess");
							return mapping.findForward("message");
						}
					}
				}
			}
		}
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward logout(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("members");
		dataBaseService.runQuery("DELETE FROM jrun_adminsessions WHERE uid='"+session.getAttribute("jsprun_uid")+"'",true);
		this.loginmsg(request, new StringBuffer(getMessage(request, "logout_succeed_admincp")), "index.jsp", null, null, null);
		return mapping.findForward("toAdmincp");
	}
	private void loginmsg(HttpServletRequest request,StringBuffer message,String url_forward,String msgtype,String jsprun_sid,String jsprun_user){
		if(message==null){
			message=new StringBuffer();
		}
		if(url_forward==null){
			url_forward="";
		}
		if(msgtype==null){
			msgtype="message";
		}
		if("message".equals(msgtype)){
			message.insert(0, "<tr><td>&nbsp;</td><td align='center' colspan='3' >");
			if(!"".equals(url_forward)){
				message.append("<br /><br /><a href='"+url_forward+"'>"+getMessage(request, "message_redirect")+"</a>");
				message.append("<script>setTimeout(\"redirect('"+url_forward+"');\", 1250);</script><br /><br /><br /></td><td>&nbsp;</td></tr>");
			}else{
				message.append("<br /><br /><br />");
			}
		}else{
			String adminaction=request.getParameter("adminaction");
			String frames=request.getParameter("frames");
			String extra=(String)request.getAttribute("extra");
			if(extra==null){
				extra="";
			}
			extra = adminaction!=null && frames==null? "?frames=yes"+("".equals(extra)?"":"&"+extra):"?"+extra;
			message.append("<form method='post' name='login' action='admincp.jsp"+extra+"'><input type='hidden' name='sid' value='"+jsprun_sid+"'><input type='hidden' name='frames' value='yes'><input type='hidden' name='url_forward' value='"+url_forward+"'><tr><td>&nbsp;</td><td align='right'>"+getMessage(request, "username")+":</td><td>"+jsprun_user+"</td><td><a href='logging.jsp?action=logout&formhash="+Common.getRandStr(8, false)+"&referer=index.jsp'>"+getMessage(request, "menu_logout")+"</a></td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td align='right'>"+getMessage(request, "password")+":</td><td><input type='password' name='admin_password' id='admin_password' size='25' style='height:18px;'><input type='button' class='keybord_button' style='vertical-align:middle;margin-left: -24px;' onclick=\"jrunKeyBoard('admin_password',this);\" title='"+getMessage(request, "keyboard_title")+"'></td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td class='line1'>&nbsp;</td><td class='line1' align='center'><input type='submit' class='button' value='"+getMessage(request, "submit")+"' /><script language='JavaScript'>document.login.admin_password.focus();</script></td><td class='line1'>&nbsp;</td><td>&nbsp;</td></tr></form>");
		}
		request.setAttribute("message", message.toString());
	}
}