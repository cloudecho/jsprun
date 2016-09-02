package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.ThreadtypesDao;
import cn.jsprun.domain.Threadtypes;
import cn.jsprun.utils.HibernateUtil;
public class ThreadtypesDaoImpl implements ThreadtypesDao {
	private static String SPECIAL="special";
	public boolean addThreadtype(Threadtypes threadtype) {
		boolean flag = false;
		if (threadtype != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.save(threadtype);
				flag = true;
				tran.commit();
			} catch (HibernateException e) {
				flag = false;
				if(tran!=null){
					tran.rollback();
				}
				e.printStackTrace();
			}
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	public List<Threadtypes> findAll() {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Threadtypes> threadtypes = null;
		try {
			threadtypes = new ArrayList<Threadtypes>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Threadtypes order by displayorder");
			threadtypes = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return threadtypes;
	}
	public Threadtypes findById(Short typeId) {
		Session session = null;
		Transaction tran = null;
		Threadtypes threadtype = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			threadtype = (Threadtypes) session.get(Threadtypes.class, typeId);
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return threadtype;
	}
	@SuppressWarnings("unchecked")
	public List<Threadtypes> findByProperty(String propertyName, Object value) {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Threadtypes> threadtypes = null;
		try {
			threadtypes = new ArrayList<Threadtypes>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Threadtypes as t where t."
					+ propertyName + "=? order by displayorder");
			query.setString(0, value.toString());
			threadtypes = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return threadtypes;
	}
	public boolean removeThreadtype(Threadtypes threadtype) {
		boolean flag = false;
		if (threadtype != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.delete(threadtype);
				flag = true;
				tran.commit();
			} catch (HibernateException e) {
				flag = false;
				if(tran!=null){
					tran.rollback();
				}
				e.printStackTrace();
			}
		}
		return flag;
	}
	public boolean updateThreadtype(Threadtypes threadtype) {
		boolean flag = false;
		if (threadtype != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.saveOrUpdate(threadtype);
				flag = true;
				tran.commit();
			} catch (HibernateException e) {
				flag = false;
				if(tran!=null){
					tran.rollback();
				}
				e.printStackTrace();
			}
		}
		return flag;
	}
	public List<Threadtypes> findBySpecial(Short value) {
		return this.findByProperty(SPECIAL, value);
	}
	public List<Threadtypes> findThreadtypeByHql(String hql) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			List<Threadtypes> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException he){
			if(tr!=null){
				tr.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}
}