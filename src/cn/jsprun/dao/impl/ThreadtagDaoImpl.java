package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.ThreadtagDao;
import cn.jsprun.domain.Threadtags;
import cn.jsprun.utils.HibernateUtil;
public class ThreadtagDaoImpl implements ThreadtagDao {
	public boolean deleteThreadtags(Threadtags threadtags) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(threadtags);
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
	public List<Threadtags> findThreadtagsByTagname(String tagname, int start,
			int max) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Threadtags as t where t.id.tagname=?");
			query.setParameter(0, tagname);
			query.setFirstResult(start);
			query.setMaxResults(max);
			List<Threadtags> list = query.list();
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
	public int findThreadtagsCountByTagname(String tagname) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("select count(*) from Threadtags as t where t.id.tagname=?");
			query.setParameter(0, tagname);
			List list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return (Integer)list.get(0);
			}
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
}
