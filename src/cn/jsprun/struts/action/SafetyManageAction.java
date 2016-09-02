package cn.jsprun.struts.action;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
public class SafetyManageAction extends BaseAction {
	public ActionForward basic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variables[]={"adminemail","dbreport","errorreport","admincp_forcesecques","admincp_checkip","admincp_tpledit","admincp_runquery","admincp_dbimport","cookiepre","cookiedomain","cookiepath"};					
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for (String variable: variables) {
					String value=request.getParameter(variable);	    	
					if(value!=null&&!value.equals(oldSettings.get(variable))){
						settings.put(variable,value);
					}
				}
				this.updateSettings(settings,oldSettings);
				request.setAttribute("message", getMessage(request, "a_safety_set_s"));
				request.setAttribute("url_forward",request.getHeader("Referer"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		return mapping.findForward("safety_basic");
	}
	public ActionForward cc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variable="attackevasive";					
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				int sum=0;
				for(int j=0;j<4;j++){
					String rewritestatus=request.getParameter(variable+j);
					if(rewritestatus!=null){
						sum=sum+Integer.valueOf(rewritestatus);
					}
				}
				String value=String.valueOf(sum);
				if(value!=null&&!value.equals(oldSettings.get(variable))){
					settings.put(variable,value);
				}
				this.updateSettings(settings,oldSettings);
				request.setAttribute("message", getMessage(request, "a_safety_ccset_s"));
				request.setAttribute("url_forward",request.getHeader("Referer"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settings=ForumInit.settings;
		int attackevasive=Common.toDigit(settings.get("attackevasive"));
		Common.setChecked(request, "attackevasive", 4, attackevasive);
		return mapping.findForward("safety_cc");
	}
	public ActionForward ddos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String variable="ddos";					
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				int sum=0;
				for(int j=0;j<4;j++){
					String ddos=request.getParameter(variable+j);
					if(ddos!=null){
						sum=sum+Integer.valueOf(ddos);
					}
				}
				String value=String.valueOf(sum);
				if(value!=null&&!value.equals(oldSettings.get(variable))){
					settings.put(variable,value);
				}
				this.updateSettings(settings,oldSettings);
				request.setAttribute("message", getMessage(request, "a_safety_DDOSset_s"));
				request.setAttribute("url_forward",request.getHeader("Referer"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Map<String,String> settings=ForumInit.settings;
		int ddos=Common.toDigit(settings.get("ddos"));
		Common.setChecked(request, "ddos", 4, ddos);
		return mapping.findForward("safety_ddos");
	}
	@SuppressWarnings("unchecked")
	public ActionForward port(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "settingsubmit")){
				String port = request.getParameter("port");   
				if(Common.empty(port)){
					request.setAttribute("message", getMessage(request, "a_safety_choose_server_config_type"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				String filepath = null;
				String targetport = null;
				if(port.equals("apache")){
					filepath = request.getParameter("apache_filepath");
					targetport = request.getParameter("apache_port");
				}else if(port.equals("iis")){
					filepath = request.getParameter("iis_filepath");
					targetport = request.getParameter("iis_port");
				}else{
					filepath = request.getParameter("tomcat_filepath");
					targetport = request.getParameter("tomcat_port");
				}
				if(!FormDataCheck.isNum(targetport)){
					request.setAttribute("message", getMessage(request, "a_safety_port_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				File file = new File(filepath);
				if(!file.exists()){
					request.setAttribute("message", getMessage(request, "a_safety_filepath_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if(port.equals("apache")){
					FileInputStream fin = null;
					InputStreamReader ir = null;
					BufferedReader br = null;
					FileOutputStream out = null;
					OutputStreamWriter wout = null;
					BufferedWriter bw  = null;
					String temppath = JspRunConfig.realPath+"temp.txt";
					try {
						fin = new FileInputStream(filepath);
						ir = new InputStreamReader(fin);
						br = new BufferedReader(ir);
						out = new FileOutputStream(temppath,true);
						wout = new OutputStreamWriter(out);
						bw = new BufferedWriter(wout);
						String newline = br.readLine();
						while(newline!=null){
							newline = newline.replaceAll("\\s*Listen\\s+\\d+", "Listen "+targetport);
							newline = newline.replaceAll("(\\s*)NameVirtualHost(\\s+)(.*):(\\d+)", "NameVirtualHost $3:"+targetport);
							newline = newline.replaceAll("<VirtualHost(\\s+)(.*):(\\d+)>", "<VirtualHost $2:"+targetport+">");
							bw.write(newline);
							bw.newLine();
							newline = br.readLine();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
							if(bw!=null){
								bw.close();
							}
							if(wout!=null){
								wout.close();
							}
							if(out!=null){
								out.close();
							}
							if(br!=null){
								br.close();
							}
							if(ir!=null){
								ir.close();
							}
							if(fin!=null){
								fin.close();
							}
					}
					try{
						fin = new FileInputStream(temppath);
						out = new FileOutputStream(filepath);
						byte[] bytes=new byte[1024];
			    		int c;
			    		while ((c=fin.read(bytes))!=-1){
			    			out.write(bytes,0,c);
			    		}
			    		File tempfile = new File(temppath);
			    		if(tempfile.exists()){
			    			tempfile.delete();
			    		}
					}catch(Exception e){
					}finally{
							if(out!=null){
								out.close();
							}
							if(fin!=null){
								fin.close();
							}
					}
				}else if(port.equals("iis")){
					request.setAttribute("message", getMessage(request, "a_safety_no_iis"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}else{
					 try {
					 SAXReader saxReader = new SAXReader();
					 Document document = saxReader.read(file);
					 List list = document.selectNodes("//Connector//@protocol" );
					 Iterator iter=list.iterator();
					 String proport = "";
				     while(iter.hasNext()){
				        Attribute attribute=(Attribute)iter.next();
				         Element el =  attribute.getParent();
				         List childlist = el.selectNodes("//Connector//@port");
				         Iterator it = childlist.iterator();
				         while(it.hasNext()){
				        	 Attribute attributesub=(Attribute)it.next();
				        	 proport = attributesub.getValue();
				         }
				      }
				     list = document.selectNodes("//Connector//@port" );
				     iter=list.iterator();
				     while(iter.hasNext()){
				    	 Attribute attribute=(Attribute)iter.next();
				    	   if(!attribute.getValue().equals(proport))
				    	      attribute.setValue(targetport); 
				     }
				     XMLWriter output = new XMLWriter( new FileWriter( new File(filepath)));
				     output.write( document );
				     output.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				request.setAttribute("message", getMessage(request, "a_safety_port_update_s"));
				request.setAttribute("url_forward",request.getHeader("Referer"));
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		return mapping.findForward("safety_port");
	}
	private void updateSettings(Map<String,String> settings,Map<String,String> oldSettings){
		if(settings!=null&&settings.size()>0){
			Set<String> variables=settings.keySet();
			StringBuffer sql=new StringBuffer();
			sql.append("REPLACE INTO jrun_settings (variable, value) VALUES ");
			for(String variable:variables){
				sql.append("('"+variable+"', '"+Common.addslashes(settings.get(variable))+"'),");
			}
			sql.deleteCharAt(sql.length()-1);
			dataBaseService.runQuery(sql.toString(),true);
			oldSettings.putAll(settings);
			ForumInit.setSettings(this.getServlet().getServletContext(), oldSettings);
		}
	}
}