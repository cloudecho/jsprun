package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.OtherSetDao;
import cn.jsprun.domain.Announcements;
import cn.jsprun.domain.Faqs;
import cn.jsprun.domain.Forumlinks;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Medals;
import cn.jsprun.domain.Settings;
import cn.jsprun.utils.HibernateUtil;
public class OtherSetDaoImpl implements OtherSetDao {
	public boolean magicBasicSet(List<Settings> setBeanList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Settings> setIt = setBeanList.iterator();
			Settings setBean = null;
			while(setIt.hasNext()){
				setBean = setIt.next();
				session.saveOrUpdate(setBean);
				session.flush();
				session.evict(setBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}	
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<Magics> queryAllMagic() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			List magicList = session.createQuery("from Magics order by displayorder,name ").list();	
			ts.commit();
			return magicList;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public List<Magics> queryAllMagic(Integer begin, Integer maxNum) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Magics ORDER BY displayorder,name ";
			Query query = session.createQuery(hql);
			query.setFirstResult(begin);
			query.setMaxResults(maxNum);
			List<Magics> magicList = query.list();
			transaction.commit();
			return magicList;
		}catch(Exception exception){
			if(transaction!= null){
				transaction.rollback();
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Magics> queryAllMagicByType(Short typeNum) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Query query = session.createQuery("from Magics m where m.type=:typeNum");
			query.setShort("typeNum", typeNum);
			List magicList = query.list();
			ts.commit();
			return magicList;			
		} catch (HibernateException e) {
			ts.rollback();
			e.printStackTrace();
		}
		return null;
	}
	public List<Magics> queryAllMagicByType(Short typeNum, Integer begin,
			Integer Max) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Query query = session.createQuery("from Magics m where m.type=:typeNum");
			query.setShort("typeNum", typeNum);
			query.setFirstResult(begin);
			query.setMaxResults(Max);
			List magicList = query.list();
			ts.commit();
			return magicList;			
		} catch (HibernateException e) {
			ts.rollback();
			e.printStackTrace();
		}
		return null;
	}
	public Magics queryMagicById(short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try{
			ts = session.beginTransaction();
			Magics magic = (Magics)session.get(Magics.class, id);
			return magic;
		}catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public List<Magics> getMagicsListByMagicIdList(List<Short> magicIdList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		List<Magics> magicsList = new ArrayList<Magics>();
		try{
			ts = session.beginTransaction();
			for(int i = 0;i<magicIdList.size();i++){
				Magics magic = (Magics)session.get(Magics.class, magicIdList.get(i));
				magicsList.add(magic);
			}
			return magicsList;
		}catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public List<Magics> getAvailableMagics() {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Magics m WHERE m.available=1";
			Query query = session.createQuery(hql);
			List<Magics> list = query.list();
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
	public boolean updateMagic(Magics magicBean){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.update(magicBean);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateMagics(List<Magics> magicBeanList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Magics> magicIt = magicBeanList.iterator();
			Magics magic = null;
			while(magicIt.hasNext()){
				magic = magicIt.next();
				session.saveOrUpdate(magic);
				session.flush();
				session.evict(magic);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean delAnyMagicByIds(String[] ids) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			int len = ids.length;
			Magics magic = null;
			for(int i=0;i<len;i++){
				magic = (Magics)session.get(Magics.class, Short.parseShort(ids[i]));
				session.delete(magic);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings({ "unchecked", "unchecked" })
	public List<Magicmarket> queryAllMagicInMarket() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			List mmBeanList = session.createQuery("from Magicmarket").list();
			ts.commit();
			return mmBeanList;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Magicmarket queryMagicMarketById(short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Magicmarket mmBean = (Magicmarket)session.get(Magicmarket.class, id);
			ts.commit();
			return mmBean;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean delAnyMagicFromMagicMarket(String[] ids) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			int len = ids.length;
			Magicmarket mmBean = null;
			for(int i=0;i<len;i++){
				mmBean = (Magicmarket)session.get(Magicmarket.class, Short.parseShort(ids[i]));
				session.delete(mmBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateMagicMarket(List<Magicmarket> mmBeanList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Magicmarket> mmBeanIt = mmBeanList.iterator();
			Magicmarket mmBean = null;
			while(mmBeanIt.hasNext()){
				mmBean = mmBeanIt.next();
				session.update(mmBean);
				session.flush();
				session.evict(mmBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public List<Magics> getMagicByIdentifier(String identifier) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = null;
		List<Magics> list = null;
		transaction = session.beginTransaction();
		String hql = "from Magics as magcis where magcis.identifier = ?";
		Query query = session.createQuery(hql);
		query.setString(0, identifier);
		list = query.list();
		transaction.commit();
		return list;
	}
	public Announcements queryAnnouncementById(short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Announcements annBean = (Announcements)session.get(Announcements.class, id);
			ts.commit();
			return annBean;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateAnnouncement(Announcements ann) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.update(ann);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean delAnyAnnouncement(String[] deletes) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction  ts = null;
		try {
			ts = session.beginTransaction();
			int len = deletes.length;
			Announcements annBean = null;
			for(int i=0;i<len;i++){
				annBean = (Announcements)session.get(Announcements.class, Short.valueOf(deletes[i]));
				session.delete(annBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateAnnouncement(List<Announcements> annBeanList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Announcements> annIt = annBeanList.iterator();
			Announcements annBean = null;
			while(annIt.hasNext()){
				annBean = annIt.next();
				session.update(annBean);
				session.flush();
				session.evict(annBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean addAnnouncement(Announcements annBean) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(annBean);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean addMedal(Medals medalBean) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(medalBean);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean delAnyMedals(String[] deletes) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			int len = deletes.length;
			Medals mBean = null;
			for(int i=0;i<len;i++){
				mBean = (Medals)session.get(Medals.class, Short.valueOf(deletes[i]));
				session.delete(mBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<Medals> queryAllMedals() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			List<Medals> medalBeanList = session.createQuery("from Medals").list();
			ts.commit();
			return medalBeanList;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Medals queryMedalById(short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Medals mBean = (Medals)session.get(Medals.class, id);
			ts.commit();
			return mBean;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateMedals(List<Medals> medalBeanList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Medals> mIt = medalBeanList.iterator();
			Medals mBean = null;
			while(mIt.hasNext()){
				mBean = mIt.next();
				session.update(mBean);
				session.flush();
				session.evict(mBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean addForumLink(Forumlinks fBean) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(fBean);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean delAnyForumLinks(String[] deletes) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			int len = deletes.length;
			Forumlinks fBean = null;
			for(int i=0;i<len;i++){
				fBean = (Forumlinks)session.get(Forumlinks.class, Short.valueOf(deletes[i]));
				session.delete(fBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<Forumlinks> queryAllForumLink() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			List<Forumlinks> flList = session.createQuery("from Forumlinks order by displayorder asc,id desc").list();
			ts.commit();
			return flList;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Forumlinks queryForumLinkById(short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Forumlinks fBean = (Forumlinks)session.get(Forumlinks.class, id);
			ts.commit();
			return fBean;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateForumLinks(List<Forumlinks> flList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Forumlinks> flIt = flList.iterator();
			Forumlinks fBean = null;
			while(flIt.hasNext()){
				fBean = flIt.next();
				session.saveOrUpdate(fBean);
				session.flush();
				session.evict(fBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean delAnyFaqs(List<Faqs> faqsListDelete) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			int len = faqsListDelete.size();
			for(int i=0;i<len;i++){
				session.delete(faqsListDelete.get(i));
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	public List<Faqs> queryAllFaq() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			List faqList = session.createQuery("from Faqs order by displayorder").list();
			ts.commit();
			return faqList;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
			return null;
		}
	}
	public Faqs queryFaqById(Short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Faqs fBean = (Faqs)session.get(Faqs.class, id);
			ts.commit();
			return fBean;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateFaq(Faqs fBean) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try{
			ts = session.beginTransaction();
			session.update(fBean);
			ts.commit();
			return true;
		}catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateFaqs(List<Faqs> faqList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Faqs> fIt = faqList.iterator();
			Faqs fBean = null;
			while(fIt.hasNext()){
				fBean = fIt.next();
				session.saveOrUpdate(fBean);
				session.flush();
				session.evict(fBean);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	public boolean addFaqs(Faqs faqs){
		if(faqs==null){
			return true;
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(faqs);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	public List<Faqs> getRootFaqs() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<Faqs> list = null;
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			String hql = "from Faqs where fpid=0";
			list = session.createQuery(hql).list();
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}
}
