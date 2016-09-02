package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.TypemodelsDao;
import cn.jsprun.domain.Typemodels;
import cn.jsprun.utils.BeanFactory;
public class TypemodelService {
	public boolean addTypemodel(Typemodels typemodel){
		return ((TypemodelsDao) BeanFactory.getBean("typemodelsDao")).addTypemodel(typemodel);
	}
	public boolean removeTypemodel(Typemodels typemodel){
		return ((TypemodelsDao) BeanFactory.getBean("typemodelsDao")).removeTypemodel(typemodel);
	}
	public boolean updateTypemodel(Typemodels typemodel){
		return ((TypemodelsDao) BeanFactory.getBean("typemodelsDao")).updateTypemodel(typemodel);
	}
	public Typemodels findById(Short id){
		return ((TypemodelsDao) BeanFactory.getBean("typemodelsDao")).findById(id);
	}
	public List<Typemodels> findByProperty(String propertyName, Object value){
		return ((TypemodelsDao) BeanFactory.getBean("typemodelsDao")).findByProperty(propertyName, value);
	}
	public List<Typemodels> findAll(){
		return ((TypemodelsDao) BeanFactory.getBean("typemodelsDao")).findAll();
	}
}
