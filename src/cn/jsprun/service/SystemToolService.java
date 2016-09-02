package cn.jsprun.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cn.jsprun.dao.AttachmentsDao;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.dao.ForumsDao;
import cn.jsprun.dao.MembersDao;
import cn.jsprun.dao.PostsDao;
import cn.jsprun.dao.SessionsDao;
import cn.jsprun.dao.SettingsDao;
import cn.jsprun.dao.ThreadsDao;
import cn.jsprun.domain.Attachments;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Posts;
import cn.jsprun.domain.Settings;
import cn.jsprun.domain.Threads;
import cn.jsprun.utils.BeanFactory;
public class SystemToolService {
	public List<Settings> findSettingsLikeVariable(String variable) {
		return ((SettingsDao) BeanFactory .getBean("settingDao")).findSettingsLikeVariable(variable);
	}
	public boolean updateSetting(Settings setting) {
		return ((SettingsDao) BeanFactory .getBean("settingDao")).updateSetting(setting);
	}
	public Settings findBySettingvariable(String variable) {
		return ((SettingsDao) BeanFactory .getBean("settingDao")).findBySettingvariable(variable);
	}
	public boolean saveSetting(Settings setting) {
		return ((SettingsDao) BeanFactory .getBean("settingDao")).saveSetting(setting);
	}
	public boolean removeSetting(Settings setting) {
		return ((SettingsDao) BeanFactory .getBean("settingDao")).removeSetting(setting);
	}
	public List<Threads> findThreadsByHql(String hql, int start, int maxrow) {
		return ((ThreadsDao) BeanFactory .getBean("threadsDao")).findThreadsByHql(hql, start, maxrow);
	}
	public List<Threads> findThreadsByHqlTwo(String hql, int start, int maxrow) {
		return ((ThreadsDao) BeanFactory .getBean("threadsDao")).findThreadsByHqlTwo(hql, start, maxrow);
	}
	public Posts findPostByThreadId(int tid) {
		return ((PostsDao) BeanFactory.getBean("postsDao")).findPostByThreadId(tid);
	}
	public Forums findById(Short fid) {
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).findById(fid);
	}
	public List<Forums> findFourmsByHql(String hql, int start, int maxrow) {
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).findFourmsByHql(hql, start, maxrow);
	}
	public List<Members> findMembersByHql(String hql, int startrow, int maxrows) {
		return ((MembersDao) BeanFactory .getBean("memberDao")).findMembersByHql(hql, startrow, maxrows);
	}
	public int findFourmsCount() {
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).findFourmsCount();
	}
	public int findThreadCount() {
		return ((ThreadsDao) BeanFactory .getBean("threadsDao")).findThreadCount();
	}
	public int findPostCount() {
		return ((PostsDao) BeanFactory.getBean("postsDao")).findPostCount();
	}
	public int findSessionsCountByType(boolean members) {
		return ((SessionsDao) BeanFactory .getBean("sessionDao")).findSessionsCountByType(members);
	}
	public int findMembersCount() {
		return ((MembersDao) BeanFactory .getBean("memberDao")).findMembersCount();
	}
	public List<Attachments> findAttchmentsByJs(String hql, int startrow, int maxrow) {
		return ((AttachmentsDao) BeanFactory .getBean("attachmentsDao")).findAttchmentsByJs(hql, startrow, maxrow);
	}
	public Threads findThreadsBytid(int tid) {
		return ((ThreadsDao) BeanFactory .getBean("threadsDao")).findThreadsBytid(tid);
	}
	public int forumpost(int current, int pertask,int processed) {
		DataBaseDao dataBaseDao = (DataBaseDao) BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>> forumslist = dataBaseDao.executeQuery("select fid from jrun_forums where type<>'group' limit "+current+","+pertask);
		if (forumslist != null && forumslist.size() > 0) {
			processed = 1;
			for(Map<String,String> forums:forumslist){	
				Map<String,String> counts = dataBaseDao.executeQuery("SELECT COUNT(*) AS threads, SUM(replies)+COUNT(*) AS posts FROM jrun_threads WHERE fid='"+forums.get("fid")+"' AND displayorder>='0'").get(0);
				String forumlastpost = "";
				List<Map<String,String>> threadslist = dataBaseDao.executeQuery("SELECT tid, subject, lastpost, lastposter FROM jrun_threads WHERE fid='"+forums.get("fid")+"' AND displayorder>='0' ORDER BY lastpost DESC LIMIT 1");
				if(threadslist.size()>0){
					Map<String,String>threads = threadslist.get(0);
					forumlastpost = threads.get("tid") + "\t"+ threads.get("subject") + "\t" + threads.get("lastpost")+ "\t" + threads.get("lastposter");
				}
				dataBaseDao.runQuery("update jrun_forums set posts="+(counts.get("posts")==null?0:counts.get("posts"))+",threads="+counts.get("threads")+",lastpost='"+forumlastpost+"' where fid="+forums.get("fid"));
			}
		}
		forumslist = null;
		dataBaseDao=null;
		return processed;
	}
	public int memberdigestposts(int current, int pertask,int processed) {
		DataBaseDao dataBaseDao=(DataBaseDao)BeanFactory.getBean("dataBaseDao");
		if(current==0){
			dataBaseDao.runQuery("update jrun_members set digestposts = 0");
		}
		List<Map<String,String>> threadlist = dataBaseDao.executeQuery("select authorid from jrun_threads where digest<>0 and displayorder>=0 limit "+current+","+pertask);
		if (threadlist != null && threadlist.size() > 0) {
			processed = 1;
			for(Map<String,String> thread:threadlist)
			{
				dataBaseDao.runQuery("update jrun_members set digestposts=digestposts+1 where uid = '"+thread.get("authorid")+"'");
			}
		}
		dataBaseDao=null;threadlist=null;
		return processed;
	}
	public int memberposts(int current, int pertask,int processed) {
		DataBaseDao dataBaseDao=(DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>> memberlist = dataBaseDao.executeQuery("select uid from jrun_members limit "+current+","+pertask);
		if (memberlist != null && memberlist.size() > 0) {
			processed = 1;
			for(Map<String,String> member : memberlist){
				dataBaseDao.runQuery("update jrun_members set posts=(select count(*) from jrun_posts as p where p.authorid = "+member.get("uid")+" and p.invisible='0') where uid="+member.get("uid"));
			}
		}
		dataBaseDao=null;memberlist=null;
		return processed;
	}
	public int threadposts(int current, int pertask,int processed) {
		DataBaseDao dataBaseDao=(DataBaseDao)BeanFactory.getBean("dataBaseDao");
		PostsDao postsDao=(PostsDao) BeanFactory.getBean("postsDao");
		List<Map<String,String>>threadlist = dataBaseDao.executeQuery("select tid from jrun_threads where displayorder>=0 limit "+current+","+pertask);
		if (threadlist != null && threadlist.size() > 0) {
			processed = 1;
			for(Map<String,String>thread: threadlist){
				int postnum = postsDao.findPostCountByHql("select count(*) from Posts as p where p.tid = "+ thread.get("tid") + " and p.invisible='0'");
				postnum--;
				if(postnum<0){
					postnum = 0;
				}
				dataBaseDao.runQuery("update jrun_threads set replies="+postnum+" where tid = "+thread.get("tid"));
			}
		}
		dataBaseDao=null;
		postsDao=null;threadlist=null;
		return processed;
	}
	public int movedthreads(int current, int pertask,int processed) {
		DataBaseDao dataBaseDao=(DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>> threadlist = dataBaseDao.executeQuery("SELECT t1.tid, t2.tid AS threadexists FROM jrun_threads t1 LEFT JOIN jrun_threads t2 ON t2.tid=t1.closed AND t2.displayorder>='0' WHERE t1.closed>'0' LIMIT "+current+", "+pertask);
		if (threadlist != null && threadlist.size() > 0) {
			processed = 1;
			StringBuffer tids = new StringBuffer("0");
			for(Map<String,String>threads:threadlist){
				if(!threads.get("threadexists").equals("0")){
					tids.append(","+threads.get("tid"));
				}
			}
			dataBaseDao.runQuery("delete from jrun_threads where tid in ("+tids.toString()+")");
		}
		threadlist=null;dataBaseDao=null;
		return processed;
	}
	public int cleanupthreads(int current, int pertask,int processed) {
		DataBaseDao dataBaseDao=(DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>>favoritelist = dataBaseDao.executeQuery("select tid,fid from jrun_favorites limit "+current+","+pertask);
		if (favoritelist != null && favoritelist.size() > 0) {
			processed = 1;
			List<String> tids = new ArrayList<String>();
			List<String> fids = new ArrayList<String>();
			for(Map<String,String> favorite:favoritelist){
				if(!favorite.get("tid").equals("0")){
					tids.add(favorite.get("tid"));
				}else if(!favorite.get("fid").equals("0")){
					fids.add(favorite.get("fid"));
				}
			}
			for(String tid:tids){
				List<Map<String,String>> threadlist = dataBaseDao.executeQuery("select tid from jrun_threads where tid="+tid);
				if(threadlist.size()<=0){
					dataBaseDao.runQuery("delete from jrun_favorites where tid="+tid);
					dataBaseDao.runQuery("delete from jrun_rewardlog where tid="+tid);
				}
			}
			for(String fid:fids){
				List<Map<String,String>> forumlist = dataBaseDao.executeQuery("select fid from jrun_forums where fid="+fid);
				if(forumlist.size()<=0){
					dataBaseDao.runQuery("DELETE FROM jrun_favorites WHERE fid='"+fid+"'");
				}
			}
		}dataBaseDao=null;favoritelist=null;
		return processed;
	}
}
