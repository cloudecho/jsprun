package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.BbcodesDao;
import cn.jsprun.domain.Bbcodes;
import cn.jsprun.utils.BeanFactory;
public class BbcodesService {
	public List<Bbcodes> findByAll() {
		return ((BbcodesDao) BeanFactory.getBean("bbcodesDao")).findByAll();
	}
	public Integer deleteArray(String[] ids) {
		if (ids != null) {
			return ((BbcodesDao) BeanFactory.getBean("bbcodesDao")).deleteArray(ids);
		}
		return -1;
	}
	public Boolean saveBbcodes(Bbcodes bbcodes) {
		if (bbcodes != null) {
			return ((BbcodesDao) BeanFactory.getBean("bbcodesDao")).saveBbcodes(bbcodes);
		}
		return false;
	}
	public Integer updateAvailableArray(List<Bbcodes> list) {
		if (list != null && list.size() > 0){
			return ((BbcodesDao) BeanFactory.getBean("bbcodesDao")).updateAvailableArray(list);
		}
		return -1;
	}
	public Integer updateIconArray(List<Bbcodes> list) {
		if (list != null && list.size() > 0){
			return ((BbcodesDao) BeanFactory.getBean("bbcodesDao")).updateIconArray(list);
		}
		return -1;
	}
	public Integer updateTagArray(List<Bbcodes> list) {
		if (list != null && list.size() > 0){
			return ((BbcodesDao) BeanFactory.getBean("bbcodesDao")).updateTagArray(list);
		}
		return -1;
	}
	public Bbcodes findByID(Integer id) {
		return ((BbcodesDao) BeanFactory.getBean("bbcodesDao")).findByID(id);
	}
	public boolean updateBbcodes(Bbcodes b) {
		if (b != null) {
			return ((BbcodesDao) BeanFactory.getBean("bbcodesDao")).updateBbcodes(b);
		}
		return false;
	}
}
