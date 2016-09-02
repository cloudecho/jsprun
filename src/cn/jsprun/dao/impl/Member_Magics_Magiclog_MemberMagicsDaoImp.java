package cn.jsprun.dao.impl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import cn.jsprun.dao.Member_Magics_Magiclog_MemberMagicsDao;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Membermagics;
import cn.jsprun.domain.Members;
import cn.jsprun.utils.HibernateUtil;
public class Member_Magics_Magiclog_MemberMagicsDaoImp implements Member_Magics_Magiclog_MemberMagicsDao {
	public boolean userBuyMagic(Magics magic, Magiclog magiclog, Members member,Membermagics memberMagics) {
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			session.update(magic);
			session.save(magiclog);
			session.update(member);
			session.saveOrUpdate(memberMagics);
			transaction.commit();
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return false;
		}
	}
}
