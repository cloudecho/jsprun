package cn.jsprun.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.StatsDao;
import cn.jsprun.domain.Stats;
import cn.jsprun.domain.StatsId;
import cn.jsprun.utils.HibernateUtil;
public class StatsDaoImpl implements StatsDao {
	public Map<String,Stats> workForFluxStatistic(){
		Map<String,Stats> map = new HashMap<String, Stats>();
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Stats AS st WHERE st.id.type=? ORDER BY st.count DESC";
			String hql2 = "FROM Stats AS st WHERE st.id.type=?";
			Query query_hour = session.createQuery(hql);
			query_hour.setMaxResults(1);
			query_hour.setString(0, "hour");
			Query query_week = session.createQuery(hql);
			query_week.setMaxResults(1);
			query_week.setString(0, "week");
			Query query_month = session.createQuery(hql);
			query_month.setMaxResults(1);
			query_month.setString(0, "month");
			Query query_total = session.createQuery(hql2);
			query_total.setString(0, "total");
			List<Stats> statsList_hour = (List<Stats>)query_hour.list();
			List<Stats> statsList_week = (List<Stats>)query_week.list();
			List<Stats> statsList_month = (List<Stats>)query_month.list();
			List<Stats> statsList_total = (List<Stats>)query_total.list();
			transaction.commit();
			map.put("hour", statsList_hour.size()>0?statsList_hour.get(0):null);
			map.put("week", statsList_week.size()>0?statsList_week.get(0):null);
			map.put("month", statsList_month.size()>0?statsList_month.get(0):null);
			for(Stats stats : statsList_total){
				map.put(stats.getId().getVariable(), stats);
			}
			return map;
		}catch (Exception e) {
			e.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
		return null;
	}
	public List<Stats> getStatsByType(String type) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Stats as st where st.id.type=?";
			Query query = session.createQuery(hql);
			query.setString(0, type);
			List<Stats> list = (List<Stats>)query.list();
			transaction.commit();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
		return null;
	}
	public Stats getStatsById(StatsId statsId) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Stats stats =(Stats) session.get(Stats.class, statsId);
			transaction.commit();
			return stats;
		}catch (Exception e) {
			e.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
		return null;
	}
}
