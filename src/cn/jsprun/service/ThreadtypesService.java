package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.ThreadtypesDao;
import cn.jsprun.domain.Threadtypes;
import cn.jsprun.utils.BeanFactory;
public class ThreadtypesService {
	public List<Threadtypes> getAllThreadtypes() {
		return ((ThreadtypesDao) BeanFactory.getBean("threadtypesDao")).findAll();
	}
}
