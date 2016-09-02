package cn.jsprun.struts.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.LogPage;
public class TagsAction extends BaseAction  {
	public ActionForward findByTags(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "tagsearchsubmit",true)){
				String tagname = request.getParameter("tagname");
				String cins = request.getParameter("cins");
				String threadnumlower = request.getParameter("threadnumlower");
				String threadnumhigher = request.getParameter("threadnumhigher");
				String status = request.getParameter("status");
				request.setAttribute("tagname", tagname);
				request.setAttribute("cins", cins);
				request.setAttribute("threadnumlower", threadnumlower);
				request.setAttribute("threadnumhigher", threadnumhigher);
				request.setAttribute("status", status);
				String sql = getTagSql(tagname,cins,threadnumlower,threadnumhigher,status);
				int totalsize = Common.toDigit(dataBaseService.executeQuery("select count(*) count "+sql).get(0).get("count"));
				LogPage loginpage = new LogPage(totalsize,100,1);
				request.setAttribute("logpage", loginpage);
				List<Map<String,String>> taglist = dataBaseService.executeQuery("select * "+sql+" limit 100");
				if (taglist != null && taglist.size()>0) {
					request.setAttribute("tagsList", taglist);
					String hiddenSB = countSB(taglist);
					request.setAttribute("hiddenSB", hiddenSB);
				}
				request.setAttribute("notfirst", "notfirst");
				return mapping.findForward("toTags");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=tags");
		return null;
	}
	public ActionForward pageTags(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int currentpage = 1;
		String page = request.getParameter("page");
		currentpage = page == null || page.equals("") ? 1 : FormDataCheck.isNum(page) ? Integer.valueOf(page) : 1;
		String tagname = request.getParameter("tagname");
		String cins = request.getParameter("cins");
		String threadnumlower = request.getParameter("threadnumlower");
		String threadnumhigher = request.getParameter("threadnumhigher");
		String status = request.getParameter("status");
		request.setAttribute("tagname", tagname);
		request.setAttribute("cins", cins);
		request.setAttribute("threadnumlower", threadnumlower);
		request.setAttribute("threadnumhigher", threadnumhigher);
		request.setAttribute("status", status);
		String sql = getTagSql(tagname,cins,threadnumlower,threadnumhigher,status);
		int totalsize = Common.toDigit(dataBaseService.executeQuery("select count(*) count "+sql).get(0).get("count"));
		LogPage loginpage = new LogPage(totalsize,100,currentpage);
		int beginsize = (currentpage-1)*100;
		request.setAttribute("logpage", loginpage);
		List<Map<String,String>> taglist = dataBaseService.executeQuery("select * "+sql+" limit "+beginsize+",100");
		if (taglist != null && taglist.size()>0) {
		request.setAttribute("tagsList", taglist);
		String hiddenSB = countSB(taglist);
		request.setAttribute("hiddenSB", hiddenSB);
		}
		request.setAttribute("notfirst", "notfirst");
		return mapping.findForward("toTags");
	}
	private String countSB(List<Map<String,String>> tagsList) {
		StringBuffer count = new StringBuffer();
		for (Map<String,String> tags:tagsList) {
			count.append(tags.get("tagname")+" ");
		}
		return count.toString();
	}
	public ActionForward batchTags(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "tagsubmit")){
				String hiddenTags = request.getParameter("hiddenSB");
				String tagname = request.getParameter("tagname");
				String cins = request.getParameter("cins");
				String threadnumlower = request.getParameter("threadnumlower");
				String threadnumhigher = request.getParameter("threadnumhigher");
				String status = request.getParameter("status");
				List<String> deleteList = new ArrayList<String>();
				List<String> closedList = new ArrayList<String>();
				List<String> openList = new ArrayList<String>();
				if (hiddenTags != null && !hiddenTags.equals("")) {
					String[] tags = hiddenTags.split(" ");
					for (int i = 0; i < tags.length; i++) {
						String value = request.getParameter(getTagArray(tags[i]));
						if (value != null && !value.equals("")) {
							if (value.trim().equals("-1")) {
								deleteList.add(tags[i]);
							}
							if (value.trim().equals("1")) {
								closedList.add(tags[i]);
							}
							if (value.trim().equals("0")) {
								openList.add(tags[i]);
							}
						}
					}
				}
				tagsService.deleteArray(deleteList);
				if(deleteList.size() > 0){
					StringBuilder sql = new StringBuilder("DELETE FROM jrun_threadtags WHERE tagname IN(");
					for(String delete : deleteList){
						sql.append("'");
						sql.append(delete);
						sql.append("',");
					}
					sql.replace(sql.length()-1, sql.length(), ")");
					dataBaseService.execute(sql.toString());
				}
				tagsService.updateToClosedTags(closedList);
				tagsService.updateToOpenTags(openList);
				deleteList = null;closedList=null;openList=null;
				Cache.updateCache("index");
				request.setAttribute("message_key", "a_post_tags_updated");
				request.setAttribute("url_forward", "admincp.jsp?action=tags&search=yes&tagname="+tagname+"&cins="+cins+"&threadnumlower="+threadnumlower+"&threadnumhigher="+threadnumhigher+"&status="+status+"&tagsearchsubmit=yes&formHash="+Common.formHash(request));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=tags");
		return null;
	}
	public String getTagArray(String tags) {
		StringBuffer sb = new StringBuffer("tag[");
		sb.append(tags);
		sb.append("]");
		return sb.toString();
	}
	private String getTagSql(String tagname,String cins,String threadnumlower,String threadnumhigher,String status){
		StringBuffer sqlbuffer = new StringBuffer("from jrun_tags ");
		String where = " where ";
		String and = " ";
		String cinse = "";
		if(cins!=null){
			cinse = " BINARY ";
		}
		if(tagname!=null && !tagname.equals("")){
			sqlbuffer.append(where);
			where = " ";
			sqlbuffer.append(and);
			and = " and ";
			sqlbuffer.append(cinse + " tagname like '%"+Common.addslashes(tagname)+"%' ");
		}
		if(threadnumlower!=null && !threadnumlower.equals("")&&Common.isNum(threadnumlower)){
			sqlbuffer.append(where);
			where = " ";
			sqlbuffer.append(and);
			and = " and ";
			sqlbuffer.append(cinse + " total < "+threadnumlower);
		}
		if(threadnumhigher!=null && !threadnumhigher.equals("")&&Common.isNum(threadnumlower)){
			sqlbuffer.append(where);
			where = " ";
			sqlbuffer.append(and);
			and = " and ";
			sqlbuffer.append(cinse + " total> "+threadnumhigher);
		}
		if(!status.equals("-1")){
			sqlbuffer.append(where);
			where = " ";
			sqlbuffer.append(and);
			and = " and ";
			sqlbuffer.append(cinse + " closed="+status);
		}
		return sqlbuffer.toString();
	}
}
