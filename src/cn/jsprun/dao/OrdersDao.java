package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Orders;
public interface OrdersDao {
	public boolean insertOrder(Orders order);
	public boolean deleteOrder(Orders order);
	public int findOrdercountorsumByHql(String hql);
	public List<Orders> findOrdersByhql(String hql);
	public Orders findOrdersById(String orderid);
}
