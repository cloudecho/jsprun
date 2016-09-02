package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.TypeoptionsDao;
import cn.jsprun.domain.Typeoptions;
import cn.jsprun.utils.HibernateUtil;
public class TypeoptionsDaoImpl implements TypeoptionsDao {
	private static String CLASSID = "classid";
	private static String IDENTIFIER="identifier";
	public boolean addTypeoption(Typeoptions typeoption) {
		boolean flag = false;
		if (typeoption != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.save(typeoption);
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
	public Typeoptions findByIdentifier(String identifier) {
		Typeoptions typeoption=null;
		List<Typeoptions> typeoptions=this.findByProperty(IDENTIFIER, identifier);
		if(typeoptions!=null)
		{
			Iterator<Typeoptions> iterators=typeoptions.iterator();
			{
				while(iterators.hasNext())
				{
					typeoption=iterators.next();
				}
			}
		}
		return typeoption;
	}
	public List<Typeoptions> findByClassId(Short classid) {
		return this.findByProperty(CLASSID, classid);
	}
	public boolean removeTypeoption(Typeoptions typeoption) {
		boolean flag = false;
		if (typeoption != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.delete(typeoption);
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
	public boolean updateTypeoption(Typeoptions typeoption) {
		boolean flag = false;
		if (typeoption != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.saveOrUpdate(typeoption);
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
	public Typeoptions findById(Short classid) {
		Session session = null;
		Transaction tran = null;
		Typeoptions typeoption = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			typeoption = (Typeoptions) session.get(Typeoptions.class, classid);
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return typeoption;
	}
	@SuppressWarnings("unchecked")
	public List<Typeoptions> findByProperty(String propertyName, Object value) {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Typeoptions> typeoptions = null;
		try {
			typeoptions = new ArrayList<Typeoptions>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Typeoptions as t where t."
					+ propertyName + "=? order by displayorder");
			query.setString(0, value.toString());
			typeoptions = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return typeoptions;
	}
	@SuppressWarnings("unchecked")
	public List<Typeoptions> findAll() {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Typeoptions> typeoptions = null;
		try {
			typeoptions = new ArrayList<Typeoptions>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Typeoptions order by displayorder");
			typeoptions = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return typeoptions;
	}
}
