package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import cn.jsprun.dao.ModworksDao;
import cn.jsprun.domain.Modworks;
import cn.jsprun.utils.HibernateUtil;
public class ModworksDaoImpl implements ModworksDao {
	public Modworks getModworksByDatelineAndModaction(String dateline, String modation, Integer uid) {
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Modworks WHERE id.uid=? AND id.modaction=? AND id.dateline=? ";
			Query query = session.createQuery(hql);
			query.setInteger(0, uid);
			query.setString(1, modation);
			query.setString(2, dateline);
			List<Modworks> list = query.list();
			transaction.commit();
			if(list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception exception) {
			if(transaction!=null){
				transaction.rollback();
			}
			exception.printStackTrace();
		}
		return null;
	}
	public void saveModworks(Modworks modworks) {
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			session.save(modworks);
			transaction.commit();
		} catch (Exception exception) {
			if(transaction!=null){
				transaction.rollback();
			}
			exception.printStackTrace();
		}
	}
}
