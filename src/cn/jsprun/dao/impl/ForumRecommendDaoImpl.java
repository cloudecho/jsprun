package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import cn.jsprun.dao.ForumRecommendDao;
import cn.jsprun.domain.Forumrecommend;
import cn.jsprun.utils.HibernateUtil;
public class ForumRecommendDaoImpl implements ForumRecommendDao {
	public void addForumrecommend(List<Forumrecommend> forumcommendList) {
		Transaction transaction = null;
		try{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		transaction = session.beginTransaction();
		for(int i = 0;i<forumcommendList.size();i++){
			session.save(forumcommendList.get(i));
		}
		transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
	public void deleteForumrecommend(List<Integer> tidList) {
		Transaction transaction = null;
		try{
			SessionFactory factory = HibernateUtil.getSessionFactory();
			Session session = factory.getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "DELETE Forumrecommend AS f WHERE f.tid=?";
			Query query = session.createQuery(hql);
			for(int i = 0;i<tidList.size();i++){
				query.setInteger(0, tidList.get(i));
				query.executeUpdate();
			}
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
	public List<Forumrecommend> getForumrecommendByTid(List<Integer> tidList) {
		Transaction transaction = null;
		try{
			SessionFactory factory = HibernateUtil.getSessionFactory();
			Session session = factory.getCurrentSession();
			transaction = session.beginTransaction();
			List<Forumrecommend> list = new ArrayList<Forumrecommend>();
			for(int i = 0;i<tidList.size();i++){
				list.add((Forumrecommend)session.get(Forumrecommend.class, tidList.get(i)));
			}
			transaction.commit();
			return list;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
}
