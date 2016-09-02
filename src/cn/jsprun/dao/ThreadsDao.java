package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Threads;
public interface ThreadsDao {
	public boolean addThread(Threads thread);
	public Threads findByTid(Integer id);
	public void totype(Integer typeid, String sbtid);
	public void toforum(Integer fid, String sbtid);
	public void donotupdatemember(Boolean b, String sbtid);
	public void sticklevel(Integer displayorder, String sbtid);
	public void adddigest(Integer digest, String sbtid);
	public void deleteattach(String sbtid);
	public boolean modifyThreads(Threads thread);
	public List<Threads> findThreadsByUid(int uid);
	public List<Threads>findThreadsByHql(String hql,int start,int maxrow);
	public int findThreadCount();
	public Threads findThreadsBytid(int tid);
	public int findThreadCountByHql(String hql);
	public boolean deleteThreads(Threads thread);
	public List<Threads>findThreadsByHqlTwo(String hql,int start,int maxrow);
	public Integer getThreadsCount();
	public boolean updateThreads(Threads thread);
	public List<Threads> getThreadsByThreadIdList(List<Integer> tidList);
	public Threads getLsatThread(Short fid);
	public List<Threads> getThreadsByHql(String hql);
	public void addThreads(List<Threads> threadsList);
}
