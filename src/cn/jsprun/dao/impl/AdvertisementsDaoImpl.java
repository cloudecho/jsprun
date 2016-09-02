package cn.jsprun.dao.impl;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.AdvertisementsDao;
import cn.jsprun.domain.Advertisements;
import cn.jsprun.utils.HibernateUtil;
public class AdvertisementsDaoImpl implements AdvertisementsDao {
	public boolean addAdv(Advertisements adv) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(adv);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean delAnyAdvs(String[] deleteIds) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			int len = deleteIds.length;
			Advertisements adv = null;
			for(int i=0;i<len;i++){
				adv = (Advertisements)session.get(Advertisements.class, Integer.parseInt(deleteIds[i]));
				session.delete(adv);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public Advertisements queryAdvById(Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Advertisements adv = (Advertisements) session.get(Advertisements.class, id);
			ts.commit();
			return adv;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Map queryAdvWithPagination(int firstResult,int maxResult,String countKey,String listKey){
		Map map = new HashMap();
		List<Advertisements> list = null;
		int dataCount = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try{
			ts = session.beginTransaction();
			String hql = "from Advertisements order by displayorder,advid desc";
			String hql_getCount = "SELECT COUNT(advid) FROM Advertisements";
			Query query = session.createQuery(hql_getCount);
			dataCount = (Integer)query.list().get(0);
			query = session.createQuery(hql);
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			list = query.list();
			ts.commit();
		}catch(Exception exception){
			if(ts!=null){
				ts.rollback();
			}
			exception.printStackTrace();
		}
		map.put(countKey, dataCount);
		map.put(listKey, list);
		return map;
	}
	public Map queryAdvWithPagination(String sqlStatement,int firstResult,int maxResult,String countKey,String listKey) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		Map map = new HashMap();
		List<Advertisements> adList = null;
		int dataCount = 0;
		try {
			ts = session.beginTransaction();
			Query query = session.createQuery(sqlStatement);
			dataCount = query.list().size();
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			adList = query.list();
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		map.put(countKey, dataCount);
		map.put(listKey, adList);
		return map;
	}
	public boolean updateAdv(Advertisements adv) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.update(adv);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateAdvs(List<Advertisements> advList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Advertisements> advIt = advList.iterator();
			Advertisements adv = null;
			while(advIt.hasNext()){
				adv = advIt.next();
				session.update(adv);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
}
