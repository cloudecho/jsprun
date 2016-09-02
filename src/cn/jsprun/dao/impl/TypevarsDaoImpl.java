package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.TypevarsDao;
import cn.jsprun.domain.Typevars;
import cn.jsprun.domain.TypevarsId;
import cn.jsprun.utils.HibernateUtil;
public class TypevarsDaoImpl implements TypevarsDao {
	public boolean addTypevar(Typevars typevar) {
		boolean flag = false;
		if (typevar != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.save(typevar);
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
	public Typevars findById(TypevarsId id) {
		Session session = null;
		Transaction tran = null;
		Typevars typevar = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			typevar = (Typevars) session.get(Typevars.class, id);
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return typevar;
	}
	public List<Typevars> findByTId(Short typeid) {
		List<Typevars> typevars = new ArrayList<Typevars>();
		List<Typevars> allTypevars = this.findAll();
		if(allTypevars!=null)
		{
			Iterator<Typevars> iterators = allTypevars.iterator();
			while (iterators.hasNext()) {
				Typevars moderator = iterators.next();
				if (moderator.getId().getTypeid().equals(typeid)) {
					typevars.add(moderator);
				}
			}
		}
		return typevars;
	}
	public Typevars findByTO(Short typeid, Short optionid) {
		List<Typevars> typevars = this.findAll();
		if(typevars!=null)
		{
			for(int i=0;i<typevars.size();i++)
			{
				Typevars typevar=typevars.get(i);
				if(typevar.getId().getTypeid().equals(typeid)&&typevar.getId().getOptionid().equals(optionid))
				{
					return typevar;
				}
			}
		}
		return null;
	}
	public boolean removeTypevar(Typevars typevar) {
		boolean flag = false;
		if (typevar != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.delete(typevar);
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
	public boolean updateTypevar(Typevars typevar) {
		boolean flag = false;
		if (typevar != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.saveOrUpdate(typevar);
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
	public List<Typevars> findAll() {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Typevars> typevars = null;
		try {
			typevars = new ArrayList<Typevars>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Typevars order by displayorder");
			typevars = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return typevars;
	}
}
