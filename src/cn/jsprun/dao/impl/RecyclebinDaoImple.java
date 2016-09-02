package cn.jsprun.dao.impl;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.hql.ast.QuerySyntaxException;
import cn.jsprun.dao.RecyclebinDao;
import cn.jsprun.domain.Threadsmod;
import cn.jsprun.struts.form.RecyclebinForm;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.HibernateUtil;
public class RecyclebinDaoImple implements RecyclebinDao {
	public String findByAll(RecyclebinForm recyclebinForm,String timeoffset) {
		StringBuffer querStr = new StringBuffer("FROM jrun_threads as t LEFT JOIN jrun_posts as p ON p.tid=t.tid AND p.first='1' LEFT JOIN jrun_threadsmod as m ON t.tid=m.tid LEFT JOIN jrun_forums as f ON t.fid=f.fid WHERE t.displayorder=-1");
			if (recyclebinForm.getInforum() > 0) {
				querStr.append(" AND t.fid = "+recyclebinForm.getInforum());
			}
			if (recyclebinForm.getAuthors() != null&& !recyclebinForm.getAuthors().equals("")) {
				String[] authors = recyclebinForm.getAuthors().split(",");
				StringBuffer author = new StringBuffer();
				for(int i=0;i<authors.length;i++){
					author.append(",'"+Common.addslashes(authors[i])+"'");
				}
				if(author.length()>0){
					querStr.append(" AND t.author in ("+author.substring(1)+")");
				}
			}
			if (recyclebinForm.getKeywords() != null&& !recyclebinForm.getKeywords().equals("")) {
				String[]keywords = recyclebinForm.getKeywords().split(","); 
				StringBuffer sqlkeywords =new StringBuffer(); 
				String or = "";
				for(int i=0;i<keywords.length;i++){
					sqlkeywords.append(or+"t.subject LIKE '%"+Common.addslashes(keywords[i])+"%'");
					or = " OR ";
				}
				querStr.append(" AND ("+sqlkeywords+")");
			}
			if (recyclebinForm.getAdmins() != null&& !recyclebinForm.getAdmins().equals("")) {
				String[] authors = recyclebinForm.getAdmins().split(",");
				StringBuffer author = new StringBuffer();
				for(int i=0;i<authors.length;i++){
					author.append(",'"+Common.addslashes(authors[i])+"'");
				}
				if(author.length()>0){
					querStr.append(" AND m.username in ("+author.substring(1)+")");
				}
			}
			if (Common.datecheck(recyclebinForm.getPstarttime())) {
				int time = Common.dataToInteger(recyclebinForm.getPstarttime(),"yyyy-MM-dd",timeoffset);
				querStr.append(" AND t.dateline > "+time);
			}
			if (Common.datecheck(recyclebinForm.getPendtime())) {
				int time = Common.dataToInteger(recyclebinForm.getPendtime(),"yyyy-MM-dd",timeoffset);
				querStr.append(" AND t.dateline < "+time);
			}
			if (Common.datecheck(recyclebinForm.getMstarttime())) {
				int time = Common.dataToInteger(recyclebinForm.getMstarttime(),"yyyy-MM-dd",timeoffset);
				querStr.append(" AND m.dateline > "+time);
			}
			if (Common.datecheck(recyclebinForm.getMendtime())) {
				int time = Common.dataToInteger(recyclebinForm.getMendtime(),"yyyy-MM-dd",timeoffset);
				querStr.append(" AND m.dateline < "+time);
			}
			querStr.append(" GROUP BY t.tid ORDER BY t.dateline DESC ");
		return querStr.toString();
	}
	public int undeleteArray(List<Threadsmod> updatelist) {
		Integer num = 0;
		Integer[] tids = new Integer[updatelist.size()];
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			try {
				for (int i = 0; i < updatelist.size(); i++) {
					tids[i] = updatelist.get(i).getId().getTid();
					session.save(updatelist.get(i));
				}
				Query query = session.createQuery("update Threads as t set t.displayorder=0,t.moderated=1 where t.tid in (:tids)");
				query.setParameterList("tids", tids);
				num = query.executeUpdate();
				query = session.createQuery("update Posts as p set p.invisible = 0 where p.tid in (:tids)");
				query.setParameterList("tids", tids);
				query.executeUpdate();
				tr.commit();
			} catch (QuerySyntaxException qu) {
				qu.printStackTrace();
			}
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
			num = 0;
		}
		return num;
	}
}
