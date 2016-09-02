package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import cn.jsprun.dao.MagicMarketDao;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.utils.HibernateUtil;
public class MagicMarketDaoImpl implements MagicMarketDao {
	public List<Magicmarket> getAllMagicFromMarket(int firstNmu,int maxNum) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			String hql = "from Magicmarket";
			Query query = session.createQuery(hql);
			query.setFirstResult(firstNmu);
			query.setMaxResults(maxNum);
			List<Magicmarket> list = query.list();
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
	public List<Magicmarket> getMagicFormMarketByMagicId(Short magicId,String orderby,String ascDesc,int firstNmu,int maxNum) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("from Magicmarket ");
			if(magicId!=null&&magicId!=0){
				buffer.append("where magicid="+magicId+" ");
			}
			if(orderby!=null&&!orderby.equals("")){
				buffer.append("order by "+orderby+" ");
				if(ascDesc!=null&&!ascDesc.equals("")){
					buffer.append(ascDesc);
				}
			}
			String hql = buffer.toString();
			Query query = session.createQuery(hql);
			query.setFirstResult(firstNmu);
			query.setMaxResults(maxNum);
			List<Magicmarket> list = query.list();
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
	public List<Magicmarket> getMagicFromMarketByUid(Integer uid,int firstNmu,int maxNum) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			String hql = "from Magicmarket as mm where mm.uid=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, uid);
			query.setFirstResult(firstNmu);
			query.setMaxResults(maxNum);
			List<Magicmarket> list = query.list();
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
	public Magicmarket getMagicmarketById(Short magicmarketId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			Magicmarket magicmarket = (Magicmarket)session.get(Magicmarket.class, magicmarketId);
			transaction.commit();
			return magicmarket;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
}
