package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.ThreadsmodDao;
import cn.jsprun.domain.Threadsmod;
import cn.jsprun.utils.HibernateUtil;
public class ThreadsmodDaoImple implements ThreadsmodDao {
	public void addThreadsmod(Threadsmod threadsmod) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			session.save(threadsmod);
			transaction.commit();
		}catch(Exception exception){
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
	public Integer saveList(List<Threadsmod> updatelist) {
		Integer num = 0;
		if (updatelist == null || updatelist.size() <= 0) {
			return num;
		}
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			for (int i = 0; i < updatelist.size(); i++) {
				session.save(updatelist.get(i));
			}
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return num;
	}
	public Threadsmod findByThreadsIdDEL(Integer tid) {
		Threadsmod tmod = null;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			tr = session.beginTransaction();
			Query query = session
					.createQuery("from Threadsmod as mod where mod.id.tid="
							+ tid + " and mod.id.action ='DEL'");
			if (query.list().size() > 0) {
				tmod = (Threadsmod) query.list().get(0);
			}
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return tmod;
	}
	@SuppressWarnings("unchecked")
	public List<Threadsmod> findByThreadsBytid(int tid) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Threadsmod as m where m.id.tid=? order by m.id.dateline desc");
			query.setParameter(0, tid);
			List<Threadsmod> modlist = query.list();
			tr.commit();
			return modlist;
		}catch(HibernateException e){
			if (tr != null)
				tr.rollback();
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public Threadsmod findByThreadsBytidTop1(int tid) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Threadsmod as m where m.id.tid=? order by m.id.dateline desc");
			query.setParameter(0, tid);
			query.setMaxResults(1);
			List<Threadsmod>modlist = query.list();
			tr.commit();
			if(modlist!=null && modlist.size()>0){
				return modlist.get(0);
			}
		}catch(HibernateException e){
			if (tr != null)
				tr.rollback();
			e.printStackTrace();
		}
		return null;
	}
	public void deleteThreadsmod(Integer tid) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("DELETE FROM Threadsmod as m WHERE m.id.tid=?");
			query.setInteger(0, tid);
			query.executeUpdate();
			tr.commit();
		}catch(HibernateException e){
			if (tr != null)
				tr.rollback();
			e.printStackTrace();
		}
	}
}
