package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import cn.jsprun.dao.PromotionsDao;
import cn.jsprun.domain.Promotions;
import cn.jsprun.utils.HibernateUtil;
public class PromotionsDaoImpl implements PromotionsDao {
	public List<Promotions> getAllPromotions() {
		Transaction transaction = null;
		try{
			SessionFactory factory = HibernateUtil.getSessionFactory();
			Session session = factory.getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Promotions";
			Query query = session.createQuery(hql);
			List<Promotions> list = query.list();
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
