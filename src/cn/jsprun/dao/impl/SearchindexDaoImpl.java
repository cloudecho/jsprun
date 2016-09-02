package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.SearchindexDao;
import cn.jsprun.domain.Searchindex;
import cn.jsprun.utils.HibernateUtil;
public class SearchindexDaoImpl implements SearchindexDao {
	public boolean deleteSearchindex(Searchindex searchindex) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(searchindex);
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
	public Searchindex findSearchindexByHql(String hql) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			List<Searchindex> list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return list.get(0);
			}
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Searchindex findSearchindexById(int searchid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Searchindex searchindex = (Searchindex)session.get(Searchindex.class, searchid);
			tr.commit();
			return searchindex;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public int insertSearchindex(Searchindex searchindex) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(searchindex);
			tr.commit();
			return searchindex.getSearchid();
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public int findseachindexcountbyHql(String hql) {
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
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
}
