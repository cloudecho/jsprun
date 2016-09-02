package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.FavoritesDao;
import cn.jsprun.domain.Favorites;
import cn.jsprun.utils.HibernateUtil;
public class FavoritesDaoImpl implements FavoritesDao {
	public boolean deleteFavoritesByUid(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Favorites as f where f.id.uid=?");
			query.setParameter(0, uid);
			query.executeUpdate();
			tr.commit();
			return true;
		}catch(HibernateException er){
			if(tr!=null){
				tr.rollback();
			}
			er.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<Favorites> findFavoritesByUid(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Favorites as f where f.id.uid=?");
			query.setParameter(0, uid);
			List<Favorites> list = query.list();
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
	public boolean insertFavorites(Favorites favorite) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(favorite);
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
	public List<Favorites> findFavoritesByHql(String hql, int startrow, int maxlength) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(startrow);
			query.setMaxResults(maxlength);
			List<Favorites> list = query.list();
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
	public boolean deleteFavoritesByTid(int tid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Favorites as f where f.id.tid=?");
			query.setParameter(0, tid);
			query.executeUpdate();
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
	public boolean delteFavoritesByFid(short fid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Favorites as f where f.id.fid=?");
			query.setParameter(0, fid);
			query.executeUpdate();
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
	public int findFavoritesCountByHql(String hql) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			List list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return Integer.valueOf(list.get(0).toString());
			}
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
}
