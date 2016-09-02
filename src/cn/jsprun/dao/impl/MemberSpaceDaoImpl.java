package cn.jsprun.dao.impl;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.MemberSpaceDao;
import cn.jsprun.domain.Memberspaces;
import cn.jsprun.utils.HibernateUtil;
public class MemberSpaceDaoImpl implements MemberSpaceDao {
	public boolean addMemberSpace(Memberspaces memberspace) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(memberspace);
			tr.commit();
			return true;
		}catch(HibernateException he){
			if (tr != null) {
				tr.rollback();
			}
			he.printStackTrace();
		}
		return false;
	}
	public boolean deleteMemberspace(Memberspaces memberspace) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(memberspace);
			tr.commit();
			return true;
		}catch(HibernateException he){
			if (tr != null) {
				tr.rollback();
			}
			he.printStackTrace();
		}
		return false;
	}
	public Memberspaces findMemberspace(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Memberspaces memberspace = (Memberspaces)session.get(Memberspaces.class, uid);
			tr.commit();
			return memberspace;
		}catch(HibernateException he){
			if (tr != null) {
				tr.rollback();
			}
			he.printStackTrace();
		}
		return null;
	}
	public boolean modifyMemberspace(Memberspaces memberspace) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.update(memberspace);
			tr.commit();
			return true;
		}catch(HibernateException he){
			if (tr != null) {
				tr.rollback();
			}
			he.printStackTrace();
		}
		return false;
	}
}
