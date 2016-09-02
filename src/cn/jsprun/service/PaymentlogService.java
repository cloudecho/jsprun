package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.PaymentlogDao;
import cn.jsprun.domain.Paymentlog;
import cn.jsprun.utils.BeanFactory;
public class PaymentlogService {
	public boolean insertPaymentlog(Paymentlog paymentlog){
		PaymentlogDao paylogDao = (PaymentlogDao)BeanFactory.getBean("paymentlogDao");
		return paylogDao.insertPaymentlog(paymentlog);
	}
	public boolean modifyPaymentlog(Paymentlog paymentlog){
		PaymentlogDao paylogDao = (PaymentlogDao)BeanFactory.getBean("paymentlogDao");
		return paylogDao.modifyPaymentlog(paymentlog);
	}
	public boolean deletePaymentlog(Paymentlog paymentlog){
		PaymentlogDao paylogDao = (PaymentlogDao)BeanFactory.getBean("paymentlogDao");
		return paylogDao.deletePaymentlog(paymentlog);
	}
	public List<Paymentlog> findPaymentlogByHql(String hql,int start,int maxrow){
		PaymentlogDao paylogDao = (PaymentlogDao)BeanFactory.getBean("paymentlogDao");
		return paylogDao.findPaymentlogByHql(hql, start, maxrow);
	}
	public int findPaymentlogCountByhql(String hql){
		PaymentlogDao paylogDao = (PaymentlogDao)BeanFactory.getBean("paymentlogDao");
		return paylogDao.findPaymentlogCountByhql(hql);
	}
}
