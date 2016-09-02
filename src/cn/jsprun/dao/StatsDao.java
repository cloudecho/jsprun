package cn.jsprun.dao;
import java.util.List;
import java.util.Map;
import cn.jsprun.domain.Stats;
import cn.jsprun.domain.StatsId;
public interface StatsDao {
	public Map<String,Stats> workForFluxStatistic();
	public List<Stats> getStatsByType(String type);
	public Stats getStatsById(StatsId statsId);
}
