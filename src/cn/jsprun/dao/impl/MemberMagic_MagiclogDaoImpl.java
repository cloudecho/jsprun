package cn.jsprun.dao.impl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import cn.jsprun.dao.MemberMagic_MagiclogDao;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.domain.Membermagics;
import cn.jsprun.utils.HibernateUtil;
public class MemberMagic_MagiclogDaoImpl implements MemberMagic_MagiclogDao {
	public void dropMagicOfOnebody(Magiclog magiclog,Membermagics memberMagics)  throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			if(memberMagics.getNum()==0){
				session.delete(memberMagics);
			}else if(memberMagics.getNum()<0){
				throw new Exception("Magic Num Is Invalid After Operation");
			}else{
				session.update(memberMagics);
			}
			session.save(magiclog);
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			throw new Exception();
		}
	}
	public void sendMagic(Magiclog magiclog, Membermagics memberMagics_send, Membermagics memberMagics_get) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			if(memberMagics_send.getNum()==0){
				session.delete(memberMagics_send);
			}else if(memberMagics_send.getNum()<0){
				throw new Exception("Magic Num Is Invalid After Operation");
			}else{
				session.update(memberMagics_send);
			}
			session.saveOrUpdate(memberMagics_get);
			session.save(magiclog);
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			throw new Exception();
		}
	}
	public void sellMagic(Magiclog magiclog, Membermagics memberMagics, Magicmarket magicmarket) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			if(memberMagics.getNum()==0){
				session.delete(memberMagics);
			}else if(memberMagics.getNum()<0){
				throw new Exception("Magic Num Is Invalid After Operation");
			}else{
				session.update(memberMagics);
			}
			session.save(magiclog);
			session.save(magicmarket);
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			throw new Exception();
		}
	}
}
