package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.MypostsDao;
import cn.jsprun.domain.Myposts;
import cn.jsprun.utils.HibernateUtil;
public class MypostsDaoImpl implements MypostsDao {
	public boolean deleteMypostByUid(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Myposts as p where p.id.uid=?");
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
	public List<Myposts> findMypostByUid(int uid) {
		Session session  = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Myposts as p where p.id.uid=?");
			query.setParameter(0, uid);
			List<Myposts> list = query.list();
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
	public boolean insertMypost(Myposts mypost) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(mypost);
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
	public void deleteMyposts(Integer tid) {
		Transaction transaction = null;
		try{
			Session session  = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "DELETE FROM Myposts AS m WHERE m.id.tid=?";
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
