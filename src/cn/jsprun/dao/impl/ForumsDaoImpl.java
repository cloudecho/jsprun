package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import cn.jsprun.dao.ForumsDao;
import cn.jsprun.domain.Forums;
import cn.jsprun.utils.HibernateUtil;
public class ForumsDaoImpl implements ForumsDao {
	public boolean addForum(Forums forum) {
		boolean flag = false;
		if (forum != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.save(forum);
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
	public Forums findById(Short fid) {
		Session session = null;
		Transaction tran = null;
		Forums forum = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			forum = (Forums) session.get(Forums.class, fid);
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return forum;
	}
	public List<Forums> getForumsList(List<Short> fidList) {
		Session session = null;
		Transaction tran = null;
		Forums forum = null;
		List<Forums> list = new ArrayList<Forums>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			for(int i = 0;i<fidList.size();i++){
				forum = (Forums) session.get(Forums.class, fidList.get(i));
				if(forum!=null){
					list.add(forum);
				}else{
					System.out.println("Can not get Forums By ID");
				}
			}
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}
	public List<Forums> findByType(String type) {
		return this.findByProperty("type", type);
	}
	@SuppressWarnings("unchecked")
	public List<Forums> findByProperty(String propertyName, Object value) {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Forums> forums = null;
		try {
			forums = new ArrayList<Forums>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Forums as f where f."
					+ propertyName + "=? order by displayorder");
			query.setString(0, value.toString());
			forums = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return forums;
	}
	public List<Forums> findByUp(Short fup) {
		return this.findByProperty("fup", fup.toString());
	}
	public boolean removeForum(Forums forum) {
		boolean flag = false;
		if (forum != null) {
			Session session = null;
			Transaction tran = null;
			Query query=null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				query = session.createQuery("delete from Forumfields as f where f.fid="+forum.getFid());
				query.executeUpdate();
				session.delete(forum);
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
	public boolean updateForum(Forums forum) {
		boolean flag = false;
		if (forum != null) {
			Session session = null;
			Transaction tran = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				tran = session.beginTransaction();
				session.saveOrUpdate(forum);
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
	public boolean updatePost(Short sourcefid, Short targetfid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		Query query = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("update Posts as p set p.fid="+targetfid+" where p.fid ="+sourcefid);
			query.executeUpdate();
			flag = true;
			tran.commit();
		} catch (HibernateException e) {
			flag = false;
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return flag;
	}
	public boolean updateThread(Short sourcefid, Short targetfid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		Query query = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("update Threads as t set t.fid="+targetfid+" where t.fid ="+sourcefid);
			query.executeUpdate();
			flag = true;
			tran.commit();
		} catch (HibernateException e) {
			flag = false;
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	public List<Forums> findAll() {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Forums> forums = null;
		try {
			forums = new ArrayList<Forums>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Forums order by displayorder");
			forums = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return forums;
	}
	public List<Forums> findExceptFupEqualZero() {
		Session session = null;
		Transaction transaction = null;
		List<Forums> forums = null;
		String hql = "from Forums where fup <> 0";
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(hql);
			forums = query.list();
			transaction.commit();
		}catch(Exception exception){
			if(transaction!=null){
				transaction.rollback();
			}
			exception.printStackTrace();
		}
		return forums;
	}
	@SuppressWarnings("unchecked")
	public List<Forums> findFourmsByHql(String hql,int start,int maxrow) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(maxrow);
			List<Forums> list = query.list();
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
	public int findFourmsCount() {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	Transaction tr = null;
	try{
		tr = session.beginTransaction();
		Query query = session.createQuery("select count(*) from Forums as f where f.status=1");
		List list = query.list();
		tr.commit();
		if(list!=null && list.size()>0){
			return Integer.valueOf(list.get(0)+"");
		}
	}catch(HibernateException e){
		if(tr!=null){
			tr.rollback();
		}
		e.printStackTrace();
	}
		return 0;
	}
	public Integer findForumsCountWithoutGroup() {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("select count(*) from Forums as f where f.status=1 AND f.type<>'group'");
			List list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return Integer.valueOf(list.get(0)+"");
			}
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public Forums getHotForumsInfo(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			String hql = "FROM Forums as fo WHERE fo.status=1 ORDER BY fo.posts DESC";
			Query query = session.createQuery(hql);
			query.setMaxResults(1);
			List<Forums> list = query.list();
			tr.commit();
			if(list.size()>0){
				return list.get(0);
			}
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
}
