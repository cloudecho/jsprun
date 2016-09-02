package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.BannedDao;
import cn.jsprun.domain.Banned;
import cn.jsprun.utils.BeanFactory;
public class BannedService {
	public boolean insertBanned(Banned banned) {
		return ((BannedDao) BeanFactory.getBean("bannedDao")).insertBanned(banned);
	}
	public boolean deleteBanned(Banned banned) {
		return ((BannedDao) BeanFactory.getBean("bannedDao")).deleteBanned(banned);
	}
	public List<Banned> findAllBanned() {
		return ((BannedDao) BeanFactory.getBean("bannedDao")).findAllBanned();
	}
	public Banned findById(Short id) {
		return ((BannedDao) BeanFactory.getBean("bannedDao")).findById(id);
	}
	public boolean modifyBanned(Banned banned) {
		return ((BannedDao) BeanFactory.getBean("bannedDao")).modifyBanned(banned);
	}
}
