package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.SettingsDao;
import cn.jsprun.domain.Settings;
import cn.jsprun.utils.HibernateUtil;
public class SettingsDaoImpl implements SettingsDao {
	public Settings findBySettingvariable(String variable) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		Settings settings = null;
		try {
			tr = session.beginTransaction();
			settings = (Settings) session.createQuery("from Settings AS s where s.variable='" + variable + "'").uniqueResult();
			tr.commit();
		} catch (HibernateException e) {
			if(tr!=null)
			{
				tr.rollback();
			}
			e.printStackTrace();
		}
		return settings;
	}
	@SuppressWarnings("unchecked")
	public List<Settings> findSettingsAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		List<Settings> list = null;
		try {
			tr = session.beginTransaction();
			list = session.createQuery("from Settings").list();
			tr.commit();
		} catch (HibernateException e) {
			if(tr!=null)
			{
				tr.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}
	public boolean removeSetting(Settings setting) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.delete(setting);
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null)
			{
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean saveSetting(Settings setting) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(setting);
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null)
			{
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean updateSetting(Settings setting) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.saveOrUpdate(setting);
			session.flush();
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null)
			{
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public List<Settings> findSettingsLikeVariable(String variable) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Query query = session.createQuery("from Settings as s where s.variable like '"+variable+"%'");
			List<Settings> list = query.list();
			ts.commit();
			return list;
		} catch (HibernateException e) {
			if(ts!=null)
			{
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
}
