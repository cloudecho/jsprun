package cn.jsprun.foreg.service;
import java.util.List;
import cn.jsprun.dao.TradesDao;
import cn.jsprun.domain.Trades;
import cn.jsprun.utils.BeanFactory;
public class TradesService {
	public List<Trades> findTradesByHql(String hql) {
		return ((TradesDao) BeanFactory.getBean("tradesDao")).findTradesByHql(hql);
	}
	public List<Trades> findTradesByHql(String hql, int start, int max) {
		return ((TradesDao) BeanFactory.getBean("tradesDao")).findTradesByHql(hql, start, max);
	}
	public Trades findTradesByPid(int pid) {
		return ((TradesDao) BeanFactory.getBean("tradesDao")).findTradesByPid(pid);
	}
	public int fidTradesCountbyHql(String hql){
		return ((TradesDao) BeanFactory.getBean("tradesDao")).fidTradesCountbyHql(hql);
	}
}
