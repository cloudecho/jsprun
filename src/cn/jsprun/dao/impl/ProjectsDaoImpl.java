package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.ProjectsDao;
import cn.jsprun.domain.Projects;
import cn.jsprun.utils.HibernateUtil;
public class ProjectsDaoImpl implements ProjectsDao {
	private final static String PROJECTS_TYPE = "type";
	@SuppressWarnings("unchecked")
	public List<Projects> findAll() {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Projects> projects = null;
		try {
			projects = new ArrayList<Projects>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Projects");
			projects = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return projects;
	}
	public boolean addProject(Projects project) {
		boolean flag = false;
		if (project != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.save(project);
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
	public boolean removeProject(Projects project) {
		boolean flag = false;
		if (project != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.delete(project);
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
	public boolean updateProject(Projects project) {
		boolean flag = false;
		if (project != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.saveOrUpdate(project);
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
	public List<Projects> findByProperty(String propertyName, Object value) {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Projects> projects = null;
		try {
			projects = new ArrayList<Projects>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Projects as p where p."
					+ propertyName + "=?");
			query.setString(0, value.toString());
			projects = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return projects;
	}
	public List<Projects> findByType(String type) {
		return this.findByProperty(PROJECTS_TYPE, type);
	}
	public Projects findById(Short id)
	{
		Session session = null;
		Transaction tran = null;
		Projects project = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			project = (Projects) session.get(Projects.class, id);
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return project;
	}
	public void saveProject(Projects project) {
		Session session = null;
		Transaction tran = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			session.save(project);
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
	}
}
