package cn.jsprun.struts.action;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.dao.ImagetypesDao;
import cn.jsprun.domain.Imagetypes;
import cn.jsprun.domain.Smilies;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.JspRunConfig;
public class SmiliesAction extends BaseAction {
	public ActionForward batchSmiliestypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			if(submitCheck(request, "smiliessubmit")){
				String hiddenStr = request.getParameter("showcount");
				Integer count = Integer.valueOf(hiddenStr != null ? hiddenStr : "0");
				List<Imagetypes> list = new ArrayList<Imagetypes>();
				for (int i = 1; i <= count; i++) {
					Imagetypes image = new Imagetypes();
					String newname = request.getParameter(this.getNewName(i));
					String newdisplayorder = request.getParameter(this.getNewDisplayorder(i));
					String newdirectory = request.getParameter(this.getNewDirectory(i));
					if (FormDataCheck.isValueString(newdirectory)) {
						image.setName(this.isNull(newname)); 
						image.setType("smiley"); 
						if (FormDataCheck.isNum(newdisplayorder)) {
							image.setDisplayorder(Short.valueOf(newdisplayorder));
						} else {
							image.setDisplayorder(Short.valueOf("0"));
						}
						image.setDirectory(newdirectory);
						list.add(image);
					}
				}
				imagetypesService.saveList(list);
				String[] ids = request.getParameterValues("delete[]");
				if (ids != null && ids.length > 0) {
					Short[] typeids = new Short[ids.length]; 
					for (int i = 0; i < ids.length; i++) {
						typeids[i] = Short.valueOf(ids[i]);
					}
					imagetypesService.deleteImagetypesAll(typeids); 
				}
				String typeids[] = request.getParameterValues("typeid");
				List<Imagetypes> nameValueList = new ArrayList<Imagetypes>();
					if (typeids != null) {
						for (int i = 0; i < typeids.length; i++) {
							String names = request.getParameter("namenew["+typeids[i]+"]");
							String values = request.getParameter("displayordernew["+typeids[i]+"]");
							short value = (short)Common.range(Common.intval(values), 1000000, 0);
							if (names!=null) {
								Imagetypes imagetypes = new Imagetypes(); 
								imagetypes.setDisplayorder(value);
								imagetypes.setName(names); 
								imagetypes.setTypeid(Short.valueOf(typeids[i]));
								nameValueList.add(imagetypes);
							}
						}
					imagetypesService.updateNameImagetypes(nameValueList); 
				}
				list = null;nameValueList=null;typeids=null;ids=null;
				Cache.updateCache("post");
				request.setAttribute("message_key", "a_post_smilies_edit_succeed");
				request.setAttribute("url_forward", "admincp.jsp?action=smilies");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=smilies");
		return null;
	}
	private String getNewName(int i) {
		StringBuffer sb = new StringBuffer("newname[");
		sb.append(i);
		sb.append("]");
		return sb.toString();
	}
	private String getNewDisplayorder(int i) {
		StringBuffer sb = new StringBuffer("newdisplayorder[");
		sb.append(i);
		sb.append("]");
		return sb.toString();
	}
	private String getNewDirectory(int i) {
		StringBuffer sb = new StringBuffer("newdirectory[");
		sb.append(i);
		sb.append("]");
		return sb.toString();
	}
	private String isNull(String str) {
		return str == null ? "" : str;
	}
	@SuppressWarnings("unchecked")
	public ActionForward findBySmilies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map map = null;
		Integer currentPage = 1;
		String page = request.getParameter("page");
		currentPage = page == null ? 1 : Integer.valueOf(page);
		String edit = request.getParameter("edit"); 
		String directoryStr = request.getParameter("directory");
		String path = JspRunConfig.realPath+"images/smilies/"+directoryStr;
		File fils = new File(path);
		if (FormDataCheck.isValueString(edit)) {
			map = imagetypesService.showImagesToID(Short.valueOf(edit.trim()),currentPage);
		}
		if (map == null || map.get(ImagetypesDao.DIRECTORY) == null || !fils.exists()) {
			String successInfo = getMessage(request, "a_post_smilies_directory_invalid", "./images/smilies/" + directoryStr);
			request.setAttribute("return", true);
			request.setAttribute("message", successInfo);
			return mapping.findForward("message");
		} else {
			request.setAttribute("edit", edit);
			List<Smilies> list = (List) map.get(ImagetypesDao.SMILIES);
			String hiddenids = getList(list);
			request.setAttribute("ids", hiddenids);
			request.setAttribute(ImagetypesDao.CURRENTPAGE, map.get(ImagetypesDao.CURRENTPAGE)); 
			request.setAttribute(ImagetypesDao.TOTALPAGE, map.get(ImagetypesDao.TOTALPAGE));
			request.setAttribute(ImagetypesDao.SMILIES, map.get(ImagetypesDao.SMILIES));
			request.setAttribute(ImagetypesDao.TOTALSIZE, map.get(ImagetypesDao.TOTALSIZE));
		}
		request.setAttribute("name", map.get("name")); 
		request.setAttribute("directory", directoryStr); 
		request.setAttribute("typeid", edit);
		return mapping.findForward("findBySmilies");
	}
	private String getList(List<Smilies> list) {
		StringBuffer ids = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ids.append(list.get(i).getId());
				ids.append(",");
			}
		}
		return ids.toString();
	}
	public ActionForward updateSmilies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "editsubmit")){
				String edit = request.getParameter("edit");
				String directory = request.getParameter("directory");
				List<Smilies> list = new ArrayList<Smilies>();
				List<Short> shortListIds = new ArrayList<Short>();
				String[] deletes = request.getParameterValues("delete[]");
				if (deletes != null) {
					for (int i = 0; i < deletes.length; i++) {
						if (deletes[i] != null && FormDataCheck.isNum(deletes[i])) {
							shortListIds.add(Short.valueOf(deletes[i]));
						}
					}
				}
				String hiddenids = request.getParameter("hiddenids"); 
				if (hiddenids != null) {
					String[] ids = hiddenids.split(",");
					if (ids != null) {
						for (int i = 0; i < ids.length; i++) {
							if (FormDataCheck.isValueString(ids[i])) {
								String display = request.getParameter(getDisplayorder(ids[i])); 
								String code = request.getParameter(getCode(ids[i]));
								Smilies s = new Smilies(); 
								s.setId(Short.valueOf(ids[i]));
								if (FormDataCheck.isValueString(display)&& FormDataCheck.isNum(display)) {
									s.setDisplayorder(Short.valueOf(display));
								} else {
									s.setDisplayorder(Short.valueOf("0"));
								}
								if (FormDataCheck.isValueString(code)) {
									code=code.replace("$","#");
									s.setCode(code);
									list.add(s);
								} else {
									shortListIds.add(Short.valueOf(ids[i])); 
								}
							}
						}
					}
				}
				imagetypesService.deleteSmiliesIds(shortListIds); 
				imagetypesService.updateSmiliesDisplayorderCode(list); 
				list = null;shortListIds=null;deletes=null;
				Cache.updateCache("post");
				request.setAttribute("message_key", "a_post_smilies_edit_succeed");
				request.setAttribute("url_forward","admincp.jsp?action=smilies&search=yes&edit="+edit+"&directory="+directory);
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=smilies");
		return null;
	}
	private String getDisplayorder(String ids) {
		StringBuffer sb = new StringBuffer("displayorder[");
		sb.append(ids);
		sb.append("]");
		return sb.toString();
	}
	private String getCode(String ids) {
		StringBuffer sb = new StringBuffer("code[");
		sb.append(ids);
		sb.append("]");
		return sb.toString();
	}
	public ActionForward ajaxSmilies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String simliepath = request.getParameter("path");
		String path = JspRunConfig.realPath+ "images/smilies/" + simliepath;
		String typeids = request.getParameter("typeid");
		Common.setResponseHeader(response);
		short typeid = (short)Common.range(Common.intval(typeids), 10000000, 0);
		int count = 0;
		File file = new File(path);
		try {
			String[] files = file.list();
			StringBuffer context = new StringBuffer();
			context.append("<form method='post' action=admincp.jsp?action=smilies&addsmilies=yes>");
			context.append("<input type='hidden' name='formHash' value='"+Common.formHash(request)+"'>");
			context.append("<table width='100%' border='0' cellpadding='0' cellspacing='0' class='tableborder'>");
			context.append("<tr class='header'><td colspan='5' align='left'>"+getMessage(request, "a_post_smilies_add")+"</td></tr>");
			context.append("<tr><td class='altbg1'><input class='checkbox' type='checkbox' name='chkall' class='category' onclick=\"checkall(this.form,'cate')\" checked>"+getMessage(request, "enabled")+"</td><td class='altbg2'>"+getMessage(request, "display_order")+"</td><td class='altbg1'>"+getMessage(request, "a_post_smilies_edit_code")+"</td><td class='altbg2'>"+getMessage(request, "filename")+"</td><td class='altbg1'>"+getMessage(request, "a_post_smilies_edit_image")+"</td></tr>");
			for (String s : files) {
				File smiliefile = new File(s);
				String filename = smiliefile.getName();
				if (!smilieService.findSmiliesbytypeid(typeid, filename)) {
					if (filename.matches("^[\\w\\-\\.\\[\\]\\(\\)\\<\\> &]+$")&&(filename.toLowerCase().lastIndexOf(".gif") != -1||filename.toLowerCase().lastIndexOf(".jpg") != -1)&&filename.length()<30) {
						count++;
						context.append("<tr><td class='altbg1'><input class='checkbox' type='checkbox' name='cate[]' value='1' checked></td><td class='altbg2'><input type='text' name='displayorder[]' value='0' size='2' maxlength='2'></td><td class='altbg1'><input type='text' name = 'code[]' id='codecode_"+count+"' value='' size='25' maxlength='30'></td><td class='altbg2'>" + filename + "<input type='hidden' name='url' id = 'codeurl_"+count+"'value='"+filename+"'></td><td class='altbg1'><img src='" + "images/smilies/" + simliepath + "/" + filename + "' onload=\"if(this.height>30) {this.resized=true; this.height=30; this.title='"+getMessage(request, "image_newwindow")+"';}\" onmouseover=\"if(this.resized) this.style.cursor='pointer';\" onclick=\"if(!this.resized) {return false;} else {window.open(this.src);}\"></td></tr>");
					}
				}
			}
			context.append("<tr><td colspan='5'>"+getMessage(request, "a_post_smilies_edit_add_code")+" <input type='text' size='2' value='"+getMessage(request, "a_post_smilies_prefix")+"' id='codeprefix' onclick='clearinput(this, \""+getMessage(request, "a_post_smilies_prefix")+"\")' style='vertical-align: middle'> + <select id='codemiddle' style='vertical-align: middle'><option value='1'>"+getMessage(request, "filename")+"</option><option value='2'>"+getMessage(request, "a_post_milies_edit_order_radom")+"</option></select> + <input type='text' size='2' value='"+getMessage(request, "a_post_smilies_suffix")+"' id='codesuffix' onclick='clearinput(this, \""+getMessage(request, "a_post_smilies_suffix")+"\")' style='vertical-align: middle'> <button type='button' onclick='addsmileycodes(\""+(count+1)+"\", \"code\");' style='vertical-align: middle'>"+getMessage(request, "apply")+"</button></td></tr>");
			context.append("<tr><td colspan='5' align='center'><center><input type='submit' name='smiliesubmit' class='button' value='"+getMessage(request, "submit")+"'>&nbsp;&nbsp;<input type='button' class='button' value='"+getMessage(request, "a_post_smilies_research")+"' onclick=\"ajaxget('" + request.getContextPath()+ "/smilies.do?smiliesaction=ajaxSmilies&path=" + simliepath+ "&typeid="+ typeids+ "', 'addsmilies', 'addsmilies','auto');doane(event);\"></center></td></tr></table>");
			context.append("<input type='hidden' name='typeid' value='"+typeid+"'></form>");
			if (count == 0) {
				context = new StringBuffer();
				context.append("<form method='post' action=admincp.jsp?action=smilies&addsmilies=yes>");
				context.append("<input type='hidden' name='formHash' value='"+Common.formHash(request)+"'>");
				context.append("<table width='100%' border='0' cellpadding='0' cellspacing='0' class='tableborder'>");
				context.append("<tr class='header'><td colspan='5' align='left'>"+getMessage(request, "a_post_smilies_add")+"</td></tr>");
				context.append("<tr><td colspan='5'>"+getMessage(request, "a_post_smilies_edit_add_tips","./images/smilies/" + simliepath)+"</td></tr></table>");
				context.append("<center><input type=\"button\" class=\"button\" value=\""+getMessage(request, "a_post_smilies_research")+"\" onclick=\"ajaxget('"
						+ request.getContextPath()+ "/smilies.do?smiliesaction=ajaxSmilies&path="+ simliepath
						+ "&typeid="+ typeids+ "', 'addsmilies', 'addsmilies','auto');doane(event);\"></center></form>");
			}
			response.getWriter().write(context.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward addSmilies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "smiliesubmit")){
				String code[] = request.getParameterValues("code[]");
				String cate[] = request.getParameterValues("cate[]");
				String displayorder[] = request.getParameterValues("displayorder[]");
				String url[] = request.getParameterValues("url");
				String typeid = request.getParameter("typeid");
				List<String> display = new ArrayList<String>();
				List<String> category = new ArrayList<String>();
				List<String> codes = new ArrayList<String>();
				List<String> urllist = new ArrayList<String>();
				List<Smilies> smilielist = new ArrayList<Smilies>();
				int size = url.length;
				for(int i=0;i<size;i++){
					display.add(displayorder[i]);
					if(cate!=null && cate.length>i){
						category.add(cate[i]);
					}else{
						category.add(null);
					}
					codes.add(code[i].replace("$", "#"));
					urllist.add(url[i]);
				}
				for(int i=0;i<size;i++){
					if(category.get(i)!=null && !category.get(i).equals("") && codes.get(i)!=null && !codes.get(i).equals("")){
						Smilies smilies = new Smilies();
						short displays = display.get(i)==null ? 0 : convertShort(display.get(i).toString());
						smilies.setCode(codes.get(i).toString());
						smilies.setDisplayorder(displays);
						smilies.setType("smiley");
						smilies.setUrl(urllist.get(i).toString());
						smilies.setTypeid(Short.valueOf(typeid));
						smilielist.add(smilies);
					}
				}
				smilieService.saveList(smilielist);
				Cache.updateCache("post");
				display = null;category=null;codes=null;urllist=null;smilielist=null;
				request.setAttribute("message_key", "a_post_smilies_edit_succeed");
				request.setAttribute("url_forward","admincp.jsp?action=smilies");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		Common.requestforward(response, "admincp.jsp?action=smilies");
		return null;
	}
	private short convertShort(String s){
		short count = 0;
		try{
			count = Short.valueOf(s);
		}catch(Exception e){}
		return count;
	}
}
