package cn.jsprun.dao;
import cn.jsprun.domain.Polls;
public interface PollsDao {
	public boolean insertPolls(Polls polls);
	public boolean updatePolls(Polls polls);
	public boolean deletePolls(Polls polls);
	public Polls findPollsBytid(int tid);
}
