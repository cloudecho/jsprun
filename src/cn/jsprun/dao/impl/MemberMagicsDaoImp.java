package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.MemberMagicsDao;
import cn.jsprun.domain.Membermagics;
import cn.jsprun.utils.HibernateUtil;
public class MemberMagicsDaoImp implements MemberMagicsDao {
	@SuppressWarnings("unchecked")
	public List<Membermagics> getMemberMagics(int userid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			String hql = "from Membermagics as mm where mm.id.uid=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, userid);
			List<Membermagics> list = query.list();
			transaction.commit();
			return list;
		}catch(Exception e){
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Membermagics> getMemberMagics(int userid, short magicid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			String hql = "from Membermagics as mm where mm.id.uid=? and mm.id.magicid=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, userid);
			query.setShort(1, magicid);
			List<Membermagics> list = query.list();
			transaction.commit();
			return list;
		}catch(Exception e){
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
}
