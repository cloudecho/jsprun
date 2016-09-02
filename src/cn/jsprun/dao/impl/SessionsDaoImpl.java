package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.SessionsDao;
import cn.jsprun.domain.Sessions;
import cn.jsprun.utils.HibernateUtil;
public class SessionsDaoImpl implements SessionsDao {
	public boolean addSessions(Sessions sessions) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(sessions);
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
	public boolean deleteSessions(Sessions sessions) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(sessions);
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
	public boolean deleteSessionsBySid(String sid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Sessions as s where s.sid=?");
			query.setParameter(0, sid);
			query.executeUpdate();
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
	public Sessions findSessionsBySid(String sid) {
		Transaction tr = null;
		Sessions ses=null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			ses =  (Sessions) session.get(Sessions.class, sid);
			tr.commit();
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return ses;
	}
	public int findSessionsCountByType(boolean members) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			String hql = "select count(*) from Sessions as s where s.uid=0";
			if(members){
				hql = "select count(*) from Sessions as s where s.uid=0";
			}else{
				hql = "select count(*) from Sessions";
			}
			Query query = session.createQuery(hql);
			List list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return Integer.valueOf(list.get(0)+"");
			}
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public boolean findSessionByUid(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Sessions as s where s.uid = ?");
			query.setParameter(0, uid);
			List list = query.list();
			if(list!=null && list.size()>0){
				return true;
			}
			tr.commit();
		}catch(HibernateException he){
			if (tr != null) {
				tr.rollback();
			}
			he.printStackTrace();
		}
		return false;
	}
}
