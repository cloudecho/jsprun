package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Paymentlog;
public interface PaymentlogDao {
	public boolean insertPaymentlog(Paymentlog paymentlog);
	public boolean modifyPaymentlog(Paymentlog paymentlog);
	public boolean deletePaymentlog(Paymentlog paymentlog);
	public List<Paymentlog> findPaymentlogByHql(String hql,int start,int maxrow);
	public int findPaymentlogCountByhql(String hql);
}
