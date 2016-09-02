package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.TypevarsDao;
import cn.jsprun.domain.Typevars;
import cn.jsprun.domain.TypevarsId;
import cn.jsprun.utils.BeanFactory;
public class TypevarService {
	public boolean addTypevar(Typevars typevar){
		return ((TypevarsDao) BeanFactory.getBean("typevarsDao")).addTypevar(typevar);
	}
	public boolean removeTypevar(Typevars typevar){
		return ((TypevarsDao) BeanFactory.getBean("typevarsDao")).removeTypevar(typevar);
	}
	public boolean updateTypevar(Typevars typevar){
		return ((TypevarsDao) BeanFactory.getBean("typevarsDao")).updateTypevar(typevar);
	}
	public Typevars findById(TypevarsId id){
		return ((TypevarsDao) BeanFactory.getBean("typevarsDao")).findById(id);
	}
	public List<Typevars> findByTId(Short typeid){
		return ((TypevarsDao) BeanFactory.getBean("typevarsDao")).findByTId(typeid);
	}
	public Typevars findByTO(Short typeid,Short optionid){
		return ((TypevarsDao) BeanFactory.getBean("typevarsDao")).findByTO(typeid, optionid);
	}
	public List<Typevars> findAll(){
		return ((TypevarsDao) BeanFactory.getBean("typevarsDao")).findAll();
	}
}