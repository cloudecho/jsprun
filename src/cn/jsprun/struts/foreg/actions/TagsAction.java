package cn.jsprun.struts.foreg.actions;
import java.util.ArrayList;
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
import cn.jsprun.domain.Tags;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
public class TagsAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward toDistags(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		int viewthreadtags=Common.toDigit(settings.get("viewthreadtags"));
		List<Map<String,String>> hottaglist=dataBaseService.executeQuery("SELECT tagname,total FROM jrun_tags WHERE closed=0 ORDER BY total DESC LIMIT "+viewthreadtags);
		if(hottaglist!=null&&hottaglist.size()>0){
			for (Map<String, String> hottag : hottaglist) {
				hottag.put("tagnameenc", Common.encode(hottag.get("tagname")));
			}
			request.setAttribute("hottaglist", hottaglist);
		}
		int count=Integer.valueOf(dataBaseService.executeQuery("SELECT count(*) count FROM jrun_tags WHERE closed=0").get(0).get("count"));
		int randlimit=(count<=viewthreadtags)?0:Common.rand(count - viewthreadtags);
		List<Map<String,String>> randtaglist=dataBaseService.executeQuery("SELECT tagname,total FROM jrun_tags WHERE closed=0 LIMIT "+randlimit+","+viewthreadtags);
		if(randtaglist!=null&&randtaglist.size()>0){
			for (Map<String, String> randtag : randtaglist) {
				randtag.put("tagnameenc", Common.encode(randtag.get("tagname")));
			}
			request.setAttribute("randtaglist", randtaglist);
		}
		return mapping.findForward("todistags");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toThreadtags(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		Tags tag = tagService.findTagsByName(name);
		if(tag!=null&&tag.getClosed()>0){
			request.setAttribute("resultInfo", getMessage(request,"tag_closed"));
			return mapping.findForward("showMessage");
		}
		if (name != null && !name.equals("")) {
			HttpSession session=request.getSession();
			Members member=(Members)session.getAttribute("user");
			ServletContext context=servlet.getServletContext();
			Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
			int count=Integer.valueOf(dataBaseService.executeQuery("SELECT count(*) count FROM jrun_threadtags WHERE tagname=?", Common.addslashes(name)).get(0).get("count"));
			int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
			int page =Math.max(Common.intval(request.getParameter("page")), 1);
			Map<String,Integer> multiInfo=Common.getMultiInfo(count, tpp, page);
			page=multiInfo.get("curpage");
			int start_limit=multiInfo.get("start_limit");
			Map<String,Object> multi=Common.multi(count, tpp, page, "tag.jsp?name=" + Common.encode(name), 0, 10, true, false, null);
			request.setAttribute("multi", multi);
			List<Map<String,String>> dislist = null;int delcount = 0;
			List<Map<String,String>> threadtaglist = dataBaseService.executeQuery("select s.tid as ttid,t.*,f.name from jrun_threadtags as s left join jrun_threads as t on s.tid=t.tid left join jrun_forums as f on t.fid=f.fid where s.tagname=? limit "+start_limit+","+tpp, Common.addslashes(name));
			if (threadtaglist != null && threadtaglist.size() > 0) {
				dislist = new ArrayList<Map<String,String>>();
				for (Map<String,String> tags : threadtaglist) {
					if (tags.get("subject") != null && !tags.get("subject").equals("")) {
						dislist.add(tags);
					}else{
						delcount++;
						dataBaseService.runQuery("delete from jrun_threadtags where tid="+tags.get("ttid"));
					}
				}
			}
			if(tag!=null && delcount>0){
				if(tag.getTotal()>delcount){
					tag.setTotal(tag.getTotal()-delcount);
					tagService.updateTags(tag);
				}else{
					tagService.deleteTags(tag);
				}
			}
			threadtaglist = null;
			request.setAttribute("dislist", dislist);
			request.setAttribute("name", Common.htmlspecialchars(name));
		}
		return mapping.findForward("todisthreads");
	}
}