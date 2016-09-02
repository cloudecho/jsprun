package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.CreditslogDao;
import cn.jsprun.domain.Creditslog;
import cn.jsprun.utils.HibernateUtil;
public class CreditslogDaoImpl implements CreditslogDao{
	public boolean deleteCreditslog(Creditslog creditslog) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.delete(creditslog);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	public List<Creditslog> findAllCreditslog() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Creditslog");
			List<Creditslog> list = query.list();
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
	@SuppressWarnings("unchecked")
	public List<Creditslog> findAllCreditslogByOperation(String []operation) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		StringBuffer sql = new StringBuffer("from Creditslog as c where c.id.operation in (");
		for(int i=0;i<operation.length;i++){
			sql.append("'");
			sql.append(operation[i]);
			sql.append("'");
			sql.append(",");
		}
		String sqlstr = sql.substring(0,sql.length()-1);
		sqlstr = sqlstr + ")";
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(sqlstr);
			List<Creditslog> list = query.list();
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
	public boolean insertCreditslog(Creditslog creditslog) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(creditslog);
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
	public boolean modifyCreditslog(Creditslog creditslog) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.update(creditslog);
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
	public List<Creditslog> findCreditslogByKeys(String keyword) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Creditslog as c where c.id.fromto like '%"+keyword+"%'");
			List<Creditslog> list = query.list();
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
	public List<Creditslog> findCreditslogByUid(int uid,int maxrow) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Creditslog as c where c.id.uid=?");
			query.setParameter(0, uid);
			query.setMaxResults(maxrow);
			List<Creditslog> list = query.list();
			if(tr!=null){
				tr.rollback();
			}
			return list;
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return null;
	}
	public List<Creditslog> findCreditsLogByHql(String hql, int startrow, int maxrow) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(startrow);
			query.setMaxResults(maxrow);
			List<Creditslog> list = query.list();
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
	public int findCreditslogCountbyHql(String hql) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
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
