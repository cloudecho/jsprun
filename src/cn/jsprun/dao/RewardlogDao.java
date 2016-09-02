package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Rewardlog;
public interface RewardlogDao {
	public boolean deleteRewardlogByTid(int tid);
	public boolean insertRewardlog(Rewardlog rewardlog);
	public List<Rewardlog>findRewardlogByHql(String hql,int start,int maxrow);
	public int findRewardlogCountByHql(String hql);
}
