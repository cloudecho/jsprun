package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.SubscriptiosDao;
import cn.jsprun.domain.Subscriptions;
import cn.jsprun.utils.HibernateUtil;
public class SubscriptionsDaoImpl implements SubscriptiosDao {
	public boolean deleteSubscriptionByUid(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Subscriptions as s where s.id.uid=?");
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
	public List<Subscriptions> findSubscriptionsByUid(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Subscriptions as s where s.id.uid=?");
			query.setParameter(0, uid);
			List<Subscriptions> list = query.list();
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
	public boolean insertSubscritption(Subscriptions subscrition) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(subscrition);
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
}
