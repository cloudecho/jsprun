package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.utils.*;
import cn.jsprun.dao.ThreadsDao;
import cn.jsprun.domain.Threads;
public class ThreadsDaoImple implements ThreadsDao {
	public boolean addThread(Threads thread) {
		boolean flag = false;
		if (thread != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.save(thread);
				flag = true;
				tran.commit();
			} catch (HibernateException e) {
				flag = false;
				if(tran!=null){
					tran.rollback();
				}
				e.printStackTrace();
			}
		}
		return flag;
	}
	public void totype(Integer typeid, String sbtid) {
		StringBuffer querystr = new StringBuffer("update Threads as t set t.typeid = " + typeid + "");
		try {
			threadTemplate(querystr, sbtid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void toforum(Integer fid, String sbtid) {
		StringBuffer querystr = new StringBuffer("update Threads as t set t.fid = " + fid + "");
		try {
			 threadTemplate(querystr, sbtid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void donotupdatemember(Boolean b, String sbtid) {
		StringBuffer querystr = new StringBuffer("delete from Threads as t");
		StringBuffer queryPosts = new StringBuffer("delete from Posts as t");
		StringBuffer attachPosts = new StringBuffer("delete from Attachments as t");
		if (b == false) {
			Transaction tr=null;
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				tr = session.beginTransaction();
				StringBuffer sb = new StringBuffer("from Threads as t where t.tid in (");
				Object[] tids = sbtid.split(",");
				for (int i = 0; i < tids.length; i++) {
					sb.append(tids[i].toString());
					sb.append(",");
				}
				String str = sb.substring(0, sb.length() - 1);
				str = str + ")";
				Query query = session.createQuery(str);
				List<Threads> threadsList = query.list();
				session.flush();
				for (int i = 0; i < threadsList.size(); i++) {
					Integer num = threadsList.get(i).getAuthorid();
					SQLQuery sqlquery = session.createSQLQuery("update Member as m set m.posts= m.posts-1,m.credits = m.credits-1 where m.uid ="+ threadsList.get(i).getAuthorid() + "");
					sqlquery.executeUpdate();
				}
				tr.commit();
			} catch (HibernateException he) {
				if(tr!=null){
					tr.rollback();
				}
				he.printStackTrace();
			}
		}
		int num = -1;
		try {
			num = threadTemplate(querystr, sbtid);
			num = threadTemplate(queryPosts, sbtid);
			num = threadTemplate(attachPosts,sbtid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sticklevel(Integer displayorder, String sbtid) {
		StringBuffer querystr = new StringBuffer( "update Threads as t set t.displayorder = " + displayorder + "");
		try {
			 threadTemplate(querystr, sbtid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void adddigest(Integer digest, String sbtid) {
		StringBuffer querystr = new StringBuffer("update Threads as t set t.digest = " + digest + "");
		try {
			 threadTemplate(querystr, sbtid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteattach(String sbtid) {
		StringBuffer queryThreads = new StringBuffer(
				"update Threads as t set t.attachment = 0 ");
		StringBuffer queryPosts = new StringBuffer(
				"update Posts as t set t.attachment = 0 ");
		StringBuffer deleteAttachments = new StringBuffer(
				"delete from Attachments as t");
		int num = -1;
		try {
			num = threadTemplate(queryThreads, sbtid);
			num = threadTemplate(queryPosts, sbtid);
			num = threadTemplate(deleteAttachments, sbtid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int threadTemplate(StringBuffer querystr, String sbtid)
			throws Exception {
		int num = -1;
		querystr.append(" where t.tid in (");
		Object[] tids = sbtid.split(",");
		for (int i = 0; i < tids.length; i++) {
			querystr.append(tids[i].toString());
			querystr.append(",");
		}
		String str = querystr.substring(0, querystr.length() - 1);
		str = str + ")";
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(str);
			num = query.executeUpdate();
			tr.commit();
		} catch (HibernateException he) {
			if(tr!=null){
				tr.rollback();
			}
			he.printStackTrace();
		}
		return num;
	}
	public boolean modifyThreads(Threads thread) {
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.update(thread);
			tr.commit();
			return true;
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<Threads> findThreadsByUid(int uid) {
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Threads as t where t.authorid=?");
			query.setParameter(0, uid);
			List<Threads> list = query.list();
			tr.commit();
			return list;
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Threads findByTid(Integer id) {
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Threads t = (Threads) session.get(Threads.class, id);
			tr.commit();
			return t;
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public List<Threads> findThreadsByHql(String hql, int start, int maxrow) {
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(maxrow);
			List<Threads> list = query.list();
			tr.commit();
			return list;
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public int findThreadCount() {
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("select count(*) from Threads");
			List list = query.list();
			tr.commit();
			if (list != null && list.size() > 0) {
				return Integer.valueOf(list.get(0) + "");
			}
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public Threads findThreadsBytid(int tid) {
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Threads thread = (Threads) session.get(Threads.class, tid);
			tr.commit();
			return thread;
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public int findThreadCountByHql(String hql) {
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			List list = query.list();
			tr.commit();
			if (list != null && list.size() > 0) {
				return Integer.valueOf(list.get(0) + "");
			}
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public boolean deleteThreads(Threads thread) {
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.delete(thread);
			tr.commit();
			return true;
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public List<Threads> findThreadsByHqlTwo(String hql, int start, int maxrow) {
		List<Threads> threadlist = new ArrayList<Threads>();
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(maxrow);
			List<Threads> list = query.list();
			tr.commit();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object[] os = (Object[]) it.next();
				Threads t1 = (Threads) os[0];
				threadlist.add(t1);
			}
			return threadlist;
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Integer getThreadsCount(){
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			String hql = "SELECT COUNT(*) FROM Threads WHERE displayorder>=?";
			Query query = session.createQuery(hql);
			query.setByte(0, (byte)0);
			List list = query.list();
			tr.commit();
			return (Integer)list.get(0);
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateThreads(Threads thread) {
		Transaction tr=null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.update(thread);
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
	public List<Threads> getThreadsByThreadIdList(List<Integer> tidList){
		StringBuffer hqlBuffer = new StringBuffer("FROM Threads AS t WHERE t.tid IN(");
		for(Integer tid : tidList){
			hqlBuffer.append(tid+",");
		}
		String hql = hqlBuffer.replace(hqlBuffer.length()-1, hqlBuffer.length(), ")").toString();
		Transaction tr=null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			List<Threads> list = query.list();
			tr.commit();
			return list;
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
			return null;
		}
	}
	public Threads getLsatThread(Short fid) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Threads as t WHERE fid=? ORDER BY t.dateline DESC LIMIT 1";
			Query query = session.createQuery(hql);
			query.setShort(0, fid);
			List<Threads> threadsList = query.list();
			transaction.commit();
			if(threadsList.size()>0){
				return threadsList.get(0);
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
	public List<Threads> getThreadsByHql(String hql) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(hql);
			List<Threads> threadsList = query.list();
			transaction.commit();
			return threadsList;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public void addThreads(List<Threads> threadsList) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			for(Threads threads : threadsList){
				session.save(threads);
			}
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
}
