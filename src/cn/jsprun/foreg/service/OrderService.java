package cn.jsprun.foreg.service;
import java.util.List;
import cn.jsprun.dao.OrdersDao;
import cn.jsprun.domain.Orders;
import cn.jsprun.utils.BeanFactory;
public class OrderService {
	public boolean insertOrder(Orders order){
		return ((OrdersDao)BeanFactory.getBean("orderDao")).insertOrder(order); 
	}
	public boolean deleteOrder(Orders order){
		return ((OrdersDao)BeanFactory.getBean("orderDao")).deleteOrder(order);
	}
	public int findOrdercountorsumByHql(String hql){
		return ((OrdersDao)BeanFactory.getBean("orderDao")).findOrdercountorsumByHql(hql);
	}
	public List<Orders> findOrdersByhql(String hql){
		return ((OrdersDao)BeanFactory.getBean("orderDao")).findOrdersByhql(hql);
	}
	public Orders findOrdersById(String orderid){
		return ((OrdersDao)BeanFactory.getBean("orderDao")).findOrdersById(orderid);
	}
}
