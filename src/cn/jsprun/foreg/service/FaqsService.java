package cn.jsprun.foreg.service;
import java.util.List;
import cn.jsprun.dao.FaqsDao;
import cn.jsprun.domain.Faqs;
import cn.jsprun.utils.BeanFactory;
public class FaqsService {
	public List<Faqs> findFaqsByfpid(short fpid,short id){
		return ((FaqsDao)BeanFactory.getBean("faqDao")).findFaqsByfpid(fpid, id);
	}
	public List<Faqs> findFaqsParents(){
		return ((FaqsDao)BeanFactory.getBean("faqDao")).findFaqsParents();
	}
	public List<Faqs> findFaqsChilds(){
		return ((FaqsDao)BeanFactory.getBean("faqDao")).findFaqsChilds();
	}
	public List<Faqs> findFaqsByHql(String hql){
		return ((FaqsDao)BeanFactory.getBean("faqDao")).findFaqsByHql(hql);
	}
	public Faqs findFaqlById(short id){
		return ((FaqsDao)BeanFactory.getBean("faqDao")).findFaqlById(id);
	}
}
