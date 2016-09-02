package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Threadsmod;
public interface ThreadsmodDao {
	public void addThreadsmod(Threadsmod threadsmod);
	public Integer saveList(List<Threadsmod> updatelist);
	public Threadsmod findByThreadsIdDEL(Integer tid);
	public List<Threadsmod>findByThreadsBytid(int tid);
	public Threadsmod findByThreadsBytidTop1(int tid);
	public void deleteThreadsmod(Integer tid);
}
