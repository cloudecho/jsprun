package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Polloptions;
public interface PolloptionsDao {
	public boolean insertPolloptions(Polloptions polloptions);
	public boolean deletePolloptions(Polloptions polloptions);
	public boolean updatePolloptions(Polloptions polloptions);
	public List<Polloptions> findPolloptionsBytid(int tid);
	public Polloptions findPolloptionsById(int optionid);
}
