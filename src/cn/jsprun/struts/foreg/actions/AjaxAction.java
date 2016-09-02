package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
public class AjaxAction extends BaseAction {
	public ActionForward checkseccode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		String seccodeverify = request.getParameter("seccodeverify");
		seccodeverify=Common.ajax_decode(seccodeverify);
		try {
			PrintWriter out=response.getWriter();
			if (seccodeverify.equalsIgnoreCase(request.getSession().getAttribute("rand").toString())) {
				out.write("succeed");
			} else {
				out.write(getMessage(request, "submit_seccode_invalid"));
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward updatesecqaa(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		try {
			request.getRequestDispatcher("/forumdata/cache/cache_secqaa.jsp").include(request, response);
			Map<String,String> secqaas=(Map<String,String>)request.getAttribute("secqaa");
			Map<String,String> secqaa=dataParse.characterParse(secqaas.get(Common.rand(9)+""), false);
			String question=null;
			String answer=null;
			if(secqaa!=null){
				question=secqaa.get("question");
				answer=secqaa.get("answer");
			}else{
				question="";
				answer="";
			}
			request.getSession().setAttribute("answer", answer);
			PrintWriter out=response.getWriter();
			out.write(question);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward checksecanswer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		String secanswer = request.getParameter("secanswer");
		String anser=(String)request.getSession().getAttribute("answer");
		secanswer=Common.ajax_decode(secanswer);
		try {
			PrintWriter out=response.getWriter();
			if(anser!=null&&anser.equals(secanswer)){
				out.write("succeed");
			}
			else{
				out.write(getMessage(request, "submit_secqaa_invalid"));
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward checkusername(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		String username = request.getParameter("username");
		username=Common.ajax_decode(username);
		Map<String,String> settings=ForumInit.settings;
		String censoruser=settings.get("censoruser");
		try {
			PrintWriter out=response.getWriter();
			if(Common.censoruser(username, censoruser)){
				out.write(getMessage(request, "profile_username_illegal"));
			}else{
				List<Map<String,String>> members=dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE username=? LIMIT 1",Common.addslashes(username));
				if(members!=null&&members.size()>0){
					out.write(getMessage(request, "register_check_found",username));
				}
				else{
					out.write("succeed");
				}
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward checkuserexists(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		String username = request.getParameter("username");
		username=Common.ajax_decode(username);
		try {
			List<Map<String,String>> members=dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE username=? LIMIT 1",Common.addslashes(username));
			PrintWriter out=response.getWriter();
			if(members!=null&&members.size()>0){
				HttpSession session = request.getSession();
				String styleid = (String)session.getAttribute("styleid");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/forumdata/cache/style_"+styleid+".jsp");
				try {
					dispatcher.include(request, response);
				} catch (ServletException e) {
					dispatcher = request.getRequestDispatcher("/forumdata/cache/style_"+ForumInit.settings.get("styleid")+".jsp");
					try{
						dispatcher.include(request, response);
					}catch(ServletException e2){
						e2.printStackTrace();
					}
				}
				String imgdir = ((Map<String,String>)request.getAttribute("styles")).get("IMGDIR");
				out.write("<img src='"+request.getContextPath()+"/"+imgdir+"/check_right.gif' width='13' height='13'>");
			}
			else{
				out.write(getMessage(request, "username_nonexistence"));
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward checkemail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		String email = request.getParameter("email");
		email=Common.ajax_decode(email);
		try {
			List<Map<String,String>> members=dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE email=? LIMIT 1",Common.addslashes(email));
			PrintWriter out=response.getWriter();
			if(members!=null&&members.size()>0){
				out.write(getMessage(request, "profile_email_duplicate"));
			}
			else{
				out.write("succeed");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward checkinvitecode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Common.setResponseHeader(response);
		String invitecode = request.getParameter("invitecode");
		invitecode=Common.ajax_decode(invitecode);
		try {
			List<Map<String,String>> invites=dataBaseService.executeQuery("SELECT invitecode FROM jrun_invites WHERE invitecode=? AND status IN ('1', '3') LIMIT 1",Common.addslashes(invitecode));
			PrintWriter out=response.getWriter();
			if(invites!=null&&invites.size()>0){
				out.write("succeed");
			}
			else{
				out.write(getMessage(request, "invite_invalid"));
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}