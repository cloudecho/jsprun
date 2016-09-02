package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Threads;
import cn.jsprun.service.ForumService;
import cn.jsprun.service.ThreadsService;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
public class RedirectManageAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward findpost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int pid=Common.toDigit(request.getParameter("pid"));
		int ptid=Common.toDigit(request.getParameter("ptid"));
		String sql="SELECT p.tid, p.dateline, t.special FROM jrun_posts p LEFT JOIN jrun_threads t USING(tid) WHERE p.pid='"+pid+"'";
		List<Map<String,String>> posts=dataBaseService.executeQuery(sql);
		if(posts!=null&&posts.size()>0){
			HttpSession session=request.getSession();
			Members member=(Members)session.getAttribute("user");
			Map<String, String> settings = ForumInit.settings;
			Map<String,String> post=posts.get(0);
			String sqladd=Common.toDigit(post.get("special"))>0?"AND first=0":"";
			sql="SELECT count(*) count FROM jrun_posts WHERE tid='"+post.get("tid")+"' AND dateline<='"+post.get("dateline")+"' "+sqladd;
			double count=Double.valueOf(dataBaseService.executeQuery(sql).get(0).get("count"));
			double ppp=member!=null&&member.getPpp()>0?member.getPpp():Common.toDigit(settings.get("postperpage"));
			int page=(int)Math.ceil(count/ppp);
			String special=request.getParameter("special");
			String location=null;
			if(special==null){
				location="viewthread.jsp?tid="+post.get("tid")+"&page="+page+"#pid"+pid;
			}
			else{
				location="viewthread.jsp?do=tradeinfo&tid="+post.get("tid")+"&pid="+pid;
			}
			try {
				response.sendRedirect(location);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("errorInfo",getMessage(request, "post_check", ptid+""));
			return mapping.findForward("showMessage");
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward lastpost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int tid=Common.intval(request.getParameter("tid"));
		String sql="";
		if(tid>0){
			sql="SELECT tid, replies,closed, special FROM jrun_threads WHERE tid='"+tid+"' AND displayorder>='0'";
		}else{
			Short fid=(short)Common.range(Common.intval(request.getParameter("fid")), 32767, 0);
			if(((ForumService) BeanFactory.getBean("forumService")).findById(fid)==null){
				request.setAttribute("errorInfo",getMessage(request, "forum_nonexistence"));
				return mapping.findForward("showMessage");
			}
			sql="SELECT tid, replies,closed, special FROM jrun_threads WHERE fid='"+fid+"' AND displayorder>='0' ORDER BY lastpost DESC LIMIT 1";
		}
		List<Map<String,String>> threads=dataBaseService.executeQuery(sql);
		if(threads!=null&&threads.size()>0){
			Map<String,String> thread=threads.get(0);
			int closed = Integer.parseInt(thread.get("closed"));
			if(closed>0){
				tid = closed;
				sql="SELECT tid, replies, special FROM jrun_threads WHERE tid='"+closed+"' AND displayorder>='0'";
				threads=dataBaseService.executeQuery(sql);
				if(threads!=null&&threads.size()>0){
					thread=threads.get(0);
				}else{
					request.setAttribute("errorInfo",getMessage(request, "thread_nonexistence"));
					return mapping.findForward("showMessage");
				}
			}
			HttpSession session=request.getSession();
			Map<String, String> settings = ForumInit.settings;
			Members member=(Members)session.getAttribute("user");
			double replies=Integer.valueOf(thread.get("replies"));
			double ppp=member!=null&&member.getPpp()>0?member.getPpp():Common.toDigit(settings.get("postperpage"));
			int page=(int)Math.ceil((Double.valueOf(thread.get("special"))>0?replies:replies+1)/ppp);
			try {
				response.sendRedirect("viewthread.jsp?tid="+tid+"&page="+page);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("errorInfo",getMessage(request, "thread_nonexistence"));
			return mapping.findForward("showMessage");
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward newpost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int tid=Common.toDigit(request.getParameter("tid"));
		Threads thread=((ThreadsService) BeanFactory.getBean("threadsService")).findByTid(tid);
		if(thread==null){
			request.setAttribute("errorInfo",getMessage(request, "thread_nonexistence"));
			return mapping.findForward("showMessage");
		}
		tid=thread.getClosed()<1?tid:thread.getClosed();
		HttpSession session=request.getSession();
		Map<String, String> settings = ForumInit.settings;
		Members member=(Members)session.getAttribute("user");
		String sql="SELECT COUNT(*) count FROM jrun_posts WHERE tid='"+tid+"' AND dateline<='"+(member!=null?member.getLastvisit():0)+"'";
		double count=Double.valueOf(dataBaseService.executeQuery(sql).get(0).get("count"));
		double ppp=member!=null&&member.getPpp()>0?member.getPpp():Common.toDigit(settings.get("postperpage"));
		int page=(int)Math.ceil(count/ppp);
		try {
			request.getRequestDispatcher("viewthread.jsp?tid="+tid+"&page="+page).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward newset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String gotos = request.getParameter("goto");
		int fid = Common.intval(request.getParameter("fid"));
		int tid=Common.toDigit(request.getParameter("tid"));
		if(gotos!=null && !gotos.equals("")&& fid>0){			
			Threads currthread = ((ThreadsService) BeanFactory.getBean("threadsService")).findByTid(tid);
			boolean isNextnewset=false;
			if(gotos.equals("nextnewset")){
				isNextnewset=true;
			}else if(gotos.equals("nextoldset")){
				isNextnewset=false;
			}
			if(currthread!=null){
				String threadsql = "SELECT tid FROM jrun_threads WHERE fid='"+fid+"' AND displayorder>='0' AND lastpost"+(isNextnewset?">":"<")+"'"+currthread.getLastpost()+"' ORDER BY lastpost "+(isNextnewset?"ASC":"DESC")+" LIMIT 1";
				List<Map<String, String>> threadlist = dataBaseService.executeQuery(threadsql);
				if(threadlist!=null && threadlist.size()>0){
					Map<String,String> threadmap = threadlist.get(0);
					tid = Common.toDigit(threadmap.get("tid"));
				}else{
					String mes = "";
					if(isNextnewset){
						mes = getMessage(request, "redirect_nextnewset_nonexistence");
					}else{
						mes = getMessage(request, "redirect_nextoldset_nonexistence");
					}
					request.setAttribute("errorInfo",mes);
					return mapping.findForward("showMessage");
				}
			}else{
				request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
				return mapping.findForward("showMessage");
			}
			try {
				response.sendRedirect("viewthread.jsp?tid="+tid+"&page=1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		return null;
	}
}