package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.AttachtypesDao;
import cn.jsprun.domain.Attachtypes;
import cn.jsprun.utils.BeanFactory;
public class AttachtypesService {
	public List<Attachtypes> getAll() {
		return ((AttachtypesDao) BeanFactory.getBean("attachtypesDao")).getAll();
	}
	public boolean saveAttachtypes(Attachtypes attachtypes) {
		if (attachtypes != null){
			return ((AttachtypesDao) BeanFactory.getBean("attachtypesDao")).saveAttachtypes(attachtypes);
		}
		return false;
	}
	public Integer deleteList(String[] id) {
		if (id != null) {
			return ((AttachtypesDao) BeanFactory.getBean("attachtypesDao")).deleteList(id);
		}
		return -1;
	}
	public Integer updateExtensionList(List<Attachtypes> list) {
		if (list != null) {
			return ((AttachtypesDao) BeanFactory.getBean("attachtypesDao")).updateExtensionList(list);
		}
		return -1;
	}
	public Integer updateMaxsizeList(List<Attachtypes> list) {
		if (list != null) {
			return ((AttachtypesDao) BeanFactory.getBean("attachtypesDao")).updateMaxsizeList(list);
		}
		return -1;
	}
	public boolean isSave(String name) {
		return ((AttachtypesDao) BeanFactory.getBean("attachtypesDao")).isSave(name);
	}
}
