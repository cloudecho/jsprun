package cn.jsprun.struts.foreg.actions;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Memberfields;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Memberspaces;
import cn.jsprun.domain.Onlinetime;
import cn.jsprun.domain.Profilefields;
import cn.jsprun.domain.Threads;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.IPSeeker;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.LogPage;
import cn.jsprun.vo.space.MythreadsVO;
import cn.jsprun.vo.space.SpaceVO;
import cn.jsprun.vo.space.UserInfoVO;
import cn.jsprun.vo.space.Users;
public class SpacesAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward toSpace(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String preview = request.getParameter("preview"); 
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		String username = request.getParameter("username");
		Members members =null;
		int uid;
		if(username!=null)
		{
			members = memberService.findByName(Common.addslashes(username));
		}
		else{
			uid = convertInt(request.getParameter("uid"));
			members = memberService.findMemberById(uid);
		}		
		if(members==null){
			request.setAttribute("errorInfo",getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(isBanMember(members)){
			Common.requestforward(response, "space.jsp?action=viewpro&uid="+members.getUid());
			return null;
		}
		uid = members.getUid();
		Memberfields membrfileds = memberService.findMemberfieldsById(uid);
		Calendar cale = Common.getCalendar(timeoffset);
		cale.set(Calendar.DATE, 1);
		int ddd = cale.get(Calendar.DAY_OF_WEEK);
		request.setAttribute("week", ddd-1);
		int days = calcCalenday(cale);
		cale.set(Calendar.DATE, days);
		request.setAttribute("days", days);
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH);
		cale.set(year, month - 1, 1);
		String beforbegintime = cale.getTimeInMillis() + "";
		beforbegintime = beforbegintime.substring(0, 10);
		days = calcCalenday(cale);
		cale.set(Calendar.DATE, days);
		String beforendtime = cale.getTimeInMillis() + "";
		beforendtime = beforendtime.substring(0, 10);
		request.setAttribute("beforendtime", beforendtime);
		request.setAttribute("beforbegintime", beforbegintime);
		request.setAttribute("year", year);
		request.setAttribute("month", month + 1);
		Memberspaces memberspace = null;
		if (preview != null) {
			preview = preview.replace('|', '\t');
			String spaceside = request.getParameter("spaceside");
			String style = request.getParameter("style");
			memberspace = new Memberspaces();
			memberspace.setDescription("");
			memberspace.setLayout(preview);
			memberspace.setSide(Byte.valueOf(spaceside));
			memberspace.setStyle(style);
		} else {
			memberspace = spaceServer.findMemberspace(uid);
			if (memberspace == null) {
				memberspace = new Memberspaces();
				memberspace.setUid(uid);
				memberspace.setDescription("");
				memberspace.setLayout("[userinfo][calendar][myreplies][myfavforums]\t[myblogs][mythreads]\t");
				memberspace.setSide(Byte.valueOf("1"));
				memberspace.setStyle("default");
				spaceServer.addMemberSpace(memberspace);
			}
		}
		Map layoutMap1 = new HashMap();
		Map layoutMap2 = new HashMap();
		Map layoutMap3 = new HashMap();
		List<String> layoutlist1 = new ArrayList<String>();
		List<String> layoutlist2 = new ArrayList<String>();
		List<String> layoutlist3 = new ArrayList<String>();
		Map<String, String> settings = ForumInit.settings;
		String spacedata = settings.get("spacedata");
		Map spacemap = dataParse.characterParse(spacedata,false);
		String dateformat = (String)session.getAttribute("dateformat");
		String timeformat = (String)session.getAttribute("timeformat");
		SimpleDateFormat simpleDateFormat_1 = Common.getSimpleDateFormat(dateformat, timeoffset);
		SimpleDateFormat simpleDateFormat_2 = Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int messagelength = convertInt(spacemap.get("textlength").toString());
		String layout = memberspace.getLayout();
		String[] layouts = layout.split("\t");
		String layout1 = layouts[0];
		String layout2 = layouts[1];
		String layout3 = "";
		if(layouts.length>2){
			layout3 = layouts[2];
		}
		if(!layout1.equals("")){
			String lays = layout1;
			lays = lays.substring(1);
			lays = lays.substring(0, lays.length() - 1);
			String disdir[] = lays.split("\\]\\[");
			if (disdir != null) {
				for (String s : disdir) {
					layoutlist1.add(s);
				}
			}
		}
		if(!layout2.equals("")){
			String lays = layout2;
			lays = lays.substring(1);
			lays = lays.substring(0, lays.length() - 1);
			String disdir[] = lays.split("\\]\\[");
			if (disdir != null) {
				for (String s : disdir) {
					layoutlist2.add(s);
				}
			}
		}
		if(!layout3.equals("")){
			String lays = layout3;
			lays = lays.substring(1);
			lays = lays.substring(0, lays.length() - 1);
			String disdir[] = lays.split("\\]\\[");
			if (disdir != null) {
				for (String s : disdir) {
					layoutlist3.add(s);
				}
			}
		}
		include(request, response,"./forumdata/cache/cache_spacesettings.jsp");
		String fids = ((Map<String,String>)request.getAttribute("spacesettings")).get("infids");
		int starttime = timestamp;
		int pendtime = starttime - (Common.intval(Common.gmdate("dd", starttime,timeoffset)) - 1) * 86400 - starttime % 86400;
		Calendar cales = Common.getCalendar(timeoffset);
		int nstarttime = pendtime + (cales.getActualMaximum(5)-1) * 86400;
		List<Map<String,String>> bloglist = dataBaseService.executeQuery("SELECT dateline FROM jrun_threads WHERE blog='1' AND authorid='"+uid+"' AND dateline BETWEEN '"+pendtime+"' AND '"+nstarttime+"' AND displayorder>='0'");
		Map<Integer,Map<String,String>> blogs = new HashMap<Integer,Map<String,String>>();
		for(Map<String,String> blog : bloglist) {
			int day = Integer.valueOf(Common.gmdate("dd", Integer.valueOf(blog.get("dateline")), timeoffset));
			int dateline = Integer.valueOf(blog.get("dateline"));
			if(blogs.get(day)!=null){
				blog = blogs.get(day);
				String num = blog.get("num");
				blog.put("num", (Integer.valueOf(num)+1)+"");
			}else{
				blog.put("num", "1");
				blog.put("dateline", (dateline-dateline%86400)+"");
			}
			blogs.put(day, blog);
		}
		request.setAttribute("blogs", blogs);
		String myblogs = spacemap.get("limitmyblogs").toString();
		if (!myblogs.equals("0")) {
			List list = new ArrayList();
			String bloghql = "select t.*,f.name,p.message,p.pid from jrun_threads as t left join jrun_forums as f on t.fid=f.fid left join jrun_posts as p on t.tid=p.tid where t.authorid = " + uid+ " and f.fid in ("+fids+") and t.blog = 1 and t.displayorder>=0 and p.first=1 order by t.lastpost desc limit 0,"+myblogs;
			List<Map<String,String>> blogthread = dataBaseService.executeQuery(bloghql);
			if (blogthread != null && blogthread.size()>0) {
				for (Map<String,String> thread : blogthread) {
					SpaceVO spacevo = new SpaceVO();
					spacevo.setFid(Common.toDigit(thread.get("fid")));
					spacevo.setForums(thread.get("name"));
					String message = thread.get("message");
					message = Common.strip_tags(message);
					spacevo.setSpecial(thread.get("special"));
					spacevo.setPrice(thread.get("price"));
					spacevo.setPid(Common.toDigit(thread.get("pid")));
					if (message.length() > messagelength) {
						message = message.substring(0, messagelength) + "...";
					}
					spacevo.setMessage(message);
					spacevo.setSubject(thread.get("subject"));
					spacevo.setTid(Common.toDigit(thread.get("tid")));
					String datetime = Common.gmdate(simpleDateFormat_1, Common.toDigit(thread.get("dateline")));
					spacevo.setOperdate(datetime);
					spacevo.setIsnew(true);
					spacevo.setViewnum(Common.toDigit(thread.get("views")));
					spacevo.setReplicenum(Common.toDigit(thread.get("replies")));
					if (thread.get("attachment").equals("1")) {
						spacevo.setIsattc(true);
					} else {
						spacevo.setIsattc(false);
					}
					if (Common.toDigit(thread.get("lastpost"))> members.getLastvisit()) {
						spacevo.setIsnew(true);
					} else {
						spacevo.setIsnew(false);
					}
					list.add(spacevo);
				}
			}
			if(isinarray("myblogs", layoutlist1)){
				layoutMap1.put("myblogs", list);
			}else if(isinarray("myblogs",layoutlist2)){
				layoutMap2.put("myblogs", list);
			}else{
				layoutMap3.put("myblogs", list);
			}
		}
		String mythread = spacemap.get("limitmythreads").toString();
		if (!mythread.equals("0")) {
			List list = new ArrayList();
			String threadhql = "select t.*,f.name,p.message,p.pid from jrun_mythreads as m INNER join jrun_threads as t on m.tid=t.tid INNER join jrun_forums as f on t.fid=f.fid INNER join jrun_posts as p on t.tid=p.tid  where m.uid = " + uid + " and t.special<>3 and t.displayorder>=0 and p.first=1 and f.fid in ("+fids+") and t.blog=0 order by t.lastpost desc limit "+mythread;
			List<Map<String,String>> threadlist = dataBaseService.executeQuery(threadhql);
			if (threadlist != null && threadlist.size()>0) {
				for (Map<String,String> thread : threadlist) {
					SpaceVO spacevo = new SpaceVO();
					spacevo.setFid(Common.toDigit(thread.get("fid")));
					spacevo.setForums(thread.get("name"));
					String message = thread.get("message");
					message = Common.strip_tags(message);
					spacevo.setPid(Common.toDigit(thread.get("pid")));
					if (message.length() > messagelength) {
						message = message.substring(0, messagelength) + "...";
					}
					spacevo.setMessage(message);
					spacevo.setSpecial(thread.get("special"));
					spacevo.setPrice(thread.get("price"));
					spacevo.setSubject(thread.get("subject"));
					spacevo.setTid(Common.toDigit(thread.get("tid")));
					String datetime = Common.gmdate(simpleDateFormat_2, Common.toDigit(thread.get("lastpost")));
					spacevo.setOperdate(datetime);
					spacevo.setIsnew(true);
					spacevo.setViewnum(Common.toDigit(thread.get("views")));
					spacevo.setReplicenum(Common.toDigit(thread.get("replies")));
					if (thread.get("attachment").equals("1")) {
						spacevo.setIsattc(true);
					} else {
						spacevo.setIsattc(false);
					}
					if (Common.toDigit(thread.get("lastpost"))> members.getLastvisit()) {
						spacevo.setIsnew(true);
					} else {
						spacevo.setIsnew(false);
					}
					list.add(spacevo);
				}
			}
			if(isinarray("mythreads", layoutlist1)){
				layoutMap1.put("mythreads", list);
			}else if(isinarray("mythreads",layoutlist2)){
				layoutMap2.put("mythreads", list);
			}else{
				layoutMap3.put("mythreads", list);
			}
		}
		UserInfoVO userinfo = new UserInfoVO();
		userinfo.setUsername(members.getUsername());
		userinfo.setAvoras(membrfileds.getAvatar());
		String bio = membrfileds.getBio();
		String[] bios = bio.split("\t");
		if(bios!=null && bios.length>0){
			MessageResources mr = getResources(request);
			Locale locale = getLocale(request);
			List<Map<String,String>> userg = dataBaseService.executeQuery("select allowbiobbcode,allowbioimgcode from jrun_usergroups where groupid="+members.getGroupid());
			userinfo.setBios(replacebio(bios[0],userg,mr,locale));
		}else{
			userinfo.setBios("");
		}
		userinfo.setWidth(membrfileds.getAvatarwidth());
		userinfo.setHeight(membrfileds.getAvatarheight());
		userinfo.setIsonline(spaceServer.findSessionByUid(uid));
		String myreplies = spacemap.get("limitmyreplies").toString();
		if (!myreplies.equals("0")) {
			List list = new ArrayList();
			String rephql = "select t.*,f.name,p.message,p.pid from jrun_myposts as m INNER join jrun_threads as t on m.tid=t.tid INNER join jrun_forums as f on t.fid=f.fid INNER join jrun_posts as p on t.tid=p.tid where m.uid="+uid+" and p.first=1 and f.fid in ("+fids+")  and t.special<>3 and t.displayorder>=0 order by t.lastpost desc limit "+myreplies;
			List<Map<String,String>> threadlist = dataBaseService.executeQuery(rephql);
			if (threadlist != null && threadlist.size()>0) {
				for (Map<String,String> thread : threadlist) {
					SpaceVO spacevo = new SpaceVO();
					spacevo.setFid(Common.toDigit(thread.get("fid")));
					spacevo.setForums(thread.get("name"));
					String message = thread.get("message");
					spacevo.setPid(Common.toDigit(thread.get("pid")));
					message = Common.strip_tags(message);
					if (message.length() > messagelength) {
						message = message.substring(0, messagelength) + "...";
					}
					spacevo.setMessage(message);
					spacevo.setSpecial(thread.get("special"));
					spacevo.setPrice(thread.get("price"));
					spacevo.setSubject(thread.get("subject"));
					spacevo.setTid(Common.toDigit(thread.get("tid")));
					String datetime = Common.gmdate(simpleDateFormat_2, Common.toDigit(thread.get("lastpost")));
					spacevo.setOperdate(datetime);
					spacevo.setIsnew(true);
					spacevo.setViewnum(Common.toDigit(thread.get("views")));
					spacevo.setReplicenum(Common.toDigit(thread.get("replies")));
					if (thread.get("attachment").equals("1")) {
						spacevo.setIsattc(true);
					} else {
						spacevo.setIsattc(false);
					}
					if (Common.toDigit(thread.get("lastpost"))> members.getLastvisit()) {
						spacevo.setIsnew(true);
					} else {
						spacevo.setIsnew(false);
					}
					list.add(spacevo);
				}
			}
			if(isinarray("myreplies", layoutlist1)){
				layoutMap1.put("myreplies", list);
			}else if(isinarray("myreplies",layoutlist2)){
				layoutMap2.put("myreplies", list);
			}else{
				layoutMap3.put("myreplies", list);
			}
		}
		String myfavforums = spacemap.get("limitmyfavforums").toString();
		if (!myfavforums.equals("0")) {
			List<Map<String,String>> myfavform = dataBaseService.executeQuery("select f.fid,f.name from jrun_favorites as v left join jrun_forums as f on f.fid=v.fid where v.uid="+uid+" and v.fid<>0 limit "+myfavforums);
			if(isinarray("myfavforums", layoutlist1)){
				layoutMap1.put("myfavforums", myfavform);
			}else if(isinarray("myfavforums",layoutlist2)){
				layoutMap2.put("myfavforums", myfavform);
			}else{
				layoutMap3.put("myfavforums", myfavform);
			}
		}
		String myfavthreads = spacemap.get("limitmyfavthreads").toString();
		if (!myfavthreads.equals("0")) {
			List<Map<String,String>> myfavform = dataBaseService.executeQuery("select t.tid,t.subject,t.special,t.price,t.attachment,t.lastpost,t.replies from jrun_favorites as v left join jrun_threads as t on t.tid=v.tid where v.uid="+uid+" and t.fid in ("+fids+") and v.tid<>0 limit "+myfavthreads);
			if(isinarray("myfavthreads", layoutlist1)){
				layoutMap1.put("myfavthreads", myfavform);
			}else if(isinarray("myfavthreads",layoutlist2)){
				layoutMap2.put("myfavthreads", myfavform);
			}else{
				layoutMap3.put("myfavthreads", myfavform);
			}
		}
		String myfriends = spacemap.get("limitmyfriends").toString();
		if (!myfriends.equals("0")) {
			List<Map<String,String>> mybuddys = dataBaseService.executeQuery("select m.uid,m.username from jrun_buddys as b left join jrun_members as m on b.buddyid=m.uid where b.uid="+uid+" limit "+myfriends);
			if(isinarray("myfriends", layoutlist1)){
				layoutMap1.put("myfriends", mybuddys);
			}else if(isinarray("myfriends",layoutlist2)){
				layoutMap2.put("myfriends", mybuddys);
			}else{
				layoutMap3.put("myfriends", mybuddys);
			}
		}
		String myrewards = spacemap.get("limitmyrewards").toString();
		if (!myrewards.equals("0")) {
			String extcredit = settings.get("extcredits");
			String unit = "";
			String creditstrans = settings.get("creditstrans");
			Map extcredits = dataParse.characterParse(extcredit,false);
			if(!creditstrans.equals("0")){
				Map extcredi = (Map)extcredits.get(Common.toDigit(creditstrans));
				unit  = (String)(extcredi==null || extcredi.get("unit")==null?"":extcredi.get("unit"));
			}
			List list = new ArrayList();
			String bloghql = "select t.*,f.name,p.message,p.pid from jrun_threads as t left join jrun_forums as f on t.fid=f.fid left join jrun_posts as p on t.tid=p.tid where t.authorid = " + uid + " and t.special = 3 and t.displayorder>=0 and p.first=1 and f.fid in ("+fids+") order by t.lastpost desc limit "+myrewards;
			List<Map<String,String>> threads = dataBaseService.executeQuery(bloghql);
			if (threads != null && threads.size()>0) {
				for (Map<String,String> thread : threads) {
					SpaceVO spacevo = new SpaceVO();
					spacevo.setFid(Common.toDigit(thread.get("fid")));
					spacevo.setForums(thread.get("name"));
					String message = thread.get("message");
					spacevo.setPid(Common.toDigit(thread.get("pid")));
					message = Common.strip_tags(message);
					if (message.length() > messagelength) {
						message = message.substring(0, messagelength) + "...";
					}
					spacevo.setMessage(message);
					spacevo.setSubject(thread.get("subject"));
					spacevo.setTid(Common.toDigit(thread.get("tid")));
					if (Common.toDigit(thread.get("lastpost"))> members.getLastvisit()) {
						spacevo.setIsnew(true);
					} else {
						spacevo.setIsnew(false);
					}
					spacevo.setViewnum(Common.toDigit(thread.get("views")));
					spacevo.setReplicenum(Common.toDigit(thread.get("replies")));
					spacevo.setRewards(getMessage(request, "thread_reward")+": " + thread.get("price") + unit);
					if (thread.get("attachment").equals("1")) {
						spacevo.setIsattc(true);
					} else {
						spacevo.setIsattc(false);
					}
					list.add(spacevo);
				}
			}
			if(isinarray("myrewards", layoutlist1)){
				layoutMap1.put("myrewards", list);
			}else if(isinarray("myrewards",layoutlist2)){
				layoutMap2.put("myrewards", list);
			}else{
				layoutMap3.put("myrewards", list);
			}
		}
		String mytrades = spacemap.get("limitmytrades").toString();
		if (!mytrades.equals("0")) {
			Map<String,String> ftpmap = dataParse.characterParse(settings.get("ftp"), false);
			String ftpurl = ftpmap.get("attachurl");
			ftpmap = null;
			String sql = "select t.*,a.isimage,a.attachment,a.thumb,a.remote from jrun_trades t LEFT JOIN jrun_attachments as a ON t.aid=a.aid where t.sellerid = " + uid+ " and t.displayorder > 0  order by t.dateline DESC";
			List<Map<String,String>> trades = dataBaseService.executeQuery(sql);
			for(Map<String,String> trade:trades){
				if(!trade.get("expiration").equals("0")){
					float expiration = ((float)Common.toDigit(trade.get("expiration"))-timestamp)/86400;
					if(expiration>0){
						trade.put("expiration", (int)Math.floor(expiration)+"");
					}else{
						trade.put("expiration", "-1");
					}
				}
				if(trade.get("isimage")!=null && trade.get("isimage").equals("1")){
					String url = trade.get("remote").equals("1")?ftpurl:settings.get("attachurl");
					url = url+"/"+trade.get("attachment");
					url = trade.get("thumb").equals("1")?url+".thumb.jpg":url;
					trade.put("attachment", url);
				}else{
					Map<String,String> styles = (Map<String,String>)request.getAttribute("styles");
					trade.put("attachment", ""+styles.get("IMGDIR")+"/trade_nophoto.gif");
				}
			}
			if(isinarray("mytrades", layoutlist1)){
				layoutMap1.put("mytrades", trades);
			}else if(isinarray("mytrades",layoutlist2)){
				layoutMap2.put("mytrades", trades);
			}else{
				layoutMap3.put("mytrades", trades);
			}
		}
		Map menuMap = getMenuMap(layout);
		request.setAttribute("menuMap", menuMap);
		request.setAttribute("layoutMap1", layoutMap1);
		request.setAttribute("layoutMap2", layoutMap2);
		request.setAttribute("layoutMap3", layoutMap3);
		request.setAttribute("layoutlist1", layoutlist1);
		request.setAttribute("layoutlist2", layoutlist2);
		request.setAttribute("layoutlist3", layoutlist3);
		String style = request.getParameter("style");
		if(style!=null && !style.equals("")){
			memberspace.setStyle(style);
		}
		request.setAttribute("memberspace", memberspace);
		request.setAttribute("userinfo", userinfo);
		request.setAttribute("member", members);
		request.setAttribute("memberfild", membrfileds);
		return mapping.findForward("tospace");
	}
	@SuppressWarnings("unused")
	private static int convertInt(String s) {
		int count = 0;
		try {
			count = Integer.valueOf(s);
		} catch (Exception e) {
		}
		return count;
	}
	@SuppressWarnings("unchecked")
	public ActionForward toSpacemodule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		if(uid==0){
			request.setAttribute("resultInfo",getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}
		Map<String, String> settings = ForumInit.settings;
		Map spacemap = dataParse.characterParse(settings.get("spacedata"),false);
		Memberspaces memberspace = spaceServer.findMemberspace(uid);
		if (memberspace == null) {
			memberspace = new Memberspaces();
			memberspace.setUid(uid);
			memberspace.setDescription("");
			memberspace.setLayout("[userinfo][calendar][myreplies][myfavforums]\t[myblogs][mythreads]\t");
			memberspace.setSide(Byte.valueOf("1"));
			memberspace.setStyle("default");
			spaceServer.addMemberSpace(memberspace);
		}
		List layoutlist1 = new ArrayList();
		List layoutlist2 = new ArrayList();
		List layoutlist3 = new ArrayList();
		String layout = memberspace.getLayout();
		String[] layouts = layout.split("\t");
		String layout1 = layouts[0];
		String layout2 = layouts[1];
		String layout3 = "";
		if(layouts.length>2){
			layout3 = layouts[2];
		}
		request.setAttribute("layout1", layout1);
		request.setAttribute("layout2", layout2);
		request.setAttribute("layout3", layout3);
		if(!layout1.equals("")){
			String lays = layout1;
			lays = lays.substring(1);
			lays = lays.substring(0, lays.length() - 1);
			String disdir[] = lays.split("\\]\\[");
			if (disdir != null) {
				for (String s : disdir) {
					layoutlist1.add(s);
				}
			}
		}
		if(!layout2.equals("")){
			String lays = layout2;
			lays = lays.substring(1);
			lays = lays.substring(0, lays.length() - 1);
			String disdir[] = lays.split("\\]\\[");
			if (disdir != null) {
				for (String s : disdir) {
					layoutlist2.add(s);
				}
			}
		}
		if(!layout3.equals("")){
			String lays = layout3;
			lays = lays.substring(1);
			lays = lays.substring(0, lays.length() - 1);
			String disdir[] = lays.split("\\]\\[");
			if (disdir != null) {
				for (String s : disdir) {
					layoutlist3.add(s);
				}
			}
		}
		Members member = memberService.findMemberById(uid);
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		request.setAttribute("member", member);
		Map spacemodules = new HashMap();
		spacemodules.put("userinfo", "1");
		spacemodules.put("calendar", "1");
		spacemodules.put("myblogs", "");
		spacemodules.put("mythreads", "");
		spacemodules.put("myreplies", "");
		spacemodules.put("myrewards", "");
		spacemodules.put("mytrades", "");
		spacemodules.put("myvideos", "1");
		spacemodules.put("myfriends", "1");
		spacemodules.put("myfavforums", "1");
		spacemodules.put("myfavthreads", "1");
		request.setAttribute("spacemodules", spacemodules);
		Map spacelaguage = new HashMap();
		spacelaguage.put("userinfo", getMessage(request, "userinfo"));
		spacelaguage.put("calendar", getMessage(request, "calendar"));
		spacelaguage.put("myblogs", getMessage(request, "myblogs"));
		spacelaguage.put("mythreads", getMessage(request, "thread"));
		spacelaguage.put("myreplies", getMessage(request, "threads_replies"));
		spacelaguage.put("myrewards", getMessage(request, "thread_reward"));
		spacelaguage.put("mytrades", getMessage(request, "mytrades"));
		spacelaguage.put("myvideos", getMessage(request, "thread_video"));
		spacelaguage.put("myfriends", getMessage(request, "myfriends"));
		spacelaguage.put("myfavforums", getMessage(request, "myfavforums"));
		spacelaguage.put("myfavthreads", getMessage(request, "myfavthreads"));
		request.setAttribute("spacelaguage", spacelaguage);
		request.setAttribute("memberfild", memberfild);
		request.setAttribute("spacemap", spacemap);
		request.setAttribute("memberspace", memberspace);
		request.setAttribute("layoutlist1", layoutlist1);
		request.setAttribute("layoutlist2", layoutlist2);
		request.setAttribute("layoutlist3", layoutlist3);
		return mapping.findForward("tospacemodule");
	}
	@SuppressWarnings("unchecked")
	public ActionForward editSpacemodule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		if(uid==0){
			request.setAttribute("resultInfo",getMessage(request, "not_loggedin"));
			return mapping.findForward("showMessage");
		}
		try{
			if(submitCheck(request, "editsubmit")){
				String spacelayout1 = request.getParameter("spacelayout[1]");
				String spacelayout2 = request.getParameter("spacelayout[2]");
				String spacelayout0 = request.getParameter("spacelayout[0]");
				String spacename = request.getParameter("spacename"); 
				String spacedescription = request.getParameter("spacedescription"); 
				String spacestyle = request.getParameter("spacestyle")==null?"default":request.getParameter("spacestyle"); 
				String spaceside = request.getParameter("spaceside"); 
				Memberspaces memberspace = spaceServer.findMemberspace(uid);
				String layout = spacelayout0+"\t"+spacelayout1+"\t"+spacelayout2;
				memberspace.setLayout(layout);
				memberspace.setSide(Byte.valueOf(spaceside));
				memberspace.setStyle(spacestyle);
				if (spacedescription != null) {
					memberspace.setDescription(spacedescription);
				}
				if (spacename != null) {
					Memberfields memberfiled = memberService.findMemberfieldsById(uid);
					memberfiled.setSpacename(spacename);
					memberService.modifyMemberfields(memberfiled);
				}
				spaceServer.modifyMemberspace(memberspace);
				request.setAttribute("successInfo", getMessage(request, "space_setting_succeed"));
				request.setAttribute("requestPath", request.getContextPath()+ "/space.jsp?uid=" + uid);
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("resultInfo",getMessage(request, "undefined_action_return"));
		return mapping.findForward("showMessage");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toUserinfo(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		Members member =null;
		int uid;
		if(username!=null)
		{
			member = memberService.findByName(Common.addslashes(username));
		}
		else{
			uid = Common.toDigit(request.getParameter("uid"));
			member = memberService.findMemberById(uid);
		}
		if(member==null){
			request.setAttribute("errorInfo", getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		uid = member.getUid();
		HttpSession session = request.getSession();
		Members tomember = (Members)session.getAttribute("user");
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> usergroups=(Map<String,String>)request.getAttribute("usergroups");
		String dateformat = (String)session.getAttribute("dateformat");
		String timeoffset = (String)session.getAttribute("timeoffset");
		SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat(dateformat, timeoffset);
		if("0".equals(usergroups.get("allowviewpro")) && uid!=jsprun_uid){
			request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		Onlinetime online = memberService.findOnlineTimeById(uid);
		List<Profilefields> profiled = memberService.findprofilefieldByAvaliable((byte)1);
		StringBuffer profileds = new StringBuffer();
		for(Profilefields profiledt :profiled){
			profileds.append(",field_"+profiledt.getFieldid());
		}
		if(!profileds.toString().equals("")){
			Map<String,String> profiledvalue = dataBaseService.executeQuery("select "+profileds.substring(1)+" from jrun_memberfields where uid="+uid).get(0);
			StringBuffer profiledis = new StringBuffer();
			for(Profilefields profiledt :profiled){
				profiledis.append("<tr><th>");
				profiledis.append(profiledt.getTitle());
				profiledis.append(":</th><td>");
				profiledis.append(profiledvalue.get("field_"+profiledt.getFieldid())==null?"":profiledvalue.get("field_"+profiledt.getFieldid()));
				profiledis.append("</td></tr>");
			}
			request.setAttribute("profiledis", profiledis);
		}
		profiled = null;
		Map<String, String> settings = ForumInit.settings;
		request.setAttribute("isfounder", !Common.empty(settings.get("forumfounders"))&&Common.isFounder(settings, member));
		Users users = new Users();
		Usergroups usergroup = userGroupService.findUserGroupById(member.getGroupid());
		memberfild.setTaobao(Common.addslashes(memberfild.getTaobao()));
		String extgroupterms = memberfild.getGroupterms();
		Map extgroupMap = dataParse.characterParse(extgroupterms,false);
		Map mainMap = null;
		Map extMap = null;
		if (extgroupMap != null) {
			mainMap = (Map) extgroupMap.get("main");
			extMap = (Map) extgroupMap.get("ext");
		}
		StringBuffer extgroups = new StringBuffer();
		String extgroupids = member.getExtgroupids();
		String sql="";
		if (extgroupids != null && !extgroupids.equals("")) {
			sql="SELECT groupid,grouptitle FROM jrun_usergroups WHERE groupid in("+extgroupids.trim().replaceAll("\t", ",")+")";
			List<Map<String,String>> extgroupList=dataBaseService.executeQuery(sql);
			for(Map<String,String> extgroup:extgroupList)
			{
				if (extMap != null && extMap.get(extgroup.get("groupid")) != null) {
					extgroups.append(extgroup.get("grouptitle")+ " ("+getMessage(request, "valid_before")+ Common.gmdate(simpleDateFormat, Common.toDigit(extMap.get(extgroup.get("groupid")).toString()))+"<br/>");
				} else {
					extgroups.append(extgroup.get("grouptitle")+"<br/>");
				}
			}
		}		
		users.setExtgroup(extgroups.toString());
		if(usergroup!=null){
			String result[] = Common.getgroupid(member, settings.get("creditsformula"), null, usergroup);
			member.setCredits(convertInt(result[2]));
			users.setGrouptitle(result[1]);
			users.setGroupstars(result[4]);
			usergroup.setColor(result[3]);
			usergroup.setStars(Short.valueOf(result[4]));
			if (mainMap != null) {
				String time = mainMap.get("time") == null ? "" : mainMap.get("time").toString();
				if(!time.equals("0") && !time.equals("")){
					users.setGrouptime("<br> "+getMessage(request, "valid_before") + Common.gmdate(simpleDateFormat, Common.toDigit(time)));
				}
			}
		}
		Map<Integer,Map<String,String>> ranks=dataParse.characterParse(((Map<String,String>)request.getAttribute("ranks")).get("ranks"),false);
		for (int j = 1; j <= ranks.size(); j++) {
			Map<String,String> rank = ranks.get(j);
			if (member.getPosts() >= Integer.valueOf(rank.get("postshigher"))) {
				users.setRankname(rank.get("ranktitle"));
				users.setStars(rank.get("stars"));
				users.setRankcolor(rank.get("color"));
				break;
			}
		}
		int postnum = spaceServer.findPostCount();
		int posts = member.getPosts();
		double proportion = 0.00;
		if(postnum>0){
			proportion = ((double) posts / (double) postnum) * 100;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		users.setPostnum(posts + "("+getMessage(request, "post_percent") + df.format(proportion) + "%)");
		double diff = new Date().getTime()/1000 - member.getRegdate();
		double day = diff / (60 * 60 * 24);
		double propro = posts /(day>1?day:1d);
		users.setEveryposts(df.format(propro));
		if (member.getGender() == 0) {
			users.setSex(getMessage(request, "a_member_edit_gender_secret"));
		} else if (member.getGender() == 1) {
			users.setSex(getMessage(request, "a_member_edit_gender_male"));
		} else {
			users.setSex(getMessage(request, "a_member_edit_gender_female"));
		}
		request.setAttribute("allowip", false);
		if(member.getAdminid()<=0 || tomember.getAdminid()<=member.getAdminid()){
			MessageResources resources = getResources(request);
			Locale locale = getLocale(request);
			IPSeeker seeker  = IPSeeker.getInstance();
			String ipaddress = seeker.getAddress(member.getLastip(),resources,locale);
			String address2 = seeker.getAddress(member.getRegip(),resources,locale);
			if(!address2.equals("")){
				address2 = " -- " +address2;
			}
			request.setAttribute("ipaddress2", address2);
			request.setAttribute("ipaddress", " -- "+ipaddress);
			request.setAttribute("allowip", true);
		}
		UserInfoVO userinfo = new UserInfoVO();
		userinfo.setUsername(member.getUsername());
		userinfo.setAvoras(memberfild.getAvatar());
		String bio = memberfild.getBio();
		String[] bios = bio.split("\t");
		if(bios!=null && bios.length>0){
			MessageResources mr = getResources(request);
			Locale locale = getLocale(request);
			List<Map<String,String>> userg = dataBaseService.executeQuery("select allowbiobbcode,allowbioimgcode from jrun_usergroups where groupid="+member.getGroupid());
			userinfo.setBios(replacebio(bios[0],userg,mr,locale));
		}else{
			userinfo.setBios("");
		}
		userinfo.setWidth(memberfild.getAvatarwidth());
		userinfo.setHeight(memberfild.getAvatarheight());
		userinfo.setIsonline(spaceServer.findSessionByUid(uid));
		request.setAttribute("member", member);
		request.setAttribute("onlitime", online);
		request.setAttribute("memberfild", memberfild);
		request.setAttribute("users", users);
		request.setAttribute("userinfo", userinfo);
		request.setAttribute("usergroup", usergroup);
		double  onlinettotal = online==null?0.0:online.getTotal();
		double onlinenum = Double.valueOf(df.format(onlinettotal/60));
		int onlinestars = (int) Math.ceil((onlinenum + 1) / 50);
		int olupgrade = onlinettotal > 0 ? (int) Math.ceil(20 - onlinenum % 20) : 20; 
		request.setAttribute("olupgrade", olupgrade);
		request.setAttribute("onlinstars",onlinestars);
		int buycredit = memberfild.getBuyercredit();
		int shellcredit = memberfild.getSellercredit();
		Map buycreditMap = dataParse.characterParse(settings.get("ec_credit"), true);
		Map buysMap = (Map) buycreditMap.get("rank");
		String postbuycredit = "0";
		String postshellcredit = "0";
		if (buysMap != null) {
			if(buycredit>0){
				Iterator it = buysMap.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					if (buycredit <= Integer.valueOf(buysMap.get(key).toString())) {
						postbuycredit=key.toString();
						break;
					}
				}
			}
			if(shellcredit>0){
				Iterator its = buysMap.keySet().iterator();
				while (its.hasNext()) {
					Object key = its.next();
					if (shellcredit <= Integer.valueOf(buysMap.get(key).toString())) {
						postshellcredit = key.toString();
						break;
					}
				}
			}
		}
		request.setAttribute("buycredit", postbuycredit);
		request.setAttribute("shellcredit", postshellcredit);
		Map extcredits = dataParse.characterParse(settings.get("extcredits"), true);
		request.setAttribute("extcredits", extcredits);
		String medal = memberfild.getMedals().trim();
		if (!medal.equals("")) {
			try {
				request.getRequestDispatcher("/forumdata/cache/cache_medals.jsp").include(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<String,Map<String,String>> medals=dataParse.characterParse(((Map<String,String>)request.getAttribute("medals")).get("medals"),true);
			if(medals!=null && medals.size()>0){
				StringBuffer medallist=new StringBuffer();
				for(int j=0;j<medals.size();j++){
					Map<String,String> medaltemp=medals.get(j);
					if(Common.matches(medal,"(^|\t)(" + medaltemp.get("medalid") + ")(\t|$)")){
						medallist.append("<img src='images/common/"+medaltemp.get("image")+"' border='0' title='"+medaltemp.get("name")+"' /> &nbsp;");
					}
				}
				request.setAttribute("medallist", medallist.length()>0?medallist:null);
			}
		}
		if(member.getAdminid()>0){
			List<Map<String, String>> modertarlist = dataBaseService.executeQuery("SELECT f.fid, f.name FROM jrun_moderators m LEFT JOIN jrun_forums f USING(fid) where m.inherited='0' and f.status=1 and m.uid="+ uid+" ORDER BY f.displayorder");
			request.setAttribute("modertarlist", modertarlist!=null&&modertarlist.size()>0?modertarlist:null);
		}
		if(settings.get("spacestatus").equals("1")&&!isBanMember(member)){
			Memberspaces memberspace = spaceServer.findMemberspace(uid);
			if (memberspace == null) {
				memberspace = new Memberspaces();
				memberspace.setUid(uid);
				memberspace.setDescription("");
				memberspace.setLayout("[userinfo][calendar][myreplies][myfavforums]	[myblogs][mythreads]	");
				memberspace.setSide(Byte.valueOf("1"));
				memberspace.setStyle("default");
				spaceServer.addMemberSpace(memberspace);
			}
			String layout = memberspace.getLayout();
			Map menuMap = getMenuMap(layout);
			request.setAttribute("menuMap", menuMap);
			request.setAttribute("memberspace", memberspace);
			return mapping.findForward("touserinfo");			
		}else{
			return mapping.findForward("userinfo");
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMyblogs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String suid = request.getParameter("uid"); 
		int uid = convertInt(suid);
		Members member = memberService.findMemberById(uid);
		if(member==null){
			request.setAttribute("errorInfo", getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(isBanMember(member)){
			Common.requestforward(response, "space.jsp?action=viewpro&uid="+uid);
			return null;
		}
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		request.setAttribute("bember", member);
		request.setAttribute("memberfild", memberfild);
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		int begin = Common.intval(request.getParameter("begin"));
		int end = Common.intval(request.getParameter("end"));
		String sqladd = "";
		if (begin <=0 || end <=0) {
			Calendar cale = Common.getCalendar(timeoffset);
			cale.set(Calendar.DATE, 1);
			int ddd = cale.get(Calendar.DAY_OF_WEEK);
			request.setAttribute("week", ddd-1);
			int days = calcCalenday(cale);
			begin = (int)(cale.getTimeInMillis()/1000);
			cale.set(Calendar.DATE, days);
			end = (int)(cale.getTimeInMillis() /1000);
			request.setAttribute("days", days);
			int year = cale.get(Calendar.YEAR);
			int month = cale.get(Calendar.MONTH);
			cale.set(year, month - 1, 1);
			String beforbegintime = cale.getTimeInMillis() + "";
			beforbegintime = beforbegintime.substring(0, 10);
			days = calcCalenday(cale);
			cale.set(Calendar.DATE, days);
			String beforendtime = cale.getTimeInMillis() + "";
			beforendtime = beforendtime.substring(0, 10);
			request.setAttribute("beforendtime", beforendtime);
			request.setAttribute("beforbegintime", beforbegintime);
			request.setAttribute("year", year);
			request.setAttribute("month", month + 1);
		} else {
			Calendar cale = Common.getCalendar(timeoffset);
			Calendar nowcale =  Common.getCalendar(timeoffset);
			cale.setTimeInMillis(Long.valueOf(begin + "000"));
			cale.set(Calendar.DATE, 1);
			int ddd = cale.get(Calendar.DAY_OF_WEEK);
			request.setAttribute("week", ddd-1);
			int days = calcCalenday(cale);
			int month = cale.get(Calendar.MONTH);
			int year = cale.get(Calendar.YEAR);
			if (cale.compareTo(nowcale) == -1&& (cale.get(Calendar.MONTH) != nowcale.get(Calendar.MONTH) || cale.get(Calendar.YEAR) != nowcale.get(Calendar.YEAR))) {
				cale.set(Calendar.MONTH, month + 1);
				int afterdays = calcCalenday(cale);
				cale.set(Calendar.DATE, 1);
				String afterbegintime = cale.getTimeInMillis() + "";
				afterbegintime = afterbegintime.substring(0, 10);
				cale.set(Calendar.DATE, afterdays);
				String afterendtime = cale.getTimeInMillis() + "";
				afterendtime = afterendtime.substring(0, 10);
				request.setAttribute("afterbegintime", afterbegintime);
				request.setAttribute("afterendtime", afterendtime);
			}
			Calendar calp = Common.getCalendar(timeoffset);
			calp.setTimeInMillis(Long.valueOf(begin + "000"));
			calp.set(Calendar.MONTH, month - 1);
			int befordays = calcCalenday(calp);
			calp.set(Calendar.DATE, 1);
			String beforbegintime = calp.getTimeInMillis() + "";
			beforbegintime = beforbegintime.substring(0, 10);
			calp.set(Calendar.DATE, befordays);
			String beforendtime = calp.getTimeInMillis() + "";
			beforendtime = beforendtime.substring(0, 10);
			request.setAttribute("beforendtime", beforendtime);
			request.setAttribute("beforbegintime", beforbegintime);
			request.setAttribute("days", days);
			request.setAttribute("month", month + 1);
			request.setAttribute("year", year);
			sqladd = " and t.dateline >= " + begin + " and t.dateline <= " + end;
		}
		request.setAttribute("begintime", begin);
		request.setAttribute("endtime", end);
		include(request, response,"./forumdata/cache/cache_spacesettings.jsp");
		String fids = ((Map<String,String>)request.getAttribute("spacesettings")).get("infids");
		List<Map<String,String>> bloglist = dataBaseService.executeQuery("SELECT dateline FROM jrun_threads WHERE blog='1' AND authorid='"+uid+"' AND dateline BETWEEN '"+begin+"' AND '"+end+"' AND displayorder>='0'");
		Map<Integer,Map<String,String>> blogs = new HashMap<Integer,Map<String,String>>();
		for(Map<String,String> blog : bloglist) {
			int day = Integer.valueOf(Common.gmdate("dd", Integer.valueOf(blog.get("dateline")), timeoffset));
			int dateline = Integer.valueOf(blog.get("dateline"));
			if(blogs.get(day)!=null){
				blog = blogs.get(day);
				String num = blog.get("num");
				blog.put("num", (Integer.valueOf(num)+1)+"");
			}else{
				blog.put("num", "1");
				blog.put("dateline", (dateline-dateline%86400)+"");
			}
			blogs.put(day, blog);
		}
		request.setAttribute("blogs", blogs);
		String blogcount = "select count(*) from Threads as t where  t.displayorder>=0 and t.authorid = " + uid + " and t.blog = 1 and t.fid in ("+fids+") "+sqladd;
		String bloghql = "select t.*,f.name,p.message,p.pid from jrun_threads as t left join jrun_forums as f on t.fid=f.fid left join jrun_posts as p on t.tid=p.tid where t.authorid = " + uid + " and t.blog = 1 and f.fid in ("+fids+") "+sqladd+" and t.displayorder>=0 and p.first=1 order by t.lastpost desc";
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		Map spacemap = dataParse.characterParse(settings.get("spacedata"),false);
		int messagelength = convertInt(spacemap.get("textlength").toString());
		int size = threadService.findThreadCountByHql(blogcount);
		String currpage = request.getParameter("page");
		int pages = 1;
		if (currpage != null) {
			pages = Integer.valueOf(currpage);
		}
		int pagesize = 10;
		LogPage logpage = new LogPage(size, pagesize, pages);
		int beginsize = (pages - 1) * pagesize;
		if (beginsize > size) {
			beginsize = size;
		}
		request.setAttribute("logpage", logpage);
		String dateformat = (String)session.getAttribute("dateformat");
		SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat(dateformat, timeoffset);
		List list = new ArrayList();
		List<Map<String,String>> blogthread = dataBaseService.executeQuery(bloghql+" limit "+beginsize+","+pagesize);
		if (blogthread != null && blogthread.size()>0) {
			for (Map<String,String> thread : blogthread) {
				SpaceVO spacevo = new SpaceVO();
				spacevo.setSpecial(thread.get("special"));
				spacevo.setPrice(thread.get("price"));
				spacevo.setFid(Common.toDigit(thread.get("fid")));
				spacevo.setForums(thread.get("name"));
				String message = thread.get("message");
				spacevo.setPid(Common.toDigit(thread.get("pid")));
				if (message.length() > messagelength) {
					message = message.substring(0, messagelength) + "...";
				}
				spacevo.setMessage(message);
				spacevo.setSubject(thread.get("subject"));
				spacevo.setTid(Common.toDigit(thread.get("tid")));
				String datetime = Common.gmdate(simpleDateFormat, Common.toDigit(thread.get("dateline")));
				spacevo.setOperdate(datetime);
				spacevo.setViewnum(Common.toDigit(thread.get("views")));
				spacevo.setReplicenum(Common.toDigit(thread.get("replies")));
				if (thread.get("attachment").equals("1")) {
					spacevo.setIsattc(true);
				} else {
					spacevo.setIsattc(false);
				}
				if (Common.toDigit(thread.get("lastpost"))> member.getLastvisit()) {
					spacevo.setIsnew(true);
				} else {
					spacevo.setIsnew(false);
				}
				list.add(spacevo);
			}
		}
		String bloghqlto5 = "from Threads as t where t.authorid = " + uid + " and t.blog = 1 and t.displayorder>=0 and t.fid in ("+fids+") order by t.views desc";
		List<Threads> blogthreads = threadService.findThreadsByHql(bloghqlto5,0, 5);
		String blogrepto5 = "from Threads as t where t.authorid = " + uid + " and t.blog = 1 and t.displayorder>=0 and t.fid in ("+fids+") order by t.lastpost desc";
		List<Threads> blogthreadr = threadService.findThreadsByHql(blogrepto5,0, 5);
		request.setAttribute("myblogs", list);
		request.setAttribute("repblog", blogthreadr);
		request.setAttribute("blogthread", blogthreads);
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
		Memberspaces memberspace = spaceServer.findMemberspace(uid);
		if(memberspace==null){
			request.setAttribute("errorInfo", getMessage(request, "space_no_unseal"));
			return mapping.findForward("showMessage");
		}
		String layout = memberspace.getLayout();
		Map menuMap = getMenuMap(layout);
		request.setAttribute("menuMap", menuMap);
		UserInfoVO userinfo = new UserInfoVO();
		userinfo.setUsername(member.getUsername());
		userinfo.setAvoras(memberfild.getAvatar());
		String bio = memberfild.getBio();
		String[] bios = bio.split("\t");
		if(bios!=null && bios.length>0){
			MessageResources mr = getResources(request);
			Locale locale = getLocale(request);
			List<Map<String,String>> userg = dataBaseService.executeQuery("select allowbiobbcode,allowbioimgcode from jrun_usergroups where groupid="+member.getGroupid());
			userinfo.setBios(replacebio(bios[0],userg,mr,locale));
		}else{
			userinfo.setBios("");
		}
		userinfo.setWidth(memberfild.getAvatarwidth());
		userinfo.setHeight(memberfild.getAvatarheight());
		userinfo.setIsonline(spaceServer.findSessionByUid(uid));
		request.setAttribute("userinfo", userinfo);
		request.setAttribute("member", member);
		request.setAttribute("memberspace", memberspace);
		return mapping.findForward("tomyblogs");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMythreads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String suid = request.getParameter("uid"); 
		int uid = convertInt(suid);
		Members member = memberService.findMemberById(uid);
		if(member==null){
			request.setAttribute("errorInfo", getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(isBanMember(member)){
			Common.requestforward(response, "space.jsp?action=viewpro&uid="+uid);
			return null;
		}
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		request.setAttribute("bember", member);
		request.setAttribute("memberfild", memberfild);
		include(request, response,"./forumdata/cache/cache_spacesettings.jsp");
		String fids = ((Map<String,String>)request.getAttribute("spacesettings")).get("infids");
		String blogcount = "select count(*) count from jrun_mythreads as m INNER join jrun_threads as t on m.tid=t.tid   where m.uid = " + uid + " and t.special<>3 and t.fid in ("+fids+") and t.displayorder>=0 and t.blog=0 ";
		int size = Common.toDigit(dataBaseService.executeQuery(blogcount).get(0).get("count"));
		String currpage = request.getParameter("page");
		int pages = 1;
		if (currpage != null) {
			pages = Integer.valueOf(currpage);
		}
		int pagesize = 10;
		LogPage logpage = new LogPage(size, pagesize, pages);
		int beginsize = (pages - 1) * pagesize;
		if (beginsize > size) {
			beginsize = size;
		}
		request.setAttribute("logpage", logpage);
		String bloghql = "select t.*,f.name from jrun_mythreads as m INNER join jrun_threads as t on m.tid=t.tid INNER join jrun_forums as f on t.fid=f.fid  where m.uid = " + uid + " and t.special<>3 and t.fid in ("+fids+") and t.displayorder>=0 and t.blog=0 order by t.lastpost desc limit "+beginsize+","+pagesize;
		List<Map<String,String>> threadlist = dataBaseService.executeQuery(bloghql);
		List list = new ArrayList();
		if (threadlist != null && threadlist.size()>0) {
			for (Map<String,String>threads  : threadlist) {
				MythreadsVO mythread = new MythreadsVO();
				mythread.setFid(threads.get("fid"));
				mythread.setForums(threads.get("name"));
				mythread.setSpecial(threads.get("special"));
				mythread.setPrice(threads.get("price"));
				mythread.setLastpost(Common.toDigit(threads.get("lastpost")));
				mythread.setLastposter(threads.get("lastposter"));
				mythread.setTid(threads.get("tid"));
				mythread.setSubjcet(threads.get("subject"));
				mythread.setViewnum(Common.toDigit(threads.get("views")));
				mythread.setReplaynum(Common.toDigit(threads.get("replies")));
				if (threads.get("attachment").equals("1")) {
					mythread.setIsattc(true);
				} else {
					mythread.setIsattc(false);
				}
				if (Common.toDigit(threads.get("lastpost")) > member.getLastvisit()) {
					mythread.setIsnew(true);
				} else {
					mythread.setIsnew(false);
				}
				list.add(mythread);
			}
		}
		request.setAttribute("mythread", list);
		Memberspaces memberspace = spaceServer.findMemberspace(uid);
		if(memberspace==null){
			request.setAttribute("errorInfo", getMessage(request, "space_no_unseal"));
			return mapping.findForward("showMessage");
		}
		String layout = memberspace.getLayout();
		Map menuMap = getMenuMap(layout);
		request.setAttribute("menuMap", menuMap);
		UserInfoVO userinfo = new UserInfoVO();
		userinfo.setUsername(member.getUsername());
		userinfo.setAvoras(memberfild.getAvatar());
		String bio = memberfild.getBio();
		String bios[] = bio.split("\t");
		if(bios!=null && bios.length>0){
			MessageResources mr = getResources(request);
			Locale locale = getLocale(request);
			List<Map<String,String>> userg = dataBaseService.executeQuery("select allowbiobbcode,allowbioimgcode from jrun_usergroups where groupid="+member.getGroupid());
			userinfo.setBios(replacebio(bios[0],userg,mr,locale));
		}else{
			userinfo.setBios("");
		}
		userinfo.setWidth(memberfild.getAvatarwidth());
		userinfo.setHeight(memberfild.getAvatarheight());
		userinfo.setIsonline(spaceServer.findSessionByUid(uid));
		request.setAttribute("userinfo", userinfo);
		request.setAttribute("member", member);
		request.setAttribute("memberspace", memberspace);
		return mapping.findForward("tomythreads");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMyreplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String suid = request.getParameter("uid"); 
		int uid = convertInt(suid);
		Members member = memberService.findMemberById(uid);
		if(member==null){
			request.setAttribute("errorInfo", getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(isBanMember(member)){
			Common.requestforward(response, "space.jsp?action=viewpro&uid="+uid);
			return null;
		}
		include(request, response,"./forumdata/cache/cache_spacesettings.jsp");
		String fids = ((Map<String,String>)request.getAttribute("spacesettings")).get("infids");
		String blogcount = "select count(*) count from jrun_myposts as m INNER join jrun_threads as t on m.tid=t.tid  where m.uid="+uid+" and  t.displayorder>=0 and t.fid in ("+fids+") ";
		int size = Common.toDigit(dataBaseService.executeQuery(blogcount).get(0).get("count"));
		String currpage = request.getParameter("page");
		int pages = 1;
		if (currpage != null) {
			pages = Integer.valueOf(currpage);
		}
		int pagesize = 10;
		LogPage logpage = new LogPage(size, pagesize, pages);
		int beginsize = (pages - 1) * pagesize;
		if (beginsize > size) {
			beginsize = size;
		}
		request.setAttribute("logpage", logpage);
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		request.setAttribute("bember", member);
		request.setAttribute("memberfild", memberfild);
		List list = new ArrayList();
		String rephql = "select t.*,f.name from jrun_myposts as m INNER join jrun_threads as t on m.tid=t.tid INNER join jrun_forums as f on t.fid=f.fid where m.uid="+uid+" and  t.displayorder>=0 and t.fid in ("+fids+") order by t.lastpost desc limit "+beginsize+","+pagesize;
		List<Map<String,String>> replythread = dataBaseService.executeQuery(rephql);
		if (replythread != null && replythread.size()>0) {
			for (Map<String,String> thread : replythread) {
				MythreadsVO mythread = new MythreadsVO();
				mythread.setFid(thread.get("fid"));
				mythread.setForums(thread.get("name"));
				mythread.setLastpost(Common.toDigit(thread.get("lastpost")));
				mythread.setLastposter(thread.get("lastposter"));
				mythread.setTid(thread.get("tid"));
				mythread.setSubjcet(thread.get("subject"));
				mythread.setSpecial(thread.get("special"));
				mythread.setPrice(thread.get("price"));
				mythread.setViewnum(Common.toDigit(thread.get("views")));
				mythread.setReplaynum(Common.toDigit(thread.get("replies")));
				if (thread.get("attachment").equals("1")) {
					mythread.setIsattc(true);
				} else {
					mythread.setIsattc(false);
				}
				if (Common.toDigit(thread.get("lastpost")) > member.getLastvisit()) {
					mythread.setIsnew(true);
				} else {
					mythread.setIsnew(false);
				}
				list.add(mythread);
			}
		}
		request.setAttribute("myreplay", list);
		Memberspaces memberspace = spaceServer.findMemberspace(uid);
		if(memberspace==null){
			request.setAttribute("errorInfo", getMessage(request, "space_no_unseal"));
			return mapping.findForward("showMessage");
		}
		String layout = memberspace.getLayout();
		Map menuMap = getMenuMap(layout);
		request.setAttribute("menuMap", menuMap);
		UserInfoVO userinfo = new UserInfoVO();
		userinfo.setUsername(member.getUsername());
		userinfo.setAvoras(memberfild.getAvatar());
		String bio = memberfild.getBio();
		String[] bios = bio.split("\t");
		if(bios!=null && bios.length>0){
			MessageResources mr = getResources(request);
			Locale locale = getLocale(request);
			List<Map<String,String>> userg = dataBaseService.executeQuery("select allowbiobbcode,allowbioimgcode from jrun_usergroups where groupid="+member.getGroupid());
			userinfo.setBios(replacebio(bios[0],userg,mr,locale));
		}else{
			userinfo.setBios("");
		}
		userinfo.setWidth(memberfild.getAvatarwidth());
		userinfo.setHeight(memberfild.getAvatarheight());
		userinfo.setIsonline(spaceServer.findSessionByUid(uid));
		request.setAttribute("userinfo", userinfo);
		request.setAttribute("member", member);
		request.setAttribute("memberspace", memberspace);
		return mapping.findForward("tomyreplay");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMyrewards(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String suid = request.getParameter("uid"); 
		int uid = convertInt(suid);
		Members member = memberService.findMemberById(uid);
		if(member==null){
			request.setAttribute("errorInfo", getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(isBanMember(member)){
			Common.requestforward(response, "space.jsp?action=viewpro&uid="+uid);
			return null;
		}
		Map<String, String> settings = ForumInit.settings;
		int creditstrans = Integer.valueOf(settings.get("creditstrans"));
		Map extcredits = dataParse.characterParse(settings.get("extcredits"),false);
		String unit = "";
		if(creditstrans>0){
			unit = (String) ((Map) extcredits.get(creditstrans)).get("unit");
		}
		include(request, response,"./forumdata/cache/cache_spacesettings.jsp");
		String fids = ((Map<String,String>)request.getAttribute("spacesettings")).get("infids");
		String blogcount = "select count(*) from Threads as t where t.authorid = " + uid + " and t.special = 3 and t.displayorder>=0 and t.fid in ("+fids+") ";
		int size = threadService.findThreadCountByHql(blogcount);
		String currpage = request.getParameter("page");
		int pages = 1;
		if (currpage != null) {
			pages = Integer.valueOf(currpage);
		}
		int pagesize = 10;
		LogPage logpage = new LogPage(size, pagesize, pages);
		int beginsize = (pages - 1) * pagesize;
		if (beginsize > size) {
			beginsize = size;
		}
		request.setAttribute("logpage", logpage);
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		request.setAttribute("bember", member);
		request.setAttribute("memberfild", memberfild);
		List list = new ArrayList();
		String rephql = "select t.*,f.name from jrun_threads as t left join jrun_forums as f on t.fid=f.fid  where t.authorid = " + uid + " and t.special = 3 and t.displayorder>=0 and t.fid in ("+fids+") order by t.lastpost desc limit "+beginsize+","+pagesize;
		List<Map<String,String>> rewardthread = dataBaseService.executeQuery(rephql);
		if (rewardthread != null && rewardthread.size()>0) {
			for (Map<String,String> t : rewardthread) {
				MythreadsVO mythread = new MythreadsVO();
				mythread.setFid(t.get("fid"));
				mythread.setForums(t.get("name"));
				mythread.setTid(t.get("tid"));
				mythread.setSubjcet(t.get("subject"));
				mythread.setRewards(t.get("price")+ unit);
				mythread.setSpecial(t.get("special"));
				mythread.setPrice(t.get("price"));
				list.add(mythread);
			}
		}
		request.setAttribute("myreward", list);
		Memberspaces memberspace = spaceServer.findMemberspace(uid);
		if(memberspace==null){
			request.setAttribute("errorInfo", getMessage(request, "space_no_unseal"));
			return mapping.findForward("showMessage");
		}
		String layout = memberspace.getLayout();
		Map menuMap = getMenuMap(layout);
		request.setAttribute("menuMap", menuMap);
		UserInfoVO userinfo = new UserInfoVO();
		userinfo.setUsername(member.getUsername());
		userinfo.setAvoras(memberfild.getAvatar());
		String bio = memberfild.getBio();
		String[] bios = bio.split("\t");
		if(bios!=null && bios.length>0){
			MessageResources mr = getResources(request);
			Locale locale = getLocale(request);
			List<Map<String,String>> userg = dataBaseService.executeQuery("select allowbiobbcode,allowbioimgcode from jrun_usergroups where groupid="+member.getGroupid());
			userinfo.setBios(replacebio(bios[0],userg,mr,locale));
		}else{
			userinfo.setBios("");
		}
		userinfo.setWidth(memberfild.getAvatarwidth());
		userinfo.setHeight(memberfild.getAvatarheight());
		userinfo.setIsonline(spaceServer.findSessionByUid(uid));
		request.setAttribute("userinfo", userinfo);
		request.setAttribute("member", member);
		request.setAttribute("memberspace", memberspace);
		return mapping.findForward("tomyreward");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMytrades(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String suid = request.getParameter("uid"); 
		int uid = convertInt(suid);
		Members member = memberService.findMemberById(uid);
		if(member==null){
			request.setAttribute("errorInfo", getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(isBanMember(member)){
			Common.requestforward(response, "space.jsp?action=viewpro&uid="+uid);
			return null;
		}
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		Memberspaces memberspace = spaceServer.findMemberspace(uid);
		request.setAttribute("bember", member);
		request.setAttribute("memberfild", memberfild);
		if(memberspace==null){
			request.setAttribute("errorInfo", getMessage(request, "space_no_unseal"));
			return mapping.findForward("showMessage");
		}
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		Map<String, String> settings = ForumInit.settings;
		String layout = memberspace.getLayout();
		Map menuMap = getMenuMap(layout);
		request.setAttribute("menuMap", menuMap);
		String bio = memberfild.getBio();
		MessageResources mr = getResources(request);
		Locale locale = getLocale(request);
		String[] bios = bio.split("\t");
		if (bios!=null && bios.length > 1) {
			bios[1] = jspcode.parseJsprunCode(bios[1],mr,locale);
			bios[1] = jspcode.parseimg(bios[1], true);
			request.setAttribute("tradeinfo", bios[1]);
		}
		UserInfoVO userinfo = new UserInfoVO();
		userinfo.setUsername(member.getUsername());
		userinfo.setAvoras(memberfild.getAvatar());
		if(bios!=null && bios.length>0){
			List<Map<String,String>> userg = dataBaseService.executeQuery("select allowbiobbcode,allowbioimgcode from jrun_usergroups where groupid="+member.getGroupid());
			userinfo.setBios(replacebio(bios[0],userg,mr,locale));
		}else{
			userinfo.setBios("");
		}
		userinfo.setWidth(memberfild.getAvatarwidth());
		userinfo.setHeight(memberfild.getAvatarheight());
		userinfo.setIsonline(spaceServer.findSessionByUid(uid));
		request.setAttribute("userinfo", userinfo);
		request.setAttribute("member", member);
		request.setAttribute("memberspace", memberspace);
		String tradetypeid = request.getParameter("tradetypeid");
		String sqladd = "";
		if(tradetypeid ==null || tradetypeid.equals("stick")){
			sqladd = " and t.displayorder > 0 ";
		}else if(tradetypeid.equals("0")){
			sqladd = " and t.typeid = 0 ";
		}
		Map<String,String> ftpmap = dataParse.characterParse(settings.get("ftp"), false);
		String ftpurl = ftpmap.get("attachurl");
		ftpmap = null;
		String sql = "select t.*,a.isimage,a.attachment,a.thumb,a.remote from jrun_trades t LEFT JOIN jrun_attachments as a ON t.aid=a.aid where  t.sellerid = " + uid+ " "+sqladd+" order by t.dateline DESC";
		List<Map<String,String>> trades = dataBaseService.executeQuery(sql);
		for(Map<String,String> trade:trades){
			if(!trade.get("expiration").equals("0")){
				float expiration = ((float)Common.toDigit(trade.get("expiration"))-timestamp)/86400;
				if(expiration>0){
					trade.put("expiration", (int)Math.floor(expiration)+"");
				}else{
					trade.put("expiration", "-1");
				}
			}
			if(trade.get("isimage")!=null&&trade.get("isimage").equals("1")){
				String url = trade.get("remote").equals("1")?ftpurl:settings.get("attachurl");
				url = url+"/"+trade.get("attachment");
				url = trade.get("thumb").equals("1")?url+".thumb.jpg":url;
				trade.put("attachment", url);
			}else{
				Map<String,String> styles = (Map<String,String>)request.getAttribute("styles");
				trade.put("attachment", ""+styles.get("IMGDIR")+"/trade_nophoto.gif");
			}
		}
		request.setAttribute("trades", trades);
		if(tradetypeid==null){
			include(request, response,"./forumdata/cache/cache_spacesettings.jsp");
			String fids = ((Map<String,String>)request.getAttribute("spacesettings")).get("infids");
			String blogcount = "select count(*) from Threads as t where t.authorid = " + uid + " and t.special = 2 and t.displayorder>=0 and t.fid in ("+fids+") ";
			int size = threadService.findThreadCountByHql(blogcount);
			String currpage = request.getParameter("page");
			int pages = 1;
			if (currpage != null) {
				pages = Integer.valueOf(currpage);
			}
			int pagesize = 10;
			LogPage logpage = new LogPage(size, pagesize, pages);
			int beginsize = (pages - 1) * pagesize;
			if (beginsize > size) {
				beginsize = size;
			}
			request.setAttribute("logpage", logpage);
			List list = new ArrayList();
			String rephql = "from Threads as t where t.authorid = " + uid + " and t.special = 2  and t.displayorder>=0 and t.fid in ("+fids+") order by t.lastpost desc";
			List<Threads> threads = threadService.findThreadsByHql(rephql, beginsize, pagesize);
			if (trades != null) {
				for (Threads t : threads) {
					MythreadsVO mythread = new MythreadsVO();
					Forums foum = forumService.findById(t.getFid());
					mythread.setFid(t.getFid() + "");
					if (foum != null) {
						mythread.setForums(foum.getName());
					}
					mythread.setTid(t.getTid() + "");
					mythread.setSubjcet(t.getSubject());
					mythread.setViewnum(t.getViews());
					mythread.setReplaynum(t.getReplies());
					list.add(mythread);
				}
			}
			request.setAttribute("mytrades", list);
		}
		return mapping.findForward("totrades");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMyfavforums(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String suid = request.getParameter("uid"); 
		int uid = convertInt(suid);
		Members member = memberService.findMemberById(uid);
		if(member==null){
			request.setAttribute("errorInfo", getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(isBanMember(member)){
			Common.requestforward(response, "space.jsp?action=viewpro&uid="+uid);
			return null;
		}
		include(request, response,"./forumdata/cache/cache_spacesettings.jsp");
		String fids = ((Map<String,String>)request.getAttribute("spacesettings")).get("infids");
		String blogcount = "select count(*) from Favorites as f where f.id.uid = " + uid + " and f.id.fid <> 0 and f.id.fid in ("+fids+") ";
		int size = spaceServer.findFavoritesCountByHql(blogcount);
		String currpage = request.getParameter("page");
		int pages = 1;
		if (currpage != null) {
			pages = Integer.valueOf(currpage);
		}
		int pagesize = 10;
		LogPage logpage = new LogPage(size, pagesize, pages);
		int beginsize = (pages - 1) * pagesize;
		if (beginsize > size) {
			beginsize = size;
		}
		request.setAttribute("logpage", logpage);
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		request.setAttribute("bember", member);
		request.setAttribute("memberfild", memberfild);
		List<Map<String,String>> myfavform = dataBaseService.executeQuery("select f.fid,f.name,f.threads,f.posts,f.todayposts from jrun_favorites as v left join jrun_forums as f on f.fid=v.fid where v.uid="+uid+" and v.fid<>0 and v.fid in ("+fids+") limit "+beginsize+","+pagesize);
		request.setAttribute("myfavforums", myfavform);
		Memberspaces memberspace = spaceServer.findMemberspace(uid);
		if(memberspace==null){
			request.setAttribute("errorInfo", getMessage(request, "space_no_unseal"));
			return mapping.findForward("showMessage");
		}
		String layout = memberspace.getLayout();
		Map menuMap = getMenuMap(layout);
		request.setAttribute("menuMap", menuMap);
		UserInfoVO userinfo = new UserInfoVO();
		userinfo.setUsername(member.getUsername());
		userinfo.setAvoras(memberfild.getAvatar());
		String bio = memberfild.getBio();
		String bios[] = bio.split("\t");
		if(bios!=null && bios.length>0){
			MessageResources mr = getResources(request);
			Locale locale = getLocale(request);
			List<Map<String,String>> userg = dataBaseService.executeQuery("select allowbiobbcode,allowbioimgcode from jrun_usergroups where groupid="+member.getGroupid());
			userinfo.setBios(replacebio(bios[0],userg,mr,locale));
		}else{
			userinfo.setBios("");
		}
		userinfo.setWidth(memberfild.getAvatarwidth());
		userinfo.setHeight(memberfild.getAvatarheight());
		userinfo.setIsonline(spaceServer.findSessionByUid(uid));
		request.setAttribute("userinfo", userinfo);
		request.setAttribute("member", member);
		request.setAttribute("memberspace", memberspace);
		return mapping.findForward("tomyfavforums");
	}
	@SuppressWarnings("unchecked")
	public ActionForward toMyfavthreads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String suid = request.getParameter("uid"); 
		int uid = convertInt(suid);
		Members member = memberService.findMemberById(uid);
		if(member==null){
			request.setAttribute("errorInfo", getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if(isBanMember(member)){
			Common.requestforward(response, "space.jsp?action=viewpro&uid="+uid);
			return null;
		}
		include(request, response,"./forumdata/cache/cache_spacesettings.jsp");
		String fids = ((Map<String,String>)request.getAttribute("spacesettings")).get("infids");
		String blogcount = "select count(*) from Favorites as f where f.id.uid = "+ uid + " and f.id.tid <> 0 and f.id.fid in ("+fids+") ";
		int size = spaceServer.findFavoritesCountByHql(blogcount);
		String currpage = request.getParameter("page");
		int pages = 1;
		if (currpage != null) {
			pages = Integer.valueOf(currpage);
		}
		int pagesize = 10;
		LogPage logpage = new LogPage(size, pagesize, pages);
		int beginsize = (pages - 1) * pagesize;
		if (beginsize > size) {
			beginsize = size;
		}
		request.setAttribute("logpage", logpage);
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		request.setAttribute("bember", member);
		request.setAttribute("memberfild", memberfild);
		List list = new ArrayList();
		List<Map<String,String>> myfavform = dataBaseService.executeQuery("select t.*,f.name from jrun_favorites as v left join jrun_threads as t on t.tid=v.tid left join jrun_forums as f on t.fid=f.fid where v.uid="+uid+" and v.tid<>0 and v.fid in ("+fids+") limit "+beginsize+","+pagesize);
		if (myfavform != null && myfavform.size()>0) {
			for (Map<String,String> f : myfavform) {
				MythreadsVO mythread = new MythreadsVO();
				mythread.setFid(f.get("fid"));
				mythread.setForums(f.get("name"));
				mythread.setLastpost(Common.toDigit(f.get("lastpost")));
				mythread.setLastposter(f.get("lastposter"));
				mythread.setReplaynum(Common.toDigit(f.get("replies")));
				mythread.setSubjcet(f.get("subject"));
				mythread.setTid(f.get("tid"));
				mythread.setSpecial(f.get("special"));
				mythread.setPrice(f.get("price"));
				mythread.setViewnum(Common.toDigit(f.get("views")));
				if ("1".equals(f.get("attachment"))) {
					mythread.setIsattc(true);
				} else {
					mythread.setIsattc(false);
				}
				if (Common.toDigit(f.get("lastpost")) > member.getLastvisit()) {
					mythread.setIsnew(true);
				} else {
					mythread.setIsnew(false);
				}
				list.add(mythread);
			}
		}
		request.setAttribute("myfavthreads", list);
		Memberspaces memberspace = spaceServer.findMemberspace(uid);
		if(memberspace==null){
			request.setAttribute("errorInfo", getMessage(request, "space_no_unseal"));
			return mapping.findForward("showMessage");
		}
		String layout = memberspace.getLayout();
		Map menuMap = getMenuMap(layout);
		request.setAttribute("menuMap", menuMap);
		UserInfoVO userinfo = new UserInfoVO();
		userinfo.setUsername(member.getUsername());
		userinfo.setAvoras(memberfild.getAvatar());
		String bio = memberfild.getBio();
		String[] bios = bio.split("\t");
		if(bios!=null && bios.length>0){
			MessageResources mr = getResources(request);
			Locale locale = getLocale(request);
			List<Map<String,String>> userg = dataBaseService.executeQuery("select allowbiobbcode,allowbioimgcode from jrun_usergroups where groupid="+member.getGroupid());
			userinfo.setBios(replacebio(bios[0],userg,mr,locale));
		}else{
			userinfo.setBios("");
		}
		userinfo.setWidth(memberfild.getAvatarwidth());
		userinfo.setHeight(memberfild.getAvatarheight());
		userinfo.setIsonline(spaceServer.findSessionByUid(uid));
		request.setAttribute("userinfo", userinfo);
		request.setAttribute("member", member);
		request.setAttribute("memberspace", memberspace);
		return mapping.findForward("tomyfavthreads");
	}
	private int calcCalenday(Calendar cale) {
		int[] mouth1 = { 1, 3, 5, 7, 8, 10, 12 };
		int[] mouth2 = { 4, 6, 9, 11 };
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH) + 1;
		for (int i = 0; i < mouth1.length; i++) {
			if (mouth1[i] == month) {
				return 31;
			}
		}
		for (int i = 0; i < mouth2.length; i++) {
			if (mouth2[i] == month) {
				return 30;
			}
		}
		if (month == 2) {
			if (year % 4 == 0 && year % 100 != 0 && year % 400 == 0) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}
	public ActionForward toCustominfo(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String suid = request.getParameter("uid"); 
		int uid = convertInt(suid);
		Members member = memberService.findMemberById(uid);
		if(member==null){
			Common.setResponseHeader(response);
			try {
				response.getWriter().write(getMessage(request, "member_nonexistence"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		Memberfields memberfild = memberService.findMemberfieldsById(uid);
		Map custominfoMap = customeinfo(request,uid);
		Usergroups usergroup = userGroupService.findUserGroupById(member.getGroupid());
		Map<String,String> settings=ForumInit.settings;
		request.setAttribute("isfounder", !Common.empty(settings.get("forumfounders"))&&Common.isFounder(settings, member));
		request.setAttribute("usergroup", usergroup);
		request.setAttribute("member", member);
		request.setAttribute("memberfild", memberfild);
		request.setAttribute("custominfo", custominfoMap);
		return mapping.findForward("tocustominfo");
	}
	@SuppressWarnings("unchecked")
	private Map<String, List> customeinfo(HttpServletRequest request, int uid) {
		Map<String,String> settings = ForumInit.settings;
		Map resultMap = new HashMap();
		String extcredits = settings.get("extcredits");
		Map<Integer,Map<String,String>> extcreditMap = dataParse.characterParse(extcredits, true);
		HttpSession session = request.getSession();
		String dateformat = (String)session.getAttribute("dateformat");
		String timeoffset= (String)session.getAttribute("timeoffset");
		Set<Integer> extcreditKey = extcreditMap.keySet();
		String extname[] = new String[8];
		String extunit[] = new String[8];
		for(Integer key : extcreditKey){
			Map<String,String> excreditmap = extcreditMap.get(key);
			extname[key-1] = excreditmap.get("title");
			extunit[key-1] = excreditmap.get("unit");
		}
		List<Map<String,String>> profiled = dataBaseService.executeQuery("select fieldid,title from jrun_profilefields where available=1 and invisible=0");
		String profievalue = "";
		StringBuffer profields = new StringBuffer();
		for(Map<String,String> profile:profiled){
			profields.append(",field_"+profile.get("fieldid"));
		}
		List<Map<String,String>> usermaplist = dataBaseService.executeQuery("select m.gender,m.posts,m.digestposts,m.credits,m.extcredits1,m.extcredits2,m.extcredits3,m.extcredits4,m.extcredits5,m.extcredits6,m.extcredits7,m.extcredits8,m.oltime,m.regdate,m.lastactivity,u.readaccess,mf.location"+profields+" from jrun_members as m LEFT JOIN jrun_memberfields as mf ON m.uid=mf.uid LEFT JOIN jrun_usergroups as u ON m.groupid=u.groupid where m.uid="+uid);
		Map<String,String> usermap = usermaplist.get(0);
		for(Map<String,String> profile:profiled){
			profievalue = profievalue+","+profile.get("title")+(usermap.get("field_"+profile.get("fieldid"))==null?"":usermap.get("field_"+profile.get("fieldid")));
		}
		profiled = null;
		StringBuffer customs = new StringBuffer("uid,posts,digest,credits");
		for(Integer key:extcreditKey){
			customs.append(",extcredits"+key);
		}
		customs.append(profields);
		customs.append(",readperm,gender,location,oltime,regtime,lastdate");
		String location = usermap.get("location").equals("") ? "" : getMessage(request, "location")+" " + usermap.get("location");
		byte gender = Byte.valueOf(usermap.get("gender"));
		String genders = "";
		if (gender == 1) {
			genders = getMessage(request, "gender")+" "+getMessage(request, "a_member_edit_gender_male");
		} else if (gender == 2) {
			genders = getMessage(request, "gender")+" "+getMessage(request, "a_member_edit_gender_female");
		}else{
			genders = getMessage(request, "gender")+" "+getMessage(request, "a_member_edit_gender_secret");
		}
		StringBuffer customnamebuffer = new StringBuffer(30);
		customnamebuffer.append(getMessage(request, "a_setting_uid")+" ");
		customnamebuffer.append(uid);
		customnamebuffer.append(","+getMessage(request, "a_setting_posts")+" ");
		customnamebuffer.append(usermap.get("posts"));
		customnamebuffer.append(","+getMessage(request, "digest")+" ");
		customnamebuffer.append(usermap.get("digestposts"));
		customnamebuffer.append(","+getMessage(request, "credits")+" ");
		customnamebuffer.append(usermap.get("credits"));
		for(int i=0;i<8;i++){
			if(extname[i]!=null){
				int temp = i+1;
				customnamebuffer.append(","+extname[i]+usermap.get("extcredits"+temp)+extunit[i]);
			}
		}
		SimpleDateFormat sf = Common.getSimpleDateFormat(dateformat, timeoffset);
		customnamebuffer.append(profievalue);
		customnamebuffer.append(","+getMessage(request, "threads_readperm")+" ");
		customnamebuffer.append(usermap.get("readaccess"));
		customnamebuffer.append(","+genders);
		customnamebuffer.append(","+location);
		customnamebuffer.append(","+getMessage(request, "stats_onlinetime")+" ");
		customnamebuffer.append(usermap.get("oltime")+"&nbsp;&nbsp;"+getMessage(request, "hr")+","+getMessage(request, "a_setting_regtime")+" ");
		customnamebuffer.append(Common.gmdate(sf,Common.intval(usermap.get("regdate"))));
		customnamebuffer.append(","+getMessage(request, "lastvisit_2")+" ");
		customnamebuffer.append(Common.gmdate(sf, Common.intval(usermap.get("lastactivity"))));
		String customauthorinfo = settings.get("customauthorinfo");
		List menulist = new ArrayList();
		Map customMap = dataParse.characterParse(customauthorinfo,false);
		String custom[] = customs.toString().split(",");
		String customvalue[] = customnamebuffer.toString().split(",");
		int size = custom.length;
		if (customMap != null && customMap.get(0) != null) {
			Map<String,Map<String,String>>  customreMap = (Map<String,Map<String,String>>)customMap.get(0);
			for(int i=0;i<size;i++){
				Map<String,String> dismap = customreMap.get(custom[i]);
				if(dismap!=null&&dismap.get("menu")!=null&&!customvalue[i].equals("")){
					menulist.add(customvalue[i]);
				}
			}
		}
		resultMap.put("menu", menulist);
		return resultMap;
	}
	private boolean isBanMember(Members member){
		if(member==null){
			return false;
		}
		short groupid = member.getGroupid();
		if(groupid==4||groupid==5||groupid==6){
			return true;
		}
		return false;
	}
	private boolean isinarray(String ext,List<String> source){
		for(String s:source){
			if(s.equals(ext)){
				return true;
			}
		}
		return false;
	}
	private String replacebio(String bio,List<Map<String,String>>usergroup,MessageResources mr,Locale locale){
		bio = bio.replace("$", Common.SIGNSTRING);
		if(usergroup!=null&&usergroup.size()>0){
			Map<String,String> groupmap = usergroup.get(0);
			if(Common.toDigit(groupmap.get("allowbiobbcode"))>0){
				bio = jspcode.parseJsprunCode(bio,mr,locale);
			}
			if(Common.toDigit(groupmap.get("allowbioimgcode"))>0){
				bio = jspcode.parseimg(bio, true);
			}
		}
		bio = bio.replace(Common.SIGNSTRING, "$");
		return bio;
	}
	private void include(HttpServletRequest request,HttpServletResponse response,String value){
		File file=null;
		try {
			file=new File(JspRunConfig.realPath+value);
			if(!file.exists()){
				Cache.updateCache("spacesettings");
			}
			request.getRequestDispatcher(value).include(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			file=null;
		}
	}
	private Map<String,String> getMenuMap(String layout){
		Map<String,String> menuMap = new HashMap<String,String>();
		if (layout.indexOf("[myblogs]") != -1) {
			menuMap.put("myblogs", "ok");
		}
		if (layout.indexOf("[mythreads]") != -1) {
			menuMap.put("mythreads", "ok");
		}
		if (layout.indexOf("[myreplies]") != -1) {
			menuMap.put("myreplies", "ok");
		}
		if (layout.indexOf("[myrewards]") != -1) {
			menuMap.put("myrewards", "ok");
		}
		if (layout.indexOf("[mytrades]") != -1) {
			menuMap.put("mytrades", "ok");
		}
		if (layout.indexOf("[myfavforums]") != -1) {
			menuMap.put("myfavforums", "ok");
		}
		if (layout.indexOf("[myfavthreads]") != -1) {
			menuMap.put("myfavthreads", "ok");
		}
		return menuMap;
	} 
}