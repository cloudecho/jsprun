package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Buddys;
public interface BuddysDao {
	public boolean deleteBuddysByUid(int uid);
	public boolean insertBuddys(Buddys buddy);
	public List<Buddys> findBuddysByUid(int uid);
}
