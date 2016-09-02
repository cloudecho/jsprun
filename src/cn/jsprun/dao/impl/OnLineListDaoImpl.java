package cn.jsprun.dao.impl;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.OnLineListDao;
import cn.jsprun.domain.Onlinelist;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.utils.HibernateUtil;
public class OnLineListDaoImpl implements OnLineListDao{
	public void addOnlinelist(List<Onlinelist> list){
		Transaction ts = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			ts = session.beginTransaction();
			for(Onlinelist onlinelist : list){
				session.save(onlinelist);
			}
			ts.commit();
		}catch(Exception exception){
			if(ts!=null){
				ts.rollback();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public List<Onlinelist> queryAllOnlineList() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			List<Onlinelist> olBeanList = session.createQuery("from Onlinelist").list();
			ts.commit();
			return olBeanList;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Usergroups> queryAllSystemUserGroup() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Query query = session.createQuery("from Usergroups ug where ug.type=:type");
			query.setString("type", "system");
			List<Usergroups> userGroupList = query.list();
			ts.commit();
			return userGroupList;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateOlList(List<Onlinelist> olBeanList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Onlinelist> it = olBeanList.iterator();
			Onlinelist olBean = null;
			while(it.hasNext()){
				olBean = it.next();
				session.save(olBean);
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
	public boolean updateUserGroup(List<Usergroups> ugList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Usergroups> it = ugList.iterator();
			Usergroups ugBean = null;
			while(it.hasNext()){
				ugBean = it.next();
				session.update(ugBean);
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
	public Usergroups queryUserGroupById(Short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Usergroups ugBean = (Usergroups)session.get(Usergroups.class, id);
			ts.commit();
			return ugBean;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean delAllOlList() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.createQuery("delete Onlinelist").executeUpdate();
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
