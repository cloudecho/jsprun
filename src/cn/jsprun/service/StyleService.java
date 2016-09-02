package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.StylesDao;
import cn.jsprun.domain.Styles;
import cn.jsprun.utils.BeanFactory;
public class StyleService {
	public boolean addStyle(Styles style){
		return ((StylesDao) BeanFactory.getBean("stylesDao")).addStyle(style);
	}
	public boolean removeStyle(Styles style){
		return ((StylesDao) BeanFactory.getBean("stylesDao")).removeStyle(style);
	}
	public boolean updateStyle(Styles style){
		return ((StylesDao) BeanFactory.getBean("stylesDao")).updateStyle(style);
	}
	public Styles findById(Short styleid){
		return ((StylesDao) BeanFactory.getBean("stylesDao")).findById(styleid);
	}
	public List<Styles> findByProperty(String propertyName,Object value){
		return ((StylesDao) BeanFactory.getBean("stylesDao")).findByProperty(propertyName, value);
	}
	public List<Styles> findAll(){
		return ((StylesDao) BeanFactory.getBean("stylesDao")).findAll();
	}
}
