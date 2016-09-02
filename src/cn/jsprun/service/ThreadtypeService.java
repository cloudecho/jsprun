package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.ThreadtypesDao;
import cn.jsprun.domain.Threadtypes;
import cn.jsprun.utils.BeanFactory;
public class ThreadtypeService {
	public boolean addThreadtype(Threadtypes threadtype){
		return ((ThreadtypesDao) BeanFactory.getBean("threadtypesDao")).addThreadtype(threadtype);
	}
	public boolean removeThreadtype(Threadtypes threadtype){
		return ((ThreadtypesDao) BeanFactory.getBean("threadtypesDao")).removeThreadtype(threadtype);
	}
	public boolean updateThreadtype(Threadtypes threadtype){
		return ((ThreadtypesDao) BeanFactory.getBean("threadtypesDao")).updateThreadtype(threadtype);
	}
	public Threadtypes findById(Short typeId){
		return ((ThreadtypesDao) BeanFactory.getBean("threadtypesDao")).findById(typeId);
	}
	public List<Threadtypes> findByProperty(String propertyName, Object value){
		return ((ThreadtypesDao) BeanFactory.getBean("threadtypesDao")).findByProperty(propertyName, value);
	}
	public List<Threadtypes> findBySpecial(Short typeId){
		return ((ThreadtypesDao) BeanFactory.getBean("threadtypesDao")).findBySpecial(typeId);
	}
	public List<Threadtypes> findAll(){
		return ((ThreadtypesDao) BeanFactory.getBean("threadtypesDao")).findAll();
	}
}
