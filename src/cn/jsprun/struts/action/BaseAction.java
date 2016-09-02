package cn.jsprun.struts.action;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import cn.jsprun.foreg.service.MagicMarketService;
import cn.jsprun.foreg.service.MemberMagicsService;
import cn.jsprun.foreg.service.Member_Magics_Magiclog_MemberMagicsService;
import cn.jsprun.foreg.service.PmsService;
import cn.jsprun.foreg.service.PolloptionsService;
import cn.jsprun.foreg.service.PollsService;
import cn.jsprun.foreg.service.SearchService;
import cn.jsprun.foreg.service.StatvarsService;
import cn.jsprun.foreg.service.TopicAdminActionService;
import cn.jsprun.foreg.service.WapService;
import cn.jsprun.service.AdvSetService;
import cn.jsprun.service.AttachmentsService;
import cn.jsprun.service.AttachtypesService;
import cn.jsprun.service.BbcodesService;
import cn.jsprun.service.CreditsSetService;
import cn.jsprun.service.CreditslogService;
import cn.jsprun.service.CronsSetService;
import cn.jsprun.service.DataBaseService;
import cn.jsprun.service.ForumService;
import cn.jsprun.service.ForumfieldService;
import cn.jsprun.service.ImagetypesService;
import cn.jsprun.service.MagiclogService;
import cn.jsprun.service.MemberService;
import cn.jsprun.service.OnLineSetService;
import cn.jsprun.service.OtherSetService;
import cn.jsprun.service.PostsService;
import cn.jsprun.service.RecyclebinService;
import cn.jsprun.service.SettingService;
import cn.jsprun.service.SmiliesService;
import cn.jsprun.service.SpaceService;
import cn.jsprun.service.StyleService;
import cn.jsprun.service.SystemToolService;
import cn.jsprun.service.TagsService;
import cn.jsprun.service.TemplateService;
import cn.jsprun.service.ThreadsService;
import cn.jsprun.service.ThreadtypeService;
import cn.jsprun.service.ThreadtypesService;
import cn.jsprun.service.TypemodelService;
import cn.jsprun.service.TypeoptionService;
import cn.jsprun.service.TypevarService;
import cn.jsprun.service.UserGroupService;
import cn.jsprun.service.WordsService;
import cn.jsprun.utils.Base64;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.FtpUtils;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Jspruncode;
public class BaseAction extends DispatchAction {
	protected ForumService forumService = (ForumService) BeanFactory.getBean("forumService");
	protected PollsService pollService = (PollsService) BeanFactory.getBean("pollsService");
	protected PolloptionsService optionService = (PolloptionsService) BeanFactory
			.getBean("polloptionsService");
	protected ThreadsService threadService = (ThreadsService) BeanFactory.getBean("threadsService");
	protected DataBaseService dataBaseService = (DataBaseService) BeanFactory.getBean("dataBaseService");
	protected PostsService postService = (PostsService) BeanFactory.getBean("postsService");
	protected MemberService memberService = (MemberService) BeanFactory.getBean("memberService");
	protected ForumfieldService forumfieldService = (ForumfieldService) BeanFactory
			.getBean("forumfieldService");
	protected CronsSetService cronsService = (CronsSetService) BeanFactory.getBean("cronsSetService");
	protected DataParse dataParse = (DataParse) BeanFactory.getBean("dataParse");
	protected FtpUtils ftputil = (FtpUtils) BeanFactory.getBean("ftputils");
	protected OtherSetService otherSetService = (OtherSetService) BeanFactory.getBean("otherSetService");
	protected CreditsSetService creSetSer = (CreditsSetService) BeanFactory.getBean("creditsSetService");
	protected MemberMagicsService memberMagicsService = (MemberMagicsService) BeanFactory
			.getBean("memberMagicsService");
	protected UserGroupService userGroupService = (UserGroupService) BeanFactory.getBean("userGroupService");
	protected Member_Magics_Magiclog_MemberMagicsService MMMMService = (Member_Magics_Magiclog_MemberMagicsService) BeanFactory
			.getBean("member_Magics_Magiclog_MemberMagicsService");
	protected MagiclogService magiclogService = (MagiclogService) BeanFactory.getBean("magiclogServer");
	protected MagicMarketService magicMarketService = (MagicMarketService) BeanFactory
			.getBean("magicMarketService");
	protected PostsService postsService = (PostsService) BeanFactory.getBean("postsService");
	protected ThreadsService threadsService = (ThreadsService) BeanFactory.getBean("threadsService");
	protected StatvarsService statvarsService = (StatvarsService) BeanFactory.getBean("statvarsService");
	protected TopicAdminActionService topicAdminActionService = (TopicAdminActionService) BeanFactory
			.getBean("topicAdminActionService");
	protected SettingService settingService = (SettingService) BeanFactory.getBean("settingService");
	protected WapService wapService = (WapService) BeanFactory.getBean("wapService");
	protected SearchService searchService = (SearchService) BeanFactory.getBean("searchserver");
	protected OnLineSetService olService = (OnLineSetService) BeanFactory.getBean("onLineSetService");
	protected AdvSetService adService = (AdvSetService) BeanFactory.getBean("advSetService");
	protected Jspruncode jspcode = (Jspruncode) BeanFactory.getBean("jspruncode");
	protected ThreadtypeService threadtypeService = (ThreadtypeService) BeanFactory
			.getBean("threadtypeService");
	protected TypemodelService typemodelService = (TypemodelService) BeanFactory.getBean("typemodelService");
	protected TypeoptionService typeoptionService = (TypeoptionService) BeanFactory
			.getBean("typeoptionService");
	protected TypevarService typevarService = (TypevarService) BeanFactory.getBean("typevarService");
	protected StyleService styleService = (StyleService) BeanFactory.getBean("styleService");
	protected TemplateService templateService = (TemplateService) BeanFactory.getBean("templateService");
	protected ImagetypesService imagetypesService = (ImagetypesService) BeanFactory
			.getBean("imagetypesService");
	protected SmiliesService smilieService = (SmiliesService) BeanFactory.getBean("smiliesService");
	protected SystemToolService systemToolServer = (SystemToolService) BeanFactory
			.getBean("systemToolServer");
	protected ThreadtypesService threadTypeServer = (ThreadtypesService) BeanFactory
			.getBean("threadtypesService");
	protected PmsService pmsServer = (PmsService) BeanFactory.getBean("pmsServer");
	protected CreditslogService creditslogServer = (CreditslogService) BeanFactory
			.getBean("creditslogServer");
	protected TagsService tagsService = (TagsService) BeanFactory.getBean("tagService");
	protected SpaceService spaceServer = (SpaceService) BeanFactory.getBean("spaceServer");
	protected TagsService tagService = (TagsService) BeanFactory.getBean("tagService");
	protected AttachmentsService attachmentsService = (AttachmentsService) BeanFactory
			.getBean("attachments_postService");
	protected WordsService wordsService = (WordsService) BeanFactory.getBean("wordsService");
	protected AttachtypesService attachtypesService = (AttachtypesService) BeanFactory
			.getBean("attachtypesService");
	protected BbcodesService bbcodesService = (BbcodesService) BeanFactory.getBean("bbcodesService");
	protected RecyclebinService recyclebinService = (RecyclebinService) BeanFactory
			.getBean("recyclebinService");
	private MessageResources mr = null;
	public String getMessage(HttpServletRequest request, String key, String... args) {
		if (key == null || key.length() == 0) {
			return null;
		}
		if (mr == null) {
			mr = getResources(request);
		}
		Locale locale = getLocale(request);
		String message = null;
		if (args != null && args.length > 0) {
			message = mr.getMessage(locale, key, args);
		} else {
			message = mr.getMessage(locale, key);
		}
		return message;
	}
	public void exportData(HttpServletRequest request, HttpServletResponse response, String fileName,
			String type, Map data) {
		Map<String, String> settings = ForumInit.settings;
		String dateformat = settings.get("dateformat");
		String timeformat = settings.get("gtimeformat");
		String timeoffset = settings.get("timeoffset");
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		String time = Common.gmdate(dateformat + " " + timeformat + " (z)", timestamp, timeoffset);
		String bbname = settings.get("bbname");
		HttpSession session = request.getSession();
		String boardurl = (String) session.getAttribute("boardurl");
		StringBuffer exportContent = new StringBuffer();
		exportContent.append("# JspRun! " + type + "\n");
		exportContent
				.append("# Version: JspRun!_" + JspRunConfig.VERSION + "_" + JspRunConfig.CHARSET + "\n");
		exportContent.append("# Time: " + time + "\n");
		exportContent.append("# From: " + bbname + " (" + boardurl + ")\n");
		exportContent.append("#\n");
		exportContent.append("# This file was BASE64 encoded\n");
		exportContent.append("#\n");
		exportContent.append("# JspRun! Community: http://www.jsprun.net\n");
		exportContent.append("# Please visit our website for latest news about JspRun!\n");
		exportContent.append("# --------------------------------------------------------\n\n\n");
		exportContent.append(Base64.encode(dataParse.combinationChar(data), JspRunConfig.CHARSET, 60));
		response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
		response.setHeader("Last-Modified", Common.gmdate("EEE, d MMM yyyy HH:mm:ss", timestamp, "0")
				+ " GMT");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setHeader("Content-Type", "application/octet-stream");
		try {
			byte[] content = exportContent.toString().getBytes(JspRunConfig.CHARSET);
			response.setHeader("Content-Length", String.valueOf(content.length));
			OutputStream os = response.getOutputStream();
			os.write(content);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected boolean submitCheck(HttpServletRequest request, String var) throws Exception {
		return submitCheck(request, var, false);
	}
	protected boolean submitCheck(HttpServletRequest request, String var, boolean allowGet) throws Exception {
		String varValue = request.getParameter(var);
		String formHashValue = request.getParameter("formHash");
		return submitCheck(request, varValue, formHashValue, allowGet);
	}
	protected boolean submitCheck(HttpServletRequest request, String varValue, String formHashValue,
			boolean allowGet) throws Exception {
		if (Common.empty(varValue)) {
			return false;
		}
		String referer = request.getHeader("Referer");
		if ((allowGet
				|| "POST".equals(request.getMethod()))
				&& Common.formHash(request).equals(formHashValue)
				&& (Common.empty(referer) || referer.replaceAll("https?://([^:/]+).*", "$1").equals(
						request.getHeader("Host").replaceAll("([^:]+).*", "$1")))) {
			return true;
		} else {
			throw new Exception(getMessage(request, "submit_invalid"));
		}
	}
}