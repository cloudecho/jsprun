package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
public class FaqAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("jsprun_action", "51");
		String action = request.getParameter("action");
		if (action == null) {
			List<Map<String,String>> faqparent=new ArrayList<Map<String,String>>();
			Map<String,List<Map<String,String>>> faqsub=new HashMap<String,List<Map<String,String>>>();
			List<Map<String,String>> faqs=dataBaseService.executeQuery("SELECT id, fpid, title FROM jrun_faqs ORDER BY displayorder");
			for(Map<String,String> faq:faqs){
				String fpid=faq.get("fpid");
				if("0".equals(fpid)){
					faqparent.add(faq);
				}else{
					List<Map<String,String>> sub=faqsub.get(fpid);
					if(sub==null){
						sub=new ArrayList<Map<String,String>>();
						faqsub.put(fpid, sub);
					}
					sub.add(faq);
				}
			}
			request.setAttribute("faqparent",faqparent);
			request.setAttribute("faqsub",faqsub);
		} else if("message".equals(action)){
			int id = Common.intval(request.getParameter("id"));
			List<Map<String,String>> faqs=dataBaseService.executeQuery("SELECT * FROM jrun_faqs WHERE id='"+id+"'");
			if(faqs!=null&&faqs.size()>0){
				Map<String,String> faq=faqs.get(0);
				List<Map<String,String>> otherfaqs=dataBaseService.executeQuery("SELECT id, fpid, title FROM jrun_faqs WHERE fpid='"+faq.get("fpid")+"' AND id!='"+faq.get("id")+"' ORDER BY displayorder");
				request.setAttribute("navigation", "&raquo; "+faq.get("title"));
				request.setAttribute("faq", faq);
				request.setAttribute("otherfaqs",otherfaqs!=null&&otherfaqs.size()>0?otherfaqs:null);
			}else{
				request.setAttribute("errorInfo", getMessage(request, "faq_content_empty"));
				return mapping.findForward("showMessage");
			}
		}else if("search".equals(action)){
			request.setAttribute("navigation","&raquo; "+getMessage(request, "faq_search"));
			String keyword=request.getParameter("keyword");
			if(keyword==null||"".equals(keyword.trim())){
				request.setAttribute("errorInfo", getMessage(request, "faq_keywords_empty"));
				return mapping.findForward("showMessage");
			}
			String searchtype=request.getParameter("searchtype");
			if(searchtype==null||!searchtype.matches("(all|title|message)")){
				searchtype="all";
			}
			String sqlsrch=null;
			String[] values = null;
			if("all".equals(searchtype)){
				values = new String[2];
				values[0] = "%"+Common.addslashes(keyword)+"%";
				values[1] = "%"+Common.addslashes(keyword)+"%";
				sqlsrch ="AND (title LIKE ? OR message LIKE ?)";
			}else if("title".equals(searchtype)){
				values = new String[1];
				values[0] = "%"+Common.addslashes(keyword)+"%";
				sqlsrch ="AND title LIKE ?";
			}else if("message".equals(searchtype)){
				values = new String[1];
				values[0] = "%"+Common.addslashes(keyword)+"%";
				sqlsrch ="AND message LIKE ?";
			}
			keyword=Common.stripslashes(keyword);
			List<Map<String,String>> faqs=dataBaseService.executeQuery("SELECT fpid,title, message FROM jrun_faqs WHERE fpid<>0 "+sqlsrch+" ORDER BY displayorder",values);
			if(faqs!=null&&faqs.size()>0){
				String regex="(?si)(?<=[\\s\"\\]>()]|[^\\x00-\\xff]|^)("+Common.pregQuote(keyword, '/')+")([.,:;-?!()\\s\"<\\[]|[^\\x00-\\xff]|$)";
				String replacement="<u><b><font color=\"#FF0000\">$1</font></b></u>$2";
				for(Map<String,String> faq:faqs){
					String title=Common.stripslashes(faq.get("title"));
					String message=Common.stripslashes(faq.get("message"));
					faq.put("title", title.replaceAll(regex, replacement));
					faq.put("message", message.replaceAll(regex, replacement));
				}
			}
			request.setAttribute("faqs", faqs);
			request.setAttribute("keyword", keyword);
		}else if("sionver".equals(action)){
			try {
				PrintWriter out = response.getWriter();
				out.write("&#80;&#111;&#119;&#101;&#114;&#101;&#100;&#32;&#98;&#121;&#32;&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#54;&#46;&#48;&#46;&#48;&#32;&#82;&#101;&#108;&#101;&#97;&#115;&#101;&#32;<br/>");
				out.write("&#67;&#111;&#112;&#121;&#82;&#105;&#103;&#104;&#116;&#32;&#50;&#48;&#48;&#55;&#45;&#50;&#48;&#49;&#49;&#32;&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#73;&#110;&#99;&#46;&#32;&#65;&#108;&#108;&#32;&#82;&#105;&#103;&#104;&#116;&#115;&#32;&#82;&#101;&#115;&#101;&#114;&#118;&#101;&#100;<br/><br/>");
				out.write("&#21271;&#20140;&#39134;&#36895;&#21019;&#24819;&#31185;&#25216;&#26377;&#38480;&#20844;&#21496;<br/>");
				out.write("&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#23448;&#26041;&#32593;&#31449;&#65306;&#119;&#119;&#119;&#46;&#106;&#115;&#112;&#114;&#117;&#110;&#46;&#99;&#111;&#109;<br/>");
				out.write("&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#23448;&#26041;&#35770;&#22363;&#65306;&#119;&#119;&#119;&#46;&#106;&#115;&#112;&#114;&#117;&#110;&#46;&#110;&#101;&#116;<br/>");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		request.setAttribute("action", action);
		return mapping.findForward("todisfaqs");
	}
}