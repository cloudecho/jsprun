package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.AttachmentsDao;
import cn.jsprun.domain.Attachments;
import cn.jsprun.domain.Threads;
import cn.jsprun.struts.form.AttachmentsForm;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.HibernateUtil;
public class AttachmentsDaoImple implements AttachmentsDao {
	public Integer deleteArray(String[] aids) {
		Integer deleteNumber = -1;
		Transaction tr = null;
		try {
			Integer[] ids = new Integer[aids.length];
			for (int i = 0; i < aids.length; i++) {
				ids[i] = Integer.valueOf(aids[i]);
			}
			String queryStr = "delete from Attachments as a where a.aid in (:ids)";
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(queryStr);
			query.setParameterList("ids", ids,new org.hibernate.type.IntegerType());
			deleteNumber = query.executeUpdate();
			session.flush();
			tr.commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			if (tr != null)
				tr.rollback();
			tr = null;
		} 
		return deleteNumber;
	}
	public String findByAttachmentsForm(AttachmentsForm attachmentsForm) {
		StringBuffer querStr = new StringBuffer("from jrun_attachments a , jrun_posts p, jrun_threads t,jrun_forums f WHERE t.tid=a.tid AND f.fid=p.fid AND t.displayorder>='0' AND p.invisible='0' AND a.pid=p.pid ");
		String and = " AND ";
			if (attachmentsForm.getInforum() > 0) {
				querStr.append(and);
				querStr.append(" p.fid = " + attachmentsForm.getInforum()+ "");
			}
			if (attachmentsForm.getSizeless() > 0) {
				querStr.append(and);
				querStr.append(" a.filesize < "+ attachmentsForm.getSizeless() + "");
			}
			if (attachmentsForm.getSizemore() > 0) {
				querStr.append(and);
				querStr.append(" a.filesize > "+ attachmentsForm.getSizemore() + "");
			}
			if (attachmentsForm.getDlcountless() > 0) {
				querStr.append(and);
				querStr.append(" a.downloads < " + attachmentsForm.getDlcountless() + "");
			}
			if (attachmentsForm.getDlcountmore() > 0) {
				querStr.append(and);
				querStr.append(" a.downloads > " + attachmentsForm.getDlcountmore() + "");
			}
			if (attachmentsForm.getDaysold() > 0) {
				int time = Common.time();
				querStr.append(and);
				querStr.append(" p.dateline <= " + (time-attachmentsForm.getDaysold()*86400) + "");
			}
			if (attachmentsForm.getFilename() != null && !attachmentsForm.getFilename().equals("")) {
				querStr.append(and);
				querStr.append(" a.filename like '%" + Common.addslashes(attachmentsForm.getFilename()) + "%'");
			}
			if (attachmentsForm.getAuthor() != null && !attachmentsForm.getAuthor().equals("")) {
				querStr.append(and);
				querStr.append(" p.author ='" + Common.addslashes(attachmentsForm.getAuthor())+ "'");
			}
			if (attachmentsForm.getKeywords() != null && !attachmentsForm.getKeywords().equals("")) {
				String[] keys = attachmentsForm.getKeywords().split(",");
				if (keys != null && keys.length > 0) {
					String or = "";
					StringBuffer sqlkeywords = new StringBuffer();
					for (int i = 0; i < keys.length; i++) {
						sqlkeywords.append(or+" a.description like '%" + Common.addslashes(keys[i]) + "%'");
						or = " OR ";
					}
					querStr.append(and);
					querStr.append(" ("+sqlkeywords+") ");
				}
			}
		return querStr.toString();
	}
	public List<Attachments> findByPostsID(Integer pid) {
		List<Attachments> list = null;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			tr = session.beginTransaction();
			Query query = session
					.createQuery("from Attachments as a where a.pid = :id");
			query.setInteger("id", pid);
			list = query.list();
			session.flush();
			tr.commit();
		} catch (HibernateException he) {
			if (tr != null) {
				tr.rollback();
				tr = null;
			}
			he.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Attachments> findAttchmentsByJs(String hql, int startrow, int maxrow) {
		List<Attachments> attlist = new ArrayList<Attachments>();
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(startrow);
			query.setMaxResults(maxrow);
			List<Attachments> list = query.list();
			tr.commit();
			Iterator it = list.iterator();
			while(it.hasNext()){
				Object[] os = (Object[]) it.next();
				Attachments a = (Attachments) os[0];
				Threads t = (Threads) os[1];
				attlist.add(a);
			}
			return attlist;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Attachments findAttachmentsById(int aid) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Attachments attc = (Attachments)session.get(Attachments.class, aid);
			tr.commit();
			return attc;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public int insertAttachments(Attachments attachments) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.save(attachments);
			tr.commit();
			return attachments.getAid();
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public boolean deleteAttachments(Attachments attachments) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.delete(attachments);
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
	public boolean updateAttachments(Attachments attachments) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			session.update(attachments);
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
	public List<Attachments> findByattaByTid(Integer tid) {
		Transaction tr = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery("from Attachments as a where a.tid=?");
			query.setParameter(0, tid);
			List<Attachments> list = query.list();
			tr.commit();
			return list;
		}catch(Exception e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public List<Attachments> getAttachmentListByTid(Integer tid) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Attachments AS a WHERE a.tid=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, tid);
			List<Attachments> list = query.list();
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
	public void updateAttachment(List<Attachments> attachmentsList) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			for(int i = 0;i<attachmentsList.size();i++){
				session.update(attachmentsList.get(i));
			}
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
}
