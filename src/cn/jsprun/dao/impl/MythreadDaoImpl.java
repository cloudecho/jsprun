package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.MythreadDao;
import cn.jsprun.domain.Mythreads;
import cn.jsprun.utils.HibernateUtil;
public class MythreadDaoImpl implements MythreadDao {
	public boolean deleteMythreadByUid(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Mythreads as t where t.id.uid=?");
			query.setParameter(0, uid);
			query.executeUpdate();
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
	@SuppressWarnings("unchecked")
	public List<Mythreads> findMythreadByUid(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Mythreads as t where t.id.uid=?");
			query.setParameter(0, uid);
			List<Mythreads> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertMythread(Mythreads mythread) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(mythread);
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
	public void deleteMythread(Integer tid) {
		Transaction transaction = null;
		try{
			Session session  = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "DELETE FROM Mythreads AS m WHERE m.id.tid=?";
			Query query  =session.createQuery(hql);
			query.setInteger(0, tid);
			query.executeUpdate();
			transaction.commit();
		}catch(Exception exception){
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
}
