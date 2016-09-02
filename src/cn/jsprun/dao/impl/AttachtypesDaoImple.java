package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.AttachtypesDao;
import cn.jsprun.domain.Attachtypes;
import cn.jsprun.utils.HibernateUtil;
public class AttachtypesDaoImple implements AttachtypesDao {
	public Integer deleteList(String[] ids) {
		int count = -1;
		StringBuffer deleteString = new StringBuffer("delete from Attachtypes as a");
		if (ids != null && ids.length > 0) {
			deleteString.append(" where a.id in (");
			for (int i = 0; i < ids.length; i++) {
				if (ids[i] != null) {
					deleteString.append(ids[i]);
					deleteString.append(",");
				}
			}
			String querystr = deleteString.substring(0,deleteString.length() - 1);
			querystr = querystr + ")";
			Transaction tr = null;
			try {
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				tr = session.beginTransaction();
				Query query = session.createQuery(querystr);
				count = query.executeUpdate();
				session.flush();
				tr.commit();
			} catch (HibernateException he) {
				if (tr != null)
					tr.rollback();
				tr = null;
				he.printStackTrace();
			}
		} else {
			return count;
		}
		return count;
	}
	@SuppressWarnings("unchecked")
	public List<Attachtypes> getAll() {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Attachtypes as a");
			List<Attachtypes> list = query.list();
			tr.commit();
			return list;
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return null;
	}
	public boolean saveAttachtypes(Attachtypes attachtypes) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.save(attachtypes);
			tr.commit();
			return true;
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return false;
	}
	public Integer updateExtensionList(List<Attachtypes> list) {
		int updateNumber = 0;
		if (list == null || list.size() <= 0)
			return updateNumber;
		for (int i = 0; i < list.size(); i++) {
			Attachtypes attachtypes = list.get(i);
			if (attachtypes != null && attachtypes.getExtension() != null) {
				if (isSave(attachtypes.getExtension()) == false) {
					list.remove(i);
				}
			}
		}
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				Attachtypes attachtypes = list.get(i);
				if (attachtypes != null && attachtypes.getExtension() != null) {
					Query query = session.createQuery("update Attachtypes as a set a.extension = :extension  where a.id=:id");
					query.setString("extension", attachtypes.getExtension());
					query.setShort("id", attachtypes.getId());
					updateNumber += query.executeUpdate();
				}
			}
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return updateNumber;
	}
	public Integer updateMaxsizeList(List<Attachtypes> list) {
		int updateNumber = 0;
		if (list == null || list.size() <= 0)
			return updateNumber;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				Attachtypes attachtypes = list.get(i);
				if (attachtypes != null && attachtypes.getMaxsize() != null) {
					Query query = session
							.createQuery("update Attachtypes as a set a.maxsize = :maxsize  where a.id=:id");
					query.setInteger("maxsize", attachtypes.getMaxsize());
					query.setShort("id", attachtypes.getId());
					updateNumber += query.executeUpdate();
				}
			}
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return updateNumber;
	}
	public boolean isSave(String name) {
		Session sessionSave = null;
		Query querySave = null;
		Transaction trSave = null;
		boolean issave = false;
		try {
			sessionSave = HibernateUtil.getSessionFactory().getCurrentSession();
			trSave = sessionSave.beginTransaction();
			querySave = sessionSave.createQuery("from Attachtypes as a where a.extension like :extension");
			querySave.setString("extension", name);
			List list = querySave.list();
			if (list == null || list.size() == 0) {
				issave = true;
			}
			trSave.commit();
		} catch (HibernateException he) {
			if (trSave != null)
				trSave.rollback();
			trSave = null;
			he.printStackTrace();
		}
		return issave;
	}
}
