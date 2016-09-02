package cn.jsprun.struts.foreg.actions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class FrameManageAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward showLeftMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Members member = (Members) session.getAttribute("user");
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		byte accessmasks = member != null ? member.getAccessmasks() : 0;
		String sql = accessmasks > 0 ? "SELECT f.fid, f.fup, f.type, f.name, ff.viewperm, a.allowview FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid LEFT JOIN jrun_access a ON a.uid='"
				+ jsprun_uid + "' AND a.fid=f.fid WHERE f.status=1 ORDER BY f.type, f.displayorder"
				: "SELECT f.fid, f.fup, f.type, f.name, ff.viewperm FROM jrun_forums f LEFT JOIN jrun_forumfields ff USING(fid) WHERE f.status=1 ORDER BY f.type, f.displayorder";
		List<Map<String, String>> forums = dataBaseService.executeQuery(sql);
		if (forums != null && forums.size() > 0) {
			List<Map<String, String>> forumlist = new ArrayList<Map<String, String>>();
			Map<String, Boolean> haschild = new HashMap<String, Boolean>();
			short groupid = (Short) session.getAttribute("jsprun_groupid");
			String extgroupid = member != null ? member.getExtgroupids() : null;
			for (Map<String, String> forumdata : forums) {
				String viewperm = forumdata.get("viewperm");
				if ("".equals(viewperm)
						|| (!"".equals(viewperm) && Common.forumperm(viewperm, groupid, extgroupid))
						|| !Common.empty(forumdata.get("allowview"))) {
					if (!"group".equals(forumdata.get("type"))) {
						haschild.put(forumdata.get("fup"), true);
					}
					forumdata.put("name", Common.strip_tags(forumdata.get("name")));
					forumlist.add(forumdata);
				}
			}
			request.setAttribute("forumlist", forumlist);
			request.setAttribute("haschild", haschild);
		}
		return mapping.findForward("showLeftMenu");
	}
	public ActionForward showFrame(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		if ("0".equals(settings.get("frameon"))) {
			request.setAttribute("errorInfo", getMessage(request, "frame_off"));
			return mapping.findForward("showMessage");
		}
		HttpSession session = request.getSession();
		String boardurl = (String) session.getAttribute("boardurl");
		String referer = request.getParameter("referer");
		if (Common.empty(referer)) {
			referer = request.getHeader("Referer");
		}
		if (Common.empty(referer) || !referer.startsWith(boardurl)) {
			referer = "index.jsp";
		} else {
			referer = referer.replaceAll("(?i)[&?]frameon=(yes|no)", "");
		}
		referer = referer + (referer.indexOf("?") != -1 ? "&" : "?") + "frameon=no";
		String frameon = request.getParameter("frameon");
		session.setAttribute("frameon", frameon);
		request.setAttribute("newurl", referer);
		return mapping.findForward("showFrame");
	}
}