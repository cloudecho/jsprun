package cn.jsprun.service;
import cn.jsprun.dao.RatelogDao;
import cn.jsprun.domain.Ratelog;
import cn.jsprun.utils.BeanFactory;
public class RatelogService {
	public boolean insertRatelog(Ratelog ratelog){
		return ((RatelogDao)BeanFactory.getBean("ratelogDao")).insertRatelog(ratelog);
	}
	public boolean deleteRatelog(Ratelog ratelog){
		return ((RatelogDao)BeanFactory.getBean("ratelogDao")).deleteRatelog(ratelog);
	}
}
