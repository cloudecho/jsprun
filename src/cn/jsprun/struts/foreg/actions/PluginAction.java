package cn.jsprun.struts.foreg.actions;
import java.io.File;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.JspRunConfig;
public class PluginAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward plugin(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String identifier=request.getParameter("identifier");
		String module=request.getParameter("module");
		Map<String, String> settings = (Map<String, String>) request.getAttribute("settings");
		Map<String,Map<String,Map<String,String>>> pluginlinks=dataParse.characterParse(settings.get("pluginlinks"), false);
		Map<String,String> pluginmodule=pluginlinks.get(identifier)!=null?pluginlinks.get(identifier).get(module):null;
		if(identifier==null||identifier.length()==0||module==null||module.length()==0||!module.matches("^[\\w|-]+$")||pluginmodule==null){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		HttpSession session=request.getSession();
		byte adminid=(Byte)session.getAttribute("jsprun_adminid");
		byte plugin_adminid=Byte.valueOf(pluginmodule.get("adminid"));
		if(plugin_adminid>0&&(adminid<1||(adminid>0&&plugin_adminid<adminid))){
			request.setAttribute("resultInfo", getMessage(request, "plugin_nopermission"));
			return mapping.findForward("showMessage");
		}
		String directory=pluginmodule.get("directory");
		String modfile="/plugins/"+directory+(directory!=null&&directory.endsWith("/")?"":"/")+module+".inc.jsp";
		File file=new File(JspRunConfig.realPath+modfile);
		if(file.exists()){
			try {
				request.getRequestDispatcher(modfile).forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else{
			request.setAttribute("resultInfo", getMessage(request, "plugin_module_nonexistence",modfile));
			return mapping.findForward("showMessage");
		}
	}
}