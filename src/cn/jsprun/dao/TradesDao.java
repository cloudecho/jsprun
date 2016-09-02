package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Trades;
public interface TradesDao {
	public List<Trades> findTradesByHql(String hql);
	public List<Trades> findTradesByHql(String hql,int start,int max);
	public Trades findTradesByPid(int pid);
	public int fidTradesCountbyHql(String hql);
}
