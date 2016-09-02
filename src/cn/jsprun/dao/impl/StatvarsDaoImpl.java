package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import cn.jsprun.dao.StatvarsDao;
import cn.jsprun.domain.Statvars;
import cn.jsprun.domain.StatvarsId;
import cn.jsprun.utils.HibernateUtil;
public class StatvarsDaoImpl implements StatvarsDao {
	public List<Statvars> getStatvarsByType(String type) {
		Transaction transaction = null;
		try{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		transaction = session.beginTransaction();
		String hql = "from Statvars as st where st.id.type=?";
		Query query = session.createQuery(hql);
		query.setString(0, type);
		List<Statvars> list = (List<Statvars>)query.list();
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
	public void updateStatvarsForMain(List<Statvars> statvarsList) {
		Transaction transaction = null;
		try{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		transaction = session.beginTransaction();
		for(int i = 0;i<statvarsList.size();i++){
			session.saveOrUpdate(statvarsList.get(i));
		}
		transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
	public Statvars getStatvarsById(StatvarsId statvarsId) {
		Transaction transaction = null;
		try{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		transaction = session.beginTransaction();
		Statvars statvars = (Statvars)session.get(Statvars.class, statvarsId);
		transaction.commit();
		return statvars;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public void saveStatvars(Statvars statvars) {
		Transaction transaction = null;
		try{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		transaction = session.beginTransaction();
		session.save(statvars);
		transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
}
