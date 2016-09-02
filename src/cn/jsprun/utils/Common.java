package cn.jsprun.utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.struts.Globals;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.foreg.service.PostOperating;
import cn.jsprun.service.DataBaseService;
import cn.jsprun.service.MemberService;
public final class Common {
	public final static String SIGNSTRING = "\t3938-3187-414023-22164\t";
	public static final String[] THREAD_COLORS={"", "red", "orange", "yellow", "green", "cyan", "blue", "purple", "gray", "white"};
	public static final String[] COLOR_OPTIONS = { "Black", "Sienna", "DarkOliveGreen","DarkGreen", "DarkSlateBlue", "Navy", "Indigo","DarkSlateGray", "DarkRed", "DarkOrange", "Olive", "Green","Teal", "Blue", "SlateGray", "DimGray", "Red", "SandyBrown","YellowGreen", "SeaGreen", "MediumTurquoise", "RoyalBlue","Purple", "Gray", "Magenta", "Orange", "Yellow", "Lime","Cyan", "DeepSkyBlue", "DarkOrchid", "Silver", "Pink", "Wheat","LemonChiffon", "PaleGreen", "PaleTurquoise", "LightBlue","Plum", "White" };
	private static final char[] PREG_CHARS={'.', '\\' ,'+' ,'*', '?', '[', '^' ,']' ,'$', '(' ,')', '{', '}', '=' ,'!', '<','>', '|',':'};
	private static final String RAND_CHARS = "0123456789abcdefghigklmnopqrstuvtxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
	private static final String CHARSET_NAME="GBK";
	private static Random random = new Random();
	private static DataBaseService dataBaseService=(DataBaseService) BeanFactory.getBean("dataBaseService");
	private static MemberService memberService =((MemberService) BeanFactory.getBean("memberService"));
	private static PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
	private static DataParse dataParse=((DataParse)BeanFactory.getBean("dataParse"));
	private static FtpUtils ftputil=((FtpUtils)BeanFactory.getBean("ftputils"));
	private static final Map<String,String[]> timeZoneIDs=new LinkedHashMap<String,String[]>(32);
	static{
		timeZoneIDs.put("-12", new String[]{"GMT-12:00","(GMT -12:00) Eniwetok, Kwajalein"});
		timeZoneIDs.put("-11", new String[]{"GMT-11:00","(GMT -11:00) Midway Island, Samoa"});
		timeZoneIDs.put("-10", new String[]{"GMT-10:00","(GMT -10:00) Hawaii"});
		timeZoneIDs.put("-9", new String[]{"GMT-09:00","(GMT -09:00) Alaska"});
		timeZoneIDs.put("-8", new String[]{"GMT-08:00","(GMT -08:00) Pacific Time (US &amp; Canada), Tijuana"});
		timeZoneIDs.put("-7", new String[]{"GMT-07:00","(GMT -07:00) Mountain Time (US &amp; Canada), Arizona"});
		timeZoneIDs.put("-6", new String[]{"GMT-06:00","(GMT -06:00) Central Time (US &amp; Canada), Mexico City"});
		timeZoneIDs.put("-5", new String[]{"GMT-05:00","(GMT -05:00) Eastern Time (US &amp; Canada), Bogota, Lima, Quito"});
		timeZoneIDs.put("-4", new String[]{"GMT-04:00","(GMT -04:00) Atlantic Time (Canada), Caracas, La Paz"});
		timeZoneIDs.put("-3.5", new String[]{"GMT-03:30","(GMT -03:30) Newfoundland"});
		timeZoneIDs.put("-3", new String[]{"GMT-03:00","(GMT -03:00) Brassila, Buenos Aires, Georgetown, Falkland Is"});
		timeZoneIDs.put("-2", new String[]{"GMT-02:00","(GMT -02:00) Mid-Atlantic, Ascension Is., St. Helena"});
		timeZoneIDs.put("-1", new String[]{"GMT-01:00","(GMT -01:00) Azores, Cape Verde Islands"});
		timeZoneIDs.put("0", new String[]{"GMT","(GMT) Casablanca, Dublin, Edinburgh, London, Lisbon, Monrovia"});
		timeZoneIDs.put("1", new String[]{"GMT+01:00","(GMT +01:00) Amsterdam, Berlin, Brussels, Madrid, Paris, Rome"});
		timeZoneIDs.put("2", new String[]{"GMT+02:00","(GMT +02:00) Cairo, Helsinki, Kaliningrad, South Africa"});
		timeZoneIDs.put("3", new String[]{"GMT+03:00","(GMT +03:00) Baghdad, Riyadh, Moscow, Nairobi"});
		timeZoneIDs.put("3.5", new String[]{"GMT+03:30","(GMT +03:30) Tehran"});
		timeZoneIDs.put("4", new String[]{"GMT+04:00","(GMT +04:00) Abu Dhabi, Baku, Muscat, Tbilisi"});
		timeZoneIDs.put("4.5", new String[]{"GMT+04:30","(GMT +04:30) Kabul"});
		timeZoneIDs.put("5", new String[]{"GMT+05:00","(GMT +05:00) Ekaterinburg, Islamabad, Karachi, Tashkent"});
		timeZoneIDs.put("5.5", new String[]{"GMT+05:30","(GMT +05:30) Bombay, Calcutta, Madras, New Delhi"});
		timeZoneIDs.put("5.75", new String[]{"GMT+05:45","(GMT +05:45) Katmandu"});
		timeZoneIDs.put("6", new String[]{"GMT+06:00","(GMT +06:00) Almaty, Colombo, Dhaka, Novosibirsk"});
		timeZoneIDs.put("6.5", new String[]{"GMT+06:30","(GMT +06:30) Rangoon"});
		timeZoneIDs.put("7", new String[]{"GMT+07:00","(GMT +07:00) Bangkok, Hanoi, Jakarta"});
		timeZoneIDs.put("8", new String[]{"GMT+08:00","(GMT +08:00) Beijing, Hong Kong, Perth, Singapore, Taipei"});
		timeZoneIDs.put("9", new String[]{"GMT+09:00","(GMT +09:00) Osaka, Sapporo, Seoul, Tokyo, Yakutsk"});
		timeZoneIDs.put("9.5", new String[]{"GMT+09:30","(GMT +09:30) Adelaide, Darwin"});
		timeZoneIDs.put("10", new String[]{"GMT+10:00","(GMT +10:00) Canberra, Guam, Melbourne, Sydney, Vladivostok"});
		timeZoneIDs.put("11", new String[]{"GMT+11:00","(GMT +11:00) Magadan, New Caledonia, Solomon Islands"});
		timeZoneIDs.put("12", new String[]{"GMT+12:00","(GMT +12:00) Auckland, Wellington, Fiji, Marshall Island"});
	}
	public static int rand(int max){
		return random.nextInt(max+1);
	}
	public static int rand(int min, int max){
		if(min<max){
			if(min>0){
				return rand(max-min)+min;
			}else{
				return rand(max);
			}
		}else{
			return min;
		}
	}
	public static String getRandStr(int length, boolean isOnlyNum) {
		int size=isOnlyNum?10:62;
		StringBuffer hash = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			hash.append(RAND_CHARS.charAt(random.nextInt(size)));
		}
		return hash.toString();
	}
	@SuppressWarnings("unchecked")
	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && (obj.equals("") || obj.equals("0"))) {
			return true;
		} else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)) {
			return true;
		} else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		}
		return false;
	}
	private static List<String> periodscheck(String banperiods,byte disableperiodctrl,String timeoffset){
		if(disableperiodctrl==0&&banperiods!=null&&!banperiods.equals("")){
			float now=Float.valueOf(gmdate("HH.mm", Common.time(), timeoffset));
			String[] periods=banperiods.split("\r\n");
			for (String period : periods) {
				period=period.trim().replaceAll(":", ".");
				String[] periodTime=period.split("-");
				if(periodTime!=null&&periodTime.length>=2)
				{
					float periodbegin=Float.valueOf(periodTime[0]);
					float periodend=Float.valueOf(periodTime[1]);
					if((periodbegin > periodend && (now >= periodbegin || now < periodend)) || (periodbegin < periodend && now >= periodbegin && now < periodend)) {
						List<String> rList = new ArrayList<String>(2);
						rList.add("period_nopermission");
						rList.add(banperiods.replaceAll("\r\n", ","));
						return rList;
					}
				}
			}
		}
		return null;
	}
	public static String periodscheck(String banperiods,byte disableperiodctrl,int timestamp,String timeoffset,MessageResources mr,Locale locale) {
		if(disableperiodctrl==0&&banperiods.length()>0) {
			float now=Float.valueOf(Common.gmdate("HH.mm", timestamp, timeoffset));
			String[] periods=banperiods.split("\r\n");
			for (String period : periods) {
				period=period.trim().replaceAll(":", ".");
				String[] periodTime=period.split("-");
				if(periodTime.length>1){
					float periodbegin=Float.valueOf(periodTime[0]);
					float periodend=Float.valueOf(periodTime[1]);
					if((periodbegin > periodend && (now >= periodbegin || now < periodend)) || (periodbegin < periodend && now >= periodbegin && now < periodend)) {
						return mr.getMessage(locale, "period_nopermission",banperiods.replaceAll("\r\n", ","));
					}
				}
			}
		}
		return null;
	}
	public static boolean datecheck(String ymd){
		return datecheck(ymd,"-");
	}
	public static boolean datecheck(String ymd,String sep){
		if(ymd!=null&&ymd.length()!=0){
			if(ymd.matches("^\\d{1,4}"+sep+"\\d{1,2}"+sep+"\\d{1,2}$")){
				String[] dates=ymd.split(sep);
				return checkdate(Integer.valueOf(dates[0]),Integer.valueOf(dates[1]),Integer.valueOf(dates[2]));
			}
		}
		return false;
	}
	public static boolean checkdate(int year,int month,int day){
		if(year<1||year>9999||month<1||month>12||day<1){
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month-1,1);
	    int maxDay  = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    if(day>maxDay){
			return false;
		}
		return true;
	}
	public static String dateformat(String ymd){
		return dateformat(ymd,"yyyy-MM-dd");
	}
	public static String dateformat(String ymd,String formattype){
		SimpleDateFormat simpleDateFormat = getSimpleDateFormat(formattype, ForumInit.settings.get("timeoffset"));
		try {
			return simpleDateFormat.format(simpleDateFormat.parse(ymd));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
	public static boolean isEmail(String email) {
		return email!=null&&email.length() > 6&& email.matches("^[\\w\\-\\.]+@[\\w\\-\\.]+(\\.\\w+)+$");
	}
	public static boolean forum(Map<String, String> forum,String hideprivate,short groupid,int lastvisit, String extgroupids,Map<String, Map<String, String>> lastposts,SimpleDateFormat sdf) {
		String viewperm = forum.get("viewperm");
		if ("".equals(viewperm) || !"".equals(viewperm)&& forumperm(viewperm,groupid,extgroupids)|| !Common.empty(forum.get("allowview"))) {
			forum.put("permission", "2");
		} else if (hideprivate!=null&&hideprivate.equals("0")) {
			forum.put("permission", "1");
		} else {
			return false;
		}
		String icon=forum.get("icon");
		if (!"".equals(icon)) {
			if (forum.get("icon").indexOf(",")>=0) {
				String[] flash = forum.get("icon").split(",");
				if(flash.length==3){
					forum.put("icon","<a href=\"forumdisplay.jsp?fid="+ forum.get("fid")+ "\">"+"<embed style=\"margin-right: 10px\" src=\""+ flash[0].trim()+ "\" width=\""+ flash[1].trim()+ "\" height=\""+ flash[2].trim()+ "\" type=\"application/x-shockwave-flash\" align=\"left\"></embed></a>");
				}
			} else {
				forum.put("icon","<a href=\"forumdisplay.jsp?fid="+ forum.get("fid")+ "\">" + "<img style=\"margin-right: 10px\" src=\""+ icon+ "\" align=\"left\" border=\"0\" /></a>");
			}
		}
		Map<String, String> lastpost = new HashMap<String, String>();
		int dateline=0;
		String lastpoststr = forum.get("lastpost").trim();
		if (lastpoststr.length()>0) {
			String[] obj = lastpoststr.split("\t");
			dateline=Integer.parseInt(obj[2]);
			lastpost.put("tid", obj[0]);
			lastpost.put("subject", obj[1]);
			lastpost.put("dateline", Common.gmdate(sdf, dateline));
			if(obj.length>3){
				lastpost.put("author", obj[3]);
			}else{
				lastpost.put("author", "");
			}
		}else{
			lastpost.put("tid", "0");
			lastpost.put("subject", "");
			lastpost.put("dateline", "0");
			lastpost.put("author", "");
		}
		forum.put("folder", lastvisit < dateline ? " class='new'" : "");
		if (Integer.valueOf(lastpost.get("tid")) > 0) {
			String author=lastpost.get("author");
			if (!"".equals(author)) {
				lastpost.put("author", "<a href=\"space.jsp?username="+ Common.encode(author) + "\">" + Common.cutstr(author, 11, null) + "</a>");
			}
			lastposts.put(forum.get("fid"), lastpost);
		} else {
			lastposts.put(forum.get("fid"), null);
		}
		lastpost=null;
		forum.put("moderators", moddisplay(forum.get("moderators"),"flat",false));
		String subforums=forum.get("subforums");
		if (subforums!= null&& !subforums.equals("")) {
			forum.put("subforums", subforums);
		}
		return true;
	}
	public static String moddisplay(String moderator, String type, Boolean inherit) {
		StringBuffer modlist = new StringBuffer();
		if ("selectbox".equals(type)) {
			if (moderator != null && !"".equals(moderator)) {
				String[] moderators = moderator.split("\t");
				for (String obj : moderators) {
					modlist.append("<li><a href=\"space.jsp?username=" + Common.encode(obj)+ "\">"	+ (inherit ? "<strong>" + obj + "</strong>" : obj)	+ "</a></li>");
				}
			}
			return modlist.toString();
		} else {
			if (moderator != null && !"".equals(moderator)) {
				String[] moderators = moderator.split("\t");
				for (String obj : moderators) {
					modlist.append("<a class=\"notabs\" href=\"space.jsp?username="+Common.encode(obj) + "\">"+ (inherit ? "<strong>" + obj + "</strong>" : obj)+ "</a>, ");
				}
			}
			int length=modlist.length();
			return length>=2?modlist.substring(0,length-2):"";
		}
	}
	public static boolean forumperm(String permstr,short groupid,String extgroupid) {
		StringBuffer groupidarray = new StringBuffer();
		groupidarray.append(groupid);
		if (extgroupid != null && extgroupid.length() > 0) {
			String[] extgroupids = extgroupid.split("\t");
			for (String obj : extgroupids) {
				if(obj.length()>0){
					groupidarray.append("|" + obj);
				}
			}
		}
		return permstr.matches(".*(^|\t)(" + groupidarray + ")(\t|$).*");
	}
	public static boolean matches(String content, String regex) {
		boolean flag=false;
		try {
			flag =new Perl5Matcher().contains(content, new Perl5Compiler().compile(regex));
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static boolean ismoderator(String ismodertars, Members member) {
		if (member == null) {
			return false;
		}
		if (member.getAdminid() == 1 || member.getAdminid() == 2 || !Common.empty(ismodertars)) {
			return true;
		}
		return false;
	}
	public static boolean ismoderator(short fid, Members member) {
		if (member == null) {
			return false;
		}
		if (member.getAdminid() == 1 || member.getAdminid() == 2) {
			return true;
		}
		List<Map<String, String>> modertar = dataBaseService.executeQuery("select m.uid from jrun_moderators m where m.uid='"+ member.getUid() + "' AND m.fid=" + fid);
		boolean flag=false;
		if (modertar != null && modertar.size() > 0) {
			flag= true;
		}
		modertar=null;
		return flag;
	}
	@SuppressWarnings("unchecked")
	public static Map<String,String> forumformulaperm(String formulaperm,Members member,boolean ismoderator,Map<Integer,Map<String,String>> extcredits,MessageResources mr,Locale locale)
	{
		if(ismoderator){
			return null;
		}
		Map<Integer,String> formula=dataParse.characterParse(formulaperm, true);
		if(formula.size()<2){
			return null;
		}
		String formulamessage=formula.get(0);
		if(!formulamessage.trim().equals(""))
		{
			if(member!=null){
				List<Map<String,String>> members=dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE uid="+member.getUid()+" AND ("+formulamessage+")");
				if(members!=null&&members.size()>0){
					return null;
				}
			}
			String usermsg=formula.get(1);
			String[] usermsgs=usermsg.split("\\$_DSESSION");
			List<String> usermsgList=new ArrayList<String>();
			if(usermsgs!=null)
			{
				for (String obj : usermsgs) {
					obj=obj.trim();
					if(!obj.equals("")){
						usermsgList.add(obj.substring(obj.indexOf("'")+1,obj.lastIndexOf("'")));
					}
				}
			}
			if(usermsgList!=null)
			{
				usermsg="";
				if(member!=null){
					Map<String,Object> usermsgValue=getValues(member,usermsgList,new HashMap<String, Object>());
					for (String obj : usermsgList) {
						Object value=usermsgValue.get(obj);
						if(value!=null)
						{
							usermsg+=obj+" = "+value+"&nbsp;&nbsp;&nbsp;";
						}else{
							usermsg +=obj+" = 0&nbsp;&nbsp;&nbsp;";
						}
					}
				}else{
					for (String obj : usermsgList) {
						usermsg +=obj+" = 0&nbsp;&nbsp;&nbsp;";
					}
				}
			}
			Map<String,String> replaces=new TreeMap<String, String>();
			replaces.put("digestposts", "&nbsp;"+mr.getMessage(locale, "digestposts")+"&nbsp;");
			replaces.put("posts", "&nbsp;"+mr.getMessage(locale, "posts")+"&nbsp;");
			replaces.put("oltime", "&nbsp;"+mr.getMessage(locale, "a_setting_creditsformula_oltime")+"&nbsp;");
			replaces.put("pageviews", "&nbsp;"+mr.getMessage(locale, "pageviews")+"&nbsp;");
			replaces.put("or", "&nbsp;&nbsp;"+mr.getMessage(locale, "or")+"&nbsp;&nbsp;");
			replaces.put("and", "&nbsp;&nbsp;"+mr.getMessage(locale, "and")+"&nbsp;&nbsp;");
			if(extcredits!=null&&extcredits.size()>0){
				for (Integer i = 1; i <= 8; i++) {
					Map<String,String> extcredit=extcredits.get(i);
					if(extcredit!=null){
						replaces.put("extcredits"+i, extcredit.get("title"));
					}else{
						replaces.put("extcredits"+i, mr.getMessage(locale, "a_setting_creditsformula_extcredits")+i);
					}
				}
			}
			Set<Entry<String,String>> dsessions=replaces.entrySet();
			for (Entry<String,String> temp : dsessions) {
				String dsession = temp.getKey();
				String values = temp.getValue();
				formulamessage=formulamessage.replaceAll(dsession, values);
				usermsg=usermsg.replaceAll(dsession, values);
			}
			Map<String,String> messages=new HashMap<String, String>();
			messages.put("formulamessage", formulamessage);
			messages.put("usermsg", usermsg);
			return messages;
		}
		return null;
	}
	public static Object setValues(Object bean, String fieldName, String value) {
		try {
			Field field = bean.getClass().getDeclaredField(fieldName);
			StringBuffer setMethod = new StringBuffer();
			setMethod.append("set");
			setMethod.append(fieldName.substring(0, 1).toUpperCase());
			setMethod.append(fieldName.substring(1, fieldName.length()));
			Method method = bean.getClass().getMethod(setMethod+"",field.getType());
			method.invoke(bean, convert(value, field.getType()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	public static Object setValues(Object bean,HttpServletRequest request) {
		try {
			Field[] fields = bean.getClass().getDeclaredFields();
			String paraName = "";
			String paraValue = "";
			String setMethod = "";
			for (int i = 0; i < fields.length; i++) {
				paraName = fields[i].getName();
				paraValue = request.getParameter(paraName);
				if (paraValue != null && !"".equals(paraValue)) {
					setMethod = "set"+paraName.substring(0,1).toUpperCase()+paraName.substring(1,paraName.length());
					Method method = bean.getClass().getMethod(setMethod,fields[i].getType());
					method.invoke(bean, convert(paraValue,fields[i].getType()));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e){
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}
	@SuppressWarnings("unchecked")
	public static Object convert(String source, Class type) {
		String typeName = type.getName();
		Object target = null;
		if (typeName.equals("java.lang.String")) {
			target = source;
		} else if (typeName.equals("java.lang.Integer")) {
			target = Common.intval(source);
		} else if (typeName.equals("java.lang.Short")) {
			target = (short)Math.min(32767, Common.intval(source));
		} else if (typeName.equals("java.lang.Byte")) {
			target = (byte)Math.min(127, Common.intval(source));
		} else if (typeName.equals("java.lang.Long")) {
			target = Long.parseLong(source);
		}
		return target;
	}
	public static Object getValues(Object bean, String fieldName) {
		Object paraValue = null;
		try {
			Method method = bean.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length()));
			paraValue = method.invoke(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paraValue;
	}
	private static Map<String, Object> getValues(Object bean, List<String> fields,	Map<String, Object> fieldsMap) {
		try {
			Field[] beanFields = bean.getClass().getDeclaredFields();
			if (fieldsMap == null) {
				fieldsMap = new HashMap<String, Object>();
			}
			int fieldLength = fields.size();
			String paraName =null;
			String getMethod=null;
			for (int i = 0; i < fieldLength; i++) {
				paraName = fields.get(i);
				Method method = null;
				Object paraValue = null;
				int beanFieldLength = beanFields.length;
				for (int j = 0; j < beanFieldLength; j++) {
					if (paraName.equals(beanFields[j].getName())) {
						getMethod = "get"+paraName.substring(0, 1).toUpperCase()+paraName.substring(1, paraName.length());
						method = bean.getClass().getMethod(getMethod);
						paraValue = method.invoke(bean, new Object[0]);
						break;
					}
				}
				if (method != null) {
					if (paraValue instanceof Short) {
						paraValue = String.valueOf(paraValue);
					}
					fieldsMap.put(paraName, paraValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fieldsMap;
	}
	public static List<String> getStr(String content, String regex) {
		List<String> strList = new ArrayList<String>();
		try {
			Perl5Matcher patternMatcher=new Perl5Matcher();
			if (patternMatcher.contains(content, new Perl5Compiler().compile(regex))) {
				MatchResult result = patternMatcher.getMatch();
				for (int i = 0; i < result.groups(); i++) {
					strList.add(result.group(i));
				}
				result=null;
			}
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return strList;
	}
	public static String checkpost(String subject, String message,Map<String, String> settings, Map<String, String> admingroups,MessageResources mr,Locale locale) {
		if (Common.strlen(subject) > 80) {
			return mr.getMessage(locale, "post_subject_toolong_com");
		}
		int disablepostctrl = admingroups!=null?Common.toDigit(admingroups.get("disablepostctrl")):0;
		if (disablepostctrl==0) {
			int maxpostsize = Common.toDigit(settings.get("maxpostsize"));
			int minpostsize = Common.toDigit(settings.get("minpostsize"));
			if (maxpostsize > 0 && Common.strlen(message) > maxpostsize) {
				return mr.getMessage(locale, "post_message_toolong",maxpostsize);
			} else if (minpostsize > 0&& (Common.strlen(message.replaceAll("\\[quote\\].+?\\[quote\\]", "")) < minpostsize)) {
				return mr.getMessage(locale, "post_message_tooshort",minpostsize);
			}
		}
		return null;
	}
	public static boolean isNum(String value) {
		boolean flag = true;
		if (value != null) {
			int length = value.length();
			for (int i = 0; i < length; i++) {
				if ((i == 0 && value.charAt(0) == '-')|| Character.isDigit(value.charAt(i))) {
				} else {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	public static int dataToInteger(String ymd,String timeoffset) {
		return dataToInteger(ymd,"yyyy-MM-dd HH:mm",timeoffset);
	}
	public static int dataToInteger(String ymd,String pattern,String timeoffset) {
		SimpleDateFormat format = getSimpleDateFormat(pattern, timeoffset);
		try {
			if(ymd==null||ymd.equals("")){
				return 0;
			}
			Date ndate = format.parse(ymd);
			if (format.format(ndate).equals(ymd)) {
				return (int)(ndate.getTime()/1000);
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			return -1;
		}
	}
	public static void procThread(Map<String,String> thread,double ppp){
		int replies = Integer.valueOf(thread.get("replies"));
		int views = Integer.valueOf(thread.get("views"));
		int special = Integer.valueOf(thread.get("special"));
		if (replies>views) {
			thread.put("views", thread.get("replies"));
		}
		double postsnum = special > 0 ? replies : replies + 1;
		if (postsnum > ppp) {
			StringBuffer pagelinks = new StringBuffer();
			int topicpages = (int)Math.ceil(postsnum/ppp);
			for (int i = 1; i<=6&&i <= topicpages; i++) {
				pagelinks.append("<a href=\"viewthread.jsp?tid="+ thread.get("tid") + "&page=" + i + "\" target=\"_blank\">" + i + "</a> ");
			}
			if (topicpages > 6) {
				pagelinks.append(" .. <a href=\"viewthread.jsp?tid="+ thread.get("tid") + "&page=" + topicpages + "\" target=\"_blank\">"+ topicpages + "</a> ");
			}
			thread.put("multipage", " &nbsp; " + pagelinks);
		}
		procThread(thread);
	}
	public static void procThread(Map<String,String> thread){
		int highlight=Integer.valueOf(thread.get("highlight"));
		if(highlight>0){
			StringBuffer style=new StringBuffer();
			style.append(" style=\"");
			if(highlight>=40){
				style.append("font-weight: bold;");
				highlight=highlight%40;
			}
			if(highlight>=20){
				style.append("font-style: italic;");
				highlight=highlight%20;
			}
			if(highlight>=10){
				style.append("text-decoration: underline;");
				highlight=highlight%10;
			}
			if(highlight>0){
				style.append("color: "+THREAD_COLORS[highlight]);
			}
			style.append("\"");
			thread.put("highlight",style.toString());
		}
		else{
			thread.put("highlight", "");
		}
	}
	@SuppressWarnings("unchecked")
	public static boolean isshowsuccess(HttpSession session,String keyword){
		Map<String,Object> msgforward = (Map<String,Object>)session.getServletContext().getAttribute("msgforward");
		byte quick = msgforward==null?(byte)0:Byte.valueOf(msgforward.get("quick").toString());
		if(quick==1){
			Map successmessages = (Map)(msgforward==null?null:msgforward.get("messages"));
			if(successmessages != null){
				Iterator<Entry> it = successmessages.entrySet().iterator();
				while(it.hasNext()){
					Entry temp = it.next();
					String value = (String)temp.getValue();
					if(keyword.equals(value)){
						return true;
					}
				}
			}
		}
		return false;
	}
	public static void requestforward(HttpServletResponse response,String referer){
		try {
			response.sendRedirect(referer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String strip_tags(String content)
	{
		return content==null?"":content.replaceAll("<[\\s\\S]*?>", "");
	}
	public static String htmlspecialchars(String string){
		return htmlspecialchars(string, 1);
	}
	public static String htmlspecialchars(String text,int quotestyle){
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while(character != StringCharacterIterator.DONE){
			switch (character) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					if(quotestyle==1||quotestyle==2){
						sb.append("&quot;");
					}else{
						sb.append(character);
					}
					break;
				case '\'':
					if(quotestyle==2){
						sb.append("&#039;");
					}else{
						sb.append(character);
					}
					break;
				default:
					sb.append(character);
					break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	public static void setExtcredits(HttpServletRequest request) {
		Map<String, String> settings = ForumInit.settings;
		request.setAttribute("extcredits", dataParse.characterParse(settings.get("extcredits"),true));
	}
	public static void sessionExists(HttpServletRequest request,HttpServletResponse response,String sid,int uid,Map<String,String> settings)
	{
		boolean sessionexists = false;
		String seccode = null;
		int lastolupdate = 0;
		int spageviews=0;
		List<Map<String,String>> sessionlist = dataBaseService.executeQuery("select uid,seccode,pageviews,lastolupdate from jrun_sessions where sid='"+sid+"'");
		if (sessionlist == null || sessionlist.size()<=0) {
			seccode = Common.getRandStr(6, true);
		} else {
			Map<String,String> session = sessionlist.get(0);sessionlist=null;
			int s_uid=Integer.parseInt(session.get("uid"));
			if(s_uid > 0){
				if(s_uid!=uid){
					int jsprun_uid=toDigit(CookieUtil.getCookie(request, "uid", true,settings));
					HttpSession httpSession=request.getSession();
					String jsprun_userss = null;
					short groupid = 7;
					byte adminid = 0;
					if (jsprun_uid>0) {
						Members member = memberService.findMemberById(jsprun_uid);
						if (member != null) {
							String validateAuth = Md5Token.getInstance().getLongToken(member.getPassword() + "\t"+ member.getSecques() + "\t"+ member.getUid());
							if (validateAuth.equals(CookieUtil.getCookie(request, "auth",true,settings))) {
								jsprun_uid = member.getUid();
								jsprun_userss = member.getUsername();
								groupid = member.getGroupid();
								adminid = member.getAdminid();
								httpSession.setAttribute("user", member);
								Common.setDateformat(httpSession, settings);
								httpSession.setAttribute("jsprun_pw", member.getPassword());
							}
						}
					} else {
						CookieUtil.setCookie(request, response, "uid", String.valueOf(jsprun_uid), 604800, true,settings);
					}
					httpSession.setAttribute("jsprun_uid", jsprun_userss!=null?jsprun_uid:0);
					httpSession.setAttribute("jsprun_userss", jsprun_userss!=null?jsprun_userss:"");
					httpSession.setAttribute("jsprun_groupid", groupid);
					httpSession.setAttribute("jsprun_adminid", adminid);
					httpSession.setAttribute("formhash", Common.getRandStr(8, false));
				}
				seccode = session.get("seccode");
			}else{
				if(s_uid!=uid){
					CookieUtil.clearCookies(request, response,settings);
				}
				seccode = Common.getRandStr(6, true);
			}
			sessionexists = true;
			lastolupdate = Integer.parseInt(session.get("lastolupdate"));
			spageviews=Integer.parseInt(session.get("pageviews"));
			session=null;
		}
		request.setAttribute("seccode", Integer.parseInt(seccode));
		request.setAttribute("sessionexists", sessionexists);
		request.setAttribute("lastolupdate", lastolupdate);
		request.setAttribute("spageviews", spageviews);
	}
	public static void setDateformat(HttpSession session,Map<String,String> settings){	
		Members member=(Members)session.getAttribute("user");
		String timeoffset=null;
		String dateformat = null;
		Byte timeformat = null;
		if (member != null) {
			if (member.getDateformat() > 0) {
				String[] userdateformat = settings.get("userdateformat").split("\r\n");
				if(userdateformat.length>=member.getDateformat()){
					dateformat = userdateformat[member.getDateformat()-1];
				}
			}
			if (member.getTimeformat() > 0) {
				timeformat=member.getTimeformat();
			}
			if (!member.getTimeoffset().equals("9999")) {
				timeoffset = member.getTimeoffset();
			}
		}
		dateformat = dateformat == null ? settings.get("dateformat"): dateformat;
		timeformat = timeformat == null ? Byte.parseByte(settings.get("timeformat")):timeformat;
		timeoffset = timeoffset == null ? settings.get("timeoffset"): timeoffset;
		session.setAttribute("dateformat", dateformat);
		session.setAttribute("timeformat",timeformat==1 ? "hh:mm a" : "HH:mm");
		session.setAttribute("timeoffset", timeoffset);
	}
	public static boolean censoruser(String content,String censoruser)
	{
		if(content!=null&&content.length()>0){
			String censorexp = censoruser.replaceAll("\\*", ".*");
			censorexp = censorexp.replaceAll("(\r\n|\r|\n)", "|");
			censorexp = censorexp.replaceAll("\\s", "");
			censorexp = "^(" + censorexp + ")";
			String guestexp = "\\xA1\\xA1|^Guest|^\\xD3\\xCE\\xBF\\xCD|\\xB9\\x43\\xAB\\xC8";
			if (Common.matches(content, "^\\s*$|^c:\\con\\con$|[%,\\\\'\\*\"\\s\\t\\<\\>\\&]|"+ guestexp)|| (censoruser.length()>0 && Common.matches(content, censorexp))) {
				return true;
			}
		}
		return false;
	}
	public static boolean allowAccessBbs(HttpServletRequest request,HttpServletResponse response,HttpSession session,Map<String,String> settings,String accessPath)
	{
		Short groupid = (Short) session.getAttribute("jsprun_groupid");
		Byte adminid = (Byte) session.getAttribute("jsprun_adminid");
		List<Map<String,String>> usergroups = dataBaseService.executeQuery("select allowvisit,disableperiodctrl from jrun_usergroups as u where groupid="+groupid);
		if(usergroups!=null&&usergroups.size()>0){
			int timestamp = Common.time();
			boolean tempB = false;
			Map<String,String> usermap = usergroups.get(0);
			byte allowvisit = Byte.valueOf(usermap.get("allowvisit"));
			byte disableperiodctrl = Byte.valueOf(usermap.get("disableperiodctrl"));
			usergroups = null;
			usermap = null;
			String ipaccess=settings.get("ipaccess");
			String ipban_expiration = settings.get("ipban_expiration");
			int ipban_expiration_int = Common.toDigit(ipban_expiration);
			if(ipban_expiration_int>0&&ipban_expiration_int<timestamp){
				ServletContext context = request.getSession().getServletContext();
				ForumInit.setSettings(context, null);
			}
			String ipbanned = settings.get("ipbanned");
			if (!allowAccess(request,ipaccess, allowvisit,ipbanned)) {
				session.setAttribute("jsprun_uid", 0);
				session.setAttribute("jsprun_userss", "");
				session.setAttribute("jsprun_groupid", (short) 7);
				session.setAttribute("jsprun_adminid", (byte) 0);
				request.setAttribute("propertyKey", true);
				request.setAttribute("resultInfo","user_banned");
				try {
					request.getRequestDispatcher("/showmessage.jsp").forward(request,response);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (!(accessPath.equals("logging.jsp")|| accessPath.equals("wap.jsp") || adminid == 1)) {
				String message = null;
				String arg0_forKey = null;
				int bbclosed = Integer.valueOf(settings.get("bbclosed"));
				if (bbclosed > 0) {
					session.setAttribute("jsprun_uid", 0);
					session.setAttribute("jsprun_userss", "");
					session.setAttribute("jsprun_pw", "");
					session.removeAttribute("user");
					session.setAttribute("jsprun_groupid", (short) 7);
					session.setAttribute("jsprun_adminid", (byte) 0);
					message = settings.get("closedreason");
					if(message == null || message.equals("")){
						tempB = true;
						message = "board_closed";
						arg0_forKey = settings.get("adminemail");
					}
				} else {
					tempB = true;
					List<String> tempL = Common.periodscheck(settings.get("visitbanperiods"), disableperiodctrl, settings.get("timeoffset")); 
					if(tempL != null && tempL.size() == 2){
						message = tempL.get(0);
						arg0_forKey = tempL.get(1);
					}
				}
				if (message != null) {
					if(tempB){
						request.setAttribute("propertyKey", true);
						request.setAttribute("arg0_forKey", arg0_forKey);
						request.setAttribute("show_message", message);
					}else{
						request.setAttribute("show_message", message);
					}
					try {
						request.getRequestDispatcher("/showmessage.jsp?action=nopermission").forward(request, response);
						return true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
	public static boolean allowAccess(HttpServletRequest request,String ipaccess, Byte allowVisit,String ipbanned) {
		String currentPage = (String) request.getAttribute("CURSCRIPT");
		String action = request.getParameter("action");
		String memberIP = Common.get_onlineip(request);
		boolean allowAccess = true;
		ipaccess = ipaccess == null ? "" : ipaccess.trim();
		if (allowVisit != null&& allowVisit == 0&& !(currentPage.equals("member.jsp") && (action != null && (action.equals("groupexpiry") || action.equals("activate"))))) {
			return false;
		}
		if (!ipaccess.equals("")) {
			String[] ipaccesses = ipaccess.split("(\r|\n)");
			boolean sign = false;
			for (String ipaccesse:ipaccesses) {
				if (memberIP.startsWith(ipaccesse)) {
					sign = true;
					break;
				}
			}
			if(!sign){
				return false;
			}
		}
		if(!ipbanned.equals("")){
			return !memberIP.matches(ipbanned);
		}
		return allowAccess;
	}
	public static void setForums(List<Map<String,String>> forumList){
		if(forumList!=null){
			List<Map<String,String>> groups = new ArrayList<Map<String,String>>();
			List<Map<String,String>> forums = new ArrayList<Map<String,String>>();
			List<Map<String,String>> subs = new ArrayList<Map<String,String>>();
			List<Map<String,String>> removeList = new ArrayList<Map<String,String>>();
			for(Map<String,String> forum1:forumList){
				if("group".equals(forum1.get("type"))){
					boolean isExit=false;
					for(Map<String,String> forum2:forumList){
						if("forum".equals(forum2.get("type")) && forum1.get("fid").equals(forum2.get("fup"))){
							isExit=true;
							break;
						}
					}
					if(!isExit){
						removeList.add(forum1);
					}
				}
			}
			forumList.removeAll(removeList);
			for(Map<String,String> forum:forumList){
				String type=forum.get("type");
				if("group".equals(type)){
					groups.add(forum);
				}else if("forum".equals(type)){
					forums.add(forum);
				}else if("sub".equals(type)){
					subs.add(forum);
				}
			}
			forumList.clear();
			for(Map<String,String> group:groups){
				forumList.add(group);
				for(Map<String,String> forum:forums){
					if(group.get("fid").equals(forum.get("fup"))){
						forumList.add(forum);
						for(Map<String,String> sub:subs){
							if(forum.get("fid").equals(sub.get("fup"))){
								forumList.add(sub);
							}
						}
					}
				}
			}
		}
	}
	public static String forumselect(boolean groupselectable,boolean tableformat,short groupid,String extgroupid,String fid){
		List<Map<String,String>> forumList=dataBaseService.executeQuery("SELECT f.fid, f.type, f.name, f.fup, ff.viewperm, ff.formulaperm FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid WHERE f.status=1 ORDER BY f.type, f.displayorder");
		setForums(forumList);
		StringBuffer forumlist=new StringBuffer();
		if(forumList!=null){
			Map<String,Boolean> visible=new HashMap<String,Boolean>();
			if(tableformat){
				forumlist.append("<dl><dd><ul>");
				for(Map<String,String> forum:forumList){
					String type=forum.get("type");
					String name=Common.strip_tags(forum.get("name"));
					String viewperm=forum.get("viewperm");
					if("group".equals(type)){
						forumlist.append("</ul></dd></dl><dl><dt><a href='index.jsp?gid="+forum.get("fid")+"'>"+name+"</a></dt><dd><ul>");
						visible.put(forum.get("fid"), true);
					}else if("forum".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<li"+(forum.get("fid").equals(fid) ? " class='current'" : "")+"><a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+name+"</a></li>");
						visible.put(forum.get("fid"), true);
					}else if("sub".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<li class='sub"+(forum.get("fid").equals(fid) ? "  current'" : "'")+"><a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+name+"</a></li>");
					}
				}
				forumlist.append("</ul></dd></dl>");
			}else{
				forumlist.append("<optgroup label='&nbsp;'>");
				for(Map<String,String> forum:forumList){
					String type=forum.get("type");
					String name=Common.strip_tags(forum.get("name"));
					String viewperm=forum.get("viewperm");
					if("group".equals(type)){
						forumlist.append(groupselectable ? "<option value='"+forum.get("fid")+"'>"+name+"</option>" : "</optgroup><optgroup label='"+name+"'>");
						visible.put(forum.get("fid"), true);
					}else if("forum".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<option value='"+forum.get("fid")+"' "+(forum.get("fid").equals(fid) ? " selected" : "")+">&nbsp; &gt; "+name+"</option>");
						visible.put(forum.get("fid"), true);
					}else if("sub".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<option value='"+forum.get("fid")+"' "+(forum.get("fid").equals(fid) ? " selected" : "")+">&nbsp; &nbsp; &nbsp; &gt; "+name+"</option>");
					}
				}
				forumlist.append("</optgroup>");
			}
			visible=null;
		}
		forumList=null;
		return forumlist.toString().replace(tableformat?"<dl><dd><ul></ul></dd></dl>":"<optgroup label='&nbsp;'></optgroup>", "");
	}
	public static String forumselect(List<Map<String,String>> forumList,boolean groupselectable,boolean tableformat,short groupid,String extgroupid,String fid){
		StringBuffer forumlist=new StringBuffer();
		if(forumList!=null){
			Map<String,Boolean> visible=new HashMap<String,Boolean>();
			if(tableformat){
				forumlist.append("<dl><dd><ul>");
				for(Map<String,String> forum:forumList){
					String type=forum.get("type");
					String name=Common.strip_tags(forum.get("name"));
					String viewperm=forum.get("viewperm");
					if("group".equals(type)){
						forumlist.append("</ul></dd></dl><dl><dt><a href='index.jsp?gid="+forum.get("fid")+"'>"+name+"</a></dt><dd><ul>");
						visible.put(forum.get("fid"), true);
					}else if("forum".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<li"+(forum.get("fid").equals(fid) ? " class='current'" : "")+"><a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+name+"</a></li>");
						visible.put(forum.get("fid"), true);
					}else if("sub".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<li class='sub"+(forum.get("fid").equals(fid) ? "  current'" : "'")+"><a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+name+"</a></li>");
					}
				}
				forumlist.append("</ul></dd></dl>");
			}else{
				forumlist.append("<optgroup label='&nbsp;'>");
				for(Map<String,String> forum:forumList){
					String type=forum.get("type");
					String name=Common.strip_tags(forum.get("name"));
					String viewperm=forum.get("viewperm");
					if("group".equals(type)){
						forumlist.append(groupselectable ? "<option value='"+forum.get("fid")+"'>"+name+"</option>" : "</optgroup><optgroup label='"+name+"'>");
						visible.put(forum.get("fid"), true);
					}else if("forum".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<option value='"+forum.get("fid")+"' "+(forum.get("fid").equals(fid) ? " selected" : "")+">&nbsp; &gt; "+name+"</option>");
						visible.put(forum.get("fid"), true);
					}else if("sub".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<option value='"+forum.get("fid")+"' "+(forum.get("fid").equals(fid) ? " selected" : "")+">&nbsp; &nbsp; &nbsp; &gt; "+name+"</option>");
					}
				}
				forumlist.append("</optgroup>");
			}
			visible=null;
		}
		forumList=null;
		return forumlist.toString().replace(tableformat?"<dl><dd><ul></ul></dd></dl>":"<optgroup label='&nbsp;'></optgroup>", "");
	}
	public static String forumselect(List<Map<String,String>> forumList,short groupid,String extgroupid,String fid){
		StringBuffer forumlist=new StringBuffer();
		if(forumList!=null){
			Map<String,Boolean> visible=new HashMap<String,Boolean>();
			forumlist.append("<dl><dd><ul>");
			for(Map<String,String> forum:forumList){
				String type=forum.get("type");
				String viewperm=forum.get("viewperm");
				if("group".equals(type)){
					forumlist.append("</ul></dd></dl><dl><dt><a href='index.jsp?gid="+forum.get("fid")+"'>"+forum.get("name")+"</a></dt><dd><ul>");
					visible.put(forum.get("fid"), true);
				}else if("forum".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
					forumlist.append("<li"+(forum.get("fid").equals(fid) ? " class='current'" : "")+"><a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+forum.get("name")+"</a></li>");
					visible.put(forum.get("fid"), true);
				}else if("sub".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
					forumlist.append("<li class='sub"+(forum.get("fid").equals(fid) ? "  current'" : "'")+"><a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+forum.get("name")+"</a></li>");
				}
			}
			forumlist.append("</ul></dd></dl>");
			visible=null;
		}
		forumList=null;
		return forumlist.toString().replace("<dl><dd><ul></ul></dd></dl>", "");
	}
	public static String showForumWithSelected(boolean groupselectable,boolean tableformat,Short groupid,String extgroupid,List<String> selectFidList){
		List<Map<String,String>> forumList=dataBaseService.executeQuery("SELECT f.fid, f.type, f.name, f.fup, ff.viewperm, ff.formulaperm, a.uid FROM jrun_forums f LEFT JOIN jrun_forumfields ff ON ff.fid=f.fid LEFT JOIN jrun_access a ON a.fid=f.fid AND a.allowview=1 WHERE f.status=1 ORDER BY f.type, f.displayorder");
		setForums(forumList);
		StringBuffer forumlist=new StringBuffer();
		if(forumList!=null){
			Map<String,Boolean> visible=new HashMap<String,Boolean>();
			if(tableformat){
				forumlist.append("<dl><dd><ul>");
				for(Map<String,String> forum:forumList){
					String type=forum.get("type");
					String name=Common.strip_tags(forum.get("name"));
					String viewperm=forum.get("viewperm");
					if("group".equals(type)){
						forumlist.append("</ul></dd></dl><dl><dt><a href='index.jsp?gid="+forum.get("fid")+"'>"+name+"</a></dt><dd><ul>");
						visible.put(forum.get("fid"), true);
					}else if("forum".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<li"+(selectFidList.contains(forum.get("fid")) ? " class='current'" : "")+"><a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+name+"</a></li>");
						visible.put(forum.get("fid"), true);
					}else if("sub".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<li class='sub"+(selectFidList.contains(forum.get("fid"))  ? "  current'" : "'")+"><a href=\"forumdisplay.jsp?fid="+forum.get("fid")+"\">"+name+"</a></li>");
					}
				}
				forumlist.append("</ul></dd></dl>");
			}else{
				forumlist.append("<optgroup label='&nbsp;'>");
				for(Map<String,String> forum:forumList){
					String type=forum.get("type");
					String name=Common.strip_tags(forum.get("name"));
					String viewperm=forum.get("viewperm");
					if("group".equals(type)){
						forumlist.append(groupselectable ? "<option value='"+forum.get("fid")+"'>"+name+"</option>" : "</optgroup><optgroup label='"+name+"'>");
						visible.put(forum.get("fid"), true);
					}else if("forum".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<option value='"+forum.get("fid")+"' "+(selectFidList.contains(forum.get("fid"))  ? " selected" : "")+">&nbsp; &gt; "+name+"</option>");
						visible.put(forum.get("fid"), true);
					}else if("sub".equals(type)&&visible.get(forum.get("fup"))!=null && ("".equals(viewperm) || (!"".equals(viewperm) && forumperm(viewperm,groupid,extgroupid)))){
						forumlist.append("<option value='"+forum.get("fid")+"' "+(selectFidList.contains(forum.get("fid"))  ? " selected" : "")+">&nbsp; &nbsp; &nbsp; &gt; "+name+"</option>");
					}
				}
				forumlist.append("</optgroup>");
			}
			visible=null;
		}
		forumList=null;
		return forumlist.toString().replace(tableformat?"<dl><dd><ul></ul></dd></dl>":"<optgroup label='&nbsp;'></optgroup>", "");
	}
	public static void visitedforums(HttpServletRequest request,HttpServletResponse response,int visitedforumcount,String visited,Map<String,String> settings){
		int count=0;
		String split="D";
		String visitedfid = CookieUtil.getCookie(request, "visitedfid", true, settings);
		String[] visitedfids=null;
		if(visitedfid!=null){
			visitedfid=Base64.decode(visitedfid,JspRunConfig.CHARSET);
			visitedfids=visitedfid.split(split); 
		}
		StringBuffer fidSB=new StringBuffer();
		fidSB.append(visited);
		StringBuffer visitedforums=new StringBuffer();
		if(visitedfids!=null){
			for(String fid:visitedfids){
				if(!visited.equals(fid)){
					fidSB.append(split);
					fidSB.append(fid);
					String[] fids=fid.split("=>");
					visitedforums.append("<option value="+fids[0]+">"+fids[1]+"</option>");
					if(++count>=visitedforumcount){
						break;
					}
				}
			}
		}
		String value = Base64.encode(fidSB.toString(),JspRunConfig.CHARSET);
		if(!value.equals(visitedfid)){
			CookieUtil.setCookie(request, response, "visitedfid", value, 2592000, true, settings);
		}
		if(visitedforums.length()>0){
			request.setAttribute("visitedforums",visitedforums.toString());
		}
	}
	public static String implode(String[] data, String separator){
		if(data==null){
			return "";
		}
		StringBuffer out=new StringBuffer();
		int length=data.length;
		String o;
		for(int i=0;i<length;i++){
			o=data[i].trim();
			if(o.length()>0){
				if(i>0){
					out.append(separator);
				}
				out.append(o);
			}
		}
		return out.toString();
	}
	public static String implodeids(String[] ids){
		String id=implode(ids, ",");
		return id.length()>0?id:"0";
	}
	@SuppressWarnings("unchecked")
	public static String implode(Object data, String separator) {
		if (data == null) {
			return "";
		}
		StringBuffer out = new StringBuffer();
		if (data instanceof Object[]) {
			boolean flag = false;
			for (Object obj : (Object[]) data) {
				if (flag) {
					out.append(separator);
				} else {
					flag = true;
				}
				out.append(obj);
			}
		} else if (data instanceof Map) {
			Map temp = (Map) data;
			Set<Object> keys = temp.keySet();
			boolean flag = false;
			for (Object key : keys) {
				if (flag) {
					out.append(separator);
				} else {
					flag = true;
				}
				out.append(temp.get(key));
			}
		} else if (data instanceof Collection) {
			boolean flag = false;
			for (Object obj : (Collection) data) {
				if (flag) {
					out.append(separator);
				} else {
					flag = true;
				}
				out.append(obj);
			}
		} else {
			return data.toString();
		}
		return out.toString();
	}
	public static String sImplode(Object ids) {
		return "'" + implode(ids, "','") + "'";
	}
	public static String number_format(double number,String format) {
		return  new DecimalFormat(format).format(number);
	}
	public static String number_format(double number) {
		return number_format(number, 0);
	}
	public static String number_format(double number,int decimals,char... separators) {
		DecimalFormat df = new DecimalFormat();
		if(separators!=null){
			DecimalFormatSymbols dfs=df.getDecimalFormatSymbols();
			int length=separators.length;
			switch (length) {
				case 2:
					dfs.setGroupingSeparator(separators[1]);
				case 1:
					dfs.setDecimalSeparator(separators[0]);
			}
			df.setDecimalFormatSymbols(dfs);
		}
		df.setMaximumFractionDigits(decimals);
		return df.format(number);
	}
	public static String sizeFormat(long dataSize){
		if(dataSize>=1073741824){
			return ((double) Math.round(dataSize / 1073741824d * 100) / 100)+ " GB";
		}else if(dataSize>=1048576){
			return ((double) Math.round(dataSize / 1048576d * 100) / 100)+ " MB";
		}else if(dataSize>=1024){
			return ((double) Math.round(dataSize / 1024d * 100) / 100)+ " KB";
		}else{
			return dataSize+ " Bytes";
		}
	}
	public static String ajax_decode(String s) {
		try {
			return URLDecoder.decode(s,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	public static String encode(String s) {
		try {
			return URLEncoder.encode(s,JspRunConfig.CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	public static String decode(String s) {
		try {
			return URLDecoder.decode(s,JspRunConfig.CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	public static String[] getgroupid(Members member,String creditsformula,Map<String,String> usermap,Usergroups usergroup){
		if(creditsformula!=null && !creditsformula.equals("")){
			String[] result = new String[6];
			if(member!=null){				
				postOperating.setCredits(creditsformula,member,null);
				if(usergroup.getType().equals("member") && !(member.getCredits()>=usergroup.getCreditshigher() && member.getCredits()<=usergroup.getCreditslower())){
					List<Map<String,String>> grouplist = dataBaseService.executeQuery("SELECT groupid,grouptitle,color,stars,groupavatar FROM jrun_usergroups WHERE type='member' AND "+member.getCredits()+">=creditshigher AND "+member.getCredits()+"<creditslower LIMIT 1");
					if(grouplist!=null && grouplist.size()>0){
						member.setGroupid(Short.valueOf(grouplist.get(0).get("groupid")));
						result[0] = grouplist.get(0).get("groupid");
						result[1] = grouplist.get(0).get("grouptitle");
						result[2] = member.getCredits()+"";
						result[3] = grouplist.get(0).get("color");
						result[4] = grouplist.get(0).get("stars");
						result[5] = grouplist.get(0).get("groupavatar");
					}
				}
				memberService.modifyMember(member);
				if(result[0]==null){
					result[0] = usergroup.getGroupid()+"";
					result[1] = usergroup.getGrouptitle()+"";
					result[2] = member.getCredits()+"";
					result[3] = usergroup.getColor();
					result[4] = usergroup.getStars()+"";
					result[5] = usergroup.getGroupavatar();
				}
			}else{
				String credits = postOperating.setCreditsbyMap(creditsformula, usermap, null);
				if(credits!=null){
					if("member".equals(usermap.get("type")) && !(Integer.valueOf(credits)>=Integer.valueOf(usermap.get("creditshigher")) && Integer.valueOf(credits)<=Integer.valueOf(usermap.get("creditslower")))){
						List<Map<String,String>> grouplist = dataBaseService.executeQuery("SELECT groupid,grouptitle,color,stars,groupavatar FROM jrun_usergroups WHERE type='member' AND "+credits+">=creditshigher AND "+credits+"<creditslower LIMIT 1");
						if(grouplist!=null && grouplist.size()>0){
							result[0] = grouplist.get(0).get("groupid");
							result[1] = grouplist.get(0).get("grouptitle");
							result[3] = grouplist.get(0).get("color");
							result[4] = grouplist.get(0).get("stars");
							result[5] = grouplist.get(0).get("groupavatar");
						}
						grouplist = null;
					}
					if(!usermap.get("credits").equals(credits)){
						result[2] = credits;
						String sql = "";
						if(result[0]!=null){
							sql = ",groupid="+result[0];
						}
						dataBaseService.runQuery("update jrun_members set credits = "+credits+sql+" where uid="+usermap.get("uid"),true);
					}
				}
				if(result[0]==null){
					result[0] = usermap.get("groupid");
					result[1] = usermap.get("grouptitle");
					result[3] = usermap.get("color");
					result[4] = usermap.get("stars");
					result[5] = usermap.get("groupavatar");
				}
				if(result[2]==null){
					result[2] = usermap.get("credits");
				}
			}
			return result;
		}
		return null;
	}
	public static void updatepostcredits(String operator, int uid,Map<Integer, Integer> creditsarray, int timestamp,int count,String creditsformula) {
		StringBuffer creditsadd = new StringBuffer();
		Set<Entry<Integer, Integer>> keys = creditsarray.entrySet();
		for (Entry<Integer, Integer> temp : keys) {
			Integer key = temp.getKey();
			creditsadd.append(", extcredits" + key + "= extcredits" + key+ operator + temp.getValue()+"*"+count);
		}
		dataBaseService.runQuery("UPDATE jrun_members SET posts=posts+('"+ operator + count + "') " + ", lastpost='" + timestamp + "'" + " " + creditsadd+ ",credits="+creditsformula+" WHERE uid =" + uid,true);
		creditsadd=null;
	}
	public static void updatepostcredits(String operator, int uid,Map<Integer, Integer> creditsarray, int timestamp) {
		StringBuffer creditsadd = new StringBuffer();
		Set<Entry<Integer, Integer>> keys = creditsarray.entrySet();
		for (Entry<Integer, Integer> temp : keys) {
			Integer key = temp.getKey();
			creditsadd.append(", extcredits" + key + "= extcredits" + key+ operator + temp.getValue());
		}
		dataBaseService.runQuery("UPDATE jrun_members SET posts=posts+('"+ operator + 1 + "') " + ", lastpost='" + timestamp + "'" + " " + creditsadd+ " WHERE uid =" + uid,true);
		creditsadd=null;
	}
	public static void updatepostcredits(String operator, int uid,int count,Map<Integer, Integer> creditsarray) {
		StringBuffer creditsadd = new StringBuffer();
		Set<Entry<Integer, Integer>> keys = creditsarray.entrySet();
		for (Entry<Integer, Integer> temp : keys) {
			Integer key = temp.getKey();
			creditsadd.append(", extcredits" + key + "= extcredits" + key+ operator + temp.getValue()+"*"+count);
		}
		if(creditsadd.length()>0){
			dataBaseService.runQuery("UPDATE jrun_members SET " + creditsadd.substring(1)+ " WHERE uid =" + uid,true);
		}
		creditsadd=null;
	}
	public static void updatepostcredits(int uid,String creditsformula) {
		dataBaseService.runQuery("UPDATE jrun_members SET credits="+creditsformula+" WHERE uid =" + uid,true);
	}
	public static void updateMember(HttpSession session,int uid) {
		if(uid>0){
			session.setAttribute("user", memberService.findMemberById(uid));
		}
	}
	@SuppressWarnings("unchecked")
	public static void calcGroup(HttpSession session,HttpServletRequest request,HttpServletResponse response,Map<String,String> settings){
		Members member = (Members) session.getAttribute("user");
		int timestamp = (Integer)request.getAttribute("timestamp");
		if (member != null) {
			List<Map<String,String>> meminfolist = dataBaseService.executeQuery("select mm.groupterms,m.extgroupids,m.credits,m.extcredits1,m.extcredits2,m.extcredits3,m.extcredits4,m.extcredits5,m.extcredits6,m.extcredits7,m.extcredits8,m.groupid,m.adminid,m.newpm,u.type from jrun_members as m left join jrun_memberfields as mm on m.uid=mm.uid left join jrun_usergroups as u on m.groupid=u.groupid where m.uid="+member.getUid());
			if(meminfolist!=null && meminfolist.size()>0){
				Map<String,String> membermap = meminfolist.get(0);
				session.setAttribute("jsprun_groupid", Short.valueOf(membermap.get("groupid")));
				member.setGroupid(Short.valueOf(membermap.get("groupid")));
				member.setAdminid(Byte.valueOf(membermap.get("adminid")));
				member.setNewpm(Byte.valueOf(membermap.get("newpm")));
				member.setExtgroupids(membermap.get("extgroupids"));
				member.setCredits(Integer.valueOf(membermap.get("credits")));
				member.setExtcredits1(Integer.valueOf(membermap.get("extcredits1")));
				member.setExtcredits2(Integer.valueOf(membermap.get("extcredits2")));
				member.setExtcredits3(Integer.valueOf(membermap.get("extcredits3")));
				member.setExtcredits4(Integer.valueOf(membermap.get("extcredits4")));
				member.setExtcredits5(Integer.valueOf(membermap.get("extcredits5")));
				member.setExtcredits6(Integer.valueOf(membermap.get("extcredits6")));
				member.setExtcredits7(Integer.valueOf(membermap.get("extcredits7")));
				member.setExtcredits8(Integer.valueOf(membermap.get("extcredits8")));
				session.setAttribute("user",member);
				String groupterm = membermap.get("groupterms");
				Map grouptermMap = dataParse.characterParse(groupterm,false);
				if(grouptermMap!=null && !grouptermMap.keySet().isEmpty()){
				Map extMap = (Map) grouptermMap.get("ext");
				if (extMap != null) {
					if (!extMap.keySet().isEmpty()) {
						String extgroupids = membermap.get("extgroupids");
						Map extids = new HashMap();
						if (!extgroupids.equals("")) {
							String extdids[] = extgroupids.split("\t");
							if (extdids != null && extdids.length > 0) {
								for (int i = 0; i < extdids.length; i++) {
									extids.put(extdids[i], "yes");
								}
							}
						}
						Iterator<Entry> it = extMap.entrySet().iterator();
						List keylist = new ArrayList();
						while (it.hasNext()) {
							Entry temp = it.next();
							Object key = temp.getKey();
							int time = (Integer) temp.getValue();
							if (time < timestamp) {
								keylist.add(key);
								if (extids != null && extids.get(key) != null) {
									extids.remove(key);
								}
							}
						}
						if(keylist.size()>0){
							for(int j=0;j<keylist.size();j++){
								extMap.remove(keylist.get(j));
							}
						}
						grouptermMap.put("ext", extMap);
						keylist = null;
						StringBuffer extidp = new StringBuffer();
						if (extids != null) {
							Iterator its = extids.keySet().iterator();
							while (its.hasNext()) {
								Object key = its.next();
								if(!key.toString().equals("")){
									extidp.append(key + "\t");
								}
							}
						}
						if(!member.getExtgroupids().equals(extidp.toString())){
							member.setExtgroupids(extidp.toString());
							dataBaseService.runQuery("update jrun_members set extgroupids = '"+extidp.toString().trim()+"' where uid="+member.getUid(),true);
						}
					}else{
						grouptermMap.remove("ext");
					}
				}
				Map mapMap = (Map) grouptermMap.get("main");
				if (mapMap != null&&mapMap.size()>0) {
					byte adminid = (mapMap.get("adminid")==null?(byte)0:Byte.valueOf(mapMap.get("adminid").toString()));
					short groupid = (mapMap.get("groupid")==null?(short)10:Short.valueOf(mapMap.get("groupid").toString()));
					int time = 0;
					if(mapMap.get("time")!=null){
						time = (Integer)mapMap.get("time");
					}
					if (time>0) {
						if (time < timestamp) {
							member.setAdminid(adminid);
							member.setGroupid(groupid);
							member.setGroupexpiry(0);
							member.setCredits(Common.toDigit(membermap.get("credits")));
							dataBaseService.runQuery("update jrun_members set adminid = "+adminid+",groupid="+groupid+",groupexpiry=0 where uid="+member.getUid(),true);
							session.setAttribute("user", member);
							session.setAttribute("jsprun_adminid", member.getAdminid());
							session.setAttribute("jsprun_groupid", member.getGroupid());
							if(mapMap.get("bktime")!=null){
								mapMap.put("time", mapMap.get("bktime"));
								mapMap.remove("bktime");
								mapMap.put("groupid", mapMap.get("bkgroupid"));
								mapMap.remove("bkgroupid");
								mapMap.put("adminid", mapMap.get("bkadminid"));
								mapMap.remove("bkadminid");
								grouptermMap.put("main", mapMap);
							}else{
								grouptermMap.remove("main");
							}
						}
					}
				}
				mapMap=null;
				if(grouptermMap.keySet().isEmpty()){
					dataBaseService.runQuery("update jrun_memberfields set groupterms = '' where uid="+member.getUid(),true);
				}else{
					String pmdf = dataParse.combinationChar(grouptermMap);
					if(!pmdf.equals(groupterm)){
						dataBaseService.runQuery("update jrun_memberfields set groupterms = '"+pmdf+"' where uid="+member.getUid(),true);
						}
					}
				}
				List<Map<String,String>> usergrouplist = dataBaseService.executeQuery("select type from jrun_usergroups where groupid="+member.getGroupid());
				if (usergrouplist==null || usergrouplist.size()<=0||usergrouplist.get(0).get("type").equals("member")) {
					List<Map<String,String>> membergrouplist = dataBaseService.executeQuery("select groupid from jrun_usergroups where type='member' and "+ membermap.get("credits")+" >= creditshigher and creditslower > "+ membermap.get("credits"));
					if (membergrouplist!=null && membergrouplist.size()>0 && !membergrouplist.get(0).get("groupid").equals(member.getGroupid()+"")) {
						member.setGroupid((short)Common.toDigit(membergrouplist.get(0).get("groupid")));
						member.setAdminid(Byte.valueOf("0"));
						dataBaseService.runQuery("update jrun_members set adminid=0,groupid="+membergrouplist.get(0).get("groupid")+" where uid="+member.getUid(),true);
					}
					membergrouplist = null;
				}
				session.setAttribute("jsprun_adminid", member.getAdminid());
				session.setAttribute("jsprun_groupid", member.getGroupid());
				session.setAttribute("user", member);
				membermap = null;
			}else{
				CookieUtil.clearCookies(request, response, settings);
			}
		}
	}
	public static boolean uploadFile(FormFile src,String targetpath){
		InputStream is = null;
		OutputStream os = null;
		boolean flag = false;
		try {
			is = new BufferedInputStream(src.getInputStream(),4096);
			os =  new BufferedOutputStream(new FileOutputStream(targetpath),4096);
			int count = 0;
			byte[] buffer = new byte[4096];
			while((count = is.read(buffer))>0){
		         os.write(buffer,0,count); 
		    }
			flag = true;
			buffer = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		is = null;src=null;
		return flag;
	}
	public static void updatemodworks(Map<String, String> settings, int uid,int timestamp, String modaction, int posts) {
		String today = gmdate("yyyy-MM-dd", timestamp, settings.get("timeoffset"));
		if ("1".equals(settings.get("modworkstatus")) && !"".equals(modaction) && posts > 0) {
			List<Map<String, String>> modworks = dataBaseService.executeQuery("SELECT * FROM jrun_modworks WHERE uid='"+ uid + "' AND modaction='" + modaction+ "' AND dateline='" + today + "'");
			if (modworks != null && modworks.size() > 0) {
				dataBaseService.runQuery("UPDATE jrun_modworks SET count=count+1, posts=posts+'" + posts+ "' WHERE uid='" + uid + "' AND modaction='"+ modaction + "' AND dateline='" + today + "'",true);
			} else {
				dataBaseService.runQuery("INSERT INTO jrun_modworks (uid, modaction, dateline, count, posts) VALUES ('"+ uid + "', '" + modaction + "', '" + today + "', 1, '"+ posts + "')",true);
			}
		}
	}
	public static void updatemodlog(Members member, int timestamp, String tid,String action, Integer expiration, int status, boolean iscron) {
		int uid = iscron ? member.getUid() : 0;
		String username = iscron ? "0" : member.getUsername();
		expiration = expiration != null ? expiration : 0;
		StringBuffer data = new StringBuffer();
		String comma = "";
		String[] tids = tid.split(",");
		if (tids != null && tids.length > 0) {
			for (String obj : tids) {
				data.append(comma + " ('" + obj + "', '" + uid + "', '" + username+ "', '" + timestamp + "', '" + action + "', '"+ expiration + "', '1', '0')");
				comma = ",";
			}
		}
		if (data.length()>0) {
			dataBaseService.runQuery("INSERT INTO jrun_threadsmod (tid, uid, username, dateline, action, expiration, status, magicid) VALUES "+ data,true);
		}
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void updatesession(HttpServletRequest request,Map<String, String> settings) {
		if(request.getAttribute("sessionupdated")==null){   
			HttpSession httpSession = request.getSession();
			int seccode = (Integer) request.getAttribute("seccode");
			boolean sessionexists = (Boolean) request.getAttribute("sessionexists");
			String sid = (String) httpSession.getAttribute("jsprun_sid");
			String onlineip = Common.get_onlineip(request);
			int jsprun_uid = (Integer) httpSession.getAttribute("jsprun_uid");
			String jsprun_userss=(String) httpSession.getAttribute("jsprun_userss");
			if(jsprun_userss==null){
				jsprun_userss="";
			}
			jsprun_userss = Common.addslashes(jsprun_userss);
			Members member = (Members) httpSession.getAttribute("user");
			int timestamp=(Integer)(request.getAttribute("timestamp"));
			int lastactivity = 0;
			int pvfrequence = Common.toDigit(settings.get("pvfrequence"));
			int spageviews = (Integer) request.getAttribute("spageviews");
			int lastolupdate = (Integer) request.getAttribute("lastolupdate");
			int oltimespan = Common.toDigit(settings.get("oltimespan"));
			int onlinehold = Common.toDigit(settings.get("onlinehold"));
			String styleid = (String)httpSession.getAttribute("styleid");
			short groupid = (Short) httpSession.getAttribute("jsprun_groupid");
			styleid = Common.toDigit(styleid)>0? styleid : settings.get("styleid");
			byte invisible = 0;
			int jsprun_action =Common.intval((String)request.getAttribute("jsprun_action"));
			Short fid = (Short) request.getAttribute("fid");
			Integer tid = (Integer) request.getAttribute("tid");
			fid = fid != null ? fid : 0;
			tid = tid != null ? tid : 0;
			if (sessionexists&&member != null) {
				lastactivity = member.getLastactivity();
				invisible = member.getInvisible();
			}
			if (oltimespan > 0&& jsprun_uid > 0	&& lastactivity > 0	&& ((timestamp - (lastolupdate > 0 ? lastolupdate : lastactivity)) > (oltimespan * 60))) {
				lastolupdate = timestamp;
				Map<String,String> result=dataBaseService.runQuery("UPDATE jrun_onlinetime SET total=total+'"+ oltimespan + "', thismonth=thismonth+'" + oltimespan	+ "', lastupdate='" + timestamp + "' WHERE uid='"+ jsprun_uid + "'");
				int affectedRows = intval(result.get("ok"));
				if(affectedRows==0){
					dataBaseService.runQuery("INSERT INTO jrun_onlinetime (uid, thismonth, total, lastupdate) VALUES ('"+ jsprun_uid + "', '" + oltimespan + "', '"	+ oltimespan + "', '" + timestamp + "')",true);
				}
			}
			if (sessionexists) {
				String pageviewsadd = null;
				if (pvfrequence > 0 && jsprun_uid>0) {
					if (spageviews >= pvfrequence) {
						pageviewsadd = ", pageviews=\'0\'";
						dataBaseService.runQuery("UPDATE jrun_members SET pageviews=pageviews+'" + spageviews+ "' WHERE uid='" + jsprun_uid + "'",true);
						member.setPageviews(member.getPageviews()+spageviews);
						httpSession.setAttribute("user",member);
					} else {
						pageviewsadd = ", pageviews=pageviews+1";
					}
				} else {
					pageviewsadd = "";
				}
				dataBaseService.runQuery("UPDATE jrun_sessions SET uid='" + jsprun_uid+ "', username='" + jsprun_userss + "', groupid='"+ groupid + "', styleid='" + styleid + "', invisible='"	+ invisible + "', action='" + jsprun_action+ "', lastactivity='" + timestamp + "', lastolupdate='"+ lastolupdate + "', seccode='" + seccode + "', fid='"+ fid + "', tid='" + tid + "' "+ pageviewsadd + " WHERE sid='" + sid + "'",true);
			} else {
				String[] ips = onlineip.split("\\.");
				dataBaseService.runQuery("DELETE FROM jrun_sessions WHERE sid='" + sid+ "' OR lastactivity<" + (timestamp - onlinehold)+ " OR ('" + jsprun_uid + "'<>'0' AND uid='" + jsprun_uid+ "') OR (uid='0' AND lastactivity>" + (timestamp - 60) + " AND ip1='" + ips[0] + "' AND ip2='"+ ips[1] + "' AND ip3='" + ips[2] + "' AND ip4='" + ips[3]+ "')",true);
				dataBaseService.runQuery("REPLACE INTO jrun_sessions (sid, ip1, ip2, ip3, ip4, uid, username, groupid, styleid, invisible, action, lastactivity, lastolupdate, seccode, fid, tid) VALUES ('"+ sid + "', '" + ips[0] + "', '" + ips[1] + "', '" + ips[2]+ "', '" + ips[3] + "', '" + jsprun_uid + "', '"+ jsprun_userss + "', '" + groupid + "', '" + styleid+ "', '" + invisible + "', '" + jsprun_action + "', '"+ timestamp + "', '" + lastolupdate + "', '" + seccode+ "', '" + fid + "', '" + tid + "')",true);
				if (jsprun_uid > 0 && (timestamp - lastactivity) > 21600) {
					String oltimeadd = null;
					if (oltimespan > 0 && timestamp - lastactivity > 86400) {
						List<Map<String, String>> total = dataBaseService.executeQuery("SELECT total FROM jrun_onlinetime WHERE uid='" + jsprun_uid + "'");
						int size = (total != null && total.size() > 0 ? Integer.valueOf(total.get(0).get("total")) : 0);
						total=null;
						short oltime=Double.valueOf(Math.round((double) size / 60d)).shortValue();
						oltimeadd = ", oltime="+oltime;
						member.setOltime(oltime);
					}else{
						oltimeadd = "";
					}
					dataBaseService.runQuery("UPDATE jrun_members SET lastip='" + onlineip+ "', lastvisit=lastactivity, lastactivity='"+ timestamp + "' " + oltimeadd + " WHERE uid='"+ jsprun_uid+"'",true);
					member.setLastip(onlineip);
					member.setLastvisit(member.getLastactivity());
					member.setLastactivity(timestamp);
					httpSession.setAttribute("user",member);
				}
			}
			request.setAttribute("sessionupdated", true);
		}
	}
	public static String quescrypt(int questionid, String answer) {
		Md5Token md5 = Md5Token.getInstance();
		return (questionid > 0 && !answer.equals("") ? md5.getLongToken(answer + md5.getLongToken(String.valueOf(questionid))).substring(16, 24) : "");
	}
	public static void updateforumcount(String fid,MessageResources mr,Locale locale){
		List<Map<String,String>> countlist = dataBaseService.executeQuery("select count(*) as threadcount,sum(t.replies)+count(*) as replycount from jrun_threads t,jrun_forums f where f.fid="+fid+" and t.fid=f.fid and t.displayorder>=0");
		Map<String,String> countmap = countlist.get(0);
		String replycount = countmap.get("replycount")==null?"0":countmap.get("replycount");
		String threadcount = countmap.get("threadcount")==null?"0":countmap.get("threadcount");
		countlist = dataBaseService.executeQuery("SELECT tid, subject, author, lastpost, lastposter FROM jrun_threads WHERE fid='"+fid+"' AND displayorder>='0' ORDER BY lastpost DESC LIMIT 1");
		if(countlist==null||countlist.size()<=0){
			dataBaseService.runQuery("update jrun_forums set posts='"+replycount+"',threads='"+threadcount+"',lastpost='' where fid="+fid,true);
			return;
		}
		countmap = countlist.get(0);
		String subject = Common.cutstr(Common.addslashes(countmap.get("subject").replaceAll("\t", " ")), 40, null);
		String lastposter = countmap.get("author").equals("")?mr.getMessage(locale, "anonymous"):Common.addslashes(countmap.get("lastposter"));
		dataBaseService.runQuery("update jrun_forums set posts='"+replycount+"',threads='"+threadcount+"',lastpost='"+countmap.get("tid")+"\t"+subject+"\t"+countmap.get("lastpost")+"\t"+lastposter+"' where fid="+fid,true);
		countmap = null;
	}
	public static void setChecked(HttpServletRequest request,String variable,int size,int value){
		for(int i=0;i<size;i++){
			request.setAttribute(variable+i, (value&(int)Math.pow(2, i))>0?"checked":"");
		}
	}
	public static boolean ipaccess(String ip, String accessips){
		return Common.matches(ip, "^("+accessips.replaceAll("(\r\n|\r|\n)", "|")+")");
	}
	public static String cutstr(String text, int length){
		return cutstr(text, length," ...");
	}
	public static String cutstr(String text, int length,String dot){
		int strBLen = strlen(text);
		if( strBLen <= length ){
     		return text;
     	}
		int temp = 0;
		StringBuffer sb = new StringBuffer(length);
		char[] ch = text.toCharArray();
		for ( char c : ch ) {
			sb.append( c );
			if ( c > 256 ) {
	    		temp += 2;
	    	} else {
	    		temp += 1;
	    	}
			if (temp >= length) {
				if( dot != null) {
					sb.append( dot );
				}
	            break;
	        }
		}
		return sb.toString();
    }
	public static int strlen(String text){
		if(text==null||text.length()==0){
			return 0;
		}
		int length=0;
		try {
			length=text.getBytes(CHARSET_NAME).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}
	public static String nl2br(String text){
		if(text==null||text.length()==0){
			return text;
		}
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while(character != StringCharacterIterator.DONE){
			switch (character) {
			case '\r':
				sb.append("<br/>");
				sb.append(character);
				character = iterator.next();
				if(character=='\n'){
					character = iterator.next();
				}
				break;
			case '\n':
				sb.append("<br/>");
				sb.append(character);
				character = iterator.next();
				if(character=='\r'){
					sb.append(character);
					character = iterator.next();
				}
				break;
			default:
				sb.append(character);
				character = iterator.next();
				break;
			}
		}
		return sb.toString();
	}
	public static void stats(HttpServletRequest request){
		boolean sessionexists = (Boolean)request.getAttribute("sessionexists");
		HttpSession session = request.getSession();
		String jsprun_user = (String)session.getAttribute("jsprun_userss");
		Map<String,String> visitor = new HashMap<String, String>();
		String user_agent = request.getHeader("User-Agent");
		visitor.put("agent", user_agent);
		Calendar calendar = Common.getCalendar(ForumInit.settings.get("timeoffset"));
		int nowMonth = calendar.get(Calendar.MONTH)+1;
		visitor.put("month", calendar.get(Calendar.YEAR)+(nowMonth>9?nowMonth+"":"0"+nowMonth));
		visitor.put("week", calendar.get(Calendar.DAY_OF_WEEK)-1+"");
		int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
		visitor.put("hour", (nowHour>9?"":"0")+nowHour);
		String visitorsadd = "";
		int updatedrows = 4;
		if(!sessionexists){
			if(user_agent != null){
				user_agent = user_agent.toLowerCase();
				if(user_agent.indexOf("netscape")>=0){
					visitor.put("browser", "Netscape");
				}else if(user_agent.indexOf("lynx")>=0){
					visitor.put("browser", "Lynx");
				}else if(user_agent.indexOf("opera")>=0){
					visitor.put("browser", "Opera");
				}else if(user_agent.indexOf("konqueror")>=0){
					visitor.put("browser", "Konqueror");
				}else if(user_agent.indexOf("msie")>=0){
					visitor.put("browser", "MSIE");
				}else if(user_agent.startsWith("mozilla")){
					visitor.put("browser", "Mozilla");
				}else{
					visitor.put("browser", "Other");
				}
				if(user_agent.indexOf("win")>=0){
					visitor.put("os", "Windows");
				}else if(user_agent.indexOf("mac")>=0){
					visitor.put("os", "Mac");
				}else if(user_agent.indexOf("linux")>=0){
					visitor.put("os", "Linux");
				}else if(user_agent.indexOf("freebsd")>=0){
					visitor.put("os", "FreeBSD");
				}else if(user_agent.indexOf("sunos")>=0){
					visitor.put("os", "SunOS");
				}else if(user_agent.indexOf("os/2")>=0){
					visitor.put("os", "OS/2");
				}else if(user_agent.indexOf("aix")>=0){
					visitor.put("os", "AIX");
				}else if(user_agent.matches("(bot|crawl|spider)")){
					visitor.put("os", "Spiders");
				}else{
					visitor.put("os", "Other");
				}
			}else{
				visitor.put("browser", "Other");
				visitor.put("os", "Other");
			}
			visitorsadd = "OR (type='browser' AND variable='"+visitor.get("browser")+"') OR (type='os' AND variable='"+visitor.get("os")+"')"+(jsprun_user!=null&&!jsprun_user.equals("") ? " OR (type='total' AND variable='members')" : " OR (type='total' AND variable='guests')");
			updatedrows = 7;
		}
		Map<String,String> resultMap = dataBaseService.runQuery("UPDATE jrun_stats SET count=count+1 WHERE (type='total' AND variable='hits') "+visitorsadd+" OR (type='month' AND variable='"+visitor.get("month")+"') OR (type='week' AND variable='"+visitor.get("week")+"') OR (type='hour' AND variable='"+visitor.get("hour")+"')");
		String updateCount = resultMap.get("ok");
		if(updateCount!=null&&updatedrows>Integer.valueOf(updateCount)){
			dataBaseService.execute("INSERT INTO jrun_stats (type, variable, count) " + "VALUES ('month', '"+visitor.get("month")+"', '1')");
		}
	}
	public static String authcode(String string,String operation,String key,String charset){
		int expiry=0;
		if(charset==null){
			charset=JspRunConfig.CHARSET;
		}
		try{
			Md5Token md5=Md5Token.getInstance();
			int ckey_length = 4; 
			long millisecond=System.currentTimeMillis();
			int time=(int)(millisecond/1000);
			key = md5.getLongToken(key);
			String keya = md5.getLongToken(key.substring(0,16));
			String keyb = md5.getLongToken(key.substring(16,32));
			String keyc = ckey_length!=0?("DECODE".equals(operation)?string.substring(0,ckey_length):md5.getLongToken(String.valueOf(millisecond)).substring(32-ckey_length)):"";
			String cryptkey = keya + md5.getLongToken(keya+keyc);
			int key_length = cryptkey.length();
			string="DECODE".equals(operation)?Base64.decode(string.substring(ckey_length),charset):(expiry>0 ? expiry+time : "0000000000") + md5.getLongToken(string+keyb).substring(0,16) + string;
			int string_length = string.length();
			int range=256;
			int[] rndkey = new int[range];
			for (int i = 0; i < range; i++) {
				rndkey[i] = cryptkey.charAt(i%key_length);
			}
			int tmp;
			int[] box = new int[range];
			for(int i=0,j=0; i < range; i++){
				j = (j + box[i] + rndkey[i]) % range;
				tmp = box[i];
				box[i] = box[j];
				box[j] = tmp;
			}
			StringBuffer result = new StringBuffer(string_length);
			for(int a=0,i=0,j=0; i < string_length;i++){
				a = (a + 1) % range;
				j = (j + box[a]) % range;
				tmp = box[a];
				box[a] = box[j];
				box[j] = tmp;
				result.append((char)((int)string.charAt(i) ^ (box[(box[a] + box[j]) % range]))); 
			}
			if("DECODE".equals(operation)){
				int resulttime=Common.intval(result.substring(0,10));
				if ((resulttime==0 || resulttime-time>0) && result.substring(10,26).equals(md5.getLongToken(result.substring(26)+keyb).substring(0,16))) {
					return result.substring(26);
				} else {
					return "";
				}
			} else {
				return keyc + (Base64.encode(result.toString(), charset)).replaceAll("=", "");
			}
		}catch(Exception e){
			return "";
		}
	}
	public static String addslashes(String text){    	
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			switch (character) {
			case '\'':
			case '"':
			case '\\':
				sb.append("\\");
			default:
				sb.append(character);
				break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	public static String mysqlEscapeString(String text){    	
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while(character != StringCharacterIterator.DONE){
			switch (character) {
				case '"':
					sb.append("\\\"");
					break;
				case '\'':
					sb.append("\\\'");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\n':
					sb.append("\\n");
					break;
				default:
					sb.append(character);
					break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	public static String stripslashes(String text){
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		boolean flag=true;
		while(character != StringCharacterIterator.DONE){
			if(character=='\\'&&flag){
				flag=false;
			}else{
				flag=true;
				sb.append(character);
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	 public static String pregQuote(String text,char... delimiter){
		char stip='\\';
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		boolean flag=false;
		while(character != StringCharacterIterator.DONE){
			flag=false;
			for (char c : PREG_CHARS) {
				if(character==c){
					flag=true;
					break;
				}
			}
			if(!flag&&delimiter!=null){
				for (char d : delimiter) {
					if(character==d){
						flag=true;
						break;
					}
				}
			}
			if(flag){
				sb.append(stip);
			}
			sb.append(character);
			character = iterator.next();
		}
		return sb.toString();
	 }
	public static String get_onlineip(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(Common.empty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if(Common.empty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		List<String> ips = Common.getStr(ip, "[\\d\\.]{7,15}");
		return ips.size()>0?ips.get(0):"0.0.0.0";
	}
	public static boolean in_array(Object[] source, Object ext){
		return in_array(source, ext, false);
	}
	public static boolean in_array(Object[] source, Object ext, boolean strict){
		if(source==null||ext==null){
			return false;
		}
		for(Object s : source){
			if(s.toString().equals(ext.toString())){
				if(strict){
					if((s.getClass().getName().equals(ext.getClass().getName()))){
						return true;
					}
				}else{
					return true;
				}
			}
		}
		return false;
	}
	public static void dunlink(String fileName,Byte havethumb,Byte remote,String filePath){
		if(remote>0){
			FTPClient fc = ftputil.getFTPClient();
			String message = ftputil.connectToServer(fc);
			if(!message.equals("")){
				ftputil.closeFtpConnect(fc);
				return;
			}
			ftputil.dftp_delete(fileName,fc);
			if(havethumb>0){
				String str = fileName.substring(fileName.lastIndexOf("."));
				ftputil.dftp_delete(fileName+".thumb"+str,fc);
			}
			ftputil.closeFtpConnect(fc);
		}else{
			File file=new File(filePath+"/"+fileName);
			if(file.exists()){
				file.delete();
			}
			if(havethumb>0){
				String str = fileName.substring(fileName.lastIndexOf("."));
				file=new File(filePath+"/"+fileName+".thumb"+str);
				if(file.exists()){
					file.delete();
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	public static void setFtpValue(String ftp,String authorkey){
		if(ftputil.isEmpty()){
			Map<String,String> ftpmap = dataParse.characterParse(ftp, false);
			if(ftpmap.get("on").equals("1")){
				ftputil.setFtpValues(ftpmap.get("host"),ftpmap.get("username"),authcode(ftpmap.get("password"),"DECODE",Md5Token.getInstance().getLongToken(authorkey),"utf-8"),ftpmap.get("attachdir"), toDigit(ftpmap.get("port")),ftpmap.get("ssl"), 0,ftpmap.get("pasv"));
			}
			ftpmap = null;
		}
	}
	@SuppressWarnings("unchecked")
	public static void include(HttpServletRequest request,HttpServletResponse response,HttpServlet servlet,String value,String defvalue){
		File file=null;
		try {
			file=new File(JspRunConfig.realPath+value);
			if(file.exists()){
				request.getRequestDispatcher(value).include(request, response);
			}else if(defvalue!=null){
				request.getRequestDispatcher(defvalue).include(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			file=null;
		}
	}
	public static void include(HttpServletRequest request,HttpServletResponse response,String value,String cachename){
		File file=null;
		try {
			file = new File(JspRunConfig.realPath+value);
			if(!file.exists()){
				Cache.updateCache(cachename);
			}
			request.getRequestDispatcher(value).include(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			file=null;
		}
	}
	@SuppressWarnings("unchecked")
	public static String typeselect(short fid,int curtypeid,int special,int modelid,String onchange,Map threadtypes){
		if(onchange==null){
			onchange="onchange=\"ajaxget('post.jsp?action=threadtypes&typeid='+this.options[this.selectedIndex].value+'&fid="+fid+"&rand='+Math.random(), 'threadtypes', 'threadtypeswait')\"";
		}
		if(threadtypes!=null&&threadtypes.size()>0){
			Map<Integer,String> types=(Map<Integer,String>)threadtypes.get("types");
			Map<Integer,String> specials=(Map<Integer,String>)threadtypes.get("special");
			Map<Integer,String> modelids=(Map<Integer,String>)threadtypes.get("modelid");
			StringBuffer html=new StringBuffer("<select name=\"typeid\" "+(special==0 ? onchange : "")+"><option value=\"0\">&nbsp;</option>");
			Set<Integer> typeids=types.keySet();
			for(Integer typeid:typeids){
				boolean isspecial=specials!=null && "1".equals(specials.get(typeid));
				if((special==0 ||specials!=null&&!isspecial)&&(modelid==0||String.valueOf(modelid).equals(modelids.get(typeid)))) {
					html.append("<option value=\""+typeid+"\" "+(curtypeid == typeid ? "selected=\"selected\"" : "")+" "+(isspecial ? "class=\"special\"" : "")+">"+Common.strip_tags(types.get(typeid))+"</option>");
				}
			}
			html.append("</select><span id=\"threadtypeswait\"></span>"+(special == -1 ? "<input type=\"hidden\" name=\"typeid\" value=\""+curtypeid+"\" />" : ""));
			return html.toString();
		}else{
			return "";
		}
	}
	public static void sendpm(String toid,String subject,String message,String fromid,String from,int timestamp){
		subject=addslashes(subject);
		message=addslashes(message);
		String[] toids =toid.split(",");
		String[] pmids=new String[toids.length];
		int i=0;
		for (String uid : toids) {
			if(uid.length()>0){
				int pid=dataBaseService.insert("INSERT INTO jrun_pms (msgfrom, msgfromid, msgtoid, folder, new, subject, dateline, message) VALUES ('"+from+"', '"+fromid+"', '"+uid+"', 'inbox', '1', '"+subject+"', '"+timestamp+"', '"+message+"')",true);
				if(pid>0){
					pmids[i]=uid;
					i++;
				}
			}
		}
		String uids=implodeids(pmids);
		if(uids.length()>0){
			dataBaseService.runQuery("UPDATE jrun_members SET newpm='1' WHERE uid IN ("+toid+")");
		}
	}
	public static Map<String,Integer> getMultiInfo(int num,int perpage,int curpage){
		Map<String,Integer> multiInfo=new HashMap<String,Integer>();
		int start_limit=0;
		if(num>perpage){
			start_limit=(curpage - 1) * perpage;
			if(start_limit>=num){
				int k = num%perpage;
				curpage=k>0?(num/perpage)+1:(num/perpage);
				start_limit=(curpage - 1) * perpage;
			}else if(start_limit<0){
				curpage=1;
				start_limit=0;
			}
		}else{
			curpage=1;
		}
		multiInfo.put("curpage", curpage);
		multiInfo.put("start_limit", start_limit);
		return multiInfo;
	}
	@SuppressWarnings("unchecked")
	public static Map<String,Object> multi(int num,int perpage,int curpage,String mpurl,int maxpages,int page,boolean autogoto,boolean simple,String ajaxtarget) {
		Map<String,Object> multi=new HashMap<String,Object>();
		int realpages=1;
		if(num>perpage){
			ajaxtarget=ajaxtarget!=null?" ajaxtarget=\""+Common.htmlspecialchars(ajaxtarget)+"\" ":"";
			mpurl+=mpurl.indexOf("?")>=0?"&amp;":"?";
			int offset=2;
			realpages=(int)Math.ceil((float)num/(float)perpage);
			int pages = maxpages>0 && maxpages < realpages ? maxpages : realpages;
			int from=0;
			int to=0;
			if(page>pages){
				from=1;
				to=pages;
			}else{
				from=curpage-offset;
				to=from+page-1;
				if(from<1){
					to=curpage+1-from;
					from=1;
					if(to-from<page){
						to=page;
					}
				}else if(to>pages){
					from=pages-page+1;
					to=pages;
				}
			}
			StringBuffer multipage=new StringBuffer();
			multipage.append((curpage - offset > 1 && pages > page ? "<a href=\""+mpurl+"page=1\" class=\"first\""+ajaxtarget+">1 ...</a>" : ""));
			multipage.append((curpage > 1 && !simple ? "<a href=\""+mpurl+"page="+(curpage - 1)+"\" class=\"prev\""+ajaxtarget+">&lsaquo;&lsaquo;</a>" : ""));
			for (int i = from; i <= to; i++) {
				multipage.append(i == curpage ? "<strong>"+i+"</strong>":"<a href=\""+mpurl+"page="+i+(ajaxtarget.length()>0 && i == pages && autogoto ? "#" : "")+"\""+ajaxtarget+">"+i+"</a>");
			}
			multipage.append((curpage < pages && !simple ? "<a href=\""+mpurl+"page="+(curpage + 1)+"\" class=\"next\""+ajaxtarget+">&rsaquo;&rsaquo;</a>" : ""));
			multipage.append(to < pages ? "<a href=\""+mpurl+"page="+pages+"\" class=\"last\""+ajaxtarget+">... "+realpages+"</a>" : "");
			multipage.append(!simple && pages > page && ajaxtarget.length()==0 ? "<kbd><input type=\"text\" name=\"custompage\" size=\"3\" onkeydown=\"if(event.keyCode==13) {window.location=\'"+mpurl+"page=\'+this.value; return false;}\" /></kbd>" : "");
			multi.put("multipage", "<div class=\"pages\">"+(!simple ? "<em>&nbsp;"+num+"&nbsp;</em>" : "")+multipage+"</div>");
		}
		multi.put("maxpage",realpages);
		return multi;
	}
	public static int time(){
		return (int)(System.currentTimeMillis()/1000);
	}
	public static Map<String,String[]> getTimeZoneIDs(){
		return timeZoneIDs;
	}
	public static SimpleDateFormat getSimpleDateFormat(String format,String timeoffset){
		String[] timeZoneID=timeZoneIDs.get(timeoffset);
		if(timeZoneID==null){
			timeZoneID=timeZoneIDs.get(ForumInit.settings.get("timeoffset"));
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID[0]));
		return sdf;
	}
	public static Calendar getCalendar(String timeoffset){
		String[] timeZoneID=timeZoneIDs.get(timeoffset);
		if(timeZoneID==null){
			timeZoneID=timeZoneIDs.get(ForumInit.settings.get("timeoffset"));
		}
		return Calendar.getInstance(TimeZone.getTimeZone(timeZoneID[0]));
	}
	public static String gmdate(SimpleDateFormat sdf,int timestamp){
		return sdf.format(timestamp*1000l);
	}
	public static String gmdate(String format,int timestamp,String timeoffset){
		return getSimpleDateFormat(format,timeoffset).format(timestamp*1000l);
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public static Map advertisement(String range)
	{
		Map advs=new HashMap();
		int timestamp = Common.time();
		List<Map<String,String>> advertisements=dataBaseService.executeQuery("SELECT advid,type,targets,parameters,code FROM jrun_advertisements WHERE available=1 AND starttime<='"+timestamp+"' and (endtime ='0' or endtime >='"+timestamp+"') ORDER BY displayorder");
		if(advertisements!=null&&advertisements.size()>0)
		{
			Map<String,String> itemsMap=new HashMap<String, String>();
			Map<String,Map<String,String>> typesMap=new HashMap<String, Map<String,String>>();
			for (Map<String, String> adv : advertisements) {
				String type=adv.get("type");
				String advid=adv.get("advid");
				String code=adv.get("code").replaceAll("\r\n", " ");
				code=code.replace("\\", "\\\\");
				Map<String,String> parameters=new HashMap<String,String>();
				if("footerbanner".equals(type)||"thread".equals(type))
				{
					parameters=dataParse.characterParse(adv.get("parameters"), false);
					type+=(parameters.get("position")!=null&&parameters.get("position").matches("^(2|3)$")?parameters.get("position"):"1");
				}
				adv.put("targets", (adv.get("targets").equals("")||adv.get("targets").equals("all"))?(type.equals("text")?"forum":(type.length()>6&&type.substring(0,6).equals("thread")?"forum":"all")):adv.get("targets"));
				String[] targets=adv.get("targets").split("\t");
				if(targets!=null&&targets.length>0)
				{
					for (String target : targets) {
						target=("0".equals(target)?"index":("all".equals(target)||"index".equals(target)||"forumdisplay".equals(target)||"viewthread".equals(target)||"register".equals(target)||"redirect".equals(target)||"archiver".equals(target)?target:("forum".equals(target)?"forum_all":"forum_"+target)));
						if((("forumdisplay".equals(range)&&!("thread".equals(adv.get("type"))||"interthread".equals(adv.get("type"))))||"viewthread".equals(range))&&(target.length()>6&&target.substring(0,6).equals("forum_")))
						{
							if("thread".equals(adv.get("type")))
							{
								String displayorder=parameters.get("displayorder");
								String []displayorders=displayorder!=null&&!displayorder.trim().equals("")?displayorder.split("\t"):new String[]{"0"};
								for (String postcount : displayorders) {
									postcount=postcount.trim();
									Map<String,String> targetMap=typesMap.get(type+"_"+postcount);
									if(targetMap==null)
									{
										targetMap=new HashMap<String, String>();
									}
									targetMap.put(target, targetMap.get(target)!=null?targetMap.get(target)+","+advid:advid);
									typesMap.put(type+"_"+postcount, targetMap);
								}
							}
							else{
								Map<String,String> targetMap=typesMap.get(type);
								if(targetMap==null)
								{
									targetMap=new HashMap<String, String>();
								}
								targetMap.put(target, targetMap.get(target)!=null?targetMap.get(target)+","+advid:advid);
								typesMap.put(type, targetMap);
							}
							if(adv.get("type").equals("float")){
								itemsMap.put(advid, addFloathForFloatAdv(code, (String)dataParse.characterParse(adv.get("parameters"), false).get("floath")));
							}else{
								itemsMap.put(advid, code);
							}
						}
						else if("all".equals(range)&&("all".equals(target)||"redirect".equals(target)))
						{
							Map targetMap=(Map)advs.get(target);
							if(targetMap==null)
							{
								targetMap=new HashMap();
							}
							Map<String,Map<String,String>> typeMap=(Map<String,Map<String,String>>)targetMap.get("type");
							Map<String,String> itemMap=(Map<String,String>)targetMap.get("items");
							if(typeMap==null)
							{
								typeMap= new HashMap<String,Map<String,String>>();
								itemMap= new HashMap<String,String>();
							}
							Map<String,String> typeitems=typeMap.get(type);
							if(typeitems==null)
							{
								typeitems=new HashMap<String, String>();
							}
							typeitems.put("all", typeitems.get("all")!=null?typeitems.get("all")+","+advid:advid);
							typeMap.put(type, typeitems);
							if(adv.get("type").equals("float")){
								itemMap.put(advid, addFloathForFloatAdv(code, (String)dataParse.characterParse(adv.get("parameters"), false).get("floath")));
							}else{
								itemMap.put(advid, code);
							}
							if(typeMap.size()>0&&itemMap.size()>0)
							{
								targetMap.put("type", typeMap);
								targetMap.put("items", itemMap);
							}
							if(targetMap.size()>0)
							{
								advs.put(target, targetMap);
							}
							typeMap=null;
						}else if("index".equals(range)&&"intercat".equals(type))
						{
							parameters=dataParse.characterParse(adv.get("parameters"), false);
							String position=parameters.get("position");
							if(position==null||position.equals(""))
							{
								position="0";
							}
							String[] positions=position.trim().split(",");
							Map<String,String> positionMap=(Map<String,String>)typesMap.get(type);
							if(positionMap==null)
							{
								positionMap=new HashMap<String,String>();
							}
							for (String obj : positions) {
								positionMap.put(obj.trim(), positionMap.get(obj.trim())!=null?positionMap.get(obj.trim())+","+advid:advid);
								if(adv.get("type").equals("float")){
									itemsMap.put(advid, addFloathForFloatAdv(code, (String)dataParse.characterParse(adv.get("parameters"), false).get("floath")));
								}else{
									itemsMap.put(advid, code);
								}
							}
							typesMap.put(type, positionMap);
						}
						else if(target.equals(range)||("index".equals(range)&&"forum_all".equals(target)))
						{
							Map<String,String> advtypeMap=(Map<String,String>)typesMap.get(type);
							if(advtypeMap==null)
							{
								advtypeMap=new HashMap<String,String>();
							}
							advtypeMap.put("0",advtypeMap.get("0")!=null?advtypeMap.get("0")+","+advid:advid);
							if(adv.get("type").equals("float")){
								itemsMap.put(advid, addFloathForFloatAdv(code, (String)dataParse.characterParse(adv.get("parameters"), false).get("floath")));
							}else{
								itemsMap.put(advid,code);
							}
							typesMap.put(type, advtypeMap);
						}
					}
				}
				if(itemsMap.size()>0&&typesMap.size()>0)
				{
					advs.put("items", itemsMap);
					advs.put("type", typesMap);
				}
			}
			itemsMap=null;
			typesMap=null;
		}
		advertisements=null;
		return advs;
	}
	private static String addFloathForFloatAdv(String code,String floath){
		Map<String,String> tempM = new HashMap<String, String>();
		tempM.put("code", code);
		tempM.put("floath", floath);
		return dataParse.combinationChar(tempM);
	}
	public static Locale getUserLocale(HttpServletRequest request) {
		HttpSession session = request.getSession();
        Locale userLocale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        if (userLocale == null) {
            userLocale = request.getLocale();
        }
        return userLocale;
    }
	public static MessageResources getMessageResources(HttpServletRequest request){
		HttpSession session = request.getSession();
		return (MessageResources)session.getServletContext().getAttribute(Globals.MESSAGES_KEY);
	}
	public static int range(int value,int max,int min){
		return Math.min(max, Math.max(value, min));
	}
	public static int intval(String s){
		return intval(s,10);
	}
	public static int toDigit(String s){
		return Math.max(intval(s,10), 0);
	}
	public static int intval(String s,int radix){
		if (s == null||s.length()==0){
			return 0;
		}
		if(radix==0){
			radix=10;
		}else if (radix < Character.MIN_RADIX) {
			return 0;
		}else if (radix > Character.MAX_RADIX) {
			return 0;
		}
		int result = 0;
		int i = 0, max = s.length();
		int limit;
		int multmin;
		int digit;
		boolean negative = false;
		if (s.charAt(0) == '-') {
			negative = true;
			limit = Integer.MIN_VALUE;
			i++;
		} else {
			limit = -Integer.MAX_VALUE;
		}
		if (i < max) {
			digit = Character.digit(s.charAt(i++),radix);
			if (digit < 0) {
				return 0;
			}else{
				result = -digit;
			}
		}
		multmin = limit / radix;
		while (i < max) {
			digit = Character.digit(s.charAt(i++),radix);
			if (digit < 0) {
				break;
			}
			if (result < multmin) {
				result = limit;
				break;
			}
			result *= radix;
			if (result < limit + digit) {
				result = limit;
				break;
			}
			result -= digit;
		}
		if (negative) {
			if (i > 1) {
				return result;
			} else {
				return 0;
			}
		} else {
			return -result;
		}
	}
	public static boolean isFounder(Map<String,String> settings,Members user) {
		if(user.getAdminid()!=1){
			return false;
		}
		String founders=settings.get("forumfounders");
		if(Common.empty(founders)){
			return true;
		}
		founders=founders.replaceAll(" ", "");
		if((","+founders+",").contains(","+user.getUid()+",")){
			return true;
		}
		return false;
	}
	public static void promotion(HttpServletRequest request,HttpServletResponse response,Map<String,String>settings,String fromuid,String fromuser,int jsprun_uid,String jsprun_user,Map creditspolicys){
		int fuid = 0;
		if(!Common.empty(fromuid)) {
			fuid = Common.intval(fromuid);
			fromuser = "";
		}
		if(jsprun_uid==0 || !(fuid == jsprun_uid || fromuser.equals(jsprun_user))) {
			if(!Common.empty(creditspolicys.get("promotion_visit"))) {
				String onlineip = Common.get_onlineip(request);
				dataBaseService.runQuery("REPLACE INTO jrun_promotions (ip, uid, username)VALUES ('"+onlineip+"', '"+fuid+"', '"+fromuser+"')");
			}
			if(!Common.empty(creditspolicys.get("promotion_register"))) {
				if(!empty(fromuser) && empty(fromuid)) {
					String promotion=CookieUtil.getCookie(request, "promotion", true, settings);
					if(empty(promotion)) {
						List<Map<String,String>> list = dataBaseService.executeQuery("SELECT uid FROM jrun_members WHERE username='"+fromuser+"'");
						fuid = list!=null&&list.size()>0?Common.intval(list.get(0).get("uid")):0;
					} else {
						fuid = Common.intval(promotion);
					}
				} 
				if(fuid>0) {
					CookieUtil.setCookie(request, response, "promotion", fuid+"", 1800, true, settings);
				}
			}
		}
	}
	public static Object[] getCacheInfo(String tid,String cacheThreadDir){
		String tidMD5=Md5Token.getInstance().getLongToken(tid).substring(3);
		String fullDir=cacheThreadDir+"/"+tidMD5.charAt(0)+"/"+tidMD5.charAt(1)+"/"+tidMD5.charAt(2)+"/";
		String fileName=fullDir+tid+".htm";
		int fileModified=0;
		File file = new File(fileName);
		if(file.exists()){
			long lastModified = file.lastModified();
			if(lastModified>0){
				fileModified=(int)(lastModified/1000);
			}
		}else{
			File fullFile = new File(fullDir);
			if(!fullFile.isDirectory()){
				for (int i=0; i<3; i++) {
					cacheThreadDir+="/"+tidMD5.charAt(i);
					File cacheThreadFile = new File(cacheThreadDir);
					if(!cacheThreadFile.isDirectory()){
						cacheThreadFile.mkdir();
					}
				}
			}
		}
		Object[] cache = new Object[2];
		cache[0]=fileModified;
		cache[1]=fileName;
		return cache;
	}
	public static String encodeText(HttpServletRequest request, String text){
		String user_agent = request.getHeader("User-Agent");
		if(user_agent==null){
			user_agent="";
		}
		try {
			if (user_agent.indexOf("MSIE")>=0) {
				text = new String(text.getBytes("GBK"),"ISO8859-1");
			}else if (user_agent.indexOf("Firefox")>=0) {
				text = MimeUtility.encodeText(text, "UTF-8", "B");
			} else {
				text=Common.encode(text);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}
	public static void setResponseHeader(HttpServletResponse response){
		setResponseHeader(response, "text/html");
	}
	public static void setResponseHeader(HttpServletResponse response,String type){
		response.setContentType(type+"; charset="+JspRunConfig.CHARSET);
		response.setHeader("Cache-Control", "no-store"); 
		response.setHeader("Program", "no-cache"); 
		response.setDateHeader("Expirse", 0);
	}
	public static void writeMessage(HttpServletResponse response,String message,boolean iserror){
		Common.setResponseHeader(response,"application/xml");
		String content = "<?xml version=\"1.0\" encoding=\""+JspRunConfig.CHARSET+"\"?><root><![CDATA[";
		if(iserror){
			message = message+" <script type=\"text/javascript\" reload=\"1\">function ajaxerror() { alert('"+message+"');}ajaxerror();</script>";
		}
		message = message.replaceAll("([\\x01-\\x09\\x0b-\\x0c\\x0e-\\x1f])+", "");
		message = message.replaceAll("]]>", "]]&gt");
		content = content+message+"]]></root>";
		try {
			response.getWriter().write(content);
		} catch (IOException e) {
		}
	}
	@SuppressWarnings("unchecked")
	public static String formHash(HttpServletRequest request) {
		Md5Token md5 = Md5Token.getInstance();
		HttpSession session = request.getSession();
		Object jsprun_userss = session.getAttribute("jsprun_userss");
		Object jsprun_uid = session.getAttribute("jsprun_uid");
		Object jsprun_pw = session.getAttribute("jsprun_pw");
		String jsprun_auth_key = md5.getLongToken(ForumInit.settings.get("authkey")+request.getHeader("User-Agent"));
		String timestamp = request.getAttribute("timestamp").toString();
		Boolean in_admincp = (Boolean)request.getAttribute("in_admincp");
		char split = '|';
		StringBuffer temp = new StringBuffer();
		temp.append(timestamp.substring(0, timestamp.length() - 7));
		temp.append(split);
		temp.append(jsprun_userss);
		temp.append(split);
		temp.append(jsprun_uid);
		temp.append(split);
		temp.append(jsprun_pw);
		temp.append(split);
		temp.append(jsprun_auth_key);
		if (in_admincp != null) {
			temp.append(split);
			temp.append("Only For JspRun! Admin Control Panel");
		}
		return md5.getLongToken(temp.toString()).substring(8, 16);
	}
	public static String clearLineBreaksFI(String str){
		char[] strArray=str.toCharArray();
		char[] toArray=new char[strArray.length];
		int j=0;
		boolean lastIsSpace=false;
		char appendChar=0;
		for (int i = 0; i < strArray.length; i++) {
			char c=strArray[i];
			if(c!='\r'&&c!='\n'){
				appendChar=c;
				lastIsSpace=(c==' '||c=='\t');
			}else if(!lastIsSpace){
				appendChar=' ';
				lastIsSpace=true;
			}else{
				appendChar=0;
			}
			if(appendChar!=0){
				toArray[j++]=appendChar;
			}
		}
		char[] dest=new char[j];
		System.arraycopy(toArray, 0, dest, 0, j);
		return new String(dest);
	}
}