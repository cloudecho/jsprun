package cn.jsprun.foreg.service;
import java.util.List;
import cn.jsprun.dao.BuddysDao;
import cn.jsprun.dao.PmsDao;
import cn.jsprun.dao.PmsearchindexDao;
import cn.jsprun.domain.Buddys;
import cn.jsprun.domain.Pms;
import cn.jsprun.domain.Pmsearchindex;
import cn.jsprun.utils.BeanFactory;
public class PmsService {
	public List<Pms> findPmsByHql(String hql, int begin, int maxlength) {
		return ((PmsDao) BeanFactory.getBean("pmsDao")).findPmsByHql(hql, begin, maxlength);
	}
	public int findPmsCountByHql(String hql) {
		return ((PmsDao) BeanFactory.getBean("pmsDao")).findPmsCountByHql(hql);
	}
	public Pms findPmsBypmid(int pmid) {
		return ((PmsDao) BeanFactory.getBean("pmsDao")).findPmsBypmid(pmid);
	}
	public void updatePms(Pms pms) {
		((PmsDao) BeanFactory.getBean("pmsDao")).updatePms(pms);
	}
	public boolean deletePms(Pms pms) {
		return ((PmsDao) BeanFactory.getBean("pmsDao")).deletePms(pms);
	}
	public List<Buddys> findBuddysByUid(int uid) {
		return ((BuddysDao) BeanFactory.getBean("buddysDao")).findBuddysByUid(uid);
	}
	public void insertPmsList(List<Pms> pmsList) {
		((PmsDao) BeanFactory.getBean("pmsDao")).insertPmsList(pmsList);
	}
	public int insertPmsearchindex(Pmsearchindex pmssearchindex){
		return ((PmsearchindexDao) BeanFactory.getBean("pmsearchindexDao")).insertPmsearchindex(pmssearchindex);
	}
	public boolean deletePmsearchindex(Pmsearchindex pmssearchindex){
		return ((PmsearchindexDao) BeanFactory.getBean("pmsearchindexDao")).deletePmsearchindex(pmssearchindex);
	}
	public Pmsearchindex findPmssearchindexByHql(String hql){
		return ((PmsearchindexDao) BeanFactory.getBean("pmsearchindexDao")).findPmssearchindexByHql(hql);
	}
	public Pmsearchindex findPmsearchindexById(int searchid){
		return ((PmsearchindexDao) BeanFactory.getBean("pmsearchindexDao")).findPmsearchindexById(searchid);
	}
}
