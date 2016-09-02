package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.BbcodesDao;
import cn.jsprun.domain.Bbcodes;
import cn.jsprun.utils.HibernateUtil;
public class BbcodesDaoImple implements BbcodesDao {
	public int deleteArray(String[] ids) {
		StringBuffer querystr = new StringBuffer("delete from Bbcodes as b ");
		int num = -1;
		querystr.append(" where b.id in (");
		for (int i = 0; i < ids.length; i++) {
			querystr.append(ids[i].toString());
			querystr.append(",");
		}
		String str = querystr.substring(0, querystr.length() - 1);
		str = str + ")";
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(str);
			num = query.executeUpdate();
			session.flush();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return num;
	}
	public Boolean saveBbcodes(Bbcodes bbcodes) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.save(bbcodes);
			session.flush();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
			return false;
		}
		return true;
	}
	public int updateAvailableArray(List<Bbcodes> list) {
		int num = -1;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session
					.createQuery("update Bbcodes as b set b.available = :available where b.id = :id");
			for (int i = 0; i < list.size(); i++) {
				query.setByte("available", list.get(i).getAvailable());
				query.setInteger("id", list.get(i).getId());
				num += query.executeUpdate();
			}
			session.flush();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
			num = -1;
		}
		return num;
	}
	public int updateIconArray(List<Bbcodes> list) {
		int num = -1;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session
					.createQuery("update Bbcodes as b set b.icon = :icon where b.id = :id");
			for (int i = 0; i < list.size(); i++) {
				query.setString("icon", list.get(i).getIcon());
				query.setInteger("id", list.get(i).getId());
				num += query.executeUpdate();
			}
			session.flush();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
			num = -1;
		}
		return num;
	}
	public int updateTagArray(List<Bbcodes> list) {
		int num = -1;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("update Bbcodes as b set b.tag = :tag where b.id = :id");
			for (int i = 0; i < list.size(); i++) {
				query.setString("tag", list.get(i).getTag());
				query.setInteger("id", list.get(i).getId());
				num += query.executeUpdate();
			}
			session.flush();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
			num = -1;
		}
		return num;
	}
	@SuppressWarnings("unchecked")
	public List<Bbcodes> findByAll() {
		List<Bbcodes> codesList = null;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Bbcodes as b order by id asc");
			codesList = query.list();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return codesList;
	}
	public Bbcodes findByID(Integer id) {
		Bbcodes bbcodes = null;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			bbcodes = (Bbcodes) session.get(Bbcodes.class, id); 
			session.flush();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null) {
				tr.rollback();
				tr = null;
			}
			he.printStackTrace();
		} 
		return bbcodes;
	}
	public boolean updateBbcodes(Bbcodes b) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.update(b);
			session.flush();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null) {
				tr.rollback();
				tr = null;
			}
			he.printStackTrace();
			return false;
		}
		return true;
	}
}
