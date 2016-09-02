package cn.jsprun.struts.foreg.actions;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import cn.jsprun.api.Tenpayapi;
import cn.jsprun.domain.Creditslog;
import cn.jsprun.domain.CreditslogId;
import cn.jsprun.domain.Memberfields;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Pms;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.domain.Validating;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.struts.form.FileUploadForm;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.IPSeeker;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Md5Token;
import cn.jsprun.vo.GroupsVO;
import cn.jsprun.vo.logs.CreditslogVO;
public class ControlAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward toControlhome(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		String extcreitds = settings.get("extcredits");
		Members member = (Members)session.getAttribute("user");
		Memberfields memberfields = memberService.findMemberfieldsById(uid);
		if(member==null){
			request.setAttribute("resultInfo", getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}
		Map extcreditsMap = dataParse.characterParse(extcreitds, true);
		Map extcreitsvalue = new HashMap();
		extcreitsvalue.put(Integer.valueOf("1"), member.getExtcredits1());
		extcreitsvalue.put(Integer.valueOf("2"), member.getExtcredits2());
		extcreitsvalue.put(Integer.valueOf("3"), member.getExtcredits3());
		extcreitsvalue.put(Integer.valueOf("4"), member.getExtcredits4());
		extcreitsvalue.put(Integer.valueOf("5"), member.getExtcredits5());
		extcreitsvalue.put(Integer.valueOf("6"), member.getExtcredits6());
		extcreitsvalue.put(Integer.valueOf("7"), member.getExtcredits7());
		extcreitsvalue.put(Integer.valueOf("8"), member.getExtcredits8());
		request.setAttribute("extcreditvalue", extcreitsvalue);
		request.setAttribute("extcreditsMap", extcreditsMap);
		request.setAttribute("member", member);
		request.setAttribute("memberfiled", memberfields);
		String pmshql = "FROM Pms WHERE msgtoid="+ uid+ " AND folder='inbox' AND delstatus!='2' ORDER BY dateline DESC";
		List<Pms> pmslist = pmsServer.findPmsByHql(pmshql, 0, 5);
		request.setAttribute("pmsList", pmslist);
		List<Map<String,String>> list = dataBaseService.executeQuery("select c.*,m.username from jrun_creditslog as c left join jrun_members as m on c.uid=m.uid where c.uid="+uid+" limit 5");
		List resultList = null;
		if (list != null && list.size() > 0) {
			resultList = new ArrayList();
			String timeoffset=(String)session.getAttribute("timeoffset");
			String dateformat = (String)session.getAttribute("dateformat");
			String timeformat = (String)session.getAttribute("timeformat");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for (int i = list.size() - 1; i >= 0; i--) {
				CreditslogVO creditslogvo = new CreditslogVO();
				Map<String,String> creditslog = list.get(i);
				creditslogvo.setUsername(creditslog.get("username"));
				creditslogvo.setFromname(creditslog.get("fromto"));
				String datetime = Common.gmdate(sdf_all, Integer.parseInt(creditslog.get("dateline")));
				creditslogvo.setOpertarDate(datetime);
				creditslogvo.setSendNum(Common.toDigit(creditslog.get("send")));
				creditslogvo.setReceiverNum(Common.toDigit(creditslog.get("receive")));
				Map sendMap = (Map) extcreditsMap.get(Integer.valueOf(creditslog.get("sendcredits")));
				Map receiveMap = (Map) extcreditsMap.get(Integer.valueOf(creditslog.get("receivecredits")));
				if (sendMap != null) {
					creditslogvo.setSendCritesNum(Integer.valueOf(creditslog.get("sendcredits")));
					creditslogvo.setSendCrites(sendMap.get("title")==null?"":sendMap.get("title").toString());
					creditslogvo.setSendunit(sendMap.get("unit")==null?"":sendMap.get("unit").toString());
				}
				if (receiveMap != null) {
					creditslogvo.setReceiveCritesNum(Integer.valueOf(creditslog.get("receivecredits")));
					creditslogvo.setReceiveCrites(receiveMap.get("title")==null?"":receiveMap.get("title").toString());
					creditslogvo.setReceiveuint(receiveMap.get("unit")==null?"":receiveMap.get("unit").toString());
				}
				creditslogvo.setOpertar(getMessage(request, creditslog.get("operation")));
				resultList.add(creditslogvo);
			}
		}
		request.setAttribute("resultlist", resultList);
		IPSeeker seeker  = IPSeeker.getInstance();
		Map addressMap = new HashMap();
		MessageResources mr = getResources(request);
		Locale locale = getLocale(request);
		if (!member.getLastip().equals("")) {
			String address = seeker.getAddress(member.getLastip(),mr,locale);
			addressMap.put(member.getLastip(), address);
		}
		if (!member.getRegip().equals("")) {
			String address = seeker.getAddress(member.getRegip(),mr,locale);
			addressMap.put(member.getRegip(), address);
		}
		request.setAttribute("addressMap", addressMap);
		if("2".equals(settings.get("regverify"))&&member.getGroupid()==8){
			Validating validate = memberService.findValidatingById(member.getUid());
			request.setAttribute("validate", validate);
		}
		Common.setExtcredits(request);
		return mapping.findForward("tohome");
	}
	@SuppressWarnings("unused")
	private int convertInt(String s) {
		int count = Common.toDigit(s);
		return count;
	}
	@SuppressWarnings("unchecked")
	public ActionForward toeditInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		if(uid==0){
			request.setAttribute("resultInfo", getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}
		String typeid = request.getParameter("typeid");
		if (typeid == null) {
			typeid = "2";
		}else if("5".equals(typeid)){
			request.setAttribute("timeZoneIDs", Common.getTimeZoneIDs());
		}
		Map<String, String> settings = ForumInit.settings;
		Members member = (Members)session.getAttribute("user");
		Map<String,String> memberfields = dataBaseService.executeQuery("select * from jrun_memberfields where uid="+uid).get(0);
		request.setAttribute("forumStyles", dataParse.characterParse(settings.get("forumStyles"),true));
		request.setAttribute("memberfield", memberfields);
		request.setAttribute("typeid", typeid);
		String bio = memberfields.get("bio");
		if (bio!=null && !bio.equals("")) {
			String bios[] = bio.split("\t");
			if(bios!=null && bios.length>0){
				request.setAttribute("bio", bios[0]);
			}
			if (bios.length > 1) {
				request.setAttribute("shop", bios[1]);
			}
		}
		byte custom = member.getCustomshow();
		String[] cus = countCustom(custom);
		request.setAttribute("custom", cus);
		Map<String, String> faqs = (Map<String, String>) request.getAttribute("faqs");
		request.setAttribute("faqs", dataParse.characterParse(faqs.get("faqs"), false));
		Map seccodedata = dataParse.characterParse(settings.get("seccodedata"), false);
		int minposts = Common.toDigit(String.valueOf(seccodedata.get("minposts")));
		int seccodestatus = Common.range(Common.intval(settings.get("seccodestatus")), 255, 0);
		boolean seccodecheck = (seccodestatus & 16) > 0 && (minposts <= 0 || member.getPosts() < minposts);
		request.setAttribute("seccodecheck", seccodecheck);
		request.setAttribute("seccodedata", seccodedata);
		Common.setExtcredits(request);
		List<Map<String,String>> profilelist = dataBaseService.executeQuery("select title,selective,choices,fieldid,required,unchangeable,description from jrun_profilefields where available=1");
		List<Map<String,String>> requiredfile = new ArrayList<Map<String,String>>();
		List<Map<String,String>> resultfile = new ArrayList<Map<String,String>>();
		if(profilelist.size()>0){
		for(Map<String,String> profile:profilelist){
			if(profile.get("selective").equals("1")){
				StringBuffer result= new StringBuffer("<select name='profile"+profile.get("fieldid")+"'><option value=''>-"+getMessage(request, "select")+"-</option>");
				String choose = profile.get("choices");
				String[] chosechild = choose.split("\n");
				if(chosechild.length>0){
					for(int i=0;i<chosechild.length;i++){
						String [] option = chosechild[i].split("=");
						String selected = "";
						if(option[0].trim().equals(memberfields.get("field_"+profile.get("fieldid")))){
							selected = "selected";
						}
						if(profile.get("unchangeable").equals("1")){
							selected = selected+" disabled ";
						}
						String optionname = "&nbsp;";
						if(option.length==2){
							optionname = option[1];
						}
						result.append("<option value='"+option[0]+"' "+selected+">"+optionname+"</option>");
					}
				}
				result.append("</select>");
				profile.put("select", result.toString());
			}else{
				profile.put("select",memberfields.get("field_"+profile.get("fieldid")));
			}
			if(profile.get("required").equals("1")){
				requiredfile.add(profile);
			}else{
				resultfile.add(profile);
			}
		}
		if(requiredfile.size()==0){
			requiredfile = null;
		}
		if(resultfile.size()==0){
			resultfile = null;
		}
		}else{
			resultfile = null;requiredfile=null;
		}
		profilelist = null;
		String[] userdateformats = settings.get("userdateformat").split("\r\n");
		request.setAttribute("userdateformats", userdateformats);
		request.setAttribute("requiredfile", requiredfile);
		request.setAttribute("profilelist", resultfile);
		return mapping.findForward("touserinfo");
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public ActionForward editInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FileUploadForm fileUploadForm = (FileUploadForm) form;
		HttpSession session = request.getSession();
		boolean isfastsuccess = Common.isshowsuccess(session, "profile_succeed");
		int uid = (Integer) session.getAttribute("jsprun_uid");
		String typeid = request.getParameter("typeid");
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		Map<String, String> usergroups = (Map<String, String>)request.getAttribute("usergroups");
		Members member = (Members)session.getAttribute("user");
		Memberfields memberfields = memberService.findMemberfieldsById(uid);
		String seccodeverify  = request.getParameter("seccodeverify");
		Map seccodedata = dataParse.characterParse(settings.get("seccodedata"), false);
		int minposts = Common.toDigit(String.valueOf(seccodedata.get("minposts")));
		int seccodestatus = Common.range(Common.intval(settings.get("seccodestatus")), 255, 0);
		boolean seccodecheck = (seccodestatus & 16) > 0&& (minposts <= 0 || member.getPosts() < minposts);
		if (seccodecheck) {
			if (!seccodeverify.equalsIgnoreCase((String)request.getSession().getAttribute("rand"))) {
				request.setAttribute("errorInfo", getMessage(request, "submit_seccode_invalid"));
				return mapping.findForward("showMessage");
			}
		}
		try{
			if(submitCheck(request, "editsubmit")){
				if (typeid.equals("1")) {
					Md5Token md5 = Md5Token.getInstance();
					String oldpassword = request.getParameter("oldpassword"); 
					String newpassword = request.getParameter("newpassword"); 
					String newpassword2 = request.getParameter("newpassword2"); 
					String email = request.getParameter("emailnew"); 
					String questionidnew = request.getParameter("questionidnew"); 
					String answernew = request.getParameter("answernew"); 
					answernew = answernew == null ? "" : answernew;
					String errormessage = "";
					String admincp_forcesecques = settings.get("admincp_forcesecques");
					int adminid = member.getAdminid();
					if("1".equals(admincp_forcesecques)){
						if ((adminid == 1 || adminid == 2 || adminid == 3)&&!questionidnew.equals("-1")&&(questionidnew.equals("0") || answernew.equals(""))) {
							request.setAttribute("errorInfo", getMessage(request, "profile_admin_security_invalid"));
							return mapping.findForward("showMessage");
						}
					}
					String oldpass = md5.getLongToken(md5.getLongToken(oldpassword)+member.getSalt());
					if (!oldpass.equals(member.getPassword())) {
						errormessage = getMessage(request, "profile_passwd_wrong");
					} else if (!newpassword.equals(newpassword2)) {
						errormessage = getMessage(request, "profile_passwd_notmatch");
					} else if (!Common.isEmail(email)) {
						errormessage = getMessage(request, "profile_email_illegal");
					} else {
						if(newpassword!=null && !newpassword.equals("")){
							String newpass = md5.getLongToken(md5.getLongToken(newpassword)+member.getSalt());
							member.setPassword(newpass);
						}
						member.setEmail(email);
						if (!questionidnew.equals("-1")) {
							if (questionidnew.equals("0") || answernew.equals("")) {
								member.setSecques("");
							} else {
								String quers = Common.quescrypt(convertInt(questionidnew), answernew);
								member.setSecques(quers);
							}
						}
						memberService.modifyMember(member);
						if(newpassword!=null && !newpassword.equals("")){
							CookieUtil.clearCookies(request, response, settings);
						}
						if(isfastsuccess){
							Common.requestforward(response, "memcp.jsp?action=profile&typeid=1");
							return null;
						}else{
							request.setAttribute("successInfo", getMessage(request, "profile_succeed"));
							request.setAttribute("requestPath","memcp.jsp?action=profile&typeid=1");
							return mapping.findForward("showMessage");
						}
					}
					request.setAttribute("errorInfo", errormessage);
					return mapping.findForward("showMessage");
				} else if (typeid.equals("2")) {
					String nicknamenew = request.getParameter("nicknamenew"); 
					String cstatusnew = request.getParameter("cstatusnew"); 
					String gendernew = request.getParameter("gendernew"); 
					String bdaynew = request.getParameter("bdaynew"); 
					String locationnew = request.getParameter("locationnew"); 
					String sitenew = request.getParameter("sitenew"); 
					String qqnew = request.getParameter("qqnew"); 
					String icqnew = request.getParameter("icqnew"); 
					String yahoonew = request.getParameter("yahoonew"); 
					String msnnew = request.getParameter("msnnew"); 
					String taobaonew = request.getParameter("taobaonew"); 
					String alipaynew = request.getParameter("alipaynew"); 
					bdaynew = bdaynew == null ? "" : bdaynew;
					gendernew = gendernew==null?"0":gendernew;
					nicknamenew = nicknamenew == null ? "" : Common.htmlspecialchars(nicknamenew.trim());
					cstatusnew = cstatusnew == null ? "" : Common.htmlspecialchars(cstatusnew);
					locationnew = locationnew == null ? "" : Common.htmlspecialchars(locationnew);
					sitenew = sitenew == null ? "" : Common.htmlspecialchars(sitenew);
					qqnew = qqnew == null ? "" : qqnew;
					icqnew = icqnew == null ? "" : icqnew;
					yahoonew = yahoonew == null ? "" : Common.htmlspecialchars(yahoonew);
					msnnew = msnnew == null ? "" : Common.htmlspecialchars(msnnew);
					taobaonew = taobaonew == null ? "" : Common.htmlspecialchars(taobaonew);
					alipaynew = alipaynew == null ? "" : Common.htmlspecialchars(alipaynew);
					String censoruser = settings.get("censoruser");
					if(!nicknamenew.equals("") && Common.censoruser(nicknamenew, censoruser)){
						request.setAttribute("errorInfo", getMessage(request, "profile_nickname_cstatus_illegal"));
						return mapping.findForward("showMessage");
					}
					nicknamenew = Common.cutstr(nicknamenew, 30);
					if(!cstatusnew.equals("")&&Common.censoruser(cstatusnew, censoruser)){
						request.setAttribute("errorInfo", getMessage(request, "profile_nickname_cstatus_illegal"));
						return mapping.findForward("showMessage");
					}
					cstatusnew = Common.cutstr(cstatusnew, 30);
					locationnew = Common.cutstr(locationnew, 30);
					if (!"".equals(msnnew) && !Common.isEmail(msnnew)) {
						request.setAttribute("errorInfo", getMessage(request, "profile_alipay_msn"));
						return mapping.findForward("showMessage");
					}
					qqnew = qqnew.matches("^\\d{5,12}$") ? qqnew: "";
					icqnew = icqnew.matches("^\\d{5,12}$") ? icqnew: "";
					alipaynew = alipaynew.matches("^\\d{5,12}$") ? alipaynew: "";
					bdaynew = Common.datecheck(bdaynew) ? Common.dateformat(bdaynew): "0000-00-00";
					member.setBday(bdaynew);
					member.setGender(Byte.valueOf(gendernew));
					memberfields.setNickname(nicknamenew);
					memberfields.setCustomstatus(cstatusnew);
					memberfields.setLocation(locationnew);
					memberfields.setSite(sitenew);
					memberfields.setQq(qqnew);
					memberfields.setIcq(icqnew);
					memberfields.setYahoo(yahoonew);
					memberfields.setMsn(msnnew);
					memberfields.setTaobao(taobaonew);
					memberfields.setAlipay(alipaynew);
					memberService.modifyMember(member);
					memberService.modifyMemberfields(memberfields);
					if(isfastsuccess){
						Common.requestforward(response, "memcp.jsp?action=profile&typeid=2");
						return null;
					}else{
						request.setAttribute("successInfo", getMessage(request, "profile_succeed"));
						request.setAttribute("requestPath","memcp.jsp?action=profile&typeid=2");
						return mapping.findForward("showMessage");
					}
				} else if (typeid.equals("4")) {
					String bionew = request.getParameter("bionew"); 
					String biotradenew = request.getParameter("biotradenew"); 
					String signaturenew = request.getParameter("signaturenew"); 
					String urlavatar = request.getParameter("urlavatar"); 
					String avatarwidthnew = request.getParameter("avatarwidthnew"); 
					String avatarheightnew = request.getParameter("avatarheightnew"); 
					bionew = bionew == null ? "" : Common.htmlspecialchars(bionew.replaceAll("\t", "   "));
					biotradenew = biotradenew == null ? "" : Common.htmlspecialchars(biotradenew.replaceAll("\t", "   "));
					signaturenew = signaturenew == null ? "" : Common.htmlspecialchars(signaturenew);
					FormFile src = fileUploadForm.getCustomavatar();
					String errormessage = "";
					if (src!=null && src.getFileSize()>0) {
						String maxavatarpixel = settings.get("maxavatarpixel"); 
						String maxavatarsize = settings.get("maxavatarsize"); 
						String customavatar = src.getFileName();
						if (!"0".equals(maxavatarsize) && src.getFileSize() > convertInt(maxavatarsize)) {
							errormessage = getMessage(request, "profile_avatar_toobig", maxavatarsize);
						} else {
							try {
								if (customavatar.matches(".*\\.(jpg|jpeg|gif|bmp)$")) {
									String type = customavatar.substring(customavatar.lastIndexOf("."));
									String targetName = "customavatars/" + uid + type;
									String realPath=JspRunConfig.realPath;
									Common.uploadFile(src, realPath+targetName);
									memberfields.setAvatar(targetName);
									Image srcImg = ImageIO.read(new File(realPath+targetName));
									int width = srcImg.getWidth(null);
									int height = srcImg.getHeight(null);
									if (width > convertInt(maxavatarpixel)) {
										memberfields.setAvatarwidth(Short.valueOf(maxavatarpixel));
									} else {
										memberfields.setAvatarwidth(Short.valueOf(width+ ""));
									}
									if (height > convertInt(maxavatarpixel)) {
										memberfields.setAvatarheight(Short.valueOf(maxavatarpixel));
									} else {
										memberfields.setAvatarheight(Short.valueOf(height + ""));
									}
								} else {
									errormessage = getMessage(request, "profile_avatar_invalid");
								}
							} catch (IOException e) {
								e.printStackTrace();
							} 
						}
					} else if (urlavatar != null && !urlavatar.equals("")) {
						if ((!Common.matches(urlavatar, "^(images\\/avatars\\/.+?)$") && !Common.matches(urlavatar, "^(http:\\/\\/.+?)$")) && !Common.matches(urlavatar,"^(customavatars\\/.+?)$")) {
							errormessage = getMessage(request, "profile_avatar_invalid");
						}else if(!Common.in_array(new String[]{"jpg","gif","png"}, fileext(urlavatar).toLowerCase())){
							errormessage = getMessage(request, "profile_avatar_invalid");
						}  else {
							int width = convertInt(avatarwidthnew);
							int heitht = convertInt(avatarheightnew);
							if (width != 0 && heitht != 0) {
								if(width>255){
									width=255;
								}
								if(heitht>255){
									heitht=255;
								}
								memberfields.setAvatarwidth(Short.valueOf(width + ""));
								memberfields.setAvatarheight(Short.valueOf(heitht + ""));
							}
							memberfields.setAvatar(urlavatar);
						}
					}else{
						memberfields.setAvatar("");
						memberfields.setAvatarwidth(Short.valueOf("0"));
						memberfields.setAvatarheight(Short.valueOf("0"));
					}
					String maxbiosize = usergroups.get("maxbiosize");
					if (maxbiosize.equals("0")) {
						maxbiosize = "200";
					}
					if (bionew.length() > convertInt(maxbiosize)) {
						errormessage = getMessage(request, "memcp_profile_bio_toolong", maxbiosize);
					}
					memberfields.setBio(bionew + "\t" + biotradenew);
					String maxsigsize = usergroups.get("maxsigsize");
					if(signaturenew.length()>convertInt(maxsigsize)){
						errormessage = getMessage(request, "memcp_profile_sig_toolong", maxsigsize);
					}
					List<Map<String,String>> wordlist = dataBaseService.executeQuery("select * from jrun_words");
					if(wordlist!=null && wordlist.size()>0){
						for(Map<String,String> word :wordlist){
							if(Common.matches(signaturenew,word.get("find"))){
								if(word.get("replacement").equals("{BANNED}")){
									request.setAttribute("errorInfo", getMessage(request, "word_banned"));
									return mapping.findForward("showMessage");
								}else if(word.get("replacement").equals("{MOD}")){
								}else{
									signaturenew = signaturenew.replaceAll(word.get("find"),word.get("replacement"));
								}
							}
						}
					}
					wordlist=null;
					memberfields.setSightml(signaturenew);
					if (errormessage.equals("")) {
						memberService.modifyMemberfields(memberfields);
						if(isfastsuccess){
							Common.requestforward(response, "memcp.jsp?action=profile&typeid=4");
							return null;
						}else{
							request.setAttribute("successInfo", getMessage(request, "profile_succeed"));
							request.setAttribute("requestPath","memcp.jsp?action=profile&typeid=4");
							return mapping.findForward("showMessage");
						}
					} else {
						request.setAttribute("errorInfo", errormessage);
						return mapping.findForward("showMessage");
					}
				} else if(typeid.equals("5")){
					String styleidnew = request.getParameter("styleidnew"); 
					String tppnew = request.getParameter("tppnew"); 
					String pppnew = request.getParameter("pppnew"); 
					String ssnew = request.getParameter("ssnew"); 
					String sanew = request.getParameter("sanew"); 
					String sinew = request.getParameter("sinew"); 
					String editormodenew = request.getParameter("editormodenew"); 
					String timeoffsetnew = request.getParameter("timeoffsetnew"); 
					String timeformatnew = request.getParameter("timeformatnew"); 
					String dateformatnew = request.getParameter("dateformatnew"); 
					String pmsoundnew = request.getParameter("pmsoundnew"); 
					String invisiblenew = request.getParameter("invisiblenew"); 
					String showemailnew = request.getParameter("showemailnew"); 
					String newsletternew = request.getParameter("newsletternew"); 
					invisiblenew = invisiblenew == null ? "0" : invisiblenew;
					showemailnew = showemailnew == null ? "0" : showemailnew;
					newsletternew = newsletternew == null ? "0" : newsletternew;
					styleidnew = styleidnew==null?"0":styleidnew;
					member.setStyleid(Short.valueOf(styleidnew));
					member.setTpp(Short.valueOf(tppnew));
					member.setPpp(Short.valueOf(pppnew));
					String custom[] = { ssnew, sanew, sinew };
					int customs = countCustomreverse(custom);
					member.setCustomshow(Byte.valueOf(customs + ""));
					member.setEditormode(Byte.valueOf(editormodenew));
					member.setTimeoffset(timeoffsetnew);
					member.setDateformat(Byte.valueOf(dateformatnew));
					member.setTimeformat(Byte.valueOf(timeformatnew));
					member.setPmsound(Byte.valueOf(pmsoundnew));
					member.setInvisible(Byte.valueOf(invisiblenew));
					member.setShowemail(Byte.valueOf(showemailnew));
					member.setNewsletter(Byte.valueOf(newsletternew));
					memberService.modifyMember(member);
					session.setAttribute("styleid", styleidnew);
					session.setAttribute("user", member);
					Common.setDateformat(session, settings);
					if(isfastsuccess){
						Common.requestforward(response, "memcp.jsp?action=profile&typeid=5");
						return null;
					}else{
						request.setAttribute("successInfo", getMessage(request, "profile_succeed"));
						request.setAttribute("requestPath","memcp.jsp?action=profile&typeid=5");
						return mapping.findForward("showMessage");
					}
				}else{
					List<Map<String,String>> profilelist = dataBaseService.executeQuery("select size,fieldid,required,unchangeable from jrun_profilefields where available=1");
					for(Map<String,String> profile:profilelist){
						String pro = request.getParameter("profile"+profile.get("fieldid"));
						if(pro==null&&profile.get("unchangeable").equals("1")){
							continue;
						}else{
							pro= pro== null?"":Common.addslashes(pro);
							int size = Common.intval(profile.get("size"));
							if(pro.length()>size){
								pro = pro.substring(0,size);
							}
							if(profile.get("required").equals("1") && pro.trim().equals("")){
								request.setAttribute("errorInfo", getMessage(request, "profile_required_info_invalid"));
								return mapping.findForward("showMessage");
							}
							dataBaseService.runQuery("update jrun_memberfields set field_"+profile.get("fieldid")+"='"+pro+"' where uid="+uid);
						}
					}
					request.setAttribute("successInfo", getMessage(request, "profile_succeed"));
					request.setAttribute("requestPath","memcp.jsp?action=profile&typeid=4");
					return mapping.findForward("showMessage");
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("resultInfo",getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	private String[] countCustom(byte custom) {
		String customs = custom % 3 + ",";
		int aa = custom / 3;
		customs += aa % 3 + ",";
		aa = aa / 3;
		customs += aa % 3;
		String cus[] = customs.split(",");
		return cus;
	}
	private int countCustomreverse(String[] cust) {
		int result = 0;
		result += convertInt(cust[0])*3*3;
		result += convertInt(cust[1]) * 3;
		result += convertInt(cust[2]);
		return result;
	}
	@SuppressWarnings("unchecked")
	public ActionForward credits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		Map<String,String> usergroups=(Map<String,String>)request.getAttribute("usergroups");
		double creditstax=Double.valueOf(settings.get("creditstax"));
		String taxpercent=Common.number_format(creditstax * 100, "0.00")+"%";
		request.setAttribute("taxpercent", taxpercent);
		int exchangestatus=Integer.valueOf(settings.get("exchangestatus"));
		boolean transferstatus=Integer.valueOf(settings.get("transferstatus"))>0&&Integer.valueOf(usergroups.get("allowtransfer"))>0;
		int ec_ratio=Integer.valueOf(settings.get("ec_ratio"));
		String operation=request.getParameter("operation");
		if(operation==null){
			if(exchangestatus>0){
				operation="exchange";
			}
			else if(transferstatus){
				operation="transfer";
			}
			else if(ec_ratio>0){
				operation="addfunds";
			}
		}
		try{
			if(submitCheck(request, "creditssubmit")){
				Md5Token md5 = Md5Token.getInstance();
				HttpSession session = request.getSession();
				String jsprun_pw = (String) session.getAttribute("jsprun_pw");
				int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
				int timestamp = (Integer)(request.getAttribute("timestamp"));
				String jsprun_user=(String)session.getAttribute("jsprun_userss");
				Members user=(Members)session.getAttribute("user");
				String timeoffset=settings.get("timeoffset");
				String dateformat = (String)session.getAttribute("dateformat");
				String timeformat = (String)session.getAttribute("timeformat");
				Map<String,Map> extcredits = dataParse.characterParse(settings.get("extcredits"), true);
				String creditsformula=settings.get("creditsformula");
				if("transfer".equals(operation)&&transferstatus){
					String password = request.getParameter("password");
					password = password!=null?md5.getLongToken(md5.getLongToken(password)+user.getSalt()):"";
					int amount=Common.toDigit(request.getParameter("amount"));
					int minbalance = Integer.valueOf(settings.get("transfermincredits")); 
					int creditstrans = Integer.valueOf(settings.get("creditstrans"));
					int credit=(Integer)Common.getValues(user, "extcredits"+creditstrans);
					int netamount = (int)Math.floor((amount * (1 - creditstax)));
					if (!password.equals(jsprun_pw)) {
						request.setAttribute("errorInfo", getMessage(request, "credits_password_invalid"));
						return mapping.findForward("showMessage");
					}else if (amount <= 0) {
						request.setAttribute("errorInfo", getMessage(request, "credits_transaction_amount_invalid"));
						return mapping.findForward("showMessage");
					}else if ((credit - amount) < minbalance) {
						request.setAttribute("errorInfo", getMessage(request, "credits_balance_insufficient", minbalance+""));
						return mapping.findForward("showMessage");
					}else if (netamount == 0) {
						request.setAttribute("errorInfo", getMessage(request, "credits_net_amount_iszero"));
						return mapping.findForward("showMessage");
					}
					String to = request.getParameter("to");
					Members member = memberService.findByName(to);
					if (member == null) {
						request.setAttribute("errorInfo", getMessage(request, "credits_transfer_send_nonexistence"));
						return mapping.findForward("showMessage");
					}   else if (member.getUid() == jsprun_uid) {
						request.setAttribute("errorInfo", getMessage(request, "credits_transfer_self"));
						return mapping.findForward("showMessage");
					} 
					dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"-"+amount+",credits="+creditsformula+" WHERE uid="+jsprun_uid, true);
					dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"+"+netamount+",credits="+creditsformula+" WHERE uid="+member.getUid(), true);
					dataBaseService.runQuery("INSERT INTO jrun_creditslog (uid, fromto, sendcredits, receivecredits, send, receive, dateline, operation) VALUES ('"+jsprun_uid+"', '"+Common.addslashes(member.getUsername())+"', '"+creditstrans+"', '"+creditstrans+"', '"+amount+"', '0', '"+timestamp+"', 'TFR'),('"+member.getUid()+"', '"+jsprun_user+"', '"+creditstrans+"', '"+creditstrans+"', '0', '"+netamount+"', '"+timestamp+"', 'RCV')", true);
					String transfermessage = request.getParameter("transfermessage");
					if(transfermessage!=null&&transfermessage.length()>0) {
						transfermessage = Common.addslashes(transfermessage);
						String boardurl=(String)session.getAttribute("boardurl");
						String transfertime = Common.gmdate(dateformat+" "+timeformat, timestamp, timeoffset);
						Map creditstran=extcredits.get(creditstrans);
						Object unit = creditstran.get("unit")==null?"":creditstran.get("unit");
						String message = getMessage(request, "transfer_message",boardurl,jsprun_uid+"",jsprun_user,transfertime, creditstran.get("title")+" "+amount+" "+unit,creditstran.get("title")+" "+netamount+" "+unit,transfermessage);
						Common.sendpm(member.getUid()+"", getMessage(request, "transfer_subject"), message, jsprun_uid+"", jsprun_user, timestamp);
					}
					request.setAttribute("successInfo", getMessage(request, "credits_transaction_succeed"));
					request.setAttribute("requestPath","memcp.jsp?action=creditslog&operation=creditslog");
					return mapping.findForward("showMessage");
				}
				else if("exchange".equals(operation)&&exchangestatus>0){
					String password = request.getParameter("password");
					password = password!=null?md5.getLongToken(md5.getLongToken(password)+user.getSalt()):"";
					int amount=Common.toDigit(request.getParameter("amount"));
					int fromcredits = Integer.valueOf(request.getParameter("fromcredits"));
					int tocredits = Integer.valueOf(request.getParameter("tocredits"));
					int minbalance = Integer.valueOf(settings.get("exchangemincredits")); 
					int credit=(Integer)Common.getValues(user, "extcredits"+fromcredits);
					Map fromcredit=extcredits.get(fromcredits);
					Map tocredit=extcredits.get(tocredits);
					double fromratio=Double.valueOf(fromcredit.get("ratio").toString());
					double toratio=Double.valueOf(tocredit.get("ratio").toString());
					int netamount = (int)Math.floor(amount * fromratio*(1 - creditstax)/toratio);
					if (!password.equals(jsprun_pw)) {
						request.setAttribute("errorInfo", getMessage(request, "credits_password_invalid"));
						return mapping.findForward("showMessage");
					}else if (fromcredits == tocredits) {
						request.setAttribute("errorInfo", getMessage(request, "credits_exchange_invalid"));
						return mapping.findForward("showMessage");
					}else if (amount <= 0) {
						request.setAttribute("errorInfo", getMessage(request, "credits_transaction_amount_invalid"));
						return mapping.findForward("showMessage");
					}else if ((credit - amount) < minbalance) {
						request.setAttribute("errorInfo", getMessage(request, "credits_balance_insufficient", minbalance+""));
						return mapping.findForward("showMessage");
					}else if (netamount == 0) {
						request.setAttribute("errorInfo", getMessage(request, "credits_net_amount_iszero"));
						return mapping.findForward("showMessage");
					}
					String allowexchangeout=(String)fromcredit.get("allowexchangeout");
					String allowexchangein=(String)tocredit.get("allowexchangein");
					if(!"1".equals(allowexchangeout)) {
						request.setAttribute("errorInfo", getMessage(request, "extcredits_disallowexchangeout", fromcredit.get("title").toString()));
						return mapping.findForward("showMessage");
					}else if(!"1".equals(allowexchangein)) {
						request.setAttribute("errorInfo", getMessage(request, "extcredits_disallowexchangein", tocredit.get("title").toString()));
						return mapping.findForward("showMessage");
					}
					dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+fromcredits+"=extcredits"+fromcredits+"-"+amount+", extcredits"+tocredits+"=extcredits"+tocredits+"+"+netamount+",credits="+creditsformula+" WHERE uid="+jsprun_uid, true);
					dataBaseService.runQuery("INSERT INTO jrun_creditslog (uid, fromto, sendcredits, receivecredits, send, receive, dateline, operation) VALUES ('"+jsprun_uid+"', '"+Common.addslashes(jsprun_user)+"', '"+fromcredits+"', '"+tocredits+"', '"+amount+"', '"+netamount+"', '"+timestamp+"', 'EXC')", true);
					request.setAttribute("successInfo", getMessage(request, "credits_transaction_succeed"));
					request.setAttribute("requestPath","memcp.jsp?action=creditslog&operation=creditslog");
					return mapping.findForward("showMessage");
				}
				else if("addfunds".equals(operation)&&ec_ratio>0){
					int ec_mincredits=Integer.valueOf(settings.get("ec_mincredits"));
					int ec_maxcredits=Integer.valueOf(settings.get("ec_maxcredits"));
					int amount=Common.toDigit(request.getParameter("amount"));
					if(amount==0||ec_mincredits>0&&amount<ec_mincredits||ec_maxcredits>0&&amount>ec_maxcredits){
						request.setAttribute("resultInfo", getMessage(request, "credits_addfunds_amount_invalid", ec_maxcredits+"",ec_mincredits+""));
						return mapping.findForward("showMessage");
					}
					int count =Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_orders WHERE uid="+jsprun_uid+" AND submitdate>="+timestamp+"-180 LIMIT 1").get(0).get("count"));
					if(count>0){
						request.setAttribute("resultInfo", getMessage(request, "credits_addfunds_ctrl"));
						return mapping.findForward("showMessage");
					}
					int ec_maxcreditspermonth=Integer.valueOf(settings.get("ec_maxcreditspermonth"));
					if(ec_maxcreditspermonth>0){
						int sum=Common.toDigit(dataBaseService.executeQuery("SELECT SUM(amount) amount FROM jrun_orders WHERE uid="+jsprun_uid+" AND submitdate>="+timestamp+"-2592000 AND status IN (2, 3)").get(0).get("amount"));
						if((sum+amount)>ec_maxcreditspermonth){
							request.setAttribute("resultInfo", getMessage(request, "credits_addfunds_toomuch", ec_maxcreditspermonth+""));
							return mapping.findForward("showMessage");
						}
					}
					float price=(float)Math.ceil((float)amount/(float)ec_ratio*100)/100;
					String orderid=Common.gmdate("yyyyMMddHHmmss", timestamp, timeoffset)+Common.getRandStr(18, false);
					List<Map<String,String>> orders=dataBaseService.executeQuery("SELECT orderid FROM jrun_orders WHERE orderid='"+orderid+"'");
					if(orders!=null&&orders.size()>0){
						request.setAttribute("resultInfo", getMessage(request, "credits_addfunds_order_invalid"));
						return mapping.findForward("showMessage");
					}
					dataBaseService.runQuery("INSERT INTO jrun_orders (orderid, status, uid, amount, price, submitdate) VALUES ('"+orderid+"', '1', '"+jsprun_uid+"', '"+amount+"', '"+price+"', '"+timestamp+"')", true);
					Map creditstrans=extcredits.get(Integer.valueOf(settings.get("creditstrans")));
					String boardurl=(String)session.getAttribute("boardurl");
					String jsprun_userss=(String)session.getAttribute("jsprun_userss");
					String chnid=settings.get("ec_account");
					String key=settings.get("ec_key");
					String bbname=settings.get("bbname");
					String onlineip = Common.get_onlineip(request);
					MessageResources resources = getResources(request);
					Locale locale = getLocale(request);
					String payurl=Tenpayapi.credit_payurl(boardurl, chnid, key, bbname, jsprun_userss, creditstrans, onlineip, ec_ratio,timestamp, price, orderid,resources,locale);
					request.setAttribute("successInfo", getMessage(request, "credits_addfunds_succeed", orderid));
					request.setAttribute("requestPath", payurl);
					return mapping.findForward("showMessage");
				}
			}else if("transfer".equals(operation)&&transferstatus||"exchange".equals(operation)&&exchangestatus>0||"addfunds".equals(operation)&&ec_ratio>0){
				request.setAttribute("creditstrans",  Integer.valueOf(settings.get("creditstrans")));
				Common.setExtcredits(request);
				request.setAttribute("operation", operation);
				return mapping.findForward("tocredits");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	@SuppressWarnings("unchecked")
	public ActionForward creditslog(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		Members member = (Members) session.getAttribute("user");
		String timeoffset= (String)session.getAttribute("timeoffset");
		String dateformat = (String)session.getAttribute("dateformat");
		String timeformat = (String)session.getAttribute("timeformat");
		SimpleDateFormat sf = Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp(): Integer.valueOf(settings.get("topicperpage"));
		int page =Math.max(Common.intval(request.getParameter("page")), 1);
		String operation = request.getParameter("operation");
		Map<Integer,Map> extcredits = dataParse.characterParse(settings.get("extcredits"), true);
		if("paymentlog".equals(operation)){
			int num=Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_paymentlog WHERE uid='"+jsprun_uid+"'").get(0).get("count"));
			Map<String,Integer> multiInfo=Common.getMultiInfo(num, tpp, page);
			page=multiInfo.get("curpage");
			int start_limit=multiInfo.get("start_limit");
			Map<String,Object> multi=Common.multi(num, tpp, page, "memcp.jsp?action=creditslog&operation=" + operation, 0, 10, true, false, null);
			request.setAttribute("multi", multi);
			List<Map<String,String>> loglist = dataBaseService.executeQuery("SELECT p.*, f.fid, f.name, t.subject, t.author, t.dateline AS tdateline FROM jrun_paymentlog p LEFT JOIN jrun_threads t ON t.tid=p.tid LEFT JOIN jrun_forums f ON f.fid=t.fid WHERE p.uid="+jsprun_uid+" ORDER BY p.dateline DESC LIMIT "+start_limit+","+tpp);
			if (loglist != null && loglist.size() > 0) {
				Map creditstrans=extcredits.get(Integer.valueOf(settings.get("creditstrans")));
				String title=creditstrans==null?"":creditstrans.get("title")+" ";
				String unit=" "+(creditstrans==null||creditstrans.get("unit")==null?"":creditstrans.get("unit"));
				for (Map<String,String> log : loglist) {
					int amount=Integer.valueOf(log.get("amount"));
					int netamount=Integer.valueOf(log.get("netamount"));
					if(amount>0||netamount>0){
						log.put("amount",title+amount+unit);
						log.put("netamount", title+netamount+unit);
					}else{
						log.remove("amount");
						log.remove("netamount");
					}
					log.put("dateline", Common.gmdate(sf, Integer.valueOf(log.get("dateline"))));
					log.put("tdateline", Common.gmdate(sf, Integer.valueOf(log.get("tdateline"))));
				}
				request.setAttribute("loglist", loglist);
			}
		}
		else if("incomelog".equals(operation)){
			int num=Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_paymentlog WHERE authorid='"+jsprun_uid+"'").get(0).get("count"));
			Map<String,Integer> multiInfo=Common.getMultiInfo(num, tpp, page);
			page=multiInfo.get("curpage");
			int start_limit=multiInfo.get("start_limit");
			Map<String,Object> multi=Common.multi(num, tpp, page, "memcp.jsp?action=creditslog&operation=" + operation, 0, 10, true, false, null);
			request.setAttribute("multi", multi);
			List<Map<String,String>> loglist = dataBaseService.executeQuery("SELECT p.*, m.username, f.fid, f.name, t.subject, t.dateline AS tdateline FROM jrun_paymentlog p LEFT JOIN jrun_threads t ON t.tid=p.tid LEFT JOIN jrun_forums f ON f.fid=t.fid LEFT JOIN jrun_members m ON m.uid=p.uid WHERE p.authorid='"+jsprun_uid+"' ORDER BY p.dateline DESC LIMIT "+start_limit+","+tpp);
			if (loglist != null && loglist.size() > 0) {
				Map creditstrans=extcredits.get(Integer.valueOf(settings.get("creditstrans")));
				String title=creditstrans==null?"":creditstrans.get("title")+" ";
				String unit=" "+(creditstrans==null||creditstrans.get("unit")==null?"":creditstrans.get("unit"));
				for (Map<String,String> log : loglist) {
					int amount=Integer.valueOf(log.get("amount"));
					int netamount=Integer.valueOf(log.get("netamount"));
					if(amount>0||netamount>0){
						log.put("amount",title+amount+unit);
						log.put("netamount", title+netamount+unit);
					}else{
						log.remove("amount");
						log.remove("netamount");
					}
					log.put("dateline", Common.gmdate(sf, Integer.valueOf(log.get("dateline"))));
					log.put("tdateline", Common.gmdate(sf, Integer.valueOf(log.get("tdateline"))));
				}
				request.setAttribute("loglist", loglist);
			}
		}
		else if("rewardpaylog".equals(operation)){
			int num=Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_rewardlog WHERE authorid='"+jsprun_uid+"'").get(0).get("count"));
			Map<String,Integer> multiInfo=Common.getMultiInfo(num, tpp, page);
			page=multiInfo.get("curpage");
			int start_limit=multiInfo.get("start_limit");
			Map<String,Object> multi=Common.multi(num, tpp, page, "memcp.jsp?action=creditslog&operation=" + operation, 0, 10, true, false, null);
			request.setAttribute("multi", multi);
			List<Map<String,String>> loglist = dataBaseService.executeQuery("SELECT r.*, m.uid, m.username , f.fid, f.name, t.subject, t.price FROM jrun_rewardlog r left JOIN jrun_threads t ON t.tid=r.tid left JOIN jrun_forums f ON f.fid=t.fid left JOIN jrun_members m ON m.uid=r.answererid WHERE r.authorid='"+jsprun_uid+"' ORDER BY r.dateline DESC LIMIT "+start_limit+","+tpp);
			if (loglist != null && loglist.size() > 0) {
				Map creditstrans=extcredits.get(Integer.valueOf(settings.get("creditstrans")));
				String title=creditstrans==null?"":creditstrans.get("title")+" ";
				String unit=" "+(creditstrans==null||creditstrans.get("unit")==null?"":creditstrans.get("unit"));
				for (Map<String,String> log : loglist) {
					log.put("dateline", Common.gmdate(sf, Integer.valueOf(log.get("dateline"))));
					log.put("price",title+Math.abs(Integer.valueOf(log.get("price")))+unit);
					log.put("netamount",title+log.get("netamount")+unit);
				}
				request.setAttribute("loglist", loglist);
			}
		}
		else if("rewardincomelog".equals(operation)){
			int num=Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_rewardlog WHERE answererid='"+jsprun_uid+"'").get(0).get("count"));
			Map<String,Integer> multiInfo=Common.getMultiInfo(num, tpp, page);
			page=multiInfo.get("curpage");
			int start_limit=multiInfo.get("start_limit");
			Map<String,Object> multi=Common.multi(num, tpp, page, "memcp.jsp?action=creditslog&operation=" + operation, 0, 10, true, false, null);
			request.setAttribute("multi", multi);
			List<Map<String,String>> loglist = dataBaseService.executeQuery("SELECT r.*, m.uid, m.username, f.fid, f.name, t.subject, t.price FROM jrun_rewardlog r LEFT JOIN jrun_threads t ON t.tid=r.tid LEFT JOIN jrun_forums f ON f.fid=t.fid LEFT JOIN jrun_members m ON m.uid=r.authorid WHERE r.answererid='"+jsprun_uid+"' and r.authorid>0 ORDER BY r.dateline DESC LIMIT "+start_limit+","+tpp);
			if (loglist != null && loglist.size() > 0) {
				Map creditstrans=extcredits.get(Integer.valueOf(settings.get("creditstrans")));
				String title=creditstrans==null?"":creditstrans.get("title")+" ";
				String unit=" "+(creditstrans==null||creditstrans.get("unit")==null?"":creditstrans.get("unit"));
				for (Map<String,String> log : loglist) {
					log.put("dateline", Common.gmdate(sf, Integer.valueOf(log.get("dateline"))));
					log.put("price",title+Math.abs(Integer.valueOf(log.get("price")))+unit);
				}
				request.setAttribute("loglist", loglist);
			}
		}
		else{
			operation = "creditslog";
			int num=Integer.valueOf(dataBaseService.executeQuery("SELECT COUNT(*) count FROM jrun_creditslog WHERE uid='"+jsprun_uid+"'").get(0).get("count"));
			Map<String,Integer> multiInfo=Common.getMultiInfo(num, tpp, page);
			page=multiInfo.get("curpage");
			int start_limit=multiInfo.get("start_limit");
			Map<String,Object> multi=Common.multi(num, tpp, page, "memcp.jsp?action=creditslog&operation=" + operation, 0, 10, true, false, null);
			request.setAttribute("multi", multi);
			List<Map<String,String>> loglist = dataBaseService.executeQuery("SELECT * FROM jrun_creditslog WHERE uid="+jsprun_uid+" ORDER BY dateline DESC LIMIT "+start_limit+","+tpp);
			if (loglist != null && loglist.size() > 0) {
				for (Map<String, String> log : loglist) {
					int sendcredits=Integer.valueOf(log.get("sendcredits"));
					int receivecredits=Integer.valueOf(log.get("receivecredits"));
					int send=Integer.valueOf(log.get("send"));
					int receive=Integer.valueOf(log.get("receive"));
					if(send>0){
						Map sendcredit=extcredits.get(sendcredits);
						String title = sendcredit==null?"":sendcredit.get("title")+" ";
						String unit=" "+(sendcredit==null||sendcredit.get("unit")==null?"":sendcredit.get("unit"));
						log.put("send", title+send+" "+unit);
					}else{
						log.remove("send");
					}
					if(receive>0){
						Map receivecredit=extcredits.get(receivecredits);
						String title = receivecredit==null?"":receivecredit.get("title")+" ";
						String unit=" "+(receivecredit==null||receivecredit.get("unit")==null?"":receivecredit.get("unit"));
						log.put("receive", title+receive+" "+unit);
					}else{
						log.remove("receive");
					}
					log.remove("sendcredits");
					log.remove("receivecredits");
					log.put("fromtoenc", Common.encode(log.get("fromto")));
					log.put("dateline", Common.gmdate(sf, Integer.valueOf(log.get("dateline"))));
					log.put("operation", getMessage(request, log.get("operation")));
				}
				request.setAttribute("loglist", loglist);
			}
		}
		request.setAttribute("operation", operation);
		Common.setExtcredits(request);
		return mapping.findForward("tologs");
	}
	@SuppressWarnings("unchecked")
	public ActionForward tousergroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		Memberfields memberfield = memberService.findMemberfieldsById(uid);
		Members member = (Members)session.getAttribute("user");
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		Map<String, String> usergroups = (Map<String, String>) request.getAttribute("usergroups");
		if (usergroups==null || usergroups.get("allowmultigroups").equals("0")) {
			request.setAttribute("show_message",getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		String extgroup = member.getExtgroupids();
		String[] extgroupids = null;
		if (!extgroup.equals("")) {
			extgroupids = extgroup.split("\t");
		}
		StringBuffer extids = new StringBuffer("0");
		if (extgroupids != null) {
			for (int i = 0; i < extgroupids.length; i++) {
				if(!extgroupids[i].equals("")){
					extids.append(","+extgroupids[i]);
				}
			}
		}
		String extcreitds = settings.get("extcredits");
		Map extcreditsMap = dataParse.characterParse(extcreitds,true);
		int creditstrans = Integer.valueOf(settings.get("creditstrans"));
		String creditname = ""; 
		String creditunit ="";
		if(creditstrans>0&&!extcreditsMap.isEmpty())
		{
			Map extcreMap = (Map) extcreditsMap.get(creditstrans);
			creditname = extcreMap.get("title")==null?"":extcreMap.get("title").toString(); 
			creditunit = extcreMap.get("unit")==null?"":extcreMap.get("unit").toString(); 
		}
		String groupterms = memberfield.getGroupterms();
		Map groupterMap = dataParse.characterParse(groupterms, false);
		Map mainMap = null;
		Map extMap = null;
		if (groupterMap != null) {
			mainMap = (Map) groupterMap.get("main");
			extMap = (Map) groupterMap.get("ext");
		}
		String hql = "FROM Usergroups as u WHERE (u.type='special' AND u.system<>'private' AND u.radminid='0') OR (u.type='member' AND " + member.getCredits() + ">=u.creditshigher AND " + member.getCredits() + "<u.creditslower) OR u.groupid IN ( " + extids + " ) or u.groupid=" + member.getGroupid() + " ORDER BY u.type, u.system";
		List<Usergroups> grouplist = userGroupService.findUsergropsByHql(hql);
		List resultlist = new ArrayList();
		if (grouplist != null) {
			for (Usergroups group : grouplist) {
				GroupsVO groupsvo = new GroupsVO();
				groupsvo.setGrouptitle(group.getGrouptitle());
				boolean ispublic = false;
				if (group.getSystem().equals("private")) {
					groupsvo.setDays("0 "+getMessage(request, "ipban_days"));
					groupsvo.setPrice(creditname + " 0 " + creditunit);
				} else {
					ispublic = true;
					String[] system = group.getSystem().split("\t");
					groupsvo.setDays(system[1] + " "+getMessage(request, "ipban_days"));
					groupsvo.setPrice(creditname +" "+ system[0] +" "+ creditunit);
				}
				boolean switchmaingroup = ispublic || group.getType().equals("member") ? true : false;
				boolean maindisabled = switchmaingroup && ((!group.getSystem().equals("private") && (group.getSystem().equals("0\t0") || group.getGroupid() == member.getGroupid() || Common.in_array(member.getExtgroupids().split("\t"), group.getGroupid()))) || group.getType().equals("member")) ? false : true;
				groupsvo.setIsaddgroup(maindisabled);
				if (group.getType().equals("special") || (group.getType().equals("member"))) {
					groupsvo.setIsspacal(true);
				} 
				if (group.getGroupid().intValue()==member.getGroupid()) {
					groupsvo.setIsmaingroup(true);
				} 
				if(!group.getType().equals("member")&&group.getGroupid().intValue()!=member.getGroupid()){
					groupsvo.setIscurrgroup(true);
				}
				if (mainMap != null && mainMap.get("time") != null && group.getGroupid().equals(member.getGroupid())) {
					groupsvo.setDateline((Integer) mainMap.get("time"));
				} else if (extMap != null && extMap.get(group.getGroupid() + "") != null) {
					groupsvo.setDateline((Integer) extMap.get(group .getGroupid() + ""));
				} else {
					groupsvo.setDateline(0);
				}
				if (extgroupids != null) {
					if(Common.in_array(extgroupids, group.getGroupid()+"")){
						groupsvo.setIsextgroup(true);
					}
				}
				groupsvo.setGroupid(group.getGroupid());
				resultlist.add(groupsvo);
			}
		}
		request.setAttribute("grouplist", resultlist);
		boolean isadmin = false;
		if (member.getGroupid() == 1 || member.getGroupid() == 2 || member.getGroupid() == 3) {
			isadmin = true;
		}
		request.setAttribute("inadmin", isadmin);
		Common.setExtcredits(request);
		return mapping.findForward("tousergroup");
	}
	@SuppressWarnings("unchecked")
	public ActionForward editUsergroupbytype(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		Memberfields memberfield = memberService.findMemberfieldsById(uid);
		Members member = (Members)session.getAttribute("user");
		String type = request.getParameter("type");
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		try{
			if(submitCheck(request, "groupsubmit",true)){
				if (type.equals("main")) {
					String groupid = request.getParameter("groupidnew");
					if (groupid == null) {
						request.setAttribute("errorInfo", getMessage(request, "usergroups_nonexistence"));
						return mapping.findForward("showMessage");
					} else {
						member.setGroupid(Short.valueOf(groupid));
						if(!Common.in_array(member.getExtgroupids().split("\t"), groupid)){
							member.setExtgroupids(member.getExtgroupids() + "\t" + groupid);
						}
						memberService.modifyMember(member);
						if(Common.isshowsuccess(session, "usergroups_update_succeed_front")){
							Common.requestforward(response, "memcp.jsp?action=usergroups");
							return null;
						}else{
							request.setAttribute("successInfo", getMessage(request, "usergroups_update_succeed_front"));
							request.setAttribute("requestPath","memcp.jsp?action=usergroups");
							return mapping.findForward("showMessage");
						}
					}
				} else if (type.equals("toextexit")) {
					String edit = request.getParameter("edit");
					String submit = request.getParameter("submit");
					if (submit != null) {
						Map extgroupMap = dataParse.characterParse(memberfield.getGroupterms(),false);
						if(extgroupMap!=null){
							Map extMap = (Map)extgroupMap.get("ext");
							List list = new ArrayList();
							if(extMap!=null){
								Iterator it = extMap.keySet().iterator();
								while(it.hasNext()){
									Object key = it.next();
									if(key.equals(edit)){
										list.add(key);
									}
								}
							}
							if(list.size()>0){
								for(int i=0;i<list.size();i++){
									extMap.remove(list.get(i));
								}
							}
							extgroupMap.remove("ext");
							extgroupMap.put("ext", extMap);
							String groupterms = dataParse.combinationChar(extgroupMap);
							memberfield.setGroupterms(groupterms);
							memberService.modifyMemberfields(memberfield);
						}
						String extgroup = member.getExtgroupids();
						String[] extgroupids = null;
						if (!extgroup.equals("")) {
							extgroupids = extgroup.split("\t");
						}
						if(extgroupids!=null){
							StringBuffer extids = new StringBuffer();
							for(int i=0;i<extgroupids.length;i++){
								if(!extgroupids[i].equals(edit)){
									extids.append(extgroupids[i]+"\t");
								}
							}
							member.setExtgroupids(extids.toString().trim());
							memberService.modifyMember(member);
						}
						if(Common.isshowsuccess(session, "usergroups_exit_succeed")){
							Common.requestforward(response, "memcp.jsp?action=usergroups");
							return null;
						}else{
							request.setAttribute("successInfo", getMessage(request, "usergroups_exit_succeed"));
							request.setAttribute("requestPath","memcp.jsp?action=usergroups");
							return mapping.findForward("showMessage");
						}
					} else {
						Usergroups usergroup = userGroupService.findUserGroupById(Short.valueOf(edit));
						request.setAttribute("grouptitle", usergroup.getGrouptitle());
						request.setAttribute("membername", member.getUsername());
						request.setAttribute("type", "toextexit");
						return mapping.findForward("tousergroup");
					}
				} else if (type.equals("toextadd")) {
					int timestamp = (Integer)(request.getAttribute("timestamp"));
					String edit = request.getParameter("edit");
					Usergroups usergroup = userGroupService.findUserGroupById(Short.valueOf(edit));
					String submit = request.getParameter("submit");
					if (submit != null) {
						int days = convertInt(request.getParameter("days"));
						String[] system = usergroup.getSystem().split("\t");
						if(system.length<2){
							request.setAttribute("errorInfo",getMessage(request, "undefined_action_return"));
							return mapping.findForward("showMessage");
						}
						if(days<convertInt(system[1])){
							request.setAttribute("errorInfo", getMessage(request, "usergroups_span_invalid", system[1]));
							return mapping.findForward("showMessage");
						}
						String transfermincredits = settings.get("transfermincredits"); 
						String creditstrans = settings.get("creditstrans");
						if(creditstrans.equals("0")){
							request.setAttribute("errorInfo", getMessage(request, "magics_credits_no_open"));
							return mapping.findForward("showMessage");
						}
						int credits = convertInt(system[0])*days;
						String filename = "extcredits"+creditstrans;
						int membervalue = (Integer)Common.getValues(member,filename);
						if(membervalue-credits<=convertInt(transfermincredits)){
							request.setAttribute("errorInfo", getMessage(request, "credits_balance_insufficient", transfermincredits));
							return mapping.findForward("showMessage");
						}
						Map extgroupMap = dataParse.characterParse(memberfield.getGroupterms(),false);
						if(extgroupMap!=null){
							Map extMap = (Map)extgroupMap.get("ext");
							if(extMap!=null){
								extgroupMap.remove("ext");
								extMap.put(edit,timestamp+days*86400);
							}else{
								extMap = new HashMap();
								extMap.put(edit, timestamp+days*86400);
							}
							extgroupMap.put("ext", extMap);
							String groupterms = dataParse.combinationChar(extgroupMap);
							memberfield.setGroupterms(groupterms);
							memberService.modifyMemberfields(memberfield);
						}else{
							extgroupMap = new HashMap();
							Map extMap = new HashMap();
							extMap.put(edit, timestamp+days*86400);
							extgroupMap.put("ext", extMap);
							String groupterms = dataParse.combinationChar(extgroupMap);
							memberfield.setGroupterms(groupterms);
							memberService.modifyMemberfields(memberfield);
						}
						String extgroup = member.getExtgroupids();
						String extids = extgroup+"\t"+edit;
						extids = extids.trim();
						member.setExtgroupids(extids);
						member = (Members)Common.setValues(member,filename,String.valueOf(membervalue-credits));
						memberService.modifyMember(member);
						Creditslog clog = new Creditslog();
						CreditslogId clogid = new CreditslogId();
						clogid.setDateline(timestamp);
						clogid.setUid(uid);
						clogid.setFromto(member.getUsername());
						clogid.setSendcredits(Byte.valueOf(creditstrans));
						clogid.setReceivecredits(Byte.valueOf("0"));
						clogid.setSend(credits);
						clogid.setReceive(0);
						clogid.setOperation("UGP");
						clog.setId(clogid);
						creditslogServer.insertCreditslog(clog);
						if(Common.isshowsuccess(session, "usergroups_join_succeed")){
							Common.requestforward(response, "memcp.jsp?action=usergroups");
							return null;
						}
						request.setAttribute("successInfo", getMessage(request, "usergroups_join_succeed"));
						request.setAttribute("requestPath","memcp.jsp?action=usergroups");
						return mapping.findForward("showMessage");
					} else {
						String extcreitds = settings.get("extcredits");
						Map extcreditsMap = dataParse.characterParse(extcreitds, true);
						String creditstrans = settings.get("creditstrans");
						if(creditstrans.equals("0")){
							request.setAttribute("errorInfo", getMessage(request, "magics_credits_no_open"));
							return mapping.findForward("showMessage");
						}
						String[] system = usergroup.getSystem().split("\t");
						if(system.length<2){
							request.setAttribute("errorInfo",getMessage(request, "undefined_action_return"));
							return mapping.findForward("showMessage");
						}
						Map extcreMap = (Map) extcreditsMap.get(convertInt(creditstrans));
						String creditname = (String) extcreMap.get("title"); 
						String creditunit = (String) extcreMap.get("unit"); 
						creditunit = creditunit==null?"":creditunit;
						request.setAttribute("price", creditname + system[0]+ creditunit);
						request.setAttribute("grouptitle", usergroup.getGrouptitle());
						request.setAttribute("membername", member.getUsername());
						request.setAttribute("days", system[1]);
						request.setAttribute("pricenum", creditname+ convertInt(system[0]) * convertInt(system[1])+ creditunit);
						request.setAttribute("type", "toextadd");
						return mapping.findForward("tousergroup");
					}
				}
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("resultInfo",getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	private String fileext(String filename) {
		return filename.substring(filename.lastIndexOf(".")+1,filename.length()).trim();
	}
}