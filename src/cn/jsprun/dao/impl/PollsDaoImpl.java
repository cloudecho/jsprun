package cn.jsprun.dao.impl;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.PollsDao;
import cn.jsprun.domain.Polls;
import cn.jsprun.utils.HibernateUtil;
public class PollsDaoImpl implements PollsDao {
	public boolean deletePolls(Polls polls) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(polls);
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
	public Polls findPollsBytid(int tid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Polls polls = (Polls)session.get(Polls.class, tid);
			tr.commit();
			return polls;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertPolls(Polls polls) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(polls);
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
	public boolean updatePolls(Polls polls) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.update(polls);
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
}
