package cn.jsprun.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.WordsDao;
import cn.jsprun.domain.Words;
import cn.jsprun.utils.HibernateUtil;
import cn.jsprun.struts.form.PageForm;
public class WordsDaoImple implements WordsDao {
	public WordsDaoImple() {
	}
	public Map getAllWords(int currentpage) {
		Map map = new HashMap();
		List<Words> list = null;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Words as w");
			session.flush();
			int totalsize = query.list().size();
			int pagesize = 30;
			int totalpage = 1;
			int startid = 0;
			if (totalsize > pagesize) {
				if (totalsize % pagesize == 0) {
					totalpage = (int) ((double) totalsize / (double) pagesize);
				} else {
					totalpage = (int) (1.0 + (double) totalsize
							/ (double) pagesize);
				}
			}
			if (currentpage > 1) {
				if (currentpage > totalpage) {
					currentpage = totalpage;
				}
			} else {
				currentpage = 1;
			}
			startid = (currentpage - 1) * pagesize;
			query.setFetchSize(pagesize);
			query.setFirstResult(startid);
			query.setMaxResults(pagesize);
			list = query.list();
			map.put(PageForm.LIST, list);
			map.put(PageForm.CURRENTPAGE, currentpage);
			map.put(PageForm.TOTALPAGE, totalpage);
			map.put(PageForm.TOTALSIZE, totalsize);
			session.flush();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		if (tr != null)
			tr.commit();
		return map;
	}
	public Integer delteCollection(String[] ids) {
		int count = -1;
		StringBuffer deleteString = new StringBuffer("delete from Words as w");
		if (ids != null && ids.length > 0) {
			deleteString.append(" where w.id in (");
			for (int i = 0; i < ids.length; i++) {
				if (ids[i] != null) {
					deleteString.append(ids[i]);
					deleteString.append(",");
				}
			}
			String querystr = deleteString.substring(0,deleteString.length() - 1);
			querystr = querystr + ")";
			Transaction tr = null;
			try {
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				tr = session.beginTransaction();
				Query query = session.createQuery(querystr);
				count = query.executeUpdate();
				session.flush();
			} catch (HibernateException he) {
				if (tr != null)
					tr.rollback();
				tr = null;
				he.printStackTrace();
			}
			if (tr != null)
				tr.commit();
		} else {
			return count;
		}
		return count;
	}
	public boolean save(Words words) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.save(words);
			tr.commit();
			return true;
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return false;
	}
	public boolean findByFindPorperty(Words words) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Words as w where w.find like :find");
			query.setString("find", words.getFind());
			List list = query.list();
			tr.commit();
			if (list.size() > 0) {
				return true;
			}
		} catch (HibernateException he) {
			if (tr != null) {
				tr.rollback();
				tr = null;
			}
			he.printStackTrace();
		}
		return false;
	}
	public Integer updateWords(Words words) {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction(); 
			Query query = session
					.createQuery("update Words as w set w.replacement=:replacement where w.find like :find");
			query.setString("replacement", words.getReplacement());
			query.setString("find", words.getFind());
			query.executeUpdate();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null) {
				tr.rollback();
				tr = null;
			}
			he.printStackTrace();
		} 
		return 1;
	}
	public Integer updateWordsList(List<Words> wordsList,List<Words> updateReplaceList) {
		int updateNumber = 0;
		if (wordsList == null || wordsList.size() <= 0)
		return updateNumber;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			for (int i = 0; i < wordsList.size(); i++) {
				Words words = wordsList.get(i);
				if (words != null && words.getFind() != null) {
					Query query = session.createQuery("update Words as w set w.find = :find  where w.id=:id");
					query.setString("find", words.getFind());
					query.setShort("id", words.getId());
					updateNumber += query.executeUpdate();
				}
			}
			for (int i = 0; i < updateReplaceList.size(); i++) {
				Words words = updateReplaceList.get(i);
				Query query = session.createQuery("update Words as w set w.replacement=:replacement where w.id=:id");
				query.setString("replacement", words.getReplacement());
				query.setShort("id", words.getId());
				updateNumber += query.executeUpdate();
			}
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public String downWordsAll() {
		StringBuffer sb = new StringBuffer();
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Words");
			List<Words> list = query.list();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Words w = list.get(i);
					sb.append(w.getFind());
					if (!w.getReplacement().equals("**")) {
						sb.append("=");
						sb.append(w.getReplacement());
					}
					sb.append("\n");
				}
			}
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null) {
				tr.rollback();
				tr = null;
			}
			he.printStackTrace();
		} 
		return sb.toString();
	}
	public Integer deleteAll() throws Exception {
		int num = 0;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Words as w");
			num = query.executeUpdate();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null) {
				tr.rollback();
				tr = null;
			}
			he.printStackTrace();
			throw he; 
		} 
		return num;
	}
}
