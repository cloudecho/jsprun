package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.BannedDao;
import cn.jsprun.domain.Banned;
import cn.jsprun.utils.HibernateUtil;
public class BannedDaoImpl implements BannedDao {
	public boolean insertBanned(Banned banned) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(banned);
			session.flush();
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
	public boolean deleteBanned(Banned banned) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(banned);
			session.flush();
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
	public List<Banned> findAllBanned() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Banned order by dateline");
			List<Banned> bannedList = query.list();
			tr.commit();
			return bannedList;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean modifyBanned(Banned banned) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.update(banned);
			session.flush();
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
	public Banned findById(Short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Banned banned = (Banned) session.get(Banned.class, id);
			tr.commit();
			return banned;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
}
