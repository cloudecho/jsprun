package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Mythreads;
public interface MythreadDao {
	public boolean deleteMythreadByUid(int uid);
	public boolean insertMythread(Mythreads mythread);
	public List<Mythreads>findMythreadByUid(int uid);
	public void deleteMythread(Integer tid);
}
