package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.PmsearchindexDao;
import cn.jsprun.domain.Pmsearchindex;
import cn.jsprun.utils.HibernateUtil;
public class PmsearchindexDaoImpl implements PmsearchindexDao {
	public boolean deletePmsearchindex(Pmsearchindex pmssearchindex) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(pmssearchindex);
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
	public Pmsearchindex findPmsearchindexById(int searchid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Pmsearchindex pmsearchindex = (Pmsearchindex)session.get(Pmsearchindex.class, searchid);
			tr.commit();
			return pmsearchindex;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Pmsearchindex findPmssearchindexByHql(String hql) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			List<Pmsearchindex> list = query.list();
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
	public int insertPmsearchindex(Pmsearchindex pmssearchindex) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(pmssearchindex);
			tr.commit();
			return pmssearchindex.getSearchid();
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
}
