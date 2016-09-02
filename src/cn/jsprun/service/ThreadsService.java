package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.ThreadsDao;
import cn.jsprun.dao.ThreadsmodDao;
import cn.jsprun.domain.Threads;
import cn.jsprun.domain.Threadsmod;
import cn.jsprun.struts.form.ThreadsForm;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
public class ThreadsService {
	public boolean addThread(Threads thread)
	{
		return ((ThreadsDao)BeanFactory.getBean("threadsDao")).addThread(thread);
	}
	public Threads getThreadsById(Integer tid){
		return ((ThreadsDao)BeanFactory.getBean("threadsDao")).findByTid(tid);
	}
	public void totype(Integer typeid, String sbtid) {
		((ThreadsDao)BeanFactory.getBean("threadsDao")).totype(typeid, sbtid);
	}
	public void toforum(Integer fid, String sbtid) {
		((ThreadsDao)BeanFactory.getBean("threadsDao")).toforum(fid, sbtid);
	}
	public void donotupdatemember(Boolean b, String sbtid) {
		((ThreadsDao)BeanFactory.getBean("threadsDao")).donotupdatemember(b, sbtid);
	}
	public void sticklevel(Integer displayorder, String sbtid) {
		((ThreadsDao)BeanFactory.getBean("threadsDao")).sticklevel(displayorder, sbtid);
	}
	public void adddigest(Integer digest, String sbtid) {
		((ThreadsDao)BeanFactory.getBean("threadsDao")).adddigest(digest, sbtid);
	}
	public void deleteattach(String sbtid) {
		((ThreadsDao)BeanFactory.getBean("threadsDao")).deleteattach(sbtid);
	}
	public List<Threads>findThreadsByHql(String hql,int start,int maxrow){
		return ((ThreadsDao)BeanFactory.getBean("threadsDao")).findThreadsByHql(hql, start, maxrow);
	}
	public Threads findByTid(Integer id){
		return ((ThreadsDao)BeanFactory.getBean("threadsDao")).findByTid(id);
	}
	public int findThreadCountByHql(String hql) {
		return ((ThreadsDao)BeanFactory.getBean("threadsDao")).findThreadCountByHql(hql);
	}
	public List<Threads>findThreadsByHqlInnerJoin(String hql,int start,int maxrow){
		return ((ThreadsDao)BeanFactory.getBean("threadsDao")).findThreadsByHqlTwo(hql, start, maxrow);
	}
	public List<Threadsmod>findByThreadsmodBytid(int tid){
		return ((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).findByThreadsBytid(tid);
	}
	public Threadsmod findByThreadsmodBytidTop1(int tid){
		return ((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).findByThreadsBytidTop1(tid);
	}
	public boolean updateThreads(Threads thread){
		return ((ThreadsDao)BeanFactory.getBean("threadsDao")).updateThreads(thread);
	}
	public List<Threads> getThreadsByThreadIdList(List<Integer> threadIdList){
		return ((ThreadsDao)BeanFactory.getBean("threadsDao")).getThreadsByThreadIdList(threadIdList);
	}
	public String batchsql(ThreadsForm tf,String timeoffset){
		StringBuffer sql = new StringBuffer("from jrun_threads t where t.displayorder>=0 ");
		String and = " and ";
		if (tf.getKeywords() != null && !tf.getKeywords().equals("")) {
			sql.append(and);
			String[] keywords = tf.getKeywords().split(",");
			sql.append("( t.subject like '%"+Common.addslashes(keywords[0])+"%'");
			if(keywords.length>1){
				for (int i = 1; i < keywords.length; i++) {
					sql.append(" or t.subject like '%" + Common.addslashes(keywords[i]) + "%' ");
				}
			}
			sql.append(")");
		}
		if (tf.getUsers() != null && !tf.getUsers().equals("")) {
			String cins ="" ;
			if (!tf.getCins()) {
				cins = " binary ";
			} else {
				cins = "";
			}
			sql.append(and);
			String[] userNames = tf.getUsers().split(",");
			sql.append("(" +cins + " t.author ='" + Common.addslashes(userNames[0]) + "' ");
			if(userNames.length>1){
				for (int i = 1; i < userNames.length; i++) {
				sql.append(" or "+cins+"t.author = '" + Common.addslashes(userNames[i]) + "' ");
				}
			}
			sql.append(")");
		}
		if (Common.datecheck(tf.getEndtime())) {
				int endtime = Common.dataToInteger(tf.getEndtime(),"yyyy-MM-dd",timeoffset);
				sql.append(and);
				sql.append("t.dateline<="+endtime);
		}
		if (Common.datecheck(tf.getStarttime())) {
				int startime = Common.dataToInteger(tf.getStarttime(),"yyyy-MM-dd",timeoffset);
				sql.append(and);
				sql.append("t.dateline>"+startime);
		}
		if (tf.getInforum() > 0) {
			sql.append(and);
			sql.append("t.fid = "+tf.getInforum());
		}
		if (tf.getIntype() > 0) {
			sql.append(and);
			sql.append("t.typeid = "+tf.getIntype());
		}
		if (tf.getViewsless() > 0 ) {
			sql.append(and);
			sql.append("t.views<"+tf.getViewsless());
		}
		if (tf.getViewsmore() > 0 ) {
			sql.append(and);
			sql.append("t.views>"+tf.getViewsmore());
		}
		if (tf.getRepliesless() > 0) {
			sql.append(and);
			sql.append("t.replies<"+tf.getRepliesless());
		}
		if (tf.getRepliesmore() > 0) {
			sql.append(and);
			sql.append("t.replies>"+tf.getRepliesmore());
		}
		if (tf.getReadpermmore() > 0) {
			sql.append(and);
			sql.append("t.readperm>"+tf.getReadpermmore());
		}
		if (tf.getPricemore() > 0) {
			sql.append(and);
			sql.append("t.price>"+tf.getPricemore());
		}
		if (tf.getNoreplydays() > 0) {
			sql.append(and);
			int time = Common.time();
			time = time-tf.getNoreplydays()*86400;
			sql.append("t.lastpost<"+time);
		}
		if (tf.getSpecialthread() > 0 && tf.getSpecial()!=null) {
			Integer[] ins = tf.getSpecial();
			sql.append(and);
			if (tf.getSpecialthread() == 1) {
				StringBuffer insinfo = new StringBuffer();
				for(int p:ins){
					insinfo.append(","+p);
				}
				sql.append("t.special in ( "+insinfo.substring(1)+" )");
			}
			if (tf.getSpecialthread() == 2) {
				StringBuffer insinfo = new StringBuffer();
				for(int p:ins){
					insinfo.append(","+p);
				}
				sql.append("t.special not in ( "+insinfo.substring(1)+" )");
			}
		}
		if (tf.getSticky() > 0) {
			sql.append(and);
				if (tf.getSticky() == 1) {
					sql.append("t.displayorder>0");
				}
				if (tf.getSticky() == 2) {
					sql.append("t.displayorder<=0");
				}
		}
		if (tf.getDigest() > 0) {
			sql.append(and);
				if (tf.getDigest() == 1) {
					sql.append("t.digest>0");
				}
				if (tf.getDigest() == 2) {
					sql.append("t.digest<=0");
				}
		}
		if (tf.getBlog() > 0) {
			sql.append(and);
				if (tf.getBlog() == 1) {
					sql.append("t.blog>0");
				}
				if (tf.getBlog() == 2) {
					sql.append("t.blog<=0");
				}
		}
		if (tf.getAttach() > 0) {
			sql.append(and);
				if (tf.getAttach() == 1) {
					sql.append("t.attachment=1");
				}
				if (tf.getAttach() == 2) {
					sql.append("t.attachment=0");
				}
		}
		if (tf.getRate() > 0) {
			sql.append(and);
				if (tf.getRate() == 1) {
					sql.append("t.rate<>0");
				}
				if (tf.getRate() == 2) {
					sql.append("t.rate=0");
				}
		}
		if (tf.getHighlight() > 0) {
			sql.append(and);
				if (tf.getHighlight() == 1) {
					sql.append("t.highlight>0");
				}
				if (tf.getHighlight() == 2) {
					sql.append("t.highlight<=0");
				}
		}
		sql.append(" order by t.displayorder desc ");
		return sql.toString();
	}
}
