package cn.jsprun.struts.action;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.dao.OtherSetDao;
import cn.jsprun.domain.Advertisements;
import cn.jsprun.domain.Announcements;
import cn.jsprun.domain.Crons;
import cn.jsprun.domain.Faqs;
import cn.jsprun.domain.Forumlinks;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.domain.Medals;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Onlinelist;
import cn.jsprun.domain.OnlinelistId;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FinalProperty;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.vo.otherset.AdvEditVO;
import cn.jsprun.vo.otherset.AdvIndexVO;
import cn.jsprun.vo.otherset.AdvVO;
import cn.jsprun.vo.otherset.Advertisement;
import cn.jsprun.vo.otherset.CronInfo;
import cn.jsprun.vo.otherset.OnlinelistVO;
public class OtherManageAction extends BaseAction {
	public ActionForward magicconfig(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "magicsubmit")){
				String variables[] = {"magicstatus","magicmarket", "maxmagicprice"};
				Map<String,String> oldSettings=ForumInit.settings;
				Map<String,String> settings=new HashMap<String,String>();
				for(String variable:variables){
					String value=request.getParameter(variable);
					if("maxmagicprice".equals(variable)){
						value = String.valueOf(Common.toDigit(value));
					}
					this.putValue(variable,value, oldSettings, settings);
				}
				this.updateSettings(settings,oldSettings);
				request.setAttribute("message", getMessage(request, "a_other_magics_config_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=magic_config");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		boolean isfounder = (Boolean) request.getAttribute("isfounder");
		if (!isfounder) {
			request.setAttribute("message", getMessage(request, "noaccess_isfounder"));
			return mapping.findForward("message");
		}
		return mapping.findForward("other_magic_config");
	}
	public ActionForward magic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "magicsubmit")){
				String delete[] = request.getParameterValues("delete");
				if(delete!=null){
					String ids=Common.implodeids(delete);
					dataBaseService.runQuery("DELETE FROM jrun_magics WHERE magicid IN ("+ids+")", true);
					dataBaseService.runQuery("DELETE FROM jrun_membermagics WHERE magicid IN ("+ids+")", true);
					dataBaseService.runQuery("DELETE FROM jrun_magicmarket WHERE magicid IN ("+ids+")", true);
					dataBaseService.runQuery("DELETE FROM jrun_magiclog WHERE magicid IN ("+ids+")", true);
				}
				List<Map<String,String>> magics=dataBaseService.executeQuery("SELECT magicid FROM jrun_magics");
				if(magics!=null&&magics.size()>0){
					for (Map<String,String> magic : magics) {
						String magicid=magic.get("magicid");
						String name=request.getParameter("name_"+magic.get("magicid"));
						byte available=(byte)Common.range(Common.intval(request.getParameter("available_"+magic.get("magicid"))), 1, 0);
						String description=request.getParameter("description_"+magic.get("magicid"));
						byte displayorder=(byte)Common.range(Common.intval(request.getParameter("displayorder_"+magic.get("magicid"))), 127, -128);
						int price=Common.range(Common.intval(request.getParameter("price_"+magic.get("magicid"))), 16777215, 0);
						int num=Common.range(Common.intval(request.getParameter("num_"+magic.get("magicid"))), 65535, 0);
						if(name!=null){
							dataBaseService.runQuery("UPDATE jrun_magics SET available='"+available+"', name='"+Common.addslashes(name)+"',description='"+Common.addslashes(description)+"', displayorder='"+displayorder+"', price='"+price+"', num='"+num+"' WHERE magicid='"+magicid+"'",true);
						}
					}
				}
				String newname=request.getParameter("newname").trim();
				if(newname.length()>0){
					String newidentifier=request.getParameter("newidentifier").trim();
					List<Map<String,String>> magic=dataBaseService.executeQuery("SELECT magicid FROM jrun_magics WHERE identifier='"+Common.addslashes(newidentifier)+"'");
					if(magic!=null&&magic.size()>0){
						request.setAttribute("message", getMessage(request, "a_other_magics_identifier_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}else{
						String newdescription=request.getParameter("newdescription").trim();
						String newtype=request.getParameter("newtype");
						byte newdisplayorder=(byte)Common.range(Common.intval(request.getParameter("newdisplayorder")), 127, -128);
						int newprice=Common.range(Common.intval(request.getParameter("newprice")), 16777215, 0);
						int newnum=Common.range(Common.intval(request.getParameter("newnum")), 65535, 0);
						dataBaseService.runQuery("INSERT INTO jrun_magics (type, name, identifier, description, displayorder, price, num, filename,magicperm) VALUES ('"+newtype+"', '"+Common.addslashes(newname)+"', '"+Common.addslashes(newidentifier)+"', '"+Common.addslashes(newdescription)+"', '"+newdisplayorder+"', '"+newprice+"', '"+newnum+"','','')", true);
					}
				}
				request.setAttribute("message", getMessage(request, "a_other_magics_update_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=magic");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		boolean isfounder = (Boolean) request.getAttribute("isfounder");
		if (!isfounder) {
			request.setAttribute("message", getMessage(request, "noaccess_isfounder"));
			return mapping.findForward("message");
		}
		String typeid=request.getParameter("typeid");
		String addtype = typeid!=null ? "WHERE type='"+typeid+"'" : "";
		List<Map<String,String>> magiclist=dataBaseService.executeQuery("SELECT magicid,available,type,name,identifier,description,displayorder,price,num,filename FROM jrun_magics "+addtype+" ORDER BY displayorder");
		for(Map<String,String> mm : magiclist){
			String mmName = mm.get("name");
			String mmDescription = mm.get("description");
			String mmIdentifier = mm.get("identifier");
			if(mmName!=null){
				mm.put("name", mmName.replace("\"", "&quot;"));
			}
			if(mmDescription!=null){
				mm.put("description", mmDescription.replace("\"", "&quot;"));
			}
			if(mmIdentifier!=null){
				mm.put("identifier", mmIdentifier.replace("\"", "&quot;"));
			}
		}
		request.setAttribute("magiclist", magiclist);
		return mapping.findForward("other_magic");
	}
	@SuppressWarnings("unchecked")
	public ActionForward magicedit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "magiceditsubmit")){
				Short magicId = Short.valueOf(request.getParameter("magicid"));
				String newName = request.getParameter("newname");
				String newIdentifier = request.getParameter("newidentifier").toUpperCase().trim();
				byte[] identifierBA = null;
				try {
					String charset=JspRunConfig.CHARSET;
					identifierBA = newIdentifier.getBytes(charset);
					newIdentifier = identifierBA.length > 3 ? new String(identifierBA,0, 3,charset) : new String(identifierBA,charset); 
				} catch (UnsupportedEncodingException e) {
					request.setAttribute("message", getMessage(request, "a_other_magics_idinfo_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				Short newType = Short.valueOf(request.getParameter("newtype"));
				String newPriceStr = request.getParameter("newprice");
				String newNumStr = request.getParameter("newnum");
				String newWeightStr = request.getParameter("newweight");
				Byte newSupplyType = Byte.valueOf(request.getParameter("newsupplytype"));
				String newSupplyNum = request.getParameter("newsupplynum");
				String newFileName = request.getParameter("newfilename");
				String newDescription = request.getParameter("newdescription");
				if (newSupplyType.byteValue() == 0) {
					newSupplyNum = "0";
				}
				String[] usergroupsperm = request.getParameterValues("usergroupsperm");
				String[] forumperm = request.getParameterValues("forumperm[]");
				String[] tousergroupsperm = request.getParameterValues("tousergroupsperm");
				if (otherSetService.isMark(newIdentifier, magicId)) {
					request.setAttribute("message", getMessage(request, "a_other_magics_id_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				HashMap<String, String> hashMap = new HashMap<String, String>();
				if (usergroupsperm != null) {
					StringBuffer buffer = new StringBuffer("");
					for (int i = 0; i < usergroupsperm.length; i++) {
						buffer.append("\t" + usergroupsperm[i]);
					}
					hashMap.put("usergroups", buffer.toString());
				} else {
					hashMap.put("usergroups", "");
				}
				if (forumperm != null) {
					StringBuffer buffer = new StringBuffer("");
					for (int i = 0; i < forumperm.length; i++) {
						buffer.append("\t" + forumperm[i]);
					}
					hashMap.put("forum", buffer.toString());
				} else {
					hashMap.put("forum", "");
				}
				if (tousergroupsperm != null) {
					StringBuffer buffer = new StringBuffer("");
					for (int i = 0; i < tousergroupsperm.length; i++) {
						buffer.append("\t" + tousergroupsperm[i]);
					}
					hashMap.put("targetgroups", buffer.toString());
				} else {
					hashMap.put("targetgroups", "");
				}
				if (!FormDataCheck.isValueString(newName)) {
					request.setAttribute("message", getMessage(request, "a_other_magics_name_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (FormDataCheck.isValueString(newFileName)) {
					String filePath =JspRunConfig.realPath+"include/magics/"+ newFileName;
					File file = new File(filePath);
					if (!file.isFile()) {
						request.setAttribute("message", getMessage(request, "a_other_magics_file_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
				} else {
					newFileName = "";
				}
				int price = 0;
				try {
					price = Integer.valueOf(FormDataCheck
							.getNumberFromForm(newPriceStr));
				} catch (Exception exception) {
					price = FinalProperty.PRICE_COLUMN_MAX_VALUE;
				}
				int num = 0;
				try {
					num = Integer.valueOf(FormDataCheck.getNumberFromForm(newNumStr));
					if (num > FinalProperty.COUNT_COLUMN_MAX_VALUE) {
						num = FinalProperty.COUNT_COLUMN_MAX_VALUE;
					}
				} catch (Exception exception) {
					num = FinalProperty.COUNT_COLUMN_MAX_VALUE;
				}
				Long weightNum = Long.valueOf(FormDataCheck
						.getNumberFromForm(newWeightStr));
				short weight = weightNum > FinalProperty.WEIGHT_PROPERTY_MAX_VALUE ? FinalProperty.WEIGHT_PROPERTY_MAX_VALUE
						: weightNum.shortValue();
				Long longNum = Long.valueOf(FormDataCheck
						.getNumberFromForm(newSupplyNum));
				int supplynum = longNum > FinalProperty.SUPPLY_NUMBER_MAX_VALUE ? FinalProperty.SUPPLY_NUMBER_MAX_VALUE
						: longNum.intValue();
				String setAvailable = "";
				if (newIdentifier.equals("") || newFileName.equals("")) {
					setAvailable = "available=0 ,";
				} else {
					setAvailable = "available=1 ,";
				}
				String magicperm = dataParse.combinationChar(hashMap);
				dataBaseService.execute("UPDATE jrun_magics SET "+ setAvailable + "type='" + newType + "', name='" + Common.addslashes(newName)+ "', identifier='" + Common.addslashes(newIdentifier) + "',description='"+ Common.addslashes(newDescription) + "',price=" + price + ",num=" + num+ ",supplytype=" + newSupplyType + ",supplynum=" + supplynum+ ",weight=" + weight + ",filename='" + Common.addslashes(newFileName)+ "',magicperm='" + magicperm + "' WHERE magicid=" + magicId);
				request.setAttribute("message", getMessage(request, "a_other_magics_data_succeed"));
				request.setAttribute("url_forward","admincp.jsp?action=magic");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String magicid=request.getParameter("magicid");
		Map<String,String> magic=dataBaseService.executeQuery("SELECT * FROM jrun_magics WHERE magicid='"+magicid+"'").get(0);
		String mName = magic.get("name");
		if(mName!=null){
			magic.put("name", mName.replace("\"", "&quot;"));
		}
		Map<String, String> magicperm = dataParse.characterParse(magic.get("magicperm"), false);
		if (magicperm != null) {
			Set<String> keys = magicperm.keySet();
			Map<String, String> ids = null;
			for(String key:keys) {
				ids = new HashMap<String, String>();
				String[] values = magicperm.get(key).trim().split("\t");
				for (String id:values) {
					if (!id.equals("")) {
						ids.put(id, "");
					}
				}
				if (key.equals("usergroups")) {
					request.setAttribute("usergroupsperm", ids);
				} else if (key.equals("forum")) {
					request.setAttribute("forumperm", ids);
				} else if (key.equals("targetgroups")) {
					request.setAttribute("tousergroupsperm", ids);
				}
			}
		}
		request.setAttribute("magicBean", magic);
		request.setAttribute("userGroupList",dataBaseService.executeQuery("SELECT groupid, grouptitle FROM jrun_usergroups"));
		request.setAttribute("forumList", dataBaseService.executeQuery("SELECT fid, name FROM jrun_forums WHERE type <> 'group' AND status=1"));
		return mapping.findForward("other_magicedit");
	}
	public ActionForward magicmarket(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "marketsubmit")){
				List<Magicmarket> mmBeanList = new ArrayList<Magicmarket>();
				String deletes[] = request.getParameterValues("delete");
				String mIds[] = request.getParameterValues("mid");
				String prices[] = request.getParameterValues("price");
				String nums[] = request.getParameterValues("num");
				int len = 0;
				if (!FormDataCheck.isZeroOption(mIds)) {
					len = mIds.length;
					for (int i = 0; i < len; i++) {
						try {
							prices[i] = FormDataCheck.getNumberFromForm(prices[i]);
							if (Integer.parseInt(prices[i]) > FinalProperty.PRICE_COLUMN_MAX_VALUE) {
								prices[i] = FinalProperty.PRICE_COLUMN_MAX_VALUE .toString();
							}
						} catch (Exception exception) {
							prices[i] = FinalProperty.PRICE_COLUMN_MAX_VALUE.toString();
						}
						try {
							nums[i] = FormDataCheck.getNumberFromForm(nums[i]);
							if (Short.parseShort(nums[i]) > FinalProperty.COUNT_SELL_MAX_VALUE) {
								nums[i] = FinalProperty.COUNT_SELL_MAX_VALUE.toString();
							}
						} catch (Exception exception) {
							nums[i] = FinalProperty.COUNT_SELL_MAX_VALUE.toString();
						}
					}
				}
				Magicmarket mmBean = null;
				for (int i = 0; i < len; i++) {
					mmBean = new Magicmarket();
					mmBean.setMid(Short.valueOf(mIds[i]));
					mmBean.setPrice(Integer.parseInt(prices[i]));
					mmBean.setNum(Short.valueOf(nums[i]));
					mmBeanList.add(mmBean);
				}
				if (otherSetService.updateMagicMarketInfo(deletes, mmBeanList)) {
					request.setAttribute("message", getMessage(request, "a_other_magicmarket_update_succeed"));
					request.setAttribute("url_forward", "admincp.jsp?action=magicmarket");
					return mapping.findForward("message");
				} else {
					request.setAttribute("message", getMessage(request, "a_other_magicmarket_update_faild"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		boolean isfounder = (Boolean) request.getAttribute("isfounder");
		if (!isfounder) {
			request.setAttribute("message",getMessage(request, "noaccess_isfounder"));
			return mapping.findForward("message");
		}
		List<Map<String, String>> resultMapList = dataBaseService.executeQuery("SELECT mm.mid,mm.magicid,mm.username,mm.price,mm.num,mm.num*m.weight AS allweight,m.name,m.description FROM jrun_magicmarket AS mm LEFT JOIN jrun_magics AS m ON mm.magicid=m.magicid");
		request.setAttribute("valueObject", resultMapList);
		return mapping.findForward("other_magicmarket");
	}
	public ActionForward announcements(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String timeoffsetInSession = (String)request.getSession().getAttribute("timeoffset");
		SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat(FinalProperty.DATA_FORMAT,timeoffsetInSession);
		try{
			if(submitCheck(request, "announcesubmit")){
				int len = 0;
				String deletes[] = request.getParameterValues("delete");
				String aIds[] = request.getParameterValues("annid");
				String newDisplayOrders[] = request.getParameterValues("newdisplayorder");
				if(!FormDataCheck.isZeroOption(aIds)){
					len = aIds.length;
				}
				for(int i=0;i<len;i++){
					newDisplayOrders[i] = FormDataCheck.getNumberFromFormOfDisplayorder(newDisplayOrders[i]);
					try {
						Byte.valueOf(newDisplayOrders[i]);
					} catch (Exception exception) {
						if(newDisplayOrders[i].startsWith("-")){
							newDisplayOrders[i] = FinalProperty.ORDER_COLUMN_MIN_VALUE.toString();
						}else{
							newDisplayOrders[i] = FinalProperty.ORDER_COLUMN_MAX_VALUE.toString();
						}
					}
				}
				otherSetService.updateFormAnn(deletes, aIds,newDisplayOrders);
				String[] updateKeyArray = {"index","forumdisplay","viewthread"};
				Cache.updateCache(updateKeyArray);
				request.setAttribute("message", getMessage(request, "a_other_announcement_update_s"));
				request.setAttribute("url_forward", "admincp.jsp?action=announcements");
				return mapping.findForward("message");
			}else if(submitCheck(request, "addsubmit")){
				HttpSession session = request.getSession();
				Announcements annBean = null;
				String subject = request.getParameter("subject");
				String startTime = request.getParameter("starttime");
				String endTime = request.getParameter("endtime");	
				String type = request.getParameter("type");
				String userGroupIds[] = request.getParameterValues("usergroupid");
				String message = request.getParameter("message");
				if(!FormDataCheck.isValueString(subject)){
					request.setAttribute("message", getMessage(request, "a_other_announcement_name_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if((startTime = validateTime(startTime, FinalProperty.TIME_OF_Announcement_MAX, FinalProperty.TIME_OF_Announcement_MIN,simpleDateFormat, request))==null){
					return mapping.findForward("message");
				}
				if(endTime==null || "".equals(endTime)){
					endTime = "0";
				}else if((endTime = validateTime(endTime, FinalProperty.TIME_OF_Announcement_MAX, FinalProperty.TIME_OF_Announcement_MIN,simpleDateFormat, request))==null){
					return mapping.findForward("message");
				}
				if(!FormDataCheck.isValueString(message)){
					request.setAttribute("message", getMessage(request, "a_other_announcement_cannotempty"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				int groupIdLen = userGroupIds.length;
				StringBuffer groupIdSb = new StringBuffer();
				for(int i=0;i<groupIdLen;i++){
					if(userGroupIds[i].equals("")){
						groupIdSb.delete(0, groupIdSb.length());
						break;
					}
					if(i==groupIdLen-1)
						groupIdSb.append(userGroupIds[i]);
					else
						groupIdSb.append(userGroupIds[i]+",");
				}
				annBean = new Announcements();
				annBean.setSubject(subject);
				try {
					annBean.setStarttime(dateStrToDateLong(startTime,simpleDateFormat));
					annBean.setEndtime(endTime.equals("0")?0:dateStrToDateLong(endTime,simpleDateFormat));
				} catch (NumberFormatException e) {
					request.setAttribute("message", getMessage(request, "a_other_announcement_time_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				} catch (ParseException e) {
					request.setAttribute("message", getMessage(request, "a_other_announcement_time_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if(session.getAttribute("members")==null){
					request.setAttribute("message", getMessage(request, "a_other_login_agian"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				annBean.setAuthor(((Members)session.getAttribute("members")).getUsername());
				annBean.setType(Byte.parseByte(type));
				annBean.setGroups(groupIdSb.toString());
				annBean.setMessage(message);
				if(otherSetService.addFormAnn(annBean)){
					String[] updateKeyArray = {"index","forumdisplay","viewthread"};
					Cache.updateCache(updateKeyArray);
					request.setAttribute("message", getMessage(request, "a_other_announcement_add_s"));
					request.setAttribute("url_forward", "admincp.jsp?action=announcements");
					return mapping.findForward("message");
				}else{
					request.setAttribute("message", getMessage(request, "a_other_announcement_add_f"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		HttpSession session=request.getSession();
		String dateformat=(String)session.getAttribute("dateformat");
		SimpleDateFormat sdf_df= Common.getSimpleDateFormat(dateformat, timeoffsetInSession);
		List<Map<String,String>> announcements=dataBaseService.executeQuery("SELECT id,author,subject,type,displayorder,starttime,endtime,message FROM jrun_announcements ORDER BY displayorder, starttime DESC, id DESC");
		if(announcements!=null&&announcements.size()>0){
			for(Map<String,String> announcement:announcements){
				int starttime=Integer.parseInt(announcement.get("starttime"));
				int endtime=Integer.parseInt(announcement.get("endtime"));
				announcement.put("subject", Common.htmlspecialchars(Common.cutstr(announcement.get("subject"), 30)));
				announcement.put("message", Common.htmlspecialchars(Common.cutstr(announcement.get("message"), 20)));
				announcement.put("starttime",Common.gmdate(sdf_df, starttime));
				announcement.put("endtime",endtime>0?Common.gmdate(sdf_df, endtime):getMessage(request, "unlimited"));
			}
		}
		List<Map<String,String>> usergroups = dataBaseService.executeQuery("SELECT groupid, grouptitle FROM jrun_usergroups WHERE groupid<4 OR groupid>6");
		request.setAttribute("usergroups", usergroups);
		request.setAttribute("announcements", announcements);
		request.setAttribute("time", Common.gmdate(simpleDateFormat, Common.time()));
		return mapping.findForward("other_announcements");
	}
	public ActionForward annedit(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String timeoffsetInSession = (String)request.getSession().getAttribute("timeoffset");
		SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat(FinalProperty.DATA_FORMAT,timeoffsetInSession);
		try{
			if(submitCheck(request, "editsubmit")){
				Short annId = Short.valueOf(request.getParameter("annid"));
				String newSubject = request.getParameter("newsubject");
				String newStartTime = request.getParameter("newstarttime");
				String newEndTime = request.getParameter("newendtime");
				Byte newType = Byte.valueOf(request.getParameter("newtype"));
				String userGroupIds[] = request.getParameterValues("usergroupid");
				String newMessage = request.getParameter("newmessage");
				if(!FormDataCheck.isValueString(newSubject)){
					request.setAttribute("message", getMessage(request, "a_other_announcement_title_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if((newStartTime= validateTime(newStartTime, FinalProperty.TIME_OF_Announcement_MAX, FinalProperty.TIME_OF_Announcement_MIN,simpleDateFormat, request))==null){
					return mapping.findForward("message");
				}
				String nowTime = simpleDateFormat.format(System.currentTimeMillis());
				if(newEndTime==null || "".equals(newEndTime)){
					newEndTime = "0";
				}else if((newEndTime= validateTime(newEndTime, FinalProperty.TIME_OF_Announcement_MAX, nowTime,simpleDateFormat, request))==null){
					return mapping.findForward("message");
				}
				if(!FormDataCheck.isValueString(newMessage)){
					request.setAttribute("message", getMessage(request, "a_other_announcement_cannotempty"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				int groupIdLen = userGroupIds.length;
				StringBuffer groupIdSb = new StringBuffer();
				for(int i=0;i<groupIdLen;i++){
					if("".equals(userGroupIds[i])){
						groupIdSb.delete(0, groupIdSb.length());
						break;
					}
					if(i==groupIdLen-1)
						groupIdSb.append(userGroupIds[i]);
					else
						groupIdSb.append(userGroupIds[i]+",");
				}
				Announcements ann = otherSetService.queryAnnById(annId);
				ann.setSubject(newSubject);
				ann.setMessage(newMessage);
				ann.setType(newType);
				ann.setGroups(groupIdSb.toString());
				ann.setAuthor(((Members)request.getSession().getAttribute("members")).getUsername());
				try {
					ann.setStarttime(dateStrToDateLong(newStartTime,simpleDateFormat));
					ann.setEndtime("0".equals(newEndTime)?0:dateStrToDateLong(newEndTime,simpleDateFormat));
				} catch (NumberFormatException e) {
					request.setAttribute("message", getMessage(request, "a_other_announcement_time_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				} catch (ParseException e) {
					request.setAttribute("message", getMessage(request, "a_other_announcement_time_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if(otherSetService.updateAnn(ann)){
					String[] updateKeyArray = {"index","forumdisplay","viewthread"};
					Cache.updateCache(updateKeyArray);
					request.setAttribute("message", getMessage(request, "a_other_announcement_update_s"));
					request.setAttribute("url_forward", "admincp.jsp?action=announcements");
					return mapping.findForward("message");
				}else{
					request.setAttribute("message", getMessage(request, "a_other_announcement_update_f"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Map<String,String>> announcements = dataBaseService.executeQuery("SELECT a.id,a.subject,a.type,a.starttime,a.endtime,a.message,a.groups FROM jrun_announcements AS a WHERE id="+request.getParameter("annid"));
		if(announcements!=null&&announcements.size()>0){
			Map<String,String> announcement = announcements.get(0);
			int endtime = Integer.parseInt(announcement.get("endtime"));
			announcement.put("subject", Common.htmlspecialchars(announcement.get("subject")));
			announcement.put("message", Common.htmlspecialchars(announcement.get("message")));
			announcement.put("starttime", Common.gmdate(simpleDateFormat, Integer.parseInt(announcement.get("starttime"))));
			announcement.put("endtime", endtime>0?Common.gmdate(simpleDateFormat, endtime):"");
			String groups = announcement.get("groups");
			if(groups!=null&&!groups.equals("")){
				String[] groupids = groups.split(",");
				Map<String,String> groupSelect = new HashMap<String, String>();
				for(String groupid : groupids){
					groupSelect.put(groupid, "selected");
				}
				request.setAttribute("groupSelect", groupSelect);
			}
			List<Map<String,String>> usergroups = dataBaseService.executeQuery("SELECT groupid, grouptitle FROM jrun_usergroups WHERE groupid<4 OR groupid>6");
			request.setAttribute("usergroups", usergroups);
			request.setAttribute("announcement", announcement);
			return mapping.findForward("other_annedit");
		}else{
			request.setAttribute("message", getMessage(request, "a_other_announcement_not_exist"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
	}
	public ActionForward medals(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "medalsubmit")){
				List<Medals> medalBeanList = new ArrayList<Medals>();
				Medals mBean = null;
				Medals addMedalBean = null;
				String deletes[] = request.getParameterValues("delete");
				String mIds[] = request.getParameterValues("medalid");
				String medalName[] = request.getParameterValues("name");
				String medalImg[] = request.getParameterValues("image");
				String newName = request.getParameter("newname");
				String newAvailable = request.getParameter("newavailable");
				String newImage = request.getParameter("newimage");
				if(!FormDataCheck.isZeroOption(mIds)){
					int len = mIds.length;
					for(int i=0;i<len;i++){
						if(!FormDataCheck.isValueString(medalName[i])){
							request.setAttribute("message", getMessage(request, "a_other_medal_name_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
					for(int i=0;i<len;i++){
						mBean = new Medals();
						mBean.setMedalid(Short.valueOf(mIds[i]));
						mBean.setName(medalName[i]);
						mBean.setImage(medalImg[i]);
						String available = request.getParameter("available" + mIds[i]);
						if (available != null) {
							mBean.setAvailable(Byte.valueOf(available));
						} else {
							mBean.setAvailable((byte)0);
						}
						medalBeanList.add(mBean);
					}
				}
				if (FormDataCheck.isValueString(newImage)) {
					if (FormDataCheck.isValueString(newName)) {
						addMedalBean = new Medals();
						addMedalBean.setName(newName);
						addMedalBean.setImage(newImage);
						if (newAvailable != null) {
							addMedalBean.setAvailable(Byte.valueOf(newAvailable));
						} else {
							addMedalBean.setAvailable((byte) 0);
						}
					} else {
						request.setAttribute("message", getMessage(request, "a_other_medal_addname_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
				}
				otherSetService.updateForumMedals(deletes, medalBeanList, addMedalBean);
				request.setAttribute("message", getMessage(request, "a_other_medal_update_s"));
				request.setAttribute("url_forward", "admincp.jsp?action=medals");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Medals> medalBeanList = otherSetService.queryAllMedals();
		for(Medals medals : medalBeanList){
			String mName = medals.getName();
			String mImage = medals.getImage();
			if(mName!=null){
				medals.setName(mName.replace("\"", "&quot;"));
			}
			if(mImage!=null){
				medals.setImage(mImage.replace("\"", "&quot;"));
			}
		}
		request.setAttribute("medalBeanList", medalBeanList);
		return mapping.findForward("other_medals");
	}
	@SuppressWarnings("unchecked")
	public ActionForward adv(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settingMap = ForumInit.settings;
		String timeoffsetInSessino = (String)request.getSession().getAttribute("timeoffset");
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat(FinalProperty.DATA_FORMAT,timeoffsetInSessino);
		String step = request.getParameter("step");
		try{
			if(submitCheck(request, "advsubmit")){
				if(step == null){
					String deleteIds[] = request.getParameterValues("delete");
					String ids[] = request.getParameterValues("id");
					String displayOrders[] = request.getParameterValues("displayorder");
					String titles[] = request.getParameterValues("title");
					if(!FormDataCheck.isZeroOption(ids)){
						int len = ids.length;
						String[] availables = new String[len];
						String avaialbe = null;
						for(int i=0;i<len;i++){
							if(!FormDataCheck.isNum(displayOrders[i])){
								displayOrders[i] = "0";
							}
							if(!FormDataCheck.isValueString(titles[i])){
								request.setAttribute("message", getMessage(request, "a_other_adv_name_invalid"));
								request.setAttribute("return", true);
								return mapping.findForward("message");
							}
							if(titles[i].length()>50){
								request.setAttribute("message", getMessage(request, "a_other_adv_name_solong"));
								request.setAttribute("return", true);
								return mapping.findForward("message");
							}
							avaialbe = request.getParameter("available"+ids[i]);
							availables[i] = avaialbe==null?"0":avaialbe;
						}
						adService.updateAds(deleteIds, ids, titles, displayOrders, availables);
						Map globaladvs = Common.advertisement("all");
						settingMap.put("globaladvs", globaladvs.get("all")!=null?dataParse.combinationChar((Map) globaladvs.get("all")):"");
						settingMap.put("redirectadvs", globaladvs.get("redirect")!=null?dataParse.combinationChar((Map)globaladvs.get("redirect")):"");
						String[] updateKeyArray = {"archiver","register","index","forumdisplay","viewthread"};
						Cache.updateCache(updateKeyArray);
					}
					request.setAttribute("message", getMessage(request, "a_other_adv_update_s"));
					request.setAttribute("url_forward", "admincp.jsp?action=adv");
					return mapping.findForward("message");
				}else if("2".equals(step)){
					String title = request.getParameter("title");	
					if(!FormDataCheck.isValueString(title)){
						request.setAttribute("message", getMessage(request, "a_other_adv_name_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(title.length()>50){
						request.setAttribute("message", getMessage(request, "a_other_adv_name_solong"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					String startTime = request.getParameter("starttime");		
					if((startTime = validateStartTime(startTime, FinalProperty.TIME_OF_Announcement_MAX, FinalProperty.TIME_OF_Announcement_MIN,dateFormat, request))==null){
						return mapping.findForward("message");
					}
					String endTime = request.getParameter("endtime");			
					String nowTime = dateFormat.format(System.currentTimeMillis());
					if(endTime==null || "".equals(endTime)){
						endTime = "0";
					}else if((endTime = validateEndTime(endTime,startTime,nowTime, FinalProperty.TIME_OF_Announcement_MAX, FinalProperty.TIME_OF_Announcement_MIN,dateFormat, request))==null){
						return mapping.findForward("message");
					}
					int starttime=0;
					int endtime=0;
					try {
						starttime=dateStrToDateLong(startTime,dateFormat);
						endtime="0".equals(endTime)?0:dateStrToDateLong(endTime,dateFormat);
					} catch (ParseException e) {
						request.setAttribute("message", getMessage(request, "a_other_adv_time_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					String targets[] = request.getParameterValues("targets");	
					String target = "";
					if(targets!=null){
						int len = targets.length;
						StringBuffer targetSb = new StringBuffer();
						for(int i=0;i<len;i++){
							if(targets[i].equals("all")){
								targetSb.delete(0, targetSb.length());
								targetSb.append("all");
								break;
							}
							if(targets[i].equals("")){
								continue;
							}
							if(i==len-1){
								targetSb.append(targets[i]);
							}else{
								targetSb.append(targets[i] + "\t");
							}
						}
						target = targetSb.toString();
					}
					String type = request.getParameter("type");	
					Map<String,String> adMap = new HashMap<String,String>();
					String position = request.getParameter("position"); 
					if (position != null) {
						if(type.equals("intercat")){
							String[] positions = request.getParameterValues("position");
							StringBuffer positionSb = new StringBuffer();
							for(String temp : positions){
								if(!temp.equals("")){
									if(temp.equals("0")){
										positionSb.append(temp+",");
										break;
									}else{
										positionSb.append(temp+",");
									}
								}
							}
							if(positionSb.length()>0){
								position = positionSb.substring(0,positionSb.length()-1);
							}else{
								position = "";
							}
						}
						adMap.put("position", position);
					} else {
						adMap.put("position", null);
					}
					String displayorder = "";
					if(type.equals("float")){
						String floatHeight = request.getParameter("floath");
						if(!FormDataCheck.isNum(floatHeight)){
							floatHeight = "200";
						}
						adMap.put("floath", floatHeight);
					}
					if(type.equals("thread")){
						String[] displayorderArray = request.getParameterValues("displayorder");
						StringBuffer buffer = new StringBuffer();
						for(String dpoTemp : displayorderArray){
							if(!dpoTemp.equals("")){
								if(dpoTemp.equals("0")){
									buffer.append(dpoTemp+"\t");
									break;
								}else{
									buffer.append(dpoTemp+"\t");
								}
							}
						}
						if(buffer.length()>0){
							displayorder = buffer.substring(0,buffer.length()-1);
						}
					}
					String code = null;
					String style = request.getParameter("style");	
					if(style.equals("code")){
						code = request.getParameter("htmlcode");
					}
					if(style.equals("text")){
						String textTitle = request.getParameter("texttitle"); 
						adMap.put("title", textTitle);
						String textLink = request.getParameter("textlink"); 
						adMap.put("link", textLink);
						String textSize = request.getParameter("textsize"); 
						adMap.put("size", textSize);
						if(!FormDataCheck.isValueString(textTitle)){
							request.setAttribute("message", getMessage(request, "a_other_adv_text_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						if(!FormDataCheck.isValueString(textLink)){
							request.setAttribute("message", getMessage(request, "a_other_adv_link_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						code = "<a href=\""+textLink+"\" target=\"_blank\" style=\"font-size: "+textSize+"\">"+textTitle+"</a>";
					}
					if(style.equals("image")){
						String imageUrl = request.getParameter("imageurl"); 
						adMap.put("url", imageUrl);
						String imageLink = request.getParameter("imagelink"); 
						adMap.put("link", imageLink);
						String imageWidth = request.getParameter("imagewidth"); 
						if(!FormDataCheck.isNum(imageWidth)){
							imageWidth = "";
						}
						if(!imageWidth.equals("")){
							adMap.put("width", imageWidth);
						}
						String imageHeight = request.getParameter("imageheight"); 
						if(!FormDataCheck.isNum(imageHeight)){
							imageHeight = "";
						}
						if(!imageHeight.equals("")){
							adMap.put("height", imageHeight);
						}
						String imageAlt = request.getParameter("imagealt"); 
						if(FormDataCheck.isValueString(imageAlt)){
							adMap.put("alt", imageAlt);
						}
						if(!FormDataCheck.isValueString(imageUrl)){
							request.setAttribute("message", getMessage(request, "a_other_adv_image_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						if(!FormDataCheck.isValueString(imageLink)){
							request.setAttribute("message", getMessage(request, "a_other_adv_image_invalid2"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						StringBuffer buffer = new StringBuffer("<a href=\""+imageLink+"\" target=\"_blank\"><img src=\""+imageUrl+"\"");
						if(!imageHeight.equals("")){
							buffer.append(" height=\""+imageHeight+"\"");
						}
						if(!imageWidth.equals("")){
							buffer.append(" width=\""+imageWidth+"\"");
						}
						buffer.append(" alt=\""+imageAlt+"\" border=\"0\"></a>");
						code = buffer.toString();
					}
					if(style.equals("flash")){
						String flashUrl = request.getParameter("flashurl"); 
						String flashLink = request.getParameter("flashlink"); 
						String flashWidth = request.getParameter("flashwidth"); 
						String flashHeight = request.getParameter("flashheight"); 
						if(!FormDataCheck.isValueString(flashUrl)){
							request.setAttribute("message", getMessage(request, "a_other_adv_flash_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						if(!FormDataCheck.isNum(flashWidth)){
							request.setAttribute("message", getMessage(request, "a_other_adv_flash_invalid2"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						if(!FormDataCheck.isNum(flashHeight)){
							request.setAttribute("message", getMessage(request, "a_other_adv_flash_invalid3"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						adMap.put("url", flashUrl);
						adMap.put("link", flashLink);
						adMap.put("width", flashWidth);
						adMap.put("height", flashHeight);
						StringBuffer codBuffer = new StringBuffer();
						boolean flashLinkBool = false;
						if(flashLink!=null){
							String flashLinkTrim = flashLink.trim();
							if(!flashLinkTrim.equals("")){
								codBuffer.append("<button onclick=\"window.open('"+flashLinkTrim+"')\" style=\"border:0px;width:"+flashWidth+"px;height:"+flashHeight+"px;background:background-color\" >");
								flashLinkBool = true;
							}
						}
						codBuffer.append("<embed width=\""+flashWidth+"\" height=\""+flashHeight+"\" src=\""+flashUrl+"\" type=\"application/x-shockwave-flash\"");
						if(flashLinkBool){
							codBuffer.append(" wmode=\"transparent\"");
						}
						codBuffer.append("></embed>");
						if(flashLinkBool){
							codBuffer.append("</button>");
						}
						code = codBuffer.toString();
					}
					adMap.put("style", style);
					adMap.put("type", type);
					adMap.put("html", code);
					adMap.put("displayorder", displayorder);
					String adParam =dataParse.combinationChar(adMap);
					Advertisements ad = null;
					ad = new Advertisements();
					ad.setTitle(title);	
					ad.setType(type);	
					ad.setTargets(target); 
					ad.setStarttime(starttime);		
					ad.setEndtime(endtime);			
					ad.setCode(code);
					ad.setParameters(adParam);
					ad.setAvailable((byte)1);
					if(adService.addAd(ad) == true){
						Map globaladvs = Common.advertisement("all");
						settingMap.put("globaladvs", globaladvs.get("all")!=null?dataParse.combinationChar((Map) globaladvs.get("all")):"");
						settingMap.put("redirectadvs", globaladvs.get("redirect")!=null?dataParse.combinationChar((Map)globaladvs.get("redirect")):"");
						String[] updateKeyArray = {"archiver","register","index","forumdisplay","viewthread"};
						Cache.updateCache(updateKeyArray);
						request.setAttribute("message", getMessage(request, "a_other_adv_add_s"));
						request.setAttribute("url_forward", "admincp.jsp?action=adv");
						return mapping.findForward("message");
					}else{
						request.setAttribute("message", getMessage(request, "a_other_adv_add_f"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
				}else{
					request.setAttribute("message", getMessage(request, "undefined_action"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		if(step == null){
			String title = request.getParameter("title");
			String startTime = request.getParameter("starttime");
			String type = request.getParameter("type");
			String orderBy = request.getParameter("orderby");
			List<Map<String,String>> formId_nameMapList = dataBaseService.executeQuery("SELECT f.fid,f.name FROM jrun_forums AS f WHERE f.type<>'group'");
			Map<String,String> forumMap = new HashMap<String, String>();
			for(Map<String,String> tempMap : formId_nameMapList){
				forumMap.put(tempMap.get("fid"), tempMap.get("name"));
			}
			StringBuffer url = new StringBuffer();
			url.append("admincp.jsp?action=adv");
			if(title!=null&&!title.equals("")){
				url.append("&title="+title);
			}
			if(startTime!=null&&!startTime.equals("")){
				url.append("&starttime="+startTime);
			}
			if(type!=null&&!type.equals("")){
				url.append("&type="+type);
			}
			if(orderBy!=null&&!orderBy.equals("")){
				url.append("&orderby="+orderBy);
			}
			int page = Math.max(Common.intval(request.getParameter("page")), 1);
			int perpage=15;
			int start_limit = (page - 1) * perpage;
			if(start_limit<0){
				page=1;
				start_limit=0;
			}
			String countKey="dataCount";
			String listKey = "dataList";
			Map map = adService.queryAdvWithPagination(title, startTime, type, orderBy, start_limit, perpage, countKey, listKey);
			Map<String,Object> multi=Common.multi((Integer)map.get(countKey), perpage, page, url.toString(), 0, 10, true, false, null);
			request.setAttribute("multi",multi);
			AdvIndexVO advIndexVO = setAdvIndexVO((List<Advertisements>)map.get(listKey), forumMap,title,orderBy, startTime, type,dateFormat,request);
			request.setAttribute("valueObject", advIndexVO);
			return mapping.findForward("other_adv");
		}else if("1".equals(step)){
			HttpSession session=request.getSession();
			String title = request.getParameter("title");
			String style = request.getParameter("style");
			String type = request.getParameter("type");
			AdvVO advVO = new AdvVO();
			advVO.setStyle(style);
			advVO.setTitle(title!=null?title.replace("\"", "&quot;"):null);
			advVO.setType(type);
			advVO.setStarttime(Common.gmdate(dateFormat, Common.time()));
			if(type.equals("intercat")){ 
				List<Map<String,String>> forumList = dataBaseService.executeQuery("SELECT fid,name FROM jrun_forums WHERE type='group'");
				StringBuffer forumlistBuffer =new StringBuffer();
				for(Map<String,String> forum:forumList){
					forumlistBuffer.append("<option value='"+forum.get("fid")+"'>"+Common.strip_tags(forum.get("name"))+"</option>");
				}
				advVO.setSelectContent(forumlistBuffer.toString());
			}else{
				Members member = (Members)session.getAttribute("user");
				Short groupid = (Short)session.getAttribute("jsprun_groupid");
				advVO.setSelectContent(Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
			}
			if(type.equals("thread")){ 
				advVO.setPostperpage(Integer.valueOf(ForumInit.settings.get("postperpage")));
			}
			request.setAttribute("valueObject", advVO);
			return mapping.findForward("other_advadd");
		}else{
			request.setAttribute("message", getMessage(request, "undefined_action"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
	}
	public ActionForward advedit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settingMap = ForumInit.settings;
		String timeoffsetInSession = (String)request.getSession().getAttribute("timeoffset");
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat(FinalProperty.DATA_FORMAT,timeoffsetInSession);
		try{
			if(submitCheck(request, "advsubmit")){
				String type = request.getParameter("type");	
				String advId = request.getParameter("advid");	
				String style = request.getParameter("style");	
				String title = request.getParameter("title");	
				String startTime = request.getParameter("starttime");		
				String endTime = request.getParameter("endtime");			
				String[] targets = request.getParameterValues("targets");	
				String errorInfo=null;
				if(advId==null||title==null||type==null||startTime==null||endTime==null||style==null){
					errorInfo= getMessage(request, "undefined_action");
				}else if(targets==null){
					errorInfo= getMessage(request, "a_other_adv_bound_invalid");
				}else if(!FormDataCheck.isValueString(title)){
					errorInfo= getMessage(request, "a_other_adv_title_invalid");
				}else if(title.length()>50){
					errorInfo= getMessage(request, "a_other_adv_title_solang");
				}
				if(errorInfo!=null){
					request.setAttribute("message", errorInfo);
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if((startTime = validateStartTime(startTime, FinalProperty.TIME_OF_Announcement_MAX, FinalProperty.TIME_OF_Announcement_MIN,dateFormat, request))==null){
					return mapping.findForward("message");
				}
				String nowTime = dateFormat.format(System.currentTimeMillis());
				if("".equals(endTime)){
					endTime = "0";
				}else if((endTime = validateEndTime(endTime,startTime,nowTime, FinalProperty.TIME_OF_Announcement_MAX, FinalProperty.TIME_OF_Announcement_MIN,dateFormat, request))==null){
					return mapping.findForward("message");
				}
				Map<String,String> parametersMap = new HashMap<String, String>();
				String html = "";
				String position = "";
				String displayOrder = "";
				if(type.equals("footerbanner")||type.equals("thread")||type.equals("intercat")){
					if(type.equals("thread")){
						String[] displayorder = request.getParameterValues("displayorder"); 
						if(displayorder==null||displayorder.length==0){
							request.setAttribute("message", getMessage(request, "a_other_adv_bound_show_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						StringBuffer dpoBuffer = new StringBuffer();
						for(String dpoTemp : displayorder){
							if(!dpoTemp.equals("")){
								if(!dpoTemp.equals("0")){
									dpoBuffer.append(dpoTemp+"\t");
								}else{
									dpoBuffer.append(dpoTemp+"\t");
									break;
								}
							}
						}
						if(dpoBuffer.length()>0){
							displayOrder = dpoBuffer.substring(0, dpoBuffer.length()-1);
						}
					}
					if(type.equals("intercat")){
						String[] positionArray = request.getParameterValues("position");
						if(positionArray==null||positionArray.length==0){
							request.setAttribute("message", getMessage(request, "a_other_adv_place_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
						StringBuffer positionBuffer = new StringBuffer();
						for(String temp : positionArray){
							if(!temp.equals("")){
								if(temp.equals("0")){
									positionBuffer.append(temp+",");
									break;
								}else{
									positionBuffer.append(temp+",");
								}
							}
						}
						position = positionBuffer.substring(0,positionBuffer.length()-1);
					}else{
						position = request.getParameter("position");
						if(position==null){
							request.setAttribute("message", getMessage(request, "a_other_adv_place_invalid"));
							request.setAttribute("return", true);
							return mapping.findForward("message");
						}
					}
				}
				if(type.equals("float")){
					String floath = request.getParameter("floath");
					if(!FormDataCheck.isNum(floath)){
						floath = "200";
					}
					parametersMap.put("floath", floath);
				}
				if(style.equals("code")){
					html = request.getParameter("htmlcode");
					if(html==null){
						request.setAttribute("message", getMessage(request, "undefined_action"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(!FormDataCheck.isValueString(html)){
						request.setAttribute("message", getMessage(request, "a_other_adv_html_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
				}else 
					if(style.equals("text")){
					String textTitle = request.getParameter("texttitle"); 
					String textLink = request.getParameter("textlink");
					String textSize = request.getParameter("textsize");
					if(textTitle==null||textLink==null||textSize==null){
						request.setAttribute("message", getMessage(request, "undefined_action"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(!FormDataCheck.isValueString(textTitle)){
						request.setAttribute("message", getMessage(request, "a_other_adv_text_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(!FormDataCheck.isValueString(textLink)){
						request.setAttribute("message", getMessage(request, "a_other_adv_link_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					parametersMap.put("title", textTitle);
					parametersMap.put("link", textLink);
					parametersMap.put("size", textSize);
					html = "<a href=\""+textLink+"\" target=\"_blank\" style=\"font-size: "+textSize+"\">"+textTitle+"</a>";
				}else 
					if(style.equals("image")){
					String imageUrl = request.getParameter("imageurl");
					String imageLink = request.getParameter("imagelink");
					String imageWidth = request.getParameter("imagewidth");
					String imageHeight = request.getParameter("imageheight");
					String imageAlt = request.getParameter("imagealt");
					if(imageUrl==null||imageLink==null||imageWidth==null||imageHeight==null||imageAlt==null){
						request.setAttribute("message", getMessage(request, "undefined_action"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(!FormDataCheck.isValueString(imageUrl)){
						request.setAttribute("message", getMessage(request, "a_other_adv_image_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(!FormDataCheck.isValueString(imageLink)){
						request.setAttribute("message", getMessage(request, "a_other_adv_image_invalid2"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(!FormDataCheck.isNum(imageWidth)){
						imageWidth = "";
					}
					if(!FormDataCheck.isNum(imageHeight)){
						imageHeight = "";
					}
					parametersMap.put("url", imageUrl);
					parametersMap.put("link", imageLink);
					if(!imageWidth.equals("")){
						parametersMap.put("width", imageWidth);
					}
					if(!imageHeight.equals("")){
						parametersMap.put("height", imageHeight);
					}
					if(FormDataCheck.isValueString(imageAlt)){
						parametersMap.put("alt", imageAlt);
					}
					StringBuffer buffer = new StringBuffer("<a href=\""+imageLink+"\" target=\"_blank\"><img src=\""+imageUrl+"\"");
					if(!imageHeight.equals("")){
						buffer.append(" height=\""+imageHeight+"\"");
					}
					if(!imageWidth.equals("")){
						buffer.append(" width=\""+imageWidth+"\"");
					}
					buffer.append(" alt=\""+imageAlt+"\" border=\"0\"></a>");
					html = buffer.toString();
				}else
					if(style.equals("flash")){
					String flashUrl = request.getParameter("flashurl");
					String flashLink = request.getParameter("flashlink"); 
					String flashWidth = request.getParameter("flashwidth");
					String flashHeight = request.getParameter("flashheight");
					if(flashUrl==null||flashLink==null||flashWidth==null||flashHeight==null){
						request.setAttribute("message", getMessage(request, "undefined_action"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(!FormDataCheck.isValueString(flashUrl)){
						request.setAttribute("message", getMessage(request, "a_other_adv_flash_invalid"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(!FormDataCheck.isValueString(flashWidth)){
						request.setAttribute("message", getMessage(request, "a_other_adv_flash_invalid2"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					if(!FormDataCheck.isValueString(flashHeight)){
						request.setAttribute("message", getMessage(request, "a_other_adv_flash_invalid3"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
					parametersMap.put("url", flashUrl);
					parametersMap.put("link", flashLink);
					parametersMap.put("width", flashWidth);
					parametersMap.put("height", flashHeight);
					StringBuffer htmlBuffer = new StringBuffer();
					boolean flashLinkBool = false;
					if(flashLink!=null){
						String flashLinkTrim = flashLink.trim();
						if(!flashLinkTrim.equals("")){
							htmlBuffer.append("<button onclick=\"window.open('"+flashLinkTrim+"')\" style=\"border:0px;width:"+flashWidth+"px;height:"+flashHeight+"px;background:background-color\" >");
							flashLinkBool = true;
						}
					}
					htmlBuffer.append("<embed width=\""+flashWidth+"\" height=\""+flashHeight+"\" src=\""+flashUrl+"\" type=\"application/x-shockwave-flash\"");
					if(flashLinkBool){
						htmlBuffer.append(" wmode=\"transparent\"");
					}
					htmlBuffer.append("></embed>");
					if(flashLinkBool){
						htmlBuffer.append("</button>");
					}
					html = htmlBuffer.toString();
				}
				parametersMap.put("type", type);
				parametersMap.put("style", style);
				parametersMap.put("position", position);
				parametersMap.put("html", html);
				parametersMap.put("displayorder", displayOrder);
				String targetsResult = "";
				if(targets!=null){
					int len = targets.length;
					StringBuffer targetSb = new StringBuffer();
					for(int i=0;i<len;i++){
						if(targets[i].equals("all")){
							targetSb.delete(0, targetSb.length());
							targetSb.append("all");
							break;
						}
						if(targets[i].equals("")){
							continue;
						}
						if(i==len-1){
							targetSb.append(targets[i]);
						}else{
							targetSb.append(targets[i] + "\t");
						}
					}
					targetsResult = targetSb.toString();
				}
				String parameters = dataParse.combinationChar(parametersMap);
				int startTimeInt = 0;
				int endTimeInt = 0;
				try {
					startTimeInt = ((int)(dateFormat.parse(startTime).getTime()/1000));
					endTimeInt = endTime.equals("0")?0:((int)(dateFormat.parse(endTime).getTime()/1000));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dataBaseService.execute("UPDATE jrun_advertisements SET title='"+title.replace("\\", "\\\\").replace("'", "\\'")+"',targets='"+targetsResult.replace("\\", "\\\\").replace("'", "\\'")+"', parameters='"+parameters.replace("\\", "\\\\").replace("'", "\\'")+"', code='"+html.replace("\\", "\\\\").replace("'", "\\'")+"',starttime="+startTimeInt+",endtime="+endTimeInt+" WHERE advid="+advId);
				Map globaladvs = Common.advertisement("all");
				settingMap.put("globaladvs", globaladvs.get("all")!=null?dataParse.combinationChar((Map) globaladvs.get("all")):"");
				settingMap.put("redirectadvs", globaladvs.get("redirect")!=null?dataParse.combinationChar((Map)globaladvs.get("redirect")):"");
				String[] updateKeyArray = {"archiver","register","index","forumdisplay","viewthread"};
				Cache.updateCache(updateKeyArray);
				request.setAttribute("message", getMessage(request, "a_other_adv_edit_s"));
				request.setAttribute("url_forward","admincp.jsp?action=adv");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		HttpSession session = request.getSession();
		int advid = Common.toDigit(request.getParameter("advid"));
		Advertisements advertisements = adService.queryAdById(advid);
		Members member = (Members)session.getAttribute("user");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		int postperpage = Integer.parseInt(ForumInit.settings.get("postperpage"));
		AdvEditVO advEditVO = adService.getAdvEditVO(advertisements, groupid, member, postperpage,dateFormat);
		request.setAttribute("valueObject", advEditVO);
		return mapping.findForward("other_advedit");
	}
	public ActionForward forumlinks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "forumlinksubmit")){
				List<Forumlinks> updateOrSaveForumLinks = new ArrayList<Forumlinks>();
				Forumlinks flBean = null;
				Forumlinks addFlBean = null;
				String deletes[] = request.getParameterValues("delete");
				String fIds[] = request.getParameterValues("fid");
				String displayorder[] = request.getParameterValues("displayorder");
				String displayorder_h[] = request.getParameterValues("displayorder_h");
				String name[] = request.getParameterValues("name");
				String name_h[] = request.getParameterValues("name_h");
				String url[] = request.getParameterValues("url");
				String url_h[] = request.getParameterValues("url_h");
				String description[] = request.getParameterValues("description");
				String description_h[] = request.getParameterValues("description_h");
				String logo[] = request.getParameterValues("logo");
				String logo_h[] = request.getParameterValues("logo_h");
				String newName = request.getParameter("newname");
				String newUrl = request.getParameter("newurl");
				String newDescription = request.getParameter("newdescription");
				String newLogo = request.getParameter("newlogo");
				String newDisplayOrder = request.getParameter("newdisplayorder");
				List<String> deleteList = new ArrayList<String>();
				if(!FormDataCheck.isZeroOption(deletes)){
					for(String delete : deletes){
						deleteList.add(delete);
					}
				}
				if (!FormDataCheck.isZeroOption(fIds)) {
					int len = fIds.length;
					for (int i = 0; i < len; i++) {
						if(!deleteList.contains(fIds[i])&&(!name[i].equals(name_h[i])
								||!url[i].equals(url_h[i])
								||!displayorder[i].equals(displayorder_h[i])
								||!description[i].equals(description_h[i])
								||!logo[i].equals(logo_h[i]))){
							flBean = new Forumlinks();
							flBean.setId(Short.valueOf(fIds[i]));
							flBean.setName(name[i]);
							flBean.setUrl(url[i]);
							flBean.setDescription(description[i]);
							try{ 
								flBean.setDisplayorder((short)(Double.parseDouble(displayorder[i])));
							}catch(Exception exception){
								flBean.setDisplayorder((short)0);
							}
							flBean.setLogo(logo[i]);
							updateOrSaveForumLinks.add(flBean);
						}
					}
				}
				if (FormDataCheck.isValueString(newName)){
					addFlBean = new Forumlinks();
					try{
						newDisplayOrder = Short.valueOf((short)(Double.parseDouble(newDisplayOrder))).toString();
					}catch(Exception exception){
						newDisplayOrder="0";
					}
					addFlBean.setDisplayorder(Short.valueOf(newDisplayOrder));
					addFlBean.setName(newName);
					addFlBean.setUrl(newUrl);
					addFlBean.setDescription(newDescription);
					addFlBean.setLogo(newLogo);
					updateOrSaveForumLinks.add(addFlBean);
				}
				otherSetService.updateForumLinks(deleteList, updateOrSaveForumLinks);
				String[] updateKeyArray = {"index"};
				Cache.updateCache(updateKeyArray);
				request.setAttribute("message", getMessage(request, "a_other_forumlinks_update_s"));
				request.setAttribute("url_forward", "admincp.jsp?action=forumlinks");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		List<Forumlinks> forumlinksList = otherSetService.queryAllForumLinkInfo();
		for(Forumlinks fm : forumlinksList){
			String fName = fm.getName();
			String fUrl = fm.getUrl();
			String fLogo = fm.getLogo();
			String fdsp = fm.getDescription();
			if (fName != null) {
				fm.setName(fName.replace("\"", "&quot;"));
			}
			if (fUrl != null) {
				fm.setUrl(fUrl.replace("\"", "&quot;"));
			}
			if (fLogo != null) {
				fm.setLogo(fLogo.replace("\"", "&quot;"));
			}
			if (fdsp != null) {
				fm.setDescription(fdsp.replace("\"", "&quot;"));
			}
		}
		request.setAttribute("flList", forumlinksList);
		return mapping.findForward("other_forumlinks");
	}
	public ActionForward crons(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "cronssubmit")){
				String[] cronId = request.getParameterValues("cronId");
				if(!FormDataCheck.isZeroOption(cronId)){
					String[] delete = request.getParameterValues("delete");
					List<String> deleteIdList = new ArrayList<String>();
					if(!FormDataCheck.isZeroOption(delete)){
						StringBuffer delete_SQLBuffer = new StringBuffer("DELETE FROM jrun_crons WHERE cronid IN(");
						for(String del : delete){
							delete_SQLBuffer.append(del+",");
							deleteIdList.add(del);
						}
						int delete_SQLBufferLen = delete_SQLBuffer.length();
						delete_SQLBuffer.replace(delete_SQLBufferLen-1, delete_SQLBufferLen, ")");
						dataBaseService.execute(delete_SQLBuffer.toString());
					}
					int cronCount = cronId.length;
					String[] cronName = request.getParameterValues("cronName");
					String[] namenew = request.getParameterValues("namenew");
					String[] cronAvailable = request.getParameterValues("cronAvailable");
					String availablenew_ = null;
					String namenew_ = null;
					String cronId_ = null;
					for(int i = 0;i<cronCount;i++){
						cronId_ = cronId[i];
						namenew_ = namenew[i];
						availablenew_ = request.getParameter("availablenew["+cronId_+"]");
						if(!deleteIdList.contains(cronId_)&&(!cronName[i].equals(namenew_)||((cronAvailable[i].equals("0")&&availablenew_!=null)||(cronAvailable[i].equals("1")&&availablenew_==null)))){
							if(availablenew_==null){
								availablenew_="0";
							}
							dataBaseService.execute("UPDATE jrun_crons SET available="+availablenew_+",name='"+Common.addslashes(namenew_)+"' WHERE cronid="+cronId_);
						}
					}
				}
				String newname = request.getParameter("newname");
				if(newname!=null&&!(newname = (newname.trim())).equals("")){
					newname = Common.addslashes(newname);
					dataBaseService.execute("INSERT INTO jrun_crons VALUES(DEFAULT,0,'user','"+newname+"','',0,0,-1,-1,-1,'')");
				}
				List<Map<String,String>> nextrunInfo = dataBaseService.executeQuery("SELECT nextrun FROM jrun_crons WHERE available=1 AND nextrun>'0' ORDER BY nextrun LIMIT 1");
				Map<String,String> settingMap = ForumInit.settings;
				settingMap.put("cronnextrun", nextrunInfo!=null&&nextrunInfo.size()>0?nextrunInfo.get(0).get("nextrun"):"0");
				request.setAttribute("message", getMessage(request, "a_other_crons_succeed"));
				request.setAttribute("url_forward","admincp.jsp?action=crons");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		try{
			if(submitCheck(request, "run",true)){
				String run=request.getParameter("run");
				Members members = (Members)request.getSession().getAttribute("members");
				if(members==null){
					request.setAttribute("message", getMessage(request, "undefined_action"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}else{
					byte adminid = members.getAdminid();
					if(adminid!=1){
						request.setAttribute("message", getMessage(request, "undefined_action"));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
				}
				cronsService.cronRunning(request, response, run);
				request.setAttribute("message", getMessage(request, "a_other_crons_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=crons");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String timeoffsetInSession = (String)request.getSession().getAttribute("timeoffset");
		List<Crons> cronsList = cronsService.queryAllCrons();
		SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat("yyyy-MM-dd // HH:mm",timeoffsetInSession);
		List<CronInfo> cronInfoList = new ArrayList<CronInfo>();
		CronInfo cronInfo = null;
		for(Crons crons : cronsList){
			cronInfo = new CronInfo();
			cronInfo.setAvailable(crons.getAvailable().toString());
			cronInfo.setCronid(crons.getCronid().toString());
			Short day = crons.getDay();
			cronInfo.setDay(day==-1?"*":day.toString());
			cronInfo.setFilename(crons.getFilename());
			Short hour = crons.getHour();
			cronInfo.setHour(hour==-1?"*":hour.toString());
			cronInfo.setLastrun(Common.gmdate(simpleDateFormat, crons.getLastrun()));
			cronInfo.setMinute(crons.getMinute().replace("\t", ","));
			String cronsName = crons.getName();
			cronInfo.setName(cronsName!=null?cronsName.replace("\"", "&quot;"):null);
			cronInfo.setNextrun(Common.gmdate(simpleDateFormat, crons.getNextrun()));
			cronInfo.setType(crons.getType());
			byte weekday = crons.getWeekday();
			switch(weekday){
				case -1:cronInfo.setWeekday("*");break;
				case 0:cronInfo.setWeekday(getMessage(request, "a_other_crons_week_day_0"));break;
				case 1:cronInfo.setWeekday(getMessage(request, "a_other_crons_week_day_1"));break;
				case 2:cronInfo.setWeekday(getMessage(request, "a_other_crons_week_day_2"));break;
				case 3:cronInfo.setWeekday(getMessage(request, "a_other_crons_week_day_3"));break;
				case 4:cronInfo.setWeekday(getMessage(request, "a_other_crons_week_day_4"));break;
				case 5:cronInfo.setWeekday(getMessage(request, "a_other_crons_week_day_5"));break;
				case 6:cronInfo.setWeekday(getMessage(request, "a_other_crons_week_day_6"));break;
			}
			cronInfoList.add(cronInfo);
		}
		request.setAttribute("valueObject", cronInfoList);	
		return mapping.findForward("other_crons");
	}
	public ActionForward cronsedit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		try{
			if(submitCheck(request, "editsubmit")){
				StringBuffer selectMinuteTemp = new StringBuffer();
				String selectMinuteResult = null;
				Short cronsId = null;
				String cronsIdString = request.getParameter("cronsId");
				String weekdaynew = request.getParameter("weekdaynew");
				String daynew = request.getParameter("daynew");
				String hournew = request.getParameter("hournew");
				String[] selectMinute = request.getParameterValues("selectMinute");
				String filenamenew = request.getParameter("filenamenew");
				if(cronsIdString==null||cronsIdString.equals("")){
					request.setAttribute("message", getMessage(request, "undefined_action"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}else{
					cronsId = Short.valueOf(cronsIdString);
				}
				for(int i = 0;i<selectMinute.length;i++){
					if(!selectMinute[i].equals("-1")){
						selectMinuteTemp.append(selectMinute[i]+"\t");
					}
				}
				if(selectMinuteTemp.length()>0){
					selectMinuteResult = selectMinuteTemp.substring(0, selectMinuteTemp.length()-1);
				}else{
					selectMinuteResult="";
				}
				if(weekdaynew.equals("-1")&&daynew.equals("-1")&&hournew.equals("-1")&&selectMinuteResult.equals("")){
					request.setAttribute("message", getMessage(request, "a_other_crons_time_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if(!filenamenew.trim().equals("")){
					String filePath=JspRunConfig.realPath+"include/crons/"+filenamenew;
					File file = new File(filePath);
					if(!file.isFile()){
						request.setAttribute("message", getMessage(request, "a_other_crons_filename_invalid",filePath));
						request.setAttribute("return", true);
						return mapping.findForward("message");
					}
				}else{
					request.setAttribute("message", getMessage(request, "a_other_crons_filename_invalid2"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if(selectMinuteResult.equals("")){
					selectMinuteResult="0";
				}
				if((!weekdaynew.equals("-1")||!daynew.equals("-1"))&&hournew.equals("-1")){
					hournew="0";
				}
				if(!weekdaynew.equals("-1")&&!daynew.equals("-1")){
					daynew="-1";
				}
				int nextRun = getNextRun(selectMinuteResult, Short.valueOf(hournew), Short.valueOf(daynew), Byte.valueOf(weekdaynew),ForumInit.settings.get("timeoffset"));
				dataBaseService.execute("UPDATE jrun_crons SET filename='"+Common.addslashes(filenamenew)+"',nextrun="+nextRun+",weekday="+weekdaynew+",day="+daynew+",hour="+hournew+",minute='"+selectMinuteResult+"' WHERE cronid="+cronsId);
				List<Map<String,String>> nextrunInfo = dataBaseService.executeQuery("SELECT nextrun FROM jrun_crons WHERE available>'0' AND nextrun>'0' ORDER BY nextrun LIMIT 1");
				Map<String,String> settingMap = ForumInit.settings;
				settingMap.put("cronnextrun", nextrunInfo!=null&&nextrunInfo.size()>0?nextrunInfo.get(0).get("nextrun"):"0");
				request.setAttribute("message", getMessage(request, "a_other_crons_succeed"));
				request.setAttribute("url_forward", "admincp.jsp?action=crons");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		String cronsIdString = request.getParameter("cronsId");
		Short cronsId = null;
		if(cronsIdString==null||cronsIdString.equals("")){
			request.setAttribute("message", getMessage(request, "undefined_action"));
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}else{
			cronsId = Short.valueOf(cronsIdString);
		}
		Crons crons = cronsService.queryCronsById(cronsId);
		String cName = crons.getName();
		if(cName!=null){
			crons.setName(cName.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;"));
		}
		String re = "\t";
		String minutes = crons.getMinute();
		String[] result = minutes.split(re);
		List<Short> selectedMinute = new ArrayList<Short>();
		if (!minutes.equals("")) {
			for (int i = 0; i < result.length; i++) {
				Short short1 = Short.valueOf(result[i]);
				selectedMinute.add(short1);
			}
		}
		request.setAttribute("selectedMinute", selectedMinute);
		request.setAttribute("cronsFromAction", crons);
		request.setAttribute("selectedMinuteSize", selectedMinute.size());
		return mapping.findForward("other_cronsedit");
	}
	public ActionForward faqlist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "faqsubmit")){
				List<String> deleteIdList = new ArrayList<String>();
				String fIds[] = request.getParameterValues("fid");
				String newTitle = request.getParameter("newtitle");
				if(!FormDataCheck.isZeroOption(fIds)){
					int faqCount = fIds.length;
					String[] deletes = request.getParameterValues("delete");
					String[] titles = request.getParameterValues("title");
					String[] title_h = request.getParameterValues("title_h");
					String[] displayOrders = request.getParameterValues("displayorder");
					String[] displayorder_h = request.getParameterValues("displayorder_h");
					for(int i = 0;i<faqCount;i++){
						String temp = FormDataCheck.getNumberFromFormOfDisplayorder(displayOrders[i]);
						try{
							Short.valueOf(temp);
							displayOrders[i] = temp;
						}catch(Exception exception){
							displayOrders[i] = FinalProperty.ORDER_COLUMN_MAX_VALUE.toString();
						}
					}
					if(deletes!=null){
						StringBuffer delete_SQLBuffer = new StringBuffer("DELETE FROM jrun_faqs WHERE id IN(");
						for(int i = 0;i<deletes.length;i++){
							String deleteId = deletes[i];
							delete_SQLBuffer.append(deleteId+",");
							deleteIdList.add(deleteId);
						}
						int delete_SQLBufferLen = delete_SQLBuffer.length();
						delete_SQLBuffer.replace(delete_SQLBufferLen-1, delete_SQLBufferLen, ")");
						dataBaseService.execute(delete_SQLBuffer.toString());
					}
					for(int i = 0;i<faqCount;i++){
						String tempId = fIds[i];
						String tempTitle = titles[i];
						String tempDisplayOrder = displayOrders[i];
						if(!deleteIdList.contains(tempId)&&
								(!title_h[i].equals(tempTitle)
										||!tempDisplayOrder.equals(displayorder_h[i]))){
							dataBaseService.execute("UPDATE jrun_faqs SET title='"+Common.addslashes(tempTitle)+"', displayorder="+tempDisplayOrder+" WHERE id="+tempId);
						}
					}
				}
				if (!newTitle.equals("")) {
					String newFpId = request.getParameter("newfpid");
					if(!deleteIdList.contains(newFpId)){
						String newDisplayOrder = request.getParameter("newdisplayorder");
						String temp = FormDataCheck.getNumberFromFormOfDisplayorder(newDisplayOrder);
						try {
							Short.valueOf(temp);
							newDisplayOrder = temp;
						} catch (Exception exception) {
							newDisplayOrder = FinalProperty.ORDER_COLUMN_MAX_VALUE.toString();
						}
						dataBaseService.execute("INSERT INTO jrun_faqs (fpid,displayorder,identifier,keyword,title,message) VALUES("+newFpId+", "+newDisplayOrder+", '','','"+Common.addslashes(newTitle)+"','')");
					}
				}
				request.setAttribute("message", getMessage(request, "a_other_faq_update_s"));
				request.setAttribute("url_forward", "admincp.jsp?action=faqlist");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		boolean isfounder = (Boolean)request.getAttribute("isfounder");
		if(!isfounder){
			request.setAttribute("message", getMessage(request, "noaccess_isfounder"));
			return mapping.findForward("message");
		}
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		request.setAttribute("valueObject", otherSetService.getFaqsIndexVO(resources,locale));
		return mapping.findForward("other_faqlist");
	}
	public ActionForward faqdetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "detailsubmit")){
				Short id = Short.valueOf(request.getParameter("id"));
				String title = request.getParameter("newtitle");
				if(!FormDataCheck.isValueString(title)){
					request.setAttribute("message", getMessage(request, "a_other_faq_invalid"));
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				Short fpId = Short.valueOf(request.getParameter("fpid"));
				if(fpId.shortValue()==0){
					dataBaseService.execute("UPDATE jrun_faqs SET title='"+Common.addslashes(title)+"' WHERE id="+id);
				}else{
					fpId = Short.valueOf(request.getParameter("newfpid"));
					String identifier = request.getParameter("newidentifier");
					String keyword = request.getParameter("newkeyword");
					String message = request.getParameter("newmessage");
					if(!FormDataCheck.isValueString(identifier)){
						identifier="";
					}
					if(!FormDataCheck.isValueString(keyword)){
						keyword="";
					}
					if(!FormDataCheck.isValueString(message)){
						message = "";
					}
					dataBaseService.execute("UPDATE jrun_faqs SET fpid="+fpId+", title='"+Common.addslashes(title)+"',identifier='"+Common.addslashes(identifier)+"',keyword='"+Common.addslashes(keyword)+"',message='"+Common.addslashes(message)+"' WHERE id="+id);
				}
				request.setAttribute("message", getMessage(request, "a_other_faq_update_s"));
				request.setAttribute("url_forward", "admincp.jsp?action=faqlist");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		short id = Short.valueOf(request.getParameter("id"));
		Faqs faq = ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryFaqById(id);
		List<Map<String,String>> faqs = dataBaseService.executeQuery("SELECT id, title FROM jrun_faqs WHERE fpid='0' ORDER BY displayorder, fpid");
		if(faq!=null){
			String title = faq.getTitle();
			String keyword= faq.getKeyword();
			String identifier = faq.getIdentifier();
			if(title!=null){
				faq.setTitle(title.replace("\"", "&quot;"));
			}
			if (keyword != null) {
				faq.setKeyword(keyword.replace("\"", "&quot;"));
			}
			if (identifier != null) {
				faq.setIdentifier(identifier.replace("\"", "&quot;"));
			}
		}
		for(Map<String,String> faqIM : faqs){
			String title = faqIM.get("title");
			if(title!=null){
				faqIM.put("title", title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;"));
			}
		}
		request.setAttribute("faqs", faqs);
		request.setAttribute("faq", faq);
		return mapping.findForward("other_faqedit");
	}
	public ActionForward onlinelist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			if(submitCheck(request, "onlinesubmit")){
				String[] existents = request.getParameterValues("existent");
				String ids[] = request.getParameterValues("id");
				String displayorders[] = request.getParameterValues("displayorder");
				String titles[] = request.getParameterValues("title");
				String[] titlehidden = request.getParameterValues("titlehidden"); 
				String[] urls = request.getParameterValues("url");
				Onlinelist olBean = null;
				OnlinelistId onLineIdBean = null;
				List<Onlinelist> addList = new ArrayList<Onlinelist>();
				List<Onlinelist> updateList = new ArrayList<Onlinelist>();
				if(!FormDataCheck.isZeroOption(ids)){
					int len = ids.length;
					StringBuffer delGroupidBuffer = new StringBuffer();
					for(int i = 0;i<len;i++){
						if(existents[i].equals("1")){
							if(urls[i].equals("")&&!ids[i].equals("0")){
								delGroupidBuffer.append(ids[i]+",");
							}else{
								if(!FormDataCheck.isValueString(titles[i])){
									if(ids[i].equals("0")){
										titles[i] = "Member";
									}else{
										titles[i] = titlehidden[i];
									}
								}
								displayorders[i] = FormDataCheck.getNumberFromFormOfDisplayorder(displayorders[i]);
								try{
									Short.valueOf(displayorders[i]);
								}catch(Exception exception){
									displayorders[i] = FinalProperty.ORDER_COLUMN_MAX_VALUE.toString();
								}
								onLineIdBean = new OnlinelistId();
								olBean = new Onlinelist();
								onLineIdBean.setTitle(titles[i]);
								onLineIdBean.setUrl(urls[i]);
								onLineIdBean.setGroupid(Short.valueOf(ids[i]));
								onLineIdBean.setDisplayorder(Short.valueOf(displayorders[i]));
								olBean.setId(onLineIdBean);
								updateList.add(olBean);
							}
						}else if(existents[i].equals("0")&&!urls[i].equals("")){
							if(!FormDataCheck.isValueString(titles[i])){
								titles[i] = titlehidden[i];
							}
							displayorders[i] = FormDataCheck.getNumberFromFormOfDisplayorder(displayorders[i]);
							try{
								Short.valueOf(displayorders[i]);
							}catch(Exception exception){
								displayorders[i] = FinalProperty.ORDER_COLUMN_MAX_VALUE.toString();
							}
							onLineIdBean = new OnlinelistId();
							olBean = new Onlinelist();
							onLineIdBean.setTitle(titles[i]);
							onLineIdBean.setUrl(urls[i]);
							onLineIdBean.setGroupid(Short.valueOf(ids[i]));
							onLineIdBean.setDisplayorder(Short.valueOf(displayorders[i]));
							olBean.setId(onLineIdBean);
							addList.add(olBean);
						}
					}
					String delGroupIds = delGroupidBuffer.length()>0?delGroupidBuffer.substring(0,delGroupidBuffer.length()-1):null;
					olService.updateOnLineList(delGroupIds, updateList, addList);
				}
				String[] updateKeyArray = {"index","forumdisplay"};
				Cache.updateCache(updateKeyArray);
				request.setAttribute("message", getMessage(request, "a_other_online_update_s"));
				request.setAttribute("url_forward", "admincp.jsp?action=onlinelist");
				return mapping.findForward("message");
			}
		}catch (Exception e) {
			request.setAttribute("message",e.getMessage());
			return mapping.findForward("message");
		}
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		List<OnlinelistVO> onlinelistVO_list = olService.queryAllSystemUserGroup(resources,locale);
		request.setAttribute("onlinelistVO_list", onlinelistVO_list);
		return mapping.findForward("other_onlinelist");
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
	private void putValue(String variable,String value,Map<String,String> oldSettings,Map<String,String> settings){
		if(value!=null&&!value.equals(oldSettings.get(variable))){
			settings.put(variable,value);
		}
	}
	private String validateTime(String targetTime,String MaxModelTime,String MinModelTime,SimpleDateFormat dateFormat,HttpServletRequest request){
		targetTime = FormDataCheck.validateDateFormat(targetTime);
		if(targetTime != null){
			if(FormDataCheck.isLess(targetTime, MinModelTime, dateFormat)){
				request.setAttribute("return", true);
				request.setAttribute("message", getMessage(request, "a_other_announcement_btime_invalid1"));
				return null;
			}else if(FormDataCheck.isLess(MaxModelTime, targetTime, dateFormat)){
				request.setAttribute("return", true);
				request.setAttribute("message", getMessage(request, "a_other_announcement_btime_invalid2"));
				return null;
			}
			return targetTime;
		}else{
			request.setAttribute("return", true);
			request.setAttribute("message", getMessage(request, "a_other_announcement_btime_invalid"));
			return null;
		}
	}
	private String validateStartTime(String targetTime, String MaxModelTime,String MinModelTime,SimpleDateFormat dateFormat,HttpServletRequest request){
		targetTime = FormDataCheck.validateDateFormat(targetTime);
		if(targetTime != null){
			if(FormDataCheck.isLess(targetTime, MinModelTime, dateFormat)){
				request.setAttribute("return", true);
				request.setAttribute("message", getMessage(request, "a_other_adv_btime_invalid1"));
				return null;
			}else if(FormDataCheck.isLess(MaxModelTime, targetTime, dateFormat)){
				request.setAttribute("return", true);
				request.setAttribute("message", getMessage(request, "a_other_adv_btime_invalid2"));
				return null;
			}
			return targetTime;
		}else{
			request.setAttribute("return", true);
			request.setAttribute("message", getMessage(request, "a_other_adv_btime_invalid"));
			return null;
		}
	}
	private String validateEndTime(String targetTime,String startTime,String nowTime, String MaxModelTime,String MinModelTime,SimpleDateFormat dateFormat,HttpServletRequest request){
		targetTime = FormDataCheck.validateDateFormat(targetTime);
		if(targetTime != null){
			if(FormDataCheck.isLess(targetTime, MinModelTime, dateFormat)){
				request.setAttribute("return", true);
				request.setAttribute("message", getMessage(request, "a_other_adv_etime_invalid1"));
				return null;
			}else if(FormDataCheck.isLess(MaxModelTime, targetTime, dateFormat)){
				request.setAttribute("return", true);
				request.setAttribute("message", getMessage(request, "a_other_adv_etime_invalid2"));
				return null;
			}else if(FormDataCheck.isLess(targetTime, startTime, dateFormat)){
				request.setAttribute("return", true);
				request.setAttribute("message", getMessage(request, "a_other_adv_etime_invalid3"));
				return null;
			}else if(FormDataCheck.isLess(targetTime, nowTime, dateFormat)){
				request.setAttribute("return", true);
				request.setAttribute("message", getMessage(request, "a_other_adv_etime_invalid4"));
				return null;
			}
			return targetTime;
		}else{
			request.setAttribute("return", true);
			request.setAttribute("message", getMessage(request, "a_other_adv_etime_invalid"));
			return null;
		}
	}
	private AdvIndexVO setAdvIndexVO(List<Advertisements> advertisementsList,Map<String,String> forumMap,String queryTitle,String queryOrderby,String queryTime,String queryType,SimpleDateFormat simpleDateFormat,HttpServletRequest request){
		AdvIndexVO advIndexVO = new AdvIndexVO();
		List<Advertisement> advertismentList = advIndexVO.getAdvertisementList();
		Advertisement advertisement = null;
		int nowTime = Common.time();
		for(Advertisements advertisements : advertisementsList){
			int endTime = advertisements.getEndtime();
			String resultTarStr = null;
			String targetString = advertisements.getTargets();
			if (targetString == null || targetString.equals("") || targetString.equals("forum") || targetString.equals("all")) {
				resultTarStr = getMessage(request, "all");
			} else {
				StringBuffer targetBuffer = new StringBuffer();
				String[] targetArray = targetString.split("\t");
				for (String targetTemp :  targetArray) {
					 if(targetTemp.equals("0")){
						 targetBuffer.append("<a href=\"index.jsp\" target=\"_blank\">").append(getMessage(request, "home")).append("</a>&nbsp;&nbsp;");
					 }else if(targetTemp.equals("register")){
						 targetBuffer.append("<a href=\"register.jsp\" target=\"_blank\">").append(getMessage(request, "a_other_adv_register")).append("</a>&nbsp;&nbsp;");
					 }else if(targetTemp.equals("redirect")){
						 targetBuffer.append(getMessage(request, "a_other_adv_jump"));
					 }else if(targetTemp.equals("archiver")){
						 targetBuffer.append("<a href=\"archiver/\" target=\"_blank\">Archiver</a>&nbsp;&nbsp;");
					 }else {
						 String forumName = forumMap.get(targetTemp);
						 if(forumName!=null){
							 targetBuffer.append("<a href=\"forumdisplay.jsp?fid="+targetTemp+"\" target=\"_blank\">"+forumName+"</a>&nbsp;&nbsp;");
						 }
					 }
				}
				resultTarStr = targetBuffer.toString();
			}
			advertisement = new Advertisement(simpleDateFormat);
			advertisement.setDisplayorder(advertisements.getDisplayorder().toString());
			advertisement.setEndtime(endTime);
			advertisement.setOverdue(endTime!=0&&endTime<=nowTime);
			advertisement.setId(advertisements.getAdvid().toString());
			advertisement.setStarttime(advertisements.getStarttime());
			advertisement.setStyle((String)dataParse.characterParse(advertisements.getParameters(), false).get("style"));
			advertisement.setTargets(resultTarStr);
			String advTitle = advertisements.getTitle();
			advertisement.setTitle(advTitle!=null?advTitle.replace("\"", "&quot;"):null);
			advertisement.setType(advertisements.getType());
			advertisement.setUserable(advertisements.getAvailable()==1);
			advertismentList.add(advertisement);
		}
		advIndexVO.setQueryTitle(queryTitle!=null?queryTitle.replace("\"", "&quot;"):null);
		advIndexVO.setQueryOrderby(queryOrderby);
		advIndexVO.setQueryTime(queryTime);
		advIndexVO.setQueryType(queryType);
		return advIndexVO;
	}
	private int getNextRun(String minute,short hour,short day,byte weekDay,String timeoffset){
		if(minute.equals("")){
			return 0;
		}
		Calendar calendar = Common.getCalendar(timeoffset);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		String[] minuteArray = minute.split("\t");
		int minuteLengh = minuteArray.length;
		int[] minuteIntArray = new int[minuteLengh];
		for(int i = 0;i<minuteLengh;i++){
			minuteIntArray[i] = Integer.parseInt(minuteArray[i]);
		}
		Arrays.sort(minuteIntArray);
		int nowMinute = calendar.get(Calendar.MINUTE);
		int minMinute = 0;
		int maxMinute = 0;
		int nextRunM = 0;
		if(minuteLengh>0){
			minMinute=minuteIntArray[0];
			maxMinute=minuteIntArray[minuteLengh-1];
		}
		if(nowMinute>=maxMinute){
			nextRunM = minMinute;
			if(hour==-1){
				calendar.add(Calendar.HOUR_OF_DAY, 1);
			}
		}else{
			for(int tempMinute : minuteIntArray){
				if(tempMinute>nowMinute){
					nextRunM = tempMinute;
					break;
				}
			}
		}
		calendar.set(Calendar.MINUTE, nextRunM);
		if(hour>-1){
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			if(!calendar.getTime().after(date)&&weekDay == -1&&day == -1){
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}
		}
		if (weekDay > -1) {
			calendar.set(Calendar.DAY_OF_WEEK, weekDay+1);
			if(!calendar.getTime().after(date)){
				calendar.add(Calendar.WEEK_OF_MONTH, 1);
			}
		} else {
			if (day > -1) {
				calendar.set(Calendar.DAY_OF_MONTH, day);
				if(!calendar.getTime().after(date)){
					calendar.add(Calendar.MONTH, 1);
				}
			}
		}
		return (int)(calendar.getTimeInMillis()/1000);
	}
	private Integer dateStrToDateLong(String dateStr,SimpleDateFormat dateFormat) throws ParseException{
		if("0".equals(dateStr)){
			return 0;
		}
		int l = (int)(dateFormat.parse(dateStr).getTime()/1000);
		return l;
	}
}