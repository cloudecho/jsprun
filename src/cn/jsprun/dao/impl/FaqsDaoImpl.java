package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.FaqsDao;
import cn.jsprun.domain.Faqs;
import cn.jsprun.utils.HibernateUtil;
public class FaqsDaoImpl implements FaqsDao {
	public Faqs findFaqlById(short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Faqs faq = (Faqs)session.get(Faqs.class, id);
			tr.commit();
			return faq;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public List<Faqs> findFaqsByHql(String hql) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			List<Faqs> list = query.list();
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
	public List<Faqs> findFaqsByfpid(short fpid, short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Faqs as f where f.fpid=? and id<>? order by f.displayorder");
			query.setParameter(0, fpid);
			query.setParameter(1, id);
			List<Faqs> list = query.list();
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
	public List<Faqs> findFaqsChilds() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Faqs as f where f.fpid<>0 order by f.displayorder");
			List<Faqs> list = query.list();
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
	public List<Faqs> findFaqsParents() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Faqs as f where f.fpid=0 order by f.displayorder");
			List<Faqs> list = query.list();
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
}
