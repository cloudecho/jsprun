package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import cn.jsprun.dao.PmsDao;
import cn.jsprun.domain.Pms;
import cn.jsprun.utils.*;
public class PmsDaoImpl implements PmsDao {
	public PmsDaoImpl() {
	}
	public void insertPmsList(List<Pms> pmsList) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession(); 
			tr = session.beginTransaction(); 
			for (int i = 0; i < pmsList.size(); i++) {
				Pms pms = pmsList.get(i);
				session.save(pms); 
			}
			session.flush();
			tr.commit();
		} catch (HibernateException e) {
			if (tr != null)
				tr.rollback();
			tr = null;
			e.printStackTrace();
		} 
	}
	@SuppressWarnings( { "unused", "unchecked" })
	public List<Pms> findPmsByMsgtoid(int touid) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session
					.createQuery("from Pms as p where p.msgtoid = ? or p.msgfromid = ?");
			query.setParameter(0, touid);
			query.setParameter(1, touid);
			List<Pms> list = query.list();
			tr.commit();
			return list;
		} catch (HibernateException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public void updatePms(Pms pms) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.update(pms);
			tr.commit();
		} catch (HibernateException e) {
			if (tr != null)
				tr.rollback();
			e.printStackTrace();
		}
	}
	private Criterion findbyOR(String type, String str,Session session) {
		Criterion criterion = null; 
		StringBuffer sbSQL = new StringBuffer(
				"select p.pmid from Pms as p where");
		List<String> srch = new ArrayList<String>(); 
		str = str.replace("|", ",");
		String[] srchAND = str.split(" and "); 
		for (int i = 0; i < srchAND.length; i++) {
			String[] srch_ = srchAND[i].split(" "); 
			for (int j = 0; j < srch_.length; j++) {
				if (cn.jsprun.utils.FormDataCheck.isValueString(srch_[j])) {
					srch.add(srch_[j]);
				}
			}
		}
		for (int i = 0; i < srch.size(); i++) {
			sbSQL.append(" p." + type + " like '%" + srch.get(i) + "%' or");
		}
		String sql = sbSQL.substring(0, sbSQL.length() - 2);
		try {
			Query query = session.createQuery(sql);
			List<Integer> list = query.list();
			criterion = Expression.in("pmid", list);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
			criterion = null;
		}
		return criterion;
	}
	private Criterion findbyAnd(String type, String str) {
		Criterion criterion = null; 
		List<String> srch = new ArrayList<String>(); 
		String[] srchAND = str.split(" and "); 
		for (int i = 0; i < srchAND.length; i++) {
			String[] srch_ = srchAND[i].split(" "); 
			for (int j = 0; j < srch_.length; j++) {
				if (cn.jsprun.utils.FormDataCheck.isValueString(srch_[j])) {
					srch.add(srch_[j]);
				}
			}
		}
		try {
			Criterion[] criterionArray = new Criterion[srch.size()];
			for (int i = 0; i < srch.size(); i++) {
				Criterion criterionCount = Expression.like(type, srch.get(i),
						MatchMode.ANYWHERE);
				criterionArray[i] = criterionCount;
			}
			for (int i = 0; i < criterionArray.length; i++) {
				if (i == 0) {
					criterion = Expression.and(criterionArray[i],
							criterionArray[i++]);
				}
				else {
					criterion = Expression.and(criterion, criterionArray[i]);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			criterion = null;
		}
		return criterion;
	}
	public List<Pms> findPmsByHql(String hql, int begin, int maxlength) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(begin);
			query.setMaxResults(maxlength);
			List<Pms> pmslist = query.list();
			tr.commit();
			return pmslist;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public int findPmsCountByHql(String hql) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
	public Pms findPmsBypmid(int pmid) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Pms pms = (Pms)session.get(Pms.class, pmid);
			tr.commit();
			return pms;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean deletePms(Pms pms) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.delete(pms);
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
}
