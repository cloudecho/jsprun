package cn.jsprun.struts.action;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FormDataCheck;
public class PmpruneAction extends BaseAction {
	public ActionForward batchPmprune(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "prunesubmit")){
				String ignorenew = request.getParameter("ignorenew");   
				String days = request.getParameter("days");           
				String cins = request.getParameter("cins");        
				String users = request.getParameter("users");    
				String srchtype = request.getParameter("srchtype");  
				String srchtxt = request.getParameter("srchtxt");   
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				if (!FormDataCheck.isValueString(days)) {
					String info = getMessage(request, "a_system_prune_pm_range_invalid");
					request.setAttribute("message", info);
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				StringBuffer sqlbuffer = new StringBuffer(" from jrun_pms");
				String where = " where ";
				String and = " ";
				if(ignorenew!=null){
					sqlbuffer.append(where);
					where = " ";
					sqlbuffer.append(and);
					and = " and ";
					sqlbuffer.append("new=0");
				}
				if(FormDataCheck.isNum(days)&&!days.equals("0")){
					sqlbuffer.append(where);
					where = " ";
					sqlbuffer.append(and);
					and = " and ";
					int day = Common.toDigit(days);
					sqlbuffer.append("dateline <="+(timestamp-day*86400));
				}
				if(users!=null && !users.equals("")){
					StringBuffer uid = new StringBuffer("0");
					String biner = cins==null?"":" BINARY ";
					List<Map<String,String>> members = dataBaseService.executeQuery("select uid from jrun_members where "+biner+" username in ('"+Common.addslashes(users)+"')");
					for(Map<String,String>member:members){
						uid.append(","+member.get("uid"));
					}
					sqlbuffer.append(where);
					where = " ";
					sqlbuffer.append(and);
					and = " and ";
					sqlbuffer.append("(msgfromid IN ("+uid+") AND folder='outbox') OR (msgtoid IN ("+uid+") AND folder='inbox')");
				}
				if(srchtxt!=null && !srchtxt.equals("")){
					srchtxt = srchtxt.replaceAll("\\*", "%");
					srchtxt = srchtxt.toLowerCase();
					sqlbuffer.append(where);
					where = " ";
					sqlbuffer.append(and);
					and = " and ";
					if (Common.matches(srchtxt,"[and|\\+|&|\\s+]") && !Common.matches(srchtxt,"[or|\\|]")) {
						srchtxt = srchtxt.replaceAll("( and |&| )", "+");
						String[] keyword = srchtxt.split("\\+");
						if (srchtype.equals("title")) {
							for (int i = 0; i < keyword.length; i++) {
								sqlbuffer.append("subject like '%" + Common.addslashes(keyword[i].trim())+ "%'");
								sqlbuffer.append(and);
							}
						} else {
							for (int i = 0; i < keyword.length; i++) {
								sqlbuffer.append("message like '%" + Common.addslashes(keyword[i].trim())+ "%'");
								sqlbuffer.append(and);
							}
						}
						int length = sqlbuffer.length();
						sqlbuffer.delete(length-4,length);
					} else {
						srchtxt = srchtxt.replaceAll("( or |\\|)", "+");
						String[] keyword = srchtxt.split("\\+");
						if (srchtype.equals("title")) {
							sqlbuffer.append("(");
							for (int i = 0; i < keyword.length; i++) {
								sqlbuffer.append("subject like '%" + Common.addslashes(keyword[i].trim())+ "%'");
								sqlbuffer.append(" or ");
							}
						} else {
							sqlbuffer.append("(");
							for (int i = 0; i < keyword.length; i++) {
								sqlbuffer.append("message like '%" + Common.addslashes(keyword[i].trim())+ "%'");
								sqlbuffer.append(" or ");
							}
						}
						int length = sqlbuffer.length();
						sqlbuffer.delete(length-4,length);
						sqlbuffer.append(")");
					}
				}
				List<Map<String,String>> pmslist = dataBaseService.executeQuery("select pmid "+sqlbuffer.toString()); 
				int size = 0;
				StringBuffer pmids = new StringBuffer();
				if(pmslist!=null && pmslist.size()>0){
					size = pmslist.size();
					for(Map<String,String> pms:pmslist){
						pmids.append(","+pms.get("pmid"));
					}
				}
				pmslist = null;
				String confirmInfo = getMessage(request, "a_system_prune_pm_confirm", size+"");
				String otherInfo = "<input type='hidden' name='pmids' value='"+pmids+"'><input type='hidden' name='count' value='"+size+"'>";
				String commitPath = "admincp.jsp?action=pmprune&delete=yes";
				request.setAttribute("url_forward", commitPath);
				request.setAttribute("msgtype", "form"); 
				request.setAttribute("message", confirmInfo);
				request.setAttribute("isnewline", "yes");
				request.setAttribute("othermsg", otherInfo);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=pmprune");
		return null;
	}
	public ActionForward deletePmprun(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "confirmed")){
				String pmids = request.getParameter("pmids");
				if(pmids==null){
					Common.requestforward(response, "admincp.jsp?action=pmprune");
					return null;
				}
				String count = request.getParameter("count");
				if(pmids.length()>0){
					dataBaseService.runQuery("DELETE FROM jrun_pms WHERE pmid in ( "+pmids.substring(1)+" ) ",true);
				}
				String info = getMessage(request, "a_system_prune_pm_succeed",count);
				request.setAttribute("message", info);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=pmprune");
		return null;
	}
}
