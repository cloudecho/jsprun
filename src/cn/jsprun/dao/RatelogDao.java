package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Ratelog;
public interface RatelogDao {
	public boolean insertRatelog(Ratelog ratelog);
	public boolean deleteRatelog(Ratelog ratelog);
	public List<Ratelog> getRatelogListByPid(List<Integer> pidList);
	public List<Ratelog> getRatelogByPid(Integer pid);
}
