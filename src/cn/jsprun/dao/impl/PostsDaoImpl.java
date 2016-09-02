package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.PostsDao;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Posts;
import cn.jsprun.struts.form.ModrepliesPageForm;
import cn.jsprun.struts.form.PageForm;
import cn.jsprun.struts.form.PostsPageForm;
import cn.jsprun.utils.HibernateUtil;
public class PostsDaoImpl implements PostsDao {
	public Posts getPostsById(Integer pid) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Posts posts = (Posts)session.get(Posts.class, pid);
			tr.commit();
			return posts;
		}catch(Exception exception){
			if(tr!=null){
				tr.rollback();
			}
			return null;
		}
	}
	public boolean deletePostsByUserUid(Integer uid) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Posts p where p.authorid = ?");
			query.setInteger(0, uid);
			query.executeUpdate();
			query = session.createQuery("delete from Threads t where t.authorid = ?");
			query.setInteger(0, uid);
			query.executeUpdate();
			query = session.createQuery("delete from Attachments a where a.uid = ?");
			query.setInteger(0, uid);
			query.executeUpdate();
			tr.commit();
			return true;
		}catch(Exception exception){
			exception.printStackTrace();
			if(tr!=null){
				tr.rollback();
			}
			return false;
		}
	}
	public boolean modifyPosts(Posts posts) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.update(posts);
			tr.commit();
			return true;
		}catch(Exception exception){
			exception.printStackTrace();
			if(tr!=null){
				tr.rollback();
			}
			return false;
		}
	}
	public List<Posts> findByUserName(String userName) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Posts p where p.author = ?");
			query.setString(0, userName);
			List<Posts> postList = query.list();
			tr.commit();
			return postList;
		}catch(Exception exception){
			exception.printStackTrace();
			if(tr!=null){
				tr.rollback();
			}
			return null;
		}
	}
	public List<Posts> findByPosts(Posts posts) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Posts p");
			List<Posts> postList = query.list();
			tr.commit();
			return postList;
		}catch(Exception exception){
			if(tr!=null){
				tr.rollback();
			}
			exception.printStackTrace();
			return null;
		}
	}
	public PostsPageForm fidnByForums(short fid,String displayorder) {
		StringBuffer queryStr = new StringBuffer("select t.tid, t.fid, t.author, t.authorid, t.subject, t.dateline,p.pid, p.message, p.useip, p.attachment from jrun_threads t LEFT JOIN jrun_posts p ON p.tid=t.tid WHERE t.displayorder="+ displayorder+" GROUP BY t.tid ");
		StringBuffer countStr = new StringBuffer("select count(*) count from jrun_threads as t where t.displayorder="+ displayorder);
		if (fid > 0) {
			queryStr.append(" and t.fid=" + fid );
			countStr.append(" and t.fid=" + fid );
		}
		queryStr.append(" ORDER BY t.dateline DESC ");
		PostsPageForm ppf = new PostsPageForm(countStr.toString(), queryStr.toString());
		return ppf;
	}
	private void updateSQL(String str) throws Exception {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			 tr = session.beginTransaction();
			Query query = session.createQuery(str);
			query.executeUpdate();
			session.flush();
			tr.commit();
		} catch (Exception e) {
			if (tr != null)
				tr.rollback();
			tr = null;
			e.printStackTrace();
		}
	}
	public List selectSQL(String str) {
		List<Posts> postsList = new ArrayList<Posts>();
		Transaction tr= null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(str);
			postsList = query.list();
			session.flush();
			tr.commit();
		} catch (Exception e) {
			postsList.clear();
			if (tr != null)
				tr.rollback();
			tr = null;
			e.printStackTrace();
		}
		return postsList;
	}
	public ModrepliesPageForm fidnByModreplies(short fid,String invisible) {
		StringBuffer queryStr = new StringBuffer("select p.*,t.subject as threadsubject from jrun_posts as p left join jrun_threads as t on t.tid=p.tid where p.first=0 and p.invisible="+invisible);
		StringBuffer countStr = new StringBuffer("select count(*) count from jrun_posts as p where p.first=0 and p.invisible="+invisible);
			if (fid > 0) {
				queryStr.append(" and p.fid=" + fid );
				countStr.append(" and p.fid=" + fid );
			}
		ModrepliesPageForm mpf = new ModrepliesPageForm(countStr.toString(),queryStr.toString());
		return mpf;
	}
	public Map batchPrune(String sb, boolean b) {
		Map m = new HashMap<String, Integer>();
		Transaction tr = null;
			try {
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				tr = session.beginTransaction();
				if (b == true) {
					StringBuffer showPosts = new StringBuffer(
							"from Posts as p where p.pid in (");
					Object[] tids = sb.split(",");
					for (int i = 0; i < tids.length; i++) {
						showPosts.append(tids[i].toString());
						showPosts.append(",");
					}
					String str = showPosts.substring(0, showPosts.length() - 1);
					str = str + ")";
					Query query = session.createQuery(str);
					List<Posts> postsList = query.list(); 
					session.flush();
					for (int i = 0; i < postsList.size(); i++) {
						Integer num = postsList.get(i).getAuthorid(); 
						Members member = (Members) session.get(Members.class, num);
						member.setPosts(member.getPosts() - 1); 
						member.setCredits(member.getCredits() - 1); 
						String d = member.getBday();
						if (d == null) {
							member.setBday("0000-00-00"); 
						}
						session.update(member);
					}
					session.flush();
				}
				String[] pids = sb.split(",");
				int threads = 0;
				int posts = 0;
				for (int i = 0; i < pids.length; i++) {
					if (pids[i] != null && !pids[i].equals("")) {
						Posts p = (Posts) session.get(Posts.class, Integer
								.valueOf(pids[i]));
						if (p != null) {
							if (p.getFirst() == 1) {
								posts += this.deleteThreads(p.getTid());
								threads++;
							}
							if (p.getFirst() == 0) {
								Query myquery = session
										.createQuery("delete from Posts as p where p.pid = :pid");
								myquery.setInteger("pid", p.getPid());
								posts = myquery.executeUpdate();
								session.flush();
							}
						}
					}
				}
				m.put("threads", threads);
				m.put("posts", posts);
				tr.commit();
			} catch (HibernateException he) {
				if(tr!=null){
					tr.rollback();
				}
				he.printStackTrace();
			} 
		return m;
	}
	private Integer deleteThreads(Integer tid) {
		int ps = 0;
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query myquery = session.createQuery("delete from Posts as p where p.tid = :tid");
			myquery.setInteger("tid", tid);
			ps = myquery.executeUpdate();
			session.flush();
			myquery = session
					.createQuery("delete from Threads as t where t.tid = :tid");
			myquery.setInteger("tid", tid);
			myquery.executeUpdate();
			session.flush();
			tr.commit();
		}catch (Exception e) {
			if(tr!=null){
				tr.rollback();
			}
		}
		return ps;
	}
	public int tableTemplate(StringBuffer querystr, String sbtid)
			throws Exception {
		int num = -1;
		if (sbtid == null || sbtid.equals(""))
			return num;
		querystr.append(" where t.tid in (");
		String[] tids = sbtid.split(",");
		if (tids.length == 1) {
			if (tids[0].trim() == null || tids[0].trim().equals("")) {
				return num;
			}
		}
		for (int i = 0; i < tids.length; i++) {
			querystr.append(tids[i]);
			querystr.append(",");
		}
		String str = querystr.substring(0, querystr.length() - 1);
		str = str + ")";
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query myquery = session.createQuery(str);
			num = myquery.executeUpdate();
			tr.commit();
		} catch (HibernateException he) {
			if(tr!=null){
				tr.rollback();
			}
			he.printStackTrace();
		}
		return num;
	}
	public void deleteForumrecommend(StringBuffer deleteSB) {
		StringBuffer deleteString = new StringBuffer(
				"delete from Forumrecommend as t");
		try {
			this.tableTemplate(deleteString, deleteSB.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Integer updateForumrecommendList(List list) {
		int updateNumber = -1;
		String updateStr = null;
		if (list == null || list.size() <= 0)
			return 0;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				Map m = (Map) list.get(i);
				if (m != null) {
					updateStr = "update Forumrecommend as f set f.displayorder =:displayorder where f.tid = :tid";
					Query query = session.createQuery(updateStr);
					String displayorder = m.get("displayorder").toString();
					try {
						int dis = Integer.valueOf(displayorder);
						if (dis > 127) {
							displayorder = "127";
						}
					} catch (NumberFormatException nfe) {
						displayorder = "127";
					}
					query.setString("displayorder", displayorder);
					query.setInteger("tid", Integer.valueOf(m.get("tid")
							.toString()));
					query.executeUpdate();
				}
			}
			tr.commit();
		} catch (HibernateException he) {
			if(tr!=null){
				tr.rollback();
			}
			he.printStackTrace();
		}
		return updateNumber;
	}
	public boolean deleteModrepliesIDArray(List<String> modrepliesList) {
		StringBuffer sb = new StringBuffer( "delete from Posts as p where p.pid in (");
		StringBuffer sba = new StringBuffer( "delete from Attachments as p where p.pid in (");
		for (int i = 0; i < modrepliesList.size(); i++) {
			sb.append(modrepliesList.get(i) + ",");
			sba.append(modrepliesList.get(i) + ",");
		}
		String strsql = sb.substring(0, sb.length() - 1);
		String strsqla = sba.substring(0, sba.length() - 1);
		strsql = strsql + ")";
		strsqla = strsqla + ")";
		try {
			updateSQL(strsql);
			updateSQL(strsqla);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean ignoreModrepliesIDArray(List<String> modrepliesList) {
		StringBuffer sb = new StringBuffer(
				"update Posts as p set p.invisible=-3 where p.pid in (");
		for (int i = 0; i < modrepliesList.size(); i++) {
			sb.append(modrepliesList.get(i) + ",");
		}
		String strsql = sb.substring(0, sb.length() - 1);
		strsql = strsql + ")";
		try {
			updateSQL(strsql);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean validateModrepliesIDArray(List<Posts> modrepliesList) {
		StringBuffer sb = new StringBuffer("update Posts as p set p.invisible=0 where p.pid in (");
		for (int i = 0; i < modrepliesList.size(); i++) {
			Posts p = modrepliesList.get(i);
			sb.append(p.getPid() + ",");
		}
		String strsql = sb.substring(0, sb.length() - 1);
		strsql = strsql + ")";
		try {
			updateSQL(strsql); 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public List validatePostsIDArray(List validateList) {
		StringBuffer updateThreadsSQL = new StringBuffer(
				"update Threads as t set t.displayorder=0,t.moderated=1 where t.tid in (");
		StringBuffer updatePostsSQL = new StringBuffer(
				"update Posts as p set p.invisible=0 where p.tid in (");
		StringBuffer showSb = new StringBuffer("from Posts as p where p.tid in (");
		for (int i = 0; i < validateList.size(); i++) {
			updateThreadsSQL.append(validateList.get(i) + ",");
			updatePostsSQL.append(validateList.get(i) + ",");
			showSb.append(validateList.get(i) + ",");
		}
		String updateThreadssql = updateThreadsSQL.substring(0,
				updateThreadsSQL.length() - 1);
		updateThreadssql = updateThreadssql + "))";
		String strsql = updatePostsSQL
				.substring(0, updatePostsSQL.length() - 1);
		strsql = strsql + ")";
		String showSQL = showSb.substring(0, showSb.length() - 1);
		showSQL = showSQL + ") AND p.first=1";
		try {
			updateSQL(updateThreadssql);
			updateSQL(strsql);
			List<Posts> postsList = selectSQL(showSQL);
			if (postsList != null && postsList.size() > 0 ) {
				return postsList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean deletePostsIDArray(List deleteList) {
		StringBuffer updateThreadsSQL = new StringBuffer(
				"update Threads as t set t.displayorder=-1,t.moderated=1 where t.tid in (select p.tid from Posts as p where p.pid in (");
		StringBuffer updatePostsSQL = new StringBuffer(
				"update Posts as p set p.invisible=-1 where p.pid in (");
		for (int i = 0; i < deleteList.size(); i++) {
			updateThreadsSQL.append(deleteList.get(i) + ",");
			updatePostsSQL.append(deleteList.get(i) + ",");
		}
		String updateThreadssql = updateThreadsSQL.substring(0,
				updateThreadsSQL.length() - 1);
		updateThreadssql = updateThreadssql + "))";
		String strsql = updatePostsSQL
				.substring(0, updatePostsSQL.length() - 1);
		strsql = strsql + ")";
		try {
			updateSQL(updateThreadssql);
			updateSQL(strsql);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean ignorePostsIDArray(List ignoreList) {
		StringBuffer updateThreadsSQL = new StringBuffer(
				"update Threads as t set t.displayorder=-3,t.moderated=0 where t.tid in (");
		StringBuffer updatePostsSQL = new StringBuffer(
				"update Posts as p set p.invisible=-2 where p.tid in (");
		for (int i = 0; i < ignoreList.size(); i++) {
			updateThreadsSQL.append(ignoreList.get(i) + ",");
			updatePostsSQL.append(ignoreList.get(i) + ",");
		}
		String updateThreadssql = updateThreadsSQL.substring(0,
				updateThreadsSQL.length() - 1);
		updateThreadssql = updateThreadssql + "))";
		String strsql = updatePostsSQL
				.substring(0, updatePostsSQL.length() - 1);
		strsql = strsql + ")";
		try {
			updateSQL(updateThreadssql);
			updateSQL(strsql);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public Posts findByTid(Integer tid) {
		Posts p = null;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Posts as p where p.tid = " + tid
					+ " and p.first =1 ");
			List<Posts> postsList = query.list();
			tr.commit();
			if(postsList.size()>0){
				p = postsList.get(0);
			}
		} catch (HibernateException he) {
			if(tr!=null){
				tr.rollback();
			}
			he.printStackTrace();
		} 
		return p;
	}
	public Posts findPostByThreadId(int tid) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
			tr = session.beginTransaction();
			Query query = session.createQuery("from Posts as p where p.tid=? and first = 1");
			query.setParameter(0, tid);
			List<Posts> list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(tr!= null){
				tr.rollback();
			}
		}
		return null;
	}
	public int findPostCount() {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("select count(*) from Posts");
			List list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return Integer.valueOf(list.get(0)+"");
			}
		}catch(Exception e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public int findPostCountByHql(String hql) {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery(hql);
			List list = query.list();
			tran.commit();
			if(list!=null && list.size()>0){
				return (Integer)list.get(0);
			}
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public List<Posts> getPostsListByPidList(List<Integer> pidList) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			List<Posts> list = new ArrayList<Posts>();
			for(int i = 0;i<pidList.size();i++){
				list.add((Posts)session.get(Posts.class, pidList.get(i)));
			}
			tr.commit();
			return list;
		}catch(Exception e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Map<String,String> getBestmemAndBestmemposts(Integer nowTime){
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			String hql = "SELECT author,COUNT(*) FROM Posts WHERE dateline>=? AND invisible=0 AND authorid>0 GROUP BY author";
			Query query = session.createQuery(hql);
			query.setInteger(0, nowTime);
			List list = query.list();
			tr.commit();
			Iterator iterator = list.iterator();
			Integer count = 0;
			String author = null;
			while(iterator.hasNext()){
				Object[] objects = (Object[])iterator.next();
				String authorTemp = objects[0].toString();
				Integer countTemp = (Integer)objects[1];
				if(countTemp>count){
					count = countTemp;
					author = authorTemp;
				}
			}
			Map<String,String> map = new HashMap<String, String>();
			if(count!=0){
				map.put("bestmem", author);
			}else{
				map.put("bestmem", "None");
			}
			map.put("bestmemposts", count.toString());
			return map;
		}catch(Exception exception){
			if(tr!=null){
				tr.rollback();
			}
			exception.printStackTrace();
			return null;
		}
	}
	public Map<String,String> getPostsAndRuntime(){
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			String hql = "SELECT COUNT(*), (MAX(dateline)-MIN(dateline))/86400 FROM Posts";
			Query query = session.createQuery(hql);
			List list = query.list();
			tr.commit();
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				Object[] objects = (Object[])iterator.next();
				Map<String,String> map = new HashMap<String, String>();
				map.put("posts", objects[0].toString());
				map.put("runtime", (objects[1]==null||(Integer)objects[1]<1)?"1":objects[1].toString());
				return map;
			}
		}catch(Exception exception){
			if(tr!=null){
				tr.rollback();
			}
			exception.printStackTrace();
		}
		return null;
	}
	public Integer getPostsaddtoday(){
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			String hql = "SELECT COUNT(*) FROM Posts WHERE dateline>=? AND invisible=?";
			Query query = session.createQuery(hql);
			int nowTime = (int)(System.currentTimeMillis()/1000);
			query.setInteger(0, nowTime-86400);
			query.setByte(1, (byte)0);
			List list = query.list();
			tr.commit();
			return (Integer)list.get(0);
		}catch(Exception exception){
			if(tr!=null){
				tr.rollback();
			}
			exception.printStackTrace();
		}
		return null;
	}
	public List<Posts> findPostByhql(String hql, int start, int max) {
		Session session = null;
		Transaction trs = null;
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			trs = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(max);
			List<Posts> postlist = query.list();
			trs.commit();
			return postlist;
		}catch(HibernateException e){
			if(trs!=null){
				trs.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean updatePosts(Posts post) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.update(post);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public int saveOrupdatePosts(Posts post) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.saveOrUpdate(post);
			tr.commit();
			return post.getPid();
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public List<Posts> getPostsListByTid(Integer tid) {
		Transaction transaction = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Posts as p WHERE p.tid=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, tid);
			List<Posts> postsList = query.list();
			transaction.commit();
			return postsList;
		} catch (Exception exception) {
			exception.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return null;
	}
	public void deletePosts(List<Integer> pidList) {
		Transaction transaction = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			for(int i = 0;i<pidList.size();i++){
				session.delete(session.get(Posts.class, pidList.get(i)));
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	public Posts getLastPosts(Integer tid) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Posts as p WHERE p.tid=? AND p.invisible=0 ORDER BY p.dateline DESC";
			Query query = session.createQuery(hql);
			query.setInteger(0, tid);
			query.setMaxResults(1);
			List<Posts> postsList = query.list();
			transaction.commit();
			if(postsList.size()>0){
				return postsList.get(0);
			}else{
				return null;
			}
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public Posts getFirstPosts(Integer tid) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Posts as p WHERE p.tid=? AND p.invisible=0 ORDER BY p.dateline ASC LIMIT 1";
			Query query = session.createQuery(hql);
			query.setInteger(0, tid);
			List<Posts> postsList = query.list();
			transaction.commit();
			if(postsList.size()>0){
				return postsList.get(0);
			}else{
				return null;
			}
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public void updatePosts(List<Posts> postsList) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			for(int i = 0;i<postsList.size();i++){
				session.update(postsList.get(i));
			}
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
	public Integer getCountOfReplyForTopic(Integer topicId) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "SELECT COUNT(*) FROM Posts AS p WHERE p.tid=? AND invisible=0";
			Query query = session.createQuery(hql);
			query.setInteger(0, topicId);
			List<Integer> list = query.list();
			transaction.commit();
			if(list!=null){
				return list.get(0)-1;
			}else{
				throw new Exception("Result IS NULL");
			}
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public void updatePostsByHQL(String hql) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.executeUpdate();
			transaction.commit();
		}catch(Exception exception){
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
}
