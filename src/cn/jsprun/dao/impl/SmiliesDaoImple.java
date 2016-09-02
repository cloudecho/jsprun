package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.ShortType;
import cn.jsprun.dao.SmiliesDao;
import cn.jsprun.domain.Smilies;
import cn.jsprun.utils.HibernateUtil;
public class SmiliesDaoImple implements SmiliesDao {
	public Integer deleteIcons(String[] ids) {
		int count = -1;
		StringBuffer deleteString = new StringBuffer("delete from Smilies as s");
		if (ids != null && ids.length > 0) {
			deleteString.append(" where s.id in (");
			for (int i = 0; i < ids.length; i++) {
				if (ids[i] != null) {
					deleteString.append(ids[i]);
					deleteString.append(",");
				}
			}
			String querystr = deleteString.substring(0,
					deleteString.length() - 1);
			querystr = querystr + ")";
			Transaction tr = null;
			try {
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				tr = session.beginTransaction();
				Query query = session.createQuery(querystr);
				count = query.executeUpdate();
				tr.commit();
			} catch (HibernateException he) {
				if (tr != null) {
					tr.rollback();
					tr = null;
				}
				he.printStackTrace();
			}
		} 
		return count;
	}
	@SuppressWarnings("unchecked")
	public List<Smilies> getIcons() {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session
					.createQuery("from Smilies as s where s.typeid = 0 and s.type='icon' order by s.displayorder");
			List<Smilies> list = query.list();
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
	public Integer updateDisplayorderIcons(List<Smilies> list) {
		int updateNumber = 0;
		if (list == null || list.size() <= 0)
			return updateNumber;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				Smilies s = list.get(i);
				if (s != null) {
					Query query = session
							.createQuery("update Smilies as s set s.displayorder = :displayorder  where s.id=:id");
					query.setShort("displayorder", s.getDisplayorder());
					query.setShort("id", s.getId());
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
	public Integer deleteSmiliesIds(List<Short> list) {
		Integer num = -1;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Smilies as s where s.id in (:ids)");
			query.setParameterList("ids", list, new ShortType());
			num = query.executeUpdate();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
			num = 0;
		}
		return num;
	}
	public Integer updateSmiliesDisplayorderCode(List<Smilies> list) {
		Integer num = -1;
		String hql = "update Smilies as s set s.displayorder = :displayorder,s.code=:code where s.id=:id";
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			for (int i = 0; i < list.size(); i++) {
				query.setShort("displayorder", list.get(i).getDisplayorder()); 
				query.setString("code", list.get(i).getCode());
				query.setShort("id", list.get(i).getId());
				num += query.executeUpdate();
			}
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
			num = 0;
		}
		return num;
	}
	public Integer save(Smilies s) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.save(s);
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
			return 0;
		} 
		return 1;
	}
	public List<Smilies> getSmilies() {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Smilies as s where s.type='smiley' order by s.displayorder");
			List<Smilies> list = query.list();
			tr.commit();
			return list;
		} catch (HibernateException he) {
			if (tr != null) {
				tr.rollback();
				tr = null;
			}
			he.printStackTrace();
		} 
		return null;
	}
	public boolean findSmiliesbytypeid(short typeid, String url) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Smilies as s where s.type='smiley' and s.typeid=? and url = ?");
			query.setParameter(0, typeid);
			query.setParameter(1, url);
			List list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return true;
			}
		}catch(HibernateException he){
			if (tr != null) {
				tr.rollback();
			}
			he.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<Smilies> findSmiliesBytypeid(short typeid,int start,int max) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Smilies as s where s.typeid=?");
			query.setParameter(0, typeid);
			query.setFirstResult(start);
			query.setMaxResults(max);
			List<Smilies> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException he){
			if (tr != null) {
				tr.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}
	public int findSmiliesCountBytypeId(short typeid) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("select count(*) from Smilies as s where s.typeid=?");
			query.setParameter(0, typeid);
			List list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return (Integer)list.get(0);
			}
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public Smilies findSmiliesById(short id) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Smilies smilies = (Smilies)session.get(Smilies.class, id);
			tr.commit();
			return smilies;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean addSmilies(Smilies smilies) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.save(smilies);
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
}
