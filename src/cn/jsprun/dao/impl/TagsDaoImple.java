package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.TagsDao;
import cn.jsprun.domain.Tags;
import cn.jsprun.utils.HibernateUtil;
public class TagsDaoImple implements TagsDao {
	public Integer deleteArray(List<String> deleteList) {
		Integer num = -1;
		StringBuffer querySQL = new StringBuffer("delete from Tags as t ");
		try {
			if (deleteList.size() > 0)
				num = tagsTemplate(querySQL, deleteList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	public Integer updateToClosedTags(List<String> closedList) {
		Integer num = -1;
		StringBuffer querySQL = new StringBuffer(
				"update Tags as t set t.closed = 1");
		try {
			if (closedList.size() > 0)
				num = this.tagsTemplate(querySQL, closedList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	public Integer updateToOpenTags(List<String> openList) {
		Integer num = -1;
		StringBuffer querySQL = new StringBuffer(
				"update Tags as t set t.closed = 0");
		try {
			if (openList.size() > 0)
				num = this.tagsTemplate(querySQL, openList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	private int tagsTemplate(StringBuffer querySQL, List<String> tagsName)
			throws Exception {
		int num = -1;
		querySQL.append(" where t.tagname in (:tagnames)");
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(querySQL.toString());
			query.setParameterList("tagnames", tagsName,
					new org.hibernate.type.StringType()); 
			num += query.executeUpdate();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null) {
				tr.rollback();
				tr = null;
			}
			he.printStackTrace();
		} 
		return num;
	}
	@SuppressWarnings("unchecked")
	public List<Tags> findTagsByHql(String hql, int maxrow) {
		 Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setMaxResults(maxrow);
			List<Tags> list = query.list();
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
	public boolean updateTags(Tags tags) {
		 Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.update(tags);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public Tags findTagsByName(String name) {
		 Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Tags as t where t.tagname=?");
			query.setParameter(0, name);
			List<Tags> list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
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
	public boolean deleteTags(Tags tags) {
		 Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.delete(tags);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
}
