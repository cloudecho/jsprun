package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.RatelogDao;
import cn.jsprun.domain.Ratelog;
import cn.jsprun.utils.HibernateUtil;
public class RatelogDaoImpl implements RatelogDao {
	public boolean deleteRatelog(Ratelog ratelog) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(ratelog);
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
	public boolean insertRatelog(Ratelog ratelog) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(ratelog);
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
	public List<Ratelog> getRatelogByPid(Integer pid) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Ratelog AS r WHERE r.id.pid=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, pid);
			List<Ratelog> list = query.list();
			transaction.commit();
			return list;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public List<Ratelog> getRatelogListByPid(List<Integer> pidList) {
		if(pidList==null){
			try{
				throw new Exception("pidList is NULL");
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			StringBuffer hqlBuffer = new StringBuffer("FROM Ratelog AS r WHERE r.id.pid IN (");
			for(int i =0;i< pidList.size();i++){
				hqlBuffer.append(pidList.get(i)+",");
			}
			String hql = hqlBuffer.substring(0,hqlBuffer.length()-1)+")";
			Query query = session.createQuery(hql);
			List<Ratelog> list = query.list();
			transaction.commit();
			return list;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
}
