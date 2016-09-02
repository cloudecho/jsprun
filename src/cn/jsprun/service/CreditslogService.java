package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.CreditslogDao;
import cn.jsprun.domain.Creditslog;
import cn.jsprun.utils.BeanFactory;
public class CreditslogService {
	public boolean insertCreditslog(Creditslog creditslog){
		CreditslogDao credisDao = (CreditslogDao)BeanFactory.getBean("creditslogDao");
		return credisDao.insertCreditslog(creditslog);
	}
	public boolean modifyCreditslog(Creditslog creditslog){
		CreditslogDao credisDao = (CreditslogDao)BeanFactory.getBean("creditslogDao");
		return credisDao.modifyCreditslog(creditslog);
	}
	public boolean deleteCreditslog(Creditslog creditslog){
		CreditslogDao credisDao = (CreditslogDao)BeanFactory.getBean("creditslogDao");
		return credisDao.deleteCreditslog(creditslog);
	}
	public List<Creditslog> findAllCreditslog(){
		CreditslogDao credisDao = (CreditslogDao)BeanFactory.getBean("creditslogDao");
		return credisDao.findAllCreditslog();
	}
	public List<Creditslog> findAllCreditslogByOperation(String []operation){
		CreditslogDao credisDao = (CreditslogDao)BeanFactory.getBean("creditslogDao");
		return credisDao.findAllCreditslogByOperation(operation);
	}
	public List<Creditslog> findCreditslogByKeys(String keyword){
		CreditslogDao credisDao = (CreditslogDao)BeanFactory.getBean("creditslogDao");
		return credisDao.findCreditslogByKeys(keyword);
	}
	public List<Creditslog> findCreditslogByUid(int uid,int maxrow){
		CreditslogDao credisDao = (CreditslogDao)BeanFactory.getBean("creditslogDao");
		return credisDao.findCreditslogByUid(uid,maxrow);
	}
	public List<Creditslog> findCreditsLogByHql(String hql,int startrow,int maxrow){
		CreditslogDao credisDao = (CreditslogDao)BeanFactory.getBean("creditslogDao");
		return credisDao.findCreditsLogByHql(hql, startrow, maxrow);
	}
	public int findCreditslogCountbyHql(String hql){
		CreditslogDao credisDao = (CreditslogDao)BeanFactory.getBean("creditslogDao");
		return credisDao.findCreditslogCountbyHql(hql);
	}
}
