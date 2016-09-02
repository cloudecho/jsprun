package cn.jsprun.struts.foreg.actions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.foreg.vo.Rss_inc;
import cn.jsprun.foreg.vo.Rss_inc.Thread;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.Md5Token;
public class RssAction extends BaseAction {
	private static final String tablepre = "jrun_";
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Rss_inc rss_inc = new Rss_inc();
		Map<String,String> settingMap = (Map<String,String>)servlet.getServletContext().getAttribute("settings");
		String rssstatus = settingMap.get("rssstatus");
		if(rssstatus==null||!rssstatus.equals("1")){
			rss_inc.setNotAccess(true);
		}else{
			String rssttl = settingMap.get("rssttl");
			float ttl = Common.empty(rssttl)?30:Double.valueOf(rssttl).floatValue();
			String num = "20";
			String groupid = "7";
			String extgroupid = "";
			String accessmasks = "";
			String auth_rq = request.getParameter("auth");
			if(!Common.empty(auth_rq)){
				Md5Token md5 = Md5Token.getInstance();
				String temp = Common.authcode(auth_rq, "DECODE", md5.getLongToken(settingMap.get("authkey")),null);
				String[] tempArray = temp.split("\t");
				String uid = tempArray.length>0?tempArray[0]:"0";
				String auth = tempArray.length>2?tempArray[2]:"";
				String sql = "SELECT uid AS jsprun_uid, username AS jsprun_user, password AS jsprun_pw, secques AS jsprun_secques, groupid,extgroupids,accessmasks FROM "+tablepre+"members WHERE uid=?";
				List<Map<String,String>> query = dataBaseService.executeQuery(sql,uid);
				if(query!=null&&query.size()>0){
					Map<String,String> member = query.get(0);
					if(auth.equals(md5.getLongToken(member.get("jsprun_pw")+member.get("jsprun_secques")).substring(0,8))){
						groupid = member.get("groupid");
						extgroupid = member.get("extgroupid");
						accessmasks = member.get("accessmasks");
					}
				}
			}
			HttpSession session = request.getSession();
			int timestamp = (Integer)request.getAttribute("timestamp");
			String timeoffset = (String)session.getAttribute("timeoffset");
			SimpleDateFormat dateFormat = Common.getSimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", timeoffset);
			String nowTime = Common.gmdate(dateFormat, timestamp);
			String boardurl = (String)session.getAttribute("boardurl");
			String bbname = Common.htmlspecialchars(Common.strip_tags(settingMap.get("bbname")));
			String fid_rq = request.getParameter("fid");
			String rssfid = fid_rq==null||fid_rq.equals("") ? "0" : fid_rq;
			Map<String,String> forumsMap = (Map<String,String>)request.getAttribute("forums");
			Map<String,Map<String,String>> forumsMap_catch = dataParse.characterParse(forumsMap.get("forums"), false);
			String forumname = "";
			List<String> fidarray = new ArrayList<String>();
			if(rssfid.equals("0")){
				for(Entry<String, Map<String,String>> entry : forumsMap_catch.entrySet()){
					String fid = entry.getKey();
					Map<String,String> forum = entry.getValue();
					if(rssforumperm(forum, groupid, extgroupid, accessmasks)){
						fidarray.add(fid);
					}
				}
			}else{
				Map<String,String> temp = forumsMap_catch.get(rssfid);
				Map<String,String> forum = temp!=null && !temp.get("type").equals("group") ? temp : null;
				if(forum!=null && rssforumperm(forum, groupid, extgroupid, accessmasks)){
					fidarray.add(rssfid);
					forumname = Common.htmlspecialchars(temp.get("name"));
				}else{
					rss_inc.setForumError(true);
				}
			}
			rss_inc.setAccessIndex(fidarray.size()>1);
			if(fidarray.size()>0){
				StringBuffer fidsBuffer = new StringBuffer();
				for(String fid : fidarray){
					fidsBuffer.append(Short.parseShort(fid));
					fidsBuffer.append(",");
				}
				String fids = fidsBuffer.substring(0, fidsBuffer.length()-1);
				String sql = "SELECT * FROM "+tablepre+"rsscaches WHERE fid IN ("+fids+") ORDER BY dateline DESC LIMIT "+num;
				List<Map<String,String>> query = dataBaseService.executeQuery(sql);
				if(query == null || query.size() == 0 
						|| (timestamp - Integer.parseInt(query.get(0).get("lastupdate"))) > ttl * 60){
					updatersscache(forumsMap_catch, timestamp, num,ttl);
					query = dataBaseService.executeQuery(sql);
				}
				if(query!=null&&query.size()>0){
					List<Thread> threadList = rss_inc.getThreadList();
					for(Map<String,String> thread : query){
						Thread thread_vo = rss_inc.getThread();
						String dateline = thread.get("dateline");
						thread_vo.setAuthor(Common.htmlspecialchars(thread.get("author")));
						thread_vo.setDateline(dateFormat.format(Long.parseLong(dateline)*1000));
						thread_vo.setDescription(thread.get("description"));
						thread_vo.setForum(Common.htmlspecialchars(thread.get("forum")));
						thread_vo.setSubject(Common.htmlspecialchars(thread.get("subject")));
						thread_vo.setTid(thread.get("tid"));
						threadList.add(thread_vo);
					}
				}
			}
			rss_inc.setBbname(bbname);
			rss_inc.setBoardurl(boardurl);
			rss_inc.setForumname(forumname);
			rss_inc.setIndexname(settingMap.get("indexname"));
			rss_inc.setNowTime(nowTime);
			rss_inc.setNum(num);
			rss_inc.setRssfid(rssfid);
			rss_inc.setTtl(ttl+"");
		}
		request.setAttribute("valueObject", rss_inc);
		return mapping.findForward("rss_inc");
	}
	private synchronized void  updatersscache(Map<String,Map<String,String>> forumsMap_catch,int timestamp,String num,float ttl){
		String sql = "SELECT lastupdate FROM "+tablepre+"rsscaches LIMIT 1";
		List<Map<String,String>> query = dataBaseService.executeQuery(sql);
		if(query==null||query.size()==0||timestamp - Integer.parseInt(query.get(0).get("lastupdate")) > ttl * 60){
			dataBaseService.runQuery("DELETE FROM "+tablepre+"rsscaches");
			for(Entry<String, Map<String,String>> entry : forumsMap_catch.entrySet()){
				String fid = entry.getKey();
				Map<String,String> forum = entry.getValue();
				if(forum!=null&&!"group".equals(forum.get("type"))){
					sql = "SELECT t.tid, t.readperm, t.price, t.author, t.dateline, t.subject, p.message " +
							"FROM "+tablepre+"threads t " +
							"LEFT JOIN "+tablepre+"posts p ON p.tid=t.tid AND p.first='1' " +
							"WHERE t.fid='"+fid+"' AND t.displayorder>='0' " +
							"ORDER BY t.dateline DESC LIMIT "+num;
					query = dataBaseService.executeQuery(sql);
					for(Map<String,String> thread : query){
						String name = Common.addslashes(forum.get("name"));
						String author = thread.get("author");
						author = !author.equals("")?Common.addslashes(author) : "Anonymous";
						String subject = Common.addslashes(thread.get("subject"));
						String readperm = thread.get("readperm");
						String price = thread.get("price");
						String description = "";
						if(!((readperm!=null && Integer.parseInt(readperm) > 0 )||(price!=null && Integer.parseInt(price)>0))){
							String message = thread.get("message");
							String reg = "<[^\">]*(\"[^\"]*\"[^\">]*)*>";
							if(message!=null){
								description = Common.addslashes(Common.cutstr(Common.htmlspecialchars(message.replaceAll("\n|\r","<br />").replaceAll(reg, "").replaceAll("(\\[.+\\])", "")), 255, ""));
							}
						}
						sql = "INSERT INTO "+tablepre+"rsscaches (lastupdate, fid, tid, dateline, forum, author, subject, description) VALUES ('"+timestamp+"', '"+fid+"', '"+thread.get("tid")+"', '"+thread.get("dateline")+"', '"+name+"', '"+author+"', '"+subject+"', '"+description+"')";
						dataBaseService.execute(sql);
					}
				}
			}
		}
	}
	private boolean rssforumperm(Map<String,String> forum,String groupid,String extgroupid,String accessmasks){
		String type = forum.get("type");
		String viewperm = forum.get("viewperm");
		return !type.equals("group") && (Common.empty(viewperm) || Common.forumperm(viewperm, Short.valueOf(groupid), extgroupid) || !Common.empty(accessmasks));
	}
}