package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Subscriptions;
public interface SubscriptiosDao {
	public boolean deleteSubscriptionByUid(int uid);
	public boolean insertSubscritption(Subscriptions subscrition);
	public List<Subscriptions> findSubscriptionsByUid(int uid);
}
