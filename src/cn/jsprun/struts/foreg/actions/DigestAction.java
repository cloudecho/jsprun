package cn.jsprun.struts.foreg.actions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
public class DigestAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		HttpSession session=request.getSession();
		Members member=(Members)session.getAttribute("user");
		short groupid=(Short)session.getAttribute("jsprun_groupid");
		request.setAttribute("threadsticky", settings.get("threadsticky").split(","));
		String forumstr=request.getParameter("forums");
		List<String> fidList=new ArrayList<String>();
		if(forumstr!=null){
			String[] fids=forumstr.split("_");
			for(String fid:fids){
				fidList.add(fid.trim());
			}
		}else{
			String[] forums=request.getParameterValues("forums[]");
			if(forums!=null&&forums.length>0){
				for(String fid:forums){
					fidList.add(fid.trim());
				}
			}
		}
		StringBuffer fids =new StringBuffer();
		fids.append("0");
		Map<String,String> forumslist = new HashMap<String,String>();
		Map<String,String> forumcheck=new HashMap<String,String>();
		Map<String,String> forumStr = (Map<String,String>)request.getAttribute("forums");
		Map<String,Map<String,String>> forumMap=dataParse.characterParse(forumStr.get("forums"), false);
		if(forumMap!=null){
			Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
			String extgroupids=member!=null?member.getExtgroupids():null;
			boolean readaccess=!usergroups.get("readaccess").equals("0"); 
			Set<Entry<String,Map<String,String>>> keys=forumMap.entrySet();
			for(Entry<String,Map<String,String>> temp : keys){
				String key = temp.getKey();
				Map<String,String> forum=temp.getValue();
				String viewperm=forum.get("viewperm");
				if((viewperm.equals("") && readaccess) || (!viewperm.equals("") && Common.forumperm(viewperm, groupid, extgroupids))){
					if(fidList.size()<=0||fidList.contains(key)){
						fids.append(","+key);
						forumcheck.put(key, "checked");
					}
					forumslist.put(key, forum.get("name"));
				}
			}
		}
		request.setAttribute("forumslist", forumslist);
		request.setAttribute("forumcheck", forumcheck);
		String keyword = request.getParameter("keyword");
		String author = request.getParameter("author");
		String authorid = request.getParameter("authorid");
		if(author!=null && !author.equals("")){
			Members user = memberService.findByName(author);
			if(user!=null){
				request.setAttribute("author", author);
				authorid =String.valueOf(user.getUid());
			}
		}
		String authoradd =Common.intval(authorid)>0?" AND authorid='"+Common.intval(authorid)+"'":"";
		String keywordadd=null;
		String [] value = null;
		if(keyword!=null&&!keyword.equals("")){
			value = new String[1];
			value[0] = "%"+Common.addslashes(keyword)+"%";
			keywordadd=" AND subject LIKE ?";
			request.setAttribute("keyword", keyword);
		}else{
			keywordadd="";
		}
		List<Map<String,String>> digestcount = dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_threads WHERE digest>'0' AND fid IN ( "+fids+" ) AND displayorder>='0' "+authoradd+keywordadd,value);
		int threadcount =Integer.parseInt(digestcount.get(0).get("count"));
		if(threadcount==0){
			request.setAttribute("errorInfo", getMessage(request, "digest_nonexistence"));
			return mapping.findForward("showMessage");
		}
		String order = request.getParameter("order");
		if(order==null){
			order = "digest";
		}
		request.setAttribute("ordercheck_"+order, "selected");
		int threadmaxpages =Integer.parseInt(settings.get("threadmaxpages"));
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.parseInt(settings.get("topicperpage"));
		int page = Math.max(Common.intval(request.getParameter("page")), 1);
		Map<String,Integer> multiInfo=Common.getMultiInfo(threadcount, tpp, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		String url = "digest.jsp?order="+order+"&keyword="+(keyword!=null?keyword:"")+"&amp;authorid="+(authorid!=null?authorid:"")+"&amp;forums="+fids.toString().replace(',', '_');
		Map<String,Object> multi=Common.multi(threadcount, tpp, page, url, threadmaxpages, 10, true, false, null);
		request.setAttribute("multi", multi);
		List<Map<String,String>> disgetThread = dataBaseService.executeQuery("SELECT t.tid,t.fid,t.iconid,t.price,t.author,t.authorid,t.subject,t.dateline,t.lastpost,t.lastposter,t.views,t.replies,t.highlight,t.digest,t.special,t.attachment,f.name FROM jrun_threads as t LEFT JOIN jrun_forums as f on t.fid=f.fid where t.digest>'0' AND t.fid IN ( "+fids+" ) AND t.displayorder>='0' "+authoradd+keywordadd+" ORDER BY "+order+" DESC LIMIT "+ start_limit+ ", " +tpp,value);
		if(disgetThread!=null&&disgetThread.size()>0){
			String timeoffset=(String)session.getAttribute("timeoffset");
			String timeformat=(String)session.getAttribute("timeformat");
			String dateformat=(String)session.getAttribute("dateformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			SimpleDateFormat sdf_dateformat=Common.getSimpleDateFormat(dateformat, timeoffset);
			int ppp = member != null&& member.getPpp() > 0 ? member.getPpp() : Integer.parseInt(settings.get("postperpage"));
			for(Map<String,String> thread:disgetThread){
				thread.put("dateline", Common.gmdate(sdf_dateformat, Integer.parseInt(thread.get("dateline"))));
				thread.put("lastpost", Common.gmdate(sdf_all, Integer.parseInt(thread.get("lastpost"))));
				Common.procThread(thread, ppp);
			}
		}
		request.setAttribute("threadlist", disgetThread);
		return mapping.findForward("todisgest");
	}
}