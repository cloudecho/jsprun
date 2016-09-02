package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.PolloptionsDao;
import cn.jsprun.domain.Polloptions;
import cn.jsprun.utils.HibernateUtil;
public class PolloptionsDaoImpl implements PolloptionsDao {
	public boolean deletePolloptions(Polloptions polloptions) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(polloptions);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public List<Polloptions> findPolloptionsBytid(int tid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Polloptions as p where p.tid=?");
			query.setParameter(0, tid);
			List<Polloptions> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertPolloptions(Polloptions polloptions) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(polloptions);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean updatePolloptions(Polloptions polloptions) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.update(polloptions);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public Polloptions findPolloptionsById(int optionid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Polloptions polloptions = (Polloptions)session.get(Polloptions.class, optionid);
			tr.commit();
			return polloptions;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
}
